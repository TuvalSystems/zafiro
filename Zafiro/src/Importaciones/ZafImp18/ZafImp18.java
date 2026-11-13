/*
 * ZafImp18.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Importaciones.ZafImp18;
////import Importaciones.ZafImp18.ZafImp18;
////import Importaciones.ZafImp18.ZafImp18;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafUtilGP.HashMapPer.Imp18_ItemsAtri_Cump;
import Librerias.ZafUtilGP.HashMapPer.Imp18_ItemsAtri;
import Librerias.ZafUtilGP.ZafUtilGP;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import Librerias.ZafTblAgr.ColumnGroup;
import Librerias.ZafTblAgr.GroupableTableHeader;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafImp18 extends javax.swing.JDialog  
{
    //Variables.
     private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafUtilGP objUtiGP;
    private ZafTblMod objTblMod;
    public String strObs;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    //Variables de la clase.
   
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_LINEA=1;
    final int INT_TBL_DAT_CLASE=2;
    final int INT_TBL_DAT_ORDEN=3;
    final int INT_TBL_DAT_PROM_PESO=4;
    final int INT_TBL_DAT_PREC_SUG_PESO=5;
    final int INT_TBL_DAT_PROM_DOLAR=6;
    final int INT_TBL_DAT_PREC_SUG_DOLAR=7;
    //final int INT_TBL_DAT_PROM_PESO_ACT=8;
    final int INT_TBL_DAT_CANT_ACT=8;
   //final int INT_TBL_DAT_PROM_DOLAR_ACT=10;
    final int INT_TBL_DAT_CANT_DOLAR_ACT=9;
    final int INT_TBL_DAT_CANT_DIF_PESO=10;
    final int INT_TBL_DAT_CANT_DIF_PREC=11;    
    
    
    
    private Vector vecReg,  vecCab, vecAux;
    
    Vector<Vector<Object>> vecCab1 = new Vector<>();

    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private Connection conGP;
    private Statement stmGP;
    private ResultSet rstGP;
    private String strSQLGP;
    private int intCodPrv;
    private int intCodBen;
    private int intCodCta;
    private String strFecFilDoc;
    private int intFilFec;
    private int intExiFec;
    private String strRstFec;
    private String strAux;


    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_REG=0;
    final int INT_ARL_NOM_PED=1;
    final int INT_ARL_EST_ELI=2;
    private java.util.Date datFecAux;    
    private ZafExpoExc objExcel; 
    private ZafThreadGUI objThrGUI;    
    private String strVersion ="v0.4";

    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafImp18(Frame parent, ZafParSis obj)
            //    public ZafCom113_02(Frame parent,  ZafParSis ZafParSis)
    {
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
            arlDat=new ArrayList();
            configurarFrm(); 
    }
    
    public ZafImp18(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        initComponents(); 
        objParSis=obj;
        objUtiGP=new ZafUtilGP(objParSis);          
        arlDat=new ArrayList(); 
        configurarFrm(); 
    }    
    
  private boolean obtenerPedidoSugeridoMensual(int anio, int mes)//
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="";
        boolean blnexis=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());        
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT top 1 tx_itm   \n";
              strSqlZa+=" FROM Dynamics2021..tbm_hisdatimp_ped   \n";
////              strSqlZa+=" WHERE  tx_codalt2='SAN'  \n";
              strSqlZa+=" where ne_mes= "+ mes +" and ne_ani="+anio +"  \n"; 
              
          //    strSqlZa+=" AND TX_CODALT2 !='' and st_REg ='A' ORDER BY tx_codalt2  DESC  \n";      
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              
              while(rstLoc1.next())
              {
                blnexis =true;  
                break;
              }
                
              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR BusquedaAsignacionStockZafiroIva "+ e );
            blnexis=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR BusquedaAsignacionStockZafiroIva "+ e );
           blnexis=false;
         }
        return blnexis;
    }
  
  
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            int ne_ultdiames=0;
            int ne_diaact=0;
            int ne_mesact=0;
            int ne_aniact=0;
            lblMsgSis.setText("Configurando tabla...");
            pgrSis.setIndeterminate(true);
            HashMap<String, Imp18_ItemsAtri>   dataMapConsultaGloba = new HashMap<>();  
            HashMap<String, Imp18_ItemsAtri_Cump>   dataMapConsultaGlobaCumpli = new HashMap<>();  
            LocalDate fechaActual = LocalDate.now();
            ne_ultdiames=objUtiGP.obtenerUltimoDiaDelMesActual();
            ne_diaact = fechaActual.getDayOfMonth(); // Día actual
            ne_mesact = fechaActual.getMonthValue(); // Mes actual
            ne_aniact = fechaActual.getYear(); // Año actual     
            //if (ne_ultdiames ==ne_diaact)
            if (!obtenerPedidoSugeridoMensual(ne_aniact,ne_mesact))
            {
                if (15 ==ne_diaact)
                {
                    if(objUtiGP.ejecucionActualizacionTablaPedidosSugeridosSP ())
                    {
                        if (cargarPresupuestoVentasGlobalesInicial(dataMapConsultaGloba)) 
                        {
                            CargarReg_18(dataMapConsultaGloba); 
                            //Inicializar objetos si no se pudo cargar los datos.
                            lblMsgSis.setText("Listo");
                            pgrSis.setValue(0); 
                        }                     

                    }
                }
                else
                {
                    if (cargarPresupuestoVentasGlobalesIntermedio(dataMapConsultaGlobaCumpli,ne_mesact,ne_aniact)) 
                    {
                        CargarReg_18_Cump(dataMapConsultaGlobaCumpli); 
                        //Inicializar objetos si no se pudo cargar los datos.
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0); 
                    }                         

                }                
            }
            else
            {
                if (cargarPresupuestoVentasGlobalesIntermedio(dataMapConsultaGlobaCumpli,ne_mesact,ne_aniact)) 
                {
                    CargarReg_18_Cump(dataMapConsultaGlobaCumpli); 
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0); 
                }                 
            }    
            
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            { 
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }                
            objThrGUI=null;
        }
    }    
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }       
        private void  configurarFrm()
    {
         // Configuración del tamaño del JDialog
         setSize(800,800);
         
         
         configurarTblDat();
         
////         cargarPresupuestoVentasPedidosNotas(dataMapConsultaGlobaPedNot);
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            

         
         
    } 
    

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {

                case INT_TBL_DAT_LINEA:
                    strMsg = "Compra";
                    break;
                case INT_TBL_DAT_CLASE:
                    strMsg = "SubCompra";
                    break;
                case INT_TBL_DAT_ORDEN:
                    strMsg = "Orden";
                    break;                    
                case INT_TBL_DAT_PROM_PESO:
                    strMsg = "Promedio(KG)";
                    break;   
                case INT_TBL_DAT_PREC_SUG_PESO:
                    strMsg = "Sugerencia de Pedido(KG)";
                    break;
                case INT_TBL_DAT_PROM_DOLAR:
                    strMsg = "Promedio($)";
                    break;
                case INT_TBL_DAT_PREC_SUG_DOLAR:
                    strMsg = "Sugerencia de Pedido ($)";
                    break;
////                case INT_TBL_DAT_PROM_PESO_ACT:
////                    strMsg = "Promedio Comprada Actual (KG)";
////                    break;
                case INT_TBL_DAT_CANT_ACT:
                    strMsg = "Cantidad Comprada Actual (KG)";
                    break;
////                case INT_TBL_DAT_PROM_DOLAR_ACT:
////                    strMsg = "Promedio Venta Actual ($)";
////                    break;   
                case INT_TBL_DAT_CANT_DOLAR_ACT:
                    strMsg = "Cantidad Comprada Actual ($)";
                    break;   

                case INT_TBL_DAT_CANT_DIF_PESO:
                    strMsg = "Diferencia(KG)";
                    break;   
                case INT_TBL_DAT_CANT_DIF_PREC:
                    strMsg = "Diferencia($)";
                    break;                       
                    
                default:
                    strMsg = "";
                    break;

            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  


private boolean configurarTblDat() {
    boolean blnRes = true;
    try {
        vecCab = new Vector();
        vecCab.clear();
        vecCab.add(""); // 0 - Línea (oculta)
        vecCab.add("Compra"); // 1
        vecCab.add("Sub-Compra"); // 2
        vecCab.add("Orden"); // 3 (oculta)
        vecCab.add("Prom.(KG)"); // 4
        vecCab.add("Sugerido(KG)"); // 5
        vecCab.add("Prom($)"); // 6
        vecCab.add("Sugerido($)"); // 7 (oculta si no se usa)
        vecCab.add("CantCompraActual(KG)"); // 8
        vecCab.add("CantCompraActual($)"); // 9
        vecCab.add("Diferencia(KG)"); // 10
        vecCab.add("Diferencia($)"); // 11       
        
        
        vecAux = new Vector();
        vecAux.clear();
        vecAux.add(""); // 0 - Línea (oculta)
        vecAux.add("Compra"); // 1
        vecAux.add("Sub-Compra"); // 2
        vecAux.add("Orden"); // 3 (oculta)
        vecAux.add("Prom.(KG)"); // 4
        vecAux.add("Sugerido(KG)"); // 5
        vecAux.add("Prom($)"); // 6
        vecAux.add("Sugerido($)"); // 7 (oculta si no se usa)
        vecAux.add("CantCompraActual(KG)"); // 8
        vecAux.add("CantCompraActual($)"); // 9
        vecAux.add("Diferencia(KG)"); // 10
        vecAux.add("Diferencia($)"); // 11        

        

        objTblMod = new ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        // ========== Renderizador numérico ==========
        ZafTblCelRenLbl objTblCelRenLblGrl = new ZafTblCelRenLbl();
        objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
        objTblCelRenLblGrl.setFormatoNumerico("###,###,##0.00", false, true);

        TableColumnModel tcmAux = tblDat.getColumnModel();

        // ========== Asignar renderizador por columnas ==========
        tcmAux.getColumn(1).setCellRenderer(new JerarquiaRenderer()); // Compra
        tcmAux.getColumn(2).setCellRenderer(new JerarquiaRenderer()); // Sub-Compra

        tcmAux.getColumn(4).setCellRenderer(objTblCelRenLblGrl); // Prom.(KG)
        tcmAux.getColumn(5).setCellRenderer(objTblCelRenLblGrl); // Sugerido(KG)
        tcmAux.getColumn(6).setCellRenderer(objTblCelRenLblGrl); // Prom($)
        tcmAux.getColumn(7).setCellRenderer(objTblCelRenLblGrl); // Sugerido($)
        tcmAux.getColumn(8).setCellRenderer(objTblCelRenLblGrl); // CantCompraActual(KG)
        tcmAux.getColumn(9).setCellRenderer(objTblCelRenLblGrl); // CantCompraActual($)
        tcmAux.getColumn(10).setCellRenderer(objTblCelRenLblGrl); // Diferencia(KG)
        tcmAux.getColumn(11).setCellRenderer(objTblCelRenLblGrl); // Diferencia($)       

        // ========== Configurar ancho de columnas ==========
        tblDat.getColumnModel().getColumn(0).setPreferredWidth(0);    // Línea
        tblDat.getColumnModel().getColumn(1).setPreferredWidth(250);  // Compra
        tblDat.getColumnModel().getColumn(2).setPreferredWidth(250);  // Sub-Compra
        tblDat.getColumnModel().getColumn(3).setPreferredWidth(0);    // Orden
        tblDat.getColumnModel().getColumn(4).setPreferredWidth(100);  // Prom.(KG)
        tblDat.getColumnModel().getColumn(5).setPreferredWidth(100);  // Sugerido(KG)
        tblDat.getColumnModel().getColumn(6).setPreferredWidth(100);  // Prom($)
        tblDat.getColumnModel().getColumn(7).setPreferredWidth(100);  // Sugerido($)
        tblDat.getColumnModel().getColumn(8).setPreferredWidth(120);  // CantCompraActual(KG)
        tblDat.getColumnModel().getColumn(9).setPreferredWidth(120);  // CantCompraActual($)
        tblDat.getColumnModel().getColumn(10).setPreferredWidth(120);  // Diferencia(KG)
        tblDat.getColumnModel().getColumn(11).setPreferredWidth(120);  // Diferencia($)      

        // ========== Ocultar columnas no necesarias ==========
        objTblMod.addSystemHiddenColumn(0, tblDat); // Línea
        objTblMod.addSystemHiddenColumn(3, tblDat); // Orden

        // ========== Agrupar encabezados ==========
        ColumnGroup grpSugerencia = new ColumnGroup("Sugerencia de Pedido");
        grpSugerencia.add(tcmAux.getColumn(4)); // Prom.(KG)
        grpSugerencia.add(tcmAux.getColumn(5)); // Sugerido(KG)
        grpSugerencia.add(tcmAux.getColumn(6)); // Prom($)
        grpSugerencia.add(tcmAux.getColumn(7)); // Sugerido($)

        ColumnGroup grpCumplimiento = new ColumnGroup("Cumplimiento de Compras");
        grpCumplimiento.add(tcmAux.getColumn(8)); // CantCompraActual(KG)
        grpCumplimiento.add(tcmAux.getColumn(9)); // CantCompraActual($)
        
        ColumnGroup grpDiferencia = new ColumnGroup("Diferencias");
        grpDiferencia.add(tcmAux.getColumn(10)); // Diferencia(KG)
        grpDiferencia.add(tcmAux.getColumn(11)); // Diferencia($)        

        GroupableTableHeader header = new GroupableTableHeader(tcmAux);
        header.addColumnGroup(grpSugerencia);
        header.addColumnGroup(grpCumplimiento);
        header.addColumnGroup(grpDiferencia);

        tblDat.setTableHeader(header);

    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}



////private boolean configurarTblDat()
////    {
////        boolean blnRes=true;
////        try 
////            {
////                vecCab = new Vector();
////                vecCab.clear();
////                vecCab.add(""); // Columna adicional para jerarquía
////                vecCab.add("Compra");
////                vecCab.add("Sub-Compra");
////                vecCab.add("Orden");
////                vecCab.add("Prom.(KG)");
////                vecCab.add("Sugerido(KG)");
////                vecCab.add("Prom($)");
////                vecCab.add("Sugerido($)");
////                //vecCab.add("PromCompraActual(KG)");                
////                vecCab.add("CantCompraActual(KG)");                
////                //vecCab.add("PromActual($)");                
////                vecCab.add("CantCompraActual($)");                
////                
////
////                
////                vecAux = new Vector();
////                vecAux.clear();
////                vecAux.add(""); // Columna adicional para jerarquía
////                vecAux.add("Línea");
////                vecAux.add("Clase");
////                vecAux.add("Orden");
////                vecAux.add("Prom(KG)");
////                vecAux.add("Sugerido(KG)");
////                vecAux.add("Prom($)");
////                vecAux.add("Sugerido($)");
////                //vecAux.add("PromCompraActual(KG)");                
////                vecAux.add("CantCompraActual(KG)");                
////                //vecAux.add("PromActual($)");                
////                vecAux.add("CantCompraActual($)");                 
////                objTblMod = new ZafTblMod();
////                objTblMod.setHeader(vecCab);
////                tblDat.setModel(objTblMod);
////
////                // Configuración del renderizador
////                TableColumnModel tcmAux = tblDat.getColumnModel();
////
////                    
////    
////                tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_CLASE).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_ORDEN).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_PROM_PESO).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_PREC_SUG_PESO).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_PROM_DOLAR).setCellRenderer(new JerarquiaRenderer());
////                tcmAux.getColumn(INT_TBL_DAT_PREC_SUG_DOLAR).setCellRenderer(new JerarquiaRenderer());
////                //tcmAux.getColumn(INT_TBL_DAT_PROM_PESO_ACT).setCellRenderer(new JerarquiaRenderer());                
////                tcmAux.getColumn(INT_TBL_DAT_CANT_ACT).setCellRenderer(new JerarquiaRenderer());                
////                //tcmAux.getColumn(INT_TBL_DAT_PROM_DOLAR_ACT).setCellRenderer(new JerarquiaRenderer());                
////                tcmAux.getColumn(INT_TBL_DAT_CANT_DOLAR_ACT).setCellRenderer(new JerarquiaRenderer());                
////                
////                    final int INT_TBL_DAT_LIN=0;
////
////                // Configurar anchura de las columnas
////
////                
////                tblDat.getColumnModel().getColumn(0).setPreferredWidth(0);
////                tblDat.getColumnModel().getColumn(1).setPreferredWidth(250);
////                tblDat.getColumnModel().getColumn(2).setPreferredWidth(250);
////                tblDat.getColumnModel().getColumn(3).setPreferredWidth(0);
////                tblDat.getColumnModel().getColumn(4).setPreferredWidth(100);
////                tblDat.getColumnModel().getColumn(5).setPreferredWidth(100);
////                tblDat.getColumnModel().getColumn(6).setPreferredWidth(100);
////                tblDat.getColumnModel().getColumn(7).setPreferredWidth(0);
////                tblDat.getColumnModel().getColumn(8).setPreferredWidth(100);
////                tblDat.getColumnModel().getColumn(9).setPreferredWidth(100);
////                //tblDat.getColumnModel().getColumn(10).setPreferredWidth(100);                
////                //tblDat.getColumnModel().getColumn(11).setPreferredWidth(100);    
////                
////                
////                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
////                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN, tblDat);
////            } catch (Exception e) {
////                objUti.mostrarMsgErr_F1(this, e);
////            }
////            return blnRes;
////    }

////private class JerarquiaRenderer extends DefaultTableCellRenderer 
////    {
////    @Override
////    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
////        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
////        String clase = (String) table.getValueAt(row, INT_TBL_DAT_CLASE);
////        if (clase == null || clase.isEmpty()) {
////            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Línea principal en negrita
////            c.setBackground(Color.LIGHT_GRAY); // Fondo diferente para la línea
////        } else {
////            c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Clases normales
////            c.setBackground(Color.WHITE); // Fondo normal para clases
////        }
////        return c;
////    }
////}


                
private class JerarquiaRenderer extends DefaultTableCellRenderer   
    {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String clase = (String) table.getValueAt(row, INT_TBL_DAT_CLASE);
        String linea = (String) table.getValueAt(row, INT_TBL_DAT_LINEA);

                if (isSelected) {
            // Si la celda está seleccionada, pintar de azul claro
            c.setBackground(new Color(173, 216, 230)); // Azul claro
            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Mantener negrita si está seleccionado
        } else {
            // Si clase y línea son null o están vacías, aplicar estilo de fila normal
            if ((clase == null || clase.isEmpty()) && (linea == null || linea.isEmpty())) {
                c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Fuente normal
                c.setBackground(Color.WHITE); // Fondo normal
            } else if (clase == null || clase.isEmpty()) {
                // Línea principal
                c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para la línea principal
                c.setBackground(Color.LIGHT_GRAY); // Fondo para la línea principal
            } else {
                if (clase.equals("TOTAL")) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para "TOTAL"
                } else {
                    if (clase.equals("TOTAL GENERAL")) 
                    {
                        c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para "TOTAL"
                    }
                    else
                    {
                        c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Fuente normal para las clases
                        c.setBackground(Color.WHITE); // Fondo normal                        
                    }
                    

                }
            }
        }
        
          // Alinear texto a la derecha para las columnas específicas
            if (column >=3) {
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT); // Alineación por defecto
            }
            
           // Formatear números a dos decimales para las columnas relevantes
            if (column >= 3 && value instanceof Number) {
                double number = ((Number) value).doubleValue();
                value = String.format("%.2f", number); // Formatear a 2 decimales
            }
            
            // Ocultar las columnas 0, 3
            if ((column == 0) || (column == 3)) {
                c.setVisible(false); // Ocultar el componente de la celda
                return c; 
            }     
////            if ((column == 0) || (column == 3) || (column == 8) || (column == 10)) {
////                c.setVisible(false); // Ocultar el componente de la celda
////                return c; 
////            }     
           // Aplicar formato de moneda a la última columna
        if (column == table.getColumnCount() - 1 && value instanceof Number) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "EC")); // Formato en dólares ecuatorianos
            value = currencyFormat.format(value);
        }
        
        // Formatear números a dos decimales para columnas >= 3 y filas >= 1
        if (row >= 1 && column >= 3 && value instanceof Number) {
            double number = ((Number) value).doubleValue();
            DecimalFormat df = new DecimalFormat("#.##0,00"); // evita notación científica
            setText(df.format(number));
        }       
            
        return c;
    }
}

private boolean cargarPresupuestoVentasGlobalesInicial(HashMap<String, Imp18_ItemsAtri> dataMapConsultaGloba)
    {
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1;
        String strEstPedEli="";
        java.sql.CallableStatement cal; 
        try{
            pgrSis.setIndeterminate(true);
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null){
                lblMsgSis.setText("Obteniendo datos..."); 
                stmGP=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQLGP="";
                strSQLGP="{call sp_ReportePedSugeridosDetallado(?,?,?)}";
                cal = conGP.prepareCall(strSQLGP); 
                cal.setInt(1,  4);// 2 si es con atributo o  1 si no tiene atributo
                cal.setInt(2,  1);// 2 si es con atributo o  1 si no tiene atributo                
                cal.setInt(3,  1);// 1 si es con agrupacion o  0 si no tiene agrupacion                         
////                cal.registerOutParameter(2, java.sql.Types.NVARCHAR);
                boolean hasResults = cal.execute();
                String llave ="";
                lblMsgSis.setText("Cargando datos...");                
                int contador =0;
                if (hasResults) 
                {
                    rstGP = cal.getResultSet();
                    vecCab.clear();
                    vecAux.clear(); 
                    
                    while (rstGP.next()) 
                    {
                        llave = rstGP.getString("Linea").toString() +"-"+ rstGP.getString("clase").toString();
                        if (!llave.equals("-"))
                        {
                            dataMapConsultaGloba.put(llave, new Imp18_ItemsAtri (rstGP.getString("linea"),rstGP.getString("clase"),rstGP.getDouble("nd_proMenVenPeso"),rstGP.getDouble("nd_sugerenciaPeso"),rstGP.getDouble("nd_proMenVenDolar"),rstGP.getDouble("nd_sugerenciaPrecio"),rstGP.getInt("ne_ord")));                      
                            contador++;
                        }
                            
                    } 
                    
                }
                lblMsgSis.setText("Se encontraron " + contador + " registros.");
                               
                rstGP.close();
                stmGP.close();
                conGP.close();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }






private boolean cargarPresupuestoVentasGlobalesIntermedio(HashMap<String, Imp18_ItemsAtri_Cump> dataMapConsultaGlobaCumpli,int mes, int anio)
    {
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1;
        String strEstPedEli="";
        
        java.sql.CallableStatement cal; 
        try{
            pgrSis.setIndeterminate(true);
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");      
                // Obtener la fecha actual
                LocalDate fechaActual = LocalDate.now();                
                // Obtener el primer día del mes
                LocalDate primerDiaMes = fechaActual.withDayOfMonth(1);   
                // Convertir ambas fechas a String con el formato requerido
                String strFechaActual = fechaActual.format(formatter);
                String strPrimerDiaMes = primerDiaMes.format(formatter);                
                lblMsgSis.setText("Obteniendo datos..."); 
                stmGP=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQLGP="";
////////////                strSQLGP="{call sp_ReportePedSugeridosGlobal(?,?,?)}";
////////////                cal = conGP.prepareCall(strSQLGP); 
////////////                cal.setInt(1,  1);// 2 si es con atributo o  1 si no tiene atributo
////////////                cal.setInt(2,  mes);//para el mes
////////////                cal.setInt(3,  anio);// para el anio
////                cal.registerOutParameter(2, java.sql.Types.NVARCHAR);
                strSQLGP="{call sp_historialItemsComprados(?,?,?,?)}";
                cal = conGP.prepareCall(strSQLGP); 
                cal.setString(1,  strPrimerDiaMes);
                cal.setString(2,  strFechaActual);
                cal.setInt(3,  mes);
                cal.setInt(4,  anio);
                boolean hasResults = cal.execute();
                String llave ="";
                lblMsgSis.setText("Cargando datos...");                
                int contador =0;
                if (hasResults) 
                {
                    rstGP = cal.getResultSet();
                    vecCab.clear();
                    vecAux.clear(); 
                    
                    while (rstGP.next()) 
                    {
                        llave = rstGP.getString("Linea").toString() +"-"+ rstGP.getString("clase").toString();
                        if (!llave.equals("-"))
                        {
                            
                            dataMapConsultaGlobaCumpli.put(llave, new Imp18_ItemsAtri_Cump (rstGP.getString("linea"),rstGP.getString("clase"),rstGP.getDouble("nd_proMenVenPeso"),rstGP.getDouble("nd_sugerenciaPeso"),rstGP.getDouble("nd_proMenVenDolar"),rstGP.getDouble("nd_sugerenciaPrecio"),rstGP.getInt("ne_ord"),rstGP.getDouble("nd_proMenVenPesoActual"),rstGP.getDouble("nd_cantidadCompraActual"),rstGP.getDouble("nd_proMenVenDolarActual"),rstGP.getDouble("nd_cantidadCompraPrecioActual"),rstGP.getDouble("nd_diferenciaKG"),rstGP.getDouble("nd_diferenciaPrecio")));                      
                            contador++;
                        }
                            
                    } 
                    
                }
                lblMsgSis.setText("Se encontraron " + contador + " registros.");
                               
                rstGP.close();
                stmGP.close();
                conGP.close();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

////private boolean cargarPresupuestoVentasGlobales(HashMap<String, Imp18_ItemsAtri> dataMapConsultaGloba)
////    {
////        boolean blnRes=true;
////        int intCodRegArl=-1;
////        int intCodRegDb=-1;
////        String strEstPedEli="";
////        try{
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
////            if (conGP!=null){
////                stmGP=conGP.createStatement();
////                //Armar la sentencia SQL.
////                strSQLGP="";
////////                strSQLGP+="SELECT a1.Linea,a1.clase,a1.nd_proMenVenNuevo,a1.nd_proMenVenPeso,cfinv.ne_ord, \n";
////////                strSQLGP+="       a1.nd_stkActPeso as disponiblePeso,a1.nd_stkImpPeso as importacionesPeso,a1.nd_transitoPeso as transitoPeso,a1.nd_stockTotalPeso as invtotalPeso,  \n";
////////                strSQLGP+="       a1.nd_stkActDolar as disponibleDolar,a1.nd_stkImpDolar as importacionesDolar,a1.nd_transitoDolar as transitoDolar,a1.nd_stockTotalDolar as invtotalDolar   \n";                
////////                strSQLGP+="FROM ( \n ";
////////                strSQLGP+="         SELECT a1.Linea, a1.clase, /* a3.ne_ord,*/ ISNULL(sum(a1.nd_proMenVenNuevo),0) as nd_proMenVenNuevo  \n";
////////                strSQLGP+="                ,round (ISNULL(sum(a1.nd_proMenVenPeso),0),2) as nd_proMenVenPeso \n";
////////                strSQLGP+="                ,ISNULL(sum(a1.nd_DisponiblePeso),0) as nd_stkActPeso,ISNULL(sum(a1.nd_ImportacionesPeso),0) as nd_stkImpPeso  \n";
////////                strSQLGP+="                ,ISNULL(sum(a1.nd_TransitoPeso),0) as nd_transitoPeso,ISNULL(sum(a1.nd_InvTotalPeso),0) as nd_stockTotalPeso     \n";
////////                strSQLGP+="                ,ISNULL(sum(a1.nd_DisponibleDolar),0) as nd_stkActDolar,ISNULL(sum(a1.nd_ImportacionesDolar),0) as nd_stkImpDolar  \n";
////////                strSQLGP+="                ,ISNULL(sum(a1.nd_TransitoDolar),0) as nd_transitoDolar,ISNULL(sum(a1.nd_InvTotalDolar),0) as nd_stockTotalDolar     \n";                
////////                strSQLGP+="         FROM ( \n ";
////////                strSQLGP+="                 SELECT trim(a1.ITEMNMBR) as ITEMNMBR, a1.ITMSHNAM, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL,   \n";
////////                strSQLGP+="                        extItem.Extender_Record_ID, TRIM(extLinea.STRGA255) as Linea, TRIM(extClase.STRGA255) as clase,  \n";
////////                strSQLGP+="                        a2.nd_proAnuVenMan AS nd_proAnuVenManSolito,ROUND((a3.UOMPRICE * 0.75) * (ISNULL(a2.nd_proAnuVenMan,0)),2) as nd_proMenVenNuevo \n";
////////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(a2.nd_proAnuVenMan, 0) )  AS nd_proMenVenPeso    \n";
////////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stkAct, 0) )  AS nd_DisponiblePeso   \n";
////////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stkImp, 0) )  AS nd_ImportacionesPeso    \n";
////////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_transito, 0) )  AS nd_TransitoPeso    \n";
////////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stockTotal, 0) )  AS nd_InvTotalPeso ,   \n";
////////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stkAct,0)),2) as nd_DisponibleDolar,   \n";
////////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stkImp,0)),2) as nd_ImportacionesDolar,   \n";
////////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_transito,0)),2) as nd_TransitoDolar,   \n";
////////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stockTotal,0)),2) as nd_InvTotalDolar   \n";
////////                strSQLGP+="                 FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 (nolock) AS a1 \n";
////////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01100 as extItem ON (a1.ITEMNMBR=extItem.Extender_Key_Values_1 AND Extender_Window_ID = 'MSW_ADI_IV') \n";                 
////////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extLinea ON (extItem.Extender_Record_ID=extLinea.Extender_Record_ID AND extLinea.Field_ID =196) \n";                 
////////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extClase ON (extItem.Extender_Record_ID=extClase.Extender_Record_ID AND extClase.Field_ID =197) \n";  
////////                strSQLGP+="                        INNER JOIN Dynamics2021.dbo.tbm_inv (nolock) as a2 ON (a1.ITEMNMBR=a2.tx_itm) \n";
////////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00108 (nolock) as a3 ON (a1.ITEMNMBR=a3.ITEMNMBR AND a3.PRCLEVEL='GENERAL') \n";     
////////                strSQLGP+="                        LEFT JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01103 AS extPeso ON (extItem.Extender_Record_ID = extPeso.Extender_Record_ID AND extPeso.Field_ID = 179) \n";                 
////////                strSQLGP+="                        LEFT JOIN Dynamics2021..view_DIS_IMP_TRANS_EXI_TOTAL as disp   on (a1.ITEMNMBR = disp.co_itmMae ) \n";                      
////////                strSQLGP+="                 WHERE  a1.ITEMTYPE = 1 AND (a1.ITEMNMBR like '%I' OR a1.ITEMNMBR like '%S' )  and a1.inactive =0 \n"; 
////////                strSQLGP+="              ) as a1  \n"; 
////////                strSQLGP+="          GROUP BY a1.Linea, a1.clase  \n"; 
////////                strSQLGP+="              ) as a1  \n"; 
////////                strSQLGP+="              INNER JOIN  Dynamics2021..tbm_cfgAtrInv as cfinv on (a1.linea = cfinv.tx_cfgPadDat and cfinv.tx_cfgHijDat is null and cfinv.co_cfg =1) \n"; 
////////                strSQLGP+=" ORDER BY cfinv.ne_ord ASC";
////
////                strSQLGP+="SELECT a1.Linea,a1.clase,CONVERT(DECIMAL(10, 2), a1.nd_proAnuVenManSolito) as nd_proMenVenMen, CONVERT(DECIMAL(10, 2), a1.nd_sugerenciaSolito) as nd_preSuge, \n";
////                strSQLGP+="       CONVERT(DECIMAL(10, 2), a1.nd_sugerenciaPeso) as nd_preSugePeso, CONVERT(DECIMAL(10, 2), a1.nd_sugerenciaPrecio) as nd_preSugePrecio,a1.ne_ord \n";
////                strSQLGP+="FROM (  \n";
////                strSQLGP+="         SELECT a1.Linea, a1.clase, /* a3.ne_ord,*/  \n";
////                strSQLGP+="                 ISNULL(sum(a1.nd_proAnuVenManSolito),0) as nd_proAnuVenManSolito,  \n";
////                strSQLGP+="                 ISNULL(sum(a1.nd_sugerenciaSolito),0) as nd_sugerenciaSolito,  \n";
////                strSQLGP+="                 ISNULL(sum(a1.nd_sugerenciaPeso),0) as nd_sugerenciaPeso  ,  \n";
////                strSQLGP+="                 ISNULL(sum(a1.nd_sugerenciaPrecio),0) as nd_sugerenciaPrecio,  \n";
////                strSQLGP+="                 a1.ne_ord \n";
////                
////                strSQLGP+="FROM ( \n ";
////                strSQLGP+="         SELECT linea,clase,nd_proAnuVenManSolito,nd_sugerenciaSolito,nd_sugerenciaPeso,nd_sugerenciaPrecio, ne_ord   \n";
////                strSQLGP+="         FROM Dynamics2021..view_det_item_linea_import2   \n";
////                strSQLGP+="     ) AS a1 \n";
////                strSQLGP+="GROUP BY a1.Linea, a1.clase ,a1.ne_ord\n";
////                strSQLGP+=") as a1 \n";
////                strSQLGP+="WHERE a1.linea !='NO_TIENE' and  (a1.linea !='') order by ne_ord asc \n";
////                System.out.println("SQL cargarPresupuestoVentasGlobales: " + strSQLGP);
////                rstGP=stmGP.executeQuery(strSQLGP);
////                //Limpiar vector de datos.
////////                vecCab.clear();
////////                vecAux.clear();v
////                
////                String llave ="";
////                //Obtener los registros.
////                while (rstGP.next()){
////                    
////                    llave = rstGP.getString("Linea").toString() +"-"+ rstGP.getString("clase").toString();
////                    if (!llave.equals("-"))
////                            dataMapConsultaGloba.put(llave, new Imp18_ItemsAtri (rstGP.getString("Linea"),rstGP.getString("clase"),rstGP.getDouble("nd_proMenVenMen"),rstGP.getDouble("nd_preSuge"),rstGP.getDouble("nd_preSugePeso"),rstGP.getDouble("nd_preSugePrecio"),rstGP.getInt("ne_ord")));                      
////                }
////                rstGP.close();
////                stmGP.close();
////                conGP.close();
////            }
////        }
////        catch (java.sql.SQLException e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }

