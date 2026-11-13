package Proceso;

/**
 * Pojo contiene campos de la tabla tbm_ingegrmentra
 * @author Tony Sanginez
 * Guayaquil 12/09/2016
 */
public class Tbm_ingegrmentra implements Cloneable {
    
    //private int intCo_emp;
    //private int intCo_tra;
    //private int intNe_ani;
    //private int intNe_mes;
    private int intNe_per;
    private int intCo_rub;
    private String strNd_porrubpag;
    
   
    public int getIntCo_rub() {
        return intCo_rub;
    }

    public void setIntCo_rub(int intCo_rub) {
        this.intCo_rub = intCo_rub;
    }

    public String getStrNd_porrubpag() {
        return strNd_porrubpag;
    }

    public void setDouNd_porrubpag(String strNd_porrubpag) {
        this.strNd_porrubpag = strNd_porrubpag;
    }  

    public int getIntNe_per() {
        return intNe_per;
    }

    public void setIntNe_per(int intNe_per) {
        this.intNe_per = intNe_per;
    }
    
}