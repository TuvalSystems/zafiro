package Principal;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Calendar;

/**
 *
 * @author Rosa Zambrano
 */
public class Main 
{    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private EnvioMail objEnvMail;
    private ZafThreadExeProFecHor objThrExeProFecHor;
    
    private boolean blnEstEjePro = false;
    private int intHorEjejePro = 0;
    private int intCodEmpGrp;
    private int intCodLocGrp;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("ZafSer04 v0.1.4 - Servicio de Envío de correos electrónicos.");
        Main main = new Main();
    }
    
    public Main() 
    {
        try 
        {
            EjecutarProceso();
        } 
        catch (Exception e)
        {
        }
    }
        
    /**
     * Carga driver de conexion a base.
     * @param driver
     * @return
     */
    private boolean cargarDrv_F1(String driver) 
    {
        boolean blnRes = true;
        try 
        {
            Class.forName(driver);
        } 
        catch (ClassNotFoundException e) 
        {
            blnRes = false;
            System.out.println("Error cargarDrv_F1" + e.toString());
        }
        return blnRes;
    }

     /**
     * Función que activa hilo para ejecución del proceso.
     */
     private void EjecutarProceso()
     {
         if (objThrExeProFecHor!=null)
         {
             objThrExeProFecHor.stop();
             objThrExeProFecHor=null;
             objThrExeProFecHor=new ZafThreadExeProFecHor();
             objThrExeProFecHor.start();
         }
         else
         {
             objThrExeProFecHor=new ZafThreadExeProFecHor();
             objThrExeProFecHor.start();
         }
     }

    /**
     * Hilo de Ejecución del Proceso.
     */
    private class ZafThreadExeProFecHor extends Thread 
    {
        public ZafThreadExeProFecHor() 
        {
        }

        @Override
        public void run()   
        {
            try 
            {
                while(true)
                {       
                    objParSis = new ZafParSis();
                    objUti = new ZafUtil();

                    cargarDrv_F1(objParSis.getDriverConexion());   

                    intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
                    intCodLocGrp=1;

                    intHorEjejePro = objParSis.getHoraEjeProceso();
                    System.out.println("Hora ejecución: "+intHorEjejePro);
                    
                    Calendar calendar = Calendar.getInstance();
                    int hora = calendar.get(Calendar.HOUR_OF_DAY);
                    
                    if (intHorEjejePro == hora)              
                    {
                        if(!blnEstEjePro)
                        {
                            System.out.println("Proceso iniciado con exito: " + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), "SELECT current_timestamp") );
                            objEnvMail = new EnvioMail(objParSis);

                            //Función que envía correo de las Notificaciones Pedidos No cerrados.
                            objEnvMail.correoNotificaciones(intCodEmpGrp, intCodLocGrp, objEnvMail.INT_COD_CFG_NOT_PED_NO_CER);
                            //Función que envía correo de las Notificaciones Pedidos No contados
                            objEnvMail.correoNotificaciones(intCodEmpGrp, intCodLocGrp, objEnvMail.INT_COD_CFG_NOT_PED_NO_CON);
                            //Función que envía correo de las Notificaciones de pedidos no ingresados a bodega.
                            //objEnvMail.correoNotificaciones(intCodEmpGrp, intCodLocGrp, objEnvMail.INT_COD_CFG_NOT_PED_NO_ARR);
                            //Función que envía correo de las Notificaciones Documentos de Ajustes de importaciones pendientes de solicitudes de transferencia.
                            objEnvMail.correoNotificaciones(intCodEmpGrp, intCodLocGrp, objEnvMail.INT_COD_CFG_NOT_DOC_AJU_IMP_SOL_PEN);
                            //Función que envía correo de las Notificaciones Documentos de Ajustes de importaciones pendientes de autorización.
                            objEnvMail.correoNotificaciones(intCodEmpGrp, intCodLocGrp, objEnvMail.INT_COD_CFG_NOT_DOC_AJU_IMP_PEN_AUT);                          

                            System.out.println("Proceso finalizado con exito: " + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), "SELECT current_timestamp"));
                            objEnvMail=null;
                            blnEstEjePro=true;
                        }
                    }
                    else
                    {
                        System.out.println("No se ejecuto ningún proceso.");
                        blnEstEjePro=false;
                    }

                    calendar = null;

                    sleep(1800000);  //3600000  1hora   //1800000 30 min    // 900000  15 min  // 600000//10 min  // 300000 5 min 
                }
            }
            catch (InterruptedException e) {    System.err.println("ERROR: " + e);            }
        }
    }
        




}
