/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;

/**
 *
 * @author Javier
 */
public class CruceDocCxCCxP {

 Librerias.ZafParSis.ZafParSis objZafParSis;
 Librerias.ZafUtil.ZafUtil objUti;

     
     public CruceDocCxCCxP(Librerias.ZafParSis.ZafParSis obj ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();

      }catch (CloneNotSupportedException e){  System.out.println("Error 2 "+e.toString() );  }
  }


public boolean _cruceDocs(java.sql.Connection conn, boolean blnEstCru, double dblValDoccru, double dblValDev, int intCodEmp, int intCodLoc, int intCodTipDocDev, int intCodDocEmp, int intCodTipdocCru  ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql = "";
  
   try{
     if(conn!=null){
      stmLoc=conn.createStatement();

       if(blnEstCru){

          // System.out.println(".."+dblValDoccru+"   =  "+dblValDev );

           if(dblValDoccru==dblValDev){

              blnRes=false;

              System.out.println("Cruca por todo el valor...");

              strSql="select co_emp, co_loc, co_tipdoc, co_doc from tbr_detmovinv "
              + " where co_emprel="+intCodEmp+" and co_locrel="+intCodLoc+" and co_tipdocrel="+intCodTipDocDev+" and co_docrel="+intCodDocEmp+" "
              + "  group by  co_emp, co_loc, co_tipdoc, co_doc ";
              rstLoc=stmLoc.executeQuery(strSql);
              if(rstLoc.next()){

//                  System.out.println("Co_emp = "+rstLoc.getInt("co_emp") );
//                  System.out.println("co_loc = "+rstLoc.getInt("co_loc") );
//                  System.out.println("co_tipdoc = "+rstLoc.getInt("co_tipdoc") );
//                  System.out.println("co_doc = "+rstLoc.getInt("co_doc") );

                  if(crucarDosCxCCxPIguales(conn, intCodEmp, intCodLoc, intCodTipDocDev, intCodDocEmp, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), dblValDoccru,  intCodTipdocCru  )){
                      blnRes=true;
                  }
                  
              }
              rstLoc.close();
              rstLoc=null;


           }else{
               blnRes=true;
           }

           
       }

     stmLoc.close();
     stmLoc=null;
  }}catch (java.sql.SQLException e) {  blnRes=false;  System.out.println(""+e.toString()); }
    catch (Exception e) {    blnRes=false;   System.out.println(""+e.toString());   }
  return blnRes;
}


