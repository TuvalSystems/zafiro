/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMovInv;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author sistemas3
 */
public class ZafMovInv {
    //ingreso y egreso
    private int intCodEmp;//Código de empresa
    private int intCodLoc;//Código de local
    private int intCodTipDoc;//Código de tipo de documento
    private int intCodDoc;//Código de documento
    private int intNumDoc;
    private int intCodCliPrv;
    //Bodega
//    private int intCodBodRegUno;//Código de Bodega origen de la empresa(Depende del tipo de préstamo)
//    private int intCodBodRegDos;//Código de la bodega de ingreso (SOLO SE USA EN TRANSFERENCIAS)
//    private String strNomBodRegUno;//Se usa en egreso o en ingreso de : préstamos o relacionadas(Facturación)
//    private String strNomBodRegDos;//Se usa en transferencias para Egreso&Ingreso

    private String strSQL;
    
    private BigDecimal bgdValDocSub;//subtotal
    private BigDecimal bgdValDocIva;//iva
    private BigDecimal bgdValDocTot;//total
    private BigDecimal bgdValPorIva;//porcentaje de iva
    private Date dtpFecDoc;
    private char chrGenIva;
    
    private ArrayList arlDatItm;
    private static final int INT_ARL_COD_EMP=0;
    private static final int INT_ARL_COD_LOC=1;
    private static final int INT_ARL_COD_TIP_DOC=2;
    private static final int INT_ARL_COD_CLI_PRV=3;
    private static final int INT_ARL_COD_BOD_ORI_DES=4;
    private static final int INT_ARL_COD_ITM_GRP=5;
    private static final int INT_ARL_COD_ITM_EMP=6;
    private static final int INT_ARL_COD_ITM_MAE=7;    
    private static final int INT_ARL_COD_ALT_ITM=8;
    private static final int INT_ARL_NOM_ITM=9;
    private static final int INT_ARL_UNI_MED_ITM=10;
    private static final int INT_ARL_COD_LET_ITM=11;    
    private static final int INT_ARL_CAN_ITM=12;
    private static final int INT_ARL_COS_UNI=13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PRE_UNI=14;
    private static final int INT_ARL_PES_UNI=15;
    private static final int INT_ARL_IVA_COM_ITM=16;
    
    private Statement stm;
    private ResultSet rst;
    
    private ZafUtil objUti;
    private ZafParSis objParSis;
    private ZafAsiDia objAsiDia;
    
    private BigDecimal bdeSig;
    private int intNumSecEmp;
    private int intNumSecGrp;
    
    private int intNumDec;//variable global que tendrá el número de decimales a usar en todo el programa.    
    
    private int intCodForPag;
    private int intNumPag;
    
    //para datos de pago            CFP = CABECERA DE FORMA DE PAGO
    private ArrayList arlRegCabForPag, arlDatCabForPag;
//    private int INT_ARL_CFP_COD_CAB_FOR_PAG=0;
//    private int INT_ARL_CFP_NOM_CAB_FOR_PAG=1;
//    private int INT_ARL_CFP_NUM_PAG=2;
    private int INT_ARL_CFP_COD_SRI=0;
    private int INT_ARL_CFP_COD_TIP_RET=1;
    private int INT_ARL_CFP_DES_COR_TIP_RET=2;
    private int INT_ARL_CFP_DES_LAR_TIP_RET=3;
    private int INT_ARL_CFP_POR_RET=4;
    private int INT_ARL_CFP_APL_RET=5;
    private int INT_ARL_CFP_COD_CTA=6;
    
    //Detalle de forma de pago      DFP = DETALLE DE FORMA DE PAGO
    
    //a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop
    private ArrayList arlRegDetForPag, arlDatDetForPag;
    private int INT_ARL_DFP_COD_EMP=0;
    private int INT_ARL_DFP_COD_PAG=1;
    private int INT_ARL_DFP_COD_REG=2;
    private int INT_ARL_DFP_DIA_CRE=3;
    private int INT_ARL_DFP_EST_SOP=4;
    
    private int intTipMov;
    
