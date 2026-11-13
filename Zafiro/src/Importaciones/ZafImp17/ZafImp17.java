/*
 * ZafImp17.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Importaciones.ZafImp17;
////import Importaciones.ZafImp17.ZafImp17;
////import Importaciones.ZafImp17.ZafImp17;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafUtilGP.HashMapPer.Imp17_ItemsAtri;
import Librerias.ZafUtilGP.HashMapPer.Imp17_ItemsAtriPedNot;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafImp17 extends javax.swing.JDialog 
{
    //Variables.
     private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafUtilGP objUtiGP;
    private ZafTblMod objTblMod;
    public String strObs;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    //Variables de la clase.
   
    final int INT_TBL_DAT_LIN_PESO=0;
    final int INT_TBL_DAT_LINEA_PESO=1;
    final int INT_TBL_DAT_CLASE_PESO=2;
    final int INT_TBL_DAT_PROM_KIL_PESO=3;
    final int INT_TBL_DAT_STO_ACT_PESO=4;
    final int INT_TBL_DAT_STO_IMP_PESO=5;
    final int INT_TBL_DAT_STO_TRAN_PESO=6;
    final int INT_TBL_DAT_INV_TOT_PESO=7;
    final int INT_TBL_DAT_ORDEN_PESO=8;
    final int INT_TBL_DAT_PROM_DOL_DOLA=9; 
    final int INT_TBL_DAT_STO_ACT_DOLA=10;
    final int INT_TBL_DAT_STO_IMP_DOLA=11;
    final int INT_TBL_DAT_STO_TRAN_DOLA=12;
    final int INT_TBL_DAT_INV_TOT_DOLA=13;
    
    
    
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

    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafImp17(Frame parent, ZafParSis obj)
            //    public ZafCom113_02(Frame parent,  ZafParSis ZafParSis)
    {
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
            arlDat=new ArrayList();
            configurarFrm(); 
    }
    
    public ZafImp17(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        initComponents(); 
        objParSis=obj;
        arlDat=new ArrayList(); 
        configurarFrm(); 
    }    
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }       
        private void  configurarFrm()
    {
         // Configuración del tamaño del JDialog
         setSize(1500,1000);
         
         
         configurarTblDat();
         HashMap<String, Imp17_ItemsAtriPedNot>   dataMapConsultaGlobaPedNot = new HashMap<>();
         HashMap<String, Imp17_ItemsAtri>   dataMapConsultaGloba = new HashMap<>();
////         cargarPresupuestoVentasPedidosNotas(dataMapConsultaGlobaPedNot);
         cargarPresupuestoVentasGlobales(dataMapConsultaGloba);
////         UnificacionPromedios(dataMapConsultaGloba,dataMapConsultaGlobaPedNot);
         CargarReg(dataMapConsultaGloba);
         
    } 
    

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_LINEA_PESO:
                    strMsg = "Linea";
                    break;
                case INT_TBL_DAT_CLASE_PESO:
                    strMsg = "Clase";
                    break;
                case INT_TBL_DAT_PROM_KIL_PESO:
                    strMsg = "Promedio (Peso)";
                    break;   
                case INT_TBL_DAT_STO_ACT_PESO:
                    strMsg = "Disponibe (Peso)";
                    break;
                case INT_TBL_DAT_STO_IMP_PESO:
                    strMsg = "Importaciones (Peso) ";
                    break;
                case INT_TBL_DAT_STO_TRAN_PESO:
                    strMsg = "Transito (Peso)";
                    break;
                case INT_TBL_DAT_INV_TOT_PESO:
                    strMsg = "Inventario Total (Peso) ";
                    break;      
                case INT_TBL_DAT_ORDEN_PESO:
                    strMsg = "Orden";
                    break; 
                case INT_TBL_DAT_PROM_DOL_DOLA:
                    strMsg = "Promedio ($)";
                    break;  
                case INT_TBL_DAT_STO_ACT_DOLA:
                    strMsg = "Disponible ($) ";
                    break;
                case INT_TBL_DAT_STO_IMP_DOLA:
                    strMsg = "Importaciones ($) ";
                    break;   
                case INT_TBL_DAT_STO_TRAN_DOLA:
                    strMsg = "Transito ($) ";
                    break;  
                case INT_TBL_DAT_INV_TOT_DOLA:
                    strMsg = "Inv. Total ($) ";
                    break;                     
                    
                    
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  



private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try 
            {
                vecCab = new Vector();
                vecCab.clear();
                vecCab.add(""); // Columna adicional para jerarquía
                vecCab.add("Línea");
                vecCab.add("Clase");
                vecCab.add("Prom.(KG)");
                vecCab.add("Disp.(KG)");
                vecCab.add("Import.(KG)");
                vecCab.add("Tran.(KG)");
                vecCab.add("Invent.(KG)");
                vecCab.add("Orden");
                vecCab.add("Prom.($)");
                vecCab.add("Disp.($)");
                vecCab.add("Import.($)");                
                vecCab.add("Trans.($)");  
                vecCab.add("Invent.($)");  
                
                vecAux = new Vector();
                vecAux.clear();
                vecAux.add(""); // Columna adicional para jerarquía
                vecAux.add("Línea");
                vecAux.add("Clase");
                vecAux.add("Prom. Kilo");
                vecAux.add("Disponible");
                vecAux.add("Stock Actual");
                vecAux.add("Importaciones");
                vecAux.add("Transito");
                vecAux.add("Inventario Total");
                vecAux.add("Orden");
                vecAux.add("Prom. Valor");
                vecAux.add("Disponible");
                vecAux.add("Importaciones");
                vecAux.add("Transito");
                vecAux.add("Inventario Total");
                objTblMod = new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);

                // Configuración del renderizador
                TableColumnModel tcmAux = tblDat.getColumnModel();
                tcmAux.getColumn(INT_TBL_DAT_LINEA_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_CLASE_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PROM_KIL_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_STO_ACT_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_STO_IMP_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_STO_TRAN_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_INV_TOT_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ORDEN_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PROM_DOL_DOLA).setCellRenderer(new JerarquiaRenderer());  
                tcmAux.getColumn(INT_TBL_DAT_STO_ACT_DOLA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_STO_IMP_DOLA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_STO_TRAN_DOLA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_INV_TOT_DOLA).setCellRenderer(new JerarquiaRenderer());

                // Configurar anchura de las columnas
                tblDat.getColumnModel().getColumn(0).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(1).setPreferredWidth(250);
                tblDat.getColumnModel().getColumn(2).setPreferredWidth(300);
                tblDat.getColumnModel().getColumn(3).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(4).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(5).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(6).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(7).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(8).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(9).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(10).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(11).setPreferredWidth(200);
                tblDat.getColumnModel().getColumn(12).setPreferredWidth(200);
                
                
                
                
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN_PESO, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN_PESO, tblDat);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
    }

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
        String clase = (String) table.getValueAt(row, INT_TBL_DAT_CLASE_PESO);

        if (isSelected) {
            // Si la celda está seleccionada, pintar de azul claro
            c.setBackground(new Color(173, 216, 230)); // Azul claro
            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Mantener negrita si está seleccionado
        } else if (clase == null || clase.isEmpty()) {
            // Línea principal
            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para la línea principal
            c.setBackground(Color.LIGHT_GRAY); // Fondo para la línea principal
        } else {
                if(clase.equals("TOTAL"))
                {
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para la línea principal
                    //c.setBackground(Color.LIGHT_GRAY); // Fondo para la línea principal                    
                }
                else
                {
                    // Clases normales
                    c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Fuente normal para las clases
                    c.setBackground(Color.WHITE); // Fondo normal                    
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
        return c;
    }
}

////private boolean cargarPresupuestoVentasGlobalesAnterior(HashMap<String, Imp17_ItemsAtri> dataMapConsultaGloba)
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
////                strSQLGP+="SELECT a1.Linea,a1.clase,a1.nd_proMenVenNuevo,a1.nd_proMenVenPeso,cfinv.ne_ord, \n";
////                strSQLGP+="       a1.nd_stkActPeso as disponiblePeso,a1.nd_stkImpPeso as importacionesPeso,a1.nd_transitoPeso as transitoPeso,a1.nd_stockTotalPeso as invtotalPeso,  \n";
////                strSQLGP+="       a1.nd_stkActDolar as disponibleDolar,a1.nd_stkImpDolar as importacionesDolar,a1.nd_transitoDolar as transitoDolar,a1.nd_stockTotalDolar as invtotalDolar   \n";                
////                strSQLGP+="FROM ( \n ";
////                strSQLGP+="         SELECT a1.Linea, a1.clase, /* a3.ne_ord,*/ ISNULL(sum(a1.nd_proMenVenNuevo),0) as nd_proMenVenNuevo  \n";
////                strSQLGP+="                ,round (ISNULL(sum(a1.nd_proMenVenPeso),0),2) as nd_proMenVenPeso \n";
////                strSQLGP+="                ,ISNULL(sum(a1.nd_DisponiblePeso),0) as nd_stkActPeso,ISNULL(sum(a1.nd_ImportacionesPeso),0) as nd_stkImpPeso  \n";
////                strSQLGP+="                ,ISNULL(sum(a1.nd_TransitoPeso),0) as nd_transitoPeso,ISNULL(sum(a1.nd_InvTotalPeso),0) as nd_stockTotalPeso     \n";
////                strSQLGP+="                ,ISNULL(sum(a1.nd_DisponibleDolar),0) as nd_stkActDolar,ISNULL(sum(a1.nd_ImportacionesDolar),0) as nd_stkImpDolar  \n";
////                strSQLGP+="                ,ISNULL(sum(a1.nd_TransitoDolar),0) as nd_transitoDolar,ISNULL(sum(a1.nd_InvTotalDolar),0) as nd_stockTotalDolar     \n";                
////                strSQLGP+="         FROM ( \n ";
////                strSQLGP+="                 SELECT trim(a1.ITEMNMBR) as ITEMNMBR, a1.ITMSHNAM, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL,   \n";
////                strSQLGP+="                        extItem.Extender_Record_ID, TRIM(extLinea.STRGA255) as Linea, TRIM(extClase.STRGA255) as clase,  \n";
////                strSQLGP+="                        a2.nd_proAnuVenMan AS nd_proAnuVenManSolito,ROUND((a3.UOMPRICE * 0.75) * (ISNULL(a2.nd_proAnuVenMan,0)),2) as nd_proMenVenNuevo \n";
////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(a2.nd_proAnuVenMan, 0) )  AS nd_proMenVenPeso    \n";
////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stkAct, 0) )  AS nd_DisponiblePeso   \n";
////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stkImp, 0) )  AS nd_ImportacionesPeso    \n";
////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_transito, 0) )  AS nd_TransitoPeso    \n";
////                strSQLGP+="                        ,((isnull(extPeso.TOTAL,0)) *  ISNULL(disp.nd_stockTotal, 0) )  AS nd_InvTotalPeso ,   \n";
////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stkAct,0)),2) as nd_DisponibleDolar,   \n";
////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stkImp,0)),2) as nd_ImportacionesDolar,   \n";
////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_transito,0)),2) as nd_TransitoDolar,   \n";
////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(disp.nd_stockTotal,0)),2) as nd_InvTotalDolar   \n";
////                strSQLGP+="                 FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 (nolock) AS a1 \n";
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01100 as extItem ON (a1.ITEMNMBR=extItem.Extender_Key_Values_1 AND Extender_Window_ID = 'MSW_ADI_IV') \n";                 
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extLinea ON (extItem.Extender_Record_ID=extLinea.Extender_Record_ID AND extLinea.Field_ID =196) \n";                 
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extClase ON (extItem.Extender_Record_ID=extClase.Extender_Record_ID AND extClase.Field_ID =197) \n";  
////                strSQLGP+="                        INNER JOIN Dynamics2021.dbo.tbm_inv (nolock) as a2 ON (a1.ITEMNMBR=a2.tx_itm) \n";
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00108 (nolock) as a3 ON (a1.ITEMNMBR=a3.ITEMNMBR AND a3.PRCLEVEL='GENERAL') \n";     
////                strSQLGP+="                        LEFT JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01103 AS extPeso ON (extItem.Extender_Record_ID = extPeso.Extender_Record_ID AND extPeso.Field_ID = 179) \n";                 
////                strSQLGP+="                        LEFT JOIN Dynamics2021..view_DIS_IMP_TRANS_EXI_TOTAL as disp   on (a1.ITEMNMBR = disp.co_itmMae ) \n";                      
////                strSQLGP+="                 WHERE  a1.ITEMTYPE = 1 AND (a1.ITEMNMBR like '%I' OR a1.ITEMNMBR like '%S' )  and a1.inactive =0 \n"; 
////                strSQLGP+="              ) as a1  \n"; 
////                strSQLGP+="          GROUP BY a1.Linea, a1.clase  \n"; 
////                strSQLGP+="              ) as a1  \n"; 
////                strSQLGP+="              INNER JOIN  Dynamics2021..tbm_cfgAtrInv as cfinv on (a1.linea = cfinv.tx_cfgPadDat and cfinv.tx_cfgHijDat is null and cfinv.co_cfg =1) \n"; 
////                strSQLGP+=" ORDER BY cfinv.ne_ord ASC";
////                System.out.println("SQL cargarPresupuestoVentasGlobales: " + strSQLGP);
////                rstGP=stmGP.executeQuery(strSQLGP);
////                //Limpiar vector de datos.
////////                vecCab.clear();
////////                vecAux.clear();
////                
////                String llave ="";
////                //Obtener los registros.
////                while (rstGP.next()){
////                    
////                    llave = rstGP.getString("Linea").toString() +"-"+ rstGP.getString("clase").toString();
////                    dataMapConsultaGloba.put(llave, new Imp17_ItemsAtri (rstGP.getString("Linea"),rstGP.getString("clase"),rstGP.getDouble("nd_proMenVenNuevo"),rstGP.getDouble("nd_proMenVenPeso"),rstGP.getInt("ne_ord"),rstGP.getDouble("disponiblePeso"),rstGP.getDouble("importacionesPeso"),rstGP.getDouble("transitoPeso"),rstGP.getDouble("invtotalPeso"),rstGP.getDouble("disponibleDolar"),rstGP.getDouble("importacionesDolar"),rstGP.getDouble("transitoDolar"),rstGP.getDouble("invtotalDolar")));                      
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

