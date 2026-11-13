/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafDate;

/**
 *
 * @author jayapata
 */
public class ZafSelectDate {

     private String strCaption = "Rango a Elegir:";
    private String strFrom = "Desde: ";
    private String strTo = "Hasta: ";
    private String strYear = "AÃ±o";
    private ZafDatePicker zafDateFrom;
    private ZafDatePicker zafDateTo;
    private javax.swing.JSpinner spiYear;
    private javax.swing.JLabel lblFrom, lblTo, lblYear;
    private javax.swing.JLabel lblLeft;
    private javax.swing.JLabel lblRight;
    private javax.swing.JPanel panFrom, panTo;
    private boolean chkVisible = true;
    private boolean BorderVisible = true;



    /**
     * Crea un nuevo zafSelectDate
     * @param Principal Recibe el JFrame en el que va a trabajar el zafSelectDate
     * @param strFormatDate Recibe el Formato para la fecha en los datepicker 'd/m/y' ; 'y/m/d'; 'm/d/y'
     */
    public ZafSelectDate(javax.swing.JFrame Principal , String strFormatDate) {
        panFrom = new javax.swing.JPanel();
        panTo = new javax.swing.JPanel();
        panFrom.setLayout(new java.awt.GridLayout(1, 2));
        panTo.setLayout(new java.awt.GridLayout(1, 2));

        zafDateFrom = new ZafDatePicker(Principal,strFormatDate);
        zafDateTo   = new ZafDatePicker(Principal,strFormatDate);


        lblFrom     = new javax.swing.JLabel(strFrom, javax.swing.SwingConstants.RIGHT);
        lblTo       = new javax.swing.JLabel(strTo , javax.swing.SwingConstants.RIGHT );
        lblYear     = new javax.swing.JLabel(strYear, javax.swing.SwingConstants.RIGHT );
        spiYear     = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(zafDateFrom.getFecha(zafDateFrom.getText())[2],1800,4000,1));
        lblLeft     = new javax.swing.JLabel();
        lblRight    = new javax.swing.JLabel();


        //Poniendo la flecha que apuntan a la derecha
        lblRight.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblRight.setForeground(new java.awt.Color(0, 51, 153));
        lblRight.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRight.setText("\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ba");
        lblRight.setOpaque(true);

        //Poniendo la flecha que apuntan a la izquierda
        lblLeft.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblLeft.setForeground(new java.awt.Color(51, 0, 153));
        lblLeft.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLeft.setText("\u25c4\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac\u25ac");

        






        panFromTo.add(panFrom);
        panFromTo.add(panTo);

        //panSelect.add(tblMeses,"Center");

        panEste.add(lblYear);
        panEste.add(spiYear);


