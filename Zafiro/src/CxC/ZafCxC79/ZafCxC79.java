/* >> Archivo: ZafCxC79.java
 * >> Cobros de Tarjetas de crédito.
 * >> Autor: Omar Gutierrez.
 * Created on NOV 15, 2012, 08:30:18 AM
 =====================================================================*/
package CxC.ZafCxC79;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafAjuCenAut.ZafAjuCenAut;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import ZafReglas.ZafMetImp;
import java.sql.Connection;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

// Clase Principal
public class ZafCxC79 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable 
    private static final int INT_TBL_LINEA = 0;                                 // NUMERO DE LINEAS
    private static final int INT_TBL_BUTCLI = 1;                                // BUTON PARA BUSCAR DOCUMENTO
    private static final int INT_TBL_CHKSEL = 2;                                // SELECCION  DE FILA
    private static final int INT_TBL_CODCLI = 3;                                // CODIGO CLIENTE
    private static final int INT_TBL_NOMCLI = 4;                                // NOMBRE CLIENTE
    private static final int INT_TBL_CODEMP = 5;                                // CODIGO EMPRESA
    private static final int INT_TBL_CODLOC = 6;                                // CODIGO DEL LOCAL
    private static final int INT_TBL_CODTID = 7;                                // CODIGO TIPO DE DOCUMENTO
    private static final int INT_TBL_CODDOC = 8;                                // CODIGO DOCUMENTAL
    private static final int INT_TBL_CODREG = 9;                                // CODIGO REGISTRO
    private static final int INT_TBL_DCTIPDOC = 10;                             // DESCRIPCION CORTA TIPO DOCUMENTO
    private static final int INT_TBL_DLTIPDOC = 11;                             // DESCRIPCION LARGA TIPO DOCUMENTO
    private static final int INT_TBL_NUMDOC = 12;                               // NUMERO DOCUMENTO
    private static final int INT_TBL_FECDOC = 13;                               // FECHA DOCUMENTO
    private static final int INT_TBL_DIACRE = 14;                               // DIA DE CREDITO
    private static final int INT_TBL_FECVEN = 15;                               // FECHA VENCIMIENTO DOCUMENTO
    private static final int INT_TBL_PORRET = 16;                               // PORCENTAJE DE RETENCION
    private static final int INT_TBL_VALDOC = 17;                               // VALOR DOCUMENTO
    private static final int INT_TBL_VALPEN = 18;                               // VALOR PENDIENTE
    private static final int INT_TBL_NUMTRA = 19;                               // INGRESO NUMERO DE TRANSACCION.
    private static final int INT_TBL_NUMAUT = 20;                               // INGRESO DE NUMERO DE AUTORIZACION.
    private static final int INT_TBL_ABONO = 21;                                // ABONO
    private static final int INT_TBL_CODREGEFE = 22;                            // CODIGO REGISTRO DE PAGO
    private static final int INT_TBL_ABONOORI = 23;                             // ABONO ORIGEN
    private static final int INT_TBL_BUTFAC = 24;                               // MUESTRA EL DOCUMENTO FACTURA.
    private static final int INT_TBL_TIPCRE = 25;                               // MUESTRA EL TIPO DE CREDITO.
    
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODREG = 0;
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;    
    
    //Variables
    private Connection con_Glo, conCab ;
    private Statement stm_Glo;
    private ResultSet rst_Glo;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafDocLis objDocLis;
    private ZafDatePicker dtpFecDoc;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoFacRet;
    private ZafTblCelEdiButGen objTblCelEdiButGen;                              //Editor: JButton en celda.
    private ZafAsiDia objAsiDia, objAsiDia2;                                    //Asiento de Diario
    private ZafAjuCenAut objAjuCenAut;
    private ZafTblPopMnu objTblPopMnu;
    private java.util.Date datFecAux;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;
    private ZafCxC79_02 objLiberaGuia;
    private MiToolBar objTooBar;
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoFac;

    private ZafCxC79_03 objZafCxC79_03;
    
    private Vector vecDat, vecCab, vecReg, vecOD;
    private Vector vecDetDiario;
    
    //Constante IVA
    private double dblPorIva=0.00;                                              //Porcentaje del IVA. Ej.:12.00
    private double dblFacIva=0.00;                                              //Factor Iva.
    
    int INT_ENV_REC_IMP_GUIA = 0;
    int intCodMnuDocIng = 0;
    int intTipModDoc = 0;
    double dblMinAjuCenAut = 0;
    double dblMaxAjuCenAut = 0;
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
   
    boolean blnHayCam = false;
    boolean blnConVenEme = false;
    boolean blnExiDifCli = false;
    
    private String strSQL, strAux;    
    
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private String strSqlTipDocAux = "";
    private String strCodCtaCli = "", strTxtCodCtaCli = "", strNomCtaCli = "";
    private String strCodCtaEfe = "", strTxtCodCtaEfe="", strNomCtaEfe = "";

    private String strVer = " v1.17 ";
    
    /** Crea una nueva instancia de la clase ZafCxC79. */
    public ZafCxC79(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    

    //Constructor2 - Ventana Consulta Cobro TC desde otros programas.
    public ZafCxC79(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu) 
    {
        try 
        {
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoEmpresa(codigoEmpresa);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);
            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);            
            blnConVenEme=true;    
            
            initComponents();
            configurarFrm();
            agregarDocLis();
            
            lblTit.setText("Cobros de tarjeta de crédito");
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);       
        } 
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            this.setTitle(objParSis.getNombreMenu() + " " + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            
            //Inicializar objetos
            objUti = new ZafUtil();
            objTooBar = new MiToolBar(this);
            panBar.add(objTooBar);
            
            objLiberaGuia = new ZafCxC79_02(this, objParSis);
            
            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAjuCenAut = new Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objParSis);

            objAsiDia = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });

            objAsiDia2 = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia2.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                    {
                        objAsiDia2.setCodigoTipoDocumento(-1);
                    }
                    else
                    {
                        //Eddye: Parche temporal hasta que se mejore el código.
                        int intCodTipDocCXCTC=0;
                        if (txtDesCorTipDoc.getText().equals("COBTCM")) {
                            intCodTipDocCXCTC=196;
                        }
                        else if (txtDesCorTipDoc.getText().equals("COBTCD"))  {
                            intCodTipDocCXCTC=217;
                        }
                        objAsiDia2.setCodigoTipoDocumento(intCodTipDocCXCTC);
                    }
                }
            });

            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(575, 4, 100, 20);   
            
            //Iva
            objParSis.cargarConfiguracionImpuestos();
            BigDecimal bdgIva=objParSis.getPorcentajeIvaVentas();
            dblPorIva= bdgIva.doubleValue();
            dblFacIva=objUti.redondear(((dblPorIva/100)+1.00), objParSis.getDecimalesMostrar());  //Ej.:1.12
            
            //Vector de ordenes de despacho.
            vecOD=new Vector();
            
            configurarVenConTipDoc();
            configurarVenConDocAbi();
            cargarRangoAjuCenAut();
            configurarVenConFac();
            
            configurarTblDat();
        }
        catch(Exception e){
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
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }     
    
    /**
     * Configuracion de la tabla para el detalle de los datos del cobro con
     * targeta de credito.
     */
    private boolean configurarTblDat() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();     //Almacena los datos
            vecCab=new Vector();     //Almacena las cabeceras
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_BUTCLI, "");
            vecCab.add(INT_TBL_CHKSEL, "");
            vecCab.add(INT_TBL_CODCLI, "Cód.Cli.");
            vecCab.add(INT_TBL_NOMCLI, "Cliente");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp");
            vecCab.add(INT_TBL_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTID, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_CODREG, "Cód.Reg.");
            vecCab.add(INT_TBL_DCTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DLTIPDOC, "Tipo de documento");
            vecCab.add(INT_TBL_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DIACRE, "Dia.Cre");
            vecCab.add(INT_TBL_FECVEN, "Fec.ven");
            vecCab.add(INT_TBL_PORRET, "Por.Ret.");
            vecCab.add(INT_TBL_VALDOC, "Val.Doc.");
            vecCab.add(INT_TBL_VALPEN, "Val.Pen.");
            vecCab.add(INT_TBL_NUMTRA, "Núm.Tra."); // INGRESO NUMERO DE TRANSACCION.
            vecCab.add(INT_TBL_NUMAUT, "Núm.Aut."); // INGRESO DE NUMERO DE AUTORIZACION.
            vecCab.add(INT_TBL_ABONO, "Val.Abo.");
            vecCab.add(INT_TBL_CODREGEFE, "");
            vecCab.add(INT_TBL_ABONOORI, "");
            vecCab.add(INT_TBL_BUTFAC, "..");
            vecCab.add(INT_TBL_TIPCRE, "Tip.Cre.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            objTblMod.setColumnDataType(INT_TBL_DIACRE, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORRET, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            // Setiamos los colores de los campos Num.Tra, Num.Aut
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_NUMTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMAUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTID, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODREG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODREGEFE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_ABONOORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DIACRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_FECVEN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_PORRET, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_TIPCRE, tblDat); // TC  José Marín M.  12/Marzo/2014
            
            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            java.util.ArrayList arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODREGEFE);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux = null;

            //Vector que contiene los campos editable de la tabla.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKSEL);
            vecAux.add("" + INT_TBL_BUTCLI);

            ZafPerUsr objZafPerUsr=new ZafPerUsr(objParSis);
            if(objZafPerUsr.isOpcionEnabled(3979)){
                vecAux.add("" + INT_TBL_ABONO);//-> Despues se inplementará
            }
            vecAux.add("" + INT_TBL_NUMDOC);
            vecAux.add("" + INT_TBL_BUTFAC);
            vecAux.add("" + INT_TBL_NUMTRA); // Campo editable para el numero de Transaccion
            vecAux.add("" + INT_TBL_NUMAUT);//Campo editable para el en campo de la autorizacion.
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;


            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                    if ((strCodCli.equals(""))) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")) {

                        if (tblDat.getValueAt(intNunFil, INT_TBL_CODREGEFE) == null) {
                            double dblValPen = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);

                            if((((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null) || ((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)).length() == 0) || (((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)!=null) && ((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO)).length()> 0)&& Double.parseDouble((String)tblDat.getValueAt(intNunFil, INT_TBL_ABONO))==0){
                                dblValPen = Math.abs(dblValPen);
                                tblDat.setValueAt("" + dblValPen, intNunFil, INT_TBL_ABONO);
                            }

                        } else {
                            tblDat.setValueAt(tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                        }
                    } else {
                        tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);

                    }
                    calculaTotMonAbo();
                }
            });

            //Codigo para el campo editable del Bono
            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
             tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);

             objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                 Double bgValAboBef=0.00;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    //System.out.println("ll "+tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE));
                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    bgValAboBef=new Double(((String)tblDat.getValueAt(intNumFil, INT_TBL_ABONO)).equals("")?"0.0":(String)tblDat.getValueAt(intNumFil, INT_TBL_ABONO));

                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    double dblValApl = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
                    double dblValPen = objUti.redondear((tblDat.getValueAt(intNunFil, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

                    dblValPen = Math.abs(dblValPen);


                    if (dblValApl != 0) {
                        tblDat.setValueAt(new Boolean(true), intNunFil, INT_TBL_CHKSEL);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_CHKSEL);
                    }

                    if (!(tblDat.getValueAt(intNunFil, INT_TBL_CODREGEFE) == null)) {
                        tblDat.setValueAt(bgValAboBef.toString(), intNunFil, INT_TBL_ABONO);
                    }


                    calculaTotMonAbo();
                }
             });

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NUMTRA).setCellEditor(objTblCelEdiTxt);


            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    Integer dblValNumTra = Integer.parseInt(tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_NUMTRA).toString()));
                    if (dblValNumTra >= 0) {
                        tblDat.setValueAt(dblValNumTra, intNunFil, INT_TBL_NUMTRA);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_NUMTRA);
                    }

                }
            });

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellEditor(objTblCelEdiTxt);


            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("") ? "" : tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    if ((strCodCli.equals(""))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNunFil = tblDat.getSelectedRow();

                    Integer dblValNumAut = Integer.parseInt(tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT) == null ? "0" : (tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT).equals("") ? "0" : tblDat.getValueAt(intNunFil, INT_TBL_NUMAUT).toString()));
                    if (dblValNumAut >= 0) {
                        tblDat.setValueAt(dblValNumAut, intNunFil, INT_TBL_NUMAUT);
                    } else {
                        tblDat.setValueAt(new Boolean(false), intNunFil, INT_TBL_NUMAUT);
                    }
                }
            });

            int intColFac[] = new int[19];
            intColFac[0] = 1;
            intColFac[1] = 2;
            intColFac[2] = 3;
            intColFac[3] = 4;
            intColFac[4] = 5;
            intColFac[5] = 8;
            intColFac[6] = 9;
            intColFac[7] = 11;
            intColFac[8] = 10;
            intColFac[9] = 6;
            intColFac[10] = 7;
            intColFac[11] = 12;
            intColFac[12] = 14;
            intColFac[13] = 13;
            intColFac[14] = 15;
            intColFac[15] = 17;

            // intColFac[16]=16;
            int intColTblFac[] = new int[19];
            intColTblFac[0] = INT_TBL_CODCLI;
            intColTblFac[1] = INT_TBL_NOMCLI;
            intColTblFac[2] = INT_TBL_CODEMP;
            intColTblFac[3] = INT_TBL_CODLOC;
            intColTblFac[4] = INT_TBL_CODTID;
            intColTblFac[5] = INT_TBL_CODDOC;
            intColTblFac[6] = INT_TBL_CODREG;
            intColTblFac[7] = INT_TBL_FECDOC;
            intColTblFac[8] = INT_TBL_NUMDOC;
            intColTblFac[9] = INT_TBL_DCTIPDOC;
            intColTblFac[10] = INT_TBL_DLTIPDOC;
            intColTblFac[11] = INT_TBL_DIACRE;
            intColTblFac[12] = INT_TBL_PORRET;
            intColTblFac[13] = INT_TBL_FECVEN;
            intColTblFac[14] = INT_TBL_VALDOC;
            intColTblFac[15] = INT_TBL_VALPEN;


            objTblCelEdiTxtVcoFacRet = new ZafTblCelEdiTxtVco(tblDat, vcoFac, intColFac, intColTblFac);
            tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
            objTblCelEdiTxtVcoFacRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (!(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE) == null)) {
                        objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
                    }
                }

                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    vcoFac.setCampoBusqueda(9);
                    vcoFac.setCriterio1(11);
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (tblDat.getValueAt(intNumFil, INT_TBL_CODLOC) == null) {
                        objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
                    } else {

                        if (!_getVerificaExistenciaVenCon(intNumFil, tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString(),
                                tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString())) {
                            objTblMod.removeRow(intNumFil);
                        }
                    }

                    /*double dblValAbo = objUti.redondear((tblDat.getValueAt(intNumFil, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(intNumFil, INT_TBL_ABONO).equals("") ? "0" : tblDat.getValueAt(intNumFil, INT_TBL_ABONO).toString())), 4);
                     if (dblValAbo != 0) {
                     calculaTotMonAbo();
                     }*/

                }
            });

            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            //------------------------------------------------------------------
            //Eddye_ventana de documentos pendientes//
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODREGEFE) == null)) {

                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli, strValDoc;
                        double dblValDoc = 0;
                        if (tblDat.getSelectedColumn() == INT_TBL_BUTCLI) {
                            objZafCxC79_03.setVisible(true);
                        }
                        if (objZafCxC79_03.getSelectedButton() == objZafCxC79_03.INT_BUT_ACE) {
                            intFilSel = tblDat.getSelectedRow();
                            intFilSelVenCon = objZafCxC79_03.getFilasSeleccionadas();
                            strCodCli = objZafCxC79_03.getCodigoCliente();
                            strNomCli = objZafCxC79_03.getNombreCliente();
                            j = intFilSel;
                            for (i = 0; i < intFilSelVenCon.length; i++) {
                                if (objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_LIN) != "P") {


                                    strValDoc = objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_CHQ);
                                    strValDoc = (strValDoc == null ? "0" : (strValDoc.equals("") ? "0" : strValDoc));
                                    dblValDoc = objUti.redondear(strValDoc, 4);
                                    if (!_getVerificaExistencia(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_LOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_TIP_DOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_DOC).toString(),
                                            objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_REG).toString()))
                                    {

                                    }
                                    else
                                    {
                                        objTblMod.insertRow(j);
                                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                                        objTblMod.setValueAt("" + objParSis.getCodigoEmpresa(), j, INT_TBL_CODEMP);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);
                                        objTblMod.setValueAt(objZafCxC79_03.getValueAt(intFilSelVenCon[i], objZafCxC79_03.INT_TBL_DAT_TIP_CRE), j, INT_TBL_TIPCRE); //TC José Marín 12/Marzo/2014
                                        objZafCxC79_03.setFilaProcesada(intFilSelVenCon[i]);
                                    }
                                    j++;
                                }
                            }
                            tblDat.requestFocus();
                        }
                    }
                }
            });
            //------------------------------------------------------------------

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenButCotEmi);
            objTblCelRenButCotEmi = null;
            new ButFac(tblDat, INT_TBL_BUTFAC);   //*****
            setEditable(false);

            objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            // objTblPopMnu.setEliminarFilaVisible(false);
            objTblPopMnu.setInsertarFilasVisible(false);
            objTblPopMnu.setInsertarFilaVisible(false);

            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()) {
                        if (!(objParSis.getCodigoUsuario() == 1)) {
                            if (!verificaSelEli()) {
                                mostrarMsgInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                                objTblPopMnu.cancelarClick();
                            }
                        }
                    }
                }

                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                    }
                    else if (objTblPopMnu.isClickEliminarFila())
                    {
                        //javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                        calculaTotMonAbo();
                    }
                }
            });
            tcmAux = null;

            blnRes = true;
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private class ButFac extends Librerias.ZafTableColBut.ZafTableColBut_uni {
        public ButFac(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx, "Factura Comercial.");
        }
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            if (tblDat.getValueAt(intCol, INT_TBL_CODCLI) != null) {
                String strCodEmp = tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString();
                String strCodLoc = tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString();
                String strCodTipDoc = tblDat.getValueAt(intCol, INT_TBL_CODTID).toString();
                String strCodDoc = tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString();

                llamarVenFac(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            }
        }
    }

    private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 14);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }  
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODCLI:
                    strMsg = "Código de cliente";
                    break;
                case INT_TBL_NOMCLI:
                    strMsg = "Nombre del cliente";
                    break;
                case INT_TBL_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_FECVEN:
                    strMsg = "Fecha de vencimiento del crédito";
                    break;
                case INT_TBL_DIACRE:
                    strMsg = "Día de crédito";
                    break;
                case INT_TBL_PORRET:
                    strMsg = "Porcentaje de retención";
                    break;
                case INT_TBL_DCTIPDOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DLTIPDOC:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_VALDOC:
                    strMsg = "Valor del documento";
                    break;
                case INT_TBL_VALPEN:
                    strMsg = "Valor pendiente";
                    break;
                case INT_TBL_NUMTRA:
                    strMsg = "Número de transacción";
                    break;
                case INT_TBL_NUMAUT:
                    strMsg = "Número de autorización";
                    break;
                case INT_TBL_ABONO:
                    strMsg = "Valor abonado";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean configurarVenConTipDoc()
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.ne_tipresmoddoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Tip.Mod.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("360");
            arlAncCol.add("20");
            //Armar la sentencia SQL.
            String Str_Sql = "";
            if (objParSis.getCodigoUsuario() == 1)
            {
                Str_Sql = "SELECT * FROM ( Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar, 3 as ne_tipresmoddoc  from tbr_tipdocprg as b "
                        + " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                        + " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                        + " b.co_loc = " + objParSis.getCodigoLocal() + " and "
                        + " b.co_mnu = " + objParSis.getCodigoMenu() + " ) AS a ";
            }
            else
            {
                Str_Sql = "SELECT * FROM ( SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a1.ne_tipresmoddoc  "
                        + " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                        + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + ""
                        + " AND a1.co_loc=" + objParSis.getCodigoLocal() + ""
                        + " AND a1.co_mnu=" + objParSis.getCodigoMenu() + ""
                        + " AND a1.co_usr=" + objParSis.getCodigoUsuario() + " ) AS a  ";
            }
            strSqlTipDocAux = " SELECT co_tipdoc FROM (" + Str_Sql + " ) AS x ";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConFac() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nomcli");
            arlCam.add("a.co_emp");
            arlCam.add("a.co_loc");
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_doc");
            arlCam.add("a.co_reg");
            arlCam.add("a.ne_numDoc");
            arlCam.add("a.fe_doc");
            arlCam.add("a.ne_diaCre");
            arlCam.add("a.fe_ven");
            arlCam.add("a.nd_porRet");
            arlCam.add("a.mo_pag");
            arlCam.add("a.nd_abo");
            arlCam.add("a.nd_pen");
            arlCam.add("a.st_sop");
            arlCam.add("a.st_entSop");
            arlCam.add("a.st_pos");
            arlCam.add("a.co_banChq");
            arlCam.add("a.a4_tx_desLar");
            arlCam.add("a.tx_numCtaChq");
            arlCam.add("a.tx_numChq");
            arlCam.add("a.fe_recChq");
            arlCam.add("a.fe_venChq");
            arlCam.add("a.nd_monChq");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Cli");
            arlAli.add("Nom.Cli");
            arlAli.add("Cód.Emp");
            arlAli.add("Cód.Loc");
            arlAli.add("Cód.TipDoc");
            arlAli.add("DesCor");
            arlAli.add("tx_desLar");
            arlAli.add("co_doc");
            arlAli.add("co_reg");
            arlAli.add("ne_numDoc");
            arlAli.add("fe_doc");
            arlAli.add("ne_diaCre");
            arlAli.add("fe_ven");
            arlAli.add("nd_porRet");
            arlAli.add("mo_pag");
            arlAli.add("nd_abo");
            arlAli.add("nd_pen");
            arlAli.add("st_sop");
            arlAli.add("st_entSop");
            arlAli.add("st_pos");
            arlAli.add("co_banChq");
            arlAli.add("a4_tx_desLar");
            arlAli.add("tx_numCtaChq");
            arlAli.add("tx_numChq");
            arlAli.add("fe_recChq");
            arlAli.add("fe_venChq");
            arlAli.add("nd_monChq");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("55");
            arlAncCol.add("150");
            arlAncCol.add("60");
            arlAncCol.add("50");
            arlAncCol.add("10");
            arlAncCol.add("70");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("75");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("60");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");

            int intColOcu[] = new int[20];
            intColOcu[0] = 3;
            intColOcu[1] = 5;
            intColOcu[2] = 7;
            intColOcu[3] = 8;
            intColOcu[4] = 9;
            intColOcu[5] = 13;
            intColOcu[6] = 15;
            intColOcu[7] = 16;
            intColOcu[8] = 17;
            intColOcu[9] = 18;
            intColOcu[10] = 19;
            intColOcu[11] = 20;
            intColOcu[12] = 21;
            intColOcu[13] = 22;
            intColOcu[14] = 23;
            intColOcu[15] = 24;
            intColOcu[16] = 25;
            intColOcu[17] = 26;
            intColOcu[18] = 27;
            intColOcu[19] = 12;

            //Armar la sentencia SQL.
            String strSQL;

            String strAux = "";
            if (!(objParSis.getCodigoUsuario() == 1)) {

                if (objParSis.getCodigoUsuario() == 89) {
                    if (objParSis.getCodigoEmpresa() == 1) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",5) ";
                    }
                    if (objParSis.getCodigoEmpresa() == 2) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",5) ";
                    }
                    if (objParSis.getCodigoEmpresa() == 4) {
                        strAux = " and a1.co_loc in (" + objParSis.getCodigoLocal() + ",2) ";
                    }
                } else {
                    strAux = " and a1.co_loc=" + objParSis.getCodigoLocal() + " ";
                }

            }

            String strAuxFil = "";
            strAuxFil = " AND a3.ne_mod IN (1, 3)  AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0) ";

            strSQL = " SELECT * FROM ( "
                   + "      SELECT a1.co_cli, a1.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc"
                   + "           , a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo "
                   + "           , (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq "
                   + "      FROM  tbm_cabMovInv AS a1  "
                   + "      INNER JOIN tbm_cli AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli) "
                   + "      INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "
                   + "      INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  "
                   + "      LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) "
                   + "      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "   " + strAux + " " + "AND a1.co_forpag = 96 AND a3.co_tipdoc = 1"
                   + "      AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')  " + strAuxFil + " "
                   + "      AND ( a2.tx_numchq IS NULL OR a2.tx_numchq='')   AND (a2.mo_pag+a2.nd_abo)<>0 "
                   + " ) AS a ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg ";

            vcoFac = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoFac.setConfiguracionColumna(14, javax.swing.JLabel.RIGHT, vcoFac.INT_FOR_NUM, objParSis.getFormatoNumero(), false, true);


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConDocAbi() {
        boolean blnRes = true;
        try {
            objZafCxC79_03 = new ZafCxC79_03(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de documentos abiertos TC", true,this);

            objZafCxC79_03.setTipoConsulta(1);
            objZafCxC79_03.setCheckedMostrarSoloDocumentosContado(true);
            objZafCxC79_03.setCheckedMostrarRetenciones(false);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        } 
        else {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
                strCodTipDoc = vcoTipDoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }    
    
    public int getTipCre() {
        int tipDoc;
        if(txtDesCorTipDoc.getText().equals("COBTCM"))
            tipDoc=1;
        else if (txtDesCorTipDoc.getText().equals("COBTCD"))
            tipDoc=2;
        else
            tipDoc=1;
        return tipDoc;
    }    
    
    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }    
    
    /**
     * Funcion que carga el tipo de documento cuando se da en clic insertar y consultar
     * @param intVal valor si tiene que cargar numero de documento o no 1 = Si cargar
     */
    public void cargarTipoDoc(int intVal) {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if (objParSis.getCodigoUsuario() == 1) {
                    strSql =" SELECT doc.co_tipdoc, doc.tx_deslar, doc.tx_descor ";
                    strSql+="      , case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc ";
                    strSql+="      , doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe";
                    strSql+="      , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta, 2 as ne_tipresmoddoc ";
                    strSql+=" FROM tbr_tipdocprg as menu ";
                    strSql+=" INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) ";
                    strSql+=" INNER JOIN tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) ";
                    strSql+=" INNER JOIN tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) ";
                    strSql+=" WHERE menu.st_reg = 'S' ";
                    strSql+=" AND menu.co_emp = " + objParSis.getCodigoEmpresa();
                    strSql+=" AND menu.co_loc = " + objParSis.getCodigoLocal();
                    strSql+=" AND menu.co_mnu = " + objParSis.getCodigoMenu();
                }
                else {
                    strSql =" SELECT doc.co_tipdoc,doc.tx_deslar,doc.tx_descor";
                    strSql+="      , case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc ";
                    strSql+="      , doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe";
                    strSql+="      , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta ";
                    strSql+="      , menu.ne_tipresmoddoc ";
                    strSql+=" FROM tbr_tipDocUsr as menu ";
                    strSql+=" INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) ";
                    strSql+=" inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) ";
                    strSql+=" inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) ";
                    strSql+=" WHERE menu.st_reg = 'S'";
                    strSql+=" AND menu.co_emp = " + objParSis.getCodigoEmpresa();
                    strSql+=" AND menu.co_loc = " + objParSis.getCodigoLocal();
                    strSql+=" AND menu.co_mnu = " + objParSis.getCodigoMenu();
                    strSql+=" AND menu.co_usr=" + objParSis.getCodigoUsuario();
                }
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    intTipModDoc = rstLoc.getInt("ne_tipresmoddoc");
                    strCodTipDoc = txtCodTipDoc.getText();
                    if (intVal == 1) {
                        txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                    }

                    strCodCtaCli = rstLoc.getString("cocta");
                    strTxtCodCtaCli = rstLoc.getString("txcodcta");
                    strNomCtaCli = rstLoc.getString("deslarcta");
                    strCodCtaEfe = rstLoc.getString("co_cta");
                    strTxtCodCtaEfe = rstLoc.getString("tx_codcta");
                    strNomCtaEfe = rstLoc.getString("txdeslarctaefe");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * Carga el tipo de documento cuando se da en click insertar y consultar
     * @param intVal valor si tiene que cargar numero de documento o no 1 =si cargar
     */
    private void cargarTipoDocSel(int intVal) {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if (objParSis.getCodigoUsuario() == 1) {
                    strSql =" SELECT doc.co_tipdoc, doc.tx_deslar, doc.tx_descor ";
                    strSql+="      , CASE WHEN (doc.ne_ultdoc+1) IS NULL THEN 1 ELSE doc.ne_ultdoc+1 END AS numDoc ";
                    strSql+="      , doc.tx_natdoc, doc.st_meringegrfisbod, a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe";
                    strSql+="      , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta, 2 as ne_tipresmoddoc ";
                    strSql+=" FROM tbr_tipdocprg as menu ";
                    strSql+=" INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) ";
                    strSql+=" INNER JOIN tbm_placta as a1 ON (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) ";
                    strSql+=" INNER JOIN tbm_placta as a2 ON (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) ";
                    strSql+=" WHERE menu.co_emp = " + objParSis.getCodigoEmpresa() ;
                    strSql+=" AND menu.co_loc = " + objParSis.getCodigoLocal() ;
                    strSql+=" AND menu.co_mnu = " + objParSis.getCodigoMenu();
                    strSql+=" AND menu.co_tipDoc=" + txtCodTipDoc.getText();
                    //strSql+=" AND  menu.st_reg = 'S' ";
                } else {
                    strSql =" SELECT doc.co_tipdoc,doc.tx_deslar,doc.tx_descor ";
                    strSql+="      , case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc ";
                    strSql+="      , doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe";
                    strSql+="      , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta, menu.ne_tipresmoddoc ";
                    strSql+=" FROM tbr_tipDocUsr as menu ";
                    strSql+=" INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) ";
                    strSql+=" INNER JOIN tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) ";
                    strSql+=" INNER JOIN tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) ";
                    strSql+=" WHERE menu.co_emp = " + objParSis.getCodigoEmpresa();
                    strSql+=" AND menu.co_loc = " + objParSis.getCodigoLocal();
                    strSql+=" AND menu.co_mnu = " + objParSis.getCodigoMenu();
                    strSql+=" AND menu.co_usr=" + objParSis.getCodigoUsuario();
                    strSql+=" AND menu.co_tipDoc=" + txtCodTipDoc.getText();
                    //strSql+=" AND menu.st_reg = 'S' ";
                }
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    intTipModDoc = rstLoc.getInt("ne_tipresmoddoc");
                    strCodTipDoc = txtCodTipDoc.getText();
                    if (intVal == 1) {
                        txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                    }

                    strCodCtaCli = rstLoc.getString("cocta");
                    strTxtCodCtaCli = rstLoc.getString("txcodcta");
                    strNomCtaCli = rstLoc.getString("deslarcta");
                    strCodCtaEfe = rstLoc.getString("co_cta");
                    strTxtCodCtaEfe = rstLoc.getString("tx_codcta");
                    strNomCtaEfe = rstLoc.getString("txdeslarctaefe");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * Esta funcion permite extraer los rangos de ajuste de centavos automativos
     * @return true si no hay problema false por algun error.
     */
    private boolean cargarRangoAjuCenAut() 
    {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = " SELECT nd_valminajucenaut, nd_valmaxajucenaut FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresa() + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    dblMinAjuCenAut = objUti.redondear(rstLoc.getDouble("nd_valminajucenaut"), 2);
                    dblMaxAjuCenAut = objUti.redondear(rstLoc.getDouble("nd_valmaxajucenaut"), 2);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    /**
     * verifica si ya tiene aplicado algun valor al registro antes de eliminar de la fila
     * @return true si se puede eliminar false no se puede eliminar
     */
    private boolean verificaSelEli() {
        boolean blnRes = true;
        String strEstApl = "";
        try {
            int intFilSel[];
            int intFil = 0;
            intFilSel = tblDat.getSelectedRows();

            for (int i = 0; i < intFilSel.length; i++) {
                intFil = intFilSel[i] - i;
                strEstApl = (tblDat.getValueAt(intFil, INT_TBL_CODREGEFE) == null ? "N" : (tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).equals("") ? "N" : tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).toString()));
                if (!(strEstApl.equals("N"))) {
                    blnRes = false;
                    break;
                }
            }
            intFilSel = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean _getVerificaExistencia(String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg) {
        boolean blnRes = true;
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                    if (tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc)) {
                        if (tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc)) {
                            if (tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc)) {
                                if (tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg)) {
                                    blnRes = false;
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean _getVerificaExistenciaVenCon(int intFilSel, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg) {
        boolean blnRes = true;
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (intFilSel != i) {
                    if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                        if (tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc)) {
                            if (tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc)) {
                                if (tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc)) {
                                    if (tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg)) {
                                        blnRes = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Calcula el monto total de los cheques ingresados
     */
    public void calculaTotMonAbo() {
        double dblMonCre = 0, dblValDoc = 0;
        String strValDoc = "";
        int intTipMov = 0;
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODCLI) != null) {
                    strValDoc = (tblDat.getValueAt(i, INT_TBL_VALDOC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

                    dblValDoc = objUti.redondear(strValDoc, 4);
                    if (dblValDoc > 0) {
                        intTipMov = -1;
                    } else {
                        intTipMov = 1;
                    }
                    dblMonCre += Math.abs(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_ABONO) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString())))) * intTipMov;
                }
            }
            dblMonCre = objUti.redondear(dblMonCre, 2);
            txtValDoc.setText("" + dblMonCre);

            if (!(strCodCtaCli.equals(""))) {
                generaAsiento(dblMonCre);
                generaAsientoCXCTCM(dblMonCre);
            } else {
                generaAsiento();
                generaAsientoCXCTCM();
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtValDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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
                formInternalFrameClosing(evt);
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
        lblTit.setText("Título");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 64));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:"); // NOI18N
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 130, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
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
        txtDesCorTipDoc.setBounds(129, 4, 56, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
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
        txtDesLarTipDoc.setBounds(185, 4, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(415, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:"); // NOI18N
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(445, 4, 130, 20);

        lblCodDoc.setText("Código del documento:"); // NOI18N
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 130, 20);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(129, 24, 100, 20);

        lblNumDoc.setText("Número de documento:"); // NOI18N
        lblNumDoc.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(445, 24, 130, 20);

        txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setVerifyInputWhenFocusTarget(false);
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(575, 24, 100, 20);

        txtValDoc.setBackground(objParSis.getColorCamposSistema());
        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setText("0.00");
        txtValDoc.setVerifyInputWhenFocusTarget(false);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(575, 44, 100, 20);

        lblValDoc.setText("Valor del documento:"); // NOI18N
        lblValDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(445, 44, 130, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panGenTot.setPreferredSize(new java.awt.Dimension(206, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
            //Eddye: Parche temporal hasta que se mejore el código.
            if (objTooBar.getEstado()=='n') {
                cargarTipoDocSel(1);
            }
            else {
                cargarTipoDocSel(2);
            }
            calculaTotMonAbo();
        }
        else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals(""))  {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
            //Eddye: Parche temporal hasta que se mejore el código.
            if (objTooBar.getEstado()=='n') {
                cargarTipoDocSel(1);
            }
            else {
                cargarTipoDocSel(2);
            }
            calculaTotMonAbo();
        }
        else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            intTipModDoc = Integer.parseInt(vcoTipDoc.getValueAt(4).toString());
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        }
        //Eddye: Parche temporal hasta que se mejore el código.
        if (objTooBar.getEstado()=='n') {
            cargarTipoDocSel(1);
        }
        else {
            cargarTipoDocSel(2);
        }
        calculaTotMonAbo();
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing
    
    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de
     * salidad.
     */
    private void exitForm() {

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtValDoc;
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

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }    
    
    public void abrirCon() {
        try {
            con_Glo = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void cerrarCon() {
        try {
            con_Glo.close();
            con_Glo = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    } 
    
    private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) {
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }  

    /**
     * Funcion que se encarga de truncar el valor recibido
     * @param dblval  valor recibido
     * @param intNumDec  numero de decilames
     * @return  retorna el valor truncado
     *  <br> ejemplo <br> 25.52  -> 25.00
     */
    private double Truncar(double dblval, int intNumDec){
        double dlbRes=0;
        try{
            if(dblval > 0)
                dblval = Math.floor(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);
            else
                dblval = Math.ceil(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);
            dlbRes=dblval;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return dlbRes;
    }    
    
    private void limpiarFrm() {
        strCodTipDoc = "";
        strDesCorTipDoc = "";
        strDesLarTipDoc = "";

        dtpFecDoc.setText("");

        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");

        txtValDoc.setText("0.00");
        dtpFecDoc.setText("");
        txtNumDoc.setText("");

        txtCodDoc.setText("");
        txaObs1.setText("");
        txaObs2.setText("");

        objTblMod.removeAllRows();

        objAsiDia.inicializar();
        objAsiDia.setEditable(true);

        objAsiDia2.inicializar();
        objAsiDia2.setEditable(true);
    }    
    
    /**
     * Función que valida los campos
     * @return 
     */
    private boolean isCamVal() {
        int intExiDatTbl = 0;
        int intExiDatTbl2 = 0;

        if (txtCodTipDoc.getText().trim().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("El campo << Tipo de documento >> es obligatorio.\nSeleccione un tipo de documento y vuelva a intentarlo.");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if (txtNumDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("El campo << Número de documento >> es obligatorio.\nIngrese un numero de documento y vuelva a intentarlo.");
            txtNumDoc.requestFocus();
            return false;
        }

        for (int i = 0; i < tblDat.getRowCount(); i++) {
            if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true"))) {
                intExiDatTbl = 1;
                if (!(!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMTRA) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMTRA).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMTRA).toString())).equals("")) && !((tblDat.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (tblDat.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_NUMAUT).toString())).equals("")))) {
                    intExiDatTbl2 = 1;
                }
            }
        }

        if (intExiDatTbl == 0) {
            mostrarMsgInf("<HTML>NO EXISTEN DOCUMENTOS SELECCIONADOS</HTML>");
            return false;
        }

        if (intExiDatTbl2 == 1) {
            mostrarMsgInf("<HTML>DATOS INCOMPLETOS EN DETALLE<<No. AUTORIZACION/No. TRANSACCION>> <BR>INGRESE DATOS Y VUELVA A INTENTARLO.... </HTML>");
            return false;
        }

        return true;
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
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }    
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a
     * los objTooBars de tipo texto para poder determinar si su contenido a
     * cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes = true;
