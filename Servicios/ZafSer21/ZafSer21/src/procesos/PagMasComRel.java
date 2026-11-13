/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesos;

import Librerias.ZafAsiDia.ZafAsiDia2;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Sistemas05
 */
public class PagMasComRel {
    
    private static final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
    private static final int INT_TBL_CHKSEL=1; // SELECCION  DE FILA
    private static final int INT_TBL_BUTCLI=2; // BUTON PARA BUSCAR DOCUMENTO
    private static final int INT_TBL_CODCLI=3; // CODIGO CLIENTE
    private static final int INT_TBL_NOMCLI=4; // NOMBRE CLIENTE
    private static final int INT_TBL_CODEMP=5; // CODIGO EMPRESA
    private static final int INT_TBL_CODLOC=6; // CODIGO DEL LOCAL
    private static final int INT_TBL_CODTID=7; // CODIGO TIPO DE DOCUMENTO
    private static final int INT_TBL_CODDOC=8; // CODIGO DOCUMENTAL
    private static final int INT_TBL_CODREG=9; // CODIGO REGISTRO
    private static final int INT_TBL_DCTIPDOC=10; // DESCRIPCION CORTA TIPO DOCUMENTO
    private static final int INT_TBL_DLTIPDOC=11; // DESCRIPCION LARGA TIPO DOCUMENTO
    private static final int INT_TBL_NUMDOC=12; // NUMERO DOCUMENTO
    private static final int INT_TBL_FECDOC=13; // FECHA DOCUMENTO
    private static final int INT_TBL_DIACRE=14; // DIA DE CREDITO
    private static final int INT_TBL_FECVEN=15; // FECHE VENCIMIENTO DOCUMENTO
    private static final int INT_TBL_PORRET=16; // PORCENTAJE DE RETENCION
    private static final int INT_TBL_VALDOC=17; // VALOR DOCUMENTO
    private static final int INT_TBL_VALPEN=18; // VALOR PENDIENTE
    private static final int INT_TBL_ABONO=19; //  ABONO
    private static final int INT_TBL_CODREGAAJU=20; // CODIGO REGISTRO DE PAGO
    private static final int INT_TBL_ABONOORI=21; //  ABONO ORIGEN
    private static final int INT_TBL_CODTIPDOC=22; // CODIGO TIPO DE DOCUMENTO
    private static final int INT_TBL_DCOTIPDOC=23; // DESCRIPCION CORTA DE TIPO DOCUMENTO
    private static final int INT_TBL_BUTTIPDOC=24;  // BOTON DE BUSQUEDA DE  TIPO DOCUMENTO
    private static final int INT_TBL_DLATIPDOC=25; // DESCRIPCION LARGA TIPO DE DOCUMENTO
    private static final int INT_TBL_CODBAN=26;  // CODIGO DEL BANCO
    private static final int INT_TBL_DSCBAN=27;  // DESCRIPCION CORTA DEL BANCO
    private static final int INT_TBL_BUTBAN=28;  // BOTON PARA BUSCAR BANCOS
    private static final int INT_TBL_NOMBAN=29;  // NOMBRE DEL BANCO
    private static final int INT_TBL_NUMCTA=30;  // NUMERO DE CUENTA DEL BANCO
    private static final int INT_TBL_BUTRET=31; // BUTON PARA BUSCAR RETENCION
    private static final int INT_TBL_IDEEMI=32;  // IDENTIFICACION DEL EMISOR
    private static final int INT_TBL_NUMCHQ=33;  // NUMERO DEL CHEQUE/RETENCION
    private static final int INT_TBL_FECVENCHQ=34;  // FECHA VENCIMIENTO EL CHEQUE
    private static final int INT_TBL_VALCHQ=35;  // VALOR DEL CHEQUE
    private static final int INT_TBL_CODCTADEB=36;
    private static final int INT_TBL_DCOCTADEB=37;
    private static final int INT_TBL_NOMCTADEB=38;
    private static final int INT_TBL_CODCTAHAB=39;
    private static final int INT_TBL_DCOCTAHAB=40;
    private static final int INT_TBL_NOMCTAHAB=41;
    private static final int INT_TBL_NUMSECDOC=42;  // NUMERO DE SERIE DEL DOCUMENTO
    private static final int INT_TBL_NUMAUTSRI=43;  // NUMERO DE AUTORIZACION DEL SRI
    private static final int INT_TBL_FECCATDOC=44;  // FECHA CADUCIDA DEL DOCUMENTO
    private static final int INT_TBL_CODSRIDOC=45;  // CODIGO DE SRI
    
    
    private static final int INT_TIPO_DOCUMENTO_PAGO_MASIVO=61;  


    Connection cnx;    
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;  
    
    Vector vecEmpresas=new Vector();
    Vector vecClientes=new Vector();
    
