/*
 * ZafCom01.java 
 *   
 * Created on 06 de enero de 2006, 09h53
 */          
package Compras.ZafCom01;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafConsulta.ZafConsulta;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
/*  
 * @author José Salazar
 * "Cotizaciones de Compra"
 */    
public class ZafCom01 extends JInternalFrame 
{
    //Constantes
    //JTable: TblDat
    private static final int INT_TBL_DAT_LINEA   = 0 ; 
    private static final int INT_TBL_DAT_COD_ITM = 1 ;
    private static final int INT_TBL_DAT_ITM_ALT = 2 ;
    private static final int INT_TBL_DAT_BUT_ITM = 3 ;
    private static final int INT_TBL_DAT_DES_ITM = 4 ;
    private static final int INT_TBL_DAT_UNI_MED = 5 ;
    private static final int INT_TBL_DAT_COD_MOT = 6 ;
    private static final int INT_TBL_DAT_TXT_MOT = 7 ;
    private static final int INT_TBL_DAT_COD_BOD = 8 ;
    private static final int INT_TBL_DAT_BUT_BOD = 9 ;
    private static final int INT_TBL_DAT_CAN_MOV = 10;           //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_DAT_COS_UNI = 11;           //Precio de Venta
    private static final int INT_TBL_DAT_POR_DES = 12;          //Porcentaje de descuento
    private static final int INT_TBL_DAT_CHK_IVA = 13;           //Boolean Iva
    private static final int INT_TBL_DAT_TXT_IVA = 14;
    private static final int INT_TBL_DAT_TOT_DET = 15;           //Total de la venta o compra del producto
    private static final int INT_TBL_DAT_EST_REG = 16;           
    /**
     * <BR> V=Vacío  .- Si al momento de carga no tenia código asociado.
     * <BR> N=Nuevo  .- Si fue modificado después de la carga y estaba vacío.
     * <BR> E=Existe .- Si al momento de carga tenía código asociado.
     * <BR> M=Modificado .-Si fue modificado después de la carga y estaba existe.
     */
    
    //JTable: TblForPag
    private static final int INT_TBL_FOR_PAG_LINEA   = 0 ; 
    private static final int INT_TBL_FOR_PAG_DIA_CRE = 1 ;
    private static final int INT_TBL_FOR_PAG_FEC_VEN = 2 ;    
    private static final int INT_TBL_FOR_PAG_POR_RET = 3 ;    
    private static final int INT_TBL_FOR_PAG_MON_PAG = 4 ;    
    private static final int INT_TBL_FOR_PAG_DIA_GRA = 5 ;  
    private static final int INT_TBL_FOR_PAG_COD_RET = 6 ;  
    private static final int INT_TBL_FOR_PAG_COD_SRI = 7 ; 
    private static final int INT_TBL_FOR_PAG_APL_RET = 8 ; 
    private static final int INT_TBL_FOR_PAG_BAS_IMP = 9 ; 

    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_COT=2;  
    private int intIndiceCon=0;

    //Variables
    private Connection con;                      
    private Statement stm;                       
    private ResultSet rst;                            
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ToolBar objTooBar;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                    //Reportes del Sistema.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafDocLis objDocLis;                    //Instancia de clase que detecta cambios
    private ZafVenCon vcoItm;                       //*****************  
    private ZafVenCon vcoPrv;                       //*****************  
    private ZafVenCon vcoCom;                       //*****************  
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;//Editor: JTextField de consulta en celda.
    private ZafDatePicker dtpFecDoc;

    private JInternalFrame jfrThis;                 //Hace referencia a this
    private Vector vecForPag;                       //Vector que contiene el codigo del pago
    private Vector vecDat, vecCab, vecReg, vecAux;
    
    private BigDecimal bgdPorIva=BigDecimal.ZERO;
    private BigDecimal bgdValTotRetFte=BigDecimal.ZERO; //Valor Total de Retención a la Fuente
    private BigDecimal bgdValTotRetIva=BigDecimal.ZERO; //Valor Total de Retención al IVA
    
    private boolean blnConVenEme=false;
    private boolean blnChangeData=false;
    private boolean blnHayCam = false;  //Detecta ke se hizo cambios en el documento
    private boolean blnIsCosenco=false; //Conocer si la empresa por la que ingreso es COSENCO

    private final int intarreglonum[] = new int[10]; 
    private final int intarreglodia[] = new int[10]; 
    private final int intCanArr[]= new int[1];
    private int intCodBodPre=0;
    
    private String strBeforeValue,strAfterValue;
    private String strSQL, strAux;
    private String strCodPrv, strDesLarPrv;   
    private String strCodCom, strDesLarCom;   
    private String strCodEmp="", strCodLoc="", strCodCot="";
    private String strItm;
    private String strVer =" v3.02";    
    
    /*** Creates new form ZafCom01 ***/
    public ZafCom01(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }       

    public ZafCom01(ZafParSis obj, int codigoEmpresaCot, String codigoLocalCot, String codigoCodigoCot) 
    {
        this(obj);
        strCodEmp=String.valueOf(codigoEmpresaCot);
        strCodLoc=codigoLocalCot;
        strCodCot=codigoCodigoCot;
        blnConVenEme=true;
        initComponents();
        if (!configurarFrm())
            exitForm();
        else
            objTooBar.consultar();
        agregarDocLis();
    }  
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            //Configurar JTable: Detectar cambios de valores en los componentes.
            objDocLis=new ZafDocLis();
            //Configurar JTable: Detectar cambios de valores en las celdas del JTable
            objTblModLis=new ZafTblModLis();            
            
            objTooBar = new ToolBar(this);
            this.getContentPane().add(objTooBar,"South");
  
            //Fecha de documento
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            dtpFecDoc.setBounds(568, 10, 100, 20);
            panGenCab.add(dtpFecDoc); 
        
            //Nombres de los tabs
            tabCotCom.setTitleAt(0,"General");
            tabCotCom.setTitleAt(1,"Forma de Pago");
        
            vecForPag = new java.util.Vector();
            getIva();

            /* Ventanas de consulta. */
            configurarVenConItm();
            configurarVenConPrv();
            configurarVenConCom();
            
            configurarTblDat();            
            
            cargarBodPre();
        
            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            /* Listener para detectar cambios en combo de pagos. */
            ActLisForPag objActLis = new ActLisForPag();
            cboForPag.addActionListener(objActLis);
        
