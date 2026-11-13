/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumIngEgr;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumPoj.Provisiones;
import Librerias.ZafUtil.ZafUtil;
import RecursosHumanos.ZafRecHum09.ZafRecHum09;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Vector;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Roberto Flores
 * Guayaquil, 08 de Abril del 2013
 */
public class ZafRecHumIngEgr {
    
    private ZafParSis objParSis;
    private ZafUtil objUti; 
    private Object obj;
    
    private Vector vecDat, vecCab, vecReg;
    private String strVer="v1.0";
    
    public ZafRecHumIngEgr(ZafParSis objParSis, ZafUtil objUti, Object obj){
        this.objParSis=objParSis;
        this.objUti=objUti;
        this.obj=obj;
    }
    
    public ZafRecHumIngEgr(ZafParSis objParSis, ZafUtil objUti){
        this.objParSis=objParSis;
        this.objUti=objUti;
    }
     
    /*public boolean generaIngresoEgreso(java.sql.Connection conn,int TipoDoc,int codloc ,int cod_tra,double valor,int rubro,int coddoc) 
    {
        boolean blnRes = true;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            int intCodDocEgrIng = 0;

            try {
                
                System.out.println("🔁 Iniciando generaIngresoEgreso()");
                System.out.println("📥 Parámetros recibidos:");
                System.out.println("TipoDoc=" + TipoDoc + ", codloc=" + codloc + ", cod_tra=" + cod_tra + ", valor=" + valor + ", rubro=" + rubro + ", coddoc=" + coddoc);
                
                con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con != null) {
                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();

                    String strSQL = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_doc FROM tbm_cabingegrprgtra WHERE "
                            + " co_emp=" + objParSis.getCodigoEmpresa();
                    System.out.println("📄 Ejecutando SQL para obtener co_egr: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intCodDocEgrIng = rstLoc.getInt("co_doc");
                         System.out.println("✅ Código generado para co_doc (egreso): " + intCodDocEgrIng);
                    }
                    rstLoc.close();
                    rstLoc = null;

                    System.out.println("📌 Llamando a insertarCab()");
                    if (insertarCab (con, intCodDocEgrIng,TipoDoc,cod_tra,valor,rubro )) {
                        System.out.println("✅ insertarCab() exitoso.");
                        
                            System.out.println("📌 Llamando a insertarDet()");
                            if (insertarDet(con, intCodDocEgrIng,  valor)) {
                                System.out.println("✅ insertarDet() exitoso.");
                                
                                System.out.println("📌 Llamando a insertarDetRelEgr()");
                                if (insertarDetRelEgr(con,intCodDocEgrIng,TipoDoc,codloc,coddoc)){
                                    System.out.println("✅ insertarDetRelEgr() exitoso. Haciendo commit...");
                                    con.commit();
                                    //new RRHHDao(objUti, objParSis).callServicio9();
                                }else{
                                System.out.println("❌ insertarDetRelEgr() falló. Rollback.");
                                con.rollback();  
                                blnRes = false;
                            }
                               
                            } else {
                                System.out.println("❌ insertarDet() falló. Rollback.");
                                con.rollback();  
                                blnRes = false;
                            }
                         
                    } else {
                        System.out.println("❌ insertarCab() falló. Rollback.");
                        con.rollback();
                        blnRes = false;
                    }

                    con.close();
                    con = null;
                }
                System.out.println("❌ No se pudo obtener conexión.");
            } catch (java.sql.SQLException Evt) {
                System.out.println("❌ SQLException en generaIngresoEgreso():");
                Evt.printStackTrace();
                blnRes = false;
             } catch (Exception Evt) {
                System.out.println("❌ Exception general en generaIngresoEgreso():");
                Evt.printStackTrace();
                blnRes = false;
             } finally {
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                } catch (Throwable ignore) {
                }
            }
            System.out.println("🔚 Fin de generaIngresoEgreso(). Resultado: " + blnRes);
            return blnRes;
    }*/
    
