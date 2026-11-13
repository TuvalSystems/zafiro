/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anufacnoimp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;

/**
 *
 * @author efloresa
 */
public class ArchivoHistorico {
    
    String strNomArchHis;
    Calendar fecha;
    
    public ArchivoHistorico(String strNomArchivo){
        this.strNomArchHis = strNomArchivo;
    }
    
    public ArchivoHistorico(){
        //Date objFec = new Date();
        fecha = Calendar.getInstance();
     
        String strDirSis = getDirectorioSistema();
        //String strMes = getNombreMes(fecha.get(Calendar.MONTH) + 1);
        //String strMes = getNombreMes(objFec.getMonth()+1);
        String strMes = getMes(fecha.get(Calendar.MONTH));
        String strDia = getDia(fecha.get(Calendar.DAY_OF_MONTH));
        //String.valueOf((objFec.getMonth()+1))            
      
        //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";
        //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf(fecha.get(Calendar.YEAR))+".txt";
        //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.YEAR))+"_"+strMes+"_"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+".txt";
        this.strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.YEAR))+"_"+strMes+"_"+strDia+".txt";
    }
    
    /**
    * Almacena un archivo de txt el proceso a ejecutarse.
    * @param strMenArchHis
    */
    public void almacenarArchHis(String strMenArchHis){
        try {
            //Date objFec = new Date();
            /*int intHora = fecha.getTime().getHours();
            int intMin = fecha.getTime().getMinutes();
            int intSec = fecha.getTime().getSeconds();*/

            setNombreArchivoHistorico();

            fecha = Calendar.getInstance();
            int intHora = fecha.get(Calendar.HOUR_OF_DAY);
            int intMin = fecha.get(Calendar.MINUTE);
            int intSec = fecha.get(Calendar.SECOND);
            String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
            //BufferedWriter bw = new BufferedWriter(new FileWriter(this.strNomArchHis, true));
            BufferedWriter bw = new BufferedWriter(new FileWriter(getNombreArchivoHistorico(), true));
            PrintWriter salida = new PrintWriter(bw);
            salida.println( strHora+" "+strMenArchHis );
            salida.close();
        }catch(IOException e){ 
            System.out.println(e) ;
        }catch(Exception e){ 
            System.out.println(e) ;
        }
    }
    
    public String getNombreArchivoHistorico(){
        return this.strNomArchHis;
    }
    
    public void setNombreArchivoHistorico(){
        fecha = Calendar.getInstance();
     
        String strDirSis = getDirectorioSistema();
        String strMes = getMes(fecha.get(Calendar.MONTH));
        String strDia = getDia(fecha.get(Calendar.DAY_OF_MONTH));
        String strAnio = String.valueOf(fecha.get(Calendar.YEAR));
        
        //this.strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.YEAR))+"_"+strMes+"_"+strDia+".txt";        
        this.strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+strAnio+"_"+strMes+"_"+strDia+".txt";        
    }
    
    public void setNombreArchivoHistorico(String strNomArchivo){
        this.strNomArchHis = strNomArchivo;       
    }
    
    /*private String getNombreMes(int intMes){
        String strMes="";
        if(intMes==1) strMes="Ene";
        else if(intMes==2) strMes="Feb";
        else if(intMes==3) strMes="Mar";
        else if(intMes==4) strMes="Abr";
        else if(intMes==5) strMes="May";
        else if(intMes==6) strMes="Jun";
        else if(intMes==7) strMes="Jul";
        else if(intMes==8) strMes="Ago";
        else if(intMes==9) strMes="Sep";
        else if(intMes==10) strMes="Oct";
        else if(intMes==11) strMes="Nov";
        else if(intMes==12) strMes="Dic";
        return strMes;
    }*/

    private String getMes(int intMes){
        String strMes="";
        switch (intMes){
            case 0 : strMes="01"; break;
            case 1 : strMes="02"; break;
            case 2 : strMes="03"; break;
            case 3 : strMes="04"; break;
            case 4 : strMes="05"; break;
            case 5 : strMes="06"; break;
            case 6 : strMes="07"; break;
            case 7 : strMes="08"; break;
            case 8 : strMes="09"; break;
            case 9 : strMes="10"; break;
            case 10 : strMes="11"; break;
            case 11 : strMes="12"; break;
        }
        return strMes;
    }

    private String getDia(int intMes){
        String strDia="";
        switch (intMes){
            case 1 : strDia="01"; break;
            case 2 : strDia="02"; break;
            case 3 : strDia="03"; break;
            case 4 : strDia="04"; break;
            case 5 : strDia="05"; break;
            case 6 : strDia="06"; break;
            case 7 : strDia="07"; break;
            case 8 : strDia="08"; break;
            case 9 : strDia="09"; break;
            default: strDia=String.valueOf(intMes);
        }
        return strDia;
    }

    private String getDirectorioSistema(){
        String strRes="";
        try{
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null){
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }catch (Exception e){
            strRes=null;
        }
        return strRes;
    }    
}
