/*
 * ZafCon15.java
 * "Movimientos de Mayor de Cuentas"
 * Created on 24 de enero de 2006, 11:06
 */
package Contabilidad.ZafCon31;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
//import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.sql.*;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

/**
 * @author Dennis Betancourt
 */
public class ZafCon31 extends javax.swing.JInternalFrame 
{
    //Constantes: JTable
    final int INT_TBL_LIN = 0;
    final int INT_TBL_ANO = 1;
    final int INT_TBL_COD_CTA = 2;
    final int INT_TBL_NUM_CTA = 3;
    final int INT_TBL_NOM_CTA = 4;
    //Variables
    private ZafParSis objParSis;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;       //ToolTipText en TableHeader.
    private ZafVenCon vcoCta;
    private ZafTblOrd objTblOrd;             //JTable de ordenamiento.
    private ZafThreadGUI objThrGUI;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;
    private String strDesCorCta, strDesLarCta, strAnoCbo;
    private String strSQL, strAux;
    private final String strVer = " v0.1 ";
    
    public ZafCon31(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }      
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Título de la ventana
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);            
            //Inicializar objetos.
            objUti = new ZafUtil();
            cargarAnioCreados();
            //Configurar objetos.
            configurarTblDat();
            configurarVenConCta();
            
            txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCta.setBackground(objParSis.getColorCamposObligatorios());
            setEnabledCta(true);            

            txtCodCta.setVisible(false);
            txtCodCta.setEditable(false);
            txtCodCta.setEnabled(false);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_ANO, "Año");
            vecCab.add(INT_TBL_COD_CTA, "Cód.Cta.");
            vecCab.add(INT_TBL_NUM_CTA, "Num.Cta.");
            vecCab.add(INT_TBL_NOM_CTA, "Nom.Cta.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_LIN);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_ANO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_COD_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setPreferredWidth(110);                        
            tcmAux.getColumn(INT_TBL_NOM_CTA).setPreferredWidth(300);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable TblDat: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_COD_CTA, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);            

            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_ANO:
                    strMsg = "Año";
                    break;
                case INT_TBL_COD_CTA:
                    strMsg = "Código de cuenta";
                    break;
                case INT_TBL_NUM_CTA:
                    strMsg = "Número de cuenta";
                    break;
                case INT_TBL_NOM_CTA:
                    strMsg = "Nombre de cuenta";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada
     * para mostrar las "Cuentas".
     */
    private boolean configurarVenConCta() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //arlCam.add("a1.tx_niv1");
            arlCam.add("a2.tx_niv1");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Nivel");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            arlAncCol.add("40");
            //Armar la sentencia SQL.
            strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_niv1";
                strSQL+=" FROM tbm_salCta AS a1";
                strSQL+=" LEFT JOIN tbm_plaCta as a2 on a1.co_emp = a2.co_emp and a1.co_cta = a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.tx_tipCta='D'";
                strSQL+=" AND a1.co_per=" + cboAno.getSelectedItem() + "01";
                strSQL+=" ORDER BY a1.tx_codCta";
            } else {
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_niv1";
                strSQL+=" FROM tbm_salCta AS a1";
                strSQL+=" LEFT JOIN tbm_plaCta as a2 on a1.co_emp = a2.co_emp and a1.co_cta = a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.tx_tipCta='D'";
                strSQL+=" AND a1.co_per=" + cboAno.getSelectedItem() + "01";
                strSQL+=" GROUP BY a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_niv1";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConCta(int intTipBus) {
        boolean blnRes = true;
        
        if (!cboAno.getSelectedItem().toString().equalsIgnoreCase(strAnoCbo))
        {
           configurarVenConCta();
           strAnoCbo = cboAno.getSelectedItem().toString();
        }
        
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText())) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        objTblMod.removeAllRows();
                    } else {
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            objTblMod.removeAllRows();
                        } else {
                            txtDesCorCta.setText(strDesCorCta);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripcián larga".
                    if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText())) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        objTblMod.removeAllRows();
                    } else {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            objTblMod.removeAllRows();
                        } else {
                            txtDesLarCta.setText(strDesLarCta);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        txtCodCta = new javax.swing.JTextField();
        lblCta = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        cboAno = new javax.swing.JComboBox();
        panDet = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        spnTblRep = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 20));
        lblTit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panCab.setLayout(null);

        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        panCab.add(txtDesCorCta);
        txtDesCorCta.setBounds(80, 31, 130, 20);

        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        panCab.add(txtDesLarCta);
        txtDesLarCta.setBounds(210, 31, 350, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panCab.add(butCta);
        butCta.setBounds(560, 31, 20, 20);

        txtCodCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaActionPerformed(evt);
            }
        });
        panCab.add(txtCodCta);
        txtCodCta.setBounds(60, 31, 20, 20);

        lblCta.setText("Cuenta:");
        panCab.add(lblCta);
        lblCta.setBounds(10, 31, 60, 20);

        lblAno.setText("Año:");
        panCab.add(lblAno);
        lblAno.setBounds(10, 10, 50, 20);
        panCab.add(cboAno);
        cboAno.setBounds(80, 10, 64, 20);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setPreferredSize(new java.awt.Dimension(100, 250));
        panDet.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(452, 320));
        panDat.setLayout(new java.awt.BorderLayout());

        spnTblRep.setPreferredSize(new java.awt.Dimension(452, 266));

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblRep.setViewportView(tblDat);

        panDat.add(spnTblRep, java.awt.BorderLayout.CENTER);

        panDet.add(panDat, java.awt.BorderLayout.CENTER);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setMinimumSize(new java.awt.Dimension(50, 33));
        panBar.setPreferredSize(new java.awt.Dimension(0, 50));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(100, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(100, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                if (con != null) {
                    con.close();
                    con = null;
                }
                dispose();
            }
        } 
        catch (java.sql.SQLException e) {
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) 
        {
            if (isCamVal()) 
            {
                blnCon = true;
                if (objThrGUI == null)
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
        } 
        else 
        {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

 
    /**
     * Cerrar la aplicacion.
     */
    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar el programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
    txtDesCorCta.transferFocus();
    setPuntosCta();
}//GEN-LAST:event_txtDesCorCtaActionPerformed

private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
    strDesCorCta = txtDesCorCta.getText();
    txtDesCorCta.selectAll();
}//GEN-LAST:event_txtDesCorCtaFocusGained

