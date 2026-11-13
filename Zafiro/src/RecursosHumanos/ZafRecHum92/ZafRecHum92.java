/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum92;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import groovy.util.MapEntry;
import java.awt.Color;
import java.beans.PropertyEditorManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Sistemas05
 */
public class ZafRecHum92 extends javax.swing.JInternalFrame {

    /**
     * Creates new form ZafRecHum92
     */
    
    Vector vecCabDep;
    Vector vecDatDep;
    Vector vecCabHExt;
    Vector vecDatHext;
    
    /*COLUMNAS DE LA TABLA DE FILTROS DE BUSQUEDA*/
    private static final int INT_TBL_LINEA=0;
    private static final int INT_TBL_CHECK=1;
    private static final int INT_TBL_COD_DEP=2;
    private static final int INT_TBL_NOM_DEP=3;
    /*COLUMNAS DE LA TABLA DE FILTROS DE BUSQUEDA*/
    
    /*COLUMNAS DE LA TABLA DE RESULTADOS DE BUSQUEDA*/
    private static final int INT_TBL_RES_LINEA=0;
    private static final int INT_TBL_RES_DUPLICAR=1;
    private static final int INT_TBL_RES_CODTRA=2;
    private static final int INT_TBL_RES_NOMTRA=3;
    private static final int INT_TBL_RES_CODDEP=4;
    private static final int INT_TBL_RES_NOMDEP=5;
    
    private static final int INT_TBL_RES_MARCENT=6;
    private static final int INT_TBL_RES_MARCSAL=7;
    private static final int INT_TBL_RES_MARCENTHEX=8;
    private static final int INT_TBL_RES_MARCSALHEX=9;
    private static final int INT_TBL_RES_OBSERV=10;
    private static final int INT_TBL_RES_BTNOBSERV=11;
    private static final int INT_TBL_RES_MODMARC=12;
    private static final int INT_TBL_RES_MODHEXT=13;
    /*COLUMNAS DE LA TABLA DE RESULTADOS DE BUSQUEDA*/
    
    private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu objTblPopMnu;
    
       
    ZafTblMod objTblModDep;
    ZafTblMod objTblModHExt;
    
    ZafParSis objParSis=null;
    
    ZafTblCelRenChk objTblCelRenChk;
    ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblFilCab objTblFilCab;
    private ZafVenCon vcoTra;
    private ZafUtil objUti;
    
    private ZafTblCelEdiTxt objTblCelEdiTxtMarEnt, objTblCelEdiTxtMarSal, objTblCelEdiTxtMarEntHext,objTblCelEdiTxtMarSalHExt, objTblCelEdiTxtObs ;    //Editor: JTextField en celda.    
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButDup;    
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private String strCodTra,strNomTra = "";
        
    int intCodTipDoc=0, intNumMax=0, intSig=0;
    
    
    private final String strVersion = " v1.2";
    
    private ZafPerUsr objPerUsr;
    
