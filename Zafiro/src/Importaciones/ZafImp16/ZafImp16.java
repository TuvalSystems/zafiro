/*
 * ZafImp16.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Importaciones.ZafImp16;
////import Importaciones.ZafImp16.ZafImp16;
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
import Librerias.ZafUtilGP.ZafUtilGP;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
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
public class ZafImp16 extends javax.swing.JDialog 
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
    final int INT_TBL_DAT_PROM_DOL=3;
    final int INT_TBL_DAT_PROM_KIL=4;
    final int INT_TBL_DAT_NE_ORDEN=5;
    
    private Vector vecReg,  vecCab, vecAux;
    
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
    private String strVersion ="0.3";
 
    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafImp16(Frame parent, ZafParSis obj)
            //    public ZafCom113_02(Frame parent,  ZafParSis ZafParSis)
    {
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
            arlDat=new ArrayList();
            configurarFrm();
    }
    
    public ZafImp16(java.awt.Frame parent, boolean modal, ZafParSis obj)
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
         setSize(800,800);
         
         configurarTblDat();
         cargarPresupuestoVentas(1);
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
                    strMsg = "Linea";
                    break;
                case INT_TBL_DAT_CLASE:
                    strMsg = "Clase";
                    break;
                case INT_TBL_DAT_PROM_DOL:
                    strMsg = "Valor";
                    break;
                case INT_TBL_DAT_PROM_KIL:
                    strMsg = "Peso";
                    break;              
                case INT_TBL_DAT_NE_ORDEN                    :
                    strMsg = "Orden";
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
                vecCab.add("Presu. Valor");
                vecCab.add("Presu. Kil.");
                vecCab.add("Orden");
                
                vecAux = new Vector();
                vecAux.clear();
                vecAux.add(""); // Columna adicional para jerarquía
                vecAux.add("Línea");
                vecAux.add("Clase");
                vecAux.add("Presu. Valor");
                vecAux.add("Presu. Kil.");
                vecAux.add("Orden");
                

                objTblMod = new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);

                // Configuración del renderizador
                TableColumnModel tcmAux = tblDat.getColumnModel();
                tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_CLASE).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PROM_DOL).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PROM_KIL).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_NE_ORDEN).setCellRenderer(new JerarquiaRenderer());

                // Configurar anchura de las columnas
                tblDat.getColumnModel().getColumn(0).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(2).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(3).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(4).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(5).setPreferredWidth(30);
                
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NE_ORDEN, tblDat);
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
        String clase = (String) table.getValueAt(row, INT_TBL_DAT_CLASE);

        if (isSelected) {
            // Si la celda está seleccionada, pintar de azul claro
            c.setBackground(new Color(173, 216, 230)); // Azul claro
            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Mantener negrita si está seleccionado
        } else if (clase == null || clase.isEmpty()) {
            // Línea principal
            c.setFont(c.getFont().deriveFont(Font.BOLD)); // Negrita para la línea principal
            c.setBackground(Color.LIGHT_GRAY); // Fondo para la línea principal
        } else 
        {
                if(clase.equals("TOTAL") || clase.equals("TOTAL GLOBAL"))
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
            if (column == INT_TBL_DAT_PROM_DOL || column == INT_TBL_DAT_PROM_KIL) {
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT); // Alineación por defecto
            }        
        return c;
    }
}

////public boolean cargarPresupuestoVentasAnterior()
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
////                strSQLGP+="SELECT a1.Linea,a1.clase,a1.nd_proMenVenNuevo,a1.nd_proMenVenPeso,cfinv.ne_ord \n";
////                strSQLGP+="FROM ( \n ";
////                strSQLGP+="         SELECT a1.Linea, a1.clase, /* a3.ne_ord,*/ ISNULL(sum(a1.nd_proMenVenNuevo),0) as nd_proMenVenNuevo \n";
////                strSQLGP+="                ,round (ISNULL(sum(a1.nd_proMenVenPeso),0),2) as nd_proMenVenPeso \n";
////                strSQLGP+="         FROM ( \n ";
////                strSQLGP+="                 SELECT trim(a1.ITEMNMBR) as ITEMNMBR, a1.ITMSHNAM, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL,  ";
////                strSQLGP+="                        extItem.Extender_Record_ID, TRIM(extLinea.STRGA255) as Linea, TRIM(extClase.STRGA255) as clase, \n";
////                strSQLGP+="                        (a3.UOMPRICE * 0.75) as preciox075, \n";
////                strSQLGP+="                        ROUND((a3.UOMPRICE * 0.75) * (ISNULL(a2.nd_proAnuVenMan,0)/12),2) as nd_proMenVen,  \n";
////                strSQLGP+="                        a2.nd_proAnuVenMan AS nd_proAnuVenManSolito,ROUND((a3.UOMPRICE * 0.75) * (ISNULL(a2.nd_proAnuVenMan,0)),2) as nd_proMenVenNuevo,  \n";
////                
////////                strSQLGP+="                        ,CASE WHEN extPeso.TOTAL IS NULL OR extPeso.TOTAL = '' THEN 0 \n";
////                //strSQLGP+="                        ELSE CAST(extPeso.STRGA255 AS FLOAT) END AS Peso_KG \n";
////                strSQLGP+="                        isnull(extPeso.TOTAL,0)  AS Peso_KG, \n";                
////////                strSQLGP+="                        ,CASE WHEN extPeso.STRGA255 IS NULL OR extPeso.STRGA255 = '' THEN 0 \n";
////////                strSQLGP+="                        ELSE CAST(extPeso.STRGA255 AS FLOAT) * ISNULL(a2.nd_proAnuVenMan, 0) END AS nd_proMenVenPeso \n";                
////                strSQLGP+="                        (isnull(extPeso.TOTAL,0) * ISNULL(a2.nd_proAnuVenMan, 0))  AS nd_proMenVenPeso \n";  
////                strSQLGP+="                 FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 (nolock) AS a1 \n";
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01100 as extItem ON (a1.ITEMNMBR=extItem.Extender_Key_Values_1 AND Extender_Window_ID = 'MSW_ADI_IV') \n";                 
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extLinea ON (extItem.Extender_Record_ID=extLinea.Extender_Record_ID AND extLinea.Field_ID =196) \n";                 
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01101 as extClase ON (extItem.Extender_Record_ID=extClase.Extender_Record_ID AND extClase.Field_ID =197) \n";                 
////                strSQLGP+="                        LEFT JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.EXT01103 AS extPeso ON (extItem.Extender_Record_ID = extPeso.Extender_Record_ID AND extPeso.Field_ID = 179) \n";                 
////                strSQLGP+="                        INNER JOIN Dynamics2021.dbo.tbm_inv (nolock) as a2 ON (a1.ITEMNMBR=a2.tx_itm) \n";     
////                strSQLGP+="                        INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00108 (nolock) as a3 ON (a1.ITEMNMBR=a3.ITEMNMBR AND a3.PRCLEVEL='GENERAL') \n";     
////                strSQLGP+="                 WHERE  a1.ITEMTYPE = 1 AND (a1.ITEMNMBR like '%I' OR a1.ITEMNMBR like '%S' )  \n"; 
////                strSQLGP+="              ) as a1  \n"; 
////                strSQLGP+="          GROUP BY a1.Linea, a1.clase  \n"; 
////                strSQLGP+="              ) as a1  \n"; 
////                strSQLGP+="              INNER JOIN  Dynamics2021..tbm_cfgAtrInv as cfinv on (a1.linea = cfinv.tx_cfgPadDat and cfinv.tx_cfgHijDat is null and cfinv.co_cfg =1) \n"; 
////                strSQLGP+=" ORDER BY cfinv.ne_ord ASC";
////                System.out.println("SQL cargarPresupuestoVentas: " + strSQLGP);
////                rstGP=stmGP.executeQuery(strSQLGP); 
////                //Limpiar vector de datos.
////                vecCab.clear();
////                vecAux.clear();
////                //Obtener los registros.
////                while (rstGP.next()){
////                    vecReg=new Vector();
////                    
////                    vecReg.add(INT_TBL_DAT_LIN,"");
////                    vecReg.add(INT_TBL_DAT_LINEA,rstGP.getString("Linea"));
////                    vecReg.add(INT_TBL_DAT_CLASE,rstGP.getString("clase"));
////                    vecReg.add(INT_TBL_DAT_PROM_DOL,rstGP.getDouble("nd_proMenVenNuevo"));
////                    vecReg.add(INT_TBL_DAT_PROM_KIL,rstGP.getDouble("nd_proMenVenPeso"));
////                    vecReg.add(INT_TBL_DAT_NE_ORDEN,rstGP.getInt("ne_ord"));
////                    vecCab.add(vecReg);
////                }
////                Collections.sort(vecCab, new Comparator<Vector>() {
////                    @Override
////                    public int compare(Vector v1, Vector v2) {
////                        Integer orden1 = (Integer) v1.get(INT_TBL_DAT_NE_ORDEN);
////                        Integer orden2 = (Integer) v2.get(INT_TBL_DAT_NE_ORDEN);
////                        return orden1.compareTo(orden2);
////                    }
////                });                   
////                vecAux = procesarDatosJerarquicamente(vecCab);
////                
////                // Ordenar por la columna ne_orden (índice INT_TBL_DAT_ORDEN)
////             
////                objTblMod.setData(vecAux);
////                tblDat.setModel(objTblMod);
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

