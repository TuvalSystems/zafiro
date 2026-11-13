
/*
 * ZafMae60.java
 *
 * Created on Dec 18, 2010, 2:03:19 PM
 */
package Maestros.ZafMae60;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
//import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DropMode;
/**
 *
 * @author jayapata
 */
public class ZafMae60 extends javax.swing.JInternalFrame 
{
    //Constantes: Jtable
    private static final int INT_TBL_LINEA=0;
    private static final int INT_TBL_CHKBOD=1;
    private static final int INT_TBL_CODBOD=2;
    private static final int INT_TBL_NOMBOD=3;
    private static final int INT_TBL_NATBOD=4;
    private static final int INT_TBL_CHKPRE=5;
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_MNU=2;  
    private static final int INT_ARL_CON_COD_USR=3;  
    private int intIndiceCon=0;    
    
    //Variables
    private Connection con;
    private Statement  stm;
    private ResultSet  rst;
    private ZafParSis objParSis;
    private ZafUtil objUti; 
    private MiToolBar objTooBar;
    private ZafTblMod objTblMod;
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    //private ZafVenCon vcoPrg;
    private ZafVenConGP vcoPrg;
    //private ZafVenCon vcoUsr;
    private ZafVenConGP vcoUsr;
    private Vector vecDat, vecCab, vecReg;
    
    private String strSQL="", strAux="";
    private String strCodUsr="", strUsr="", strNomUsr="";
    private String strCodPrg="", strNomPrg="";
    private String strVer = " v0.8 ";
    
    javax.swing.JTextField txtCodUsr= new javax.swing.JTextField();
    
    private boolean blnHayCam = false; //Detecta si se hicieron cambios en el documento.

  
    /** Creates new form ZafMae60 */
    public ZafMae60(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            if (!configurarFrm()) 
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar = new MiToolBar(this);
            objTooBar.activarEtiqueta(true);
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            panBar.add(objTooBar);
            objTooBar.setVisibleAnular(false);
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Configurar las ZafVenConGP.
            configurarVenConPrg();
            configurarVenConUsr();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarTblDat() 
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CHKBOD, " ");
            vecCab.add(INT_TBL_CODBOD, "Cód.Bod");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod");
            vecCab.add(INT_TBL_NATBOD, "Naturaleza");
            vecCab.add(INT_TBL_CHKPRE, "Pre.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            // objMouMotAda=new ZafMouMotAda();
            // tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_NATBOD).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_CHKPRE).setPreferredWidth(50);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKBOD);
            vecAux.add("" + INT_TBL_NATBOD);
            vecAux.add("" + INT_TBL_CHKPRE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            new ZafTblEdi(tblDat);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKPRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenCmbBox = new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_NATBOD).setCellRenderer(objTblCelRenCmbBox);

            objTblCelEdiCmbBox = new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_NATBOD).setCellEditor(objTblCelEdiCmbBox);
            objTblCelEdiCmbBox.addItem("");
            objTblCelEdiCmbBox.addItem("Ingreso"); // I
            objTblCelEdiCmbBox.addItem("Egreso");  // E
            objTblCelEdiCmbBox.addItem("Ingreso/Egreso"); // A