    FrozenTablePaneHorExt paneFrozen;
    
    
    public ZafRecHum92(Librerias.ZafParSis.ZafParSis obj) {
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            initComponents();
            lblTitulo.setText(objParSis.getNombreMenu()); 
            this.setTitle(objParSis.getNombreMenu() + strVersion);            
            configurarFrm();
        }catch(CloneNotSupportedException e) {/*objUti.mostrarMsgErr_F1(this, e);*/e.printStackTrace();}
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpBtnEmp = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        PanPer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbAnio = new javax.swing.JComboBox<>();
        cmbSemana = new javax.swing.JComboBox<>();
        txtSemDes = new javax.swing.JTextField();
        txtSemHas = new javax.swing.JTextField();
        panEmp = new javax.swing.JPanel();
        rbEmp = new javax.swing.JRadioButton();
        rbTotEmp = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDep = new javax.swing.JTable();
        btnEmp = new javax.swing.JButton();
        panRes = new javax.swing.JPanel();
        tblDatExt = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }

        };
        panSur = new javax.swing.JPanel();
        btnCon = new javax.swing.JButton();
        btnReg = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(475, 550));
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
            }
        });

        panNor.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panNor.setMinimumSize(new java.awt.Dimension(24, 14));
        panNor.setPreferredSize(new java.awt.Dimension(24, 18));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setText("Título de la ventana");

        javax.swing.GroupLayout panNorLayout = new javax.swing.GroupLayout(panNor);
        panNor.setLayout(panNorLayout);
        panNorLayout.setHorizontalGroup(
            panNorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 684, Short.MAX_VALUE)
            .addGroup(panNorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panNorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblTitulo)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panNorLayout.setVerticalGroup(
            panNorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
            .addGroup(panNorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panNorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblTitulo)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(1, 315));
        panFil.setLayout(null);

        PanPer.setLayout(null);

        jLabel1.setText("Periodo:");
        PanPer.add(jLabel1);
        jLabel1.setBounds(30, 20, 60, 20);

        cmbAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAnioActionPerformed(evt);
            }
        });
        PanPer.add(cmbAnio);
        cmbAnio.setBounds(90, 20, 120, 20);

        cmbSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemanaActionPerformed(evt);
            }
        });
        PanPer.add(cmbSemana);
        cmbSemana.setBounds(220, 20, 120, 20);

        txtSemDes.setEnabled(false);
        PanPer.add(txtSemDes);
        txtSemDes.setBounds(370, 20, 100, 20);

        txtSemHas.setEnabled(false);
        PanPer.add(txtSemHas);
        txtSemHas.setBounds(500, 20, 100, 20);

        panFil.add(PanPer);
        PanPer.setBounds(0, 0, 679, 69);

        panEmp.setLayout(null);

        grpBtnEmp.add(rbEmp);
        rbEmp.setText("Sólo los empleados que cumplan el criterio seleccionado");
        rbEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEmpActionPerformed(evt);
            }
        });
        panEmp.add(rbEmp);
        rbEmp.setBounds(30, 20, 350, 23);

        grpBtnEmp.add(rbTotEmp);
        rbTotEmp.setSelected(true);
        rbTotEmp.setText("Todos los empleados");
        rbTotEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTotEmpActionPerformed(evt);
            }
        });
        panEmp.add(rbTotEmp);
        rbTotEmp.setBounds(30, 0, 186, 23);

        jLabel2.setText("Empleado:");
        panEmp.add(jLabel2);
        jLabel2.setBounds(40, 60, 60, 14);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.setDragEnabled(true);
        txtCodEmp.setEnabled(false);
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
        panEmp.add(txtCodEmp);
        txtCodEmp.setBounds(100, 60, 60, 20);

        txtNomEmp.setEnabled(false);
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
        panEmp.add(txtNomEmp);
        txtNomEmp.setBounds(160, 60, 340, 20);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("LISTADO DE DEPARTAMENTOS"));

        tblDep.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDep);

        panEmp.add(jScrollPane1);
        jScrollPane1.setBounds(30, 120, 320, 120);

        btnEmp.setText("...");
        btnEmp.setEnabled(false);
        btnEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpActionPerformed(evt);
            }
        });
        panEmp.add(btnEmp);
        btnEmp.setBounds(500, 60, 20, 20);

        panFil.add(panEmp);
        panEmp.setBounds(0, 65, 679, 280);

        tabGen.addTab("Filtro", panFil);

        panRes.setLayout(new java.awt.BorderLayout());

        tblDatExt.setModel(new javax.swing.table.DefaultTableModel(
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
        panRes.add(tblDatExt, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("Reporte", panRes);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        panSur.setPreferredSize(new java.awt.Dimension(684, 50));

        btnCon.setText("Consultar");
        btnCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConActionPerformed(evt);
            }
        });
        panSur.add(btnCon);

        btnReg.setText("Guardar");
        btnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegActionPerformed(evt);
            }
        });
        panSur.add(btnReg);

        btnCerrar.setText("Cerrar");
        btnCerrar.setMaximumSize(new java.awt.Dimension(71, 23));
        btnCerrar.setMinimumSize(new java.awt.Dimension(71, 23));
        btnCerrar.setPreferredSize(new java.awt.Dimension(73, 23));
        panSur.add(btnCerrar);

        getContentPane().add(panSur, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 700, 484);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSemanaActionPerformed
        // TODO add your handling code here:
        Semana sm=(Semana)cmbSemana.getSelectedItem();
        if(sm!=null && cmbSemana.getSelectedIndex()>=0){
            String strFecDes=sm.getFecDes();
            String strFecHas=sm.getFecHas();
            txtSemDes.setText(strFecDes);
            txtSemHas.setText(strFecHas);     
            configurarTblRes();
            agregarColTblDat();            
        }

        
    }//GEN-LAST:event_cmbSemanaActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        if(!txtCodEmp.getText().equals(strCodTra)){
            if(txtCodEmp.getText().equals("")){
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                isEnabDepa(true);
            }else{
                //buscarTra("a.co_tra", txtCodEmp.getText(), 0);
                mostrarVenConTra(1);
            }
        }
        
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodTra=txtCodEmp.getText();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomTra= txtNomEmp.getText();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        if(!strNomTra.equals(txtNomEmp.getText())){
            if(txtNomEmp.getText().equals("")){
                txtNomEmp.setText("");
                txtCodEmp.setText("");
                if(txtCodEmp.getText().equals("")){
                    isEnabDepa(true);
                }                
            }else{
                //buscarTra("a.tx_ape", txtNomEmp.getText(),1);
                mostrarVenConTra(2);
            }            
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void rbEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEmpActionPerformed
        // TODO add your handling code here:
        if(rbEmp.isSelected()){
            txtCodEmp.setEnabled(true);
            txtNomEmp.setEnabled(true);
            btnEmp.setEnabled(true);
        }
    }//GEN-LAST:event_rbEmpActionPerformed

    private void rbTotEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTotEmpActionPerformed
        // TODO add your handling code here:
        if(rbTotEmp.isSelected()){
            txtCodEmp.setEnabled(false);
            txtNomEmp.setEnabled(false);
            btnEmp.setEnabled(false);
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            isEnabDepa(true);
        }        
    }//GEN-LAST:event_rbTotEmpActionPerformed

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }    
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpActionPerformed
        // TODO add your handling code here:
        mostrarVenConTra(0);
    }//GEN-LAST:event_btnEmpActionPerformed

    private void btnConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConActionPerformed
        // TODO add your handling code here:
        cargarCons2();
    }//GEN-LAST:event_btnConActionPerformed

    private void btnRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegActionPerformed
        // TODO add your handling code here:
        int rsp=JOptionPane.showConfirmDialog(this, "Esta seguro que desea guardar?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(rsp==0){
            if(!validarDatos()){
                if(!verificarSolapeHorasPantalla()){
                    if(registrarSolicitudes()){
                        mensajeInf("Se grabó con éxito");                
                    }else{
                        mensajeInf("La operación falló");
                    }
                }else{
                    mensajeInf("Hay Empleado(s) que registran horas solapadas en el mismo dia, Por favor ingrese la marcacion y vuelva a intentarlo");                
                }
            }
        }
    }//GEN-LAST:event_btnRegActionPerformed

    private void cmbAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAnioActionPerformed
        // TODO add your handling code here:
        cargarSemanas();
    }//GEN-LAST:event_cmbAnioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanPer;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCon;
    private javax.swing.JButton btnEmp;
    private javax.swing.JButton btnReg;
    private javax.swing.JComboBox<String> cmbAnio;
    private javax.swing.JComboBox<Semana> cmbSemana;
    private javax.swing.ButtonGroup grpBtnEmp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panEmp;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panRes;
    private javax.swing.JPanel panSur;
    private javax.swing.JRadioButton rbEmp;
    private javax.swing.JRadioButton rbTotEmp;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDatExt;
    private javax.swing.JTable tblDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtSemDes;
    private javax.swing.JTextField txtSemHas;
    // End of variables declaration//GEN-END:variables

    private void cargarAnio(){
        Connection con=null;
        Statement stm=null;
        ResultSet rsAnio=null;
        String strSql="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stm=con.createStatement();
            strSql="Select ne_ani from tbm_aniCreSis where co_emp=0 and ne_ani>="+((Calendar.getInstance().get(Calendar.YEAR))-1)+" and ne_ani<="+Calendar.getInstance().get(Calendar.YEAR);
            rsAnio=stm.executeQuery(strSql);
            while(rsAnio.next()){
                cmbAnio.addItem(String.valueOf(rsAnio.getInt("ne_ani")));
            }
            con.close();
            rsAnio.close();
            stm.close();
            con=null;
            rsAnio=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    
    }
    
    private void cargarSemanas(){
        Connection con=null;
        Statement stm=null;
        ResultSet rsAnio=null;
        String strSql="";
        Semana sem=null;
 
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            //stm=con.createStatement();
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //strSql="Select co_ani, co_sem, tx_deslar,fe_ini,fe_fin from tbm_semAni where co_ani="+Calendar.getInstance().get(Calendar.YEAR);
            strSql="Select co_ani, co_sem, tx_deslar,fe_ini,fe_fin from tbm_semAni where co_ani="+(String)cmbAnio.getSelectedItem();
            rsAnio=stm.executeQuery(strSql);
            
            cmbSemana.removeAllItems();
//            sem=new Semana();
//            sem.setIntAnio(2019);
//            sem.setIntSemana(0);
//            sem.setStrSemana("Seleccione");
//
//            cmbSemana.addItem(sem);            
            //sino hay registros significa que no estan creadas la semanas del año en consulta por lo que se procede a crearla
            if (!rsAnio.isBeforeFirst()) {
                strSql="SELECT insertar_semana("+(String)cmbAnio.getSelectedItem() + ");";
                rsAnio=stm.executeQuery(strSql);
            }  
  
            while(rsAnio.next()){
                sem=new Semana();
                sem.setIntAnio(rsAnio.getInt("co_ani"));
                sem.setIntSemana(rsAnio.getInt("co_sem"));
                sem.setStrSemana(rsAnio.getString("tx_deslar"));     
                sem.setFecDes(rsAnio.getString("fe_ini"));
                sem.setFecHas(rsAnio.getString("fe_fin"));
                cmbSemana.addItem(sem);
            }
            //rsAnio.beforeFirst();
            con.close();
            rsAnio.close();
            stm.close();
            con=null;
            rsAnio=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    
    }
    
    private void configurarFrm(){
        objUti=new ZafUtil();
        cargarAnio();
        cargarSemanas();     
        configurarTblDep();
        cargarDep();
        configurarVenConTra();
        configurarTblRes();
        agregarColTblDat();

        if(objPerUsr.isOpcionEnabled(4596)){
            btnCon.setEnabled(true);
            btnCon.setVisible(true);
        }
        if(objPerUsr.isOpcionEnabled(4597)){
            btnReg.setEnabled(true);
            btnReg.setVisible(true);
        }            
        
    }
    
    private void configurarTblDep(){
        Vector vecColEdi=new Vector();
        
        vecCabDep=new Vector();
        vecCabDep.add(INT_TBL_LINEA, "");
        vecCabDep.add(INT_TBL_CHECK, "");
        vecCabDep.add(INT_TBL_COD_DEP, "Cód.Dep.");
        vecCabDep.add(INT_TBL_NOM_DEP, "Departamento");
        
        objTblModDep=new ZafTblMod();
        objTblModDep.setHeader(vecCabDep);
        tblDep.setModel(objTblModDep);
        tblDep.setRowSelectionAllowed(true);
        tblDep.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        TableColumnModel tcm=tblDep.getColumnModel();
        tcm.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
        tcm.getColumn(INT_TBL_CHECK).setPreferredWidth(15);
        tcm.getColumn(INT_TBL_COD_DEP).setPreferredWidth(80);
        tcm.getColumn(INT_TBL_NOM_DEP).setPreferredWidth(150);
        
        tblDep.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tcm.getColumn(INT_TBL_CHECK).setResizable(false);
        
        vecColEdi.add(""+ INT_TBL_CHECK);
        objTblModDep.setColumnasEditables(vecColEdi);
        
        objTblFilCab = new ZafTblFilCab(tblDep);
        tcm.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
        
        
        objTblCelRenChk = new ZafTblCelRenChk();
        tcm.getColumn(INT_TBL_CHECK).setCellRenderer(objTblCelRenChk);

        objTblCelEdiChk = new ZafTblCelEdiChk(tblDep);
        tcm.getColumn(INT_TBL_CHECK).setCellEditor(objTblCelEdiChk);
        
        
        objTblModDep.setModoOperacion(ZafTblMod.INT_TBL_EDI);
    
    }
    
    private void isEnabDepa(boolean booVal){
        tblDep.setEnabled(booVal);
    }
    
    private void setearDepa(int intCodDep){
        
        for(int i=0; i<objTblModDep.getRowCount(); i++){
            objTblModDep.setValueAt(Boolean.FALSE, i, INT_TBL_CHECK);
            tblDep.setModel(objTblModDep);
        }        
        for(int i=0; i<objTblModDep.getRowCount(); i++){
            if((Integer)objTblModDep.getValueAt(i, INT_TBL_COD_DEP)==intCodDep){
                objTblModDep.setValueAt(Boolean.TRUE, i, INT_TBL_CHECK);
                tblDep.setModel(objTblModDep);
                isEnabDepa(false);
                break;
            }
        }
        
    }
    
    public void buscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoTra.getValueAt(1));
            txtNomEmp.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodEmp.setText(vcoTra.getValueAt(1));
                txtNomEmp.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            }else{
                txtCodEmp.setText(strCodTra);
                txtNomEmp.setText("");
            }
        }
        
    }     
    
    
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tra");
            arlCam.add("a.tx_ape");
            arlCam.add("a.tx_nom");
            arlCam.add("b.co_dep");
            //Alias de los campos.

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            arlAli.add("Departamento");

            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom, b.co_dep from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL+="SELECT a.co_tra,a.tx_ape,a.tx_nom, b.co_dep "+"\n";
                strSQL+="FROM tbm_tra a  "+"\n";
                strSQL+="INNER JOIN tbm_traemp b on(b.co_tra=a.co_tra AND b.st_reg = 'A')  "+"\n";
                strSQL+="WHERE  b.co_emp= "+objParSis.getCodigoEmpresa()+"\n";
                /*strSQL+="AND b.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr"+"\n";
                strSQL+="WHERE co_emp="+objParSis.getCodigoEmpresa()+"\n";
                strSQL+="AND co_usr = "+objParSis.getCodigoUsuario()+"\n";
                strSQL+="AND co_loc="+objParSis.getCodigoLocal()+"\n";
                strSQL+="AND co_mnu="+objParSis.getCodigoMenu()+") "+"\n";*/
                strSQL+="ORDER BY (a.tx_ape || ' ' || a.tx_nom)";
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
    
    private void cargarDep(){
    
        Connection con=null;
        Statement stm=null;
        ResultSet rsDep=null;
        String strSql="";
        Vector vecReg=null;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stm=con.createStatement();
            strSql="Select co_dep, tx_deslar from tbm_dep order by co_dep";
            rsDep=stm.executeQuery(strSql);
            vecDatDep=new Vector();
            while(rsDep.next()){
                vecReg=new Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_CHECK, false);
                vecReg.add(INT_TBL_COD_DEP, rsDep.getInt("co_dep"));
                vecReg.add(INT_TBL_NOM_DEP,rsDep.getString("tx_deslar"));
                vecDatDep.add(vecReg);
            }
            objTblModDep.setData(vecDatDep);
            tblDep.setModel(objTblModDep);
            con.close();
            rsDep.close();
            stm.close();
            con=null;
            rsDep=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
    
    private int obtenerEmpPag(int intCodTra){
    
        Connection con=null;
        Statement stm=null;
        ResultSet rsEmpPag=null;
        String strSql="";
        int intEmpPag=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stm=con.createStatement();

            strSql =  "select b.co_emp,a.co_tra, (a.tx_ape || ' ' || a.tx_nom) AS nomCom ";
            strSql += " from tbm_tra a ";
            strSql += " inner join tbm_traemp  b on (a.co_tra=b.co_tra and b.st_reg like 'A') ";
            strSql += " where a.st_reg like 'A'";
            strSql += " and  b.co_tra="+intCodTra;
            strSql += " order by (a.tx_ape || ' ' || a.tx_nom)";            
            
            rsEmpPag=stm.executeQuery(strSql);
            if(rsEmpPag.next())
            {
                intEmpPag=rsEmpPag.getInt("co_emp");
            }
            con.close();
            rsEmpPag.close();
            stm.close();
            con=null;
            rsEmpPag=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intEmpPag;
    }    
    
    
    private void configurarTblRes(){
        vecCabHExt=new Vector();
        vecCabHExt.add(INT_TBL_RES_LINEA, "");
        vecCabHExt.add(INT_TBL_RES_DUPLICAR, "");
        vecCabHExt.add(INT_TBL_RES_CODTRA, "Cod. Emp");
        vecCabHExt.add(INT_TBL_RES_NOMTRA, "Empleado");
        vecCabHExt.add(INT_TBL_RES_CODDEP, "Cod.Dep.");
        vecCabHExt.add(INT_TBL_RES_NOMDEP, "Departamento.");
        Semana sem=(Semana)cmbSemana.getSelectedItem();
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            Calendar calFecDes=Calendar.getInstance();
            calFecDes.setTime(fmt.parse(sem.getFecDes()));
            Calendar calFecHas=Calendar.getInstance();
            calFecHas.setTime(fmt.parse(sem.getFecHas()));
            
            int intCntIte=0;
            while(calFecDes.compareTo(calFecHas)<=0){
                if(intCntIte==0){
                    vecCabHExt.add(INT_TBL_RES_MARCENT, "Entrada");
                    vecCabHExt.add(INT_TBL_RES_MARCSAL, "Salida");
                    vecCabHExt.add(INT_TBL_RES_MARCENTHEX, "Entrada");
                    vecCabHExt.add(INT_TBL_RES_MARCSALHEX, "Salida");
                    vecCabHExt.add(INT_TBL_RES_OBSERV, "Obs.");
                    vecCabHExt.add(INT_TBL_RES_BTNOBSERV, "");
                    vecCabHExt.add(INT_TBL_RES_MODMARC, "");
                    vecCabHExt.add(INT_TBL_RES_MODHEXT, "");
                }else{
                    vecCabHExt.add((INT_TBL_RES_MARCENT +(intCntIte*8)), "Entrada");
                    vecCabHExt.add((INT_TBL_RES_MARCSAL +(intCntIte*8)), "Salida");
                    vecCabHExt.add((INT_TBL_RES_MARCENTHEX+(intCntIte*8)), "Entrada");
                    vecCabHExt.add((INT_TBL_RES_MARCSALHEX+(intCntIte*8)), "Salida");
                    vecCabHExt.add((INT_TBL_RES_OBSERV+(intCntIte*8)), "Obs.");
                    vecCabHExt.add((INT_TBL_RES_BTNOBSERV+(intCntIte*8)), "");
                    vecCabHExt.add((INT_TBL_RES_MODMARC+(intCntIte*8)), "");
                    vecCabHExt.add((INT_TBL_RES_MODHEXT+(intCntIte*8)), "");
                }
                intCntIte++;
                calFecDes.add(Calendar.DATE, 1);
            }
            objTblModHExt=new ZafTblMod();
            objTblModHExt.setHeader(vecCabHExt);
            tblDatExt.setModel(objTblModHExt);
            
            
        tblDatExt.setRowSelectionAllowed(true);
        tblDatExt.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        TableColumnModel tcm=tblDatExt.getColumnModel();
        tcm.getColumn(INT_TBL_RES_LINEA).setPreferredWidth(20);
        tcm.getColumn(INT_TBL_RES_DUPLICAR).setPreferredWidth(100);
        tcm.getColumn(INT_TBL_RES_CODTRA).setPreferredWidth(60);
        tcm.getColumn(INT_TBL_RES_NOMTRA).setPreferredWidth(250);
        tcm.getColumn(INT_TBL_RES_CODDEP).setPreferredWidth(60);
        tcm.getColumn(INT_TBL_RES_NOMDEP).setPreferredWidth(150);
        
        tblDatExt.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);



        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_RES_MODMARC);
        arlColHid.add(""+INT_TBL_RES_MODHEXT);
        arlColHid.add(""+(INT_TBL_RES_MODMARC+8));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+8));
        arlColHid.add(""+(INT_TBL_RES_MODMARC+16));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+16)); //3 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+24));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+24));//4 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+32));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+32));//5 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+40));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+40));//6 DIA
        arlColHid.add(""+(INT_TBL_RES_MODMARC+48));
        arlColHid.add(""+(INT_TBL_RES_MODHEXT+48));//7 DIA
        
        objTblModHExt.setSystemHiddenColumns(arlColHid, tblDatExt);
        arlColHid=null;
        
        

        Vector vecColEdi=new Vector();
        int intCntIte2=0;
        calFecDes=Calendar.getInstance();
        calFecDes.setTime(fmt.parse(sem.getFecDes()));        
        while(calFecDes.compareTo(calFecHas)<=0){        
            vecColEdi.add(""+ (INT_TBL_RES_MARCENT+(intCntIte2*8)));
            vecColEdi.add(""+ (INT_TBL_RES_MARCSAL+(intCntIte2*8)));
            vecColEdi.add(""+ (INT_TBL_RES_MARCENTHEX+(intCntIte2*8)));
            vecColEdi.add(""+ (INT_TBL_RES_MARCSALHEX+(intCntIte2*8)));
            vecColEdi.add(""+ (INT_TBL_RES_OBSERV+(intCntIte2*8)));
            vecColEdi.add(""+ (INT_TBL_RES_BTNOBSERV+(intCntIte2*8)));            
            
            intCntIte2++;
            calFecDes.add(Calendar.DATE, 1);        
        }
        vecColEdi.add(""+ (INT_TBL_RES_DUPLICAR));
        objTblModHExt.setColumnasEditables(vecColEdi);
        
        objTblFilCab = new ZafTblFilCab(tblDatExt);
        tcm.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
        
        
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        zafTblDocCelRenBut.setText("Duplicar");
        tcm.getColumn(INT_TBL_RES_DUPLICAR).setCellRenderer(zafTblDocCelRenBut);
        

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButDup=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcm.getColumn(INT_TBL_RES_DUPLICAR).setCellEditor(objTblCelEdiButDup);
            objTblCelEdiButDup.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     intFilSel=tblDatExt.getSelectedRow();
                        if (intFilSel!=-1)
                        { 
                            Vector vecReg=(Vector)((Vector)objTblModHExt.getRow(intFilSel)).clone();
                            int i=0;
                            while(i<7){
                                String strMarEnt=(String)tblDatExt.getValueAt(intFilSel, INT_TBL_RES_MARCENT+(8*i));
                                String strMarSal=(String)tblDatExt.getValueAt(intFilSel, INT_TBL_RES_MARCSAL+(8*i));
                                vecReg.setElementAt(strMarEnt, INT_TBL_RES_MARCENT+(8*i));
                                vecReg.setElementAt(strMarSal, INT_TBL_RES_MARCSAL+(8*i));
                                vecReg.setElementAt("", INT_TBL_RES_MARCENTHEX+(8*i));
                                vecReg.setElementAt("", INT_TBL_RES_MARCSALHEX+(8*i));
                                vecReg.setElementAt("", INT_TBL_RES_OBSERV+(8*i));
                                vecReg.setElementAt("", INT_TBL_RES_BTNOBSERV+(8*i));                            
                                vecReg.setElementAt("", INT_TBL_RES_MODMARC+(8*i));
                                vecReg.setElementAt("", INT_TBL_RES_MODHEXT+(8*i)); 
                                i++;
                            }
                            Vector vecData=(Vector)objTblModHExt.getData();
                            vecData.insertElementAt(vecReg, intFilSel+1);
                            /*CODIGO PARA BORRAR LA ULTIMA FILA VACIA INSERTADA */
                            Vector vecRegAct=((Vector)vecData.get(vecData.size()-1));
                            Integer intCodTra=(Integer)vecRegAct.get(INT_TBL_RES_CODTRA);
                            if(null == intCodTra ){
                                vecData.remove(vecRegAct);
                            }
                            /*CODIGO PARA BORRAR LA ULTIMA FILA VACIA INSERTADA */
                            objTblModHExt.setData(vecData);
                            tblDatExt.setModel(objTblModHExt);
                        }
                 }
                    
           });

        
        
        //objTblModHExt.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        objTblModHExt.setModoOperacion(ZafTblMod.INT_TBL_INS);
        
        objTblCelRenLbl=new ZafTblCelRenLbl();
        tcm.getColumn(INT_TBL_RES_MARCENT).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+8).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*2)).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*3)).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*4)).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*5)).setCellRenderer(objTblCelRenLbl);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*6)).setCellRenderer(objTblCelRenLbl);
        //tcm.getColumn(INT_TBL_RES_MARCENT+(6*7)).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                switch (objTblCelRenLbl.getColumnRender())
                {
                    case INT_TBL_RES_MARCENT:// primer dia semana
                        //if (!objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+6)).toString().equals("") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+6)).toString().equals("MF") ))                        
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        }
                        break;
                    case INT_TBL_RES_MARCENT+8:// segundo dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+8+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+8+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+8+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+8+6)).toString().equals("MF") ))
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;
                        case INT_TBL_RES_MARCENT+16://tercer dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+16+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+16+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+16+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+16+6)).toString().equals("MF") ))                            
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;                        
                        case INT_TBL_RES_MARCENT+24:// cuarto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+24+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+24+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+24+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+24+6)).toString().equals("MF") ))
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;  
                        case INT_TBL_RES_MARCENT+32:// quinto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+32+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+32+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+32+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+32+6)).toString().equals("MF") ))
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;   
                        case INT_TBL_RES_MARCENT+40:// sexto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+40+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+40+6))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+40+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+40+6)).toString().equals("MF") ))
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;   
                        case INT_TBL_RES_MARCENT+48:// sexto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+48+6)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+48+6))!=null &&( objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+48+6)).toString().equals("ME") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCENT+48+6)).toString().equals("MF") ))
                        {
                            objTblCelRenLbl.setBackground(Color.RED);
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);                        
                        } 
                        break;                        
                        
                }
            }
            
            
        });
        
        
        
        
        /*RENDERER MARCACION SALIDA*/
        
        objTblCelRenLbl2=new ZafTblCelRenLbl();
        tcm.getColumn(INT_TBL_RES_MARCSAL).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+8).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*2)).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*3)).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*4)).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*5)).setCellRenderer(objTblCelRenLbl2);
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*6)).setCellRenderer(objTblCelRenLbl2);
        //tcm.getColumn(INT_TBL_RES_MARCENT+(6*7)).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl2.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                switch (objTblCelRenLbl2.getColumnRender())
                {
                    case INT_TBL_RES_MARCSAL:// primer dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+5))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+5)).toString().equals("MF") ) )                                               
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);
                        }
                        break;
                    case INT_TBL_RES_MARCSAL+8:// segundo dia semana
