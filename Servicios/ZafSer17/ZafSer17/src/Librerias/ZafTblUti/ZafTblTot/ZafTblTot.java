/*
 * ZafTblTot.java
 *
 * Created on 08 de enero de 2006, 07:56 PM
 * v0.3.1
 */


package Librerias.ZafTblUti.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

/**
 * Esta clase permite configurar un JTable de totales. Se configura lo siguiente:
 * <UL>
 * <LI>Al redimensionar una columna se redimensiona la columna respectiva en el JTable de totales.
 * <LI>Al hacer un desplazamiento ya sea en el JTable de datos o en el JTable de totales se hace un
 * desplazamiento en el otro JTable.
 * <LI>Permite calcular los totales de las columnas especificadas.
 * </UL>
 * @author  Eddye Lino
 */
public class ZafTblTot
{
    //Variables.
    private ZafUtil objUti;
    private ZafTblMod objTblMod;                        //Modelo del JTable.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    //Variables de la clase.
    private JTable tblDat, tblTot;
    private JScrollBar barDat, barTot;
    private int intColTot[];                            //Columnas de totales.
    private String strForNum;                           //Formato que se utilizará para mostrar los números.
    private int intNumDec;

    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnas Las columnas a las que se le calculará el total.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnas[])
    {
        this(panelDatos, panelTotales, datos, totales, columnas, null);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnas Las columnas a las que se le calculará el total.
     * @param formato El formato que se utilizará para mostrar los números en el JTable de totales.
     * Ejemplos: ###,###,###.##, ###,###,##0.00, etc.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> se utilizará el formato "###,###,##0.00".
     * <LI>Si el parámetro recibido es diferente a <I>null</I> se utilizará el formato recibido.
     * </UL>
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnas[], String formato)
    {
        tblDat=datos;
        tblTot=totales;
        intColTot=columnas;
        if (formato==null)
            strForNum="###,###,##0.00";
        else
            strForNum=formato;
        intNumDec=6;
        //Inicializar objetos.
        objUti=new ZafUtil();
        //Establecer el mismo número de columnas para el JTable de totales.
        objTblMod=new ZafTblMod();
        objTblMod.setSize(1, tblDat.getColumnCount());
        tblTot.setModel(objTblMod);
        //Establecer el tipo de redimensionamiento del JTable de totales.
        tblTot.setAutoResizeMode(tblDat.getAutoResizeMode());
        //Configurar JTable: Renderizar celdas.
        javax.swing.table.TableColumnModel tcmAux=tblTot.getColumnModel();
        objTblCelRenBut=new ZafTblCelRenBut();
        objTblCelRenBut.setText("");
        tcmAux.getColumn(0).setCellRenderer(objTblCelRenBut);
        //Configurar JTable: Renderizar celdas.
        for (int i=0;i<intColTot.length;i++)
        {
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(strForNum);
            tcmAux.getColumn(intColTot[i]).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
        }
        //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
        for (int j=0; j<tblDat.getColumnCount(); j++)
        {
            tcmAux.getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
            tcmAux.getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
            tcmAux.getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
            tcmAux.getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
            tcmAux.getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
        }
        //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
        panelTotales.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelTotales.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        tblTot.setTableHeader(null);
        //Adicionar el listener que controla el redimensionamiento de las columnas.
        ZafTblColModLis objTblColModLis=new ZafTblColModLis();
        tblDat.getColumnModel().addColumnModelListener(objTblColModLis);
        //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
        barDat=panelDatos.getHorizontalScrollBar();
        barTot=panelTotales.getHorizontalScrollBar();
        barDat.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                barTot.setValue(evt.getValue());
            }
        });
        barTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                barDat.setValue(evt.getValue());
            }
        });
        //Libero los objetos auxiliares.
        tcmAux=null;
    }
    
    /**
     * Esta función calcula el total de las columnas especificadas.
     * @return true: Si se pudo calcular el total de las columnas especificadas sin que ocurra ningún error.
     * <BR>false: En el caso contrario.
     */
    public boolean calcularTotales()
    {
        int intNumFil, intNumCol, i, j;
        boolean blnRes=true;
        try
        {
            intNumFil=tblDat.getRowCount();
            intNumCol=intColTot.length;
            double dblTot[]=new double[intNumCol];
            for (i=0; i<intNumFil; i++)
            {
                for (j=0; j<intNumCol; j++)
                    dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
            }
            for (j=0; j<intNumCol; j++)
                tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el valor que se encuentra almacenado en la celda especificada.
     * @param row La fila de la celda de la que se desea obtener su valor.
     * @param col La columna de la celda de la que se desea obtener su valor.
     * @return El valor almacenado en la celda.
     */
    public Object getValueAt(int row, int col)
    {
        return objTblMod.getValueAt(row, col);
    }
    
    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(Object valor, int row, int col)
    {
        objTblMod.setValueAt(valor, row, col);
    }
    
    /**
     * Esta función obtiene el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @return El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public int getNumeroDecimales()
    {
        return intNumDec;
    }
    
    /**
     * Esta función establece el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @param decimales El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public void setNumeroDecimales(int decimales)
    {
        intNumDec=decimales;
    }




/**
 * Esta función permite insertar filas al final del modelo de datos.
 * @param numeroFilas El número de filas que se desea adicionar al modelo.
 * @return true: Si se pudo realizar la inserción
 * <BR>false: En el caso contrario.
 */

    public boolean insertMoreRows(int numeroFilas){
        boolean blnRes=true;
        try{
            for(int i=0; i<numeroFilas; i++){
                objTblMod.insertRow();
            }
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }


/**
 * Esta función presenta el numero de filas en el modelo de datos.
 * @return int: El número de filas en el modelo de datos
 */

    public int getRowCountTrue(){
        int intNumFilModDat=-1;
        try{
            intNumFilModDat=objTblMod.getRowCountTrue();
        }
        catch(Exception e){
        }
        return intNumFilModDat;
    }



    public boolean removeAllRows(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();

        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }



//------------------------------------------------------------------------------
    
//------------------------------------------------------------------------------

    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener
    {
        
        public void columnAdded(javax.swing.event.TableColumnModelEvent e)
        {

        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e)
        {
            int intColSel, intAncCol;
            if (tblDat.getTableHeader().getResizingColumn()!=null)
            {
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0)
                {
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTot.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e)
        {

        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e)
        {
//------------------------------------------------------------------------------
//            System.out.println("Columna eliminada: " + e.getFromIndex());
//            ((ZafTblMod)tblTot.getModel()).removeColumn(tblTot, e.getFromIndex());
//------------------------------------------------------------------------------
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e)
        {

        }
    }
    
}
