/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon65;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author sistemas4
 */
public class ZafCon65_04 extends  javax.swing.JDialog {
    private ZafParSis objParSis;
    private ZafCon65 objCon65;
    private String CodPeriodo="",strNePeri="";
    private ZafUtil objUti;
    private Vector vecDatDP,vecDatDG, vecCab1,vecCab,vecRegDG,vecRegDP,vecAux,vec1quin,vecReg1quin,vecReg,vecDat;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblModDG,objTblModDP;
    private ZafTblPopMnu objTblPopMnu,objTblPopMnu1;
    private ZafTblCelRenLbl objTblCelRenLbl,objTblCelRenLbl1;                    //Render: Presentar JLabel en JTable.
    List<DetalleDiario> resArmarDatDiario = new ArrayList<>();
    private ZafTblTot objTblTot,objTblTotp;                                //JTable de totales.
    String strSQL="";

    private Statement stm,stm1,stm2,stm3,stm4,stm5,stm6,stm7,stm8,stm9,stm10,stm11,stm12;
    private ResultSet rst,rstsum,rstconf,rstOtIng,rstconcta,rstCtaImpRen,rstOtrDes,rstOtrDes1,rstconf1,rstconf2,rstOtIng1,rstconf3,rstconf4,rstCtaImpRen1;
    //TABLA DIARIO GENERAL
    private static final int INT_TBL_DAT_LINEA_DG = 0;   //Índice de columna Linea.       
    final int INT_DAT_DG_COD_CTA=1;
    final int INT_DAT_DG_NOM_CTA=2;
    final int INT_DAT_DG_NUM_CTA=3;    
    final int INT_DAT_DG_DEBE=4;
    final int INT_DAT_DG_HABER=5; 
    
    //TABLA DIARIO PROVICIONAL
    private static final int INT_TBL_DAT_LINEA_DP = 0;                          //Índice de columna Linea.       
    final int INT_DAT_DP_COD_CTA=1;
    final int INT_DAT_DP_NOM_CTA=2;  
    final int INT_DAT_DP_NUM_CTA=3;    
    final int INT_DAT_DP_DEBE=4;
    final int INT_DAT_DP_HABER=5;
    
    private boolean btnGuardar=false;    

    /**
     * 
     * Creates new form ZafCon65_04
     */
    public ZafCon65_04(ZafParSis obj) {
         try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }  
    }
    
     private boolean configurarTblDiarioGeneral()
    {          
        objUti = new ZafUtil();
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatDG=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA_DG, "");
            vecCab.add(INT_DAT_DG_COD_CTA,"Cod.cta");
            vecCab.add(INT_DAT_DG_NOM_CTA,"Cuenta");
            vecCab.add(INT_DAT_DG_NUM_CTA,"#Cta.");
            vecCab.add(INT_DAT_DG_DEBE,"DEBE"); 
            vecCab.add(INT_DAT_DG_HABER,"HABER");  
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDG=new ZafTblMod();
            objTblModDG.setHeader(vecCab);
            tbldiaGen.setModel(objTblModDG);
            //Configurar JTable: Establecer tipo de selección.
            tbldiaGen.setRowSelectionAllowed(true);
            tbldiaGen.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tbldiaGen);
            objColNum = new ZafColNumerada(tbldiaGen, INT_TBL_DAT_LINEA_DG);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tbldiaGen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tbldiaGen.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA_DG).setPreferredWidth(50);    
            tcmAux.getColumn(INT_DAT_DG_COD_CTA).setPreferredWidth(45);
            tcmAux.getColumn(INT_DAT_DG_NOM_CTA).setPreferredWidth(300);           
            tcmAux.getColumn(INT_DAT_DG_NUM_CTA).setPreferredWidth(90);
            tcmAux.getColumn(INT_DAT_DG_DEBE).setPreferredWidth(70);
            tcmAux.getColumn(INT_DAT_DG_HABER).setPreferredWidth(70); 
 
           //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tbldiaGen.getTableHeader().setReorderingAllowed(false);
                      
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
           //Configurar JTable: Establecer columnas editables.
//            vecAux = new Vector(); 
//            vecAux.add("" + INT_DAT_HIS_ADD_ROW); 
//            vecAux.add("" + INT_DAT_HIS_BTN_MOD); 
//            vecAux.add("" + INT_DAT_HIS_CHK_PAG);
//            vecAux.add("" + INT_DAT_HIS_MNT);
//            objTblModExc.setColumnasEditables(vecAux);
            vecAux = null;
            objTblCelRenLbl1 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl1.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl1.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl1.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_DG_DEBE).setCellRenderer(objTblCelRenLbl1);         
            objTblCelRenLbl1 = null; 
            
             objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_DG_HABER).setCellRenderer(objTblCelRenLbl);         
            objTblCelRenLbl = null; 
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModDG.setModoOperacion(ZafTblMod.INT_TBL_EDI);
             //Libero los objetos auxiliares.
            tcmAux=null;
            
            int intCol[]={INT_DAT_DG_DEBE,INT_DAT_DG_HABER};
            objTblTot=new ZafTblTot(spnDG, spnTotDG, tbldiaGen, tblTot, intCol);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
       private boolean configurarTblDiarioProvisional()
    {          
        objUti = new ZafUtil();
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatDP=new Vector();    //Almacena los datos
            vecCab1=new Vector();   //Almacena las cabeceras
            vecCab1.clear();
            vecCab1.add(INT_TBL_DAT_LINEA_DP, "");
            vecCab1.add(INT_DAT_DP_COD_CTA,"Cod.cta");
            vecCab1.add(INT_DAT_DP_NOM_CTA,"Cuenta");
            vecCab1.add(INT_DAT_DP_NUM_CTA,"#Cta.");
            vecCab1.add(INT_DAT_DP_DEBE,"DEBE"); 
            vecCab1.add(INT_DAT_DP_HABER,"HABER");  
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDP=new ZafTblMod();
            objTblModDP.setHeader(vecCab1);
            tbldiaprov.setModel(objTblModDP);
            //Configurar JTable: Establecer tipo de selección.
            tbldiaprov.setRowSelectionAllowed(true);
            tbldiaprov.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu1=new ZafTblPopMnu(tbldiaprov);
            objColNum = new ZafColNumerada(tbldiaprov, INT_TBL_DAT_LINEA_DP);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tbldiaprov.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tbldiaprov.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA_DP).setPreferredWidth(50);    
            tcmAux.getColumn(INT_DAT_DP_COD_CTA).setPreferredWidth(45);
            tcmAux.getColumn(INT_DAT_DP_NOM_CTA).setPreferredWidth(300);           
            tcmAux.getColumn(INT_DAT_DP_NUM_CTA).setPreferredWidth(90);
            tcmAux.getColumn(INT_DAT_DP_DEBE).setPreferredWidth(70);
            tcmAux.getColumn(INT_DAT_DP_HABER).setPreferredWidth(70); 
 
           //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tbldiaprov.getTableHeader().setReorderingAllowed(false);
                      
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
           //Configurar JTable: Establecer columnas editables.
