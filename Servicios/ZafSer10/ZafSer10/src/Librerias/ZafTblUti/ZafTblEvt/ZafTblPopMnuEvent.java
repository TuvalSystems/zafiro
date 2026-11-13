/*
 * ZafTblPopMnuEvent.java
 *
 * Created on 10 de mayo de 2007, 09:34 AM
 * v0.1
 */  

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento
 * al "EventObject".
 * @author  Eddye Lino
 */
public class ZafTblPopMnuEvent extends java.util.EventObject
{
    
    /** Creates a new instance of ZafTblPopMnuEvent */
    public ZafTblPopMnuEvent(Object source)
    {
        super(source);
    }
    
}