    public boolean generaIngresoEgreso(java.sql.Connection conn, int TipoDoc, int codloc, int cod_tra, double valor, int coddoc, char tipo) {
    boolean blnRes = true;
    java.sql.Connection con = null;
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;
    int intCodDocEgrIng = 0;

    // Rubros definidos internamente
    int intRubVac = 17; // Rubro de egreso
    int intRubIng = 40; // Rubro de ingreso

    try {
        System.out.println("Iniciando generaIngresoEgreso()");
        con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

        if (con != null) {
            con.setAutoCommit(false);
            stmLoc = con.createStatement();

            String strSQL = "SELECT COALESCE(MAX(co_egr)+1, 1) AS co_doc FROM tbm_cabingegrprgtra WHERE co_emp=" + objParSis.getCodigoEmpresa();
            System.out.println("Ejecutando SQL para obtener co_doc: " + strSQL);
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next()) {
                intCodDocEgrIng = rstLoc.getInt("co_doc");
                System.out.println("✅ Código generado para co_doc (egreso/ingreso): " + intCodDocEgrIng);
            }

            if (tipo =='G'){
                System.out.println("Vac GOZ - "+tipo);
                // EGRESO: Rubro 17                
                System.out.println("Insertando EGRESO con rubro 17");
                if (insertarCab(con, intCodDocEgrIng, TipoDoc, cod_tra, valor, intRubVac, coddoc)) {
                    if (insertarDet(con, intCodDocEgrIng, valor,coddoc)) {
                        if (insertarDetRelEgr(con, intCodDocEgrIng, TipoDoc, codloc, coddoc)) {
                            //con.commit();
                            System.out.println("Egreso registrado y confirmado (Rubro 17)");
                        } else {
                            con.rollback();
                            System.out.println("❌ Falló insertarDetRelEgr() en EGRESO");
                            return false;
                        }
                    } else {
                        con.rollback();
                        System.out.println("❌ Falló insertarDet() en EGRESO");
                        return false;
                    }
                } else {
                    con.rollback();
                    System.out.println("❌ Falló insertarCab() en EGRESO");
                    return false;
                }

                con.commit();
                 
            }
            if (tipo =='P'){
               System.out.println("Vac PAG - "+tipo);
              // INGRESO: Rubro 40
                
                System.out.println("Insertando INGRESO con rubro 40 - cod: " + intCodDocEgrIng);
                if (insertarCab(con, intCodDocEgrIng, TipoDoc, cod_tra, valor, intRubIng, coddoc)) {
                    if (insertarDet(con, intCodDocEgrIng, valor, coddoc)) {
                        if (insertarDetRelEgr(con, intCodDocEgrIng, TipoDoc, codloc, coddoc)) {
                            //con.commit();
                            System.out.println("Ingreso registrado y confirmado (Rubro 40)");
                        } else {
                            con.rollback();
                            System.out.println("Falló insertarDetRelEgr() en INGRESO");
                            return false;
                        }
                    } else {
                        con.rollback();
                        System.out.println("Falló insertarDet() en INGRESO");
                        return false;
                    }
                } else {
                    con.rollback();
                    System.out.println("Falló insertarCab() en INGRESO");
                    return false;
                }
              con.commit();
                
            }
            
            con.close();
            con = null;
        }

    } catch (java.sql.SQLException Evt) {
        blnRes = false;
        Evt.printStackTrace();
    } catch (Exception Evt) {
        blnRes = false;
        Evt.printStackTrace();
    } finally {
        try { if (rstLoc != null) rstLoc.close(); } catch (Throwable ignore) {}
        try { if (stmLoc != null) stmLoc.close(); } catch (Throwable ignore) {}
        try { if (con != null) con.close(); } catch (Throwable ignore) {}
    }