private boolean cargarPresupuestoVentasGlobales(HashMap<String, Imp17_ItemsAtri> dataMapConsultaGloba)
    {
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1;
        String strEstPedEli="";
        java.sql.CallableStatement cal; 
        try{
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null){
                stmGP=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQLGP="";
                strSQLGP="{call sp_ElecucionReporteImp(?)}";
                cal = conGP.prepareCall(strSQLGP);
                cal.setInt(1,  2);
////                cal.registerOutParameter(2, java.sql.Types.NVARCHAR);
                boolean hasResults = cal.execute();  
                String llave ="";
                if (hasResults) 
                {
                    rstGP = cal.getResultSet();
                    vecCab.clear();
                    vecAux.clear();   
                    while (rstGP.next()) 
                    {
                        llave = rstGP.getString("linea").toString() +"-"+ rstGP.getString("clase").toString();
                        dataMapConsultaGloba.put(llave, new Imp17_ItemsAtri (rstGP.getString("linea"),rstGP.getString("clase"),rstGP.getDouble("nd_proMenVenDolar"),rstGP.getDouble("nd_proMenVenPeso"),rstGP.getInt("ne_ord"),rstGP.getDouble("nd_DisponiblePeso"),rstGP.getDouble("nd_ImportacionesPeso"),rstGP.getDouble("nd_TransitoPeso"),rstGP.getDouble("nd_InvTotalPeso"),rstGP.getDouble("nd_DisponibleDolar"),rstGP.getDouble("nd_ImportacionesDolar"),rstGP.getDouble("nd_TransitoDolar"),rstGP.getDouble("nd_InvTotalDolar")));                                              
                    }                    
                }                
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

private boolean cargarPresupuestoVentasPedidosNotas(HashMap<String, Imp17_ItemsAtriPedNot> dataMapConsultaPedNot)
    {
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1;
        String strEstPedEli="";
        try{
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null){
                stmGP=conGP.createStatement();
                //Armar la sentencia SQL.
                strSQLGP="";
                strSQLGP+="SELECT a1.Linea,a1.clase,sum(a1.nd_can) as totalPediDol, sum (a1.nd_proMenVenPeso) as totalPediKil \n";
                strSQLGP+="FROM ( \n ";
                strSQLGP+="         SELECT TRIM(extLinea.STRGA255) as Linea, TRIM(extClase.STRGA255) as clase, a1.nd_can-- /*a1.item,*/ sum (a1.nd_can) as totalpedido \n";
////                strSQLGP+="                ,CASE WHEN extPeso.STRGA255 IS NULL OR extPeso.STRGA255 = '' THEN 0  \n";
////                strSQLGP+="                 ELSE CAST(extPeso.STRGA255 AS FLOAT) END AS Peso_KG  \n";                
////                strSQLGP+="                ,CASE WHEN extPeso.STRGA255 IS NULL OR extPeso.STRGA255 = '' THEN 0  \n";
////                strSQLGP+="                 ELSE CAST(extPeso.STRGA255 AS FLOAT) * ISNULL(a1.nd_can, 0) END AS nd_proMenVenPeso   \n";      
                strSQLGP+="                  ,isnull(extPeso.TOTAL,0) AS Peso_KG   \n";  
                strSQLGP+="                  ,((isnull(extPeso.TOTAL,0)) *  ISNULL(a1.nd_can, 0) )  AS nd_proMenVenPeso   \n";                  
                strSQLGP+="         FROM   Dynamics2021.dbo.view_det_pedi_not as a1 \n ";
                strSQLGP+="                INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01100 as extItem ON (a1.item=extItem.Extender_Key_Values_1 AND Extender_Window_ID = 'MSW_ADI_IV') \n";                 
                strSQLGP+="                INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extLinea ON (extItem.Extender_Record_ID=extLinea.Extender_Record_ID AND extLinea.Field_ID =196) \n";                 
                strSQLGP+="                INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extClase ON (extItem.Extender_Record_ID=extClase.Extender_Record_ID AND extClase.Field_ID =197) \n";                 
                strSQLGP+="                LEFT JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01103 AS extPeso ON (extItem.Extender_Record_ID = extPeso.Extender_Record_ID AND extPeso.Field_ID = 179) \n";                 
                strSQLGP+="     ) as a1  \n"; 
                strSQLGP+="          GROUP BY a1.Linea, a1.clase  \n"; 
                System.out.println("SQL cargarPresupuestoVentasPedidosNotas: " + strSQLGP);
                rstGP=stmGP.executeQuery(strSQLGP);
                //Limpiar vector de datos.
////                vecCab.clear();
////                vecAux.clear();
               
                String llave ="";
                //Obtener los registros.
                while (rstGP.next()){
                    
                    llave = rstGP.getString("Linea").toString() +"-"+ rstGP.getString("clase").toString();
                    dataMapConsultaPedNot.put(llave, new Imp17_ItemsAtriPedNot (rstGP.getDouble("totalPediDol"),rstGP.getDouble("totalPediKil")));                      
                }
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

private boolean UnificacionPromedios(HashMap<String, Imp17_ItemsAtri> dataMapConsultaGloba,HashMap<String, Imp17_ItemsAtriPedNot> dataMapConsultaPedNot)
    {
        boolean blnRes=false;
        
        for (String llave : dataMapConsultaGloba.keySet()) 
        {
        // Verificar si la llave también existe en dataMapConsultaGlobaPedNot
            if (dataMapConsultaPedNot.containsKey(llave)) {
                // Obtener los valores del primer HashMap
                Imp17_ItemsAtri itemAtri = dataMapConsultaGloba.get(llave);

                // Obtener los valores del segundo HashMap
                Imp17_ItemsAtriPedNot itemAtriPedNot = dataMapConsultaPedNot.get(llave);

                // Actualizar los valores del primer HashMap
                double nuevoNdProMenVenNuevo = redondear(itemAtri.getNd_totProDo() + itemAtriPedNot.getNd_totPediDo(), 2);  
                double nuevoNdProMenVenPeso = redondear(itemAtri.getNd_totProKi() + itemAtriPedNot.getNd_totPediKi(), 2);


                // Setear los valores actualizados en el objeto del primer HashMap
                itemAtri.setNd_totProDo(nuevoNdProMenVenNuevo);
                itemAtri.setNd_totProKi(nuevoNdProMenVenPeso);

                // Actualizar el objeto en el HashMap
                dataMapConsultaGloba.put(llave, itemAtri);      
    }
}

        
        
        return blnRes;
    } 

// Método auxiliar para redondear valores a un número específico de decimales
private double redondear(double valor, int decimales) {
    if (decimales < 0) throw new IllegalArgumentException("El número de decimales debe ser mayor o igual a 0");
    BigDecimal bd = BigDecimal.valueOf(valor);
    bd = bd.setScale(decimales, RoundingMode.HALF_UP);
    return bd.doubleValue();
}

private boolean CargarReg(HashMap<String, Imp17_ItemsAtri> dataMapConsultaGloba)
    {
    boolean blnRes=false;
    vecCab.clear();
    vecAux.clear();    
    for (String llave : dataMapConsultaGloba.keySet()) 
    {
        Imp17_ItemsAtri item = dataMapConsultaGloba.get(llave);
    
        Vector<Object> vecReg1 = new Vector<>();

        vecReg1.add(INT_TBL_DAT_LIN_PESO,"");
        vecReg1.add(INT_TBL_DAT_LINEA_PESO,item.getSt_linea());
        vecReg1.add(INT_TBL_DAT_CLASE_PESO,item.getSt_clase());
        
        vecReg1.add(INT_TBL_DAT_PROM_KIL_PESO,item.getNd_totProKi());
        vecReg1.add(INT_TBL_DAT_STO_ACT_PESO,item.getNd_stkAct());
        vecReg1.add(INT_TBL_DAT_STO_IMP_PESO,item.getNd_stkImp());
        vecReg1.add(INT_TBL_DAT_STO_TRAN_PESO,item.getNd_stkTra());
        vecReg1.add(INT_TBL_DAT_INV_TOT_PESO,item.getNd_stkTotal());
        vecReg1.add(INT_TBL_DAT_ORDEN_PESO,item.getNe_orden());
        vecReg1.add(INT_TBL_DAT_PROM_DOL_DOLA,item.getNd_totProDo());
        vecReg1.add(INT_TBL_DAT_STO_ACT_DOLA,item.getNd_stkActDola());
        vecReg1.add(INT_TBL_DAT_STO_IMP_DOLA,item.getNd_stkImpDola());
        vecReg1.add(INT_TBL_DAT_STO_TRAN_DOLA,item.getNd_stkTraDola());
        vecReg1.add(INT_TBL_DAT_INV_TOT_DOLA,item.getNd_stkTotalDola());
        vecCab1.add(vecReg1);
        
        // Ordenar el vector por INT_TBL_DAT_ORDEN (índice 9)
       
    }
 Collections.sort(vecCab1, new Comparator<Vector<Object>>() {
            @Override
            public int compare(Vector<Object> o1, Vector<Object> o2) {
                Integer orden1 = (Integer) o1.get(8); // INT_TBL_DAT_ORDEN
                Integer orden2 = (Integer) o2.get(8); // INT_TBL_DAT_ORDEN
                return orden1.compareTo(orden2);
            }
        });

                for (Vector<Object> vec : vecCab1) {
                    System.out.println(vec);
        }
                
                
    
    vecAux = procesarDatosJerarquicamente(vecCab1);
    objTblMod.setData(vecAux);
    tblDat.setModel(objTblMod);    
    
    return blnRes;
}
   
   

private Vector procesarDatosJerarquicamente(Vector vecDatos) {
    Vector vecDatosProcesados = new Vector();
    String lineaActual = "";
    double totalProMenVenNuevo = 0; // Total para nd_proMenVenNuevo
    double totalProMenVenPeso = 0;  // Total para nd_proMenVenPeso
    double totalStockActual = 0; // Total para Stock Actual
    double totalStockImport = 0;  // Total para Importaciones
    double totalStockTrans = 0; // Total para Transito
    double totalProStockTotal = 0;  // Total para Stock Total
    double totalOrden = 0;  // Total para orden    
    
    double totalStockActual_Dol = 0; // Total para Stock Actual  
    double totalStockImport_Dol = 0; // Total para Importaciones  
    double totalStockTrans_Dol = 0; // Total para Transito  
    double totalStockTotal_Dol = 0; // Total para Stock Total 

    for (int i = 0; i < vecDatos.size(); i++) {
        Vector registro = (Vector) vecDatos.get(i);
        String linea = (String) registro.get(INT_TBL_DAT_LINEA_PESO);

        // Si se detecta un cambio de línea
        if (!linea.equals(lineaActual)) {
            if (!lineaActual.isEmpty()) {
                // Agregar la fila de TOTAL al finalizar el grupo actual
                Vector filaTotal = new Vector();
                filaTotal.add(""); // Indice
                filaTotal.add(""); // Espacio para jerarquía
                filaTotal.add("TOTAL"); // Etiqueta TOTAL
                filaTotal.add(String.format("%.2f", totalProMenVenPeso));  // Total nd_proMenVenPeso
                filaTotal.add(String.format("%.2f", totalStockActual)); // Total Stock Actual
                filaTotal.add(String.format("%.2f", totalStockImport));  // Total Importaciones
                filaTotal.add(String.format("%.2f", totalStockTrans)); // Total Transito
                filaTotal.add(String.format("%.2f", totalProStockTotal));  // Total Stock Total
                filaTotal.add(String.format("%.2f", totalOrden));  // Total orden
                filaTotal.add(String.format("%.2f", totalProMenVenNuevo)); // Total nd_proMenVenNuevo
                filaTotal.add(String.format("%.2f", totalStockActual_Dol)); // Total Stock Actual 
                filaTotal.add(String.format("%.2f", totalStockImport_Dol)); // Total Importaciones 
                filaTotal.add(String.format("%.2f", totalStockTrans_Dol)); // Total Transito 
                filaTotal.add(String.format("%.2f", totalStockTotal_Dol)); // Total Stock Total 
                vecDatosProcesados.add(filaTotal);
            }

            // Reiniciar los totales para la nueva línea
            totalProMenVenNuevo = 0;
            totalProMenVenPeso = 0;
            totalStockActual = 0;
            totalStockImport = 0;
            totalStockTrans = 0;
            totalProStockTotal = 0;
            totalOrden = 0;   
            totalStockActual_Dol=0;
            totalStockImport_Dol=0;
            totalStockTrans_Dol=0;
            totalStockTotal_Dol=0;

            // Crear fila principal para la nueva línea
            Vector filaLinea = new Vector();
            filaLinea.add(""); // Indice
            filaLinea.add(linea); // Línea
            filaLinea.add(""); // Clase vacío
            filaLinea.add(""); // Prom. Kil vacío
            filaLinea.add(""); // Stock Actual
            filaLinea.add(""); // Stock Import
            filaLinea.add(""); // Stocak Transito
            filaLinea.add(""); // Stock Total
            filaLinea.add(""); // Orden
            filaLinea.add(""); // Prom. Valor vacío
            filaLinea.add(""); // Stock Actual 
            filaLinea.add(""); // Stock Import 
            filaLinea.add(""); // Stock Transito 
            filaLinea.add(""); // Stock Total 
            vecDatosProcesados.add(filaLinea);
            lineaActual = linea;
        }

        // Acumular totales

        totalProMenVenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_KIL_PESO).toString());
        totalStockActual += Double.parseDouble(registro.get(INT_TBL_DAT_STO_ACT_PESO).toString());
        totalStockImport += Double.parseDouble(registro.get(INT_TBL_DAT_STO_IMP_PESO).toString());
        totalStockTrans += Double.parseDouble(registro.get(INT_TBL_DAT_STO_TRAN_PESO).toString());
        totalProStockTotal += Double.parseDouble(registro.get(INT_TBL_DAT_INV_TOT_PESO).toString());
        totalOrden += Double.parseDouble(registro.get(INT_TBL_DAT_ORDEN_PESO).toString());        
        totalProMenVenNuevo += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOL_DOLA).toString());
        totalStockActual_Dol += Double.parseDouble(registro.get(INT_TBL_DAT_STO_ACT_DOLA).toString());
        totalStockImport_Dol += Double.parseDouble(registro.get(INT_TBL_DAT_STO_IMP_DOLA).toString());
        totalStockTrans_Dol += Double.parseDouble(registro.get(INT_TBL_DAT_STO_TRAN_DOLA).toString());
        totalStockTotal_Dol += Double.parseDouble(registro.get(INT_TBL_DAT_INV_TOT_DOLA).toString());
        
        // Crear fila solo con clase y valores (sin repetir la línea)
        Vector filaClase = new Vector();
        filaClase.add(""); // Indice
        filaClase.add(""); // Espacio para jerarquía
        filaClase.add(registro.get(INT_TBL_DAT_CLASE_PESO)); // Clase

        filaClase.add(registro.get(INT_TBL_DAT_PROM_KIL_PESO)); // Prom. Kil
        filaClase.add(registro.get(INT_TBL_DAT_STO_ACT_PESO)); // Stock Actual
        filaClase.add(registro.get(INT_TBL_DAT_STO_IMP_PESO)); // Stock Importa
        filaClase.add(registro.get(INT_TBL_DAT_STO_TRAN_PESO)); // Stock Trans
        filaClase.add(registro.get(INT_TBL_DAT_INV_TOT_PESO)); // Stock Total
        filaClase.add(registro.get(INT_TBL_DAT_ORDEN_PESO)); // Orden       
        filaClase.add(registro.get(INT_TBL_DAT_PROM_DOL_DOLA)); // Prom. Valor
        filaClase.add(registro.get(INT_TBL_DAT_STO_ACT_DOLA)); // Stock Actual 
        filaClase.add(registro.get(INT_TBL_DAT_STO_IMP_DOLA)); // Stock Importa 
        filaClase.add(registro.get(INT_TBL_DAT_STO_TRAN_DOLA)); // Stock Trans 
        filaClase.add(registro.get(INT_TBL_DAT_INV_TOT_DOLA)); // Stock Trans 
        vecDatosProcesados.add(filaClase);
    }

    // Agregar la fila de TOTAL para el último grupo
    if (!lineaActual.isEmpty()) {
        Vector filaTotal = new Vector();
        filaTotal.add(""); // Indice
        filaTotal.add(""); // Espacio para jerarquía
        filaTotal.add("TOTAL"); // Etiqueta TOTAL

        filaTotal.add(String.format("%.2f", totalProMenVenPeso));  // Total nd_proMenVenPeso
        filaTotal.add(String.format("%.2f", totalStockActual)); // Total Stock Actual
        filaTotal.add(String.format("%.2f", totalStockImport));  // Total Stock Importa
        filaTotal.add(String.format("%.2f", totalStockTrans)); // Total Stock Transito
        filaTotal.add(String.format("%.2f", totalProStockTotal));  // Total Stock         
        filaTotal.add(String.format("%.2f", totalOrden));  // Total Orden
        filaTotal.add(String.format("%.2f", totalProMenVenNuevo)); // Total nd_proMenVenNuevo
        filaTotal.add(String.format("%.2f", totalStockActual_Dol)); // Total Stock Actual   
        filaTotal.add(String.format("%.2f", totalStockImport_Dol)); // Total Stock Importa  
        filaTotal.add(String.format("%.2f", totalStockTrans_Dol)); // Total Stock Trans  
        filaTotal.add(String.format("%.2f", totalStockTotal_Dol)); // Total Stock Actual  
        vecDatosProcesados.add(filaTotal);
    }

    return vecDatosProcesados;
}



    
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
        panGen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butExport = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Presupuesto de Ventas-Global...");
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
        lblTit.setText("Reporte de Presupuesto de Venta-Global");
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

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butExport.setText("Exportar");
        butExport.setPreferredSize(new java.awt.Dimension(92, 25));
        butExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExportActionPerformed(evt);
            }
        });
        panBot.add(butExport);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.LINE_END);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(445, 317));
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
        String directorio = "C:\\Zafiro\\Reportes\\Importaciones\\ZafImp17\\";
        String fechaFormateada = fechaHoraActual.format(formato);
        String nombreArchivo = "ZafImp17_" + fechaFormateada + ".xlsx";
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
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGen1;
    private javax.swing.JPanel panTit;
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


