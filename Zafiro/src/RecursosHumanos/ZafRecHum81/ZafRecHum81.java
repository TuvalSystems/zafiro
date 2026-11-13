

package RecursosHumanos.ZafRecHum81;
import RecursosHumanos.ZafRecHum69.*;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Recursos humanos - Kardex de Vacaciones...
 * @author  Jean Paúl Morán
 * Guayaquil 11/11/2025
 */
public class ZafRecHum81 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_TRA=1;
    private static final int INT_TBL_DAT_IDE_TRA=2;
    private static final int INT_TBL_DAT_NOM_TRA=3;
    private static final int INT_TBL_DAT_INICON_TRA=4;
    private static final int INT_TBL_DAT_CENCOS=5;    
    private static final int INT_TBL_DAT_DEP_TRA=6;
    private static final int INT_TBL_DAT_CARLAB_TRA=7;
    
    private static final int INT_TBL_DAT_PRI_ANIO = 8;

    private int intColTot = -1;
    private int intColAntiguedad = -1;
    
    private java.util.ArrayList<Integer> arrAniosVac = new java.util.ArrayList<Integer>();

    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                //Editor: JTextField en celda.

    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafVenCon vcoCarLab;
    private ZafVenCon vcoDep;                                   //Ventana de consulta "Departamentos".

//    private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.
    
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;

    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strNomDep = "";
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;
    
    private boolean blnCon;  
    private ZafTblOrd objTblOrd;    
    
    private String strVersion="v1.0";
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVco;           //Editor: JTextField de consulta en celda.
        
    /** Crea una nueva instancia de la clase  */
    public ZafRecHum81(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);            
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3878)){
                butCon.setVisible(true);
            }            
            if(objPerUsr.isOpcionEnabled(3879)){
                butCer.setVisible(true);
            }

            if (!configurarFrm())
                exitForm();
            
            chkvalores.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (tblDat.getRowCount() > 0) {
                        butConActionPerformed(null);
                    }
                }
            });
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtDesLarDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        chkvalores = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        }

        ;
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
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setAutoscrolls(true);
        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 50, 330, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 70, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 90, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 90, 60, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 90, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 90, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 110, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(140, 110, 60, 20);

        txtDesLarDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusLost(evt);
            }
        });
        txtDesLarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarDepActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarDep);
        txtDesLarDep.setBounds(200, 110, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 110, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 130, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(140, 130, 60, 20);

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
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 130, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 130, 20, 20);

        chkvalores.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkvalores.setText("Mostrar periodos con detalle de valores");
        panFil.add(chkvalores);
        chkvalores.setBounds(10, 170, 310, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 413);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
   
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtDesLarDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtDesLarDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");            
        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
   }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
   }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
                txtNomEmp.setText(strNomEmp);
        }
   }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
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

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
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
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    private void txtDesLarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarDepActionPerformed
        // TODO add your handling code here:
        txtDesLarDep.transferFocus();
    }//GEN-LAST:event_txtDesLarDepActionPerformed

    private void txtDesLarDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusGained
        // TODO add your handling code here:
        strDesLarDep=txtDesLarDep.getText();
        txtDesLarDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtDesLarDepFocusGained

    private void txtDesLarDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusLost
        // TODO add your handling code here:
        if (txtDesLarDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtDesLarDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtDesLarDep.setText("");
                }
//                else
//                {
//                    mostrarVenConOfi(2);
//                }
            }
            else
                txtDesLarDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtDesLarDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
