/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafSer12;

/**
 * @author: Dennis Betancourt
 */
public class ZafSer12
{
   public ZafSer12()
   {
      System.out.println("ZafSer12: Servicio autorizacion en diferido de Comprobantes Electronicos v0.3.8");
      new ZafBatCpbEle();
   }
   
   public static void main(String arg[])
   {
      ZafSer12 oz = new ZafSer12();
   }
}