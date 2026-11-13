package RecursosHumanos.ZafRecHum86;

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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
 
/**
 *
 * @author Roberto Flores
 */
public class ZafRecHum86_02 extends javax.swing.JDialog 
{

    private ZafRecHum86 objZafRecHum86;

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
    private String strFechaDesdeVac;                                   //fecha desde vac.
    private String strFechaHastaVac;                                   //fecha hasta vac.

    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                   //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                 //Renderer de Check Box

    // TABLA DE DATOS
    private final int INT_TBL_LIN = 0;
    private final int INT_TBL_CODUSR = 1;
    private final int INT_TBL_USR = 2;
    private final int INT_TBL_NOMUSR = 3;
    private final int INT_TBL_VALVAC = 4;    
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
    public ZafRecHum86_02(java.awt.Frame parent, boolean modal, ZafParSis obj) 
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

    private int getCodigoRegistro(java.sql.Connection conExt, int ne_anio ){
        int intRet=-1;
        String strSQL ="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_reg) IS NULL THEN 1 ELSE MAX(a1.co_reg)+1 END AS maximoRegistro \n";
                strSQL+=" FROM tbm_vacTra as a1 \n";
                strSQL+=" WHERE a1.co_emp="+ objParSis.getCodigoEmpresa() + " AND a1.co_tra="+ strCodTra +";" ; 
                System.out.println("Codigo Registro: "+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRet = rstLoc.getInt("maximoRegistro");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            intRet=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           intRet=-1;
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return intRet;
    }
    
    public boolean CalcularMontoVac(java.sql.Connection conn) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmIns = null;
        java.sql.ResultSet rst = null;
        String strSql = ""; 
        int diasRestantes = 0;
        int diasolvac = 0;
        int diasExcedentes = 0;
        String tipoSoli = "";

        douvalorvac = 0;
        
        try 
        {
            diasolvac = Integer.parseInt(strcantdiasolvac);
            tipoSoli = strTipoSoli;

            /*
             * Para vacaciones pagadas las fechas vienen vacías.
             * Si se insertan como '', PostgreSQL da error porque son campos date.
             * Por eso se preparan como NULL o como 'yyyy-mm-dd'.
             */
            String strFechaDesdeVacSql = "";
            String strFechaHastaVacSql = "";

            if (strFechaDesdeVac == null || strFechaDesdeVac.trim().equals("") || strFechaDesdeVac.trim().equalsIgnoreCase("null")) {
                strFechaDesdeVacSql = "NULL";
            } else {
                strFechaDesdeVacSql = "'" + strFechaDesdeVac + "'";
            }

            if (strFechaHastaVac == null || strFechaHastaVac.trim().equals("") || strFechaHastaVac.trim().equalsIgnoreCase("null")) {
                strFechaHastaVacSql = "NULL";
            } else {
                strFechaHastaVacSql = "'" + strFechaHastaVac + "'";
            }

            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                stmIns = conn.createStatement();

                strSql = "";
                strSql += "SELECT * ";
                strSql += "FROM tbm_cfgvactra ";
                strSql += "WHERE st_reg = 'A' ";
                strSql += "  AND co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "  AND co_tra = " + strCodTra + " ";
                strSql += "  AND (ne_diagoz + ne_diapag) < ne_diaper ";
                strSql += "ORDER BY ne_anio ASC ";

                System.out.println("Primer select: " + strSql);

                rst = stmLoc.executeQuery(strSql);

                while (rst.next()) 
                {   
                    douvalorvac = 0;

                    diasRestantes = rst.getInt("ne_diaper") - (rst.getInt("ne_diagoz") + rst.getInt("ne_diapag"));

                    if (diasRestantes > 0)
                    {
                        /*
                         * CASO 1:
                         * El período actual alcanza para cubrir todos los días solicitados.
                         */
                        if (diasolvac <= diasRestantes)
                        {
                            if (tipoSoli.equals("GOZ"))
                            {
                                BigDecimal valorDiaVac = obtenerValorDiaVacacion(conn, rst.getInt("ne_anio"));
                                BigDecimal valorVac = valorDiaVac.multiply(new BigDecimal(diasolvac)).setScale(2, RoundingMode.HALF_UP);
                                douvalorvac = valorVac.doubleValue();

                                System.out.println("ValorVac: " + douvalorvac + " * dias: " + diasolvac);

                                strSql = "";
                                strSql += "UPDATE tbm_cfgvactra ";
                                strSql += "SET ne_diagoz = ne_diagoz + " + diasolvac + " ";
                                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                strSql += "  AND co_tra = " + strCodTra + " ";
                                strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                System.out.println("Update GOZ: " + strSql);
                            }
                            else if (tipoSoli.equals("PAG"))
                            {
                                douvalorvac = objUti.redondear(
                                        (((rst.getDouble("nd_totinganu") / intMesPerVac) / intdiaPerVac) * diasolvac),
                                        objParSis.getDecimalesMostrar()
                                );

                                strSql = "";
                                strSql += "UPDATE tbm_cfgvactra ";
                                strSql += "SET ne_diapag = ne_diapag + " + diasolvac + " ";
                                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                strSql += "  AND co_tra = " + strCodTra + " ";
                                strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                System.out.println("Update PAG: " + strSql);
                            }
                            else
                            {
                                mostrarMsgInf("Tipo de solicitud de vacaciones no reconocido: " + tipoSoli);
                                return false;
                            }

                            stmIns.executeUpdate(strSql);
                            strSql = "";

                            if (!insertarMovimientoVacTra(
                                    conn,
                                    stmIns,
                                    rst.getInt("ne_anio"),
                                    rst.getInt("ne_diaper"),
                                    diasolvac,
                                    douvalorvac,
                                    tipoSoli,
                                    strFechaDesdeVacSql,
                                    strFechaHastaVacSql)) 
                            {
                                return false;
                            }

                            break;
                        }
                        /*
                         * CASO 2:
                         * El período actual no alcanza.
                         * Se consume lo restante del período actual y luego el excedente del siguiente período.
                         */
                        else 
                        {
                            diasExcedentes = diasolvac - diasRestantes;

                            if (tipoSoli.equals("GOZ"))
                            {
                                douvalorvac = objUti.redondear(
                                        (((rst.getDouble("nd_totinganu") / intMesPerVac) / intdiaPerVac) * diasRestantes),
                                        objParSis.getDecimalesMostrar()
                                );

                                strSql = "";
                                strSql += "UPDATE tbm_cfgvactra ";
                                strSql += "SET ne_diagoz = ne_diagoz + " + diasRestantes + " ";
                                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                strSql += "  AND co_tra = " + strCodTra + " ";
                                strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                System.out.println("Update GOZ periodo actual: " + strSql);
                            }
                            else if (tipoSoli.equals("PAG"))
                            {
                                douvalorvac = objUti.redondear(
                                        (((rst.getDouble("nd_totinganu") / intMesPerVac) / intdiaPerVac) * diasRestantes),
                                        objParSis.getDecimalesMostrar()
                                );

                                strSql = "";
                                strSql += "UPDATE tbm_cfgvactra ";
                                strSql += "SET ne_diapag = ne_diapag + " + diasRestantes + " ";
                                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                strSql += "  AND co_tra = " + strCodTra + " ";
                                strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                System.out.println("Update PAG periodo actual: " + strSql);
                            }
                            else
                            {
                                mostrarMsgInf("Tipo de solicitud de vacaciones no reconocido: " + tipoSoli);
                                return false;
                            }

                            stmIns.executeUpdate(strSql);
                            strSql = "";

                            if (!insertarMovimientoVacTra(
                                    conn,
                                    stmIns,
                                    rst.getInt("ne_anio"),
                                    rst.getInt("ne_diaper"),
                                    diasRestantes,
                                    douvalorvac,
                                    tipoSoli,
                                    strFechaDesdeVacSql,
                                    strFechaHastaVacSql)) 
                            {
                                return false;
                            }

                            /*
                             * Siguiente período para completar los días excedentes.
                             */
                            if (rst.next()) 
                            {
                                douvalorvac = 0;

                                if (tipoSoli.equals("GOZ"))
                                {
                                    douvalorvac = objUti.redondear(
                                            (((rst.getDouble("nd_totinganu") / intMesPerVac) / intdiaPerVac) * diasExcedentes),
                                            objParSis.getDecimalesMostrar()
                                    );

                                    strSql = "";
                                    strSql += "UPDATE tbm_cfgvactra ";
                                    strSql += "SET ne_diagoz = ne_diagoz + " + diasExcedentes + " ";
                                    strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                    strSql += "  AND co_tra = " + strCodTra + " ";
                                    strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                    System.out.println("Update GOZ siguiente periodo: " + strSql);
                                }
                                else if (tipoSoli.equals("PAG"))
                                {
                                    douvalorvac = objUti.redondear(
                                            (((rst.getDouble("nd_totinganu") / intMesPerVac) / intdiaPerVac) * diasExcedentes),
                                            objParSis.getDecimalesMostrar()
                                    );

                                    strSql = "";
                                    strSql += "UPDATE tbm_cfgvactra ";
                                    strSql += "SET ne_diapag = ne_diapag + " + diasExcedentes + " ";
                                    strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                    strSql += "  AND co_tra = " + strCodTra + " ";
                                    strSql += "  AND ne_anio = " + rst.getInt("ne_anio") + ";";

                                    System.out.println("Update PAG siguiente periodo: " + strSql);
                                }
                                else
                                {
                                    mostrarMsgInf("Tipo de solicitud de vacaciones no reconocido: " + tipoSoli);
                                    return false;
                                }

                                stmIns.executeUpdate(strSql);
                                strSql = "";

                                if (!insertarMovimientoVacTra(
                                        conn,
                                        stmIns,
                                        rst.getInt("ne_anio"),
                                        rst.getInt("ne_diaper"),
                                        diasExcedentes,
                                        douvalorvac,
                                        tipoSoli,
                                        strFechaDesdeVacSql,
                                        strFechaHastaVacSql)) 
                                {
                                    return false;
                                }
                            }
                            else
                            {
                                mostrarMsgInf("No existen suficientes períodos disponibles para completar la solicitud.");
                                return false;
                            }

                            break;
                        }
                    }
                }

                if (rst != null) {
                    rst.close();
                    rst = null;
                }

                if (stmLoc != null) {
                    stmLoc.close();
                    stmLoc = null;
                }

                if (stmIns != null) {
                    stmIns.close();
                    stmIns = null;
                }
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
    
    
    private BigDecimal obtenerValorDiaVacacion(Connection conn, int intPerHasVac) throws Exception {
        BigDecimal valorDia = BigDecimal.ZERO;

        Statement stmLoc = null;
        ResultSet rstLoc = null;

        try {
            stmLoc = conn.createStatement();

            String strSql = "";
            strSql += "SELECT COALESCE(SUM(nd_valvac),0) AS total_valvac ";
            strSql += "FROM tbm_detpervactra ";
            strSql += "WHERE co_tra = " + strCodTra + " ";
            strSql += "  AND ne_perhas = " + intPerHasVac + " ";
            strSql += "  AND ne_perdes = " + (intPerHasVac - 1);

            System.out.println("obtenerValorDiaVacacion: " + strSql);

            rstLoc = stmLoc.executeQuery(strSql);

            if (rstLoc.next()) {
                BigDecimal totalValVac = rstLoc.getBigDecimal("total_valvac");
                valorDia = totalValVac.divide(new BigDecimal("15"), 2, RoundingMode.HALF_UP);
            }

        } finally {
            if (rstLoc != null) rstLoc.close();
            if (stmLoc != null) stmLoc.close();
        }

        return valorDia;
}
    
    
     public boolean ActualizaEstSol(java.sql.Connection conn,int co_doc, int co_tra, int co_loc) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSql = "";
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                strSql = "update tbm_cabSolVac set st_aut='A'\n" +
                        "where co_emp =" + objParSis.getCodigoEmpresa() +  " and co_doc=" + co_doc +" and co_loc = " + co_loc  + " and co_tra= "+ co_tra ; 
                
                System.out.println("⏳ Ejecutando actualizaEstSol...");
                System.out.println("📌 Parámetros recibidos:");
                System.out.println(" - co_emp : " + objParSis.getCodigoEmpresa());
                System.out.println(" - co_loc : " + co_loc);
                System.out.println(" - co_doc : " + co_doc);
                //System.out.println(" - Estado a aplicar: " + estado);  // A = autorizado, D = denegado
                System.out.println("Update 7: "+strSql);
                stmLoc.executeUpdate(strSql);
                int intRes = stmLoc.executeUpdate(strSql);
                System.out.println("🟢 UPDATE ejecutado. Filas afectadas: " + intRes);

                       
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    public boolean guardarAutorizaciones(java.sql.Connection conn) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        String strSql = "";

        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) 
                {
                    if (tblDat.getValueAt(intFil, INT_TBL_LIN).toString().compareTo("M") == 0) 
                    { 
                        Object objObs = tblDat.getValueAt(intFil, INT_TBL_OBS);
                        String strObs = "";

                        if (objObs != null && !objObs.toString().trim().isEmpty()) {
                            strObs = objUti.codificar(objObs.toString());
                        }

                        String strAut = "";

                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(true)) {
                            strAut = "'A'";
                        }

                        if (tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(true)) {
                            strAut = "'R'";
                        }

                        if (tblDat.getValueAt(intFil, INT_TBL_CHKAUT).equals(false) 
                                && tblDat.getValueAt(intFil, INT_TBL_CHKDEN).equals(false)) {
                            mostrarMsgInf("NO HA INGRESADO SI AUTORIZA O DENIEGA LA SOLICITUD");
                            return false;
                        }

                        if (objParSis.getCodigoUsuario() == Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString())) 
                        {
                            strSql = "";
                            strSql += "UPDATE tbm_autsolvac SET ";
                            strSql += " st_aut = " + strAut + ", ";
                            strSql += " tx_obsaut = '" + strObs + "', ";
                            strSql += " fe_aut = CURRENT_TIMESTAMP, ";
                            strSql += " tx_comaut = '" + objParSis.getDireccionIP() + "' ";
                            strSql += "WHERE co_emp = " + intCodEmp + " ";
                            strSql += "  AND co_loc = " + intCodLoc + " ";
                            strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                            strSql += "  AND co_doc = " + intCodDoc + " ";
                            strSql += "  AND co_reg = " + tblDat.getValueAt(intFil, INT_TBL_COREG).toString() + " ";
                            strSql += "  AND co_usraut = " + objParSis.getCodigoUsuario();

                            System.out.println("guardarAutorizaciones Vac: " + strSql);

                            int intFilAct = stmLoc.executeUpdate(strSql);

                            if (intFilAct <= 0) {
                                mostrarMsgInf("No se actualizó la autorización. Verifique si el usuario tiene permiso para autorizar esta solicitud.");
                                return false;
                            }
                        }
                    }
                }

                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
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

                if (!actualizarCabecera(conn, strEstado))
                {
                    conn.rollback();
                    return false;
                }

                if (strEstado.equals("T"))
                {
                    if (!aplicarVacaciones(conn))
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
                strSql = "";
                strSql += "SELECT COUNT(*) AS total ";
                strSql += "FROM tbm_autsolvac ";
                strSql += "WHERE co_emp = " + intCodEmp + " ";
                strSql += "  AND co_loc = " + intCodLoc + " ";
                strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                strSql += "  AND co_doc = " + intCodDoc + " ";
                strSql += "  AND st_aut IN ('D','R') ";

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

                /* AUTORIZÓ USUARIO FINAL 156 */
                strSql = "";
                strSql += "SELECT COUNT(*) AS total ";
                strSql += "FROM tbm_autsolvac ";
                strSql += "WHERE co_emp = " + intCodEmp + " ";
                strSql += "  AND co_loc = " + intCodLoc + " ";
                strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                strSql += "  AND co_doc = " + intCodDoc + " ";
                strSql += "  AND co_usraut = 156 ";
                strSql += "  AND st_aut = 'A' ";

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next())
                {
                    if (rstLoc.getInt("total") > 0)
                    {
                        rstLoc.close();
                        rstLoc = null;

                        stmLoc.close();
                        stmLoc = null;

                        return "T";
                    }
                }

                rstLoc.close();
                rstLoc = null;

                /* CUALQUIER AUTORIZACIÓN */
                strSql = "";
                strSql += "SELECT COUNT(*) AS total ";
                strSql += "FROM tbm_autsolvac ";
                strSql += "WHERE co_emp = " + intCodEmp + " ";
                strSql += "  AND co_loc = " + intCodLoc + " ";
                strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                strSql += "  AND co_doc = " + intCodDoc + " ";
                strSql += "  AND st_aut = 'A' ";

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next())
                {
                    if (rstLoc.getInt("total") > 0)
                    {
                        strEstado = "A";
                    }
                    else
                    {
                        strEstado = "P";
                    }
                }

                rstLoc.close();
                rstLoc = null;

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
    
    private boolean actualizarCabecera(java.sql.Connection conn, String strEstado)
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        String strSql = "";

        try
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                if (strEstado.equals("D"))
                {
                    strSql = "";
                    strSql += "UPDATE tbm_cabsolvac SET ";
                    strSql += "st_aut = 'D' ";
                    strSql += "WHERE co_emp = " + intCodEmp + " ";
                    strSql += "  AND co_loc = " + intCodLoc + " ";
                    strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                    strSql += "  AND co_doc = " + intCodDoc + " ";
                }
                else if (strEstado.equals("A"))
                {
                    strSql = "";
                    strSql += "UPDATE tbm_cabsolvac SET ";
                    strSql += "st_aut = 'A' ";
                    strSql += "WHERE co_emp = " + intCodEmp + " ";
                    strSql += "  AND co_loc = " + intCodLoc + " ";
                    strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                    strSql += "  AND co_doc = " + intCodDoc + " ";
                }
                else if (strEstado.equals("T"))
                {
                    strSql = "";
                    strSql += "UPDATE tbm_cabsolvac SET ";
                    strSql += "st_aut = 'T', ";
                    strSql += "fe_aut = CURRENT_TIMESTAMP ";
                    strSql += "WHERE co_emp = " + intCodEmp + " ";
                    strSql += "  AND co_loc = " + intCodLoc + " ";
                    strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                    strSql += "  AND co_doc = " + intCodDoc + " ";
                }
                else
                {
                    strSql = "";
                    strSql += "UPDATE tbm_cabsolvac SET ";
                    strSql += "st_aut = 'P' ";
                    strSql += "WHERE co_emp = " + intCodEmp + " ";
                    strSql += "  AND co_loc = " + intCodLoc + " ";
                    strSql += "  AND co_tipdoc = " + intCodTipDoc + " ";
                    strSql += "  AND co_doc = " + intCodDoc + " ";
                }

                System.out.println("actualizarCabecera vacaciones: " + strSql);

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
    
    private boolean insertarMovimientoVacTra(java.sql.Connection conn,java.sql.Statement stmIns,int intAnio,int intDiasPeriodo,int intDiasAplicar,double dblValorVac,
        String tipoSoli,String strFechaDesdeVacSql,String strFechaHastaVacSql)
            
    {
        System.out.println("TIPO SOLICITUD EN insertarMovimientoVacTra: [" + tipoSoli + "]");
        System.out.println("DIAS APLICAR: " + intDiasAplicar);
        System.out.println("VALOR VAC: " + dblValorVac);
        
        boolean blnRes = true;
        String strSql = "";

        try {
            String strNumDiaVacPag = "NULL";
            String strMonVacPag = "NULL";
            String strNumDiaVacGoz = "NULL";
            String strMonVacGoz = "NULL";

            if (tipoSoli.equals("PAG")) {
                strNumDiaVacPag = String.valueOf(intDiasAplicar);
                strMonVacPag = String.valueOf(dblValorVac);
            } else if (tipoSoli.equals("GOZ")) {
                strNumDiaVacGoz = String.valueOf(intDiasAplicar);
                strMonVacGoz = String.valueOf(dblValorVac);
            } else {
                mostrarMsgInf("Tipo de solicitud de vacaciones no reconocido: " + tipoSoli);
                return false;
            }

            strSql = "";
            strSql += "INSERT INTO tbm_vacTra ";
            strSql += "(co_emp, co_tra, co_reg, fe_doc, ne_ani, ne_numdiavacper, ";
            strSql += "co_locrelsolvac, co_tipdocrelsolvac, co_docrelsolvac, ";
            strSql += "st_vacpag, fe_inivacgoz, fe_finvacgoz, ";
            strSql += "ne_numdiavacpag, nd_monpervacpag, ";
            strSql += "ne_numdiavacgoz, nd_monpervacgoz, ";
            strSql += "st_reg, fe_ing, co_usring) ";
            strSql += "VALUES (";
            strSql += objParSis.getCodigoEmpresa() + ",";
            strSql += strCodTra + ",";
            strSql += getCodigoRegistro(conn, intAnio) + ",";
            strSql += "'" + strFecha + "',";
            strSql += intAnio + ",";
            strSql += intDiasPeriodo + ",";
            strSql += intCodLoc + ",";
            strSql += intCodTipDoc + ",";
            strSql += intCodDoc + ",";
            strSql += "'P',";
            strSql += strFechaDesdeVacSql + ",";
            strSql += strFechaHastaVacSql + ",";
            strSql += strNumDiaVacPag + ",";
            strSql += strMonVacPag + ",";
            strSql += strNumDiaVacGoz + ",";
            strSql += strMonVacGoz + ",";
            strSql += "'A',CURRENT_TIMESTAMP,";
            strSql += objParSis.getCodigoUsuario();
            strSql += ");";

            System.out.println("Insert tbm_vacTra: " + strSql);

            stmIns.executeUpdate(strSql);

        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        return blnRes;
    }
    
    
    private boolean aplicarVacaciones(java.sql.Connection conn)
    {
        boolean blnRes = true;

        try
        {
            if (CalcularMontoVac(conn))
            {
                if (strSolAde.equals("N"))
                {
                    if (strTipoSoli.equals("GOZ"))
                    {
                        /*
                         * Vacaciones gozadas:
                         * - CalcularMontoVac ya aplicó los días.
                         * - Aquí sí genera ingreso/egreso.
                         */
                        if (douvalorvac > 0)
                        {
                            Librerias.ZafRecHum.ZafRecHumIngEgr.ZafRecHumIngEgr ZafRecHumIngEgr =
                                    new Librerias.ZafRecHum.ZafRecHumIngEgr.ZafRecHumIngEgr(objParSis, objUti);

                            if (!ZafRecHumIngEgr.generaIngresoEgreso(
                                    conn,
                                    intCodTipDoc,
                                    intCodLoc,
                                    Integer.parseInt(strCodTra),
                                    douvalorvac,
                                    intCodDoc,
                                    'G'))
                            {
                                blnRes = false;
                            }
                        }
                        else
                        {
                            blnRes = false;
                        }
                    }
                    else if (strTipoSoli.equals("PAG"))
                    {
                        /*
                         * Vacaciones pagadas:
                         * - CalcularMontoVac ya aplicó los días pagados.
                         * - NO debe generar ingreso/egreso.
                         */
                        System.out.println("Vacaciones PAGADAS: se aplican días, pero no se genera ingreso/egreso.");
                        blnRes = true;
                    }
                    else
                    {
                        System.out.println("Tipo de solicitud de vacaciones no reconocido: " + strTipoSoli);
                        blnRes = false;
                    }
                }
            }
            else
            {
                blnRes = false;
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
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
           // arlParDlg = new ArrayList();
            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    public void setParDlg(ZafParSis objZafParsis, ZafRecHum86 objRecHum86, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc,
            String strFecha, String strEstDoc, String strCodTra, String strTipSol, String strSolAde, String numdiasolvac, String FechadEsde, String FechaHasta) 
    {
        this.objParSis = objZafParsis;
        this.intCodEmp = intCodEmp;
        this.intCodLoc = intCodLoc;
        this.intCodTipDoc = intCodTipDoc;
        this.intCodDoc = intCodDoc;
        this.strFecha = strFecha;
        this.intNumDoc = intNumDoc;
        this.strEstDoc = strEstDoc;        
        this.objZafRecHum86 = objRecHum86;
        this.strCodTra = strCodTra;        
        this.strTipoSoli = strTipSol;        
        this.strSolAde = strSolAde;  
        this.strcantdiasolvac = numdiasolvac;   
        this.strFechaDesdeVac = FechadEsde;        
        this.strFechaHastaVac = FechaHasta;        
     
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
        vecCab.add(INT_TBL_VALVAC, "Valor Vac.");        
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
        tcmAux.getColumn(INT_TBL_VALVAC).setPreferredWidth(100);        
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
                    
                    //DESCOMENTRAR PARA PRODUCCION JP
                    int codusuinises=objParSis.getCodigoUsuario();
                    String codusuaut = (tblDat.getValueAt(intFilSel, INT_TBL_CODUSR) == null ? "" : tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString());
                     
                    if (codusuaut.equals(String.valueOf(codusuinises))){
                        tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKAUT);
                        tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKDEN);
                    }else{
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                    
                    
                    //COMETAR PARA PRUEBAS
                    //tblDat.setValueAt(new Boolean(true), intFilSel, INT_TBL_CHKAUT);
                   // tblDat.setValueAt(new Boolean(false), intFilSel, INT_TBL_CHKDEN);
                    //

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
                        JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
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
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum86_02.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                    }
                }
            };

           
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
                         Object obj = objTblMod.getValueAt(intFilSel, INT_TBL_CHKAUT);
                        boolean blnAut = (obj != null && !obj.toString().isEmpty());
                        String str = objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString();
 
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_LIN).toString().compareTo("B") == 0) {
                            objTblCelEdiChk.setCancelarEdicion(true);
                        }
                    }
