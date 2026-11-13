/*
 * ZafUtil.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.56
 */

package Librerias.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafUtilGP.HashMapPer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ZafExpoExc 
{
     private ZafUtil objUti;
     public void exportarJTableAExcel(JTable table, String archivo) throws IOException {
        // Crear un libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        // Crear una hoja de trabajo
        Sheet sheet = workbook.createSheet("Datos");

        // Obtener el modelo de la tabla
        TableModel model = table.getModel();

        // Crear una fila para los encabezados de las columnas
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < model.getColumnCount(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(model.getColumnName(col));
        }

        // Llenar la hoja con los datos de la tabla
        for (int row = 0; row < model.getRowCount(); row++) {
            Row dataRow = sheet.createRow(row + 1);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = dataRow.createCell(col);
                Object value = model.getValueAt(row, col);
                if (value !=null)
                {
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
////                        cell.setCellValue(0);
                    }
                }
                else
                    cell.setCellValue("");

                    
                    

            }
        }

        // Escribir el libro de trabajo en el archivo
        try (FileOutputStream fileOut = new FileOutputStream(archivo)) {
            workbook.write(fileOut);
        }

        // Cerrar el libro de trabajo
        workbook.close();
    }
     
// Exporta solo columnas desde "columnaInicio" hasta "columnaFin" (inclusive)
//////    public void exportarJTableAExcelPorColumnas(JTable table, String archivo, int columnaInicio, int columnaFin) throws IOException {
//////        Workbook workbook = new XSSFWorkbook();
//////        Sheet sheet = workbook.createSheet("Datos");
//////        TableModel model = table.getModel();
//////
//////        // Validar índices
//////        if (columnaInicio < 0) columnaInicio = 0;
//////        if (columnaFin >= model.getColumnCount()) columnaFin = model.getColumnCount() - 1;
//////
//////        // Crear encabezados
//////        Row headerRow = sheet.createRow(0);
//////        int colIndex = 0;
//////        for (int col = columnaInicio; col <= columnaFin; col++) {
//////            Cell cell = headerRow.createCell(colIndex++);
//////            cell.setCellValue(model.getColumnName(col));
//////        }
//////
//////        // Agregar datos
//////        for (int row = 0; row < model.getRowCount(); row++) {
//////            Row dataRow = sheet.createRow(row + 1);
//////            colIndex = 0;
//////            for (int col = columnaInicio; col <= columnaFin; col++) {
//////                Cell cell = dataRow.createCell(colIndex++);
//////                Object value = model.getValueAt(row, col);
//////                if (value != null) {
//////                    if (value instanceof Number) {
//////                        cell.setCellValue(((Number) value).doubleValue());
//////                    } else {
//////                        cell.setCellValue(value.toString());
//////                    }
//////                } else {
//////                    cell.setCellValue("");
//////                }
//////            }
//////        }
//////
//////        try (FileOutputStream fileOut = new FileOutputStream(archivo)) {
//////            workbook.write(fileOut);
//////        }
//////        workbook.close();
//////    }
    
    public void exportarJTableAExcelColum(JTable tableCon, String archivo, HashMap<String, HashMapPer.ZafCol_Excel> dataMapConsulta) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Datos");
        TableModel model = tableCon.getModel();
        Row headerRow = sheet.createRow(0);

        // Paso 1: Obtener nombres de columnas válidas desde dataMapConsulta
        Set<String> columnasValidas = new HashSet<>();
        for (HashMapPer.ZafCol_Excel val : dataMapConsulta.values()) {
            columnasValidas.add(val.getSt_des());
        }

        // Paso 2: Obtener índices de columnas a exportar
        List<Integer> columnasAExportar = new ArrayList<>();
        for (int col = 0; col < model.getColumnCount(); col++) {
          //  String nombreColumna = model.getColumnName(col);
            String nombreColumna = model.getColumnName(col)
                                   .replaceAll("<[^>]*>", "")   // Quita etiquetas HTML
                                   .replaceAll("\\s+", "")      // Quita todos los espacios, tabs, saltos de línea, etc.
                                   .trim();                     // Limpieza final (por si acaso)

            if (columnasValidas.contains(nombreColumna)) {
                columnasAExportar.add(col);
            }
        }

        // Paso 3: Crear encabezados en el Excel solo para las columnas válidas
        for (int i = 0; i < columnasAExportar.size(); i++) {
            int colIndex = columnasAExportar.get(i);
            Cell cell = headerRow.createCell(i);
            String nombreCrudo = model.getColumnName(colIndex);
            String nombreLimpio = nombreCrudo
                .replaceAll("<[^>]*>", "")  // Quita etiquetas HTML
                .replaceAll("\\s+", "")     // Quita todos los espacios
                .trim();

            cell.setCellValue(nombreLimpio);
        }

        // Paso 4: Llenar la hoja con los datos de las columnas filtradas
        for (int row = 0; row < model.getRowCount(); row++) {
            Row dataRow = sheet.createRow(row + 1);
            for (int i = 0; i < columnasAExportar.size(); i++) {
                int colIndex = columnasAExportar.get(i);
                Cell cell = dataRow.createCell(i);
                Object value = model.getValueAt(row, colIndex);
                if (value != null) {
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
                    }
                } else {
                    cell.setCellValue("");
                }
            }
        }

        // Guardar archivo Excel
        try (FileOutputStream fileOut = new FileOutputStream(archivo)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
        
    
    
    
}