public boolean cargarPresupuestoVentas(int tipo)
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

                strSQLGP="{call sp_ElecucionReporteImp_1(?,?)}";
                cal = conGP.prepareCall(strSQLGP); 
                cal.setInt(1,  1);// 2 si es con atributo o  1 si no tiene atributo
                cal.setInt(2,  tipo);// 2 si es con atributo o  1 si no tiene atributo
////                cal.registerOutParameter(2, java.sql.Types.NVARCHAR);
                boolean hasResults = cal.execute();    
                if (hasResults) 
                {
                    rstGP = cal.getResultSet();
                    vecCab.clear();
                    vecAux.clear();                    
                    while (rstGP.next()) 
                    {
                        vecReg=new Vector();

                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_LINEA,rstGP.getString("linea"));
                        vecReg.add(INT_TBL_DAT_CLASE,rstGP.getString("clase"));
                        vecReg.add(INT_TBL_DAT_PROM_DOL,rstGP.getDouble("promVtaDola"));
                        vecReg.add(INT_TBL_DAT_PROM_KIL,rstGP.getDouble("promVtaPeso"));
                        vecReg.add(INT_TBL_DAT_NE_ORDEN,rstGP.getInt("ne_ord"));
                        vecCab.add(vecReg);
                    }         
                    Collections.sort(vecCab, new Comparator<Vector>() {
                        @Override
                        public int compare(Vector v1, Vector v2) {
                            Integer orden1 = (Integer) v1.get(INT_TBL_DAT_NE_ORDEN);
                            Integer orden2 = (Integer) v2.get(INT_TBL_DAT_NE_ORDEN);
                            return orden1.compareTo(orden2);
                        }
                    });                   
                    vecAux = procesarDatosJerarquicamente(vecCab);                    
                }                
                // Ordenar por la columna ne_orden (índice INT_TBL_DAT_ORDEN)
             
                objTblMod.setData(vecAux);
                tblDat.setModel(objTblMod);
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


