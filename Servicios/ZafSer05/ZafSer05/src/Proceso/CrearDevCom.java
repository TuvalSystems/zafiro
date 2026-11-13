/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;

/**
 *
 * @author jayapata
 */
public class CrearDevCom {

 Librerias.ZafParSis.ZafParSis objZafParSis;
 Librerias.ZafUtil.ZafUtil objUti;
 procesoClass objClass;
 IniciarProceso pantallaPrincipal;
 Librerias.ZafUtil.ZafCtaCtb  objCtaCtb;
String strTipPer_empCos = "";
 double dblivaEmp   = 0;
 int intCodBodPreCos = 0;
 int intCodTipPerEmpCos =0;
 String strNomBodPtaParCos = "";
 String strNomBodPrvCos = "";
 private java.util.Date datFecAux;

 java.util.Vector vecDetDiario;

  public CrearDevCom(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.pantallaPrincipal = pantallaPrin;
        objClass = new procesoClass(objZafParSis, pantallaPrincipal,  "" );

      }catch (CloneNotSupportedException e){  System.out.println("Error 2 "+e.toString() );  }
  }


  
     
  
public boolean _generaDevCom(java.sql.Connection connEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpOc, int intCodLocOc, int intCodTipDocOc, int intCodDocOc, int intCodTipDocDev, boolean blnEstSiReaCruAut, double dblValFacCom  ){
  boolean blnRes=false;
  String strFecSistema="";
  int intCodDocEmp=0;
  int intNumDocEmp=0;
  java.util.Date DatFecDoc= new java.util.Date();
  double dlbIva=0;
  int intCodTipdocCru=91; // tipo de documento de cruce automatico
  try{
    if(connEmp != null ){

        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis, connEmp, intCodEmp, intCodLoc, intCodTipDocDev, 1 );

        CruceDocCxCCxP objCruc = new CruceDocCxCCxP(objZafParSis);


     cargarTipEmp(connEmp, intCodEmp, intCodLoc );

     strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

      DatFecDoc= objUti.parseDate(strFecSistema, "yyyy-MM-dd");
     intCodDocEmp = objClass._getCodigoDoc(connEmp, intCodEmp, intCodLoc, intCodTipDocDev );
     intNumDocEmp = objClass._getUltNumDoc(connEmp, intCodEmp, intCodLoc, intCodTipDocDev );
  
    objClass.iniciarObj();

       if(objClass._ingDetDevCom(connEmp, intCodEmp,  intCodLoc,  intCodTipDoc, intCodDoc, intCodTipDocDev, intCodDocEmp  )){

         dlbIva = objUti.redondear(  (objClass.dblSubTotDevCom * (dblivaEmp / 100 ) ), 2);

       if(objClass._ingCabDevCom(connEmp, intCodEmp,  intCodLoc,  intCodTipDocOc, intCodDocOc, strFecSistema, 0, 0 , intCodTipDocDev, intCodDocEmp, intNumDocEmp, objClass.dblSubTotFacCom, dlbIva, dblivaEmp  )){

           if(objClass._ingresarPagDevVen(connEmp, intCodEmp,  intCodLoc, intCodTipDocDev,  intCodDocEmp, objUti.redondear((objUti.redondear(objClass.dblSubTotDevCom,2)+objUti.redondear(dlbIva,2)),2), DatFecDoc, -1 )){


             if( generaAsientoDevCom( objUti.redondear((objClass.dblSubTotDevCom+dlbIva),2), objUti.redondear(objClass.dblSubTotDevCom,2), objUti.redondear(dlbIva,2) , intCodBodPreCos ) ){

                 
              if(pantallaPrincipal.objAsiDia.insertarDiario(connEmp, intCodEmp,  intCodLoc, intCodTipDocDev,  String.valueOf(intCodDocEmp), String.valueOf(intNumDocEmp), datFecAux )) {

                  if(objCruc._cruceDocs(connEmp, blnEstSiReaCruAut, dblValFacCom, objUti.redondear((objClass.dblSubTotDevCom+dlbIva),2), intCodEmp, intCodLoc, intCodTipDocDev, intCodDocEmp ,intCodTipdocCru )){

                      blnRes=true;

                  }
              }
             }

           }
        }
      }

   objClass.cerrarObj();

   objCruc=null;

  }}catch(Exception e)   { blnRes=false;  System.out.println(""+e.toString() );   objClass.mensaje("_generaFacVen "+ e.toString());     }
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
            dblivaEmp   =  rstEmp.getDouble("porIva");
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



