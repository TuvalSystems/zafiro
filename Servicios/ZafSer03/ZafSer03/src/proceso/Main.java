/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package proceso;
     
import Librerias.ZafParSis.ZafParSis;
import java.util.Date;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

/**
 *    
 * @author jayapata
 */
public class Main 
{
    private ZafParSis objParSis;
    private ZafThreadExeProFecHor objThrExeProFecHor;
    private ZafUtil objUti;

    int intHorEjejePro=0;
    int intAnioEje=0;
    boolean blnEstEjepro=false;

    private String strCorEleTo="sistemas3@tuvalsa.com; sistemas9@tuvalsa.com";
    private String strNomComEjeSis="";
    private String strDirIpEjeSis="";

    public Main()
    {
        try
        {
             System.out.println("ZafSer03 v0.7");
            objParSis=new ZafParSis();
            objUti = new ZafUtil();

            cargarDrv_F1(objParSis.getDriverConexion());

            java.net.InetAddress objIneAdd;
            objIneAdd=java.net.InetAddress.getLocalHost();

            strNomComEjeSis=objIneAdd.getHostName();
            strDirIpEjeSis=objIneAdd.getHostAddress();

            intHorEjejePro= objParSis.getHoraEjeProceso();

            EjecutarProcedoFechHoraDet();

        }
        catch (java.net.UnknownHostException e){}
        catch (Exception e){}
    }

    private void EjecutarProcedoFechHoraDet(){
         objThrExeProFecHor=new ZafThreadExeProFecHor();
         objThrExeProFecHor.start();
    }
        

    private class ZafThreadExeProFecHor extends Thread
    {
        java.util.Date datFecSis, datFecExe;
        public ZafThreadExeProFecHor()
        {

        }
        public void run()
        {
            while (true){
                datFecSis=new java.util.Date();

                java.text.SimpleDateFormat objSimDatFor;
                objSimDatFor=new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                System.out.println("Inicia: " + objSimDatFor.format(datFecSis));
                System.out.println("Hora de ejecucion del proceso: "+intHorEjejePro);

                if(intHorEjejePro==datFecSis.getHours()){
                  if(!blnEstEjepro){
                        intAnioEje =(datFecSis.getYear() + 1900);
                        if( realizaMigraDatoRep(intAnioEje) ){
                            if(cargarDatosTbm_VenMenInvBod()){
                                if(actualizaDatosInventario()){
                                    blnEstEjepro=true;
                                    System.out.println("Se ejecuto!!!!");
                                    datFecSis=null;
                                    datFecSis=new java.util.Date();
                                    enviarCorreo(" "+  strNomComEjeSis + " (" + strDirIpEjeSis + ")  Se ejecuto el proceso bien......" + objSimDatFor.format(datFecSis) );
                                }
                                else
                                    enviarCorreo(" "+  strNomComEjeSis + " (" + strDirIpEjeSis + ")  Error en el proceso ACTUALIZA STOCK DE ACUERDO AL KARDEX fallo ......"+objSimDatFor.format(datFecSis) );
                            }
                            else  
                                enviarCorreo(" "+  strNomComEjeSis + " (" + strDirIpEjeSis + ")  Error en el proceso ACTUALIZA GRUPO fallo ......"+objSimDatFor.format(datFecSis) );
                        }
                        else  
                            enviarCorreo(" "+  strNomComEjeSis + " (" + strDirIpEjeSis + ")  Error en el proceso ACTUALIZA EMPRESA fallo  ......"+objSimDatFor.format(datFecSis) );
                    }
                }
                else{
                    blnEstEjepro=false;
                }
                objSimDatFor=null;
                datFecSis=null;
                try
                {
                  sleep(600000);//60*10000  10 minutos
                }
                catch (InterruptedException e) {   System.out.println("Excepción: " + e.toString());   }
            }
        }
    }


