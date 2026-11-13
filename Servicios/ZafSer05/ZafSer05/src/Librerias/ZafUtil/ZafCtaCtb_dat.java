/*
 * ZafCtaCtb.java
 *
 * Created on 8 de diciembre de 2004, 16:16
 *Version 2.1
 */

package Librerias.ZafUtil;

/**   
 * 
 * @author  IdiTrix
 */
public class ZafCtaCtb_dat {
         /*
         * Contiene true si es ke no se encontro todas las cuentas para saber si devolver null o no
         */ 
        private boolean isEmpty = true; 
        /*
         * Datos las Cuentas
         */
        private int intCtaHab    = 0;  // CLientes 
        
        private int intCtaDescVentas  = 0;  // Descuento en Ventas

        private int intCtaDeb      = 0;  // Ventas

        private int intCtaIvaVentas   = 0;  // Iva en Ventas

        private int intCtaIvaCompras  = 0;  //Iva en COMPRAS

        private int intCtaCostoVtas   = 0;  // Costo de Ventas

        private int intCtaExistencia  = 0;  // Existencias en Bodega

        private int intCtaRetFue      = 0;  // Retencion en la fuente

        private int intCtaRetIva      = 0;  // Retencion de Iva 
        
        
        private String tx_codctadeb  = "";  
        private String tx_deslardeb  = "";  
        
        private String tx_codctades  = "";  
        private String tx_deslardes  = "";  
        
        private String tx_codctahab  = "";  
        private String tx_deslarhab  = "";  
        
        private String tx_codctaivaven  = "";  
        private String tx_deslarivaven  = "";  
        
        /*
         * Variables Sql
         */
        
      
        private java.sql.Statement stmCtaEmp;   //Statement para la sacar las cuentas de tabla empresas
        private java.sql.ResultSet rstCtaEmp;     //Resultset que tendra los datos 

        private java.sql.Statement stmCtaLoc;   //Statement para la sacar las cuentas de tabla Local
        private java.sql.ResultSet rstCtaLoc;   //Resultset que tendra los datos 

        private java.sql.Statement stmCtaBod;   //Statement para la sacar las cuentas de tabla Bodega
        private java.sql.ResultSet rstCtaBod;   //Resultset que tendra los datos 

        private java.sql.Statement stmCtaTipDoc; //Statement para la sacar las cuentas de tabla Tipos Documentoes
        private java.sql.ResultSet rstCtaTipDoc; //Resultset que tendra los datos
        
        private java.sql.Statement stmCta;   //Statement para obtener numero de cuenta y nombre
        private java.sql.ResultSet rstCta;   //Resultset que tendra los datos 
        
        private Librerias.ZafParSis.ZafParSis objZafParSis;
    
    /** Creates a new instance of ZafCtaCtb */
        public ZafCtaCtb_dat(Librerias.ZafParSis.ZafParSis objZafParSis, java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCoTipDoc) {
               isEmpty = true;
               this.objZafParSis = objZafParSis;
               try{
                   
               
                    String strSqlEmp = "select * from tbm_emp where " +
                                        "co_emp = " +intCodEmp;
                    
                    //System.out.println("CUENTA EMPRESA "+ strSqlEmp );
                    
                    String strSqlLoc = "select * from tbm_loc where " +
                                        "co_emp = " + intCodEmp+ " and " +
                                        "co_loc = " +intCodLoc;

                     //System.out.println("CUENTA LOCAL "+ strSqlLoc );
                     
                    String strSqlBod = "select * from tbm_bod where " +
                                        "co_bod = " + intCodLoc;

                   // System.out.println("CUENTA BODEGA "+ strSqlBod );
                     
                    String strSqlTipDoc = "select * from tbm_cabtipdoc where " +
                                        "co_emp = " +intCodEmp + " and " +
                                        "co_loc = " + intCodLoc+ " and " +
                                        "co_tipdoc=" + intCoTipDoc;  

 
                     //System.out.println("CUENTA TIPODOCUMENTO "+ strSqlTipDoc );
                    
                    
                    stmCtaEmp = conn.createStatement();
                    rstCtaEmp = stmCtaEmp.executeQuery(strSqlEmp);

                    stmCtaBod = conn.createStatement();
                    rstCtaBod = stmCtaBod.executeQuery(strSqlBod);
                    
                    stmCtaLoc = conn.createStatement();
                    rstCtaLoc = stmCtaLoc.executeQuery(strSqlLoc);
                    
                    stmCtaTipDoc = conn.createStatement();
                    rstCtaTipDoc = stmCtaTipDoc.executeQuery(strSqlTipDoc);
                    
                    /*
                     * Significa ke encontro datos de empresa, bodega , local, TipDoc
                     */
                    if(rstCtaEmp.next() && rstCtaLoc.next() && rstCtaBod.next() && rstCtaTipDoc.next()){
                        // Si todos trajeron algun registro significa ke ya no esta vacia la clase
                        isEmpty = false;
                        
                        System.out.println("Ingreso Cta ");

                        /*
                         * Procedemos a cargar las cuentas
                         */
                        
                         intCtaHab    = rstCtaTipDoc.getInt("co_ctahab");  // CLientes 
                         intCtaDescVentas  = rstCtaLoc.getInt("co_ctadesven");  // Descuento en Ventas
                         intCtaDeb      = rstCtaTipDoc.getInt("co_ctadeb");  // Ventas
                         intCtaIvaVentas   = rstCtaEmp.getInt("co_ctaivaven");  // Iva en Ventas
                         intCtaIvaCompras   = rstCtaEmp.getInt("co_ctaivacom");  // Iva en Ventas
                         intCtaCostoVtas   = rstCtaLoc.getInt("co_ctacosven");  // Costo de Ventas
                         intCtaExistencia  = rstCtaBod.getInt("co_ctaexi");  // Existencias en Bodega
                         intCtaRetFue      = rstCtaEmp.getInt("co_ctaretfteven");  // Retencion en la fuente
                         intCtaRetIva      = rstCtaEmp.getInt("co_ctaretivaven");  // Retencion de Iva 
                         
                         String sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp+ " and " +
                                          "co_cta = " + intCtaDeb;
                          stmCta = conn.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctadeb=rstCta.getString(1);
                             tx_deslardeb=rstCta.getString(2);
                          }
                         
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp + " and " +
                                          "co_cta = " + intCtaDescVentas;
                          stmCta = conn.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctades=rstCta.getString(1);
                             tx_deslardes=rstCta.getString(2);
                          }
                          
                          
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp + " and " +
                                          "co_cta = " + intCtaHab;
                          stmCta = conn.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctahab=rstCta.getString(1);
                             tx_deslarhab=rstCta.getString(2);
                          }
                          
