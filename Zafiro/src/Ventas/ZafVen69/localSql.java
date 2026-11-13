/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventas.ZafVen69;

/**
 *
 * @author sistemas4
 */
public class localSql {
    private int co_emp;
    private String tx_nom;
    private int co_loc;
    private String locZa;

    public int getCo_emp() {
        return co_emp;
    }

    public void setCo_emp(int co_emp) {
        this.co_emp = co_emp;
    }

    public String getTx_nom() {
        return tx_nom;
    }

    public void setTx_nom(String tx_nom) {
        this.tx_nom = tx_nom;
    }

    public int getCo_loc() {
        return co_loc;
    }

    public void setCo_loc(int co_loc) {
        this.co_loc = co_loc;
    }

    public String getTx_ptoVta() {
        return locZa;
    }

    public void setTx_ptoVta(String tx_ptoVta) {
        this.locZa = tx_ptoVta;
    }
    // Constructor
    public localSql(int co_emp, String tx_nom, int co_loc, String tx_ptoVta) {
        this.co_emp = co_emp;
        this.tx_nom = tx_nom;
        this.co_loc = co_loc;
        this.locZa = tx_ptoVta;
    }

    
}
 