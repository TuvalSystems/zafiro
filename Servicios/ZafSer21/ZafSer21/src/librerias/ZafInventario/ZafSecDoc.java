/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package Librerias.ZafInventario;

/**
 *
 * @author jayapata   Mail: jaya_gar@hotmail.com     
 * 
 * @Version: 0.1
 */
public class ZafSecDoc {
   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafUtil.ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;
   javax.swing.JDialog jDialo=null;
   int INTCODEMPGRP=0;
   java.sql.Connection conGlo;
  
      /** Creates a new instance of ZafInv */
    public ZafSecDoc(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }        
    }
 
    public ZafSecDoc(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }        
    } 
    
      
    

/**
 * Funcion que abre conección con la base de datos.
 * @param INTCODREGCEN 1 si hay coneccion remota 0 no hay
 */
public void abrirConnBD(int INTCODREGCEN){
 try{
   if(INTCODREGCEN != 0)
       conGlo=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(INTCODREGCEN), objZafParSis.getUsuarioBaseDatos(INTCODREGCEN), objZafParSis.getClaveBaseDatos(INTCODREGCEN));
    else
        conGlo=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

    
  }catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
     catch(Exception e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
}
   

/**
 * Funcion que permite cerrar la coneccion de la base.
 */
public void cerrarConnBD(){
 try{
   if(conGlo != null){
      conGlo.close();
      conGlo=null;
  }}catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
     catch(Exception e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
}


/**
 * Esta funcion permite extraer el numero de secuencia de documento.
 * @return numero de secuencia de la grupo.
 */
public int getNumSecDoc(int intCodEmp ){
 int intNumSec=0;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstloc;
 String strSql="";
 try{

      stmLoc=conGlo.createStatement();
      stmLoc01=conGlo.createStatement();

      strSql="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS numsec " +
      " FROM tbm_emp WHERE co_emp="+intCodEmp;
      rstloc=stmLoc.executeQuery(strSql);
      if(rstloc.next())
          intNumSec=rstloc.getInt("numsec");
      rstloc.close();
      rstloc=null;

      strSql="UPDATE tbm_emp SET  ne_secUltDocTbmCabMovInv="+intNumSec+" WHERE co_emp="+intCodEmp;
      System.out.println("strSql:  "+ strSql );
      stmLoc01.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

   }catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
    catch(Exception e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
return intNumSec;
}












}
