/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum77;

/**
 *
 * @author sistemas4
 */
public class ResultadoJtable {

 
    private int intcodigo;
    private String strEmpleado;
    private double douValor;
    private String strIdentificacion;
    private int intempre;
    private String strempre;
    private String fil;

    public String getFil() {
        return fil;
    }

    public void setFil(String fil) {
        this.fil = fil;
    }

    public ResultadoJtable(int intcodigo, String strEmpleado, double douValor, String strIdentificacion, int intempre, String strempre, String fil) {
        this.intcodigo = intcodigo;
        this.strEmpleado = strEmpleado;
        this.douValor = douValor;
        this.strIdentificacion = strIdentificacion;
        this.intempre = intempre;
        this.strempre = strempre;
        this.fil = fil;
    }


    public int getIntempre() {
        return intempre;
    }

    public void setIntempre(int intempre) {
        this.intempre = intempre;
    }

    public String getStrempre() {
        return strempre;
    }

    public void setStrempre(String strempre) {
        this.strempre = strempre;
    }

  
    public String getStrIdentificacion() {
        return strIdentificacion;
    }

    public void setStrIdentificacion(String strIdentificacion) {
        this.strIdentificacion = strIdentificacion;
    }


 

    public int getIntcodigo() {
        return intcodigo;
    }

    public void setIntcodigo(int intcodigo) {
        this.intcodigo = intcodigo;
    }

    public String getStrEmpleado() {
        return strEmpleado;
    }

    public void setStrEmpleado(String strEmpleado) {
        this.strEmpleado = strEmpleado;
    }

    public Double getDouValor() {
        return douValor;
    }

    public void setDouValor(Double douValor) {
        this.douValor = douValor;
    }
 
}
