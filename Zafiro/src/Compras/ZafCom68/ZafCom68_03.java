/*
 * ZafCom68_03.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Compras.ZafCom68;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafCom68_03 extends javax.swing.JDialog 
{
    //Variables.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafImp objZafImp;
    private ZafVenCon vcoPed;                       //Ventana de consulta "Pedidos".
    
    private boolean blnIsAce;  
    private String strSQL, strAux;
    private String strCodPed, strNumPed;            //Contenido del campo al obtener el foco.
    private String strNumPedSel="";

    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafCom68_03(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        //Inicializar objetos.
        objParSis = obj;
        objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));   
        blnIsAce=false;
        initComponents(); 
        configurarPedidos(); 
        txtCodPed.setVisible(false); 
    }
   
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_Doc, a.ne_numDoc, a.tx_numDoc2, a.ne_ani, a.ne_mes";
            strSQL+=" FROM tbm_cabNotPedPreImp AS a";
            strSQL+=" WHERE a.st_Reg IN ('A') AND a.st_cie IN ('N') ";
            strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_TipDoc, a.co_Doc, a.ne_numDoc, a.tx_numDoc2, a.ne_ani, a.ne_mes";
            strSQL+=" ORDER BY a.fe_doc DESC, a.ne_ani desc, a.ne_mes desc, a.tx_numDoc2 ";
            
            //Ocultar columnas.
            int intColOcu[]=new int[5];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=5;
            
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)     {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPed.setCampoBusqueda(5);
                    vcoPed.setVisible(true);
                    if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumPed.setText(vcoPed.getValueAt(6));
                        strNumPedSel = vcoPed.getValueAt(6);
                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumPed.setText(vcoPed.getValueAt(6));
                        strNumPedSel = vcoPed.getValueAt(6);
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(3);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumPed.setText(vcoPed.getValueAt(6));
                            strNumPedSel = vcoPed.getValueAt(6);
                        }
                        else
                        {
                            txtCodPed.setText(strCodPed); 
                        }
                    }
                    break;

                case 2: //Búsqueda directa por "Numero de Pedido.
                    if (vcoPed.buscar("a1.tx_numDoc2", txtNumPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumPed.setText(vcoPed.getValueAt(6));
                        strNumPedSel = vcoPed.getValueAt(6);    
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(5);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumPed.setText(vcoPed.getValueAt(6));
                            strNumPedSel = vcoPed.getValueAt(6);    
                        }
                        else
                        {
                            txtNumPed.setText(strNumPed);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panPed = new javax.swing.JPanel();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumPed = new javax.swing.JTextField();
        butPed = new javax.swing.JButton();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de Items según movimiento de importaciones...");
        setMinimumSize(new java.awt.Dimension(161, 157));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        panTit.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Ingrese el nombre del Pedido Previo:");
        lblTit.setPreferredSize(new java.awt.Dimension(207, 25));
        panTit.add(lblTit, java.awt.BorderLayout.NORTH);

        PanFrm.add(panTit, java.awt.BorderLayout.NORTH);

        panGen.setPreferredSize(new java.awt.Dimension(0, 50));
        panGen.setLayout(new java.awt.BorderLayout());

        panPed.setLayout(null);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panPed.add(lblPed);
        lblPed.setBounds(10, 10, 80, 20);

        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        panPed.add(txtCodPed);
        txtCodPed.setBounds(60, 10, 20, 20);

        txtNumPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedFocusLost(evt);
            }
        });
        txtNumPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumPedActionPerformed(evt);
            }
        });
        panPed.add(txtNumPed);
        txtNumPed.setBounds(80, 10, 220, 20);

        butPed.setText("...");
        butPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedActionPerformed(evt);
            }
        });
        panPed.add(butPed);
        butPed.setBounds(300, 10, 20, 20);

        panGen.add(panPed, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.LINE_END);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(407, 147));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if(!txtCodPed.getText().equals("")){
            aceptar();
        }
        else {
            mostrarMsgInf("<HTML>Seleccione un Pedido y de click en el botón Aceptar.<BR>Si no desea seleccionar pedido alguno de click en Cancelar.</HTML>");
        }    
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnIsAce=false;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void txtCodPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusGained
        strCodPed=txtCodPed.getText();
        txtCodPed.selectAll();
    }//GEN-LAST:event_txtCodPedFocusGained

    private void txtCodPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPed.getText().equalsIgnoreCase(strCodPed))
        {
            if (txtCodPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumPed.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(1);
            }
        }
        else
            txtCodPed.setText(strCodPed);
    }//GEN-LAST:event_txtCodPedFocusLost

    private void txtCodPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPedActionPerformed
        txtCodPed.transferFocus();
    }//GEN-LAST:event_txtCodPedActionPerformed

    private void txtNumPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedFocusGained
        strNumPed=txtNumPed.getText();
        txtNumPed.selectAll();
    }//GEN-LAST:event_txtNumPedFocusGained

    private void txtNumPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumPed.getText().equalsIgnoreCase(strNumPed))
        {
            if (txtNumPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumPed.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(2);
            }
        }
        else
            txtNumPed.setText(strNumPed);

    }//GEN-LAST:event_txtNumPedFocusLost

    private void txtNumPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumPedActionPerformed
        txtNumPed.transferFocus();
    }//GEN-LAST:event_txtNumPedActionPerformed

    private void butPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedActionPerformed
        configurarPedidos();
        mostrarPedidos(0);
    }//GEN-LAST:event_butPedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butPed;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPed;
    private javax.swing.JPanel panTit;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNumPed;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal(){
        //Valida que exista algún pedido seleccionado
        if(txtCodPed.getText().equals("")){
            mostrarMsgInf("<HTML>Seleccione un Pedido y de click en el botón Aceptar.<BR>Si no desea seleccionar pedido alguno de click en Cancelar.</HTML>");
            return false;
        }    
        return true;
    } 
    
    private void aceptar()
    {
        if(isCamVal()){
            blnIsAce=true;
            dispose();
        } 
        else {
            mostrarMsgInf("<HTML>Verifique los datos y vuelva a intentarlo.</HTML>");
        }        
    }    
    
    /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Autorizar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Cerrar
    */
    public boolean isPedSel() {
        return blnIsAce;
    }
    
    public String getNumPedSel(){
        return strNumPedSel;
    }    
  
   
  
    
    
}


