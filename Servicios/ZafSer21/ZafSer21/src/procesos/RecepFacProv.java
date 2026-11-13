/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesos;

import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Vector;

/**
 *
 * @author Sistemas05
 */
public class RecepFacProv {
    
    
    private ZafTblMod objTblMod;
    Connection CONN_GLO;
    ZafUtil objUti; /**/
    final int RECFPR=177;
    ZafParSis objParSis;
    
    static final int INT_TBL_LINEA = 0;                            //NUMERO DE LINEAS
    static final int INT_TBL_CODPRV = 1;                           //CODIGO DEL PROVEEDOR
    static final int INT_TBL_BUTPRV = 2;                           //BOTON PARA BUSCAR PROVEEDOR
    static final int INT_TBL_NOMPRV = 3;                           //NOMBRE DEL PROVEEDOR
    static final int INT_TBL_NUMFAC = 4;                           //NUMERO DE FACTURA
    static final int INT_TBL_FECFAC = 5;                           //FECHA DE FACTURA
    static final int INT_TBL_VALIVA = 6;                           //VALOR IVA DE FACTURAS
    static final int INT_TBL_VALFAC = 7;                           //VALOR DE FACTURAS
    static final int INT_TBL_NUMSER = 8;                           //NUMERO  DE SERIE
    static final int INT_TBL_NUMAUT = 9;                           //NUMERO DE AUTORIZACION
    static final int INT_TBL_FECCAD = 10;                          //FECHA DE CADUCIDAD
    static final int INT_TBL_BUTOC = 11;                           //BOTON PARA BUSCAR ORDENES DE COMPRA
    static final int INT_TBL_TXTOBS = 12;                          //OBSERVACION
    static final int INT_TBL_CODREG = 13;                          //CODIGO DE REGISTRO
    static final int INT_TBL_DATFAC = 14;                          //DATOS DE LAS FACTURAS SELECCIONADAS
    static final int INT_TBL_VALFACORI = 15;                       //VALOR DE FACTURAS ORIGEN
    static final int INT_TBL_VAPLOC = 16;                          //VALOR APLICADO DE LAS ORDENES DE COMPRA
    static final int INT_TBL_ESTCAMFAC = 17;                       //estado si ha cambiado o agregado facturas.
    static final int INT_TBL_ESTAPL = 18;          
    
