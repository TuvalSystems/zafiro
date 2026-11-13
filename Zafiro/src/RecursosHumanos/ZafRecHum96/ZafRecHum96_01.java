package RecursosHumanos.ZafRecHum96;

import RecursosHumanos.ZafRecHum30.*;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Jean Paúl Morán
 */
public class ZafRecHum96_01 extends javax.swing.JDialog 
{

    private ZafRecHum96 objZafRecHum96;

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkAut;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiChk objTblCelEdiChkDen;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private ArrayList arlParDlg;                                //Parametros a pasar al JDialog que se llama desde el JTable.
    //Variables de la clase.
    private int intOpcSelDlg;                                   //Opción seleccionada en el JDialog.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    private int intNumDoc;
    private String strFecha = "";
    private int intCodAut;                                      //Código de la autorización.
    private int intCodCli;                                      //Código del cliente.
    private String strIdeCli;                                   //Identificación del cliente.
    private String strEstDoc;                                   //Estado del documento.
    private String strEstValDoc;                                //Estado de validez del documento.

    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                   //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                 //Renderer de Check Box

    // TABLA DE DATOS
    private final int INT_TBL_LIN = 0;
    private final int INT_TBL_CODUSR = 1;
    private final int INT_TBL_USR = 2;
    private final int INT_TBL_NOMUSR = 3;
    
    private final int INT_TBL_CHKAUT = 4;
    private final int INT_TBL_CHKDEN = 5;
   
    private final int INT_TBL_HORENT = 6;
    private final int INT_TBL_HORSAL = 7;
    private final int INT_TBL_OBS = 8;
    private final int INT_TBL_BUTOBS = 9;
    private final int INT_TBL_COREG = 10;
    private final int INT_TBL_NEJERAUT = 11;
    private final int INT_TBL_STOBLSEGJERAUT = 12;

    //private Vector vecDat, vecReg;
    String strCodEmp = "";
    String strCodLoc = "";
    String strCodTipdoc = "";
    String strCodDoc = "";
    String strNumDoc = "";
    String strHorSupExt = "";
    int intDiaSem = 0;

    /**
     * Creates new form ZafHer03_01
     */
    public ZafRecHum96_01(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        initComponents();
        vecDat = new Vector();
        //Inicializar objetos.
        objParSis = obj;
        intOpcSelDlg = 0; 
        
        if (!configurarFrm()) {
            exitForm();
        }
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
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (objThrGUI == null) {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(0);
            objThrGUI.start();
        }
    }//GEN-LAST:event_butAceActionPerformed

  
    
