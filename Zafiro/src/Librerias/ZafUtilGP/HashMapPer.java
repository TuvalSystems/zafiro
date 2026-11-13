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
public class HashMapPer
{
   
    static java.awt.Color colComDes;
    //Constantes: Columnas utilizadas por el proceso de recosteo:
  
    

    private java.util.Date datFecAux;  
    String mensajeConf ="",strFecUltMod="";
    private ZafParSis objParSis;
    private String version ="v1.2";
    

    
////    private static final int INT_ARL_CON_INV_MAR_UTI=5;
////    private static final int INT_ARL_CON_INV_MAX_VEN=6;
////    private static final int INT_ARL_CON_INV_COS_UNI=7;    
////    private static final int INT_ARL_CON_INV_PRE_VTA=8;
////    private static final int INT_ARL_CON_INV_GRP_IMP=9;
////    private static final int INT_ARL_CON_INV_NOM_ITM=10;
////    private static final int INT_ARL_CON_INV_REV_TEC=11;    
////    private static final int INT_ARL_CON_INV_ITM_STO=12;    
    
    
    /**
    * Crea una nueva instancia de la clase ZafUtilGP
    */
////    public HashMapPer(ZafParSis obj)
////    {
////         try {
////             
////            this.objParSis = (ZafParSis) obj.clone();
////            
////        } catch (CloneNotSupportedException ex) {
////          //  Logger.getLogger(ZafMae85_02.class.getName()).log(Level.SEVERE, null, ex);
////        }      
////           
////    }
    
    public static class Historico {

        Double univend;
        Double numVec;
        Double numMes;
        Double salUni;