    private String strCodTipDocOpimpo="106";
    private String strCodTipDocOpcolo="246";
    private String strMensajesErrorCorreo="";    
    
    
    public RecepFacProv(Connection cnx) {
        try{
            objUti = new ZafUtil(); 
            objParSis=new ZafParSis();
            configurarTblMod();
            //abrirCon2();
            CONN_GLO=cnx;
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }    
    
    
    public RecepFacProv(ZafParSis objParSis, Connection cnx){
        try{
            objParSis= (ZafParSis)objParSis.clone();
            objUti=new ZafUtil();
            configurarTblMod();
            //abrirCon2ParSis();
            CONN_GLO=cnx;
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }    
    
    public boolean procesar(){
        boolean booAutRec=false;
        int intCodEmp=0, intCodLoc=11, intAnio=0, intCodEmpEmi=0, intCodLocEmi=11, intUltDiaMes=30;
        String strMes="06", strFacSel="";
        int[] empresas={1,2,4};
        String strQry1="";
        Vector vecData, vecReg;
        BigDecimal bgdTotRec=BigDecimal.ZERO;
        boolean booErr=false;
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        String strCorreosUsers="";

        try{
            /*Calendar calFecAct=Calendar.getInstance();
            int intDia=calFecAct.get(Calendar.DATE);
            int intMes=0;
            if (intDia>=1 && intDia<=31){            
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
            intUltDiaMes=getUltimoDiaMes(intAnio, intMes-1);
            Formatter fmtMes = new Formatter();
            fmtMes = new Formatter();

            strMes=fmtMes.format("%02d",intMes).toString(); */  
            
            
            Calendar calFec=Calendar.getInstance();  
            calFec.add(Calendar.MONTH, -1);
            int intMesAnterior=calFec.get(Calendar.MONTH);
            int intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
            intAnio=calFec.get(Calendar.YEAR);
            intMesAnterior++;//ya que calendar cuenta los meses desde 0 siendo enero 0; diciembre es 11 
            
            
            
            
            Statement stmConexion;
            ResultSet rsRecep=null;
            objTblMod=new ZafTblMod();

            String strQry="SELECT a.co_emp as empoc,a.co_loc as lococ, a.co_tipDoc as tipdococ, a.co_doc as dococ, a.ne_numDoc as ne_numdococ,a.fe_doc, a.co_cli, a.co_empDes, a.tx_nomCli, a.nd_porIva,  a.nd_sub,  a.nd_ValIva,  a.nd_tot, \n" +
                "       b.co_emp,b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numDoc,b.fe_doc, b.co_cli, b.co_empDes, b.tx_nomCli, b.nd_porIva, b.nd_sub, b.nd_ValIva as nd_valivafac, b.nd_tot as nd_totfac, b.tx_numAutFacEle, b.tx_secDoc/*, b.st_autFacEle*/, b.tx_desErrFacEle\n" +
                "  FROM \n" +
                "  (      \n" +
                "	SELECT Seg.co_Seg, a2.tx_ruc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, cfg.co_empDes, a1.tx_nomCli, a3.tx_ide,  a1.nd_porIva, a1.nd_sub,a1.nd_valIva, a1.nd_tot, a1.tx_numAutFacEle, a1.tx_tipMov\n" +
                "	FROM tbm_cabSegMovInv as Seg \n" +
                "	INNER JOIN tbm_cabMovInv as a1 ON (Seg.co_empRelCabMovInv=a1.co_emp AND Seg.co_locRelCabMovInv=a1.co_loc AND Seg.co_tipDocRelCabMovInv=a1.co_tipDoc AND Seg.co_docRelCabMovInv=a1.co_Doc and Seg.co_tipDocRelCabMovInv=2)\n" +
                "        INNER JOIN tbm_cfgEmpRel as cfg ON (cfg.co_empOrg=a1.co_emp AND cfg.co_cliEmpOrg=a1.co_cli)	\n" +
                "	INNER JOIN tbm_emp as a2 ON (a1.co_emp=a2.co_emp)\n" +
                "	INNER JOIN tbm_loc as loc ON (a1.co_Emp=loc.co_emp AND a1.co_loc=loc.co_loc)     \n" +
                "	INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)		\n" +
                "	WHERE a1.st_reg='A' AND tx_tipMov not in ('I') AND a1.co_tipDoc IN (2) \n" +
                //"	AND ((a1.co_emp=:intCodEmp and a1.co_loc=:intCodLoc) ) AND a1.fe_doc between '"+intAnio+"-"+strMes+"-27' AND '"+intAnio+"-"+strMes+"-"+intUltDiaMes+"'\n" +
                "	AND ((a1.co_emp=:intCodEmp and a1.co_loc=:intCodLoc) ) AND a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" +
                "	ORDER BY a1.co_tipDoc, a1.co_emp, a1.co_loc, cfg.co_empDes, a1.co_Doc	\n" +
                "  ) as a  --  FACCOM\n" +
                "  INNER JOIN (\n" +
                "	SELECT a1.co_usrIng, Seg.co_Seg, a2.tx_ruc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, cfg.co_empDes, a1.tx_nomCli, a3.tx_ide, a1.nd_porIva, a1.nd_sub, a1.nd_ValIva, a1.nd_tot, a1.tx_numAutFacEle, a1.tx_tipMov, loc.tx_secDoc, a1.st_autFacEle, a1.tx_desErrFacEle\n" +
                "	FROM tbm_cabSegMovInv as Seg\n" +
                "        INNER JOIN tbm_cabMovInv as a1 ON (Seg.co_empRelCabMovInv=a1.co_emp AND Seg.co_locRelCabMovInv=a1.co_loc AND Seg.co_tipDocRelCabMovInv=a1.co_tipDoc AND Seg.co_docRelCabMovInv=a1.co_Doc)\n" +
                "	INNER JOIN tbm_cfgEmpRel as cfg  ON (cfg.co_empOrg=a1.co_emp AND cfg.co_cliEmpOrg=a1.co_cli)    	\n" +
                "	INNER JOIN tbm_emp as a2 ON (a1.co_emp=a2.co_emp)\n" +
                "	INNER JOIN tbm_loc as loc ON (a1.co_Emp=loc.co_emp AND a1.co_loc=loc.co_loc)\n" +
                "	INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)            \n" +
                "	WHERE a1.st_reg='A' AND tx_tipMov not in ('I') AND  a1.co_tipDoc IN (228) 	\n" +
              //"	AND ((a1.co_emp=:intiCodEmpEmi and a1.co_loc=:intiCodLocEmi)) AND a1.fe_doc between '"+intAnio+"-"+strMes+"-27' AND '"+intAnio+"-"+strMes+"-"+intUltDiaMes+"'\n" +                                    
                "	AND ((a1.co_emp=:intiCodEmpEmi and a1.co_loc=:intiCodLocEmi)) AND a1.fe_doc between '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27' AND '"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'\n" +                
                "	ORDER BY a1.co_tipDoc, a1.co_emp, a1.co_loc, cfg.co_empDes, a1.co_Doc	\n" +
                "  ) as B ON ( a.tx_ruc=b.tx_ide AND a.nd_tot=ABS(b.nd_tot) AND a.fe_doc=b.fe_doc AND a.co_empDes=b.co_emp AND (CASE WHEN a.co_Seg IS NOT NULL THEN a.co_seg=b.co_seg END) /*AND a.nd_sub=ABS(b.nd_sub) */ )\n" +
                "  \n" +
                "  where not exists (select 1 from tbr_detrecdoccabmovinv where co_emprel=:intCodEmp and co_locrel=:intCodLoc and co_tipdocrel=a.co_tipdoc and co_docrel=a.co_doc ) \n"+                               
                "  ORDER BY a.co_emp, a.co_loc, a.co_empDes, a.co_Doc";        
            for (int j=0;j<3;j++){
                intCodEmp=empresas[j];
                intCodLoc=obtenerLocRel(intCodEmp);
                for(int k=0;k<3;k++){
                    if((empresas[k]!=empresas[j])){
                            intCodEmpEmi=empresas[k];
                            intCodLocEmi=obtenerLocRel(intCodEmpEmi);
                            strQry1=strQry.replace(":intCodEmp", Integer.toString(intCodEmp));
                            strQry1=strQry1.replace(":intiCodEmpEmi", Integer.toString(intCodEmpEmi));
                            strQry1=strQry1.replace(":intCodLoc", Integer.toString(intCodLoc));
                            strQry1=strQry1.replace(":intiCodLocEmi", Integer.toString(intCodLocEmi));
                            stmConexion=CONN_GLO.createStatement();
                            rsRecep=stmConexion.executeQuery(strQry1);
                            Formatter fmt = new Formatter();        
                            vecData=new Vector();
                            while (rsRecep.next()){
                                //rsRecep.
                                fmt = new Formatter();
                                vecReg=new Vector();
                                vecReg.add(INT_TBL_LINEA, "");
                                vecReg.add(INT_TBL_CODPRV, rsRecep.getString("co_cli"));
                                vecReg.add(INT_TBL_BUTPRV, "");
                                vecReg.add(INT_TBL_NOMPRV, rsRecep.getString("tx_nomcli"));
                                vecReg.add(INT_TBL_NUMFAC, fmt.format("%09d",Integer.parseInt(rsRecep.getString("ne_numdoc")) ));
                                vecReg.add(INT_TBL_FECFAC, objUti.formatearFecha(rsRecep.getDate("fe_doc"), "yyyy-MM-dd"));
                                vecReg.add(INT_TBL_VALIVA, rsRecep.getString("nd_valiva"));
                                vecReg.add(INT_TBL_VALFAC, rsRecep.getString("nd_tot"));
                                vecReg.add(INT_TBL_NUMSER, rsRecep.getString("tx_secdoc"));
                                vecReg.add(INT_TBL_NUMAUT, rsRecep.getString("tx_numAutFacEle"));
                                vecReg.add(INT_TBL_FECCAD, "");
                                vecReg.add(INT_TBL_BUTOC, "");
                                vecReg.add(INT_TBL_TXTOBS, rsRecep.getString("ne_numdococ"));
                                vecReg.add(INT_TBL_CODREG,"");
                                strFacSel="SELECT "+rsRecep.getInt("empoc")+" as coemp,"+rsRecep.getInt("lococ")+" as coloc,"+
                                                  rsRecep.getInt("tipdococ")+" as cotipdoc,"+rsRecep.getInt("dococ")+" as codoc," +
                                                  ""+rsRecep.getString("nd_tot")+" as valapl "+
                                                  " "; 

                                vecReg.add(INT_TBL_DATFAC,strFacSel);
                                vecReg.add(INT_TBL_VALFACORI,"");
                                vecReg.add(INT_TBL_VAPLOC,rsRecep.getString("nd_tot"));
                                vecReg.add(INT_TBL_ESTCAMFAC,"S");
                                vecReg.add(INT_TBL_ESTAPL,"");
                                vecData.add(vecReg);
                            }
                            objTblMod.setData(vecData);
                            bgdTotRec=calculaTotMonChqAut();
                            //booAutRec=insertarAut(intCodEmp, intCodLoc, intAnio+"-"+strMes+"-"+intUltDiaMes, bgdTotRec);
                            booAutRec=insertarAut(intCodEmp, intCodLoc, intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia, bgdTotRec);
                            if(!booAutRec){
                                CONN_GLO.rollback();
                                booErr=true;//Existio un problema salir del For
                                booAutRec=false;
                                break;
                            }
                    }else{
                        continue;
                    }                    
                }
                if(booErr){
                    break;
                }
            }
            if(!booErr){
                CONN_GLO.commit();
                booAutRec=true;
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            strMensajesErrorCorreo+=ex.getMessage();
            booAutRec=false;
            try{
                CONN_GLO.rollback();
            }catch(SQLException ex2){
                ex2.printStackTrace();
                strMensajesErrorCorreo+=ex2.getMessage();
            }
        }catch (Exception Evt) {
            Evt.printStackTrace();
            strMensajesErrorCorreo+= Evt.getMessage();
            booAutRec=false;
        }finally{
            if(strMensajesErrorCorreo.length()>0){
                strCorreosUsers="sistemas5@tuvalsa.com";
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - ERRORES EN RECEPCION FACTURAS PROVEEDORES - ", strMensajesErrorCorreo);
            }
        }     
        return booAutRec;
    }  
    
    private int obtenerLocRel(int intCodEmp){
        int intCodLoc=0;
        if(intCodEmp==1 || intCodEmp==4){
            intCodLoc=11;
        }else if(intCodEmp==2){
            intCodLoc=5;
        }
        return intCodLoc;
    }    
    
    public void abrirCon2(){
        try{
           CONN_GLO=java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_31_Ene_2019","postgres","*szlsc*");
           CONN_GLO.setAutoCommit(false);
        }catch(java.sql.SQLException  evt){            
            evt.printStackTrace();
        }
    } 
    
    
    public void abrirCon2ParSis(){
        try{
           CONN_GLO=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           CONN_GLO.setAutoCommit(false);
        }catch(java.sql.SQLException  evt){            
            evt.printStackTrace();
        }
    }     
    
    public void cerrarCon2(){
        try{
           CONN_GLO.close();
           CONN_GLO=null;
        }catch(java.sql.SQLException  evt){  
            evt.printStackTrace();
        }
    }      
    
    private int getUltimoDiaMes(int anio, int mes)
    {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, anio);
        cal.set(java.util.Calendar.MONTH, mes);
        cal.set(java.util.Calendar.DATE, 1);
        return cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }
    
    public BigDecimal calculaTotMonChqAut() {
        //double dblMonChq = 0;
        BigDecimal bgdMon=BigDecimal.ZERO, bgdTot=BigDecimal.ZERO;
        try {
            for (int i = 0; i < objTblMod.getRowCount(); i++) {
                if (!((objTblMod.getValueAt(i, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODPRV).toString())).equals(""))) {
                    bgdMon=((objTblMod.getValueAt(i, INT_TBL_VALFAC) == null) ? BigDecimal.ZERO : new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString()));
                    bgdTot=bgdTot.add(bgdMon);
                }
            }
            bgdTot=bgdTot.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bgdTot;
    }  
    
    
    public boolean insertarAut(int intCodEmpRec, int intCodLocRec,String strFecFinMes,BigDecimal bgTot ) {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        int intCodDoc = 0, intEmpInd=0, intCodEmp=0, intCodLoc=0;
        Vector vecReg;
        try {
            if (CONN_GLO != null) {
                stmLoc = CONN_GLO.createStatement();

                strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabrecdoc WHERE "
                        + " co_emp=" + intCodEmpRec + " and co_loc=" + intCodLocRec + " "
                        + " and co_tipDoc = " + RECFPR;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodDoc = rstLoc.getInt("co_doc");
                }
                rstLoc.close();
                rstLoc = null;

                if(validarInfoAut()){
                    if (insertarCabAut(CONN_GLO, intCodDoc, intCodEmpRec, intCodLocRec,strFecFinMes,bgTot)) {
                        if (insertarDetRecAut(CONN_GLO, intCodDoc, intCodEmpRec, intCodLocRec,strFecFinMes)) {
                            blnRes = true;
                            mostrarMsgInf("La operacion se realizó con éxito");
                            inicializar();

                        } else {
                            strMensajesErrorCorreo+="Error en insertarDetRecAut Recepción Facturas Proveedor";                            
                            blnRes=false;
                        }
                    } else {
                        strMensajesErrorCorreo+="Error en insertarCabAut Recepción Facturas Proveedor";                                                    
                        blnRes=false;                        
                    }
                }else{
                    blnRes=false;
                }

                stmLoc.close();
                stmLoc = null;
            }

        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        } catch (Exception Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        }
        return blnRes;
    }    
    
