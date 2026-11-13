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
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 *
 * @author Sistemas05
 */
public class EmiRetComRel {
    
    /*static */Connection cnx=null;
    ZafParSis objParSis;
    Vector vecEmpresas;
    Vector vecClientes;
    
    
    final int INT_TBL_DAT_LIN=0;                        //Línea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificación.
    final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //Código del tipo de documento.
    final int INT_TBL_DAT_DEC_TIP_DOC=4;                //Descripción corta del tipo de documento.
    final int INT_TBL_DAT_DEL_TIP_DOC=5;                //Descripción larga del tipo de documento.
    final int INT_TBL_DAT_COD_DOC=6;                    //Código del documento (Sistema).
    final int INT_TBL_DAT_COD_REG=7;                    //Código del registro (Sistema).
    final int INT_TBL_DAT_NUM_DOC=8;                    //Número del documento.
    final int INT_TBL_DAT_FEC_DOC=9;                    //Fecha del documento.
    final int INT_TBL_DAT_COD_TIP_RET=10;               //Código del tipo de retención.
    final int INT_TBL_DAT_DEC_TIP_RET=11;               //Descripción corta del tipo de retención.
    final int INT_TBL_DAT_BUT_TIP_RET=12;               //Botón de consulta de Tipos de retenciones.
    final int INT_TBL_DAT_DEL_TIP_RET=13;               //Descripción larga del tipo de retención.
    final int INT_TBL_DAT_POR_RET=14;                   //Porcentaje de retención.
    final int INT_TBL_DAT_APL_RET=15;                   //Aplicar retencióna a "Subtotal" o "IVA".
    final int INT_TBL_DAT_BAS_IMP=16;                   //Base imponible. Puede ser el "Subtotal" o "IVA" según sea el caso.
    final int INT_TBL_DAT_VAL_DOC=17;                   //Valor del Impuesto.
    final int INT_TBL_DAT_VAL_PEN=18;                   //Valor pendiente.
    final int INT_TBL_DAT_ABO_DOC=19;                   //Abono del documento.
    final int INT_TBL_DAT_AUX_ABO=20;                   //Auxiliar:Abono del documento.
    final int INT_TBL_DAT_SEC_DOC=21;                   //Secuencia del documento. Ejemplo: 001-001
    final int INT_TBL_DAT_NUM_PED=22;                   //Número de pedido. Es el número de la factura del proveedor.
    final int INT_TBL_DAT_NUM_AUT=23;                   //Número de autorización del SRI.
    final int INT_TBL_DAT_FEC_CAD=24;                   //Fecha de caducidad del documento.
    final int INT_TBL_DAT_COD_SRI=25;                   //Codigo del SRI.      
    
    
    ZafTblMod objTblMod, objTblModDia;
    final int RFP1E=230;
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.    
    String strCta="", strTxCodCta="",strTxDesLar="";
    ZafAsiDia2 objAsiDia;
    ZafUtil objUti;
    