//
                    if (objParSis.getCodigoUsuario() == 1) {

                    } else {

                        boolean blnActUsr = true;
                        boolean bln = false;
                        Boolean blnsTOBLSEGJERAUT = false; //(Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {
 
                            if (!blnActUsr) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                    if (!bln) {
                                        String strTit = "Mensaje del sistema Zafiro";
                                        String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                                        JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Solo puede autorizar el usuario loggeado. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
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
                        Boolean blnsTOBLSEGJERAUT = false;//(Boolean) tblDat.getValueAt(intFilSel, INT_TBL_STOBLSEGJERAUT);
                        if (blnsTOBLSEGJERAUT) {

                             if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) == objParSis.getCodigoUsuario()) {
 
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
                                        JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                        objTblCelEdiChk2.setCancelarEdicion(true);
                                    }
                                }

                            }
                        } else {
                            if (Integer.valueOf(tblDat.getValueAt(intFilSel, INT_TBL_CODUSR).toString()) != objParSis.getCodigoUsuario()) {
                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Solo puede denegar el usuario loggeado. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum86_02.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
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
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum86_02.this), true, strObs);

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
 
        }

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
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;

        boolean blnRes = true;
        boolean UsrlogAut = false;

        String strSQL = "";
        boolean chkaut = false;
        boolean chkden = false;  
        int codusrLogg = 0; 
        
        java.math.BigDecimal bgdValVac = java.math.BigDecimal.ZERO;

        try 
        {
            codusrLogg = objParSis.getCodigoUsuario(); 

            contchkAut = 0;
            totnumAut = 0;

            con = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(), 
                    objParSis.getUsuarioBaseDatos(), 
                    objParSis.getClaveBaseDatos()
            );

            if (con != null) 
            {
                /*
                * Se calcula una sola vez para mostrarlo
                * en todos los autorizadores.
                */
                bgdValVac = obtenerValorVacacion(con);
                
                stm = con.createStatement();

                vecDat = new Vector();

                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    aut.co_usraut, ";
                strSQL += "    aut.co_reg, ";
                strSQL += "    aut.co_doc, ";
                strSQL += "    CASE WHEN aut.st_aut = 'P' THEN 'PENDIENTE' ELSE 'REGISTRADO' END AS estado_autorizacion, ";
                strSQL += "    aut.st_aut AS st_aut, ";
                strSQL += "    aut.fe_aut, ";
                strSQL += "    aut.tx_obsaut, ";
                strSQL += "    aut.tx_comaut, ";
                strSQL += "    usr.tx_nom AS nomaut, ";
                strSQL += "    usr.tx_usr ";
                strSQL += "FROM tbm_autsolvac aut ";
                strSQL += "INNER JOIN tbm_usr usr ";
                strSQL += "    ON usr.co_usr = aut.co_usraut ";
                strSQL += "   AND usr.st_reg = 'A' ";
                strSQL += "WHERE aut.co_emp = " + intCodEmp + " ";
                strSQL += "  AND aut.co_loc = " + intCodLoc + " ";
                strSQL += "  AND aut.co_tipdoc = " + intCodTipDoc + " ";
                strSQL += "  AND aut.co_doc = " + intCodDoc + " ";
                strSQL += "ORDER BY aut.co_reg ASC ";

                System.out.println("cargarReg: " + strSQL);

                rst = stm.executeQuery(strSQL);

                while (rst.next()) 
                {
                    totnumAut++;

                    chkaut = false;
                    chkden = false;

                    String strAut = rst.getString("st_aut");

                    if ("A".equals(strAut)) {
                        chkaut = true;
                        chkden = false;
                    }
                    else if ("D".equals(strAut) || "R".equals(strAut)) {
                        chkaut = false;
                        chkden = true;
                    }
                    else {
                        chkaut = false;
                        chkden = false;
                    }

                    if (codusrLogg == rst.getInt("co_usraut") 
                            && "REGISTRADO".equals(rst.getString("estado_autorizacion"))) {
                        UsrlogAut = true;
                    }

                    if ("REGISTRADO".equals(rst.getString("estado_autorizacion"))) { 
                        contchkAut++;
                    }

                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CODUSR, rst.getInt("co_usraut"));
                    vecReg.add(INT_TBL_USR, rst.getString("tx_usr"));
                    vecReg.add(INT_TBL_NOMUSR, rst.getString("nomaut"));
                    //vecReg.add(INT_TBL_VALVAC, bgdValVac.doubleValue());
                    vecReg.add(INT_TBL_VALVAC,"<html><b>$ "+ new java.text.DecimalFormat("###,##0.00").format(bgdValVac)+ "</b></html>");                    
                    vecReg.add(INT_TBL_CHKAUT, chkaut);  
                    vecReg.add(INT_TBL_CHKDEN, chkden);                                              
                    vecReg.add(INT_TBL_FECAUT, rst.getString("fe_aut"));                                
                    vecReg.add(INT_TBL_OBS, rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_BUTOBS, "...");
                    vecReg.add(INT_TBL_COREG, rst.getString("co_reg"));   
                    vecReg.add(INT_TBL_ORDAUT, rst.getString("co_reg"));

                    vecDat.add(vecReg);
                }

                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                /*
                 * Si ya está totalmente aprobada o denegada,
                 * no se permite editar.
                 */
                if ("T".equals(strEstDoc) || "D".equals(strEstDoc)) 
                {
                    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                    butAce.setEnabled(false);
                }

                /*
                 * Si el usuario logueado ya registró autorización/denegación,
                 * no puede volver a modificar.
                 */
                if (UsrlogAut)
                {
                    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                    butAce.setEnabled(false);
                }

                /*
                 * Si no se encontraron autorizadores, bloquear.
                 */
                if (totnumAut == 0)
                {
                    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
                    butAce.setEnabled(false);
                    mostrarMsgInf("No existen autorizadores configurados para esta solicitud.");
                }
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
        finally
        {
            try
            {
                if (rst != null) rst.close();
                if (stm != null) stm.close();
                if (con != null) con.close();
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
    } 
    
    private java.math.BigDecimal obtenerValorVacacion(java.sql.Connection con ) throws Exception
    {
        java.math.BigDecimal bgdValorTotal =  java.math.BigDecimal.ZERO;

        String strTipo = "";
        int intCodTra = 0;

        /*
        * ============================================================
        * VALIDACIONES INICIALES
        * ============================================================
        */
       if (con == null)
       {
           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }

       if (strTipoSoli != null)
       {
           strTipo = strTipoSoli.trim().toUpperCase();
       }

       if (!"GOZ".equals(strTipo)
               && !"PAG".equals(strTipo))
       {
           System.out.println(
                   "Tipo de solicitud de vacaciones no reconocido: "
                   + strTipoSoli
           );

           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }

       if (strCodTra == null
               || strCodTra.trim().equals(""))
       {
           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }

       intCodTra = Integer.parseInt(
               strCodTra.trim()
       );


       /*
        * ============================================================
        * PASO 1:
        * BUSCAR VALOR HISTÓRICO.
        *
        * Si la solicitud ya fue procesada se utiliza el valor
        * registrado en tbm_vacTra.
        *
        * GOZ -> nd_monpervacgoz
        * PAG -> nd_monpervacpag
        * ============================================================
        */
       java.sql.PreparedStatement pstHistorico = null;
       java.sql.ResultSet rstHistorico = null;

       try
       {
           String strCampoValor = "";

           if ("GOZ".equals(strTipo))
           {
               strCampoValor = "nd_monpervacgoz";
           }
           else
           {
               strCampoValor = "nd_monpervacpag";
           }

           String strSqlHistorico = "";

           strSqlHistorico += "SELECT ";
           strSqlHistorico += "    SUM(" + strCampoValor + ") ";
           strSqlHistorico += "        AS valor_vacacion ";
           strSqlHistorico += "FROM tbm_vacTra ";
           strSqlHistorico += "WHERE co_emp = ? ";
           strSqlHistorico += "  AND co_tra = ? ";
           strSqlHistorico += "  AND co_locrelsolvac = ? ";
           strSqlHistorico += "  AND co_tipdocrelsolvac = ? ";
           strSqlHistorico += "  AND co_docrelsolvac = ? ";
           strSqlHistorico += "  AND st_reg = 'A' ";

           System.out.println(
                   "Consulta valor histórico vacaciones: "
                   + strSqlHistorico
           );

           pstHistorico =
                   con.prepareStatement(
                           strSqlHistorico
                   );

           pstHistorico.setInt(
                   1,
                   intCodEmp
           );

           pstHistorico.setInt(
                   2,
                   intCodTra
           );

           pstHistorico.setInt(
                   3,
                   intCodLoc
           );

           pstHistorico.setInt(
                   4,
                   intCodTipDoc
           );

           pstHistorico.setInt(
                   5,
                   intCodDoc
           );

           rstHistorico =
                   pstHistorico.executeQuery();

           if (rstHistorico.next())
           {
               java.math.BigDecimal bgdValorHistorico =
                       rstHistorico.getBigDecimal(
                               "valor_vacacion"
                       );

               /*
                * Si encuentra valor histórico significa que la
                * solicitud ya fue aplicada.
                *
                * Se devuelve ese valor y NO se recalcula.
                */
               if (bgdValorHistorico != null)
               {
                   bgdValorHistorico =
                           bgdValorHistorico.setScale(
                                   2,
                                   java.math.RoundingMode.HALF_UP
                           );

                   System.out.println(
                           "Valor histórico de vacaciones "
                           + strTipo
                           + ": "
                           + bgdValorHistorico
                   );

                   return bgdValorHistorico;
               }
           }
       }
       finally
       {
           if (rstHistorico != null)
           {
               rstHistorico.close();
               rstHistorico = null;
           }

           if (pstHistorico != null)
           {
               pstHistorico.close();
               pstHistorico = null;
           }
       }


       /*
        * ============================================================
        * PASO 2:
        * VALIDAR CANTIDAD DE DÍAS SOLICITADOS.
        * ============================================================
        */
       if (strcantdiasolvac == null
               || strcantdiasolvac.trim().equals(""))
       {
           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }

       int intDiasSolicitud =
               Integer.parseInt(
                       strcantdiasolvac.trim()
               );

       if (intDiasSolicitud <= 0)
       {
           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }


       /*
        * ============================================================
        * PASO 3:
        * OBTENER EL PERÍODO DE ESTA SOLICITUD.
        *
        * IMPORTANTE:
        *
        * No se buscan los períodos pendientes del trabajador.
        * Se utiliza exclusivamente ne_perhasvac de la solicitud.
        *
        * Ejemplo:
        *
        * ne_perhasvac = 2026
        *
        * Entonces el período es:
        *
        * 2025 - 2026
        * ============================================================
        */
       int intPerHasVac = 0;

       java.sql.PreparedStatement pstPeriodo = null;
       java.sql.ResultSet rstPeriodo = null;

       try
       {
           String strSqlPeriodo = "";

           strSqlPeriodo += "SELECT ";
           strSqlPeriodo += "    ne_perhasvac ";
           strSqlPeriodo += "FROM tbm_detsolvac ";
           strSqlPeriodo += "WHERE co_emp = ? ";
           strSqlPeriodo += "  AND co_loc = ? ";
           strSqlPeriodo += "  AND co_tipdoc = ? ";
           strSqlPeriodo += "  AND co_doc = ? ";
           strSqlPeriodo += "  AND co_reg = 1 ";
           strSqlPeriodo += "LIMIT 1 ";

           System.out.println(
                   "Consulta período solicitud vacaciones: "
                   + strSqlPeriodo
           );

           pstPeriodo =
                   con.prepareStatement(
                           strSqlPeriodo
                   );

           pstPeriodo.setInt(
                   1,
                   intCodEmp
           );

           pstPeriodo.setInt(
                   2,
                   intCodLoc
           );

           pstPeriodo.setInt(
                   3,
                   intCodTipDoc
           );

           pstPeriodo.setInt(
                   4,
                   intCodDoc
           );

           rstPeriodo =
                   pstPeriodo.executeQuery();

           if (rstPeriodo.next())
           {
               intPerHasVac =
                       rstPeriodo.getInt(
                               "ne_perhasvac"
                       );

               if (rstPeriodo.wasNull())
               {
                   intPerHasVac = 0;
               }
           }
       }
       finally
       {
           if (rstPeriodo != null)
           {
               rstPeriodo.close();
               rstPeriodo = null;
           }

           if (pstPeriodo != null)
           {
               pstPeriodo.close();
               pstPeriodo = null;
           }
       }


       /*
        * Si no se encontró período, no debemos tomar otro período
        * automáticamente porque produciría nuevamente diferencias
        * respecto al reporte.
        */
       if (intPerHasVac <= 0)
       {
           System.out.println(
                   "La solicitud "
                   + intCodDoc
                   + " no tiene ne_perhasvac."
           );

           return java.math.BigDecimal.ZERO.setScale(
                   2,
                   java.math.RoundingMode.HALF_UP
           );
       }


       /*
        * ============================================================
        * PASO 4:
        * CALCULAR EL VALOR DE LA SOLICITUD.
        *
        * Esta lógica se utiliza tanto para:
        *
        * GOZ
        * PAG
        *
        * Se calcula exactamente sobre el período de la solicitud.
        *
        * Fórmula:
        *
        * SUM(nd_valvac) / 15
        *
        * El valor diario se redondea PRIMERO a 2 decimales.
        *
        * Luego:
        *
        * valorDia * diasSolicitud
        * ============================================================
        */
       java.sql.PreparedStatement pstValor = null;
       java.sql.ResultSet rstValor = null;

       try
       {
           String strSqlValor = "";

           strSqlValor += "SELECT ";
           strSqlValor += "    COALESCE(SUM(d.nd_valvac), 0) ";
           strSqlValor += "        AS total_valvac ";
           strSqlValor += "FROM tbm_detpervactra AS d ";
           strSqlValor += "WHERE d.co_emp = ? ";
           strSqlValor += "  AND d.co_tra = ? ";
           strSqlValor += "  AND d.ne_perhas = ? ";
           strSqlValor += "  AND d.ne_perdes = d.ne_perhas - 1 ";
           strSqlValor += "  AND d.st_reg = 'A' ";

           System.out.println(
                   "Consulta valor vacaciones "
                   + strTipo
                   + " período "
                   + (intPerHasVac - 1)
                   + "-"
                   + intPerHasVac
                   + ": "
                   + strSqlValor
           );

           pstValor =
                   con.prepareStatement(
                           strSqlValor
                   );

           pstValor.setInt(
                   1,
                   intCodEmp
           );

           pstValor.setInt(
                   2,
                   intCodTra
           );

           pstValor.setInt(
                   3,
                   intPerHasVac
           );

           rstValor =
                   pstValor.executeQuery();

           if (rstValor.next())
           {
               java.math.BigDecimal bgdTotalValVac =
                       rstValor.getBigDecimal(
                               "total_valvac"
                       );

               if (bgdTotalValVac == null)
               {
                   bgdTotalValVac =
                           java.math.BigDecimal.ZERO;
               }


               /*
                * ====================================================
                * VALOR DIARIO
                *
                * Primero se divide para 15.
                *
                * IMPORTANTE:
                * Se redondea aquí a dos decimales ANTES de
                * multiplicar por los días.
                * ====================================================
                */
               java.math.BigDecimal bgdValorDia =
                       bgdTotalValVac.divide(
                               new java.math.BigDecimal("15"),
                               2,
                               java.math.RoundingMode.HALF_UP
                       );


               /*
                * ====================================================
                * VALOR TOTAL DE LA SOLICITUD.
                * ====================================================
                */
               bgdValorTotal =
                       bgdValorDia.multiply(
                               java.math.BigDecimal.valueOf(
                                       intDiasSolicitud
                               )
                       ).setScale(
                               2,
                               java.math.RoundingMode.HALF_UP
                       );


               /*
                * ====================================================
                * MENSAJES PARA PRUEBAS.
                * ====================================================
                */
               System.out.println(
                       "=============================================="
               );

               System.out.println(
                       "CALCULO VALOR VACACIONES"
               );

               System.out.println(
                       "Tipo solicitud: "
                       + strTipo
               );

               System.out.println(
                       "Trabajador: "
                       + intCodTra
               );

               System.out.println(
                       "Solicitud: "
                       + intCodDoc
               );

               System.out.println(
                       "Periodo solicitud: "
                       + (intPerHasVac - 1)
                       + "-"
                       + intPerHasVac
               );

               System.out.println(
                       "Total nd_valvac período: "
                       + bgdTotalValVac
               );

               System.out.println(
                       "Valor día vacaciones: "
                       + bgdValorDia
               );

               System.out.println(
                       "Días solicitud: "
                       + intDiasSolicitud
               );

               System.out.println(
                       "Valor calculado vacaciones: "
                       + bgdValorTotal
               );

               System.out.println(
                       "=============================================="
               );
           }
       }
       finally
       {
           if (rstValor != null)
           {
               rstValor.close();
               rstValor = null;
           }

           if (pstValor != null)
           {
               pstValor.close();
               pstValor = null;
           }
       }


       return bgdValorTotal;
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
                 * 1. Guardar autorización individual en tbm_autsolvac.
                 */
                if (!guardarAutorizaciones(conn))
                {
                    conn.rollback();
                    return false;
                }

                /*
                 * 2. Calcular estado final de la solicitud.
                 *    P = Pendiente
                 *    A = Parcial
                 *    T = Aprobado total
                 *    D = Denegado
                 */
                String strEstado = obtenerEstadoFinal(conn);

                System.out.println("Estado final calculado: " + strEstado);

                /*
                 * 3. Actualizar cabecera tbm_cabsolvac.
                 */
                if (!actualizarCabecera(conn, strEstado))
                {
                    conn.rollback();
                    return false;
                }

                /*
                 * 4. Solo si está totalmente aprobada se aplica vacaciones.
                 *    En vacaciones, T se logra cuando autoriza el usuario 156.
                 */
                if (strEstado.equals("T"))
                {
                    if (!aplicarVacaciones(conn))
                    {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();

                blnRes = true;
            }
            else
            {
                blnRes = false;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            try
            {
                if (conn != null)
                {
                    conn.rollback();
                }
            }
            catch (Exception e)
            {
            }

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            try
            {
                if (conn != null)
                {
                    conn.rollback();
                }
            }
            catch (Exception e)
            {
            }

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                    conn = null;
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
                case INT_TBL_VALVAC:
                    strMsg = "Valor Vacaciones";
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
