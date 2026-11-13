/*
 * Clientes_dat.java
 *
 * Created on 25 de noviembre de 2004, 16:47
 */

package Librerias.ZafUtil;

/**
 * Clase para tener informacion de un cliente <br>
 * Recibe como parametro el codigo del cliente y obtiene la informacion del mismo<br>
 * si el codigo no existe o hubo algun error se puede conprobar con el metodo <strong>isEmpty</strong><br>
 * que retorna true si la clase esta vacia y false si la clase obtuvo datos
 * @author IdiTrix
 */
public class ZafCliente_dat {
        Librerias.ZafParSis.ZafParSis objZafParSis;
        Librerias.ZafUtil.ZafUtil objUti;
        /*
         * Contiene true si es ke no se encontro ese cliente para saber si devolver null o no
         */ 
        private boolean isEmpty = true; 
        /*
         * Datos del CLiente
         */
        private int codigo = 0;
        private String nombre = "";
        private String direccion = "";
        private String telefono = "";
        private String identificacion  = "";
        private int    codciudad  = 0;
        private double maxDescuento = 0;
        private char tipPer;
        private int co_tipper;
        private int ne_diagra;
        private int intCodEmp;
        private int intCodMaeEqu;
         /* Variables Sql
         */
        private java.sql.Connection conCli;  //Coneccion a la base donde se encuentra el cliente
        private java.sql.Statement stmCli;   //Statement para la cliente
        private java.sql.ResultSet rstCli;   //Resultset que tendra los datos 
        
        
        /**
         * Construye un nuevo objeto del tipo CLiente_dat <br>el cual contendra los datos de un cliente enviado como parametro
         * @param cod_cli Codigo de cliente para el que se obtendran los datos
         */        
        public ZafCliente_dat(int cod_cli, Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;
            intCodEmp = objZafParSis.getCodigoEmpresa();
            objUti =  new Librerias.ZafUtil.ZafUtil ();
            setCliente(cod_cli);            
        }

        /**
         * Construye un nuevo objeto del tipo CLiente_dat <br>el cual contendra los datos de un cliente enviado como parametro
         */        
        public ZafCliente_dat(Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;
            intCodEmp = objZafParSis.getCodigoEmpresa();
            objUti =  new Librerias.ZafUtil.ZafUtil ();
        }
        /**
         * @autor: jsalazar
         */
        public ZafCliente_dat(int intparEmp,int cod_cli, Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;           
            intCodEmp= intparEmp;
            objUti =  new Librerias.ZafUtil.ZafUtil ();
            setCliente(intparEmp,cod_cli);
        }
        public ZafCliente_dat(Librerias.ZafParSis.ZafParSis objZafParSis,String strCli){
            this.objZafParSis = objZafParSis;
            intCodEmp = objZafParSis.getCodigoEmpresa();
            objUti =  new Librerias.ZafUtil.ZafUtil ();
            setCliente(objZafParSis.getCodigoEmpresa(),strCli);
        }

