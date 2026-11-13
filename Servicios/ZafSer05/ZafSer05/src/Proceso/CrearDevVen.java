/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;

/**
 *
 * @author jayapata
 */
public class CrearDevVen {

 Librerias.ZafParSis.ZafParSis objZafParSis;
 Librerias.ZafUtil.ZafUtil objUti;
 procesoClass objClass;
 IniciarProceso pantallaPrincipal;
 private Librerias.ZafUtil.ZafCtaCtb_dat objCtaCta_Dat;
 String strTipPer_empCos = "";
 double dblivaEmpCos   = 0;
 int intCodBodPreCos = 0;
 int intCodTipPerEmpCos =0;
 String strNomBodPtaParCos = "";
 String strNomBodPrvCos = "";
 private java.util.Date datFecAux;
 int intCodDocCosGlo=0;
 StringBuffer stbDatFacVen;

  public CrearDevVen(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.pantallaPrincipal = pantallaPrin;
        objClass = new procesoClass(objZafParSis, pantallaPrincipal,  "" );
        stbDatFacVen=new StringBuffer();

      }catch (CloneNotSupportedException e){  System.out.println("Error 2 "+e.toString() );  }
  }







private boolean _obtenerDatosDetVen(java.sql.Connection connLoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intEstIns=0;
  try{
    if(connLoc!=null){
      stmLoc=connLoc.createStatement();
       
     strSql="select a1.nd_can as candev, a6.co_emprel, a6.co_locrel, a6.co_tipdocrel, a6.co_docrel, a6.co_regrel  "
     + " from tbm_cabmovinv as a "
     + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
     + " inner join tbm_inv as ser on (ser.co_emp=a1.co_emp and ser.co_itm=a1.co_itm ) "
     + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
     + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel ) "
     + " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc ) "
     + " inner join tbr_detmovinv as a5 on (a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=a3.co_reg "
     + " and  a5.co_tipdocrel=2 ) "
     + " inner join tbr_detmovinvotrbasdat as a6 on (a6.co_emp=a5.co_emprel and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel and a6.co_reg=a5.co_regrel ) "
     + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  and  a4.st_itmserpro = 'P' and ser.st_ser='S' "
     + " ";
    /// System.out.println(" --> "+ strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

         if (intEstIns==1) stbDatFacVen.append(" UNION ALL ");

         stbDatFacVen.append("SELECT "+rstLoc.getInt("co_emprel")+" as co_emp ,"+rstLoc.getInt("co_locrel")+" as co_loc "
         + " ,"+rstLoc.getInt("co_tipdocrel")+" as co_tipdoc, "+rstLoc.getInt("co_docrel")+" as co_doc,  "+rstLoc.getInt("co_regrel")+" as co_reg "
         + " ,"+rstLoc.getString("candev")+" as cantidad ");

         intEstIns=1;
         blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    
  }}catch(java.sql.SQLException e){ blnRes=false;    objClass.mensaje("_obtenerDatosDetVen "+ e.toString());   }
    catch(Exception e)   { blnRes=false;     objClass.mensaje("_obtenerDatosDetVen "+ e.toString());  System.out.println(""+e.toString());    }
 return blnRes;
}






public boolean _generaDevVen(java.sql.Connection connEmp, java.sql.Connection connCos, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCos, int intCodLocCos, int intCodTipDocCos,  boolean blnEstSiReaCruAut, double dblValFacVen  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strFecSistema="";
  java.util.Date DatFecDoc= new java.util.Date();
  
  double dlbIva=0;
  
  int intNumDocCos=0;
  int intCodTipdocCru=90; // tipo de documento de cruce automatico
  try{
   if(connCos != null ){
    if(connEmp != null ){
       connCos.setAutoCommit(false);

    
       objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, connCos, intCodEmpCos, intCodLocCos,  intCodTipDocCos );

       CruceDocCxCCxP objCruc = new CruceDocCxCCxP(objZafParSis);


       cargarTipEmp(connCos, intCodEmpCos, intCodLocCos );

     stmLoc=connCos.createStatement();

     strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

     DatFecDoc= objUti.parseDate(strFecSistema, "yyyy-MM-dd");

     intCodDocCosGlo = objClass._getCodigoDoc(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos );
     intNumDocCos = objClass._getUltNumDoc(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos );
   
     objClass.iniciarObj();


     if(_obtenerDatosDetVen(connEmp,  intCodEmp,  intCodLoc,  intCodTipDoc, intCodDoc )){


       if(objClass._ingDetDevVen(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, stbDatFacVen  )){

         dlbIva = objUti.redondear(  (objClass.dblSubTotDevVen * (dblivaEmpCos / 100 ) ), 2);

         if(objClass._ingCabDevVen(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, intNumDocCos, strFecSistema, 0, 0, objClass.dblSubTotDevVen, dlbIva , stbDatFacVen  )){

            if(objClass._ingresarPagDevVen(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, objUti.redondear((objUti.redondear(objClass.dblSubTotDevVen,2)+objUti.redondear(dlbIva,2)),2), DatFecDoc, 1 )){

             if( generaAsientoDevVen( objUti.redondear((objClass.dblSubTotDevVen+dlbIva),2), objUti.redondear(objClass.dblSubTotDevVen,2), objUti.redondear(dlbIva, 2) , objUti.redondear(objClass.dblValTotDesDevVen, 2)   ) ){

               if(pantallaPrincipal.objAsiDia.insertarDiario(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos,  String.valueOf(intCodDocCosGlo), String.valueOf(intNumDocCos), datFecAux )) {

                 if(objCruc._cruceDocs(connCos, blnEstSiReaCruAut, dblValFacVen, objUti.redondear((objClass.dblSubTotDevVen+dlbIva),2), intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo ,intCodTipdocCru )){

                     System.out.println("OK...............");

                     blnRes=true;
                   }
                 }
                }
             }
           }
          }
        }

     objClass.cerrarObj();

     objCruc=null;

      stmLoc.close();
      stmLoc=null;
     

  }}}catch(java.sql.SQLException e){ blnRes=false;     objClass.mensaje("_generaFacVen "+ e.toString());    }
    catch(Exception e)   { blnRes=false;     objClass.mensaje("_generaFacVen "+ e.toString());     }
 return blnRes;
}






