package RecursosHumanos.ZafRecHum86;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Autorizaciones de horas suplementarias/extraordinarias.
 *
 * @author Roberto Flores
 */
public class ZafRecHum86 extends javax.swing.JInternalFrame
{
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_COEMP = 1;
    final int INT_TBL_DAT_EMP = 2;
    final int INT_TBL_DAT_CODLOC = 3;
    final int INT_TBL_DAT_CODTIPDOC = 4;
    final int INT_TBL_DAT_DESCORTIPDOC = 5;
    final int INT_TBL_DAT_DESLARTIPDOC = 6;
    final int INT_TBL_DAT_CODDOC = 7;  
    final int INT_TBL_DAT_NUMDOC = 8;
    final int INT_TBL_DAT_FECDOC = 9; 
    final int INT_TBL_DAT_EMPLE_SOLI = 10;
    final int INT_TBL_DAT_BUTANESOLHSE = 11;
    final int INT_TBL_DAT_CHKAUTPEN = 12;
    final int INT_TBL_DAT_CHKAUTPAR = 13;
    final int INT_TBL_DAT_CHKAUTTOT = 14;
    
    final int INT_TBL_DAT_BUTANE2 = 15;
    final int INT_TBL_DAT_CO_TRA = 16;
    
    final int INT_TBL_DAT_TIP_SOL = 17;
    
    final int INT_TBL_DAT_SOL_ADE = 18;
    final int INT_TBL_DAT_SOL_CANT_DIA_SOL = 19;
    final int INT_TBL_DAT_FECDESDE_SOL = 20;
    final int INT_TBL_DAT_FECHASTA_SOL = 21;
    
    final int INT_TBL_DAT_BUTANEREP = 22;
    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;
    private int intCoReg;   
    private String strCodTra = "";
    private String strCodDep = "", strDesLarDep = "", strNomDep = "";

    private String strFecha;

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                    //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru, objTblCelRenLblDocRel, objTblCelRenLblNum, objTblCelRenLblCol;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;
    private ZafSelFec objSelFec;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafPerUsr objPerUsr;

    private ZafVenCon vcoDep;                                   //Ventana de consulta "Departamentos".
    
    private ZafRecHum86_02 ZafRecHum86_02;
    public int intPosAct;

    private String strVersion = "v1.2";
    private int intTipDocSolVac = 322;

