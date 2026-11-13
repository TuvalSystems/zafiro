package Contabilidad.ZafCon65;

import Librerias.ZafAsiDia.ZafAsiDia;
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
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
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
/**
 * Autorización de roles de pago...
 *
 * @author Roberto Flores Guayaquil, 24/09/2013
 */
public class ZafCon65 extends javax.swing.JInternalFrame {

    private final int INT_TBL_DAT_LIN = 0;
    private final int INT_TBL_DAT_CHKEXCCARAUT = 1;
    private final int INT_TBL_DAT_FECEXCCARAUT = 2;
    private final int INT_TBL_DAT_BUTEXCCARAUT = 3;
    
    private final int INT_TBL_DAT_CHKOTRING = 4;
    private final int INT_TBL_DAT_FECOTRING = 5;
    private final int INT_TBL_DAT_BUTOTRING = 6;   
    
    private final int INT_TBL_DAT_CHKOTRDESC = 7;
    private final int INT_TBL_DAT_FECOTRDESC = 8;
    private final int INT_TBL_DAT_BUTOTRDESC = 9;
    
    private final int INT_TBL_DAT_CHKIMPRENT = 10;
    private final int INT_TBL_DAT_FECIMPRENT = 11;
    private final int INT_TBL_DAT_BUTIMPRENT = 12;
    
    private final int INT_TBL_DAT_CHKAUTDIABOR = 13;
    private final int INT_TBL_DAT_FECAUTDIABOR = 14;
    private final int INT_TBL_DAT_BUTAUTDIABOR = 15;
    
    private final int INT_TBL_DAT_CHKDIAGEN = 16;
    private final int INT_TBL_DAT_FECDIAGEN  = 17;
    private final int INT_TBL_DAT_NUMDIAGEN  = 18;
    private final int INT_TBL_DAT_NUMDIAPRO  = 19;

    private ZafAsiDia objAsiDia = null;

    private static final int INT_ARL_COD_CTA=0;
    private static final int INT_ARL_REG_DIA=1; 
    private static final int INT_ARL_MON_ANA=2;
    private static final int INT_ARL_COD_ANA=3; 
    private static final int INT_ARL_TIP_MOV=4;
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafNotCorEle objNotCorEle;                          //Libreria para Notificaciones de Correo. 
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm,stm1,stm2,stm3,stm4,stm5,stm6,stm7,stm8,stm9,stm10  ;
    private ResultSet rst00,rst,rstsum,rstconcta,rstColcta,rstConf,rstDatos,rstCountAna,rstCodana,rstOtrosIngNoGra,rstConf1,rstDatos1,rstCountAna1,rstCodana1,rstDatos2,rstDatos3,rstCountAna2,rstOtrosDESC,rstTra;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;
    private ResultSet rvRst,rvRst1;

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
    public List<DetalleAnalitica> ListAnalitica = new ArrayList<>();

    private String strVersion = "v1.17";
    private String MesError="";

    private boolean blnPas2 = false;
    private boolean blnPas3 = false;
    private boolean blnPas4 = false;

    private ArrayList<String> arrLstAni = null;
    private ArrayList<String> arrLstMes = null;

    private ArrayList<String> arrLstMeses = null;

    private int intNePer = 0;
    private int intAño = 0;
    private int intMes = 0;

    /**Periodo de la ultima quicena generada*/
    private int intUltPer = 0;
    /**Año de la ultima quicena generada*/
    private int intUltAni = 0;
    /**Mes de la ultima quicena generada*/
    private int intUltMes = 0;
    private int intCoTipDoc = 0;

    private String strMes, strperiodo ="";
    private boolean blnRRHHEnvCor = false;
    private boolean blnGerGenEnvCor = false;
    private boolean blnGerAdmEnvCor = false;
    
    private boolean blnIngImpRen = false;
    private boolean blnOtrIngNoGrav = false;  
    private boolean blnOtrIngDes = false;
    private boolean blnDiaBorr = false;
    private boolean blnDiaGen = false;


    List<DetalleDiario> resArmarDatDiario = new ArrayList<>();
    private Librerias.ZafGenAna.ZafGenAna objgenAnalitica;    // Para Obtener la informacion del proveedor