private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
        if (txtDesCorCta.getText().equals("")) {
            txtCodCta.setText("");
            txtDesLarCta.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConCta(1);
        }
    } else {
        txtDesCorCta.setText(strDesCorCta);
    }

}//GEN-LAST:event_txtDesCorCtaFocusLost

private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
    txtDesLarCta.transferFocus();
}//GEN-LAST:event_txtDesLarCtaActionPerformed

private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
    strDesLarCta = txtDesLarCta.getText();
    txtDesLarCta.selectAll();
}//GEN-LAST:event_txtDesLarCtaFocusGained

private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta)) {
        if (txtDesLarCta.getText().equals("")) {
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConCta(2);
        }
    } else {
        txtDesLarCta.setText(strDesLarCta);
    }
}//GEN-LAST:event_txtDesLarCtaFocusLost

    private void txtCodCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodCtaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCta;
    private javax.swing.JComboBox cboAno;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblCta;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnTblRep;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesLarCta;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema zafiro";
        obj.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMsgErr(String strMsg) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema zafiro";
        obj.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void setEnabledCta(boolean blnEstado) {
        txtCodCta.setEnabled(blnEstado);
        txtDesCorCta.setEnabled(blnEstado);
        txtDesLarCta.setEnabled(blnEstado);
        butCta.setVisible(blnEstado);
    }   
    
    private class ZafThreadGUI extends Thread {
        public void run() {
            if (!cargarDetReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            objThrGUI = null;
        }
    }    
    
    private boolean isCamVal() 
    {      
        if (txtCodCta.getText().equals("")) 
        {
            mostrarMsgInf("Ingrese la cuenta a consultar.");
            return false;
        }
        return true;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros. <BR>false: En el caso
     * contrario.
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        
        try 
        {   butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con != null)
            {   stm = con.createStatement();
                //Obtener el número total de registros.
                strSQL =  "SELECT co_emp, substring(cast(co_per as varchar), 1, 4) as tx_ano, co_cta, tx_codCta, tx_desLar as tx_nomCta ";
                strSQL += "FROM tbm_salcta ";
                strSQL += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " and co_cta = " + txtCodCta.getText();
                strSQL += "   and cast(co_per as varchar) in ( select distinct substring(cast(co_per as varchar), 1, 4) || '01' from tbm_salcta ";
                strSQL += "   where co_emp = " + objParSis.getCodigoEmpresa() + " and co_cta = " + txtCodCta.getText() + " order by 1 ) ";
                strSQL += "ORDER BY tx_ano";
                //System.out.println("cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                int i = 0;
                while (rst.next()) 
                {   if (blnCon) 
                    {   vecReg = new Vector();
                        vecReg.add(INT_TBL_LIN, "");
                        vecReg.add(INT_TBL_ANO, rst.getString("tx_ano"));
                        vecReg.add(INT_TBL_COD_CTA, rst.getString("co_cta"));
                        vecReg.add(INT_TBL_NUM_CTA, rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_NOM_CTA, rst.getString("tx_nomCta"));
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else 
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst = null;
                stm = null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (tblDat.getRowCount() == tblDat.getRowCount()) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                }
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount() > 0) 
                {
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }

                con.close();
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void setPuntosCta() {
        strAux = txtDesCorCta.getText();
        String strCodCtaOri = strAux;
        String strCodCtaDes = "";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta = strCodCtaOri.length();
        int intLonCodCtaMen = intLonCodCta - 1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig = intLonCodCta - 2;
        if (strCodCtaOri.length() <= 1) {
            return;
        } else {
            chrCtaOri = strCodCtaOri.charAt(1);
            if (chrCtaOri != '.') {
                for (int i = 0; i < strCodCtaOri.length(); i++) {
                    if (i == 0) {
                        strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                        strCodCtaDes = strCodCtaDes + ".";
                    } else {
                        if ((strCodCtaOri.length() % 2) == 0) {
                            if (((i % 2) == 0) && (i < intLonCodCtaMenTreDig)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                strCodCtaDes = strCodCtaDes + ".";
                            }
                            if (((i % 2) == 0) && (i == intLonCodCtaMenTreDig)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                            } else {
                                if ((i % 2) != 0) {
                                    strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                }
                            }
                        } else {
                            if (((i % 2) == 0) && (i != intLonCodCtaMen)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                strCodCtaDes = strCodCtaDes + ".";
                            }
                            if (((i % 2) == 0) && (i == intLonCodCtaMen)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                            } else {
                                if ((i % 2) != 0) {
                                    strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                }
                            }
                        }
                    }
                }
                txtDesCorCta.setText(strCodCtaDes);
            }
        }
    }    
    
    private boolean cargarAnioCreados()
    {
        boolean blnRes=true;
        int intUltAni=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani";
                strSQL+=" FROM tbm_anicresis";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" ORDER BY ne_ani";
                
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    cboAno.addItem("" + rst.getString("ne_ani"));
                    intUltAni++;
                }
                cboAno.setSelectedIndex(intUltAni);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion cargarAnioCreados()
}