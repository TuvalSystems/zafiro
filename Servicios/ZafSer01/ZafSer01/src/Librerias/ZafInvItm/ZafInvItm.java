/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package Librerias.ZafInvItm;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
  
/**
 *
 * @author jayapata   
 * 
 * @Version: 1.1
 */ 
public class ZafInvItm {
   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafUtil.ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;
   javax.swing.JDialog jDialo=null;
   int intTipConf= 2;    // variable de configuracion 1=: Trabaja con Grupo  2=: Trabaja con Empresa.
   StringBuffer stbinvemp; //VARIABLE TIPO BUFFER
   StringBuffer stbinvgrp; //VARIABLE TIPO BUFFER
   StringBuffer stbConStkInvEmp; //VARIABLE TIPO BUFFER Control de Stock Inventario Empresa
   StringBuffer stbConStkInvGrp; //VARIABLE TIPO BUFFER Control de Stock Inventario Grupo
   int intIndUni=0;  
   int INTCODEMPGRP=0;
   double dblCanIngFisBod=0;
   double dblCanEgrFisBod=0;
 
      /** Creates a new instance of ZafInv */
    public ZafInvItm(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }        
    }
 
    public ZafInvItm(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }        
    } 
    
    public ZafInvItm(Librerias.ZafParSis.ZafParSis obj){
       try{
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
           objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){   }
    }
      
    
 /**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ventas.
  * 
  * @return el sql cargado.
  */   
 public String getSqlInvVen(){
     String strSQL="";   
      
        String strAux = ",CASE " +
        "WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
        " ))) THEN 'S' " +
        "" +
        " WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
        " ))) THEN 'I' " +
        " ELSE 'N' END  as isterL ";            

        String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
        " THEN 'S' ELSE 'N' END AS proconf  ";

       if(intTipConf==1){ 
          if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                strSQL="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                strSQL+=strAux;
                strSQL+=" , case when (trim(SUBSTR (UPPER(a7.tx_codAlt), length(a7.tx_codAlt) ,1)) = 'S' ) then 0 else   a7.nd_stkActcen  end as nd_stkActcen  , a7.nd_maruti, a7.st_permodnomitmven, a7.nd_pesitmkgr  ";
                strSQL+=strAux2;
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser , a2.nd_maruti, a2.st_permodnomitmven,   d3.nd_stkActcen, a2.nd_pesitmkgr ";
                strSQL+=" FROM (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                strSQL+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a1 " +
                "" +
                " LEFT OUTER JOIN ( SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkActcen FROM tbm_equInv AS c1 " +
                " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm) " +
                " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer) " +
                " WHERE c3.co_emp=1 AND c3.co_loc=1 GROUP BY c1.co_itmMae " +
                " ) AS d3 ON (a1.co_itmMae=d3.co_itmMae) " +
                ", (";
                strSQL+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti, c1.st_permodnomitmven , c1.nd_pesitmkgr  ";
                strSQL+=" FROM tbm_inv AS c1";
                strSQL+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                strSQL+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                strSQL+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" ) AS a2";
                strSQL+=" WHERE a1.co_itmMae=a2.co_itmMae";
                strSQL+=" ) AS a7  order by a7.tx_codalt";
            }else{
                strSQL="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser, a7.nd_maruti, a7.st_permodnomitmven, a7.nd_pesitmkgr ";
                strSQL+=strAux;
                strSQL+=", 0 as nd_stkActcen ";
                strSQL+=strAux2;
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser, a2.nd_maruti, a2.st_permodnomitmven, a2.nd_pesitmkgr  ";
                strSQL+=" FROM (";
                strSQL+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                strSQL+=" FROM tbm_equInv AS b1";
                strSQL+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                strSQL+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                strSQL+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" GROUP BY b1.co_itmMae";
                strSQL+=" ) AS a1 " +
                ", (";
                strSQL+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti, c1.st_permodnomitmven, c1.nd_pesitmkgr   ";
                strSQL+=" FROM tbm_inv AS c1";
                strSQL+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                strSQL+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                strSQL+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" ) AS a2";
                strSQL+=" WHERE a1.co_itmMae=a2.co_itmMae";
                strSQL+=" ) AS a7  order by a7.tx_codalt";
            }
          
       }    
       if(intTipConf==2){ 
           strSQL="SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_prevta1, a.st_ivaven, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser, a.nd_maruti, a.st_permodnomitmven, a.nd_pesitmkgr ";
           strSQL+=strAux;
           strSQL+=", 0 as nd_stkActcen ";
           strSQL+=strAux2;
           strSQL +="    ,a1.tx_tipunimed  , a.st_blqprevta, a2.nd_caningbod   FROM tbm_inv AS a " +
           " LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " +
           " INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm " +
           " AND a2.co_bod = " +
           " ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and st_reg='P'  )  ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND  a.st_reg not in ('T','I','E','U')        " +
           " ORDER BY a.tx_codalt";
       }
        
     return strSQL;
   } 
    
  
     
      
 /**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ordenes de compra.
  * 
  * @return el sql cargado.
  */        
 public String getSqlInvCom(){
     String strSQL="";   

     int co_tipdoc=2;

     String strAux = ",CASE WHEN (" +
     " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
     " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
     " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
     " ))) THEN 'S' ELSE 'N' END  as isterL";


       if(intTipConf==1){ 
            
            strSQL=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " +
            " FROM ( " +
            "        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
            strSQL+=strAux;
            strSQL+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
            strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" WHERE a1.st_reg not in('T','I','E','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
            strSQL+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
            strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSQL+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
            strSQL+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
            strSQL+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
            strSQL+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
            strSQL+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
            strSQL+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
            " )  AS x WHERE " +
            " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
            " ORDER BY tx_codAlt";
       }    
       if(intTipConf==2){ 
           strSQL="SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser" +
           " FROM ( SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser , isterl  " +
           " FROM ( SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_cosuni, a.st_ivacom, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser  "+
           " "+strAux+" FROM tbm_inv AS a " +
           " LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " +
           " INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm " +
           " AND a2.co_bod = " +
           " ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and st_reg='P' )  ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND  a.st_reg not in ('T','I','E','U')   ) AS x WHERE  "+ // isterl='S'   " +
           " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
           " )  AS x  ORDER BY tx_codalt ";
       }
     //System.out.println(""+ strSQL );   
     return strSQL;
   } 
  
    
   
   
