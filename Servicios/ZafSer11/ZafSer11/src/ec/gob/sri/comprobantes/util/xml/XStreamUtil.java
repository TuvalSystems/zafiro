/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ec.gob.sri.comprobantes.util.xml;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import ec.gob.sri.comprobantes.modelo.ComprobanteXml;
import ec.gob.sri.comprobantes.modelo.LoteXml;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.RespuestaLote;
import java.io.Writer;

/**
 *
 * @author: Dennis Betancourt
 */
public class XStreamUtil
{
  public static XStream getLoteXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      /*public HierarchicalStreamWriter createWriter(Writer out)
      {
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }*/
      
      public HierarchicalStreamWriter createWriter(Writer out)
      {
        HierarchicalStreamWriter a;
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
        a = null;
        return a;
      }
    });
    xstream.alias("lote", LoteXml.class);
    xstream.alias("comprobante", ComprobanteXml.class);

    xstream.registerConverter(new ComprobanteXmlConverter());
    
    return xstream;
  }
  
  public static XStream getRespuestaXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      /*public HierarchicalStreamWriter createWriter(Writer out)
      {
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }*/
      
      public HierarchicalStreamWriter createWriter(Writer out)
      { HierarchicalStreamWriter a;
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
        a = null;
        return a;
      }
    });
    xstream.alias("respuesta", RespuestaComprobante.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());
    
    return xstream;
  }
  
  public static XStream getRespuestaLoteXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      /*public HierarchicalStreamWriter createWriter(Writer out)
      {
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }*/
      
      public HierarchicalStreamWriter createWriter(Writer out)
      { HierarchicalStreamWriter a;
        new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
        a = null;
        return a;
      }
    });
    xstream.alias("respuesta", RespuestaLote.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());
    
    return xstream;
  }
}