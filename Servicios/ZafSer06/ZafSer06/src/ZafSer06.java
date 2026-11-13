

import com.tuval.utilities.archivos.ArchivosTuval;
import com.tuval.utilities.mail.MailTuval;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hilo de ejecucion recibe parametros del cliente para subir archivos al servidor
 * @author Carlos Lainez
 * Guayaquil 14/06/2011
 */
public class ZafSer06 extends Thread {
    private int PUERTO;
    private ArchivosTuval log;
    private MailTuval mail;
    private String strRutArcLog;
    private String slash = File.separator;

    public ZafSer06(int PUERTO) {
        this.PUERTO = PUERTO;
        //Prepara Archivo de logs
        SimpleDateFormat fmt = new SimpleDateFormat("dd_MMM_yyyy");
        String strFec = fmt.format(new Date());
        strRutArcLog = slash+"Zafiro"+slash+"logs"+slash+"Servicios"+slash+"ZafSer06"+slash+strFec;
        //strRutArcLog = "/home/jflores/Desktop/logsZafSer06/" +slash+strFec;
        if(!System.getProperty("os.name").equals("Linux")){
            strRutArcLog = "C:"+slash+strRutArcLog;
        }
        log = new ArchivosTuval(strRutArcLog);
        mail = new MailTuval();
    }

    @Override
    public void run() {
        GetPutSer();
    }

    private void GetPutSer() {
        ServerSocket socketServidor = null;
        Socket socketCliente = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        String strIdCliente = null;
        String strRutDes = null;
        String strNewNomArc = null;
        String strTipPet = null;

        System.out.println();
        System.out.println("*********************************");
        System.out.println("*** Bienvenido \"Tuval Server\" ***");
        System.out.println("***  \"GetPut\"   Version 1.0   ***");
        System.out.println("*********************************");
        System.out.println(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date()));
        System.out.println();

        try {
            try {

                    log.println("");
                    log.println("*********************************");
                    log.println("*** Bienvenido \"Tuval Server\" ***");
                    log.println("***  \"GetPut\"   Version 1.0   ***");
                    log.println("*********************************");
                    log.println(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date()));
                    log.println("");

                } catch(Exception ex){
                    System.out.format("[Server] Archivo LOG \n",PUERTO);
                    System.out.println(ex.getMessage());

                    try{log.println("[Server] Archivo LOG "+strRutArcLog+" [ERROR]");}catch(Throwable ignore){}
                    try{log.println(ex.getMessage());}catch(Throwable ignore){}

                    try{mail.enviar(this.getClass().getSimpleName(), "[Server] Archivo LOG "+PUERTO+" [ERROR]");}catch(Throwable ignore){}
                    try{mail.enviar(this.getClass().getSimpleName(), ex.getMessage());}catch(Throwable ignore){}
                    System.exit(0);
                }

            try {
                
                socketServidor = new ServerSocket(PUERTO);
                System.out.format("[Server] Escuchando por el puerto %d [OK] \n",PUERTO);
                log.println("[Server] Escuchando por el puerto "+PUERTO+" [OK]");

            } catch (Exception ex) {
                System.out.format("[Server] Escuchando por el puerto %d [ERROR] \n",PUERTO);
                System.out.println(ex.getMessage());

                try{log.println("[Server] Escuchando por el puerto "+PUERTO+" [ERROR]");}catch(Throwable ignore){}
                try{log.println(ex.getMessage());}catch(Throwable ignore){}
                
                try{mail.enviar(this.getClass().getSimpleName(), "[Server] Escuchando por el puerto "+PUERTO+" [ERROR]");}catch(Throwable ignore){}
                try{mail.enviar(this.getClass().getSimpleName(), ex.getMessage());}catch(Throwable ignore){}
                System.exit(0);
            }