/**
  * Función que se encarga de obtener el Query de inventario que se manejara en el programa de ingreso y egreso de varios motivos.
  * 
  * @return el sql cargado.
  */          
 public String getSqlInvIngEgr(){
     String strSQL="";   
     int co_tipdoc=1; 
     
        String strAux = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

         String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
        " THEN 'S' ELSE 'N' END AS proconf  ";
             
       if(intTipConf==1){ 
             
            strSQL=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " +
            " "+strAux2+" "+
            " FROM ( " +
            "        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
            strSQL+=strAux;
            strSQL+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
            strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" WHERE a1.st_reg not in('T','I','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
            strSQL+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
            strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSQL+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
            strSQL+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
            strSQL+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
            strSQL+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
            strSQL+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
            strSQL+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
            " )  AS x WHERE " +
            " CASE WHEN (" +
            " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
            " ORDER BY tx_codAlt";
       }    
       if(intTipConf==2){ 
           strSQL="SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_cosuni, a.st_ivacom, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser  "+
           " "+strAux2+" "+
           " FROM tbm_inv AS a " +
           " LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " +
           " INNER JOIN tbm_invbod AS a2 ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm " +
           " AND a2.co_bod = " +
           " ( select co_bod from tbr_bodloc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and st_reg='P'  )  ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND  a.st_reg not in ('T','I','E','U')        " +
           " ORDER BY a.tx_codalt";
       }
     //System.out.println(""+ strSQL );   
     return strSQL;
   } 
  
      
   
   /**
    * Esta función se encarga de inicializar las variable de tipo buffer para que sean adicionados todos los querys que se ejecutaran.
    */
   public void inicializaObjeto(){
      stbinvemp=new StringBuffer(); 
      stbinvgrp=new StringBuffer(); 
      stbConStkInvEmp=new StringBuffer();
      stbConStkInvGrp=new StringBuffer(); 
      intIndUni=0;
   }
  
   
   
     
   /**
    * Esta función se encarga de armar el Query y lo adiciona en las variables de tipo buffer. 
    * 
    * @param intCodEmp Código de empresa
    * @param intCodItm Código de item
    * @param intCodBod Código de bodega
    * @param dblCan    Cantidad  
    * @param intTipMov Tipo de movimiento sea esta aumenta o disminuye sea el caso
    * @param strEstFisBod Estado de si ingresa o no fisicamente a bodega
    * @param strMerIngEgr Estado si mercaderia ingresa o egresa fisicamente a bodega
    * @param strTipIngEgr Estado de tipo si es ingreso o egreso fisicamente a bodega
    * @param intTipMovFisBod Tipo de movimiento fisico de bodega
    */
   public void generaQueryStock(int intCodEmp, int intCodItm, int intCodBod, double dblCan, int intTipMov, String strEstFisBod, String strMerIngEgr, String strTipIngEgr, int intTipMovFisBod ,String conf ){
    try{
        dblCanIngFisBod=0;
        dblCanEgrFisBod=0;
        
        if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S")){   
             if(strTipIngEgr.equals("I")) dblCanIngFisBod=dblCan*intTipMovFisBod;
             if(strTipIngEgr.equals("E")) dblCanEgrFisBod=dblCan*intTipMovFisBod;
         }}
        dblCan=dblCan*intTipMov;
        stbinvemp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+"; ");
        stbinvemp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END ) +"+dblCan+" " +
        ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"
		/*cambio realizado para evitar actualizar la cantidad de egreso cuando ya se ha realizado una confirmacion por egreso*/
		+(!((conf!=null && conf.equalsIgnoreCase("C")))?dblCanEgrFisBod:0)+" "+
		/*cambio realizado para evitar actualizar la cantidad de egreso cuando ya se ha realizado una confirmacion por egreso*/
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+" AND co_bod="+intCodBod+"; ");

        if(intIndUni>0) stbConStkInvEmp.append(" UNION ALL ");
        stbConStkInvEmp.append("SELECT co_emp, co_itm, nd_stkact FROM tbm_invbod AS a WHERE a.co_emp="+intCodEmp+" AND a.co_itm="+intCodItm+" AND a.co_bod="+intCodBod+" ");
                
       if(intTipConf==1){  
            stbinvgrp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")); "); 
            stbinvgrp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END )+"+dblCan+" " +
            ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+        
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")) " +
            "  AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+"); "); 

            if(intIndUni>0) stbConStkInvGrp.append(" UNION ALL ");
            stbConStkInvGrp.append("SELECT co_itm, nd_stkact FROM tbm_invBod AS a " +
            " WHERE co_emp="+INTCODEMPGRP+" AND co_itm=( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            " (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+" )) " +
            " AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+" ) ");
       }
     intIndUni=1;  
    }catch(Exception evt){ }
   }
   


   /**
    * Esta función se encarga de armar el Query y lo adiciona en las variables de tipo buffer.
    *
    * @param intCodEmp Código de empresa
    * @param intCodItm Código de item
    * @param intCodBod Código de bodega
    * @param dblCan    Cantidad
    * @param intTipMov Tipo de movimiento sea esta aumenta o disminuye sea el caso
    * @param strEstFisBod Estado de si ingresa o no fisicamente a bodega
    * @param strMerIngEgr Estado si mercaderia ingresa o egresa fisicamente a bodega
    * @param strTipIngEgr Estado de tipo si es ingreso o egreso fisicamente a bodega
    * @param intTipMovFisBod Tipo de movimiento fisico de bodega
    * @param ndCanEgreFisBod  cantidad que egresa fisicamente de bodega.
    */
   public void generaQueryStock(int intCodEmp, int intCodItm, int intCodBod, double dblCan, int intTipMov, String strEstFisBod, String strMerIngEgr, String strTipIngEgr, int intTipMovFisBod, double ndCanEgreFisBod ){
    try{
        dblCanIngFisBod=0;
        dblCanEgrFisBod=0;

        if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S")){
             if(strTipIngEgr.equals("E")) dblCanEgrFisBod=ndCanEgreFisBod*intTipMovFisBod;
         }}
        dblCan=dblCan*intTipMov;
        stbinvemp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+"; ");
        stbinvemp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END ) +"+dblCan+" " +
        ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+
        " WHERE co_emp="+intCodEmp+" AND co_itm="+intCodItm+" AND co_bod="+intCodBod+"; ");

        if(intIndUni>0) stbConStkInvEmp.append(" UNION ALL ");
        stbConStkInvEmp.append("SELECT co_emp, co_itm, nd_stkact FROM tbm_invbod AS a WHERE a.co_emp="+intCodEmp+" AND a.co_itm="+intCodItm+" AND a.co_bod="+intCodBod+" ");

       if(intTipConf==1){
            stbinvgrp.append("UPDATE tbm_inv SET st_regrep='M', nd_stkAct=nd_stkAct+"+dblCan+" " +
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")); ");
            stbinvgrp.append("UPDATE tbm_invBod SET nd_stkAct=( CASE WHEN nd_stkAct IS NULL THEN 0 ELSE nd_stkAct END )+"+dblCan+" " +
            ", nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )+"+dblCanIngFisBod+", nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )+"+dblCanEgrFisBod+" "+
            " WHERE  co_emp="+INTCODEMPGRP+" AND co_itm=" +
            " ( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            "  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+")) " +
            "  AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+"); ");

            if(intIndUni>0) stbConStkInvGrp.append(" UNION ALL ");
            stbConStkInvGrp.append("SELECT co_itm, nd_stkact FROM tbm_invBod AS a " +
            " WHERE co_emp="+INTCODEMPGRP+" AND co_itm=( Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" And co_itmmae IN " +
            " (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+intCodItm+" )) " +
            " AND co_bod=(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+intCodBod+" ) ");
       }
     intIndUni=1;
    }catch(Exception evt){  mostarErrorException(evt); }
   }

   
   

