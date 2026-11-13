/*
 * To change this template, choose Tools | Templates   st_bie
 * and open the template in the editor.   tbm_detmovinv
 */

package proceso;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.*;
import java.util.Vector;

/**
 *
 * @author jayapata
 */
public class arrancarFacVenFacCom  {

    ZafParSis objZafParSis;
    ZafUtil objUti;
    ZafCtaCtb_dat objCtaCta_Dat;
    ZafTipDoc  objTipDoc;
    ZafCtaCtb  objCtaCtb;
    procesoClass objClass;
    int intNumLimImp=0;
    String strFecCorte="";
    String strTipoBus="";
    String strNumFac="";
    String strNomArchHis="";
    IniciarProceso pantallaPrincipal;
    java.util.Date datFecAux;

    String strNumCom="";
    Vector vecDetDiario;
    String strJOTA;
    private UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
    
    public arrancarFacVenFacCom(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin, String strNomArchHis ) {
     try{
        this.objZafParSis = obj;
        objUti = new ZafUtil();
        this.pantallaPrincipal = pantallaPrin;
        objClass = new procesoClass(objZafParSis, pantallaPrincipal, strNomArchHis );
        objTipDoc = new ZafTipDoc(objZafParSis);
        objUltDocPrint = new UltDocPrint(objZafParSis);
        this.strNomArchHis = strNomArchHis;

       }catch (Exception e){  objUti.mostrarMsgErr_F1(null, e);  }
    }


    public boolean realizarProcesoFacVen01(java.sql.Connection conn, String strFecCort, int intLimImp, String strTipBus ){
       boolean blnRes=false;

       strFecCorte=strFecCort;
       intNumLimImp=intLimImp;
       strTipoBus= strTipBus;

       if(generaFacVenFacCom(conn)){
              blnRes=true;
       }

       return blnRes;
    }









public boolean generaFacVenFacCom(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strSqlCtaCon="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();

       /* QUERY QUE PERMITE ACTUALIZAR LA FECHA DEL DIARIO CON LA FECHA DE TBM_CABMOVINV*/
        strSql="update tbm_cabdia set fe_dia=c.fedoc  " +
        " from( " +
        " select distinct(a.co_emp) as coemp,a.co_loc as coloc,a.fe_doc as fedoc,a.co_doc as codoc,a.co_tipdoc as tipdoc "+
        " from tbm_cabmovinv as a,tbm_cabdia as b  "+
        " where a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_doc=b.co_dia and  b.co_tipdoc=a.co_tipdoc and a.fe_doc<>b.fe_dia "+
        " ORDER BY a.co_doc "+
        " ) as c   "+
        " where co_dia=c.codoc and co_emp=c.coemp and co_loc = c.coloc and co_tipdoc=c.tipdoc ";


       /* QUERY QUE PERMITE ACTUALIZAR LAS CUENTAS CONTABLE A LAS CUENTAS QUE SON POR EJEMPLO EN OC CUENTA CASTE ->  PROVEEDORES VARIOS **/
        strSqlCtaCon=" " +
        " update tbm_detdia set co_cta=29 where co_emp=1 and co_loc=5 and co_tipdoc in (1,228,3,229 )  and co_cta=2636 ;" +
        " update tbm_detdia set co_cta=29 where co_emp=1 and co_loc=5 and co_tipdoc in (1,228,3,229 )  and co_cta=2637 ;  " +
        " update tbm_detdia set co_cta=733 where co_emp=1 and co_loc=5 and co_tipdoc in (2,4 )  and co_cta=2636 ; " +
        " update tbm_detdia set co_cta=733 where co_emp=1 and co_loc=5 and co_tipdoc in (2,4 )  and co_cta=2637 ; " +

        " update tbm_detdia set co_cta=24  where co_emp=2 and co_loc=5 and co_tipdoc in (1,228,3,229 )  and co_cta in ( 1156, 1159 ) ; " +
        " update tbm_detdia set co_cta=154 where co_emp=2 and co_loc=5  and co_tipdoc in (2,4 ) and co_cta in ( 1156, 1159 ) ;  " +

        " update tbm_detdia set co_cta=28  where co_emp=4 and co_loc=2 and co_tipdoc in (1,228,3,229 )  and co_cta in ( 2098, 2099 ) ; " +
        " update tbm_detdia set co_cta=688 where co_emp=4 and co_loc=2  and co_tipdoc in (2,4 ) and co_cta in ( 2098, 2099  )  " ;


    if(generaFacVem(conn)){
     if(generaFacCom(conn)){
        blnRes=true;
        stmLoc.executeUpdate(strSql);
        stmLoc.executeUpdate(strSqlCtaCon);
    }}

    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false; mensaje(" generaFacVenFacCom "+ e.toString()); }
   catch(Exception e) { blnRes=false; mensaje(" generaFacVenFacCom "+ e.toString());  }
 return blnRes;
}







