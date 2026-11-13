/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;

import Librerias.ZafUtil.UltDocPrint;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author jayapata
 */
public class CrearFacVen {

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
 UltDocPrint objUltDocPrint;
 
  public CrearFacVen(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.pantallaPrincipal = pantallaPrin;
        objClass = new procesoClass(objZafParSis, pantallaPrincipal,  "" );
        objUltDocPrint = new UltDocPrint(objZafParSis);        
      }catch (CloneNotSupportedException e){  
          System.out.println("Error 2 "+e.toString() );  
      }
  }




public boolean _generaFacVen(Connection connEmp, Connection connCos, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCos, int intCodLocCos, int intCodTipDocCos, int intCodCliPrvCos, String strSqlCliCos  ){
  boolean blnRes=false;
  Statement stmLoc;
  String strFecSistema="";
  java.util.Date DatFecDoc= new java.util.Date();
  int intSecEmp=0, intSecGrp=0;
  double dlbIva=0;
  
  int intNumDocCos=0;
  try{
      
//      System.out.println("_generaFacVen ");
   if(connCos != null ){
        if(connEmp != null ){
            connCos.setAutoCommit(false);

            intSecEmp=objUltDocPrint.getNumSecDoc(connCos, intCodEmpCos );
            intSecGrp=objUltDocPrint.getNumSecDoc(connCos, objZafParSis.getCodigoEmpresaGrupoCosenco() );

            objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, connCos, intCodEmpCos, intCodLocCos,  intCodTipDocCos );

            cargarTipEmp(connCos, intCodEmpCos, intCodLocCos );

          stmLoc=connCos.createStatement();

          strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

           datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

          DatFecDoc= objUti.parseDate(strFecSistema, "yyyy-MM-dd");

          intCodDocCosGlo = objClass._getCodigoDoc(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos );
          intNumDocCos = objClass._getUltNumDoc(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos );

          objClass.iniciarObj();
//            System.out.println("INICIA - _generaFacVen ");
            if(objClass._generaVenDet(connEmp, connCos,  intCodEmp,  intCodLoc,  intCodTipDoc, intCodDoc, intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, intCodBodPreCos  )){
                   dlbIva = objUti.redondear(  (objClass.dblSubTotFacVen * (dblivaEmpCos / 100 ) ), 2);
                    //if(objClass._generaVenCab(connCos, strFecSistema, 0, 0 , intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, intNumDocCos, intCodCliPrvCos, objClass.dblSubTotFacVen, dlbIva , dblivaEmpCos, strSqlCliCos  )){
                    if(objClass._generaVenCab(connCos, strFecSistema, intSecEmp, intSecGrp, intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, intNumDocCos, intCodCliPrvCos, objClass.dblSubTotFacVen, dlbIva , dblivaEmpCos, strSqlCliCos  )){      
                        if(objClass._CalculoPagoVen( connCos, intCodEmpCos )){
                            if(objClass._calculaPagosVen(connCos, objClass.dblSubTotFacVen, dlbIva, objUti.redondear((objClass.dblSubTotFacVen+dlbIva),2), intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodDocCosGlo, objClass.strTipPerCli , DatFecDoc )){
                                if( generaAsientoVen( objUti.redondear((objClass.dblSubTotFacVen+dlbIva),2), objUti.redondear(objClass.dblSubTotFacVen,2), objUti.redondear(dlbIva, 2) , objUti.redondear(objClass.dblValTotDesFacVen, 2)   ) ){
                                    if(pantallaPrincipal.objAsiDia.insertarDiario(connCos, intCodEmpCos, intCodLocCos, intCodTipDocCos,  String.valueOf(intCodDocCosGlo), String.valueOf(intNumDocCos), datFecAux )) {
                                        System.out.println("OK...............");         
                                        blnRes=true;          
                                    }
                                }
                            }  
                        }   
                    } 
            }
          objClass.cerrarObj();

           stmLoc.close();
           stmLoc=null;


  }
}
}
catch(java.sql.SQLException e){ 
    blnRes=false;     
    objClass.mensaje("_generaFacVen "+ e.toString());    
}
catch(Exception e){ 
    blnRes=false;     
    objClass.mensaje("_generaFacVen "+ e.toString());     
}
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

private boolean generaAsientoVen(double dblTotalCot, double dblSubtotalCot, double dblIvaCot, double dblValTotDes ){
 boolean blnRes=false;
 try{
 pantallaPrincipal.objAsiDia.inicializar();
 java.util.Vector vecAsiento = new java.util.Vector();
 
 if(dblTotalCot>0){
    java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb());
    vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblTotalCot,6)), 5);
    vecAsiento.add(vecRowAsientoDeb);
 }


/*
 * Agregando la cuenta de descuento
 */

//double dblValTotDes = 0 ; // getTotalDescuento();


if( dblValTotDes > 0){

    java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes());
    vecRowAsientoDeb.setElementAt(new Double(dblValTotDes), 5);
    vecAsiento.add(vecRowAsientoDeb);
}

/*
 * Agregando la cuenta de Ventas
 */
if( (dblValTotDes+dblSubtotalCot) > 0){
    java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab());
    vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblValTotDes+objUti.redondeo(dblSubtotalCot,6),6)), 6);
    vecAsiento.add(vecRowAsientoHab);
}
/*
 * Agregando la cuenta Iva en Ventas
 */
if( dblIvaCot > 0){
    java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen());
    vecRowAsientoHab.setElementAt(new Double(objUti.redondeo(dblIvaCot,6)), 6);
    vecAsiento.add(vecRowAsientoHab);
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