    /**
     * Función que permite obtener el objeto ZafParSis
     * @param obj El objeto ZafParSis
     */
    public ZafMovInv(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            objAsiDia = new ZafAsiDia(objParSis);
            intNumDec=objParSis.getDecimalesBaseDatos();
            arlDatCabForPag=new ArrayList();
            arlDatDetForPag=new ArrayList();
            intTipMov=0;// 0= un solo movimiento. Ejm: Egreso  ......  1= un documento pero en el detalle cantidad por negativo y por positivo. Ejm: Transferencias
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafMovInv: " + e);
            objUti.mostrarMsgErr_F1(null, e);
        }
    }   


    
    /**
     * Función que permite obtener datos necesarios para generar el documento
     * @param con                       Conexión enviada
     * @param dtpFechaDocumento         <HTML>Fecha de documento con la que se guardará el documento. <BR>Formato: dd/MM/yyyy</HTML>
     * @param chrGenerarIva             Estado que permitirá saber si hay que calcular valor de iva. Según tipo de documento. Por Préstamos ='N', por Facturación/Compra ='S'
     * @param arlDatosItemEgreso        <HTML>Información del item:
     *                                      <BR>Código de empresa donde se guardará el documento
     *                                      <BR>Código de local donde se guardará el documento
     *                                      <BR>Código de tipo de documento donde se guardará el documento
     *                                      <BR>Cliente/Proveedor a quien se le genera el documento
     *                                      <BR>Código de bodega donde se ingresará/egresará la mercadería
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item en negativo
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
     *                                  </HTML>
     * @param arlDatosItemIngreso       <HTML>Información del item:
     *                                      <BR>Código de empresa donde se guardará el documento
     *                                      <BR>Código de local donde se guardará el documento
     *                                      <BR>Código de tipo de documento donde se guardará el documento
     *                                      <BR>Cliente/Proveedor a quien se le genera el documento
     *                                      <BR>Código de bodega donde se ingresará/egresará la mercadería
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item en positivo
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
     *                                  </HTML>
     * @return 
     */
    public boolean getDatoEgresoIngreso(Connection con, Date dtpFechaDocumento, char chrGenerarIva
            , ArrayList arlDatosItemEgreso, ArrayList arlDatosItemIngreso){
        
        boolean blnRes=false;
        try{
            if(con!=null){
                intTipMov=0;
                dtpFecDoc=dtpFechaDocumento;
                chrGenIva=chrGenerarIva;
                stm=con.createStatement();
                
                if(arlDatosItemEgreso.size()>0){
                    intCodEmp=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP);
                    intCodLoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_LOC);
                    intCodTipDoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_TIP_DOC);
                    intCodCliPrv=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_CLI_PRV);
                    //intCodBodRegUno=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_BOD);
                    //System.out.println("ZafMovInv.getDatoEgresoIngreso EGRESO: " + intCodBodRegUno);
                    arlDatItm=arlDatosItemEgreso;
                    
                    //código
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                    else
                        return false;

                    //número
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
                    else
                        return false;

                    //signo para generar el documento
                    strSQL="";
                    strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                    else
                        return false;

                    //para obtener secuencia de empresa
                    strSQL="";
                    strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                    strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumSecEmp=rst.getInt("ne_numSecEmp");

                    //para obtener secuencia de grupo
                    strSQL="";
                    strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                    strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumSecGrp=rst.getInt("ne_numSecGrp");
                                        
                    //--Forma de pago
                    strSQL="";
                    strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag";
                    strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                    strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                    strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    System.out.println("Forma de pago: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        intNumPag=rst.getInt("ne_numPag");
                        intCodForPag=rst.getInt("co_forPag");
                    }
                    else
                        return false;
                    
                    //--Retenciones
                    if(chrGenIva=='S'){//no debe generar retenciones por iva, por tanto: solo aplica a SUBTOTAL
                        //strSQL+=" AND tx_aplRet='S'";
                        strSQL="";
                        strSQL+="SELECT a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" FROM tbm_motDoc AS a1 LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                        strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                        strSQL+=" LEFT OUTER JOIN tbm_emp AS a5";
                        strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                        strSQL+=" LEFT OUTER JOIN tbm_tipPer AS a3";
                        strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                        strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                        strSQL+=" ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                        strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND CURRENT_DATE BETWEEN a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                        strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                        strSQL+=" AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                        strSQL+=" AND a1.co_mot=1";//quemado: para bienes
                        strSQL+=" AND a6.co_cli=" + intCodCliPrv + "";
                        strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet , a9.tx_desCor, a9.tx_desLar";
                        strSQL+=" , a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" ORDER BY a2.co_tipRet, a1.co_mot, a1.tx_desCor, a2.tx_codSri";
                        System.out.println("Retenciones-egreso: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        arlDatCabForPag.clear();
                        if(rst.next()){
                            arlRegCabForPag=new ArrayList();
//                            arlRegCabForPag.add(INT_ARL_CFP_COD_CAB_FOR_PAG, rst.getInt("co_forPag"));
//                            arlRegCabForPag.add(INT_ARL_CFP_NOM_CAB_FOR_PAG, rst.getString("tx_nomForPag"));
//                            arlRegCabForPag.add(INT_ARL_CFP_NUM_PAG, rst.getInt("ne_numPag"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_SRI, rst.getString("tx_codSri"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_TIP_RET, rst.getInt("co_tipRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_DES_COR_TIP_RET, rst.getString("tx_desCorRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_DES_LAR_TIP_RET, rst.getString("tx_desLarRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_POR_RET, rst.getInt("nd_porRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_APL_RET, rst.getString("tx_aplRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_CTA, rst.getInt("co_cta"));
                            arlDatCabForPag.add(arlRegCabForPag);
                        }
                        else
                            return false;
                    }


                    //datos de forma de pago de detalle
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop";
                    strSQL+=" FROM tbm_detForPag AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_forPag=" + intCodForPag + "";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_forPag, a1.co_reg";
                    rst=stm.executeQuery(strSQL);
                    arlDatDetForPag.clear();
                    while(rst.next()){
                        arlRegDetForPag=new ArrayList();
                        arlRegDetForPag.add(INT_ARL_DFP_COD_EMP, rst.getString("co_emp"));
                        arlRegDetForPag.add(INT_ARL_DFP_COD_PAG, rst.getString("co_forPag"));
                        arlRegDetForPag.add(INT_ARL_DFP_COD_REG, rst.getString("co_reg"));
                        arlRegDetForPag.add(INT_ARL_DFP_DIA_CRE, rst.getString("ne_diaCre"));
                        arlRegDetForPag.add(INT_ARL_DFP_EST_SOP, rst.getString("st_sop"));
                        arlDatDetForPag.add(arlRegDetForPag);
                    }
                                        
                    if(generarSQLDocumentoEgreso(con)){
                        //INGRESO
                        arlDatItm=arlDatosItemIngreso;
                        if(arlDatItm.size()>0){
                            intCodEmp=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_EMP);
                            intCodLoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_LOC);
                            intCodTipDoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_TIP_DOC);
                            intCodCliPrv=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_CLI_PRV);
