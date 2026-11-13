/*
 * To change this template, choose Tools | Templates tbm_sercliserloc
 * and open the template in the editor.
 */

package proceso;

import Librerias.ZafUtil.ZafUtil;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
  
/**
 *
 * @author jayapata
 */
public class procesoClass {


   public String strTipPerCli="";
   Librerias.ZafParSis.ZafParSis objZafParSis;
   ZafUtil objUti;
   IniciarProceso pantallaPrincipal;
   int intarreglonum[] = new int[10];
   int intarreglodia[] = new int[10];
   int intarreglonumCom[] = new int[10];
   int intarreglodiaCom[] = new int[10];
   int intCanArr[]= new int[1];
   String strarreglosop[] = new String[10];
   String strCorEleTo="";
   String strNomArchHis="";

    public procesoClass(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin, String strNomArchHis ) {
      try{
        this.objZafParSis = obj;
        objUti = new ZafUtil();
        this.pantallaPrincipal = pantallaPrin;

      
        _getCorEleToSistemas();

       
        this.strNomArchHis = strNomArchHis;

        
       }catch(Exception e){   mensaje(" procesoClass "+ e.toString());   }
    }



    


public int _getCodigoDoc(java.sql.Connection conn, int intCodTipDoc ){
  int intCodDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
     " co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intCodDoc = rstLoc.getInt("co_doc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException e) {  mensaje("_getCodigoDoc "+ e.toString());    }
   catch(Exception e) {  mensaje("_getCodigoDoc "+ e.toString());   }
 return intCodDoc;
}


public int _getUltNumDoc(java.sql.Connection conn, int intCodTipDoc ){
  int intUltNumDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException e) {   mensaje("_getUltNumDoc "+ e.toString());    }
   catch(Exception e) {   mensaje("_getUltNumDoc "+ e.toString());   }
 return intUltNumDoc;
}



  private void mensaje(String strMes){
      //javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
     // obj.showMessageDialog( null, strMes, "Mensaje Zafiro..", javax.swing.JOptionPane.INFORMATION_MESSAGE );
      almacenarArchHis(strNomArchHis, strMes );
  }


public boolean _generaVenCab(java.sql.Connection conn, String strFec, int intSecEmp, int intSecGrp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, int intTipDocDocImpFac  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      double dlbPorIva=12;

      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());



       if(intTipDocDocImpFac==126){
               strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel , a.co_tipper  from tbm_cli as a " +
               " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
               " WHERE a.co_emp="+intCodEmp+" AND a.co_cli="+intCodCliPrv;
       }else if(intTipDocDocImpFac==166){
               strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel , a.co_tipper  from tbm_cli as a " +
               " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
               " WHERE a.co_emp="+intCodEmp+" AND a.co_cli="+intCodCliPrv;
       } else{
         strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli="+intCodCliPrv;
       }


     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         strTipPerCli=rstLoc.getString("co_tipper");

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
          " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
          ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
          " '', '', "+intNumDoc+" , 0, 0 , '' ,'' , "+ objUti.redondear(dlbSub,2)+" ,"+dlbPorIva+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )+", "+ objUti.redondear( dlbIva, 2)+" , 3 ,'Crédito 30-60 días', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'N', 'S', null )";
          pantallaPrincipal.txtSql.append(strSql+" ; \n");
         stmLoc.executeUpdate(strSql);

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false;  mensaje("_generaVenCab "+ e.toString());      }
    catch(Exception e)   { blnRes=false;  mensaje("_generaVenCab "+ e.toString());      }
 return blnRes;
}


public boolean _generaComCab(java.sql.Connection conn, String strFec, int intSecEmp, int intSecGrp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, int intTipDocDocImpFac, String strEstConf, int intCodMnu  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      double dlbPorIva=12;

      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());



       if(intTipDocDocImpFac==126){
               strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel , a.co_tipper  from tbm_cli as a " +
               " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
               " WHERE a.co_emp="+intCodEmp+" AND a.co_cli="+intCodCliPrv;
       }else{
         strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli="+intCodCliPrv;
       }


     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         strTipPerCli=rstLoc.getString("co_tipper");

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
          " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
          ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
          " '', '', "+intNumDoc+" , 0, 0 , '' ,'' , "+ objUti.redondear(dlbSub,2)+" ,"+dlbPorIva+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )+", "+ objUti.redondear( dlbIva, 2)+" , 3 ,'Crédito 30-60 días', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', "+intCodMnu+" "+
          " ,'N', 'S', '"+strEstConf+"' )";
          pantallaPrincipal.txtSql.append(strSql+" ;  \n");
          stmLoc.executeUpdate(strSql);

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false;  mensaje("_generaComCab "+ e.toString());     }
    catch(Exception e)   { blnRes=false;  mensaje("_generaComCab "+ e.toString());     }
 return blnRes;
}





public boolean _generaDevVenCab(java.sql.Connection conn, int intSecEmp, int intSecGrp, int intNumCot, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, String strFecDoc, int intTipMov, String strEstConf, StringBuffer stbinsTemp, int intCodMnu   ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();
      double dlbPorIva=12;

      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli="+intCodCliPrv;
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         strTipPerCli=rstLoc.getString("co_tipper");

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
          " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFecDoc+"', "+rstLoc.getString("co_cli")+" " +
          ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
          " '', '', "+intNumDoc+" , "+intNumCot+", 0 , '' ,'' , abs( "+objUti.redondear(dlbSub,2)+" ) * "+intTipMov+"  ,"+dlbPorIva+" , abs ("+objUti.redondear( (dlbSub+dlbIva),2 )+") * "+intTipMov+" ,  abs ("+objUti.redondear( dlbIva, 2)+" )* "+intTipMov+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', "+intCodMnu+" "+
          " ,'N', 'S', '"+strEstConf+"' )";
          pantallaPrincipal.txtSql.append(strSql+" ;  \n");
          stmLoc.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;


     strSql="SELECT * FROM ( "+stbinsTemp.toString()+" ) AS x GROUP BY co_emprel, co_locrel, co_tipdocrel, co_docrel ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        strSql="INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, st_reg, st_regrep, co_emprel, co_locrel, co_tipdocrel, co_docrel )" +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+",'A', 'I' ,"+rstLoc.getString("co_emprel")+" " +
        " ,"+rstLoc.getString("co_locrel")+","+rstLoc.getString("co_tipdocrel")+","+rstLoc.getString("co_docrel")+"  )";
        stmLoc01.executeUpdate(strSql);
     }
      rstLoc.close();
      rstLoc=null;



      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false;   mensaje("_generaDevVenCab "+ e.toString());     }
    catch(Exception e)   { blnRes=false;    mensaje("_generaDevVenCab "+ e.toString());     }
 return blnRes;
}

