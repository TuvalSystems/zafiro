/*
 * ColumnGroup.java
 *
 * Created on 15 de febrero de 2005, 14:10
 */
package Librerias.ZafTblAgr;
import java.awt.*;
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
public class ColumnGroup {
    protected TableCellRenderer renderer;
    protected Vector v;
    protected String text;
    protected int margin = 0;
    public ColumnGroup(String text) {
        this(null, text);
    }
    public ColumnGroup(TableCellRenderer renderer, String text) {
        if (renderer == null) {
            this.renderer = new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        } else {
            this.renderer = renderer;
        }
        this.text = text;
        v = new Vector();
    }
    
    public void add(Object obj) {
        if (obj == null) return;
        v.addElement(obj);
    }    
    
    public Vector getColumnGroups(TableColumn c, Vector g) {
        g.addElement(this);
        if (v.contains(c)) return g;
        Enumeration enumeration = v.elements();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj instanceof ColumnGroup) {
                Vector groups = ((ColumnGroup) obj).getColumnGroups(c, (Vector) g.clone());
                if (groups != null) return groups;
            }
        }
        return null;
    }    
    
    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    public void setHeaderRenderer(TableCellRenderer renderer) {
        if (renderer != null) this.renderer = renderer;
    }

    public Object getHeaderValue() {
        return text;
    }    

    
    public Dimension getSize(JTable table) {
        Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue(), false, false, -1, -1);
        int height = comp.getPreferredSize().height;
        int width = 0;
        Enumeration enumeration = v.elements();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj instanceof TableColumn)
                width += ((TableColumn) obj).getWidth();
            else
                width += ((ColumnGroup) obj).getSize(table).width;
        }
        return new Dimension(width, height);
    }    
    public void setColumnMargin(int margin) {
        this.margin = margin;
        Enumeration enumeration = v.elements();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj instanceof ColumnGroup) ((ColumnGroup) obj).setColumnMargin(margin);
        }
    }
      

    }