private void mostarErrorException(Exception evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);    
     else objUti.mostrarMsgErr_F1(jDialo, evt);  
}   
   
private void mostarErrorSqlException(java.sql.SQLException evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);    
     else objUti.mostrarMsgErr_F1(jDialo, evt);  
}   
   


/**
 * Esta función se encarga de hacer le ejecución de la actualización de inventario.
 *  
 * @param conn Conexion de la base de datos
 * @param intActGrp si actualiza grupo o no 1=Si  0 = NO
 * @return True :Si la ejecución fue con exito.
 * @return False:Si la ejecución dio un error.
 */  
public boolean ejecutaActStock(java.sql.Connection conn, int intActGrp ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   try{
     stmLoc=conn.createStatement();
     
     //System.out.println("sqlEmp: "+ stbinvemp.toString() );
     //System.out.println("sqlGrp: "+ stbinvgrp.toString() );
     
     stmLoc.executeUpdate(stbinvemp.toString());
        
     if(intTipConf==1){ 
      if(intActGrp==1) stmLoc.executeUpdate(stbinvgrp.toString());
     }
     stmLoc.close();
     stmLoc=null;
   }catch(java.sql.SQLException Evt){ blnRes=false; }     
    catch(Exception evt){  blnRes=false;  } 
  return  blnRes; 
}

  


