/*
 * ZafUtil.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.59
 */ 

package Librerias.ZafUtil;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
  

/**
 * El fin de la creación de esta clase es el de ubicar en ella todas aquellas operaciones
 * que se harían de uso común para el sistema. Esta clase contendrá tantos mï¿½todos como
 * operaciones vaya a realizar. Por ejemplo, como ejemplo de operaciones comunes tenemos
 * la opción de desactivar los componentes en el momento que se realiza una consulta para
 * que no se haga modificaciones a los datos que se presentan; otra operación es la de
 * añadir o eliminar filas de un JTable, que se usará cada vez que vayamos a crear una
 * factura o una cotización.
 * @author Josï¿½ Javier Naranjo Ch.
 * @since 25/Ago/2004
 */
public class HashMapPer_Imp
{
   
    static java.awt.Color colComDes;
    //Constantes: Columnas utilizadas por el proceso de recosteo:
  
    

    private java.util.Date datFecAux;  
    String mensajeConf ="",strFecUltMod="";
    private ZafParSis objParSis;
    private String version ="v1.1";
    

    public static class PedidosEmbarc {

        int codItm_;
        Double cant;
        int codReg;

        public PedidosEmbarc( int codItm_, Double cant_, int codReg_) 
        {
            this.codItm_ = codItm_;
            this.cant = cant_;
            this.codReg = codReg_;
        }  

        public double getNd_codItm() {
            return codItm_;
        }  

        public double getNe_cant() {
            return cant;
        }

        public double getNe_codReg() {
            return codReg;
        }

   
    }
    
    public static class NotasPedido {

        int codItm_;
        Double cant;
        int codReg;
        Double cantPend;

        public NotasPedido( int codItm_, Double cant_, int codReg_, Double cantPend_) 
        {
            this.codItm_ = codItm_;
            this.cant = cant_;
            this.codReg = codReg_;
            this.cantPend = cantPend_;            
        }  

        public double getNd_codItm() {
            return codItm_;
        }  

        public double getNe_cant() {
            return cant;
        }

        public double getNe_codReg() {
            return codReg;
        }
        
        public double getNe_cantPend() {
            return cantPend;
        }        

   
    }    
}