//        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux)) {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
////                        blnRes=objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//                blnRes=true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes=false;
//                break;
//        }
        return blnRes;
    }   
    
    /**
     * Función para verificar el estado de impresion antes de insertar un nuevo documento.
     * @return
     */
    private boolean getEstImpDocAnt() {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        int intCodTipDoc = 0;
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if ((txtCodTipDoc.getText()).equals("")) {
                    intCodTipDoc = 0; // Motivo: Se generaba una excepcion al momento de realiza el cambio a int.
                } else {
                    intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());
                }

                strSql =" select  st_imp  from tbm_cabpag where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " "
                      + " and co_tipdoc=" + intCodTipDoc + " and ne_numdoc1 = " + (txtNumDoc.getText().equals("") ? "0" : txtNumDoc.getText()) + "-1 and st_imp='N'";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    mostrarMsgInf("NO ESTA IMPRESO EL DOCUMENTO ANTERIOR  \nTIENE QUE REALIZAR LA IMPRESIÓN PARA PODER INSERTAR UN NUEVO DOCUMENTO..");
                    blnRes = false;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    private boolean validaExiNumDoc(){
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        String strSql;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc=conLoc.createStatement();
            //************  PERMITE SABER SI EL NUMERO DE RECEPCION ESTA DUPLICADO  *****************/
            strSql = " SELECT count(ne_numdoc1) as num FROM tbm_cabpag  "
                   + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                   + " AND co_tipdoc=" + txtCodTipDoc.getText() + " AND ne_numdoc1=" + txtNumDoc.getText();
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {
                if (rstLoc.getInt("num") >= 1) {
                    mostrarMsgInf("<HTML>No. de Cobro ya existe.</HTML>");
                    blnRes = false;
                }
            }
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc=null;
            conLoc.close();
            conLoc=null;
        }        
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }        
        return blnRes;   
    }    

    /**
     * Funcion que permite validar si el documento esta Impreso o no para modificar
     * @param conn Coneccion de la base
     * @param intCodEmp Codigo de la empresa
     * @param intCodLoc Codigo del local
     * @param intCodTipDoc Codigo del tipo del documento
     * @param intCodDoc Codigo documento
     * @return true no esta impreso false si esta impreso
     */
    private boolean validaModDocEstImp() {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if (intTipModDoc == 1) {
                    blnRes = false;
                    mostrarMsgInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                } 
                else if (intTipModDoc == 2) {
                    strSql =" SELECT st_imp FROM tbm_cabpag WHERE  co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + ""
                          + " AND co_doc=" + txtCodDoc.getText() + "  and st_imp='S'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = false;
                        mostrarMsgInf("EL DOCUMENTO YA ESTA IMPRESO Y NO SE PUEDE MODIFICAR ..");
                    }
                    rstLoc.close();
                    rstLoc = null;

                } else if (intTipModDoc == 3) {
                    // SI PERMITE MODIFCAR
                }
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean validaModDocCer() {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();

                if (intTipModDoc == 1) {
                    blnRes = false;
                    mostrarMsgInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                } 
                else if (intTipModDoc == 2) {
                    strSql = " SELECT st_ciecobtarcre FROM tbm_cabpag WHERE  co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + ""
                           + " AND co_doc=" + txtCodDoc.getText() + "  and st_ciecobtarcre ='S'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = false;
                        mostrarMsgInf("EL DOCUMENTO ESTA CERRADO, NO SE PUEDE MODIFICAR/ANULAR/ELIMINAR ..");
                    }
                    rstLoc.close();
                    rstLoc = null;

                } else if (intTipModDoc == 3) {
                    // SI PERMITE MODIFCAR
                }
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch (java.sql.SQLException ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /**
     * Permite saber si el documento esta anulado
     *
     * @param conn coneccion de la base
     * @param intCodTipDoc codigo del tipo de documento
     * @param intCodDoc codigo de documento
     * @return true no esta anulado false esta anulado el registro
     */
    private boolean validaDocAnu() {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "", strEst = "";
        try {
            conLoc= DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql =" SELECT st_reg FROM tbm_cabpag "
                       +" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                       +" AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + " AND st_reg='I' ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (rstLoc.getString("st_reg").equals("I")) {
                        strEst = "ANULADO";
                    }
                    mostrarMsgInf("El documento ya está " + strEst + ".\nNo es posible modifcar un documento " + strEst + ".");
                    blnRes = false;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    private void generaAsiento()
    {
        try {
            objAsiDia.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }

            java.util.Vector vecReg;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaCli));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaCli);
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaEfe));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe);
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsiento(double dblValDoc)
    {
        try {
            objAsiDia.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            double dblValDebCli = 0;
            double dblValHabCli = 0;
            double dblValDebEfe = 0;
            double dblValHabEfe = 0;

            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }

            if (dblValDoc < 0) {
                dblValDebCli = Math.abs(dblValDoc);
                dblValHabCli = 0;
                dblValDebEfe = 0;
                dblValHabEfe = Math.abs(dblValDoc);;
            } else {
                dblValDebCli = 0;
                dblValHabCli = Math.abs(dblValDoc);;
                dblValDebEfe = Math.abs(dblValDoc);;
                dblValHabEfe = 0;
            }
            java.util.Vector vecReg;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaCli));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaCli);
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebCli));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabCli));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, new Integer(strCodCtaEfe));
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe);
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebEfe));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabEfe));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsientoCXCTCM()
    {
        try
        {
            objAsiDia2.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            int intCodCta1 = 0;
            int intCodCta2 = 0;

            if (vecDetDiario==null)
            {
                vecDetDiario=new java.util.Vector();
            }
            else
            {
                vecDetDiario.removeAllElements();
            }
            java.util.Vector vecReg;
            if (txtDesCorTipDoc.getText().equals("COBTCM"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3527;
                        intCodCta2=3528;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1520;
                        intCodCta2=1521;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2534;
                        intCodCta2=2535;
                        break;
                }
            }
            else if (txtDesCorTipDoc.getText().equals("COBTCD"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3809;
                        intCodCta2=3808;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1664;
                        intCodCta2=1663;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2679;
                        intCodCta2=2678;
                        break;
                }
            }
            //OJO: No se cambió los números de las cuentas y nombres de las cuentas de acuerdo al tipo de documento porque
            //     éste documento se guarda en segundo plano y no se ve hasta que se lo vuelva a consultar.
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta1);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.17");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "TRANSITORIA TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta2);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.18");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "CXC TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia2.setDetalleDiario(vecDetDiario);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void generaAsientoCXCTCM(double dblValDoc)
    {
        try
        {
            objAsiDia2.inicializar();
            int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
            int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE = 5; //5) Debe.
            int INT_VEC_HABER = 6; //6) Haber.
            int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO = 8; //7) Referencia: Se debe asignar una cadena vacía o null

            int intCodCta1 = 0;
            int intCodCta2 = 0;

            double dblValDebCli = 0;
            double dblValHabCli = 0;
            double dblValDebEfe = 0;
            double dblValHabEfe = 0;

            if (vecDetDiario==null)
            {
                vecDetDiario=new java.util.Vector();
            }
            else
            {
                vecDetDiario.removeAllElements();
            }
            if (dblValDoc<0)
            {
                dblValDebCli=Math.abs(dblValDoc);
                dblValHabCli=0;
                dblValDebEfe=0;
                dblValHabEfe=Math.abs(dblValDoc);;
            }
            else
            {
                dblValDebCli=0;
                dblValHabCli=Math.abs(dblValDoc);;
                dblValDebEfe=Math.abs(dblValDoc);;
                dblValHabEfe=0;
            }
            java.util.Vector vecReg;
            if (txtDesCorTipDoc.getText().equals("COBTCM"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3527;
                        intCodCta2=3528;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1520;
                        intCodCta2=1521;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2534;
                        intCodCta2=2535;
                        break;
                }
            }
            else if (txtDesCorTipDoc.getText().equals("COBTCD"))
            {
                switch (objParSis.getCodigoEmpresa())
                {
                    case 1: //TUVAL
                        intCodCta1=3809;
                        intCodCta2=3808;
                        break;
                    case 2: //CASTEK
                        intCodCta1=1664;
                        intCodCta2=1663;
                        break;
                    case 4: //DIMULTI
                        intCodCta1=2679;
                        intCodCta2=2678;
                        break;
                }
            }
            //OJO: No se cambió los números de las cuentas y nombres de las cuentas de acuerdo al tipo de documento porque
            //     éste documento se guarda en segundo plano y no se ve hasta que se lo vuelva a consultar.
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta1);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.17");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "TRANSITORIA TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebCli));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabCli));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, intCodCta2);
            vecReg.add(INT_VEC_NUMCTA, "1.01.03.01.18");
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, "CXC TC (BANCO INTERNACIONAL)");
            vecReg.add(INT_VEC_DEBE, new Double(dblValDebEfe));
            vecReg.add(INT_VEC_HABER, new Double(dblValHabEfe));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia2.setDetalleDiario(vecDetDiario);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
                        if ( blnHayCam ){
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
            try {
                limpiarFrm();

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                txtValDoc.setEditable(false);
                txtValDoc.setBackground(colBack);
                
                //Fecha del documento
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));                

                setEditable(true);
                objTblMod.setDataModelChanged(false);

                cargarTipoDoc(1);

                if (!getEstImpDocAnt()) {
                    this.setEstado('w');
                }

            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickConsultar() {
            limpiarFrm();
            cargarTipoDoc(2);
        }

        public void clickModificar() {
            try {
                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();

                bloquea(txtCodDoc, colBack, false);
                bloquea(txtDesCorTipDoc, colBack, false);
                bloquea(txtDesLarTipDoc, colBack, false);
                bloquea(txtValDoc, colBack, false);

                butTipDoc.setEnabled(false);

                setEditable(true);
                objTblMod.setDataModelChanged(false);

                blnHayCam = false;

                if (intTipModDoc == 1) {
                    mostrarMsgInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
                    this.setEstado('w');
                } else if (intTipModDoc == 2) {
                    if (!validaModDocEstImp() || !validaModDocCer()) {
                        this.setEstado('w');
                    }
                } else if (intTipModDoc == 3) {
                    // SI PERMITE MODIFCAR
                }
            } catch (Exception evt) {
                objUti.mostrarMsgErr_F1(this, evt);
            }
        }        

        public void clickVisPreliminar() {
        }
        
        public void clickImprimir() {
        }

        public void clickAnular() {
        }

        public void clickEliminar() {
        }

        public void clickAceptar() {
        }
        
        public void clickCancelar() {
        }        

        public boolean insertar()
        {            
            if (!insertarReg()) 
                return false;   
            return true;
        }
        
        public boolean consultar() {
            if (!consultarReg()) 
                return false;   
            return true;            
        }
        
        public boolean modificar()
        {            
            if (!actualizarReg()) 
                return false;   
            return true;
        }        
        
        public boolean vistaPreliminar() {
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean imprimir() {
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }        
        
        public boolean anular() {
            boolean blnRes = false;
            String strAux = "";
            try {
                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                    return false;
                }
                
                if (!validaDocAnu()) 
                    return false;
                
                if (!validaModDocCer()) 
                    return false;
                
                if(!anularReg())
                    return false;
                
                objTooBar.setEstadoRegistro("Anulado");
                blnHayCam = false;
            }
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }
        
        public boolean eliminar() {
            return true;
        }            
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if ( blnHayCam ){
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

        public boolean afterInsertar() {
            this.setEstado('w');
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }        

        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }

        public boolean afterAnular() {            
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean beforeInsertar() {
            if (!isCamVal()) 
                return false;
            
            if(!validaExiNumDoc())
                return false;
            
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeModificar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                return false;
            }     
            
            if (!isCamVal()) 
                return false;
            
            if (!validaDocAnu()) 
                return false;
            
            if (!validaModDocEstImp()) 
                return false;
            
            if (!validaModDocCer())    
                return false;
            
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }        

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }              


    }
     

    private boolean consultarReg() {
        boolean blnRes = true;
        try {
            abrirCon();
            if (con_Glo != null) {
                stm_Glo = con_Glo.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc";
                strSQL+=" FROM tbm_cabpag AS a";
                strSQL+=" WHERE a.st_reg NOT IN ('E')";
                strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                
                if (!(txtCodTipDoc.getText().equals(""))) {
                    strSQL += " AND a.co_tipdoc=" + txtCodTipDoc.getText();
                } else {
                    strSQL += " AND a.co_tipdoc IN (" + strSqlTipDocAux + ") ";
                }

                if (!txtCodDoc.getText().equals("")) {
                    strSQL += " AND a.co_doc=" + txtCodDoc.getText();
                }

                if (!txtNumDoc.getText().equals("")) {
                    strSQL += " AND a.ne_numdoc1=" + txtNumDoc.getText();
                }

                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                            

                strSQL+=" ORDER BY a.fe_doc, a.ne_numdoc1 ";
                rst_Glo = stm_Glo.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst_Glo.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,    rst_Glo.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,    rst_Glo.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC, rst_Glo.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,    rst_Glo.getInt("co_doc"));
                    arlDatCon.add(arlRegCon);
                }                
                stm_Glo.close();
                stm_Glo=null;
                rst_Glo.close();
                rst_Glo=null;
                cerrarCon();
                
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
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        System.gc();
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
                    if(objAsiDia.consultarDiario( objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) )){
                        blnRes=true;
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
            abrirCon();
            if (con_Glo!=null){
                stm_Glo=con_Glo.createStatement();
                strSQL="";
                strSQL+=" SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc ";
                strSQL+="      , a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, a.tx_obs1, a.tx_obs2, a.co_mnu";
                strSQL+=" FROM tbm_cabpag as a ";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                rst_Glo=stm_Glo.executeQuery(strSQL);
                if (rst_Glo.next())
                {
                    strAux=rst_Glo.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst_Glo.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst_Glo.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
       
                    dtpFecDoc.setText(objUti.formatearFecha(rst_Glo.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    intCodMnuDocIng = rst_Glo.getInt("co_mnu");
                    
                    strAux=rst_Glo.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux); 
                    strAux=rst_Glo.getString("ne_numdoc1");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst_Glo.getObject("nd_mondoc")==null?"0":(rst_Glo.getString("nd_mondoc").equals("")?"0":rst_Glo.getString("nd_mondoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst_Glo.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst_Glo.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst_Glo.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));                    
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
                rst_Glo.close();
                rst_Glo=null;
                stm_Glo.close();
                stm_Glo=null;
                cerrarCon();                
            }
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)     {
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
            abrirCon();
            if (con_Glo!=null)
            {
                stm_Glo=con_Glo.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a2.co_cli, a2.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar ";
                strSQL+="      , a2.ne_numdoc, a2.fe_doc ,a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag";
                strSQL+="      , ( a1.mo_pag + a1.nd_abo ) as valpen, a.nd_abo, a.co_reg as coregpag, a.tx_numtra as tx_numtra, a.tx_numaut as tx_numaut,a2.co_tipCre ";
                strSQL+=" FROM tbm_detpag as a  ";
                strSQL+=" INNER JOIN tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) ";
                strSQL+=" INNER JOIN tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) ";
                strSQL+=" WHERE a.st_reg='A'";
                strSQL+=" AND a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a.co_Doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ORDER BY a.co_reg  ";                
                rst_Glo=stm_Glo.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst_Glo.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,     "");
                    vecReg.add(INT_TBL_BUTCLI,    "..");
                    vecReg.add(INT_TBL_CHKSEL,    new Boolean(true));
                    vecReg.add(INT_TBL_CODCLI,    rst_Glo.getString("co_cli"));
                    vecReg.add(INT_TBL_NOMCLI,    rst_Glo.getString("tx_nomcli"));
                    vecReg.add(INT_TBL_CODEMP,    rst_Glo.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC,    rst_Glo.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTID,    rst_Glo.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC,    rst_Glo.getString("co_doc"));
                    vecReg.add(INT_TBL_CODREG,    rst_Glo.getString("co_reg"));
                    vecReg.add(INT_TBL_DCTIPDOC,  rst_Glo.getString("tx_descor"));
                    vecReg.add(INT_TBL_DLTIPDOC,  rst_Glo.getString("tx_deslar"));
                    vecReg.add(INT_TBL_NUMDOC,    rst_Glo.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC,    rst_Glo.getString("fe_doc"));
                    vecReg.add(INT_TBL_DIACRE,    rst_Glo.getString("ne_diacre"));
                    vecReg.add(INT_TBL_FECVEN,    rst_Glo.getString("fe_ven"));
                    vecReg.add(INT_TBL_PORRET,    rst_Glo.getString("nd_porret"));
                    vecReg.add(INT_TBL_VALDOC,    rst_Glo.getString("mo_pag"));
                    vecReg.add(INT_TBL_VALPEN,    rst_Glo.getString("valpen"));
                    vecReg.add(INT_TBL_NUMTRA,    rst_Glo.getString("tx_numtra"));
                    vecReg.add(INT_TBL_NUMAUT,    rst_Glo.getString("tx_numaut"));
                    vecReg.add(INT_TBL_ABONO,     rst_Glo.getString("nd_abo"));
                    vecReg.add(INT_TBL_CODREGEFE, rst_Glo.getString("coregpag"));
                    vecReg.add(INT_TBL_ABONOORI,  rst_Glo.getString("nd_abo"));
                    vecReg.add(INT_TBL_BUTFAC,    "..");
                    vecReg.add(INT_TBL_TIPCRE,    "");                    
                    vecDat.add(vecReg);
                }
                rst_Glo.close();
                rst_Glo=null;
                stm_Glo.close();
                stm_Glo=null;
                cerrarCon();
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)  {
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;   
        try{
            abrirCon();
            if(con_Glo!=null){
                con_Glo.setAutoCommit(false);
                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCorTipDoc.getText().equals("COBTCM")) {   intCodTipDocCXCTC=196;    } else if (txtDesCorTipDoc.getText().equals("COBTCD")) {    intCodTipDocCXCTC=217;    }                        
                
                INT_ENV_REC_IMP_GUIA = 0;

                if (insertarCab()) {
                    if (insertarDet()) {
                        if (objAsiDia.insertarDiario(con_Glo, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") ))   {
                            if (objAsiDia2.insertarDiario(con_Glo, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocCXCTC, txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) {
                                if (objAjuCenAut.realizaAjuCenAut(con_Glo, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), 80, objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) {
                                    con_Glo.commit();
                                    blnRes = true;
                                } 
                                else 
                                    con_Glo.rollback();
                            } 
                            else 
                                con_Glo.rollback();
                        }
                        else
                            con_Glo.rollback();
                    } 
                    else 
                        con_Glo.rollback();
                }
                else 
                    con_Glo.rollback();

                cerrarCon();
            }
            
            if(blnRes){
                consultarReg();
                generarOrdDes();            
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
    
    private boolean insertarCab() {
        boolean blnRes = false;
        int intCodDoc;
        String strSql = "";
        try {
            if (con_Glo != null) {
                stm_Glo = con_Glo.createStatement();
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Obtener el código del último registro.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN (MAX(a1.co_doc)+1) IS NULL THEN 1 ELSE (MAX(a1.co_doc)+1) END AS codoc";
                strSQL+=" FROM tbm_cabpag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("insertarCab.tbm_cabpag: " + strSQL);
                intCodDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intCodDoc==-1)
                    return false;
                txtCodDoc.setText("" + intCodDoc);
                //***********************************************************************************************/
                strSql =  " INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 "
                        + "                       ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_ciecobtarcre ) "
                        + " VALUES("
                        + "   " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                        + " ,'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'" //fe_doc
                        + " , " + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) + "" //ne_numdoc1
                        + " , " + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) + "" //ne_numdoc2
                        + " , " + objUti.codificar(txaObs1.getText()) + ", " + objUti.codificar(txaObs2.getText()) + " "
                        + " , " + (txtValDoc.getText().equals("") ? "0" : txtValDoc.getText()) + ""  //nd_mondoc
                        + ", 'A', " + objParSis.getFuncionFechaHoraBaseDatos() + "," + objParSis.getCodigoUsuario() + " "
                        + " ,'" + objParSis.getNombreComputadoraConDirIP() + "', " + objParSis.getCodigoMenu() + ", '' ) ; ";
                stm_Glo.executeUpdate(strSql);
                //Eddye: Parche temporal hasta que se mejore el código.
                if (txtDesCorTipDoc.getText().equals("COBTCM"))
                {
                    int intCodTipDocCXCTC=196;
                    String strIdeCli="1790098354001";
                    String strCodCli="";
                    String strNomCli="";
                    strSql = " select co_cli, tx_nom from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " AND tx_ide = '" + strIdeCli + "'";
                    rst_Glo = stm_Glo.executeQuery(strSql);
                    while (rst_Glo.next()) {
                        strCodCli = rst_Glo.getString("co_cli");
                        strNomCli = rst_Glo.getString("tx_nom");
                    }
                    strSql =  " INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli, co_forpag, nd_tot ,tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, co_mnu)"
                            + " VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                            + " ," + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) +""
                            + " ,'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"
                            + " ," + strCodCli + ", '" + strIdeCli + "', '" + strNomCli + "', 97, " + (txtValDoc.getText().equals("") ? "0" : txtValDoc.getText()) + " * -1 "
                            + " ," + objUti.codificar(txaObs1.getText()) + "," + objUti.codificar(txaObs2.getText()) + ",'A'," + objParSis.getFuncionFechaHoraBaseDatos() + ", " + objParSis.getCodigoUsuario() + " "
                            + " ," + objParSis.getCodigoMenu() + ")";
                    System.out.println("ZafCxC79.insertarCab: " + strSql);
                    stm_Glo.executeUpdate(strSql);
                }
                else if (txtDesCorTipDoc.getText().equals("COBTCD"))
                {
                    int intCodTipDocCXCTC=217;
                    String strIdeCli="1790010937001";
                    String strCodCli="";
                    String strNomCli="";
                    strSql = "select co_cli, tx_nom from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " AND tx_ide = '" + strIdeCli + "'";
                    rst_Glo = stm_Glo.executeQuery(strSql);
                    while (rst_Glo.next()) {
                        strCodCli = rst_Glo.getString("co_cli");
                        strNomCli = rst_Glo.getString("tx_nom");
                    }
                    strSql =  " INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli, co_forpag, nd_tot ,tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, co_mnu)"
                            + " VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                            + "," + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) +""
                            + ",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"
                            + "," + strCodCli + ", '" + strIdeCli + "', '" + strNomCli + "', 97, " + (txtValDoc.getText().equals("") ? "0" : txtValDoc.getText()) + " * -1 "
                            + "," + objUti.codificar(txaObs1.getText()) + "," + objUti.codificar(txaObs2.getText()) + ",'A'," + objParSis.getFuncionFechaHoraBaseDatos() + ", " + objParSis.getCodigoUsuario() + " "
                            + "," + objParSis.getCodigoMenu() + ")";
                    //System.out.println("ZafCxC79.insertarCab: " + strSql);
                    stm_Glo.executeUpdate(strSql);
                }
                stm_Glo.close();
                stm_Glo = null;
                rst_Glo.close();
                rst_Glo=null;
                blnRes = true;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /* Función que permite Insertar el Detalle del Cobro de Tarjeta de Crédito */ 
    private boolean insertarDet()
    {
        boolean blnRes = false;
        int intCodDoc;
        String strSql = "";
        String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "",strNumDoc="";
        String strAbono="0", strValPen="", strNumTra="0", strNumAut="0",  strValDoc="0", strFecVen = "", strMetRed="";
        double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.000, dblPorRetIva=20.000 ;
        double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
        double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0 ;
        int intTipMov = 1;  //Artificio para saber si es valor abonado se guardará con negativo o positivo.
        int intCodRegDet=0; //Código de registro.
        vecOD.clear();

        try
        {
            if (con_Glo != null)
            {
                stm_Glo = con_Glo.createStatement();
                intCodDoc=Integer.parseInt(txtCodDoc.getText());  

                for (int i = 0; i < tblDat.getRowCount(); i++)
                {
                    if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")))
                    {
                        if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")))
                        {
                            strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                            strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                            strCodTipDocRec = tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                            strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                            strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            strFecVen = tblDat.getValueAt(i, INT_TBL_FECVEN).toString();
                            strValDoc = tblDat.getValueAt(i, INT_TBL_VALDOC).toString();
                            strNumTra = tblDat.getValueAt(i, INT_TBL_NUMTRA).toString();
                            strNumAut = tblDat.getValueAt(i, INT_TBL_NUMAUT).toString();
                            strAbono = (tblDat.getValueAt(i, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
                            strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));

                            /*MODIFICADO PARA NUEVO SERVICIO 1*/
                            strNumDoc = tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();
                            /*MODIFICADO PARA NUEVO SERVICIO 1*/
                            dblValPen = objUti.redondear(strValPen, 2);
                            if (dblValPen > 0)
                            {     intTipMov = -1;          }
                            else
                            {     intTipMov = 1;           }

                            /* Almacena los pagos realizados en el Local que se realiza el Cobro con TC para la factura correspondiente.
                             * Adicional enlaza el Local de donde se realizo la factura de venta.*/
                            intCodRegDet++;
                            strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag,"
                                    + " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, tx_numtra, tx_numaut )"
                                    + " VALUES( " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intCodRegDet + ", " + strCodLoc + ", " + strCodTipDocRec + ", " + strCodDoc + ", " + strCodReg + ", " + strAbono + " * " + intTipMov + " , 'A','I', " + objUti.codificar(strNumTra) + "," + objUti.codificar(strNumAut) + " ) ; ";
                            //System.out.println("ZafCxC79.insertarDet: " + strSql);
                            stm_Glo.executeUpdate(strSql);

                            /* Actualiza el Valor de Abono en la Factura en el Local donde se realizo la factura de venta*/
                            strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + " + strAbono + " * " + intTipMov + ", "
                                    + " st_regrep='M' WHERE "
                                    + " co_emp=" + strCodEmp + " and co_loc=" + strCodLoc + " and co_tipdoc=" + strCodTipDocRec + " and co_doc=" + strCodDoc + " "
                                    + " and co_reg=" + strCodReg + " ; ";
                            //System.out.println("ZafCxC79.insertarDet: " + strSql);
                            stm_Glo.executeUpdate(strSql);

                            /* Consulta Datos de la Factura de Ventas para utilizar valores en calculos de retenciones, comisión, etc. */
                            strSql=" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                            strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                            strSql+=" FROM tbm_cabMovInv AS a1 ";
                            strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                            strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                            strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                            //System.out.println("ZafCxC79.insertarDet: " + strSql);
                            rst_Glo = stm_Glo.executeQuery(strSql);
                            while (rst_Glo.next()) 
                            {
                                dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2);    //Obtener IVA
                                dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                dblPorCom = Math.abs(Double.parseDouble(rst_Glo.getString("nd_porCom") == null ? "0" : rst_Glo.getString("nd_porCom").equals("") ? "0" : rst_Glo.getString("nd_porCom")));
                                dblComFij= 0.00;

                                //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                //dblComFij= Math.abs(Double.parseDouble(rst_Glo.getString("nd_valComFij") == null ? "0" : rst_Glo.getString("nd_valComFij").equals("") ? "0" : rst_Glo.getString("nd_valComFij")));
                                //</editor-fold>

                                dblValCom = (dblValAbo * dblPorCom) / 100;
                                dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA)
                                strMetRed=rst_Glo.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M

                                if (rst_Glo.getString("tx_tipcre").equals("C")) //Corriente
                               {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100; //Comisión + IvaComisión
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2); //Comisión sin Iva
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                if (rst_Glo.getString("tx_tipcre").equals("D"))//Diferido
                                {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100;
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                 if(strMetRed.equals("1")) // Metodo de truncamiento
                                 {
                                    dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                                 else if(strMetRed.equals("2")) //Metodo de redondeo
                                 {
                                    dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                            }
                            //Pulso Genera OD
                            if( objLiberaGuia._getVerificaPagTotFac(con_Glo, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc  ))
                            {
                                strSql = " UPDATE tbm_cabguirem SET " +
                                "  st_aut='A' "+
                                " ,fe_aut="+objParSis.getFuncionFechaHoraBaseDatos()+" "+
                                " ,tx_comAut='"+objParSis.getNombreComputadoraConDirIP()+"' "+
                                " ,co_usrAut="+objParSis.getCodigoUsuario()+" "+
                                " FROM  ( " +
                                "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where " +
                                "  co_emprel="+strCodEmp+" and co_locrel="+strCodLoc+"  and  co_tipdocrel="+strCodTipDocRec+" and co_docrel="+strCodDoc+" " +
                                "  group by co_emp, co_loc, co_tipdoc, co_doc " +
                                " ) AS x " +
                                " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc " +
                                " and x.co_doc=tbm_cabguirem.co_doc  and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P'  ;   ";
                                //System.out.println("ZafCxC79.insertarDet._getVerificaPagTotFac (SALE GUIA): " + strSql);
                                stm_Glo.executeUpdate( strSql );

                                INT_ENV_REC_IMP_GUIA=1;                           

                                vecOD.add(strCodEmp+"-"+strCodLoc+"-"+strCodTipDocRec+"-"+strCodDoc+"-"+strNumDoc);

                             }
                        }
                    }
                }
                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCorTipDoc.getText().equals("COBTCM"))
                {             intCodTipDocCXCTC=196;                  }
                else if (txtDesCorTipDoc.getText().equals("COBTCD"))
                {             intCodTipDocCXCTC=217;                  }
                intTipMov = -1;

                if(strMetRed.equals("1")) // Metodo de truncamiento
                {
                    dblTotRetFte = Truncar(dblBasRetFte * dblRetFte, 2); //Total Retención I/R
                    dblTotRetIva = Truncar(dblBasRetIva * dblRetIva, 2); //Total Retención IVA
                    //<editor-fold defaultstate="collapsed" desc="/*comentado por que presentaba el valor final incorrecto, se quito el truncamiento*/">
                    //dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar  
                    //</editor-fold>
                    dblTotValCob = (dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom); //Total Valor a Cobrar
                }
                else // Metodo de redondeo
                {
                     dblTotRetFte = objUti.redondear(dblBasRetFte * dblRetFte, 2);//Total Retención I/R
                     dblTotRetIva = objUti.redondear(dblBasRetIva * dblRetIva, 2); //Total Retención IVA
                     dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2);//Total Valor a Cobrar
                }

                /* Insertar Detalle para % Retención Fuente I/R (2%) */
                intCodRegDet++;
                strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, nd_porret, nd_basimp)"
                       + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                       + ", " + intCodRegDet + ", 'R', '" + strFecVen + "', " + dblTotRetFte + " * " + intTipMov + ", 'A', " + dblPorRetFte+ ", " + dblBasRetFte + " * " + intTipMov + ")";
                System.out.println("ZafCxC79.insertarDet: " + strSql);
                stm_Glo.executeUpdate(strSql);

                /* Insertar Detalle para % Retención IVA (20%) */
                intCodRegDet++;
                strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, nd_porret, nd_basimp)"
                       + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                       + ", " + intCodRegDet + ", 'R', '" + strFecVen + "', " + dblTotRetIva + " * " + intTipMov + ", 'A', " + dblPorRetIva+ ", " + dblBasRetIva + " * " + intTipMov + ")";
                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                stm_Glo.executeUpdate(strSql);

                /* Insertar Detalle para Comisión */
                intCodRegDet++;
                strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg, tx_aplRet, nd_basimp)"
                       + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                       + ", " + intCodRegDet + ", 'C', '" + strFecVen + "', " + dblTotCom + " * " + intTipMov + ", 'A', 'O', " + dblBasComNet + ")";
                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                stm_Glo.executeUpdate(strSql);

                /* Insertar Detalle para Valor A Cobrar */
                intCodRegDet++;
                strSql = "INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tipreg, fe_ven, mo_pag, st_reg)"
                       + "VALUES(" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + intCodTipDocCXCTC + ", " + intCodDoc + " "
                       + ", " + intCodRegDet + ", 'V', '" + strFecVen + "', " + dblTotValCob + " * " + intTipMov + ", 'A')";
                //System.out.println("ZafCxC79.insertarDet: " + strSql);
                stm_Glo.executeUpdate(strSql);

                stm_Glo.close();
                stm_Glo = null;
                rst_Glo.close();
                rst_Glo=null;
                blnRes = true;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    private void generarOrdDes(){
        try{
            ZafGenOdPryTra objGenOD= new ZafGenOdPryTra();
            ZafCnfDoc objValCnf=new ZafCnfDoc(objParSis,this);   
            abrirCon();
            if(con_Glo!=null){
                con_Glo.setAutoCommit(false);
                if (INT_ENV_REC_IMP_GUIA == 1) {
                    Iterator itVecOD=vecOD.iterator();
                    while(itVecOD.hasNext()){
                       String strDat=(String)itVecOD.next();
                       String[] strArrDat=strDat.split("-");

                       int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                       int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                       int intCodTipDoc=Integer.parseInt((String)strArrDat[2]);
                       int intCodDoc=Integer.parseInt((String)strArrDat[3]);

						//comentado por lo nuevo de facturas parciales 08 enero 2019					   
                        //if(!objValCnf.isDocIngPenCnfxFac(con_Glo, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "I")){
                            if(!(objGenOD.validarODExs(con_Glo, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc))){
                                if(objGenOD.generarNumOD(con_Glo, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, false)){
                                    con_Glo.commit();
                                    String strIp=objGenOD.obtenerIpSerImp(con_Glo);
                                    objGenOD.imprimirOdLocal(con_Glo, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strIp);                                                
                                    objGenOD.generarTermL(con_Glo, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                                }
                                else
                                    con_Glo.rollback();
                            }
                        //}
                    }
                    vecOD.clear();
                }  
                cerrarCon();
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }        
    }    
    
    private boolean actualizarReg() {
        boolean blnRes = false;
        try {
            //Eddye: Parche temporal hasta que se mejore el código.
            int intCodTipDocCXCTC=0;
            if (txtDesCorTipDoc.getText().equals("COBTCM")) { intCodTipDocCXCTC=196; }
            else if (txtDesCorTipDoc.getText().equals("COBTCD"))   { intCodTipDocCXCTC=217; } 
        
            INT_ENV_REC_IMP_GUIA = 0;
                  
            abrirCon();
            if (con_Glo != null) {
                con_Glo.setAutoCommit(false);
                if (modificarCab()) {
                    if (eliminarRegCobEfe()) {
                        if (modificarDet()) {
                            if (objAsiDia.actualizarDiario(con_Glo, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                                  , String.valueOf(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)), objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"), "A"))   {
                                if (objAsiDia2.actualizarDiario(con_Glo, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                                       , intCodTipDocCXCTC, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                                       , String.valueOf(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)), objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"), "A"))  {
                                    if (objAjuCenAut.realizaAjuCenAut(con_Glo, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC), 80, objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy") ))
                                    {
                                        con_Glo.commit();
                                        blnRes = true;
                                    }
                                    else
                                        con_Glo.rollback();
                                }
                                else
                                    con_Glo.rollback();
                            } 
                            else 
                                con_Glo.rollback();
                        } 
                        else 
                            con_Glo.rollback();
                    } 
                    else 
                        con_Glo.rollback();
                } 
                else 
                    con_Glo.rollback();

                cerrarCon();
            }
            if(blnRes){
                consultarReg();
                generarOrdDes();                        
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /**
     * Función que modifica la cabecera.
     *
     * @param conn coneccion de la base
     * @param intCodTipDoc codigo del tipo de documento
     * @param intCodDoc codigo de documento
     * @return
     */
    private boolean modificarCab() {
        boolean blnRes = true;
        String strSql = "";
        try {
            if (con_Glo != null) {
                stm_Glo = con_Glo.createStatement();

                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCorTipDoc.getText().equals("COBTCM"))   {   intCodTipDocCXCTC=196;  }
                else if (txtDesCorTipDoc.getText().equals("COBTCD")) {    intCodTipDocCXCTC=217;   }

                strSql = " UPDATE tbm_cabpag "
                       + " SET fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"
                       + " , ne_numdoc1=" + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) + ""
                       + " , ne_numdoc2=" + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) + " "
                       + " , tx_obs1=" + objUti.codificar(txaObs1.getText()) + ", tx_obs2=" + objUti.codificar(txaObs2.getText()) + ""
                       + " , nd_mondoc=" + (txtValDoc.getText().equals("") ? "0" : txtValDoc.getText()) +  ""
                       + " , fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ""
                       + " , co_usrmod=" + objParSis.getCodigoUsuario() + " "
                       + " , tx_commod='" + objParSis.getNombreComputadoraConDirIP() +"'"
                       + " , st_regrep='M' "
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+"" //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+"" ;     //co_doc
                stm_Glo.executeUpdate(strSql);

                strSql =  " UPDATE tbm_cabmovinv "
                        + " SET fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"
                        +"  , ne_numdoc=" + (txtNumDoc.getText().trim().equals("") ? null : txtNumDoc.getText()) + ""
                        + " , tx_obs1=" + objUti.codificar(txaObs1.getText()) + ", tx_obs2=" + objUti.codificar(txaObs2.getText()) + ""
                        + " , nd_tot=" + (txtValDoc.getText().equals("") ? "0" : txtValDoc.getText()) + " * -1 "
                        + " , fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + " "
                        + " , co_usrmod=" + objParSis.getCodigoUsuario() + " "
                        + " , tx_commod='" + objParSis.getNombreComputadoraConDirIP() + "', st_regrep='M' "
                        + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                        + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                        + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                        + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+"" ;     //co_doc                        
                stm_Glo.executeUpdate(strSql);

                stm_Glo.close();
                stm_Glo = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /* Función que permite Eliminar el Detalle del Cobro de Tarjeta de Crédito */ 
    private boolean eliminarRegCobEfe()
    {
        boolean blnRes = true;
        ResultSet rstLoc;
        String strSql = "";
        String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "";
        String strAbono="0", strValPen="",  strMetRed="";
        double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.000, dblPorRetIva=20.000 ;
        double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
        double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0 ;
        int intTipMov = 1; //Artificio para saber si es valor abonado se guardará con negativo o positivo.
        try
        {
            if (con_Glo != null)
            {
                stm_Glo = con_Glo.createStatement();
                java.util.ArrayList arlAux = objTblMod.getDataSavedBeforeRemoveRow();
                if (arlAux != null)
                {
                    for (int i = 0; i < arlAux.size(); i++)
                    {
                        int intCodReg = objUti.getIntValueAt(arlAux, i, INT_ARR_CODREG);
                        strSql = " SELECT co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag , nd_abo FROM tbm_detpag "
                               + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                               + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                               + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+"" //co_tipDoc
                               + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc                                
                               + " AND co_reg=" + intCodReg+"" ;                                                              //co_reg                
                        //System.out.println("ZafCxC79.eliminarRegCobEfe: " + strSql);
                        rstLoc = stm_Glo.executeQuery(strSql);
                        if (rstLoc.next())
                        {
                            strCodEmp = rstLoc.getString("co_emp");
                            strCodLoc = rstLoc.getString("co_locpag");
                            strCodTipDocRec = rstLoc.getString("co_tipdocpag");
                            strCodDoc = rstLoc.getString("co_docpag");
                            strCodReg = rstLoc.getString("co_regpag");
                            strSql  = " UPDATE tbm_detpag SET st_reg='E' "
                                    + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                                    + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                                    + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+"" //co_tipDoc
                                    + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc                                
                                    + " AND co_reg=" + intCodReg+";" ;                                                             //co_reg    
                            strSql += " UPDATE tbm_pagmovinv SET  nd_abo = nd_abo - " + rstLoc.getString("nd_abo") + " "
                                    + " WHERE co_emp=" + strCodEmp + " AND co_loc=" + strCodLoc + " AND co_tipdoc=" + strCodTipDocRec + " "
                                    + " AND co_doc=" + strCodDoc + " AND co_reg=" + strCodReg + " ";
                            //System.out.println("ZafCxC79.eliminarRegCobEfe: " + strSql);
                            stm_Glo.executeUpdate(strSql);

                            strSql=" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                            strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                            strSql+=" FROM tbm_cabMovInv AS a1 ";
                            strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                            strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                            strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                            //System.out.println("ZafCxC79.eliminarRegCobEfe: " + strSql);
                            rst_Glo = stm_Glo.executeQuery(strSql);

                            strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                            dblValPen = objUti.redondear(strValPen, 2);
                            if (dblValPen > 0)
                            {   intTipMov = -1;     }
                            else
                            {   intTipMov =  1;     }

                            while (rst_Glo.next())
                            {
                                dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2); //Obtener IVA
                                dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                dblPorCom = Math.abs(Double.parseDouble(rst_Glo.getString("nd_porCom") == null ? "0" : rst_Glo.getString("nd_porCom").equals("") ? "0" : rst_Glo.getString("nd_porCom")));
                                dblValCom = (dblValAbo * dblPorCom) / 100;
                                dblComFij= 0.00;

                                //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                //dblComFij= Math.abs(Double.parseDouble(rst_Glo.getString("nd_valComFij") == null ? "0" : rst_Glo.getString("nd_valComFij").equals("") ? "0" : rst_Glo.getString("nd_valComFij")));
                                //</editor-fold>

                                strMetRed=rst_Glo.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M
                                dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA) 

                                if (rst_Glo.getString("tx_tipcre").equals("C"))
                                {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100 ;
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom /  100 ;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                if (rst_Glo.getString("tx_tipcre").equals("D"))
                                {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom  / 100 ;
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                 if(strMetRed.equals("1")) // Metodo de truncamiento
                                 {
                                    dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                                 else if(strMetRed.equals("2")) //Metodo de redondeo
                                 {
                                    dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                            }
                        }
                        else
                        {
                            mostrarMsgInf(" NO ELIMINADO... > ");
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }
                    //Eddye: Parche temporal hasta que se mejore el código.
                    int intCodTipDocCXCTC=0;
                    if (txtDesCorTipDoc.getText().equals("COBTCM"))
                    {              intCodTipDocCXCTC=196;               }
                    else if (txtDesCorTipDoc.getText().equals("COBTCD"))
                    {              intCodTipDocCXCTC=217;               }
                    intTipMov = -1;

                    if(strMetRed.equals("1")) // Metodo de truncamiento
                    {
                          dblTotRetFte= Truncar(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                          dblTotRetIva= Truncar(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                          dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                    }
                    else // Metodo de redondeo
                    {
                          dblTotRetFte= objUti.redondear(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                          dblTotRetIva= objUti.redondear(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                          dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                    }

                    /* Actualiza Detalle para % Retención Fuente I/R (2%) */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotRetFte + " * " + intTipMov + ", "
                           + " nd_basimp = nd_basimp - " + dblBasRetFte + " * " + intTipMov + ", st_regrep = 'M'"
                           + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                           + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                           + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                           + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc                                
                           + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetFte +" ; ";
                    stm_Glo.executeUpdate(strSql);

                    /* Actualiza Detalle para % Retención IVA (20%) */
                      strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotRetIva + " * " + intTipMov + ", "
                             + " nd_basimp = nd_basimp - " + dblBasRetIva + " * " + intTipMov + ", st_regrep = 'M' "
                             + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                             + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                             + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                             + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc     
                             + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetIva +" ; ";
                    stm_Glo.executeUpdate(strSql);

                    /* Actualiza Detalle para Comisión */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotCom + " * " + intTipMov + ", "
                           + " nd_basimp = nd_basimp - " + dblBasComNet + ", "
                           + " st_regrep = 'M' "
                           + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                           + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                           + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                           + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc    
                           + " AND tx_tipreg = 'C' ; ";
                    stm_Glo.executeUpdate(strSql);

                    /* Actualiza Detalle para Valor A Cobrar */
                    strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag - " + dblTotValCob + " * " + intTipMov + ", st_regrep = 'M' "
                           + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                           + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                           + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                           + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc    
                           + " AND tx_tipreg = 'V' ; ";
                    stm_Glo.executeUpdate(strSql);
                }
                stm_Glo.close();
                stm_Glo = null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /* Función que permite Modificar el Detalle del Cobro de Tarjeta de Crédito */ 
    private boolean modificarDet()
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        String strCodEmp = "", strCodLoc = "", strCodTipDocRec = "", strCodDoc = "", strCodReg = "",strNumDoc= "";
        String strAbono="0", strValPen="", strNumTra="", strNumAut="", strTipCre="", strMetRed="";
        double dblSubTot=0,	dblIva=0, dblPorCom=0, dblValPen=0, dblValAbo=0, dblPorRetFte=2.00, dblPorRetIva=20.00 ;;
        double dblBasComNet=0,  dblBasRetFte=0, dblBasRetIva=0,  dblRetFte=0.02,  dblRetIva=0.20, dblValCom=0.00, dblComFij=0.00;
        double dblTotValCob=0, dblTotValPag=0, dblTotCom=0,  dblTotRetFte=0, dblTotRetIva=0;
        int intTipMov = 1; //Artificio para saber si es valor abonado se guardará con negativo o positivo.
        int intCodRegDet = 0; //Código de registro.
        vecOD.clear();
        try
        {
            if (con_Glo != null)
            {
                stmLoc = con_Glo.createStatement();
                intCodRegDet = getMaxCodRegDet(con_Glo);

                for (int i = 0; i < tblDat.getRowCount(); i++)
                {
                    if (((tblDat.getValueAt(i, INT_TBL_CODREGEFE) == null ? "" : tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString()).equals("")))
                    {
                        if (!((tblDat.getValueAt(i, INT_TBL_CODCLI) == null ? "" : (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")))
                        {
                            if (((tblDat.getValueAt(i, INT_TBL_CHKSEL) == null ? "false" : (tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("") ? "false" : tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")))
                            {
                                strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                                strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                                strCodTipDocRec = tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                                strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                                /*MODIFICADO PARA NUEVO SERVICIO 1*/
                                strNumDoc = tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();
                                /*MODIFICADO PARA NUEVO SERVICIO 1*/

                                strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                strNumTra = (tblDat.getValueAt(i, INT_TBL_NUMTRA) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NUMTRA).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NUMTRA).toString()));
                                strNumAut = (tblDat.getValueAt(i, INT_TBL_NUMAUT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NUMAUT).toString()));
                                strAbono = (tblDat.getValueAt(i, INT_TBL_ABONO) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
                                strTipCre = tblDat.getValueAt(i, INT_TBL_TIPCRE).toString();

                                strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                                dblValPen = objUti.redondear(strValPen, 2);
                                if (dblValPen > 0)
                                {       intTipMov = -1;          }
                                else
                                {       intTipMov =  1;          }

                                /* Almacena los pagos realizados en el Local que se realiza el Cobro con TC para la factura correspondiente.
                                 * Adicional enlaza el Local de donde se realizo la factura de venta.*/
                                strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag"
                                        + "                      , co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, tx_numtra, tx_numaut )"
                                        + " VALUES( " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP) +""
                                        + ", " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC) + ""
                                        + ", " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+""
                                        + ", " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) + " "
                                        + " ," + intCodRegDet + ", " + strCodLoc + ", " + strCodTipDocRec + ", " + strCodDoc + ", " + strCodReg + ", " + strAbono + "  * " + intTipMov + " , 'A','I'," + objUti.codificar(strNumTra) + "," + objUti.codificar(strNumAut) + " ) ; ";  //);
                                //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                stmLoc.executeUpdate(strSql);

                                /* Actualiza el Valor de Abono en la Factura en el Local donde se realizo la factura de venta*/
                                intCodRegDet++;
                                strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + " + strAbono + " * " + intTipMov + ", "
                                       + " st_regrep='M' "
                                       + " WHERE co_emp=" + strCodEmp + " AND co_loc=" + strCodLoc + " AND co_tipdoc=" + strCodTipDocRec + " AND co_doc=" + strCodDoc + " "
                                       + " AND co_reg=" + strCodReg + " ; ";
                                //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                stmLoc.executeUpdate(strSql);

                                /* Consulta Datos de la Factura de Ventas para utilizar valores en calculos de retenciones, comisión, etc. */
                                strSql =" SELECT a1.nd_sub, a1.nd_tot, a2.nd_porCom, a2.tx_tipCre, a1.co_tipCre,";
                                strSql+=" CASE WHEN a2.nd_valComFij IS NULL THEN 0 ELSE a2.nd_valComFij END AS nd_valComFij, a2.ne_metRed ";
                                strSql+=" FROM tbm_cabMovInv AS a1 ";
                                strSql+=" INNER JOIN tbm_tipCreRedTarCre AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_tipcre=a2.co_tipcre) ";
                                strSql+=" WHERE a1.co_emp = " + strCodEmp + " AND a1.co_loc = " + strCodLoc + " ";
                                strSql+=" AND a1.co_tipdoc = " + strCodTipDocRec + " and a1.co_doc = " + strCodDoc + "";
                                //System.out.println("ZafCxC79.modificarDet: " + strSql);
                                rst_Glo = stmLoc.executeQuery(strSql);

                                while (rst_Glo.next())
                                {
                                    dblSubTot= objUti.redondear(Math.abs(Double.parseDouble(strAbono) / dblFacIva), 2); //Obtener Subtotal
                                    dblIva= objUti.redondear(Math.abs(Double.parseDouble(strAbono) - dblSubTot), 2); //Obtener IVA
                                    dblValAbo = Math.abs(Double.parseDouble(strAbono));
                                    dblPorCom = Math.abs(Double.parseDouble(rst_Glo.getString("nd_porCom") == null ? "0" : rst_Glo.getString("nd_porCom").equals("") ? "0" : rst_Glo.getString("nd_porCom")));
                                    dblValCom = (dblValAbo * dblPorCom) / 100;
                                    dblComFij= 0.00;

                                    //<editor-fold defaultstate="collapsed"  desc="/* Habilitar en caso de que se vuelva a manejar comisión fija por parte de algún emisor de tarjeta de crédito (Ej.: Banco Guayaquil) */">
                                    //dblComFij= Math.abs(Double.parseDouble(rst_Glo.getString("nd_valComFij") == null ? "0" : rst_Glo.getString("nd_valComFij").equals("") ? "0" : rst_Glo.getString("nd_valComFij")));
                                    //</editor-fold>

                                    dblBasRetFte = dblBasRetFte + dblSubTot;//Obtener Base Imponible I/R 2% (Subtotal)
                                    dblBasRetIva = dblBasRetIva + dblIva;//Obtener Base Imponible IVA 20%(IVA)   
                                    strMetRed=rst_Glo.getString("ne_metRed"); //Metodo de redondeo 1:Truncar 2:Redondear JoséMarín M

                                    if (rst_Glo.getString("tx_tipcre").equals("C"))
                                    {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100 ;
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100 ;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                if (rst_Glo.getString("tx_tipcre").equals("D"))
                                {
                                    if(strMetRed.equals("1")) // Metodo de truncamiento
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100 ;
                                        dblBasComNet = Truncar((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                    else if(strMetRed.equals("2")) //Metodo de redondeo
                                    {
                                        dblValCom = dblValAbo * dblPorCom / 100 ;
                                        dblBasComNet = objUti.redondear((dblBasComNet + (dblValCom / dblFacIva)), 2);
                                    }
                                }
                                 if(strMetRed.equals("1")) // Metodo de truncamiento
                                 {
                                    dblTotValPag = Truncar((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = Truncar((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                                 else if(strMetRed.equals("2")) //Metodo de redondeo
                                 {
                                    dblTotValPag = objUti.redondear((dblTotValPag + dblValAbo + dblComFij), 2);
                                    dblTotCom = objUti.redondear((dblTotCom + dblValCom + dblComFij), 2);
                                 }
                                }
                                //Pulso Genera OD
                                if (objLiberaGuia._getVerificaPagTotFac(con_Glo, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc))
                                {
                                    strSql = " UPDATE tbm_cabguirem SET "
                                            + "  st_aut='A' "
                                            + " ,tx_obsAut=' AUTOMATICO '  "
                                            + " ,fe_aut=" + objParSis.getFuncionFechaHoraBaseDatos() + " "
                                            + " ,tx_comAut='" + objParSis.getNombreComputadoraConDirIP() + "' "
                                            + " ,co_usrAut=" + objParSis.getCodigoUsuario() + " "
                                            + " FROM  ( "
                                            + "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where "
                                            + "  co_emprel=" + strCodEmp + " and co_locrel=" + strCodLoc + "  and  co_tipdocrel=" + strCodTipDocRec + " and co_docrel=" + strCodDoc + " "
                                            + "  group by co_emp, co_loc, co_tipdoc, co_doc "
                                            + " ) AS x "
                                            + " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc "
                                            + " and x.co_doc=tbm_cabguirem.co_doc and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P' ;  ";
                                    stmLoc.executeUpdate(strSql);

                                    INT_ENV_REC_IMP_GUIA = 1;
                                    vecOD.add(strCodEmp+"-"+strCodLoc+"-"+strCodTipDocRec+"-"+strCodDoc+"-"+strNumDoc);
                                }
                            }
                        }
                    }
                }
                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCorTipDoc.getText().equals("COBTCM"))
                {        intCodTipDocCXCTC=196;                     }
                else if (txtDesCorTipDoc.getText().equals("COBTCD"))
                {        intCodTipDocCXCTC=217;                     }
                intTipMov = -1;

                if(strMetRed.equals("1")) // Metodo de truncamiento
                {
                     dblTotRetFte = Truncar(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                     dblTotRetIva = Truncar(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                     dblTotValCob = Truncar((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Valor A Cobrar
                }
                else // Metodo de redondeo
                {
                     dblTotRetFte= objUti.redondear(dblBasRetFte * (dblRetFte), 2); //Total Retención I/R
                     dblTotRetIva= objUti.redondear(dblBasRetIva * (dblRetIva), 2); //Total Retención IVA
                     dblTotValCob = objUti.redondear((dblTotValPag - dblTotRetFte - dblTotRetIva - dblTotCom), 2); //Total Valor a Cobrar
                }

                /* Actualiza Detalle para % Retención Fuente I/R (2%) */
                strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotRetFte + " * " + intTipMov + ", "
                       + " nd_basimp = nd_basimp + " + dblBasRetFte + " * " + intTipMov + ", "
                       + " st_regrep = 'M'"
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc                     
                       + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetFte +" ; ";
                stmLoc.executeUpdate(strSql);

                /* Actualiza Detalle para % Retención IVA (20%) */
                strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotRetIva + " * " + intTipMov + ", "
                       + " nd_basimp = nd_basimp + " + dblBasRetIva + " * " + intTipMov + ", "
                       + " st_regrep = 'M' "
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc
                       + " AND tx_tipreg = 'R' AND nd_porret="+dblPorRetIva +" ; ";
                stmLoc.executeUpdate(strSql);

                /* Actualiza Detalle para Comisión */
                strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotCom + " * " + intTipMov + ", "
                       + " nd_basimp = nd_basimp + " + dblBasComNet + " * " + intTipMov + ", st_regrep = 'M'"
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc
                       + " AND tx_tipreg = 'C' ; ";
                stmLoc.executeUpdate(strSql);

                /* Actualiza Detalle para Valor A Cobrar */
                strSql = " UPDATE tbm_pagmovinv set mo_pag = mo_pag + " + dblTotValCob + " * " + intTipMov + ",  st_regrep = 'M' "
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + intCodTipDocCXCTC+""                                                     //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+""       //co_doc
                       + " AND tx_tipreg = 'V' ; ";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    } 
    
    private int getMaxCodRegDet(java.sql.Connection conn) {
        int intCodReg = -1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = " SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detpag "
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""     //co_emp
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""       //co_loc
                       + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+"" //co_tipDoc
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+"" ;     //co_doc                        
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodReg = rstLoc.getInt("coreg");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return intCodReg;
    }    
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            //Obtiene Tipos de documentos para Bancos
            //Eddye: Parche temporal hasta que se mejore el código.
            int intCodTipDocCXCTC=0;
            if (txtDesCorTipDoc.getText().equals("COBTCM")) {  intCodTipDocCXCTC=196;  }
            else if (txtDesCorTipDoc.getText().equals("COBTCD")) { intCodTipDocCXCTC=217; }
        
            abrirCon();
            if (con_Glo != null) {
                con_Glo.setAutoCommit(false);
                if (anulaEstReg()) {
                    if (objAsiDia.anularDiario(con_Glo, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),  Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) ))
                    {
                        if (objAsiDia2.anularDiario(con_Glo, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocCXCTC,   Integer.parseInt(txtCodDoc.getText()) ))
                        {
                            con_Glo.commit();
                            blnRes = true;
                        }
                        else
                            con_Glo.rollback();
                    }
                    else
                        con_Glo.rollback();
                } 
                else 
                    con_Glo.rollback();

                cerrarCon();
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }        
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        

    /**
     * Anula el registro
     *
     * @param conn coneccion de la base
     * @param intCodTipDoc codigo del tipo de documento
     * @param intCodDoc codigo del documento
     * @return true si se pudo anular false no se puedo anular
     */
    private boolean anulaEstReg() {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            if (con_Glo != null) {
                stmLoc = con_Glo.createStatement();

                strSql =  " UPDATE tbm_cabpag SET st_reg='I', st_regrep='M' "
                        + " , fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ""
                        + " , co_usrmod=" + objParSis.getCodigoUsuario() + ""
                        + " , tx_commod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                        + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""
                        + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""
                        + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)+""
                        + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)+ " ; "
                        + " "
                        + " UPDATE tbm_pagmovinv SET nd_abo=nd_abo - x.ndabo, st_regrep='M' "
                        + "  FROM ( "
                        + "   select co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo as ndabo  from tbm_detpag as a "
                        + "   where a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)+""
                        + "   and a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)+""
                        + "   and a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC) + ""
                        + "   and a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)  + " and a.st_reg='A' "
                        + " ) AS x   "
                        + " WHERE tbm_pagmovinv.co_emp=x.co_emp AND tbm_pagmovinv.co_loc=x.co_locpag AND tbm_pagmovinv.co_tipdoc=x.co_tipdocpag AND tbm_pagmovinv.co_doc=x.co_docpag "
                        + " AND tbm_pagmovinv.co_reg=x.co_regpag ; "
                        + " ";
                stmLoc.executeUpdate(strSql);

                //Eddye: Parche temporal hasta que se mejore el código.
                int intCodTipDocCXCTC=0;
                if (txtDesCorTipDoc.getText().equals("COBTCM")) {
                    intCodTipDocCXCTC=196;
                }
                else if (txtDesCorTipDoc.getText().equals("COBTCD"))  {
                    intCodTipDocCXCTC=217;
                }
                //Anulamos el documento que corresponde a cuentas por pagar CXCTCM
                strSql = " UPDATE tbm_cabMovInv SET st_reg = 'I' "
                       + " WHERE co_emp =" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP) + " and co_loc = " +  objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC) + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" +  objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) ;
                stmLoc.executeUpdate(strSql);

                //Se comenta la actualización del st_reg. Asi se anule el cobro los registros de pagos no deben anularse, indicado por Ing.Eddye Lino.
                //strSql = "UPDATE tbm_pagMovInv SET st_reg = 'I' "
                //        + " WHERE co_emp =" +  objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP) + " and co_loc = " +  objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC) + " and co_tipdoc = " + intCodTipDocCXCTC + " and co_doc =" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) ;
                //stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread {
        private int intIndFun;

        public ZafThreadGUI() {
            intIndFun = 0;
        }

        public void run() {
            switch (intIndFun) {
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
            objThrGUI = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar. <BR>Puede tomar uno de los
     * siguientes valores: <UL> <LI>0: Impresión directa. <LI>1: Impresión
     * directa (Cuadro de dialogo de impresión). <LI>2: Vista preliminar. </UL>
     * @return true: Si se pudo generar el reporte. <BR>false: En el caso
     * contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try {
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {
                //Obtener la fecha y hora del servidor.
                /* datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                 if (datFecAux==null)
                 return false;
                 strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                 datFecAux=null;*/
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            case 19:
                            default:
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar = new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("co_emp", objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP));
                                mapPar.put("co_loc", objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC));
                                mapPar.put("co_tipdoc", objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC));
                                mapPar.put("co_doc", objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC));
                                //   mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                actualizarEstImp();
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private void actualizarEstImp() {
        Connection conLoc;
        Statement stmLoc;
        String strSql;
        try {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = " UPDATE tbm_cabpag SET st_imp='S' "
                       + " WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP) +""
                       + " AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC) +""
                       + " AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC) +""
                       + " AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) + "; ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch (java.sql.SQLException ex) {
            objUti.mostrarMsgErr_F1(this, ex);
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }    

    
    
    
    

}
