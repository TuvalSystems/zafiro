/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon65;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author sistemas4
 */
public class ZafCon65_05 extends javax.swing.JDialog  {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Vector vecDat,vecCab,vecReg,vecAux;
    private ZafCon65 objCon65;
    private ZafColNumerada objColNum;

    private static final int INT_TBL_DAT_LINEA = 0;                          //Índice de columna Linea.       
    final int INT_DAT_HIS_ADD_ROW=1;
    final int INT_DAT_HIS_COD_TRA=2;
    final int INT_DAT_HIS_NOM_TRA=3;
    final int INT_DAT_HIS_MNT=4;
     
    private boolean blnMarTodChkTblEmp=false;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private double valorTra=0,strvalorTotal=0;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private int codigoTra=0;                    //Marcar todas las casillas de verificación del JTable de empresas.
    List<DetalleRol> movimientos = new ArrayList<>();
    List<List<DetalleRol>> historialMovimientos = new ArrayList<>();
    
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Object[] estadoOriginal; // Array para almacenar el estado original de la fila
    private ZafMouMotAda objMouMotAda;                        //ToolTipText en TableHeader.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUsr;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUsr;           //Editor: JTextField de consulta en celda.
    private ZafVenConGP vcoEmpleado;                                   //Ventana de consulta.

    private String strperiodo="",intNePer="",strSQL="",strInsertDet="",strSQLCAb="",strSQLDet="",strSqlIns="",strSqlSeg="",strSqldel="";
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblMod objTblModExc;
    private ZafTblPopMnu objTblPopMnu;
    private boolean btnGuardar=false;    

