/*
 * Clase Para setear el Sueldo Basico correspondiente al anio 2018 - 386.00
 * 
 * 
 */
package RecursosHumanos.ZafRecHum44;

/**
 *
 * @author postgres
 */
public class ZafSueBas {
    private final double douNd_SueBas=482; /// 2024 -- > 460.04   
    
    /**
     * @return the douNd_SueBas
     */
    public double getDouNd_SueBas() {
        return douNd_SueBas;
    }

//    /**
//     * @param douNd_SueBas the douNd_SueBas to set
//     */
//    public void setDouNd_SueBas(double douNd_SueBas) {
//        this.douNd_SueBas = douNd_SueBas;
//    }
    
    /**
     * Método que permite clonar el objeto
     * @return Retorna objeto clonado o nulo
     */
    @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}
