
package RecursosHumanos.ZafRecHum85;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon; 
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.TableColumnModel;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Vector;

/**
 * Pantalla solicitud de vacaciones
 * @author Sayda Villalta
 * Guayaquil 01/04/2011
 */

public class ZafRecHum85 extends javax.swing.JInternalFrame {
    private final String strVer = " v1.01";                     //Versión de clase
    public ZafDatePicker dtpFecIniCon,dtpFecDesSol,dtpFecHasSol,dtpFecReiSol;                          //Date Time Picker para la fecha de inicio de contrato
    private ZafUtil objUti;                                     //Utilidades
    private ZafParSis objParSis;                                //Parámetros del Sistema
    private String strCodTra = "",strCodTraRee="";
    private String strNomTra = "", strNomTraRee = "";                             //Ventana de consulta.
    private ZafVenCon vcoTra;
    private String strSQL, strAux;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafPerUsr objPerUsr;
    private MiToolBar objTooBar;
    public static final int INT_CON_LINEA = 0;                  //Índice de columna "Línea" de la Tabla de Contactos
    public static final int INT_CON_AÑOS = 1;             
    public static final int INT_CON_DIAS_VACPER = 2;                 //DIAS DE VACACIONES PERMITIDAS POR PERIODO 
    public static final int INT_CON_DIAS_VACPEN = 3;       //DIAS DE VACACIONES PENDIENTE       
    public static final int INT_CON_TIPVAC = 4;       /// PAGADAS O DISFRUTADAS   
    public ZafTblMod zafTblModCon;                              //Table Model para la Tabla de Contactos
    private Vector vecDat,vecReg;
    private boolean blnHayCam;
    private ZafRecHum85 objRecHum85;                            //JInternalFrame manejado
    private String EstadoSoli ;   
    private int intTipDocSolVac = 322;
    private String strCodEmpAut = "";
    private String strCodLocAut = "";
    private String strCodTipDocAut = "";
    private String strCodDocAut = "";
    private boolean blnEstCarSolHSE = false;
    
    private int intRowSugerida = -1; //jp
    
