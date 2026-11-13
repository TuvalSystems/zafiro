/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum43;

import RecursosHumanos.ZafRecHum09.*;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenEmp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafRecHum.ZafVenFun.ZafVenFun; 
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Listado de Marcaciones de Empleados
 *
 * @author Roberto Flores Guayaquil 22/08/2012
 */
public class ZafRecHum43 extends javax.swing.JInternalFrame {

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                             //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                             //Renderer de Check Box para campo Laborable
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafVenTra zafVenTra;                                            //Ventana de consulta de Empleados
    private ZafVenDep zafVenDep;                                            //Ventana de consulta de Departamentos
    private ZafVenEmp zafVenEmp;                                            //Ventana de consulta de Empresas
    private ZafThreadGUI objThrGUI;
    private ZafSelFec objSelFec;
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    //private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
    
    private static final int INT_TBL_DAT_NUM_TOT_CDI = 19;

    private static final int INT_TBL_LINEA                = 0;
    private static final int INT_TBL_DAT_CODIGO           = 1;
    private static final int INT_TBL_DAT_CEDULA           = 2;
    private static final int INT_TBL_DAT_EMPLEADO         = 3;
    private static final int INT_TBL_DAT_DEP              = 4;    
    private static final int INT_TBL_DAT_ENTRADA          = 5;
    private static final int INT_TBL_DAT_SALIDA           = 6;
    private static final int INT_TBL_DAT_SOLICITUD        = 7;
    private static final int INT_TBL_DAT_USRING           = 8;    
    private static final int INT_TBL_DAT_BUTANESOLMAR     = 9;
    private static final int INT_TBL_DAT_DIA_SOLICITUD    = 10;
    private static final int INT_TBL_DAT_ESTADO           = 11;
    private static final int INT_TBL_DAT_AUT_DESDE        = 12;
    private static final int INT_TBL_DAT_AUT_HASTA        = 13;
    private static final int INT_TBL_DAT_SUELDO           = 14;
    private static final int INT_TBL_DAT_MINUTOS          = 15;
    private static final int INT_TBL_DAT_VALOR_MINUTO     = 16;
    private static final int INT_TBL_DAT_POR_PAGAR        = 17;
    private static final int INT_TBL_DAT_VALOR_SOBRETIEMPO= 18;
    private static final int INT_TBL_DAT_OBSERVACION      = 19;

    //private static final int INT_TBL_DAT_BUT             = 16; // si necesitas botón oculto
    
    private String strVersion = " v1.00";
    private Vector vecCab = new Vector();                                  //Vector que contiene la cabecera del Table.
    private Vector vecAux;
    private int intCodEmp;                                                 //Código de la empresa.
    private boolean blnMod;                                                //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblTot objTblTot;                        //JTable de totales.
    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strFecDes = "";
    private String strFecHas = "";
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon vcoDep;                                   //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafPerUsr objPerUsr;
    private boolean blnEstCarMarcEmp = false;
    
    private ZafTblCelEdiButGen objTblCelEdiButGen;

    /**
     * Creates new form ZafRecHum14
     */
    public ZafRecHum43(Librerias.ZafParSis.ZafParSis obj) {

        try {

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            objPerUsr = new ZafPerUsr(objZafParSis);


//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                txtCodEmp.setEnabled(true);
//                txtNomEmp.setEnabled(true);
//                butEmp.setEnabled(true);
//            }


            this.setTitle(objZafParSis.getNombreMenu() + "  " + strVersion + " ");
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            configuraTbl();
//            agregarColTblDat();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            cargarPeriodo();
            cargarComboObservacion();

            blnMod = false;
            blnConsDat = false;

        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }  /**/
    }

    /**
     * Creates new form ZafRecHum27
     */
    public ZafRecHum43(Librerias.ZafParSis.ZafParSis obj, String strCoEmp, String strCoTra, String strFecDes, String strFecHas, int intOp) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            objPerUsr = new ZafPerUsr(objZafParSis);


            this.setTitle(objZafParSis.getNombreMenu() + "  " + strVersion + " ");
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            configuraTbl();
            //agregarColTblDat();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
          

            butCon.setVisible(false);
            

            blnMod = false;
            blnConsDat = false;
            blnEstCarMarcEmp = true;