private boolean generaFacVem(java.sql.Connection conIns){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03;
 java.sql.ResultSet rstLoc, rstLoc01, rstLoc03, rstLoc04;
 int intNumLin=0;
 int intCodCliPrv=0;
 int intCodTipDocFac=0;
 int intCodTipDocFacVenEle=228;
 
 int intCodDoc=0;
 int intConTad=0;
 int intNumDoc=0;
 int intCodTipDoc=0;
 String strSql="";
 String strCodDoc="";
 int intControl=0;
 StringBuffer stbins;
 double dlbSub=0;
 double dlbIva=0;
 double dblValTotDes=0;
 double dlbPorIva=12;
 int intEstEje=0;
 int intSecEmp=0;
 int intSecGrp=0;
 int intCodLocFin=0;
 int intCodLocOri=0;
 String strFec="";
 String strFecDoc="";
 String strSecEmpGrp="";
 String strSqlVerfRel="";
 java.util.Date DatFecDoc= new java.util.Date();
 int intCodEmp=0;
 
 int intCodEmpOrg=0;
 int intCodLocOrg=0;        
         
 try{
    if(conIns!=null){
     stmLoc=conIns.createStatement();
     stmLoc01=conIns.createStatement();
     stmLoc03=conIns.createStatement();
     stmLoc02=conIns.createStatement();
     
     intCodEmpOrg=pantallaPrincipal.objZafParSis.getCodigoEmpresa();
     intCodLocOrg=pantallaPrincipal.objZafParSis.getCodigoLocal();
     
     
     intCodLocOri=objZafParSis.getCodigoLocal();

     intCodTipDoc=1;

     objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  1 );
     
     
     intNumLin=intNumLimImp;


 strSql="SELECT co_emp, co_loc, co_empper, co_locven, co_tipdocven, co_locvenfin, co_tipdocvenfin  FROM " +
 " tbr_bodemp WHERE co_empper="+objZafParSis.getCodigoEmpresa()+" AND co_locven="+intCodLocOri+" AND co_tipdocvenfin="+intCodTipDoc+"  AND st_reg IN ('A','C','U')"+
 " GROUP BY co_emp, co_loc, co_empper, co_locven, co_tipdocven, co_locvenfin, co_tipdocvenfin  ";
 
 rstLoc01=stmLoc01.executeQuery(strSql);
 while(rstLoc01.next()){

   intCodTipDocFac=rstLoc01.getInt("co_tipdocven");
   intCodLocFin=rstLoc01.getInt("co_locvenfin");

   objZafParSis.setCodigoLocal(intCodLocFin);

   strSql="SELECT distinct(a.fe_doc) as fe_doc, a.co_cli FROM tbm_cabmovinv AS a " +
   " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  AND a.co_loc="+intCodLocOri+"  AND a.co_tipdoc="+intCodTipDocFac+"  "+strTipoBus+" "+
   "  AND a.fe_doc <= '"+strFecCorte+"'  ORDER BY a.fe_Doc ";
   //System.out.println("-> "+ strSql );
   rstLoc03=stmLoc03.executeQuery(strSql);
   while(rstLoc03.next()){

     intCodCliPrv=rstLoc03.getInt("co_cli");
     strFecDoc=rstLoc03.getString("fe_doc");
     stbins=new StringBuffer(); //VARIABLE TIPO BUFFER

     intConTad=0; // CONTADOR DE LINEA
     strCodDoc="";
     intControl=0;
     strSecEmpGrp="";
     intEstEje=0;
     dlbSub=0;
     dlbIva=0;
     dblValTotDes=0;
     intSecEmp=0;
     intSecGrp=0;

      /*strSql="SELECT a1.tx_obs1,  a.co_emp, a.co_loc, a.co_tipdoc, a.fe_doc,  a.co_Doc, a1.co_reg as coregdet, a.co_cli, a.ne_secgrp, a.ne_secemp, a.tx_nomcli, a1.tx_codalt, a.st_reg " +
      " ,a1.tx_codAlt, a1.tx_codAlt2, a1.co_itm, a1.co_itmact, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosuni " +
      " ,a1.co_bod, abs(a1.nd_can) as nd_can, abs(a1.nd_tot) as nd_tot, a1.nd_cosUnigrp, (abs(a1.nd_costot)*-1) as nd_costot, a1.nd_preUni, a1.nd_porDes, a1.st_ivaCom "+
      " ,a1.nd_costotgrp, a1.st_regrep, a1.st_meringegrfisbod, a1.nd_cancon " +
      " ,a2.co_emp AS coempguia, a2.co_loc as colocguia, a2.co_tipdoc as cotipdocguia,  a2.co_doc as codocguia , a2.co_reg as coregguia "+
      " ,a2.co_emprel AS coemprelguia, a2.co_locrel as colocreldoc, a2.co_tipdocrel as cotipdocreldoc,  a2.co_docrel as codocreldoc , a2.co_regrel as coregreldoc "+
      " , a1.tx_nombodorgdes "
    + " ,a1.st_cliretemprel, a1.fe_retemprel, a1.tx_perretemprel, a1.tx_vehretemprel, a1.tx_plavehretemprel, a1.tx_obscliretemprel " +
      " FROM tbm_cabmovinv AS a " +
      " INNER JOIN tbm_detmovinv AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
      "   LEFT JOIN  tbm_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "+
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLocOri+" " +
      " AND a.co_tipdoc="+intCodTipDocFac+" AND a.co_cli="+intCodCliPrv+"  and a.fe_Doc='"+strFecDoc+"'  "+strTipoBus+" "+
      " ORDER BY a1.nd_tot, a.ne_secemp ";  // a.ne_secemp ,a1.tx_codalt */

      strSql=" SELECT a1.tx_obs1, a.co_emp, a.co_loc, a.co_tipdoc, a.fe_doc, a.co_Doc, a1.co_reg as coregdet, a.co_cli, "
            + " case when a.ne_secgrp is null then 0 else a.ne_secgrp end as ne_secgrp, "
            + " case when a.ne_secemp is null then 0 else a.ne_secemp end as ne_secemp, "
            + " a.tx_nomcli, a1.tx_codalt, a.st_reg, "
            + " a1.tx_codAlt, a1.tx_codAlt2, a1.co_itm, a1.co_itmact, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosuni, "
            + " a1.co_bod, abs(a1.nd_can) as nd_can, abs(a1.nd_tot) as nd_tot, a1.nd_cosUnigrp, (abs(a1.nd_costot)*-1) as nd_costot, a1.nd_preUni, a1.nd_porDes, a1.st_ivaCom, "
            + " a1.nd_costotgrp, a1.st_regrep, a1.st_meringegrfisbod, a1.nd_cancon, "
            + " a2.co_emp AS coempguia, a2.co_loc as colocguia, a2.co_tipdoc as cotipdocguia, a2.co_doc as codocguia, a2.co_reg as coregguia, "
            + " a2.co_emprel AS coemprelguia, a2.co_locrel as colocreldoc, a2.co_tipdocrel as cotipdocreldoc, a2.co_docrel as codocreldoc, a2.co_regrel as coregreldoc, "
            + " a1.tx_nombodorgdes, a1.st_cliretemprel, a1.fe_retemprel, a1.tx_perretemprel, a1.tx_vehretemprel, a1.tx_plavehretemprel, a1.tx_obscliretemprel "
            + " FROM tbm_cabmovinv AS a "
            + " INNER JOIN tbm_detmovinv AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + " LEFT JOIN tbm_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
            + " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLocOri+" "
            + " AND a.co_tipdoc="+intCodTipDocFac+" AND a.co_cli="+intCodCliPrv+"  and a.fe_Doc='"+strFecDoc+"'  "+strTipoBus+" "
            + " ORDER BY a1.nd_tot, a.ne_secemp ";  // a.ne_secemp ,a1.tx_codalt
//      System.out.println("-->" + strSql);
      rstLoc=stmLoc.executeQuery(strSql);

        while(rstLoc.next()){

          intEstEje=1;
          if(intControl==1) strCodDoc +=","+rstLoc.getString("co_Doc");
          else strCodDoc += rstLoc.getString("co_Doc");

          if(intConTad>=1) strSecEmpGrp+=" UNION ALL ";
          strSecEmpGrp+=" SELECT  "+rstLoc.getInt("ne_secemp")+" AS secemp,  "+rstLoc.getInt("ne_secgrp")+" AS secgrp ";

          intConTad++;

          intControl=1;
          
          intCodEmp=objZafParSis.getCodigoEmpresa();

         if(intConTad==1){
             //intSecEmp=rstLoc.getInt("ne_secemp");
             //intSecGrp=rstLoc.getInt("ne_secgrp");
             strFec=rstLoc.getString("fe_doc");
             DatFecDoc = rstLoc.getDate("fe_doc");
            // intCodBod=rstLoc.getInt("co_bod");
             intCodDoc = objClass._getCodigoDoc(conIns, intCodTipDocFacVenEle );
             intNumDoc = objClass._getUltNumDoc(conIns, intCodTipDocFacVenEle );

             strNumFac +="\n"+intNumDoc;
          }

          /*strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
          " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
          " co_bod, nd_can ,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
          ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosuni, tx_nombodorgdes, tx_obs1 "
        + " ,st_cliretemprel, fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel, tx_obscliretemprel ) " +
          " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", "+intConTad+", " +
          " '"+rstLoc.getString("tx_codAlt")+"', '"+rstLoc.getString("tx_codAlt2")+"' "+
          " ,"+rstLoc.getString("co_itm")+", "+rstLoc.getString("co_itmact")+"  "+
          " ,'"+rstLoc.getString("tx_nomItm")+"', '"+rstLoc.getString("tx_unimed")+"' "+
          " ,"+rstLoc.getString("co_bod")+", "+(rstLoc.getDouble("nd_can")*-1)+"  "+
          " ,"+(rstLoc.getDouble("nd_tot")*-1)+", "+rstLoc.getString("nd_cosUnigrp")+"  "+
          " ,"+rstLoc.getString("nd_costot")+", "+rstLoc.getString("nd_preUni")+"  "+
          " ,"+rstLoc.getString("nd_porDes")+", '"+rstLoc.getString("st_ivaCom")+"'  "+
          " , "+rstLoc.getString("nd_costotgrp")+"  "+
          " ,'I', '"+rstLoc.getString("st_meringegrfisbod")+"', "+rstLoc.getString("nd_cancon")+", "+rstLoc.getString("nd_cosuni")+" " +
          "  , '"+rstLoc.getString("tx_nombodorgdes")+"' ,'"+rstLoc.getString("tx_obs1")+"' "

          + ", "+(rstLoc.getString("st_cliretemprel")==null?null:"'"+rstLoc.getString("st_cliretemprel")+"'")+" "
          + ", "+(rstLoc.getString("fe_retemprel")==null?null:"'"+rstLoc.getString("fe_retemprel")+"'")+" "


          + ", "+(rstLoc.getString("tx_perretemprel")==null?null:"'"+rstLoc.getString("tx_perretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_vehretemprel")==null?null:"'"+rstLoc.getString("tx_vehretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_plavehretemprel")==null?null:"'"+rstLoc.getString("tx_plavehretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_obscliretemprel")==null?null:"'"+rstLoc.getString("tx_obscliretemprel")+"'")+" "

            
          + " ) ; ";*/
         
          strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
          " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
          " co_bod, nd_can ,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
          ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosuni, tx_nombodorgdes, tx_obs1 "+
          " ,st_cliretemprel, fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel, tx_obscliretemprel ) " +
          " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDocFacVenEle+", "+intCodDoc+", "+intConTad+", " +
          " '"+rstLoc.getString("tx_codAlt")+"', '"+rstLoc.getString("tx_codAlt2")+"' "+
          " ,"+rstLoc.getString("co_itm")+", "+rstLoc.getString("co_itmact")+"  "+
          " ,"+objUti.codificar(rstLoc.getString("tx_nomItm"))+", '"+rstLoc.getString("tx_unimed")+"' "+
          " ,"+rstLoc.getString("co_bod")+", "+(rstLoc.getDouble("nd_can")*-1)+"  "+
          " ,"+(rstLoc.getDouble("nd_tot")*-1)+", "+rstLoc.getString("nd_cosUnigrp")+"  "+
          " ,"+rstLoc.getString("nd_costot")+", "+rstLoc.getString("nd_preUni")+"  "+
          " ,"+rstLoc.getString("nd_porDes")+", '"+rstLoc.getString("st_ivaCom")+"'  "+
          " , "+rstLoc.getString("nd_costotgrp")+"  "+
          " ,'I', '"+rstLoc.getString("st_meringegrfisbod")+"', "+rstLoc.getString("nd_cancon")+", "+rstLoc.getString("nd_cosuni")+" " +
          "  , '"+rstLoc.getString("tx_nombodorgdes")+"' ,'"+rstLoc.getString("tx_obs1")+"' " +
          ", "+(rstLoc.getString("st_cliretemprel")==null?null:"'"+rstLoc.getString("st_cliretemprel")+"'")+" " +
          ", "+(rstLoc.getString("fe_retemprel")==null?null:"'"+rstLoc.getString("fe_retemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_perretemprel")==null?null:"'"+rstLoc.getString("tx_perretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_vehretemprel")==null?null:"'"+rstLoc.getString("tx_vehretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_plavehretemprel")==null?null:"'"+rstLoc.getString("tx_plavehretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_obscliretemprel")==null?null:"'"+rstLoc.getString("tx_obscliretemprel")+"'")+" " +           
          " ) ; ";
//          System.out.println("INSERT >>  \n"+strSql);
          stbins.append( strSql );


         if(!(rstLoc.getString("coempguia")==null)){
          strSql="UPDATE tbm_detguirem SET co_emprel="+objZafParSis.getCodigoEmpresa()+" , co_locrel="+objZafParSis.getCodigoLocal()+"" +
          ", co_tipdocrel="+intCodTipDocFacVenEle+" ,co_docrel="+intCodDoc+", co_regrel="+intConTad+" " +
          " WHERE co_emp="+rstLoc.getInt("coempguia")+" AND co_loc="+rstLoc.getInt("colocguia")+" AND " +
          " co_tipdoc="+rstLoc.getInt("cotipdocguia")+" and co_doc="+rstLoc.getInt("codocguia")+" and co_reg="+rstLoc.getInt("coregguia")+" ; ";
          pantallaPrincipal.txt.append( strSql+" \n" );
         }



            strSqlVerfRel=" select co_doc  from tbr_detmovinv where co_emprel="+rstLoc.getInt("co_emp")+" and co_locrel="+rstLoc.getInt("co_loc")+" " +
            " and co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" and co_docrel="+rstLoc.getInt("co_doc")+" and co_regrel="+rstLoc.getInt("coregdet")+" ";
            rstLoc04=stmLoc02.executeQuery(strSqlVerfRel);
            if(rstLoc04.next()){

              strSql="UPDATE tbr_detmovinv SET co_emprel="+objZafParSis.getCodigoEmpresa()+", co_locrel="+objZafParSis.getCodigoLocal()+" ,co_tipdocrel="+intCodTipDocFacVenEle+" " +
              " ,co_docrel="+intCodDoc+" ,co_regrel="+intConTad+"  " +
              " WHERE " +
              " co_emprel="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" " +
              " AND co_docrel="+rstLoc.getInt("co_doc")+"  AND co_regrel="+rstLoc.getInt("coregdet")+" ; ";
              pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;

 

            strSqlVerfRel=" select co_doc  from tbr_detmovinv where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" " +
            " and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" and co_reg="+rstLoc.getInt("coregdet")+" ";
            rstLoc04=stmLoc02.executeQuery(strSqlVerfRel);
            if(rstLoc04.next()){

              strSql="UPDATE tbr_detmovinv SET co_emp="+objZafParSis.getCodigoEmpresa()+", co_loc="+objZafParSis.getCodigoLocal()+" ,co_tipdoc="+intCodTipDocFacVenEle+" " +
              " ,co_doc="+intCodDoc+" ,co_reg="+intConTad+"  " +
              " WHERE " +
              " co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" " +
              " AND co_doc="+rstLoc.getInt("co_doc")+"  AND co_reg="+rstLoc.getInt("coregdet")+" ; ";
              pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;




          dlbSub+=rstLoc.getDouble("nd_tot");

          if(rstLoc.getString("st_ivaCom").equals("S"))
             dlbIva += ((( rstLoc.getDouble("nd_tot") * dlbPorIva)==0)?0:( rstLoc.getDouble("nd_tot") * dlbPorIva)/100) ;

          dblValTotDes += objUti.redondear( ((rstLoc.getDouble("nd_can") * rstLoc.getDouble("nd_preUni"))==0)?0:(objUti.redondear((rstLoc.getDouble("nd_can") * rstLoc.getDouble("nd_preUni"))* rstLoc.getDouble("nd_porDes"), 6)  / 100),  6);

         if(intNumLin==intConTad){

            strSecEmpGrp=" select max(secemp) as secemp,  max(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x where secgrp > 0  ";
            rstLoc04=stmLoc02.executeQuery(strSecEmpGrp);
            if(rstLoc04.next()){
                intSecEmp=rstLoc04.getInt("secemp");
                intSecGrp=rstLoc04.getInt("secgrp");
            }
            rstLoc04.close();
            rstLoc04=null;
             
            //intSecEmp=objUltDocPrint.getNumSecDoc(conIns, intCodEmp );
            //intSecGrp=objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() );
            
             intSecEmp=(intSecEmp==0)?objUltDocPrint.getNumSecDoc(conIns, intCodEmp ):intSecEmp;
             intSecGrp=(intSecGrp==0)?objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() ):intSecGrp;
            
            generaAsientoVen( objUti.redondear((dlbSub+dlbIva),2), objUti.redondear(dlbSub,2), objUti.redondear(dlbIva, 2) , objUti.redondear(dblValTotDes, 2) , intCodCliPrv  );
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

            if(objClass._generaVenCab(conIns, strFec, intSecEmp, intSecGrp, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDocFacVenEle , intCodDoc, intNumDoc, intCodCliPrv, (dlbSub*-1),  (dlbIva*-1), intCodTipDocFac  )){
             if(objClass._CalculoPagoVen(conIns)){
              if(objClass._calculaPagosVen(conIns, dlbSub, dlbIva, objUti.redondear((dlbSub+dlbIva),2), intCodTipDocFacVenEle, intCodDoc, objClass.strTipPerCli , DatFecDoc )){
               if(pantallaPrincipal.objAsiDia.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDocFacVenEle,  String.valueOf(intCodDoc), String.valueOf(intNumDoc), datFecAux )) {

               }else  return false;
              }else  return false;
             }else  return false;
            }else  return false;

            dlbSub=0;
            dlbIva=0;
            dblValTotDes=0;
            intConTad=0;
            intEstEje=0;
            strSecEmpGrp="";
         }
      }

      if(intEstEje==1){

          //strSecEmpGrp=" select max(secemp) as secemp,  max(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x  ";
          strSecEmpGrp=" select max(secemp) as secemp,  max(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x where secgrp > 0  ";
            rstLoc04=stmLoc02.executeQuery(strSecEmpGrp);
            if(rstLoc04.next()){
                intSecEmp=rstLoc04.getInt("secemp");
                intSecGrp=rstLoc04.getInt("secgrp");
            }
            rstLoc04.close();
            rstLoc04=null;
            
        //intSecEmp=objUltDocPrint.getNumSecDoc(conIns, intCodEmp );
        //intSecGrp=objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() );
            
        intSecEmp=(intSecEmp==0)?objUltDocPrint.getNumSecDoc(conIns, intCodEmp ):intSecEmp;
        intSecGrp=(intSecGrp==0)?objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() ):intSecGrp;

        generaAsientoVen( objUti.redondear((dlbSub+dlbIva),2), objUti.redondear(dlbSub,2), objUti.redondear(dlbIva, 2)  , objUti.redondear(dblValTotDes, 2) , intCodCliPrv );
        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

        if(objClass._generaVenCab(conIns, strFec, intSecEmp, intSecGrp,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDocFacVenEle , intCodDoc, intNumDoc, intCodCliPrv, (dlbSub*-1),  (dlbIva*-1) , intCodTipDocFac )){
         if(objClass._CalculoPagoVen(conIns)){
          if(objClass._calculaPagosVen(conIns, dlbSub, dlbIva, objUti.redondear((dlbSub+dlbIva),2), intCodTipDocFacVenEle, intCodDoc, objClass.strTipPerCli, DatFecDoc  )){
           if(pantallaPrincipal.objAsiDia.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDocFacVenEle,  String.valueOf(intCodDoc), String.valueOf(intNumDoc), datFecAux )) {

           }else  return false;
          }else  return false;
        }else  return false;
       }else  return false;
      }

      if(intControl==1){
       pantallaPrincipal.txtSql.append(stbins.toString()+" ; \n");
       stmLoc.executeUpdate( stbins.toString() );
       strSql="DELETE FROM tbm_detmovinv WHERE co_Emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intCodLocOri+" AND co_tipdoc="+intCodTipDocFac+" AND co_doc in ( "+strCodDoc+" ) ; ";
       pantallaPrincipal.txt.append( strSql+" \n" );
       strSql="DELETE FROM tbm_cabmovinv WHERE co_Emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intCodLocOri+" AND co_tipdoc="+intCodTipDocFac+" AND co_doc in ( "+strCodDoc+" ) ; ";
       pantallaPrincipal.txt.append( strSql+" \n" );
      }
      stbins=null;

   }
   rstLoc03.close();
   rstLoc03=null;
 }
 rstLoc01.close();
 rstLoc01=null;

   objZafParSis.setCodigoLocal(intCodLocOri);


     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc03.close();
     stmLoc03=null;
     stmLoc02.close();
     stmLoc02=null;

    blnRes=true;
 }}catch(java.sql.SQLException e) { blnRes=false; System.out.println(" generaFacVem"+ e.toString()); mensaje(" generaFacVem "+ e.toString()); }
   catch(Exception e) { blnRes=false;System.out.println(" generaFacVem"+ e.toString()); mensaje(" generaFacVem "+ e.toString());  }
 return blnRes;
}




