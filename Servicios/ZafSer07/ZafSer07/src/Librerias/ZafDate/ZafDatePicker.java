/*
 * zafDatePicker.java
 * Version : 0.1
 * Created on 29 de julio de 2004, 14:19
 */
   
package Librerias.ZafDate;
import java.awt.event.ActionListener;

/**
 * Crea un DatePicker, con el formato dd/mm/aaaa.
 * @author Idi Reyes Marcillo
 */
public class ZafDatePicker extends javax.swing.JPanel {
     ZafCalendario Calendario;    
     String strFormatDate  ;//"d/m/y"
     String strFormatText  ;//"##/##/####"
     ZafDatePicker dteThis ; 
     /**
      * Construye un nuevo zafDatePicker
      * @param Fecha Recibe un String con la fecha que queremos que salga <br>
      * predeterminada en el datePicker.<br>
      * Ej: zafDatePicker objDatePick = new zafDatePicker("10/12/2004");<br>
      * En el ejemplo estamos enviando la fecha rpedeterminada al 10 de Dic. de 2004
      * @param FrmPrincipal un JFrame que representa el JFrame que contendra al datepicker,
      * para el que el datepicker actuara de manera modal
      */
     public ZafDatePicker(String Fecha, javax.swing.JFrame FrmPrincipal, String strFormatoFecha){
        strFormatDate = strFormatoFecha.toLowerCase();
        strFormatText = FormatoFecha();
        ConstruyeTodo(FrmPrincipal);
        dteThis = this;
        setText(Fecha);
        
    }
    /**
     * Construye un nuevo zafDatePicker con los parametros (dia , mes, aï¿½o)
     * @param Day <br>un Entero que representa el dia predeterminado en el que se va a trabajar
     * @param Mes <br>un Entero que representa el mes predeterminado en el que se va a trabajar<br>
     * puede ser de 1 a 12 (enero a diciembre)
     * @param Anio <br>un Entero que representa el aï¿½o predeterminado en el que se va a trabajar
     * @param FrmPrincipal un JFrame que representa el JFrame que contendra al datepicker,
     * para el que el datepicker actuara de manera modal
     */    
     public ZafDatePicker(int Day, int Mes, int Anio, javax.swing.JFrame FrmPrincipal, String strFormatoFecha){
        strFormatDate = strFormatoFecha.toLowerCase();
        strFormatText = FormatoFecha();
        ConstruyeTodo(FrmPrincipal);
        setText(Day,Mes,Anio);
    }
    /**
     * Construye un DatePicker que presenta la fecha actual del sistema
     * @param FrmPrincipal un JFrame que representa el JFrame que contendra al datepicker,
     * para el que el datepicker actuara de manera modal
     */    
     public ZafDatePicker(javax.swing.JFrame FrmPrincipal, String strFormatoFecha) {
        strFormatDate = strFormatoFecha.toLowerCase();
        strFormatText = FormatoFecha();
        ConstruyeTodo(FrmPrincipal);
        setHoy();
    }

    /**
     * Construye un DatePicker que presenta la fecha actual del sistema
     * @param FrmPrincipal un JFrame que representa el JFrame que contendra al datepicker,
     * para el que el datepicker actuara de manera modal
     */    
     public ZafDatePicker(java.awt.Frame FrmPrincipal, String strFormatoFecha) {
        strFormatDate = strFormatoFecha.toLowerCase();
        strFormatText = FormatoFecha();
        ConstruyeTodo(FrmPrincipal);
        setHoy();
    }
     