public boolean _generaDevComCab(java.sql.Connection conn, int intSecEmp, int intSecGrp, int intNumCot, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, String strFecDoc, int intTipMov, StringBuffer stbinsTemp ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      double dlbPorIva=12;

      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli="+intCodCliPrv;
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         strTipPerCli=rstLoc.getString("co_tipper");

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
          " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFecDoc+"', "+rstLoc.getString("co_cli")+" " +
          ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
          " '', '', "+intNumDoc+" , "+intNumCot+", 0 , '' ,'' , abs( "+objUti.redondear(dlbSub,2)+" ) * "+intTipMov+"  ,"+dlbPorIva+" , abs ("+objUti.redondear( (dlbSub+dlbIva),2 )+") * "+intTipMov+" ,  abs ("+objUti.redondear( dlbIva, 2)+" )* "+intTipMov+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'N', 'S', null )";
          pantallaPrincipal.txtSql.append(strSql+" ;  \n");
          stmLoc.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;




     strSql="SELECT * FROM ( "+stbinsTemp.toString()+" ) AS x GROUP BY co_emprel, co_locrel, co_tipdocrel, co_docrel ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        strSql="INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, st_reg, st_regrep, co_emprel, co_locrel, co_tipdocrel, co_docrel )" +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+",'A', 'I' ,"+rstLoc.getString("co_emprel")+" " +
        " ,"+rstLoc.getString("co_locrel")+","+rstLoc.getString("co_tipdocrel")+","+rstLoc.getString("co_docrel")+"  )";
        stmLoc.executeUpdate(strSql);
     }
     rstLoc.close();
     rstLoc=null;



      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false;  mensaje("_generaDevComCab "+ e.toString());     }
    catch(Exception e)   { blnRes=false;  mensaje("_generaDevComCab "+ e.toString());    }
 return blnRes;
}

public boolean _ingresarPagDevVen(java.sql.Connection conn, double dlbTot, int intCodTipDoc, int intCodDoc, java.util.Date datFecDoc, int intTip  ){
 boolean blnRes=false;
 int intVal =  intCanArr[0];
 int intsizearre = intarreglodia.length;
 intVal= intsizearre - (intsizearre-intVal);
 java.sql.Statement stmLoc;
 double dblBaseDePagos = dlbTot;
 String strSql="";
 int VarC = 0;
 String VarN = "N";
 String VarA = "A";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        java.util.Calendar calObj = java.util.Calendar.getInstance();
        calObj.setTime(datFecDoc);
        
        String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
        calObj.get(java.util.Calendar.MONTH)+1     ,
        calObj.get(java.util.Calendar.YEAR)  );



      int Fecha[] =  pantallaPrincipal._getFecha(strFechaCal);  //txtFecDoc.getFecha(txtFecDoc.getText());
      String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

      strSql = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
      " values("+ objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+intCodTipDoc+","+intCodDoc+", 1 ,"+
      VarC+",'"+strFecha+"',abs("+dblBaseDePagos+")*"+intTip+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I')";
      stmLoc.executeUpdate(strSql);

     stmLoc.close();
     stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException e) {  blnRes=false;   mensaje("_ingresarPagDevVen "+ e.toString());    }
    catch(Exception e){  blnRes=false;  mensaje("_ingresarPagDevVen "+ e.toString());   }
  return  blnRes;
}




public boolean _CalculoPagoCom(java.sql.Connection conPag){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="",strSql01="";
 try{
 if(conPag!=null){

    stmLoc=conPag.createStatement();

    strSql="SELECT A1.ne_numPag, A2.ne_diaCre " +
    " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
    " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
    " and A1.co_forPag = 3 Order by A2.co_reg";

    strSql01="SELECT count(A2.ne_diaCre) as c  " +
   " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
   " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
   " and A1.co_forPag = 3 ";
    rstLoc=stmLoc.executeQuery(strSql01);
    if(rstLoc.next()) intCanArr[0] = rstLoc.getInt(1);
    rstLoc.close();
    rstLoc=null;

    rstLoc=stmLoc.executeQuery(strSql);
    int x=0;
    while(rstLoc.next()){
      intarreglodiaCom[x]=rstLoc.getInt(2);
      intarreglonumCom[x]=rstLoc.getInt(1);
      x++;
    }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;
  blnRes=true;
 }}catch(Exception e) { blnRes=false;  mensaje("_CalculoPagoCom "+ e.toString());    }
  return blnRes;
}