//                            intCodBodRegUno=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_BOD);
//                            System.out.println("ZafMovInv.getDatoEgresoIngreso BODEGA INGRESO " + intCodBodRegUno);
                            
                            //código
                            strSQL="";
                            strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            System.out.println("codigo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                            else
                                return false;

                            //número
                            strSQL="";
                            strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
                            else
                                return false;

                            //signo para generar el documento
                            strSQL="";
                            strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                            else
                                return false;

                            //para obtener secuencia de empresa
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                            System.out.println("para obtener secuencia de empresa " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecEmp=rst.getInt("ne_numSecEmp");

                            //para obtener secuencia de grupo
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            System.out.println("para obtener secuencia de grupo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecGrp=rst.getInt("ne_numSecGrp");
                            
                            //--Forma de pago
                            strSQL="";
                            strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag";
                            strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                            strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                            strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                            strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                            strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                            strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            System.out.println("Forma de pago: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intNumPag=rst.getInt("ne_numPag");
                                intCodForPag=rst.getInt("co_forPag");
                            }
                            else
                                return false;

                            //--Retenciones
                            if(chrGenIva=='S'){//no debe generar retenciones por iva, por tanto: solo aplica a SUBTOTAL
                                //strSQL+=" AND tx_aplRet='S'";
                                strSQL="";
                                strSQL+="SELECT a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                                strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                                strSQL+=" FROM tbm_motDoc AS a1 LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                                strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                                strSQL+=" LEFT OUTER JOIN tbm_emp AS a5";
                                strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                                strSQL+=" LEFT OUTER JOIN tbm_tipPer AS a3";
                                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                                strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                                strSQL+=" ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                                strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                                strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND CURRENT_DATE BETWEEN a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                                strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                                strSQL+=" AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                                strSQL+=" AND a1.co_mot=1";//quemado: para bienes
                                strSQL+=" AND a6.co_cli=" + intCodCliPrv + "";
                                strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet , a9.tx_desCor, a9.tx_desLar";
                                strSQL+=" , a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                                strSQL+=" ORDER BY a2.co_tipRet, a1.co_mot, a1.tx_desCor, a2.tx_codSri";
                                System.out.println("Retenciones-ingreso: " + strSQL);
                                rst=stm.executeQuery(strSQL);
                                arlDatCabForPag.clear();
                                if(rst.next()){
                                    arlRegCabForPag=new ArrayList();
//                                    arlRegCabForPag.add(INT_ARL_CFP_COD_CAB_FOR_PAG, rst.getInt("co_forPag"));
//                                    arlRegCabForPag.add(INT_ARL_CFP_NOM_CAB_FOR_PAG, rst.getString("tx_nomForPag"));
//                                    arlRegCabForPag.add(INT_ARL_CFP_NUM_PAG, rst.getInt("ne_numPag"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_SRI, rst.getString("tx_codSri"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_TIP_RET, rst.getInt("co_tipRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_DES_COR_TIP_RET, rst.getString("tx_desCorRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_DES_LAR_TIP_RET, rst.getString("tx_desLarRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_POR_RET, rst.getInt("nd_porRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_APL_RET, rst.getString("tx_aplRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_CTA, rst.getInt("co_cta"));
                                    arlDatCabForPag.add(arlRegCabForPag);
                                }
                                else
                                    return false;
                            }


                            //datos de forma de pago de detalle
                            strSQL="";
                            strSQL+="SELECT a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop";
                            strSQL+=" FROM tbm_detForPag AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_forPag=" + intCodForPag + "";
                            strSQL+=" ORDER BY a1.co_emp, a1.co_forPag, a1.co_reg";
                            System.out.println("datos de forma de pago de detalle " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            arlDatDetForPag.clear();
                            while(rst.next()){
                                arlRegDetForPag=new ArrayList();
                                arlRegDetForPag.add(INT_ARL_DFP_COD_EMP, rst.getString("co_emp"));
                                arlRegDetForPag.add(INT_ARL_DFP_COD_PAG, rst.getString("co_forPag"));
                                arlRegDetForPag.add(INT_ARL_DFP_COD_REG, rst.getString("co_reg"));
                                arlRegDetForPag.add(INT_ARL_DFP_DIA_CRE, rst.getString("ne_diaCre"));
                                arlRegDetForPag.add(INT_ARL_DFP_EST_SOP, rst.getString("st_sop"));
                                arlDatDetForPag.add(arlRegDetForPag);
                            }
     
                            //Limpiar objetos de conexion a db
                            stm.close();
                            stm=null;
                            rst.close();
                            rst=null;
                            
                            if(generarSQLDocumentoIngreso(con)){
                                blnRes=true;
                            }
                        }
                    }
                }

            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            System.err.println("ERROR SQL ingreso/egreso: " + e.toString());
        }
        catch(Exception e){
            blnRes=false;
            System.err.println("ERROR ingreso/egreso: " + e.toString());
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento 
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoEgreso(Connection con){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 0)){
                    if(insertar_tbmDetMovInv(con)){
                        if(insertar_tbmPagMovInv(con)){
                            //cambiar cuenta contable
                            if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFecDoc)){
                                System.out.println("generarSQLDocumentoEgreso");
                                blnRes=true;
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoEgreso: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoIngreso(Connection con){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 0)){
                    if(insertar_tbmDetMovInv(con)){
                        if(insertar_tbmPagMovInv(con)){
                            if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFecDoc)){
                                System.out.println("generarSQLDocumentoIngreso");
                                blnRes=true;
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoIngreso: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite realizar la inserción de la cabecera
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    private boolean insertar_tbmCabMovInv(Connection con, int tipoTransaccion){
        boolean blnRes=true;
        String strRucCliPrv="";
        String strNomCliPrv="";
        String strDirCliPrv="";
        String strTelCliPrv="";
        int intCodForPagCliPrv=-1;
        String strDesForPagCliPrv="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                if(tipoTransaccion==0){//egreso o ingreso
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a1.co_forPag, a2.tx_des";
                    strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_cabForPag AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_cli=" + intCodCliPrv + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        strRucCliPrv=rst.getString("tx_ide");
                        strNomCliPrv=rst.getString("tx_nom");
                        strDirCliPrv=rst.getString("tx_dir");
                        strTelCliPrv=rst.getString("tx_tel");
                        intCodForPagCliPrv=rst.getInt("co_forPag");
                        strDesForPagCliPrv=rst.getString("tx_des");
                    }
                }

                
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, ne_numcot, ne_numdoc, ne_numgui, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli, tx_telcli,";
                strSQL+="   co_forpag, tx_desforpag, nd_sub, nd_tot, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_numped,";
                strSQL+="   ne_secgrp, ne_secemp, nd_valiva, co_mnu, st_tipdev, st_imp, tx_coming, tx_commod)";
                strSQL+="   (";
                strSQL+="    SELECT ";
                strSQL+="" + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="0, ";//ne_numcot
                strSQL+="" + intNumDoc + ",";//ne_numdoc
                strSQL+="0, ";//ne_numgui
                //strSQL+=" '" + objUti.formatearFecha(dtpFecDoc.toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="'"+ objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) +"', "; //fe_doc
                if(tipoTransaccion==0){//egreso o ingreso
                    strSQL+="" + intCodCliPrv + ",";//co_cli
                    strSQL+="" + objUti.codificar(strRucCliPrv) + ",";//tx_ruc
                    strSQL+="" + objUti.codificar(strNomCliPrv) + ",";//tx_nomcli
                    strSQL+="" + objUti.codificar(strDirCliPrv) + ",";//tx_dircli
                    strSQL+="" + objUti.codificar(strTelCliPrv) + ",";//tx_telcli
                    strSQL+="" + objUti.codificar(intCodForPagCliPrv) + ",";//co_forpag
                    strSQL+="" + objUti.codificar(strDesForPagCliPrv) + ",";//tx_desforpag
                }
                else{
                    strSQL+="Null,";//co_cli
                    strSQL+="Null,";//tx_ruc
                    strSQL+="Null,";//tx_nomcli
                    strSQL+="Null,";//tx_dircli
                    strSQL+="Null,";//tx_telcli
                    strSQL+="Null,";//co_forpag
                    strSQL+="Null,";//tx_desforpag
                }

                strSQL+="0,";//nd_sub
                strSQL+="0,";//nd_tot
                strSQL+="'Generado por Zafiro: '||" + " CURRENT_TIMESTAMP" + ",";//tx_obs1
                strSQL+="'A'" + ",";//st_reg
                strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ing
                strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="Null,";//tx_numped   No almacena nada en este momento sino cuando se obtengan los dos tipos de documentos
                strSQL+="" + intNumSecEmp + ",";//ne_secgrp
                strSQL+="" + intNumSecGrp + ",";//ne_secemp
                strSQL+="0,";//nd_valiva
                strSQL+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                strSQL+="'C',";//st_tipdev
                strSQL+="'N',";//st_imp
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_coming
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
                stm.executeUpdate(strSQL);
             
                stm.close();
                stm=null;
//                rst.close();
//                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmCabMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmCabMovInv: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite realizar la inserción de la detalle
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean insertar_tbmDetMovInv(Connection con){
        boolean blnRes=true;
        int j=0;
        BigDecimal bgdCanItm;
        BigDecimal bgdCosItm;
        String strSQLIns="";
        String strEstIva="";
        BigDecimal bgdTotItm;
        BigDecimal bgdIntSig;
        String strNomBodDet="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLIns="";
                bgdValDocSub=new BigDecimal("0");
                bgdValDocIva=new BigDecimal("0");
                bgdValDocTot=new BigDecimal("0");

                System.out.println("arlDatItm: " + arlDatItm.toString());
                for(int i=0; i<arlDatItm.size(); i++){
                    //nombre de bodega origen
                    strSQL="";
                    strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_bod=" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES)) + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strNomBodDet=rst.getString("tx_nomBod");
                    else
                        return false;


                    //Egreso, Ingreso, Factura, Orden de Compra
                    //--aqui se inserta en positivo porque la cantidad se la recibe en postivo
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt,";
                    strSQL+="   tx_nomitm, tx_unimed, co_bod, nd_can, nd_canorg, nd_cosuni, nd_preuni,";
                    strSQL+="   nd_pordes, st_ivacom, nd_exi, nd_valexi, st_reg, nd_cancon, tx_obs1,";
                    strSQL+="   co_usrcon, ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro,";
                    strSQL+="   nd_cosunigrp, nd_costotgrp, nd_exigrp, nd_valexigrp, nd_cosprogrp,";
                    strSQL+="   nd_candev, co_itmact, co_locrelsoldevven, co_tipdocrelsoldevven,";
                    strSQL+="   co_docrelsoldevven, co_regrelsoldevven, st_meringegrfisbod, nd_cannunrec,";
                    strSQL+="   co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer,";
                    strSQL+="   co_regrelsolsaltemmer, nd_preunivenlis, nd_pordesvenmax, tx_nombodorgdes,";
                    strSQL+="   nd_cantotmalest, nd_cantotmalestpro, nd_cantotnunrecpro, st_cliretemprel,";
                    strSQL+="   fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel,";
                    strSQL+="   tx_obscliretemprel, nd_ara, nd_preuniimp, nd_valtotfobcfr, nd_cantonmet,";
                    strSQL+="   nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_canutiorddis";
                    strSQL+=")";
                    strSQL+="(";
                    strSQL+="   SELECT ";
                    strSQL+="" + intCodEmp + ",";//co_emp
                    strSQL+="" + intCodLoc + ",";//co_loc
                    strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                    strSQL+="" + intCodDoc + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM)) + ",";//tx_codalt
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_NOM_ITM)) + ",";//tx_nomitm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_UNI_MED_ITM)) + ",";//tx_unimed
                    //strSQL+="" + intCodBodRegUno + ",";//co_bod
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES)) + ",";//co_bod
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatItm, i, INT_ARL_CAN_ITM)==null?"0":(objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM).equals("")?"0":objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM)));
                    strSQL+="" + bgdCanItm + ",";//nd_can
                    strSQL+="Null,";//nd_canorg
                    bgdCosItm=objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI), intNumDec);
                    strSQL+="" + bgdCosItm + ",";//nd_cosuni
                    strSQL+="" + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_PRE_UNI), intNumDec) + ",";//nd_preuni
                    strSQL+="0,";//nd_pordes     por disposición de Eddye para FACVENE, FACCOM, INGBOPR, EGBOPR, TRANSFERENCIAS EN GENERAL, no se debe realizar descuento           
                    strEstIva=objUti.getStringValueAt(arlDatItm, i, INT_ARL_IVA_COM_ITM);
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdCosItm)), intNumDec);
                    if(chrGenIva=='S'){
                        if(strEstIva.equals("S")){                        
                            bgdValDocIva=bgdValDocIva.add(objUti.redondearBigDecimal((bgdTotItm.multiply(bgdValPorIva)), intNumDec));//al final tendrá el valor del iva del documento
                        }
                    }                
                    strSQL+="" + objUti.codificar(strEstIva) + ",";//st_ivacom                
                    strSQL+="Null, ";//nd_exi
                    strSQL+="Null,";//nd_valexi
                    strSQL+="Null,";//st_reg
                    strSQL+="0,";//nd_cancon
                    strSQL+="Null,";//tx_obs1
                    strSQL+="Null,";//co_usrcon
                    strSQL+="Null,";//ne_numfil
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdCosItm)), intNumDec) + ", ";//nd_tot
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_LET_ITM)) + ",";//tx_codalt2
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdCosItm)), intNumDec) + ",";//nd_costot
                    strSQL+="Null,";//nd_cospro
                    strSQL+="" + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_PRE_UNI), intNumDec) + ",";//nd_cosunigrp
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdCosItm)), intNumDec) + ",";//nd_costotgrp
                    strSQL+="Null,";//nd_exigrp
                    strSQL+="Null,";//nd_valexigrp
                    strSQL+="Null,";//nd_cosprogrp
                    strSQL+="0, ";//nd_candev
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itmact
                    strSQL+="Null,";//co_locrelsoldevven
                    strSQL+="Null,";//co_tipdocrelsoldevven
                    strSQL+="Null,";//co_docrelsoldevven
                    strSQL+="Null,";//co_regrelsoldevven
                    strSQL+="'N',";//st_meringegrfisbod
                    strSQL+="0,";//nd_cannunrec
                    strSQL+="Null,";//co_locrelsolsaltemmer
                    strSQL+="Null,";//co_tipdocrelsolsaltemmer
                    strSQL+="Null,";//co_docrelsolsaltemmer
                    strSQL+="Null,";//co_regrelsolsaltemmer
                    strSQL+="Null,";//nd_preunivenlis
                    strSQL+="Null,";//nd_pordesvenmax
    //                strSQL+="" + objUti.codificar(strNomBodRegUno) + ",";//tx_nombodorgdes
                    strSQL+="" + objUti.codificar(strNomBodDet) + ",";//tx_nombodorgdes
                    strSQL+="Null,";//nd_cantotmalest
                    strSQL+="Null,";//nd_cantotmalestpro
                    strSQL+="Null,";//nd_cantotnunrecpro
                    strSQL+="Null,";//st_cliretemprel
                    strSQL+="Null,";//fe_retemprel
                    strSQL+="Null,";//tx_perretemprel
                    strSQL+="Null,";//tx_vehretemprel
                    strSQL+="Null,";//tx_plavehretemprel
                    strSQL+="Null,";//tx_obscliretemprel
                    strSQL+="Null,";//nd_ara
                    strSQL+="Null,";//nd_preuniimp
                    strSQL+="Null,";//nd_valtotfobcfr
                    strSQL+="Null,";//nd_cantonmet
                    strSQL+="Null,";//nd_valfle
                    strSQL+="Null,";//nd_valcfr
                    strSQL+="Null,";//nd_valtotara
                    strSQL+="Null,";//nd_valtotgas
                    strSQL+="Null";//nd_canutiorddis
                    strSQL+=");";
                    System.out.println("insertar_tbmDetMovInv: " + strSQL);
                    strSQLIns+=strSQL;

                    //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                    bgdValDocSub=bgdValDocSub.add(bgdTotItm);//al final tendrá el valor del subtotal del documento
                    bgdValDocTot=bgdValDocTot.add((bgdValDocSub.add(bgdValDocIva)));//al final tendrá el valor del total del documento
                }
                
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmDetMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmDetMovInv: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite realizar la inserción en la tabla de pagos
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    private boolean insertar_tbmPagMovInv(Connection con){
        boolean blnRes=true;
        String strTipRetApl="";
        int j=0;
        BigDecimal bgdMonPag=new BigDecimal("0");
        BigDecimal bgdMonTotAplRet=new BigDecimal("0");
        String strSQLIns="";
        BigDecimal bgdValPenDocTot=new BigDecimal("0");
        BigDecimal bgdValAplDocTot=new BigDecimal("0");//Valor que se ha aplicado del total pendiente(Total - retenciones)
        BigDecimal bgdValCuoPag=new BigDecimal("0");//valor de la cuota que se debe cancelar
        BigDecimal bgdValCuoPagAcu=new BigDecimal("0");//valor de la cuota que ya se ha "pagado"
        try{
            stm=con.createStatement();
            //registros de retenciones en tbm_pagMovInv
            System.out.println("arlDatCabForPag:" + arlDatCabForPag.toString());
            System.out.println("arlDatDetForPag:" + arlDatDetForPag.toString());
            
            for(int i=0; i<arlDatCabForPag.size(); i++){
                j++;
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven,";
                strSQL+="   co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,";
                strSQL+="   st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                strSQL+="   fe_venchq, nd_monchq, co_prochq, st_reg, fe_ree, co_usrree,";
                strSQL+="   tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp,";
                strSQL+="   tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva,";
                strSQL+="   tx_tipreg";
                strSQL+=" ) (";
                strSQL+=" SELECT " + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="" + j + ",";//co_reg
                strSQL+="0,";//ne_diacre
                strSQL+="" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, "yyyy-MM-dd")) + ",";//fe_ven
                strSQL+="" + objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_COD_TIP_RET) + ",";//co_tipret
                strSQL+="" + objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET) + ",";//nd_porret
                strTipRetApl=objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_APL_RET);
                strSQL+="" + objUti.codificar(strTipRetApl) + ",";//tx_aplret
                if(strTipRetApl.equals("S"))
                    bgdMonPag=bgdValDocSub.multiply(objUti.getBigDecimalValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET));
                else if(strTipRetApl.equals("I"))
                    bgdMonPag=bgdValDocIva.multiply(objUti.getBigDecimalValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET));
                else
                    return false;
                
                bgdMonTotAplRet=bgdMonTotAplRet.add(bgdMonPag);                                                             //Valor aplicado por retenciones
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, intNumDec) + ",";//mo_pag
                strSQL+="0,";//ne_diagra
                strSQL+="0,";//nd_abo
                strSQL+="'N',";//st_sop
                strSQL+="'N',";//st_entsop
                strSQL+="'N',";//st_pos
                strSQL+="Null,";//co_banchq
                strSQL+="Null,";//tx_numctachq
                strSQL+="Null,";//tx_numchq
                strSQL+="Null,";//fe_recchq
                strSQL+="Null,";//fe_venchq
                strSQL+="Null,";//nd_monchq
                strSQL+="Null,";//co_prochq
                strSQL+="'A',";//st_reg
                strSQL+="Null,";//fe_ree
                strSQL+="Null,";//co_usrree
                strSQL+="Null,";//tx_comree
                strSQL+="'N',";//st_autpag
                strSQL+="Null,";//co_ctaautpag
                strSQL+="Null,";//tx_obs1
                strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_COD_SRI)) + ",";//tx_codsri
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, intNumDec) + ",";//nd_basimp
                strSQL+="Null,";//tx_numser
                strSQL+="Null,";//tx_numautsri
                strSQL+="Null,";//tx_feccad
                strSQL+="Null,";//fe_venchqautpag
                strSQL+="" + bgdValDocIva + ",";//nd_valiva
                strSQL+="Null";//tx_tipreg
                strSQL+=");";
                System.out.println("insertar_tbmPagMovInv: " + strSQL);
                strSQLIns+=strSQL;
            }
            
            bgdValPenDocTot=bgdValDocTot.subtract(bgdMonTotAplRet);//Valor del documento - Valor aplicado por retenciones
            
            for(int i=0; i<intNumPag; i++){
                bgdValCuoPag=bgdValPenDocTot.divide(new BigDecimal(""+intNumPag), intNumDec, BigDecimal.ROUND_UP);
                if(i==(intNumPag-1)){
                    bgdValCuoPag=bgdValPenDocTot.subtract(bgdValCuoPagAcu);
                }                        
                j++;
                bgdValCuoPagAcu=bgdValCuoPagAcu.add(bgdValCuoPag);
                
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven,";
                strSQL+="   co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,";
                strSQL+="   st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                strSQL+="   fe_venchq, nd_monchq, co_prochq, st_reg, fe_ree, co_usrree,";
                strSQL+="   tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp,";
                strSQL+="   tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva,";
                strSQL+="   tx_tipreg";
                strSQL+=" ) (";
                strSQL+=" SELECT " + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="" + j + ",";//co_reg
                strSQL+="" + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + ", ";//ne_diacre
//              strSQL+=" ( SELECT (" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, "yyyy-MM-dd")) + ") + " + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + "),";//fe_ven
                strSQL+=" ( SELECT CAST(" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos())) + " AS DATE) + " + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + "),";//fe_ven
                //strSQL+=" ( SELECT CAST('" + objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) + "' AS DATE) + CAST('" + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + " days' AS INTERVAL)),";//fe_ven
                
                strSQL+="0,";//co_tipret
                strSQL+="0,";//nd_porret
                strSQL+="Null,";//tx_aplret

                bgdMonTotAplRet=bgdMonTotAplRet.add(bgdMonPag);
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, intNumDec) + ",";//mo_pag
                strSQL+="0,";//ne_diagra
                strSQL+="0,";//nd_abo
                strSQL+="'N',";//st_sop
                strSQL+="'N',";//st_entsop
                strSQL+="'N',";//st_pos
                strSQL+="Null,";//co_banchq
                strSQL+="Null,";//tx_numctachq
                strSQL+="Null,";//tx_numchq
                strSQL+="Null,";//fe_recchq
                strSQL+="Null,";//fe_venchq
                strSQL+="Null,";//nd_monchq
                strSQL+="Null,";//co_prochq
                strSQL+="'A',";//st_reg
                strSQL+="Null,";//fe_ree
                strSQL+="Null,";//co_usrree
                strSQL+="Null,";//tx_comree
                strSQL+="'N',";//st_autpag
                strSQL+="Null,";//co_ctaautpag
                strSQL+="Null,";//tx_obs1
                strSQL+="Null,";//tx_codsri
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, intNumDec) + ",";//nd_basimp
                strSQL+="Null,";//tx_numser
                strSQL+="Null,";//tx_numautsri
                strSQL+="Null,";//tx_feccad
                strSQL+="Null,";//fe_venchqautpag
                strSQL+="" + bgdValDocIva + ",";//nd_valiva
                strSQL+="Null";//tx_tipreg
                strSQL+=");";
                System.out.println("insertar_tbmPagMovInv: " + strSQL);
                strSQLIns+=strSQL;
            }
            stm.executeUpdate(strSQLIns);
            
            System.out.println("---------------------------");
            System.out.println("bgdValDocTot: " + bgdValDocTot);
            System.out.println("bgdValCuoPagAcu: " + bgdValCuoPagAcu);
            System.out.println("bgdMonTotAplRet: " + bgdMonTotAplRet);
            
            

            if(bgdValDocTot.compareTo(bgdValCuoPagAcu.add(bgdMonTotAplRet))!=0){
                System.out.println("Se generaron mal los cálculos de los pagos");
                return false;
            }            
        }
        catch(java.sql.SQLException e){
            System.out.println("Error SQL - insertar_tbmPagMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmPagMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite obtener datos necesarios para generar el documento
     * @param con                       Conexión enviada
     * @param intCodigoEmpresa          Código de empresa donde se guardará el documento
     * @param intCodigoLocal            Código de local donde se guardará el documento
     * @param intCodigoTipoDocumento    Código de tipo de documento donde se guardará el documento
     * @param dtpFechaDocumento         <HTML>Fecha de documento con la que se guardará el documento. <BR>Formato: dd/MM/yyyy</HTML>
     * @param intCodigoBodegaEgreso     Código de bodega donde se egresará la mercadería
     * @param intCodigoBodegaIngreso    Código de bodega donde se ingresará la mercadería
     * @param chrGenerarIva             Estado que permitirá saber si hay que calcular valor de subtotal. Según tipo de documento. Por Préstamos ='N', por Facturación/Compra ='S'
     * @param arlDatosItem              <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item.     La cantidad se envía en negativo. 
     *                                                             <BR>La clase se encarga de generar registro para cantidad positiva y registro para cantidad negativa.
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
     *                                  </HTML>
     * @return 
     */
    public boolean getDatoTransferencia(Connection con, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento
        , Date dtpFechaDocumento, int intCodigoBodegaEgreso, int intCodigoBodegaIngreso, char chrGenerarIva, ArrayList arlDatosItem){
        boolean blnRes=false;
        try{
            if(con!=null){
                intTipMov=1;
                intCodEmp=intCodigoEmpresa;
                intCodLoc=intCodigoLocal;
                intCodTipDoc=intCodigoTipoDocumento;
                dtpFecDoc=dtpFechaDocumento;
//                intCodBodRegUno=intCodigoBodegaEgreso;
//                intCodBodRegDos=intCodigoBodegaIngreso;
                arlDatItm=arlDatosItem;
                chrGenIva=chrGenerarIva;
                
                stm=con.createStatement();
                //código
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())//JOTA
                    intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"0":(rst.getString("co_doc").equals("")?"0":rst.getString("co_doc")))) + 1;
                else
                    return false;
                
                //número
                strSQL="";
                strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())//JOTA
                    intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"0":(rst.getString("ne_ultDoc").equals("")?"0":rst.getString("ne_ultDoc")))) + 1;
                else
                    return false;
                
                //signo para generar el documento
                strSQL="";
                strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                else
                    return false;
                
                //para obtener secuencia de empresa
                strSQL="";
                strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumSecEmp=rst.getInt("ne_numSecEmp");
                    
                //para obtener secuencia de grupo
                strSQL="";
                strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumSecGrp=rst.getInt("ne_numSecGrp");
                               