    private String strMensajesErrorCorreo="";    
    
    
    public EmiRetComRel(ZafParSis objParSis, Connection cnx){
        try{
            objParSis= (ZafParSis)objParSis.clone();
            this.cnx=cnx;
            cargarEmpReg(); 
            configurarTblMod();
            configurarTblDiario();
            objAsiDia=new ZafAsiDia2(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {

                        objAsiDia.setCodigoTipoDocumento(RFP1E);
                }
            });    
            objUti=new ZafUtil();            

        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }
   
    
    public EmiRetComRel(Connection cnx){
        objParSis=new ZafParSis();
        objParSis.setCodigoUsuario(1);
        //abrirCon();
        this.cnx=cnx;
        cargarEmpReg();
        configurarTblMod();    
        configurarTblDiario();
        
        objAsiDia=new ZafAsiDia2(objParSis);
        objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
            public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    objAsiDia.setCodigoTipoDocumento(RFP1E);
            }
        });  
        objUti=new ZafUtil();
        
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
    
    private void cargarCliReg(Connection cnx,int intCodEmp, String strNomEmp){
        Statement stmQry=null;
        ResultSet rs=null;
        String strQry="select co_cli,tx_nom, tx_ide, tx_dir from tbm_cli where co_emp="+intCodEmp+ " and tx_nom not like '"+strNomEmp+"%' and tx_nom not like 'NOSITOL%' and co_empgrp=0";
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
    
    private void configurarTblMod(){
    
        Vector vecCab=null;
        vecCab=new Vector(26);  //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LIN,"");
        vecCab.add(INT_TBL_DAT_CHK,"");
        vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");//no sale
        vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc");//no sale
        vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");//tipDoc//
        vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");//tipo de documento//
        vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");//no sale
        vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");//no sale
        vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");//NumDoc//
        vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//FecDoc//
        vecCab.add(INT_TBL_DAT_COD_TIP_RET,"Cód.Tip.Ret");//no sale
        vecCab.add(INT_TBL_DAT_DEC_TIP_RET,"Tip.Ret.");//TipRet//
        vecCab.add(INT_TBL_DAT_BUT_TIP_RET,"");//no sale
        vecCab.add(INT_TBL_DAT_DEL_TIP_RET,"Tipo de retención");//Tipo de Retencion//
        vecCab.add(INT_TBL_DAT_POR_RET,"%");//Simbolo POrcentaje//
        vecCab.add(INT_TBL_DAT_APL_RET,"Aplicado");//Aplicacion de Retencion//
        vecCab.add(INT_TBL_DAT_BAS_IMP,"Bas.Imp.");//Base Imponible//
        vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Ret.");//Valor de la Retencion//
        vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");//Valor Pendiente//
        vecCab.add(INT_TBL_DAT_ABO_DOC,"Abono");//Abono//
        vecCab.add(INT_TBL_DAT_AUX_ABO,"Aux.Abo.");//no sale
        vecCab.add(INT_TBL_DAT_SEC_DOC,"Sec.Doc.");///serie del documento del cliente
        vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");///numero de pedido del cliente
        vecCab.add(INT_TBL_DAT_NUM_AUT,"Núm.Aut.");///numero de autSri del docum del cliente
        vecCab.add(INT_TBL_DAT_FEC_CAD,"Fec.Cad.");///fecha caducidad del documento del cliente
        vecCab.add(INT_TBL_DAT_COD_SRI,"Cod.SRI.");///codigo del SRI
        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
    }
            
    
    private void configurarTblDiario()
    {
        boolean blnRes=true;
        Vector vecCabDia=null;

        vecCabDia=new Vector(26);  //Almacena las cabeceras
        vecCabDia.clear();
        vecCabDia.add(INT_TBL_DAT_LIN,"");
        vecCabDia.add(INT_TBL_DAT_CHK,"");
        vecCabDia.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");//no sale
        vecCabDia.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc");//no sale
        vecCabDia.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");//tipDoc//
        vecCabDia.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");//tipo de documento//
        vecCabDia.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");//no sale
        vecCabDia.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");//no sale
        vecCabDia.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");//NumDoc//
        vecCabDia.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//FecDoc//
        vecCabDia.add(INT_TBL_DAT_COD_TIP_RET,"Cód.Tip.Ret");//no sale
        vecCabDia.add(INT_TBL_DAT_DEC_TIP_RET,"Tip.Ret.");//TipRet//
        vecCabDia.add(INT_TBL_DAT_BUT_TIP_RET,"");//no sale
        vecCabDia.add(INT_TBL_DAT_DEL_TIP_RET,"Tipo de retención");//Tipo de Retencion//
        vecCabDia.add(INT_TBL_DAT_POR_RET,"%");//Simbolo POrcentaje//
        vecCabDia.add(INT_TBL_DAT_APL_RET,"Aplicado");//Aplicacion de Retencion//
        vecCabDia.add(INT_TBL_DAT_BAS_IMP,"Bas.Imp.");//Base Imponible//
        vecCabDia.add(INT_TBL_DAT_VAL_DOC,"Val.Ret.");//Valor de la Retencion//
        vecCabDia.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");//Valor Pendiente//
        vecCabDia.add(INT_TBL_DAT_ABO_DOC,"Abono");//Abono//
        vecCabDia.add(INT_TBL_DAT_AUX_ABO,"Aux.Abo.");//no sale
        vecCabDia.add(INT_TBL_DAT_SEC_DOC,"Sec.Doc.");///serie del documento del cliente
        vecCabDia.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");///numero de pedido del cliente
        vecCabDia.add(INT_TBL_DAT_NUM_AUT,"Núm.Aut.");///numero de autSri del docum del cliente
        vecCabDia.add(INT_TBL_DAT_FEC_CAD,"Fec.Cad.");///fecha caducidad del documento del cliente
        vecCabDia.add(INT_TBL_DAT_COD_SRI,"Cod.SRI.");///codigo del SRI
        objTblModDia=new ZafTblMod();
        objTblModDia.setHeader(vecCabDia);        
    }
    
    
    
    private void consultar(Connection cnx,int intCodEmp, int intCodLoc, int intProv){
    
        ResultSet rst=null;
        Statement stm=null;
        Vector vecDat=null,vecReg;
        String strAux="";
        int intUltDia,intMes,intAnio, intMesAnterior;        
        Calendar calFec=Calendar.getInstance();  
        calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        intMesAnterior++;//ya que calendar cuenta los meses desde 0 siendo enero 0; diciembre es 11 
        

        String strSQL="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc \n";
            strSQL+=", a1.fe_doc, a2.co_tipRet, a4.tx_desCor AS a4_tx_desCor, a4.tx_desLar AS a4_tx_desLar, a2.nd_porRet \n";
            strSQL+=", a2.tx_aplRet, a1.nd_sub, a1.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen \n";
            strSQL+=", a1.tx_numPed, a2.tx_codsri \n"; ///, a1.tx_codsri";
            strSQL+=", a2.nd_basImp \n";
            strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad, a2.tx_numChq \n";
            strSQL+=" FROM tbm_cabMovInv AS a1 \n";
            strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
            strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n";
            strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet) \n";
            strSQL+=" WHERE a1.co_emp=${empresa} \n";
            strSQL+=" AND a1.co_loc=${local} \n";
            strSQL+=" AND a1.co_cli=${proveedor} \n";
            strSQL+=" AND a1.st_reg IN ('A','R','C','F') \n";
            strSQL+=" and a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" ;
            //strSQL+=" and a1.fe_doc between '2018-05-01' and '2018-05-31' \n" ;
            strSQL+=" and tx_natdoc='I' \n";
            strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 \n";
            strSQL+=" AND a2.nd_porRet>0 \n";
            strSQL+=" AND a2.st_reg IN ('A','C') \n";
            strSQL+=" AND a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL \n";
            strSQL+=" AND ( case when (length(a2.tx_numAutSri) <> 37 and length(a2.tx_numAutSri) <> 49) then a2.tx_fecCad IS NOT NULL \n "; //Si NumAutSri <> 37 o <> 49, significa que no es factura electronica y por tanto se debe validar el campo tx_fecCad
            strSQL+="             else 1 = 1"; //Si es factura electronica; por tanto no se debe validar el campo tx_fecCad
            strSQL+="       end ) \n";

            strSQL+=" AND a2.tx_numChq IS NOT NULL \n";
            strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg \n";
        
            try{
                Map<String, Integer> placehold=new HashMap<String,Integer>();
                StrSubstitutor subs;
                String strSql2="";
                placehold.put("empresa",intCodEmp);
                placehold.put("proveedor",intProv);
                placehold.put("local",intCodLoc);
                subs=new StrSubstitutor(placehold);
                strSql2=subs.replace(strSQL);
                stm=cnx.createStatement();
                rst=stm.executeQuery(strSql2);  
                vecDat=new Vector();
                vecDat.clear();
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_RET,rst.getString("co_tipRet"));
                    vecReg.add(INT_TBL_DAT_DEC_TIP_RET,rst.getString("a4_tx_desCor"));
                    vecReg.add(INT_TBL_DAT_BUT_TIP_RET,null);
                    vecReg.add(INT_TBL_DAT_DEL_TIP_RET,rst.getString("a4_tx_desLar"));
                    vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));
                    strAux=rst.getString("tx_aplRet");
                    if (strAux==null)
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,null);
                        vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                    }
                    else if (strAux.equals("S"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"Subtotal");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,rst.getString("nd_basImp"));
                    }
                    else if (strAux.equals("I"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"IVA");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getDouble("nd_basImp"));
                    }
                    else if (strAux.equals("O"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"OTROS");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getDouble("nd_basImp"));
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,null);
                        vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                    }

                    vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getBigDecimal("mo_pag"));
                    vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                    vecReg.add(INT_TBL_DAT_ABO_DOC,rst.getString("nd_pen"));
                    vecReg.add(INT_TBL_DAT_AUX_ABO,"");
                    vecReg.add(INT_TBL_DAT_SEC_DOC,rst.getString("tx_numSer"));
                    vecReg.add(INT_TBL_DAT_NUM_PED,rst.getString("tx_numChq"));
                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("tx_numAutSRI"));
                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("tx_fecCad"));
                    vecReg.add(INT_TBL_DAT_COD_SRI,rst.getString("tx_codsri"));
                    vecDat.add(vecReg);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            objTblMod.setData(vecDat);           
            
    }
    
    public boolean procesar(){        
        boolean booRet=true;
        int intCodEmp=0;
        String strNomEmp="",strSQL="";
        ResultSet rst=null;
        Statement stm=null;
        Vector vecDat;
        String strAux;
        boolean booErr=false;
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
                int intCodLocPre=obtenerLocPred(cnx, intCodEmp);
                obtenerNatTipDoc(cnx, intCodEmp, intCodLocPre);
                while(itCli.hasNext()){
                    Vector vecActCli=(Vector)itCli.next();
                    int intProv=(Integer)vecActCli.get(0);
                    consultar(cnx,intCodEmp, intCodLoc, intProv);
                    booRet=procesarReg(intCodEmp, intCodLocPre, vecActCli, cnx);
                    if(booRet){
                        cnx.commit();
                    }else{
                        cnx.rollback();
                        booErr=true;//Existio un problema salir del For
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
            strMensajesErrorCorreo+="Error en método procesar en Emisión de retenciones: "+ex.getMessage();
        }finally{
            if(strMensajesErrorCorreo.length()>0){
                strCorreosUsers="sistemas5@tuvalsa.com";
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - ERRORES EN EMISION RETENCIONES - ", strMensajesErrorCorreo);
            }        
        }
        return booRet;
    }
    
    
    private String armarDetIns(int i, int intCodEmp, int intCodLoc, int intCodDoc, int intSec) throws Exception
    {
        String strSQL="";
        strSQL="";
        strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
        strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
        strSQL+=" VALUES (";
        strSQL+="" + intCodEmp;
        strSQL+=", " + intCodLoc;
        strSQL+=", " + RFP1E;
        strSQL+=", " + intCodDoc;
        strSQL+=", " + intSec;
        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
        strSQL+=", " + intSig*Double.parseDouble(codificar(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC), 3));
        strSQL+=", Null";
        strSQL+=", Null";
        strSQL+=", Null";
        strSQL+=", Null";
        strSQL+=", Null";
        strSQL+=", Null";                        
        strSQL+=");";
        return strSQL;                  
    }     
    
    private int getIntCodMax(Connection conn,int intCodEmp, int intCodLoc) throws SQLException{
        String strSQL="";
        int intUltReg=0;
        Statement stm=null;
        ResultSet rst=null;
        strSQL+="SELECT MAX(a1.co_doc) as ultimo";
        strSQL+=" FROM tbm_cabPag AS a1";
        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
        strSQL+=" AND a1.co_loc=" + intCodLoc;
        strSQL+=" AND a1.co_tipDoc=" + RFP1E;
        try{
            stm=conn.createStatement();
            rst=stm.executeQuery(strSQL);
            while (rst.next()){
                intUltReg=rst.getInt("ultimo");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            throw ex;
        }
        //intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
        return intUltReg;
    }    
    
    /**
     * Obtiene la cta contable de la compañia relacionada de la empresa 
     * @param conn Conexion
     * @param intCodEmp codigo de la empresa 
     * @param strDesc Nombre del proveedor 
     * @throws SQLException 
     */
    private void getCtaComRel(Connection conn,int intCodEmp, String strDesc) throws SQLException{
        String strSQL="";
        int intUltReg=0;
        Statement stm=null;
        ResultSet rst=null;
        strSQL+="SELECT co_cta,tx_codcta,tx_deslar ";
        strSQL+=" FROM tbm_placta AS a1";
        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
        strSQL+=" AND LOWER(a1.tx_deslar) like 'compañ%"+strDesc.substring(0, strDesc.indexOf("S.A.")-1).toLowerCase()+"%'";
        try{
            stm=conn.createStatement();
            rst=stm.executeQuery(strSQL);
            while (rst.next()){
                strCta=rst.getString("co_cta");
                strTxCodCta=rst.getString("tx_codcta");
                strTxDesLar=rst.getString("tx_deslar");                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            throw ex;
        }
    }     
    
    private String getFechaUltimoDiaMesparaPruebas(){
        int intAnio=0,intUltDia=0;
        Calendar calFec=Calendar.getInstance();     
        int intDia=calFec.get(Calendar.DATE);
        int intMes=0;
        if (intDia>=1 && intDia<=31){            
            intMes=calFec.get(Calendar.MONTH);
            intMes=intMes+1; // porque Enero =0
            if (intMes==1){
                intMes=12;
                intAnio=calFec.get(Calendar.YEAR)-1;
            }else {
                intMes=intMes-1;
                intAnio=calFec.get(Calendar.YEAR);
            }
        }
        
        intUltDia=getUltimoDiaMes(intAnio, intMes-1);
        Formatter fmtMes = new Formatter();
        fmtMes = new Formatter();

        String strMes=fmtMes.format("%02d",intMes).toString();    
        return intAnio+"-"+strMes+"-"+intUltDia;        

        
    }
    
    private int getUltimoDiaMes(int anio, int mes)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, anio);
        cal.set(java.util.Calendar.MONTH, mes);
        cal.set(java.util.Calendar.DATE, 1);
        return cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }    
    
    private String obtenerUltDiaMes(){
        Calendar calFecAct=Calendar.getInstance();
        int intAnio=0,intUltDiaMes=0;
        String strMes="";
        int intDia=calFecAct.get(Calendar.DATE);
        int intMes=0;
        if (intDia>=1 && intDia<=30){            
            intMes=calFecAct.get(Calendar.MONTH);
            intMes=intMes+1; // porque Enero =0
            if (intMes==1){
                intMes=12;
                intAnio=calFecAct.get(Calendar.YEAR)-1;
            }else {
                intMes=intMes-1;
                intAnio=calFecAct.get(Calendar.YEAR);
            }
        }
        //abrirCon2();
        intUltDiaMes=getUltimoDiaMes(intAnio, intMes-1);
        Formatter fmtMes = new Formatter();
        fmtMes = new Formatter();

        strMes=fmtMes.format("%02d",intMes).toString();    
        return intAnio+"-"+strMes+"-"+intUltDiaMes;        
    }
    
    private boolean procesarReg( int intEmpEmi, int intCodLoc, Vector vecProv,Connection CONN){
        boolean booRet=true;
        int intNumDocAux=0, intSec=0, intCodDoc=0, intNumDoc=0;
        String strInsDet="";
        BigDecimal bgdTotCab=BigDecimal.ZERO;
        Vector vecRegDia, vecDataDia=new Vector();
        boolean booCab=false, booDet=false, booDia=false, booActCabMovInv=false;
        
        /*vecProv.get(0);
        vecProv.get(1);
        vecProv.get(2);*/
        try{
            System.out.println("count "+objTblMod.getRowCountTrue());
            for(int i=0; i<objTblMod.getRowCountTrue();i++){    
                if(i==0){
                    intSec++;
                    intNumDocAux=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                    intCodDoc=getIntCodMax(CONN,intEmpEmi, intCodLoc)+1;
                    strInsDet+=armarDetIns(i, intEmpEmi, intCodLoc, intCodDoc, intSec);
                    bgdTotCab=bgdTotCab.add((BigDecimal)objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                    vecRegDia=new Vector();
                    vecRegDia=(Vector)objTblMod.getRow(i);
                    vecDataDia.add(vecRegDia);

                }else if(intNumDocAux==Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC))){
                    bgdTotCab=bgdTotCab.add((BigDecimal)objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                    intSec++;
                    strInsDet+=armarDetIns(i, intEmpEmi, intCodLoc, intCodDoc, intSec);
                    vecRegDia=new Vector();
                    vecRegDia=(Vector)objTblMod.getRow(i);
                    vecDataDia.add(vecRegDia);

                }else{
                        booCab=insertarCab(CONN,intCodDoc,bgdTotCab, intEmpEmi,vecProv);
                        if(booCab){
                            booDet=insertarDet(CONN, strInsDet);
                            if(booDet){
                                objTblModDia.setData(vecDataDia);

                                intNumDoc=obtenerUltNumDoc(CONN, intEmpEmi, intCodLoc)+1;
                                actualizarGlo("RFP1E", String.valueOf(intNumDoc), String.valueOf(intNumDocAux),((String)vecProv.get(1)));
                                objAsiDia.generarDiario(RFP1E,objTblModDia, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC,intEmpEmi, intCodLoc,CONN);
                                getCtaComRel(CONN, intEmpEmi, (String)vecProv.get(1));
                                objAsiDia.seteaCtaCompRela(0, strCta, strTxCodCta, strTxDesLar);


                                //ZafAsiDia objAsiDia=new ZafAsiDia(objParSis);
                                //booDia=objAsiDia.generarDiario(RFP1E,objTblModDia, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC,intEmpEmi, intCodLoc,CONN);
                                objParSis.cargarAniosCreadosSistema(intEmpEmi);
                                booDia=objAsiDia.insertarDiario(CONN, intEmpEmi, intCodLoc, RFP1E, String.valueOf(intNumDoc), objUti.parseDate(getFechaUltimoDiaMesparaPruebas(),"yyyy-MM-dd"));                        
                                if(booDia){
                                    intSec=0;
                                    strInsDet="";
                                    bgdTotCab=BigDecimal.ZERO;
                                    vecRegDia=new Vector();
                                    vecDataDia=new Vector();
                                    intNumDocAux=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                                    intCodDoc=getIntCodMax(CONN,intEmpEmi, intCodLoc)+1;
                                    intSec++;
                                    strInsDet+=armarDetIns(i, intEmpEmi, intCodLoc, intCodDoc, intSec);
                                    vecRegDia=new Vector();
                                    vecRegDia=(Vector)objTblMod.getRow(i);
                                    vecDataDia.add(vecRegDia);
                                    bgdTotCab=bgdTotCab.add((BigDecimal)objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC));                                    
                                }else{
                                    booRet=false;
                                    strMensajesErrorCorreo+="Error en insertarDiario Emisión Retenciones ";
                                    break;
                                }   

                                
                            }else{
                                booRet=false;
                                strMensajesErrorCorreo+="Error en insertarDet Emisión Retenciones ";
                                break;
                            }
                        }else{
                            booRet=false;
                            strMensajesErrorCorreo+="Error en insertarCab Emisión Retenciones ";
                            break;
                        }
                    }
            }
            if(booRet){
                if (strInsDet.length()>0 /*&& booCab && booDet && booDia*/){
                    //if(booCab ){ 
                      //  if( booDet){  
                        //    if( booDia){

                                /*
                                    booCab=insertarCab(CONN,intCodDoc,bgdTotCab, intEmpEmi,vecProv);
                                    booDet=insertarDet(CONN, strInsDet);
                                */

                                booCab=insertarCab(CONN,intCodDoc,bgdTotCab,intEmpEmi,vecProv);
                                if(booCab ){
                                    booDet=insertarDet(CONN, strInsDet);
                                    if(booDet){
                                        objTblModDia.setData(vecDataDia);
                                        //tblDatDiario.setModel(objTblModDia);

                                        intNumDoc=obtenerUltNumDoc(CONN, intEmpEmi, intCodLoc)+1;
                                        //actualizarGlo("RFP1E", String.valueOf(intNumDoc), String.valueOf(intNumDocAux),((String)vecProv.get(1)));
                                        actualizarGlo("RFP1E", String.valueOf(intNumDoc), String.valueOf(intNumDocAux),((String)vecProv.get(1)));
                                        objAsiDia.generarDiario(RFP1E,objTblModDia, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC,intEmpEmi, intCodLoc,CONN);
                                        //objAsiDia.seteaCtaCompRela(0, strCta, strTxCodCta, strTxDesLar);
                                        objAsiDia.seteaCtaCompRela(0, strCta, strTxCodCta, strTxDesLar);                
                                        booDia=objAsiDia.insertarDiario(CONN, intEmpEmi, intCodLoc, RFP1E, String.valueOf(intNumDoc), objUti.parseDate(obtenerUltDiaMes(),"yyyy-MM-dd"));                    
                                        if(booDia){
                                            booActCabMovInv= actualizarTbmCabMovInv(intEmpEmi,CONN);
                                            booRet=true;                                        
                                        }else{
                                            booRet=false;
                                            strMensajesErrorCorreo+="Error en insertarDiario Emisión Retenciones ";
                                                                    }                                    
                                    }else{
                                        booRet=false;
                                        strMensajesErrorCorreo+="Error en insertarDet Emisión Retenciones ";                        
                                    }
                                }else{
                                    booRet=false;
                                    strMensajesErrorCorreo+="Error en insertarCab Emisión Retenciones ";
                                }



                }   
            }
            
            if(booRet){
                if(booCab ){ 
                    if( booDet){  
                        if( booDia){  
                            if(booActCabMovInv){
                                //btnProcesar.setEnabled(false);
                                boolean booActPagMovInv=actualizarTbmPagMovInv(intEmpEmi,CONN);
                                if(booActPagMovInv){
                                    //CONN.commit();                
                                    //mostrarMsgInf("La operacion se realizó con éxito");
                                    inicializar();                
                                }else{
                                    booRet=false;
                                    strMensajesErrorCorreo+="Error en actualizarTbmPagMovInv Emisión Retenciones ";
                                }
                            }else{
                                booRet=false;
                                strMensajesErrorCorreo+="Error en actualizarTbmCabMovInv Emisión Retenciones ";
                            }
                        }else{
                            booRet=false;
                            strMensajesErrorCorreo+="Error en insertarDiario Emisión Retenciones ";
                        }
                    }else{
                        booRet=false;
                        strMensajesErrorCorreo+="Error en insertarDet Emisión Retenciones ";
                    }
                }else{
                    booRet=false;
                    strMensajesErrorCorreo+="Error en insertarCab Emisión Retenciones ";
                    //CONN.rollback();
                } 
            }
            
        
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
            strMensajesErrorCorreo+=ex.getMessage();
        }
        return booRet;
    }
    
    
    private void inicializar(){
        Vector vecData=new Vector();
        objTblMod.setData(vecData);//Datos vacios
        /*cmbEmpEmi.setSelectedIndex(0);
        cmbBeneficiario.setSelectedIndex(0);
        
        txtCodCta.setText("");
        txtDesCorCta.setText("");
        txtDesCta.setText("");
        dtpFecDoc.setText("");
        //valDoc.setText("");
        btnProcesar.setEnabled(false); */
        objAsiDia=new ZafAsiDia2(objParSis);
        objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
         public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
        //                    if (txtCodTipDoc.getText().equals(""))
        //                        objAsiDia.setCodigoTipoDocumento(-1);
        //                    else
                 objAsiDia.setCodigoTipoDocumento(RFP1E);
         }
        });
    }    
    
    private boolean actualizarTbmPagMovInv(int intEmpEmi,Connection CONN)
    {
        boolean booRes=true;
        double dblAbo1=0d, dblAbo2=0d;
        //int intIndEmpEmi=0--, intEmpEmi=0;
        String strSQL="";
        
        try{        
            Statement stm=CONN.createStatement();
            /*intIndEmpEmi=cmbEmpEmi.getSelectedIndex();
            Vector vecRegEmi=(Vector)vecEmpEmi.get(intIndEmpEmi);
            intEmpEmi=(Integer)vecRegEmi.get(0);*/
            

            for (int i=0;i<objTblMod.getRowCountTrue();i++)
            {
                if (objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC)!=objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO))
                    {
                        dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC));
                        dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_pagMovInv";
                        strSQL+=" SET nd_abo=nd_abo+" + intSig*objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos());
                        strSQL+=" WHERE co_emp=" + intEmpEmi;
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                        strSQL+=";";
                        strSQL+=" UPDATE tbm_detRecDoc";
                        strSQL+=" SET nd_valApl=" + objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos()) + " FROM(";
                        strSQL+="       SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                        strSQL+="       FROM tbm_pagMovInv AS a1 INNER JOIN tbr_detRecDocCabMovInv AS a2";
                        strSQL+="       ON a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel ";
                        strSQL+="       INNER JOIN tbm_detRecDoc AS a3";
                        strSQL+="       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc ";
                        strSQL+="       AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                        strSQL+="       WHERE a1.co_emp=" + intEmpEmi;
                        strSQL+="       AND a1.co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+="       AND a1.co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+="       AND a1.co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);

                        strSQL+="       AND a1.st_reg IN('A','C') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E')";
                        strSQL+="       AND a1.tx_numChq=a3.tx_numChq";
                        strSQL+="       AND a1.co_reg In(" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG) + ")";
                        strSQL+="       AND a1.tx_numChq='" + objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED) + "'";
                        strSQL+="       GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                        strSQL+=") AS x";
                        strSQL+=" WHERE tbm_detRecDoc.co_emp=x.co_emp AND tbm_detRecDoc.co_loc=x.co_loc AND tbm_detRecDoc.co_tipDoc=x.co_tipDoc";
                        strSQL+=" AND tbm_detRecDoc.co_doc=x.co_doc AND tbm_detRecDoc.co_reg=x.co_reg";
                        strSQL+=";";
                        stm.executeUpdate(strSQL);
                        objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                    }
                    //break;            
            }
        }catch(Exception ex){
            ex.printStackTrace();
            booRes=false;
        }
        return booRes;
    }    
    
    private boolean actualizarTbmCabMovInv(int intEmpEmi, Connection con)
    {
        int i;
        boolean blnRes=true;
        Statement stm=null;
        int intIndEmpEmi/*, intEmpEmi*/;
        String strSQL="";
        try
        {
            /*intIndEmpEmi=cmbEmpEmi.getSelectedIndex();
            Vector vecRegEmi=(Vector)vecEmpEmi.get(intIndEmpEmi);
            intEmpEmi=(Integer)vecRegEmi.get(0);*/
            
            if (con!=null)
            {
                stm=con.createStatement();
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_cabMovInv";
                        //strSQL+=" SET tx_secDoc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_SEC_DOC));
                        strSQL+=" SET tx_numPed=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED));
                        //strSQL+=", tx_numAutSRI=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_AUT));
                        //strSQL+=", tx_fecCad=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_FEC_CAD));
                        strSQL+=" WHERE co_emp=" + intEmpEmi;
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        stm.executeUpdate(strSQL);

                    }
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private void actualizarGlo(String strDesCorTipDoc, String strNumRet,String strNumOC, String strProv)
    {
        String strAux="";
        int i;
        strAux="";
        strAux+=strDesCorTipDoc + ": " + strNumRet;
        strAux+="; Beneficiario: " + strProv;
        strAux+="; Documentos: ";
        //for (i=0; i<objTblMod.getRowCountTrue(); i++)
        //    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                //strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) + ", ";
        strAux+=strNumOC;
        objAsiDia.setGlosa(strAux);
    }        
    
    private boolean insertarDet(Connection con,String strSql){
        Statement stm=null;
        boolean booRetorno=false;
        try{
            stm=con.createStatement();
            stm.executeUpdate(strSql);
            booRetorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            booRetorno=false;
        }
        return booRetorno;
    }  
    
    
    
    private boolean insertarCab(Connection con, int intCodDoc, BigDecimal bgdMonDoc, int intCodEmpEmi, Vector vecProv)
    {
        int intCodUsr, intUltReg, x=10;
        boolean blnRes=true;
        Statement stm,stmQry;
        ResultSet rstNumDoc;
        Date datFecAux;
        int intIndBene=0, intIndEmpEmi=0, intCodLocPre=0, intNumDoc=0, intMesAnterior, intUltDia=0,intAnio=0;
        int intCodProv=0;/*, intEmpEmi=0;*/
        String strDirProv="", strIdeProv="", strDesProv,strSqlNum="", strSQL="", strAux="";
        try
        {
            if (con!=null)
            {
   
                intCodProv=(Integer)vecProv.get(0);
                strDesProv=(String)vecProv.get(1);
                strIdeProv= (String)vecProv.get(2);
                strDirProv= (String)vecProv.get(3);
                stm=con.createStatement();
                intCodUsr=1;//objParSis.getCodigoUsuario();
                
                Calendar calFec=Calendar.getInstance();     
                //calFec.set(Calendar.YEAR, 2018);
                //calFec.set(Calendar.MONTH, Calendar.JULY);
                //calFec.set(Calendar.DAY_OF_MONTH, 3);                
                int intDia=calFec.get(Calendar.DATE);
                int intMes=0;
                if (intDia>=1 && intDia<=31){            
                    intMes=calFec.get(Calendar.MONTH);
                    intMes=intMes+1; // porque Enero =0
                    if (intMes==1){
                        intMes=12;
                        intAnio=calFec.get(Calendar.YEAR)-1;
                    }else {
                        intMes=intMes-1;
                        intAnio=calFec.get(Calendar.YEAR);
                    }
                }

                intUltDia=getUltimoDiaMes(intAnio, intMes-1);
                String strFecDoc=intAnio+"-"+String.format("%02d",intMes)+"-"+intUltDia;                
                
                intCodLocPre=obtenerLocPred(con, intCodEmpEmi);//co_loc
                intNumDoc=obtenerUltNumDoc(con, intCodEmpEmi, intCodLocPre)+1;
                datFecAux=getFechaServidor(objParSis.getQueryFechaHoraBaseDatos(),con);
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, fe_ven, ne_numDoc1, ne_numDoc2, co_cta, co_cli";
                strSQL+=", tx_ruc, tx_nomCli, tx_dirCli, nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, co_mnu, tx_comIng, tx_comMod)"; ///tx_secdoc, tx_numped, tx_numautsri, tx_feccad,
                strSQL+=" VALUES (";
                strSQL+=intCodEmpEmi;//co_emp
                strSQL+=", " + intCodLocPre;//co_loc
                strSQL+=", " + RFP1E;//co_tipDoc
                strSQL+=", " + intCodDoc;//co_doc
                strAux=strFecDoc;//fe_doc
                strSQL+=", '" + strAux + "'";//fe_ven
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intNumDoc;//ne_numDoc1
                strSQL+=", " + intNumDoc;//ne_numDoc2
                strSQL+=", null";//co_cta
                strSQL+=", " + intCodProv;//co_cli
                strSQL+=", " + codificar(strIdeProv);//tx_ruc
                strSQL+=", " + codificar(strDesProv);//tx_nomCli
                strSQL+=", " + codificar(strDirProv);//tx_dirCli
                strSQL+=", " +bgdMonDoc.multiply(new BigDecimal(intSig));               
                strSQL+=", ''" ;//tx_obs1
                strSQL+=", ''" ;//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());//fe_ing
                strSQL+=", '" + strAux + "'";
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intCodUsr;//co_usrIng
                strSQL+=", " + intCodUsr;//co_usrMod
                //strSQL+=", " + objParSis.getCodigoMenu() + "";//co_mnu
                strSQL+=", null ";//co_mnu
                strSQL+=", null ";
                strSQL+=", null ";
                strSQL+=")";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
            strMensajesErrorCorreo+=e.getMessage();
            blnRes=false;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            strMensajesErrorCorreo+=ex.getMessage();
            blnRes=false;
        }
        return blnRes;
    }        
    

    
public java.util.Date getFechaServidor(/*String stringConexion, String usuario, String clave, */String sentenciaSQL,Connection con)
    {
        //java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        java.util.Date datRes=null;
        try
        {
            //con=java.sql.DriverManager.getConnection(stringConexion,usuario,clave);
            if (con!=null)
            {
                stm=con.createStatement();
                rst=stm.executeQuery(sentenciaSQL);
                if (rst.next())
                    datRes=(java.util.Date)rst.getTimestamp(1);
                rst.close();
                stm.close();
                rst=null;
                stm=null;

            }
        }
        catch (java.sql.SQLException e)
        {
            datRes=null;
        }
        catch (Exception e)
        {
            datRes=null;
        }
        return datRes;        
    }    
    
    public String formatearFecha(java.util.Date fecha, String formato)
    {
        java.text.SimpleDateFormat objSimDatFor;
        String strRes="";
        try
        {
            objSimDatFor=new java.text.SimpleDateFormat(formato);
            //Formatear la fecha de acuerdo al formato.
            strRes=objSimDatFor.format(fecha);
            objSimDatFor=null;
        }
        catch (IllegalArgumentException e)
        {
            strRes="[ERROR]";
        }
        catch (Exception e)
        {
            strRes="[ERROR]";
        }
        return strRes;
    }
    
    
    public String codificar(Object cadena, int tipo)
    {
        String strAux="";
        switch (tipo)
        {
            case 0:
                //CADENA_NULL
                strAux="Null";
                break;
            case 1:
                //CADENA_VACIA
                strAux="''";
                break;
            case 2:
                //NUMERO_NULL
                strAux="Null";
                break;
            case 3:
                //NUMERO_CERO
                strAux="0";
                break;
        }
        if (cadena==null)
            return strAux;
        if (cadena.toString().equals(""))
            return strAux;
        if (tipo==2 || tipo==3)
            return cadena.toString();
        else
            return "'" + cadena.toString().replaceAll("'", "''") + "'";
    }    
    
    
    public String codificar(Object cadena)
    {
        if (cadena==null)
            return "Null";
        if (cadena.toString().equals(""))
            return "Null";
        return "'" + cadena.toString().replaceAll("'", "''") + "'";
    }
    
    private int obtenerUltNumDoc(Connection con, int intCodEmp, int intCodLoc){
        String strSqlNum="";
        Statement stmQry=null;
        ResultSet rst=null;
        int intNumDoc=0;
        try{
            stmQry=con.createStatement();
            strSqlNum+="select ne_ultdoc";
            strSqlNum+=" from tbm_cabtipdoc";
            strSqlNum+=" where co_emp="+intCodEmp+" and co_loc="+intCodLoc;
            strSqlNum+=" and co_tipdoc="+RFP1E;  
            rst=stmQry.executeQuery(strSqlNum);   
            while(rst.next()){
                intNumDoc=rst.getInt("ne_ultdoc");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intNumDoc;
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
    
    private int obtenerLocPred(Connection cnx,int intCodEmp){
        Statement stm=null;
        ResultSet rst=null;
        int intCodLoc=0;
        String strQryLoc="";
        try{
            strQryLoc="select st_reg,co_loc from tbm_loc where co_emp="+intCodEmp+" and st_reg='P'";
            stm=cnx.createStatement();
            rst=stm.executeQuery(strQryLoc);
            while(rst.next()){
                intCodLoc=rst.getInt("co_loc");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intCodLoc;
    }    
    
    
    public void abrirCon2(){
            try{
               //cnx=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
               cnx=java.sql.DriverManager.getConnection("jdbc\\:postgresql\\://172.16.8.110\\:5432/Zafiro2006_31_Ene_2019","postgres","*szlsc*");
               cnx.setAutoCommit(false);
            }catch(java.sql.SQLException  Evt){  
                //objUti.mostrarMsgErr_F1(this, Evt); 
                Evt.printStackTrace();
            }
    }
    
    
    public void abrirCon(){
        try{
/*            try { 
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }*/
            cnx = DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_31_Ene_2019","postgres", "*szlsc*");        
            cnx.setAutoCommit(false);

        }catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    
    

    private int obtenerNatTipDoc(Connection cnx,int intCodEmp, int intCodLoc){
        Statement stm=null;
        ResultSet rst=null;
        String strQryTipDoc="";
        String strNatDoc="";
        try{
            strQryTipDoc="select tx_natdoc,* from tbm_cabtipdoc where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+RFP1E;
            stm=cnx.createStatement();
            rst=stm.executeQuery(strQryTipDoc);
            while(rst.next()){
                strNatDoc=rst.getString("tx_natdoc");
            }
            if(strNatDoc.equals("I")){
                intSig=1;
            }else{
                intSig=-1;
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intCodLoc;
    }
    
    
    private void enviarPulsoServ14(){
        
        Socket so;
        String host="172.16.8.7";
        int puerto=6012;
        DataOutputStream mensaje;

    
         try {
             so=new Socket(host, puerto);
             mensaje=new DataOutputStream(so.getOutputStream());
             mensaje.writeInt(1);
             so.close();
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
    
        
    }
    
    /**
     * Metodo que permite 
     * @param con
     * @param intEmpEmi
     * @param intLocPre
     * @param intEmpCli
     * @param strFechaFinMes 
     */
    public void autorizarRetencionesEmitidas(Connection con,int intEmpEmi,int intLocPre,int intEmpCli, String strFechaFinMes){
        
        //actualizar el estado en tandas de 30
        String strSqlRet=" select * from tbm_cabpag "+
                         " where co_tipdoc=230 and co_emp="+intEmpEmi+ 
                         " and co_cli="+intEmpCli+" and fe_doc>='2018-04-01'"+
                         " and fe_doc<='"+strFechaFinMes+"'"+
                         " and st_autfacele='A' order by co_doc";
        
        
        String strUpd="Update tbm_cabpag set st_autfacele=null where co_emp="+intEmpEmi+" and co_loc="+intLocPre+" and co_tipdoc="+RFP1E+" and co_doc in(";
        Statement stmQryRetxAut=null;
        Statement stmUpd=null;
        ResultSet rstAutRet=null;
        boolean band=true;
        int intContReg=0, intContReg2=0;
        String strCodigos="";
        String strUpd2="";
        try{            
            stmQryRetxAut=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            stmUpd=con.createStatement();
            rstAutRet=stmQryRetxAut.executeQuery(strSqlRet);            
            rstAutRet.last();
            int intCantReg=rstAutRet.getRow();
            rstAutRet.beforeFirst();;
            while(band){                
                while(intContReg<10 && (band=(rstAutRet.next()))==true){
                    strCodigos+=rstAutRet.getInt("co_doc")+",";
                    intContReg++;intContReg2++;                    
                }
                strCodigos= strCodigos.substring(0, strCodigos.length()-1);
                strUpd2=strUpd;
                strUpd2+=strCodigos+")";
                stmUpd.executeUpdate(strUpd2);
                //enviar pulso servicio 14
                enviarPulsoServ14();
                Thread.sleep(3000);                
                intContReg=0;
                strCodigos="";
                strUpd2="";
            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }    
    }

    /*public static Connection getCnx() {
        return cnx;
    }*/
    
    
    
    
//    public static void main(String [] args){
//        
//        String str="TUVAL Armando S.A.";
//        System.out.println("String r "+str.substring(0, str.indexOf("S.A.")-1).toLowerCase());
//        //EmiRetComRel emit=new EmiRetComRel();
//        //emit.procesar();        
//        //emit.abrirCon2();
//        //emit.autorizarRetencionesEmitidas(getCnx(), 1, 4, 603, "2018-04-30");
//        int intUltDia=0,intMes=0,intAnio=0, intMesAnterior=0;
//        
//        Calendar calFec=Calendar.getInstance();
//        System.out.println("Fecha actual: " + calFec.getTime());
//        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println("Ultimo dia: "+ intUltDia);
//        intMes=calFec.get(Calendar.MONTH);       
//        intAnio=calFec.get(Calendar.YEAR);
//        calFec.add(Calendar.MONTH, -1);
//        System.out.println("Fecha actual: " + calFec.getTime());
//        intMesAnterior=calFec.get(Calendar.MONTH);
//        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
//        intAnio=calFec.get(Calendar.YEAR);
//        System.out.println("Año: " + intAnio);
//
//        
//        /*if(intMes==0){ // si es enero es el año anterior            
//            intAnio--;
//        }*/
//        intMesAnterior++;//ya que calendar cuenta los meses desde 0 siendo enero 0; diciembre es 11 
//        
//
//        String strSQL="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc \n";
//        strSQL+=", a1.fe_doc, a2.co_tipRet, a4.tx_desCor AS a4_tx_desCor, a4.tx_desLar AS a4_tx_desLar, a2.nd_porRet \n";
//        strSQL+=", a2.tx_aplRet, a1.nd_sub, a1.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen \n";
//        strSQL+=", a1.tx_numPed, a2.tx_codsri \n"; ///, a1.tx_codsri";
//        strSQL+=", a2.nd_basImp \n";
//        strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad, a2.tx_numChq \n";
//        strSQL+=" FROM tbm_cabMovInv AS a1 \n";
//        strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
//        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n";
//        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet) \n";
//        strSQL+=" WHERE a1.co_emp=${empresa} \n";
//        strSQL+=" AND a1.co_loc=${local} \n";
//        strSQL+=" AND a1.co_cli=${proveedor} \n";
//        strSQL+=" AND a1.st_reg IN ('A','R','C','F') \n";
//        strSQL+=" and a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" ;
//        strSQL+=" and tx_natdoc='I' \n";
//        strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 \n";
//        strSQL+=" AND a2.nd_porRet>0 \n";
//        strSQL+=" AND a2.st_reg IN ('A','C') \n";
//        strSQL+=" AND a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL \n";
//        strSQL+=" AND ( case when (length(a2.tx_numAutSri) <> 37 and length(a2.tx_numAutSri) <> 49) then a2.tx_fecCad IS NOT NULL \n "; //Si NumAutSri <> 37 o <> 49, significa que no es factura electronica y por tanto se debe validar el campo tx_fecCad
//        strSQL+="             else 1 = 1"; //Si es factura electronica; por tanto no se debe validar el campo tx_fecCad
//        strSQL+="       end ) \n";
//
//        strSQL+=" AND a2.tx_numChq IS NOT NULL \n";
//        strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg \n";
//        
//        System.out.println("String SQL "+strSQL);
//    }
    
}