/*
 * GroupableTableHeader.java
 *
 * Created on 15 de febrero de 2005, 14:10
 */

package Librerias.ZafTblAgr;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Calcula totales de columnas de 2 tablas, tambien maneja el scrollbar y las vistas<br>
 * de forma que mientras mueves el srollbar horizontal de la tabla donde van los totales<br>
 * tambien se actualice la vista en la tabla principal.
 * @author IdiTrix
 * @version 1.0
 */
public class GroupableTableHeader extends JTableHeader {
    
 protected Vector columnGroups = null;

    public GroupableTableHeader(TableColumnModel model) {
        super(model); 
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
    }

    public void addColumnGroup(ColumnGroup g) {
        if (columnGroups == null) {
            columnGroups = new Vector();
        }
        columnGroups.addElement(g);
    }    
    
    public Enumeration getColumnGroups(TableColumn col) {
        if (columnGroups == null) return null;
        Enumeration enumeration = columnGroups.elements();
        while (enumeration.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup) enumeration.nextElement();
            Vector v_ret = cGroup.getColumnGroups(col, new Vector());
            if (v_ret != null) return v_ret.elements();
        }
        return null;
    }    
    
    public void setColumnMargin() {
        if (columnGroups == null) return;
        int columnMargin = getColumnModel().getColumnMargin();
        Enumeration enumeration = columnGroups.elements();
        while (enumeration.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup) enumeration.nextElement();
            cGroup.setColumnMargin(columnMargin);
        }
    }    
    
     
}