/**
 * Esta función se encarga de obtener el query que se ejecutara para disminucion o aumentar el inventario.
 *
 * @return  El Query del inventario.
 */
public String getQueryEjecutaActStock(){
   String strSqlQue="";
   try{
  
     strSqlQue=stbinvemp.toString();
   
   }catch(Exception evt){  mostarErrorException(evt); }
  return  strSqlQue;
}



/**
 * Esta función se encarga de limpiar las variable de tipo buffer tipo querys.
 */
public void limpiarObjeto(){
  stbinvemp=null;
  stbinvgrp=null;
  stbConStkInvEmp=null;
  stbConStkInvGrp=null;
}


/** 
 * Esta función se encarga de saber si el item es de servicio o no.
 * 
 * @param conn  Conexion de la base de datos
 * @param intCodItm Codigo del item 
 * @return True: si el item no es de servicio.
 * @return False: si el item es de servicio.
 */
public boolean isItmServicio(java.sql.Connection conn, int intCodItm){
  boolean blnRes=false;
  String strSql="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
      if(conn!=null){
         stmLoc=conn.createStatement(); 
        
         strSql="SELECT st_ser FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodItm;
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next())
            blnRes=(rstLoc.getString("st_ser").equalsIgnoreCase("N"))?true:false;
         rstLoc.close();
         stmLoc.close();
         rstLoc=null;
         stmLoc=null;
     }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
       catch(Exception Evt){ blnRes=false; mostarErrorException(Evt); }
    return blnRes;
  }   
    
 



