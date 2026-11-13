/*
 * To change this template, choose Tools | Templates  st_bie
 * and open the template in the editor.
 */

package Proceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


/**
 *
 * @author jayapata
 */
public class arrancaProceso extends Thread {

    private IniciarProceso pantalla;
    String strNomArchHis="";
    String strDirSis="";
    procesoClass objClass;
       
    public arrancaProceso(IniciarProceso pantalla, String strNomArchHis, String strDirSis ) {
      try{
      this.pantalla = pantalla;
      this.strNomArchHis = strNomArchHis;
      this.strDirSis = strDirSis;
      objClass = new procesoClass(pantalla.objZafParSis, null, strNomArchHis  );
     }catch(Exception e){  mensaje("Error ! arrancaProceso "+e.toString() ); }
    }

    
    @Override
    public void run() {
      try{

        iniProCosenco();
                        
                       
      }catch(Exception e){  mensaje("Error ! verifique datos "+e.toString() ); }
    }


   
    private void iniProCosenco(){
        try{
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");

          iniciarProcesoGenerarDatos();

        }catch(Exception e){  mensaje("Error ! iniProCosenco "+e.toString() ); }
    }



private boolean iniciarProcesoGenerarDatos(){
 boolean blnRes=false;
 Connection  connLoc, connCos;
 Statement stmLoc;
 String strSql="";
 try{
   connLoc =  DriverManager.getConnection(pantalla.objZafParSis.getStringConexion(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
   connCos =  DriverManager.getConnection(pantalla.objZafParSis.getUsaStringConexionCosenco(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );

   if(connLoc != null ){
     if(connCos != null ){

         stmLoc=connLoc.createStatement();

       if(consultarDocResProFac(connLoc, connCos )){
        //if(consultarDocResProDev(connLoc, connCos )){  // SE QUITA POR QUE NO DEDBE GENERAR DEVOLUCION YA QUE ES SERVICIO ES IRREVERSIBLE. 

           strSql="UPDATE tbm_cabmovinv SET st_itmserpro=null WHERE co_tipdoc=3 and  st_itmserpro='S' ";
           stmLoc.executeUpdate(strSql);

       }//}

       stmLoc.close();
       stmLoc=null;

     

   connLoc.close();
   connLoc=null;
   connCos.close();
   connCos=null;

 }}}catch(SQLException e)  {  blnRes=false;  System.out.println(""+e );   objClass.enviarCorreo(" "+e.toString() );  }
   catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt );     objClass.enviarCorreo(" "+Evt.toString() ); }
  return blnRes;
}


private boolean consultarDocResProFac(Connection conn, Connection connCos ){
 boolean blnRes=true;
 Statement stmLoc, stmLoc01;
 ResultSet rstLoc;
 String strSql="";
 int intCodEmpCos=1;
 try{
   if(conn != null ){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

      /*strSql=" select *, ( abs(canven) - case when  candev is null then 0 else abs(candev) end ) as dif  from ( "
      + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.canven) as canven, sum(a2.candev)  as candev "
      + " from ( "
      + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_can as canven  "
      + " from tbm_cabmovinv as a "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
      + " inner join tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) "
      + " where a.co_tipdoc=1 and a.st_reg='A' and a.ne_numdoc > 0  and a.st_itmserpro='S' and a2.st_ser='S' "
      + " and upper(trim(substr(a1.tx_codalt, length(a1.tx_codalt) ,1))) <> 'P' " 
      + " ) as a1  "
      + " left join ( "
      + " select ( a1.nd_can - a2.nd_canvolfac) as candev, a.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel  "
      + "  ,a.co_loc, a.co_tipdoc, a.co_doc  "
      + "  from tbm_cabmovinv as a    "
      + "  inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
      + "  inner join tbm_inv as a3 on (a3.co_emp=a1.co_emp and a3.co_itm=a1.co_itm )   "
      + "  inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven )  "
      + "  where a.co_tipdoc=3 and a.st_reg='A' and a.st_itmserpro='S'  and a3.st_ser='S'  "
      + "  ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg )  "
      + "  group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  "
      + " ) as x  "; */

      strSql=" SELECT *, ( abs(canven) - case when  candev is null then 0 else abs(candev) end ) as dif ";
      strSql+=" FROM ( ";
      strSql+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.canven) as canven, sum(a2.candev)  as candev, a1.ne_secemp, a1.ne_secgrp ";
      strSql+="     FROM ( ";
      strSql+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_can as canven, ne_secemp, ne_secgrp  ";
      strSql+="             FROM tbm_cabmovinv as a ";
      strSql+="             INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  ";
      strSql+="             INNER JOIN tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) ";
      strSql+="             WHERE a.co_tipdoc IN (1,228) and a.st_reg='A' and a.ne_numdoc > 0  and a.st_itmserpro='S' and a2.st_ser='S' ";
      strSql+="                   and upper(trim(substr(a1.tx_codalt, length(a1.tx_codalt) ,1))) <> 'P' " ;
      strSql+="     ) as a1  ";
      strSql+=" LEFT JOIN ( ";
      strSql+="     SELECT ( a1.nd_can - a2.nd_canvolfac) as candev, a.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel, a.co_loc, a.co_tipdoc, a.co_doc ";
      strSql+="     FROM tbm_cabmovinv as a    ";
      strSql+="     INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  ";
      strSql+="     INNER JOIN tbm_inv as a3 on (a3.co_emp=a1.co_emp and a3.co_itm=a1.co_itm )   ";
      strSql+="     INNER JOIN tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven )  ";
      strSql+="     WHERE a.co_tipdoc=3 and a.st_reg='A' and a.st_itmserpro='S'  and a3.st_ser='S'  ";
      strSql+=" ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg )  ";
      strSql+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_secemp, a1.ne_secgrp ";
      strSql+=" ) as x  ";
      
      System.out.println("JOTA - arrancaProceso.consultarDocResProFac: " + strSql);

       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){
            if(rstLoc.getDouble("dif") > 0 ){
                if(objClass._getVerificaExisItmCos(conn, connCos, intCodEmpCos,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc")  )){
                         //if(_generarFacVenFacCom( rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), intCodEmpCos )){
                         if(_generarFacVenFacCom( rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), intCodEmpCos, rstLoc.getInt("ne_secemp"), rstLoc.getInt("ne_secgrp") )){
                             blnRes=true;
                         }
                         else {  
                             blnRes=false;  
                             break; 
                         }
                     }
                     else {  
                         blnRes=false;  
                         break; 
                     }
                }
                else
                {
                   if(objClass._cambiarEstNoProCos(conn,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") ) ){
                           blnRes=true;
                   }else {  
                       blnRes=false;  
                       break; 
                   }
                }
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;

 }}catch(SQLException e)  {  blnRes=false;  System.out.println(""+e );   objClass.enviarCorreo(" "+e.toString() );  }
   catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt );     objClass.enviarCorreo(" "+Evt.toString() ); }
  return blnRes;
}