            /* Metodo para agregar o eliminar lineas con enter y con escape. */
            objUti.verDecimalesEnTabla(tblForPag, INT_TBL_FOR_PAG_MON_PAG, objParSis.getDecimalesMostrar());
            
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblForPag, INT_TBL_FOR_PAG_LINEA);

            objUti.desactivarCom(this);  
            
            //Saber si la empresa que ingreso es COSENCO
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    private boolean configurarTblDat()
    {
        boolean blnRes=false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();            
            vecCab.add(INT_TBL_DAT_LINEA,""); 
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_ITM_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_DES_ITM,"Descripción");            
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_COD_MOT,"Cód.Mot.");
            vecCab.add(INT_TBL_DAT_TXT_MOT,"Motivo");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Bodega");            
            vecCab.add(INT_TBL_DAT_BUT_BOD,"");
            vecCab.add(INT_TBL_DAT_CAN_MOV,"Cantidad");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");            
            vecCab.add(INT_TBL_DAT_POR_DES,"Descuento");
            vecCab.add(INT_TBL_DAT_CHK_IVA,"IVA");
            vecCab.add(INT_TBL_DAT_TXT_IVA,"");
            vecCab.add(INT_TBL_DAT_TOT_DET,"Total");            
            vecCab.add(INT_TBL_DAT_EST_REG,"");
            
            objTblMod=new ZafTblMod(); 
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_DAT_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_MOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_POR_DES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT_DET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_ITM_ALT);
            arlAux.add("" + INT_TBL_DAT_COD_BOD);
            arlAux.add("" + INT_TBL_DAT_CAN_MOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());                   
            
            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_DES_ITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_MOT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TXT_MOT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(25);         
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setPreferredWidth(80);         
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_DAT_TOT_DET).setPreferredWidth(80);         

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TXT_IVA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_REG, tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);   
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_ITM_ALT);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            //vecAux.add("" + INT_TBL_DAT_COD_BOD);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD);
            vecAux.add("" + INT_TBL_DAT_CAN_MOV);
            vecAux.add("" + INT_TBL_DAT_COS_UNI);
            vecAux.add("" + INT_TBL_DAT_POR_DES);
            vecAux.add("" + INT_TBL_DAT_CHK_IVA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_IVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel;
                @Override 
                public void beforeEdit(ZafTableEvent evt){
                    intFilSel=tblDat.getSelectedRow();
                    blnChangeData = false;        
                    if(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CHK_IVA) != null)
                        strBeforeValue = objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CHK_IVA).toString();
                   
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG)==null){
                        objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);
                    }
                }        
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                     if(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CHK_IVA) != null)
                       strAfterValue = objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CHK_IVA).toString();
                   
                      if ((objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("M") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E")) && objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null){
                                objTblMod.setValueAt("D", intFilSel, INT_TBL_DAT_EST_REG);                            
                        }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V")){
                            if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)!=null)
                                objTblMod.setValueAt("N", intFilSel, INT_TBL_DAT_EST_REG);                            
                        }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("N")){
        	            if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null)
                                objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);                            
                        }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("D")){
        	            objTblMod.setValueAt("M", intFilSel, INT_TBL_DAT_EST_REG);                            
                        }
                                
                        if (!objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                            calculaDetalle();
                            blnChangeData = true;
                        }                        
                }
            });

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_MOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TXT_MOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_DET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.        
            int intColVen[]=new int[8];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=5;            
            intColVen[4]=6;          
            intColVen[5]=7;   
            intColVen[6]=9;   
            intColVen[7]=10;   
            int intColTbl[]=new int[8];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_ITM_ALT;
            intColTbl[2]=INT_TBL_DAT_DES_ITM;
            intColTbl[3]=INT_TBL_DAT_COS_UNI;
            intColTbl[4]=INT_TBL_DAT_TXT_IVA;
            intColTbl[5]=INT_TBL_DAT_UNI_MED;   
            intColTbl[6]=INT_TBL_DAT_COD_MOT;   
            intColTbl[7]=INT_TBL_DAT_TXT_MOT;   
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);  
            tcmAux.getColumn(INT_TBL_DAT_ITM_ALT).setCellEditor(objTblCelEdiTxtVcoItm);  
            objTblCelEdiTxtVcoItm.addTableEditorListener(new ZafTableAdapter() {   
                int intFilSel;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    intFilSel=tblDat.getSelectedRow(); 
                    blnChangeData = false;                            
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT)!=null)
                        strBeforeValue = objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT).toString();
                    else 
			strBeforeValue = "";
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG)==null){
                        objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT)!=null)
                        strAfterValue = objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT).toString();
                    else
                        strAfterValue ="";
                    if ((objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("M") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E")) && objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null){
                            objTblMod.setValueAt("D", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V")){
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)!=null)
                            objTblMod.setValueAt("N", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("N")){
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null)
                            objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("D")){
                        objTblMod.setValueAt("M", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }
                    
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_TXT_IVA)!=null){
                        if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_TXT_IVA).equals("S"))
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_IVA);
                        else
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_IVA);
                    }
                    if (!objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;        
                        objTblMod.setValueAt( ""+intCodBodPre , intFilSel, INT_TBL_DAT_COD_BOD);
                        calculaDetalle();
                    }     
                    /*
                     * 2012-05-09: Se modifica para que no se permita hacer cotizaciones/ordenes de compra para items terminal L              
                     * Nota: Solo se permitirá hacer cotizaciones de items terminal L en COSENCO.
                     */                    
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT)!=null){
                        strItm = objTblMod.getValueAt(intFilSel,INT_TBL_DAT_ITM_ALT).toString();
                        if (strItm.endsWith("L") && !blnIsCosenco){
                            mostrarMsgInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                            objTblMod.removeRow(intFilSel);  
                        }
                    }
                }
            });
              
            ButFndItm butFndItm = new ButFndItm(tblDat, INT_TBL_DAT_BUT_ITM); //*****
            
            intColVen=null;
            intColTbl=null;
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(new RenderDecimales(objParSis.getDecimalesMostrar()) );
            tcmAux.getColumn(INT_TBL_DAT_POR_DES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    intFilSel=tblDat.getSelectedRow(); 
                    intColSel=tblDat.getSelectedColumn(); 
                    blnChangeData = false;
                    if (objTblMod.getValueAt(intFilSel, intColSel)!=null)
                        strBeforeValue = objTblMod.getValueAt(intFilSel, intColSel).toString();
                    else
                        strBeforeValue = "";
                    if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG)==null){
                        objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (objTblMod.getValueAt(intFilSel, intColSel)!=null)
                        strAfterValue = objTblMod.getValueAt(intFilSel, intColSel).toString();
                    else
                        strAfterValue ="";
                    if ((objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("M") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E")) && objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null){
                            objTblMod.setValueAt("D", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V")){
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)!=null)
                            objTblMod.setValueAt("N", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("N")){
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITM_ALT)==null)
                            objTblMod.setValueAt("V", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }else if (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("E") || objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("D")){
                        objTblMod.setValueAt("M", intFilSel, INT_TBL_DAT_EST_REG);                            
                    }
                           
                    if (objTblMod.getValueAt(intFilSel, intColSel)==null ||  objTblMod.getValueAt(intFilSel,intColSel).equals(""))
                        objTblMod.setValueAt("0",intFilSel, intColSel);
                    if (intColSel==INT_TBL_DAT_POR_DES){
                        if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            mostrarMsgInf("Error! Porcentaje de Descuento ");
                            objTblMod.setValueAt(strBeforeValue, intFilSel, INT_TBL_DAT_POR_DES);
                        }
                    }
                    if (!objTblMod.getValueAt(intFilSel,INT_TBL_DAT_EST_REG).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true; 
                        calculaDetalle();
                    }                                        
                }
            });
            
            ButBodEmp butBodEmp = new ButBodEmp(tblDat, INT_TBL_DAT_BUT_BOD);
              
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);            

            blnRes=true;    
        }
        catch(Exception e) {
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }  
   
    private class ZafMouMotAda extends MouseMotionAdapter
    {
        @Override
        public void mouseMoved(MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol) {                   
                case INT_TBL_DAT_LINEA: strMsg=""; break;
                case INT_TBL_DAT_ITM_ALT: strMsg="Código Item"; break;
                case INT_TBL_DAT_DES_ITM: strMsg="Nombre del Item"; break; 
                case INT_TBL_DAT_CAN_MOV: strMsg="Cantidad"; break;
                case INT_TBL_DAT_COS_UNI: strMsg="Costo Unitario"; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements TableModelListener
    {
        @Override
        public void tableChanged(TableModelEvent e)
        {
            switch (e.getType())
            {
                case TableModelEvent.INSERT:
                    calcularTotal();
                    calcularPagos();                    
//                    objTblMod.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_DAT_EST_REG);
                    break;
                case TableModelEvent.DELETE:
                    calcularTotal();
                    calcularPagos();
                    break;
                case TableModelEvent.UPDATE:                         
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
    
    private class ButBodEmp extends ZafTableColBut{
        public ButBodEmp(JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
 
        @Override
        public void butCLick() {
            int intNumFil = tblDat.getSelectedRow();
            if(intNumFil >= 0 ) {
                blnChangeData = false;
                String strCodItm = ((objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COD_ITM)==null)?"":objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COD_ITM).toString());
                llamarVentanaFac(intNumFil, strCodItm);
            }
        }
    }    
    
    private void llamarVentanaFac(int intCol, String strCodItm)
    {
        String strSql="";
        if(!strCodItm.equals("")) 
        {    
            //Armar sentencia SQL
            if(objParSis.getCodigoUsuario()==1) {
                strSql ="";
                strSql+=" SELECT a.co_bod, a1.tx_nom, a.nd_canDis AS nd_stkact";
                strSql+=" FROM tbm_invbod AS a ";
                strSql+=" INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)  " ;
                strSql+=" WHERE a1.st_reg NOT IN ('I')";
                strSql+=" AND a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_itm="+strCodItm+"";
                strSql+=" ORDER BY a.co_bod";
            }
            else{  // José Marín M. 5/Nov/2014 --> Solicitado por Ingrid Lino, por cambio en manejo de OC 
                strSql ="";
                strSql+=" SELECT a1.co_emp, a.co_bod, a1.tx_nom, a.nd_canDis AS nd_stkact";
                strSql+=" FROM tbm_invbod AS a";
                strSql+=" INNER JOIN tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)";
                strSql+=" INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                strSql+=" ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod AND a2.co_tipDoc=2 AND a2.co_mnu=35 AND a2.co_usr="+ objParSis.getCodigoUsuario() +") ";
                strSql+=" WHERE a1.st_reg NOT IN ('I')";
                strSql+=" AND a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_itm="+strCodItm+" ";
                strSql+=" ORDER BY a.co_bod";
            }  
            Ventas.ZafVen02.ZafVen02_02 obj = new  Ventas.ZafVen02.ZafVen02_02(JOptionPane.getFrameForComponent(this), true,  objParSis, strSql);
            obj.show();  
            if(obj.acepta()){ 
                objTblMod.setValueAt(obj.GetCamSel(1), intCol, INT_TBL_DAT_COD_BOD);
            }     
            obj.dispose();
            obj=null;
        }
        else 
            mostrarMsgInf("AGREGE UN ITEM EN LA COTIZACIÓN. ");
    }        
    
    private class ButFndItm extends ZafTableColBut{
        public ButFndItm(JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        @Override
        public void butCLick() {
            listaItems();
            tblDat.requestFocus();
            tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MOV, false, false);
        }
    }
    
    private void listaItems()
    {
        int intNumFil=tblDat.getSelectedRow();
        String strEstReg="";
        vcoItm.setTitle("Listado de Items"); 
        vcoItm.show();
        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE) 
        {
            objTblMod.setValueAt(vcoItm.getValueAt(1),intNumFil,INT_TBL_DAT_COD_ITM); 
            objTblMod.setValueAt(vcoItm.getValueAt(2),intNumFil,INT_TBL_DAT_ITM_ALT); 
            objTblMod.setValueAt(vcoItm.getValueAt(3),intNumFil,INT_TBL_DAT_DES_ITM); 
            objTblMod.setValueAt(vcoItm.getValueAt(7),intNumFil,INT_TBL_DAT_UNI_MED); 
            objTblMod.setValueAt(vcoItm.getValueAt(9),intNumFil,INT_TBL_DAT_COD_MOT); 
            objTblMod.setValueAt(vcoItm.getValueAt(10),intNumFil,INT_TBL_DAT_TXT_MOT); 
            objTblMod.setValueAt( ""+intCodBodPre , intNumFil, INT_TBL_DAT_COD_BOD); 
            objTblMod.setValueAt(vcoItm.getValueAt(5),intNumFil,INT_TBL_DAT_COS_UNI); 
            objTblMod.setValueAt(vcoItm.getValueAt(6),intNumFil,INT_TBL_DAT_TXT_IVA);  

            if(objTblMod.getValueAt(intNumFil,INT_TBL_DAT_CAN_MOV)==null)
               objTblMod.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_CAN_MOV); 
            if(objTblMod.getValueAt(intNumFil,INT_TBL_DAT_POR_DES)==null)
               objTblMod.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_POR_DES); 
            if(objTblMod.getValueAt(intNumFil,INT_TBL_DAT_TOT_DET)==null)
               objTblMod.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DAT_TOT_DET); 

            blnChangeData = false;                            
            if (objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT)!=null)
                strBeforeValue = objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT).toString();
            else 
                strBeforeValue = "";
            
            if (objTblMod.getValueAt(intNumFil,INT_TBL_DAT_EST_REG)==null){
                objTblMod.setValueAt("V", intNumFil, INT_TBL_DAT_EST_REG);
            }
            if (objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT)!=null)
                strAfterValue = objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT).toString();
            else
                strAfterValue ="";
            
            strEstReg=objTblMod.getValueAt(intNumFil,INT_TBL_DAT_EST_REG).toString();
            if ((strEstReg.equals("M") || strEstReg.equals("E")) && objTblMod.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)==null){
                    objTblMod.setValueAt("D",intNumFil , INT_TBL_DAT_EST_REG);                            
            }else if (strEstReg.equals("V")){
                if (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)!=null)
                    objTblMod.setValueAt("N", intNumFil, INT_TBL_DAT_EST_REG);                            
            }else if (strEstReg.equals("N")){
                if (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_ITM_ALT)==null)
                    objTblMod.setValueAt("V", intNumFil, INT_TBL_DAT_EST_REG);                            
            }
            else if (strEstReg.equals("E") || strEstReg.equals("D")){
                objTblMod.setValueAt("M", intNumFil, INT_TBL_DAT_EST_REG);                            
            }

            if (objTblMod.getValueAt(intNumFil,INT_TBL_DAT_TXT_IVA)!=null){
                if (objTblMod.getValueAt(intNumFil,INT_TBL_DAT_TXT_IVA).equals("S"))
                    objTblMod.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_IVA);
                else
                    objTblMod.setValueAt(false, intNumFil, INT_TBL_DAT_CHK_IVA);
            }

            if(objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT)!=null){
                strItm = objTblMod.getValueAt(intNumFil,INT_TBL_DAT_ITM_ALT).toString();
                if (strItm.endsWith("L") && !blnIsCosenco){
                    mostrarMsgInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                    objTblMod.removeRow(intNumFil);     
                    return;
                }
            }
            calculaDetalle();
        }
    }
    
    private void cargarBodPre()
    {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                //Armar sentencia SQL.
                if(objParSis.getCodigoUsuario()==1) {
                    strSQL ="";
                    strSQL+=" SELECT b.co_tipper, b.tx_descor, round(a.nd_ivaVen,2) as porIva, bod.co_bod ";
                    strSQL+=" FROM tbm_emp AS a";
                    strSQL+=" LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp AND b.co_tipper=a.co_tipper)";
                    strSQL+=" LEFT JOIN tbr_bodloc AS bod ";
                    strSQL+=" ON(bod.co_emp=a.co_emp AND bod.co_loc="+objParSis.getCodigoLocal()+" AND bod.st_reg='P') ";
                    strSQL+=" WHERE a.co_emp="+objParSis.getCodigoEmpresa();
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT b.co_tipper, b.tx_descor, round(a.nd_ivaVen,2) as porIva, a2.co_bod";
                    strSQL+=" FROM tbm_emp AS a";
                    strSQL+=" LEFT JOIN tbm_tipper AS b on(b.co_emp=a.co_emp AND b.co_tipper=a.co_tipper)";
                    strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr AS a2 ";
                    strSQL+=" ON (b.co_emp=a2.co_emp AND "+objParSis.getCodigoLocal()+"=a2.co_loc ";
                    strSQL+=" AND a2.co_tipDoc=2 AND a2.co_mnu=35 AND a2.co_usr="+ objParSis.getCodigoUsuario() + ") ";
                    strSQL+=" WHERE a.co_emp="+ objParSis.getCodigoEmpresa() + " AND a2.st_reg='P'";
                }
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodBodPre = rstLoc.getInt("co_bod");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
    
    private boolean configurarVenConCom()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_usr,a.tx_nom ";
            strSQL+=" FROM tbm_usr AS a ";
            strSQL+=" INNER JOIN tbr_usrEmp AS a1 ON a.co_usr=a1.co_usr";
            strSQL+=" WHERE a.st_reg NOT IN('I')";
            strSQL+=" AND a1.st_com='S'"; /* Presenta solo los compradores configurados. */
            strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a.tx_nom"; 

            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
        
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide";
            strSQL+=" FROM tbr_cliloc AS a1 ";
            strSQL+=" INNER JOIN tbm_cli AS a ON (a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) ";
            strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal();
            strSQL+=" AND a.st_reg IN('A','N') AND a.st_prv='S' ";
            strSQL+=" ORDER BY a.tx_nom ";   
            
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
     
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_itm");    //1
            arlCam.add("tx_codAlt"); //2
            arlCam.add("tx_nomItm"); //3
            arlCam.add("nd_stkAct"); //4
            arlCam.add("nd_CosUni"); //5
            arlCam.add("st_ivaCom"); //6 
            arlCam.add("tx_descor"); //7
            arlCam.add("st_ser");    //8
            arlCam.add("co_mot");    //9
            arlCam.add("tx_tipMot"); //10
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Descripción");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Iva.");
            arlAli.add("Uni.Med.");
            arlAli.add("Servicio");
            arlAli.add("Cód.Mot.");
            arlAli.add("Motivo");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("60");
            arlAncCol.add("50");
            arlAncCol.add("50");
              
            //Armar sentencia SQL
            strSQL ="";  
            strSQL+=" SELECT a3.co_emp, a3.co_itm, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a4.tx_desCor ";      
            strSQL+="      , CASE WHEN a3.nd_cosUni IS NULL THEN 0 ELSE ROUND(a3.nd_cosUni,2) END AS nd_cosUni";
            strSQL+="      , ROUND(a1.nd_canDis,2) AS nd_stkAct, a3.st_ser, a3.st_ivaCom, a5.co_mot, a5.tx_tipMot";
            strSQL+=" FROM ";
            strSQL+=" ( ";
            strSQL+="    SELECT z.co_emp, z.co_itmMae, SUM(z.nd_canDis) AS nd_canDis ";
            strSQL+="    FROM("; 
            strSQL+="        SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_canDis, a2.co_itmMae ";
            strSQL+="        FROM tbm_invBod AS a1  ";
            strSQL+="        INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) ";
            strSQL+="        INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod )";
            strSQL+="        WHERE a1.co_emp="+objParSis.getCodigoEmpresa();
            strSQL+="        AND a3.co_loc="+objParSis.getCodigoLocal()+" AND a3.st_reg='P' ";
            strSQL+="    ) AS z";
            strSQL+="    GROUP BY z.co_emp, z.co_itmMae";  
            strSQL+=" ) AS a1 "; 
            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itmMae=a2.co_itmMae) ";
            strSQL+=" INNER JOIN (tbm_inv AS a3 LEFT OUTER JOIN tbm_var AS a4 ON (a3.co_uni=a4.co_reg) ) ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";          
            strSQL+=" INNER JOIN tbm_motDoc AS a5 ON a3.co_emp=a5.co_emp AND (CASE WHEN a3.st_ser ='N' THEN 'B' ELSE a3.st_ser END) = a5.tx_tipMot AND a5.st_reg = 'A' ";
                  System.out.println("j j j j j j " + strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=7;
               
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.CENTER); 
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private void buscarComprador(String campo,String strBusqueda,int tipo)
    {
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
    
    private void buscarProveedor(String campo,String strBusqueda,int tipo)
    {
        vcoPrv.setTitle("Listado de Proveedores"); 
        if (vcoPrv.buscar(campo, strBusqueda ))
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
            calcularPagos();
            cargarDatComPrv();
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
                if (txtCodCom.getText().equals("")){
                    calcularPagos();
                    cargarDatComPrv(); 
                }
            }
            else{
                txtCodPrv.setText(strCodPrv);
                txtNomPrv.setText(strDesLarPrv);
            }
        }
    }    
    
    private void cargarDatComPrv()
    {
        Connection conPrvCom;
        Statement stmPrvCom;
        ResultSet rstPrvCom;
        String strSql ="";
        try 
        {
            conPrvCom=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conPrvCom!=null)
            {
                stmPrvCom = conPrvCom.createStatement();
                //Armar sentencia SQL
                strSql+=" SELECT * FROM tbm_cli  ";
                strSql+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                strSql+=" AND co_cli = " + Integer.parseInt(txtCodPrv.getText());
                rstPrvCom = stmPrvCom.executeQuery(strSql);
                if(rstPrvCom.next())
                {
                    int intCodVen = rstPrvCom.getInt("co_ven");
                    txtCodCom.setText(rstPrvCom.getString("co_ven"));
                    Librerias.ZafConsulta.ZafConsulta objFndVen = 
                    new ZafConsulta( JOptionPane.getFrameForComponent(this)
                        ,"Codigo,Nombre","a.co_usr,a.tx_nom"
                        ," Select a.co_usr, a.tx_nom from tbm_usr as a inner join tbr_usrEmp as a1 on a.co_usr=a1.co_usr where a1.st_com='S'"
                        , txtCodCom.getText(), objParSis.getStringConexion()
                        ,objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()
                    );
                    objFndVen.setTitle("Listado compradores");
                    if(objFndVen.buscar("a.co_usr = " + intCodVen )){
                        txtCodCom.setText(objFndVen.GetCamSel(1));
                        txtNomCom.setText(objFndVen.GetCamSel(2));
                    }    
                }
                stmPrvCom.close();
                stmPrvCom=null;
                rstPrvCom.close();
                rstPrvCom=null;
                conPrvCom.close();
                conPrvCom=null;
            }
        }
        catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
    }   
    
    @Override
    protected void finalize() throws Throwable{   
        Runtime.getRuntime().gc();
        super.finalize();
    }

    private class RenderDecimales extends JLabel implements TableCellRenderer {
        int numeroDecimales = 0;
        public RenderDecimales(int numDec) {
            this.numeroDecimales=numDec ;
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
            }else{
                setBorder(null);
            }
            
            this.setText("" + objutil.redondeo(dblSubPro, numeroDecimales));
            this.setFont(new java.awt.Font("Tahoma", 0, 11));
            this.setOpaque(true);
            setBackground(new Color(201,223,245));
            setForeground(new Color(0, 0, 0));
            
            return this;
        }
    }
        
    /** Cerrar la aplicación. **/
    private void exitForm() 
    {
        dispose();
    }   
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabCotCom = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblCom = new javax.swing.JLabel();
        lblFecDoc = new javax.swing.JLabel();
        txtAte = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtCodCom = new javax.swing.JTextField();
        txtNomCom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        lblNumCot = new javax.swing.JLabel();
        txtNumCot = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        lblDirPrv = new javax.swing.JLabel();
        txtDirPrv = new javax.swing.JTextField();
        txtCodPrv = new javax.swing.JTextField();
        txtNomPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenSur = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        panObs1 = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panObs2 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panTot = new javax.swing.JPanel();
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panForPag = new javax.swing.JPanel();
        panForPagCab = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        cboForPag = new javax.swing.JComboBox();
        spnForPag = new javax.swing.JScrollPane();
        tblForPag = new javax.swing.JTable();
        lblTit = new javax.swing.JLabel();

        setClosable(true);
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

        tabCotCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCotCom.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 75));
        panGenCab.setLayout(null);

        lblCom.setText("Comprador:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblCom);
        lblCom.setBounds(430, 30, 80, 20);

        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(430, 10, 108, 20);
        panGenCab.add(txtAte);
        txtAte.setBounds(500, 50, 170, 20);

        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblAte);
        lblAte.setBounds(430, 50, 80, 20);

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
        panGenCab.add(txtCodCom);
        txtCodCom.setBounds(500, 30, 30, 20);

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
        panGenCab.add(txtNomCom);
        txtNomCom.setBounds(530, 30, 120, 20);

        butCom.setText("...");
        butCom.setPreferredSize(new java.awt.Dimension(20, 20));
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panGenCab.add(butCom);
        butCom.setBounds(650, 30, 20, 20);

        lblNumCot.setText("No. Cotización:");
        panGenCab.add(lblNumCot);
        lblNumCot.setBounds(4, 10, 100, 20);

        txtNumCot.setBackground(objParSis.getColorCamposSistema());
        txtNumCot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumCot.setMaximumSize(null);
        txtNumCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCotActionPerformed(evt);
            }
        });
        panGenCab.add(txtNumCot);
        txtNumCot.setBounds(90, 10, 92, 20);

        lblPrv.setText("Proveedor:");
        panGenCab.add(lblPrv);
        lblPrv.setBounds(4, 30, 100, 20);

        lblDirPrv.setText("Dirección:");
        panGenCab.add(lblDirPrv);
        lblDirPrv.setBounds(4, 50, 100, 20);

        txtDirPrv.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtDirPrv);
        txtDirPrv.setBounds(90, 50, 330, 20);

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
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(90, 30, 50, 20);

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
        panGenCab.add(txtNomPrv);
        txtNomPrv.setBounds(140, 30, 260, 20);

        butPrv.setText("...");
        butPrv.setPreferredSize(new java.awt.Dimension(20, 20));
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenCab.add(butPrv);
        butPrv.setBounds(400, 30, 20, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setPreferredSize(new java.awt.Dimension(452, 419));
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

        panGenSur.setLayout(new java.awt.BorderLayout());

        panObs.setLayout(new java.awt.GridLayout(2, 1));

        panObs1.setPreferredSize(new java.awt.Dimension(250, 25));
        panObs1.setLayout(new java.awt.BorderLayout());

        lblObs1.setText("Observación 1:"); // NOI18N
        panObs1.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panObs1.add(spnObs1, java.awt.BorderLayout.CENTER);

        panObs.add(panObs1);

        panObs2.setPreferredSize(new java.awt.Dimension(250, 25));
        panObs2.setLayout(new java.awt.BorderLayout());

        lblObs2.setText("Observación 2:"); // NOI18N
        panObs2.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panObs2.add(spnObs2, java.awt.BorderLayout.CENTER);

        panObs.add(panObs2);

        panGenSur.add(panObs, java.awt.BorderLayout.CENTER);

        panTot.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panTot.add(lblSubTot);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panTot.add(txtSub);

        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panTot.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panTot.add(txtIva);

        lblTot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panTot.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panTot.add(txtTot);

        panGenSur.add(panTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenSur, java.awt.BorderLayout.SOUTH);

        tabCotCom.addTab("General", panGen);

        panForPag.setLayout(new java.awt.BorderLayout());

        panForPagCab.setEnabled(false);
        panForPagCab.setPreferredSize(new java.awt.Dimension(249, 60));
        panForPagCab.setLayout(null);

        lblForPag.setText("Forma de Pago:");
        lblForPag.setPreferredSize(new java.awt.Dimension(100, 25));
        panForPagCab.add(lblForPag);
        lblForPag.setBounds(200, 10, 120, 25);

        cboForPag.setPreferredSize(new java.awt.Dimension(200, 25));
        panForPagCab.add(cboForPag);
        cboForPag.setBounds(320, 10, 150, 25);

        panForPag.add(panForPagCab, java.awt.BorderLayout.NORTH);

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

        tabCotCom.addTab("Forma de Pago", panForPag);

        getContentPane().add(tabCotCom, java.awt.BorderLayout.CENTER);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusGained
        strCodCom=txtCodCom.getText();
        txtCodCom.selectAll();
    }//GEN-LAST:event_txtCodComFocusGained

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        strDesLarCom=txtNomCom.getText();
        txtNomCom.selectAll();
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtNomPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusGained
        strDesLarPrv=txtNomPrv.getText();
        txtNomPrv.selectAll();
    }//GEN-LAST:event_txtNomPrvFocusGained

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusLost
        if (!txtCodCom.getText().equalsIgnoreCase(strCodCom))
        {
            if (txtCodCom.getText().equals(""))
            {
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else {
                buscarComprador("a.co_usr",txtCodCom.getText(),0);
            }
        }
        else
            txtCodCom.setText(strCodCom);
       
    }//GEN-LAST:event_txtCodComFocusLost

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        String strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
            cerrarObj();
            Runtime.getRuntime().gc();
            dispose();
        }  
    }//GEN-LAST:event_exitForm
    
    private void cerrarObj() {
        try{
            vcoItm.dispose(); 
            vcoItm=null;  
            vcoCom.dispose();
            vcoCom=null;  

            objUti=null;
            objTooBar=null;

            objDocLis=null;
            objParSis=null;
            dtpFecDoc=null;
            objTblCelEdiTxtVcoItm=null;
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }                   
    }
    
    
    private void txtNomPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusLost
        if (!txtNomPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtNomPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtNomPrv.setText("");
            }
            else  {
                buscarProveedor("a.tx_nom",txtNomPrv.getText(),1);
            }
        }
        else
            txtNomPrv.setText(strDesLarPrv);
             
    }//GEN-LAST:event_txtNomPrvFocusLost

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
        if (!txtNomCom.getText().equalsIgnoreCase(strDesLarCom))
        {
            if (txtNomCom.getText().equals(""))
            {
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else {
               buscarComprador("a.co_usr","",0);
            }
        }
        else
            txtNomCom.setText(strDesLarCom);
       
    }//GEN-LAST:event_txtNomComFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtCodPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtNomPrv.setText("");
            }
            else   {
                buscarProveedor("a.co_cli",txtCodPrv.getText(),0);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
    }//GEN-LAST:event_txtCodPrvFocusLost

    private void txtNomPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvActionPerformed
       txtNomPrv.transferFocus();
   
    }//GEN-LAST:event_txtNomPrvActionPerformed

    private void txtCodComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodComActionPerformed
        txtCodCom.transferFocus();
    }//GEN-LAST:event_txtCodComActionPerformed
  
    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
      txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
       txtNomCom.transferFocus();
    }//GEN-LAST:event_txtNomComActionPerformed
    
    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        buscarComprador("a.co_usr","",0);
    }//GEN-LAST:event_butComActionPerformed

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        vcoPrv.setTitle("Listado de Proveedores");         
        vcoPrv.setCampoBusqueda(1);
        vcoPrv.show();
        if (vcoPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
            if (txtCodCom.getText().equals("")) {
                calcularPagos();
                cargarDatComPrv(); 
            }
        } 
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtNumCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumCotActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCom;
    private javax.swing.JButton butPrv;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDirPrv;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNumCot;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panForPag;
    private javax.swing.JPanel panForPagCab;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenSur;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObs1;
    private javax.swing.JPanel panObs2;
    private javax.swing.JPanel panTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCotCom;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblForPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCodCom;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtDirPrv;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomCom;
    private javax.swing.JTextField txtNomPrv;
    private javax.swing.JTextField txtNumCot;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
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
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE); 
    }    
    
    /* Muestra mensaje para validar los campos. */
    private void mostrarMsgValCam(String strNomCampo){
        String strTit="Mensaje del sistema Zafiro";
        String strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }    
         
    private void setEditable(boolean editable)
    {
        if (editable==true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        else  {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }      
    }
   
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis(){
        //Cabecera
        txtNumCot.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtNomPrv.getDocument().addDocumentListener(objDocLis);
        txtDirPrv.getDocument().addDocumentListener(objDocLis);
        //dtpFecDoc.getDocument().addDocumentListener(objDocLis);
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
        
    private void noEditable(boolean blnEditable)
    {
        java.awt.Color colBack = txtNumCot.getBackground();
        txtNumCot.setEditable(blnEditable);
        txtNumCot.setBackground(colBack);
            
        colBack = txtSub.getBackground();      

        //txtSub.setEnabled(blnEditable);
        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);        

        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);        

        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);        
    }
    
    private void limpiarFrm() 
    {
        //Cabecera
        strCodPrv="";
        strDesLarPrv="";   
        strCodCom="";
        strDesLarCom="";
              
        txtNumCot.setText("");
        txtCodPrv.setText("");
        txtNomPrv.setText("");
        txtDirPrv.setText("");
        dtpFecDoc.setText("");
        txtCodCom.setText("");
        txtNomCom.setText("");
        txtAte.setText("");
        
        //Detalle        
        objTblMod.removeAllRows();         

        //Pie de pagina
        txaObs1.setText("");
        txaObs2.setText("");
        txtSub.setText("0");
        txtIva.setText("0");
        //txtDes.setText("0");
        txtTot.setText("0");
        lblTit.setText("Cotizaciones");

         //Detalle de Pagos
        while(tblForPag.getRowCount()>0)
            ((DefaultTableModel)tblForPag.getModel()).removeRow(0);
            //cboForPag.setSelectedIndex(-1);
            //cboForPag.removeAllItems();
    }    
    
    private void calculaDetalle()
    {
        BigDecimal bgdCan=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        BigDecimal bgdDscto=BigDecimal.ZERO; 
        BigDecimal bgdPorDscto=BigDecimal.ZERO; 
        BigDecimal bgdTotDet=BigDecimal.ZERO; 
        int intNumFil = tblDat.getSelectedRow();
        
        if (objTblMod.getValueAt( intNumFil , INT_TBL_DAT_CAN_MOV)==null)
            objTblMod.setValueAt("0",intNumFil, INT_TBL_DAT_CAN_MOV);
        if (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COS_UNI)==null)
            objTblMod.setValueAt("0",intNumFil, INT_TBL_DAT_COS_UNI);
        if (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_POR_DES)==null)
            objTblMod.setValueAt("0",intNumFil, INT_TBL_DAT_POR_DES);
        
        bgdCan=new BigDecimal (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CAN_MOV)==null?"0":(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CAN_MOV).toString().equals("")?"0":objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CAN_MOV).toString()) );
        bgdCosUni=new BigDecimal (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intNumFil, INT_TBL_DAT_COS_UNI).toString()) );
        bgdPorDscto=new BigDecimal (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_POR_DES)==null?"0":(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_POR_DES).toString().equals("")?"0":objTblMod.getValueAt(intNumFil, INT_TBL_DAT_POR_DES).toString()) );
        
        if(bgdCan.compareTo(BigDecimal.ZERO)>0){
            bgdDscto=(bgdCan.multiply(bgdCosUni)).multiply( (bgdPorDscto.divide(BigDecimal.valueOf(100)) ) );
        } 
        else{
            bgdDscto=BigDecimal.ZERO;
        }
        
        bgdTotDet=objUti.redondearBigDecimal( ((bgdCan.multiply(bgdCosUni)).subtract(bgdDscto)), 3);
        bgdTotDet=objUti.redondearBigDecimal(  bgdTotDet, objParSis.getDecimalesMostrar());
        objTblMod.setValueAt(""+bgdTotDet, intNumFil, INT_TBL_DAT_TOT_DET);
        calcularTotal();
    }    

    private void calcularTotal()
    {
        //System.out.println("calcularTotal");
        BigDecimal bgdSub=BigDecimal.ZERO;
        BigDecimal bgdIva=BigDecimal.ZERO;
        BigDecimal bgdTmp=BigDecimal.ZERO;
        
        for (int i=0;i<objTblMod.getRowCountTrue();i++) { 
            bgdSub=bgdSub.add(objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString()) , objParSis.getDecimalesMostrar()) );
            
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA)){
                bgdTmp=objUti.redondearBigDecimal (objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString()) , objParSis.getDecimalesMostrar() );
                bgdIva=bgdIva.add( bgdTmp.multiply(bgdPorIva.divide(BigDecimal.valueOf(100)))  );
            }
        }
        txtSub.setText( "" + objUti.redondearBigDecimal(bgdSub, objParSis.getDecimalesMostrar()) );
        txtIva.setText( "" + objUti.redondearBigDecimal(bgdIva, objParSis.getDecimalesMostrar()) );
        txtTot.setText( "" + objUti.redondearBigDecimal(bgdSub.add(bgdIva), objParSis.getDecimalesMostrar()) );
        calcularPagos();   
    }       
    
    private void calcularPagos()
    {
        //System.out.println("calcularPagos");
        if(cboForPag.getSelectedIndex()!=-1) /* Si hay forma de pago seleccionada. */
        {  
            int intVal =  intCanArr[0];
            int intSizeArl = intarreglodia.length;  
            intVal = intSizeArl - (intSizeArl-intVal); 

            Calendar objFec = Calendar.getInstance();
            int fecCot [] = dtpFecDoc.getFecha(dtpFecDoc.getText());
            if(fecCot!=null) { 
                objFec.set(Calendar.DAY_OF_MONTH, fecCot[0]); 
                objFec.set(Calendar.MONTH, fecCot[1] - 1);  
                objFec.set(Calendar.YEAR, fecCot[2]);  
            } 
            
            //Borrando los datos actuales del jtable
            while(tblForPag.getRowCount()>0) 
                ((DefaultTableModel)tblForPag.getModel()).removeRow(0);            

            BigDecimal bgdValTotDoc= objUti.redondearBigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()), objParSis.getDecimalesMostrar());
            //BigDecimal bgdValTotRet=BigDecimal.ZERO;   // Pendiente obtener el valor total de retenciones
            BigDecimal bgdValTotRet=calculaValorRetenciones();  
            
            //BigDecimal bgdValMonCal = objUti.redondearBigDecimal( bgdValTotDoc, objParSis.getDecimalesMostrar());  //Monto a Calcular los pagos (Sin incluir calculo de retenciones) 
            BigDecimal bgdValMonCal = objUti.redondearBigDecimal( (bgdValTotDoc.subtract(bgdValTotRet)), objParSis.getDecimalesMostrar());  //Monto a Calcular los pagos (Incluyendo calculo de retenciones) 

            ZafDatePicker dtpForPag =  new ZafDatePicker(new JFrame(),"d/m/y");
            int intDiaCre=0, numPag, diaCreAcum = 0, diaCreAnt = 0;
            BigDecimal bgdTmpAux=BigDecimal.ZERO;
            BigDecimal bgdValTotPag=bgdValTotRet;    /* El valor total pagado se inicializa con el valor pagado en retenciones. */
            int intFil=(tblForPag.getRowCount()-1);
            int intFilUltPag=0;
            for(int i=0; i<intVal; i++) 
            {
                intFil++;
                diaCreAnt = intDiaCre;
                intDiaCre = intarreglodia[i]; 
                numPag = intarreglonum[i];
                diaCreAcum = intDiaCre - diaCreAnt;
                
                if (intDiaCre!=0)
                    objFec.add(Calendar.DATE, diaCreAcum);                                                  

                dtpForPag.setAnio( objFec.get(Calendar.YEAR));
                dtpForPag.setMes( objFec.get(Calendar.MONTH)+1);
                dtpForPag.setDia(objFec.get(Calendar.DAY_OF_MONTH));

                BigDecimal bgdValMon=BigDecimal.ZERO;
                
                bgdTmpAux=objUti.redondearBigDecimal( (numPag==0)? BigDecimal.ZERO : (bgdValMonCal.divide(new BigDecimal("" + numPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar() );  
                bgdValTotPag=bgdValTotPag.add(bgdTmpAux);
                bgdTmpAux=objUti.redondearBigDecimal(bgdTmpAux, objParSis.getDecimalesMostrar());
                bgdValMon=bgdTmpAux;

                ((DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());  
                    tblForPag.setValueAt(new Integer(intDiaCre) , intFil, INT_TBL_FOR_PAG_DIA_CRE);  
                    tblForPag.setValueAt(dtpForPag.getText(), intFil, INT_TBL_FOR_PAG_FEC_VEN);
                    tblForPag.setValueAt(""+bgdValMon, intFil, INT_TBL_FOR_PAG_MON_PAG);
                    tblForPag.setValueAt(new Integer(0),  intFil, INT_TBL_FOR_PAG_DIA_GRA);
                
                if(i<intVal)
                    intFilUltPag=intFil;
            }
            
            BigDecimal bgdValUltPag=objUti.redondearBigDecimal(new BigDecimal(tblForPag.getValueAt( (intFilUltPag), INT_TBL_FOR_PAG_MON_PAG).toString()),objParSis.getDecimalesMostrar());
            bgdValUltPag = objUti.redondearBigDecimal ( bgdValUltPag.add(bgdValTotDoc.subtract(bgdValTotPag))  ,objParSis.getDecimalesMostrar());
            tblForPag.setValueAt(""+bgdValUltPag , (intFilUltPag), INT_TBL_FOR_PAG_MON_PAG);
        }                             
    }        
    
    //*******************************************************************************************************
    
    private BigDecimal calculaValorRetenciones()
    {
        //System.out.println("calculaValorRetenciones");
        BigDecimal bgdValTotRet=BigDecimal.ZERO;
        BigDecimal bgdValTmp=BigDecimal.ZERO;
        BigDecimal bgdValSubBien=BigDecimal.ZERO, bgdValIvaBien=BigDecimal.ZERO;
        BigDecimal bgdValSubSer=BigDecimal.ZERO, bgdValIvaSer=BigDecimal.ZERO; 
        BigDecimal bgdValSubTrans=BigDecimal.ZERO, bgdValIvaTrans=BigDecimal.ZERO; 
        String strMotBien="B", strMotSer="S", strMotTrans="T";
        int intCodMotBien=0, intCodMotSer=0, intCodMotTrans=0;
        bgdValTotRetFte=BigDecimal.ZERO;  //Se inicializa el valor total de retenciones
        bgdValTotRetIva=BigDecimal.ZERO;  //Se inicializa el valor total de retenciones    
        try{ 
            for(int i=0; i<objTblMod.getRowCountTrue(); i++) 
            {
                bgdValTmp=objUti.redondearBigDecimal (objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_DET).toString()) , objParSis.getDecimalesMostrar() );
                String strMot=objTblMod.getValueAt(i, INT_TBL_DAT_TXT_MOT)==null?"": objTblMod.getValueAt(i, INT_TBL_DAT_TXT_MOT).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_DAT_TXT_MOT).toString() + "";//Motivo Transacción Item
                if(strMot.equals(strMotBien)){  //Bien
                    intCodMotBien=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString()); 
                    bgdValSubBien=bgdValSubBien.add(bgdValTmp);
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA)) {
                        bgdValIvaBien=bgdValIvaBien.add(bgdValTmp.multiply(bgdPorIva.divide(BigDecimal.valueOf(100))));
                    }
                }
                else if(strMot.equals(strMotSer)){  //Servicio
                    intCodMotSer=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString()); 
                    bgdValSubSer=bgdValSubSer.add(bgdValTmp);
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA)) {
                        bgdValIvaSer=bgdValIvaSer.add(bgdValTmp.multiply(bgdPorIva.divide(BigDecimal.valueOf(100))));
                    }                    
                }
                else if(strMot.equals(strMotTrans)){ //Transporte
                    intCodMotTrans=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString()); 
                    bgdValSubTrans=bgdValSubTrans.add(bgdValTmp);
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA)) {
                        bgdValIvaTrans=bgdValIvaTrans.add(bgdValTmp.multiply(bgdPorIva.divide(BigDecimal.valueOf(100))));
                    }                      
                }
            }

            //System.out.println("bgdValSubBien: "+bgdValSubBien+" - bgdValIvaBien: "+bgdValIvaBien);
            //System.out.println("bgdValSubSer: "+bgdValSubSer+" - bgdValIvaSer: "+bgdValIvaSer);
            //System.out.println("bgdValSubTrans: "+bgdValSubTrans+" - bgdValIvaTrans: "+bgdValIvaTrans);            
            
            /* Genera retenciones por cada tipo de transacción. */
            if(bgdValSubBien.compareTo(BigDecimal.ZERO)>0){
                cargarForPagRet(intCodMotBien, bgdValSubBien, bgdValIvaBien); 
            }
            if(bgdValSubSer.compareTo(BigDecimal.ZERO)>0){
                cargarForPagRet(intCodMotSer, bgdValSubSer, bgdValIvaSer); 
            }
            if(bgdValSubTrans.compareTo(BigDecimal.ZERO)>0){
                cargarForPagRet(intCodMotTrans, bgdValSubTrans, bgdValIvaTrans); 
            }
        
            //System.out.println("bgdValTotRetFte: "+bgdValTotRetFte);
            //System.out.println("bgdValTotRetIva: "+bgdValTotRetIva);
            
            //Calcula el valor total de retenciones.
            bgdValTotRet=bgdValTotRetFte.add(bgdValTotRetIva);
            //System.out.println("Valor Total de Retenciones: "+bgdValTotRet);
        }
        //catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }        
        return bgdValTotRet;
    } 
   
    private void cargarForPagRet(int intCodMot, BigDecimal bgdBasImpSub, BigDecimal bgdBasImpIva) 
    {
        //System.out.println("cargarForPagRet");
        Connection conForPagRet;
        ResultSet rstForPagRet;
        Statement stmForPagRet;
        BigDecimal bgdBasImp=BigDecimal.ZERO, bgdValRet=BigDecimal.ZERO;
        try
        {
            conForPagRet=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conForPagRet!=null){
                stmForPagRet=conForPagRet.createStatement();
                String strFecDoc=""+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"";
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT x.co_tipRet, x.tx_descor, x.tx_deslar, x.tx_aplret, x.co_cta, x.tx_codsri";
                strSQL+="     , (CASE WHEN (x.tx_aplRet='S' AND x.fe_doc < '2007-07-01') THEN CAST( 0.01 AS NUMERIC) ELSE x.nd_porRet END) AS nd_porRet";
                strSQL+="     ,  x.nd_porret AS nd_porRetBRUTO";                
                strSQL+=" FROM (";
                strSQL+="    SELECT a5.co_tipret, a5.tx_descor, a5.tx_deslar, a5.nd_porret, a5.tx_aplret, a5.co_cta, a.tx_codsri ";
                strSQL+="         , CAST ( '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd")+"' AS DATE) AS fe_doc";
                strSQL+="    FROM tbm_polRet AS a";
                strSQL+="    INNER JOIN tbm_emp AS a2 ON (a.co_emp=a2.co_emp AND a.co_ageRet=a2.co_tipPer)"; /* Tipo de Persona: Empresa */
                strSQL+="    INNER JOIN tbm_cli AS a3 ON (a.co_emp=a3.co_emp AND a.co_sujRet=a3.co_tipPer)"; /* Tipo de Persona: Cliente */
                strSQL+="    LEFT OUTER JOIN tbm_motDoc AS a4 ON (a.co_emp=a4.co_emp AND a.co_mottra=a4.co_mot)";
                strSQL+="    LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a.co_emp=a5.co_emp AND a.co_tipret=a5.co_tipret)";
                strSQL+="    WHERE a.st_reg='A' AND a4.co_mot = "+intCodMot+""; 
                strSQL+="    AND a2.co_emp = "+ objParSis.getCodigoEmpresa();
                if(!txtCodPrv.getText().equals(""))
                    strSQL+="   AND a3.co_cli="+txtCodPrv.getText();
                strSQL+="    AND (CASE WHEN a.fe_vigHas IS NULL THEN '"+strFecDoc+"'>=fe_vigDes ELSE '"+strFecDoc+"'>=fe_vigDes AND '"+strFecDoc+"'<=fe_vigHas END)";
                strSQL+="    GROUP BY a5.co_tipret, a5.tx_descor, a5.tx_deslar, a5.nd_porret, a5.tx_aplret, a5.co_cta, a.tx_codsri";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.nd_porRet";
                //System.out.println("cargaForPag: "+strSQL);
                rstForPagRet = stmForPagRet.executeQuery(strSQL);  
                while (rstForPagRet.next())
                {
                    String strAplRet=rstForPagRet.getString("tx_aplret");
                    if(strAplRet.equals("S")) /*Retencion a la fuente */
                    {
                        bgdBasImp=objUti.redondearBigDecimal (bgdBasImpSub, objParSis.getDecimalesMostrar()); 
                        bgdValRet=objUti.redondearBigDecimal (bgdBasImpSub.multiply(new BigDecimal(rstForPagRet.getDouble("nd_porret")).divide(BigDecimal.valueOf(100))) , objParSis.getDecimalesMostrar());
                        bgdValTotRetFte=bgdValTotRetFte.add(bgdValRet);                        
                    }
                    else if (strAplRet.equals("I"))
                    {
                        bgdBasImp=BigDecimal.ZERO;
                        bgdValRet=BigDecimal.ZERO;
                        if(bgdBasImpIva.compareTo(BigDecimal.ZERO) > 0 )
                        {
                            bgdBasImp=objUti.redondearBigDecimal (bgdBasImpIva, objParSis.getDecimalesMostrar()); 
                            bgdValRet = objUti.redondearBigDecimal ( bgdBasImpIva.multiply(new BigDecimal(rstForPagRet.getDouble("nd_porret")).divide(BigDecimal.valueOf(100))) , objParSis.getDecimalesMostrar());
                            bgdValTotRetIva=bgdValTotRetIva.add(bgdValRet);
                        }
                    }
                    
                    /* Se agregan los pagos de retenciones. */
                    ((javax.swing.table.DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());
                        tblForPag.setValueAt("0",tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_DIA_CRE);
                        tblForPag.setValueAt(dtpFecDoc.getText(), tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_FEC_VEN);
                        tblForPag.setValueAt(rstForPagRet.getString("nd_porret"), tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_POR_RET);
                        tblForPag.setValueAt(bgdValRet , tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_MON_PAG);
                        tblForPag.setValueAt("0",tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_DIA_GRA);
                        tblForPag.setValueAt(rstForPagRet.getString("co_tipret") , tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_COD_RET);
                        tblForPag.setValueAt(rstForPagRet.getString("tx_codsri") , tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_COD_SRI);
                        tblForPag.setValueAt(strAplRet , tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_APL_RET);
                        tblForPag.setValueAt(bgdBasImp , tblForPag.getRowCount()-1, INT_TBL_FOR_PAG_BAS_IMP);
                }
                stmForPagRet.close();
                stmForPagRet=null;
                rstForPagRet.close();
                rstForPagRet=null;
                conForPagRet.close();
                conForPagRet=null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    }    
    
    /**
     * Función que obtiene el IVA que se debe cobrar en la empresa actual.
     */
    private void getIva(){
        bgdPorIva=objParSis.getPorcentajeIvaCompras();
        lblIva.setText("IVA " + bgdPorIva + "%");
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
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }       

    public class ToolBar extends ZafToolBar{
        public ToolBar(JInternalFrame jfrThis){
            super(jfrThis, objParSis);
        }
        
        @Override
        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) ){
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
        
        @Override
        public void clickAnterior() 
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) ){
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

        @Override
        public void clickSiguiente()
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if ((blnHayCam) ){
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
        
        @Override
        public void clickFin() 
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if ((blnHayCam) ){
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
        
        @Override
        public void clickAnular()
        {
            setEditable(false);
        }

        @Override
        public void clickConsultar() 
        {
            txtCodCom.setEnabled(true);
            setEditable(false);
        }

        @Override
        public void clickEliminar()
        {
            setEditable(false);
        }
                   
        @Override
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
                dtpFecDoc.setHoy();
                objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                     objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
                cboForPag.setSelectedIndex(0);
                setEditable(true);
                blnHayCam=false;
            }
            catch (Exception e)     {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
  
        @Override
        public boolean eliminar()
        {
            try
            {          
                if (isAnulada())
                    return false;
                        
                if(!eliminarReg())
                    return false;
            
                //Desplazarse al siguiente registro si es posible.
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                         intIndiceCon++;
                         cargarReg();
                    }
                    else
                    {
                        objTooBar.setEstadoRegistro("Eliminado");
                        limpiarFrm();
                    }
                }

                blnHayCam=false;            
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }   
            return true;
        }

        @Override
        public boolean insertar()
        {
            if (!isCamVal())
                return false;
            if (!insertarReg())
                return false;
            blnHayCam=false;
            return true;
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

            //Validar los campos de la cabecera
            if(!isCamVal() || isAnulada())
                return false;
            
            if (!actualizarReg())
                return false;
            blnHayCam=false;
            return true;
        }
         
        @Override
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
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
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
            
            if (isAnulada())
                return false;
            
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        @Override
        public void clickAceptar()
        {
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
            consultarReg();
            blnHayCam=false;
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
        public boolean consultar() {
            if(!consultarReg())
                return false;
            return true;            
        }
                                                
        @Override
        public void clickModificar(){
            setEditable(true);
            noEditable(false);
            strCodPrv="";
            strDesLarPrv="";   
            strCodCom="";
            strDesLarCom="";
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
        public boolean imprimir(){
           if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        @Override
        public void clickImprimir(){
        }
        
        @Override
        public void clickVisPreliminar(){
        }
  
        @Override
        public void clickCancelar(){
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
        
   }   
 
   /**
    * Función que permite realizar las validaciones.
    * <> No se permita hacer cotizaciones/ordenes de compra para items terminal L
    * <> COSENCO se permita hacer la cotizacion de items terminal L
    * @return 
    */
    private boolean isCamVal()
    {
        if(txtCodPrv.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);                  
            mostrarMsgValCam("Cliente");
            txtCodPrv.requestFocus();
            return false;
        }
        
        if(!dtpFecDoc.isFecha()){
            tabCotCom.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de Cotización");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(txtCodCom.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);                  
            mostrarMsgValCam("Vendedor");
            txtCodCom.requestFocus();
            return false;
        }

        /* Validar los datos del Pago. */
        if(cboForPag.getSelectedIndex() ==-1 ){
            tabCotCom.setSelectedIndex(1);
            mostrarMsgValCam("Forma de pago");
            cboForPag.requestFocus();
            return false;
        }

        int intColObligadasPag[] = {2};
        if( !objUti.verifyTbl(tblForPag,intColObligadasPag))
        {
            tabCotCom.setSelectedIndex(1);
            mostrarMsgInf("<HTML>Existe un campo en el Detalle que es obligatorio y no esta ingresado.<BR>Escriba en el campo y vuelva a intentarlo. </HTML>");
            objUti.verifyTbl(tblForPag,intColObligadasPag);
            return false;
        }

        /* Valida que la suma de los montos de pagos, sean igual a total de cotización. */
        BigDecimal bgdTotMon=BigDecimal.ZERO;
        for(int rowIdx = 0; rowIdx<tblForPag.getRowCount() ;rowIdx++){
            BigDecimal bgdSubMon= new BigDecimal (tblForPag.getValueAt(rowIdx, INT_TBL_FOR_PAG_MON_PAG)==null?"0":(tblForPag.getValueAt(rowIdx,INT_TBL_FOR_PAG_MON_PAG).toString().equals("")?"0":tblForPag.getValueAt(rowIdx, INT_TBL_FOR_PAG_MON_PAG).toString())) ;
            bgdTotMon=bgdTotMon.add(bgdSubMon);
        }

        BigDecimal bgdTot=objUti.redondearBigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()), objParSis.getDecimalesMostrar());
        if( objUti.redondearBigDecimal(bgdTotMon,3).compareTo(bgdTot) !=0  ){
            tabCotCom.setSelectedIndex(1);
            mostrarMsgInf("<HTML>La suma de los montos a pagar es mayor al total de la Cotizacion.<BR>Verifique y vuelva a intentarlo. </HTML>");
            return false;
        }

        for (int x=0; x<objTblMod.getRowCountTrue(); x++) {
            if(objTblMod.getValueAt(x, INT_TBL_DAT_COD_ITM)!=null){
                strItm = objTblMod.getValueAt(x,INT_TBL_DAT_ITM_ALT).toString();
                if (strItm.endsWith("L") && !blnIsCosenco){
                    mostrarMsgInf("<HTML>No se puede ingresar una cotizacion de compra de items con terminal L.</HTML>");
                    return false;
                }
            }
        }
      
        return true; 
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
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT CotCab.co_emp,CotCab.co_loc, CotCab.co_cot, cli.co_cli, cli.tx_nom AS tx_nomPrv, cli.tx_dir AS tx_dirPrv";
                strSQL+="      , Usr.co_usr AS co_com, Usr.tx_nom AS tx_nomCom";
                strSQL+="      , CotCab.fe_cot, CotCab.tx_ate, CotCab.co_forPag";
                strSQL+="      , CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, CotCab.st_reg";
                strSQL+=" FROM tbm_cabCotCom AS CotCab ";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS Cli ON (CotCab.co_emp=Cli.co_emp AND CotCab.co_prv=Cli.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS Usr ON (Usr.co_usr=CotCab.co_com) ";
                strSQL+=" WHERE CotCab.st_reg NOT IN('E')";
                if(!blnConVenEme) {
                    strSQL+=" AND CotCab.co_emp = " + objParSis.getCodigoEmpresa();
                    strSQL+=" AND CotCab.co_loc = " + objParSis.getCodigoLocal();
                }
                else{
                    strSQL+=" AND CotCab.co_emp = " + strCodEmp;
                    strSQL+=" AND CotCab.co_loc = " + strCodLoc;     
                    strSQL+=" AND CotCab.co_cot = " + strCodCot; 
                }
                
                //Agregando filtro por Numero de Cotizacion
                if(!txtNumCot.getText().equals(""))
                    strSQL+=" AND CotCab.co_cot =" + txtNumCot.getText()+ "";

                //Agregando filtro por Fecha
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND CotCab.fe_cot='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";

                //Agregando filtro por Codigo de Cliente
                if(!txtCodPrv.getText().equals(""))
                    strSQL+=" AND CotCab.co_prv =" + txtCodPrv.getText() + "";

                //Agregando filtro por Codigo de Vendedor
                if(!txtCodCom.getText().equals(""))
                    strSQL+=" AND CotCab.co_com = " + txtCodCom.getText() + "";

                //Agregando filtro por Codigo de Vendedor
                if(!txtAte.getText().equals(""))
                    strSQL+=" AND CotCab.tx_ate LIKE '" + txtAte.getText() + "'";

                //Agregando filtro por Direccion del cliente
                if(!txtDirPrv.getText().equals(""))
                    strSQL+=" AND Cli.tx_dir LIKE '" + txtDirPrv.getText() + "'";

                //Agregando filtro por Nombre del cliente
                if(!txtNomPrv.getText().equals(""))
                    strSQL+=" AND Cli.tx_nom LIKE '" + txtNomPrv.getText() + "'";

                //Agregando filtro por Nombre del cliente
                if(!txtNomCom.getText().equals(""))
                    strSQL+=" AND Usr.tx_nom LIKE '" + txtNomCom.getText() + "'";

                strSQL+=" ORDER BY CotCab.co_cot";
                //System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,    rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,    rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_COT,    rst.getInt("co_cot"));
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
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){ 
                    if (cargarTabForPag()){ 
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
    
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT cli.co_cli AS co_prv, cli.tx_nom AS tx_nomPrv, cli.tx_dir AS tx_dirPrv, Usr.co_usr AS co_com, Usr.tx_nom AS tx_nomCom ";
                strSQL+="      , CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate, CotCab.co_forPag, CotCab.tx_obs1, CotCab.tx_obs2";    
                strSQL+="      , CotCab.nd_sub, CotCab.nd_valIva, CotCab.nd_tot, CotCab.nd_porIva, CotCab.nd_valDes, CotCab.st_reg";
                strSQL+=" FROM tbm_cabCotCom AS CotCab ";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS Cli ON (CotCab.co_emp=Cli.co_emp AND CotCab.co_prv=Cli.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS Usr ON (Usr.co_usr=CotCab.co_com) ";
                strSQL+=" WHERE CotCab.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND CotCab.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND CotCab.co_cot=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_COT);  
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {    
                    //Cabecera
                    strAux=rst.getString("co_cot");
                    txtNumCot.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_prv");
                    txtCodPrv.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_nomPrv");
                    txtNomPrv.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_dirPrv");
                    txtDirPrv.setText((strAux==null)?"":strAux);    
                    
                    dtpFecDoc.setText("");
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_cot"),"dd/MM/yyyy"));
                    
                    strAux=rst.getString("co_com");
                    txtCodCom.setText((strAux==null)?"":strAux);    
                    
                    strAux=rst.getString("tx_nomCom");
                    txtNomCom.setText((strAux==null)?"":strAux);    

                    strAux=rst.getString("tx_ate");
                    txtAte.setText((strAux==null)?"":strAux);        
                    
                    lblTit.setText("Cotización No. " + txtNumCot.getText() +  " (" + txtNomPrv.getText() + ") ");
                   
                    //Pie de pagina
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);    
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);    
                    
                    strAux=rst.getObject("nd_sub")==null?"0":(rst.getString("nd_sub").equals("")?"0":rst.getString("nd_sub"));
                    txtSub.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    
                    strAux=rst.getObject("nd_valIva")==null?"0":(rst.getString("nd_valIva").equals("")?"0":rst.getString("nd_valIva"));
                    txtIva.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtTot.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));                    

                    strAux=rst.getObject("nd_porIva")==null?"0":(rst.getString("nd_porIva").equals("")?"0":rst.getString("nd_porIva"));
                    lblIva.setText("IVA " + strAux + "%");    
                     
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
                else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        BigDecimal bgdAux=BigDecimal.ZERO;
        try { 
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT detCot.co_itm, inv.tx_codalt, detCot.tx_nomItm, var.tx_descor AS tx_uniMed, detCot.st_ivaCom";
                strSQL+="      , detCot.co_bod, detCot.nd_can, detCot.nd_cosUni, detCot.nd_porDes, a5.co_mot, a5.tx_tipMot";
                strSQL+="      ,  CAST( round ( round( (detCot.nd_can * detCot.nd_cosUni) - ( (detCot.nd_can * detCot.nd_cosUni) * (detCot.nd_porDes/100) ) , 3) ";
                strSQL+="          , "+objParSis.getDecimalesMostrar()+" )  AS NUMERIC) AS nd_tot ";
                strSQL+=" FROM tbm_detCotCom AS detCot";
                strSQL+=" LEFT OUTER JOIN( tbm_inv AS inv";
                strSQL+="   INNER JOIN tbm_motDoc AS a5 ON inv.co_emp=a5.co_emp AND (CASE WHEN inv.st_ser ='N' THEN 'B' ELSE inv.st_ser END) = a5.tx_tipMot AND a5.st_reg = 'A'";
                strSQL+=" ) ON (detcot.co_emp=inv.co_emp AND detcot.co_itm=inv.co_itm) ";
                strSQL+=" LEFT OUTER JOIN tbm_var AS var ON (inv.co_uni=var.co_reg)" ;
                strSQL+=" WHERE detCot.co_emp ="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND detCot.co_loc ="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND detCot.co_cot ="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_COT);
                strSQL+=" ORDER BY detCot.co_reg";   
                //System.out.println("cargarDetReg: " +strSQL);
                rst=stm.executeQuery(strSQL);  
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) { 
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LINEA, "");
                    vecReg.add(INT_TBL_DAT_COD_ITM,     rst.getString("co_itm"));   
                    vecReg.add(INT_TBL_DAT_ITM_ALT,     rst.getString("tx_codalt"));       
                    vecReg.add(INT_TBL_DAT_BUT_ITM,     "");      
                    vecReg.add(INT_TBL_DAT_DES_ITM,     rst.getString("tx_nomItm"));       
                    vecReg.add(INT_TBL_DAT_UNI_MED,     rst.getString("tx_uniMed"));  
                    vecReg.add(INT_TBL_DAT_COD_MOT,     rst.getString("co_mot"));  
                    vecReg.add(INT_TBL_DAT_TXT_MOT,     rst.getString("tx_tipMot"));  
                    vecReg.add(INT_TBL_DAT_COD_BOD,     rst.getInt("co_bod"));       
                    vecReg.add(INT_TBL_DAT_BUT_BOD,     "");   
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_can")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_can"))  , objParSis.getDecimalesMostrar());  
                    vecReg.add(INT_TBL_DAT_CAN_MOV,     ""+bgdAux); 
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_cosUni")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_cosUni"))  , objParSis.getDecimalesMostrar());                     
                    vecReg.add(INT_TBL_DAT_COS_UNI,      ""+bgdAux);     
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_porDes")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_porDes"))  , objParSis.getDecimalesMostrar());                                         
                    vecReg.add(INT_TBL_DAT_POR_DES,     ""+bgdAux);
                    vecReg.add(INT_TBL_DAT_CHK_IVA,     null);   
                    vecReg.add(INT_TBL_DAT_TXT_IVA,     "");   
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_tot")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_tot"))  , objParSis.getDecimalesMostrar());                                         
                    vecReg.add(INT_TBL_DAT_TOT_DET,      ""+bgdAux);   
                    vecReg.add(INT_TBL_DAT_EST_REG,     "E");   
                    
                    strAux=rst.getObject("st_ivaCom")==null?"N":(rst.getString("st_ivaCom").equals("")?"N":rst.getString("st_ivaCom"));
                    if(strAux.equals("S")){
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_IVA);
                    }         
                    vecDat.add(vecReg);      
                }
                rst.close(); 
                rst=null;
                stm.close(); 
                stm=null;
                con.close(); 
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabForPag(){
        boolean blnRes=true;
        int intFil=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (con!=null){
                stm=con.createStatement(); 
                    
                //Armar sentencia SQL
                strSQL  =" ";
                strSQL+=" SELECT  a.*, a1.co_forPag ";
                strSQL+=" FROM tbm_pagCotCom AS a";
                strSQL+=" INNER JOIN tbm_cabCotCom AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_cot=a1.co_cot";
                strSQL+=" WHERE a.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_cot="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_COT);
                //System.out.println("cargarTabForPag: " +strSQL);
                rst=stm.executeQuery(strSQL);    
                //Obtener los registros.
                while (rst.next()) { 
                    /* Llenando el combo de descripciones de pagos.*/
                    String strCodForPag = (rst.getString("co_forPag")==null)?"":rst.getString("co_forPag");  
                    objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCodForPag);                          
                    
                    /* Llenando la tabla de Pagos. */
                    ((DefaultTableModel)tblForPag.getModel()).addRow(new java.util.Vector());  
                        tblForPag.setValueAt(rst.getInt("ne_diaCre"), intFil,INT_TBL_FOR_PAG_DIA_CRE);
                        tblForPag.setValueAt(objUti.formatearFecha(rst.getString("fe_ven"), "yyyy-MM-dd", "dd/MM/yyyy"), intFil, INT_TBL_FOR_PAG_FEC_VEN);
                        tblForPag.setValueAt(rst.getBigDecimal("mo_pag"), intFil, INT_TBL_FOR_PAG_MON_PAG);
                        tblForPag.setValueAt(new Integer(0),  intFil, INT_TBL_FOR_PAG_DIA_GRA);
                    intFil++;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    private boolean insertarReg()
    {
        Connection conCot=null;
        PreparedStatement pstReg=null;
        ResultSet rstNum=null;
        Statement stmCot=null;
        BigDecimal bgdAux=BigDecimal.ZERO;
        try{
            conCot=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try{
                if (conCot!=null){
                    conCot.setAutoCommit(false);
                    stmCot=conCot.createStatement();
                    getIva();

                    int intNumCot = 0;
                   
                    //Verificando que si es ke la tabla esta vacia 
                    strSQL=" SELECT Max(co_cot) as co_cot FROM tbm_cabCotCom  " +
                           " WHERE co_emp = " + objParSis.getCodigoEmpresa() + 
                           " AND co_loc = " + objParSis.getCodigoLocal() ;
                    rstNum = stmCot.executeQuery(strSQL);
                    if(rstNum.next())
                        intNumCot = rstNum.getInt("co_cot");
                    intNumCot++;

                    /*********************************************\    
                     * Armando el Insert para los datos          *   
                     * de la cabecera de cotizacion              *
                    \*********************************************/
                    String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

                    strSQL =" ";
                    strSQL+=" INSERT INTO  tbm_cabCotCom";
                    strSQL+=" (co_emp, co_loc, co_cot, fe_cot, co_prv, co_com, tx_ate";
                    strSQL+=" , tx_obs1, tx_obs2, nd_sub, nd_valiva, nd_tot, nd_porIva";
                    strSQL+=" , co_forPag, fe_ing, fe_ultmod, co_usring, co_usrmod )"; 
                    strSQL+=" VALUES (";
                    strSQL+="  "+objParSis.getCodigoEmpresa()+""; //co_emp
                    strSQL+=", "+objParSis.getCodigoLocal() +"";  //co_loc
                    strSQL+=", "+intNumCot + "";                  //co_cot
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_cot
                    strSQL+=", "+txtCodPrv.getText()+"";
                    strSQL+=", "+txtCodCom.getText()+"";
                    strSQL+=", " + objUti.codificar(txtAte.getText()) + "";  //tx_ate
                    strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()) + ""; //tx_obs2
                    bgdAux= objUti.redondearBigDecimal(txtSub.getText()==null?"0":(txtSub.getText().equals("")?"0":txtSub.getText()), objParSis.getDecimalesBaseDatos());                    
                    strSQL+=", " + bgdAux+ ""; //nd_sub
                    bgdAux= objUti.redondearBigDecimal(txtIva.getText()==null?"0":(txtIva.getText().equals("")?"0":txtIva.getText()), objParSis.getDecimalesBaseDatos());                    
                    strSQL+=", " + bgdAux+ ""; //nd_valIva
                    bgdAux= objUti.redondearBigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()), objParSis.getDecimalesBaseDatos());                    
                    strSQL+=", " + bgdAux+ ""; //nd_tot   
                    strSQL+=", " + bgdPorIva+ ""; //nd_porIva
                    strSQL+=", " + vecForPag.get(cboForPag.getSelectedIndex())+ ""; //co_forPag
                    strSQL+=", " + objUti.codificar(strFecSis)  +"";  //fe_ing
                    strSQL+=", " + objUti.codificar(strFecSis)  +"";  //fe_ultmod
                    strSQL+=", " + objParSis.getCodigoUsuario() +"";  //co_usrIng
                    strSQL+=", " + objParSis.getCodigoUsuario() +"";  //co_usrMod
                    strSQL+=");";  

                    /* Ejecutando el insert */
                    //System.out.println("Insertando cabecera de compra: \n "+strSQL);
                    pstReg = conCot.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    /*********************************************\    
                     * Armando el Insert para los datos          *   
                     * del detalle de cotizacion                 *
                    \*********************************************/
                    objTblMod.removeEmptyRows();
                    for (int i=0;i<objTblMod.getRowCountTrue();i++) {              /* 25/Sep/2015 JoséMario  */         
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_detCotCom";
                        strSQL+=" (co_emp, co_loc, co_cot, co_reg";
                        strSQL+=" , co_itm, tx_nomItm, co_bod ";
                        strSQL+=" , nd_can, nd_cosuni, nd_porDes, st_ivaCom ";
                        strSQL+=" )";
                        strSQL+=" VALUES (";                        
                        strSQL+="  " + objParSis.getCodigoEmpresa() + ""; //co_emp
                        strSQL+=", " + objParSis.getCodigoLocal()   + ""; //co_loc
                        strSQL+=", " + intNumCot   + "";  //co_cot
                        strSQL+=", " + (i+1)    + "";     //co_reg
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM); //co_itm
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_ITM)) + "";//tx_nomItm
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD); //co_bod
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MOV)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_MOV).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MOV).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_Can 
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_cosUni
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_POR_DES)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_POR_DES).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_POR_DES).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_porDes 
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA))
                            strSQL+=", 'S'";//st_ivaCom
                        else
                            strSQL+=", 'N'";//st_ivaCom
                        strSQL+=" ); "; 
                        
                        /* Ejecutando el insert */
                        //System.out.println("Insertando detalle de compra: \n"+strSQL);
                        pstReg = conCot.prepareStatement(strSQL);
                        pstReg.executeUpdate();
                    }

                    /*********************************************\    
                     * Armando el Insert para los datos del detalle de Pago  *   
                    \*********************************************/
                    for(int i=0; i<tblForPag.getRowCount();i++) {
                        strSQL ="";
                        strSQL+=" INSERT INTO  tbm_pagCotCom";
                        strSQL+=" (co_emp, co_loc, co_cot, co_reg, ne_diaCre, fe_ven, mo_pag, ne_diaGra) ";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa() + ""; //co_emp
                        strSQL+=", " + objParSis.getCodigoLocal()   + ""; //co_loc
                        strSQL+=", " + intNumCot   + "";  //co_cot
                        strSQL+=", " + (i+1)    + "";     //co_reg
                        strSQL+=", " + tblForPag.getValueAt(i, INT_TBL_FOR_PAG_DIA_CRE); //ne_diaCre
                        strSQL+=", " + objUti.codificar(objUti.formatearFecha(tblForPag.getValueAt(i,INT_TBL_FOR_PAG_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ ""; //fe_ven
                        strSQL+=", " + objUti.redondearBigDecimal(tblForPag.getValueAt(i, INT_TBL_FOR_PAG_MON_PAG)==null?"0":(tblForPag.getValueAt(i,INT_TBL_FOR_PAG_MON_PAG).toString().equals("")?"0":tblForPag.getValueAt(i, INT_TBL_FOR_PAG_MON_PAG).toString()), objParSis.getDecimalesBaseDatos()) + "";//mo_pag 
                        strSQL+=", " + tblForPag.getValueAt(i, INT_TBL_FOR_PAG_DIA_GRA); //nd_diaGra
                        strSQL+=" );";
                        //System.out.println("insertando pagos: \n"+strSQL);
                        /* Ejecutando el insert */
                        pstReg = conCot.prepareStatement(strSQL);
                        pstReg.executeUpdate();
                    }
                    txtNumCot.setText(""+intNumCot);
                    conCot.commit();
                }
            }
            catch(SQLException Evt){
                conCot.rollback();
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
            catch(Exception Evt){
                conCot.rollback();
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
            finally{
                try{
                    if (rstNum != null)
                        rstNum.close();
                    rstNum=null;

                    if (stmCot != null)
                        stmCot.close();
                    stmCot=null;

                    if (pstReg != null) 
                        pstReg.close();
                    pstReg=null;

                    if (conCot != null)
                        conCot.close();
                    conCot=null;
                }
                catch(Throwable e){
                    ;
                }
            } 
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                                                 
        return true;                                     
    }       
    
    private boolean actualizarReg()
    {
        Connection conActCot=null;
        PreparedStatement pstReg=null;
        Statement stmCot=null;
        try{
            conActCot=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            conActCot.setAutoCommit(false);
            try{
                if (conActCot!=null){
                    stmCot=conActCot.createStatement();
                    
                    /*********************************************\    
                     * Armando el Update para los datos          *   
                     * de la cabecera de cotizacion              *
                    \*********************************************/
                    String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL ="";
                    strSQL+=" UPDATE tbm_cabCotCom  ";
                    strSQL+=" SET fe_cot  = '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=" , co_prv  = "  + txtCodPrv.getText() ;
                    strSQL+=" , co_com  = "  + txtCodCom.getText() ;
                    strSQL+=" , tx_ate  = '" + txtAte.getText()    + "'" ;
                    strSQL+=" , tx_obs1 = '" + txaObs1.getText()   + "'" ;
                    strSQL+=" , tx_obs2 = '" + txaObs2.getText()   + "'" ;
                    strSQL+=" , nd_sub  = "  + objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar()) ;
                    strSQL+=" , nd_valiva = "  + objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar());
                    strSQL+=" , nd_tot    = "  + objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar());
                    strSQL+=" , nd_porIva = "  + bgdPorIva ;
                    strSQL+=" , co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) ; 
                    strSQL+=" , fe_ultMod ='"+strFecSis+"' ";
                    strSQL+=" , co_usrMod = "  +objParSis.getCodigoUsuario();
                    strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa(); 
                    strSQL+=" AND co_loc = "   + objParSis.getCodigoLocal();  
                    strSQL+=" AND co_cot = "   + txtNumCot.getText();      
                    strSQL+=" ; ";
                    /* Ejecutando el Update */
                    pstReg = conActCot.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    /*********************************************\    
                     * Armando el Delete para quitar los         *   
                     * registro de detalle de cotizacion         *
                     * actuales.                                 *
                    \*********************************************/
                    strSQL= " DELETE FROM tbm_detCotCom " +
                          " WHERE co_emp = " + objParSis.getCodigoEmpresa()+
                          " AND co_loc = " + objParSis.getCodigoLocal()+
                          " AND co_cot = " +  txtNumCot.getText();      
                    /* Ejecutando el Delete */
                    pstReg = conActCot.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    /*********************************************\    
                     * Armando el Insert para los datos          *   
                     * del detalle de cotizacion                 *
                    \*********************************************/
                    objTblMod.removeEmptyRows();
                    for (int i=0;i<objTblMod.getRowCountTrue();i++)
                    {                                            
                        strSQL ="";
                        strSQL+=" INSERT INTO  tbm_detCotCom";
                        strSQL+=" (co_emp, co_loc, co_cot, co_reg, co_itm, tx_nomItm, co_bod , nd_can, nd_cosUni, nd_porDes, st_ivaCom )";
                        strSQL+=" VALUES (";
                        strSQL+="   "+objParSis.getCodigoEmpresa();
                        strSQL+=", "+objParSis.getCodigoLocal();
                        strSQL+=", "+txtNumCot.getText();
                        strSQL+=", "+(i+1) ;
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM); //co_itm
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_ITM)) + "";//tx_nomItm
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD); //co_bod
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MOV)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_MOV).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MOV).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_Can 
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesBaseDatos()) + "";   //nd_cosUni
                        strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_POR_DES)==null?"0":(objTblMod.getValueAt(i,INT_TBL_DAT_POR_DES).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_POR_DES).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_porDes 
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IVA))
                            strSQL+=", 'S'";//st_ivaCom
                        else
                            strSQL+=", 'N'";//st_ivaCom
                                                
                        strSQL+=" "+");"; 
                        /* Ejecutando el insert */
                        pstReg = conActCot.prepareStatement(strSQL);
                        pstReg.executeUpdate();
                    }

                    /*********************************************\    
                     * Armando el Delete para quitar los         *   
                     * registro de detalle de Pago               *
                     * actuales.                                 *
                    \*********************************************/
                    strSQL ="";
                    strSQL+=" DELETE FROM tbm_pagCotCom ";
                    strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa() ;
                    strSQL+=" AND co_loc = " + objParSis.getCodigoLocal() ;
                    strSQL+=" AND co_cot = " +  txtNumCot.getText();      

                    /* Ejecutando el Delete */
                    pstReg = conActCot.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    /*********************************************\    
                     * Armando el Insert para los datos del detalle de Pago  *   
                    \*********************************************/
                    for(int i=0; i<tblForPag.getRowCount();i++) {
                        strSQL ="";
                        strSQL+=" INSERT INTO  tbm_pagCotCom";
                        strSQL+=" (co_emp, co_loc, co_cot, co_reg, ne_diaCre, fe_ven, mo_pag, ne_diaGra) ";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa() + ""; //co_emp
                        strSQL+=", " + objParSis.getCodigoLocal()   + ""; //co_loc
                        strSQL+=", " + txtNumCot.getText()   + "";        //co_cot
                        strSQL+=", " + (i+1)    + "";     //co_reg
                        strSQL+=", " + tblForPag.getValueAt(i, INT_TBL_FOR_PAG_DIA_CRE); //ne_diaCre
                        strSQL+=", " + objUti.codificar(objUti.formatearFecha(tblForPag.getValueAt(i,INT_TBL_FOR_PAG_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ ""; //fe_ven
                        strSQL+=", " + objUti.redondearBigDecimal(tblForPag.getValueAt(i, INT_TBL_FOR_PAG_MON_PAG)==null?"0":(tblForPag.getValueAt(i,INT_TBL_FOR_PAG_MON_PAG).toString().equals("")?"0":tblForPag.getValueAt(i, INT_TBL_FOR_PAG_MON_PAG).toString()), objParSis.getDecimalesBaseDatos()) + "";//mo_pag 
                        strSQL+=", " + tblForPag.getValueAt(i, INT_TBL_FOR_PAG_DIA_GRA); //ne_diaCre
                        strSQL+=" );";
                        /* Ejecutando el insert */
                        pstReg = conActCot.prepareStatement(strSQL);
                        pstReg.executeUpdate();
                    }
                    conActCot.commit();
                }
            }
            catch(SQLException Evt){ 
                conActCot.rollback();
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
            catch(Exception Evt){ 
                conActCot.rollback();
                objUti.mostrarMsgErr_F1(jfrThis, Evt); 
                return false;
            }
            finally{
                try{ 
                    if (stmCot != null)
                        stmCot.close();
                    stmCot=null;

                    if (pstReg != null)
                        pstReg.close();
                    pstReg=null;

                    if (conActCot != null)
                        conActCot.close();
                    conActCot=null;                                            
                }
                catch(Throwable e){
                    ;
                }
            }
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                       
        return true;
    }

    private boolean eliminarReg()
    {
        PreparedStatement pstElmReg;
        try
        {
            Connection conElmReg=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try{
                if (conElmReg!=null){
                    conElmReg.setAutoCommit(false);

                    /*********************************************\    
                     * Armando el Update para ANULAR             *   
                     * la cotizacion                             *
                    \*********************************************/
                    String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL =" ";
                    strSQL+=" UPDATE tbm_cabCotCom ";
                    strSQL+=" SET st_reg  = 'E' "; 
                    strSQL+="  , fe_ultMod ='"+strFecSis+"' ";
                    strSQL+="  , co_usrMod = "+objParSis.getCodigoUsuario();
                    strSQL+="  WHERE co_emp = " + objParSis.getCodigoEmpresa();
                    strSQL+="  AND co_loc = " + objParSis.getCodigoLocal();
                    strSQL+="  AND co_cot = " +  txtNumCot.getText();      
                    /* Ejecutando el Update */
                    pstElmReg = conElmReg.prepareStatement(strSQL);
                    pstElmReg.executeUpdate();
                    conElmReg.commit();
                    conElmReg.close();
                }
            }
            catch(SQLException Evt){
                conElmReg.rollback();
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                       
        return true;
    }    

    private boolean anularReg()
    {
        try{
            Connection conAnuReg=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try{
                if (conAnuReg!=null){
                    conAnuReg.setAutoCommit(false);
                    PreparedStatement pstAnuReg;

                    /*********************************************\    
                     * Armando el Update para ANULAR             *   
                     * la cotizacion                             *
                    \*********************************************/
                    String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

                    strSQL ="";
                    strSQL+=" UPDATE tbm_cabCotCom  ";
                    strSQL+=" SET st_reg  = 'I' ";
                    strSQL+="  , fe_ultMod ='"+strFecSis+"' ";
                    strSQL+="  , co_usrMod = "+objParSis.getCodigoUsuario();
                    strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc = " + objParSis.getCodigoLocal();
                    strSQL+=" AND co_cot = " +  txtNumCot.getText();      
                    /* Ejecutando el Update */
                    pstAnuReg = conAnuReg.prepareStatement(strSQL);
                    pstAnuReg.executeUpdate();
                    conAnuReg.commit();
                    conAnuReg.close();
                }
            }
            catch(SQLException Evt){
               conAnuReg.rollback();
               objUti.mostrarMsgErr_F1(jfrThis, Evt);
               return false;
            }
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                       
        return true;
    }       
    /***
     * Función que valida si la cotización ha sido anulada.
     * @return 
     */
    private boolean isAnulada()
    {
        boolean blnRes = false;
        Connection conTmp ;
        Statement stmTmp;
        try
        {
            conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
                stmTmp = conTmp.createStatement();
                strSQL ="";
                strSQL+=" SELECT COUNT(*) FROM tbm_cabcotcom ";
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+"";
                strSQL+=" AND co_cot="+txtNumCot.getText() + " AND st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
                    mostrarMsgInf("Documento anulado no se puede modificar");
                }else
                    blnRes = false;
                stmTmp.close();
                conTmp.close();
            }
        }
        catch(SQLException Evt){    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
        }
        catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
        }
        return blnRes;
    }           
    
    public class ActLisForPag implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            try{
                Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conPag!=null){     
                    if(cboForPag.getSelectedIndex()!=-1)
                    {
                        String strSQL2=" SELECT a1.ne_numPag, a2.ne_diaCre "+
                                       " FROM tbm_cabForPag AS a1 LEFT OUTER JOIN tbm_detForPag AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_forPag=a1.co_forPag ) "+
                                       " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa()+
                                       " AND a1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) ;

                        String strSQL3="SELECT count(a2.ne_diaCre) AS c "+
                                       " FROM tbm_cabForPag AS a1 LEFT OUTER JOIN tbm_detForPag AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_forPag=a1.co_forPag ) "+
                                       " WHERE a1.co_emp = " + objParSis.getCodigoEmpresa()+
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
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
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
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                Map mapPar=new HashMap();
                                mapPar.put("codEmp", new Integer(objParSis.getCodigoEmpresa()) );
                                mapPar.put("codLoc", new Integer(objParSis.getCodigoLocal()) );
                                mapPar.put("codCot", new Integer( Integer.parseInt(txtNumCot.getText())) );
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
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



    
    
    
}

