/*
 * ZafMae82.java    chkEsItmSer
 * Esta clase sirve para almacenar los registros de inventario  System.out
 * Created on 11 de octubre de 2004, 9:20   tbm_invmae
 */
     
package Maestros.ZafMae82;
  
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat; 
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import java.sql.Connection; 
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;    
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Calendar; 
import java.util.Date;
import java.text.SimpleDateFormat;
////import Librerias.ZafVenCon.ZafVenCon;  //**************************
import Librerias.ZafVenConGP.ZafVenConGP;  //**************************
import java.util.ArrayList;  //*******************
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafUtilGP.ZafUtilGP;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.util.Random;
import javax.swing.JTextField;
import Librerias.ZafKon.ZafKon;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author  kcerezo
 * corregido por jsalazar date: 12/Oct/2005
 * ultima actualizacion : 16/Ene/2006
 */

public class ZafMae82 extends javax.swing.JInternalFrame {
    Connection conCab, conRemGlo=null,conRemGloGP=null;       //Variable para conexiÃ³n a la Base de Datos
    Statement stmCab,stmLocG;        //Variable para ejecuciÃ³n de sentencias SQL
    ResultSet rstCab,rstLocG;        //Variable para manipular registro de la tabla en ejecuciÃ³n
    Librerias.ZafInventario.ZafInventario objInventario;
    String strSql;        //Variable de tipo cadena en la cual se almacenarÃ¡ el Query 
    String strSqlAux;     //Variable de tipo cadena en la cual se almacenarÃ¡ el Query Auxiliar
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    String strFil;        //Filtro para almacenar el criterio de bÃºsqueda actual
    String strAux;        //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    String strPat;        //PatrÃ³n
    String strFec;        //Fecha formateada de acuerdo al patrÃ³n
    boolean blnCmb = false; //Detecta si se realizaron cambios en el documento dentro de las caja de texto
    boolean blnCln = false; //Si los TextField estan vacios
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje
    ToolBar objTooBar;       //Objeto de tipo ToolBar para poder manipular la clase ZafToolBar
    ZafUtil objUti;       //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    ZafParSis objZafParSis;  //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    Calendar objCal;      //Objeto que me permitira obtener la fecha del sistema  
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
    LisTxt objLisCmb;     //Instancia de clase que detecta cambios
    javax.swing.JInternalFrame jfrThis;   
    private final String VERSION = "v2.5"; 
    Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla, objTblModPrv;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCla, objTblCelRenButPrv;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblCelRenChkP,objTblCelRenChkA, objTblCelRenChkI, objTblRenChkClaA, objTblRenChkClaI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblCelEdiChkP,objTblCelEdiChkA, objTblCelEdiChkI, objTblEdiChkClaA, objTblEdiChkClaI;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv; 
    
    ZafVenConGP objVenConItm; //*****************  
    ZafVenConGP objVenConUni; //*****************  
    ZafVenConGP objVenConMar; //*****************  
    ZafVenConGP objVenConPrv; //*****************
    ZafVenConGP objVenConMat; //*****************     
    ZafVenConGP objVenConRefGen; //*****************     
    ZafVenConGP objVenConRefDeta; //***************** 
    ZafVenConGP objVenConLinea; //***************** 
    ZafVenConGP objVenConClase; //*****************    
    ZafVenConGP objVenConCompra; //***************** 
    ZafVenConGP objVenConCategoria; //***************** 
    ZafVenConGP objVenControl; //*****************     
    ZafVenConGP objVenABCTuval; //*****************       
    ZafVenConGP objVenABCFre; //*****************       
    ZafVenConGP objVenABCPeso; //*****************       
    ZafVenConGP objVenABCValor; //*****************       
    ZafVenConGP objVenABCClas; //*****************       
    ZafVenConGP objVenConCategoriaCompra; //*****************     
    ZafVenConGP objVenConLineaCompra; //***************** 
    ZafVenConGP objVenConClaseCompra; //***************** 
     
 
    int intEst=0;
    String strItm="";
         
    private String
    strCodLin="", strDesLarLin="",
    strCodGrp="", strDesLarGrp="",
    strCodMar="", strDesLarMar="", 
    strCodUni="", strDesLarUni="",  
    strCodMat="", strDesLarMat="", 
    strCodRefG="", strDesLarRefG="",
    strCodRefD="", strDesLarRefD="",
    strCodCompr="", strDesLarCompr="",
    strCodCategoria="", strDesLarCategoria="",
    strCodControl="", strDesLarControl="",
    strCodAbcTUV="", strDesLarAbcTUV="",
    strCodAbcFRE="", strDesLarAbcFRE="",
    strCodAbcPESO="", strDesLarAbcPESO="",
    strCodAbcVALOR="", strDesLarAbcVALOR="",
    strCodAbcCLAS="", strDesLarAbcCLAS="",
    strCodCategoriaCompra="", strDesLarCategoriaCompra="",
    strCodLineaCompra="", strDesLarLineaCompra="",
    strCodClaseCompra="", strDesLarClaseCompra="";
            

    int INTCODREGCEN=0;
    int INTVERCONCEN=0;

   // DATOS DE LA TABLA DE CLASIFICACIONES
   final int INT_TBL_CLALIN=0;
   final int INT_TBL_CLACOD=1;
   final int INT_TBL_CLABUT=2;
   final int INT_TBL_CLANOM=3;
   final int INT_TBL_CLAACT=4;
   final int INT_TBL_CLAINA=5;
   final int INT_TBL_CODREGCLA=6;
   final int INT_TBL_CODGRP=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODCLA   = 0;
   //****************************

   // DATOS DE LA TABLA DE PROVEEDORES
   final int INT_TBL_PRVLIN=0;
   final int INT_TBL_PRVCODCLI=1;
   final int INT_TBL_PRVBUTCLI=2;
   final int INT_TBL_PRVNOMCLI=3;
   final int INT_TBL_PRVACT=4;
   final int INT_TBL_PRVINA=5;
   final int INT_TBL_PRVPRE=6;
   final int INT_TBL_CODREGPRV=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODPRV   = 0;
   //****************************
   private ZafHisTblBasDat objHisTblBasDat;
   private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
   
   private ZafParSis objParSis;
   private ArrayList arlDatITEMPRE,arlRegITEMPRE;
   private static final int INT_ARL_CON_QUERY=0;
   private ZafSelFec objSelFec;
   private String strFecDes,strFecHora;
   String strPatGP,strPatGPHora;        //PatrÃ³n
   String prefijo ="";
   
   private JTextField textField;
   private String bodegasExclui ="'ADMIN','COMP_LOCAL','CUENCA','RECLAM_TER','VENTAS'";
   ZafUtilGP objUtiGP;    
   private ZafHisTblBasDatGP objHisTblBasDatGP;                    //Histórico   
   private java.util.Date datFecAuxGP;
   private ZafKon objKon;                    //OBJJETO DE SISTEMA KONFIABLE      
   private boolean blnUsaKon=false;   
   

    /** Creates new form ZafMae82 */
    public ZafMae82(ZafParSis obj) {
        initComponents();
//       if(!isVersionActualizada()){
//            strMsg="Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..";
//            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
//        dispose();
//    }
////        
        addListenerCambios();
        
        
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = this;
            jfrThis.setTitle(objZafParSis.getNombreMenu()+ " " + VERSION);  
            lblTit.setText(objZafParSis.getNombreMenu() );    
            objTooBar = new ToolBar(this);
            objInventario= new Librerias.ZafInventario.ZafInventario(this, objZafParSis ); 
            objHisTblBasDat=new ZafHisTblBasDat();
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            objKon = new ZafKon(objZafParSis);
            objUti = new ZafUtil();
            objUtiGP=new ZafUtilGP(objZafParSis);              
            oppMsg = new JOptionPane();    
            txtCodAltAux.setVisible(false);
            txtStkMinAux.setVisible(false);
            txtStkMaxAux.setVisible(false);
            txtCortaUniMed.setVisible(false);
            
            
            
            lblMat2.setVisible(false);
            txtCodCategoria.setVisible(false);                     
            txtCategoria.setVisible(false);
            butCategoria.setVisible(false);
            lblRefG.setVisible(false);
            txtCodLinea.setVisible(false);
            txtLinea.setVisible(false);
            butLinea.setVisible(false);
            lblRefD.setVisible(false);
            txtCodCla.setVisible(false);
            txtCla.setVisible(false);
            butCla.setVisible(false);        
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            txaObs2.setLineWrap(true);
            txaObs2.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            deshabilitarDatInv();
            txtNomItm.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtCosUni.setEnabled(false);
            txtCodItm.setEnabled(false);
            txtStkAct.setEnabled(false);
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
            Ocultar_MostrarCampos(false);

            /**Alineando las cajas de texto que contendran valores numericos*/
            txtCodItm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkAct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodLin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodUniMed.setHorizontalAlignment(javax.swing.JTextField.RIGHT); 
            txtItmUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodItm.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            txtCodAlt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            txtCodUniMed.setHorizontalAlignment(javax.swing.JTextField.LEFT);

           
            this.getContentPane().add(objTooBar, "South");

            strPat = "dd/MM/yyyy";
            SimpleDateFormat sdfFec = new SimpleDateFormat (strPat);
            Date datFec = new Date();
            strFec = sdfFec.format(datFec);
            txtCosUni.setEnabled(false);
            txtCosUni1.setEnabled(false);
            txtCosUni2.setEnabled(false);
            txtCosUni3.setEnabled(false);
            this.setBounds(10,10, 700, 450);
            blnUsaKon=objKon.obtenerParaKon();
            
            //System.out.println(strFec);
     }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }        
    }

private boolean isVersionActualizada()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQL="";
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objZafParSis.getCodigoMenu();
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    System.out.println("Version " + VERSION);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(VERSION.equals(rstLoc.getString("tx_ver"))){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
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

    
  private void Ocultar_MostrarCampos(boolean estado)
  {
      panItmSer1.setVisible(estado);
      panIva.setVisible(estado);
      lblLin.setVisible(estado);
      txtCodLin.setVisible(estado);
      
      txtLin.setVisible(estado);
      butLin.setVisible(estado);
      lblgrp.setVisible(estado);
      txtCodGrp.setVisible(estado);     
      
      txtGrp.setVisible(estado);
      butGrp.setVisible(estado);
      lblItmUni.setVisible(estado);
      txtItmUni.setVisible(estado);
      chkCamPreVta.setVisible(estado);
      panItmSer.setVisible(estado);
  }
    
private boolean configurarVenConProveedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_cli");
        arlCam.add("tx_nom");
        arlCam.add("tx_dir");
        arlCam.add("tx_ide");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Prv.");
        arlAli.add("Dirección");
        arlAli.add("RUC/CI");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("190");
        arlAncCol.add("220");
        arlAncCol.add("80");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL+="select trim (VENDORID) as co_cli, trim (VENDNAME) as tx_nom , trim(ADDRESS1) as tx_dir, trim (VENDORID) as tx_ide  "
        + "FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..PM00200";        

        objVenConPrv=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
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

public boolean abrirConRem(){
    boolean blnres=false;
    try{
        int intIndEmp=INTCODREGCEN;
        if(intIndEmp != 0){
            conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
            conRemGlo.setAutoCommit(false);
        }else {
            conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            conRemGlo.setAutoCommit(false);
        }
        blnres=true;
    }
    catch (java.sql.SQLException e) {
        mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
        INTVERCONCEN=1;
        return false;
    }
    return blnres;
}

//////    public boolean abrirConRemGP( int tipo)
//////    {
//////        boolean blnres=false;
//////        try{
//////                if (tipo==1)
//////                {
//////                    conRemGloGPUno=DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
//////                    conRemGloGPUno.setAutoCommit(false);
//////                }
//////                else
//////                {
//////                    conRemGloGP=DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
//////                    conRemGloGP.setAutoCommit(false);                    
//////                }
//////            blnres=true;
//////        }
//////        catch (java.sql.SQLException e) {
//////            mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
//////            INTVERCONCEN=1;
//////            return false;
//////        }
//////        return blnres;
//////    }

public boolean abrirConRemGP(){
    boolean blnres=false;
    try{

            conRemGloGP=DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            conRemGloGP.setAutoCommit(false);
        blnres=true;
    }
    catch (java.sql.SQLException e) {
        mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
        INTVERCONCEN=1;
        return false;
    }
    return blnres;
}



private void mostrarMsg(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
}


    
    
    public ZafMae82(ZafParSis obj,String strcoditm) {
        initComponents();
        addListenerCambios();
      try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = this;
            jfrThis.setTitle(objZafParSis.getNombreMenu()+ VERSION);        
            objTooBar = new ToolBar(this);
            objUti = new ZafUtil();
            oppMsg = new JOptionPane();    
            txtCodAltAux.setVisible(false);
            txtStkMinAux.setVisible(false);
            txtStkMaxAux.setVisible(false);
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            txaObs2.setLineWrap(true);
            txaObs2.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            deshabilitarDatInv();
            txtNomItm.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtCosUni.setEnabled(false);
            txtCodItm.setEnabled(false);
            txtStkAct.setEnabled(false);

            /**Alineando las cajas de texto que contendran valores numericos*/
            txtCodItm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkAct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodLin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodUniMed.setHorizontalAlignment(javax.swing.JTextField.RIGHT); 
            txtItmUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

            this.getContentPane().add(objTooBar, "South");

            intEst=1;
            txtCodItm.setText(""+strcoditm);
            
            strPat = "dd/MM/yyyy";
            SimpleDateFormat sdfFec = new SimpleDateFormat (strPat);
            Date datFec = new Date();
            strFec = sdfFec.format(datFec);
            txtCosUni.setEnabled(false);
            txtCosUni1.setEnabled(false);
            txtCosUni2.setEnabled(false);
            txtCosUni3.setEnabled(false);
            this.setBounds(10,10, 700, 450);
            
            if(objZafParSis.getCodigoMenu()==424){
                objTooBar.setVisible(false);
            }
     }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }        
    }
   
    
    
    
    public void Configura_ventana_consulta(){
         configurarVenConItem();//CAMBIADO
         configurarVenConUnidad();//CAMBIADO
         configurarVenConMaterial();//chiqui
         configurarVenConRefGene();//chiqui
         configurarVenConRefDeta();//chiqui
         configurarVenConMarca();//MODIFICADO
         configurarVenConProveedor();//MODIFICADO
         configurarVenConLinea();//MODIFICADO     
         configurarVenConClase();//MODIFICADO    
         configurarVenConCompras();//MODIFICADO    
         configurarVenConCategoria();
         configurarVenConCategoriaCompra();
         configurarVenConLineaCompra();
         configurarVenConClaseCompra();
         configurarVenConControl();
         configurarVenConABC();
//////         configurarVenConABCTuval();
//////         configurarVenConABCFrecuencia();
//////         configurarVenConABCPeso();
//////         configurarVenConABCValorVenta();
//////         configurarVenConABCClase();
     }
    private boolean configurarVenConABC()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_nivpar");
            arlCam.add("tx_nivpar");
            arlCam.add("nd_porautubi");
            arlCam.add("st_reg");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripcion");
            arlAli.add("Por.Aut.");
            arlAli.add("Estado.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");


            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
             strSQL+="WHERE  st_reg='A' "; 

            objVenABCTuval=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCFre=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCValor=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCClas=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCPeso=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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

////////    private boolean configurarVenConABCFrecuencia()
////////    {
////////        boolean blnRes=true;
////////        try
////////        {
////////            ArrayList arlCam=new ArrayList();
////////            arlCam.add("co_nivpar");
////////            arlCam.add("tx_nivpar");
////////            arlCam.add("nd_porautubi");
////////            arlCam.add("st_reg");
////////
////////            ArrayList arlAli=new ArrayList();
////////            arlAli.add("Código");
////////            arlAli.add("Descripcion");
////////            arlAli.add("Por.Aut.");
////////            arlAli.add("Estado.");
////////
////////            ArrayList arlAncCol=new ArrayList();
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////
////////
////////            //Armar la sentencia SQL.
////////             String  strSQL="";
////////             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
////////             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
////////             strSQL+="WHERE  st_reg='A' "; 
////////
////////            objVenABC=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
////////            arlCam=null;
////////            arlAli=null;
////////            arlAncCol=null;
////////
////////        }
////////        catch (Exception e)
////////        {
////////            blnRes=false;
////////            objUti.mostrarMsgErr_F1(this, e);
////////        }
////////        return blnRes;
////////    } 
////////private boolean configurarVenConABC()
////////      {
////////        boolean blnRes=true;
////////        try
////////        {
////////            ArrayList arlCam=new ArrayList();
////////            arlCam.add("co_nivpar");
////////            arlCam.add("tx_nivpar");
////////            arlCam.add("nd_porautubi");
////////            arlCam.add("st_reg");
////////             
////////            ArrayList arlAli=new ArrayList();
////////            arlAli.add("Código");
////////            arlAli.add("Descripcion");
////////            arlAli.add("Por.Aut.");
////////            arlAli.add("Estado.");
////////            
////////            ArrayList arlAncCol=new ArrayList();
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            
////////              
////////            //Armar la sentencia SQL.
////////             String  strSQL="";
////////             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
////////             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
////////             strSQL+="WHERE  st_reg='A' "; 
////////             
////////            objVenABC=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
////////            arlCam=null;
////////            arlAli=null;
////////            arlAncCol=null;
////////            
////////        }
////////        catch (Exception e)
////////        {
////////            blnRes=false;
////////            objUti.mostrarMsgErr_F1(this, e);
////////        }
////////        return blnRes;
////////    } 
////////private boolean configurarVenConABC()
////////      {
////////        boolean blnRes=true;
////////        try
////////        {
////////            ArrayList arlCam=new ArrayList();
////////            arlCam.add("co_nivpar");
////////            arlCam.add("tx_nivpar");
////////            arlCam.add("nd_porautubi");
////////            arlCam.add("st_reg");
////////             
////////            ArrayList arlAli=new ArrayList();
////////            arlAli.add("Código");
////////            arlAli.add("Descripcion");
////////            arlAli.add("Por.Aut.");
////////            arlAli.add("Estado.");
////////            
////////            ArrayList arlAncCol=new ArrayList();
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            
////////              
////////            //Armar la sentencia SQL.
////////             String  strSQL="";
////////             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
////////             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
////////             strSQL+="WHERE  st_reg='A' "; 
////////             
////////            objVenABC=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
////////            arlCam=null;
////////            arlAli=null;
////////            arlAncCol=null;
////////            
////////        }
////////        catch (Exception e)
////////        {
////////            blnRes=false;
////////            objUti.mostrarMsgErr_F1(this, e);
////////        }
////////        return blnRes;
////////    } 
////////private boolean configurarVenConABC()
////////    {
////////        boolean blnRes=true;
////////        try
////////        {
////////            ArrayList arlCam=new ArrayList();
////////            arlCam.add("co_nivpar");
////////            arlCam.add("tx_nivpar");
////////            arlCam.add("nd_porautubi");
////////            arlCam.add("st_reg");
////////             
////////            ArrayList arlAli=new ArrayList();
////////            arlAli.add("Código");
////////            arlAli.add("Descripcion");
////////            arlAli.add("Por.Aut.");
////////            arlAli.add("Estado.");
////////            
////////            ArrayList arlAncCol=new ArrayList();
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            arlAncCol.add("50");
////////            
////////              
////////            //Armar la sentencia SQL.
////////             String  strSQL="";
////////             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
////////             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
////////             strSQL+="WHERE  st_reg='A' "; 
////////             
////////            objVenABC=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
////////            arlCam=null;
////////            arlAli=null;
////////            arlAncCol=null;
////////            
////////        }
////////        catch (Exception e)
////////        {
////////            blnRes=false;
////////            objUti.mostrarMsgErr_F1(this, e);
////////        }
////////        return blnRes;
////////    } 

private boolean configurarVenConMarca()
    {
    boolean blnRes=true;
    try
    {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_reg");
        arlCam.add("tx_desCor");
        arlCam.add("tx_desLar");
        arlCam.add("st_reg");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Desc.Corta");
        arlAli.add("Desc.Larga");
        arlAli.add("Estado.");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("103");
        arlAncCol.add("160");
        arlAncCol.add("170");
        arlAncCol.add("110");


        //Armar la sentencia SQL.
         String  strSQL="";
         strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
         strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
         strSQL+="WHERE  Extender_Form_ID = 'MSW_MARCA' ";             

        objVenConMar=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
       
private boolean configurarVenConRefGene()//chiqui--se deshabilita 19012024
    {
    boolean blnRes=true;
    try
    {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_reg");
        arlCam.add("tx_desCor");
        arlCam.add("tx_desLar");
        arlCam.add("st_reg");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Desc.Corta");
        arlAli.add("Desc.Larga");
        arlAli.add("Estado.");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("103");
        arlAncCol.add("160");
        arlAncCol.add("170");
        arlAncCol.add("110");


        //Armar la sentencia SQL.
         String  strSQL="";
         strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
         strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
         strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_GENERAL' "; 

        objVenConRefGen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
        
      private boolean configurarVenConLinea()//chiqui--
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_LINEAS' "; 
             
            objVenConLinea=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    private boolean configurarVenConCompras()//chiqui--
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_COMPRAS' "; 
             
            objVenConCompra=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    
private boolean configurarVenConControl()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CONTROL' "; 
             
            objVenControl=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    
    private boolean configurarVenConCategoria()//chiqui--
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CATEGORIA' "; 
             
            objVenConCategoria=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    
    private boolean configurarVenConCategoriaCompra()//chiqui--
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CAT_COM' "; 
             
            objVenConCategoriaCompra=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    
    private boolean configurarVenConLineaCompra()//20052026
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_LIN_COM' "; 
             
            objVenConLineaCompra=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
    private boolean configurarVenConClaseCompra()//20052026--
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CLA_COM' "; 
             
            objVenConClaseCompra=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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

    

        private boolean configurarVenConClase()//chiqui--cambio a
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CLASE_SUB' "; 
             
            objVenConClase=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
        
      private boolean configurarVenConRefDeta()//chiqui
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_DETALL' "; 
             
             
           
            objVenConRefDeta=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
        
                
       private boolean configurarVenConMaterial()//chiqui
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_MATERIAL' ";             
           
            objVenConMat=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
       
    
    
        private boolean configurarVenConUnidad()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT a.DEX_ROW_ID as co_reg,trim(a.BASEUOFM) as tx_desCor,trim(a.UMSCHDSC) as tx_desLar,'A' as st_reg FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40201 AS a "; 
            objVenConUni=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);            
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
       
    
    
       private boolean configurarVenConItem()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_itm");
            arlCam.add("tx_codAlt");
            arlCam.add("tx_codAlt2");
            arlCam.add("tx_nomItm");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cod.Zafiro");
            arlAli.add("Cod.3L");
            arlAli.add("Nombre.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("300");
            //Armar la sentencia SQL. 
             String  strSQL="";
             strSQL+="SELECT trim(ITEMNMBR) as co_itm,trim(ITMSHNAM) as tx_codAlt ,trim(ITMGEDSC) as tx_codAlt2,trim(ITEMDESC) as tx_nomItm FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..iv00101 WHERE ITMGEDSC !=''"; 
            objVenConItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
       
 
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Maestro Inventario";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        strTit=" Sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
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
                blnCmb=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
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
        boolean blnRes=true;
        try
        {
            if (!cargarCabReg())            
            {
                MensajeInf("Error al cargar registro");
                blnCmb=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }  
    private BigDecimal bdePrec= BigDecimal.ZERO;
    private BigDecimal bdeMarUti= BigDecimal.ZERO;
    private int ne_codKon=-1;      
    
private boolean cargarCabReg() {
    boolean blnRes = false;

    try {
        if (rstLocG == null) {
            return false;
        }

        if (rstLocG.isBeforeFirst()) {
            if (!rstLocG.next()) {
                return false;
            }
        }

        if (rstLocG.isAfterLast()) {
            if (!rstLocG.previous()) {
                return false;
            }
        }

        txtCodAlt2.setText(getStr(rstLocG, "tx_codAlt2", ""));
        txtNomItm.setText(getStr(rstLocG, "tx_nomItm", ""));
        txtCosUni.setText(getStr(rstLocG, "nd_cosUni", ""));
        txtCosUni1.setText(getStr(rstLocG, "nd_preVta1", ""));
        txtStkAct.setText(getStr(rstLocG, "nd_stkAct", ""));

        spnPeso.setValue(new Double(rstLocG.getDouble("nd_pesitmkgr")));

        txtCodAlt.setText(getStr(rstLocG, "codZafiro", ""));
        txtCodItm.setText(getStr(rstLocG, "co_itmMae", ""));
        txtCodGP.setText(getStr(rstLocG, "co_itmMae", ""));

        lblusuing.setText(getStr(rstLocG, "usuariocrea", ""));
        lblusuing1.setText(getStr(rstLocG, "usuario", ""));

        txtCodTama.setText(getStr(rstLocG, "tamano", ""));

        if (rstLocG.getBoolean("estado")) {
            cboEst.setSelectedIndex(1);
        } else {
            cboEst.setSelectedIndex(0);
        }

        String strfec2 = String.valueOf(rstLocG.getDate("fecha"));
        if (strfec2.equalsIgnoreCase("null")) {
            strfec2 = "";
        }
        txtfecmod.setText(" " + strfec2);

        String strfec1 = String.valueOf(rstLocG.getDate("fechaCrea"));
        if (strfec1.equalsIgnoreCase("null")) {
            strfec1 = "";
        }
        txtfecing.setText(" " + strfec1);

        txtMar.setText(getStr(rstLocG, "marca", "NO TIENE"));
        txtCodMar.setText(getStr(rstLocG, "IDMarca", ""));

        txtMat.setText(getStr(rstLocG, "material", "NO TIENE"));
        txtCodMat.setText(getStr(rstLocG, "IDMaterial", ""));

        txtLinea.setText(getStr(rstLocG, "Linea", "NO_TIENE"));
        txtCodLinea.setText(getStr(rstLocG, "IDLinea", ""));

        txtCla.setText(getStr(rstLocG, "Clase", "NO_TIENE"));
        txtCodCla.setText(getStr(rstLocG, "IDClase", ""));

        txtCodCompr.setText(getStr(rstLocG, "IDCompra", ""));
        txtCompr.setText(getStr(rstLocG, "Compra", ""));

        txtCodCategoria.setText(getStr(rstLocG, "IDCategoria", ""));
        txtCategoria.setText(getStr(rstLocG, "Categoria", "NO_TIENE"));

        txtCodCategoriaCompra.setText(getStr(rstLocG, "IDCategoriaCompra", ""));
        txtCategoriaCompra.setText(getStr(rstLocG, "CategoriaComp", "NO_TIENE"));

        txtCodClaseCompra.setText(getStr(rstLocG, "IDClaseCompra", ""));
        txtClaseCompra.setText(getStr(rstLocG, "ClaseComp", "NO_TIENE"));

        txtCodLineaCompra.setText(getStr(rstLocG, "IDLineaCompra", ""));
        txtLineaCompra.setText(getStr(rstLocG, "LineaComp", "NO_TIENE"));

        txtCodUniMed.setText(getStr(rstLocG, "codUnidad", ""));
        txtUniMed.setText(getStr(rstLocG, "tx_desCor", ""));
        txtCortaUniMed.setText(getStr(rstLocG, "tx_cortaUnid", ""));

        String strControl = getStr(rstLocG, "Control", "");
        if (strControl.equals("") || strControl.equals("0")) {
            txtCodControl.setText("");
            txtControl.setText("");
        } else {
            txtCodControl.setText(getStr(rstLocG, "IDControl", ""));
            txtControl.setText(strControl);
        }

        bdePrec = rstLocG.getBigDecimal("nd_prevta1");
        bdeMarUti = rstLocG.getBigDecimal("nd_marUti");

        txtCodAbcTUV.setText(getStr(rstLocG, "codABCT", ""));
        txtAbcTUV.setText(getStr(rstLocG, "txABCT", ""));

        txtCodAbcFRE.setText(getStr(rstLocG, "codABCF", ""));
        txtAbcFRE.setText(getStr(rstLocG, "txABCF", ""));

        txtCodAbcPES.setText(getStr(rstLocG, "codABCP", ""));
        txtAbcPES.setText(getStr(rstLocG, "txABCP", ""));

        txtCodAbcVAL.setText(getStr(rstLocG, "codABCV", ""));
        txtAbcVAL.setText(getStr(rstLocG, "txABCV", ""));

        txtCodAbcCLA.setText(getStr(rstLocG, "codABCC", ""));
        txtAbcCLA.setText(getStr(rstLocG, "txABCC", ""));

        int intPosRel = rstLocG.getRow();

        rstLocG.last();
        objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstLocG.getRow());
        rstLocG.absolute(intPosRel);

        blnCmb = false;
        blnRes = true;
    } catch (java.sql.SQLException e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}

private String getStr(java.sql.ResultSet rs, String campo, String valorDefault) throws java.sql.SQLException {
    String val = rs.getString(campo);
    if (val == null) {
        return valorDefault;
    }
    return val.trim();
}


    /**
     * JM: Validacion para saber que se esta tratando de modificar el terminal de un item. Solicitada por el Ing. Eddye el 12 de Dic del 2019
     * @return 
     */

    private boolean isModificandoTerminal()
    {
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT tx_codAlt   \n";
                strSql+=" FROM tbm_inv as a1  \n";
                strSql+=" WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_itm="+txtCodItm.getText()+"  \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(!rstLoc.getString("tx_codAlt").substring(rstLoc.getString("tx_codAlt").length()-1).equals(txtCodAlt.getText().substring(txtCodAlt.getText().length()-1)  )  ){
                        blnRes=true;
                    }  
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
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        catch (Exception e){   
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;
    }
    
    public class ToolBar extends ZafToolBar 
    {
    
        public ToolBar (javax.swing.JInternalFrame ifrTmp) {
            super (ifrTmp, objZafParSis);
        }
        public boolean beforeInsertar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            return true;
        }

        public boolean beforeEliminar()
        {
            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean anular()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if(itemSinStock()){
                if (!anularReg())
                return false;
            }else{
                MensajeInf("El Item que desea anular posee Stock.\nFavor revisar y volver a intentarlo.");
                return false;
            }
            
            objTooBar.setEstadoRegistro("Anulado");
            blnCmb=false;
            return true;
        }

        public void clickAnterior(){
            try
            {
                if (!rstLocG.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstLocG.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstLocG.previous();
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

        public void clickFin() {
            try
            {
                if (!rstLocG.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstLocG.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstLocG.last();
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

        public void clickInicio(){
            try
            {
                if (!rstLocG.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstLocG.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstLocG.first();
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
                if (blnCmb)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstLocG.close();
                    stmLocG.close();
                    rstLocG=null;
                    stmLocG=null;
                }

                limpiarDatInv();
                habilitarDatInv();
                cboEst.setSelectedIndex(0);
                txtStkAct.setText("0");
                txtCodItm.setEditable(false);
////                butCod.setEnabled(false);CHIQUI
                chkIvaCom.setSelected(true);
                chkIvaVta.setSelected(true);
                txtCodAlt.requestFocus();
                blnCmb=false;
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

        public void clickSiguiente(){
            try
            {
                if (!rstLocG.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstLocG.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstLocG.next();
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
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar_ANTES()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
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
                    txtCln();
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }
        
         public boolean eliminar()//CHIQUI
        {
             int indicador=0;
            try
            {
                   indicador =ProcesoAnulacion();

                if (indicador ==1)
                    strMsg="Se ha realizado la anulación del item de forma exitosa.";
                if (indicador ==2)
                    strMsg="No se puede realizar la anulación del item , el item posse srock disponible.";
                if ((indicador ==3) ||(indicador ==-1))
                    strMsg="No se puede realizar la anulacion del item , el item posee alguna novedad.";
                
    
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);    
                
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    txtCln();
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }
                
        public void clickCancelar() {
            
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            txtCln();
            blnCmb=false;
            return true;
        }

        public boolean modificar_antes()
        {
            
            
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                deshabilitarDatInv();
                if (mensaje("Para modificar primero debe reactivar el item \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
                    habilitarDatInv();
                    return Reactivar();
                }

            }
            
            if(cboEst.getSelectedIndex()==1){
                /* QUIERE ANULAR */
                if(!itemSinStock()){
                    MensajeInf("El Item que desea anular posee Stock.\nFavor revisar y volver a intentarlo.");
                    return false;
                }
            }
            
                if (mensaje("Esta seguro que s desea modificar el Item .. \n ¿Desea continuar?")==JOptionPane.YES_OPTION)
                {
                     if (!actualizarReg())
                            return false;
                      blnCmb=false;
               }else return false;
            
          return true;
        }
        
        public boolean modificar()//chiqui
        {
            if (mensaje("Esta seguro que s desea modificar el Item .. \n ¿Desea continuar?")==JOptionPane.YES_OPTION)
            {
                if (!actualizarReg())
                   return false;
                blnCmb=false;
            }
            else return false;
            return true;
        }        
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnCmb)
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
                    rstCab=null;
                    stmCab=null;
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
            txtCln();
            limpiarDatInv();
            blnCmb=false;
            return blnRes;
        }
                   
        public boolean anularReg()
        {
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    con.setAutoCommit(false);
                    if(cambiarEstadoItemTodasLasEmpresas(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()), "I")){
                        con.commit();
                        con.close();
                    }
                    else{
                        con.rollback();
                        con.close();
                    }
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;            
      }        
        
        public void clickAceptar() 
        {
        }
        
        public void clickAnular() 
        {
        }
               
        public void clickConsultar() 
        {
            txtCodItm.setEditable(true);
            txtCodItm.setEnabled(true);
            habilitarDatInv();
            txtCln();
            limpiarDatInv();
        }
        
        public void clickEliminar() {
            
            deshabilitarDatInv();
            
            
                
        }
        
        private int ProcesoAnulacion()
        {
            java.sql.Connection conLocGP;
             java.sql.Connection conLoc;
            java.sql.Statement stmLocGP;  
            java.sql.Statement stmLoc;            
            int ndRes=-1;
            String strSQLGP="",strSQL="";
            int indicador=-1;
          
            try
            {
                conLocGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021()); 
                conLoc = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos()); 
                if (conLocGP!=null)
                {
                    stmLocGP=conLocGP.createStatement();
                    stmLoc=conLoc.createStatement();
                    conLocGP.setAutoCommit(false);
                    conLoc.setAutoCommit(false);
                        indicador =ConsultaItem();
                        if (indicador ==1)
                        {
                            strSQLGP="";
                            strSQLGP+="UPDATE "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101";
                            strSQLGP+=" SET INACTIVE=1";
                            strSQLGP+=" WHERE ITEMNMBR='"+ txtCodGP.getText() +"'";
                            System.out.println("UPDATE " + strSQLGP);     
                            stmLocGP.executeUpdate(strSQLGP);
                            
                            strSQL="";
                            strSQL+="UPDATE tbm_inv";
                            strSQL+=" SET st_reg='I'";
                            strSQL+=" WHERE tx_codalt2='"+ txtCodAlt2.getText() +"'";
                            System.out.println("UPDATE " + strSQL);     
                            stmLoc.executeUpdate(strSQL);
                            ndRes=1;//todo ok
                            conLoc.commit();
                            conLocGP.commit();
                            
                        }
                        else
                        {
                             if (indicador ==2)
                                 
                                 ndRes=2;//no se puede
                             else
                             {
                                 if (indicador ==3)
                                      ndRes=3;//no existe
                                 else
                                     ndRes=-1;//error
                             }
                            conLoc.rollback();
                            conLocGP.rollback();                             
                        }
                    stmLocGP.close();
                    stmLocGP=null;
                    stmLoc.close();
                    stmLoc=null;                    
                }
                conLocGP.close();
                conLocGP=null;    
                conLoc.close();
                conLoc=null;                  
            }
            catch (java.sql.SQLException e)
            {
                ndRes=-1;
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                ndRes=-1;
                objUti.mostrarMsgErr_F1(this, e);
            }


                return ndRes;
        }
        
        private int ConsultaItem()
        {
            boolean blnResult =false;
            java.sql.Connection conLocGP;
            java.sql.ResultSet rstLocGP;
            java.sql.Statement stmLocGP;
            String strSQL="";
            double cantidad=0;
            boolean blnExiste=false;
            int ndRes=-1;
            try{
                conLocGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
                //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if(conLocGP!=null){
                    stmLocGP = conLocGP.createStatement();
                    strSQL="";
                    strSQL+=" SELECT iv02.total as stock,iv01.ITEMNMBR as stock from "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01";
                    strSQL+="   left outer join  ( select sum(QTYONHND) as total ,ITEMNMBR as item ";
                    strSQL+="   from tuval..IV00102 group by ITEMNMBR) as iv02 on (iv01.ITEMNMBR =iv02.item) ";
                    strSQL+=" where iv01.ITEMNMBR ='"+ txtCodGP.getText() +"'";                    
                    rstLocGP = stmLocGP.executeQuery(strSQL);
                    if(rstLocGP.next()){
                        cantidad =rstLocGP.getDouble("stock");
                         if(cantidad ==0)
                             ndRes=1;//se puede
                         else
                             ndRes=2;//no se puede
                         blnExiste=true;
                    }
                    if (blnExiste==false)
                        ndRes=3;//no existe
                    rstLocGP.close();
                    rstLocGP=null;
                    stmLocGP.close();
                    stmLocGP=null;
                    conLocGP.close();
                    conLocGP=null;
                }

            }
            catch(java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                ndRes=-1;//error
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                ndRes=-1;
            }

                return ndRes;
        }
       
        public void clickImprimir() {
        }
        
        public void clickModificar() {
            habilitarDatInv();
            txtCodItm.setEnabled(false);
            txtCodAlt.requestFocus();
            chkIvaCom.setSelected(true);
            chkIvaVta.setSelected(true);
            
            chkEsItmSer.setEnabled(false);
            chkEsItmTra.setEnabled(false);
            chkEsItmOtr.setEnabled(false);
        }
        
        public void clickVisPreliminar() 
        {
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
             consultarReg();
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
    
    public boolean actualizarReg() 
    {
        return modificarDatInvGP();
    }

    public boolean insertarReg() 
    {
    boolean blnRes=false;
    String item="";
    txtCln();
////       if(!isVersionActualizada()){
////            strMsg="Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..";
////            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////         dispose();
////       }
////       else
////       {
    if (blnCln) 
    {
        if(validadCampos())
        {
            if(insertarDatReg())
            {   
                blnRes=true;
                java.sql.ResultSet rstLoc;
                java.sql.Statement stmLoc;
                java.sql.Connection conLocEXT;
                String values="",cabecera="",strSQL="";
                try
                {
                  conLocEXT = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
                  if(InsersionIV00102(conLocEXT,2))
                  {
                      if(InsersionEXT01101(conLocEXT))
                         conLocEXT.commit();
                      else
                         conLocEXT.rollback();     
                  }
                  else
                      conLocEXT.rollback();

                  item = "'"+ txtCodGP.getText() + "'";
////////////////                  if(objUtiGP.ejecucionActualizacionAtributo(item))
////////////////                  {
////////////////                    strMsg="Se realizo la actualizacion del atributo.";
////////////////                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////////////////                  }
////////////////                  else
////////////////                  {
////////////////                    strMsg="NO se realizo la actualizacion del atributo.";
////////////////                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);                          
////////////////                  }                          
                }    
                catch(java.sql.SQLException e)
                {
                    objUti.mostrarMsgErr_F1(this, e);
                    blnRes=false;
                }
                catch(Exception e)
                {
                    objUti.mostrarMsgErr_F1(this, e);
                    blnRes=false;
                }
            }
            else
            {
                strMsg="No se ha realizado ningún cambio que se pueda guardar.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            }                         
        }
    }
    else 
    {
        strMsg="No se ha realizado ningún cambio que se pueda guardar.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    } 
////       }
    return blnRes;
} 

    private boolean InsersionEXT01101(java.sql.Connection conEXT)//CHIQUI -PARA LA INSERCION DE MARCA-MATERIAL-REF GENERAL Y DETALLADA--1101//28032026
    {
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values00="",cabecera00="",strSQL="",values01="",cabecera01="",strSQLGlo="";
        int ultimoId=0,indicador =0,ultimoIdSgte=0;
        String codExtender ="";
        
        try
        {
            //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                ultimoId=1;
                if (ultimoId !=0)
                {
                    strSQL="";
                    for (int i=0 ;i<=11;i++)
                    {
                        
                        if (i==0)//para la marca
                        {
                             strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                             strSQL+=" VALUES ( \n";
                             strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 119 ,'"+ txtMar.getText() +"'); \n"; 
                        }
                        else
                        {
                            if (i==1)//para el material
                            {
                                 strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                 strSQL+=" VALUES ( \n";
                                 strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 120 ,'"+ txtMat.getText() +"'); \n"; 
                            }
                            else
                            {
                                if (i==2)//PARA LA REF GENEERAL-se cambio para lo que es la linea
                                {
                                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                    strSQL+=" VALUES ( \n";
                                    strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 196 ,'"+ txtLinea.getText() +"'); \n";                                          
                                }
                                else
                                {
                                    if (i==3)//PARA LA REF GENEERAL//PARA LA REF DETALLADA -se cambio para lo que es la clase
                                    {
                                        strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                        strSQL+=" VALUES ( \n";
                                        strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 197 ,'"+ txtCla.getText() +"'); \n";                                          
                                    }
                                    else
                                    {
                                        if(i==4)
                                        {
                                            strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                            strSQL+=" VALUES ( \n";
                                            strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 181 ,'"+ txtCodTama.getText() +"'); \n";                                             
                                        }
                                        else
                                        {
                                            if(i==5)
                                            {
                                                strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                strSQL+=" VALUES ( \n";
                                                strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 199 ,'"+ txtCompr.getText() +"'); \n";                                             
                                            }
                                            else
                                            {
                                                if(i==6)
                                                {
                                                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                    strSQL+=" VALUES ( \n";
                                                    strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 204 ,'"+ txtCategoria.getText() +"'); \n";                                             
                                                }                                                
                                                else
                                                {
                                                   if(i==7)
                                                   {
                                                        strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                        strSQL+=" VALUES ( \n";
                                                        strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 203 ,'"+ txtControl.getText() +"'); \n";                                             
                                                   }  
                                                   else
                                                   {
                                                       if(i==8)
                                                        {
                                                            codExtender =obtenerCodExtender(conEXT,txtCategoriaCompra.getText(),8);
                                                            strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                            strSQL+=" VALUES ( \n";
                                                            strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 206 ,'"+ codExtender +"'); \n";                                             
                                                        }      
                                                       else
                                                       {
                                                           if(i==9)
                                                            {
                                                                codExtender =obtenerCodExtender(conEXT,txtClaseCompra.getText(),9);
                                                                strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                strSQL+=" VALUES ( \n";
                                                                strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 208 ,'"+ codExtender +"'); \n";                                             
                                                            }   
                                                           else
                                                           {
                                                               if(i==10)
                                                                {
                                                                    codExtender =obtenerCodExtender(conEXT,txtLineaCompra.getText(),10);
                                                                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                    strSQL+=" VALUES ( \n";
                                                                    strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 207 ,'"+ codExtender +"'); \n";                                             
                                                                }  
                                                                else
                                                                {
                                                                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                    strSQL+=" VALUES ( \n";
                                                                    strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 121 ,'"+ prefijo +"'); \n";                                                                                                                                               
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
                    System.out.println("INSERT EXT01100-EXT01101" + strSQL);                     
                    blnRes=true;
                }
                else
                    blnRes=false;
                if (blnRes ==true)
                    stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;                    
            }
        }    
        catch(java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;        
    }
    
    private String obtenerCodExtender(java.sql.Connection conExt,String desext,int indicador)
    {
        String tx_desext="";

        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT trim(UD_Form_Field_ID) as codExt  ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
                if(indicador==8)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_CAT_COM' and UD_Form_Field_Desc = '"+ desext +"'";
                if(indicador==9)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_CLA_COM' and UD_Form_Field_Desc = '"+ desext +"'";          
                if(indicador==10)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_LIN_COM' and UD_Form_Field_Desc = '"+ desext +"'";                 
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    tx_desext=rstLoc.getString("codExt");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return tx_desext;
    }

    public boolean consultarReg() 
    {
   return consultarDatInvGP(filtarSql());
}
    
    private int mensaje()
    {
        strMsg = "¿Desea guardar los cambios efectuados a este registro?\n";
        strMsg+= "Si no guarda los cambios perderá toda la información que no haya guardado.";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
    
    private int mensaje(String strMsg)
    {
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
    
    public boolean txtCln()
    {
        if ((txtCodAlt.getText().equals("")) &&  (txtCodAlt2.getText().equals("")) && (txtNomItm.getText().equals("")) && (txtCodLin.getText().equals("")) && (txtCodMar.getText().equals("")) && (txtCodGrp.getText().equals("")) && (txtCodUniMed.getText().equals("")) && (txtItmUni.getText().equals("")) && (txtCosUni1.getText().equals("")) && (txtCosUni2.getText().equals("")) && (txtCosUni3.getText().equals("")) && (txtStkMin.getText().equals("")) && (txtStkMax.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
            blnCln = false;
        }
        else {
            blnCln = true;
        }
        return blnCln;
    }
        
    public void asignarDat()
    {
        try{ 
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            java.sql.Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
            java.sql.ResultSet rstAux;
            int intCodInv = 0;
                if(rstCab != null){

                    }
            blnCmb = false;
                }
        catch(SQLException Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

        catch(Exception Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
        }                       
    }        
    
    private boolean Reactivar()
    {
   try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    con.setAutoCommit(false);
                    if(cambiarEstadoItemTodasLasEmpresas(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()), "A")){
                        con.commit();
                        con.close();
                    }else{
                        con.rollback();
                        con.close();
                    }
                    objTooBar.setEstadoRegistro("Activo");
                    cboEst.setSelectedIndex(0);
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;        
    }
    
    private boolean InsersionGP()
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="";
        
        try
        {
            strPatGP = "yyyy-MM-dd";
            strPatGPHora = "HH:mm:ss";
            SimpleDateFormat sdfFecGP = new SimpleDateFormat (strPatGP);
            SimpleDateFormat sdfFecGPHora = new SimpleDateFormat (strPatGPHora);
            Date datFecGP = new Date();
            Date datFecGPHora = new Date();
            strFecDes = sdfFecGP.format(datFecGP);
            strFecHora = sdfFecGPHora.format(datFecGPHora);
            
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());            
            if(conLoc!=null)
            {
                conLoc.setAutoCommit(false);
                
                if(InsersionIV00101(conLoc))
                {
                    if(InsersionIV00106(conLoc))
                    {
                        if(InsersionIV00105(conLoc))
                        {
                            if(InsersionIV00102(conLoc,1))
                            {
                                if(InsersionIV00107(conLoc))
                                {
                                    if(InsersionIV00108(conLoc))
                                    {
                                        if(InsersionIV00400(conLoc))
                                        {
                                            if(InsersionEXT01100_EXT01103(conLoc))
                                            {
                                                if(InsersionEXT01101_EXT01100(conLoc))
                                                {
                                                    if(InsersionTmbTmhInv(conLoc))
                                                    {
                                                       if(InsersionABC(conLoc))
                                                       {
                                                            if (blnUsaKon)
                                                            {
                                                                if(insertarCreaModiItmKON(1,conLoc))
                                                                {
                                                                    conLoc.commit();
                                                                    blnRes=true;                                                                 
                                                                }
                                                                else
                                                                {
                                                                    MensajeInf("Problemas al crear codigo de item, en el maestro de tbm_inv:KON");
                                                                    blnRes=false;
                                                                    conLoc.rollback();                                                                
                                                                }
                                                            }                                                        
                                                            else
                                                            {
                                                                conLoc.commit();
                                                                blnRes=true;                                                            
                                                            }                                                           
                                                           
                                                       }
                                                        

                                                    }
                                                    else
                                                    {
                                                      MensajeInf("Problemas al crear codigo de item, en el maestro de tbm_inv:GP");
                                                      blnRes=false;
                                                      conLoc.rollback();
                                                    }
                                                }
                                                else
                                                {
                                                    MensajeInf("Problemas al crear codigo de item, en el maestro de marca-modelo-referencias:encabezado en GP");
                                                    blnRes=false;
                                                    conLoc.rollback();                                                    
                                                }
                                            }
                                            else
                                            {
                                                MensajeInf("Problemas al crear codigo de item, en el maestro de articulo-peso:peso en GP");
                                                blnRes=false;
                                                conLoc.rollback();
                                            }
                                        }
                                        else
                                        {
                                            MensajeInf("Problemas al crear codigo de item, en el maestro de articulos:encabezado en GP");
                                            blnRes=false;
                                            conLoc.rollback();
                                        }
                                    }
                                    else
                                    {
                                        MensajeInf("Problemas al crear codigo de item, en el maestro de lista de precio:precio general en GP");
                                        blnRes=false;
                                        conLoc.rollback();
                                    }                                        
                                }
                                else
                                {
                                    MensajeInf("Problemas al crear codigo de item, en el maestro de lista de precio:precio paralelo en GP,");
                                    blnRes=false;
                                    conLoc.rollback();
                                }
                            }
                            else
                            {
                                MensajeInf("Problemas al crear codigo de item, en el maestro de articulo-bodega-tipo1:bodega en GP,");
                                blnRes=false;
                                conLoc.rollback();
                            }
                        }
                        else
                        {
                            MensajeInf("Problemas al crear codigo de item, en el maestro de articulos:moneda en GP");
                            blnRes=false;
                            conLoc.rollback();
                        }
                    }
                    else
                    {
                        MensajeInf("Problemas al crear codigo de item, en el maestro de articulos:compras en GP");
                        blnRes=false;
                        conLoc.rollback();
                    }
                }
                else
                {
                    MensajeInf("Problemas al crear codigo de item, en el maestro de articulos:GP");
                    blnRes=false;
                    conLoc.rollback();
                }
            }
            else
            {
                MensajeInf("Problemas al conectar con GP");
                blnRes=false;
                conLoc.rollback();
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

    private boolean InsersionEXT01100_EXT01103(java.sql.Connection conEXT)//CHIQUI -PARA LA INSERCION DE PESO DEL ITEM
    {
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values00="",cabecera00="",strSQL="",values03="",cabecera03="",strSQLGlo="";
        int ultimoId=0;
        
        try
        {
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                ultimoId=getObtenerUltimoIdPesoGP(conEXT) +1 ;
                if (ultimoId !=0)
                {
                    strSQL="";
                    values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"',0,'"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";
                    cabecera00 ="(Extender_Record_ID, Extender_Window_ID, Extender_Key_Values_1, NOTEINDX,MODIFDT,MDFUSRID,CREATDDT,CRUSRID)";
                    strSQL=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100  "+ cabecera00 +" VALUES "+ values00 +"\n";
                    values03 ="('"+ ultimoId +"',179 ,"+ spnPeso.getModel().getValue().toString() +",0);";
                    cabecera03 ="(Extender_Record_ID, Field_ID,  TOTAL,FUNCTAMT)";
                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01103  "+ cabecera03 +" VALUES "+ values03 +"\n";
                    
                    System.out.println("INSERT EXT01100-EXT01103" + strSQL);                     
                    blnRes=true;
                }
                else
                    blnRes=false;
                if (blnRes ==true)
                     stmLoc.executeUpdate(strSQL);
               
                stmLoc.close();
                stmLoc=null;                    
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

    private boolean InsersionEXT01101_EXT01100(java.sql.Connection conEXT)//CHIQUI -PARA LA INSERCION DE MARCA-MATERIAL-REF GENERAL Y DETALLADA
    {
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values00="",cabecera00="",strSQL="",values01="",cabecera01="",strSQLGlo="";
        int ultimoId=0,indicador =0,ultimoIdSgte=0;
        
        try
        {
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                ultimoId=getObtenerUltimoIdPesoGP(conEXT) +1 ;
                if (ultimoId !=0)
                {
                    strSQL="";
                    for (int i=0 ;i<=4;i++)
                    {

                        if (i==0)//para la marca
                        {
////                            values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";
////                            values01 ="('"+ ultimoId +"',119 ,'"+ txtMar.getText() +"' );";
                        }
                        else
                        {
                            if (i==1)//para el material
                            {
////                                values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";
////////                                values01 ="('"+ ultimoId +"',120 ,'"+ txtMat.getText() +"' );";
////                                                    cabecera00 ="(Extender_Record_ID, Extender_Window_ID, Extender_Key_Values_1, MODIFDT,MDFUSRID,CREATDDT,CRUSRID)";
////                        strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100  "+ cabecera00 +" VALUES "+ values00 +"\n"; 
                            }
                                
                            else
                            {
                                if (i==2)//PARA LA REF GENEERAL
                                {
                                    ultimoIdSgte=ultimoId+1;
                                    values00 ="('"+ ultimoIdSgte+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
//                                    values01 ="('"+ ultimoIdSgte +"',117 ,'"+ txtRefG.getText() +"' );";
                                                    cabecera00 ="(Extender_Record_ID, Extender_Window_ID, Extender_Key_Values_1, MODIFDT,MDFUSRID,CREATDDT,CRUSRID)";
                                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100  "+ cabecera00 +" VALUES "+ values00 +"\n"; 
                                }
                                    
                                else
                                {
                                    if (i==3)//PARA LA REF GENEERAL//PARA LA REF DETALLADA
                                    {
                                        ultimoIdSgte=ultimoId+1;
////                                        values00 ="('"+ ultimoIdSgte+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
////                                        values01 ="('"+ ultimoIdSgte +"',118,'"+ txtRefD.getText() +"' );";  
                                    }
                                        
                                    else
                                    {
                                        if(i==4)
                                        {
////                                            values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                        
////                                            values01 ="('"+ ultimoId +"',181,'"+ txtCodTama.getText() +"' );";  
                                        }
                                        else
                                        {
////                                            values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ prefijo +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                        
////                                            values01 ="('"+ ultimoId +"',181,'"+ txtCodTama.getText() +"' );";                                         
                                        }    
                                    }
                                        
                                }
                                
                            }
                        }
                        
       
                        
////                        cabecera01 ="(Extender_Record_ID, Field_ID,  STRGA255)";
////                        strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  "+ cabecera01 +" VALUES "+ values01 +"\n";        
                
                    }
////////                    ultimoId=ultimoId+1;
////////                    for (int i=0 ;i<=4;i++)
////////                    {
////////                        if (i==2)//PARA LA REF GENEERAL
////////                        {
////////                            values00 ="('"+ ultimoId+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"',0,'"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
////////                            values01 ="('"+ ultimoId +"',117 ,'"+ txtRefG.getText() +"' );";
////////                        }
////////                        if (i==3)//PARA LA REF GENEERAL//PARA LA REF DETALLADA
////////                        {
////////                            values00 ="('"+ ultimoId+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"',0,'"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
////////                            values01 ="('"+ ultimoId +"',118,'"+ txtRefD.getText() +"' );";  
////////                        }                        
////////                    }


                    
                    System.out.println("INSERT EXT01100-EXT01101" + strSQL);                     
                    blnRes=true;
                }
                else
                    blnRes=false;
                if (blnRes ==true)
                     stmLoc.executeUpdate(strSQL);
               
                stmLoc.close();
                stmLoc=null;                    
////                }
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

    private int getObtenerUltimoIdPesoGP(java.sql.Connection conExt)//CHIQUI -PARA OBTENER EL ULTIMPO ID DEL PESO EN EXT01100
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        int ultimoId=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT TOP 1  Extender_Record_ID  ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 ";
                strSQL+=" ORDER BY Extender_Record_ID DESC";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    ultimoId=rstLoc.getInt("Extender_Record_ID");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            ultimoId=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            ultimoId=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return ultimoId;
    }

///////////////////////////////////////////////////////////////////////////FIN CREACION DE PESO
    
    private boolean InsersionIV00101(java.sql.Connection conEXT)// CHIQUI -PARA LA CREACION DEL ITEM
    {
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="",unidadGP;
        int ultimoNOT =0;
        
        try
        {
           // conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                unidadGP =getObtenerUnidadMedidaGP(txtUniMed.getText(),conEXT);
                ultimoNOT =getObtenerUltimoNOTINDEX(conEXT);
                if (ultimoNOT !=0)
                {
                    if (unidadGP !="ERROR")
                    {


                         values ="('"+ txtCodGP.getText() +"', "+ objUti.codificar(txtNomItm.getText(),0) +", "+ ultimoNOT  +", '"+ txtCodAlt.getText() +"', 1, '"+txtCodAlt2.getText() +"', 0.00000, 0.00000, 0, 1, 5, 'VENTAS IVA 15%', 1, 55, 0, 287, 258, 258, "
                                 + "282, 0, 0, 0, 0, 55, 0, 0, 0, 0, 'VT-BNS-15  ', 1, '', 1, 1, 1, 1, 0, 3, "
                                 //+ "'"+ txtUniMed.getText() +"', '', '', '', '', '', '', '', '', 1, '"+ strFecDes +"', '"+ strFecDes +"', 0, 'GENERAL', '', 0, 0, 0, 0, 0, 0, '', '', 'GENERAL', 1, '"+ unidadGP +"','"+ unidadGP +"', 0, '', 1, 0, 0, 'COMP BIEN 15%', 1, 1, 0, '', 0, "
                                 + "'"+ txtUniMed.getText() +"', '', '', '', '', '', '', '', '', 1, '"+ strFecDes +"', '"+ strFecDes +"', 0, 'GENERAL', '', 0, 0, 0, 0, 0, 0, '', '', 'GENERAL', 1, '"+ unidadGP +"','"+ unidadGP +"', 0, '', 1, 0, 0, 'COMP BIEN 15%', 1, 1, 0, '', 0, "
                                 + "0, 0, 0, 0, 0, '', 0.00000, 0, 0, 0, 0, 55, '"+objZafParSis.getNombreUsuario()+"')";
                        strSQL="";
                         cabecera ="(ITEMNMBR, ITEMDESC, NOTEINDX, ITMSHNAM, ITEMTYPE, ITMGEDSC, STNDCOST, CURRCOST, ITEMSHWT, DECPLQTY, DECPLCUR, ITMTSHID, TAXOPTNS, IVIVINDX, IVIVOFIX, IVCOGSIX, IVSLSIDX, IVSLDSIX, IVSLRNIX, \n" +
                                    "  IVINUSIX, IVINSVIX, IVDMGIDX, IVVARIDX, DPSHPIDX, PURPVIDX, UPPVIDX, IVRETIDX, ASMVRIDX, ITMCLSCD, ITMTRKOP, LOTTYPE, KPERHIST, KPTRXHST, KPCALHST, KPDSTHST, ALWBKORD, VCTNMTHD, UOMSCHDL, ALTITEM1, \n" +
                                    "  ALTITEM2, USCATVLS_1, USCATVLS_2, USCATVLS_3, USCATVLS_4, USCATVLS_5, USCATVLS_6, MSTRCDTY, MODIFDT, CREATDDT, WRNTYDYS, PRCLEVEL, LOCNCODE, PINFLIDX, PURMCIDX, IVINFIDX, INVMCIDX, CGSINFLX, CGSMCIDX,\n" +
                                    "  ITEMCODE, TCC, PriceGroup, PRICMTHD, PRCHSUOM, SELNGUOM, KTACCTSR, LASTGENSN, ABCCODE, Revalue_Inventory, Tolerance_Percentage, Purchase_Item_Tax_Schedu, Purchase_Tax_Options, ITMPLNNNGTYP, \n" +
                                    "  STTSTCLVLPRCNTG, CNTRYORGN, INACTIVE, MINSHELF1, MINSHELF2, INCLUDEINDP, LOTEXPWARN, LOTEXPWARNDAYS, LASTGENLOT, Lot_Split_Quantity, UseQtyOverageTolerance, UseQtyShortageTolerance, \n" +
                                    "  QtyOverTolerancePercent, QtyShortTolerancePercent, IVSCRVIX, USERID)";
                        strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101  "+ cabecera +" VALUES "+ values +"\n";
                        System.out.println("INSERT IV00101" + strSQL); 
                        blnRes=true;
                    }
                    else
                        blnRes=false;                    
                }
                else
                     blnRes=false; 

////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;                    
////                }
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

    private String getObtenerUnidadMedidaGP(String UnidadZafiro,java.sql.Connection conExt)//CHIQUI -PARA OBTENER LA UNIDAD DE MEDIDA DE GP
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String unidadGP="",strSQL="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT RTRIM(BASEUOFM) as unidad ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40201 ";
                strSQL+=" WHERE UOMSCHDL ='"+ UnidadZafiro +"'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    unidadGP=rstLoc.getString("unidad");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            unidadGP="ERROR";
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            unidadGP="ERROR";
            objUti.mostrarMsgErr_F1(null, e);
        }
        return unidadGP;
    }

    private int getObtenerUltimoNOTINDEX(java.sql.Connection conExt)//CHIQUI -PARA OBTENER LA UNIDAD DE MEDIDA DE GP
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        int ultimo=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT TOP 1 NOTEINDX";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 ";
                strSQL+=" ORDER BY DEX_ROW_ID DESC";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    ultimo=rstLoc.getInt("NOTEINDX")+10;
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            ultimo=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            ultimo=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return ultimo;
    }

    private boolean InsersionIV00106(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION DE ARTICULOS CON RESPECTO A LA COMPRA
    {
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="";
        
        try
        {
            //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
////                if (validacionGp())
////                {
                     values ="('"+ txtCodGP.getText() +"', '"+ txtCortaUniMed.getText().toUpperCase() +"',1,2)";
                    strSQL="";
                     cabecera ="(ITEMNMBR, UOFM, QTYBSUOM, UMPUROPT)";
                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00106  "+ cabecera +" VALUES  "+ values +"\n";
                    System.out.println("INSERT IV00106" + strSQL); 
                    blnRes=true;
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;                    
////                }
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

    private boolean InsersionIV00105(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION DEL MAESTRO DE MONEDA CON RESPECTO AL ITEM
    {
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="";
        
        try
        {
          //  conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
////                if (validacionGp())
////                {
                     values ="('"+ txtCodGP.getText() +"','DOLAR',100,6,0)";//SE DEBE CAMBIAR PARA PRODUCCION EN VEZ DE 5 PONER 6
                    strSQL="";
                     cabecera ="(ITEMNMBR, CURNCYID, CURRNIDX, DECPLCUR,LISTPRCE)";
                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00105  "+ cabecera +" VALUES "+ values +"\n";
                    System.out.println("INSERT IV00105" + strSQL);    
                    blnRes=true;
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;                    
////                }
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

    private boolean InsersionTmbTmhInv(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION EN EL TABLA DE TBM_INV-------InsersionTmbInvTmhHistorico
    {
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="",strUnidadGP="";
        String values00="",cabecera00="";
        String strFecUltMod;// JM 2/Ene/2024
        String codExtenderCatCom="",codExtenderClaCom="",codExtenderLinCom="";
        
        
        try
        {
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
               datFecAuxGP=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAuxGP==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAuxGP, objZafParSis.getFormatoFechaHoraBaseDatos());                
                codExtenderCatCom =obtenerCodExtender(conEXT,txtCategoriaCompra.getText(),8);                
                codExtenderClaCom =obtenerCodExtender(conEXT,txtClaseCompra.getText(),9); 
                codExtenderLinCom =obtenerCodExtender(conEXT,txtLineaCompra.getText(),10);
////                if (validacionGp())
////                {
                    strUnidadGP =getObtenerUnidadMedidaGP(txtUniMed.getText(),conEXT);
                     values ="('"+ txtCodGP.getText() +"','"+ txtCodGP.getText() +"',"+ objUti.codificar(txtNomItm.getText(),0) +",'"+ txtCodAlt2.getText() +"',0,0,0,'"+objZafParSis.getNombreUsuario() +"','"+ strFecUltMod +"',0,0,'','','',0,'"+strUnidadGP+"',"+ Double.parseDouble(spnPeso.getModel().getValue().toString()) +",0,0,0,15,1,0,0,'A',0,0,0,0,0,0,0,0,'',"
                             + "'','"+ txtLinea.getText()+"','"+txtCla.getText()+"','"+txtMar.getText()+"','"+txtMat.getText()+"','"+txtCompr.getText()+"','','"+txtControl.getText()+"','','"+ txtCategoria.getText()+"','"+ codExtenderCatCom+"','"+ codExtenderClaCom+"','"+ codExtenderLinCom+"')";
                     values00 ="(1,'"+ txtCodGP.getText() +"',0,0,0,'"+ objZafParSis.getNombreUsuario() +"','"+ strFecUltMod +"','"+ txtCodGP.getText() +"',"+ objUti.codificar(txtNomItm.getText(),0) +",'"+ txtCodAlt2.getText() +"',0,0,'','','','','',0,'"+strUnidadGP+"',"+ Double.parseDouble(spnPeso.getModel().getValue().toString()) +",0,0,0,15,1,0,0,'A',0,0,0,0,0,0,0,"
                             + "0,'"+ txtLinea.getText()+"','"+txtCla.getText()+"','"+txtMar.getText()+"','"+txtMat.getText()+"','"+txtCompr.getText()+"','','"+txtControl.getText()+"','','"+ txtCategoria.getText()+"','"+ codExtenderCatCom+"','"+ codExtenderClaCom+"','"+ codExtenderLinCom+"')";
                     
                    strSQL="";
                     cabecera ="(tx_itm,tx_codalt,tx_nomitm,tx_codalt2,nd_maruti,nd_canMaxVen,nd_facCosUni,tx_usrmod,fe_ultmod,nd_prevta1,co_grpimp,tx_nomitming,st_revtec,st_itmstk,nd_proVenMenAnt,tx_uniMed,nd_pes,co_grpcom,co_detgrpcom,nd_proVenTeo,nd_porIvaVen,co_tipcla,co_grpinv,co_detGrpInv,st_REg,nd_proAnuVenMan,nd_stkBas,nd_tra,nd_fre,nd_punParMin,nd_punParMax,nd_moq,nd_exc,tx_obs1,tx_obs2,"
                             + "tx_atrlin,tx_atrcla,tx_atrmar,tx_atrmat,tx_atrcom,tx_atrsubCom,tx_atrcon,tx_atrsubCon,tx_atrCat,tx_atrCatCom,tx_atrClaCom,tx_atrLinCom)";
                    strSQL+=" INSERT INTO Dynamics2021..tbm_inv  "+ cabecera +" VALUES "+ values +"\n";
                    cabecera00 ="(co_his,tx_itm,nd_marUti,nd_canMaxVen,nd_facCosUni,tx_usrmod,fe_ultmod,tx_codalt,tx_nomitm,tx_codalt2,nd_preVta1,co_grpimp,tx_nomitming,st_revtec,st_itmstk,tx_obs1,tx_obs2,nd_proVenMenAnt,tx_uniMed,nd_pes,co_grpcom,co_detgrpcom,nd_proVenTeo,nd_porIvaVen,co_tipcla,co_grpinv,co_detGrpInv,st_REg,nd_proAnuVenMan,nd_stkBas,nd_tra,nd_fre,nd_punParMin,nd_punParMax,nd_moq,nd_exc,"
                             + "tx_atrlin,tx_atrcla,tx_atrmar,tx_atrmat,tx_atrcom,tx_atrsubCom,tx_atrcon,tx_atrsubCon,tx_atrCat,tx_atrCatCom,tx_atrClaCom,tx_atrLinCom)";
                    strSQL+=" INSERT INTO Dynamics2021..tbh_inv  "+ cabecera00 +" VALUES "+ values00 +"\n";
                    
                    System.out.println("INSERT tbm_inv,tbh_inv" + strSQL);    
                    blnRes=true;
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;                    
////                }
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
    
    private boolean InsersionABC(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION EN EL TABLA DE 
    {
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String valuesF="",cabeceraF="";
        String valuesP="",cabeceraP="";
        String valuesV="",cabeceraV="";
        String valuesT="",cabeceraT="";
        String valuesC="",cabeceraC="";
        String strSQLF="";
        String strSQLP="";
        String strSQLV="";
        String strSQLT="";
        String strSQLC="";
        String strSQLGLOBAL="";
        String strFecUltMod;
        try
        {
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                datFecAuxGP=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAuxGP==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAuxGP, objZafParSis.getFormatoFechaHoraBaseDatos());                
                valuesF ="(0,1,'"+ txtCodGP.getText() +"',1,"+ txtCodAbcFRE.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ strFecUltMod +"',0,'1900-01-01 00:00:00.000')";
                valuesP ="(0,1,'"+ txtCodGP.getText() +"',2,"+ txtCodAbcPES.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ strFecUltMod +"',0,'1900-01-01 00:00:00.000')";
                valuesV ="(0,1,'"+ txtCodGP.getText() +"',3,"+ txtCodAbcVAL.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ strFecUltMod +"',0,'1900-01-01 00:00:00.000')";
                valuesT ="(0,1,'"+ txtCodGP.getText() +"',4,"+ txtCodAbcTUV.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ strFecUltMod +"',0,'1900-01-01 00:00:00.000')";
                valuesC ="(0,1,'"+ txtCodGP.getText() +"',5,"+ txtCodAbcCLA.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ strFecUltMod +"',0,'1900-01-01 00:00:00.000')";
                strSQLF="";
                strSQLP="";
                strSQLV="";
                strSQLT="";
                strSQLC="";
                cabeceraF ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                cabeceraP ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                cabeceraV ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                cabeceraT ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                cabeceraC ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                
                strSQLF+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabeceraF +" VALUES "+ valuesF +";\n";
                strSQLP+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabeceraP +" VALUES "+ valuesP +";\n";
                strSQLV+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabeceraV +" VALUES "+ valuesV +";\n";
                strSQLT+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabeceraT +" VALUES "+ valuesT +";\n";
                strSQLC+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabeceraC +" VALUES "+ valuesC +";\n";
                //System.out.println("INSERT InsersionABC" + strSQL);    
                strSQLGLOBAL = strSQLF + strSQLP + strSQLV + strSQLT + strSQLC ;
                
                blnRes=true;
                stmLoc.executeUpdate(strSQLGLOBAL);
                stmLoc.close();
                stmLoc=null;                    
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



//////////////////////////////////////////////////////////////////////////////// INICIO DE CREACION DE REGISTRO EN LA 112 CON TIPO 2

    private boolean InsercionItemsBodegasUbicacion(java.sql.Connection conEXT)//chiqui-ESTE
    {
////     java.sql.Connection conLocGP;
    java.sql.Statement stmLocGP;
    java.sql.ResultSet rstLocGP;
    String strSqlGP="",itemUbicacion="",strNomProCorGP="",strDir1ProGP="",strTele1ProGP="",strTele2ProGP="",strCodProGP;
    int ultimoIdSQL=0;
    int contador =0;
    boolean validaIngreso=false,blnRes=false;
    int blnProZaf =-1;

    try
    {
        //conEXT=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
        if(conEXT!=null)
        {
            conEXT.setAutoCommit(false);
            stmLocGP = conEXT.createStatement();
            strSqlGP="";
////                strSqlGP+=" select ITEMNMBR, ITMSHNAM, ITEMDESC, ITEMTYPE \n";//para altiora
            strSqlGP+=" SELECT DISTINCT trim(a1.ITEMNMBR) as tx_itm \n";//para masterware       
                strSqlGP+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 (nolock) as a1  \n";
                strSqlGP+=" INNER JOIN  "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00102 as a2 (nolock) ON (a1.ITEMNMBR=a2.ITEMNMBR) \n";
                strSqlGP+=" INNER JOIN Dynamics2021.dbo.tbm_bod (nolock) as a3 ON (a2.LOCNCODE=a3.tx_descor) \n";
                strSqlGP+=" WHERE a1.INACTIVE = 0 and a1.ITEMTYPE = 1 AND a3.st_bodRep = 'S' and (a1.ITEMNMBR like '%S' OR a1.ITEMNMBR like '%I') AND a2.RCRDTYPE = 2 AND a2.LOCNCODE <> 'IMPORT' and a1.ITEMNMBR ='"+ txtCodGP.getText() +"' \n";
            rstLocGP = stmLocGP.executeQuery(strSqlGP);
            //mensajePantalla ="BusquedaItemsSQLvsZafiro:Inicia la consulta de los items que tienen menos de 24 ubicaciones \n ";
          while(rstLocGP.next())
          {
              itemUbicacion =rstLocGP.getString("tx_itm");

              blnProZaf = BusquedaItemBodegaUbicacion(conEXT,itemUbicacion);

              if (blnProZaf ==1)
              {
////                  conLocGP.commit();
                  System.out.println("todo bien " );
                  blnRes=true;
              }
              else
              {
                  if (blnProZaf ==-1)
                  {
                      blnRes=false;
////                      conLocGP.rollback();
                      System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" );
                  }

              }

          }

          stmLocGP.close();
          stmLocGP=null;
          rstLocGP.close();
          rstLocGP=null;
        }
////          conLocGP.close();
////          conLocGP=null;  
    }
    catch(java.sql.SQLException e){ 
        System.err.println("OCURRIO UN ERROR busquedaItemGP "+ e );
        blnRes=false;
     }
     catch (Exception e) {
        System.err.println("OCURRIO UN ERROR busquedaItemGP "+ e );
        blnRes=false;
     }
return blnRes;
}


    private int BusquedaItemBodegaUbicacion(java.sql.Connection conGlobal, String item)//chiqui
    {
    ////java.sql.Connection conLoc;
    java.sql.Statement stmLoc;
////        java.sql.Statement stm;
    java.sql.ResultSet rstLoc;    

    String strSqlZa="",strSQL="",strSQLGlobal12="",tx_ultUbicacion="",strSQLGlobal02="",strSQLGlobal="";
    int ultimoxUbicar=0;
    boolean validaCli=false;
    int blnRes=-1;
    try
    {
       //// conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
        ultimoxUbicar =obtenerUltimoIdporUbicar();

        if(conGlobal!=null)
        {
          stmLoc=conGlobal.createStatement();
////              stm=conGlobal.createStatement();
          strSqlZa="";
////          strSqlZa+=" SELECT tx_descor as bodega  \n";
////          strSqlZa+=" FROM Dynamics2021..tbm_bod  \n";
////          strSqlZa+=" WHERE  co_emp=0 and st_bodRep ='S' and tx_descor <> 'IMPORT'  \n";
////          strSqlZa+=" EXCEPT  \n";
////          strSqlZa+=" SELECT distinct(LOCNCODE) as bodega \n";
////          strSqlZa+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00102]  \n";
////          strSqlZa+=" WHERE  ITEMNMBR='"+item +"' and  LOCNCODE <> 'IMPORT'  \n";
          strSqlZa+=" SELECT LOCNCODE as bodega  \n";
          strSqlZa+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV40700]  \n";
          strSqlZa+=" WHERE LOCNCODE  not in ("+bodegasExclui +")  \n";
          strSqlZa+=" EXCEPT  \n";
          strSqlZa+=" SELECT distinct(LOCNCODE) as bodega \n";
          strSqlZa+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00102]  \n";
          strSqlZa+=" WHERE  ITEMNMBR='"+item +"'   \n";
          rstLoc = stmLoc.executeQuery(strSqlZa);

          while(rstLoc.next())
          {
            validaCli =true;
            ultimoxUbicar =ultimoxUbicar+1;
            tx_ultUbicacion = "POR_UBI_" + ultimoxUbicar;
                    strSQL="";

                    strSQL+="INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00102] ([ITEMNMBR]	,[LOCNCODE]	,[RCRDTYPE],[QTYONHND]	,[ATYALLOC],"
                            + " ORDERPOLICY,NMBROFDYS,ORDERMULTIPLE,REPLENISHMENTMETHOD,FRCSTCNSMPTNPRD,\n" 
                            + " ReplenishmentLevel,POPOrderMethod,POPVendorSelection,POPPricingSelection,IncludeAllocations,IncludeBackorders,IncludeRequisitions, \n"
                            + " INCLDMRPMOVEIN,INCLDMRPMOVEOUT,INCLDMRPCANCEL"
                            + ")";
                    strSQL+=" VALUES (";
                    strSQL+=" '" + item + "'"; //co_emp
                    strSQL+=",'"+ rstLoc.getString("bodega") +"'"; //
                    strSQL+=" ,2,0,0,1,1,1,2,3,1,1,2,1,1,1,1,1,1,1";
                    strSQL+=");  ";
                    strSQLGlobal02 = strSQL;

                    strSQL="";
////////                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00112] ([ITEMNMBR],[LOCNCODE],[BIN],[QTYTYPE],[QUANTITY],[ATYALLOC]) \n";
////////                    strSQL+=" VALUES ( \n";
////////                    strSQL+=" '" + item + "'"; //co_emp
////////                    strSQL+=",'"+ rstLoc.getString("bodega") +"'";
////////                    strSQL+=",'"+ tx_ultUbicacion +"'";
////////                    strSQL+=",1,0,0";
////////                    strSQL+=" );  ";
                    strSQLGlobal12 = strSQL;
                    strSQLGlobal += strSQLGlobal02 + strSQLGlobal12;

          }
          if(validaCli ==true)
          {
              String bodega ="";
                 System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                 stmLoc.executeUpdate(strSQLGlobal); 
                 blnRes=1;
////                 if(modificarSecUltiUbicacion(conGlobal,ultimoxUbicar))
////                 {
////                      blnRes=1;
////                 }
////                 else
////                      blnRes=2;
          }
          else
               blnRes=3;

          stmLoc.close();
          stmLoc=null;
          rstLoc.close();
          rstLoc=null;
////              stm.close();
////              stm=null;              

        }
//////              conLoc.close();
//////              conLoc=null; 
////         txtDat.setText(mensajePantalla);  
    }
    catch(java.sql.SQLException e){ 
        System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
        blnRes=-1;
     }
     catch (Exception e) {
        System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
        blnRes=-1;
     }
    return blnRes;
}


    private int obtenerUltimoIdporUbicar()//chiqui
    {
    java.sql.Connection conLoc;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;    

    String strSqlZa="";
    int codigomax=0;
    try
    {
        conLoc=DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());

        if(conLoc!=null)
        {
          stmLoc=conLoc.createStatement();
          strSqlZa="";
          strSqlZa+=" select (ne_secultubiinv) as ultimo from tbm_emp where co_emp=0 \n";
          rstLoc = stmLoc.executeQuery(strSqlZa);

          while(rstLoc.next())
          {
              codigomax =rstLoc.getInt("ultimo");
          }
          stmLoc.close();
          stmLoc=null;
          rstLoc.close();
          rstLoc=null;
        }
          conLoc.close();
          conLoc=null; 
        // txtDat.setText(mensajePantalla);  
    }
    catch(java.sql.SQLException e){ 
        System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
     }
     catch (Exception e) {
        System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
     }
    return codigomax;
}


public boolean modificarSecUltiUbicacion(java.sql.Connection conGlobal, int ultimo)//chiqui
{
    java.sql.Statement stmLoc;
    boolean blnRes=false;
    try {           
        System.out.println("ponerEstadosReservaFacturadaCancelada");

        if(conGlobal!=null){
            stmLoc = conGlobal.createStatement();
            strSql="";
            strSql+=" UPDATE tbm_emp SET ne_secultubiinv = "+ ultimo +"  \n";
            strSql+=" where  co_emp=0 \n";
            stmLoc.executeUpdate(strSql);
            stmLoc.close();
            stmLoc = null;
            blnRes=true;
        }
    } 
    catch (java.sql.SQLException e) {
        //txtDat.setText("ERROR: " + e);
        System.err.println("ERROR: " + e);
        blnRes=false;
    } 
    catch (Exception e) {
      //  txtDat.setText("ERROR: " + e);
        System.err.println("ERROR: " + e);
        blnRes=false;
    }
    return blnRes;
}

////////////////////////////////////////////////////////////////////////////////FIN CREACION DE REGISTRO EN LA 112 CON TIPO 2

private boolean InsersionIV00102(java.sql.Connection conEXT,int tipo)//CHIQUI -PARA LA CREACION DEl REGISTRO CON TIPO 1 y TIPO 2 CON RESPECTO AL ITEM
{
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="";
        
        try
        {
            //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                if (tipo==1)
                {
                    
                     values ="('"+ txtCodGP.getText() +"', '', '', 1, '', 0, 0.00000, 0.00000, 0.00000,'"+ strFecDes +"', '', '"+ strFecDes +"', 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, "
                             + "0.00000, 0.00000, 0.00000, 0.00000, '"+ strFecDes +"', '"+ strFecHora +"', '"+ strFecDes +"','"+ strFecHora +"', 0, '', '', '', 1, 0.00000, 0.00000, 1, 0.00000, 0.00000, 1.00000, 2, 0.00000, "
                             + "0.00000, 0.00000, 0.00000, 0.00000, 0, 0, 1, 0, 0, 0, 3, 0.00000, 0.00000, 0.00000, '', '', '', '', '', '', '', '', 1, 1, '', 2, 1, 0.00000, 1, 1, 1, 0, 1, 1, 1, 0, 0)";//SE DEBE CAMBIAR PARA PRODUCCION EN VEZ DE 5 PONER 6
                    strSQL="";
                     cabecera ="(ITEMNMBR, LOCNCODE, BINNMBR, RCRDTYPE, PRIMVNDR, ITMFRFLG, BGNGQTY, LSORDQTY, LRCPTQTY, LSTORDDT, LSORDVND, LSRCPTDT, QTYRQSTN, QTYONORD, QTYBKORD, \n"
                              + "QTY_Drop_Shipped, QTYINUSE, QTYINSVC, QTYRTRND, QTYDMGED, QTYONHND, ATYALLOC, QTYCOMTD, QTYSOLD, NXTCNTDT, NXTCNTTM, LSTCNTDT, LSTCNTTM, STCKCNTINTRVL, \n"
                              + "Landed_Cost_Group_ID, BUYERID, PLANNERID, ORDERPOLICY, FXDORDRQTY, ORDRPNTQTY, NMBROFDYS, MNMMORDRQTY, MXMMORDRQTY, ORDERMULTIPLE, REPLENISHMENTMETHOD, \n"
                              + "SHRINKAGEFACTOR, PRCHSNGLDTM, MNFCTRNGFXDLDTM, MNFCTRNGVRBLLDTM, STAGINGLDTME, PLNNNGTMFNCDYS, DMNDTMFNCPRDS, INCLDDINPLNNNG, CALCULATEATP, AUTOCHKATP, \n"
                              + "PLNFNLPAB, FRCSTCNSMPTNPRD, ORDRUPTOLVL, SFTYSTCKQTY, REORDERVARIANCE, PORECEIPTBIN, PORETRNBIN, SOFULFILLMENTBIN, SORETURNBIN, BOMRCPTBIN, MATERIALISSUEBIN, \n"
                              + "MORECEIPTBIN, REPAIRISSUESBIN, ReplenishmentLevel, POPOrderMethod, MasterLocationCode, POPVendorSelection, POPPricingSelection, PurchasePrice, IncludeAllocations, \n"
                              + "IncludeBackorders, IncludeRequisitions, PICKTICKETITEMOPT, INCLDMRPMOVEIN, INCLDMRPMOVEOUT, INCLDMRPCANCEL, Move_Out_Fence, INACTIVE)";
                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00102  "+ cabecera +" VALUES "+ values +"\n";
                    System.out.println("INSERT IV00102_TIPO1" + strSQL);                    
                }
                else
                {
////                    blnRes =true;
                    if (BusquedaItemBodegaUbicacion(conEXT,txtCodGP.getText())==1)
                    {
                        blnRes=true;
                    }
                    else
                        blnRes=false;
                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                blnRes=true;
                stmLoc.close();
                stmLoc=null;                    
////                }
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
//////////////////////////////////////////////////////////////////////////////INICIO PARA LA CREACION DEL MAESTRO PARALELO A LA 108
private boolean InsersionIV00107(java.sql.Connection conEXT)//CHIQUI -
{
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false,validaIngreso=false;
        String cadenaInsert="",codigoSLQ="",strSQL="",unidadGP="",values="",cabecera="",strSQLGlo="";
        int codigoPrecio=0;
        
        try
        {
           // conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();

                strSQL="";
                strSQL+=" SELECT  distinct (trim(PRCLEVEL)) as listaPrecio \n";//para masterware       
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40800    \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                arlDatITEMPRE= new ArrayList();
                
                    strSQL="";
                     
                while(rstLoc.next())
                {
                    unidadGP =getObtenerUnidadMedidaGP(txtUniMed.getText(),conEXT);
                    if (unidadGP !="ERROR")
                    {
                        codigoPrecio= obtenerCodPre(conEXT,unidadGP);
                        cabecera =" (ITEMNMBR, CURNCYID, PRCLEVEL, UOFM,RNDGAMNT,ROUNDHOW,ROUNDTO,UMSLSOPT,QTYBSUOM)";
                        values ="('"+ txtCodGP.getText() +"','DOLAR','"+rstLoc.getString("listaPrecio") +"','"+ unidadGP +"',0,0,1,"+ codigoPrecio +",1)";
                        strSQL=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00107  "+ cabecera +" VALUES "+ values +"\n";
                        strSQLGlo +=strSQL;
                        validaIngreso=true;
                        
                    }
                    else
                    {
                        validaIngreso=false;
                        break;
                    }
                }
                
                if (validaIngreso==true)
                {
                    stmLoc.executeUpdate(strSQLGlo);
                    blnRes=true;
                }
                    
                else
                    blnRes=false;
////                     values ="('"+ txtCodGP.getText() +"','DOLAR',100,5,0)";//SE DEBE CAMBIAR PARA PRODUCCION EN VEZ DE 5 PONER 6
////                    strSQL="";
////                     cabecera ="(ITEMNMBR, CURNCYID, CURRNIDX, DECPLCUR,LISTPRCE)";
////                    strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..IV00105  "+ cabecera +"  "+ values +"\n";
////                    System.out.println("INSERT IV00105" + strSQL);                    

                
                //Armar la sentencia SQL.

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;  
                
////                }
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

private int obtenerCodPre(java.sql.Connection conEXT,String unidadGP)
{
    int codigo=0;
    
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT TOP 1 UMSLSOPT ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..iv00107 ";
                strSQL+=" WHERE UOFM ='"+ unidadGP +"'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    codigo=rstLoc.getInt("UMSLSOPT");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            codigo=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            codigo=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        
    
    return codigo;
}
/////////////////////////////////////////////////////////////////////////////FIN PARA LA CREACION DEL MAESTRO PARALELO A LA 108


private boolean InsersionIV00108(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION DEL MAESTRO EN LA 108
{
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false,validaIngreso=false;
        String cadenaInsert="",codigoSLQ="",strSQL="",unidadGP="",values="",cabecera="",strSQLGlo="";
        
        try
        {
           // conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();

                strSQL="";
                strSQL+=" SELECT  distinct (trim(PRCLEVEL)) as listaPrecio \n";//para masterware       
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40800    \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                arlDatITEMPRE= new ArrayList();
                
                    strSQL="";
                     
                while(rstLoc.next())
                {
                    unidadGP =getObtenerUnidadMedidaGP(txtUniMed.getText(),conEXT);
                    if (unidadGP !="ERROR")
                    {
                        cabecera =" (ITEMNMBR, CURNCYID, PRCLEVEL, UOFM,TOQTY,FROMQTY,UOMPRICE,QTYBSUOM)";
                        values ="('"+ txtCodGP.getText() +"','DOLAR','"+rstLoc.getString("listaPrecio") +"','"+ unidadGP +"',999999999999,1,0,1)";
                        strSQL=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00108  "+ cabecera +" VALUES "+ values +"\n";
                        strSQLGlo +=strSQL;
                        validaIngreso=true;
                    }
                    else
                    {
                        validaIngreso=false;
                        break;
                    }
                }
                
                if (validaIngreso==true)
                {
                    stmLoc.executeUpdate(strSQLGlo);
                    blnRes=true;
                }
                    
                else
                    blnRes=false;
////                     values ="('"+ txtCodGP.getText() +"','DOLAR',100,5,0)";//SE DEBE CAMBIAR PARA PRODUCCION EN VEZ DE 5 PONER 6
////                    strSQL="";
////                     cabecera ="(ITEMNMBR, CURNCYID, CURRNIDX, DECPLCUR,LISTPRCE)";
////                    strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..IV00105  "+ cabecera +"  "+ values +"\n";
////                    System.out.println("INSERT IV00105" + strSQL);                    

                
                //Armar la sentencia SQL.

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;  
                
////                }
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

private boolean InsersionIV00400(java.sql.Connection conEXT)//CHIQUI -PARA LA CREACION DEL ENCABEZADO DE. ARTICULO DE VENTA
{
////        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String values="",cabecera="",strSQL="";
        
        try
        {
          //  conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            if(conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
////                if (validacionGp())
////                {
                     values ="('"+ txtCodGP.getText() +"',1,1,1,1)";
                    strSQL="";
                     cabecera ="(ITEMNMBR, QUOTE, PRDER, FULFILL,INVOICECB)";
                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00400  "+ cabecera +" VALUES "+ values +"\n";
                    System.out.println("INSERT IV00105" + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                blnRes=true;
                stmLoc.close();
                stmLoc=null;                    
////                }
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

private boolean ActualizacionItemZafiro(java.sql.Connection conn)//CHIQUI - PARA ACTUALIZAR EL ITEM EN ZAFIRO CON EL ITEM MAESTRO DE GP
{
    boolean blnRes=false;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     String strSql="";
     try{
           stmLoc=conn.createStatement();

           strSql="update  Dynamics2021..tbm_inv set tx_codaltdyn ='"+ txtCodGP.getText() +"' WHERE tx_codalt2 ='"+ txtCodAlt2.getText() +"' and tx_codalt ='"+ txtCodAlt.getText() +"'";
           stmLoc.executeUpdate(strSql);

           stmLoc.close();
           stmLoc=null;
           blnRes=true;

      }catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
      return blnRes;

}


private int getObtenerCodigoGP(String linea, String tipo)//CHIQUI -PARA OBTENER LA UNIDAD DE MEDIDA DE GP
 {
        java.sql.Statement stmLoc;
        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String item="",strSQL="",cadena ="";
        boolean valida =false;
        int codigo=-1;
        String strLineaGP[];
        try
        {
            conGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stmLoc=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT TOP 1 TRIM (ITEMNMBR) as item ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 ";
                strSQL+=" WHERE ITEMNMBR like  '"+ linea +"-%-"+tipo +"' ";
                strSQL+=" ORDER BY  ITEMNMBR  DESC";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    
                    item=rstLoc.getString("item");
                    strLineaGP= item.split("-");
                    item = strLineaGP[1];
                    codigo =  Integer.parseInt(item)+1;
                    valida=true;
                    break;
                }
                if (!valida)
                    codigo=1;
                    
                rstLoc.close();
                stmLoc.close();
                conGP.close();
                rstLoc=null;
                stmLoc=null;
                conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            codigo=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            codigo=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return codigo;
    }

private void obtenerValorAdicItm()
    {
        java.sql.Statement stmLoc;
        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        boolean valida =false;
        bdePrec= BigDecimal.ZERO;
        bdeMarUti= BigDecimal.ZERO;
        String strLineaGP[];
        try
        {
            conGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stmLoc=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT isnull(nd_preVta1,0) as pre, isnull(nd_marUti,0) as maruti";
                strSQL+=" FROM  Dynamics2021..tbm_inv ";
                strSQL+=" WHERE tx_codalt2 ='"+ txtCodAlt2.getText() +"'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    
                    bdePrec = rstLoc.getBigDecimal("pre");
                    bdeMarUti = rstLoc.getBigDecimal("maruti");
                    valida=true;
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                conGP.close();
                rstLoc=null;
                stmLoc=null;
                conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
    }
    
private boolean obtenerCodKon(java.sql.Connection conGP)
    {
        java.sql.Statement stmLoc;
        //java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        boolean blnRes =false;
        ne_codKon=0;
        try
        {
            
            //conGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stmLoc=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT co_itm ";
                strSQL+=" FROM  "+ objZafParSis.getNombreBaseDatosKON()  +"..tbm_inv ";
                strSQL+=" WHERE tx_codalt2 ='"+ txtCodAlt2.getText() +"'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    ne_codKon = rstLoc.getInt("co_itm");
                    blnRes=true;
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }


public static String crearJsonItems(List<Map<String, Object>> listaItems) {
    JSONArray jsonArray = new JSONArray();
    for (Map<String, Object> item : listaItems) {
        jsonArray.put(new JSONObject(item));
    }
    JSONObject jsonFinal = new JSONObject();
    jsonFinal.put("items", jsonArray);
    return jsonFinal.toString(2); 
}
    

    
    private boolean insertarCreaModiItmKON(int tipo,java.sql.Connection connGP)
    {
        boolean blnRes=true;
        String chrEst="",strJson="";
        try
        {
            if (tipo==1)
            {
                List<Map<String, Object>> listaItems = new ArrayList<>();
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("co_emp", 1);
                itemData.put("tx_codAlt1", txtCodAlt.getText());
                itemData.put("tx_codAlt2", txtCodAlt2.getText());
                itemData.put("tx_codAlt3", txtCodGP.getText());
                itemData.put("tx_nomItm", txtNomItm.getText());
                itemData.put("co_uniMed", Integer.parseInt(txtCodUniMed.getText()));
                itemData.put("tx_tipItm", "B");
                itemData.put("nd_preUni", BigDecimal.ZERO);
                itemData.put("nd_marUti", BigDecimal.ZERO);
                itemData.put("nd_pesItmKgr", new BigDecimal(spnPeso.getModel().getValue().toString()));
                itemData.put("tx_obs1", "");
                itemData.put("tx_obs2", "");
                itemData.put("co_usrIng", objZafParSis.getCodigoUsuario());

                listaItems.add(itemData);    
                
                String jsonGenerado = crearJsonItems(listaItems);
                System.out.println(jsonGenerado);                
                
                
//                blnRes = objKon.ejecuSPCreaItmKon(1,txtCodAlt.getText(),txtCodAlt2.getText(),txtCodGP.getText(),txtNomItm.getText(),Integer.parseInt(txtCodUniMed.getText()),"B",BigDecimal.ZERO,BigDecimal.ZERO,new BigDecimal(spnPeso.getModel().getValue().toString()),"","",objZafParSis.getCodigoUsuario());
                blnRes = objKon.ejecuSPCreaItmKonJson(jsonGenerado);
                
            }
                
            else
            {
                //obtenerValorAdicItm();
                if (obtenerCodKon(connGP))
                {
                    
                    if(cboEst.getSelectedIndex()==0) chrEst="A";
                    else  chrEst="I";                            
                    
                    List<Map<String, Object>> listaItems = new ArrayList<>();
                    Map<String, Object> itemData = new HashMap<>();
                    itemData.put("co_emp", 1);
                    itemData.put("tx_codAlt1", txtCodAlt.getText());
                    itemData.put("tx_nomItm", txtNomItm.getText());
                    itemData.put("nd_preUni", bdePrec);
                    itemData.put("nd_marUti", bdeMarUti);
                    itemData.put("nd_pesItmKgr", new BigDecimal(spnPeso.getModel().getValue().toString()));
                    itemData.put("tx_obs1", "Modificado por Creacion de ItemGP");
                    itemData.put("tx_obs2", "");
                    itemData.put("co_usrIng", objZafParSis.getCodigoUsuario());
                    itemData.put("estReg", chrEst);

                    listaItems.add(itemData); 



                    String jsonGenerado = crearJsonItems(listaItems);
                    System.out.println(jsonGenerado);                    
//                    blnRes = objKon.ejecuSPActuaItmKon(1,ne_codKon,txtCodAlt.getText(),txtCodAlt2.getText(),txtCodGP.getText(),txtNomItm.getText(),Integer.parseInt(txtCodUniMed.getText()),"B",bdePrec,bdeMarUti,new BigDecimal(spnPeso.getModel().getValue().toString()),"","",chrEst,  objZafParSis.getCodigoUsuario());                    
                    blnRes = objKon.ejecuSPActuaItmKonJson(jsonGenerado);                    
                    
                }

            }
                
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




private boolean insertarDatReg(){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodEqu=0,ultimoCodigoGp=0;
 boolean blnCodAlt = false,blnCodGP=false;
 int idUnidad=0;
 try
 {
     if(abrirConRem())
     {
        if(conRemGlo!=null)
        {
            stmLoc=conRemGlo.createStatement();
            intCodEqu=getCodItemMaestro(conRemGlo);
         //////////////////ZAFIRO////////////////////////
            if(validadCodAlt(conRemGlo, txtCodAlt.getText() ,"tx_codAlt" )) 
            {
                String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
                String strLineaGP[] = txtCodAlt.getText().trim().toUpperCase().split("-");
                String cadena = strLineaGP[0];
                if (strTerItm.equals("I") || strTerItm.equals("S")) 
                {
                    if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected()))
                    {
                        while (!blnCodAlt) 
                        {
                            txtCodGP.setText(title);    
                            txtCodAlt2.setText(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 3));
                            blnCodAlt = validadCodAlt(conRemGlo, txtCodAlt2.getText(), "tx_codAlt2" ); 
                        } 
                    } 
                    else 
                        blnCodAlt = validadCodAlt(conRemGlo, txtCodAlt2.getText() ,"tx_codAlt2" );
                    prefijo =strTerItm;
                } 
                if(blnCodAlt) 
                {
                    idUnidad = ObtenerUnidadMedidaZaf(conRemGlo,txtUniMed.getText());
                    if (idUnidad ==-1)
                        blnCodGP=false; 
                    else
                         blnCodGP=true;
                    if(blnCodGP)
                    {
                        ultimoCodigoGp=getObtenerCodigoGP(cadena,strTerItm);
                        if(ultimoCodigoGp>0)
                        {
                            blnCodGP=true;
                            txtCodGP.setText(cadena+"-"+String.format("%06d", ultimoCodigoGp)+"-"+strTerItm);
                        }
                        else
                            blnCodGP=false;
                    }
                    if(blnCodGP)
                    {
                        if(intCodEqu!=0)
                        {
                            if(_CrearItemEmpresas(conRemGlo, intCodEqu ,idUnidad))
                            {
                                if(_creaInvMovEmpGrp(conRemGlo, intCodEqu ))
                                {
                                    cboEst.setSelectedIndex(0);
                                    txtStkAct.setText("0");
                                    txtCodItm.setEditable(false);
                                    chkIvaCom.setSelected(true);
                                    chkIvaVta.setSelected(true);
                                    txtCodAlt.requestFocus();
                                    //////////////////GP////////////////////////
                                    if (InsersionGP())
                                    {
                                        conRemGlo.commit();
                                        blnRes=true;
                                    }
                                    else 
                                        conRemGlo.rollback();                
                                }
                                else conRemGlo.rollback();
                            }
                            else conRemGlo.rollback();
                        }
                        else
                        { 
                            MensajeInf("Problemas al crear codigo de item consolidado");  
                            conRemGlo.rollback(); 
                        }
                    }
                    else
                    { 
                        MensajeInf("Problemas al crear codigo de item GP consolidado");  
                        conRemGlo.rollback(); 
                    }
                }
                else
                {  
                    valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);  
                    conRemGlo.rollback(); 
                }
            }
            else
            { 
                valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);  
                conRemGlo.rollback(); 
            }
            stmLoc.close();
            stmLoc=null;
            conRemGlo.close();
            conRemGlo=null;
        }
     }
 }
 catch(SQLException e)
 { 
     blnRes=false; objUti.mostrarMsgErr_F1(this, e);   
 }
 catch(Exception e)
 { 
     blnRes=false; objUti.mostrarMsgErr_F1(this, e);   
 }
 return blnRes;
}

////public boolean insertarPrvItm(java.sql.Connection conn,int intCodEmp,int intCodItm ){
////   boolean blnRes=false;
////   java.sql.Statement stmLoc;
////   strSql="";
////   String strEst="";
////   try{
////      stmLoc=conn.createStatement();
////
////       for(int i=0; i<tblPrv.getRowCount(); i++){
////        if(tblPrv.getValueAt(i, INT_TBL_PRVCODCLI) != null){
////         if(!tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString().equals("")){
////
////               strEst="A";
////                if(tblPrv.getValueAt( i, INT_TBL_PRVPRE)!=null){
////                  if(tblPrv.getValueAt( i, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
////                         strEst="P";
////                }
////                if(tblPrv.getValueAt( i, INT_TBL_PRVACT)!=null){
////                   if(tblPrv.getValueAt( i, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
////                        strEst="A";
////                }
////                if(tblPrv.getValueAt( i, INT_TBL_PRVINA)!=null){
////                  if(tblPrv.getValueAt( i, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
////                         strEst="I";
////                }
////
////               strSql+=" INSERT INTO tbr_prvinv(co_emp, co_itm, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
////               " ("+objZafParSis.getCodigoEmpresa()+","+ intCodItm +", "+tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString()+" "+
////               " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";
////
////       }}}
////
////      if(!strSql.equals(""))
////          stmLoc.executeUpdate(strSql);
////
////      stmLoc.close();
////      stmLoc=null;
////      blnRes=true;
////
////   }catch(java.sql.SQLException Evt){ blnRes=false;   MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
////    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
////    return blnRes;
////}


private int ObtenerUnidadMedidaZaf(java.sql.Connection conn,String unidad)
{
    java.sql.ResultSet rstLoc;
    java.sql.Statement stmEmp;
    int estado= -1;
 try{
     stmEmp = conn.createStatement();
     strSql="";
     strSql="select co_reg from tbm_var  where UPPER(TX_DESLAR) ='"+ unidad +"'";
     rstLoc=stmEmp.executeQuery(strSql);
     while(rstLoc.next()){
        estado = rstLoc.getInt("co_reg");
     }
     
     
 }catch(SQLException evt){ estado=-1;  objUti.mostrarMsgErr_F1(this, evt); }
   catch(Exception evt){  estado=-1;  objUti.mostrarMsgErr_F1(this, evt); }
  return estado;    
}

private int ObtenerUnidadMedidaGP(String unidad)
{
   java.sql.Statement stmLoc;
        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String item="",strSQL="",cadena ="";
        boolean valida =false;
        int codigo=-1;
        String strLineaGP[];
        try
        {
            conGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stmLoc=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT BASEUOFM ";
                strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40201 ";
                strSQL+=" WHERE BASEUOFM ='"+unidad+"'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    valida=true;
                    break;
                }
                    
                rstLoc.close();
                stmLoc.close();
                conGP.close();
                rstLoc=null;
                stmLoc=null;
                conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            codigo=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            codigo=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return codigo;
        
        
}
private boolean _CrearItemEmpresas(java.sql.Connection conn,int intCodEqu, int idUnidad){
 boolean blnRes=false;
 java.sql.Statement stmInv,stmEmp;
 java.sql.ResultSet rstLoc,rstEmp;
 int intCodItm=0;
 int intControl=0;
 String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="";
 String strFecUltMod;
 try{
    stmEmp = conn.createStatement();
    stmInv = conn.createStatement();
 
    if(chkEsItmSer.isSelected()) chrItmSer="S";
    if(chkEsItmTra.isSelected()) chrItmSer="T";
    if(chkEsItmOtr.isSelected()) chrItmSer="O";


    if(chkIvaCom.isSelected())  chrIvaCom="S";
    else  chrIvaCom="N";

    if(chkIvaVta.isSelected()) chrIvaVta="S";
    else  chrIvaVta="N";

    if(cboEst.getSelectedIndex()==0) chrEst="A";
    else  chrEst="I";

    if(chkCamPreVta.isSelected()) strCamPreVta="S";


    strSql="SELECT co_emp FROM tbm_emp ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    if (datFecAux==null)
      return false;
    strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    while(rstEmp.next()){

       strSql="select case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp="+rstEmp.getString("co_emp");
       rstLoc=stmInv.executeQuery(strSql);
       if(rstLoc.next())
          intCodItm = rstLoc.getInt("co_itm");
       rstLoc.close();
       rstLoc=null;

        strAuxCodAlt=txtCodAlt.getText().replaceAll("'", "''");
        String codigoZafiro ="";
        codigoZafiro=objUti.codificar(txtCodAlt.getText());
        

        strSql="INSERT INTO tbm_inv ( co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_uni "+
        " ,nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax "+
        " ,st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng,co_usrmod, fe_ing, fe_ultmod, nd_pesitmkgr, st_permodnomitmven, " +
        " st_permodnomitmcom, st_blqPreVta,st_oblCamNomItm ,tx_codaltdyn) "+
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodItm+", "+
                ((strAux=   codigoZafiro).equals("")?"Null":""+strAux.toUpperCase()+"")+", "
                + ""+
        " "+((strAux=txtCodAlt2.getText().replaceAll("'", "''")).equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAux.toUpperCase()+"'")+", "+
        "  "+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
                
////        "  "+((strAux=txtCodUniMed.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
                "  "+idUnidad+", "+        
        "  "+((strAux=txtItmUni.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " '"+chrItmSer+"', "+
        "  "+((strAux=txtCosUni.getText().replaceAll("'", "''")).equals("")?"0":strAux )+",0, "+
        "  "+((strAux=txtCosUni1.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtCosUni2.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtCosUni3.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtStkAct.getText().replaceAll("'", "''")).equals("")?"Null":strAux)+", "+
        "  "+((strAux=txtStkMin.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txtStkMax.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " '"+chrIvaCom+"', '"+chrIvaVta+"', "+
        "  "+((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  '"+chrEst+"', "+objZafParSis.getCodigoUsuario()+", "+objZafParSis.getCodigoUsuario()+", '"+strFecUltMod+"', '"+strFecUltMod+"',  "+
        "  "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', "+
        "  '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "+
        "  "+(strCamPreVta.equals("")?null:"'"+strCamPreVta+"'")+" ,"+
        "  "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+" ,'"+ txtCodGP.getText() +"'"+
        "  "+" ); ";
        stmInv.executeUpdate(strSql);
        
        
        
        strSql="INSERT INTO tbm_equinv (co_itmmae, co_emp, co_itm) values ("+intCodEqu +", "+rstEmp.getInt("co_emp") +","+intCodItm+")";
        stmInv.executeUpdate(strSql);

        strSql="SELECT co_bod FROM tbm_bod WHERE co_emp="+rstEmp.getInt("co_emp")+" ORDER BY co_bod ";
        rstLoc=stmInv.executeQuery(strSql);
        while(rstLoc.next()){
            
            if(!creaItemBodega(conn, rstEmp.getInt("co_emp"), intCodItm,  rstLoc.getInt("co_bod") )){
                intControl=1;
                break;
            }

        }
        rstLoc.close();
        rstLoc=null;

////        if(rstEmp.getInt("co_emp")==objZafParSis.getCodigoEmpresa()){
////         if(!insertarPrvItm(conn, rstEmp.getInt("co_emp"), intCodItm )){
////            intControl=1;
////            break;
////         }
////        }

////        if(!insertarClaItm(conn, rstEmp.getInt("co_emp"), intCodItm )){
////            intControl=1;
////            break;
////
////         }

    }
    //tony implementacion historico de inventario
    objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
    rstEmp.close();
    rstEmp=null;

    stmInv.close();
    stmInv=null;
    stmEmp.close();
    stmEmp=null;
    if(intControl==0) blnRes=true;
   

  }catch(SQLException evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
   catch(Exception evt){  blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}

public boolean creaItemBodega(java.sql.Connection conn,int intCodEmp,int intCodItm, int intCodBod){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   try{
      stmLoc=conn.createStatement();
      strSql="INSERT INTO tbm_invbod(co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
      " VALUES("+intCodEmp+", "+intCodBod+", "+intCodItm+ ", 0, 0, 0)";
      stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
   }catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

public boolean _creaInvMovEmpGrp(java.sql.Connection conn, int intCodEqu ){
 boolean blnRes=false;
 java.sql.Statement stmLoc,stmLoc01, stmLoc02;
 java.sql.ResultSet rstLoc,rstLoc01;
 String strSql="";
 try{
   stmLoc=conn.createStatement();
   stmLoc01=conn.createStatement();
   stmLoc02=conn.createStatement();

   strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae="+intCodEqu+" ";
   rstLoc=stmLoc.executeQuery(strSql);
   while(rstLoc.next()){

      strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae="+intCodEqu+" " +
      " AND co_emp NOT IN ("+rstLoc.getString("co_emp")+") AND co_itm NOT IN ( "+rstLoc.getString("co_itm")+" ) ";
      rstLoc01 = stmLoc01.executeQuery(strSql);
      while(rstLoc01.next()){

        strSql="INSERT INTO tbm_invmovempgrp(co_emp, co_itm, co_emprel, co_itmrel, nd_salven, nd_salcom, st_regrep) " +
        "  VALUES("+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_itm")+", "+rstLoc01.getString("co_emp")+", "+rstLoc01.getString("co_itm")+", 0.00, 0.00, 'I' ) ";
        stmLoc02.executeUpdate(strSql);

      }
      rstLoc01.close();
      rstLoc01=null;
   }
   rstLoc.close();
   rstLoc=null;

  stmLoc.close();
  stmLoc=null;
  stmLoc01.close();
  stmLoc01=null;
  stmLoc02.close();
  stmLoc02=null;
  blnRes=true;

  }catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  return blnRes;
}

private boolean validadCodAlt(java.sql.Connection conn, String strCodAlt, String strCampCodAlt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                strSql=" SELECT tx_codAlt FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND UPPER("+strCampCodAlt+")='"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"'";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }  
        } 
        catch(SQLException e){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, e);   
        }
        catch(Exception e){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(this, e);   
        }
        return blnRes;
    }

public String randomString(String chars, int len) {
    Random rnd = new Random();

    StringBuilder sb = new StringBuilder( len );
    for( int i = 0; i < len; i++ ) {
       sb.append( chars.charAt( rnd.nextInt(chars.length()) ) );            
    }
    return sb.toString();
 }
////    
////public boolean insertarClaItm(java.sql.Connection conn,int intCodEmp,int intCodItm ){
////   boolean blnRes=true;
////   java.sql.Statement stmLoc;
////   strSql="";
////   String strEst="";
////   try{
////      stmLoc=conn.createStatement();
////
////       for(int i=0; i<tblCla.getRowCount(); i++){
////        if(tblCla.getValueAt(i, INT_TBL_CLACOD) != null){
////         if(!tblCla.getValueAt(i, INT_TBL_CLACOD).toString().equals("")){
////
////               strEst="A";
////                if(tblCla.getValueAt( i, INT_TBL_CLAACT)!=null){
////                   if(tblCla.getValueAt( i, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
////                        strEst="A";
////                }
////                if(tblCla.getValueAt( i, INT_TBL_CLAINA)!=null){
////                  if(tblCla.getValueAt( i, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
////                         strEst="I";
////                }
////
////               strSql+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing, co_grp ) VALUES" +
////               " ("+intCodEmp+","+ intCodItm +", "+tblCla.getValueAt(i, INT_TBL_CLACOD).toString()+" "+
////               " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+" , "+tblCla.getValueAt( i, INT_TBL_CODGRP)+" ) ; ";
////       }}}
////
////     
////      if(!strSql.equals(""))
////          stmLoc.executeUpdate(strSql);
////
////      stmLoc.close();
////      stmLoc=null;
////   
////   }catch(java.sql.SQLException Evt){ blnRes=false;   MensajeInf("Error. Al Insertar Clasificaciones posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
////    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
////    return blnRes;
////}

private boolean consultarDatInv(String strFlt){
 boolean blnRes=false;
 strFil = strFlt;
 try{
  if(abrirConRem()){
   if(conRemGlo!=null){

    stmCab = conRemGlo.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

////    strSql="SELECT * FROM tbm_inv WHERE st_reg not in ('T','E','U') and co_emp="+objZafParSis.getCodigoEmpresa()+" "+
////    " "+strFil+" ORDER BY co_itm ";
////    strSql="SELECT * FROM tbm_inv WHERE st_reg not in ('T','E','U') and co_emp="+objZafParSis.getCodigoEmpresa()+" "+
////    " "+strFil+" ORDER BY co_itm ";
        strSql="";
    
        strSql+="  SELECT trim(a1.ITEMNMBR) as co_itmMae, trim(a1.ITEMNMBR) as co_itm, trim(a1.ITEMNMBR) as tx_codAlt, trim(a1.ITMGEDSC) as tx_codAlt2, trim(a1.ITEMNMBR) as tx_codAltDyn, trim(a1.ITEMDESC) as tx_nomItm, \n"
            + "  1 as co_reg, trim(a1.UOMSCHDL) as tx_desCor, 'N' as st_ser, a2.QTYONHND as nd_stkact,a4.UOMPRICE as nd_prevta1, a3.nd_marUti,a7.DATA as nd_pesItmKgr,0 as st_revTec,trim(a12.BASEUOFM) as tx_cortaUnid  \n";
        strSql+=", a1.CURRCOST as nd_cosuni, a3.nd_canMaxVen, a3.nd_facCosUni, a3.fe_ultmod ,a12.DEX_ROW_ID as codUnidad, a4.PRCLEVEL, a1.CREATDDT as fechaCrea,a1.USERID as usuariocrea  \n";
        strSql+=", a4.PRCLEVEL,trim(isnull(a8.MSW_ADI_IV_119_Marca,'NO TIENE')) as marca,trim(isnull(a9.MSW_ADI_IV_120_Material,'NO TIENE')) as material  \n";
        strSql+=", trim(isnull(a10.MSW_ADI_IV_117_Referencia_General,'NO TIENE')) as Referencia_General,trim(isnull(a11.MSW_ADI_IV_118_Referencia_Detallada,'NO TIENE')) as Referencia_Detallada     \n";
        strSql+=", ISNULL(a13.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MARCA' AND UD_Form_Field_ID ='NO TIENE')) as IDMarca  \n";
        strSql+=", ISNULL(a14.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MATERIAL' AND UD_Form_Field_ID ='NO TIENE')) as IDMaterial  \n";
        strSql+=", ISNULL(a15.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_GENERAL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefGene  \n";
        strSql+=", ISNULL(a16.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_DETALL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefDe  \n";
        strSql+=" , a4.PRCLEVEL,a1.ITMGEDSC  \n";
        strSql+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1 \n";
        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00102 as a2 on (a1.ITEMNMBR =a2.ITEMNMBR) \n";
        strSql+="       left OUTER join dynamics2021..tbm_inv as a3 on (a1.ITEMNMBR =a3.tx_itm) \n";
        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00108 as a4 on (a1.ITEMNMBR =a4.ITEMNMBR) \n";
        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e103.TOTAL as data,e100.Extender_Key_Values_1 as item  \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01103 as e103 on (e100.Extender_Record_ID = e103.Extender_Record_ID) \n";
        strSql+="            WHERE e103.Field_ID =179  ) a7 on (a1.ITEMNMBR =a7.item) --peso \n";        
        
        
        
////        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..ALT_MIS_INFOADD as a7 on (a1.ITEMNMBR =a7.MASTERID) \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_119_Marca,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =119 ) as a8 on (a1.ITEMNMBR =a8.Extender_Key_Values_1)--marca \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_120_Material,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =120 ) as a9 on (a1.ITEMNMBR =a9.Extender_Key_Values_1)--material \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_119_Referencia_General,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =119 ) as a10 on (a1.ITEMNMBR =a10.Extender_Key_Values_1)--Referencia General \n";        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_118_Referencia_Detallada,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =118 ) as a11 on (a1.ITEMNMBR =a11.Extender_Key_Values_1)--Referencia Detallado \n";   
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40201 as a12 on (a1.UOMSCHDL =a12.UOMSCHDL) \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a13 on (a8.MSW_ADI_IV_119_Marca =a13.UD_Form_Field_ID and a13.Extender_Form_ID = 'MSW_MARCA') \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a14 on (a9.MSW_ADI_IV_120_Material =a14.UD_Form_Field_ID and a14.Extender_Form_ID = 'MSW_MATERIAL') \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a15 on (a10.MSW_ADI_IV_117_Referencia_General =a15.UD_Form_Field_ID and a15.Extender_Form_ID = 'MSW_REF_GENERAL')  \n";        
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a16 on (a11.MSW_ADI_IV_118_Referencia_Detallada =a16.UD_Form_Field_ID and a16.Extender_Form_ID = 'MSW_REF_DETALL')  \n";        
        
        strSql+=" WHERE  a2.RCRDTYPE =1  and a4.PRCLEVEL ='GENERAL' " ;        
        strSql+=" ORDER BY   a3.fe_ultmod desc" ;
        
    
    rstCab = stmCab.executeQuery(strSql);
    conRemGlo.close();
    conRemGlo=null;
    if(rstCab.next()){
        rstCab.last();
        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros.");
        cargarReg();
        blnRes=true;
    }
    else{
        objTooBar.setMenSis("Se encontraron 0 registros...");
        strMsg="No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        rstCab = null;
        stmCab.close();
        conCab.close();
        stmCab=null;
        conCab=null;
        limpiarDatInv();
        blnRes=false;
    }
   blnCmb = false;
   
  }}}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}
////

private boolean consultarDatInvGP(String strFlt){
 boolean blnRes=false;
////java.sql.Statement stmLoc;
////java.sql.Connection conExt;
////java.sql.ResultSet rstLoc;  
 strFil = strFlt;
 try{
  if(abrirConRemGP()){
////    conRemGloGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
   if(conRemGloGP!=null){
       
//////       if(!isVersionActualizada()){
//////            strMsg="Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..";
//////            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
//////        dispose();
//////    }
       
       

    stmLocG=conRemGloGP.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        strSql="";
        strSql="  SELECT trim(a1.ITEMNMBR) as co_itmMae, trim(a1.ITEMNMBR) as co_itm, trim(a1.ITEMNMBR) as tx_codAlt, trim(a1.ITMGEDSC) as tx_codAlt2, trim(a1.ITEMNMBR) as tx_codAltDyn, trim(a1.ITEMDESC) as tx_nomItm, \n"
            + "  1 as co_reg, trim(a1.UOMSCHDL) as tx_desCor, 'N' as st_ser, a2.QTYONHND as nd_stkact,a4.UOMPRICE as nd_prevta1, a3.nd_marUti,a7.DATA as nd_pesItmKgr,0 as st_revTec ,trim(a12.BASEUOFM) as tx_cortaUnid  \n";
        strSql+=", a1.CURRCOST as nd_cosuni, a3.nd_canMaxVen, a3.nd_facCosUni, a3.fe_ultmod,a12.DEX_ROW_ID as codUnidad, a4.PRCLEVEL , a1.CREATDDT as fechaCrea,a1.USERID as usuariocrea   \n";
        strSql+=",CASE WHEN a8.MSW_ADI_IV_119_Marca ='' THEN 'NO TIENE' ELSE trim(a8.MSW_ADI_IV_119_Marca) end as marca \n";
        strSql+=",CASE WHEN a9.MSW_ADI_IV_120_Material ='' THEN 'NO TIENE' ELSE trim(a9.MSW_ADI_IV_120_Material) end as material \n";
        strSql+=",CASE WHEN a18.MSW_ADI_IV_121_TipoCompra ='' THEN 'NO TIENE' ELSE trim(a18.MSW_ADI_IV_121_TipoCompra) end as tipocompra \n";
        strSql+=",CASE WHEN a10.MSW_ADI_IV_117_Referencia_General ='' THEN 'NO TIENE' ELSE trim(a10.MSW_ADI_IV_117_Referencia_General) end as Referencia_General \n";
        strSql+=",CASE WHEN a11.MSW_ADI_IV_118_Referencia_Detallada ='' THEN 'NO TIENE' ELSE trim(a11.MSW_ADI_IV_118_Referencia_Detallada) end as Referencia_Detallada \n";
        /////////////////////////
        strSql+=",CASE WHEN a20.MSW_ADI_IV_196_Linea ='' THEN 'NO_TIENE' ELSE trim(a20.MSW_ADI_IV_196_Linea) end as Linea \n";
        strSql+=",CASE WHEN a21.MSW_ADI_IV_197_Clase ='' THEN 'NO_TIENE' ELSE trim(a21.MSW_ADI_IV_197_Clase) end as Clase \n"; 
        strSql+=",CASE WHEN a99.MSW_ADI_IV_199_Compras ='' THEN '' ELSE trim(a99.MSW_ADI_IV_199_Compras) end as Compra \n"; 
        strSql+=",CASE WHEN a999.MSW_ADI_IV_204_Categoria ='' THEN 'NO_TIENE' ELSE trim(a999.MSW_ADI_IV_204_Categoria) end as Categoria \n";      
        strSql+=",CASE WHEN extCatCom.det ='' THEN 'NO_TIENE' ELSE trim(extCatCom.det) end as CategoriaComp \n";         
        strSql+=",CASE WHEN extClaCom.det ='' THEN 'NO_TIENE' ELSE trim(extClaCom.det) end as ClaseComp \n";  
        strSql+=",CASE WHEN extLinCom.det ='' THEN 'NO_TIENE' ELSE trim(extLinCom.det) end as LineaComp \n";  
        strSql+=",trim(a111.MSW_ADI_IV_203_Control ) as Control \n";         
        
        /////////////////////////
        strSql+=", ISNULL(a13.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MARCA' AND UD_Form_Field_ID ='NO TIENE')) as IDMarca\n";
        strSql+=", ISNULL(a14.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MATERIAL' AND UD_Form_Field_ID ='NO TIENE')) as IDMaterial      \n";
        strSql+=", ISNULL(a15.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_GENERAL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefGe\n";
        strSql+=", ISNULL(a16.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_DETALL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefDe\n";
        ////////////////////////////////////
        strSql+=", ISNULL(a22.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_LINEAS' AND UD_Form_Field_ID ='NO_TIENE')) as IDLinea\n";
        strSql+=", ISNULL(a23.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CLASE_SUB' AND UD_Form_Field_ID ='NO_TIENE')) as IDClase\n";
        strSql+=", ISNULL(a991.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_COMPRAS' AND UD_Form_Field_ID ='')) as IDCompra\n";
        strSql+=", ISNULL(a9993.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CATEGORIA' AND UD_Form_Field_ID ='NO_TIENE')) as IDCategoria\n";        
        strSql+=", a9994.Extender_Record_ID as IDControl\n";                
        strSql+=", ISNULL(extcatcom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CAT_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDCategoriaCompra\n";                
        strSql+=", ISNULL(extclacom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CLA_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDClaseCompra\n"; 
        strSql+=", ISNULL(extlincom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_LIN_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDLineaCompra\n"; 
        
        
        ///////////////////////////////////
        
        strSql+=", a4.PRCLEVEL,a3.fe_ultmod as fecha, a3.tx_usrmod as usuario, TRIM(ITMSHNAM) AS codZafiro,a1.inactive as estado,trim(ISNULL(a17.MSW_ADI_IV_181_Tamano, '')) as tamano \n";
        //////////////////////////////////
        strSql+=",isnull(abcF.co_nivpar,0) as codABCF,isnull(abcF.tx_nivpar,'') as txABCF \n";                        
        strSql+=",isnull(abcP.co_nivpar,0) as codABCP,isnull(abcP.tx_nivpar,'') as txABCP \n";
        strSql+=",isnull(abcV.co_nivpar,0) as codABCV,isnull(abcV.tx_nivpar,'') as txABCV \n";
        strSql+=",isnull(abcT.co_nivpar,0) as codABCT,isnull(abcT.tx_nivpar,'') as txABCT \n";
        strSql+=",isnull(abcC.co_nivpar,0) as codABCC,isnull(abcC.tx_nivpar,'') as txABCC \n";        
        
        strSql+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1 \n";
        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00102 as a2 on (a1.ITEMNMBR =a2.ITEMNMBR) \n";
        strSql+="       left OUTER join dynamics2021..tbm_inv as a3 on (a1.ITEMNMBR =a3.tx_itm) \n";
        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00108 as a4 on (a1.ITEMNMBR =a4.ITEMNMBR) \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e103.TOTAL as data,e100.Extender_Key_Values_1 as item  \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01103 as e103 on (e100.Extender_Record_ID = e103.Extender_Record_ID) \n";
        strSql+="            WHERE e103.Field_ID =179  ) a7 on (a1.ITEMNMBR =a7.item) --peso \n";         
////        strSql+="       left OUTER join "+ objZafParSis.getNombreBaseDatosGP()  +"..ALT_MIS_INFOADD as a7 on (a1.ITEMNMBR =a7.MASTERID) \n";

        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_119_Marca,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =119 ) as a8 on (a1.ITEMNMBR =a8.Extender_Key_Values_1)--marca \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_120_Material,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =120 ) as a9 on (a1.ITEMNMBR =a9.Extender_Key_Values_1)--material \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_117_Referencia_General,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =117 ) as a10 on (a1.ITEMNMBR =a10.Extender_Key_Values_1)--Referencia General \n";        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_118_Referencia_Detallada,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =118 ) as a11 on (a1.ITEMNMBR =a11.Extender_Key_Values_1)--Referencia Detallado \n";
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_181_Tamano,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =181 ) as a17 on (a1.ITEMNMBR =a17.Extender_Key_Values_1)--Tamano \n";  
        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_121_TipoCompra,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =121 ) as a18 on (a1.ITEMNMBR =a18.Extender_Key_Values_1)--Tipo_Compra \n";     
///////////////////////////
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_196_Linea,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =196 ) as a20 on (a1.ITEMNMBR =a20.Extender_Key_Values_1)--Linea \n";       
        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_197_Clase,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =197 ) as a21 on (a1.ITEMNMBR =a21.Extender_Key_Values_1)--Clase \n";       
        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_199_Compras,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =199 ) as a99 on (a1.ITEMNMBR =a99.Extender_Key_Values_1)--Compras \n";          
        
        
        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_204_Categoria,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =204 ) as a999 on (a1.ITEMNMBR =a999.Extender_Key_Values_1)--Categoria \n";     

        strSql+="       left outer join ( \n";
        strSql+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_203_Control,e100.Extender_Key_Values_1 \n";
        strSql+="            FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
        strSql+="                 left join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
        strSql+="            WHERE e101.Field_ID =203 ) as a111 on (a1.ITEMNMBR =a111.Extender_Key_Values_1)--CONTROL \n";         
        
        strSql+="       left outer join ( \n";
        strSql+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
        strSql+="                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
        strSql+="                               LEFT JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
        strSql+="                               INNER JOIN ( \n";          
        strSql+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
        strSql+="                                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
        strSql+="                                               INNER JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
        strSql+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
        strSql+="                          WHERE e199.Field_ID = 206 \n";         
        strSql+="                       ) AS extCatCom    ON (a1.ITEMNMBR = extCatCom.itm) --CATEGORIACOMPRA \n";
        
        strSql+="       left outer join ( \n";
        strSql+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
        strSql+="                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
        strSql+="                               LEFT JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
        strSql+="                               INNER JOIN ( \n";          
        strSql+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
        strSql+="                                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
        strSql+="                                               INNER JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
        strSql+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
        strSql+="                          WHERE e199.Field_ID = 208 \n";         
        strSql+="                       ) AS extClaCom    ON (a1.ITEMNMBR = extClaCom.itm) --CLASECOMPRA \n";   
        
        strSql+="       left outer join ( \n";
        strSql+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
        strSql+="                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
        strSql+="                               LEFT JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
        strSql+="                               INNER JOIN ( \n";          
        strSql+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
        strSql+="                                           FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
        strSql+="                                               INNER JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
        strSql+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
        strSql+="                          WHERE e199.Field_ID = 207 \n";         
        strSql+="                       ) AS extLinCom    ON (a1.ITEMNMBR = extLinCom.itm) --LINEACOMPRA \n";        
        


//////////////////////////
        
        
        
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..IV40201 as a12 on (a1.UOMSCHDL =a12.UOMSCHDL) \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a13 on (a8.MSW_ADI_IV_119_Marca =a13.UD_Form_Field_ID and a13.Extender_Form_ID = 'MSW_MARCA') \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a14 on (a9.MSW_ADI_IV_120_Material =a14.UD_Form_Field_ID and a14.Extender_Form_ID = 'MSW_MATERIAL') \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a15 on (a10.MSW_ADI_IV_117_Referencia_General =a15.UD_Form_Field_ID and a15.Extender_Form_ID = 'MSW_REF_GENERAL')  \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a16 on (a11.MSW_ADI_IV_118_Referencia_Detallada =a16.UD_Form_Field_ID and a16.Extender_Form_ID = 'MSW_REF_DETALL')  \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a19 on (a18.MSW_ADI_IV_121_TipoCompra =a19.UD_Form_Field_ID and a16.Extender_Form_ID = 'MSW_TIPO_COMPRA')  \n";        
        
        ////////////////
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a22 on (a20.MSW_ADI_IV_196_Linea =a22.UD_Form_Field_ID and a22.Extender_Form_ID = 'TUV_LINEAS')  \n";  
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a23 on (a21.MSW_ADI_IV_197_Clase =a23.UD_Form_Field_ID and a23.Extender_Form_ID = 'TUV_CLASE_SUB')  \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a991 on (a99.MSW_ADI_IV_199_Compras =a991.UD_Form_Field_ID and a991.Extender_Form_ID = 'TUV_COMPRAS')  \n";
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a9993 on (a999.MSW_ADI_IV_204_Categoria =a9993.UD_Form_Field_ID and a9993.Extender_Form_ID = 'TUV_CATEGORIA')  \n";        
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as a9994 on (a111.MSW_ADI_IV_203_Control =a9994.UD_Form_Field_ID and a9994.Extender_Form_ID = 'TUV_CONTROL')  \n";                
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as extcatcom2 on (extCatCom.det =extcatcom2.UD_Form_Field_Desc and extcatcom2.Extender_Form_ID = 'TUV_CAT_COM')  \n";                         
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as extclacom2 on (extClaCom.det =extclacom2.UD_Form_Field_Desc and extclacom2.Extender_Form_ID = 'TUV_CLA_COM')  \n";  
        strSql+="       left outer join "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01200 as extlincom2 on (extlinCom.det =extlincom2.UD_Form_Field_Desc and extlincom2.Extender_Form_ID = 'TUV_LIN_COM')  \n";  
        
        ///////////////        
        strSql+="       left join ( \n";
        strSql+="            SELECT conf.tx_itm, conf.co_nivpar, niv.tx_nivpar \n";
        strSql+="            FROM   dynamics2021..tbr_itmpar as conf \n";
        strSql+="                 INNER JOIN Dynamics2021..tbm_nivpar as niv on (conf.co_nivpar = niv.co_nivpar) \n";
        strSql+="            WHERE niv.st_reg='A' and conf.co_catpar=4 ) as abcT on  (a1.ITEMNMBR = abcT.tx_itm)--ABC TUVAL \n"; 
        
        //////////////
        strSql+="       left join ( \n";
        strSql+="            SELECT conf.tx_itm, conf.co_nivpar, niv.tx_nivpar \n";
        strSql+="            FROM   dynamics2021..tbr_itmpar as conf \n";
        strSql+="                 INNER JOIN Dynamics2021..tbm_nivpar as niv on (conf.co_nivpar = niv.co_nivpar) \n";
        strSql+="            WHERE niv.st_reg='A' and conf.co_catpar=1 ) as abcF on  (a1.ITEMNMBR = abcF.tx_itm)--ABC FRECUENCIA \n"; 
        
        ///////////////        
        strSql+="       left join ( \n";
        strSql+="            SELECT conf.tx_itm, conf.co_nivpar, niv.tx_nivpar \n";
        strSql+="            FROM   dynamics2021..tbr_itmpar as conf \n";
        strSql+="                 INNER JOIN Dynamics2021..tbm_nivpar as niv on (conf.co_nivpar = niv.co_nivpar) \n";
        strSql+="            WHERE niv.st_reg='A' and conf.co_catpar=2 ) as abcP on  (a1.ITEMNMBR = abcP.tx_itm)--ABC PESO \n"; 
        
        //////////////
        strSql+="       left join ( \n";
        strSql+="            SELECT conf.tx_itm, conf.co_nivpar, niv.tx_nivpar \n";
        strSql+="            FROM   dynamics2021..tbr_itmpar as conf \n";
        strSql+="                 INNER JOIN Dynamics2021..tbm_nivpar as niv on (conf.co_nivpar = niv.co_nivpar) \n";
        strSql+="            WHERE niv.st_reg='A' and conf.co_catpar=3 ) as abcV on  (a1.ITEMNMBR = abcV.tx_itm)--ABC VALOR \n";         
        
        //////////////
        strSql+="       left join ( \n";
        strSql+="            SELECT conf.tx_itm, conf.co_nivpar, niv.tx_nivpar \n";
        strSql+="            FROM   dynamics2021..tbr_itmpar as conf \n";
        strSql+="                 INNER JOIN Dynamics2021..tbm_nivpar as niv on (conf.co_nivpar = niv.co_nivpar) \n";
        strSql+="            WHERE niv.st_reg='A' and conf.co_catpar=5 ) as abcC on  (a1.ITEMNMBR = abcC.tx_itm)--ABC CLASE \n";           


        if (strFil.equals(""))
            strSql+=" WHERE  a2.RCRDTYPE =1  and a4.PRCLEVEL ='GENERAL' ";
        else
            strSql+=" WHERE  a2.RCRDTYPE =1  and a4.PRCLEVEL ='GENERAL'   "+ strFil  +"";
        
        strSql+=" ORDER BY   a3.fe_ultmod asc" ;        
        
        
    
    rstLocG=stmLocG.executeQuery(strSql);

    if(rstLocG.next()){
        rstLocG.last();
        objTooBar.setMenSis("Se encontraron " + rstLocG.getRow() + " registros.");
        cargarReg();
        blnRes=true;
    }
    else{
        objTooBar.setMenSis("Se encontraron 0 registros...");
        strMsg="No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        rstLocG = null;
        stmLocG.close();
        rstLocG.close();
        stmLocG=null;
////        conCab=null;
////        conCab=null;
        limpiarDatInv();
        blnRes=false;        
    }
////    conRemGloGPUno.close();
////    conRemGloGPUno=null;
    
        


   blnCmb = false;
  }
  }
 }catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}




    /**FunciÃ³n que me permÃ­te guardar los datos del Articulo*/
public boolean guardarDatInv(){
        String strFecUltMod;
         datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
        /**Validar que el CÃ³digo alterno no exista duplicado.*/
        strSql = "";
        strSql = strSql + " SELECT tx_codAlt";
        strSql = strSql + " FROM tbm_inv";
        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
        strSql = strSql + " and UPPER(tx_codAlt) = '" + txtCodAlt.getText().trim().replaceAll("'", "''").toUpperCase() + "'";
        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql)){ 
            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);
            return false;
        }      
        
        
        strSql = "";
        strSql = strSql + " SELECT tx_codAlt2";
        strSql = strSql + " FROM tbm_inv";
        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
        strSql = strSql + " and UPPER(tx_codAlt2) = '" + txtCodAlt2.getText().trim().replaceAll("'", "''").toUpperCase() + "'";
        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql)){ 
            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);
            return false;
        }      
        
        
        
        
       if (txtNomItm.getText().equals("")) {
            strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            tabGenInv.setSelectedIndex(0);
            txtNomItm.requestFocus();
            return false;}
        else if (!this.chkEsItmSer.isSelected()){
            if (!this.chkIvaCom.isSelected()&&!this.chkIvaVta.isSelected()){
                strMsg = "El campo  <<IVA>> es obligatorio.\nSeleccione una clase de IVA y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(0);
                chkIvaCom.requestFocus(); 
                return false;}   
        }
        else if (txtItmUni.getText().length()>6){
            valTxt(txtItmUni, txtItmUni, "Item(s) por Unidad", "", 2, "el", 0);
            return false;
        }
        if (!txtStkMin.getText().equals("") && !txtStkMin.getText().equals("")){
            if (Integer.parseInt(txtStkMin.getText())>=Integer.parseInt(txtStkMax.getText())) {
                valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1);
                return false;
            }
        }

        /**Almacena el estado que haya seleccionado el usuario en una variable*/
        char chrEst,chrItmSer,chrIvaCom,chrIvaVta;

        /**Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo*/
        if (cboEst.getSelectedIndex()==0)
            chrEst= 'A';
        else
            chrEst= 'I';

            try
            {
                java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                java.sql.PreparedStatement pstInv;  
              try{
                if (con!=null)
                {
                    java.sql.Statement stm = con.createStatement();
                    strSql = "";
                    strSql = strSql + "SELECT case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp = " + objZafParSis.getCodigoEmpresa();
                    java.sql.ResultSet rst = stm.executeQuery(strSql);

                    /**Bloque que almacena el numero de registro que toca guardar, caso contrario
                     *que no haya registros en la tabla automaticamente guarda en esta !*/
                    int intNumReg=0;
                    if (rst.next())
                        intNumReg=rst.getInt("co_itm");
                   


                    /**Bloque que almacena en una variable de tipo char si es item de servicio
                     *el articulo segun si han marcado la casilla o no*/

                    chrItmSer = 'N';
                      
                    if (chkEsItmSer.isSelected())
                        chrItmSer = 'S';
                   
                    if (chkEsItmTra.isSelected())
                        chrItmSer = 'T';

                     if (chkEsItmOtr.isSelected())
                        chrItmSer = 'O';
                   
                    
                    
                    /**Bloque que almacena en una variable de tipo char si el articulo
                     *tiene Iva en Compras o no*/             
                    if (chkIvaCom.isSelected())
                        chrIvaCom = 'S';
                    else 
                        chrIvaCom = 'N';

                    /**Bloque que almacena en una variable de tipo char si el articulo
                     *tiene Iva en Ventas o no*/             
                    if (chkIvaVta.isSelected())
                        chrIvaVta = 'S';
                    else 
                        chrIvaVta = 'N';    
                    
                    strAux = txtCodAlt.getText().replaceAll("'", "''");
                    if(strAux.equals(""))
                      strAux="Null";
                    String strAux2="Null"; 
                    
                      strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
                         if(strAux2.equals(""))
                           strAux2="Null";
                    
                    if(txtCodAlt2.getText().trim().equals(""))
                       strAux2=strAux; 
                   
                    
                    /**Haciendo el Insert en la tabla*/
                    strSql="";
                    strSql+="INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_cla1, co_cla2, co_cla3, co_uni";
                    strSql+=", nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
                    strSql+=", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng,co_usrmod, fe_ing, fe_ultmod, ";
                    strSql+="  nd_pesitmkgr, st_permodnomitmven, st_permodnomitmcom, st_oblCamNomItm,tx_codaltdyn) ";
                    strSql+=" VALUES (" + objZafParSis.getCodigoEmpresa() + ", " +  intNumReg  + "";
                    strSql+=", " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
                    strSql+=", '" + strAux2.toUpperCase() + "'";
                
                    strSql+=", " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                    if (txtCodLin.getText().equals(""))//cambiar por material
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodLin.getText().replaceAll("'", "''") + "";}

                    if (txtCodGrp.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodGrp.getText().replaceAll("'", "''") + "";}

                    if (txtCodMar.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodMar.getText().replaceAll("'", "''") + "";}

                    if (txtCodUniMed.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodUniMed.getText().replaceAll("'", "''") + "";}

                    if (txtItmUni.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtItmUni.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrItmSer + "'";

                    if (txtCosUni.getText().equals(""))
                        strSql+=", 0,0";
                    else{
                        strSql+=", " + txtCosUni.getText().replaceAll("'", "''") + ",0";}

                    if (txtCosUni1.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni1.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni2.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni2.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni3.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni3.getText().replaceAll("'", "''") + "";}

                    if (txtStkAct.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkAct.getText().replaceAll("'", "''") + "";}

                    if (txtStkMin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMin.getText().replaceAll("'", "''") + "";}

                    if (txtStkMax.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMax.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrIvaCom + "', '" + chrIvaVta + "'";
                    strSql+=", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", '" + chrEst + "', " + objZafParSis.getCodigoUsuario() + ", " + objZafParSis.getCodigoUsuario() + ", '" + strFecUltMod + "',  '" + strFecUltMod + "' " +
                    " , "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "; 
                    strSql+=" "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+",'"+ txtCodGP.getText() +"'";
                    strSql+=" )";
                    //strSql = strSql + ", '" + objUti.getFecha() + "')";   
                    //System.out.println(strSql);   
                    //stm.executeUpdate(strSql);
                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    int intCodEqu  = getCodItemMaestro(con);
                    if (intCodEqu == 0){
                        MensajeInf("Problemas al crear codigo de item consolidado");
                        return false;
                    }
                    objHisTblBasDat.insertarHistoricoIndividual(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());

                    strSql = "";
                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + objZafParSis.getCodigoEmpresa() +","+ intNumReg + ")";
                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    CrearItemEmpresas(con,intCodEqu);
                    //Creacion de Inventario Bodega
                    
                    strSql = " Select co_bod from tbm_bod where co_emp ="+objZafParSis.getCodigoEmpresa()+"  and co_bod not in " +
                             " (select co_bod from tbm_invbod where co_emp ="+objZafParSis.getCodigoEmpresa()+" and co_itm ="+ intNumReg+") " +
                             " order by co_bod ";
                    rst = stm.executeQuery(strSql);                    
                    while(rst.next()){
                        if (!objInventario.creaItemBodega(con,objZafParSis.getCodigoEmpresa(),intNumReg,rst.getInt("co_bod"))){
                            con.rollback();
                            con.close();
                            return false;
                        }
                    }   
                    rst.close();
                    rst=null;
                      
                    java.sql.ResultSet rstLoc;
                    java.sql.Statement stmLoc=con.createStatement();
                    java.sql.Statement stmLoc01=con.createStatement();
                    strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae IN( SELECT co_itmmae FROM tbm_equinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intNumReg+" ) ";
                    rst = stm.executeQuery(strSql);                    
                    while(rst.next()){

                     strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae " +
                     " IN( SELECT co_itmmae FROM tbm_equinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intNumReg+" ) " +
                     " AND co_emp NOT IN ("+rst.getString("co_emp")+") AND co_itm NOT IN ( "+rst.getString("co_itm")+" ) ";
                     rstLoc = stmLoc.executeQuery(strSql);                    
                     while(rstLoc.next()){

                        strSql="INSERT INTO tbm_invmovempgrp(co_emp, co_itm, co_emprel, co_itmrel, nd_sal, st_regrep) " +
                        "  VALUES("+rst.getString("co_emp")+", "+rst.getString("co_itm")+", "+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_itm")+", 0.00, 'I' ) ";
                        stmLoc01.executeUpdate(strSql);
                        
                     }   
                     rstLoc.close();
                     rstLoc=null;
                   }
                    
                   stmLoc.close();
                   stmLoc=null;
                   stmLoc01.close();
                   stmLoc01=null;
                    
                    
                    con.commit();
                    con.setAutoCommit(true);
                    stm.close();
                    rst.close();
                    con.close();
                   cboEst.setSelectedIndex(0);
                    txtStkAct.setText("0");
                    txtCodItm.setEditable(false);
////                    butCod.setEnabled(false);CHIQUI
                    chkIvaCom.setSelected(true);
                    chkIvaVta.setSelected(true);
                    txtCodAlt.requestFocus();
                   
                    
                    
                    
                   


                    
                    
                    
                }  
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }

private int getCodItemMaestro(java.sql.Connection conn){
 int intRes = 0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{
  if(conn!=null){
    stmLoc = conn.createStatement();
    strSQL="SELECT  CASE WHEN MAX(co_itmmae) is null THEN 1 else MAX(co_itmmae)+1 END AS co_itmmae FROM tbm_equinv";
    rstLoc=stmLoc.executeQuery(strSQL);
    if(rstLoc.next()){
      intRes = rstLoc.getInt("co_itmmae");
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return intRes;
}



    
    

private boolean CrearItemEmpresas(java.sql.Connection conInv,int intCodEqu){
 boolean blnRes=false;
 java.sql.PreparedStatement pstInv;
 java.sql.Statement stmInv,stmEmp;
 java.sql.ResultSet rstInv,rstEmp;
 String strFecUltMod;
 int intCodItm=0;
 char chrEst,chrItmSer= 'N',chrIvaCom,chrIvaVta;
 try{
    stmEmp = conInv.createStatement();
    stmInv = conInv.createStatement();

    strSql="Select co_emp from tbm_emp where co_emp <> "+objZafParSis.getCodigoEmpresa()+" and st_reg='A' order by co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    while(rstEmp.next()){

       strSql="select case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp="+rstEmp.getString("co_emp");
       rstInv=stmInv.executeQuery(strSql);
       if(rstInv.next())
          intCodItm = rstInv.getInt("co_itm");
       rstInv.close();
       rstInv=null;

       if(cboEst.getSelectedIndex()==0)
                        chrEst= 'A';
                    else
                        chrEst= 'I';
                
                    if (chkEsItmSer.isSelected())
                        chrItmSer = 'S';
                   if (chkEsItmTra.isSelected())
                                    chrItmSer = 'T';
                   if (chkEsItmOtr.isSelected())
                                    chrItmSer = 'O';



                    if (chkIvaCom.isSelected())
                        chrIvaCom = 'S';
                    else 
                        chrIvaCom = 'N';
                    if (chkIvaVta.isSelected())
                        chrIvaVta = 'S';
                    else 
                        chrIvaVta = 'N';                    
                    
                
                    strAux = txtCodAlt.getText().replaceAll("'", "''");
                    if(strAux.equals(""))
                      strAux="Null";
                    String strAux2="Null"; 
                    
                      strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
                         if(strAux2.equals(""))
                           strAux2="Null";
                    
                    if(txtCodAlt2.getText().trim().equals(""))
                       strAux2=strAux; 
                    
                    strSql="";
                    strSql+="INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_cla1, co_cla2, co_cla3, co_uni";
                    strSql+=", nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
                    strSql+=", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng, co_usrmod, fe_ing, fe_ultmod, nd_pesitmkgr, ";
                    strSql+="  st_permodnomitmven, st_permodnomitmcom,st_oblCamNomItm,tx_codaltdn ='"+ txtCodGP.getText() +"' ) ";
                    strSql+=" VALUES (" + rstEmp.getInt("co_emp") + ", " +  intCodItm  + "";
                    strSql+=", " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
                    strSql+=", '" +  strAux2 + "'";
                    strSql+=", " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                    if (txtCodLin.getText().equals(""))//cambiar por material
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodLin.getText().replaceAll("'", "''") + "";}

                    if (txtCodGrp.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodGrp.getText().replaceAll("'", "''") + "";}

                    if (txtCodMar.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodMar.getText().replaceAll("'", "''") + "";}

                    if (txtCodUniMed.getText().equals(""))
                        strSql+= ", " + null + "";
                    else{
                        strSql+=", " + txtCodUniMed.getText().replaceAll("'", "''") + "";}

                    if (txtItmUni.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtItmUni.getText().replaceAll("'", "''") + "";}
     

                    strSql+=", '" + chrItmSer + "'";

                    if (txtCosUni.getText().equals(""))
                        strSql+=", 0,0";
                    else{
                        strSql+=", " + txtCosUni.getText().replaceAll("'", "''") + ",0";}

                    if (txtCosUni1.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni1.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni2.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni2.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni3.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni3.getText().replaceAll("'", "''") + "";}

                    if (txtStkAct.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkAct.getText().replaceAll("'", "''") + "";}

                    if (txtStkMin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMin.getText().replaceAll("'", "''") + "";}

                    if (txtStkMax.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMax.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrIvaCom + "', '" + chrIvaVta + "'";
                    strSql+=", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", '" + chrEst + "', " + objZafParSis.getCodigoUsuario() + ",  " + objZafParSis.getCodigoUsuario() + ", '" + strFecUltMod + "' , '" + strFecUltMod + "' " +
                    " , "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', '"+(chkpermodnomcom.isSelected()?"S":"N")+"', ";   
                    strSql+=" "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+"";
                    strSql+=" )";
                    pstInv = conInv.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    strSql = "";
                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + rstEmp.getInt("co_emp") +","+ intCodItm + ")";
                    pstInv = conInv.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    //Creacion de Inventario Bodega                    
                    strSql = " Select co_bod from tbm_bod where co_emp ="+ rstEmp.getInt("co_emp") +"  and co_bod not in " +
                             " (select co_bod from tbm_invbod where co_emp ="+ rstEmp.getInt("co_emp") +" and co_itm ="+ intCodItm+") " +
                             " order by co_bod ";
                    java.sql.ResultSet rst = stmInv.executeQuery(strSql);                    
                    while(rst.next()){
                        if (!objInventario.creaItemBodega(conInv,rstEmp.getInt("co_emp"),intCodItm,rst.getInt("co_bod"))){
                            conInv.rollback();
                            conInv.close();
                            return false;
                        }
                    }  
                    rst.close();
                    rst=null;
                    

            }
           objHisTblBasDat.insertarHistoricoMasivo(conInv, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
           stmInv.close();
           stmInv=null;
           stmEmp.close();
           stmEmp=null;
        }catch(SQLException evt)  {
            try{
                conInv.rollback();
                conInv.close();
            }catch(SQLException Evts){
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }                        
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        } catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        return true;
    } 





//public boolean actualizarTabCla(java.sql.Connection conMod, String strCodItm ){
// boolean blnRes=true;
// Statement stm;
// String strEst="A";
// String strSqlCla="";
// try{
// if(conMod!=null){
// stm=conMod.createStatement();
//
// strSqlCla="DELETE FROM tbr_invcla  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm;
// stm.executeUpdate(strSqlCla);
// strSqlCla="";
//
// for(int x=0; x<tblCla.getRowCount(); x++){
//  if(tblCla.getValueAt(x, INT_TBL_CLACOD) != null){
//   if(!tblCla.getValueAt(x, INT_TBL_CLACOD).toString().equals("")){
//    strEst="A";
//    if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
//     if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
//           strEst="A";
//    }
//    if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
//        if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
//           strEst="I";
//    }
//
//   strSqlCla+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing ) VALUES" +
//   " ("+objZafParSis.getCodigoEmpresa()+","+ strCodItm +", "+tblCla.getValueAt(x, INT_TBL_CLACOD).toString()+" "+
//   " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) ; ";
//
// }}}
//
// if(!strSqlCla.equals(""))
//   stm.executeUpdate(strSqlCla);
//
//
// stm.close();
// stm=null;
//}}catch (SQLException evt){ blnRes=false;   MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
//  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
// return blnRes;
//}



////
////public boolean actualizarTabCla(java.sql.Connection conMod, String strCodItm  ){
//// boolean blnRes=true;
//// java.sql.ResultSet rstLoc;
//// Statement stmLoc;
//// String strEst="A";
//// String strSqlCla="";
//// String strSqlInv="";
//// String strCodCla="";
//// try{
//// if(conMod!=null){
////  stmLoc=conMod.createStatement();
////
////    strSqlInv="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
////    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
////    " AND co_itm="+strCodItm+")  ";
////    rstLoc = stmLoc.executeQuery(strSqlInv);
////    while(rstLoc.next()){
////
////       strSqlCla+="DELETE FROM tbr_invcla  WHERE co_emp="+rstLoc.getInt("co_emp")+" and co_itm="+rstLoc.getInt("co_itm")+" ; ";
////
////         for(int x=0; x<tblCla.getRowCount(); x++){
////          if(tblCla.getValueAt(x, INT_TBL_CLACOD) != null){
////           if(!tblCla.getValueAt(x, INT_TBL_CLACOD).toString().equals("")){
////            strEst="A";
////            if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
////             if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
////                   strEst="A";
////            }
////            if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
////                if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
////                   strEst="I";
////            }
////           strCodCla=tblCla.getValueAt(x, INT_TBL_CLACOD).toString();
////           strSqlCla+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing, co_grp ) VALUES" +
////           " ("+rstLoc.getInt("co_emp")+","+ rstLoc.getInt("co_itm") +", "+strCodCla+" "+
////           " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+tblCla.getValueAt( x, INT_TBL_CODGRP)+"  ) ; ";
////
////         }}}
////    }
////    rstLoc.close();
////    rstLoc=null;
////
////    System.out.println(" strSqlCla : "+strSqlCla );
////
//// if(!strSqlCla.equals(""))
////   stmLoc.executeUpdate(strSqlCla);
////
////
//// stmLoc.close();
//// stmLoc=null;
////}}catch (SQLException evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, evt);  MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
////  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
//// return blnRes;
////}

 




////
////public boolean actualizarTabPrv(java.sql.Connection conMod, String strCodItm ){
//// boolean blnRes=true;
//// Statement stm;
//// String strEst="A";
//// String strSqlPrv="";
//// try{
//// if(conMod!=null){
//// stm=conMod.createStatement();
////
////  strSqlPrv="DELETE FROM tbr_prvinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm;
////  stm.executeUpdate(strSqlPrv);
////  strSqlPrv="";
////
//// for(int x=0; x<tblPrv.getRowCount(); x++){
////  if(tblPrv.getValueAt(x, INT_TBL_PRVCODCLI) != null){
////   if(!tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString().equals("")){
////    strEst="A";
////    if(tblPrv.getValueAt( x, INT_TBL_PRVPRE)!=null){
////     if(tblPrv.getValueAt( x, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
////        strEst="P";
////    }
////    if(tblPrv.getValueAt( x, INT_TBL_PRVACT)!=null){
////     if(tblPrv.getValueAt( x, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
////           strEst="A";
////    }
////    if(tblPrv.getValueAt( x, INT_TBL_PRVINA)!=null){
////        if(tblPrv.getValueAt( x, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
////           strEst="I";
////    }
////    
////       strSqlPrv+=" INSERT INTO tbr_prvinv(co_emp, co_itm, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
////       " ("+objZafParSis.getCodigoEmpresa()+","+ strCodItm +", "+tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString()+" "+
////       " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";
////
//// }}}
////
//// if(!strSqlPrv.equals(""))
////   stm.executeUpdate(strSqlPrv);
//// 
////
//// stm.close();
//// stm=null;
////}}catch (SQLException evt){ blnRes=false;  MensajeInf("Error. Al Modificar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
////  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
//// return blnRes;
////}

public boolean modificarDatInv(){
 boolean blnRes=false;
 java.sql.Connection conn;
 try{
   conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn != null){
      conn.setAutoCommit(false);
   
   if(validadCampos()){

    if(validadCodAltMod(conn, txtCodItm.getText(), txtCodAlt.getText() ,"tx_codAlt" )) {
     if(validadCodAltMod(conn, txtCodItm.getText(), txtCodAlt2.getText() ,"tx_codAlt2" )) {
    //  if(validadStkMaxMin(conn, txtCodItm.getText() )) {  SE QUITA POR SEGUNDA ORDEN 
       if(modificarInv(conn)){
////        if(actualizarTabPrv(conn, txtCodItm.getText() )){
////          if(actualizarTabCla(conn, txtCodItm.getText() )){
         
              conn.commit();
              blnRes=true;
          
////          }else conn.rollback();
////        }else conn.rollback();
       }else conn.rollback();
    //  }else{ valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1); conn.rollback(); }
     }else{  valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);  conn.rollback(); }
    }else{ valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);  conn.rollback(); }

   }
     conn.close();
     conn=null;
      
 }}catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
 return blnRes;
}

public boolean modificarDatInvGP()
{
    boolean blnRes=false;
    java.sql.Connection conn;
    java.sql.Connection connGP;
    String strValidaGrupo="";  
    String strFecUltMod ="";
    String codigo ="";
    try
    {
        conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        connGP = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());   
        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
        if (datFecAux==null)
            return false;
        strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
        if((conn != null) && (connGP != null)) 
        {
            conn.setAutoCommit(false);
            connGP.setAutoCommit(false);
            if(validadCampos())
            {
                if(modificarInv(conn))
                {
                    if(modificarInvGP(connGP))
                    {
                        conn.commit();
                        connGP.commit();
                        blnRes=true;  
                        strValidaGrupo= objUtiGP.validacionItemAgrupacion(objUti.codificar(txtCodItm.getText(),0));            
                        if (cboEst.getSelectedIndex()==1)
                        {
                            if (!strValidaGrupo.equals (""))
                            {
                                String [] columnasAct = strValidaGrupo.toString().split("-");
                                if (columnasAct[1].equals("CABECERA"))
                                    objUtiGP.anulacionAgrupacionCabecera(Integer.parseInt(columnasAct[0]) ,strFecUltMod);
                                else
                                {
                                    if (columnasAct[1].equals("UNICO"))
                                        objUtiGP.anulacionAgrupacionSoloItem(objUti.codificar(txtCodItm.getText(),0) ,strFecUltMod);
                                    else
                                    {
                                        if (columnasAct[1].equals("ULTIMO"))
                                            objUtiGP.anulacionAgrupacionItemUltimo(txtCodItm.getText()  ,strFecUltMod);
                                    }
                                }
                            } 
                        } 
                        codigo="'"+ txtCodItm.getText()+"'";
//                        if(objUtiGP.ejecucionActualizacionAtributo(codigo))
//                        {
//                            strMsg="Se realizo la actualizacion del atributo.";
//                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
//                        }
//                        else
//                        {
//                            strMsg="NO se realizo la actualizacion del atributo.";
//                            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);                          
//                        }             
                    }
                    else
                    {
                        valTxt(txtCodAlt, txtCodAlt, "No se pudo modificar codigo GP", "<<Codigo>>", 3, "el", 0);  
                        conn.rollback();
                        connGP.commit(); 
                    }   
                }
                else
                { 
                    valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);  
                    conn.rollback();
                    connGP.commit(); 
                }
            }
            conn.close();
            conn=null;
            connGP.close();
            connGP=null;     
        }
    }
    catch (SQLException evt)
    { 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(jfrThis, evt); 
    }
    catch (Exception evt)
    { 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(jfrThis, evt);  
    }
    return blnRes;
}

private boolean validadCampos(){
  boolean blnRes=true;
  try
  {
      if(txtCodUniMed.getText().equals("")) 
      {
        strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscoja la Unidad de Medida del Item y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodUniMed.requestFocus();
        blnRes=false;
     }
      if(txtCodLineaCompra.getText().equals("")) 
      {
        strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscoja la Linea del Item y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodLineaCompra.requestFocus();
        blnRes=false;
     }  
      if(txtControl.getText().equals("")) 
      {
        strMsg = "El campo <<Control> es obligatorio.\nEscoja el Control y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtControl.requestFocus();
        blnRes=false;
     }      
      
      if(txtNomItm.getText().equals("")) 
      {
        strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtNomItm.requestFocus();
        blnRes=false;
     }
     if(txtMat.getText().equals("")) 
     {
        strMsg = "El campo <<Material> es obligatorio.\nNo se puede tener vacio el Material.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtMat.requestFocus();
        blnRes=false;
     }     
     if(txtMar.getText().equals("")) 
     {
        strMsg = "El campo <<Marca> es obligatorio.\nNo se puede tener vacio la Marca.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtMar.requestFocus();
        blnRes=false;
     }     
     if(txtLineaCompra.getText().equals("")) 
     {
        strMsg = "El campo <<Ref. General> es obligatorio.\nNo se puede tener vacio la Linea.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtLineaCompra.requestFocus();
        blnRes=false;
     }       
     if(txtCodClaseCompra.getText().equals("")) 
     {
        strMsg = "El campo <<Ref. Detallada> es obligatorio.\nNo se puede tener vacio la Clase.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodClaseCompra.requestFocus();
        blnRes=false;
     } 
     if(txtClaseCompra.getText().equals("")) 
     {
        strMsg = "El campo <<Compra> es obligatorio.\nNo se puede tener vacio la Compra";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtClaseCompra.requestFocus();
        blnRes=false;
     }      
     if(txtCodCategoriaCompra.getText().equals("")) 
     {
        strMsg = "El campo <<Categoria> es obligatorio.\nNo se puede tener vacio la Categoria";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodCategoriaCompra.requestFocus();
        blnRes=false;
     }     
     
     if(txtNomItm.getText().length() >101) 
     {
        strMsg = "El campo <<Nombre del Item> sobre pasa la cantida de 101 caracteres.\nNo se puede ingresar con esa descripcion.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtNomItm.requestFocus();
        blnRes=false;
     }         
     if ((txtCodAbcTUV.getText().equals("")|| (txtCodAbcTUV.getText().equals("0"))|| (txtAbcTUV.getText().equals("0"))) || txtAbcTUV.getText().equals(""))
     {
        strMsg = "El campo <<ABC-TUVAL> es obligatrio.\nNo se puede tener vacio.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodAbcTUV.requestFocus();
        blnRes=false;         
     }
    if ((txtCodAbcFRE.getText().equals("")|| (txtCodAbcFRE.getText().equals("0"))|| (txtAbcFRE.getText().equals("0"))) || txtAbcFRE.getText().equals(""))
     {
        strMsg = "El campo <<ABC-FRECUENCIA> es obligatrio.\nNo se puede tener vacio.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodAbcFRE.requestFocus();
        blnRes=false;         
     }
    if ((txtCodAbcPES.getText().equals("")|| (txtCodAbcPES.getText().equals("0"))|| (txtAbcPES.getText().equals("0"))) || txtAbcPES.getText().equals(""))    
     {
        strMsg = "El campo <<ABC-PESO> es obligatrio.\nNo se puede tener vacio.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodAbcPES.requestFocus();
        blnRes=false;         
     }
    if ((txtCodAbcCLA.getText().equals("")|| (txtCodAbcCLA.getText().equals("0"))|| (txtAbcCLA.getText().equals("0"))) || txtAbcCLA.getText().equals(""))        
     {
        strMsg = "El campo <<ABC-CLASE> es obligatrio.\nNo se puede tener vacio.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodAbcCLA.requestFocus();
        blnRes=false;         
     }
    if ((txtCodAbcVAL.getText().equals("")|| (txtCodAbcVAL.getText().equals("0"))|| (txtAbcVAL.getText().equals("0"))) || txtAbcVAL.getText().equals(""))            
     {
        strMsg = "El campo <<ABC-VALOR VENTA> es obligatrio.\nNo se puede tener vacio.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtCodAbcVAL.requestFocus();
        blnRes=false;         
     }     
  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean validadCodAltMod(java.sql.Connection conn, String strCodItm, String strCodAlt, String strCampCodAlt)
{
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT * FROM ( select "+strCampCodAlt+"," +
    " CASE WHEN ( " +
            " select "+strCampCodAlt+" from tbm_inv where co_emp="+objZafParSis.getCodigoEmpresa()+" "+
            " AND UPPER("+strCampCodAlt+") = '"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"' "+
            " )  IS NOT NULL  THEN  'S' ELSE 'N' END AS tip " +
            "," +
            " CASE WHEN  "+strCampCodAlt+" IN ( " +
            "   select "+strCampCodAlt+" from tbm_inv where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
            " AND UPPER("+strCampCodAlt+") = '"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"' "+
            " )   THEN  'S' ELSE 'N' END AS est " +
            " FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm+"  "+
     " ) AS x WHERE tip='S' and est='N' ";
    System.out.println("-->"+strSql);
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
      blnRes=false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
 }}catch(SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}

private boolean validadStkMaxMin(java.sql.Connection conn, String strCodItm )
{
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT nd_stkMin, nd_stkMax FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+strCodItm+" " +
    " AND nd_stkMin >= nd_stkMax ";
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
      blnRes=false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
 }}catch(SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}


public boolean modificarInvGP(java.sql.Connection conn )
{
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc;
    String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="",strUnidadGP="";
    String strFecUltMod,strSQLInt="",strSqlGP="";
    int intControl=0,IdResult=0;
    int idItemPeso=0;
    String codExtenderCatCom="",codExtenderClaCom,codExtenderLinCom="";
     try
     {
        if(conn != null)
        {
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
            stmLoc = conn.createStatement();
            String inactivo ="";
            if (cboEst.getSelectedIndex()==0)  
                inactivo ="A";
            else
                inactivo ="I";

                codExtenderCatCom =obtenerCodExtender(conn,txtCategoriaCompra.getText(),8);                
                codExtenderClaCom =obtenerCodExtender(conn,txtClaseCompra.getText(),9); 
                codExtenderLinCom =obtenerCodExtender(conn,txtLineaCompra.getText(),10); 
                strUnidadGP =getObtenerUnidadMedidaGP(txtUniMed.getText(),conn);
                strSqlGP="UPDATE Dynamics2021..tbm_inv SET  tx_nomItm="+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", " +
                         " tx_atrmat="+((strAux=txtMat.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +
                         " tx_atrmar="+((strAux=txtMar.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +
                         " tx_atrlin="+((strAux=txtLinea.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +
                         " tx_atrcom="+((strAux=txtCompr.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                
                         " tx_atrcon="+((strAux=txtControl.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                    
                         " tx_atrcat="+((strAux=txtCategoria.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                                    
                         " tx_atrcatcom="+((strAux=codExtenderCatCom.replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                                                            
                         " tx_atrclacom="+((strAux=codExtenderClaCom.replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                                                                                    
                         " tx_atrlincom="+((strAux=codExtenderLinCom.replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                                                                                                            
                         " tx_atrcla="+((strAux=txtCla.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +                                                            
                         " tx_obs1='"+((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"S/N":"'"+strAux+"'")+"', "+
                         " tx_obs2='"+((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"S/N":"'"+strAux+"'")+"', "+
                         " tx_usrmod='"+objZafParSis.getNombreUsuario()+"', fe_ultmod='"+strFecUltMod +"', "+
                         " st_reg='"+ inactivo+"', "+
                         " nd_pes="+Double.parseDouble(spnPeso.getModel().getValue().toString())  +" "+                
                         " WHERE  tx_codalt2='"+ txtCodAlt2.getText()+ "';";
                String strSQL1="";

                for (int j=0;j<=10;j++)
                {
                    IdResult =consultaEXT01101_EXT01100(conn,j);
                    if(IdResult!=-1)
                    {
                        if (j==0)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtLinea.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";
                        if(j==1)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtCla.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";
                        if (j==2)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtMar.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";
                        if(j==3)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtMat.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                
                        if(j==4)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtCodTama.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                
                        if(j==5)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtCompr.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                  
                        if(j==6)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtCategoria.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                  
                        if(j==7)
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ txtControl.getText() +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                
                        if(j==8)
                        {
                            //codExtender =obtenerCodExtender(conn,txtCategoriaCompra.getText(),8);
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderCatCom +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                        
                        }
                        if(j==9)
                        {
                            //codExtender =obtenerCodExtender(conn,txtCategoriaCompra.getText(),8);
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderClaCom +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                        
                        }
                        if(j==10)
                        {
                            //codExtender =obtenerCodExtender(conn,txtCategoriaCompra.getText(),8);
                            strSqlGP+= " UPDATE   "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderLinCom +"' WHERE DEX_ROW_ID ="+ IdResult +" ;\n";                        
                        }                        
                            
                        blnRes=true;
                    }
                    else
                    {
                        if(IdResult==-1)
                        {
                            if (j==2)//para la marca
                            {
                                 strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                 strSQL1+=" VALUES ( \n";
                                 strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 119 ,'"+ txtMar.getText() +"'); \n"; 
                            }
                            else
                            {
                                if (j==3)//para el material
                                {
                                    strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                    strSQL1+=" VALUES ( \n";
                                    strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 120 ,'"+ txtMat.getText() +"'); \n"; 
                                }
                                else
                                {
                                    if (j==0)//PARA LA REF GENEERAL-se cambio para lo que es la linea
                                    {
                                        strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                        strSQL1+=" VALUES ( \n";
                                        strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 196 ,'"+ txtLinea.getText() +"'); \n";                                          
                                    }
                                    else
                                    {
                                        if (j==1)//PARA LA REF GENEERAL//PARA LA REF DETALLADA -se cambio para lo que es la clase
                                        {
                                            strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                            strSQL1+=" VALUES ( \n";
                                            strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 197 ,'"+ txtCla.getText() +"'); \n";                                          
                                        }
                                        else
                                        {
                                            if(j==4)//para tamaño
                                            {
                                                strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                strSQL1+=" VALUES ( \n";
                                                strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 181 ,'"+ txtCodTama.getText() +"'); \n";                                             
                                            }
                                            else
                                            {
                                                if(j==5)//para compra
                                                { 
                                                    strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                    strSQL1+=" VALUES ( \n";
                                                    strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 199 ,'"+ txtCompr.getText() +"'); \n";                                             
                                                } 
                                                else
                                                {
                                                    if(j==6)//para categoria
                                                    { 
                                                        strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                        strSQL1+=" VALUES ( \n";
                                                        strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 204 ,'"+ txtCategoria.getText() +"'); \n";                                             
                                                    }                                                
                                                    else
                                                    {
                                                        if(j==7)//para control
                                                        { 
                                                            strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                            strSQL1+=" VALUES ( \n";
                                                            strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 203 ,'"+ txtControl.getText() +"'); \n";                                             
                                                        } 
                                                        else
                                                        {
                                                            if(j==8)//para categoria comrpas
                                                            { 

                                                                strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                strSQL1+=" VALUES ( \n";
                                                                strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 206 ,'"+ codExtenderCatCom +"'); \n";                                             
                                                            }  
                                                            else
                                                            {
                                                                if(j==9)//para clase comrpas
                                                                { 

                                                                    strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                    strSQL1+=" VALUES ( \n";
                                                                    strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 208 ,'"+ codExtenderClaCom +"'); \n";                                             
                                                                }
                                                                else
                                                                {
                                                                    if(j==10)//para linea comrpas
                                                                    { 

                                                                        strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                        strSQL1+=" VALUES ( \n";
                                                                        strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 207 ,'"+ codExtenderLinCom +"'); \n";                                             
                                                                    }
                                                                    else// PARA LOS PREFIJOSS
                                                                    {
                                                                        strSQL1+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
                                                                        strSQL1+=" VALUES ( \n";
                                                                        strSQL1+=" (SELECT TOP 1 Extender_Record_ID FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 121 ,'"+ prefijo +"'); \n";                                                                                                                                             
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
                            blnRes=true;
                        }
                        else
                            blnRes=false;
                    } 
                }
                String strSQL2="";                
                idItemPeso =getObtenerIdPesoGP(txtCodGP.getText(),conn);
                if(insertarModificarPesoGP(conn,txtCodGP.getText(), idItemPeso, Double.parseDouble(spnPeso.getModel().getValue().toString())  )==true)       
                    blnRes=true;
                else
                    blnRes=false;        
                if (cboEst.getSelectedIndex()==0)
                    strSqlGP+="UPDATE "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 SET INACTIVE =0 ,  ITEMDESC ="+ objUti.codificar(txtNomItm.getText(),0) +" ,MODIFDT = cast ( '"+strFecUltMod+"' as date) WHERE ITMGEDSC ='"+ txtCodAlt2.getText()  +"'; \n";
                else
                    strSqlGP+="UPDATE "+ objZafParSis.getNombreBaseDatosGP()  +"..IV00101 SET INACTIVE =1 ,  ITEMDESC ="+ objUti.codificar(txtNomItm.getText(),0) +" ,MODIFDT = cast ( '"+strFecUltMod+"' as date) WHERE ITMGEDSC ='"+ txtCodAlt2.getText()  +"'; \n";        
                
                //////////
                String strSqlGPABC="";
                strSqlGPABC =getEjecucionABC(txtCodGP.getText(),conn,strFecUltMod);
                if (!strSqlGPABC.equals(""))
                    strSqlGP =strSqlGP +strSqlGPABC;
                else
                    blnRes=false;
                /////////
                strSqlGP = strSqlGP + strSQL1;
                if (blnRes==true)
                {
                    stmLoc.executeUpdate(strSqlGP);
                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrMod='" + objZafParSis.getNombreUsuario()+"'"))
                    {
                        if (blnUsaKon)
                        {
                            if(insertarCreaModiItmKON(2,conn))
                                blnRes=true;                                                                 
                            else
                                blnRes=false;
                        }                        
                        else
                            blnRes=true;                        
                    }
                    else
                        blnRes=false;
                }
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch(SQLException evt) 
        { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        catch(Exception evt) 
        { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        return blnRes;
    }

private boolean insertarHistoricoMasivoInt(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL)
{
        java.sql.Statement stmLocGP, stmLoc01;
        java.sql.ResultSet rstLocGP;  
        java.sql.ResultSetMetaData rmdGP;
        java.sql.DatabaseMetaData dmdGP;
        String strSQL ="";
        int intNumCam, j;
        String strLisCam="", strLisCamClaPri="", strRelTbl=""; //Listado de campos sin alias, Listado de campos clave primaria, Relación entre las tablas.
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stmLocGP=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " AS a1 " + strConSQL;
                rstLocGP=stmLocGP.executeQuery(strSQL);
                rmdGP=rstLocGP.getMetaData();
                intNumCam=rmdGP.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmdGP.getColumnName(j);
                }
                rstLocGP.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmdGP=con.getMetaData();
                rstLocGP=dmdGP.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rstLocGP.next())
                {
                    if (!strLisCamClaPri.equals(""))
                    {
                        strLisCamClaPri+=", ";
                        strRelTbl+=" AND ";
                    }
                    strLisCamClaPri+="a1." + rstLocGP.getString("COLUMN_NAME");
                    strRelTbl+="a1." + rstLocGP.getString("COLUMN_NAME") + "=a2." + rstLocGP.getString("COLUMN_NAME");
                }
                rstLocGP.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.

                    strSQL="";
                    strSQL+=" INSERT INTO " + strNomTblHis + "(co_his," + strLisCam + ") \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo, a1.* \n";
                    strSQL+=" FROM " + strNomTbl + " AS a1 \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from " + strNomTblHis + " as a2   \n";
                    strSQL+=" group by tx_itm \n";
                    strSQL+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQL+=" " + strConSQL;

                    
                    
                    
                stmLocGP.executeUpdate(strSQL);
                stmLocGP.close();
                stmLocGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }



private boolean insertarModificarPesoGP(java.sql.Connection conExt, String item,int idItem,double  peso)//chiqui
    {
        
        java.sql.Statement stmGP;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSQLOP="";
        int intCodRes=-1;
        
        boolean blnRes=true;
        try
        {
            //conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
             
            if (conExt!=null)
            {
                stmGP = conExt.createStatement();
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSQLOP="";
                strSQLOP+="{call SP_INSER_MODI_PESO_1(?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSQLOP);
                cal = conExt.prepareCall(strSQLOP);
                cal.setString("ITEM",  item);//Envio de parametro
                cal.setDouble("PESO",  peso);//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmGP.close();
                stmGP=null;
                
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

private int getObtenerIdPesoGP(String item,java.sql.Connection conExt)//chiqui
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQLOP="";
        int blnRes=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQLOP="";
                strSQLOP+="SELECT Extender_Record_ID ";
                strSQLOP+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 ";
                strSQLOP+=" WHERE Extender_Key_Values_1 ='"+ item +"'";
                strSQLOP+=" AND Extender_Window_ID = 'MSW_ADI_IV'";
                rstLoc=stmLoc.executeQuery(strSQLOP);
                while (rstLoc.next())
                {
                    blnRes=rstLoc.getInt("Extender_Record_ID");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

private String getEjecucionABC(String item,java.sql.Connection conExt,String fecha)//chiqui
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;  
        String strSQLOP="";
        String strValor="",strPeso="",strFre="",strTuv="",strCla="";
        String strSQLInt="";
        String cabecera="",values="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                strSQLInt="";
                cabecera="";
                values="";
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQLOP="";
                strSQLOP+="SELECT isnull([Valor venta],'') as valor ,isnull(Peso,'') as peso,isnull(Frecuencia,'') as fre,isnull(Tuval,'') as tuv,isnull(Clase,'') as cla ";
                strSQLOP+="FROM dynamics2021..vw_itemsABC_Columnas ";
                strSQLOP+="WHERE  tx_itm='"+ item +"'";
                rstLoc=stmLoc.executeQuery(strSQLOP);
                while (rstLoc.next())
                {
                    strFre=rstLoc.getString("fre");
                    strPeso=rstLoc.getString("peso");
                    strValor=rstLoc.getString("valor");
                    strTuv=rstLoc.getString("tuv");
                    strCla=rstLoc.getString("cla");
                    if (!strFre.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ txtCodAbcFRE.getText() +" where tx_itm = '"+txtCodGP.getText() +"' and co_catpar =1 ;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ txtCodGP.getText() +"',1,"+ txtCodAbcFRE.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }
                    if (!strPeso.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ txtCodAbcPES.getText() +" where tx_itm = '"+txtCodGP.getText() +"' and co_catpar =2 ;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ txtCodGP.getText() +"',2,"+ txtCodAbcPES.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }

                    if (!strValor.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ txtCodAbcVAL.getText() +" where tx_itm = '"+txtCodGP.getText() +"' and co_catpar =3 ;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ txtCodGP.getText() +"',3,"+ txtCodAbcVAL.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                    
                    
                    if (!strTuv.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ txtCodAbcTUV.getText() +" where tx_itm = '"+txtCodGP.getText() +"' and co_catpar =4 ;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ txtCodGP.getText() +"',4,"+ txtCodAbcTUV.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                    
                    if (!strCla.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ txtCodAbcCLA.getText() +" where tx_itm = '"+txtCodGP.getText() +"' and co_catpar =5 ;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ txtCodGP.getText() +"',5,"+ txtCodAbcCLA.getText() +",'A',"+objZafParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                                        
                   // blnRes=true;
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            strSQLInt="";
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            strSQLInt="";
            objUti.mostrarMsgErr_F1(null, e);
        }
        return strSQLInt;
    }
private boolean InsertarHistorico (java.sql.Connection conEXT,String fecha,String usuario)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="",strSQL="";
        try
        {
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                    strSQL="";
                    
                    strSQL+=" INSERT INTO tbh_inv\n" +
"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk,tx_obs1,tx_obs2) \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm,a1.nd_marUti, a1.nd_canMaxVen, a1.nd_facCosUni, a1.tx_usrmod, a1.fe_ultmod, a1.tx_codalt, a1.tx_nomitm, a1.tx_codalt2, a1.nd_preVta1,\n";
                    strSQL+="   a1.co_grpimp, a1.tx_nomitming, a1.st_revtec, a1.st_itmstk,a1.tx_obs1,a1.tx_obs2  \n";
                    strSQL+=" FROM tbm_inv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,tx_itm from tbh_inv as a2   \n";
                    strSQL+=" group by tx_itm \n";
                    strSQL+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQL+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                
                    System.out.println("UPDATE " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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

public int consultaEXT01101_EXT01100(java.sql.Connection conn,int tipo )
{
////    java.sql.Connection conEXT;
    java.sql.ResultSet rstLoc;
    java.sql.Statement stmLoc;
    int ndRsul=-1;
    boolean blnsExiste=false;
    String cabecera="",values="",strSQL="";
    int indicador =0;
    try
    {
    
    if (tipo ==0)
        indicador =196;//ref general
    if (tipo ==1)
        indicador =197;//ref detallada
    if (tipo ==2)
        indicador =119;//marca
    if (tipo ==3)
        indicador =120;//material
    if (tipo ==4)
        indicador =181;//tamano   
    if (tipo ==5)
        indicador =199;//compra       
    if (tipo ==6)
        indicador =204;//categoria     
    if (tipo ==7)
        indicador =203;//control
    if (tipo ==8)
        indicador =206;//categoria compra 
    if (tipo ==9)
        indicador =208;//clase compra 
    if (tipo ==10)
        indicador =207;//linea compra     
////      conn = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
      if(conn!=null)
        {
            stmLoc=conn.createStatement();
            strSQL="";
            strSQL+=" SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_118_Referencia_Detallada,e100.Extender_Key_Values_1,e101.DEX_ROW_ID as id  \n";            
            strSQL+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e101 \n";
            strSQL+="       INNER JOIN "+ objZafParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
            strSQL+="       WHERE e101.Field_ID ="+ indicador +" and e100.Extender_Key_Values_1  ='"+ txtCodGP.getText() +"'";
            
            rstLoc=stmLoc.executeQuery(strSQL);
            if (rstLoc.next())
            {
                blnsExiste=true;
                ndRsul=rstLoc.getInt("id");
            }
            else
                ndRsul=-1;
////            stmLoc.executeUpdate(strSQL);

            stmLoc.close();
            stmLoc=null;  
            rstLoc.close();
            rstLoc=null;            

////                }
        }    
    }    
    catch(java.sql.SQLException e){
        objUti.mostrarMsgErr_F1(this, e);
        ndRsul=-2;

    }
    catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
        ndRsul=-2;
    }
    
    

    
    
                
    
    return ndRsul;
}

public boolean modificarInv(java.sql.Connection conn ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="";
 String strFecUltMod;
 int intControl=0;
 String estado ="";
 try{
  if(conn != null){
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    if (datFecAux==null)
        return false;
    strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    stmLoc = conn.createStatement();
////    stmLoc01 = conn.createStatement();

////    if(chkEsItmSer.isSelected()) chrItmSer="S";
////    if(chkEsItmTra.isSelected()) chrItmSer="T";
////    if(chkEsItmOtr.isSelected()) chrItmSer="O";
////
////
////    if(chkIvaCom.isSelected())  chrIvaCom="S";
////    else  chrIvaCom="N";
////
////    if(chkIvaVta.isSelected()) chrIvaVta="S";
////    else  chrIvaVta="N";
////
////    if(cboEst.getSelectedIndex()==0) chrEst="A";
////    else  chrEst="I";
////
////    if(chkCamPreVta.isSelected()) strCamPreVta="S";

////    strSql="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
////    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
////    " AND co_itm="+txtCodItm.getText()+")";
////    rstLoc = stmLoc.executeQuery(strSql);
////    while(rstLoc.next()){
       //if(!CreaItem_His(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_itm") )){
       //   intControl=1;
       //   break;
       //}
       
        if (cboEst.getSelectedIndex()==0)
            estado="A";
        else
            estado="I";
           
        strSql="UPDATE tbm_inv SET  tx_nomItm="+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", " +
////        " tx_codAlt="+((strAux=txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +
////        " tx_codAlt2="+((strAux=txtCodAlt2.getText().replaceAll("'", "''")).equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAux.toUpperCase()+"'")+", "+
////        " tx_nomItm="+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " co_uni="+((strAux=txtCodUniMed.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " nd_itmUni="+((strAux=txtItmUni.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " st_ser='"+chrItmSer+"', "+
////        " nd_cosUni="+((strAux=txtCosUni.getText().replaceAll("'", "''")).equals("")?"0":strAux )+",nd_cosult=0, "+  
        /* JM: (10/Ene/2020) Se hablo con el Ing. Eddye, quedamos en que el nd_cosUni no debe modificarse para todas las empresas. */
////        " nd_preVta1="+((strAux=txtCosUni1.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
////        " nd_preVta2="+((strAux=txtCosUni2.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
////        " nd_preVta3="+((strAux=txtCosUni3.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
//////        " nd_stkAct="+((strAux=txtStkAct.getText().replaceAll("'", "''")).equals("")?"Null":strAux)+", "+
////        " nd_stkMin="+((strAux=txtStkMin.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " nd_stkMax="+((strAux=txtStkMax.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " st_ivaCom='"+chrIvaCom+"', st_ivaVen='"+chrIvaVta+"', "+
        " tx_obs1="+((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " tx_obs2="+((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
////        " st_reg='"+chrEst+"' ,"+                
        " co_usrMod="+objZafParSis.getCodigoUsuario()+", fe_ultmod='"+strFecUltMod +"', "+
        " st_reg='"+ estado +"', "+
        //" nd_pesitmkgr="+spnPeso.getModel().getValue().toString()+", st_permodnomitmven='"+(chkpermodnomven.isSelected()?"S":"N")+"', st_permodnomitmcom= '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "+
        " nd_pesitmkgr="+spnPeso.getModel().getValue().toString()+""+
////        " st_oblCamNomItm="+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+
                
                
        " WHERE  tx_codalt2='"+ txtCodAlt2.getText()+ "'";
        
       
        stmLoc.executeUpdate(strSql);
////    }
    //objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
////    rstLoc.close();
////    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
////    stmLoc.close();
////    stmLoc=null;

    if(intControl==0) blnRes=true;

  }}catch(SQLException evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
    catch(Exception evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}


//
///**FunciÃ³n que me permÃ­te modificar los datos del Articulo seleccionado*/
//public boolean modificarInv(java.sql.Connection con ){
// boolean blnRes=false;
// java.sql.Statement stm;
// java.sql.ResultSet rst;
// txtCodAltAux.setText("");
// try{
//  if(con != null){
//    stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//    strSql="";
//    strSql = strSql + " SELECT tx_codAlt";
//    strSql = strSql + " FROM tbm_inv";
//    strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//    strSql = strSql + " and co_itm = " + txtCodItm.getText().replaceAll("'", "''") + "";
//
//    rst = stm.executeQuery(strSql);
//    if (rst.next())
//        txtCodAltAux.setText(((rst.getString("tx_codAlt")==null)?"":rst.getString("tx_codAlt")));
//    rst.close();
//    rst=null;
//
//    if (!txtCodAltAux.getText().equals(txtCodAlt.getText().replaceAll("'", "''"))){
//        strSql = "";
//        strSql = strSql + " SELECT tx_codAlt";
//        strSql = strSql + " FROM tbm_inv";
//        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//        strSql = strSql + " and UPPER(tx_codAlt) = '" + txtCodAlt.getText().replaceAll("'", "''").toUpperCase() + "'";
//        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
//        {
//            valTxt(txtCodAlt, txtCodAlt, "CÃ³digo alterno", "<<CÃ³digo alterno>> ya existe", 3, "el", 0);
//            return false;
//        }
//
//        strSql = "";
//        strSql = strSql + " SELECT tx_codAlt2";
//        strSql = strSql + " FROM tbm_inv";
//        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//        strSql = strSql + " and UPPER(tx_codAlt2) = '" + txtCodAlt2.getText().replaceAll("'", "''").toUpperCase() + "'";
//        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
//        {
//            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);
//            return false;
//        }
//    }
//
//
//
//
//
//
//                        strSql="";
//                        strSql = strSql + " SELECT nd_stkMin, nd_stkMax";
//                        strSql = strSql + " FROM tbm_inv";
//                        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//                        strSql = strSql + " and co_itm = " + txtCodItm.getText().replaceAll("'", "''") + "";
//
//                        rst = stm.executeQuery(strSql);
//                        if (rst.next()){
//                            txtStkMinAux.setText(((rst.getString("nd_stkMin")==null)?"":rst.getString("nd_stkMin")));
//                            txtStkMaxAux.setText(((rst.getString("nd_stkMax")==null)?"":rst.getString("nd_stkMax")));
//                        }
//                        if ((!txtStkMinAux.getText().equals(txtStkMin.getText())) || (!txtStkMaxAux.getText().equals(txtStkMax.getText()))){
//                            //Validando los Stocks.
//                            double dblStkMin = Double.parseDouble(txtStkMin.getText().toString());
//                            double dblStkMax = Double.parseDouble(txtStkMax.getText().toString());
//                            if (dblStkMin >= dblStkMax) {
//                                valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1);
//                                return false;
//                            }
//                        }
//                        rst.close();
//
//                        /**Almacena el estado que haya seleccionado el usuario en una variable*/
//                        char chrEst,chrItmSer,chrIvaCom,chrIvaVta;
//
//                        /**Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo*/
//                        if (cboEst.getSelectedIndex()==0)
//                            chrEst= 'A';
//                        else
//                            chrEst= 'I';
//
//                       /**Bloque que almacena en una variable de tipo char si es item de servicio
//                         *el articulo segun si han marcado la casilla o no*/
//                         chrItmSer = 'N';
//
//                        if (chkEsItmSer.isSelected())
//                            chrItmSer = 'S';
//
//                        if (chkEsItmTra.isSelected())
//                            chrItmSer = 'T';
//
//                        /**Bloque que almacena en una variable de tipo char si el articulo
//                         tiene Iva en Compras o no*/
//                        if (chkIvaCom.isSelected())
//                            chrIvaCom = 'S';
//                        else
//                            chrIvaCom = 'N';
//
//                        /**Bloque que almacena en una variable de tipo char si el articulo
//                         tiene Iva en Ventas o no*/
//                        if (chkIvaVta.isSelected())
//                            chrIvaVta = 'S';
//                        else
//                            chrIvaVta = 'N';
//
//
//
//                         String  sql ="SELECT co_emp , co_itm FROM tbm_equInv  WHERE " +
//                         " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
//                         "  AND co_itm="+txtCodItm.getText()+")";
//                        // System.out.println(">>>>>>> "+ sql );
//                          java.sql.Statement stmMae=con.createStatement();
//                          java.sql.ResultSet rstMae;
//                          rstMae = stm.executeQuery(sql);
//                          while(rstMae.next()){
//
//                               // System.out.println("OKKK 1.. ");
//
//                           if(!CreaItem_His(con, rstMae.getInt(1), rstMae.getInt(2) )){
//                          // System.out.println(" ERROR .......... ");
//                            con.rollback();
//                            con.close();
//                            con=null;
//                            return false;
//                           }
//
//
//                         strAux = txtCodAlt.getText().replaceAll("'", "''");
//                         if(strAux.equals(""))
//                           strAux="Null";
//
//                         String strAux2="Null";
//
//                          strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
//                         if(strAux2.equals(""))
//                           strAux2="Null";
//
//                        if(txtCodAlt2.getText().trim().equals(""))
//                             strAux2=strAux;
//
//                        /**Haciendo la modificaciÃ³n en la tabla segun el codigo del articulo
//                        al que estemos haciendo referencia*/
//                        strSql = "";
//                        strSql = strSql + "UPDATE tbm_inv SET";
//                        strSql = strSql + " tx_codAlt = " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
//                        strSql = strSql + ", tx_codAlt2 = '" + strAux2.toUpperCase() + "' " +
//                                "" +
//                        " ,st_permodnomitmven='"+(chkpermodnomven.isSelected()?"S":"N")+"' "+
//                        " ,st_permodnomitmcom='"+(chkpermodnomcom.isSelected()?"S":"N")+"' "+
//                        "";
//
//                        strSql = strSql + ", tx_nomItm = " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//
////                        if (txtCodLin.getText().equals(""))
////                            strSql = strSql + ", co_cla1 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla1 = " + txtCodLin.getText().replaceAll("'", "''") + "";}
////
////                        if (txtCodGrp.getText().equals(""))
////                            strSql = strSql + ", co_cla2 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla2 = " + txtCodGrp.getText().replaceAll("'", "''") + "";}
////
////                        if (txtCodMar.getText().equals(""))
////                            strSql = strSql + ", co_cla3 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla3 = " + txtCodMar.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCodUniMed.getText().equals(""))
//                            strSql = strSql + ", co_uni = " + null + "";
//                        else{
//                            strSql = strSql + ", co_uni = " + txtCodUniMed.getText().replaceAll("'", "''") + "";}
//
//                        if (txtItmUni.getText().equals(""))
//                            strSql = strSql + ", nd_itmUni = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_itmUni = " + txtItmUni.getText().replaceAll("'", "''") + "";}
//
//                        strSql = strSql + ", st_ser = '" + chrItmSer + "'";
//
//                        if (txtCosUni.getText().equals(""))
//                            strSql = strSql + ", nd_cosUni = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_cosUni = " + txtCosUni.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni1.getText().equals(""))
//                            strSql = strSql + ", nd_preVta1 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta1 = " + txtCosUni1.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni2.getText().equals(""))
//                            strSql = strSql + ", nd_preVta2 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta2 = " + txtCosUni2.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni3.getText().equals(""))
//                            strSql = strSql + ", nd_preVta3 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta3 = " + txtCosUni3.getText().replaceAll("'", "''") + "";}
//
//
//                        if (txtStkMin.getText().equals(""))
//                            strSql = strSql + ", nd_stkMin = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_stkMin = " + txtStkMin.getText().replaceAll("'", "''") + "";}
//
//                        if (txtStkMax.getText().equals(""))
//                            strSql = strSql + ", nd_stkMax = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_stkMax = " + txtStkMax.getText().replaceAll("'", "''") + "";}
//
//                        strSql = strSql + ", st_ivaCom = '" + chrIvaCom + "'";
//                        strSql = strSql + ", st_ivaVen = '" + chrIvaVta + "'";
//                        strSql = strSql + ", tx_obs1 = " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//                        strSql = strSql + ", tx_obs2 = " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//                        strSql = strSql + ", st_reg = '" + chrEst + "'";
//                        strSql = strSql + ", co_usrmod = " + objZafParSis.getCodigoUsuario() + "";
//
//                        strSql = strSql + ", fe_ultmod = '"+objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos())+"'";
//
//                        strSql = strSql + ", nd_pesitmkgr = "+spnPeso.getModel().getValue().toString();
//
//                        strSql = strSql + " WHERE co_emp = " + rstMae.getInt(1) + "";
//                        strSql = strSql + " and co_itm = " + rstMae.getInt(2) + "";
//                        //System.out.println(strSql);
//                        stmMae.executeUpdate(strSql);
//                           //  System.out.println("OK.......... ");
//
//                       }    //*****}
//                       //  System.out.println(" ERROR 222.......... ");
//                        con.commit();
//                        stm.close();
//                        con.close();
//                    }
//                }
//		catch(SQLException evt)
//		{
//                    objUti.mostrarMsgErr_F1(this, evt);
//                    return false;
//		}
//		catch(Exception evt)
//		{
//                    objUti.mostrarMsgErr_F1(this, evt);
//                    return false;
//		}
//            blnCmb = false;
//            return true;
//	}
//



//*********************************************************************************************************  
private boolean CreaItem_His(java.sql.Connection conn,int coemp,int codItm){
  boolean blnres=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rst;
  String sql="";
  try{ 
   int intCorRegPre=0;
   stmLoc=conn.createStatement();

    sql="select count(co_reg)+1 as co_reg from tbh_inv where co_emp="+coemp+" and co_itm="+codItm;
    rst = stmLoc.executeQuery(sql);
    if(rst.next()) intCorRegPre=rst.getInt(1);
    else  intCorRegPre=1;
        sql="INSERT INTO tbh_inv(co_emp,co_itm,co_reg,tx_codAlt,tx_codAlt2,tx_nomItm,   co_uni,nd_itmUni," +
        "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod," +
        "co_usrIng,co_usrMod,nd_stkact, nd_pesitmkgr, st_permodnomitmcom, st_permodnomitmven ,tx_codaltdyn,)" +
        " SELECT co_emp,co_itm, "+intCorRegPre+",tx_codAlt,tx_codAlt2,tx_nomItm, co_uni,nd_itmUni," +
        "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing," +
        "fe_ultMod,co_usrIng,co_usrMod,nd_stkact, nd_pesitmkgr, st_permodnomitmcom, st_permodnomitmven,tx_codaltdyn  " +
        " FROM tbm_inv WHERE co_emp="+coemp+" and co_itm="+codItm;
        stmLoc.executeUpdate(sql);

        stmLoc.close();
        stmLoc=null;
        rst.close();
        rst=null;
     
   }catch (java.sql.SQLException e) { blnres=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch (Exception e)  {  blnres=false;  objUti.mostrarMsgErr_F1(this, e); }
  return blnres;
}
   
    //*********************************************************************************************************  
   
    
    
    /**FunciÃ³n que permitirÃ¡ eliminar un registro*/
    public boolean eliminarReg() {
        java.sql.PreparedStatement pstInv;
        String strFecUltMod;
        boolean blnMaestro=false;
        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            try{
              if (con!=null){
                  datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                
                java.sql.Statement stm = con.createStatement();
                strSql=" Select count(co_reg) as num from tbm_detmovinv as det ," +
                       " (Select co_itmmae,co_emp, co_itm  from tbm_equinv where co_itmmae = (select co_itmmae from tbm_equinv " +
                       " where co_emp = "+ objZafParSis.getCodigoEmpresa() + " and  co_itm="+ txtCodItm.getText()+") order by co_emp) as a2 "+
                       " where det.co_emp=a2.co_emp and det.co_itm = a2.co_itm";
                java.sql.ResultSet rst = stm.executeQuery(strSql);
                if (rst.next()){
                    if (rst.getInt("num")>0){
                        MensajeInf("No se puede eliminar existen movimientos asociados con el item");
                        rst.close();
                        stm.close();
                        con.close();
                        return false;
                    }                        
                }else{
                    MensajeInf("Error!! \n"+strSql);
                    rst.close();
                    stm.close();
                    con.close();
                    return false;
                }
                rst.close();
                strSql = "";
                strSql = " Select co_itmmae,co_emp, co_itm  from tbm_equinv where co_itmmae = (select co_itmmae from tbm_equinv where co_emp = " + objZafParSis.getCodigoEmpresa() +" and  co_itm="+ txtCodItm.getText().replaceAll("'", "''") + ") order by co_emp";
                rst = stm.executeQuery(strSql);
                while (rst.next())
                {                    
                    strSql = "";
                    strSql = strSql + " DELETE FROM tbm_equinv";
                    strSql = strSql + " WHERE co_itmmae =" + rst.getInt("co_itmmae");
                    strSql = strSql + " and co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                   // pstInv = con.prepareStatement(strSql);
                  //  pstInv.executeUpdate();                    

                    
//                     if(!CreaItem_His(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()))){
//                            con.rollback();
//                            con.close();
//                            con=null;
//                            return false;
//                        }
//                      
                    strSql = "";
                    strSql = strSql + " DELETE FROM tbm_invbod";
                    strSql = strSql + " WHERE co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                   // pstInv = con.prepareStatement(strSql);
                   // pstInv.executeUpdate();
                    strSql = "";
                    strSql = strSql + " update tbm_inv set st_reg='E' , fe_ultmod='"+strFecUltMod+"', co_usrmod="+objZafParSis.getCodigoUsuario();
                    strSql = strSql + " WHERE co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                }
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
                con.commit();
                con.setAutoCommit(true);
                rst.close();
                stm.close();
                con.close();

                limpiarDatInv();
                deshabilitarDatInv();
              }
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
    blnCmb = false;
    return true;
   }
        
    
    private String filtarSql() {
        String sqlFlt = "";
        //Agregando filtro por Codigo del Item
        if(!txtCodItm.getText().equals(""))
            sqlFlt = sqlFlt + " and a1.ITEMNMBR = '" + txtCodItm.getText().replaceAll("'", "''") + "'";

        //Agregando filtro por Codigo Alterno
        if(!txtCodAlt.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(a1.ITMSHNAM ) LIKE '" + txtCodAlt.getText().replaceAll("'", "''").toLowerCase() + "%' ";


          if(!txtCodAlt2.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(a1.ITMGEDSC) LIKE '" + txtCodAlt2.getText().replaceAll("'", "''").toLowerCase() + "%' ";

        
        //Agregando filtro por Nombre
        if(!txtNomItm.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(a1.ITEMDESC) LIKE '" + txtNomItm.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "%' ";
        
////////        //Agregando filtro por Codigo de Material
////////        if(!txtCodMat.getText().equals(""))//cambiar por
////////            sqlFlt = sqlFlt + " and a9.Extender_Record_ID = " + txtCodMat.getText().replaceAll("'", "''") + "";
////////        
////////                //Agregando filtro por Codigo ref general
////////        if(!txtCodRefG.getText().equals(""))//cambiar por 
////////            sqlFlt = sqlFlt + " and a10.Extender_Record_ID = " + txtCodRefG.getText().replaceAll("'", "''") + "";
////////        
////////        //Agregando filtro por Codigo Ref Detallada
////////        if(!txtCodRefD.getText().equals(""))//cambiar por 
////////            sqlFlt = sqlFlt + " and a11.Extender_Record_ID  = " + txtCodRefD.getText().replaceAll("'", "''") + "";
////////        
////////        //Agregando filtro por Codigo de Marca
////////        if(!txtCodMar.getText().equals(""))
////////            sqlFlt = sqlFlt + " and a8.Extender_Record_ID = " + txtCodMar.getText().replaceAll("'", "''") + "";
////////        
////////        //Agregando filtro por Codigo de Unidad de Medida
////////        if(!txtCodUniMed.getText().equals(""))
////////            sqlFlt = sqlFlt + " and a1.UOMSCHDL = " + txtUniMed.getText().replaceAll("'", "''") + "";
        
////        //Agregando filtro por Item(s) por Unidad//NO VA
////        if(!txtItmUni.getText().equals(""))
////            sqlFlt = sqlFlt + " and nd_itmUni = " + txtItmUni.getText().replaceAll("'", "''") + "";
              
        
      return sqlFlt ;
    }
    
//
//    private boolean consultarDatInv(String strFlt){
//       strFil = strFlt;
//       try{
//            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if (conCab!=null){
//
//                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//
//                /**Agregando el Sql de Consulta para el Maestro*/
//                strSql = "";
//                strSql = strSql + "SELECT * FROM tbm_inv WHERE st_reg not in ('T','E','U') and co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//                strSql = strSql + strFil + " ORDER BY co_itm";
//
//              //  System.out.println("====> "+strSql);
//
//                rstCab = stmCab.executeQuery(strSql);
//
//                if(rstCab.next()){
//                    rstCab.last();
//                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros.");
//                    cargarReg();
//                }
//                else{
//                    objTooBar.setMenSis("Se encontraron 0 registros...");
//                    strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
//                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
//                    rstCab = null;
//                    stmCab.close();
//                    conCab.close();
//                    stmCab=null;
//                    conCab=null;
//                    limpiarDatInv();
//                    return false;
//                }
//            blnCmb = false;
//            }
//   }
//   catch(SQLException Evt)
//   {
//      objUti.mostrarMsgErr_F1(this, Evt);
//      return false;
//   }
//   catch(Exception Evt)
//   {
//      objUti.mostrarMsgErr_F1(this, Evt);
//      return false;
//   }
//   return true;
//}
//
    
    
    
    /* Agrega el listener para detectar que hubo algun cambio en la caja de texto*/
    private void addListenerCambios(){
        objLisCmb = new LisTxt();
        txtCodItm.setText("");
        txtCodItm.getDocument().addDocumentListener(objLisCmb);
        txtCodAlt.getDocument().addDocumentListener(objLisCmb);
        txtCodAlt2.getDocument().addDocumentListener(objLisCmb);
        txtCosUni.getDocument().addDocumentListener(objLisCmb);
        txtCosUni1.getDocument().addDocumentListener(objLisCmb);
        txtCosUni2.getDocument().addDocumentListener(objLisCmb);
        txtCosUni3.getDocument().addDocumentListener(objLisCmb);
        txtItmUni.getDocument().addDocumentListener(objLisCmb);
        txtNomItm.getDocument().addDocumentListener(objLisCmb);
        txtStkAct.getDocument().addDocumentListener(objLisCmb);
        txtStkMax.getDocument().addDocumentListener(objLisCmb);
        txtStkMin.getDocument().addDocumentListener(objLisCmb);
        txtCodLin.getDocument().addDocumentListener(objLisCmb);//cambiar por material
        txtLin.getDocument().addDocumentListener(objLisCmb);
        txtCodGrp.getDocument().addDocumentListener(objLisCmb);
        txtGrp.getDocument().addDocumentListener(objLisCmb);
        txtCodMar.getDocument().addDocumentListener(objLisCmb); 
        txtMar.getDocument().addDocumentListener(objLisCmb);
        txtCodUniMed.getDocument().addDocumentListener(objLisCmb);
        txtUniMed.getDocument().addDocumentListener(objLisCmb);
        txaObs1.getDocument().addDocumentListener(objLisCmb);
        txaObs2.getDocument().addDocumentListener(objLisCmb);
    }   
    

     /* Clase de tipo DocumentListener para detectar los cambios en los textcomponent*/
    class LisTxt implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
    }
    
    
    /**Procedimiento que limpia todas las cajas de texto que existen en el frame*/
    public void limpiarDatInv()
    { 
        txtCodItm.setText("");
        txtCodAlt.setText("");
        txtCodAlt2.setText("");
        txtNomItm.setText("");
        txtItmUni.setText("");
        txtCosUni.setText("");
        txtCosUni1.setText("");
        txtCosUni2.setText("");
        txtCosUni3.setText("");
        txtStkAct.setText("");
        txtStkMin.setText("");
        txtStkMax.setText("");
        txtCodLin.setText("");//cambiar por material
        txtLin.setText("");
        txtCodGrp.setText("");
        txtGrp.setText("");
        txtCodMar.setText("");
        txtMar.setText("");
        txtCodUniMed.setText("");
        txtUniMed.setText("");
////        chkEsItmSer.setSelected(false);
////        chkEsItmOtr.setSelected(false);
////        chkEsItmTra.setSelected(false);
////        chkIvaCom.setSelected(false);
////        chkIvaVta.setSelected(false);
////        chkCamPreVta.setSelected(false);
////
////        chkOblCamNomItm.setSelected(false);

        txaObs1.setText("");
        txaObs2.setText(""); 
        txtfecing.setText("");
        txtfecmod.setText("");
        lblusuing.setText("");
        lblusuing1.setText("");
      
        spnPeso.setValue(new Double(0.00));
////        objTblModPrv.removeAllRows();
////        objTblModCla.removeAllRows();


        strCodLin="";
        strDesLarLin="";
        strCodGrp="";
        strDesLarGrp="";
        strCodMar="";
        strDesLarMar=""; 
        strCodUni="";
        strDesLarUni="";  
        txtCodGP.setText("");
        txtCodCla.setText("");
        txtCodCompr.setText("");
        txtCodLinea.setText("");
        txtCla.setText("");
        txtLinea.setText("");
        txtCodMat.setText("");
        txtMat.setText("");
        txtCodMar.setText("");
        txtMar.setText("");       
        txtCodUniMed.setText("");
        txtUniMed.setText("");         
        txtCodTama.setText(""); 
        txtCompr.setText("");  
        txtCategoria.setText(""); 
        txtCodCategoria.setText(""); 
        txtCodControl.setText(""); 
        txtControl.setText("");  
        txtCategoriaCompra.setText(""); 
        txtCodCategoriaCompra.setText("");         
        txtClaseCompra.setText(""); 
        txtCodClaseCompra.setText(""); 
        txtCodLineaCompra.setText("");  
        txtLineaCompra.setText("");  
    }
        
    
    
    
    /**Procedimiento que me permÃ­te habilitar las cajas de texto para que estas puedan 
    ser manipuladas por el usuario*/
    public void habilitarDatInv()
    {                                
       txtCodAlt.setEnabled(true);
       txtNomItm.setEditable(true);
       txtItmUni.setEnabled(true);
       //txtCosUni1.setEnabled(true);
       //txtCosUni2.setEnabled(true);
       //txtCosUni3.setEnabled(true);
       txtStkMin.setEnabled(true);
       txtStkMax.setEnabled(true);
       txtCodLin.setEnabled(true);//cambiar por material
       txtLin.setEnabled(true);
       txtCodGrp.setEnabled(true);
       txtGrp.setEnabled(true);
       txtCodMar.setEnabled(true);
       txtMar.setEnabled(true);
       txtCodUniMed.setEnabled(true);
       txtUniMed.setEnabled(true);
       chkEsItmSer.setEnabled(true);
       chkEsItmOtr.setEnabled(true);
       chkIvaCom.setEnabled(true);
       chkIvaVta.setEnabled(true);
       txaObs1.setEditable(true);
       txaObs2.setEditable(true);
       cboEst.setEnabled(true);
       butGrp.setEnabled(true);
       butLin.setEnabled(true);
       butMar.setEnabled(true);
       butUniMed.setEnabled(true);
////       butCod.setEnabled(false);//CHIQUI

    }
    
    
    /**Procedimiento que deshabilita las cajas de texto para que estas no puedan ser 
    manipuladas por el usuario*/
    public void deshabilitarDatInv()
    {
       txtCodItm.setEditable(false);
       txtCodAlt.setEnabled(false);
       txtNomItm.setEditable(false);
       txtItmUni.setEnabled(false);
       txtCosUni1.setEnabled(false);
       txtCosUni2.setEnabled(false);
       txtCosUni3.setEnabled(false);
       txtStkMin.setEnabled(false);
       txtStkMax.setEnabled(false);
       txtCodLin.setEnabled(false);//cambiar por material
       txtLin.setEnabled(false);
       txtCodGrp.setEnabled(false);
       txtGrp.setEnabled(false);
       txtCodMar.setEnabled(false);
       txtMar.setEnabled(false);
       txtCodUniMed.setEnabled(false);
       txtUniMed.setEnabled(false);
       chkEsItmSer.setEnabled(false);
       chkEsItmOtr.setEnabled(false);
       chkIvaCom.setEnabled(false);
       chkIvaVta.setEnabled(false);
       txaObs1.setEditable(false);
       txaObs2.setEditable(false);
       cboEst.setEnabled(false);
       butGrp.setEnabled(false);
       butLin.setEnabled(false);
       butMar.setEnabled(false);
       butUniMed.setEnabled(false);
////       butCod.setEnabled(false);//CHIQUI
    }
    
    
    /**Procedimiento para hacer editable las Cajas de Texto*/
    public void editableDatInv()
    {
       txtCodAlt.setEditable(true);
       txtNomItm.setEditable(true);
       txtItmUni.setEditable(true);
       txtCosUni1.setEditable(true);
       txtCosUni2.setEditable(true);
       txtCosUni3.setEditable(true);
       txtStkMin.setEditable(true);
       txtStkMax.setEditable(true);
       txtCodLin.setEditable(true);//cambiar por material
       txtLin.setEditable(true);
       txtCodGrp.setEditable(true);
       txtGrp.setEditable(true);
       txtCodMar.setEditable(true);
       txtMar.setEditable(true);
       txtCodUniMed.setEditable(true);
       txtUniMed.setEditable(true);
       chkEsItmSer.setEnabled(true);
       chkEsItmOtr.setEnabled(true);
       chkIvaCom.setEnabled(true);
       chkIvaVta.setEnabled(true);
       txaObs1.setEditable(true);
       txaObs2.setEditable(true);
       cboEst.setEnabled(true);
       butGrp.setEnabled(false);
       butLin.setEnabled(true);
       butMar.setEnabled(true);
       butUniMed.setEnabled(true);
////       butCod.setEnabled(true);//CHIQUI
    }
    
    
    private boolean flagNum(boolean blnFlgNum)
    {
        return blnFlgNum; 
    }
    
    
    private boolean flagCon(boolean blnFlgCon)
    {
        return blnFlgCon; 
    }
    
    
    /**<PRE>
     *FunciÃ³n quÃ© permitirÃ¡ validar que contenido de las cajas de texto segÃºn la validaciÃ³n que se desee
     *realizar. 
     *Esta funciÃ³n recibirÃ¡ los siquientes parÃ¡metros:
     *
     *jtfTxt: Objeto de tipo JTextField
     *jtfTxtAux: AlgÃºn objeto adicional de tipo JtextField
     *strCam: El campo al quÃ© estamos haciendo referencia.
     *strMsj: Si se envÃ­a como parÃ¡metro 3 en intOpc en estÃ¡ variable se deberÃ¡ enviar el Mensaje 
     *        de validaciÃ³n y el campo, pero si se envia 1 o 2 se la envÃ­a vacÃ­a. 
     *        (Ejm: "El campo <<X>> ya existe")
     *intOpc: se enviarÃ¡ como parÃ¡metro 1 si es una validaciÃ³n de nÃºmeros y 2 si es de lÃ­mite.
     *strMsjGen: El generÃ³ del campo (Ejm: "el")
     *intTab: Variable de tipo entero que recibirÃ¡ el Ã­ndice de JTabbedPane al que va a hacer referencia
     *</PRE>**/
    private void valTxt (javax.swing.JTextField jtfTxt, javax.swing.JTextField jtfTxtAux, String strCam, String strMsj, int intOpc,String strMsjGen, int intTab) {
        if (intOpc == 1){
            strMsg = "El campo <<"+ strCam +">> sólo acepta valores numéricos.\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        if (intOpc == 2) {
            strMsg = "El campo <<"+ strCam +">> ha sobrepasado el límite.\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        if (intOpc == 3){
            strMsg = "El campo " + strMsj + ".\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        int intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (intOpp == JOptionPane.YES_OPTION){
            tabGenInv.setSelectedIndex(intTab);
            jtfTxt.selectAll();
            jtfTxt.requestFocus();
        }
        else {
            tabGenInv.setSelectedIndex(intTab);
            jtfTxt.setText(""); 
            jtfTxtAux.setText("");
            jtfTxt.requestFocus();            
            }
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panGen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGenInv = new javax.swing.JTabbedPane();
        panPriInv = new javax.swing.JPanel();
        lblCodClaInv = new javax.swing.JLabel();
        lblCodAlt = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        lblNomItm = new javax.swing.JLabel();
        txtNomItm = new javax.swing.JTextField();
        lblgrp = new javax.swing.JLabel();
        lblLin = new javax.swing.JLabel();
        lblItmUni = new javax.swing.JLabel();
        txtItmUni = new javax.swing.JTextField();
        panItmSer = new javax.swing.JPanel();
        chkEsItmSer = new javax.swing.JCheckBox();
        chkEsItmTra = new javax.swing.JCheckBox();
        chkEsItmOtr = new javax.swing.JCheckBox();
        panIva = new javax.swing.JPanel();
        chkIvaVta = new javax.swing.JCheckBox();
        chkIvaCom = new javax.swing.JCheckBox();
        butLin = new javax.swing.JButton();
        txtLin = new javax.swing.JTextField();
        txtGrp = new javax.swing.JTextField();
        butGrp = new javax.swing.JButton();
        txtCodLin = new javax.swing.JTextField();
        txtCodGrp = new javax.swing.JTextField();
        butCod = new javax.swing.JButton();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltAux = new javax.swing.JTextField();
        lblCodAlt1 = new javax.swing.JLabel();
        txtCodAlt2 = new javax.swing.JTextField();
        lblWebCli1 = new javax.swing.JLabel();
        lblWebCli2 = new javax.swing.JLabel();
        txtfecing = new javax.swing.JLabel();
        lblWebCli4 = new javax.swing.JLabel();
        lblusuing = new javax.swing.JLabel();
        txtfecmod = new javax.swing.JLabel();
        lblWebCli5 = new javax.swing.JLabel();
        lblusuing1 = new javax.swing.JLabel();
        lblPeso = new javax.swing.JLabel();
        spnPeso = new javax.swing.JSpinner();
        panItmSer1 = new javax.swing.JPanel();
        chkpermodnomcom = new javax.swing.JCheckBox();
        chkpermodnomven = new javax.swing.JCheckBox();
        chkOblCamNomItm = new javax.swing.JCheckBox();
        chkCamPreVta = new javax.swing.JCheckBox();
        lblCodAlt2 = new javax.swing.JLabel();
        txtCodGP = new javax.swing.JTextField();
        txtCortaUniMed = new javax.swing.JTextField();
        lblCtaLetra = new javax.swing.JLabel();
        panItmSer2 = new javax.swing.JPanel();
        txtUniMed = new javax.swing.JTextField();
        txtCodUniMed = new javax.swing.JTextField();
        txtCodMar = new javax.swing.JTextField();
        txtMar = new javax.swing.JTextField();
        txtCodMat = new javax.swing.JTextField();
        txtMat = new javax.swing.JTextField();
        butMat = new javax.swing.JButton();
        butMar = new javax.swing.JButton();
        butUniMed = new javax.swing.JButton();
        lblUniMed = new javax.swing.JLabel();
        lblMar = new javax.swing.JLabel();
        lblMat = new javax.swing.JLabel();
        lblEst = new javax.swing.JLabel();
        cboEst = new javax.swing.JComboBox();
        lblTama = new javax.swing.JLabel();
        txtCodTama = new javax.swing.JTextField();
        txtCodCompr = new javax.swing.JTextField();
        lblMat1 = new javax.swing.JLabel();
        txtCompr = new javax.swing.JTextField();
        butCompr = new javax.swing.JButton();
        lblControl = new javax.swing.JLabel();
        txtCodControl = new javax.swing.JTextField();
        txtControl = new javax.swing.JTextField();
        butCon = new javax.swing.JButton();
        lblMat4 = new javax.swing.JLabel();
        txtCodCategoriaCompra = new javax.swing.JTextField();
        txtCategoriaCompra = new javax.swing.JTextField();
        butCategoriaCompra = new javax.swing.JButton();
        lblMat5 = new javax.swing.JLabel();
        txtCodLineaCompra = new javax.swing.JTextField();
        txtLineaCompra = new javax.swing.JTextField();
        butLineaCompra = new javax.swing.JButton();
        lblMat7 = new javax.swing.JLabel();
        txtCodClaseCompra = new javax.swing.JTextField();
        txtClaseCompra = new javax.swing.JTextField();
        butClaseCompra = new javax.swing.JButton();
        lblMat2 = new javax.swing.JLabel();
        txtCodCategoria = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        butCategoria = new javax.swing.JButton();
        lblRefG = new javax.swing.JLabel();
        txtCodLinea = new javax.swing.JTextField();
        txtLinea = new javax.swing.JTextField();
        butLinea = new javax.swing.JButton();
        lblRefD = new javax.swing.JLabel();
        txtCodCla = new javax.swing.JTextField();
        txtCla = new javax.swing.JTextField();
        butCla = new javax.swing.JButton();
        panDatTec = new javax.swing.JPanel();
        panGrpPre = new javax.swing.JPanel();
        lblCosUni = new javax.swing.JLabel();
        txtCosUni = new javax.swing.JTextField();
        txtCosUni1 = new javax.swing.JTextField();
        lblCosUni1 = new javax.swing.JLabel();
        lblCosUni2 = new javax.swing.JLabel();
        txtCosUni2 = new javax.swing.JTextField();
        txtCosUni3 = new javax.swing.JTextField();
        lblCosUni3 = new javax.swing.JLabel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panStk = new javax.swing.JPanel();
        lblStkAct = new javax.swing.JLabel();
        txtStkAct = new javax.swing.JTextField();
        lblStkMin = new javax.swing.JLabel();
        txtStkMin = new javax.swing.JTextField();
        txtStkMax = new javax.swing.JTextField();
        lblStkMax = new javax.swing.JLabel();
        txtStkMinAux = new javax.swing.JTextField();
        txtStkMaxAux = new javax.swing.JTextField();
        panDatABC = new javax.swing.JPanel();
        panGrpPre1 = new javax.swing.JPanel();
        lblMat3 = new javax.swing.JLabel();
        txtCodAbcTUV = new javax.swing.JTextField();
        txtAbcTUV = new javax.swing.JTextField();
        butAbcTUV = new javax.swing.JButton();
        lblFre = new javax.swing.JLabel();
        txtCodAbcFRE = new javax.swing.JTextField();
        txtAbcFRE = new javax.swing.JTextField();
        butAbcFRE = new javax.swing.JButton();
        lblPe = new javax.swing.JLabel();
        txtCodAbcPES = new javax.swing.JTextField();
        txtAbcPES = new javax.swing.JTextField();
        butAbcPES = new javax.swing.JButton();
        lblMat6 = new javax.swing.JLabel();
        txtCodAbcCLA = new javax.swing.JTextField();
        txtAbcCLA = new javax.swing.JTextField();
        butAbcCLA = new javax.swing.JButton();
        lblValven = new javax.swing.JLabel();
        txtCodAbcVAL = new javax.swing.JTextField();
        txtAbcVAL = new javax.swing.JTextField();
        butAbcVAL = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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

        panGen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setForeground(new java.awt.Color(10, 36, 106));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Maestro de Inventario");
        panGen.add(lblTit, java.awt.BorderLayout.PAGE_START);

        tabGenInv.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        panPriInv.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        panPriInv.setLayout(null);

        lblCodClaInv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodClaInv.setText("Código ");
        panPriInv.add(lblCodClaInv);
        lblCodClaInv.setBounds(20, 28, 60, 15);

        lblCodAlt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt.setText("Codigo Zafiro");
        lblCodAlt.setToolTipText("");
        panPriInv.add(lblCodAlt);
        lblCodAlt.setBounds(20, 45, 116, 15);

        txtCodAlt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodAlt);
        txtCodAlt.setBounds(136, 46, 84, 20);

        lblNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomItm.setText("Nombre del Item");
        panPriInv.add(lblNomItm);
        lblNomItm.setBounds(20, 91, 120, 15);

        txtNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomItmKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomItmKeyTyped(evt);
            }
        });
        panPriInv.add(txtNomItm);
        txtNomItm.setBounds(136, 90, 328, 20);

        lblgrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblgrp.setText("Grupo");
        panPriInv.add(lblgrp);
        lblgrp.setBounds(320, 10, 160, 15);

        lblLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLin.setText("Linea");
        panPriInv.add(lblLin);
        lblLin.setBounds(320, 10, 160, 15);

        lblItmUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblItmUni.setText("Item(s) por Unidad");
        panPriInv.add(lblItmUni);
        lblItmUni.setBounds(330, 10, 140, 15);

        txtItmUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtItmUni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItmUniFocusLost(evt);
            }
        });
        txtItmUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItmUniActionPerformed(evt);
            }
        });
        panPriInv.add(txtItmUni);
        txtItmUni.setBounds(320, 10, 184, 20);

        panItmSer.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado del Item"));
        panItmSer.setName("Estado del Item"); // NOI18N
        panItmSer.setLayout(null);

        chkEsItmSer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmSer.setText("Es item de Servicio");
        chkEsItmSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmSerActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmSer);
        chkEsItmSer.setBounds(10, 20, 160, 20);

        chkEsItmTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmTra.setText("Es item de Transporte");
        chkEsItmTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmTraActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmTra);
        chkEsItmTra.setBounds(10, 40, 156, 20);

        chkEsItmOtr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmOtr.setText("Otros");
        chkEsItmOtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmOtrActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmOtr);
        chkEsItmOtr.setBounds(10, 60, 152, 20);

        panPriInv.add(panItmSer);
        panItmSer.setBounds(320, 30, 80, 30);

        panIva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Iva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panIva.setLayout(null);

        chkIvaVta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkIvaVta.setText("Iva en Ventas");
        chkIvaVta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIvaVtaItemStateChanged(evt);
            }
        });
        chkIvaVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaVtaActionPerformed(evt);
            }
        });
        panIva.add(chkIvaVta);
        chkIvaVta.setBounds(8, 40, 116, 23);

        chkIvaCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkIvaCom.setText("Iva en Compras");
        chkIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaComActionPerformed(evt);
            }
        });
        panIva.add(chkIvaCom);
        chkIvaCom.setBounds(8, 16, 101, 23);

        panPriInv.add(panIva);
        panIva.setBounds(500, 0, 70, 40);

        butLin.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butLin.setText("...");
        butLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLinActionPerformed(evt);
            }
        });
        panPriInv.add(butLin);
        butLin.setBounds(400, 10, 24, 20);

        txtLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLinFocusLost(evt);
            }
        });
        txtLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLinActionPerformed(evt);
            }
        });
        panPriInv.add(txtLin);
        txtLin.setBounds(360, 10, 144, 20);

        txtGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGrpFocusLost(evt);
            }
        });
        txtGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpActionPerformed(evt);
            }
        });
        panPriInv.add(txtGrp);
        txtGrp.setBounds(360, 10, 144, 20);

        butGrp.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butGrp.setText("...");
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });
        panPriInv.add(butGrp);
        butGrp.setBounds(420, 10, 24, 20);

        txtCodLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodLin.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtCodLinAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtCodLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLinFocusLost(evt);
            }
        });
        txtCodLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLinActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodLin);
        txtCodLin.setBounds(330, 60, 40, 21);

        txtCodGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });
        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodGrp);
        txtCodGrp.setBounds(390, 10, 40, 20);

        butCod.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCod.setText("...");
        butCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodActionPerformed(evt);
            }
        });
        panPriInv.add(butCod);
        butCod.setBounds(220, 24, 24, 20);

        txtCodItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodItm);
        txtCodItm.setBounds(136, 24, 80, 20);

        txtCodAltAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panPriInv.add(txtCodAltAux);
        txtCodAltAux.setBounds(230, 50, 84, 20);

        lblCodAlt1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt1.setText("Código 3L");
        panPriInv.add(lblCodAlt1);
        lblCodAlt1.setBounds(20, 68, 116, 15);

        txtCodAlt2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodAlt2.setEnabled(false);
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
        });
        panPriInv.add(txtCodAlt2);
        txtCodAlt2.setBounds(136, 68, 84, 20);

        lblWebCli1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli1.setText("Fecha Ingreso:");
        lblWebCli1.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli1);
        lblWebCli1.setBounds(20, 280, 100, 20);

        lblWebCli2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli2.setText("Fecha Modificacion:");
        lblWebCli2.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli2);
        lblWebCli2.setBounds(20, 300, 100, 20);

        txtfecing.setBackground(new java.awt.Color(255, 255, 255));
        txtfecing.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtfecing.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        txtfecing.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtfecing.setOpaque(true);
        panPriInv.add(txtfecing);
        txtfecing.setBounds(136, 280, 112, 20);

        lblWebCli4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli4.setText("Usuario Ingreso:");
        lblWebCli4.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli4);
        lblWebCli4.setBounds(270, 280, 96, 20);

        lblusuing.setBackground(new java.awt.Color(255, 255, 255));
        lblusuing.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblusuing.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        lblusuing.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblusuing.setOpaque(true);
        panPriInv.add(lblusuing);
        lblusuing.setBounds(380, 280, 172, 20);

        txtfecmod.setBackground(new java.awt.Color(255, 255, 255));
        txtfecmod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtfecmod.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        txtfecmod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtfecmod.setOpaque(true);
        panPriInv.add(txtfecmod);
        txtfecmod.setBounds(136, 300, 112, 20);

        lblWebCli5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli5.setText("Usuario Modfico:");
        lblWebCli5.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli5);
        lblWebCli5.setBounds(270, 300, 96, 20);

        lblusuing1.setBackground(new java.awt.Color(255, 255, 255));
        lblusuing1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblusuing1.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        lblusuing1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblusuing1.setOpaque(true);
        panPriInv.add(lblusuing1);
        lblusuing1.setBounds(380, 300, 172, 20);

        lblPeso.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPeso.setText("Peso:");
        panPriInv.add(lblPeso);
        lblPeso.setBounds(226, 114, 60, 15);

        spnPeso.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        panPriInv.add(spnPeso);
        spnPeso.setBounds(268, 114, 80, 20);

        panItmSer1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifica Nombre Itm."));
        panItmSer1.setName("Desc.Itm"); // NOI18N
        panItmSer1.setLayout(null);

        chkpermodnomcom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkpermodnomcom.setText("Permite cambiar nombre del item  en compras.");
        chkpermodnomcom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkpermodnomcomActionPerformed(evt);
            }
        });
        panItmSer1.add(chkpermodnomcom);
        chkpermodnomcom.setBounds(10, 50, 260, 23);

        chkpermodnomven.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkpermodnomven.setText("Permite modificar nombre del item  en ventas.");
        chkpermodnomven.setActionCommand("Permite modificar nombre del item en ventas.");
        chkpermodnomven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkpermodnomvenActionPerformed(evt);
            }
        });
        panItmSer1.add(chkpermodnomven);
        chkpermodnomven.setBounds(10, 20, 260, 30);

        chkOblCamNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkOblCamNomItm.setText("Obligatorio cambiar nombre del ítem");
        panItmSer1.add(chkOblCamNomItm);
        chkOblCamNomItm.setBounds(10, 80, 260, 23);

        panPriInv.add(panItmSer1);
        panItmSer1.setBounds(580, 10, 90, 30);

        chkCamPreVta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkCamPreVta.setText("Bloquear cambio de precio ventas.");
        panPriInv.add(chkCamPreVta);
        chkCamPreVta.setBounds(440, 30, 210, 23);

        lblCodAlt2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt2.setText("Codigo Dynamics (GP)");
        lblCodAlt2.setToolTipText("");
        panPriInv.add(lblCodAlt2);
        lblCodAlt2.setBounds(20, 114, 116, 15);

        txtCodGP.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        txtCodGP.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodGP.setEnabled(false);
        txtCodGP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGPFocusGained(evt);
            }
        });
        panPriInv.add(txtCodGP);
        txtCodGP.setBounds(136, 114, 84, 20);

        txtCortaUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCortaUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCortaUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCortaUniMedFocusLost(evt);
            }
        });
        txtCortaUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCortaUniMedActionPerformed(evt);
            }
        });
        panPriInv.add(txtCortaUniMed);
        txtCortaUniMed.setBounds(590, 280, 40, 20);

        lblCtaLetra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCtaLetra.setText("Cuenta Letras");
        panPriInv.add(lblCtaLetra);
        lblCtaLetra.setBounds(478, 92, 130, 15);

        panItmSer2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panItmSer2.setName(""); // NOI18N
        panItmSer2.setLayout(null);

        txtUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUniMedFocusLost(evt);
            }
        });
        txtUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUniMedActionPerformed(evt);
            }
        });
        panItmSer2.add(txtUniMed);
        txtUniMed.setBounds(160, 50, 130, 20);

        txtCodUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodUniMedFocusLost(evt);
            }
        });
        txtCodUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodUniMedActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodUniMed);
        txtCodUniMed.setBounds(120, 50, 40, 20);

        txtCodMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarFocusLost(evt);
            }
        });
        txtCodMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodMar);
        txtCodMar.setBounds(120, 30, 40, 20);

        txtMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarFocusLost(evt);
            }
        });
        txtMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarActionPerformed(evt);
            }
        });
        panItmSer2.add(txtMar);
        txtMar.setBounds(160, 30, 130, 20);

        txtCodMat.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodMat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMatFocusLost(evt);
            }
        });
        txtCodMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMatActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodMat);
        txtCodMat.setBounds(120, 10, 40, 20);

        txtMat.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtMat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatFocusLost(evt);
            }
        });
        txtMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatActionPerformed(evt);
            }
        });
        panItmSer2.add(txtMat);
        txtMat.setBounds(160, 10, 130, 20);

        butMat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butMat.setText("...");
        butMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMatActionPerformed(evt);
            }
        });
        panItmSer2.add(butMat);
        butMat.setBounds(290, 10, 24, 20);

        butMar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panItmSer2.add(butMar);
        butMar.setBounds(290, 30, 24, 20);

        butUniMed.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butUniMed.setText("...");
        butUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUniMedActionPerformed(evt);
            }
        });
        panItmSer2.add(butUniMed);
        butUniMed.setBounds(290, 50, 24, 20);

        lblUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblUniMed.setText("Uni. Med.");
        panItmSer2.add(lblUniMed);
        lblUniMed.setBounds(0, 50, 80, 15);

        lblMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMar.setText("Marca");
        panItmSer2.add(lblMar);
        lblMar.setBounds(0, 30, 80, 15);

        lblMat.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat.setText("Material");
        panItmSer2.add(lblMat);
        lblMat.setBounds(0, 10, 80, 15);

        lblEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEst.setText("Estado");
        panItmSer2.add(lblEst);
        lblEst.setBounds(330, 110, 40, 15);

        cboEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboEst.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEst.setToolTipText("");
        cboEst.setEnabled(false);
        cboEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstActionPerformed(evt);
            }
        });
        panItmSer2.add(cboEst);
        cboEst.setBounds(410, 110, 80, 20);

        lblTama.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTama.setText("Tamaño");
        lblTama.setToolTipText("");
        panItmSer2.add(lblTama);
        lblTama.setBounds(0, 90, 50, 15);

        txtCodTama.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodTama.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTamaFocusLost(evt);
            }
        });
        txtCodTama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTamaActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodTama);
        txtCodTama.setBounds(120, 90, 84, 20);

        txtCodCompr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCompr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodComprFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodComprFocusLost(evt);
            }
        });
        txtCodCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodComprActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodCompr);
        txtCodCompr.setBounds(120, 70, 40, 20);

        lblMat1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat1.setText("Compras");
        panItmSer2.add(lblMat1);
        lblMat1.setBounds(0, 70, 80, 15);

        txtCompr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCompr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComprFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComprFocusLost(evt);
            }
        });
        txtCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComprActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCompr);
        txtCompr.setBounds(160, 70, 130, 20);

        butCompr.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCompr.setText("...");
        butCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComprActionPerformed(evt);
            }
        });
        panItmSer2.add(butCompr);
        butCompr.setBounds(290, 70, 24, 20);

        lblControl.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblControl.setText("Control");
        panItmSer2.add(lblControl);
        lblControl.setBounds(330, 10, 80, 15);

        txtCodControl.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodControl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodControlFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodControlFocusLost(evt);
            }
        });
        txtCodControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodControlActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodControl);
        txtCodControl.setBounds(440, 10, 40, 20);

        txtControl.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtControl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtControlFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtControlFocusLost(evt);
            }
        });
        txtControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtControlActionPerformed(evt);
            }
        });
        panItmSer2.add(txtControl);
        txtControl.setBounds(480, 10, 130, 20);

        butCon.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCon.setText("...");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panItmSer2.add(butCon);
        butCon.setBounds(610, 10, 24, 20);

        lblMat4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat4.setText("Categoria Compra");
        panItmSer2.add(lblMat4);
        lblMat4.setBounds(330, 70, 100, 15);

        txtCodCategoriaCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCategoriaCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCategoriaCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCategoriaCompraFocusLost(evt);
            }
        });
        txtCodCategoriaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCategoriaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodCategoriaCompra);
        txtCodCategoriaCompra.setBounds(440, 73, 40, 20);

        txtCategoriaCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCategoriaCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCategoriaCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCategoriaCompraFocusLost(evt);
            }
        });
        txtCategoriaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCategoriaCompra);
        txtCategoriaCompra.setBounds(480, 73, 130, 20);

        butCategoriaCompra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCategoriaCompra.setText("...");
        butCategoriaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCategoriaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(butCategoriaCompra);
        butCategoriaCompra.setBounds(610, 73, 24, 20);

        lblMat5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat5.setText("Linea Compra");
        panItmSer2.add(lblMat5);
        lblMat5.setBounds(330, 50, 100, 15);

        txtCodLineaCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodLineaCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLineaCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLineaCompraFocusLost(evt);
            }
        });
        txtCodLineaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLineaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodLineaCompra);
        txtCodLineaCompra.setBounds(440, 52, 40, 20);

        txtLineaCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtLineaCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLineaCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineaCompraFocusLost(evt);
            }
        });
        txtLineaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtLineaCompra);
        txtLineaCompra.setBounds(480, 52, 130, 20);

        butLineaCompra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butLineaCompra.setText("...");
        butLineaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLineaCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(butLineaCompra);
        butLineaCompra.setBounds(610, 51, 24, 20);

        lblMat7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat7.setText("Clase Compra");
        panItmSer2.add(lblMat7);
        lblMat7.setBounds(330, 30, 100, 15);

        txtCodClaseCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodClaseCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodClaseCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClaseCompraFocusLost(evt);
            }
        });
        txtCodClaseCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClaseCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtCodClaseCompra);
        txtCodClaseCompra.setBounds(440, 31, 40, 20);

        txtClaseCompra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtClaseCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClaseCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtClaseCompraFocusLost(evt);
            }
        });
        txtClaseCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaseCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(txtClaseCompra);
        txtClaseCompra.setBounds(480, 31, 130, 20);

        butClaseCompra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butClaseCompra.setText("...");
        butClaseCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaseCompraActionPerformed(evt);
            }
        });
        panItmSer2.add(butClaseCompra);
        butClaseCompra.setBounds(610, 31, 24, 20);

        panPriInv.add(panItmSer2);
        panItmSer2.setBounds(20, 140, 650, 130);

        lblMat2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat2.setText("Categoria");
        panPriInv.add(lblMat2);
        lblMat2.setBounds(400, 70, 80, 10);

        txtCodCategoria.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCategoriaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCategoriaFocusLost(evt);
            }
        });
        txtCodCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCategoriaActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodCategoria);
        txtCodCategoria.setBounds(480, 60, 40, 20);

        txtCategoria.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCategoriaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCategoriaFocusLost(evt);
            }
        });
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });
        panPriInv.add(txtCategoria);
        txtCategoria.setBounds(520, 60, 130, 20);

        butCategoria.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCategoria.setText("...");
        butCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCategoriaActionPerformed(evt);
            }
        });
        panPriInv.add(butCategoria);
        butCategoria.setBounds(650, 60, 24, 20);

        lblRefG.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRefG.setText("Linea");
        panPriInv.add(lblRefG);
        lblRefG.setBounds(360, 70, 80, 15);

        txtCodLinea.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodLinea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLineaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLineaFocusLost(evt);
            }
        });
        txtCodLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLineaActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodLinea);
        txtCodLinea.setBounds(480, 70, 40, 20);

        txtLinea.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtLinea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLineaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineaFocusLost(evt);
            }
        });
        txtLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineaActionPerformed(evt);
            }
        });
        panPriInv.add(txtLinea);
        txtLinea.setBounds(520, 70, 130, 20);

        butLinea.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butLinea.setText("...");
        butLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLineaActionPerformed(evt);
            }
        });
        panPriInv.add(butLinea);
        butLinea.setBounds(650, 70, 24, 20);

        lblRefD.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRefD.setText("Clase");
        panPriInv.add(lblRefD);
        lblRefD.setBounds(360, 60, 80, 15);

        txtCodCla.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClaFocusLost(evt);
            }
        });
        txtCodCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClaActionPerformed(evt);
            }
        });
        panPriInv.add(txtCodCla);
        txtCodCla.setBounds(480, 60, 40, 20);

        txtCla.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtClaFocusLost(evt);
            }
        });
        txtCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaActionPerformed(evt);
            }
        });
        panPriInv.add(txtCla);
        txtCla.setBounds(520, 60, 130, 20);

        butCla.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCla.setText("...");
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        panPriInv.add(butCla);
        butCla.setBounds(650, 60, 24, 20);

        tabGenInv.addTab("General", panPriInv);

        panDatTec.setLayout(null);

        panGrpPre.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Precios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panGrpPre.setLayout(null);

        lblCosUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni.setText("Costo Unitario");
        panGrpPre.add(lblCosUni);
        lblCosUni.setBounds(8, 20, 140, 15);

        txtCosUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni.setEnabled(false);
        panGrpPre.add(txtCosUni);
        txtCosUni.setBounds(128, 20, 120, 21);

        txtCosUni1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni1.setEnabled(false);
        txtCosUni1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni1FocusLost(evt);
            }
        });
        txtCosUni1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni1ActionPerformed(evt);
            }
        });
        panGrpPre.add(txtCosUni1);
        txtCosUni1.setBounds(128, 44, 120, 21);

        lblCosUni1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni1.setText("Precio Unitario 1");
        panGrpPre.add(lblCosUni1);
        lblCosUni1.setBounds(8, 44, 140, 15);

        lblCosUni2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni2.setText("Precio Unitario 2");
        panGrpPre.add(lblCosUni2);
        lblCosUni2.setBounds(8, 68, 140, 15);

        txtCosUni2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni2.setEnabled(false);
        txtCosUni2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni2FocusLost(evt);
            }
        });
        txtCosUni2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni2ActionPerformed(evt);
            }
        });
        panGrpPre.add(txtCosUni2);
        txtCosUni2.setBounds(128, 68, 120, 21);

        txtCosUni3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni3ActionPerformed(evt);
            }
        });
        txtCosUni3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni3FocusLost(evt);
            }
        });
        panGrpPre.add(txtCosUni3);
        txtCosUni3.setBounds(128, 92, 120, 21);

        lblCosUni3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni3.setText("Precio Unitario 3");
        panGrpPre.add(lblCosUni3);
        lblCosUni3.setBounds(8, 92, 140, 15);

        panDatTec.add(panGrpPre);
        panGrpPre.setBounds(16, 12, 256, 120);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 1");
        panDatTec.add(lblObs1);
        lblObs1.setBounds(20, 152, 136, 15);

        txaObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });
        spnObs1.setViewportView(txaObs1);

        panDatTec.add(spnObs1);
        spnObs1.setBounds(112, 152, 408, 52);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs2.setText("Observación 2");
        panDatTec.add(lblObs2);
        lblObs2.setBounds(20, 208, 128, 15);

        txaObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txaObs2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2FocusLost(evt);
            }
        });
        spnObs2.setViewportView(txaObs2);

        panDatTec.add(spnObs2);
        spnObs2.setBounds(112, 208, 408, 52);

        panStk.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panStk.setLayout(null);

        lblStkAct.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkAct.setText("Stock Actual");
        panStk.add(lblStkAct);
        lblStkAct.setBounds(12, 16, 92, 15);

        txtStkAct.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtStkAct.setEnabled(false);
        panStk.add(txtStkAct);
        txtStkAct.setBounds(96, 16, 112, 21);

        lblStkMin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkMin.setText("Stock Mínimo");
        panStk.add(lblStkMin);
        lblStkMin.setBounds(12, 40, 96, 15);

        txtStkMin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtStkMin.setEnabled(false);
        txtStkMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStkMinFocusLost(evt);
            }
        });
        txtStkMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStkMinActionPerformed(evt);
            }
        });
        panStk.add(txtStkMin);
        txtStkMin.setBounds(96, 40, 112, 21);

        txtStkMax.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtStkMax.setEnabled(false);
        txtStkMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStkMaxFocusLost(evt);
            }
        });
        txtStkMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStkMaxActionPerformed(evt);
            }
        });
        panStk.add(txtStkMax);
        txtStkMax.setBounds(96, 64, 112, 21);

        lblStkMax.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkMax.setText("Stock Máximo");
        panStk.add(lblStkMax);
        lblStkMax.setBounds(12, 64, 92, 15);

        panDatTec.add(panStk);
        panStk.setBounds(312, 12, 220, 96);

        txtStkMinAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panDatTec.add(txtStkMinAux);
        txtStkMinAux.setBounds(316, 112, 112, 21);

        txtStkMaxAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panDatTec.add(txtStkMaxAux);
        txtStkMaxAux.setBounds(428, 112, 112, 21);

        tabGenInv.addTab("Datos Técnicos", panDatTec);

        panDatABC.setLayout(null);

        panGrpPre1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panGrpPre1.setLayout(null);

        lblMat3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat3.setText("Tuval");
        panGrpPre1.add(lblMat3);
        lblMat3.setBounds(10, 10, 80, 15);

        txtCodAbcTUV.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAbcTUV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAbcTUVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAbcTUVFocusLost(evt);
            }
        });
        txtCodAbcTUV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAbcTUVActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtCodAbcTUV);
        txtCodAbcTUV.setBounds(98, 10, 40, 20);

        txtAbcTUV.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtAbcTUV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbcTUVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbcTUVFocusLost(evt);
            }
        });
        txtAbcTUV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcTUVActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtAbcTUV);
        txtAbcTUV.setBounds(140, 10, 130, 21);

        butAbcTUV.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butAbcTUV.setText("...");
        butAbcTUV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbcTUVActionPerformed(evt);
            }
        });
        panGrpPre1.add(butAbcTUV);
        butAbcTUV.setBounds(270, 10, 24, 20);

        lblFre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFre.setText("Frecuencia");
        panGrpPre1.add(lblFre);
        lblFre.setBounds(10, 30, 80, 15);

        txtCodAbcFRE.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAbcFRE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAbcFREFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAbcFREFocusLost(evt);
            }
        });
        txtCodAbcFRE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAbcFREActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtCodAbcFRE);
        txtCodAbcFRE.setBounds(98, 30, 40, 20);

        txtAbcFRE.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtAbcFRE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbcFREFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbcFREFocusLost(evt);
            }
        });
        txtAbcFRE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcFREActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtAbcFRE);
        txtAbcFRE.setBounds(140, 30, 130, 21);

        butAbcFRE.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butAbcFRE.setText("...");
        butAbcFRE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbcFREActionPerformed(evt);
            }
        });
        panGrpPre1.add(butAbcFRE);
        butAbcFRE.setBounds(270, 30, 24, 20);

        lblPe.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPe.setText("Peso");
        panGrpPre1.add(lblPe);
        lblPe.setBounds(10, 50, 80, 15);

        txtCodAbcPES.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAbcPES.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAbcPESFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAbcPESFocusLost(evt);
            }
        });
        txtCodAbcPES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAbcPESActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtCodAbcPES);
        txtCodAbcPES.setBounds(98, 50, 40, 20);

        txtAbcPES.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtAbcPES.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbcPESFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbcPESFocusLost(evt);
            }
        });
        txtAbcPES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcPESActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtAbcPES);
        txtAbcPES.setBounds(140, 50, 130, 21);

        butAbcPES.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butAbcPES.setText("...");
        butAbcPES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbcPESActionPerformed(evt);
            }
        });
        panGrpPre1.add(butAbcPES);
        butAbcPES.setBounds(270, 50, 24, 20);

        lblMat6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMat6.setText("Clase");
        panGrpPre1.add(lblMat6);
        lblMat6.setBounds(10, 90, 80, 15);

        txtCodAbcCLA.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAbcCLA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAbcCLAFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAbcCLAFocusLost(evt);
            }
        });
        txtCodAbcCLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAbcCLAActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtCodAbcCLA);
        txtCodAbcCLA.setBounds(98, 90, 40, 20);

        txtAbcCLA.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtAbcCLA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbcCLAFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbcCLAFocusLost(evt);
            }
        });
        txtAbcCLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcCLAActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtAbcCLA);
        txtAbcCLA.setBounds(140, 90, 130, 21);

        butAbcCLA.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butAbcCLA.setText("...");
        butAbcCLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbcCLAActionPerformed(evt);
            }
        });
        panGrpPre1.add(butAbcCLA);
        butAbcCLA.setBounds(270, 90, 24, 20);

        lblValven.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblValven.setText("Valor Venta");
        panGrpPre1.add(lblValven);
        lblValven.setBounds(10, 70, 80, 15);

        txtCodAbcVAL.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAbcVAL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAbcVALFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAbcVALFocusLost(evt);
            }
        });
        txtCodAbcVAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAbcVALActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtCodAbcVAL);
        txtCodAbcVAL.setBounds(98, 70, 40, 20);

        txtAbcVAL.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtAbcVAL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbcVALFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbcVALFocusLost(evt);
            }
        });
        txtAbcVAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcVALActionPerformed(evt);
            }
        });
        panGrpPre1.add(txtAbcVAL);
        txtAbcVAL.setBounds(140, 70, 130, 21);

        butAbcVAL.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butAbcVAL.setText("...");
        butAbcVAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbcVALActionPerformed(evt);
            }
        });
        panGrpPre1.add(butAbcVAL);
        butAbcVAL.setBounds(270, 70, 24, 20);

        panDatABC.add(panGrpPre1);
        panGrpPre1.setBounds(10, 10, 660, 300);

        tabGenInv.addTab("Listado ABC", panDatABC);

        panGen.add(tabGenInv, java.awt.BorderLayout.CENTER);

        getContentPane().add(panGen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 406);
    }// </editor-fold>//GEN-END:initComponents


//     public int getCodItemenMaestro(java.sql.Connection con){
//        int intRes = 0;
//        try{
//                if (con!=null){
//                    java.sql.Statement stm = con.createStatement();
//                    String strSQL = "select max(co_itmmae) + 1 as co_itmmae from tbm_equinv";
//                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
//                    if (rst.next()){
//                        intRes = rst.getInt("co_itmmae");
//                    }else{
//                        intRes = 1;
//                    }
//                }
//            }catch (java.sql.SQLException e){
//                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
//            }
//    
//        return intRes;
//    }
//    
//    
//     
//      private boolean CrearItemenEmpresas(java.sql.Connection conInv,int intCodEqu , int intCodIt, String Ter)
//    {
//        java.sql.PreparedStatement pstInv;
//        java.sql.Statement stmInv,stmEmp;
//        java.sql.ResultSet rstInv,rstEmp;
//        int intCodItm=0;
//        try
//        {
//            stmEmp = conInv.createStatement();
//            stmInv = conInv.createStatement();
//            strSql = "";
//            strSql = " Select co_emp from tbm_emp where  st_reg = 'A' order by co_emp ";
//            rstEmp = stmEmp.executeQuery(strSql);
//            char chrEst,chrItmSer,chrIvaCom,chrIvaVta;
//            while (rstEmp.next())
//            {
//                strSql = "";
//                strSql = "select max(co_itm)+1 as co_itm from tbm_inv where co_emp = " + rstEmp.getString("co_emp") +" and st_reg = 'A'";
//                rstInv = stmInv.executeQuery(strSql);
//                if (rstInv.next())
//                    intCodItm = rstInv.getInt("co_itm");
//                rstInv.close();
//                 
//                    strSql = "";
//                    strSql = strSql + "INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm,  co_uni";
//                    strSql = strSql + ", st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
//                    strSql = strSql + ", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng, fe_ing)";
//                   
//                   strSql = strSql + " select "+rstEmp.getString("co_emp")+", "+intCodItm+" ,  REPLACE (tx_codalt, '"+Ter+"' , 'S'),REPLACE (tx_codalt, '"+Ter+"' , 'S'), tx_nomitm, co_uni , st_ser, nd_cosuni,0, nd_prevta1"; 
//                   strSql = strSql + ",0,0, 0, 0,0, st_ivacom ,st_ivaven, '','', st_reg,9, '2006-10-28' from tbm_inv where co_emp=1 and co_itm="+intCodIt;
//
//                   //System.out.println("OK......"+strSql);
//                   
//                    pstInv = conInv.prepareStatement(strSql);
//                    pstInv.executeUpdate();
//                    
//                    strSql = "";
//                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + rstEmp.getInt("co_emp") +","+ intCodItm + ")";
//                    pstInv = conInv.prepareStatement(strSql);
//                    pstInv.executeUpdate();
//                    
//                    //Creacion de Inventario Bodega                    
//                    strSql = " Select co_bod from tbm_bod where co_emp ="+ rstEmp.getInt("co_emp") +" and st_reg='A' and co_bod not in " +
//                             " (select co_bod from tbm_invbod where co_emp ="+ rstEmp.getInt("co_emp") +" and co_itm ="+ intCodItm+") " +
//                             " order by co_bod ";
//                    java.sql.ResultSet rst = stmInv.executeQuery(strSql);                    
//                    while(rst.next()){
//                        if (!creaItemenBodega(conInv,rstEmp.getInt("co_emp"),intCodItm,rst.getInt("co_bod"))){
//                            conInv.rollback();
//                            conInv.close();
//                            return false;
//                        }
//                    }  
//                    rst.close();
//                    stmInv.close();
//                    
//
//            }
//        }catch(SQLException evt)  {
//            try{
//                conInv.rollback();
//                conInv.close();
//            }catch(SQLException Evts){
//                objUti.mostrarMsgErr_F1(this, evt);
//                return false;
//            }                        
//            objUti.mostrarMsgErr_F1(this, evt);
//            return false;
//        } catch(Exception evt){
//            objUti.mostrarMsgErr_F1(this, evt);
//            return false;
//        }
//        return true;
//    } 
//       
//    
//      public boolean creaItemenBodega(java.sql.Connection con,int intCodEmp,int intCodItm, int intCodBod){
//        
//        try{                           
//            String strSQL = " Insert into tbm_invbod  (co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
//                            " values( " + intCodEmp + ", "+  intCodBod + ", "+  intCodItm + ", 0, 0, 0)"; //stock actual, minimo y maximo==0  
//            java.sql.PreparedStatement pstInvBod = con.prepareStatement(strSQL);
//           // System.out.println(strSQL);
//            pstInvBod.executeUpdate();                           
//         }catch(java.sql.SQLException Evt){
//                try{
//                        con.rollback();
//                        con.close();
//                   }catch(java.sql.SQLException Evts){
//                        objUti.mostrarMsgErr_F1(this, Evts);
//                   }
//                   objUti.mostrarMsgErr_F1(this, Evt); 
//                   return false;
//            }catch(Exception Evt){
//                objUti.mostrarMsgErr_F1(this, Evt);
//                return false;
//            }       
//        return true;
//     }    
//    
//    
//      
      
      
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

         Configura_ventana_consulta();
         

        if(intEst==1){
            
         objTooBar.consultar();
         
         objTooBar.setEstado('w');
         
        }
        
          
        
        
    }//GEN-LAST:event_formInternalFrameOpened

    
      public void BuscarItem(String campo,String strBusqueda,int tipo){
         objVenConItm.setTitle("Listado de Items");         
         if (objVenConItm.buscar(campo, strBusqueda ))
        {
                txtCodItm.setText(objVenConItm.getValueAt(1) );
                txtCodAlt.setText(objVenConItm.getValueAt(2) );
                txtCodAlt2.setText(objVenConItm.getValueAt(2) );
                txtNomItm.setText(objVenConItm.getValueAt(3) );
        }
        else
        {     objVenConItm.setCampoBusqueda(tipo);
              objVenConItm.cargarDatos();
              objVenConItm.show();
             if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE)
            {
                txtCodItm.setText(objVenConItm.getValueAt(1) );
                txtCodAlt.setText(objVenConItm.getValueAt(2) );
                txtCodAlt2.setText(objVenConItm.getValueAt(2) );
                txtNomItm.setText(objVenConItm.getValueAt(3) );
             }
        }
     }
     
    
        
public void BuscarMarca(String campo,String strBusqueda,int tipo){
         objVenConMar.setTitle("Listado de Marca");   
        if (objVenConMar.buscar(campo, strBusqueda ))
        {
             txtCodMar.setText(objVenConMar.getValueAt(1) );
             txtMar.setText(objVenConMar.getValueAt(3) );
        }
        else
        {     objVenConMar.setCampoBusqueda(tipo);
              objVenConMar.cargarDatos();
              objVenConMar.show();
             if (objVenConMar.getSelectedButton()==objVenConMar.INT_BUT_ACE)
            {
                txtCodMar.setText(objVenConMar.getValueAt(1) );
                txtMar.setText(objVenConMar.getValueAt(3) );
             }
              else{
                    txtCodMar.setText(strCodMar);
                    txtMar.setText(strDesLarMar);
                  }
              
        }
     }
    
////public void BuscarGrupo(String campo,String strBusqueda,int tipo){
////         objVenConGrp.setTitle("Listado de Linea");   
////        if (objVenConGrp.buscar(campo, strBusqueda ))
////        {
////              txtCodGrp.setText(objVenConGrp.getValueAt(1) );
////              txtGrp.setText(objVenConGrp.getValueAt(3) );
////        }
////        else
////        {     objVenConGrp.setCampoBusqueda(tipo);
////              objVenConGrp.cargarDatos();
////              objVenConGrp.show();
////             if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE)
////            {
////                 txtCodGrp.setText(objVenConGrp.getValueAt(1) );
////                 txtGrp.setText(objVenConGrp.getValueAt(3) );
////             }
////              else{
////                    txtCodGrp.setText(strCodGrp);
////                    txtGrp.setText(strDesLarGrp);
////                  }
////        }
////     }
////    
////public void BuscarLinea(String campo,String strBusqueda,int tipo){
////         objVenConLin.setTitle("Listado de Linea");   
////        if (objVenConLin.buscar(campo, strBusqueda ))
////        {
////               txtCodLin.setText(objVenConLin.getValueAt(1) );
////               txtLin.setText(objVenConLin.getValueAt(3) );
////        }
////        else
////        {     objVenConLin.setCampoBusqueda(tipo);
////              objVenConLin.cargarDatos();
////              objVenConLin.show();
////             if (objVenConLin.getSelectedButton()==objVenConLin.INT_BUT_ACE)
////            {
////                 txtCodLin.setText(objVenConLin.getValueAt(1) );
////                 txtLin.setText(objVenConLin.getValueAt(3) );
////             }
////              else{
////                    txtCodLin.setText(strCodLin);
////                    txtLin.setText(strDesLarLin);
////                  }
////        }
////     }

public void BuscarMat(String campo,String strBusqueda,int tipo){
         objVenConMat.setTitle("Listado de Material");   
        if (objVenConMat.buscar(campo, strBusqueda ))
        {
               txtCodMat.setText(objVenConMat.getValueAt(1) );
               txtMat.setText(objVenConMat.getValueAt(3) );
        }
        else
        {     objVenConMat.setCampoBusqueda(tipo);
              objVenConMat.cargarDatos();
              objVenConMat.show();
             if (objVenConMat.getSelectedButton()==objVenConMat.INT_BUT_ACE)
            {
                 txtCodMat.setText(objVenConMat.getValueAt(1) );
                 txtMat.setText(objVenConMat.getValueAt(3) );
             }
              else{
                    txtCodMat.setText(strCodMat);
                    txtMat.setText(strDesLarMat);
                  }
        }
     }

public void BuscarRefG(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConRefGen.setTitle("Listado de Referencias Generales");   
        if (objVenConRefGen.buscar(campo, strBusqueda ))
        {
               txtCodLinea.setText(objVenConRefGen.getValueAt(1) );
               txtLinea.setText(objVenConRefGen.getValueAt(3) );
        }
        else
        {     objVenConRefGen.setCampoBusqueda(tipo);
              objVenConRefGen.cargarDatos();
              objVenConRefGen.show();
             if (objVenConRefGen.getSelectedButton()==objVenConRefGen.INT_BUT_ACE)
            {
                 txtCodLinea.setText(objVenConRefGen.getValueAt(1) );
                 txtLinea.setText(objVenConRefGen.getValueAt(3) );
             }
              else{
                    txtCodLinea.setText(strCodRefG);
                    txtLinea.setText(strDesLarRefG);
                  }
        }
     }

public void BuscarLinea(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConLinea.setTitle("Listado de Lineas");   
        if (objVenConLinea.buscar(campo, strBusqueda ))
        {
               txtCodLinea.setText(objVenConLinea.getValueAt(1) );
               txtLinea.setText(objVenConLinea.getValueAt(3) );
        }
        else
        {     objVenConLinea.setCampoBusqueda(tipo);
              objVenConLinea.cargarDatos();
              objVenConLinea.show();
             if (objVenConLinea.getSelectedButton()==objVenConLinea.INT_BUT_ACE)
            {
                 txtCodLinea.setText(objVenConLinea.getValueAt(1) );
                 txtLinea.setText(objVenConLinea.getValueAt(3) );
             }
              else{
                    txtCodLinea.setText(strCodRefG);
                    txtLinea.setText(strDesLarRefG);
                  }
        }
     }

public void BuscarRefD(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConRefDeta.setTitle("Listado de Referencias Detalladas");   
        if (objVenConRefDeta.buscar(campo, strBusqueda ))
        {
               txtCodCla.setText(objVenConRefDeta.getValueAt(1) );
               txtCla.setText(objVenConRefDeta.getValueAt(3) );
        }
        else
        {     objVenConRefDeta.setCampoBusqueda(tipo);
              objVenConRefDeta.cargarDatos();
              objVenConRefDeta.show();
             if (objVenConRefDeta.getSelectedButton()==objVenConRefDeta.INT_BUT_ACE)
            {
                 txtCodCla.setText(objVenConRefDeta.getValueAt(1) );
                 txtCla.setText(objVenConRefDeta.getValueAt(3) );
             }
              else{
                    txtCodCla.setText(strCodRefD);
                    txtCla.setText(strDesLarRefD);
                  }
        }
     }

public void BuscarCompra(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConCompra.setTitle("Listado de Compras");   
        if (objVenConCompra.buscar(campo, strBusqueda ))
        {
               txtCodCompr.setText(objVenConCompra.getValueAt(1) );
               txtCompr.setText(objVenConCompra.getValueAt(3) );
        }
        else
        {     objVenConCompra.setCampoBusqueda(tipo);
              objVenConCompra.cargarDatos();
              objVenConCompra.show();
             if (objVenConCompra.getSelectedButton()==objVenConCompra.INT_BUT_ACE)
            {
                 txtCodCompr.setText(objVenConCompra.getValueAt(1) );
                 txtCompr.setText(objVenConCompra.getValueAt(3) );
             }
              else{
                    txtCodCompr.setText(strCodCompr);
                    txtCompr.setText(strDesLarCompr);
                  }
        }
     }

public void BuscarCategoria(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConCategoria.setTitle("Listado de Categoria");   
        if (objVenConCategoria.buscar(campo, strBusqueda ))
        {
               txtCodCategoria.setText(objVenConCategoria.getValueAt(1) );
               txtCategoria.setText(objVenConCategoria.getValueAt(3) );
        }
        else
        {     objVenConCategoria.setCampoBusqueda(tipo);
              objVenConCategoria.cargarDatos();
              objVenConCategoria.show();
             if (objVenConCategoria.getSelectedButton()==objVenConCategoria.INT_BUT_ACE)
            {
                 txtCodCategoria.setText(objVenConCategoria.getValueAt(1) );
                 txtCategoria.setText(objVenConCategoria.getValueAt(3) );
             }
              else{
                    txtCodCategoria.setText(strCodCategoria);
                    txtCategoria.setText(strDesLarCategoria);
                  }
        }
}

public void BuscarCategoriaCompra(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConCategoriaCompra.setTitle("Listado de Categoria Compra");   
        if (objVenConCategoriaCompra.buscar(campo, strBusqueda ))
        {
               txtCodCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(1) );
               txtCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(3) );
        }
        else
        {     objVenConCategoriaCompra.setCampoBusqueda(tipo);
              objVenConCategoriaCompra.cargarDatos();
              objVenConCategoriaCompra.show();
             if (objVenConCategoriaCompra.getSelectedButton()==objVenConCategoriaCompra.INT_BUT_ACE)
            {
                 txtCodCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(1) );
                 txtCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(3) );
             }
              else{
                    txtCodCategoriaCompra.setText(strCodCategoriaCompra);
                    txtCategoriaCompra.setText(strDesLarCategoriaCompra);
                  }
        }
}

public void BuscarLineaCompra(String campo,String strBusqueda,int tipo)//20052026
{
         objVenConLineaCompra.setTitle("Listado de Linea Compra");   
        if (objVenConLineaCompra.buscar(campo, strBusqueda ))
        {
               txtCodLineaCompra.setText(objVenConLineaCompra.getValueAt(1) );
               txtLineaCompra.setText(objVenConLineaCompra.getValueAt(3) );
        }
        else
        {     objVenConLineaCompra.setCampoBusqueda(tipo);
              objVenConLineaCompra.cargarDatos();
              objVenConLineaCompra.show();
             if (objVenConLineaCompra.getSelectedButton()==objVenConLineaCompra.INT_BUT_ACE)
            {
                 txtCodLineaCompra.setText(objVenConLineaCompra.getValueAt(1) );
                 txtLineaCompra.setText(objVenConLineaCompra.getValueAt(3) );
             }
              else{
                    txtCodLineaCompra.setText(strCodLineaCompra);
                    txtLineaCompra.setText(strDesLarLineaCompra);
                  }
        }
}

public void BuscarClaseCompra(String campo,String strBusqueda,int tipo)//20052026
{
         objVenConClaseCompra.setTitle("Listado de Clase Compra");   
        if (objVenConClaseCompra.buscar(campo, strBusqueda ))
        {
               txtCodClaseCompra.setText(objVenConClaseCompra.getValueAt(1) );
               txtClaseCompra.setText(objVenConClaseCompra.getValueAt(3) );
        }
        else
        {     objVenConClaseCompra.setCampoBusqueda(tipo);
              objVenConClaseCompra.cargarDatos();
              objVenConClaseCompra.show();
             if (objVenConClaseCompra.getSelectedButton()==objVenConClaseCompra.INT_BUT_ACE)
            {
                 txtCodClaseCompra.setText(objVenConClaseCompra.getValueAt(1) );
                 txtClaseCompra.setText(objVenConClaseCompra.getValueAt(3) );
             }
              else{
                    txtCodLineaCompra.setText(strCodClaseCompra);
                    txtLineaCompra.setText(strDesLarClaseCompra);
                  }
        }
}

public void BuscarControl(String campo,String strBusqueda,int tipo)
{
    objVenControl.setTitle("Listado de Control");   
    if (objVenControl.buscar(campo, strBusqueda ))
    {
           txtCodControl.setText(objVenControl.getValueAt(1) );
           txtControl.setText(objVenControl.getValueAt(3) );
    }
    else
    {     objVenControl.setCampoBusqueda(tipo);
          objVenControl.cargarDatos();
          objVenControl.show();
        if (objVenControl.getSelectedButton()==objVenControl.INT_BUT_ACE)
        {
             txtCodControl.setText(objVenControl.getValueAt(1) );
             txtControl.setText(objVenControl.getValueAt(3) );
        }
        else
        {
            txtCodControl.setText(strCodControl);
            txtControl.setText(strDesLarControl);
        }
    }
}

public void BuscarABCTuval(String campo,String strBusqueda,int tipo)
{
    objVenABCTuval.setTitle("Listado de ABC de TUVAL");   
    if (objVenABCTuval.buscar(campo, strBusqueda ))
    {
           txtCodAbcTUV.setText(objVenABCTuval.getValueAt(1) );
           txtAbcTUV.setText(objVenABCTuval.getValueAt(2) );
    }
    else
    {     objVenABCTuval.setCampoBusqueda(tipo);
          objVenABCTuval.cargarDatos();
          objVenABCTuval.show();
        if (objVenABCTuval.getSelectedButton()==objVenABCTuval.INT_BUT_ACE)
        {
             txtCodAbcTUV.setText(objVenABCTuval.getValueAt(1) );
             txtAbcTUV.setText(objVenABCTuval.getValueAt(2) );
        }
        else
        {
            txtCodAbcTUV.setText(strCodAbcTUV);
            txtAbcTUV.setText(strDesLarAbcTUV);
        }
    }
}
public void BuscarABCFre(String campo,String strBusqueda,int tipo)
{
    objVenABCFre.setTitle("Listado de ABC de FRECUENCIA");   
    if (objVenABCFre.buscar(campo, strBusqueda ))
    {
           txtCodAbcFRE.setText(objVenABCFre.getValueAt(1) );
           txtAbcFRE.setText(objVenABCFre.getValueAt(2) );
    }
    else
    {     objVenABCFre.setCampoBusqueda(tipo);
          objVenABCFre.cargarDatos();
          objVenABCFre.show();
        if (objVenABCFre.getSelectedButton()==objVenABCFre.INT_BUT_ACE)
        {
             txtCodAbcFRE.setText(objVenABCFre.getValueAt(1) );
             txtAbcFRE.setText(objVenABCFre.getValueAt(2) );
        }
        else
        {
            txtCodAbcFRE.setText(strCodAbcFRE);
            txtAbcFRE.setText(strDesLarAbcFRE);
        }
    }
}
public void BuscarABCPes(String campo,String strBusqueda,int tipo)
{
    objVenABCPeso.setTitle("Listado de ABC de PESO");   
    if (objVenABCPeso.buscar(campo, strBusqueda ))
    {
           txtCodAbcPES.setText(objVenABCPeso.getValueAt(1) );
           txtAbcPES.setText(objVenABCPeso.getValueAt(2) );
    }
    else
    {     objVenABCPeso.setCampoBusqueda(tipo);
          objVenABCPeso.cargarDatos();
          objVenABCPeso.show();
        if (objVenABCPeso.getSelectedButton()==objVenABCPeso.INT_BUT_ACE)
        {
             txtCodAbcPES.setText(objVenABCPeso.getValueAt(1) );
             txtAbcPES.setText(objVenABCPeso.getValueAt(2) );
        }
        else
        {
            txtCodAbcTUV.setText(strCodAbcPESO);
            txtAbcTUV.setText(strDesLarAbcPESO);
        }
    }
}
public void BuscarABCValor(String campo,String strBusqueda,int tipo)
{
    objVenABCValor.setTitle("Listado de ABC de Valor Venta");   
    if (objVenABCValor.buscar(campo, strBusqueda ))
    {
           txtCodAbcVAL.setText(objVenABCValor.getValueAt(1) );
           txtAbcVAL.setText(objVenABCValor.getValueAt(2) );
    }
    else
    {     objVenABCValor.setCampoBusqueda(tipo);
          objVenABCValor.cargarDatos();
          objVenABCValor.show();
        if (objVenABCValor.getSelectedButton()==objVenABCValor.INT_BUT_ACE)
        {
             txtCodAbcVAL.setText(objVenABCValor.getValueAt(1) );
             txtAbcVAL.setText(objVenABCValor.getValueAt(2) );
        }
        else
        {
            txtCodAbcVAL.setText(strCodAbcVALOR);
            txtAbcVAL.setText(strDesLarAbcVALOR);
        }
    }
}
public void BuscarABCClase(String campo,String strBusqueda,int tipo)
{
    objVenABCClas.setTitle("Listado de ABC de Clase");   
    if (objVenABCClas.buscar(campo, strBusqueda ))
    {
           txtCodAbcCLA.setText(objVenABCClas.getValueAt(1) );
           txtAbcCLA.setText(objVenABCClas.getValueAt(2) );
    }
    else
    {     objVenABCClas.setCampoBusqueda(tipo);
          objVenABCClas.cargarDatos();
          objVenABCClas.show();
        if (objVenABCClas.getSelectedButton()==objVenABCClas.INT_BUT_ACE)
        {
             txtCodAbcCLA.setText(objVenABCClas.getValueAt(1) );
             txtAbcCLA.setText(objVenABCClas.getValueAt(2) );
        }
        else
        {
            txtCodAbcCLA.setText(strCodAbcCLAS);
            txtAbcCLA.setText(strDesLarAbcCLAS);
        }
    }
}


public void BuscarClase(String campo,String strBusqueda,int tipo)//CHIQUI
{
         objVenConClase.setTitle("Listado de Clases");   
        if (objVenConClase.buscar(campo, strBusqueda ))
        {
               txtCodCla.setText(objVenConClase.getValueAt(1) );
               txtCla.setText(objVenConClase.getValueAt(3) );
        }
        else
        {     objVenConClase.setCampoBusqueda(tipo);
              objVenConClase.cargarDatos();
              objVenConClase.show();
             if (objVenConClase.getSelectedButton()==objVenConClase.INT_BUT_ACE)
            {
                 txtCodCla.setText(objVenConClase.getValueAt(1) );
                 txtCla.setText(objVenConClase.getValueAt(3) );
             }
              else{
                    txtCodCla.setText(strCodRefD);
                    txtCla.setText(strDesLarRefD);
                  }
        }
     }
    
public void BuscarUnidad(String campo,String strBusqueda,int tipo)
{
        objVenConUni.setTitle("Listado de Unidad de Medidas");   
        if (objVenConUni.buscar(campo, strBusqueda ))
        {
                txtCodUniMed.setText(objVenConUni.getValueAt(1) );
                txtUniMed.setText(objVenConUni.getValueAt(3) );
                txtCortaUniMed.setText(objVenConUni.getValueAt(2) );
        }
        else
        {     objVenConUni.setCampoBusqueda(tipo);
              objVenConUni.cargarDatos();
              objVenConUni.show();
             if (objVenConUni.getSelectedButton()==objVenConUni.INT_BUT_ACE)
            {
                txtCodUniMed.setText(objVenConUni.getValueAt(1) );
                txtUniMed.setText(objVenConUni.getValueAt(3) );
                txtCortaUniMed.setText(objVenConUni.getValueAt(2) );
             }
              else{
                    txtCodUniMed.setText(strCodUni);
                    txtUniMed.setText(strDesLarUni);
                    txtCortaUniMed.setText(objVenConUni.getValueAt(2) );
                  }
        }
     }
       
    
    
    
    /**Cerrar la aplicaciÃ³n*/
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtStkMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMaxActionPerformed
        txaObs1.requestFocus();
    }//GEN-LAST:event_txtStkMaxActionPerformed

    private void txtStkMaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMaxFocusLost

    }//GEN-LAST:event_txtStkMaxFocusLost

    private void txtStkMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMinActionPerformed
        txtStkMax.requestFocus();
    }//GEN-LAST:event_txtStkMinActionPerformed

    private void txtStkMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMinFocusLost

    }//GEN-LAST:event_txtStkMinFocusLost

    private void txaObs2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2FocusLost
        if (!txaObs2.getText().equals("")) {
            if (txaObs2.getText().length()>200 ){
                strMsg="El campo <<Observación 2>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(1);
                txaObs2.setText("");
                txaObs2.requestFocus();
            }
        }
    }//GEN-LAST:event_txaObs2FocusLost

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
        if (!txaObs1.getText().equals("")) {
            if (txaObs1.getText().length()>200 ){
                strMsg="El campo <<Observación 1>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(1);
                txaObs1.setText("");
                txaObs1.requestFocus();
            }
        }
    }//GEN-LAST:event_txaObs1FocusLost

    private void txtCosUni3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni3FocusLost

    }//GEN-LAST:event_txtCosUni3FocusLost

    private void txtCosUni3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni3ActionPerformed
        txtStkMin.requestFocus();
    }//GEN-LAST:event_txtCosUni3ActionPerformed

    private void txtCosUni2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni2ActionPerformed
        txtCosUni3.requestFocus();
    }//GEN-LAST:event_txtCosUni2ActionPerformed

    private void txtCosUni2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni2FocusLost

    }//GEN-LAST:event_txtCosUni2FocusLost

    private void txtCosUni1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni1ActionPerformed
        txtCosUni2.requestFocus();
    }//GEN-LAST:event_txtCosUni1ActionPerformed

    private void txtCosUni1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni1FocusLost

    }//GEN-LAST:event_txtCosUni1FocusLost

    private void butAbcTUVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbcTUVActionPerformed
        objVenABCTuval.setTitle("Listado de ABC de Tuval");
        objVenABCTuval.setCampoBusqueda(1);
        objVenABCTuval.show();
        if (objVenABCTuval.getSelectedButton()==objVenABCTuval.INT_BUT_ACE)
        {
            txtCodAbcTUV.setText(objVenABCTuval.getValueAt(1) );
            txtAbcTUV.setText(objVenABCTuval.getValueAt(2) );
        }
    }//GEN-LAST:event_butAbcTUVActionPerformed

    private void txtAbcTUVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcTUVActionPerformed
        txtAbcTUV.transferFocus();
    }//GEN-LAST:event_txtAbcTUVActionPerformed

    private void txtAbcTUVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcTUVFocusLost
        if (!txtAbcTUV.getText().equalsIgnoreCase(strDesLarAbcTUV))
        {
            if (txtAbcTUV.getText().equals(""))
            {
                txtCodAbcTUV.setText("");
                txtAbcTUV.setText("");
            }
            else
            {
                BuscarABCTuval("tx_nivpar",txtAbcTUV.getText(),2);
            }
        }
        else
        txtAbcTUV.setText(strDesLarAbcTUV);
    }//GEN-LAST:event_txtAbcTUVFocusLost

    private void txtAbcTUVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcTUVFocusGained
        strDesLarAbcTUV=txtAbcTUV.getText();
        txtAbcTUV.selectAll();
    }//GEN-LAST:event_txtAbcTUVFocusGained

    private void txtCodAbcTUVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAbcTUVActionPerformed
        // TODO add your handling code here:
        txtCodAbcTUV.transferFocus();
    }//GEN-LAST:event_txtCodAbcTUVActionPerformed

    private void txtCodAbcTUVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcTUVFocusLost
        if (!txtCodAbcTUV.getText().equalsIgnoreCase(strCodAbcTUV))
        {
            if (txtCodAbcTUV.getText().equals(""))
            {
                txtCodAbcTUV.setText("");
                txtAbcTUV.setText("");
            }
            else
            {
                BuscarABCTuval("co_nivpar",txtCodAbcTUV.getText(),0);
            }
        }
        else
        txtCodAbcTUV.setText(strCodAbcTUV);
    }//GEN-LAST:event_txtCodAbcTUVFocusLost

    private void txtCodAbcTUVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcTUVFocusGained
        strCodAbcTUV=txtCodAbcTUV.getText();
        txtCodAbcTUV.selectAll();
    }//GEN-LAST:event_txtCodAbcTUVFocusGained

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        objVenControl.setTitle("Listado de Control");
        objVenControl.setCampoBusqueda(1);
        objVenControl.show();
        if (objVenControl.getSelectedButton()==objVenControl.INT_BUT_ACE)
        {
            txtCodControl.setText(objVenControl.getValueAt(1) );
            txtControl.setText(objVenControl.getValueAt(3) );
        }
    }//GEN-LAST:event_butConActionPerformed

    private void txtControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtControlActionPerformed

    private void txtControlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtControlFocusLost
        // TODO add your handling code here:
        if (!txtControl.getText().equalsIgnoreCase(strDesLarControl))
        {
            if (txtControl.getText().equals(""))
            {
                txtCodControl.setText("");
                txtControl.setText("");
            }
            else
            {
                BuscarControl("tx_desLar",txtControl.getText(),2);
            }
        }
        else
        txtControl.setText(strDesLarControl);
    }//GEN-LAST:event_txtControlFocusLost

    private void txtControlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtControlFocusGained
        // TODO add your handling code here:
        strDesLarControl=txtControl.getText();
        txtControl.selectAll();
    }//GEN-LAST:event_txtControlFocusGained

    private void txtCodControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodControlActionPerformed

    private void txtCodControlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodControlFocusLost
        // TODO add your handling code here:
        if (!txtCodControl.getText().equalsIgnoreCase(strCodControl))
        {
            if (txtCodControl.getText().equals(""))
            {
                txtCodControl.setText("");
                txtControl.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarControl("co_reg",txtCodControl.getText(),0);
            }
        }
        else
        txtCodControl.setText(strCodControl);

    }//GEN-LAST:event_txtCodControlFocusLost

    private void txtCodControlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodControlFocusGained
        // TODO add your handling code here:
        // TODO add your handling code here:
        strCodControl=txtCodControl.getText();
        txtCodControl.selectAll();
    }//GEN-LAST:event_txtCodControlFocusGained

    private void butCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCategoriaActionPerformed
        // TODO add your handling code here:
        objVenConCategoria.setTitle("Listado de Categoria");
        objVenConCategoria.setCampoBusqueda(1);
        objVenConCategoria.show();
        if (objVenConCategoria.getSelectedButton()==objVenConCategoria.INT_BUT_ACE)
        {
            txtCodCategoria.setText(objVenConCategoria.getValueAt(1) );
            txtCategoria.setText(objVenConCategoria.getValueAt(3) );
        }
    }//GEN-LAST:event_butCategoriaActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
        txtCategoria.transferFocus();
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void txtCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCategoriaFocusLost
        // TODO add your handling code here:
        if (!txtCategoria.getText().equalsIgnoreCase(strDesLarCategoria))
        {
            if (txtCategoria.getText().equals(""))
            {
                txtCodCategoria.setText("");
                txtCategoria.setText("");
            }
            else
            {
                BuscarCategoria("tx_desLar",txtCategoria.getText(),2);
            }
        }
        else
        txtCategoria.setText(strDesLarCategoria);
    }//GEN-LAST:event_txtCategoriaFocusLost

    private void txtCategoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCategoriaFocusGained
        // TODO add your handling code here:
        strDesLarCategoria=txtCategoria.getText();
        txtCategoria.selectAll();
    }//GEN-LAST:event_txtCategoriaFocusGained

    private void txtCodCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCategoriaActionPerformed
        txtCodCategoria.transferFocus();
    }//GEN-LAST:event_txtCodCategoriaActionPerformed

    private void txtCodCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCategoriaFocusLost
        // TODO add your handling code here:
        if (!txtCodCategoria.getText().equalsIgnoreCase(strCodCategoria))
        {
            if (txtCodCategoria.getText().equals(""))
            {
                txtCodCategoria.setText("");
                txtCategoria.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarCategoria("co_reg",txtCodCategoria.getText(),0);
            }
        }
        else
        txtCodCategoria.setText(strCodCategoria);

    }//GEN-LAST:event_txtCodCategoriaFocusLost

    private void txtCodCategoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCategoriaFocusGained
        // TODO add your handling code here:
        strCodCategoria=txtCodCategoria.getText();
        txtCodCategoria.selectAll();
    }//GEN-LAST:event_txtCodCategoriaFocusGained

    private void butComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComprActionPerformed
        // TODO add your handling code here:
        objVenConCompra.setTitle("Listado de Compras");
        objVenConCompra.setCampoBusqueda(1);
        objVenConCompra.show();
        if (objVenConCompra.getSelectedButton()==objVenConCompra.INT_BUT_ACE)
        {
            txtCodCompr.setText(objVenConCompra.getValueAt(1) );
            txtCompr.setText(objVenConCompra.getValueAt(3) );
        }
    }//GEN-LAST:event_butComprActionPerformed

    private void txtComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComprActionPerformed
        txtCompr.transferFocus();
    }//GEN-LAST:event_txtComprActionPerformed

    private void txtComprFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComprFocusLost
        // TODO add your handling code here:
        if (!txtCompr.getText().equalsIgnoreCase(strDesLarCompr))
        {
            if (txtCompr.getText().equals(""))
            {
                txtCodCompr.setText("");
                txtCompr.setText("");
            }
            else
            {
                BuscarCompra("tx_desLar",txtCompr.getText(),2);
            }
        }
        else
        txtCompr.setText(strDesLarCompr);
    }//GEN-LAST:event_txtComprFocusLost

    private void txtComprFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComprFocusGained
        strDesLarCompr=txtCompr.getText();
        txtCompr.selectAll();
    }//GEN-LAST:event_txtComprFocusGained

    private void txtCodComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodComprActionPerformed
        txtCodCompr.transferFocus();
    }//GEN-LAST:event_txtCodComprActionPerformed

    private void txtCodComprFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComprFocusLost
        // TODO add your handling code here:
        if (!txtCodCompr.getText().equalsIgnoreCase(strCodCompr))
        {
            if (txtCodCompr.getText().equals(""))
            {
                txtCodCompr.setText("");
                txtCompr.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarCompra("co_reg",txtCodCompr.getText(),0);
            }
        }
        else
        txtCodCompr.setText(strCodCompr);
    }//GEN-LAST:event_txtCodComprFocusLost

    private void txtCodComprFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComprFocusGained
        // TODO add your handling code here:
        strCodCompr=txtCodCompr.getText();
        txtCodCompr.selectAll();
    }//GEN-LAST:event_txtCodComprFocusGained

    private void txtCodTamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTamaActionPerformed

    private void txtCodTamaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTamaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTamaFocusLost

    private void cboEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_cboEstActionPerformed

    private void butClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaActionPerformed
        // TODO add your handling code here:
        objVenConClase.setTitle("Listado de Clases");
        objVenConClase.setCampoBusqueda(1);
        objVenConClase.show();
        if (objVenConClase.getSelectedButton()==objVenConClase.INT_BUT_ACE)
        {
            txtCodCla.setText(objVenConClase.getValueAt(1) );
            txtCla.setText(objVenConClase.getValueAt(3) );
        }
    }//GEN-LAST:event_butClaActionPerformed

    private void butLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLineaActionPerformed
        // TODO add your handling code here:
        objVenConLinea.setTitle("Listado de Lineas");
        objVenConLinea.setCampoBusqueda(1);
        objVenConLinea.show();
        if (objVenConLinea.getSelectedButton()==objVenConLinea.INT_BUT_ACE)
        {
            txtCodLinea.setText(objVenConLinea.getValueAt(1) );
            txtLinea.setText(objVenConLinea.getValueAt(3) );
        }
    }//GEN-LAST:event_butLineaActionPerformed

    private void butUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUniMedActionPerformed

        objVenConUni.setTitle("Listado de Unidad de Medidas");
        objVenConUni.setCampoBusqueda(1);
        objVenConUni.show();
        objVenConUni.cargarDatos();
        if (objVenConUni.getSelectedButton()==objVenConUni.INT_BUT_ACE)
        {
            txtCodUniMed.setText(objVenConUni.getValueAt(1) );
            txtUniMed.setText(objVenConUni.getValueAt(3) );
            txtCortaUniMed.setText(objVenConUni.getValueAt(2));

        }

    }//GEN-LAST:event_butUniMedActionPerformed

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        // FndGrupoVarios("",0,4);

        objVenConMar.setTitle("Listado de Marca");
        objVenConMar.setCampoBusqueda(1);
        objVenConMar.show();
        if (objVenConMar.getSelectedButton()==objVenConMar.INT_BUT_ACE)
        {
            txtCodMar.setText(objVenConMar.getValueAt(1) );
            txtMar.setText(objVenConMar.getValueAt(3) );
        }

    }//GEN-LAST:event_butMarActionPerformed

    private void butMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMatActionPerformed
        // TODO add your handling code here:
        objVenConMat.setTitle("Listado de Material");
        objVenConMat.setCampoBusqueda(1);
        objVenConMat.show();
        if (objVenConMat.getSelectedButton()==objVenConMat.INT_BUT_ACE)
        {
            txtCodMat.setText(objVenConMat.getValueAt(1) );
            txtMat.setText(objVenConMat.getValueAt(3) );
        }
    }//GEN-LAST:event_butMatActionPerformed

    private void txtMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatActionPerformed
        // TODO add your handling code here:
        txtMat.transferFocus();

    }//GEN-LAST:event_txtMatActionPerformed

    private void txtMatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatFocusLost
        // TODO add your handling code here:
        if (!txtMat.getText().equalsIgnoreCase(strDesLarMat))
        {
            if (txtMat.getText().equals(""))
            {
                txtCodMat.setText("");
                txtMat.setText("");
            }
            else
            {
                BuscarMat("tx_desLar",txtMat.getText(),2);
            }
        }
        else
        txtMat.setText(strDesLarMat);
    }//GEN-LAST:event_txtMatFocusLost

    private void txtMatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatFocusGained
        // TODO add your handling code here:
        strDesLarMat=txtMat.getText();
        txtMat.selectAll();
    }//GEN-LAST:event_txtMatFocusGained

    private void txtCodMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMatActionPerformed
        // TODO add your handling code here:
        txtCodMat.transferFocus();
    }//GEN-LAST:event_txtCodMatActionPerformed

    private void txtCodMatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMatFocusLost
        // TODO add your handling code here:

        if (!txtCodMat.getText().equalsIgnoreCase(strCodMat))
        {
            if (txtCodMat.getText().equals(""))
            {
                txtCodMat.setText("");
                txtMat.setText("");
            }
            else
            {
                BuscarMat("co_reg",txtCodMat.getText(),0);
            }
        }
        else
        txtCodMat.setText(strCodMat);
    }//GEN-LAST:event_txtCodMatFocusLost

    private void txtCodMatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMatFocusGained
        // TODO add your handling code here:
        strCodMat=txtCodMat.getText();
        txtCodMat.selectAll();
    }//GEN-LAST:event_txtCodMatFocusGained

    private void txtMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarActionPerformed
        //  FndGrupoVarios(txtMar.getText(),2,4);

        txtMar.transferFocus();
    }//GEN-LAST:event_txtMarActionPerformed

    private void txtMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarFocusLost

        if (!txtMar.getText().equalsIgnoreCase(strDesLarMar))
        {
            if (txtMar.getText().equals(""))
            {
                txtCodMar.setText("");
                txtMar.setText("");
            }
            else
            {
                BuscarMarca("a.tx_desLar",txtMar.getText(),2);
            }
        }
        else
        txtMar.setText(strDesLarMar);
    }//GEN-LAST:event_txtMarFocusLost

    private void txtMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarFocusGained
        // TODO add your handling code here:
        strDesLarMar=txtMar.getText();
        txtMar.selectAll();
    }//GEN-LAST:event_txtMarFocusGained

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
        // FndGrupoVarios(txtCodMar.getText(),1,4);

        txtCodMar.transferFocus();
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost

        if (!txtCodMar.getText().equalsIgnoreCase(strCodMar))
        {
            if (txtCodMar.getText().equals(""))
            {
                txtCodMar.setText("");
                txtMar.setText("");
            }
            else
            {
                BuscarMarca("a.co_reg",txtCodMar.getText(),0);
            }
        }
        else
        txtCodMar.setText(strCodMar);
    }//GEN-LAST:event_txtCodMarFocusLost

    private void txtCodMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusGained
        // TODO add your handling code here:
        strCodMar=txtCodMar.getText();
        txtCodMar.selectAll();
    }//GEN-LAST:event_txtCodMarFocusGained

    private void txtCodUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUniMedActionPerformed
        // FndGrupoVarios(txtCodUniMed.getText(),1,5);

        txtCodUniMed.transferFocus();
    }//GEN-LAST:event_txtCodUniMedActionPerformed

    private void txtCodUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUniMedFocusLost

        if (!txtCodUniMed.getText().equalsIgnoreCase(strCodUni))
        {
            if (txtCodUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtUniMed.setText("");
            }
            else
            {
                BuscarUnidad("a.co_reg",txtCodUniMed.getText(),0);
            }
        }
        else
        txtCodUniMed.setText(strCodUni);
    }//GEN-LAST:event_txtCodUniMedFocusLost

    private void txtCodUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUniMedFocusGained
        // TODO add your handling code here:
        strCodUni=txtCodUniMed.getText();
        txtCodUniMed.selectAll();
    }//GEN-LAST:event_txtCodUniMedFocusGained

    private void txtUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUniMedActionPerformed
        //  FndGrupoVarios(txtCodMar.getText(),1,2);

        txtUniMed.transferFocus();
    }//GEN-LAST:event_txtUniMedActionPerformed

    private void txtUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUniMedFocusLost
        /*  if (!txtUniMed.getText().equals("")) {
            if (txtUniMed.getText().length()>30){
                strMsg="El campo <<DescripciÃ³n de Unidad de Medida>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtUniMed.setText("");
                tabGenInv.setSelectedIndex(0);
                txtUniMed.requestFocus();
            }
        }
        if (txtUniMed.getText().equals("")) {
            txtCodUniMed.setText("");
        }
        */

        if (!txtUniMed.getText().equalsIgnoreCase(strDesLarUni))
        {
            if (txtUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtUniMed.setText("");
            }
            else
            {
                BuscarUnidad("a.tx_desLar",txtUniMed.getText(),2);
            }
        }
        else
        txtUniMed.setText(strDesLarUni);
    }//GEN-LAST:event_txtUniMedFocusLost

    private void txtUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUniMedFocusGained
        // TODO add your handling code here:
        strDesLarUni=txtUniMed.getText();
        txtUniMed.selectAll();
    }//GEN-LAST:event_txtUniMedFocusGained

    private void txtLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineaActionPerformed
        // TODO add your handling code here:
        txtLinea.transferFocus();
    }//GEN-LAST:event_txtLineaActionPerformed

    private void txtLineaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineaFocusLost
        // TODO add your handling code here:
        if (!txtLinea.getText().equalsIgnoreCase(strDesLarRefG))
        {
            if (txtLinea.getText().equals(""))
            {
                txtCodLinea.setText("");
                txtLinea.setText("");
            }
            else
            {
                //BuscarRefG("tx_desLar",txtRefG.getText(),2);
                BuscarLinea("tx_desLar",txtLinea.getText(),2);
            }
        }
        else
        txtLinea.setText(strDesLarRefG);
    }//GEN-LAST:event_txtLineaFocusLost

    private void txtLineaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineaFocusGained
        // TODO add your handling code here:
        strDesLarRefG=txtLinea.getText();
        txtLinea.selectAll();
    }//GEN-LAST:event_txtLineaFocusGained

    private void txtClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaActionPerformed
        // TODO add your handling code here:
        txtCla.transferFocus();
    }//GEN-LAST:event_txtClaActionPerformed

    private void txtClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaFocusLost
        // TODO add your handling code here:
        if (!txtCla.getText().equalsIgnoreCase(strDesLarRefD))
        {
            if (txtCla.getText().equals(""))
            {
                txtCodCla.setText("");
                txtCla.setText("");
            }
            else
            {
                ////               BuscarRefD("tx_desLar",txtRefD.getText(),2);
                BuscarClase("tx_desLar",txtCla.getText(),2);
            }
        }
        else
        txtCla.setText(strDesLarRefD);
    }//GEN-LAST:event_txtClaFocusLost

    private void txtClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaFocusGained
        // TODO add your handling code here:
        strDesLarRefD=txtCla.getText();
        txtCla.selectAll();
    }//GEN-LAST:event_txtClaFocusGained

    private void txtCodClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClaActionPerformed
        // TODO add your handling code here:
        txtCodCla.transferFocus();
    }//GEN-LAST:event_txtCodClaActionPerformed

    private void txtCodClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusLost
        // TODO add your handling code here:
        if (!txtCodCla.getText().equalsIgnoreCase(strCodRefD))
        {
            if (txtCodCla.getText().equals(""))
            {
                txtCodCla.setText("");
                txtCla.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarClase("co_reg",txtCodCla.getText(),0);
            }
        }
        else
        txtCodCla.setText(strCodRefD);
    }//GEN-LAST:event_txtCodClaFocusLost

    private void txtCodClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusGained
        // TODO add your handling code here:
        strCodRefD=txtCodCla.getText();
        txtCodCla.selectAll();
    }//GEN-LAST:event_txtCodClaFocusGained

    private void txtCodLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLineaActionPerformed
        // TODO add your handling code here:
        txtCodLinea.transferFocus();
    }//GEN-LAST:event_txtCodLineaActionPerformed

    private void txtCodLineaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLineaFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (!txtCodLinea.getText().equalsIgnoreCase(strCodRefG))
        {
            if (txtCodLinea.getText().equals(""))
            {
                txtCodLinea.setText("");
                txtLinea.setText("");
            }
            else
            {
                // BuscarRefG("co_reg",txtCodRefG.getText(),0);
                BuscarLinea("co_reg",txtCodLinea.getText(),0);
            }
        }
        else
        txtCodLinea.setText(strCodRefG);
    }//GEN-LAST:event_txtCodLineaFocusLost

    private void txtCodLineaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLineaFocusGained
        // TODO add your handling code here:
        strCodRefG=txtCodLinea.getText();
        txtCodLinea.selectAll();
    }//GEN-LAST:event_txtCodLineaFocusGained

    private void txtCortaUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCortaUniMedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCortaUniMedActionPerformed

    private void txtCortaUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCortaUniMedFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCortaUniMedFocusLost

    private void txtCortaUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCortaUniMedFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCortaUniMedFocusGained

    private void txtCodGPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGPFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodGPFocusGained

    private void chkpermodnomvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkpermodnomvenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkpermodnomvenActionPerformed

    private void chkpermodnomcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkpermodnomcomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkpermodnomcomActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        // TODO add your handling code here:
        if (txtCodAlt.isEditable() || txtCodAlt.isEnabled()){
            String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
            if (strTerItm.equals("I") || strTerItm.equals("S")) {
                if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                    txtCodAlt2.setEditable(false);
                } else {
                    txtCodAlt2.setEditable(true);
                    txtCodAlt2.setBackground(txtCodAlt.getBackground());
                }
            } else {
                txtCodAlt2.setEditable(true);
                txtCodAlt2.setBackground(txtCodAlt.getBackground());
            }
        }
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        //  FndItem(txtCodItm.getText(),1);
        BuscarItem("a.co_itm",txtCodItm.getText(),0);
    }//GEN-LAST:event_txtCodItmActionPerformed

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost

    }//GEN-LAST:event_txtCodItmFocusLost

    private void butCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodActionPerformed
        //FndItem("",0);

        objVenConItm.setTitle("Listado de Items");
        objVenConItm.setCampoBusqueda(1);
        // objVenConItm.cargarDatos();
        objVenConItm.show();

        if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE)
        {
            txtCodItm.setText(objVenConItm.getValueAt(1) );
            txtCodAlt.setText(objVenConItm.getValueAt(2) );
            txtCodAlt2.setText(objVenConItm.getValueAt(3) );
            txtNomItm.setText(objVenConItm.getValueAt(4) );

        }

    }//GEN-LAST:event_butCodActionPerformed

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // FndGrupoVarios(txtCodGrp.getText(),1,3);

        txtCodGrp.transferFocus();
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        ////
        ////  if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp))
        ////        {
            ////            if (txtCodGrp.getText().equals(""))
            ////            {
                ////                txtCodGrp.setText("");
                ////                txtGrp.setText("");
                ////            }
            ////            else
            ////            {
                ////                 BuscarGrupo("a.co_reg",txtCodGrp.getText(),0);
                ////            }
            ////        }
        ////        else
        ////            txtCodGrp.setText(strCodGrp);
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
    }//GEN-LAST:event_txtCodGrpFocusGained

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
        /// FndGrupoVarios("",0,3);

        ////              objVenConGrp.setTitle("Listado de Linea");
        ////              objVenConGrp.setCampoBusqueda(1);
        ////              objVenConGrp.show();
        ////             if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE)
        ////             {
            ////                 txtCodGrp.setText(objVenConGrp.getValueAt(1) );
            ////                 txtGrp.setText(objVenConGrp.getValueAt(3) );
            ////             }
        ////

    }//GEN-LAST:event_butGrpActionPerformed

    private void txtGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpActionPerformed
        // FndGrupoVarios(txtGrp.getText(),2,3);

        txtGrp.transferFocus();
    }//GEN-LAST:event_txtGrpActionPerformed

////////
    private void txtGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusLost
        ////       if (!txtGrp.getText().equalsIgnoreCase(strDesLarGrp))
        ////        {
            ////            if (txtGrp.getText().equals(""))
            ////            {
                ////                txtCodGrp.setText("");
                ////                txtGrp.setText("");
                ////            }
            ////            else
            ////            {
                ////              BuscarGrupo("a.tx_desLar",txtGrp.getText(),2);
                ////            }
            ////        }
        ////        else
        ////            txtGrp.setText(strDesLarGrp);
    }//GEN-LAST:event_txtGrpFocusLost

    private void txtGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusGained
        // TODO add your handling code here:
        strDesLarGrp=txtGrp.getText();
        txtGrp.selectAll();
    }//GEN-LAST:event_txtGrpFocusGained

    private void txtLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLinActionPerformed
        //       FndGrupoVarios(txtLin.getText(),2,2);

        txtLin.transferFocus();
    }//GEN-LAST:event_txtLinActionPerformed

//cambiar por material
    private void txtLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLinFocusLost
        ////         if (!txtLin.getText().equalsIgnoreCase(strDesLarLin))
        ////        {
            ////            if (txtLin.getText().equals(""))
            ////            {
                ////                txtCodLin.setText("");
                ////                txtLin.setText("");
                ////            }
            ////            else
            ////            {
                ////               BuscarLinea("a.tx_desLar",txtLin.getText(),2);
                ////            }
            ////        }
        ////        else
        ////            txtLin.setText(strDesLarLin);
    }//GEN-LAST:event_txtLinFocusLost

    private void txtLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLinFocusGained
        // TODO add your handling code here:
        strDesLarLin=txtLin.getText();
        txtLin.selectAll();
    }//GEN-LAST:event_txtLinFocusGained

    private void butLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLinActionPerformed
        //  FndGrupoVarios("",0,2);

        ////
        ////              objVenConLin.setTitle("Listado de Linea");
        ////              objVenConLin.setCampoBusqueda(1);
        ////              objVenConLin.show();
        ////             if (objVenConLin.getSelectedButton()==objVenConLin.INT_BUT_ACE)
        ////             {
            ////                 txtCodLin.setText(objVenConLin.getValueAt(1) );
            ////                 txtLin.setText(objVenConLin.getValueAt(3) );
            ////             }
        ////
    }//GEN-LAST:event_butLinActionPerformed

    private void chkIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaComActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkIvaComActionPerformed

    private void chkIvaVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaVtaActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkIvaVtaActionPerformed

    private void chkIvaVtaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIvaVtaItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_chkIvaVtaItemStateChanged

    private void chkEsItmOtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmOtrActionPerformed
        // TODO add your handling code here:

        chkEsItmTra.setSelected(false);
        chkEsItmSer.setSelected(false);

        if (chkEsItmOtr.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        }
    }//GEN-LAST:event_chkEsItmOtrActionPerformed

    private void chkEsItmTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmTraActionPerformed
        // TODO add your handling code here:
        chkEsItmSer.setSelected(false);
        chkEsItmOtr.setSelected(false);

        if (chkEsItmTra.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        }
    }//GEN-LAST:event_chkEsItmTraActionPerformed

    private void chkEsItmSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmSerActionPerformed
        blnCmb = true;

        chkEsItmTra.setSelected(false);
        chkEsItmOtr.setSelected(false);

        if (chkEsItmSer.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        }
    }//GEN-LAST:event_chkEsItmSerActionPerformed

    private void txtItmUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmUniActionPerformed
        cboEst.requestFocus();
    }//GEN-LAST:event_txtItmUniActionPerformed

    private void txtItmUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItmUniFocusLost

    }//GEN-LAST:event_txtItmUniFocusLost

    private void txtNomItmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomItmKeyTyped
        // TODO add your handling code here:
        actualizarContador();
    }//GEN-LAST:event_txtNomItmKeyTyped

    private void txtNomItmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomItmKeyPressed
        // TODO add your handling code here:
        ////
        ////        textField = new JTextField();
        ////        textField.addKeyListener(new KeyListener() {
            ////        @Override
            ////           public void keyTyped(KeyEvent e) {
                ////                // Este método se llama cuando se presiona y luego se libera una tecla.
                ////                actualizarContador();
                ////            }
            ////        @Override
            ////        public void keyPressed(KeyEvent e) {
                ////            // Este método se llama cuando una tecla es presionada.
                ////        }
            ////
            ////        @Override
            ////        public void keyReleased(KeyEvent e) {
                ////            // Este método se llama cuando una tecla es liberada.
                ////        }
            ////        });
    ////        getContentPane().add(textField);
    ////        pack();
    ////        setLocationRelativeTo(null);
    }//GEN-LAST:event_txtNomItmKeyPressed

//cambiar por material
    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtCodLin.requestFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        if (!txtNomItm.getText().equals("")) {
            if (txtNomItm.getText().length()>110 ){
                strMsg="El campo <<Nombre del Item>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtNomItm.setText("");
                tabGenInv.setSelectedIndex(0);
                txtNomItm.requestFocus();
            }
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtNomItm.requestFocus();

    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        if (txtCodAlt.isEditable() || txtCodAlt.isEnabled()){
            String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
            if (strTerItm.equals("I") || strTerItm.equals("S")) {
                if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                    txtCodAlt2.setEditable(false);
                } else {
                    txtCodAlt2.setEditable(true);
                    txtCodAlt2.setBackground(txtCodAlt.getBackground());
                }
            } else {
                txtCodAlt2.setEditable(true);
                txtCodAlt2.setBackground(txtCodAlt.getBackground());
            }
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAbcFREFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcFREFocusGained
        strCodAbcFRE=txtCodAbcFRE.getText();
        txtCodAbcFRE.selectAll();        
    }//GEN-LAST:event_txtCodAbcFREFocusGained

    private void txtCodAbcFREFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcFREFocusLost
        if (!txtCodAbcFRE.getText().equalsIgnoreCase(strCodAbcFRE))
        {
            if (txtCodAbcFRE.getText().equals(""))
            {
                txtCodAbcFRE.setText("");
                txtAbcFRE.setText("");
            }
            else
            {
                BuscarABCFre("co_nivpar",txtCodAbcFRE.getText(),0);
            }
        }
        else
        txtCodAbcFRE.setText(strCodAbcFRE);
    }//GEN-LAST:event_txtCodAbcFREFocusLost

    private void txtCodAbcFREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAbcFREActionPerformed
        txtCodAbcFRE.transferFocus();        
    }//GEN-LAST:event_txtCodAbcFREActionPerformed

    private void txtAbcFREFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcFREFocusGained
        strDesLarAbcFRE=txtAbcFRE.getText();
        txtAbcFRE.selectAll();
    }//GEN-LAST:event_txtAbcFREFocusGained

    private void txtAbcFREFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcFREFocusLost
        if (!txtAbcFRE.getText().equalsIgnoreCase(strDesLarAbcFRE))
        {
            if (txtAbcFRE.getText().equals(""))
            {
                txtCodAbcFRE.setText("");
                txtAbcFRE.setText("");
            }
            else
            {
                BuscarABCFre("tx_nivpar",txtAbcFRE.getText(),2);
            }
        }
        else
        txtAbcFRE.setText(strDesLarAbcFRE);
    }//GEN-LAST:event_txtAbcFREFocusLost

    private void txtAbcFREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcFREActionPerformed
        txtAbcFRE.transferFocus();
    }//GEN-LAST:event_txtAbcFREActionPerformed

    private void butAbcFREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbcFREActionPerformed
        objVenABCFre.setTitle("Listado de ABC de Frecuencia");
        objVenABCFre.setCampoBusqueda(1);
        objVenABCFre.show();
        if (objVenABCFre.getSelectedButton()==objVenABCFre.INT_BUT_ACE)
        {
            txtCodAbcFRE.setText(objVenABCFre.getValueAt(1) );
            txtAbcFRE.setText(objVenABCFre.getValueAt(2) );
        }
    }//GEN-LAST:event_butAbcFREActionPerformed

    private void txtCodAbcPESFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcPESFocusGained
        strCodAbcPESO=txtCodAbcPES.getText();
        txtCodAbcPES.selectAll();        
    }//GEN-LAST:event_txtCodAbcPESFocusGained

    private void txtCodAbcPESFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcPESFocusLost
        if (!txtCodAbcPES.getText().equalsIgnoreCase(strCodAbcPESO))
        {
            if (txtCodAbcPES.getText().equals(""))
            {
                txtCodAbcPES.setText("");
                txtAbcPES.setText("");
            }
            else
            {
                BuscarABCPes("co_nivpar",txtCodAbcPES.getText(),0);
            }
        }
        else
        txtCodAbcPES.setText(strCodAbcPESO);
    }//GEN-LAST:event_txtCodAbcPESFocusLost

    private void txtCodAbcPESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAbcPESActionPerformed
        txtCodAbcPES.transferFocus();        
    }//GEN-LAST:event_txtCodAbcPESActionPerformed

    private void txtAbcPESFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcPESFocusGained
        strDesLarAbcPESO=txtAbcPES.getText();
        txtAbcPES.selectAll();        
    }//GEN-LAST:event_txtAbcPESFocusGained

    private void txtAbcPESFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcPESFocusLost
        if (!txtAbcPES.getText().equalsIgnoreCase(strDesLarAbcPESO))
        {
            if (txtAbcPES.getText().equals(""))
            {
                txtCodAbcPES.setText("");
                txtAbcPES.setText("");
            }
            else
            {
                BuscarABCPes("tx_nivpar",txtAbcPES.getText(),2);
            }
        }
        else
        txtAbcPES.setText(strDesLarAbcPESO);
    }//GEN-LAST:event_txtAbcPESFocusLost

    private void txtAbcPESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcPESActionPerformed
        txtAbcPES.transferFocus();
    }//GEN-LAST:event_txtAbcPESActionPerformed

    private void butAbcPESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbcPESActionPerformed
        objVenABCPeso.setTitle("Listado de ABC de Peso");
        objVenABCPeso.setCampoBusqueda(1);
        objVenABCPeso.show();
        if (objVenABCPeso.getSelectedButton()==objVenABCPeso.INT_BUT_ACE)
        {
            txtCodAbcPES.setText(objVenABCPeso.getValueAt(1) );
            txtAbcPES.setText(objVenABCPeso.getValueAt(2) );
        }
    }//GEN-LAST:event_butAbcPESActionPerformed

    private void txtCodAbcCLAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcCLAFocusGained
        strCodAbcCLAS=txtCodAbcCLA.getText();
        txtCodAbcCLA.selectAll();
    }//GEN-LAST:event_txtCodAbcCLAFocusGained

    private void txtCodAbcCLAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcCLAFocusLost
        if (!txtCodAbcCLA.getText().equalsIgnoreCase(strCodAbcCLAS))
        {
            if (txtCodAbcCLA.getText().equals(""))
            {
                txtCodAbcCLA.setText("");
                txtAbcCLA.setText("");
            }
            else
            {
                BuscarABCClase("co_nivpar",txtCodAbcCLA.getText(),0);
            }
        }
        else
        txtCodAbcCLA.setText(strCodAbcCLAS);
    }//GEN-LAST:event_txtCodAbcCLAFocusLost

    private void txtCodAbcCLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAbcCLAActionPerformed
        txtCodAbcCLA.transferFocus();
    }//GEN-LAST:event_txtCodAbcCLAActionPerformed

    private void txtAbcCLAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcCLAFocusGained
        strDesLarAbcCLAS=txtAbcCLA.getText();
        txtAbcCLA.selectAll();   
    }//GEN-LAST:event_txtAbcCLAFocusGained

    private void txtAbcCLAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcCLAFocusLost
        if (!txtAbcCLA.getText().equalsIgnoreCase(strDesLarAbcCLAS)) 
        {
            if (txtAbcCLA.getText().equals(""))
            {
                txtCodAbcCLA.setText("");
                txtAbcCLA.setText("");
            }
            else
            {
                BuscarABCClase("tx_nivpar",txtAbcCLA.getText(),2);
            }
        }
        else
        txtAbcCLA.setText(strDesLarAbcCLAS);
    }//GEN-LAST:event_txtAbcCLAFocusLost

    private void txtAbcCLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcCLAActionPerformed
        txtAbcCLA.transferFocus();
    }//GEN-LAST:event_txtAbcCLAActionPerformed

    private void butAbcCLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbcCLAActionPerformed
        objVenABCClas.setTitle("Listado de ABC de Clase");
        objVenABCClas.setCampoBusqueda(1);
        objVenABCClas.show();
        if (objVenABCClas.getSelectedButton()==objVenABCClas.INT_BUT_ACE)
        {
            txtCodAbcCLA.setText(objVenABCClas.getValueAt(1) );
            txtAbcCLA.setText(objVenABCClas.getValueAt(2) );
        }
    }//GEN-LAST:event_butAbcCLAActionPerformed

    private void txtCodAbcVALFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcVALFocusGained
        strCodAbcVALOR=txtCodAbcVAL.getText();
        txtCodAbcVAL.selectAll();
    }//GEN-LAST:event_txtCodAbcVALFocusGained

    private void txtCodAbcVALFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAbcVALFocusLost
        if (!txtCodAbcVAL.getText().equalsIgnoreCase(strCodAbcVALOR))
        {
            if (txtCodAbcVAL.getText().equals(""))
            {
                txtCodAbcVAL.setText("");
                txtAbcVAL.setText("");
            }
            else
            {
                BuscarABCValor("co_nivpar",txtCodAbcVAL.getText(),0);
            }
        }
        else
        txtCodAbcVAL.setText(strCodAbcVALOR);
    }//GEN-LAST:event_txtCodAbcVALFocusLost

    private void txtCodAbcVALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAbcVALActionPerformed
        txtCodAbcVAL.transferFocus();
    }//GEN-LAST:event_txtCodAbcVALActionPerformed

    private void txtAbcVALFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcVALFocusGained
        strDesLarAbcVALOR=txtAbcVAL.getText();
        txtAbcVAL.selectAll();   
    }//GEN-LAST:event_txtAbcVALFocusGained

    private void txtAbcVALFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbcVALFocusLost
        if (!txtAbcVAL.getText().equalsIgnoreCase(strDesLarAbcVALOR))
        {
            if (txtAbcVAL.getText().equals(""))
            {
                txtCodAbcVAL.setText("");
                txtAbcVAL.setText("");
            }
            else
            {
                BuscarABCValor("tx_nivpar",txtAbcVAL.getText(),2);
            }
        }
        else
        txtAbcVAL.setText(strDesLarAbcVALOR);
    }//GEN-LAST:event_txtAbcVALFocusLost

    private void txtAbcVALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcVALActionPerformed
        txtAbcVAL.transferFocus();
    }//GEN-LAST:event_txtAbcVALActionPerformed

    private void butAbcVALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbcVALActionPerformed
        objVenABCValor.setTitle("Listado de ABC de Valor Venta");
        objVenABCValor.setCampoBusqueda(1);
        objVenABCValor.show();
        if (objVenABCValor.getSelectedButton()==objVenABCValor.INT_BUT_ACE)
        {
            txtCodAbcVAL.setText(objVenABCValor.getValueAt(1) );
            txtAbcVAL.setText(objVenABCValor.getValueAt(2) );
        }
    }//GEN-LAST:event_butAbcVALActionPerformed

    private void txtCodLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLinActionPerformed
        //  FndGrupoVarios(txtCodLin.getText(),1,2);
        txtCodLin.transferFocus();
    }//GEN-LAST:event_txtCodLinActionPerformed

//cambiar por material
    private void txtCodLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLinFocusLost

        ////
        ////      if (!txtCodLin.getText().equalsIgnoreCase(strCodLin))
        ////        {
            ////            if (txtCodLin.getText().equals(""))
            ////            {
                ////                txtCodLin.setText("");
                ////                txtLin.setText("");
                ////            }
            ////            else
            ////            {
                ////                BuscarLinea("a.co_reg",txtCodLin.getText(),0);
                ////            }
            ////        }
        ////        else
        ////            txtCodLin.setText(strCodLin);
    }//GEN-LAST:event_txtCodLinFocusLost

    private void txtCodLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLinFocusGained

    }//GEN-LAST:event_txtCodLinFocusGained

    private void txtCodLinAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtCodLinAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodLinAncestorAdded

    private void txtCodCategoriaCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCategoriaCompraFocusGained
        // TODO add your handling code here:
        strCodCategoriaCompra=txtCodCategoriaCompra.getText();
        txtCodCategoriaCompra.selectAll();        
    }//GEN-LAST:event_txtCodCategoriaCompraFocusGained

    private void txtCodCategoriaCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCategoriaCompraFocusLost
        // TODO add your handling code here:
        if (!txtCodCategoriaCompra.getText().equalsIgnoreCase(strCodCategoriaCompra))
        {
            if (txtCodCategoriaCompra.getText().equals(""))
            {
                txtCodCategoriaCompra.setText("");
                txtCategoriaCompra.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarCategoriaCompra("co_reg",txtCodCategoriaCompra.getText(),0);
            }
        }
        else
        txtCodCategoriaCompra.setText(strCodCategoriaCompra);        
    }//GEN-LAST:event_txtCodCategoriaCompraFocusLost

    private void txtCodCategoriaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCategoriaCompraActionPerformed
        // TODO add your handling code here:
        txtCodCategoriaCompra.transferFocus();
    }//GEN-LAST:event_txtCodCategoriaCompraActionPerformed

    private void butCategoriaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCategoriaCompraActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        objVenConCategoriaCompra.setTitle("Listado de Categoria de Compra");
        objVenConCategoriaCompra.setCampoBusqueda(1);
        objVenConCategoriaCompra.show();
        if (objVenConCategoriaCompra.getSelectedButton()==objVenConCategoriaCompra.INT_BUT_ACE)
        {
            txtCodCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(1) );
            txtCategoriaCompra.setText(objVenConCategoriaCompra.getValueAt(3) );
        }        
    }//GEN-LAST:event_butCategoriaCompraActionPerformed

    private void txtCategoriaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaCompraActionPerformed
        // TODO add your handling code here:
        txtCategoriaCompra.transferFocus();
    }//GEN-LAST:event_txtCategoriaCompraActionPerformed

    private void txtCategoriaCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCategoriaCompraFocusLost
        // TODO add your handling code here:
        if (!txtCategoriaCompra.getText().equalsIgnoreCase(strDesLarCategoriaCompra))
        {
            if (txtCategoriaCompra.getText().equals(""))
            {
                txtCodCategoriaCompra.setText("");
                txtCategoriaCompra.setText("");
            }
            else
            {
                BuscarCategoriaCompra("tx_desLar",txtCategoriaCompra.getText(),2);
            }
        }
        else
        txtCategoriaCompra.setText(strDesLarCategoriaCompra);
    }//GEN-LAST:event_txtCategoriaCompraFocusLost

    private void txtCategoriaCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCategoriaCompraFocusGained
        // TODO add your handling code here:
        strDesLarCategoriaCompra=txtCategoriaCompra.getText();
        txtCategoriaCompra.selectAll();
    }//GEN-LAST:event_txtCategoriaCompraFocusGained

    private void txtCodLineaCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLineaCompraFocusGained
        strCodLineaCompra=txtCodLineaCompra.getText();
        txtCodLineaCompra.selectAll();
    }//GEN-LAST:event_txtCodLineaCompraFocusGained

    private void txtCodLineaCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLineaCompraFocusLost
        if (!txtCodLineaCompra.getText().equalsIgnoreCase(strCodLineaCompra))
        {
            if (txtCodLineaCompra.getText().equals(""))
            {
                txtCodLineaCompra.setText("");
                txtLineaCompra.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarLineaCompra("co_reg",txtCodLineaCompra.getText(),0);
            }
        }
        else
        txtCodLineaCompra.setText(strCodLineaCompra); 
    }//GEN-LAST:event_txtCodLineaCompraFocusLost

    private void txtCodLineaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLineaCompraActionPerformed
        txtCodLineaCompra.transferFocus();
    }//GEN-LAST:event_txtCodLineaCompraActionPerformed

    private void txtLineaCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineaCompraFocusGained
        strDesLarLineaCompra=txtLineaCompra.getText();
        txtLineaCompra.selectAll();
    }//GEN-LAST:event_txtLineaCompraFocusGained

    private void txtLineaCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineaCompraFocusLost
        // TODO add your handling code here:
        if (!txtLineaCompra.getText().equalsIgnoreCase(strDesLarLineaCompra))
        {
            if (txtLineaCompra.getText().equals(""))
            {
                txtCodLineaCompra.setText("");
                txtLineaCompra.setText("");
            }
            else
            {
                BuscarLineaCompra("tx_desLar",txtLineaCompra.getText(),2);
            }
        }
        else
        txtLineaCompra.setText(strDesLarLineaCompra);
    }//GEN-LAST:event_txtLineaCompraFocusLost

    private void txtLineaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineaCompraActionPerformed
        txtLineaCompra.transferFocus();
    }//GEN-LAST:event_txtLineaCompraActionPerformed

    private void butLineaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLineaCompraActionPerformed
        objVenConLineaCompra.setTitle("Listado de Linea de Compra");
        objVenConLineaCompra.setCampoBusqueda(1);
        objVenConLineaCompra.show();
        if (objVenConLineaCompra.getSelectedButton()==objVenConLineaCompra.INT_BUT_ACE)
        {
            txtCodLineaCompra.setText(objVenConLineaCompra.getValueAt(1) );
            txtLineaCompra.setText(objVenConLineaCompra.getValueAt(3) );
        } // TODO add your handling code here:
    }//GEN-LAST:event_butLineaCompraActionPerformed

    private void txtCodClaseCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaseCompraFocusGained
        strCodClaseCompra=txtCodClaseCompra.getText();
        txtCodClaseCompra.selectAll();
    }//GEN-LAST:event_txtCodClaseCompraFocusGained

    private void txtCodClaseCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaseCompraFocusLost
        if (!txtCodClaseCompra.getText().equalsIgnoreCase(strCodClaseCompra))
        {
            if (txtCodClaseCompra.getText().equals(""))
            {
                txtCodClaseCompra.setText("");
                txtClaseCompra.setText("");
            }
            else
            {
                //BuscarRefD("co_reg",txtCodRefD.getText(),0);
                BuscarClaseCompra("co_reg",txtCodClaseCompra.getText(),0);
            }
        }
        else
        txtCodClaseCompra.setText(strCodClaseCompra); 
    }//GEN-LAST:event_txtCodClaseCompraFocusLost

    private void txtCodClaseCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClaseCompraActionPerformed
         txtCodClaseCompra.transferFocus();
    }//GEN-LAST:event_txtCodClaseCompraActionPerformed

    private void txtClaseCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaseCompraFocusGained
        strDesLarClaseCompra=txtClaseCompra.getText();
        txtClaseCompra.selectAll();
    }//GEN-LAST:event_txtClaseCompraFocusGained

    private void txtClaseCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaseCompraFocusLost
    if (!txtClaseCompra.getText().equalsIgnoreCase(strDesLarClaseCompra))
        {
            if (txtClaseCompra.getText().equals(""))
            {
                txtCodClaseCompra.setText("");
                txtClaseCompra.setText("");
            }
            else
            {
                BuscarClaseCompra("tx_desLar",txtClaseCompra.getText(),2);
            }
        }
        else
        txtClaseCompra.setText(strDesLarClaseCompra);
    }//GEN-LAST:event_txtClaseCompraFocusLost

    private void txtClaseCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaseCompraActionPerformed
        txtClaseCompra.transferFocus();
    }//GEN-LAST:event_txtClaseCompraActionPerformed

    private void butClaseCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaseCompraActionPerformed
        //20052026
        objVenConClaseCompra.setTitle("Listado de Clase de Compra");
        objVenConClaseCompra.setCampoBusqueda(1);
        objVenConClaseCompra.show();
        if (objVenConClaseCompra.getSelectedButton()==objVenConClaseCompra.INT_BUT_ACE)
        {
            txtCodClaseCompra.setText(objVenConClaseCompra.getValueAt(1) );
            txtClaseCompra.setText(objVenConClaseCompra.getValueAt(3) );
        }         
    }//GEN-LAST:event_butClaseCompraActionPerformed

    private void actualizarContador() {
        int numeroCaracteres = txtNomItm.getText().trim().length();
        int numeroMaximoCaracteres = 109;
        txtNomItm.setText(txtNomItm.getText().trim());
        String numeroFaltantes = (numeroCaracteres-numeroMaximoCaracteres) + " Letras Faltantes";
        lblCtaLetra.setText(numeroFaltantes);
        System.out.println("Número de caracteres: " + numeroCaracteres);
        // Puedes mostrar el número de caracteres en algún componente adicional o hacer lo que desees.
    }    
    
    
//    private void FndGrupoVarios(String strBusqueda,int TipoBusqueda, int intTipoGrupo){
//     try{
//        Librerias.ZafConsulta.ZafConsulta objFndGrupoVarios = 
//            new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
//            "Codigo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
//            "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = " + intTipoGrupo ,
//            strBusqueda, 
//            objZafParSis.getStringConexion(), 
//            objZafParSis.getUsuarioBaseDatos(), 
//            objZafParSis.getClaveBaseDatos()
//            );
//  
//        objFndGrupoVarios.setTitle("Listado de Unidad de Medida");
//        switch (TipoBusqueda)
//            {
//                case 1:                                        
//                     objFndGrupoVarios.setSelectedCamBus(0);
//                    if(!objFndGrupoVarios.buscar("co_reg = " + (strBusqueda.equals("")?"0":strBusqueda)));
//                        objFndGrupoVarios.show();
//                     break;
//                case 2:                                        
//                     objFndGrupoVarios.setSelectedCamBus(2);
//                    if(!objFndGrupoVarios.buscar("tx_desLar = '" +strBusqueda+"'"));
//                        objFndGrupoVarios.show();
//                     break;
//                default:
//                    objFndGrupoVarios.show();
//                    break;             
//             }                     
//            if(!objFndGrupoVarios.GetCamSel(1).equals("")){
//                switch (intTipoGrupo){
//                    case 2:
//                        txtCodLin.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtLin.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 3:
//                        txtCodGrp.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtGrp.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 4:
//                        txtCodMar.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtMar.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 5:
//                        txtCodUniMed.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtUniMed.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;                        
//                }
//          }else{
//                switch (intTipoGrupo){
//                    case 2:
//                        txtCodLin.setText("");
//                        txtLin.setText("");
//                        break;
//                    case 3:
//                        txtCodGrp.setText("");
//                        txtGrp.setText("");
//                        break;
//                    case 4:
//                        txtCodMar.setText("");
//                        txtMar.setText("");
//                        break;
//                    case 5:
//                        txtCodUniMed.setText("");
//                        txtUniMed.setText("");
//                        break;                        
//                }
//          }
//        } catch(Exception Evt) {
//                objUti.mostrarMsgErr_F1(this, Evt);                    
//        }                           
//                   
//        
//    }
//    
      
//    private void FndItem (String strBusqueda,int TipoBusqueda){
//        try{
//              Librerias.ZafConsulta.ZafConsulta objFndCodItm = 
//                    new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
//                    "Codigo,Cod Alt,Nombre,Prec Unit,Stk Act,Estado","co_itm,tx_codAlt,tx_codAlt2,tx_nomItm,nd_cosUni,nd_stkAct,st_reg",
//                    "SELECT co_itm,tx_codAlt,tx_codAlt2,tx_nomItm,nd_cosUni,nd_stkAct,st_reg FROM tbm_inv WHERE st_reg not in('T') and  co_emp = " + objZafParSis.getCodigoEmpresa(),
//                    strBusqueda, 
//                    objZafParSis.getStringConexion(), 
//                    objZafParSis.getUsuarioBaseDatos(), 
//                    objZafParSis.getClaveBaseDatos()
//                    );
//
//               objFndCodItm.setTitle("Listado de Productos");
//            switch (TipoBusqueda)
//            {
//                case 1:                                        
//                     objFndCodItm.setSelectedCamBus(0);
//                    if(!objFndCodItm.buscar("co_itm = " + (strBusqueda.equals("")?"0":strBusqueda)));
//                        objFndCodItm.show();
//                     break;
//                default:
//                    objFndCodItm.show();
//                    break;             
//             }                     
//            if(!objFndCodItm.GetCamSel(1).equals("")){            
//                txtCodItm.setText(objFndCodItm.GetCamSel(1).toString());
//                txtCodAlt.setText(objFndCodItm.GetCamSel(2).toString());
//                txtCodAlt2.setText(objFndCodItm.GetCamSel(3).toString());
//                txtNomItm.setText(objFndCodItm.GetCamSel(4).toString());
//                
//            }
//            
//        } catch(Exception Evt) {
//                objUti.mostrarMsgErr_F1(this, Evt);                    
//        }                           
//        
//    }
//    
    
   /**Cerrar la aplicaciÃ³n*/
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAbcCLA;
    private javax.swing.JButton butAbcFRE;
    private javax.swing.JButton butAbcPES;
    private javax.swing.JButton butAbcTUV;
    private javax.swing.JButton butAbcVAL;
    private javax.swing.JButton butCategoria;
    private javax.swing.JButton butCategoriaCompra;
    private javax.swing.JButton butCla;
    private javax.swing.JButton butClaseCompra;
    private javax.swing.JButton butCod;
    private javax.swing.JButton butCompr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butLin;
    private javax.swing.JButton butLinea;
    private javax.swing.JButton butLineaCompra;
    private javax.swing.JButton butMar;
    private javax.swing.JButton butMat;
    private javax.swing.JButton butUniMed;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboEst;
    private javax.swing.JCheckBox chkCamPreVta;
    private javax.swing.JCheckBox chkEsItmOtr;
    private javax.swing.JCheckBox chkEsItmSer;
    private javax.swing.JCheckBox chkEsItmTra;
    private javax.swing.JCheckBox chkIvaCom;
    private javax.swing.JCheckBox chkIvaVta;
    private javax.swing.JCheckBox chkOblCamNomItm;
    private javax.swing.JCheckBox chkpermodnomcom;
    private javax.swing.JCheckBox chkpermodnomven;
    private javax.swing.JLabel lblCodAlt;
    private javax.swing.JLabel lblCodAlt1;
    private javax.swing.JLabel lblCodAlt2;
    private javax.swing.JLabel lblCodClaInv;
    private javax.swing.JLabel lblControl;
    private javax.swing.JLabel lblCosUni;
    private javax.swing.JLabel lblCosUni1;
    private javax.swing.JLabel lblCosUni2;
    private javax.swing.JLabel lblCosUni3;
    private javax.swing.JLabel lblCtaLetra;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblFre;
    private javax.swing.JLabel lblItmUni;
    private javax.swing.JLabel lblLin;
    private javax.swing.JLabel lblMar;
    private javax.swing.JLabel lblMat;
    private javax.swing.JLabel lblMat1;
    private javax.swing.JLabel lblMat2;
    private javax.swing.JLabel lblMat3;
    private javax.swing.JLabel lblMat4;
    private javax.swing.JLabel lblMat5;
    private javax.swing.JLabel lblMat6;
    private javax.swing.JLabel lblMat7;
    private javax.swing.JLabel lblNomItm;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPe;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblRefD;
    private javax.swing.JLabel lblRefG;
    private javax.swing.JLabel lblStkAct;
    private javax.swing.JLabel lblStkMax;
    private javax.swing.JLabel lblStkMin;
    private javax.swing.JLabel lblTama;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUniMed;
    private javax.swing.JLabel lblValven;
    private javax.swing.JLabel lblWebCli1;
    private javax.swing.JLabel lblWebCli2;
    private javax.swing.JLabel lblWebCli4;
    private javax.swing.JLabel lblWebCli5;
    private javax.swing.JLabel lblgrp;
    private javax.swing.JLabel lblusuing;
    private javax.swing.JLabel lblusuing1;
    private javax.swing.JPanel panDatABC;
    private javax.swing.JPanel panDatTec;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGrpPre;
    private javax.swing.JPanel panGrpPre1;
    private javax.swing.JPanel panItmSer;
    private javax.swing.JPanel panItmSer1;
    private javax.swing.JPanel panItmSer2;
    private javax.swing.JPanel panIva;
    private javax.swing.JPanel panPriInv;
    private javax.swing.JPanel panStk;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JSpinner spnPeso;
    private javax.swing.JTabbedPane tabGenInv;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAbcCLA;
    private javax.swing.JTextField txtAbcFRE;
    private javax.swing.JTextField txtAbcPES;
    private javax.swing.JTextField txtAbcTUV;
    private javax.swing.JTextField txtAbcVAL;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCategoriaCompra;
    private javax.swing.JTextField txtCla;
    private javax.swing.JTextField txtClaseCompra;
    private javax.swing.JTextField txtCodAbcCLA;
    private javax.swing.JTextField txtCodAbcFRE;
    private javax.swing.JTextField txtCodAbcPES;
    private javax.swing.JTextField txtCodAbcTUV;
    private javax.swing.JTextField txtCodAbcVAL;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltAux;
    private javax.swing.JTextField txtCodCategoria;
    private javax.swing.JTextField txtCodCategoriaCompra;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtCodClaseCompra;
    private javax.swing.JTextField txtCodCompr;
    private javax.swing.JTextField txtCodControl;
    private javax.swing.JTextField txtCodGP;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLin;
    private javax.swing.JTextField txtCodLinea;
    private javax.swing.JTextField txtCodLineaCompra;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtCodMat;
    private javax.swing.JTextField txtCodTama;
    private javax.swing.JTextField txtCodUniMed;
    private javax.swing.JTextField txtCompr;
    private javax.swing.JTextField txtControl;
    private javax.swing.JTextField txtCortaUniMed;
    private javax.swing.JTextField txtCosUni;
    private javax.swing.JTextField txtCosUni1;
    private javax.swing.JTextField txtCosUni2;
    private javax.swing.JTextField txtCosUni3;
    private javax.swing.JTextField txtGrp;
    private javax.swing.JTextField txtItmUni;
    private javax.swing.JTextField txtLin;
    private javax.swing.JTextField txtLinea;
    private javax.swing.JTextField txtLineaCompra;
    private javax.swing.JTextField txtMar;
    private javax.swing.JTextField txtMat;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtStkAct;
    private javax.swing.JTextField txtStkMax;
    private javax.swing.JTextField txtStkMaxAux;
    private javax.swing.JTextField txtStkMin;
    private javax.swing.JTextField txtStkMinAux;
    private javax.swing.JTextField txtUniMed;
    private javax.swing.JLabel txtfecing;
    private javax.swing.JLabel txtfecmod;
    // End of variables declaration//GEN-END:variables
 

    /**
     * Valida si el item posee stock 
     * @return 
     */

    private boolean itemSinStock(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT nd_stkAct  \n";
                strSql+=" FROM(  \n";
                strSql+="       SELECT SUM(CASE WHEN a.nd_stkAct IS NULL THEN 0 ELSE a.nd_stkAct END) as nd_stkAct \n";
                strSql+="       FROM tbm_invbod as a  \n";
                strSql+="       INNER JOIN tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm)  \n";
                strSql+="       INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) \n";
                strSql+="       WHERE a2.co_itmmae= ( SELECT a2.co_itmMae  \n";
                strSql+="                             FROM tbm_inv as a1   \n";
                strSql+="                             INNER JOIN tbm_equinv as a2 ON (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) \n";
                strSql+="                             WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_itm="+txtCodItm.getText()+"  \n";
                strSql+="                           )    \n";
                strSql+=" ) AS x   \n";
                strSql+=" WHERE nd_stkAct > 0  \n";
                System.out.println("itemSinStock: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    //if(rstLoc.getDouble("nd_stkAct")==0){
                        blnRes=false;
                    //}
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean cambiarEstadoItemTodasLasEmpresas(java.sql.Connection conExt,int CodEmp, int CodItm,String EstReg ){
        boolean blnRes=false;
        String strFecUltMod;
        java.sql.Statement stmLoc, stmIns;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                stmLoc=conExt.createStatement();stmIns=conExt.createStatement();
                strSql= "";
                strSql+=" SELECT a2.co_itmMae, a2.co_emp,a2.co_itm   \n";
                strSql+=" FROM tbm_equinv as a2  \n";
                strSql+=" INNER JOIN tbm_inv AS inv ON(inv.co_emp=a2.co_emp AND inv.co_itm=a2.co_itm )     \n";
                strSql+=" WHERE a2.co_itmmae= ( SELECT a2.co_itmMae \n";
                strSql+="                       FROM tbm_inv as a1  \n";
                strSql+="                       INNER JOIN tbm_equinv as a2 ON (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) \n";
                strSql+="                       WHERE a1.co_emp="+CodEmp+" and a1.co_itm="+CodItm+" \n";
                strSql+=" ) ";
                rstLoc = stmLoc.executeQuery(strSql) ;
                while(rstLoc.next()){
                    strSql = " UPDATE tbm_inv SET st_reg= '"+EstReg+"',co_usrMod="+objZafParSis.getCodigoUsuario()+", fe_ultMod='"+strFecUltMod+"'";
                    strSql+= " WHERE co_emp ="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm")+" ;";    
                    stmIns.executeUpdate(strSql);
                }
                objHisTblBasDat.insertarHistoricoMasivo(conExt, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                stmIns.close();
                stmIns=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            blnRes=false;
        }
        return blnRes;
    }


}
    




