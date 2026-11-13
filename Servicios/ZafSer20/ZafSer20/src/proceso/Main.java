/*
 * To change this template, choose Tools | tbm_sercliserloc
 * and open the template in the editor.
 */
     
package proceso;
     
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *      
 * @author jayapata
 */
public class Main {

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafThreadarrancap objThrArr;
 
    String strNomArchHis="";
    String strFormatDate ="d/m/y";
    String strEstEjeProeFecHorDet="N";
    String strFechHorEjePro="";
 
    int intPuerto=0;
    int intEstMens=0;
    int intTiempoHiloDormido=0;
    int intHorEjejePro=0;
 

    boolean blnEjeProHor=true;

    public Main(){
        try{
            System.out.println("ZafSer20 v 0.1"); 
            objParSis=new ZafParSis();          
            objUti = new ZafUtil();        
            cargarDrv_F1(objParSis.getDriverConexion());    

            intPuerto    = objParSis.getPuertoCom();
            intTiempoHiloDormido= objParSis.getTiempoHiloDormido();
            intHorEjejePro=  objParSis.getHoraEjeProceso();

            strEstEjeProeFecHorDet=objParSis.getEstEjeProFecHor();
            strFechHorEjePro= objParSis.getFecHorEjePro();
       
            iniciaServidorEntreCompañias();
        }
        catch (Exception e) { 
            System.out.println("error  "+e.toString() );    
        }
    }
 

          
    private void iniciaServidorEntreCompañias(){
        try{
            objThrArr= new ZafThreadarrancap();
            objThrArr.start();
        } 
        catch (Exception e) { 
            System.out.println("error  "+e.toString() );    
        }
    }

    

    private class ZafThreadarrancap extends Thread{
        public ZafThreadarrancap()
        {
        }

            @Override
        public void run(){
            while(true){
                try{
                    iniciarProceso();
                    sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.
                }
                catch(Exception e) {   
                    e.printStackTrace();           
                }
            }
        }
    }

 


    private boolean iniciarProceso(){
        boolean blnRes=false;

        try{
            java.util.Date objFec = new java.util.Date();
            Calendar calendario = new GregorianCalendar();
            int intDia = calendario.get(Calendar.DATE);
            calendario=null;
            System.out.println("ZafSer19 v 0.1"); 
            System.out.println("BASE: " + objParSis.getStringConexion());
            System.out.println("HORA: " + objFec.getHours());
            System.out.println("HORA DE EJECUCION: " + intHorEjejePro);

            if( objFec.getHours() == intHorEjejePro  ){
                if(blnEjeProHor){
                    blnEjeProHor=false;
                    ZafGenDocDepAct obj1 = new ZafGenDocDepAct();
                    if(obj1.iniciaGeneracionDepreciacion()){
                        blnRes=true;
                        System.out.println("J END GAME");
                    }
                    obj1 = null;
                    
                    
                }
            }
            else {
                   blnEjeProHor=true;
            }
            System.out.println("sale ");
            objFec=null;
        }
        catch(Exception Evt){ 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
             
        }
        return blnRes;
    }

    
    private boolean modificarConfiguracionVendedores(){
        boolean blnRes=false;
        try{
           Librerias.ZafCfgVenLocCli.ZafCfgVenLocCli objCfgVenLocCli = new Librerias.ZafCfgVenLocCli.ZafCfgVenLocCli(objParSis, null);
            if(objCfgVenLocCli.modificaConfiguracionActual()){
                blnRes=true;
            }
            objCfgVenLocCli = null;
        }
        catch(Exception Evt){ 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
             
        }
        return blnRes;
    }
  


 
 public String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            java.net.URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=java.net.URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
             
        }
        return strRes;
    }
  

    /**
     * Carga driver de conceccion a base.
     * @param driver
     * @return
     */
    private boolean cargarDrv_F1(String driver){
        boolean blnRes=true;
        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){  
            blnRes=false;    
            System.out.println(""+ e.toString());  
        }
        return blnRes;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
    }

}