    public ZafCon65(ZafParSis obj) {
        try {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objNotCorEle = new ZafNotCorEle(objParSis);  
            objPerUsr = new ZafPerUsr(objParSis);

            butCon.setVisible(false);
            bntGenAsiento.setVisible(false);
            butCer.setVisible(false);
            bntEliminar.setVisible(false);
            
            if (objPerUsr.isOpcionEnabled(4778)) {
                bntEliminar.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(4779)) {
                butCon.setVisible(true);
            }

            if (objPerUsr.isOpcionEnabled(4780)) {
                bntGenAsiento.setVisible(true);
            }

            if (objPerUsr.isOpcionEnabled(337)) {
                butCer.setVisible(true);
            }

            if (!configurarFrm()) {
                exitForm();
            }
            agregarColTblDat();
            cargarAnios();
            EventoCombo objEvent = new EventoCombo();
            cboPer.addItemListener(objEvent);
            cboPerMes.addItemListener(objEvent);
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
        cboPerMes = new javax.swing.JComboBox<>();
        cboPer = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panTab = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        bntEliminar = new javax.swing.JButton();
        butCon = new javax.swing.JButton();
        bntGenAsiento = new javax.swing.JButton();
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

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.setSelectedIndex(1);
        panCom.add(cboPerMes);
        cboPerMes.setBounds(190, 10, 130, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primera Quincena", "Fin de Mes" }));
        panCom.add(cboPer);
        cboPer.setBounds(330, 10, 140, 20);

        jLabel1.setText("Periodo:");
        panCom.add(jLabel1);
        jLabel1.setBounds(50, 10, 50, 14);

        panRpt.add(panCom, java.awt.BorderLayout.NORTH);

        panTab.setLayout(new java.awt.BorderLayout());

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

        panTab.add(spnDat, java.awt.BorderLayout.CENTER);

        panRpt.add(panTab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(385, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        bntEliminar.setText("Eliminar Registros");
        bntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEliminarActionPerformed(evt);
            }
        });
        panBot.add(bntEliminar);

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        bntGenAsiento.setText("Generar Asientos");
        bntGenAsiento.setToolTipText("Cierra la ventana.");
        bntGenAsiento.setPreferredSize(new java.awt.Dimension(140, 25));
        bntGenAsiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGenAsientoActionPerformed(evt);
            }
        });
        panBot.add(bntGenAsiento);

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

            if (objThrGUI == null && asignacionPeriodo()) {
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
    
    private boolean EliminarRegistros(){
        boolean blnRes=true;
         java.sql.Statement stmLoc,stmLoc1;
        String strSql1="",strSql="";
        
           if (objTblMod.getRowCountTrue() <=0){
            mostrarMsgInf("No hay registros para eliminar consulte un periodo.");
            blnRes=false;
            return blnRes ;
        }  

        try{
            con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            strperiodo="";
            String strMes = String.format("%02d", intMes);
            strperiodo = strMes + intAño;
            
            if(con!=null){   
                stmLoc = con.createStatement();
                stmLoc1 = con.createStatement();

                strSql1="";
                strSql1="select st_gendiarolpag,ne_diagen,st_revdiabor from tbm_seggendiarolpag WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n";
                rst  = stmLoc1.executeQuery(strSql1);
                if (rst.next()) { 
                    if ( rst.getString("st_gendiarolpag").equals("N") &&  rst.getInt("ne_diagen") == 0 && rst.getString("st_revdiabor").equals("N")){
                        strSql="";
                        strSql+=" DELETE FROM tbm_seggendiarolpag WHERE co_per="+strperiodo+" and ne_per=" + intNePer + ";\n"+
                                " DELETE FROM tbh_rolpagexc WHERE co_per="+strperiodo+" and ne_per=" + intNePer + ";\n" +
                                " DELETE FROM tbm_rolpagexc WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_ingimprenrolpag WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_detotringnogra WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_cabotringnogra WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_detotrdes WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_cabotrdes WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";\n"  +
                                " DELETE FROM tbm_diagenbor WHERE co_per=" +strperiodo+" and ne_per=" + intNePer + ";" ;
  
                        stmLoc.executeUpdate(strSql);  
                        stmLoc.close();                    
                        stmLoc1.close();
                        stmLoc1=null; 
                        stmLoc=null; 
                    }else{
                        blnRes=false;    
                     }
                 
                 }
            }
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;        
         }
        return blnRes;
    }

    private boolean GenerarAsiento(Connection con) {
        boolean blnRes = true; 
        java.sql.CallableStatement cal;
        java.sql.ResultSet rstLoc = null;
        String strSql = "",strSqlsp="",strSqldi="",strSqlpro="",TipDia="";   
        resArmarDatDiario = new ArrayList<>();        
        String strMes = String.format("%02d", intMes);
        String strperge = strMes + intAño;
   
        try {
            if (con != null) {
               ////DIARO GENERAL
               
                stm = con.createStatement(); 
                strSql = "";    
                strSql += " select trim(tx_tipdia) tx_tipdia, co_cta, tx_tipmov, sum(nd_mntcta) nd_mntcta from tbm_diagenbor  where ne_per=" + intNePer + " and co_per=" + strperge +" GROUP BY co_cta, TRIM(tx_tipdia),tx_tipmov  order by tx_tipdia, tx_tipMov, co_cta " ;
                //strSql += " select trim(tx_tipdia) tx_tipdia, co_cta, tx_tipmov, nd_mntcta from tbm_diagenbor  where ne_per=" + intNePer + " and co_per=" + strperge  ;
                rst = stm.executeQuery(strSql);
                 if (rst.next()) {
                do{
                    TipDia="";
                 //System.out.println(rst.getString("tx_tipdia"));
                 if (rst.getString("tx_tipdia").equals("F")){
                     TipDia="General";
                     strSqldi +=rst.getString("co_cta")+ ","+ rst.getString("tx_tipmov") + ","+ objUti.redondear(Double.parseDouble(String.valueOf( rst.getString("nd_mntcta"))), objParSis.getDecimalesMostrar()) + "|";                    
                    }
                 if (rst.getString("tx_tipdia").equals("P")){  
                     TipDia="Provision";
                     strSqlpro +=rst.getString("co_cta")+ ","+ rst.getString("tx_tipmov") + ","+ objUti.redondear(Double.parseDouble(String.valueOf( rst.getString("nd_mntcta"))), objParSis.getDecimalesMostrar()) + "|";        
                 } 
                }while(rst.next());
                ListAnalitica = new ArrayList<>();  

                if (intNePer==1){
                    if (!GenerarAsientoGeneral(con,strSqldi,TipDia)){
                        blnRes=false;
                    }                     
                }else{
                    if (ReversoAsientoGeneralQuincena(con)){
                        if (ValidarExisteDimensiones(con)){
                            if (GenerarAsientoGeneral(con,strSqldi,TipDia)){
                                if (!GenerarAsientoProvisional(con,strSqlpro,TipDia)){ 
                                    blnRes=false;
                                } 
                            }else{
                                blnRes=false;
                            } 
                        }else{
                                blnRes=false;
                            } 
                    }else{
                        blnRes=false;
                    }
                }
                }
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
                rstLoc.close();
                rstLoc = null;
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }
    
    private boolean ReversoAsientoGeneralQuincena(Connection con) {
        boolean blnRes = true;    
        java.sql.CallableStatement cal;
        Statement stm,stm1,stmUpd,stmDat8;
        String strSql="",strSql1="",strSql2="",strSql3="",StrCadena="",strSqlErr="";
        String strMes = String.format("%02d", intMes);
        String strperge = strMes + intAño;
         try{
             int co_cta=0,tipmov=0,ne_diario=0,numdia=0 ;
             double mntCta=0;
             stm = con.createStatement(); 
            stm1 = con.createStatement(); 
            stm2= con.createStatement();
            stmDat8= con.createStatement();
            //consulto diario y valores para encerar 
            strSql3="";
            strSql3="select distinct a.co_cta,a.nd_mntcta,a.tx_tipdia,a.tx_tipmov,b.ne_diagen from tbm_diagenbor a\n" +
                    "inner join tbm_seggendiarolpag b on a.co_per=a.co_per and a.ne_per=b.ne_per\n" +
                    "where  b.ne_diagen is not null and a.co_per="+strperge+" and a.ne_per=1" ;
            rvRst = stm.executeQuery(strSql3);
            if (rvRst.next()) {
                do{
                    co_cta= rvRst.getInt("co_cta"); 
                    mntCta=rvRst.getDouble("nd_mntcta");  
                    numdia=rvRst.getInt("ne_diagen"); 
                    if (rvRst.getInt("tx_tipmov")== 0){
                        tipmov=1;
                        StrCadena +=co_cta+ ","+tipmov +","+ objUti.redondear(Double.parseDouble(String.valueOf(mntCta)), objParSis.getDecimalesMostrar()) + "|";                    
                    }
                 if (rvRst.getInt("tx_tipmov")== 1){  
                     tipmov=0;
                      StrCadena +=co_cta+ ","+tipmov +","+ objUti.redondear(Double.parseDouble(String.valueOf(mntCta)), objParSis.getDecimalesMostrar()) + "|";                    
                 } 
                }while(rvRst.next());  
                
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fechaFormateada = fechaActual.format(formateador); 
                
                int num_sec= 0;
                String tx_alias="";
                strSql = "";      
                strSql += "select nd_sec+1 sec,tx_for from [dbo].[tbm_cfglot] where co_lot=3" ;
                rvRst1 = stm1.executeQuery(strSql);
                if (rvRst1.next()) {   
                      num_sec= rvRst1.getInt("sec");   
                      tx_alias= rvRst1.getString("tx_for");          
                }
               //stmsec.close();
                tx_alias= tx_alias+"_"+ num_sec;
                strSql1=""; 
                strSql1="{call dbo.sp_creacionDiario(?, ?,?,?,?,?,?,?)}";
                cal = con.prepareCall(strSql1);
                cal.setString(1,  tx_alias); 
                cal.setString(2,  "Diario #" + numdia + " Reversa General de nomina " + strperge + "/1" ); 
                cal.setString(3,  fechaFormateada);  
                cal.setInt(4,  0); 
                cal.setString(5,  StrCadena); 
                cal.registerOutParameter(6, java.sql.Types.INTEGER);
                cal.registerOutParameter(7, java.sql.Types.INTEGER);
                cal.registerOutParameter(8, java.sql.Types.NVARCHAR);

                cal.execute();                
                int  intCodDiarioG = cal.getInt(7);  
                int  intCodError = cal.getInt(6);
                String  strError = cal.getString(8);
                if(intCodError==1){
                    blnRes=false;
                    intCodDiarioG=0;
                     if (!strError.equals("")){
                            String[] strErrorwords = strError.split(" "); // Dividir la cadena por espacio
                  
                            String codErrorGP = String.join(",", strErrorwords); // Une las palabras con comas
                            strSqlErr = "";    
                            strSqlErr = " select ErrorDesc,SourceProc,ErrorParms from dynamics.dbo.taerrorcode where errorcode in (" + codErrorGP + ")";   
                            rst00 = stmDat8.executeQuery(strSqlErr);
                            if(rst00.next()){
                                MesError+= "Parrametro Error: " + rst00.getString("ErrorParms")+ "/ Error: "+ rst00.getString("ErrorDesc");                                                      
                            }
                             
                        }
                }else{
                    strSql2 = "";  
                    strSql2=" update tbm_seggendiarolpag set ne_diagen="+ intCodDiarioG +" where co_emp=1 and co_per="+ strperge +" and ne_per=" + intNePer + "; \n";
                    strSql2 += "update dbo.[tbm_cfglot] set nd_sec=" +num_sec + " where co_lot=3";
                    stmUpd = con.createStatement();
                    stmUpd.executeUpdate(strSql2);
                }    
            }
            
            } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    return blnRes;
    }
  
    private boolean ValidarExisteDimensiones(Connection con) {
        boolean blnRes = true;   
        int codCta=0,coddim=0,tipcencos=0;
        String strMes = String.format("%02d", intMes);
        String strperge = strMes + intAño;
        Statement stm ,stm1,stm2,stm3,stm4,stm5;
        String TextMensError ="";
        try{ 
            stm= con.createStatement(); 
            stm1= con.createStatement();
            stm2= con.createStatement();
            stm3= con.createStatement();
            stm4= con.createStatement();
            stm5= con.createStatement();
            
            String strSq0="",strSql="",strSq2="",strSq3="",strSq4="",strSq5="",idcencos="",tx_ide="",aaTrxDimCode="";
            strSql = "";  
            strSql += "  select  co_cta ,SUM(nd_mntcta) montocta from tbm_diagenbor where co_per=" + strperge + " and ne_per=" + intNePer + " and tx_tipdia='F' and tx_tipmov=0 group by co_cta" ;
            rstconcta = stm.executeQuery(strSql);
            if (rstconcta.next()) {  
                do{
                    codCta=0;
                     codCta=rstconcta.getInt("co_cta");
                     
                      strSq0 = "";   
                      strSq0 += " select top 1* from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='D' and tx_tipdia='F' and tx_conana='S'" ;
                      rstConf1 = stm1.executeQuery(strSq0);
                      if (rstConf1.next()) {
                          strSq2 = "";  
                          strSq2 += "  select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 (nolock) a \n" +
                                     " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                     " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                     " where a.ACTINDX =" +codCta ;
                          rst  = stm2.executeQuery(strSq2);
                          if (rst.next()) {
                              do{
                                  coddim=0;
                                  aaTrxDimCode="";
                                  coddim= rst.getInt("aaTrxDimID");
                                  strSq3 = ""; 
                                  strSq3 += "  select trim(b.tx_ide) tx_ide,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc  a inner join tbm_tra b on a.co_tra=b.co_tra  where  a.co_per="+strperge+" and a.ne_per="+intNePer ;
                                  rstDatos1 = stm3.executeQuery(strSq3);
                                  if (rstDatos1.next()) {   
                                      do{  
                                          tx_ide="";
                                          tipcencos=0;
                                          idcencos="";
                                          tipcencos=rstDatos1.getInt("tx_tipcos");
                                          idcencos= rstDatos1.getString("tx_cencos");          
                                          tx_ide= rstDatos1.getString("tx_ide");
                                          
                                           if (coddim==1){ 
                                               aaTrxDimCode=idcencos;
                                           }else if (coddim==30){
                                               aaTrxDimCode=tx_ide;
                                           }else if (coddim==23){
                                                //Obtengo el nombre del trabajador para buscar el id de codigo de analitica
                                                strSq5="";
                                                strSq5=" select (tx_ape + ' ' + tx_nom) nombrecompleto from tbm_tra where tx_ide='" + tx_ide + "'";
                                                rstTra = stm3.executeQuery(strSq5);
                                                if (rstTra.next()) {
                                                aaTrxDimCode=rstTra.getString("nombrecompleto");
                                                }
                                           }else{
                                              if (tipcencos==1){
                                                   aaTrxDimCode="ADMINISTRATIVO";
                                               }else  if (tipcencos==2){
                                                   aaTrxDimCode="VENTA";
                                               }else{ 
                                                   aaTrxDimCode="LOGISTICA";
                                               }
                                           }

                                           strSq4="";
                                           strSq4=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                   " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                   " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                           System.out.println(strSq4);
                                           rstCodana1 = stm4.executeQuery(strSq4);
                                           if (!rstCodana1.next()) {   
                                                TextMensError+= TextMensError + "Error falta de crear dimension: " + coddim + " Cedula Empleado: " + aaTrxDimCode + ";\n ";
                                               // mostrarMsgInf("Error falta de crear dimension: " + coddim + " Cedula Empleado: " + aaTrxDimCode );
                                                mostrarMsgInf(TextMensError );
                                                return false; //DESCOMENTAR
                                           }
                                      
                                      }while(rstDatos1.next());
                                  }            
                              }while(rst.next());
                          }
                      }
                     
                } while(rstconcta.next());
            }
//            if (!TextMensError.equals("")){
//                mostrarMsgInf(TextMensError);   
//              return false;
//            } 
            
            stm.close();
            stm1.close();
            stm2.close();
            stm3.close();
            stm4.close();
            
        } catch (java.sql.SQLException Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
        
       
        return blnRes;
        
    }
    
  public static LocalDate obtenerUltimoDiaDelMes(int mes, int año) {
        // Validar el mes
        if(mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        // Obtener el último día del mes
        // Se utiliza el primer día del siguiente mes y se resta un día
        LocalDate primerDiaDelSiguienteMes = LocalDate.of(año, mes, 1).plusMonths(1);
        return primerDiaDelSiguienteMes.minusDays(1);
    }
  
    private boolean GenerarAsientoGeneral(Connection con,String cadena,String TipDia) {
            boolean blnRes = true;  
            java.sql.CallableStatement cal;
            java.sql.ResultSet rstLoc = null;
            int intRegDia=-1; //JM 4/Oct/2024  
            String strSql = "",strSql1="",strSql2="",strSql3="",strSql4="" ,strSql5="" ,strSql6="",strSql7="" ,strSql8="",strSql9="",StringGenAna="",StringGenAnaH="",tx_ide="",strSqlErr="";   
            resArmarDatDiario = new ArrayList<>();        
            String strMes = String.format("%02d", intMes);
            String strperge = strMes + intAño;
            double ctaDebe= 0; 
            double ctaHaber= 0;
            double totcta= 0;     
            int co_tra=0;
            Statement stmsec,stmUpd,stmsel,stmconf,stmdat, stmDat2, stmDat3, stmDat4, stmDat5, stmDat6, stmDat7, stmDat8, stmDat9;
            vecAnali = new Vector();
            vecAnali.clear(); 

           // LocalDate fechaActual = LocalDate.now();
            LocalDate fechaActual = obtenerUltimoDiaDelMes(intMes, intAño); //FECHA DIARIO 
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaFormateada = fechaActual.format(formateador);   
             
            try{

            stmsec = con.createStatement(); 
            stmUpd = con.createStatement(); 
            stmsel= con.createStatement();
            stmconf= con.createStatement();
            stmdat= con.createStatement();
            stmDat2 = con.createStatement();
            stmDat3 = con.createStatement();

            stmDat4 = con.createStatement();
            stmDat5 = con.createStatement();
            stmDat6 = con.createStatement();
            stmDat7 = con.createStatement();
            stmDat8 = con.createStatement();
            stmDat9 = con.createStatement();

            stm5 = con.createStatement();
            stm6 = con.createStatement();
            stm7 = con.createStatement();
            stm8 = con.createStatement();
            stm9 = con.createStatement();
            stm10 = con.createStatement();

                    int num_sec= 0;
                    String tx_alias="";
                    strSql = "";      
                    strSql += "select nd_sec+1 sec,tx_for from [dbo].[tbm_cfglot] where co_lot=3" ;
                    rst = stmsec.executeQuery(strSql);
                    if (rst.next()) {   
                          num_sec= rst.getInt("sec");   
                          tx_alias= rst.getString("tx_for");          
                    }
                   //stmsec.close();
                    tx_alias= tx_alias+"_"+ num_sec;
                    strSql1=""; 
                    strSql1="{call dbo.sp_creacionDiario(?, ?,?,?,?,?,?,?)}";
                    cal = con.prepareCall(strSql1);
                    cal.setString(1,  tx_alias); 
                    cal.setString(2,  "Diario " + TipDia +" de nomina" + strperge + "/"+intNePer ); 
                    cal.setString(3,  fechaFormateada);  
                    cal.setInt(4,  0); //antes 0
                    cal.setString(5,  cadena); 
                    cal.registerOutParameter(6, java.sql.Types.INTEGER);
                    cal.registerOutParameter(7, java.sql.Types.INTEGER);
                    cal.registerOutParameter(8, java.sql.Types.NVARCHAR);

                    cal.execute();
                    //cal.getInt("diario");
                    ArrayList arlDat = new ArrayList();  // Donde necesites iniciar a llenar algo nuevo 
                    int  intCodDiarioG = cal.getInt(7);  
                    int  intCodError = cal.getInt(6);
                   // String codErrorGP = "" ;
                    String  strError = cal.getString(8);
                    if(intCodError==1){
                        blnRes=false;
                        intCodDiarioG=0;
                        if (!strError.equals("")){
                            String[] strErrorwords = strError.split(" "); // Dividir la cadena por espacio
                  
                            String codErrorGP = String.join(",", strErrorwords); // Une las palabras con comas
                            strSqlErr = "";    
                            strSqlErr = " select ErrorDesc,SourceProc,ErrorParms from dynamics.dbo.taerrorcode where errorcode in (" + codErrorGP + ")";   
                            rst00 = stmDat8.executeQuery(strSqlErr);
                            if(rst00.next()){
                                MesError+= "Parrametro Error: " + rst00.getString("ErrorParms").trim()+ "/ Error: "+ rst00.getString("ErrorDesc").trim();                                                      
                            }
                        }                          
                    }else{

                            //actualizo el secuencial 
                             strSql2 = "";  
                             strSql2=" update tbm_seggendiarolpag set st_gendiarolpag='S',fe_gendiarolpag=getdate(),ne_diagen="+ intCodDiarioG +" where co_emp=1 and co_per="+ strperge +" and ne_per=" + intNePer + "; \n";
                             strSql2 += "update dbo.[tbm_cfglot] set nd_sec=" +num_sec + " where co_lot=3";
                             stmUpd = con.createStatement();
                             stmUpd.executeUpdate(strSql2);

                            // stmUpd.close();
                             int  codCta = 0,coddim=0;   
                             double montotcta =0;  
                             double montotctadesg =0,mntOtrIngNOG =0,mntImpRenPro =0,mntOtrDESC=0;

                             int  tipcencos = 0;
                             String  idcencos = "",aaTrxDimCode="";

                             //CREAR LAS ANALITICAS
                             if (intNePer==2){ 
                             //--------------OBTENGO LAS CUENTAS DEL DIARIO GENERAL / DEBE------------------
                             strSql3 = "";      
                             strSql3 += "  select  co_cta ,SUM(nd_mntcta) montocta from tbm_diagenbor where co_per=" + strperge + " and ne_per=" + intNePer + " and tx_tipdia='F' and tx_tipmov=0 group by co_cta" ;
                             rstconcta = stmsel.executeQuery(strSql3);
                             if (rstconcta.next()) {  
                                 do{
                                     codCta=0;
                                     montotcta=0;co_tra=0;
                                     mntOtrIngNOG=0;   
                                     mntOtrDESC=0;

                                     StringGenAna="";
                                     codCta=rstconcta.getInt("co_cta");
                                     montotcta=rstconcta.getDouble("montocta");

                                     ///----------------------CASO DE OTROS INGRESOS NO GRAVABLES--------------------------
                                     strSql8 = "";    
                                     strSql8 = "select * from tbm_detotringnogra where co_cta="+codCta + " and tx_tipmov='D' and ne_per="+intNePer + " and co_per="+ strperge;    
                                     rstOtrosIngNoGra = stm5.executeQuery(strSql8);
                                        if(rstOtrosIngNoGra.next()){
                                            do{
                                                mntOtrIngNOG=rstOtrosIngNoGra.getDouble("nd_mntcta");
                                                co_tra=rstOtrosIngNoGra.getInt("co_tra");

                                           //OBTENGO LAS columnas  DEL DIARIO GENERAL - DEBE y que tengan configurado la analitica                          
                                        strSql4 = "";      
                                        //strSql4 += " select * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='D' and tx_tipdia='F' and tx_conana='S'" ;
                                        //prueba corregir que se duplicó un valor
                                        strSql4 = "SELECT TOP 1 * "
                                        + "FROM tbr_ctacolgendia "
                                        + "WHERE co_cta=" + codCta
                                        + " AND tx_tipmov='D'"
                                        + " AND tx_tipdia='F'"
                                        + " AND tx_conana='S'"
                                        + " AND tx_nomcol='nd_otringra'";
                                        //prueba corregir que se duplicó un valor
                                        rstConf1 = stm6.executeQuery(strSql4);
                                        if (rstConf1.next()) {
                                            //do{   
                                                String colCon ="";

                                                if (rstConf1.getString("tx_conana").equals("S")){
                                                    intRegDia++; 
                                                    strSql5 = ""; 
                                                    strSql5 += "  select  trim(b.tx_ide) tx_ide,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc  a inner join tbm_tra b on a.co_tra=b.co_tra  where  a.co_per="+strperge+" and a.ne_per="+intNePer + " and a.co_tra="  +co_tra ;
                                                    rstDatos1 = stm7.executeQuery(strSql5);
                                                    if (rstDatos1.next()) {                                                
                                                       do{     
                                                           StringGenAna="";
                                                           tx_ide="";
                                                           tipcencos=0;
                                                           idcencos="";
                                                           montotctadesg=0;
                                                           tipcencos=rstDatos1.getInt("tx_tipcos");       
                                                           idcencos= rstDatos1.getString("tx_cencos"); 
                                                           tx_ide= rstDatos1.getString("tx_ide");

                                                           montotctadesg=mntOtrIngNOG;

                                                            //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                           strSql6="";
                                                           strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 (nolock) a \n" +
                                                                   " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                                   " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                                   " where a.ACTINDX = "+codCta; 
                                                           rstCountAna1 = stm8.executeQuery(strSql6);
                                                           if (rstCountAna1.next()) {
                                                                do{
                                                                   coddim=0;
                                                                   coddim=rstCountAna1.getInt("aaTrxDimID");  
                                                                   if (coddim==1){ 
                                                                       aaTrxDimCode=idcencos;
                                                                   }else if (coddim==30){
                                                                       aaTrxDimCode=tx_ide;
                                                                    }else if (coddim==23){
                                                                    //Obtengo el nombre del trabajador para buscar el id de codigo de analitica
                                                                    strSql9="";
                                                                    strSql9=" select (tx_ape + ' ' + tx_nom) nombrecompleto from tbm_tra where tx_ide='" + tx_ide + "'";
                                                                    rstTra = stmDat9.executeQuery(strSql9);
                                                                    if (rstTra.next()) {
                                                                    aaTrxDimCode=rstTra.getString("nombrecompleto");
                                                                    }    
                                                                   }else{
                                                                      if (tipcencos==1){
                                                                           aaTrxDimCode="ADMINISTRATIVO";
                                                                       }else  if (tipcencos==2){
                                                                           aaTrxDimCode="VENTA";
                                                                       }else{ 
                                                                           aaTrxDimCode="LOGISTICA";
                                                                       }
                                                                   }

                                                                   strSql7="";
                                                                   strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                                           " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                           " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) =TRIM('" +aaTrxDimCode+ "')";
                                                                   rstCodana1 = stm9.executeQuery(strSql7);
                                                                   if (rstCodana1.next()) {
                                                                       StringGenAna+=coddim + "-"+ rstCodana1.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                                   }
                                                                }while(rstCountAna1.next());
                                                           }
                                                           if (StringGenAna.length() >0 ){ 
                                                               ArrayList arlReg = new ArrayList();
                                                               arlReg.add(INT_ARL_COD_CTA, codCta);
                                                               arlReg.add(INT_ARL_REG_DIA, intRegDia);
                                                               arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                               arlReg.add(INT_ARL_COD_ANA, StringGenAna);
                                                               arlReg.add(INT_ARL_TIP_MOV, "D");

                                                               arlDat.add(arlReg); 
                                                           }else{ 
                                                               
                                                           }
                                                       }while(rstDatos1.next());
                                                    }
                                                }
                                            //}while(rstConf1.next());
                                        }   
                                            }while(rstOtrosIngNoGra.next());
                                        }
                                     ///////------------------------------------------------------------------------------------------------------

                                     //OBTENGO LAS columnas  DEL DIARIO GENERAL - DEBE y que tengan configurado la analitica                          
                                     strSql4 = "";      
                                     strSql4 += " select * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='D' and tx_tipdia='F' and tx_conana='S'" ;
                                     rstConf = stmconf.executeQuery(strSql4);
                                     if (rstConf.next()) {
                                         do{   
                                             String colCon ="";
                                             if (rstConf.getString("tx_conana").equals("S")){
                                                 intRegDia++;//JM 4/Oct/2024 
                                                 strSql5 = ""; 
                                                 strSql5 += "  select trim(b.tx_ide) tx_ide,"+ rstConf.getString("tx_nomcol") + " as mntdes,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc a inner join tbm_tra b on a.co_tra=b.co_tra  where  a.co_per="+strperge+" and a.ne_per="+intNePer + " and " + rstConf.getString("tx_nomcol") +" >0";
                                                 rstDatos = stmdat.executeQuery(strSql5);
                                                 if (rstDatos.next()) {

                                                    do{     
                                                        StringGenAna="";
                                                        tipcencos=0;
                                                        idcencos="";
                                                        montotctadesg=0;  
                                                        tx_ide="";                      
                                                        tx_ide= rstDatos.getString("tx_ide");
                                                        tipcencos=rstDatos.getInt("tx_tipcos");       
                                                        idcencos= rstDatos.getString("tx_cencos");
                                                        montotctadesg=rstDatos.getDouble("mntdes");

                                                         //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                        strSql6="";
                                                        strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 (nolock) a\n" +
                                                                " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                                " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                                " where a.ACTINDX = "+codCta; 
                                                        rstCountAna = stmDat3.executeQuery(strSql6);
                                                        if (rstCountAna.next()) {
                                                             do{
                                                                coddim=0;
                                                                coddim=rstCountAna.getInt("aaTrxDimID");  
                                                                if (coddim==1){ 
                                                                    aaTrxDimCode=idcencos;
                                                                }else if (coddim==30){
                                                                    aaTrxDimCode=tx_ide;
                                                                }else if (coddim==23){
                                                                    //Obtengo el nombre del trabajador para buscar el id de codigo de analitica
                                                                    strSql9="";
                                                                    strSql9=" select (tx_ape + ' ' + tx_nom) nombrecompleto from tbm_tra where tx_ide='" + tx_ide + "'";
                                                                    rstTra = stmDat9.executeQuery(strSql9);
                                                                    if (rstTra.next()) {
                                                                    aaTrxDimCode=rstTra.getString("nombrecompleto");
                                                                    }    
                                                                }else{
                                                                   if (tipcencos==1){
                                                                        aaTrxDimCode="ADMINISTRATIVO";
                                                                    }else  if (tipcencos==2){
                                                                        aaTrxDimCode="VENTA";
                                                                    }else{ 
                                                                        aaTrxDimCode="LOGISTICA";
                                                                    }
                                                                }

                                                                strSql7="";
                                                                strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                                        " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                        " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                                                rstCodana = stmDat2.executeQuery(strSql7);
                                                                if (rstCodana.next()) {
                                                                    StringGenAna+=coddim + "-"+ rstCodana.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                                }
                                                             }while(rstCountAna.next());
                                                        }
                                                        if (StringGenAna.length() >0 ){ 
                                                            ArrayList arlReg = new ArrayList();
                                                            arlReg.add(INT_ARL_COD_CTA, codCta);
                                                            arlReg.add(INT_ARL_REG_DIA, intRegDia);
                                                            arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                            arlReg.add(INT_ARL_COD_ANA, StringGenAna);
                                                            arlReg.add(INT_ARL_TIP_MOV, "D");

                                                            arlDat.add(arlReg); 
                                                        }
                                                    }while(rstDatos.next());
                                                 }
                                             }
                                         }while(rstConf.next());
                                     }
                                     //add al arraylist

                                 } while(rstconcta.next());
                         }
                             stmdat.close();
                             stmDat2.close();
                             stmDat3.close();
                             stmsec.close();
                             stmUpd.close();
                             stmsel.close();
                             stmconf.close(); 
                             //--------------OBTENGO LAS CUENTAS DEL DIARIO GENERAL / HABER------------------
                            stmsec = con.createStatement(); 
                            stmUpd = con.createStatement(); 
                            stmsel= con.createStatement();
                            stmconf= con.createStatement();
                            stmdat= con.createStatement();
                            stmDat2 = con.createStatement();
                            stmDat3 = con.createStatement();     
                            StringGenAnaH="";

                             strSql3 = "";      
                             strSql3 += "  select  co_cta ,SUM(nd_mntcta) montocta from tbm_diagenbor where co_per=" + strperge + " and ne_per=" + intNePer + " and tx_tipdia='F' and tx_tipmov=1 group by co_cta" ;
                             rstconcta = stmsel.executeQuery(strSql3);
                             if (rstconcta.next()) {  
                                 do{
                                     codCta=0;
                                     montotcta=0;mntImpRenPro=0;
                                     codCta=rstconcta.getInt("co_cta");
                                     montotcta=rstconcta.getDouble("montocta");

                                     ///----------------------CASO DE OTROS DESCUENTOS--------------------------
                                        strSql8 = "";    
                                        strSql8 = "select * from tbm_detotrdes where co_cta="+codCta + " and ne_per="+intNePer + " and tx_tipmov='H' and co_per="+ strperge;    
                                        rstOtrosDESC = stm10.executeQuery(strSql8);
                                        if(rstOtrosDESC.next()){
                                            do{
                                                mntOtrDESC=rstOtrosDESC.getDouble("nd_mntcta");
                                                co_tra=rstOtrosDESC.getInt("co_tra");

                                                //OBTENGO LAS columnas  DEL DIARIO GENERAL - HABER y que tengan configurado la analitica                          
                                        strSql4 = "";      
                                        strSql4 += " select TOP 1 * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='H' and tx_tipdia='F' and tx_conana='S'" ;
                                        rstConf1 = stm6.executeQuery(strSql4);
                                        if (rstConf1.next()) {
                                            do{   
                                                String colCon ="";

                                                if (rstConf1.getString("tx_conana").equals("S")){
                                                    intRegDia++; 
                                                    strSql5 = ""; 
                                                    strSql5 += "  select trim(b.tx_ide) tx_ide,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc  a inner join tbm_tra b on a.co_tra=b.co_tra  where  a.co_per="+strperge+" and a.ne_per="+intNePer + " and a.co_tra="  +co_tra ;
                                                    rstDatos1 = stm7.executeQuery(strSql5);
                                                    if (rstDatos1.next()) {                                                
                                                       do{     
                                                           StringGenAna="";
                                                           tx_ide="";
                                                           tipcencos=0;
                                                           idcencos="";
                                                           montotctadesg=0;
                                                           tipcencos=rstDatos1.getInt("tx_tipcos");       
                                                           idcencos= rstDatos1.getString("tx_cencos"); 
                                                           tx_ide= rstDatos1.getString("tx_ide");

                                                           montotctadesg=mntOtrDESC;

                                                            //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                           strSql6="";
                                                           strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 (nolock) a\n" +
                                                                   " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                                   " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                                   " where a.ACTINDX = "+codCta; 
                                                           rstCountAna1 = stm8.executeQuery(strSql6);
                                                           if (rstCountAna1.next()) {
                                                                do{
                                                                   coddim=0;
                                                                   coddim=rstCountAna1.getInt("aaTrxDimID");  
                                                                   if (coddim==1){ 
                                                                       aaTrxDimCode=idcencos;
                                                                   }else if (coddim==30){
                                                                       aaTrxDimCode=tx_ide;
                                                                   }else if (coddim==23){
                                                                    //Obtengo el nombre del trabajador para buscar el id de codigo de analitica
                                                                    strSql9="";
                                                                    strSql9=" select (tx_ape + ' ' + tx_nom) nombrecompleto from tbm_tra where tx_ide='" + tx_ide + "'";
                                                                    rstTra = stmDat9.executeQuery(strSql9);
                                                                    if (rstTra.next()) {
                                                                    aaTrxDimCode=rstTra.getString("nombrecompleto");
                                                                    }    
                                                                   }else{
                                                                      if (tipcencos==1){
                                                                           aaTrxDimCode="ADMINISTRATIVO";
                                                                       }else  if (tipcencos==2){
                                                                           aaTrxDimCode="VENTA";
                                                                       }else{ 
                                                                           aaTrxDimCode="LOGISTICA";
                                                                       }
                                                                   }

                                                                   strSql7="";
                                                                   strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                                           " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                           " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                                                   rstCodana1 = stm9.executeQuery(strSql7);
                                                                   if (rstCodana1.next()) {
                                                                       StringGenAna+=coddim + "-"+ rstCodana1.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                                   }
                                                                }while(rstCountAna1.next());
                                                           }
                                                           if (StringGenAna.length() >0 ){ 
                                                               ArrayList arlReg = new ArrayList();
                                                               arlReg.add(INT_ARL_COD_CTA, codCta);
                                                               arlReg.add(INT_ARL_REG_DIA, intRegDia);
                                                               arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                               arlReg.add(INT_ARL_COD_ANA, StringGenAna);
                                                               arlReg.add(INT_ARL_TIP_MOV, "H");

                                                               arlDat.add(arlReg); 
                                                           }
                                                       }while(rstDatos1.next());
                                                    }
                                                }
                                            }while(rstConf1.next());
                                        }   
                                            }while(rstOtrosDESC.next());
                                        }
                                     ///////------------------------------------------------------------------------------------------------------


                                     //OBTENGO LAS columnas  DEL DIARIO GENERAL - HABER y que tengan configurado la analitica                          
                                     strSql4 = "";      
                                     strSql4 += " select * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='H' and tx_tipdia='F' and tx_conana='S'" ;
                                     rstConf = stmconf.executeQuery(strSql4);
                                     if (rstConf.next()) {
                                         String colCon ="";
                                         do{
                                             if (rstConf.getString("tx_conana").equals("S")){

                                                 ///------CASO DE IMPUESTO RENTA PROYECTADA---------------------
                                                 if (codCta ==190){
                                                     intRegDia++;//JM 4/Oct/2024 
                                                     strSql5 = ""; 
                                                     strSql5 += "  select * from tbm_ingimprenrolpag where  co_per="+strperge+" and ne_per="+intNePer ;
                                                     rstDatos2 = stmdat.executeQuery(strSql5);
                                                     if (rstDatos2.next()) {
                                                         do{
                                                         co_tra= rstDatos2.getInt("co_tra");
                                                         mntImpRenPro=rstDatos2.getDouble("nd_mnt");
                                                         intRegDia++;//JM 4/Oct/2024 
                                                         strSql5 = ""; 
                                                         strSql5 += " select trim(b.tx_ide) tx_ide,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc a \n" +
                                                                 " inner join tbm_tra b on a.co_tra=b.co_tra where  a.co_per="+strperge+" and a.ne_per="+intNePer + " and a.co_tra=" + co_tra;
                                                         rstDatos3 = stmDat4.executeQuery(strSql5);
                                                         if (rstDatos3.next()) {

                                                             do{    
                                                        tipcencos=0;
                                                        idcencos="";
                                                        montotctadesg=0;
                                                        tipcencos=rstDatos3.getInt("tx_tipcos");
                                                        idcencos= rstDatos3.getString("tx_cencos");
                                                        tx_ide= rstDatos3.getString("tx_ide");
                                                        montotctadesg=mntImpRenPro;

                                                        StringGenAnaH="";
                                                         //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                        strSql6="";
                                                        strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200  (nolock) a\n" +
                                                                " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                                " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                                " where a.ACTINDX = "+codCta; 
                                                        rstCountAna2 = stmDat3.executeQuery(strSql6);
                                                        if (rstCountAna2.next()) {
                                                             do{
                                                                coddim=0;
                                                                coddim=rstCountAna2.getInt("aaTrxDimID");  

                                                                strSql7="";
                                                                strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                                        " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                        " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +tx_ide+ "'";
                                                                rstCountAna2 = stmDat2.executeQuery(strSql7);
                                                                if (rstCountAna2.next()) {
                                                                    StringGenAnaH+=coddim + "-"+ rstCountAna2.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                                }
                                                             }while(rstCountAna2.next());
                                                        }
                                                        if (StringGenAnaH.length() > 0 ){
                                                            ArrayList arlReg = new ArrayList();
                                                            arlReg.add(INT_ARL_COD_CTA, codCta);
                                                            arlReg.add(INT_ARL_REG_DIA, intRegDia);
                                                            arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                            arlReg.add(INT_ARL_COD_ANA, StringGenAnaH);
                                                            arlReg.add(INT_ARL_TIP_MOV, "H");

                                                            arlDat.add(arlReg);  
                                                        }

                                                    }while(rstDatos3.next());
                                                 }
                                                         }while(rstDatos2.next());
                                                     }
                                                 }else{
                                                 //------------------------------------------------

                                                 intRegDia++;//JM 4/Oct/2024 
                                                 strSql5 = ""; 
                                                 strSql5 += "  select "+ rstConf.getString("tx_nomcol") + " as mntdes, trim(b.tx_ide) tx_ide,a.co_tra,a.tx_cencos,a.tx_tipcos from tbm_rolpagexc a inner join tbm_tra b on a.co_tra=b.co_tra where  a.co_per="+strperge+" and a.ne_per="+intNePer + " and " + rstConf.getString("tx_nomcol") +" >0";
                                                 rstDatos = stmDat5.executeQuery(strSql5);
                                                 if (rstDatos.next()) {

                                                    do{    
                                                        tipcencos=0;
                                                        idcencos="";
                                                        montotctadesg=0;tx_ide="";
                                                        tipcencos=rstDatos.getInt("tx_tipcos");
                                                        idcencos= rstDatos.getString("tx_cencos");
                                                        montotctadesg=rstDatos.getDouble("mntdes");
                                                        tx_ide= rstDatos.getString("tx_ide");

                                                        StringGenAnaH="";
                                                         //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                        strSql6="";
                                                        strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 (nolock) a\n" +
                                                                " inner join tuval..AAG00202 (nolock) b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                                " inner join tuval..AAG00400 (nolock) c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                                " where a.ACTINDX = "+codCta; 
                                                        rstCountAna = stmDat3.executeQuery(strSql6);
                                                        if (rstCountAna.next()) {
                                                             do{
                                                                coddim=0;
                                                                coddim=rstCountAna.getInt("aaTrxDimID");  
                                                                if (coddim==1){ 
                                                                    aaTrxDimCode=idcencos;
                                                                }else if (coddim==30){
                                                                    aaTrxDimCode=tx_ide;
                                                                }else if (coddim==23){
                                                                    //Obtengo el nombre del trabajador para buscar el id de codigo de analitica
                                                                    strSql9="";
                                                                    strSql9=" select (tx_ape + ' ' + tx_nom) nombrecompleto from tbm_tra where tx_ide='" + tx_ide + "'";
                                                                    rstTra = stmDat9.executeQuery(strSql9);
                                                                    if (rstTra.next()) {
                                                                    aaTrxDimCode=rstTra.getString("nombrecompleto");
                                                                    }
                                                                    
                                                                }else{
                                                                   if (tipcencos==1){
                                                                        aaTrxDimCode="ADMINISTRATIVO";
                                                                    }else  if (tipcencos==2){
                                                                        aaTrxDimCode="VENTA";
                                                                    }else{ 
                                                                        aaTrxDimCode="LOGISTICA";
                                                                    }
                                                                }

                                                                strSql7="";
                                                                strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 (nolock) a\n" +
                                                                        " inner join tuval..AAG00401 (nolock) b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                        " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                                                rstCodana = stmDat2.executeQuery(strSql7);
                                                                if (rstCodana.next()) {
                                                                    StringGenAnaH+=coddim + "-"+ rstCodana.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                                }
                                                             }while(rstCountAna.next());
                                                        }
                                                        if (StringGenAnaH.length() > 0 ){
                                                            ArrayList arlReg = new ArrayList();
                                                            arlReg.add(INT_ARL_COD_CTA, codCta);
                                                            arlReg.add(INT_ARL_REG_DIA, intRegDia);
                                                            arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                            arlReg.add(INT_ARL_COD_ANA, StringGenAnaH);
                                                            arlReg.add(INT_ARL_TIP_MOV, "H");

                                                            arlDat.add(arlReg);  
                                                        }

                                                    }while(rstDatos.next());
                                                 }
                                                 } 
                                             }
                                         }while(rstConf.next());
                                     }
                                 } while(rstconcta.next());
                         }}
                             stmdat.close();
                             stmDat2.close();
                             stmDat3.close();
                             stmDat4.close();
                             stmDat5.close();
                             stmDat8.close(); 
                             stmDat9.close();

                             stmsec.close();
                             stmUpd.close();
                             stmsel.close();
                             stmconf.close(); 

                    }
                    //generar las analiticas
                    if(blnRes){
                        if (intNePer==2){ 
                            if(GenerarAnaliticas(con,tx_alias,intCodDiarioG,arlDat)){
                                blnRes=true;
                            }
                        }
                    }
                    cal.close();
                    cal=null;

            } 
            catch (java.sql.SQLException Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            return blnRes;
    }

    private boolean GenerarAsientoProvisional(Connection con,String cadena,String TipDia) {
        boolean blnRes = true;  
        java.sql.CallableStatement cal;
        java.sql.ResultSet rstLoc = null;
        String strSql = "",strSql1="",strSql2="",strSql3="",strSql4="" ,strSql5="" ,strSql6="",strSql7="" ,StringGenAna="",StringGenAnaH="",strSqlErr="";   
        resArmarDatDiario = new ArrayList<>();        
        String strMes = String.format("%02d", intMes);
        String strperge = strMes + intAño;
        double ctaDebe= 0; 
        double ctaHaber= 0;
        double totcta= 0;     
        Statement stmsec,stmUpd,stmsel,stmconf,stmdat, stmDat2, stmDat3,stmDat8;
        
        LocalDate fechaActual = obtenerUltimoDiaDelMes(intMes, intAño); //FECHA DIARIO 
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formateador);   
        
        try{
        
        stmsec = con.createStatement(); 
        stmUpd = con.createStatement(); 
        stmsel= con.createStatement();
        stmconf= con.createStatement();
        stmdat= con.createStatement();
        stmDat2 = con.createStatement();
        stmDat3 = con.createStatement();
        stmDat8 = con.createStatement();
                int num_sec= 0;
                String tx_alias="";
                strSql = "";      
                strSql += "select nd_sec+1 sec,tx_for from [dbo].[tbm_cfglot] where co_lot=3" ;
                rst = stmsec.executeQuery(strSql);
                if (rst.next()) {   
                      num_sec= rst.getInt("sec");   
                      tx_alias= rst.getString("tx_for");          
                }
               //stmsec.close();
                tx_alias= tx_alias+"_"+ num_sec;
                strSql1=""; 
                strSql1="{call dbo.sp_creacionDiario(?, ?,?,?,?,?,?,?)}";
                cal = con.prepareCall(strSql1);
                cal.setString(1, tx_alias ); 
                cal.setString(2,  "Diario " + TipDia +" de nomina" + strperge + "/"+intNePer ); 
                cal.setString(3,  fechaFormateada);  
                cal.setInt(4,  0); 
                cal.setString(5,  cadena); 
                cal.registerOutParameter(6, java.sql.Types.INTEGER);
                cal.registerOutParameter(7, java.sql.Types.INTEGER);
                cal.registerOutParameter(8, java.sql.Types.NVARCHAR);
                
                ArrayList arlDat = new ArrayList();  // Donde necesites iniciar a llenar algo nuevo 

                cal.execute();
                //cal.getInt("diario");
                
                int  intCodDiariop = cal.getInt(7);  
                int  intCodError = cal.getInt(6);
                String  strError = cal.getString(8);
                if(intCodError==1){
                    blnRes=false;
                    intCodDiariop=0;
                     if (!strError.equals("")){
                            String[] strErrorwords = strError.split(" "); // Dividir la cadena por espacio
                  
                            String codErrorGP = String.join(",", strErrorwords); // Une las palabras con comas
                            strSqlErr = "";    
                            strSqlErr = " select ErrorDesc,SourceProc,ErrorParms from dynamics.dbo.taerrorcode where errorcode in (" + codErrorGP + ")";   
                            rst00 = stmDat8.executeQuery(strSqlErr);
                            if(rst00.next()){
                                MesError+= "Parrametro Error: " + rst00.getString("ErrorParms")+ "/ Error: "+ rst00.getString("ErrorDesc");                                                      
                            }
                             
                        }
                }else{
                    
                        //actualizo el secuencial 
                         strSql2 = "";  
                         strSql2=" update tbm_seggendiarolpag set st_gendiarolpag='S',fe_gendiarolpag=getdate(),ne_diapro="+ intCodDiariop +" where co_emp=1 and co_per="+ strperge +" and ne_per=" + intNePer + "; \n";
                         strSql2 += "update dbo.[tbm_cfglot] set nd_sec=" +num_sec + " where co_lot=3";
                         stmUpd = con.createStatement();
                         stmUpd.executeUpdate(strSql2);
                         
                        // stmUpd.close();
                         int  codCta = 0,coddim=0;   
                         double montotcta =0;  
                         double montotctadesg =0;

                         int  tipcencos = 0;
                         String  idcencos = "",aaTrxDimCode="";
                         //CREAR LAS ANALITICAS
                        if (intNePer==2){ 
                         //--------------OBTENGO LAS CUENTAS DEL DIARIO PROVISION / DEBE------------------
                         strSql3 = "";      
                         strSql3 += "  select  co_cta ,SUM(nd_mntcta) montocta from tbm_diagenbor where co_per=" + strperge + " and ne_per=" + intNePer + " and tx_tipdia='P' and tx_tipmov=0 group by co_cta" ;
                         rstconcta = stmsel.executeQuery(strSql3);
                         if (rstconcta.next()) {  
                             do{
                                 codCta=0;
                                 montotcta=0;
                                 StringGenAna="";
                                 codCta=rstconcta.getInt("co_cta");
                                 montotcta=rstconcta.getDouble("montocta");
                                 //OBTENGO LAS columnas  DEL DIARIO PROVISION - DEBE y que tengan configurado la analitica                          
                                 strSql4 = "";      
                                 strSql4 += " select * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='D' and tx_tipdia='P' and tx_conana='S'" ;
                                 rstConf = stmconf.executeQuery(strSql4);
                                 if (rstConf.next()) {
                                     do{    
                                         String colCon ="";

                                         if (rstConf.getString("tx_conana").equals("S")){
                                             strSql5 = ""; 
                                             strSql5 += "  select "+ rstConf.getString("tx_nomcol") + " as mntdes,co_tra,tx_cencos,tx_tipcos from tbm_rolpagexc  where  co_per="+strperge+" and ne_per="+intNePer + " and " + rstConf.getString("tx_nomcol") +" >0";
                                             rstDatos = stmdat.executeQuery(strSql5);
                                             if (rstDatos.next()) {                                                
                                                do{     
                                                    StringGenAna="";
                                                    tipcencos=0;
                                                    idcencos="";
                                                    montotctadesg=0;
                                                    tipcencos=rstDatos.getInt("tx_tipcos");
                                                    idcencos= rstDatos.getString("tx_cencos");
                                                    montotctadesg=rstDatos.getDouble("mntdes");

                                                     //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                    strSql6="";
                                                    strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 a\n" +
                                                            " inner join tuval..AAG00202 b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                            " inner join tuval..AAG00400 c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                            " where a.ACTINDX = "+codCta; 
                                                    rstCountAna = stmDat3.executeQuery(strSql6);
                                                    if (rstCountAna.next()) {
                                                         do{
                                                            coddim=0;
                                                            coddim=rstCountAna.getInt("aaTrxDimID");  
                                                            if (coddim==1){ 
                                                                aaTrxDimCode=idcencos;
                                                            }else{
                                                               if (tipcencos==1){
                                                                    aaTrxDimCode="ADMINISTRATIVO";
                                                                }else  if (tipcencos==2){
                                                                    aaTrxDimCode="VENTA";
                                                                }else{ 
                                                                    aaTrxDimCode="LOGISTICA";
                                                                }
                                                            }
                                                            
                                                            strSql7="";
                                                            strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 a\n" +
                                                                    " inner join tuval..AAG00401 b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                    " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                                            rstCodana = stmDat2.executeQuery(strSql7);
                                                            if (rstCodana.next()) {
                                                                StringGenAna+=coddim + "-"+ rstCodana.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                            }
                                                         }while(rstCountAna.next());
                                                    }
                                                    if (StringGenAna.length() >0 ){
                                                        ArrayList arlReg = new ArrayList();
                                                        arlReg.add(INT_ARL_COD_CTA, codCta);
                                                        arlReg.add(INT_ARL_REG_DIA, intCodDiariop);
                                                        arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                        arlReg.add(INT_ARL_COD_ANA, StringGenAna);
                                                        arlReg.add(INT_ARL_TIP_MOV, "D");
                                                        
                                                        arlDat.add(arlReg);  
                                                    }
                                                }while(rstDatos.next());
                                             }
                                         }
                                     }while(rstConf.next());
                                 }
                                 //add al arraylist
                                 
                             } while(rstconcta.next());
                     }
                         stmdat.close();
                         stmDat2.close();
                         stmDat3.close();
                         stmsec.close();
                         stmUpd.close();
                         stmsel.close();
                         stmconf.close();
                         stmDat3.close();
                         //--------------OBTENGO LAS CUENTAS DEL DIARIO PROVISION / HABER------------------
                        stmsec = con.createStatement(); 
                        stmUpd = con.createStatement(); 
                        stmsel= con.createStatement();
                        stmconf= con.createStatement();
                        stmdat= con.createStatement();
                        stmDat2 = con.createStatement();
                        stmDat3 = con.createStatement();     
                        StringGenAnaH="";

                         strSql3 = "";      
                         strSql3 += "  select  co_cta ,SUM(nd_mntcta) montocta from tbm_diagenbor where co_per=" + strperge + " and ne_per=" + intNePer + " and tx_tipdia='P' and tx_tipmov=1 group by co_cta" ;
                         rstconcta = stmsel.executeQuery(strSql3);
                         if (rstconcta.next()) {  
                             do{
                                 codCta=0;
                                 montotcta=0;
                                 codCta=rstconcta.getInt("co_cta");
                                 montotcta=rstconcta.getDouble("montocta");
                                 //OBTENGO LAS columnas  DEL DIARIO PROVISION - HABER y que tengan configurado la analitica                          
                                 strSql4 = "";      
                                 strSql4 += " select * from tbr_ctacolgendia a where co_cta=" + codCta + " and tx_tipmov='H' and tx_tipdia='P' and tx_conana='S'" ;
                                 rstConf = stmconf.executeQuery(strSql4);
                                 if (rstConf.next()) {
                                     do{
                                         String colCon ="";

                                         if (rstConf.getString("tx_conana").equals("S")){
                                             strSql5 = ""; 
                                             strSql5 += "  select "+ rstConf.getString("tx_nomcol") + " as mntdes,co_tra,tx_cencos,tx_tipcos from tbm_rolpagexc  where  co_per="+strperge+" and ne_per="+intNePer + " and " + rstConf.getString("tx_nomcol") +" >0";
                                             rstDatos = stmdat.executeQuery(strSql5);
                                             if (rstDatos.next()) {
                                               
                                                do{
                                                    tipcencos=0;
                                                    idcencos="";
                                                    montotctadesg=0;
                                                    tipcencos=rstDatos.getInt("tx_tipcos");
                                                    idcencos= rstDatos.getString("tx_cencos");
                                                    montotctadesg=rstDatos.getDouble("mntdes");

                                                    StringGenAnaH="";
                                                     //OBTENGO CUANTAS ANALITICAS TIENE ESA CUENTA 
                                                    strSql6="";
                                                    strSql6=" select b.aaTrxDimID,c.aaOrder,* from tuval..AAG00200 a\n" +
                                                            " inner join tuval..AAG00202 b on a.aaAcctClassID=b.aaAcctClassID\n" +
                                                            " inner join tuval..AAG00400 c on b.aaTrxDimID=c.aaTrxDimID\n" +
                                                            " where a.ACTINDX = "+codCta; 
                                                    rstCountAna = stmDat3.executeQuery(strSql6);
                                                    if (rstCountAna.next()) {
                                                         do{
                                                            coddim=0;
                                                            coddim=rstCountAna.getInt("aaTrxDimID");  
                                                            if (coddim==1){ 
                                                                aaTrxDimCode=idcencos;
                                                            }else{
                                                               if (tipcencos==1){
                                                                    aaTrxDimCode="ADMINISTRATIVO";
                                                                }else  if (tipcencos==2){
                                                                    aaTrxDimCode="VENTA";
                                                                }else{ 
                                                                    aaTrxDimCode="LOGISTICA";
                                                                }
                                                            }
                                                            
                                                            strSql7="";
                                                            strSql7=" select aaTrxDimCodeID from  tuval..AAG00400 a\n" +
                                                                    " inner join tuval..AAG00401 b on a.aaTrxDimID=b.aaTrxDimID\n" +
                                                                    " where a.aaTrxDimID=" + coddim + " and TRIM(b.aaTrxDimCode) ='" +aaTrxDimCode+ "'";
                                                            rstCodana = stmDat2.executeQuery(strSql7);
                                                            if (rstCodana.next()) {
                                                                StringGenAnaH+=coddim + "-"+ rstCodana.getInt("aaTrxDimCodeID")+ "|";                                                         
                                                            }
                                                         }while(rstCountAna.next());
                                                    }
                                                    if (StringGenAnaH.length() > 0 ){
                                                         ArrayList arlReg = new ArrayList();
                                                        arlReg.add(INT_ARL_COD_CTA, codCta);
                                                        arlReg.add(INT_ARL_REG_DIA, intCodDiariop);
                                                        arlReg.add(INT_ARL_MON_ANA, montotctadesg);
                                                        arlReg.add(INT_ARL_COD_ANA, StringGenAnaH);
                                                        arlReg.add(INT_ARL_TIP_MOV, "H");
                                                        
                                                        arlDat.add(arlReg);  
                                                    }
                                                }while(rstDatos.next());
                                             }
                                         }
                                     }while(rstConf.next());
                                 }
                             } while(rstconcta.next());
                     }}
                        stmdat.close();
                         stmDat2.close();
                         stmDat3.close();
                         stmsec.close();
                         stmUpd.close();
                         stmsel.close();
                         stmconf.close(); 
                         
                }
                
                if (intNePer==2){ 
                    if(GenerarAnaliticas(con,tx_alias,intCodDiariop,arlDat)){
                       blnRes=true;
                    }
                    else{
                       blnRes=false;
                    }
                }
                
                cal.close();
                cal=null;
               
        } 
        catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
            
            return blnRes;

    }

    
    /**
     * Metodo para llamar a la generacion de analiticas 
     * @param con
     * @param strLote
     * @param intCodDia
     * @param arlDatAna
     * @return 
     */
    private boolean GenerarAnaliticas(Connection con, String strLote, int intCodDia,  ArrayList arlDatAna) {
        boolean blnRes = true;    
            
            ZafGenAna objgenAnalitica = new ZafGenAna(objParSis,javax.swing.JOptionPane.getFrameForComponent(this));            
            if (!objgenAnalitica.agregarAnaliticas(con, strLote, intCodDia, arlDatAna)) { 
                blnRes = false;
            }
            objgenAnalitica = null;
        return blnRes;
    }
    
    
    
    private boolean iniciaGenerarAsiento(){
        boolean blnRes=true;
        String  strSql1="";   
        java.sql.Connection con;
        java.sql.Statement stmLoc ;
        String strMes = String.format("%02d", intMes);
        String strperge = strMes + intAño;
        if (objTblMod.getRowCountTrue() <=0){
            mostrarMsgInf("No hay registros para generar el asiento.");
            return false;
        }  

        if ((cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==12 && cboPer.getSelectedIndex()==2))
        {
            mostrarMsgInf("Período invalido. Comuníquese con su administrador del Sistema.");
            return false;
        }
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Generando asientos...");
        try{
       
                 
            //con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null)
            {    
                //validar que el asientoborrador este generado
                stmLoc = con.createStatement();
                strSql1="";  
                strSql1="select st_revotring,st_revdiabor,st_gendiarolpag,ne_diagen from tbm_seggendiarolpag WHERE co_per=" +strperge+" and ne_per=" + intNePer + ";\n";

                rst  = stmLoc.executeQuery(strSql1);
                if (rst.next()) { 
                    if (  rst.getString("st_revdiabor").equals("N") && rst.getString("st_gendiarolpag").equals("N") ){       
                        mostrarMsgInf("El Diario borrador no se encuentra guardado. Revisar Paso 5");
                        return false;                         
                    }
                }

                con.setAutoCommit(false);
                if (GenerarAsiento(con))
                {
                    con.commit();
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
//                            butCon.doClick();
                    objThrGUI = null;
                    if (objThrGUI == null)
                    {
                        objThrGUI = new ZafThreadGUI();
                        objThrGUI.start();
                    }
                }
                else
                {
                    blnRes=false;
                    con.rollback();
                    pgrSis.setValue(0);
                    pgrSis.setIndeterminate(false);
                    mostrarMsgInf("Error al guardar generar el diario con sus analiticas. (" + MesError.trim() + ")");
                }
            }
                
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    
    private void bntGenAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGenAsientoActionPerformed
        
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
            if(objThrGua==null){
                objThrGua=new ZafCon65.ZafThrGua();
                objThrGua.start();
            }
        }
         
    }//GEN-LAST:event_bntGenAsientoActionPerformed

    private void butPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPerActionPerformed

    }//GEN-LAST:event_butPerActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void bntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEliminarActionPerformed

        // TODO add your handling code here:
        if (!EliminarRegistros()){
            mostrarMsgInf("No se puede eliminar el asiento generado.");
        }else{  
            mostrarMsgInf("Registros eliminados correctamente, puede volver a subir el excel");   
            objThrGUI = null;
            if (objThrGUI == null)
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }       
        }
    }//GEN-LAST:event_bntEliminarActionPerformed

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton bntEliminar;
    private javax.swing.JButton bntGenAsiento;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JComboBox<String> cboPer;
    private javax.swing.JComboBox<String> cboPerAAAA;
    private javax.swing.JComboBox<String> cboPerMes;
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
    private javax.swing.JTable tblDat;
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
            if (tblDat.getRowCount() > 0) {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    private ZafThrGua objThrGua;
   
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
               //Inicializar objetos si no se pudo cargar los datos.
                
                if (iniciaGenerarAsiento()){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);        
                    pgrSis.setIndeterminate(false);
                    butCon.setText("Consultar");
                }
                objThrGua=null;
                
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    /**
     * Configurar el formulario.
     */
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
            
            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 1: Cargado Automatico desde Zafiro");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKEXCCARAUT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECEXCCARAUT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTEXCCARAUT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 2: Conf. Otros Ingresos NO Gravables");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKOTRING));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECOTRING));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTOTRING));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            
            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 3: Conf. Otros Descuentos");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKOTRDESC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECOTRDESC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTOTRDESC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 4: Ingreso de Imp. Renta");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKIMPRENT));   
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECIMPRENT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTIMPRENT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 5: Revision Diario Borrador");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTDIABOR));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECAUTDIABOR));   
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTAUTDIABOR));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 6: Diarios Generados");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKDIAGEN)); 
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECDIAGEN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDIAGEN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDIAPRO));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
 
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
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
            vecCab.add(INT_TBL_DAT_CHKEXCCARAUT, "Realizado");
            vecCab.add(INT_TBL_DAT_FECEXCCARAUT, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTEXCCARAUT, "Muestra inf.cargada desde Zafiro");
            vecCab.add(INT_TBL_DAT_CHKOTRING, "Realizado");
            vecCab.add(INT_TBL_DAT_FECOTRING, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTOTRING, "Revision/Ingreso Valores");
            vecCab.add(INT_TBL_DAT_CHKOTRDESC, "Realizado");
            vecCab.add(INT_TBL_DAT_FECOTRDESC, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTOTRDESC, "Revision/Otros Descuentos");
            vecCab.add(INT_TBL_DAT_CHKIMPRENT, "Ingresado");
            vecCab.add(INT_TBL_DAT_FECIMPRENT, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTIMPRENT, "Ingreso Valores");
            vecCab.add(INT_TBL_DAT_CHKAUTDIABOR, "Autorizado");
            vecCab.add(INT_TBL_DAT_FECAUTDIABOR, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTAUTDIABOR, "Muestra Diario Borrador");
            vecCab.add(INT_TBL_DAT_CHKDIAGEN, "D.Generados");
            vecCab.add(INT_TBL_DAT_FECDIAGEN, "Fecha D.Generado");
            vecCab.add(INT_TBL_DAT_NUMDIAGEN, "#D.General");
            vecCab.add(INT_TBL_DAT_NUMDIAPRO, "#D.Provision");

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

            //Tamano de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHKEXCCARAUT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECEXCCARAUT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTEXCCARAUT).setPreferredWidth(180); 
            
            tcmAux.getColumn(INT_TBL_DAT_CHKOTRING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECOTRING).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRING).setPreferredWidth(180); 
            
            tcmAux.getColumn(INT_TBL_DAT_CHKOTRDESC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECOTRDESC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRDESC).setPreferredWidth(180); 
            
            tcmAux.getColumn(INT_TBL_DAT_CHKIMPRENT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECIMPRENT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTIMPRENT).setPreferredWidth(150); 
            
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTDIABOR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECAUTDIABOR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUTAUTDIABOR).setPreferredWidth(180); 
            
            tcmAux.getColumn(INT_TBL_DAT_CHKDIAGEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECDIAGEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NUMDIAGEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUMDIAPRO).setPreferredWidth(80);
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDat);

            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTEXCCARAUT);
            vecAux.add("" + INT_TBL_DAT_BUTOTRING);
            vecAux.add("" + INT_TBL_DAT_BUTAUTDIABOR); 
            vecAux.add("" + INT_TBL_DAT_BUTIMPRENT); 
            vecAux.add("" + INT_TBL_DAT_BUTOTRDESC); 

            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTEXCCARAUT).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();//llamarPantallaListadoFaltasEmpleados
            tcmAux.getColumn(INT_TBL_DAT_BUTEXCCARAUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTEXCCARAUT:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    strperiodo="";
                    if (intFilSel != -1) {
                       String strMes = String.format("%02d", intMes);
                       strperiodo = strMes + intAño;
                        
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTEXCCARAUT:
                        llamarPantallaDatosExcel(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer));
                        }
                    }
                }
            });

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRING).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRING).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTOTRING:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                         switch (intColSel) {
                             case INT_TBL_DAT_BUTOTRING:
                                String strMes = String.format("%02d", intMes);
                                String strperiodo = strMes + intAño;
                                if (String.valueOf(intNePer).equals("1")){
                                    mostrarMsgInf("<HTML>La configuracion de otros Ingresos NO Gravables,<BR>es solo para el periodo de Fin de mes. .</HTML>");
                                }else{
                                    llamarPantallaDatosOtrosIngresos(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer));
                                } 
                          }
                    }
                }
            });

            
            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTAUTDIABOR).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTAUTDIABOR).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTAUTDIABOR:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) { 
                        switch (intColSel) {
                             case INT_TBL_DAT_BUTAUTDIABOR:
                                String strMes = String.format("%02d", intMes);
                                String strperiodo = strMes + intAño;
                                llamarPantallaGeneraDiarioBorrador(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer));
                                  
                          }
                    }
                }
            });
            
            
            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTIMPRENT).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTIMPRENT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTIMPRENT:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) { 
                        switch (intColSel) {
                             case INT_TBL_DAT_BUTIMPRENT:
                                String strMes = String.format("%02d", intMes);
                                String strperiodo = strMes + intAño;
                                
                                if (String.valueOf(intNePer).equals("1")){
                                    mostrarMsgInf("<HTML>La configuracion de Ingresos de Impuesta Renta Proyectada,<BR>es solo para el periodo de Fin de mes. .</HTML>");
                                }else{
                                    llamarPantallaIngresoImpuestoRentaProyectada(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer));
                                } 
                          }
                    }
                }
            });
            
            
            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRDESC).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTOTRDESC).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTOTRDESC:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) { 
                        switch (intColSel) {
                             case INT_TBL_DAT_BUTOTRDESC:
                                String strMes = String.format("%02d", intMes);
                                String strperiodo = strMes + intAño;
                                
                                if (String.valueOf(intNePer).equals("1")){
                                    mostrarMsgInf("<HTML>La configuracion de Ingresos de Otros Descuentos,<BR>es solo para el periodo de Fin de mes. .</HTML>");
                                }else{
                                    llamarPantallaIngresoOtrosDescuentos(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(strperiodo), String.valueOf(intNePer));
                                } 
                          }
                    }
                }
            });
            
            
            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

  
