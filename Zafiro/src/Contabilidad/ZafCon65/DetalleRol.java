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
public class DetalleRol {

    public int getCoEmp() {
        return coEmp;
    }

    public void setCoEmp(int coEmp) {
        this.coEmp = coEmp;
    }

    public int getCoPer() {
        return coPer;
    }

    public void setCoPer(int coPer) {
        this.coPer = coPer;
    }

    public int getNePer() {
        return nePer;
    }

    public void setNePer(int nePer) {
        this.nePer = nePer;
    }

    public int getCoTra() {
        return coTra;
    }

    public void setCoTra(int coTra) {
        this.coTra = coTra;
    }

    public int getCoReg() {
        return coReg;
    }

    public void setCoReg(int coReg) {
        this.coReg = coReg;
    }

    public String getCoCta() {
        return coCta;
    }

    public void setCoCta(String coCta) {
        this.coCta = coCta;
    }

    public String getTxTipMov() {
        return txTipMov;
    }

    public void setTxTipMov(String txTipMov) {
        this.txTipMov = txTipMov;
    }

    public String getTxObs() {
        return txObs;
    }

    public void setTxObs(String txObs) {
        this.txObs = txObs;
    }

    public double getNdMntCta() {
        return ndMntCta;
    }

    public void setNdMntCta(double ndMntCta) {
        this.ndMntCta = ndMntCta;
    }

    public DetalleRol(int coEmp, int coPer, int nePer, int coTra, int coReg, String coCta, String txTipMov, String txObs, double ndMntCta) {
        this.coEmp = coEmp;
        this.coPer = coPer;
        this.nePer = nePer;
        this.coTra = coTra;
        this.coReg = coReg;
        this.coCta = coCta;
        this.txTipMov = txTipMov;
        this.txObs = txObs;
        this.ndMntCta = ndMntCta;
    }
        int coEmp;
        int coPer;
        int nePer;
        int coTra;
        int coReg;
        String coCta;
        String txTipMov;
        String txObs;
        double ndMntCta;

        
}