public boolean _calculaPagosCom(java.sql.Connection conn, double dblSub, double dlbIva, double dlbTot, int intCodTipDoc, int intCodDoc, String strCodTipPerCli, java.util.Date datFecDoc ){
 boolean blnRes=false;
 int intVal =  intCanArr[0];
 int intsizearre = intarreglodiaCom.length;
 int intCodMotDoc=0;
 int intCodTipPerEmp=0;
 intVal= intsizearre - (intsizearre-intVal);
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 int intFilUltPag = 0;
 double dblTotalDoc=0, dblMonCal=0, dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
 String strSql="";
 int intNumDec=2;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();


    java.util.Calendar calObj = java.util.Calendar.getInstance();
    calObj.setTime(datFecDoc);

     String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
     calObj.get(java.util.Calendar.MONTH)+1     ,
     calObj.get(java.util.Calendar.YEAR)  );

//    txtFecDoc.setText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
//    calObj.get(java.util.Calendar.MONTH)+1     ,
//    calObj.get(java.util.Calendar.YEAR)  );


      java.util.Calendar objFec = java.util.Calendar.getInstance();
      int fecDoc [] =  pantallaPrincipal._getFecha(strFechaCal);  //txtFecDoc.getFecha(txtFecDoc.getText());

       objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
       objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
       objFec.set(java.util.Calendar.YEAR, fecDoc[2]);

      dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)
      dblTotalDoc = dlbTot;

      while(pantallaPrincipal.tblOrdComForPag.getRowCount()>0)
        ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).removeRow(0);

         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker("d/m/y");
         int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
         double dblPagos=0.00;
         double dblPago=0.00;
         int i=0;


        strSql="SELECT co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B') ";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()) intCodMotDoc = rstLoc.getInt(1);
        rstLoc.close();
        rstLoc=null;

        strSql="SELECT b.co_tipper,  b.tx_descor FROM tbm_emp AS a "+
        " LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper) WHERE a.co_emp="+objZafParSis.getCodigoEmpresa();
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()) intCodTipPerEmp = rstLoc.getInt("co_tipper");
        rstLoc.close();
        rstLoc=null;

          strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri "+
          " FROM tbm_polret as polret " +
          " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
          " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+
          " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" " +
          "  AND polret.st_reg='A'  AND  '"+objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

             ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).addRow(new java.util.Vector());
             pantallaPrincipal.tblOrdComForPag.setValueAt(new String("") ,pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 1);
             pantallaPrincipal.tblOrdComForPag.setValueAt(strFechaCal, pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 2);

             if(rstLoc.getString("tx_aplret").equals("S")){

              String fecha = objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd");
              java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
              java.util.Date fe2 = objUti.parseDate("2007/07/01","yyyy/MM/dd");

               if(fe1.before(fe2)) { // maneja el porsentaje anterior antes del 1 de julio del 2007
                    dblRetFue = objUti.redondeo( (   dblSub  * 0.01 ),2 );

                    pantallaPrincipal.tblOrdComForPag.setValueAt("1.000", pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                    pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetFue) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                    pantallaPrincipal.tblOrdComForPag.setValueAt("1" , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                    pantallaPrincipal.tblOrdComForPag.setValueAt(""  , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
               }else {
                   dblRetFue = objUti.redondeo( (  dblSub * ( rstLoc.getDouble("nd_porret")/100)),2 );

                   pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("nd_porret"), pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                   pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetFue) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                   pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("co_tipret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                   pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_codsri") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
               }
              }
              if(rstLoc.getString("tx_aplret").equals("I")){
                 dblRetIva = objUti.redondeo(  (  dlbIva * ( rstLoc.getDouble("nd_porret")/100)),2 );
                 pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("nd_porret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                 pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetIva) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                 pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("co_tipret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                 pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_codsri") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
              }

            pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_aplret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 8);
          }
          rstLoc.close();
          rstLoc=null;

         double dblRete = 0;
         dblRete = dblRetFueFle+dblRetFue+dblRetIva;
         dblMonCal = objUti.redondear( ( dblTotalDoc - dblRete ),intNumDec);

         for(i=0; i < intVal; i++) {
                diaCreAnt = diaCre;
                diaCre = intarreglodiaCom[i];
                numPag = intarreglonumCom[i];
                diaCreAcum = diaCre - diaCreAnt;
                if (diaCre!=0)
                  objFec.add(java.util.Calendar.DATE, diaCreAcum);

                dtePckPag.setAnio( objFec.get(java.util.Calendar.YEAR));
                dtePckPag.setMes( objFec.get(java.util.Calendar.MONTH)+1);
                dtePckPag.setDia(objFec.get(java.util.Calendar.DAY_OF_MONTH));
                double dblValPag =0;

               dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
               dblPago += dblPagos;
               dblPagos = objUti.redondear(dblPagos ,intNumDec);
               dblValPag = dblPagos;

               ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                intFilUltPag = pantallaPrincipal.tblOrdComForPag.getRowCount()-1;
                pantallaPrincipal.tblOrdComForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                pantallaPrincipal.tblOrdComForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                pantallaPrincipal.tblOrdComForPag.setValueAt(new Double(dblValPag), intFilUltPag, 4);
                pantallaPrincipal.tblOrdComForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
        }

      double dblultpag =  objUti.redondear(Double.parseDouble(pantallaPrincipal.tblOrdComForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
      dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);
      pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);


  String strApliRet="";
  for(int x=0; x<pantallaPrincipal.tblOrdComForPag.getRowCount(); x++){

     strApliRet=(pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8).toString();
     int FecPagDoc[] =  pantallaPrincipal._getFecha(pantallaPrincipal.tblOrdComForPag.getValueAt(x, 2).toString());
     String strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];

     strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " +
     " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
     " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+intCodTipDoc+","+
     intCodDoc+", "+(x+1)+", "+((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 1).equals(""))?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 1))+", "+
     "'"+strFechaPag+"', "+((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 3)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 3))+", "+
     ""+objUti.redondeo(Double.parseDouble( (pantallaPrincipal.tblOrdComForPag.getValueAt(x, 4)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 4).toString() ),6)+", "+
     ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 5)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 5).toString())+","+
     ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 6)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 6).toString())+",'"+
     ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 7)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 7).toString())+"','"+
     ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8).toString())+"',"+
     (strApliRet.equals("S")?dblSub:(strApliRet.equals("I")?dlbIva:0) )+",'I' )";
             //(strApliRet.equals("S")?dblSub:0)+",'I' )";
     stmLoc.executeUpdate(strSql);
  }
  stmLoc.close();
  stmLoc=null;
  blnRes=true;

 }}catch(java.sql.SQLException e) { blnRes=false;  mensaje2("_calculaPagosCom 1 ",  e );    }
   catch(Exception e){ blnRes=false;  mensaje2("_calculaPagosCom 2 ",  e );   }
 return blnRes;
}