            if (blnEstCarMarcEmp) {
                this.strCodEmp = strCoEmp;
                this.strCodTra = strCoTra;
                //vecDat=null; 
                //cargarDatos();
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI(intOp);
                    objThrGUI.start();
                }
            }


        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public class MyTableModelListener implements TableModelListener {

        JTable table;

        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source
        MyTableModelListener(JTable table) {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e) {
            if (!blnConsDat) {
                switch (e.getType()) {
                    case TableModelEvent.UPDATE:
                        blnMod = true;
                        break;
                }
            }
            blnConsDat = false;
        }
    }

    private boolean configuraTbl() {

        boolean blnRes = false;

        try {

            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,                 "");
            vecCab.add(INT_TBL_DAT_CODIGO,            "Código");
            vecCab.add(INT_TBL_DAT_CEDULA,            "Cédula");
            vecCab.add(INT_TBL_DAT_EMPLEADO,          "Empleado");
            vecCab.add(INT_TBL_DAT_DEP,               "Departamento");            
            vecCab.add(INT_TBL_DAT_ENTRADA,           "Entrada");
            vecCab.add(INT_TBL_DAT_SALIDA,            "Salida");
            vecCab.add(INT_TBL_DAT_SOLICITUD,         "Solicitud");
            vecCab.add(INT_TBL_DAT_USRING,            "Usr.Ing.Sol.");            
            vecCab.add(INT_TBL_DAT_BUTANESOLMAR,      "");
            vecCab.add(INT_TBL_DAT_DIA_SOLICITUD,     "Fecha");
            vecCab.add(INT_TBL_DAT_ESTADO,            "Estado");
            vecCab.add(INT_TBL_DAT_AUT_DESDE,         "Aut.Desde");
            vecCab.add(INT_TBL_DAT_AUT_HASTA,         "Aut.Hasta");
            vecCab.add(INT_TBL_DAT_SUELDO,            "Sueldo");
            vecCab.add(INT_TBL_DAT_MINUTOS,           "Minutos");
            vecCab.add(INT_TBL_DAT_VALOR_MINUTO,      "Val.Min");
            vecCab.add(INT_TBL_DAT_POR_PAGAR,         "% Pago");
            vecCab.add(INT_TBL_DAT_VALOR_SOBRETIEMPO, "Val.Sobretiempo");
            vecCab.add(INT_TBL_DAT_OBSERVACION,       "Observación");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);

            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CEDULA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_EMPLEADO).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_DAT_DEP).setPreferredWidth(110);

            tcmAux.getColumn(INT_TBL_DAT_ENTRADA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_SALIDA).setPreferredWidth(70);

            tcmAux.getColumn(INT_TBL_DAT_SOLICITUD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_USRING).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLMAR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DIA_SOLICITUD).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_DAT_ESTADO).setPreferredWidth(120);

            tcmAux.getColumn(INT_TBL_DAT_AUT_DESDE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_AUT_HASTA).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_DAT_SUELDO).setPreferredWidth(70);

            tcmAux.getColumn(INT_TBL_DAT_MINUTOS).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_DAT_VALOR_MINUTO).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_DAT_POR_PAGAR).setPreferredWidth(60);

            tcmAux.getColumn(INT_TBL_DAT_VALOR_SOBRETIEMPO).setPreferredWidth(100);

            tcmAux.getColumn(INT_TBL_DAT_OBSERVACION).setPreferredWidth(150);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            
             //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTANESOLMAR);            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
           
            //vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            //objTblMod.setColumnasEditables(vecAux);
            //objTblMod.addColumnasEditables(vecAux);
            //vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);

            //Configurar JTable: Establecer el ordenamiento en la tabla.
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            configurarColoresFila();
            
            ZafTblCelRenHorExt objRen = new ZafTblCelRenHorExt();
            tblDat.setDefaultRenderer(Object.class, objRen);
            tblDat.setDefaultRenderer(Number.class, objRen);
            tblDat.setDefaultRenderer(Double.class, objRen);

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[] = {};
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            tblTot.setFont(new java.awt.Font(tblTot.getFont().getName(),java.awt.Font.BOLD,tblTot.getFont().getSize()));
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes = true;
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLMAR).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLMAR).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTANESOLMAR:

                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTANESOLMAR:

                                llamarPantSol(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_SOLICITUD).toString());

                                break;

                        }
                    }
                }
            });
            
            
            

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
 