    public ZafRecHum86(ZafParSis obj)
    {
        try 
        {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
 
            if (!configurarFrm()) {
                exitForm();
            }
            agregarColTblDat();
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panFecCor = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtDesLarDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        chkMosDocTotAut = new javax.swing.JCheckBox();
        chkMosDocPenAut = new javax.swing.JCheckBox();
        chkMosDocParAut = new javax.swing.JCheckBox();
        chkMosDocDen = new javax.swing.JCheckBox();
        jPanelReport = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panFecCor.setPreferredSize(new java.awt.Dimension(100, 100));
        panFecCor.setLayout(null);
        jPanel1.add(panFecCor, java.awt.BorderLayout.NORTH);

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(0, 200));
        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 4, 400, 14);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setSelected(true);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 20, 400, 14);

        lblLoc.setText("Departamento:");
        lblLoc.setToolTipText("Proveedor");
        panFil.add(lblLoc);
        lblLoc.setBounds(14, 48, 100, 20);

        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(126, 49, 56, 20);

        txtDesLarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarDepActionPerformed(evt);
            }
        });
        txtDesLarDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusLost(evt);
            }
        });
        panFil.add(txtDesLarDep);
        txtDesLarDep.setBounds(182, 49, 264, 20);

        butDep.setText("...");
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(446, 49, 20, 20);

        chkMosDocTotAut.setText("Mostrar los documentos que están autorizados totalmente");
        chkMosDocTotAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocTotAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocTotAut);
        chkMosDocTotAut.setBounds(10, 140, 440, 20);

        chkMosDocPenAut.setSelected(true);
        chkMosDocPenAut.setText("Mostrar los documentos que están pendientes de autorizar");
        chkMosDocPenAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocPenAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocPenAut);
        chkMosDocPenAut.setBounds(10, 100, 430, 20);

        chkMosDocParAut.setSelected(true);
        chkMosDocParAut.setText("Mostrar los documentos que están autorizados parcialmente");
        chkMosDocParAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocParAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocParAut);
        chkMosDocParAut.setBounds(10, 120, 440, 20);

        chkMosDocDen.setText("Mostrar los documentos que están denegados");
        chkMosDocDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocDenActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocDen);
        chkMosDocDen.setBounds(10, 160, 440, 20);

        jPanel1.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", jPanel1);

        jPanelReport.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

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
        spnDat.setViewportView(tblDat);

        jPanelReport.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanelReport);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(385, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
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

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents


    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private boolean mensajeConfir(String strMsg2)
    {
        boolean blnRes=false;
        String strTit="Mensaje del sistema Zafiro";
        
        if((JOptionPane.showConfirmDialog(this,strMsg2,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ))
            blnRes=true;  // SI
        else
            blnRes=false;  // NO 
        return blnRes;
    }    
    

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */


    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
     }//GEN-LAST:event_formInternalFrameOpened

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
            txtCodDep.setText("");
            txtDesLarDep.setText("");            
            chkMosDocPenAut.setSelected(true);
            chkMosDocParAut.setSelected(true);
            chkMosDocTotAut.setSelected(true);
        }
        else
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            optTod.setSelected(false);
            chkMosDocTotAut.setSelected(false);
        }
        else
        {
            optTod.setSelected(true);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtDesLarDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo, String strBusqueda, int tipo) 
    {
        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtDesLarDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtDesLarDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtDesLarDep.setText(strDesLarDep);
            }
        }
    }
    
    
    private void txtDesLarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarDepActionPerformed
        txtDesLarDep.transferFocus();
    }//GEN-LAST:event_txtDesLarDepActionPerformed

    private void txtDesLarDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusGained
        strDesLarDep = txtDesLarDep.getText();
        txtDesLarDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtDesLarDepFocusGained

    private void txtDesLarDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusLost
        if (txtDesLarDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarDep.getText().equalsIgnoreCase(strNomDep)) {
                if (txtDesLarDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtDesLarDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtDesLarDep.setText(strNomDep);
            }
        }
    }//GEN-LAST:event_txtDesLarDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        mostrarVenConDep(0);
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_butDepActionPerformed

    private void chkMosDocTotAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocTotAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocTotAutActionPerformed

    private void chkMosDocPenAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocPenAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocPenAutActionPerformed

    private void chkMosDocParAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocParAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocParAutActionPerformed

    private void chkMosDocDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocDenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosDocDenActionPerformed

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) 
    {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesLarDep.setText(strNomDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    if (vcoDep.buscar("a1.tx_desLar", txtDesLarDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesLarDep.setText(strNomDep);
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
    
    
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
     private void habilitarChkBox(boolean blnOptFil, boolean blnOptTod) {
        optFil.setSelected(blnOptFil);
        optTod.setSelected(blnOptTod);
    }
    

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JCheckBox chkMosDocDen;
    private javax.swing.JCheckBox chkMosDocParAut;
    private javax.swing.JCheckBox chkMosDocPenAut;
    private javax.swing.JCheckBox chkMosDocTotAut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelReport;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFecCor;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtDesLarDep;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) 
    {
      //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() 
    {
    boolean blnRes = true;

    try 
    {
        // Listado de campos.
        ArrayList arlCam = new ArrayList();
        arlCam.add("a1.co_dep");
        arlCam.add("a1.tx_desCor");
        arlCam.add("a1.tx_desLar");
        arlCam.add("a1.st_reg");

        // Alias de los campos.
        ArrayList arlAli = new ArrayList();
        arlAli.add("Código");
        arlAli.add("Descripción corta");
        arlAli.add("Descripción larga");
        arlAli.add("Estado");

        // Ancho de las columnas.
        ArrayList arlAncCol = new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("110");
        arlAncCol.add("220");
        arlAncCol.add("40");

        String strSQL = "";

        if (objParSis.getCodigoUsuario() == 1 
                || objParSis.getCodigoUsuario() == 317 
                || objParSis.getCodigoUsuario() == 156) 
        {
            strSQL = "";
            strSQL += "SELECT ";
            strSQL += "    a1.co_dep, ";
            strSQL += "    a1.tx_desCor, ";
            strSQL += "    a1.tx_desLar, ";
            strSQL += "    a1.st_reg ";
            strSQL += "FROM tbm_dep AS a1 ";
            strSQL += "WHERE a1.st_reg IN ('P','A') ";
            strSQL += "ORDER BY a1.co_dep";
        } 
        else 
        {
            strSQL = "";
            strSQL += "SELECT DISTINCT ";
            strSQL += "    a1.co_dep, ";
            strSQL += "    a1.tx_desCor, ";
            strSQL += "    a1.tx_desLar, ";
            strSQL += "    a1.st_reg ";
            strSQL += "FROM tbm_dep AS a1 ";
            strSQL += "INNER JOIN tbr_depprgusr AS dpu ";
            strSQL += "    ON dpu.co_dep = a1.co_dep ";
            strSQL += "   AND dpu.co_usr = " + objParSis.getCodigoUsuario() + " ";
            strSQL += "   AND dpu.co_mnu = " + objParSis.getCodigoMenu() + " ";
            strSQL += "WHERE a1.st_reg = 'A' ";
            strSQL += "  AND COALESCE(dpu.st_reg, 'A') = 'A' ";
            strSQL += "ORDER BY a1.co_dep";
        }

        // Ocultar columnas.
        int intColOcu[] = new int[1];
        intColOcu[0] = 4; // Oculta Estado.

        vcoDep = new ZafVenCon(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado Departamentos",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol,
                intColOcu
        );

        // Configurar columnas.
        vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        arlCam = null;
        arlAli = null;
        arlAncCol = null;
        intColOcu = null;
    } 
    catch (Exception e) 
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}
    
    

    private class ZafThreadGUI extends Thread 
    {
        public void run() {
            if (!cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    
    
    
    
 
        private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();
            
            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 1: Solicitudes de Vacaciones");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COEMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_EMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODLOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESCORTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESLARTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODDOC));
            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECDOC));

            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_EMPLE_SOLI)); 
            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANESOLHSE));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 2: Autorizacion");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPAR));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTTOT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANE2));     
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CO_TRA));   
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_TIP_SOL));            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_SOL_ADE));   
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_SOL_CANT_DIA_SOL)); 
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANEREP));
            
            
 
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                     
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {

            Configura_ventana_consulta();
            strAux = objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);
            
            chkMosDocDen.setVisible(false);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(15);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_EMP, "Empresa");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC, "Cód.Doc.");            
          
            
            vecCab.add(INT_TBL_DAT_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc.");
            
            vecCab.add(INT_TBL_DAT_EMPLE_SOLI, "Empleado");
            
            vecCab.add(INT_TBL_DAT_BUTANESOLHSE, "Muestra la Sol.Vac");

            vecCab.add(INT_TBL_DAT_CHKAUTPEN, "Pendiente");
            vecCab.add(INT_TBL_DAT_CHKAUTPAR, "Parcial");
            vecCab.add(INT_TBL_DAT_CHKAUTTOT, "Aprobado");
            
            vecCab.add(INT_TBL_DAT_BUTANE2, "Muestra la Aut.Sol.Vac");     
            vecCab.add(INT_TBL_DAT_CO_TRA, "Cod.Trab");
            vecCab.add(INT_TBL_DAT_TIP_SOL, "Tip.Sol");
            vecCab.add(INT_TBL_DAT_SOL_ADE, "Sol.Ade");
            vecCab.add(INT_TBL_DAT_SOL_CANT_DIA_SOL, "Can.Dias.Sol");
            vecCab.add(INT_TBL_DAT_FECDESDE_SOL, "fec.Desde");
            vecCab.add(INT_TBL_DAT_FECHASTA_SOL, "fec.Hasta");
            vecCab.add(INT_TBL_DAT_BUTANEREP, "Reporte");
            
            

  
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Tama�o de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(90);
            
            tcmAux.getColumn(INT_TBL_DAT_EMPLE_SOLI).setPreferredWidth(250);
            
            
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTPEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTPAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTTOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setPreferredWidth(70);
            
            
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panFecCor.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CO_TRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_SOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_ADE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_CANT_DIA_SOL, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FECDESDE_SOL, tblDat);   
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FECHASTA_SOL, tblDat);


            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDat);

            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDat);
 
            //*****************************************************************************
      
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCru = new ZafTblCelRenLbl();
            objTblCelRenLblCru.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCru.setTipoFormato(objTblCelRenLblCru.INT_FOR_NUM);
            objTblCelRenLblCru.setFormatoNumerico("####", false, true);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setCellRenderer(objTblCelRenLblCru);
            objTblCelRenLblCru = null;

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTANESOLHSE);
            vecAux.add("" + INT_TBL_DAT_BUTANE2);
            vecAux.add("" + INT_TBL_DAT_BUTANEREP);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            if ((objPerUsr.isOpcionEnabled(3065))) {
                butCon.setEnabled(true);
            }
            if ((objPerUsr.isOpcionEnabled(3066))) {
                butCer.setEnabled(true);
            }

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setCellRenderer(zafTblDocCelRenBut);
            
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(zafTblDocCelRenBut);
            

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTANESOLHSE:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTANESOLHSE:
                                llamarPantSolVac(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()   
                                );
                                break;
                            case INT_TBL_DAT_BUTANEREP:                                
                                llamarReporte(
                                    getValTbl(intFilSel, INT_TBL_DAT_EMPLE_SOLI),
                                    getValTbl(intFilSel, INT_TBL_DAT_SOL_CANT_DIA_SOL),
                                    getValTbl(intFilSel, INT_TBL_DAT_CODDOC),
                                    getValTbl(intFilSel, INT_TBL_DAT_CO_TRA),
                                    getValTbl(intFilSel, INT_TBL_DAT_FECDESDE_SOL),
                                    getValTbl(intFilSel, INT_TBL_DAT_FECHASTA_SOL)
                                );
                             
                                break;
                        }
                    }
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(objTblCelRenBut);
            

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPAR).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPAR).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPAR)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPAR)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut = null;

            ZafRecHum86_02 = new ZafRecHum86_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg = new ZafTblCelEdiButDlg(ZafRecHum86_02);
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                int intCol;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();

                    if (intFilSel != -1) 
                    {
                        intPosAct = intFilSel;

                        String strEstDoc = "";

                        if (Boolean.TRUE.equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTPEN))) {
                            strEstDoc = "P";
                        }

                        if (Boolean.TRUE.equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTPAR))) {
                            strEstDoc = "A";
                        }

                        if (Boolean.TRUE.equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTTOT))) {
                            strEstDoc = "T";
                        }

                        String strCoTra      = getValTbl(intFilSel, INT_TBL_DAT_CO_TRA);
                        String strTipSol     = getValTbl(intFilSel, INT_TBL_DAT_TIP_SOL);
                        String strSolAde     = getValTbl(intFilSel, INT_TBL_DAT_SOL_ADE);
                        String strCantDiaSol = getValTbl(intFilSel, INT_TBL_DAT_SOL_CANT_DIA_SOL);
                        String strFecDesde   = getValTbl(intFilSel, INT_TBL_DAT_FECDESDE_SOL);
                        String strFecHasta   = getValTbl(intFilSel, INT_TBL_DAT_FECHASTA_SOL);

                        System.out.println("Esto envio: " 
                                + objParSis
                                + ZafRecHum86.this
                                + Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_COEMP).toString())
                                + Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString())
                                + Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString())
                                + Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString())
                                + Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString())
                                + tblDat.getValueAt(intFilSel, INT_TBL_DAT_FECDOC).toString()
                                + strEstDoc
                                + strCoTra
                                + strTipSol
                                + strSolAde
                                + strCantDiaSol
                                + strFecDesde
                                + strFecHasta
                        );

                        ZafRecHum86_02.setParDlg(
                                objParSis,
                                ZafRecHum86.this,
                                Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_COEMP).toString()),
                                Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()),
                                Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()),
                                Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()),
                                Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()),
                                tblDat.getValueAt(intFilSel, INT_TBL_DAT_FECDOC).toString(),
                                strEstDoc,
                                strCoTra,
                                strTipSol,
                                strSolAde,
                                strCantDiaSol,
                                strFecDesde,
                                strFecHasta
                        );

                        ZafRecHum86_02.cargarReg();
                    }
}

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void llamarPantSolVac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ) {
        RecursosHumanos.ZafRecHum85.ZafRecHum85 obj1 = new RecursosHumanos.ZafRecHum85.ZafRecHum85(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
    
     private void llamarReporte(String strEmple, String strDias, String strCodDoc, String strCodTra, String strFecDes, String strFecHas) 
    {
        if (strEmple == null) strEmple = "";
        if (strDias == null) strDias = "";
        if (strCodDoc == null) strCodDoc = "";
        if (strCodTra == null) strCodTra = "";
        if (strFecDes == null) strFecDes = "";
        if (strFecHas == null) strFecHas = "";

        ZafRecHum86_03 obj1 = new ZafRecHum86_03(
                objParSis, 
                strCodDoc, 
                strEmple,
                strDias, 
                strCodTra, 
                strFecDes, 
                strFecHas
        );

        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
    
     private String getValTbl(int fila, int columna)
    {
        Object objVal = objTblMod.getValueAt(fila, columna);
        return (objVal == null) ? "" : objVal.toString();
    }
     
     
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_COEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_EMP:
                    strMsg = "Empresa";
                    break;
                case INT_TBL_DAT_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg = "Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOC:
                    strMsg = "Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOC:
                    strMsg = "Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg = "Código del documento";
                    break;
                                        
                case INT_TBL_DAT_NUMDOC:
                    strMsg = "Número de Solicitud";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                    
                case INT_TBL_DAT_EMPLE_SOLI:
                    strMsg = "Fecha del documento";
                    break;
                                       
                case INT_TBL_DAT_BUTANESOLHSE:
                    strMsg = "Muestra la solicitud";
                    break;
                case INT_TBL_DAT_CHKAUTPEN:
                    strMsg = "Autorización pendiente";
                    break;
                case INT_TBL_DAT_CHKAUTPAR:
                    strMsg = "Autorización parcial";
                    break;           
                case INT_TBL_DAT_CHKAUTTOT:
                    strMsg = "Autorización total";
                    break;               
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
 
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                if (cargarDetReg()) {
                }
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        int i;
        strAux = "";
        String strEstConf = "";
        int intNumReg = 0;

        try 
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null) 
            { 
                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0:
                        strAux += " AND (a.fe_doc BETWEEN '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaDesde(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "' AND '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaHasta(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "')";
                        break;

                    case 1:
                        strAux += " AND (a.fe_doc<='"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaHasta(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "')";
                        break;

                    case 2:
                        strAux += " AND (a.fe_doc>='"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaDesde(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "')";
                        break;

                    case 3:
                        break;
                }

                if (optFil.isSelected()) 
                {

                    if (chkMosDocPenAut.isSelected()) 
                    {
                        if (strEstConf.equals("")) {
                            strEstConf += "'P'";
                        } else {
                            strEstConf += ",'P'";
                        }
                    }

                    if (chkMosDocParAut.isSelected()) 
                    {
                        if (strEstConf.equals("")) {
                            strEstConf += "'A'";
                        } else {
                            strEstConf += ",'A'";
                        }
                    }

                    if (chkMosDocTotAut.isSelected()) 
                    {
                        if (strEstConf.equals("")) {
                            strEstConf += "'T'";
                        } else {
                            strEstConf += ",'T'";
                        }
                    }

                    if (!strEstConf.equals("")) 
                    {
                        strAux += " AND a.st_aut IN (" + strEstConf + ") ";
                    }

                    if (!txtCodDep.getText().equals("")) 
                    {
                        strAux += " AND traemp.co_dep="
                                + txtCodDep.getText();
                    }
                    
                }

                if (optTod.isSelected()) 
                {
                    strEstConf = "'P','A','T'";

                    strAux += " AND a.st_aut IN ("
                            + strEstConf + ") ";
                }
                
                
                stm = con.createStatement();
                strSQL = ""; 
                
                if (objParSis.getCodigoUsuario() == 1 || objParSis.getCodigoUsuario() == 317 || objParSis.getCodigoUsuario() == 156) 
                {
                    strSQL += " SELECT DISTINCT "
                            + " (tra.tx_ape||' '||tra.tx_nom) AS empleado, "
                            + " a.*, "
                            + " d.tx_nom AS empresa, "
                            + " c.co_tipdoc, c.tx_descor, c.tx_deslar, "
                            + " CASE a.st_aut "
                            + "     WHEN 'P' THEN 'PENDIENTE' "
                            + "     WHEN 'A' THEN 'PARCIAL' "
                            + "     WHEN 'T' THEN 'COMPLETA' "
                            + "     WHEN 'D' THEN 'DENEGADA' "
                            + "     ELSE 'PENDIENTE' "
                            + " END AS estado_autorizacion, "
                            + " CASE "
                            + "     WHEN e.ne_numdiavacsolpag > 0 THEN 'PAG' "
                            + "     WHEN e.ne_numdiavacsolgoz > 0 THEN 'GOZ' "
                            + "     ELSE '' "
                            + " END AS tiposol, "
                            + " CASE "
                            + "     WHEN e.ne_numdiavacsolpag > 0 THEN e.ne_numdiavacsolpag "
                            + "     WHEN e.ne_numdiavacsolgoz > 0 THEN e.ne_numdiavacsolgoz "
                            + " END AS candiavacsol, "
                            + " e.fe_inivacgoz, e.fe_finvacgoz "
                            + " FROM tbm_cabsolvac a "
                            + " INNER JOIN tbm_tra tra ON a.co_tra = tra.co_tra " 
                            + " INNER JOIN tbm_traemp traemp ON traemp.co_emp = a.co_emp "
                            + "     AND traemp.co_tra = a.co_tra "
                            + " INNER JOIN tbm_cabtipdoc c ON a.co_emp = c.co_emp "
                            + "     AND a.co_loc = c.co_loc "
                            + "     AND c.co_tipdoc = " + intTipDocSolVac + " "
                            + " INNER JOIN tbm_emp d ON a.co_emp = d.co_emp "
                            + "     AND d.st_reg = 'A' "
                            + " INNER JOIN tbm_detsolvac e ON a.co_loc = e.co_loc "
                            + "     AND a.co_emp = e.co_emp "
                            + "     AND a.co_tipdoc = e.co_tipdoc "
                            + "     AND a.co_doc = e.co_doc "
                            + " WHERE a.st_reg = 'A' "
                            + strAux + " ";

                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        strSQL += " AND a.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                        strSQL += " AND a.co_loc = " + objParSis.getCodigoLocal() + " ";
                    }

                    strSQL += " ORDER BY a.co_doc ";
                }
                else
                {
                    strSQL += " SELECT DISTINCT "
                    + " (tra.tx_ape||' '||tra.tx_nom) AS empleado, "
                    + " a.*, "
                    + " d.tx_nom AS empresa, "
                    + " c.co_tipdoc, c.tx_descor, c.tx_deslar, "
                    + " CASE a.st_aut "
                    + "     WHEN 'P' THEN 'PENDIENTE' "
                    + "     WHEN 'A' THEN 'PARCIAL' "
                    + "     WHEN 'T' THEN 'COMPLETA' "
                    + "     WHEN 'D' THEN 'DENEGADA' "
                    + "     ELSE 'PENDIENTE' "
                    + " END AS estado_autorizacion, "
                    + " CASE "
                    + "     WHEN e.ne_numdiavacsolpag > 0 THEN 'PAG' "
                    + "     WHEN e.ne_numdiavacsolgoz > 0 THEN 'GOZ' "
                    + "     ELSE '' "
                    + " END AS tiposol, "
                    + " CASE "
                    + "     WHEN e.ne_numdiavacsolpag > 0 THEN e.ne_numdiavacsolpag "
                    + "     WHEN e.ne_numdiavacsolgoz > 0 THEN e.ne_numdiavacsolgoz "
                    + " END AS candiavacsol, "
                    + " e.fe_inivacgoz, e.fe_finvacgoz "
                    + " FROM tbm_cabsolvac a "
                    + " INNER JOIN tbm_tra tra ON a.co_tra = tra.co_tra "
                    + " INNER JOIN tbm_traemp traemp ON traemp.co_emp = a.co_emp "
                    + "     AND traemp.co_tra = a.co_tra "
                    + " INNER JOIN tbr_depprgusr dpu ON dpu.co_dep = traemp.co_dep "
                    + "     AND dpu.co_usr = " + objParSis.getCodigoUsuario() + " "
                    + "     AND dpu.co_mnu = " + objParSis.getCodigoMenu() + " "                    
                    + " INNER JOIN tbm_cabtipdoc c ON a.co_emp = c.co_emp "
                    + "     AND a.co_loc = c.co_loc "
                    + "     AND c.co_tipdoc = " + intTipDocSolVac + " "
                    + " INNER JOIN tbm_emp d ON a.co_emp = d.co_emp "
                    + "     AND d.st_reg = 'A' "
                    + " INNER JOIN tbm_detsolvac e ON a.co_loc = e.co_loc "
                    + "     AND a.co_emp = e.co_emp "
                    + "     AND a.co_tipdoc = e.co_tipdoc "
                    + "     AND a.co_doc = e.co_doc "
                    /*+ " INNER JOIN tbm_autsolvac autusr ON autusr.co_emp = a.co_emp "
                    + "     AND autusr.co_loc = a.co_loc "
                    + "     AND autusr.co_tipdoc = a.co_tipdoc "
                    + "     AND autusr.co_doc = a.co_doc "
                    + "     AND autusr.co_usraut = " + objParSis.getCodigoUsuario() + " "*/
                    + " WHERE a.st_reg = 'A' "
                    + strAux + " ";

                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        strSQL += " AND a.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                        strSQL += " AND a.co_loc = " + objParSis.getCodigoLocal() + " ";
                    }

                    strSQL += " ORDER BY a.fe_doc ";
                }
                
                System.out.println("SQL cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);

                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i = 0;

                lblMsgSis.setText("Listo");
                if (rst.next()) {
                    vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COEMP, rst.getInt("co_emp"));
                        vecReg.add(INT_TBL_DAT_EMP, rst.getString("empresa"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rst.getInt("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC, rst.getInt("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC, rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC, rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_CODDOC, rst.getInt("co_doc"));                      
                        
                        vecReg.add(INT_TBL_DAT_NUMDOC, rst.getInt("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC, rst.getString("fe_doc")); 
                        
                        vecReg.add(INT_TBL_DAT_EMPLE_SOLI, "<html><b>" + rst.getString("empleado") + "</b></html>");
                        //vecReg.add(INT_TBL_DAT_EMPLE_SOLI, rst.getString("empleado")); 
                      
                        vecReg.add(INT_TBL_DAT_BUTANESOLHSE, "...");

                        String strStAut = rst.getString("estado_autorizacion");

                        if (strStAut.compareTo("PENDIENTE") == 0) {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, true);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, false);
                        } else if (strStAut.compareTo("PARCIAL") == 0) {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, true);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, false);
                        } else if (strStAut.compareTo("COMPLETA") == 0) {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, true);
                        }                         
                        else {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, false);
                        }

                        vecReg.add(INT_TBL_DAT_BUTANE2, "...");
                        vecReg.add(INT_TBL_DAT_CO_TRA,  rst.getString("co_tra"));
                        vecReg.add(INT_TBL_DAT_TIP_SOL,  rst.getString("tiposol"));
                        vecReg.add(INT_TBL_DAT_SOL_ADE,  rst.getString("st_vacade"));
                        vecReg.add(INT_TBL_DAT_SOL_CANT_DIA_SOL,  rst.getInt("candiavacsol"));
                        vecReg.add(INT_TBL_DAT_FECDESDE_SOL,  rst.getString("fe_inivacgoz"));
                        vecReg.add(INT_TBL_DAT_FECHASTA_SOL,  rst.getString("fe_finvacgoz"));
                        vecReg.add(INT_TBL_DAT_BUTANEREP, "...");
                        
                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                }

                pgrSis.setValue(i);
                intNumReg = i;

                rst.close();
                stm.close();
                rst = null;
                stm = null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                objTblMod.initRowsState();

                lblMsgSis.setText("Se encontraron " + intNumReg + " registros.");
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
    
    
    public void Configura_ventana_consulta() 
    {
        configurarVenConDep();        
    }



    
}