//                //nombre de bodega de egreso
//                strSQL="";
//                strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
//                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
//                strSQL+=" AND a1.co_bod=" + intCodBodRegUno + "";
//                rst=stm.executeQuery(strSQL);
//                if(rst.next())
//                    strNomBodRegUno=rst.getString("tx_nomBod");
//                else
//                    return false;
                
//                //nombre de bodega de ingreso
//                strSQL="";
//                strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
//                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
//                strSQL+=" AND a1.co_bod=" + intCodBodRegDos + "";
//                rst=stm.executeQuery(strSQL);
//                if(rst.next())
//                    strNomBodRegDos=rst.getString("tx_nomBod");
//                else
//                    return false;
                
                if(generarSQLDocumentoTransferencia(con)){
                    blnRes=true;
                }
                else
                    blnRes=false;
                if(stm!=null){
                    stm.close();
                    stm=null;
                }
                if(rst!=null){
                    rst.close();
                    rst=null;
                }
                
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR ZafMovInv " + e.toString());
            blnRes=false;
        }
        catch(Exception e){
            System.err.println("ERROR ZafMovInv " + e.toString());
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento 
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoTransferencia(java.sql.Connection con){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 1)){
                    if(insertar_tbmDetMovInv(con)){
                        if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, ""+intCodDoc, ""+intNumDoc, dtpFecDoc)){
                            blnRes=true;
                        }
                        else
                            blnRes=false;
                    }
                    else
                        blnRes=false;
                }
                else 
                    blnRes=false;
            }
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoTransferencia: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }               


}
