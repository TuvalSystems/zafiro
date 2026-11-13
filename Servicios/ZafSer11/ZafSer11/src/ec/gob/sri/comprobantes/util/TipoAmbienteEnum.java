/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ec.gob.sri.comprobantes.util;

/**
 *
 * @author: Dennis Betancourt
 */
public enum TipoAmbienteEnum
{
  PRODUCCION("2"),  PRUEBAS("1");
  
  private String code;
  
  private TipoAmbienteEnum(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
}