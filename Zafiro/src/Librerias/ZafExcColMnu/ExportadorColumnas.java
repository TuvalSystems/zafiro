/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafExcColMnu;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author sistemas2
 */
public class ExportadorColumnas 
{
    
     // Clase auxiliar para almacenar la información de cada columna
    public static class ColumnaInfo {
        private int indice;
        private String descripcion;

        public ColumnaInfo(int indice, String descripcion) {
            this.indice = indice;
            this.descripcion = descripcion;
        }

        public int getIndice() {
            return indice;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            return "Índice: " + indice + ", Descripción: " + descripcion;
        }
    }

    // Método para obtener las columnas de la tabla
    public List<ColumnaInfo> obtenerColumnasDeJTable(JTable table) {
        List<ColumnaInfo> columnas = new ArrayList<>();
        TableModel model = table.getModel();

        for (int col = 0; col < model.getColumnCount(); col++) {
            String nombreColumna = model.getColumnName(col);
            columnas.add(new ColumnaInfo(col, nombreColumna));
        }

        return columnas;
    }
    
}