////private boolean UnificacionListaOrden(HashMap<String, Imp18_ItemsAtri> dataMapConsultaGloba) {
////    try {
////        int ne_orden =1;
////        int contador =0;
////        // Recorrer el HashMap
////        for (Map.Entry<String, Imp18_ItemsAtri> entrada : dataMapConsultaGloba.entrySet()) {
////            contador++;
////            ne_orden=0;
////            String linea = entrada.getKey(); // Obtener la clave (línea)
////            Imp18_ItemsAtri objeto = entrada.getValue(); // Obtener el objeto asociado (clase)
////
////            // Aquí accedes a los atributos de la clase Imp18_ItemsAtri
////            // Por ejemplo, si la clase tiene un atributo "atributo1" y "atributo2":
////            String str_linea = objeto.getSt_linea();
////            String str_clase = objeto.getSt_clase();
////            ne_orden = obtenerOrden(str_linea,str_clase);
////            objeto.setNd_orden(ne_orden);
////        }
////
////        // Si todo se ejecuta correctamente, devuelve true
////        return true;
////    } catch (Exception e) {
////        // Manejo de excepciones
////        e.printStackTrace();
////        return false;
////    }
////}

private int obtenerOrden(String linea, String clase)
    {
        java.sql.Statement stmLocOr;
        java.sql.Connection conGPOr;
        java.sql.ResultSet rstLocOr;  
        String strSQLGPOrd="";
        int ne_orden=-1;
        try
        {
            conGPOr = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conGPOr!=null)
            {
                stmLocOr=conGPOr.createStatement();
                //Armar la sentencia SQL.
                strSQLGP="";
                strSQLGP+="SELECT ne_ord ";
                strSQLGP+=" FROM  Dynamics2021..tbm_cfgAtrInv ";
                strSQLGP+=" WHERE co_cfg=1 and tx_cfgPadDat='"+ linea +"'";
                rstLocOr=stmLocOr.executeQuery(strSQLGP);
                while (rstLocOr.next())
                {
                    ne_orden=rstLocOr.getInt("ne_ord");
                    break;
                }
                rstLocOr.close();
                stmLocOr.close();
                conGPOr.close();
                rstLocOr=null;
                stmLocOr=null;
                conGPOr=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            ne_orden=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            ne_orden=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return ne_orden;
    }   




// Método auxiliar para redondear valores a un número específico de decimales
private double redondear(double valor, int decimales) {
    if (decimales < 0) throw new IllegalArgumentException("El número de decimales debe ser mayor o igual a 0");
    BigDecimal bd = BigDecimal.valueOf(valor);
    bd = bd.setScale(decimales, RoundingMode.HALF_UP);
    return bd.doubleValue();
}

private boolean CargarReg_18(HashMap<String, Imp18_ItemsAtri> dataMapConsultaGloba)
    {
    boolean blnRes=false;
    vecCab.clear();
    vecAux.clear();  
    lblMsgSis.setText("Ordenando los datos..."); 
    for (String llave : dataMapConsultaGloba.keySet()) 
    {
        Imp18_ItemsAtri item = dataMapConsultaGloba.get(llave);
    
        Vector<Object> vecReg1 = new Vector<>();

        vecReg1.add(INT_TBL_DAT_LIN,"");
        vecReg1.add(INT_TBL_DAT_LINEA,item.getSt_linea());
        vecReg1.add(INT_TBL_DAT_CLASE,item.getSt_clase());
        vecReg1.add(INT_TBL_DAT_ORDEN,item.getNe_orden());
        
        vecReg1.add(INT_TBL_DAT_PROM_PESO,item.getNd_nd_proVenMenPeso());
        vecReg1.add(INT_TBL_DAT_PREC_SUG_PESO,item.getNd_preSugePeso());
        vecReg1.add(INT_TBL_DAT_PROM_DOLAR,item.getNd_proVenMenDolar());
        vecReg1.add(INT_TBL_DAT_PREC_SUG_DOLAR,item.getNd_preSugeDolar());
        
        //vecReg1.add(INT_TBL_DAT_PROM_PESO_ACT,0);
        vecReg1.add(INT_TBL_DAT_CANT_ACT,0);
        //vecReg1.add(INT_TBL_DAT_PROM_DOLAR_ACT,0);
        vecReg1.add(INT_TBL_DAT_CANT_DOLAR_ACT,0);  
        
        vecReg1.add(INT_TBL_DAT_CANT_DIF_PESO,0);
        vecReg1.add(INT_TBL_DAT_CANT_DIF_PREC,0);

        
        
        vecCab1.add(vecReg1);
        
        
        // Ordenar el vector por INT_TBL_DAT_ORDEN (índice 9)
       
    }
 Collections.sort(vecCab1, new Comparator<Vector<Object>>() {
            @Override
            public int compare(Vector<Object> o1, Vector<Object> o2) {
                Integer orden1 = (Integer) o1.get(3); // INT_TBL_DAT_ORDEN
                Integer orden2 = (Integer) o2.get(3); // INT_TBL_DAT_ORDEN
                return orden1.compareTo(orden2); 
            }
        });

                for (Vector<Object> vec : vecCab1) {
                    System.out.println(vec); 
        }
                
                
    
    vecAux = procesarDatosJerarquicamente(vecCab1);
    objTblMod.setData(vecAux);
    tblDat.setModel(objTblMod); 
    pgrSis.setIndeterminate(false);
    
    return blnRes;
}

private boolean CargarReg_18_Cump(HashMap<String, Imp18_ItemsAtri_Cump> dataMapConsultaGloba_Cump)
    {
    boolean blnRes=false;
    vecCab.clear();
    vecAux.clear();  
    lblMsgSis.setText("Ordenando los datos..."); 
    for (String llave : dataMapConsultaGloba_Cump.keySet()) 
    {
        Imp18_ItemsAtri_Cump item = dataMapConsultaGloba_Cump.get(llave);
    
        Vector<Object> vecReg1 = new Vector<>(Collections.nCopies(12, null));

        vecReg1.add(INT_TBL_DAT_LIN,"");
        vecReg1.add(INT_TBL_DAT_LINEA,item.getSt_linea());
        vecReg1.add(INT_TBL_DAT_CLASE,item.getSt_clase());
        vecReg1.add(INT_TBL_DAT_ORDEN,item.getNe_orden());
        
        vecReg1.add(INT_TBL_DAT_PROM_PESO,item.getNd_nd_proVenMenPeso());
        vecReg1.add(INT_TBL_DAT_PREC_SUG_PESO,item.getNd_preSugePeso());
        vecReg1.add(INT_TBL_DAT_PROM_DOLAR,item.getNd_proVenMenDolar());
        vecReg1.add(INT_TBL_DAT_PREC_SUG_DOLAR,item.getNd_preSugeDolar());
        
        //vecReg1.add(INT_TBL_DAT_PROM_PESO_ACT,item.getNd_proMenVenPesoActual());
        vecReg1.add(INT_TBL_DAT_CANT_ACT,item.getNd_cantidadCompraActual());
        //vecReg1.add(INT_TBL_DAT_PROM_DOLAR_ACT,item.getNd_proMenVenDolarActual());
        vecReg1.add(INT_TBL_DAT_CANT_DOLAR_ACT,item.getNd_cantidadCompraPrecioActual());   
        
        vecReg1.add(INT_TBL_DAT_CANT_DIF_PESO,item.getNd_diferenciaKG());
        vecReg1.add(INT_TBL_DAT_CANT_DIF_PREC,item.getNd_diferenciaPRE());        
        
        
        vecCab1.add(vecReg1);
        
        
        // Ordenar el vector por INT_TBL_DAT_ORDEN (índice 9)
       
    }
 Collections.sort(vecCab1, new Comparator<Vector<Object>>() {
            @Override
            public int compare(Vector<Object> o1, Vector<Object> o2) {
                Integer orden1 = (Integer) o1.get(3); // INT_TBL_DAT_ORDEN
                Integer orden2 = (Integer) o2.get(3); // INT_TBL_DAT_ORDEN
                return orden1.compareTo(orden2); 
            }
        });

                for (Vector<Object> vec : vecCab1) {
                    System.out.println(vec); 
        }
                
                
    
    vecAux = procesarDatosJerarquicamente(vecCab1);
    objTblMod.setData(vecAux);
    tblDat.setModel(objTblMod); 
    pgrSis.setIndeterminate(false);
    
    return blnRes;
}

private Vector procesarDatosJerarquicamente(Vector vecDatos) {
    Vector vecDatosProcesados = new Vector();
    DecimalFormat df = new DecimalFormat("#,##0.00"); // ✅ formato con coma de miles y 2 decimales
    String lineaActual = "";

    double totalGeneralOrden = 0;
    double totalGeneralProMenPeso = 0;
    double totalGeneralSugeridoPeso = 0;
    double totalGeneralProMenDolar = 0;
    double totalGeneralSugeridoDolar = 0;
    
    double totalGeneralProMenPesoActual = 0;
    double totalGeneralCantCompraActual = 0;
    double totalGeneralProMenDolarActual = 0;
    double totalGeneralCantCompraDolarActual = 0;    
    
    double totalGeneralDifKG = 0;   
    double totalGeneralDifPRE = 0;   
    
    for (int i = 0; i < vecDatos.size(); i++) {
        Vector registro = (Vector) vecDatos.get(i);
        totalGeneralOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString());
        totalGeneralProMenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString());
        totalGeneralSugeridoPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString());
        totalGeneralProMenDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString());
        totalGeneralSugeridoDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString());
        
        //totalGeneralProMenPesoActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO_ACT).toString());
        totalGeneralCantCompraActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_ACT).toString());
        //totalGeneralProMenDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT).toString());
        totalGeneralCantCompraDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT).toString());
        
        totalGeneralDifKG += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PESO).toString());
        totalGeneralDifPRE += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PREC).toString());  
       
    }
    
    Vector filaTotalGeneral = new Vector();
    filaTotalGeneral.add(""); 
    filaTotalGeneral.add(""); 
    filaTotalGeneral.add("TOTAL GENERAL"); 
    filaTotalGeneral.add(df.format(totalGeneralOrden));
    filaTotalGeneral.add(df.format(totalGeneralProMenPeso));
    filaTotalGeneral.add(df.format(totalGeneralSugeridoPeso));
    filaTotalGeneral.add(df.format(totalGeneralProMenDolar));
    filaTotalGeneral.add(df.format(totalGeneralSugeridoDolar));
    //filaTotalGeneral.add(df.format(totalGeneralProMenPesoActual));
    filaTotalGeneral.add(df.format(totalGeneralCantCompraActual));
    //filaTotalGeneral.add(df.format(totalGeneralProMenDolarActual));
    filaTotalGeneral.add(df.format(totalGeneralCantCompraDolarActual));
    
    filaTotalGeneral.add(df.format(totalGeneralDifKG));
    filaTotalGeneral.add(df.format(totalGeneralDifPRE));    
    vecDatosProcesados.add(filaTotalGeneral);
    
    double totalProMenPeso = 0;
    double totalSugeridoPeso = 0;
    double totalOrden = 0;
    double totalProMenDolar = 0;
    double totalSugeridoDolar = 0;

    double totalProMenActualDolar = 0;
    double totalCantComprActual = 0;
    double totalProMenDolarActual = 0;
    double totalCantComprDolarActual = 0;    
    
    double totalDifPRE = 0;  
    double totalDifKG = 0;      
    
    for (int i = 0; i < vecDatos.size(); i++) {
        Vector registro = (Vector) vecDatos.get(i);
        String linea = (String) registro.get(INT_TBL_DAT_LINEA);
        
        if (!linea.equals(lineaActual)) {
            if (!lineaActual.isEmpty()) {
                Vector filaTotal = new Vector();
                filaTotal.add(""); 
                filaTotal.add(""); 
                filaTotal.add("TOTAL"); 
                filaTotal.add(df.format(totalOrden));
                filaTotal.add(df.format(totalProMenPeso));
                filaTotal.add(df.format(totalSugeridoPeso));
                filaTotal.add(df.format(totalProMenDolar));
                filaTotal.add(df.format(totalSugeridoDolar));
                //filaTotal.add(df.format(totalProMenActualDolar));
                filaTotal.add(df.format(totalCantComprActual));
                //filaTotal.add(df.format(totalProMenDolarActual));
                filaTotal.add(df.format(totalCantComprDolarActual));
                
                filaTotal.add(df.format(totalDifKG));
                filaTotal.add(df.format(totalDifPRE));                
                vecDatosProcesados.add(filaTotal);
            }

            totalProMenPeso = 0;
            totalSugeridoPeso = 0;
            totalOrden = 0;
            totalProMenDolar = 0;
            totalSugeridoDolar = 0;
            totalProMenActualDolar = 0;
            totalCantComprActual = 0;
            totalProMenDolarActual = 0;
            totalCantComprDolarActual = 0;            
            totalDifKG = 0;   
            totalDifPRE = 0;   

            Vector filaLinea = new Vector();
            filaLinea.add(""); 
            filaLinea.add(linea); 
            filaLinea.add(""); 
            filaLinea.add(""); 
            filaLinea.add(""); 
            filaLinea.add(""); 
            filaLinea.add(""); 
            filaLinea.add("");
            filaLinea.add("");
            filaLinea.add(""); 
////            filaLinea.add("");
////            filaLinea.add("");            
            filaLinea.add("");
            filaLinea.add(""); 
            vecDatosProcesados.add(filaLinea);

            lineaActual = linea;
        }
        
        totalProMenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString());
        totalSugeridoPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString());
        totalProMenDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString());
        totalOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString());        
        totalSugeridoDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString());
        
        //totalProMenActualDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO_ACT).toString());
        totalCantComprActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_ACT).toString());
        //totalProMenDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT).toString());
        totalCantComprDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT).toString());        
        
        totalDifKG += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PESO).toString());   
        totalDifPRE += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PREC).toString()); 
 
        
        Vector filaClase = new Vector();
        filaClase.add(""); 
        filaClase.add(""); 
        filaClase.add(registro.get(INT_TBL_DAT_CLASE));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString())));
        //filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO_ACT).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_CANT_ACT).toString())));
        //filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT).toString())));
        
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PESO).toString())));
        filaClase.add(df.format(Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DIF_PREC).toString())));        
        
        vecDatosProcesados.add(filaClase);
    }
    
    if (!lineaActual.isEmpty()) {
        Vector filaTotal = new Vector();
        filaTotal.add(""); 
        filaTotal.add(""); 
        filaTotal.add("TOTAL"); 
        filaTotal.add(df.format(totalOrden));
        filaTotal.add(df.format(totalProMenPeso));
        filaTotal.add(df.format(totalSugeridoPeso));
        filaTotal.add(df.format(totalProMenDolar));
        filaTotal.add(df.format(totalSugeridoDolar));
        //filaTotal.add(df.format(totalProMenActualDolar));
        filaTotal.add(df.format(totalCantComprActual));
        //filaTotal.add(df.format(totalProMenDolarActual));
        filaTotal.add(df.format(totalCantComprDolarActual));        
        
        filaTotal.add(df.format(totalDifKG));    
        filaTotal.add(df.format(totalDifPRE));            
        vecDatosProcesados.add(filaTotal);
    }
    
    return vecDatosProcesados;
}