//                        if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+8+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+8+5))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+8+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+8+5)).toString().equals("MF") ))                                                                        
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;
                        case INT_TBL_RES_MARCSAL+16://tercer dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+16+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+16+5))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+16+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+16+5)).toString().equals("MF") ) )                                                                           
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;                        
                        case INT_TBL_RES_MARCSAL+24:// cuarto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+24+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+24+5))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+24+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+24+5)).toString().equals("MF") ) )                                                                           
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;  
                        case INT_TBL_RES_MARCSAL+32:// quinto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+32+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+32+5))!=null && (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+32+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+32+5)).toString().equals("MF") ))                                                                            
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;   
                        case INT_TBL_RES_MARCSAL+40:// sexto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+40+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+40+5))!=null &&(objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+40+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+40+5)).toString().equals("MF") )   )                                                                         
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;   
                        case INT_TBL_RES_MARCSAL+48:// sexto dia semana
                        //if (objTblModHExt.getValueAt(objTblCelRenLbl2.getRowRender(), (INT_TBL_RES_MARCSAL+48+5)).toString().equals("M") )
                        if (objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+48+5))!=null &&(objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+48+5)).toString().equals("MS") || objTblModHExt.getValueAt(objTblCelRenLbl.getRowRender(), (INT_TBL_RES_MARCSAL+48+5)).toString().equals("MF") ) )                                                                           
                        {
                            objTblCelRenLbl2.setBackground(Color.RED);
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        }else{
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);                        
                        } 
                        break;                        
                        
                }
            }
        });        
        
        
        /*RENDERER MARCACION SALIDA*/
        
        
        /*EDITOR MARCACION ENTRADA*/
        objTblCelEdiTxtMarEnt=new ZafTblCelEdiTxt(tblDatExt);
        tcm.getColumn(INT_TBL_RES_MARCENT).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+8).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*2)).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*3)).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*4)).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*5)).setCellEditor(objTblCelEdiTxtMarEnt);
        tcm.getColumn(INT_TBL_RES_MARCENT+(8*6)).setCellEditor(objTblCelEdiTxtMarEnt);
        
        objTblCelEdiTxtMarEnt.addTableEditorListener(new ZafTableAdapter() {
            public void beforeEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                int intColSig=intCol+1;
                String strValMarEnt =(String)tblDatExt.getValueAt(intFil, intCol);
                String strValMarSal =(String)tblDatExt.getValueAt(intFil, intColSig);
                String strModMar    =(String)tblDatExt.getValueAt(intFil, intCol+6);
                if(!(strValMarEnt.equals("") ) && ((strModMar.equals("")) || strModMar.equals("MS")) ){
                    objTblCelEdiTxtMarEnt.setCancelarEdicion(true);
                }
            }
            public void afterEdit(ZafTableEvent evt){   
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                String strValCel=(String)tblDatExt.getValueAt(intFil,intCol);
                boolean booHoraValida=validarIngresoHoras(strValCel);
                String strValMar=(String)tblDatExt.getValueAt(intFil, (intCol+6));
                if(booHoraValida){                   

                    if(strValCel.equals("")){
                        if(strValMar.equals("MF")){
                            tblDatExt.setValueAt("MS", intFil, (intCol+6));
                        }else if(strValMar.equals("ME")){
                            tblDatExt.setValueAt("", intFil, (intCol+6));
                        }
                    }else{
                        if(strValMar.equals("")){
                            tblDatExt.setValueAt("ME", intFil, (intCol+6));
                        }else if(strValMar.equals("ME")){
                            tblDatExt.setValueAt("ME", intFil, (intCol+6));
                        }else if(strValMar.equals("MS")){
                            tblDatExt.setValueAt("MF", intFil, (intCol+6));
                        }
                    }
                }else{
                    tblDatExt.setValueAt("",intFil,intCol);
                    if(strValMar.equals("ME")){
                        tblDatExt.setValueAt("", intFil, (intCol+6));
                    }else if(strValMar.equals("MF")){
                        tblDatExt.setValueAt("MS", intFil, (intCol+6));
                    }                    
                }
            }
        
        });
        
        /*EDITOR MARCACION ENTRADA*/

        /*EDITOR MARCACION SALIDA*/        
        
        objTblCelEdiTxtMarSal=new ZafTblCelEdiTxt(tblDatExt);
        tcm.getColumn(INT_TBL_RES_MARCSAL).setCellEditor(objTblCelEdiTxtMarSal);
        tcm.getColumn(INT_TBL_RES_MARCSAL+8).setCellEditor(objTblCelEdiTxtMarSal);        
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*2)).setCellEditor(objTblCelEdiTxtMarSal);        
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*3)).setCellEditor(objTblCelEdiTxtMarSal);        
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*4)).setCellEditor(objTblCelEdiTxtMarSal);                
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*5)).setCellEditor(objTblCelEdiTxtMarSal);                
        tcm.getColumn(INT_TBL_RES_MARCSAL+(8*6)).setCellEditor(objTblCelEdiTxtMarSal);                
        
        
        objTblCelEdiTxtMarSal.addTableEditorListener(new ZafTableAdapter() {
            public void beforeEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                int intColAnt=intCol-1;
                String strValMarSal=(String)tblDatExt.getValueAt(intFil, intCol);
                String strValMarEnt=(String)tblDatExt.getValueAt(intFil, intColAnt);
                String strModMar    =(String)tblDatExt.getValueAt(intFil, intCol+5);
                if(!(strValMarSal.equals("")) && ((strModMar.equals("")) || strModMar.equals("ME"))){
                    objTblCelEdiTxtMarSal.setCancelarEdicion(true);
                }
            }
            public void afterEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                String strValCel=(String)tblDatExt.getValueAt(intFil,intCol);
                boolean booHoraValida=validarIngresoHoras(strValCel);
                String strValMar=(String)tblDatExt.getValueAt(intFil, (intCol+5));  
                if(booHoraValida){
                    if(strValCel.equals("")){
                        if(strValMar.equals("MF")){
                            tblDatExt.setValueAt("ME", intFil, (intCol+5));
                        }else if(strValMar.equals("MS")){
                            tblDatExt.setValueAt("", intFil, (intCol+5));
                        }
                    }else {               
                        if(strValMar.equals("")){
                            tblDatExt.setValueAt("MS", intFil, (intCol+5));
                        }else if(strValMar.equals("MS")){
                            tblDatExt.setValueAt("MS", intFil, (intCol+5));
                        }else if(strValMar.equals("ME")){
                            tblDatExt.setValueAt("MF", intFil, (intCol+5));
                        }  
                    }
                    //tblDatExt.getValueAt(intFil, intCol-1)
                    //tblDatExt.setValueAt("M", intFil, (intCol+5));                
                }else{
                    tblDatExt.setValueAt("",intFil,intCol);
                    if(strValMar.equals("MS")){
                        tblDatExt.setValueAt("", intFil, (intCol+5));
                    }else if(strValMar.equals("MF")){
                        tblDatExt.setValueAt("ME", intFil, (intCol+5));
                    }
                }
            }
        
        });        
        
        /*EDITOR MARCACION SALIDA*/                
        
        
        
        /*Editores de marcaciones Horas Extras*/
        
        objTblCelEdiTxtMarEntHext=new ZafTblCelEdiTxt(tblDatExt);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+8).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+(8*2)).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+(8*3)).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+(8*4)).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+(8*5)).setCellEditor(objTblCelEdiTxtMarEntHext);
        tcm.getColumn(INT_TBL_RES_MARCENTHEX+(8*6)).setCellEditor(objTblCelEdiTxtMarEntHext);
        
        objTblCelEdiTxtMarEntHext.addTableEditorListener(new ZafTableAdapter() {
            public void beforeEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                int intColSig=intCol+1;
                String strValMarEnt=(String)tblDatExt.getValueAt(intFil, intCol);
                String strValMarSal=(String)tblDatExt.getValueAt(intFil, intColSig);
                String strModMar    =(String)tblDatExt.getValueAt(intFil, intCol+5);
                if(!(strValMarEnt.equals("") || strValMarSal.equals("")) && !(strModMar.equals("M"))){
                    objTblCelEdiTxtMarEntHext.setCancelarEdicion(true);
                }
            }
            public void afterEdit(ZafTableEvent evt){   
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                String strValCel=(String)tblDatExt.getValueAt(intFil,intCol);    
                String strValCelOtr=(String)tblDatExt.getValueAt(intFil,intCol+1);    
                boolean booHoraValida=validarIngresoHoras(strValCel);
                boolean booMenorHoraEntrada=validaHorMay(strValCel,strValCelOtr );
                String strValMar=(String)tblDatExt.getValueAt(intFil, (intCol+5));                  
                if(booHoraValida && booMenorHoraEntrada){
                    tblDatExt.setValueAt("M", intFil, (intCol+5));
                }else{
                    tblDatExt.setValueAt("",intFil,intCol);
                    if(strValMar.equals("M")){
                        if(((String)tblDatExt.getValueAt( intFil, (intCol+1))).equals("")){
                            tblDatExt.setValueAt("", intFil, (intCol+5));
                        }
                    }
                   
                }					                
            }
        });
        
        objTblCelEdiTxtMarSalHExt=new ZafTblCelEdiTxt(tblDatExt);
        tcm.getColumn(INT_TBL_RES_MARCSALHEX).setCellEditor(objTblCelEdiTxtMarSalHExt);
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+8).setCellEditor(objTblCelEdiTxtMarSalHExt);        
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+(8*2)).setCellEditor(objTblCelEdiTxtMarSalHExt);        
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+(8*3)).setCellEditor(objTblCelEdiTxtMarSalHExt);        
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+(8*4)).setCellEditor(objTblCelEdiTxtMarSalHExt);                
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+(8*5)).setCellEditor(objTblCelEdiTxtMarSalHExt);                
        tcm.getColumn(INT_TBL_RES_MARCSALHEX+(8*6)).setCellEditor(objTblCelEdiTxtMarSalHExt);                
        
        
        objTblCelEdiTxtMarSalHExt.addTableEditorListener(new ZafTableAdapter() {
            public void beforeEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                int intColAnt=intCol-1;
                String strValMarSal=(String)tblDatExt.getValueAt(intFil, intCol);
                String strValMarEnt=(String)tblDatExt.getValueAt(intFil, intColAnt);
                String strModMar    =(String)tblDatExt.getValueAt(intFil, intCol+4);                
                if(!(strValMarEnt.equals("") || strValMarSal.equals(""))&& !(strModMar.equals("M"))){
                    objTblCelEdiTxtMarSalHExt.setCancelarEdicion(true);
                }
            }
            public void afterEdit(ZafTableEvent evt){
                int intFil=tblDatExt.getSelectedRow();
                int intCol=tblDatExt.getSelectedColumn();
                String strValCel=(String)tblDatExt.getValueAt(intFil,intCol);  
                String strValCelOtr=(String)tblDatExt.getValueAt(intFil,intCol-1);    
                boolean booHoraValida=validarIngresoHoras(strValCel);
                boolean booMayorHoraSalida=validaHorMay(strValCelOtr,strValCel );
                String strValMar=(String)tblDatExt.getValueAt(intFil, (intCol+4));                  
                if(booHoraValida && booMayorHoraSalida){
                    tblDatExt.setValueAt("M", intFil, (intCol+4));
                }else{
                    tblDatExt.setValueAt("",intFil,intCol);
                    if(strValMar.equals("M")){
                        if(((String)tblDatExt.getValueAt( intFil, (intCol-1))).equals("")){
                            tblDatExt.setValueAt("", intFil, (intCol+4));
                        }
                    }
                }
            }
        });  
        
        /*Editores de marcaciones Horas Extras*/
                
        /*Editor observacion*/
        
        
        objTblCelEdiTxtObs=new ZafTblCelEdiTxt(tblDatExt);
        tcm.getColumn(INT_TBL_RES_OBSERV).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+8).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+(8*2)).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+(8*3)).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+(8*4)).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+(8*5)).setCellEditor(objTblCelEdiTxtObs);
        tcm.getColumn(INT_TBL_RES_OBSERV+(8*6)).setCellEditor(objTblCelEdiTxtObs);
        
        
        objTblCelEdiTxtObs.addTableEditorListener(new ZafTableAdapter() {
            public void afterEdit(ZafTableEvent evt){
                   System.out.println("ssd");
            }            
        });
        
        
        
        
        /*Editor observacion*/
        
        /*POPUP MENU*/
        
        objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDatExt);
        objTblPopMnu.setInsertarFilasVisible(false);
        objTblPopMnu.setInsertarFilaVisible(false);

        objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
            public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                if(objTblPopMnu.isClickEliminarFila()){
                  /*if(!verificaSelEli()){
                     MensajeInf("NO SE PUEDE ELIMINAR. HAY DATOS QUE YA ESTAN PROCESADOS ");
                     objTblPopMnu.cancelarClick();
                   }*/
                }
            }
            public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                if (objTblPopMnu.isClickInsertarFila()) {
                    //Escriba aquí el código que se debe realizar luego de insertar la fila.
                    //System.out.println("afterClick: isClickInsertarFila");
                }                     
                else if (objTblPopMnu.isClickEliminarFila())
                {
                    System.out.println("afterClick: isClickEliminarFila");
                }
            }
        }); 
        
        /*POPUP MENU*/
        
        
        objTblCelRenButDG=new ZafTblCelRenBut();    
        tcm.getColumn(INT_TBL_RES_BTNOBSERV).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+8).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*2)).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*3)).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*4)).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*5)).setCellRenderer(objTblCelRenButDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*6)).setCellRenderer(objTblCelRenButDG);        
        
        objTblCelRenButDG.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt)
            {
                objTblCelRenButDG.setText("...");
            }
        });
        
        
        objTblCelEdiButGenDG=new ZafTblCelEdiButGen();        
        tcm.getColumn(INT_TBL_RES_BTNOBSERV).setCellEditor(objTblCelEdiButGenDG);
        
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+8).setCellEditor(objTblCelEdiButGenDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*2)).setCellEditor(objTblCelEdiButGenDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*3)).setCellEditor(objTblCelEdiButGenDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*4)).setCellEditor(objTblCelEdiButGenDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*5)).setCellEditor(objTblCelEdiButGenDG);
        tcm.getColumn(INT_TBL_RES_BTNOBSERV+(8*6)).setCellEditor(objTblCelEdiButGenDG);        
        
        objTblCelEdiButGenDG.addTableEditorListener(new ZafTableAdapter() {
            int intFilSel, intColSel;
            @Override
            public void beforeEdit(ZafTableEvent evt)
            {
                intFilSel=tblDatExt.getSelectedRow();
                intColSel=tblDatExt.getSelectedColumn();

            }
            @Override
            public void afterEdit(ZafTableEvent evt)
            {
                intFilSel=tblDatExt.getSelectedRow();
                intColSel=tblDatExt.getSelectedColumn();                
                String strObs=llamarVenObs( objTblModHExt.getValueAt(intFilSel, intColSel-1).toString() );
                objTblModHExt.setValueAt(strObs, intFilSel, intColSel-1);
            }
        });            
        

        //setEditable (false);
        

        }catch(ParseException pex){
            pex.printStackTrace();
        }
    }
    


