/*
 *    Created on 6 de Enero de 2006, 14h35
 */      
package Compras.ZafCom02;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
  
/**
 * "ORDENES DE COMPRA"
 * @author  Javier Ayapata G. 
 */
public class ZafCom02 extends javax.swing.JInternalFrame
{
    //Constantes: JTable TblDat
    private static final int INT_TBL_DAT_LIN       = 0 ; 
    private static final int INT_TBL_DAT_ITM_ALT   = 1 ;
    private static final int INT_TBL_DAT_BUT_ITM   = 2 ;
    private static final int INT_TBL_DAT_DES_ITM   = 3 ;
    private static final int INT_TBL_DAT_UNI_MED   = 4 ;
    private static final int INT_TBL_DAT_COD_BOD   = 5 ;
    private static final int INT_TBL_DAT_BUT_BOD   = 6 ;
    private static final int INT_TBL_DAT_CAN_ORI   = 7 ;
    private static final int INT_TBL_DAT_CAN_MOV   = 8 ;     //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_DAT_PRE_COS   = 9 ;     //Precio de Venta
    private static final int INT_TBL_DAT_POR_DES   = 10;     //Porcentaje de descuento
    private static final int INT_TBL_DAT_CHK_IVA   = 11;     //Boolean Iva
    private static final int INT_TBL_DAT_VAL_TOT   = 12;     //Total de la venta o compra del producto
    private static final int INT_TBL_DAT_COD_ITM   = 13;
    private static final int INT_TBL_DAT_ITM_ORI   = 14;     //Código del item de O/C Cargada.
    private static final int INT_TBL_DAT_BOD_ORI   = 15;     //Código del bodega de O/C Cargada.
    private static final int INT_TBL_DAT_EST_REG   = 16;
    private static final int INT_TBL_DAT_TXT_IVA   = 17;
    private static final int INT_TBL_DAT_ITM_ALT2  = 18;
    private static final int INT_TBL_DAT_ITM_SER   = 19;     //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_DAT_COD_REG   = 20;     //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_DAT_COD_ITMACT= 21;     //Código  alterno del código actual.
    private static final int INT_TBL_DAT_IEBODFIS  = 22;     //Estado que indica si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_DAT_COS_UNI_ORI= 23;  
    private static final int INT_TBL_DAT_POR_DES_ORI= 24;    //Porcentaje de descuento de O/C Cargada.
    private static final int INT_TBL_DAT_PRE_COS_RES= 25;  
    private static final int INT_TBL_DAT_POR_DES_RES= 26;  

    //Constantes del ArrayList
    final int INT_ARL_STK_INV_STK_DIS=10;               // nd_stkDisp
    final int INT_ARL_STK_INV_STK=0;                    // nd_stk

    //Variables
    private Connection conCab, CONN_GLO, conRemGlo;     
    private Statement stmCab;   //Statement para la cotizacion 
    private ResultSet rstCab;//Resultset que tendra los datos de la cabecera    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private Librerias.ZafUtil.ZafCliente_dat objCliente_dat;    // Para Obtener la informacion del proveedor
    private Librerias.ZafUtil.ZafCiudad_dat objCiudad_dat;      // Para Obtener la informacion de la ciudad del proveedor
    private Librerias.ZafUtil.UltDocPrint objUltDocPrint;       // Para trabajar con la informacion de tipo de documento    
    private Librerias.ZafUtil.ZafCtaCtb objCtaCtb;              // Para obtener  los codigos y nombres de ctas ctbles
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;         //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;         //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;         //Editor: JTextField en celda.
    private ZafTblCelEdiBut objTblCelEdiButBod;        //Editor: JButton en celda.   
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConBod;  //Editor: JTextField de consulta en celda.
    private ZafTblCelRenChk objTblCelRenChk;         //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;         //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
    private Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    private LisTextos objDocLis;                   //Instancia de clase que detecta cambios
    private ZafTblPopMnu ZafTblPopMnu;
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.   
    private ZafVenCon vcoItm; 
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoPrv; 
    private ZafVenCon vcoCom; 
    private javax.swing.JInternalFrame jfrThis; //Hace referencia a this

    private boolean blnLimpiar = true; //Limpiar o no en el metodo limpiarFrm
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento    
    private boolean blnChangeData=false;
    
    private double dblPorIva ;         //Porcentaje de Iva para la empresa enviado en ZafParSis.
    private double dblPorDesOc=0;      //Porcentaje de descuento para las ordenes de compra.
    private double dblTotalCom, dblIvaCom,dblSubtotalCom;  
    
    private int INTCODREGCEN=0;
    private int intCodMnu; //obtiene el codigo del menu
    private int intNumDec, intNumDoc= 0; //Numero de decimales a presentar
    private int intCodEmp =0;   
    private int intCodLoc =0;   
    private int intCodCot=0;  
    private int intValBus = 0; 
    private int Glo_intCodSec=0;
    private int intCodTipPerEmp;
    
    private String strBeforeValue,strAfterValue;    
    private String strCodPrv, strDesLarPrv;   
    private String strCodCom, strDesLarCom;       
    private String GLO_strArreItm="";
    private String STR_ESTREG="";
    private String stIvaCom="S";        
    private final String strVer = " v9.0 ";

    private final int intarreglonum[] = new int[10]; 
    private final int intarreglodia[] = new int[10]; 
    private final int intCanArr[]= new int[1];
    private final ArrayList arreBod=new ArrayList();

    //Constante Mensaje Resaltador
    private Vector vecDat, vecCab, vecReg; 
    private Vector vecForPag; 
    private Vector vecDetDia;      
    private String strFecSis;
    private String strSQL, strFiltro, strAux;
    private String strMerIngEgr="";

    private double dlbValorOriTotOrd=0.00;

    private JPanel panForRet = new JPanel();
    private JLabel lblForRet = new JLabel();
    private JTextField txtCod = new JTextField();
    private JTextField txtDes = new JTextField();
    private JButton butForret = new JButton();
    private JLabel lblvehRet = new JLabel();
    private JTextField txtVehRet = new JTextField();
    private JTextField txtChoRet = new JTextField(); 
    private JLabel lblChoret = new JLabel();

    private int intCodTipDocOc=0;
    private int intCodDocOc=0;
    
    private String strCodEmpOC="";
    private String strCodLocOC="";
    private String strCodTipOC="";
    private String strCodDocOC="";
    private boolean blnEstCarOC=false;

    /** Creates new form ZafCom02 */  
    public ZafCom02(ZafParSis obj) 
    {
        try{  
            this.objParSis = (ZafParSis) obj.clone();
            jfrThis = this;
            initComponents();
            
            objUti = new ZafUtil();
            this.setTitle(objParSis.getNombreMenu()+strVer);
            
            //***********************************************************************
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            //***********************************************************************
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            /* Iniciando clase de informacion de cliente*/
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);         
       
            tabFrm.addTab("Asiento Diario",objAsiDia);    
        
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            txtCodTipDoc.setVisible(false);        
        
            abrirCnx();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
          
            cargarTipDocPre();
        
            intCodMnu = objParSis.getCodigoMenu();
            intNumDec = objParSis.getDecimalesMostrar();
            
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());      
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText(strFecSis);
            dtpFecDoc.setBounds(584, 4, 100, 20);
            dtpFecDoc.setEnabled(false);
            panGenNor.add(dtpFecDoc);
            
            panForRet.setLayout(null);
            panForRet.add(txtDes);
            txtDes.setBounds(130, 20, 220, 21);

            lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblForRet.setText("Forma de Retiro:");
            panForRet.add(lblForRet);
            lblForRet.setBounds(20, 20, 100, 15);

            lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblvehRet.setText("Vehiculo de Retiro:");
            panForRet.add(lblvehRet);
            lblvehRet.setBounds(20, 45, 100, 15);

            lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblChoret.setText("Chofer de Retiro:");
            panForRet.add(lblChoret);
            lblChoret.setBounds(20, 75, 100, 15);

            panForRet.add(txtChoRet);
            txtChoRet.setBounds(130, 70, 220, 21);

            panForRet.add(txtVehRet);
            txtVehRet.setBounds(130, 45, 220, 21);

            txtCod.setEditable(false);
            txtCod.setBounds(130, 20, 40, 21);
        
            panForRet.add(butForret);
         
