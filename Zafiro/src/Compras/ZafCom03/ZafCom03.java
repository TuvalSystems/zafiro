    /*
 * ZafCom03.java     
 *             
 * Created on 6 de septiembre de 2004, 15:54  
 */    
package Compras.ZafCom03;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList; 
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;   
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_Ing_Mot;
import Librerias.ZafUtil.ZafTipDoc;
/*   
 * @author : jayapata      
 *    
 *    
 */
public class ZafCom03 extends javax.swing.JInternalFrame
{
    //Constantes: JTable TblDat
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_CANORI   = 7 ;
    final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PRECOS   = 9 ;           //Precio de Venta
    final int INT_TBL_PORDES   = 10;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 11;           //Boolean Iva
    final int INT_TBL_TOTAL    = 12;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 13;
    final int INT_TBL_ITMORI   = 14;    //Codigo del item de una o/c cargada
    final int INT_TBL_BODORI   = 15;    //Codigo del bodega de una o/c cargada
    final int INT_TBL_ESTADO   = 16;
    final int INT_TBL_IVATXT   = 17;
    final int INT_TBL_ITMSER   = 18;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_CODITMACT= 19;     //Codigo del item actual    
    final int INT_TBL_IEBODFIS = 20;  // estado que dice si ingresa/egresa fisicamente en bodega
  
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    final int INT_ARR_IEBODFIS = 6;
    
    //Variables
    private Connection conCab;  //Coneccion a la base donde se encuentra la Documento
    private Statement stmCab;   //Statement para la Documento 
    private ResultSet rstCab;   //Resultset que tendra los datos de la cabecera de la Documento    
    private ZafParSis objParSis;
    private ZafAsiDia objAsiDia;
    private ZafTipDoc objTipDoc;
    private ZafUtil objUti;
    private MiToolBar objTooBar;    
    private ZafCtaCtb_Ing_Mot objCtaCtb;   //PARA OBTENER  LAS CUENTAS   
    private UltDocPrint objUltDocPrint;    //Para trabajar con la informacion de tipo de documento
    private ZafTblCelEdiTxtVco  objTblCelEdiTxtVcoBod;
    private Librerias.ZafInventario.ZafInvItm objInvItm;
    private ZafDocLis objDocLis;    // Instancia de clase que detecta cambios
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafVenCon vcoItm; 
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoPrv; 
    private ZafVenCon vcoBod;   
    private ZafDatePicker dtpFecDoc;
   
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    
    int intCodMnu;         //Obtiene el codigo del menu
    double dblPorIva ;     //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblSubtotalCot, dblTotalDoc, dblIvaCot;
    int intNumDec, intNumDoc= 0, intNumOrdCom=0, op=1 ; //Numero de decimales a presentar
    int intCodBodPre=0;
      
    private Vector vecDatDia; //vector que genera y actuliza el objAsiDia (ZafAsiDia)   
      
    final ArrayList arreBod=new ArrayList();
    
    String strTipEmp;    
    String STR_ESTREG="";      
    String GLO_strArreItm="";
    String strMerIngEgr="", strTipIngEgr="";
    String strCodEmpOC="", strCodLocOC="", strCodTipOC="", strCodDocOC="";
    String sSQL, strFiltro,strAux;//EL filtro de la Consulta actual
    
    boolean blnEstCarOC=false;

    String strFecResDoc=""; 
    
    final String strVer = " v4.6";

    /** Creates new form ZafCom03 */
    public ZafCom03(ZafParSis obj) {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;

            initComponents();
            objUti         = new ZafUtil();
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);        
            this.setTitle(objParSis.getNombreMenu()+strVer);
            intCodMnu = objParSis.getCodigoMenu();
            intNumDec = objParSis.getDecimalesMostrar();        
            dtpFecDoc = new ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText(""); 
            dtpFecDoc.setBounds(571, 6, 95, 20);
            panGenCab.add(dtpFecDoc);
            objAsiDia = new ZafAsiDia(objParSis);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            //***********************************************************************
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
               public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                  if (txtCodTipDoc.getText().equals(""))
                      objAsiDia.setCodigoTipoDocumento(-1);
                  else
                      objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
             });
            //***********************************************************************

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            objDocLis=new ZafDocLis();
            tabFrm.addTab("Diario",objAsiDia);    
            //objAsiDia.setEditable(true);

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis,1);

            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objParSis);
            txtCodTipDoc.setVisible(false);
            
            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.setTitleAt(1,"Asiento de Diario");
            objTooBar = new MiToolBar(this);

            getIva();
            this.getContentPane().add(objTooBar,"South");

            pack();

            cargarTipEmp();
            getObtenerBodPre();

            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            /* Metodo para agregar o eliminar lineas con enter y con escape
             */
            agregarDocLis();
            objUti.desactivarCom(this);

            cargarBodegas();

            // this.setBounds(10,10, 700,450);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }         
    } 

    /** Creates new form ZafCom03 */
    public ZafCom03(ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc ) {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;

            initComponents();
            objUti         = new ZafUtil();
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            this.setTitle(objParSis.getNombreMenu()+strVer);

            strCodEmpOC= String.valueOf( intCodEmp.intValue() );
            strCodLocOC= String.valueOf( intCodLoc.intValue() );;
            strCodTipOC= String.valueOf( intCodTipDoc.intValue() );;
            strCodDocOC= String.valueOf( intCodDoc.intValue() );;
            blnEstCarOC=true;

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            intCodMnu = objParSis.getCodigoMenu();
            intNumDec = objParSis.getDecimalesMostrar();
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            dtpFecDoc.setBounds(570, 6, 92, 20);
            panGenCab.add(dtpFecDoc);
            
            objInvItm = new ZafInvItm(this, objParSis); 

            //***********************************************************************
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            //***********************************************************************

            objDocLis=new ZafDocLis();

            tabFrm.addTab("Diario",objAsiDia);
            //objAsiDia.setEditable(true);

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis,1);

            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objParSis);
            txtCodTipDoc.setVisible(false);
            //Nombres de los tabs
            tabFrm.setTitleAt(0,"General");
            tabFrm.setTitleAt(1,"Asiento de Diario");
            objTooBar = new MiToolBar(this);

            getIva();
            //this.getContentPane().add(objTooBar,"South");

            pack();

            cargarTipEmp();

            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            /* Metodo para agregar o eliminar lineas con enter y con escape
             */
            agregarDocLis();
            objUti.desactivarCom(this);

            cargarBodegas();

            // this.setBounds(10,10, 700,450);
        }
        catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void cargarBodegas(){
        java.sql.Connection conTmp ;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try
        {
            conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
               stmTmp = conTmp.createStatement();
               String sql = "SELECT distinct(co_bod) FROM tbr_bodLoc WHERE co_emp="+objParSis.getCodigoEmpresa();  
               rst=stmTmp.executeQuery(sql);
               while(rst.next()){
               arreBod.add(""+rst.getInt(1));    
               }

               rst.close();
               stmTmp.close();
               conTmp.close();
               rst=null;
               stmTmp=null;
               conTmp=null;

            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    }
   
    private void configurarVentanaConsulta(){
        configurarVenConItm();
        configurarVenConTipDoc();
        configurarVenConPrv();
        configurarVenConBod();
        configurarTblDat();

        if(blnEstCarOC){
           cargarDatos( strCodEmpOC, strCodLocOC, strCodTipOC, strCodDocOC  );
         }
    }
   
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        String Str_Sql="";
        try
        {
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
            arlCam.add("a7.proconf");
            
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
            arlAli.add("IE.FisBod."); 
            
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
            arlAncCol.add("20");
                
            //Armar la sentencia SQL.   a7.nd_stkTot,
            Str_Sql = objInvItm.getSqlInvIngEgr();
            Str_Sql = "SELECT * FROM ( "+Str_Sql+" ) AS a7 WHERE a7.st_ser='N' ";
                       
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
              
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            
        
        }
        catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e);  } 
        return blnRes;
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
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                          " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                          " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                          " b.co_mnu = " + objParSis.getCodigoMenu();
             }else {     
                    Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objParSis.getCodigoUsuario();
             }    
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
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
             String  strSQL="";
           //  strSQL+="select co_cli,tx_nom,tx_dir,tx_tel,tx_ide from tbm_cli as a where a.co_emp = " + objParSis.getCodigoEmpresa() +" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom"; 
            strSQL="SELECT  a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide FROM tbr_cliloc as a1 " +
            " INNER JOIN tbm_cli AS a on (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
            " WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" and a1.co_loc="+objParSis.getCodigoLocal()+" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom";
             
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
                  
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConBod() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";

             if(objParSis.getCodigoUsuario()==1){
              Str_Sql="select a.co_bod, a.tx_nom from tbm_bod as a  where a.co_emp="+objParSis.getCodigoEmpresa()+" order by co_bod ";
             }else {
                    Str_Sql ="SELECT * FROM (    select a.co_bod, a1.tx_nom  from tbr_bodtipdocprgusr as a " +
                    " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
                    " where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
                    " and a.co_usr="+objParSis.getCodigoUsuario()+" ) AS a   ";
             }

            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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
            vecCab.add(INT_TBL_CANORI,"");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_PRECOS,"Costo");            
            vecCab.add(INT_TBL_PORDES,"Descuento");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ITMORI,"");
            vecCab.add(INT_TBL_BODORI,"");            
            vecCab.add(INT_TBL_ESTADO,"Estado");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_ITMSER,"Ser.");
            vecCab.add(INT_TBL_CODITMACT, "");
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
            objTblMod.setColumnDataType(INT_TBL_CANORI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PRECOS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMORI);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            arlAux.add("" + INT_TBL_ITMALT);   // INT_TBL_ITMORI 
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
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_PRECOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(80);         
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
            
            tcmAux.getColumn(INT_TBL_ITMSER).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CODITMACT).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setPreferredWidth(0);
             
            
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
                                
              
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PRECOS);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_BLNIVA);
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
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                  
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
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

                     calculaTotal();
                     generaAsiento();                            
                     blnChangeData = true;
                }
            });


            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL="";            
         
             
        int intColFac[]=new int[1];
        intColFac[0]=1;

        int intColTblFac[]=new int[1];
        intColTblFac[0]=INT_TBL_CODBOD;

        objTblCelEdiTxtVcoBod=new ZafTblCelEdiTxtVco(tblDat, vcoBod, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtVcoBod);
        objTblCelEdiTxtVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
               int intNumFil = tblDat.getSelectedRow();

               if( (tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null))
                 objTblCelEdiTxtVcoBod.setCancelarEdicion(true);

                if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null)
                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);


            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                vcoBod.setCampoBusqueda(9);
                vcoBod.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

                 if( tblDat.getValueAt( intNumFil, INT_TBL_CODITM)==null){
                     objTblCelEdiTxtVcoBod.setCancelarEdicion(true);
                 }


                   generaAsiento();

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

            }
        });

        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButBod(tblDat, INT_TBL_BUTBOD);

            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            strSQL="";
  
            int intColVen[]=new int[9];
            intColVen[0]=1;
            intColVen[1]=3;
            intColVen[2]=5;
            intColVen[3]=2;            
            intColVen[4]=6;          
            intColVen[5]=7; 
            intColVen[6]=9;
            intColVen[7]=2;
            intColVen[8]=10;
            int intColTbl[]=new int[9];
            intColTbl[0]=INT_TBL_ITMALT;
            intColTbl[1]=INT_TBL_DESITM;
            intColTbl[2]=INT_TBL_PRECOS;
            intColTbl[3]=INT_TBL_CODITM;
            intColTbl[4]=INT_TBL_IVATXT;
            intColTbl[5]=INT_TBL_UNIDAD;
            intColTbl[6]=INT_TBL_ITMSER;
            intColTbl[7]=INT_TBL_CODITMACT;
            intColTbl[8]=INT_TBL_IEBODFIS;
            
                
            
           ////**********objTblCelEdiTxtCon=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon(tblDat, intColVen, intColTbl,vcoItm);
          
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            
            // objTblCelEdiTxtCon.setIndiceCampoBusqueda(0);
          //  objTblCelEdiTxtCon.setCampoBusqueda("tx_codAlt", objTblCelEdiTxtCon.INT_CAM_STR);
          //  objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);

          ///************  tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtCon);
          
           tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);
             
          ///********* objTblCelEdiTxtCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
           objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                     
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    System.out.println("beforeEdit:"+blnChangeData);
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
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
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT).equals("S"))
                            tblDat.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(new Boolean(false), tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;
                        if(objParSis.getCodigoUsuario()==1)
                          tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), tblDat.getSelectedRow(), INT_TBL_CODBOD);
                        else
                          tblDat.setValueAt(new Integer(intCodBodPre), tblDat.getSelectedRow(), INT_TBL_CODBOD);


                        calculaSubtotal();
                    }                        
                }
            });

            
     
            
               ButFndPrv ObjFndPrv = new ButFndPrv(tblDat, INT_TBL_BUTITM);   //*****
            

           
            
            
               
            
            intColVen=null;
            intColTbl=null;
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    System.out.println("beforeEdit:"+blnChangeData);
                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="";