//        strCodDep=txtCodDep.getText();
        mostrarVenConDep(0);
        optFil.setSelected(true);
        optTod.setSelected(false);
       
    }//GEN-LAST:event_butDepActionPerformed
    
    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
                    txtCodTra.setText("");
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

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
//        if(txtMinGra.getText().equals("")){
//            txtMinGra.setText(" ");
//        }
    }//GEN-LAST:event_tblDatFocusGained

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTra;
    private javax.swing.JCheckBox chkvalores;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDesLarDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);
            
            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            
            //Configurar los JTables.
            configuraTbl();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        
        try{

            //Configurar JTable: Establecer el modelo.
            cargarAniosVacaciones();

            vecCab = new Vector();
            vecCab.clear();

            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_TRA, "Código.");
            vecCab.add(INT_TBL_DAT_IDE_TRA, "Identificación");
            vecCab.add(INT_TBL_DAT_NOM_TRA, "Empleado");
            vecCab.add(INT_TBL_DAT_INICON_TRA, "Fec.Ini.Con.");
            vecCab.add(INT_TBL_DAT_CENCOS, "Sucursal");            
            vecCab.add(INT_TBL_DAT_DEP_TRA, "Área");
            vecCab.add(INT_TBL_DAT_CARLAB_TRA, "Cargo");

            boolean blnDetVal = chkvalores.isSelected();

            int intCol = INT_TBL_DAT_PRI_ANIO;

            for (int i = 0; i < arrAniosVac.size(); i++) {
                int intAnio = arrAniosVac.get(i);
                String strPeriodo = (intAnio - 1) + "-" + intAnio;

                if (blnDetVal) {
                    vecCab.add(intCol++, "DIAS");
                    vecCab.add(intCol++, "BASE");
                    vecCab.add(intCol++, "ADICIONALES");
                } else {
                    vecCab.add(intCol++, strPeriodo);
                }
            }

            intColTot = intCol++;
            intColAntiguedad = intCol++;

            vecCab.add(intColTot, "TOTAL");
            vecCab.add(intColAntiguedad, "AÑOS");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            if (!agregarColGrpTblDat()) {
                return false;
            }
            //configurarEstiloCabeceraTblDat();
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IDE_TRA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TRA).setPreferredWidth(280);
            tcmAux.getColumn(INT_TBL_DAT_INICON_TRA).setPreferredWidth(90); 
            tcmAux.getColumn(INT_TBL_DAT_CENCOS).setPreferredWidth(110); 
            tcmAux.getColumn(INT_TBL_DAT_DEP_TRA).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_CARLAB_TRA).setPreferredWidth(190);            
            
            int intColAux = INT_TBL_DAT_PRI_ANIO;

            for (int i = 0; i < arrAniosVac.size(); i++) {
                tcmAux.getColumn(intColAux++).setPreferredWidth(90);

                if (blnDetVal) {
                    tcmAux.getColumn(intColAux++).setPreferredWidth(120);
                    tcmAux.getColumn(intColAux++).setPreferredWidth(120);
                }
            }

            tcmAux.getColumn(intColTot).setPreferredWidth(70);
            tcmAux.getColumn(intColAntiguedad).setPreferredWidth(60);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TRA, tblDat);
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_COD_CAR);
//            vecAux.add("" + INT_TBL_DAT_COM_BUT);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_DAT_COD_EM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            //tcmAux.getColumn(INT_TBL_DAT_COD_CAR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            //tcmAux.getColumn(INT_TBL_DAT_MINSEC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            objTblCelRenLbl=null;
            
            int intColVen[]=new int[5];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=4;
            intColVen[3]=5;
            intColVen[4]=6;
            
            int intColTbl[]=new int[5];
            
            objTblCelEdiTxtVco=new ZafTblCelEdiTxtVco(tblDat, vcoCarLab, intColVen, intColTbl);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CAR).setCellEditor(objTblCelEdiTxtVco);
            objTblCelEdiTxtVco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCarLab.setCampoBusqueda(0);
                    vcoCarLab.setCriterio1(1);
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            objTblCelRenLbl=null;         
           
            //Configurar JTable: Establecer el ordenamiento en la tabla.
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            // Renderer propio del Kardex
            ZafTblCelRenVac objRen = new ZafTblCelRenVac();

            tblDat.setDefaultRenderer(Object.class, objRen);
            tblDat.setDefaultRenderer(Number.class, objRen);
            tblDat.setDefaultRenderer(Double.class, objRen);
            
                
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    
    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

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
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if (!configuraTbl() || !cargarDat())
        {
            //Inicializar objetos si no se pudo cargar los datos.
            //lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
        }

        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
            tabFrm.setSelectedIndex(1);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
        }else{
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
        }

        objThrGUI=null;
    }
}
     
    private boolean cargarDat(){
      boolean blnRes=false;
      java.sql.Connection conn=null;
      java.sql.Statement stmLoc=null;
      java.sql.Statement stmLocAux=null;
      java.sql.ResultSet rstLoc = null;
      java.sql.ResultSet rstLocAux = null;
      String strSql="";
      String strFilFec="";
      String sqlFilEmp = "";
      Vector vecDataCon;
      Vector vecData;
      Vector vecFecCon;
      try{

          conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

          if(conn!=null){


              stmLoc=conn.createStatement();
              stmLocAux=conn.createStatement();
              vecDataCon = new Vector();
              vecData = new Vector();
              vecFecCon = new Vector();

//              if(txtCodEmp.getText().compareTo("")!=0){
//                  sqlFilEmp+= " and a.co_emp  = " + txtCodEmp.getText() + " ";
//              }

              if(txtCodTra.getText().compareTo("")!=0){
                  sqlFilEmp+= " AND e.codigo  = " + txtCodTra.getText() + " ";
              }

              String strDep="";
              if(txtCodDep.getText().compareTo("")!=0){
                  sqlFilEmp+= " AND e.codep  = " + txtCodDep.getText() + " ";
              }

//              String strEmp="";
//              if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                  strEmp=" and a.co_emp = "+ objParSis.getCodigoEmpresa();
//              }
              
              cargarAniosVacaciones();

                if (arrAniosVac.isEmpty()) {
                    lblMsgSis.setText("No existen años para consultar.");
                    return false;
                }

                int intAnioDesde = arrAniosVac.get(0);
                int intAnioHasta = arrAniosVac.get(arrAniosVac.size() - 1);

                StringBuilder sbColsAnios = new StringBuilder();
                
                boolean blnDetVal = chkvalores.isSelected();
                

                for (Integer intAnio : arrAniosVac) {

                    if (sbColsAnios.length() > 0) {
                        sbColsAnios.append(", \n");
                    }

                    sbColsAnios.append("  COALESCE(MAX(CASE WHEN c.ne_anio = ").append(intAnio).append(" THEN \n");
                    sbColsAnios.append("    CASE \n");
                    sbColsAnios.append("      WHEN c.dias_disponibles > 0 THEN c.dias_disponibles::text || ' de ' || c.ne_diaper::text \n");
                    sbColsAnios.append("      WHEN c.ne_diagoz > 0 THEN c.ne_diagoz::text || ' GOZADAS' \n");
                    sbColsAnios.append("      WHEN c.ne_diapag > 0 THEN c.ne_diapag::text || ' PAGADAS' \n");
                    sbColsAnios.append("      ELSE '0' \n");
                    sbColsAnios.append("    END \n");
                    sbColsAnios.append("  END), '-') AS \"").append(intAnio).append("\"");
                    
                    if (blnDetVal) {
                        sbColsAnios.append(", \n");

                        /*sbColsAnios.append("  COALESCE(MAX(CASE WHEN c.ne_anio = ").append(intAnio).append(" THEN \n");
                        sbColsAnios.append("    CASE WHEN c.ne_diaper > 0 THEN \n");
                        sbColsAnios.append("      '<html><b>' || LEAST(c.ne_diaper, 15)::text || ' d / $' || ROUND(COALESCE(c.valor_base, 0), 2)::text || '</b></html>' \n");
                        sbColsAnios.append("    ELSE '-' END \n");
                        sbColsAnios.append("  END), '-') AS \"base_").append(intAnio).append("\", \n");

                        sbColsAnios.append("  COALESCE(MAX(CASE WHEN c.ne_anio = ").append(intAnio).append(" THEN \n");
                        sbColsAnios.append("    CASE WHEN c.ne_diaper > 15 THEN \n");
                        sbColsAnios.append("      '<html><b>' || (c.ne_diaper - 15)::text || ' d / $' || ROUND(COALESCE(c.valor_adic, 0), 2)::text || '</b></html>' \n");
                        sbColsAnios.append("    ELSE '0 d / $0.00' END \n");
                        sbColsAnios.append("  END), '-') AS \"adic_").append(intAnio).append("\"");*/
                        
                        sbColsAnios.append("  COALESCE(MAX(CASE WHEN c.ne_anio = ").append(intAnio).append(" THEN \n");
                        sbColsAnios.append("    CASE WHEN c.ne_diaper > 0 THEN \n");
                        sbColsAnios.append("      LEAST(c.ne_diaper, 15)::text || ' d / $' || ROUND(COALESCE(c.valor_base, 0), 2)::text \n");
                        sbColsAnios.append("    ELSE '-' END \n");
                        sbColsAnios.append("  END), '-') AS \"base_").append(intAnio).append("\", \n");
                        
                        sbColsAnios.append("  COALESCE(MAX(CASE WHEN c.ne_anio = ").append(intAnio).append(" THEN \n");
                        sbColsAnios.append("    CASE WHEN c.ne_diaper > 15 THEN \n");
                        sbColsAnios.append("      (c.ne_diaper - 15)::text || ' d / $' || ROUND(COALESCE(c.valor_adic, 0), 2)::text \n");
                        sbColsAnios.append("    ELSE '0 d / $0.00' END \n");
                        sbColsAnios.append("  END), '-') AS \"adic_").append(intAnio).append("\"");
                        
                        
                        
                        
                    }
                     
                }

                strSql = "";

                strSql += "WITH cfg_base AS ( \n";
                strSql += "  SELECT DISTINCT a.co_tra \n";
                strSql += "  FROM tbm_cfgvactra a \n";
                strSql += "  WHERE a.st_reg = 'A' \n";
                strSql += "    AND a.ne_anio BETWEEN " + intAnioDesde + " AND " + intAnioHasta + " \n";
                strSql += "), \n";

                strSql += "vigente AS ( \n";
                strSql += "  SELECT DISTINCT ON (t.co_tra) \n";
                strSql += "         t.co_tra, \n";
                strSql += "         t.co_dep, \n";
                strSql += "         t.co_car, \n";
                strSql += "         t.fe_inicon, \n";
                strSql += "         t.st_reg \n";
                strSql += "  FROM tbm_traemp t \n";
                strSql += "  WHERE t.co_emp = 1 \n";
                strSql += "  ORDER BY t.co_tra, t.fe_inicon DESC NULLS LAST \n";
                strSql += "), \n";

                strSql += "empleados AS ( \n";
                strSql += "  SELECT tr.co_tra AS codigo, \n";
                strSql += "         tr.tx_ide AS identificacion, \n";
                strSql += "         tr.tx_ape || ' ' || tr.tx_nom AS empleado, \n";
                strSql += "         v.fe_inicon AS inicio_contrato, \n";
                strSql += "         COALESCE(cc.tx_nom, '-') AS centro_costo, \n";
                strSql += "         d.tx_deslar AS departamento, \n";
                strSql += "         car.tx_nomcar AS cargo, \n";
                //strSql += "         tr.st_reg AS estado, \n";
                strSql += "         v.st_reg AS estado, \n";
                strSql += "         d.co_dep AS codep \n";
                strSql += "  FROM cfg_base cb \n";
                strSql += "  JOIN tbm_tra tr \n";
                strSql += "    ON tr.co_tra = cb.co_tra \n";
                strSql += "  JOIN vigente v \n";
                strSql += "    ON v.co_tra = tr.co_tra \n";

                strSql += "  LEFT JOIN tbm_cencos cc \n";
                strSql += "    ON cc.tx_codcos = tr.tx_codcos \n";

                strSql += "  JOIN tbm_dep d \n";
                strSql += "    ON d.co_dep = v.co_dep \n";
                strSql += "  JOIN tbm_carlab car \n";
                strSql += "    ON car.co_car = v.co_car \n";
                strSql += "  WHERE tr.tx_espas = 'N' \n";
                strSql += "), \n";

                strSql += "valores_periodo AS ( \n";
                strSql += "  SELECT co_emp, co_tra, ne_perdes, ne_perhas, \n";
                strSql += "         SUM(COALESCE(nd_valvac, 0)) AS valor_base \n";
                strSql += "  FROM tbm_detpervactra \n";
                strSql += "  WHERE st_reg = 'A' \n";
                strSql += "  GROUP BY co_emp, co_tra, ne_perdes, ne_perhas \n";
                strSql += "), \n";
                
                strSql += "cfg AS ( \n";
                strSql += "  SELECT a.co_tra, \n";
                strSql += "         a.ne_anio, \n";
                strSql += "         COALESCE(a.ne_diaper, 0) AS ne_diaper, \n";
                strSql += "         COALESCE(a.ne_diagoz, 0) AS ne_diagoz, \n";
                strSql += "         COALESCE(a.ne_diapag, 0) AS ne_diapag, \n";
                strSql += "         COALESCE(vp.valor_base, 0) AS valor_base, \n";
                strSql += "         CASE WHEN COALESCE(a.ne_diaper, 0) > 15 THEN \n";
                strSql += "              (COALESCE(vp.valor_base, 0) / 15) * (COALESCE(a.ne_diaper, 0) - 15) \n";
                strSql += "              ELSE 0 END AS valor_adic, \n";
                strSql += "         GREATEST( \n";
                strSql += "             COALESCE(a.ne_diaper, 0) \n";
                strSql += "           - COALESCE(a.ne_diagoz, 0) \n";
                strSql += "           - COALESCE(a.ne_diapag, 0), \n";
                strSql += "             0 \n";
                strSql += "         ) AS dias_disponibles \n";
                strSql += "  FROM tbm_cfgvactra a \n";
                strSql += "  JOIN cfg_base cb \n";
                strSql += "    ON cb.co_tra = a.co_tra \n";
                strSql += "  LEFT JOIN valores_periodo vp \n";
                strSql += "    ON vp.co_emp = a.co_emp \n";
                strSql += "   AND vp.co_tra = a.co_tra \n";
                strSql += "   AND vp.ne_perdes = a.ne_anio - 1 \n";
                strSql += "   AND vp.ne_perhas = a.ne_anio \n";
                strSql += "  WHERE a.st_reg = 'A' \n";
                strSql += "    AND a.ne_anio BETWEEN " + intAnioDesde + " AND " + intAnioHasta + " \n";
                strSql += ") \n";

                strSql += "SELECT e.codigo, \n";
                strSql += "       e.identificacion, \n";
                strSql += "       e.empleado, \n";
                strSql += "       e.inicio_contrato, \n";
                strSql += "       e.centro_costo, \n";
                strSql += "       e.departamento, \n";
                strSql += "       e.cargo, \n";
                strSql += "       e.estado, \n";
                strSql += "       e.codep, \n";

                strSql += sbColsAnios.toString();
                strSql += ", \n";

                strSql += "       COALESCE(SUM(c.dias_disponibles), 0) AS total, \n";

                
                strSql += "       ROUND(((CURRENT_DATE - e.inicio_contrato::date)::numeric / 365), 1) AS anios \n";

                strSql += "FROM empleados e \n";
                strSql += "LEFT JOIN cfg c \n";
                strSql += "  ON c.co_tra = e.codigo \n";
                strSql += "WHERE e.estado = 'A' \n";
                strSql += sqlFilEmp + " \n";
                strSql += "GROUP BY e.codigo, \n";
                strSql += "         e.identificacion, \n";
                strSql += "         e.empleado, \n";
                strSql += "         e.inicio_contrato, \n";
                strSql += "         e.centro_costo, \n";
                strSql += "         e.departamento, \n";
                strSql += "         e.cargo, \n";
                strSql += "         e.estado, \n";
                strSql += "         e.codep \n";
                strSql += "ORDER BY e.codigo \n";


              System.out.println("q consul: " + strSql);
              rstLoc=stmLoc.executeQuery(strSql);

              vecDat = new Vector();
              while(rstLoc.next()){
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");                
                    vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("codigo"));
                    vecReg.add(INT_TBL_DAT_IDE_TRA,rstLoc.getString("identificacion"));
                    vecReg.add(INT_TBL_DAT_NOM_TRA,rstLoc.getString("empleado"));
                    //vecReg.add(INT_TBL_DAT_NOM_TRA, "<html><b>" + rstLoc.getString("empleado") + "</b></html>");
                    vecReg.add(INT_TBL_DAT_INICON_TRA,rstLoc.getString("inicio_contrato")); 
                    vecReg.add(INT_TBL_DAT_CENCOS, rstLoc.getString("centro_costo"));                    
                    vecReg.add(INT_TBL_DAT_DEP_TRA,rstLoc.getString("departamento"));
                    vecReg.add(INT_TBL_DAT_CARLAB_TRA,rstLoc.getString("cargo"));              

                    int intCol = INT_TBL_DAT_PRI_ANIO;

                    for (Integer intAnio : arrAniosVac) {
                        vecReg.add(intCol++, rstLoc.getString(String.valueOf(intAnio)));

                        if (blnDetVal) {
                            vecReg.add(intCol++, rstLoc.getString("base_" + intAnio));
                            vecReg.add(intCol++, rstLoc.getString("adic_" + intAnio));
                        }
                    }

                    //vecReg.add(intCol++, "<html><b>" + rstLoc.getString("total") + "</b></html>");
                    vecReg.add(
                        intCol++,
                        rstLoc.getString("total")
                    );
                    vecReg.add(intCol++, rstLoc.getString("anios"));


                    vecDataCon.add(vecReg);
                }

                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod);          
                lblMsgSis.setText("Listo");
                tabFrm.setSelectedIndex(1);
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                pgrSis.setIndeterminate(false);
                vecDat.clear();
                
                blnRes = true;
          }
      }catch(java.sql.SQLException Evt) {Evt.printStackTrace(); blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
         catch(Exception Evt) { Evt.printStackTrace();blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
      finally {
            try{rstLoc.close();}catch(Throwable ignore){}
            try{rstLocAux.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            try{stmLocAux.close();}catch(Throwable ignore){}
            try{conn.close();}catch(Throwable ignore){}
        }
        System.gc();
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;    
                case INT_TBL_DAT_IDE_TRA:
                    strMsg="Cedula del empleado";
                    break; 
                case INT_TBL_DAT_NOM_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_INICON_TRA:
                    strMsg="Fecha Inicio Contrado del empleado";
                    break;
                case INT_TBL_DAT_CENCOS:
                    strMsg="Sucursal";
                    break;
                case INT_TBL_DAT_DEP_TRA:
                    strMsg="Departamento del empleado";
                    break;
                case INT_TBL_DAT_CARLAB_TRA:
                    strMsg="Nombre del cargo laboral";
                    break;               
                
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

     private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
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
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            //Armar la sentencia SQL.            
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
              arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            String strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg in ('P' ,'A') order by co_dep";
            } else {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg = 'A' and co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objParSis.getCodigoUsuario() + " "
                        + //"and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";
                        "and co_mnu=" + objParSis.getCodigoMenu() + ")";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void cargarAniosVacaciones() {
        arrAniosVac.clear();

        int intAnioDesde = 2023;
        int intAnioHasta = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

        for (int intAnio = intAnioDesde; intAnio <= intAnioHasta; intAnio++) {
            arrAniosVac.add(intAnio);
        }

        intColTot = INT_TBL_DAT_PRI_ANIO + arrAniosVac.size();
        intColAntiguedad = intColTot + 1;
    }
    
    
    private boolean agregarColGrpTblDat()
    {
        boolean blnRes = true;

        try {
            boolean blnDetVal = chkvalores.isSelected();

            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();

            if (!blnDetVal) {
                objTblHeaGrp.setHeight(16);
                tblDat.getTableHeader().revalidate();
                tblDat.getTableHeader().repaint();
                return true;
            }

            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrp = null;

            objTblHeaColGrp = new ZafTblHeaColGrp("");
            objTblHeaColGrp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            for (int intCol = 0; intCol < INT_TBL_DAT_PRI_ANIO; intCol++) {
                objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intCol));
            }

            int intCol = INT_TBL_DAT_PRI_ANIO;

            for (int i = 0; i < arrAniosVac.size(); i++) {
                int intAnio = arrAniosVac.get(i);
                String strPeriodo = (intAnio - 1) + "-" + intAnio;

                objTblHeaColGrp = new ZafTblHeaColGrp(strPeriodo);
                objTblHeaColGrp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intCol++)); // DIAS
                objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intCol++)); // BASE
                objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intCol++)); // ADIC.
            }

            objTblHeaColGrp = new ZafTblHeaColGrp("");
            objTblHeaColGrp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intColTot));
            objTblHeaColGrp.add(tblDat.getColumnModel().getColumn(intColAntiguedad));

            tblDat.getTableHeader().revalidate();
            tblDat.getTableHeader().repaint();

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private class ZafTblCelRenVac extends javax.swing.table.DefaultTableCellRenderer
    {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
        {
            java.awt.Component cmp =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);

            boolean blnNeg = false;

            // Empleado
            if (column == INT_TBL_DAT_NOM_TRA)
            {
                blnNeg = true;
            }

            // Total
            if (column == intColTot)
            {
                blnNeg = true;
            }

            /*
             * Cuando se muestran valores detallados:
             *
             * DIAS | BASE | ADICIONALES
             * DIAS | BASE | ADICIONALES
             * ...
             *
             * Sólo BASE y ADICIONALES van en negrita.
             */
            if (chkvalores.isSelected()
                    && column >= INT_TBL_DAT_PRI_ANIO
                    && column < intColTot)
            {
                int intPos = (column - INT_TBL_DAT_PRI_ANIO) % 3;

                // 0 = DIAS
                // 1 = BASE
                // 2 = ADICIONALES
                if (intPos == 1 || intPos == 2)
                {
                    blnNeg = true;
                }
            }

            if (blnNeg)
            {
                cmp.setFont(
                    new java.awt.Font(
                        cmp.getFont().getName(),
                        java.awt.Font.BOLD,
                        cmp.getFont().getSize()
                    )
                );
            }
            else
            {
                cmp.setFont(
                    new java.awt.Font(
                        cmp.getFont().getName(),
                        java.awt.Font.PLAIN,
                        cmp.getFont().getSize()
                    )
                );
            }

            return cmp;
        }
    }


}