    private void ConstruyeTodo(javax.swing.JFrame FormPadre){

        FmtTxtFecha = maskedFormattedTextField(strFormatText,true);	
        FmtTxtFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(!isFecha()){
                    //System.out.println("Entra en no es fecha");
                    setText("");
                }
            }
        });                        
        
        FmtTxtFecha.setBorder(null);
        FmtTxtFecha.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        FmtTxtFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    //    FmtTxtFecha.addPropertyChangeListener("value",  );
        initComponents();
        add(FmtTxtFecha, java.awt.BorderLayout.CENTER);
        Calendario = new ZafCalendario(FormPadre, true);
    }
    
    private void ConstruyeTodo(java.awt.Frame FormPadre){

        FmtTxtFecha = maskedFormattedTextField(strFormatText,true);	
        FmtTxtFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(!isFecha()){
                    //System.out.println("Entra en no es fecha");
                    setText("");
                }
            }
        });                        
        
        FmtTxtFecha.setBorder(null);
        FmtTxtFecha.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        FmtTxtFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    //    FmtTxtFecha.addPropertyChangeListener("value",  );
        initComponents();
        add(FmtTxtFecha, java.awt.BorderLayout.CENTER);
        Calendario = new ZafCalendario(FormPadre, true);
    }
    
    public String FormatoFecha(){
        String strFormatoListo="";
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(strFormatDate,"/");
        
         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){ 
             String  strLetra = MiToken.nextToken().toString();
                    if(strLetra.equals("d"))
                        strFormatoListo =  strFormatoListo + "##/";
                    if(strLetra.equals("m"))    
                        strFormatoListo =  strFormatoListo + "##/";
                    if(strLetra.equals("y"))    
                        strFormatoListo =  strFormatoListo + "####/";
             
             //
         }
     return strFormatoListo.substring(0,10);
    }
    /**
     * Agrega un listener a la caja de texto
     */ 
    public synchronized void addActionListener(ActionListener l) {
        FmtTxtFecha.addActionListener(l);
    }

    /**
     * Asigna la fecha actual al datepicker
     */    
    
    public void setHoy(){
        java.util.Calendar today;
        today = java.util.Calendar.getInstance();

        setText(today.get(java.util.Calendar.DAY_OF_MONTH),
                 today.get(java.util.Calendar.MONTH)+1, 
                 today.get(java.util.Calendar.YEAR));
    }
    
    public void setEnabled(boolean blnEnabled){
          butVerCalendar.setEnabled(blnEnabled);
          FmtTxtFecha.setEnabled(blnEnabled);
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butVerCalendar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        butVerCalendar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        butVerCalendar.setText("V");
        butVerCalendar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        butVerCalendar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        butVerCalendar.setMaximumSize(new java.awt.Dimension(25, 23));
        butVerCalendar.setPreferredSize(new java.awt.Dimension(16, 10));
        butVerCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVerCalendarActionPerformed(evt);
            }
        });
        add(butVerCalendar, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        FmtTxtFecha.requestFocus();
    }//GEN-LAST:event_formFocusGained

    private void butVerCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVerCalendarActionPerformed
        //Calendario.setLocation(butVerCalendar.getLocation());
        Calendario.setLocationRelativeTo(butVerCalendar);
        
        //java.util.Calendar objCalend =  new java.util.Calendar();
        
        
        int fec[] = getFecha(FmtTxtFecha.getText()) ;  
        
        if(fec != null)
            Calendario.setFecha(fec[0],fec[1],fec[2]);   
        
        Calendario.show();

        this.setText(Calendario.Dia(),Calendario.Mes(),Calendario.Anio());
    }//GEN-LAST:event_butVerCalendarActionPerformed
    /*
     *  Recibe un String en formato "dd/mm/aaaa"y devuelve un arreglo de enteros con el 
     *  intArray[0]=dia , intArray[1]=mes , intArray[2]=aï¿½o 
     *  
     */
    /**
     * Obtiene un arreglo de enteros que contiene la fecha en dia,mes,aï¿½o
     * @return un arreglo de enteros con la fecha
     * <pre>
     *    int fecha[0] = dia
     *    int fecha[1] = Mes
     *    int fecha[2] = Aï¿½o
     * </pre>
     * @param Fecha un String con una fecha vï¿½lida, en formato<br>
     * "dd/mm/aaaa"
     */    
    public int [] getFecha(String Fecha){
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(Fecha,"/");
        int fec[] = new int[3] ;  
        char formato[]  =  strFormatDate.replaceAll("/", "").toCharArray();
        
         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){ 
             String  strDate = MiToken.nextToken().toString();
             if(strDate.trim().equals("")){
                fec = null ;
                return fec;
             }
             switch (formato[i]){
                 case 'd':
                     fec[0] = Integer.parseInt(strDate.trim());
                     break;
                 case 'm':
                     fec[1] = Integer.parseInt(strDate.trim());
                     break;
                 case 'y':
                     fec[2] = Integer.parseInt(strDate.trim());
                     break;
             }
         }
        return fec;
    }
    /**
     * Obtiene un String con la fecha que esta en el objeto<br>
     * pero con el formato pasado como parametro
     * @return String con la fecha formateada
     * @param strCaracterSeparador un String cque contiene el caracter que separara los dias de mes y de año<br>
     *  <pre>            
     *                             Por ejemplo: "/"   es separador de "d/m/y"
     *                                          "-"   es separador de "d-m-y"
     *  Es importante que el caracter separador sea el mismo que utiliza el formato 
     *  que se envia
     *  </pre>
     * @param strDateFormat un String cque contiene el el formato en que se devolvera la fecha<br>
     *  <pre>            
     *                             Por ejemplo: "d/m/y" o "y/m/d" 
     *                                          "d-m-y" o "y/m/d"
     *  Es importante que el caracter separador sea el mismo que utiliza el formato 
     *  que se envia
     *  </pre>
     */        
    public String getFecha(String strCaracterSeparador, String strDateFormat){
        //java.util.StringTokenizer MiToken = new java.util.StringTokenizer(this.getText(),"/");
        String strFecha="", strDia,strMes,strAno ;
        int intFec[] = getFecha(this.getText()) ;
        strDia=(intFec[0]<10)?"0"+intFec[0]:""+intFec[0];
        strMes=(intFec[1]<10)?"0"+intFec[1]:""+intFec[1];
        //strAno="" + (1900 + Anio);
        strAno="" + intFec[2];
        
        
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(strDateFormat,strCaracterSeparador);
        
         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){ 
             String  strLetra = MiToken.nextToken().toString();
                    if(strLetra.equals("d"))
                        strFecha =  strFecha + strDia + strCaracterSeparador;
                    if(strLetra.equals("m"))    
                        strFecha =  strFecha + strMes + strCaracterSeparador;
                    if(strLetra.equals("y"))    
                        strFecha =  strFecha + strAno + strCaracterSeparador;
         }        

        return strFecha;
    }
    
    /**
     * Asigna una fecha enviada por parametro
     * @param txt un String que tiene la fecha que se va a asignar en formato "dd/mm/aaaa"<br>
     * caso contrario se asignara null.
     */    
    public void setText(String txt){
        FmtTxtFecha.setText(txt);
    }
    /**
     * Asigna una fecha enviada por parametro
     * @param Dia Un entero que representa el dia que se asignara al datepicker
     * @param Mes Un entero que representa el mes que se asignara al datepicker
     * @param Anio Un entero que representa el aï¿½o que se asignara al datepicker
     */    
    public void setText(int Dia, int Mes, int Anio){
        String strFecha = "", strDia,strMes,strAno ;
        
        strDia=(Dia<10)?"0"+Dia:""+Dia;
        strMes=(Mes<10)?"0"+Mes:""+Mes;
        //strAno="" + (1900 + Anio);
        strAno="" + Anio;
        
        
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(strFormatDate,"/");
        
         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){ 
             String  strLetra = MiToken.nextToken().toString();
                    if(strLetra.equals("d"))
                        strFecha =  strFecha + strDia + "/";
                    if(strLetra.equals("m"))    
                        strFecha =  strFecha + strMes + "/";
                    if(strLetra.equals("y"))    
                        strFecha =  strFecha + strAno + "/";
         }
        
        //strFecha= strDia + "/" + strMes + "/" + strAno;
        FmtTxtFecha.setText(strFecha.substring(0,10));
    }
    public void selectAll(){
        FmtTxtFecha.selectAll();
    }
    
    /**
     * Asigna el aï¿½o que tendra el DatePicker
     * @param Anio un int con el aï¿½o que se asignara al DatePicker
     */    
    public void setAnio(int Anio){
        int FecAct [] = getFecha(FmtTxtFecha.getText());
        this.setText(FecAct[0], FecAct[1], Anio);
    }
    /**
     * Asigna el Mes que tendra el DatePicker
     * @param Mes un int con el mes que se asignara al DatePicker
     */    
    public void setMes(int Mes){
        int FecAct [] = getFecha(FmtTxtFecha.getText());
        this.setText(FecAct[0], Mes,FecAct[2]);
    }
    /**
     * Asigna el Dia que tendra el DatePicker
     * @param Dia un int con el Dia que se asignara al DatePicker
     */    
    public void setDia(int Dia){
        int FecAct [] = getFecha(FmtTxtFecha.getText());
        this.setText( Dia, FecAct[1], FecAct[2]);
    }
    
    /**
     * Obtiene el texto que representa la fecha que tiene el datepicker
     * @return un String que contiene la fecha en formato "dd/mm/aaaa"
     */    
    public String getText(){
        return FmtTxtFecha.getText().toString();
    }
    
    /**
     * Verfica si la fecha ingresada es una fecha vÃ¡lida 
     * @return true  si es una fecha valida, false si no es fecha valida
     */    
    public boolean isFecha(){
        int fec[] = getFecha(FmtTxtFecha.getText()) ;  
        
        
        if(fec == null)
            return false;   

        java.util.Calendar CalVal =  java.util.Calendar.getInstance();

        /*
         * Si el aÃ±o es menor que 1900 no es una fecha valida
         * y se retorna false y salimos de la funcion
         */
        if(fec[2]<1900)
            return false;   
        
        CalVal.set(java.util.Calendar.YEAR,fec[2]);
        
        
        /*
         * Si el Mes no esta entre 1 y 12 no es una fecha vÃ¡lida
         * salimos de la funcion retornando false
         */
        if(fec[1]<1 || fec[1]>12)
            return false;   
        
        CalVal.set(java.util.Calendar.MONTH,fec[1]-1);
        
        
        /*
         * Si el Dia no esta entre 1 y el maximo dia definido para ese mes
         * salimos de la funcion retornando false
         */
        if(fec[0]<1 || fec[0] > CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH))
            return false;
        
        return true;
    }

/*	Recibe los siguientes caracteres para formatear el ingreso de datos
		
		Character  Description
 
		# Un numero valido 
		' Un caracter especial de escape
		U Un caracter ke se conevrtira a mayuscula uppercase
		L Un caracter ke se conevrtira a minuscula lowercase
		A Algun caracter o numero.
		? Un caracter. 
		* Cualquier cosa letra, numero, chars especiales. 
		H Un caracter hexadecimal (0-9, a-f or A-F). 

		Ver referencias en java.sun.com ==> Class MaskFormatter
*/
private static javax.swing.JFormattedTextField maskedFormattedTextField(String mask, boolean valueContainsLiteralChars) {
        	javax.swing.JFormattedTextField field = null;
        	try {
                    
           
                    javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter(mask);
        	    mf.setValueContainsLiteralCharacters(valueContainsLiteralChars);
	            field = field = new javax.swing.JFormattedTextField(mf);
                    
	        }
	        catch (Exception e) {
	            field = new javax.swing.JFormattedTextField();
	        }
                
	        return field;
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butVerCalendar;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JFormattedTextField FmtTxtFecha;    
}
