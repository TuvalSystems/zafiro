/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.   ne_numguides
 */

package Librerias.ZafClassImpGuia;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author jayapata
 */
public class ZafClassEmvMesImp {

 private String strStrConnDb="";
 private String strUsrConnDb="";
 private String strClaConnDb="";
 private String strRutaRpt="";
 private String strNomImpBod="";
   
 private int intCodBodGrp=0;


  public ZafClassEmvMesImp(String strStrConnDbRec, String strUsrConnDbRec, String strClaConnDbRec, int intCodBodGrpRec, String strRutaRptRec, String strNomImpBodRec ) {
   try{
        strStrConnDb = strStrConnDbRec;
        strUsrConnDb = strUsrConnDbRec;
        strClaConnDb = strClaConnDbRec;

        strRutaRpt = strRutaRptRec;
        strNomImpBod = strNomImpBodRec;

        intCodBodGrp = intCodBodGrpRec;

      }catch(Exception e){ System.out.println("Erro al cargar clase ZafClassImpGuia "); }
   }



  


public boolean verificaGuiaOrdenAnulado(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=true;
  java.sql.Connection connLoc;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    connLoc =  java.sql.DriverManager.getConnection(strStrConnDb, strUsrConnDb, strClaConnDb );
   if(connLoc!=null){
     stmLoc=connLoc.createStatement();

     strSql="select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.ne_numdoc, a2.ne_numorddes from tbm_cabmovinv as a  "
     + " inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc )  "
     + " inner join tbm_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
     + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a2.co_emp AND a6.co_bod=a2.co_ptopar) "
     + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and  a2.st_reg='I' "
     + " and ( a6.co_empGrp=0 AND a6.co_bodGrp  = "+intCodBodGrp+"   ) "
     + " GROUP BY  a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.ne_numdoc, a2.ne_numorddes ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         System.out.println(" Hay Algo.");

         impresionGuiaRemAutBod(connLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc")  );

     }else System.out.println(" no Hay nada.");
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
     connLoc.close();
     connLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false;  System.out.println(""+Evt );   }
    catch(Exception Evt){ blnRes=false;  System.out.println(""+Evt );    }
  return  blnRes;
}




private boolean impresionGuiaRemAutBod(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
  String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  try{
    if(conn!=null){

        strDirSis= getDirectorioSistema();
        
        DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt;
        
        System.out.println("impresionGuiaRemAutBod: "+DIRECCION_REPORTE_GUIA);
        System.out.println("co_emp: "+intCodEmp + " co_loc: "+intCodLoc + " co_tipdoc: "+intCodTipDoc + " co_doc: "+intCodDoc );
        
        Map parameters = new HashMap();
        parameters.put("co_emp", new Integer(intCodEmp) );
        parameters.put("co_loc", new Integer(intCodLoc) );
        parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
        parameters.put("co_doc",  new Integer(intCodDoc) );

        javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
        objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
        javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod , null);
        javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);
        net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        objJRPSerExp.exportReport();
        objPriReqAttSet=null;

 }}catch (JRException e) {   
    }
 return true;
}


private String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            java.net.URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=java.net.URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
      
        }
        return strRes;
    }



  


}
