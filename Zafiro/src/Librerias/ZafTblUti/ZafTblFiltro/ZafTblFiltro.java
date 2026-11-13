/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafTblUti.ZafTblFiltro;

import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author sistemas4
 */
public class ZafTblFiltro {

    private JTable tabla;
    private ZafTblMod modelo;
    private int colRuc;
    private int colNom;
    private JLabel lblFiltro;
    private String filtro = "";
    private long ultimaTecla = 0;

    public ZafTblFiltro(JTable tabla, ZafTblMod modelo, int colRuc, int colNom, JLabel lblFiltro) {
        this.tabla = tabla;
        this.modelo = modelo;
        this.colRuc = colRuc;
        this.colNom = colNom;
        this.lblFiltro = lblFiltro;

        init();
    }

    private void init() {
        tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                manejarFiltro(evt);
            }
        });
    }

    private void manejarFiltro(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();
        long ahora = System.currentTimeMillis();

        // Reset si pasa 1 segundo
        if (ahora - ultimaTecla > 1000) {
            filtro = "";
        }

        ultimaTecla = ahora;

        // BACKSPACE
        if (c == '\b') {
            if (filtro.length() > 0) {
                filtro = filtro.substring(0, filtro.length() - 1);
            }
        }
        // ESC limpia
        else if (c == 27) {
            filtro = "";
        }
        // Letras, números y espacio
        else if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) {
            filtro += c;
        } else {
            return;
        }

        aplicarFiltro();

        if (lblFiltro != null) {
            lblFiltro.setText("Filtro: " + filtro);
        } 
    }

    private void aplicarFiltro() {

        // 
        if (tabla.getRowCount() == 0) {
            if (tabla.getRowSorter() != null) {
                tabla.setRowSorter(null);
            }
            return;
        }

        TableRowSorter sorter = (TableRowSorter) tabla.getRowSorter();
              
        
        if (sorter == null) {
            sorter = new TableRowSorter(modelo);
            tabla.setRowSorter(sorter);
        }

        if (filtro.equals("")) {
            sorter.setRowFilter(null);
        } else {
            String texto = java.util.regex.Pattern.quote(filtro);
            sorter.setRowFilter(RowFilter.regexFilter("(?i).*" + texto + ".*", colRuc, colNom));
        }
    }
    
    public String getFiltro() {
        return filtro;
    }
    
    public void setFiltro(String filtro) {
        this.filtro = filtro;
        aplicarFiltro();
    }
    
}
