package RecursosHumanos.ZafRecHum45;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
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
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
 
/**
 *
 * @author Jean Moran
 */
public class ZafRecHum45_02 extends javax.swing.JDialog 
{

    private ZafRecHum45 objZafRecHum45;

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private String  strAux;
    private Vector vecDat,  vecReg;
    //private ArrayList arlParDlg;                                //Parametros a pasar al JDialog que se llama desde el JTable.
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
    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                   //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                 //Renderer de Check Box

    // TABLA DE DATOS
    private final int INT_TBL_LIN = 0;
    private final int INT_TBL_CODUSR = 1;
    private final int INT_TBL_USR = 2;
    private final int INT_TBL_NOMUSR = 3;
    private final int INT_TBL_VALLIQ = 4;
    private final int INT_TBL_CHKAUT = 5;
    private final int INT_TBL_CHKDEN = 6;
    private final int INT_TBL_FECAUT = 7;
    private final int INT_TBL_OBS = 8;
    private final int INT_TBL_BUTOBS = 9;
    private final int INT_TBL_COREG = 10;
    private final int INT_TBL_ORDAUT = 11;
 
    //private Vector vecDat, vecReg;
    String strCodEmp = "";
    String strCodLoc = "";   
    String strCodTra = "";
    String strCodTipdoc = "";
    String strCodDoc = "";
    String strNumDoc = "";
    String strTipoSoli = ""; 
    String strSolAde = "";
    String strcantdiasolvac = "";

    int intDiaSem = 0;
    int contchkAut=0;
    int totnumAut=0;  
    private int intRubVac = 17;  
    private int intRubIng = 40; //agregar jp
    private int intMesPerVac = 24;
    private int intdiaPerVac = 15;  //Dias de permiso por ley  
 

    private double douvalorvac = 0;

    /**
     * Creates new form ZafHer03_01
     */
    public ZafRecHum45_02(java.awt.Frame parent, boolean modal, ZafParSis obj) 
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

    
    