private boolean generaAsientoDevCom(double dblTotal, double dblSubtotal, double dblIva,  int intCodBod ){
 boolean blnRes=false;
    try{
    pantallaPrincipal.objAsiDia.inicializar();
    int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
    int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
    int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
    int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
    int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
    int INT_VEC_DEBE   = 5; //5) Debe.
    int INT_VEC_HABER  = 6; //6) Haber.
    int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
    int INT_VEC_NUEVO = 8;
    String stIvaCom="S";

    double dblTotSer=dblSubtotal;

    dblSubtotal=0;

     if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
     else vecDetDiario.removeAllElements();

     java.util.Vector vecReg;

     if(dblSubtotal > 0 ){
       vecReg = new java.util.Vector();
       vecReg.add(INT_LINEA, null);
       vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
       vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
       vecReg.add(INT_VEC_BOTON, null);
       vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
       vecReg.add(INT_VEC_DEBE, new Double(0));
       vecReg.add(INT_VEC_HABER, new Double(objUti.redondear( dblSubtotal, objZafParSis.getDecimalesMostrar())));
       vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
       vecDetDiario.add(vecReg);
     }


      if(dblIva > 0 ){
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
            vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());
            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, new Double(objUti.redondear( dblIva, objZafParSis.getDecimalesMostrar())));
            vecReg.add(INT_VEC_REF, null);
             vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);
        }

        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
        vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
        vecReg.add(INT_VEC_DEBE, new Double( dblTotal ) );
        vecReg.add(INT_VEC_HABER, new Double(0));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);

        

        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaSerCompras()));
        vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaSerCom());
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaSerCom());
        vecReg.add(INT_VEC_DEBE, new Double(0));
        vecReg.add(INT_VEC_HABER, new Double(dblTotSer));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);


    pantallaPrincipal.objAsiDia.setDetalleDiario(vecDetDiario);
    blnRes=true;

  } catch(Exception e) {  blnRes=false; System.out.println(""+e.toString() ); objClass.mensaje("IniPro "+ e.toString()); }
 return blnRes;
}






///**
// * Funcion que cambia los estado que ya fue procesado.
// * @param conn
// * @param intCodEmp
// * @param intCodLoc
// * @param intCodTipDoc
// * @param intCodDoc
// * @return true = todo bien  <br> false =  error.
// */
//public boolean _cambiarEstProCos(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
//boolean blnRes=false;
//java.sql.Statement stmLoc, stmLoc01;
//java.sql.ResultSet rstLoc;
//String strSql="";
//try{
//    if(conn!=null){
//        stmLoc=conn.createStatement();
//        stmLoc01=conn.createStatement();
//
//        strSql="select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  "
//        + " ,a2.co_emp as coempdev, a2.co_loc as colocdev, a2.co_tipdoc as cotipdocdev, a2.co_doc as codocdev  "
//        + " from ( "
//        + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_can as canven "
//        + " from tbm_cabmovinv as a  "
//        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
//        + " where  a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  "
//        + " ) as a1  "
//        + " left join (  "
//        + " select a1.nd_can as candev, a.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel  "
//        + " ,a.co_loc, a.co_tipdoc, a.co_doc  "
//        + "  From tbm_cabmovinv as a  "
//        + "  inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
//        + "  inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
//        + "  where a.co_tipdoc=3 and a.st_itmserpro='S'  "
//        + " ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg ) "
//        + " ";
//        rstLoc = stmLoc.executeQuery(strSql);
//        while(rstLoc.next()){
//
//            strSql="UPDATE tbm_cabmovinv set st_itmSerPro ='P' where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" ";
//            strSql+=" ; UPDATE tbm_cabmovinv set st_itmSerPro ='P' where co_emp="+rstLoc.getInt("coempdev")+" and co_loc="+rstLoc.getInt("colocdev")+" and co_tipdoc="+rstLoc.getInt("cotipdocdev")+" and co_doc="+rstLoc.getInt("codocdev")+" ";
//            stmLoc01.executeUpdate(strSql);
//            blnRes=true;
//
//        }
//        rstLoc.close();
//        rstLoc=null;
//
// }}catch(java.sql.SQLException e){ blnRes=false; }
//   catch(Exception e){  blnRes=false; }
//return blnRes;
//}
//



    
}