public boolean _CalculoPagoVen(java.sql.Connection conPag){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="",strSql01="";
 try{
 if(conPag!=null){

    stmLoc=conPag.createStatement();

    strSql="SELECT A1.ne_numPag, A2.ne_diaCre , A2.st_sop " +
    " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
    " Where A1.co_forPag = 3 "+//  forma de pago contado
    " and A2.co_forPag = A1.co_forPag and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
    " and A2.co_emp = A1.co_emp order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

    strSql01="SELECT count(A2.ne_diaCre) as c " +
    " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
    " Where A1.co_forPag = 3 "+  // forma de pago contado
    " and A2.co_forPag = A1.co_forPag  and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
    " and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando
    rstLoc=stmLoc.executeQuery(strSql01);
    if(rstLoc.next()) intCanArr[0] = rstLoc.getInt(1);
    rstLoc.close();
    rstLoc=null;

    rstLoc=stmLoc.executeQuery(strSql);
    int x=0;
    while(rstLoc.next()){
      intarreglodia[x]=rstLoc.getInt(2);
      intarreglonum[x]=rstLoc.getInt(1);
      strarreglosop[x]=rstLoc.getString("st_sop");
      x++;
    }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;
  blnRes=true;
 }}catch(Exception e) { blnRes=false;  mensaje("_CalculoPagoVen "+ e.toString());    }
  return blnRes;
}

