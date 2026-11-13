/*
 * ZafCom71.java
 *
 * Created on May 09, 2011, 8:09 AM
 */
package Compras.ZafCom71;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author  Gigi
 */
public class ZafCom71 extends javax.swing.JInternalFrame 
{
    //Constantes
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EXP=0;  
    private int intIndiceCon=0;    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoExp, vcoCiu, vcoForPag;
    private java.util.Date datFecAux;
    
    private boolean blnHayCam;    
    
    private String strSQL;
    private String strAux;    
    private String strCodExp, strNomExp;
    private String strDesCorCiu, strCodCiu, strDesLarCiu;
    private String strCodForPag, strNomForPag;

    /** Creates new form ZafCom71 */
    public ZafCom71(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();   
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3");
            lblTit.setText(strAux);
            txtCodExp.setBackground(objParSis.getColorCamposSistema());
            txtNomExp.setBackground(objParSis.getColorCamposObligatorios());

            txtCodCiu.setVisible(false);
            lblForPag.setVisible(false);
            txtCodForPag.setVisible(false);
            txtNomForPag.setVisible(false);
            butForPag.setVisible(false);

            configurarVenConExp();
            configurarVenConCiu();
            configurarVenConForPag();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
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
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
        txtCodExp.getDocument().addDocumentListener(objDocLis);
        txtNomExp.getDocument().addDocumentListener(objDocLis);
        txtDir.getDocument().addDocumentListener(objDocLis);
        txtTel1.getDocument().addDocumentListener(objDocLis);
        txtTel2.getDocument().addDocumentListener(objDocLis);
        txtFax.getDocument().addDocumentListener(objDocLis);
        txtPerCon.getDocument().addDocumentListener(objDocLis);
        txtCodCiu.getDocument().addDocumentListener(objDocLis);
        txtDesCorCiu.getDocument().addDocumentListener(objDocLis);
        txtDesLarCiu.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }    
    

    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_exp, a1.tx_nom";
            strSQL+=" FROM tbm_expimp as a1";
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";

            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoExp.setCampoBusqueda(2);
                    vcoExp.show();
                    if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(0);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtCodExp.setText(strCodExp);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(2);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtNomExp.setText(strNomExp);
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

    private boolean configurarVenConCiu()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ciu");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT co_ciu, tx_descor, tx_deslar";
            strSQL+=" FROM tbm_ciu WHERE st_reg NOT IN('I','E')";

