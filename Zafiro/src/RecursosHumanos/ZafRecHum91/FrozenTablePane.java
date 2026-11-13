/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum91;

import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class FrozenTablePane extends JScrollPane{
 
  public FrozenTablePane(JTable table, int colsToFreeze){
    super(table);
 
    TableModel model = table.getModel();
    String strColumnName="";
    Vector vecColName=new Vector();
 
    //create a frozen model
    DefaultTableModel frozenModel = new DefaultTableModel(
                                model.getRowCount(),
                                colsToFreeze);
 
    //populate the frozen model
    for (int i = 0; i < model.getRowCount(); i++) {
      for (int j = 0; j < colsToFreeze; j++) {
          if(j==0 ){
            String value = (String) model.getValueAt(i, j);
            frozenModel.setValueAt(value, i, j);
          }else if(j==1){
            Integer values = (Integer) model.getValueAt(i, j);
            frozenModel.setValueAt(values, i, j);
          }else if(j==2){
            String valuess = (String) model.getValueAt(i, j);
            frozenModel.setValueAt(valuess, i, j);          
          }else if(j==3){
            String valuessr = (String) model.getValueAt(i, j);
            frozenModel.setValueAt(valuessr, i, j);                    
          }
      }
    }
    
    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < colsToFreeze; j++) {
          strColumnName=model.getColumnName(j);
          vecColName.add(strColumnName);
      }
    }    
    frozenModel.setColumnIdentifiers(vecColName);
 
    //create frozen table
    JTable frozenTable = new JTable(frozenModel);
    
 
    ArrayList arlColHid=new ArrayList();

    for (int j = 0; j < colsToFreeze; j++) {
      arlColHid.add(""+j);      
    }
   ((ZafTblMod)table.getModel()).setSystemHiddenColumns(arlColHid, table);
    
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 
    //format the frozen table
    JTableHeader header = table.getTableHeader();
    frozenTable.setBackground(header.getBackground());
    frozenTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    frozenTable.setEnabled(false);
 
    //set frozen table as row header view
    JViewport viewport = new JViewport();
    viewport.setView(frozenTable);
    viewport.setPreferredSize(frozenTable.getPreferredSize());
    setRowHeaderView(viewport);
    setCorner(JScrollPane.UPPER_LEFT_CORNER, frozenTable.getTableHeader());
  }
}