package Librerias.ZafTblUti.ZafTblCabGrp;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class ZafTblCabGrp extends JTableHeader {
    private java.util.List<ZafTblCabGrpItem> groups = new ArrayList<>();

    public ZafTblCabGrp(JTable table) { 
        super(((DefaultTableColumnModel) table.getColumnModel()));
    }

    public ZafTblCabGrpItem addGroup(String name) {
        ZafTblCabGrpItem group = new ZafTblCabGrpItem(name);
        groups.add(group);
        return group;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (groups.isEmpty()) return;

        for (ZafTblCabGrpItem group : groups) {
            if (group.getColumns().isEmpty()) continue;
            Rectangle groupRect = getGroupBounds(group);
            g.setColor(new Color(230, 230, 230));
            g.fillRect(groupRect.x, groupRect.y, groupRect.width, groupRect.height);
            g.setColor(Color.BLACK);
            g.drawRect(groupRect.x, groupRect.y, groupRect.width, groupRect.height);

            // Centrar el texto del grupo
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(group.getName());
            int textHeight = fm.getAscent();
            int textX = groupRect.x + (groupRect.width - textWidth) / 2;
            int textY = groupRect.y + textHeight + 2;
            g.drawString(group.getName(), textX, textY);
        }
    }

    private Rectangle getGroupBounds(ZafTblCabGrpItem group) {
        DefaultTableColumnModel model = (DefaultTableColumnModel) getColumnModel();
        TableColumn firstCol = group.getColumns().get(0);
        TableColumn lastCol = group.getColumns().get(group.getColumns().size() - 1);

        int firstIndex = model.getColumnIndex(firstCol.getIdentifier());
        int lastIndex = model.getColumnIndex(lastCol.getIdentifier());

        Rectangle first = getHeaderRect(firstIndex);
        Rectangle last = getHeaderRect(lastIndex);
        return new Rectangle(first.x, 0, last.x + last.width - first.x, 20);
    }

    // Clase interna: representa un grupo de columnas
    public static class ZafTblCabGrpItem {
        private String name;
        private java.util.List<TableColumn> columns = new ArrayList<>();

        public ZafTblCabGrpItem(String name) {
            this.name = name;
        }

        public void addColumn(TableColumn column) {
            columns.add(column);
        }

        public String getName() {
            return name;
        }

        public java.util.List<TableColumn> getColumns() {
            return columns;
        }
    }
}