public boolean crucarDosCxCCxPIguales(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDocDev, int intCodDocDev, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac,  double dblValDocCru , int intCodTipdocCru ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql = "", strFecSistema="";
   StringBuffer strSqlInsDet;
   int intCodDocCru=0;
   int intCodCliPrv=0;
   int intCodRegCru=0;
   double dblValCru=0;
   int intNumDocCru=0;
   String strNomCli="";
   try{
    if(conn!=null){
     stmLoc=conn.createStatement();

     strSqlInsDet=new StringBuffer();

     strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());


     strSql="select co_cli,tx_nomCli,tx_dirCli,tx_ruc,tx_telCli,ne_numdoc   from tbm_cabmovinv where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDocDev+" and co_doc="+intCodDocDev+" ";
     //strSql=" select tx_nom  from tbm_cli where co_emp="+intCodEmp+" and co_cli="+intCodCliPrv+" ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         intCodCliPrv=rstLoc.getInt("co_cli");
         strNomCli=rstLoc.getString("tx_nomCli");
     }
     rstLoc.close();
     rstLoc=null;


     strSql=" select case when max(co_doc)+1 is null then 1 else  max(co_doc)+1 end as codoc FROM  tbm_cabpag " +
     " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipdocCru+"  ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         intCodDocCru=rstLoc.getInt("codoc");
     }
     rstLoc.close();
     rstLoc=null;


     strSql=" select case when max(ne_ultdoc)+1 is null then 1 else  max(ne_ultdoc)+1 end as numdoc FROM  tbm_cabtipdoc " +
     " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipdocCru+"  ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         intNumDocCru=rstLoc.getInt("numdoc");
     }
     rstLoc.close();
     rstLoc=null;



    strSql="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 " +
    " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_regrep, co_cli, tx_nomcli ) "+
    " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipdocCru+", "+intCodDocCru+" " +
    " ,'"+strFecSistema+"', "+intNumDocCru+", "+intNumDocCru+", 'CRUCE AUTOMATICO', '' " +
    " ,"+dblValDocCru+", 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", 1 " +
    " , null, 799, 'I', "+intCodCliPrv+", '"+strNomCli+"' ) ; ";
    strSqlInsDet.append( strSql );

     strSql=" UPDATE tbm_cabtipdoc SET ne_ultdoc ="+intNumDocCru+" " +
     " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipdocCru+" ; ";
     strSqlInsDet.append( strSql );


     strSql=" select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg,  a1.ne_numdoc, a3.tx_descor, a2.mo_pag, a2.nd_abo , a1.fe_doc  " +
     "  , case when  a2.mo_pag < 0 then 1 else -1 end as tipMov,  "
     + " abs(a2.mo_pag) as valdoccru FROM tbm_cabmovinv as a1 "
     + " INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  "
     + " INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) "
     + " where a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+"  and a1.co_tipdoc="+intCodTipDocDev+" and a1.co_doc="+intCodDocDev+" "
     + " and a1.st_reg not in ('I','E') and a2.st_reg in ('A','C')  "
     + " order by abs(a2.mo_pag)";
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){
     
          dblValCru=rstLoc.getDouble("valdoccru")*rstLoc.getInt("tipMov");
            
          intCodRegCru++;
          strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, "
          +" co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon,  tx_codsri, tx_numchq, fe_recchq, fe_venchq  )" +
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipdocCru+", "+intCodDocCru+" " +
          " ,"+intCodRegCru+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+", "+rstLoc.getInt("co_doc")+", " +
          " "+rstLoc.getInt("co_reg")+", "+dblValCru+", 'A','I', null, '', '"+intNumDocCru+"',  '"+strFecSistema+"',  '"+strFecSistema+"'  ) ; ";
          strSqlInsDet.append( strSql );

          strSql = " UPDATE tbm_pagmovinv set nd_abo= "+dblValCru+", " +
          " st_regrep='M' WHERE " +
          " co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" " +
          " and co_reg="+rstLoc.getInt("co_reg")+" ; ";
          strSqlInsDet.append( strSql );

     }
     rstLoc.close();
     rstLoc=null;


    
     strSql=" select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg,  a1.ne_numdoc, a3.tx_descor, a2.mo_pag, a2.nd_abo , a1.fe_doc " +
     "   , case when  a2.mo_pag < 0 then 1 else -1 end as tipMov,"
     + " abs(a2.mo_pag) as valdoccru FROM tbm_cabmovinv as a1 "
     + " INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  "
     + " INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) "
     + " where a1.co_emp="+intCodEmpFac+" and a1.co_loc="+intCodLocFac+" and a1.co_tipdoc="+intCodTipDocFac+" and a1.co_doc="+intCodDocFac+"  "
     + " and a1.st_reg not in ('I','E') and a2.st_reg in ('A','C')  "
     + " order by abs(a2.mo_pag) ";
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

     
          dblValCru=rstLoc.getDouble("valdoccru")*rstLoc.getInt("tipMov");
           

          intCodRegCru++;
          strSql =" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_codsri, tx_numchq, fe_recchq, fe_venchq )" +
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipdocCru+", "+intCodDocCru+" " +
          " ,"+intCodRegCru+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+", "+rstLoc.getInt("co_doc")+", " +
          " "+rstLoc.getInt("co_reg")+", "+dblValCru+", 'A','I', null, '',  '"+intNumDocCru+"', '"+strFecSistema+"',  '"+strFecSistema+"'  ) ; ";
          strSqlInsDet.append( strSql );

          strSql =" UPDATE tbm_pagmovinv set nd_abo= "+dblValCru+", " +
          " st_regrep='M' WHERE " +
          " co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" " +
          " and co_reg="+rstLoc.getInt("co_reg")+" ; ";
          strSqlInsDet.append( strSql );

     }
     rstLoc.close();
     rstLoc=null;


   //  System.out.println("----> "+ strSqlInsDet.toString() );

     stmLoc.executeUpdate( strSqlInsDet.toString() );

     strSqlInsDet=null;

     stmLoc.close();
     stmLoc=null;

  }}catch (java.sql.SQLException e) {  blnRes=false;  System.out.println(""+e.toString()); }
    catch (Exception e) {    blnRes=false;   System.out.println(""+e.toString());   }
  return blnRes;
}



     

}