public boolean _calculaPagosVen(java.sql.Connection conn, double dblSub, double dlbIva, double dlbTot, int intCodTipDoc, int intCodDoc, String strCodTipPerCli, java.util.Date datFecDoc ){
 boolean blnRes=false;
 int intVal =  intCanArr[0];
 int intsizearre = intarreglodia.length;
 int intCodMotDoc=0;
 int intCodTipPerEmp=0;
 intVal= intsizearre - (intsizearre-intVal);
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 int intFilUltPag = 0;
 double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
 String strSql="";
 int intNumDec=2;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();


        java.util.Calendar calObj = java.util.Calendar.getInstance();
        calObj.setTime(datFecDoc);


        String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
        calObj.get(java.util.Calendar.MONTH)+1     ,
        calObj.get(java.util.Calendar.YEAR)  );


      java.util.Calendar objFec = java.util.Calendar.getInstance();
      int fecDoc [] = pantallaPrincipal._getFecha(strFechaCal);  // txtFecDoc.getFecha(txtFecDoc.getText());
      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
      }

       Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker("d/m/y");


       javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel());
        modTbl.setRowCount(0);

   dblBaseDePagos = dlbTot;
   if(dblBaseDePagos>0){

    strSql="SELECT co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B') ";
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()) intCodMotDoc = rstLoc.getInt(1);
    rstLoc.close();
    rstLoc=null;

    strSql="SELECT b.co_tipper,  b.tx_descor FROM tbm_emp AS a "+
    " LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper) WHERE a.co_emp="+objZafParSis.getCodigoEmpresa();
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()) intCodTipPerEmp = rstLoc.getInt("co_tipper");
    rstLoc.close();
    rstLoc=null;

    strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta"+
    " FROM tbm_polret AS polret left OUTER JOIN tbm_motdoc AS mot ON (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" +
    " WHERE polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret="+intCodTipPerEmp+" and co_ageret  = "+strCodTipPerCli+" "+
    " AND polret.st_reg='A'   AND  '"+objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd")+"' BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel()).addRow(new java.util.Vector());
      intFilUltPag = pantallaPrincipal.tblCotForPag.getRowCount()-1;
      pantallaPrincipal.tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
      pantallaPrincipal.tblCotForPag.setValueAt(strFechaCal, intFilUltPag, 2);

      if(rstLoc.getString("tx_aplret").equals("S")){
        dblRetFue =dblSub;
        dblRetFue = objUti.redondeo( (dblRetFue  * ( rstLoc.getDouble("nd_porret")/100)),2 );
        pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("nd_porret"), intFilUltPag, 3);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
      }
      if(rstLoc.getString("tx_aplret").equals("I")){
        dblRetIva = objUti.redondeo( (dlbIva * ( rstLoc.getDouble("nd_porret")/100)) ,2 );
        pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("nd_porret") , intFilUltPag, 3);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
      }
      pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("co_tipret") , intFilUltPag, 6);
    }
    rstLoc.close();
    rstLoc=null;
   }
  //*************************************************************************************///
    double dblPago=0.00;
    double dblPagos=0.00;
    int i=0;

    double dblRete=0;
    dblRete = dblRetFueFle+dblRetFue+dblRetIva;
    dblBaseDePagos = objUti.redondear( ( dlbTot - dblRete ), 2);

    for(i=0; i < intVal; i++) {
      java.util.Calendar objFecPagActual = objFec.getInstance();
      objFecPagActual.setTime(objFec.getTime());

      int diaCre = intarreglodia[i];
      int numPag = intarreglonum[i];
      String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
      if(diaCre!=0)
        objFecPagActual.add(java.util.Calendar.DATE, diaCre);

        dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

         System.out.print("-->"+ dtePckPag.getText() );

        ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel()).addRow(new java.util.Vector());
        intFilUltPag = pantallaPrincipal.tblCotForPag.getRowCount()-1;
        pantallaPrincipal.tblCotForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
        pantallaPrincipal.tblCotForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
        dblPago += dblPagos;
        dblPagos = objUti.redondear(dblPagos , intNumDec);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
        pantallaPrincipal.tblCotForPag.setValueAt(new Integer(0),  intFilUltPag, 5);

        pantallaPrincipal.tblCotForPag.setValueAt(strSop,  intFilUltPag, 7);
     }

     double dblultpag =  objUti.redondear(Double.parseDouble(pantallaPrincipal.tblCotForPag.getValueAt( intFilUltPag, 4).toString()), intNumDec);
     dblultpag = objUti.redondear(dblultpag + (dlbTot  - (dblPago + dblRete ) ), intNumDec);
     pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);


     for(int x=0; x<pantallaPrincipal.tblCotForPag.getRowCount(); x++){
       int FecPagDoc[] =  pantallaPrincipal._getFecha(pantallaPrincipal.tblCotForPag.getValueAt(x, 2).toString());
       String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
       String strSop = ((pantallaPrincipal.tblCotForPag.getValueAt(x, 7)==null)?"":pantallaPrincipal.tblCotForPag.getValueAt(x, 7).toString() );

       strSql="INSERT INTO  tbm_pagMovInv( co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
       " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet , st_regrep ,st_sop, co_tipret )" +
       " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+
       intCodTipDoc+", "+intCodDoc+", "+(x+1)+", "+
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 1)==null || pantallaPrincipal.tblCotForPag.getValueAt(x, 1).toString().equals(""))?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 1)) + ",'" +
       strFechaPag+"',"+
       (objUti.redondear(Double.parseDouble( (pantallaPrincipal.tblCotForPag.getValueAt(x, 4)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 4).toString() ),intNumDec) * -1 )+ ", " +
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 5)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 5).toString() ) + ", " +
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 3)==null || pantallaPrincipal.tblCotForPag.getValueAt(x, 3).toString().equals(""))?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 3).toString()) +
       " ,'I','"+strSop+"' ,"+Integer.parseInt(((pantallaPrincipal.tblCotForPag.getValueAt(x, 6)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 6).toString()))+")";
       stmLoc.executeUpdate(strSql);
     }
     stmLoc.close();
     stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException e) {  blnRes=false;   mensaje2("_calculaPagosVen 1  " , e );    }
    catch(Exception e){  blnRes=false;  mensaje2("_calculaPagosVen 2 ",  e );   }
  return  blnRes;
}



  private void mensaje2(String strMes, Exception excepcion){

      String strTit, strMsg, strFun="", strExc="";
      
       StackTraceElement[] objSte=excepcion.getStackTrace();
        for (int i=0;i<objSte.length;i++)
        {
            if ( (!(objSte[i].toString().startsWith("java"))) && (!(objSte[i].toString().startsWith("sun"))) && (!(objSte[i].toString().startsWith("org"))) && (!(objSte[i].toString().startsWith("net"))) )
                strFun+=objSte[i].toString() + "<BR>";
        }

        strExc=excepcion.toString();
        if (strExc.length()>120)
            strExc=strExc.substring(0,120) + "<BR>" + strExc.substring(120);
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>Ocurrió el siguiente error:";
        strMsg+="<BR><FONT COLOR=\"blue\">Clase:</FONT> " + (this.getClass().getName() );
        strMsg+="<BR><FONT COLOR=\"blue\">Función:</FONT> " + strFun;
        strMsg+="<FONT COLOR=\"blue\">Error:</FONT> " + strExc + "</HTML>";
        strMsg=strMsg.replaceAll("\n", "<BR>");

        almacenarArchHis(strNomArchHis, strMsg );
  }



public boolean _ejecutaSqlDatNueRem(java.sql.Connection conn, int intConRem ){
boolean blnRes=false;
  java.sql.Statement stmLoc;
  try{
   if(intConRem==1){
    if(conn!=null){
      stmLoc=conn.createStatement();

      stmLoc.execute(pantallaPrincipal.txtSql.getText());
      stmLoc.execute(pantallaPrincipal.txt.getText());
      stmLoc.execute(pantallaPrincipal.txtSqlUpTbl.getText());
      stmLoc.execute(pantallaPrincipal.txtSqlUptCC.getText());

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

    }
  }else blnRes=true;

  }catch(java.sql.SQLException e){ blnRes=false;  mensaje("_ejecutaSqlDatNueRem "+ e.toString());      }
    catch(Exception e)   { blnRes=false;  mensaje("_ejecutaSqlDatNueRem "+ e.toString());      }
 return blnRes;
}



public boolean _ejecutaSqlUpDel(java.sql.Connection conn){
boolean blnRes=false;
  java.sql.Statement stmLoc;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
//      System.out.println("\n\n\n\n_ejecutaSqlUpDel >>>> \n ");
//      System.out.println("1 >>>> \n " +  pantallaPrincipal.txt.getText());
      stmLoc.execute(pantallaPrincipal.txt.getText());
//            System.out.println("2 >>>> \n " +  pantallaPrincipal.txtSqlDia.getText());
      stmLoc.execute(pantallaPrincipal.txtSqlDia.getText());
//            System.out.println("3 >>>> \n " +  pantallaPrincipal.txtSqlUpTbl.getText());
      stmLoc.execute(pantallaPrincipal.txtSqlUpTbl.getText());


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false; mensaje("_ejecutaSqlUpDel "+ e.toString()); System.out.println("SQL ERROR " + e.toString());    }
    catch(Exception e)   { blnRes=false; mensaje("_ejecutaSqlUpDel "+ e.toString());    }
 return blnRes;
}




