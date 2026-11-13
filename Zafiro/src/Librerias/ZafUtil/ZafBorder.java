

package Librerias.ZafUtil;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * El fin de la creación de esta clase es el de ubicar en ella todas aquellas operaciones
 * que se harían de uso común para el sistema. 
 */
public class ZafBorder 
{
   
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Colored Border JTable Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DefaultTableModel model = new DefaultTableModel(new Object[][]{
                    {1, "John", "Doe"},
                    {2, "Jane", "Smith"},
                    {3, "Bob", "Johnson"}
            }, new Object[]{"ID", "First Name", "Last Name"});

            JTable table = new JTable(model);
            table.getColumnModel().getColumn(1).setCellRenderer(new CustomTableCellRenderer());

            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);

            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
   static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private static final Color BORDER_COLOR = Color.RED;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 1) { // Customize the border for the second column
                ((JComponent) cellComponent).setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
            }

            return cellComponent;
        }
    }



}