    public boolean guardarAutorizaciones(java.sql.Connection conn)
    {
        boolean blnRes = false;
        boolean blnProcesado = false;

        java.sql.PreparedStatement pstLoc = null;

        try
        {
            for (int intFil = 0;
                    intFil < tblDat.getRowCount();
                    intFil++)
            {
                Object objLin =
                        tblDat.getValueAt(
                                intFil,
                                INT_TBL_LIN
                        );

                String strLin =
                        objLin == null
                        ? ""
                        : objLin.toString();

                /*
                 * Solamente procesar filas modificadas.
                 */
                if ("M".equals(strLin))
                {
                    boolean blnAutoriza =
                            Boolean.TRUE.equals(
                                    tblDat.getValueAt(
                                            intFil,
                                            INT_TBL_CHKAUT
                                    )
                            );

                    boolean blnDeniega =
                            Boolean.TRUE.equals(
                                    tblDat.getValueAt(
                                            intFil,
                                            INT_TBL_CHKDEN
                                    )
                            );

                    /*
                     * Debe seleccionar exactamente
                     * una de las dos opciones.
                     */
                    if (blnAutoriza == blnDeniega)
                    {
                        mostrarMsgInf(
                                "Seleccione Autorizar o Denegar."
                        );

                        return false;
                    }

                    int intCodUsrAut =
                            Integer.parseInt(
                                    tblDat.getValueAt(
                                            intFil,
                                            INT_TBL_CODUSR
                                    ).toString()
                            );

                    /*
                     * Cada usuario solamente puede registrar
                     * su propia decisión.
                     */
                    if (objParSis.getCodigoUsuario()
                            != intCodUsrAut)
                    {
                        mostrarMsgInf(
                                "Sólo el usuario asignado puede "
                                + "autorizar o denegar esta liquidación."
                        );

                        return false;
                    }

                    String strEstado =
                            blnAutoriza
                            ? "A"
                            : "D";

                    Object objObs =
                            tblDat.getValueAt(
                                    intFil,
                                    INT_TBL_OBS
                            );

                    String strObs =
                            objObs == null
                            ? ""
                            : objObs.toString().trim();

                    int intCoReg =
                            Integer.parseInt(
                                    tblDat.getValueAt(
                                            intFil,
                                            INT_TBL_COREG
                                    ).toString()
                            );

                    String strSQL =
                            "UPDATE tbm_autliqhab "
                            + "SET "
                            + "    st_aut = ?, "
                            + "    tx_obsaut = ?, "
                            + "    fe_aut = CURRENT_TIMESTAMP, "
                            + "    tx_comaut = ?, "
                            + "    co_usrmod = ?, "
                            + "    fe_ultmod = CURRENT_TIMESTAMP "
                            + "WHERE co_emp = ? "
                            + "  AND co_loc = ? "
                            + "  AND co_tipdoc = ? "
                            + "  AND co_doc = ? "
                            + "  AND co_reg = ? "
                            + "  AND co_usraut = ? "
                            + "  AND st_reg = 'A' "
                            + "  AND st_aut = 'P'";

                    pstLoc = conn.prepareStatement(
                            strSQL
                    );

                    int intPar = 1;

                    pstLoc.setString(
                            intPar++,
                            strEstado
                    );

                    pstLoc.setString(
                            intPar++,
                            strObs
                    );

                    pstLoc.setString(
                            intPar++,
                            objParSis.getDireccionIP()
                    );

                    pstLoc.setInt(
                            intPar++,
                            objParSis.getCodigoUsuario()
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCodEmp
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCodLoc
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCodTipDoc
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCodDoc
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCoReg
                    );

                    pstLoc.setInt(
                            intPar++,
                            intCodUsrAut
                    );

                    System.out.println(
                            "Guardar autorización liquidación: "
                            + intCodDoc
                            + " - Usuario: "
                            + intCodUsrAut
                            + " - Estado: "
                            + strEstado
                    );

                    int intFilAct =
                            pstLoc.executeUpdate();

                    pstLoc.close();
                    pstLoc = null;

                    if (intFilAct != 1)
                    {
                        mostrarMsgInf(
                                "No se actualizó la autorización. "
                                + "Es posible que ya haya sido procesada."
                        );

                        return false;
                    }

                    blnProcesado = true;
                }
            }

            if (!blnProcesado)
            {
                mostrarMsgInf(
                        "No ha seleccionado Autorizar o Denegar."
                );

                return false;
            }

            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (pstLoc != null)
                {
                    pstLoc.close();
                }
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
    }    
    
    private String obtenerEstadoFinal(
            java.sql.Connection conn)
            throws Exception
    {
        String strEstado = "P";

        java.sql.PreparedStatement pstLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            String strSQL =
                    "SELECT "
                    + "    COUNT(*) AS can_tot, "
                    + "    COALESCE(SUM("
                    + "        CASE WHEN st_aut = 'A' "
                    + "        THEN 1 ELSE 0 END"
                    + "    ),0) AS can_aut, "
                    + "    COALESCE(SUM("
                    + "        CASE WHEN st_aut = 'D' "
                    + "        THEN 1 ELSE 0 END"
                    + "    ),0) AS can_den, "
                    + "    COALESCE(SUM("
                    + "        CASE WHEN st_aut = 'P' "
                    + "        THEN 1 ELSE 0 END"
                    + "    ),0) AS can_pen "
                    + "FROM tbm_autliqhab "
                    + "WHERE co_emp = ? "
                    + "  AND co_loc = ? "
                    + "  AND co_tipdoc = ? "
                    + "  AND co_doc = ? "
                    + "  AND st_reg = 'A'";

            pstLoc = conn.prepareStatement(
                    strSQL
            );

            pstLoc.setInt(
                    1,
                    intCodEmp
            );

            pstLoc.setInt(
                    2,
                    intCodLoc
            );

            pstLoc.setInt(
                    3,
                    intCodTipDoc
            );

            pstLoc.setInt(
                    4,
                    intCodDoc
            );

            rstLoc = pstLoc.executeQuery();

            if (rstLoc.next())
            {
                int intCanTot =
                        rstLoc.getInt("can_tot");

                int intCanAut =
                        rstLoc.getInt("can_aut");

                int intCanDen =
                        rstLoc.getInt("can_den");

                int intCanPen =
                        rstLoc.getInt("can_pen");

                if (intCanTot == 0)
                {
                    throw new Exception(
                            "La liquidación no tiene "
                            + "autorizadores configurados."
                    );
                }

                /*
                 * Una denegación convierte toda
                 * la liquidación en denegada.
                 */
                if (intCanDen > 0)
                {
                    strEstado = "D";
                }
                /*
                 * Todos autorizaron.
                 */
                else if (intCanPen == 0
                        && intCanAut == intCanTot)
                {
                    strEstado = "T";
                }
                /*
                 * Todavía falta al menos un autorizador.
                 */
                else
                {
                    strEstado = "P";
                }
            }
        }
        finally
        {
            if (rstLoc != null)
            {
                rstLoc.close();
            }

            if (pstLoc != null)
            {
                pstLoc.close();
            }
        }

        return strEstado;
    }
    