            vcoCiu=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de ciudades", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCiu.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCiu(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCiu.setCampoBusqueda(1);
                    vcoCiu.show();
                    if (vcoCiu.getSelectedButton()==vcoCiu.INT_BUT_ACE){
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtDesCorCiu.setText(vcoCiu.getValueAt(2));
                        txtDesLarCiu.setText(vcoCiu.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoCiu.buscar("a1.tx_desCor", txtDesCorCiu.getText())){
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtDesCorCiu.setText(vcoCiu.getValueAt(2));
                        txtDesLarCiu.setText(vcoCiu.getValueAt(3));
                    }
                    else
                    {
                        vcoCiu.setCampoBusqueda(1);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton()==vcoCiu.INT_BUT_ACE){
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtDesCorCiu.setText(vcoCiu.getValueAt(2));
                            txtDesLarCiu.setText(vcoCiu.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorCiu.setText(strDesCorCiu);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCiu.buscar("a1.tx_desLar", txtDesLarCiu.getText()))
                    {
                        txtCodCiu.setText(vcoCiu.getValueAt(1));
                        txtDesCorCiu.setText(vcoCiu.getValueAt(2));
                        txtDesLarCiu.setText(vcoCiu.getValueAt(3));
                    }
                    else
                    {
                        vcoCiu.setCampoBusqueda(2);
                        vcoCiu.setCriterio1(11);
                        vcoCiu.cargarDatos();
                        vcoCiu.show();
                        if (vcoCiu.getSelectedButton()==vcoCiu.INT_BUT_ACE){
                            txtCodCiu.setText(vcoCiu.getValueAt(1));
                            txtDesCorCiu.setText(vcoCiu.getValueAt(2));
                            txtDesLarCiu.setText(vcoCiu.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCiu.setText(strDesLarCiu);
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
    
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a.co_forPag, a.tx_des as tx_desCor ";
            strSQL+=" FROM tbm_cabForPag as a WHERE a.st_reg NOT IN('I','E')";
            //strSQL+=" WHERE (a.co_emp=1 AND a.co_forPag=109) OR (a.co_emp=2 AND a.co_forPag=127) OR (a.co_emp=2 AND a.co_forPag=107)";

            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de formas de pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setCampoBusqueda(1);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText())){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForPag.buscar("a1.tx_desCor", txtNomForPag.getText())) 
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(2);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtNomForPag.setText(strNomForPag);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblPerCon = new javax.swing.JLabel();
        txtPerCon = new javax.swing.JTextField();
        lblTel2 = new javax.swing.JLabel();
        txtTel1 = new javax.swing.JTextField();
        lblTel1 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        lblFax = new javax.swing.JLabel();
        txtTel2 = new javax.swing.JTextField();
        lblNomExp = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        lblCiu = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtDesCorCiu = new javax.swing.JTextField();
        txtDesLarCiu = new javax.swing.JTextField();
        butCiu = new javax.swing.JButton();
        lblDir = new javax.swing.JLabel();
        lblNomExpPed = new javax.swing.JLabel();
        txtNomExpPed = new javax.swing.JTextField();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(0, 250));
        panDat.setRequestFocusEnabled(false);
        panDat.setLayout(null);

        lblExp.setText("Código:");
        lblExp.setToolTipText("Beneficiario");
        panDat.add(lblExp);
        lblExp.setBounds(4, 0, 62, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panDat.add(txtCodExp);
        txtCodExp.setBounds(136, 0, 124, 20);
        panDat.add(txtNomExp);
        txtNomExp.setBounds(136, 20, 544, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panDat.add(butExp);
        butExp.setBounds(260, 0, 20, 20);

        lblPerCon.setText("Persona de contacto:"); // NOI18N
        panDat.add(lblPerCon);
        lblPerCon.setBounds(4, 100, 130, 20);

        txtPerCon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPerConFocusGained(evt);
            }
        });
        panDat.add(txtPerCon);
        txtPerCon.setBounds(136, 100, 544, 20);

        lblTel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTel2.setText("Teléfono 2:"); // NOI18N
        panDat.add(lblTel2);
        lblTel2.setBounds(280, 80, 66, 20);
        panDat.add(txtTel1);
        txtTel1.setBounds(136, 80, 140, 20);

        lblTel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTel1.setText("Teléfono 1:"); // NOI18N
        panDat.add(lblTel1);
        lblTel1.setBounds(4, 80, 130, 20);
        panDat.add(txtFax);
        txtFax.setBounds(550, 80, 130, 20);

        lblFax.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFax.setText("Fax:"); // NOI18N
        panDat.add(lblFax);
        lblFax.setBounds(500, 80, 40, 20);
        panDat.add(txtTel2);
        txtTel2.setBounds(350, 80, 130, 20);

        lblNomExp.setText("Nombre:"); // NOI18N
        lblNomExp.setToolTipText("Direcciòn de la pagina del cliente/proveedor"); // NOI18N
        panDat.add(lblNomExp);
        lblNomExp.setBounds(4, 20, 130, 20);

        txtDir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDirFocusGained(evt);
            }
        });
        panDat.add(txtDir);
        txtDir.setBounds(136, 60, 544, 20);

        lblCiu.setText("Ciudad:");
        panDat.add(lblCiu);
        lblCiu.setBounds(4, 120, 120, 20);
        panDat.add(txtCodCiu);
        txtCodCiu.setBounds(104, 120, 30, 20);

        txtDesCorCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCiuFocusLost(evt);
            }
        });
        txtDesCorCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCiuActionPerformed(evt);
            }
        });
        panDat.add(txtDesCorCiu);
        txtDesCorCiu.setBounds(136, 120, 116, 20);

        txtDesLarCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCiuFocusLost(evt);
            }
        });
        txtDesLarCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCiuActionPerformed(evt);
            }
        });
        panDat.add(txtDesLarCiu);
        txtDesLarCiu.setBounds(252, 120, 288, 20);

        butCiu.setText("...");
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panDat.add(butCiu);
        butCiu.setBounds(540, 120, 20, 20);

        lblDir.setText("Dirección:"); // NOI18N
        panDat.add(lblDir);
        lblDir.setBounds(4, 60, 130, 20);

        lblNomExpPed.setText("Nombre:"); // NOI18N
        lblNomExpPed.setToolTipText("Direcciòn de la pagina del cliente/proveedor"); // NOI18N
        panDat.add(lblNomExpPed);
        lblNomExpPed.setBounds(4, 40, 130, 20);
        panDat.add(txtNomExpPed);
        txtNomExpPed.setBounds(136, 40, 544, 20);

        lblForPag.setText("Forma de Pago:");
        panDat.add(lblForPag);
        lblForPag.setBounds(4, 140, 130, 20);

        txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusLost(evt);
            }
        });
        txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForPagActionPerformed(evt);
            }
        });
        panDat.add(txtCodForPag);
        txtCodForPag.setBounds(136, 140, 116, 20);

        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        panDat.add(txtNomForPag);
        txtNomForPag.setBounds(252, 140, 288, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panDat.add(butForPag);
        butForPag.setBounds(540, 140, 20, 20);

        panGen.add(panDat, java.awt.BorderLayout.NORTH);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
    catch (Exception e){
        dispose();
    }
}//GEN-LAST:event_exitForm