            butForret.setText("...");
            butForret.setPreferredSize(new java.awt.Dimension(35, 30));
            butForret.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                  buscarFormaRetiro();
                }
            });
            butForret.setBounds(350, 20, 22, 20);
            tabFrm.addTab("Forma de Retiro", panForRet);
        
            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.setTitleAt(1,"Forma de Pago");
            tabFrm.setTitleAt(2,"Asiento de Diario");
            objTooBar = new MiToolBar(this);
          
            vecForPag = new java.util.Vector();
            this.getContentPane().add(objTooBar,"South");
            pack();
        
            cargarTipEmp(CONN_GLO);
            getPorDesOC(CONN_GLO);

            /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
                NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
      
        
            /* Listener para detectar cambios en combo de pagos */
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
       
            cboForPag.addItem("< Refrescar >");
        
            ActLisForPag objActLis = new ActLisForPag();
            cboForPag.addActionListener(objActLis);
        
            /* Metodo para agregar o eliminar lineas con enter y con escape */
            objUti.verDecimalesEnTabla(tblForPag,4, objParSis.getDecimalesMostrar());
            agregarDocLis();

            /* Clase que agrega numeracion a el JTable */
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblForPag,0);         
        
            getTipMotRet(CONN_GLO);
            cargarBodegas(CONN_GLO);
         
            cerrarCnx();
         
            objUti.desactivarCom(this);
        }
        catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafCom02(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc ) {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
            strCodEmpOC= String.valueOf( intCodEmp.intValue() );
            strCodLocOC= String.valueOf( intCodLoc.intValue() );
            strCodTipOC= String.valueOf( intCodTipDoc.intValue() );
            strCodDocOC= String.valueOf( intCodDoc.intValue() );
            blnEstCarOC=true;
            
            initComponents();
            this.setTitle(objParSis.getNombreMenu()+strVer);
            objUti = new ZafUtil();

            //***********************************************************************
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
               @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                   if (txtCodTipDoc.getText().equals(""))
                       objAsiDia.setCodigoTipoDocumento(-1);
                   else
                       objAsiDia.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
                 }
            });
            //***********************************************************************

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objParSis);
            INTCODREGCEN=objObtConCen.intCodReg;

            /* Iniciando clase de informacion de cliente*/
             objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
             objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
             objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);

            tabFrm.addTab("Asiento de Diario",objAsiDia);

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            txtCodTipDoc.setVisible(false);

            abrirCnx();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */

            cargarTipDocPre();

            intCodMnu = objParSis.getCodigoMenu();
            intNumDec = 2;  //objParSis.getDecimalesMostrar();
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText(strFecSis);
            panGenNor.add(dtpFecDoc);
            dtpFecDoc.setBounds(584, 4, 100, 20);
            dtpFecDoc.setEnabled(false);

            //********************************
            panForRet.setLayout(null);

            panForRet.add(txtDes);
            txtDes.setBounds(130, 20, 220, 21);

            lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblForRet.setText("Forma de Retiro:");
            panForRet.add(lblForRet);
            lblForRet.setBounds(20, 20, 100, 15);

            lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblvehRet.setText("Vehiculo de Retiro:");
            panForRet.add(lblvehRet);
            lblvehRet.setBounds(20, 45, 100, 15);

            lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblChoret.setText("Chofer de Retiro:");
            panForRet.add(lblChoret);
            lblChoret.setBounds(20, 75, 100, 15);

            panForRet.add(txtChoRet);
            txtChoRet.setBounds(130, 70, 220, 21);

            panForRet.add(txtVehRet);
            txtVehRet.setBounds(130, 45, 220, 21);

            txtCod.setEditable(false);
            //panForRet.add(txtCod);
            txtCod.setBounds(130, 20, 40, 21);

            panForRet.add(butForret);

            butForret.setText("...");
            butForret.setPreferredSize(new java.awt.Dimension(35, 30));
            butForret.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                  buscarFormaRetiro();
                }
            });
            butForret.setBounds(350, 20, 22, 20);

            tabFrm.addTab("Forma de Retiro", panForRet);


            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.setTitleAt(1,"Forma de Pago");
            tabFrm.setTitleAt(2,"Asiento de Diario");
            objTooBar = new MiToolBar(this);

            vecForPag = new java.util.Vector();
            pack();

            cargarTipEmp(CONN_GLO);

            /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
                NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));


            /* Listener para detectar cambios en combo de pagos */ 
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);

            cboForPag.addItem("< Refrescar >");

            ActLisForPag objActLis = new ActLisForPag();
            cboForPag.addActionListener(objActLis);

           /* Metodo para agregar o eliminar lineas con enter y con escape */
           objUti.verDecimalesEnTabla(tblForPag,4, objParSis.getDecimalesMostrar());
           agregarDocLis();

            /* Clase que agrega numeracion a el JTable */
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblForPag,0);

            getTipMotRet(CONN_GLO);
            cargarBodegas(CONN_GLO);

            cerrarCnx();

            objUti.desactivarCom(this);
        }
        catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author TonySanginez
     * @fecha 15/Jul/2016
     * @param map
     */
    public ZafCom02(HashMap map) 
    {
        Librerias.ZafParSis.ZafParSis obj;
        Integer intCodEmp = null;
        Integer intCodLoc = null;
        Integer intCodTipDoc = null;
        Integer intCodDoc = null;
        try 
        {
            obj = (ZafParSis) map.get("objParSis");
            intCodEmp = Integer.valueOf(new String(map.get("strCodEmp").toString()));
            intCodLoc = Integer.valueOf(new String(map.get("strCodLoc").toString()));
            intCodTipDoc = Integer.valueOf(new String(map.get("strCodTipDoc").toString()));
            intCodDoc = Integer.valueOf(new String(map.get("strCodDoc").toString()));
            this.objParSis = (ZafParSis) obj.clone();
            strCodEmpOC = String.valueOf(intCodEmp.intValue());
            strCodLocOC = String.valueOf(intCodLoc.intValue());;
            strCodTipOC = String.valueOf(intCodTipDoc.intValue());;
            strCodDocOC = String.valueOf(intCodDoc.intValue());;
            blnEstCarOC = true;            
            jfrThis = this;
            initComponents();
            this.setTitle(objParSis.getNombreMenu() + strVer);
            objUti = new ZafUtil();
            
            //***********************************************************************
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(strCodTipOC));
                    }
                }
            });
            //***********************************************************************

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objParSis);
            INTCODREGCEN = objObtConCen.intCodReg;

            /* Iniciando clase de informacion de cliente*/
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
            objCiudad_dat = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);

            tabFrm.addTab("Diario", objAsiDia);

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            txtCodTipDoc.setVisible(false);

            abrirCnx();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
            
            intCodMnu = objParSis.getCodigoMenu();
            intNumDec = objParSis.getDecimalesMostrar();
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText(strFecSis);
            panGenNor.add(dtpFecDoc);
            dtpFecDoc.setBounds(584, 4, 100, 20);
            dtpFecDoc.setEnabled(false);

            //********************************
            panForRet.setLayout(null);

            panForRet.add(txtDes);
            txtDes.setBounds(130, 20, 220, 21);

            lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblForRet.setText("Forma de Retiro:");
            panForRet.add(lblForRet);
            lblForRet.setBounds(20, 20, 100, 15);

            lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblvehRet.setText("Vehiculo de Retiro:");
            panForRet.add(lblvehRet);
            lblvehRet.setBounds(20, 45, 100, 15);

            lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblChoret.setText("Chofer de Retiro:");
            panForRet.add(lblChoret);
            lblChoret.setBounds(20, 75, 100, 15);

            panForRet.add(txtChoRet);
            txtChoRet.setBounds(130, 70, 220, 21);

            panForRet.add(txtVehRet);
            txtVehRet.setBounds(130, 45, 220, 21);

            txtCod.setEditable(false);
            //panForRet.add(txtCod);
            txtCod.setBounds(130, 20, 40, 21);

            panForRet.add(butForret);

            butForret.setText("...");
            butForret.setPreferredSize(new java.awt.Dimension(35, 30));
            butForret.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buscarFormaRetiro();
                }
            });
            butForret.setBounds(350, 20, 22, 20);

            tabFrm.addTab("Forma de Retiro", panForRet);

            //********************************
            //Nombres de los tabs
            tabFrm.setTitleAt(0, "General");
            tabFrm.setTitleAt(1, "Forma de Pago");
            tabFrm.setTitleAt(2, "Asiento de Diario");
            objTooBar = new MiToolBar(this);

            vecForPag = new java.util.Vector();
            pack();

            cargarTipEmp(CONN_GLO);

            /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
             NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350, 30));


            /* Listener para detectar cambios en combo de pagos */
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);

            cboForPag.addItem("< Refrescar >");

            ActLisForPag objActLis = new ActLisForPag();
            cboForPag.addActionListener(objActLis);

            /* Metodo para agregar o eliminar lineas con enter y con escape */
            objUti.verDecimalesEnTabla(tblForPag, 4, objParSis.getDecimalesMostrar());
            agregarDocLis();

            /* Clase que agrega numeracion a el JTable */
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblForPag, 0);

            getTipMotRet(CONN_GLO);
            cargarBodegas(CONN_GLO);

            cerrarCnx();

            objUti.desactivarCom(this);
        }
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafCom02(ZafParSis objParSis,int CodEmp,int CodLoc,int CodCot) {
        this(objParSis);
        intCodEmp = CodEmp;
        intCodLoc = CodLoc;
        intCodCot= CodCot;
        intValBus = 1;
    }
    
        public ZafCom02(ZafParSis objParSis,int CodEmp,int CodLoc, int intCodTpDoc, int CodDoc, int intCodMnuOC ) {
        this(objParSis);
        intCodEmp = CodEmp;
        intCodLoc = CodLoc;
        intCodTipDocOc=intCodTpDoc;
        intCodDocOc= CodDoc;
        intCodMnu=intCodMnuOC;
        intValBus = 2;
    }
     
    
    private boolean configurarTblDat() {
        boolean blnRes=true;
        try{
            txtCod.setBackground(objParSis.getColorCamposObligatorios());
            txtDes.setBackground(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();           //Almacena los datos 
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");            
            vecCab.add(INT_TBL_DAT_ITM_ALT,"Cod. Item");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_DES_ITM,"Descripcion");            
            vecCab.add(INT_TBL_DAT_UNI_MED,"Unidad");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Bodega");            
            vecCab.add(INT_TBL_DAT_BUT_BOD,"");
            vecCab.add(INT_TBL_DAT_CAN_ORI,"");
            vecCab.add(INT_TBL_DAT_CAN_MOV,"Cantidad");
            vecCab.add(INT_TBL_DAT_PRE_COS,"Costo");            
            vecCab.add(INT_TBL_DAT_POR_DES,"Descuento");
            vecCab.add(INT_TBL_DAT_CHK_IVA,"IVA");
            vecCab.add(INT_TBL_DAT_VAL_TOT,"Total");            
            vecCab.add(INT_TBL_DAT_COD_ITM,"");
            vecCab.add(INT_TBL_DAT_ITM_ORI,"");
            vecCab.add(INT_TBL_DAT_BOD_ORI,"");
            vecCab.add(INT_TBL_DAT_EST_REG,"");
            vecCab.add(INT_TBL_DAT_TXT_IVA,"");
            vecCab.add(INT_TBL_DAT_ITM_ALT2,"Cod.Item2");
            vecCab.add(INT_TBL_DAT_ITM_SER,"Ser.");
            vecCab.add(INT_TBL_DAT_COD_REG , "CodReg");
            vecCab.add(INT_TBL_DAT_COD_ITMACT, "CodAct");
            vecCab.add(INT_TBL_DAT_IEBODFIS, "IE.Fis.Bod"); 
            vecCab.add(INT_TBL_DAT_COS_UNI_ORI, "CosUniOri");
            vecCab.add(INT_TBL_DAT_POR_DES_ORI, "PorDesOri");
            vecCab.add(INT_TBL_DAT_PRE_COS_RES, "CosUniRes");
            vecCab.add(INT_TBL_DAT_POR_DES_RES, "PorDesRes");

            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada objColNumerada = new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ORI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_MOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PRE_COS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_POR_DES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_TOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_ITM_ALT);
            arlAux.add("" + INT_TBL_DAT_COD_BOD);
            arlAux.add("" + INT_TBL_DAT_CAN_MOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ITM);
            arlAux.add("" + INT_TBL_DAT_COD_BOD);
            arlAux.add("" + INT_TBL_DAT_CAN_MOV);
            arlAux.add("" + INT_TBL_DAT_ITM_ORI);
            arlAux.add("" + INT_TBL_DAT_BOD_ORI);
            arlAux.add("" + INT_TBL_DAT_CAN_ORI);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
                  
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_DES_ITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_DAT_PRE_COS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setPreferredWidth(80);         
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setPreferredWidth(80);     
                     
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ITM_SER, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_IEBODFIS, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TXT_IVA, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ORI, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ITM_ORI, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BOD_ORI, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_REG, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ITM_ALT2, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ITM, tblDat);   
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITMACT, tblDat);      
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI_ORI, tblDat);             
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_DES_ORI, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PRE_COS_RES, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_DES_RES, tblDat);    

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DES_ITM);
            vecAux.add("" + INT_TBL_DAT_PRE_COS);
            vecAux.add("" + INT_TBL_DAT_POR_DES);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi objTblEdi = new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer el menú de contexto.
            ZafTblPopMnu = new ZafTblPopMnu(tblDat);
            ZafTblPopMnu.setEliminarFilaVisible(false);
            ZafTblPopMnu.setInsertarFilasVisible(false);
            ZafTblPopMnu.setInsertarFilaVisible(false);            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    blnChangeData = false;         
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG)==null){  
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);
                    } 
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }

                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V") && tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_CHK_IVA)!=null){
                        calculaSubtotal();
                        blnChangeData = true;
                    }                        
                }
            });

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_COS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL="";            
            strSQL= " SELECT distinct(a1.co_bod), a1.tx_nom, a2.nd_stkAct "+
                    " FROM tbm_bod AS a1 RIGHT OUTER JOIN tbr_bodLoc AS bodloc ON ( a1.co_emp=bodloc.co_emp AND a1.co_bod=bodloc.co_bod) "+
                    " RIGHT OUTER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)"+
                    " WHERE a1.st_reg='A' AND a2.co_emp=" + objParSis.getCodigoEmpresa() + "";            
            int intColVenBod[]=new int[1];
            intColVenBod[0]=1;
            int intColTblBod[]=new int[1];
            intColTblBod[0]=INT_TBL_DAT_COD_BOD;
            objTblCelEdiTxtConBod=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado Bodega", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL,  "a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            objTblCelEdiTxtConBod.setIndiceCampoBusqueda(0);
            objTblCelEdiTxtConBod.setCampoBusqueda("a1.co_bod", ZafTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtConBod.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellEditor(objTblCelEdiTxtConBod);            
            objTblCelEdiTxtConBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString();
                    objTblCelEdiTxtConBod.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                }
                @Override
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!objTblCelEdiTxtConBod.isConsultaAceptada()){
                    }
                }                
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD).toString();
                    else
                        strBeforeValue ="";
 
                }
                    
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
                }
            });
            
            objTblCelEdiButBod=new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado Bodega", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setCellEditor(objTblCelEdiButBod);
            objTblCelEdiButBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString();
                    objTblCelEdiButBod.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                    
                }
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD).toString();
                    else
                        strBeforeValue ="";
 
                }                    
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_BOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
                }
            });
            intColVenBod=null;
            intColTblBod=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
          
  
            int intColVen2[]=new int[8];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=5;
            intColVen2[3]=2;            
            intColVen2[4]=6;          
            intColVen2[5]=7;         
            intColVen2[6]=8;
            intColVen2[7]=9;
            int intColTbl2[]=new int[8];
            intColTbl2[0]=INT_TBL_DAT_ITM_ALT;
            intColTbl2[1]=INT_TBL_DAT_DES_ITM;
            intColTbl2[2]=INT_TBL_DAT_PRE_COS;
            intColTbl2[3]=INT_TBL_DAT_COD_ITM;
            intColTbl2[4]=INT_TBL_DAT_TXT_IVA;
            intColTbl2[5]=INT_TBL_DAT_UNI_MED;
            intColTbl2[6]=INT_TBL_DAT_ITM_ALT2;
            intColTbl2[7]=INT_TBL_DAT_ITM_SER;
            
              objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen2, intColTbl2);  //********
              tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setCellEditor(objTblCelEdiTxtVcoItm);  //******
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******  
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_ITM_ALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_ITM_ALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);
                    }
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_ITM_ALT)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_ITM_ALT).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_TXT_IVA)!=null){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_TXT_IVA).equals("S"))
                            tblDat.setValueAt(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_IVA);
                        else
                            tblDat.setValueAt(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_IVA);
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;        
                       tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD);
                        calculaSubtotal();
                    }                        
                }
            });

            ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_DAT_BUT_ITM);   //*****
            
            ///////////////////////////////////////////////////////////////////***********************//////////////////////////////////

            intColVen2=null;
            intColTbl2=null;
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PRE_COS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PRE_COS).setCellRenderer(new RenderDecimales(intNumDec) );            
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);
                    }
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ITM_ALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);                            
                    }
                           
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
                    if (tblDat.getSelectedColumn()==INT_TBL_DAT_POR_DES){
                        if(strAfterValue.equals("")) strAfterValue="0.00";
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            mostrarMsgInf("Error! Porcentaje de Descuento ");
                            tblDat.setValueAt(strBeforeValue, tblDat.getSelectedRow(), INT_TBL_DAT_POR_DES);
                        }

                       if(!getVerificaMarPor(tblDat.getSelectedRow())){
                           mostrarMsgInf("Ha sobrepasado el porcentaje establecido del "+dblPorDesOc+"%  ");
                           tblDat.setValueAt( tblDat.getValueAt(  tblDat.getSelectedRow(), INT_TBL_DAT_POR_DES_RES), tblDat.getSelectedRow(), INT_TBL_DAT_POR_DES);
                        }
                    }
                    

                    if (tblDat.getSelectedColumn()==INT_TBL_DAT_PRE_COS){
                        if(!getVerificaMarPor(tblDat.getSelectedRow())){
                           mostrarMsgInf("Ha sobrepasado el porcentaje establecido del "+dblPorDesOc+"%  ");
                           tblDat.setValueAt( tblDat.getValueAt(  tblDat.getSelectedRow(), INT_TBL_DAT_PRE_COS_RES), tblDat.getSelectedRow(), INT_TBL_DAT_PRE_COS);
                        }
                    }

                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_EST_REG).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true; 
                        calculaSubtotal();
                    }
                    
                    
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
        }
        catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }    

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_ITM_ALT:
                    strMsg="Codigo Item";
                    break;
                case INT_TBL_DAT_DES_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Codigo Bodega";
                    break;
                case INT_TBL_DAT_CAN_MOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_PRE_COS:
                    strMsg="Costo";
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
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
              ///      tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularTotal();
                    calcularPagos();
                    generaAsiento();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis(){
        //Cabecera
        txtNumCot.setText("");
        txtNumCot.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtNomPrv.getDocument().addDocumentListener(objDocLis);
        txtDirPrv.getDocument().addDocumentListener(objDocLis);
        txtCodCom.getDocument().addDocumentListener(objDocLis);
        txtNomCom.getDocument().addDocumentListener(objDocLis);
        txtAte.getDocument().addDocumentListener(objDocLis);
        
        //Pie de pagina
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
        txtSub.getDocument().addDocumentListener(objDocLis);
        txtIva.getDocument().addDocumentListener(objDocLis);
        txtTot.getDocument().addDocumentListener(objDocLis);
    }   
    
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements javax.swing.event.DocumentListener {
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }     
   
    @Override
    protected void finalize() throws Throwable {  
        super.finalize();
        System.out.println ("Se libera Objeto...");
    }

    private class RenderDecimales extends JLabel implements TableCellRenderer {
        int intNumDecimales = 0;
        public RenderDecimales(int numeroDecimales) {
            this.intNumDecimales=numeroDecimales ;
            setHorizontalAlignment(JLabel.RIGHT);
            setOpaque(true);
            setBackground(new Color(255, 255, 255));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            double dblSubPro = Double.parseDouble( (value == null)?"0":""+value);
            ZafUtil objutil = new ZafUtil();
            if(isSelected){
                setBorder(new LineBorder(UIManager.getDefaults().getColor("Button.focus"), 1));
            }
            else{
                setBorder(null);
            }
            this.setText("" + objutil.redondeo(dblSubPro, intNumDecimales));
            this.setFont(new java.awt.Font("Tahoma", 0, 11));
            this.setOpaque(true);
            setBackground(new Color(201,223,245));
            setForeground(new Color(0, 0, 0));
            return this;
        }
    }    
    
    private void configurarVentanaConsulta(){
        configurarVenConTipDoc();
        configurarVenConItm();
        configurarVenConPrv();
        configurarVenConCom();
        objAsiDia.setEditable(false);

        if(blnEstCarOC){
          cargarDatos( strCodEmpOC, strCodLocOC, strCodTipOC, strCodDocOC  );
         }
    }
      
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");   
            
            //Armar la sentencia SQL.
            String sSQL="";
            if(objParSis.getCodigoUsuario()==1){
                sSQL=" SELECT distinct a.co_tipdoc, a.tx_descor, a.tx_deslar "+
                     " FROM tbr_tipdocprg AS b " + 
                     " LEFT OUTER JOIN tbm_cabtipdoc AS a ON (b.co_emp = a.co_emp AND b.co_loc = a.co_loc AND b.co_tipdoc = a.co_tipdoc)" +
                     " WHERE b.co_emp = " + objParSis.getCodigoEmpresa() + 
                     " AND b.co_loc = " + objParSis.getCodigoLocal() + 
                     " AND b.co_mnu = " + objParSis.getCodigoMenu();
            }
            else {     
                sSQL ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+ 
                      " FROM tbr_tipDocUsr AS a1 INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)"+
                      " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                      " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                      " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                      " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }
                  
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), sSQL, arlCam, arlAli, arlAncCol); 
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_CosUni");
            arlCam.add("a7.st_ivaCom");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.st_ser");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cód.Sis.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Código2");
            arlAli.add("Itm.Ser."); 
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("64");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
            String sSQL="";
            sSQL+=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " ;
            sSQL+=" FROM ( " ;
            sSQL+="        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
            sSQL+="        FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
            sSQL+="               FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            sSQL+="               WHERE a1.st_reg not in('T','I','E','U') and  a1.co_emp="+objParSis.getCodigoEmpresa()+" ";
            sSQL+="        ) AS d1";
            sSQL+="        INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
            sSQL+="                     INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            sSQL+="                     WHERE b1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" ";
            sSQL+="        ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
            sSQL+="        INNER JOIN ( SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
            sSQL+="                     FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
            sSQL+="                     INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
            sSQL+="                     WHERE c3.co_emp="+objParSis.getCodigoEmpresa()+" AND c3.co_loc="+objParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
            sSQL+="        ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
            sSQL+="        LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " ;
            sSQL+=" )  AS x WHERE upper(tx_codAlt) like '%S'" ;
            sSQL+=" ORDER BY tx_codAlt";
                   
             //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
              
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), sSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
         
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.co_tipper");
            arlCam.add("a.st_ivacom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("CO_TIPO");
            arlAli.add("Iva.Com");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
            String sSQL=" SELECT a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide, tx_tipper ,co_tipper, a.st_ivacom "+
                        " FROM tbr_cliloc as a1 " +
                        " INNER JOIN tbm_cli AS a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
                        " WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND a.st_reg='A'  " +
                        " AND a.st_prv='S' " +
                        " ORDER BY a.tx_nom";

            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , sSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    private boolean configurarVenConCom()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            
            //Armar la sentencia SQL.
            String sSQL="";
            sSQL+=" SELECT a.co_usr,a.tx_nom ";
            sSQL+=" FROM tbm_usr AS a ";
            sSQL+=" INNER JOIN tbr_usrEmp AS a1 ON a.co_usr=a1.co_usr";
            sSQL+=" WHERE a.st_reg NOT IN('I') ";
            sSQL+=" AND a1.st_com='S' ";  /* Sólo compradores */
            sSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa(); 
            sSQL+=" GROUP BY a.co_usr,a.tx_nom ";
            sSQL+=" ORDER BY a.tx_nom"; 

            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , sSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    private void cargarBodegas(Connection conTmp){
        Statement stmTmp;
        ResultSet rstTmp;
        try
        {
            if (conTmp!=null)
            {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT distinct(co_bod) FROM tbr_bodLoc WHERE co_emp="+objParSis.getCodigoEmpresa(); 
                rstTmp=stmTmp.executeQuery(sql);
                while(rstTmp.next()){
                  arreBod.add(""+rstTmp.getInt(1));    
                }
                rstTmp.close();
                rstTmp=null;
                stmTmp.close();
                stmTmp=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    }
    
    private void buscarFormaRetiro(){
        Compras.ZafCom02.ZafCom02_01 obj = new  Compras.ZafCom02.ZafCom02_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
        obj.show();  
        if(obj.acepta()){ 
            txtCod.setText(obj.GetCamSel(1));
            txtDes.setText(obj.GetCamSel(2));
        }     
    }    
    
    private void listaProductos(String strDesBusqueda){
        vcoItm.setTitle("Listado de Item"); 
         int intNumFil=tblDat.getSelectedRow();

        vcoItm.show();
        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            tblDat.setValueAt(vcoItm.getValueAt(1),intNumFil,INT_TBL_DAT_ITM_ALT);
            tblDat.setValueAt(vcoItm.getValueAt(2),intNumFil,INT_TBL_DAT_COD_ITM);
            tblDat.setValueAt(vcoItm.getValueAt(3),intNumFil,INT_TBL_DAT_DES_ITM);
            tblDat.setValueAt(vcoItm.getValueAt(7),intNumFil,INT_TBL_DAT_UNI_MED);
            tblDat.setValueAt(vcoItm.getValueAt(5),intNumFil,INT_TBL_DAT_PRE_COS);
            tblDat.setValueAt(vcoItm.getValueAt(6),intNumFil,INT_TBL_DAT_TXT_IVA); 
            tblDat.setValueAt(vcoItm.getValueAt(8),intNumFil,INT_TBL_DAT_ITM_ALT2);
            tblDat.setValueAt(vcoItm.getValueAt(9),intNumFil,INT_TBL_DAT_ITM_SER);
            tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), intNumFil, INT_TBL_DAT_COD_BOD);
              
            if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_CAN_MOV)==null)
                tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_CAN_MOV); 
            if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_POR_DES)==null)
                tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_POR_DES); 
            if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_VAL_TOT)==null)
                tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_VAL_TOT); 

            blnChangeData = false;                            
            if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT)!=null)
                strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT).toString();
            else 
                strBeforeValue = "";
            
            if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG)==null){
                tblDat.setValueAt("V", intNumFil, INT_TBL_DAT_EST_REG);
            }
              
            if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT)!=null)
                strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT).toString();
            else
                strAfterValue ="";
            
            if ((tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)==null){
                    tblDat.setValueAt("D",intNumFil , INT_TBL_DAT_EST_REG);                            
            }else if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("V")){
                if (tblDat.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)!=null)
                    tblDat.setValueAt("N", intNumFil, INT_TBL_DAT_EST_REG);                            
            }else if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("N")){
                if (tblDat.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)==null)
                    tblDat.setValueAt("V", intNumFil, INT_TBL_DAT_EST_REG);                            
            }else if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString().equals("D")){
                tblDat.setValueAt("M", intNumFil, INT_TBL_DAT_EST_REG);                            
            }

            if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_TXT_IVA)!=null){
                if (tblDat.getValueAt(intNumFil,INT_TBL_DAT_TXT_IVA).equals("S"))
                    tblDat.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_IVA);
                else
                    tblDat.setValueAt(false, intNumFil, INT_TBL_DAT_CHK_IVA);
            }
              
            calculaSubtotal();
        }
    }
    
    private class ButFndItm extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndItm(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
         @Override
        public void butCLick() {
             listaProductos("");
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MOV, false, false);
        }
    }
    
    private void noEditable(boolean blnEditable)
    {
        java.awt.Color colBack = txtNumCot.getBackground();

        txtNumDoc.setEditable(blnEditable);

        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);        

        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);        

        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);        
    }
    
    private void setEditable(boolean editable)
    {
        if (editable==true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else  {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }      
    }
             
    private void cargarTipDocPre()
    {
        objTipDoc.DocumentoPredeterminado_usr();
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar());
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
        getIva();
    }

    private void cargarCabTipDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar());      
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
        generaAsiento();
        getIva();
    }
    
    private void cargarTipEmp(java.sql.Connection conTipEmp){
        Statement stmTipEmp;
        ResultSet rstTipEmp;
        try{
            if (conTipEmp!=null){
                stmTipEmp=conTipEmp.createStatement();
                //Armar sentencia SQL
                String sSQL=" SELECT b.co_tipper, b.tx_descor, round(a.nd_ivaCom,2) AS porIva " +  /*JoseMario 6/Jun/2016*/
                            " FROM tbm_emp AS a LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp AND b.co_tipper=a.co_tipper)" +
                            " WHERE a.co_emp="+objParSis.getCodigoEmpresa();
                rstTipEmp = stmTipEmp.executeQuery(sSQL);
                if(rstTipEmp.next()){
                    intCodTipPerEmp=rstTipEmp.getInt("co_tipper");
                    dblPorIva = rstTipEmp.getDouble("porIva");
                    lblIva.setText("IVA " + dblPorIva + "%");
                }
                rstTipEmp.close();
                rstTipEmp=null;
                stmTipEmp.close();
                stmTipEmp=null;
            }
        }
        catch(SQLException Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }    
    }    

    /**
     * Función que valida si es nulo, asignas "0". 
     * <BR> Caso contrario, devuelve el valor que tiene.
     * @param objTbl Objeto de la celda.
     * @return  "0" si el nulo ó vacío "" el valor que tiene
     */
    private String getIntDatoValidado( Object objTbl ){
        String strVar="0";
        try{
            if(objTbl==null) strVar="0";
            else if(objTbl.toString().equals("")) strVar="0";
            else strVar=objTbl.toString();
        }
        catch(Exception e){ e.printStackTrace(); }
        return strVar;   
    }    
    
    private void cargaListadoFormaPago(){
        objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                             objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
        cboForPag.addItem("< Refrescar >");
        cboForPag.setSelectedIndex(0);
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenNor = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblNumCot = new javax.swing.JLabel();
        txtNumCot = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumOrdCom = new javax.swing.JLabel();
        txtNumOrdCom = new javax.swing.JTextField();
        lblNumPed = new javax.swing.JLabel();
        txtNumPed = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtNomPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblDirPrv = new javax.swing.JLabel();
        txtDirPrv = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblCom = new javax.swing.JLabel();
        txtCodCom = new javax.swing.JTextField();
        txtNomCom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        lblAte = new javax.swing.JLabel();
        txtAte = new javax.swing.JTextField();
        lblNumFacPrv = new javax.swing.JLabel();
        txtNumFacPrv = new javax.swing.JTextField();
        chkEmiChq = new javax.swing.JCheckBox();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenSur = new javax.swing.JPanel();
        panGenSurObs = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        panObs1 = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panObs2 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panGenSurTot = new javax.swing.JPanel();
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panForPag = new javax.swing.JPanel();
        panForPagNor = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        cboForPag = new javax.swing.JComboBox();
        spnForPag = new javax.swing.JScrollPane();
        tblForPag = new javax.swing.JTable();

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
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenNor.setPreferredSize(new java.awt.Dimension(800, 148));
        panGenNor.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenNor.add(lblTipDoc);
        lblTipDoc.setBounds(6, 4, 100, 20);
        panGenNor.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios()
        );
        txtDesCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenNor.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(112, 4, 63, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenNor.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(176, 4, 210, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenNor.add(butTipDoc);
        butTipDoc.setBounds(386, 5, 20, 20);

        lblNumCot.setText("No. Cotización:");
        panGenNor.add(lblNumCot);
        lblNumCot.setBounds(6, 24, 102, 20);

        txtNumCot.setMaximumSize(null);
        txtNumCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCotActionPerformed(evt);
            }
        });
        panGenNor.add(txtNumCot);
        txtNumCot.setBounds(112, 24, 92, 20);

        lblNumDoc.setText("No. Documento:");
        panGenNor.add(lblNumDoc);
        lblNumDoc.setBounds(6, 44, 102, 20);

        txtNumDoc.setBackground(objParSis.getColorCamposSistema()
        );
        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenNor.add(txtNumDoc);
        txtNumDoc.setBounds(112, 44, 92, 20);

        lblNumOrdCom.setText("No. Orden Compra:");
        panGenNor.add(lblNumOrdCom);
        lblNumOrdCom.setBounds(6, 64, 102, 20);

        txtNumOrdCom.setBackground(objParSis.getColorCamposSistema()
        );
        txtNumOrdCom.setMaximumSize(null);
        txtNumOrdCom.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumOrdCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumOrdComFocusLost(evt);
            }
        });
        txtNumOrdCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumOrdComActionPerformed(evt);
            }
        });
        txtNumOrdCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumOrdComKeyPressed(evt);
            }
        });
        panGenNor.add(txtNumOrdCom);
        txtNumOrdCom.setBounds(112, 64, 92, 20);

        lblNumPed.setText("No. Pedido:");
        panGenNor.add(lblNumPed);
        lblNumPed.setBounds(214, 64, 66, 20);

        txtNumPed.setBackground(objParSis.getColorCamposObligatorios()
        );
        txtNumPed.setMaximumSize(null);
        txtNumPed.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenNor.add(txtNumPed);
        txtNumPed.setBounds(280, 64, 124, 20);

        lblPrv.setText("Proveedor:");
        panGenNor.add(lblPrv);
        lblPrv.setBounds(6, 84, 72, 20);

        txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
        txtCodPrv.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodPrv.setPreferredSize(new java.awt.Dimension(25, 20));
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
        panGenNor.add(txtCodPrv);
        txtCodPrv.setBounds(112, 84, 63, 20);

        txtNomPrv.setBackground(objParSis.getColorCamposObligatorios());
        txtNomPrv.setPreferredSize(new java.awt.Dimension(100, 20));
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
        panGenNor.add(txtNomPrv);
        txtNomPrv.setBounds(176, 84, 264, 20);

        butPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butPrv.setText("...");
        butPrv.setPreferredSize(new java.awt.Dimension(20, 20));
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenNor.add(butPrv);
        butPrv.setBounds(440, 84, 22, 20);

        lblDirPrv.setText("Dirección:");
        panGenNor.add(lblDirPrv);
        lblDirPrv.setBounds(6, 104, 60, 20);

        txtDirPrv.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDirPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirPrvActionPerformed(evt);
            }
        });
        panGenNor.add(txtDirPrv);
        txtDirPrv.setBounds(112, 104, 350, 20);

        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenNor.add(lblFecDoc);
        lblFecDoc.setBounds(408, 4, 108, 20);

        lblCom.setText("Comprador:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenNor.add(lblCom);
        lblCom.setBounds(408, 24, 70, 20);

        txtCodCom.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCom.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodCom.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodComFocusLost(evt);
            }
        });
        txtCodCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodComActionPerformed(evt);
            }
        });
        panGenNor.add(txtCodCom);
        txtCodCom.setBounds(484, 24, 35, 20);

        txtNomCom.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomComFocusLost(evt);
            }
        });
        txtNomCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomComActionPerformed(evt);
            }
        });
        panGenNor.add(txtNomCom);
        txtNomCom.setBounds(520, 24, 144, 20);

        butCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCom.setText("...");
        butCom.setPreferredSize(new java.awt.Dimension(20, 20));
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panGenNor.add(butCom);
        butCom.setBounds(664, 24, 20, 20);

        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenNor.add(lblAte);
        lblAte.setBounds(408, 44, 110, 20);
        panGenNor.add(txtAte);
        txtAte.setBounds(520, 44, 164, 20);

        lblNumFacPrv.setText("No. Fac. Proveedor:");
        lblNumFacPrv.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenNor.add(lblNumFacPrv);
        lblNumFacPrv.setBounds(408, 64, 110, 20);

        txtNumFacPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumFacPrvFocusLost(evt);
            }
        });
        panGenNor.add(txtNumFacPrv);
        txtNumFacPrv.setBounds(520, 64, 164, 20);

        chkEmiChq.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        chkEmiChq.setText("Emitir cheque antes de recibir la factura del proveedor");
        panGenNor.add(chkEmiChq);
        chkEmiChq.setBounds(5, 126, 500, 20);

        panGen.add(panGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "...", "Descripción", "Unidad", "Bodega", "...", "Cantidad", "Costo", "%Desc", "Iva", "Total", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.setCellSelectionEnabled(true);
        spnDat.setViewportView(tblDat);

        panGen.add(spnDat, java.awt.BorderLayout.CENTER);

        panGenSur.setLayout(new java.awt.BorderLayout());

        panGenSurObs.setLayout(new java.awt.BorderLayout());

        panObs.setLayout(new java.awt.GridLayout(2, 1));

        panObs1.setLayout(new java.awt.BorderLayout());

        lblObs1.setText("Observación 1:");
        panObs1.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panObs1.add(spnObs1, java.awt.BorderLayout.CENTER);

        panObs.add(panObs1);

        panObs2.setPreferredSize(new java.awt.Dimension(250, 25));
        panObs2.setLayout(new java.awt.BorderLayout());

        lblObs2.setText("Observación 2:");
        panObs2.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panObs2.add(spnObs2, java.awt.BorderLayout.CENTER);

        panObs.add(panObs2);

        panGenSurObs.add(panObs, java.awt.BorderLayout.CENTER);

        panGenSur.add(panGenSurObs, java.awt.BorderLayout.CENTER);

        panGenSurTot.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panGenSurTot.add(lblSubTot);

        txtSub.setEditable(false);
        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubActionPerformed(evt);
            }
        });
        panGenSurTot.add(txtSub);

        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenSurTot.add(lblIva);

        txtIva.setEditable(false);
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenSurTot.add(txtIva);

        lblTot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenSurTot.add(lblTot);

        txtTot.setEditable(false);
        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenSurTot.add(txtTot);

        panGenSur.add(panGenSurTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panForPag.setLayout(new java.awt.BorderLayout());

        panForPagNor.setPreferredSize(new java.awt.Dimension(249, 40));

        lblForPag.setText("Forma de Pago:");
        lblForPag.setPreferredSize(new java.awt.Dimension(100, 25));
        panForPagNor.add(lblForPag);

        cboForPag.setPreferredSize(new java.awt.Dimension(200, 25));
        cboForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboForPagActionPerformed(evt);
            }
        });
        panForPagNor.add(cboForPag);

        panForPag.add(panForPagNor, java.awt.BorderLayout.NORTH);

        tblForPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "% Retención", "Monto de Pago", "Días de gracia", "Cód.Ret.", "Cód.Sri.", "Apl.Ret.", "Bas.Imp."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblForPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblForPag);

        panForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Forma de Pago", panForPag);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumOrdComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumOrdComKeyPressed
        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultar();
            objTooBar.setEstado('w');
        }
    }//GEN-LAST:event_txtNumOrdComKeyPressed

    private void txtCodComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusGained
        strCodCom=txtCodCom.getText();
        txtCodCom.selectAll();
    }//GEN-LAST:event_txtCodComFocusGained

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        strDesLarCom=txtNomCom.getText();
        txtNomCom.selectAll();
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
        if (!txtNomCom.getText().equalsIgnoreCase(strDesLarCom))
        {
            if (txtNomCom.getText().equals(""))   {
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else {
               buscarComprador("a.tx_nom",txtNomCom.getText(),1);
            }
        }
        else
            txtNomCom.setText(strDesLarCom); 
       
    }//GEN-LAST:event_txtNomComFocusLost

    private void txtNomPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusGained
        strDesLarPrv=txtNomPrv.getText();
        txtNomPrv.selectAll();
    }//GEN-LAST:event_txtNomPrvFocusGained

    private void txtNomPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusLost
        if (!txtNomPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtNomPrv.getText().equals(""))  {
                txtCodPrv.setText("");
                txtNomPrv.setText("");
            }
            else   {
               buscarProveedor("a.tx_nom", txtNomPrv.getText(),1);
            }
        }
        else
            txtNomPrv.setText(strDesLarPrv);
       
    }//GEN-LAST:event_txtNomPrvFocusLost

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void cboForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboForPagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboForPagActionPerformed

    private void txtDirPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirPrvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirPrvActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarTblDat();
        configurarVentanaConsulta();
        if(intValBus==1){
            txtNumDoc.setText(""+ intCodCot);
            consultar2(intCodEmp ,intCodLoc);
            objTooBar.setEstado('w');      
        }
        if(intValBus==2){
            objParSis.setCodigoMenu( intCodMnu );
            txtCodTipDoc.setText(""+intCodTipDocOc);
            txtNumDoc.setText(""+intCodDocOc);
            consultar2(intCodEmp ,intCodLoc);
            objTooBar.setEstado('w');
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
            cerrarObj();
            System.gc(); 
            dispose(); 
        }    
    }//GEN-LAST:event_CerrarVentana

    private void txtNumFacPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFacPrvFocusLost
  
    }//GEN-LAST:event_txtNumFacPrvFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
       listaTipdoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtNumOrdComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumOrdComFocusLost
        if (txtCodTipDoc.getText().equals(""))
        {
            txtNumOrdCom.setText("");
            txtCodTipDoc.requestFocus();
        }
    }//GEN-LAST:event_txtNumOrdComFocusLost

    private void txtNumOrdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumOrdComActionPerformed
        if (txtCodTipDoc.getText().equals(""))
        {
            txtNumOrdCom.setText("");
            txtCodTipDoc.requestFocus();
        }
    }//GEN-LAST:event_txtNumOrdComActionPerformed

    private void txtNumCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCotActionPerformed
        if (!txtNumCot.getText().equals(""))
            cargarDatCot();
    }//GEN-LAST:event_txtNumCotActionPerformed

    
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost

    }//GEN-LAST:event_txtDesCorTipDocFocusLost
    
    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        listaTipdoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed
 
    private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
        vcoTipDoc.setTitle("Listado Tipos de Documentos"); 
        switch (intTipo){
            case 0:  
                break;
            case 1:
                vcoTipDoc.setCampoBusqueda(1);
                if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }
                break;
            case 2:
                vcoTipDoc.setCampoBusqueda(2); 
                if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }                
                break;
        }
        vcoTipDoc.cargarDatos();
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            if(!vcoTipDoc.getValueAt(1).equals("")){
                cargarCabTipDoc(Integer.parseInt(vcoTipDoc.getValueAt(1)));            
                if (objTooBar.getEstado()=='n') cargaNum_Doc_OrdCom();
                // generaAsiento();
            }
        }      
    }
     
    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtCodPrv.getText().equals(""))   {
                txtCodPrv.setText("");
                txtNomPrv.setText("");
            }
            else  {
               buscarProveedor("a.co_cli",txtCodPrv.getText(),0);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
          
    }//GEN-LAST:event_txtCodPrvFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        vcoPrv.setTitle("Listado de Clientes");         
        vcoPrv.setCampoBusqueda(1);
        vcoPrv.show();
        if (vcoPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
            stIvaCom =  vcoPrv.getValueAt(8);   
            cargarCompradorProveedor();
            calcularPagos();
        } 
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtNomPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvActionPerformed
        txtNomPrv.transferFocus();
    }//GEN-LAST:event_txtNomPrvActionPerformed

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void buscarProveedor(String campo,String strBusqueda,int tipo){
        vcoPrv.setTitle("Listado de Clientes"); 
        if (vcoPrv.buscar(campo, strBusqueda ))
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
            stIvaCom =  vcoPrv.getValueAt(8);
            cargarCompradorProveedor();
            calcularPagos();
        }
        else
        {    
            vcoPrv.setCampoBusqueda(tipo);
            vcoPrv.cargarDatos();
            vcoPrv.show();
            if (vcoPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodPrv.setText(vcoPrv.getValueAt(1));
                txtNomPrv.setText(vcoPrv.getValueAt(2));
                txtDirPrv.setText(vcoPrv.getValueAt(3));
                stIvaCom =  vcoPrv.getValueAt(8);
                calcularPagos();
                if (txtCodCom.getText().equals(""))
                    cargarCompradorProveedor();
            }
            else{
                txtCodPrv.setText(strCodPrv);
                txtNomPrv.setText(strDesLarPrv);
            }
        }
         
        if(!txtCodPrv.getText().equals(""))
            objCliente_dat.setCliente(Integer.parseInt( txtCodPrv.getText()  ));
        else {
            if (objCliente_dat==null) objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);       
        }
    }

    private void txtCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusLost
         if (!txtCodCom.getText().equalsIgnoreCase(strCodCom))
         {
            if (txtCodCom.getText().equals(""))
            {
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else
            {
               buscarComprador("a.co_usr",txtCodCom.getText(),0);
            }
        }
        else
            txtCodCom.setText(strCodCom);
        
    }//GEN-LAST:event_txtCodComFocusLost

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
       txtNomCom.transferFocus();
     
    }//GEN-LAST:event_txtNomComActionPerformed

    private void txtCodComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodComActionPerformed
       txtCodCom.transferFocus();
       
    }//GEN-LAST:event_txtCodComActionPerformed

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        buscarComprador("a.co_usr","",0);
    }//GEN-LAST:event_butComActionPerformed

    private void buscarComprador(String campo,String strBusqueda,int tipo){
        vcoCom.setTitle("Listado de Compradores"); 
        if (vcoCom.buscar(campo, strBusqueda ))
        {
            txtCodCom.setText(vcoCom.getValueAt(1));
            txtNomCom.setText(vcoCom.getValueAt(2));
        }
        else
        {     
            vcoCom.setCampoBusqueda(tipo);
            vcoCom.cargarDatos();
            vcoCom.show();
            if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodCom.setText(vcoCom.getValueAt(1));
                txtNomCom.setText(vcoCom.getValueAt(2));
            }
            else{
                txtCodCom.setText(strCodCom);
                txtNomCom.setText(strDesLarCom);
            }
        }
    }
  
    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCom;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JCheckBox chkEmiChq;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDirPrv;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNumCot;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumFacPrv;
    private javax.swing.JLabel lblNumOrdCom;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panForPag;
    private javax.swing.JPanel panForPagNor;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenNor;
    private javax.swing.JPanel panGenSur;
    private javax.swing.JPanel panGenSurObs;
    private javax.swing.JPanel panGenSurTot;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObs1;
    private javax.swing.JPanel panObs2;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblForPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCodCom;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDirPrv;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomCom;
    private javax.swing.JTextField txtNomPrv;
    private javax.swing.JTextField txtNumCot;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumFacPrv;
    private javax.swing.JTextField txtNumOrdCom;
    private javax.swing.JTextField txtNumPed;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
    
    private void mostrarMsgOK(String strMsg) {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.OK_OPTION);
    }    
    
    private void mostrarMsgInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void mostrarMsgValCam(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    
    //*****************************************************************************************************************************//
    private boolean abrirConRem(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=DriverManager.getConnection(objParSis.getStringConexion(intIndEmp), objParSis.getUsuarioBaseDatos(intIndEmp), objParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (java.sql.SQLException e) {
            mostrarMsgOK("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            return false;
        }
        return blnres;
    }
    
    private void abrirCnx(){
        try{
            CONN_GLO=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());          
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
      
    private void cerrarCnx(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;  
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    private void cerrarObj(){
        try{
            vcoItm.dispose(); 
            vcoItm=null; 
            vcoCom.dispose();
            vcoCom=null;  
            vcoPrv.dispose();
            vcoPrv=null;

            objUti=null;
            objCiudad_dat=null;
            objTooBar=null;
            objDocLis=null;
            objParSis=null;
            dtpFecDoc=null;

            objTblCelEdiTxtVcoItm=null;
            objUltDocPrint=null;
        }
        catch (Exception e)  { objUti.mostrarMsgErr_F1(this, e);  }
    }        
    
    //*****************************************************************************************************************************//

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
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        @Override
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
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
      
    /* Obtiene el Iva que se debe cobrar en la empresa actual */
    private void getIva(){
        if (objTipDoc.getst_iva().equals("S")){
            dblPorIva = objCtaCtb.getPorcentajeIvaCompras();
        }else{
            dblPorIva = 0;                        
        }
        lblIva.setText("IVA " + dblPorIva + "%");
    }

    private void limpiarFrm()
    {
        if(blnLimpiar)
        {
            //Cabecera 
            strCodPrv="";
            strDesLarPrv="";   
            strCodCom="";
            strDesLarCom="";
     
            txtNumCot.setText("");
            txtNumDoc.setText("");
            txtAte.setText("");
            txtNumFacPrv.setText("");
            txtCodPrv.setText("");
            txtNomPrv.setText("");
            txtDirPrv.setText("");
            dtpFecDoc.setText("");
            txtCodCom.setText("");
            txtNomCom.setText("");
            txtNumOrdCom.setText("");
            txtNumPed.setText("");
            
            txtCod.setText("");
            txtDes.setText("");
            txtVehRet.setText("");
            txtChoRet.setText("");
            
            //Detalle
            objTblMod.removeAllRows();       

            //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            txtTot.setText("0");
            lblTit.setText("Orden de Compra"); 

            //Forma de Pago
            while(tblForPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).removeRow(0);
            
            //Asiento de Diario
            objAsiDia.inicializar();
        }
    }
   
    private void calculaSubtotal()
    {
        try{
            double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
            int intNumFil=tblDat.getSelectedRow();

            if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV)==null)
                tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_CAN_MOV);
            if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS)==null)
                tblDat.setValueAt("0",intNumFil, INT_TBL_DAT_PRE_COS);
            if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null)
                tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_POR_DES);

            dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV).toString());
            dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS).toString());

            dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES).toString()))), objParSis.getDecimalesBaseDatos()  );

            dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));

            dblTotal = (dblCan * dblCosto) - dblValDes;
            dblTotal = objUti.redondear(dblTotal, 3);
            dblTotal = objUti.redondear(dblTotal, objParSis.getDecimalesMostrar());

            tblDat.setValueAt(""+dblTotal, intNumFil ,INT_TBL_DAT_VAL_TOT);

            calcularTotal();
            calcularPagos();
            generaAsiento();
        }
        catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    private void calculaDetalle()
    {
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
        double dblSub = 0, dblIva = 0, dblDes2 = 0, dblTmp=0, dblSub2=0;
        int intNumFil=0;  
        for (intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++)
        {
            if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_COD_ITM) != null ){  
                if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_CAN_MOV);
                if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS)==null) tblDat.setValueAt("0",intNumFil, INT_TBL_DAT_PRE_COS);
                if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_POR_DES);

                dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV).toString());
                dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS).toString());
                dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES).toString()))),2);
                dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
                   
                dblTotal = (dblCan * dblCosto) - dblValDes ;
                dblTotal = objUti.redondear(dblTotal, 3);
                dblTotal = objUti.redondear(dblTotal, objParSis.getDecimalesMostrar());

                tblDat.setValueAt(dblTotal+"", intNumFil ,INT_TBL_DAT_VAL_TOT);
        
                dblSub2 = objUti.redondear(((tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString())),objParSis.getDecimalesMostrar());  
                dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                
                if(tblDat.getValueAt(intNumFil, INT_TBL_DAT_CHK_IVA)==null)
                      dblIva = dblIva + 0;
                else{
                   dblTmp = ((tblDat.getValueAt(intNumFil, INT_TBL_DAT_CHK_IVA).toString().equals("true"))? ((tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString())) : 0 );
                   dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
               }
            }
        }
        
        //************************************************///
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva,intNumDec);
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,intNumDec);
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,intNumDec);
        
        if(stIvaCom.equals("N")){
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "0.00" );
            txtTot.setText( ""+ dblSubtotalCom );
            dblTotalCom=dblSubtotalCom;
            dblIvaCom=0;
        }
        else{
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "" + dblIvaCom );
            txtTot.setText( ""+ dblTotalCom);
        }
        
        //************************************************///
        calcularPagos();
        if(objTooBar.getEstado()!='m'){  /* JoséMario 30/Nov/2015 */  
            generaAsiento();    
        }
    }

    private void calcularTotal(){
        double dblSub = 0, dblIva = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){ 
            dblSub2 = objUti.redondear(((tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString())),objParSis.getDecimalesBaseDatos() );  
            dblSub = dblSub + objUti.redondear(dblSub2,objParSis.getDecimalesBaseDatos());
            
           
             if(tblDat.getValueAt(i, INT_TBL_DAT_CHK_IVA)==null)
                dblIva = dblIva + 0;
             else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_DAT_CHK_IVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString())) : 0 );
                //Valor de iva no se redondeaba//
                dblIva = dblIva + objUti.redondear((((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100),objParSis.getDecimalesBaseDatos()) ;
            }
        }
              
        //************************************************///
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva, objParSis.getDecimalesMostrar() );
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,objParSis.getDecimalesMostrar());
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,objParSis.getDecimalesMostrar());
       
        if(stIvaCom.equals("N")){
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "0.00" );
            txtTot.setText( ""+ dblSubtotalCom );
            dblTotalCom=dblSubtotalCom;
            dblIvaCom=0;
        }
        else{
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "" + dblIvaCom );
            txtTot.setText( ""+ dblTotalCom);
        }
    }
    
    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
    double dblRetFteGlo = 0.0, dblRetIvaGlo = 0;
    private void calcularPagos()
    {
        int intVal =  intCanArr[0];
        int intsizearre = intarreglodia.length;
        intVal= intsizearre - (intsizearre-intVal);
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            dblRetFteGlo = 0.0;
            dblRetIvaGlo = 0.00;
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            int fecCot [] = dtpFecDoc.getFecha(dtpFecDoc.getText());

            if(fecCot!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecCot[0]);
                objFec.set(java.util.Calendar.MONTH, fecCot[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecCot[2]);
            }

            int intFilUltPag = 0;
            double dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)
            double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText().equals("")?"0":txtTot.getText())),objParSis.getDecimalesBaseDatos());

            while(tblForPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).removeRow(0);

            if(cboForPag.getSelectedIndex()!=-1)
            {
                Librerias.ZafDate.ZafDatePicker dtpForPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
                double dblPagos=0.00;
                double dblPago=0.00;
                int i=0;

                Connection  conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conn!=null)
                {
                    /******************************************************************************************************************************/
                    calculaSubtotalServiNoServi("N");
                    if(dblSubSerNoSer > 0.00 ){
                        cargaForPag(conn, intCodMotBien );
                    }                   
                    /******************************************************************************************************************************/
                    calculaSubtotalServiNoServi("S");
                    if(dblSubSerNoSer > 0.00 ){
                        cargaForPag(conn, intCodMotServ );
                    }
                    /******************************************************************************************************************************/
                    calculaSubtotalServiNoServi("T");
                    if(dblSubSerNoSer > 0.00 ){
                        cargaForPag(conn, intCodMotTran );
                    }
                    /******************************************************************************************************************************/
                    conn.close();
                    conn=null;
                }

                double dblRete = 0;
                dblRete = dblRetFteGlo+dblRetIvaGlo;
                dblMonCal = objUti.redondear( ( dblTotalDoc - dblRete ),intNumDec);

                for(i=0; i < intVal; i++)
                {
                    diaCreAnt = diaCre;
                    diaCre = intarreglodia[i];
                    numPag = intarreglonum[i];
                    diaCreAcum = diaCre - diaCreAnt;
                    if (diaCre!=0)
                        objFec.add(java.util.Calendar.DATE, diaCreAcum);

                    dtpForPag.setAnio( objFec.get(java.util.Calendar.YEAR));
                    dtpForPag.setMes( objFec.get(java.util.Calendar.MONTH)+1);
                    dtpForPag.setDia(objFec.get(java.util.Calendar.DAY_OF_MONTH));
                    double dblValPag =0;
                    dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
                    dblPago += dblPagos;
                    dblPagos = objUti.redondear(dblPagos ,intNumDec);
                    dblValPag = dblPagos;

                    ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());
                        intFilUltPag = tblForPag.getRowCount()-1;
                        tblForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                        tblForPag.setValueAt(dtpForPag.getText(), intFilUltPag, 2);
                        tblForPag.setValueAt(new Double(dblValPag), intFilUltPag, 4);
                        tblForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
                }

                double dblultpag =  objUti.redondear(Double.parseDouble(tblForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
                dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);

                tblForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);
             }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    }
    
    double dblSubSerNoSer=0, dblIvaSerNoSer=0;
    private void calculaSubtotalServiNoServi(String strServi){
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
        double dblSub = 0, dblIva = 0,  dblTmp=0, dblSub2=0;
        int intNumFil=0;
        dblSubSerNoSer=0;
        dblIvaSerNoSer=0;
        try{
            for (intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++){
                if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_COD_ITM) != null ){
                    if(tblDat.getValueAt(intNumFil,INT_TBL_DAT_ITM_SER).toString().equals(strServi)){
                        if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_CAN_MOV);
                        if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS)==null) tblDat.setValueAt("0",intNumFil, INT_TBL_DAT_PRE_COS);
                        if (tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_DAT_POR_DES);

                        dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV).toString());
                        dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_DAT_PRE_COS).toString());
                        dblDes =  Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_DAT_POR_DES).toString())) );
                        dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));

                        dblTotal = (dblCan * dblCosto) - dblValDes ;
                        dblTotal = objUti.redondear(dblTotal, 3);
                        dblTotal = objUti.redondear(dblTotal, objParSis.getDecimalesMostrar());           

                        tblDat.setValueAt(""+dblTotal, intNumFil ,INT_TBL_DAT_VAL_TOT);

                        dblSub2 = objUti.redondear(((tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString())),objParSis.getDecimalesMostrar());
                        dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                        
                        if(tblDat.getValueAt(intNumFil, INT_TBL_DAT_CHK_IVA)==null)
                            dblIva = dblIva + 0;
                        else{
                           dblTmp = ((tblDat.getValueAt(intNumFil, INT_TBL_DAT_CHK_IVA).toString().equals("true"))? ((tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT)==null||tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_DAT_VAL_TOT).toString())) : 0 );
                           dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                       }
                    }
                }
            }
            if(stIvaCom.equals("N")) dblIvaSerNoSer = 0.00;
            else dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);

            dblSubSerNoSer = objUti.redondear(dblSub ,intNumDec);
        }
        catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */
    private void generaAsiento()
    {
        objAsiDia.inicializar();
        double dblSubtotal=0, dblTotSer=0;
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null

        int intEst=0;
         
        //********************************************************
        for(int i=0; i<tblDat.getRowCount(); i++){
            if(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM) != null ){
                if(!(tblDat.getValueAt(i,INT_TBL_DAT_ITM_SER).toString().trim().equalsIgnoreCase("N"))) {
                    dblTotSer += objUti.redondear(tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT)==null?"0":tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT).toString(),objParSis.getDecimalesMostrar());
                    intEst=1;
                }
            }
        }
      
        //********************************************************
        if (vecDetDia==null) 
            vecDetDia = new java.util.Vector();
        else 
            vecDetDia.removeAllElements();
          
        java.util.Vector vecReg; 
         
        int intCodBodT,intCodBod;
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
            for (int j=0;j<arreBod.size();j++){
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM)!=null && tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_DAT_ITM_SER).toString().trim().equalsIgnoreCase("N")) {
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT).toString())>0)
                            {
                                intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD)==null?"0":tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD).toString()));
                                if(intCodBod==intCodBodT)
                                    dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT)==null?"0":tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT).toString(),objParSis.getDecimalesMostrar());
                            }
                        }
                    }
                }
            
                if(dblSubtotal > 0 ){
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                    vecReg.add(INT_VEC_BOTON, null);                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                    vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(dblSubtotal));  
                    vecReg.add(INT_VEC_HABER, new Double(0));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDia.add(vecReg);
                }
            }   
          
            if(stIvaCom.equals("S")){
                if (objTipDoc.getst_iva().equals("S"))
                {
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                    vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf((objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar()))));
                    vecReg.add(INT_VEC_HABER, new Double(0)); 
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDia.add(vecReg);                       
                }
            }   
            
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf((objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()))));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDia.add(vecReg);  
        }
        else{
            for (int j=0;j<arreBod.size();j++){
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM)!=null && tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_DAT_ITM_SER).toString().trim().equalsIgnoreCase("N")) { 
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT).toString())>0)
                            {
                                intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD)==null?"0":tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD).toString()));
                                if(intCodBod==intCodBodT)
                                    dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT)==null?"0":tblDat.getValueAt(i,INT_TBL_DAT_VAL_TOT).toString(),objParSis.getDecimalesMostrar());
                            }
                        }
                    }
                }
                
                if(dblSubtotal > 0 ){
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                    vecReg.add(INT_VEC_BOTON, null);                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                    vecReg.add(INT_VEC_DEBE, new Double(0));  
                    vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf(dblSubtotal));
                    vecReg.add(INT_VEC_REF, null);
                     vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDia.add(vecReg);
                }
            }  
              
            if(stIvaCom.equals("S")){   
                if (objTipDoc.getst_iva().equals("S"))
                {
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                    vecReg.add(INT_VEC_DEBE, new Double(0)); 
                    vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));   
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDia.add(vecReg);                       
                }       
            }
              
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDia.add(vecReg);  
        }
         
        if(intEst==1){
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaSerCompras()));
            vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaSerCom());   
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaSerCom());                 
            vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(dblTotSer));
            vecReg.add(INT_VEC_HABER, new Double(0)); 
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDia.add(vecReg);      
        }
        objAsiDia.setDetalleDiario(vecDetDia);         
    }
        
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
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
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }   
             
    private boolean isCamVal()
    {
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Codigo de Tipo de Documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
               
        if(txtNumOrdCom.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("No. Orden de Compra");
            txtNumOrdCom.requestFocus();
            return false;
        }
               
        if(objTooBar.getEstado()=='n'){
            if(txtNumPed.getText().equals("") ){
                tabFrm.setSelectedIndex(0);                  
                mostrarMsgValCam("No. de Pedido");
                txtNumPed.requestFocus();
                return false;
            }
        }

        if(txtCodPrv.getText().equals("") )
        {
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Proveedor");
            txtCodPrv.requestFocus();
            return false;
        }
        
        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de Compra");
            dtpFecDoc.requestFocus();
            return false;
        }
        if( txtCodCom.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Vendedor");
            txtCodCom.requestFocus();
            return false;
        }
        
        if (!objTblMod.isAllRowsComplete()){
            mostrarMsgInf("Existen datos incompletos. \nRevise filas sombreadas");
            return false;                                    
        }
 
        /* Validar Datos del pago */
        if(cboForPag.getSelectedIndex() ==-1 ){
            tabFrm.setSelectedIndex(1);
            mostrarMsgValCam("Forma de pago");
            cboForPag.requestFocus();
            return false;
        }
               
        //*********************************************************************************************
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++)
        {
            if(tblDat.getValueAt(intRowVal,INT_TBL_DAT_COD_ITM) != null )
            {
                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_DAT_CAN_MOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_DAT_CAN_MOV).toString());
                if(dblCan <= 0.00 )
                {
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_DAT_CAN_MOV);
                    return false; 
                }
                    
                double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_DAT_PRE_COS).toString());
                if(dblCos <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_DAT_PRE_COS);
                    return false; 
                }
            }
        }
        
        //*****************************************************************************************************
        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("Asiento descuadrado.");
            tabFrm.setSelectedIndex(2);
            return false;                                    
        }
                
        /* Valida que la suma de los montos de pagos sean igual a total de cotización. */
        double dblTotalMonto=0;
        double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText())),6);
        for(int rowIdx = 0; rowIdx<tblForPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblForPag.getValueAt(rowIdx, 4)==null)?"0":tblForPag.getValueAt(rowIdx, 4).toString());
            dblTotalMonto = dblTotalMonto + dblSubMonto ; 
        }
                
        if( objUti.redondear(dblTotalMonto, intNumDec)!= objUti.redondear(dblTotalDoc, intNumDec) && objTooBar.getEstado()=='n'){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("La suma de los montos a pagar es mayor al total de la Cotización.\nCorrija y vuelva a intentarlo.");
            return false;
        }
        
        if (!objAsiDia.isDocumentoCuadrado(dblTotalDoc)){
             mostrarMsgInf("Asiento y Monto Total Descuadrado.");
             tabFrm.setSelectedIndex(2);
             return false;                                                       
        }
               
        //********************************************************************
        if( txtCod.getText().equals("") ){
            mostrarMsgValCam("Forma de Retiro");
            txtCod.requestFocus();
            tabFrm.setSelectedIndex(3);
            return false;
        }
        return true; 
    }
    
    //***********************************************************************************************************************//
    public class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objParSis);
        }
 
            @Override
        public boolean beforeAceptar() {
            return true;
        }        
            @Override
        public boolean beforeAnular() {
            return true;
        }        
            @Override
        public boolean beforeCancelar() {
            return true;
        }        
            @Override
        public boolean beforeConsultar() {
            return true;
        }        
            @Override
        public boolean beforeEliminar() {
            return true;
        }        
            @Override
        public boolean beforeImprimir() {
            return true;
        }        
            @Override
        public boolean beforeInsertar() {
            return true;
        }        
            @Override
        public boolean beforeModificar() {
            return true;
        }        
            @Override
        public boolean beforeVistaPreliminar() {
            return true;
        }        
            @Override
        public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if(!verificaItmUni())
                return false;
            
            if(!objUltDocPrint.Verificar_Doc_Asociado(txtNumDoc.getText(),txtCodTipDoc.getText()))
                      return false;
          
            if(!objUltDocPrint.verificarsiesconfirmado(txtNumDoc.getText(),txtCodTipDoc.getText()))
                      return false;
            
            if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText())))
                       return false;
            
            if (!anularReg())
                return false;
            
            recostearItmGrp();
            
            objTooBar.setEstadoRegistro("Anulado");
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
        
        @Override
        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
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

        @Override
        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
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

        @Override
        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
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
        
        @Override
        public void clickInsertar()
        {
            try
            { 
                if (objTblMod.isDataModelChanged())
                {
                    isRegPro();
                }
                 
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }

                limpiarFrm();
                  
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj =datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                dtpFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                     calObj.get(java.util.Calendar.MONTH)+1     ,
                                     calObj.get(java.util.Calendar.YEAR)        );

                /*JoseMario 6/Jun/2016*/
                java.sql.Connection conLoc;
                conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    cargarTipEmp(conLoc);
                }
                conLoc.close();
                conLoc=null;
                /*JoseMario 6/Jun/2016*/

                txtNumCot.setEnabled(true);
                txtSub.setEditable(false); //***
                txtIva.setEditable(false);  //***
                txtTot.setEditable(false);  //***
            
                cboForPag.setSelectedIndex(0);
                if (!txtCodTipDoc.getText().equals("")){
                    cargaNum_Doc_OrdCom();                        
                    txtNumOrdCom.requestFocus();
                    txtNumOrdCom.setSelectionStart(0);
                    txtNumOrdCom.setSelectionEnd(txtNumOrdCom.getText().length());
                }
                    
                cargaListadoFormaPago();
                objAsiDia.setEditable(true);
                setEditable(true);
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)      {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        @Override
        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
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

        @Override
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }
        
        @Override
        public boolean eliminar()
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                 if(!verificaItmUni())
                     return false;
                
                 if(!objUltDocPrint.Verificar_Doc_Asociado(txtNumDoc.getText(),txtCodTipDoc.getText()))
                      return false;
          
                 if(!objUltDocPrint.verificarsiesconfirmado(txtNumDoc.getText(),txtCodTipDoc.getText()))
                      return false;
                
                 if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText())))
                       return false;
           
                if (!eliminarReg())
                 return false;
          
                recostearItmGrp();
                
                
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                objTblMod.setDataModelChanged(false);    
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        
        @Override
        public boolean insertar()
        {
            boolean blnRes = false;
            if (!isCamVal()) 
                return false;
            
            if(insertarReg())
            {
               blnRes=true;
               recostearItmGrp();
               objTblMod.setDataModelChanged(false);
               blnHayCam = false;
            }
            return blnRes;
        }

        @Override
        public boolean modificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }            
         
            if(!verificaItmUni())
                return false;
            
            if(!objUltDocPrint.Verificar_Doc_Asociado(txtNumDoc.getText(),txtCodTipDoc.getText()))
                  return false;
            
            if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText()))){
                      return false;
            } 
            
            if(!verificaNumPag()){
                mostrarMsgInf("No es posible modificar.\nEl Número de pagos no es igual al original.");
                return false;
            }

            if(!verificaRecFacPrv()){
                mostrarMsgInf("No es posible modificar.\n Tiene asociado a factura del proveedor. ");
                return false;
            }

            if (!isCamVal())
                return false;
            
            if (!actualizarReg())
                return false;
            
            recostearItmGrp();
            
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
            
        @Override
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (objTblMod.isDataModelChanged())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return blnRes;
        }
                    
            @Override
        public boolean aceptar() {
            return true;
        }
        
            @Override
        public boolean afterAceptar() {
            return true;
        }
        
            @Override
        public boolean afterAnular() {
            return true;
        }
        
            @Override
        public boolean afterCancelar() {
            return true;
        }
        
            @Override
        public boolean afterConsultar() {
          
            return true;
        }
           
            @Override
        public boolean afterEliminar() {
            return true;
        }
        
            @Override
        public boolean afterImprimir() {
            return true;
        }
        
            @Override
        public boolean afterInsertar() {
            this.setEstado('w');
            cargarRegistroInsert();
             objTblMod.setDataModelChanged(false);
            return true;
        }
        
            @Override
        public boolean afterModificar() {
            return true;
        }
        
            @Override
        public boolean afterVistaPreliminar() {
            return true;
        }
                      
        @Override
        public boolean imprimir(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        @Override
        public boolean vistaPreliminar(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
      
        
        @Override
        public void clickConsultar(){
            txtNumCot.setEnabled(false);
            txtSub.setEditable(false); //***
            txtIva.setEditable(false);  //***
            txtTot.setEditable(false);  //***
            dtpFecDoc.setText("");
            objAsiDia.setEditable(false);
            //setEditable(false);
        }

        @Override
        public void clickModificar(){
            txtSub.setEditable(false); //***
            txtIva.setEditable(false);  //***
            txtTot.setEditable(false);  //***
                    
            objAsiDia.setEditable(true);  
           
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtNumDoc.setEditable(false);
           
            txtNumOrdCom.setEditable(false);
            txtNumPed.setEditable(false);
          
            strCodPrv="";
            strDesLarPrv="";   
            strCodCom="";
            strDesLarCom="";
            
            java.awt.Color color = txtNumCot.getBackground();
            txtNumCot.setEditable(false);
            txtNumCot.setBackground(color);
            
            butTipDoc.setEnabled(false);
            
            butPrv.setEnabled(false);
            color = txtCodPrv.getBackground();
            txtCodPrv.setEditable(false);
            txtCodPrv.setBackground(color); 
            color = txtNomPrv.getBackground();
            txtNomPrv.setEditable(false);
            txtNomPrv.setBackground(color);
            color = txtDirPrv.getBackground();
            txtDirPrv.setEditable(false);
            txtDirPrv.setBackground(color);
                    
            setEditable(true);
            
            dtpFecDoc.setEnabled(false);
           
            objTblMod.setDataModelChanged(true);
            blnHayCam=false;
        }
        
        @Override 
        public void clickEliminar(){
            txtNumCot.setEnabled(false);
            txtSub.setEditable(false); //***
            txtIva.setEditable(false);  //***
            txtTot.setEditable(false);  //***
            objAsiDia.setEditable(false);
        }
                         
        @Override 
        public void clickAnular(){
            txtNumCot.setEnabled(false);
            txtSub.setEditable(false); //***
            txtIva.setEditable(false);  //***
            txtTot.setEditable(false);  //***
            objAsiDia.setEditable(false);
        }
            
        @Override
        public void clickImprimir(){
        }
        @Override
        public void clickVisPreliminar(){
        }
        @Override
        public void clickAceptar(){

        }
        @Override
        public void clickCancelar(){

        }
     
 
    
    } 
    
    //***********************************************************************************************************************//

    private boolean validarTipDoc(){
       if(txtCodTipDoc.getText().equals("")){
           mostrarMsgInf("NO HAY TIPO DE DOCUMENTO PARA LA BUSQUEDA..");
           return false;
       } else
          return true;
    }

    private boolean consultarReg() {
        if(validarTipDoc()){
          return _consultar(FilSql()); 
        }else
           return false;
    }

    private String FilSql() 
    {
        String sqlFiltro = "";

        //Agregando filtro por Codigo Tipo de Documento
       if(!txtCodTipDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";

        //Agregando filtro por Numero de Documento
        if(!txtNumDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_doc = " + txtNumDoc.getText()+ "";

        //Agregando filtro por Numero de Cotizacion
        if(!txtNumOrdCom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.ne_numdoc = " + txtNumOrdCom.getText()+ "";

        //Agregando filtro por Numero de Pedido
        if(!txtNumPed.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.tx_numdoc2 = " + txtNumPed.getText()+ "";                

        //Agregando filtro por Numero de Orden de Compra
        if(!txtNumCot.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.ne_numcot = " + txtNumCot.getText()+ "";

        //Agregando filtro por Fecha
        if(dtpFecDoc.isFecha()){
            int FecSql[] = dtpFecDoc.getFecha(dtpFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and CabMovInv.fe_doc = '" +  strFecSql + "'";
        }    
        //Agregando filtro por Codigo de Cliente
        if(!txtCodPrv.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_cli = " + txtCodPrv.getText() + "";

        //Agregando filtro por Codigo de Vendedor
        if(!txtCodCom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_com = " + txtCodCom.getText() + "";


        sqlFiltro = sqlFiltro   + " and menu.co_mnu = " + intCodMnu  ;
      
        return sqlFiltro ;
    }

    private boolean _consultar(String strFil){
        strFiltro= strFil;
        try{

            conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                strSQL= "SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc "
                        + " FROM tbm_cabMovInv as CabMovInv "
                        + " left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) "
                        + " left outer join tbr_tipdocprg as menu  on (menu.co_emp = doc.co_emp and menu.co_loc = doc.co_loc and menu.co_tipdoc = doc.co_tipdoc) "
                        + " Where CabMovInv.co_emp = " + objParSis.getCodigoEmpresa()  // Consultando en la empresa en la ke se esta trabajando
                        + " and CabMovInv.st_reg not in ('E') "
                        + " and CabMovInv.co_loc = " + objParSis.getCodigoLocal()+" "+
                      " ";

                strSQL= strSQL + strFiltro + " ORDER BY CabMovInv.fe_doc, CabMovInv.ne_numdoc";                                

                rstCab=stmCab.executeQuery(strSQL);

                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros"); 
                    cargarReg();
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    limpiarFrm();
                    return false;
                }

            }

        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false; }
        return true;     
    }  
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {                      
                refrescaDatos();                
            }
            else
            {
                mostrarMsgInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
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
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";

                
                strSQL =" SELECT a.st_emiChqAntRecFacPrv, a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli,  a.co_com , a.tx_nomven as nomcom "+
                        "      , a.co_doc, a.fe_doc, a.tx_ate,  a.co_forPag,  a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva, a.nd_tot, a.st_reg, a.ne_numcot as num_cot"+
                        "      , a.ne_numdoc as num_doc,  a.tx_numDoc2, a.tx_numped as num_ped, a.co_tipdoc"+
                        "      , a.co_forret,b.tx_deslar,a.tx_vehret,a.tx_choret,a.ne_secemp  "+
                        "      , a.st_regrep ,cli.tx_tipper ,cli.co_tipper, cli.st_ivacom, a.nd_porIva /*JoseMario IVA 6/Jun/2016*/"+
                        " FROM tbm_cabMovInv as a LEFT OUTER JOIN tbm_var as b ON (b.co_reg=a.co_forret)"+
                        " INNER JOIN tbm_cli AS cli ON(cli.co_emp=a.co_emp AND cli.co_cli=a.co_cli) " +
                        " WHERE a.st_reg NOT IN('E') "+
                        " AND a.co_emp="+rstCab.getString("co_emp")+" AND a.co_loc="+rstCab.getString("co_loc")+" "+
                        " AND a.co_tipDoc="+ rstCab.getString("co_tipDoc")+" AND a.co_doc="+rstCab.getString("co_doc") +
                        " ORDER BY a.fe_doc,  a.ne_numdoc " ;
                System.out.println("ZafCom02.CabReg.: " + strSQL);
                ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    Glo_intCodSec=rst.getInt("ne_secemp");
                    
                    intNumDoc = rst.getInt("co_doc");
                    txtNumDoc.setText(""+intNumDoc);
                    
                    chkEmiChq.setSelected( (rst.getString("st_emiChqAntRecFacPrv")==null?false:(rst.getString("st_emiChqAntRecFacPrv").equals("S")?true:false) ) );

                    txtCodPrv.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomPrv.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtDirPrv.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    txtNumCot.setText(((rst.getString("num_cot")==null)?"":rst.getString("num_cot")));
                    txtNumOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    
                    txtNumPed.setText(((rst.getString("tx_numdoc2")==null)?"":rst.getString("tx_numdoc2")));                    
                    
                    txtNumFacPrv.setText(((rst.getString("num_ped")==null)?"":rst.getString("num_ped")));
                   
                    STR_ESTREG=rst.getString("st_regrep");
                    
                    txtCod.setText(((rst.getString("co_forret")==null)?"":rst.getString("co_forret")));
                    txtDes.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    txtVehRet.setText(((rst.getString("tx_vehret")==null)?"":rst.getString("tx_vehret")));
                    txtChoRet.setText(((rst.getString("tx_choret")==null)?"":rst.getString("tx_choret")));
                    
                    stIvaCom=rst.getString("st_ivacom");
                    
                    if(rst.getDate("fe_doc")==null){
                      dtpFecDoc.setText("");  
                    }
                    else{
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        dtpFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }
                      
                    txtCodCom.setText(((rst.getString("co_com")==null)?"":rst.getString("co_com")));
                    txtNomCom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                  
                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objParSis.getDecimalesMostrar()));
                   
                    dblPorIva=rst.getDouble("nd_porIva");  /*JoseMario - 6/Jun/2016*/
                    
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                   
                    lblTit.setText("Orden de Compra No. " + txtNumOrdCom.getText() +  " (" + txtNomPrv.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");
                    
                    /* Cargando datos del TAB: Forma de Pago. */
                    String strCo_ForPag = (rst.getString("co_forPag")==null)?"":rst.getString("co_forPag") ;
                    cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));
                   
                    refrescaPagos(intNumDoc);
                     
                    /* Verificando si se encuentra en estado ANULADO*/
                    if(rst.getString("st_reg").equals("I")){
                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                        }
                    }
                    
                    txtSub.setText(""+dblSub);
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else if (strAux.equals("C"))
                        strAux="Pendiente de Confirmacion";
                    else if (strAux.equals("F"))
                        strAux="Diferencia Inventario";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                    dlbValorOriTotOrd=((rst.getString("nd_tot")==null)?0:objUti.redondear(rst.getDouble("nd_tot"),objParSis.getDecimalesMostrar()));
                   
                    System.out.println(" >>> "+ dlbValorOriTotOrd );
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
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
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
         
    private void cargarRegistroInsert(){
        try{
            conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                strSQL= " SELECT co_emp,co_loc,co_tipdoc, co_doc " +
                        " FROM tbm_cabMovInv" +
                        " WHERE co_emp = "  + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                        " AND co_loc = "    + objParSis.getCodigoLocal() +
                        " AND co_tipDoc="   + objTipDoc.getco_tipdoc() +
                        " AND co_doc="      + txtNumDoc.getText() ;
                System.out.println(strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    for (int i=0;i<objTblMod.getRowCountTrue();i++){                                            
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM).toString(), i, INT_TBL_DAT_ITM_ORI);
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_DAT_COD_BOD).toString(), i, INT_TBL_DAT_BOD_ORI);
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_DAT_CAN_MOV).toString(), i, INT_TBL_DAT_CAN_ORI);
                    }                               
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    limpiarFrm();
                }
            }
        }
        catch(SQLException Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
       }                       
    }      

    //**************************************************************************************************  
    private boolean refrescaDatosCot(java.sql.Connection conn,  java.sql.ResultSet rstDatCot)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.Statement stmLocPri;
        java.sql.ResultSet rstLoc;
        java.sql.ResultSet rstLocPri;
        String strSql="";
        int intNumCot=0;
        double dblCan=0, dblPre=0, dblPorDes=0, dblValDes=0, dblTotal=0;
        String strUnidad="",strCodAlt="",strCodAlt2="";
        String strSer="";
        try{
            if(conn!=null){ 
               stmLoc=conn.createStatement();
               stmLocPri=conn.createStatement();
                if(rstDatCot != null){
                    intNumCot = rstDatCot.getInt("co_cot");
                    txtNumCot.setText(""+intNumCot);
                    txtCodPrv.setText(((rstDatCot.getString("co_cli")==null)?"":rstDatCot.getString("co_cli")));
                    txtNomPrv.setText(((rstDatCot.getString("nomcli")==null)?"":rstDatCot.getString("nomcli")));
                    txtDirPrv.setText(((rstDatCot.getString("dircli")==null)?"":rstDatCot.getString("dircli")));
                      
                    if(!txtCodPrv.getText().equals(""))
                       objCliente_dat.setCliente(Integer.parseInt(txtCodPrv.getText()));                    
                    
                    dtpFecDoc.setHoy();
                    txtCodCom.setText(((rstDatCot.getString("co_com")==null)?"":rstDatCot.getString("co_com")));
                    txtNomCom.setText(((rstDatCot.getString("nomcom")==null)?"":rstDatCot.getString("nomcom")));
                    txtAte.setText(((rstDatCot.getString("tx_ate")==null)?"":rstDatCot.getString("tx_ate")));
                    stIvaCom =   rstDatCot.getString("st_ivaCom");  
                    txaObs1.setText(((rstDatCot.getString("tx_obs1")==null)?"":rstDatCot.getString("tx_obs1")));
                    txaObs2.setText(((rstDatCot.getString("tx_obs2")==null)?"":rstDatCot.getString("tx_obs2")));
                    
                   //Detalle        
                    strSql =" SELECT a.tx_nomitm, a.co_bod, a.nd_can, a.nd_cosuni, a.nd_pordes, a.st_ivacom, a.co_itm" +
                            "      , inv.tx_codalt, inv.tx_codalt2, var.tx_descor, inv.st_ser " +
                            "      , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))" +
                            "        IN ( SELECT  UPPER(trim(a1.tx_cad)) FROM tbr_bodloc AS a " +
                            "             INNER JOIN tbm_reginvmernuningegrfisbod AS a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                            "             WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" AND a1.st_reg='A' AND a.st_reg='P'"+
                            "        ))) THEN 'S' ELSE 'N' END AS proconf  "+
                            " FROM tbm_detCotCom AS a " +
                            " INNER JOIN tbm_inv AS inv ON (a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm) " +
                            " LEFT OUTER JOIN tbm_var AS var ON (inv.co_uni = var.co_reg) " +
                            " WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + " " +
                            " AND a.co_loc = " + objParSis.getCodigoLocal()   + " " +
                            " AND a.co_cot = " + intNumCot  +
                            " ORDER BY a.co_reg";
                    rstLoc = stmLoc.executeQuery(strSql);
                    Vector vecDat = new Vector();
                    while(rstLoc.next()){
                        java.util.Vector vecReg = new java.util.Vector();
                        strCodAlt = (rstLoc.getString("tx_codalt")==null?"":rstLoc.getString("tx_codalt"));
                        strCodAlt2= (rstLoc.getString("tx_codalt2")==null?"":rstLoc.getString("tx_codalt2"));
                        strUnidad = (rstLoc.getString("tx_descor")==null?"":rstLoc.getString("tx_descor"));
                        strSer    = (rstLoc.getString("st_ser")==null?"":rstLoc.getString("st_ser"));

                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_ITM_ALT, strCodAlt);
                        vecReg.add(INT_TBL_DAT_BUT_ITM, "");
                        vecReg.add(INT_TBL_DAT_DES_ITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED, strUnidad);
                        vecReg.add(INT_TBL_DAT_COD_BOD, new Integer(rstLoc.getInt("co_bod")));
                        vecReg.add(INT_TBL_DAT_BUT_BOD, "");
                        vecReg.add(INT_TBL_DAT_CAN_ORI, new Double(0));
                        vecReg.add(INT_TBL_DAT_CAN_MOV, new Double(rstLoc.getDouble("nd_can")));                                                                           
                        vecReg.add(INT_TBL_DAT_PRE_COS, new Double(rstLoc.getDouble("nd_cosuni")));
                        vecReg.add(INT_TBL_DAT_POR_DES, new Double(rstLoc.getDouble("nd_pordes")));
                        String strIva = rstLoc.getString("st_ivacom");
                        Boolean blnIva = new Boolean(strIva.equals("S")?true:false);                         
                        vecReg.add(INT_TBL_DAT_CHK_IVA, blnIva);
                        dblCan    = rstLoc.getDouble("nd_can");
                        dblPre    = rstLoc.getDouble("nd_cosuni");
                        dblPorDes = rstLoc.getDouble("nd_pordes");
                        dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;

                        dblTotal = (dblCan * dblPre) - dblValDes ;
                        dblTotal = objUti.redondear(dblTotal, 3);
                        dblTotal = objUti.redondear(dblTotal, objParSis.getDecimalesMostrar());

                        vecReg.add(INT_TBL_DAT_VAL_TOT, new Double(dblTotal));
                        vecReg.add(INT_TBL_DAT_COD_ITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_ITM_ORI, "");
                        vecReg.add(INT_TBL_DAT_BOD_ORI, "");
                        vecReg.add(INT_TBL_DAT_EST_REG, "E");
                        vecReg.add(INT_TBL_DAT_TXT_IVA, "");
                        vecReg.add(INT_TBL_DAT_ITM_ALT2, strCodAlt2);
                        vecReg.add(INT_TBL_DAT_ITM_SER, strSer );
                        vecReg.add(INT_TBL_DAT_COD_REG, ""); 
                        vecReg.add(INT_TBL_DAT_COD_ITMACT,  rstLoc.getString("co_itm") ); 
                        vecReg.add(INT_TBL_DAT_IEBODFIS,    rstLoc.getString("proconf"));
                        vecReg.add(INT_TBL_DAT_COS_UNI_ORI, rstLoc.getString("nd_cosuni"));
                        vecReg.add(INT_TBL_DAT_POR_DES_ORI, rstLoc.getString("nd_pordes"));
                        vecReg.add(INT_TBL_DAT_PRE_COS_RES, rstLoc.getString("nd_cosuni"));
                        vecReg.add(INT_TBL_DAT_POR_DES_RES, rstLoc.getString("nd_pordes"));

                        vecDat.add(vecReg);                         
                    }
                    objTblMod.setData(vecDat);
                    tblDat .setModel(objTblMod);                                 
                     
                    calcularTotal();
                    generaAsiento();
                     
                    /* CARGANDO DATOS DEL TAB FORMA DE PAGO*/                     
                    String strCo_ForPag = (rstDatCot.getString("co_forPag")==null)?"":rstDatCot.getString("co_forPag") ;
                    
                    /* LLenando el combo de descripciones de pagos */
                    objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCo_ForPag);      
                    cboForPag.addItem("< Refrescar >");                    
                    calcularPagos();
                    String strStatus = rstDatCot.getString("st_reg");
                    if(strStatus.equals("I")){
                        objUti.desactivarCom(jfrThis);
                    }
                    else{
                        if (objTooBar.getEstado() == 'a'){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                        }
                    }
                }
                stmLoc.close();
                stmLoc=null;
                stmLocPri.close();
                stmLocPri=null;
                blnRes=true;
                blnHayCam = false; // Seteando que no se ha hecho cambios
            }
        }
        catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
    }
        
    private void refrescaPagos(int intNumeroDocumento)
    {
        try{       
            Connection conDetPag = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
            if (conDetPag!=null)
            {
                Statement stmDetPag = conDetPag.createStatement();
                //Extrayendo los datos del detalle respectivo a ESTE PAGO
                strSQL =" SELECT ne_diacre,fe_ven,nd_porret,tx_aplret,mo_pag,ne_diagra, co_tipret, co_reg, tx_codsri, tx_aplret, nd_basimp  " +
                        " FROM tbm_pagMovInv  " +
                        " WHERE co_emp = " + objParSis.getCodigoEmpresa() + "" +
                        " AND co_loc = " + objParSis.getCodigoLocal()   + "" +
                        " AND co_tipdoc = " + rstCab.getString("co_tipdoc")  + "" +
                        " AND co_doc = " + rstCab.getString("co_doc") + ""+
                        " AND st_reg IN ('A','F') "+
                        " ORDER BY co_reg ";
                //System.out.println(strSQL);
                ResultSet rstDetPag = stmDetPag.executeQuery(strSQL);

                /*LLenando el combo de descripciones de pagos */
                //Borrando los datos actuales del jtable
                while(tblForPag.getRowCount()>0)
                    ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).removeRow(0);

                /* LLenando la tabla de pagos*/
                for(int i=0 ; rstDetPag.next() ; i++){
                    ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());    

                    tblForPag.setValueAt(rstDetPag.getObject("ne_diaCre") , i, 1);
                    /* Formateando la fecha que viene desde la base para presentarla en d/m/y */
                    java.util.Calendar calFecPag = java.util.Calendar.getInstance();

                    calFecPag.setTime(rstDetPag.getDate("fe_ven"));
                    Librerias.ZafDate.ZafDatePicker dtpForPag = 
                      new Librerias.ZafDate.ZafDatePicker(calFecPag.get(java.util.Calendar.DAY_OF_MONTH), 
                                                        (calFecPag.get(java.util.Calendar.MONTH)+1), 
                                                        calFecPag.get(java.util.Calendar.YEAR), new javax.swing.JFrame(), "d/m/y"     
                     );

                    tblForPag.setValueAt(dtpForPag.getText() , i, 2);
                    tblForPag.setValueAt(rstDetPag.getObject("nd_porRet") , i, 3);
                    tblForPag.setValueAt(rstDetPag.getObject("mo_pag") , i, 4);
                    tblForPag.setValueAt(rstDetPag.getObject("ne_diaGra") , i, 5);  // ne_diaGra
                    tblForPag.setValueAt(rstDetPag.getObject("co_tipret") , i, 6);
                    tblForPag.setValueAt(rstDetPag.getObject("tx_codsri") , i, 7);
                    tblForPag.setValueAt(rstDetPag.getObject("tx_aplret") , i, 8);
                    tblForPag.setValueAt(rstDetPag.getObject("nd_basimp") , i, 9);  // nd_basimp
                }
                rstDetPag.close();
                stmDetPag.close();
                conDetPag.close();
            }
        }
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }            
     }
      
    private void refrescaDatos()
    {
        try{
            int intNumDoc = 0;
            if(rstCab != null){                    
                Connection  conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                String strSer="";
                Statement stm = conCab.createStatement();
                //Extrayendo los datos del detalle respectivo a esta orden de compra
                strSQL =" SELECT a.co_itm,a.tx_codalt,a.tx_codalt2,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                        "      , b.st_ser , a.co_reg , a.co_itmact, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax, a.nd_tot  " +
                        "      , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) " +
                        "        IN ( SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                        "             INNER JOIN tbm_reginvmernuningegrfisbod AS a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                        "             WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' " +
                        "        ))) THEN 'S' ELSE 'N' END AS proconf  "+ 
                        " FROM tbm_detMovInv AS a" + 
                        " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp and a.co_itm=b.co_itm) " +
                        " WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + "" +
                        " AND a.co_loc = " + objParSis.getCodigoLocal()   + "" +
                        " AND a.co_tipdoc = " +rstCab.getString("co_tipdoc")  + "" +
                        " AND a.co_doc = " + rstCab.getString("co_doc") +
                        " ORDER BY a.co_reg";
                System.out.println("ZafCom02: " + strSQL);
                ResultSet rst= stm.executeQuery(strSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;
                java.util.Vector vecDat = new java.util.Vector();
                while (rst.next())
                { 
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_ITM_ALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_BUT_ITM, "");
                    vecReg.add(INT_TBL_DAT_DES_ITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_DAT_COD_BOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_DAT_BUT_BOD, "");
                    vecReg.add(INT_TBL_DAT_CAN_ORI, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_DAT_CAN_MOV, new Double(rst.getDouble("nd_can")));                                                                           
                    vecReg.add(INT_TBL_DAT_PRE_COS, new Double(rst.getDouble("nd_cosuni")));
                    vecReg.add(INT_TBL_DAT_POR_DES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivacom");
                    Boolean blnIva = strIva.equals("S")?true:false;                         
                    vecReg.add(INT_TBL_DAT_CHK_IVA, blnIva);
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_cosuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                    //REVISAR
                    dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;
                    if (blnIva.booleanValue()){                            
                        dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                    }
                    vecReg.add(INT_TBL_DAT_VAL_TOT, rst.getString("nd_tot"));  //new Double(dblTotal));
                    vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_ITM_ORI, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_BOD_ORI, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_DAT_EST_REG, "E");
                    vecReg.add(INT_TBL_DAT_TXT_IVA, "");
                    vecReg.add(INT_TBL_DAT_ITM_ALT2, rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_ITM_SER, strSer );
                    vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg") );   
                    vecReg.add(INT_TBL_DAT_COD_ITMACT, rst.getString("co_itmact"));
                    vecReg.add(INT_TBL_DAT_IEBODFIS, rst.getString("st_meringegrfisbod"));
                    vecReg.add(INT_TBL_DAT_COS_UNI_ORI, rst.getString("nd_preunivenlis"));
                    vecReg.add(INT_TBL_DAT_POR_DES_ORI, rst.getString("nd_pordesvenmax"));
                    vecReg.add(INT_TBL_DAT_PRE_COS_RES, rst.getString("nd_cosuni"));
                    vecReg.add(INT_TBL_DAT_POR_DES_RES, rst.getString("nd_pordes"));
                    vecDat.add(vecReg);                         
                }
                objTblMod.setData(vecDat);
                tblDat .setModel(objTblMod);                                 

                calcularTotal();
           
                /* Cargando datos del TAB: Asiento de Diario. */
                objAsiDia.consultarDiarioCompleto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), rstCab.getInt("co_tipdoc"), rstCab.getInt("co_doc"));

                stm.close();
                rst.close();
                conCab.close();
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam = false; // Seteando que no se ha hecho cambios
   
        }
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                          
    }    
     
   //*******************************************************************************************************
    private void cargaForPag(java.sql.Connection conn, int intCodMot ){
        Statement stm;
        ResultSet rst;
        int intCodEmp=0;
        String strSQL="";
        double dblBasImp=0, dblValRet=0;
        try{
            if(conn!=null){
                if(blnEstCarOC)   {
                   intCodEmp= Integer.parseInt(strCodEmpOC); 
                }
                else {
                   intCodEmp=objParSis.getCodigoEmpresa();
                }

                stm=conn.createStatement();
                
                String strFecDoc=""+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd")+"";
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT x.co_tipRet, x.tx_descor, x.tx_deslar, x.tx_aplret, x.co_cta, x.tx_codsri";
                strSQL+="     , (CASE WHEN (x.tx_aplRet='S' AND x.fe_doc < '2007-07-01') THEN CAST( 0.01 AS NUMERIC) ELSE x.nd_porRet END) AS nd_porRet";
                strSQL+=" FROM (";
                strSQL+="    SELECT a5.co_tipret, a5.tx_descor, a5.tx_deslar, a5.nd_porret, a5.tx_aplret, a5.co_cta, a.tx_codsri ";
                strSQL+="         , CAST ( '"+strFecDoc+"' AS DATE) AS fe_doc";
                strSQL+="    FROM tbm_polRet AS a";
                strSQL+="    INNER JOIN tbm_emp AS a2 ON (a.co_emp=a2.co_emp AND a.co_ageRet=a2.co_tipPer)"; /* Tipo de Persona: Empresa */
                strSQL+="    INNER JOIN tbm_cli AS a3 ON (a.co_emp=a3.co_emp AND a.co_sujRet=a3.co_tipPer)"; /* Tipo de Persona: Cliente */
                strSQL+="    LEFT OUTER JOIN tbm_motDoc AS a4 ON (a.co_emp=a4.co_emp AND a.co_mottra=a4.co_mot)";
                strSQL+="    LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a.co_emp=a5.co_emp AND a.co_tipret=a5.co_tipret)";
                strSQL+="    WHERE a.st_reg='A' AND a4.co_mot = "+intCodMot+""; 
                strSQL+="    AND a2.co_emp = "+ intCodEmp;
                if(!txtCodPrv.getText().equals(""))
                    strSQL+="   AND a3.co_cli="+txtCodPrv.getText(); 
                strSQL+="    AND (CASE WHEN a.fe_vigHas IS NULL THEN '"+strFecDoc+"'>=fe_vigDes ELSE '"+strFecDoc+"'>=fe_vigDes AND '"+strFecDoc+"'<=fe_vigHas END)";
                strSQL+="    GROUP BY a5.co_tipret, a5.tx_descor, a5.tx_deslar, a5.nd_porret, a5.tx_aplret, a5.co_cta, a.tx_codsri";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.nd_porRet";
                System.out.println("cargaForPag: "+strSQL);
                rst = stm.executeQuery(strSQL);
                while (rst.next())
                {
                    dblBasImp=0; dblValRet=0;
                    if(rst.getString("tx_aplret").equals("S"))
                    {
                        dblBasImp=dblSubSerNoSer;
                        dblValRet = objUti.redondeo( (   dblBasImp  * ( rst.getDouble("nd_porret")/100)),2 );
                        dblRetFteGlo+=dblValRet;  
                    }
                    else if (rst.getString("tx_aplret").equals("I"))
                    {
                        if(dblIvaSerNoSer > 0 ){
                            dblBasImp=dblIvaSerNoSer;
                            dblValRet = objUti.redondeo(  (  dblBasImp  * ( rst.getDouble("nd_porret")/100)),2 );
                            dblRetIvaGlo+=dblValRet;
                        }
                    }
                        
                    ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());
                        tblForPag.setValueAt("",tblForPag.getRowCount()-1, 1);
                        tblForPag.setValueAt(dtpFecDoc.getText(), tblForPag.getRowCount()-1, 2);
                        tblForPag.setValueAt(rst.getString("nd_porret") , tblForPag.getRowCount()-1, 3);
                        tblForPag.setValueAt(new Double(dblValRet) , tblForPag.getRowCount()-1, 4);
                        tblForPag.setValueAt(rst.getString("co_tipret") , tblForPag.getRowCount()-1, 6);
                        tblForPag.setValueAt(rst.getString("tx_codsri") , tblForPag.getRowCount()-1, 7);
                        tblForPag.setValueAt(rst.getString("tx_aplret") , tblForPag.getRowCount()-1, 8);
                        tblForPag.setValueAt(new Double(objUti.redondear(dblBasImp,2))  , tblForPag.getRowCount()-1, 9);
                }
                rst.close();
                rst=null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    }

    public class ActLisForPag implements java.awt.event.ActionListener{
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if(cboForPag.getSelectedIndex()>-1 ){
                if (cboForPag.getSelectedItem().toString().equals("< Refrescar >")){                                   
                    cargaListadoFormaPago();
                }
            }
              
            try{
                java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conPag!=null){     
                    if(cboForPag.getSelectedIndex()!=-1){
                        String strSQL2 = " SELECT a1.ne_numPag, a2.ne_diaCre " +
                                         " FROM tbm_cabForPag AS a1 LEFT OUTER JOIN tbm_detForPag AS a2 ON (a2.co_emp = a1.co_emp AND a2.co_forPag = a1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                         " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                         " AND a1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) + 
                                         " ORDER BY a2.co_reg";
                                            
                        String strSQL3 = " SELECT count(a2.ne_diaCre) AS c  " +
                                         " FROM tbm_cabForPag AS a1 LEFT OUTER JOIN tbm_detForPag AS a2 ON (a2.co_emp = a1.co_emp AND a2.co_forPag = a1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                         " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                         " AND a1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex());
                               
                        Statement stmDoc2   = conPag.createStatement();                                  
                        ResultSet rstDocCab2= stmDoc2.executeQuery(strSQL3);
                        rstDocCab2.next();
                        intCanArr[0] = rstDocCab2.getInt(1);

                        stmDoc2   = conPag.createStatement();                                  
                        rstDocCab2= stmDoc2.executeQuery(strSQL2);
                        int x=0;
                        while(rstDocCab2.next()){
                              intarreglodia[x]=rstDocCab2.getInt(2);
                              intarreglonum[x]=rstDocCab2.getInt(1);
                              x++;  
                        }
                    }
                    conPag.close();
                    conPag = null ;
                }
            }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
            calcularPagos(); 
        }
    }    
           
    private boolean consultar2(int CodEmp,int CodLoc)
    {
        try{
            conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){

                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                strSQL ="";
                strSQL+=" SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc ";                              
                strSQL+=" FROM tbm_cabMovInv as CabMovInv left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) left outer join tbr_tipdocprg as menu  on (menu.co_emp = doc.co_emp and menu.co_loc = doc.co_loc and menu.co_tipdoc = doc.co_tipdoc) " ;
                strSQL+=" WHERE CabMovInv.st_reg NOT IN ('E')";// Consultando en la empresa en la ke se esta trabajando
                strSQL+=" AND CabMovInv.co_emp = " + CodEmp ;
                strSQL+=" AND CabMovInv.co_loc = " + CodLoc;         

                //Agregando filtro por Codigo Tipo de Documento
                if(!txtCodTipDoc.getText().equals(""))
                    strSQL+=" AND CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";
                //Agregando filtro por Numero de Documento
                if(!txtNumDoc.getText().equals(""))
                    strSQL+=" AND CabMovInv.co_doc = " + txtNumDoc.getText()+ "";
                //Agregando filtro por Menú
                strSQL+=" AND menu.co_mnu = " + intCodMnu  ;      

                strSQL+=" ORDER BY CabMovInv.co_doc";

                rstCab=stmCab.executeQuery(strSQL);

                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    limpiarFrm();
                    return false;
                }
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false; }
        return true;     
    }   
   
    int intCodMotBien=0;
    int intCodMotServ=0;
    int intCodMotTran=0;

    private boolean getTipMotRet(Connection conTmp){
        boolean blnRes=true;
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            if (conTmp!=null)
            {
                stmTmp = conTmp.createStatement();
                String sSQL = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B','S','T')";
                rstTmp=stmTmp.executeQuery(sSQL);
                while(rstTmp.next()){
                    if(rstTmp.getString("tx_tipmot").equals("B")) intCodMotBien= rstTmp.getInt("co_mot");
                    else if(rstTmp.getString("tx_tipmot").equals("S")) intCodMotServ= rstTmp.getInt("co_mot");
                    else if(rstTmp.getString("tx_tipmot").equals("T")) intCodMotTran= rstTmp.getInt("co_mot");
                }
                rstTmp.close();
                stmTmp.close();
                rstTmp=null;
                stmTmp=null;
            }
        }
        catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        return blnRes;
   }

    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc )
    {
        boolean blnRes=true;
        Connection conn;
        try{
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conn!=null){
                cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
                cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

    private boolean cargarCabReg(Connection con, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc)
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                Statement stm=con.createStatement();
                String strSQL="";

                strSQL =" SELECT a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli, a.co_com, a.tx_nomven as nomcom"+
                        "      , a.co_doc, a.fe_doc, a.tx_ate,  a.co_forPag, a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva"+
                        "      , a.nd_tot, a.st_reg, a.ne_numcot as num_cot, a.ne_numdoc as num_doc "+
                        "      , a.tx_numDoc2, a.tx_numped as num_ped, a.co_tipdoc, a.co_forret, b.tx_deslar, a.tx_vehret, a.tx_choret, a.ne_secemp "+
                        "      , a.st_regrep ,cli.tx_tipper ,cli.co_tipper, cli.st_ivacom " +
                        "      , a1.tx_descor, a1.tx_deslar as nomtipdoc  " +
                        " FROM tbm_cabMovInv AS a " +
                        " LEFT OUTER JOIN tbm_var AS b ON (b.co_reg=a.co_forret)"+
                        " INNER JOIN tbm_cabtipdoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc )"+
                        " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a.co_emp AND cli.co_cli=a.co_cli) " +
                        " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipDoc="+ intCodTipDoc+""+
                        " AND a.st_reg not in('E') AND a.co_doc="+intCodDoc;
                ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    Glo_intCodSec=rst.getInt("ne_secemp");

                    intNumDoc = rst.getInt("co_doc");
                    txtNumDoc.setText(""+intNumDoc);

                    txtDesCorTipDoc.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rst.getString("nomtipdoc")==null)?"":rst.getString("nomtipdoc")));

                    txtCodPrv.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomPrv.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtDirPrv.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    txtNumCot.setText(((rst.getString("num_cot")==null)?"":rst.getString("num_cot")));
                    txtNumOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    txtNumPed.setText(((rst.getString("tx_numdoc2")==null)?"":rst.getString("tx_numdoc2")));
                    txtNumFacPrv.setText(((rst.getString("num_ped")==null)?"":rst.getString("num_ped")));

                    STR_ESTREG=rst.getString("st_regrep");

                    txtCod.setText(((rst.getString("co_forret")==null)?"":rst.getString("co_forret")));
                    txtDes.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    txtVehRet.setText(((rst.getString("tx_vehret")==null)?"":rst.getString("tx_vehret")));
                    txtChoRet.setText(((rst.getString("tx_choret")==null)?"":rst.getString("tx_choret")));

                    stIvaCom=rst.getString("st_ivacom");

                    if(rst.getDate("fe_doc")==null){
                      dtpFecDoc.setText("");
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        dtpFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }

                    txtCodCom.setText(((rst.getString("co_com")==null)?"":rst.getString("co_com")));
                    txtNomCom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));

                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objParSis.getDecimalesMostrar()));

                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));

                    lblTit.setText("Orden de Compra No. " + txtNumOrdCom.getText() +  " (" + txtNomPrv.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");
        
                    /* Cargar datos del TAB: Forma de Pago */
                    String strCo_ForPag = (rst.getString("co_forPag")==null)?"":rst.getString("co_forPag") ;
                    cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));
                    //refrescaPagos(intNumDoc);

                    txtSub.setText(""+dblSub);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                //Mostrar la posición relativa del registro.
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /***
     * Función que extrae los datos del detalle respectivo a esta orden de compra
     */
    private void cargarDetReg(java.sql.Connection con, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc)
    {
        try{
            String strAux2 =" , 'S' as proconf ";

            String strSer="";
            Statement stm = con.createStatement();
            //Armar sentencia SQL
            strSQL ="";
            strSQL+=" SELECT a.co_itm,a.tx_codalt,a.tx_codalt2,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can"+
                    "       ,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                    "       ,b.st_ser, a.co_reg, a.co_itmact, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax  " +
                    " "+strAux2+" "+
                    " FROM tbm_detMovInv AS a" +
                    " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp AND a.co_itm=b.co_itm) " +
                    " WHERE a.co_emp = " + intCodEmp + "" +
                    " AND a.co_loc = " + intCodLoc  + "" +
                    " AND a.co_tipdoc = " +intCodTipDoc  + "" +
                    " AND a.co_doc = " + intCodDoc  +
                    " ORDER BY a.co_reg";
            ResultSet rst= stm.executeQuery(strSQL);
            
            double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;

            //Limpiar vector de datos.
            vecDat.clear();
            while (rst.next())
            {
                strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                vecReg = new Vector(); 
                vecReg.add(INT_TBL_DAT_LIN, "");
                vecReg.add(INT_TBL_DAT_ITM_ALT, rst.getString("tx_codAlt"));
                vecReg.add(INT_TBL_DAT_BUT_ITM, "");
                vecReg.add(INT_TBL_DAT_DES_ITM, rst.getString("tx_nomitm"));
                vecReg.add(INT_TBL_DAT_UNI_MED, rst.getString("tx_unimed"));
                vecReg.add(INT_TBL_DAT_COD_BOD, new Integer(rst.getInt("co_bod")));
                vecReg.add(INT_TBL_DAT_BUT_BOD, "");
                vecReg.add(INT_TBL_DAT_CAN_ORI, new Double(rst.getDouble("nd_can")));
                vecReg.add(INT_TBL_DAT_CAN_MOV, new Double(rst.getDouble("nd_can")));
                vecReg.add(INT_TBL_DAT_PRE_COS, new Double(rst.getDouble("nd_cosuni")));
                vecReg.add(INT_TBL_DAT_POR_DES, new Double(rst.getDouble("nd_pordes")));
                String strIva = rst.getString("st_ivacom");
                Boolean blnIva = new Boolean(strIva.equals("S")?true:false);
                vecReg.add(INT_TBL_DAT_CHK_IVA, blnIva);
                dblCan    = rst.getDouble("nd_can");
                dblPre    = rst.getDouble("nd_cosuni");
                dblPorDes = rst.getDouble("nd_pordes");
                dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;

                dblTotal = (dblCan * dblPre) - dblValDes ;
                dblTotal = objUti.redondear(dblTotal, 3);
                dblTotal = objUti.redondear(dblTotal, objParSis.getDecimalesMostrar());

                if (blnIva.booleanValue()){
                   dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                }

                vecReg.add(INT_TBL_DAT_VAL_TOT, new Double(dblTotal));
                vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                vecReg.add(INT_TBL_DAT_ITM_ORI, rst.getString("co_itm"));
                vecReg.add(INT_TBL_DAT_BOD_ORI, new Integer(rst.getInt("co_bod")));
                vecReg.add(INT_TBL_DAT_EST_REG, "E");
                vecReg.add(INT_TBL_DAT_TXT_IVA, "");
                vecReg.add(INT_TBL_DAT_ITM_ALT2, rst.getString("tx_codAlt2"));
                vecReg.add(INT_TBL_DAT_ITM_SER, strSer );
                vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg") );
                vecReg.add(INT_TBL_DAT_COD_ITMACT, rst.getString("co_itmact"));
                vecReg.add(INT_TBL_DAT_IEBODFIS, rst.getString("st_meringegrfisbod"));
                vecReg.add(INT_TBL_DAT_COS_UNI_ORI, rst.getString("nd_preunivenlis"));
                vecReg.add(INT_TBL_DAT_POR_DES_ORI, rst.getString("nd_pordesvenmax"));
                vecReg.add(INT_TBL_DAT_PRE_COS_RES, rst.getString("nd_cosuni"));
                vecReg.add(INT_TBL_DAT_POR_DES_RES, rst.getString("nd_pordes"));

                vecDat.add(vecReg);
            }
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);

            calcularTotal();

            objAsiDia.consultarDiarioCompleto( Integer.parseInt( intCodEmp ), Integer.parseInt( intCodLoc ), Integer.parseInt( intCodTipDoc ), Integer.parseInt( intCodDoc )  );

            stm.close();
            rst.close();
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt) {    objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    private boolean insertarReg(){
        boolean blnRes=false;
        java.sql.Connection conIns;
        int intSecEmp=0, intSecGrp=0;
        try{ 
            calculaDetalle();  
            conIns=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

            if(!abrirConRem())
                return false;

            if (conIns!=null){
                if(INTCODREGCEN!=0){
                      conRemGlo.setAutoCommit(true);
                      intSecEmp = objUltDocPrint.getNumSecDoc(conRemGlo, objParSis.getCodigoEmpresa() );
                      intSecGrp = objUltDocPrint.getNumSecDoc(conRemGlo, objParSis.getCodigoEmpresaGrupo() );
                      conRemGlo.setAutoCommit(false);
                }else{
                     intSecEmp = objUltDocPrint.getNumSecDoc(conIns, objParSis.getCodigoEmpresa() );
                     intSecGrp = objUltDocPrint.getNumSecDoc(conIns, objParSis.getCodigoEmpresaGrupo() );
                }

                conIns.setAutoCommit(false);

                if(!verificaEstCliPrv(conIns))
                    return false;
                
                objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
                txtNumDoc.setText(""+objUltDocPrint.getCodigoDocumento(conIns, objTipDoc.getco_tipdoc()));
                if(Integer.parseInt(txtNumOrdCom.getText())>0){
                    if(objUltDocPrint.getExisteDoc(Integer.parseInt(txtNumOrdCom.getText()) ,objTipDoc.getco_tipdoc(), Integer.parseInt(txtNumDoc.getText()) ) ){
                        mostrarMsgInf("No. de Orden de Compra ya duplicada ");
                        txtNumOrdCom.requestFocus();
                        txtNumOrdCom.selectAll();
                        conIns.close();
                        conIns=null;
                        return false;
                    }
                }
                
                intNumDoc = Integer.parseInt(txtNumDoc.getText());

                if(insertarCab(conIns, intNumDoc, intSecEmp, intSecGrp )){
                    if(insertarDet(conIns, conRemGlo, intNumDoc )){
                      if(insertDetPag(intNumDoc, conIns, "I")){  
                        if(objAsiDia.insertarDiario(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  txtNumDoc.getText(), txtNumOrdCom.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) {
                            conIns.commit();
                                if(INTCODREGCEN!=0){
                                    conRemGlo.commit();
                                }
                           blnRes=true;
                       }else { conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                     }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                  }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }

                if(blnRes){
                    for(int i=0; i<tblDat.getRowCount(); i++){ 
                        if(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM) != null ){ 
                            tblDat.setValueAt("E", i, INT_TBL_DAT_EST_REG);
                            tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString() , i, INT_TBL_DAT_ITM_ORI);
                            tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD).toString() , i, INT_TBL_DAT_BOD_ORI);
                            tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV).toString() , i, INT_TBL_DAT_CAN_ORI);
                        }
                    }
                }           

                conIns.close();
                conIns=null;
                if(INTCODREGCEN!=0){
                    conRemGlo.close();
                    conRemGlo=null;
                }

                STR_ESTREG="I";  
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;                  
    }
                      
    private boolean insertarCab(java.sql.Connection conIns, int intNumDoc, int intSecEmp, int intSecGrp  ){
        boolean blnRes = true;
        java.sql.Statement stm;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strNomBen="";
        int intCodBen=0;
        String strSQL, strFecha, strFecSis;
        try{
              stmLoc=conIns.createStatement();
              
                Glo_intCodSec=intSecEmp;
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                     
                String strconinv="F";
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM)!=null){
                        String strEst=(tblDat.getValueAt(i, INT_TBL_DAT_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_DAT_IEBODFIS).toString());                          
                        String strEstSer=(tblDat.getValueAt(i, INT_TBL_DAT_ITM_SER)==null?"":tblDat.getValueAt(i, INT_TBL_DAT_ITM_SER).toString());
                        if( (strEst.equals("N")) && (strEstSer.equals("N")) ){
                          strconinv="P";
                        }
                    }
                }

                if(strconinv.equals("P"))
                    if(strMerIngEgr.equals("N")) strconinv="F";
                   
                //Armar sentencia SQL: Obtiene Datos del Beneficiario.
                strSQL="";
                strSQL+=" SELECT co_reg, tx_benchq FROM tbm_benchq ";
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" " ;
                strSQL+=" AND co_cli="+txtCodPrv.getText()+" AND st_reg='P'";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                   intCodBen=rstLoc.getInt("co_reg");
                   strNomBen=rstLoc.getString("tx_benchq");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;

                String strEmiChq="";
                if(chkEmiChq.isSelected()) strEmiChq="S";

                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabMovInv ";
                strSQL+=" ( co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli";
                strSQL+=" , tx_nomven, ne_numDoc, ne_numCot, tx_numPed, tx_obs1, tx_obs2, nd_sub , nd_tot, nd_valiva, nd_porIva, co_forPag";
                strSQL+=" , fe_ing, co_usrIng,fe_ultMod, co_usrMod , tx_desforpag ,st_reg";
                strSQL+=" , co_forret,tx_vehret,tx_choret,ne_secgrp,ne_secemp";
		strSQL+=" , st_regrep ,st_coninv, co_ben, tx_benchq , st_emiChqAntRecFacPrv, st_imp, tx_numDoc2 ";
                strSQL+=" ) ";
                strSQL+=" VALUES (";
                strSQL+=" "+objParSis.getCodigoEmpresa();
                strSQL+=" ,"+objParSis.getCodigoLocal();
                strSQL+=" ,"+txtCodTipDoc.getText();
                strSQL+=" ,"+intNumDoc;
                strSQL+=" ,'"+strFecha+"'";
                strSQL+=" ,"+txtCodPrv.getText();
                strSQL+=" ,"+txtCodCom.getText();
                strSQL+=" ,'"+txtAte.getText()+"'";
                strSQL+=" ,'"+txtNomPrv.getText()+"'";
                strSQL+=" ,'"+txtDirPrv.getText()+"'";
                strSQL+=" ,'"+objCliente_dat.getIdentificacion()+"'";
                strSQL+=" ,'"+objCliente_dat.getTelefono()+"'";
                strSQL+=" ,'"+objCiudad_dat.getNombrelargo()+"'";
                strSQL+=" ,'"+txtNomCom.getText()+"'";
                strSQL+=" ,"+txtNumOrdCom.getText();
                strSQL+=" ,"+((txtNumCot.getText().equals(""))?"0":txtNumCot.getText());
                strSQL+=" ,"+objUti.codificar(txtNumFacPrv.getText());
                strSQL+=" ,'"+txaObs1.getText()+"'";
                strSQL+=" ,'"+txaObs2.getText()+"'";
                strSQL+=" ,"+objUti.redondeo(Double.parseDouble(txtSub.getText()),6);
                strSQL+=" ,"+objUti.redondeo(Double.parseDouble(txtTot.getText()),6);
                strSQL+=" ,"+txtIva.getText();
                strSQL+=" ,"+objUti.redondeo(dblPorIva , 2);
                strSQL+=" ,"+Integer.parseInt(""+vecForPag.get(cboForPag.getSelectedIndex()));
                strSQL+=" ,'"+ strFecSis+"'";
                strSQL+=" ,"+objParSis.getCodigoUsuario();
                strSQL+=" ,'"+ strFecSis+"'";
                strSQL+=" ,"+objParSis.getCodigoUsuario();
                strSQL+=" ,'"+cboForPag.getSelectedItem()+ "'";
                strSQL+=" ,'A'";
                strSQL+=" ,"+ txtCod.getText();
                strSQL+=" ,'"+ txtVehRet.getText()+"'";
                strSQL+=" ,'"+ txtChoRet.getText()+"'";
                strSQL+=" ,"+intSecGrp;
                strSQL+=" ,"+intSecEmp;
		strSQL+=" ,'I'";
                strSQL+=" ,'"+strconinv+"'";
                strSQL+=" ,"+intCodBen;
                strSQL+=" ,'"+strNomBen+"'";
                strSQL+=" ,"+(strEmiChq.equals("")?null:"'"+strEmiChq+"'");
                strSQL+=" ,'S'";
                strSQL+=" ," + objUti.codificar(txtNumPed.getText());
                strSQL+=" );";
                
                strSQL+=" UPDATE tbm_cabcotcom SET st_reg='F' WHERE co_emp="+objParSis.getCodigoEmpresa() +" AND co_loc="+objParSis.getCodigoLocal() + " AND co_cot = " + ((txtNumCot.getText().equals(""))?"0":txtNumCot.getText());
                stm = conIns.createStatement();
                stm.executeUpdate(strSQL);
                
                strSQL ="";
                strSQL+=" INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, st_regrep) ";
                strSQL+=" VALUES(";
                strSQL+=" "+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+"";
                strSQL+=" , "+txtCodTipDoc.getText()+", "+intNumDoc+", 1, "+intCodMotBien+", 'I' ";
                strSQL+=" );";
                stm.executeUpdate(strSQL);
                          
                stm.close();
                stm=null;
          
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
        
    private boolean insertarDet(java.sql.Connection conIns, java.sql.Connection connRemota, int intNumDoc  ){
        boolean blnRes = true;
        int intControl=0;
        double dlbCanMov=0;
        String strSQL;
        int intTipCli=-1;
        int intCodItm=0;
        int intCodBod=0;
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        java.sql.Statement stmDet;
        try{
            GLO_strArreItm="";

            String str_MerIEFisBod="S";
            double dbl_canConIE=0.00;
            intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodPrv.getText(),conIns);

            for(int i=0; i<tblDat.getRowCount(); i++){ 
                if(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM) != null ){ 
                    intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM)); 
                    intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD));
                    dlbCanMov=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV).toString()));

                    dbl_canConIE = Double.parseDouble(  (tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV ).toString());
                    String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_DAT_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_DAT_IEBODFIS).toString());                          
                    str_MerIEFisBod="N";  
                    /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                    if(tblDat.getValueAt(i, INT_TBL_DAT_ITM_SER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                        if(strEstFisBod.equals("N")){
                            if(strMerIngEgr.equals("S")){   
                                str_MerIEFisBod="S";
                                dbl_canConIE=0;
                            }
                        }
                        ZafStkInv objStkInv= new ZafStkInv(objParSis); 
                        if(objStkInv.actualizaInventario(conIns, objParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK_DIS, "+", 1, getArlItmInv(conIns, objParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                            if(!objStkInv.actualizaInventario(conIns, objParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK, "+", 0, getArlItmInv(conIns, objParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                    
                    /****************************************************************************************************/
                    if(intControl!=0)    
                        GLO_strArreItm=GLO_strArreItm+",";

                    GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM).toString();
                    intControl++;

                    if (i>0)
                        stbins.append(" UNION ALL ");/* JoséMario 25/Sep/2015 */
                    
                    stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intNumDoc+","+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_DAT_UNI_MED)+"'" +
                    " ,"+tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM)+","+tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM)+",'"+tblDat.getValueAt(i, INT_TBL_DAT_ITM_ALT)+"','"+tblDat.getValueAt(i, INT_TBL_DAT_ITM_ALT2)+"'"+
                    " ,'"+objUti.remplazarComillaSimple(tblDat.getValueAt(i, INT_TBL_DAT_DES_ITM))+"' " +
                    " ,"+tblDat.getValueAt(i, INT_TBL_DAT_COD_BOD)+""+
                    " ,"+Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_DAT_CAN_MOV))+""+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())+"" +
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())+""+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())+"" +
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_POR_DES)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_POR_DES).toString()),objParSis.getDecimalesMostrar())+""+
                    " ,'"+((tblDat.getValueAt(i, INT_TBL_DAT_CHK_IVA)==null)?"N":(tblDat.getValueAt(i, INT_TBL_DAT_CHK_IVA).toString().equals("true"))?"S":"N") +"'"+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())+""+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())+"" +
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())+""+
                    " ,'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" "+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())+""+
                    " ,"+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_POR_DES)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_POR_DES).toString()),objParSis.getDecimalesMostrar())+""+
                    " ");
                    tblDat.setValueAt(""+(i+1) , i, INT_TBL_DAT_COD_REG);
                }
            }

            if(intControl > 0 ){
                strSQL = "";
                strSQL+=" INSERT INTO tbm_detMovInv ";
                strSQL+=" ( co_emp, co_loc, co_tipdoc , co_doc, co_reg, tx_unimed, co_itm, co_itmact, tx_codAlt,tx_codAlt2, tx_nomItm, co_bod";
                strSQL+=" , nd_can, nd_cosUni, nd_cosUniGrp, nd_preuni, nd_porDes, st_ivaCom, nd_costot, nd_costotgrp, nd_tot, st_regrep, st_meringegrfisbod, nd_cancon ";
                strSQL+=" , nd_preunivenlis, nd_pordesvenmax ";
                strSQL+="  ) ";
                strSQL+=""+stbins.toString();
                stmDet=conIns.createStatement();
                stmDet.executeUpdate(strSQL);
                stmDet.close();
                stmDet=null;
            }
            stbins=null;
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
        
    private boolean insertDetPag(int intNumDoc, java.sql.Connection conInsPag, String strRegRep){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strApliRet="";
        String strSql="";
        try{
           stmLoc=conInsPag.createStatement();
            for(int i=0; i<tblForPag.getRowCount();i++){

               strApliRet=(tblForPag.getValueAt(i, 8)==null)?"":tblForPag.getValueAt(i, 8).toString(); 

               int FecPagDoc[] =  dtpFecDoc.getFecha(tblForPag.getValueAt(i, 2).toString());
               String strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];

               strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " + 
               " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
               " VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+
               intNumDoc+", "+(i+1)+", "+((tblForPag.getValueAt(i, 1).equals(""))?"0":tblForPag.getValueAt(i, 1))+", "+
               "'"+strFechaPag+"', "+((tblForPag.getValueAt(i, 3)==null)?"0":tblForPag.getValueAt(i, 3))+", "+
               ""+objUti.redondeo(Double.parseDouble((tblForPag.getValueAt(i, 4)==null)?"0":tblForPag.getValueAt(i, 4).toString() ),6)+", "+
               ((tblForPag.getValueAt(i, 5)==null)?"0":tblForPag.getValueAt(i, 5).toString())+","+
               ((tblForPag.getValueAt(i, 6)==null)?"0":tblForPag.getValueAt(i, 6).toString())+",'"+
               ((tblForPag.getValueAt(i, 7)==null)?"":tblForPag.getValueAt(i, 7).toString())+"','"+
               ((tblForPag.getValueAt(i, 8)==null)?"":tblForPag.getValueAt(i, 8).toString())+"',"+
               ((tblForPag.getValueAt(i, 9)==null)?"0":tblForPag.getValueAt(i, 9).toString())+",'I' )";
               stmLoc.executeUpdate(strSql);  
            }
            stmLoc.close();
            stmLoc=null;

            blnRes=true;  
         }catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
          catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
    }
            
    private boolean actualizarReg()
    {
        calculaDetalle();

        double dblDif;
        java.util.ArrayList arlActInv=new java.util.ArrayList();
        strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

        if (!objUltDocPrint.getDocReposicion(Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtNumOrdCom.getText())))
        {
             mostrarMsgInf("Orden de Compra no puede ser modificada.");
             txtNumOrdCom.requestFocus();
             return false;                               
        }

        objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
        java.sql.Connection conMod;
         try{
                conMod =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try{
                    java.sql.PreparedStatement pstModCabMovInv ;
                    if (conMod!=null)
                    {

                        conMod.setAutoCommit(false);
                        /**
                         * verificar que no este anulada
                         * @autor: jsalazar
                         */
                        if (isAnulada()){
                             mostrarMsgInf("Documento anulado no se puede modificar");
                            return false;                                          
                        }
                        /*
                        * Convirtiendo la fecha en formato aaaa/mm/dd
                        * para poder grabarlo en la base
                        */
                       int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
                       String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                        /*********************************************\    
                         * Armando el Update para los datos          *   
                         * de la cabecera de cotizacion              *
                        \*********************************************/
                        if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  

                        String SQL =" Update tbm_cabMovInv set " +
                                    " fe_doc  = '" + strFecha            + "', " +
                                    " co_cli  = "  + txtCodPrv.getText() + ",  " + 
                                    " ne_numCot="  + (txtNumCot.getText().equals("")?"0":txtNumCot.getText()) + ",  " + 
                                    " ne_numDoc="  + txtNumOrdCom.getText()    + ",  " + 
                                    " tx_numdoc2="  + objUti.codificar(txtNumPed.getText())    + ",  " + 
                                    " tx_nomCli='" + txtNomPrv.getText() + "', " +
                                    " tx_dirCli='" + txtDirPrv.getText() + "', " +
                                    " tx_ruc='"    + objCliente_dat.getIdentificacion() + "', " +
                                    " tx_telCli='" + objCliente_dat.getTelefono()       + "', " +
                                    " tx_ciuCli='" + objCiudad_dat.getNombrelargo() + "', " +
                                    " co_com  = "  + txtCodCom.getText() + ",  " +
                                    " tx_nomven='" + txtNomCom.getText() + "', " +
                                    " tx_ate  = '" + txtAte.getText()    + "', " + 
                                    " tx_obs1 = '" + txaObs1.getText().replaceAll("'","")   + "', " + 
                                    " tx_obs2 = '" + txaObs2.getText().replaceAll("'","")   + "', " + 
                                    " nd_sub  = "  + objUti.redondeo(Double.parseDouble(txtSub.getText()),objParSis.getDecimalesBaseDatos())    + ",  " +
                                    " nd_tot  = "  + objUti.redondeo(Double.parseDouble(txtTot.getText()),objParSis.getDecimalesBaseDatos())    + ",  " +
                                    " nd_valiva  = "  + objUti.redondeo(Double.parseDouble(txtIva.getText()),objParSis.getDecimalesBaseDatos())    + ",  " +
                                    " nd_porIva = "+ dblPorIva           +  ", " +
                                    " co_forPag = "+ vecForPag.get(cboForPag.getSelectedIndex()) + ", " + 
                                    " tx_desforpag = '"+ cboForPag.getSelectedItem() +  "' , " +
                                    " fe_ultMod   =  '"+strFecSis+"', " +
                                    " co_usrMod   = "+ objParSis.getCodigoUsuario() + ", " +
                                    " tx_numped = " + objUti.codificar(txtNumFacPrv.getText()) + ", " +
                                    " st_regrep='"+STR_ESTREG+"' "+
                                    " where co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                                    " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                                    " co_tipDoc = " + txtCodTipDoc.getText()  + " and "+  
                                    " co_doc = " +  txtNumDoc.getText();    
                    //System.out.println(SQL);
                    pstModCabMovInv = conMod.prepareStatement(SQL);                                     
                    /* Ejecutando el Update */
                    pstModCabMovInv.executeUpdate();
                    //****************************************************************                 
                    int intTipCli=3;
                    intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodPrv.getText(),conMod);
                    //****************************************************************                   
                    int intControl=0;
                    GLO_strArreItm="";
                    String strCodReg="";
                    for(int i=0; i<tblDat.getRowCount(); i++){  //**************
                        if(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM) != null ){  //*********
                            if(tblDat.getValueAt(i,INT_TBL_DAT_COD_REG) != null ){  //*********  
                                if(intControl!=0)    
                                    GLO_strArreItm=GLO_strArreItm+",";

                                GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM).toString();

                                strCodReg = tblDat.getValueAt(i,INT_TBL_DAT_COD_REG).toString();

                                intControl++;

                                SQL = " UPDATE tbm_detMovInv SET  " +
                                      " nd_cosUni="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())   + ", " +
                                      " nd_cosUniGrp="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())+ ", " +
                                      " nd_preuni="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_PRE_COS).toString()),objParSis.getDecimalesBaseDatos())   + ", " +
                                      " nd_porDes="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_POR_DES)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_POR_DES).toString()),objParSis.getDecimalesMostrar())     + ", " +
                                      " nd_costot="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())     + ", " +
                                      " nd_costotGrp="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())  + ", " +
                                      " nd_tot="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT)==null)?"0":tblDat.getValueAt(i, INT_TBL_DAT_VAL_TOT).toString()),objParSis.getDecimalesBaseDatos())        + " " + 
                                      " WHERE " +
                                      " co_emp="+objParSis.getCodigoEmpresa()+" AND " +
                                      " co_loc="+objParSis.getCodigoLocal()+" AND " +
                                      " co_tipDoc="+txtCodTipDoc.getText()+" AND " +
                                      " co_doc="+txtNumDoc.getText()+" AND  co_reg="+strCodReg;
                                     pstModCabMovInv = conMod.prepareStatement(SQL); 
                                     pstModCabMovInv.executeUpdate(); 
                            }
                        }  
                    }   
                    //************************************************************************************************//
                    java.sql.Statement stm = conMod.createStatement();
                    java.sql.Statement stmLoc = conMod.createStatement();
                    java.sql.ResultSet rstLoc;
                    String sql ="", strSql="";
                    String strFechaPag="", strAplRet="";
                    int intDiaCre=0;   
                    double dlbPorRet=0;
                    double dblMon=0;
                    String strPorRet="";
                    int intConPorRet=0;
                    int intNumReg=1;
                    String strCodSri="";
                    for(int i=0; i<tblForPag.getRowCount(); i++){
                        intDiaCre = Integer.parseInt((tblForPag.getValueAt(i, 1)==null?"0":(tblForPag.getValueAt(i, 1).toString().equals("")?"0":tblForPag.getValueAt(i, 1).toString()))); 
                        dlbPorRet = objUti.redondear((tblForPag.getValueAt(i, 3)==null?"0":(tblForPag.getValueAt(i, 3).toString().equals("")?"0":tblForPag.getValueAt(i, 3).toString())), 2); 

                        strAplRet = (tblForPag.getValueAt(i, 8)==null?"":tblForPag.getValueAt(i, 8).toString()); 

                        strCodSri = (tblForPag.getValueAt(i, 7)==null?"":tblForPag.getValueAt(i, 7).toString());
                        if(!strCodSri.equals(""))
                            strCodSri=" and tx_codsri='"+strCodSri+"'";
                        else strCodSri="";

                        int FecPagDoc[] =  dtpFecDoc.getFecha(tblForPag.getValueAt(i, 2).toString());
                        strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];
                        dblMon= objUti.redondear((tblForPag.getValueAt(i, 4)==null?"0":(tblForPag.getValueAt(i, 4).toString().equals("")?"0":tblForPag.getValueAt(i, 4).toString())), 6 );


                        String strBasImp="0";

                       strBasImp = (tblForPag.getValueAt(i, 9)==null?"0":tblForPag.getValueAt(i, 9).toString());


                        if(dlbPorRet>0){
                            if(intConPorRet==1)  strPorRet += ",";
                            strPorRet +=  ""+dlbPorRet;
                            intConPorRet=1;

                            strSql="SELECT co_doc FROM tbm_pagmovinv WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
                            " AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtNumDoc.getText()+" " +
                            " AND nd_porret="+dlbPorRet+" "+strCodSri+" ";
                            System.out.println("--> "+strSql);
                            rstLoc=stmLoc.executeQuery(strSql);
                            if(rstLoc.next()){
                               sql = "UPDATE tbm_pagMovInv SET fe_ven='"+strFechaPag+"', mo_pag="+dblMon+", nd_basimp="+strBasImp+" "+
                               " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
                               " AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtNumDoc.getText()+" AND nd_porRet="+dlbPorRet+" "+strCodSri+" ";
                            }else{
                                 if(intNumReg==1) intNumReg += tblForPag.getRowCount();
                                 else  intNumReg++;

                                 sql="INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " +
                                 " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
                                 " VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+
                                 intNumDoc+", "+intNumReg+", "+((tblForPag.getValueAt(i, 1).equals(""))?"0":tblForPag.getValueAt(i, 1))+", "+
                                 "'"+strFechaPag+"', "+((tblForPag.getValueAt(i, 3)==null)?"0":tblForPag.getValueAt(i, 3))+", "+
                                 ""+objUti.redondeo(Double.parseDouble((tblForPag.getValueAt(i, 4)==null)?"0":tblForPag.getValueAt(i, 4).toString() ),6)+", "+
                                 ((tblForPag.getValueAt(i, 5)==null)?"0":tblForPag.getValueAt(i, 5).toString())+","+
                                 ((tblForPag.getValueAt(i, 6)==null)?"0":tblForPag.getValueAt(i, 6).toString())+",'"+
                                 ((tblForPag.getValueAt(i, 7)==null)?"":tblForPag.getValueAt(i, 7).toString())+"','"+
                                 ((tblForPag.getValueAt(i, 8)==null)?"":tblForPag.getValueAt(i, 8).toString())+"',"+
                                 strBasImp+",'I' )";
                             }
                            rstLoc.close();
                            rstLoc=null;

                            }else{
                            sql = "UPDATE tbm_pagMovInv SET fe_ven='"+strFechaPag+"', mo_pag="+dblMon+", nd_basimp="+strBasImp+" "+
                            " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
                            " AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtNumDoc.getText()+" AND ne_diaCre="+intDiaCre+" AND nd_porRet=0";
                          }
                         stm.executeUpdate(sql); 
                   }

                 strSql="DELETE FROM tbm_pagmovinv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
                 " AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtNumDoc.getText()+"  and nd_porret not in ( "+strPorRet+" ) and nd_porret > 0 ";
                 stmLoc.executeUpdate(strSql);

                 stmLoc.close();
                 stmLoc=null;

                stm.close();
                stm=null;

                if (!objAsiDia.actualizarDiario(conMod, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText()),txtNumOrdCom.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A")){
                    conMod.rollback();
                    conMod.close();
                    return false;
                }

                /**  Insertando el detalle de Diario.*/  

                 conMod.commit();
                 conMod.setAutoCommit(true);  
                 conMod.close();            
                 objTblMod.clearDataSavedBeforeRemoveRow();
                 objTblMod.initRowsState();                                        
                }    
          }catch(SQLException Evt){    
           conMod.rollback();
           conMod.close();
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
           return false;
          }
        }
        catch(SQLException Evt){    
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
           return false;
        }
        return true;
    }    
    
    /**
     * Metodo verificar que no este anulada
     * @autor: jayapata
     */
    private boolean isAnulada(){
        boolean blnRes = false;
        java.sql.Connection conTmp ;
        java.sql.Statement stmTmp;
        String strSQL = "";
        try{
         conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(conTmp!=null){
            stmTmp = conTmp.createStatement();
            strSQL = "";
            strSQL = " Select count(*) from tbm_cabmovinv ";
            strSQL += " where  co_emp = " + objParSis.getCodigoEmpresa() + " and  co_loc = " + objParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtNumDoc.getText() + " and st_reg = 'I' " ;
            if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
              blnRes = true;
       //      mostrarMsgInf("Documento anulado no se puede modificar");
             }else
                 blnRes = false;
             stmTmp.close();
             conTmp.close();
         }

       }catch(SQLException Evt){    
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
           blnRes = true;
       }catch(Exception Evt)  {
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
           blnRes = true;
       }
       return blnRes;
    }            
    
    
    private boolean anularReg(){
        boolean blnRes=false;
        try{ 
         java.sql.Connection conAnu =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                
         if(conAnu!=null){
          conAnu.setAutoCommit(false);                                                                                                                    

          if(anularCab(conAnu,"I")){
           if(anularDet(conAnu)){
            if(objAsiDia.anularDiario(conAnu,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText()))){
              conAnu.commit();
              blnRes=true;
           }else conAnu.rollback();
          }else conAnu.rollback();
         }else conAnu.rollback();
        conAnu.close();
        conAnu=null;
       }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
         catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
       return blnRes;
    }    

    private boolean anularCab(java.sql.Connection conAnu, String strEstReg){
        boolean blnRes=false;
        String strSql="";
        java.sql.Statement stm;
        try{
           strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
           stm=conAnu.createStatement();     
           strSql="UPDATE tbm_cabMovInv SET st_reg  ='"+strEstReg+"' ,fe_ultMod='"+strFecSis+"',co_usrMod="+objParSis.getCodigoUsuario()+",st_regrep='"+STR_ESTREG+"'"+
           " WHERE  co_emp="+objParSis.getCodigoEmpresa()+" AND  co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtNumDoc.getText();
            if (!txtNumCot.getText().equals(""))
             strSql += " ; Update tbm_cabcotcom set st_reg = 'A' where co_emp = " + objParSis.getCodigoEmpresa() +" and co_loc = "+ objParSis.getCodigoLocal() + " and co_cot = " + ((txtNumCot.getText().equals(""))?"0":txtNumCot.getText());

           stm.executeUpdate(strSql);
           blnRes=true;
       }catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
       return blnRes;
    }

    private boolean anularDet(java.sql.Connection conAnu){
        boolean blnRes=true;
        String strSql="";
        int intControl=0;
        double dlbCanMov=0;
        int intTipCli=-1;
        java.sql.Statement stmDet;
        java.sql.ResultSet rst;
        try{

           String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
           " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
           " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
           " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
           " THEN 'S' ELSE 'N' END AS proconf  ";

           strSql = "SELECT  a.co_itm, a.co_bod, sum(abs(a.nd_can)) as cantidad " +
           " "+strAux2+" FROM tbm_detmovinv as a " +
           " INNER JOIN tbm_inv as inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
           " WHERE a.co_Emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a.co_tipdoc="+txtCodTipDoc.getText()+" " +
           " and a.co_doc="+txtNumDoc.getText()+" and inv.st_ser='N' " +
           " GROUP BY a.co_itm,a.co_bod, inv.tx_codalt ";
           System.out.println(""+ strSql );
           stmDet=conAnu.createStatement();
           rst= stmDet.executeQuery(strSql);
           while(rst.next()){
              dlbCanMov=rst.getDouble("cantidad");
              int intCodItm=rst.getInt("co_itm");
              int intCodBod=rst.getInt("co_bod");
              String strEstFisBod=rst.getString("proconf");

              if(intControl!=0)    
                GLO_strArreItm=GLO_strArreItm+",";

                GLO_strArreItm=GLO_strArreItm+rst.getString("co_itm");
                intControl++;
                ZafStkInv objStkInv= new ZafStkInv(objParSis);
                if(objStkInv.actualizaInventario(conAnu, objParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK_DIS, "-", 1, getArlItmInv(conAnu, objParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                    if(!objStkInv.actualizaInventario(conAnu, objParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK, "-", 0, getArlItmInv(conAnu, objParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                        return false;
                    }
                    }else{
                       return false;
                    }
                }
           rst.close();
           rst=null;       
           stmDet.close();
           stmDet=null;

           intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodPrv.getText(),conAnu);

       }catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
         catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
       return blnRes;
    }
    
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{ 
         java.sql.Connection conAnu=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                
         if(conAnu!=null){
          conAnu.setAutoCommit(false);                                                                                                                    

          if(anularCab(conAnu,"E")){
           if(objAsiDia.anularDiario(conAnu,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText()))){
             blnRes=true;
           }else conAnu.rollback();
          }else conAnu.rollback();

          if(blnRes==true){
           if(!isAnulada()){   
             if(anularDet(conAnu)){
               conAnu.commit(); 
               blnRes=true;
             }else{  blnRes=false; conAnu.rollback(); }
           }else  conAnu.commit();    
          }    
         conAnu.close();
         conAnu=null;
        }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
          catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
    }    
        
    
    //************************************************************************************************************************//
    
    
    private ArrayList getArlItmInv(Connection cnx, int intCodEmp,int intCodItm, double dblCan, int intCodBod)
    {
        String strSql="";
        Statement stmSql=null;
        ResultSet rsItmInv=null;
        ArrayList lstItmInv=new ArrayList();
        ArrayList lstItm=new ArrayList();
        try{
            stmSql=cnx.createStatement();
            strSql=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n" +
                   "	CASE WHEN a1.nd_cosUni IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.nd_cosUni \n" +
                   "	END as nd_cosUni, \n" +
                   "        a1.nd_preVta1, a1.st_ivaVen, \n" +
                   "        CASE WHEN a1.tx_codAlt2 IS NULL THEN \n" +
                   "		'' \n" +
                   "	ELSE \n" +
                   "		a1.tx_codAlt2 \n" +
                   "	END as tx_codAlt2, \n" +
                   "	a2.co_itmMae, \n" +
                   "        CASE WHEN a1.co_uni IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.co_uni \n" +
                   "	END as co_uni, \n" +
                   "        CASE WHEN a1.nd_pesItmKgr IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.nd_pesItmKgr \n" +
                   "	END as nd_pesItmKgr , \n" +
                   "	GRU.co_itm as co_itmGru,a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor\n" +
                   " FROM tbm_inv as a1 \n" +
                   " INNER JOIN tbm_equInv as a2 \n" +
                   " ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                   " INNER JOIN tbm_equInv as GRU \n" +
                   " ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp=0) \n" +
                   " LEFT OUTER JOIN tbm_var as a3 \n" +
                   " ON (a1.co_uni=a3.co_reg) \n" +
                   " WHERE a1.co_emp="+intCodEmp+" and a1.st_reg='A' AND a1.co_itm="+intCodItm;
            rsItmInv=stmSql.executeQuery(strSql);
            while (rsItmInv.next()){
               lstItmInv.add(rsItmInv.getString("co_itmGru"));
               lstItmInv.add(rsItmInv.getString("co_itm"));
               lstItmInv.add(rsItmInv.getString("co_itmMae"));
               lstItmInv.add(rsItmInv.getString("tx_codAlt"));
               lstItmInv.add((Double.valueOf(dblCan<0?(dblCan*-1):dblCan)).toString());
               lstItmInv.add(Integer.valueOf(intCodBod).toString());
               lstItm.add(lstItmInv);
            }         
        }
        catch(Exception ex){
            ex.printStackTrace();     
        }
        return lstItm;
    }
    
    /**
     * verifica que el total de la orden de compra no sobrepase el marguen  establecido del
     * 0.5 % + -  con el valor nuevo
     * @return true : si esta dentro del marguen
     * @return false : si ha sobrepasado el marguen
     *
     */
    private boolean getVerificaMarPor(int intNumFilTbl )
    {
        boolean blnRes=true;
        double dblPorDesAct=0, dblPorDesOri=0;
        double dblCosUniAct=0, dblCosUniOri=0;
        double dblCosFinAct=0, dblCosFinOri=0;
        double dblMarDes=0;
        try{
            dblPorDesAct = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_DAT_POR_DES)), 5 );
            dblPorDesOri = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_DAT_POR_DES_ORI)), 5 );
            dblCosUniAct = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_DAT_PRE_COS)), 5 );
            dblCosUniOri = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_DAT_COS_UNI_ORI)), 5 );

            dblCosFinAct = ( dblCosUniAct - ( dblCosUniAct * (dblPorDesAct/100) ) );
            dblCosFinAct = objUti.redondear( dblCosFinAct , 5 );

            dblCosFinOri = ( dblCosUniOri - ( dblCosUniOri * (dblPorDesOri/100) ) );
            dblCosFinOri = objUti.redondear( dblCosFinOri , 5 );

            dblMarDes =  dblCosFinAct - dblCosFinOri;
            dblMarDes = objUti.redondear( dblMarDes , 5 );
            dblMarDes = ( (dblMarDes*100)/dblCosFinOri);
            dblMarDes = objUti.redondear( dblMarDes , 2 );

            System.out.println( "--> "+dblMarDes +"  "+ dblPorDesOc);

            if(dblMarDes > 0 ){
             dblMarDes = Math.abs( dblMarDes );
             if( dblMarDes > dblPorDesOc )  blnRes=false;
            }
        }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private void getPorDesOC(Connection conTmp){
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            if (conTmp!=null){
                stmTmp=conTmp.createStatement();
                String sSQL=" SELECT nd_maxporvarperordcomfacprv FROM tbm_loc WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" ";
                rstTmp = stmTmp.executeQuery(sSQL);
                if(rstTmp.next()){
                  dblPorDesOc=rstTmp.getDouble("nd_maxporvarperordcomfacprv");
                }
                rstTmp.close();
                rstTmp=null;
                stmTmp.close();
                stmTmp=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }

    private void cargarDatCot(){
        java.sql.Connection conTmp; 
        java.sql.Statement stmTmp;
        java.sql.ResultSet rstTmp;
        String strSql="";
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conTmp !=null){
                stmTmp = conTmp.createStatement();                                
                //Armar sentencia SQL
                strSql =" SELECT cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli" + //<== Campos con los datos del CLiente para la cabecera
                        "      , Usr.co_usr as co_com, Usr.tx_nom as nomcom,  CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        "      , CotCab.co_forPag, CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        "      , CotCab.st_reg ,Cli.co_tipper, Cli.st_ivacom FROM tbm_cabCotCom as CotCab " +
                        " LEFT OUTER JOIN tbm_cli AS Cli ON (cotcab.co_emp = cli.co_emp AND cotcab.co_prv = cli.co_cli) "+
                        " LEFT OUTER JOIN tbm_usr as Usr on (Usr.co_usr = CotCab.co_com ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " WHERE CotCab.co_cot = " + txtNumCot.getText() +
                        " AND CotCab.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                        " AND CotCab.co_loc = " + objParSis.getCodigoLocal()+" " +
                        " ORDER BY CotCab.co_cot";
                rstTmp = stmTmp.executeQuery(strSql);      
                if(rstTmp.next()){
                    if(rstTmp.getString("st_reg").equals("A"))
                        refrescaDatosCot(conTmp, rstTmp);
                    else
                        mostrarMsgInf("Cotizacion ya fue generada.");
                }
                else{
                    txtNumCot.setText("");                                    
                }
                rstTmp.close();
                rstTmp=null;
                stmTmp.close();  
                stmTmp=null;
                conTmp.close();
                conTmp=null;
            }
        }
        catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }

    /**
     * Carga datos del comprador enlazado al proveedor.
     */
    private void cargarCompradorProveedor()
    {
        try
        {
            Connection conComPrv = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conComPrv!=null)
            {
                Statement stmComPrv = conComPrv.createStatement();

                //Armar sentencia SQL
                String sSQL = "select co_ven from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " and co_cli = " + Integer.parseInt(txtCodPrv.getText()); 
                ResultSet rstComPrv = stmComPrv.executeQuery(sSQL);

                if(rstComPrv.next())
                {
                    int intCodVen  = rstComPrv.getInt("co_ven");
                    Librerias.ZafConsulta.ZafConsulta objFndComPrv = 
                        new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent( this),
                        "Codigo,Nombre","co_usr,tx_nom", "Select co_usr,tx_nom from tbm_usr  ", ""
                         , objParSis.getStringConexion() 
                         , objParSis.getUsuarioBaseDatos()
                         , objParSis.getClaveBaseDatos()
                        );
                    objFndComPrv.setTitle("Listado compradores");

                    if(objFndComPrv.buscar("co_usr = " + intCodVen )){
                        txtCodCom.setText(objFndComPrv.GetCamSel(1));
                        txtNomCom.setText(objFndComPrv.GetCamSel(2));
                    }    
                }
                rstComPrv.close();
                rstComPrv=null;
                stmComPrv.close();
                stmComPrv=null;
                conComPrv.close();
                conComPrv=null;
            }
        }
        catch(SQLException Evt)  {
               objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt)  {
               objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
    }
                    
    private void cargaNum_Doc_OrdCom(){        
        int intNumeroDoc = objUltDocPrint.getUltDoc(Integer.parseInt(txtCodTipDoc.getText()));
        intNumeroDoc=(intNumeroDoc+1);
        txtNumOrdCom.setText("" + intNumeroDoc); 
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
        String strRutRpt, strNomRpt;
        String strRutImgLogo = " ";
        int i, intNumTotRpt;
        boolean blnRes=true;

        try
        {
            Map mapPar=new HashMap();
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            case 28: // Ordenes de Compra (Cosenco)
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                
                                //Inicializar los parametros que se van a pasar al reporte.                                
                                mapPar.put("codEmp", new Integer( objParSis.getCodigoEmpresa() ) );
                                mapPar.put("codLoc", new Integer( objParSis.getCodigoLocal() ) );
                                mapPar.put("CodTipDoc", new Integer( txtCodTipDoc.getText() ) );
                                mapPar.put("codDoc", new Integer( txtNumDoc.getText() ) );
                                    
                                //mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                            default: // Rose: Grupo LSC 
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                strRutImgLogo=objRptSis.getRutaReporte(i);
                                if(objParSis.getCodigoEmpresa()==1) {
                                    strRutImgLogo+="Logos/logTuv.png";
                                }
                                else if(objParSis.getCodigoEmpresa()==2){
                                    strRutImgLogo+="Logos/logCas.png";
                                }
                                else{
                                    strRutImgLogo+="Logos/logDim.png";
                                }
                                
                                mapPar.put("codEmp", new Integer( objParSis.getCodigoEmpresa() ) );
                                mapPar.put("codLoc", new Integer( objParSis.getCodigoLocal() ) );
                                mapPar.put("CodTipDoc", new Integer( txtCodTipDoc.getText() ) );
                                mapPar.put("codDoc", new Integer( txtNumDoc.getText() ) );
                                mapPar.put("RUTA_LOGO", strRutImgLogo );
                                
                                //mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
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
    
    
    private void recostearItmGrp()
    {
        int i,j;
        try
        {    
            java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                   for(i=0; i<tblDat.getRowCount(); i++){  
                    if(tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM) != null ){ 
                            objUti.recostearItm2009DesdeFecha( this, objParSis, con,  objParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_DAT_COD_ITM).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                   }}
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    }
          
    /**
     * verifica que el total de la orden de compra no sobrepase el marguen  establecido del
     * 0.5 % + -  con el valor nuevo   
     * @return true : si esta dentro del marguen
     * @return false : si ha sobrepasado el marguen
     * 
     */
    private boolean verificaMarTot(){
        boolean blnRes=false;
        try{
        double dlbTotOrd = objUti.redondear( (txtTot.getText().equals("")?"0":txtTot.getText()) , objParSis.getDecimalesMostrar() );
        dlbTotOrd = dlbTotOrd -  dlbValorOriTotOrd;
        if(dlbTotOrd > 0 ){
        double dlbPor = objUti.redondear(  ((dlbTotOrd*100)/dlbValorOriTotOrd)  , 2);
        dlbPor = Math.abs( dlbPor );

         if( dlbPor <= 0.50 )
             blnRes=true;
        }else  blnRes=true;

        }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;   
    }

    private boolean verificaRecFacPrv() {
        boolean blnRes=true;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSql="";
        try{
            con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSql=" SELECT co_reg  " +
                "  FROM tbm_pagmovinv  WHERE  co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND " +
                " co_tipDoc = "+txtCodTipDoc.getText()+" AND co_doc = "+txtNumDoc.getText()+"  and nd_monchq > 0  ";
                rst=stm.executeQuery(strSql);
                if(rst.next()){
                  blnRes=false;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean verificaNumPag(){
        boolean blnRes=false;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSql="";
        int intNumPag=0;
        int intSumDiaCreOri=0;
        int intSumDiaCrePag=0;
        try{
            con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
              stm=con.createStatement();

              intNumPag=tblForPag.getRowCount();

              strSql=" SELECT count(co_reg) as numpag , sum(ne_diacre) as diascre " +
              "  FROM tbm_pagmovinv  WHERE  co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND " +
              " co_tipDoc = "+txtCodTipDoc.getText()+" AND co_doc = "+txtNumDoc.getText();   
              rst=stm.executeQuery(strSql);
              if(rst.next()){
                 intSumDiaCreOri= rst.getInt("diascre");
                 if(intNumPag==rst.getInt("numpag"))
                  blnRes=true;

              }
             rst.close();
             rst=null;
             stm.close();
             stm=null;
             con.close();
             con=null;

             if(!blnRes){
                return false;
             }

             for(int i=0; i<tblForPag.getRowCount(); i++){
                intSumDiaCrePag += Integer.parseInt((tblForPag.getValueAt(i, 1)==null?"0":(tblForPag.getValueAt(i, 1).toString().equals("")?"0":tblForPag.getValueAt(i, 1).toString()))); 
             }
             if(intSumDiaCreOri==intSumDiaCrePag)
               blnRes=true;
               else  blnRes=false;

             System.out.println("DiaCre: "+ intSumDiaCreOri +" -- "+ intSumDiaCrePag );

         }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }   
    
    private boolean verificaItmUni(){
        boolean  blnRes=true;
        try{
            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM) != null){
                   int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()));
                   int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_DAT_COD_ITMACT).toString()));
                  // if(tblDat.getValueAt(i, INT_TBL_DAT_EST_REG).equals("M")){
                       if(intCodItm != intCodItmAct){
                           mostrarMsgInf("El Item "+tblDat.getValueAt(i, INT_TBL_DAT_ITM_ALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                           blnRes=false;
                           break;
                       }
                  // }
                }
            }
        }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
       return blnRes;     
    }
    
    private void Actualizar_valores(){
        java.sql.Connection conTipDoc;
        java.sql.PreparedStatement pstReg;
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                String sql="UPDATE tbm_cabmovinv set nd_sub="+txtSub.getText()+", nd_valiva="+txtIva.getText()+",nd_tot="+txtTot.getText()+" where co_emp="+objParSis.getCodigoEmpresa()+"" +
                " and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+txtNumDoc.getText();
                pstReg = conTipDoc.prepareStatement(sql);
                pstReg.executeUpdate();
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
    }
    
    /**
       * Aqui se verifica si el proveedor estan dado de alto por el respectivas personas
       * @param conn conecion de la base
       * @return true: si esta todo bien
       * @return false: si necesita ser dado de alta 
    */        
    private boolean verificaEstCliPrv(java.sql.Connection conn){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strMesj="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                strSql="SELECT tx_nom FROM TBM_CLI WHERE CO_EMP="+objParSis.getCodigoEmpresa()+" AND CO_CLI="+txtCodPrv.getText()+" and ST_REG='N'";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strMesj+="<html>LOS DATOS DEL PROVEEDOR: <FONT COLOR=\"blue\"> "+rstLoc.getString("tx_nom")+"</font>  TIENE QUE SER REVISADO.</html>\n";
                    blnRes=false;
                }
                if(!blnRes){
                   mostrarMsgOK(strMesj);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }       
        
      

    
}
