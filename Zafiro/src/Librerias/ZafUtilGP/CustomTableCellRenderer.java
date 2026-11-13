/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafUtilGP;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JTable;


/**
 *
 * @author sistemas4
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private int specificColumn;
    private int stockColumn;
    private int priceColumn;
    private ZafUtil objUti;
    private DecimalFormat decimalFormat;

    public CustomTableCellRenderer(int specificColumn, int stockColumn, int priceColumn) {
        objUti=new ZafUtil();
        this.specificColumn = specificColumn;
        this.stockColumn = stockColumn;
        this.priceColumn = priceColumn;
        this.decimalFormat = new DecimalFormat("0.00");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Verificar la condición del stock y el precio
        if (column == specificColumn) {
            Object stockValue = table.getValueAt(row, stockColumn);
            Object priceValue = table.getValueAt(row, priceColumn);
            double  stock = objUti.parseDouble(stockValue);
            double  precio = objUti.parseDouble(priceValue);
            if ((stock>0 ) && (precio==0))
            {
                c.setBackground(Color.YELLOW);  // Cambia el color de fondo si la condición se cumple
            }
            else
            {
                c.setBackground(table.getBackground());
            }
      // Formatear el valor de la columna específica con dos decimales
            if (value instanceof Number) {
                value = decimalFormat.format(((Number) value).doubleValue());
                setText(value.toString());
            }
        } else {
            c.setBackground(table.getBackground());
        }

        return c;
    }
}
