package Librerias.ZafUtil;

/**
 *
 * @author clainez
 * @version 1.0
 * 04/04/2011
 */
public class UtilitiesException extends Exception {
    private String strErr;

    public UtilitiesException() {
        super();
        strErr = "Desconocido";
    }

    public UtilitiesException(String strErr) {
        super(strErr);
        this.strErr = strErr;
    }

    public UtilitiesException(String strErr, Throwable thrwbl) {
        super(strErr, thrwbl);
        this.strErr = strErr;
    }

    public UtilitiesException(Throwable thrwbl) {
        super(thrwbl);
    }

    public String getStrErr() {
        return strErr;
    }

}