    int intCodCtaDeb=0,intCodCtaHab=0;    
    private Vector vecDetDiario;
    private String strTxtCodCtaDeb="",strNomCtaDeb="",strTxtCodCtaHab="",strNomCtaHab="",strNumDoc="";        
    private ZafAsiDia2 objAsiDia;    
    
    private Vector vecCab=new Vector();
    

    private static final int  INT_MENU_PAGO_MASIVO=488;
    
    BigDecimal valDoc=BigDecimal.ZERO;
    private ZafUtil objUti; /**/    
    
    private String strMensajesErrorCorreo="";    
    
    
    
    public PagMasComRel(Connection cnx) {
        objParSis=new ZafParSis();
        objParSis.cargarAniosCreadosSistema(1);
        objParSis.setCodigoUsuario(1);
        objUti = new ZafUtil();
        objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia2(this.objParSis);        
        this.cnx=cnx;
        cargarEmpReg();
        configurarTabla(); 
    }

    public PagMasComRel(ZafParSis objParSis,Connection cnx) {
        try{
            objParSis= (ZafParSis)objParSis.clone();
            this.cnx=cnx;
            objParSis.cargarAniosCreadosSistema(1);
            objParSis.setCodigoUsuario(1);
            objUti = new ZafUtil();
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia2(this.objParSis);                    
            cargarEmpReg();
            configurarTabla(); 
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }        
    }
    
    
    public void abrirCon(){
        try{
           cnx=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           cnx.setAutoCommit(false);
        }catch(java.sql.SQLException  Evt){  
            //objUti.mostrarMsgErr_F1(this, Evt); 
        }
    }    
    

