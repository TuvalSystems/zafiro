/*
 * ZafCon15.java
 * "Movimientos de Mayor de Cuentas"
 * Created on 24 de enero de 2006, 11:06
 */
package Contabilidad.ZafCon15;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import java.awt.Color;
import java.math.BigDecimal;

/**
 * @author ilino
 */
public class ZafCon15 extends javax.swing.JInternalFrame 
{
    //Constantes: JTable
    final int INT_TBL_LIN = 0;
    final int INT_TBL_NOM_LOC = 1;
    final int INT_TBL_NUM_CTA = 2;
    final int INT_TBL_NOM_CTA = 3;
    final int INT_TBL_FEC_DIA = 4;
    final int INT_TBL_COD_DIA = 5;
    final int INT_TBL_TIP_DOC = 6;                                                        
    final int INT_TBL_NUM_DOC = 7;
    final int INT_TBL_NUM_CHQ = 8;
    final int INT_TBL_NOM_CLI = 9;       //Para Código de Cliente o Glosa.
    final int INT_TBL_MON_DEB = 10;
    final int INT_TBL_MON_HAB = 11;
    final int INT_TBL_VAL_SAL = 12;
    final int INT_TBL_USR_GEN_DOC = 13;
    final int INT_TBL_EST_REG = 14;
    
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
    private ZafTblCelRenLbl objTblCelRenLblHid, objTblCelRenLbl, objTblCelRenLblCol; //Render: Presentar JLabel en JTable.
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafSelFec objSelFec;
    private ZafRptSis objRptSis;
    private ZafMouMotAda objMouMotAda;                                                //ToolTipText en TableHeader.
    private ZafVenCon vcoPrv;   
    private ZafVenCon vcoCta;
    private ZafTblOrd objTblOrd;                                                      //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;    
    private ZafThreadGUI objThrGUI;
    private ZafTblTot objTblTot;                                                      //JTable de totales.
        
    private java.io.File fil;
    private java.util.Date datFecAux;                                                //Auxiliar: Para almacenar fechas.
    
    private Vector vecDat, vecCab, vecReg;
    
    private boolean blnCon;
    
    private BigDecimal bgdSalActExc;
    private BigDecimal bgdSalAntExc;
    private BigDecimal bgdSalAcuExc;    
    
    public String strSQLRep;
    private String strCodPrv, strDesLarPrv;                                           //Contenido del campo al obtener el foco.
    private String strIdePrv, strDirPrv;                                              //Campos: RUC y Direccián del Beneficiario.
    private String strDesCorCta, strDesLarCta, strAnoCbo_VenConCta, strAnoCboAnt;
    private String strSQL, strAux;
    private final String strVer = " v7.21 ";
    
    /**
     * Creates new form ZafCon15
     */
    public ZafCon15(ZafParSis obj)
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
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objPerUsr = new ZafPerUsr(objParSis);            
           
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setBounds(4, 4, 472, 72);
            //objSelFec.setFlechaDerechaHabilitada(false);
            objSelFec.setFlechaIzquierdaHabilitada(false);            
            panFec.add(objSelFec);
            cargarAnioCreados();
            
            //Configurar objetos.
            configurarTblDat();
            configurarVenConPrv();
            configurarVenConCta();
            
            txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCta.setBackground(objParSis.getColorCamposObligatorios());
            setEnabledCta(true);            
            txtNivCta.setEnabled(false);
            txtNivCta.setVisible(false);
            txtNivCta.setEditable(false);

            if (objParSis.getCodigoUsuario() != 1) {
                chkNotDebPrv.setVisible(false);
                chkNotDebPrv.setEnabled(false);
            }
            
            txtCodCta.setVisible(false);
            txtCodCta.setEditable(false);
            txtCodCta.setEnabled(false);
            cboEstDoc.setSelectedIndex(1);                 

            txtSal.setEnabled(false);
            txtSal.setEditable(false);
            txtSalAnt.setEnabled(false);
            txtSalAnt.setEditable(false);
            txtSalAcu.setEnabled(false);
            txtSalAcu.setEditable(false);
            
