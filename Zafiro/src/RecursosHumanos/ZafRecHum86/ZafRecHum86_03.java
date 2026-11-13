
package RecursosHumanos.ZafRecHum86;

import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon; 
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.TableColumnModel;
import java.sql.DriverManager;
import java.text.NumberFormat;

import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Pantalla solicitud de vacaciones
 * @author Jean Moran
 * Guayaquil 10/09/2025
 */
public class ZafRecHum86_03 extends javax.swing.JInternalFrame {
    //private final String strVer = " v0.01";                     //Versión de clase
                         //Date Time Picker para la fecha de inicio de contrato
    private ZafUtil objUti;                                     //Utilidades
    private ZafParSis objParSis;                                //Parámetros del Sistema
    private String strCodTra = "",strCodTraRee="";
    private String strNomTra = "", strNomTraRee = "";                             //Ventana de consulta.
    private ZafVenCon vcoTra;
    private String strSQL, strAux;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafPerUsr objPerUsr;
    
    private JButton butImprimir;
    
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    
    private String diasParam; 
    private int anioDesde = 0, anioHasta = 0; // se obtienen de la consulta (ne_perdes, ne_perhas)
    private java.math.BigDecimal valorDiaVac = java.math.BigDecimal.ZERO; // = totValDiv

    private int diasNormales = 0;
    private int diasAdicionales = 0;
    private int diasGozados = 0;
    private int diasPendientes = 0;
    
    
    public static final int INT_CON_LINEA = 0;                  //Índice de columna "Línea" de la Tabla de Contactos
    public static final int INT_CON_FE_INI = 1;             
    public static final int INT_CON_FE_FIN = 2;                  
    public static final int INT_CON_TOT_ING = 3;             
    public static final int INT_CON_DIAS = 4;       
    public static final int INT_CON_PRO_VAC = 5;
    public static final int INT_CON_VAL_VAC = 6;
    
    public static final int INT_DET_VAC_PER          = 0;
    public static final int INT_DET_VAC_DIA_NOR      = 1;
    public static final int INT_DET_VAC_DIA_ADI      = 2;
    public static final int INT_DET_VAC_UTILIZADOS   = 3;
    public static final int INT_DET_VAC_DIA_TOM      = 4;
    public static final int INT_DET_VAC_DIA_PEN      = 5;
    public static final int INT_DET_VAC_VALOR        = 6;
    
    
    
    
    public ZafTblMod zafTblModCon;                              //Table Model para la Tabla de Contactos
    public ZafTblMod zafTblDetVac;
    private Vector vecDat,vecReg,vecDat2, vecReg2;
    private boolean blnHayCam;
    
    private ZafRecHum86_03 objRecHum85;                            //JInternalFrame manejado
 
    private boolean blnEstCarSolHSE = false;
    