//    private boolean agregarColTblDat() {
//
//        int i, intNumFil, intNumColTblDat;
//        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
//        objTblHeaGrp.setHeight(16 * 2);
//        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
//        java.awt.Color colFonCol;
//        boolean blnRes = true;
//
//        try {
//            intNumFil = objTblMod.getRowCountTrue();
//            intNumColTblDat = objTblMod.getColumnCount();
//
//            for (i = 0; i < 1; i++) {
//
//                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Marcaciones");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_MARENT + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_MARSAL + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TIEATR + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALATR + i * INT_TBL_DAT_NUM_TOT_CDI));                
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TIEADE + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALADE + i * INT_TBL_DAT_NUM_TOT_CDI));  
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
//
//
//                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de atrasos");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSATR + i * INT_TBL_DAT_NUM_TOT_CDI));
////                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESATR+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSATR + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSATR + i * INT_TBL_DAT_NUM_TOT_CDI));
//
//
//                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de faltas");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSPER + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
////                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
//
//                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de salidas adelantadas");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));
////                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));
//
//                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de marcaciones incompletas");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));
////                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));
//
//
////                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_HORFUEOFI+i*INT_TBL_DAT_NUM_TOT_CDI));
//
//            }
//        } catch (Exception e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        cboObservacion = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        buttonGroup1.add(optTod);
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

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
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
        jLabel4.setBounds(40, 95, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 95, 60, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 95, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 95, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 115, 100, 20);

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
        txtCodDep.setBounds(140, 115, 60, 20);

        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 115, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 115, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 135, 100, 20);

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
        txtCodTra.setBounds(140, 135, 60, 20);

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
        txtNomTra.setBounds(200, 135, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 135, 20, 20);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Periodo:");
        panFil.add(jLabel8);
        jLabel8.setBounds(20, 20, 70, 20);

        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(90, 20, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panFil.add(cboPerMes);
        cboPerMes.setBounds(170, 20, 120, 20);

        cboObservacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboObservacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboObservacionFocusGained(evt);
            }
        });
        panFil.add(cboObservacion);
        cboObservacion.setBounds(140, 155, 130, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Observación"); // NOI18N
        panFil.add(jLabel7);
        jLabel7.setBounds(40, 155, 100, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        jPanel1.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 376);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    public void consultarRepTra() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            //conn= java.sql.DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(),objZafParSis.getUsuarioBaseDatos_Dynamics2021(),objZafParSis.getClaveBaseDatos_Dynamics2021());

            if (conn != null && txtCodTra.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT tx_ape , tx_nom from tbm_tra where co_tra =  " + txtCodTra.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                    txtNomTra.setHorizontalAlignment(2);
                    txtNomTra.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomTra.setText("");
                    txtCodTra.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }

    public void consultarRepEmp() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            //conn= java.sql.DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(),objZafParSis.getUsuarioBaseDatos_Dynamics2021(),objZafParSis.getClaveBaseDatos_Dynamics2021());

            if (conn != null && txtCodEmp.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT co_emp , tx_nom from tbm_emp where co_emp =  " + txtCodEmp.getText() + " and st_reg like 'A' and co_emp not in (0) ";
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomEmp.setText(rstLoc.getString("tx_nom"));
                    txtNomEmp.setHorizontalAlignment(2);
                    txtNomEmp.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomEmp.setText("");
                    txtCodEmp.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void consultarRepDep() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            //conn= java.sql.DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(),objZafParSis.getUsuarioBaseDatos_Dynamics2021(),objZafParSis.getClaveBaseDatos_Dynamics2021());

            if (conn != null && txtCodDep.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT co_dep,tx_deslar from tbm_dep where co_dep =  " + txtCodDep.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomDep.setText(rstLoc.getString("tx_deslar"));
                    txtNomDep.setHorizontalAlignment(2);
                    txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomDep.setText("");
                    txtCodDep.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");

        }
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
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

    public void BuscarEmp(String campo, String strBusqueda, int tipo) {

        vcoEmp.setTitle("Listado de Empresas");
        if (vcoEmp.buscar(campo, strBusqueda)) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        } else {
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
            } else {
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
            }
        }
    }

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();

    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp = txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

}//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp)) {
                if (txtNomEmp.getText().equals("")) {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                } else {
                    mostrarVenConEmp(2);
                }
            } else {
                txtNomEmp.setText(strNomEmp);
            }
        }

}//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed

