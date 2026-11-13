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
public enum TokensValidos
{
  ANF1("ANF - Certificado Exportado"),  ANF_TOKEN("ANF - Plug & Sign"),  BCE_IKEY2032("BCE - iKey2032"),  BCE_ALADDIN("BCE - Aladdin eToken Pro"),  SD_EPASS3000("SD - ePass3003 auto"),  SD_BIOPASS("SD - BioPass3000");
  
  String id;
  
  private TokensValidos(String modelo)
  {
    this.id = modelo;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String toString()
  {
    return getId();
  }
}