/**
 * Funcion que se encarga de validar que no haya negativo en algun item que tenga movimiento.
 * 
 * @param conn Recibe la conexion de la base
 * @param intValGrpEmp si validad stock con grupo  1=Si  0=No
 * @return True: si no hay ningun problema
 * @return False:si hay problema con item en negativo 
 */
public boolean ejecutaVerificacionStock(java.sql.Connection conn, int intValGrpEmp ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   String strItmProStk=""; // Item con problema de stock
   try{
     if(conn!=null){  
     stmLoc=conn.createStatement();
     
     if(intTipConf==2) 
        strSql="SELECT x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvEmp.toString()+" ) AS x  LEFT JOIN tbm_inv AS inv ON(inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
     
     if(intTipConf==1) {
       if(intValGrpEmp==0) strSql="SELECT x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvEmp.toString()+" ) AS x LEFT JOIN tbm_inv AS inv ON(inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
       if(intValGrpEmp==1) strSql="SELECT x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+stbConStkInvGrp.toString()+" ) AS x LEFT JOIN tbm_inv AS inv ON(inv.co_emp="+INTCODEMPGRP+" AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_itm, inv.tx_codalt, inv.tx_nomitm "; 
     }
    // System.out.println(" >> "+ strSql );
     
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next())
        strItmProStk=strItmProStk+rstLoc.getString("tx_codalt")+" "+rstLoc.getString("tx_nomitm")+" //  "+rstLoc.getString("nd_stkact")+    "\n";
     rstLoc.close();
     rstLoc=null;
     
     System.out.println(strItmProStk);
     
     if(!strItmProStk.equals("")){
         //strItmProStk="Problemas de Inventario. \n"+strItmProStk;
         //MensajeInf(strItmProStk);
         blnRes=false;
     }
     stmLoc.close();
     stmLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false;  }     
    catch(Exception evt){  blnRes=false;  } 
  return  blnRes; 
}





public void MensajeInf(String strMsg){
   javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
   String strTit="Mensaje del sistema Zafiro";
   obj.showMessageDialog(jIntfra,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
   obj=null;
}





/**
 * Función que se encarga de devolver el costo de item sea esta del grupo o empresa dependiendo como se configure.
 *
 * @param conn Concexion del motor de la base de datos en que se trabaja
 * @param codigoEmpresa Código de la empresa con la que se trabaje
 * @param codigoItem  Código del item
 * 
 * @return El costo de item
 */
public double getCostoItm(java.sql.Connection conn, int codigoEmpresa, int codigoItem){
  double dblRes=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
       stmLoc=conn.createStatement();
       
      if(intTipConf==1) 
       strSql="SELECT nd_cosuni FROM tbm_inv WHERE co_emp="+INTCODEMPGRP+" AND co_itm = " +
       " (SELECT co_itm FROM tbm_equinv WHERE co_emp="+INTCODEMPGRP+" AND co_itmmae=(Select co_itmmae from tbm_equinv where co_emp ="+ codigoEmpresa +" and co_itm="+ codigoItem +"))";
       
      if(intTipConf==2) 
        strSql="SELECT nd_cosuni FROM tbm_inv WHERE co_emp="+codigoEmpresa+" AND co_itm ="+codigoItem+"";
       
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
          dblRes=rstLoc.getDouble("nd_cosUni");
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
       }
    }}catch(java.sql.SQLException e){ mostarErrorSqlException(e); }
      catch(Exception e){ mostarErrorException(e); }
     return dblRes;
  }
   

    
/**
 * Función que validad si es nulo asignas "" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda 
 * @return "" si el nulo ó el valor que tiene
 */
public String getStringDatoValidado( Object objTbl ){
 String strVar="";
 try{
    if(objTbl==null) strVar="";
    else if(objTbl.toString().equals("")) strVar="";
    else strVar=objTbl.toString();
  }catch(Exception e){ mostarErrorException(e); }
 return strVar;   
}

/**
 * Función que validad si es nulo asignas "0" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda 
 * @return "0" si el nulo ó  vacion "" el valor que tiene
 */