private boolean generaFacCom(java.sql.Connection conIns ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03, stmLoc04;
 java.sql.ResultSet rstLoc, rstLoc01, rstLoc03, rstLoc04;
 int intNumLin=0;
 int intCodCliPrv=0;
 int intCodTipDocFac=0;
 int intCodDoc=0;
 int intConTad=0;
 int intNumDoc=0;
 int intCodTipDoc=0;
 String strSql="";
 String strCodDoc="";
 int intControl=0;
 StringBuffer stbins;
 double dlbSub=0;
 double dlbIva=0;
 double dlbPorIva=12;
 int intCodBod=0;
 int intEstEje=0;
 int intSecEmp=0;
 int intSecGrp=0;
 int intCodLocFin=0;
 int intCodLocOri=0;
 String strFec="";
 String strFecDoc="";
 String strSecEmpGrp="";
 String strSqlVerfRel="";
 java.util.Date DatFecDoc= new java.util.Date();
 int intCodEmp=0;
 try{
    if(conIns!=null){
     stmLoc=conIns.createStatement();
     stmLoc01=conIns.createStatement();
     stmLoc03=conIns.createStatement();
     stmLoc02=conIns.createStatement();
     stmLoc04=conIns.createStatement();

     intCodTipDoc=2;

     intCodLocOri=objZafParSis.getCodigoLocal();


     objTipDoc.cargarTipoDoc(intCodTipDoc);
     objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis, 2 );

     intNumLin=intNumLimImp;


 strSql="SELECT co_empper, co_loccom, co_tipdoccom, co_loccomfin, co_tipdoccomfin FROM " +
 " tbr_bodemp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intCodLocOri+" AND co_tipdoccomfin="+intCodTipDoc+" "+
 " GROUP BY co_empper, co_loccom, co_tipdoccom, co_loccomfin, co_tipdoccomfin  ";
 rstLoc01=stmLoc01.executeQuery(strSql);
 while(rstLoc01.next()){

    intCodTipDocFac=rstLoc01.getInt("co_tipdoccom");
    intCodLocFin=rstLoc01.getInt("co_loccomfin");
    objZafParSis.setCodigoLocal(intCodLocFin);

    strSql="SELECT distinct(a.fe_doc) as fe_doc, a.co_cli FROM tbm_cabmovinv AS a " +
    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLocOri+" AND a.co_tipdoc="+intCodTipDocFac+"  "+strTipoBus+" "+
    "  AND a.fe_doc <= '"+strFecCorte+"'  ORDER BY a.fe_Doc  ";
    rstLoc03=stmLoc03.executeQuery(strSql);
    while(rstLoc03.next()){
      intCodCliPrv=rstLoc03.getInt("co_cli");
      strFecDoc=rstLoc03.getString("fe_doc");
      stbins=new StringBuffer(); //VARIABLE TIPO BUFFER

      intConTad=0; // CONTADOR DE LINEA
      strCodDoc="";
      intControl=0;
      intEstEje=0;
      dlbSub=0;
      dlbIva=0;

      String strEstConf="";
      String strConF="";
      String strConE="";
      String strConC="";
      String strConP="";
      strSecEmpGrp="";
      intSecEmp=0;
      intSecGrp=0;

      /*strSql="SELECT a.st_coninv,  a1.st_meringegrfisbod "+  //CASE WHEN a.st_coninv='F' THEN 'N' ELSE 'S' END AS st_meringegrfisbod " +
      " ,a.co_emp, a.co_loc, a.co_tipdoc, a.fe_doc,  a.co_Doc, a1.co_reg,  a.co_cli, a.ne_secemp, a.ne_secgrp,  a.tx_nomcli, a1.tx_codalt, a1.nd_can, a.st_reg " +
      " ,a1.tx_codAlt, a1.tx_codAlt2, a1.co_itm, a1.co_itmact, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosuni " +
      " ,a1.co_bod, a1.nd_can, a1.nd_tot, a1.nd_cosUnigrp, a1.nd_costot, a1.nd_preUni, a1.nd_porDes, a1.st_ivaCom "+
      " ,a1.nd_costotgrp, a1.st_regrep, a1.nd_cancon, a1.tx_nombodorgdes "
    + " ,a1.st_cliretemprel, a1.fe_retemprel, a1.tx_perretemprel, a1.tx_vehretemprel, a1.tx_plavehretemprel, a1.tx_obscliretemprel " +
      " FROM tbm_cabmovinv AS a " +
      " INNER JOIN tbm_detmovinv AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLocOri+" AND a.co_tipdoc="+intCodTipDocFac+" " +
      " AND a.co_cli="+intCodCliPrv+" AND a.fe_doc='"+strFecDoc+"' "+strTipoBus+" "+
      " ORDER BY a1.nd_tot, a.ne_secemp "; //a.ne_secemp,a1.tx_codalt  ";*/

      strSql=" SELECT a.st_coninv,  a1.st_meringegrfisbod,a.co_emp, a.co_loc, a.co_tipdoc, a.fe_doc, a.co_Doc, a1.co_reg,  a.co_cli, "
            + " case when a.ne_secemp is null then 0 else a.ne_secemp end as ne_secemp, case when a.ne_secgrp is null then 0 else a.ne_secgrp end as ne_secgrp, "
            + " a.tx_nomcli, a1.tx_codalt, a1.nd_can, a.st_reg, a1.tx_codAlt, a1.tx_codAlt2, a1.co_itm, a1.co_itmact, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosuni, "
            + " a1.co_bod, a1.nd_can, a1.nd_tot, a1.nd_cosUnigrp, a1.nd_costot, a1.nd_preUni, a1.nd_porDes, a1.st_ivaCom, "
            + " a1.nd_costotgrp, a1.st_regrep, a1.nd_cancon, a1.tx_nombodorgdes, "
            + " a1.st_cliretemprel, a1.fe_retemprel, a1.tx_perretemprel, a1.tx_vehretemprel, a1.tx_plavehretemprel, a1.tx_obscliretemprel "
            + " FROM tbm_cabmovinv AS a "
            + " INNER JOIN tbm_detmovinv AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLocOri+" AND a.co_tipdoc="+intCodTipDocFac+" "
            + " AND a.co_cli="+intCodCliPrv+" AND a.fe_doc='"+strFecDoc+"' "+strTipoBus+" "
            + " ORDER BY a1.nd_tot, a.ne_secemp "; //a.ne_secemp,a1.tx_codalt  ";
//      System.out.println("PROBLEMA: " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

          intEstEje=1;
          if(intControl==1) strCodDoc +=","+rstLoc.getString("co_Doc");
          else strCodDoc += rstLoc.getString("co_Doc");

          if(intConTad>=1) strSecEmpGrp+=" UNION ALL ";
          strSecEmpGrp+=" SELECT  "+rstLoc.getInt("ne_secemp")+" AS secemp,  "+rstLoc.getInt("ne_secgrp")+" AS secgrp ";

           intConTad++;

          intControl=1;
          
          intCodEmp=objZafParSis.getCodigoEmpresa();
          
          if(intConTad==1){
             //intSecEmp=rstLoc.getInt("ne_secemp");
             //intSecGrp=rstLoc.getInt("ne_secgrp");
             strFec=rstLoc.getString("fe_doc");
             DatFecDoc=rstLoc.getDate("fe_doc");
             intCodBod=rstLoc.getInt("co_bod");
             intCodDoc = objClass._getCodigoDoc(conIns, intCodTipDoc );
             intNumDoc = objClass._getUltNumDoc(conIns, intCodTipDoc );
             strNumCom +="\n"+intNumDoc;
          }

          if( rstLoc.getString("st_coninv").equals("F"))
           strConF="F";
          else if( rstLoc.getString("st_coninv").equals("E"))
           strConE="E";
          else if( rstLoc.getString("st_coninv").equals("C"))
            strConC="C";
          else
            strConP="P";


          /*strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
          " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
          " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
          ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosuni, tx_nombodorgdes"
         + " ,st_cliretemprel, fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel, tx_obscliretemprel ) " +
          " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", "+intConTad+", " +
          " '"+rstLoc.getString("tx_codAlt")+"', '"+rstLoc.getString("tx_codAlt2")+"' "+
          " ,"+rstLoc.getString("co_itm")+", "+rstLoc.getString("co_itmact")+"  "+
          " ,'"+rstLoc.getString("tx_nomItm")+"', '"+rstLoc.getString("tx_unimed")+"' "+
          " ,"+rstLoc.getString("co_bod")+", "+rstLoc.getString("nd_can")+"  "+
          " ,"+rstLoc.getString("nd_tot")+", "+rstLoc.getString("nd_cosUnigrp")+"  "+
          " ,"+rstLoc.getString("nd_costot")+", "+rstLoc.getString("nd_preUni")+"  "+
          " ,"+rstLoc.getString("nd_porDes")+", '"+rstLoc.getString("st_ivaCom")+"'  "+
          " , "+rstLoc.getString("nd_costotgrp")+"  "+
          " ,'I', '"+rstLoc.getString("st_meringegrfisbod")+"', "+rstLoc.getString("nd_cancon")+", "+rstLoc.getString("nd_cosuni")+" " +
          " , '"+rstLoc.getString("tx_nombodorgdes")+"' "
          + ", "+(rstLoc.getString("st_cliretemprel")==null?null:"'"+rstLoc.getString("st_cliretemprel")+"'")+" "
          + ", "+(rstLoc.getString("fe_retemprel")==null?null:"'"+rstLoc.getString("fe_retemprel")+"'")+" "


          + ", "+(rstLoc.getString("tx_perretemprel")==null?null:"'"+rstLoc.getString("tx_perretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_vehretemprel")==null?null:"'"+rstLoc.getString("tx_vehretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_plavehretemprel")==null?null:"'"+rstLoc.getString("tx_plavehretemprel")+"'")+" "
          + ", "+(rstLoc.getString("tx_obscliretemprel")==null?null:"'"+rstLoc.getString("tx_obscliretemprel")+"'")+" "

           
          + " ) ; ";*/

          strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
          " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
          " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
          ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosuni, tx_nombodorgdes"+
          " ,st_cliretemprel, fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel, tx_obscliretemprel ) " +
          " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", "+intConTad+", " +
          " '"+rstLoc.getString("tx_codAlt")+"', '"+rstLoc.getString("tx_codAlt2")+"' "+
          " ,"+rstLoc.getString("co_itm")+", "+rstLoc.getString("co_itmact")+"  "+
          " ,"+objUti.codificar(rstLoc.getString("tx_nomItm"))+", '"+rstLoc.getString("tx_unimed")+"' "+
          " ,"+rstLoc.getString("co_bod")+", "+rstLoc.getString("nd_can")+"  "+
          " ,"+rstLoc.getString("nd_tot")+", "+rstLoc.getString("nd_cosUnigrp")+"  "+
          " ,"+rstLoc.getString("nd_costot")+", "+rstLoc.getString("nd_preUni")+"  "+
          " ,"+rstLoc.getString("nd_porDes")+", '"+rstLoc.getString("st_ivaCom")+"'  "+
          " , "+rstLoc.getString("nd_costotgrp")+"  "+
          " ,'I', '"+rstLoc.getString("st_meringegrfisbod")+"', "+rstLoc.getString("nd_cancon")+", "+rstLoc.getString("nd_cosuni")+" " +
          " , '"+rstLoc.getString("tx_nombodorgdes")+"' " +
          ", "+(rstLoc.getString("st_cliretemprel")==null?null:"'"+rstLoc.getString("st_cliretemprel")+"'")+" " +
          ", "+(rstLoc.getString("fe_retemprel")==null?null:"'"+rstLoc.getString("fe_retemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_perretemprel")==null?null:"'"+rstLoc.getString("tx_perretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_vehretemprel")==null?null:"'"+rstLoc.getString("tx_vehretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_plavehretemprel")==null?null:"'"+rstLoc.getString("tx_plavehretemprel")+"'")+" " +
          ", "+(rstLoc.getString("tx_obscliretemprel")==null?null:"'"+rstLoc.getString("tx_obscliretemprel")+"'")+" " +         
          " ) ; ";
          
          stbins.append( strSql );


           strSqlVerfRel=" select co_doc  from tbm_detingegrmerbod where co_emp="+rstLoc.getInt("co_emp")+" and co_locrel="+rstLoc.getInt("co_loc")+" " +
           " and co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" and co_docrel="+rstLoc.getInt("co_doc")+" and co_regrel="+rstLoc.getInt("co_reg")+" ";
           rstLoc04=stmLoc04.executeQuery(strSqlVerfRel);
           if(rstLoc04.next()){

                  strSql="UPDATE tbm_detingegrmerbod SET co_locrel="+objZafParSis.getCodigoLocal()+" ,co_tipdocrel="+intCodTipDoc+" " +
                  " ,co_docrel="+intCodDoc+" ,co_regrel="+intConTad+"  " +
                  " WHERE " +
                  " co_emp="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+"" +
                  " AND co_docrel="+rstLoc.getInt("co_doc")+"  AND co_regrel="+rstLoc.getInt("co_reg")+" ; ";
                  
                  pantallaPrincipal.txt.append( strSql+" \n" );
            }
            rstLoc04.close();
            rstLoc04=null;

            strSqlVerfRel=" select co_doc  from tbm_cabingegrmerbod where co_emp="+rstLoc.getInt("co_emp")+" and co_locrel="+rstLoc.getInt("co_loc")+" " +
           " and co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" and co_docrel="+rstLoc.getInt("co_doc")+" ";
           rstLoc04=stmLoc04.executeQuery(strSqlVerfRel);
           if(rstLoc04.next()){
               
                  strSql="UPDATE tbm_cabingegrmerbod SET co_locrel="+objZafParSis.getCodigoLocal()+" ,co_tipdocrel="+intCodTipDoc+" " +
                  " ,co_docrel="+intCodDoc+" " +
                  " WHERE " +
                  " co_emp="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+"" +
                  " AND co_docrel="+rstLoc.getInt("co_doc")+" ; ";
                  pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;

           strSqlVerfRel=" select co_emp  from tbm_coninvdocingbod where co_emprel="+rstLoc.getInt("co_emp")+" and co_locrel="+rstLoc.getInt("co_loc")+" " +
           " and co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" and co_docrel="+rstLoc.getInt("co_doc")+" ";
           System.out.println("tbm_coninvdocingbod: \n" + strSqlVerfRel);
           rstLoc04=stmLoc04.executeQuery(strSqlVerfRel);
           if(rstLoc04.next()){
                  strSql="UPDATE tbm_coninvdocingbod SET co_locrel="+objZafParSis.getCodigoLocal()+" ,co_tipdocrel="+intCodTipDoc+" " +
                  " ,co_docrel="+intCodDoc+" " +
                  " WHERE " +
                  " co_emprel="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+"" +
                  " AND co_docrel="+rstLoc.getInt("co_doc")+" ; ";
                  System.out.println("---->>> " + strSql);
                  pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;



           strSqlVerfRel=" select co_doc  from tbr_detmovinv where co_emprel="+rstLoc.getInt("co_emp")+" and co_locrel="+rstLoc.getInt("co_loc")+" " +
           " and co_tipdocrel="+rstLoc.getInt("co_tipdoc")+" and co_docrel="+rstLoc.getInt("co_doc")+" and co_regrel="+rstLoc.getInt("co_reg")+" ";
           rstLoc04=stmLoc04.executeQuery(strSqlVerfRel);
           if(rstLoc04.next()){

              strSql="UPDATE tbr_detmovinv SET co_emprel="+objZafParSis.getCodigoEmpresa()+", co_locrel="+objZafParSis.getCodigoLocal()+" ,co_tipdocrel="+intCodTipDoc+" " +
              " ,co_docrel="+intCodDoc+" ,co_regrel="+intConTad+"  " +
              " WHERE " +
              " co_emprel="+rstLoc.getInt("co_emp")+" AND co_locrel="+rstLoc.getInt("co_loc")+" AND co_tipdocrel="+rstLoc.getInt("co_tipdoc")+"" +
              " AND co_docrel="+rstLoc.getInt("co_doc")+"  AND co_regrel="+rstLoc.getInt("co_reg")+" ; ";
              pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;



           strSqlVerfRel=" select co_doc  from tbr_detmovinv where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" " +
           " and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" and co_reg="+rstLoc.getInt("co_reg")+" ";
           rstLoc04=stmLoc04.executeQuery(strSqlVerfRel);
           if(rstLoc04.next()){

              strSql="UPDATE tbr_detmovinv SET co_emp="+objZafParSis.getCodigoEmpresa()+", co_loc="+objZafParSis.getCodigoLocal()+" ,co_tipdoc="+intCodTipDoc+" " +
              " ,co_doc="+intCodDoc+" ,co_reg="+intConTad+"  " +
              " WHERE " +
              " co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+"" +
              " AND co_doc="+rstLoc.getInt("co_doc")+"  AND co_reg="+rstLoc.getInt("co_reg")+" ; ";
              pantallaPrincipal.txt.append( strSql+" \n" );

            }
            rstLoc04.close();
            rstLoc04=null;




          dlbSub+=rstLoc.getDouble("nd_tot");

          if(rstLoc.getString("st_ivaCom").equals("S")){
             dlbIva += ((( rstLoc.getDouble("nd_tot") * dlbPorIva)==0)?0:( rstLoc.getDouble("nd_tot") * dlbPorIva)/100) ;
          }


         if(intNumLin==intConTad){

            strSecEmpGrp=" select min(secemp) as secemp,  min(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x where secgrp > 0   ";
            rstLoc04=stmLoc02.executeQuery(strSecEmpGrp);
            if(rstLoc04.next()){
                intSecEmp=rstLoc04.getInt("secemp");
                intSecGrp=rstLoc04.getInt("secgrp");
            }
            rstLoc04.close();
            rstLoc04=null;
            
            //intSecEmp=objUltDocPrint.getNumSecDoc(conIns, intCodEmp );
            //intSecGrp=objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() );
            
            intSecEmp=(intSecEmp==0)?objUltDocPrint.getNumSecDoc(conIns, intCodEmp ):intSecEmp;
            intSecGrp=(intSecGrp==0)?objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() ):intSecGrp;
            
             generaAsientoCom( objUti.redondear((dlbSub+dlbIva),2), objUti.redondear(dlbSub,2), objUti.redondear(dlbIva,2) , intCodBod, intCodCliPrv );
             datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());


            if(strConE.equals("E")) strEstConf="E";
            else{
              if( (strConP.equals("P")) && (strConC.equals("C")) ) strEstConf="E";
              else{
                   if(strConP.equals("P")) strEstConf="P";
                   else{
                       if(strConC.equals("C")) strEstConf="C";
                       else {
                         if(strConF.equals("F")) strEstConf="F";
                       }
                   }
                }
              }


            if(objClass._generaComCab(conIns, strFec, intSecEmp, intSecGrp,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc , intCodDoc, intNumDoc, intCodCliPrv, dlbSub,  dlbIva, intCodTipDocFac , strEstConf, 45 )){
             if(objClass._CalculoPagoCom(conIns)){
              if(objClass._calculaPagosCom(conIns, dlbSub, dlbIva, objUti.redondear((dlbSub+dlbIva),2), intCodTipDoc, intCodDoc, objClass.strTipPerCli, DatFecDoc  )){
               if(pantallaPrincipal.objAsiDia.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc,  String.valueOf(intCodDoc), String.valueOf(intNumDoc), datFecAux )) {

               }else  return false;
              }else  return false;
             }else  return false;
            }else  return false;
            dlbSub=0;
            dlbIva=0;
            intConTad=0;
            intEstEje=0;
            strSecEmpGrp="";

            strConF="";
            strConE="";
            strConC="";
            strConP="";
            strEstConf="";
         }

      }

      if(intEstEje==1){

           //strSecEmpGrp=" select min(secemp) as secemp,  min(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x  ";
           strSecEmpGrp=" select min(secemp) as secemp,  min(secgrp) as secgrp  from ( "+strSecEmpGrp+" ) as x where secgrp > 0   ";
            rstLoc04=stmLoc02.executeQuery(strSecEmpGrp);
            if(rstLoc04.next()){
                intSecEmp=rstLoc04.getInt("secemp");
                intSecGrp=rstLoc04.getInt("secgrp");
            }
            rstLoc04.close();
            rstLoc04=null;

        //intSecEmp=objUltDocPrint.getNumSecDoc(conIns, intCodEmp );
        //intSecGrp=objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() );
        
            intSecEmp=(intSecEmp==0)?objUltDocPrint.getNumSecDoc(conIns, intCodEmp ):intSecEmp;
            intSecGrp=(intSecGrp==0)?objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() ):intSecGrp;       
            
            generaAsientoCom( objUti.redondear((dlbSub+dlbIva),2), objUti.redondear(dlbSub,2), objUti.redondear(dlbIva,2) , intCodBod, intCodCliPrv );  
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

            if(strConE.equals("E")) strEstConf="E";
                else{
                  if( (strConP.equals("P")) && (strConC.equals("C")) ) strEstConf="E";
                  else{
                       if(strConP.equals("P")) strEstConf="P";
                       else{
                           if(strConC.equals("C")) strEstConf="C";
                           else {
                             if(strConF.equals("F")) strEstConf="F";
                           }
                   }
                }
              }


        if(objClass._generaComCab(conIns,strFec, intSecEmp, intSecGrp,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc , intCodDoc, intNumDoc, intCodCliPrv, dlbSub,  dlbIva , intCodTipDocFac, strEstConf, 45  )){
         if(objClass._CalculoPagoCom(conIns)){
          if(objClass._calculaPagosCom(conIns, dlbSub, dlbIva, objUti.redondear((dlbSub+dlbIva),2), intCodTipDoc, intCodDoc, objClass.strTipPerCli , DatFecDoc  )){
          if(pantallaPrincipal.objAsiDia.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc,  String.valueOf(intCodDoc), String.valueOf(intNumDoc), datFecAux )) {

                        }else  return false;
                    }else  return false;
                }else  return false;
            }else  return false;
        }

      if(intControl==1){
       pantallaPrincipal.txtSql.append(stbins.toString()+" ; \n");
       stmLoc.executeUpdate( stbins.toString() );
       strSql="DELETE FROM tbm_detmovinv WHERE co_Emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intCodLocOri+" AND co_tipdoc="+intCodTipDocFac+" AND co_doc in ( "+strCodDoc+" ) ;";
       pantallaPrincipal.txt.append( strSql+" \n" );
       strSql="DELETE FROM tbm_cabmovinv WHERE co_Emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intCodLocOri+" AND co_tipdoc="+intCodTipDocFac+" AND co_doc in ( "+strCodDoc+" ) ; ";
       pantallaPrincipal.txt.append( strSql+" \n" );


      }
      stbins=null;

  }
  rstLoc03.close();
  rstLoc03=null;

 }
 rstLoc01.close();
 rstLoc01=null;
    objZafParSis.setCodigoLocal(intCodLocOri);

     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc03.close();
     stmLoc03=null;
     stmLoc02.close();
     stmLoc02=null;
     stmLoc04.close();
     stmLoc04=null;

    blnRes=true;
 }}catch(java.sql.SQLException e) { blnRes=false; mensaje(" generaFacCom "+ e.toString());  }
   catch(Exception e) { blnRes=false; mensaje(" generaFacCom "+ e.toString()); }
 return blnRes;
}



  private void mensaje(String strMes){
      //javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
     // obj.showMessageDialog( null, strMes, "Mensaje Zafiro..", javax.swing.JOptionPane.INFORMATION_MESSAGE );
      objClass.almacenarArchHis(strNomArchHis, strMes );
  }


 /*
 USO DE NUEVAS CUENTAS SEGUN CORREO 26/Agosto/2015

 Eddye:

Hemos procedido a crear las cuentas deudoras que se van a utilizar en movimientos relacionados según lo acordado en la 
última reunión con el Sr. Sensi y Werner, “Todo movimiento que no sea préstamo entre bodegas se van a contabilizar en 
 estas cuentas según sea el caso (Deudor saldo positivo, Acreedor saldo negativo)”.

Las cuentas creadas  son estas:
TUVAL
4385 1.01.03.01.90 Compañias Relacionadas Castek
4386 1.01.03.01.91 Compañias Relacionadas Dimulti

CASTEK 
2110 1.01.03.01.90 Compañias Relacionadas Tuval
2111 1.01.03.01.91 Compañias Relacionadas Dimulti

DIMULTI
3061 1.01.03.01.90 Compañias Relacionadas Castek
3062 1.01.03.01.91 Compañias Relacionadas Tuval 
 **/

