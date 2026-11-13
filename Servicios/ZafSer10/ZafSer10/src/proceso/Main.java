

package proceso;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jayapata
 */
public class Main {

 private ZafParSis objParSis;
 private ZafThreadarrancap objThrArr;
 procesoClass objClass;
 String strNomArchHis="";
 String strFormatDate ="d/m/y";
 String strEstEjeProeFecHorDet="N";
 String strFechHorEjePro="";
 private ZafThreadExeProFecHor objThrExeProFecHor;
 ZafUtil objUti;

 int intPuerto=0;
 int intEstMens=0;
 int intTiempoHiloDormido=0;
 int intHorEjejePro=0;
 int intDiaResFecCorte=1;
// int intDiaResFecCorte=0;
 java.text.SimpleDateFormat objSimDatFor;
    java.util.Date datFecSis;

 boolean blnEjeProHor=true;

    public Main(){
        objParSis=new ZafParSis();
        objClass = new procesoClass(objParSis, null,  "" );
        objUti = new ZafUtil();
        cargarDrv_F1(objParSis.getDriverConexion());


        intPuerto    = objParSis.getPuertoCom();
        intTiempoHiloDormido= objParSis.getTiempoHiloDormido();
        intHorEjejePro=  objParSis.getHoraEjeProceso();

        strEstEjeProeFecHorDet=objParSis.getEstEjeProFecHor();
        strFechHorEjePro= objParSis.getFecHorEjePro();

        if(strEstEjeProeFecHorDet.equals("N")) {
            iniciaServidorEntreCompañias();
        }
        else {
            EjecutarProcedoFechHoraDet();
        }

        actualizaDatos();

    }//fin de main

private void EjecutarProcedoFechHoraDet(){
    System.out.println("EjecutarProcedoFechHoraDet  ");
     objThrExeProFecHor=new ZafThreadExeProFecHor();
     objThrExeProFecHor.start();
}


 private class ZafThreadExeProFecHor extends Thread
 {
     java.util.Date datFecSis, datFecExe;
      public ZafThreadExeProFecHor()
      {
          datFecExe=objUti.parseDate(strFechHorEjePro, "yyyy/MM/dd HH:mm:ss");
      }
        @Override
      public void run()
      {
          System.out.println("ZafThreadExeProFecHor  ");
         datFecExe=objUti.parseDate(strFechHorEjePro, "yyyy/MM/dd HH:mm:ss");
         while (true)
         {
           datFecSis=new java.util.Date();
           if (datFecExe.before(datFecSis))
           {
                iniciaServidorEntreCompañias();
                break;
           }
           try
           {

             sleep(2000);
           }
           catch (InterruptedException e) {   System.out.println("Excepción: " + e.toString());   }
       }
     }
   }


private void iniciaServidorEntreCompañias(){
   try{
  	 objThrArr= new ZafThreadarrancap();
         objThrArr.start();
      } catch (Exception e) { System.out.println("error  "+e.toString() );    }
  }



private class ZafThreadarrancap extends Thread
{


    public ZafThreadarrancap()
    {
    }

        @Override
    public void run()
    {
       while(true){
          try{
        System.out.println("ZafThreadarrancap  ");
            iniciarProceso();

            sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.


          }catch(Exception e) {   e.printStackTrace();     objClass.enviarCorreo(" "+e.toString() );       }
}}}

  public int [] _getFecha(String Fecha){
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(Fecha,"/");
        int fec[] = new int[3] ;
        char formato[]  =  strFormatDate.replaceAll("/", "").toCharArray();

         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){
             String  strDate = MiToken.nextToken().toString();
             if(strDate.trim().equals("")){
                fec = null ;
                return fec;
             }
             switch (formato[i]){
                 case 'd':
                     fec[0] = Integer.parseInt(strDate.trim());
                     break;
                 case 'm':
                     fec[1] = Integer.parseInt(strDate.trim());
                     break;
                 case 'y':
                     fec[2] = Integer.parseInt(strDate.trim());
                     break;
             }
         }
        return fec;
    }