/**
 * Almacena un archivo de txt el proceso a ejecutarse.
 * @param strMenArchHis
 */
public void almacenarArchHis(String strNomArchHis, String strMenArchHis){
 try {

               java.util.Date objFec = new java.util.Date();
               java.util.Calendar fecha = java.util.Calendar.getInstance();
               int intHora = fecha.getTime().getHours();
               int intMin = fecha.getTime().getMinutes();
               int intSec = fecha.getTime().getSeconds();
               String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));


      java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(strNomArchHis, true));
      java.io.PrintWriter salida = new java.io.PrintWriter(bw);
      salida.println( strHora+" "+strMenArchHis );
      salida.close();
  }catch(java.io.IOException Ext ){ System.out.println(""+Ext ); }
}





public boolean procesarUnirDocTuv(String strFecDes, String strFecHas ){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stm1;
  String strSql1="", strSql2="";
  try{
    conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
    if(conn!= null){
      conn.setAutoCommit(false);

       stm1=conn.createStatement();

         strSql1=" select * from (	 " +
        " select *, (nd_tot-pag) as dif, case when co_cli in (1039) then  1 else 2 end as cliord from (	"+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc,  a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli,  a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a		"+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )	"+
        " where a.co_emp=1 and a.co_loc=5 and  a.co_tipdoc in (1,228,4 ) and a.co_cli  in (1039, 603 )   and a.fe_doc between 	"+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped		"+
        " order by  a.co_cli,   a.fe_doc, abs(a.nd_tot)		"+
        " ) as x 				"+
        " ) as x order by  cliord,  co_tipdoc,   fe_doc,   abs(nd_tot)		";


        strSql2=" select * from ( " +
        "  select *, (nd_tot-pag) from ( "+
        "  select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a "+
        "  inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "+
        "  where a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,3)  and a.co_cli= 3117  and a.fe_doc between "+
        "  date('"+strFecDes+"')+1  and	'"+strFecHas+"'	 "+
        "  group by a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped "+
        "  order by a.co_tipdoc, a.fe_doc, abs(a.nd_tot) "+
        "  ) as x "+
        "  union all "+
        "  select *, (nd_tot-pag) from ( "+
        "  select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a "+
        "  inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "+
        "  where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,3) and a.co_cli= 2854   and a.fe_doc between "+
        "  date('"+strFecDes+"')+1  and	'"+strFecHas+"'	 "+
        "  group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.co_doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped "+
        "  order by  a.co_tipdoc, a.fe_doc, abs(a.nd_tot) "+
        "  ) as x "+
        "  ) as x ";

        if(procesarVerificacionUnirDoc(conn, strSql1, strSql2)){
            if(generaSqlUniDoc()){

                 stm1.executeUpdate(pantallaPrincipal.txtSqlUniDoc.getText());
                 conn.commit();
                 pantallaPrincipal.objTblMod1.removeAllRows();
                 pantallaPrincipal.objTblMod2.removeAllRows();
                 pantallaPrincipal.txtSqlUniDoc.setText("");
                 blnRes=true;
                 System.out.println(" OK........... " );
            }else System.out.println(" error3.. " );
        }else System.out.println(" error1.. " );

        stm1.close();
        stm1=null;
        conn.close();
        conn=null;

   }}catch(java.sql.SQLException e){   mensaje("procesarUnirDocTuv "+ e.toString());         }
     catch(Exception e){  mensaje("procesarUnirDocTuv "+ e.toString());  }
   return blnRes;
  }

public boolean procesarUnirDocDim(String strFecDes, String strFecHas){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stm1;
  String strSql1="", strSql2="";
  try{
    conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
    if(conn!= null){
      conn.setAutoCommit(false);

       stm1=conn.createStatement();

        strSql1=" select * from ( "+
        " select *, (nd_tot-pag) as dif, case when co_cli in (3117) then  1 else 2 end as cliord from (		 "+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc,  a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli,  a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a	 "+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "+
        " where a.co_emp=4 and a.co_loc=2 and  a.co_tipdoc in (1,228,4 ) and a.co_cli  in (3117, 498 )   and a.fe_doc between  "+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped	 "+
        " order by  a.co_cli,   a.fe_doc, abs(a.nd_tot)	 "+
        " ) as x 				 "+
        " ) as x order by  cliord,  co_tipdoc,   fe_doc,   abs(nd_tot)  ";



        strSql2=" select * from (	 "+
        " select *, (nd_tot-pag) from (	 "+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a "+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "+
        " where a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,3)  and a.co_cli= 1039  and a.fe_doc between "+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped "+
        " order by a.co_tipdoc, a.fe_doc, abs(a.nd_tot)	 "+
        " ) as x "+
        " union all "+
        " select *, (nd_tot-pag) from ( "+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a "+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "+
        " where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,3) and a.co_cli= 789   and a.fe_doc between "+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.co_doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped "+
        " order by  a.co_tipdoc, a.fe_doc, abs(a.nd_tot) "+
        " ) as x "+
        " ) as x  ";

        if(procesarVerificacionUnirDoc(conn, strSql1, strSql2)){
            if(generaSqlUniDoc()){

                 stm1.executeUpdate(pantallaPrincipal.txtSqlUniDoc.getText());
                 conn.commit();
                 pantallaPrincipal.objTblMod1.removeAllRows();
                 pantallaPrincipal.objTblMod2.removeAllRows();
                 pantallaPrincipal.txtSqlUniDoc.setText("");
                 blnRes=true;
                 System.out.println(" OK............ " );
            }
        }

        stm1.close();
        stm1=null;
        conn.close();
        conn=null;

   }}catch(java.sql.SQLException e){  mensaje("IniPro "+ e.toString());         }
     catch(Exception e){  mensaje("IniPro "+ e.toString());  }
   return blnRes;
  }

