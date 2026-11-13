/*
 * ZafDtePckEdi.java
 *
 * Created on 27 de julio de 2005, 10:00
 */

package Librerias.ZafTblUti.ZafDtePckEdi;

/**
 *
 * @author  IdiTrix
 */
public class ZafDtePckEdi extends Librerias.ZafDate.ZafDatePicker implements javax.swing.table.TableCellEditor
{
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private Librerias.ZafUtil.ZafUtil objUtil;
    
     /**
      * Construye un nuevo ZafDtePckEdi
      * si la celda no tiene nada el editor pondra la fecha actual del sistema 
      * como valor predeterminado, si la celda tiene un valor, el editor seleccionara 
      * ese valor para poder borrarlo al digitar.
      * @param strFormat  Recibe un String con la fecha que queremos que salga <br>
      *                   Ejemplo :   "d/m/y"   // Dia/mes/Año<br>
      *                               "y/m/d"   // Año/mes/dia
      */
    public ZafDtePckEdi(String strFormat)
    {   super(new javax.swing.JFrame(), strFormat);
        objUtil = new Librerias.ZafUtil.ZafUtil();
        //this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("actionPerformed ");
                setValCelEdi(isFecha());                
            }
        });
    }
    
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
    public void addCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        System.out.println("addCellEditorListener");
        this.selectAll();
        this.requestFocus();
        objLinLis.add(l);
    }
    
    public void cancelCellEditing()
    {
        System.out.println("cancelCellEditing");
    }
    
    /**
     * Esta función devuelve el nuevo valor que debe aparecer en la celda que se estaba editando.
     * Por ejemplo, si el editor era un "JComboBox" devolverá la opción que se seleccionó en
     * dicho combo.
     */
    public Object getCellEditorValue()
    {
        System.out.println("getCellEditorValue");
        return this.getText();
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        System.out.println("getTableCellEditorComponent");
        if(value==null)
            this.setHoy();
        else
            this.setText(this.getText());
            
        
        //this.requestFocus();             
        return this;
    }
    
    /**
     * Esta función devuelve "true" si se desea que la celda sea editable y "false" en caso contrario.
     * Si se desea que apenas se de click en la celda aparezca el editor se debe quitar la validación
     * y sólo dejar la línea "return true;".
     */
    public boolean isCellEditable(java.util.EventObject anEvent)
    {
        System.out.println("isCellEditable");
        //Validar que sólo se muestre el editor cuando se da doble click.
        if (anEvent instanceof java.awt.event.MouseEvent) 
        {
            if (((java.awt.event.MouseEvent)anEvent).getClickCount()!=2)
                return false;
        }            
        return true;
    }
    
    /**
     * Esta función remueve el listener que notifica cuando el editor se detiene
     * o cancele la edición de la celda.
     */
    public void removeCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        System.out.println("removeCellEditorListener");
        objLinLis.remove(l);
    }
    
    /**
     * Esta función devuelve "true" si se desea que la fila de la celda que se está editando sea
     * seleccionada y "false" en caso contrario.
     */
    public boolean shouldSelectCell(java.util.EventObject anEvent)
    {
        System.out.println("shouldSelectCell");
        return true;
    }
    
    /**
     * Esta función debe devolver "true" cuando los datos que está introduciendo el usuario son correctos y false
     * en el caso contrario. Por ejemplo, puede ser que se esté utilizando un editor para ingreso de fechas y el
     * usuario ha ingresado 31/Feb/2005 en cuyo caso deveríamos devolver "false" para obligar al usuario a que
     * ingrese una fecha válida.
     */
    public boolean stopCellEditing()
    {
        System.out.println("stopCellEditing");
        //fireActionPerformed();
        setValCelEdi(false);
        return true;
    }
    
    /**
     * Nombre de la función: Establecer valor de la celda editada.
     * Establece el valor de la celda luego de utilizar el editor siempre y cuando el argumento que
     * reciba sea verdadero en caso contrario deja el valor que tenía la celda antes de ser editada.
     * @param hayCambios El estado de la celda que se está editando.
     * <BR>True: Si el contenido de la celda ha cambiado se avisa a los suscriptores de que se ha 
     * terminado la edición y que hay que establecer el nuevo valor de la celda.
     * <BR>False: Se avisa a los suscriptores que se ha cancelado la edición y que se debe dejar
     * el valor que tenía la celda tal y como estaba antes de la edición de la misma.
     */
    protected void setValCelEdi(boolean hayCambios)
    {
        System.out.println("setValCelEdi");
        javax.swing.event.ChangeEvent evt=new javax.swing.event.ChangeEvent(this);
        javax.swing.event.CellEditorListener objCelEdiLis;
        int i;
        for (i=0;i<objLinLis.size();i++)
        {
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLinLis.get(i);
            if (hayCambios)
                objCelEdiLis.editingStopped(evt);
            else
                objCelEdiLis.editingCanceled(evt);
        }
    }
    
}
