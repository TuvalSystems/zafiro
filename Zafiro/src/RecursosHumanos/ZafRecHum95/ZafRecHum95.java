package RecursosHumanos.ZafRecHum95;


import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotMarInc;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Solicitudes de horas suplementarias/extraordinarias
 *
 * @author Roberto Flores Guayaquil 03/04/2012
 */
public class ZafRecHum95 extends javax.swing.JInternalFrame {

    private java.sql.Connection CONN_GLO = null;
    private java.sql.Statement STM_GLO = null;
    private java.sql.ResultSet rstCab = null;

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;
    private ZafTblCelRenChk zafTblCelRenChkLab;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecSol;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private java.util.Date datFecAux;

    private ZafVenMotHorSupExt zafVenMotHorSupExt;              //Ventana de consulta "Motivos de horas suplementarias/extraordinarias".
    
    private ZafVenMotMarInc zafVenMotMarInc;

    private int intCodEmp, intCodLoc;                           //Código de la empresa y local. 
    private String strAux = "";
    private String strDesCorTipDoc = "";
    private String strDesLarTipDoc = "";
    private String strCodTipDoc = "";
    private String strCodDep = "";
    private String strDesDep = "";
    private String strCodMot = "";
    private String strDesMot = "";

    private String strMerIngEgr = "", strTipIngEgr = "";

    private Vector vecCab = new Vector();
    private ZafVenCon objVenConTipdoc;
    private ZafVenCon objVenConMotHorSupExt;
    private ZafVenCon objVenConMarInc;

    private ZafUtil objUti;
    private mitoolbar objTooBar;

    private boolean blnHayCam = false;

    private final int INT_TBL_LINEA = 0;
    private final int INT_TBL_CHKTRA = 1;
    private final int INT_TBL_CODEMPPAG = 2;
    private final int INT_TBL_CODTRA = 3;
    private final int INT_TBL_NOMTRA = 4;
    private final int INT_TBL_COREG = 5;
    private final int INT_TBL_HORENT = 6;
    private final int INT_TBL_HORSAL = 7;

    private javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    private javax.swing.JTextField txtCodTipDocSol = new javax.swing.JTextField();
    private javax.swing.JTextArea txtsql = new javax.swing.JTextArea();

    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.

    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".

    private int intSig = 1;                                       //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;//, rstCab;

    private ZafVenCon vcoDep;                                  //Ventana de consulta "Tipo de documento".

    private Vector vecAux;
    private Vector vecDat = null;

    private String strCodEmpAut = "";
    private String strCodLocAut = "";
    private String strCodTipDocAut = "";
    private String strCodDocAut = "";
    private boolean blnEstCarSolHSE = false;
    
    private ZafNotCorEle objNotCorEle;

    private String strVersion = " v1.00"; //

    public ZafRecHum95(Librerias.ZafParSis.ZafParSis obj) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();


