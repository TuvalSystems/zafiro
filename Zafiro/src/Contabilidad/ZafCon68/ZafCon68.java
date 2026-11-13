package Contabilidad.ZafCon68;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCalImpRenSRI.ZafCalImpRenSRI;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafGenAna.ZafGenAna;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
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
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Autorización de roles de pago...
 *
 * @author Roberto Flores Guayaquil, 24/09/2013
 */
public class ZafCon68 extends javax.swing.JInternalFrame {

    private final int INT_TBL_DAT_LIN = 0;
    private final int INT_TBL_DAT_BENGAL = 1;
    private final int INT_TBL_DAT_TRACARENFCAT = 2;
    private final int INT_TBL_DAT_NUMCARFAM = 3;    
    private final int INT_TBL_DAT_TIPIDETRA = 4;
    private final int INT_TBL_DAT_IDENTTRA = 5;
    private final int INT_TBL_DAT_APETRA = 6; 
    private final int INT_TBL_DAT_NOMTRA = 7;
    private final int INT_TBL_DAT_CODESTTRA= 8;
    private final int INT_TBL_DAT_TIPRESTRA = 9;    
    private final int INT_TBL_DAT_PAIRESTRA = 10;
    private final int INT_TBL_DAT_APLCONDOBIMP = 11;
    private final int INT_TBL_DAT_CONTRARESDISC = 12;    
    private final int INT_TBL_DAT_PORDISC = 13;
    private final int INT_TBL_DAT_TIPIDEDISC = 14;
    private final int INT_TBL_DAT_IDEDISC = 15;
    private final int INT_TBL_DAT_SUELTRA = 16;
    private final int INT_TBL_DAT_OTRINGGRA  = 17;
    private final int INT_TBL_DAT_PARTUTIL  = 18;
    private final int INT_TBL_DAT_OTRINGGRAGENOTREMP  = 19;
    private final int INT_TBL_DAT_IMPRENASUEMP = 20;
    private final int INT_TBL_DAT_DECTERCERO = 21;
    private final int INT_TBL_DAT_DECCUARTO = 22;
    private final int INT_TBL_DAT_FONRESERVA = 23;    
    private final int INT_TBL_DAT_COMECOSALDIG = 24;
    private final int INT_TBL_DAT_OTRINGRELDEP = 25;
    private final int INT_TBL_DAT_INGGRAEMP = 26;
    private final int INT_TBL_DAT_TIPSALNETO = 27;    
    private final int INT_TBL_DAT_APOPEREMP = 28;
    private final int INT_TBL_DAT_APOPEROTREMP = 29;
    private final int INT_TBL_DAT_GASVIVTRA = 30;
    private final int INT_TBL_DAT_GASEDUTRA = 31;
    private final int INT_TBL_DAT_GASSALUDTRA = 32;     
    private final int INT_TBL_DAT_GASVESTRA = 33;
    private final int INT_TBL_DAT_GASALITRA = 34;
    private final int INT_TBL_DAT_GASTURTRA = 35;
    private final int INT_TBL_DAT_EXODISC = 36;
    private final int INT_TBL_DAT_EXOTEREDAD= 37;
    private final int INT_TBL_DAT_BASIMPGRA = 38;    
    private final int INT_TBL_DAT_IMPRENCAUS = 39;
    private final int INT_TBL_DAT_REBGASTPER = 40;
    private final int INT_TBL_DAT_IMPRENDESREB = 41;
    private final int INT_TBL_DAT_RENTERASUOTREMPL = 42;
    private final int INT_TBL_DAT_RENASUEMP = 43;
    private final int INT_TBL_DAT_RENASUTRA = 44; 
    
    private ZafTblMod objTblModExc;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
 
     private final int INT_ARL_BAS_IMP_GRA=0;
    private final int INT_ARL_IMP_REN_CAU=1;
    private final int INT_ARL_REB_GAS_PER=2;
    private final int INT_ARL_IMP_DES_REB_GAS_PER=3;
    private final int INT_ARL_REN_RET_TRA=4;
 
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafNotCorEle objNotCorEle;                          //Libreria para Notificaciones de Correo. 
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm,stm1,stm2,stm3,stm4,stm5 ;
    private ResultSet rst,rst1,rst2,rst3;
    private String strSQL, strAux,strSqlInsHis;
    private ZafTblFilCab objTblFilCab;
 
    private Vector vecDat, vecCab, vecReg, vecAux,vecAnali;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru;
    private ZafTblBus objTblBus;

    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;

    private ZafTblCelRenChk objTblCelRenChkRRHH;
    private ZafTblCelEdiChk objTblCelEdiChkRRHH;

    private ZafTblCelRenChk objTblCelRenChkGer;
    private ZafTblCelEdiChk objTblCelEdiChkGer;

    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenButGenArcBco;

    private ZafPerUsr objPerUsr;

    private String strVersion = "v1.1"; 
 
  
 
    private int intAño = 0; 

    /**Periodo de la ultima quicena generada*/
    private int intUltPer = 0;
    /**Año de la ultima quicena generada*/
    private int intUltAni = 0;
    /**Mes de la ultima quicena generada*/
    private int intUltMes = 0; 

    private boolean btnStGXmlGenerado , btnDatosGuard;