/**
* Permite cargar datos de la empresa en variables
*/
public void cargarTipEmp(java.sql.Connection connCos, int intCodEmpCos, int intCodLocCos){
java.sql.Statement stmTipEmp;
java.sql.ResultSet rstEmp;
String sSql;
try{
    if (connCos!=null){
        stmTipEmp=connCos.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+intCodLocCos+" and bod.st_reg='P')  " +
        " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) " +
        " where a.co_emp="+intCodEmpCos;
        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            strTipPer_empCos = rstEmp.getString("tx_descor");
            dblivaEmpCos   =  rstEmp.getDouble("porIva");
            intCodBodPreCos = rstEmp.getInt("co_bod");
            intCodTipPerEmpCos = rstEmp.getInt("co_tipper");
            strNomBodPtaParCos = rstEmp.getString("tx_dir");
            strNomBodPrvCos =  rstEmp.getString("nombod");
        }

        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
    }
}catch(java.sql.SQLException Evt){

}catch(Exception Evt){

}
}





private boolean generaAsientoDevVen(double dblTotalCot, double dblSubtotalCot, double dblIvaCot, double dblValTotDes ){
 boolean blnRes=false;
 try{
 pantallaPrincipal.objAsiDia.inicializar();
 java.util.Vector vecAsiento = new java.util.Vector();
 
   if( dblTotalCot > 0){ //if( (dblValTotDes+dblSubtotalCot) > 0){
        java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab());
        vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblTotalCot,2)), 6);
        vecAsiento.add(vecRowAsientoHab);
    }

/*
 * Agregando la cuenta de descuento
 */


 if( dblValTotDes > 0){ //if( dblIvaCot > 0){
    java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes());
    vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblValTotDes,2)), 6);
    vecAsiento.add(vecRowAsientoHab);
 }

/*
 * Agregando la cuenta de Ventas
 */

  if((dblValTotDes+dblSubtotalCot)>0){ // if(dblTotalCot>0){
     java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb());
     vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblValTotDes+dblSubtotalCot,2)), 5);
     vecAsiento.add(vecRowAsientoDeb);
  }

/*
 * Agregando la cuenta Iva en Ventas
 */

 if( dblIvaCot > 0){ // if( dblValTotDes > 0){
    java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen());
    vecRowAsientoDeb.setElementAt(new Double(dblIvaCot), 5);
    vecAsiento.add(vecRowAsientoDeb);
 }

pantallaPrincipal.objAsiDia.setDetalleDiario(vecAsiento);

blnRes=true;

}catch(Exception Evt){ blnRes=false; }
 return blnRes;
}





private java.util.Vector vecFila2(int intCodCtaCtb,String codcta,String Nomcta){
    java.util.Vector vecRowAsiento = new java.util.Vector();
    /**Linea*/
    vecRowAsiento.add(null);

    /** Codigo de cuenta */
    vecRowAsiento.add(intCodCtaCtb+"");

    /** Codigo de ALterno de cuenta */
    vecRowAsiento.add(codcta);  //objCtaCta_Dat.getCodAltCta(intCodCtaCtb));

    /** Boton buscar cuenta */
    vecRowAsiento.add(null);

    /** NOmbre de cuenta */
    vecRowAsiento.add(Nomcta);  //objCtaCta_Dat.getNomCta(intCodCtaCtb));

    /** Valor que va en el debe */
    vecRowAsiento.add("0");

    /** Valor que va en el Haber  */
    vecRowAsiento.add("0");

    /** Referencia */
    vecRowAsiento.add(null);


    vecRowAsiento.add(null);

    return vecRowAsiento;

}






    
}
