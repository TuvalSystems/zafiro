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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static procesos.EmiRetComRel.cnx;

/**
 * Esta clase realiza la Recepcion de las retenciones del 1% y 10%
 * @author Sistemas05
 */
public class RetRecComRel {
    
    Vector vecClientes;
    Vector vecEmpresas;
    Vector vecTiposDocu=new Vector();
    ZafTblMod objTblMod;
    ZafParSis objParSis;
    private ZafUtil objUti; /**/
    private Vector vecDetDiario;
    Connection cnx;
    
    
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

    
    int intCodCtaDeb=0,intCodCtaHab=0;    
    private ZafAsiDia2 objAsiDia;    
    
    private String strTxtCodCtaDeb="",strNomCtaDeb="",strTxtCodCtaHab="",strNomCtaHab="",strNumDoc="";    
    private String strMensajesErrorCorreo="";
    
    private BigDecimal valDoc=BigDecimal.ZERO;
    
    
    private static final int INT_MNU_RET=1648;
    
    public RetRecComRel(Connection con){
        objParSis=new ZafParSis();
        objParSis.cargarAniosCreadosSistema(1);
        objParSis.setCodigoUsuario(1);
        objUti = new ZafUtil();
        objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia2(this.objParSis);        
        this.cnx=con;
        cargarEmpReg();
        ConfigurarTabla();      
    }
    