            txtSal.setBackground(new java.awt.Color(255, 255, 255));
            txtSalAnt.setBackground(new java.awt.Color(255, 255, 255));
            txtSalAcu.setBackground(new java.awt.Color(255, 255, 255));
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
            vecCab.add(INT_TBL_NOM_LOC, "Local");
            vecCab.add(INT_TBL_NUM_CTA, "Num.Cta.");
            vecCab.add(INT_TBL_NOM_CTA, "Nom.Cta.");
            vecCab.add(INT_TBL_FEC_DIA, "Fec.Doc.");
            vecCab.add(INT_TBL_COD_DIA, "Cód.Dia.");
            vecCab.add(INT_TBL_TIP_DOC, "Tip.Doc.");
            vecCab.add(INT_TBL_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_NUM_CHQ, "Núm.Chq.");
            vecCab.add(INT_TBL_NOM_CLI, "Cliente/Proveedor");
            vecCab.add(INT_TBL_MON_DEB, "Debe");
            vecCab.add(INT_TBL_MON_HAB, "Haber");
            vecCab.add(INT_TBL_VAL_SAL, "Saldo");
            vecCab.add(INT_TBL_USR_GEN_DOC, "Usr.Gen.Doc.");
            vecCab.add(INT_TBL_EST_REG, "Est.Reg.");

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
            tcmAux.getColumn(INT_TBL_NOM_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOM_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FEC_DIA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_COD_DIA).setPreferredWidth(60);                        
            tcmAux.getColumn(INT_TBL_TIP_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_MON_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_MON_HAB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_VAL_SAL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_USR_GEN_DOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_EST_REG).setPreferredWidth(50);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable TblDat: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DIA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_SAL, tblDat);
            if (!objPerUsr.isOpcionEnabled(2598)) {
                objTblMod.addSystemHiddenColumn(INT_TBL_USR_GEN_DOC, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            java.awt.Color colFonCol;
            colFonCol = new java.awt.Color(250, 250, 250);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblHid = new ZafTblCelRenLbl();
            objTblCelRenLblHid.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblHid.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_COD_DIA).setCellRenderer(objTblCelRenLblHid);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLblHid);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellRenderer(objTblCelRenLblHid);
            objTblCelRenLblHid.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLblHid.getRowRender(), INT_TBL_EST_REG).toString();
                    if (strEstReg.equals("A")) {
                        objTblCelRenLblHid.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLblHid.setForeground(Color.RED);
                    }
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_NOM_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_DIA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_USR_GEN_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_EST_REG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_EST_REG).toString();                    
                    if (strEstReg.equals("A")) {
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLbl.setForeground(Color.RED);
                    }
                }
            });

            objTblCelRenLblCol = new ZafTblCelRenLbl();
            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCol.setTipoFormato(objTblCelRenLblCol.INT_FOR_NUM);
            objTblCelRenLblCol.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_MON_DEB).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_MON_HAB).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_VAL_SAL).setCellRenderer(objTblCelRenLblCol);
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_EST_REG).toString();
                    if (strEstReg.equals("A")) {
                        objTblCelRenLblCol.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLblCol.setForeground(Color.RED);
                    }
                }
            });
            
            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);            

            //TOTALES
            int intCol[] = {INT_TBL_MON_DEB, INT_TBL_MON_HAB, INT_TBL_VAL_SAL};
            objTblTot = new ZafTblTot(spnTblRep, spnTotal, tblDat, tblTotal, intCol);                    
            
            tblTotal.setRowSelectionAllowed(true);
            tblTotal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblTotal.getColumnModel().getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tblTotal.getColumnModel().getColumn(INT_TBL_NOM_LOC).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NUM_CTA).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NOM_CTA).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_FEC_DIA).setPreferredWidth(90);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_TIP_DOC).setPreferredWidth(65);
            tblTotal.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NOM_CLI).setPreferredWidth(170);
            tblTotal.getColumnModel().getColumn(INT_TBL_MON_DEB).setPreferredWidth(80);
            tblTotal.getColumnModel().getColumn(INT_TBL_MON_HAB).setPreferredWidth(80);
            tblTotal.getColumnModel().getColumn(INT_TBL_VAL_SAL).setPreferredWidth(90);
            tblTotal.getColumnModel().getColumn(INT_TBL_USR_GEN_DOC).setPreferredWidth(100);
            tblTotal.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(50);
            
            //Configurar JTable TblTot: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DIA, tblTotal);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_SAL, tblTotal);

            if (!objPerUsr.isOpcionEnabled(2598)) 
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_USR_GEN_DOC, tblTotal);
            }  
            
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
                case INT_TBL_NOM_LOC:
                    strMsg = "Nombre de local";
                    break;
                case INT_TBL_NUM_CTA:
                    strMsg = "Número de cuenta";
                    break;
                case INT_TBL_NOM_CTA:
                    strMsg = "Nombre de cuenta";
                    break;    
                case INT_TBL_FEC_DIA:
                    strMsg = "Fecha de Diario";
                    break;
                case INT_TBL_COD_DIA:
                    strMsg = "Código de Diario";
                    break;
                case INT_TBL_TIP_DOC:
                    strMsg = "Tipo de documento";
                    break;
                case INT_TBL_NUM_DOC:
                    strMsg = "Número de Documento";
                    break;
                case INT_TBL_NUM_CHQ:
                    strMsg = "Número del Cheque";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg = "Nombre del Cliente";
                    break;
                case INT_TBL_MON_DEB:
                    strMsg = "Monto del Debe";
                    break;
                case INT_TBL_MON_HAB:
                    strMsg = "Monto del Haber";
                    break;
                case INT_TBL_VAL_SAL:
                    strMsg = "Saldos";
                    break;
                case INT_TBL_USR_GEN_DOC:
                    strMsg = "Persona que generó el documento";
                    break;
                case INT_TBL_EST_REG:
                    strMsg = "Estado del Registro";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            //strSQL+=" AND a1.st_prv='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 4;
            vcoPrv = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    } else {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv = vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv = vcoPrv.getValueAt(4);
                            objTblMod.removeAllRows();
                        } else {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripcián larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    } else {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv = vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv = vcoPrv.getValueAt(4);
                            objTblMod.removeAllRows();
                        } else {
                            txtDesLarPrv.setText(strDesLarPrv);
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
                /*
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_codCta";
                */
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_niv1";
                strSQL+=" FROM tbm_salCta AS a1";
                strSQL+=" LEFT JOIN tbm_plaCta as a2 on a1.co_emp = a2.co_emp and a1.co_cta = a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.tx_tipCta='D'";
                strSQL+=" AND a1.co_per=" + cboAno.getSelectedItem() + "01";
                strSQL+=" ORDER BY a1.tx_codCta";
            } else {
                /*
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL+=" FROM tbm_placta as a1";
                //strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                //strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" GROUP BY a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL+=" ORDER BY a1.tx_codCta";
                */
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
        
        if (!cboAno.getSelectedItem().toString().equalsIgnoreCase(strAnoCbo_VenConCta))
        {   
           configurarVenConCta();
           strAnoCbo_VenConCta = cboAno.getSelectedItem().toString();
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
                        txtNivCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText())) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        txtNivCta.setText(vcoCta.getValueAt(4));
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
                            txtNivCta.setText(vcoCta.getValueAt(4));
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
                        txtNivCta.setText(vcoCta.getValueAt(4));
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
                            txtNivCta.setText(vcoCta.getValueAt(4));
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
      panFec = new javax.swing.JPanel();
      panCta = new javax.swing.JPanel();
      txtDesCorCta = new javax.swing.JTextField();
      txtDesLarCta = new javax.swing.JTextField();
      butCta = new javax.swing.JButton();
      txtCodCta = new javax.swing.JTextField();
      chkNotDebPrv = new javax.swing.JCheckBox();
      lblCta = new javax.swing.JLabel();
      txtNivCta = new javax.swing.JTextField();
      cboEstDoc = new javax.swing.JComboBox();
      jLabel1 = new javax.swing.JLabel();
      lblPrv = new javax.swing.JLabel();
      txtCodPrv = new javax.swing.JTextField();
      txtDesLarPrv = new javax.swing.JTextField();
      butPrv = new javax.swing.JButton();
      cboAno = new javax.swing.JComboBox();
      panPrv = new javax.swing.JPanel();
      panRpt = new javax.swing.JPanel();
      panDat = new javax.swing.JPanel();
      spnTblRep = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      spnTotal = new javax.swing.JScrollPane();
      tblTotal = new javax.swing.JTable();
      panSal = new javax.swing.JPanel();
      panLblSal = new javax.swing.JPanel();
      lblSal = new javax.swing.JLabel();
      lblSalAnt = new javax.swing.JLabel();
      lblSalAcu = new javax.swing.JLabel();
      panTxtSal = new javax.swing.JPanel();
      txtSal = new javax.swing.JTextField();
      txtSalAnt = new javax.swing.JTextField();
      txtSalAcu = new javax.swing.JTextField();
      panTot = new javax.swing.JPanel();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      butCon = new javax.swing.JButton();
      butVisPre = new javax.swing.JButton();
      butExpExc = new javax.swing.JButton();
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

      panFec.setPreferredSize(new java.awt.Dimension(0, 100));
      panFec.setLayout(new java.awt.BorderLayout());
      panGen.add(panFec, java.awt.BorderLayout.NORTH);

      panCta.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
      panCta.setLayout(null);

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
      panCta.add(txtDesCorCta);
      txtDesCorCta.setBounds(80, 10, 130, 20);

      txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarCtaActionPerformed(evt);
         }
      });
      txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarCtaFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarCtaFocusLost(evt);
         }
      });
      panCta.add(txtDesLarCta);
      txtDesLarCta.setBounds(210, 10, 350, 20);

      butCta.setText("...");
      butCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCtaActionPerformed(evt);
         }
      });
      panCta.add(butCta);
      butCta.setBounds(560, 10, 20, 20);

      txtCodCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodCtaActionPerformed(evt);
         }
      });
      panCta.add(txtCodCta);
      txtCodCta.setBounds(60, 10, 20, 20);

      chkNotDebPrv.setText("No presentar Nota de débito de proveedores");
      panCta.add(chkNotDebPrv);
      chkNotDebPrv.setBounds(10, 114, 350, 14);

      lblCta.setText("Cuenta:");
      panCta.add(lblCta);
      lblCta.setBounds(10, 10, 60, 20);
      panCta.add(txtNivCta);
      txtNivCta.setBounds(580, 30, 40, 20);

      cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A: Activo", "I: Anulado" }));
      panCta.add(cboEstDoc);
      cboEstDoc.setBounds(210, 50, 230, 20);

      jLabel1.setText("Estado del documento:");
      panCta.add(jLabel1);
      jLabel1.setBounds(10, 50, 130, 20);

      lblPrv.setText("Proveedor:");
      lblPrv.setToolTipText("Proveedor");
      panCta.add(lblPrv);
      lblPrv.setBounds(10, 30, 70, 20);

      txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodPrvActionPerformed(evt);
         }
      });
      txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodPrvFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodPrvFocusLost(evt);
         }
      });
      panCta.add(txtCodPrv);
      txtCodPrv.setBounds(80, 30, 130, 20);

      txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarPrvFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarPrvFocusLost(evt);
         }
      });
      txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarPrvActionPerformed(evt);
         }
      });
      panCta.add(txtDesLarPrv);
      txtDesLarPrv.setBounds(210, 30, 350, 20);

      butPrv.setText("...");
      butPrv.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butPrvActionPerformed(evt);
         }
      });
      panCta.add(butPrv);
      butPrv.setBounds(560, 30, 20, 20);

      cboAno.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            cboAnoActionPerformed(evt);
         }
      });
      panCta.add(cboAno);
      cboAno.setBounds(580, 10, 64, 20);

      panGen.add(panCta, java.awt.BorderLayout.CENTER);

      panPrv.setPreferredSize(new java.awt.Dimension(100, 80));
      panPrv.setLayout(null);
      panGen.add(panPrv, java.awt.BorderLayout.SOUTH);

      tabFrm.addTab("General", panGen);

      panRpt.setLayout(new java.awt.BorderLayout());

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

      spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

      tblTotal.setModel(new javax.swing.table.DefaultTableModel(
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
      spnTotal.setViewportView(tblTotal);

      panDat.add(spnTotal, java.awt.BorderLayout.SOUTH);

      panRpt.add(panDat, java.awt.BorderLayout.CENTER);

      panSal.setPreferredSize(new java.awt.Dimension(72, 64));
      panSal.setLayout(new java.awt.BorderLayout());

      panLblSal.setPreferredSize(new java.awt.Dimension(460, 50));
      panLblSal.setLayout(new java.awt.GridLayout(3, 0));

      lblSal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      lblSal.setText("Saldo:  ");
      panLblSal.add(lblSal);

      lblSalAnt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      lblSalAnt.setText("Saldo Anterior:  ");
      panLblSal.add(lblSalAnt);

      lblSalAcu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      lblSalAcu.setText("Saldo Acumulado:  ");
      panLblSal.add(lblSalAcu);

      panSal.add(panLblSal, java.awt.BorderLayout.WEST);

      panTxtSal.setPreferredSize(new java.awt.Dimension(100, 50));
      panTxtSal.setLayout(new java.awt.GridLayout(3, 0));

      txtSal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panTxtSal.add(txtSal);

      txtSalAnt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtSalAnt.setFocusCycleRoot(true);
      panTxtSal.add(txtSalAnt);

      txtSalAcu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panTxtSal.add(txtSalAcu);

      panSal.add(panTxtSal, java.awt.BorderLayout.CENTER);

      panTot.setMinimumSize(new java.awt.Dimension(10, 6));
      panTot.setPreferredSize(new java.awt.Dimension(10, 6));
      panSal.add(panTot, java.awt.BorderLayout.NORTH);

      panRpt.add(panSal, java.awt.BorderLayout.SOUTH);

      tabFrm.addTab("Reporte", panRpt);

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

      butVisPre.setText("Vista preliminar");
      butVisPre.setPreferredSize(new java.awt.Dimension(130, 25));
      butVisPre.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butVisPreActionPerformed(evt);
         }
      });
      panBot.add(butVisPre);

      butExpExc.setText("Exportar");
      butExpExc.setPreferredSize(new java.awt.Dimension(100, 25));
      butExpExc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butExpExcActionPerformed(evt);
         }
      });
      panBot.add(butExpExc);

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
        txtSal.setText("");
        txtSalAnt.setText("");
        txtSalAcu.setText("");

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
     * Cerrar la aplicacián.
     */
    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar el programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            if (fil.exists()) {
                fil.delete();
            }
            this.dispose();
        }
    }

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        cargarReporte(1); //Rose
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
    txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
    strCodPrv = txtCodPrv.getText();
    txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)) {
        if (txtCodPrv.getText().equals("")) {
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrv(1);
        }
    } else {
        txtCodPrv.setText(strCodPrv);
    }
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
    txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
    strDesLarPrv = txtDesLarPrv.getText();
    txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)) {
        if (txtDesLarPrv.getText().equals("")) {
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrv(2);
        }
    } else {
        txtDesLarPrv.setText(strDesLarPrv);
    }
}//GEN-LAST:event_txtDesLarPrvFocusLost

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
    strCodPrv = txtCodPrv.getText();
    mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
    try 
    {
        String strNomEmp = objParSis.getNombreEmpresa();
        String strNumCta = txtDesCorCta.getText();
        String strNomCta = txtDesLarCta.getText();
        //String strFecDes=objUti.formatearFecha(txtFecIni.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
        //String strFecHas=objUti.formatearFecha(txtFecFin.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
        String strFecDes = objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
        String strFecHas = objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
        BigDecimal bgdSalActImp = bgdSalActExc;
        BigDecimal bgdSalAntImp = bgdSalAntExc;
        BigDecimal bgdSalAcuImp = bgdSalAcuExc;
        fil = new java.io.File("C:\\Zafiro\\Reportes\\Excel\\MMC.xls");
        //fil=new java.io.File("C:\\MMC.xls");
        String strNomHoj = "Hoja1";

        ZafExpExc objExc = new ZafExpExc(tblDat, fil, strNomHoj, strNomEmp, strNumCta, strNomCta, strFecDes, strFecHas, bgdSalActImp, bgdSalAntImp, bgdSalAcuImp);

        if (objExc.export()) 
        {
            mostrarMsgInf("El archivo se cargó correctamente.");
            //Runtime.getRuntime().exec( "C:\\Zafiro\\Reportes_impresos\\Excel\\MMC.xls"); 
            //Process p = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes_impresos/Excel/MMC.xls");
            Process p = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Excel/MMC.xls");
        } 
        else 
        {
            mostrarMsgErr("Falló la carga del archivo");
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}//GEN-LAST:event_butExpExcActionPerformed

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
            txtNivCta.setText("");
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
            txtNivCta.setText("");
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

   private void cboAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAnoActionPerformed

      if (!cboAno.getSelectedItem().toString().equalsIgnoreCase(strAnoCboAnt))
      {
         strAnoCboAnt = cboAno.getSelectedItem().toString();
         txtCodCta.setText("");
         txtDesCorCta.setText("");
         txtDesLarCta.setText("");
      }
   }//GEN-LAST:event_cboAnoActionPerformed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butCer;
   private javax.swing.JButton butCon;
   private javax.swing.JButton butCta;
   private javax.swing.JButton butExpExc;
   private javax.swing.JButton butPrv;
   private javax.swing.JButton butVisPre;
   private javax.swing.JComboBox cboAno;
   private javax.swing.JComboBox cboEstDoc;
   private javax.swing.JCheckBox chkNotDebPrv;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel lblCta;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblPrv;
   private javax.swing.JLabel lblSal;
   private javax.swing.JLabel lblSalAcu;
   private javax.swing.JLabel lblSalAnt;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panCta;
   private javax.swing.JPanel panDat;
   private javax.swing.JPanel panFec;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panGen;
   private javax.swing.JPanel panLblSal;
   private javax.swing.JPanel panPrgSis;
   private javax.swing.JPanel panPrv;
   private javax.swing.JPanel panRpt;
   private javax.swing.JPanel panSal;
   private javax.swing.JPanel panTot;
   private javax.swing.JPanel panTxtSal;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JScrollPane spnTblRep;
   private javax.swing.JScrollPane spnTotal;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblDat;
   private javax.swing.JTable tblTotal;
   private javax.swing.JTextField txtCodCta;
   private javax.swing.JTextField txtCodPrv;
   private javax.swing.JTextField txtDesCorCta;
   private javax.swing.JTextField txtDesLarCta;
   private javax.swing.JTextField txtDesLarPrv;
   private javax.swing.JTextField txtNivCta;
   private javax.swing.JTextField txtSal;
   private javax.swing.JTextField txtSalAcu;
   private javax.swing.JTextField txtSalAnt;
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
        BigDecimal bgdSalAct= BigDecimal.ZERO;
        BigDecimal bgdSalAcu= BigDecimal.ZERO;
        BigDecimal bgdSalAnt= BigDecimal.ZERO;
        strAux = "";
        String strFecIniCtaRes = "";
        String strAuxTmp = "";
        String strCodUsr = "";
        String strFilEstReg = "";
        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            switch (objSelFec.getTipoSeleccion()) {
                case 0: //Búsqueda por rangos
                    strAuxTmp += " AND (a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAuxTmp += " AND (a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAuxTmp += " AND (a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 3: //Todo.
                    break;
            }
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (cboEstDoc.getSelectedIndex() == 0) {//Todos
                strFilEstReg = " AND a2.st_reg IN('A','I')";
            } else if (cboEstDoc.getSelectedIndex() == 1) {//Activos
                strFilEstReg = " AND a2.st_reg='A'";
            } else if (cboEstDoc.getSelectedIndex() == 2) {//Inactivos
                strFilEstReg = " AND a2.st_reg='I'";
            }

            if (chkNotDebPrv.isSelected()) {
                strAux += " AND a2.co_tipDoc NOT IN (74)";
            }
            if (con != null) {
                stm = con.createStatement();
                //Obtener el número total de registros.
                strSQL ="";
                strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia, x.fe_dia, x.fe_ing,  x.descrip as descrip";
                strSQL+=" 	, CASE WHEN y.tx_nomCli IS NULL THEN x.tx_glo";
                strSQL+="         WHEN y.tx_nomCli = (cast('' as character varying)) THEN x.tx_glo ELSE y.tx_nomCli END AS tx_nomCli";
                strSQL+=" 	, CASE WHEN y.ne_numDoc IS NULL THEN x.ne_numDoc ELSE y.ne_numDoc END AS ne_numDoc";
                strSQL+="       , CASE WHEN y.ne_numDoc2 IS NULL THEN x.tx_numDia ELSE y.ne_numDoc2 END AS ne_numDoc2";
                strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb)    END AS monDeb";
                strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monHab)    END AS monHab";
                strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb - x.monHab)   END AS saldo";
                strSQL+="      , x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg, x.tx_nom, x.tx_codCta, x.tx_nomCta";
                strSQL+=" FROM( ";
                strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia";
                strSQL+="	     , a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, a2.tx_numDia as ne_numDoc, ''||Null as tx_numDia";
                strSQL+="            , a2.tx_glo as tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb ";
                strSQL+=" 	     , SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab";
                strSQL+=" 	     , SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL+=" 	     , d1.co_usr AS co_usrDia, d1.tx_nom AS tx_nomDia, a2.st_reg, a99.tx_nom, a5.tx_codCta, a5.tx_desLar as tx_nomCta ";
                strSQL+=" 	FROM (tbm_cabDia AS a2 LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) ";
                strSQL+=" 	INNER JOIN tbm_loc as a99 on(a2.co_emp=a99.co_emp and a2.co_loc=a99.co_loc)";
                strSQL+=" 	INNER JOIN tbm_detDia AS a3 ";
                strSQL+=" 	ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL+="       INNER JOIN tbm_salCta as a5 ON a3.co_emp = a5.co_emp and a3.co_cta = a5.co_cta";
                strSQL+="          and cast(a5.co_per as varchar) = ( select distinct substring(cast(a2.fe_dia as varchar), 1, 4) || '01' from tbm_salcta where co_emp = a3.co_emp and co_cta = a3.co_cta )";
                strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a3.co_cta=" + txtCodCta.getText() + "";
                //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
                strSQL+="       " + strAuxTmp;
                //strSQL+=" 	AND a2.st_reg='A'" + strAux + "";
                strSQL+=" 	" + strFilEstReg + " " + strAux + "";
                strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a2.tx_numDia, a2.tx_numDia, a2.tx_glo, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg, a99.tx_nom, a5.tx_codCta, a5.tx_desLar";
                strSQL+=" 	ORDER BY fe_dia, descrip, ne_numDoc";
                strSQL+=" ) AS x";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) {
                    strSQL+=" INNER JOIN(";
                } else {
                    strSQL+=" LEFT OUTER JOIN(";
                }
                strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia ";
                strSQL+=" 	    , a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc as ne_numDoc, ''||Null AS ne_numDoc2, a1.tx_nomCli, a2.tx_glo";
                strSQL+=" 	    , SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb";
                strSQL+=" 	    , SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab";
                strSQL+=" 	    , SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL+=" 	    , d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg ";
                strSQL+=" 	FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
                strSQL+=" 	INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
                strSQL+=" 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a3.co_cta=" + txtCodCta.getText() + "";
                //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
                strSQL+="" + strAuxTmp;
                //strSQL+=" 	AND a2.st_reg='A'" + strAux + "";
                strSQL+=" 	" + strFilEstReg + " " + strAux + "";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) {
                    //strSQL += " AND a1.co_cli LIKE '" + strCodPrv.replaceAll("'", "''") + "'";
                    strSQL+=" AND a1.co_cli = " + strCodPrv.replaceAll("'", "");
                }
                strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,a2.fe_dia,a2.fe_ing, a4.tx_desCor, a1.ne_numDoc, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, d1.co_usr, d1.tx_nom, a2.st_reg";
                strSQL+=" UNION ALL";
                strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia";
                strSQL+=" 	     , a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc1 as ne_numDoc, ''||a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb ";
                strSQL+=" 	     , SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL+=" 	     , d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg ";
                strSQL+=" 	FROM (tbm_cabPag AS a1 INNER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
                strSQL+=" 	LEFT OUTER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
                strSQL+=" 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a3.co_cta=" + txtCodCta.getText() + "";
                strSQL+="       " + strAuxTmp;
                //strSQL+="       AND a2.st_reg='A'" + strAux + "";
                strSQL+=" 	" + strFilEstReg + " " + strAux + "";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) 
                {
                   //strSQL+= " AND a1.co_cli LIKE '" + strCodPrv.replaceAll("'", "''") + "'";
                    strSQL+=" AND a1.co_cli = " + strCodPrv.replaceAll("'", "");
                }
                strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a1.ne_numDoc1, a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg";
                strSQL+=" 	) AS y";
                strSQL+=" ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_dia=y.co_dia";
                strSQL+=" GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia";
                strSQL+=" 	 , x.fe_dia, x.fe_ing, y.tx_nomCli, x.descrip, x.ne_numDoc, y.ne_numDoc, x.tx_numDia";
                strSQL+="  	 , x.tx_nomCli,x.tx_glo, y.ne_numDoc2, x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg, x.tx_nom, x.tx_codCta, x.tx_nomCta";
                strSQL+=" ORDER BY x.fe_dia, x.fe_ing, x.descrip, x.ne_numDoc";
                strSQLRep=strSQL;
                System.out.println("cargarDetReg: " + strSQL);
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
                {
                    if (blnCon) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_LIN, "");
                        vecReg.add(INT_TBL_NOM_LOC,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_NOM_CTA,rst.getString("tx_nomCta"));
                        vecReg.add(INT_TBL_FEC_DIA, rst.getString("fe_dia"));
                        vecReg.add(INT_TBL_COD_DIA, rst.getString("co_dia"));
                        vecReg.add(INT_TBL_TIP_DOC, rst.getString("descrip"));
                        vecReg.add(INT_TBL_NUM_DOC, rst.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_NUM_CHQ, rst.getString("ne_numdoc2"));
                        vecReg.add(INT_TBL_NOM_CLI, rst.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_MON_DEB, rst.getString("monDeb"));
                        vecReg.add(INT_TBL_MON_HAB, rst.getString("monHab"));
                        vecReg.add(INT_TBL_VAL_SAL, rst.getString("saldo"));
                        vecReg.add(INT_TBL_USR_GEN_DOC, "");

                        strCodUsr = rst.getObject("co_usrDia") == null ? "" : rst.getString("co_usrDia");
                        if (strCodUsr.equals("")) {
                            vecReg.setElementAt("" + rst.getString("tx_nomDoc"), INT_TBL_USR_GEN_DOC);
                        } else {
                            vecReg.setElementAt("" + rst.getString("tx_nomDia"), INT_TBL_USR_GEN_DOC);
                        }

                        vecReg.add(INT_TBL_EST_REG, rst.getString("st_reg"));
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    } else {
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
                objTblTot.calcularTotales();
                tabFrm.setSelectedIndex(1);
                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount() > 0) 
                {
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }

                //PARA SALDO ACTUAL
                bgdSalAct = getSalAct(con);
                //txtSal.setText("" + objUti.redondearBigDecimal(bgdSalAct, objParSis.getDecimalesMostrar()));
                bgdSalActExc =  objUti.redondearBigDecimal(bgdSalAct, objParSis.getDecimalesMostrar()) ;
                txtSal.setText("" + objUti.formatearNumero(bgdSalAct.doubleValue(), "###,###.##", true));

                //PARA SALDO ANTERIOR
                if ((txtNivCta.getText().equals("4")) || (txtNivCta.getText().equals("5")) || (txtNivCta.getText().equals("6")) || (txtNivCta.getText().equals("7")) || (txtNivCta.getText().equals("8")))
                {
                    java.util.Date datAux = Date.valueOf(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                    strFecIniCtaRes = "" + (datAux.getYear() + 1900) + "/01/01";
                    bgdSalAnt = getSalAnt(con, strFecIniCtaRes);
                } 
                else 
                {
                    bgdSalAnt = getSalAnt(con);  
                }
                //txtSalAnt.setText("" + objUti.redondear(bgdSalAnt,objParSis.getDecimalesMostrar()));
                bgdSalAntExc = objUti.redondearBigDecimal(bgdSalAnt, objParSis.getDecimalesMostrar());  
                txtSalAnt.setText("" + objUti.formatearNumero(bgdSalAnt.doubleValue(), "###,###.##", true));
                txtSalAnt.setEnabled(false);

                //PARA SALDO ACUMULADO
                bgdSalAcu = bgdSalAnt.add(bgdSalAct);
                
                //txtSalAcu.setText("" + objUti.redondearBigDecimal(bgdSalAcu, objParSis.getDecimalesMostrar()));
                bgdSalAcuExc = objUti.redondearBigDecimal(bgdSalAcu, objParSis.getDecimalesMostrar());
                txtSalAcu.setText("" + objUti.formatearNumero(bgdSalAcu.doubleValue(), "###,###.##", true));
                txtSalAcu.setEnabled(false);

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
    
    private BigDecimal getSalAnt(Connection conSalAnt)
    {
        String sqlSalAnt;
        BigDecimal bgdSalAnt = BigDecimal.ZERO;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try 
        {
            if (conSalAnt != null)
            {
                stmSal = conSalAnt.createStatement();

                if (chkNotDebPrv.isSelected()) 
                    strAux += " AND a1.co_tipDoc NOT IN(74)";

                //Obtener Saldo Anterior
                sqlSalAnt ="";
                sqlSalAnt+=" SELECT (CASE WHEN SUM(salAnt) IS NULL THEN 0 ELSE SUM(salAnt) END ) AS salAnt ";
                sqlSalAnt+=" FROM( ";
                sqlSalAnt+="      SELECT SUM( (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) AS salAnt ";
                sqlSalAnt+=" 	  FROM (tbm_cabdia AS a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                sqlSalAnt+=" 	  INNER JOIN tbm_detdia AS a2 ";
                sqlSalAnt+=" 	  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAnt+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAnt+="      AND a2.co_cta=" + txtCodCta.getText() + "";
                //sqlSalAnt+="    AND a1.fe_dia<'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";

                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Búsqueda por rangos
                        sqlSalAnt+=" AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";                        
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlSalAnt+=" AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                sqlSalAnt+="  AND a1.st_reg='A'";
                sqlSalAnt+="  "+ strAux;
                if (txtCodPrv.getText().length() > 0) {
                    sqlSalAnt+=" AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAnt+=" UNION ";
                sqlSalAnt+=" 	SELECT SUM(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) AS salAnt ";
                sqlSalAnt+=" 	FROM ( tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc )";
                sqlSalAnt+=" 	INNER JOIN tbm_detdia AS a2 ";
                sqlSalAnt+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAnt+="    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAnt+="    AND a2.co_cta=" + txtCodCta.getText() + "";
                //sqlSalAnt+="  AND a1.fe_dia<'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";

                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Búsqueda por rangos
                        sqlSalAnt+=" AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlSalAnt+=" AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                sqlSalAnt+="  AND a1.st_reg='A'";
                sqlSalAnt+="  "+strAux;
                if (txtCodPrv.getText().length() > 0) 
                {
                    sqlSalAnt+=" AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAnt+=" ) AS x";
                //System.out.println("Saldo anterior, sin parametro Fecha: " + sqlSalAnt);
                rstSal = stmSal.executeQuery(sqlSalAnt);
                if (rstSal.next()) {
                    bgdSalAnt = rstSal.getBigDecimal("salAnt");
                }
                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdSalAnt;
    }

    private BigDecimal getSalAnt(Connection conSalAnt, String fechaInicialCtasResultado) {
        BigDecimal bgdSalAnt = BigDecimal.ZERO;
        String sqlSalAnt;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try {
            conSalAnt = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conSalAnt != null) {
                stmSal = conSalAnt.createStatement();

                if (chkNotDebPrv.isSelected()) {
                    strAux += " AND a1.co_tipDoc NOT IN(74)";
                }

                //Obtener Saldo Anterior
                sqlSalAnt ="";
                sqlSalAnt+=" SELECT (CASE WHEN SUM(salAnt) IS NULL THEN 0 ELSE SUM(salAnt) END ) AS salAnt ";
                sqlSalAnt+=" FROM( ";
                sqlSalAnt+="       SELECT SUM(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt ";
                sqlSalAnt+=" 	   FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                sqlSalAnt+=" 	   INNER JOIN tbm_detdia AS a2 ";
                sqlSalAnt+=" 	   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAnt+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAnt+="       AND a2.co_cta=" + txtCodCta.getText() + "";
                sqlSalAnt+="       AND a1.fe_dia >= '" + fechaInicialCtasResultado + "' AND a1.fe_dia < '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                sqlSalAnt+="       AND a1.st_reg='A'";
                sqlSalAnt+="       "+ strAux;
                if (txtCodPrv.getText().length() > 0) {
                    sqlSalAnt+="     AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAnt+="  UNION ";
                sqlSalAnt+="       SELECT SUM(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt ";
                sqlSalAnt+="       FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                sqlSalAnt+=" 	   INNER JOIN tbm_detdia AS a2 ";
                sqlSalAnt+=" 	   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAnt+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAnt+="       AND a2.co_cta=" + txtCodCta.getText() + "";
                sqlSalAnt+="       AND a1.fe_dia >= '" + fechaInicialCtasResultado + "' AND a1.fe_dia < '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                sqlSalAnt+="       AND a1.st_reg='A'";
                sqlSalAnt+="       "+ strAux;
                if (txtCodPrv.getText().length() > 0) {
                    sqlSalAnt+="   AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAnt+=" ) AS x";
                //System.out.println("saldo anterior: " + sqlSalAnt);
                rstSal = stmSal.executeQuery(sqlSalAnt);
                if (rstSal.next()) {
                    bgdSalAnt = rstSal.getBigDecimal("salAnt");
                }
                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdSalAnt;
    }

    private BigDecimal getSalAct(Connection conSalAct) {
        BigDecimal bgdSalAct = BigDecimal.ZERO;
        String sqlSalAct;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try 
        {
            if (conSalAct != null) {
                if (chkNotDebPrv.isSelected()) {
                    strAux += " AND a1.co_tipDoc NOT IN(74)";
                }
                stmSal = conSalAct.createStatement();
                
                //Obtener Saldo Actual
                sqlSalAct =" ";
                sqlSalAct+=" SELECT (CASE WHEN SUM(salAct) IS NULL THEN 0 ELSE SUM(salAct) END) AS salAct ";
                sqlSalAct+=" FROM( ";
                sqlSalAct+="       SELECT SUM(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct ";
                sqlSalAct+=" 	   FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                sqlSalAct+=" 	   INNER JOIN tbm_detdia AS a2 ";
                sqlSalAct+=" 	   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAct+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAct+="       AND a2.co_cta=" + txtCodCta.getText() + "";
                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Búsqueda por rangos
                        sqlSalAct+=" AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlSalAct+=" AND (a1.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlSalAct+=" AND (a1.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                //sqlSalAct+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                sqlSalAct+="  AND a1.st_reg='A'";
                sqlSalAct+="  "+ strAux;
                if (txtCodPrv.getText().length() > 0) {
                    sqlSalAct += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAct+=" 	UNION ";
                sqlSalAct+=" 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAct ";
                sqlSalAct+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                sqlSalAct+=" 	INNER JOIN tbm_detdia AS a2 ";
                sqlSalAct+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                sqlSalAct+="    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlSalAct+="    AND a2.co_cta=" + txtCodCta.getText() + "";
                sqlSalAct+="    AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                sqlSalAct+="    AND a1.st_reg='A'";
                sqlSalAct+=" "+ strAux;
                if (txtCodPrv.getText().length() > 0) {
                    sqlSalAct += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                sqlSalAct+=" ) AS x";
                //System.out.println("saldo actual: " + sqlSalAct);
                rstSal = stmSal.executeQuery(sqlSalAct);
                if (rstSal.next()) {
                    bgdSalAct = rstSal.getBigDecimal("salAct");
                }
                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdSalAct;
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

    private void cargarReporte(int intTipo){
        if (objThrGUIRpt==null)
         {
             objThrGUIRpt=new ZafThreadGUIRpt();
             objThrGUIRpt.setIndFunEje(intTipo);
             objThrGUIRpt.start();
         }
    }
    
   /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    generarRpt(1);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIRpt=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
     /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
   private boolean generarRpt(int intTipRpt)
    {
        boolean blnRes = true;
        Connection conRpt;
        String strRutRpt, strNomRpt, strFecIniCtaRes;        
        int i, intNumTotRpt;
        BigDecimal bgdSalAcu= BigDecimal.ZERO;
        BigDecimal bgdSalAct= BigDecimal.ZERO;
        BigDecimal bgdSalAnt= BigDecimal.ZERO;
        strAux = "";
        try 
        {
            conRpt = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            //Saldo Anterior
            if ((txtNivCta.getText().equals("4")) || (txtNivCta.getText().equals("5")) || (txtNivCta.getText().equals("6")) || (txtNivCta.getText().equals("7")) || (txtNivCta.getText().equals("8")))
            {
                java.util.Date datAux = Date.valueOf(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                strFecIniCtaRes = "" + (datAux.getYear() + 1900) + "-01-01";
                bgdSalAnt = getSalAnt(conRpt, strFecIniCtaRes);
            } 
            else {
                bgdSalAnt = getSalAnt(conRpt);
            }
            bgdSalAct = getSalAct(conRpt);
            bgdSalAcu = bgdSalAnt.add(bgdSalAct);

            if (conRpt != null) 
            {
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) {
                    intNumTotRpt = objRptSis.getNumeroTotalReportes();
                    for (i = 0; i < intNumTotRpt; i++) {
                        if (objRptSis.isReporteSeleccionado(i)) {
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                                case 0:
                                default: 
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);

                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("co_emp",        objParSis.getCodigoEmpresa());
                                    mapPar.put("tx_nomemp",     objParSis.getNombreEmpresa());
                                    mapPar.put("fecha_rep",     "" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos()));
                                    mapPar.put("fecha_ini",     objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                                    mapPar.put("fecha_fin",     objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                                    mapPar.put("co_codcta",     new Integer(txtCodCta.getText()));
                                    mapPar.put("tx_codcta",     txtDesCorCta.getText());
                                    mapPar.put("tx_descta",     txtDesLarCta.getText());
                                    mapPar.put("nd_saldoant",   bgdSalAnt);
                                    mapPar.put("nd_saldoAct",   bgdSalAct);
                                    mapPar.put("nd_saldoacum",  objUti.redondearBigDecimal( (bgdSalAnt.add(bgdSalAct)) , objParSis.getDecimalesMostrar()));
                                    mapPar.put("co_prv",        (txtCodPrv.getText().equals("") ? new Integer("0") : (txtCodPrv.getText().equals("null") ? new Integer("0") : new Integer(txtCodPrv.getText()))));
                                    mapPar.put("strCamAudRpt",  "" + objParSis.getNombreUsuario() );
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                            }
                        }
                    }
                }
                conRpt.close();
                conRpt = null;
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);          
        }
        return blnRes;
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