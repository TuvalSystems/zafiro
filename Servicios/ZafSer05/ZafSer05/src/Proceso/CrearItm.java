/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;

    
/**
 *
 * @author jayapata
 */
public class CrearItm {

 Librerias.ZafParSis.ZafParSis objZafParSis;
 Librerias.ZafUtil.ZafUtil objUti;
 procesoClass objClass;

  public CrearItm(Librerias.ZafParSis.ZafParSis obj ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        objClass = new procesoClass(objZafParSis, null,  "" );

      }catch (CloneNotSupportedException e){  System.out.println("Error 2 "+e.toString() );  }
  }



  
  
public boolean crearItmCos(java.sql.Connection conn, int intCodEmp, int intCodItm ){
 boolean blnRes=false;
 java.sql.Connection connCos;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intCodEqu=0;
 try{
   connCos =  java.sql.DriverManager.getConnection(objZafParSis.getUsaStringConexionCosenco(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
  if(connCos != null ){
   if(conn != null ){
       connCos.setAutoCommit(false);

       stmLoc=conn.createStatement();
      
       strSql="SELECT co_itm, tx_codalt, tx_nomitm, st_ser, st_ivacom, st_ivaven, co_uni  FROM tbm_inv WHERE co_emp="+intCodEmp+" and co_itm="+intCodItm+" ";
//       System.out.println("COCENCO - crearItmCos: " + strSql);
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
           
         intCodEqu=getCodItemMaestro(connCos);

          if(_CrearItemEmpresas(connCos, intCodEqu, rstLoc.getString("st_ser"), rstLoc.getString("st_ivacom"), rstLoc.getString("st_ivaven"), rstLoc.getString("tx_codalt"), rstLoc.getString("tx_nomitm"), rstLoc.getString("co_uni") ) ){
              connCos.commit();
              blnRes=true;
          }else connCos.rollback();

       }
       rstLoc.close();
       rstLoc=null;

       stmLoc.close();
       stmLoc=null;
      
 }}}catch(java.sql.SQLException e)  {  blnRes=false;  objClass.mensaje("_generaFacVen "+ e.toString());       }
   catch(Exception e)  { blnRes=false;   objClass.mensaje("_generaFacVen "+ e.toString());     }
  return blnRes;
}


/* CREAR ITEM COCENCO  */
private boolean _CrearItemEmpresas(java.sql.Connection conn,int intCodEqu, String strServi, String strIvaCom, String strIvaVen, String strAuxCodAlt, String strNomItm, String strCodUni ){
 boolean blnRes=false;
 java.sql.Statement stmInv,stmEmp;
 java.sql.ResultSet rstLoc,rstEmp;
 int intCodItm=0;
 int intControl=0;
 String strCamPreVta="N", strCamPreCom="N";
 String strSql="";
 try{
    stmEmp = conn.createStatement();
    stmInv = conn.createStatement();


    strSql="SELECT co_emp FROM tbm_emp ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

       strSql="select case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp="+rstEmp.getString("co_emp");
//       System.out.println("COCENCO + _CrearItemEmpresas " + strSql);
       rstLoc=stmInv.executeQuery(strSql);
       if(rstLoc.next())
          intCodItm = rstLoc.getInt("co_itm");
       rstLoc.close();
       rstLoc=null;

        strAuxCodAlt=strAuxCodAlt.replaceAll("'", "''");
        strNomItm=strNomItm.replaceAll("'", "''");


        strSql="INSERT INTO tbm_inv ( co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_uni "+
        " ,nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax "+
        " ,st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng, fe_ing, nd_pesitmkgr, st_permodnomitmven, " +
        " st_permodnomitmcom, st_blqPreVta ) "+
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodItm+", "
        + " "+(strAuxCodAlt.equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAuxCodAlt.toUpperCase()+"'")+", "+
        "   "+(strAuxCodAlt.equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAuxCodAlt.toUpperCase()+"'")+", "+
        "  "+(strNomItm.equals("")?"Null":"'"+strNomItm+"'")+", "+
        "  "+((strCodUni==null)?null:(strCodUni.equals("")?null:strCodUni))+", "+
        "  null, '"+strServi+"', 0 ,0, 0,0,0 , 0, null, null, "+
        " '"+strIvaCom+"', '"+strIvaVen+"', "+
        "  '', '', 'A', 1, "+objZafParSis.getFuncionFechaHoraBaseDatos()+",  "+
        "  0, '"+strCamPreVta+"', '"+strCamPreCom+"', null ) ";
//        System.out.println("COCENCO - _CrearItemEmpresas - " + strSql);
        stmInv.executeUpdate(strSql);
        
        strSql="INSERT INTO tbm_equinv (co_itmmae, co_emp, co_itm) values ("+intCodEqu +", "+rstEmp.getInt("co_emp") +","+intCodItm+")";
//        System.out.println("COCENCO - _CrearItemEmpresas - 2 - " + strSql);
        stmInv.executeUpdate(strSql);

        strSql="SELECT co_bod FROM tbm_bod WHERE co_emp="+rstEmp.getInt("co_emp")+" ORDER BY co_bod ";
//        System.out.println("COCENCO - _CrearItemEmpresas - 3 " + strSql);
        rstLoc=stmInv.executeQuery(strSql);
        while(rstLoc.next()){

            if(!creaItemBodega(conn, rstEmp.getInt("co_emp"), intCodItm,  rstLoc.getInt("co_bod") )){
                intControl=1;
                break;
            }
        }
        rstLoc.close();
        rstLoc=null;
     
    }
    rstEmp.close();
    rstEmp=null;

    stmInv.close();
    stmInv=null;
    stmEmp.close();
    stmEmp=null;
    
    if(intControl==0) blnRes=true;

  }catch(java.sql.SQLException e){ blnRes=false; objClass.mensaje("_generaFacVen "+ e.toString());   }
   catch(Exception e){  blnRes=false;  objClass.mensaje("_generaFacVen "+ e.toString());   }
  return blnRes;
}


private boolean creaItemBodega(java.sql.Connection conn,int intCodEmp,int intCodItm, int intCodBod){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   try{
      stmLoc=conn.createStatement();
      strSql="INSERT INTO tbm_invbod(co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
      " VALUES("+intCodEmp+", "+intCodBod+", "+intCodItm+ ", 0, 0, 0)";
//      System.out.println("COCENCO - creaItemBodega - " + strSql);
      stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

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





private int getCodItemMaestro(java.sql.Connection conn){
 int intRes = 0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{
  if(conn!=null){
    stmLoc = conn.createStatement();
    strSQL="SELECT  CASE WHEN MAX(co_itmmae) is null THEN 1 else MAX(co_itmmae)+1 END AS co_itmmae FROM tbm_equinv";
    rstLoc=stmLoc.executeQuery(strSQL);
    if(rstLoc.next()){
      intRes = rstLoc.getInt("co_itmmae");
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch (java.sql.SQLException e){  objClass.mensaje("_generaFacVen "+ e.toString());   }
   catch(Exception e){  objClass.mensaje("_generaFacVen "+ e.toString());    }
 return intRes;
}








}