    private boolean actualizarCabecera(
            java.sql.Connection conn,
            String strEstado)
    {
        boolean blnRes = false;

        java.sql.PreparedStatement pstLoc = null;

        try
        {
            /*
             * Si todavía faltan autorizadores,
             * la cabecera continúa pendiente.
             */
            if ("P".equals(strEstado))
            {
                return true;
            }

            String strSQL =
                    "UPDATE tbm_cabliqhab "
                    + "SET "
                    + "    st_aut = ?, "
                    + "    co_usraut = ?, "
                    + "    fe_aut = CURRENT_TIMESTAMP, "
                    + "    co_usrmod = ?, "
                    + "    fe_ultmod = CURRENT_TIMESTAMP "
                    + "WHERE co_emp = ? "
                    + "  AND co_loc = ? "
                    + "  AND co_tipdoc = ? "
                    + "  AND co_doc = ? "
                    + "  AND st_reg = 'A' "
                    + "  AND st_aut = 'P'";

            pstLoc = conn.prepareStatement(
                    strSQL
            );

            int intPar = 1;

            pstLoc.setString(
                    intPar++,
                    strEstado
            );

            pstLoc.setInt(
                    intPar++,
                    objParSis.getCodigoUsuario()
            );

            pstLoc.setInt(
                    intPar++,
                    objParSis.getCodigoUsuario()
            );

            pstLoc.setInt(
                    intPar++,
                    intCodEmp
            );

            pstLoc.setInt(
                    intPar++,
                    intCodLoc
            );

            pstLoc.setInt(
                    intPar++,
                    intCodTipDoc
            );

            pstLoc.setInt(
                    intPar++,
                    intCodDoc
            );

            int intFilAct =
                    pstLoc.executeUpdate();

            if (intFilAct != 1)
            {
                mostrarMsgInf(
                        "No se actualizó el estado final "
                        + "de la liquidación."
                );

                return false;
            }

            System.out.println(
                    "Estado final liquidación: "
                    + strEstado
            );

            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (pstLoc != null)
                {
                    pstLoc.close();
                }
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
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
           // arlParDlg = new ArrayList();
            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    public void setParDlg(
            ZafParSis objZafParSis,
            ZafRecHum45 objZafRecHum45,
            int intCodEmp,
            int intCodLoc,
            int intCodTipDoc,
            int intCodDoc,
            int intNumDoc,
            String strFecha,
            String strEstDoc)
    {
        this.intOpcSelDlg = 0;

        this.objParSis = objZafParSis;
        this.objZafRecHum45 = objZafRecHum45;

        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.intNumDoc = intNumDoc;

        this.strFecha = strFecha;
        this.strEstDoc = strEstDoc;
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
        vecCab.add(INT_TBL_VALLIQ, "Valor");
        vecCab.add(INT_TBL_CHKAUT, "Autorizar");
        vecCab.add(INT_TBL_CHKDEN, "Denegar");  
        vecCab.add(INT_TBL_FECAUT, "Fec. Aut/Den");
        vecCab.add(INT_TBL_OBS, "Observación");
        vecCab.add(INT_TBL_BUTOBS, "...");
        vecCab.add(INT_TBL_COREG, "Cód. Reg."); 
        vecCab.add(INT_TBL_ORDAUT, "Ord Aut.");
 
        objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux = new java.util.ArrayList();
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
        tcmAux.getColumn(INT_TBL_VALLIQ).setPreferredWidth(100);
        
        tcmAux.getColumn(INT_TBL_CHKAUT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CHKDEN).setPreferredWidth(60);   
        tcmAux.getColumn(INT_TBL_FECAUT).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_ORDAUT).setPreferredWidth(50);

        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_CODUSR, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_ORDAUT, tblDat);

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
        //vecAux.add("" + INT_TBL_HORAUTDES);
        //vecAux.add("" + INT_TBL_HORAUTHAS);
        vecAux.add("" + INT_TBL_BUTOBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux = null;

        objTblCelRenChkLab = new ZafTblCelRenChk();

        tcmAux.getColumn(
                INT_TBL_CHKAUT
        ).setCellRenderer(
                objTblCelRenChkLab
        );

        objTblCelEdiChk = new ZafTblCelEdiChk();

        tcmAux.getColumn(
                INT_TBL_CHKAUT
        ).setCellEditor(
                objTblCelEdiChk
        );

        objTblCelEdiChk.addTableEditorListener(
            new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                int intFilSel = -1;

                public void beforeEdit(
                        Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    intFilSel = tblDat.getSelectedRow();

                    /*
                     * Por defecto se cancela. Solamente se habilita
                     * cuando el usuario conectado es el autorizador.
                     */
                    objTblCelEdiChk.setCancelarEdicion(true);

                    if (intFilSel == -1)
                    {
                        return;
                    }

                    int intCodUsrAut = Integer.parseInt(
                            tblDat.getValueAt(
                                    intFilSel,
                                    INT_TBL_CODUSR
                            ).toString()
                    );

                    if (objParSis.getCodigoUsuario() != intCodUsrAut)
                    {
                        mostrarMsgInf(
                                "Solo puede autorizar el usuario loggeado. "
                                + "Revisar e intentar nuevamente."
                        );

                        tblDat.setValueAt(
                                Boolean.FALSE,
                                intFilSel,
                                INT_TBL_CHKAUT
                        );

                        return;
                    }

                    /*
                     * Usuario correcto: permitir el cambio.
                     */
                    objTblCelEdiChk.setCancelarEdicion(false);
                }

                public void afterEdit(
                        Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    if (intFilSel != -1)
                    {
                        tblDat.setValueAt(
                                Boolean.TRUE,
                                intFilSel,
                                INT_TBL_CHKAUT
                        );

                        tblDat.setValueAt(
                                Boolean.FALSE,
                                intFilSel,
                                INT_TBL_CHKDEN
                        );
                    }
                }
            }
        );
        
        
        objTblCelRenChkLab = new ZafTblCelRenChk();

        tcmAux.getColumn(
                INT_TBL_CHKDEN
        ).setCellRenderer(
                objTblCelRenChkLab
        );

        objTblCelEdiChk2 = new ZafTblCelEdiChk();

        tcmAux.getColumn(
                INT_TBL_CHKDEN
        ).setCellEditor(
                objTblCelEdiChk2
        );

        objTblCelEdiChk2.addTableEditorListener(
            new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                int intFilSel = -1;

                public void beforeEdit(
                        Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    intFilSel = tblDat.getSelectedRow();

                    /*
                     * Por defecto se cancela.
                     */
                    objTblCelEdiChk2.setCancelarEdicion(true);

                    if (intFilSel == -1)
                    {
                        return;
                    }

                    int intCodUsrAut = Integer.parseInt(
                            tblDat.getValueAt(
                                    intFilSel,
                                    INT_TBL_CODUSR
                            ).toString()
                    );

                    if (objParSis.getCodigoUsuario() != intCodUsrAut)
                    {
                        mostrarMsgInf(
                                "Solo puede denegar el usuario loggeado. "
                                + "Revisar e intentar nuevamente."
                        );

                        tblDat.setValueAt(
                                Boolean.FALSE,
                                intFilSel,
                                INT_TBL_CHKDEN
                        );

                        return;
                    }

                    /*
                     * Usuario correcto: permitir el cambio.
                     */
                    objTblCelEdiChk2.setCancelarEdicion(false);
                }

                public void afterEdit(
                        Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    if (intFilSel != -1)
                    {
                        tblDat.setValueAt(
                                Boolean.FALSE,
                                intFilSel,
                                INT_TBL_CHKAUT
                        );

                        tblDat.setValueAt(
                                Boolean.TRUE,
                                intFilSel,
                                INT_TBL_CHKDEN
                        );
                    }
                }
            }
        );
        