public boolean procesarUnirDocCas(String strFecDes, String strFecHas){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stm1;
  String strSql1="", strSql2="";
  try{
    conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
    if(conn!= null){
      conn.setAutoCommit(false);

       stm1=conn.createStatement();

        strSql1=" select * from ( " +
        " select *, (nd_tot-pag) as dif, case when co_cli in (2854) then  1 else 2 end as cliord from (	"+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc,  a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli,  a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a		"+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )		"+
        " where a.co_emp=2 and a.co_loc=5 and  a.co_tipdoc in (1,228,4 ) and a.co_cli  in ( 2854, 789 )   and a.fe_doc between 	"+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped		"+
        " order by  a.co_cli,   a.fe_doc, abs(a.nd_tot)		"+
        " ) as x 					"+
        " ) as x order by  cliord,  co_tipdoc,   fe_doc,   abs(nd_tot)  ";

        strSql2=" select * from (	" +
        " select *, (nd_tot-pag) from (	"+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a	"+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )	"+
        " where a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,3)  and a.co_cli= 603  and a.fe_doc between  "+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped	"+
        " order by a.co_tipdoc, a.fe_doc, abs(a.nd_tot)	"+
        " ) as x 		"+
        " union all	"+
        " select *, (nd_tot-pag) from (	"+
        " select a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.co_tipdoc , a.ne_orddoc, a.tx_numped,  sum(a1.mo_pag) as pag from tbm_cabmovinv  as a "+
        " inner join  tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )	"+
        " where a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,3) and a.co_cli= 498   and a.fe_doc between "+
        " date('"+strFecDes+"')+1  and	'"+strFecHas+"'	"+
        " group by  a.co_emp,  a.co_loc,  a.co_tipdoc, a.co_doc, a.fe_Doc, a.co_doc, a.ne_numdoc, a.nd_tot, a.co_cli, a.tx_nomcli, a.ne_orddoc, a.tx_numped	"+
        " order by  a.co_tipdoc, a.fe_doc, abs(a.nd_tot)	"+
        " ) as x 	"+
        " ) as x ";

        if(procesarVerificacionUnirDoc(conn, strSql1, strSql2)){
            if(generaSqlUniDoc()){

                 stm1.executeUpdate(pantallaPrincipal.txtSqlUniDoc.getText());
                 conn.commit();
                 pantallaPrincipal.objTblMod1.removeAllRows();
                 pantallaPrincipal.objTblMod2.removeAllRows();
                 pantallaPrincipal.txtSqlUniDoc.setText("");
                 blnRes=true;
                 //System.out.println(" OK............ " );
            }
        }

        stm1.close();
        stm1=null;
        conn.close();
        conn=null;

   }}catch(java.sql.SQLException e){   mensaje("IniPro "+ e.toString());       }
     catch(Exception e){  mensaje("IniPro "+ e.toString());  }
   return blnRes;
  }



private boolean generaSqlUniDoc(){
   boolean blnRes=true;
   double intResul=0, intTot1=0, intTot2=0;
   String strSql="";
   try{

      for(int i=0; i < pantallaPrincipal.tblUno.getRowCount(); i++){

         intTot1 = Double.parseDouble(pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_NDTOT).toString());
         intTot2 = Double.parseDouble(pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_NDTOT).toString());
         intResul =  (intTot1 + intTot2);


         if( java.lang.Math.abs(intResul) > 1.00 ){
             blnRes=false;
             break;
         }else{

             strSql =" UPDATE tbm_cabmovinv SET tx_numped=LPAD( text("+pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_NUDOC).toString()+"),7,'0') WHERE "+
             " co_emp="+pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_COEMP)+" and co_loc="+pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_COLOC)+" and co_tipdoc="+pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_COTDO)+" " +
             " and co_doc="+pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_CODOC)+" ; "+
             " UPDATE tbm_cabmovinv SET tx_numped=LPAD( text("+pantallaPrincipal.tblUno.getValueAt(i, pantallaPrincipal.INT_TBL_NUDOC).toString()+"),7,'0') WHERE "+
             " co_emp="+pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_COEMP)+" and co_loc="+pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_COLOC)+" and co_tipdoc="+pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_COTDO)+" " +
             " and co_doc="+pantallaPrincipal.tblDos.getValueAt(i, pantallaPrincipal.INT_TBL_CODOC)+" ; ";
             pantallaPrincipal.txtSqlUniDoc.append(strSql+" \n");

         }
      }

  }catch(Exception e){ mensaje("IniPro "+ e.toString()); }
 return blnRes;
}

