/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum92;

import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Sistemas5
 */
public class FrozenTablePaneHorExt extends JScrollPane{
    
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButDup; 

    private static final int INT_TBL_RES_LINEA=0;
    private static final int INT_TBL_RES_DUPLICAR=1;
    private static final int INT_TBL_RES_CODTRA=2;
    private static final int INT_TBL_RES_NOMTRA=3;
    private static final int INT_TBL_RES_CODDEP=4;
    private static final int INT_TBL_RES_NOMDEP=5;
    
    private static final int INT_TBL_RES_MARCENT=6;
    private static final int INT_TBL_RES_MARCSAL=7;
    private static final int INT_TBL_RES_MARCENTHEX=8;
    private static final int INT_TBL_RES_MARCSALHEX=9;
    private static final int INT_TBL_RES_OBSERV=10;
    private static final int INT_TBL_RES_BTNOBSERV=11;
    private static final int INT_TBL_RES_MODMARC=12;
    private static final int INT_TBL_RES_MODHEXT=13;

    JTable tblHorExt=null;

    public FrozenTablePaneHorExt(JTable table, int colsToFreeze){
      
        super(table);
        tblHorExt=table;
        TableModel model = table.getModel();
        String strColumnName="";
        Vector vecColName=new Vector();

        //create a frozen model
        DefaultTableModel frozenModel = new DefaultTableModel(
                                    model.getRowCount(),
                                    colsToFreeze);

        //populate the frozen model
        int intss=model.getRowCount();
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < colsToFreeze; j++) {
                if(j==0 ){
                  String value = (String) model.getValueAt(i, j);
                  frozenModel.setValueAt(value, i, j);
                }else if(j==1){
                  String values = (String) model.getValueAt(i, j);
                  frozenModel.setValueAt(values, i, j);
                }else if(j==2){
                  Integer valuess = (Integer) model.getValueAt(i, j);
                  frozenModel.setValueAt(valuess, i, j);          
                }else if(j==3){
                  String valuessr = (String) model.getValueAt(i, j);
                  frozenModel.setValueAt(valuessr, i, j);                    
                }else if(j==4){
                  Integer valuessr = (Integer) model.getValueAt(i, j);
                  frozenModel.setValueAt(valuessr, i, j);                    
                }else if(j==5){
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
        TableColumnModel tcm=  frozenTable.getColumnModel();
        tcm.getColumn(3).setPreferredWidth(250);
        tcm.getColumn(5).setPreferredWidth(200);
        tcm.getColumn(1).setPreferredWidth(150);
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        zafTblDocCelRenBut.setText("Duplicar");
        tcm.getColumn(1).setCellRenderer(zafTblDocCelRenBut);
        DefaultTableModel objTblModFrozen=(DefaultTableModel)frozenTable.getModel();

    
        ZafTblMod objTblModHExt=(ZafTblMod)table.getModel();
        objTblCelEdiButDup=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
        tcm.getColumn(1).setCellEditor(objTblCelEdiButDup);
        objTblCelEdiButDup.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel, intColSel;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
             public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 //intFilSel=table.getSelectedRow();
                 ZafTblCelEdiButGen btnss=((ZafTblCelEdiButGen)evt.getSource());

                 intFilSel=frozenTable.getSelectedRow();
                    if (intFilSel!=-1)
                    { 
                        Vector vecReg       = (Vector)((Vector)objTblModHExt.getRow(intFilSel)).clone();
                        //Vector vecRegFrozen = (Vector)((Vector)objTblModFrozen.getRow(intFilSel)).clone();
                        Vector vecRegFrozen = (Vector)((Vector)objTblModFrozen.getDataVector().elementAt(intFilSel)).clone();
                        int i=0;
                        while(i<7){

                            vecRegFrozen.setElementAt("", INT_TBL_RES_LINEA);
                            vecRegFrozen.setElementAt("", INT_TBL_RES_DUPLICAR);
                            vecRegFrozen.setElementAt(vecRegFrozen.get(INT_TBL_RES_CODTRA), INT_TBL_RES_CODTRA);
                            vecRegFrozen.setElementAt(vecRegFrozen.get(INT_TBL_RES_NOMTRA), INT_TBL_RES_NOMTRA);
                            vecRegFrozen.setElementAt(vecRegFrozen.get(INT_TBL_RES_CODDEP), INT_TBL_RES_CODDEP);
                            vecRegFrozen.setElementAt(vecRegFrozen.get(INT_TBL_RES_NOMDEP), INT_TBL_RES_NOMDEP);


                            String strMarEnt=(String)tblHorExt.getValueAt(intFilSel, INT_TBL_RES_MARCENT+(8*i));
                            String strMarSal=(String)tblHorExt.getValueAt(intFilSel, INT_TBL_RES_MARCSAL+(8*i));
                            vecReg.setElementAt(strMarEnt, INT_TBL_RES_MARCENT+(8*i));
                            vecReg.setElementAt(strMarSal, INT_TBL_RES_MARCSAL+(8*i));
                            vecReg.setElementAt("", INT_TBL_RES_MARCENTHEX+(8*i));
                            vecReg.setElementAt("", INT_TBL_RES_MARCSALHEX+(8*i));
                            vecReg.setElementAt("", INT_TBL_RES_OBSERV+(8*i));
                            vecReg.setElementAt("", INT_TBL_RES_BTNOBSERV+(8*i));                            
                            vecReg.setElementAt("", INT_TBL_RES_MODMARC+(8*i));
                            vecReg.setElementAt("", INT_TBL_RES_MODHEXT+(8*i)); 
                            i++;
                        }
                        Vector vecDataFrozen=(Vector)objTblModFrozen.getDataVector();
                        vecDataFrozen.insertElementAt(vecRegFrozen, intFilSel+1);
                        
                        Vector vecData=(Vector)objTblModHExt.getData();
                        vecData.insertElementAt(vecReg, intFilSel+1);
                        /*CODIGO PARA BORRAR LA ULTIMA FILA VACIA INSERTADA */
                        Vector vecRegAct=((Vector)vecData.get(vecData.size()-1));
                        Integer intCodTra=(Integer)vecRegAct.get(INT_TBL_RES_CODTRA);
                        if(null == intCodTra ){
                            vecData.remove(vecRegAct);
                        }
                        /*CODIGO PARA BORRAR LA ULTIMA FILA VACIA INSERTADA */
                        objTblModHExt.setData(vecData);
                        tblHorExt.setModel(objTblModHExt);
                    }
             }

       });    
    
        ArrayList arlColHid=new ArrayList();

        for (int j = 0; j < colsToFreeze; j++) {
          arlColHid.add(""+j);      
        }
        arlColHid.add(""+INT_TBL_RES_MODMARC);
        arlColHid.add(""+INT_TBL_RES_MODHEXT);
        arlColHid.add(""+(INT_TBL_RES_MODMARC+8));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+8));
        arlColHid.add(""+(INT_TBL_RES_MODMARC+16));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+16)); //3 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+24));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+24));//4 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+32));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+32));//5 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+40));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+40));//6 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+48));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+48));//7 DIA        
        
       ((ZafTblMod)table.getModel()).setSystemHiddenColumns(arlColHid, table);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //format the frozen table
        JTableHeader header = table.getTableHeader();
        frozenTable.setBackground(header.getBackground());
        frozenTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //frozenTable.setEnabled(false);


        //set frozen table as row header view
        JViewport viewport = new JViewport();
        viewport.setView(frozenTable);
        viewport.setPreferredSize(frozenTable.getPreferredSize());
        setRowHeaderView(viewport);
        setCorner(JScrollPane.UPPER_LEFT_CORNER, frozenTable.getTableHeader());
    }    
}