private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
    txtCodExp.transferFocus();
}//GEN-LAST:event_txtCodExpActionPerformed

private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
    if (txtCodExp.isEditable()) {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)) {
            if (txtCodExp.getText().equals("")) {
                txtCodExp.setText("");
                txtNomExp.setText("");
            } else {
                mostrarVenConExp(1);
            }
        } else
            txtCodExp.setText(strCodExp);
    }
}//GEN-LAST:event_txtCodExpFocusLost

private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
    strCodExp=txtCodExp.getText();
    mostrarVenConExp(0);

}//GEN-LAST:event_butExpActionPerformed

private void txtPerConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPerConFocusGained
    txtPerCon.selectAll();
    txtPerCon.requestFocus();
}//GEN-LAST:event_txtPerConFocusGained

private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
    txtCodExp.requestFocus();
}//GEN-LAST:event_txtCodExpFocusGained

private void txtDirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirFocusGained
    // TODO add your handling code here:
}//GEN-LAST:event_txtDirFocusGained

private void txtDesCorCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCiuActionPerformed
    txtDesCorCiu.transferFocus();
}//GEN-LAST:event_txtDesCorCiuActionPerformed

private void txtDesCorCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCiuFocusGained
    strDesCorCiu=txtDesCorCiu.getText();
    txtDesCorCiu.selectAll();
}//GEN-LAST:event_txtDesCorCiuFocusGained

private void txtDesCorCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCiuFocusLost
    if (!txtDesCorCiu.getText().equalsIgnoreCase(strDesCorCiu)) {
        if (txtDesCorCiu.getText().equals("")) {
            txtCodCiu.setText("");
            txtDesLarCiu.setText("");
        } else {
            mostrarVenConCiu(1);
        }
    } else
        txtDesCorCiu.setText(strDesCorCiu);
}//GEN-LAST:event_txtDesCorCiuFocusLost

private void txtDesLarCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCiuActionPerformed
    txtDesLarCiu.transferFocus();
}//GEN-LAST:event_txtDesLarCiuActionPerformed