//        try{
//            zafVenEmp.limpiar();
//            zafVenEmp.setCampoBusqueda(0);
//            zafVenEmp.setVisible(true);
//
//            if (zafVenEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
//                txtCodEmp.setText(String.valueOf(zafVenEmp.getValueAt(1)));
//                txtNomEmp.setText(String.valueOf(zafVenEmp.getValueAt(2)));
//            }
//        }catch (Exception ex){
//            objUti.mostrarMsgErr_F1(this, ex);
//        }
        strCodEmp = txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI == null) {
            int intOp = -1;
            objThrGUI = new ZafThreadGUI(intOp);
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
//        if(optFil.isSelected()){
//
//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                txtCodEmp.setEnabled(true);
//                butEmp.setEnabled(true);
//                txtCodEmp.requestFocus();
//            }
//            else{
//                //txtCodEmp.setEnabled(false);
//                //butEmp.setEnabled(false);
//                txtCodDep.requestFocus();
//            }
//
//            /*txtCodDep.setEnabled(true);
//            txtCodTra.setEnabled(true);
//            butDep.setEnabled(true);
//            butTra.setEnabled(true);*/
//        }
}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed

        if (optTod.isSelected()) {
//            txtCodEmp.setEnabled(false);
//            butEmp.setEnabled(false);
            txtCodEmp.setText("");
            txtNomEmp.setText("");
//            txtCodDep.setEnabled(false);
//            butDep.setEnabled(false);
            txtCodDep.setText("");
            txtNomDep.setText("");
//            txtCodTra.setEnabled(false);
//            butTra.setEnabled(false);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        //consultarRepDep();
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
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo, String strBusqueda, int tipo) {

        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
            }
        }
    }

    public void BuscarTra(String campo, String strBusqueda, int tipo) {

        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
    }

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed

        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep = txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep)) {
                if (txtNomDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtNomDep.setText(strDesLarDep);
            }
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep = txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        //consultarRepTra();
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
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        strCodTra = txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void cboObservacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboObservacionFocusGained
        // TODO add your handling code here:
        
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_cboObservacionFocusGained

    private class ZafThreadGUI extends Thread {

        private int intOp;

        public ZafThreadGUI(int intOp) {
            this.intOp = intOp;
        }

        public void run() {
            try
            {
                lblMsgSis.setText("Obteniendo datos...");
                pgrSis.setIndeterminate(true);

                cargarDat(intOp);
                tabGen.setSelectedIndex(1);
                
            }
            finally
            {
               pgrSis.setIndeterminate(false);
                pgrSis.setValue(0); 
            }
            
            objThrGUI = null;
        }
        
    }
    
    private class ZafTblCelRenHorExt extends javax.swing.table.DefaultTableCellRenderer
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

            String strObs = "";

            Object objObs = table.getValueAt(
                    row,
                    INT_TBL_DAT_OBSERVACION);

            if (objObs != null)
                strObs = objObs.toString();

            if (!isSelected)
            {
                if (strObs.equalsIgnoreCase("Falta marcación"))
                {
                    cmp.setBackground(new java.awt.Color(200,255,200));
                }
                else if (strObs.equalsIgnoreCase("No aprobado"))
                {
                    cmp.setBackground(new java.awt.Color(255,255,180));
                }
                else
                {
                    cmp.setBackground(java.awt.Color.WHITE);
                }

                cmp.setForeground(java.awt.Color.BLACK); 
                
                if (column == INT_TBL_DAT_VALOR_SOBRETIEMPO || column == INT_TBL_DAT_EMPLEADO)
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
                
                               
            }

            return cmp;
        }
    }
    
    
   
        
    /**
     * Se encarga de cargar la informacion en la tabla
     *
     * @return true si esta correcto y false si hay algun error
     */
    private boolean cargarDat(int intOp)
    {
        blnConsDat = true;
        boolean blnRes = false;
        blnMod = false;

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.Statement stmPer;
        java.sql.ResultSet rstLoc;
        java.sql.ResultSet rstPer;

        String strSql = "";
        String strSqlPer = "";
        String strFecDes = "";
        String strFecHas = "";

        Vector vecData;
        Vector vecDataCon;

        try
        {
            conn = java.sql.DriverManager.getConnection(
                    objZafParSis.getStringConexion(),
                    objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos());

            if (conn != null)
            {
                stmLoc = conn.createStatement();
                stmPer = conn.createStatement();

                vecDataCon = new Vector();

                int intCodEmp = 1;

                if (!txtCodEmp.getText().trim().equals(""))
                {
                    intCodEmp = Integer.parseInt(txtCodEmp.getText().trim());
                }

                int intAnio = Integer.parseInt(
                        cboPerAAAA.getSelectedItem().toString());

                int intMes = cboPerMes.getSelectedIndex() + 1;

                strSqlPer =
                        " SELECT MIN(fe_des) AS fe_des, "
                      + "        MAX(fe_has) AS fe_has "
                      + " FROM tbm_feccorrolpag "
                      + " WHERE co_emp = " + intCodEmp
                      + " AND ne_ani = " + intAnio
                      + " AND ne_mes = " + intMes;

                rstPer = stmPer.executeQuery(strSqlPer);

                if (rstPer.next())
                {
                    strFecDes = rstPer.getDate("fe_des").toString();
                    strFecHas = rstPer.getDate("fe_has").toString();
                }

                rstPer.close();
                rstPer = null;

                if (strFecDes.equals("") || strFecHas.equals(""))
                {
                    mostrarMsgInf("No existe período configurado");
                    return false;
                }

                strSql =
                    " SELECT * "
                  + " FROM vw_reporte_horext_zaf "
                  + " WHERE dia_solicitud BETWEEN '"
                  + strFecDes
                  + "' AND '"
                  + strFecHas
                  + "' ";

                if (objZafParSis.getCodigoUsuario() != 1   &&  objZafParSis.getCodigoUsuario() != 24 &&
                    objZafParSis.getCodigoUsuario() != 156 &&  objZafParSis.getCodigoUsuario() != 93 && 
                    objZafParSis.getCodigoUsuario() != 493)
                {
                    strSql +=
                        " AND co_dep IN ( "
                      + "     SELECT co_dep "
                      + "     FROM tbr_depprgusr "
                      + "     WHERE co_mnu = " + objZafParSis.getCodigoMenu()
                      + "     AND co_usr = " + objZafParSis.getCodigoUsuario()
                      + " ) ";
                }

                if (optFil.isSelected())
                {
                    if (!txtCodEmp.getText().trim().equals(""))
                    {
                        strSql += " AND co_emp="
                                + txtCodEmp.getText().trim();
                    }

                    if (!txtCodDep.getText().trim().equals(""))
                    {
                        strSql += " AND co_dep="
                                + txtCodDep.getText().trim();
                    }

                    if (!txtCodTra.getText().trim().equals(""))
                    {
                        strSql += " AND codigo="
                                + txtCodTra.getText().trim();
                    }

                    if (!cboObservacion.getSelectedItem().toString().equals("Todos"))
                    {
                        strSql += " AND observacion='"
                                + cboObservacion.getSelectedItem().toString()
                                + "'";
                    }
                }

                strSql +=
                        " ORDER BY empleado, dia_solicitud ";

                System.out.println("SQL: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);

                while (rstLoc.next())
                {
                    vecData = new Vector();
                    vecData.setSize(INT_TBL_DAT_NUM_TOT_CDI + 1);

                    vecData.set(INT_TBL_LINEA, "");

                    vecData.set(INT_TBL_DAT_CODIGO,
                            rstLoc.getString("codigo"));

                    vecData.set(INT_TBL_DAT_CEDULA,
                            rstLoc.getString("cedula"));

                    vecData.set(INT_TBL_DAT_EMPLEADO,
                            rstLoc.getString("empleado"));
                    
                    vecData.set(INT_TBL_DAT_DEP,
                            rstLoc.getString("departamento"));

                    vecData.set(INT_TBL_DAT_ENTRADA,
                            rstLoc.getString("entrada"));

                    vecData.set(INT_TBL_DAT_SALIDA,
                            rstLoc.getString("salida"));

                    vecData.set(INT_TBL_DAT_SOLICITUD,
                            rstLoc.getString("solicitud"));
                    
                    vecData.set(INT_TBL_DAT_USRING,
                            rstLoc.getString("tx_usr"));
                    
                    vecData.set(INT_TBL_DAT_BUTANESOLMAR,
                                "...");
                    

                    vecData.set(INT_TBL_DAT_DIA_SOLICITUD,
                            rstLoc.getString("dia_solicitud"));

                    vecData.set(INT_TBL_DAT_ESTADO,
                            rstLoc.getString("estado"));

                    vecData.set(INT_TBL_DAT_AUT_DESDE,
                            rstLoc.getString("autorizado_desde"));

                    vecData.set(INT_TBL_DAT_AUT_HASTA,
                            rstLoc.getString("autorizado_hasta"));

                    double dblSue = rstLoc.getDouble("sueldo");
                    vecData.set(INT_TBL_DAT_SUELDO,
                            objUti.redondear(dblSue, 2));

                    vecData.set(INT_TBL_DAT_MINUTOS,
                            rstLoc.getObject("minutos"));

                    vecData.set(INT_TBL_DAT_VALOR_MINUTO,
                            rstLoc.getObject("valor_minuto"));

                    vecData.set(INT_TBL_DAT_POR_PAGAR,
                            rstLoc.getObject("por_pagar"));

//                    double dblValSob = rstLoc.getDouble("valor_sobretiempo");
//                    vecData.set(INT_TBL_DAT_VALOR_SOBRETIEMPO,
//                            objUti.redondear(dblValSob, 2));

                    vecData.set(INT_TBL_DAT_VALOR_SOBRETIEMPO,
                            rstLoc.getObject("valor_sobretiempo"));

                    vecData.set(INT_TBL_DAT_OBSERVACION,
                            rstLoc.getString("observacion"));

                    vecDataCon.add(vecData);
                }

                objTblMod.setData(vecDataCon);
                tblDat.setModel(objTblMod);

                calcularTotales();

                lblMsgSis.setText(
                        "Se encontraron "
                        + tblDat.getRowCount()
                        + " registros");
                
                pgrSis.setIndeterminate(false);
                pgrSis.setValue(0);

                rstLoc.close();
                rstLoc = null;

                stmLoc.close();
                stmLoc = null;

                stmPer.close();
                stmPer = null;

                conn.close();
                conn = null;

                blnRes = true;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
    /**
     * Esta función calcula el total de las columnas especificadas.
     *
     * @return true: Si se pudo calcular el total de las columnas especificadas
     * sin que ocurra ningún error. <BR>false: En el caso contrario.
     */
    public boolean calcularTotales()
    {
        boolean blnRes = true;

        try
        {
            double dblTotMin = 0;
            double dblTotValSob = 0;

            for (int i = 0; i < tblDat.getRowCount(); i++)
            {
//                if (tblDat.getValueAt(i, INT_TBL_DAT_MINUTOS) != null
//                        && !tblDat.getValueAt(i, INT_TBL_DAT_MINUTOS).toString().equals(""))
//                {
//                    dblTotMin += Double.parseDouble(
//                            tblDat.getValueAt(i, INT_TBL_DAT_MINUTOS).toString());
//                }

                if (tblDat.getValueAt(i, INT_TBL_DAT_VALOR_SOBRETIEMPO) != null
                        && !tblDat.getValueAt(i, INT_TBL_DAT_VALOR_SOBRETIEMPO).toString().equals(""))
                {
                    dblTotValSob += Double.parseDouble(
                            tblDat.getValueAt(i, INT_TBL_DAT_VALOR_SOBRETIEMPO).toString());
                }
            }

            // Limpiar fila de totales
            for (int j = 0; j <= INT_TBL_DAT_NUM_TOT_CDI; j++)
            {
                objTblTot.setValueAt("", 0, j);
            }

            // Etiqueta
            objTblTot.setValueAt("TOTAL", 0, INT_TBL_DAT_EMPLEADO);

            // Totales          
            objTblTot.setValueAt(
                    objUti.redondear(dblTotValSob, 2),
                    0,
                    INT_TBL_DAT_VALOR_SOBRETIEMPO);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de
     * salidad.
     */
    private void exitForm() {

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboObservacion;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {

            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";

            switch (intCol) {

                
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

//    /**
//     * Esta clase crea un hilo que permite manipular la interface gráfica de
//     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
//     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
//     * internamente. Es decir a medida que se llevan a cabo los procesos se
//     * podría presentar mensajes informativos en un JLabel e ir incrementando un
//     * JProgressBar con lo cual el usuario estaría informado en todo momento de
//     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
//     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
//     * proceso.
//     */
//    private class ZafThreadGUIRpt extends Thread {
//
//        private int intIndFun;
//
//        public ZafThreadGUIRpt() {
//            intIndFun = 0;
//        }
//
//        public void run() {
//            switch (intIndFun) {
//                
//            }
//            objThrGUIRpt = null;
//        }
//
//        /**
//         * Esta función establece el indice de la función a ejecutar. En la
//         * clase Thread se pueden ejecutar diferentes funciones. Esta función
//         * sirve para determinar la función que debe ejecutar el Thread.
//         *
//         * @param indice El indice de la función a ejecutar.
//         */
//        public void setIndFunEje(int indice) {
//            intIndFun = indice;
//        }
//    }

    

    private boolean mostrarVenConEmp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));

                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));

                    } else {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));

                        } else {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));

                    } else {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));

                        } else {
                            txtNomEmp.setText(strNomEmp);
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

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));

                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));

                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));


                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));


                        } else {
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp() {
        boolean blnRes = true;
        String strTitVenCon = "";
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
 
            if (objZafParSis.getCodigoUsuario() == 1) {
                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp in (" + objZafParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }

            } 
            else {
 
                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp in (" + objZafParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }
            }
            vcoEmp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
     * @return true: Si no se presentó ningún problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));

                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                            
                        } else {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(3))))
                            //{

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));

                        } else {
                            txtNomDep.setText(strDesLarDep);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() {
        boolean blnRes = true;
        try {
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
            if (objZafParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objZafParSis.getCodigoMenu() + ")";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");


            if (objZafParSis.getCodigoEmpresa() == 1) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra and b.co_emp = 1) where a.st_reg like 'A' "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL =
                    " SELECT DISTINCT a.co_tra, a.tx_ape, a.tx_nom "
                  + " FROM tbm_tra a "
                  + " INNER JOIN tbm_traemp b ON (a.co_tra = b.co_tra) "
                  + " WHERE a.st_reg = 'A' "
                  + " AND b.co_emp = " + objZafParSis.getCodigoEmpresa()
                  + " AND b.co_dep IN ( "
                  + "     SELECT co_dep "
                  + "     FROM tbr_depprgusr "
                  + "     WHERE co_usr = " + objZafParSis.getCodigoUsuario()
                  + "     AND co_mnu = " + objZafParSis.getCodigoMenu()                  
                  + " ) ";                  
            }
            
            System.out.println("configurarVenConTra: "+strSQL);

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=3;
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void cargarPeriodo() {
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";

        try {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stmLoc = con.createStatement();
                strSQL = " select  ne_ani,ne_mes from tbm_ingegrmentra ";
                strSQL = strSQL + " group by ne_ani,ne_mes order by ne_ani desc, ne_mes desc";
                rstLoc = stmLoc.executeQuery(strSQL);
                DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox
                String strMes = "";
                Boolean blnUltFec = true;
                while (rstLoc.next()) {
                    if (blnUltFec) {
                        strMes = rstLoc.getString("ne_mes");
                        blnUltFec = false;
                    }
                    String strAnio = rstLoc.getString("ne_ani");
                    if (model.getIndexOf(strAnio) == -1) { //Pregunto si existe el anio para que no se repita al momento de llenarlo en el combobox
                        model.addElement(strAnio);
                        cboPerAAAA.addItem(strAnio);
                    }
                }
                int intMes = Integer.valueOf(strMes);
                cboPerMes.setSelectedIndex(intMes - 1);
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }
    }
    
    
    private void cargarComboObservacion()
    {
        cboObservacion.removeAllItems();

        cboObservacion.addItem("Todos");
        cboObservacion.addItem("OK");
        cboObservacion.addItem("Falta marcación");
        cboObservacion.addItem("No aprobado");

        cboObservacion.setSelectedIndex(0);
    }
    
    private void configurarColoresFila()
    {
        tblDat.setDefaultRenderer(Object.class,new ZafTblCelRenHorExt());
    }
    
    private void llamarPantSol(String strCodDoc) {        
        RecursosHumanos.ZafRecHum27.ZafRecHum27 obj1 = new RecursosHumanos.ZafRecHum27.ZafRecHum27(objZafParSis, "1", "4", "191", strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
   
}