        //Agregamos el Listener para la seleccion de columnas en la tabla de meses.
        javax.swing.ListSelectionModel LsmCol = tblMeses.getColumnModel().getSelectionModel();
        LsmCol.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                javax.swing.ListSelectionModel lsm = (javax.swing.ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.print("No selecciono ninguna columna");
                } else {
                    /*  Selecciono alguna columna
                     *   utilizamos lsm.getMaxSelectionIndex() y lsm.getMinSelectionIndex()
                     *   para saber que rango de columnas se selecciono y actualizar los DatePicker
                     */
                    java.util.Calendar MiCal = java.util.Calendar.getInstance();
                    MiCal.set(java.util.Calendar.YEAR, Integer.parseInt(spiYear.getValue().toString()));
                    MiCal.set(java.util.Calendar.MONTH, lsm.getMaxSelectionIndex());
                    int UltDiaMes = MiCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    zafDateTo.setText(UltDiaMes,lsm.getMaxSelectionIndex()+1,Integer.parseInt(spiYear.getValue().toString()));
                    zafDateFrom.setText(1,lsm.getMinSelectionIndex()+1,Integer.parseInt(spiYear.getValue().toString()));
                    if(butRight.isSelected())
                        tblMeses.setColumnSelectionInterval( tblMeses.getSelectedColumn() ,11);
                    if(butLeft.isSelected()){
                        int ColSel[] = tblMeses.getSelectedColumns();
                        tblMeses.setColumnSelectionInterval(  0 , ColSel[(ColSel.length - 1)]);
                    }

                }
            }
        });


        spiYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {

                  zafDateFrom.setAnio(Integer.parseInt(spiYear.getValue().toString()));
                  zafDateTo.setAnio(Integer.parseInt(spiYear.getValue().toString()));
            }
        });

      

                //Con borde Normal y titulo setBorder(new javax.swing.border.TitledBorder("Prueba"));
                //Con cualquier Borde y Titulo setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED), "Rangos a elegir"));
                // Titulo sin borde setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)), "Rangos a elegir"));
        //tblMeses.setSelectionMode( javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }






    public void ocultarFecha(){
          panFrom.setVisible(false);
          panTo.setVisible(false);
          panFromTo.setLayout(new java.awt.BorderLayout());

    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  
    private void butLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLeftActionPerformed
          if(butLeft.isSelected()){
              if(tblMeses.getSelectedColumn()>=0){
                int ColSel[] = tblMeses.getSelectedColumns();
                tblMeses.setColumnSelectionInterval(  0 , ColSel[(ColSel.length - 1)]);
              }
              remCompFromTo(lblLeft,0);
          }else{
              remCompFromTo(panFrom,0);
          }

    }//GEN-LAST:event_butLeftActionPerformed


    private void remCompFromTo(java.awt.Component CompAdd, int Idx){
          panFromTo.remove(Idx);
          panFromTo.add(CompAdd, Idx);
          panFromTo.repaint();
          panFromTo.doLayout();
    }

    private void butRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRightActionPerformed
          if(butRight.isSelected()){
              if(tblMeses.getSelectedColumn()>=0)
                tblMeses.setColumnSelectionInterval( tblMeses.getSelectedColumn() ,11);
              remCompFromTo(lblRight,1);

          }else{
              remCompFromTo(panTo,1);
          }

    }//GEN-LAST:event_butRightActionPerformed

    /**
     * Asigna el Texto que saldra en la parte <br>
     * superior izquierda como nombre o titulo<br>
     * de el objeto.<br>
     * Tambien pone Borde<br>
     * Predeterminado saldra como titulo "Rango a elegir:"<br>
     * Y no tendra borde.
     * @param Caption Un string que contiene el texto que queremos que salga de titulo
     * @param Border Un boolean (true o false) que significa que si pondra borde o no
     */


    /**
     * Obtiene el titulo que esta asignado al objeto.
     * @return un String con el titulo tiene el objeto.
     */
    public String getCaption(){
        return strCaption ;
    }

    /**
     * Obtiene informacion de si el borde es visible o no
     * @return <pre>
     *    true .- Si el borde esta visible.
     *    false.- Si el Borde no esta Visible
     * </pre>
     */
    public boolean BorderIsVisible(){
        return BorderVisible;
    }

    /**
     * Asigna el Texto que saldra en la etiqueta desde. <br>
     * De manera predeterminada tiene "Desde: "
     * @param From un String con el texto a presentar en la etiqueta desde:
     */
    public void setFrom(String From){
        strFrom = From;
        lblFrom.setText(From);
    }

    /**
     * Obtiene el Texto que se esta presentando en la etiqueta desde.
     * @return un String con el texto que tiene la etiqueta desde
     */
    public String getFrom(){
        return strFrom;
    }

    /**
     * Asigna el Texto que saldra en la etiqueta hasta. <br>
     * De manera predeterminada tiene "Hasta: "
     * @param To un String con el texto que tiene la etiqueta hasta
     */
    public void setTo(String To){
        strTo = To;
        lblTo.setText(To);
    }
    /**
     * Obtiene el Texto que se esta presentando en la etiqueta hasta.
     * @return un String con el texto que tiene la etiqueta hasta
     */
    public String getTo(){
        return strTo;
    }

    /**
     * Hace que el chkbox este visible u oculto
     * @param isVisible <pre>
     *    true .- Para hacer visible el checkbox
     *    false.- Para Ocultar el checkbox
     * </pre>
     */
    public void chkVisible(boolean isVisible){
        chkVisible = isVisible;
        chkOpcion.setVisible(isVisible);
    }

    /**
     * Devuelve si el chkbox esta oculto o visible
     * @return Un boolean que representa:
     * <pre>
     *    true  .- Si el chkbox esta Visible
     *    false .- Si el chkbox esta Oculto
     * </pre>
     */
    public boolean chkIsVisible(){
        return chkVisible ;
    }

    /**
     * Devuelve si el checkbox esta seleccionado
     * @return Un boolean que representa:
     * <pre>
     *    true .- si esta seleccionado
     *    false.- si no esta seleccionado
     * </pre>
     */
    public boolean chkIsSelected(){
        return  chkOpcion.isSelected();
    }

    /**
     * Selecciona o deselecciona el checkbox
     * @param isSelected <pre>
     *    true .- Para seleccionar el checkbox
     *    false.- Para deseleccionar el checkbox
     * </pre>
     */
    public void chkSetSelected(boolean isSelected){
        chkOpcion.setSelected(isSelected);
    }

    /**
     * Asigna una fecha en el campo desde
     * @param strDateFrom un String con la fecha en Formato "dd/mm/aaaa"
     */
    public void setDateFrom(String strDateFrom){
        zafDateFrom.setText(strDateFrom);
    }

    /**
     * Obtiene la fecha que contiene el campo desde
     * @return un String con la fecha que contiene el campo desde en formato "dd/mm/aaaa"
     */
    public String getDateFrom(){
        return zafDateFrom.getText();
    }

    /**
     * Obtiene la fecha que contiene el campo desde
     * @return un arreglo de Int con la fecha dia int[0] , mes int[1] , anio int[2]
     */
    public int[] getArrayDateFrom(){
        return zafDateFrom.getFecha(getDateFrom());
    }
    /**
     * Asigna una fecha en el campo hasta
     * @param strDateTo un String con la fecha en Formato "dd/mm/aaaa"
     */
    public void setDateTo(String strDateTo){
        zafDateTo.setText(strDateTo);
    }

    /**
     * Obtiene la fecha que contiene el campo hasta
     * @return un String con la fecha que contiene el campo hasta en formato "dd/mm/aaaa"
     */
    public String getDateTo(){
        return zafDateTo.getText();
    }

    /**
     * Verifica ke la fecga en: desde, es una fecha correcta
     * @return True si la fecha en el datepicker desde, es correcta
     *         false si la fecha en el datepicker desde, es incorrecta
     */
    public boolean isDateto(){
        return zafDateTo.isFecha();
    }

    /**
     * Verifica ke la fecga en: hasta, es una fecha correcta
     * @return True si la fecha en el datepicker hasta, es correcta
     *         false si la fecha en el datepicker hasta, es incorrecta
     */
    public boolean isDateFrom(){
        return zafDateFrom.isFecha();
    }

    /**
     * Obtiene la fecha que contiene el campo hasta
     * @return un arreglo de Int con la fecha dia int[0] , mes int[1] , anio int[2]
     */
    public int[] getArrayDateTo(){
        return zafDateTo.getFecha(getDateTo());
    }

    /**
     * Obtiene el AÃ¯Â¿Â½o actual en el que se esta trabajando en el zafSelectDate
     * @return un int que contiene el aÃ¯Â¿Â½o que esta trabajando el zafDatePicker
     */
    public int getYearSelected(){
        return Integer.parseInt(spiYear.getValue().toString());
    }

    /**
     * Obtiene el tipo de seleccion en el selector de Fecha.<br>
     * Puede ser que el usuario haya realizado una busqueda por rango.<br>
     * o por los que sean >= a la fecha desde,  o los <= a la fecha hasta, <br>
     * o quiere ver todo desde el inicio a fin.
     * @return un int que representa:<pre>
     *    1 = Busqueda por Rangos
     *    2 = Busqueda por >= a la fecha Desde
     *    3 = Busqueda por <= a la fecha Hasta
     *    4 = Buscar Todo
     * </pre>
     */
    public int getTypeSeletion(){
        int intTipoBus =1;
            if(butRight.isSelected()||butLeft.isSelected()){
                intTipoBus=4;
                if(butRight.isSelected()&&!butLeft.isSelected())
                    intTipoBus=2;
                if(butLeft.isSelected()&&!butRight.isSelected())
                    intTipoBus=3;
            }

        return intTipoBus;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton butLeft;
    private javax.swing.JToggleButton butRight;
    private javax.swing.JCheckBox chkOpcion;
    private javax.swing.JPanel panCentro;
    private javax.swing.JPanel panEste;
    private javax.swing.JPanel panFromTo;
    private javax.swing.JPanel panSelect;
    private javax.swing.JTable tblMeses;
    // End of variables declaration//GEN-END:variables

    
}