     public ZafRecHum86_03 (ZafParSis obj) {
         
        try{
         ///   System.out.println(".....llegallll....");
           
            objParSis=(ZafParSis)obj.clone();
            objUti = new ZafUtil();
            
            initComponents();
            configurarFrm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
         
    }
      
    
    public void Configura_ventana_consulta() {
       
        if (blnEstCarSolHSE) {
            vecDat = null;
            //objTooBar.consultarReg_2();
        }
    }
    
    public ZafRecHum86_03(Librerias.ZafParSis.ZafParSis obj, String strCodDoc, String strEmple, String strDias, String strCodTra, String strFecDes, String strFecHas) {
        try {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            configurarFrm();
            //this.setTitle("  " + strVer);
            //lblTit.setText(objParSis.getNombreMenu());
            
            this.diasParam = strDias;
            
            String strCodDocVac = strCodDoc;
            String strEmpleVac = strEmple;
            String strDiasVac = strDias;
            String strCodTraVac = strCodTra;
            String strFecDesVac = strFecDes;
            String strFecHasVac = strFecHas;
            
            
            LblSolicitud.setText(strCodDocVac);
            LblEmpleado.setText(strEmpleVac);
            LblDias.setText(strDiasVac);
            
//            LblDesde.setText(strFecDesVac);
//            LblHasta.setText(strFecHasVac);
            if (strFecDesVac == null || strFecDesVac.trim().equals("") || strFecDesVac.trim().equalsIgnoreCase("null")) {
                LblDesde.setText("N/A");
            } else {
                LblDesde.setText(strFecDesVac);
            }

            if (strFecHasVac == null || strFecHasVac.trim().equals("") || strFecHasVac.trim().equalsIgnoreCase("null")) {
                LblHasta.setText("N/A");
            } else {
                LblHasta.setText(strFecHasVac);
            }
            
            
            blnEstCarSolHSE = true;

            objUti = new ZafUtil();
       
            
            cargarRegTabla(strCodTra,strCodDoc);
            cargarDatCab(strCodTra);            
            
            // Usar el valor que ya calculaste en tu código
        
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
  
        
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panBar = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbhisVac = new javax.swing.JTable();
        lblTit = new javax.swing.JLabel();
        LblCargo = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LblIniCon = new javax.swing.JLabel();
        LblDesde = new javax.swing.JLabel();
        LblHasta = new javax.swing.JLabel();
        LblEmpleado = new javax.swing.JLabel();
        LblCedula = new javax.swing.JLabel();
        LblSolicitud = new javax.swing.JLabel();
        LblDias = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDetVac = new javax.swing.JTable();
        lblTit1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
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

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        panDat.setLayout(null);

        jLabel6.setText("Cargo:"); // NOI18N
        panDat.add(jLabel6);
        jLabel6.setBounds(20, 70, 50, 20);

        jLabel4.setText("Fec. Hasta:");
        panDat.add(jLabel4);
        jLabel4.setBounds(20, 120, 70, 20);

        jLabel3.setText("Fec. Desde:");
        panDat.add(jLabel3);
        jLabel3.setBounds(20, 100, 80, 20);

        jLabel1.setText("Cedula:");
        panDat.add(jLabel1);
        jLabel1.setBounds(430, 50, 110, 20);

        tbhisVac.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbhisVac);

        panDat.add(jScrollPane1);
        jScrollPane1.setBounds(20, 257, 608, 265);

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("VACACIONES");
        panDat.add(lblTit);
        lblTit.setBounds(0, 0, 650, 19);
        panDat.add(LblCargo);
        LblCargo.setBounds(100, 70, 270, 20);
        panDat.add(jSeparator2);
        jSeparator2.setBounds(20, 150, 600, 10);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("DETALLE DEL PERIODO EN MESES");
        panDat.add(jLabel12);
        jLabel12.setBounds(20, 160, 210, 15);

        jLabel2.setText("Fe. Ini. Contrato:");
        panDat.add(jLabel2);
        jLabel2.setBounds(430, 70, 110, 20);

        jLabel13.setText("Empleado:"); // NOI18N
        panDat.add(jLabel13);
        jLabel13.setBounds(20, 50, 60, 20);
        panDat.add(LblIniCon);
        LblIniCon.setBounds(540, 70, 150, 20);
        panDat.add(LblDesde);
        LblDesde.setBounds(100, 100, 220, 20);
        panDat.add(LblHasta);
        LblHasta.setBounds(100, 120, 220, 20);
        panDat.add(LblEmpleado);
        LblEmpleado.setBounds(100, 50, 280, 20);
        panDat.add(LblCedula);
        LblCedula.setBounds(540, 50, 150, 20);
        panDat.add(LblSolicitud);
        LblSolicitud.setBounds(540, 100, 150, 20);
        panDat.add(LblDias);
        LblDias.setBounds(540, 120, 150, 20);

        jLabel5.setText("Nro. Solicitud:");
        panDat.add(jLabel5);
        jLabel5.setBounds(430, 100, 80, 20);

        jLabel7.setText("Dias Solicitados:");
        panDat.add(jLabel7);
        jLabel7.setBounds(430, 120, 100, 20);

        tbDetVac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane3.setViewportView(tbDetVac);

        panDat.add(jScrollPane3);
        jScrollPane3.setBounds(20, 180, 543, 59);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        lblTit1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit1.setText("TUVAL S.A.");
        getContentPane().add(lblTit1, java.awt.BorderLayout.NORTH);

        setBounds(0, 0, 661, 632);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    
    }//GEN-LAST:event_formInternalFrameClosing
   
   
   private void initTbl(){
        //Títulos de Cabecera
        Vector vecCabCon=new Vector();
        vecCabCon.clear();
        vecCabCon.add(INT_CON_LINEA, "");
        vecCabCon.add(INT_CON_FE_INI, "<html><b> Fe.Inicio <b><html>");
        vecCabCon.add(INT_CON_FE_FIN, "<html><b>Fe.Fin <b><html>");
        vecCabCon.add(INT_CON_TOT_ING, "<html><b>Tot.Ingresos <b><html>");
        vecCabCon.add(INT_CON_DIAS, "<html><b>Dias <b><html>");
        vecCabCon.add(INT_CON_PRO_VAC, "<html><b>Prov.Vac <b><html>");
        vecCabCon.add(INT_CON_VAL_VAC, "<html><b>Val.Vac <b><html>");
        
        Vector vecDetVac = new Vector();
        vecDetVac.clear();
        ///vecDetVac.add(INT_DET_VAC_LINEA, ""); 
        
        vecDetVac.add(INT_DET_VAC_PER, "<html><b>Periodo<b><html>");
        vecDetVac.add(INT_DET_VAC_DIA_NOR, "<html><b>Dias<br>Normales<b><html>");
        vecDetVac.add(INT_DET_VAC_DIA_ADI, "<html><b>Dias<br>Adicionales<b><html>");
        vecDetVac.add(INT_DET_VAC_UTILIZADOS, "<html><b>GOZADOS</b></html>");
        vecDetVac.add(INT_DET_VAC_DIA_TOM, "<html><b>Dias<br>Tomados<b><html>");
        vecDetVac.add(INT_DET_VAC_DIA_PEN, "<html><b>Dias<br>Pendientes<b><html>");
        vecDetVac.add(INT_DET_VAC_VALOR, "<html><b>Valor<b><html>");
        

        zafTblModCon = new ZafTblMod();
        zafTblDetVac = new ZafTblMod();
        zafTblModCon.setHeader(vecCabCon);
        zafTblDetVac.setHeader(vecDetVac);
        
        tbhisVac.setModel(zafTblModCon);
        tbhisVac.setRowSelectionAllowed(true);
        
        tbDetVac.setModel(zafTblDetVac);
        tbDetVac.setRowSelectionAllowed(true);
        
        tbDetVac.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        // Aumentar la altura del encabezado de la tabla
        tbDetVac.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tbDetVac.getColumnCount(); i++) {
            tbDetVac.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        new ZafColNumerada(tbhisVac,INT_CON_LINEA);

        tbhisVac.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu = new ZafTblPopMnu(tbhisVac);
        
        //Ancho de columnas
        TableColumnModel tcmAux = tbhisVac.getColumnModel();
        tcmAux.getColumn(INT_CON_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_CON_FE_INI).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_FE_FIN).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_TOT_ING).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_DIAS).setPreferredWidth(80); 
        tcmAux.getColumn(INT_CON_PRO_VAC).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_VAL_VAC).setPreferredWidth(100);
        
        TableColumnModel tcmAux2 = tbDetVac.getColumnModel();
        
        tcmAux2.getColumn(INT_DET_VAC_PER).setPreferredWidth(90);
        tcmAux2.getColumn(INT_DET_VAC_DIA_NOR).setPreferredWidth(75);
        tcmAux2.getColumn(INT_DET_VAC_DIA_ADI).setPreferredWidth(80);
        tcmAux2.getColumn(INT_DET_VAC_UTILIZADOS).setPreferredWidth(70);
        tcmAux2.getColumn(INT_DET_VAC_DIA_TOM).setPreferredWidth(70);
        tcmAux2.getColumn(INT_DET_VAC_DIA_PEN).setPreferredWidth(80);
        tcmAux2.getColumn(INT_DET_VAC_VALOR).setPreferredWidth(75);
