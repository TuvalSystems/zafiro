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
public class ZafCon65_06 extends javax.swing.JDialog  {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Vector vecDat,vecCab,vecReg,vecAux;
    private ZafCon65 objCon65;
    private ZafColNumerada objColNum;

    private static final int INT_TBL_DAT_LINEA = 0;                          //Índice de columna Linea.       
    final int INT_DAT_HIS_ADD_ROW=1;
    final int INT_DAT_HIS_CHK_PAG=2;
    final int INT_DAT_HIS_COD_TRA=3;
    final int INT_DAT_HIS_NOM_TRA=4;
    final int INT_DAT_HIS_MNT=5;
    final int INT_DAT_HIS_BTN_MOD=6;
     
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
    public ZafCon65_06(ZafParSis obj) {
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
    public ZafCon65_06(Frame parent, ZafParSis obj, String strCodEmp, String strCodPeriodo, String strNePer,boolean blnGuardar){
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
            configurarTblRegOtrosIngrNoGraFinde();

            if (con != null) {
                stm = con.createStatement(); 
                strSql = "";      
                strSql += " select a.*,(  b.tx_ape+ ' ' + b.tx_nom) nombreTra from tbm_cabotrdes a inner join tbm_tra b on a.co_tra=b.co_tra where co_emp=1 and ne_per=" + strNePer + " and a.co_per=" + strCodPeriodo  ;
                rst = stm.executeQuery(strSql);

                vecDat.clear(); 
                i = 0;
                //se contruye la tabla en base al periodo 15na o fin de mes
                   if (rst.next()) {
                   vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LINEA, "");
                        vecReg.add(INT_DAT_HIS_ADD_ROW, "...");
                        vecReg.add(INT_DAT_HIS_CHK_PAG, true);   
                        vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                        vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra")); 
                        vecReg.add(INT_DAT_HIS_MNT, rst.getString("nd_mnt"));
                        vecReg.add(INT_DAT_HIS_BTN_MOD, "..."); 
                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                    
                    //add lista de detalle guardado
                    strSql="";
                    strSql = "SELECT * FROM tbm_detotrdes " +
                             "WHERE co_emp=1 and ne_per = " + strNePer + " AND co_per = " + strCodPeriodo;   
                     
                     rst = stm.executeQuery(strSql); 
                     while (rst.next()) {
                         int coTra = rst.getInt("co_tra");
                         String cocta = rst.getString("co_cta");
                         String tipmov = rst.getString("tx_tipmov");
                         String observacion = rst.getString("tx_obs");
                         double monto = rst.getDouble("nd_mntcta");
                         int coreg = rst.getInt("co_reg");

                         DetalleRol detalle = new DetalleRol(1, Integer.parseInt(strCodPeriodo),Integer.parseInt(strNePer) ,coTra,coreg,cocta,tipmov,observacion,monto  );  
                         movimientos.add(detalle);
                     }
                     historialMovimientos.add(movimientos);
                    //add los datos no configurados
                     // Agregar los datos no configurados
                     strSql="";
                     strSql = "SELECT a.co_tra, a.co_per, a.ne_per, ( b.tx_ape+ ' ' + b.tx_nom ) AS nombreTra, a.nd_otrdes " +
                             "FROM tbm_rolpagexc a " +
                             "INNER JOIN tbm_tra b ON a.co_tra = b.co_tra " +
                             "WHERE a.nd_otrdes <> 0 AND a.nd_otrdes IS NOT NULL AND a.ne_per = " + strNePer + " AND a.co_per = " + strCodPeriodo;   
                     
                     rst = stm.executeQuery(strSql);   
                     while (rst.next()) {
                         codigotra = rst.getString("co_tra");
                         boolean exists = false;
                         // Verificar si co_tra ya está en vecDat
                         for (Object obj : vecDat) {
                             Vector<?> existingReg = (Vector<?>) obj;
                             String existingCodTra = (String) existingReg.get(INT_DAT_HIS_COD_TRA);
                             if (existingCodTra.equals(codigotra)) {
                                 exists = true;
                                 break;
                             }
                         }
                         // Si no existe, añadir nuevo registro
                         if (!exists) {
                             Vector<Object> vecReg = new Vector<>();
                             vecReg.add(INT_TBL_DAT_LINEA, "");
                             vecReg.add(INT_DAT_HIS_ADD_ROW, "...");
                             vecReg.add(INT_DAT_HIS_CHK_PAG, false);
                             vecReg.add(INT_DAT_HIS_COD_TRA, codigotra);
                             vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra"));
                             vecReg.add(INT_DAT_HIS_MNT, rst.getString("nd_otrdes"));
                             vecReg.add(INT_DAT_HIS_BTN_MOD, "...");
                             vecDat.add(vecReg);
                         }
                     }
                 
                }else{
                       strSql = "";   
                       strSql += "  select a.co_tra,a.co_per, a.ne_per,( b.tx_ape + ' ' + b.tx_nom ) nombreTra ,a.nd_otrdes\n" +
                               "  from tbm_rolpagexc a \n" +
                               "  inner join tbm_tra b on a.co_tra=b.co_tra \n" +
                               "  where a.nd_otrdes <>0 and a.nd_otrdes is not null and  a.ne_per= " + strNePer + " and a.co_per=" + strCodPeriodo  ;
                       rst = stm.executeQuery(strSql);
                       if (rst.next()) {
                           vecDat = new Vector();
                           do {
                               vecReg = new Vector();     
                               vecReg.add(INT_TBL_DAT_LINEA, "");
                               vecReg.add(INT_DAT_HIS_ADD_ROW, "...");
                               vecReg.add(INT_DAT_HIS_CHK_PAG, false);                               
                               vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                               vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra"));    
                               vecReg.add(INT_DAT_HIS_MNT, rst.getString("nd_otrdes"));
                               vecReg.add(INT_DAT_HIS_BTN_MOD, "...");  
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
    private boolean configurarTblRegOtrosIngrNoGraFinde()
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
            vecCab.add(INT_DAT_HIS_CHK_PAG,"Se Paga");            
            vecCab.add(INT_DAT_HIS_COD_TRA,"Cod.Tra.");
            vecCab.add(INT_DAT_HIS_NOM_TRA,"Nom.Tra.");
            vecCab.add(INT_DAT_HIS_MNT,"Valor"); 
            vecCab.add(INT_DAT_HIS_BTN_MOD,"Conf.");  
            
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
            tcmAux.getColumn(INT_DAT_HIS_CHK_PAG).setPreferredWidth(60);           
            tcmAux.getColumn(INT_DAT_HIS_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_DAT_HIS_NOM_TRA).setPreferredWidth(300);
            tcmAux.getColumn(INT_DAT_HIS_MNT).setPreferredWidth(80); 
            tcmAux.getColumn(INT_DAT_HIS_BTN_MOD).setPreferredWidth(45); 
 
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_DAT_HIS_ADD_ROW).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tbDatos.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tbDatos.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            
            tbDatos.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                   tblLocMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
           //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector(); 
            vecAux.add("" + INT_DAT_HIS_ADD_ROW); 
            vecAux.add("" + INT_DAT_HIS_BTN_MOD); 
            vecAux.add("" + INT_DAT_HIS_CHK_PAG);
            vecAux.add("" + INT_DAT_HIS_MNT);
            objTblModExc.setColumnasEditables(vecAux);
            vecAux = null;
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_HIS_MNT).setCellRenderer(objTblCelRenLbl);         
            objTblCelRenLbl = null;
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_DAT_HIS_BTN_MOD).setCellRenderer(zafTblDocCelRenBut);
             
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();//llamarPantallaListadoFaltasEmpleados
            tcmAux.getColumn(INT_DAT_HIS_BTN_MOD).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tbDatos.getSelectedRow();
                    intColSel = tbDatos.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_DAT_HIS_BTN_MOD:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tbDatos.getSelectedRow();
                    intColSel = tbDatos.getSelectedColumn();
                    valorTra=  Double.parseDouble(objTblModExc.getValueAt(tbDatos.getSelectedRow(), INT_DAT_HIS_MNT).toString());
                    codigoTra= Integer.parseInt(objTblModExc.getValueAt(tbDatos.getSelectedRow(), INT_DAT_HIS_COD_TRA).toString());

                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_DAT_HIS_BTN_MOD:     
                                llamarPantallaEditOtrosInNoGrav(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer),valorTra,codigoTra);

                        }
                    }
                }
            });
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
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tbDatos.getColumnModel().getColumn(INT_DAT_HIS_CHK_PAG).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tbDatos);
            tbDatos.getColumnModel().getColumn(INT_DAT_HIS_CHK_PAG).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                 
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
    
      private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModExc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tbDatos.columnAtPoint(evt.getPoint())==INT_DAT_HIS_CHK_PAG)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModExc.setChecked(true, i, INT_DAT_HIS_CHK_PAG);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModExc.setChecked(false, i, INT_DAT_HIS_CHK_PAG);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
  
    private void llamarPantallaEditOtrosInNoGrav(String strCodEmp, String strCoPer,String strNePer,Double Valor,Integer Codtra ) {
        //envio la lista x codigo de trabajador
         List<DetalleRol> movimientosxtrab=new ArrayList<>(); 
         int his_codtrab =0;
        for (int i=0; i<historialMovimientos.size();i++){
            List<DetalleRol> movimientosInternos = historialMovimientos.get(i); 
            for (DetalleRol detalle : movimientosInternos) {
                his_codtrab = detalle.getCoTra();
                if (his_codtrab ==Codtra){
                    movimientosxtrab.add(detalle);
                }
            }
        }
        
        Contabilidad.ZafCon65.ZafCon65_07 obj1 = new Contabilidad.ZafCon65.ZafCon65_07(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer,Valor,Codtra,movimientosxtrab);
        //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
        if (obj1.acepta()) {
            strvalorTotal= obj1.getvalorTotal();
            strInsertDet+=obj1.getstrSqlIns();
            List<DetalleRol> movimientos = obj1.getstrmovimientos();  
            if (movimientos != null) {
                for (DetalleRol movimiento : movimientos) {
                    int codigoTrabajador = movimiento.getCoTra();
                    for (List<DetalleRol> lista : historialMovimientos) {
                        lista.removeIf(detalle -> detalle.getCoTra() == codigoTrabajador);
                    }
                }
                historialMovimientos.add(movimientos);
            }
        }
        obj1 = null;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDatos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnGuarda = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setPreferredSize(new java.awt.Dimension(600, 550));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Otros Descuentos");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

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
        jScrollPane2.setViewportView(tbDatos);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

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
        String Sepaga="" ;  
        boolean blnRes=true;
        boolean TienechkSepag=false;
        int hist_codreg =0;

        strSQLDet="";
        strSqlIns="";
        strSQLCAb="";
        strSqlSeg="";
        strSqldel="";
        
        
        strSqldel= " delete from tbm_detotrdes where co_emp=1 and co_per="+strperiodo+" and ne_per="+intNePer+" ;\n "
                + " delete from tbm_cabotrdes where co_emp=1 and  co_per=" +strperiodo +" and ne_per=" +intNePer +" ;" ;
        
        for (int i = 0; i < objTblModExc.getRowCountTrue(); i++) {
            Sepaga =objTblModExc.getValueAt(i, INT_DAT_HIS_CHK_PAG).toString();
            if (Sepaga.equals("true")){
                TienechkSepag=true;
            }
        }
        if (TienechkSepag){
            for (int i = 0; i < objTblModExc.getRowCountTrue(); i++) {
                hist_codreg=0;
            Sepaga =objTblModExc.getValueAt(i, INT_DAT_HIS_CHK_PAG).toString();
            if (Sepaga.equals("true")){
                 
                Object objcoTra = objTblModExc.getValueAt(i, INT_DAT_HIS_COD_TRA);
                String co_tra = (objcoTra != null) ? objcoTra.toString() : ""; 
                 
                Object objmntTra = objTblModExc.getValueAt(i, INT_DAT_HIS_MNT);
                String mntTra = (objmntTra != null) ? objmntTra.toString() : ""; 
                
                Object objnomTra = objTblModExc.getValueAt(i, INT_DAT_HIS_NOM_TRA);
                String nomTra = (objnomTra != null) ? objnomTra.toString() : ""; 
                
                strSQLCAb+="insert into tbm_cabotrdes (co_emp,co_per,ne_per,co_tra,nd_mnt) values(1," + strperiodo + "," + intNePer + "," + co_tra+ "," + mntTra+ "); \n"; 
                System.out.println("strSQLCAb: "+strSQLCAb);
               
        //armo el detalle para grabar
        int hist_codtrab =0;
        String hist_codcta="";
        String hist_tipMov="";
        double hist_mntcta=0;
        String hist_obs="";
        

        for (int k=0; k<historialMovimientos.size();k++){   
            List<DetalleRol> movimientosInternos = historialMovimientos.get(k);  
            hist_codreg=0;
            for (DetalleRol detalle : movimientosInternos) {
                 hist_codreg+=1;            

                hist_codtrab = detalle.getCoTra();
                hist_codcta = detalle.getCoCta();
                hist_tipMov = detalle.getTxTipMov();
                hist_mntcta = detalle.getNdMntCta();
                hist_obs = detalle.getTxObs();
                if (hist_codtrab==Integer.parseInt(co_tra)){
                    strSQLDet+=" insert into tbm_detotrdes (co_emp,co_per,ne_per,co_tra,co_reg,co_cta,tx_tipmov,tx_obs,nd_mntcta)values(1," +
                       strperiodo+","+ intNePer+ "," + hist_codtrab+ "," + hist_codreg+ "," + hist_codcta+ ",'"+ hist_tipMov+ "','" + hist_obs + "',"+hist_mntcta+ ");\n"; 
                    System.out.println("strSQLDet: "+strSQLDet);
                } 
                

            }
        }
            }
            }
              
            if (blnRes){
                 //actualizo tabla de seguimineto 
            strSqlSeg=" update tbm_seggendiarolpag set st_ingotrdes='S',fe_ingotrdes=getdate() where co_emp=1 and co_per="+ strperiodo +" and ne_per=" + intNePer + ";\n";
                System.out.println("strSqlSeg: "+strSqlSeg);
            strSqlIns = strSqldel + strSQLCAb + strSQLDet + strSqlSeg ;
            if(strSqlIns.length()>0){
                if(!insertarCadena(con)){
                    //blnRes=false;
                }
            }  
            this.setVisible(false);
            }
       
        }else{
            blnRes=false;
            mostrarMsgErr("Debe seleccionar que valores deben pagarse y a su vez configurarse");
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
                    System.out.println("OK LISTO");
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTable tbDatos;
    // End of variables declaration//GEN-END:variables
}