//    private boolean agregarColTblDat() 

    private String setMes() {
        String strMes = "";

        int intPosMes = intMes - 1;
        
        arrLstMeses = new ArrayList<String>();
        arrLstMeses.add("ENERO");
        arrLstMeses.add("FEBRERO");
        arrLstMeses.add("MARZO");
        arrLstMeses.add("ABRIL");
        arrLstMeses.add("MAYO");
        arrLstMeses.add("JUNIO");
        arrLstMeses.add("JULIO");
        arrLstMeses.add("AGOSTO");
        arrLstMeses.add("SEPTIEMBRE");
        arrLstMeses.add("OCTUBRE");
        arrLstMeses.add("NOVIEMBRE");
        arrLstMeses.add("DICIEMBRE");

        return arrLstMeses.get(intPosMes).toString();
    }

   
    private String retornaValorStr(String strValor) {
        String cadena = strValor;
        String cadena2 = "";
        int car = cadena.indexOf(".");
        int limite = 0;
        if (car > 0) {
            cadena2 = cadena.substring(car + 1);
        }

        limite = (cadena2.length() > 0) ? cadena2.length() : 2;

        if (limite % 2 != 0) {
            for (int i = 0; i < limite; i++) {
                cadena += "0";
            }
        }

        cadena = cadena.replace(".", "");
        strValor = cadena;
        return strValor;
    }

    private void llamarPantallaDatosExcel(String strCodEmp, String strCoPer,String strNePer) {
      Contabilidad.ZafCon65.ZafCon65_01 obj1  = new Contabilidad.ZafCon65.ZafCon65_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer);
      obj1.show();
    }

    private void llamarPantallaDatosOtrosIngresos(String strCodEmp, String strCoPer,String strNePer) {
        Contabilidad.ZafCon65.ZafCon65_02 obj1 = new Contabilidad.ZafCon65.ZafCon65_02(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer,blnOtrIngNoGrav);
        //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
//        }
    }
   
    private void llamarPantallaGeneraDiarioBorrador(String strCodEmp, String strCoPer,String strNePer) {
        pgrSis.setIndeterminate(true);       
        lblMsgSis.setText("Generando borrador...");
        System.out.println("Generando borrador...");
        Contabilidad.ZafCon65.ZafCon65_04 obj1  = new Contabilidad.ZafCon65.ZafCon65_04(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer,blnIngImpRen);
        obj1.show();
        pgrSis.setIndeterminate(false); 
        System.out.println("Listo...");
        lblMsgSis.setText("Listo...");
    }
    
    private void llamarPantallaIngresoImpuestoRentaProyectada(String strCodEmp, String strCoPer,String strNePer) {
        Contabilidad.ZafCon65.ZafCon65_05 obj1 = new Contabilidad.ZafCon65.ZafCon65_05(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer,blnDiaBorr);
        //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
//        }
    } 
    private void llamarPantallaIngresoOtrosDescuentos(String strCodEmp, String strCoPer,String strNePer) {
        Contabilidad.ZafCon65.ZafCon65_06 obj1 = new Contabilidad.ZafCon65.ZafCon65_06(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, String.valueOf(strCodEmp), strCoPer, strNePer,blnDiaBorr);
        //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
//        }
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
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;

                case INT_TBL_DAT_CHKEXCCARAUT:
                    strMsg = "Excel cargado automaticamente";
                    break;
                case INT_TBL_DAT_FECEXCCARAUT:
                    strMsg = "Fecha en que se cargo desde Zafiro";
                    break;
                case INT_TBL_DAT_BUTEXCCARAUT:
                    strMsg = "Muestra el la inf. del excel\"";
                    break;
                case INT_TBL_DAT_CHKOTRING:
                    strMsg = "Revision de otros Ingresos no gravables";
                    break;
                case INT_TBL_DAT_FECOTRING:
                    strMsg = "Fecha en que se reviso los otros ingresos no gravables";
                    break;
                case INT_TBL_DAT_BUTOTRING:
                    strMsg = "Muestra la configuracion de los otros ingresos no gravables";
                    break;
                case INT_TBL_DAT_CHKAUTDIABOR:
                    strMsg = "Autorizacion de Diario del Rol";
                    break;
                case INT_TBL_DAT_FECAUTDIABOR:
                    strMsg = "Fecha de Autorizacion del Diario";
                    break;
                case INT_TBL_DAT_BUTAUTDIABOR:
                    strMsg = "Muestra el Diario";
                    break;
                case INT_TBL_DAT_CHKIMPRENT:
                    strMsg = "Se ingresaron los valores de impuesto a la renta";
                    break;
                case INT_TBL_DAT_FECIMPRENT:
                    strMsg = "Fecha en que se ingresaron los valores";
                    break;
                case INT_TBL_DAT_BUTIMPRENT:
                    strMsg = "Muestra inf. de Imp. Renta";
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
                }
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean asignacionPeriodo() {
        boolean blnRes = false;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "";
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                con.setAutoCommit(false);
                intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                intMes = cboPerMes.getSelectedIndex()+1;
                intNePer = cboPer.getSelectedIndex()+1;
                blnRes = true;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try {
                stm.close();
                stm = null;
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

    private boolean cargarDetReg() {
        boolean blnRes = true;
        int i;
        int intNumReg = 0;
        blnIngImpRen=false;      
        blnOtrIngNoGrav=false;  
        blnOtrIngDes=false;

        blnDiaBorr=false;
        blnDiaGen=false;

        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null) {
                stm = con.createStatement();

                arrLstAni = new ArrayList<String>();
                arrLstMes = new ArrayList<String>();
                strSQL = "";   
                strSQL += " SELECT SUBSTRING(co_per, 3, 4) anio,SUBSTRING(co_per, 1, 2) mes, ne_per,st_genexcaut,st_gendiarolpag,st_ingotrdes,fe_ingotrdes,st_revdiabor,st_revotring,st_ingimpren,fe_gendiarolpag,fe_revotring,fe_genexcaut,fe_ingimpren,fe_revdiabor,isnull(ne_diagen,0) ne_diagen,isnull(ne_diapro,0) ne_diapro"
                        + " from tbm_seggendiarolpag where co_emp= 1 and SUBSTRING(co_per, 3, 4) =" + intAño +
                        " and SUBSTRING(co_per, 1, 2)  = " + intMes + " and  ne_per = " + intNePer;
                System.out.println("Select 1: "+strSQL);
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
                        String strAux = rst.getString("st_genexcaut");
                        if (null == strAux || strAux.equals("N") ) {
                            vecReg.add(INT_TBL_DAT_CHKEXCCARAUT, false);
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKEXCCARAUT, true);
                        }
                        vecReg.add(INT_TBL_DAT_FECEXCCARAUT, rst.getString("fe_genexcaut"));
                        vecReg.add(INT_TBL_DAT_BUTEXCCARAUT, "...");
                        
                        String strAux1 = rst.getString("st_revotring");
                        if (null ==strAux1 || strAux1.equals("N") ) {
                            vecReg.add(INT_TBL_DAT_CHKOTRING, false);   
                            blnOtrIngNoGrav = false;
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKOTRING, true);
                            blnOtrIngNoGrav = true;
                        }
                        vecReg.add(INT_TBL_DAT_FECOTRING, rst.getString("fe_revotring"));
                        vecReg.add(INT_TBL_DAT_BUTOTRING, "...");
                        
                        String strAux5 = rst.getString("st_ingotrdes");
                        if ( null== strAux5  || strAux5.equals("N") ) {
                            vecReg.add(INT_TBL_DAT_CHKOTRDESC, false);
                            blnOtrIngDes=false;
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKOTRDESC, true);
                            blnOtrIngDes = true;
                        }
                        vecReg.add(INT_TBL_DAT_FECOTRDESC, rst.getString("fe_ingotrdes"));
                        vecReg.add(INT_TBL_DAT_BUTOTRDESC, "...");

                        String strAux2 = rst.getString("st_ingimpren");
                        if (null ==strAux2 || strAux2.equals("N")) {
                            vecReg.add(INT_TBL_DAT_CHKIMPRENT, false);
                            blnIngImpRen=false;
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKIMPRENT, true);
                            blnIngImpRen = true;
                        }
                        vecReg.add(INT_TBL_DAT_FECIMPRENT, rst.getString("fe_ingimpren"));
                        vecReg.add(INT_TBL_DAT_BUTIMPRENT, "...");
                        
                        String strAux3 = rst.getString("st_revdiabor");
                        if ( null == strAux3 || strAux3.equals("N") ) {
                            vecReg.add(INT_TBL_DAT_CHKAUTDIABOR, false);  
                            blnOtrIngNoGrav = false;   
                            blnIngImpRen=false;
                            blnDiaBorr=false;
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKAUTDIABOR, true);
                            blnDiaBorr = true;                          
                            blnOtrIngNoGrav = true;
                            blnIngImpRen = true;
                        }
                        vecReg.add(INT_TBL_DAT_FECAUTDIABOR, rst.getString("fe_revdiabor"));
                        vecReg.add(INT_TBL_DAT_BUTAUTDIABOR, "...");
                        
 
                        String strAux4 = rst.getString("st_gendiarolpag");
                        if ( null==strAux4 || strAux4.equals("N") ) {
                            vecReg.add(INT_TBL_DAT_CHKDIAGEN, false);
                            blnDiaGen=false;
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKDIAGEN, true);
                            blnDiaGen = true;
                        }
                        vecReg.add(INT_TBL_DAT_FECDIAGEN, rst.getString("fe_gendiarolpag"));
                        vecReg.add(INT_TBL_DAT_NUMDIAGEN, rst.getString("ne_diagen"));
                        vecReg.add(INT_TBL_DAT_NUMDIAPRO,rst.getString("ne_diapro"));
                        
                      

                        arrLstAni.add(i, rst.getString("anio"));
                        arrLstMes.add(i, rst.getString("mes"));

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
             //Deshabilitar el btn de eliminar si el asiento esta generado
            if (blnDiaGen || blnDiaBorr ){ 
                bntEliminar.setVisible(false);
                bntEliminar.setEnabled(false); 
            }else{
                bntEliminar.setVisible(true);
                bntEliminar.setEnabled(true); 
            }
             //Deshabilitar el btn de Generar asientos  si el asiento esta generado
            // DESCOMENTAR
//             if (blnDiaGen){ 
//                bntGenAsiento.setVisible(false);
//                bntGenAsiento.setEnabled(false); 
//            }else{
//                 bntGenAsiento.setVisible(true);
//                bntGenAsiento.setEnabled(true); 
//             }
             
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
    
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

                //Pre cargar mes
                for (int i = 0; i < cboPerMes.getItemCount(); i++) {
                        cboPerMes.setSelectedIndex(i);
                        break;
                }
                //Pre cargar Periodo
                for (int i = 0; i < cboPer.getItemCount(); i++) {
                        cboPer.setSelectedIndex(i);
                        break;
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
}