     public ZafRecHum85 (ZafParSis obj) {
         
        try{
         ///   System.out.println(".....llegallll....");
           
            objParSis=(ZafParSis)obj.clone();
            objUti = new ZafUtil();
            
            initComponents();
            configurarFrm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
         
    }
      
    public void Configura_ventana_consulta() {
       
        if (blnEstCarSolHSE) {
            vecDat = null;
            objTooBar.consultarReg_2();
        }
    }
    
    public ZafRecHum85(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        try {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            //actualizarNumeradores();
            configurarFrm();
            this.setTitle(objParSis.getNombreMenu() + " " + strVer);
            lblTit.setText(objParSis.getNombreMenu());

            chkVacAdel.setEnabled(false);
            
            strCodEmpAut = strCodEmp;
            strCodLocAut = strCodLoc;
            strCodTipDocAut = strCodTipDoc;
            strCodDocAut = strCodDoc;
            blnEstCarSolHSE = true;

         objUti = new ZafUtil();
       //     objTooBar = new MiToolBar(this);

            
            objTooBar=new MiToolBar(this);
            objTooBar.setVisible(false); 

            panBar.add(objTooBar);

            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
  
         //   objTooBar.setVisible(false);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtIdeTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        lblNumDoc2 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        btgrVacGoz = new javax.swing.JRadioButton();
        btgrVacPag = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDiaSol = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        chkVacAdel = new javax.swing.JCheckBox();
        txtCodTraRee = new javax.swing.JTextField();
        txtNomTraRee = new javax.swing.JTextField();
        butTraRee = new javax.swing.JButton();
        txtIdeTraRee = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbhisVac = new javax.swing.JTable();
        txtCodTra = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Solicitud de Vacaciones");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        panDat.setLayout(null);

        jLabel6.setText("Empleado:"); // NOI18N
        panDat.add(jLabel6);
        jLabel6.setBounds(30, 80, 80, 20);

        txtIdeTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdeTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeTraFocusLost(evt);
            }
        });
        txtIdeTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeTraActionPerformed(evt);
            }
        });
        panDat.add(txtIdeTra);
        txtIdeTra.setBounds(110, 80, 100, 20);

        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panDat.add(txtNomTra);
        txtNomTra.setBounds(210, 80, 340, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panDat.add(butTra);
        butTra.setBounds(550, 80, 20, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:"); // NOI18N
        panDat.add(lblNumDoc);
        lblNumDoc.setBounds(350, 30, 140, 20);

        lblNumDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc2.setText("Código del Documento:"); // NOI18N
        panDat.add(lblNumDoc2);
        lblNumDoc2.setBounds(350, 50, 140, 20);

        txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panDat.add(txtNumDoc);
        txtNumDoc.setBounds(490, 30, 80, 20);

        txtCodDoc.setEditable(false);
        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDocFocusGained(evt);
            }
        });
        panDat.add(txtCodDoc);
        txtCodDoc.setBounds(490, 50, 80, 20);

        buttonGroup1.add(btgrVacGoz);
        btgrVacGoz.setSelected(true);
        btgrVacGoz.setText("Vac. Gozadas");
        btgrVacGoz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btgrVacGozActionPerformed(evt);
            }
        });
        panDat.add(btgrVacGoz);
        btgrVacGoz.setBounds(460, 140, 110, 23);

        buttonGroup1.add(btgrVacPag);
        btgrVacPag.setText("Vac. Pagadas");
        btgrVacPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btgrVacPagActionPerformed(evt);
            }
        });
        panDat.add(btgrVacPag);
        btgrVacPag.setBounds(460, 120, 110, 23);

        jLabel7.setText("Reemplazo:");
        panDat.add(jLabel7);
        jLabel7.setBounds(30, 220, 70, 20);

        jLabel5.setText("Fecha de Reintegro:");
        panDat.add(jLabel5);
        jLabel5.setBounds(220, 190, 130, 20);

        jLabel2.setText("Dias Solicitados:");
        panDat.add(jLabel2);
        jLabel2.setBounds(30, 190, 100, 20);

        txtDiaSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaSolActionPerformed(evt);
            }
        });
        panDat.add(txtDiaSol);
        txtDiaSol.setBounds(130, 190, 60, 20);

        jLabel4.setText("Fecha Hasta:");
        panDat.add(jLabel4);
        jLabel4.setBounds(220, 160, 110, 14);

        jLabel3.setText("Fecha Desde:");
        panDat.add(jLabel3);
        jLabel3.setBounds(30, 160, 100, 14);

        jLabel1.setText("Fe. Inicio Contrato");
        panDat.add(jLabel1);
        jLabel1.setBounds(30, 120, 110, 20);

        chkVacAdel.setText("Vac. Adelantadas");
        chkVacAdel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVacAdelActionPerformed(evt);
            }
        });
        panDat.add(chkVacAdel);
        chkVacAdel.setBounds(20, 50, 130, 23);
        panDat.add(txtCodTraRee);
        txtCodTraRee.setBounds(80, 220, 20, 20);

        txtNomTraRee.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraReeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraReeFocusLost(evt);
            }
        });
        txtNomTraRee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraReeActionPerformed(evt);
            }
        });
        panDat.add(txtNomTraRee);
        txtNomTraRee.setBounds(190, 220, 360, 20);

        butTraRee.setText(".."); // NOI18N
        butTraRee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraReeActionPerformed(evt);
            }
        });
        panDat.add(butTraRee);
        butTraRee.setBounds(550, 220, 20, 20);

        txtIdeTraRee.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdeTraRee.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeTraReeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeTraReeFocusLost(evt);
            }
        });
        txtIdeTraRee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeTraReeActionPerformed(evt);
            }
        });
        panDat.add(txtIdeTraRee);
        txtIdeTraRee.setBounds(100, 220, 90, 20);

        tbhisVac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbhisVac);

        panDat.add(jScrollPane1);
        jScrollPane1.setBounds(20, 250, 550, 170);

        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panDat.add(txtCodTra);
        txtCodTra.setBounds(90, 80, 20, 20);

        txtObs.setColumns(20);
        txtObs.setRows(5);
        jScrollPane2.setViewportView(txtObs);

        panDat.add(jScrollPane2);
        jScrollPane2.setBounds(20, 450, 550, 50);

        jLabel8.setText("Observacion:");
        panDat.add(jLabel8);
        jLabel8.setBounds(20, 420, 130, 30);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 606, 625);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtIdeTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtIdeTra.getText();
        txtIdeTra.selectAll();
      
    }//GEN-LAST:event_txtIdeTraFocusGained

    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtIdeTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2));
            txtCodTra.setText(vcoTra.getValueAt(3));
            dtpFecIniCon.setText(vcoTra.getValueAt(4));

         }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtIdeTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2));
                txtCodTra.setText(vcoTra.getValueAt(3));      
                dtpFecIniCon.setText(vcoTra.getValueAt(4));
        }else{
                txtIdeTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
        cargarRegTabla(vcoTra.getValueAt(3));
    }
    
    public void BuscarTraRee(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados reemplazo");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtIdeTraRee.setText(vcoTra.getValueAt(1));
            txtNomTraRee.setText(vcoTra.getValueAt(2));
            txtCodTraRee.setText(vcoTra.getValueAt(3));
 
         }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtIdeTraRee.setText(vcoTra.getValueAt(1));
                txtNomTraRee.setText(vcoTra.getValueAt(2));
                txtCodTraRee.setText(vcoTra.getValueAt(3));      
         }else{
                txtIdeTraRee.setText(strCodTraRee);
                txtNomTraRee.setText(strNomTraRee);
            }
        }
     }    
        
    private void txtIdeTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraFocusLost
        // TODO add your handling code here:
        if (!txtIdeTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtIdeTra.getText().equals("")) {
                txtIdeTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.tx_ide", txtIdeTra.getText(), 0);
            }
        } else {
            txtIdeTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtIdeTraFocusLost

    private void txtIdeTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeTraActionPerformed
        txtIdeTra.transferFocus();
        txtNomTra.setText("");
    }//GEN-LAST:event_txtIdeTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
      
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtIdeTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
            txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost
     
    /**
     * 
     * @return 
     */
   private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.nombre");
            arlCam.add("a1.co_tra");
            arlCam.add("a1.fe_inicon");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("codigo");
            arlAli.add("Fec.IniCon");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("255");
            arlAncCol.add("50");
            arlAncCol.add("0");
            
            String strSQL="";

                strSQL+="SELECT a.co_tra,(a.tx_ape || ' ' || a.tx_nom) nombre, a.tx_ide , TO_CHAR(b.fe_inicon, 'DD/MM/YYYY') AS fe_inicon   "+"\n";
                strSQL+="FROM tbm_tra a  "+"\n";
                strSQL+="INNER JOIN tbm_traemp b on(b.co_tra=a.co_tra AND b.st_reg = 'A')  "+"\n";
                strSQL+="WHERE    a.st_reg='A' and  b.co_emp= "+ objParSis.getCodigoEmpresa();//+objParSis.getCodigoEmpresa()+"\n";
                strSQL+="ORDER BY (a.tx_ape || ' ' || a.tx_nom)";

           // System.out.println(strSQL);
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   private void initTbl(){
        //Títulos de Cabecera
        Vector vecCabCon=new Vector();
        vecCabCon.clear();
        vecCabCon.add(INT_CON_LINEA, "");
        vecCabCon.add(INT_CON_AÑOS, "Año");
        vecCabCon.add(INT_CON_DIAS_VACPER, "Dias Vac.Permitidas");
        vecCabCon.add(INT_CON_DIAS_VACPEN, "Dias Vac.Pendientes");
        vecCabCon.add(INT_CON_TIPVAC, "Tip. Vac"); 

        zafTblModCon = new ZafTblMod();
        zafTblModCon.setHeader(vecCabCon);
        tbhisVac.setModel(zafTblModCon);
        tbhisVac.setRowSelectionAllowed(true);
          
        new ZafColNumerada(tbhisVac,INT_CON_LINEA);

        tbhisVac.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tbhisVac.setDefaultRenderer(Object.class, new RenFilaSugerida());
        
        //Ancho de columnas
        TableColumnModel tcmAux = tbhisVac.getColumnModel();
        tcmAux.getColumn(INT_CON_LINEA).setPreferredWidth(40);
        tcmAux.getColumn(INT_CON_AÑOS).setPreferredWidth(50);
        tcmAux.getColumn(INT_CON_DIAS_VACPER).setPreferredWidth(150);
        tcmAux.getColumn(INT_CON_DIAS_VACPEN).setPreferredWidth(150);
        tcmAux.getColumn(INT_CON_TIPVAC).setPreferredWidth(150); 

        //Columnas editables
        Vector vecColEdi = new Vector(); 
 
        zafTblModCon.setColumnasEditables(vecColEdi);
        vecColEdi = null;
  
        zafTblModCon.setModoOperacion(ZafTblMod.INT_TBL_INS);
        
        
    }
   
    /**
     * 
     * @return 
     */
     private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            
            
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleAceptar(true);
            objTooBar.setVisibleInsertar(true);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleVistaPreliminar(false);
            objTooBar.setVisibleImprimir(false);

            panBar.add(objTooBar);
            
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            //Configurar objetos.   
            chkVacAdel.setVisible(false);
            
            
            txtNumDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNomTra.setBackground(objParSis.getColorCamposObligatorios());
            txtIdeTra.setBackground(objParSis.getColorCamposObligatorios()); 
            txtCodTraRee.setBackground(objParSis.getColorCamposObligatorios());
 
             //Configurar ZafDatePicker: Para la fecha Inicio de Contrato
            dtpFecIniCon=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecIniCon.setBackground(objParSis.getColorCamposSistema());
            dtpFecIniCon.setText("");
            panDat.add(dtpFecIniCon);
            dtpFecIniCon.setBounds(140, 116, 90, 20);
            dtpFecIniCon.setEnabled(false);  /// TEMPORAL!!! 
            //fin config zafDatePicker
            
            //Configurar ZafDatePicker: Para la fecha desde Solicitud
            dtpFecDesSol=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDesSol.setBackground(objParSis.getColorCamposSistema());
            dtpFecDesSol.setText("");
            panDat.add(dtpFecDesSol);
            dtpFecDesSol.setBounds(109, 155, 90, 20);
            dtpFecDesSol.setEnabled(false);  /// TEMPORAL!!!
            
            
            
            
            //fin config zafDatePicker
            
             //Configurar ZafDatePicker: Para la fecha hasta Solicitud
            dtpFecHasSol=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecHasSol.setBackground(objParSis.getColorCamposSistema());
            dtpFecHasSol.setText("");
            panDat.add(dtpFecHasSol);
            dtpFecHasSol.setBounds(298, 155, 90, 20);
            dtpFecHasSol.setEnabled(false);  /// TEMPORAL!!! 
            //fin config zafDatePicker
            
            //Configurar ZafDatePicker: Para la fecha de reintregro
            dtpFecReiSol=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecReiSol.setBackground(objParSis.getColorCamposSistema());
            dtpFecReiSol.setText("");
            panDat.add(dtpFecReiSol);
            dtpFecReiSol.setBounds(338, 190, 90, 20);
            dtpFecReiSol.setEnabled(false);  /// TEMPORAL!!! 
            //fin config zafDatePicker
            
            // Recalcular al cambiar "Fecha Desde" o "Fecha Hasta"
            dtpFecDesSol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { actualizarDiasYReintegro(); }
            });
            dtpFecHasSol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) { actualizarDiasYReintegro(); }
            });

            // Opcional: también al salir del campo (por si escriben manualmente)
            dtpFecDesSol.getComponent(0).addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) { actualizarDiasYReintegro(); }
            });
            dtpFecHasSol.getComponent(0).addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) { actualizarDiasYReintegro(); }
            });
            
             txtCodTraRee.setVisible(false);
             txtCodTra.setVisible(false);

            //Configurar los JTables.
            configurarVenConTra();            
            initTbl();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     @SuppressWarnings("serial")
    private class RenFilaSugerida extends javax.swing.table.DefaultTableCellRenderer {
        //private final java.awt.Color VERDE_SUAVE = new java.awt.Color(198, 239, 206); // estilo Excel

        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            // reset colores cuando NO está seleccionada
            if (!isSelected) {
                c.setForeground(java.awt.Color.BLACK);
                c.setBackground(java.awt.Color.WHITE);
            }

            // pinta la fila sugerida
            if (row == intRowSugerida && !isSelected) {
                //c.setBackground(VERDE_SUAVE);
                c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
            }
            return c;
        }
    }
     
     // Calcula días solicitados (inclusive) y fija el reintegro = día siguiente a "hasta".
    private void actualizarDiasYReintegro() {
        try {
            String sDes = dtpFecDesSol.getText();
            String sHas = dtpFecHasSol.getText();
            if (sDes == null || sDes.trim().isEmpty()) return;
            if (sHas == null || sHas.trim().isEmpty()) return;

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            java.util.Date d1 = sdf.parse(sDes);
            java.util.Date d2 = sdf.parse(sHas);

            long diffMs = d2.getTime() - d1.getTime();
            if (diffMs < 0) {
                // si el usuario puso hasta < desde, no hacemos nada
                return;
            }

            long dias = (diffMs / (1000L * 60 * 60 * 24)) + 1; // inclusivo
            txtDiaSol.setText(String.valueOf(dias));

            // reintegro = día siguiente a "hasta"
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(d2);
            cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
            dtpFecReiSol.setText(
                String.format("%02d/%02d/%04d",
                    cal.get(java.util.Calendar.DAY_OF_MONTH),
                    cal.get(java.util.Calendar.MONTH) + 1,
                    cal.get(java.util.Calendar.YEAR))
            );
        } catch (Exception ex) {
            // silenciar: si el usuario aún no termina de digitar la fecha, ignoramos
        }
    }
    
     private boolean cargarRegTabla(String strco_tra) {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "";
        boolean blnRes = true;

        // ---- NUEVO: variables para detectar la fila sugerida
        int filaSugerida = -1;
        boolean buscaUltimoPerUsado = false;      // vimos algún periodo con pendientes = 0
        int filaDisponible = -1;          // respaldo: 1ra fila con > 0 si nunca hubo 0
        int fila = 0;

        try {
            con = DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos());

            vecDat = new Vector();

            if (con != null) {
                stm = con.createStatement();
                strSql  = "SELECT * "
                        + "FROM tbm_cfgvactra "
                        + "WHERE co_emp=" + objParSis.getCodigoEmpresa()
                        + "  AND co_tra=" + strco_tra
                        + "  AND st_reg = 'A' "
                        + " ORDER BY ne_anio";  // importante para la lógica
                System.out.println("cargarRegTabla: " + strSql);
                rst = stm.executeQuery(strSql);

                vecDat.clear();
                if (rst.next()) {
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_CON_LINEA, ""); 
                        vecReg.add(INT_CON_AÑOS,  rst.getString("ne_anio"));
                        vecReg.add(INT_CON_DIAS_VACPER, rst.getString("ne_diaper"));

                        int diasPermitidos = rst.getInt("ne_diaper");
                        int diasGozados    = rst.getInt("ne_diagoz");
                        int diasPagados    = rst.getInt("ne_diapag");
                        int diaspend       = diasPermitidos - (diasGozados + diasPagados);

                        vecReg.add(INT_CON_DIAS_VACPEN, diaspend);

                        if (diasGozados > 0) {
                            vecReg.add(INT_CON_TIPVAC, "Vac. Gozadas");
                        } else if (diasPagados > 0) {
                            vecReg.add(INT_CON_TIPVAC, "Vac. Pagadas");
                        } else {
                            vecReg.add(INT_CON_TIPVAC, "");
                        }

                        // Mantén tu lógica de check según pendientes
                        if (diaspend <= 0) {
                            chkVacAdel.setSelected(true);
                            chkVacAdel.setEnabled(false);
                        } else {
                            chkVacAdel.setSelected(false);
                        }

                        vecDat.add(vecReg);

                        // ---- NUEVO: detección de fila sugerida
                        if (diaspend == 0) buscaUltimoPerUsado = true;
                        if (diaspend > 0 && filaDisponible == -1) {
                            filaDisponible = fila;
                        }
                        if (filaSugerida == -1 && buscaUltimoPerUsado && diaspend > 0) {
                            filaSugerida = fila;  // primera >0 después de un 0
                        }

                        fila++;
                    } while (rst.next());
                } else {
                    chkVacAdel.setSelected(true);
                    chkVacAdel.setEnabled(false);
                }
            }

            // Asignar al modelo
            zafTblModCon.setData(vecDat);
            tbhisVac.setModel(zafTblModCon);

            // ---- NUEVO: si no hubo “>0 después de 0”, usar la primera >0
            if (filaSugerida == -1) filaSugerida = filaDisponible;

            // Guardar en la variable de clase para que el renderer pinte
            this.intRowSugerida = filaSugerida;

            // Refrescar y seleccionar/mostrar la fila sugerida
            tbhisVac.repaint();
            if (intRowSugerida >= 0 && intRowSugerida < tbhisVac.getRowCount()) {
                tbhisVac.getSelectionModel().setSelectionInterval(intRowSugerida, intRowSugerida);
                tbhisVac.scrollRectToVisible(tbhisVac.getCellRect(intRowSugerida, 0, true));
            }

            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
        } finally {
            try { if (rst != null) rst.close(); } catch (Exception ex) {}
            try { if (stm != null) stm.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return blnRes;
    }

     
    private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){                        
                        txtIdeTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                        txtCodTra.setText(vcoTra.getValueAt(3)); 
                        dtpFecIniCon.setText(vcoTra.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.tx_ide", txtIdeTra.getText())){
                        txtIdeTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                        txtCodTra.setText(vcoTra.getValueAt(3));
                        dtpFecIniCon.setText(vcoTra.getValueAt(4));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtIdeTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2));
                            txtCodTra.setText(vcoTra.getValueAt(3));     
                            dtpFecIniCon.setText(vcoTra.getValueAt(4));

                        }
                        else{
                            txtIdeTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.nombre", txtNomTra.getText())){
                        txtIdeTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                        txtCodTra.setText(vcoTra.getValueAt(3));  
                        dtpFecIniCon.setText(vcoTra.getValueAt(4));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtIdeTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2));
                            txtCodTra.setText(vcoTra.getValueAt(3));
                            dtpFecIniCon.setText(vcoTra.getValueAt(4));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;
            }
            dtpFecIniCon.setEnabled(false);  /// TEMPORAL!!! 
            //cargar tabla de visualizacion 
            cargarRegTabla(vcoTra.getValueAt(3));

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
   
    private boolean mostrarVenConTraRee(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtIdeTraRee.setText(vcoTra.getValueAt(1));
                        txtNomTraRee.setText(vcoTra.getValueAt(2));
                        txtCodTraRee.setText(vcoTra.getValueAt(3));

                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.tx_ide", txtIdeTraRee.getText())){
                        txtIdeTraRee.setText(vcoTra.getValueAt(1));
                        txtNomTraRee.setText(vcoTra.getValueAt(2));
                        txtCodTraRee.setText(vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtIdeTraRee.setText(vcoTra.getValueAt(1));
                            txtNomTraRee.setText(vcoTra.getValueAt(2));
                            txtCodTraRee.setText(vcoTra.getValueAt(3));
                        }
                        else{
                            txtIdeTraRee.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTraRee.getText())){
                        txtIdeTraRee.setText(vcoTra.getValueAt(1));
                        txtNomTraRee.setText(vcoTra.getValueAt(2));
                        txtCodTraRee.setText(vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtIdeTraRee.setText(vcoTra.getValueAt(1));
                            txtNomTraRee.setText(vcoTra.getValueAt(2));
                            txtCodTraRee.setText(vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTraRee.setText(strNomTra);
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
    
    
    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();    
        txtIdeTra.setText("");

    }//GEN-LAST:event_txtNomTraActionPerformed

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtIdeTra.getText();
      
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        txtNumDoc.selectAll();
        txtNumDoc.disable();
        
        
    }//GEN-LAST:event_txtNumDocFocusGained

    private void chkVacAdelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVacAdelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkVacAdelActionPerformed

    private void txtNomTraReeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraReeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTraReeFocusGained

    private void txtNomTraReeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraReeFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTraReeFocusLost

    private void txtNomTraReeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraReeActionPerformed
        // TODO add your handling code here:
          txtNomTraRee.transferFocus();
    }//GEN-LAST:event_txtNomTraReeActionPerformed

    private void butTraReeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraReeActionPerformed
        // TODO add your handling code here:
         strCodTraRee=txtIdeTraRee.getText();
      
        mostrarVenConTraRee(0);
    }//GEN-LAST:event_butTraReeActionPerformed

    private void txtIdeTraReeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraReeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdeTraReeFocusGained

    private void txtIdeTraReeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraReeFocusLost
        // TODO add your handling code here:
           if (!txtIdeTraRee.getText().equalsIgnoreCase(strCodTraRee)) {
            if (txtIdeTraRee.getText().equals("")) {
                txtIdeTraRee.setText("");
                txtNomTraRee.setText("");
            } else {
                BuscarTraRee("a1.tx_ide", txtIdeTraRee.getText(), 0);
            }
        } else {
            txtIdeTraRee.setText(strCodTraRee);
        }
    }//GEN-LAST:event_txtIdeTraReeFocusLost

    private void txtIdeTraReeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeTraReeActionPerformed
        // TODO add your handling code here:
        txtIdeTraRee.transferFocus();

    }//GEN-LAST:event_txtIdeTraReeActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void btgrVacPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btgrVacPagActionPerformed
        // TODO add your handling code here:
            dtpFecDesSol.setText("");
            dtpFecHasSol.setText("");
            dtpFecReiSol.setText("");
            dtpFecDesSol.setEnabled(false);
            dtpFecHasSol.setEnabled(false);
            dtpFecReiSol.setEnabled(false);
            txtIdeTraRee.setText("");
            txtNomTraRee.setText("");
            txtCodTraRee.setText("");            
            txtIdeTraRee.setEnabled(false);
            txtNomTraRee.setEnabled(false);
            txtCodTraRee.setEnabled(false);
            butTraRee.setEnabled(false);
    }//GEN-LAST:event_btgrVacPagActionPerformed

    private void btgrVacGozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btgrVacGozActionPerformed
        // TODO add your handling code here:
            dtpFecDesSol.setEnabled(true);
            dtpFecHasSol.setEnabled(true);
            dtpFecReiSol.setEnabled(true);          
            txtIdeTraRee.setEnabled(true);
            txtNomTraRee.setEnabled(true);
            txtCodTraRee.setEnabled(true);
            butTraRee.setEnabled(true);

    }//GEN-LAST:event_btgrVacGozActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        System.out.println("si la llama xD");
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDiaSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaSolActionPerformed
        // TODO add your handling code here:
        
            
        
    }//GEN-LAST:event_txtDiaSolActionPerformed

    private void txtCodDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusGained
        // TODO add your handling code here:
        txtCodDoc.selectAll();
        txtCodDoc.disable();
    }//GEN-LAST:event_txtCodDocFocusGained

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocFocusLost

    

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
 

    private void dtpFecNacActionPerformed(java.awt.event.ActionEvent evt) {
        //zafRecHum03Bean.getTooBar().consultar();
    }

    private void anular(String text, ZafParSis objParSis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   private class ZafTblModLis implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btgrVacGoz;
    private javax.swing.JRadioButton btgrVacPag;
    private javax.swing.JButton butTra;
    private javax.swing.JButton butTraRee;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkVacAdel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDat;
    private javax.swing.JTable tbhisVac;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtCodTraRee;
    private javax.swing.JTextField txtDiaSol;
    private javax.swing.JTextField txtIdeTra;
    private javax.swing.JTextField txtIdeTraRee;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtNomTraRee;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs;
    // End of variables declaration//GEN-END:variables

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
         
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

        public void clickInicio() {
            try{
                if (!rstCab.isFirst()){
                    rstCab.first();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try{
                if (!rstCab.isFirst()){
                    rstCab.previous();
                    cargarReg();
                         
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }           
        }

        public void clickSiguiente() {
            try
            {
                if (!rstCab.isLast())
                {
                     
                         
                    rstCab.next();
                    cargarReg();
                         
                     
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                         
                    rstCab.last();
                    cargarReg();
                         
                     
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            limpiarFrm();
            actualizarNumeradores();
        }

        public void clickConsultar() {            
        }
 
        public void clickAnular() {
             objUti.desactivarCom(objRecHum85);
        }
        
        private boolean anularSolicitud(java.sql.Connection conexion){
            boolean blnRes=true;
            String strIns ;
            try{
                if (conexion!=null){
                    stm=conexion.createStatement();   
                  
                    strIns="";
                    strIns+="UPDATE tbm_cabSolVac set st_reg='I' where co_emp="+ objParSis.getCodigoEmpresa() + " and co_tipdoc=" + intTipDocSolVac +" and co_loc="+objParSis.getCodigoLocal() + " and co_doc=" + txtCodDoc.getText().toString();
                  //  System.out.println("strIns " + strIns);
                    stm.executeUpdate(strIns);           
                    
                    stm.close();
                    stm=null;
                }
               
            }catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
          return blnRes;
        }
        
        
    public boolean anular() {
        java.sql.Connection conLoc;
            boolean blnOk = false;
          
            try { 
                String strAux=objTooBar.getEstadoRegistro();
            
            if (strAux.equals("Anulado"))
            {
                mostrarMsgCon("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if(txtCodDoc.getText() !="" ){
                    
                    conLoc=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(conLoc!=null){
                        conLoc.setAutoCommit(false);
                          if(anularSolicitud(conLoc)){
                              blnOk = true;
                              conLoc.commit(); 
                              EstadoSoli="I";
                          }  
                    }
                    conLoc.close();
                    conLoc=null;   
                    
                    mostrarEstado();                         
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(objRecHum85,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }  
                
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum85, ex);
            }
            return blnOk;
        }
 
    public void clickAceptar() {
        }

    public void clickCancelar() {
        }

    public boolean insertar() {
        boolean Continuar=true;
        if (btgrVacPag.isSelected()) {
            if (txtDiaSol.getText().isEmpty() ) {
                Continuar=false;
                JOptionPane.showMessageDialog(null, "Por favor, Los dias de solicitud es obligatorios.");
            } else if (txtCodTra.getText().isEmpty() ) {
                Continuar=false;
                System.out.println("Por favor, El trabajador es obligatorios.");
            }
        } 
        if (btgrVacGoz.isSelected()) {
            if (txtDiaSol.getText().isEmpty() ) { 
                Continuar=false;
                JOptionPane.showMessageDialog(null, "Por favor, dias de solicitud es obligatorios.");
            } else if (txtCodTra.getText().isEmpty() ) {
                Continuar=false;
                System.out.println("Por favor, El trabajador es obligatorios.");
            }else if (dtpFecDesSol.getText().isEmpty() ) {
                Continuar=false;
                System.out.println("Por favor, La fecha Desde solicitud es obligatorios.");
            }else if (dtpFecHasSol.getText().isEmpty() ) {
                Continuar=false;
                System.out.println("Por favor, La fecha Hasta solicitud es obligatorios.");
            }else if (dtpFecReiSol.getText().isEmpty() ) {
                Continuar=false;
                System.out.println("Por favor,La fecha reintegro solicitud es obligatorios.");
            }
        }  
        if (Continuar){
            if (!insertarReg())
                Continuar=false;
            
        } 
        return Continuar;
        } 
    
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertarSol()){
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
            
    private int getCodigodoc(java.sql.Connection conExt,  int Codloc){
        int intRet=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_doc) IS NULL THEN 1 ELSE MAX(a1.co_doc)+1 END AS maximoRegistro \n";
                strSQL+=" FROM tbm_cabSolVac as a1 \n";
                strSQL+=" WHERE a1.co_emp="+ objParSis.getCodigoEmpresa() + " AND a1.co_tipdoc="+ intTipDocSolVac+" and a1.co_loc="+Codloc ;
                System.out.println("CodigoDoc: "+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRet = rstLoc.getInt("maximoRegistro");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            intRet=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           intRet=-1;
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return intRet;
    }
    
    private boolean insertarSol(){
        String strIns, st_vacad = "N", codtraree = "null", numdiasolpag = "null", numdiasolgoz = "null";
        //st_vacad="",codtraree="", numdiasolpag="",numdiasolgoz="" ;
        boolean blnRes=true;
        int codlocal=0,codDoc=0;
         
        try{
            if (con!=null){
                stm=con.createStatement();   
                codlocal = 0;
                numdiasolpag = "NULL";
                numdiasolgoz = "NULL";
                codDoc = 0;
                codlocal= objParSis.getCodigoLocal();
                codDoc=getCodigodoc(con,codlocal);               
                
                //validacion dias contra disponibles por periodo
                int[] arrPerVac = obtenerPeriodoVacacionesDisponible(con, txtCodTra.getText());

                int intPerHasVac = arrPerVac[0];
                int intDiaPenVac = arrPerVac[1];
                int intCanPerDisp = arrPerVac[2];

                
                if (intPerHasVac <= 0) {
                    JOptionPane.showMessageDialog(null, "El trabajador no tiene período de vacaciones disponible.");
                    return false;
                }

                int intDiaSol = Integer.parseInt(txtDiaSol.getText().trim());

                if (intDiaSol > intDiaPenVac) {
                    String strMsg = "";

                    strMsg += "No puede solicitar " + intDiaSol + " días.\n";

                    if (intCanPerDisp > 1) {
                        strMsg += "Debe consumir primero el período " + intPerHasVac + ", que solo tiene " + intDiaPenVac + " día(s) pendiente(s).";
                    } else {
                        strMsg += "El período " + intPerHasVac + " tiene solo " + intDiaPenVac + " día(s) pendiente(s).";
                    }

                    JOptionPane.showMessageDialog(null, strMsg);
                    return false;
                }
                
                ResumenPeriodoVac objResumenPeriodo = obtenerResumenPeriodoVac(con,Integer.parseInt(txtCodTra.getText().trim()),intPerHasVac,intDiaSol);

                if (objResumenPeriodo == null) {JOptionPane.showMessageDialog(null,"No se pudo obtener la información del período de vacaciones.");
                    return false;
                }
                
                
                //PONER VALIDACION SI YA EXISTE UNA SOLICITUD PARA EL TRABAJADOR CON CRUCE DE FECHAS
                if (btgrVacGoz.isSelected()) {

                    String strFecDes = objUti.formatearFecha(
                            dtpFecDesSol.getText(),
                            "dd/MM/yyyy",
                            objParSis.getFormatoFechaBaseDatos()
                    );

                    String strFecHas = objUti.formatearFecha(
                            dtpFecHasSol.getText(),
                            "dd/MM/yyyy",
                            objParSis.getFormatoFechaBaseDatos()
                    );

                    String strVal = "";
                    strVal += "SELECT 1 ";
                    strVal += "FROM tbm_cabSolVac a ";
                    strVal += "INNER JOIN tbm_detSolVac b ";
                    strVal += "   ON b.co_emp = a.co_emp ";
                    strVal += "  AND b.co_loc = a.co_loc ";
                    strVal += "  AND b.co_tipdoc = a.co_tipdoc ";
                    strVal += "  AND b.co_doc = a.co_doc ";
                    strVal += "WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                    strVal += "  AND a.co_tra = " + txtCodTra.getText() + " ";
                    strVal += "  AND a.st_reg = 'A' ";
                    strVal += "  AND a.st_aut IN ('P', 'A', 'T') ";
                    strVal += "  AND b.fe_inivacgoz IS NOT NULL ";
                    strVal += "  AND b.fe_finvacgoz IS NOT NULL ";
                    strVal += "  AND b.fe_inivacgoz <= '" + strFecHas + "' ";
                    strVal += "  AND b.fe_finvacgoz >= '" + strFecDes + "' ";
                    strVal += "LIMIT 1";

                    System.out.println("Validar cruce vacaciones: " + strVal);

                    ResultSet rsVal = stm.executeQuery(strVal);

                    if (rsVal.next()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ya existe una solicitud activa para este trabajador con fechas que se cruzan."
                        );

                        rsVal.close();
                        stm.close();
                        return false;
                    }

                    rsVal.close();
                } 
    
                // Datos de reemplazo
                codtraree=txtCodTraRee.getText().toString().isEmpty() ? null : txtCodTraRee.getText().toString();
                
                // Estado de vacaciones adelantadas
                if (chkVacAdel.isSelected()) {
                    st_vacad="S";  
                } else {
                    st_vacad="N";
                }     
                
                // Días solicitados
                 if (btgrVacPag.isSelected())
                {
                    numdiasolpag = txtDiaSol.getText().trim();
                    numdiasolgoz = "NULL";
                }
                else if (btgrVacGoz.isSelected())
                {
                    numdiasolgoz = txtDiaSol.getText().trim();
                    numdiasolpag = "NULL";
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar si la solicitud es de vacaciones gozadas o pagadas.");
                    return false;
                } 
                  
                // Insertar en cabecera
                strIns="";
                strIns+=" INSERT INTO tbm_cabSolVac (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_tra, st_vacade,st_aut,co_trarem,tx_obs1,st_reg,fe_ing,co_usring) ";
                strIns+="values(1,"+objParSis.getCodigoLocal()+","+intTipDocSolVac+"," + codDoc +",now()," + codDoc +","+ txtCodTra.getText().toString()+",'"+ st_vacad +"','P'," + codtraree + ",'"+ txtObs.getText().toString() + "','A',Now()," +objParSis.getCodigoUsuario() ;
                strIns+=" );";
                System.out.println("Insert 1:"+strIns);
                stm.executeUpdate(strIns);
                
                strIns = "";

                // Insertar en detalle
                if (btgrVacGoz.isSelected()) {

                    strIns += "INSERT INTO tbm_detSolVac ";
                    strIns += "(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_numdiavacsolpag, ";
                    strIns += "fe_inivacgoz, fe_finvacgoz, ne_numdiavacsolgoz, fe_reivac, ne_perhasvac, ";
                    strIns += "ne_dianorvac, ne_diaadivac, ne_diaconsvac, ne_diapenvac) ";
                    strIns += "VALUES (";
                    strIns += objParSis.getCodigoEmpresa() + ",";
                    strIns += objParSis.getCodigoLocal() + ",";
                    strIns += intTipDocSolVac + ",";
                    strIns += codDoc + ",";
                    strIns += "1,";
                    strIns += numdiasolpag + ",";
                    strIns += "'" + objUti.formatearFecha(dtpFecDesSol.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                    strIns += "'" + objUti.formatearFecha(dtpFecHasSol.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                    strIns += numdiasolgoz + ",";
                    strIns += "'" + objUti.formatearFecha(dtpFecReiSol.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                    strIns += intPerHasVac + ",";
                    strIns += objResumenPeriodo.diasNormales + ",";
                    strIns += objResumenPeriodo.diasAdicionales + ",";
                    strIns += objResumenPeriodo.diasConsumidos + ",";
                    strIns += objResumenPeriodo.diasPendientes;
                    strIns += ");";

                    System.out.println("Insert detalle GOZ: " + strIns);
                }
                
                else if (btgrVacPag.isSelected())
                {
                    strIns += "INSERT INTO tbm_detSolVac ";
                    strIns += "(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ";
                    strIns += "ne_numdiavacsolpag, ne_numdiavacsolgoz, ne_perhasvac, ";
                    strIns += "ne_dianorvac, ne_diaadivac, ne_diaconsvac, ne_diapenvac) ";

                    strIns += "VALUES (";
                    strIns += objParSis.getCodigoEmpresa() + ",";
                    strIns += objParSis.getCodigoLocal() + ",";
                    strIns += intTipDocSolVac + ",";
                    strIns += codDoc + ",";
                    strIns += "1,";
                    strIns += numdiasolpag + ",";
                    strIns += numdiasolgoz + ",";
                    strIns += intPerHasVac + ",";
                    strIns += objResumenPeriodo.diasNormales + ",";
                    strIns += objResumenPeriodo.diasAdicionales + ",";
                    strIns += objResumenPeriodo.diasConsumidos + ",";
                    strIns += objResumenPeriodo.diasPendientes;

                    strIns += ");";

                    System.out.println("Insert detalle PAG: " + strIns);
                }
                 // System.out.println("strIns " + strIns);
                stm.executeUpdate(strIns);
                
                if (!insertarAutorizadoresVacaciones(con, codDoc, btgrVacPag.isSelected())) {
                    return false;
                }     
                
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
    
    private boolean insertarAutorizadoresVacaciones(Connection con, int codDoc, boolean blnVacPagada) {
        boolean blnRes = true;
        Statement stmLoc = null;
        String strSql = "";

        try {
            stmLoc = con.createStatement();

            /*
             * Si la solicitud es de vacaciones PAGADAS,
             * solo debe autorizar el usuario 156.
             */
            if (blnVacPagada) {
                strSql = "";
                strSql += "INSERT INTO tbm_autsolvac ";
                strSql += "(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut, tx_obsaut, fe_aut, co_usraut, tx_comaut) ";
                strSql += "SELECT ";
                strSql += "    cab.co_emp, ";
                strSql += "    cab.co_loc, ";
                strSql += "    cab.co_tipdoc, ";
                strSql += "    cab.co_doc, ";
                strSql += "    1 AS co_reg, ";
                strSql += "    'P' AS st_aut, ";
                strSql += "    NULL AS tx_obsaut, ";
                strSql += "    NULL AS fe_aut, ";
                strSql += "    156 AS co_usraut, ";
                strSql += "    NULL AS tx_comaut ";
                strSql += "FROM tbm_cabsolvac cab ";
                strSql += "INNER JOIN tbm_usr usr ";
                strSql += "    ON usr.co_usr = 156 ";
                strSql += "   AND usr.st_reg = 'A' ";
                strSql += "WHERE cab.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "  AND cab.co_loc = " + objParSis.getCodigoLocal() + " ";
                strSql += "  AND cab.co_tipdoc = " + intTipDocSolVac + " ";
                strSql += "  AND cab.co_doc = " + codDoc + " ";

                System.out.println("insertarAutorizadoresVacaciones PAGADA: " + strSql);
            }
            else {
                /*
                 * Solicitud de vacaciones GOZADAS:
                 * mantiene la lógica actual de autorizadores por departamento.
                 */
                strSql = "";
                strSql += "INSERT INTO tbm_autsolvac ";
                strSql += "(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut, tx_obsaut, fe_aut, co_usraut, tx_comaut) ";
                strSql += "SELECT ";
                strSql += "    cab.co_emp, ";
                strSql += "    cab.co_loc, ";
                strSql += "    cab.co_tipdoc, ";
                strSql += "    cab.co_doc, ";
                strSql += "    aut.co_reg, ";
                strSql += "    'P' AS st_aut, ";
                strSql += "    NULL AS tx_obsaut, ";
                strSql += "    NULL AS fe_aut, ";
                strSql += "    aut.co_usr AS co_usraut, ";
                strSql += "    NULL AS tx_comaut ";
                strSql += "FROM tbm_cabsolvac cab ";
                strSql += "INNER JOIN tbm_traemp traemp ";
                strSql += "    ON traemp.co_emp = cab.co_emp ";
                strSql += "   AND traemp.co_tra = cab.co_tra ";
                strSql += "INNER JOIN tbm_usrAutSolVacDep aut ";
                strSql += "    ON aut.co_dep = traemp.co_dep ";
                strSql += "   AND aut.st_reg = 'A' ";
                strSql += "INNER JOIN tbm_usr usr ";
                strSql += "    ON usr.co_usr = aut.co_usr ";
                strSql += "   AND usr.st_reg = 'A' ";
                strSql += "WHERE cab.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "  AND cab.co_loc = " + objParSis.getCodigoLocal() + " ";
                strSql += "  AND cab.co_tipdoc = " + intTipDocSolVac + " ";
                strSql += "  AND cab.co_doc = " + codDoc + " ";

                /*
                 * Regla especial para departamento Sistemas.
                 * Departamento 21:
                 * - Si el trabajador es Alarcón, co_tra = 920, autorizan 128 y 156.
                 * - Si el trabajador es co_tra = 61, autoriza solo 156.
                 * - Si es cualquier otro trabajador, autorizan 458 y 156.
                 *
                 * Para otros departamentos, mantiene la configuración normal.
                 */
                strSql += "  AND ( ";
                strSql += "        traemp.co_dep <> 21 ";
                strSql += "        OR ";
                strSql += "        ( ";
                strSql += "            traemp.co_dep = 21 ";
                strSql += "            AND ";
                strSql += "            ( ";
                strSql += "                (cab.co_tra = 920 AND aut.co_usr IN (128,156)) ";
                strSql += "                OR ";
                strSql += "                (cab.co_tra = 61 AND aut.co_usr IN (156)) ";
                strSql += "                OR ";
                strSql += "                (cab.co_tra NOT IN (920,61) AND aut.co_usr IN (458,156)) ";
                strSql += "            ) ";
                strSql += "        ) ";
                strSql += "      ) ";

                strSql += "ORDER BY aut.co_reg ";

                System.out.println("insertarAutorizadoresVacaciones GOZADA: " + strSql);
            }

            int intFilIns = stmLoc.executeUpdate(strSql);

            if (intFilIns <= 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "No existen usuarios asociados para autorizar vacaciones."
                );
                return false;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try {
                if (stmLoc != null) stmLoc.close();
            } catch (Exception e) {}
        }

        return blnRes;
}
    
    private int obtenerPeriodoVacaciones(Connection con, String strCodTra) throws Exception {
        int intPerHasVac = 0;

        Statement stmLoc = null;
        ResultSet rstLoc = null;

        try {
            stmLoc = con.createStatement();

            String strSql = "";
            strSql += "SELECT MIN(a.ne_anio) FILTER (";
            strSql += "    WHERE COALESCE(a.ne_diaper,0) > COALESCE(a.ne_diagoz,0) + COALESCE(a.ne_diapag,0)";
            strSql += ") AS ne_perhasvac ";
            strSql += "FROM tbm_cfgvactra a ";
            strSql += "WHERE a.co_tra = " + strCodTra;

            System.out.println("Periodo vacaciones: " + strSql);

            rstLoc = stmLoc.executeQuery(strSql);

            if (rstLoc.next()) {
                intPerHasVac = rstLoc.getInt("ne_perhasvac");
            }

        } finally {
            if (rstLoc != null) rstLoc.close();
            if (stmLoc != null) stmLoc.close();
        }

        return intPerHasVac;
    }
    
    private int[] obtenerPeriodoVacacionesDisponible(Connection con, String strCodTra) throws Exception {
        int[] arrRes = new int[3]; // [0]=periodo, [1]=dias disponibles, [2]=cantidad periodos disponibles

        Statement stmLoc = null;
        ResultSet rstLoc = null;

        try {
            stmLoc = con.createStatement();

            String strSql = "";

            strSql += "WITH per AS ( ";
            strSql += "    SELECT ";
            strSql += "        a.ne_anio, ";
            strSql += "        (COALESCE(a.ne_diaper,0) - COALESCE(a.ne_diagoz,0) - COALESCE(a.ne_diapag,0)) AS ne_diapen_real ";
            strSql += "    FROM tbm_cfgvactra a ";
            strSql += "    WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + " ";
            strSql += "      AND a.co_tra = " + strCodTra + " ";
            strSql += "      AND (COALESCE(a.ne_diaper,0) > COALESCE(a.ne_diagoz,0) + COALESCE(a.ne_diapag,0)) ";
            strSql += "), sol_pen AS ( ";
            strSql += "    SELECT ";
            strSql += "        b.ne_perhasvac AS ne_anio, ";
            strSql += "        SUM(COALESCE(b.ne_numdiavacsolgoz,0) + COALESCE(b.ne_numdiavacsolpag,0)) AS ne_dias_reservados ";
            strSql += "    FROM tbm_cabSolVac a ";
            strSql += "    INNER JOIN tbm_detSolVac b ";
            strSql += "       ON b.co_emp = a.co_emp ";
            strSql += "      AND b.co_loc = a.co_loc ";
            strSql += "      AND b.co_tipdoc = a.co_tipdoc ";
            strSql += "      AND b.co_doc = a.co_doc ";
            strSql += "    WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + " ";
            strSql += "      AND a.co_tra = " + strCodTra + " ";
            strSql += "      AND a.st_reg = 'A' ";
            strSql += "      AND a.st_aut = 'P' ";
            strSql += "      AND b.ne_perhasvac IS NOT NULL ";
            strSql += "    GROUP BY b.ne_perhasvac ";
            strSql += "), per_disp AS ( ";
            strSql += "    SELECT ";
            strSql += "        p.ne_anio, ";
            strSql += "        p.ne_diapen_real, ";
            strSql += "        COALESCE(s.ne_dias_reservados,0) AS ne_dias_reservados, ";
            strSql += "        (p.ne_diapen_real - COALESCE(s.ne_dias_reservados,0)) AS ne_diapen_disp ";
            strSql += "    FROM per p ";
            strSql += "    LEFT JOIN sol_pen s ";
            strSql += "      ON s.ne_anio = p.ne_anio ";
            strSql += "    WHERE (p.ne_diapen_real - COALESCE(s.ne_dias_reservados,0)) > 0 ";
            strSql += ") ";
            strSql += "SELECT ";
            strSql += "    ne_anio AS ne_perhasvac, ";
            strSql += "    ne_diapen_disp AS ne_diapen, ";
            strSql += "    (SELECT COUNT(*) FROM per_disp) AS ne_canperdisp ";
            strSql += "FROM per_disp ";
            strSql += "ORDER BY ne_anio ASC ";
            strSql += "LIMIT 1";

            System.out.println("Periodo vacaciones disponible con reservas: " + strSql);

            rstLoc = stmLoc.executeQuery(strSql);

            if (rstLoc.next()) {
                arrRes[0] = rstLoc.getInt("ne_perhasvac");
                arrRes[1] = rstLoc.getInt("ne_diapen");
                arrRes[2] = rstLoc.getInt("ne_canperdisp");
            }

        } finally {
            if (rstLoc != null) rstLoc.close();
            if (stmLoc != null) stmLoc.close();
        }

        return arrRes;
    }
    
    
    
    /*private boolean insertarSol() {
    String strIns;
    boolean blnRes = true;
    int codLocal = 0, codDoc = 0;
    String st_vacad = "N", codtraree = "null", numdiasolpag = "null", numdiasolgoz = "null";

    try {
        if (con != null) {
            stm = con.createStatement();
            codLocal = objParSis.getCodigoLocal();
            codDoc = getCodigodoc(con, codLocal);

            

            // Datos de reemplazo
            codtraree = txtCodTraRee.getText().trim().isEmpty() ? "null" : txtCodTraRee.getText();

            // Estado de vacaciones adelantadas
            if (chkVacAdel.isSelected()) {
                st_vacad = "S";
            }

            // Días solicitados
            if (btgrVacPag.isSelected()) {
                numdiasolpag = txtDiaSol.getText();
            }
            if (btgrVacGoz.isSelected()) {
                numdiasolgoz = txtDiaSol.getText();
            }

            // Insertar en cabecera
            strIns = "INSERT INTO tbm_cabSolVac (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_tra, st_vacade, st_aut, co_trarem, tx_obs1, st_reg, fe_ing, co_usring) "
                   + "VALUES (" + objParSis.getCodigoEmpresa() + "," + codLocal + "," + intTipDocSolVac + "," + codDoc
                   + ", now(), " + codDoc + ", " + txtCodTra.getText() + ", '" + st_vacad + "', 'P', "
                   + codtraree + ", '" + txtObs.getText().replace("'", "''") + "', 'A', now(), " + objParSis.getCodigoUsuario() + ");";
            stm.executeUpdate(strIns);

            // Insertar en detalle
            if (btgrVacGoz.isSelected()) {
                strIns = "INSERT INTO tbm_detSolVac (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_numdiavacsolpag, fe_inivacgoz, fe_finvacgoz, ne_numdiavacsolgoz, fe_reivac) "
                       + "VALUES (" + objParSis.getCodigoEmpresa() + ", " + codLocal + ", " + intTipDocSolVac + ", " + codDoc
                       + ", 1, " + numdiasolpag + ", '"
                       + objUti.formatearFecha(dtpFecDesSol.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "', '"
                       + objUti.formatearFecha(dtpFecHasSol.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "', "
                       + numdiasolgoz + ", '"
                       + objUti.formatearFecha(dtpFecReiSol.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "');";
            } else {
                strIns = "INSERT INTO tbm_detSolVac (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_numdiavacsolpag) "
                       + "VALUES (" + objParSis.getCodigoEmpresa() + ", " + codLocal + ", " + intTipDocSolVac + ", " + codDoc + ", 1, " + numdiasolpag + ");";
            }
            stm.executeUpdate(strIns);

            
            //mostrarMsgInf("La solicitud de vacaciones se ha registrado correctamente.");
            stm.close();
            stm = null;
        }
    } catch (java.sql.SQLException e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}*/
    
    
    private int obtenerDiasDisponibles(Connection conn, String codTra) {
        int diasDisponibles = 0;
        Statement stm = null;
        ResultSet rst = null;
        String strSQL = "";

        try {
            stm = conn.createStatement();
            strSQL = "SELECT COALESCE(SUM(ne_diaper - (ne_diagoz + ne_diapag)), 0) AS dias_disp " +
                     "FROM tbm_cfgvactra " +
                     "WHERE st_reg = 'A' AND co_emp = " + objParSis.getCodigoEmpresa() +
                     " AND co_tra = " + codTra;
            rst = stm.executeQuery(strSQL);
            if (rst.next()) {
                diasDisponibles = rst.getInt("dias_disp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rst != null) rst.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
        }

        return diasDisponibles;
    }
    
    private ResumenPeriodoVac obtenerResumenPeriodoVac(Connection con,int coTra,int anioPeriodo,int diasSolicitud) {
     
     ResumenPeriodoVac objRes = new ResumenPeriodoVac();

     Statement stmLoc = null;
     ResultSet rstLoc = null;

     try {
         stmLoc = con.createStatement();

         String strSql = "";

         strSql += "SELECT ";
         strSql += "    ne_diaper, ";
         strSql += "    ne_diagoz, ";
         strSql += "    ne_diapag ";
         strSql += "FROM tbm_cfgvactra ";
         strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
         strSql += "  AND co_tra = " + coTra + " ";
         strSql += "  AND ne_anio = " + anioPeriodo + " ";
         strSql += "  AND st_reg = 'A' ";
         strSql += "LIMIT 1";

         System.out.println("obtenerResumenPeriodoVac: " + strSql);

         rstLoc = stmLoc.executeQuery(strSql);

         if (rstLoc.next()) {
             int diasPermitidos = rstLoc.getInt("ne_diaper");
             int diasGozadosBD = rstLoc.getInt("ne_diagoz");
             int diasPagadosBD = rstLoc.getInt("ne_diapag");

             objRes.diasNormales = Math.min(diasPermitidos, 15);
             objRes.diasAdicionales = Math.max(0, diasPermitidos - 15);
             objRes.diasConsumidos = diasGozadosBD + diasPagadosBD;

             objRes.diasPendientes = diasPermitidos
                     - objRes.diasConsumidos
                     - diasSolicitud;

             if (objRes.diasPendientes < 0) {
                 objRes.diasPendientes = 0;
             }

             System.out.println("Periodo          : " + anioPeriodo);
             System.out.println("Dias permitidos  : " + diasPermitidos);
             System.out.println("Dias normales    : " + objRes.diasNormales);
             System.out.println("Dias adicionales : " + objRes.diasAdicionales);
             System.out.println("Dias gozados BD  : " + diasGozadosBD);
             System.out.println("Dias pagados BD  : " + diasPagadosBD);
             System.out.println("Dias consumidos  : " + objRes.diasConsumidos);
             System.out.println("Dias solicitud   : " + diasSolicitud);
             System.out.println("Dias pendientes  : " + objRes.diasPendientes);
         } else {
             throw new Exception(
                     "No se encontró configuración de vacaciones para el trabajador "
                     + coTra + " en el año " + anioPeriodo
             );
         }

     } catch (Exception e) {
         objUti.mostrarMsgErr_F1(this, e);
         return null;

     } finally {
         try {
             if (rstLoc != null) {
                 rstLoc.close();
             }

             if (stmLoc != null) {
                 stmLoc.close();
             }
         } catch (SQLException e) {
             objUti.mostrarMsgErr_F1(this, e);
         }
     }

     return objRes;
}
    

    

    public boolean consultar() {
        consultarReg();
        return true;
     }
    
    
    
    
    
    /**
     * LLamar a todas las solicitudes + los filtros 
     * @return 
     */
   private boolean consultarReg_2(){
        boolean blnRes=true;
        try{
            String strAuxCon="";
             conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_tipDoc, co_doc   ";
                strSQL+=" FROM tbm_cabSolVac ";
                strSQL+=" WHERE co_emp="+strCodEmpAut+" AND co_loc="+strCodLocAut+" AND ";
                strSQL+="       co_tipDoc="+strCodTipDocAut+" AND co_doc="+strCodDocAut+" ";
                strSQL+="ORDER BY co_doc" ;
               System.out.println("consultarReg 2: " + strSQL);    
               rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }
                else{
                    mostrarMsgCon("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * LLamar a todas las solicitudes + los filtros 
     * @return 
     */
   private boolean consultarReg(){
        boolean blnRes=true;
        try{
            String strAuxCon="";
             conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_tipDoc, co_doc   ";
                strSQL+=" FROM tbm_cabSolVac ";
                if(txtNumDoc.getText().length()>0 || txtCodTra.getText().length()>0){
                    strSQL+="WHERE 1=1 ";
                    if(txtNumDoc.getText().length()>0){
                        strSQL+=" AND ne_numDoc = "+txtNumDoc.getText()+" ";
                    }
                    if(txtCodTra.getText().length()>0){
                        strSQL+=" AND co_tra = "+txtCodTra.getText()+" ";
                    }
                }
                strSQL+="ORDER BY co_doc" ;
               System.out.println("consultarReg: " + strSQL);    
               rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }
                else{
                    mostrarMsgCon("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
   
   private String strSql="";
   private Date datFecDoc;                                     //Fecha del documento.
    
   
    private boolean cargarReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                java.sql.Statement stm=con.createStatement();
                strSql = "";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_tra, a3.tx_ide as ideSolicitante, (a3.tx_nom || ' ' || a3.tx_ape) as solicitante, a1.st_vacAde,  \n";
                strSql+="        a1.co_traRem, a4.tx_ide as ideReemplazo, (a4.tx_nom || ' ' || a4.tx_ape) as reemplazo, a1.tx_obs1, a1.st_reg,  \n";
                strSql+="        a2.co_reg, ne_numDiaVacSolPag, a2.fe_iniVacGoz, a2.fe_finVacGoz, a2.ne_numDiaVacSolGoz ,fe_reivac \n";
                strSql+=" FROM tbm_cabSolVac as a1 \n";
                strSql+=" INNER JOIN tbm_detSolVac as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDOc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSql+=" INNER JOIN tbm_tra as a3 ON (a1.co_tra=a3.co_tra) \n";
                strSql+=" LEFT JOIN tbm_tra as a4 ON (a1.co_traRem=a4.co_tra) \n";
                strSql+=" WHERE a1.co_emp="+rstCab.getInt("co_emp")+ " AND a1.co_loc="+rstCab.getInt("co_loc")+" AND ";
                strSql+="       a1.co_tipDOc="+rstCab.getInt("co_tipDoc")+" AND a1.co_doc="+rstCab.getInt("co_doc");
                 System.out.println("cargarReg " + strSql);
                java.sql.ResultSet rst=stm.executeQuery(strSql);
                if (rst.next())
                {
                    txtCodTra.setText(""+rst.getInt("co_tra"));
                    txtIdeTra.setText(rst.getString("ideSolicitante"));
                    txtNomTra.setText(rst.getString("solicitante"));
                    txtCodDoc.setText(rst.getString("co_doc"));
                    txtNumDoc.setText(rst.getString("ne_numDoc"));
                    
                    txtCodTraRee.setText(""+rst.getInt("co_traRem"));
                    txtIdeTraRee.setText(rst.getString("ideReemplazo"));
                    txtNomTraRee.setText(rst.getString("reemplazo"));
                    
                    txtObs.setText(rst.getString("tx_obs1"));
                    
                    datFecDoc=rst.getDate("fe_iniVacGoz");
                    dtpFecDesSol.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));     
                    
                    datFecDoc=rst.getDate("fe_finVacGoz");
                    dtpFecHasSol.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy")); 
                    
                    datFecDoc=rst.getDate("fe_reivac");
                    dtpFecReiSol.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy")); 
                    
                    if(rst.getString("st_vacAde").equals("S")){
                        chkVacAdel.setSelected(true);
                    }
                    else{
                        chkVacAdel.setSelected(false);
                    }
                    
                    int intAuxGozadas = (rst.getObject("ne_numDiaVacSolGoz") == null) ? 0 : rst.getInt("ne_numDiaVacSolGoz");
                    int intAuxPagadas = (rst.getObject("ne_numDiaVacSolPag") == null) ? 0 : rst.getInt("ne_numDiaVacSolPag");   
                    
                    if(intAuxGozadas>intAuxPagadas){
                        txtDiaSol.setText(intAuxGozadas+"");
                        btgrVacGoz.setSelected(true);
                    }
                    else{
                        txtDiaSol.setText(intAuxPagadas+"");
                        btgrVacPag.setSelected(true);
                    }
                    
                    cargarRegTabla(rst.getInt("co_tra")+"");
                     
                    String strAux=rst.getString("st_reg");
                    EstadoSoli=strAux;
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
//                    limpiarData();
                }
                // Falta cargar la tabla 
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                mostrarEstado();
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
//                blnCmb=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
      private void mostrarEstado(){
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if(EstadoSoli.equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            } 
            if(EstadoSoli.equals("A")){
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(true);
             } 
        }
 
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }     
        
    private boolean isRegPro()
    {
  //      System.out.println("isRegPro:::..");
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

        public boolean aceptar() {
            return true;
        }

    public boolean cancelar() {
            boolean blnRes=true;
            try{
       //         System.out.println("cancelar:::..");
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }    
                
       private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtNomTra.setText("");
            txtIdeTra.setText("");
            txtCodDoc.setText("");
            txtCodTra.setText("");
            txtObs.setText("");
            txtDiaSol.setText("");
            txtIdeTraRee.setText("");
            txtNomTraRee.setText("");
            txtCodTraRee.setText("");
            dtpFecIniCon.setText("");
            dtpFecDesSol.setText("");
            dtpFecHasSol.setText("");
            dtpFecReiSol.setText("");
            txtNumDoc.setText("");
            chkVacAdel.setSelected(false);
            //chkmosanisinsal.setSelected(false);                            
            zafTblModCon.removeAllRows();
                       

        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }

        public boolean beforeInsertar() {
 
            
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeAnular() {
  
            return true;
        }

   
        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            
            objTooBar.setEstado('w');          
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
     //       System.out.println("afterConsultar:::...");
            return true;
        }

      public boolean afterVistaPreliminar() {
     //       System.out.println("afterConsultar:::...");
            return true;
        }
        public boolean afterAnular() {
//            cargarReg();
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        
        public void clickModificar() {
         }

         public void clickEliminar() {
         }

         public void clickImprimir() {
         }

         public void clickVisPreliminar() {
         }

       
        public boolean modificar() {
            return true;
        }

         public boolean eliminar() {
             return true;
         }

         public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        
        public boolean beforeModificar() {
            return true;
        }

         public boolean beforeEliminar() {
            return true;
        }

         public boolean beforeImprimir() {
            return true;
        }

         public boolean beforeVistaPreliminar() {
            return true;
        }

         public boolean afterModificar() {
             return true;
         }

         public boolean afterEliminar() {
            return true;
        }

         public boolean afterImprimir() {
            return true;
        }
         
         
         @SuppressWarnings("serial")
        private class RenFilaSugerida extends javax.swing.table.DefaultTableCellRenderer {
            private final java.awt.Color VERDE = new java.awt.Color(198, 239, 206); // estilo Excel

            @Override
            public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);

                // Colores base de la tabla (por si quieres resetear)
                java.awt.Color bg = tbl.getBackground();
                java.awt.Color fg = tbl.getForeground();

                // Reset por defecto
                setBackground(bg);
                setForeground(fg);

                // *** Tu regla de pintado en verde ***
                int cPen = INT_CON_DIAS_VACPEN;   // índice de "Días Vac. Pendientes"
                int cAnio = INT_CON_AÑOS;         // índice de "Año"
                try {
                    Object oPend = tbl.getValueAt(row, cPen);
                    int pend = (oPend == null || oPend.toString().trim().isEmpty()) ? 0 : Integer.parseInt(oPend.toString());

                    boolean todosPreviosCero = true;
                    for (int r = 0; r < row; r++) {
                        Object v = tbl.getValueAt(r, cPen);
                        int p = (v == null || v.toString().trim().isEmpty()) ? 0 : Integer.parseInt(v.toString());
                        if (p > 0) { todosPreviosCero = false; break; }
                    }
                    if (pend > 0 && todosPreviosCero) {
                        setBackground(VERDE);
                    }
                } catch (Exception ignore) { /* nada */ }

                // *** IGNORAR selección: que no pinte azul ***
                // Si la celda está seleccionada, reponemos los colores calculados (no el azul por defecto)
                if (isSelected) {
                    // Mantén el background ya decidido (verde o base)
                    setForeground(fg);
                    // Opcional: quita borde de foco para que no se vea el recuadro
                    setBorder(noFocusBorder);
                }

                return this;
            }
            
        }
         
         private class ResumenPeriodoVac {
            int diasNormales;
            int diasAdicionales;
            int diasConsumidos;
            int diasPendientes;
        }
         
        //cargar proximo co_doc y num_doc a usar.
        private void actualizarNumeradores() {
            Connection con = null;
            PreparedStatement p1 = null, p2 = null;
            ResultSet r1 = null, r2 = null;

            try {
                con = DriverManager.getConnection(
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos()
                );

                final int coEmp = objParSis.getCodigoEmpresa();

                // Siguiente CÓDIGO DE DOCUMENTO (co_doc) desde tbm_cabsolvac
                String sqlCoDoc = "SELECT COALESCE(MAX(co_doc)+1, 1) AS co_doc " +
                                  "FROM tbm_cabsolvac WHERE co_emp = ?";
                System.out.println("co_doc: "+sqlCoDoc);
                p1 = con.prepareStatement(sqlCoDoc);
                p1.setInt(1, coEmp);
                r1 = p1.executeQuery();
                if (r1.next()) {
                    String nextCoDoc = r1.getString("co_doc");
                    txtCodDoc.setText(nextCoDoc);       // <-- tu JTextField de “Código del Documento”
                }

                // Siguiente NÚMERO DE DOCUMENTO (num_doc) desde tbm_detsolvac
                String sqlNumDoc = "SELECT COALESCE(MAX(co_doc)+1, 1) AS num_doc " +
                                   "FROM tbm_detsolvac WHERE co_emp = ?";
                System.out.println("num_doc: "+sqlNumDoc);
                p2 = con.prepareStatement(sqlNumDoc);
                p2.setInt(1, coEmp);
                r2 = p2.executeQuery();
                if (r2.next()) {
                    String nextNumDoc = r2.getString("num_doc");
                    txtNumDoc.setText(nextNumDoc);      // <-- tu JTextField de “Número de Documento”
                }

                // Opcional: bloquear edición manual
                txtCodDoc.setEditable(false);
                txtNumDoc.setEditable(false);

            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            } finally {
                try { if (r2!=null) r2.close(); } catch(Exception ignore){}
                try { if (r1!=null) r1.close(); } catch(Exception ignore){}
                try { if (p2!=null) p2.close(); } catch(Exception ignore){}
                try { if (p1!=null) p1.close(); } catch(Exception ignore){}
                try { if (con!=null) con.close(); } catch(Exception ignore){}
            }
        }


         
         
    }
    
    
    

    
}