package Librerias.ZafUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author clainez
 * @version 1.0
 * 21/06/2011
 */
public class Archivos {
    private File filLog;
    private FileWriter fwr;
    private PrintWriter pwr;

    /**
     * Constructor recibe la ruta destino completa del archivo
     * @param strPat Ruta destino completa del archivo y su nombre
     */
    public Archivos(String strPat) {
        filLog = new File(strPat);
    }

    /**
     * Escribe una linea en el archivo con retorno de carro.
     * Si archivo no existe lo crea.
     * @param strTex
     * @throws Exception
     */
    public void println(String strTex) throws UtilitiesException {
        try{
            if(!filLog.exists()){
                filLog.createNewFile();
            }
            fwr = new FileWriter(filLog, true);
            //PrintWriter pw = new PrintWriter("filename", "ISO-8859-1");
            pwr = new PrintWriter(fwr);
            pwr.println(strTex);
            pwr.close();
        }catch(Exception ex){
            throw new UtilitiesException(ex.getMessage());
        }
    }
}