private boolean iniciarProceso(){
 boolean blnRes=false;
 String strMes="";
 try{
    objSimDatFor=new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    datFecSis=new java.util.Date();
     System.out.println("ZafSer10 v3.2 iniciarProceso"); 
        java.util.Date objFec = new java.util.Date();
        java.util.Calendar fecha = java.util.Calendar.getInstance();

        SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");   //dd-MM-yyyy");
        String strFechaCorte = d1.format(restarDias(intDiaResFecCorte));

        int Fecha[] = _getFecha(strFechaCorte);
        strFechaCorte = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

        String strDirSis= getDirectorioSistema();
        strMes = getObtenerNomMes(objFec.getMonth()+1);
        strNomArchHis=strDirSis+"/logs/Servicios/ZafSer10/"+String.valueOf(fecha.get(java.util.Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";


       System.out.println("HORA: " + objFec.getHours());
       System.out.println("HORA DE EJECUCION: " + intHorEjejePro);
       if(obtenerEstArch()){
         if( objFec.getHours() == intHorEjejePro  ){
             objClass.enviarCorreo("Hora actual inicia proceso......" + objSimDatFor.format(datFecSis) );
           if(blnEjeProHor){
               System.out.println("Entra.... ");
            blnEjeProHor=false;
            objClass.almacenarArchHis(strNomArchHis, "==================================================================");
            objClass.almacenarArchHis(strNomArchHis, "Iniciar Proceso entre Compañias.....");
            IniciarProceso objIniProComp = new IniciarProceso(objParSis, strFechaCorte, strNomArchHis, strDirSis );
            objIniProComp.arrancarProceso();
            objIniProComp=null;
           }
         }else blnEjeProHor=true;
        }else{
           objClass.almacenarArchHis(strNomArchHis, "==================================================================");
           objClass.almacenarArchHis(strNomArchHis, "Iniciar Proceso Compañia Faltante.....");
           IniciarProceso objIniProComp = new IniciarProceso(objParSis, strFechaCorte, strNomArchHis, strDirSis );
           objIniProComp.arrancarProceso();
           objIniProComp=null;
        }
 }
 catch(Exception Evt)  {
     blnRes=false;
     System.out.println("Error 1 "+Evt );
     objClass.enviarCorreo(" "+Evt.toString() );
 }
  return blnRes;
}


    private boolean obtenerEstArch(){
         boolean blnRes=false;
         try{

           String strDirSis= getDirectorioSistema();
           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer10/config/ZafSer10_Est_Emp.properties");
           proArc.load(fis);
           fis.close();


           if(proArc.getProperty("ProcesadoTuv").toString().equals("OK")){
            if(proArc.getProperty("ProcesadoDim").toString().equals("OK")){
             if(proArc.getProperty("ProcesadoCtQ").toString().equals("OK")){
              if(proArc.getProperty("ProcesadoCtM").toString().equals("OK")){
               if(proArc.getProperty("ProcesadoCSD").toString().equals("OK")){
                 if(proArc.getProperty("ProcesadoCCU").toString().equals("OK")){ /* José Marín M 11/Nov/2014 */
                   if(proArc.getProperty("ProcesadoDIN").toString().equals("OK")){ /* José Marín M 28/Ene/2015 */
                        if(proArc.getProperty("ProcesadoTIN").toString().equals("OK")){ /* José Marín M 28/Ene/2015 */
                         if(proArc.getProperty("ProcesadoCtQuIn").toString().equals("OK")){ /* José Marín M Castek Quito - Inmaconsa 25/Sep/2015 */
                                if(proArc.getProperty("ProcesadoCtMnIn").toString().equals("OK")){ /* José Marín M Castek Manta - Inmaconsa 25/Sep/2015 */
                                    if(proArc.getProperty("ProcesadoCtStIn").toString().equals("OK")){ /* José Marín M Castek Santo Domingo - Inmaconsa 25/Sep/2015 */
                                        if(proArc.getProperty("ProcesadoCtCuIn").toString().equals("OK")){ /* José Marín M Castek Cuenca - Inmaconsa 25/Sep/2015 */
                                            System.out.println("PAssssaaa");
                                            blnRes=true;
                                        }
                                    }
                                }
                            }
                        }
            }
            }}}}}}

           proArc=null;

        }catch(Exception e){  System.out.println("Error 1 "+e.toString() ); }
      return blnRes;
     }


public  Date restarDias(int dias){

    java.util.Calendar  fecha;
    fecha = java.util.Calendar.getInstance();
    //fecha.add(java.util.Calendar.DATE, -dias);
    return  fecha.getTime();

}



/***********************************************************************************/

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
            objClass.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }

/***********************************************************************************/

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


/*************************************************************************************/



/***********************************************************************************/


/**
 * Carga driver de conceccion a base.
 * @param driver
 * @return
 */
private boolean cargarDrv_F1(String driver){
 boolean blnRes=true;
 try{
     Class.forName(driver);
  }catch (ClassNotFoundException e){  blnRes=false;    System.out.println(""+ e.toString());  }
 return blnRes;
}

//--CORRECCIÓN DE DOCUMENTOS DESCUADRADOS.
//realiza un update de cuadre de valores en la tabla de movimientos del inventario tbm_detMovInv al final de la ejecucion del hilo
private void actualizaDatos(){

    java.sql.Statement stm = null;

    ZafParSis objZafParSis=new ZafParSis();
    String str= " UPDATE tbm_detMovInv SET nd_cosUni=nd_preUni, nd_cosUniGrp=nd_preUni, nd_cosTot=nd_tot, nd_cosTotGrp=nd_tot "
            + " WHERE co_tipDoc IN (205, 206) AND (nd_cosUni<>nd_preUni OR nd_cosUni<>nd_cosUniGrp OR nd_cosTot<>nd_tot OR nd_cosTot<>nd_cosTotGrp)";

    try {
        Connection conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
    if (conn !=null) {
            stm=conn.createStatement();
            stm.executeUpdate(str);
    }
    stm.close();
    conn.close();

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

}//fin de actualiza datos para la --CORRECCIÓN DE DOCUMENTOS DESCUADRADOS.





    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
    }




}

