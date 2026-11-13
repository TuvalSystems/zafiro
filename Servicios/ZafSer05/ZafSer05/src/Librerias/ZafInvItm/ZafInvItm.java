/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package Librerias.ZafInvItm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;
  
/**
 *
 * @author jayapata   Mail: jaya_gar@hotmail.com     
 * 
 * @Version: 1.1  
 */ 
public class ZafInvItm {
    
    Librerias.ZafParSis.ZafParSis objParSis;
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
   
    private int intCodLocGrp=1;
    private int intCodCfgCorEleCfgGen=28;
    
    private String server="", userName="", password="", fromAddres="", puerto="", toAddres="";   
 
      /** Creates a new instance of ZafInv */
    public ZafInvItm(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{  
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }        
    }
 
    public ZafInvItm(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }        
    } 
    
    public ZafInvItm(Librerias.ZafParSis.ZafParSis obj){
       try{
           this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
           objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){   }
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
    public void generaQueryStock(int intCodEmp, int intCodItm, int intCodBod, double dblCan, int intTipMov, String strEstFisBod, String strMerIngEgr, String strTipIngEgr, int intTipMovFisBod ){
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

    public boolean enviarCorreo(String strCorEleTo, String message ){
        boolean blnRes=false;
        boolean htmlFormat = false;
        String strSQL="";
        String CC="", CCO="";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;  
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_loc="+intCodLocGrp+" AND co_not="+intCodCfgCorEleCfgGen+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem"); // "zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    toAddres = strCorEleTo;
                    puerto = rstLoc.getString("ne_pueSerCor");
                    if(sendMail(server, userName, password, fromAddres, toAddres, CC, CCO, htmlFormat, "Sistema Zafiro: ", message)){
                        blnRes=true;
                    }                    
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;            
        }
        catch (java.sql.SQLException ex) {
            System.err.println("ERROR " + ex.toString());
            blnRes=false;
        }  
        catch (Exception e) {  
            System.err.println("ERROR "+ e.toString());
            blnRes=false;
        } 
        return blnRes;
    }
    
    
    public boolean sendMail(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) 
    {
        boolean blnRes=false;
        try{
           Properties props = System.getProperties();
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", puerto);
           props.put("mail.smtp.auth", "true");  

            // Get session
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() { 
                        return new PasswordAuthentication(userName, password);
                    }
                }
            );
            
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject); 

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
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

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
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