            objTblCelEdiCmbBox.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    for (int p = (tblDat.getSelectedRow()); p <= (tblDat.getSelectedRow()); p++) {
                        if (!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHKBOD)) {
                            mostrarMsgInf("<HTML>No se puede seleccionar la naturaleza si la bodega no ha sido seleccionada.<BR>Seleccione la bodega y vuelva a seleccionar la naturaleza de la bodega.<HTML>");
                            objTblCelEdiCmbBox.setSelectedIndex(-1);
                            break;
                        }
                    }
                }
            });

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKPRE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel = tblDat.getSelectedRow();
                    int intColSel = tblDat.getSelectedColumn();
                    switch(intColSel)
                    {
                        case INT_TBL_CHKPRE:
                            if(!objTblMod.isChecked(intFilSel, INT_TBL_CHKBOD)) {
                                mostrarMsgInf("NO SE PUEDE MARCAR COMO PREDETERMINADA SELECCIONE PRIMERO LA BODEGA..");
                                objTblMod.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKPRE);
                            } 
                            else {
                                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                                    if(objTblMod.isChecked(intFilSel, INT_TBL_CHKBOD)) {
                                        if (intFilSel != i) {
                                            objTblMod.setValueAt(new Boolean(false), i, INT_TBL_CHKPRE);
                                        }
                                    }
                                }
                            }                            
                            break;
                        default:
                            break;
                    }
                }
            });
            
            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
            tblDat.setDragEnabled(true);
            tblDat.setDropMode(DropMode.INSERT_ROWS);
            tblDat.setDropTarget(new DropTarget()
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    int intFilMov[] = tblDat.getSelectedRows(); //Filas a Mover.
                    Point point = dtde.getLocation();
                    int intRowOrigen = tblDat.getSelectedRow();
                    int intRowPtoDestino = tblDat.rowAtPoint(point);
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    if (intRowOrigen > -1  && intRowPtoDestino>-1) //MOVER DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMov.length; i++)  {
                            if (intFilMov[i] < 0)
                                blnMov=false;
                        }
                        if(blnMov) {
                            objTblMod.moveRows(intFilMov, intRowPtoDestino);
                        }                        
                    }   
                }
            });
            //</editor-fold>  
            
        }
        catch(Exception e) {   blnRes=false;       objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBodegas()
    {
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bod, a.tx_nom as tx_nomBod";
                strSQL+=" FROM tbm_bod AS a";
                strSQL+=" WHERE a.st_Reg IN ('A') ";
                strSQL+=" AND a.co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND a.st_reg NOT IN('E')"; 
                strSQL+=" ORDER BY a.co_bod";
                //System.out.println("cargarBodegas: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){

                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,  "");
                    vecReg.add(INT_TBL_CHKBOD, new Boolean(false) );
                    vecReg.add(INT_TBL_CODBOD, "" + rst.getObject("co_bod")==null?"":rst.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD, "" + rst.getObject("tx_nomBod")==null?"":rst.getString("tx_nomBod"));
                    vecReg.add(INT_TBL_NATBOD, "");
                    vecReg.add(INT_TBL_CHKPRE, new Boolean(false) );
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)      {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }             
    
    private boolean configurarVenConPrg() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_mnu");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Programa");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("494");
            
            //Armar la sentencia SQL.
            strSQL=" SELECT a1.co_mnu, a1.tx_nom "+
                   " FROM tbm_mnusis AS a1 "+
                   " WHERE (a1.tx_tipmnu='C'or a1.tx_tipmnu='R') and a1.tx_nom!='' "+
                   " AND a1.st_reg<>'E' AND a1.tx_acc IS NOT NULL ";
                   //" ORDER BY a1.tx_nom ";
            
            vcoPrg = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Programas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoPrg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoPrg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {      blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    

    private boolean configurarVenConUsr() 
    {
        String strSQL="";
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("85");
            arlAncCol.add("250");
            
            //Armar la sentencia SQL.
            if (objTooBar.getEstado() == 'c') 
            {
                strSQL = " SELECT a1.co_usr, a1.tx_usr, a1.tx_nom FROM tbm_usr as a1 WHERE st_reg='A'";// ORDER BY a1.tx_usr";
            } 
            else
            {
                strSQL = " SELECT a1.co_usr, a1.tx_usr, a1.tx_nom  "
                       + " FROM tbr_perusr as a "
                       + " INNER JOIN tbm_usr as a1 ON (a1.co_usr=a.co_usr) "
                       + " WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " AND a.co_loc=" + objParSis.getCodigoLocal() + " "
                       + " AND a.co_mnu=" + txtCodPrg.getText() + " AND a1.st_reg='A'  ";
                       //+ " ORDER BY a1.tx_usr ";
            }
            
            vcoUsr = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoUsr.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {      blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
   
    private boolean mostrarVenConPrg(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrg.setCampoBusqueda(1);
                    vcoPrg.show();
                    if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoPrg.buscar("a1.co_mnu", txtCodPrg.getText()))
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    }
                    else 
                    {
                        vcoPrg.setCampoBusqueda(0);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                        {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                            txtCodUsr.setText("");
                            txtUsr.setText("");
                            txtNomUsr.setText("");
                        } 
                        else 
                        {
                            txtCodPrg.setText(strCodPrg);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Programa".
                    if (vcoPrg.buscar("a1.tx_nom", txtNomPrg.getText())) 
                    {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                        txtCodUsr.setText("");
                        txtUsr.setText("");
                        txtNomUsr.setText("");
                    } 
                    else 
                    {
                        vcoPrg.setCampoBusqueda(1);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) 
                        {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                            txtCodUsr.setText("");
                            txtUsr.setText("");
                            txtNomUsr.setText("");
                        } 
                        else 
                        {
                            txtNomPrg.setText(strNomPrg);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
 
    private boolean mostrarVenConUsr(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.show();
                    if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoUsr.buscar("a1.co_usr", txtCodUsr.getText()))
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    }
                    else 
                    {
                        vcoUsr.setCampoBusqueda(0);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        } 
                        else 
                        {
                            txtCodUsr.setText(strCodUsr);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Usuario".
                    if (vcoUsr.buscar("a1.tx_usr", txtUsr.getText())) 
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    } 
                    else 
                    {
                        vcoUsr.setCampoBusqueda(1);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        } 
                        else 
                        {
                            txtUsr.setText(strUsr);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre".
                    if (vcoUsr.buscar("a1.tx_nom", txtNomUsr.getText())) 
                    {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtUsr.setText(vcoUsr.getValueAt(2));
                        txtNomUsr.setText(vcoUsr.getValueAt(3));
                    } 
                    else 
                    {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) 
                        {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtUsr.setText(vcoUsr.getValueAt(2));
                            txtNomUsr.setText(vcoUsr.getValueAt(3));
                        } 
                        else 
                        {
                            txtNomUsr.setText(strNomUsr);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    private void setEditable(boolean editable) 
    {
        if (editable == true) 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) 
    {
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
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
        lblPrg = new javax.swing.JLabel();
        txtCodPrg = new javax.swing.JTextField();
        txtNomPrg = new javax.swing.JTextField();
        butPrg = new javax.swing.JButton();
        lblUsr = new javax.swing.JLabel();
        txtUsr = new javax.swing.JTextField();
        txtNomUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        panGenDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();

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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 60));
        panGenCab.setLayout(null);

        lblPrg.setText("Programa:"); // NOI18N
        panGenCab.add(lblPrg);
        lblPrg.setBounds(10, 10, 110, 20);

        txtCodPrg.setBackground(objParSis.getColorCamposObligatorios());
        txtCodPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusLost(evt);
            }
        });
        txtCodPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrgActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodPrg);
        txtCodPrg.setBounds(120, 10, 70, 20);

        txtNomPrg.setBackground(objParSis.getColorCamposObligatorios());
        txtNomPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusLost(evt);
            }
        });
        txtNomPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrgActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomPrg);
        txtNomPrg.setBounds(190, 10, 430, 20);

        butPrg.setText(".."); // NOI18N
        butPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrgActionPerformed(evt);
            }
        });
        panGenCab.add(butPrg);
        butPrg.setBounds(620, 10, 20, 20);

        lblUsr.setText("Usuario:"); // NOI18N
        panGenCab.add(lblUsr);
        lblUsr.setBounds(10, 30, 110, 20);

        txtUsr.setBackground(objParSis.getColorCamposObligatorios());
        txtUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrFocusLost(evt);
            }
        });
        txtUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrActionPerformed(evt);
            }
        });
        panGenCab.add(txtUsr);
        txtUsr.setBounds(120, 30, 70, 20);

        txtNomUsr.setBackground(objParSis.getColorCamposObligatorios());
        txtNomUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusLost(evt);
            }
        });
        txtNomUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomUsrActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomUsr);
        txtNomUsr.setBounds(190, 30, 430, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panGenCab.add(butUsr);
        butUsr.setBounds(620, 30, 20, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDat.setLayout(new java.awt.BorderLayout());

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
        tblDat.setToolTipText("<HTML>Para establecer un orden a las bodegas<BR>Seleccionar la fila y soltar en la ubicación deseada.</HTML>");
        spnDat.setViewportView(tblDat);

        panGenDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
        txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

    private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
        strNomPrg=txtNomPrg.getText();
        txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

    private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
        if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) 
        {
            if (txtNomPrg.getText().equals(""))
            {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
                mostrarVenConPrg(2);
        }
        else
            txtNomPrg.setText(strNomPrg);
}//GEN-LAST:event_txtNomPrgFocusLost

    private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
        txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

    private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
        strCodPrg=txtCodPrg.getText();
        txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained

    private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
        if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) 
        {
            if (txtCodPrg.getText().equals(""))
            {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
                mostrarVenConPrg(1);
        }
        else
            txtCodPrg.setText(strCodPrg);
}//GEN-LAST:event_txtCodPrgFocusLost

    private void butPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrgActionPerformed
        mostrarVenConPrg(0);
}//GEN-LAST:event_butPrgActionPerformed

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        strUsr=txtUsr.getText();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
         if (!txtUsr.getText().equalsIgnoreCase(strUsr))
         {
            if (txtUsr.getText().equals("")) 
            {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                if(validaPrgSel())
                {
                   configurarVenConUsr();
                   mostrarVenConUsr(2);
                }
                else{
                    txtCodUsr.setText("");
                    txtUsr.setText("");
                    txtNomUsr.setText("");                 
                }                  
            }
        }
        else
            txtUsr.setText(strUsr);
    }//GEN-LAST:event_txtUsrFocusLost


    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        configurarVenConUsr();
        if(validaPrgSel())
        {
            configurarVenConUsr();
            mostrarVenConUsr(0);
        }
    }//GEN-LAST:event_butUsrActionPerformed

    private void txtNomUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusLost
        if (!txtNomUsr.getText().equalsIgnoreCase(strNomUsr)) 
        {
            if (txtNomUsr.getText().equals("")) 
            {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }
            else
            {
                if(validaPrgSel())
                {
                   configurarVenConUsr();
                   mostrarVenConUsr(3);
                }
                else{
                    txtCodUsr.setText("");
                    txtUsr.setText("");
                    txtNomUsr.setText("");                 
                }                  
            }
        }
        else
           txtNomUsr.setText(strNomUsr);
    }//GEN-LAST:event_txtNomUsrFocusLost

    private void txtNomUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsrActionPerformed
        txtNomUsr.transferFocus();
    }//GEN-LAST:event_txtNomUsrActionPerformed

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        txtUsr.transferFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void txtNomUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusGained
        strNomUsr=txtNomUsr.getText();
        txtNomUsr.selectAll();
    }//GEN-LAST:event_txtNomUsrFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify  */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPrg;
    private javax.swing.JButton butUsr;
    private javax.swing.JLabel lblPrg;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUsr;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDat;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtNomPrg;
    private javax.swing.JTextField txtNomUsr;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    /**
     * Cerrar el formulario.
     */
    private void exitForm()
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }
    
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". 
     * Presenta las opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }        
    
    private void limpiarFrm() 
    {
        strCodPrg = "";
        strNomPrg = "";
        strUsr = "";
        strNomUsr = "";
        strCodUsr = "";

        txtCodPrg.setText("");
        txtNomPrg.setText("");

        txtCodUsr.setText("");
        txtUsr.setText("");
        txtNomUsr.setText("");

        objTblMod.removeAllRows();
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux, true))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){                        
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
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        //Valida que se seleccione un programa.
        if(!validaPrgSel()){
            return false;
        }

        //Valida que se seleccione un usuario.
        if(!validaUsrSel()){
            return false;
        }

        //Valida datos de la tabla
        if(!validaTblDatSel()){
            return false;
        }            

        if(!validaExiPerUsr()){
            mostrarMsgInf("<HTML>Ya existen datos ingresados para este usuario.</HTML>");
            return false;
        }
        
        return true;
    }
    
    private boolean validaPrgSel()
    {
        boolean blnRes=true;
        if(objTooBar.getEstado()!='c'){
            if (txtCodPrg.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo << Programa >> es obligatorio.<BR>Elija un programa y vuelva a intentarlo.</HTML>");
                txtCodPrg.requestFocus();
                blnRes=false;
            }        
        }

        return blnRes;
    }
    
    private boolean validaUsrSel()
    {
        boolean blnRes=true;
        if(objTooBar.getEstado()!='c'){
            if (txtUsr.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo << Usuario >> es obligatorio.<BR>Elija un usuario y vuelva a intentarlo.</HTML>");
                txtUsr.requestFocus();
                blnRes=false;
            }
        }
        return blnRes;
    }    
    
    private boolean validaTblDatSel()
    {
        boolean blnRes=true;
        String strNatBod = "";
        int intExiDatTbl = 0;
        int intExiDatPre = 0;
        int intExiDatNat = 0;            
        for (int i=0;i<objTblMod.getRowCountTrue();i++){
            if(objTblMod.isChecked(i, INT_TBL_CHKBOD)){
                intExiDatTbl = 1;

                strNatBod = tblDat.getValueAt(i,INT_TBL_NATBOD)==null?"":(objTblMod.getValueAt(i,INT_TBL_NATBOD).equals("null")? "":objTblMod.getValueAt(i,INT_TBL_NATBOD).toString());
                if (strNatBod.equals("")) {
                    intExiDatNat = 1;
                }

                if(objTblMod.isChecked(i, INT_TBL_CHKPRE)){
                    intExiDatPre = 1;
                }
            }
        }

        if (intExiDatTbl == 0) {
            mostrarMsgInf("<HTML>Debe ingresar datos en el detalle.</HTML>");
            blnRes=false;
        }

        if (intExiDatNat == 1) {
            mostrarMsgInf("<HTML>Debe seleccionar la naturaleza de la bodega.</HTML>");
            blnRes=false;
        }

        if (intExiDatPre == 0) {
            mostrarMsgInf("<HTML>Debe seleccionar la bodega predeterminada.</HTML>");
            blnRes=false;
        } 
        return blnRes;
    }     
    
    private boolean validaExiPerUsr() 
    {
        boolean blnRes = true;
        if(objTooBar.getEstado()=='n') 
        {
            Connection conLoc;
            Statement stmLoc;
            ResultSet rstLoc;
            try 
            {
                conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if (conLoc != null) 
                {
                    stmLoc = conLoc.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT co_mnu FROM tbr_bodlocprgusr ";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                    strSQL+=" AND co_usr= " + txtCodUsr.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    conLoc.close();
                    conLoc=null;
                }
            }
            catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
            catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        }
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

        public void clickConsultar()
        {
        }   
        
        public void clickInsertar() 
        {
            try
            {
                limpiarFrm();
                setEditable(true);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.setDataModelChanged(false);
                cargarBodegas();
            }
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        }

        public void clickModificar() 
        {
            try 
            {
                setEditable(true);

                java.awt.Color colBack;
                colBack = txtCodPrg.getBackground();

                bloquea(txtCodPrg, colBack, false);
                bloquea(txtNomPrg, colBack, false);
                bloquea(txtUsr, colBack, false);
                bloquea(txtNomUsr, colBack, false);

                butPrg.setEnabled(false);
                butUsr.setEnabled(false);

                objTblMod.setDataModelChanged(false);
            }
            catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
        }
        
        public void clickAnular()
        {
        }
        public void clickEliminar()
        {
        }    
        public void clickImprimir()
        {
        }    
        public void clickVisPreliminar()
        {
        }        
        public void clickAceptar()
        {
        }
        public void clickCancelar()
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
            if(!insertarReg())
                return false;
            return true;
        }
        
        public boolean modificar()
        {
            if(!modificarReg())
                return false;
            return true;
        }        

        public boolean anular()
        {
            return true;
        }
        
        public boolean eliminar()
        {
            if(!eliminarReg())
                return false;
            return true;
        }
        
        public boolean imprimir()
        {
            return true;
        }        
        public boolean vistaPreliminar()
        {
            return true;
        }

        public boolean aceptar()
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
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;            
        }


        public boolean beforeConsultar(){
            return true;
        }
        
        public boolean beforeInsertar()
        {
             if(!isCamVal())
                return false;            

            return true;
        }  
        
        public boolean beforeModificar()
        {
             if(!isCamVal())
                return false;            

            return true;
        }  
        
        public boolean beforeAnular()
        {
            return true;
        }
        public boolean beforeEliminar()
        {
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
            objTooBar.setEstado('w');
            return true;
        }
        public boolean afterModificar()
        {
            return true;
        }
        public boolean afterAnular()
        {
            return true;
        }        
        public boolean afterEliminar()
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
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_mnu, a.co_usr";
                strSQL+=" FROM tbr_bodlocprgusr AS a ";
                strSQL+=" INNER JOIN tbm_usr AS a1 ON (a.co_usr=a1.co_usr)";
                strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A' ";
                        
                if(!txtCodPrg.getText().equals(""))
                    strSQL+=" AND a.co_mnu="+txtCodPrg.getText();

                if(!txtCodUsr.getText().equals(""))
                    strSQL+=" AND a.co_usr="+txtCodUsr.getText();

                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_mnu, a.co_usr";
                //strSQL+=" ORDER BY a.co_usr ";
                //System.out.println("consultarReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,     rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,     rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_MNU,     rst.getInt("co_mnu"));
                    arlRegCon.add(INT_ARL_CON_COD_USR,     rst.getInt("co_usr"));
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
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDetReg())
                {
                    blnRes=true;               
                }                
            }
            else
            {
                mostrarMsgInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    } 
    
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_mnu, a1.tx_nom as tx_nomPrg, a.co_usr, a2.tx_usr, a2.tx_nom as tx_nomUsr ";
                strSQL+=" FROM tbr_bodlocprgusr AS a ";
                strSQL+=" INNER JOIN tbm_mnusis AS a1 ON (a.co_mnu=a1.co_mnu) ";
                strSQL+=" INNER JOIN tbm_usr AS a2 ON (a.co_usr=a2.co_usr)";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_mnu=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_MNU);
                strSQL+=" AND a.co_usr=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_USR);
                strSQL+=" GROUP BY a.co_emp, a.co_loc,  a.co_mnu, a1.tx_nom, a.co_usr, a2.tx_usr, a2.tx_nom ";
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {    
                    strAux=rst.getString("co_mnu");
                    txtCodPrg.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_nomPrg");
                    txtNomPrg.setText((strAux==null)?"":strAux);
                                      
                    strAux=rst.getString("co_usr");
                    txtCodUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usr");
                    txtUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_nomUsr");
                    txtNomUsr.setText((strAux==null)?"":strAux);   
                  
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
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
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)       {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bod, a.tx_nom as tx_nomBod, a1.tx_natBod, a1.st_reg";
                strSQL+=" FROM tbm_bod AS a";
                strSQL+=" LEFT OUTER JOIN( ";
                strSQL+="      SELECT co_emp, co_bod, tx_natBod, st_reg, ne_ord";
                strSQL+="      FROM tbr_bodlocprgusr";
                strSQL+="      WHERE co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+="      AND co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+="      AND co_mnu="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_MNU);
                strSQL+="      AND co_usr="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_USR);
                strSQL+=" ) AS a1 ON a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod";
                strSQL+=" WHERE a.st_Reg IN ('A')";
                strSQL+=" AND a.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" ORDER BY a1.ne_ord, a.co_bod";
                //System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,  "");
                    vecReg.add(INT_TBL_CHKBOD, null );
                    vecReg.add(INT_TBL_CODBOD, "" + rst.getObject("co_bod")==null?"":rst.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD, "" + rst.getObject("tx_nomBod")==null?"":rst.getString("tx_nomBod"));
                    vecReg.add(INT_TBL_NATBOD, "");
                    vecReg.add(INT_TBL_CHKPRE, null );
                    vecDat.add(vecReg);
                    
                    strAux=rst.getObject("tx_natBod")==null?"":rst.getString("tx_natBod");
                    if(!strAux.equals("")){
                        if(strAux.equals("I")){
                            vecReg.setElementAt(new String("Ingreso"),INT_TBL_NATBOD);
                        }
                        else if(strAux.equals("E")){
                            vecReg.setElementAt(new String("Egreso"),INT_TBL_NATBOD);
                        }
                        else if(strAux.equals("A")){
                            vecReg.setElementAt(new String("Ingreso/Egreso"),INT_TBL_NATBOD);
                        }                        
                    }
                    
                    strAux=rst.getObject("st_reg")==null?"":rst.getString("st_reg");
                    if(!strAux.equals("")){
                        vecReg.setElementAt(new Boolean(true),INT_TBL_CHKBOD);
                    }
                    if(strAux.equals("P")){
                        vecReg.setElementAt(new Boolean(true),INT_TBL_CHKPRE);
                    }                    
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)      {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }      
    
    private boolean insertarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(insertarBodLocUsr()){
                    con.commit();
                    blnRes=true;
                }else 
                    con.rollback(); 
                con.close();                                            
                con=null;
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;                  
    }     
    
    private boolean insertarBodLocUsr() 
    {
        boolean blnRes = false;
        int intOrdBod=0;   //Variable usada para el orden de las bodegas.
        try 
        {
            if (con != null) 
            {
                stm = con.createStatement();
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++) 
                { 
                    if(objTblMod.isChecked(i, INT_TBL_CHKBOD))
                    {
                        intOrdBod++; 
                        //Bodega
                        String strCodBod = objTblMod.getValueAt(i, INT_TBL_CODBOD).toString();
                        //Naturaleza de Bodega
                        strAux = objTblMod.getValueAt(i, INT_TBL_NATBOD) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NATBOD).equals("null") ? "" : objTblMod.getValueAt(i, INT_TBL_NATBOD).toString());
                        String strNatBod= (strAux.equals("Ingreso")?"I":strAux.equals("Egreso")?"E": strAux.equals("Ingreso/Egreso")? "A":"");
                        //Estado de registro
                        String strEstReg= (objTblMod.isChecked(i, INT_TBL_CHKPRE)? "P":"A") ;
                        
                        //Armar sentencia SQL
                        //System.out.println("<"+intOrdBod+" >Bodega: "+objTblMod.getValueAt(i, INT_TBL_NOMBOD).toString());
                        strSQL="";
                        strSQL+=" INSERT INTO tbr_bodlocprgusr(co_emp, co_loc, co_mnu, co_usr, co_bod, tx_natbod, st_reg, ne_ord) ";
                        strSQL+=" VALUES(";
                        strSQL+="  " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodPrg.getText() + " ";
                        strSQL+=" ," + txtCodUsr.getText() + ", " + strCodBod + ", '" + strNatBod + "', '" + strEstReg + "' , "+intOrdBod+"";
                        strSQL+=" );";
                        stm.executeUpdate(strSQL);                        
                    }
                }               
                stm.close();
                stm = null;
                blnRes = true;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

    private boolean modificarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(eliminarBodLocUsr()){
                    if(insertarBodLocUsr()){
                        con.commit();
                        blnRes=true;
                    }else 
                        con.rollback(); 
                }else 
                    con.rollback();                     
                con.close();                                            
                con=null;
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;                  
    }  
    
    private boolean eliminarBodLocUsr() 
    {
        boolean blnRes = false;
        try 
        {
            if (con != null) 
            {
                stm = con.createStatement();
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" DELETE FROM tbr_bodlocprgusr ";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                strSQL+=" AND co_usr=" + txtCodUsr.getText();
                stm.executeUpdate(strSQL);                        
                stm.close();
                stm = null;
                blnRes = true;
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    } 
    
    private boolean eliminarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(eliminarBodLocUsr()){
                    con.commit();
                    blnRes=true;
                }else 
                    con.rollback();                     
                con.close();                                            
                con=null;
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;                  
    }      

   
        
        
        
        
        
}