    private boolean realizaMigraDatoRep(int intAnio )
    {
        System.out.println("realizaMigraDatoRep");
        boolean blnRes=false;
        try{
            java.sql.Connection conn=null;
            java.sql.Statement stmLoc;
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                String sql="";
                sql=" delete from tbm_venMenInvBod where ne_Ani="+intAnio+" ; " +
                    " insert into tbm_venMenInvBod  " +
                    "" +
                    " select  co_emp, co_bod, co_itm, anio,  mes, sum(nd_uniVen) ,  sum(num_veces)  , 0 ,  sum(total_vtas_netas),  sum(costo_total)  from (  " +
                    " select co_itmMae, co_itm,  tx_codalt,  x1.co_emp,  x1.co_bod, x1.anio,  x1.mes,  nd_uniVen , total_vtas_netas , num_veces, costo_total  from ( " +
                    " select * from tbr_bodempbodgrp as a   " +
                    " where   a.co_empgrp=0  " +
                    " ) as  x    inner join (  " +
                    " SELECT " +
                    " c.co_itmMae, c1.co_itm,  b.tx_codalt, a.co_emp, b.co_bod, extract(year from a.fe_doc) as anio, extract(month from a.fe_doc) as mes ,   -SUM(b.nd_can) AS nd_uniVen   " +
                    " ,sum(abs(b.nd_tot)) as total_vtas_netas  , count(a.co_doc) as num_veces, sum(abs(b.nd_costot))  as costo_total   " +
                    " FROM tbm_cabmovinv as a  " +
                    " INNER JOIN tbm_detmovinv as b ON (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc)  " +
                    " INNER JOIN tbm_equInv AS c ON (a.co_emp=c.co_emp AND c.co_itm=b.co_itm)   " +
                    " INNER JOIN tbm_equInv AS c1 ON (c1.co_itmMae=c.co_itmMae AND c1.co_emp=b.co_emp)  " +
                    " where a.st_reg not in ('E','I')  and a.co_tipdoc in (1,228,3,229)  and a.fe_doc      BETWEEN '"+intAnio+"-01-01' and '"+intAnio+"-12-31'   " +
                    " AND (a.st_tipDev IS NULL OR a.st_tipDev='C')  " +
                    " AND NOT (a.co_emp=1 AND a.co_cli=3515) AND NOT (a.co_emp=1 AND a.co_cli=3516)  " +
                    " AND NOT (a.co_emp=1 AND a.co_cli=602)  AND NOT (a.co_emp=1 AND a.co_cli=603)   " +
                    " AND NOT (a.co_emp=1 AND a.co_cli=2600) AND NOT (a.co_emp=1 AND a.co_cli=1039)  " +
                    " AND NOT (a.co_emp=2 AND a.co_cli=2853) AND NOT (a.co_emp=2 AND a.co_cli=2854) " +
                    " AND NOT (a.co_emp=2 AND a.co_cli=446)  AND NOT (a.co_emp=2 AND a.co_cli=447)  " +
                    " AND NOT (a.co_emp=2 AND a.co_cli=2105) AND NOT (a.co_emp=2 AND a.co_cli=789) " +
                    " AND NOT (a.co_emp=2 AND a.co_cli=790)  AND NOT (a.co_emp=3 AND a.co_cli=2857) " +
                    " AND NOT (a.co_emp=3 AND a.co_cli=2858) AND NOT (a.co_emp=3 AND a.co_cli=452) " +
                    " AND NOT (a.co_emp=3 AND a.co_cli=453)  AND NOT (a.co_emp=3 AND a.co_cli=2107) " +
                    " AND NOT (a.co_emp=3 AND a.co_cli=832)  AND NOT (a.co_emp=4 AND a.co_cli=3116) " +
                    " AND NOT (a.co_emp=4 AND a.co_cli=3117) AND NOT (a.co_emp=4 AND a.co_cli=497)  " +
                    " AND NOT (a.co_emp=4 AND a.co_cli=498)  AND NOT (a.co_emp=4 AND a.co_cli=2294) " +
                    " AND NOT (a.co_emp=4 AND a.co_cli=886)  AND NOT (a.co_emp=4 AND a.co_cli=887)  " +
                    " group by c.co_itmmae, c1.co_itm , b.tx_codalt, a.co_emp, b.co_bod , extract(year from a.fe_doc) , extract(month from a.fe_doc) " +
                    " order by    b.tx_codalt  " +
                    "  ) as  x1 on (x1.co_emp=x.co_emp  and  x1.co_bod=x.co_bod)   " +
                    "  ) as x  group by  co_emp, co_bod, co_itm, anio,  mes";
                stmLoc.executeUpdate(sql);

                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                blnRes=true;
            }
        } 
        catch (java.sql.SQLException e) { blnRes=false;  System.out.println("Excepción: " + e.toString()); }
        catch (Exception e)  { blnRes=false;  System.out.println("Excepción: " + e.toString()); }
        return blnRes;
    }

    private boolean cargarDatosTbm_VenMenInvBod()
    {
        System.out.println("cargarDatosTbm_VenMenInvBod");
        boolean blnRes=true;
        java.sql.Connection conVenMenInvBod;
        int intCodEmpGrpVenMenInvBod=0;
        try{
            conVenMenInvBod=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conVenMenInvBod.setAutoCommit(false);
            if(conVenMenInvBod!=null){
                if(delete_TbmVenMenInvBod(conVenMenInvBod, intCodEmpGrpVenMenInvBod )){
                    if(inserta_TbmVenMenInvBod(conVenMenInvBod, intCodEmpGrpVenMenInvBod )){
                        conVenMenInvBod.commit();
                    }
                    else{
                        conVenMenInvBod.rollback();
                        blnRes=false;
                    }
                }
                else{
                    conVenMenInvBod.rollback();
                    blnRes=false;
                }
                conVenMenInvBod.close();
                conVenMenInvBod=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean delete_TbmVenMenInvBod(java.sql.Connection conexionVenMen, int intCodEmpGrpVenMenInvBod ){
        boolean blnRes=true;
        java.sql.Statement stmVenMen;
        String strSQL="";
        try{
            if(conexionVenMen!=null){
                stmVenMen=conexionVenMen.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbm_venmeninvbod";
                strSQL+=" WHERE co_emp= "+intCodEmpGrpVenMenInvBod+"  ";
                //System.out.println("delete_TbmVenMenInvBod: " + strSQL);
                stmVenMen.executeUpdate(strSQL);
                stmVenMen.close();
                stmVenMen=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean inserta_TbmVenMenInvBod(java.sql.Connection conexionVenMen, int intCodEmpGrpVenMenInvBod )
    {
        boolean blnRes=true;
        java.sql.Statement stmVenMen;
        String strSQL="";
        try{
            if(conexionVenMen!=null){
                stmVenMen=conexionVenMen.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbm_venmeninvbod(";
                strSQL+="             co_emp, co_bod, co_itm, ne_ani, ne_mes, nd_univen, ne_numvec, ";
                strSQL+="             ne_nummes)";
                strSQL+=" (	SELECT c1.co_empGrp, c1.co_bodGrp, c1.co_itm, c1.ne_ani, c1.ne_mes, SUM(c1.nd_uniVen) AS nd_uniVen, ";
                strSQL+=" 	SUM(c1.ne_numVec) AS ne_numVec, SUM(c1.ne_numMes) AS ne_numMes ";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b2.co_emp,b2.co_empGrp, b2.co_bodGrp, b1.co_itm, b2.ne_ani, b2.ne_mes, b2.nd_uniVen, b2.ne_numVec, COUNT(b2.ne_numMes) AS ne_numMes";
                strSQL+=" 		 FROM tbm_equInv AS b1 INNER JOIN(";
                strSQL+=" 			SELECT a1.*, a2.co_itmMae, a3.co_empGrp, a3.co_bodGrp";
                strSQL+=" 			FROM (tbm_venMenInvBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" 			INNER JOIN (    SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+=" 					FROM tbr_bodempbodgrp";
                strSQL+=" 					WHERE co_empGrp=" + intCodEmpGrpVenMenInvBod + ") AS a3";
                strSQL+=" 					ON a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod";
                strSQL+=" 			ORDER BY a2.co_itmMae) AS b2";
                strSQL+=" 			ON b1.co_itmMae=b2.co_itmMae AND b1.co_emp=" + intCodEmpGrpVenMenInvBod + "";
                strSQL+=" 			GROUP BY b2.co_emp, b2.co_empGrp, b2.co_bodGrp, b1.co_itm, b2.ne_ani, b2.ne_mes, b2.nd_uniVen, b2.ne_numVec";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_empGrp, c1.co_bodGrp, c1.co_itm, c1.ne_ani, c1.ne_mes";
                strSQL+=" 	ORDER BY c1.co_empGrp, c1.co_bodGrp, c1.co_itm, c1.ne_ani, c1.ne_mes";
                strSQL+=" )";
                //System.out.println("inserta_TbmVenMenInvBod: " + strSQL);
                stmVenMen.executeUpdate(strSQL);
                stmVenMen.close();
                stmVenMen=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean actualizaDatosInventario()
    {
        System.out.println("actualizaDatosInventario");
        boolean blnRes=true;
        java.sql.Connection conUpdDatInv;
        try{
            conUpdDatInv=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conUpdDatInv.setAutoCommit(false);
            if(conUpdDatInv!=null){
                if(update_tbmCabMovInv_tbmCabGuiRem(conUpdDatInv)){
                    if(update_tbmInvBod_movKar(conUpdDatInv)){
                        if(update_tbmInv_invBod(conUpdDatInv)){
                            conUpdDatInv.commit();
                        }
                        else{
                            conUpdDatInv.rollback();
                            blnRes=false;
                        }                        
                    }
                    else{
                        conUpdDatInv.rollback();
                        blnRes=false;
                    }
                }
                else{
                    conUpdDatInv.rollback();
                    blnRes=false;
                }
                conUpdDatInv.close();
                conUpdDatInv=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

       
    private boolean update_tbmCabMovInv_tbmCabGuiRem(java.sql.Connection conexionStkInv )
    {
        boolean blnRes=true;
        java.sql.Statement stmStkInv;
        String strSQL="";
        try{
            if(conexionStkInv!=null){
                stmStkInv=conexionStkInv.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabmovinv SET st_coninv='C' FROM (";
                strSQL+="     SELECT * FROM (";
                strSQL+="             SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  a.fe_doc,  a.st_coninv,  sum(abs(a1.nd_can)) as nd_can, sum(a1.nd_cancon+a1.nd_cannunrec) as nd_cancon  ";
                strSQL+="             FROM tbm_cabmovinv as a";
                strSQL+="             INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )";
                strSQL+="             WHERE  a.co_tipdoc IN (46,153) AND a.st_coninv IN ('P','E') AND a1.nd_can > 0";
                strSQL+="             GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.st_coninv  ORDER BY a.co_emp, a.co_loc";
                strSQL+="     ) AS x";
                strSQL+="     WHERE (nd_can-nd_cancon) = 0";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabmovinv.co_emp=x.co_emp AND tbm_cabmovinv.co_loc=x.co_loc AND tbm_cabmovinv.co_tipdoc=x.co_tipdoc AND tbm_cabmovinv.co_doc=x.co_doc";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabguirem SET st_coninv='C' FROM (";
                strSQL+="     SELECT * FROM (";
                strSQL+="        SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, SUM(abs(a1.nd_can)) AS nd_can, SUM(a1.nd_cancon+a1.nd_cannunrec) AS nd_cancon  FROM tbm_cabguirem AS a";
                strSQL+="         INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )";
                strSQL+="         WHERE  a.co_tipdoc IN (101,102 )  AND   a.st_coninv='E'  AND a1.st_meregrfisbod='S'";
                strSQL+="         GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc";
                strSQL+="     ) AS x";
                strSQL+="     WHERE (nd_can-nd_cancon) = 0";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabguirem.co_emp=x.co_emp AND tbm_cabguirem.co_loc=x.co_loc AND tbm_cabguirem.co_tipdoc=x.co_tipdoc AND tbm_cabguirem.co_doc=x.co_doc";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabguirem set st_coninv='C' FROM (";
                strSQL+="     SELECT * FROM (";
                strSQL+="         SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, SUM(abs(a1.nd_can)) AS nd_can, SUM(a1.nd_cancon+a1.nd_cannunrec) AS nd_cancon  FROM tbm_cabguirem AS a";
                strSQL+="         INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )";
                strSQL+="         WHERE  a.co_tipdoc IN (101,102 )  AND   a.st_coninv='P'  AND a1.st_meregrfisbod='S'";
                strSQL+="         GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc";
                strSQL+="     ) AS x";
                strSQL+="     WHERE (nd_can-nd_cancon) = 0";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabguirem.co_emp=x.co_emp AND tbm_cabguirem.co_loc=x.co_loc AND tbm_cabguirem.co_tipdoc=x.co_tipdoc AND tbm_cabguirem.co_doc=x.co_doc ";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabguirem SET st_coninv='F' FROM (";
                strSQL+="    SELECT * FROM (";
                strSQL+="        SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc,";
                strSQL+="        ( SELECT COUNT(x.co_doc) FROM tbm_detguirem AS x WHERE x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc AND x.co_doc=a.co_doc AND x.st_meregrfisbod='S' ) AS estdet";
                strSQL+="        FROM tbm_cabguirem AS a";
                strSQL+="        WHERE  a.co_tipdoc IN (101,102 )  AND   a.st_coninv  IN ('P','C')  AND a.st_reg='A'";
                strSQL+="    ) AS x";
                strSQL+="    WHERE estdet = 0";
                strSQL+=") AS x ";
                strSQL+=" WHERE tbm_cabguirem.co_emp=x.co_emp AND tbm_cabguirem.co_loc=x.co_loc AND tbm_cabguirem.co_tipdoc=x.co_tipdoc AND tbm_cabguirem.co_doc=x.co_doc";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabmovinv SET st_coninv='F' FROM (";
                strSQL+="     SELECT * FROM (";
                strSQL+="         SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc,";
                strSQL+="         ( SELECT COUNT(x.co_doc) FROM tbm_detmovinv AS x where x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc AND x.co_doc=a.co_doc AND x.st_meringegrfisbod='S' ) AS estdet";
                strSQL+="         FROM tbm_cabmovinv AS a";
                strSQL+="         WHERE  a.co_tipdoc IN (2, 56,116, 83  )  AND   a.st_coninv  IN ('P','C')  AND a.st_reg='A'";
                strSQL+="     ) AS x";
                strSQL+="     WHERE estdet = 0";
                strSQL+=" ) AS x ";
                strSQL+=" WHERE tbm_cabmovinv.co_emp=x.co_emp AND tbm_cabmovinv.co_loc=x.co_loc AND tbm_cabmovinv.co_tipdoc=x.co_tipdoc AND tbm_cabmovinv.co_doc=x.co_doc ";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabmovinv SET st_coninv='F' FROM (";
                strSQL+="     SELECT * FROM (";
                strSQL+="         SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc,";
                strSQL+="         ( SELECT COUNT(x.co_doc) FROM tbm_detmovinv AS x WHERE x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc AND x.co_doc=a.co_doc AND x.st_meringegrfisbod='S' ) AS estdet";
                strSQL+="         FROM tbm_cabmovinv AS a";
                strSQL+="         WHERE  a.co_tipdoc IN ( 3  )  AND   a.st_coninv  IN ('P','C','E')  AND a.st_reg='A'";
                strSQL+="     ) AS x";
                strSQL+="     WHERE estdet = 0";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabmovinv.co_emp=x.co_emp AND tbm_cabmovinv.co_loc=x.co_loc AND tbm_cabmovinv.co_tipdoc=x.co_tipdoc AND tbm_cabmovinv.co_doc=x.co_doc";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabmovinv SET st_coninv='C' FROM (";
                strSQL+="    SELECT * FROM (";
                strSQL+="        SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  a.fe_doc,  a.st_coninv,  SUM(abs(a1.nd_can)) AS nd_can, SUM(a1.nd_cancon+a1.nd_cannunrec) AS nd_cancon ";
                strSQL+="        FROM tbm_cabmovinv AS a";
                strSQL+="         INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )";
                strSQL+="         WHERE  a.co_tipdoc IN ( 3 ) AND a.st_coninv IN ('P','E') AND a1.nd_can > 0         AND  a1.st_meringegrfisbod='S'";
                strSQL+="         GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.st_coninv  ORDER BY a.co_emp, a.co_loc ";
                strSQL+="     ) AS x";
                strSQL+="     WHERE (nd_can-nd_cancon) = 0";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabmovinv.co_emp=x.co_emp AND tbm_cabmovinv.co_loc=x.co_loc AND tbm_cabmovinv.co_tipdoc=x.co_tipdoc AND tbm_cabmovinv.co_doc=x.co_doc ";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabmovinv SET st_coninv='P' FROM (";
                strSQL+="    SELECT * FROM (";
                strSQL+="        SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc,";
                strSQL+="        ( SELECT COUNT(x.co_doc) FROM tbm_detmovinv AS x WHERE x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc AND x.co_doc=a.co_doc AND x.st_meringegrfisbod='S' ) AS estdet";
                strSQL+="        FROM tbm_cabmovinv AS a";
                strSQL+="        WHERE  a.fe_doc >='2011-02-28' AND  a.co_emp=1 AND a.co_loc=5 AND a.co_tipdoc in ( 3  )  AND   a.st_coninv = '' AND a.st_reg='A'";
                strSQL+="     ) AS x ";
                strSQL+="     WHERE estdet=1";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabmovinv.co_emp=x.co_emp AND tbm_cabmovinv.co_loc=x.co_loc AND tbm_cabmovinv.co_tipdoc=x.co_tipdoc AND tbm_cabmovinv.co_doc=x.co_doc";
                strSQL+=";";
                strSQL+="UPDATE tbm_cabguirem SET st_coninv='C' FROM(";
                strSQL+="    SELECT * FROM (";
                strSQL+="         SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, SUM(abs(a1.nd_can)) AS nd_can, SUM(a1.nd_cantotguisec) AS nd_cantotguisec  FROM tbm_cabguirem AS a";
                strSQL+="        INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )";
                strSQL+="         WHERE  a.co_tipdoc IN (101,102 )  AND   a.st_coninv IN ('P','E')  AND a1.st_meregrfisbod='S'";
                strSQL+="         GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc";
                strSQL+="     ) AS x";
                strSQL+="    WHERE (nd_can-nd_cantotguisec) = 0";
                strSQL+=") AS x ";
                strSQL+=" WHERE tbm_cabguirem.co_emp=x.co_emp AND tbm_cabguirem.co_loc=x.co_loc AND tbm_cabguirem.co_tipdoc=x.co_tipdoc AND tbm_cabguirem.co_doc=x.co_doc";
                strSQL+=";";                
                System.out.println("update_tbmCabMovInv_tbmCabGuiRem: " + strSQL);
                stmStkInv.executeUpdate(strSQL);
                stmStkInv.close();
                stmStkInv=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }

    private boolean update_tbmInvBod_movKar(java.sql.Connection conexionStkInv )
    {
        boolean blnRes=true;
        java.sql.Statement stmStkInv;
        String strSQL="";
        try{
            if(conexionStkInv!=null){
                stmStkInv=conexionStkInv.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabmovinv SET st_tipdev='C' WHERE co_emp<>0 AND st_tipdev is null";
                strSQL+=";";
                //ACTUALIZA STOCK DE ACUERDO AL MOVIMIENTO Y A LA BODEGA
                //Tuval     Bodegas : 1-15,28
                for(int i=1; i<=31; i++){
                    switch(i){
                        case 1://California
                        case 2://Neplos
                        case 3://Importaciones
                        case 4://Dañados (1)
                        case 5://Consignación
                        case 6://Exposición (Vía Daule)
                        case 7://Vía Daule
                        case 8://Quito Norte
                        case 9://Compras locales
                        case 10://Manta
                        case 11://Interna Vía Daule
                        case 12://Santo Domingo
                        case 13://Faltantes
                        case 14://Faltantes (California)
                        case 15://Inmaconsa                            
                        case 16://Faltantes (Quito)*
                        case 17://Faltantes (Manta)*
                        case 18://Faltantes (Santo Domingo)*
                        case 19://Faltantes (Vía Daule)*
                        case 20://Exposición (California)*
                        case 21://Exposición (Quito)*
                        case 22://Exposición (Manta)*
                        case 23://Exposición (Santo Domingo)*
                        case 24://Dañados (2)*
                        case 25://Dañados (Dar de baja)*
                        case 26://Proyectos*
                        case 27://Faltantes (Inmaconsa)*
                        case 28://Cuenca
                        case 29://Exposición (Cuenca)*
                        case 30://Faltantes (Cuenca)*
                        case 31://Inmaconsa 2
//                            strSQL+="UPDATE tbm_invbod SET nd_stkact=a.mov FROM(";
//                            strSQL+="    SELECT *FROM(";
//                            strSQL+="        SELECT *FROM(";
//                            strSQL+="            SELECT sum(b.nd_can) AS mov,b.co_itm FROM tbm_cabmovinv AS a";
//                            strSQL+="            INNER JOIN tbm_detmovinv AS b on(a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipdoc=b.co_tipdoc AND a.co_doc=b.co_doc)";
//                            strSQL+="            INNER JOIN tbm_inv AS c on(b.co_emp=c.co_emp AND b.co_itm=c.co_itm)";
//                            strSQL+="            WHERE  a.st_reg NOT IN ('I','E')    AND  ( a.st_tipdev='C' OR  a.st_tipdev IS null )";
//                            strSQL+="            AND a.co_emp=1 AND c.st_ser='N'   AND  b.co_bod=" + i + "";
//                            strSQL+="            GROUP BY b.co_itm";
//                            strSQL+="        ) AS  x,";
//                            strSQL+="        (";
//                            strSQL+="            SELECT  sum(nd_stkact),co_itm AS coitm FROM tbm_invbod WHERE co_emp=1 AND co_bod=" + i + "";
//                            strSQL+="            GROUP BY co_itm";
//                            strSQL+="        ) AS y WHERE y.coitm=x.co_itm";
//                            strSQL+="    ) AS x";
//                            strSQL+="    WHERE x.mov<>x.sum";
//                            strSQL+=") AS a";
//                            strSQL+=" WHERE tbm_invbod.co_emp=1 AND tbm_invbod.co_bod=" + i + " AND tbm_invbod.co_itm=a.coitm";
//                            strSQL+=";";
                            
                            strSQL+=" UPDATE tbm_invbod SET nd_stkact=a.mov FROM (";
                            strSQL+="	SELECT * FROM (";
                            strSQL+="		SELECT w.co_itm";
                            strSQL+=" 		       , CASE WHEN x.mov IS NULL THEN 0 ELSE x.mov END AS mov";
                            strSQL+=" 		       , CASE WHEN y.sum IS NULL THEN 0 ELSE y.sum END AS sum";
                            strSQL+="		 FROM (";
                            strSQL+=" 			SELECT c.co_itm FROM tbm_inv AS c";
                            strSQL+=" 			WHERE  c.st_reg NOT IN ('I','E')"; 
                            strSQL+=" 			AND c.co_emp=1 AND c.st_ser='N'";
                            strSQL+=" 			GROUP BY c.co_itm";
                            strSQL+=" 		) AS  w";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT sum(b.nd_can) AS mov,b.co_itm FROM tbm_cabmovinv AS a";
                            strSQL+=" 			INNER JOIN tbm_detmovinv AS b ON(a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipdoc=b.co_tipdoc AND a.co_doc=b.co_doc)";
                            strSQL+=" 			INNER JOIN tbm_inv AS c ON(b.co_emp=c.co_emp AND b.co_itm=c.co_itm)";
                            strSQL+=" 			WHERE  a.st_reg NOT IN ('I','E','O') ";  //Rose 19/Oct/2017
                            strSQL+=" 			AND  ( a.st_tipdev='C' OR  a.st_tipdev IS null )";
                            strSQL+="                   AND (CASE WHEN b.st_Reg IS NULL THEN 'A' ELSE b.st_Reg END ) NOT IN ('I') ";  //Rose 20/Oct/2017: Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                            strSQL+=" 			AND a.co_emp=1 AND c.st_ser='N'   AND  b.co_bod=" + i + "";
                            strSQL+=" 			GROUP BY b.co_itm";
                            strSQL+=" 		) AS  x";
                            strSQL+=" 		ON w.co_itm=x.co_itm";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT  sum(nd_stkact) AS sum,co_itm AS coitm FROM tbm_invbod WHERE co_emp=1 AND co_bod=" + i + "";
                            strSQL+=" 			GROUP BY co_itm";
                            strSQL+=" 		) AS y";
                            strSQL+=" 		ON w.co_itm=y.coitm";
                            strSQL+=" 	) AS x";
                            strSQL+="	WHERE x.mov<>x.sum";
                            strSQL+=") AS a";
                            strSQL+=" WHERE tbm_invbod.co_emp=1 AND tbm_invbod.co_bod=" + i + " AND tbm_invbod.co_itm=a.co_itm";
                            strSQL+=";";
                            break;
                        default:
                            break;                        
                    }
                }
                //Castek    Bodegas: 1,3-12,15,28
                for(int i=1; i<=31; i++){
                    switch(i){
                        case 1://Quito Norte
                        case 3://California
                        case 4://Manta
                        case 5://Importaciones
                        case 6://Dañados (1)
                        case 7://Consignación
                        case 8://Exposición (Vía Daule)
                        case 9://Vía Daule
                        case 10://Compras locales
                        case 11://Interna Vía Daule
                        case 12://Santo Domingo
                        case 13://Faltantes
                        case 14://Faltantes (California)
                        case 15://Inmaconsa                            
                        case 16://Faltantes (Quito)*
                        case 17://Faltantes (Manta)*
                        case 18://Faltantes (Santo Domingo)*
                        case 19://Faltantes (Vía Daule)*
                        case 20://Exposición (California)*
                        case 21://Exposición (Quito)*
                        case 22://Exposición (Manta)*
                        case 23://Exposición (Santo Domingo)*
                        case 24://Dañados (2)*
                        case 25://Dañados (Dar de baja)*
                        case 26://Proyectos*
                        case 27://Faltantes (Inmaconsa)*                            
                        case 28://Cuenca
                        case 29://Exposición (Cuenca)
                        case 30://Faltantes (Cuenca)
                        case 31://Inmaconsa 2                            
                            strSQL+=" UPDATE tbm_invbod SET nd_stkact=a.mov FROM (";
                            strSQL+="	SELECT * FROM (";
                            strSQL+="		SELECT w.co_itm";
                            strSQL+=" 		       , CASE WHEN x.mov IS NULL THEN 0 ELSE x.mov END AS mov";
                            strSQL+=" 		       , CASE WHEN y.sum IS NULL THEN 0 ELSE y.sum END AS sum";
                            strSQL+="		 FROM (";
                            strSQL+=" 			SELECT c.co_itm FROM tbm_inv AS c";
                            strSQL+=" 			WHERE  c.st_reg NOT IN ('I','E')";
                            strSQL+=" 			AND c.co_emp=2 AND c.st_ser='N'";
                            strSQL+=" 			GROUP BY c.co_itm";
                            strSQL+=" 		) AS  w";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT sum(b.nd_can) AS mov,b.co_itm FROM tbm_cabmovinv AS a";
                            strSQL+=" 			INNER JOIN tbm_detmovinv AS b ON(a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipdoc=b.co_tipdoc AND a.co_doc=b.co_doc)";
                            strSQL+=" 			INNER JOIN tbm_inv AS c ON(b.co_emp=c.co_emp AND b.co_itm=c.co_itm)";
                            strSQL+=" 			WHERE  a.st_reg NOT IN ('I','E','O') "; //Rose 19/Oct/2017
                            strSQL+=" 			AND  ( a.st_tipdev='C' OR  a.st_tipdev IS null )";
                            strSQL+="                   AND (CASE WHEN b.st_Reg IS NULL THEN 'A' ELSE b.st_Reg END ) NOT IN ('I') ";  //Rose 20/Oct/2017: Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                            strSQL+=" 			AND a.co_emp=2 AND c.st_ser='N'   AND  b.co_bod=" + i + "";
                            strSQL+=" 			GROUP BY b.co_itm";
                            strSQL+=" 		) AS  x";
                            strSQL+=" 		ON w.co_itm=x.co_itm";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT  sum(nd_stkact) AS sum,co_itm AS coitm FROM tbm_invbod WHERE co_emp=2 AND co_bod=" + i + "";
                            strSQL+=" 			GROUP BY co_itm";
                            strSQL+=" 		) AS y";
                            strSQL+=" 		ON w.co_itm=y.coitm";
                            strSQL+=" 	) AS x";
                            strSQL+="	WHERE x.mov<>x.sum";
                            strSQL+=") AS a";
                            strSQL+=" WHERE tbm_invbod.co_emp=2 AND tbm_invbod.co_bod=" + i + " AND tbm_invbod.co_itm=a.co_itm";
                            strSQL+=";";
                            break;
                        default:
                            break;
                        
                    }
                }

                //Dimulti   Bodegas: 1-12,15,28
                for(int i=1; i<=31; i++){
                    switch(i){
                        case 1://Vía Daule
                        case 2://California
                        case 3://Importaciones
                        case 4://Dañados (1)
                        case 5://Consignación
                        case 6://Exposición (Vía Daule)
                        case 7://Quito Norte
                        case 8://Compras locales
                        case 9://Manta
                        case 10://Interna Vía Daule
                        case 11://Santo Domingo
                        case 12://Faltantes
                        case 14://Faltantes (California)                            
                        case 15://Inmaconsa
                        case 16://Faltantes (Quito)
                        case 17://Faltantes (Manta)
                        case 18://Faltantes (Santo Domingo)
                        case 19://Faltantes (Vía Daule)
                        case 20://Exposición (California)
                        case 21://Exposición (Quito)
                        case 22://Exposición (Manta)
                        case 23://Exposición (Santo Domingo)
                        case 24://Dañados (2)
                        case 25://Dañados (Dar de baja)
                        case 26://Proyectos
                        case 27://Faltantes (Inmaconsa)
                        case 28://Cuenca
                        case 29://Exposición (Cuenca)
                        case 30://Faltantes (Cuenca)
                        case 31://Inmaconsa 2     
//                            strSQL+="UPDATE tbm_invbod SET nd_stkact=a.mov FROM(";
//                            strSQL+="    SELECT *FROM(";
//                            strSQL+="        SELECT *FROM(";
//                            strSQL+="            SELECT sum(b.nd_can) AS mov,b.co_itm FROM tbm_cabmovinv AS a";
//                            strSQL+="            INNER JOIN tbm_detmovinv AS b on(a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipdoc=b.co_tipdoc AND a.co_doc=b.co_doc)";
//                            strSQL+="            INNER JOIN tbm_inv AS c on(b.co_emp=c.co_emp AND b.co_itm=c.co_itm)";
//                            strSQL+="            WHERE  a.st_reg NOT IN ('I','E')    AND  ( a.st_tipdev='C' OR  a.st_tipdev IS null )";
//                            strSQL+="            AND a.co_emp=4 AND c.st_ser='N'   AND  b.co_bod=" + i + "";
//                            strSQL+="            GROUP BY b.co_itm";
//                            strSQL+="        ) AS  x,";
//                            strSQL+="        (";
//                            strSQL+="            SELECT  sum(nd_stkact),co_itm AS coitm FROM tbm_invbod WHERE co_emp=4 AND co_bod=" + i + "";
//                            strSQL+="            GROUP BY co_itm";
//                            strSQL+="        ) AS y WHERE y.coitm=x.co_itm";
//                            strSQL+="    ) AS x";
//                            strSQL+="    WHERE x.mov<>x.sum";
//                            strSQL+=") AS a";
//                            strSQL+=" WHERE tbm_invbod.co_emp=4 AND tbm_invbod.co_bod=" + i + " AND tbm_invbod.co_itm=a.coitm";
//                            strSQL+=";";
                            
                            strSQL+=" UPDATE tbm_invbod SET nd_stkact=a.mov FROM (";
                            strSQL+="	SELECT * FROM (";
                            strSQL+="		SELECT w.co_itm";
                            strSQL+=" 		       , CASE WHEN x.mov IS NULL THEN 0 ELSE x.mov END AS mov";
                            strSQL+=" 		       , CASE WHEN y.sum IS NULL THEN 0 ELSE y.sum END AS sum";
                            strSQL+="		 FROM (";
                            strSQL+=" 			SELECT c.co_itm FROM tbm_inv AS c";
                            strSQL+=" 			WHERE  c.st_reg NOT IN ('I','E')";
                            strSQL+=" 			AND c.co_emp=4 AND c.st_ser='N'";
                            strSQL+=" 			GROUP BY c.co_itm";
                            strSQL+=" 		) AS  w";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT sum(b.nd_can) AS mov,b.co_itm FROM tbm_cabmovinv AS a";
                            strSQL+=" 			INNER JOIN tbm_detmovinv AS b ON(a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipdoc=b.co_tipdoc AND a.co_doc=b.co_doc)";
                            strSQL+=" 			INNER JOIN tbm_inv AS c ON(b.co_emp=c.co_emp AND b.co_itm=c.co_itm)";
                            strSQL+=" 			WHERE  a.st_reg NOT IN ('I','E','O') "; //Rose 19/Oct/2017
                            strSQL+=" 			AND  ( a.st_tipdev='C' OR  a.st_tipdev IS null )";
                            strSQL+="                   AND (CASE WHEN b.st_Reg IS NULL THEN 'A' ELSE b.st_Reg END ) NOT IN ('I') ";  //Rose 20/Oct/2017: Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                            strSQL+=" 			AND a.co_emp=4 AND c.st_ser='N'   AND  b.co_bod=" + i + "";
                            strSQL+=" 			GROUP BY b.co_itm";
                            strSQL+=" 		) AS  x";
                            strSQL+=" 		ON w.co_itm=x.co_itm";
                            strSQL+="		LEFT OUTER JOIN(";
                            strSQL+=" 			SELECT  sum(nd_stkact) AS sum,co_itm AS coitm FROM tbm_invbod WHERE co_emp=4 AND co_bod=" + i + "";
                            strSQL+=" 			GROUP BY co_itm";
                            strSQL+=" 		) AS y";
                            strSQL+=" 		ON w.co_itm=y.coitm";
                            strSQL+=" 	) AS x";
                            strSQL+="	WHERE x.mov<>x.sum";
                            strSQL+=") AS a";
                            strSQL+=" WHERE tbm_invbod.co_emp=4 AND tbm_invbod.co_bod=" + i + " AND tbm_invbod.co_itm=a.co_itm";
                            strSQL+=";";                            
                            break;
                        default:
                            break;
                        
                    }
                }
                System.out.println("update_tbmInvBod_movKar: " + strSQL);
                stmStkInv.executeUpdate(strSQL);
                stmStkInv.close();
                stmStkInv=null;
            }

        }
        catch(java.sql.SQLException e){
            blnRes=false;
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
       
       
    private boolean update_tbmInv_invBod(java.sql.Connection conexionStkInv ){
        boolean blnRes=true;
        java.sql.Statement stmStkInv;
        String strSQL="";
        try{
            if(conexionStkInv!=null){
                stmStkInv=conexionStkInv.createStatement();
                //ACTUALIZA STOCK DE ACUERDO AL MOVIMIENTO A INVENTARIO
                strSQL="";
                for(int i=0; i<=4; i++){
                    switch(i){
                        case 0://Grupo
                        case 1://Tuval
                        case 2://Castek
                        case 4://Dimulti
                            strSQL+="UPDATE tbm_inv SET nd_stkact=a.sum FROM(";
                            strSQL+="	SELECT * FROM(";
                            strSQL+="		SELECT *FROM(";
                            strSQL+=" 			SELECT co_itm,nd_stkact FROM tbm_inv WHERE co_emp=" + i + "";
                            strSQL+=" 		) AS a";
                            strSQL+="		,";
                            strSQL+="		(";
                            strSQL+="			SELECT co_itm AS coitm, sum(nd_stkact) FROM tbm_invbod WHERE co_emp=" + i + " GROUP BY co_itm";
                            strSQL+=" 		) AS b";
                            strSQL+="		WHERE b.coitm=a.co_itm";
                            strSQL+="	) AS x";
                            strSQL+="	WHERE x.nd_stkact <> sum";
                            strSQL+=") AS a";
                            strSQL+=" WHERE tbm_inv.co_emp=" + i + "";
                            strSQL+=" AND tbm_inv.co_itm=a.coitm";
                            strSQL+=";";
                            break;
                        default:
                            break;                        
                    }
                }
                System.out.println("update_tbmInv_invBod: " + strSQL);
                stmStkInv.executeUpdate(strSQL);
                stmStkInv.close();
                stmStkInv=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
       
       

    /***********************************************************************************/

    private String getObtenerNomMes(int intMes){
       String strMes="";

       if(intMes==1) strMes="Ene";
       else if(intMes==2) strMes="Feb";
       else if(intMes==3) strMes="Mar";
       else if(intMes==4) strMes="Abr";
       else if(intMes==5) strMes="May";
       else if(intMes==6) strMes="Jun";
       else if(intMes==7) strMes="Jul";
       else if(intMes==8) strMes="Ago";
       else if(intMes==9) strMes="Sep";
       else if(intMes==10) strMes="Oct";
       else if(intMes==11) strMes="Nov";
       else if(intMes==12) strMes="Dic";

      return strMes;
    }


    /*************************************************************************************/



    /***********************************************************************************/
    /**
     * Carga driver de conceccion a base.
     * @param driver
     * @return
     */
    public boolean cargarDrv_F1(String driver){
        boolean blnRes=true;
        try{
            Class.forName(driver);
         }catch (ClassNotFoundException e){  blnRes=false;    System.out.println(""+ e.toString());  }
        return blnRes;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       new Main();
    }

    private void enviarCorreo(String strMsExcp)
    {
        System.out.println("enviarCorreo");
        try
          {
            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "3Mjoafdfma!6MK7n";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = false;
            String subject = "SafSer03";
            String body = strMsExcp;

            sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);
        }
        catch (Exception e) {  e.printStackTrace();    }
    }


    public boolean sendMailTuv(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String body)
    {
        boolean blnRes=false;
        try{
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", server);
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.user", userName);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", "true");              

            // Get session
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() { 
                        return new PasswordAuthentication(userName, password);
                    }
                }
            );            

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
        }
        catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }
        return blnRes;
    }



}