    /**
     * Creates new form ZafCon65_02
     */
    public ZafCon65_05(ZafParSis obj) {
          try{
             initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    public ZafCon65_05(Frame parent, ZafParSis obj, String strCodEmp, String strCodPeriodo, String strNePer,boolean blnGuardar){
   super(parent, true);
      try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);
            strperiodo=strCodPeriodo;
            intNePer=strNePer; 
            btnGuardar=blnGuardar;
            configurarEmpleados();
            cargarReg(strCodEmp,strCodPeriodo,strNePer);

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    
       private boolean cargarReg(String strCodEmp, String strCodPeriodo, String strNePer) {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "",str_TipoCosto="",codigotra="";
        boolean blnRes = true;  
        int i;
        int intNumReg = 0;
 
        try {
            con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            vecDat = new Vector();
            configurarTblRegImpRentaProy();

            if (con != null) {
                stm = con.createStatement(); 
                strSql = "";      
                strSql += " SELECT a.*,( c.tx_ape + ' ' + c.tx_nom ) nombreTra\n" +
                        "   FROM tbm_ingimprenrolpag a\n" +
                        "   inner join tbm_tra c on a.co_tra=c.co_tra\n" +
                        "   WHERE a.co_per = "+strCodPeriodo+" AND a.ne_per = "+strNePer;
                System.out.println("cargarReg: "+strSql);
                rst = stm.executeQuery(strSql);

                vecDat.clear(); 
                i = 0;
                   if (rst.next()) {
                   vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LINEA, "");
                        vecReg.add(INT_DAT_HIS_ADD_ROW, "...");
                        vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                        vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra")); 
                        vecReg.add(INT_DAT_HIS_MNT,  objUti.redondear(Double.parseDouble(String.valueOf(rst.getDouble("nd_mnt"))), objParSis.getDecimalesMostrar())); 
                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                 
                }else{
                       strSql = "";   
                       strSql += "    SELECT a.co_tra, a.nd_suetra,(a.nd_suetra * 12) AS sueldoanual,( c.tx_ape + ' ' + c.tx_nom ) nombreTra\n" +
                        "   FROM tbm_rolpagexc a\n" +
                        "   JOIN tbm_confImpRent b ON (a.nd_suetra * 12) BETWEEN b.nd_frabas AND b.nd_exchas\n" +
                        "   inner join tbm_tra c on a.co_tra=c.co_tra\n" +
                        "   WHERE a.co_per = "+strCodPeriodo+" AND a.ne_per = "+strNePer+" AND b.nd_porimpfraexc > 0 AND b.ne_anio = 2025;" ;
                       System.out.println("cargarReg 2: "+strSql);
                       rst = stm.executeQuery(strSql);
                       if (rst.next()) {
                           vecDat = new Vector();
                           do {
                               vecReg = new Vector();     
                               vecReg.add(INT_TBL_DAT_LINEA, "");
                               vecReg.add(INT_DAT_HIS_ADD_ROW, "...");
                               vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                               vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra"));    
                               vecReg.add(INT_DAT_HIS_MNT,  objUti.redondear(Double.parseDouble(String.valueOf("0")), objParSis.getDecimalesMostrar()));
                               vecDat.add(vecReg);
                               i++;
                           } while (rst.next());
                       }
                   
                   }
                         
                }
                //Asignar vectores al modelo.
                objTblModExc.setData(vecDat);
                tbDatos.setModel(objTblModExc);                 
                
                con.close();
                con = null;
          
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }
    
       
        private boolean configurarEmpleados()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_nom");
             //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombres Completos");
             //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("370");
             
            strSQL="";
            strSQL+=" select co_tra, (tx_ape+' ' + tx_nom) tx_nom from tbm_tra where st_reg='A' ";
            System.out.println("configurarEmpleados: "+strSQL);
            //Armar la sentencia SQL.
            vcoEmpleado=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmpleado.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tbDatos.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) { 
                case INT_DAT_HIS_ADD_ROW:
                    strMsg = "Muestra listado de Empleados";
                    break;
            }
            tbDatos.getTableHeader().setToolTipText(strMsg);
        }
    } 
    private boolean configurarTblRegImpRentaProy()
    {          
        objUti = new ZafUtil();
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA, "");
            vecCab.add(INT_DAT_HIS_ADD_ROW,"...");
            vecCab.add(INT_DAT_HIS_COD_TRA,"Cod.Tra.");
            vecCab.add(INT_DAT_HIS_NOM_TRA,"Nom.Tra.");
            vecCab.add(INT_DAT_HIS_MNT,"Valor"); 
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModExc=new ZafTblMod();
            objTblModExc.setHeader(vecCab);
            tbDatos.setModel(objTblModExc);
            //Configurar JTable: Establecer tipo de selección.
            tbDatos.setRowSelectionAllowed(true);
            tbDatos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tbDatos);
            objColNum = new ZafColNumerada(tbDatos, INT_TBL_DAT_LINEA);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tbDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tbDatos.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);    
            tcmAux.getColumn(INT_DAT_HIS_ADD_ROW).setPreferredWidth(45);
            tcmAux.getColumn(INT_DAT_HIS_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_DAT_HIS_NOM_TRA).setPreferredWidth(300);
            tcmAux.getColumn(INT_DAT_HIS_MNT).setPreferredWidth(80); 
 
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_DAT_HIS_ADD_ROW).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tbDatos.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tbDatos.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
           //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector(); 
            vecAux.add("" + INT_DAT_HIS_ADD_ROW); 
            vecAux.add("" + INT_DAT_HIS_MNT);
            objTblModExc.setColumnasEditables(vecAux);
            vecAux = null;
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_DAT_HIS_MNT).setCellRenderer(objTblCelRenLbl);         
            objTblCelRenLbl = null;
     
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_DAT_HIS_ADD_ROW).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_DAT_HIS_COD_TRA;
            intColTbl[1]=INT_DAT_HIS_NOM_TRA;
            
            List<Object[]> datosTemporales = new ArrayList<>();

            
            objTblCelEdiButVcoUsr=new ZafTblCelEdiButVco(tbDatos, vcoEmpleado, intColVen, intColTbl);
            tcmAux.getColumn(INT_DAT_HIS_ADD_ROW).setCellEditor(objTblCelEdiButVcoUsr);
            objTblCelEdiButVcoUsr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   datosTemporales.clear(); // Limpiar la lista antes de almacenar
                   for (int i = 0; i < objTblModExc.getRowCount(); i++) {
                       Object[] fila = new Object[objTblModExc.getColumnCount()];
                       for (int j = 0; j < objTblModExc.getColumnCount(); j++) {
                           fila[j] = objTblModExc.getValueAt(i, j);
                       }
                       datosTemporales.add(fila);
                   }
                }
                
                
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    String codtra = vcoEmpleado.getValueAt(1);
                    int intRow = tbDatos.getSelectedRow();

                    if (objTblCelEdiButVcoUsr.isConsultaAceptada()){    
                        for (int i = 0; i < objTblModExc.getRowCountTrue(); i++) {
                            String codigoActual = objTblModExc.getValueAt(i, INT_DAT_HIS_COD_TRA).toString(); 
                            String NombreActual = objTblModExc.getValueAt(i, INT_DAT_HIS_NOM_TRA).toString(); 
                            if (i!=intRow){
                                if (codigoActual.equals(codtra)) {
                                if (intRow >= 0 && intRow < datosTemporales.size()) {
                                    Object[] filaAnterior = datosTemporales.get(intRow);
                                    for (int j = 0; j < filaAnterior.length; j++) {
                                        objTblModExc.setValueAt(filaAnterior[j], intRow, j); // Restaurar los valores en la fila correspondiente
                                    }
                                }
                               mostrarMsgErr("El empleado " + NombreActual + " ya se encuentra ingresado.");
                            }
                            }
                        } 
                    }   
                    objTblModExc.insertRow();
                } 
            });
            
         
            //Configurar JTable: Establecer el modo de operación.
            objTblModExc.setModoOperacion(ZafTblMod.INT_TBL_INS);
             //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }
     
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {                          
    String strTit, strMsg;
    //try{
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    } 

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnGuarda = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDatos = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(600, 550));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingreso Impuesto Renta Proyectada");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        btnGuarda.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnGuarda.setText("Guardar");
        btnGuarda.setPreferredSize(new java.awt.Dimension(90, 23));
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuarda);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel4.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel4.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);

        tbDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbDatos);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
        
    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        // TODO add your handling code here:
        Connection con=null;
        if(!btnGuardar){
        try
        {
            if (objTblModExc.isDataModelChanged())
            {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0)
                {
                    con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());          
                    if (con != null)
                    {
                        con.setAutoCommit(false);
                        if (bntGuardar(con))
                        {
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            
                        }
                        else
                        {
                            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                        }
                    }
                }
            }
            else
            {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        }  else{         
            mostrarMsgInf("El asiento ya se encuentra generado para este periodo, no se aceptan cambios.");
        }
 
    }//GEN-LAST:event_btnGuardaActionPerformed

    private boolean bntGuardar(Connection con){
        boolean blnRes=true;
        strSqlIns="";
        strSQLCAb="";
        strSqlSeg="";
        strSqldel="";
        strSqldel=  " delete from tbm_ingimprenrolpag where co_per=" +strperiodo +" and ne_per=" +intNePer +" ;\n" ;
    
        for (int i = 0; i < objTblModExc.getRowCountTrue(); i++) {
            
                Object objmntTra = objTblModExc.getValueAt(i, INT_DAT_HIS_MNT);
                String mntTra = (objmntTra != null) ? objmntTra.toString() : ""; 
                
                Object objcoTra = objTblModExc.getValueAt(i, INT_DAT_HIS_COD_TRA);
                String co_tra = (objcoTra != null) ? objcoTra.toString() : ""; 
                
                Object objnomTra = objTblModExc.getValueAt(i, INT_DAT_HIS_NOM_TRA);
                String nomTra = (objnomTra != null) ? objnomTra.toString() : ""; 
                
                if (Double.parseDouble(mntTra) >0 ){
                strSQLCAb+="insert into tbm_ingimprenrolpag (co_per,ne_per,co_tra,nd_mnt) values(" + strperiodo + "," + intNePer + "," + co_tra+ "," + Double.parseDouble(mntTra)+ "); \n";   
                }
        }
        
        if (blnRes){
            //actualizo tabla de seguimineto 
            strSqlSeg=" update tbm_seggendiarolpag set st_ingimpren='S',fe_ingimpren=getdate() where co_emp=1 and co_per="+ strperiodo +" and ne_per=" + intNePer + ";\n";
            strSqlIns = strSqldel + strSQLCAb  + strSqlSeg ;
            if(strSqlIns.length()>0){
                if(!insertarCadena(con)){
                    //blnRes=false;
                }
            }  
            this.setVisible(false);
        }
        return blnRes;
    }
    
    
    private boolean insertarCadena(Connection conLoc){
        boolean blnRes=false;
       // java.sql.Connection conLoc;
        try{
           // conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                if(ejecutaUpdate(conLoc)){
                    conLoc.commit();
                    blnRes=true;
                    System.out.println("OK");
                }
                else{
                    conLoc.rollback();                    
                }
                strSqlIns="";
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean ejecutaUpdate(java.sql.Connection conExt){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmLoc.executeUpdate(strSqlIns);
                blnRes=true;
                strSqlIns="";
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        // TODO add your handling code here:
         exitForm(null);
    }//GEN-LAST:event_butCerrActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton butCerr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTable tbDatos;
    // End of variables declaration//GEN-END:variables
}