////private Vector procesarDatosJerarquicamente(Vector vecDatos) {
////    Vector vecDatosProcesados = new Vector();
////    String lineaActual = "";
////    double totalGeneralOrden = 0;
////    double totalGeneralProMenPeso = 0;
////    double totalGeneralSugeridoPeso = 0;
////    double totalGeneralProMenDolar = 0;
////    double totalGeneralSugeridoDolar = 0;
////    
////    double totalGeneralProMenPesoActual = 0;
////    double totalGeneralCantCompraActual = 0;
////    double totalGeneralProMenDolarActual = 0;
////    double totalGeneralCantCompraDolarActual = 0;    
////    
////    for (int i = 0; i < vecDatos.size(); i++) {
////        Vector registro = (Vector) vecDatos.get(i);
////        totalGeneralOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString());
////        totalGeneralProMenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString());
////        totalGeneralSugeridoPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString());
////        totalGeneralProMenDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString());
////        totalGeneralSugeridoDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString());
////        
////        totalGeneralProMenPesoActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO_ACT).toString());
////        totalGeneralCantCompraActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_ACT).toString());
////        totalGeneralProMenDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT).toString());
////        totalGeneralCantCompraDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT).toString());
////
////    
////    }
////    
////    Vector filaTotalGeneral = new Vector();
////    filaTotalGeneral.add(""); 
////    filaTotalGeneral.add(""); 
////    filaTotalGeneral.add("TOTAL GENERAL"); 
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralOrden));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralProMenPeso));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralSugeridoPeso));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralProMenDolar));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralSugeridoDolar));
////
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralProMenPesoActual));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralCantCompraActual));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralProMenDolarActual));
////    filaTotalGeneral.add(String.format("%.2f", totalGeneralCantCompraDolarActual));    
////    vecDatosProcesados.add(filaTotalGeneral);
////    
////    double totalProMenPeso = 0;
////    double totalSugeridoPeso = 0;
////    double totalOrden = 0;
////    double totalProMenDolar = 0;
////    double totalSugeridoDolar = 0;
////
////    double totalProMenActualDolar = 0;
////    double totalCantComprActual = 0;
////    double totalProMenDolarActual = 0;
////    double totalCantComprDolarActual = 0;    
////    
////    for (int i = 0; i < vecDatos.size(); i++) {
////        Vector registro = (Vector) vecDatos.get(i);
////        String linea = (String) registro.get(INT_TBL_DAT_LINEA);
////        
////        if (!linea.equals(lineaActual)) {
////            if (!lineaActual.isEmpty()) {
////                Vector filaTotal = new Vector();
////                filaTotal.add(""); 
////                filaTotal.add(""); 
////                filaTotal.add("TOTAL"); 
////                filaTotal.add(String.format("%.2f", totalOrden));
////                filaTotal.add(String.format("%.2f", totalProMenPeso));
////                filaTotal.add(String.format("%.2f", totalSugeridoPeso));
////                filaTotal.add(String.format("%.2f", totalProMenDolar));
////                filaTotal.add(String.format("%.2f", totalSugeridoDolar));
////                
////                filaTotal.add(String.format("%.2f", totalProMenActualDolar));
////                filaTotal.add(String.format("%.2f", totalCantComprActual));
////                filaTotal.add(String.format("%.2f", totalProMenDolarActual));
////                filaTotal.add(String.format("%.2f", totalCantComprDolarActual));                
////                vecDatosProcesados.add(filaTotal);
////            }
////            totalProMenPeso = 0;
////            totalSugeridoPeso = 0;
////            totalOrden = 0;
////            totalProMenDolar = 0;
////            totalSugeridoDolar = 0;
////
////            totalProMenActualDolar = 0;
////            totalCantComprActual = 0;
////            totalProMenDolarActual = 0;
////            totalCantComprDolarActual = 0;            
////        
////            
////            Vector filaLinea = new Vector();
////            filaLinea.add(""); 
////            filaLinea.add(linea); 
////            filaLinea.add(""); 
////            filaLinea.add(""); 
////            filaLinea.add(""); 
////            filaLinea.add(""); 
////            filaLinea.add(""); 
////            filaLinea.add("");
////            filaLinea.add("");
////            filaLinea.add(""); 
////            filaLinea.add("");
////            filaLinea.add("");            
////            
////            vecDatosProcesados.add(filaLinea);
////            lineaActual = linea;
////        }
////        
////        totalProMenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString());
////        totalSugeridoPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString());
////        totalProMenDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString());
////        totalOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString());        
////        totalSugeridoDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString());
////        
////        totalProMenActualDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO_ACT).toString());
////        totalCantComprActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_ACT).toString());
////        totalProMenDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT).toString());
////        totalCantComprDolarActual += Double.parseDouble(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT).toString());        
////        
////        Vector filaClase = new Vector();
////        filaClase.add(""); 
////        filaClase.add(""); 
////        filaClase.add(registro.get(INT_TBL_DAT_CLASE));
////        filaClase.add(registro.get(INT_TBL_DAT_ORDEN));
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_PESO));
////        filaClase.add(registro.get(INT_TBL_DAT_PREC_SUG_PESO));
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_DOLAR));
////        filaClase.add(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR));
////        
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_PESO_ACT));
////        filaClase.add(registro.get(INT_TBL_DAT_CANT_ACT));
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_DOLAR_ACT));
////        filaClase.add(registro.get(INT_TBL_DAT_CANT_DOLAR_ACT));        
////        vecDatosProcesados.add(filaClase);
////    }
////    
////    if (!lineaActual.isEmpty()) {
////        Vector filaTotal = new Vector();
////        filaTotal.add(""); 
////        filaTotal.add(""); 
////        filaTotal.add("TOTAL"); 
////        filaTotal.add(String.format("%.2f", totalOrden));
////        filaTotal.add(String.format("%.2f", totalProMenPeso));
////        filaTotal.add(String.format("%.2f", totalSugeridoPeso));
////        filaTotal.add(String.format("%.2f", totalProMenDolar));
////        filaTotal.add(String.format("%.2f", totalSugeridoDolar));
////        
////        filaTotal.add(String.format("%.2f", totalProMenActualDolar));
////        filaTotal.add(String.format("%.2f", totalCantComprActual));
////        filaTotal.add(String.format("%.2f", totalProMenDolarActual));
////        filaTotal.add(String.format("%.2f", totalCantComprDolarActual));        
////        vecDatosProcesados.add(filaTotal);
////    }
////    
////    return vecDatosProcesados;
////}

   
   

