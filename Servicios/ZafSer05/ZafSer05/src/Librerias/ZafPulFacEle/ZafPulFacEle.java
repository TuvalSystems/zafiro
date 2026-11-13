/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafPulFacEle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 * v 0.1
 * @author José Marín M.
 */
public class ZafPulFacEle {
     final int puerto = 6010; //PUERTO DE FAC ELE 
 //  String host = "172.16.1.2";
  String host = "172.16.8.30";
   Socket so;
   DataOutputStream mensaje;
   DataInputStream entrada;

   public void iniciar(){
       try {
          so = new Socket(host, puerto);
          mensaje = new DataOutputStream(so.getOutputStream());
          mensaje.writeInt(1);
          so.close();
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
   }

}
