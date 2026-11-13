/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafCnfDoc;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JInternalFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author sistemas3
 */
public class ZafImpDoc {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Statement stm;
    private ResultSet rst;
    //private Connection con;
    private String strSQL="";
    private java.util.Date datFecAux;
    
    private int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
    private String strSQLRep;
    private ZafRptSis objRptSis;
    private String strRutRpt, strNomRpt;
    private JInternalFrame jifCnt;
    
    public ZafImpDoc(ZafParSis obj, javax.swing.JInternalFrame jfrthis){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            jifCnt=jfrthis;
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(jifCnt), true, objParSis);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(null, e);
        }
    }
    
    
    
    
    /**
     * Función que permite imprimir la Orden de Almacenamiento
     * return true: si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    public boolean imprimirOrdAlm(Connection con, int codigoBodega
                                                , int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes = true;
        intCodEmp=codigoEmpresa;
        intCodLoc=codigoLocal;
        intCodTipDoc=codigoTipoDocumento;
        intCodDoc=codigoDocumento;
        String strNomPrnSer="";//Nombre del print server
        int intNumTotRpt;
        String strUsu = "", strFecHorSer;
        
        try{
            if(con!=null){
                switch(codigoBodega){
                    case 1://California
                        strNomPrnSer = "od_califormia";
                        break;
                    case 2://Vía Daule
                        strNomPrnSer = "od_dimulti";
                        break;
                    case 3://Quito Norte
                        strNomPrnSer = "od_quito";
                        break;
                    case 5://Manta
                        strNomPrnSer = "od_manta";
                        break;
                    case 11://Santo Domingo
                        strNomPrnSer = "od_stodgo";
                        break;
                    case 15://Inmaconsa
                        strNomPrnSer = "od_inmaconsa";
                        break;
                    case 28://Cuenca
                        strNomPrnSer = "od_cuenca";
                        break;
                    default:
                        break;
                }
                
                
                objRptSis.cargarListadoReportes();
                objRptSis.show();
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    stm=con.createStatement();
                    for(int i=0; i<intNumTotRpt; i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 37://"Confirmación de ingresos a Bodega..."
                                case 320://"Confirmación de salida temporal de mercadería..."
                                case 321://"Confirmación de ingreso de mercadería que salió temporalmente..."
                                case 325://"Ingresos provisionales de mercadería a Bodega..."
                                case 460://"Confirmación de ingresos a Bodega (Relacionadas)..."
                                case 476://"Orden de Almacenamiento"
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);                                    
                                    
                                    strSQL ="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, ";
                                    strSQL+="        CASE WHEN a5.st_impord='S' THEN a4.tx_codAlt2 ELSE '' END AS tx_codAlt, ";
                                    strSQL+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, ";
                                    strSQL+="        a2.nd_can,a5.tx_ubi,a6.tx_desLar as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, ";
                                    strSQL+="        a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc, b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng";
                                    strSQL+=" FROM (tbm_cabIngEgrMerBod  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr)";
                                    strSQL+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc)";
                                    strSQL+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc)";
                                    strSQL+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                                    strSQL+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)";
                                    strSQL+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg)";
                                    strSQL+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)";
                                    strSQL+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)";
                                    strSQL+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc)";
                                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                                    strSQL+=" AND a1.co_doc=" + intCodDoc + "";
                                    strSQL+=" AND a2.nd_can NOT IN(0)";
                                    strSQLRep = strSQL;
                                    //System.out.println("imprimirOrdenAlmacenamiento: " + strSQLRep);
                                    rst=stm.executeQuery(strSQLRep);
                                    if(rst.next())
                                        strUsu=rst.getString("tx_usr");

                                    java.util.Map mapPar = new java.util.HashMap();
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strCamAudRpt", "" + strUsu + "    " + strFecHorSer + "");

                                    javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                                    objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

                                    JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutRpt, mapPar, con);
                                    javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                                    javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                                    printServiceAttributeSet.add(printerName);
                                    net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                                    objJRPSerExp.exportReport();
                                    objPriReqAttSet=null;
                                    //System.out.println("Se imprimio Orden de Almacenamiento");                                    
                            }
                        }
                    }
                    stm.close();
                    stm=null;
                    rst.close();
                    rst=null;
                }
            }
        } 
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e); 
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    

    
    
    
    
    
    
}