    public boolean validarInfoAut(){
        boolean booRet=true;        
        
        if(!(objTblMod.getRowCount()>0)){
            booRet=false;
            strMensajesErrorCorreo+="No Hay registros para procesar";            
        }
        
        for (int i = 0; i < objTblMod.getRowCount(); i++) {
            if (((objTblMod.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString())).equals(""))) {
                booRet=false;
                strMensajesErrorCorreo+="Hay facturas que no tienen autorizacion, Corregir";
                break;
            }
        }
        
        return booRet;
    }    
    
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
       
        //oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }  
    
    private boolean insertarCabAut(java.sql.Connection conn, int intCodDoc, int intCodEmp, int intCodLoc, String strFechFinMes, BigDecimal bgdTot ) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        String strFecDoc = "";
        int intNumdoc=0;
        
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                //intNumdoc=obtenerNumDocRece(conn);
                //************  PERMITE SABER SI EL NUMERO DE recepción ESTA DUPLICADO  *****************/
                strSql = "select count(ne_numdoc1) as num from tbm_cabrecdoc WHERE "
                        + " co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " "
                        + "and co_tipdoc=" + RECFPR + " and ne_numdoc1=" + intCodDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (rstLoc.getInt("num") >= 1) {
                        blnRes = true;
                    }
                }
                rstLoc.close();
                rstLoc = null;
                if (blnRes) {
                    strMensajesErrorCorreo+="Ya existe registro de recepción de factura con el mismo numero";
                    return false;
                }
                blnRes = false;
                //***********************************************************************************************/

                strSql = "INSERT INTO tbm_cabrecdoc(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_usrrec, st_imp, tx_obs1, tx_obs2 "
                        + " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, st_regrep ) "
                        + " VALUES(" + intCodEmp + ", " + intCodLoc + ", " + RECFPR + ", " + intCodDoc + " "
                        + " ,'" + strFechFinMes + "', " + intCodDoc + ", null, 'N', '','' "
                        + " ," + (bgdTot) + ", 'A', current_timestamp , 1 "
                        + " ,null, 'I' ) ; ";

                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        } catch (Exception Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        }
        return blnRes;
    } 
    
    private boolean insertarDetRecAut(java.sql.Connection conn, int intCodDoc, int intCodEmp, int intCodLoc, String strFecFinMes) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        String strFecDoc = "";
        String strEstasidocrel = "N";
        String strNumAutSri = "", strSecDoc = "", strFecCad = "", strCodCli = "";
        int intNumReg = 0;
        double dblValFacPrv = 0, dblValIvaFacPrv = 0;
        double dblValOC = 0;
        int intConUpCli = 0;
        Calendar fechaAct=Calendar.getInstance();
        int intAnioCal=0, intMesCal=0, intMesCalCmpVen,intUltDiaMes;
        StringBuffer strSqlDetUpdCli=new StringBuffer("");
        Formatter fmtMes = new Formatter();

        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                
                intMesCal=fechaAct.get(Calendar.MONTH);
                
                intMesCal=intMesCal+1; // porque Enero =0
                    if (intMesCal==1){
                        intMesCal=12;
                        intAnioCal=fechaAct.get(Calendar.YEAR)-1;
                    }else {
                        intMesCal=intMesCal-1;
                        intAnioCal=fechaAct.get(Calendar.YEAR);
                    }
                
                intUltDiaMes=getUltimoDiaMes(intAnioCal, intMesCal-1);                

                strFecDoc = intAnioCal+"/"+fmtMes.format("%02d",intMesCal).toString()+"/"+intUltDiaMes;                

                for (int i = 0; i < objTblMod.getRowCount(); i++) {
                    if (!((objTblMod.getValueAt(i, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODPRV).toString())).equals(""))) {

                        strEstasidocrel = "N";

                        strCodCli = objTblMod.getValueAt(i, INT_TBL_CODPRV).toString();
                        strNumAutSri = (objTblMod.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString()));
                        strSecDoc = (objTblMod.getValueAt(i, INT_TBL_NUMSER) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NUMSER).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMSER).toString()));
                        strFecCad = (objTblMod.getValueAt(i, INT_TBL_FECCAD) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_FECCAD).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_FECCAD).toString()));

                        dblValIvaFacPrv = objUti.redondear((objTblMod.getValueAt(i, INT_TBL_VALIVA) == null ? "0" : (objTblMod.getValueAt(i, INT_TBL_VALIVA).toString().equals("") ? "0" : objTblMod.getValueAt(i, INT_TBL_VALIVA).toString())), 2);
                        dblValFacPrv = objUti.redondear((objTblMod.getValueAt(i, INT_TBL_VALFAC) == null ? "0" : (objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("") ? "0" : objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), 2);

                        dblValOC = objUti.redondear((objTblMod.getValueAt(i, INT_TBL_VAPLOC) == null ? "0" : (objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString().equals("") ? "0" : objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString())), 2);
                        if (dblValOC == dblValFacPrv) {
                            strEstasidocrel = "S";
                        }

                        intNumReg++;
                        strSql = "INSERT INTO tbm_detrecdoc(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_cli, co_banchq,  tx_numchq, fe_recchq, "
                                + " fe_venchq, nd_valiva,  nd_monchq, tx_obs1, nd_valapl, st_asidocrel,  st_regrep , st_reg, tx_numser, tx_numautsri, tx_feccad, nd_valtotasi  ) "
                                + " VALUES(" + intCodEmp + ", " + intCodLoc + ", " + RECFPR + ", " + intCodDoc + " "
                                + " ," + intNumReg + ", " + objTblMod.getValueAt(i, INT_TBL_CODPRV).toString() + ", null  "
                                + " , '" + objTblMod.getValueAt(i, INT_TBL_NUMFAC).toString() + "' "
                                + ", '" + strFecFinMes + "', '" + objTblMod.getValueAt(i, INT_TBL_FECFAC).toString() + "' "
                                + ", " + dblValIvaFacPrv + ", " + dblValFacPrv + ", '" + (objTblMod.getValueAt(i, INT_TBL_TXTOBS) == null ? "" : objTblMod.getValueAt(i, INT_TBL_TXTOBS).toString()) + "' "
                                + ", 0  "
                                + " , '" + strEstasidocrel + "' ,'I', 'A', '" + strSecDoc + "', '" + strNumAutSri + "', '" + strFecCad + "', " + dblValOC + " )  ;  ";

                        if (!((objTblMod.getValueAt(i, INT_TBL_DATFAC) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_DATFAC).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_DATFAC).toString())).equals(""))) {

                            strSql += "INSERT INTO tbr_detrecdoccabmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel,"
                                    + " co_tipdocrel, co_docrel, nd_valasi, st_reg,  st_regrep) "
                                    + " select " + intCodEmp + ", " + intCodLoc + ", " + RECFPR + ", " + intCodDoc + " ," + intNumReg + ",  coemp, coloc, cotipdoc, codoc,  valapl,  'A', 'I' from( "
                                    + objTblMod.getValueAt(i, INT_TBL_DATFAC).toString() + " ) as x  ; ";

                            if (!actualizaPagMovInvAut(conn, objTblMod.getValueAt(i, INT_TBL_DATFAC).toString(), dblValFacPrv, i, strFecDoc, dblValIvaFacPrv)) {
                                blnRes = false;
                                break;
                            }
                        }
                        stmLoc.executeUpdate(strSql);

                        strSqlDetUpdCli=new StringBuffer(" SELECT " + intCodEmp + " as coemp, " + strCodCli + " as cocli, text('" + strSecDoc + "')  as numser, text('" + strNumAutSri + "')  as numautsri,text('" + strFecCad + "')  as feccadsri   ");
                        intConUpCli = 1;

                    }
                }

                if (intConUpCli > 0 && blnRes) {

                    strSql = " UPDATE tbm_cli SET  tx_numserfacprv=x.numser "
                            + " ,tx_numautsrifacprv=x.numautsri, tx_feccadfacprv=x.feccadsri  FROM ( "
                            + " select x.*,  x1.tx_numserfacprv, x1.tx_numautsrifacprv, x1.tx_feccadfacprv from ( "
                            + " select * from ( " + strSqlDetUpdCli.toString() + " ) AS x "
                            + " group by coemp, cocli, numser, numautsri, feccadsri "
                            + " ) as x "
                            + " inner join tbm_cli as x1 on (x1.co_emp=x.coemp and x1.co_cli=x.cocli ) "
                            + " WHERE  "
                            + "  ( x1.tx_numserfacprv is null or  x1.tx_numserfacprv != x.numser ) "
                            + " or ( x1.tx_numautsrifacprv is null or  x1.tx_numautsrifacprv != x.numautsri ) "
                            + " or ( x1.tx_feccadfacprv is null or  x1.tx_feccadfacprv != x.feccadsri ) "
                            + " ) AS x WHERE  tbm_cli.co_emp=x.coemp and tbm_cli.co_cli=x.cocli  ";
                    stmLoc.executeUpdate(strSql);
                }

                stmLoc.close();
                stmLoc = null;

            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        } catch (Exception Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        }
        return blnRes;
    } 
    

    private boolean actualizaPagMovInvAut(java.sql.Connection conn, String strSql, double dblValFacPrv, int intNumFilTbl, String strFecDoc, double dblValIvaFacPrv) {
        boolean blnRes = true;
        java.sql.Statement stm, stm1;
        java.sql.ResultSet rst;
        String strNumFac = "", strNumSer = "", strNumAut = "", strFecCad = "", strFecFac="", strAux="";
        String strSqlUp = "";
        try {
            if (conn != null) {
                stm = conn.createStatement();
                stm1 = conn.createStatement();

                strNumFac = (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMFAC) == null ? "0" : (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMFAC).toString().equals("") ? "0" : objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMFAC).toString()));
                strNumSer = (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMSER) == null ? "0" : (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMSER).toString().equals("") ? "0" : objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMSER).toString()));
                strNumAut = (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMAUT) == null ? "0" : (objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMAUT).toString().equals("") ? "0" : objTblMod.getValueAt(intNumFilTbl, INT_TBL_NUMAUT).toString()));
                strFecCad = (objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECCAD) == null ? "0" : (objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECCAD).toString().equals("") ? "0" : objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECCAD).toString()));
                
                strAux = (objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECFAC) == null ? "0" : (objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECFAC).toString().equals("") ? "0" : objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECFAC).toString()));
                strFecFac= objUti.formatearFecha(strAux, "dd/MM/yyyy", "yyyy-MM-dd") ; 
                

                strSql = "select x.*, a.nd_tot from ( "
                        + " " + strSql + " "
                        + " ) as x inner join tbm_cabmovinv as a on (x.coemp=a.co_emp and x.coloc=a.co_loc and x.cotipdoc=a.co_tipdoc and x.codoc=a.co_doc )  ";
                rst = stm.executeQuery(strSql);
                while (rst.next()) {
                    if (rst.getDouble("nd_tot") == rst.getDouble("valapl")) {  // dblValFacPrv){

                        strSqlUp = "UPDATE tbm_pagmovinv set "
                                + "  st_entSop='S', tx_numChq='" + strNumFac + "',fe_recChq='" + /*objUti.formatearFecha(*/objTblMod.getValueAt(intNumFilTbl, INT_TBL_FECFAC).toString()/*, "dd/MM/yyyy", "yyyy/MM/dd")*/ + "' "
                                + " ,fe_venChq='" + strFecDoc + "', nd_valiva=" + dblValIvaFacPrv + " ,nd_monChq=" + dblValFacPrv + ",tx_numSer='" + strNumSer + "',tx_numAutSRI='" + strNumAut + "',tx_fecCad='" + strFecCad + "'    "
                                + "  WHERE co_emp=" + rst.getInt("coemp") + "  and co_loc=" + rst.getInt("coloc") + " and co_tipdoc=" + rst.getInt("cotipdoc") + " "
                                + " and co_doc=" + rst.getInt("codoc") + "  "; // and nd_porret != 0 ";
                        stm1.executeUpdate(strSqlUp);
                        // actualiza pago

                            //Rose: Actualiza fecha de vencimiento: OPIMPO y OPCOLO
                            if(rst.getString("cotipdoc").equals(strCodTipDocOpimpo)  || rst.getString("cotipdoc").equals(strCodTipDocOpcolo) ){
                                strSqlUp+=" UPDATE tbm_pagmovinv set fe_ven=(cast('"+strFecFac+"' as date) + ne_diaCre) ";
                                strSqlUp+=" WHERE co_emp=" + rst.getInt("coemp") + "";
                                strSqlUp+=" AND co_loc=" + rst.getInt("coloc")+" ";
                                strSqlUp+=" AND co_tipdoc=" + rst.getInt("cotipdoc") + " ";
                                strSqlUp+=" AND co_doc=" + rst.getInt("codoc") + " ";      
                                //Solo actualiza cuando no han sido emitidas las retenciones , ni existe autorización de pagos.
                                strSqlUp+=" AND (mo_pag+nd_abo) <>0 AND st_autPag IN ('N');" ; 
                                System.out.println("actualizaPagMovInv: "+strSqlUp);
                                stm1.executeUpdate(strSqlUp);
                            }

                    } 

                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;

            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        } catch (Exception Evt) {
            blnRes = false;
            strMensajesErrorCorreo+= Evt.getMessage();
        }
        return blnRes;
    }    
    
    
