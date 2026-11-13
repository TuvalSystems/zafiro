            
package Compras.ZafCom04;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafUtil.ZafCliente_dat;  
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
/*
 * @author  Jayapata
 * 
 */
public class ZafCom04 extends javax.swing.JInternalFrame
{
    //Constantes: JTable TblDat  
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;    
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_CANCOM   = 7;
    final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PREVEN   = 9 ;            //Precio de Venta
    final int INT_TBL_PORDES   = 10 ;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 11;            //Boolean Iva
    final int INT_TBL_TOTAL    = 12;            //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 13;
    final int INT_TBL_ITMORI   = 14;            //Codigo del item de una o/c cargada
    final int INT_TBL_BODORI   = 15;            //Codigo del bodega de una o/c cargada
    final int INT_TBL_CANORI   = 16;            //Codigo del bodega de una o/c cargada
    final int INT_TBL_CANDEV   = 17;            //Codigo del bodega de una o/c cargada
    final int INT_TBL_ESTADO   = 18;
    final int INT_TBL_IVATXT   = 19;
    final int INT_TBL_ITMALT2  = 20 ;
    final int INT_TBL_ITMSER   = 21;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_CODITMACT= 22;  
    final int INT_TBL_IEBODFIS = 23;            //Estado que dice si ingresa/egresa fisicamente en bodega
   
    //Constantes del ArrayList Elementos Eliminados  
    final ArrayList arreBod=new ArrayList();
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    final int INT_ARR_IEBODFIS = 6;

    //Variables
    private Connection conCab;  //Coneccion a la base donde se encuentra la cotizacion
    private Statement stmCab;   //Statement para la cotizacion 
    private ResultSet rstCab;   //Resultset que tendra los datos de la cabecera
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    private MiToolBar objTooBar;
    private ZafVenCon vcoTipDoc; 
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                    //Reportes del Sistema.
    private ZafDocLis objDocLis;                    //Instancia de clase que detecta cambios
    private Librerias.ZafUtil.ZafCliente_dat objCliente_dat;  // Para Obtener la informacion del cliente
    private Librerias.ZafUtil.ZafCiudad_dat  objCiudad_dat;   // Para Obtener la informacion de una ciudad
    private Librerias.ZafUtil.UltDocPrint    objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
    private Librerias.ZafUtil.ZafCtaCtb      objCtaCtb;       // Para obtener  los codigos y nombres de ctas ctbles
    private Librerias.ZafUtil.ZafTipDoc      objTipDoc;
    private Librerias.ZafInventario.ZafInvItm objInvItm;
    javax.swing.JInternalFrame jfrThis;   //Hace referencia a this
    private ZafDatePicker dtpFecDoc;
    
    private Vector vecTipDoc,vecDetDia; //Vector que contiene el codigo de tipos de documentos    
    private Vector vecLoc;              //Vector que contiene los locales de la empresa
    
    boolean blnEstCarOC=false;
    boolean blnChangeData=false;
    boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento     
    
    private double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    
    int intSecuencialFac=0;
    int intNumDocOrdCom=0;
    int intNumDec;              //Numero de decimales a presentar
    int intEstCruAut=0;
    double dblValCruAut=0;    
   
    private String strCodEmpOC="", strCodLocOC="", strCodTipOC="", strCodDocOC="";

    private String strBeforeValue,strAfterValue;
    private String sSQL, strFiltro,strAux; //EL filtro de la Consulta actual
    private String strFecSis;
    private String STR_ESTREG="";
    private String stIvaCom="S";     
    private String strMerIngEgr="", strTipIngEgr="";
    private String strVer = " v3.8";

    /** Creates new form ZafCom04 */
    public ZafCom04(ZafParSis obj) 
    {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis)obj.clone();
            jfrThis = this;
            initComponents();
            objUti = new ZafUtil();
            this.setTitle(objParSis.getNombreMenu()+strVer);        
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);

            //***********************************************************************
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
             
            //***********************************************************************

            cargarTipDocPre();
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(540, 10, 110, 20);
            intNumDec = objParSis.getDecimalesMostrar();
            txtCodTipDoc.setVisible(false);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.addTab("Asiento Diario", objAsiDia);    

            objTooBar = new MiToolBar(this);        
            this.getContentPane().add(objTooBar,"South");
            pack();

            vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sqlPre =" Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
                           " where docprg.co_mnu = 45 "+
                           " and docprg.co_emp = " + objParSis.getCodigoEmpresa() +
                           " and docprg.co_loc = " + objParSis.getCodigoLocal() ;       
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos()
                                    , objParSis.getClaveBaseDatos(), sqlPre, cboTipDoc, vecTipDoc);      
            cboTipDoc.setSelectedIndex(0);

            vecLoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal();
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                    objParSis.getClaveBaseDatos(), sql, local, vecLoc);      
            local.setSelectedIndex(0);

            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            cargarBodegas();

            objUti.desactivarCom(this);

            this.setBounds(10,10, 700,450);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafCom04(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc ) {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis)obj.clone();
            jfrThis = this;
            initComponents();
            objUti = new ZafUtil();

            strCodEmpOC= String.valueOf( intCodEmp.intValue() );
            strCodLocOC= String.valueOf( intCodLoc.intValue() );;
            strCodTipOC= String.valueOf( intCodTipDoc.intValue() );;
            strCodDocOC= String.valueOf( intCodDoc.intValue() );;
            blnEstCarOC=true;

            this.setTitle(objParSis.getNombreMenu()+strVer);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);

            //***********************************************************************
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
              public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                     objAsiDia.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
               }
            });
            //***********************************************************************

            //cargarTipDocPre();
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(540, 10, 110, 20);
            intNumDec = objParSis.getDecimalesMostrar();
            txtCodTipDoc.setVisible(false);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.addTab("Asiento Diario",objAsiDia);

            objTooBar = new MiToolBar(this);
            //getIva();
           // this.getContentPane().add(objTooBar,"South");
            pack();

            vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sqlPrede = "Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
                    " where   docprg.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                            " docprg.co_loc = " + objParSis.getCodigoLocal()   + " and  docprg.co_mnu = 45";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
                                    objParSis.getClaveBaseDatos(), sqlPrede, cboTipDoc, vecTipDoc);
            cboTipDoc.setSelectedIndex(0);

            vecLoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal();
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
                                    objParSis.getClaveBaseDatos(), sql, local, vecLoc);
            local.setSelectedIndex(0);


            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            cargarBodegas();

            objUti.desactivarCom(this);

            this.setBounds(10,10, 700,450);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void cargarBodegas(){
        Connection conTmp ;
        Statement stmTmp;
        ResultSet rstTmp;
        try
        {
            conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT co_bod FROM tbr_bodLoc WHERE co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal();
                rstTmp=stmTmp.executeQuery(sql);
                while(rstTmp.next()){
                arreBod.add(""+rstTmp.getInt(1));    
                }
                rstTmp.close();
                stmTmp.close();
                conTmp.close();
                rstTmp=null;
                stmTmp=null;
                conTmp=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    }
 
    private void configurarVentanConsulta()
    {
        configurarVenConTipDoc();
        configurarTblDat();
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
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");
              
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            if(objParSis.getCodigoUsuario()==1){
                Str_Sql=" SELECT distinct a.co_tipdoc,a.tx_descor,a.tx_deslar"+
                        " FROM tbr_tipdocprg AS b " +
                        " LEFT OUTER JOIN tbm_cabtipdoc AS a ON (b.co_emp = a.co_emp AND b.co_loc = a.co_loc AND b.co_tipdoc = a.co_tipdoc)" +
                        " WHERE b.co_emp = " + objParSis.getCodigoEmpresa() +  
                        " AND b.co_loc = " + objParSis.getCodigoLocal() +  
                        " AND b.co_mnu = " + objParSis.getCodigoMenu();
            }
            else {     
                Str_Sql=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                        " FROM tbr_tipDocUsr AS a1 INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                        " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa()+
                        " AND a1.co_loc=" + objParSis.getCodigoLocal()+
                        " AND a1.co_mnu=" + objParSis.getCodigoMenu()+
                        " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblDat() {
        boolean blnRes=false;
        try{
           
            //Configurar JTable: Establecer el modelo.
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");            
            vecCab.add(INT_TBL_ITMALT,"Cod. Item");
            vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_DESITM,"Descripcion");            
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");            
            vecCab.add(INT_TBL_BUTBOD,"");
            vecCab.add(INT_TBL_CANCOM,"Cantidad");
            vecCab.add(INT_TBL_CANMOV,"Devolucion");
            vecCab.add(INT_TBL_PREVEN,"Costo");            
            vecCab.add(INT_TBL_PORDES,"Desc.");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ITMORI,"");
            vecCab.add(INT_TBL_BODORI,"");
            vecCab.add(INT_TBL_CANORI,"");
            vecCab.add(INT_TBL_CANDEV,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_ITMALT2,"Cod.Item2");
            vecCab.add(INT_TBL_ITMSER,"Ser.");
            vecCab.add(INT_TBL_CODITMACT, "CodItmAct"); 
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
             
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setRowSelectionAllowed(false);
//            tblDat.setCellSelectionEnabled(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            
            //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.            
//            objTblMod.setColumnDataType(INT_TBL_CANDEV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCOM, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREVEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL , objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODITM);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            arlAux.add("" + INT_TBL_ITMORI);
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            arlAux.add("" + INT_TBL_IEBODFIS);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
                  

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_PREVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(50);         
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(80);         
            tcmAux.getColumn(INT_TBL_CODITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CANORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CANDEV).setWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(0);
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            /**
             * Estado : 
                V:vacio.- si al momento de carga no tenia codigo asociado
                N:Nuevo.- Si fue modificado despues de la carga y estaba vacio
                E:Existe.- Si al momento de carga tenia codigo asociado
                M:Modificado.- Si fue modificado despues de la carga y estaba existe
             */
            tcmAux.getColumn(INT_TBL_ESTADO).setWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setResizable(false);                         
                                
            
               tcmAux.getColumn(INT_TBL_BUTBOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setResizable(false); 
            
            
               tcmAux.getColumn(INT_TBL_BUTITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setResizable(false); 
            
            
            tcmAux.getColumn(INT_TBL_ITMALT2).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setResizable(false);                         
                                
          
            tcmAux.getColumn(INT_TBL_CODITMACT).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setResizable(false);                         
          
            
            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CANMOV);

            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

                    blnChangeData = false;        
                    strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA).toString();
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    System.out.println("afterEdit:" +blnChangeData );
                    strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA).toString();
                        if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                                tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                                tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
        	            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                                tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
        	            tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }
                                