private boolean consultarDocResProDev(java.sql.Connection conn, java.sql.Connection connCos ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
   if(conn != null ){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

       
       strSql=" select * from ( "
       + "  select  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.co_emprel, a5.co_locrel, a5.co_tipdocrel, a5.co_docrel "
       + "  from tbm_cabmovinv as a "
       + "  inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
       + " inner join tbm_inv as ser on (ser.co_emp=a1.co_emp and ser.co_itm=a1.co_itm ) "
       + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
       + "  inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )  "
       + "  inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc )  "
       + "  inner join tbr_detmovinv as a5 on (a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=a3.co_reg  "
       + "  and  a5.co_tipdocrel=2 )   "
       + "  where a.co_tipdoc=3 and a.st_reg='A' and a.st_itmserpro='S'  and  a4.st_itmserpro = 'P' and ser.st_ser='S' "
       + " ) as x  group by co_emp, co_loc, co_tipdoc, co_doc, co_emprel, co_locrel, co_tipdocrel, co_docrel  ";

       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){
          
              if(_generarDevVenDevCom( rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel")  )){
                    blnRes=true;
              }else{
                  blnRes=false;
                  break;
              }
            
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;

 }}catch(java.sql.SQLException e)  {  blnRes=false;  System.out.println(""+e );   objClass.enviarCorreo(" "+e.toString() );  }
   catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt );     objClass.enviarCorreo(" "+Evt.toString() ); }
  return blnRes;
}



