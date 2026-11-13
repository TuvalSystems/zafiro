/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ec.gob.sri.comprobantes.sql;

import ec.gob.sri.comprobantes.administracion.modelo.Proxy;
import ec.gob.sri.comprobantes.util.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author: Dennis Betancourt
 */
public class ProxySQL
{
  private Connection conn = null;
  private Statement statement = null;
  private ResultSet rs;
  private String url;
  
  public ProxySQL()
  {
    this.url = null;
    getStringURL();
  }
  
  private String getStringURL()
  {
    if (Constantes.obtenerUrlBD() != null) {
      this.url = ("jdbc:hsqldb:file:" + Constantes.obtenerUrlBD() + "/" + "PROXY");
    }
    return null;
  }
  
  private void crear(Proxy proxy)
    throws ClassNotFoundException, SQLException
  {
    Constantes.cargarJDC();
    getConecction();
    String sql = "INSERT INTO PROXY(CODIGO,URL,PUERTO,USUARIO,CLAVE ,WS_PRODUCCION ,WS_PRUEBAS ) VALUES (1,'" + proxy.getUrl() + "','" + proxy.getPuerto() + "','" + proxy.getUsuario() + "','" + proxy.getClave() + "','" + proxy.getWsProduccion() + "','" + proxy.getWsPruebas() + "')";
    
    this.statement.executeUpdate(sql);
    flushDataBase();
    cerrarConexion();
  }
  
  public void crearProxy(Proxy proxy)
    throws ClassNotFoundException, SQLException
  {
    eliminarProxy();
    crear(proxy);
  }
  
  public Boolean hayRegistros()
    throws ClassNotFoundException, SQLException
  {
    Constantes.cargarJDC();
    getConecction();
    this.rs = this.statement.executeQuery("SELECT * FROM PROXY");
    int i = 0;
    if (this.rs.next()) {
      i++;
    }
    cerrarConexion();
    if (i > 0) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public void eliminarProxy()
    throws ClassNotFoundException, SQLException
  {
    Constantes.cargarJDC();
    getConecction();
    this.statement.executeUpdate("DELETE FROM PROXY");
    flushDataBase();
    cerrarConexion();
  }
  
  private void cerrarConexion()
    throws SQLException
  {
    this.statement.close();
    this.conn.close();
  }
  
  private void getConecction()
    throws SQLException
  {
    this.conn = DriverManager.getConnection(this.url);
    this.statement = this.conn.createStatement();
  }
  
  private void flushDataBase()
    throws SQLException
  {
    this.statement = this.conn.createStatement();
    this.statement.executeUpdate("SHUTDOWN");
  }
  
  public Proxy obtenerProxy()
    throws SQLException, ClassNotFoundException
  {
    /*if (Constantes.obtenerUrlBD() != null)
    {
      Constantes.cargarJDC();
      this.conn = DriverManager.getConnection(this.url);
      StringBuilder sql = new StringBuilder("select * from PROXY ");
      this.statement = this.conn.createStatement();
      this.rs = this.statement.executeQuery(sql.toString());
      return getProxy();
    }
    return null;*/
     
    return getProxy();
  }
  
  private Proxy getProxy()
    throws SQLException
  {
    Proxy proxy = null;
    /*while (this.rs.next())
    {
      proxy = new Proxy();
      proxy.setUrl(this.rs.getString("URL"));
      proxy.setPuerto(Integer.valueOf(this.rs.getInt("PUERTO")));
      proxy.setUsuario(this.rs.getString("USUARIO"));
      proxy.setClave(this.rs.getString("CLAVE"));
      proxy.setWsProduccion(this.rs.getString("WS_PRODUCCION"));
      proxy.setWsPruebas(this.rs.getString("WS_PRUEBAS"));
    }*/
    
    proxy = new Proxy();
    proxy.setUrl("");
    proxy.setPuerto(1);
    proxy.setUsuario("");
    proxy.setClave("[C@187f4d7");
    proxy.setWsProduccion("https://cel.sri.gob.ec");
    proxy.setWsPruebas("https://celcer.sri.gob.ec");
    
    return proxy;
  }
}