        /**
         * Recupera y carga a la clase Cliente _dat, la informacion respectiva al<br>
         * al cliente enviado como parametro
         * @return <pre>
         * true  .- Si los datos del cliente fueron cargados con exito
         * false .- Si el codigo de cliente no se encontro o no se pudo cargar los datos
         * </pre>
         * @param cod_cli Codigo del cliente del cual se recuperaran los datos
         */        
        public boolean setCliente(int cod_cli){
               codigo = cod_cli;
               isEmpty = true;
               
               try{
                   conCli=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de clientes y obtener 
                *  sus datos .
                */
               
                    String strSql = "select * from tbm_cli where " +
                                        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "co_cli = " + codigo;
                    stmCli = conCli.createStatement();
                    rstCli = stmCli.executeQuery(strSql);
                    
                    /*
                     * Significa ke encontro el cliente
                     */
                    if(rstCli.next()){
                        nombre = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                        direccion = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                        telefono = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                        identificacion  = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                        codciudad  = rstCli.getInt("co_ciu");
                        maxDescuento  = rstCli.getDouble("nd_maxDes");
                        tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                        co_tipper = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                        ne_diagra = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                        intCodMaeEqu = buscarEquiCliente();
                        isEmpty = false;
                    }
                    rstCli.close();
                    stmCli.close();
                    conCli.close();
               }
               catch(java.sql.SQLException Evt){
                   System.out.println(Evt.toString());
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }

               catch(Exception Evt){
                   System.out.println(Evt.toString());
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }
               return true;
        }
        /**
         * @autor: jsalazar
         */
        public boolean setCliente(int intCodEmp,int cod_cli){
               codigo = cod_cli;
               isEmpty = true;
               
               try{
                   conCli=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de clientes y obtener 
                *  sus datos .
                */
               
                    String strSql = "select * from tbm_cli where " +
                                        "co_emp = " + intCodEmp + " and " +
                                        "co_cli = " + codigo;
                    stmCli = conCli.createStatement();
                    rstCli = stmCli.executeQuery(strSql);
                    
                    /*
                     * Significa ke encontro el cliente
                     */
                    if(rstCli.next()){
                        nombre = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                        direccion = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                        telefono = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                        identificacion  = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                        codciudad  = rstCli.getInt("co_ciu");
                        maxDescuento  = rstCli.getDouble("nd_maxDes");
                        tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                        co_tipper = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                        ne_diagra = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                        intCodMaeEqu = buscarEquiCliente();
                        isEmpty = false;
                    }
                    rstCli.close();
                    stmCli.close();
                    conCli.close();
               }
               catch(java.sql.SQLException Evt){
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }

               catch(Exception Evt){
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }
               return true;
        }        
        public boolean setCliente(int intCodEmp,String strCli){
               isEmpty = true;
               
               try{
                   conCli=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   stmCli = conCli.createStatement();
                   /*
                    *  String para el hacer el query en la tabla de clientes y obtener 
                    *  sus datos .
                    */

                    String strSql = "select * from tbm_cli where " +
                                        "co_emp = " + intCodEmp + " and " +
                                        "tx_nom = '" + strCli + "'";
                    System.out.println(strSql);
                    rstCli = stmCli.executeQuery(strSql);
                    
                    /*
                     * Significa ke encontro el cliente
                     */
                    if(rstCli.next()){
                        nombre = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                        direccion = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                        telefono = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                        identificacion  = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                        codciudad  = rstCli.getInt("co_ciu");
                        maxDescuento  = rstCli.getDouble("nd_maxDes");
                        tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                        co_tipper = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                        ne_diagra = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                        intCodMaeEqu = buscarEquiCliente();
                        isEmpty = false;
                    }
                    rstCli.close();
                    stmCli.close();
                    conCli.close();
               }
               catch(java.sql.SQLException Evt){
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }

               catch(Exception Evt){
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
                       return false;
               }
               return true;
        }                
        public boolean isEmpty (){
            return isEmpty;
        }
        public String getNombre (){
            return (isEmpty)?null:nombre;
        }
        public String getDireccion (){
            return (isEmpty)?null:direccion;
        }
        public String getTelefono (){
            return (isEmpty)?null:telefono;
        }
        public String getIdentificacion  (){
            return (isEmpty)?null:identificacion;
        }
        public int  getCodciudad  (){
            return (isEmpty)?-1:codciudad;
        }
        public double  getMaxdescuento(){
            return (isEmpty)?-1:maxDescuento;
        }
        public char  getTipPer(){
            return (isEmpty)?' ':tipPer;
        }
        public int getCodTipPer(){
            return (isEmpty()?0:co_tipper);
        }
        public int getDiaGracia(){
            return (isEmpty()?0:ne_diagra);
        }
        /**
         *<pre>
         * Busca codigo maestro de equivalencia del consolidado de clientes, si no existe
         * se retornara cero (0) como codigo maestro</pre>
         */
        private int buscarEquiCliente(){
            int intResp=0;
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        java.sql.Statement stm = con.createStatement();
                        String strSQL = "";
                        strSQL = " Select co_climae from tbm_equcli where co_emp = " + intCodEmp + " and co_cli = " + codigo;
//                        System.out.println (strSQL);
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intResp = rst.getInt("co_climae");
                        }
                        rst.close();
                        stm.close();
                        con.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    System.out.println(e.toString());
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
                }
            }catch(Exception e){                
                System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
            }
            return intResp;
        }
        /**
         * Devuelve el codigo maestro asignado. Cero (0) sino tiene equivalencia
         */
        public int getCodMaestroCliente(){
            return intCodMaeEqu;
        }
}