private boolean _generarDevVenDevCom( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpOc, int intCodLocOc, int intCodTipDocOc, int intCodDocOc  ){
 boolean blnRes=false;
 java.sql.Connection connCos;
 java.sql.Connection connEmp;

 int intCodEmpCos=1;
 int intCodLocCos=2;
 int intCodTipDocCos=3; // DevVen
 int intCodTipDocComEmp=4;  // DevCom
 boolean blnEstSiReaCruAut=false; // variable que permite saber si realiza cruce auto o no

 try{
    connCos =  java.sql.DriverManager.getConnection(pantalla.objZafParSis.getUsaStringConexionCosenco(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
    connEmp =  java.sql.DriverManager.getConnection(pantalla.objZafParSis.getStringConexion(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
   if(connCos != null ){
    if(connEmp != null ){
       connCos.setAutoCommit(false);
       connEmp.setAutoCommit(false);

       CrearDevCom objDevCom = new CrearDevCom(pantalla.objZafParSis, pantalla);
       CrearDevVen objDevVen = new CrearDevVen(pantalla.objZafParSis, pantalla );

  

        blnEstSiReaCruAut=objClass._getSiHaceCruceAuto(connEmp, connCos,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc ) ;

        System.out.println("Realiza Cruce automatico---->  "+blnEstSiReaCruAut);


        if(objDevCom._generaDevCom(connEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpOc, intCodLocOc, intCodTipDocOc, intCodDocOc, intCodTipDocComEmp, blnEstSiReaCruAut, objClass.dblValFacCom ) ){

          if(objDevVen._generaDevVen(connEmp, connCos, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCos, intCodLocCos, intCodTipDocCos, blnEstSiReaCruAut, objClass.dblValFacVen  ) ){

           if(objClass._cambiarEstProDev(connEmp,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc ) ){

//                blnRes=false;
//                connCos.rollback();
//                connEmp.rollback();

                blnRes=true;
                connCos.commit();
                connEmp.commit();

               objClass.mensaje("Ingreso de DevVen DevCom.............OK..");

          }else{  blnRes=false; connCos.rollback();   connEmp.rollback();   System.out.print("Error 1...........OK.."); }
        }else{  blnRes=false; connCos.rollback();   connEmp.rollback();   System.out.print("Error 2...........OK.."); }
        }else {  blnRes=false; connCos.rollback();   connEmp.rollback();   System.out.print("Error 3...........OK.."); }
        

       objDevCom=null;
       objDevVen=null;
       connCos.close();
       connCos=null;
       connEmp.close();
       connEmp=null;

 }}}catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt );     objClass.enviarCorreo(" "+Evt.toString() ); }
  return blnRes;
}




/**
 * Crea factura de venta en cosenco
 * @param conn
 * @param intCodEmpCos
 * @param intCodLocCos
 * @param intCodTipDocCos
 * @param intCodCliPrvCos
 * @return
 */
//private boolean _generarFacVenFacCom( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCos  ){
private boolean _generarFacVenFacCom( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCos, int intSecEmp, int intSecGrp ){
 boolean blnRes=false;
 Connection connCos;
 Connection connEmp;

 int intCodCliPrvCos=0;
 int intCodCliPrvEmp=0;
 int intCodLocCos=2;
 int intCodTipDocCos=1; // facven
 int intCodTipDocComEmp=2;  // faccom

 String strSqlCliCos="";

 try{
    connCos = DriverManager.getConnection(pantalla.objZafParSis.getUsaStringConexionCosenco(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
    connEmp = DriverManager.getConnection(pantalla.objZafParSis.getStringConexion(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
   if(connCos != null ){
    if(connEmp != null ){
       connCos.setAutoCommit(false);
       connEmp.setAutoCommit(false);

        if(intCodEmp==1 ){
           intCodCliPrvCos=3516;     // CLIENTE TUVAL EN COSENCO
           intCodCliPrvEmp=3938;  // CLIENTE COSENCO EN TUVAL
           strSqlCliCos="SELECT co_cli, co_tipper, tx_nom, tx_dir, tx_ide, tx_tel  FROM tbm_cli WHERE co_emp="+intCodEmpCos+" AND co_cli="+intCodCliPrvCos;
        }else if(intCodEmp==2 ){
           intCodCliPrvCos=602;     // CLIENTE CASTEK EN COSENCO
           intCodCliPrvEmp=3080;  // CLIENTE COSENCO EN castek
           if(intCodLoc==1){  // 2;1;"CASTEK - Quito (002-001)"
            strSqlCliCos="SELECT co_cli, co_tipper, tx_nom, tx_dir, tx_ide, tx_tel  "
                    + "FROM tbm_cli "
                    + "WHERE co_emp="+intCodEmpCos+" AND co_cli="+intCodCliPrvCos;
           }
           else if (intCodLoc == 4){ /// "CASTEK - Manta (005-001)"
            strSqlCliCos="SELECT a.co_cli, a.co_tipper, a.tx_nom, a1.tx_dir, a.tx_ide, a1.tx_tel1 as tx_tel "
             + "  FROM tbm_cli as  a"
             + "  inner join tbm_dircli as a1 on(a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
             + " WHERE a.co_emp="+intCodEmpCos+" AND a.co_cli="+intCodCliPrvCos+" and  a1.co_reg=1 ";
           }
           else if (intCodLoc == 6){  // "CASTEK - Santo Domingo (006-001)"
            strSqlCliCos="SELECT a.co_cli, a.co_tipper, a.tx_nom, a1.tx_dir, a.tx_ide, a1.tx_tel1 as tx_tel "
             + "  FROM tbm_cli as  a"
             + "  inner join tbm_dircli as a1 on(a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
             + " WHERE a.co_emp="+intCodEmpCos+" AND a.co_cli="+intCodCliPrvCos+" and  a1.co_reg=2 ";
           }
           else if(intCodLoc == 10){  /// "CASTEK - Cuenca (001-001)"
             strSqlCliCos="SELECT a.co_cli, a.co_tipper, a.tx_nom, a1.tx_dir, a.tx_ide, a1.tx_tel1 as tx_tel "
             + "  FROM tbm_cli as  a"
             + "  inner join tbm_dircli as a1 on(a1.co_emp=a.co_emp and a1.co_cli=a.co_cli ) "
             + " WHERE a.co_emp="+intCodEmpCos+" AND a.co_cli="+intCodCliPrvCos+" and  a1.co_reg=1 ";  /* Jota */
           }
         }else if(intCodEmp==4 ){
           intCodCliPrvCos=1039;     // CLIENTE DIMULTI EN COSENCO
           intCodCliPrvEmp=3370;  // CLIENTE COSENCO EN dimulti
           strSqlCliCos="SELECT co_cli, co_tipper, tx_nom, tx_dir, tx_ide, tx_tel  FROM tbm_cli WHERE co_emp="+intCodEmpCos+" AND co_cli="+intCodCliPrvCos;
        }

       CrearFacVen objFac = new CrearFacVen(pantalla.objZafParSis, pantalla);
       CrearFacCom objCom = new CrearFacCom(pantalla.objZafParSis, pantalla );

        if(objFac._generaFacVen(connEmp, connCos,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc , intCodEmpCos, intCodLocCos, intCodTipDocCos, intCodCliPrvCos, strSqlCliCos ) ){

          //if(objCom._generaFacCom(connEmp,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodTipDocComEmp, intCodCliPrvEmp,  intCodEmpCos, intCodLocCos, intCodTipDocCos, objFac.intCodDocCosGlo ) ){
          if(objCom._generaFacCom(connEmp,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodTipDocComEmp, intCodCliPrvEmp,  intCodEmpCos, intCodLocCos, intCodTipDocCos, objFac.intCodDocCosGlo, intSecEmp, intSecGrp ) ){

           if(objClass._cambiarEstProCos(connEmp,  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc ) ){


                blnRes=true;
                connCos.commit();
                connEmp.commit();



            System.out.print("Ingreso de facven faccom.............OK.." + Calendar.getInstance().getTime() );
            mensaje("Proceso finalizo con exito.");
          }else{
            blnRes=false;
            connCos.rollback();
            connEmp.rollback();
             System.out.print("Error 3...........OK..");
        }

        }else{
            blnRes=false;
            connCos.rollback();
            connEmp.rollback();
             System.out.print("Error 2...........OK..");
        }


        }else{
            blnRes=false;
            connCos.rollback();
            connEmp.rollback();
             System.out.print("Error 1...........OK..");
        }




       objFac=null;
       objCom=null;

       connCos.close();
       connCos=null;
       connEmp.close();
       connEmp=null;

 }}}catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt );     objClass.enviarCorreo(" "+Evt.toString() ); }
  return blnRes;
}

  
    

    private void mensaje(String strMes){
        //javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
       // obj.showMessageDialog( null, strMes, "Mensaje Zafiro..", javax.swing.JOptionPane.INFORMATION_MESSAGE );
         objClass.almacenarArchHis(strMes );
    }



}
