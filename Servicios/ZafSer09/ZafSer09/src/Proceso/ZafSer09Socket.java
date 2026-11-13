package Proceso;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import com.tuval.util.ZafParSis;

public class ZafSer09Socket 
{
    ZafSer09 zafSer09 = new ZafSer09();
    public int intPuerto = 0;
    private int contador = 0;

    public static void main(String argv[])  {
        new ZafSer09Socket();
    }
    
    public ZafSer09Socket() {
    	ZafParSis sis = new ZafParSis();
    	intPuerto = sis.getPuertoCom();
    	iniciarTarea();
    	iniciarSocket();
    	
    }

    public void iniciarSocket() {
    	Calendar cal = Calendar.getInstance();
        System.out.println("INICIO ServerSocket. Fecha:" + cal.getTime() + " Puerto:" +intPuerto);
        System.out.println("strVersion:" + "v4.6");
        try {
            ServerSocket serverSocket = new ServerSocket(intPuerto);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                callServ9();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            //iniciarSocket();
        }
    }
    
    private void iniciarTarea(){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 01);
        c.set(Calendar.SECOND, 03);
        new Timer().schedule(new Tarea(), c.getTime(), 1000*60*60*24);
    }

    public void callServ9() {
        try {
            zafSer09.iniciar();
            contador++;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    class Tarea extends TimerTask {
        public void run() {
            System.out.println("TimerTask");
            try {
                System.out.println("Inicia Servicio09");
                zafSer09.iniciar();
                System.out.println("Finaliza Servicio09");
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
    }
    
    
    

}
