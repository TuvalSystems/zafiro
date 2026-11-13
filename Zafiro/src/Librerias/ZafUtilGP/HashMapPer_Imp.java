/*
 * ZafUtilGP.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.59
 */ 

package Librerias.ZafUtilGP;
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
    private String version ="v1.2";
    

public static class NotaPedidos {

    Double univend;
    Double numVec;
    Double numMes;
    Double salUni;

    public NotaPedidos( Double univend_, Double numVec_, Double numMes_,Double salUni_) {
        this.univend = univend_;
        this.numVec = numVec_;
        this.numMes = numMes_;
        this.salUni = salUni_;
    }  
    
    @Override
    public String toString() {
        return "ColumnData{" +
               "univend=" + univend +
               ", numVec=" + numVec +
               ", numMes=" + numMes +
               ", salUni=" + salUni +
               '}';
    }  
    public double getNd_uniVen() {
        return univend;
    }  
    
    public double getNe_numVec() {
        return numVec;
    }

    public double getNe_numMes() {
        return numMes;
    }

    public double getNd_salUni() {
        return salUni;
    }    
}

public static class PedidosDetEmbarc {

        int tipDoc;
        String codItm;
        String orden;
        Double cant;
        String estado;
        int columna;

        public PedidosDetEmbarc( String orden_,int tipDoc_,String codItm_, Double cant_,String estado_,int columna_) 
        {
            this.codItm = codItm_;
            this.cant = cant_;
            this.tipDoc = tipDoc_;
            this.orden = orden_;
            this.columna = columna_;
            this.estado = estado_;            
            
        }  

        public String getSt_codItm() {
            return codItm;
        }  
        
        public String getSt_Orden() {
            return orden;
        }          

        public double getNe_cant() {
            return cant;
        }

        public int getNe_tipDoc() {
            return tipDoc;
        }
        
        public String getNd_item() {
            return codItm;
        }   
        public int getNd_columna() {
            return columna;
        }

        public void setCant(Double cant_) {
            this.cant = cant_;
        }

        public void setTipDoc(int tipDoc_) {
            this.tipDoc = tipDoc_;
        }

        public void setEstado(String estado_) {
            this.estado = estado_;
        }        
        
        public void setOrden(String orden_) {
            this.orden = orden_;
        }          

   
    }

public static class ColumnasExcelCom112 {

        Double promedio;
        Double ppmax;
        String item;
        Double ppmin;
        Double exce;
        Double stock;
        Double fre;
        Double tran;
        Double moq;
        Double teorico;

        public ColumnasExcelCom112( String item_,Double promedio_,Double ppmin_,Double ppmax_,Double exce_,Double stock_,Double fre_,Double tran_,Double moq_,Double teorico_) 
        {
            this.item = item_;
            this.promedio = promedio_;
            this.ppmin = ppmin_;
            this.ppmax = ppmax_;
            this.exce = exce_;
            this.stock = stock_;            
            this.fre = fre_;            
            this.tran = tran_;            
            this.moq = moq_;            
            this.teorico = teorico_;            
            
        }  

        public String getSt_item() {
            return item;
        }  
        
        public double getNe_promedio() {
            return promedio;
        }          

        public double getNe_ppmin() {
            return ppmin;
        }

        public double getNe_ppmax() {
            return ppmax;
        }
        
        public double getNe_exce() {
            return exce;
        }   
        public double getNe_stock() {
            return stock;
        }

        public double getNe_fre() {
            return fre;
        }
        
        public double getNe_tran() {
            return tran;
        }   
        public double getNe_moq() {
            return moq;
        }
        public double getNe_teorico() {
            return teorico;
        }        

   
    }




}