////
////private Vector procesarDatosJerarquicamente(Vector vecDatos) {
////    Vector vecDatosProcesados = new Vector();
////    String lineaActual = "";
////    for (int i = 0; i < vecDatos.size(); i++) {
////        Vector registro = (Vector) vecDatos.get(i);
////        String linea = (String) registro.get(INT_TBL_DAT_LINEA);
////        if (!linea.equals(lineaActual)) {
////            // Crear fila principal para la línea
////            Vector filaLinea = new Vector();
////            filaLinea.add(""); // Espacio para la jerarquía
////            filaLinea.add(linea); // Línea
////            filaLinea.add(""); // Clase vacío
////            filaLinea.add(""); // Prom. Valor vacío
////            filaLinea.add(""); // Prom. Kil vacío
////            vecDatosProcesados.add(filaLinea);
////            lineaActual = linea;
////        }
////        // Agregar registro de clase correspondiente
////        vecDatosProcesados.add(registro);
////    }
////    return vecDatosProcesados;
////}    
   

private Vector procesarDatosJerarquicamente(Vector vecDatos) {
    Vector vecDatosProcesados = new Vector();
    String lineaActual = "";
    double totalProMenVenNuevo = 0; // Total para nd_proMenVenNuevo
    double totalProMenVenPeso = 0;  // Total para nd_proMenVenPeso
    double totalOrden = 0;  // Total para nd_proMenVenPeso
    
    double totalPeso = 0; 
    double totalDolar = 0;  

    for (int i = 0; i < vecDatos.size(); i++) {
        Vector registro = (Vector) vecDatos.get(i);
        String linea = (String) registro.get(INT_TBL_DAT_LINEA);

        // Si se detecta un cambio de línea
        if (!linea.equals(lineaActual)) {
            if (!lineaActual.isEmpty()) {
                // Agregar la fila de TOTAL al finalizar el grupo actual
                Vector filaTotal = new Vector();
                filaTotal.add(""); // Indice
                filaTotal.add(""); // Espacio para jerarquía
                filaTotal.add("TOTAL"); // Etiqueta TOTAL
                filaTotal.add(String.format("%.2f", totalProMenVenNuevo)); // Total nd_proMenVenNuevo
                filaTotal.add(String.format("%.2f", totalProMenVenPeso));  // Total nd_proMenVenPeso
                filaTotal.add(String.format("%.2f", totalOrden));  // Total orden
                vecDatosProcesados.add(filaTotal);
                totalPeso = totalPeso +totalProMenVenPeso;
                totalDolar = totalDolar +totalProMenVenNuevo;                
            }

            // Reiniciar los totales para la nueva línea
            totalProMenVenNuevo = 0;
            totalProMenVenPeso = 0;

            // Crear fila principal para la nueva línea
            Vector filaLinea = new Vector();
            filaLinea.add(""); // Indice
            filaLinea.add(linea); // Línea
            filaLinea.add(""); // Clase vacío
            filaLinea.add(""); // Prom. Valor vacío
            filaLinea.add(""); // Prom. Kil vacío
            filaLinea.add(""); // Total orden
            vecDatosProcesados.add(filaLinea);
            lineaActual = linea;
        }

        // Acumular totales
        totalProMenVenNuevo += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_DOL).toString());
        totalProMenVenPeso += Double.parseDouble(registro.get(INT_TBL_DAT_PROM_KIL).toString());

        // Crear fila solo con clase y valores (sin repetir la línea)
        Vector filaClase = new Vector();
        filaClase.add(""); // Indice
        filaClase.add(""); // Espacio para jerarquía
        filaClase.add(registro.get(INT_TBL_DAT_CLASE)); // Clase
        filaClase.add(registro.get(INT_TBL_DAT_PROM_DOL)); // Prom. Valor
        filaClase.add(registro.get(INT_TBL_DAT_PROM_KIL)); // Prom. Kil
        filaClase.add(registro.get(INT_TBL_DAT_NE_ORDEN)); // Total orden
        vecDatosProcesados.add(filaClase);
 
    }

    // Agregar la fila de TOTAL para el último grupo
    if (!lineaActual.isEmpty()) {
        Vector filaTotal = new Vector();
        filaTotal.add(""); // Indice
        filaTotal.add(""); // Espacio para jerarquía
        filaTotal.add("TOTAL"); // Etiqueta TOTAL
        filaTotal.add(String.format("%.2f", totalProMenVenNuevo)); // Total nd_proMenVenNuevo
        filaTotal.add(String.format("%.2f", totalProMenVenPeso));  // Total nd_proMenVenPeso
        filaTotal.add(String.format("%.2f", totalOrden));  // Total orden
        vecDatosProcesados.add(filaTotal);
    }
    totalPeso = totalPeso +totalProMenVenPeso;
    totalDolar = totalDolar +totalProMenVenNuevo;
    
        Vector filaTotal = new Vector();
        filaTotal.add(""); // Indice
        filaTotal.add(""); // Espacio para jerarquía
        filaTotal.add("TOTAL GLOBAL"); // Etiqueta TOTAL
        filaTotal.add(String.format("%.2f", totalDolar)); // Total nd_proMenVenNuevo
        filaTotal.add(String.format("%.2f", totalPeso));  // Total nd_proMenVenPeso
        filaTotal.add(String.format("%.2f", totalOrden));  // Total orden
        vecDatosProcesados.add(filaTotal);
    
    

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
        cmb_TipoProm = new javax.swing.JComboBox<>();
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
        setTitle("Presupuesto de Ventas...");
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

        panTit.setLayout(new java.awt.GridLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Reporte de Presupuesto de Venta");
        lblTit.setToolTipText("");
        lblTit.setPreferredSize(new java.awt.Dimension(207, 25));
        panTit.add(lblTit);

        cmb_TipoProm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Promedio Actual", "Promedio Anterior", "Promedio Teorico" }));
        cmb_TipoProm.setToolTipText("");
        cmb_TipoProm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_TipoPromActionPerformed(evt);
            }
        });
        panTit.add(cmb_TipoProm);

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
        String directorio = "C:\\Zafiro\\Reportes\\Importaciones\\ZafImp16\\";
        String fechaFormateada = fechaHoraActual.format(formato);
        String nombreArchivo = "ZafImp16_" + fechaFormateada + ".xlsx";
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

    private void cmb_TipoPromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_TipoPromActionPerformed
        // TODO add your handling code here:
        if (cmb_TipoProm.getSelectedIndex()==0)
            cargarPresupuestoVentas(1);
        if (cmb_TipoProm.getSelectedIndex()==1)
            cargarPresupuestoVentas(2);
        if (cmb_TipoProm.getSelectedIndex()==2)
            cargarPresupuestoVentas(3);        

    }//GEN-LAST:event_cmb_TipoPromActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butExport;
    private javax.swing.JComboBox<String> cmb_TipoProm;
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