    public ZafCon68(ZafParSis obj) {
        try {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objNotCorEle = new ZafNotCorEle(objParSis);  
            objPerUsr = new ZafPerUsr(objParSis);

            butCon.setVisible(false);
            bntGenXML.setVisible(false);
            butCer.setVisible(false);
            bntGuardar.setVisible(false);
            
//            if (objPerUsr.isOpcionEnabled(4778)) {
//                bntGuardar.setVisible(true);
//            }
//            if (objPerUsr.isOpcionEnabled(4779)) {
//                butCon.setVisible(true);
//            }
//
//            if (objPerUsr.isOpcionEnabled(4780)) {
//                bntGenXML.setVisible(true);
//            }
//
//            if (objPerUsr.isOpcionEnabled(337)) {
//                butCer.setVisible(true);
//            }

            if (!configurarFrm()) {
                exitForm();
            }
            cargarAnios();
            butCon.setVisible(true);
            butCer.setVisible(true);
            bntGenXML.setVisible(false);
            bntGuardar.setVisible(false);
            EventoCombo objEvent = new EventoCombo();
          
            cboPerAAAA.addItemListener(objEvent);
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    class EventoCombo implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            objTblMod.removeAllRows();
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
        panRpt = new javax.swing.JPanel();
        panCom = new javax.swing.JPanel();
        cboPerAAAA = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panTab = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        bntGuardar = new javax.swing.JButton();
        bntGenXML = new javax.swing.JButton();
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
        setVisible(true);
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

        panRpt.setLayout(new java.awt.BorderLayout());

        panCom.setPreferredSize(new java.awt.Dimension(100, 70));
        panCom.setLayout(null);

        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panCom.add(cboPerAAAA);
        cboPerAAAA.setBounds(100, 10, 80, 20);

        jLabel1.setText("Periodo:");
        panCom.add(jLabel1);
        jLabel1.setBounds(50, 10, 50, 14);

        panRpt.add(panCom, java.awt.BorderLayout.NORTH);

        panTab.setLayout(new java.awt.BorderLayout());

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat.setViewportView(tblDatos);

        panTab.add(spnDat, java.awt.BorderLayout.CENTER);

        panRpt.add(panTab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panRpt);

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

        bntGuardar.setText("Guardar");
        bntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGuardarActionPerformed(evt);
            }
        });
        panBot.add(bntGuardar);

        bntGenXML.setText("Generar XML");
        bntGenXML.setToolTipText("Cierra la ventana.");
        bntGenXML.setPreferredSize(new java.awt.Dimension(140, 25));
        bntGenXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGenXMLActionPerformed(evt);
            }
        });
        panBot.add(bntGenXML);

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

        setBounds(0, 0, 1211, 503);
    }// </editor-fold>//GEN-END:initComponents
                   /*Permite obtener un log de la tabla tbm_grpvar
     *
     */

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true; 
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
           
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicaci�n.
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
//        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si y No. El usuario es quien determina lo que debe hacer el
     * sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se
     * grabaron y que debe comunicar de este particular al administrador del
     * sistema.
     */
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * Metodo para llamar a la generacion de analiticas 
     * @param con
     * @param strLote
     * @param intCodDia
     * @param arlDatAna
     * @return 
     */
    private boolean GeneraXML(){
            boolean blnRes=true; 
            String strSql1="",strSql2="",strXML="", Nombre="",Apellido="";
            intAño=0;
            try {
                intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());      

                con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

                // Crear el documento XML (Document vacío)
                Document doc = documentBuilder.newDocument();

                // Crear el elemento raíz <rdep>
                Element rdep = doc.createElement("rdep");
                doc.appendChild(rdep);
                
                stm2 = con.createStatement();
                stm1 = con.createStatement();
                stm = con.createStatement();

                //elimina registros de la historica (datos cargados automaticamente sin modificacion manual)
                strSql2="";
                strSql2=" select * from tbm_emp where co_emp=1" ;
                rst2  = stm2.executeQuery(strSql2);
                if (rst2.next()) { 
                    // Agregar subelementos <numRuc>, <anio>, <tipoEmpleador>, <enteSegSocial>
                    rdep.appendChild(createElement(doc, "numRuc", rst2.getString("tx_ruc")));
                    rdep.appendChild(createElement(doc, "anio", String.valueOf(intAño)));
                    rdep.appendChild(createElement(doc, "tipoEmpleador",rst2.getString("tx_tipEmp")));
                    rdep.appendChild(createElement(doc, "enteSegSocial", rst2.getString("tx_entSegSoc")));
 
                    // Crear <retRelDep> y su contenido
                    Element retRelDep = doc.createElement("retRelDep");
                    rdep.appendChild(retRelDep);

                    ///for 
                    strSql2="";
                     strSql2="   select * from Tbm_detdecreldep  where co_ejeFis= " + intAño ;
                   // strSql2="   select * from Tbm_detdecreldep  where tx_ide='0923237838' and co_ejeFis= " + intAño ;
                    rst  = stm1.executeQuery(strSql2);
                    if (rst.next()) {  
                        do{
                            Element datRetRelDep = doc.createElement("datRetRelDep");
                            retRelDep.appendChild(datRetRelDep);
                                 
                            Element empleado = doc.createElement("empleado");
                            datRetRelDep.appendChild(empleado);
                            empleado.appendChild(createElement(doc, "benGalpg",  rst.getString("tx_bengal").trim()));
                            empleado.appendChild(createElement(doc, "enfcatastro", rst.getString("tx_traCarEnfCat").trim()));
                            empleado.appendChild(createElement(doc, "numCargRebGastPers",rst.getString("ne_carFam")));
                            empleado.appendChild(createElement(doc, "tipIdRet", rst.getString("tx_tipIdeTra")));
                            empleado.appendChild(createElement(doc, "idRet", rst.getString("tx_ide")));
                            
                            if (rst.getString("tx_nom").endsWith(" ")) {
                                Nombre = rst.getString("tx_nom").substring(0, rst.getString("tx_nom").length() - 1);
                            }else{
                                Nombre = rst.getString("tx_nom");
                            }
                            if (rst.getString("tx_ape").endsWith(" ")) {
                                Apellido = rst.getString("tx_ape").substring(0, rst.getString("tx_ape").length() - 1);
                            }else{
                                Apellido = rst.getString("tx_ape");
                            }
                            
                            empleado.appendChild(createElement(doc, "apellidoTrab", Apellido.trim()));
                            empleado.appendChild(createElement(doc, "nombreTrab",Nombre.trim()));
                            empleado.appendChild(createElement(doc, "estab", rst.getString("tx_codEst") ));
                            empleado.appendChild(createElement(doc, "residenciaTrab", rst.getString("tx_tipRes")));
                            empleado.appendChild(createElement(doc, "paisResidencia", rst.getString("ne_paiRes")));
                            empleado.appendChild(createElement(doc, "aplicaConvenio",rst.getString("tx_aplConDobImp")));
                            empleado.appendChild(createElement(doc, "tipoTrabajDiscap", rst.getString("tx_conTraResDis")));
                            empleado.appendChild(createElement(doc, "porcentajeDiscap",rst.getString("ne_porDis")));
                            empleado.appendChild(createElement(doc, "tipIdDiscap", rst.getString("tx_tipIdeDis")));
                            empleado.appendChild(createElement(doc, "idDiscap", rst.getString("ne_ideDis")));

                            // Crear los otros elementos dentro de <datRetRelDep>
                            datRetRelDep.appendChild(createElement(doc, "suelSal", rst.getString("nd_sueTot")));
                            datRetRelDep.appendChild(createElement(doc, "sobSuelComRemu",rst.getString("nd_otrIngGra")));
                            datRetRelDep.appendChild(createElement(doc, "partUtil", rst.getString("nd_parUti")));
                            datRetRelDep.appendChild(createElement(doc, "intGrabGen", rst.getString("nd_otrIngGraGenOtrEmp")));
                            datRetRelDep.appendChild(createElement(doc, "impRentEmpl", rst.getString("nd_impRenAsuEmp")));
                            datRetRelDep.appendChild(createElement(doc, "decimTer", rst.getString("nd_decTer")));
                            datRetRelDep.appendChild(createElement(doc, "decimCuar",rst.getString("nd_decCua")));
                            datRetRelDep.appendChild(createElement(doc, "fondoReserva", rst.getString("nd_fonRes")));
                            datRetRelDep.appendChild(createElement(doc, "salarioDigno", rst.getString("nd_comEcoSalDig")));
                            datRetRelDep.appendChild(createElement(doc, "otrosIngRenGrav", rst.getString("nd_otrIngRelDep")));
                            datRetRelDep.appendChild(createElement(doc, "ingGravConEsteEmpl", rst.getString("nd_ingGraEmp")));
                            datRetRelDep.appendChild(createElement(doc, "sisSalNet", rst.getString("ne_tipSalNet")));
                            datRetRelDep.appendChild(createElement(doc, "apoPerIess", rst.getString("nd_apoPerEmp")));
                            datRetRelDep.appendChild(createElement(doc, "aporPerIessConOtrosEmpls",rst.getString("nd_apoPerOtrEmp")));
                            datRetRelDep.appendChild(createElement(doc, "deducVivienda",rst.getString("nd_gasVivTra")));
                            datRetRelDep.appendChild(createElement(doc, "deducSalud", rst.getString("nd_gasSalTra")));
                            datRetRelDep.appendChild(createElement(doc, "deducEducartcult", rst.getString("nd_gasEduTra")));
                            datRetRelDep.appendChild(createElement(doc, "deducAliement", rst.getString("nd_gasAliTra")));
                            datRetRelDep.appendChild(createElement(doc, "deducVestim", rst.getString("nd_gasVesTra")));
                            datRetRelDep.appendChild(createElement(doc, "deduccionTurismo", rst.getString("nd_gasTurTra")));
                            datRetRelDep.appendChild(createElement(doc, "exoDiscap", rst.getString("nd_exoDis")));
                            datRetRelDep.appendChild(createElement(doc, "exoTerEd",rst.getString("nd_exoTerEda")));
                            datRetRelDep.appendChild(createElement(doc, "basImp", rst.getString("nd_basImpGra")));
                            datRetRelDep.appendChild(createElement(doc, "impRentCaus", rst.getString("nd_impRenCau")));
                            datRetRelDep.appendChild(createElement(doc, "rebajaGastosPersonales", rst.getString("nd_rebGasPer")));
                            datRetRelDep.appendChild(createElement(doc, "impuestoRentaRebajaGastosPersonales", rst.getString("nd_impRenDesReb")));
                            datRetRelDep.appendChild(createElement(doc, "valRetAsuOtrosEmpls", rst.getString("nd_renRetAsuOtrEmp")));
                            datRetRelDep.appendChild(createElement(doc, "valImpAsuEsteEmpl", rst.getString("nd_renAsuEmp")));
                            datRetRelDep.appendChild(createElement(doc, "valRet",rst.getString("nd_renAsuTra")));
                            
                        } while (rst.next());
                        // Usar JFileChooser para seleccionar la ubicación y nombre del archivo
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Seleccionar ubicación para guardar el archivo XML");
                        fileChooser.setSelectedFile(new File("rdep_"+ intAño + ".xml"));
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos XML", "xml"));

                        // Abrir el cuadro de diálogo para que el usuario seleccione la ruta
                        int userSelection = fileChooser.showSaveDialog(null);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            StringWriter stringWriter = new StringWriter();
                            StreamResult result = new StreamResult(stringWriter);  // Utilizar StringWriter para capturar el XML
                            File fileToSave = fileChooser.getSelectedFile();
                        
                            // Escribir el documento XML al archivo seleccionado
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                            DOMSource source = new DOMSource(doc);
                         
                            transformer.transform(source, result);
                           // strXML = reemplazarCaracteresEspeciales(strXML);
                            strXML = stringWriter.toString();   
                            
                            // Guardar el XML con caracteres reemplazados en el archivo
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                                writer.write(strXML);  // Escribe el XML reemplazado en el archivo
                            }
                            
//                            StreamResult fileResult = new StreamResult(fileToSave);
//                            transformer.transform(source, fileResult);            
                            
                            //actualizo estado y guardo xml
                            if (strXML.length() > 0 ){
                                strSQL="";
                                strSQL=" update Tbm_cabdecreldep set st_xmlGen='S',tx_xmlSRI='" + strXML  + "' where co_ejeFis= " + intAño + ";\n" +
                                        " update Tbh_cabdecreldep set st_xmlGen='S',tx_xmlSRI='" + strXML  + "' where co_ejeFis= " + intAño + ";\n";
                                if(strSQL.length()>0){
                                    if(!insertarCadena(con)){
                                        blnRes=false;
                                    }
                                } 
                            
                            }
                         
                            System.out.println("Archivo XML generado correctamente en: " + fileToSave.getAbsolutePath());
                        } else {
                            System.out.println("Operación cancelada por el usuario.");
                        }
                    }
                }   
                stm.close();
                stm=null;
                stm1.close();
                stm1=null;
                rst.close();
                rst=null;
                stm2.close();
                stm2=null;
                rst2.close();
                rst2=null;
                bntGenXML.setVisible(false);
                bntGuardar.setVisible(false);


  
            } catch (Exception e) {
                blnRes=false;
            }
            return blnRes;
    }
    
    
    public static String reemplazarCaracteresEspeciales(String contenido) {
        // Mapa de reemplazos
        Map<String, String> reemplazos = new HashMap<>();
        reemplazos.put("á", "a");
        reemplazos.put("é", "e");
        reemplazos.put("í", "i");
        reemplazos.put("ó", "o");
        reemplazos.put("ú", "u");
        reemplazos.put("ñ", "n");
        reemplazos.put("Á", "A");
        reemplazos.put("É", "E");
        reemplazos.put("Í", "I");
        reemplazos.put("Ó", "O");
        reemplazos.put("Ú", "U");
        reemplazos.put("Ñ", "N");

        // Reemplazar los caracteres en el contenido
        for (Map.Entry<String, String> entry : reemplazos.entrySet()) {
            contenido = contenido.replace(entry.getKey(), entry.getValue());
        }

        return contenido;
    }
  
    private static Element createElement(Document doc, String tagName, String value) {
        // Crear el elemento
        Element element = doc.createElement(tagName);
        
        // Crear el nodo de texto
        Text textNode = doc.createTextNode(value);
        
        // Agregar el texto al elemento
        element.appendChild(textNode);
        
        return element;
    }
    
    private void bntGenXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGenXMLActionPerformed
        lblMsgSis.setText("Generando XML");
        pgrSis.setIndeterminate(true);
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
             if(GeneraXML()){  
                 mostrarMsgInf("<HTML>El XML </FONT> se generó correctamente.</HTML>");
                 pgrSis.setIndeterminate(false);
                 lblMsgSis.setText("XML se ha Generado y guardado correctamente");
             }
            else{
                mostrarMsgInf("<HTML>Ocurrió un error al generar en xml </FONT>.<BR>.Verifique y vuelva a intentarlo.</HTML>");
             }
        } 
    }//GEN-LAST:event_bntGenXMLActionPerformed

    private void butPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPerActionPerformed

    }//GEN-LAST:event_butPerActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void bntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGuardarActionPerformed

        // TODO add your handling code here:
       try {
          //  con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
              con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

           if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0)
                {
                    if (con != null)
                    {
                        if (GuardarRegistros(con ))
                        {
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            
                        }
                        else
                        {
                            mostrarMsgInf("Error al guardar.");
                        }
                        con = null;
                    }
                }
        } catch (Exception e) {
         }
  
         
    }//GEN-LAST:event_bntGuardarActionPerformed

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }
    
    private boolean GuardarRegistros(Connection con){
        boolean blnRes=true;   
        int tipomov=0,co_reg=1; 
        strSQL="";
        intAño=0;
        intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());      
            
        ///elimina datos para volverlos a guardar
        strSQL+=   " delete from Tbm_detdecreldep where  co_ejeFis="+intAño +  ";\n" +
                " update Tbm_cabdecreldep set st_datgua ='S' where co_ejeFis="+intAño + ";\n" ;
               
        //strSqlIns  += "insert into Tbm_cabdecreldep (co_ejeFis,tx_xmlSRI,st_xmlGen,st_datGua,st_reg,fe_ing,co_usring) values("+ intAño + ",'','N','N','A',getdate(),"+ objParSis.getCodigoUsuario() + "); \n ";

        for (int v=0;v<objTblMod.getRowCountTrue();v++){
             Object objbengal = objTblMod.getValueAt(v, INT_TBL_DAT_BENGAL);
            String tx_bengal = (objbengal != null) ? objbengal.toString() : ""; 
            
            Object objTRACARENFCAT = objTblMod.getValueAt(v, INT_TBL_DAT_TRACARENFCAT);
            String tx_TRACARENFCAT = (objTRACARENFCAT != null) ? objTRACARENFCAT.toString() : ""; 
            
            Object objNUMCARFAM = objTblMod.getValueAt(v, INT_TBL_DAT_NUMCARFAM);
            Object objTIPIDETRA = objTblMod.getValueAt(v, INT_TBL_DAT_TIPIDETRA);
            Object objIDENTTRA = objTblMod.getValueAt(v, INT_TBL_DAT_IDENTTRA);
            Object objAPETRA = objTblMod.getValueAt(v, INT_TBL_DAT_APETRA);
            objAPETRA = reemplazarCaracteresEspeciales(objAPETRA.toString());

            Object objNOMTRA = objTblMod.getValueAt(v, INT_TBL_DAT_NOMTRA);
            objNOMTRA = reemplazarCaracteresEspeciales(objNOMTRA.toString());
            
            Object objCODESTTRA = objTblMod.getValueAt(v, INT_TBL_DAT_CODESTTRA);
            Object objTIPRESTRA = objTblMod.getValueAt(v, INT_TBL_DAT_TIPRESTRA);
            Object objPAIRESTRA = objTblMod.getValueAt(v, INT_TBL_DAT_PAIRESTRA);
            Object objAPLCONDOBIMP = objTblMod.getValueAt(v, INT_TBL_DAT_APLCONDOBIMP);
            Object objCONTRARESDISC = objTblMod.getValueAt(v, INT_TBL_DAT_CONTRARESDISC);
            
            Object objPORDISC = objTblMod.getValueAt(v, INT_TBL_DAT_PORDISC);
            Object objTIPIDEDISC = objTblMod.getValueAt(v, INT_TBL_DAT_TIPIDEDISC);
            Object objIDEDISC = objTblMod.getValueAt(v, INT_TBL_DAT_IDEDISC);
            
            Object objSUELTRA = objTblMod.getValueAt(v, INT_TBL_DAT_SUELTRA);
            String nd_SUELTRA = (objSUELTRA != null) ? objSUELTRA.toString() :""; 
            
            Object objOTRINGGRA = objTblMod.getValueAt(v, INT_TBL_DAT_OTRINGGRA);
            Object objPARTUTIL = objTblMod.getValueAt(v, INT_TBL_DAT_PARTUTIL);
            Object objOTRINGGRAGENOTREMP = objTblMod.getValueAt(v, INT_TBL_DAT_OTRINGGRAGENOTREMP);
            Object objIMPRENASUEMP = objTblMod.getValueAt(v, INT_TBL_DAT_IMPRENASUEMP);
            Object objDECTERCERO = objTblMod.getValueAt(v, INT_TBL_DAT_DECTERCERO);
            Object objDECCUARTO = objTblMod.getValueAt(v, INT_TBL_DAT_DECCUARTO);
            
            Object objFONRESERVA = objTblMod.getValueAt(v, INT_TBL_DAT_FONRESERVA);
            String nd_FONRESERVA = (objFONRESERVA != null) ? objFONRESERVA.toString() :""; 

            Object objINGGRAEMP = objTblMod.getValueAt(v, INT_TBL_DAT_INGGRAEMP);
            String nd_INGGRAEMP = (objINGGRAEMP != null) ? objINGGRAEMP.toString() :""; 

            Object objAPOPEREMP = objTblMod.getValueAt(v, INT_TBL_DAT_APOPEREMP);
            String nd_APOPEREMP = (objAPOPEREMP != null) ? objAPOPEREMP.toString() :""; 
            double Truncadototalapopers = Math.floor(Double.parseDouble(nd_APOPEREMP) * 100) / 100;


            Object objGASVIVTRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASVIVTRA);
            String nd_GASVIVTRA  = (objGASVIVTRA != null) ? objGASVIVTRA.toString() :""; 

            Object objGASEDUTRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASEDUTRA);
            String nd_GASEDUTRA = (objGASEDUTRA != null) ? objGASEDUTRA.toString() :""; 

            Object objGASSALUDTRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASSALUDTRA);
            String nd_GASSALUDTRA = (objGASSALUDTRA != null) ? objGASSALUDTRA.toString() :""; 

            Object objGASVESTRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASVESTRA);           
            String nd_GASVESTRA = (objGASVESTRA != null) ? objGASVESTRA.toString() :""; 

            Object objGASALITRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASALITRA);
            String nd_GASALITRA = (objGASALITRA != null) ? objGASALITRA.toString() :""; 

            Object objGASTURTRA = objTblMod.getValueAt(v, INT_TBL_DAT_GASTURTRA);   
            String nd_GASTURTRA = (objGASTURTRA != null) ? objGASTURTRA.toString() :""; 

            Object objBASIMPGRA = objTblMod.getValueAt(v, INT_TBL_DAT_BASIMPGRA);   
            String nd_BASIMPGRA = (objBASIMPGRA != null) ? objBASIMPGRA.toString() :""; 

            Object objIMPRENCAUS = objTblMod.getValueAt(v, INT_TBL_DAT_IMPRENCAUS);  
            String nd_IMPRENCAUS = (objIMPRENCAUS != null) ? objIMPRENCAUS.toString() :""; 

            Object objREBGASTPER = objTblMod.getValueAt(v, INT_TBL_DAT_REBGASTPER);  
            String nd_REBGASTPER = (objREBGASTPER != null) ? objREBGASTPER.toString() :""; 

            Object objIMPRENDESREB = objTblMod.getValueAt(v, INT_TBL_DAT_IMPRENDESREB);  
            String nd_IMPRENDESREB = (objIMPRENDESREB != null) ? objIMPRENDESREB.toString() :""; 
            
            Object objRENASUTRA = objTblMod.getValueAt(v, INT_TBL_DAT_RENASUTRA);  
            String nd_RENASUTRA = (objRENASUTRA != null) ? objRENASUTRA.toString() :""; 

            
            strSQL  += "Insert into Tbm_detdecreldep values ("+ intAño + ",'" +tx_bengal +  "','"+ objTRACARENFCAT + "'," + objNUMCARFAM + ",'" + objTIPIDETRA + "','" + 
                                      objIDENTTRA + "','" + objAPETRA + "','" + objNOMTRA + "','" + objCODESTTRA + "','" +  objTIPRESTRA + "'," + objPAIRESTRA + ",'" + 
                                      objAPLCONDOBIMP + "','" + objCONTRARESDISC + "'," + objPORDISC + ",'" + objTIPIDEDISC + "','" +  objIDEDISC + "'," + 
                                      objUti.redondear(Double.parseDouble(nd_SUELTRA) , objParSis.getDecimalesMostrar()) + "," +  objOTRINGGRA + "," + objPARTUTIL + ",0,0," +  objDECTERCERO + "," + objDECCUARTO + "," + objUti.redondear(Double.parseDouble(nd_FONRESERVA) , objParSis.getDecimalesMostrar()) + ",0,0," + objUti.redondear(Double.parseDouble(nd_INGGRAEMP) , objParSis.getDecimalesMostrar()) + ",1," + 
                                      Truncadototalapopers + ",0," + Double.parseDouble(nd_GASVIVTRA) + "," + Double.parseDouble(nd_GASEDUTRA) + "," + Double.parseDouble(nd_GASSALUDTRA) + "," +  Double.parseDouble(nd_GASVESTRA) + "," + Double.parseDouble(nd_GASALITRA) + "," + Double.parseDouble(nd_GASTURTRA) + ",0,0," +
                                      objUti.redondear(Double.parseDouble(nd_BASIMPGRA) , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(Double.parseDouble(nd_IMPRENCAUS) , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(Double.parseDouble(nd_REBGASTPER) , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(Double.parseDouble(nd_IMPRENDESREB) , objParSis.getDecimalesMostrar()) + ",0,0," + objUti.redondear(Double.parseDouble(nd_RENASUTRA) , objParSis.getDecimalesMostrar()) + ",'A',getdate()," + objParSis.getCodigoUsuario() + 
                                      "); \n";
            
            
            // int coCta =;  
        co_reg++;
        }
            if(strSQL.length()>0){
                if(!insertarCadena(con)){
                     blnRes=false;
                }
            }  
            bntGenXML.setVisible(true);

        return blnRes;
    }
    
    private boolean ejecutaUpdate(java.sql.Connection conExt){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmLoc.executeUpdate(strSQL);
                blnRes=true;
                strSQL="";
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
                strSQL="";
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
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton bntGenXML;
    private javax.swing.JButton bntGuardar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JComboBox<String> cboPerAAAA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCom;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTab;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ZafThreadGUI extends Thread {

        public void run() {
            if (cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDatos.getRowCount() > 0) {
                tblDatos.setRowSelectionInterval(0, 0);
                tblDatos.requestFocus();
            }
            objThrGUI = null;
        }
    }

 
    /**
     * Configurar el formulario.
     */
 
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            strAux = objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_BENGAL,"Ben.Gal");
            vecCab.add(INT_TBL_DAT_TRACARENFCAT,"Trab.Car.EnfCatast");
            vecCab.add(INT_TBL_DAT_NUMCARFAM,"Carg.Fam");
            vecCab.add(INT_TBL_DAT_TIPIDETRA,"Tip.Ident");
            vecCab.add(INT_TBL_DAT_IDENTTRA,"Num.Ident");
            vecCab.add(INT_TBL_DAT_APETRA,"Apellido");
            vecCab.add(INT_TBL_DAT_NOMTRA,"Nombre");
            vecCab.add(INT_TBL_DAT_CODESTTRA,"cod.Estab");
            vecCab.add(INT_TBL_DAT_TIPRESTRA,"Tip.Resid");
            vecCab.add(INT_TBL_DAT_PAIRESTRA,"PaisResid");
            vecCab.add(INT_TBL_DAT_APLCONDOBIMP,"AplicaConvDobImp");
            
            vecCab.add(INT_TBL_DAT_CONTRARESDISC,"Cond.RespDisc");
            vecCab.add(INT_TBL_DAT_PORDISC,"%Disc");
            vecCab.add(INT_TBL_DAT_TIPIDEDISC,"Tip.Disc");
            vecCab.add(INT_TBL_DAT_IDEDISC,"Num.Iden.Disc");
            vecCab.add(INT_TBL_DAT_SUELTRA,"Sueldo");
            vecCab.add(INT_TBL_DAT_OTRINGGRA,"Otr.Ing.Grav");
            vecCab.add(INT_TBL_DAT_PARTUTIL,"Utilidad");
            vecCab.add(INT_TBL_DAT_OTRINGGRAGENOTREMP,"Otr.Ing.Grav.Gen.Otr.Emp");
            vecCab.add(INT_TBL_DAT_IMPRENASUEMP,"Imp.Rent.Asu");
            vecCab.add(INT_TBL_DAT_DECTERCERO,"Dec.Tercero");
            vecCab.add(INT_TBL_DAT_DECCUARTO,"Dec.Cuarto");
            vecCab.add(INT_TBL_DAT_FONRESERVA,"Fond.Reserv");
            vecCab.add(INT_TBL_DAT_COMECOSALDIG,"Comp.Sal.Digno");
            vecCab.add(INT_TBL_DAT_OTRINGRELDEP,"Otr.Ing.RelDep");
            vecCab.add(INT_TBL_DAT_INGGRAEMP,"Ing.Grav");
            vecCab.add(INT_TBL_DAT_TIPSALNETO,"Tip.SalNeto");
            vecCab.add(INT_TBL_DAT_APOPEREMP,"Apo.Personal");
            vecCab.add(INT_TBL_DAT_APOPEROTREMP,"Apo.Per.ConOtrEmp");
            vecCab.add(INT_TBL_DAT_GASVIVTRA,"Gast.Vivienda");
            vecCab.add(INT_TBL_DAT_GASEDUTRA,"Gast.Educacion");
            vecCab.add(INT_TBL_DAT_GASSALUDTRA,"Gast.Salud");            
            vecCab.add(INT_TBL_DAT_GASVESTRA,"Gast.Vestimenta");
            vecCab.add(INT_TBL_DAT_GASALITRA,"Gast.Alimentacion");
            vecCab.add(INT_TBL_DAT_GASTURTRA,"Gast.Turismo");
            vecCab.add(INT_TBL_DAT_EXODISC,"Exo.Disc");
            vecCab.add(INT_TBL_DAT_EXOTEREDAD,"Exo.Ter.Edad");
            vecCab.add(INT_TBL_DAT_BASIMPGRA,"BaseImpGrav");
            vecCab.add(INT_TBL_DAT_IMPRENCAUS,"ImpRenCausado");
            vecCab.add(INT_TBL_DAT_REBGASTPER,"RebajaGast.Per");
            vecCab.add(INT_TBL_DAT_IMPRENDESREB,"ImpRentDesRebaja");
            vecCab.add(INT_TBL_DAT_RENTERASUOTREMPL,"RentRetOtrEmp");           
            vecCab.add(INT_TBL_DAT_RENASUEMP,"RentRetEmp");
            vecCab.add(INT_TBL_DAT_RENASUTRA,"RentRetTra");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatos.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selecci�n.
            tblDatos.setRowSelectionAllowed(true);
            tblDatos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatos);
            objColNum = new ZafColNumerada(tblDatos, INT_TBL_DAT_LIN);
            tblDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Tamano de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDatos.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);            
            tcmAux.getColumn(INT_TBL_DAT_BENGAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRACARENFCAT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMCARFAM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TIPIDETRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IDENTTRA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_APETRA).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_NOMTRA).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CODESTTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TIPRESTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PAIRESTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_APLCONDOBIMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CONTRARESDISC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PORDISC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIPIDEDISC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_IDEDISC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_SUELTRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_OTRINGGRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PARTUTIL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_OTRINGGRAGENOTREMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IMPRENASUEMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DECTERCERO).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DECCUARTO).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_FONRESERVA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COMECOSALDIG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_OTRINGRELDEP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_INGGRAEMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIPSALNETO).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_APOPEREMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_APOPEROTREMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_GASVIVTRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_GASEDUTRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_GASSALUDTRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_GASVESTRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_GASALITRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_GASTURTRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_EXODISC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EXOTEREDAD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BASIMPGRA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IMPRENCAUS).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_REBGASTPER).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IMPRENDESREB).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_RENTERASUOTREMPL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_RENASUEMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_RENASUTRA).setPreferredWidth(90);
            
            tblDatos.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDatos);

            objMouMotAda = new ZafMouMotAda();
            tblDatos.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDatos);

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector(); 
            vecAux.add("" + INT_TBL_DAT_SUELTRA); 
            vecAux.add("" + INT_TBL_DAT_OTRINGGRA);  
            vecAux.add("" + INT_TBL_DAT_PARTUTIL); 
            vecAux.add("" + INT_TBL_DAT_OTRINGGRAGENOTREMP); 
            vecAux.add("" + INT_TBL_DAT_IMPRENASUEMP); 
            vecAux.add("" + INT_TBL_DAT_DECTERCERO);
            vecAux.add("" + INT_TBL_DAT_DECCUARTO);
            vecAux.add("" + INT_TBL_DAT_FONRESERVA); 
            vecAux.add("" + INT_TBL_DAT_COMECOSALDIG); 
            vecAux.add("" + INT_TBL_DAT_OTRINGRELDEP); 
            vecAux.add("" + INT_TBL_DAT_INGGRAEMP);             
            vecAux.add("" + INT_TBL_DAT_APOPEREMP); 
            vecAux.add("" + INT_TBL_DAT_APOPEROTREMP);             
            vecAux.add("" + INT_TBL_DAT_GASVIVTRA); 
            vecAux.add("" + INT_TBL_DAT_GASEDUTRA); 
            vecAux.add("" + INT_TBL_DAT_GASSALUDTRA); 
            vecAux.add("" + INT_TBL_DAT_GASVESTRA); 
            vecAux.add("" + INT_TBL_DAT_GASALITRA); 
            vecAux.add("" + INT_TBL_DAT_GASTURTRA); 
            vecAux.add("" + INT_TBL_DAT_BASIMPGRA); 
            vecAux.add("" + INT_TBL_DAT_IMPRENCAUS); 
            vecAux.add("" + INT_TBL_DAT_REBGASTPER); 
            vecAux.add("" + INT_TBL_DAT_IMPRENDESREB); 
            vecAux.add("" + INT_TBL_DAT_RENASUTRA); 
 
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
  
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_TBL_DAT_SUELTRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_OTRINGGRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PARTUTIL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_OTRINGGRAGENOTREMP).setCellRenderer(objTblCelRenLbl);       
            tcmAux.getColumn(INT_TBL_DAT_IMPRENASUEMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_DECTERCERO).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_DECCUARTO).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_FONRESERVA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_COMECOSALDIG).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_OTRINGRELDEP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_INGGRAEMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_APOPEREMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_APOPEROTREMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_GASVIVTRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_GASEDUTRA).setCellRenderer(objTblCelRenLbl);         
            tcmAux.getColumn(INT_TBL_DAT_GASSALUDTRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_GASVESTRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_GASALITRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_GASTURTRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_EXODISC).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_EXOTEREDAD).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_BASIMPGRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_IMPRENCAUS).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_REBGASTPER).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_IMPRENDESREB).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_RENTERASUOTREMPL).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_RENASUEMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_RENASUTRA).setCellRenderer(objTblCelRenLbl);     
            
            objTblCelRenLbl = null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

    public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDatos.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;