public void generaAsientoVen(double dblTotalCot, double dblSubtotalCot, double dblIvaCot, double dblValTotDes, int intCodCli ){
 pantallaPrincipal.objAsiDia.inicializar();
 java.util.Vector vecAsiento = new java.util.Vector();
  int intCamCta=0,intCodCtaTuvCas=0,intCodCtaTuvDim=0,intCodCtaCasTuv=0,intCodCtaCasDim=0,intCodCtaDimTuv=0,intCodCtaDimCas=0;
 String strCodCtaTuvCas,strCodCtaTuvDim,strNomCtaTuvCas,strNomCtaTuvDim;
 String strCodCtaCasTuv,strCodCtaCasDim,strNomCtaCasTuv,strNomCtaCasDim;
 String strCodCtaDimTuv,strCodCtaDimCas,strNomCtaDimTuv,strNomCtaDimCas;
 boolean blnCtaUsa=true; 
 
 if(blnCtaUsa==false){
     
    intCodCtaTuvCas=2636;
    intCodCtaTuvDim=2637;
    strCodCtaTuvCas="2.01.03.06.01";
    strCodCtaTuvDim="2.01.03.06.02";
    strNomCtaTuvCas="CASTEK S.A.";
    strNomCtaTuvDim="DIMULTI S.A.";

    intCodCtaCasTuv=1156;
    intCodCtaCasDim=1159;
    strCodCtaCasTuv="1.01.03.04.01";
    strCodCtaCasDim="2.01.03.06.01";
    strNomCtaCasTuv="TUVAL S.A.";
    strNomCtaCasDim="DIMULTI S.A.";

    intCodCtaDimTuv=2098;
    intCodCtaDimCas=2099;
    strCodCtaDimTuv="1.01.03.03.01";
    strCodCtaDimCas="1.01.03.03.02";
    strNomCtaDimTuv="TUVAL S.A.";
    strNomCtaDimCas="CASTEK S.A.";
 }
 else{
    //     TUVAL
    //4385 1.01.03.01.90 Compañias Relacionadas Castek
    //4386 1.01.03.01.91 Compañias Relacionadas Dimulti
    intCodCtaTuvCas=4385;strCodCtaTuvCas="1.01.03.01.90";strNomCtaTuvCas="Compañias Relacionadas Castek";
    intCodCtaTuvDim=4386;strCodCtaTuvDim="1.01.03.01.91";strNomCtaTuvDim="Compañias Relacionadas Dimulti";
    //      CASTEK 
    //2110 1.01.03.01.90 Compañias Relacionadas Tuval
    //2111 1.01.03.01.91 Compañias Relacionadas Dimulti
    intCodCtaCasTuv=2110;strCodCtaCasTuv="1.01.03.01.90";strNomCtaCasTuv="Compañias Relacionadas Tuval";
    intCodCtaCasDim=2111;strCodCtaCasDim="1.01.03.01.91";strNomCtaCasDim="Compañias Relacionadas Dimulti";
    //      DIMULTI
    //3061 1.01.03.01.90 Compañias Relacionadas Castek
    //3062 1.01.03.01.91 Compañias Relacionadas Tuval
    intCodCtaDimCas=3061;strCodCtaDimCas="1.01.03.01.90";strNomCtaDimCas="Compañias Relacionadas Castek";
    intCodCtaDimTuv=3062;strCodCtaDimTuv="1.01.03.01.91";strNomCtaDimTuv="Compañias Relacionadas Tuval";   
 }
 int intCodCta=0;
 String strCodCta="";
 String strNomCta="";



 if(objZafParSis.getCodigoEmpresa()==1){
   if(intCodCli==603){   // castek
     intCamCta=1;
     intCodCta=intCodCtaTuvCas;
     strCodCta=strCodCtaTuvCas;
     strNomCta=strNomCtaTuvCas;
   }if(intCodCli==1039){  // dimulti
     intCamCta=1;
     intCodCta=intCodCtaTuvDim;
     strCodCta=strCodCtaTuvDim;
     strNomCta=strNomCtaTuvDim;
   }
 }

 if(objZafParSis.getCodigoEmpresa()==2){
   if(intCodCli==2854){   // tuval
     intCamCta=1;
     intCodCta=intCodCtaCasTuv;
     strCodCta=strCodCtaCasTuv;
     strNomCta=strNomCtaCasTuv;
   }if(intCodCli==789){  // dimulti
     intCamCta=1;
     intCodCta=intCodCtaCasDim;
     strCodCta=strCodCtaCasDim;
     strNomCta=strNomCtaCasDim;
   }
 }

if(objZafParSis.getCodigoEmpresa()==4){
   if(intCodCli==3117){   // tuval
     intCamCta=1;
     intCodCta=intCodCtaDimTuv;
     strCodCta=strCodCtaDimTuv;
     strNomCta=strNomCtaDimTuv;
   }if(intCodCli==498){  // castek
     intCamCta=1;
     intCodCta=intCodCtaDimCas;
     strCodCta=strCodCtaDimCas;
     strNomCta=strNomCtaDimCas;
   }
 }





 if(intCamCta==1){
     if(dblTotalCot>0){
        java.util.Vector vecRowAsientoDeb = vecFila2( intCodCta, strCodCta, strNomCta );
        vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblTotalCot,6)), 5);
        vecAsiento.add(vecRowAsientoDeb);
     }
 }else{
 if(dblTotalCot>0){
    java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb());
    vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblTotalCot,6)), 5);
    vecAsiento.add(vecRowAsientoDeb);
 }}





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
}

 /*
 USO DE NUEVAS CUENTAS SEGUN CORREO 26/Agosto/2015

 Eddye:

Hemos procedido a crear las cuentas deudoras que se van a utilizar en movimientos relacionados según lo acordado en la 
última reunión con el Sr. Sensi y Werner, “Todo movimiento que no sea préstamo entre bodegas se van a contabilizar en 
 estas cuentas según sea el caso (Deudor saldo positivo, Acreedor saldo negativo)”.

Las cuentas creadas  son estas:
TUVAL
4385 1.01.03.01.90 Compañias Relacionadas Castek
4386 1.01.03.01.91 Compañias Relacionadas Dimulti

CASTEK 
2110 1.01.03.01.90 Compañias Relacionadas Tuval
2111 1.01.03.01.91 Compañias Relacionadas Dimulti

DIMULTI
3061 1.01.03.01.90 Compañias Relacionadas Castek
3062 1.01.03.01.91 Compañias Relacionadas Tuval 
 **/