public void setEditable(boolean editable) {
  if (editable==true){
    objTblModHExt.setModoOperacion(objTblModHExt.INT_TBL_INS);

 }else{  objTblModHExt.setModoOperacion(objTblModHExt.INT_TBL_NO_EDI); }
}
    
    
    private boolean agregarColTblDat()
    {
        
        int i, intNumFil, intNumColTblDat;


        ZafTblHeaColGrp objTblHeaColGrpEmp=null,objTblHeaColGrpEmpMarc=null, objTblHeaColGrpEmpHext=null;
        ZafTblHeaColGrp objTblHeaColGrpFecha=null;
        boolean blnRes=true;

        try
        {

            //intNumFil=objTblModHExt.getRowCountTrue();
            intNumColTblDat=objTblModHExt.getColumnCount();
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDatExt.getTableHeader();     
            objTblHeaGrp.setHeight(16*3);



            objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


            /*COLUMNAS QUE NO SE REPITEN*/
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_LINEA));
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_DUPLICAR));
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_CODTRA));
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_NOMTRA));
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_CODDEP));
            objTblHeaColGrpEmp.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_NOMDEP));
            /*COLUMNAS QUE NO SE REPITEN*/
            

            Semana sem=(Semana)cmbSemana.getSelectedItem();
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calFecDes=Calendar.getInstance();
            calFecDes.setTime(fmt.parse(sem.getFecDes()));
            Calendar calFecHas=Calendar.getInstance();
            calFecHas.setTime(fmt.parse(sem.getFecHas()));
            
            
            int itermarc=0;
             while(calFecDes.compareTo(calFecHas)<=0){
                 objTblHeaColGrpFecha=new ZafTblHeaColGrp(fmt.format(calFecDes.getTime()));
                 objTblHeaColGrpFecha.setHeight(16);

                objTblHeaColGrpEmpMarc=new ZafTblHeaColGrp("Marcaciones");
                objTblHeaColGrpEmpMarc.setHeight(16);
                objTblHeaColGrpEmpMarc.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MARCENT+(itermarc*8)));
                objTblHeaColGrpEmpMarc.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MARCSAL+(itermarc*8)));
                
                
                objTblHeaColGrpEmpHext=new ZafTblHeaColGrp("Horas suplementarias/extraordinarias");
                objTblHeaColGrpEmpHext.setHeight(16);
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MARCENTHEX+(itermarc*8)));
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MARCSALHEX+(itermarc*8)));
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_OBSERV+(itermarc*8)));
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_BTNOBSERV+(itermarc*8)));
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MODMARC+(itermarc*8)));                
                objTblHeaColGrpEmpHext.add(tblDatExt.getColumnModel().getColumn(INT_TBL_RES_MODHEXT+(itermarc*8)));                

                objTblHeaColGrpFecha.add(objTblHeaColGrpEmpMarc);
                objTblHeaColGrpFecha.add(objTblHeaColGrpEmpHext);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpFecha);
                calFecDes.add(Calendar.DATE, 1);
                itermarc++;
            }

        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    
    private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        int intCodDep=0;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodEmp.setText(vcoTra.getValueAt(1));
                        txtNomEmp.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        intCodDep=Integer.parseInt(vcoTra.getValueAt(4));
                        setearDepa(intCodDep);
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo trabajador".
                    if (vcoTra.buscar("a1.co_tra", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoTra.getValueAt(1));
                        txtNomEmp.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        intCodDep=Integer.parseInt(vcoTra.getValueAt(4));
                        setearDepa(intCodDep);
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodEmp.setText(vcoTra.getValueAt(1));
                            txtNomEmp.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                            intCodDep=Integer.parseInt(vcoTra.getValueAt(4));
                            setearDepa(intCodDep);
                        }
                        else{
                            txtCodEmp.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Apellido".
                    if (vcoTra.buscar("a1.tx_ape", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoTra.getValueAt(1));
                        txtNomEmp.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        intCodDep=Integer.parseInt(vcoTra.getValueAt(4));
                        setearDepa(intCodDep);
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodEmp.setText(vcoTra.getValueAt(1));
                            txtNomEmp.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                            intCodDep=Integer.parseInt(vcoTra.getValueAt(4));
                            setearDepa(intCodDep);
                        }
                        else{
                            //txtNomTra.setText(strNomTra);
                            txtNomEmp.setText("");
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
    
    
    private String obtenerBodSel(){
        String strBod="", strBodRet="";
        for(int i=0; i<tblDep.getRowCount();i++){
              if((Boolean)tblDep.getValueAt(i, INT_TBL_CHECK)){
                  strBod+=String.valueOf((Integer)tblDep.getValueAt(i, INT_TBL_COD_DEP))+",";
              }
        }
        if(strBod.length()>0){
            strBodRet=strBod.substring(0, strBod.length()-1);
        }
        return strBodRet;    
    }
    
    
    private void presentarTable(Calendar calFecHas, String strNomEmp, int intCodTraAnt,int intCodDep, String strNomDep, Map mapFech){
        
        Vector vecReg;
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        /*PRESENTAR EN EL TABLE*/
        Calendar calFecDesTab=Calendar.getInstance();
        try{
        calFecDesTab.setTime(fmt.parse(txtSemDes.getText()));                        

        int mayor=0;
        while(calFecDesTab.compareTo(calFecHas)<=0){
             Vector vecDia=(Vector)mapFech.get(intCodTraAnt+"-"+fmt.format(calFecDesTab.getTime()));
             if (vecDia.size()>mayor){
                 mayor=vecDia.size();
             }
            calFecDesTab.add(Calendar.DATE, 1);
        }


        for(int i=0; i<=(mayor-1); i++ ){
            calFecDesTab.setTime(fmt.parse(txtSemDes.getText()));  
            vecReg=new Vector();
            vecReg.add(INT_TBL_RES_LINEA,"");
            vecReg.add(INT_TBL_RES_DUPLICAR,"");
            vecReg.add(INT_TBL_RES_CODTRA,intCodTraAnt);                            
            vecReg.add(INT_TBL_RES_NOMTRA, strNomEmp);
            vecReg.add(INT_TBL_RES_CODDEP, intCodDep);
            vecReg.add(INT_TBL_RES_NOMDEP, strNomDep);
            int intInd=0;
            while(calFecDesTab.compareTo(calFecHas)<=0){
                 Vector vecDia=(Vector)mapFech.get(intCodTraAnt+"-"+fmt.format(calFecDesTab.getTime()));
                 if((vecDia.size()-1)>=i){
                    Vector vect=(Vector)vecDia.get(i);
                    vecReg.add(INT_TBL_RES_MARCENT+(intInd*8),vect.get(0));
                    vecReg.add(INT_TBL_RES_MARCSAL+(intInd*8),vect.get(1));
                    vecReg.add(INT_TBL_RES_MARCENTHEX+(intInd*8),vect.get(2));
                    vecReg.add(INT_TBL_RES_MARCSALHEX+(intInd*8),vect.get(3));
                    vecReg.add(INT_TBL_RES_OBSERV+(intInd*8),vect.get(4));
                    vecReg.add(INT_TBL_RES_BTNOBSERV+(intInd*8),"");
                    vecReg.add(INT_TBL_RES_MODMARC+(intInd*8),"");                    
                    vecReg.add(INT_TBL_RES_MODHEXT+(intInd*8),"");                    
                 }else{
                    vecReg.add(INT_TBL_RES_MARCENT+(intInd*8),"");
                    vecReg.add(INT_TBL_RES_MARCSAL+(intInd*8),"");
                    vecReg.add(INT_TBL_RES_MARCENTHEX+(intInd*8),"");
                    vecReg.add(INT_TBL_RES_MARCSALHEX+(intInd*8),"");                                 
                    vecReg.add(INT_TBL_RES_OBSERV+(intInd*8),"");
                    vecReg.add(INT_TBL_RES_BTNOBSERV+(intInd*8),"");                                    
                    vecReg.add(INT_TBL_RES_MODMARC+(intInd*8),"");                    
                    vecReg.add(INT_TBL_RES_MODHEXT+(intInd*8),"");                    
                 }
                 calFecDesTab.add(Calendar.DATE, 1);
                 intInd++;
             }
            vecDatHext.add(vecReg);
        }
        objTblModHExt.setData(vecDatHext);


        /*PRESENTAR EN EL TABLE*/   
        }catch(ParseException ex){
            ex.printStackTrace();
        }
    
    }
    
    private void cargarCons2(){
        String strSQL=  " select * \n"+
                        " from (select  traemp.co_emp,cabsol.fe_sol,tra.co_tra, tra.tx_nom ||' '||tra.tx_ape as nomemp, dep.co_dep,dep.tx_deslar, cast(asis.ho_ent as varchar) as ho_ent,cast(asis.ho_sal as varchar) as ho_sal,cast(cabsol.ho_soldes as varchar) as ho_soldes, cast(cabsol.ho_solhas as varchar) as ho_solhas, cabsol.tx_obs1 \n" +
                        " from tbm_cabsolhorsupext as cabsol\n" +
                        " inner join tbm_detsolhorsupext as detsol\n" +
                        " on (cabsol.co_emp=detsol.co_emp and cabsol.co_loc=detsol.co_loc and cabsol.co_tipdoc=detsol.co_tipdoc and cabsol.co_doc=detsol.co_doc)\n" +
                        " inner join tbm_tra as tra\n" +
                        " on(tra.co_tra=detsol.co_tra)\n" +
                        " inner join tbm_traemp as traemp\n" +
                        " on(traemp.co_tra=tra.co_tra and traemp.st_reg='A')\n" +
                        " inner join tbm_dep as dep\n" +
                        " on(dep.co_dep=traemp.co_dep)\n" +
                        " inner join tbm_cabconasitra as asis\n" +
                        " on (asis.co_tra=detsol.co_tra and asis.fe_dia=cabsol.fe_sol)\n" +
                        " where cabsol.fe_sol>='" +txtSemDes.getText()+"'"+
                        " and cabsol.fe_sol<='" +txtSemHas.getText()+"'"+
                        " and traemp.st_reg='A' \n"+                
                        //" and tra.co_tra in(576,600) "+                
        
                        " union \n "+

                        " select traemp.co_emp,asis.fe_dia as fe_sol, tra.co_tra,tra.tx_nom ||' '||tra.tx_ape as nomemp, dep.co_dep,dep.tx_deslar, case when  asis.ho_ent is null then '' else cast(asis.ho_ent as varchar ) end as ho_ent,case when asis.ho_sal is null then '' else cast(asis.ho_sal as varchar) end as ho_sal ,'' as ho_soldes,'' as ho_solhas,'' as tx_obs1 \n"+
                        " from tbm_cabconasitra as asis \n"+
                        " inner join tbm_tra as tra \n"+
                        " on(tra.co_tra=asis.co_tra) \n"+
                        " inner join tbm_traemp as traemp \n"+
                        " on(traemp.co_tra=tra.co_tra) \n"+
                        " inner join tbm_dep as dep \n"+
                        " on(traemp.co_dep=dep.co_dep) \n"+
                        " where asis.fe_dia>='"+txtSemDes.getText()+"' and asis.fe_dia<='"+txtSemHas.getText()+"' \n"+
                        " and traemp.st_reg='A' \n"+
                        //" and ((asis.ho_ent>='03:00' and asis.ho_ent<='07:15') or (asis.ho_sal>='17:30'))"+
                        //" and tra.co_tra in(576,600) \n"+
                        " and not exists(select * \n"+
                                    "   from tbm_cabsolhorsupext as c \n"+
                                    "   inner join tbm_detsolhorsupext as d \n"+
                                    " on (c.co_emp=d.co_emp and c.co_loc=d.co_loc and c.co_tipdoc=d.co_tipdoc and c.co_doc=d.co_doc) \n"+
                                    " where c.fe_sol=asis.fe_dia and d.co_tra=tra.co_tra \n"+
                                    "      ) \n"+
                        " ) as a\n "+
                        " WHERE 1=1 ";
                        

                                
        ResultSet rst=null;
        Statement stm=null;
        Connection con=null;
        Vector vecReg=null;
        Vector vecHmap=null;
        Map mapFech=new HashMap();
        
        try{
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if(txtCodEmp.getText()!=null && txtCodEmp.getText().length()>0){
                strSQL+=" AND a.co_tra="+txtCodEmp.getText();
            }
            String strBodegas=obtenerBodSel();
            if(strBodegas!=null && strBodegas.length()>0){
                strSQL+=" and a.co_dep in(" +strBodegas+")";
            }
            
            if(objParSis.getCodigoEmpresa()!=0){
                strSQL+=" and a.co_emp ="+objParSis.getCodigoEmpresa();
            }
            
            strSQL+=" order by  a.co_tra,a.fe_sol";
            
            stm=con.createStatement();
            rst=stm.executeQuery(strSQL);
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calFecDes=Calendar.getInstance();
            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
            
            Calendar calFecHas=Calendar.getInstance();
            calFecHas.setTime(fmt.parse(txtSemHas.getText()));
            
            Calendar calFecReg=Calendar.getInstance(), calFecRegAnt=Calendar.getInstance();
            int intCodTra=0, intCodTraAnt=0, intCodDep=0;
            int intCond=0;
            String strNomEmp="",strNomDep="";
            vecDatHext=new Vector();

            int intCont=0;
            while (rst.next()){
                if(intCont==0){
                    intCodTraAnt=rst.getInt("co_tra");
                    strNomEmp=rst.getString("nomemp");
                    intCodDep=rst.getInt("co_dep");
                    strNomDep=rst.getString("tx_deslar");
                }
                if(intCodTraAnt==rst.getInt("co_tra")){
                    vecHmap=new Vector();
                    calFecReg.setTime(fmt.parse( rst.getString("fe_sol")));
                    vecHmap.add(0, rst.getString("ho_ent"));
                    vecHmap.add(1, rst.getString("ho_sal"));
                    vecHmap.add(2, rst.getString("ho_soldes"));
                    vecHmap.add(3, rst.getString("ho_solhas"));     
                    vecHmap.add(4, rst.getString("tx_obs1"));
                    vecHmap.add(5, "");                    
                    if(mapFech.containsKey(intCodTraAnt+"-"+fmt.format(calFecReg.getTime()))){
                        //Vector vecAcu=new Vector();
                        Vector vecValue=(Vector)mapFech.get(intCodTraAnt+"-"+fmt.format(calFecReg.getTime()));
                        //vecAcu.add(vecValue);
                        vecValue.add(vecHmap);
                        mapFech.put(intCodTraAnt+"-"+fmt.format(calFecReg.getTime()), vecValue);
                    }else{
                        Vector vecAcu=new Vector();
                        vecAcu.add(vecHmap);
                        //mapFech.put(intCodTraAnt+"-"+fmt.format(calFecReg.getTime()), vecHmap);
                        mapFech.put(intCodTraAnt+"-"+fmt.format(calFecReg.getTime()), vecAcu);
                    } 
                }else{
                    calFecDes.setTime(fmt.parse(txtSemDes.getText()));
                    while(calFecDes.compareTo(calFecHas)<=0){
                        if(!mapFech.containsKey(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()))){
                            vecHmap=new Vector();
                            vecHmap.add(0, "");
                            vecHmap.add(1, "");
                            vecHmap.add(2, "");
                            vecHmap.add(3, "");
                            vecHmap.add(4, "");
                            vecHmap.add(5, "");                    
                            Vector vecAcu=new Vector();
                            vecAcu.add(vecHmap);                            
                            //mapFech.put(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()), vecHmap);
                            mapFech.put(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()), vecAcu);
                        }  
                        calFecDes.add(Calendar.DATE, 1);
                    }       
                    presentarTable(calFecHas, strNomEmp, intCodTraAnt, intCodDep, strNomDep, mapFech);

                    vecHmap=new Vector();
                    calFecReg.setTime(fmt.parse( rst.getString("fe_sol")));
                    vecHmap.add(0, rst.getString("ho_ent"));
                    vecHmap.add(1, rst.getString("ho_sal"));
                    vecHmap.add(2, rst.getString("ho_soldes"));
                    vecHmap.add(3, rst.getString("ho_solhas"));   
                    vecHmap.add(4, rst.getString("tx_obs1"));
                    vecHmap.add(5, "");                    
                    Vector vecAcu=new Vector();
                    vecAcu.add(vecHmap);
                    mapFech.put(rst.getInt("co_tra")+"-"+fmt.format(calFecReg.getTime()), vecAcu);
                    intCodTraAnt=rst.getInt("co_tra");
                    strNomEmp=rst.getString("nomemp");
                    intCodDep=rst.getInt("co_dep");
                    strNomDep=rst.getString("tx_deslar");                    
                }
                intCont++;
            }
            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
            while(calFecDes.compareTo(calFecHas)<=0){
                if(!mapFech.containsKey(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()))){
                    vecHmap=new Vector();
                    vecHmap.add(0, "");
                    vecHmap.add(1, "");
                    vecHmap.add(2, "");
                    vecHmap.add(3, "");
                    vecHmap.add(4, "");
                    vecHmap.add(5, "");                                        
                    Vector vecAcu=new Vector();
                    vecAcu.add(vecHmap);                    
                    //mapFech.put(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()), vecHmap);
                    mapFech.put(intCodTraAnt+"-"+fmt.format(calFecDes.getTime()), vecAcu);
                }                
                calFecDes.add(Calendar.DATE, 1);
            }
            
            /**************************************/
            presentarTable(calFecHas, strNomEmp, intCodTraAnt, intCodDep, strNomDep, mapFech);            
            tabGen.setSelectedIndex(1);
            
            //tblDatExt.setModel(objTblModHExt);
            
            if(paneFrozen!=null){
                panRes.remove(paneFrozen);
            }
            paneFrozen=new FrozenTablePaneHorExt(tblDatExt, 6);
            panRes.add(paneFrozen, java.awt.BorderLayout.CENTER);
            this.getContentPane().validate();
//            
            
            /*************************************/
            Set valores=mapFech.entrySet();
            //Set valores=(Set)mapFech.values();
            Iterator itvalor=valores.iterator();
            while(itvalor.hasNext()){
                //Map.Entry<String,Vector> entrada=(Map.Entry)itvalor.next();
                //Vector vecValor=(Vector)entrada.getValue();
                //if(vecValor.size()>1){
                  //  System.out.println(""+vecValor);
                //}
                System.out.println(itvalor.next());
            }
        }catch(Exception ex ){
            ex.printStackTrace();
        }
    }
    
