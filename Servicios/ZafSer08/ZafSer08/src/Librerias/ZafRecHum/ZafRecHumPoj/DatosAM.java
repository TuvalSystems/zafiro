package Librerias.ZafRecHum.ZafRecHumPoj;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




/*
 * To change this templatye, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roberto Flores
 */
public class DatosAM{

    private int intPin;
    private String strName;
    //private Date datSDate;
    private Date strSDate;
    private String strFeDia;
    private String strSTime;
    private int intMacNo;
    
    private Timestamp ts;

    /**
     * @return the intPin
     */
    public int getIntPin() {
        return intPin;
    }

    /**
     * @param intPin the intPin to set
     */
    public void setIntPin(int intPin) {
        this.intPin = intPin;
    }

    /**
     * @return the strName
     */
    public String getStrName() {
        return strName;
    }

    /**
     * @param strName the strName to set
     */
    public void setStrName(String strName) {
        this.strName = strName;
    }

    /**
     * @return the strSDate
     */
    public Date getStrSDate() {
        return strSDate;
    }

    /**
     * @param strSDate the strSDate to set
     */
    public void setStrSDate(Date strSDate) {
        this.strSDate = strSDate;
    }

    /**
     * @return the strSTime
     */
    public String getStrSTime() {
        return strSTime;
    }

    /**
     * @param strSTime the strSTime to set
     */
    public void setStrSTime(String strSTime) {
        this.strSTime = strSTime;
    }

    /**
     * @return the strFeDia
     */
    public String getStrFeDia() {
        return strFeDia;
    }

    /**
     * @param strFeDia the strFeDia to set
     */
    public void setStrFeDia(String strFeDia) {
        this.strFeDia = strFeDia;
    }

    /**
     * @return the intMacNo
     */
    public int getIntMacNo() {
        return intMacNo;
    }

    /**
     * @param intMacNo the intMacNo to set
     */
    public void setIntMacNo(int intMacNo) {
        this.intMacNo = intMacNo;
    }


    /**
     * @return the ts
     */
    public Timestamp getTs() {
        return ts;
    }

    /**
     * @param ts the ts to set
     */
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}