//                case INT_TBL_DAT_CHKEXCCARAUT:
//                    strMsg = "Excel cargado automaticamente";
//                    break;
               
                default:
                    strMsg = "";
                    break;
            }
            tblDatos.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean cargarReg() {
        boolean blnRes = true;
        try {
          //  con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
              con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null) {
                if (cargarDetReg()) {
                }else{
                    mostrarMsgInf("No hay datos en el periodo seleccionado.");
                }
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }
 
    private boolean cargarDetReg() {
        boolean blnRes = true;
        int i;
        String strSqlCons="",strSql2="",strSql3="",strSql4="";
        int intNumReg = 0;
        double totalingres =0.00,totalapopers =0.00,totalgastper =0.00,nd_impdesrebgasper =0.00,nd_INGGRAEMP=0.00; 
        double nd_frabasCom =0.00;
        double nd_baseimp =0.00, nd_imprencau =0.00,nd_rebgasper =0.00,nd_impretpagtra=0.00;        

        btnStGXmlGenerado=false;
        btnDatosGuard=false;
        intAño=0;
        strSqlInsHis="";
        try {   
            pgrSis.setIndeterminate(true); 
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());      
            
            if (con != null) {
                stm = con.createStatement();
                stm1 = con.createStatement();
                stm2 = con.createStatement();
                stm3 = con.createStatement();
                stm5 = con.createStatement();

                //elimina registros de la historica (datos cargados automaticamente sin modificacion manual)
                strSql2="";
                strSql2=" select * from Tbm_cabdecreldep  where co_ejeFis=" +intAño ;
                rst2  = stm2.executeQuery(strSql2);
                 if (rst2.next()) { 
                    if ( rst2.getString("st_xmlGen").equals("N") &&  rst2.getString("st_datGua").equals("N")){
                        strSql3="";
                        strSql3="delete from Tbh_cabdecreldep  where co_ejeFis="+intAño + ";\n" +
                                " delete from Tbh_detdecreldep  where co_ejeFis="+intAño + ";\n" +    
                                " delete from Tbm_cabdecreldep  where co_ejeFis="+intAño + ";\n" ;  

                        stm3.executeUpdate(strSql3);  
                                    
                    }       
                    if (  rst2.getString("st_datGua").equals("S")){   
                        bntGenXML.setVisible(true);
                        btnDatosGuard=true;
                    }
                    if (  rst2.getString("st_xmlGen").equals("S")){   
                        bntGenXML.setVisible(false);
                        bntGuardar.setVisible(false);      
                        btnDatosGuard=true;
                    }
                 }
                  stm3.close();  
                  stm3=null;  
                  stm2.close();  
                  stm2=null;  
                  rst2.close();
                  rst2=null;
                  
                if (!btnDatosGuard){
                    
                        strSQL = "";   
                        strSQL += "SELECT  a.codigo ,a.cod_employee,\n" +
                                    " (CASE B.COD_PROV WHEN 420 THEN 'SI' ELSE 'NO' END ) BENEFICIARIO_GALAPAGOS,\n" +
                                    " 'NO' TRAB_CARG_DISC_CATAS,\n" +
                                    " ( SELECT count(*) nd_carfam FROM Dynamics2021.dbo.tbm_contra y\n" +
                                    "inner join Dynamics2021.dbo.tbm_TipFamCon z  on y.co_tipfamcon=z.co_tipfamcon\n" +
                                    "where co_tra=a.cod_employee and z.tx_tipcarfam <> 'N'  ) CARGAS_FAM,\n" +
                                    " (CASE B.TIPO_DOCUMENTO WHEN 'IDENTIFICATION' THEN 'C' WHEN 'PASSPORT' THEN 'P' WHEN 'IDENTIFICACION TRIBUTARIA EXTERIOR' THEN 'E' else 'NA' end ) TIPO_IDENTIFICACION,\n" +
                                    " a.cedula,\n" +
                                    " --[172.31.1.6].[gh_tuvalsa].dbo.spyraldecrypt(a.APELLIDO) as apellido, \n" +
                                    " -- [172.31.1.6].[gh_tuvalsa].dbo.spyraldecrypt(a.NOMBRE) as nombre,\n" +
                                    "  trim(c.tx_ape) apellido,\n" +
                                    "  trim(c.tx_nom) nombre,\n" +
                                    " RIGHT(REPLICATE('0', 3) + CAST(  ISNULL(c.tx_codcos,0) AS VARCHAR(3)),3) COD_ESTABLECIMIENTO,\n" +
                                    "(CASE A.COD_PAIS WHEN 'ECU' THEN '01' ELSE '02' END) TIPO_RESIDENCIA,\n" +
                                    "'593' PAIS_RESIDENCIA,\n" +
                                    "'NA' APLICA_CONV_DOBLE_IMPO,\n" +
                                    "--b.TIENE_DISCAPACIDAD,\n" +
                                    " (CASE b.TIENE_DISCAPACIDAD WHEN 'NO' THEN '01' ELSE '02' END ) CONDICION_TRAB_RESP_DISCAPACIDAD,\n" +
                                    " isnull(b.PERCENT_DISCAPACIDAD,0) porcen_discapa,\n" +
                                    " 'N' TIP_IDEN_PER_DISCA,\n" +
                                    "  '999' NUM_IDEN_PER_DISCA,\n" +
                                    "    ISNULL(( select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] S where S.COD_TRANS='OTROS_INGRESOS_GRAVABLES' AND S.COD_PROCESO=1 and S.CODIGO=a.CODIGO AND  S.anio = 2024),0) otringgrav,\n"+ 
                                    "  ISNULL(( select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] S where S.COD_TRANS IN ('COMISION_PAG_QUINC','TOTAL_H_EXTRAS') AND S.COD_PROCESO=1 and S.CODIGO=a.CODIGO AND  S.anio = "+ intAño +"),0) ingresos,\n" +
                                    "  ISNULL(( select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] S where S.COD_TRANS IN ('SUELDO_EMP' ) AND S.COD_PROCESO=1 and S.CODIGO=a.CODIGO AND  S.anio = "+ intAño +"),0) sueldoanual,\n "+
                                    "  ISNULL(( select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] S where S.COD_TRANS='DECIMO_TERCERO' AND S.COD_PROCESO=3 and S.CODIGO=a.CODIGO AND  S.anio = "+ intAño +"),0) decimotercero,\n" +
                                    "  ISNULL((select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] P where P.COD_TRANS='DECIMO_CUARTO'  AND P.COD_PROCESO=4 and P.CODIGO=a.CODIGO AND  P.anio = "+ intAño +"),0)  decimoCuarto,\n" +
                                    "  ISNULL((select SUM(RESULTADO) from [sp].[gh_tuvalsa].[dbo].[ROL_HISTORICO_T2] w where w.COD_TRANS='FONDOS_RESERVA_NOMINA'  AND w.COD_PROCESO=1 and w.CODIGO=a.CODIGO AND  w.anio = "+ intAño +"),0)  FONDORESERVA,\n" +
                                    "  ISNULL((d.nd_valdistra+nd_valdiscar),0)  part_uti, " +
                                    "ISNULL((select  nd_gasvivtra from [dbo].Tbm_forprogasper	f where f.co_tra =a.cod_employee  and  f.co_ejefis = 2024),0)  GastosVivienda,\n" +
                                    "  ISNULL((select nd_gasvestra from [dbo].Tbm_forprogasper	g where g.co_tra =a.cod_employee  and  g.co_ejefis = 2024),0)  GastosVestimenta,\n" +
                                    " ISNULL((select  nd_gasturtra from [dbo].Tbm_forprogasper	h where h.co_tra =a.cod_employee  and  h.co_ejefis = 2024),0)  Gastosturismo,\n" +
                                    " ISNULL((select  nd_gassaltra from [dbo].Tbm_forprogasper	i where i.co_tra =a.cod_employee  and  i.co_ejefis = 2024),0)  Gastossalud,\n" +
                                    " ISNULL((select  nd_gasedutra from [dbo].Tbm_forprogasper	j where j.co_tra =a.cod_employee  and  j.co_ejefis = 2024),0)  Gastoseducacion,\n" +
                                    "  ISNULL((select  nd_gasalitra from [dbo].Tbm_forprogasper	k where k.co_tra =a.cod_employee  and  k.co_ejefis = 2024),0)  Gastosalimentacion\n" +                        
                                    "FROM   [sp].[gh_tuvalsa].dbo.RH_EMPLEADO a\n" +
                                    "inner join   [sp].[gh_tuvalsa].[dbo].[RH_PERSON] b on a.codigo=b.codigo\n" +
                                    "inner join Dynamics2021.dbo.tbm_tra c on /*c.tx_ide COLLATE SQL_Latin1_General_CP1_CI_AS=a.cedula COLLATE SQL_Latin1_General_CP1_CI_AS and*/  c.co_tra=a.cod_employee \n" +
                                    "LEFT JOIN Dynamics2021.dbo.Tbm_paguti d ON d.co_tra =a.cod_employee \n" +
                                     "WHERE EXISTS (\n" +
                                    "    SELECT 1\n" +
                                    "    FROM  [sp].[gh_tuvalsa].dbo.[ROL_SUELDO_EMPL] b\n" +
                                    "    WHERE b.anio = " + intAño +"\n" +
                                    "    AND a.codigo = b.codigo\n" +
                                  ")  \n"+
                               //   ") and a.cod_employee in (934,591) \n" +
                                    "order by  a.COD_EMPLOYEE ASC";


                        rst = stm.executeQuery(strSQL);

                        vecDat.clear();
                        lblMsgSis.setText("Cargando datos...");
                        pgrSis.setMinimum(0);
                        pgrSis.setValue(0);
                        i = 0;
                        String Nombre="",Apellido="";
                        double TotalIngTra = 0.00;

                         if (rst.next()) {
                           vecDat = new Vector();
                           strSqlInsHis += "insert into Tbh_cabdecreldep (co_ejeFis,tx_xmlSRI,st_xmlGen,st_datGua,st_reg,fe_ing,co_usring) values("+ intAño + ",'','N','N','A',getdate(),"+ objParSis.getCodigoUsuario() + "); \n ";
                            strSqlInsHis += "insert into Tbm_cabdecreldep (co_ejeFis,tx_xmlSRI,st_xmlGen,st_datGua,st_reg,fe_ing,co_usring) values("+ intAño + ",'','N','N','A',getdate(),"+ objParSis.getCodigoUsuario() + "); \n ";

                           do {
                                nd_frabasCom=0;
                                nd_impretpagtra=0;
                                nd_baseimp=0;
                                nd_imprencau=0;
                                nd_rebgasper=0;
                                nd_impdesrebgasper=0;
                                TotalIngTra=0;
                                
                                vecReg = new Vector();
                                vecReg.add(INT_TBL_DAT_LIN, "");
                                vecReg.add(INT_TBL_DAT_BENGAL, rst.getString("BENEFICIARIO_GALAPAGOS"));
                                vecReg.add(INT_TBL_DAT_TRACARENFCAT, rst.getString("TRAB_CARG_DISC_CATAS"));
                                vecReg.add(INT_TBL_DAT_NUMCARFAM, rst.getInt("CARGAS_FAM"));
                                vecReg.add(INT_TBL_DAT_TIPIDETRA, rst.getString("TIPO_IDENTIFICACION"));
                                vecReg.add(INT_TBL_DAT_IDENTTRA, rst.getString("cedula"));
                                
                                if (rst.getString("nombre").endsWith(" ")) {
                                    Nombre = rst.getString("nombre").substring(0, rst.getString("nombre").length() - 1);
                                }else{
                                    Nombre = rst.getString("nombre");
                                }
                                if (rst.getString("apellido").endsWith(" ")) {
                                    Apellido = rst.getString("apellido").substring(0, rst.getString("apellido").length() - 1);
                                }else{
                                    Apellido = rst.getString("apellido");
                                }
                                vecReg.add(INT_TBL_DAT_APETRA, Apellido);
                                vecReg.add(INT_TBL_DAT_NOMTRA,Nombre);
                                vecReg.add(INT_TBL_DAT_CODESTTRA, rst.getString("COD_ESTABLECIMIENTO"));
                                vecReg.add(INT_TBL_DAT_TIPRESTRA, rst.getString("TIPO_RESIDENCIA"));
                                vecReg.add(INT_TBL_DAT_PAIRESTRA, rst.getString("PAIS_RESIDENCIA"));
                                vecReg.add(INT_TBL_DAT_APLCONDOBIMP, rst.getString("APLICA_CONV_DOBLE_IMPO"));
                                vecReg.add(INT_TBL_DAT_CONTRARESDISC, rst.getString("CONDICION_TRAB_RESP_DISCAPACIDAD"));
                                vecReg.add(INT_TBL_DAT_PORDISC, rst.getString("porcen_discapa"));
                                vecReg.add(INT_TBL_DAT_TIPIDEDISC, rst.getString("TIP_IDEN_PER_DISCA"));
                                vecReg.add(INT_TBL_DAT_IDEDISC, rst.getString("NUM_IDEN_PER_DISCA"));
                                 //sumar todos los ingresos del empleado 
                                totalingres= rst.getDouble("ingresos") + rst.getDouble("otringgrav") + rst.getDouble("sueldoanual") ;
                                vecReg.add(INT_TBL_DAT_SUELTRA, totalingres);
                                vecReg.add(INT_TBL_DAT_OTRINGGRA,  0);  
                                vecReg.add(INT_TBL_DAT_PARTUTIL, rst.getDouble("part_uti"));
                                vecReg.add(INT_TBL_DAT_OTRINGGRAGENOTREMP,0);
                                vecReg.add(INT_TBL_DAT_IMPRENASUEMP,0);
                                vecReg.add(INT_TBL_DAT_DECTERCERO, rst.getDouble("decimotercero"));
                                vecReg.add(INT_TBL_DAT_DECCUARTO, rst.getDouble("decimoCuarto"));
                                vecReg.add(INT_TBL_DAT_FONRESERVA, rst.getDouble("FONDORESERVA"));
                                vecReg.add(INT_TBL_DAT_COMECOSALDIG,0);
                                vecReg.add(INT_TBL_DAT_OTRINGRELDEP,0);
                                nd_INGGRAEMP= totalingres +  rst.getDouble("part_uti");
                                vecReg.add(INT_TBL_DAT_INGGRAEMP, nd_INGGRAEMP);
                                vecReg.add(INT_TBL_DAT_TIPSALNETO, 1);

                                //calcular el aporte personal suelo*9.45/100
                               //TotalIngTra= (nd_INGGRAEMP + rst.getDouble("sueldoanual"));
                                totalapopers= ( totalingres * 0.0945)  ;
                               // double Truncadototalapopers = Math.floor(totalapopers * 100) / 100;
                                vecReg.add(INT_TBL_DAT_APOPEREMP,totalapopers);// Truncadototalapopers);

                                vecReg.add(INT_TBL_DAT_APOPEROTREMP, 0);
                                vecReg.add(INT_TBL_DAT_GASVIVTRA, rst.getDouble("GastosVivienda"));
                                vecReg.add(INT_TBL_DAT_GASEDUTRA, rst.getDouble("Gastoseducacion")); 
                                vecReg.add(INT_TBL_DAT_GASSALUDTRA, rst.getDouble("Gastossalud"));
                                vecReg.add(INT_TBL_DAT_GASVESTRA, rst.getDouble("GastosVestimenta"));
                                vecReg.add(INT_TBL_DAT_GASALITRA, rst.getDouble("Gastosalimentacion"));
                                vecReg.add(INT_TBL_DAT_GASTURTRA, rst.getDouble("Gastosturismo"));

                                totalgastper= (rst.getDouble("GastosVivienda") + rst.getDouble("Gastosalimentacion") +  rst.getDouble("Gastoseducacion") + rst.getDouble("GastosVestimenta") + rst.getDouble("Gastossalud") +  rst.getDouble("Gastosturismo") );
                                vecReg.add(INT_TBL_DAT_EXODISC, 0);
                                vecReg.add(INT_TBL_DAT_EXOTEREDAD, 0);

                                // uso la clase para calcular los valores 
                              strSqlCons="";
                              strSqlCons+="select  top 1 nd_frabas from tbm_confImpRent where ne_anio=" + intAño + " and nd_frabas > 0; ";
                              rst1 = stm1.executeQuery(strSqlCons);
                              if(rst1.next()){
                                  nd_frabasCom = rst1.getDouble("nd_frabas");   
                                 // if (totalingres >= nd_frabasCom  ){
                                    if (!CalcularImpRentTra(con,totalingres ,rst.getDouble("part_uti"),  objUti.redondear(rst.getDouble("sueldoanual") , objParSis.getDecimalesMostrar()),intAño,rst.getInt("CARGAS_FAM") ,totalgastper,totalapopers )){
                                           blnRes = false;
                                           vecReg.add(INT_TBL_DAT_BASIMPGRA, 0);
                                           vecReg.add(INT_TBL_DAT_IMPRENCAUS,0);
                                           vecReg.add(INT_TBL_DAT_REBGASTPER, 0);
                                           vecReg.add(INT_TBL_DAT_IMPRENDESREB, 0);
                                           vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, 0);
                                           vecReg.add(INT_TBL_DAT_RENASUEMP, 0);
                                           vecReg.add(INT_TBL_DAT_RENASUTRA, 0);

                                    }else{
                                           if (arlImpRenTraDat.size()>= 1){
                                               for(int w=0;w<arlImpRenTraDat.size();w++){     
                                                   nd_baseimp= Double.parseDouble(objUti.getStringValueAt(arlImpRenTraDat, w, INT_ARL_BAS_IMP_GRA)); 
                                                   vecReg.add(INT_TBL_DAT_BASIMPGRA,nd_baseimp);

                                                   nd_imprencau= Double.parseDouble(objUti.getStringValueAt(arlImpRenTraDat, w, INT_ARL_IMP_REN_CAU)); 
                                                   vecReg.add(INT_TBL_DAT_IMPRENCAUS,nd_imprencau);

                                                   nd_rebgasper= Double.parseDouble(objUti.getStringValueAt(arlImpRenTraDat, w, INT_ARL_REB_GAS_PER)); 
                                                   vecReg.add(INT_TBL_DAT_REBGASTPER,nd_rebgasper);

                                                   nd_impdesrebgasper= Double.parseDouble(objUti.getStringValueAt(arlImpRenTraDat, w, INT_ARL_IMP_DES_REB_GAS_PER)); 
                                                   vecReg.add(INT_TBL_DAT_IMPRENDESREB, nd_impdesrebgasper);

                                                   vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, 0);
                                                   vecReg.add(INT_TBL_DAT_RENASUEMP, 0);

                                                   nd_impretpagtra= Double.parseDouble(objUti.getStringValueAt(arlImpRenTraDat, w, INT_ARL_REN_RET_TRA)); 
                                                   vecReg.add(INT_TBL_DAT_RENASUTRA, nd_impretpagtra);
                                               }  
                                            }else{
                                               vecReg.add(INT_TBL_DAT_BASIMPGRA, 0);
                                               vecReg.add(INT_TBL_DAT_IMPRENCAUS,0);
                                               vecReg.add(INT_TBL_DAT_REBGASTPER, 0);
                                               vecReg.add(INT_TBL_DAT_IMPRENDESREB, 0);
                                               vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, 0);
                                               vecReg.add(INT_TBL_DAT_RENASUEMP, 0);
                                               vecReg.add(INT_TBL_DAT_RENASUTRA, 0);
                                            }
                                       } 
//                                  }else{
//                                           vecReg.add(INT_TBL_DAT_BASIMPGRA, 0);
//                                           vecReg.add(INT_TBL_DAT_IMPRENCAUS,0);
//                                           vecReg.add(INT_TBL_DAT_REBGASTPER, 0);
//                                           vecReg.add(INT_TBL_DAT_IMPRENDESREB, 0);
//                                           vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, 0);
//                                           vecReg.add(INT_TBL_DAT_RENASUEMP, 0);
//                                           vecReg.add(INT_TBL_DAT_RENASUTRA, 0);
//                                      }
                              }else{
                                     vecReg.add(INT_TBL_DAT_BASIMPGRA, 0);
                                       vecReg.add(INT_TBL_DAT_IMPRENCAUS,0);
                                       vecReg.add(INT_TBL_DAT_REBGASTPER, 0);
                                       vecReg.add(INT_TBL_DAT_IMPRENDESREB, 0);
                                       vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, 0);
                                       vecReg.add(INT_TBL_DAT_RENASUEMP, 0);
                                       vecReg.add(INT_TBL_DAT_RENASUTRA, 0);
                              }
                              
                             // objUti.redondear(rst.getDouble("FONDORESERVA") , objParSis.getDecimalesMostrar())
                              //armo la cadena para guardar los datos en la historica
                              strSqlInsHis += "Insert into Tbh_detdecreldep values ("+ intAño + ",'" + rst.getString("BENEFICIARIO_GALAPAGOS") +  "','"+ rst.getString("TRAB_CARG_DISC_CATAS") + "'," + rst.getInt("CARGAS_FAM") + ",'" + rst.getString("TIPO_IDENTIFICACION") + "','" + 
                                              rst.getString("cedula") + "','" + rst.getString("apellido") + "','" + rst.getString("nombre") + "','" + rst.getString("COD_ESTABLECIMIENTO") + "','" +  rst.getString("TIPO_RESIDENCIA") + "'," + rst.getString("PAIS_RESIDENCIA")+ ",'" + 
                                              rst.getString("APLICA_CONV_DOBLE_IMPO") + "','" + rst.getString("CONDICION_TRAB_RESP_DISCAPACIDAD") + "'," + rst.getString("porcen_discapa") + ",'" + rst.getString("TIP_IDEN_PER_DISCA") + "','" +  rst.getString("NUM_IDEN_PER_DISCA") + "'," + 
                                              objUti.redondear(totalingres , objParSis.getDecimalesMostrar()) + "," +  rst.getDouble("otringgrav") + "," + rst.getDouble("part_uti") + ",0,0," +  rst.getDouble("decimotercero") + "," + rst.getDouble("decimoCuarto") + "," + objUti.redondear(rst.getDouble("FONDORESERVA") , objParSis.getDecimalesMostrar()) + ",0,0," + objUti.redondear(totalingres , objParSis.getDecimalesMostrar()) + ",1," + 
                                              totalapopers + ",0," + rst.getDouble("GastosVivienda") + "," + rst.getDouble("Gastoseducacion") + "," + rst.getDouble("Gastossalud") + "," +  rst.getDouble("GastosVestimenta") + "," + rst.getDouble("Gastosalimentacion") + "," + rst.getDouble("Gastosturismo") + ",0,0," +
                                              objUti.redondear(nd_baseimp , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(nd_imprencau , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(nd_rebgasper , objParSis.getDecimalesMostrar()) + "," + objUti.redondear(nd_impdesrebgasper , objParSis.getDecimalesMostrar()) + ",0,0," + objUti.redondear(nd_impretpagtra , objParSis.getDecimalesMostrar()) + ",'A',getdate()," + objParSis.getCodigoUsuario() + 
                                              "); \n";
 
                              vecDat.add(vecReg);
                                i++;
                            } while (rst.next());
                        }
                         rst1.close();
                         rst1=null;
                         stm1.close(); 
                         stm1 = null;
                         rst.close();  
                         rst = null;
                         stm.close();  
                         stm = null; 
                         
                         //guardo en la base
                        if(bntGuardarDatosHistoricoConsulta(con,strSqlInsHis)){
                            con.commit();
                            blnRes=true;
                             pgrSis.setValue(i);
                            intNumReg = i;
 
                            //Asignar vectores al modelo.
                            objTblMod.setData(vecDat);
                            tblDatos.setModel(objTblMod);
                            pgrSis.setIndeterminate(false);

                            //pgrSis.setValue(0);
                           // butCon.setText("Consultar");
                           // objTblMod.initRowsState();

                            lblMsgSis.setText("Se encontraron " + intNumReg + " registros.");
                          }else{
                            con.rollback();   
                            blnRes=false;
                            mostrarMsgInf("Error al guardar los valores historicos notifique a sistemas.");
                        }
                        bntGuardar.setVisible(true);   

                }else { 
                    i = 0;
                    strSql4="";
                    strSql4="  select * from Tbm_detdecreldep  where co_ejeFis="+intAño + ";\n";
                    rst3 = stm5.executeQuery(strSql4);
                    
                    vecDat.clear();
                 if (rst3.next()) {        
                     vecDat = new Vector();    
                     do {
                                nd_frabasCom=0;
                                nd_impretpagtra=0;
                                nd_baseimp=0;
                                nd_imprencau=0;
                                nd_rebgasper=0;
                                nd_impdesrebgasper=0;

                                vecReg = new Vector();
                                vecReg.add(INT_TBL_DAT_LIN, "");
                                vecReg.add(INT_TBL_DAT_BENGAL, rst3.getString("tx_bengal"));
                                vecReg.add(INT_TBL_DAT_TRACARENFCAT, rst3.getString("tx_traCarEnfCat"));
                                vecReg.add(INT_TBL_DAT_NUMCARFAM, rst3.getInt("ne_carFam"));
                                vecReg.add(INT_TBL_DAT_TIPIDETRA, rst3.getString("tx_tipIdeTra"));
                                vecReg.add(INT_TBL_DAT_IDENTTRA, rst3.getString("tx_ide"));
                                vecReg.add(INT_TBL_DAT_APETRA, rst3.getString("tx_ape"));
                                vecReg.add(INT_TBL_DAT_NOMTRA, rst3.getString("tx_nom"));
                                vecReg.add(INT_TBL_DAT_CODESTTRA, rst3.getString("tx_codEst"));
                                vecReg.add(INT_TBL_DAT_TIPRESTRA, rst3.getString("tx_tipRes"));
                                vecReg.add(INT_TBL_DAT_PAIRESTRA, rst3.getString("ne_paiRes"));
                                vecReg.add(INT_TBL_DAT_APLCONDOBIMP, rst3.getString("tx_aplConDobImp"));
                                vecReg.add(INT_TBL_DAT_CONTRARESDISC, rst3.getString("tx_conTraResDis"));
                                vecReg.add(INT_TBL_DAT_PORDISC, rst3.getString("ne_porDis"));
                                vecReg.add(INT_TBL_DAT_TIPIDEDISC, rst3.getString("tx_tipIdeDis"));
                                vecReg.add(INT_TBL_DAT_IDEDISC, rst3.getString("ne_ideDis"));
                                vecReg.add(INT_TBL_DAT_SUELTRA,  rst3.getDouble("nd_sueTot"));
                                vecReg.add(INT_TBL_DAT_OTRINGGRA,  rst3.getDouble("nd_otrIngGra"));  
                                vecReg.add(INT_TBL_DAT_PARTUTIL, rst3.getDouble("nd_parUti"));
                                vecReg.add(INT_TBL_DAT_OTRINGGRAGENOTREMP,rst3.getDouble("nd_otrIngGraGenOtrEmp"));
                                vecReg.add(INT_TBL_DAT_IMPRENASUEMP,rst3.getDouble("nd_impRenAsuEmp"));
                                vecReg.add(INT_TBL_DAT_DECTERCERO, rst3.getDouble("nd_decTer"));
                                vecReg.add(INT_TBL_DAT_DECCUARTO, rst3.getDouble("nd_decCua"));
                                vecReg.add(INT_TBL_DAT_FONRESERVA, rst3.getDouble("nd_fonRes"));
                                vecReg.add(INT_TBL_DAT_COMECOSALDIG, rst3.getDouble("nd_comEcoSalDig"));
                                vecReg.add(INT_TBL_DAT_OTRINGRELDEP, rst3.getDouble("nd_otrIngRelDep"));
                                vecReg.add(INT_TBL_DAT_INGGRAEMP,  rst3.getDouble("nd_ingGraEmp"));
                                vecReg.add(INT_TBL_DAT_TIPSALNETO,  rst3.getDouble("ne_tipSalNet"));
                                vecReg.add(INT_TBL_DAT_APOPEREMP,  rst3.getDouble("nd_apoPerEmp"));
                                vecReg.add(INT_TBL_DAT_APOPEROTREMP,  rst3.getDouble("nd_apoPerOtrEmp"));
                                vecReg.add(INT_TBL_DAT_GASVIVTRA, rst3.getDouble("nd_gasVivTra"));
                                vecReg.add(INT_TBL_DAT_GASEDUTRA, rst3.getDouble("nd_gasEduTra")); 
                                vecReg.add(INT_TBL_DAT_GASSALUDTRA, rst3.getDouble("nd_gasSalTra"));
                                vecReg.add(INT_TBL_DAT_GASVESTRA, rst3.getDouble("nd_gasVesTra"));
                                vecReg.add(INT_TBL_DAT_GASALITRA, rst3.getDouble("nd_gasAliTra"));
                                vecReg.add(INT_TBL_DAT_GASTURTRA, rst3.getDouble("nd_gasTurTra"));
                                vecReg.add(INT_TBL_DAT_EXODISC, rst3.getDouble("nd_exoDis"));
                                vecReg.add(INT_TBL_DAT_EXOTEREDAD,rst3.getDouble("nd_exoTerEda"));
                                vecReg.add(INT_TBL_DAT_BASIMPGRA, rst3.getDouble("nd_basImpGra"));
                               vecReg.add(INT_TBL_DAT_IMPRENCAUS,rst3.getDouble("nd_impRenCau"));
                               vecReg.add(INT_TBL_DAT_REBGASTPER, rst3.getDouble("nd_rebGasPer"));
                               vecReg.add(INT_TBL_DAT_IMPRENDESREB, rst3.getDouble("nd_impRenDesReb"));
                               vecReg.add(INT_TBL_DAT_RENTERASUOTREMPL, rst3.getDouble("nd_renRetAsuOtrEmp"));
                               vecReg.add(INT_TBL_DAT_RENASUEMP, rst3.getDouble("nd_renAsuEmp"));
                               vecReg.add(INT_TBL_DAT_RENASUTRA, rst3.getDouble("nd_renAsuTra"));
 
                         vecDat.add(vecReg);
                         i++;
                     } while (rst3.next());
                 }  
                 rst3.close();  
                 rst3 = null;
                 stm5.close();      
                 stm5 = null;
                 
                pgrSis.setValue(i);
                intNumReg = i;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatos.setModel(objTblMod);
                pgrSis.setIndeterminate(false);
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
    
    private boolean bntGuardarDatosHistoricoConsulta(Connection con,String Cadena){
              boolean blnRes=true;   
               try{
                   stm4 = con.createStatement();
                   stm4.executeUpdate(Cadena);
                   strSqlInsHis="";
                   stm4.close();
                   stm4=null;
               }catch (java.sql.SQLException e){
                   blnRes=false;
                   System.out.println("ERROR: " + e);
               }
               catch (Exception e) {
                   System.out.println("ERROR: " + e);
                   blnRes=false;
               }
               return blnRes;
    }
    
    private ArrayList arlImpRenTraDat;
    
    private boolean CalcularImpRentTra(Connection con,double otrIngtra, double utirectra, double sueltra, int anio,int carfam, double totgasper , double iess) {
      boolean blnRes = true;    
            
            ZafCalImpRenSRI objgenCalImpRetTra = new ZafCalImpRenSRI(objParSis,javax.swing.JOptionPane.getFrameForComponent(this));            
            if (!objgenCalImpRetTra.CalcularImpRentSRI(con, otrIngtra, utirectra,sueltra,anio,carfam,totgasper,iess)){
                blnRes = false;                
            }else{
                arlImpRenTraDat = objgenCalImpRetTra.arlPasar;                
            } ;
             
            objgenCalImpRetTra = null;
        return blnRes;
    }

    private boolean cargarAnios() {
        boolean blnRes = true;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                RRHHDao dao = new RRHHDao(objUti, objParSis);
                String cadena = dao.conAnioUltimoRolGenerado(con, objParSis.getCodigoEmpresa(), true);
                intUltAni = Integer.valueOf(cadena.split(";")[0]);
                intUltMes = Integer.valueOf(cadena.split(";")[1]);
                intUltPer = Integer.valueOf(cadena.split(";")[2]);
                
                int intArrItems[];
                intArrItems = objParSis.getAniosCreadosSistema();        
                cboPerAAAA.removeAllItems();
                for(int i=0;i<intArrItems.length;i++){
                    cboPerAAAA.addItem(String.valueOf(intArrItems[i]));
                }
                cboPerAAAA.setSelectedIndex(intArrItems.length-1);

            
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
}