private void txtDesLarCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCiuFocusGained
    strDesLarCiu=txtDesLarCiu.getText();
    txtDesLarCiu.selectAll();
}//GEN-LAST:event_txtDesLarCiuFocusGained

private void txtDesLarCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCiuFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesLarCiu.getText().equalsIgnoreCase(strDesLarCiu)) {
        if (txtDesLarCiu.getText().equals("")) {
            txtCodCiu.setText("");
            txtDesCorCiu.setText("");
        } else {
            mostrarVenConCiu(2);
        }
    } else
        txtDesLarCiu.setText(strDesLarCiu);
}//GEN-LAST:event_txtDesLarCiuFocusLost

private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
    mostrarVenConCiu(0);
}//GEN-LAST:event_butCiuActionPerformed

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag)) {
            if (txtCodForPag.getText().equals("")) {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
            } else {
                mostrarVenConForPag(1);
            }
        } else
            txtCodForPag.setText(strCodForPag);
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
        if (!txtNomForPag.getText().equalsIgnoreCase(strNomForPag)) {
            if (txtNomForPag.getText().equals("")) {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
            } else {
                mostrarVenConForPag(2);
            }
        } else
            txtNomForPag.setText(strNomForPag);
    }//GEN-LAST:event_txtNomForPagFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butForPag;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblNomExp;
    private javax.swing.JLabel lblNomExpPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPerCon;
    private javax.swing.JLabel lblTel1;
    private javax.swing.JLabel lblTel2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtDesCorCiu;
    private javax.swing.JTextField txtDesLarCiu;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExpPed;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtPerCon;
    private javax.swing.JTextField txtTel1;
    private javax.swing.JTextField txtTel2;
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
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodExp.setText("");
            txtNomExp.setText("");
            txtDir.setText("");
            txtTel1.setText("");
            txtTel2.setText("");
            txtFax.setText("");
            txtPerCon.setText("");
            txtCodCiu.setText("");
            txtDesCorCiu.setText("");
            txtDesLarCiu.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
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
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
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
    private boolean isCamVal(){
        if (txtNomExp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">nombre de exportador</FONT> es obligatorio.<BR>Escriba un nombre de exportador y vuelva a intentarlo.</HTML>");
            txtNomExp.requestFocus();
            txtNomExp.selectAll();
            return false;
        }
        return true;
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
        
        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
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
        
        public void clickAnterior() 
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
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
        
        public void clickSiguiente()
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam) {
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
                        if (blnHayCam) {
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
        
        public void clickConsultar() {
            txtCodExp.requestFocus();
        }

        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                txtCodExp.setEditable(false);
                txtNomExp.selectAll();
                txtNomExp.requestFocus();
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {
            txtCodExp.setEditable(false);
            cargarReg();
            txtNomExp.selectAll();
            txtNomExp.requestFocus();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }
        
        public void clickAnular() {
        }
        
        public void clickEliminar() {
        }        

        public void clickAceptar() {
        }
        
        public void clickCancelar() {
        }        
        
        public boolean aceptar() {
            return true;
        }


        public boolean afterConsultar() {
            return true;
        }
  
        public boolean afterInsertar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }        

        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                blnRes=false;
            }
            if (!isCamVal())
                return false;
            return blnRes;
        }

        public boolean beforeImprimir() {
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAnular() {
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
        
        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }        

        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }        

        public boolean consultar() {
            consultarReg();
            return true;
        }
        
        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public boolean eliminar() 
        {
            try
            {               
                if(!eliminarReg())
                    return false;
            
                //Desplazarse al siguiente registro si es posible.
                if(arlDatCon.size()>0){
                   if(intIndiceCon < arlDatCon.size()-1){
                        intIndiceCon++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }
                blnHayCam=false;            
            }
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
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
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;            
        }  
        
        
    }


    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                strSQL="";
                strSQL+=" SELECT a1.co_exp";
                strSQL+=" FROM tbm_expImp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E')";
                    
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_exp=" + strAux.replaceAll("'", "''") + "";
                strAux=txtPerCon.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a1.tx_percon) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                strSQL+=" ORDER BY a1.co_exp";
                System.out.println("consultarReg:" + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EXP,     rst.getInt("co_exp"));
                    arlDatCon.add(arlRegCon);
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                

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
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                blnRes=true;
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2, a1.tx_dir, a1.tx_tel1, a1.tx_tel2, a1.tx_fax, a1.tx_percon,";
                strSQL+=" a1.co_ciu, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod,";
                strSQL+=" a1.co_usring, a1.co_usrmod, a2.tx_desCor AS tx_desCorCiu, a2.tx_desLar AS tx_desLarCiu";
                strSQL+=" FROM tbm_expimp AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu";
                strSQL+=" WHERE a1.co_exp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EXP);
                strSQL+=" AND a1.st_reg NOT IN('E')";
                System.out.println("cargarCabReg:" + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtNomExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom2");
                    txtNomExpPed.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_dir");
                    txtDir.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tel1");
                    txtTel1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tel2");
                    txtTel2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_fax");
                    txtFax.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_percon");
                    txtPerCon.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_ciu");
                    txtCodCiu.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCorCiu");
                    txtDesCorCiu.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLarCiu");
                    txtDesLarCiu.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                                       
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
            blnHayCam=false;
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertar_tbmExpImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmExpImp(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_exp)";
                strSQL+=" FROM tbm_expImp AS a1";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodExp.setText("" + intUltReg);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_expimp(";
                strSQL+="             co_exp, tx_nom, tx_nom2, tx_dir, tx_tel1, tx_tel2, tx_fax, tx_percon";
                strSQL+="           , co_ciu, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod)";
                strSQL+=" VALUES (";
                strSQL+="" + txtCodExp.getText() + "";//co_exp
                strSQL+="," + objUti.codificar(txtNomExp.getText()) + "";//tx_nom
                strSQL+="," + objUti.codificar(txtNomExpPed.getText()) + "";//tx_nom2
                strSQL+="," + objUti.codificar(txtDir.getText()) + "";//tx_dir
                strSQL+="," + objUti.codificar(txtTel1.getText()) + "";//tx_tel1
                strSQL+="," + objUti.codificar(txtTel2.getText()) + "";//tx_tel2
                strSQL+="," + objUti.codificar(txtFax.getText()) + "";//tx_fax
                strSQL+="," + objUti.codificar(txtPerCon.getText()) + "";//tx_percon
                strSQL+=", " + objUti.codificar(txtCodCiu.getText()) + "";//co_ciu
                strSQL+="," + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+="," + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=",'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=",'" + strAux + "'";//fe_ing
                strSQL+=",'" + strAux + "'";//fe_ultmod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod

                strSQL+=");";
                System.out.println("insertar_tbmCtaBan: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizar_tbmCtaban()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCtaban(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_expimp";
                strSQL+=" SET tx_nom=" + objUti.codificar(txtNomExp.getText()) + "";
                strSQL+=", tx_nom2=" + objUti.codificar(txtNomExpPed.getText()) + "";
                strSQL+=", tx_dir=" + objUti.codificar(txtDir.getText()) + "";
                strSQL+=", tx_tel1=" + objUti.codificar(txtTel1.getText()) + "";
                strSQL+=", tx_tel2=" + objUti.codificar(txtTel2.getText()) + "";
                strSQL+=", tx_fax=" + objUti.codificar(txtFax.getText()) + "";
                strSQL+=", tx_percon=" + objUti.codificar(txtPerCon.getText()) + "";
                strSQL+=", co_ciu=" + objUti.codificar(txtCodCiu.getText()) + "";
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_exp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EXP);
                System.out.println("UPDATE tbm_expimp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anular_tbmExp()){//no se anula tbm_conCli
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmExp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_expImp";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_exp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EXP);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminar_tbmExp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)     {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmExp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_expImp";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_exp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EXP);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



}