public String getIntDatoValidado( Object objTbl ){
 String strVar="0";
 try{
    if(objTbl==null) strVar="0";
    else if(objTbl.toString().equals("")) strVar="0";
    else strVar=objTbl.toString();
  }catch(Exception e){ mostarErrorException(e); }
 return strVar;   
}




/**
 * Funcion que se encarga de truncar el valor recibido
 * @param dblval  valor recibido
 * @param intNumDec  numero de decilames
 * @return  retorna el valor truncado
 *  <br> ejemplo <br>
 *   25.52  -> 25.00
 */
public double Truncar(double dblval, int intNumDec){
 double dlbRes=0;
 try{
  if(dblval > 0)
    dblval = Math.floor(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);
  else
    dblval = Math.ceil(dblval * Math.pow(10,intNumDec))/Math.pow(10,intNumDec);

  dlbRes=dblval;

 }catch(Exception e){ dblval=-1;  mostarErrorException(e); }
  return dlbRes;
}





public void enviarCorreo(String strCorEleTo, String strMsExcp ){
  try
    {

        String server = "mail.tuvalsa.com";
        String userName = "zafiro";
        String password = "#tuv*sis28/10=";
        String fromAddres = "zafiro@tuvalsa.com";
        String toAddres = strCorEleTo;

        String cc = "";
        String bcc = "";
        boolean htmlFormat = false;
        String subject = "Sistema Zafiro:   ";
        String body = strMsExcp;

        

        sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,
                     htmlFormat, subject, body);

        }catch (Exception e) {  e.printStackTrace();    }
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

                    MimeMultipart multipart= new MimeMultipart();
                    multipart.addBodyPart(cuerpoCorreo);

                    message.setContent(multipart);

                    message.saveChanges();


            Transport.send(message);

           blnRes=true;

       }catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }

      return blnRes;
    }



static class MyAuthenticator extends Authenticator {
 PasswordAuthentication l = new PasswordAuthentication("zafiro", "#tuv*sis28/10=");
 protected PasswordAuthentication getPasswordAuthentication() {
 return l;
 }
}



  
/**
 * Funcion que permite saber si el ingreso que corresponde al egreso esta cofirmado o no y actuliza la cantidad
 * nunca recividad
 * @param conn
 * @param strCodEmp
 * @param strCodLoc
 * @param strCodTipDoc
 * @param strCodDoc
 * @param strDocReg
 * @param ndCanNunEnv
 * @return
 */
public boolean _getVerificaConfirmacionIng(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunEnv  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();


      strSql="select * from ( select ( nd_can - "
      + " ( case when nd_cancon is null then 0 else nd_cancon end +  "
      + "   case when nd_cannunrec is null then 0 else nd_cannunrec end +  "
      + "   case when nd_cantotmalest is null then 0 else nd_cantotmalest end  "
      + " ) ) as totconf from tbm_detmovinv "
      + " where  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
      + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" "
      + "  ) as x  where  "+ndCanNunEnv+"  <=  totconf    ";
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){

           strSql="UPDATE tbm_detmovinv SET nd_cannunrec="+ndCanNunEnv+" "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" ";
           strSql+=" ; UPDATE tbm_cabmovinv SET st_coninv='E' "
           + " WHERE  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+"  ";

           stmLoc01.executeUpdate(strSql);

         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { blnRes=false; System.out.println(strSql);  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false; System.out.println(strSql); mostarErrorException(e);  }
  return blnRes;
}



/**
 * Funcion que permite saber si ya tiene ingresado cantidad nunca recibida que corresponde a una confirmacion de
 * egreso que mercaderia que nunca enviarar .
 * @param conn
 * @param strCodEmp
 * @param strCodLoc
 * @param strCodTipDoc
 * @param strCodDoc
 * @param strDocReg
 * @param ndCanNunRec
 * @return
 */
public boolean _getVerificaCanNunRecIng(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strDocReg, double ndCanNunRec  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="select nd_cannunrec from tbm_detmovinv "
      + " where  co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
      + " and co_doc="+strCodDoc+"  and co_reg="+strDocReg+" "
      + " and   nd_cannunrec <= "+ndCanNunRec+"  ";
      System.out.println(""+strSql );
      rst=stmLoc.executeQuery(strSql);
      if(rst.next()){

         blnRes=true;
      }
      rst.close();
      rst=null;

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}




}
