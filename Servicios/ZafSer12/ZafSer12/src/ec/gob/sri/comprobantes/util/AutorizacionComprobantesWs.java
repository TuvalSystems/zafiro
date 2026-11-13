/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer12_OffLine
package ec.gob.sri.comprobantes.util;

import com.thoughtworks.xstream.XStream;

import ec.gob.sri.comprobantes.util.xml.XStreamUtil;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion.Mensajes;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante.Autorizaciones;
import ec.gob.sri.comprobantes.ws.aut.RespuestaLote;
//import ec.gob.sri.comprobantes.ws.aut.RespuestaLote.Autorizaciones;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author: Dennis Betancourt
 */
public class AutorizacionComprobantesWs
{
  private AutorizacionComprobantesOfflineService service;
  public static final String ESTADO_AUTORIZADO = "AUTORIZADO";
  public static final String ESTADO_NO_AUTORIZADO = "NO AUTORIZADO";
  public static String strFecAut, strNumAut;
  
  public AutorizacionComprobantesWs(String wsdlLocation)
  {
    try
    {
      this.service = new AutorizacionComprobantesOfflineService(new URL(wsdlLocation), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService"));
    }
    catch (Exception ex)
    {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
      //JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Se ha producido un error ", 0);
    }
  }
  
  public RespuestaComprobante llamadaWSAutorizacionInd(String claveDeAcceso)
  {
    RespuestaComprobante response = null;
    try
    {
      AutorizacionComprobantesOffline port = this.service.getAutorizacionComprobantesOfflinePort();
      response = port.autorizacionComprobante(claveDeAcceso);
    }
    catch (Exception e)
    {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, e);
      return response;
    }
    return response;
  }
  
