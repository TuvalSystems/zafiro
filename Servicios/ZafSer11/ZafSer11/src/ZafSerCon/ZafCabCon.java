/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafSerCon;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class ZafCabCon implements Serializable
{
    String strFec;
    String strCodCpbl;
    String strRucEmp;
    int intTipAmb;
    int intTipDoc;
    int intCodEmp;
    
    public ZafCabCon(){}

    public String getStrFec() {
        return strFec;
    }

    public void setStrFec(String strFec) {
        this.strFec = strFec;
    }

    public String getStrCodCpbl() {
        return strCodCpbl;
    }

    public void setStrCodCpbl(String strCodCpbl) {
        this.strCodCpbl = strCodCpbl;
    }

    public String getStrRucEmp() {
        return strRucEmp;
    }

    public void setStrRucEmp(String strRucEmp) {
        this.strRucEmp = strRucEmp;
    }

    public int getIntTipAmb() {
        return intTipAmb;
    }

    public void setIntTipAmb(int intTipAmb) {
        this.intTipAmb = intTipAmb;
    }

    public int getIntTipDoc() {
        return intTipDoc;
    }

    public void setIntTipDoc(int intTipDoc) {
        this.intTipDoc = intTipDoc;
    }

    public int getIntCodEmp() {
        return intCodEmp;
    }

    public void setIntCodEmp(int intCodEmp) {
        this.intCodEmp = intCodEmp;
    }
}