    public void abrirCon2(){
        try{
            cnx = java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_31_Ene_2019","postgres", "*szlsc*");        
            cnx.setAutoCommit(false);

        }catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }    
    
    
    public void inicializar(){
    
        Vector vecData=new Vector();
        objTblMod.setData(vecData);//Datos vacios
        valDoc=BigDecimal.ZERO;
        strNumDoc="";
    }    
    
    
    public PagMasComRel(ZafParSis objParSis) {
        try{
            this.objParSis=(ZafParSis)objParSis.clone();
            objUti = new ZafUtil();  
            configurarTabla();            
            abrirCon();    
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }    
    
    private void configurarTabla() {

        boolean blnRes=false;
        vecCab=new Vector();
        
         //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
        vecCab.add(INT_TBL_NOMCLI,"Nom.CLi");
        vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
        vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
        vecCab.add(INT_TBL_CODREG,"Cod.Reg");
        vecCab.add(INT_TBL_DCTIPDOC,"Des.Cor");
        vecCab.add(INT_TBL_DLTIPDOC,"Des.Lar");
        vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
        vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBL_DIACRE,"Dia.Cre");
        vecCab.add(INT_TBL_FECVEN,"Fec.ven");
        vecCab.add(INT_TBL_PORRET,"Por.ret");
        vecCab.add(INT_TBL_VALDOC,"Val.Doc");
        vecCab.add(INT_TBL_VALPEN,"Val.Pen");
        vecCab.add(INT_TBL_ABONO,"Abono");
        vecCab.add(INT_TBL_CODREGAAJU,"");
        vecCab.add(INT_TBL_ABONOORI,"");
        vecCab.add(INT_TBL_CODTIPDOC,"");
        vecCab.add(INT_TBL_DCOTIPDOC,"DC.Tip.Doc");
        vecCab.add(INT_TBL_BUTTIPDOC,"");
        vecCab.add(INT_TBL_DLATIPDOC,"DL.Tip.Doc");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Num.Cta.");
        vecCab.add(INT_TBL_BUTRET,"");
        vecCab.add(INT_TBL_IDEEMI,"Ide.Emi.");
        vecCab.add(INT_TBL_NUMCHQ,"Num.Chq.");
        vecCab.add(INT_TBL_FECVENCHQ,"Fec.Ven.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_CODCTADEB,"deb");
        vecCab.add(INT_TBL_DCOCTADEB,"deb");
        vecCab.add(INT_TBL_NOMCTADEB,"deb");
        vecCab.add(INT_TBL_CODCTAHAB,"hab");
        vecCab.add(INT_TBL_DCOCTAHAB,"hab");
        vecCab.add(INT_TBL_NOMCTAHAB,"hab");
        vecCab.add(INT_TBL_NUMSECDOC,"Num.Ser.Doc");
        vecCab.add(INT_TBL_NUMAUTSRI,"Num.Aut.Sri");
        vecCab.add(INT_TBL_FECCATDOC,"Fec.Cad.Doc");
        vecCab.add(INT_TBL_CODSRIDOC,"Cod.Sri");

        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
      
    }
    
    
    private String getCodSri(int intCodTipRet, int intCodEmp)
    {
        String strRes = "", strSQL;
        java.sql.Statement stm;
        java.sql.ResultSet rst;

        try
        {
           stm = cnx.createStatement();
           strSQL =  "SELECT distinct tx_codsri ";
           strSQL += "FROM   tbm_polret ";
           strSQL += "WHERE  st_reg = 'A' and tx_codsri is not null and fe_vighas is null ";
           strSQL += "       and co_emp = " + intCodEmp;
           strSQL += "       and co_tipret = " + intCodTipRet;
           rst = stm.executeQuery(strSQL);

           if (rst.next())
              strRes = rst.getString("tx_codsri") == null? "" :rst.getString("tx_codsri");

           rst.close();
           rst = null;
           stm.close();
           stm = null;
        } //try

        catch(Exception e)
        {  
           strRes = "";
        }

        return strRes;
    } //Funcion getCodSri()
    
    private int getCodTipRet(BigDecimal bdePorRet)
    {
        int intRes = 0, intCodTipRet;
      
        try
        {  
            intCodTipRet = 0;
         
            if (bdePorRet.equals(new BigDecimal("30.00")))
               intCodTipRet = 4; //Retención al IVA 30%
            else if (bdePorRet.equals(new BigDecimal("70.00")))
               intCodTipRet = 5; //Retención al IVA 70%
            else if (bdePorRet.equals(new BigDecimal("100.00")))
               intCodTipRet = 6; //Retención al IVA 100%
            else if (bdePorRet.equals(new BigDecimal("10.00")))
               intCodTipRet = 14; //Retención al IVA 10%
            else if (bdePorRet.equals(new BigDecimal("20.00")))
               intCodTipRet = 15; //Retención al IVA 20%
            else if (bdePorRet.equals(new BigDecimal("1.00")))
               intCodTipRet = 1; //Retención en la fuente 1%
            else if (bdePorRet.equals(new BigDecimal("2.00")))
               intCodTipRet = 8; //Retención en la fuente 2%
            else if (bdePorRet.equals(new BigDecimal("8.00")))
               intCodTipRet = 3; //Retención en la fuente 8%

            intRes = intCodTipRet;
      }
      catch(Exception e)
      {  
         intRes = 0;
      }
      
      return intRes;
    } //Funcion getCodTipRet()  

    private int obtenerCodLocRela(int intCodEmp){
        int intCodLoc=0;
        if(intCodEmp==1 || intCodEmp==4){
            intCodLoc=11;
        }else if(intCodEmp==2){
            intCodLoc=5;
        }
        return intCodLoc;
    }    
    
    
    private void consultarDatos(Connection cnx, int intCodEmp, int intCodCli, String strCliLar){
                
        Vector vecData=new Vector();
        String strJoinFecha="";
        

        int intUltDia,intMes,intAnio, intMesAnterior;        
        Calendar calFec=Calendar.getInstance();  
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intMes=calFec.get(Calendar.MONTH);       
        intAnio=calFec.get(Calendar.YEAR);
        calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        /*if(intMesAnterior==0){ // si es enero es el año anterior            
            intAnio--;
        }*/
        intMesAnterior++;

        String strSqlDat="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, \n" +
                        "       a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, \n" +
                        "       a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, \n" +
                        "       a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a4.tx_desLar AS a4_tx_desLar, \n" +
                        "       a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq \n" +
                        " FROM  tbm_cabMovInv AS a1 \n" +
                        " INNER JOIN tbm_cli AS b2 \n" +
                        " ON (a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli) \n" +
                        " INNER JOIN tbm_pagMovInv AS a2 \n" +
                        " ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n" +
                        " INNER JOIN tbm_cabTipDoc AS a3 \n" +
                        " ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n" +
                        " LEFT OUTER JOIN tbm_var AS a4 \n" +
                        " ON (a2.co_banChq=a4.co_reg) \n" +
                        " LEFT OUTER JOIN tbm_cabForPag AS a5 \n" +
                        " ON (a1.co_emp=a5.co_emp AND a1.co_forPag=a5.co_forPag) \n" +
                        " WHERE a1.co_emp="+intCodEmp+" AND a1.co_cli="+intCodCli+" \n" +
                        " and a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" +
                        " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') \n" +
                        " AND a3.ne_mod IN (2, 4) \n" +
                        " AND (a2.mo_pag+a2.nd_abo)<>0 \n" +
                        " AND a2.nd_porret=0 \n"+
                        " AND a1.co_loc="+obtenerCodLocRela(intCodEmp)+" AND a1.co_tipdoc=2\n" +
                        " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";                        
                        
        try{
         
            Statement stm=cnx.createStatement();
            ResultSet rst=stm.executeQuery(strSqlDat);
            BigDecimal bgdPorRet=BigDecimal.ZERO; 
            int intTipRet=0;
            String strAux;

            while(rst.next())
            {
                Vector vecReg = new Vector();

                vecReg.add(INT_TBL_LINEA,"");
                vecReg.add(INT_TBL_CHKSEL,true);
                vecReg.add(INT_TBL_BUTCLI,"");
                vecReg.add(INT_TBL_CODCLI,intCodCli);
                vecReg.add(INT_TBL_NOMCLI,strCliLar);
                vecReg.add(INT_TBL_CODEMP, intCodEmp );
                vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc") );
                vecReg.add(INT_TBL_CODTID, rst.getString("co_tipDoc") );
                vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc"));
                vecReg.add(INT_TBL_CODREG, rst.getString("co_reg") );
                vecReg.add(INT_TBL_DCTIPDOC, rst.getString("tx_desCor") );
                vecReg.add(INT_TBL_DLTIPDOC, rst.getString("tx_desLar"));                    
                vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numDoc") );
                vecReg.add(INT_TBL_FECDOC,rst.getString("fe_doc"));
                vecReg.add(INT_TBL_DIACRE, rst.getString("ne_diaCre") );
                vecReg.add(INT_TBL_FECVEN, rst.getString("fe_ven") );
                vecReg.add(INT_TBL_PORRET, rst.getString("nd_porRet") );
                vecReg.add(INT_TBL_VALDOC, rst.getString("mo_pag") );
                vecReg.add(INT_TBL_VALPEN, rst.getString("nd_pen"));
                vecReg.add(INT_TBL_ABONO, rst.getBigDecimal("mo_pag").abs());
                vecReg.add(INT_TBL_CODREGAAJU, "");
                vecReg.add(INT_TBL_ABONOORI, "");
                vecReg.add(INT_TBL_CODTIPDOC, "");
                vecReg.add(INT_TBL_DCOTIPDOC, "");
                vecReg.add(INT_TBL_BUTTIPDOC, "");
                vecReg.add(INT_TBL_DLATIPDOC, "");
                vecReg.add(INT_TBL_CODBAN, rst.getString("co_banChq") );
                vecReg.add(INT_TBL_DSCBAN, "" );
                vecReg.add(INT_TBL_BUTBAN, "" );
                vecReg.add(INT_TBL_NOMBAN, rst.getString("a4_tx_desLar") );
                vecReg.add(INT_TBL_NUMCTA, rst.getString("tx_numCtaChq"));
                vecReg.add(INT_TBL_BUTRET, "");
                vecReg.add(INT_TBL_IDEEMI, "");               
                vecReg.add(INT_TBL_NUMCHQ, rst.getString("tx_numChq"));

                vecReg.add(INT_TBL_FECVENCHQ,"");
                //vecReg.add(INT_TBL_VALCHQ,rst.getString("nd_monChq"));  // VALOR DEL CHEQUE
                vecReg.add(INT_TBL_VALCHQ,rst.getString("mo_pag"));
                vecReg.add(INT_TBL_CODCTADEB,"");
                vecReg.add(INT_TBL_DCOCTADEB,"");
                vecReg.add(INT_TBL_NOMCTADEB,"");
                vecReg.add(INT_TBL_CODCTAHAB,"");
                vecReg.add(INT_TBL_DCOCTAHAB,"");
                vecReg.add(INT_TBL_NOMCTAHAB,"");
                //vecReg.add(INT_TBL_NUMSECDOC, rst.getString("tx_numSerRet"));
                vecReg.add(INT_TBL_NUMSECDOC, "");
                vecReg.add(INT_TBL_NUMAUTSRI,""); // NUMERO DE AUTORIZACION DEL SRI
                vecReg.add(INT_TBL_FECCATDOC,""); // FECHA CADUCIDA DEL DOCUMENTO

                bgdPorRet=new BigDecimal(rst.getString("nd_porRet"));
                if (bgdPorRet!=null && bgdPorRet.compareTo(BigDecimal.ZERO)>0){
                    intTipRet=getCodTipRet(bgdPorRet);
                    strAux = getCodSri(intTipRet, intCodEmp);                
                    vecReg.add(INT_TBL_CODSRIDOC,strAux);                
                    
                }else{
                    vecReg.add(INT_TBL_CODSRIDOC,""); 
                }

                vecData.add(vecReg); 
            }
           
            objTblMod.setData(vecData);

            calculaTotMonAbo();    
            rst.close();
            rst=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }    
    
    public void calculaTotMonAbo(){
        double dblMonChq=0, dblValDoc=0;
        String strValDoc="";
        int intTipMov=0;
        try{
            for(int i=0; i<objTblMod.getRowCount(); i++){
                if( ( (objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

                    strValDoc=(objTblMod.getValueAt(i, INT_TBL_VALDOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALDOC).toString()));

                    dblValDoc=objUti.redondear(strValDoc, 4);
                    if(dblValDoc > 0 ){
                        intTipMov=-1;
                    }else{
                        intTipMov=1;
                    }
                    valDoc= valDoc.add(new BigDecimal(((objTblMod.getValueAt(i, INT_TBL_ABONO)==null)?"0":(objTblMod.getValueAt(i, INT_TBL_ABONO).toString()))).abs().multiply(new BigDecimal(intTipMov)));
                    //dblMonChq += Math.abs( Double.parseDouble(((objTblMod.getValueAt(i, INT_TBL_ABONO)==null)?"0":(objTblMod.getValueAt(i, INT_TBL_ABONO).toString()))) ) * intTipMov;

                }
            }
            valDoc=valDoc.setScale(2, RoundingMode.HALF_UP);
            generaAsiento(valDoc.doubleValue());

        }catch(Exception e) {   
            e.printStackTrace();
        }
    }   
    
    
    /**
     * Metodo que sirve para cargar las empresas relacionadas.
     */
    private void cargarEmpReg(){
        Statement stmQry=null;
        ResultSet rs=null;
        String strQry="select co_emp,tx_nom from tbm_emp where tx_tipper='C' and tx_nom not like 'NOSITOL%' order by co_emp";
        Vector vecRegDataEmp=null;
        try{
            stmQry=cnx.createStatement();
            rs=stmQry.executeQuery(strQry);
            vecEmpresas=new Vector();            
            while(rs.next()){
                vecRegDataEmp=new Vector();
                vecRegDataEmp.add(rs.getInt("co_emp"));
                vecRegDataEmp.add(rs.getString("tx_nom"));
                vecEmpresas.add(vecRegDataEmp);
            }
            vecRegDataEmp=new Vector();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                rs.close();
                stmQry.close();
                rs=null;
                stmQry=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }     
    
    private void cargarListadoClientes(Connection cnx,int intCodEmp, String strEmp){
        String strQry=" select co_cli, tx_nom from tbm_cli where co_emp="+intCodEmp+" and co_empgrp=0 and tx_nom not like '"+strEmp+"%' and tx_nom not like 'NOSITOL%'";
        
        ResultSet rsQry=null;
        Vector vecReg=null;
        try{
            Statement stm=cnx.createStatement();
            rsQry=stm.executeQuery(strQry);
            vecClientes.clear();
            while(rsQry.next()){
                vecReg=new Vector();
                vecReg.add(rsQry.getInt("co_cli"));
                vecReg.add(rsQry.getString("tx_nom"));
                vecClientes.add(vecReg);
            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }  
    
    private void obtenerNumDoc1(Connection cnx,int intEmp){
        
        String strSql="";        
        
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;   
        
        try{
            if(objParSis.getCodigoUsuario()==1){
                strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
                " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
                " ,doc.tx_natdoc, doc.st_meringegrfisbod " +
                " FROM tbr_tipdocprg as menu " +
                " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
                " WHERE   menu.co_emp = "+intEmp + " and " +
                " menu.co_loc = " + obtenerCodLocRela(intEmp)+" AND  " +
                " menu.co_mnu = "+INT_MENU_PAGO_MASIVO+" AND  menu.st_reg = 'S' ";
            }        
            stmLoc=cnx.createStatement();
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()){
                strNumDoc=((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc"));
            }
            rstLoc.close();
            stmLoc.close();
            stmLoc=null;
            rstLoc=null;        
        }catch(Exception ex){
            ex.printStackTrace();
        }        
    }    
 
    
    private void obtenerCtasDebHabRel(Connection cnx,int intCodEmp, String strNomCli){        
        int intCodLocRe=obtenerCodLocRela(intCodEmp);
        strNomCli=strNomCli.substring(0, strNomCli.length()-5).toLowerCase();
        Statement stm=null;
        ResultSet rs=null;
        String strQryCta="";
        try{
            strQryCta=" SELECT DISTINCT  c.co_Cta, c.tx_codCta, c.tx_desLar as Cuenta \n" +
                        " FROM tbm_CabDia as a \n" +
                        " INNER JOIN tbm_DetDia as b ON (a.co_Emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipDoc=b.co_TipDoc and a.co_dia=b.co_dia)\n" +
                        " INNER JOIN tbm_plaCta as c ON (b.co_emp=c.co_emp and b.co_cta=c.co_cta)\n" +
                        " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLocRe+"  AND a.co_tipDoc=228 AND  c.tx_desLar like  '%Relacionadas%' AND lower(c.tx_deslar) like '%"+strNomCli+"%' AND fe_dia BETWEEN '2018-02-01' AND '2018-02-28'\n" +
                        " ORDER BY c.tx_desLar";
            stm=cnx.createStatement();
            rs=stm.executeQuery(strQryCta);
            while(rs.next()){
                intCodCtaDeb=rs.getInt("co_Cta");
                strTxtCodCtaDeb=rs.getString("tx_codCta");
                strNomCtaDeb=rs.getString("Cuenta");
                intCodCtaHab=rs.getInt("co_Cta");
                strTxtCodCtaHab=rs.getString("tx_codCta");
                strNomCtaHab=rs.getString("Cuenta");
            }
            rs.close();
            rs=null;
            stm.close();
            stm=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }    
    }    
    
    public boolean procesar(){        
        boolean booRet=true;
        int intCodEmp=0;
        String strNomEmp="",strSQL="",strNomCli="";
        ResultSet rst=null;
        Statement stm=null;
        Vector vecDat;
        String strAux;
        boolean booErr=false;
        Vector vecActTip;
        int intPorRet=0, intCodTipDoc=0;
        String strCorreosUsers="";
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        
        try{
            for(int i=0;i<vecEmpresas.size();i++){                
                Vector vecReg=(Vector)vecEmpresas.get(i);
                intCodEmp=(Integer)vecReg.get(0);
                strNomEmp=(String)vecReg.get(1);
                int intCodLoc=obtenerCodLocRela(intCodEmp);
                cargarListadoClientes(cnx,intCodEmp, strNomEmp);
                Iterator itCli=vecClientes.iterator();
                
                /*int intCodLocPre=obtenerLocPred(cnx, intCodEmp);
                obtenerNatTipDoc(cnx, intCodEmp, intCodLocPre);*/
                while(itCli.hasNext()){
                    Vector vecActCli=(Vector)itCli.next();
                    int intProv=(Integer)vecActCli.get(0);
                    strNomCli=(String)vecActCli.get(1);
                    obtenerCtasDebHabRel(cnx, intCodEmp, strNomCli);
                    consultarDatos(cnx, intCodEmp, intProv, strNomCli);   
                    obtenerNumDoc1(cnx, intCodEmp);
                    booRet=insertarPagosMasivos(intCodEmp, cnx);
                    if(booRet){
                        cnx.commit();
                    }else{
                        cnx.rollback();
                        booErr=true;//Existio un problema salir del while
                        break;
                    }                  
                }
                if(booErr){//SALIR DEL FOR
                    booRet=false;
                    break;
                }
            }
        }
        catch(Exception ex){
            booRet=false;
            ex.printStackTrace();
            strMensajesErrorCorreo+="Error en metodo Procesar Pagos masivos: "+ex.getMessage(); 
        }finally{
            if(strMensajesErrorCorreo.length()>0){
                strCorreosUsers="sistemas5@tuvalsa.com";
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - ERRORES EN PAGOS MASIVOS - ", strMensajesErrorCorreo);
            }        
        }
        return booRet;
    }     
    
    private String obtenerMesLetras(int intMesCalendar){
        String strMes="";
        switch(intMesCalendar){
            case 0:
            {
                strMes="Enero";
                break;
            }
          case 1:
            {
                strMes="Febrero";
                break;
            }
          case 2:
            {
                strMes="Marzo";
                break;
            }
          case 3:
            {
                strMes="Abril";
                break;
            }
          case 4:
            {
                strMes="Mayo";
                break;
            }            
          case 5:
            {
                strMes="Junio";
                break;
            }
          case 6:
            {
                strMes="Julio";
                break;
            }            
          case 7:
            {
                strMes="Agosto";
                break;
            }            
          case 8:
            {
                strMes="Septiembre";
                break;
            }            
          case 9:
            {
                strMes="Octubre";
                break;
            }            
          case 10:
            {
                strMes="Noviembre";
                break;
            } 
          case 11:
            {
                strMes="Diciembre";
                break;
            }
        }
        return strMes;
    }    
    
    
    private boolean insertarCab(java.sql.Connection conn, int intCodEmp,int intCodDoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strFecDoc="";
        int intUltDia,intMes,intAnio, intMesAnterior; 
        try{
            if(conn!=null){
               stmLoc=conn.createStatement();
               
               //************  PERMITE SABER SI EL NUMERO DE recepción ESTA DUPLICADO  *****************/
                strSql ="select count(ne_numdoc1) as num from tbm_cabpag WHERE " +
                        " co_emp="+intCodEmp+" and co_loc="+obtenerCodLocRela(intCodEmp)+" " +
                        "and co_tipdoc="+INT_TIPO_DOCUMENTO_PAGO_MASIVO+" and ne_numdoc1="+strNumDoc;
                 rstLoc = stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     if(rstLoc.getInt("num") >= 1 ){
                           //JOptionPane oppMsg = new JOptionPane();
                           String strTit, strMsg;
                           strTit="Mensaje del sistema Zafiro";
                           strMsg=" No. de Cobro ya existe... ?";
                           //JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
                           blnRes=true;
                     }
                 }

                 rstLoc.close();
                 rstLoc=null;
                 if(blnRes) return false;
                 blnRes=false;
               //***********************************************************************************************/

                Calendar calFec=Calendar.getInstance();  
                intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
                intMes=calFec.get(Calendar.MONTH);       
                intAnio=calFec.get(Calendar.YEAR);
                calFec.add(Calendar.MONTH, -1);
                intMesAnterior=calFec.get(Calendar.MONTH);
                intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
                intAnio=calFec.get(Calendar.YEAR);
                /*if(intMesAnterior==0){ // si es enero es el año anterior            
                    intAnio--;
                } */             

               strSql="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 " +
               " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_regrep ) "+
               " VALUES("+intCodEmp+", "+obtenerCodLocRela(intCodEmp)+", "+INT_TIPO_DOCUMENTO_PAGO_MASIVO+", "+intCodDoc+" " +
               " ,'"+obtenerFecUltDiaMesSinComillas()+"', "+(strNumDoc.trim().equals("")?null:strNumDoc)+", "+(null)+", '"+null+"', 'Pago masivo generado por el sistema para liquidar valores pendientes entre empresas relacionadas "+obtenerMesLetras(intMesAnterior)+" "+intAnio+"' " +
               " ,"+(valDoc.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:valDoc)+", 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+","+objParSis.getCodigoUsuario()+" " +
               " ,'"+objParSis.getNombreComputadoraConDirIP()+"', "+INT_MENU_PAGO_MASIVO+", 'I' ) ; ";

               stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }catch(java.sql.SQLException Evt) { 
            blnRes=false;
        }catch(Exception  Evt){ 
            blnRes=false;
        }
        return blnRes;
    }    
    
    private String obtenerFecUltDiaMesSinComillas(){
        int intUltDia,intMes,intAnio, intMesAnterior;
        String strFechUltDia="";
        Calendar calFec=Calendar.getInstance();  
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intMes=calFec.get(Calendar.MONTH);       
        intAnio=calFec.get(Calendar.YEAR);
        calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        /*if(intMesAnterior==0){ // si es enero es el año anterior            
            intAnio--;
        }*/
        intMesAnterior++;    
        strFechUltDia=intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia;
        return strFechUltDia;
    }    
    
    private boolean insertarDetPagMas(java.sql.Connection conn, int intCodEmp,int intCodDoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strNumCta="",strNumChq=""/*, strFecDoc=""*/;
        String strCodTipCon="", strCodBan="", strFevVenChq="", strValDoc="";
        int intCodRegDet=0;
        double dblValDoc=0;
        int intTipMov=1;
        StringBuffer strSqlInsDet=new StringBuffer("");
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                
                strCodTipCon=String.valueOf(INT_TIPO_DOCUMENTO_PAGO_MASIVO);
               

                for(int i=0; i<objTblMod.getRowCount(); i++){
                    if( ((objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
                        if( !((objTblMod.getValueAt(i, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

                            strCodEmp=objTblMod.getValueAt(i, INT_TBL_CODEMP).toString();
                            strCodLoc=objTblMod.getValueAt(i, INT_TBL_CODLOC).toString();
                            strCodTipDocRec=objTblMod.getValueAt(i, INT_TBL_CODTID).toString();
                            strCodDoc=objTblMod.getValueAt(i, INT_TBL_CODDOC).toString();
                            strCodReg=objTblMod.getValueAt(i, INT_TBL_CODREG).toString();
                            strAbono=(objTblMod.getValueAt(i, INT_TBL_ABONO)==null?"0":(objTblMod.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_ABONO).toString()));
                            strNumCta=(objTblMod.getValueAt(i, INT_TBL_NUMCTA)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMCTA).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMCTA).toString()));
                            strNumChq=(objTblMod.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString()));
                            strCodBan=(objTblMod.getValueAt(i, INT_TBL_CODBAN)==null?null:(objTblMod.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?null:objTblMod.getValueAt(i, INT_TBL_CODBAN).toString()));
                            strFevVenChq=(objTblMod.getValueAt(i, INT_TBL_FECVENCHQ)==null?"":(objTblMod.getValueAt(i, INT_TBL_FECVENCHQ).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_FECVENCHQ).toString()));

                            strValDoc=(objTblMod.getValueAt(i, INT_TBL_VALDOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALDOC).toString()));

                            dblValDoc=objUti.redondear(strValDoc, 4);
                            if(dblValDoc > 0 ){
                              intTipMov=-1;
                            }else{
                                intTipMov=1;
                            }

                            if(!(strFevVenChq.equals("")))
                              strFevVenChq = objUti.formatearFecha( strFevVenChq,"dd/MM/yyyy","yyyy/MM/dd");

                            intCodRegDet++;
                            strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
                            " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq )" +
                            " VALUES( "+intCodEmp+", "+obtenerCodLocRela(intCodEmp)+", "+INT_TIPO_DOCUMENTO_PAGO_MASIVO+", "+intCodDoc+" " +
                            " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", ("+strAbono+"*"+intTipMov+") , 'A','I', "+strCodTipCon+" " +
                            " ,"+strCodBan+", '"+strNumCta+"', '"+strNumChq+"', '"+obtenerFecUltDiaMesSinComillas() +"' " +
                            " , "+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+"  ) ; ");

                            strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + ("+strAbono+"*"+intTipMov+"), " +
                            " tx_numctachq='"+strNumCta+"', tx_numchq='"+strNumChq+"',  nd_monchq=("+strAbono+"*"+intTipMov+"), fe_recchq='"+obtenerFecUltDiaMesSinComillas()+"', " +
                            " fe_venchq="+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+",  " +
                            " st_regrep='M' WHERE " +
                            " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
                            " and co_reg="+strCodReg+" ; ");

                        }
                    }
                }

                if(intCodRegDet > 0 ){
                    //System.out.println("--> "+ strSqlInsDet.toString()  );
                    stmLoc.executeUpdate(strSqlInsDet.toString());
                    blnRes=true;
                }

                stmLoc.close();
                stmLoc=null;

        }
        }catch(java.sql.SQLException Evt){ 
            blnRes=false;  
        }catch(Exception  Evt){ 
            blnRes=false;  
        }
        return blnRes;
    }    
    
    
    public boolean insertarPagosMasivos(int intCodEmp,Connection cnx){
        
        Statement stmLoc=null;
        ResultSet rstLoc=null;
        String strSql="";
        int intCodDoc=0;
        boolean blnRes=false;
        int intCodLoc=0;
        StringBuffer strSqlInsDet;
        
        try{
            if(cnx!=null){
                stmLoc=cnx.createStatement();
                intCodLoc=obtenerCodLocRela(intCodEmp);

                strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as codoc  FROM tbm_cabpag WHERE " +
                " co_emp="+intCodEmp+" AND co_loc="+obtenerCodLocRela(intCodEmp)+" AND co_tipdoc="+INT_TIPO_DOCUMENTO_PAGO_MASIVO;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodDoc = rstLoc.getInt("codoc");
                rstLoc.close();
                rstLoc=null;
                strSqlInsDet=new StringBuffer();

                if(insertarCab(cnx, intCodEmp,intCodDoc)){
                    if(insertarDetPagMas(cnx, intCodEmp,intCodDoc)){
                        if(objAsiDia.insertarDiario(cnx, intCodEmp,intCodLoc, INT_TIPO_DOCUMENTO_PAGO_MASIVO, String.valueOf(intCodDoc), strNumDoc, objUti.parseDate(obtenerFecUltDiaMesSinComillas(),"yyyy-MM-dd") )) {
                            blnRes=true;
                            inicializar();
                        }else {
                            blnRes=false;
                            strMensajesErrorCorreo+="Error en insertarDiario Pagos Masivos";
                        }
                    }else{
                       blnRes=false;
                       strMensajesErrorCorreo+="Error en insertarDetPagMas Pagos Masivos";
                    }
                }else{
                   blnRes=false;
                   strMensajesErrorCorreo+="Error en insertarCab Pagos Masivos";
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            strMensajesErrorCorreo+="Insertar Pagos masivos: "+ex.getMessage();                    
            blnRes=false;
        }
        return blnRes;
    }    
    
    private void generaAsiento(double dblValDoc) throws Exception{
        try{
            objAsiDia.inicializar();
            int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
            int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
            int INT_VEC_NUMCTA = 2; //2) Numero de la cuenta (Preimpreso).
            int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
            int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
            int INT_VEC_DEBE   = 5; //5) Debe.
            int INT_VEC_HABER  = 6; //6) Haber.
            int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
            int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null



            if (vecDetDiario==null) 
                vecDetDiario = new java.util.Vector();
            else 
                vecDetDiario.removeAllElements();


            java.util.Vector vecReg;

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, (intCodCtaDeb==0?null:intCodCtaDeb ) );
            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaDeb );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaDeb );
            if(objParSis.getCodigoMenu()==488){
                if(dblValDoc > 0 ){
                   vecReg.add(INT_VEC_DEBE,  new Double(0));
                   vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
                }else{
                  vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
                  vecReg.add(INT_VEC_HABER, new Double(0));
                }
            }else{
                if(dblValDoc > 0 ){
                   vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
                   vecReg.add(INT_VEC_HABER, new Double(0));
                }else{
                  vecReg.add(INT_VEC_DEBE,  new Double(0));
                  vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
                }
            }
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);

            vecDetDiario.add(vecReg);
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);

               //vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaHab ));
               //if (strCodCtaHab.equals(""))
               //    this.MensajeInf("No se ha encontrado la cuenta del HABER. Por favor no olvide ingresarla.");

            vecReg.add(INT_VEC_CODCTA,(intCodCtaHab==0?null:intCodCtaHab));

            vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaHab );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strNomCtaHab );
            if(objParSis.getCodigoMenu()==488){
                if(dblValDoc > 0 ){
                  vecReg.add(INT_VEC_DEBE,  new Double( Math.abs( dblValDoc ) ));
                  vecReg.add(INT_VEC_HABER, new Double(0));
                }else{
                  vecReg.add(INT_VEC_DEBE,  new Double(0));
                  vecReg.add(INT_VEC_HABER, new Double( Math.abs( dblValDoc ) ));
                }
            }else{
                if(dblValDoc > 0 ){
                  vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
                  vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
                }else{
                  vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
                  vecReg.add(INT_VEC_HABER, new Double( 0 ));
                }
            }
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);
        }catch(Exception e) {   
            e.printStackTrace();
        }
    }    
    
    
}
