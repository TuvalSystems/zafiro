/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum37.ExcelTableExporter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author sistemas2
 */
public class ExcelTableExporterXlsx {
    
//    private File file;
//
//    private JTable table;
//
//    private String nombreTab;
//    
//    private ArrayList<String> arrLst=null;
//
//
//    public ExcelTableExporterXlsx(JTable table,File file,String nombreTab,ArrayList<String> arrLst){
//
//        this.file=file;
//
//        this.table=table;
//
//        this.nombreTab=nombreTab;
//
//        this.arrLst=arrLst;
//    }
//    
//    
//     public boolean export() {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet(nombreTab);
//
//            // Estilo para encabezados
//            CellStyle headerStyle = workbook.createCellStyle();
//            Font headerFont = workbook.createFont();
//            headerFont.setBold(true);
//            headerStyle.setFont(headerFont);
//
//            // Crear encabezados
//            Row headerRow = sheet.createRow(0);
//            for (int i = 0; i < table.getColumnCount(); i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(table.getColumnName(i));
//                cell.setCellStyle(headerStyle);
//            }
//
//            // Llenar datos
//            for (int r = 0; r < table.getRowCount(); r++) {
//                Row row = sheet.createRow(r + 1);
//                for (int c = 0; c < table.getColumnCount(); c++) {
//                    Object value = table.getValueAt(r, c);
//                    Cell cell = row.createCell(c);
//                    cell.setCellValue(value == null ? "" : value.toString());
//                }
//            }
//
//            // Autoajustar ancho de columnas
//            for (int i = 0; i < table.getColumnCount(); i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            try (FileOutputStream out = new FileOutputStream(file)) {
//                workbook.write(out);
//            }
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    
   private File file;
    private Workbook workbook;

    public ExcelTableExporterXlsx(File file) {
        this.file = file;
        this.workbook = new XSSFWorkbook();
    }

    public void addSheet(JTable table, String nombreTab) throws IOException {

        Sheet sheet = workbook.createSheet(nombreTab);

        // Estilo para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
            cell.setCellStyle(headerStyle);
        }

        // Llenar datos
        for (int r = 0; r < table.getRowCount(); r++) {
            Row row = sheet.createRow(r + 1);
            for (int c = 0; c < table.getColumnCount(); c++) {
                Object value = table.getValueAt(r, c);
                Cell cell = row.createCell(c);

                if (value == null) {
                    cell.setCellValue("");
                }
                else if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                }
                else {
                    // Intentar convertir String a número
                    String s = value.toString().replace(",", "").trim();
                    try {
                        double d = Double.parseDouble(s);
                        cell.setCellValue(d);
                    }
                    catch (Exception e) {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }


        // Autoajustar ancho de columnas
        for (int i = 0; i < table.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public boolean export() {
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
            workbook.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    } 
    
    
    
}