//    private boolean agregarCambioCuentasContables(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi) {
//        boolean blnRes = false;
//        try {
//            if (conn != null) {
//
//                //Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans(jfrThis, objParSis);
//                Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans(null, objParSis);
//
//                if (objCtaTrans.cambioCtaConIvaOC(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblValAsi)) {
//                    if (objCtaTrans.cambioCtaConProOC(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblValAsi)) {
//                        if (objCtaTrans.agrecambioCtaConDevCom(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) {
//                            blnRes = true;
//                        }
//                    }
//                }
//
//                objCtaTrans = null;
//
//            }
//        } catch (Exception Evt) {
//            blnRes = false;
//            //objUti.mostrarMsgErr_F1(this, Evt);
//            Evt.printStackTrace();
//        }
//        return blnRes;
//    }     
    
    
    
    private void configurarTblMod(){
    
        Vector vecCab=null;
        vecCab=new Vector(26);  //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CODPRV,"");
        vecCab.add(INT_TBL_BUTPRV,"Cód.Loc.");//no sale
        vecCab.add(INT_TBL_NOMPRV,"Cód.Tip.Doc");//no sale
        vecCab.add(INT_TBL_NUMFAC,"Tip.Doc.");//tipDoc//
        vecCab.add(INT_TBL_FECFAC,"Tipo de documento");//tipo de documento//
        vecCab.add(INT_TBL_VALIVA,"Cod.Doc.");//no sale
        vecCab.add(INT_TBL_VALFAC,"Cod.Reg.");//no sale
        vecCab.add(INT_TBL_NUMSER,"Num.Doc.");//NumDoc//
        vecCab.add(INT_TBL_NUMAUT,"Fec.Doc.");//FecDoc//
        vecCab.add(INT_TBL_FECCAD,"Cód.Tip.Ret");//no sale
        vecCab.add(INT_TBL_BUTOC,"Tip.Ret.");//TipRet//
        vecCab.add(INT_TBL_TXTOBS,"");//no sale
        vecCab.add(INT_TBL_CODREG,"Tipo de retención");//Tipo de Retencion//
        vecCab.add(INT_TBL_DATFAC,"%");//Simbolo POrcentaje//
        vecCab.add(INT_TBL_VALFACORI,"Aplicado");//Aplicacion de Retencion//
        vecCab.add(INT_TBL_VAPLOC,"Bas.Imp.");//Base Imponible//
        vecCab.add(INT_TBL_ESTCAMFAC,"Val.Ret.");//Valor de la Retencion//
        vecCab.add(INT_TBL_ESTAPL,"Val.Pen.");//Valor Pendiente//
        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
    }    
    
    private void inicializar(){
        Vector vecData=new Vector();
        objTblMod.setData(vecData);//Datos vacios 
    }    
    
}