//                        System.out.println("Fila:"+tblDat.getSelectedRow()+" Estado: "+tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString());
                        if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                            calculaSubtotal();
                            blnChangeData = true;
                        }                        
                }
            });

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
   
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL="";            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    System.out.println("beforeEdit:"+blnChangeData);
                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "0";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="0";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }
                           
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
                    if (!(strBeforeValue.equals(strAfterValue))){
                         if (Double.parseDouble(strAfterValue) - Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANORI).toString()) > Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANDEV).toString())){                                                      
                             String strMsg = "<html>La  <FONT COLOR=\"blue\">cantidad </font>a devolver es mayor a la orden de compra." +
                                             "\n Verifique si tiene otras devoluciones </html> " ;                            
                            mostrarMsgInf(strMsg);
                            tblDat.setValueAt(strBeforeValue,tblDat.getSelectedRow(),INT_TBL_CANMOV);
                            blnChangeData = true; 
                        }
                        if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !blnChangeData){
                            blnChangeData = true; 
                            calculaSubtotal();
                        }
                    }
                    
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
             new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            //Configurar JTable: Centrar columnas.
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
           // System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this,e);
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
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Codigo Item";
                    break;
                case INT_TBL_DESITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_CODBOD:
                    strMsg="Codigo Bodega";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_PREVEN:
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
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTotal();
                    generaAsiento();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }    
    
    private void setEditable(boolean editable)
    {
         if (editable==true){
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

         }else{
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
        strTipIngEgr=objTipDoc.gettx_natdoc();
        
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), "DevCom");            
        
         
        //getIva();
    }
    
    private void cargarTipDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar());    
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();  
        strTipIngEgr=objTipDoc.gettx_natdoc();
        
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), "DevCom");            
        
        //getIva();
    }   
    
    private void listadoTipDoc(String campo,String strDesBusqueda ,int intTipo){
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
        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
        {
            if(!vcoTipDoc.getValueAt(1).equals("")){
                 cargarTipDoc(Integer.parseInt(vcoTipDoc.getValueAt(1)));
                 if (objTooBar.getEstado() == 'n') cargarNumDocPreImp();

            }
        }      
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCliDir = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        txtAte = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        lblDoc = new javax.swing.JLabel();
        txtDev = new javax.swing.JTextField();
        lblDev = new javax.swing.JLabel();
        txtVenNom = new javax.swing.JTextField();
        txtVenCod = new javax.swing.JTextField();
        lblVen = new javax.swing.JLabel();
        lblFecDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFromFac = new javax.swing.JLabel();
        txtFromFac = new javax.swing.JTextField();
        txtFecFac = new javax.swing.JTextField();
        lblFecFac = new javax.swing.JLabel();
        cboTipDoc = new javax.swing.JComboBox();
        txtDesCorTipDoc = new javax.swing.JTextField();
        local = new javax.swing.JComboBox();
        txtDoc = new javax.swing.JTextField();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
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
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(600, 400));
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

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 135));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo Documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(8, 10, 94, 20);

        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCliDir);
        txtCliDir.setBounds(104, 110, 326, 20);

        lblCli.setText("Proveedor:");
        panGenCab.add(lblCli);
        lblCli.setBounds(8, 90, 94, 20);

        lblDir.setText("Dirección:");
        panGenCab.add(lblDir);
        lblDir.setBounds(8, 110, 94, 20);

        txtCliCod.setBackground(objParSis.getColorCamposObligatorios());
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panGenCab.add(txtCliCod);
        txtCliCod.setBounds(104, 90, 66, 20);

        txtCliNom.setBackground(objParSis.getColorCamposObligatorios());
        txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panGenCab.add(txtCliNom);
        txtCliNom.setBounds(170, 90, 260, 20);

        txtAte.setBackground(objParSis.getColorCamposSistema());
        panGenCab.add(txtAte);
        txtAte.setBounds(510, 70, 140, 20);

        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblAte);
        lblAte.setBounds(432, 70, 75, 20);

        lblDoc.setText("Codigo documento:");
        panGenCab.add(lblDoc);
        lblDoc.setBounds(8, 50, 94, 20);

        txtDev.setBackground(objParSis.getColorCamposObligatorios());
        txtDev.setMaximumSize(null);
        txtDev.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtDev);
        txtDev.setBounds(104, 70, 91, 20);

        lblDev.setText("No. Devolucion:");
        panGenCab.add(lblDev);
        lblDev.setBounds(8, 70, 94, 20);

        txtVenNom.setBackground(objParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panGenCab.add(txtVenNom);
        txtVenNom.setBounds(540, 50, 110, 20);

        txtVenCod.setBackground(objParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panGenCab.add(txtVenCod);
        txtVenCod.setBounds(510, 50, 30, 20);

        lblVen.setText("Comprador:");
        lblVen.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblVen);
        lblVen.setBounds(432, 50, 75, 20);

        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(432, 10, 105, 20);

        txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtCodTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 10, 15, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(195, 10, 210, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(405, 10, 20, 20);

        lblFromFac.setText("Núm.Doc. O/C:");
        panGenCab.add(lblFromFac);
        lblFromFac.setBounds(8, 30, 92, 20);

        txtFromFac.setBackground(objParSis.getColorCamposSistema());
        txtFromFac.setMaximumSize(null);
        txtFromFac.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFromFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFromFacActionPerformed(evt);
            }
        });
        panGenCab.add(txtFromFac);
        txtFromFac.setBounds(104, 30, 91, 20);

        txtFecFac.setMaximumSize(null);
        txtFecFac.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtFecFac);
        txtFecFac.setBounds(540, 30, 110, 20);

        lblFecFac.setText("Fecha de Orden:");
        panGenCab.add(lblFecFac);
        lblFecFac.setBounds(432, 30, 105, 20);

        panGenCab.add(cboTipDoc);
        cboTipDoc.setBounds(195, 30, 80, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(104, 10, 91, 20);

        panGenCab.add(local);
        local.setBounds(275, 30, 150, 20);

        txtDoc.setBackground(objParSis.getColorCamposSistema()
        );
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtDoc);
        txtDoc.setBounds(104, 50, 91, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "Descripción", "Unidad", "Bodega", "Cantidad", "Devolucion", "Costo", "%Desc", "Iva", "Total", "co_itm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false, false, false
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

        panSur.setPreferredSize(new java.awt.Dimension(430, 75));
        panSur.setLayout(new java.awt.BorderLayout());

        panObs.setLayout(new java.awt.GridLayout(2, 1));

        panObs1.setLayout(new java.awt.BorderLayout());

        lblObs1.setText("Observación 1:");
        panObs1.add(lblObs1, java.awt.BorderLayout.WEST);

        spnObs1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnObs1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs1.setLineWrap(true);
        txaObs1.setWrapStyleWord(true);
        spnObs1.setViewportView(txaObs1);

        panObs1.add(spnObs1, java.awt.BorderLayout.CENTER);

        panObs.add(panObs1);

        panObs2.setPreferredSize(new java.awt.Dimension(250, 25));
        panObs2.setLayout(new java.awt.BorderLayout());

        lblObs2.setText("Observación 2:");
        panObs2.add(lblObs2, java.awt.BorderLayout.WEST);

        spnObs2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnObs2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs2.setLineWrap(true);
        txaObs2.setWrapStyleWord(true);
        spnObs2.setViewportView(txaObs2);

        panObs2.add(spnObs2, java.awt.BorderLayout.CENTER);

        panObs.add(panObs2);

        panSur.add(panObs, java.awt.BorderLayout.CENTER);

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

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panTot.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panTot.add(txtTot);

        panSur.add(panTot, java.awt.BorderLayout.EAST);

        panGen.add(panSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarVentanConsulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        listadoTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        //Buscando los que contengan lo que se digito en DEscripcion de Tipo de cliente
        listadoTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
       listadoTipDoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtFromFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromFacActionPerformed
        if(cboTipDoc.getSelectedIndex()>=0 && !txtFromFac.getText().trim().equals(""))
        {
            try{
                /*Armar sentencia SQL */
                sSQL  = " SELECT a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli " + 
                        "      , a.co_com as co_ven, a.tx_nomven as nomven, a.co_doc, a.fe_doc, a.tx_ate " + 
                        "      , a.co_forPag, a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva, a.st_reg, b.st_ivacom  " + 
                        " FROM tbm_cabMovInv AS a " + 
                        " INNER JOIN tbm_cli AS b ON (b.co_emp=a.co_emp AND b.co_cli=a.co_cli) "+   
                        " WHERE a.co_emp = " + objParSis.getCodigoEmpresa() +
                        " AND a.co_loc = " + vecLoc.elementAt(local.getSelectedIndex())+
                        " AND a.co_tipdoc = " + vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()) +
                        " AND a.ne_numdoc = " + txtFromFac.getText() +
                        " AND a.st_reg NOT IN ('I','E') AND a.st_coninv = 'C'";

                if(objUti.getNumeroRegistro(jfrThis,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),sSQL)>0)
                {
                    cargaFactura(sSQL);
                    noEditable2(false);  
                             
                    java.awt.Color colBack = txtFromFac.getBackground(); 
                    txtFromFac.setEditable(false);
                    txtFromFac.setBackground(colBack);  
                }
                else{
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El documento esta anulado, pendiente de Comfirmación o no existe." ;
                    javax.swing.JOptionPane.showMessageDialog(javax.swing.JOptionPane.getFrameForComponent(this),strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);                            
                    limpiarFrm();
                    cargarNumDocPreImp();
                }
            }                                
            catch(Exception Evt) {
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
            }
        }
        else{
            mostrarMsgInf("Debe seleccionar el tipo o numero de documento a cargar para la devolucion.");
        }
    }//GEN-LAST:event_txtFromFacActionPerformed


        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboTipDoc;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDev;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecFac;
    private javax.swing.JLabel lblFromFac;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JLabel lblVen;
    private javax.swing.JComboBox local;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObs1;
    private javax.swing.JPanel panObs2;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDev;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFecFac;
    private javax.swing.JTextField txtFromFac;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";            
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);        
    }    
    
    private void mostrarMsgValCam(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    
    
    private boolean mostrarMsgYesNo(String strMsg2){
        boolean blnres=false;
        javax.swing.JOptionPane oppMsg2=new javax.swing.JOptionPane();
        String strTit2="Mensaje del sistema Zafiro";
        if(!(oppMsg2.showConfirmDialog(this,strMsg2,strTit2,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 1 ))
             blnres=true;  // SI
         else
            blnres=false;  // NO
        return blnres;
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis(){
        objDocLis = new ZafDocLis();
        //Cabecera
        txtDoc.getDocument().addDocumentListener(objDocLis);
    }   
    
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
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

    /*
     * Obtiene el Iva que se debe cobrar en la empresa actual
     */
    private void getIva(){
        if (objTipDoc.getst_iva().equals("S")){
            dblPorIva = objCtaCtb.getPorcentajeIvaCompras();
        }else{
            dblPorIva = 0;                        
        }
        lblIva.setText("IVA " + dblPorIva + "%");
    }

    private void noEditable2(boolean blnEditable)
    {
        java.awt.Color colBack = txtDoc.getBackground();
 
        colBack = txtCodTipDoc.getBackground();
        txtCodTipDoc.setEditable(blnEditable);
        txtCodTipDoc.setBackground(colBack);        

        colBack = txtDesCorTipDoc.getBackground();
        txtDesCorTipDoc.setEditable(blnEditable);
        txtDesCorTipDoc.setBackground(colBack);        

        colBack = txtDoc.getBackground();
        txtDoc.setEditable(blnEditable);
        txtDoc.setBackground(colBack);  
            
        colBack = txtFromFac.getBackground();
        txtFromFac.setEditable(!blnEditable);
        txtFromFac.setBackground(colBack);    

        cboTipDoc.setEnabled(!blnEditable);
    }
    
    private void noEditable(boolean blnEditable){
        java.awt.Color colBack = txtDoc.getBackground();

        txtDoc.setEditable(blnEditable);
        txtDoc.setBackground(colBack);

        colBack = txtCliCod.getBackground();
        txtCliCod.setEnabled(blnEditable);
        txtCliCod.setBackground(colBack);        

        colBack = txtCliCod.getBackground();
        txtCliCod.setEditable(blnEditable);
        txtCliCod.setBackground(colBack);        

        colBack = txtCliNom.getBackground();
        txtCliNom.setEditable(blnEditable);
        txtCliNom.setBackground(colBack);        

        colBack = txtCliDir.getBackground();
        txtCliDir.setEditable(blnEditable);
        txtCliDir.setBackground(colBack);        

        colBack = txtAte.getBackground();
        txtAte.setEditable(blnEditable);
        txtAte.setBackground(colBack);        

        colBack = txtVenCod.getBackground();
        txtVenCod.setEditable(blnEditable);
        txtVenCod.setBackground(colBack);        

        colBack = txtVenNom.getBackground();
        txtVenNom.setEditable(blnEditable);
        txtVenNom.setBackground(colBack);        

        colBack = txtFecFac.getBackground();
        txtFecFac.setEnabled(blnEditable);
        txtFecFac.setBackground(colBack);        


        colBack = txtSub.getBackground();
        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);        

        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);        

        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);  
    }
  
    private void limpiarFrm(){
        //Cabecera
        txtDoc.setText("");
        txtFromFac.setText("");
        txtDev.setText("");
        txtCliCod.setText("");
        txtCliNom.setText("");
        txtCliDir.setText("");
        dtpFecDoc.setText("");
        txtVenCod.setText("");
        txtVenNom.setText("");
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
        lblTit.setText(""+objParSis.getNombreMenu()+"");
        //Asiento de Diario
        objAsiDia.inicializar();
    }   

    private void calculaSubtotal(){
        double dblCan,dblDes,dblCosto;
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_CANMOV);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PREVEN)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PREVEN);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PORDES);
        dblCan = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV).toString());
        dblCosto = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PREVEN).toString());
        if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES).toString())/100);
        else dblDes = 0;
        tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objParSis.getDecimalesMostrar())),tblDat.getSelectedRow(), INT_TBL_TOTAL);
        calculaTotal();
        generaAsiento();
    }
    
    private void calculaTotal()
    {
        double dblSub = 0, dblSub2 = 0, dblIva = 0, dblDes = 0, dblTmp=0;
        double dblIvaCom=0,dblTotalCom=0,dblSubtotalCom=0;
        for (int i=0;i<tblDat.getRowCount();i++){ 
            dblSub2 = objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar());  
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
            if(tblDat.getValueAt(i, INT_TBL_BLNIVA)!=null){
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar()): 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
        }
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva,intNumDec);
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,intNumDec);
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,intNumDec);
        if(stIvaCom.equals("N")){
           txtSub.setText( "" + dblSubtotalCom );
           txtIva.setText( "0.00" );
           txtTot.setText( ""+ dblSubtotalCom);
           dblTotalCom=dblSubtotalCom;
             dblIvaCom=0;
        }
        else{
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "" + dblIvaCom );
            txtTot.setText( ""+ dblTotalCom);
        } 
    }
    
    private void cargaFactura(String strSQL){
        String strSer ="";
        try{
            ResultSet rstDet,rstCab ; 
            Connection con;
            Statement  stm;
            double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;            
            
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null){
                stm = con.createStatement();
                rstCab = stm.executeQuery(strSQL);
                int intNumCot = 0;
                    if(rstCab.next()){
                        intNumCot   = rstCab.getInt("co_doc");
                        intNumDocOrdCom = intNumCot;
                        intSecuencialFac = rstCab.getInt("co_doc");
                        
                        txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
                        txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
                        txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));
                        
                        if(rstCab.getDate("fe_doc")==null){
                          dtpFecDoc.setText("");  
                        }else{
                            java.util.Date dateObj = rstCab.getDate("fe_doc");
                            java.util.Calendar calObj = java.util.Calendar.getInstance();
                            calObj.setTime(dateObj);
                            dtpFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                              calObj.get(java.util.Calendar.MONTH)+1     ,
                                              calObj.get(java.util.Calendar.YEAR)        );
                        }
                        txtFecFac.setText(dtpFecDoc.getText());
                        dtpFecDoc.setHoy();
                        txtVenCod.setText(((rstCab.getString("co_ven")==null)?"":rstCab.getString("co_ven")));
                        txtVenNom.setText(((rstCab.getString("nomven")==null)?"":rstCab.getString("nomven")));
                        txtAte.setText(((rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate")));

                        //Pie de pagina
                        txaObs1.setText(((rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1")));
                        txaObs2.setText(((rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2")));
                        
                        stIvaCom = rstCab.getString("st_ivacom");
                        /*AGREGADO PARA CAMBIO DEL 14% IVA*/
                        dblPorIva=rstCab.getDouble("nd_porIva");
                        /*AGREGADO PARA CAMBIO DEL 14% IVA*/
                     
                        //Detalle         

                        //Extrayendo los datos del detalle respectivo a esta orden de compra nd_cancon
                        String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                        " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                        " THEN 'S' ELSE 'N' END AS proconf  ";
                    
                        sSQL  = " Select a.co_reg, a.co_itm, a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, a.nd_can," +
                                " a.nd_canorg, a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, inv.st_ser, a.co_itmact, a.nd_cancon " +
                                " "+strAux2+"  FROM tbm_detMovInv as a " +
                                " INNER JOIN tbm_inv AS inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) where " +
                                " a.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                                " a.co_loc = " +vecLoc.elementAt(local.getSelectedIndex())+ " and " +
                                " a.co_tipdoc = " + vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())      + " and " +
                                " a.co_doc = " + rstCab.getString("co_doc")      + " order by a.co_reg";
                        //System.out.println(sSQL);
                   
                        Statement stmaux = con.createStatement();
                        ResultSet rst= stmaux.executeQuery(sSQL);
                      
                        java.util.Vector vecData = new java.util.Vector();
                        while (rst.next())
                        {  
                            strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                            vecReg.add(INT_TBL_BUTITM, "");
                            vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                            vecReg.add(INT_TBL_BUTBOD, "");
                            vecReg.add(INT_TBL_CANCOM, new Double(rst.getDouble("nd_cancon"))); 
                            vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_canorg")));                                                                           
                            vecReg.add(INT_TBL_PREVEN, new Double(rst.getDouble("nd_cosuni")));
                            vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                            String strIva = rst.getString("st_ivacom");
                            Boolean blnIva = new Boolean(strIva.equals("S"));                         
                            vecReg.add(INT_TBL_BLNIVA, blnIva);
                            dblCan    = rst.getDouble("nd_canorg");
                            dblPre    = rst.getDouble("nd_cosuni");
                            dblPorDes = rst.getDouble("nd_pordes");
                            dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                            dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;
                            if (blnIva.booleanValue()){                            
                               dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                            }

                            vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                            vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                            vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                            vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                            vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_canorg")));
                            strSQL = " select abs(abs(nd_canorg) - abs(sum(nd_cancon))) as nd_can "+
                                     " from tbm_cabmovinv as cab left outer join tbm_detmovinv as det on (cab.co_emp = det.co_emp  and cab.co_loc =det.co_loc and cab.co_tipdoc = det.co_tipdoc and cab.co_doc = det.co_doc) "+
                                     " where cab.co_emp = " + objParSis.getCodigoEmpresa() + " and cab.co_loc = "+vecLoc.elementAt(local.getSelectedIndex())+ " and cab.co_tipdoc = " + objTipDoc.getco_tipdoc() + " and ne_numcot = " + txtFromFac.getText() +" and co_reg = " + rst.getInt("co_reg") + " and co_itm=" + rst.getInt("co_itm") + " and cab.st_reg ='A'" +
                                     " group by nd_canorg ";

                            // System.out.println(">>> "+ strSQL);
                            java.sql.Statement stmDev = con.createStatement();
                            java.sql.ResultSet rstAux = stmDev.executeQuery(strSQL);
                            if (rstAux.next()){
                                vecReg.add(INT_TBL_CANDEV, new Double(rstAux.getDouble("nd_can")));
                            }else{
                                vecReg.add(INT_TBL_CANDEV, new Double(rst.getDouble("nd_can")));
                            }
                            rstAux.close();
                            stmDev.close();
                            vecReg.add(INT_TBL_ESTADO, "E");
                            vecReg.add(INT_TBL_IVATXT, "");
                            vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));

                            vecReg.add(INT_TBL_ITMSER, strSer );
                            vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact"));
                            vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                         
                            vecData.add(vecReg);                         
                        }
                        rst.close();
                        stmaux.close();
                        objTblMod.setData(vecData);
                        tblDat .setModel(objTblMod);                                 
                     
                        txtIva.setText(""+dblIva);
                        txtTot.setText(""+objUti.redondear((objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar())+dblIva),objParSis.getDecimalesMostrar()));

                        //calculaTotal();
  
                        /*VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO*/
                        String strStatus = (rstCab.getString("st_reg")==null)?"":rstCab.getString("st_reg");
                        if(strStatus.equals("I")){
                            //lblFacNumDes.setText( lblFacNumDes.getText()+ "#ANULADO#");
                            objUti.desactivarCom(jfrThis);
                        }
                        calculaTotal();
                    }
                    rstCab.close();
                    stm.close();
                    con.close();
                    setEditable(true);                    
            }
        }//fin Try
        catch(SQLException Evt)    {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt)    {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
        noEditable2(true);
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
     
    private void  refrescaDatos(){
        String strSer="";
        try{      
            int intNumCot = 0;
            if(rstCab != null){

                //Detalle        
                //Extrayendo los datos del detalle respectivo a esta orden de compra
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";

                sSQL  = "Select a.co_reg, a.co_itm, a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, a.nd_can, " +
                " a.nd_canorg, a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, inv.st_ser, a.co_itmact , a.nd_poriva " +
                        " "+strAux2+" FROM tbm_detMovInv as a" +
                        " INNER JOIN tbm_inv as inv ON (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) WHERE " +
                        " a.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                        " a.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                        " a.co_tipdoc = " + objTipDoc.getco_tipdoc()     + " and " +
                        " a.co_doc = " + rstCab.getString("co_doc")      + " order by a.co_reg";
//                    System.out.println(sSQL);
                java.sql.Statement stm = conCab.createStatement();
                java.sql.ResultSet rst= stm.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;
                      
                java.util.Vector vecData = new java.util.Vector();
                while (rst.next()){   
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANCOM, new Double(rst.getDouble("nd_canorg"))); 
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")*(rst.getDouble("nd_can")<0?getAccionDoc():1)));                                                                           
                    vecReg.add(INT_TBL_PREVEN, new Double(rst.getDouble("nd_cosuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivacom");
                    Boolean blnIva = new Boolean(strIva.equals("S"));                         
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can")*getAccionDoc();
                    dblPre    = rst.getDouble("nd_cosuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                    dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;

                    dblPorIva= rst.getDouble("nd_poriva");
                    if (blnIva.booleanValue()){                            
                       dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                    }

                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")*(rst.getDouble("nd_can")<0?getAccionDoc():1)));                                                                           
                    String strSQL = " select abs(abs(nd_canorg) - abs(sum(nd_can))) as nd_can "+
                             " from tbm_cabmovinv as cab left outer join tbm_detmovinv as det on (cab.co_emp = det.co_emp  and cab.co_loc =det.co_loc and cab.co_tipdoc = det.co_tipdoc and cab.co_doc = det.co_doc) "+
                             " where cab.co_emp = " + objParSis.getCodigoEmpresa() + " and cab.co_loc = "+ objParSis.getCodigoLocal()+ " and cab.co_tipdoc = " + objTipDoc.getco_tipdoc() + " and ne_numcot = " + rstCab.getString("ne_numcot") +" and co_reg = " + rst.getInt("co_reg") + " and co_itm=" + rst.getInt("co_itm") + " and cab.st_reg ='A'" +
                             " group by nd_canorg ";
                    java.sql.Statement stmDev = conCab.createStatement();
                 //   System.out.println(strSQL);
                    java.sql.ResultSet rstAux = stmDev.executeQuery(strSQL);
                    if (rstAux.next()){
                        vecReg.add(INT_TBL_CANDEV, new Double(rstAux.getDouble("nd_can")));
                    }else{
                        vecReg.add(INT_TBL_CANDEV, new Double(rst.getDouble("nd_canorg")));
                    }
                    rstAux.close();
                    stmDev.close();                         
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_CODITMACT , rst.getString("co_itmact"));
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));

                    vecData.add(vecReg);                         
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);                                 

                txtIva.setText(""+dblIva);
                txtTot.setText(""+objUti.redondear((objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar())+dblIva),objParSis.getDecimalesMostrar()));

                calculaTotal();
                    
                /*
                 * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                 */
                objAsiDia.consultarDiarioCompleto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), rstCab.getInt("co_doc"));
            }
            blnHayCam = false; // Seteando que no se ha hecho cambios
        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt)   {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
    }

    /*
     * Retorna el numero secuencial asociados a ese numero y tipo de documento.
     */
    private int[] getNumTipDoc(int int_OrdDoc){
        int intSec[] = new int[2];
        try{
            ResultSet rstFromFac;
            Statement stmFromFac = conCab.createStatement();
            String SqlSecuencial = "Select ne_numdoc, co_tipdoc from tbm_cabmovinv where ne_numdoc   = " + int_OrdDoc ;
            rstFromFac = stmFromFac.executeQuery(SqlSecuencial);
            if(rstFromFac.next()){
                intSec[0] = rstFromFac.getInt(1);
                intSec[1] = rstFromFac.getInt(2);
            }
            rstFromFac.close();
            stmFromFac.close();
        }
        catch(SQLException Evt) {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt) {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
        return intSec;
    }
    /**
     * Metodo que verifica la naturaleza del documento verificando del objTooBar Tipo Doc
     * devuleve 1 si es tipo Ingreso y -1 si es Egreso
     *@autor: jsalazar
     */
    private int getAccionDoc(){
        if (objTipDoc.gettx_natdoc().equals("I"))
            return 1;
        else
            return -1;
   }       
    

    public class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objParSis);
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
            return true;
        }        
        public boolean beforeModificar() {
            return true;
        }        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
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
                noEditable(false);                
                noEditable2(false);                
                dtpFecDoc.setHoy();
               // cargarTipDocPre();  
                cargarNumDocPreImp();
                objAsiDia.setEditable(true);
                setEditable(true);
                blnHayCam=false;
                
                java.awt.Color colBack = txtFromFac.getBackground();
                txtFromFac.setEditable(true);
                txtFromFac.setBackground(colBack);    
                dtpFecDoc.setEnabled(true);
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
            
            if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                return false;
            
            if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtDoc.getText())))
                return false;
             
            if(!verificaItmUni())
                return false;
            
            if (!anularReg())
                return false;
            
            recostearItmGrp();
            
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
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

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
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

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
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
                
                if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                    return false;
                
                if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtDoc.getText())))
                    return false;
           
                if (!eliminarReg())
                    return false;
                
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
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar(){
        boolean blnPag=true;
        if(!isCamVal())
          return false;  

        if(objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(), Integer.parseInt(vecLoc.elementAt(local.getSelectedIndex()).toString()) ,  Integer.parseInt(vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString()) , intSecuencialFac  ))
        {
            blnPag=mostrarMsgYesNo("¿Desea Continuar de todas forma.. ?");
            //return false;
        }
        if(!blnPag) return false;

        if(!validarCanDev()) return false; 

        if(!verificaItmUni()) return false;

        if(!insertarReg()) return false;

         recostearItmGrp();

        blnHayCam=false;
        return true;
    }

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
            
            if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                return false;
                
            if (objUltDocPrint.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtDoc.getText())))
                return false;
            
            if (!isCamVal())
                return false;
            
            getNumDocOrdCom();
            
            if(!validarCanDev2()) 
                return false; 
            
            if(!verificaItmUni())
                return false;
             
            if (!actualizarReg())
                return false;
             
            recostearItmGrp();
              
            consultar2();     
              
            blnHayCam=false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
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
            blnHayCam=false;
            return blnRes;
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
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
            cargarRegistroInsert();
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
     
        public boolean imprimir() {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }
 
        public boolean vistaPreliminar(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }               
       
        public void clickConsultar(){
            limpiarFrm();
            dtpFecDoc.setText("");
            if (txtCodTipDoc.getText().trim().equals(""))
                //cargarTipDocPre();
            objAsiDia.setEditable(false);
            setEditable(false);
            dtpFecDoc.setEnabled(true);
        }    
        
        public boolean consultar() {
            strFiltro = FilSql();
            return _consultar(strFiltro);
        }           

        public void clickModificar(){
            txtDev.setEditable(true);                
            objAsiDia.setEditable(true);
            setEditable(true);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtDoc.setEditable(false);
            butTipDoc.setEnabled(false);
           
            noEditable(false);                
            noEditable2(false);  
                
            java.awt.Color colBack = txtFromFac.getBackground();
            txtFromFac.setEditable(false);
            txtFromFac.setBackground(colBack);    
                
            dtpFecDoc.setEnabled(true);
        }
        
        public void clickEliminar(){
            objAsiDia.setEditable(false);
            setEditable(false);
        }
           
        public void clickAnular(){
            objAsiDia.setEditable(false);
            setEditable(false);
        }

        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        public void clickAceptar(){

        }
        public void clickCancelar(){
        }
            
             
    }             
   
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */
    private void generaAsiento(){
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
        int INT_VEC_NUEVO    = 8;
        int intEst=0;
         
        //********************************************************
        for(int i=0; i<tblDat.getRowCount(); i++){
            if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("S")) {
                    dblTotSer += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                    intEst=1;
                }
            }
        }
        //********************************************************
        if (vecDetDia==null) vecDetDia = new java.util.Vector();
        else vecDetDia.removeAllElements();
          
        java.util.Vector vecReg; 
         
        int intCodBodT,intCodBod;
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
            for (int j=0;j<arreBod.size();j++){
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) {  
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                            {
                                intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                if(intCodBod==intCodBodT)
                                   dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
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
                    vecReg.add(INT_VEC_DEBE, new Double(dblSubtotal));  
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
                    int intCodIvaCtble=objParSis.getCodigoCuentaContableIvaCompras(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objUti.parseDate(txtFecFac.getText(), "yyyy-MM-dd"));
                    //vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_CODCTA,intCodIvaCtble);                
                    //vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.obtenerNumCta(objParSis.getCodigoEmpresa(), intCodIvaCtble));   
                    vecReg.add(INT_VEC_BOTON, null);
                    //vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.obtenerNomCta(objParSis.getCodigoEmpresa(), intCodIvaCtble));                 
                    vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));
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
            vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar())));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDia.add(vecReg);  
        }
        else{
            for (int j=0;j<arreBod.size();j++){
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) {    
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                            {
                                intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                if(intCodBod==intCodBodT)
                                  dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
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
                    vecReg.add(INT_VEC_HABER, new Double(dblSubtotal));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDia.add(vecReg);
                }
            }  

            if(stIvaCom.equals("S"))
            {  
                if (objTipDoc.getst_iva().equals("S"))
                {
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);   
                    int intCodIvaCtble=objParSis.getCodigoCuentaContableIvaCompras(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objUti.parseDate(txtFecFac.getText(), "yyyy-MM-dd"));
                    //vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_CODCTA,intCodIvaCtble);                
                    //vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.obtenerNumCta(objParSis.getCodigoEmpresa(), intCodIvaCtble));   
                    vecReg.add(INT_VEC_BOTON, null);
                    //vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.obtenerNomCta(objParSis.getCodigoEmpresa(), intCodIvaCtble));                 
                    vecReg.add(INT_VEC_DEBE, new Double(0)); 
                    vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));   
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
            vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDia.add(vecReg);  
        }   
         
        if(intEst==1){
            if(dblTotSer > 0 ){
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaSerCompras()));
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaSerCom());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaSerCom());                 
                vecReg.add(INT_VEC_DEBE, new Double(0));
                vecReg.add(INT_VEC_HABER, new Double(dblTotSer)); 
                vecReg.add(INT_VEC_REF, null);
                vecReg.add(INT_VEC_NUEVO, null);
                vecDetDia.add(vecReg);  
            }
        }
         
        objAsiDia.setDetalleDiario(vecDetDia);         
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
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                butTipDoc.setEnabled(false);
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
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= "SELECT DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                      " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                      " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra "   + //Campos del Tab OTROS 
                      " ,DocCab.st_regrep, cli.st_ivacom FROM tbm_cabMovInv as DocCab " +
                      "  left outer join  tbm_cabtipdoc as doc  on (DocCab.co_emp = doc.co_emp and DocCab.co_loc = doc.co_loc and DocCab.co_tipdoc = doc.co_tipdoc) " +
                      " inner join tbm_cli as cli on (cli.co_emp=DocCab.co_emp and cli.co_cli=DocCab.co_cli ) "+
                      " Where  DocCab.co_emp=" + rstCab.getString("co_emp") +
                      " AND DocCab.co_loc="    + rstCab.getString("co_loc") +
                      " AND DocCab.co_tipDoc=" + rstCab.getString("co_tipDoc") +
                      " and DocCab.st_reg not in('E') AND DocCab.co_doc="    + rstCab.getString("co_doc") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                    int intNumCot = rst.getInt("co_doc");
                    txtDoc.setText(""+intNumCot);

                    txtCodTipDoc.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                    int intNumTipDoc[] = getNumTipDoc(rst.getInt("ne_numcot"));
                    if(intNumTipDoc != null){
                        txtFromFac.setText(""+intNumTipDoc[0]);
                       // cboTipDoc.setSelectedIndex(vecTipDoc.indexOf(""+intNumTipDoc[1]));
                    }
                    
                    cboTipDoc.setSelectedIndex(0);
                    
                    txtDev.setText(""+rst.getInt("ne_numDoc"));
                    txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    
                    STR_ESTREG=rst.getString("st_regrep");
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

                    txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    /*
                     * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                     */
                    if(strAux.equals("I")){
                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm' ){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                            noEditable2(false);
                        }
                    }
                 
                 java.awt.Color colBack = txtFromFac.getBackground();
                 txtFromFac.setEditable(false);
                 txtFromFac.setBackground(colBack);    
                 
                 /*CAMBIO PARA EL IVA DEL 14%*/
                 dblPorIva=rst.getDouble("nd_porIva");
                 /*CAMBIO PARA EL IVA DEL 14%*/
                 
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
                blnHayCam=false;
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
    * Metodo verificar que no este anulada
    * @autor: jsalazar
    */
    private boolean isAnulada()
    {
       boolean blnRes = false;
        java.sql.Connection conTmp ;
        java.sql.Statement stmTmp;
        String strSQL = "";
        try
        {
            conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
                stmTmp = conTmp.createStatement();
                strSQL = "";
                strSQL = " Select count(*) from tbm_cabmovinv ";
                strSQL += " where  co_emp = " + objParSis.getCodigoEmpresa() + " and  co_loc = " + objParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtDoc.getText() + " and st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
  //                          mostrarMsgInf("Documento anulado no se puede modificar");
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

    private void cargarRegistroInsert(){
        try{
            conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                sSQL= "SELECT co_emp,co_loc,co_tipdoc, co_doc " +
                      " FROM tbm_cabMovInv" +
                      " WHERE co_emp = "  + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                      " and co_loc = "    + objParSis.getCodigoLocal() +
                      " AND co_tipDoc="   + objTipDoc.getco_tipdoc() +
                      " AND co_doc="      + txtDoc.getText() ;
                rstCab=stmCab.executeQuery(sSQL);
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    for (int i=0;i<objTblMod.getRowCountTrue();i++){                                            
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODITM).toString(), i, INT_TBL_ITMORI);
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODBOD).toString(), i, INT_TBL_BODORI);
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CANMOV).toString(), i, INT_TBL_CANORI);
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
                 
    private void cargarNumDocPreImp(){
        int intNumeroDoc = objUltDocPrint.getUltDoc(Integer.parseInt(txtCodTipDoc.getText()));
        intNumeroDoc++;
        txtDev.setText("" + intNumeroDoc);
    }
     
    private boolean _consultar(String strFil){
        strFiltro= strFil;
        try{//odbc,usuario,password
            conCab=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){

                //stmCab=conCab.createStatement();
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                 /*
                  * Agregando el Sql de Consulta para el Documento
                  */
                sSQL= "SELECT DocCab.co_emp,DocCab.co_loc,DocCab.co_tipdoc, co_doc,ne_numcot " +                                                                            
                      " FROM tbm_cabMovInv as DocCab left outer join  tbm_cabtipdoc as doc  on (DocCab.co_emp = doc.co_emp and DocCab.co_loc = doc.co_loc and DocCab.co_tipdoc = doc.co_tipdoc) " +
                      " Where  DocCab.co_emp = " + objParSis.getCodigoEmpresa()   +// Consultando en la empresa en la ke se esta trabajando
                      " and DocCab.st_reg not in('E')       and DocCab.co_loc = " + objParSis.getCodigoLocal() ; // Consultando en el local en el ke se esta trabajando

                sSQL= sSQL + strFiltro + " ORDER BY DocCab.co_Doc";
              //  System.out.println(sSQL);
                rstCab=stmCab.executeQuery(sSQL);

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
        catch(SQLException Evt)   {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        catch(Exception Evt)    {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                       
        return true;     
    }

    private boolean isCamVal(){
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Tipo de Documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de Cotización");
            dtpFecDoc.requestFocus();
            return false;
        }

        /*
         * VAlidando los campos ke deben ser numericos
         */
        if(!txtFromFac.getText().equals(""))
            if(!objUti.isNumero(txtFromFac.getText())){
                tabFrm.setSelectedIndex(0);                  
                mostrarMsgValCam("Cotizacion");
                txtFromFac.requestFocus();
                return false;
            }

        if(txtDev.getText().equals("") || !objUti.isNumero(txtDev.getText())){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("No devolucion");
            txtDev.selectAll();
            txtDev.requestFocus();
            return false;
        }
        for (int i=0;i<tblDat.getRowCount();i++){
            if (tblDat.getValueAt(i,INT_TBL_CANMOV)!=null){
                Double CanOri = Double.valueOf(tblDat.getValueAt(i,INT_TBL_CANCOM).toString());
                Double CanDev = Double.valueOf(tblDat.getValueAt(i,INT_TBL_CANMOV).toString());
                if (CanDev.doubleValue() > CanOri.doubleValue() ){
                     mostrarMsgInf("Selecciono una cantidad de devolucion mayor que la generada por la compra.");
                     tabFrm.setSelectedIndex(0);   
                     return false;
                }    
            }
        }
        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("Asiento descuadrado.");
            tabFrm.setSelectedIndex(1);
            return false;                                    
        }
        
        if (!objAsiDia.isDocumentoCuadrado(txtTot.getText())){
             mostrarMsgInf("Asiento y Total del Documento no cuadran.\nPor favor verifique!!");
             tabFrm.setSelectedIndex(1);
             return false;                                                       
        }
        
        if(!txtCliCod.getText().equals(""))
            objCliente_dat.setCliente(Integer.parseInt( txtCliCod.getText()  ));
        else
            objCliente_dat = new ZafCliente_dat(objParSis);               

        return true; 
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
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
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

                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("codEmp", new Integer(objParSis.getCodigoEmpresa()) );
                                mapPar.put("codLoc", new Integer(objParSis.getCodigoLocal()) );
                                mapPar.put("codTipDoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
                                mapPar.put("codDoc", new Integer( Integer.parseInt(txtDoc.getText())) );
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                
//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    protected void finalize() throws Throwable
    {   
        System.gc();
        super.finalize();
    }
 
    private void getNumDocOrdCom(){
        try{
            Connection conn =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

            //Armar sentencia SQL
            String sql= " SELECT co_doc" +
                        " FROM tbm_cabMovInv" + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " WHERE co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                        " AND co_loc = " + objParSis.getCodigoLocal()   +// Consultando en el local en el ke se esta trabajando
                        " AND co_tipdoc = " + vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()) +
                        " AND ne_numdoc = " + txtFromFac.getText() +// Consultando en el numero de documento                        
                        " AND st_reg = 'A' "+
                        " ORDER BY co_doc";
            //System.out.println("SSQLLLL >>>> "+ sql );  
            Statement stmDocDet=conn.createStatement();
            ResultSet rstDocDet2 = stmDocDet.executeQuery(sql);
            if(rstDocDet2.next()){
                intNumDocOrdCom = rstDocDet2.getInt("co_doc");
            }
        }
        catch(SQLException Evt)  {    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }  
    }
 
    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
        boolean blnRes=true;
        Connection conn;
        try{
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conn!=null){

                cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
                cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );

                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

    private boolean cargarCabReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                //Armar sentencia SQL
                strSql= " SELECT doc.tx_descor, doc.tx_deslar, DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli" + //<== Campos con los datos del CLiente para la cabecera
                        "      , DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate" + //<==Campos que aparecen en la parte superior del 1er Tab
                        "      , DocCab.ne_numCot, DocCab.ne_numDoc, DocCab.ne_numGui, DocCab.co_forPag" + //<==Campos que aparecen en la parte superior del 2do Tab
                        "      , DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, DocCab.st_reg, DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra"   + //Campos del Tab OTROS
                        "      , DocCab.st_regrep, cli.st_ivacom "+
                        " FROM tbm_cabMovInv AS DocCab " +
                        " LEFT OUTER JOIN tbm_cabtipdoc AS doc ON (DocCab.co_emp = doc.co_emp AND DocCab.co_loc = doc.co_loc AND DocCab.co_tipdoc = doc.co_tipdoc) " +
                        " INNER JOIN tbm_cli AS cli ON (cli.co_emp=DocCab.co_emp AND cli.co_cli=DocCab.co_cli ) "+
                        " WHERE DocCab.co_emp="+strCodEmp+" AND DocCab.co_loc="+strCodLoc+" AND DocCab.co_tipDoc="+strCodTipDoc+" AND DocCab.co_doc="+strCodDoc;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    int intNumCot = rstLoc.getInt("co_doc");
                    txtDoc.setText(""+intNumCot);
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc")==null)?"":rstLoc.getString("co_tipdoc")));
                    txtFromFac.setText(rstLoc.getString("ne_numcot"));
                    
                    dblPorIva=rstLoc.getDouble("nd_porIva");

                    txtDesCorTipDoc.setText(rstLoc.getString("tx_descor"));
                    txtDesLarTipDoc.setText(rstLoc.getString("tx_deslar"));

                    cboTipDoc.setSelectedIndex(0);

                    txtDev.setText(""+rstLoc.getInt("ne_numDoc"));
                    txtCliCod.setText(((rstLoc.getString("co_cli")==null)?"":rstLoc.getString("co_cli")));
                    txtCliNom.setText(((rstLoc.getString("nomcli")==null)?"":rstLoc.getString("nomcli")));
                    txtCliDir.setText(((rstLoc.getString("dircli")==null)?"":rstLoc.getString("dircli")));

                    STR_ESTREG=rstLoc.getString("st_regrep");
                    stIvaCom=rstLoc.getString("st_ivacom");

                    if(rstLoc.getDate("fe_doc")==null){
                      dtpFecDoc.setText("");
                    }else{
                        java.util.Date dateObj = rstLoc.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        dtpFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }

                    txtVenCod.setText(((rstLoc.getString("co_ven")==null)?"":rstLoc.getString("co_ven")));
                    txtVenNom.setText(((rstLoc.getString("nomven")==null)?"":rstLoc.getString("nomven")));
                    txtAte.setText(((rstLoc.getString("tx_ate")==null)?"":rstLoc.getString("tx_ate")));
                    //Pie de pagina
                    txaObs1.setText(((rstLoc.getString("tx_obs1")==null)?"":rstLoc.getString("tx_obs1")));
                    txaObs2.setText(((rstLoc.getString("tx_obs2")==null)?"":rstLoc.getString("tx_obs2")));

                    strAux=rstLoc.getString("st_reg");
                }
                rstLoc.close();
                rstLoc=null;

                objAsiDia.consultarDiario( Integer.parseInt( strCodEmp ), Integer.parseInt( strCodLoc ), Integer.parseInt( strCodTipDoc ), Integer.parseInt( strCodDoc ) );

                if (strAux.equals("A"))
                    strAux="Activo";
                else if (strAux.equals("I"))
                    strAux="Anulado";
                else if (strAux.equals("E"))
                     strAux="Eliminado";
                else
                    strAux="Otro";
                objTooBar.setEstadoRegistro(strAux);
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }

    private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rst;
        String strSql="", strSer="";
        Vector vecData;
        double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0, dblIva = 0;

        try{
          if(conn!=null){
                stmLoc=conn.createStatement();

                vecData = new Vector();

                strSql= " SELECT a.co_reg, a.co_itm, a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, abs(a.nd_can) as nd_can" +
                        "      , a.nd_canorg, a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, inv.st_ser, a.co_itmact, a.nd_poriva  " +
                        "      , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) " +
                        "        IN ( SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                        "             inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                        "             WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a1.st_reg='A' and   a.st_reg='P' " +
                        "         )))THEN 'S' ELSE 'N' END AS proconf  "+
                        " FROM tbm_detMovInv AS a" +  
                        " INNER JOIN tbm_inv as inv ON (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm)" +
                        " WHERE a.co_emp = " +strCodEmp+ 
                        " and a.co_loc = " +strCodLoc +
                        " and a.co_tipdoc = " +strCodTipDoc +
                        " and a.co_doc = " +strCodDoc+ 
                        " order by a.co_reg";
                rst=stmLoc.executeQuery(strSql);
                while(rst.next()){
                    java.util.Vector vecReg = new java.util.Vector();

                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANCOM, new Double(rst.getDouble("nd_canorg")));
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can") ));
                    vecReg.add(INT_TBL_PREVEN, new Double(rst.getDouble("nd_cosuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivacom");
                    Boolean blnIva = new Boolean(strIva.equals("S"));
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_cosuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                    dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;
                    dblPorIva= rst.getDouble("nd_poriva");
                    if (blnIva.booleanValue()){
                       dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                    }

                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CANORI, "" );

                    vecReg.add(INT_TBL_CANDEV, new Double(rst.getDouble("nd_can")));

                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_CODITMACT , rst.getString("co_itmact"));
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));

                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);

                calculaTotal();

                rst.close();
                rst=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }

    private boolean validarCanDev2(){
        try{
            Connection conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
                    double dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                    if( dblCanMov > 0.00 )
                    {
                        int intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 

                        String sql =" select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                    " inner join tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
                                    " where a.co_docrel="+intNumDocOrdCom+" and a.co_emp="+objParSis.getCodigoEmpresa()+"  "+
                                    " and a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+
                                    " and a.co_doc != "+txtDoc.getText()+" and co_itm="+intCodItm+" and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.st_reg='A'";
                        //System.out.println(">>> "+ sql );
                        Statement stmDocDet=conIns.createStatement();
                        ResultSet rstDocDet2 = stmDocDet.executeQuery(sql);
                        double intNumDev=0; 
                        if(rstDocDet2.next()){
                            intNumDev = rstDocDet2.getDouble(1); 
                        }
                          
                        intNumDev=(intNumDev*-1)+dblCanMov;
                        //intNumDev=intNumDev+dblCanMov;
                        double dblCanOri = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString());

                        //System.out.println(">>> "+ tblFacDet.getValueAt(i, 1) + " >> " + dblCanOri + " >> " +intNumDev);     

                        if(intNumDev > dblCanOri ) {
                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                            String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="Este Item  "+ tblDat.getValueAt(i, INT_TBL_ITMALT) + "  Ya tiene devoluciones y la cantidad a devolver es mayor";
                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            return false;
                        }
                    }
               }
           }   
        }
        catch(SQLException Evt)   {    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }  
        return true;
    }

    private boolean actualizarPag(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                
                //Armar sentencia SQL
                strSql=" DELETE FROM tbm_pagMovInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
                       " AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText();
                stmLoc.executeUpdate(strSql);

                strSql="INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,fe_ven,mo_pag ,st_regrep) VALUES("+ 
                objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", "+objTipDoc.getco_tipdoc()+","+ 
                txtDoc.getText()+", 1 , '"+strFecSis+"', "+objUti.redondeo((Double.parseDouble(txtTot.getText())*getAccionDoc()),6)+",'M')";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;

                blnRes=true;      
            }
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }   
       
    private boolean actualizarReg(){
        boolean blnRes=false;
        java.sql.Connection conn;
        try{
            conn=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conn!=null){
               conn.setAutoCommit(false);

               objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());

                if(!objUltDocPrint.getExisteDoc( conn, txtCodTipDoc.getText(), txtDoc.getText(), txtDev.getText() )){
                    if(actualizarCab(conn)){
                        if(actualizarDet(conn)){ 
                            if(actualizarPag(conn)){ 
                                if(objAsiDia.actualizarDiario(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtDoc.getText()), txtDev.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A")){
                                    conn.commit();
                                    blnRes=true;
                                }else conn.rollback();  
                            }else conn.rollback();  
                        }else conn.rollback();  
                    }else conn.rollback();  
                }
                else{    
                    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="No. de Devolucion ya Existe";
                    oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    txtDev.requestFocus();
                    txtDev.selectAll();
                    return false;
                }
                objInvItm.limpiarObjeto(); 
                conn.close();
                conn=null;  
            }
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }   
    
    private boolean actualizarDet(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intTipCli=3;
        int intCodItm=0;
        int intCodBod=0;
        int intReaActStk=0;
        int intSer=0;
        double dlbCanMov=0;
        double dblCanDev=0;
        String strSql="";
        String strEstFisBod="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                StringBuffer stbins=new StringBuffer();

                intTipCli=objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(), conn);

                objInvItm.inicializaObjeto();

                strSql= " SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can"+
                        " , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) " +
                        "   IN ( SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                        "        INNER JOIN tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                        "        WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' " +
                        "   ))) THEN 'S' ELSE 'N' END AS proconf  "+
                        " FROM tbm_detMovInv as a" +
                        " INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) "+
                        " WHERE inv.st_ser='N' AND a.co_emp="+objParSis.getCodigoEmpresa() +
                        " AND a.co_loc="+objParSis.getCodigoLocal()+" AND a.co_tipDoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtDoc.getText(); 
                rstLoc=stmLoc.executeQuery(strSql); 
                while(rstLoc.next()){
                    intCodItm=rstLoc.getInt("co_itm");
                    intCodBod=rstLoc.getInt("co_bod");
                    dlbCanMov=rstLoc.getDouble("nd_can");
                    strEstFisBod=rstLoc.getString("proconf");

                    objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, strMerIngEgr, strTipIngEgr, -1); 
                    intReaActStk=1;
                }
                rstLoc.close();
                rstLoc=null;

                strSql =" DELETE FROM tbm_detMovInv WHERE co_emp="+objParSis.getCodigoEmpresa()+"  "+  
                        " AND co_loc="+objParSis.getCodigoLocal()+" AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText(); 
                stmLoc.executeUpdate(strSql);       

                if(intReaActStk==1){
                    if(!objInvItm.ejecutaActStock(conn, intTipCli)){
                        stmLoc.close();
                        stmLoc=null;
                        return false;
                    }
                } 
                objInvItm.limpiarObjeto();  

                objInvItm.inicializaObjeto();

                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
                        dlbCanMov=Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMOV)) );
                        if(dlbCanMov > 0){
                            intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM)); 
                            intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
                            strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                            strSql =" SELECT sum(abs(b.nd_can)) AS total FROM tbr_cabmovinv AS a "+ 
                                    " INNER JOIN tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
                                    " WHERE a.co_docrel="+intNumDocOrdCom+" and a.co_emp="+objParSis.getCodigoEmpresa()+" and "+
                                    " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+tblDat.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";
                            rstLoc=stmLoc.executeQuery(strSql);
                            if(rstLoc.next())
                                dblCanDev = rstLoc.getDouble("total");
                            rstLoc.close();
                            rstLoc=null;

                            dblCanDev=dblCanDev+dlbCanMov;

                            strSql =" INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_itmact, tx_codalt, tx_codalt2, " +
                                    " tx_nomItm, co_bod, tx_unimed, nd_can, nd_canorg,  nd_cosUni, nd_cosUniGrp, nd_preuni, nd_porDes, st_ivaCom "+
                                    " ,st_reg, nd_costot, nd_costotgrp, nd_tot, nd_candev, st_regrep ) "+
                                    " VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", " + 
                                    txtDoc.getText()+", "+(i+1)+", "+tblDat.getValueAt(i, INT_TBL_CODITM)+", "+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+
                                    tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"','"+
                                    tblDat.getValueAt(i, INT_TBL_DESITM)+"', "+tblDat.getValueAt(i, INT_TBL_CODBOD)+", '"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+
                                    ( getAccionDoc() * objUti.redondear(  objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CANMOV) ), 6) )+", "+
                                    ( getAccionDoc() *-1 * objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CANCOM) ) ,6 ) )+", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PORDES) ), 2 )+", '"+
                                    ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P', "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
                                    objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
                                    dblCanDev * getAccionDoc() +" , 'I' ) ; ";

                            stbins.append( strSql );
                            intSer=1;

                            if(objInvItm.isItmServicio(conn, intCodItm)){
                                objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgr, strTipIngEgr, 1); 
                                intReaActStk=1;
                            }
                        }
                    }
                }

                if(intSer > 0 )
                   stmLoc.executeUpdate( stbins.toString() );
                stbins=null;
                stmLoc.close();
                stmLoc=null;

                if(intReaActStk==1){
                    if(!objInvItm.ejecutaActStock(conn, intTipCli))
                      return false;

                    if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                      return false;
                } 
                objInvItm.limpiarObjeto();  

                blnRes=true;      
            }
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean anueliCab(java.sql.Connection conn, String strEst){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                strSql =" UPDATE tbm_cabMovInv SET st_reg='"+strEst+"', st_regrep='M', fe_ultMod="+objParSis.getFuncionFechaHoraBaseDatos()+", "+
                        " co_usrMod="+objParSis.getCodigoUsuario()+" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND "+
                        " co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText();  
                strSql+=" ; UPDATE tbr_cabMovInv SET st_reg='"+strEst+"', st_regrep='M' WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND "+
                        " co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText();  
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;

               blnRes=true;      
            }
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }   
      
    private boolean invertirDet(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intTipCli=3;
        int intCodItm=0;
        int intCodBod=0;
        int intReaActStk=0;
        double dlbCanMov=0;
        String strSql="";
        String strEstFisBod="";
        try{
          if(conn!=null){
           stmLoc=conn.createStatement();

           intTipCli=objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(), conn);

           objInvItm.inicializaObjeto();

           String strAux2=" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
           " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
           " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
           " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
           " THEN 'S' ELSE 'N' END AS proconf  ";

           strSql="SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can  "+strAux2+"  FROM tbm_detMovInv as a" +
           " INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND "+  
           " a.co_loc="+objParSis.getCodigoLocal()+" AND a.co_tipDoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtDoc.getText()+" AND inv.st_ser='N'"; 
           rstLoc=stmLoc.executeQuery(strSql); 
           while(rstLoc.next()){

            intCodItm=rstLoc.getInt("co_itm");
            intCodBod=rstLoc.getInt("co_bod");
            dlbCanMov=rstLoc.getDouble("nd_can");
            strEstFisBod=rstLoc.getString("proconf");

            objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, strMerIngEgr, strTipIngEgr, -1); 
            intReaActStk=1;
           }
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null;

          if(intReaActStk==1){
            if(!objInvItm.ejecutaActStock(conn, intTipCli))
               return false;
            if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
              return false;
          } 
          objInvItm.limpiarObjeto();  

          blnRes=true;      
        }}catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }   
        
    private boolean eliminarReg(){
        boolean blnRes=false;
        java.sql.Connection conn;
        try{
         conn=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(conn!=null){
           conn.setAutoCommit(false);

           if(anueliCab(conn, "E")){
            if(objAsiDia.eliminarDiario(conn,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
             if(!isAnulada()){
               if(invertirDet(conn)){
                  conn.commit();
                  blnRes=true; 
                }else conn.rollback();    
              }else{
                  conn.commit();
                  blnRes=true; 
              } 
            }else conn.rollback();    
           }else conn.rollback();  
          objInvItm.limpiarObjeto();   
          conn.close();
          conn=null;
        }}catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }           

    private boolean validarCanDev(){
        try{
            Connection conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
                    double dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                    if( dblCanMov > 0.00 ){
                        int intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                        String  sql = "select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                    " inner join tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
                                    " where a.co_docrel="+intNumDocOrdCom+" and a.co_emp="+objParSis.getCodigoEmpresa()+" and  a.co_loc="+objParSis.getCodigoLocal()+" and "+
                                    " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+intCodItm+" and a.co_tipdoc="+txtCodTipDoc.getText()+" and a.st_reg='A'";
                        //System.out.println(" >>>> "+ sql);

                        java.sql.Statement stmDocDet=conIns.createStatement();
                        java.sql.ResultSet  rstDocDet2 = stmDocDet.executeQuery(sql);
                        double intNumDev=0; 
                        if(rstDocDet2.next()){
                            intNumDev = rstDocDet2.getDouble(1); 
                        }
                        intNumDev=(intNumDev*-1)+dblCanMov;
                        double dblCanOri = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString());
                        // System.out.println(">>> "+ dblCanOri + " >> " +intNumDev);

                        if(intNumDev > dblCanOri ) {
                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                            String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="Este Item  "+ tblDat.getValueAt(i, INT_TBL_ITMALT) + "  Ya tiene devoluciones y la cantidad a devolver es mayor";
                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            return false;
                        }
                    }
                }
            }   
        }
        catch(SQLException Evt)
        {    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }  
        return true;
    }
            
    private boolean verificaItmUni(){
        boolean  blnRes=true;
        try{
        for(int i=0; i<tblDat.getRowCount();i++){
           if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                  int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                  int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                 // if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                      if(intCodItm != intCodItmAct){
                          mostrarMsgInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                          blnRes=false;
                          break;
                      }
                  //}
          }}
        }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
        return blnRes;     
    }

    private void recostearItmGrp()
    {
        int i,j;
        String strFecRan[][];

         try
         {    
          java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          if (con!=null)
          {  

          ///  System.out.println(">> "+ dtpFecDoc.getFecha("/","d/m/y") ); 

            strFecRan=objUti.getIntervalosMensualesRangoFechas(dtpFecDoc.getFecha("/","d/m/y"), dtpFecDoc.getFecha("/","d/m/y"), "dd/MM/yyyy");
              for(i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
                   double dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                   if( dblCanMov > 0.00 ){


                ////    System.out.println(">> "+i);
                     for (j=0; j<strFecRan.length; j++)
                         objUti.recostearItmGrp(this, objParSis, con,  objParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_CODITM).toString(), strFecRan[j][0], strFecRan[j][1], "yyyy/MM/dd");

                }}}
          }
          con.close();
          con=null;
         }
        catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    }
       
    private boolean insertarCab(java.sql.Connection conn, int intCodDoc, int intSecEmp, int intSecGrp  ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        String strconinv="F";
        String strEst="";
        String strFecha="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement(); 

                /*******************************************************************/
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                        strEst=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                        String strEstSer=(tblDat.getValueAt(i, INT_TBL_ITMSER)==null?"":tblDat.getValueAt(i, INT_TBL_ITMSER).toString());
                        if( (strEst.equals("N")) && (strEstSer.equals("N")) )
                            strconinv="P";
                    }
                }

                if(strconinv.equals("P"))
                if(strMerIngEgr.equals("N")) strconinv="F";

                int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_secemp, ne_secgrp, "+ //"  ne_secgrpant, ne_secempant,  "   +
                " co_cli, co_com, tx_ate, tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "+
                " ne_numDoc, ne_numCot, tx_obs1, tx_obs2, nd_sub,nd_tot,nd_valiva,nd_porIva, fe_ing, co_usrIng  "+ 
                " ,st_reg, st_regrep, st_coninv ) "+
                " VALUES ("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+ 
                intCodDoc+", '"+strFecha+"', "+intSecEmp+", "+intSecGrp+", "+ //(intSecGrp-1) + ", "+(intSecEmp-1) + " , " +
                txtCliCod.getText()+", "+((txtVenCod.getText().equals(""))?"9":txtVenCod.getText())+", '"+
                txtAte.getText()+"', '"+txtCliNom.getText()+"', '"+txtCliDir.getText()+"', '"+objCliente_dat.getIdentificacion()+"', '"+
                objCliente_dat.getTelefono()+"', '"+objCiudad_dat.getNombrelargo()+"', '"+txtVenNom.getText()+"', "+ 
                txtDev.getText()+", "+((txtFromFac.getText().equals(""))?"0":txtFromFac.getText())+", '"+
                txaObs1.getText()+"', '"+txaObs2.getText()+"', "+(objUti.redondeo(Double.parseDouble(txtSub.getText()),6) * getAccionDoc()) + ","+
                (objUti.redondeo(Double.parseDouble(txtTot.getText()),6) * getAccionDoc())+", "+
                (objUti.redondeo(Double.parseDouble(txtIva.getText()),6) * getAccionDoc())+","+
                objUti.redondeo(dblPorIva , 2)+", '"+strFecSis+"', "+objParSis.getCodigoUsuario()+",'A', 'I', '"+strconinv+"')";
                stmLoc.executeUpdate(strSql);

                strSql="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel, st_regrep)"+
                " VALUES("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
                txtCodTipDoc.getText()+","+intCodDoc+",'A',"+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
                vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+","+intNumDocOrdCom+",'I')";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;

                blnRes=true;
            }
        }
        catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
    }                      

    private boolean insertarDet(java.sql.Connection conn, int intCodDoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intTipCli=3;
        int intSer=0;
        int intCodItm=0;
        int intCodBod=0;
        int intReaActStk=0;
        double dlbCanMov=0;  
        double dblCanDev=0;
        String strSql="";
        String strEstFisBod="";
        try{
         if(conn!=null){
           stmLoc=conn.createStatement(); 

           StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER

           intTipCli=objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(),conn);

           objInvItm.inicializaObjeto();

           for(int i=0; i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){

            dlbCanMov=Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMOV)) );
            if(dlbCanMov > 0){

             intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM)); 
             intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));


             strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
             strSql="SELECT sum(abs(b.nd_can)) AS total FROM tbr_cabmovinv AS a "+ 
             " INNER JOIN tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
             " WHERE a.co_docrel="+intNumDocOrdCom+" and a.co_emp="+objParSis.getCodigoEmpresa()+" and "+
             " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+tblDat.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";
             rstLoc=stmLoc.executeQuery(strSql);
             if(rstLoc.next())
               dblCanDev = rstLoc.getDouble("total");
              rstLoc.close();
              rstLoc=null;

             dblCanDev=dblCanDev+dlbCanMov;

             strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_itmact, tx_codalt, tx_codalt2, " +
             " tx_nomItm, co_bod, tx_unimed, nd_can, nd_canorg,  nd_cosUni, nd_cosUniGrp, nd_preuni, nd_porDes, st_ivaCom "+
             " ,st_reg, nd_costot, nd_costotgrp, nd_tot, nd_candev, st_regrep ) "+
             " VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", " + 
             intCodDoc+", "+(i+1)+", "+tblDat.getValueAt(i, INT_TBL_CODITM)+", "+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+
             tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"','"+
             tblDat.getValueAt(i, INT_TBL_DESITM)+"', "+tblDat.getValueAt(i, INT_TBL_CODBOD)+", '"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+
             ( getAccionDoc() * objUti.redondear(  objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CANMOV) ), 6) )+", "+
             ( getAccionDoc() *-1 * objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CANCOM) ) ,6 ) )+", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PREVEN) ), 6 )+", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_PORDES) ), 2 )+", '"+
             ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P', "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
             objUti.redondear( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_TOTAL) ), 2 ) * getAccionDoc() +", "+
             dblCanDev * getAccionDoc() +" , 'I' ) ; ";

             stbins.append( strSql );
             intSer=1;

             if(objInvItm.isItmServicio(conn, intCodItm)){

               objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgr, strTipIngEgr, 1); 
               intReaActStk=1;
             }
           }}}

           if(intSer > 0 )
             stmLoc.executeUpdate( stbins.toString() );
           stbins=null;
           stmLoc.close();
           stmLoc=null;

           if(intReaActStk==1){
            if(!objInvItm.ejecutaActStock(conn, intTipCli))
              return false;

            if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
              return false;
           } 
          objInvItm.limpiarObjeto();  
          blnRes=true;
        }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
          catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
    }                      
        
    private boolean insertarPag(java.sql.Connection conn, int intCodDoc, int intCruAut ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dlbTot=0; 
        double dblBaseDePagos=0;
        String strSql="";
        String strFecha="";
        try{
         if(conn!=null){
           stmLoc=conn.createStatement(); 

          int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
          strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

          dlbTot=Double.parseDouble(txtTot.getText());  
          dblBaseDePagos=objUti.redondear((dlbTot),2);

          strSql="INSERT INTO tbm_pagmovinv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, fe_ven, mo_pag, st_regrep) " +
          " VALUES("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+", "+objTipDoc.getco_tipdoc()+", "+ 
          intCodDoc+", 1 , '"+strFecSis+"', "+objUti.redondear((dlbTot*getAccionDoc()),2)+", 'I')";
        // System.out.println("-->>> "+ strSql );
          stmLoc.executeUpdate(strSql);
         stmLoc.close();
         stmLoc=null;

          if(intCruAut==1){ 
            java.sql.Statement stm = conn.createStatement();
            double dblTotDev=0;

            strSql="SELECT  sum(abs(a1.nd_tot)) as totdev FROM tbr_cabmovinv as a " +
            " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
            " where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a.co_tipdoc="+objTipDoc.getco_tipdoc()+" "+
            " and a.co_doc not in ("+intCodDoc+") and  "+
            " a.co_emprel="+objParSis.getCodigoEmpresa()+" and a.co_locrel="+vecLoc.elementAt(local.getSelectedIndex()).toString()+" " +
            " and a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString()+" and a.co_docrel="+intNumDocOrdCom+" and a1.st_reg not in ('I','E') and a.st_reg ='A' ";
            rstLoc=stm.executeQuery(strSql);
            if(rstLoc.next()){
               dblTotDev=rstLoc.getDouble("totdev");
            }
            rstLoc.close();
            rstLoc=null;

            if(objUltDocPrint.isCruceComDev(conn, objParSis.getCodigoEmpresa(), vecLoc.elementAt(local.getSelectedIndex()).toString(),
             vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString(), intNumDocOrdCom, dblBaseDePagos ) ){
             if(!objUltDocPrint.isPagoDocRet( objParSis.getCodigoEmpresa(), Integer.parseInt(vecLoc.elementAt(local.getSelectedIndex()).toString()) ,  Integer.parseInt(vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString()) , intNumDocOrdCom  )){

               if( objUltDocPrint.vericaPag( conn, objParSis.getCodigoEmpresa(), vecLoc.elementAt(local.getSelectedIndex()).toString(),
                vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString(),  intNumDocOrdCom , dblBaseDePagos, dblTotDev ) ){
                if( objUltDocPrint.vericaPagCru( conn, objParSis.getCodigoEmpresa(), vecLoc.elementAt(local.getSelectedIndex()).toString(),
                 vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString(),  intNumDocOrdCom , dblBaseDePagos, dblTotDev ) ){
                      realizaCruceAut( conn, strFecha, intCodDoc, dblBaseDePagos, " and nd_porret = 0 " );
                 }
               }else realizaCruceAut( conn, strFecha, intCodDoc, dblBaseDePagos, "  " );


            }else{

               realizaCruceAut( conn, strFecha, intCodDoc, dblBaseDePagos, "  " );

            }}

          stm.close();
          stm=null;
        }
        blnRes=true;
        }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
          catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
    }                      
                                    
    private boolean realizaCruceAut(java.sql.Connection conn, String strFecha, int intCodDoc, double dblBaseDePagos, String strAux ){
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01;
        String strSql="";
        int intTipDocCru=91;
        int intCodDocCru=0;
        int intSecc=1;
        double dblValCru=0;
        int intCodEmp=0;
        int intCodLoc=0;
        int intCodTipDoc=0;
        int intCodDocOC=0;
        int intCodReg=0;
        try{
           if(conn!=null){
             stmLoc=conn.createStatement();
             stmLoc01=conn.createStatement();

             dblValCruAut=dblBaseDePagos;

          intCodDocCru = objUltDocPrint.getCodDocTbmCabPag(conn, intTipDocCru);
          strSql="INSERT INTO TBM_CABPAG(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_cli, tx_nomcli, nd_mondoc, st_reg, fe_ing , co_usring, st_regrep, st_imp)" +
          " VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", "+intTipDocCru+", "+intCodDocCru+" " +
          ", '"+strFecha+"', "+intCodDocCru+", "+txtCliCod.getText()+", '"+txtCliNom.getText()+"',"+dblBaseDePagos+",'A','"+ strFecSis +"',"+objParSis.getCodigoUsuario()+",'I','P')";
          stmLoc01.executeUpdate(strSql);

          strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
          " co_regpag, nd_abo, co_tipdoccon) VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", " +
          " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+objParSis.getCodigoLocal()+","+objTipDoc.getco_tipdoc()+","+intCodDoc+" "+
          " ,1, "+dblBaseDePagos+","+objTipDoc.getco_tipdoc()+")";
          stmLoc01.executeUpdate(strSql);

          strSql="SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, abs((abs(mo_pag)-abs(nd_abo))) as mopag FROM tbm_pagmovinv where co_Emp="+objParSis.getCodigoEmpresa()+" " +
          " and co_loc="+vecLoc.elementAt(local.getSelectedIndex())+" and co_tipdoc="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" " +
          " and co_doc="+intNumDocOrdCom+" and st_reg in ('C','A') "+strAux+"   order by co_reg ";
          java.sql.ResultSet rst = stmLoc.executeQuery(strSql);
          while(rst.next()){
             if(dblBaseDePagos > 0){
              if( rst.getDouble("mopag") < dblBaseDePagos ){
                  dblValCru=rst.getDouble("mopag");
                  dblBaseDePagos=dblBaseDePagos-dblValCru;
              }else{
                  dblValCru=dblBaseDePagos;
                  dblBaseDePagos=dblBaseDePagos-dblValCru;
              }

                intCodEmp=rst.getInt("co_emp");
                intCodLoc=rst.getInt("co_loc");
                intCodTipDoc=rst.getInt("co_tipdoc");
                intCodDocOC=rst.getInt("co_doc");
                intCodReg=rst.getInt("co_reg");

              if(dblValCru!=0){
                 intSecc++;
                 strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                 " co_regpag, nd_abo, co_tipdoccon) VALUES("+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+", " +
                 " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rst.getString("co_loc")+","+rst.getString("co_tipdoc")+","+rst.getString("co_doc")+" "+
                 " ,"+rst.getString("co_reg")+",abs("+dblValCru+")*-1,"+rst.getString("co_tipdoc")+")";

                 strSql +=" ; UPDATE tbm_pagmovinv SET nd_abo= (abs(nd_abo)+abs("+dblValCru+"))*-1 where " +
                 "  co_emp="+rst.getString("co_emp")+" and co_loc="+rst.getString("co_loc")+" and  co_tipdoc="+rst.getString("co_tipdoc")+" and co_doc="+rst.getString("co_doc")+" and co_reg="+rst.getString("co_reg");
                 stmLoc01.executeUpdate(strSql);

                 //agregarCambioCuentasContables(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDocOC, java.lang.Math.abs(dblValCru) );
              }
            }
          }
         rst.close();
         rst=null;

         if( dblBaseDePagos != 0 ){
             strSql =" UPDATE tbm_pagmovinv SET nd_abo= (abs(nd_abo)+abs("+dblBaseDePagos+"))*-1 where " +
             "  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and  co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDocOC+" and co_reg="+intCodReg;
             stmLoc01.executeUpdate(strSql);


            // agregarCambioCuentasContables(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDocOC, java.lang.Math.abs(dblBaseDePagos) );
         }


         strSql="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag) WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
         " AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipdoc="+objTipDoc.getco_tipdoc()+" AND co_doc="+intCodDoc+" AND co_reg=1";
         stmLoc01.executeUpdate(strSql);

         intEstCruAut=1;

         stmLoc.close();
         stmLoc=null;
         stmLoc01.close();
         stmLoc01=null;

         blnRes=true;
       }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
         catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
       return blnRes;
    }      

    private boolean insertarReg(){
        boolean blnRes=false;
        java.sql.Connection conIns;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql;
        int intRegSel=1; // realiza cruce automatico sin importar valor.
        int intCodDoc=1;
        int intSecEmp=0, intSecGrp=0;
        try{
           conIns=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           if(conIns!=null){

              intSecEmp = objUltDocPrint.getNumSecDoc( conIns, objParSis.getCodigoEmpresa() );
              intSecGrp = objUltDocPrint.getNumSecDoc( conIns, objParSis.getCodigoEmpresaGrupo() );

              conIns.setAutoCommit(false); 

              stmLoc=conIns.createStatement();


               strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE "+
               " co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText();
               rstLoc= stmLoc.executeQuery(strSql);
               if(rstLoc.next())
                   intCodDoc=rstLoc.getInt("co_doc");
               rstLoc.close();
               rstLoc=null;

               intEstCruAut=0;
               dblValCruAut=0;

               Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans( jfrThis, objParSis);

              if(!objUltDocPrint.getExisteDoc( conIns, txtCodTipDoc.getText(), txtDev.getText() )){
               if(insertarCab(conIns, intCodDoc, intSecEmp, intSecGrp )){
                if(insertarDet(conIns, intCodDoc )){     
                 if(insertarPag(conIns, intCodDoc, intRegSel )){    
                  if(objAsiDia.insertarDiario(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )){   

                     if( objCtaTrans.cambioCtaConIvaDevCom(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc
                          , objParSis.getCodigoEmpresa(), Integer.parseInt(vecLoc.elementAt(local.getSelectedIndex()).toString()), Integer.parseInt(vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString()), intNumDocOrdCom ) ){
                      if( objCtaTrans.cambioCtaConProDevCom(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc
                           , objParSis.getCodigoEmpresa(), Integer.parseInt(vecLoc.elementAt(local.getSelectedIndex()).toString()), Integer.parseInt(vecTipDoc.elementAt(cboTipDoc.getSelectedIndex()).toString()), intNumDocOrdCom ) ){

           //         agregarCambioCuentasContablesDevCom(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),
           //         Integer.parseInt(txtCodTipDoc.getText()), intCodDoc, java.lang.Math.abs(dblValCruAut) );



                     conIns.commit();
                     STR_ESTREG="I"; 
                     consultar2(); 
                     blnRes=true;

                    }else conIns.rollback();
                   }else conIns.rollback();
                 }else conIns.rollback();         
                }else conIns.rollback();     
               }else conIns.rollback(); 
              }else conIns.rollback();   

             }else{
                   mostrarMsgInf("No. de Devolucion ya Existe");
                   txtDev.requestFocus();
                   txtDev.selectAll();
                }
             objInvItm.limpiarObjeto();           
             conIns.close();
             conIns=null;

             objCtaTrans=null;

        }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
          catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
}     
 
    private boolean actualizarCab(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        String strFecha=""; 
        try{
           if(conn!=null){
             stmLoc=conn.createStatement();

           int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
           strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

           strSql="UPDATE tbm_cabMovInv SET fe_doc='"+strFecha+"', co_cli="+txtCliCod.getText()+", ne_numCot="+txtFromFac.getText()+", "+ 
           " ne_numDoc="+txtDev.getText()+", tx_nomCli='" + txtCliNom.getText()+"', tx_dirCli='"+txtCliDir.getText()+"', "+
           " tx_ruc='"+objCliente_dat.getIdentificacion()+"', tx_telCli='"+objCliente_dat.getTelefono()+"', "+
           " tx_ciuCli='"+objCiudad_dat.getNombrelargo()+"', co_com="+((txtVenCod.getText().equals(""))?"9":txtVenCod.getText())+", "+
           " tx_nomven='"+txtVenNom.getText()+"', tx_ate='"+txtAte.getText()+"', tx_obs1='"+txaObs1.getText()+"', "+ 
           " tx_obs2 = '"+txaObs2.getText()+"', nd_sub="+(objUti.redondeo(Double.parseDouble(txtSub.getText()),6)* -1)+", "+
           " nd_valiva ="+(objUti.redondeo(Double.parseDouble(txtIva.getText()),6)* -1)+", "+
           " nd_tot  = "+(objUti.redondeo(Double.parseDouble(txtTot.getText()),6)* -1)+", "+
           " nd_porIva ="+dblPorIva+", fe_ultMod='"+strFecSis+"', co_usrMod="+objParSis.getCodigoUsuario()+" ,st_regrep='M' "+
           " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND "+  
           " co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText();
           stmLoc.executeUpdate(strSql);       
           stmLoc.close();
           stmLoc=null;

           blnRes=true;      
        }}catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean anularReg(){
        boolean blnRes=false;
        java.sql.Connection conn;
        try{
         conn=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(conn!=null){
           conn.setAutoCommit(false);

           if(anueliCab(conn, "I")){
            if(objAsiDia.anularDiario(conn,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
              if(invertirDet(conn)){
                  conn.commit();
                  blnRes=true; 
             }else conn.rollback();    
            }else conn.rollback();    
           }else conn.rollback();  
          objInvItm.limpiarObjeto();    
          conn.close();
          conn=null;
        }}catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }       

    private boolean consultar2() {
        strFiltro = FilSql2();
        return _consultar(strFiltro);
    }            
    
    private String FilSql() {
        String sqlFiltro = "";
        //Agregando filtro por codigo de Tipo de Documento
        if(!txtCodTipDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc = " + txtCodTipDoc.getText()+ "";                   

        //Agregando filtro por Numero de Documento DEVOLUCION
        if(!txtDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_doc = " + txtDoc.getText()+ "";


        //Agregando filtro por Numero de Documento DEVOLUCION
        if(!txtDev.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc = " + txtDev.getText()+ "";


        //Agregando filtro por Fecha
        if(dtpFecDoc.isFecha()){
            int FecSql[] = dtpFecDoc.getFecha(dtpFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
        }    

      return sqlFiltro ;
    }


    private String FilSql2() {
        String sqlFiltro = "";
        //Agregando filtro por codigo de Tipo de Documento
        if(!txtCodTipDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc = " + txtCodTipDoc.getText()+ "";                   

        //Agregando filtro por Numero de Documento DEVOLUCION
        if(!txtDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_doc = " + txtDoc.getText()+ "";


        //Agregando filtro por Numero de Documento DEVOLUCION
        if(!txtDev.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc = " + txtDev.getText()+ "";


        //Agregando filtro por Fecha
        if(dtpFecDoc.isFecha()){
            int FecSql[] = dtpFecDoc.getFecha(dtpFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
        }    

      return sqlFiltro ;
    }    
    
}