    public RetRecComRel(ZafParSis objParSis,Connection con){
        try{
            objParSis= (ZafParSis)objParSis.clone();
            //objParSis=new ZafParSis();
            this.cnx=con;
            objParSis.cargarAniosCreadosSistema(1);
            objParSis.setCodigoUsuario(1);
            objUti = new ZafUtil();
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia2(this.objParSis);                    
            cargarEmpReg();
            ConfigurarTabla(); 
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }    
    
    
    /**
     * Metodo que sirve para cargar las empresas relacionadas.
     */
    private void cargarEmpReg(){
        Statement stmQry=null;
        ResultSet rs=null;
        String strQry="select co_emp,tx_nom from tbm_emp where tx_tipper='C' and tx_nom not like 'NOSITOL%' order by co_emp";
        //String strQry="select co_emp,tx_nom from tbm_emp where tx_tipper='C' and tx_nom not like 'NOSITOL%' and co_emp in(1) order by co_emp";
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
    
    public void abrirCon(){
        try{
            cnx = DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_31_Ene_2019","postgres", "*szlsc*");        
            cnx.setAutoCommit(false);
        }catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }    
    
    /**
     * Método que carga los clientes relacionadas de una empresa x
     * @param cnx Conexión a la BD.
     * @param intCodEmp Código de empresa.
     * @param strNomEmp Nombre de empresa.
     */
    private void cargarCliReg(Connection cnx,int intCodEmp, String strNomEmp){
        Statement stmQry=null;
        ResultSet rs=null;
        String strQry="select co_cli,tx_nom, tx_ide, tx_dir from tbm_cli where co_emp="+intCodEmp+ " and tx_nom not like '"+strNomEmp+"%' and tx_nom not like 'NOSITOL%' and co_empgrp=0";
        //String strQry="select co_cli,tx_nom, tx_ide, tx_dir from tbm_cli where co_emp="+intCodEmp+ " and tx_nom not like '"+strNomEmp+"%' and tx_nom  like 'CASTEK%' and co_empgrp=0";        
        Vector vecDatCli=null;
        try{
            stmQry=cnx.createStatement();
            rs=stmQry.executeQuery(strQry);
            vecClientes=new Vector();
            while(rs.next()){
                vecDatCli=new Vector();
                vecDatCli.add(rs.getInt("co_cli"));
                vecDatCli.add(rs.getString("tx_nom"));
                vecDatCli.add(rs.getString("tx_ide"));
                vecDatCli.add(rs.getString("tx_dir"));
                vecClientes.add(vecDatCli);
            }

        }catch(SQLException ex){
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
    
    
    private int obtenerCodEmpCliente(String strNomEmpCli){
        int intCodEmp=0;
        if(strNomEmpCli.equals("TUVAL S.A.")){
            intCodEmp=1;
        }else if(strNomEmpCli.equals("CASTEK S.A.")){
            intCodEmp=2;
        }else if(strNomEmpCli.equals("DIMULTI S.A.")){
            intCodEmp=4;
        }
        return intCodEmp;
    }    
    
    private int obtenerCodLocRela(int intCodEmp){
        int intCodLoc=0;
        if(intCodEmp==1 || intCodEmp==4){
            intCodLoc=11;
        }else if(intCodEmp==2){
            intCodLoc=5;
        }
        return intCodLoc;
    }  
    
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
    }    
    
    private String getCodSri(int intCodEmp,int intCodTipRet, Connection CONN)
    {
      String strRes = "", strSQL;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         stm = CONN.createStatement();
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
    }    
    
    private boolean validarRetRecvsRetEmi(int intCodEmp,String strNomEmp,int intCodCli, String strNomCli, int intPorRet,Connection cnx,String strJoinFecha)
    {
        
        int intCodEmpEmi=obtenerCodEmpCliente(strNomCli);
        int intCodLocEmi=obtenerCodLocRela(intCodEmpEmi);
        
        boolean booRet=true;
        

        String strQry="SELECT  r.co_emprel ,r.co_locrel ,r.co_tipdocrel ,r.co_docrel, cabpag.co_tipdoc \n" +
          " FROM tbm_cabMovInv AS x1 \n" +
          " INNER JOIN tbm_cli AS b2 \n" +
          " ON (x1.co_emp=b2.co_emp AND x1.co_cli=b2.co_cli) \n" +
          " INNER JOIN tbm_pagMovInv AS a2 \n" +
          " ON (x1.co_emp=a2.co_emp AND x1.co_loc=a2.co_loc AND x1.co_tipDoc=a2.co_tipDoc AND x1.co_doc=a2.co_doc) \n" +
          " INNER JOIN tbm_cabTipDoc AS a3 \n" +
          " ON (x1.co_emp=a3.co_emp AND x1.co_loc=a3.co_loc AND x1.co_tipDoc=a3.co_tipDoc) \n" +
          " LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) \n" +
          " LEFT OUTER JOIN tbm_cabForPag AS a5 ON (x1.co_emp=a5.co_emp AND x1.co_forPag=a5.co_forPag) \n" +
          " \n" +
          " INNER JOIN tbr_detmovinv as r ON (r.co_emp=x1.co_emp and r.co_loc=x1.co_loc and r.co_tipdoc=x1.co_tipdoc and r.co_doc=x1.co_doc and r.co_reg=1) \n" +
          " INNER JOIN tbm_pagmovinv as pagoc on (pagoc.co_emp=r.co_emprel and pagoc.co_loc=r.co_locrel and pagoc.co_tipdoc=r.co_tipdocrel and pagoc.co_doc=r.co_docrel and pagoc.nd_porret=1) \n" +
          " inner join tbm_detpag as detpag on detpag.co_emp=pagoc.co_emp and detpag.co_locpag=pagoc.co_loc and detpag.co_tipdocpag=pagoc.co_tipdoc and detpag.co_docpag=pagoc.co_doc and detpag.co_regpag=pagoc.co_reg \n" +
          " inner join tbm_cabpag as cabpag on cabpag.co_emp=detpag.co_emp and cabpag.co_loc=detpag.co_loc and cabpag.co_tipdoc=detpag.co_tipdoc and cabpag.co_doc=detpag.co_doc\n" +
          "\n" +
          " WHERE x1.co_emp="+intCodEmp+" AND x1.co_cli="+intCodCli+" \n" +
          " AND x1.fe_doc BETWEEN "+ strJoinFecha+

          " AND x1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') \n" +
          " AND a3.ne_mod IN (1, 3) \n" +
          " AND a2.nd_porRet>0 and a2.nd_porret="+intPorRet+" and pagoc.nd_porret="+intPorRet+"\n" +
          " AND (a2.mo_pag+a2.nd_abo)<>0  \n" +
          " AND cabpag.tx_numAutFacEle IS NOT NULL \n" +
          "\n" +
          "and  not exists(\n" +
          "\n" +
          "\n" +
          " SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc\n" +
          " FROM tbm_cabMovInv AS a1 \n" +
          " LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n" +
          " LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n" +
          " LEFT OUTER JOIN tbm_cabTipRet AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet) \n" +
          " WHERE a1.co_emp="+intCodEmpEmi+"\n" +
          " AND a1.co_loc="+intCodLocEmi+"\n" +
          " AND a1.co_cli=(select CO_CLI from tbm_cli where co_emp="+intCodEmpEmi+" and tx_nom like '"+strNomEmp+"%')\n"+                
          " AND a1.st_reg IN ('A','R','C','F') \n" +
          " AND a1.fe_doc between "+strJoinFecha+                  
          " and tx_natdoc='I' \n" +
         // " --AND (a2.mo_pag+a2.nd_abo)>0 \n" + /*NO VA ESTA LINEA PORQUE LA EMISION DE RETENCIONES ABONA EL VALOR DE LA RETENCION*/
          " AND a2.nd_porRet="+intPorRet+"\n" +
          " AND a2.st_reg IN ('A','C') \n" +
          " AND a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL \n" +
          " AND ( case when (length(a2.tx_numAutSri) <> 37 and length(a2.tx_numAutSri) <> 49) then a2.tx_fecCad IS NOT NULL \n" +
          "              else 1 = 1       end ) \n" +
          " AND a2.tx_numChq IS NOT NULL \n" +
          " and r.co_emprel=a1.co_emp and r.co_locrel=a1.co_loc and r.co_tipdocrel=a1.co_tipdoc and r.co_docrel=a1.co_doc \n" +
          ")";
        try{
            Statement stm=cnx.createStatement();
            ResultSet rst=stm.executeQuery(strQry);
            if(rst.next()){
                booRet=false;
            }
            rst.close();
        }catch(SQLException ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;        
    }   
    
    
    private void obtenerCtaHaber(int intCodEmp, int intCodLoc,int intCodCli, Connection con){
        String strSql= " SELECT DISTINCT  b.co_Cta, c.tx_codCta, c.tx_desLar as Cuenta \n" +
                      " FROM tbm_CabDia as a \n" +
                      " INNER JOIN tbm_DetDia as b ON (a.co_Emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipDoc=b.co_TipDoc and a.co_dia=b.co_dia) \n" +
                      " INNER JOIN tbm_plaCta as c ON (b.co_emp=c.co_emp and b.co_cta=c.co_cta) \n" +
                      " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+"  AND a.co_tipDoc=228 \n" +
                      " AND  c.tx_desLar like  '%Relacionadas%' \n" +                      
                      " AND fe_dia BETWEEN '2018-02-01' AND '2018-02-28' \n" ;
        StringBuffer strBuffQry=null;
                      //" ORDER BY c.tx_desLar ";
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.createStatement();
            if(intCodEmp==1  ){
                if(intCodCli==1039){
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%dimulti%' ");
                }else if(intCodCli==603){
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%castek%' ");                
                }
            }else if(intCodEmp==2 ){
                if(intCodCli==2854){
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%tuval%' ");
                }else if(intCodCli==789){
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%dimulti%' ");                
                }
            }else if(intCodEmp==4 ){
                if(intCodCli==3117){//tuval
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%tuval%' ");
                }else if(intCodCli==498){//castek
                    strBuffQry=new StringBuffer(strSql);
                    strBuffQry.append(" AND lower(c.tx_desLar) like '%castek%' ");                
                }
            }
            
            rs=stm.executeQuery(strBuffQry.toString());                
            while(rs.next()){
                strTxtCodCtaHab=rs.getString("tx_codCta");
                strNomCtaHab=rs.getString("Cuenta"); 
                intCodCtaHab=rs.getInt("co_Cta");                
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }    
    
    
    private String obtenerFecUltDiaMes(){
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
        strFechUltDia="'"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'";
        return strFechUltDia;
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
    
    private void consultarDatos(int intCodEmp,String strNomEmp,Connection cnx, int intPorRet, int intCodCli, String strCliLar){
        
        Vector vecData=new Vector();
        String strJoinFecha="";
        int intCantReg=0;
        
        
        int intUltDia,intMes,intAnio, intMesAnterior;        
        Calendar calFec=Calendar.getInstance();  
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intMes=calFec.get(Calendar.MONTH);       
        intAnio=calFec.get(Calendar.YEAR);
        calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        intMesAnterior++;        
        String strSqlDat=""
                + "SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, \n" +
                " a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, \n" +
                " a1.fe_doc, a2.ne_diaCre, a2.fe_ven, round(a2.nd_porRet,2) as nd_porRet, \n" +
                " a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, \n" +
                " a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, \n" +
                " a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, \n" +
                " a2.fe_venChq, a2.nd_monChq , b2.tx_nom, cabpag.ne_numDoc1,cabpag.tx_numAutFacEle, b2.tx_numSerRet\n" +
                " FROM tbm_cabMovInv AS a1 \n" +
                " INNER JOIN tbm_cli AS b2 \n" +
                " ON (a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli) \n" +
                " INNER JOIN tbm_pagMovInv AS a2 \n" +
                " ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n" +
                " INNER JOIN tbm_cabTipDoc AS a3 \n" +
                " ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n" +
                " LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) \n" +
                " LEFT OUTER JOIN tbm_cabForPag AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_forPag=a5.co_forPag) \n" +
                "\n" +
                " INNER JOIN tbr_detmovinv as r ON (r.co_emp=a1.co_emp and r.co_loc=a1.co_loc and r.co_tipdoc=a1.co_tipdoc and r.co_doc=a1.co_doc and r.co_reg=1/*and r.co_loc in(11,5)*/)\n" +
                " INNER JOIN tbm_pagmovinv as pagoc on (pagoc.co_emp=r.co_emprel and pagoc.co_loc=r.co_locrel and pagoc.co_tipdoc=r.co_tipdocrel and pagoc.co_doc=r.co_docrel and pagoc.nd_porret="+intPorRet+") \n"+                
                //" inner join tbm_detpag as detpag on detpag.co_emp=r.co_emprel   and detpag.co_locpag=r.co_locrel  and detpag.co_tipdocpag=r.co_tipdocrel  and detpag.co_docpag=r.co_docrel \n" +
                //" and detpag.co_regpag=r.co_regrel\n" +
                " inner join tbm_detpag as detpag on detpag.co_emp=pagoc.co_emp and detpag.co_locpag=pagoc.co_loc and detpag.co_tipdocpag=pagoc.co_tipdoc and detpag.co_docpag=pagoc.co_doc and detpag.co_regpag=pagoc.co_reg \n"+               
                " inner join tbm_cabpag as cabpag on cabpag.co_emp=detpag.co_emp and cabpag.co_loc=detpag.co_loc and cabpag.co_tipdoc=detpag.co_tipdoc and cabpag.co_doc=detpag.co_doc\n" +
                //" inner join tbm_cabmovinv as facv on (facv.co_emp=r.co_emp and facv.co_loc= r.co_loc and facv.co_tipdoc= r.co_tipdoc and facv.co_doc=r.co_doc)\n" +
                "\n" +
                " WHERE a1.co_emp="+intCodEmp+" AND a1.co_cli="+intCodCli+" \n" +
                //" AND a1.fe_doc BETWEEN '2017-10-01' AND '2017-10-31' \n" +
                //strJoinFecha+
                " and a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" +                
                " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') \n" +
                " AND a3.ne_mod IN (1, 3) \n" +
                " AND a2.nd_porRet>0 and a2.nd_porret="+intPorRet+" and pagoc.nd_porret="+intPorRet+"\n" +
                " AND (a2.mo_pag+a2.nd_abo)<>0  \n"+
                " AND cabpag.tx_numAutFacEle IS NOT NULL \n"+
                " order by a1.ne_numdoc";
        
        try{
         
            Statement stm=cnx.createStatement();
            strJoinFecha="'"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'"; 
            //int intCodEmp,String strNomEmp,int intCodCli, String strNomCli, int intCodTipDoc,Connection cnx,String strJoinFecha
            //if(validarRetRecvsRetEmi(intCodEmp,strNomEmp,intCodCli,strCliLar,intPorRet,cnx, strJoinFecha)){
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
                    //vecReg.add(INT_TBL_CODEMP, objParSis.getCodigoEmpresa() );
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
                    vecReg.add(INT_TBL_NUMCHQ, rst.getString("ne_numDoc1"));

                    vecReg.add(INT_TBL_FECVENCHQ,"");
                    vecReg.add(INT_TBL_VALCHQ,"");  // VALOR DEL CHEQUE
                    vecReg.add(INT_TBL_CODCTADEB,"");
                    vecReg.add(INT_TBL_DCOCTADEB,"");
                    vecReg.add(INT_TBL_NOMCTADEB,"");
                    vecReg.add(INT_TBL_CODCTAHAB,"");
                    vecReg.add(INT_TBL_DCOCTAHAB,"");
                    vecReg.add(INT_TBL_NOMCTAHAB,"");
                    vecReg.add(INT_TBL_NUMSECDOC, rst.getString("tx_numSerRet"));
                    vecReg.add(INT_TBL_NUMAUTSRI,rst.getString("tx_numAutFacEle")); // NUMERO DE AUTORIZACION DEL SRI
                    vecReg.add(INT_TBL_FECCATDOC,""); // FECHA CADUCIDA DEL DOCUMENTO

                    bgdPorRet=new BigDecimal(rst.getString("nd_porRet"));
                    intTipRet=getCodTipRet(bgdPorRet);
                    if(bgdPorRet.compareTo(new BigDecimal(1))==0){
                        strAux="312";
                    }else{
                        strAux = getCodSri(intCodEmp,intTipRet,cnx);                
                    }

                    vecReg.add(INT_TBL_CODSRIDOC,strAux);                
                    vecData.add(vecReg); 


                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            //}
            
            if(!(vecData.size()>0)){
                intCantReg=vecData.size();
                //mostrarMsgInf("No existen registros");
            }else{
                //btnProcesar.setEnabled(true);
                intCantReg=0;
            }
            objTblMod.setData(vecData);
            //tblDat.setModel(objTblMod);        

            //tblDat.requestFocus();
            calculaTotMonAbo();             
            
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    } 
    
    
    private void generaAsiento(double dblValDoc){
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



              if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
                else vecDetDiario.removeAllElements();


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
        }catch(Exception e) {   /*objUti.mostrarMsgErr_F1(this,e); */ 
            e.printStackTrace();
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

                dblMonChq += Math.abs( Double.parseDouble(((objTblMod.getValueAt(i, INT_TBL_ABONO)==null)?"0":(objTblMod.getValueAt(i, INT_TBL_ABONO).toString()))) ) * intTipMov;

          }}
         dblMonChq=objUti.redondear(dblMonChq, 2);
         valDoc=new BigDecimal(dblMonChq).setScale(2, RoundingMode.HALF_UP);

         if((intCodCtaDeb!=0))
           generaAsiento(dblMonChq);
//         else generaAsiento();

       }catch(Exception e) {   
           //objUti.mostrarMsgErr_F1(this,e);  
           e.printStackTrace();
       }
    }    
    
    
    private void cargarListadoTiposDocumentos(Connection CONN_GLO, int intCodEmp){
        ResultSet rsQry=null;
        Vector vecReg;
        String strQry="";
        int intCodLoc=0;
        
        /*PARTE DONDE SE ELIGE EL LOCAL DE LAS RELACIONADAS SEGUN LA EMPRESA*/
        if(intCodEmp==1 || intCodEmp==4){
            intCodLoc=11;
        }else if(intCodEmp==2){
            intCodLoc=5;
        }
        
        strQry="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar " +
        " ,a.co_ctadeb, a1.tx_codcta AS txctadeb, a1.tx_deslar as nomctadeb  " +
        " ,a.co_ctahab, a2.tx_codcta as txctahab, a2.tx_deslar as nomctahab   " +                    
        " ,case when (a.ne_ultdoc+1) is null then 1 else a.ne_ultdoc+1 end as numDoc "+                     
        " from tbr_tipdocprg as b " +
        " left outer join tbm_cabtipdoc as a " +
        " on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
        " LEFT JOIN tbm_placta AS a1 on (a1.co_emp = a.co_emp and a1.co_cta = a.co_ctadeb ) " +
        " LEFT JOIN tbm_placta AS a2 on (a2.co_emp = a.co_emp and a2.co_cta = a.co_ctahab ) " +

        " where   b.co_emp = " + intCodEmp + " and " +
        " b.co_loc = " + intCodLoc   + " and " +
        " b.co_tipdoc in (25,247) ";
        try{
            Statement stm=CONN_GLO.createStatement();
            rsQry=stm.executeQuery(strQry);
            vecTiposDocu.clear();
            while(rsQry.next()){
                vecReg=new Vector();
                vecReg.add(rsQry.getInt("co_tipdoc"));
                vecReg.add(rsQry.getString("tx_desLar"));
                if(rsQry.getString("tx_desCor").equals("RETE")){
                    vecReg.add(1);
                }else if(rsQry.getString("tx_desCor").equals("RETE2%")){
                    vecReg.add(2);
                }else if(rsQry.getString("tx_desCor").equals("RFIV10")){
                    vecReg.add(10);
                }else if(rsQry.getString("tx_desCor").equals("RFIV20")){
                    vecReg.add(20);
                }else if(rsQry.getString("tx_desCor").equals("RFIV30")){
                    vecReg.add(30);
                }else if(rsQry.getString("tx_desCor").equals("RFIV70")){
                    vecReg.add(70);
                }else if(rsQry.getString("tx_desCor").equals("RFIV100")){
                    vecReg.add(100);
                }
                vecReg.add(rsQry.getInt("co_ctadeb"));
                vecReg.add(rsQry.getString("txctadeb"));
                vecReg.add(rsQry.getString("nomctadeb"));
                vecReg.add(rsQry.getInt("co_ctahab"));
                vecReg.add(rsQry.getString("txctahab"));
                vecReg.add(rsQry.getString("nomctahab"));
                vecReg.add(rsQry.getInt("numDoc"));
                vecTiposDocu.add(vecReg);
            }               
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }     
    
    
    private void ConfigurarTabla() {

        boolean blnRes=false;
        Vector vecCab=null;
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
        vecCab.add(INT_TBL_NUMCHQ,"Num.Ret.");
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
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        String strCorreosUsers="";
        try{
            for(int i=0;i<vecEmpresas.size();i++){                
                Vector vecReg=(Vector)vecEmpresas.get(i);
                intCodEmp=(Integer)vecReg.get(0);
                strNomEmp=(String)vecReg.get(1);
                int intCodLoc=obtenerCodLocRela(intCodEmp);
                cargarCliReg(cnx,intCodEmp, strNomEmp);
                Iterator itCli=vecClientes.iterator();
                
                /*int intCodLocPre=obtenerLocPred(cnx, intCodEmp);
                obtenerNatTipDoc(cnx, intCodEmp, intCodLocPre);*/
                while(itCli.hasNext()){
                    Vector vecActCli=(Vector)itCli.next();
                    int intProv=(Integer)vecActCli.get(0);
                    strNomCli=(String)vecActCli.get(1);
                    obtenerCtaHaber(intCodEmp, intCodLoc,intProv, cnx);
                    cargarListadoTiposDocumentos(cnx, intCodEmp);
                    Iterator itTipDoc=vecTiposDocu.iterator();                    
                    while (itTipDoc.hasNext()){
                        vecActTip=(Vector)itTipDoc.next();
                        intPorRet=(Integer)vecActTip.get(2);
                        intCodTipDoc=(Integer)vecActTip.get(0);
                        intCodCtaDeb=(Integer)vecActTip.get(3);
                        strTxtCodCtaDeb=(String)vecActTip.get(4);
                        strNomCtaDeb=(String)vecActTip.get(5);
			strNumDoc=((Integer)vecActTip.get(9)).toString();
                        consultarDatos(intCodEmp, strNomEmp, cnx, intPorRet, intProv, strNomCli);   
                        booRet=insertar(intCodTipDoc, strNumDoc, intCodEmp, cnx);
                        if(booRet){
                            cnx.commit();
                        }else{
                            cnx.rollback();
                            booErr=true;//Existio un problema salir del while
                            break;
                        }
                        
                    }
                    if(booErr){//SALIR DEL while
                        booRet=false;
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
            strMensajesErrorCorreo+="Error en metodo Procesar Retenciones Recibidas: "+ex.getMessage();
        }finally{
            if(strMensajesErrorCorreo.length()>0){
                strCorreosUsers="sistemas5@tuvalsa.com";
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - ERRORES EN RETENCIONES RECIBIDAS - ", strMensajesErrorCorreo);
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
    
    
    private boolean insertarCab(java.sql.Connection conn,int intCodEmp,int intCodTipDoc, int intCodDoc, String strNumDoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strFecDoc="";
        String strAux="";
        int intCodLoc=0;
        int intUltDia,intMes,intAnio, intMesAnterior;        
        try{
            if(conn!=null){
               stmLoc=conn.createStatement();
               
               if(intCodEmp==1 || intCodEmp==4){
                   intCodLoc=11;
               }else if(intCodEmp==2){
                   intCodLoc=5;
               }
               

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
                //intMesAnterior++;               


               //************  PERMITE SABER SI EL NUMERO DE recepción ESTA DUPLICADO  *****************/
                strSql ="select count(ne_numdoc1) as num from tbm_cabpag WHERE " +
                        " co_emp="+intCodEmp+" and co_loc="+intCodLoc+" " +
                        "and co_tipdoc="+intCodTipDoc+" and ne_numdoc1="+strNumDoc;
                 rstLoc = stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     if(rstLoc.getInt("num") >= 1 ){
                           strAux=" No. de Cobro ya existe... ?";
                           strMensajesErrorCorreo+="\n "+strAux+"\n";
                           blnRes=true;
                 }}

                 rstLoc.close();
                 rstLoc=null;
                 if(blnRes){ 
                     return false;
                 }
                 blnRes=false;
               //***********************************************************************************************/


               strSql="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 " +
               " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_regrep ) "+
               " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+" " +
               " ,'"+obtenerFecUltDiaMesSinComillas()+"', "+(strNumDoc.equals("")?null:strNumDoc)+", null, '',' Retenciones recibidas generadas por Sistemas para dar de baja las retenciones recibidas de "+obtenerMesLetras(intMesAnterior)+" "+intAnio +"'"+
               " ,"+(valDoc.compareTo(BigDecimal.ZERO)==0?"0":valDoc)+", 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+","+objParSis.getCodigoUsuario()+" " +
               " ,'"+objParSis.getNombreComputadoraConDirIP()+"', "+INT_MNU_RET+", 'I' ) ; ";

               stmLoc.executeUpdate(strSql);

               stmLoc.close();

               stmLoc=null;
               blnRes=true;
           }
        }catch(java.sql.SQLException evt) { 
             blnRes=false;  
             strMensajesErrorCorreo+=evt.getMessage();
         }catch(Exception  evt2){ 
            blnRes=false;  
            strMensajesErrorCorreo+=evt2.getMessage();
        }
        return blnRes;
    }       
    
    
/**
    * Este metodo se encarga de verificar si todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja.
    * @param con Conexion con la base de datos.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @return true: Indica que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja.
   * <BR>false: En el caso contrario.
   */
   private boolean verificarValRetClientePagMovInv(Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
   {
      boolean blnRes = true;
      String strSQL;
      int i, intConRetDadBaj;
      BigDecimal bdeAux, bdePorRet;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {  if (conn != null)
         {  stmLoc = conn.createStatement();
            strSQL =  "SELECT (mo_pag + nd_abo) as val_pen, nd_porret ";
            strSQL += "FROM   tbm_pagmovinv ";
            strSQL += "WHERE  st_reg in ('A','C') and nd_porret <> 0 ";
            strSQL += "       and co_emp = " + strCodEmp;
            strSQL += "       and co_loc = " + strCodLoc;
            strSQL += "       and co_tipdoc = " + strCodTipDoc;
            strSQL += "       and co_doc = " + strCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            i = 0;
            intConRetDadBaj = 0;

            while (rstLoc.next())
            {  i++;
               bdePorRet = rstLoc.getBigDecimal("nd_porret");
               bdePorRet = objUti.redondearBigDecimal(bdePorRet, objParSis.getDecimalesMostrar());
               bdeAux = rstLoc.getBigDecimal("val_pen");
               bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
               if (bdeAux.equals(new BigDecimal("0.00")))
               {  //Si es cero, significa que dicho valor de Retencion que aparece en tbm_pagMovInv ya fue dado de baja
                  intConRetDadBaj++; //Contador de Retenciones Dadas de Baja
               }
            }
            
            if (i == intConRetDadBaj)
            {  //Si es igual, significa que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja
               blnRes = true;
            }
            else
            {  //Si no es igual, significa que hay valores de Retenciones de los clientes que aparecen en tbm_pagMovInv pendientes en ser dados de baja
               blnRes = false;
            }
         
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion verificarValRetClientePagMovInv()    
    
    private boolean insertarDet(Connection conn,int intCodEmp, int intCodTipDoc, int intCodDoc){
        boolean blnRes=false;
        Statement stmLoc;
        String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strNumRet="", strFecDoc="";
        String strCodTipCon="", strNumAutSri="", strSecDoc="", strFecCad="", strCodSri="", strCodCli="", strAux, strLocIdeEmi, strNumSerNumRet;
        String strSql="";
        int intCodRegDet=0;
        int intConUpCli=0, intAux;
        Date datFecAuxLoc;
        int intCodLoc=0;
        StringBuffer strSqlInsDet=new StringBuffer(),strSqlDetUpdCli=new StringBuffer();    

        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                strFecDoc =obtenerFecUltDiaMes();

                if(intCodEmp==1 || intCodEmp==4){
                   intCodLoc=11;
               }else if(intCodEmp==2){
                   intCodLoc=5;
               }

                strCodTipCon= String.valueOf(intCodTipDoc);

                for(int i=0; i<objTblMod.getRowCount(); i++){
                    if( ((objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
                        if( !((objTblMod.getValueAt(i, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

                            strCodCli =objTblMod.getValueAt(i, INT_TBL_CODCLI).toString();
                            strCodEmp=objTblMod.getValueAt(i, INT_TBL_CODEMP).toString();
                            strCodLoc=objTblMod.getValueAt(i, INT_TBL_CODLOC).toString();
                            strCodTipDocRec=objTblMod.getValueAt(i, INT_TBL_CODTID).toString();
                            strCodDoc=objTblMod.getValueAt(i, INT_TBL_CODDOC).toString();
                            strCodReg=objTblMod.getValueAt(i, INT_TBL_CODREG).toString();
                            strAbono=(objTblMod.getValueAt(i, INT_TBL_ABONO)==null?"0":(objTblMod.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_ABONO).toString()));
                            strNumRet=(objTblMod.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString()));

                            strSecDoc=(objTblMod.getValueAt(i, INT_TBL_NUMSECDOC)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString()));
                            strNumAutSri=(objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString()));
                            strFecCad=(objTblMod.getValueAt(i, INT_TBL_FECCATDOC)==null?"":(objTblMod.getValueAt(i, INT_TBL_FECCATDOC).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_FECCATDOC).toString()));
                            strCodSri=(objTblMod.getValueAt(i, INT_TBL_CODSRIDOC)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODSRIDOC).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODSRIDOC).toString()));
                            strLocIdeEmi = "";
                            strNumSerNumRet = "";

                            if (strNumAutSri.length() != 10)
                            {  //Esta verificacion solo se debera realizar cuando la autorizacion es electronica.
                               strLocIdeEmi = (objTblMod.getValueAt(i, INT_TBL_IDEEMI) == null? "" : (objTblMod.getValueAt(i, INT_TBL_IDEEMI).toString().equals("")? "" :objTblMod.getValueAt(i, INT_TBL_IDEEMI).toString()));
                               intAux = Integer.parseInt(strNumRet);
                               strAux = String.format("%09d", intAux); // => Ej: "000000001"
                               strNumSerNumRet = strSecDoc.substring(0, 3) + "-" + strSecDoc.substring(3, 6) + "-" + strAux;
                            }


                            intCodRegDet++;


                            strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
                            " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_numchq )" +
                            " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+" " +
                            " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I', "+strCodTipCon+", '"+strNumRet+"' ) ; ");



                            strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
                            " tx_numchq='"+strNumRet+"', nd_monchq="+strAbono+", fe_recchq="+strFecDoc+", fe_venchq="+strFecDoc+",  " +
                            " st_regrep='M', tx_numautsri='"+strNumAutSri+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"', tx_numser='"+strSecDoc+"' " +
                            " WHERE " +
                            " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
                            " and co_reg="+strCodReg;
                            stmLoc.executeUpdate(strSql.toString());

                            strSqlInsDet.append(" UPDATE tbm_cabmovinv set tx_codsri='"+strCodSri+"' " +
                            " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+"  ; ");

                            if (strNumAutSri.length() != 10 && verificarValRetClientePagMovInv(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc) == true)
                            {  //Esta verificacion solo se debera realizar cuando la autorizacion es electronica.
                               //Si es verdadero, significa que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja
                               datFecAuxLoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                               strAux = objUti.formatearFecha(datFecAuxLoc, objParSis.getFormatoFechaHoraBaseDatos());
                               strSqlInsDet.append(" UPDATE tbm_cabDocEleSri set st_asidocrel = 'S', fe_ultmod = '" + strAux + "', co_usrmod = " + objParSis.getCodigoUsuario() +
                               " WHERE tx_ideemi = '" + strLocIdeEmi + "' and tx_tipdoc = 'R' and tx_numdoc = '" + strNumSerNumRet +"'; ");
                            }

                            if(intConUpCli==1) strSqlDetUpdCli.append(" UNION ALL ");
                            strSqlDetUpdCli.append(" SELECT "+strCodEmp+" as coemp, "+strCodCli+" as cocli, text('"+strCodSri+"') as codsri, text('"+strSecDoc+"')  as numserret,text('"+strNumAutSri+"')  as numautsri,text('"+strFecCad+"')  as feccadsri   ");
                            intConUpCli=1;

                        }
                    }
                }

                if(intCodRegDet > 0 ){
                     System.out.println("ZafCxC01.insertarDet"+ strSqlInsDet.toString() );
                     stmLoc.executeUpdate(strSqlInsDet.toString());

                    strSql=" UPDATE tbm_cli SET tx_codsriret='"+strCodSri+"', tx_numserret='"+ strSecDoc+"'"+
                     " ,tx_numautsriret='"+strNumAutSri+"', tx_feccadret='"+strFecCad+"'"+
                     " WHERE  co_emp="+strCodEmp+" and co_cli="+strCodCli;


                     System.out.println("insertarDet: "+ strSql  );
                     stmLoc.executeUpdate(strSql);

                     blnRes=true;
                }

                stmLoc.close();
                stmLoc=null;

            }
        }catch(SQLException evt) { 
            blnRes=false;  
            strMensajesErrorCorreo+=evt.getMessage();
        }
         catch(Exception  evt2){ 
             blnRes=false;  
             strMensajesErrorCorreo+=evt2.getMessage();
         }
        return blnRes;
    }    
    
    private boolean _getValidarFecFacFecDoc(){
        boolean blnRes=false;
        String strFecDoc="";
        String strFecFacTbl="";
        int intAnoDoc=0;
        int intMesDoc=0;
        int intAnoFacDoc=0;
        int intMesFacDoc=0;
        try{
           System.out.println("ddd");
           strFecDoc = obtenerFecUltDiaMesSinComillas();
           intAnoDoc = objUti.getAnio(strFecDoc, "yyyy-MM-dd");
           intMesDoc = objUti.getMes(strFecDoc, "yyyy-MM-dd")+1;
           //if(objParSis.getCodigoMenu()==1561){
           for(int i=0; i<objTblMod.getRowCount(); i++){
             if( ((objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
              if( !((objTblMod.getValueAt(i, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

                strFecFacTbl = objTblMod.getValueAt(i, INT_TBL_FECDOC).toString();
                intAnoFacDoc = objUti.getAnio(strFecFacTbl, "yyyy-MM-dd");
                intMesFacDoc = objUti.getMes(strFecFacTbl, "yyyy-MM-dd")+1;

                if(intAnoDoc==intAnoFacDoc){
                 if(intMesDoc==intMesFacDoc){
                      blnRes=true;
                 }else{ blnRes=false; break; }
                }else{ blnRes=false; break; }

           }}}
          //}else blnRes=true;

        }catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
        return blnRes;
    }    
    
    private void inicializar(){
        Vector vecData=new Vector();
        objTblMod.setData(vecData);//Datos vacios
        valDoc=BigDecimal.ZERO;
        strNumDoc="";
    }    
    
    
    private boolean validaCampos(int intCodTipDoc, String strNumDoc, int intCodEmp){

        int intExiDatTbl=0, intCodTipRet;
        String strMens="RETENCIONES";
        String strCodCli="", strAux, strCodTipDoc, strCodTipDocCon, strNumSerLoc, strAux1, strAux2, strYearMes_Det, strDiaLimIngRet, strFecSrv;
        String strFecDet, strFecLimIngRet, strYearMesFecSrv;
        int intValCamp= 1;
        boolean blnRes, blnFound, blnIsCos_Ecu_Det;
        BigDecimal bdePorRet, bdeAux, bdeTotAbo;
        Date datFecAuxLoc, datFecLimIngRet, datFecSrv, datFecDet;
        StringTokenizer strTokCad;
        int intCon,intNumDec;
        

        if((objParSis.getCodigoMenu()==256)){
             strMens="CHEQUE";
        }


        //if(txtCodTipDoc.getText().trim().equals("") ){
        if(intCodTipDoc==0 ){
          return false;
        }



        if(strNumDoc.equals("") ){
            return false;
        }

          BigDecimal bdeValDia=BigDecimal.ZERO;
          BigDecimal bdeValDoc=BigDecimal.ZERO;
          
        if(objAsiDia.isDiarioCuadrado()){
            bdeValDia=objUti.redondearBigDecimal(new BigDecimal(objAsiDia.getMontoDebe()), objParSis.getDecimalesMostrar());
            bdeValDoc=valDoc.abs().setScale(2, RoundingMode.HALF_UP);
        } else {
            strAux="Asiento descuadrado.";
            strMensajesErrorCorreo+="\n"+strAux+"\n";
            //tabGen.setSelectedIndex(1);
            return false;          
        }          


        //if((objParSis.getCodigoMenu()==4181)){  // RETENCIONES
            for(int i=0; i<objTblMod.getRowCount(); i++){
                if( !((objTblMod.getValueAt(i, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
                    if( ((objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("false")) ){
                        //tabGen.setSelectedIndex(0);
                        strMensajesErrorCorreo+="HAY ALGUN DATO QUE NO ESTA SELECCIONADO.."+"\n";
                        //txtAlt1.requestFocus();
                        break;
                    }
                }
            }
        //}

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        objTblMod.removeEmptyRows();
        /*blnIsCos_Ecu_Det = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") != -1
                || objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") != -1
                || objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") != -1)? true: false;*/

        String strLngNumAutSri="";
        String strLngFecCad="";
        bdeTotAbo = new BigDecimal("0");
        strYearMes_Det = "";
        datFecAuxLoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        strFecSrv = objUti.formatearFecha(datFecAuxLoc, objParSis.getFormatoFechaHoraBaseDatos());
        strYearMesFecSrv = strFecSrv.substring(0, 7); //Si strFecSrv es 2016-12-01, va a extraer "2016-12"
        strFecSrv = strFecSrv.substring(0, 10); //De la fecha/hora del servidor, se va a extraer solo la fecha
        strFecLimIngRet = "";
        strDiaLimIngRet = "";

        if (intCodEmp == 1)
        {  //Para Tuval, la fecha limite para ingreso de retenciones es el dia 14 de cada mes
           strDiaLimIngRet = "14";
           //strDiaLimIngRet = "10";
           strFecLimIngRet = strYearMesFecSrv + "-" + strDiaLimIngRet;
        }
        else if (intCodEmp == 2 || intCodEmp == 4)
        {  //Para Castek y Dimulti, la fecha limite para ingreso de retenciones es el dia 8 de cada mes
           strDiaLimIngRet = "08";
           //strDiaLimIngRet = "10";
           strFecLimIngRet = strYearMesFecSrv + "-" + strDiaLimIngRet;
        }
        else
           strFecLimIngRet = strYearMesFecSrv + "-01";

        strFecSrv = strFecSrv.replace("-", "/");
        strFecLimIngRet = strFecLimIngRet.replace("-", "/");

        for(int i=0; i<objTblMod.getRowCount(); i++){
            if( ((objTblMod.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":objTblMod.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
                if( !((objTblMod.getValueAt(i, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

                    strCodCli =objTblMod.getValueAt(i, INT_TBL_CODCLI).toString();

                    intExiDatTbl=1;


                    //if((objParSis.getCodigoMenu()==4181)){  // RETENCIONES
                        if(intValCamp==1){
                            
                            strCodTipDoc=String.valueOf(intCodTipDoc);

                            bdePorRet = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_PORRET).toString());
                            bdePorRet = objUti.redondearBigDecimal(bdePorRet, objParSis.getDecimalesMostrar());
                   
                            strAux1 = "";
                            strAux2 = "";
                            strAux = objTblMod.getValueAt(i, INT_TBL_ABONO).toString();
                            strAux = strAux.indexOf(".") < 0 ? strAux : strAux.replaceAll("0*$", "").replaceAll("\\.$", ""); //Para suprimir ceros a la derecha del punto decimal.
                            strTokCad = new StringTokenizer(strAux, ".");
                            intCon = 0; //Contador
                            intNumDec = 0;
             
                            while (strTokCad.hasMoreElements())
                            {  
                               intCon++;
                               strAux = strTokCad.nextToken().toString();

                               if (intCon == 1)
                               {  //Cuando intCon = 1, quiere decir que en la variable strAux estaran todos los digitos que estan a la izquierda del punto decimal.
                                  strAux1 = strAux; //strAux1 tendra la parte entera del numero.
                               }
                               else if (intCon == 2)
                               {  //Cuando intCon = 2, quiere decir que en la variable strAux estaran todos los digitos que estan a la derecha del punto decimal.
                                  strAux2 = strAux; //strAux2 tendra la parte decimal del numero.
                                  intNumDec = strAux.length();
                               }
                           }

                            if (intNumDec > 2)
                            {  //En la col. 'Abono' solo se debe permitir un maximo de 2 decimales.
                               strAux1 = strAux1 + "." + strAux2; //Se adjunta la parte entera (strAux1) y la parte decimal (strAux2) del numero.
                               //tabGen.setSelectedIndex(0);
                               strAux =  "<HTML>En el detalle hay un valor de abono (" + strAux1 + ") que tiene mas de 2 decimales.<BR>";
                               strAux += "<HTML>El valor de abono puede tener como máximo 2 decimales.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                               strAux += "Nom. cliente: " + objTblMod.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                               strAux += "Num. documento: " + objTblMod.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                               strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                               strMensajesErrorCorreo+="\n"+strAux+"\n";
                               return false;
                            }                   
                   
                   
                            bdeAux = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_ABONO).toString());
                            bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                            bdeTotAbo = bdeTotAbo.add(bdeAux);



                            if (strCodTipDoc.equals("247") && !bdePorRet.equals(new BigDecimal("10.00")))
                            {  //CodTipDoc 247 = RFIV10 = Retencion al IVA 10%
                               //tabGen.setSelectedIndex(0);
                               strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                               strAux += "Nom. cliente: " + objTblMod.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                               strAux += "Num. documento: " + objTblMod.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                               strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                               strMensajesErrorCorreo+="\n"+strAux+"\n";
                               return false;
                            }


                            if (strCodTipDoc.equals("25") && !bdePorRet.equals(new BigDecimal("1.00")))
                            {  //CodTipDoc 25 = RETE = Retenciones en la Fuente 1%
                               //tabGen.setSelectedIndex(0);
                               strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                               strAux += "Nom. cliente: " + objTblMod.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                               strAux += "Num. documento: " + objTblMod.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                               strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                               strMensajesErrorCorreo+="\n"+strAux+"\n";
                               return false;
                            }


                        } //if (rdaAplMisCta.isSelected())

                        strAux = objTblMod.getValueAt(i, INT_TBL_CODSRIDOC).toString();

                        if (strAux.equals(""))
                        {  //tabGen.setSelectedIndex(0);
                           strAux =  "<HTML>En el detalle no hay valor de código del SRI.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                           strAux += "Nom. cliente: " + objTblMod.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                           strAux += "Num. documento: " + objTblMod.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                           strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                           strMensajesErrorCorreo+="\n"+strAux+"\n";
                           return false;
                        }

                        if( ((objTblMod.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString().trim().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMCHQ).toString())).trim().equals("")) ){
                              strAux = "AGREGE EL NUMERO DE RETENCIÓN.. ";
                              strMensajesErrorCorreo+="\n"+strAux+"\n";
                              return false;
                        }
                        if( ((objTblMod.getValueAt(i, INT_TBL_NUMSECDOC)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString().trim().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString())).trim().equals("")) ){
                              strAux ="AGREGE EL NUMERO DE SERIE.. ";
                              strMensajesErrorCorreo+="\n"+strAux+"\n";
                              return false;
                        }
                        if( ((objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":(objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString().trim().equals("")?"":objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString())).trim().equals("")) ){
                              strAux ="AGREGE EL NUMERO DE AUTORIZACIÓN DEL SRI.. ";
                              strMensajesErrorCorreo+="\n"+strAux+"\n";
                              return false;
                        }

                        //se valida si debe llevar FECHA CADUCIDAD
                        strLngNumAutSri=objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString();
                        strLngFecCad=objTblMod.getValueAt(i, INT_TBL_FECCATDOC)==null?"":objTblMod.getValueAt(i, INT_TBL_FECCATDOC).toString();
                        
                        if( (strLngNumAutSri.length()!=10) && (strLngNumAutSri.length()!=37) && (strLngNumAutSri.length()!=49) ){
                            strAux ="<HTML>El campo número de autorización debe tener 10, 37 ó 49 dígitos.<BR>Verifique y vuelva a intentarlo.</HTML>";
                            strMensajesErrorCorreo+="\n"+strAux+"\n";
                            return false;
                        }

                        strNumSerLoc = objTblMod.getValueAt(i, INT_TBL_NUMSECDOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString();

                        if (strNumSerLoc.length() != 6)
                        {  
                           strAux ="<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.Doc.</FONT> debe tener 6 digitos sin guion.<BR>Verifique y vuelva a intentarlo.</HTML>";
                           strMensajesErrorCorreo+="\n"+strAux+"\n";
                           return false;
                        }

                        strAux1 = strNumSerLoc.substring(0, 3); // Si la serie es 001002, va a extraer "001"
                        strAux2 = strNumSerLoc.substring(3, 6); // Si la serie es 001002, va a extraer "002"
                        blnFound = true;

                        if (strNumSerLoc.lastIndexOf("-") == -1)
                           blnFound = false;

                        if (objUti.isNumero(strAux1) == false || objUti.isNumero(strAux2) == false || blnFound == true)
                        {  
                            strAux ="<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.Doc.</FONT> debe ser numérico sin guion.<BR>Verifique y vuelva a intentarlo.</HTML>";
                            strMensajesErrorCorreo+="\n"+strAux+"\n";   
                            return false;
                        }

                        //De la columna "Fec.Doc." se va a extraer aaaa-mm
                        strAux1 = objTblMod.getValueAt(i, INT_TBL_FECDOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_FECDOC).toString();
                        strAux1 = strAux1.substring(0, 7); // Si strAux1 es 2016-12-01, va a extraer "2016-12"

//                        if (i > 0 && !strYearMes_Det.equals(strAux1))
//                        {  //En el detalle en la columna "Fec.Doc." se encontró documentos con "aaaa-mm" que no son iguales, y por tanto no se debe permitir
//                           //grabar
//                           //tabGen.setSelectedIndex(0);
//                           strAux =  "<HTML>En el detalle en la columna <FONT COLOR=\"blue\">Fec.Doc.</FONT> se encontró documentos con 'aaaa-mm' que no son iguales.<BR><BR>";
//                           strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
//                           mostrarMsgInf(strAux);
//                           return false;
//                        }

//                        if (blnIsCos_Ecu_Det == false)
//                        {  strYearMes_Det = strAux1;
//                           //strFecDet va a estar en formato "yyyy/MM/dd"
//                           strFecDet = strYearMes_Det.substring(0, 4) + "/" + strYearMes_Det.substring(5, 7) + "/" + strFecSrv.substring(8, 10);
//
//                           datFecSrv = objUti.parseDate(strFecSrv, "yyyy/MM/dd");
//                           datFecLimIngRet = objUti.parseDate(strFecLimIngRet, "yyyy/MM/dd");
//                           datFecDet = objUti.parseDate(strFecDet, "yyyy/MM/dd");
//
//                           if (datFecSrv.compareTo(datFecLimIngRet) > 0 && datFecDet.compareTo(datFecSrv) < 0)
//                           {  //No se debe permitir el ingreso de la retencion
//                              //tabGen.setSelectedIndex(0);
//                              strAux =  "<HTML>En el detalle hay una retención que no puede ser ingresada por<BR>";
//                              strAux += "sobrepasar la fecha límite para el ingreso de retenciones.<BR>";
//                              strAux += "Fecha límite: <FONT COLOR=\"blue\">" + strFecLimIngRet + "</FONT><BR>";
//                              strAux += "Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
//                              strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
//                              strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR>";
//                              strAux += "Fecha documento: " + tblDat.getValueAt(i, INT_TBL_FECDOC).toString() + "<BR><BR>";
//                              strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
//                              mostrarMsgInf(strAux);
//                              return false;
//                           }
//                        }

                        if( ((objTblMod.getValueAt(i, INT_TBL_CODSRIDOC)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODSRIDOC).toString().trim().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODSRIDOC).toString())).trim().equals("")) ){
                              strAux ="AGREGE EL CÓDIGO DEL SRI.. ";
                              strMensajesErrorCorreo+="\n"+strAux+"\n";   
                              return false;
                        }
                    //}
                }
            }
        }

        if (bdeValDoc.compareTo(bdeTotAbo) != 0)
        {  //Cod. menu 1648 = Retenciones recibidas
           strAux ="<HTML>El valor del documento tiene un valor diferente al total abonado<BR>Verifique estos valores y vuelva a intentarlo.</HTML>";
           strMensajesErrorCorreo+="\n"+strAux+"\n";  
           return false;
        }

         if(intExiDatTbl==0){
             strAux ="NO HAY DATOS EN DETALLE INGRESE DATOS.... ";
             strMensajesErrorCorreo+="\n"+strAux+"\n";
             return false;
         }

         return true;
    }         
    
    public boolean insertar(int intCodTipDoc, String strNumDoc,int intCodEmp,Connection cnx) {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        int intCodDoc=0;
        int intCodLoc=0;
        StringBuffer strSqlInsDet,strSqlDetUpdCli;
        
        try{
            
            if(intCodEmp==1 || intCodEmp==4){
                intCodLoc=11;
            }else if(intCodEmp==2){
                intCodLoc=5;
            }

            if(validaCampos(intCodTipDoc,strNumDoc, intCodEmp)){

                if(cnx!=null){

                    stmLoc=cnx.createStatement();
//
                    strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as codoc  FROM tbm_cabpag WHERE " +
                    " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(rstLoc.next())
                        intCodDoc = rstLoc.getInt("codoc");
                    rstLoc.close();
                    rstLoc=null;

                    strSqlInsDet=new StringBuffer();
                    strSqlDetUpdCli=new StringBuffer();

                    if(_getValidarFecFacFecDoc()){
                        if(insertarCab(cnx, intCodEmp, intCodTipDoc,intCodDoc,strNumDoc)){
                            if(insertarDet(cnx, intCodEmp, intCodTipDoc,intCodDoc)){
                                objAsiDia.getDetalleDiario();
                                if(objAsiDia.insertarDiario(cnx, intCodEmp,intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), strNumDoc, objUti.parseDate(obtenerFecUltDiaMesSinComillas(),"yyyy-MM-dd") )) {
                                        inicializar();
                                        blnRes=true;
                                }else{
                                    strMensajesErrorCorreo+="Error en insertarDiario Retenciones Recibidas";
                                    blnRes=false;
                                }
                            }else{
                                strMensajesErrorCorreo+="Error en insertarDet Retenciones Recibidas";
                                blnRes=false;
                            }
                        }else{
                            strMensajesErrorCorreo+="Error en insertarCab Retenciones Recibidas";
                            blnRes=false;
                        }
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            strMensajesErrorCorreo+=ex.getMessage();
        }
        return blnRes;
    }    
    
    
//    public static void main(String []args){
//        RetRecComRel ret=new RetRecComRel();
//        String strFecha=ret.obtenerFecUltDiaMesSinComillas();
//                
//        java.text.SimpleDateFormat objSimDatFor;
//        java.util.Date datRes;
//        try
//        {
//            objSimDatFor=new java.text.SimpleDateFormat("yyyy-MM-dd");
//            datRes=objSimDatFor.parse(strFecha);
//        }
//        catch (java.text.ParseException e)
//        {
//            datRes=null;
//        }
//        catch (Exception e)
//        {
//            datRes=null;
//        }                
//    
//    }
   
    
}
