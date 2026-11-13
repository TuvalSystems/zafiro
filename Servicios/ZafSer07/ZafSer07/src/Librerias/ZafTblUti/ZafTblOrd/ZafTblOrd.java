/*
 * ZafTblOrd.java
 *
 * Created on 18 de febrero de 2006, 13:10 PM
 * v0.4
 */

package Librerias.ZafTblUti.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.Vector;

public class ZafTblOrd
{
    //Constantes:
    public static final int INT_TBL_SIN=0;   /**Un valor para setTipoOrdenamiento: Indica "Sin ordenar".*/
    public static final int INT_TBL_ASC=1;   /**Un valor para setTipoOrdenamiento: Indica "Orden ascendente".*/
    public static final int INT_TBL_DES=2;   /**Un valor para setTipoOrdenamiento: Indica "Orden descendente".*/
    //Variables:
    private javax.swing.JTable tblDat;
    private ZafTblMod objTblMod;            //Modelo del JTable.
    private ZafCom objCom;                  //Objeto Comparator utilizado para realizar el ordenamiento.
    private ZafHeaRenLbl objHeaRenLbl;      //Header: Render para mostrar el ordenamiento.
    private int intUltColOrd;               //Indice de la última columna ordenada.
    private int intUltTipOrd;               //Ultimo tipo de ordenamiento.
    
    public ZafTblOrd(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
        objCom=new ZafCom();
        intUltColOrd=-1;
        intUltTipOrd=INT_TBL_SIN;
        objHeaRenLbl=new ZafHeaRenLbl(tblDat.getTableHeader().getDefaultRenderer());
        tblDat.getTableHeader().setDefaultRenderer(objHeaRenLbl);
        tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thisMouseClicked(evt);
            }
        });
    }
    
    private void thisMouseClicked(java.awt.event.MouseEvent evt)
    {
        //Ordenar sólo si se da 1 click con el botón izquierdo.
        if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1)
        {
            int intColSel;
            intColSel=tblDat.columnAtPoint(evt.getPoint());
            if (intColSel!=intUltColOrd)
                intUltTipOrd=INT_TBL_SIN;
            intUltColOrd=intColSel;
            switch (intUltTipOrd)
            {
                case INT_TBL_SIN:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd,INT_TBL_ASC);
                    objCom.setColumnaOrdenar(intUltColOrd,true);
                    java.util.Collections.sort(objTblMod.getData(),objCom);
                    objTblMod.fireTableDataChanged();
                    intUltTipOrd=INT_TBL_ASC;
                    break;
                case INT_TBL_ASC:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_DES);
                    objCom.setColumnaOrdenar(intUltColOrd,false);
                    java.util.Collections.sort(objTblMod.getData(),objCom);
                    objTblMod.fireTableDataChanged();
                    intUltTipOrd=INT_TBL_DES;
                    break;
                case INT_TBL_DES:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_SIN);
                    intUltTipOrd=INT_TBL_SIN;
                    break;
            }
            tblDat.getTableHeader().repaint();
        }
    }
    
    /**
     * Esta función determina si la cadena que se recibe como argumento es un valor numérico o no. 
     * @param numero La cadena que contiene el número que se desea evaluar.
     * @return true: Si el valor es un número.
     * <BR>false: En el caso contrario.
     */
    private boolean isNumero(String numero)
    {
        double dblNum;
        boolean blnRes=true;
        try
        {
            dblNum=Double.parseDouble(numero);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
    * Esta clase implementa la interface "Comparator" la cual es utilizada para indicar
    * el modo que se utilizará para comparar objetos.
    */
    private class ZafCom implements java.util.Comparator
    {
        private int intCol;
        private boolean blnAsc;
        
        public ZafCom()
        {
            intCol=-1;
            blnAsc=true;
        }
        
        /**
         * Este constructor establece la columna que se debe ordenar ascendentemente/descendentemente. 
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public ZafCom(int columna, boolean ascendente)
        {
            intCol=columna;
            blnAsc=ascendente;
        }

        /**
         * Esta función establece la columna que se debe ordenar ascendentemente/descendentemente. 
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public void setColumnaOrdenar(int columna, boolean ascendente)
        {
            intCol=columna;
            blnAsc=ascendente;
        }
        
        /**
         * Esta función compara los objetos que recibe y devuelve el resultado de la comparación.
         * @param o1 El objeto a comparar.
         * @param o2 El otro objeto a comparar.
         * @return Puede tomar los siguientes valores:
         * <UL>
         * <LI>-1: Si o1<o2.
         * <LI>0: Si o1=o2.
         * <LI>1: Si o1>o2.
         * </UL>
         * Nota.- Se intercambia el -1 con el 1 para cambiar de ascendente a descendente.
         */
        public int compare(Object o1, Object o2)
        {
            int intRes=0;
            double dblNum1, dblNum2;
            Vector vecFil1, vecFil2;
            vecFil1=(Vector)o1;
            vecFil2=(Vector)o2;
            Object objCel1, objCel2;
            objCel1=vecFil1.get(intCol);
            objCel2=vecFil2.get(intCol);
//            //Convertir las cadenas vacías a null.
//            if (objCel1 instanceof String && ((String)objCel1).length()==0)
//            {
//                objCel1=null;
//            }
//            if (objCel2 instanceof String && ((String)objCel2).length()==0)
//            {
//                objCel2=null;
//            }
            //Comparar el contenido de las celdas.
            if (objCel1==null && objCel2==null)
            {
                intRes=0;
            }
            else if (objCel1==null)
            {
                if (blnAsc)
                    intRes=-1;
                else
                    intRes=1;
            }
            else if (objCel2==null)
            {
                if (blnAsc)
                    intRes=1;
                else
                    intRes=-1;
            }
            else if (isNumero(objCel1.toString()) && isNumero(objCel2.toString()))
            {
                //Comparar los números como números caso contrario serán considerados como cadena.
                dblNum1=Double.parseDouble(objCel1.toString());
                dblNum2=Double.parseDouble(objCel2.toString());
                if (blnAsc)
                {
                    if (dblNum1<dblNum2)
                        intRes=-1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=1;
                }
                else
                {
                    if (dblNum1<dblNum2)
                        intRes=1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=-1;
                }
            }
            else if (objCel1 instanceof String)
            {
                //Comparar cadenas.
                if (blnAsc)
                    intRes=objCel1.toString().compareToIgnoreCase(objCel2.toString());
                else
                    intRes=objCel2.toString().compareToIgnoreCase(objCel1.toString());
            }
            else if (objCel1 instanceof Comparable)
            {
                if (blnAsc)
                    intRes=((Comparable)objCel1).compareTo(objCel2);
                else
                    intRes=((Comparable)objCel2).compareTo(objCel1);
            }
            return intRes;
        }
        
    }
    
}