//        

       
       //zafTblDetVac.addSystemHiddenColumn(INT_DET_VAC_LINEA, tbDetVac);
        
        
        //Columnas editables
        Vector vecColEdi = new Vector(); 
        
        zafTblModCon.setColumnasEditables(vecColEdi);
        vecColEdi = null;
  
        //zafTblModCon.setModoOperacion(ZafTblMod.INT_TBL_INS); 
        zafTblModCon.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        
        // Aplicar TotalesRenderer a todas las columnas
        // Aplicar el renderer a TODAS las columnas para que la línea cubra toda la fila
        for (int i = 0; i < tbhisVac.getColumnCount(); i++) {
            if (i == INT_CON_LINEA) continue; // <-- no tocar la columna de numeración
            tbhisVac.getColumnModel().getColumn(i).setCellRenderer(new TotalesLineRenderer());
        }

    }
   
    /**
     * 
     * @return 
     */
     private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            objPerUsr=new ZafPerUsr(objParSis);
            //this.setTitle(strAux + strVer);
            initTbl();
            agregarBotonImprimir();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
   
    private boolean cargarRegTabla(  String strco_tra, String strCodDoc) {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
          java.sql.Statement stm2 = null;
        java.sql.ResultSet rst2 = null;
        
        String strSql = "" ;
        Double TotalVac = 0.0;
        boolean blnRes = true;  
        int i;
  
        try {
            con= DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            vecDat = new Vector();
 
            if (con != null) {
                stm = con.createStatement(); 
                strSql = "";      
                
                strSql = "";
                strSql += "SELECT ";
                strSql += "    fe_ini, ";
                strSql += "    fe_fin, ";
                strSql += "    nd_tot, ";
                strSql += "    ne_dias, ";
                strSql += "    nd_provac, ";
                strSql += "    nd_valvac, ";
                strSql += "    ne_perdes AS desde, ";
                strSql += "    ne_perhas AS hasta ";
                strSql += "FROM tbm_detpervactra ";
                strSql += "WHERE co_tra = " + strco_tra + " ";
                strSql += "  AND ne_perhas = ( ";
                strSql += "        SELECT ne_perhasvac ";
                strSql += "        FROM tbm_detsolvac ";
                strSql += "        WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "          AND co_loc = " + objParSis.getCodigoLocal() + " ";
                strSql += "          AND co_tipdoc = 322 ";
                strSql += "          AND co_doc = " + strCodDoc + " ";
                strSql += "          AND co_reg = 1 ";
                strSql += "        LIMIT 1 ";
                strSql += "  ) ";
                strSql += "  AND ne_perdes = ne_perhas - 1 ";
                strSql += "ORDER BY fe_ini;";
                System.out.println("cargarRegTabla: "+strSql);
                
                rst = stm.executeQuery(strSql);

                
                
                vecDat.clear(); 
                i = 0;
                
                int    totDias = 0;
                java.math.BigDecimal totProv = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totVal  = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totIng  = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totDia  = java.math.BigDecimal.ZERO;
                
               
               boolean primeraFila = true;
                
                if (rst.next()) {
                    vecDat = new Vector();
                    do {
                        
                        if (primeraFila) {
                        anioDesde = rst.getInt("desde");
                        anioHasta = rst.getInt("hasta");
                        primeraFila = false;
        }
                        
                        vecReg = new Vector();     
                        vecReg.add(INT_CON_LINEA, ""); 
                        vecReg.add(INT_CON_FE_INI,  rst.getString("fe_ini"));                               
                        vecReg.add(INT_CON_FE_FIN, rst.getString("fe_fin"));
                               
                        vecReg.add(INT_CON_TOT_ING, rst.getString("nd_tot"));
                        vecReg.add(INT_CON_DIAS, rst.getString("ne_dias"));
                        vecReg.add(INT_CON_PRO_VAC, rst.getString("nd_provac"));
                        vecReg.add(INT_CON_VAL_VAC, rst.getString("nd_valvac"));
                              
                        // === Sumas ===
                        totDias += rst.getInt("ne_dias");
                        java.math.BigDecimal bTot = rst.getBigDecimal("nd_tot");
                        java.math.BigDecimal bPro = rst.getBigDecimal("nd_provac");
                        java.math.BigDecimal bVal = rst.getBigDecimal("nd_valvac");
                        if (bTot != null) totIng = totIng.add(bTot);
                        if (bPro != null) totProv = totProv.add(bPro);
                        if (bVal != null) totVal  = totVal.add(bVal);
                                
                        vecDat.add(vecReg);
                        i++;
                     }
                    while (rst.next());
                        // === Fila TOTAL ===
                        Vector vecTot = new Vector();
                        vecTot.add(INT_CON_LINEA, "");
                        vecTot.add(INT_CON_FE_INI,  "<html><b>"+ "TOTALES:" + "</b></html>");           // vacío
                        vecTot.add(INT_CON_FE_FIN, "");     // etiqueta
                        vecTot.add(INT_CON_TOT_ING, "");       // suma ingresos
                        vecTot.add(INT_CON_DIAS,   "<html><b>"+  totDias + "</b></html>");      // suma días
                        vecTot.add(INT_CON_PRO_VAC, "<html><b>$"+  totProv + "</b></html>");      // suma provac
                        vecTot.add(INT_CON_VAL_VAC, "<html><b>$"+ totVal + "</b></html>");       // suma valvac
                        vecDat.add(vecTot);
                                
                        BigDecimal totValDiv = totVal.divide(new BigDecimal(15), 2, RoundingMode.HALF_UP);
                                
                        Vector vecTotDiv = new Vector();
                        vecTotDiv.add(INT_CON_LINEA, "");
                        vecTotDiv.add(INT_CON_FE_INI,  "<html><b>VALOR DIA: </b></html>");
                        vecTotDiv.add(INT_CON_FE_FIN,  "");
                        vecTotDiv.add(INT_CON_TOT_ING, "");   // vacío
                        vecTotDiv.add(INT_CON_DIAS,    "");   // vacío
                        vecTotDiv.add(INT_CON_PRO_VAC, "");   // vacío
                        vecTotDiv.add(INT_CON_VAL_VAC, "<html><body style='background-color:yellow;'><b>$"+ totValDiv + "</b></body></html>");
                        vecDat.add(vecTotDiv);
                                
                                
                        this.valorDiaVac = totValDiv;

                        // Leer la fotografía histórica guardada en la solicitud
                        if (!cargarResumenSolicitud(strCodDoc)) {
                            return false;
                        }

                        // Llenar la tabla superior
                        actualizarResumenDetVac();
                               
                            
                       }else{
                    }
                }
                
                zafTblModCon.setData(vecDat);
                //tbhisVac.setModel(zafTblModCon);                 
                
                con.close();
                con = null;
          
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    } 
     
    
    
    private boolean cargarDatCab(  String strco_tra ) {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "" ;
        boolean blnRes = true;  
  
        try {
            con= DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            
 
            if (con != null) {
                stm = con.createStatement(); 
                strSql = "";      
                //strSql += "select * from tbm_cfgvactra where co_emp="+ objParSis.getCodigoEmpresa() + " and co_tra =" + strco_tra  ;
                strSql += "select a.co_tra, a.tx_ide as cedula, b.fe_inicon as inicon, b.co_car, c.tx_nomcar as cargo";
                strSql += " from tbm_tra a";
                strSql += " inner join tbm_traemp as b ON a.co_tra = b.co_tra and b.co_emp = 1";
                strSql += " inner join tbm_carlab as c ON b.co_car = c.co_car ";
                strSql += " WHERE a.co_tra = "+ strco_tra; ;
                System.out.println("PruebaCab: "+strSql); 
        
                rst = stm.executeQuery(strSql);
                
                if(rst.next()) {
                    String cargo = rst.getString("cargo");
                    String cedula = rst.getString("cedula");
                    String inicon = rst.getString("inicon");
                    
                    
                    LblCargo.setText(cargo);
                    LblCedula.setText(cedula);
                    LblIniCon.setText(inicon);
                    
                }
                               
            }
                con.close();
                con = null;
          
        } catch (Exception e) {
            blnRes = false;}
        return blnRes;
    }
    
    
    private void actualizarResumenDetVac() {

        int dias = 0;
        try {
            dias = Integer.parseInt(diasParam);
        } catch (NumberFormatException e) {
            dias = 0;
        }

        BigDecimal total = valorDiaVac.multiply(new BigDecimal(dias))
                                      .setScale(2, RoundingMode.HALF_UP);

        String periodo = (anioDesde > 0 && anioHasta > 0)
                ? (anioDesde + "-" + anioHasta)
                : "";

        Vector vecDatDet = new Vector();
        Vector vecRegDet = new Vector();

        vecRegDet.add(INT_DET_VAC_PER, periodo);
        vecRegDet.add(INT_DET_VAC_DIA_NOR, String.valueOf(diasNormales));
        vecRegDet.add(INT_DET_VAC_DIA_ADI, String.valueOf(diasAdicionales));
        vecRegDet.add(INT_DET_VAC_UTILIZADOS, String.valueOf(diasGozados));
        vecRegDet.add(INT_DET_VAC_DIA_TOM, "<html><b>" + String.valueOf(dias) + "</b></html>");
        vecRegDet.add(INT_DET_VAC_DIA_PEN, String.valueOf(diasPendientes));
        vecRegDet.add(INT_DET_VAC_VALOR, "<html><b>$ " + total + "</b></html>");

        vecDatDet.add(vecRegDet);

        zafTblDetVac.setData(vecDatDet);
    }
    
    private boolean cargarResumenSolicitud(String strCodDoc) {
        java.sql.Connection conLoc = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        boolean blnRes = true;

        try {
            // Reiniciar valores para evitar mostrar datos de otra solicitud
            diasNormales = 0;
            diasAdicionales = 0;
            diasGozados = 0;
            diasPendientes = 0;

            conLoc = DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos()
            );

            if (conLoc != null) {
                stmLoc = conLoc.createStatement();

                String strSql = "";

                strSql += "SELECT ";
                strSql += "    ne_dianorvac, ";
                strSql += "    ne_diaadivac, ";
                strSql += "    ne_diaconsvac, ";
                strSql += "    ne_diapenvac ";
                strSql += "FROM tbm_detsolvac ";
                strSql += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSql += "  AND co_loc = " + objParSis.getCodigoLocal() + " ";
                strSql += "  AND co_tipdoc = 322 ";
                strSql += "  AND co_doc = " + strCodDoc + " ";
                strSql += "  AND co_reg = 1 ";
                strSql += "LIMIT 1";

                System.out.println("cargarResumenSolicitud: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    diasNormales = rstLoc.getInt("ne_dianorvac");
                    diasAdicionales = rstLoc.getInt("ne_diaadivac");
                    diasGozados = rstLoc.getInt("ne_diaconsvac");
                    diasPendientes = rstLoc.getInt("ne_diapenvac");

                    System.out.println("Días normales    : " + diasNormales);
                    System.out.println("Días adicionales : " + diasAdicionales);
                    System.out.println("Días consumidos  : " + diasGozados);
                    System.out.println("Días pendientes  : " + diasPendientes);
                } else {
                    System.out.println(
                            "No se encontró el resumen histórico para la solicitud: "
                            + strCodDoc
                    );
                }
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);

        } finally {
            try {
                if (rstLoc != null) {
                    rstLoc.close();
                }

                if (stmLoc != null) {
                    stmLoc.close();
                }

                if (conLoc != null) {
                    conLoc.close();
                }
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        return blnRes;
    }

 
    private void agregarBotonImprimir() {
    try {
        butImprimir = new JButton("Imprimir");

        butImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirReporte();
            }
        });

        JPanel panBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panBotones.add(butImprimir);

        panBar.add(panBotones, BorderLayout.EAST);
        panBar.revalidate();
        panBar.repaint();

    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}
    
    private void imprimirReporte() {
    try {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Reporte de Vacaciones");

        PageFormat formato = job.defaultPage();
        formato.setOrientation(PageFormat.PORTRAIT);

        job.setPrintable(new ReporteVacacionesPrintable(), formato);

        if (job.printDialog()) {
            job.print();
        }

    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}
   
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formInternalFrameOpened
 
   private class ZafTblModLis implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                
            }
        }
    }
   
   
    // Renderer para resaltar la fila
    // Renderer con borde negro arriba
    // Pone un borde negro arriba en TODA la penúltima fila
    class TotalesLineRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);


            if (c instanceof javax.swing.JComponent) {
                ((javax.swing.JComponent) c).setOpaque(true);
            }

            // para poner negrita y bordes negros para simular una separacion
            int filaTotales = table.getRowCount() - 2;

            if (row == filaTotales) {
                //negrita en toda la fila
                c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
                // Borde superior negro en todas las columnas
                ((javax.swing.JLabel) c).setBorder(
                    javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, java.awt.Color.BLACK)
                );
            } else {
                // Resto normal sin fondo
                if (!isSelected) {
                    c.setBackground(java.awt.Color.WHITE);
                }
                ((javax.swing.JLabel) c).setBorder(null);
            }

            return c;
        }
    }

    private class ReporteVacacionesPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;

            g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            int x = (int) pageFormat.getImageableX() + 20;
            int y = (int) pageFormat.getImageableY() + 20;
            int anchoPagina = (int) pageFormat.getImageableWidth() - 40;

            Font fuenteTitulo = new Font("SansSerif", Font.BOLD, 11);
            Font fuenteSubtitulo = new Font("SansSerif", Font.BOLD, 10);
            Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 8);
            Font fuenteNegrita = new Font("SansSerif", Font.BOLD, 8);

            // =========================
            // TÍTULO
            // =========================
            g2d.setFont(fuenteTitulo);
            dibujarTextoCentrado(g2d, "TUVAL S.A.", x, y, anchoPagina);
            y += 15;

            dibujarTextoCentrado(g2d, "VACACIONES", x, y, anchoPagina);
            y += 30;

            // =========================
            // DATOS DE CABECERA
            // =========================
            g2d.setFont(fuenteNegrita);

            int xIzq = x;
            int xDer = x + 290;

            dibujarEtiquetaValor(g2d, "Empleado:", LblEmpleado.getText(), xIzq, y, 50);
            dibujarEtiquetaValor(g2d, "Cedula:", LblCedula.getText(), xDer, y, 70);
            y += 15;

            dibujarEtiquetaValor(g2d, "Cargo:", LblCargo.getText(), xIzq, y, 50);
            dibujarEtiquetaValor(g2d, "Fe. Ini. Contrato:", LblIniCon.getText(), xDer, y, 70);
            y += 25;

            dibujarEtiquetaValor(g2d, "Fec. Desde:", LblDesde.getText(), xIzq, y, 50);
            dibujarEtiquetaValor(g2d, "Nro. Solicitud:", LblSolicitud.getText(), xDer, y, 70);
            y += 15;

            dibujarEtiquetaValor(g2d, "Fec. Hasta:", LblHasta.getText(), xIzq, y, 50);
            dibujarEtiquetaValor(g2d, "Dias Solicitados:", LblDias.getText(), xDer, y, 70);
            y += 18;

            // Línea separadora
            g2d.drawLine(x, y, x + anchoPagina, y);
            y += 25;

            // =========================
            // DETALLE DEL PERIODO
            // =========================
            g2d.setFont(fuenteSubtitulo);
            g2d.drawString("DETALLE DEL PERIODO EN MESES", x, y);
            y += 18;

            y = dibujarTabla(g2d, tbDetVac, x, y, anchoPagina);
            y += 25;

            // =========================
            // TABLA PRINCIPAL
            // =========================
            
            y = dibujarTabla(g2d, tbhisVac, x, y, anchoPagina);

            // =========================
            // FIRMAS
            // =========================
            y += 75;
            dibujarFirmas(g2d, x, y, anchoPagina);

            return Printable.PAGE_EXISTS;
        }
    }

    private void dibujarFirmas(Graphics2D g2d, int x, int y, int anchoPagina) {
        Font fuenteFirma = new Font("SansSerif", Font.BOLD, 8);
        g2d.setFont(fuenteFirma);

        // Mientras menor sea este valor, más corta será cada línea.
        int anchoLinea = 120;

        // Mientras menor sea este margen, más hacia los extremos se moverán.
        int margenFirmas = 20;

        // Firma izquierda
        int xElaborado = x + margenFirmas;

        // Firma derecha
        int xAprobado = x + anchoPagina - anchoLinea - margenFirmas;

        // Línea individual de ELABORADO
        g2d.drawLine(xElaborado, y, xElaborado + anchoLinea, y);

        // Línea individual de APROBADO
        g2d.drawLine(xAprobado, y, xAprobado + anchoLinea, y);

        y += 14;

        // Textos centrados debajo de cada línea
        dibujarTextoCentrado(g2d, "ELABORADO", xElaborado, y, anchoLinea);
        dibujarTextoCentrado(g2d, "APROBADO", xAprobado, y, anchoLinea);
    }

    private void dibujarTextoCentrado(Graphics2D g2d, String texto, int x, int y, int ancho) {
        FontMetrics fm = g2d.getFontMetrics();
        int anchoTexto = fm.stringWidth(texto);
        int xTexto = x + ((ancho - anchoTexto) / 2);
        g2d.drawString(texto, xTexto, y);
    }

    private void dibujarEtiquetaValor(Graphics2D g2d, String etiqueta, String valor, int x, int y, int anchoEtiqueta) {
        Font fuenteActual = g2d.getFont();

        g2d.setFont(fuenteActual.deriveFont(Font.BOLD));
        g2d.drawString(etiqueta, x, y);

        g2d.setFont(fuenteActual.deriveFont(Font.BOLD));
        g2d.drawString(valor == null ? "" : limpiarTexto(valor), x + anchoEtiqueta, y);

        g2d.setFont(fuenteActual);
    }

    private int dibujarTabla(Graphics2D g2d, JTable tabla, int x, int y, int anchoMaximo) {
        TableModel modelo = tabla.getModel();

        int columnas = tabla.getColumnCount();
        int filas = modelo.getRowCount();

        if (columnas == 0) {
            return y;
        }

        /*
         * La tabla pequeña necesita una cabecera más alta porque contiene
         * títulos divididos en dos líneas.
         */
        int altoCabecera = (tabla == tbDetVac) ? 28 : 18;
        int altoFila = 16;

        int[] anchos = obtenerAnchosColumnas(tabla, anchoMaximo);

        Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 8);
        Font fuenteNegrita = new Font("SansSerif", Font.BOLD, 8);

        int yActual = y;
        int xActual = x;

        // =========================
        // CABECERA
        // =========================
        g2d.setFont(fuenteNegrita);

        xActual = x;

        for (int col = 0; col < columnas; col++) {

            int colModelo = tabla.convertColumnIndexToModel(col);

            // Fondo gris de la cabecera
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(xActual,yActual,anchos[col],altoCabecera);

            // Borde de la celda
            g2d.setColor(Color.BLACK);
            g2d.drawRect(xActual,yActual,anchos[col],altoCabecera);

            if (tabla == tbDetVac) {

                /*
                 * Para la tabla pequeña no usamos limpiarTexto()
                 * porque eliminaría el salto de línea.
                 */
                String linea1 = "";
                String linea2 = "";

                if (colModelo == INT_DET_VAC_PER) {
                    linea1 = "Periodo";

                } else if (colModelo == INT_DET_VAC_DIA_NOR) {
                    linea1 = "Dias";
                    linea2 = "Normales";

                } else if (colModelo == INT_DET_VAC_DIA_ADI) {
                    linea1 = "Dias";
                    linea2 = "Adicionales";

                } else if (colModelo == INT_DET_VAC_UTILIZADOS) {
                    linea1 = "GOZADOS";

                } else if (colModelo == INT_DET_VAC_DIA_TOM) {
                    linea1 = "Dias";
                    linea2 = "Tomados";

                } else if (colModelo == INT_DET_VAC_DIA_PEN) {
                    linea1 = "Dias";
                    linea2 = "Pendientes";

                } else if (colModelo == INT_DET_VAC_VALOR) {
                    linea1 = "Valor";
                }

                dibujarCabeceraDosLineas(
                        g2d,
                        linea1,
                        linea2,
                        xActual,
                        yActual,
                        anchos[col],
                        altoCabecera
                );

            } else {

                String titulo = limpiarTexto(
                        tabla.getColumnName(col)
                );

                dibujarTextoEnCelda(
                        g2d,
                        titulo,
                        xActual,
                        yActual,
                        anchos[col],
                        altoCabecera,
                        true
                );
            }

            xActual += anchos[col];
        }

        g2d.setColor(Color.BLACK);
        yActual += altoCabecera;

        // =========================
        // FILAS
        // =========================
        g2d.setFont(fuenteNormal);

        for (int fila = 0; fila < filas; fila++) {
            xActual = x;

            boolean esFilaTotales = false;
            boolean esFilaValorDia = false;

            if (tabla == tbhisVac) {
                esFilaTotales = (fila == filas - 2);
                esFilaValorDia = (fila == filas - 1);
            }

            for (int col = 0; col < columnas; col++) {

                int colModelo = tabla.convertColumnIndexToModel(col);

                Object valorObj = modelo.getValueAt(fila,colModelo);

                String valor = valorObj == null ? "" : limpiarTexto(valorObj.toString());

                /*
                 * Numeración automática de la tabla principal.
                 */
                if (tabla == tbhisVac && colModelo == INT_CON_LINEA && !esFilaTotales && !esFilaValorDia)
                {
                    valor = String.valueOf(fila + 1);
                }

                /*
                 * Fondo amarillo para la fila VALOR DIA.
                 */
                if (esFilaValorDia && colModelo == INT_CON_VAL_VAC)
                {
                    g2d.setColor(Color.YELLOW);

                    g2d.fillRect(xActual,yActual,anchos[col],altoFila);

                    g2d.setColor(Color.BLACK);
                }
                
                 // Fondo gris suave para Días Tomados y Valor del mini kardex
                boolean pintarCeldaResumen = tabla == tbDetVac && (colModelo == INT_DET_VAC_DIA_TOM || colModelo == INT_DET_VAC_VALOR);

                if (pintarCeldaResumen) {
                    g2d.setColor(new Color(235, 235, 235));
                    g2d.fillRect(xActual, yActual, anchos[col], altoFila);
                    g2d.setColor(Color.BLACK);
                }

                boolean esColumnaValorDetVac = tabla == tbDetVac  && colModelo == INT_DET_VAC_VALOR;

                /*
                 * Fuentes especiales.
                 */
                if (esFilaTotales) {

                    g2d.setStroke(new java.awt.BasicStroke(1.5f) );

                    g2d.drawLine(xActual,yActual,xActual + anchos[col],yActual);

                    g2d.setStroke(new java.awt.BasicStroke(1f));

                    g2d.setFont(fuenteNegrita);

                } else if (esFilaValorDia) {

                    g2d.setFont(fuenteNegrita);

                } else if (esColumnaValorDetVac) {

                    g2d.setFont(fuenteNegrita);

                } else {

                    g2d.setFont(fuenteNormal);
                }

                /*
                 * Borde de la celda.
                 */
                g2d.drawRect(xActual,yActual,anchos[col],altoFila);

                /*
                 * En el mini kardex centramos los datos.
                 * En la tabla principal mantenemos la alineación actual.
                 */
                boolean centrarValor = tabla == tbDetVac;

                dibujarTextoEnCelda(g2d,valor,xActual,yActual,anchos[col],altoFila,centrarValor);

                xActual += anchos[col];
            }

            yActual += altoFila;
        }

        return yActual;
    }
    
    private void dibujarCabeceraDosLineas(Graphics2D g2d,String linea1,String linea2,int x,int y,int ancho,int alto)
    {
        if (linea1 == null) {
            linea1 = "";
        }

        if (linea2 == null) {
            linea2 = "";
        }

        FontMetrics fm = g2d.getFontMetrics();

        /*
         * Encabezado de una sola línea.
         */
        if (linea2.trim().isEmpty()) {

            int anchoTexto = fm.stringWidth(linea1);

            int xTexto = x + (ancho - anchoTexto) / 2;

            int yTexto = y + ((alto - fm.getHeight()) / 2) + fm.getAscent();

            g2d.drawString(linea1,xTexto,yTexto);
            
            return;
        }

        /*
         * Encabezado de dos líneas.
         */
        int separacion = 1;
        int altoTexto = fm.getHeight() * 2 + separacion;

        int yPrimera = y + ((alto - altoTexto) / 2) + fm.getAscent();

        int ySegunda = yPrimera + fm.getHeight() + separacion;

        int anchoLinea1 = fm.stringWidth(linea1);
        int anchoLinea2 = fm.stringWidth(linea2);

        int xLinea1 = x + ( ancho - anchoLinea1 ) / 2;

        int xLinea2 = x + ( ancho - anchoLinea2
        ) / 2;

        g2d.drawString(linea1,xLinea1,yPrimera);

        g2d.drawString(linea2,xLinea2,ySegunda);
}
    

    private int[] obtenerAnchosColumnas(JTable tabla, int anchoMaximo) {
        int columnas = tabla.getColumnCount();

        // Medidas especiales SOLO para la impresión de la tabla pequeña tbDetVac
        if (tabla == tbDetVac) {
            int[] anchosDetVac = new int[columnas];

            for (int i = 0; i < columnas; i++) {
                int colModelo = tabla.convertColumnIndexToModel(i);

                if (colModelo == INT_DET_VAC_PER) {
                    anchosDetVac[i] = 55;   // Periodo

                } else if (colModelo == INT_DET_VAC_DIA_NOR) {
                    anchosDetVac[i] = 45;   // Días normales

                } else if (colModelo == INT_DET_VAC_DIA_ADI) {
                    anchosDetVac[i] = 50;   // Días adicionales

                } else if (colModelo == INT_DET_VAC_UTILIZADOS) {
                    anchosDetVac[i] = 45;   // Gozados

                } else if (colModelo == INT_DET_VAC_DIA_TOM) {
                    anchosDetVac[i] = 45;   // Días tomados

                } else if (colModelo == INT_DET_VAC_DIA_PEN) {
                    anchosDetVac[i] = 50;   // Días pendientes

                } else if (colModelo == INT_DET_VAC_VALOR) {
                    anchosDetVac[i] = 45;   // Valor

                } else {
                    anchosDetVac[i] = 15;   // Línea oculta o numeración
                }
            }

            return anchosDetVac;
        }

        int[] anchos = new int[columnas];

        int total = 0;

        for (int i = 0; i < columnas; i++) {
            int ancho = tabla.getColumnModel().getColumn(i).getPreferredWidth();

            if (ancho <= 0) {
                ancho = 50;
            }

            anchos[i] = ancho;
            total += ancho;
        }

        if (total > anchoMaximo) {
            double factor = (double) anchoMaximo / (double) total;

            total = 0;
            for (int i = 0; i < columnas; i++) {
                anchos[i] = Math.max(25, (int) Math.floor(anchos[i] * factor));
                total += anchos[i];
            }
        }

        return anchos;
}

    private void dibujarTextoEnCelda(Graphics2D g2d, String texto, int x, int y, int ancho, int alto, boolean centrado) {
        if (texto == null) {
            texto = "";
        }

        FontMetrics fm = g2d.getFontMetrics();

        int xTexto;

        if (centrado) {
            int anchoTexto = fm.stringWidth(texto);
            xTexto = x + ((ancho - anchoTexto) / 2);
        } else {
            xTexto = x + 3;
        }

        int yTexto = y + ((alto - fm.getHeight()) / 2) + fm.getAscent();

        g2d.drawString(texto, xTexto, yTexto);
    }

    private String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replaceAll("(?i)<br\\s*/?>", " ")
                .replaceAll("<[^>]*>", "")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .trim();
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblCargo;
    private javax.swing.JLabel LblCedula;
    private javax.swing.JLabel LblDesde;
    private javax.swing.JLabel LblDias;
    private javax.swing.JLabel LblEmpleado;
    private javax.swing.JLabel LblHasta;
    private javax.swing.JLabel LblIniCon;
    private javax.swing.JLabel LblSolicitud;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTit1;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDat;
    private javax.swing.JTable tbDetVac;
    private javax.swing.JTable tbhisVac;
    // End of variables declaration//GEN-END:variables

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
         
    

    
}