            while (true) {
                try{
                    try {
                        socketCliente = socketServidor.accept();
                        System.out.println();
                        System.out.println("[Server] Peticion de cliente detectada [OK]");
                        System.out.println(new Date()+" IP "+socketCliente.getInetAddress());

                        log.println("");
                        log.println("[Server] Peticion de cliente detectada [OK]");
                        log.println(new Date()+" IP "+socketCliente.getInetAddress());
                    } catch (Exception ex) {
                        System.out.println("[Server] Peticion de cliente detectada [ERROR]");
                        System.out.println(ex.getMessage());

                        try{log.println("[Server] Peticion de cliente detectada [ERROR]");}catch(Throwable ignore){}
                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),"[Server] Peticion de cliente detectada [ERROR]");}catch(Throwable ignore){}
                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    try {
                        dis = new DataInputStream(socketCliente.getInputStream());
                        dos = new DataOutputStream(socketCliente.getOutputStream());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());

                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    try {
                        strIdCliente = dis.readUTF();
                        System.out.format("ID Cliente: %s [OK] \n",strIdCliente);

                        log.println("ID Cliente: "+strIdCliente+" [OK]");
                    } catch (Exception ex) {
                        System.out.println("ID Client: [ERROR]");
                        System.out.println(ex.getMessage());

                        try{log.println("ID Client: [ERROR]");}catch(Throwable ignore){}
                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),"ID Client: [ERROR]");}catch(Throwable ignore){}
                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    try {
                        strRutDes = dis.readUTF();
                        System.out.format("[%s] Ruta: %s [OK] \n",strIdCliente,strRutDes);

                        log.println("["+strIdCliente+"] Ruta: "+strRutDes+" [OK]");
                    } catch (Exception ex) {
                        System.out.format("[%s] Ruta: [ERROR] \n",strIdCliente);
                        System.out.println(ex.getMessage());

                        try{log.println("["+strIdCliente+"] Ruta: [ERROR]");}catch(Throwable ignore){}
                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),"["+strIdCliente+"] Ruta: [ERROR]");}catch(Throwable ignore){}
                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    try {
                        strNewNomArc = dis.readUTF();
                        System.out.format("[%s] Nombre de Archivo: %s [OK] \n",strIdCliente,strNewNomArc);

                        log.println("["+strIdCliente+"] Nombre de Archivo: "+strNewNomArc+" [OK]");
                    } catch (Exception ex) {
                        System.out.format("[%s] Nombre de Archivo: [ERROR] \n",strIdCliente);
                        System.out.println(ex.getMessage());

                        try{log.println("["+strIdCliente+"] Nombre de Archivo: [ERROR]");}catch(Throwable ignore){}
                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),"["+strIdCliente+"] Nombre de Archivo: [ERROR]");}catch(Throwable ignore){}
                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    try {
                        strTipPet = dis.readUTF();
                        System.out.format("[%s] Tipo de peticion R/W: %s [OK] \n",strIdCliente,strTipPet);

                        log.println("["+strIdCliente+"] Tipo de peticion R/W: "+strTipPet+" [OK]");
                    } catch (Exception ex) {
                        System.out.format("[%s] Tipo de peticion: [ERROR] \n",strIdCliente);
                        System.out.println(ex.getMessage());

                        try{log.println("["+strIdCliente+"] Tipo de peticion: [ERROR]");}catch(Throwable ignore){}
                        try{log.println(ex.getMessage());}catch(Throwable ignore){}

                        try{mail.enviar(this.getClass().getSimpleName(),"["+strIdCliente+"] Tipo de peticion: [ERROR]");}catch(Throwable ignore){}
                        try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                        //System.exit(0);
                    }
                    if(strTipPet!=null && strRutDes!=null && strNewNomArc!=null){
                        if(strTipPet.equalsIgnoreCase("W")){
                            try {
                                fos = new FileOutputStream(new File(strRutDes + strNewNomArc));
                                System.out.println("[Server] Espacio creado [OK]");

                                log.println("[Server] Espacio creado [OK]");
                            } catch (Exception ex) {
                                System.out.println("[Server] Espacio creado [ERROR]");
                                System.out.println(ex.getMessage());

                                try{log.println("[Server] Espacio creado [ERROR]");}catch(Throwable ignore){}
                                try{log.println(ex.getMessage());}catch(Throwable ignore){}

                                try{mail.enviar(this.getClass().getSimpleName(),"[Server] Espacio creado [ERROR]");}catch(Throwable ignore){}
                                try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                                //System.exit(0);
                            }

                            int len;
                            byte[] buf = new byte[1024];
                            try {
                                while ((len = dis.read(buf)) > 0) {
                                    fos.write(buf, 0, len);
                                    fos.flush();
                                }
                                System.out.println("[Server] Archivo creado [OK]");

                                log.println("[Server] Archivo creado [OK]");
                            } catch (Exception ex) {
                                System.out.println("[Server] Archivo creado [ERROR]");
                                System.out.println(ex.getMessage());

                                try{log.println("[Server] Archivo creado [ERROR]");}catch(Throwable ignore){}
                                try{log.println(ex.getMessage());}catch(Throwable ignore){}

                                try{mail.enviar(this.getClass().getSimpleName(),"[Server] Archivo creado [ERROR]");}catch(Throwable ignore){}
                                try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                                //System.exit(0);
                            }
                        }else{
                            if(strTipPet.equalsIgnoreCase("R")){
                                try {
                                    fis = new FileInputStream(new File(strRutDes + strNewNomArc));
                                    System.out.println("[Server] Archivo encontrado [OK]");

                                    log.println("[Server] Archivo encontrado [OK]");
                                } catch (Exception ex) {
                                    System.out.println("[Server] Archivo encontrado [ERROR]");
                                    System.out.println(ex.getMessage());

                                    try{log.println("[Server] Archivo encontrado [ERROR]");}catch(Throwable ignore){}
                                    try{log.println(ex.getMessage());}catch(Throwable ignore){}

                                    try{mail.enviar(this.getClass().getSimpleName(),"[Server] Archivo encontrado [ERROR]");}catch(Throwable ignore){}
                                    try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                                    //System.exit(0);
                                }

                                byte[] buf = new byte[1024];
                                int len;
                                try {
                                    while ((len = fis.read(buf)) > 0) {
                                        dos.write(buf, 0, len);
                                        dos.flush();
                                    }
                                    System.out.println("[Server] Archivo enviado [OK]");

                                    log.println("[Server] Archivo enviado [OK]");
                                } catch (Exception ex) {
                                    System.out.println("[Server] Archivo enviado [ERROR]");
                                    System.out.println(ex.getMessage());

                                    try{log.println("[Server] Archivo enviado [ERROR]");}catch(Throwable ignore){}
                                    try{log.println(ex.getMessage());}catch(Throwable ignore){}

                                    try{mail.enviar(this.getClass().getSimpleName(),"[Server] Archivo enviado [ERROR]");}catch(Throwable ignore){}
                                    try{mail.enviar(this.getClass().getSimpleName(),ex.getMessage());}catch(Throwable ignore){}
                                    //System.exit(0);
                                }
                            }
                        }
                    }
                } finally {
                    try{fos.close();}catch(Throwable ignore){}
                    try{dos.close();}catch(Throwable ignore){}
                    try{dis.close();}catch(Throwable ignore){}
                    try{socketCliente.close();}catch(Throwable ignore){}
                }
            }
        }finally {
            try{socketServidor.close();}catch(Throwable ignore){}
        }
    }

}