////private Vector procesarDatosJerarquicamente(Vector vecDatos) {
////    Vector vecDatosProcesados = new Vector();
////    String lineaActual = "";
////    double totalProMenPeso = 0; // Total para nd_proMenVenNuevo
////    double totalSugeridoPeso = 0;  // Total para Stock Total
////    
////    double totalOrden = 0;  // Total para orden    
////    
////    double totalProMenDolar = 0;
////    double totalSugeridoDolar = 0;
////
////    for (int i = 0; i < vecDatos.size(); i++) {
////        Vector registro = (Vector) vecDatos.get(i);
////        String linea = (String) registro.get(INT_TBL_DAT_LINEA);
////
////        // Si se detecta un cambio de línea
////        if (!linea.equals(lineaActual)) {
////            if (!lineaActual.isEmpty()) {
////                // Agregar la fila de TOTAL al finalizar el grupo actual
////                Vector filaTotal = new Vector();
////                filaTotal.add(""); // Indice
////                filaTotal.add(""); // Espacio para jerarquía
////                filaTotal.add("TOTAL"); // Etiqueta TOTAL
////                filaTotal.add(String.format("%.2f", totalOrden));  // Total orden
////                filaTotal.add(String.format("%.2f", totalProMenPeso));  // Total nd_proMenVen
////                filaTotal.add(String.format("%.2f", totalSugeridoPeso));  // Total totalSugerido
////                filaTotal.add(String.format("%.2f", totalProMenDolar)); // Total totalSugPeso
////                filaTotal.add(String.format("%.2f", totalSugeridoDolar));  // Total totalSugPrecio
////                vecDatosProcesados.add(filaTotal);
////            }
////
////            // Reiniciar los totales para la nueva línea
////            totalProMenPeso = 0;
////            totalSugeridoPeso = 0;
////            totalOrden = 0;   
////            totalProMenDolar=0;
////            totalSugeridoDolar=0;
////
////            // Crear fila principal para la nueva línea
////            Vector filaLinea = new Vector();
////            filaLinea.add(""); // Indice
////            filaLinea.add(linea); // Línea
////            filaLinea.add(""); // Clase vacío
////            filaLinea.add(""); // Orden
////            filaLinea.add(""); // Prom.Peso.
////            filaLinea.add(""); // Sug.Peso.
////            filaLinea.add(""); // Prom.Dolar
////            filaLinea.add(""); // Sug.Dolar
////            
////            vecDatosProcesados.add(filaLinea);
////            lineaActual = linea;
////        }
////
////        // Acumular totales
////
////        
////       
////
////        totalProMenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_PESO).toString());
////        totalSugeridoPeso+= Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_PESO).toString());
////        totalProMenDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOLAR).toString());
////        totalOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN).toString());        
////        totalSugeridoDolar += Double.parseDouble(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR).toString());
////        
////        // Crear fila solo con clase y valores (sin repetir la línea)
////        Vector filaClase = new Vector();
////        filaClase.add(""); // Indice
////        filaClase.add(""); // Espacio para jerarquía
////        filaClase.add(registro.get(INT_TBL_DAT_CLASE)); // Clase
////        filaClase.add(registro.get(INT_TBL_DAT_ORDEN)); // Orden    
////
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_PESO)); // Prom. 
////        filaClase.add(registro.get(INT_TBL_DAT_PREC_SUG_PESO)); // Sugerido
////        filaClase.add(registro.get(INT_TBL_DAT_PROM_DOLAR)); // Sug. Pes
////        filaClase.add(registro.get(INT_TBL_DAT_PREC_SUG_DOLAR)); // Sug. Prec.
////        vecDatosProcesados.add(filaClase);
////    }
////
////    // Agregar la fila de TOTAL para el último grupo
////    if (!lineaActual.isEmpty()) {
////        Vector filaTotal = new Vector();
////        filaTotal.add(""); // Indice
////        filaTotal.add(""); // Espacio para jerarquía
////        filaTotal.add("TOTAL"); // Etiqueta TOTAL
////
////        filaTotal.add(String.format("%.2f", totalOrden));  // Total Orden
////        filaTotal.add(String.format("%.2f", totalProMenPeso)); // Total Promedio
////        filaTotal.add(String.format("%.2f", totalSugeridoPeso));  // Total Sugerido
////        filaTotal.add(String.format("%.2f", totalProMenDolar)); // Total Sugerido Peso
////        filaTotal.add(String.format("%.2f", totalSugeridoDolar));  // Total Sugerido Precio
////        vecDatosProcesados.add(filaTotal);
////    }
////
////    return vecDatosProcesados;
////}



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panRep = new javax.swing.JScrollPane();
        panGen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar1 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot1 = new javax.swing.JPanel();
        butExport = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sugerencia de Pedido...");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(161, 157));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        panTit.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Reporte de Compras-Sugerencia de Pedido");
        lblTit.setPreferredSize(new java.awt.Dimension(207, 25));
        panTit.add(lblTit, java.awt.BorderLayout.NORTH);

        PanFrm.add(panTit, java.awt.BorderLayout.NORTH);

        panGen.setPreferredSize(new java.awt.Dimension(0, 50));
        panGen.setLayout(new java.awt.BorderLayout());

        panGen1.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panGen1.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen1);

        panGen.add(tabFrm, java.awt.BorderLayout.CENTER);

        panRep.setViewportView(panGen);

        PanFrm.add(panRep, java.awt.BorderLayout.CENTER);

        panBar1.setLayout(new java.awt.BorderLayout());
        panBar1.add(panBot, java.awt.BorderLayout.LINE_END);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butExport.setText("Exportar");
        butExport.setPreferredSize(new java.awt.Dimension(92, 25));
        butExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExportActionPerformed(evt);
            }
        });
        panBot1.add(butExport);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot1.add(butCan);

        panBar.add(panBot1, java.awt.BorderLayout.CENTER);

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

        panBar1.add(panBar, java.awt.BorderLayout.SOUTH);

        PanFrm.add(panBar1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(445, 299));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExportActionPerformed
      try 
      {
         LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String directorio = "C:\\Zafiro\\Reportes\\Importaciones\\ZafImp18\\";
        String fechaFormateada = fechaHoraActual.format(formato);
        String nombreArchivo = "ZafImp18_" + fechaFormateada + ".xlsx";
        File directorioFile = new File(directorio);
        
        if (!directorioFile.exists()) {
            directorioFile.mkdirs();
        }

        String archivoCompleto = directorio + nombreArchivo;

        objExcel = new ZafExpoExc();
        objExcel.exportarJTableAExcel(tblDat, archivoCompleto);
        mostrarMsgInf("Reporte exportado de forma exitosa a la ubicación: " + archivoCompleto);

        // Intentar abrir el archivo automáticamente
        File archivo = new File(archivoCompleto);
        if (archivo.exists()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(archivo);
            } else {
                mostrarMsgInf("El sistema no soporta la apertura automática de archivos.");
            }
        } else {
            mostrarMsgInf("El archivo no se encontró en la ubicación especificada.");
        }
    } catch (IOException ex) {
        mostrarMsgInf("Falló la exportación del archivo. Puede ser que el archivo esté abierto.");
    } catch (Exception ex) {
        mostrarMsgInf("Ocurrió un error inesperado: " + ex.getMessage());
    } 
    }//GEN-LAST:event_butExportActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        dispose();        
    }//GEN-LAST:event_butCanActionPerformed

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
  
    }//GEN-LAST:event_tblDatMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butExport;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBar1;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBot1;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGen1;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JScrollPane panRep;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean acepta(){
 
        return true;
    } 
    
   

   
  
    
    
}


