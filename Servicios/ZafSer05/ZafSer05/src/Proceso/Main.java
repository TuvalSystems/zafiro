/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Proceso;  

import Librerias.ZafParSis.ZafParSis;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jayapata
 */
public class Main {
     
    private ZafParSis objParSis;
    private ZafThreadarrancap objThrArr;
    private procesoClass objClass;
  
    private String strCorEleTo="sistemas6@tuvalsa.com";
    private String strEnvCorEle="S";

    private String strNomArchHis="";
    private String strMensRec="";

    private int intHorEjePro=0;
    private int intDiaEjePro=0;

    private boolean blnEstEjepro=false;

    public Main(){

        objParSis=new ZafParSis();
        objClass = new procesoClass(objParSis, null,  "" );

        intHorEjePro= objParSis.getHoraEjeProceso();
        intDiaEjePro= objParSis.getDiaEjeProceso();
        
        iniciaServidor();

   }


private void iniciaServidor(){
   try{
     	
         objThrArr= new ZafThreadarrancap();
         objThrArr.start();
         
      } catch (Exception e) { System.out.println("error  ");   e.printStackTrace();  }
  }
 


private class ZafThreadarrancap extends Thread
{
    Date datFecSis;
    Calendar fec;

    public ZafThreadarrancap()
    {
    }

    @Override
    public void run()
    {
       while(true){

           //datFecSis=new Date();
           fec = Calendar.getInstance();

           //System.out.println("---> "+datFecSis.getDay());  
           System.out.println("ZafSer05 v0.5");  
           System.out.println("Main.ZafThreadarrancap.run: Fecha actual: " + fec.getTime());  
          
          //if( (datFecSis.getDay() == 6 ) ){  // ejecuta dia sabado
            //if(intHorEjejePro==datFecSis.getHours()){
          if( fec.get(Calendar.DAY_OF_WEEK) == intDiaEjePro ){  // ejecuta dia sabado
            if( fec.get(Calendar.HOUR_OF_DAY) == intHorEjePro ){
              if(!blnEstEjepro){

              //System.out.println("Ejecutando..... "+datFecSis.getDay() );
              System.out.println("Ejecutando..... " + fec.get(Calendar.DAY_OF_WEEK) );

              iniciarProceso();
              blnEstEjepro=true;


           }}else
               blnEstEjepro=false;
            }

           
           try
           {
             sleep(1800000);  // 1800000  30 min
           }
           catch (InterruptedException e) {   System.out.println("Excepción: " + e.toString());   }
}}}



private boolean iniciarProceso(){
 boolean blnRes=false;
 String strMes="";
 try{
        //Date objFec = new Date();
        Calendar fecha = Calendar.getInstance();

        String strDirSis= getDirectorioSistema();

        //strMes = getObtenerNomMes(objFec.getMonth()+1);
        strMes = getObtenerNomMes(fecha.get(Calendar.MONTH)+1);
     
        //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer05/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";
        strNomArchHis=strDirSis+"/logs/Servicios/ZafSer05/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf(fecha.get(Calendar.YEAR))+".txt";

        almacenarArchHis("==================================================================");
        
         IniciarProceso objIniProComp = new IniciarProceso(objParSis, strNomArchHis, strDirSis );
         objIniProComp.arrancarProceso();
         objIniProComp=null;
                       
 }catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt ); objClass.enviarCorreo(" "+Evt.toString() );  }
  return blnRes;
}





 public String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
            objClass.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }


private String getObtenerNomMes(int intMes){
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
}

               


/**
 * Almacena un archivo de txt el proceso a ejecutarse.
 * @param strMenArchHis
 */
private void almacenarArchHis(String strMenArchHis){
 try {

      Date objFec = new Date();
      Calendar fecha = Calendar.getInstance();
      //int intHora = fecha.getTime().getHours();
      //int intMin = fecha.getTime().getMinutes();
      //int intSec = fecha.getTime().getSeconds();
      
      int intHora = fecha.get(Calendar.HOUR_OF_DAY);
      int intMin = fecha.get(Calendar.MINUTE);
      int intSec = fecha.get(Calendar.SECOND);
      String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
      
      BufferedWriter bw = new BufferedWriter(new FileWriter(strNomArchHis, true));
      PrintWriter salida = new PrintWriter(bw);
      salida.println( strHora+" "+strMenArchHis );
      salida.close();
  }catch(IOException Ext ){ System.out.println(""+Ext ); }
}




    /**  
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
    }


}