    System.out.println("🟢 Fin de generaIngresoEgreso() -> Resultado: " + blnRes);
    return blnRes;
}
 
    
    
       public boolean insertarCab (java.sql.Connection con, int intCodDoc, int tipdoc,int co_tra,double valor, int rubro, int intCodDocSolVac) {
            boolean blnRes = false;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.Statement stmLocAut = null;
            java.sql.ResultSet rstLocAut = null;
            try {

                stmLoc = con.createStatement();
                
                LocalDate fechaActual = LocalDate.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String strFechaIniVac = obtenerFechaInicioVacacion(con, intCodDocSolVac);

                if (strFechaIniVac == null || strFechaIniVac.trim().equals("")) {
                    throw new Exception("No se encontró la fecha de inicio de vacaciones para la solicitud N° " + intCodDocSolVac);
                }

                LocalDate fechaVacacion = LocalDate.parse(strFechaIniVac, formatter);
                LocalDate primerDiaMesVacacion = fechaVacacion.withDayOfMonth(1);

                String strFechaDoc = fechaActual.format(formatter);
                String strFechaIni = primerDiaMesVacacion.format(formatter);
  
        
                String strTx_tipEgrPrg = "";
                strTx_tipEgrPrg = "D";
                String strTx_tipCuo = "";
                strTx_tipCuo = "M";  
                String strSt_Reg = "A"; //registo activo
                String strNe_NumCuo = null;
                strNe_NumCuo = "1";
               

                strSql = "INSERT INTO tbm_cabingegrprgtra(co_emp, co_egr, co_tra, co_rub, fe_doc, tx_tipegrprg, fe_priCuo, "
                        + "fe_ultCuo, tx_obscan, nd_valegrprg, ne_numcuo, tx_tipcuo, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, fe_utiPag) "
                        + "VALUES ( "
                        + objParSis.getCodigoEmpresa() + " , "
                        + intCodDoc + " , "
                        + co_tra + " ,"+ rubro +", "
                        + "'" + strFechaDoc + "' , "
                        + objUti.codificar(strTx_tipEgrPrg) + " , "
                        + "'" + strFechaIni + "' , "
                        + " null , "
                        + " null , "
                         + objUti.parseDouble(valor) + " , "
                        + strNe_NumCuo + " , "
                        + objUti.codificar(strTx_tipCuo) + " , "
                        + objUti.codificar("Generado automaticamente desde la ventana de autorizaciones de Vacaciones. Solicitud N° " + intCodDocSolVac) + " , "
                        + objUti.codificar("") + ", "
                        + objUti.codificar(strSt_Reg) + " , "
                        + " current_timestamp , "
                        + " null , "
                        + objParSis.getCodigoUsuario() + " , "
                        + " null, current_timestamp )";
                System.out.println("Insert cabecera: "+strSql);
                 stmLoc.executeUpdate(strSql);

                blnRes = true;

            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                System.err.println("SQLException en insertarCab(): " + Evt.getMessage());
             } catch (Exception Evt) {
                blnRes = false;
                 System.err.println("Exception en insertarCab(): " + Evt.getMessage());
             } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLocAut.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLocAut.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }
      
       public boolean insertarDet(java.sql.Connection con, int intCodDoc, double valor, int intCodDocSolVac) {
        boolean blnRes = true;
        String strSql = "";
        java.sql.Statement stmLoc = null;

        try {
            stmLoc = con.createStatement();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String strFechaIniVac = obtenerFechaInicioVacacion(con, intCodDocSolVac);

            if (strFechaIniVac == null || strFechaIniVac.trim().equals("")) {
                throw new Exception("No se encontró la fecha de inicio de vacaciones para la solicitud N° " + intCodDocSolVac);
            }

            LocalDate fechaVacacion = LocalDate.parse(strFechaIniVac, formatter);
            LocalDate primerDiaMesVacacion = fechaVacacion.withDayOfMonth(1);

            String strFechaCuo = primerDiaMesVacacion.format(formatter);

            String strStReg = "A";

            strSql = "INSERT INTO tbm_detingegrprgtra(co_emp, co_egr, co_reg, fe_cuo, nd_valcuo, st_reg) "
                    + "VALUES ( "
                    + objParSis.getCodigoEmpresa() + " , "
                    + intCodDoc + " , 1,"
                    + "'" + strFechaCuo + "',"
                    + valor + ","
                    + objUti.codificar(strStReg)
                    + " )";

            System.out.println("Insert detalle: " + strSql);
            stmLoc.executeUpdate(strSql);

        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            System.err.println("SQLException en insertarDet(): " + Evt.getMessage());
        } catch (Exception Evt) {
            blnRes = false;
            System.err.println("Exception en insertarDet(): " + Evt.getMessage());
        } finally {
            try {
                if (stmLoc != null) stmLoc.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
      
        
        
        
        public boolean insertarDetRelEgr(java.sql.Connection con, int intCodDoc,int tipdoc ,int codloc,int coddoc ) { //con,intCodDoc,TipoDoc,codloc
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            try {
                stmLoc = con.createStatement();
  
                    strSql = "INSERT INTO tbr_pagvacrolpag(co_emp, co_loc, co_tipDoc, co_doc, st_reg, co_egr, fe_ing, co_usring) "
                            + "VALUES ( "
                            + objParSis.getCodigoEmpresa() + " , " 
                            + codloc + "," 
                            + tipdoc + ","
                            + coddoc + " , 'A',"
                            + intCodDoc + ", current_timestamp, " 
                            + objParSis.getCodigoUsuario()
                            + " )";
                     System.out.println("insertarDetRelEgr: "+strSql);
                     stmLoc.executeUpdate(strSql);
   
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
             } catch (Exception Evt) {
                blnRes = false;
             } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                 } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }
        
        private String obtenerFechaInicioVacacion(java.sql.Connection con, int intCodDocSolVac) throws Exception {
            String strFecha = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try {
                stmLoc = con.createStatement();

                String strSql = "";
                strSql += "SELECT fe_inivacgoz ";
                strSql += "FROM tbm_detsolvac ";
                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "  AND co_loc = " + objParSis.getCodigoLocal() + " ";
                strSql += "  AND co_tipdoc = 322 ";
                strSql += "  AND co_doc = " + intCodDocSolVac + " ";
                strSql += "  AND co_reg = 1 ";

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    strFecha = rstLoc.getString("fe_inivacgoz");
                }

            } finally {
                if (rstLoc != null) rstLoc.close();
                if (stmLoc != null) stmLoc.close();
            }

            return strFecha;
        }
        
    
}