/*
 * ZafTableEvent.java
 *
 * Created on 26 de octubre de 2005, 11:38 AM
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento
 * al "EventObject".
 * @author  Eddye Lino
 */
public class ZafTableEvent extends java.util.EventObject
{
    
    /** Creates a new instance of ZafTableEvent */
    public ZafTableEvent(Object source)
    {
        super(source);
    }
    
}