  public RespuestaLote llamadaWsAutorizacionLote(String claveDeAcceso)
  {
    RespuestaLote response = null;
    try
    {
      AutorizacionComprobantesOffline port = this.service.getAutorizacionComprobantesOfflinePort();
      response = port.autorizacionComprobanteLote(claveDeAcceso);
    }
    catch (Exception e)
    {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, e);
      return response;
    }
    return response;
  }
  
  public static String autorizarComprobanteIndividual(String claveDeAcceso, String nombreArchivo, String tipoAmbiente, ArrayList arlDatAut)
  {
    StringBuilder mensaje = new StringBuilder();
    String strMes, strDia, strHor, strMin, strSeg;
    int intYear, intMes, intDia, intHor, intMin, intSeg;
    ArrayList arlMsjClvAccReg = new ArrayList();
    
    try
    {
      //String dirAutorizados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.AUTORIZADOS.getCode()).getPath();
      //String dirNoAutorizados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.NO_AUTORIZADOS.getCode()).getPath();
      //String dirAutorizados = "C:\\Dennis\\Dennis\\Borrame";
      //String dirNoAutorizados = "C:\\Dennis\\Dennis\\Borrame";
      
      RespuestaComprobante respuesta = null;
      for (int i = 0; i < 5; i++)
      {
        respuesta = new AutorizacionComprobantesWs(FormGenerales.devuelveUrlWs(tipoAmbiente, "AutorizacionComprobantesOffline")).llamadaWSAutorizacionInd(claveDeAcceso);
        if (!respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
          break;
        }
        Thread.currentThread();Thread.sleep(300L);
      }
      
      arlMsjClvAccReg.add(0, "");
      arlMsjClvAccReg.set(0, "N"); //Arreglo usado para indicar si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
      
      int i;
      if (respuesta != null)
      {
        i = 0;
        for (Autorizacion item : respuesta.getAutorizaciones().getAutorizacion())
        {
          mensaje = new StringBuilder(); //DBN
          mensaje.append(item.getEstado());
          item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
          XStream xstream = XStreamUtil.getRespuestaXStream();
          Writer writer = null;
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          writer = new OutputStreamWriter(outputStream, "UTF-8");
          writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
          //xstream.toXML(item, writer); //DBN_OJO, se cae por "null"
          String xmlAutorizacion = outputStream.toString("UTF-8");
          
          //stringToArchivo("C://Dennis//Dennis//Borrame" + File.separator + nombreArchivo, item); //DBN_Prueba
          //stringToArchivo(tipoAmbiente, nombreArchivo, item); //DBN_Prueba
          
          if (item.getEstado().equals("AUTORIZADO")) //DBN
          {
            //ArchivoUtils.stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion); //DBN
            intYear = item.getFechaAutorizacion().getYear();
            intMes = item.getFechaAutorizacion().getMonth();
            intDia = item.getFechaAutorizacion().getDay();
            intHor = item.getFechaAutorizacion().getHour();
            intMin = item.getFechaAutorizacion().getMinute();
            intSeg = item.getFechaAutorizacion().getSecond();
            strMes = String.format("%02d", intMes);
            strDia = String.format("%02d", intDia);
            strHor = String.format("%02d", intHor);
            strMin = String.format("%02d", intMin);
            strSeg = String.format("%02d", intSeg);
            strFecAut = intYear + "-" + strMes + "-" + strDia + " " + strHor + ":" + strMin + ":" + strSeg;
            arlDatAut.set(0, item.getNumeroAutorizacion());
            arlDatAut.set(1, strFecAut);
            arlDatAut.set(2, "N"); //Indice 2: Indica si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
            stringToArchivo(tipoAmbiente, nombreArchivo, item);
            //VisualizacionRideUtil.decodeArchivoBase64(dirAutorizados + File.separator + nombreArchivo, item.getNumeroAutorizacion(), item.getFechaAutorizacion().toString());
            break;
          }
          
          if (item.getEstado().equals("NO AUTORIZADO"))
          {
            //ArchivoUtils.stringToArchivo(dirNoAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
            mensaje.append("|" + obtieneMensajesAutorizacion((item), arlMsjClvAccReg));
            
            if (arlMsjClvAccReg.get(0) == "S")
            {  //Si es verdadero, significa que el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". Por tanto, se va a proceder
               //a buscar la fecha y numero de autorizacion
               buscarNumAut(respuesta, tipoAmbiente, nombreArchivo);
               arlDatAut.set(0, strNumAut);
               arlDatAut.set(1, strFecAut);
               arlDatAut.set(2, "S"); //Indice 2: Indica que el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
            }
               
            //verificarOCSP(item);
            break;
          }
          i++;
        }
      }
      if ((respuesta == null) || (respuesta.getAutorizaciones().getAutorizacion().isEmpty() == true))
      {
        //mensaje.append("TRANSMITIDO SIN RESPUESTA|Ha ocurrido un error en el proceso de la Autorización, por lo que se traslado el archivo a la carpeta de: transmitidosSinRespuesta"); //DBN
        mensaje.append("TRANSMITIDO SIN RESPUESTA|Ha ocurrido un error en el proceso de la Autorización. El valor de Respuesta es NULL o esta vacio");
        
        /*String dirFirmados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.FIRMADOS.getCode()).getPath();
        String dirFirmados = "C:\\Dennis\\Dennis\\Borrame";
        String dirTransmitidos = dirFirmados + File.separator + "transmitidosSinRespuesta";
        
        File transmitidos = new File(dirTransmitidos);
        if (!transmitidos.exists()) {
          new File(dirTransmitidos).mkdir();
        }
        File archivoFirmado = new File(new File(dirFirmados), nombreArchivo);
        
        if (!ArchivoUtils.copiarArchivo(archivoFirmado, transmitidos.getPath() + File.separator + nombreArchivo)) {
          mensaje.append("\nError al mover archivo a carpeta de Transmitidos sin Respuesta");
        } else {
          archivoFirmado.delete();
        }*/
      }
    }
    catch (Exception ex)
    {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
    }
    return mensaje.toString();
  }
  
  private static void stringToArchivo(String tipoAmbiente, String rutaNomArchivo, Autorizacion item)
  {
    String strMes, strDia, strHor, strMin, strSeg, strXml, strAux, strLocFecAut, strAmb;
    int intYear, intMes, intDia, intHor, intMin, intSeg;
    PrintWriter xml;
    
    try
    {
      strLocFecAut = "";
      intYear = item.getFechaAutorizacion().getYear();
      intMes = item.getFechaAutorizacion().getMonth();
      intDia = item.getFechaAutorizacion().getDay();
      intHor = item.getFechaAutorizacion().getHour();
      intMin = item.getFechaAutorizacion().getMinute();
      intSeg = item.getFechaAutorizacion().getSecond();
      strMes = String.format("%02d", intMes);
      strDia = String.format("%02d", intDia);
      strHor = String.format("%02d", intHor);
      strMin = String.format("%02d", intMin);
      strSeg = String.format("%02d", intSeg);
      strLocFecAut = strDia + "/" + strMes + "/" + intYear + " " + strHor + ":" + strMin + ":" + strSeg;

      if (tipoAmbiente.equals("1"))
         strAmb = "PRUEBAS";
      else if (tipoAmbiente.equals("2"))
         strAmb = "PRODUCCION";
      else
         strAmb = "";
            
      strAux = item.getComprobante();
      //Hay ocasiones que, en el XML que retorna el Servicio Web, viene 2 veces la leyenda "CDATA"
      strAux = strAux.replace("<![CDATA[<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
      strAux = strAux.replace("]]>]]>", "");
      
      //Hay ocasiones que, en el XML que retorna el Servicio Web, viene una sola vez la leyenda "CDATA"
      strAux = strAux.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
      strAux = strAux.replace("]]>", "");

      xml = new PrintWriter(new FileOutputStream(rutaNomArchivo));
      
      strXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
      strXml += "<autorizacion> ";
      strXml += "<estado>AUTORIZADO</estado> ";
      strXml += "<numeroAutorizacion>" + item.getNumeroAutorizacion() + "</numeroAutorizacion> ";
      strXml += "<fechaAutorizacion>" + strLocFecAut + "</fechaAutorizacion> ";
      strXml += "<ambiente>" + strAmb + "</ambiente> ";
      strXml += "<comprobante><![CDATA[";
      strXml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
      strXml += strAux;
      strXml += "]]></comprobante> ";
      strXml += "</autorizacion>";

      xml.println(strXml);
      xml.println();
      xml.close();
    }
    
    catch (Exception ex)
    {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private static void buscarNumAut(RespuestaComprobante respuesta, String tipoAmbiente, String nombreArchivo)
  {
     String strMes, strDia, strHor, strMin, strSeg;
     int intYear, intMes, intDia, intHor, intMin, intSeg;
    
     try
     {
        strFecAut = "";
        strNumAut = "";
      
        if (respuesta != null)
        {  for (Autorizacion item : respuesta.getAutorizaciones().getAutorizacion())
           {  if (item.getEstado().equals("AUTORIZADO"))
              {  intYear = item.getFechaAutorizacion().getYear();
                 intMes = item.getFechaAutorizacion().getMonth();
                 intDia = item.getFechaAutorizacion().getDay();
                 intHor = item.getFechaAutorizacion().getHour();
                 intMin = item.getFechaAutorizacion().getMinute();
                 intSeg = item.getFechaAutorizacion().getSecond();
                 strMes = String.format("%02d", intMes);
                 strDia = String.format("%02d", intDia);
                 strHor = String.format("%02d", intHor);
                 strMin = String.format("%02d", intMin);
                 strSeg = String.format("%02d", intSeg);
                 strFecAut = intYear + "-" + strMes + "-" + strDia + " " + strHor + ":" + strMin + ":" + strSeg;
                 strNumAut = item.getNumeroAutorizacion();
                 stringToArchivo(tipoAmbiente, nombreArchivo, item);
                 break;
              }
           }
        }
     }
    
     catch (Exception ex)
     {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
     }
  }
  /*
  public static List<Autorizacion> autorizarComprobanteLote(String claveDeAcceso, String nombreArchivoLote, int timeout, int cantidadArchivos, String tipoAmbiente)
  {
    List<Autorizacion> autorizaciones = new ArrayList();
    RespuestaLote respuestaAutorizacion = null;
    try
    {
      //dirAutorizados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.AUTORIZADOS.getCode()).getPath();
      //dirRechazados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.NO_AUTORIZADOS.getCode()).getPath();
      //dirFirmados = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.FIRMADOS.getCode()).getPath();
      String dirAutorizados = "C:\\Dennis\\";
      String dirRechazados = "C:\\Dennis\\";
      String dirFirmados = "C:\\Dennis\\";

      Thread.currentThread();Thread.sleep(timeout * cantidadArchivos * 1000);
      for (int i = 0; i < 5; i++)
      {
        respuestaAutorizacion = new AutorizacionComprobantesWs(FormGenerales.devuelveUrlWs(tipoAmbiente, "AutorizacionComprobantes")).llamadaWsAutorizacionLote(claveDeAcceso);
        if (!respuestaAutorizacion.getAutorizaciones().getAutorizacion().isEmpty()) {
          break;
        }
        Thread.currentThread();Thread.sleep(500L);
      }
      String comprobantesProcesados; //DBN
      comprobantesProcesados = respuestaAutorizacion.getNumeroComprobantesLote();
      for (Autorizacion item : respuestaAutorizacion.getAutorizaciones().getAutorizacion())
      {
        item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
        String claveAcceso = ArchivoUtils.obtieneClaveAccesoAutorizacion(item);
        
        XStream xstream = XStreamUtil.getRespuestaLoteXStream();
        Writer writer = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        
        xstream.toXML(item, writer);
        
        String nombreArchivoConRespuesta = claveAcceso + ".xml";
        String xmlAutorizacion = outputStream.toString("UTF-8");
        if (item.getEstado().equals("AUTORIZADO")) {
          ArchivoUtils.stringToArchivo(dirAutorizados + File.separator + nombreArchivoConRespuesta, xmlAutorizacion);
        } else {
          ArchivoUtils.stringToArchivo(dirRechazados + File.separator + nombreArchivoConRespuesta, xmlAutorizacion);
        }
        File archivoABorrar = new File(dirFirmados + File.separator + nombreArchivoConRespuesta);
        if (archivoABorrar.exists() == true) {
          archivoABorrar.delete();
        }
        item.setEstado(nombreArchivoConRespuesta + "|" + item.getEstado() + "|" + comprobantesProcesados);
        autorizaciones.add(item);
      }
    }
    catch (Exception ex)
    {
      String dirAutorizados;
      String dirRechazados;
      String dirFirmados;
      String comprobantesProcesados;
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
    }
    return autorizaciones;
  }
  */
  public static String obtieneMensajesAutorizacion(Autorizacion autorizacion, ArrayList arlMsjClvAccReg)
  {
     StringBuilder mensaje = new StringBuilder();
     
     for (Mensaje m : autorizacion.getMensajes().getMensaje())
     {
        if (m.getInformacionAdicional() != null)
        {  
           mensaje.append("\n" + m.getMensaje() + ": " + m.getInformacionAdicional());
        }
        else 
        {
           mensaje.append("\n" + m.getMensaje());
        }
        
        if (m.getMensaje().equals("CLAVE ACCESO REGISTRADA"))
           arlMsjClvAccReg.set(0, "S"); //Indica que el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
     }
     
     return mensaje.toString();
  }
  /*
  public static boolean verificarOCSP(Autorizacion autorizacion)
    throws SQLException, ClassNotFoundException
  {
    boolean respuesta = true;
    for (Mensaje m : autorizacion.getMensajes().getMensaje()) {
      if (m.getIdentificador().equals("61"))
      {
        int i = JOptionPane.showConfirmDialog(null, "No se puede validar el certificado digital.\n Desea emitir en contingencia?", "Advertencia", 0);
        if (i == 0)
        {
          Emisor emisor = new EmisorSQL().obtenerDatosEmisor();
          FormGenerales.actualizaEmisor(TipoEmisionEnum.CONTINGENCIA.getCode(), emisor);
        }
        respuesta = false;
      }
    }
    return respuesta;
  }*/
}