                           sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp+ " and " +
                                          "co_cta = " + intCtaIvaVentas;
                          stmCta = conn.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctaivaven=rstCta.getString(1);
                             tx_deslarivaven=rstCta.getString(2);
                          }
                         
                    }else   System.out.println("No  Ingreso Cta ");
                     

                    rstCta.close();
                    rstCtaEmp.close();
                    rstCtaLoc.close();
                    rstCtaBod.close();
                    rstCtaTipDoc.close();
                    
                    stmCta.close();
                    stmCtaEmp.close();
                    stmCtaLoc.close();
                    stmCtaBod.close();
                    stmCtaTipDoc.close();
                    
                   
               }
               catch(java.sql.SQLException Evt){
                     isEmpty = true;
               }

               catch(Exception Evt){
                     isEmpty = true;
               }
    }
    
        //**************COSAS NUEVAS IMPLEMENTADAS POR JAVIER AYAPATA
        public String getCtaCodDeb(){
            return (isEmpty)?"":tx_codctadeb;
        }
         public String getCtaNomDeb(){
            return (isEmpty)?"":tx_deslardeb;
        }
           
         public String getCtaCodDes(){
            return (isEmpty)?"":tx_codctades;
        }
         public String getCtaNomDes(){
            return (isEmpty)?"":tx_deslardes;
        }
         
         public String getCtaCodHab(){
            return (isEmpty)?"":tx_codctahab;
        }
         public String getCtaNomHab(){
            return (isEmpty)?"":tx_deslarhab;
        } 
         
        public String getCtaCodIvaVen(){
            return (isEmpty)?"":tx_codctaivaven;
        }
         public String getCtaNomIvaVen(){
            return (isEmpty)?"":tx_deslarivaven;
        }   
         
       //***********************************  
         
        public int getCtaHab (){
            return (isEmpty)?-1:intCtaHab;
        }
        public int getCtaDescVentas (){
            return (isEmpty)?-1:intCtaDescVentas;
        }
        public int getCtaDeb(){
            return (isEmpty)?-1:intCtaDeb;
        }
        public int getCtaIvaVentas(){
            return (isEmpty)?-1:intCtaIvaVentas;
        }
        public int getCtaIvaCompras(){
            return (isEmpty)?-1:intCtaIvaCompras;
        }
        public int getCtaCostoVtas(){
            return (isEmpty)?-1:intCtaCostoVtas;
        }
        public int getCtaExistencia(){
            return (isEmpty)?-1:intCtaExistencia;
        }
        public int getCtaRetFue(){
            return (isEmpty)?-1:intCtaRetFue;
        }
        public int getCtaRetIva(){
            return (isEmpty)?-1:intCtaRetIva;
        }
//        public String getNomCta(int intCodCta){
//            String strNomCta = null;
//             try{
//                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                  String strSql = "select * from tbm_placta where " +
//                                  "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
//                                  "co_cta = " + intCodCta ;
//                  java.sql.Statement stm = con.createStatement();
//                  java.sql.ResultSet rst = stm.executeQuery(strSql);
//                  if(rst.next()){
//                    strNomCta = rst.getString("tx_deslar");
//                  }
//                  rst.close();
//                  stm.close();
//                  con.close();
//                  con=null;
//             }catch(java.sql.SQLException Evt){
//                  return null;
//              }
//
//              catch(Exception Evt){
//                  return null;
//              }
//           return strNomCta;
//        }
//
//        public String getCodAltCta(int intCodCta){
//             try{
//                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                  String strSql = "select tx_codcta from tbm_placta where " +
//                                  "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
//                                  "co_cta = " + intCodCta ;
//
//
//                 // System.out.println("GETCODALTCTA >> "+ strSql );
//
//                  java.sql.Statement stm = con.createStatement();
//                  java.sql.ResultSet rst = stm.executeQuery(strSql);
//                  if(rst.next()){
//                    return rst.getString("tx_codcta");
//                  }
//                  rst.close();
//                  stm.close();
//                  con.close();
//
//             }catch(java.sql.SQLException Evt){
//                  return null;
//              }
//
//              catch(Exception Evt){
//                  return null;
//              }
//           return null;
//        }
}
