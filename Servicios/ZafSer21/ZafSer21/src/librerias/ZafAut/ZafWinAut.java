/*
 * ZafWinAut2.java
 *
 * Created on 15 de noviembre de 2005, 17:28
 */

package Librerias.ZafAut;

/**
 *
 * @author  ireyes
 */
public class ZafWinAut extends javax.swing.JDialog {
    final int INT_TBL_LINEA    = 0 ;            //fecha de documento
    final int INT_TBL_DESCOR   = 1 ;            //CODIGO ALTERNO DE ITEM
    final int INT_TBL_OBS1     = 2 ;            //Observacion de la regla
    final int INT_TBL_CO_REG   = 3 ;            //Codigo de regla (Hide)
    
    
    /**
     * retorna un entero 
     *-1 ocurrio algun error 
     * 2 Solicita autorizacion
     * 3 Cancela la grabacion.. El usuario no quiere hacer ninguna accion.
     */    
    private int INT_RESUTADO   = 1 ;//RESPUESTA DEL USUARIO
    
    private java.util.Vector vecLstCtrls ; 
    
    private Librerias.ZafPopupMenu.ZafPopupMenu objPopUpMnu ;
    
    /** Creates new form ZafWinAut2 */
    public ZafWinAut(java.awt.Frame parent, boolean modal, java.util.Vector vecLstCrtls) {
        super(parent, modal);
        this.vecLstCtrls = vecLstCrtls;
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                INT_RESUTADO=3;
                dispose();
            }
        });
               
        pack();
        spnMotivos.setColumnHeader(null);
        //objPopUpMnu = new Librerias.ZafPopupMenu.ZafPopupMenu(tblMot);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-400)/2, 400, 400);
        listoGrid();
        tblMot.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); 
    }
    private void listoGrid(){
        java.util.Vector vecColNme = new java.util.Vector();
        vecColNme.add("");
        vecColNme.add("");
        vecColNme.add("");
        vecColNme.add("");
        tblMot.setModel(new javax.swing.table.DefaultTableModel(vecLstCtrls, vecColNme)
        {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            
            boolean[] canEdit = new boolean [] {
                false, false, false , false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }        
        );        
        
        //Agregamos el Listener para la seleccion de columnas en la tabla para ver los datos de la regla
        javax.swing.ListSelectionModel LsmCol = tblMot.getColumnModel().getSelectionModel();
        LsmCol.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                javax.swing.ListSelectionModel lsm = (javax.swing.ListSelectionModel)e.getSource();
                
                if (!lsm.isSelectionEmpty()) {
                    
                    /*  Selecciono alguna columna
                     *   utilizamos lsm.getMaxSelectionIndex() y lsm.getMinSelectionIndex()
                     *   para saber que rango de columnas se selecciono y actualizar los DatePicker
                     */
                    
                    int intCol = lsm.getMaxSelectionIndex();
                    int intRow = tblMot.getSelectedRow();                
                    if(intRow != -1){
                        //txtDesLar.setText(tblMot.getValueAt(intRow , INT_TBL_DESLAR) + "" );
                        txtObs1.setText(tblMot.getValueAt(intRow , INT_TBL_OBS1) + "" );
                    }
                }
            }
        });
        
        //objPopUpMnu.ocultaCol(INT_TBL_DESLAR);
        //objPopUpMnu.ocultaCol(INT_TBL_OBS1);
        ResizeTbl();
    }
    private void ResizeTbl(){
         //Asignando tamaÃ±o y definiendo ke no se pueda cambiar el tamaÃ±o 
         
         tblMot.getTableHeader().setReorderingAllowed(false);
         tblMot.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);
         tblMot.getColumnModel().getColumn(INT_TBL_DESCOR).setPreferredWidth(365);
         tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setPreferredWidth(100);

        /*Asignando anchos de manera que la columna quede oculta*/
        tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setMaxWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setMinWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setResizable(false);
        tblMot.getColumnModel().getColumn(INT_TBL_CO_REG).setPreferredWidth(0);

        /*Asignando anchos de manera que la columna quede oculta*/
        tblMot.getColumnModel().getColumn(INT_TBL_OBS1).setWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_OBS1).setMaxWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_OBS1).setMinWidth(0);
        tblMot.getColumnModel().getColumn(INT_TBL_OBS1).setResizable(false);
        tblMot.getColumnModel().getColumn(INT_TBL_OBS1).setPreferredWidth(0);

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel();
        panSur = new javax.swing.JPanel();
        panSurNor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        spnDesPar = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        spnDesMot = new javax.swing.JScrollPane();
        txtDesLar = new javax.swing.JTextArea();
        panSurSur = new javax.swing.JPanel();
        butSolAut = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        spnMotivos = new javax.swing.JScrollPane();
        tblMot = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de controles");
        jLabel1.setText("El documento no se puede grabar por los siguientes motivos:");
        getContentPane().add(jLabel1, java.awt.BorderLayout.NORTH);

        panSur.setLayout(new java.awt.BorderLayout());

        panSurNor.setLayout(new java.awt.GridLayout(2, 1));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(new javax.swing.border.TitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(32, 70));
        spnDesPar.setPreferredSize(new java.awt.Dimension(20, 40));
        txtObs1.setEditable(false);
        txtObs1.setPreferredSize(new java.awt.Dimension(0, 40));
        spnDesPar.setViewportView(txtObs1);

        jPanel2.add(spnDesPar, java.awt.BorderLayout.CENTER);

        panSurNor.add(jPanel2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(new javax.swing.border.TitledBorder("Observaci\u00f3n"));
        spnDesMot.setViewportView(txtDesLar);

        jPanel1.add(spnDesMot, java.awt.BorderLayout.CENTER);

        panSurNor.add(jPanel1);

        panSur.add(panSurNor, java.awt.BorderLayout.NORTH);

        butSolAut.setText("Solicitar Autorizaci\u00f3n");
        butSolAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolAutActionPerformed(evt);
            }
        });

        panSurSur.add(butSolAut);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });

        panSurSur.add(butCan);

        panSur.add(panSurSur, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        tblMot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnMotivos.setViewportView(tblMot);

        getContentPane().add(spnMotivos, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void butSolAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolAutActionPerformed
        // TODO add your handling code here:
        INT_RESUTADO = 2;
        dispose();
    }//GEN-LAST:event_butSolAutActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        INT_RESUTADO = 3;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
    /**
     * Obtiene la respuesta que usuario dio a la ventana
     *-1 = Ocurrio algun error
     * 2 = Solicita autorizacion
     * 3 = Cancela la ventana
     */ 
    public int getResultado(){
       return INT_RESUTADO;
    }
    
    public java.util.Vector getVecCtls(){
        return vecLstCtrls;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        ZafWinAut objWinAut = new ZafWinAut(new javax.swing.JFrame(), true, new java.util.Vector());
        objWinAut.show();
        objWinAut = null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton butSolAut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panSurNor;
    private javax.swing.JPanel panSurSur;
    private javax.swing.JScrollPane spnDesMot;
    private javax.swing.JScrollPane spnDesPar;
    private javax.swing.JScrollPane spnMotivos;
    private javax.swing.JTable tblMot;
    private javax.swing.JTextArea txtDesLar;
    private javax.swing.JTextArea txtObs1;
    // End of variables declaration//GEN-END:variables
    
    public String getObservacion1()
    {
        return txtDesLar.getText();
    }
    

}