//    private void cargarCons(){
//    
//        String strSQL=  " select  cabsol.fe_sol,tra.co_tra, tra.tx_nom ||' '||tra.tx_ape as nomemp, dep.co_dep,dep.tx_deslar, asis.ho_ent,asis.ho_sal,cabsol.ho_soldes, cabsol.ho_solhas\n" +
//                        " from tbm_cabsolhorsupext as cabsol\n" +
//                        " inner join tbm_detsolhorsupext as detsol\n" +
//                        " on (cabsol.co_emp=detsol.co_emp and cabsol.co_loc=detsol.co_loc and cabsol.co_tipdoc=detsol.co_tipdoc and cabsol.co_doc=detsol.co_doc)\n" +
//                        " inner join tbm_tra as tra\n" +
//                        " on(tra.co_tra=detsol.co_tra)\n" +
//                        " inner join tbm_traemp as traemp\n" +
//                        " on(traemp.co_tra=tra.co_tra and traemp.st_reg='A')\n" +
//                        " inner join tbm_dep as dep\n" +
//                        " on(dep.co_dep=traemp.co_dep)\n" +
//                        " inner join tbm_cabconasitra as asis\n" +
//                        " on (asis.co_tra=detsol.co_tra and asis.fe_dia=cabsol.fe_sol)\n" +
//                        " where cabsol.fe_sol>='" +txtSemDes.getText()+"'"+
//                        " and cabsol.fe_sol<='" +txtSemHas.getText()+"'";
//        ResultSet rst=null;
//        Statement stm=null;
//        Connection con=null;
//        Vector vecReg=null;
//        
//        try{
//            
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            //System.out.println(obtenerBodSel());
//            
//            if(txtCodEmp.getText()!=null && txtCodEmp.getText().length()>0){
//                strSQL+=" and tra.co_tra="+txtCodEmp.getText();
//            }
//            String strBodegas=obtenerBodSel();
//            if(strBodegas!=null && strBodegas.length()>0){
//                strSQL+=" and dep.co_dep in(" +strBodegas+")";
//            }
//            strSQL+=" order by  tra.co_tra,cabsol.fe_sol";
//            
//            stm=con.createStatement();
//            rst=stm.executeQuery(strSQL);
//
//            //Semana sem=(Semana)cmbSemana.getSelectedItem();
//            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
//            Calendar calFecDes=Calendar.getInstance();
//            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
//            
//            Calendar calFecHas=Calendar.getInstance();
//            calFecHas.setTime(fmt.parse(txtSemHas.getText()));
//            
//            Calendar calFecReg=Calendar.getInstance();
//            int intCodTra=0;
//            int intCond=0;
//            vecDatHext=new Vector();
//            while(rst.next()){
//                calFecReg.setTime(fmt.parse( rst.getString("fe_sol")));
//                if(intCodTra!=rst.getInt("co_tra")){
//                   if(vecReg!=null && vecReg.size()>0){
//                        while(calFecDes.compareTo(calFecHas)<=0){ 
//                            vecReg.add(INT_TBL_RES_MARCENT+(intCond*6),"");
//                            vecReg.add(INT_TBL_RES_MARCSAL+(intCond*6),"");
//                            vecReg.add(INT_TBL_RES_MARCENTHEX+(intCond*6), "");
//                            vecReg.add(INT_TBL_RES_MARCSALHEX+(intCond*6), "");
//                            vecReg.add(INT_TBL_RES_OBSERV+(intCond*6), "");
//                            vecReg.add(INT_TBL_RES_BTNOBSERV+(intCond*6), "");                        
//                            calFecDes.add(Calendar.DATE, 1);
//                            intCond++;                    
//                        }
//                        vecDatHext.add(vecReg);
//                        calFecDes.setTime(fmt.parse(txtSemDes.getText()));
//                        intCond=0;
//                   }
//                   intCodTra=rst.getInt("co_tra");
//                   vecReg=new Vector();
//                   vecReg.add(INT_TBL_RES_LINEA,"");
//                   vecReg.add(INT_TBL_RES_DUPLICAR,"");
//                   vecReg.add(INT_TBL_RES_CODTRA ,rst.getInt("co_tra"));
//                   vecReg.add(INT_TBL_RES_NOMTRA ,rst.getString("nomemp"));
//                   vecReg.add(INT_TBL_RES_CODDEP,rst.getInt("co_dep"));
//                   vecReg.add(INT_TBL_RES_NOMDEP,rst.getString("tx_deslar"));            
//                }
//                while(calFecDes.compareTo(calFecHas)<=0){
//                    if(calFecDes.compareTo(calFecReg)==0){
//                       vecReg.add(INT_TBL_RES_MARCENT+(intCond*6),rst.getString("ho_ent"));
//                       vecReg.add(INT_TBL_RES_MARCSAL+(intCond*6),rst.getString("ho_sal"));
//                       vecReg.add(INT_TBL_RES_MARCENTHEX+(intCond*6), rst.getString("ho_soldes"));
//                       vecReg.add(INT_TBL_RES_MARCSALHEX+(intCond*6), rst.getString("ho_solhas"));
//                       vecReg.add(INT_TBL_RES_OBSERV+(intCond*6), "");
//                       vecReg.add(INT_TBL_RES_BTNOBSERV+(intCond*6), "");
//                       calFecDes.add(Calendar.DATE, 1);
//                       intCond++;
//                       break;
//                    }else if(calFecDes.compareTo(calFecReg)<0){
//                       vecReg.add(INT_TBL_RES_MARCENT+(intCond*6),"");
//                       vecReg.add(INT_TBL_RES_MARCSAL+(intCond*6),"");
//                       vecReg.add(INT_TBL_RES_MARCENTHEX+(intCond*6), "");
//                       vecReg.add(INT_TBL_RES_MARCSALHEX+(intCond*6), "");
//                       vecReg.add(INT_TBL_RES_OBSERV+(intCond*6), "");
//                       vecReg.add(INT_TBL_RES_BTNOBSERV+(intCond*6), "");                        
//                    }/*else if(calFecDes.compareTo(calFecReg)>0){//Cuando el empleado tiene dos horas extras en el mismo dia(en la mañana y en la tarde-noche)
//                       vecReg=new Vector();
//                       vecReg.add(INT_TBL_RES_LINEA,"");
//                       vecReg.add(INT_TBL_RES_DUPLICAR,"");
//                       vecReg.add(INT_TBL_RES_CODTRA ,rst.getInt("co_tra"));
//                       vecReg.add(INT_TBL_RES_NOMTRA ,rst.getString("nomemp"));
//                       vecReg.add(INT_TBL_RES_CODDEP,rst.getInt("co_dep"));
//                       vecReg.add(INT_TBL_RES_NOMDEP,rst.getString("tx_deslar"));
//                       intCond--;
//                       vecReg.add(INT_TBL_RES_MARCENT+(intCond*6),rst.getString("ho_ent"));
//                       vecReg.add(INT_TBL_RES_MARCSAL+(intCond*6),rst.getString("ho_sal"));
//                       vecReg.add(INT_TBL_RES_MARCENTHEX+(intCond*6), rst.getString("ho_soldes"));
//                       vecReg.add(INT_TBL_RES_MARCSALHEX+(intCond*6), rst.getString("ho_solhas"));
//                       vecReg.add(INT_TBL_RES_OBSERV+(intCond*6), "");
//                       vecReg.add(INT_TBL_RES_BTNOBSERV+(intCond*6), "");
//                       break;
//                    }*/                    
//                    calFecDes.add(Calendar.DATE, 1);
//                    intCond++;                    
//                }
//            }
//            if(vecReg!=null && vecReg.size()>0){
//                while(calFecDes.compareTo(calFecHas)<=0){ 
//                    vecReg.add(INT_TBL_RES_MARCENT+(intCond*6),"");
//                    vecReg.add(INT_TBL_RES_MARCSAL+(intCond*6),"");
//                    vecReg.add(INT_TBL_RES_MARCENTHEX+(intCond*6), "");
//                    vecReg.add(INT_TBL_RES_MARCSALHEX+(intCond*6), "");
//                    vecReg.add(INT_TBL_RES_OBSERV+(intCond*6), "");
//                    vecReg.add(INT_TBL_RES_BTNOBSERV+(intCond*6), "");                        
//                    calFecDes.add(Calendar.DATE, 1);
//                    intCond++;                    
//                }
//                vecDatHext.add(vecReg);
//            }
//            objTblModHExt.setData(vecDatHext);
//            tblDatExt.setModel(objTblModHExt);
//            
//            rst.close();
//            stm.close();
//            con.close();
//
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }catch(ParseException ex2){
//            ex2.printStackTrace();
//        }catch(Exception ex3){
//            ex3.printStackTrace();
//        }
//        
//    }


    
    private void mensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }


    //public boolean esHoraTraValidaCruce(int intHorDes, int intMinDes, int intHorHas, int intMinHas, int dia) throws SQLException {
    public boolean esHoraTraValidaCruce(String strtHorDes, String strHorHas, int dia, int intCodTra) throws SQLException {
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        boolean blnCanSol = false, booSolapa=false;
        boolean blnRetorno = true;
        String strHoEnHo="", strHoSalHo="";
        Date horaDesActDt,horaFinActDt,horaDesRecDt,horaFinRecDt;
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                //for (int i = 0; i < tblDatExt.getRowCount(); i++) {
                    //if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {
                        strSQL = " select distinct a2.ho_ent, a2.ho_sal "
                                + " from tbm_traemp a1 "
                                + " inner join tbm_dethortra a2 on (a1.co_hor=a2.co_hor) "
                                + " where a1.co_tra= " + intCodTra
                                + " and   a2.ne_dia= " + dia
                                + " and   a1.st_Reg='A' ";
                        rstLoc = stmLoc.executeQuery(strSQL);
                        DateFormat df= new SimpleDateFormat("HH:mm");
                        while (rstLoc.next()) {
                            strHoEnHo=rstLoc.getString("ho_ent");
                            strHoSalHo=rstLoc.getString("ho_sal");   
                            horaDesActDt=df.parse(strtHorDes);//HORA EXTRA DESDE 
                            horaFinActDt=df.parse(strHorHas);// HORA EXTRA HASTA 
                            horaDesRecDt=df.parse(strHoEnHo);// HORA DEL HORARIO DEL DIA (BASE)
                            horaFinRecDt=df.parse(strHoSalHo);// HORA DEL HORARIO DEL DIA (BASE)
                            
                            if((horaDesRecDt.compareTo(horaDesActDt))<0 && (horaFinRecDt.compareTo(horaDesActDt)>0)){
                                booSolapa=true;
                                break;
                            }else if((horaDesRecDt.compareTo(horaDesActDt)>0) && (horaDesRecDt.compareTo(horaFinActDt)<0)){
                                booSolapa=true;    
                                break;
                            }
                            
                            
//                            
//                            boolean booTieneCruzeHoras = validarHoraNoAdmiteCruze(rstLoc.getString("ho_ent"), rstLoc.getString("ho_sal"), Integer.parseInt(cboHHDes.getSelectedItem().toString()), Integer.parseInt(cboMMDes.getSelectedItem().toString()), Integer.parseInt(cboHHHas.getSelectedItem().toString()), Integer.parseInt(cboMMHas.getSelectedItem().toString()));
//                            if (booTieneCruzeHoras) {
//                                blnCanSol = true;
//                                blnRetorno = false;
//                                i = tblDat.getRowCount();
//                                break;
//                            }
//                            if (blnCanSol) {
//                                blnRetorno = false;
//                                break;
//                            }
                        }
            }
            con.close();
            con = null;

        } catch (java.sql.SQLException Evt) {
            booSolapa = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            booSolapa = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rstLoc.close();
                rstLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
                stmLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                con.close();
                con = null;
            } catch (Throwable ignore) {
            }
        }
        return booSolapa;
    }
    
    
    public boolean esHoraValida(int intHorDes, int intMinDes, int intHorHas, int intMinHas, int dia) {
        boolean booRetorno = true;

        if (dia != 7 && dia != 1) {// lunes a viernes
            if (intHorDes >= 4 && intHorDes <= 10) {
                if (intHorHas <= 10) {//Se agrega validacion para que pueda crear una solicitud hasta las 10am
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    } else if (intHorHas < 10) {
                        if (intHorDes == intHorHas) {
                            if (intMinHas <= intMinDes) {
                                booRetorno = false;
                            }
                        }
                    } else if (intHorHas == 10) {
                        if (intMinHas > 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }
            } else if (intHorDes >= 17 && intHorDes <= 23) {
                if (intHorHas <= 23 && intHorHas != 0) {
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    } else if (intHorDes == intHorHas) {
                        if (intMinDes >= intMinHas) {
                            booRetorno = false;
                        }
                    }
                } else if (intHorHas == 0) {
                    if (intMinHas != 0) {
                        booRetorno = false;
                    }
                } else {
                    booRetorno = false;
                }

            } else {
                booRetorno = false;
            }
        } else if (dia == 7) {//Dias sabados 
            //Se agregó validación para que se pueda laborar como horas extra hasta las 8pm. tony
            //Se agrega validación para trabajar desde las 2y 30 hasta las 8
            //if (intHorDes >= 2 && intHorDes <= 8) {// COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
            if (intHorDes >= 4 && intHorDes <= 23) {
                if (intHorDes == 4) {
                    if (intMinDes < 0) {
                        booRetorno = false;
                    }
                }
                //if (intHorHas <= 8) {// COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
                if (intHorHas <= 23) {
                    if (intHorDes > intHorHas) {
                        booRetorno = false;
                    //} else if (intHorHas < 8) {// COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
                    } else if (intHorHas < 23) {
                        if (intHorDes == intHorHas) {
                            if (intMinHas <= intMinDes) {
                                booRetorno = false;
                            }
                        }
                    //} else if (intHorHas == 8) {// COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
                    } else if (intHorHas == 23) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        // COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
                        /*if (intMinHas > 30) { //tony validacion para que se pueda asignar hasta as 8y30
                            booRetorno = false;
                        }*/
                        // COMENTADO POR INGRESO SEMANAL HORAS EXTRAS
                    }
                } else {
                    booRetorno = false;
                }
            } /*else if (intHorDes >= 12 && intHorDes <= 23) {///
                if (intHorDes == 12) {
                    if (intMinDes < 30) {
                        booRetorno = false;
                    }
                }
                if (intHorHas <= 23 ) {
                    if (intHorHas < 23) {
                        if (intHorDes > intHorHas) {
                            booRetorno = false;
                        } else if (intHorDes == intHorHas) {
                            if (intMinDes >= intMinHas) {
                                booRetorno = false;
                            }
                        }

                    } else if (intHorHas == 23) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        if (intMinHas != 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }

            } */
            else {
                booRetorno = false;
            }
        } else if (dia == 1) {// Domingo
            //Se agregó validación para que los días domingos se puedan asignar para laborar como horas extras. tony
            if (intHorDes >= 8 && intHorDes <= 23) {
                if (intHorHas <= 23 && intHorHas >= 8/*&& intHorHas!=0*/) {
                    if (intHorHas < 23) {
                        if (intHorDes > intHorHas) {
                            booRetorno = false;
                        } else if (intHorDes == intHorHas) {
                            if (intMinDes >= intMinHas) {
                                booRetorno = false;
                            }
                        }

                    } else if (intHorHas == 23) {
                        if (intHorDes == intHorHas) {
                            booRetorno = false;
                        }
                        if (intMinHas != 0) {
                            booRetorno = false;
                        }
                    }
                } else {
                    booRetorno = false;
                }
            } else {
                booRetorno = false;
            }
        }
        return booRetorno;
    }    
    
    
    public boolean validarDatos(){
        boolean booRet=false;
        Calendar calFecDes=Calendar.getInstance();
        Calendar calFecHas=Calendar.getInstance();
        int intCont=0,intCodTra=0, intDia=0;
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        String[] arrHorDes,arrHorHas;
        String strNomTra="";
        
        try{
            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
            calFecHas.setTime(fmt.parse(txtSemHas.getText()));
            String strValModExt,strHEntExt,strHSalExt, strHEnt,strHSal;
            if(!(tblDatExt.getRowCount()>0)){
                mensajeInf("Debe consultar primero, para poder registrar los datos");
                return true;
            }
            
            while(calFecDes.compareTo(calFecHas)<=0){
                for(int i=0;i<tblDatExt.getRowCount();i++){
                    if (tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA) != null) {
                        strValModExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MODHEXT+(8*intCont));
                        strHEntExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCENTHEX+(8*intCont));
                        strHSalExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCSALHEX+(8*intCont));
                        strHEnt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCENT+(8*intCont));
                        strHSal=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCSAL+(8*intCont));
                        intCodTra=(Integer)tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA);
                        strNomTra=(String)tblDatExt.getValueAt(i, INT_TBL_RES_NOMTRA);
                        if(strValModExt!=null && strValModExt.equals("M")){//VERIFICA QUE HAYA SIDO MODIFICADO Y NO SEA UNA DE LA BASE
                            if(strHEntExt!=null && !strHEntExt.equals("")){// VERIFCA QUE TENGA HORA EXTRA DE ENTRADA VALIDA
                                if(strHSalExt!=null && !strHSalExt.equals("")){// VERIFCA QUE TENGA HORA EXTRA DE SALIDA VALIDA
                                    if((strHEnt!=null && strHEnt.equals(""))||(strHSal!=null && strHSal.equals(""))){
                                        mensajeInf("El empleado "+intCodTra+" "+strNomTra+" presenta horas extras pero no tiene marcaciones\n Por favor ingrese la marcación y vuelva a intentarlo");
                                        booRet=true;
                                        break;
                                    }
                                    intDia=calFecDes.get(Calendar.DAY_OF_WEEK);                             
                                    if(esHoraTraValidaCruce(strHEntExt, strHSalExt, intDia, intCodTra)){
                                        mensajeInf("<HTML> <FONT COLOR=\"blue\">Algunos de los trabajadores seleccionados no se les puede asignar una solicitud de horas extras porque estan dentro de su horario laboral.</FONT>.</HTML>");
                                        booRet=true;
                                        break;
                                    }
                                    arrHorDes=strHEntExt.split(":");
                                    arrHorHas=strHSalExt.split(":");
                                    if(!esHoraValida(Integer.parseInt(arrHorDes[0]), Integer.parseInt(arrHorDes[1]), Integer.parseInt(arrHorHas[0]), Integer.parseInt(arrHorHas[1]), intDia)){
                                        mensajeInf("<HTML> <FONT COLOR=\"blue\">Las horas pueden estar dentro de: 04:00 AM - 10:00 AM y 17:30 PM - 00:00 AM, los SABADOS DE 02:30 AM - 08:00 AM Y 12:30 A 23:00 y los DOMINGOS DE 08:00 AM a 23:00 PM. DEPENDIENDO DEL HORARIO DEL TRABAJADOR.</FONT>.</HTML>");
                                        booRet=true;
                                        break;
                                    }
                                }else{
                                        mensajeInf("El empleado "+intCodTra+" "+strNomTra+" presenta horas extras de entrada pero no horas extras de salida \n Por favor ingrese la hora de salida y vuelva a intentarlo");                                
                                }
                            }
                        }
                    }
                }
                if(booRet){
                    break;
                }
                intCont++;
                calFecDes.add(Calendar.DATE, 1);
            }
        }catch(ParseException pex){
            pex.printStackTrace();
        }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        return booRet;
    }  
    
    
    private boolean verificarFaltaEmpleado(Connection con,int intCodTra, String strFecha){
        boolean booFal=true;
        Statement stmAsis=null;
        ResultSet rsAsis=null;
        String strSelect= "select ho_ent, ho_sal \n"+
                        " from tbm_cabconasitra "+
                        " where co_tra="+intCodTra+"\n" +
                        " and fe_dia='"+strFecha+"'";
        try{
            stmAsis=con.createStatement();
            rsAsis=stmAsis.executeQuery(strSelect);
            if(rsAsis.next()){
                if(rsAsis.getString("ho_ent")!=null && !rsAsis.getString("ho_ent").equalsIgnoreCase("")
                     && rsAsis.getString("ho_sal")!=null && !rsAsis.getString("ho_sal").equalsIgnoreCase("")){
                    booFal=false;
                }else{
                    booFal=true;
                }
            }
            rsAsis.close();
            rsAsis=null;
            stmAsis.close();
            stmAsis=null;
        }catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return booFal;
    }
    
    
    
    private boolean registrarFaltasMarcacionesIncompletas(Vector vecMarc, Connection con){
        boolean booRet=false;
        Vector vecReg;
        String strMod, strSql="",strMarcEnt,strMarcSal,strFecJus;
        int intCodTra=0;
        Statement stmLoc=null;
        
        try{
            stmLoc=con.createStatement();
            for(int i=0; i<vecMarc.size();i++){
                vecReg=(Vector)vecMarc.get(i);
                strMod=(String)vecReg.get(4);
                strMarcEnt=(String)vecReg.get(2);
                strMarcSal=(String)vecReg.get(3);
                intCodTra=(Integer)vecReg.get(0);
                strFecJus=(String)vecReg.get(1);
                if(strMod!=null && !strMod.equals("MF")){//MARCACION INCOMPLETA
                        strSql = "";
                        strSql += "UPDATE tbm_cabconasitra SET";
                        strSql += " ho_entjus= '" + strMarcEnt + "' , ";
                        strSql += " ho_saljus= '" + strMarcSal + "' , ";
                        strSql += " st_jusmarinc= 'S', ";
                        //strSql += " co_motjusmarinc= " + strCoMot + " , ";
                        //strSql += " tx_obsjusmarinc= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                        strSql += " fe_jusmarinc= current_timestamp" + " , ";
                        strSql += " co_usrjusmarinc= " + objParSis.getCodigoUsuario() + " , ";
                        //strSql += " tx_comjusmarinc= " + objUti.codificar(objParSis.getDireccionIP());
                        strSql += " tx_comjusmarinc= '" + objParSis.getDireccionIP()+"'";
                        strSql += " WHERE";
                        strSql += " co_tra= " + intCodTra;
                        strSql += " and fe_dia= '" + strFecJus+"'";
                        System.out.println("act marcacion incom:" + strSql);
                        stmLoc.executeUpdate(strSql);                    

                }else if(strMod!=null && strMod.equals("MF")){//MARCACION POR FALTA
                        if(verificarFaltaEmpleado(con, intCodTra, strFecJus)){//JUSTIFICAR SOLAMENTE CUANDO HAYA FALTADO ES DECIR NO TENGA MARCACION DE ENTRADA Y SALIDA EN LA BASE.
                            strSql = "";
                            strSql += "UPDATE tbm_cabconasitra SET";
                            strSql += " ho_entjus= '" + strMarcEnt + "' , ";
                            strSql += " ho_saljus= '" + strMarcSal + "' , ";
                            strSql += " st_jusfal= 'S', ";
                            //strSql += " co_motjusmarinc= " + strCoMot + " , ";
                            //strSql += " tx_obsjusmarinc= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                            strSql += " fe_jusFal= current_timestamp" + " , ";
                            strSql += " co_usrJusFal= " + objParSis.getCodigoUsuario() + " , ";
                            //strSql += " tx_comJusFal= " + objUti.codificar(objParSis.getDireccionIP());
                            strSql += " tx_comJusFal= '" +objParSis.getDireccionIP()+"'";
                            strSql += " WHERE";
                            strSql += " co_tra= " + intCodTra;
                            strSql += " and fe_dia= '" + strFecJus+"'";
                            System.out.println("act marcacion falta:" + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                }
            }
            booRet=true;
            stmLoc.close();
            stmLoc=null;
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }        
        return booRet;
    }
    
    public boolean registrarSolicitudes(){
        boolean booRet=true, booHorSol=false;
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calFecDes=Calendar.getInstance();
        Calendar calFecHas=Calendar.getInstance();
        String strValModExt="",strHEntExt="", strHSalExt="", strCalFecDes="";
        int intCodDep=0, intCodTra=0,intCont=0;
        String strObs="";
        Map mapSolHex=new HashMap();
        Vector vecReg, vecRegModMar= new Vector(), vecMarc=new Vector();
        Connection con;
        try{
            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
            calFecHas.setTime(fmt.parse(txtSemHas.getText()));
            while(calFecDes.compareTo(calFecHas)<=0){
                strCalFecDes=fmt.format(calFecDes.getTime());
                for(int i=0;i<tblDatExt.getRowCount();i++){
                    if (tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA) != null) {
                        strValModExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MODHEXT+(8*intCont));
                        strHEntExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCENTHEX+(8*intCont));
                        strHSalExt=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCSALHEX+(8*intCont));
                        intCodDep=(Integer)tblDatExt.getValueAt(i, INT_TBL_RES_CODDEP);
                        intCodTra=(Integer)tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA);
                        strObs=(String)tblDatExt.getValueAt(i, INT_TBL_RES_OBSERV+(8*intCont));
                        if(!((String)tblDatExt.getValueAt(i, INT_TBL_RES_MODMARC+(8*intCont))).equals("")){
                            vecRegModMar=new Vector();
                            vecRegModMar.add(intCodTra);
                            //vecRegModMar.add(calFecDes);
                            vecRegModMar.add(strCalFecDes);
                            vecRegModMar.add((String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCENT+(8*intCont)));
                            vecRegModMar.add((String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCSAL+(8*intCont)));
                            vecRegModMar.add((String)tblDatExt.getValueAt(i, INT_TBL_RES_MODMARC+(8*intCont)));
                            vecMarc.add(vecRegModMar);
                            //tblDatExt.setValueAt("", i, INT_TBL_RES_MODMARC+(8*intCont));
                        }      
                        if(strHEntExt!=null && !strHEntExt.equals("") && strHSalExt!=null && !strHSalExt.equals("")){
                            vecReg=new Vector();
                            vecReg.add(0, intCodTra);
                            vecReg.add(1, intCodDep);
                            vecReg.add(2, obtenerEmpPag(intCodTra));
                            vecReg.add(3,strObs);
                            if(strValModExt.equalsIgnoreCase("M")){                        
                                if(mapSolHex.containsKey(intCodDep+"+"+strCalFecDes+"+"+strHEntExt+"+"+strHSalExt)){
                                    Vector vecValue=(Vector)mapSolHex.get(intCodDep+"+"+strCalFecDes+"+"+strHEntExt+"+"+strHSalExt);
                                    vecValue.add(vecReg);
                                }else{                           
                                    Vector vecAcu=new Vector();
                                    vecAcu.add(vecReg);
                                    mapSolHex.put(intCodDep+"+"+strCalFecDes+"+"+strHEntExt+"+"+strHSalExt, vecAcu);
                                }
                                //tblDatExt.setValueAt("",i, INT_TBL_RES_MODHEXT+(8*intCont));// despues de enviar a grabar se borra el estado de modificado
                            }
                        }
                    }
                }
                calFecDes.add(Calendar.DATE, 1);
                if(calFecDes.get(Calendar.DATE)==29){
                    System.out.println("ddkd");
                }
                intCont++;
            }
            //booHorSol=verificarHoraSolapxFechaBase(mapSolHex);
            //if(!booHorSol){
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if(registrarFaltasMarcacionesIncompletas(vecMarc, con)){
                booRet=insertarReg(mapSolHex, con);
                if(booRet){
                    
                    con.commit();
                    con.close();
                    con=null;
                    cargarCons2();
                }else{
                    con.rollback();
                    con.close();
                    con=null;
                }
            }else{
                booRet=false;
                con.rollback();
                con.close();
                con=null;                
            }
        }catch(ParseException pex){
            pex.printStackTrace();
            booRet=false;
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    
    /**
     * Verifica si una hora tiene cruze o se encuentra en el rango de otra hora.
     * @param strHorDes hora desde Registro de la base.
     * @param strHorHas hora hasta Registro de la base.
     * @param intHorDes hora desde pantalla.
     * @param intMinDes minuto desde pantalla.
     * @param intHorHas hora hasta pantalla.
     * @param intMinHas minuto hasta pantalla.
     * @return boolean indica si la hora tiene un cruce con otra
     */
    public boolean validarHoraNoAdmiteCruze(String strHorDes, String strHorHas, int intHorDes, int intMinDes, int intHorHas, int intMinHas) {
        Boolean booRet = false;
        boolean booCru = false;
        String[] strArrDes, strArrHas;
        strArrDes = strHorDes.split(":");
        strArrHas = strHorHas.split(":");
        int intHorDesDb = Integer.parseInt(strArrDes[0]);
        int intHorHasDb = Integer.parseInt(strArrHas[0]);


        if (intHorHasDb == 0) {
            intHorHasDb = 24;
        }
        if (intHorHas == 0) {
            intHorHas = 24;
        }
        List lstHorRan = new ArrayList();
        List lstMinRan = new ArrayList();

        if (intHorDesDb == intHorHasDb) {//Caso en el que las hora desde y hora hasta es la misma es decir HD=17(8):15 - HH=17(8):30
            if (intHorDes == intHorDesDb) {
                if (intHorHas == intHorHasDb) {
                    if (intMinDes > Integer.parseInt(strArrDes[1])) {
                        if (intMinDes < Integer.parseInt(strArrHas[1])) {
                            booRet = true;
                        }
                    } else if (intMinDes < Integer.parseInt(strArrDes[1])) {
                        if (intMinHas > Integer.parseInt(strArrDes[1])) {
                            booRet = true;
                        }
                    } else if (intMinDes == Integer.parseInt(strArrDes[1])) {
                        booRet = true;
                    }
                } else if (intHorHas > intHorHasDb) {
                    if (intMinDes < Integer.parseInt(strArrHas[1])) {
                        booRet = true;
                    }
                }
            } else if (intHorDes < intHorDesDb) {
                if (intHorHas > intHorHasDb) {
                    booRet = true;
                } else if (intHorHas == intHorHasDb) {
                    if (intMinHas > Integer.parseInt(strArrDes[1])) {
                        booRet = true;
                    }
                }
            }
        } else {
            for (int i = intHorDesDb; i <= intHorHasDb; i++) {
                lstHorRan.add(i);
            }
            if (intHorHas == 0) {
                intHorHas = 24;
            }
            for (int j = intHorDes; j <= intHorHas; j++) {

                if (intHorDes == intHorHas) {
                    if (intHorDes == intHorDesDb) {
                        if (intMinDes < Integer.parseInt(strArrDes[1])) {
                            if (intMinHas > Integer.parseInt(strArrDes[1])) {
                                booRet = true;
                                break;
                            }
                        } else if (intMinDes > Integer.parseInt(strArrDes[1])) {
                            if (intHorHasDb > intHorHas) {
                                booRet = true;
                                break;
                            } else if (intHorHas == intHorHasDb) {
                                if (intMinHas <= Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                }
                            }                            
                        }
                    }else if (intHorDes > intHorDesDb) {
                        if (intHorHas == intHorHasDb) {
                            if (intMinHas <= Integer.parseInt(strArrHas[1])) {
                                booRet = true;
                                break;
                            }
                        } else if (intHorHas < intHorHasDb) {
                            booRet = true;
                            break;
                        }
                    }
                } else if (lstHorRan.contains(j)) {
                    int indice = lstHorRan.indexOf(j);
                    if (j != intHorDesDb && j != intHorHasDb) {/*Entra cuando la hora esta dentro del rango y no en los limites del rango HD o  HH*/

                        booRet = true;
                        break;
                    } else {
                        if (j == intHorDes) {//Coincidencia encontrada en Hora Desde

                            if (indice == 0) {//Hora Desde usuario es igual a Hora Desde base 
                                if (intMinDes > Integer.parseInt(strArrDes[1])) {
                                    booRet = true;
                                    break;
                                } else if (intMinDes == Integer.parseInt(strArrDes[1])) {
                                    booCru = true;
                                } else if (intMinDes < Integer.parseInt(strArrDes[1])) {
                                    booCru = true;
                                }
                            } else if (indice > 0) {// Hora desde usuario es igual a Hora Hasta base
                                if (intMinDes < Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                }
                            }
                        } else if (j == intHorHas) {//Coincidencia encontrada en Hora Hasta
                            if (indice == 0) {
                                if (intMinHas > Integer.parseInt(strArrDes[1])) {
                                    booRet = true;
                                    break;
                                }
                            } else if (indice > 0) {
                                if (intMinHas < Integer.parseInt(strArrHas[1])) {
                                    booRet = true;
                                    break;
                                } else if (intMinHas == Integer.parseInt(strArrHas[1])) {
                                    if (booCru) {
                                        booRet = true;
                                        break;
                                    }
                                } else if (intMinHas > Integer.parseInt(strArrHas[1])) {
                                    if (booCru) {
                                        booRet = true;
                                        break;
                                    }
                                }
                            }
                        } else {//En el caso en que no sean hora desde ni hora hasta de la pantalla pero se cruze con un rango 
                            booRet = true;
                            break;
                        }
                    }
                }
            }
        }
        return booRet;
    } 
    
    /**
     * 
     * @return 
     */
    public boolean verificarSolapeHorasPantalla(){
        boolean booSolapa=false;
        try{
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");            
            DateFormat df= new SimpleDateFormat("HH:mm");
            Date horaDesActDt;
            Date horaFinActDt;
            Date horaDesRecDt;
            Date horaFinRecDt;
            Calendar calFecDes=Calendar.getInstance();
            calFecDes.setTime(fmt.parse(txtSemDes.getText()));
            
            Calendar calFecHas=Calendar.getInstance();
            calFecHas.setTime(fmt.parse(txtSemHas.getText()));
            
            int intCont=0;
            int intCodTra=0, intCodTraPrin=0;
            String strHEntExtPrin, strHSalExtPrin, strHEntExt, strHSalExt;
            while(calFecDes.compareTo(calFecHas)<=0){
                for(int i=0; i<tblDatExt.getRowCount();i++){   
                        if (tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA) != null) {
                        intCodTraPrin=(Integer)tblDatExt.getValueAt(i, INT_TBL_RES_CODTRA);
                        strHEntExtPrin=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCENTHEX+(8*intCont));
                        strHSalExtPrin=(String)tblDatExt.getValueAt(i, INT_TBL_RES_MARCSALHEX+(8*intCont));
                        for(int j=0;j<tblDatExt.getRowCount();j++){
                            if (tblDatExt.getValueAt(j, INT_TBL_RES_CODTRA) != null) {
                                if(i!=j){
                                    intCodTra=(Integer)tblDatExt.getValueAt(j, INT_TBL_RES_CODTRA);
                                    if(intCodTra==intCodTraPrin){
                                        strHEntExt=(String)tblDatExt.getValueAt(j, INT_TBL_RES_MARCENTHEX+(8*intCont));
                                        strHSalExt=(String)tblDatExt.getValueAt(j, INT_TBL_RES_MARCSALHEX+(8*intCont));
                                        if(!strHEntExt.equals("") && !strHSalExt.equals("") && !strHEntExtPrin.equals("") && !strHSalExtPrin.equals("")){
                                            horaDesActDt=df.parse(strHEntExtPrin);
                                            horaFinActDt=df.parse(strHSalExtPrin);
                                            horaDesRecDt=df.parse(strHEntExt);
                                            horaFinRecDt=df.parse(strHSalExt);                                
                                            if((horaDesRecDt.compareTo(horaDesActDt))<=0 && (horaFinRecDt.compareTo(horaDesActDt)>0)){
                                                booSolapa=true;
                                                break;
                                            }else if((horaDesRecDt.compareTo(horaDesActDt)>0) && (horaDesRecDt.compareTo(horaFinActDt)<0)){
                                                booSolapa=true;    
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(booSolapa){
                            break;
                        }
                    }
                }
                if(booSolapa){
                    break;
                }
                intCont++;
                calFecDes.add(Calendar.DATE, 1);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            booSolapa=true;//Para que no grabe si falla
        }
        return booSolapa;
    }

    
    private int obtenerCodLocPred(int intCodEmp){
        int intCodLoc=0;
        if(intCodEmp==1 ){
            intCodLoc=4;
        }else if(intCodEmp==2){
            intCodLoc=1;
        }else if(intCodEmp==4){
            intCodLoc=3;
        }else if(intCodEmp==5){
            intCodLoc=5;
        }
        return intCodLoc;
    }     
    
    private boolean verificarHoraSolapxFechaBase(Map mapReg){

        ResultSet rstLoc=null;
        Statement stmLoc=null;
        String strSQL="";
        boolean booRet=false;
        boolean blnExtTraSel = false; 
        try{
            
            for(Object key: mapReg.keySet()){
                               
                String strKey=(String)key;
                String[] arrKeys=strKey.split("+");
                String[] arrHorDes=arrKeys[2].split(":");
                String[] arrHorHas=arrKeys[3].split(":");
                //mapSolHex.put(intCodDep+"+"+strCalFecDes+"+"+strHEntExt+"+"+strHSalExt, vecAcu);
                Vector vecValues=(Vector)mapReg.get(key);
                for(int i=0; i<vecValues.size(); i++){
                    Vector vecReg=(Vector)vecValues.get(i);
                    /*                    
                    vecReg.add(0, intCodTra);
                    vecReg.add(1, intCodDep);
                    vecReg.add(2, obtenerEmpPag(intCodTra));                    
                    */
                    String strFecha=arrKeys[1];
                    int intCodTra=(Integer)vecReg.get(0);
                    int intCodEmp=(Integer)vecReg.get(2);
                    strSQL = "SELECT co_tra , b.ho_soldes, b.ho_solhas"
                                + " from tbm_detSolHorSupExt a , tbm_cabSolHorSupExt as b"
                                + " where a.co_doc = b.co_doc "
                                + " and a.co_emp=b.co_emp"
                                + " and a.co_loc=b.co_loc"
                                + " and a.co_tipdoc=b.co_tipdoc"
                                + " and  a.co_emp = " + intCodEmp
                                + " and a.co_loc = " + obtenerCodLocPred(intCodEmp)
                                + " and b.fe_sol='" + strFecha + "'"
                                + " and a.co_tra="+intCodTra;
                    rstLoc = stmLoc.executeQuery(strSQL);
                    
                    while (rstLoc.next()) {
                        boolean booTieneCruzeHoras = validarHoraNoAdmiteCruze
                                                            (rstLoc.getString("ho_soldes"), rstLoc.getString("ho_solhas"), 
                                                             Integer.parseInt(arrHorDes[0]), 
                                                             Integer.parseInt(arrHorDes[1]), 
                                                             Integer.parseInt(arrHorHas[0]), 
                                                             Integer.parseInt(arrHorHas[1]));
                        if (booTieneCruzeHoras) {
                            blnExtTraSel = true;
                            //i = tblDat.getRowCount();
                            break;
                        }
                    }
                    if(blnExtTraSel){
                        break;
                    }
                    
                }
                if(blnExtTraSel){
                    break;
                }
            }
        }catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return blnExtTraSel;
    }
    
    
    private boolean obtenerTipDocSol(Connection con, int intEmp, int intLoc) {
        boolean blnRes = true;
        String strSQL = "";
        //Connection con;
        Statement stm;
        ResultSet rst;       
        try {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intEmp;
                    strSQL += " AND a1.co_loc=" + intLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocPrg";
                    strSQL += " WHERE co_emp=" + intEmp;
                    strSQL += " AND co_loc=" + intLoc;
                    strSQL += " AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    rst = stm.executeQuery(strSQL);
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intEmp;
                    strSQL += " AND a1.co_loc=" + intLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocUsr";
                    strSQL += " WHERE co_emp=" + intEmp;
                    strSQL += " AND co_loc=" + intLoc;
                    strSQL += " AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL += " AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    rst = stm.executeQuery(strSQL);
                }
                if (rst.next()) {
                    intCodTipDoc=rst.getInt("co_tipDoc");
                    intNumMax= (rst.getInt("ne_ultDoc") + 1);//THERE
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                }else{
                    blnRes=false;
                }
                rst.close();
                stm.close();
                rst = null;
                stm = null;
            
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private boolean insertarReg(Map mpSol, Connection con){
    
        String strSt_Imp = "N", strSt_Aut = "P", strSt_Reg = "A";
        String strFecha="", strSQL="", strObs="", strSQL2="", strSQLDet="", strSqlUsrAut="";
        int intDepar=0;
        Statement stmLoc=null, stmInsert=null, stmInsertDet=null, stmLocAut=null, stmAutIns=null, stmActTipDoc=null;        
        ResultSet rstLoc=null, rstLocAut=null;
        int intCodDoc=0, intEmpPrimTra=0, intLocPrimTra=0;
        boolean booRet=false;    

        try{

            if (con != null) {
                
                stmLoc=con.createStatement();
                stmInsert=con.createStatement();
                stmInsertDet=con.createStatement();
                /*INSERT EN LA CABECERA*/

                for(Object key: mpSol.keySet()){
                    String strKey=(String)key;
                    String[] arrKeys=strKey.split("\\+");
                    String[] arrHorDes=arrKeys[2].split(":");//Hora Desde
                    String[] arrHorHas=arrKeys[3].split(":");//Hora Hasta
                    intDepar=Integer.parseInt(arrKeys[0]);// Departamento de la solicitud
                    strFecha=arrKeys[1];// Fecha de la solicitud

                    Vector vecReg=(Vector)mpSol.get(key);

                    strObs=(String)(((Vector)vecReg.get(0)).get(3));//Obtiene la observacion del primer empleado, para grabar en cabecera
                    intEmpPrimTra=(Integer)(((Vector)vecReg.get(0)).get(2));//Obtiene el codigo de la empresa del primer empleado.
                    intLocPrimTra=obtenerCodLocPred(intEmpPrimTra);// obtiene el local predeterminado de la empresa del primer empleado
                    
                    booRet=obtenerTipDocSol(con, intEmpPrimTra, intLocPrimTra);                    
                    if(!booRet){
                        mensajeInf("El Usuario no posee los permisos en el tipo de documento en la empresa logoneada");
                        return booRet;
                    }
                    
                    strSQL2 = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabsolhorsupext WHERE "
                            + " co_emp=" + intEmpPrimTra + " and co_loc=" + intLocPrimTra + " "
                            + " and co_tipDoc = " + intCodTipDoc;
                    rstLoc = stmLoc.executeQuery(strSQL2);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;               
                    
                    

                    strSQL = "INSERT INTO tbm_cabsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_dep, co_mot, fe_sol, ho_soldes , ho_solhas, st_imp, st_aut, ho_authas, "
                           + "tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) "
                            + "VALUES (" + intEmpPrimTra + ", " + intLocPrimTra + ", " + intCodTipDoc + ", " + intCodDoc
                            + ", '" + strFecha + "'," + intNumMax + "," + intDepar + ",4,'" + strFecha + "','" + arrKeys[2] + "','" + arrKeys[3] + "','" + strSt_Imp + "','" + strSt_Aut + "',null, "
                            + "'"+/*objUti.codificar(*/strObs/*)*/ + "','SOLICITUD GENERADA AUTOMATICAMENTE POR ZAFIRO','" + strSt_Reg + "',current_timestamp,null, " + objParSis.getCodigoUsuario() + ",null"
                            + ")";
                    stmInsert.executeUpdate(strSQL);
                    int intCantReg=vecReg.size();
                    int intReg=1;
                    for(int i=0; i<intCantReg; i++){
                        Vector vecRegInt=(Vector)vecReg.get(i);
                        strSQLDet = " INSERT INTO tbm_detsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra, co_emppag) "
                                  + " VALUES (" + intEmpPrimTra + ", " + intLocPrimTra + "," + intCodTipDoc + "," + intCodDoc + "," + intReg + ", "
                                  + vecRegInt.get(0).toString() + " , " + vecRegInt.get(2) + ")";
                        stmInsertDet.executeUpdate(strSQLDet);
                        intReg++;
                    }
                    /*GRABA DATOS PARA AUTORIZACIONES DE SOLICITUDES DE HORAS EXTRAS*/
                    strSqlUsrAut = "select * from tbm_usrAutHorSupExtDep a "
                                 + "where co_dep in(" + intDepar+ ") ";

                    stmLocAut=con.createStatement();
                    rstLocAut = stmLocAut.executeQuery(strSqlUsrAut);
                    stmAutIns=con.createStatement();

                    if (rstLocAut.next()) {
                        do {

                            String st_Aut = "P";
                            int str = rstLocAut.getInt("co_reg");
                            strSqlUsrAut = "INSERT INTO tbm_autSolHorSupExt(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut) "
                                         + "VALUES(" + intEmpPrimTra + ", " + intLocPrimTra + "," + intCodTipDoc + "," + intCodDoc + "," + str + ", '"
                                         + st_Aut + "')";
                            stmAutIns.executeUpdate(strSqlUsrAut);
                        } while (rstLocAut.next());
                        /*ACTUALIZAR EL NUMERO DE DOCUMENTO*/
                        stmActTipDoc=con.createStatement();
                        String strSqlActTipDoc = " update tbm_cabtipdoc set ne_ultdoc=" + intNumMax 
                                               + " where co_emp=" + intEmpPrimTra + " and co_loc=" + intLocPrimTra + " and co_tipdoc=" + intCodTipDoc;
                        stmActTipDoc.executeUpdate(strSqlActTipDoc);
                        /*ACTUALIZAR EL NUMERO DE DOCUMENTO*/
                        booRet=true;
                        
                    } else {
                        mensajeInf("NO EXISTEN USUARIOS ASOCIADOS PARA AUTORIZAR HORAS SUPLEMENTARIAS/EXTRAORDINARIAS");
                        booRet= false;
                        break;//Salir del recorrido del hashmap
                    } 
                    /*GRABA DATOS PARA AUTORIZACIONES DE SOLICITUDES DE HORAS EXTRAS*/                      
                }  
                if(booRet){                
                    booRet=true;
                    stmInsertDet.close();
                    stmInsert.close();
                    stmAutIns.close();
                    rstLocAut.close();
                    stmActTipDoc.close();
                }                
            }
        }catch(SQLException sqlex){
            sqlex.printStackTrace();
            booRet=false;
            try{
                con.rollback();
                con.close();
            }catch(Exception ex3){
                ex3.printStackTrace();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
            try{
                con.rollback();
                con.close();
            }catch(Exception ex2){
                ex2.printStackTrace();
            }
        }
        return booRet;                  
        /*FIN DE INSERT EN LA CABECERA*/    
    }
    
    private boolean validarIngresoHoras(String strHora){
        Pattern pattern;
	Matcher matcher;        
        //String TIME24HOURS_PATTERN ="([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";   
        String TIME24HOURS_PATTERN ="([01]?[0-9]|2[0-3]):[0-5][0-9]";   
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
        matcher = pattern.matcher(strHora);
	return matcher.matches();
    }
    

    
    private boolean validaHorMay(String strHorEnt, String strHorSal ){
        DateFormat df= new SimpleDateFormat("HH:mm");
        boolean isHoraValida=true;
        Date horaEnt, horaSal;
        try{
            if(strHorSal!=null && !strHorSal.equals("") && strHorEnt!=null && !strHorEnt.equals("")){
                horaEnt=df.parse(strHorEnt);
                horaSal=df.parse(strHorSal);
                if((horaEnt.compareTo(horaSal))>0 ){
                    isHoraValida=false;
                    mensajeInf("La Hora de entrada no puede ser mayor");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isHoraValida;
    }
    
    private String llamarVenObs(String strObs)
    {
        String strRetorno="";
        ZafRecHum92_01 obj1 = new  ZafRecHum92_01(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
        obj1.show();
        /*if(obj1.acepta()){*/
            strRetorno=obj1.getObsr();
        //}
        obj1=null; 
        return strRetorno;
    }    
}
