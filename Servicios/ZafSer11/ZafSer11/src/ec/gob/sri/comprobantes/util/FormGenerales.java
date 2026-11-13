/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ec.gob.sri.comprobantes.util;

import ec.gob.sri.comprobantes.administracion.modelo.Proxy;
import ec.gob.sri.comprobantes.sql.ProxySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author: Dennis Betancourt
 */


public class FormGenerales
{
  public static String devuelveUrlWs(String ambiente, String nombreServicio)
  {
    StringBuilder url = new StringBuilder();
    String direccionIPServicio = null;
    Proxy configuracion = null;
    
    try
    {
      configuracion = new ProxySQL().obtenerProxy();
    }
    catch (Exception ex)
    {
      Logger.getLogger(FormGenerales.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (configuracion != null)
    {
      if (configuracion.getUrl() != null)
      {
        String uri = configuracion.getUrl() + ":" + configuracion.getPuerto();
        ProxyConfig.init(uri);
      }
      if (ambiente.equals(TipoAmbienteEnum.PRODUCCION.getCode()) == true) {
        direccionIPServicio = configuracion.getWsProduccion();
      } else if (ambiente.equals(TipoAmbienteEnum.PRUEBAS.getCode()) == true) {
        direccionIPServicio = configuracion.getWsPruebas();
      }
      url.append(direccionIPServicio);
      
      url.append("/comprobantes-electronicos-ws/");
      url.append(nombreServicio);
      url.append("?wsdl");
      System.out.print(url.toString());
    }
    return url.toString();
  }
  
  /**
   * 
   * @param tipoAmbiente
   * @param nombreServicio
   * @return 
   */
  public static boolean existConnection(String tipoAmbiente, String nombreServicio)
  {
    String url = devuelveUrlWs(tipoAmbiente, nombreServicio);
    int i = 0;
    Boolean respuesta = Boolean.valueOf(false);
    while (i < 3)
    {
      Object c = EnvioComprobantesWs.webService(url);
      if (c == null)
      {
        respuesta = Boolean.valueOf(true);
        break;
      }
      if ((c instanceof WebServiceException)) {
        respuesta = Boolean.valueOf(false);
      }
      i++;
    }
    return respuesta.booleanValue();
  }
  
  /**
   * 
   * @param claveDeAcceso
   * @return 
   */
  public static String obtieneTipoDeComprobante(String claveDeAcceso)
  {
    String abreviatura = null;
    if ((claveDeAcceso != null) && (claveDeAcceso.length() == 49))
    {
      String tipo = claveDeAcceso.substring(8, 10);
      if (tipo.equals(TipoComprobanteEnum.FACTURA.getCode())) {
        abreviatura = TipoComprobanteEnum.FACTURA.getDescripcion();
      } else if (tipo.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())) {
        abreviatura = TipoComprobanteEnum.NOTA_DE_DEBITO.getDescripcion();
      } else if (tipo.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {
        abreviatura = TipoComprobanteEnum.NOTA_DE_CREDITO.getDescripcion();
      } else if (tipo.equals(TipoComprobanteEnum.GUIA_DE_REMISION.getCode())) {
        abreviatura = TipoComprobanteEnum.GUIA_DE_REMISION.getDescripcion();
      } else if (tipo.equals(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode())) {
        abreviatura = TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getDescripcion();
      } else if (tipo.equals(TipoComprobanteEnum.LOTE.getCode())) {
        abreviatura = TipoComprobanteEnum.LOTE.getDescripcion();
      }
    }
    return abreviatura;
  }
  
  /**
   * 
   * @param cadenaLarga
   * @param aInsertar
   * @param longitud
   * @return 
   */
  public static String insertarCaracteres(String cadenaLarga, String aInsertar, int longitud)
  {
    StringBuilder sb = new StringBuilder(cadenaLarga);
    
    int i = 0;
    while ((i = sb.indexOf(" ", i + longitud)) != -1) {
      sb.replace(i, i + 1, aInsertar);
    }
    return sb.toString();
  }
}