private boolean procesarVerificacionUnirDoc(java.sql.Connection conn, String strSql1, String strSql2){
  boolean blnRes=false;
  java.sql.Statement stm1, stm2;
  java.sql.ResultSet rst1, rst2;
  String strSqlAux1="", strSqlAux2="";
  int intNumSql1=0, intNumSql2=0;
  try{
    if(conn!= null){

        stm1=conn.createStatement();
        stm2=conn.createStatement();

        strSqlAux1 = " SELECT count(*) as cantidad FROM ( "+strSql1+" ) AS X ";
        strSqlAux2 = " SELECT count(*) as cantidad FROM ( "+strSql2+" ) AS X ";

        rst1 = stm1.executeQuery(strSqlAux1);
        rst2 = stm2.executeQuery(strSqlAux2);

        if(rst1.next())
           intNumSql1=rst1.getInt("cantidad");
        rst1.close();
        rst1=null;

        if(rst2.next())
           intNumSql2=rst2.getInt("cantidad");
        rst2.close();
        rst2=null;

        if(intNumSql1==intNumSql2){
            rst1 = stm1.executeQuery(strSql1);
            rst2 = stm2.executeQuery(strSql2);

            java.util.Vector vecData1 = new java.util.Vector();
            while(rst1.next()){
                  java.util.Vector vecReg = new java.util.Vector();
                  vecReg.add(pantallaPrincipal.INT_TBL_COEMP, rst1.getString("co_emp") );
                  vecReg.add(pantallaPrincipal.INT_TBL_COLOC, rst1.getString("co_loc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_COTDO, rst1.getString("co_tipdoc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_CODOC, rst1.getString("co_doc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_FEDOC, rst1.getString("fe_Doc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_NUDOC, rst1.getString("ne_numdoc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_NDTOT, rst1.getString("nd_tot") );
                  vecData1.add(vecReg);
            }
            pantallaPrincipal.objTblMod1.setData(vecData1);
            pantallaPrincipal.tblUno .setModel(pantallaPrincipal.objTblMod1);
            rst1.close();
            rst1=null;

            java.util.Vector vecData2 = new java.util.Vector();
            while(rst2.next()){
                  java.util.Vector vecReg = new java.util.Vector();
                  vecReg.add(pantallaPrincipal.INT_TBL_COEMP, rst2.getString("co_emp") );
                  vecReg.add(pantallaPrincipal.INT_TBL_COLOC, rst2.getString("co_loc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_COTDO, rst2.getString("co_tipdoc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_CODOC, rst2.getString("co_doc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_FEDOC, rst2.getString("fe_Doc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_NUDOC, rst2.getString("ne_numdoc") );
                  vecReg.add(pantallaPrincipal.INT_TBL_NDTOT, rst2.getString("nd_tot") );
                  vecData2.add(vecReg);
            }
            pantallaPrincipal.objTblMod2.setData(vecData2);
            pantallaPrincipal.tblDos .setModel(pantallaPrincipal.objTblMod2);
            rst2.close();
            rst2=null;
            blnRes=true;
        }

        stm1.close();
        stm1=null;
        stm2.close();
        stm2=null;

   }}catch(java.sql.SQLException e){    System.out.println( e.toString() );       }
     catch(Exception e){  System.out.println( e.toString() );    }
   return blnRes;
  }



/**
 * obtiene el correo electronico al cual tiene que ser enviado.
 * @return
 */
private boolean _getCorEleToSistemas(){
 boolean blnRes=false;
 java.sql.Connection  conn;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 String strSql="";
 try{
   conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
   if(conn != null ){
       stm=conn.createStatement();
 
      strSql="SELECT tx_coreleerr FROM tbm_sercliserloc WHERE co_emp="+objZafParSis.getCodigoEmpresaCorEle()+"  AND co_loc="+objZafParSis.getCodigoLocalCorEle()+" ";
      rst=stm.executeQuery(strSql);
      if(rst.next()){
//          strCorEleTo=rst.getString("tx_coreleerr");
          strCorEleTo="sistemas6@tuvalsa.com";
      }
      rst.close();
      rst=null;
      stm.close();
      stm=null;
   conn.close();
   conn=null;
 }}catch(java.sql.SQLException e)  {  blnRes=false;  System.out.println(""+e );    }
   catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt ); }
  return blnRes;
}




public void enviarCorreo(String strMsExcp){
  try
    {
        String server = "mail.tuvalsa.com";
        String userName = "zafiro";
        String password = "#tuv*sis28/10=";
        String fromAddres = "zafiro@tuvalsa.com";
        //        String toAddres = strCorEleTo;
        String toAddres = "sistemas6@tuvalsa.com";

        String cc = "";
        String bcc = "";
        boolean htmlFormat = false;
        String subject = "SafSer02";
        String body = strMsExcp;

        sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,
                     htmlFormat, subject, body);


        }
        catch (Exception e) {  e.printStackTrace();    }


      }



    public void sendMail(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String mensajeBady)
    {

        Properties properties = System.getProperties();
        properties.put("mail.smtps.host", server);
        properties.put("mail.smtps.auth", "true");
        Session ses  = Session.getInstance(properties);

        ses.setDebug(true);

        try{

            MimeMessage msg = new MimeMessage(ses);

            msg.setFrom(new InternetAddress(fromAddress));

            if (toAddress != null)
            {
               msg.addRecipients(Message.RecipientType.TO, toAddress);
            }


             msg.setSubject(subject);

             MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
             cuerpoCorreo.setText(mensajeBady);

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            msg.setContent(multipart);

            msg.saveChanges();

            Transport tr = ses.getTransport("smtps");
            tr.connect(server,userName, password);
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        }

        catch(MessagingException e)
        {
            e.printStackTrace();
        }



    }



 public boolean sendMailTuv(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String body)
   {
    boolean blnRes=false;
        try{

           Properties props = System.getProperties();
           props.put("mail.smtp.host", server);

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to address
            message.addRecipient(Message.RecipientType.TO,
            new InternetAddress(toAddress));

            // Set the subject
            message.setSubject(subject);


                MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
                //cuerpoCorreo.setText("Estado de Cuenta ...");
                 cuerpoCorreo.setContent(body, "text/html");


                //java.io.File archivo = new java.io.File("C://Estado_Cuenta.pdf");

               // MimeBodyPart  adjuntoCorreo = new MimeBodyPart();
               // adjuntoCorreo.attachFile(archivo);

                    MimeMultipart multipart= new MimeMultipart();
                    multipart.addBodyPart(cuerpoCorreo);
                   // multipart.addBodyPart(adjuntoCorreo);

                    message.setContent(multipart);

                    message.saveChanges();


            // Set the content
           // message.setText("Welcome");

            // Send message
            Transport.send(message);

           blnRes=true;

       }catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }

      return blnRes;
    }



static class MyAuthenticator extends Authenticator {
 PasswordAuthentication l = new PasswordAuthentication("sistemas6", "jota1316");
 @Override
 protected PasswordAuthentication getPasswordAuthentication() {
 return l;
 }
}


}