    public boolean guardarDat(java.sql.Connection conn)
    {
        boolean blnRes = false;

        try
        {
            if (conn != null)
            {
                conn.setAutoCommit(false);

                if (!guardarAutorizaciones(conn))
                {
                    conn.rollback();
                    return false;
                }

                String strEstado = obtenerEstadoFinal(conn);

                if (!actualizarCabecera(conn,strEstado))
                {
                    conn.rollback();
                    return false;
                }

                if (strEstado.equals("T"))
                {
                    if (!aplicarMarcaciones(conn))
                    {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();

                blnRes = true;
            }

        }
        catch (Exception Evt)
        {
            try
            {
                conn.rollback();
            }
            catch (Exception e)
            {
            }

            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    /*
    
    */
    
    private boolean guardarAutorizaciones(java.sql.Connection conn)
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        String strSql = "";
        
        try
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                for (int intFil = 0;intFil < tblDat.getRowCount();intFil++)
                {
                    if (tblDat.getValueAt(intFil,INT_TBL_LIN).toString().compareTo("M") == 0)
                    {
                        /* ENTRADA */
                        Object objHoEnt = tblDat.getValueAt(intFil,INT_TBL_HORENT);

                        String strHoEnt = "null";
                        if (objHoEnt != null)
                        {
                            if (!objHoEnt.toString().equals(""))
                            {
                                strHoEnt ="'" + objHoEnt.toString() + "'";
                            }
                        }

                        /* SALIDA */
                        Object objHoSal = tblDat.getValueAt(intFil,INT_TBL_HORSAL);

                        String strHoSal = "null";

                        if (objHoSal != null)
                        {
                            if (!objHoSal.toString().equals(""))
                            {
                                strHoSal ="'"+ objHoSal.toString()+ "'";
                            }
                        }

                        /* OBSERVACION */
                        Object objObs = tblDat.getValueAt(intFil,INT_TBL_OBS);

                        String strObs = "null";

                        if (objObs != null)
                        {
                            if (!objObs.toString().equals(""))
                            {
                                strObs = objUti.codificar(objObs.toString());
                            }
                        }

                        /* ESTADO */
                        String strAut = "";

                        if (tblDat.getValueAt(intFil,INT_TBL_CHKAUT).equals(true))
                        {
                            strAut = "'A'";
                        }

                        if (tblDat.getValueAt(intFil,INT_TBL_CHKDEN).equals(true))
                        {
                            strAut = "'D'";
                            strHoEnt = "null";
                            strHoSal = "null";
                        }

                        if (tblDat.getValueAt(intFil,INT_TBL_CHKAUT).equals(false) &&
                            tblDat.getValueAt(intFil,INT_TBL_CHKDEN).equals(false))
                        {
                            mostrarMsgInf("NO HA INGRESADO SI AUTORIZA O DENIEGA LA SOLICITUD");
                            return false;
                        }

                        /* SOLO AUTORIZADOR */
                        if (objParSis.getCodigoUsuario() == Integer.valueOf(tblDat.getValueAt(intFil,INT_TBL_CODUSR).toString()) ||
                            objParSis.getCodigoUsuario() == 1)
                        {

                            strSql =
                                    "UPDATE tbm_autsolmar SET "
                                    + "st_aut="
                                    + strAut
                                    + ", "
                                    + "ho_autent="
                                    + strHoEnt
                                    + ", "
                                    + "ho_autsal="
                                    + strHoSal
                                    + ", "
                                    + "tx_obsaut="
                                    + strObs
                                    + ", "
                                    + "fe_aut=CURRENT_TIMESTAMP, "
                                    + "co_usraut="
                                    + objParSis.getCodigoUsuario()
                                    + ", "
                                    + "tx_comaut='"
                                    + objParSis.getDireccionIP()
                                    + "' "
                                    + "WHERE co_emp="
                                    + intCodEmp
                                    + " AND co_loc="
                                    + intCodLoc
                                    + " AND co_tipdoc="
                                    + intCodTipDoc
                                    + " AND co_doc="
                                    + intCodDoc
                                    + " AND co_reg="
                                    + tblDat.getValueAt(intFil,INT_TBL_COREG).toString();

                            System.out.println("guardarAutorizaciones: "+ strSql);

                            stmLoc.executeUpdate(strSql);

                            /* ACTUALIZA CABECERA HORAS */
                            strSql =
                                    "UPDATE tbm_cabsolmar SET "
                                    + "ho_autent="
                                    + strHoEnt
                                    + ", "
                                    + "ho_autsal="
                                    + strHoSal
                                    + " "
                                    + "WHERE co_emp="
                                    + intCodEmp
                                    + " AND co_loc="
                                    + intCodLoc
                                    + " AND co_tipdoc="
                                    + intCodTipDoc
                                    + " AND co_doc="
                                    + intCodDoc;

                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }

                stmLoc.close();
                stmLoc = null;

                blnRes = true;
            }

        }
        catch (java.sql.SQLException Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
    private String obtenerEstadoFinal(java.sql.Connection conn)
    {
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql = "";
        String strEstado = "P";

        try
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                /* DENEGADAS */
                strSql =
                        "SELECT COUNT(*) AS total "
                        + "FROM tbm_autsolmar "
                        + "WHERE co_emp="
                        + intCodEmp
                        + " AND co_loc="
                        + intCodLoc
                        + " AND co_tipdoc="
                        + intCodTipDoc
                        + " AND co_doc="
                        + intCodDoc
                        + " AND st_aut='D'";

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next())
                {
                    if (rstLoc.getInt("total") > 0)
                    {
                        rstLoc.close();
                        rstLoc = null;

                        stmLoc.close();
                        stmLoc = null;

                        return "D";
                    }
                }

                rstLoc.close();
                rstLoc = null;

                /* TOTAL AUTORIZADORES */
                int intTotAut = 0;

                strSql =
                        "SELECT COUNT(*) AS total "
                        + "FROM tbm_autsolmar "
                        + "WHERE co_emp="
                        + intCodEmp
                        + " AND co_loc="
                        + intCodLoc
                        + " AND co_tipdoc="
                        + intCodTipDoc
                        + " AND co_doc="
                        + intCodDoc;

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next())
                {
                    intTotAut = rstLoc.getInt("total");
                }

                rstLoc.close();
                rstLoc = null;

                /* TOTAL AUTORIZADOS */
                int intTotApr = 0;

                strSql =
                        "SELECT COUNT(*) AS total "
                        + "FROM tbm_autsolmar "
                        + "WHERE co_emp="
                        + intCodEmp
                        + " AND co_loc="
                        + intCodLoc
                        + " AND co_tipdoc="
                        + intCodTipDoc
                        + " AND co_doc="
                        + intCodDoc
                        + " AND st_aut='A'";

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next())
                {
                    intTotApr =  rstLoc.getInt("total");
                }

                rstLoc.close();
                rstLoc = null;

                /* ESTADO FINAL */
                if (intTotApr == 0)
                {
                    strEstado = "P";
                }
                else if (intTotApr < intTotAut)
                {
                    strEstado = "A";
                }
                else if (intTotApr == intTotAut)
                {
                    strEstado = "T";
                }

                stmLoc.close();
                stmLoc = null;
            }

        }
        catch (java.sql.SQLException Evt)
        {
            Evt.printStackTrace();

            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            Evt.printStackTrace();

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return strEstado;
    }
    
    private boolean actualizarCabecera(java.sql.Connection conn,String strEstado)
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        String strSql = "";

        try
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                /* DENEGADA */
                if (strEstado.equals("D"))
                {
                    strSql =
                            "UPDATE tbm_cabsolmar SET "
                            + "st_aut='D', "
                            + "ho_autent=NULL, "
                            + "ho_autsal=NULL, "
                            + "fe_aut=NULL "
                            + "WHERE co_emp="
                            + intCodEmp
                            + " AND co_loc="
                            + intCodLoc
                            + " AND co_tipdoc="
                            + intCodTipDoc
                            + " AND co_doc="
                            + intCodDoc;
                }

                /* PARCIAL */
                else if (strEstado.equals("A"))
                {
                    strSql =
                            "UPDATE tbm_cabsolmar SET "
                            + "st_aut='A' "
                            + "WHERE co_emp="
                            + intCodEmp
                            + " AND co_loc="
                            + intCodLoc
                            + " AND co_tipdoc="
                            + intCodTipDoc
                            + " AND co_doc="
                            + intCodDoc;
                }

                /* TOTAL */
                else if (strEstado.equals("T"))
                {
                    strSql =
                            "UPDATE tbm_cabsolmar SET "
                            + "st_aut='T', "
                            + "fe_aut=CURRENT_TIMESTAMP "
                            + "WHERE co_emp="
                            + intCodEmp
                            + " AND co_loc="
                            + intCodLoc
                            + " AND co_tipdoc="
                            + intCodTipDoc
                            + " AND co_doc="
                            + intCodDoc;
                }

                /* PENDIENTE */
                else
                {
                    strSql =
                            "UPDATE tbm_cabsolmar SET "
                            + "st_aut='P' "
                            + "WHERE co_emp="
                            + intCodEmp
                            + " AND co_loc="
                            + intCodLoc
                            + " AND co_tipdoc="
                            + intCodTipDoc
                            + " AND co_doc="
                            + intCodDoc;
                }

                System.out.println("actualizarCabecera: "+ strSql);

                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc = null;

                blnRes = true;
            }

        }
        catch (java.sql.SQLException Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
    private boolean aplicarMarcaciones(java.sql.Connection conn)
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmCab = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstCab = null;
        String strSql = "";

        try
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                stmCab = conn.createStatement();
                
                 String strObsMar = "Marcación agregada desde Solicitud " + intCodDoc;

                /* OBTENER HORAS AUTORIZADAS */
                String strHoEnt = null;

                String strHoSal = null;

                strSql =
                        "SELECT fe_sol, "
                        + "ho_autent, "
                        + "ho_autsal "
                        + "FROM tbm_cabsolmar "
                        + "WHERE co_emp="
                        + intCodEmp
                        + " AND co_loc="
                        + intCodLoc
                        + " AND co_tipdoc="
                        + intCodTipDoc
                        + " AND co_doc="
                        + intCodDoc;

                rstCab = stmCab.executeQuery(strSql);

                String strFechaSol = "";

                if (rstCab.next())
                {
                    strFechaSol =  rstCab.getString("fe_sol");
                    strHoEnt = rstCab.getString("ho_autent");
                    strHoSal = rstCab.getString("ho_autsal");
                }

                rstCab.close();
                rstCab = null;

                /* EMPLEADOS */
                strSql =
                        "SELECT co_tra "
                        + "FROM tbm_detsolmar "
                        + "WHERE co_emp="
                        + intCodEmp
                        + " AND co_loc="
                        + intCodLoc
                        + " AND co_tipdoc="
                        + intCodTipDoc
                        + " AND co_doc="
                        + intCodDoc;

                rstLoc = stmLoc.executeQuery(strSql);

                while (rstLoc.next())
                {
                    String strCoTra =
                            rstLoc.getString("co_tra");

                    /* EXISTE ASISTENCIA */
                    strSql =
                            "SELECT * "
                            + "FROM tbm_cabconasitra "
                            + "WHERE fe_dia='"
                            + strFechaSol
                            + "' "
                            + "AND co_tra="
                            + strCoTra;

                    rstCab = stmCab.executeQuery(strSql);

                    if (rstCab.next())
                    {
                        /* UPDATE */
                        String strUpd = "";

                        if (strHoEnt != null)
                        {
                            strUpd +="ho_ent='" + strHoEnt + "'";
                        }

                        if (strHoSal != null)
                        {
                            if (!strUpd.equals(""))
                            {
                                strUpd += ", ";
                            }

                            strUpd += "ho_sal='" + strHoSal + "'";
                        }
                        
                        if (!strUpd.equals(""))
                        {
                            strUpd += ", st_jusmarinc='S'";

                            strUpd += ", tx_obsjusmarinc='" + strObsMar + "'";
                        }
                        

                        if (!strUpd.equals(""))
                        {
                            strSql =
                                    "UPDATE tbm_cabconasitra SET "
                                    + strUpd
                                    + " "
                                    + "WHERE fe_dia='"
                                    + strFechaSol
                                    + "' "
                                    + "AND co_tra="
                                    + strCoTra;

                            System.out.println("UPDATE MARCACION: "+ strSql);

                            stmCab.executeUpdate(strSql);
                        }
                    }
                    else
                    {
                        /* INSERT */
                        String strCampos = "";
                        String strValores = "";

                        if (strHoEnt != null)
                        {
                            strCampos += "ho_ent";
                            strValores += "'" + strHoEnt + "'";
                        }

                        if (strHoSal != null)
                        {
                            if (!strCampos.equals(""))
                            {
                                strCampos += ", ";
                                strValores += ", ";
                            }

                            strCampos += "ho_sal";

                            strValores += "'" + strHoSal + "'";
                        }
                        
                        if (!strCampos.equals(""))
                        {
                            strCampos += ", ";
                            strValores += ", ";
                        }

                        strCampos += "st_jusmarinc, tx_obsjusmarinc";

                        strValores += "'S', '" + strObsMar + "'";
                        

                        strSql =
                                "INSERT INTO tbm_cabconasitra "
                                + "("
                                + "co_tra, "
                                + "fe_dia"
                                + (
                                        strCampos.equals("")
                                        ? ""
                                        : ", " + strCampos
                                )
                                + ") "
                                + "VALUES "
                                + "("
                                + strCoTra
                                + ", '"
                                + strFechaSol
                                + "'"
                                + (
                                        strValores.equals("")
                                        ? ""
                                        : ", " + strValores
                                )
                                + ")";

                        System.out.println("INSERT MARCACION: "+ strSql);
                        stmCab.executeUpdate(strSql);
                    }

                    rstCab.close();
                    rstCab = null;
                }

                rstLoc.close();
                rstLoc = null;

                stmLoc.close();
                stmLoc = null;

                stmCab.close();
                stmCab = null;

                blnRes = true;
            }

        }
        catch (java.sql.SQLException Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            Evt.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
    private boolean validarFechaSincronizada()
    {
        boolean blnRes = true;

        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        ResultSet rstCab = null;

        try
        {
            
            conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            stm = conn.createStatement();
            String strSql =
                "SELECT MAX(fe_dia) AS fe_ult "
                + "FROM tbm_cabconasitra";

            stm = conn.createStatement();
            rst = stm.executeQuery(strSql);

            java.sql.Date datUltSin = null;

            if (rst.next())
            {
                datUltSin = rst.getDate("fe_ult");
            }

            strSql =
                "SELECT fe_sol "
                + "FROM tbm_cabsolmar "
                + "WHERE co_emp=" + intCodEmp
                + " AND co_loc=" + intCodLoc
                + " AND co_tipdoc=" + intCodTipDoc
                + " AND co_doc=" + intCodDoc;

            rstCab = stm.executeQuery(strSql);

            if (rstCab.next())
            {
                java.sql.Date datSol =
                    rstCab.getDate("fe_sol");

                if (datUltSin != null
                        && datSol.after(datUltSin))
                {
                    blnRes = false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes = false;
        }
        finally
        {
            try { if (rst != null) rst.close(); } catch (Exception e) {}
            try { if (rstCab != null) rstCab.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
        }

        return blnRes;
    }
    
    
    private boolean validarRolEnProceso()
    {
        boolean blnRes = true;

        java.sql.Connection conn = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        java.sql.ResultSet rstCab = null;

        try
        {
            conn = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos());

            stm = conn.createStatement();

            /* OBTENER FECHA DE LA SOLICITUD */
            String strSql =
                    "SELECT fe_sol "
                    + "FROM tbm_cabsolmar "
                    + "WHERE co_emp=" + intCodEmp
                    + " AND co_loc=" + intCodLoc
                    + " AND co_tipdoc=" + intCodTipDoc
                    + " AND co_doc=" + intCodDoc;

            rstCab = stm.executeQuery(strSql);

            if (rstCab.next())
            {
                String strFechaSol = rstCab.getString("fe_sol");

                rstCab.close();
                rstCab = null;

                /* OBTENER EL MES DE ROL AL QUE PERTENECE LA FECHA */
                strSql =
                        "SELECT ne_ani, ne_mes "
                        + "FROM tbm_feccorrolpag "
                        + "WHERE co_emp=" + intCodEmp
                        + " AND '" + strFechaSol + "' BETWEEN fe_des AND fe_has";

                System.out.println("PeriodoRol: " + strSql);

                rst = stm.executeQuery(strSql);

                if (rst.next())
                {
                    int intAni = rst.getInt("ne_ani");
                    int intMes = rst.getInt("ne_mes");

                    rst.close();
                    rst = null;

                    /* VALIDAR SI EL PERIODO 2 DE ESE MES ESTA EN REVISION */
                    strSql =
                            "SELECT 1 "
                            + "FROM tbm_feccorrolpag "
                            + "WHERE co_emp=" + intCodEmp
                            + " AND ne_ani=" + intAni
                            + " AND ne_mes=" + intMes
                            + " AND ne_per=2 "
                            + " AND st_revfal='S'";

                    System.out.println("validarRolEnProceso: " + strSql);

                    rst = stm.executeQuery(strSql);

                    if (rst.next())
                    {
                        blnRes = false;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes = false;
        }
        finally
        {
            try { if (rst != null) rst.close(); } catch (Exception e) {}
            try { if (rstCab != null) rstCab.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return blnRes;
    }
    
   
    public static java.sql.Time SparseToTime(String hora) {
        int h, m, s;
        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        s = Integer.parseInt(hora.charAt(6) + "" + hora.charAt(7));
        return (new java.sql.Time(h, m, s));
    }

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg = 0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        intOpcSelDlg = 0;
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            objUti = new ZafUtil();
            strAux = "Listado de usuarios que pueden autorizar";
            this.setTitle(strAux + " v1.0");
            lblTit.setText(strAux);
            arlParDlg = new ArrayList();
            
            // Solo mostrar el botón de autorización masiva para los usuarios 1, 156 y 493
            
            
            //Configurar los JTables.
            configurarTblDat();
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
    private boolean configurarTblDat() {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CODUSR, "Cód.Usr.");
        vecCab.add(INT_TBL_USR, "Usuario");
        vecCab.add(INT_TBL_NOMUSR, "Nombre");
        vecCab.add(INT_TBL_CHKAUT, "Autorizar");
        vecCab.add(INT_TBL_CHKDEN, "Denegar");
        vecCab.add(INT_TBL_HORENT, "Hor.Ent.Aut.");
        vecCab.add(INT_TBL_HORSAL, "Hor.Sal.Aut.");
        vecCab.add(INT_TBL_OBS, "Observación");
        vecCab.add(INT_TBL_BUTOBS, "...");
        vecCab.add(INT_TBL_COREG, "Cód. Reg.");
        vecCab.add(INT_TBL_NEJERAUT, "Jer. Aut.");
        vecCab.add(INT_TBL_STOBLSEGJERAUT, "Obl. Jer.");

        objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux = new java.util.ArrayList();
        arlAux.add("" + INT_TBL_HORENT);
        arlAux.add("" + INT_TBL_HORSAL);
        objTblMod.setColumnasObligatorias(arlAux);
        arlAux = null;
        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODUSR).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_USR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(200);
        tcmAux.getColumn(INT_TBL_CHKAUT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CHKDEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_HORENT).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_HORSAL).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NEJERAUT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setPreferredWidth(50);

        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_CODUSR, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_NEJERAUT, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_STOBLSEGJERAUT, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_CHKDEN, tblDat);
        

        ArrayList arlColHid = new ArrayList();
        arlColHid.add("" + INT_TBL_CODUSR);

    //objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        //arlColHid=null;
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda = new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKAUT);
        vecAux.add("" + INT_TBL_CHKDEN);
        //vecAux.add("" + INT_TBL_HORENT);
        //vecAux.add("" + INT_TBL_HORSAL);
        vecAux.add("" + INT_TBL_BUTOBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux = null;

        if (objParSis.getCodigoUsuario() == 1) {

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();                    
                                       
                    //obtener el codigo usuario y validar 
                    int codusuinises=objParSis.getCodigoUsuario();
                    String codusuaut = (tblDat.getValueAt(intFilSel, INT_TBL_CODUSR) == null ? "" : tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString());
                     
                    if (codusuaut.equals(String.valueOf(codusuinises))){
                        tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKAUT);
                        tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKDEN);
                    }else{
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                    
                }
            });

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk2 = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellEditor(objTblCelEdiChk2);
            objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                     //obtener el codigo usuario y validar 
                    int codusuinises=objParSis.getCodigoUsuario();
                    String codusuaut = (tblDat.getValueAt(intFilSel, INT_TBL_CODUSR) == null ? "" : tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString());
                     
                    if (codusuaut.equals(String.valueOf(codusuinises))){
                        tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKAUT);
                        tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKDEN);
                    }else{
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum96_01.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORSAL).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_HORENT).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFilSel = tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM = 0;
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;
                    try {

                        switch (tblDat.getSelectedColumn()) {
                            case INT_TBL_HORENT:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORENT));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                            case INT_TBL_HORSAL:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORSAL));
                                intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                break;
                        }

                        if ((intHH >= 0 && intHH <= 24)) {
                            if (!(intMM >= 0 && intMM <= 59)) {
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                    objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                }
                            } else {

                                String strHorEnt = "";
                                java.sql.Time t = null;

                                switch (tblDat.getSelectedColumn()) {
                                    case INT_TBL_HORENT:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORENT));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;
                                    case INT_TBL_HORSAL:
                                        strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_HORSAL));
                                        if (strHorEnt.length() == 5) {
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        t = SparseToTime(strHorEnt);
                                        break;
                                }
                            }
                        } else {

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora ingresada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }
            });
        } else {

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKAUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                        //Validar que exista el cliente para la empresa especificada.
                        Object objDen = objTblMod.getValueAt(intFilSel, INT_TBL_CHKDEN);
                        boolean blnDen = (objDen != null && !objDen.toString().isEmpty());

                       // boolean blnDen = (Boolean) objDen;

                        Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);
                        boolean blnAut = (obj != null && !obj.toString().isEmpty());

                       // boolean blnAut = (Boolean) obj;
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();

                        /**
                         * ****************************************************************************************
                         */
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk.setCancelarEdicion(true);
                        }
                    }
                    
                     // VALIDAR FECHA SINCRONIZADA
                    if (!validarFechaSincronizada())
                    {
                        String strTit = "Mensaje del sistema Zafiro";

                        String strMen =
                            "Aún no se han sincronizado las marcaciones "
                            + "del día de la solicitud.\n\n"
                            + "Por favor intente nuevamente más tarde.";

                        JOptionPane.showMessageDialog(
                                ZafRecHum96_01.this,
                                strMen,
                                strTit,
                                JOptionPane.INFORMATION_MESSAGE);

                        objTblCelEdiChk.setCancelarEdicion(true);

                        return;
                    }                  
                    //
                    //validar fecha solicitud con proceso del rol en curso
                    if (!validarRolEnProceso())
                    {
                        String strTit = "Mensaje del sistema Zafiro";

                        String strMen =
                                "El rol correspondiente a la fecha solicitada "
                                + "ya se encuentra en proceso\n"
                                + "o ya está cerrado.\n\n"
                                + "Por favor comunicarse con RRHH "
                                + "para gestionar la novedad.";

                        JOptionPane.showMessageDialog(
                                ZafRecHum96_01.this,
                                strMen,
                                strTit,
                                JOptionPane.INFORMATION_MESSAGE);

                        objTblCelEdiChk.setCancelarEdicion(true);

                        return;
                    }
                    //

                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {

                        boolean blnActUsr = true;
                        boolean bln = false;
                        Boolean blnsTOBLSEGJERAUT = (Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {

                            int intNejerautSel = Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString());
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario()) {

                                if (intNejerautSel != 1) {
                                    for (int i = intFilSel; i <= 0; i--) {

                                        if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString()) < intNejerautSel) {
                                            bln = true;
                                            blnActUsr = false;
                                        }
                                    }
                                }

                            } else {
                                blnActUsr = false;
                            }

                            if (!blnActUsr) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                    if (!bln) {
                                        String strTit = "Mensaje del sistema Zafiro";
                                        String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                                        JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                objTblCelEdiChk.setCancelarEdicion(true);
                            }
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    tblDat.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_CHKAUT);
                    tblDat.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_CHKDEN);
                }

            });

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk2 = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKDEN).setCellEditor(objTblCelEdiChk2);
            objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) {
                    //intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                        //Validar que exista el cliente para la empresa especificada.
                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);  
                        boolean blnAut = (objAut != null && !objAut.toString().isEmpty());

                        Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKDEN);
                        boolean blnDen = (obj != null && !obj.toString().isEmpty());

                       // boolean blnDen = (Boolean) obj;
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk2.setCancelarEdicion(true);
                        }
                    }

                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {

                        boolean blnActUsr = true;
                        boolean bln = false;
                        Boolean blnsTOBLSEGJERAUT = (Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {

                            int intNejerautSel = Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString());
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario()) {

                                if (intNejerautSel != 1) {
                                    for (int i = intFilSel; i <= 0; i--) {

                                        if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_NEJERAUT).toString()) < intNejerautSel) {
                                            bln = true;
                                            blnActUsr = false;
                                        }
                                    }
                                }

                            } else {
                                blnActUsr = false;
                            }

                            if (!blnActUsr) {
                                objTblCelEdiChk2.setCancelarEdicion(true);
                            } else {
                                if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                    if (!bln) {
                                        String strTit = "Mensaje del sistema Zafiro";
                                        String strMen = "Solo puede denegar el usuario loggeado. Revisar e intentar nuevamente.";
                                        JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                        objTblCelEdiChk2.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Solo puede denegar el usuario loggeado. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum96_01.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                objTblCelEdiChk2.setCancelarEdicion(true);
                            }
                        }

                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();
                    tblDat.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKAUT);
                    tblDat.setValueAt(Boolean.TRUE, intFil, INT_TBL_CHKDEN);
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBS, "Observación") {

                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum96_01.this), true, strObs);

                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {

                        if (objParSis.getCodigoUsuario() == 1) {

                        }
                        if (objTblMod.getValueAt(intSelFil, INT_TBL_LIN).toString().compareTo("B") != 0) {
                            if (Integer.valueOf(tblDat.getValueAt(intSelFil, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario() || objParSis.getCodigoUsuario() == 1) {
                                tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                            }
                        }
                    }
                }
            };

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_HORENT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_HORSAL).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {} );
        }

        objTblCelRenLbl = new ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
        tcmAux.getColumn(INT_TBL_HORENT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_HORSAL).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl = null;

        objTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellRenderer(objTblCelRenChkLab);
        ZafTblCelEdiChk objTblCelEdiChkST = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_STOBLSEGJERAUT).setCellEditor(objTblCelEdiChkST);

        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        return blnres;
    }

    public boolean cargarReg()
    {
        java.sql.Connection con = null;

        java.sql.Statement stm;

        java.sql.ResultSet rst;

        boolean blnRes = true;

        String strSQL = "";

        try
        {
            con = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos()
            );

            if (con != null)
            {
                stm = con.createStatement();

                /* AUTORIZADOS */
                if (strEstDoc.compareTo("T") == 0)
                {
                    strSQL  = " SELECT DISTINCT a.* , ";
                    strSQL += " b.co_usr, ";
                    strSQL += " b.tx_usr, ";
                    strSQL += " b.tx_nom , ";
                    strSQL += " d.* ";
                    strSQL += " FROM tbm_autsolmar a ";

                    strSQL += " INNER JOIN tbm_cabsolmar c ";
                    strSQL += " ON (c.co_emp=a.co_emp ";
                    strSQL += " AND c.co_loc=a.co_loc ";
                    strSQL += " AND c.co_tipdoc=a.co_tipdoc ";
                    strSQL += " AND c.co_doc=a.co_doc) ";

                    strSQL += " LEFT OUTER JOIN tbm_usr b ";
                    strSQL += " ON (b.co_usr=a.co_usraut) ";

                    strSQL += " LEFT OUTER JOIN tbm_usrAutSolMarDep d ";
                    strSQL += " ON (d.co_dep=c.co_dep ";
                    strSQL += " AND d.co_usr=a.co_usraut) ";

                    strSQL += " WHERE a.co_emp = "
                            + intCodEmp;

                    strSQL += " AND a.co_loc = "
                            + intCodLoc;

                    strSQL += " AND a.co_tipdoc = "
                            + intCodTipDoc;

                    strSQL += " AND a.co_doc = "
                            + intCodDoc;

                    strSQL += " AND b.tx_usr IS NOT NULL";
                }
                else /* PENDIENTES */
                {

                    strSQL  = " SELECT * FROM ( ";

                    strSQL += " SELECT ";
                    strSQL += " a.*, ";
                    strSQL += " b.co_usr, ";
                    strSQL += " b.tx_usr, ";
                    strSQL += " b.tx_nom, ";
                    strSQL += " d.st_aut, ";
                    strSQL += " e.ho_autent, ";
                    strSQL += " e.ho_autsal, ";
                    strSQL += " d.tx_obsAut ";

                    strSQL += " FROM tbm_cabsolmar e ";

                    strSQL += " INNER JOIN tbm_autsolmar d ";
                    strSQL += " ON (e.co_emp=d.co_emp ";
                    strSQL += " AND e.co_loc=d.co_loc ";
                    strSQL += " AND e.co_tipdoc=d.co_tipdoc ";
                    strSQL += " AND e.co_doc=d.co_doc ) ";

                    strSQL += " INNER JOIN tbm_usr b ";
                    strSQL += " ON (d.co_usraut=b.co_usr) ";

                    strSQL += " INNER JOIN tbm_usrAutSolMarDep a ";
                    strSQL += " ON (b.co_usr=a.co_usr ";
                    strSQL += " AND a.co_dep=e.co_dep) ";

                    strSQL += " WHERE e.co_emp="
                            + intCodEmp;

                    strSQL += " AND e.co_loc="
                            + intCodLoc;

                    strSQL += " AND e.co_tipdoc="
                            + intCodTipDoc;

                    strSQL += " AND e.co_doc="
                            + intCodDoc;

                    strSQL += " AND d.st_aut='A' ";

                    strSQL += " AND e.st_aut!='T' ";

                    strSQL += " UNION ";

                    strSQL += " SELECT ";
                    strSQL += " a.*, ";
                    strSQL += " b.co_usr AS usua, ";
                    strSQL += " b.tx_usr, ";
                    strSQL += " b.tx_nom, ";
                    strSQL += " d.st_aut, ";
                    strSQL += " e.ho_autent, ";
                    strSQL += " e.ho_autsal, ";
                    strSQL += " d.tx_obsAut ";

                    strSQL += " FROM tbm_cabsolmar e ";

                    strSQL += " INNER JOIN tbm_autsolmar d ";
                    strSQL += " ON (e.co_emp=d.co_emp ";
                    strSQL += " AND e.co_loc=d.co_loc ";
                    strSQL += " AND e.co_tipdoc=d.co_tipdoc ";
                    strSQL += " AND e.co_doc=d.co_doc ) ";

                    strSQL += " LEFT OUTER JOIN tbm_usrAutSolMarDep a ";
                    strSQL += " ON (a.co_dep=e.co_dep ";
                    strSQL += " AND d.co_reg=a.co_reg ";

                    strSQL += " AND a.co_usr NOT IN ";

                    strSQL += " (SELECT co_usraut ";
                    strSQL += " FROM tbm_autsolmar ";
                    strSQL += " WHERE co_emp="
                            + intCodEmp;

                    strSQL += " AND co_loc="
                            + intCodLoc;

                    strSQL += " AND co_tipdoc="
                            + intCodTipDoc;

                    strSQL += " AND co_doc="
                            + intCodDoc;

                    strSQL += " AND st_aut='A')) ";

                    strSQL += " LEFT OUTER JOIN tbm_usr b ";
                    strSQL += " ON (a.co_usr=b.co_usr) ";

                    strSQL += " WHERE e.co_emp="
                            + intCodEmp;

                    strSQL += " AND e.co_loc="
                            + intCodLoc;

                    strSQL += " AND e.co_tipdoc="
                            + intCodTipDoc;

                    strSQL += " AND e.co_doc="
                            + intCodDoc;

                    strSQL += " AND d.st_aut='P' ";

                    strSQL += " AND e.st_aut!='T' ";

                    strSQL += " ) a ";

                    strSQL += " ORDER BY a.ne_jeraut, a.co_reg ";
                }

                vecDat.clear();

                int intCantAut = 0;

                int intCantUsrAut = 0;

                System.out.println(
                        "cargarReg() Marcaciones: "
                        + strSQL
                );

                rst = stm.executeQuery(strSQL);

                boolean blq = false;

                while (rst.next())
                {
                    vecReg = new Vector();

                    String strAut =
                            rst.getString("st_aut");

                    vecReg.add(INT_TBL_LIN, "");

                    vecReg.add(INT_TBL_CODUSR,
                            rst.getString("co_usr"));

                    intCantUsrAut++;

                    vecReg.add(INT_TBL_USR,
                            rst.getString("tx_usr"));

                    vecReg.add(INT_TBL_NOMUSR,
                            rst.getString("tx_nom"));

                    if (strAut == null)
                    {
                        vecReg.add(INT_TBL_CHKAUT,
                                Boolean.FALSE);

                        vecReg.add(INT_TBL_CHKDEN,
                                Boolean.FALSE);

                    }
                    else
                    {
                        if (strAut.compareTo("A") == 0)
                        {
                            vecReg.add(INT_TBL_CHKAUT,
                                    Boolean.TRUE);

                            vecReg.add(INT_TBL_CHKDEN,
                                    Boolean.FALSE);

                            intCantAut++;

                            blq = true;

                        }
                        else if (strAut.compareTo("D") == 0)
                        {
                            vecReg.add(INT_TBL_CHKAUT,
                                    Boolean.FALSE);

                            vecReg.add(INT_TBL_CHKDEN,
                                    Boolean.TRUE);

                            blq = true;

                        }
                        else
                        {
                            vecReg.add(INT_TBL_CHKAUT,
                                    Boolean.FALSE);

                            vecReg.add(INT_TBL_CHKDEN,
                                    Boolean.FALSE);

                            blq = false;
                        }
                    }

                    /* HORAS SOLICITADAS */
                    Object objHorEnt = objZafRecHum96.tblDat.getValueAt(objZafRecHum96.tblDat.getSelectedRow(),objZafRecHum96.INT_TBL_DAT_HORENT);

                    String strHorEnt = "";

                    if (objHorEnt != null)
                    {
                        strHorEnt = objHorEnt.toString();
                    }

                    vecReg.add(INT_TBL_HORENT,strHorEnt);

                    Object objHorSal = objZafRecHum96.tblDat.getValueAt(objZafRecHum96.tblDat.getSelectedRow(),objZafRecHum96.INT_TBL_DAT_HORSAL);

                    String strHorSal = "";

                    if (objHorSal != null)
                    {
                        strHorSal = objHorSal.toString();
                    }

                    vecReg.add(INT_TBL_HORSAL,strHorSal);

                    vecReg.add(INT_TBL_OBS,rst.getString("tx_obsaut"));

                    vecReg.add(INT_TBL_BUTOBS,"...");

                    vecReg.add(INT_TBL_COREG,rst.getString("co_reg"));

                    vecReg.add(INT_TBL_NEJERAUT,rst.getString("ne_jeraut"));

                    boolean bln;

                    String strSt_oblsegjeraut = rst.getString("st_oblgesjeraut");

                    if (strSt_oblsegjeraut == null)
                    {
                        vecReg.add(INT_TBL_STOBLSEGJERAUT,Boolean.FALSE);
                    }
                    else
                    {
                        if (rst.getString("st_oblgesjeraut").compareTo("N") == 0)
                        {
                            bln = Boolean.FALSE;
                        }
                        else
                        {
                            bln = Boolean.TRUE;
                        }

                        vecReg.add(INT_TBL_STOBLSEGJERAUT,bln);
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

                /* BLOQUEOS */
                if (objParSis.getCodigoUsuario() == 1)
                {
                    int intFilMax =
                            objTblMod.getRowCount() - 1;

                    if (intCantUsrAut == intCantAut)
                    {
                        objTblMod.setModoOperacion(
                                ZafTblMod.INT_TBL_NO_EDI
                        );

                        butAce.setEnabled(false);

                    }
                    else if (intCantAut >= 1
                            && intCantAut < intCantUsrAut)
                    {
                        objTblMod.setModoOperacion(
                                ZafTblMod.INT_TBL_EDI
                        );

                        butAce.setEnabled(true);

                        butCan.setEnabled(true);

                    }
                    else
                    {
                        butAce.setEnabled(true);

                        butCan.setEnabled(true);
                    }
                }
                else
                {
                    boolean blnPuedeEditar = false;

                    /* VALIDAR SI EL USUARIO ACTUAL AUN TIENE PENDIENTE AUTORIZAR */
                    for (int i = 0; i < objTblMod.getRowCount(); i++)
                    {
                        int intCodUsrFila =
                                Integer.valueOf(
                                        objTblMod.getValueAt(
                                                i,
                                                INT_TBL_CODUSR
                                        ).toString()
                                );

                        boolean blnAut =
                                (Boolean)objTblMod.getValueAt(
                                        i,
                                        INT_TBL_CHKAUT
                                );

                        boolean blnDen =
                                (Boolean)objTblMod.getValueAt(
                                        i,
                                        INT_TBL_CHKDEN
                                );

                        /* SI ES MI FILA Y TODAVIA NO AUTORIZO */
                        if (intCodUsrFila == objParSis.getCodigoUsuario()
                                && !blnAut
                                && !blnDen)
                        {
                            blnPuedeEditar = true;
                            break;
                        }
                    }

                    /* SI YA NO TIENE NADA PENDIENTE */
                    if (!blnPuedeEditar)
                    {
                        objTblMod.setModoOperacion(
                                ZafTblMod.INT_TBL_NO_EDI
                        );

                        butAce.setEnabled(false);

                        butCan.setEnabled(true);
                    }
                    else
                    {
                        objTblMod.setModoOperacion(
                                ZafTblMod.INT_TBL_EDI
                        );

                        butAce.setEnabled(true);

                        butCan.setEnabled(true);
                    }

                    /* SI LA SOLICITUD YA FUE DENEGADA */
                    boolean blnDen =
                            (Boolean) tblDat.getValueAt(
                                    tblDat.getRowCount() - 1,
                                    INT_TBL_CHKDEN
                            );

                    if (blnDen)
                    {
                        objTblMod.setModoOperacion(
                                ZafTblMod.INT_TBL_NO_EDI
                        );

                        butAce.setEnabled(false);

                        butCan.setEnabled(true);
                    }
                }

                vecDat.clear();
            }

        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, e);

        }
        catch (Exception e)
        {
            e.printStackTrace();

            blnRes = false;

            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            objTblMod.removeAllRows();
            //txaObs1.setText("");
            //txaObs2.setText("");
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si y No. El usuario es quien determina lo que debe hacer el
     * sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
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
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función establece los parámetros que debe utilizar el JDialog.
     *
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código de la cotización/documento.
     * @param intCodCli El código del cliente.
     * @param strIdeCli La identificación del cliente.
     */
    public void setParDlg(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodCli, String strIdeCli, String strEstDoc) 
    {
        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.intCodCli = intCodCli;
        this.strIdeCli = strIdeCli;
        this.strEstDoc = strEstDoc;
    }

    public void setParDlg(ZafParSis objZafParsis, ZafRecHum96 objRecHum96, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, String strFecha, String strEstDoc) 
    {
        this.objParSis = objZafParsis;
        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.strFecha = strFecha;
        this.intNumDoc = intNumDoc;
        this.strEstDoc = strEstDoc;
        this.objZafRecHum96 = objRecHum96;
    }

    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     *
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcSelDlg() 
    {
        return intOpcSelDlg;
    }

    private boolean invocarClase(String clase)
    {
        boolean blnRes = true;
        try 
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen = Class.forName(clase);
            Class objCla[] = new Class[1];
            objCla[0] = arlParDlg.getClass();
            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
            //Inicializar el objeto que tiene los parámetros que se van pasar al JDialog.
            arlParDlg.clear();
            arlParDlg.add(javax.swing.JOptionPane.getFrameForComponent(this));
            arlParDlg.add(objParSis);
            arlParDlg.add("" + intCodEmp);
            arlParDlg.add("" + intCodLoc);
            arlParDlg.add(null);
            arlParDlg.add("" + intCodDoc);
            //Inicializar el constructor que se obtuvo.
            Object objObj[] = new Object[1];
            objObj[0] = arlParDlg;
            javax.swing.JDialog dlgVen;
            dlgVen = (javax.swing.JDialog) objCon.newInstance(objObj);
            dlgVen.setVisible(true);
        } catch (ClassNotFoundException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (NoSuchMethodException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (SecurityException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (InstantiationException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (IllegalAccessException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (IllegalArgumentException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread 
    {

        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun = 0;
        }

        public void run()
        {
            switch (intIndFun) 
            {
                case 0: //Guardar los cambios.

                    if (actualizarReg()) 
                    {
                        mostrarMsgInf("La operación AUTORIZAR/DENEGAR se realizó con éxito.");
                        intOpcSelDlg = 1;
                        dispose();
                    }
            }
            objThrGUI = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta función actualiza el registro en la base de datos.
     *
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() 
    {

        boolean blnRes = false;
        java.sql.Connection conn;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                conn.setAutoCommit(false);
                
                if (guardarDat(conn)) {
                    conn.commit();
                    blnRes = true;
                } else {
                    conn.rollback();
                }

                conn.close();
                conn = null;
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

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_CODUSR:
                    strMsg = "Cód. Usr.";
                    break;
                case INT_TBL_NOMUSR:
                    strMsg = "Nombre del usuario";
                    break;
                case INT_TBL_CHKAUT:
                    strMsg = "Autoriza la solicitud";
                    break;
                case INT_TBL_HORENT:
                    strMsg = "Hora autorizada desde";
                    break;
                case INT_TBL_HORSAL:
                    strMsg = "Hora autorizada hasta";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}