private void generaAsientoCom(double dblTotal, double dblSubTot, double dblIva, int intCodBod, int intCodCli ){
pantallaPrincipal.objAsiDia.inicializar();
int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
int INT_VEC_DEBE   = 5; //5) Debe.
int INT_VEC_HABER  = 6; //6) Haber.
int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
int INT_VEC_NUEVO    = 8;
java.util.Vector vecReg;
String stIvaCom="S";

 int intCamCta=0,intCodCtaTuvCas=0,intCodCtaTuvDim=0,intCodCtaCasTuv=0,intCodCtaCasDim=0,intCodCtaDimTuv=0,intCodCtaDimCas=0;
 String strCodCtaTuvCas,strCodCtaTuvDim,strNomCtaTuvCas,strNomCtaTuvDim;
 String strCodCtaCasTuv,strCodCtaCasDim,strNomCtaCasTuv,strNomCtaCasDim;
 String strCodCtaDimTuv,strCodCtaDimCas,strNomCtaDimTuv,strNomCtaDimCas;
 boolean blnCtaUsa=true; 
if(blnCtaUsa==false){
     
    intCodCtaTuvCas=2636;
    intCodCtaTuvDim=2637;
    strCodCtaTuvCas="2.01.03.06.01";
    strCodCtaTuvDim="2.01.03.06.02";
    strNomCtaTuvCas="CASTEK S.A.";
    strNomCtaTuvDim="DIMULTI S.A.";

    intCodCtaCasTuv=1156;
    intCodCtaCasDim=1159;
    strCodCtaCasTuv="1.01.03.04.01";
    strCodCtaCasDim="2.01.03.06.01";
    strNomCtaCasTuv="TUVAL S.A.";
    strNomCtaCasDim="DIMULTI S.A.";

    intCodCtaDimTuv=2098;
    intCodCtaDimCas=2099;
    strCodCtaDimTuv="1.01.03.03.01";
    strCodCtaDimCas="1.01.03.03.02";
    strNomCtaDimTuv="TUVAL S.A.";
    strNomCtaDimCas="CASTEK S.A.";
 }
 else{
    //     TUVAL
    //4385 1.01.03.01.90 Compañias Relacionadas Castek
    //4386 1.01.03.01.91 Compañias Relacionadas Dimulti
    intCodCtaTuvCas=4385;strCodCtaTuvCas="1.01.03.01.90";strNomCtaTuvCas="Compañias Relacionadas Castek";
    intCodCtaTuvDim=4386;strCodCtaTuvDim="1.01.03.01.91";strNomCtaTuvDim="Compañias Relacionadas Dimulti";
    //      CASTEK 
    //2110 1.01.03.01.90 Compañias Relacionadas Tuval
    //2111 1.01.03.01.91 Compañias Relacionadas Dimulti
    intCodCtaCasTuv=2110;strCodCtaCasTuv="1.01.03.01.90";strNomCtaCasTuv="Compañias Relacionadas Tuval";
    intCodCtaCasDim=2111;strCodCtaCasDim="1.01.03.01.91";strNomCtaCasDim="Compañias Relacionadas Dimulti";
    //      DIMULTI
    //3061 1.01.03.01.90 Compañias Relacionadas Castek
    //3062 1.01.03.01.91 Compañias Relacionadas Tuval
    intCodCtaDimCas=3061;strCodCtaDimCas="1.01.03.01.90";strNomCtaDimCas="Compañias Relacionadas Castek";
    intCodCtaDimTuv=3062;strCodCtaDimTuv="1.01.03.01.91";strNomCtaDimTuv="Compañias Relacionadas Tuval";   
 }

 int intCodCta=0;
 String strCodCta="";
 String strNomCta="";


//********************************************************


 if(objZafParSis.getCodigoEmpresa()==1){
   if(intCodCli==603){   // castek
     intCamCta=1;
     intCodCta=intCodCtaTuvCas;
     strCodCta=strCodCtaTuvCas;
     strNomCta=strNomCtaTuvCas;
   }if(intCodCli==1039){  // dimulti
     intCamCta=1;
     intCodCta=intCodCtaTuvDim;
     strCodCta=strCodCtaTuvDim;
     strNomCta=strNomCtaTuvDim;
   }
 }

 if(objZafParSis.getCodigoEmpresa()==2){
   if(intCodCli==2854){   // tuval
     intCamCta=1;
     intCodCta=intCodCtaCasTuv;
     strCodCta=strCodCtaCasTuv;
     strNomCta=strNomCtaCasTuv;
   }if(intCodCli==789){  // dimulti
     intCamCta=1;
     intCodCta=intCodCtaCasDim;
     strCodCta=strCodCtaCasDim;
     strNomCta=strNomCtaCasDim;
   }
 }

if(objZafParSis.getCodigoEmpresa()==4){
   if(intCodCli==3117){   // tuval
     intCamCta=1;
     intCodCta=intCodCtaDimTuv;
     strCodCta=strCodCtaDimTuv;
     strNomCta=strNomCtaDimTuv;
   }if(intCodCli==498){  // castek
     intCamCta=1;
     intCodCta=intCodCtaDimCas;
     strCodCta=strCodCtaDimCas;
     strNomCta=strNomCtaDimCas;
   }
 }



if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
else vecDetDiario.removeAllElements();

if(dblSubTot > 0 ){
   vecReg = new java.util.Vector();
   vecReg.add(INT_LINEA, null);
   vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
   vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
   vecReg.add(INT_VEC_BOTON, null);
   vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
   vecReg.add(INT_VEC_DEBE, new Double(dblSubTot));
   vecReg.add(INT_VEC_HABER, new Double(0));
   vecReg.add(INT_VEC_REF, null);
    vecReg.add(INT_VEC_NUEVO, null);


   vecDetDiario.add(vecReg);
}



if(stIvaCom.equals("S")){
if (objTipDoc.getst_iva().equals("S")){
    vecReg = new java.util.Vector();
    vecReg.add(INT_LINEA, null);
    vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());
    vecReg.add(INT_VEC_BOTON, null);
    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());
    vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear( dblIva, objZafParSis.getDecimalesMostrar())));
    vecReg.add(INT_VEC_HABER, new Double(0));
    vecReg.add(INT_VEC_REF, null);
     vecReg.add(INT_VEC_NUEVO, null);
    vecDetDiario.add(vecReg);
}}


vecReg = new java.util.Vector();
vecReg.add(INT_LINEA, null);




if(intCamCta==1){
 vecReg.add(INT_VEC_CODCTA,new Integer( intCodCta  ));
 vecReg.add(INT_VEC_NUMCTA, strCodCta );
 vecReg.add(INT_VEC_BOTON, null);
 vecReg.add(INT_VEC_NOMCTA, strNomCta );
}else{
  vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
  vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
  vecReg.add(INT_VEC_BOTON, null);
  vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
}

vecReg.add(INT_VEC_DEBE, new Double(0));
vecReg.add(INT_VEC_HABER, new Double(objUti.redondear( dblTotal, objZafParSis.getDecimalesMostrar())));
vecReg.add(INT_VEC_REF, null);
 vecReg.add(INT_VEC_NUEVO, null);
vecDetDiario.add(vecReg);


pantallaPrincipal.objAsiDia.setDetalleDiario(vecDetDiario);
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