//                    System.out.println("afterEdit:"+strAfterValue);
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
                           
//                    System.out.println("txt Fila:"+tblDat.getSelectedRow()+" Estado: "+tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString());
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
//                    System.out.println("before: "  + strBeforeValue + " - after: " + strAfterValue);
                    if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                         if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            mostrarMsgInf("Error! Porcentaje de Descuento ");
                            tblDat.setValueAt(strBeforeValue, tblDat.getSelectedRow(), INT_TBL_PORDES);
                        }
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
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
            //Configurar JTable: Centrar columnas.
              new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
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
                case INT_TBL_PRECOS:
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
//                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
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

    private class ButBod extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButBod(javax.swing.JTable tbl, int intIdx){
             super(tbl,intIdx);
        }
        public void butCLick() {
            int intNumFil = tblDat.getSelectedRow();
            if(intNumFil >= 0 ) {
                String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_CODITM).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString()));

                if(!strEstApl.equals("N")){
                  listaBodega(intNumFil);
                }
            }
        }
    }

    private void listaBodega(int intNumFil){
        vcoBod.setTitle("Listado de Bodega");
        vcoBod.show();
        if (vcoBod.getSelectedButton()==vcoBod.INT_BUT_ACE) {
            tblDat.setValueAt(vcoBod.getValueAt(1),intNumFil,INT_TBL_CODBOD);

            generaAsiento();

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
            //eventoVenConCli(intNumFil);
        }
    }

    private void listaProductos(String strDesBusqueda){
        vcoItm.setTitle("Listado de Item"); 
         int intNumFil=tblDat.getSelectedRow();

        vcoItm.show();
        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
        {
           tblDat.setValueAt(vcoItm.getValueAt(1),intNumFil,INT_TBL_ITMALT);
           tblDat.setValueAt(vcoItm.getValueAt(2),intNumFil,INT_TBL_CODITM);
           tblDat.setValueAt(vcoItm.getValueAt(3),intNumFil,INT_TBL_DESITM);
           tblDat.setValueAt(vcoItm.getValueAt(7),intNumFil,INT_TBL_UNIDAD);
           tblDat.setValueAt(vcoItm.getValueAt(5),intNumFil,INT_TBL_PRECOS);
           tblDat.setValueAt(vcoItm.getValueAt(6),intNumFil,INT_TBL_IVATXT); 
           tblDat.setValueAt(vcoItm.getValueAt(9),intNumFil,INT_TBL_ITMSER);
           tblDat.setValueAt(vcoItm.getValueAt(2),intNumFil,INT_TBL_CODITMACT); 

           tblDat.setValueAt(vcoItm.getValueAt(10),intNumFil,INT_TBL_IEBODFIS); 


         if(objParSis.getCodigoUsuario()==1)
           tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), intNumFil, INT_TBL_CODBOD);
         else tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);


            if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
               tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV); 
            if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
               tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES); 
            if(tblDat.getValueAt(intNumFil,INT_TBL_TOTAL)==null)
               tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_TOTAL); 

                 blnChangeData = false;                            
                 if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                     strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                 else 
                     strBeforeValue = "";
                 if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                     tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                 }


                 if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                     strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                 else
                     strAfterValue ="";
                 if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                         tblDat.setValueAt("D",intNumFil , INT_TBL_ESTADO);                            
                 }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                     if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                         tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);                            
                 }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                     if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                         tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);                            
                 }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                     tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);                            
                 }

                 if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                     if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S"))
                         tblDat.setValueAt(new Boolean(true), intNumFil, INT_TBL_BLNIVA);
                     else
                         tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);
                 }

                calculaSubtotal();
           }
    }
    
    private class ButFndPrv extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndPrv(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        public void butCLick() {
             listaProductos("");
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_CANMOV, false, false);
        }
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
              cargarCabTipoDoc(Integer.parseInt(vcoTipDoc.getValueAt(1)));            
              if (objTooBar.getEstado()=='n') cargarNumDocOrdCom();
              generaAsiento();
           }
        }      
    }
    
    private void setEditable(boolean editable)
    {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
   }
   
    private void cargaTipoDocPredeterminado()
    {
        objTipDoc.DocumentoPredeterminado_usr();
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar());
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();  
        strTipIngEgr=objTipDoc.gettx_natdoc(); 
        
        getIva();
    }
    
    private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar()); 
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();  
        strTipIngEgr=objTipDoc.gettx_natdoc(); 
        
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), objTipDoc.gettx_natdoc() );            
        
        getIva();
    }
      
    private void cargarTipEmp(){
        Statement stmTipEmp;
        ResultSet rstTipEmp;
        String sSql;
        try{
            Connection conTipEmp=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTipEmp!=null){
                stmTipEmp=conTipEmp.createStatement();                    
                sSql="Select tx_tipper from tbm_emp where co_emp = " + objParSis.getCodigoEmpresa();
                rstTipEmp = stmTipEmp.executeQuery(sSql);
                if(rstTipEmp.next())
                    strTipEmp=rstTipEmp.getString("tx_tipper");
                rstTipEmp.close();
                stmTipEmp.close();
                conTipEmp.close();
            }
        }
        catch(SQLException Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                        
    }

    private void getObtenerBodPre(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql="";
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();

                strSql =" SELECT a.co_bod "+
                       " FROM (  select a.co_bod, a1.tx_nom from tbr_bodtipdocprgusr as a " +
                       "         inner join tbm_bod AS a1 ON (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
                       "         where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+
                       "         and a.co_mnu="+objParSis.getCodigoMenu()+" " +
                       "         and a.co_usr="+objParSis.getCodigoUsuario()+" and a.st_reg='P' "+
                       " ) AS a   ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodBodPre=rstLoc.getInt("co_bod");
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
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
    private void agregarDocLis(){        
        //Pie de pagina
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
        txtSub.getDocument().addDocumentListener(objDocLis);
        txtIva.getDocument().addDocumentListener(objDocLis);
        txtTot.getDocument().addDocumentListener(objDocLis);
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
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtNomPrv = new javax.swing.JTextField();
        txtCodPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblDir = new javax.swing.JLabel();
        txtDirPrv = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
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
        lblTit.setText("Título");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 110));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(5, 8, 100, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(100, 8, 10, 20);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(110, 8, 92, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(202, 8, 238, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(440, 8, 20, 20);

        lblCodDoc.setText("Cod. Documento:");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(5, 28, 102, 20);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema()
        );
        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(110, 28, 92, 20);

        lblNumDoc.setText("Núm. Documento:");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(5, 48, 105, 20);

        txtNumDoc.setBackground(objParSis.getColorCamposSistema()
        );
        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(110, 48, 92, 20);

        lblPrv.setText("Proveedor:");
        panGenCab.add(lblPrv);
        lblPrv.setBounds(5, 68, 100, 20);

        txtNomPrv.setBackground(objParSis.getColorCamposObligatorios());
        txtNomPrv.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrvActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomPrv);
        txtNomPrv.setBounds(170, 68, 270, 20);

        txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
        txtCodPrv.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodPrv.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(110, 68, 60, 20);

        butPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butPrv.setText("...");
        butPrv.setPreferredSize(new java.awt.Dimension(20, 20));
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenCab.add(butPrv);
        butPrv.setBounds(440, 68, 20, 20);

        lblDir.setText("Dirección:");
        panGenCab.add(lblDir);
        lblDir.setBounds(5, 88, 100, 20);

        txtDirPrv.setBackground(objParSis.getColorCamposSistema());
        txtDirPrv.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtDirPrv);
        txtDirPrv.setBounds(110, 88, 350, 20);

        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(465, 8, 108, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.PAGE_START);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Codigo", "...", "Descripción", "Unidad", "Bodega", "...", "Cantidad", "Costo", "%Desc", "Iva", "Total", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, false, false, false, false
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

        panGenSur.setPreferredSize(new java.awt.Dimension(430, 75));
        panGenSur.setLayout(new java.awt.BorderLayout());

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

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panTot.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panTot.add(txtTot);

        panGenSur.add(panTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        buscarProveedor("a.co_cli",txtCodPrv.getText(),0);
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void txtNomPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvActionPerformed
        buscarProveedor("a.tx_nom",txtNomPrv.getText(),1);
    }//GEN-LAST:event_txtNomPrvActionPerformed

    
     public void buscarProveedor(String campo,String strBusqueda,int tipo){
        vcoPrv.setTitle("Listado de Clientes"); 
        if (vcoPrv.buscar(campo, strBusqueda ))
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
           // FndVenToCli();
        }
        else
        {     
            vcoPrv.setCampoBusqueda(tipo);
            vcoPrv.cargarDatos();
            vcoPrv.show();
            if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
            {
                txtCodPrv.setText(vcoPrv.getValueAt(1));
                txtNomPrv.setText(vcoPrv.getValueAt(2));
                txtDirPrv.setText(vcoPrv.getValueAt(3));
               // FndVenToCli();
             }
        }
   }
  
    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        vcoPrv.setTitle("Listado de Clientes");         
        vcoPrv.setCampoBusqueda(1);
        vcoPrv.show();
        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
        {
            txtCodPrv.setText(vcoPrv.getValueAt(1));
            txtNomPrv.setText(vcoPrv.getValueAt(2));
            txtDirPrv.setText(vcoPrv.getValueAt(3));
            //FndVenToCli();
         } 
    }//GEN-LAST:event_butPrvActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarVentanaConsulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        listadoTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        listadoTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
         listadoTipDoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed

    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost

    }//GEN-LAST:event_txtNumDocFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated
 
    private void noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCodTipDoc.getBackground();

        txtCodDoc.setEditable(blnEditable);            
        txtSub.setEnabled(blnEditable);
        txtSub.setBackground(colBack);                    
        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);                    
        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);        
    }
  
    private void limpiarFrm()
    {
        //Cabecera
        txtCodDoc.setText("");
        dtpFecDoc.setText("");
        txtNumDoc.setText("");
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodPrv.setText("");
        txtNomPrv.setText("");
        txtDirPrv.setText("");
        //Detalle
        objTblMod.removeAllRows();          
        //Diario
        objAsiDia.inicializar();
        //Pie de pagina
        txaObs1.setText("");
        txaObs2.setText("");
        txtSub.setText("0");
        txtIva.setText("0");
        txtTot.setText("0");
        lblTit.setText(""+objParSis.getNombreMenu()+"");
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
    
    private void calculaSubtotal(){
        try{
            double dblCan,dblDes,dblCosto;
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_CANMOV);
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PRECOS);
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PORDES);
            dblCan = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV).toString());
            dblCosto = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS).toString());
            if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES).toString())/100);
            else dblDes = 0;
            tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objParSis.getDecimalesMostrar())),tblDat.getSelectedRow(), INT_TBL_TOTAL);
            calculaTotal();
            generaAsiento();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(jfrThis, e );
        }
    }
    
    private void  calculaTotal(){
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0;
        try{
            for (int i=0;i<tblDat.getRowCount();i++){ 
                dblSub += objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar());  

                if(tblDat.getValueAt(i, INT_TBL_BLNIVA)!=null){
                    dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar()): 0 );
                    dblIva += objUti.redondear((((dblTmp * dblPorIva)==0)?0:dblTmp * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                }
            }
            txtSub.setText( "" + objUti.redondear(dblSub,intNumDec) );
            txtIva.setText( "" + objUti.redondear(dblIva,intNumDec) );
            txtTot.setText( "" + objUti.redondear(dblSub + dblIva ,intNumDec)   );
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(jfrThis, e );
        }
    }
    
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */
    private void generaAsiento(){
        objAsiDia.inicializar();
        double dblSubtotal;
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO    = 8;

        if (vecDatDia==null)
            vecDatDia = new java.util.Vector();
        else 
            vecDatDia.removeAllElements();
          
        Vector vecReg; 
        int intCodBodT,intCodBod;
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
            for (int j=0;j<arreBod.size();j++)
            {
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                    {
                        if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                        {
                            intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                            if(intCodBod==intCodBodT)
                                dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                        }
                    }
                }
                
                if(dblSubtotal > 0 ){
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaExistencia( intCodBod ) );
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                    vecReg.add(INT_VEC_BOTON, null);                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                    vecReg.add(INT_VEC_DEBE, new Double(dblSubtotal));  
                    vecReg.add(INT_VEC_HABER, new Double(0));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDatDia.add(vecReg);
                }
            }  
             
            if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaIvaCompras() );
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));
                vecReg.add(INT_VEC_HABER, new Double(0)); 
                vecReg.add(INT_VEC_REF, null);
                vecReg.add(INT_VEC_NUEVO, null);
                vecDatDia.add(vecReg);                       
            }       
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaCompra() );
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar())));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDatDia.add(vecReg);  
        }
        else{  
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaCompra() );
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDatDia.add(vecReg);  
            
            for (int j=0;j<arreBod.size();j++){
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                   if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                   {
                        if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                        {
                            intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                            if(intCodBod==intCodBodT)
                               dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                        }
                   }
                }
                
                if(dblSubtotal > 0 ){
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaExistencia( intCodBod ) );
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                    vecReg.add(INT_VEC_BOTON, null);                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                    vecReg.add(INT_VEC_DEBE, new Double(0));  
                    vecReg.add(INT_VEC_HABER, new Double(dblSubtotal));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDatDia.add(vecReg);
                }
            }  
            
            if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaIvaCompras() );
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                vecReg.add(INT_VEC_DEBE, new Double(0)); 
                vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));   
                vecReg.add(INT_VEC_REF, null);
                vecReg.add(INT_VEC_NUEVO, null);
                vecDatDia.add(vecReg);                       
            }       
        }   
        objAsiDia.setDetalleDiario(vecDatDia);         
    }  
    
    /*
     * Regresa si el tipo de documento es ingreso o egreso 
     * Retorna  un int  
     *   1  si es ingreso 
     *  -1  si es egreso  
     */
    private int getAccionDoc(){
        if (objTipDoc.gettx_natdoc().equals("I"))
            return 1;
        else
            return -1;
    }          
   
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenSur;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObs1;
    private javax.swing.JPanel panObs2;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDirPrv;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomPrv;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
    
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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

    private void mostrarMsgValCam(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
     
    private int mostrarMsgYesNo(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit="Sistema Zafiro";        
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
    } 
    
    private void mostrarMsgErr(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit="Sistema Zafiro";        
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);                
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
    
    private void refrescaDatos(){
        try{
            int intNumDoc = 0;
            conCab = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(rstCab != null)
            {
                //Detalle        
                String strSer="";
                
                //Extrayendo los datos del detalle respectivo a esta orden de compra
                sSQL  = " SELECT a.co_itm,a.tx_codalt,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                        "      , b.st_ser, a.co_itmact " +
                        "      , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1))  " +
                        "        IN ( SELECT  UPPER(trim(a1.tx_cad)) "+
                        "             FROM tbr_bodloc AS a " +
                        "             INNER JOIN tbm_reginvmernuningegrfisbod AS a1 ON(a1.co_emp=a.co_Emp AND a1.co_bod=a.co_bod) " +
                        "             WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+""+
                        "             AND a1.st_reg='A' AND a.st_reg='P'  " +
                        "        )))THEN 'S' ELSE 'N' END AS proconf  "+                        
                        " FROM  tbm_detMovInv AS a " +
                        " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp AND a.co_itm=b.co_itm)"+
                        " WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + 
                        " AND a.co_loc = " + objParSis.getCodigoLocal()   + 
                        " AND a.co_tipdoc = " + objTipDoc.getco_tipdoc()     + 
                        " AND a.co_doc = " + rstCab.getString("co_doc")      +
                        " ORDER BY a.co_reg";
                    
                //System.out.println(sSQL);
                Statement stm = conCab.createStatement();
                ResultSet rst= stm.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;
                      
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
                    vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")*getAccionDoc()));
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")*getAccionDoc()));                                                                           
                    vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_cosuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivacom");
                    Boolean blnIva = new Boolean(strIva.equals("S"));                         
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can")* Double.parseDouble(""+getAccionDoc());
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
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact") );
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    
                         
                    vecData.add(vecReg);                         
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod); 
                calculaTotal();
                lblTit.setText(""+objParSis.getNombreMenu()+" # "+txtNumDoc.getText() );
            }
            conCab.close();
            blnHayCam = false; // Seteando que no se ha hecho cambios
        }//fin Try
        catch(SQLException Evt) {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt)  {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
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
            Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                Statement stm=con.createStatement();
                String strSQL="";
                strSQL= " SELECT CabMovInv.co_cli, CabMovInv.tx_nomcli as nomcli, CabMovInv.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                        " CabMovInv.co_com as co_com, CabMovInv.tx_nomven as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                        " CabMovInv.co_doc, CabMovInv.fe_doc, CabMovInv.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        " CabMovInv.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                        " CabMovInv.tx_obs1, CabMovInv.tx_obs2, CabMovInv.nd_sub, CabMovInv.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        " CabMovInv.st_reg, CabMovInv.ne_numcot as num_cot, CabMovInv.ne_numdoc as num_doc, " + // Campo para saber si esta anulado o no
                        " CabMovInv.tx_numped as num_ped, CabMovInv.co_tipdoc, CabMovInv.ne_secemp, CabMovInv.st_regrep " +
                        " FROM tbm_cabMovInv as CabMovInv " +
                        " Where CabMovInv.st_reg not in('E') and  CabMovInv.co_emp=" + rstCab.getString("co_emp") +
                        " AND CabMovInv.co_loc="    + rstCab.getString("co_loc") +
                        " AND CabMovInv.co_tipDoc=" + rstCab.getString("co_tipDoc") +
                        " AND CabMovInv.co_doc="    + rstCab.getString("co_doc") ;
                //System.out.println(strSQL);
                ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intNumDoc = rst.getInt("co_doc");
                    txtCodDoc.setText(""+intNumDoc);                    
                    cargarCabTipoDoc(rst.getInt("co_tipdoc"));
                    txtNumDoc.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    objAsiDia.consultarDiarioCompleto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), rst.getInt("co_doc"));
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
                    strFecResDoc= rst.getString("fe_doc");
                    STR_ESTREG=rst.getString("st_regrep");
                      
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
      
                    /* Verificando si se encuentra en estado Anulado */
                    if(rst.getString("st_reg").equals("I")){

                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                        }
                    }
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
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
                objAsiDia.setEditable(true);
                dtpFecDoc.setHoy();
                if (objTipDoc.getco_tipdoc()==-1)
                    cargaTipoDocPredeterminado();
                else
                    cargarCabTipoDoc(objTipDoc.getco_tipdoc());
                
                
                if (!txtCodTipDoc.getText().equals(""))
                    cargarNumDocOrdCom();

                txtNumDoc.requestFocus();
                txtNumDoc.setSelectionStart(0);
                txtNumDoc.setSelectionEnd(txtNumDoc.getText().length());
                setEditable(true);
                blnHayCam=false;
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
            //cargarRegistroInsert();            
            return true;
        }

        public boolean insertar()
        {
            if(!isCamVal())
              return false;

            if(!insertarReg())
              return false;

            recostearItmGrp();

            blnHayCam=false;
           return true;
        }        
        
        public boolean afterModificar() {
            butTipDoc.setEnabled(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txtNumDoc.setEditable(false);
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
            //limpiarFrm();
            if(!dtpFecDoc.isFecha()){
                dtpFecDoc.setHoy();
            }            
            objAsiDia.setEditable(false);
           // setEditable(false);
        }
             
        public boolean consultar() {
          return _consultar(FilSql());
        }

        public boolean modificar(){
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                 mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                 return false;
            }
            if (strAux.equals("Anulado")){
                 mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                 return false;
            }
            if(!verificaItmUni())
               return false;

            if (!isCamVal())
                return false;

            if (!actualizarReg())
               return false;

            recostearItmGrp();

          blnHayCam=false;
          return true;
        }

        public void clickModificar(){
            objAsiDia.setEditable(true);
            setEditable(true);
            butTipDoc.setEnabled(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txtNumDoc.setEditable(false);
            java.awt.Color colBack = txtSub.getBackground();
            txtSub.setEditable(false);
            txtSub.setBackground(colBack);
            txtIva.setEditable(false);
            txtIva.setBackground(colBack);
            txtTot.setEditable(false);
            txtTot.setBackground(colBack);
        }
    
        public void clickEliminar(){
            objAsiDia.setEditable(false);
            setEditable(false);
        }
          
        public boolean eliminar(){
            try{
               strAux=objTooBar.getEstadoRegistro();
               if (strAux.equals("Eliminado")){
                   mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                   return false;
               }
              if(!verificaItmUniAnu())
                 return false;

              if(!objUltDocPrint.verificarsiesconfirmado(txtCodDoc.getText(),txtCodTipDoc.getText()))
                 return false;

               if (!eliminarReg())
                   return false;
               //Desplazarse al siguiente registro si es posible.
               if (!rstCab.isLast()){
                   rstCab.next();
                   cargarReg();
               }else{
                   objTooBar.setEstadoRegistro("Eliminado");
                   limpiarFrm();
               }
               blnHayCam=false;
           }catch (java.sql.SQLException e){  return true; }
           return true;
        }

        public void clickAnular(){
            objAsiDia.setEditable(false);
            setEditable(false);
        }

        public boolean anular()
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
           if(!verificaItmUniAnu())
             return false;

           if(!objUltDocPrint.verificarsiesconfirmado(txtCodDoc.getText(),txtCodTipDoc.getText()))
             return false;

           if(!anularReg())
              return false;

            recostearItmGrp();

            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
       
        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        public void clickAceptar(){

        }
        public void clickCancelar(){

        }             
            
            
   }   //Fin del ToolBar
       

    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
        ///    System.out.println ("Se libera Objeto...");

    }

    private boolean verificaItmUni(){
        boolean  blnRes=true;
         try{
         for(int i=0; i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                   int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                   int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                   if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                       if(intCodItm != intCodItmAct){
                           mostrarMsgInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                           blnRes=false;
                           break;
                       }
                   }
           }}
         }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
       return blnRes;     
    }
        
    private boolean verificaItmUniAnu(){
        boolean  blnRes=true;
         try{
         for(int i=0; i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                   int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                   int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                 //  if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                       if(intCodItm != intCodItmAct){
                           mostrarMsgInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                           blnRes=false;
                           break;
                       }
                 //  }
           }}
        }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
        return blnRes;     
   }    

    private boolean isCamVal(){
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Codigo de Tipo de Documento");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if(txtNumDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("No. Ingreso/Egreso");
            txtNumDoc.requestFocus();
            return false;
        }


        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de Documento");
            dtpFecDoc.requestFocus();
            return false;
        }

      if (!objAsiDia.isDiarioCuadrado()){
             mostrarMsgInf("Asiento descuadrado.");
             tabFrm.setSelectedIndex(2);
             return false;                                    
        }
        if (!objAsiDia.isDocumentoCuadrado(txtTot.getText())){
             mostrarMsgInf("Asiento y Total del documento esta descuadrado.");
             tabFrm.setSelectedIndex(1);
             return false;                                                       
        }


         for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
             if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){

             double dblCan = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
             if(dblCan <= 0.00 ){
                 javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                 String strTit, strMsg;
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                 obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                 tblDat.repaint();    
                 tblDat.requestFocus();
                 tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                 return false; 
             }

              double dblCos = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_PRECOS).toString());
             if(dblCos <= 0.00 ){
                 javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                 String strTit, strMsg;
                 strTit="Mensaje del sistema Zafiro";
                 strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                 obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                 tblDat.repaint();    
                 tblDat.requestFocus();
                 tblDat.editCellAt(intRowVal, INT_TBL_PRECOS);
                 return false; 
             }

         }}

        if(!objAsiDia.isDocumentoCuadrado(txtTot.getText()))               
          return false;

        return true; 
    }    
    
    /********************************************************************************/      
    private boolean actualizarReg(){
        boolean blnRes = false;
        java.sql.Connection conMod; 
        try{
           conMod =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           if(conMod!=null){
              conMod.setAutoCommit(false);

              if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  

              if(modificaCab(conMod,STR_ESTREG)){
               if(AnularRegEliminados(conMod)){
                if(AnularRegdeDetalle(conMod)){
                 if(InvertirDet(conMod, STR_ESTREG)){
                  if(objAsiDia.actualizarDiario(conMod, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(),Integer.parseInt(txtCodDoc.getText()),txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A")){
                     blnRes = true;
                     conMod.commit();
                     conMod.setAutoCommit(true);  
                     objTblMod.clearDataSavedBeforeRemoveRow();
                     objTblMod.initRowsState();   
                   }else conMod.rollback(); 
                  }else conMod.rollback();  
                 }else conMod.rollback();  
                }else conMod.rollback();  
             }else conMod.rollback();  

            if(blnRes){
                for(int i=0; i<tblDat.getRowCount(); i++){ 
                  if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                      tblDat.setValueAt("E", i, INT_TBL_ESTADO);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
            }}}

           conMod.close();
           conMod=null;
        }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes = false;  }
          catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes = false;  } 
         return blnRes;      
    }
    /*****************************************************************************************/   
            
    private boolean eliminarReg(){
        boolean blnRes=false;   
        java.sql.Connection conAnu;   
        try{
           conAnu =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                
           if(conAnu!=null){
              conAnu.setAutoCommit(false);                                                                                         

             if(!isAnulada()){  
                 if(anueliCab(conAnu, "E")){
                  if(anueliDet(conAnu)){ 
                   if(objAsiDia.eliminarDiario(conAnu,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtCodDoc.getText()))){
                     conAnu.commit();
                     blnRes=true;
                   }else conAnu.rollback();
                  }else conAnu.rollback();
                 }else conAnu.rollback();  
             }else{
                   if(anueliCab(conAnu, "E")){
                    if(objAsiDia.eliminarDiario(conAnu,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtCodDoc.getText()))){
                      conAnu.commit();
                      blnRes=true;
                    }else conAnu.rollback();
                   }else conAnu.rollback();
             }     
            conAnu.close();    
            conAnu=null;     
          }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
            catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
         return blnRes;
     }         

    private boolean anularReg(){
        boolean blnRes=false; 
        java.sql.Connection conAnu;
        try{
           conAnu =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                
           if(conAnu!=null){
              conAnu.setAutoCommit(false);     

              if(anueliCab(conAnu, "I")){
               if(anueliDet(conAnu)){ 
                if(objAsiDia.anularDiario(conAnu,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtCodDoc.getText()))){
                   conAnu.commit();
                   blnRes=true;
               }else conAnu.rollback();
              }else conAnu.rollback(); 
             }else conAnu.rollback();   
           conAnu.close();    
           conAnu=null;     
         }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
           catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
        return blnRes;
     } 

    private boolean anueliCab(java.sql.Connection conn, String strEst){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        String strFecSis="";
        try{
           if(conn!=null){
              stmLoc=conn.createStatement();

              strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

              if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  

              strSql="UPDATE tbm_cabMovInv SET st_reg='"+strEst+"', st_regrep='"+STR_ESTREG+"' "+
              " ,fe_ultMod='"+strFecSis+"', co_usrMod="+objParSis.getCodigoUsuario()+ 
              " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+"" +
              " AND co_doc="+txtCodDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText();
              stmLoc.executeUpdate(strSql);                                       

              stmLoc.close();
              stmLoc=null;
              blnRes=true;
        }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
          catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
      }            

    private boolean anueliDet(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strEstFisBod="";
        int intReaActStk=0;
        int intTipMov=0;
        int intCodItm=0;
        int intCodBod=0;
        double dblCan=0;
        try{
           if(conn!=null){
              stmLoc=conn.createStatement();

              intTipMov=getAccionDoc();
              objInvItm.inicializaObjeto();

              String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( " +
              " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
              " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
              " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
              " THEN 'S' ELSE 'N' END AS proconf  ";

              strSql="SELECT a.co_itmact, a.co_bod,  abs(a.nd_can) as ndcan FROM tbm_detMovInv AS a " +
              " INNER JOIN tbm_inv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) "+        
              " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+"" +
              " AND a.co_doc="+txtCodDoc.getText()+" AND a.co_tipDoc="+txtCodTipDoc.getText()+" AND a1.st_ser='N' ";
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){
                  intCodItm =rstLoc.getInt("co_itmact");
                  intCodBod =rstLoc.getInt("co_bod");
                  dblCan=rstLoc.getDouble("ndcan");
                  strEstFisBod="N";

                  objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                  intReaActStk=1;
              }    
              rstLoc.close();
              rstLoc=null;
              stmLoc.close();
              stmLoc=null;

            if(intReaActStk==1){
              if(!objInvItm.ejecutaActStock(conn, 1))
                  return false;
              if(!objInvItm.ejecutaVerificacionStock(conn, 1))
                 return false;
            }
           blnRes=true;
        }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
          catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
      }            

    /**
     *Esta funcion permite verificar que exista stock,
     *caso cotrario mostrara un mensaje indicando que item tiene el problema de inventario
     * @Param    conAnu : Coneccion de la base 
     * @Param    intCodItm : codigo del item 
     * @Param    strCodAlt : codigo alterno del item que se esta verificando
     *
     * @return  Retorna un valor true en caso de no tener el stock en negativo 
     *          caso contrario devuelve un valor falso.  
     */
    private boolean verificaStock(java.sql.Connection conAnu, int intCodItm, int intCodBod, String strCodAlt ){
        boolean blnRes=true;
        try{
            String sql = "SELECT  a.nd_stkact , b.tx_codalt  FROM tbm_invbod as a inner join tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) " +
            " where a.co_emp="+objParSis.getCodigoEmpresaGrupo()+" and a.co_itm = (Select co_itm from tbm_equinv where co_emp= "+objParSis.getCodigoEmpresaGrupo()+" " +
            " and co_itmmae IN  ( Select co_itmmae from tbm_equinv where co_emp =  "+objParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+" ) )  " +
            " and a.co_bod = ( SELECT co_bodgrp FROM  tbr_bodempbodgrp where co_emp="+objParSis.getCodigoEmpresa()+" and co_bod= "+intCodBod+" ) ";

                java.sql.ResultSet rst2;
                java.sql.Statement stm2= conAnu.createStatement();   
                rst2 = stm2.executeQuery(sql);
                if(rst2.next()){
                   double douStk = rst2.getDouble("nd_stkAct");
                //        System.out.println("Stock ==> "+ sql ); 
                if(douStk < 0.00) {
                     if (mostrarMsgYesNo("Ocurrio  un problema en el inventario \n ¿Desea seguir verificando?")==javax.swing.JOptionPane.YES_OPTION){
                        mostrarMsgInf(" " + rst2.getString("tx_codalt")  +"  falta  Stock  para completar el pedido"); 
                     }
                  return false;  
               }}
                rst2.close();
                rst2=null;
                stm2.close();
                stm2=null;
            blnRes=true;  
        }
        catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }

    private boolean modificaCab(java.sql.Connection conMod, String strestreg){
        boolean blnRes=false;
        java.sql.PreparedStatement pstModMovInv;
        String strFecSis="";
        String strFecha="";
        String strSqlAux="",strSql="";
        try{
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
            strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

           if(txtCodPrv.getText().equals("")) strSqlAux =" co_cli=null "; 
            else strSqlAux =" co_cli="+txtCodPrv.getText()+" "; 

           strSqlAux += ",tx_nomCli='"+txtNomPrv.getText()+"'";
           strSqlAux += ",tx_dirCli='"+txtDirPrv.getText()+"',";



           strSql="UPDATE tbm_cabMovInv SET "+strSqlAux+" "+ 
           " fe_doc='"+strFecha+"', ne_numDoc="+txtNumDoc.getText()+", "+ 
           " tx_obs1='"+txaObs1.getText()+"', tx_obs2='"+txaObs2.getText()+"', "+ 
           " nd_sub="+(objUti.redondeo(Double.parseDouble(txtSub.getText()),6)*getAccionDoc())+", "+
           " nd_porIva="+dblPorIva+", fe_ultMod='"+strFecSis+"', " +
           " co_usrMod="+objParSis.getCodigoUsuario()+" ,st_regrep='"+strestreg+"' "+
           " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND "+  
           " co_loc="+objParSis.getCodigoLocal()+ " AND co_tipDoc="+txtCodTipDoc.getText()+ " " +
           " AND co_doc="+txtCodDoc.getText(); 
           pstModMovInv = conMod.prepareStatement(strSql);    
           pstModMovInv.executeUpdate(); /* Ejecutando el Update */
         blnRes=true;

        }
        catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
        return blnRes;
    }

    //********************************************************
    private boolean AnularRegdeDetalle(java.sql.Connection conMod){
        boolean blnRes=false;
        int intReaActStk=0;
        int intTipMov=1;
        int intCodItmOri=0;
        int intCodBodOri=0;
        int intCodItm2=0;
        int intCodBod2=0;
        double dblCanOri=0;
        double dblCan=0;
        String strNomItem="";
        String strEstFisBod="";
        try{  
            objInvItm.inicializaObjeto();
            intTipMov=getAccionDoc();

           for(int i=0; i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){

              intCodItmOri =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_ITMORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_ITMORI).toString()));
              intCodBodOri =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_BODORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_BODORI).toString())); 
              dblCanOri = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANORI).toString()));   

              intCodItm2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
              intCodBod2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
              strNomItem =  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString());

              dblCan=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
              strEstFisBod="N"; //(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          

              if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
                /**/ System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION >> ");
                /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                   if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                      objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov, strEstFisBod, strMerIngEgr, strTipIngEgr, 1  );
                      intReaActStk=1;
                   }
                /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
              }else{
                     if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("E")){
                          System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO >> ");    
                     }
                     if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                         if(intCodItmOri!=intCodItm2){
                           //   System.out.println("** REALIZA DEVOLUCION DEL ITEM ORIGEN >> "+intCodItmOri+ " CANTIDAD "+ dblCanOri ); 
                            if(objInvItm.isItmServicio(conMod, intCodItmOri)){ // no es de servicio.
                                 objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItmOri, intCodBodOri, dblCanOri, (intTipMov*-1),  strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                                 intReaActStk=1;
                             }
                            //  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
                            /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                            if(objInvItm.isItmServicio(conMod, intCodItm2)){ // no es de servicio.
                                objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                                intReaActStk=1;
                            }
                           /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                        }else if(intCodBodOri!=intCodBod2){
                           //   System.out.println("** REALIZA DEVOLUCION DEL INVENTARIO DEL ITEM ORIGEN >> "+intCodItmOri+ " CANTIDAD "+ dblCanOri ); 
                           if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                               objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItmOri, intCodBodOri, dblCanOri, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                                intReaActStk=1;
                           }
                           /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                           //  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
                           if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                              objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                              intReaActStk=1;
                           }
                           /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                       }else if(dblCanOri!=dblCan){
                        if(dblCan > dblCanOri){ 
                             double dblCanDif = dblCan-dblCanOri;
                             dblCanDif=Math.abs(dblCanDif);
                              // System.out.println("** REALIZA AUMENTO EN EL INVENTARIO AL ITEM >> "+ dblCanDif);
                              /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                              if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                                  objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCanDif, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                                  intReaActStk=1;
                               }
                         }
                         if(dblCan < dblCanOri){  
                            double dblCanDif = dblCanOri-dblCan;
                            dblCanDif=Math.abs(dblCanDif);
                            //System.out.println("**  REALIZA DISMINUCION EN INVENTARIO  AL ITEM >> "+ dblCanDif);
                            /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                            if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                                 objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCanDif, (intTipMov*-1),  strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                                 intReaActStk=1;
                            } 
                         }
                      }else  System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO 2 >> ");    
                  }
               }
             }}
             blnRes=true;     
            if(intReaActStk==1){
               if(!objInvItm.ejecutaActStock(conMod, 1))
                   blnRes=false;
              if(!objInvItm.ejecutaVerificacionStock(conMod, 1))
                   blnRes=false;
            }     
        }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }
    //*************************************************************************    
    
    private boolean InvertirDet(java.sql.Connection conMod, String strEstReg){
        boolean blnRes=true;
        String strSQL="";
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        java.sql.Statement stmDet;
        int intControl=0;
        try{
           stmDet=conMod.createStatement();                                                                            
          //********** ELIMINA EL DETALLLE 
             strSQL=" DELETE FROM tbm_detMovInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" "+
             " AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" " +
             " AND co_doc="+txtCodDoc.getText();
             stmDet.executeUpdate(strSQL);
          //********** INSERTA EL DETALLLE 
           GLO_strArreItm="";
           for(int i=0; i<tblDat.getRowCount(); i++){ 
            if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 

              if(intControl!=0)    
               GLO_strArreItm=GLO_strArreItm+",";

               GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
               intControl++;

               if (i>0)
               stbins.append(" UNION ALL ");
               stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
               ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITMACT))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
               ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
               ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
               ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
               ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
               ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'"+strEstReg+"'");
         }}

          if(intControl > 0 ){
              strSQL = " INSERT INTO  tbm_detMovInv " +
              " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
              "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep ) "+stbins.toString();
              stmDet.executeUpdate(strSQL);
              stmDet.close();
              stmDet=null;
          }else return false;

        blnRes=true;
       }catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
       return blnRes;
    }

    private void recostearItmGrp(){
        int i,j; 
        java.sql.Connection con;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="", strFecResDes="";
        try{    
         con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(con!=null){  
             stmLoc=con.createStatement();

              strSql="select *, case when fecori < fecact then fecori else fecact end  as fecres "
              + " from ( select  date('"+strFecResDoc+"') as fecori, date('"+objUti.formatearFecha( dtpFecDoc.getText(), "dd/MM/yyyy", "yyyy-MM-dd" )+"') as fecact ) as x ";
              rstLoc=stmLoc.executeQuery(strSql);
              if(rstLoc.next()){
                  strFecResDes=rstLoc.getString("fecres");
                  strFecResDoc=rstLoc.getString("fecres");
              }
              rstLoc.close();
              rstLoc=null;

             strFecResDes = objUti.formatearFecha( strFecResDes, "yyyy-MM-dd", "yyyy/MM/dd" );
             for(i=0; i<tblDat.getRowCount(); i++){
               if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                 objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_CODITM).toString(), strFecResDes, "yyyy/MM/dd");
             }}

          con.close();
          con=null;
        }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    }

    private boolean insertarReg(){   
        boolean blnRes=false;
        java.sql.Connection conIns;
        int intSecEmp=0, intSecGrp=0;
        if( objUltDocPrint.getExisteDoc(Integer.parseInt(txtNumDoc.getText()) ,  Integer.parseInt(txtCodTipDoc.getText()) ) ){
           mostrarMsgInf("No. de Doc ya Existe");
           txtNumDoc.requestFocus();
           txtNumDoc.selectAll();
           return false;
        }
        try{
            conIns=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conIns!=null){


               intSecEmp = objUltDocPrint.getNumSecDoc( conIns, objParSis.getCodigoEmpresa()   );
               intSecGrp = objUltDocPrint.getNumSecDoc( conIns, objParSis.getCodigoEmpresaGrupo()  );


               conIns.setAutoCommit(false);

               txtCodDoc.setText(""+objUltDocPrint.getCodigoDocumento(conIns, objTipDoc.getco_tipdoc()));
               intNumDoc = Integer.parseInt(txtCodDoc.getText());                                 
               if(insertarCab(conIns, intNumDoc, intSecEmp, intSecGrp )){
                if(insertarDet(conIns, intNumDoc)){
                  if(objAsiDia.insertarDiario(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) {
                      conIns.commit();
                      blnRes=true;
                  }else conIns.rollback();   
                 }else conIns.rollback();   
               }else conIns.rollback();

               if(blnRes){
                  for(int i=0; i<tblDat.getRowCount(); i++){ 
                       if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                             tblDat.setValueAt("E", i, INT_TBL_ESTADO);
                             tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
                             tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
                             tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
                }}}   

               STR_ESTREG="I";
               conIns.close();                                            
               conIns=null;
               objTblMod.clearDataSavedBeforeRemoveRow();
               objTblMod.initRowsState();
        }}
        catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;                  
    }  

    private boolean insertarCab(java.sql.Connection conIns, int intNumDoc, int intSecEmp, int intSecGrp   ){
        boolean blnRes = true;
        java.sql.Statement stm;
        String strSQL, strFecha, strFecSis;
        try{
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                int Fecha[] =  dtpFecDoc.getFecha(dtpFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                strFecResDoc= strFecha;

                strSQL="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, ne_secemp, ne_secgrp,"+ //" ne_secgrpant, ne_secempant,  " +
                " fe_doc,  ne_numDoc, tx_obs1, tx_obs2, nd_sub,nd_tot, fe_ing," +
                " co_usrIng,st_reg , co_cli , tx_nomCli, tx_dirCli , st_regrep ) " +
                " VALUES("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal() + "," + Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+intSecEmp+", " +
                " "+intSecGrp+","+ //" "+(intSecGrp-1)+", "+(intSecEmp-1)+", " +
                " '"+strFecha+"',"+txtNumDoc.getText()+",'"+txaObs1.getText().replaceAll("'","") + "','" + txaObs2.getText().replaceAll("'","") + "', " +
                " "+(objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar()) * getAccionDoc())+","+(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()) * getAccionDoc())+", " +
                " '"+strFecSis+"',"+objParSis.getCodigoUsuario()+",'A',"+ objUti.codificar(txtCodPrv.getText(), 2 ) +",'"+txtNomPrv.getText()+"','"+txtDirPrv.getText()+"','I')";

                stm = conIns.createStatement();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
        }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
    }
  
    private boolean insertarDet_respaldo(java.sql.Connection conIns, int intNumDoc  ){
        boolean blnRes = true;
        int intControl=0;
        double dlbCanMov=0;
        String strSQL;
        int intSer=0;
        int INTCODEMPGRP=0;
        StringBuffer stbinvemp=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stbinvbodemp=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stbinvgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stbinvbodgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stbEmp=new StringBuffer(); //VARIABLE TIPO BUFFER
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        java.sql.Statement stmDet;
        java.sql.ResultSet rst;
        try{
             GLO_strArreItm="";
             INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();

              String str_MerIEFisBod="S";
              double dbl_canConIE=0.00;
              for(int i=0; i<tblDat.getRowCount(); i++){ 
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 

                   //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/
                    dbl_canConIE  = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
                    String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                     str_MerIEFisBod="N";

                      //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                              if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                                  if(strEstFisBod.equals("N")){
                                    if(strMerIngEgr.equals("S")){   
                                      str_MerIEFisBod="S";
                                      dbl_canConIE=0;
                                  }}
                              }
                      /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/


                     if(intControl!=0)    
                       GLO_strArreItm=GLO_strArreItm+",";

                       GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                       intControl++;

                       if (i>0)
                       stbins.append(" UNION ALL ");
                       stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
                       ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
                       ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
                       ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
                       ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
                       ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
                       ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
               }}

               if(intControl > 0 ){
                  strSQL = " INSERT INTO  tbm_detMovInv " +
                  " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
                  "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep, st_meringegrfisbod , nd_cancon ) "+stbins.toString();
                  stmDet=conIns.createStatement();
                  stmDet.executeUpdate(strSQL);
                  stmDet.close();
                  stmDet=null;
              }else return false;



              String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
              " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
              " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
              " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
              " THEN 'S' ELSE 'N' END AS proconf  ";

                strSQL = "SELECT  a.co_itm, a.co_bod, sum(a.nd_can) as cantidad "+strAux2+" FROM tbm_detmovinv as a " +
                " INNER JOIN tbm_inv as inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
                " WHERE a.co_Emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a.co_tipdoc="+txtCodTipDoc.getText()+" " +
                " and a.co_doc="+intNumDoc+" and inv.st_ser='N' " +
                " GROUP BY a.co_itm,a.co_bod, proconf";
                //System.out.println(" "+ strSQL );
                stmDet=conIns.createStatement();
                rst= stmDet.executeQuery(strSQL);
                while(rst.next()){
                    dlbCanMov=rst.getDouble("cantidad");
                    int intCodItm=rst.getInt("co_itm");
                    int intCodBod=rst.getInt("co_bod");

                    String strEstFisBod=rst.getString("proconf");
                    String strAux=" ,0 as caningbod, 0 as canegrbod ";
                    if(strEstFisBod.equals("N")){
                     if(strMerIngEgr.equals("S")){
                       if(strTipIngEgr.equals("I")){ 
                           strAux=", abs("+dlbCanMov+") as caningbod, 0 as canegrbod ";
                       }
                       if(strTipIngEgr.equals("E")){
                           strAux=", abs("+dlbCanMov+") as canegrbod, 0 as caningbod  ";
                       } 
                     }} 

                   if (intSer>0)
                        stbinvemp.append(" UNION ALL ");
                        stbinvemp.append("SELECT "+objParSis.getCodigoEmpresa()+" AS coemp,"+intCodItm+" AS coitm,"+dlbCanMov+" AS stock");

                    if (intSer>0)
                        stbinvbodemp.append(" UNION ALL ");
                        stbinvbodemp.append("SELECT "+objParSis.getCodigoEmpresa()+" AS coemp,"+intCodBod+" as cobod,"+intCodItm+" AS coitm,"+dlbCanMov+" AS stock "+strAux+" ");

                    /********************* EMPRESA GRUPO  **********************/

                    if (intSer>0)
                        stbinvgrp.append(" UNION ALL ");
                        stbinvgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                        "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+")) as coitm," +
                        ""+dlbCanMov+" AS stock");


                    if (intSer>0)
                        stbinvbodgrp.append(" UNION ALL ");
                        stbinvbodgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                        "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+")) as coitm," +
                        "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+objParSis.getCodigoEmpresa()+" and co_bod="+intCodBod+") as cobod, "+
                        ""+dlbCanMov+" AS stock "+strAux+" ");

                    /****************************************************************/

                     //**********************  STOCK DEL ITEM **************************//
                    if (intSer>0)
                        stbEmp.append(" UNION ALL ");
                        stbEmp.append(" SELECT  nd_stkact ,  "+rst.getInt("co_itm")+" as coitm FROM tbm_invbod   where co_emp="+INTCODEMPGRP+" and co_itm = " +
                        " ( Select co_itm from tbm_equinv where co_emp= "+INTCODEMPGRP+" and co_itmmae IN " +
                        " ( Select co_itmmae from tbm_equinv where co_emp = "+objParSis.getCodigoEmpresa()+" and co_itm= "+rst.getInt("co_itm")+" ) )  " +
                        "  and co_bod = ( SELECT co_bodgrp FROM  tbr_bodempbodgrp where co_emp="+objParSis.getCodigoEmpresa()+" and co_bod= "+rst.getInt("co_bod")+") ");


                    intSer=1;
                }



                 if(intSer==1){
                    if(intControl > 0){
                       //******************************* EMPRESA *********************************************
                       strSQL ="Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct + a.stock " +
                       " FROM ("+stbinvemp.toString()+") AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                      // stmDet.executeUpdate(strSQL);

                       strSQL +=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct + a.stock , nd_caningbod=nd_caningbod+a.caningbod, nd_canegrbod=nd_canegrbod+a.canegrbod " +
                       " FROM ("+stbinvbodemp.toString()+") AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";
                       System.out.println(" "+ strSQL );
                       stmDet.executeUpdate(strSQL);

                       //******************************* GRUPO *********************************************
                        strSQL ="Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact + a.stock " +
                        " FROM("+stbinvgrp.toString()+") AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                       // stmDet.executeUpdate(strSQL);

                        strSQL +=" ; Update tbm_invbod  set nd_stkact = nd_stkact + a.stock, nd_caningbod=nd_caningbod+a.caningbod, nd_canegrbod=nd_canegrbod+a.canegrbod " +
                        " FROM("+stbinvbodgrp.toString()+") AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                        System.out.println(" "+ strSQL );
                        stmDet.executeUpdate(strSQL);
                        //*************************************************************************************    
                  }}

                if(getAccionDoc() == -1 ){
                  rst = stmDet.executeQuery(stbEmp.toString());  // LOCAL
                   while(rst.next()){
                       if(rst.getFloat(1) < 0.00){
                          for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                                if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString().equals(rst.getString(2))){
                                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                            String strTit, strMsg;
                                            strTit="Mensaje del sistema Zafiro";
                                            strMsg="El producto :  " + ((tblDat.getValueAt(intRowVal, INT_TBL_ITMALT)==null || tblDat.getValueAt(intRowVal, INT_TBL_ITMALT).toString().equals(""))?"0":tblDat.getValueAt(intRowVal, INT_TBL_ITMALT).toString())
                                            + "  -   " + ((tblDat.getValueAt(intRowVal, INT_TBL_DESITM)==null || tblDat.getValueAt(intRowVal, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(intRowVal, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede Realizar la transaccion..!!" ;
                                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                            blnRes=false;
                                            break;
                                        }}}
                          }
                    }
                }    
                 rst.close();
                 rst=null;       
                 stmDet.close();
                 stmDet=null;
                 stbinvemp=null;
                 stbinvbodemp=null;
                 stbinvgrp=null;
                 stbinvbodgrp=null;
                 stb=null;
                 stbEmp=null;
                 stbins=null;
        }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
         catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
        
    private boolean insertarDet(java.sql.Connection conIns, int intNumDoc  ){
        boolean blnRes = true;
        java.sql.Statement stmDet;
        double dblCanIngFisBod=0;
        double dblCanEgrFisBod=0;
        double dblCan=0;
        double dbl_canConIE=0.00;
        int intCodItm=0;
        int intCodBod=0;
        int intControl=0;
        int intReaActStk=0;
        int intTipMov=0;
        String strSQL;
        String str_MerIEFisBod="S";
        String strEstFisBod="";
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        try{
            GLO_strArreItm="";

            objInvItm.inicializaObjeto();
            intTipMov=getAccionDoc();

            for(int i=0; i<tblDat.getRowCount(); i++){ 
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                    intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM)); 
                    intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
                    dblCan=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
                    dblCanIngFisBod=0;
                    dblCanEgrFisBod=0;
                    //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/
                    dbl_canConIE  = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
                    strEstFisBod="N"; //(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                    str_MerIEFisBod="N"; 
                    //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                    if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                          if(strEstFisBod.equals("N")){
                            if(strMerIngEgr.equals("S")){   
                              str_MerIEFisBod="S";
                              dbl_canConIE=0;
                        }}
                         objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, intTipMov, strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                         intReaActStk=1;
                    }
                    /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                    if(intControl!=0)    
                       GLO_strArreItm=GLO_strArreItm+",";

                       GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                       intControl++;

                       if (i>0)
                       stbins.append(" UNION ALL ");
                       stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
                       ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
                       ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
                       ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objParSis.getDecimalesBaseDatos())+", " +
                       ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
                       ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
                       ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
                }
            }

            if(intControl > 0 ){
                strSQL = " INSERT INTO  tbm_detMovInv " +
                " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
                "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep, st_meringegrfisbod , nd_cancon ) "+stbins.toString();
                stmDet=conIns.createStatement();
                stmDet.executeUpdate(strSQL);
                stmDet.close();
                stmDet=null;
            }
            else return false;

            if(intReaActStk==1){
              if(!objInvItm.ejecutaActStock(conIns, 1))
                  return false;
              if(!objInvItm.ejecutaVerificacionStock(conIns, 1))
                  return false; 
            }
           stbins=null;
       }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
    }

    private boolean consultar3() {
        return _consultar(FilSql());
    }    
            
    private boolean AnularRegEliminados(java.sql.Connection conAnu){
        boolean blnRes=true;
        int intReaActStk=0;
        int intTipMov=0;
        int intCodItm=0;
        int intCodBod=0; 
        double dblCan=0;
        String strEstFisBod="";
        java.util.ArrayList arlAux;
        try{
            arlAux=objTblMod.getDataSavedBeforeRemoveRow();
            if(arlAux!=null){
                intTipMov=getAccionDoc();
                objInvItm.inicializaObjeto();

                for(int i=0;i<arlAux.size();i++){
                    intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
                    intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
                    dblCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
                    strEstFisBod="N"; //objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
                    //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                    if(objInvItm.isItmServicio(conAnu, intCodItm)){ // no es de servicio
                        objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                        intReaActStk=1;
                    }
                    /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                }
                if(intReaActStk==1){
                  if(!objInvItm.ejecutaActStock(conAnu, 1))
                      return false;
                  if(!objInvItm.ejecutaVerificacionStock(conAnu, 1))
                     return false;
                }
            }
            blnRes=true;
        }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }        
            
    private String FilSql() {
        String sqlFiltro = "";
        //Agregando filtro por Codigo Tipo de Documento
       if(!txtCodTipDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";

        //Agregando filtro por Numero de Documento
        if(!txtCodDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.co_doc = " + txtCodDoc.getText()+ "";

        //Agregando filtro por Numero de Documento
        if(!txtNumDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CabMovInv.ne_numdoc = " + txtNumDoc.getText()+ "";


        //Agregando filtro por Fecha
        if(dtpFecDoc.isFecha()){
            int FecSql[] = dtpFecDoc.getFecha(dtpFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and CabMovInv.fe_doc = '" +  strFecSql + "'";
        }    
        return sqlFiltro ;
    }   
    
    private boolean _consultar(String strFil){
        strFiltro= strFil;
        try{
            conCab=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conCab!=null){

                //stmCab=conCab.createStatement();
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                sSQL= " SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc " +                                                                            
                      " FROM tbm_cabMovInv as CabMovInv left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) "+
                      " LEFT OUTER JOIN tbr_tipdocprg as docprg on (docprg.co_emp = Doc.co_emp and docprg.co_loc = Doc.co_loc and docprg.co_tipdoc = Doc.co_tipdoc)" +
                      " WHERE CabMovInv.st_reg not in('E') and CabMovInv.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                      " AND CabMovInv.co_loc = " + objParSis.getCodigoLocal()  +" and docprg.co_mnu = " + objParSis.getCodigoMenu() + 
                      strFiltro + " ORDER BY CabMovInv.ne_secemp";
                //System.out.println(sSQL);
                rstCab=stmCab.executeQuery(sSQL);
                if(rstCab.next()){
                    rstCab.last();                                 
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    limpiarFrm();
                    conCab.close();
                    return false;
                }
                conCab.close();
            }
        }
        catch(SQLException Evt)   {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }                       
    return true;     
    }
    
    private boolean isServicio(int cod_itm){
        boolean blnIsServicio=false;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                /*
                 *  String para el hacer el query en la tabla de clientes y obtener 
                 *  sus datos .
                */
                String strSql = "select st_ser from tbm_inv where co_emp = " + objParSis.getCodigoEmpresa() + " and co_itm = " + cod_itm;
                stm = con.createStatement();
                rst = stm.executeQuery(strSql);
                if(rst.next()){
                    blnIsServicio =  (rst.getString("st_ser").charAt(0)=='S')? true: false;
                }
                rst.close();
                stm.close();
                con.close();
            }
        }
        catch(java.sql.SQLException Evt){
                return false;
        }
        catch(Exception Evt){
                return false;
        }
        return blnIsServicio;
    }
    
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
                strSQL += " where  co_emp = " + objParSis.getCodigoEmpresa() + " and  co_loc = " + objParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtCodDoc.getText() + " and st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
  //                  mostrarMsgInf("Documento anulado no se puede modificar");
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
      
    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
        boolean blnRes=true;
        java.sql.Connection conn;
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

    private boolean cargarCabReg(java.sql.Connection con, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc )
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= " SELECT CabMovInv.co_cli, CabMovInv.tx_nomcli as nomcli, CabMovInv.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                        " CabMovInv.co_com as co_com, CabMovInv.tx_nomven as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                        " CabMovInv.co_doc, CabMovInv.fe_doc, CabMovInv.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        " CabMovInv.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                        " CabMovInv.tx_obs1, CabMovInv.tx_obs2, CabMovInv.nd_sub, CabMovInv.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        " CabMovInv.st_reg, CabMovInv.ne_numcot as num_cot, CabMovInv.ne_numdoc as num_doc, " + // Campo para saber si esta anulado o no
                        " CabMovInv.tx_numped as num_ped, CabMovInv.co_tipdoc, CabMovInv.ne_secemp, CabMovInv.st_regrep " +
                        " FROM tbm_cabMovInv as CabMovInv " +
                        " Where CabMovInv.st_reg not in('E') and  CabMovInv.co_emp=" + strCodEmp +
                        " AND CabMovInv.co_loc="    + strCodLoc +
                        " AND CabMovInv.co_tipDoc=" + strCodTipDoc +
                        " AND CabMovInv.co_doc="    + strCodDoc;
                //System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intNumDoc = rst.getInt("co_doc");
                    txtCodDoc.setText(""+intNumDoc);
                    cargarCabTipoDoc(rst.getInt("co_tipdoc"));
                    txtNumDoc.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    objAsiDia.consultarDiarioCompleto( Integer.parseInt( strCodEmp ), Integer.parseInt( strCodLoc ), Integer.parseInt( strCodTipDoc ), Integer.parseInt( strCodDoc )  );
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

                      STR_ESTREG=rst.getString("st_regrep");

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
                    if(rst.getString("st_reg").equals("I")){

                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                        }
                    }

                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }

                rst.close();
                stm.close();
                rst=null;
                stm=null;
                //Mostrar la posición relativa del registro.
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

    private void cargarDetReg(java.sql.Connection con, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        try{
            int intNumDoc = 0;
            String strSer="";

            //Extrayendo los datos del detalle respectivo a esta orden de compra
            sSQL  = " Select a.co_itm,a.tx_codalt,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                    "      , b.st_ser, a.co_itmact " +
                    "      , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1))  " +
                    "        IN ( SELECT  UPPER(trim(a1.tx_cad)) "+
                    "             FROM tbr_bodloc AS a " +
                    "             INNER JOIN tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                    "             WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a1.st_reg='A' and   a.st_reg='P' " +
                    "         ))) THEN 'S' ELSE 'N' END AS proconf  "+                   
                    " FROM tbm_detMovInv AS a " + 
                    " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp AND a.co_itm=b.co_itm) "+
                    " WHERE a.co_emp = " + strCodEmp + 
                    " AND a.co_loc = " + strCodLoc   + 
                    " AND a.co_tipdoc = " + strCodTipDoc   + 
                    " AND a.co_doc = " + strCodDoc  +
                    " ORDER BY a.co_reg";

            //System.out.println(sSQL);
            Statement stm = con.createStatement();
            ResultSet rst= stm.executeQuery(sSQL);
            double dblCan =  0, dblPre =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;

            Vector vecData = new java.util.Vector();
            while (rst.next())
            {
                strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                vecReg.add(INT_TBL_BUTITM, "");
                vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                vecReg.add(INT_TBL_BUTBOD, "");
                vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")));
                vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_cosuni")));
                vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                String strIva = rst.getString("st_ivacom");
                Boolean blnIva = new Boolean(strIva.equals("S"));
                vecReg.add(INT_TBL_BLNIVA, blnIva);

                dblCan    = rst.getDouble("nd_can");
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
                vecReg.add(INT_TBL_ESTADO, "E");
                vecReg.add(INT_TBL_IVATXT, "");
                vecReg.add(INT_TBL_ITMSER, strSer );
                vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact") );
                vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));

                vecData.add(vecReg);
            }
            objTblMod.setData(vecData);
            tblDat .setModel(objTblMod);
            calculaTotal();
            lblTit.setText(""+objParSis.getNombreMenu()+" # "+txtNumDoc.getText() );

            blnHayCam = false; // Seteando que no se ha hecho cambios
        }//fin Try
        catch(SQLException Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt)    {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }

    private void cargarNumDocOrdCom(){
        intNumDoc = 0;
        intNumOrdCom = 0;
        try{
            if (objTooBar.getEstado()=='n'){                        
                intNumOrdCom=objTipDoc.getne_ultdoc()+1;
                txtNumDoc.setText("" + intNumOrdCom);
            }
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
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
                                mapPar.put("codDoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                
                                //mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }}
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    


}