/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafClassExecCom;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jflores
 */
public class ZafClassExecCom {
    
    public int execCom(String command){
    

        int returnCode = -1;
        
        try
        {

            final Process process = Runtime.getRuntime().exec(command);
            new Thread(){
                public void run(){
                    try{
                        InputStream is = process.getInputStream();
                        byte[] buffer = new byte[1024];
                        for(int count = 0; (count = is.read(buffer)) >= 0;){
                            System.out.write(buffer, 0, count);
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread(){
                public void run(){
                    try{
                        InputStream is = process.getErrorStream();
                        byte[] buffer = new byte[1024];
                        for(int count = 0; (count = is.read(buffer)) >= 0;){
                            System.err.write(buffer, 0, count);
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();

            returnCode = process.waitFor();
            System.out.println("Return code = " + returnCode);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
    
        return returnCode;
    
    }
    
    public boolean isMounted(String strUniBus, String command){
        
        boolean blnRes = false;
        
        try {
            
                Process proc = Runtime.getRuntime().exec(command);
                InputStream is = proc.getInputStream();
                int size;
                String s;
                
                int exCode = proc.waitFor();
                StringBuffer ret = new StringBuffer();
                
                while((size = is.available()) != 0) {
                    byte[] b = new byte[size];
                    is.read(b);
                    s = new String(b);
                    ret.append(s);
                }
                
                String[] res = ret.toString().split("\n");
                
                for(int i = 0; i < res.length; i++){
                    String[] strTemp = res[i].toString().split(" ");
                    if(strTemp[0].compareTo(strUniBus)==0){
                        blnRes = true;
                        i = res.length;
                    }                   
                }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return blnRes;
    }
}