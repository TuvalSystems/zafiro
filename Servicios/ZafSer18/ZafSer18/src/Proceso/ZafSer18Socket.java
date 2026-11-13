package Proceso;

import Librerias.ZafParSis.ZafParSis;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ZafSer18Socket {
    ZafSer18 zafSer18 = new ZafSer18();
    public int intPuerto = 0;
    private int contador = 0;

    public static void main(String argv[])  {
        new ZafSer18Socket();
    }
    
    public ZafSer18Socket() {
    	ZafParSis sis = new ZafParSis();
    	intPuerto = sis.getPuertoCom();
    	iniciarTarea();
    	iniciarSocket();
    	
	}

    public void iniciarSocket() {
    	Calendar cal = Calendar.getInstance();
        System.out.println("INICIO ServerSocket. Fecha:" + cal.getTime() + " Puerto:" +intPuerto);
        System.out.println("strVersion:" + "ZafSer18 v0.1");
        try {
            ServerSocket serverSocket = new ServerSocket(intPuerto);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                callServ18();
            }
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        	//iniciarSocket();
        }
        
    }
    
    private void iniciarTarea(){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 07);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 03);
		new Timer().schedule(new Tarea(), c.getTime(), 1000*60*60*24);
    }

    public void callServ18() {
        try {
            System.out.println("begin");
            System.out.println("Contador:" + contador);
            zafSer18.iniciar();
            System.out.println("end;");
            contador++;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
	class Tarea extends TimerTask {
		public void run() {
			System.out.println("TimerTask");
			try {
                                System.out.println("*****INICIA*****");
				zafSer18.iniciar();
                                System.out.println("*****FINALIZA******");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
