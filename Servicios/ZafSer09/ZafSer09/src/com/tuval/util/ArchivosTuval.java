package com.tuval.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author clainez
 * @version 1.0
 * 21/06/2011
 */
public class ArchivosTuval {
    private File filLog;
    private FileWriter fwr;
    private PrintWriter pwr;

    /**
     * Constructor recibe la ruta destino completa del archivo
     * @param strPat Ruta destino completa del archivo y su nombre
     */
    public ArchivosTuval(String strPat) {
        filLog = new File(strPat);
    }

    /**
     * Escribe una linea en el archivo con retorno de carro.
     * Si archivo no existe lo crea.
     * @param strTex
     * @throws Exception
     */
    public void println(String strTex) throws TuvalUtilitiesException {
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
            throw new TuvalUtilitiesException(ex.getMessage());
        }
    }
}
