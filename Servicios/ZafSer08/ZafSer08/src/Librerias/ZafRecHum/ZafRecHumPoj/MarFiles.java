/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.io.File;

/**
 * 12/09/2013
 * @author sistemas2
 */
public class MarFiles implements java.util.Comparator<File>{
    
    private String marFileName;
    private long marLastModified;
    
    public MarFiles(){
        
    }
    
    public MarFiles(String marFileName , long marLastModified){
        this.marFileName= marFileName;
        this.marLastModified=marLastModified;
    }

    /**
     * @return the marFileName
     */
    public String getMarFileName() {
        return marFileName;
    }

    /**
     * @param marFileName the marFileName to set
     */
    public void setMarFileName(String marFileName) {
        this.marFileName = marFileName;
    }

    /**
     * @return the marLastModified
     */
    public long getMarLastModified() {
        return marLastModified;
    }

    /**
     * @param marLastModified the marLastModified to set
     */
    public void setMarLastModified(long marLastModified) {
        this.marLastModified = marLastModified;
    }

    public int compare(File f1, File f2) {
        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
    }

}