//            vecAux = new Vector(); 
//            vecAux.add("" + INT_DAT_HIS_ADD_ROW); 
//            vecAux.add("" + INT_DAT_HIS_BTN_MOD); 
//            vecAux.add("" + INT_DAT_HIS_CHK_PAG);
//            vecAux.add("" + INT_DAT_HIS_MNT);
           // objTblModExc.setColumnasEditables(vecAux);
            vecAux = null;
            objTblCelRenLbl1 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl1.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl1.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl1.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_DP_DEBE).setCellRenderer(objTblCelRenLbl1);         
            objTblCelRenLbl1 = null; 
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_DP_HABER).setCellRenderer(objTblCelRenLbl);         
            objTblCelRenLbl = null; 
////            
            //Configurar JTable: Establecer el modo de operación.
            objTblModDP.setModoOperacion(ZafTblMod.INT_TBL_EDI);
             //Libero los objetos auxiliares.
            tcmAux=null;
            
             int intCol[]={INT_DAT_DP_DEBE,INT_DAT_DP_HABER};
            objTblTotp=new ZafTblTot(spnDP, spnTotDP, tbldiaprov, tblTot1, intCol);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    public ZafCon65_04(Frame parent, ZafParSis obj, String strCodEmp, String strCodPeriodo, String strNePer,boolean blnGuardar  ){
        super(parent, true);
      try
        {
            initComponents(); 
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);
            //Inicializar objetos.
            CodPeriodo=strCodPeriodo;
            strNePeri=strNePer;    
            btnGuardar=blnGuardar;
            configurarTblDiarioGeneral();  
            configurarTblDiarioProvisional();
            cargarReg(CodPeriodo,strNePeri);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
 
    private boolean cargarReg( String strCodPeriodo, String strNePer) {
        java.sql.Connection con = null;
        java.sql.Statement stm,stm1 = null;
        java.sql.ResultSet rst,rst1 = null;
        boolean blnRes = false;      
        String strSqlDG="",strSqlDP="";   
        int i,j;
        try{ 
             
            con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            vecDatDP = new Vector(); 
            vecDatDG = new Vector();

            if (con != null) {
                i = 0;         
                j = 0;
                stm = con.createStatement();    
                stm1 = con.createStatement(); 

                strSqlDG = "";      
                strSqlDG += "  select A.*, trim(b.ACTDESCR)ACTDESCR,trim(c.ACTNUMST)ACTNUMST from tbm_diagenbor a inner join tuval..GL00100 (nolock) AS b on a.co_cta=b.ACTINDX\n" +                       
                        "inner join tuval..GL00105 (nolock) AS c on b.ACTINDX=c.ACTINDX where a.ne_per=" + strNePer + " and a.co_per=" + strCodPeriodo + " and a.tx_tipdia ='F'";
                System.out.println("strSqlDG: "+strSqlDG);
                rst = stm.executeQuery(strSqlDG);
                if (rst.next()) {
                //DIARIO GENERAL
                 do {
                        vecRegDG = new Vector();
                        vecRegDG.add(INT_TBL_DAT_LINEA_DG, "");
                        vecRegDG.add(INT_DAT_DG_COD_CTA, rst.getString("co_cta"));  
                        vecRegDG.add(INT_DAT_DG_NOM_CTA, rst.getString("ACTDESCR"));     
                        vecRegDG.add(INT_DAT_DG_NUM_CTA, rst.getString("ACTNUMST")); 
                        if (rst.getString("tx_tipmov").equals("0")){
                            vecRegDG.add(INT_DAT_DG_DEBE, rst.getString("nd_mntcta")); 
                            vecRegDG.add(INT_DAT_DG_HABER, 0); 
                        }else if (rst.getString("tx_tipmov").equals("1")){
                            vecRegDG.add(INT_DAT_DG_DEBE, 0); 
                            vecRegDG.add(INT_DAT_DG_HABER, rst.getString("nd_mntcta"));                             
                        }
                        vecDatDG.add(vecRegDG);
                        i++;
                    } while (rst.next());  
                 objTblModDG.setData(vecDatDG);
                 tbldiaGen.setModel(objTblModDG); 
                 objTblTot.calcularTotales();
                 blnRes=true;

                }else{
                    blnRes=false;
                }
//                 objTblModDG.setData(vecDatDG);
//                 tbldiaGen.setModel(objTblModDG); 
//                  objTblTot.calcularTotales();
                 
                strSqlDP = "";      
                strSqlDP += "  select A.*, trim(b.ACTDESCR)ACTDESCR,trim(c.ACTNUMST)ACTNUMST from tbm_diagenbor a inner join tuval..GL00100 (nolock) AS b on a.co_cta=b.ACTINDX\n" +                       
                        "inner join tuval..GL00105 (nolock) AS c on b.ACTINDX=c.ACTINDX where a.ne_per=" + strNePer + " and a.co_per=" + strCodPeriodo + " and tx_tipdia ='P'";
                rst1 = stm1.executeQuery(strSqlDP);
                System.out.println("strSqlDP: "+strSqlDP);
                //DIARIO PROVISIONES
                if (rst1.next()) {
                do {
                        vecRegDP = new Vector();
                        vecRegDP.add(INT_TBL_DAT_LINEA_DP, "");
                        vecRegDP.add(INT_DAT_DP_COD_CTA, rst1.getString("co_cta"));  
                        vecRegDP.add(INT_DAT_DP_NOM_CTA, rst1.getString("ACTDESCR"));     
                        vecRegDP.add(INT_DAT_DP_NUM_CTA, rst1.getString("ACTNUMST")); 
                        if (rst1.getString("tx_tipmov").equals("0")){
                            vecRegDP.add(INT_DAT_DP_DEBE, rst1.getString("nd_mntcta"));
                            vecRegDP.add(INT_DAT_DP_HABER, 0); 

                        }else  if (rst1.getString("tx_tipmov").equals("1")){
                            vecRegDP.add(INT_DAT_DP_DEBE, 0); 
                            vecRegDP.add(INT_DAT_DP_HABER, rst1.getString("nd_mntcta")); 
                        }
                        
                        vecDatDP.add(vecRegDP);
                        j++;
                    } while (rst1.next());
                
                 objTblModDP.setData(vecDatDP);
                 tbldiaprov.setModel(objTblModDP);   
                 objTblTotp.calcularTotales(); 
                }
                //else{
//                    blnRes=false;
//                }
//                 objTblModDP.setData(vecDatDP);
//                 tbldiaprov.setModel(objTblModDP);   
//                 objTblTotp.calcularTotales(); 
                 if(!blnRes){
                     generarDiarioBorrador(vecDatDG,vecDatDP,strCodPeriodo,Integer.parseInt(strNePer));
                }
                con.close();
                con = null;
            }
            
        } catch (Exception e) {
            blnRes = false;
        }
        
        return blnRes;
    }
     private boolean InsertaListDiario(int co_cta, Double valor_cta, String Tip_mov, String Tip_Diario, int periodo, String tieAnali){
         boolean blnRes = true;     
         String strSql="",nomctA="",numctA="";  
         java.sql.Connection con = null;
       //  resArmarDatDiario = new ArrayList<>(); 

         //BUSCA EL NOMBRE Y NUMERO DE CUENTA
         
        try{      
                con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
                if (con != null) {          
                    stm4 = con.createStatement(); 
                    strSql = "";  
                    strSql += " select  B.ACTINDX,trim(b.ACTDESCR)ACTDESCR,trim(c.ACTNUMST)ACTNUMST from tuval..GL00100 (nolock) AS b \n" +
                            "inner join tuval..GL00105 (nolock) AS c on b.ACTINDX=c.ACTINDX where B.ACTINDX=" + co_cta;
                    System.out.println("InsertaListDiario " + strSql);
                    rstconcta = stm4.executeQuery(strSql);
                    if (rstconcta.next()) {
                        do {
                            nomctA=rstconcta.getString("ACTDESCR");
                            numctA=rstconcta.getString("ACTNUMST");
                        }  while (rstconcta.next());        
                    }
                    
                    if (valor_cta >0){
                        DetalleDiario ArmarDatDiario = new DetalleDiario(
                            co_cta , //rstconf.getInt("co_cta"),
                            objUti.redondear(Double.parseDouble(String.valueOf(valor_cta)), objParSis.getDecimalesMostrar()),
                            tieAnali, //rstconf.getString("tx_conana"),
                            Tip_mov, //rstconf.getString("tx_tipmov"),
                            "", //rst.getString("COLUMN_NAME"), 
                            Tip_Diario, // rstconf.getString("tx_tipdia"),
                            periodo, //rstconf.getInt("ne_ctaper"),   
                            nomctA, 
                            numctA                                     
                           );
                        resArmarDatDiario.add(ArmarDatDiario);   
                        blnRes=true;

                    } 
                    con.close();
                    con = null;
                }
        } 
        catch (Exception e) {
            blnRes = false;
        }         
        return blnRes;     
    } 
     
        private boolean generarDiarioBorrador(Vector vecDatDG,Vector vecDatDP, String strperge, Integer intNePer ) {
                   boolean blnRes = true;     
                   java.sql.Connection con = null;
                   java.sql.Statement stmLoc = null;
                   java.sql.Statement stmLocAux = null;   
                   java.sql.CallableStatement cal;
                   java.sql.ResultSet rstLoc = null;
                   String strSql = "",strSqlsp="",strSqldi="",strSqlpro="";   
                   resArmarDatDiario = new ArrayList<>(); 
                   double totcta= 0;  
                   boolean InserOk= false;   

                    try{           
                           con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

                           if (con != null) {
                               stmLoc = con.createStatement();
                               stmLocAux = con.createStatement();

                               stm = con.createStatement();    
                               stm1 = con.createStatement(); 
                               stm2 = con.createStatement(); 
                               stm3 = con.createStatement(); 
                               stm4 = con.createStatement(); 
                               stm5 = con.createStatement(); 
                               stm6 = con.createStatement(); 
                               stm7 = con.createStatement(); 
                               stm8 = con.createStatement(); 
                               stm9 = con.createStatement(); 
                               stm10 = con.createStatement(); 
                               stm11 = con.createStatement(); 
                               stm12 = con.createStatement(); 

                           if (intNePer ==2){
                           strSql = "";      
                           strSql += " SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'tbm_rolpagexc'\n" +
                                   "  AND DATA_TYPE IN ( 'bigint', 'tinyint', 'decimal', 'numeric', 'float', 'real')"  ;
                           System.out.println("1 " + strSql);
                           rst = stm.executeQuery(strSql);

                              if (rst.next()) {
                               do {

                                   if (!rst.getString("COLUMN_NAME").equals("nd_otringnogra")  || !rst.getString("COLUMN_NAME").equals("nd_imprenpro") || !rst.getString("COLUMN_NAME").equals("nd_otrdes")){
                                    strSql = "";      
                                   strSql += "SELECT sum(isnull("+ rst.getString("COLUMN_NAME") +",0)) total_cta FROM tbm_rolpagexc where co_per="+ strperge +" and ne_per=" +intNePer ;
                                //   System.out.println("2 " + strSql);
                                   rstsum = stm1.executeQuery(strSql);
                                   if (rstsum.next()) {

                                       do {
                                         strSql = "";      
                                         strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  tx_nomcol ='" + rst.getString("COLUMN_NAME") +"'";
                                        // System.out.println("3 " + strSql);
                                         rstconf = stm2.executeQuery(strSql);
                                          if (rstconf.next()) {
                                              do {
                                              String nomctA="",numctA="";
                                              int num_cta= rstconf.getInt("co_cta");    
                                              String tip_mov= rstconf.getString("tx_tipmov"); 
                                              String tip_dia= rstconf.getString("tx_tipdia");  
                                              String tie_conana= rstconf.getString("tx_conana"); 
                                              int ctaper= rstconf.getInt("ne_ctaper"); 

                                              InserOk = InsertaListDiario(num_cta, Double.parseDouble(rstsum.getString("total_cta")),tip_mov, tip_dia, ctaper,tie_conana);

                                              } while (rstconf.next());
                                       }
                                   } while (rstsum.next());   
                                   }  
                                   }
                               } while (rst.next());
                              }

                              //CASOS DE INGRESO MANUAL

                              //OTROS DESCUENTOS 
                              double ctaDebe= 0; 
                              double ctaHaber= 0;
                                 strSql = "";
                                 strSql += "select distinct co_cta from tbm_detotrdes where co_emp=1 and co_per="+ strperge +" and ne_per=" +intNePer   ;//+ " and tx_tipmov='"+tip_mov+"'";
                                 System.out.println("4 " + strSql);
                                 rstOtrDes = stm6.executeQuery(strSql);
                                 if (rstOtrDes.next()) {
                                     do{
                                         //recorro cada una de las cuentas 
                                         int num_ctaotrodes=0;
                                         ctaDebe=0;
                                         ctaHaber=0;
                                         num_ctaotrodes=rstOtrDes.getInt("co_cta");
                                          strSql = "";
                                          strSql += "select co_tra,tx_tipmov,tx_obs,nd_mntcta,co_cta from tbm_detotrdes where co_emp=1 and co_per="+ strperge +" and ne_per=" +intNePer + " and co_cta="+ num_ctaotrodes ;
                                          System.out.println("5 " + strSql);
                                          rstOtrDes1 = stm7.executeQuery(strSql);
                                          if (rstOtrDes1.next()) {
                                              do{
                                                  if (rstOtrDes1.getString("tx_tipmov").equals("D")){
                                                      ctaDebe+= Double.parseDouble(rstOtrDes1.getString("nd_mntcta"));
                                                  }else{  
                                                      ctaHaber+=Double.parseDouble(rstOtrDes1.getString("nd_mntcta"));
                                                  }
                                              }  while (rstOtrDes1.next());  
                                          }    
                                          if (ctaDebe> 0){
                                               //CONSULTO CONFIGURACION DE CTA
                                               strSql = "";      
                                               strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  co_cta =" + num_ctaotrodes ;
                                               System.out.println("6 " + strSql);
                                               rstconf1 = stm3.executeQuery(strSql);
                                               if (rstconf1.next()) {
                                                   InserOk = InsertaListDiario(num_ctaotrodes, ctaDebe,"D", "F", rstconf1.getInt("ne_ctaper"),rstconf1.getString("tx_conana"));
                                               }                                    
                                          }
                                          if (ctaHaber>0){
                                              //CONSULTO CONFIGURACION DE CTA
                                               strSql = "";      
                                               strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  co_cta =" + num_ctaotrodes ;
                                               System.out.println("7 " + strSql);
                                               rstconf2 = stmLoc.executeQuery(strSql);
                                               if (rstconf2.next()) {
                                                   InserOk = InsertaListDiario(num_ctaotrodes, ctaHaber,"H", "F", rstconf2.getInt("ne_ctaper"),rstconf2.getString("tx_conana"));
                                               } 
                                          }
                                     }  while (rstOtrDes.next());    
                                  } 

                                 //OTROS INGRESOS NO GRAVABLES
                                 /*strSql = "";
                                 strSql += "select distinct co_cta from tbm_detotringnogra where co_emp=1 and co_per="+ strperge +" and ne_per=" +intNePer   ;//+ " and tx_tipmov='"+tip_mov+"'";
                                 System.out.println("8 " + strSql);
                                 rstOtIng = stm5.executeQuery(strSql);
                                 if (rstOtIng.next()) {
                                     do{
                                         //recorro cada una de las cuentas 
                                         int num_ctaotrodes=0;
                                         ctaDebe=0;
                                         ctaHaber=0;
                                         num_ctaotrodes=rstOtIng.getInt("co_cta");
                                          strSql = "";
                                          strSql += "select co_tra,tx_tipmov,tx_obs,nd_mntcta,co_cta from tbm_detotringnogra where co_emp=1 and co_per="+ strperge +" and ne_per=" +intNePer + " and co_cta="+ num_ctaotrodes ;
                                          System.out.println("8 " + strSql);
                                          rstOtIng1 = stm8.executeQuery(strSql);
                                          if (rstOtIng1.next()) {
                                              do{
                                                  if (rstOtIng1.getString("tx_tipmov").equals("D")){
                                                      ctaDebe+= Double.parseDouble(rstOtIng1.getString("nd_mntcta"));
                                                  }else{  
                                                      ctaHaber+=Double.parseDouble(rstOtIng1.getString("nd_mntcta"));
                                                  }
                                              }  while (rstOtIng1.next());   
                                          }    
                                          if (ctaDebe> 0){
                                               //CONSULTO CONFIGURACION DE CTA
                                               strSql = "";      
                                               strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  co_cta =" + num_ctaotrodes ;
                                               System.out.println("9 " + strSql);
                                               rstconf3 = stm9.executeQuery(strSql);
                                               if (rstconf3.next()) {
                                                   InserOk = InsertaListDiario(num_ctaotrodes, ctaDebe,"D", "F", rstconf3.getInt("ne_ctaper"),rstconf3.getString("tx_conana"));
                                               }                                    
                                          }
                                          if (ctaHaber>0){
                                              //CONSULTO CONFIGURACION DE CTA
                                               strSql = "";      
                                               strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  co_cta =" + num_ctaotrodes ;
                                               System.out.println("10 " + strSql);
                                               rstconf4 = stm10.executeQuery(strSql);
                                               if (rstconf4.next()) {
                                                   InserOk = InsertaListDiario(num_ctaotrodes, ctaHaber,"H", "F", rstconf4.getInt("ne_ctaper"),rstconf4.getString("tx_conana"));
                                               } 
                                          } 
                                     }  while (rstOtIng.next());    
                                  }*/
                                 
                                 // OTROS INGRESOS NO GRAVABLES
                                strSql = "";
                                strSql += "SELECT "
                                       + "    d.co_cta, "
                                       + "    d.total_cta, "
                                       + "    COALESCE(man.tx_conana, fija.tx_conana) AS tx_conana, "
                                       + "    d.tx_tipmov AS tx_tipmov, "
                                       + "    COALESCE(man.tx_tipdia, 'F') AS tx_tipdia, "
                                       + "    COALESCE(man.ne_ctaper, fija.ne_ctaper, " + intNePer + ") AS ne_ctaper "
                                       + "FROM "
                                       + "( "
                                       + "    SELECT "
                                       + "        co_cta, "
                                       + "        UPPER(LTRIM(RTRIM(tx_tipmov))) AS tx_tipmov, "
                                       + "        SUM(ISNULL(nd_mntcta, 0)) AS total_cta "
                                       + "    FROM tbm_detotringnogra "
                                       + "    WHERE co_emp = 1 "
                                       + "      AND co_per = " + strperge + " "
                                       + "      AND ne_per = " + intNePer + " "
                                       + "    GROUP BY co_cta, UPPER(LTRIM(RTRIM(tx_tipmov))) "
                                       + "    HAVING SUM(ISNULL(nd_mntcta, 0)) <> 0 "
                                       + ") d "
                                       + "INNER JOIN tbm_plactapregendia p "
                                       + "    ON p.co_cta = d.co_cta "
                                       + "   AND p.st_reg = 'A' "
                                       + "OUTER APPLY "
                                       + "( "
                                       + "    SELECT TOP 1 "
                                       + "        m.co_cta, "
                                       + "        UPPER(LTRIM(RTRIM(m.tx_conana))) AS tx_conana, "
                                       + "        UPPER(LTRIM(RTRIM(m.tx_tipdia))) AS tx_tipdia, "
                                       + "        m.ne_ctaper "
                                       + "    FROM tbr_ctamangendia m "
                                       + "    WHERE m.co_cta = d.co_cta "
                                       + "      AND m.ne_ctaper = " + intNePer + " "
                                       + "      AND UPPER(LTRIM(RTRIM(m.tx_origen))) = 'OTRINGNOGRA' "
                                       + "      AND UPPER(LTRIM(RTRIM(m.st_reg))) = 'A' "
                                       + "    ORDER BY m.fe_ing DESC "
                                       + ") man "
                                       + "OUTER APPLY "
                                       + "( "
                                       + "    SELECT TOP 1 "
                                       + "        c.co_cta, "
                                       + "        UPPER(LTRIM(RTRIM(c.tx_conana))) AS tx_conana, "
                                       + "        c.ne_ctaper "
                                       + "    FROM tbr_ctacolgendia c "
                                       + "    WHERE c.co_cta = d.co_cta "
                                       + "      AND c.ne_ctaper = " + intNePer + " "
                                       + "    ORDER BY c.fe_ing DESC "
                                       + ") fija "
                                       + "WHERE man.co_cta IS NOT NULL "
                                       + "   OR fija.co_cta IS NOT NULL";
                                System.out.println("OTROS INGRESOS NO GRAVABLES: " + strSql);

                                rstOtIng = stm5.executeQuery(strSql);
                                
                                int intFilasOtIng = 0;
                                
                                while (rstOtIng.next()) {

                                    
                                    intFilasOtIng++;

                                    System.out.println(
                                        "OTRINGNOGRA -> Cuenta: " + rstOtIng.getInt("co_cta")
                                        + " | Total: " + rstOtIng.getDouble("total_cta")
                                        + " | TipMov: " + rstOtIng.getString("tx_tipmov")
                                        + " | TipDia: " + rstOtIng.getString("tx_tipdia")
                                        + " | ConAna: " + rstOtIng.getString("tx_conana")
                                        + " | NeCtaPer: " + rstOtIng.getInt("ne_ctaper")
                                    );
                                    
                                    InserOk = InsertaListDiario(
                                        rstOtIng.getInt("co_cta"),
                                        rstOtIng.getDouble("total_cta"),
                                        rstOtIng.getString("tx_tipmov"),
                                        rstOtIng.getString("tx_tipdia"),
                                        rstOtIng.getInt("ne_ctaper"),
                                        rstOtIng.getString("tx_conana")
                                    );
                                }
                                 
                                System.out.println("Total filas otros ingresos no gravables: " + intFilasOtIng); 

                                 // IMPUESTO A RENTA PROYECTADA
                                   strSql = "";
                                   strSql += " select SUM(nd_mnt) nd_mnt from tbm_ingimprenrolpag where co_per ="+ strperge +" and ne_per= " +intNePer + " HAVING SUM(nd_mnt) IS NOT NULL";    
                                   System.out.println("11 " + strSql);
                                   rstCtaImpRen = stm11.executeQuery(strSql);
                                   if (rstCtaImpRen.next()) { 
                                          strSql = "";
                                          strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where ne_ctaper=" + intNePer + " and  co_cta =190";
                                          System.out.println("12 " + strSql);
                                          rstCtaImpRen1 = stm12.executeQuery(strSql);
                                          if (rstCtaImpRen1.next()) {
                                              InserOk = InsertaListDiario(190,  Double.parseDouble(rstCtaImpRen.getString("nd_mnt")),"H", "F", rstCtaImpRen1.getInt("ne_ctaper"),rstCtaImpRen1.getString("tx_conana"));
                                          }
                                   }  

                           }else if (intNePer ==1){
                               vec1quin=new Vector();  
                               vec1quin.clear(); 
                               String tipo="";

                               double valorTipo=0;
                               totcta=0;

                                   strSql = "";      
                                   strSql += "select (sum(isnull(nd_comant,0)) + sum(isnull(nd_antqui,0))) - (sum(isnull(nd_imprenant,0)) + sum(isnull(nd_vacliq,0))) total from tbm_rolpagexc where co_per="+ strperge +" and ne_per=" +intNePer ;
                                   rstsum = stm1.executeQuery(strSql);
                                   if (rstsum.next()) {
                                       do { 
                                           strSql = "";  
                                           strSql += "select co_cta,tx_nomcol,tx_conana,tx_tipmov,trim(tx_tipdia) tx_tipdia,ne_ctaper from tbr_ctacolgendia where   ne_ctaper=" + intNePer ;
                                           rstconf = stm2.executeQuery(strSql);
                                           if (rstconf.next()) {
                                               do {
                                                   totcta=0;
                                                   String nomctA="",numctA="";
                                                   int num_cta= rstconf.getInt("co_cta");    
                                                   String tip_mov= rstconf.getString("tx_tipmov");  
                                                   String nom_col= rstconf.getString("tx_nomcol"); 


                                                      strSql = "";  
                                                strSql += " select  B.ACTINDX,trim(b.ACTDESCR)ACTDESCR,trim(c.ACTNUMST)ACTNUMST from tuval..GL00100 (nolock) AS b \n" +
                                                        "inner join tuval..GL00105 (nolock) AS c on b.ACTINDX=c.ACTINDX where B.ACTINDX=" + num_cta;
                                                rstconcta = stm4.executeQuery(strSql);
                                                if (rstconcta.next()) {
                                                    do {
                                                        nomctA=rstconcta.getString("ACTDESCR");
                                                        numctA=rstconcta.getString("ACTNUMST");
                                                    }  while (rstconcta.next());        
                                                }
                                                     DetalleDiario ArmarDatDiario = new DetalleDiario(
                                                      rstconf.getInt("co_cta"),
                                                      objUti.redondear(Double.parseDouble(String.valueOf(rstsum.getDouble("total"))), objParSis.getDecimalesMostrar()),
                                                      rstconf.getString("tx_conana"),
                                                      rstconf.getString("tx_tipmov"),
                                                      rstconf.getString("tx_nomcol"), 
                                                      rstconf.getString("tx_tipdia"),
                                                      rstconf.getInt("ne_ctaper"),   
                                                      nomctA, 
                                                      numctA                                     
                                              );
                                              resArmarDatDiario.add(ArmarDatDiario);  
                                               }while (rstconf.next());   
                                           }                                    
                                       } while (rstsum.next());   
                                   }
                           }

                           stm.close();
                           stm=null;
                           stm1.close();
                           stm1=null;
                           stm2.close();
                           stm2=null;
                           stm3.close();
                           stm3=null;
                           stm4.close();
                           stm4=null;
                           stm5.close();
                           stm5=null;
                           stm6.close();
                           stm6=null;
                           stm7.close();
                           stm7=null;
                           stm8.close();
                           stm8=null;
                           stm9.close();
                           stm9=null;
                           stm10.close();
                           stm10=null;
                           stm11.close();
                           stm11=null;
                           stm12.close();
                           stm12=null;
                           stmLoc.close();
                           stmLoc=null;
                           
                           strSqldi="";
                           strSqlpro="";
                           vecDatDG = new Vector();
                           vecDatDP = new Vector();

                           Collections.sort(resArmarDatDiario, (d1, d2) -> d1.getTipMovi().compareTo(d2.getTipMovi()));

                           for (int j = 0; j < resArmarDatDiario.size(); j++) {
                               DetalleDiario detalle = resArmarDatDiario.get(j); 
                               int coCta = detalle.getCocta();  
                               String tipmov = detalle.getTipMovi();  
                               double montocta=detalle.getTotcta();                    
                               String tx_tipdia = detalle.getTipDia() == null ? "" : detalle.getTipDia();  
                               String nomcta=detalle.getNomcta();                    
                               String numcta=detalle.getNumcta();                    

                               if (tx_tipdia.equals("F")){

                                   vecRegDG = new Vector();
                                   vecRegDG.add(INT_TBL_DAT_LINEA_DG, "");
                                   vecRegDG.add(INT_DAT_DG_COD_CTA, coCta);  
                                   vecRegDG.add(INT_DAT_DG_NOM_CTA,nomcta);     
                                   vecRegDG.add(INT_DAT_DG_NUM_CTA, numcta); 
                                   if(tipmov.equals("D")){
                                       vecRegDG.add(INT_DAT_DG_DEBE, montocta);
                                       vecRegDG.add(INT_DAT_DG_HABER, 0); 
                                   }else if(tipmov.equals("H")){  
                                       vecRegDG.add(INT_DAT_DG_DEBE, 0);
                                       vecRegDG.add(INT_DAT_DG_HABER, montocta); 
                                   }       
                                   vecDatDG.add(vecRegDG);
                               //   armar la cadena para el diario
                            //   strSqldi +=coCta+ ","+ tipmov + ","+montocta + "|";
                             //  System.out.println("Diario General: "+strSqldi);

                               }
                               if (tx_tipdia.equals("P")){

                                   vecRegDP = new Vector();
                                   vecRegDP.add(INT_TBL_DAT_LINEA_DP, "");
                                   vecRegDP.add(INT_DAT_DP_COD_CTA,coCta);  
                                   vecRegDP.add(INT_DAT_DP_NOM_CTA,nomcta);     
                                   vecRegDP.add(INT_DAT_DP_NUM_CTA, numcta); 
                                   if(tipmov.equals("D")){
                                   vecRegDP.add(INT_DAT_DP_DEBE, montocta);
                                   vecRegDP.add(INT_DAT_DP_HABER, 0); 
                                   }else if(tipmov.equals("H")){   
                                       vecRegDP.add(INT_DAT_DP_DEBE, 0);
                                       vecRegDP.add(INT_DAT_DP_HABER, montocta); 
                                   }  
                                   vecDatDP.add(vecRegDP);
                                 //  strSqlpro +=coCta+ ","+ tipmov + ","+montocta + "|";
                             //  System.out.println("Diario Provicion: "+strSqlpro);
                               }
                           }    
                           objTblModDG.setData(vecDatDG);
                           tbldiaGen.setModel(objTblModDG);   
                           objTblTot.calcularTotales(); 

                           objTblModDP.setData(vecDatDP);
                           tbldiaprov.setModel(objTblModDP);  
                           objTblTotp.calcularTotales(); 

                           con.close();
                           con = null;
                       }

                    }  catch (Exception e) {
                       blnRes = false;
                   }         
                    return blnRes;
                }
       /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        spnDG = new javax.swing.JScrollPane();
        tbldiaGen = new javax.swing.JTable();
        spnTotDG = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spnDP = new javax.swing.JScrollPane();
        tbldiaprov = new javax.swing.JTable();
        spnTotDP = new javax.swing.JScrollPane();
        tblTot1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnGuarda = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setPreferredSize(new java.awt.Dimension(1500, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(700, 900));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panDat.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setPreferredSize(new java.awt.Dimension(450, 400));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        tbldiaGen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDG.setViewportView(tbldiaGen);

        jPanel5.add(spnDG, java.awt.BorderLayout.CENTER);

        spnTotDG.setPreferredSize(new java.awt.Dimension(454, 20));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotDG.setViewportView(tblTot);

        jPanel5.add(spnTotDG, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Diario General Nomina");
        jPanel3.add(jLabel2, java.awt.BorderLayout.NORTH);

        panDat.add(jPanel3);

        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 400));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Diario Provisional (BBSS)");
        jPanel4.add(jLabel1, java.awt.BorderLayout.NORTH);

        tbldiaprov.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDP.setViewportView(tbldiaprov);

        jPanel4.add(spnDP, java.awt.BorderLayout.CENTER);

        spnTotDP.setPreferredSize(new java.awt.Dimension(454, 20));

        tblTot1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotDP.setViewportView(tblTot1);

        jPanel4.add(spnTotDP, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        panDat.add(jPanel2);

        jPanel1.add(panDat, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(320, 50));
        jPanel6.setLayout(new java.awt.BorderLayout());

        btnGuarda.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnGuarda.setText("Guardar");
        btnGuarda.setPreferredSize(new java.awt.Dimension(90, 23));
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
            }
        });
        jPanel7.add(btnGuarda);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel7.add(butCerr);

        jPanel6.add(jPanel7, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
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

        jPanel6.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

     
    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        // TODO add your handling code here:
        if (ValidarValoresAsientos()){
        if(!btnGuardar){
        Connection con=null;
        try
        {
            
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0)
                {
                    con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());          
                    if (con != null)
                    {
                        con.setAutoCommit(false);
                        if (bntGuardar(con,CodPeriodo,strNePeri))
                        {
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            
                        }
                        else
                        {
                            mostrarMsgInf("Error al guardar.");
                        }
                        con.close();
                        con = null;
                    }
                }
            
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        }else{                         
            mostrarMsgInf("El asiento borrador ya se encuentra generado para este periodo, no se aceptan cambios.");
        }}
    }//GEN-LAST:event_btnGuardaActionPerformed
  
    private boolean ValidarValoresAsientos(){
    boolean blnRes=true;
    double totalDebeGP=0;
    double totalHaberGP=0;
    double totalDebePP=0;
    double totalHaberPP=0;
    
    for (int v=0;v<objTblModDG.getRowCountTrue();v++){
          Object objmntDebe = objTblModDG.getValueAt(v, INT_DAT_DG_DEBE);
          String mntDebe = (objmntDebe != null) ? objmntDebe.toString() : ""; 
            
          Object objmntHaber = objTblModDG.getValueAt(v, INT_DAT_DG_HABER);
          String mntHaber = (objmntHaber != null) ? objmntHaber.toString() : ""; 
         
          totalDebeGP+=Double.parseDouble(mntDebe);
          totalHaberGP+=Double.parseDouble(mntHaber);
    }
    
    for (int v=0;v<objTblModDP.getRowCountTrue();v++){
        Object objmntDebePP = objTblModDP.getValueAt(v, INT_DAT_DP_DEBE);
        String mntDebePP = (objmntDebePP != null) ? objmntDebePP.toString() : ""; 
            
        Object objmntHaberPP = objTblModDP.getValueAt(v, INT_DAT_DP_HABER);
        String mntHaberPP = (objmntHaberPP != null) ? objmntHaberPP.toString() : ""; 
        
        totalDebePP+=Double.parseDouble(mntDebePP);
        totalHaberPP+=Double.parseDouble(mntHaberPP);
        
    } 
     BigDecimal totalDebeGPRedondeado = new BigDecimal(totalDebeGP).setScale(2, RoundingMode.HALF_UP);
     BigDecimal totalHaberGPRedondeado = new BigDecimal(totalHaberGP).setScale(2, RoundingMode.HALF_UP);
     
     BigDecimal totalDebePPRedondeado = new BigDecimal(totalDebePP).setScale(2, RoundingMode.HALF_UP);
     BigDecimal totalHaberPPRedondeado = new BigDecimal(totalHaberPP).setScale(2, RoundingMode.HALF_UP);
     
     int resultadoDG =-1; 
     int resultadoDP =-1;

     resultadoDG=totalDebeGPRedondeado.compareTo(totalHaberGPRedondeado);
     resultadoDP=totalDebePPRedondeado.compareTo(totalHaberPPRedondeado);

    //if (totalDebeGPRedondeado!=totalHaberGPRedondeado){
    if (resultadoDG!=0) //si bota 0 es igual, si te bota 1 es menor 
    {
        blnRes=false;
        mostrarMsgInf("El asiento General esta descuadrado");

    }
    // if (totalDebePP!=totalHaberPP){
    if (resultadoDP!=0) //si bota 0 es igual, si te bota 1 es menor 
    {
        blnRes=false;
        mostrarMsgInf("El asiento Provision esta descuadrado");

    }
    
    return blnRes;
    }
    private boolean bntGuardar(Connection con,String co_periodo,String ne_periodo){
        boolean blnRes=true;   
        int tipomov=0,co_reg=1; 
          strSQL="";
        double montocta=0;
        
        ///elimina datos para volverlos a guardar
         strSQL+= " delete from tbm_diagenbor where  co_per="+co_periodo+" and ne_per="+ne_periodo+" ;\n ";
                //+ " delete from tbm_cabotringnogra where co_emp=1 and  co_per=" +strperiodo +" and ne_per=" +intNePer +" ;" ;
        
        for (int v=0;v<objTblModDG.getRowCountTrue();v++){
            tipomov=0;montocta=0;
            Object objcta = objTblModDG.getValueAt(v, INT_DAT_DG_COD_CTA);
            String co_cta = (objcta != null) ? objcta.toString() : ""; 
            
            Object objmntDebe = objTblModDG.getValueAt(v, INT_DAT_DG_DEBE);
            String mntDebe = (objmntDebe != null) ? objmntDebe.toString() : ""; 
            
            Object objmntHaber = objTblModDG.getValueAt(v, INT_DAT_DG_HABER);
            String mntHaber = (objmntHaber != null) ? objmntHaber.toString() : ""; 
            
            if (Double.parseDouble(mntDebe) >0){
                tipomov=0;
                montocta=Double.parseDouble(mntDebe);
            }else  if (Double.parseDouble(mntHaber) >0){
                tipomov=1;
                montocta=Double.parseDouble(mntHaber);
            }
                 strSQL+=" insert into tbm_diagenbor (co_per,ne_per,co_reg,co_cta,tx_tipmov,nd_mntcta,tx_tipdia,fe_ing,co_usring)values(" +
                       co_periodo+","+ ne_periodo+ ","+co_reg+ ","+ co_cta + "," +tipomov + ","+ montocta + ",'F',getdate()," + objParSis.getCodigoUsuario() +");\n";      
           
            // int coCta =;  
        co_reg++;
        }
        co_reg=1;
        for (int v=0;v<objTblModDP.getRowCountTrue();v++){
            tipomov=0;montocta=0;

            Object objcocta = objTblModDP.getValueAt(v, INT_DAT_DP_COD_CTA);
            String co_cta = (objcocta != null) ? objcocta.toString() : ""; 
            
            Object objmntDebe = objTblModDP.getValueAt(v, INT_DAT_DP_DEBE);
            String mntDebe = (objmntDebe != null) ? objmntDebe.toString() : ""; 
            
            Object objmntHaber = objTblModDP.getValueAt(v, INT_DAT_DP_HABER);
            String mntHaber = (objmntHaber != null) ? objmntHaber.toString() : ""; 
            
            if (Double.parseDouble(mntDebe) >0){
                tipomov=0;
                montocta=Double.parseDouble(mntDebe);
            }else  if (Double.parseDouble(mntHaber) >0){
                tipomov=1;
                montocta=Double.parseDouble(mntHaber);
            }
                 strSQL+=" insert into tbm_diagenbor (co_per,ne_per,co_reg,co_cta,tx_tipmov,nd_mntcta,tx_tipdia,fe_ing,co_usring)values(" +
                       co_periodo+","+ ne_periodo+ ","+co_reg+ ","+ co_cta + "," +tipomov + ","+ montocta + ",'P',getdate()," + objParSis.getCodigoUsuario() +");\n";      
           
            // int coCta =;  
        co_reg++;
        }
         strSQL+=" update tbm_seggendiarolpag set st_revdiabor='S',fe_revdiabor=getdate() where co_emp=1 and co_per="+ co_periodo +" and ne_per=" + ne_periodo + ";\n";
          
         if(strSQL.length()>0){
                if(!insertarCadena(con)){
                     blnRes=false;
                }
            }     
         this.setVisible(false);

    return blnRes;
    }
    
    private boolean ejecutaUpdate(java.sql.Connection conExt){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmLoc.executeUpdate(strSQL);
                blnRes=true;
                strSQL="";
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
     private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
        private boolean insertarCadena(Connection conLoc){
        boolean blnRes=false;
       // java.sql.Connection conLoc;
        try{
           // conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                if(ejecutaUpdate(conLoc)){
                    conLoc.commit();
                    blnRes=true;
                    System.out.println("OK");
                }
                else{
                    conLoc.rollback();                    
                }
                strSQL="";
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
     
    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        // TODO add your handling code here:
        exitForm(null);
    }//GEN-LAST:event_butCerrActionPerformed

   private void exitForm(javax.swing.event.InternalFrameEvent evt) {                          
    String strTit, strMsg;
    //try{
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }
   
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton butCerr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDG;
    private javax.swing.JScrollPane spnDP;
    private javax.swing.JScrollPane spnTotDG;
    private javax.swing.JScrollPane spnTotDP;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTot1;
    private javax.swing.JTable tbldiaGen;
    private javax.swing.JTable tbldiaprov;
    // End of variables declaration//GEN-END:variables
}