        public Historico( Double univend_, Double numVec_, Double numMes_,Double salUni_) {
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
    
    public static class Com113 { 

        Double nd_costo; 
        Double nd_precio; 

        public Com113 ( Double nd_costo_, Double nd_precio_) {
            this.nd_costo = nd_costo_;
            this.nd_precio = nd_precio_;
        }  


        public double getNd_costo() {
            return nd_costo; 
        }  

        public double getNe_precio() {
            return nd_precio;
        }

    }
    
        public static class Com113_inv { 

        String str_item;  
        Double nd_stock; 
        Double nd_factor; 
        int ne_grupo;
        int ne_detgrupo;
        Double nd_eje;
        Double nd_FacAju;

        public Com113_inv ( String str_item_, Double nd_stock_, Double nd_factor_,int ne_grupo_,int ne_detgrupo_, Double nd_eje_,Double nd_FacAju_) {
            this.str_item = str_item_;
            this.nd_stock = nd_stock_;
            this.nd_factor = nd_factor_; 
            this.ne_grupo = ne_grupo_;
            this.ne_detgrupo = ne_detgrupo_;            
            this.nd_eje = nd_eje_;            
            this.nd_FacAju = nd_FacAju_; 
        }  


        public String getStr_item() {
            return str_item; 
        }  

        public double getNe_stock() {
            return nd_stock;
        }
        
        public double getNe_factor() {
            return nd_factor;
        }        
        
        public int getNe_grupo() {
            return ne_grupo;
        }  
        
        public int getNe_detgrupo() {
            return ne_detgrupo;
        }  
        
        public double getNe_eje() {
            return nd_eje;
        }        
        public double getNe_FacAju() {
            return nd_FacAju;
        }         

    } 
    
    public static class Com113_hist 
    { 

        String str_valor; 

        public Com113_hist ( String str_valor_) {
            this.str_valor = str_valor_;
        }  


        public String getStr_valor() {
            return str_valor; 
        }  

      

    }      
    
    public static class Com112_hist 
    { 

        BigDecimal bgdValor; 

        public Com112_hist ( BigDecimal bgdValor_) {
            this.bgdValor = bgdValor_;
        }  

        public BigDecimal getBgd_valor() {
            return bgdValor; 
        }  
    }      
     
    public static class Imp17_ItemsAtri { 

        String str_linea;
        String str_clase; 
        Double nd_totProDo;
        Double nd_totProKi; 
        int ne_orden;
        Double nd_stkAct; 
        Double nd_stkImp; 
        Double nd_stkTra;
        Double nd_stkTotal;
        Double nd_stkAct_Dola; 
        Double nd_stkImp_Dola; 
        Double nd_stkTra_Dola;
        Double nd_stkTotal_Dola;        

//        public Imp17_ItemsAtri ( String str_linea_, String str_clase_,Double nd_totProDo_, Double nd_totProKi_,int ne_orden_,Double nd_stkAct_,Double nd_stkImp_,Double nd_stkTra_,Double nd_stkTotal_,Double nd_stkActDola_,Double nd_stkImpDola_,Double nd_stkTraDola_,Double nd_stkTotalDola_) {
    public Imp17_ItemsAtri ( String str_linea_, String str_clase_,Double nd_totProDo_, Double nd_totProKi_,int ne_orden_,Double nd_stkAct_,Double nd_stkImp_,Double nd_stkTra_,Double nd_stkTotal_,Double nd_stkActDola_,Double nd_stkImpDola_,Double nd_stkTraDola_,Double nd_stkTotalDola_) {            
            this.str_linea = str_linea_;
            this.str_clase = str_clase_;
            this.nd_totProDo = nd_totProDo_;
            this.nd_totProKi = nd_totProKi_;
            this.ne_orden = ne_orden_;
            this.nd_stkAct = nd_stkAct_;
            this.nd_stkImp = nd_stkImp_;
            this.nd_stkTra = nd_stkTra_;
            this.nd_stkTotal = nd_stkTotal_;
            this.nd_stkAct_Dola = nd_stkActDola_;
            this.nd_stkImp_Dola = nd_stkImpDola_;
            this.nd_stkTra_Dola = nd_stkTraDola_;
            this.nd_stkTotal_Dola = nd_stkTotalDola_;            
            
        }
        public String getSt_linea() {
            return  str_linea;
        }  
        public String getSt_clase() {
            return  str_clase;
        }  
        
        public double getNd_totProDo() {
            return  nd_totProDo;
        }  

        public double getNd_totProKi() {
            return nd_totProKi;
        }
        
        public int getNe_orden() {
            return  ne_orden;
        }  

        public double getNd_stkAct() {
            return nd_stkAct;
        }        

        public double getNd_stkImp() {
            return  nd_stkImp;
        }  

        public double getNd_stkTra() {
            return nd_stkTra;
        }        

        public double getNd_stkTotal() {
            return  nd_stkTotal;
        }        
        
    // Setter para nd_totPediDo
        public void setNd_totProDo(double nd_totPediDoI) {
        this.nd_totProDo = nd_totPediDoI;
        }        
        
        public void setNd_totProKi(double nd_totProKiI) {
        this.nd_totProKi = nd_totProKiI;
        }  
        
        
        public double getNd_stkActDola() {
            return nd_stkAct_Dola;
        }        

        public double getNd_stkImpDola() {
            return  nd_stkImp_Dola;
        }  

        public double getNd_stkTraDola() {
            return nd_stkTra_Dola;
        }        

        public double getNd_stkTotalDola() {
            return  nd_stkTotal_Dola;
        }          


    }    

    public static class Imp17_ItemsAtriPedNot { 

        Double nd_totPediDo;
        Double nd_totPediKi; 


        public Imp17_ItemsAtriPedNot ( Double nd_totPediDo_, Double nd_totPediKi_) {
            this.nd_totPediDo = nd_totPediDo_;
            this.nd_totPediKi = nd_totPediKi_;
            
            
        }  
        public double getNd_totPediDo() {
            return  nd_totPediDo;
        }  

        public double getNd_totPediKi() {
            return nd_totPediKi;
        }


    }    
    
    
    public static class Imp18_ItemsAtri { 

        String str_linea;
        String str_clase; 
        Double nd_proVenMenPeso;
        Double nd_preSugePeso;
        Double nd_proVenMenDolar;
        Double nd_preSugeDolar; 
        int ne_orden;
//        public Imp17_ItemsAtri ( String str_linea_, String str_clase_,Double nd_totProDo_, Double nd_totProKi_,int ne_orden_,Double nd_stkAct_,Double nd_stkImp_,Double nd_stkTra_,Double nd_stkTotal_,Double nd_stkActDola_,Double nd_stkImpDola_,Double nd_stkTraDola_,Double nd_stkTotalDola_) {
    public Imp18_ItemsAtri ( String str_linea_, String str_clase_,Double nd_proVenMenPeso_, Double nd_preSugePeso_,Double nd_proVenMenDolar_,Double nd_preSugeDolar_,int ne_orden_) {            
            this.str_linea = str_linea_;
            this.str_clase = str_clase_;
            this.nd_proVenMenPeso = nd_proVenMenPeso_;
            this.nd_preSugePeso = nd_preSugePeso_;
            this.nd_proVenMenDolar = nd_proVenMenDolar_;
            this.nd_preSugeDolar = nd_preSugeDolar_;
            this.ne_orden = ne_orden_;
            
            
        }
        public String getSt_linea() {
            return  str_linea;
        }  
        public String getSt_clase() { 
            return  str_clase;
        }  
        
        public double getNd_nd_proVenMenPeso() {
            return  nd_proVenMenPeso;
        }   
        
        public double getNd_preSugePeso() {
            return nd_preSugePeso;
        }         

        public double getNd_proVenMenDolar() {
            return nd_proVenMenDolar;
        }
        
        public double getNd_preSugeDolar() {
            return  nd_preSugeDolar;
        }          
        
        public int getNe_orden() {
            return  ne_orden;
        }  

        public void setNd_orden(int ne_orden_) {
        this.ne_orden = ne_orden_;
        }        
    }      
    
    
    public static class Imp18_ItemsAtri_Cump { 

        String str_linea;
        String str_clase; 
        Double nd_proVenMenPeso;
        Double nd_preSugePeso;
        Double nd_proVenMenDolar;
        Double nd_preSugeDolar; 
        int ne_orden;
        Double nd_proMenVenPesoActual;
        Double nd_cantidadCompraActual;
        Double nd_proMenVenDolarActual;
        Double nd_cantidadCompraPrecioActual;         
        Double nd_diferenciaKG;
        Double nd_diferenciaPRE;           
//        public Imp17_ItemsAtri ( String str_linea_, String str_clase_,Double nd_totProDo_, Double nd_totProKi_,int ne_orden_,Double nd_stkAct_,Double nd_stkImp_,Double nd_stkTra_,Double nd_stkTotal_,Double nd_stkActDola_,Double nd_stkImpDola_,Double nd_stkTraDola_,Double nd_stkTotalDola_) {
    public Imp18_ItemsAtri_Cump ( String str_linea_, String str_clase_,Double nd_proVenMenPeso_, Double nd_preSugePeso_,Double nd_proVenMenDolar_,Double nd_preSugeDolar_,int ne_orden_,Double nd_proMenVenPesoActual_,Double nd_cantidadCompraActual_,Double nd_proMenVenDolarActual_,Double nd_cantidadCompraPrecioActual_, Double nd_diferenciaKG_,Double nd_diferenciaPRE_) 
    {            
            this.str_linea = str_linea_;
            this.str_clase = str_clase_;
            this.nd_proVenMenPeso = nd_proVenMenPeso_;
            this.nd_preSugePeso = nd_preSugePeso_;
            this.nd_proVenMenDolar = nd_proVenMenDolar_;
            this.nd_preSugeDolar = nd_preSugeDolar_;
            this.ne_orden = ne_orden_;
            this.nd_proMenVenPesoActual = nd_proMenVenPesoActual_;
            this.nd_cantidadCompraActual = nd_cantidadCompraActual_;
            this.nd_proMenVenDolarActual = nd_proMenVenDolarActual_;
            this.nd_cantidadCompraPrecioActual = nd_cantidadCompraPrecioActual_;            
            this.nd_diferenciaKG = nd_diferenciaKG_;
            this.nd_diferenciaPRE = nd_diferenciaPRE_;              
            
            
        }
        public String getSt_linea() {
            return  str_linea;
        }  
        public String getSt_clase() { 
            return  str_clase;
        }  
        
        public double getNd_nd_proVenMenPeso() {
            return  nd_proVenMenPeso;
        }   
        
        public double getNd_preSugePeso() {
            return nd_preSugePeso;
        }         

        public double getNd_proVenMenDolar() {
            return nd_proVenMenDolar;
        }
        
        public double getNd_preSugeDolar() {
            return  nd_preSugeDolar;
        }          
        
        public int getNe_orden() {
            return  ne_orden;
        }  

        public void setNd_orden(int ne_orden_) {
        this.ne_orden = ne_orden_;
        }    
        
        public double getNd_proMenVenPesoActual() {
            return  nd_proMenVenPesoActual;
        }   
        
        public double getNd_cantidadCompraActual() {
            return nd_cantidadCompraActual;
        }         

        public double getNd_proMenVenDolarActual() {
            return nd_proMenVenDolarActual;
        }
        
        public double getNd_cantidadCompraPrecioActual() {
            return  nd_cantidadCompraPrecioActual;
        } 
        
        public double getNd_diferenciaKG() {
            return nd_diferenciaKG; 
        }
        
        public double getNd_diferenciaPRE() {
            return  nd_diferenciaPRE;
        }         
        
    }        
 public static class ZafCol_Excel { 

        int co_mnu;
        String str_des; 
        //int nd_sel; 
    //public ZafCol_Excel ( int co_mnu_, String str_des_,int nd_sel_) {            
    public ZafCol_Excel ( int co_mnu_, String str_des_) {           
            this.co_mnu = co_mnu_;
            this.str_des = str_des_;
            //this.nd_sel = nd_sel_;
        }
        public int getCo_mnu() {
            return  co_mnu;
        }  
        public String getSt_des() { 
            return  str_des;
        }  
////        public int getNd_se() {
////            return  nd_sel;
////        }         
    }     
    
////    public static class Com90
////    {
////
////        String st_codigo; 
////        Double nd_stock; 
////
////        public Com90 ( String st_codigo_, Double nd_stock_) {
////            this.st_codigo = st_codigo_;
////            this.nd_stock = nd_stock_;
////        }  
////
////
////        public String getSt_codigo() {
////            return st_codigo; 
////        }  
////
////        public double getNd_stock() {
////            return nd_stock;
////        }        
////        
////    }    
    
public static class Com113_Pedidos { 

        int ne_tipo; 
        String tx_pedido; 

        public Com113_Pedidos ( int ne_tipo_, String tx_pedido_) {
            this.ne_tipo = ne_tipo_;
            this.tx_pedido = tx_pedido_;
        }  


        public int getNe_tipo() {
            return ne_tipo; 
        }  

        public String getTx_pedido() {
            return tx_pedido;
        }

    }


public static class Items_Historia_Modificados_Imp{ 

        Double nd_pesAct;
        Double nd_pesMod;
        Double nd_freAct;
        Double nd_freMod;
        Double nd_moqAct;
        Double nd_moqMod;        
        Double nd_leaTimAct;
        Double nd_leaTimMod;  
        Double nd_frefinAct;
        Double nd_frefinMod;  
        Double nd_stobasAct;
        Double nd_stobasMod;  
        Double nd_cobmaxAct;
        Double nd_cobmaxMod;          
//        public Imp17_ItemsAtri ( String str_linea_, String str_clase_,Double nd_totProDo_, Double nd_totProKi_,int ne_orden_,Double nd_stkAct_,Double nd_stkImp_,Double nd_stkTra_,Double nd_stkTotal_,Double nd_stkActDola_,Double nd_stkImpDola_,Double nd_stkTraDola_,Double nd_stkTotalDola_) {
    public Items_Historia_Modificados_Imp ( Double nd_pesAct_, Double nd_pesMod_,Double nd_freAct_, Double nd_freMod_,Double nd_moqAct_,Double nd_moqMod_,Double nd_leaTimAct_,Double nd_leaTimMod_,Double nd_frefinAct_,Double nd_frefinMod_,Double nd_stobasAct_,Double nd_stobasMod_,Double nd_cobmaxAct_,Double nd_cobmaxMod_) 
    {            
            this.nd_pesAct = nd_pesAct_;
            this.nd_pesMod = nd_pesMod_;
            this.nd_freAct = nd_freAct_;
            this.nd_freMod = nd_freMod_;
            this.nd_moqAct = nd_moqAct_;
            this.nd_moqMod = nd_moqMod_;
            this.nd_leaTimAct = nd_leaTimAct_;
            this.nd_leaTimMod = nd_leaTimMod_;
            this.nd_frefinAct = nd_frefinAct_;
            this.nd_frefinMod = nd_frefinMod_;
            this.nd_stobasMod = nd_stobasAct_;
            this.nd_stobasMod = nd_stobasMod_;
            
            
    }
    
        // ======== GETTERS ========

        public Double getNd_pesAct() {
            return nd_pesAct;
        }

        public Double getNd_pesMod() {
            return nd_pesMod;
        }

        public Double getNd_freAct() {
            return nd_freAct;
        }

        public Double getNd_freMod() {
            return nd_freMod;
        }

        public Double getNd_moqAct() {
            return nd_moqAct;
        }

        public Double getNd_moqMod() {
            return nd_moqMod;
        }

        public Double getNd_leaTimAct() {
            return nd_leaTimAct;
        }

        public Double getNd_leaTimMod() {
            return nd_leaTimMod;
        }

        public Double getNd_frefinAct() {
            return nd_frefinAct;
        }

        public Double getNd_frefinMod() {
            return nd_frefinMod;
        }
        
        public Double getNd_stobasAct() {
            return nd_stobasAct;
        }

        public Double getNd_stobasMod() {
            return nd_stobasMod;
        }  
        
        public Double getNd_cobmaxAct() {
            return nd_cobmaxAct;
        }

        public Double getNd_cobmaxMod() {
            return nd_cobmaxMod;
        }        
        
        // ======== SETTERS ========

        public void setNd_pesAct(Double nd_pesAct_) {
            this.nd_pesAct = nd_pesAct_;
        }

        public void setNd_pesMod(Double nd_pesMod_) {
            this.nd_pesMod = nd_pesMod_;
        }

        public void setNd_freAct(Double nd_freAct_) {
            this.nd_freAct = nd_freAct_;
        }

        public void setNd_freMod(Double nd_freMod_) {
            this.nd_freMod = nd_freMod_;
        }

        public void setNd_moqAct(Double nd_moqAct_) {
            this.nd_moqAct = nd_moqAct_;
        }

        public void setNd_moqMod(Double nd_moqMod_) {
            this.nd_moqMod = nd_moqMod_;
        }

        public void setNd_leaTimAct(Double nd_leaTimAct_) {
            this.nd_leaTimAct = nd_leaTimAct_;
        }

        public void setNd_leaTimMod(Double nd_leaTimMod_) {
            this.nd_leaTimMod = nd_leaTimMod_;
        }

        public void setNd_frefinAct(Double nd_frefinAct_) {
            this.nd_frefinAct = nd_frefinAct_;
        }

        public void setNd_frefinMod(Double nd_frefinMod_) {
            this.nd_frefinMod = nd_frefinMod_;
        }    
        
        public void setNd_stobasAct(Double nd_stobasAct_) {
            this.nd_stobasAct = nd_stobasAct_;
        }

        public void setNd_stobasMod(Double nd_stobasMod_) {
            this.nd_stobasMod = nd_stobasMod_;
        }        
        public void setNd_cobmaxAct(Double nd_cobmaxAct_) {
            this.nd_cobmaxAct = nd_cobmaxAct_;
        }

        public void setNd_cobmaxMod(Double nd_cobmaxMod_) {
            this.nd_cobmaxMod = nd_cobmaxMod_;
        }         
        
        
    }   



}