            objUti = new ZafUtil();

            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            txtFecDoc.setEnabled(false);
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            txtFecDoc.setEnabled(false);
            txtFecDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecDocActionPerformed(evt);
                }
            });
            
            

            txtFecSol = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecSol.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecSol.setText("");
            PanTabGen.add(txtFecSol);
            txtFecSol.setBounds(140, 70, 110, 20);
            //txtFecSol.setEnabled(false);
            txtFecSol.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecSol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecSolActionPerformed(evt);
                }
            });
            
            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
            zafVenMotHorSupExt = new ZafVenMotHorSupExt(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Motivos de horas suplementarias/extraordinarias");
            zafVenMotMarInc = new ZafVenMotMarInc(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Motivos de marcaciones incompletas");
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }
    }

    /**
     * Creates new form ZafRecHum27
     */
    public ZafRecHum95(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            
            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

            strCodEmpAut = strCodEmp;
            strCodLocAut = strCodLoc;
            strCodTipDocAut = strCodTipDoc;
            strCodDocAut = strCodDoc;
            blnEstCarSolHSE = true;

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objZafParSis.getCodigoMenu() == 2216) {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            txtFecDoc.setEnabled(false);
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            txtFecSol = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecSol.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecSol.setText(strFecSis);
            PanTabGen.add(txtFecSol);
            txtFecSol.setBounds(140, 70, 110, 20);
            objTooBar.setVisible(false);
//        } catch (CloneNotSupportedException e) {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
          } catch (Exception e) {
            String strErr = e.toString();

            if (e.getCause() != null) {
                strErr += "\n\nCAUSE:\n" + e.getCause().toString();
            }

            javax.swing.JOptionPane.showMessageDialog(
                this,
                strErr,
                "ERROR REAL",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Esta función carga el documento especificado.
     *
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento) {
        intCodEmp = codigoEmpresa.intValue();
        intCodLoc = codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtCodDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_merIngEgrFisBod");
            arlCam.add("a1.ne_numLin");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            arlAli.add("Núm.Lín.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("60");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objZafParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + intCodEmp;
                strSQL += " AND a1.co_loc=" + intCodLoc;
                strSQL += " AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objZafParSis.getCodigoUsuario();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            //Ocultar columnas.
            int intColOcu[] = new int[3];
            intColOcu[0] = 5;
            intColOcu[1] = 6;
            intColOcu[2] = 7;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;
                    strSQL += " AND a1.co_loc=" + intCodLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocPrg";
                    strSQL += " WHERE co_emp=" + intCodEmp;
                    strSQL += " AND co_loc=" + intCodLoc;
                    strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    System.out.println(strSQL);
                    rst = stm.executeQuery(strSQL);
                    
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmp;
                    strSQL += " AND a1.co_loc=" + intCodLoc;
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocUsr";
                    strSQL += " WHERE co_emp=" + intCodEmp;
                    strSQL += " AND co_loc=" + intCodLoc;
                    strSQL += " AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL += " AND co_usr=" + objZafParSis.getCodigoUsuario();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                    System.out.println(strSQL);
                    rst = stm.executeQuery(strSQL);
                }
                if (rst.next()) {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc") + 1));//THERE
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                    //strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")));
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
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

    /**
     * Esta función muestra el departamento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDepPre() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
//                    strSQL+=" FROM tbm_cabTipDoc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//                    strSQL+=" AND a1.co_loc=" + intCodLoc;
//                    strSQL+=" AND a1.co_tipDoc=";
//                    strSQL+=" (";
//                    strSQL+=" SELECT co_tipDoc";
//                    strSQL+=" FROM tbr_tipDocPrg";
//                    strSQL+=" WHERE co_emp=" + intCodEmp;
//                    strSQL+=" AND co_loc=" + intCodLoc;
//                    strSQL+=" AND co_mnu=" + objZafParSis.getCodigoMenu();
//                    strSQL+=" AND st_reg='S'";
//                    strSQL+=" )";

                } else {
                    //Armar la sentencia SQL.
                    strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="
                            + objZafParSis.getCodigoUsuario() + "and co_mnu=" + objZafParSis.getCodigoMenu() + " and st_reg in ('A', 'P'))";
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()) {

                        txtCodDep.setText(rst.getString("co_dep"));
                        txtDesDep.setText(rst.getString("tx_deslar"));
                        insertarDetalle();
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst = null;
                    stm = null;
                    con = null;
                }
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

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon() {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
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
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objZafParSis.getCodigoMenu() + " and st_reg in ('A', 'P'))";
            } 

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=4;
            //intColOcu[1]=6;
            //intColOcu[2]=7;
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

    private boolean configurarVenConMotHorSupExt() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_mot");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción larga");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_mot, a.tx_deslar  from tbm_motSolMar as a"
                    + " where a.st_reg not in ('I','E') order by a.tx_deslar";
            //objVenConMotHorSupExt = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            objVenConMarInc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
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
    private boolean configurarTabla() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CHKTRA, "");
            vecCab.add(INT_TBL_CODEMPPAG, "Cód.Emp.");
            vecCab.add(INT_TBL_CODTRA, "Código");
            vecCab.add(INT_TBL_NOMTRA, "Empleado");
            vecCab.add(INT_TBL_COREG, "Cód.Reg.");
            vecCab.add(INT_TBL_HORENT, "Entrada");
            vecCab.add(INT_TBL_HORSAL, "Salida");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODEMPPAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_COREG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_HORENT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_HORSAL).setPreferredWidth(50);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CHKTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODEMPPAG).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_HORENT).setResizable(false);
            tcmAux.getColumn(INT_TBL_HORSAL).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPPAG, tblDat);
            System.out.println("menu: " + objZafParSis.getCodigoMenu());
            if (objZafParSis.getCodigoMenu() == 2862 || objZafParSis.getCodigoMenu() == 3485) {

            } else {
                objTblMod.addSystemHiddenColumn(INT_TBL_HORENT, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_HORSAL, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHKTRA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);

            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);

            //Configurar JTable: Detectar cambios de valores en las celdas.
            /*objTblModLis=new ZafTblModLis();
             objTblMod.addTableModelListener(objTblModLis);*/
            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKTRA).setCellRenderer(zafTblCelRenChkLab);
            
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKTRA).setCellEditor(zafTblCelEdiChkLab);

            
            //cambios realizados a la tabla
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);           
                       
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
                
        return blnRes;
    }

    public void Configura_ventana_consulta() {
        configurarVenConTipDoc();
        configurarVenConDep();
        configurarVenConMotHorSupExt();
        configurarTabla();

        if (blnEstCarSolHSE) {
            vecDat = null;
            cargarDatos();
        }
    }

    private boolean cargarDatos() {
        boolean blnRes = true;
        try {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab != null) {

                if (cargarCabReg()) {
                    if (cargarDetReg()) {
                        blnRes = true;
                    }
                }
                //   objAsiDia.setDiarioModificado(false);
                blnHayCam = false;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() {

        int intPosRel;

        boolean blnRes = true;

        String strSQL = "";

        String strSql = "", strStReg = "";

        Vector vecDataCon = null;

        try {

            con = DriverManager.getConnection(
                    objZafParSis.getStringConexion(),
                    objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos()
            );

            if (con != null) {

                stm = con.createStatement();

                strSQL = "";

                strSQL += "SELECT * ";
                strSQL += "FROM ( ";
                strSQL += "SELECT c.*, ";
                strSQL += "f.tx_deslar AS deslardep, ";
                strSQL += "g.tx_deslar AS deslarmot, ";
                strSQL += "c.st_reg, ";
                strSQL += "h.tx_descor AS txdescortipdoc, ";
                strSQL += "h.tx_deslar AS txdeslartipdoc ";
                strSQL += "FROM ( ";

                strSQL += "SELECT ";
                strSQL += "a.co_emp, ";
                strSQL += "a.co_loc, ";
                strSQL += "a.co_tipdoc, ";
                strSQL += "a.co_doc, ";
                strSQL += "a.fe_doc, ";
                strSQL += "a.ne_numdoc, ";
                strSQL += "a.co_dep, ";
                strSQL += "a.co_mot, ";
                strSQL += "a.fe_sol, ";
                strSQL += "a.ho_ent, ";
                strSQL += "a.ho_sal, ";
                strSQL += "a.tx_obs1, ";
                strSQL += "a.tx_obs2, ";
                strSQL += "a.st_reg ";
                strSQL += "FROM tbm_cabsolmar a ";

                strSQL += ") c ";

                strSQL += "INNER JOIN tbm_dep f ";
                strSQL += "ON (f.co_dep=c.co_dep) ";

                strSQL += "INNER JOIN tbm_motsolmar g ";
                strSQL += "ON (g.co_mot=c.co_mot) ";

                strSQL += "INNER JOIN tbm_cabtipdoc h ";
                strSQL += "ON (h.co_emp=c.co_emp ";
                strSQL += "AND h.co_loc=c.co_loc ";
                strSQL += "AND h.co_tipdoc=c.co_tipdoc) ";

                strSQL += ") e ";

                strSQL += "WHERE co_emp = " + strCodEmpAut;
                strSQL += " AND co_loc = " + strCodLocAut;
                strSQL += " AND co_tipdoc = " + strCodTipDocAut;
                strSQL += " AND co_doc = " + strCodDocAut;

                System.out.println("cargarCabReg: " + strSQL);

                rst = stm.executeQuery(strSQL);

                if (rst.next()) {

                    strStReg = rst.getString("st_reg");

                    txtDesCorTipDoc.setText(
                            rst.getString("txdescortipdoc")
                    );

                    txtDesLarTipDoc.setText(
                            rst.getString("txdeslartipdoc")
                    );

                    txtCodDep.setText(
                            rst.getString("co_dep")
                    );

                    txtDesDep.setText(
                            rst.getString("deslardep")
                    );

                    txtCodMot.setText(
                            rst.getString("co_mot")
                    );

                    txtDesMot.setText(
                            rst.getString("deslarmot")
                    );

                    txtNumDoc.setText(
                            rst.getString("ne_numdoc")
                    );

                    txtCodDoc.setText(
                            rst.getString("co_doc")
                    );

                    txtObs1.setText(
                            rst.getString("tx_obs1")
                    );

                    txtObs2.setText(
                            rst.getString("tx_obs2")
                    );

                    /* ENTRADA */
                    if (rst.getString("ho_ent") != null) {

                    chkEnt.setSelected(true);

                    cboHHEnt.setEnabled(false);
                    cboMMEnt.setEnabled(false);

                    cboHHEnt.setSelectedItem(
                            rst.getString("ho_ent").substring(0, 2)
                    );

                    cboMMEnt.setSelectedItem(
                            rst.getString("ho_ent").substring(3, 5)
                    );

                } else {

                    chkEnt.setSelected(false);

                    cboHHEnt.setEnabled(false);
                    cboMMEnt.setEnabled(false);

                    cboHHEnt.setSelectedItem("00");
                    cboMMEnt.setSelectedItem("00");
                }

                    /* SALIDA */
                    if (rst.getString("ho_sal") != null) {

                    chkSal.setSelected(true);

                    cboHHSal.setEnabled(false);
                    cboMMSal.setEnabled(false);

                    cboHHSal.setSelectedItem(
                            rst.getString("ho_sal").substring(0, 2)
                    );

                    cboMMSal.setSelectedItem(
                            rst.getString("ho_sal").substring(3, 5)
                    );

                } else {

                    chkSal.setSelected(false);

                    cboHHSal.setEnabled(false);
                    cboMMSal.setEnabled(false);

                    cboHHSal.setSelectedItem("00");
                    cboMMSal.setSelectedItem("00");
                }

                    /* FECHA DOCUMENTO */
                    java.util.Date dateObj =
                            rst.getDate("fe_doc");

                    if (dateObj == null) {

                        txtFecDoc.setText("");

                    } else {

                        java.util.Calendar calObj =
                                java.util.Calendar.getInstance();

                        calObj.setTime(dateObj);

                        txtFecDoc.setText(
                                calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR)
                        );
                    }

                    /* FECHA SOLICITUD */
                    dateObj = rst.getDate("fe_sol");

                    if (dateObj == null) {

                        txtFecSol.setText("");

                    } else {

                        java.util.Calendar calObj =
                                java.util.Calendar.getInstance();

                        calObj.setTime(dateObj);

                        txtFecSol.setText(
                                calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR)
                        );
                    }
                }

                rst.close();

                stm.close();

                con.close();

                rst = null;
                stm = null;
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

    private boolean cargarDetReg() {

        String strSQL;

        boolean blnRes = true;

        try {

            con = DriverManager.getConnection(
                    objZafParSis.getStringConexion(),
                    objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos()
            );

            if (con != null) {

                objTblMod.removeAllRows();

                vecDat = new Vector();

                stm = con.createStatement();

                strSQL = "";

                strSQL += "SELECT * ";
                strSQL += "FROM ( ";

                strSQL += "SELECT ";
                strSQL += "c.*, ";
                strSQL += "(d.tx_ape || ' ' || d.tx_nom) AS nomCom, ";
                strSQL += "f.tx_deslar AS deslardep, ";
                strSQL += "g.tx_deslar AS deslarmot, ";
                strSQL += "c.st_reg, ";
                strSQL += "h.tx_descor AS txdescortipdoc, ";
                strSQL += "h.tx_deslar AS txdeslartipdoc ";

                strSQL += "FROM ( ";

                strSQL += "SELECT ";
                strSQL += "a.co_emp, ";
                strSQL += "a.co_loc, ";
                strSQL += "a.co_tipdoc, ";
                strSQL += "a.co_doc, ";
                strSQL += "a.fe_doc, ";
                strSQL += "a.ne_numdoc, ";
                strSQL += "a.co_dep, ";
                strSQL += "a.co_mot, ";
                strSQL += "a.fe_sol, ";
                strSQL += "a.ho_ent, ";
                strSQL += "a.ho_sal, ";
                strSQL += "a.tx_obs1, ";
                strSQL += "a.tx_obs2, ";
                strSQL += "a.st_reg, ";
                strSQL += "b.co_reg, ";
                strSQL += "b.co_tra ";

                strSQL += "FROM tbm_cabsolmar a ";

                strSQL += "INNER JOIN tbm_detsolmar b ";
                strSQL += "ON (a.co_emp=b.co_emp ";
                strSQL += "AND a.co_loc=b.co_loc ";
                strSQL += "AND a.co_tipdoc=b.co_tipdoc ";
                strSQL += "AND a.co_doc=b.co_doc) ";

                strSQL += ") c ";

                strSQL += "INNER JOIN tbm_tra d ";
                strSQL += "ON (c.co_tra=d.co_tra) ";

                strSQL += "INNER JOIN tbm_dep f ";
                strSQL += "ON (f.co_dep=c.co_dep) ";

                strSQL += "INNER JOIN tbm_motsolmar g ";
                strSQL += "ON (g.co_mot=c.co_mot) ";

                strSQL += "INNER JOIN tbm_cabtipdoc h ";
                strSQL += "ON (h.co_emp=c.co_emp ";
                strSQL += "AND h.co_loc=c.co_loc ";
                strSQL += "AND h.co_tipdoc=c.co_tipdoc) ";

                strSQL += ") e ";

                strSQL += "WHERE e.co_emp="
                        + strCodEmpAut;

                strSQL += " AND e.co_loc="
                        + strCodLocAut;

                strSQL += " AND e.co_tipdoc="
                        + strCodTipDocAut;

                strSQL += " AND e.co_doc="
                        + strCodDocAut;

                strSQL += " ORDER BY nomCom";

                System.out.println(
                        "detalle sol marcaciones: "
                        + strSQL
                );

                rst = stm.executeQuery(strSQL);

                while (rst.next()) {

                    Vector vecReg = new Vector();

                    vecReg.add(INT_TBL_LINEA, "");

                    vecReg.add(INT_TBL_CHKTRA,
                            Boolean.TRUE);

                    vecReg.add(INT_TBL_CODEMPPAG,
                            rst.getInt("co_emp"));

                    vecReg.add(INT_TBL_CODTRA,
                            rst.getInt("co_tra"));

                    vecReg.add(INT_TBL_NOMTRA,
                            rst.getString("nomCom"));

                    vecReg.add(INT_TBL_COREG,
                            rst.getString("co_reg"));

                    /* ENTRADA */
                    if (rst.getString("ho_ent") == null) {

                        vecReg.add(INT_TBL_HORENT, "");

                    } else {

                        vecReg.add(
                                INT_TBL_HORENT,
                                rst.getString("ho_ent")
                                        .substring(0, 5)
                        );
                    }

                    /* SALIDA */
                    if (rst.getString("ho_sal") == null) {

                        vecReg.add(INT_TBL_HORSAL, "");

                    } else {

                        vecReg.add(
                                INT_TBL_HORSAL,
                                rst.getString("ho_sal")
                                        .substring(0, 5)
                        );
                    }

                    vecDat.add(vecReg);
                }

                rst.close();

                stm.close();

                con.close();

                rst = null;
                stm = null;
                con = null;

                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);

                vecDat.clear();

                vecDat = null;
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        butBusTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtDesDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodMot = new javax.swing.JTextField();
        txtDesMot = new javax.swing.JTextField();
        butMot = new javax.swing.JButton();
        lblNumDoc1 = new javax.swing.JLabel();
        lblFecDto1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtHorSolHas = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        cboMMSal = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboHHSal = new javax.swing.JComboBox();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblNumDoc2 = new javax.swing.JLabel();
        cboHHEnt = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cboMMEnt = new javax.swing.JComboBox();
        txtHorSolDes = new javax.swing.JTextField();
        lblNumDoc4 = new javax.swing.JLabel();
        chkEnt = new javax.swing.JCheckBox();
        chkSal = new javax.swing.JCheckBox();
        panDetTraDep = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 110));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        butBusTipDoc.setText("jButton1"); // NOI18N
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusTipDoc);
        butBusTipDoc.setBounds(420, 10, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(450, 10, 130, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 70, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(450, 30, 140, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        PanTabGen.add(jLabel5);
        jLabel5.setBounds(10, 30, 110, 20);

        txtCodDep.setBackground(objZafParSis.getColorCamposObligatorios());
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
        PanTabGen.add(txtCodDep);
        txtCodDep.setBounds(140, 30, 70, 20);

        txtDesDep.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesDepActionPerformed(evt);
            }
        });
        txtDesDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesDepFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesDep);
        txtDesDep.setBounds(210, 30, 210, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        butDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butDepFocusGained(evt);
            }
        });
        PanTabGen.add(butDep);
        butDep.setBounds(420, 30, 20, 20);

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel6.setText(":"); // NOI18N
        PanTabGen.add(jLabel6);
        jLabel6.setBounds(537, 70, 10, 20);

        txtCodMot.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodMot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMotFocusLost(evt);
            }
        });
        txtCodMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMotActionPerformed(evt);
            }
        });
        PanTabGen.add(txtCodMot);
        txtCodMot.setBounds(140, 50, 70, 20);

        txtDesMot.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesMotActionPerformed(evt);
            }
        });
        txtDesMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesMotFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesMot);
        txtDesMot.setBounds(210, 50, 210, 20);

        butMot.setText(".."); // NOI18N
        butMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotActionPerformed(evt);
            }
        });
        PanTabGen.add(butMot);
        butMot.setBounds(420, 50, 20, 20);

        lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc1.setText("Salida"); // NOI18N
        PanTabGen.add(lblNumDoc1);
        lblNumDoc1.setBounds(450, 70, 40, 20);

        lblFecDto1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDto1.setText("Fecha solicitada:"); // NOI18N
        PanTabGen.add(lblFecDto1);
        lblFecDto1.setBounds(10, 70, 120, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(590, 30, 80, 20);

        txtHorSolHas.setBackground(objZafParSis.getColorCamposObligatorios());
        txtHorSolHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorSolHasActionPerformed(evt);
            }
        });
        txtHorSolHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorSolHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorSolHasFocusLost(evt);
            }
        });
        PanTabGen.add(txtHorSolHas);
        txtHorSolHas.setBounds(580, 70, 80, 20);
        txtHorSolHas.setVisible(false);

        txtCodDoc.setEditable(false);
        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(590, 50, 80, 20);

        cboMMSal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        PanTabGen.add(cboMMSal);
        cboMMSal.setBounds(545, 70, 50, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Motivo:"); // NOI18N
        PanTabGen.add(jLabel7);
        jLabel7.setBounds(10, 50, 110, 20);

        cboHHSal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        PanTabGen.add(cboHHSal);
        cboHHSal.setBounds(485, 70, 50, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(210, 10, 210, 20);

        lblNumDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc2.setText("Código del Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc2);
        lblNumDoc2.setBounds(450, 50, 140, 20);

        cboHHEnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cboHHEnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHHEntActionPerformed(evt);
            }
        });
        PanTabGen.add(cboHHEnt);
        cboHHEnt.setBounds(315, 70, 50, 20);
        cboHHEnt.setSelectedIndex(18);

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel8.setText(":"); // NOI18N
        PanTabGen.add(jLabel8);
        jLabel8.setBounds(369, 70, 10, 20);

        cboMMEnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        PanTabGen.add(cboMMEnt);
        cboMMEnt.setBounds(375, 70, 50, 20);

        txtHorSolDes.setBackground(objZafParSis.getColorCamposObligatorios());
        txtHorSolDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorSolDesActionPerformed(evt);
            }
        });
        txtHorSolDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorSolDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorSolDesFocusLost(evt);
            }
        });
        PanTabGen.add(txtHorSolDes);
        txtHorSolDes.setBounds(420, 70, 80, 20);
        txtHorSolDes.setVisible(false);

        lblNumDoc4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc4.setText("Entrada"); // NOI18N
        PanTabGen.add(lblNumDoc4);
        lblNumDoc4.setBounds(275, 70, 40, 20);

        chkEnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEntActionPerformed(evt);
            }
        });
        PanTabGen.add(chkEnt);
        chkEnt.setBounds(255, 70, 20, 21);

        chkSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSalActionPerformed(evt);
            }
        });
        PanTabGen.add(chkSal);
        chkSal.setBounds(430, 70, 20, 21);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

        scrTbl.setToolTipText("");

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
        tblDat.setColumnSelectionAllowed(true);
        scrTbl.setViewportView(tblDat);
        tblDat.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        panDetTraDep.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panDetTraDep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setBackground(objZafParSis.getColorCamposObligatorios());
        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc = txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    if (txtDesCorTipDoc.isEditable()) {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecDoc.transferFocus();
    }

    private void txtFecSolActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecSol.transferFocus();
       // validarFecSol();
    }
    
    
    private boolean validaHoraDesde(){
        boolean booRet=true;
        try{
            if(((String)cboHHEnt.getSelectedItem()).equals("17") ){
                if(Integer.parseInt((String)cboMMEnt.getSelectedItem())<30){
                    booRet=false;
                }        
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booRet;
        
    }
    
    private Calendar obtenerSgteDiaLaboraldeFecSol(Calendar calFecAct){
        Connection con=null;
        Statement stmCalCiu=null;
        ResultSet rsCalCiu=null;
        Calendar calFecLab=null;
        String strSQLSigDiaLab="";
        try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
            strSQLSigDiaLab="SELECT a1.fe_dia,* \n" +
                            " FROM tbm_calCiu as a1 \n" +
                            " INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu) \n"+
                            " WHERE a2.co_emp="+objZafParSis.getCodigoEmpresa()+ " and co_loc="+objZafParSis.getCodigoLocal()+
                            " AND a1.tx_tipDia='L' "+
                            " and a1.fe_dia > '"+sf.format(calFecAct.getTime())+"'"+                         
                            " ORDER BY a1.fe_dia ASC "+
                            " LIMIT 1";
            stmCalCiu=con.createStatement();
            rsCalCiu=stmCalCiu.executeQuery(strSQLSigDiaLab);
            if(rsCalCiu!=null && rsCalCiu.next()){
                calFecLab=Calendar.getInstance();
                calFecLab.setTime(rsCalCiu.getDate("fe_dia"));
            }
            rsCalCiu.close();
            stmCalCiu.close();            
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  calFecLab;
    }
    
//    private boolean validarFecSol(){
//        boolean booRet=false;
//        Calendar calFecAct=Calendar.getInstance();
//        Calendar calFecSol=Calendar.getInstance();
//        Calendar calFecIngSol=Calendar.getInstance();
//        try{
//            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy"); 
//            System.out.println("Solici: "+ txtFecSol.getText());
//            calFecSol.setTime(sf.parse(txtFecSol.getText()));
//            Calendar calFecSgteDiaLab=obtenerSgteDiaLaboraldeFecSol(calFecSol);
//            calFecIngSol.setTime(sf.parse(txtFecDoc.getText()));
//            if(calFecIngSol.compareTo(calFecSgteDiaLab)>0){
//                booRet=false;
//            }else if(calFecIngSol.compareTo(calFecSgteDiaLab)<=0){
//                booRet=true;
//            }
//        }catch(ParseException ex){
//            ex.printStackTrace();
//        }         
//        return booRet;
//    }

private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butBusTipDocActionPerformed

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
    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            
                            System.out.println("TipoDoc=" + vcoTipDoc.getValueAt(2));
                            System.out.println("MaxRowsAllowed=" + vcoTipDoc.getValueAt(7));
                            
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            
                            System.out.println("TipoDoc=" + vcoTipDoc.getValueAt(2));
                            System.out.println("MaxRowsAllowed=" + vcoTipDoc.getValueAt(7));
                            
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                                                                
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n') {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            } else {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {
                            
                                                       
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7)))) {                                
                                                               
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado() == 'n') {
                                    strAux = vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                                }
                                intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            } else {
                                txtDesLarTipDoc.setText(strDesLarTipDoc);
                            }
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
    cboHHEnt.setEnabled(false);
    cboMMEnt.setEnabled(false);
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
    String strTit;
    strTit = "Mensaje del sistema Zafiro";
    if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {
        if (rstCab != null) {
            rstCab = null;
        }
        if (STM_GLO != null) {
            STM_GLO = null;
        }

        strCodEmpAut = "";
        strCodLocAut = "";
        strCodTipDocAut = "";
        strCodDocAut = "";
        vecDat = null;
        System.gc();
        dispose();
    }
}//GEN-LAST:event_formInternalFrameClosing

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
        if (!txtCodDep.equals("")) {
            insertarDetalle();
        } else {
            objTblMod.removeAllRows();
        }

    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtDesDep.setText("");
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
            txtDesDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtDesDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtDesDep.setText(strDesDep);
            }
        }
    }

    public void BuscarMotMarInc(String campo, String strBusqueda, int tipo) {
        objVenConMarInc.setTitle("Listado de Motivos de Horas Suplementarias/Extraordinarias");
        if (objVenConMarInc.buscar(campo, strBusqueda)) {
            txtCodMot.setText(objVenConMarInc.getValueAt(1));
            txtDesMot.setText(objVenConMarInc.getValueAt(2));
        } else {
            objVenConMarInc.setCampoBusqueda(tipo);
            objVenConMarInc.cargarDatos();
            objVenConMarInc.show();
            if (objVenConMarInc.getSelectedButton() == objVenConMarInc.INT_BUT_ACE) {
                txtCodMot.setText(objVenConMarInc.getValueAt(1));
                txtDesMot.setText(objVenConMarInc.getValueAt(2));
            } else {
                txtCodMot.setText(strCodMot);
                txtDesMot.setText(strDesMot);
            }
        }
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
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesDep.setText(strDesDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtDesDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesDep.setText(strDesDep);
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

    private void txtDesDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesDepActionPerformed
        txtDesDep.transferFocus();
    }//GEN-LAST:event_txtDesDepActionPerformed

    private void txtDesDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDepFocusGained
        strDesDep = txtDesDep.getText();
        txtDesDep.selectAll();
    }//GEN-LAST:event_txtDesDepFocusGained

    private boolean insertarDetalle() {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.PreparedStatement preSta = null;
        java.sql.ResultSet resSet = null;
        Vector vecDataCon = null;

        objTblMod.removeAllRows();

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {

                if (txtCodDep.getText() != null && !txtCodDep.getText().equals("")) {

                    String strSql = "select b.co_emp,a.co_tra, (a.tx_ape || ' ' || a.tx_nom) AS nomCom from tbm_tra a ";
                    strSql += " inner join tbm_traemp  b on (a.co_tra=b.co_tra and b.st_reg like 'A' and b.co_emp=1) ";
                    strSql += " where b.co_dep = ? and a.st_reg = 'A'";
                    strSql += " order by (a.tx_ape || ' ' || a.tx_nom)";

                    preSta = conn.prepareStatement(strSql);

                    if (Integer.parseInt(txtCodDep.getText()) > 0) {
                        preSta.setInt(1, Integer.parseInt(txtCodDep.getText().toString()));
                    } else {
                        preSta.setNull(1, Types.INTEGER);
                    }

                    resSet = preSta.executeQuery();

                    int i = 1;
                    if (resSet.next()) {
                        vecDataCon = new Vector();
                        do {
                            Vector vecReg = new Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CHKTRA, false);
                            vecReg.add(INT_TBL_CODEMPPAG, resSet.getInt("co_emp"));
                            vecReg.add(INT_TBL_CODTRA, resSet.getInt("co_tra"));
                            vecReg.add(INT_TBL_NOMTRA, resSet.getString("nomCom"));
                            vecReg.add(INT_TBL_COREG, i++);
                            vecReg.add(INT_TBL_HORENT, "");
                            vecReg.add(INT_TBL_HORSAL, "");
                            vecDataCon.add(vecReg);
                        } while (resSet.next());
                    } else {
                        return false;
                    }
                    conn.close();
                    conn=null;
                    if (vecDataCon != null) {
                        if (vecDataCon.size() > 0) {
                            objTblMod.setData(vecDataCon);                                                      
                            tblDat.repaint();
                                                        
                            blnRes = true;
                        }
                    } else {
                        vecDataCon = new Vector();
                        objTblMod.setData(vecDataCon);                        
                        tblDat.repaint();
                        
                        blnRes = true;
                    }
                }
            }
        } catch (Exception ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
            ex.printStackTrace();
        } finally {
            try {
                resSet.close();
            } catch (Throwable ignore) {
            }
            try {
                preSta.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    private void txtDesDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDepFocusLost
        if (txtDesDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesDep.getText().equalsIgnoreCase(strDesDep)) {
                if (txtDesDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtDesDep.setText("");
                } else {
                    mostrarVenConDep(2);
                    if (!txtCodDep.equals("")) {
                        insertarDetalle();
                    } else {
                        objTblMod.removeAllRows();
                    }
                }
            } else {
                txtDesDep.setText(strDesDep);
            }
        }

    }//GEN-LAST:event_txtDesDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && (txtCodMot.getText().compareTo("") != 0)) {

                stmLoc = conn.createStatement();
                strSql = "select co_mot, tx_deslar from tbm_motSolMar where st_reg like 'A' and co_mot =  " + txtCodMot.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {

                    txtDesMot.setText(rstLoc.getString("tx_deslar"));
                    txtDesMot.setHorizontalAlignment(2);
                    //txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtDesMot.setText("");
                    txtCodMot.setText("");
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
        }
    }//GEN-LAST:event_txtCodMotActionPerformed

    private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
        //agregar a real
        strCodMot = txtCodDep.getText();
        txtCodMot.selectAll();      
        
    }//GEN-LAST:event_txtCodMotFocusGained

    private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
        // TODO add your handling code here:
        //agregar a real
        if (!txtCodMot.getText().equalsIgnoreCase(strCodMot)) {
            if (txtCodMot.getText().equals("")) {
                txtCodMot.setText("");
                txtCodMot.setText("");
            } else {
                BuscarMotMarInc("a.co_mot", txtCodMot.getText(), 0);
            }
        } else {
            txtCodMot.setText(strCodMot);
        }
    }//GEN-LAST:event_txtCodMotFocusLost

    private void txtDesMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesMotActionPerformed
        txtDesMot.transferFocus();
    }//GEN-LAST:event_txtDesMotActionPerformed

    private void txtDesMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusGained
        strDesMot = txtDesMot.getText();
        txtDesMot.selectAll();
    }//GEN-LAST:event_txtDesMotFocusGained

    private void txtDesMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusLost
        if (!txtDesMot.getText().equalsIgnoreCase(strDesMot)) {
            if (txtDesMot.getText().equals("")) {
                txtCodMot.setText("");
                txtDesMot.setText("");
            } else {
                BuscarMotMarInc("a.tx_deslar", txtDesMot.getText(), 1);
            }
        } else {
            txtDesMot.setText(strDesMot);
        }
    }//GEN-LAST:event_txtDesMotFocusLost

    private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
        try {
            zafVenMotMarInc.limpiar();
            zafVenMotMarInc.setCampoBusqueda(0);
            zafVenMotMarInc.setVisible(true);

            if (zafVenMotMarInc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodMot.setText(zafVenMotMarInc.getValueAt(1));
                txtDesMot.setText(zafVenMotMarInc.getValueAt(3));
            }
        } catch (Exception ex) {
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butMotActionPerformed

    private void butDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butDepFocusGained
        if (!txtDesDep.equals("")) {
            insertarDetalle();
        } else {
            objTblMod.removeAllRows();
        }
    }//GEN-LAST:event_butDepFocusGained

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        txtNumDoc.selectAll();
    }//GEN-LAST:event_txtNumDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void cboHHEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHHEntActionPerformed
        if (Integer.valueOf(cboHHEnt.getSelectedItem().toString()) == 17) {
            //cboHHDes.setSelectedItem("18");
        }
    }//GEN-LAST:event_cboHHEntActionPerformed

    private void txtHorSolDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolDesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesFocusLost

    private void txtHorSolDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolDesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesFocusGained

    private void txtHorSolDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorSolDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolDesActionPerformed

    private void txtHorSolHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolHasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasFocusLost

    private void txtHorSolHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSolHasFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasFocusGained

    private void txtHorSolHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorSolHasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorSolHasActionPerformed

    private void chkEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEntActionPerformed
        // TODO add your handling code here:
        boolean blnSel = chkEnt.isSelected();

        cboHHEnt.setEnabled(blnSel);
        cboMMEnt.setEnabled(blnSel);
        
        if (!blnSel) {
            cboHHEnt.setSelectedIndex(0);
            cboMMEnt.setSelectedIndex(0);
        }
        
    }//GEN-LAST:event_chkEntActionPerformed

    private void chkSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSalActionPerformed
        // TODO add your handling code here:
        
        boolean blnSel = chkSal.isSelected();

        cboHHSal.setEnabled(blnSel);
        cboMMSal.setEnabled(blnSel);
        
        if (!blnSel) {
            cboHHSal.setSelectedIndex(0);
            cboMMSal.setSelectedIndex(0);
        }
    }//GEN-LAST:event_chkSalActionPerformed

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

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        if (objVenConTipdoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strMerIngEgr = objVenConTipdoc.getValueAt(4);
            strTipIngEgr = objVenConTipdoc.getValueAt(5);
        } else {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton() == objVenConTipdoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc = objVenConTipdoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butMot;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboHHEnt;
    private javax.swing.JComboBox cboHHSal;
    private javax.swing.JComboBox cboMMEnt;
    private javax.swing.JComboBox cboMMSal;
    private javax.swing.JCheckBox chkEnt;
    private javax.swing.JCheckBox chkSal;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDto1;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblNumDoc4;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodMot;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesDep;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesMot;
    private javax.swing.JTextField txtHorSolDes;
    private javax.swing.JTextField txtHorSolHas;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class mitoolbar extends ZafToolBar {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
        }

        public boolean anular() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }
                    if (strAux.equals("Anulado")) {
                        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                        blnRes = true;
                    }
                    if (!blnRes) {

                        if (anularReg(conn)) {

                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Anulado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean anularReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";

            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "UPDATE tbm_cabSolMar SET st_reg='I', fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario()
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        public void clickAnterior() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.previous();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        //if(blnHayCam || objTblMod.isDataModelChanged()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.next();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.next();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.last();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.first();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular() {

        }

        public void clickConsultar() {
            clnTextos();
            noEditable(false);
            bloquea(txtNumDoc, false);
            cargarTipoDoc(2);

        }

        public void clickEliminar() {
            //          noEditable(false);
        }

        public void clickInsertar() {
            try {
                clnTextos();
                //noEditable(false);
                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                cboHHEnt.setEnabled(false);
                cboMMEnt.setEnabled(false);
                
                cboHHSal.setEnabled(false);
                cboMMSal.setEnabled(false);
                
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj = datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                txtFecSol.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                mostrarTipDocPre();
                mostrarDepPre();

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }

                //txtFecDoc.setEnabled(true);
                txtFecDoc.setEnabled(false);
                txtFecSol.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void setEstadoBotonMakeFac() {
            switch (getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }

        public boolean eliminar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }

                    if (!blnRes) {
                        if (eliminarReg(conn)) {
                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Eliminado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean eliminarReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc = null;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabSolHorSupExt SET st_reg='E' , fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario()
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                }

                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos.
         * <BR>false: En el caso contrario.
         */
        private boolean validaCampos() throws SQLException {
            //Validar el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
                return false;
            }
            //Validar el "Departamento".
            if (txtCodDep.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Departamento</FONT> es obligatorio.<BR>Escriba o seleccione un departamento y vuelva a intentarlo.</HTML>");
                txtCodDep.requestFocus();
                return false;
            }
            //Validar el "Motivo de marcacion".
            if (txtCodMot.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Motivo de Marcacion</FONT> es obligatorio.<BR>Escriba o seleccione un motivo de marcación y vuelva a intentarlo.</HTML>");
                txtCodMot.requestFocus();
                return false;
            }
            //Validar la "Fecha del Documento".
            if (!txtFecDoc.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de documento y vuelva a intentarlo.</HTML>");
                txtFecDoc.requestFocus();
                return false;
            }
            //Validar el "Número de Documento".
            String str = txtNumDoc.getText();
            if (txtNumDoc.getText().equals("")) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.requestFocus();
                return false;
            } else {
                if (!objUti.isNumero(txtNumDoc.getText())) {
                    tabGen.setSelectedIndex(1);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> contiene formato érroneo.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                    txtNumDoc.requestFocus();
                    return false;
                }
            }
            //Validar la "Fecha Solicitada".
            if (!txtFecSol.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha Solicitada</FONT> es obligatorio.<BR>Escriba o seleccione una fecha solicitada y vuelva a intentarlo.</HTML>");
                txtFecSol.requestFocus();
                return false;
            } else {
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

                String patron = "dd/MM/yyyy";
                SimpleDateFormat formato = new SimpleDateFormat(patron);
                System.out.println(formato.format(datFecAux));
                String strSplit[] = formato.format(datFecAux).split("/");
                Calendar calObjAux = java.util.Calendar.getInstance();
                calObjAux.set(Integer.valueOf(strSplit[2]), Integer.valueOf(strSplit[1]) - 1, Integer.valueOf(strSplit[0]));
                java.util.Date datFecAuxSis = calObjAux.getTime();

                java.util.Calendar calObj = java.util.Calendar.getInstance();
                int Fecha[] = txtFecSol.getFecha(txtFecSol.getText());
                //String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                calObj.set(Fecha[2], Fecha[1] - 1, Fecha[0]);
                java.util.Date datFecSolIng = calObj.getTime();
            } 
            
            if (!chkEnt.isSelected() && !chkSal.isSelected()) {

                tabGen.setSelectedIndex(0);

                mostrarMsgInf(
                        "<HTML>Debe seleccionar al menos una "
                        + "<FONT COLOR=\"blue\">Marcación</FONT> "
                        + "(Entrada o Salida).</HTML>"
                );

                chkEnt.requestFocus();

                return false;
            }
            
            if (chkEnt.isSelected() && chkSal.isSelected()) {

                String strHorEnt = cboHHEnt.getSelectedItem().toString()+ ":" + cboMMEnt.getSelectedItem().toString();

                String strHorSal = cboHHSal.getSelectedItem().toString()+ ":" + cboMMSal.getSelectedItem().toString();

                if (strHorEnt.equals(strHorSal)) {

                    mostrarMsgInf(
                            "<HTML>La <FONT COLOR=\"blue\">Hora Entrada</FONT> "
                            + "no puede ser igual a la "
                            + "<FONT COLOR=\"blue\">Hora Salida</FONT>.</HTML>"
                    );

                    cboHHSal.requestFocus();
                    return false;
                }
            }
            

            if (txtObs1.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Observacion 1</FONT> es obligatorio.<BR>Escriba en el campo y vuelva a intentarlo.</HTML>");
                txtObs1.requestFocus();
                return false;
            }
            
            
            return true;
     }

        public boolean insertar() {
            boolean blnRes = false;

            try {
                if (validaCampos()) {
                    if (validaSolicitudPendiente()) {
                        if (insertarReg()) {
                            blnRes = true;
                        }
                    }

                }

            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }
        

        public boolean insertarReg() {

            boolean blnRes = false;

            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            String strSQL = "";

            int intCodDoc = 0;
            int intNumDoc = 0;

            try {

                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                if (con != null) {

                    con.setAutoCommit(false);

                    stmLoc = con.createStatement();

                    txtsql.setText("");

                    /*OBTENER co_doc*/
                    strSQL = "SELECT CASE "
                            + "WHEN (MAX(co_doc)+1) IS NULL THEN 1 "
                            + "ELSE MAX(co_doc)+1 "
                            + "END AS co_doc "
                            + "FROM tbm_cabsolmar "
                            + "WHERE co_emp="
                            + objZafParSis.getCodigoEmpresa()
                            + " AND co_loc="
                            + objZafParSis.getCodigoLocal()
                            + " AND co_tipdoc="
                            + txtCodTipDoc.getText();

                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }

                    rstLoc.close();
                    rstLoc = null;

                    /*OBTENER NUMERO DOCUMENTO*/
                    strSQL = "SELECT CASE "
                            + "WHEN (ne_ultdoc+1) IS NULL THEN 1 "
                            + "ELSE ne_ultdoc+1 "
                            + "END AS numDoc "
                            + "FROM tbm_cabtipdoc "
                            + "WHERE co_emp="
                            + objZafParSis.getCodigoEmpresa()
                            + " AND co_loc="
                            + objZafParSis.getCodigoLocal()
                            + " AND co_tipdoc="
                            + txtCodTipDoc.getText();

                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next()) {
                        intNumDoc = rstLoc.getInt("numDoc");
                    }

                    rstLoc.close();
                    rstLoc = null;

                    /*VALIDAR EMPLEADOS SELECCIONADOS*/
                    int intCom = 0;

                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i,INT_TBL_CHKTRA).equals(true)) {
                            intCom++;
                        }
                    }

                    if (intCom >= 1) {
                        int Fecha[] =txtFecDoc.getFecha(txtFecDoc.getText());

                        String strFecha = "#" + Fecha[2] + "/" + Fecha[1] + "/" + Fecha[0] + "#";

                        /*INSERTAR SOLICITUD*/
                        if (insertarRegSol(con,intCodDoc,intNumDoc,strFecha)) {

                            if (actualizarCabTipDoc(con,intNumDoc)) {

                                con.commit();

                                txtCodDoc.setText("" + intCodDoc);
                                txtNumDoc.setText("" + intNumDoc);

                                try
                                {
                                    enviarCorreoSolicitudMarcacion();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                                blnRes = true;

                            } else {

                                con.rollback();
                            }

                        } else {

                            con.rollback();
                        }

                    } else {

                        con.rollback();

                        mostrarMsgInf("NO HA SELECCIONADO EMPLEADOS " + "QUE APLIQUEN A LA SOLICITUD");

                        blnRes = false;
                    }

                    con.close();
                    con = null;
                }

            } catch (java.sql.SQLException Evt) {

                blnRes = false;

                objUti.mostrarMsgErr_F1(this, Evt);

            } catch (Exception Evt) {

                blnRes = false;

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

            return blnRes;
        }

        private boolean actualizarCabTipDoc(java.sql.Connection conn, int intNumDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql += " ; UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean insertarRegSol(java.sql.Connection con, int intCodDoc, int intNumDoc, String strFecha) {
            boolean blnRes = false;

            String strSql = "";
            String strFecSis = "";

            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            java.sql.Statement stmLocAut = null;
            java.sql.ResultSet rstLocAut = null;

            try {

                if (con != null) {

                    stmLoc = con.createStatement();

                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),
                            objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    String strSt_Aut = "P";
                    String strSt_Reg = "A";

                    int intCodTipDoc = Integer.parseInt(txtCodTipDoc.getText());
                    int intNum_Doc = Integer.parseInt(txtNumDoc.getText());
                    int intCo_Dep = Integer.parseInt(txtCodDep.getText());
                    int intCo_Mot = Integer.parseInt(txtCodMot.getText());

                    int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());

                    String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                    /*HORAS SOLICITADAS*/
                    String strHorEnt = "null";
                    String strHorSal = "null";

                    if (chkEnt.isSelected()) {
                        strHorEnt = "'" + cboHHEnt.getSelectedItem().toString() + ":" + cboMMEnt.getSelectedItem().toString() + ":00'";
                    }

                    if (chkSal.isSelected()) {
                        strHorSal = "'" + cboHHSal.getSelectedItem().toString() + ":" + cboMMSal.getSelectedItem().toString()+ ":00'";
                    }

                    /*VALIDACION*/
                    if (!chkEnt.isSelected() && !chkSal.isSelected()) {

                        mostrarMsgInf("Debe seleccionar Entrada o Salida.");
                        return false;
                    }

                    /*INSERT CABECERA*/
                    strSql = "INSERT INTO tbm_cabsolmar("
                            + "co_emp, "
                            + "co_loc, "
                            + "co_tipdoc, "
                            + "co_doc, "
                            + "fe_doc, "
                            + "ne_numdoc, "
                            + "co_dep, "
                            + "co_mot, "
                            + "fe_sol, "
                            + "ho_ent, "
                            + "ho_sal, "
                            + "st_aut, "
                            + "ho_autent, "
                            + "ho_autsal, "
                            + "tx_obs1, "
                            + "tx_obs2, "
                            + "st_reg, "
                            + "fe_ing, "
                            + "fe_ultmod, "
                            + "co_usring, "
                            + "co_usrmod"
                            + ") "
                            + "VALUES("
                            + objZafParSis.getCodigoEmpresa() + ", "
                            + objZafParSis.getCodigoLocal() + ", "
                            + intCodTipDoc + ", "
                            + intCodDoc + ", "
                            + "'" + strFecha + "', "
                            + intNum_Doc + ", "
                            + intCo_Dep + ", "
                            + intCo_Mot + ", "
                            + "'" + strFechaSol + "', "
                            + strHorEnt + ", "
                            + strHorSal + ", "
                            + "'" + strSt_Aut + "', "
                            + "null, "
                            + "null, "
                            + objUti.codificar(txtObs1.getText()) + ", "
                            + objUti.codificar(txtObs2.getText()) + ", "
                            + "'" + strSt_Reg + "', "
                            + "current_timestamp, "
                            + "null, "
                            + objZafParSis.getCodigoUsuario() + ", "
                            + "null"
                            + ")";

                    System.out.println("insertCab: " + strSql);

                    stmLoc.executeUpdate(strSql);

                    /*INSERT DETALLE*/
                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_LINEA).toString().equals("M")) {

                            strSql = "select 1 as flag "
                                    + "from tbm_detsolmar "
                                    + "where co_emp=" + objZafParSis.getCodigoEmpresa()
                                    + " and co_loc=" + objZafParSis.getCodigoLocal()
                                    + " and co_tipdoc=" + txtCodTipDoc.getText()
                                    + " and co_doc=" + intCodDoc
                                    + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString()
                                    + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();

                            rstLoc = stmLoc.executeQuery(strSql);

                            int intFlag = 0;

                            if (rstLoc.next()) {
                                intFlag = rstLoc.getInt("flag");
                            }

                            if (intFlag == 0) {

                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true)) {

                                    strSql = "INSERT INTO tbm_detsolmar("
                                            + "co_emp, "
                                            + "co_loc, "
                                            + "co_tipdoc, "
                                            + "co_doc, "
                                            + "co_reg, "
                                            + "co_tra, "
                                            + "co_emppag"
                                            + ") "
                                            + "VALUES("
                                            + objZafParSis.getCodigoEmpresa() + ", "
                                            + objZafParSis.getCodigoLocal() + ", "
                                            + txtCodTipDoc.getText() + ", "
                                            + intCodDoc + ", "
                                            + tblDat.getValueAt(i, INT_TBL_COREG).toString() + ", "
                                            + tblDat.getValueAt(i, INT_TBL_CODTRA).toString() + ", "
                                            + tblDat.getValueAt(i, INT_TBL_CODEMPPAG).toString()
                                            + ")";

                                    System.out.println("insertDet: " + strSql);
                                    stmLoc.executeUpdate(strSql);
                                }

                            } else {

                                if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(false)) {

                                    strSql = "delete from tbm_detsolmar "
                                            + "where co_emp=" + objZafParSis.getCodigoEmpresa()
                                            + " and co_loc=" + objZafParSis.getCodigoLocal()
                                            + " and co_tipdoc=" + txtCodTipDoc.getText()
                                            + " and co_doc=" + intCodDoc
                                            + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_COREG).toString()
                                            + " and co_tra=" + tblDat.getValueAt(i, INT_TBL_CODTRA).toString();

                                    stmLoc.executeUpdate(strSql);

                                    System.out.println("deleteDet: " + strSql);
                                }
                            }
                        }
                    }

                    
                    //validacion solo para el caso de Alarcon Josue, que el jefe es DOBANDO y no JMARIN
                    boolean blnEsAlarcon = false;

                    for (int i = 0; i < tblDat.getRowCount(); i++)
                    {
                        if (tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(true))
                        {
                            int intCodTra = Integer.parseInt(
                                    tblDat.getValueAt(i, INT_TBL_CODTRA).toString()
                            );

                            if (intCodTra == 920)
                            {
                                blnEsAlarcon = true;
                                break;
                            }
                        }
                    }
                    
                    /*INSERT AUTORIZACION*/
                    strSql = "select * "
                            + "from tbm_usrAutSolMarDep "
                            + "where co_dep=" + txtCodDep.getText();

                    System.out.println("Autorizadores: " + strSql);

                    stmLocAut = con.createStatement();

                    rstLocAut = stmLocAut.executeQuery(strSql);

                    if (rstLocAut.next()) {

                        do {
                            //validacion solo para el caso de Alarcon Josue, que el jefe es DOBANDO y no JMARIN
                            int intCodUsr = rstLocAut.getInt("co_usr");

                            if (txtCodDep.getText().equals("21"))
                            {
                                if (blnEsAlarcon)
                                {
                                    // Alarcón 
                                    if (intCodUsr != 128 && intCodUsr != 317)
                                    {
                                        continue;
                                    }
                                }
                                else
                                {
                                    // Resto de Sistemas
                                    if (intCodUsr != 458 && intCodUsr != 317)
                                    {
                                        continue;
                                    }
                                }
                            }
                            
                            strSql = "INSERT INTO tbm_autsolmar("
                                    + "co_emp, "
                                    + "co_loc, "
                                    + "co_tipdoc, "
                                    + "co_doc, "
                                    + "co_reg, "
                                    + "st_aut"
                                    + ") "
                                    + "VALUES("
                                    + objZafParSis.getCodigoEmpresa() + ", "
                                    + objZafParSis.getCodigoLocal() + ", "
                                    + txtCodTipDoc.getText() + ", "
                                    + intCodDoc + ", "
                                    + rstLocAut.getInt("co_reg") + ", "
                                    + "'P'"
                                    + ")";

                            System.out.println("insertAut: " + strSql);

                            stmLoc.executeUpdate(strSql);

                        } while (rstLocAut.next());

                    } else {
                        mostrarMsgInf("NO EXISTEN USUARIOS ASOCIADOS PARA AUTORIZAR MARCACIONES");
                        return false;
                    }

                    /*ACTUALIZA ULTIMO DOCUMENTO*/
                    strSql = "update tbm_cabtipdoc "
                            + "set ne_ultdoc="
                            + Integer.parseInt(txtNumDoc.getText())
                            + " where co_emp=" + objZafParSis.getCodigoEmpresa()
                            + " and co_loc=" + objZafParSis.getCodigoLocal()
                            + " and co_tipdoc=" + txtCodTipDoc.getText();

                    System.out.println("updateCabTipDoc: " + strSql);

                    stmLoc.executeUpdate(strSql);

                    blnRes = true;
                }

            } catch (java.sql.SQLException Evt) {

                Evt.printStackTrace();

                blnRes = false;

                objUti.mostrarMsgErr_F1(this, Evt);

            } catch (Exception Evt) {

                Evt.printStackTrace();

                blnRes = false;

                objUti.mostrarMsgErr_F1(this, Evt);

            } finally {

                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }

                try {
                    stmLocAut.close();
                } catch (Throwable ignore) {
                }

                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }

                try {
                    rstLocAut.close();
                } catch (Throwable ignore) {
                }
            }

            return blnRes;
        }


        private boolean validarDat() {
            boolean blnRes = true;
            if (txtCodTipDoc.getText().trim().equals("")) {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }

            return blnRes;
        }

        public boolean modificar() {
            boolean blnRes = false;
            java.sql.Connection conn = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";

            try {

                strAux = objTooBar.getEstadoRegistro();

                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                    return false;
                }

                if (validaCampos()) {
                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        if (objTblMod.getRowCountChecked(INT_TBL_CHKTRA) >= 1) {

                            /*VALIDAR QUE LOS EMPLEADOS ESCOGIDOS NO SE ENCUENTREN EN OTRA SOLICITUD*/
                            int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
                            String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                            strSQL = "select co_tra from tbm_detSolMar a "
                                    + "where co_doc in (select distinct co_doc from tbm_cabSolMar where st_reg='A' and fe_sol='" + strFechaSol + "')";
                            rstLoc = stmLoc.executeQuery(strSQL);

                            boolean blnExtTraSel = false;
                            while (rstLoc.next()) {
                                String strCoTraSel = rstLoc.getString("co_tra");
                                for (int i = 0; i < tblDat.getRowCount(); i++) {

                                }
                            }

                            if (!blnExtTraSel) {
                                if (modificarCab(conn, Integer.parseInt(txtCodDoc.getText()))) {
                                    if (modificarDet(conn, Integer.parseInt(txtCodDoc.getText()))) {
                                        conn.commit();
                                        blnRes = true;
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                                mostrarMsgInf("HAY EMPLEADOS YA SELECCIONADOS PARA OTRA SOLICITUD");
                                return false;
                            }

                        } else {
                            con.rollback();
                            mostrarMsgInf("NO HA SELECCIONADO EMPLEADOS QUE APLIQUEN A LA SOLICITUD");
                            return false;
                        }
                    }
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
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
                    conn.close();
                } catch (Throwable ignore) {
                }
            }

            return blnRes;
        }

        public boolean modificarCab(java.sql.Connection conn,int intCodDoc) {

        boolean blnRes = false;

        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        String strFechaDoc = "";
        String strFechaSol = "";

        String strFecSis = "";

        try {

            if (conn != null) {

                stmLoc = conn.createStatement();

                /*VALIDAR ESTADO AUTORIZACION*/
                strSQL = "SELECT st_aut "
                        + "FROM tbm_cabsolmar "
                        + "WHERE co_emp="
                        + objZafParSis.getCodigoEmpresa()
                        + " AND co_loc="
                        + objZafParSis.getCodigoLocal()
                        + " AND co_tipdoc="
                        + txtCodTipDoc.getText()
                        + " AND co_doc="
                        + intCodDoc;

                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next()) {

                    String strStAut = rstLoc.getString("st_aut");

                    if (!strStAut.equals("P")) {
                        mostrarMsgInf("NO SE PUEDE MODIFICAR LA SOLICITUD " + "PORQUE YA SE ENCUENTRA APROBADA.");
                        return false;
                    }
                }

                /*FECHA DOCUMENTO*/
                int FechaDoc[] = txtFecDoc.getFecha(txtFecDoc.getText());

                strFechaDoc = "#" + FechaDoc[2] + "/" + FechaDoc[1] + "/" + FechaDoc[0] + "#";

                /*FECHA SOLICITUD*/
                int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());

                strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";

                /*FECHA SISTEMA*/
                strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                /*HORAS*/
                String strHorEnt = "null";
                String strHorSal = "null";

                if (chkEnt.isSelected()) {
                    strHorEnt = "'" + cboHHEnt.getSelectedItem().toString() + ":" + cboMMEnt.getSelectedItem().toString() + ":00'";
                }

                if (chkSal.isSelected()) {
                    strHorSal = "'" + cboHHSal.getSelectedItem().toString() + ":" + cboMMSal.getSelectedItem().toString()+ ":00'";
                }

                /*UPDATE*/
                strSQL = "UPDATE tbm_cabsolmar SET "
                        + "fe_doc='" + strFechaDoc + "', "
                        + "co_dep=" + txtCodDep.getText() + ", "
                        + "co_mot=" + txtCodMot.getText() + ", "
                        + "fe_sol='" + strFechaSol + "', "
                        + "ho_ent=" + strHorEnt + ", "
                        + "ho_sal=" + strHorSal + ", "
                        + "tx_obs1="
                        + objUti.codificar(txtObs1.getText()) + ", "
                        + "tx_obs2="
                        + objUti.codificar(txtObs2.getText()) + ", "
                        + "fe_ultmod='" + strFecSis + "', "
                        + "co_usrmod="
                        + objZafParSis.getCodigoUsuario()
                        + " "
                        + "WHERE co_emp="
                        + objZafParSis.getCodigoEmpresa()
                        + " AND co_loc="
                        + objZafParSis.getCodigoLocal()
                        + " AND co_tipdoc="
                        + txtCodTipDoc.getText()
                        + " AND co_doc="
                        + intCodDoc;

                System.out.println("modificarCab: " + strSQL);

                stmLoc.executeUpdate(strSQL);

                stmLoc.close();
                stmLoc = null;

                blnRes = true;
            }

        } catch (java.sql.SQLException Evt) {

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);

        } catch (Exception Evt) {

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }

        public boolean modificarDet(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSQL = "";

            try {
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    int intCom = 0;
                    for (int i = 0; i < tblDat.getRowCount(); i++) {
 
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void noEditable(boolean blnEst) {
            txtObs1.setEditable(false);
            txtObs2.setEditable(false);
        }

        public void clnTextos() {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";

            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtFecDoc.setText("");

            txtCodDep.setText("");
            txtDesDep.setText("");
            txtNumDoc.setText("");

            txtCodMot.setText("");
            txtDesMot.setText("");
            txtCodDoc.setText("");

            txtObs1.setText("");
            txtObs2.setText("");

            txtCodTipDocSol.setText("");
            txtCodDoc.setText("");

            txtFecSol.setText("");
            txtHorSolHas.setText("");

            cboHHSal.setSelectedIndex(0);
            cboMMSal.setSelectedIndex(0);

            cboHHEnt.setSelectedIndex(0);
            cboMMEnt.setSelectedIndex(0);

            objTblMod.removeAllRows();
        }

        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    if (STM_GLO != null) {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam = false;

            return blnRes;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {

            return true;
        }

        public boolean afterConsultar() {

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');

            return true;
        }

        public boolean afterModificar() {

            this.setEstado('w');

            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        /**
         * Esta función muestra un mensaje "showConfirmDialog". Presenta las
         * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
         * hacer el sistema seleccionando una de las opciones que se presentan.
         */
        private int mostrarMsgCon(String strMsg) {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
        }

        /**
         * Esta función se encarga de agregar el listener "DocumentListener" a
         * los objTooBars de tipo texto para poder determinar si su contenido a
         * cambiado o no.
         */
        private boolean isRegPro() {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;
                    blnRes = true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
                    break;
            }
            return blnRes;
        }

        public boolean consultar() {
            return _consultar(FilSql()); //Solo Modo Consulta
        }

        private boolean _consultar(String strFil) {
            boolean blnRes = false;
            String strSql = "";
            try {
                if (!validarDat()) {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                    String strCoUsrIng = "";
                    
                    int intCodUsr = objZafParSis.getCodigoUsuario();
                    
                    if (intCodUsr != 1 && intCodUsr != 317) {
                        strCoUsrIng = " and a.co_usring= " + objZafParSis.getCodigoUsuario();
                    }
                    strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc "
                            + " FROM tbm_cabSolMar AS a  "
                            + " WHERE a.co_emp=1" +// objZafParSis.getCodigoEmpresa() +
                            " and a.co_loc=" + objZafParSis.getCodigoLocal() + strCoUsrIng
                            + " AND a.st_reg NOT IN('E') " + strFil + " ORDER BY a.ne_numdoc ";
                    System.out.println("Consultar: "+strSql);

                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        setEstado('l');
                        setMenSis("Listo");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            System.gc();
            return blnRes;
        }

        private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatSolCon) {

        boolean blnRes = false;

        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstDatSol = null;

        String strSql = "";
        String strStReg = "";

        try {

            if (conn != null) {

                stmLoc = conn.createStatement();

                strSql = "";
                strSql += " SELECT c.*, ";
                strSql += "        (d.tx_ape || ' ' || d.tx_nom) AS nomCom, ";
                strSql += "        f.tx_deslar AS deslardep, ";
                strSql += "        g.tx_deslar AS deslarmot, ";
                strSql += "        h.tx_descor AS txdescortipdoc ";
                strSql += " FROM ( ";
                strSql += "        SELECT a.co_emp, ";
                strSql += "               a.co_loc, ";
                strSql += "               a.co_tipdoc, ";
                strSql += "               a.co_doc, ";
                strSql += "               a.fe_doc, ";
                strSql += "               a.ne_numdoc, ";
                strSql += "               a.co_dep, ";
                strSql += "               a.co_mot, ";
                strSql += "               a.fe_sol, ";
                strSql += "               a.ho_ent, ";
                strSql += "               a.ho_sal, ";
                strSql += "               a.tx_obs1, ";
                strSql += "               a.tx_obs2, ";
                strSql += "               a.st_reg, ";
                strSql += "               b.co_reg, ";
                strSql += "               b.co_tra ";
                strSql += "        FROM tbm_cabsolmar a ";
                strSql += "        INNER JOIN tbm_detsolmar b ";
                strSql += "        ON (a.co_emp=b.co_emp ";
                strSql += "        AND a.co_loc=b.co_loc ";
                strSql += "        AND a.co_tipdoc=b.co_tipdoc ";
                strSql += "        AND a.co_doc=b.co_doc) ";
                strSql += "      ) c ";
                strSql += " INNER JOIN tbm_tra d ";
                strSql += " ON (c.co_tra=d.co_tra) ";
                strSql += " INNER JOIN tbm_dep f ";
                strSql += " ON (f.co_dep=c.co_dep) ";
                strSql += " INNER JOIN tbm_motsolmar g ";
                strSql += " ON (g.co_mot=c.co_mot) ";
                strSql += " INNER JOIN tbm_cabtipdoc h ";
                strSql += " ON (h.co_emp=c.co_emp ";
                strSql += " AND h.co_loc=c.co_loc ";
                strSql += " AND h.co_tipdoc=c.co_tipdoc) ";
                strSql += " WHERE c.co_emp="
                        + rstDatSolCon.getString("co_emp");
                strSql += " AND c.co_loc="
                        + rstDatSolCon.getString("co_loc");
                strSql += " AND c.co_tipdoc="
                        + rstDatSolCon.getString("co_tipdoc");
                strSql += " AND c.co_doc="
                        + rstDatSolCon.getString("co_doc");
                strSql += " ORDER BY nomCom ";

                System.out.println("refrescaDatos: " + strSql);

                rstDatSol = stmLoc.executeQuery(strSql);

                boolean blnPriIng = false;

                while (rstDatSol.next()) {

                    if (!blnPriIng) {

                        vecDat = new Vector();

                        strStReg = rstDatSol.getString("st_reg");

                        txtDesCorTipDoc.setText(rstDatSol.getString("txdescortipdoc"));
                        txtCodDep.setText(rstDatSol.getString("co_dep"));
                        insertarDetalle();
                        txtDesDep.setText(rstDatSol.getString("deslardep"));
                        txtCodMot.setText(rstDatSol.getString("co_mot"));
                        txtDesMot.setText(rstDatSol.getString("deslarmot"));
                        txtNumDoc.setText(rstDatSol.getString("ne_numdoc"));
                        txtCodDoc.setText(
                                rstDatSol.getString("co_doc")
                        );

                        txtObs1.setText(
                                rstDatSol.getString("tx_obs1")
                        );

                        txtObs2.setText(
                                rstDatSol.getString("tx_obs2")
                        );

                        /*FECHA DOCUMENTO*/
                        java.util.Date dateObj =
                                rstDatSol.getDate("fe_doc");

                        if (dateObj == null) {

                            txtFecDoc.setText("");

                        } else {

                            java.util.Calendar calObj =
                                    java.util.Calendar.getInstance();

                            calObj.setTime(dateObj);

                            txtFecDoc.setText(
                                    calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                    calObj.get(java.util.Calendar.MONTH) + 1,
                                    calObj.get(java.util.Calendar.YEAR)
                            );
                        }

                        /*FECHA SOLICITUD*/
                        dateObj = rstDatSol.getDate("fe_sol");

                        if (dateObj == null) {

                            txtFecSol.setText("");

                        } else {

                            java.util.Calendar calObj =
                                    java.util.Calendar.getInstance();

                            calObj.setTime(dateObj);

                            txtFecSol.setText(
                                    calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                    calObj.get(java.util.Calendar.MONTH) + 1,
                                    calObj.get(java.util.Calendar.YEAR)
                            );
                        }

                        /*ENTRADA*/
                        if (rstDatSol.getString("ho_ent") != null) {

                            chkEnt.setSelected(true);

                            cboHHEnt.setEnabled(true);
                            cboMMEnt.setEnabled(true);

                            cboHHEnt.setSelectedItem(
                                    rstDatSol.getString("ho_ent")
                                    .substring(0, 2)
                            );

                            cboMMEnt.setSelectedItem(
                                    rstDatSol.getString("ho_ent")
                                    .substring(3, 5)
                            );

                        } else {

                            cboHHEnt.setSelectedItem("00");
                            cboMMEnt.setSelectedItem("00");
                            
                            chkEnt.setSelected(false);
                            cboHHEnt.setEnabled(false);
                            cboMMEnt.setEnabled(false);
                        }

                        /*SALIDA*/
                        if (rstDatSol.getString("ho_sal") != null) {

                            chkSal.setSelected(true);

                            cboHHSal.setEnabled(true);
                            cboMMSal.setEnabled(true);

                            cboHHSal.setSelectedItem(
                                    rstDatSol.getString("ho_sal")
                                    .substring(0, 2)
                            );

                            cboMMSal.setSelectedItem(
                                    rstDatSol.getString("ho_sal")
                                    .substring(3, 5)
                            );

                        } else {

                            cboHHEnt.setSelectedItem("00");
                            cboMMEnt.setSelectedItem("00");
                            
                            chkSal.setSelected(false);
                            cboHHSal.setEnabled(false);
                            cboMMSal.setEnabled(false);
                        }

                        blnPriIng = true;
                    }

                    /*DETALLE EMPLEADOS*/
                    Vector vecReg = new Vector();

                    vecReg.add(INT_TBL_LINEA, null);

                    vecReg.add(INT_TBL_CHKTRA, true);

                    vecReg.add(
                            INT_TBL_CODEMPPAG,
                            rstDatSol.getString("co_emp")
                    );

                    vecReg.add(
                            INT_TBL_CODTRA,
                            rstDatSol.getString("co_tra")
                    );

                    vecReg.add(
                            INT_TBL_NOMTRA,
                            rstDatSol.getString("nomCom")
                    );

                    vecReg.add(
                            INT_TBL_COREG,
                            rstDatSol.getString("co_reg")
                    );

                    vecDat.add(vecReg);
                }

                /*ASIGNAR DATOS*/
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);

                vecDat.clear();
                vecDat = null;

                rstDatSol.close();
                rstDatSol = null;

                /*POSICION TOOLBAR*/
                int intPosRel = rstDatSolCon.getRow();

                rstDatSolCon.last();

                objTooBar.setPosicionRelativa(
                        ""
                        + intPosRel
                        + " / "
                        + rstDatSolCon.getRow()
                );

                mostrarEstado(strStReg);

                rstDatSolCon.absolute(intPosRel);

                blnRes = true;
            }

        } catch (java.sql.SQLException Evt) {

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);

        } catch (Exception Evt) {

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
        
    private boolean validaSolicitudPendiente() {

        boolean blnRes = true;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try {

            con = java.sql.DriverManager.getConnection(
                    objZafParSis.getStringConexion(),
                    objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos()
            );

            if (con != null) {

                stmLoc = con.createStatement();

                int FechaSol[] =
                        txtFecSol.getFecha(
                                txtFecSol.getText()
                        );

                String strFechaSol = "#"
                        + FechaSol[2] + "/"
                        + FechaSol[1] + "/"
                        + FechaSol[0] + "#";

                for (int i = 0; i < tblDat.getRowCount(); i++) {

                    if (tblDat.getValueAt(
                            i,
                            INT_TBL_CHKTRA
                    ).equals(true)) {

                        String strCodTra =
                                tblDat.getValueAt(
                                        i,
                                        INT_TBL_CODTRA
                                ).toString();

                        /*VALIDAR ENTRADA*/
                        if (chkEnt.isSelected()) {

                            strSQL = "SELECT a.co_tra "
                                    + "FROM tbm_detsolmar a "
                                    + "INNER JOIN tbm_cabsolmar b "
                                    + "ON a.co_emp=b.co_emp "
                                    + "AND a.co_loc=b.co_loc "
                                    + "AND a.co_tipdoc=b.co_tipdoc "
                                    + "AND a.co_doc=b.co_doc "
                                    + "WHERE b.st_reg='A' "                                    
                                    + "AND b.fe_sol='"
                                    + strFechaSol + "' "
                                    + "AND a.co_tra="
                                    + strCodTra + " "
                                    + "AND b.ho_ent IS NOT NULL";

                            rstLoc = stmLoc.executeQuery(strSQL);

                            if (rstLoc.next()) {

                                mostrarMsgInf(
                                        "EL EMPLEADO "
                                        + strCodTra
                                        + " YA TIENE UNA SOLICITUD "
                                        + "PENDIENTE DE ENTRADA "
                                        + "PARA ESA FECHA."
                                );

                                blnRes = false;

                                break;
                            }

                            rstLoc.close();
                            rstLoc = null;
                        }

                        /*VALIDAR SALIDA*/
                        if (chkSal.isSelected()) {

                            strSQL = "SELECT a.co_tra "
                                    + "FROM tbm_detsolmar a "
                                    + "INNER JOIN tbm_cabsolmar b "
                                    + "ON a.co_emp=b.co_emp "
                                    + "AND a.co_loc=b.co_loc "
                                    + "AND a.co_tipdoc=b.co_tipdoc "
                                    + "AND a.co_doc=b.co_doc "
                                    + "WHERE b.st_reg='A' "                                    
                                    + "AND b.fe_sol='"
                                    + strFechaSol + "' "
                                    + "AND a.co_tra="
                                    + strCodTra + " "
                                    + "AND b.ho_sal IS NOT NULL";

                            rstLoc = stmLoc.executeQuery(strSQL);

                            if (rstLoc.next()) {

                                mostrarMsgInf(
                                        "EL EMPLEADO "
                                        + strCodTra
                                        + " YA TIENE UNA SOLICITUD "
                                        + "PENDIENTE DE SALIDA "
                                        + "PARA ESA FECHA."
                                );

                                blnRes = false;

                                break;
                            }

                            rstLoc.close();
                            rstLoc = null;
                        }
                    }
                }
            }

        } catch (java.sql.SQLException Evt) {

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);

        } catch (Exception Evt) {

            blnRes = false;

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

        return blnRes;
    }
    
    private boolean enviarCorreoSolicitudMarcacion()
    {
    boolean blnRes = true;   
    try
    {
        String strSbj =
                "Zafiro: Solicitud de Marcacion Nro. "
                + txtCodDoc.getText();

        StringBuilder strDet = new StringBuilder();

        strDet.append("<table style='border-collapse:collapse; font-family:Arial; font-size:12px;'>");

        strDet.append("<tr style='background-color:#2c3e50; color:white;'>");
        strDet.append("<th style='padding:6px;'>Codigo</th>");
        strDet.append("<th style='padding:6px;'>Empleado</th>");
        strDet.append("<th style='padding:6px;'>Entrada Solicitada</th>");
        strDet.append("<th style='padding:6px;'>Salida Solicitada</th>");
        strDet.append("</tr>");

        int intTotTra = 0;

        for (int i = 0; i < tblDat.getRowCount(); i++)
        {
            Boolean blnSel = (Boolean)tblDat.getValueAt(i,INT_TBL_CHKTRA);

            if (blnSel != null && blnSel)
            {
                intTotTra++;

                String strCodTra = tblDat.getValueAt(i,INT_TBL_CODTRA).toString();

                String strNomTra = tblDat.getValueAt(i,INT_TBL_NOMTRA).toString();

                String strHorEnt = chkEnt.isSelected() ? cboHHEnt.getSelectedItem().toString() + ":" + cboMMEnt.getSelectedItem().toString(): "N/A";

                String strHorSal = chkSal.isSelected() ? cboHHSal.getSelectedItem().toString() + ":" + cboMMSal.getSelectedItem().toString(): "N/A";

                strDet.append("<tr style='border-bottom:1px solid #ddd;'>");

                strDet.append("<td style='padding:6px;'>")
                      .append(strCodTra)
                      .append("</td>");

                strDet.append("<td style='padding:6px;'>")
                      .append(strNomTra)
                      .append("</td>");

                strDet.append("<td style='padding:6px;'>")
                      .append(strHorEnt)
                      .append("</td>");

                strDet.append("<td style='padding:6px;'>")
                      .append(strHorSal)
                      .append("</td>");

                strDet.append("</tr>");
            }
        }

        strDet.append("</table>");

        String strMsg =
                "<html>"
                + "<body style='font-family:Arial;font-size:12px'>"              

                + "<b>Usuario:</b> "
                + objZafParSis.getNombreUsuario()
                + "<br>"
                
//                + "<b>Solicitud:</b> "
//                + txtCodDoc.getText()
//                + "<br>"
                
                + "<b>Fecha solicitada:</b> "
                + txtFecSol.getText()
                + "<br>"

                + "<b>Motivo:</b> "
                + txtDesMot.getText()
                + "<br>"
                
                + "<b>Observacion:</b>"
                + txtObs1.getText()
                + "<br>"
                + "<br><br>"
                
//                + "<b>Total empleados:</b> "
//                + intTotTra
//                + "<br><br>"

                + strDet.toString()

                + "<br>"

                + "<br><br>"                

                + "<br><hr>"

                + "<span style='font-size:11px;color:#777;'>"
                + "Este correo ha sido generado automaticamente por Zafiro."
                + "</span>"

                + "</body>"
                + "</html>";

        if (objNotCorEle == null)
        {
            objNotCorEle =
                    new ZafNotCorEle(objZafParSis);
        }

        objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(
                objZafParSis.getCodigoEmpresa(),
                objZafParSis.getCodigoLocal(),
                42,
                strMsg,
                strSbj
        );
    }
    catch (Exception e)
    {
        e.printStackTrace();
        blnRes = false;
    }

    return blnRes;


    }


        private void mostrarEstado(String strStReg) {
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if (strStReg.equals("I")) {
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            } else {
                if (strStReg.equals("E")) {
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                } else {
                    setEstadoRegistro("");
                }
            }
        }

        private String FilSql() {
            String sqlFiltro = "";
            //Agregando filtro por Numero de Cotizacion
            if (!txtCodTipDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_tipdoc=" + txtCodTipDoc.getText();
            }

            if (!txtCodDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_doc=" + txtCodDoc.getText();
            }

            if (!txtNumDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.ne_numdoc=" + txtNumDoc.getText();
            }

            //Agregando filtro por Fecha
            if (txtFecDoc.isFecha()) {
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
                sqlFiltro = sqlFiltro + " and a.fe_doc = '" + strFecSql + "'";
            }

            return sqlFiltro;
        }

        public void clickModificar() {
            setEditable(true);

            java.awt.Color colBack;
            colBack = txtCodDoc.getBackground();

            this.setEnabledConsultar(false);

            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickCancelar() {

        }

        public void cierraConnections() {

        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeImprimir() {

            return true;
        }

        public boolean beforeInsertar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {

            return true;
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CHKTRA:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMPPAG:
                    strMsg = "Código de la empresa que paga el sobretiempo";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_HORENT:
                    strMsg = "Hora de entrada";
                    break;
                case INT_TBL_HORSAL:
                    strMsg = "Hora de salida";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /*
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread {

        private int intIndFun;

        public ZafThreadGUI() {
            intIndFun = 0;
        }

        public void run() {
            switch (intIndFun) {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI = null;
        }

        /*
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }
    
    

    /*
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
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {

                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            case 19:
                            default:
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                java.util.Map mapPar = new java.util.HashMap();
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                                mapPar.put("codTipDoc", new Integer(Integer.parseInt(txtCodTipDoc.getText())));
                                mapPar.put("codDoc", new Integer(Integer.parseInt(txtCodDoc.getText())));
                                mapPar.put("SUBREPORT_DIR", strRutRpt);

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

        
    public void cargarTipoDoc(int intVal) {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        System.out.println("intVal: "+intVal);
        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();

                if (objZafParSis.getCodigoUsuario() == 1) {
                    strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                            + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                            + " ,doc.tx_natdoc, doc.st_meringegrfisbod "
                            + " FROM tbr_tipdocprg as menu "
                            + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                            + " WHERE   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " AND  "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " AND  menu.st_reg = 'S' ";
                    System.out.println("cargarTipoDoc1: "+strSql);
                } else {

                    strSql = "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, "
                            + " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "
                            + " ,doc.tx_natdoc, doc.st_meringegrfisbod "
                            + " FROM tbr_tipDocUsr as menu "
                            + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                            + " WHERE   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " AND  "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " AND  "
                            + " menu.co_usr=" + objZafParSis.getCodigoUsuario() + " AND menu.st_reg = 'S' ";
                }

                System.out.println("ZafVen28.cargarTipoDoc: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    strCodTipDoc = txtCodTipDoc.getText();
                    /*strMerIngEgr=((rstLoc.getString("st_meringegrfisbod")==null)?"":rstLoc.getString("st_meringegrfisbod"));
                     strTipIngEgr=((rstLoc.getString("tx_natdoc")==null)?"":rstLoc.getString("tx_natdoc"));*/

                    if (intVal == 1) {
                        txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                    }
                    //objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

   
    
}