        objTblCelRenLbl = new ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
      

        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        return blnres;
    } 
 
    public boolean cargarReg()
    {
        java.sql.Connection conLoc = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        boolean blnRes = true;
        boolean blnUsrLogAut = false;

        String strSQL = "";

        boolean blnChkAut;
        boolean blnChkDen;

        int intCodUsrLog = 0;

        try
        {
            intCodUsrLog = objParSis.getCodigoUsuario();

            contchkAut = 0;
            totnumAut = 0;

            /*
             * Reiniciar la ventana porque se reutiliza
             * para diferentes liquidaciones.
             */
            butAce.setEnabled(true);

            objTblMod.setModoOperacion(
                    ZafTblMod.INT_TBL_EDI
            );

            conLoc = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos()
            );

            if (conLoc != null)
            {
                stmLoc = conLoc.createStatement();

                vecDat = new Vector();

                strSQL = "";

                strSQL += "SELECT ";
                strSQL += "    aut.co_usraut, ";
                strSQL += "    aut.co_reg, ";
                strSQL += "    aut.co_doc, ";
                strSQL += "    aut.st_aut, ";
                strSQL += "    aut.fe_aut, ";
                strSQL += "    aut.tx_obsaut, ";
                strSQL += "    aut.tx_comaut, ";
                strSQL += "    usr.tx_nom AS nomaut, ";
                strSQL += "    usr.tx_usr, ";
                strSQL += "    COALESCE(cab.nd_totliq, 0) AS nd_totliq ";

                strSQL += "FROM tbm_autliqhab AS aut ";

                strSQL += "INNER JOIN tbm_usr AS usr ";
                strSQL += "    ON usr.co_usr = aut.co_usraut ";
                strSQL += "   AND usr.st_reg = 'A' ";
                strSQL += "INNER JOIN tbm_cabliqhab AS cab ";
                strSQL += "    ON cab.co_emp = aut.co_emp ";
                strSQL += "   AND cab.co_loc = aut.co_loc ";
                strSQL += "   AND cab.co_tipdoc = aut.co_tipdoc ";
                strSQL += "   AND cab.co_doc = aut.co_doc ";
                strSQL += "   AND cab.st_reg = 'A' ";

                strSQL += "WHERE aut.co_emp = "
                        + intCodEmp
                        + " ";

                strSQL += "  AND aut.co_loc = "
                        + intCodLoc
                        + " ";

                strSQL += "  AND aut.co_tipdoc = "
                        + intCodTipDoc
                        + " ";

                strSQL += "  AND aut.co_doc = "
                        + intCodDoc
                        + " ";

                strSQL += "  AND aut.st_reg = 'A' ";

                strSQL += "ORDER BY aut.co_reg ASC";

                System.out.println(
                        "cargarReg autorizadores liquidación: "
                        + strSQL
                );

                rstLoc = stmLoc.executeQuery(strSQL);

                while (rstLoc.next())
                {
                    totnumAut++;

                    blnChkAut = false;
                    blnChkDen = false;

                    String strAut =
                            rstLoc.getString("st_aut");

                    /*
                     * Estado individual:
                     *
                     * P = Pendiente
                     * A = Autorizó
                     * D = Denegó
                     */
                    if ("A".equals(strAut))
                    {
                        blnChkAut = true;
                        blnChkDen = false;
                    }
                    else if ("D".equals(strAut))
                    {
                        blnChkAut = false;
                        blnChkDen = true;
                    }

                    /*
                     * Si el usuario conectado ya autorizó
                     * o denegó, no podrá modificar nuevamente.
                     */
                    if (intCodUsrLog
                            == rstLoc.getInt("co_usraut")
                            && !"P".equals(strAut))
                    {
                        blnUsrLogAut = true;
                    }

                    if (!"P".equals(strAut))
                    {
                        contchkAut++;
                    }

                    vecReg = new Vector();

                    vecReg.add(
                            INT_TBL_LIN,
                            ""
                    );

                    vecReg.add(
                            INT_TBL_CODUSR,
                            rstLoc.getInt("co_usraut")
                    );

                    vecReg.add(
                            INT_TBL_USR,
                            rstLoc.getString("tx_usr")
                    );

                    vecReg.add(
                            INT_TBL_NOMUSR,
                            rstLoc.getString("nomaut")
                    );
                    vecReg.add(
                            INT_TBL_VALLIQ,
                            "<html><b>$ "
                            + new java.text.DecimalFormat("###,##0.00")
                                    .format(rstLoc.getBigDecimal("nd_totliq"))
                            + "</b></html>"
                    );

                    vecReg.add(
                            INT_TBL_CHKAUT,
                            blnChkAut
                    );

                    vecReg.add(
                            INT_TBL_CHKDEN,
                            blnChkDen
                    );

                    vecReg.add(
                            INT_TBL_FECAUT,
                            rstLoc.getString("fe_aut")
                    );

                    vecReg.add(
                            INT_TBL_OBS,
                            rstLoc.getString("tx_obsaut")
                    );

                    vecReg.add(
                            INT_TBL_BUTOBS,
                            "..."
                    );

                    vecReg.add(
                            INT_TBL_COREG,
                            rstLoc.getInt("co_reg")
                    );

                    /*
                     * La columna todavía existe en el modelo,
                     * aunque no utilizamos ne_ordaut.
                     */
                    vecReg.add(
                            INT_TBL_ORDAUT,
                            rstLoc.getInt("co_reg")
                    );

                    vecDat.add(vecReg);
                }

                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblMod.initRowsState();

                /*
                 * Una liquidación autorizada o denegada
                 * ya no puede modificarse.
                 */
                if ("T".equals(strEstDoc)
                        || "D".equals(strEstDoc))
                {
                    objTblMod.setModoOperacion(
                            ZafTblMod.INT_TBL_NO_EDI
                    );

                    butAce.setEnabled(false);
                }

                /*
                 * El usuario ya registró su decisión.
                 */
                if (blnUsrLogAut)
                {
                    objTblMod.setModoOperacion(
                            ZafTblMod.INT_TBL_NO_EDI
                    );

                    butAce.setEnabled(false);
                }

                if (totnumAut == 0)
                {
                    objTblMod.setModoOperacion(
                            ZafTblMod.INT_TBL_NO_EDI
                    );

                    butAce.setEnabled(false);

                    mostrarMsgInf(
                            "No existen autorizadores configurados "
                            + "para esta liquidación."
                    );
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
            blnRes = false;

            objUti.mostrarMsgErr_F1(
                    this,
                    e
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes = false;

            objUti.mostrarMsgErr_F1(
                    this,
                    e
            );
        }
        finally
        {
            try
            {
                if (rstLoc != null)
                {
                    rstLoc.close();
                }

                if (stmLoc != null)
                {
                    stmLoc.close();
                }

                if (conLoc != null)
                {
                    conLoc.close();
                }
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
    }

       
    
    private String obtenerEstadoAutorizacionSolicitud(Connection conn) {
        String estado = "P"; // P = nadie autorizó, A = parcial, T = completa, R = rechazada

        Statement stmLoc = null;
        ResultSet rstLoc = null;

        try {
            stmLoc = conn.createStatement();

            String strSql = "";
            strSql += "SELECT ";
            strSql += "    COALESCE(SUM(CASE WHEN st_aut = 'R' THEN 1 ELSE 0 END),0) AS can_den, ";
            strSql += "    COALESCE(SUM(CASE WHEN st_aut = 'A' AND co_usraut = 156 THEN 1 ELSE 0 END),0) AS can_aut_final, ";
            strSql += "    COALESCE(SUM(CASE WHEN st_aut = 'A' THEN 1 ELSE 0 END),0) AS can_aut ";
            strSql += "FROM tbm_autsolvac ";
            strSql += "WHERE co_emp = " + intCodEmp + " ";
            strSql += "  AND co_loc = " + intCodLoc + " ";
            strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
            strSql += "  AND co_doc = " + intCodDoc + " ";

            System.out.println("obtenerEstadoAutorizacionSolicitud: " + strSql);

            rstLoc = stmLoc.executeQuery(strSql);

            if (rstLoc.next()) {
                int canDen = rstLoc.getInt("can_den");
                int canAutFinal = rstLoc.getInt("can_aut_final");
                int canAut = rstLoc.getInt("can_aut");

                if (canDen > 0) {
                    estado = "R";
                } else if (canAutFinal > 0) {
                    estado = "T";
                } else if (canAut > 0) {
                    estado = "A";
                } else {
                    estado = "P";
                }
            }

        } catch (Exception e) {
            estado = "N";
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try {
                if (rstLoc != null) rstLoc.close();
                if (stmLoc != null) stmLoc.close();
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        return estado;
    }
    
 
    private void mostrarMsgInf(String strMsg) 
    {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
  
    public int getOpcSelDlg() 
    {
        return intOpcSelDlg;
    }

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

        
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }
 
    
//    private boolean actualizarReg() 
//    {
//        boolean blnRes = true;
//        java.sql.Connection conn;
//        try 
//        {
//            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (conn != null) {
//                conn.setAutoCommit(false);
//
//         if (guardarDat(conn)) {
//             System.out.println("pasa guardarDat");
//                    contchkAut++;
//                    System.out.println(contchkAut+" = "+totnumAut);
//                     if ( contchkAut==totnumAut){
//                         System.out.println(contchkAut+" = "+totnumAut);
//                         System.out.println("todos los check ok");
//                        if (CalcularMontoVac(conn)){
//                            System.out.println("si calcula monto");
//                            if (strSolAde.equals("N")){
//                                System.out.println("No es adelantada");
//                                if ( strTipoSoli.equals("GOZ") || strTipoSoli.equals("PAG") ){
//                                    System.out.println(strTipoSoli);
//                                    // Mapear GOZ -> 'G' | PAG -> 'P'
//                                    final char tipo = "GOZ".equals(strTipoSoli) ? 'G' : 'P';
//                                    //System.out.println("💡 douvalorvac = " + douvalorvac);
//                                    System.out.println(tipo);
//                                    if (douvalorvac>0){
//                                        System.out.println("Datos: "+conn+","+intCodTipDoc+","+intCodLoc+","+Integer.parseInt(strCodTra)+","+douvalorvac+","+intRubVac+","+intCodDoc);
//                                        System.out.println("👉 Ejecutando generaIngresoEgreso con valores: " + douvalorvac);
//                                        Librerias.ZafRecHum.ZafRecHumIngEgr.ZafRecHumIngEgr ZafRecHumIngEgr = new Librerias.ZafRecHum.ZafRecHumIngEgr.ZafRecHumIngEgr(objParSis, objUti);
//                                        //if (ZafRecHumIngEgr.generaIngresoEgreso(conn,intCodTipDoc,intCodLoc ,Integer.parseInt(strCodTra),douvalorvac,intRubVac, intCodDoc)) { 
//                                        if (ZafRecHumIngEgr.generaIngresoEgreso(conn,intCodTipDoc,intCodLoc ,Integer.parseInt(strCodTra),douvalorvac, intCodDoc, tipo)) { 
//                                            //actualiza el estado de sol autorizada 
//                                            //System.out.println("✅ Todos los registros han sido autorizados, llamando a actualizaEstSol()");
//                                            if (ActualizaEstSol(conn,intCodDoc,Integer.parseInt(strCodTra),intCodLoc)) {
//                                                System.out.println("✅ Estado actualizado");
//                                                conn.commit();  
//                                            }else{
//                                                System.out.println("❌ Falló actualización de estado");
//                                                conn.rollback();
//                                                blnRes=false;
//                                            }
//                                        } 
//                                        else {
//                                            conn.rollback();
//                                            blnRes=false;
//                                        }
//                                        
//                                    }else{
//                                        conn.rollback();
//                                        blnRes=false;                                    
//                                    }                                   
//                                }else{
//                                    
//                                    conn.commit(); 
//                                }
//                            }else{
//                                conn.commit(); 
//                            }
////                  -------------------------------------------------------------------------
//                        }else {  
//                            conn.rollback();
//                            blnRes=false;
//                        }
//                     }else{      
//                         if(contchkAut>0){
//                             conn.commit();  
//                             blnRes=true;    
//                         }else if (contchkAut==0){
//                             conn.rollback();
//                             blnRes=false;   
//                             mostrarMsgInf("No se puede autorizar faltan usuarios por autorizar");
//                         }else{
//                             conn.rollback();
//                             blnRes=false;   
//                         }
//                         
//                     }
//          } else{
//              conn.commit(); 
//          } 
//            } else {
//                    blnRes=false;
//                    conn.rollback();
//                } 
//                conn.close();
//                conn = null;
//            
//        } catch (java.sql.SQLException Evt) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        } catch (Exception Evt) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }
//        return blnRes;
//    }

    
    private boolean actualizarReg()
    {
        boolean blnRes = false;

        java.sql.Connection conn = null;

        try
        {
            conn = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos()
            );

            if (conn != null)
            {
                conn.setAutoCommit(false);

                /*
                 * 1. Guardar la decisión individual.
                 */
                if (!guardarAutorizaciones(conn))
                {
                    conn.rollback();
                    return false;
                }

                /*
                 * 2. Determinar el estado de toda
                 * la liquidación.
                 */
                String strEstado =
                        obtenerEstadoFinal(conn);

                /*
                 * 3. Actualizar tbm_cabliqhab.
                 */
                if (!actualizarCabecera(
                        conn,
                        strEstado))
                {
                    conn.rollback();
                    return false;
                }

                /*
                 * En liquidaciones no se generan
                 * movimientos adicionales al autorizar.
                 */
                conn.commit();

                blnRes = true;
            }
        }
        catch (Exception e)
        {
            try
            {
                if (conn != null)
                {
                    conn.rollback();
                }
            }
            catch (Exception ex)
            {
            }

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
    }
    
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
                case INT_TBL_VALLIQ:
                    strMsg = "Valor total de la liquidación";
                    break;
                case INT_TBL_FECAUT:
                    strMsg = "Fecha Autorizacion / Denegado";
                    break;
                case INT_TBL_CHKAUT:
                    strMsg = "Autoriza la solicitud";
                    break; 
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

}
