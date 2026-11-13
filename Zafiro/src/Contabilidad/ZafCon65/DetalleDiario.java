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
public class DetalleDiario {

    public int getCocta() {
        return cocta;
    }

    public void setCocta(int cocta) {
        this.cocta = cocta;
    }

    public double getTotcta() {
        return totcta;
    }

    public void setTotcta(double totcta) {
        this.totcta = totcta;
    }

    public String getTieneAnalitica() {
        return tieneAnalitica;
    }

    public void setTieneAnalitica(String tieneAnalitica) {
        this.tieneAnalitica = tieneAnalitica;
    }

    public String getTipMovi() {
        return tipMovi;
    }

    public void setTipMovi(String tipMovi) {
        this.tipMovi = tipMovi;
    }

    public String getNomCol() {
        return nomCol;
    }

    public void setNomCol(String nomCol) {
        this.nomCol = nomCol;
    }

    public DetalleDiario(int cocta, double totcta, String tieneAnalitica, String tipMovi, String nomCol, String tipDia, int periodo, String nomcta, String numcta) {
        this.cocta = cocta;
        this.totcta = totcta;
        this.tieneAnalitica = tieneAnalitica;
        this.tipMovi = tipMovi;
        this.nomCol = nomCol;
        this.tipDia = tipDia;
        this.periodo = periodo;
        this.nomcta = nomcta;
        this.numcta = numcta;
    }

 
     
            int cocta;
            double totcta;
            String tieneAnalitica;
            String tipMovi;
            String nomCol;
            String tipDia;
            int periodo;
            String nomcta;
            String numcta;
            
            
    public String getNomcta() {
        return nomcta;
    }

    public void setNomcta(String nomcta) {
        this.nomcta = nomcta;
    }

    public String getNumcta() {
        return numcta;
    }

    public void setNumcta(String numcta) {
        this.numcta = numcta;
    }
           
            
    public String getTipDia() {
        return tipDia;
    }

    public void setTipDia(String tipDia) {
        this.tipDia = tipDia;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
}
