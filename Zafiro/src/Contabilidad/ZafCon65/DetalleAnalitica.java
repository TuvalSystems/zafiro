/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon65;

/**
 *
 * @author sistemas4
 */
public class DetalleAnalitica {
    int co_cta;   
    double monto_cta;
    String codAnalitica;
    String tipo_movi;

    public DetalleAnalitica(int co_cta, double monto_cta, String codAnalitica, String tipo_movi) {
        this.co_cta = co_cta;
        this.monto_cta = monto_cta;
        this.codAnalitica = codAnalitica;
        this.tipo_movi = tipo_movi;
    }

    public int getCo_cta() {
        return co_cta;
    }

    public void setCo_cta(int co_cta) {
        this.co_cta = co_cta;
    }

    public double getMonto_cta() {
        return monto_cta;
    }

    public void setMonto_cta(double monto_cta) {
        this.monto_cta = monto_cta;
    }

    public String getCodAnalitica() {
        return codAnalitica;
    }

    public void setCodAnalitica(String codAnalitica) {
        this.codAnalitica = codAnalitica;
    }

    public String getTipo_movi() {
        return tipo_movi;
    }

    public void setTipo_movi(String tipo_movi) {
        this.tipo_movi = tipo_movi;
    }

}
