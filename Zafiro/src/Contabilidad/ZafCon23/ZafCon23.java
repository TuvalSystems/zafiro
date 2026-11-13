/*
 * ZafCon23.java 
 *
 * Created on 03 de enero de 2007
 * "Datos de autorización asignados por el SRI..."
 */  
package Contabilidad.ZafCon23;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Javier Ayapata
 */
public class ZafCon23 extends javax.swing.JInternalFrame 
{
    //Constantes
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_REG=3;  
    private int intIndiceCon=0;
    
    //Variables
    private Connection conGlo;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoTipDoc;
    private ZafDatePicker dtpFecCad;

    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();

    private String strCodTipDoc="", strDesCorTipDoc="",strDesLarTipDoc="";
    private String strSQL, strAux;
    private String strVer = " v0.6 ";

    /** Crea una nueva instancia de la clase ZafCon23. */
    public ZafCon23(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }        
    }
    
    public ZafCon23(Librerias.ZafParSis.ZafParSis obj, Integer codigoCuenta)
    {
        this(obj);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }

    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{        
            //Título de la ventana
            this.setTitle(objParSis.getNombreMenu() + strVer );
            lblTit.setText(objParSis.getNombreMenu());
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objDocLis=new ZafDocLis();
            
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);

            dtpFecCad = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecCad.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecCad.setText("");
            dtpFecCad.setBounds(150, 90, 90, 20);
            panGenDet.add(dtpFecCad);

            //Configurar ventana de consulta
            configurarVenConTipDoc();
        }
        catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");

            //Armar la sentencia SQL. 
            strSQL="";
            if(objParSis.getCodigoUsuario()==1){
                strSQL+=" SELECT a.co_tipdoc,a.tx_descor,a.tx_deslar FROM tbr_tipdocprg AS b ";
                strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc AS a ON (b.co_emp=a.co_emp AND b.co_loc=a.co_loc AND b.co_tipdoc=a.co_tipdoc)";
                strSQL+=" WHERE b.co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND b.co_loc = " + objParSis.getCodigoLocal();
                strSQL+=" AND b.co_mnu = "+objParSis.getCodigoMenu();
            }
            else {
                strSQL+=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar ";
                strSQL+=" FROM tbr_tipDocUsr AS a1 INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private void buscarTipDoc(String campo, String strBusqueda,int tipo)
    {
        vcoTipDoc.setTitle("Listado de Tipos de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda )) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc=vcoTipDoc.getValueAt(1);
            strDesCorTipDoc=vcoTipDoc.getValueAt(2);
            strDesLarTipDoc=vcoTipDoc.getValueAt(3);
        }
        else{
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc=vcoTipDoc.getValueAt(1);
                strDesCorTipDoc=vcoTipDoc.getValueAt(2);
                strDesLarTipDoc=vcoTipDoc.getValueAt(3);
            }
            else{
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCodReg = new javax.swing.JLabel();
        txtCodReg = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        lblNumFacDes = new javax.swing.JLabel();
        txtNumFacDes = new javax.swing.JTextField();
        lblNumFacHas = new javax.swing.JLabel();
        txtNumFacHas = new javax.swing.JTextField();
        lblNumSerDoc = new javax.swing.JLabel();
        txtNumSerDoc = new javax.swing.JTextField();
        lblNumAutSri = new javax.swing.JLabel();
        txtNumAutSri = new javax.swing.JTextField();
        lblFecCad = new javax.swing.JLabel();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(15, 20, 130, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 20, 90, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(240, 20, 260, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(500, 20, 20, 20);

        lblCodReg.setText("Código de registro:");
        lblCodReg.setToolTipText("Código del documento");
        panGenCab.add(lblCodReg);
        lblCodReg.setBounds(15, 40, 130, 20);

        txtCodReg.setBackground(objParSis.getColorCamposSistema());
        txtCodReg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodReg);
        txtCodReg.setBounds(150, 40, 90, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setPreferredSize(new java.awt.Dimension(100, 80));
        panGenDet.setLayout(null);

        lblNumFacDes.setText("Núm.Doc.Desde:");
        lblNumFacDes.setToolTipText("Número Factura Desde");
        panGenDet.add(lblNumFacDes);
        lblNumFacDes.setBounds(15, 10, 130, 20);

        txtNumFacDes.setBackground(objParSis.getColorCamposObligatorios());
        txtNumFacDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenDet.add(txtNumFacDes);
        txtNumFacDes.setBounds(150, 10, 90, 20);

        lblNumFacHas.setText("Núm.Doc.Hasta:");
        lblNumFacHas.setToolTipText("Número Factura Hasta");
        panGenDet.add(lblNumFacHas);
        lblNumFacHas.setBounds(15, 30, 130, 20);

        txtNumFacHas.setBackground(objParSis.getColorCamposObligatorios());
        txtNumFacHas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenDet.add(txtNumFacHas);
        txtNumFacHas.setBounds(150, 30, 90, 20);

        lblNumSerDoc.setText("Núm.Ser.Doc.:");
        lblNumSerDoc.setToolTipText("Número de Serie del Doc.");
        panGenDet.add(lblNumSerDoc);
        lblNumSerDoc.setBounds(15, 50, 130, 20);

        txtNumSerDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNumSerDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenDet.add(txtNumSerDoc);
        txtNumSerDoc.setBounds(150, 50, 90, 20);

        lblNumAutSri.setText("Núm.Aut.SRI.:");
        lblNumAutSri.setToolTipText("Número de Autorizacion del S.R.I.");
        panGenDet.add(lblNumAutSri);
        lblNumAutSri.setBounds(15, 70, 130, 20);

        txtNumAutSri.setBackground(objParSis.getColorCamposObligatorios());
        txtNumAutSri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenDet.add(txtNumAutSri);
        txtNumAutSri.setBounds(150, 70, 90, 20);

        lblFecCad.setText("Fec.Cad.Doc.:");
        lblFecCad.setToolTipText("Fecha de Caducidad del Documento");
        panGenDet.add(lblFecCad);
        lblFecCad.setBounds(15, 90, 130, 20);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(100, 50));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.BorderLayout());

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1, java.awt.BorderLayout.CENTER);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.BorderLayout());

        spnObs1.setPreferredSize(new java.awt.Dimension(52, 100));

        txaObs1.setPreferredSize(new java.awt.Dimension(50, 50));
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1, java.awt.BorderLayout.CENTER);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
    
    
    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else{ buscarTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);   }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else{ buscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);   }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);

    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        buscarTipDoc("a.co_tipdoc","",0);
}//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodReg;
    private javax.swing.JLabel lblFecCad;
    private javax.swing.JLabel lblNumAutSri;
    private javax.swing.JLabel lblNumFacDes;
    private javax.swing.JLabel lblNumFacHas;
    private javax.swing.JLabel lblNumSerDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodReg;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumAutSri;
    private javax.swing.JTextField txtNumFacDes;
    private javax.swing.JTextField txtNumFacHas;
    private javax.swing.JTextField txtNumSerDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }    
    
    private void abrirCnx(){
        try{
           conGlo=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    private void cerrarCnx(){
        try{
            conGlo.close();
            conGlo=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
    private void noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCodReg.getBackground();
        txtCodReg.setEditable(blnEditable);
        txtCodReg.setEditable(blnEditable);
    }    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");            
            txtCodReg.setText("");
            txtNumFacDes.setText("");
            txtNumFacHas.setText("");
            txtNumSerDoc.setText("");
            txtNumAutSri.setText("");
            dtpFecCad.setText("");
            txaObs1.setText("");
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodReg.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar())  {
                            blnRes=objTooBar.insertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar())  {
                            blnRes=objTooBar.modificar();
                        }
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        //Validar "Número de documento: Desde"
        if(txtNumFacDes.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Doc.Desde</FONT> es obligatorio.<BR>Escriba un numeración y vuelva a intentarlo.</HTML>");
            txtNumFacDes.requestFocus();
            return false;
        }
        
        //Validar "Número de documento: Hasta"
        if(txtNumFacHas.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Doc.Hasta</FONT> es obligatorio.<BR>Escriba un numeración y vuelva a intentarlo.</HTML>");
            txtNumFacHas.requestFocus();
            return false;
        }        

        //Validar "Número de serie de documento"
        if(txtNumSerDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Ser.Doc.</FONT> es obligatorio.<BR>Escriba un numeración y vuelva a intentarlo.</HTML>");
            txtNumSerDoc.requestFocus();
            return false;
        }        
        
        //Validar "Número de autorización del SRI"
        if(txtNumAutSri.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Aut.Sri.</FONT> es obligatorio.<BR>Escriba un numeración y vuelva a intentarlo.</HTML>");
            txtNumAutSri.requestFocus();
            return false;
        }                   

        //Validar "Fecha de caducidad"
        if(!dtpFecCad.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fec.Cad.Doc.</FONT> es obligatorio.<BR>Escriba una fecha y vuelva a intentarlo.</HTML>");
            dtpFecCad.requestFocus();
            return false;
        }    
        
        if( !validaExiNumDesHas()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>YA EXISTE NUMERACIÓN INGRESADA.</HTML>");        
            txtNumFacDes.requestFocus();
            return false;
        }
        
        return true;
    }

    private boolean validaExiNumDesHas(){
        boolean blnRes=true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
            if(conLoc!=null) { 
                stmLoc=conLoc.createStatement();

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT * FROM tbm_datautsri ";
                strSQL+=" WHERE  st_reg NOT IN ('I','E')";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc="+objParSis.getCodigoLocal();
                strSQL+=" AND co_tipdoc="+txtCodTipDoc.getText();
                if(txtCodReg.getText().length()>0)
                    strSQL+=" AND co_reg <>"+txtCodReg.getText();
                strSQL+=" AND ( ( "+txtNumFacDes.getText()+" BETWEEN ne_numdocdes AND ne_numdochas )" ;
                strSQL+="        OR (  "+txtNumFacHas.getText()+" BETWEEN ne_numdocdes AND ne_numdochas ) ) ";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }    

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio(){
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }  
        
        public void clickAnterior(){
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }        
        
        public void clickSiguiente(){
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickFin() 
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAceptar()
        {
            
        }

        public void clickAnular()
        {
            cargarCabReg();
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar() 
        {
            txtCodTipDoc.setEditable(true);
            txtDesCorTipDoc.setEditable(true);
            txtDesLarTipDoc.setEditable(true);
            butTipDoc.setEnabled(true);
            txtCodReg.setEditable(true);
            //Inicializar las variables que indican cambios.
            blnHayCam=false;
        }

        public void clickEliminar()
        {
            cargarCabReg();
        }

        public void clickImprimir()
        {
            
        }

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam) {
                    isRegPro();
                }
                limpiarFrm();
                noEditable(false);
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodReg.setEditable(false);
            //Inicializar las variables que indican cambios.
            blnHayCam=false;
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar() 
        {
            if(!consultarReg())
                return false;
            return true;
        }
        
        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean anular()
        {
            if (!anularReg())
                return false;
            
            blnHayCam=false;
            return true;
        }
        
        public boolean eliminar()
        {
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try{
                if ((blnHayCam) ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean vistaPreliminar()
        {
             return true;
        }
        
        public boolean aceptar()
        {            
            return true;           
        }
        
        public boolean imprimir()
        {
             return true;
        }

        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean afterConsultar()
        {
            return true;
        }        
        
        public boolean afterInsertar()
        {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');            
            consultarReg();
            blnHayCam=false;
            return true;            
        }

        public boolean afterModificar()
        {
            blnHayCam = false;
            objTooBar.setEstado('w');
            objTooBar.afterConsultar();
            return true;            
        }

        public boolean afterEliminar()
        {
            blnHayCam=false;
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }
        
    }
  
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            abrirCnx();
            if(conGlo!=null) {
                stmLoc=conGlo.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_reg, a1.ne_numdocdes, a1.ne_numdochas  ";
                strSQL+="      , a1.tx_numserfac, a1.tx_numautsri, a1.tx_feccadfac, a1.fe_ing ";
                strSQL+=" FROM tbm_datAutSri a1 ";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" ";
                if(txtCodTipDoc.getText().length()>0)
                    strSQL+=" AND a1.co_tipdoc="+txtCodTipDoc.getText();
                if(txtCodReg.getText().length()>0)
                    strSQL+=" AND a1.co_reg="+txtCodReg.getText();                
                strSQL+=" ORDER BY a1.co_reg ";
                rstLoc=stmLoc.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rstLoc.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,    rstLoc.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,    rstLoc.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC, rstLoc.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_REG,    rstLoc.getInt("co_reg"));
                    arlDatCon.add(arlRegCon);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                cerrarCnx();
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }                
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e)    { blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if(cargarCabReg()){
                blnRes=true;
            }
            blnHayCam=false;
        }
        catch (Exception e)  {
            blnRes=false;
        }
        return blnRes;
    }
        
    private boolean cargarCabReg(){
        boolean blnRes=true;
        int intPosRel;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            abrirCnx();
            if(conGlo!=null){
                stmLoc=conGlo.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.tx_descor, a2.tx_deslar, a1.co_emp, a1.co_loc, a1.co_tipDoc";
                strSQL+="      , a1.co_reg, a1.ne_numdocdes, a1.ne_numdochas";
                strSQL+="      , tx_numserfac, tx_numautsri, a1.tx_feccadfac, a1.tx_obs1, a1.st_reg ";
                strSQL+=" FROM tbm_datautsri AS a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON( a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc ) ";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_reg=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_REG);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    txtDesCorTipDoc.setText(rstLoc.getString("tx_descor") );
                    txtDesLarTipDoc.setText(rstLoc.getString("tx_deslar"));
                    txtCodTipDoc.setText(rstLoc.getString("co_tipDoc"));
                    txtCodReg.setText(rstLoc.getString("co_reg"));
                    txtNumFacDes.setText(rstLoc.getString("ne_numDocdes"));
                    txtNumFacHas.setText(rstLoc.getString("ne_numDochas"));
                    txtNumSerDoc.setText(rstLoc.getString("tx_numserfac"));
                    txtNumAutSri.setText(rstLoc.getString("tx_numautsri"));
                    dtpFecCad.setText(rstLoc.getString("tx_feccadfac"));
                    txaObs1.setText(rstLoc.getString("tx_obs1"));

                    if (rstLoc.getString("st_reg").equals("A"))
                       strAux="Activo";
                    else if (rstLoc.getString("st_reg").equals("I"))
                        strAux="Anulado";
                    else if (rstLoc.getString("st_reg").equals("E"))
                        strAux="Eliminado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                cerrarCnx();
                //Mostrar la posición relativa del registro.
                intPosRel = intIndiceCon+1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
            }
        }
        catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            abrirCnx();
            if(conGlo!=null) {
                conGlo.setAutoCommit(false);
                if(insertarCab()){
                    conGlo.commit();
                    blnRes=true;
                }
                else   
                    conGlo.rollback();
                        
                cerrarCnx();
            }
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        boolean blnRes=true;
        Statement stmLoc;
        ResultSet rstLoc;
        int intCodReg=0;
        try{
            if(conGlo!=null){
                stmLoc=conGlo.createStatement();

                //Obtiene código de registro
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(co_reg)+1 IS NULL THEN 1 ELSE MAX(co_reg)+1 END AS co_Reg ";
                strSQL+=" FROM tbm_datautsri ";
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc="+txtCodTipDoc.getText();
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){  
                    intCodReg = rstLoc.getInt("co_Reg");  
                }
                txtCodReg.setText(""+intCodReg);
                rstLoc.close(); 
                rstLoc=null; 

                //Armar sentencia SQL
                strSQL="";
                strSQL+=" INSERT INTO tbm_datautsri";
                strSQL+=" ( co_emp, co_loc, co_tipDoc, co_reg, ne_numdocdes, ne_numdochas";
                strSQL+=" , tx_numserfac, tx_numautsri, tx_feccadfac, tx_obs1, st_reg, fe_ing, co_usrIng ) ";
                strSQL+=" VALUES(";
                strSQL+="  " +objParSis.getCodigoEmpresa();
                strSQL+=" ," +objParSis.getCodigoLocal();
                strSQL+=" ," +txtCodTipDoc.getText();
                strSQL+=" ," +intCodReg;
                strSQL+=" ," +objUti.codificar(txtNumFacDes.getText(),2);
                strSQL+=" ," +objUti.codificar(txtNumFacHas.getText(),2);
                strSQL+=" ," +objUti.codificar(txtNumSerDoc.getText());
                strSQL+=" ," +objUti.codificar(txtNumAutSri.getText());
                strSQL+=" ,'"+dtpFecCad.getText()+"'";
                strSQL+=" ," +objUti.codificar(txaObs1.getText());
                strSQL+=" ,'A'";
                strSQL+=" ,"+objParSis.getFuncionFechaHoraBaseDatos();
                strSQL+=" ,"+objParSis.getCodigoUsuario();
                strSQL+=" ); ";
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }    
   
     /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            abrirCnx();
            if(conGlo!=null){
                conGlo.setAutoCommit(false);
                if(actualizarCab()){  
                    conGlo.commit();
                    blnRes=true;
                }
                else
                   conGlo.rollback();
                cerrarCnx(); 
            }
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
   
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab(){
        boolean blnRes=true;
        Statement stmLoc;
        try{
            if(conGlo!=null){
                stmLoc=conGlo.createStatement();
                //Armar sentencia SQL
                strSQL =" ";
                strSQL+=" UPDATE tbm_datautsri ";
                strSQL+=" SET ne_numDocdes="+objUti.codificar(txtNumFacDes.getText(),2);
                strSQL+="   , ne_numDochas="+objUti.codificar(txtNumFacHas.getText(),2);
                strSQL+="   , tx_numserfac="+objUti.codificar(txtNumSerDoc.getText());
                strSQL+="   , tx_numautsri="+objUti.codificar(txtNumAutSri.getText());
                strSQL+="   , tx_feccadfac='"+dtpFecCad.getText()+"' ";
                strSQL+="   , tx_obs1="+objUti.codificar(txaObs1.getText());
                strSQL+="   , fe_ultMod="+objParSis.getFuncionFechaHoraBaseDatos();
                strSQL+="   , co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" ";
                strSQL+=" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_reg="+txtCodReg.getText();
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            abrirCnx();
            if(conGlo!=null){
               conGlo.setAutoCommit(false);
                if(anularCab()){
                    conGlo.commit(); 
                    objTooBar.setEstadoRegistro("Anulado");
                    blnRes=true;
                }
                else
                   conGlo.rollback();

                cerrarCnx();
            }
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
  
    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab(){
        boolean blnRes=true;
        Statement stmLoc;
        try{
            if(conGlo!=null){
                stmLoc=conGlo.createStatement();
                //Armar sentencia SQL
                strSQL =""; 
                strSQL+=" UPDATE tbm_datautsri ";
                strSQL+=" SET st_reg='I'";
                strSQL+="    , fe_ultMod="+objParSis.getFuncionFechaHoraBaseDatos()+", co_usrMod="+objParSis.getCodigoUsuario()+" ";
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" ";
                strSQL+=" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_reg="+txtCodReg.getText();
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }



  
}

