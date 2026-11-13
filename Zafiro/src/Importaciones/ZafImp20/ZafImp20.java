/*
 * ZafImp20.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Importaciones.ZafImp20;
////import Importaciones.ZafImp20.ZafImp20;
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
import Librerias.ZafWebSer.ZafWebSer; 
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.function.Function;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafImp20 extends javax.swing.JDialog 
{
    //Variables.
     private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafUtilGP objUtiGP;
    private ZafTblMod objTblMod;
    public String strObs;
    private boolean blnCon=false;                             //true: Continua la ejecucián del hilo.
    //Variables de la clase.
   
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_ITEMNMBR = 1;
    final int INT_TBL_DAT_ITMSHNAM = 2;
    final int INT_TBL_DAT_ITMGEDSC = 3;
    final int INT_TBL_DAT_UOMSCHDL = 4;
    final int INT_TBL_DAT_LINEA = 5;
    final int INT_TBL_DAT_CLASE = 6;
    final int INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO = 7;
    final int INT_TBL_DAT_ND_INV_SOLITO = 8;
    final int INT_TBL_DAT_ND_STOCK_BASE = 9;
    final int INT_TBL_DAT_ND_TRANSITO_INV = 10;
    final int INT_TBL_DAT_ND_MOQ = 11;
    final int INT_TBL_DAT_ND_FRE = 12;
    final int INT_TBL_DAT_ND_FRECUENCIA_CAL_SOLITO = 13;
    final int INT_TBL_DAT_ND_FRECUENCIA_FINAL_SOLITO = 14;
    final int INT_TBL_DAT_ND_PESO_SOLITO = 15;
    final int INT_TBL_DAT_ND_PRECIO_SOLITO = 16;
    final int INT_TBL_DAT_IMP_CANTIDAD = 17;
    final int INT_TBL_DAT_NE_ORD = 18;
    final int INT_TBL_DAT_ITEM_CAB_GRP = 19;
    final int INT_TBL_DAT_SUMATORIA = 20;
    final int INT_TBL_DAT_ND_COBMAX_MES = 21;
    final int INT_TBL_DAT_ND_COBMAX_UN = 22;
    final int INT_TBL_DAT_ND_COBMIN_UN = 23;
    final int INT_TBL_DAT_ND_INV_SOLITO_PESO = 24;
    final int INT_TBL_DAT_PEDGERIDO_SOLITO = 25;
    final int INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO_PESO = 26;
    final int INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO = 27;
    final int INT_TBL_DAT_ND_MOQ_PESO = 28;
    final int INT_TBL_DAT_IMP_CANTIDAD_PESO = 29;
    final int INT_TBL_DAT_ND_COBMAX_UN_PESO = 30;
    final int INT_TBL_DAT_ND_COBMIN_UN_PESO = 31;
    final int INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO_PESO = 32;
    final int INT_TBL_DAT_PEDGERIDO_SOLITO_PESO = 33;
    final int INT_TBL_DAT_PEDGERIDO_SOLITO_DOLAR = 34;
    final int INT_TBL_DAT_TX_TIP_ATR = 35;
    final int INT_TBL_DAT_VTA = 36;
    final int INT_TBL_DAT_PES= 37;
    final int INT_TBL_DAT_FRE = 38;            
    final int INT_TBL_DAT_TUV = 39;
    
    final int INT_TBL_DAT_CFR_FEC_PEN = 40;
    final int INT_TBL_DAT_CFR_PED_PEN = 41;
    final int INT_TBL_DAT_CFR_PRE_PEN = 42;    
    final int INT_TBL_DAT_CFR_FEC_ULT = 43;
    final int INT_TBL_DAT_CFR_PED_ULT = 44;
    final int INT_TBL_DAT_CFR_PRE_ULT = 45;     
    
//////    final int INT_TBL_DAT_EXI = 46;
//////    final int INT_TBL_DAT_IMP = 47;
//////    final int INT_TBL_DAT_TRA = 48;    
//////    final int INT_TBL_DAT_DIS = 49;
//////    final int INT_TBL_DAT_RES = 50;
//////    final int INT_TBL_DAT_TOT_DIS = 51;         
    
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
    private String strVersion ="0.4";
    private ZafThreadGUI objThrGUI;
 
    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafImp20(Frame parent, ZafParSis obj)
            //    public ZafCom113_02(Frame parent,  ZafParSis ZafParSis)
    {
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
            arlDat=new ArrayList();
            configurarFrm();
    }
    
    public ZafImp20(java.awt.Frame parent, boolean modal, ZafParSis obj)
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
    
   private class ZafThreadGUI extends Thread
    {
        public void run()
        {   
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                 tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
                //Ubicar el foco en la fila seleccionada.
                tblDat.changeSelection(0, 0, true, true);
            }
            objThrGUI=null;
        }
    }
   
   private boolean cargarReg()
   {
         // Configuración del tamaño del JDialog
         setSize(800,800);
         
         configurarTblDat();
         cargarPresupuestoVentas();
         obtenerNotasPedidosCFR();
         lblMsgSis.setText("Listo.");
         return true;
   }
       
       
    private void  configurarFrm()
    {
        blnCon=true;
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();                
        }
        

    } 
private String construirJsonItemsEnCadena() {
    StringBuilder sb = new StringBuilder();
    StringJoiner joiner = new StringJoiner(",");

    try {
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
            String item = objTblMod.getValueAt(i, INT_TBL_DAT_ITEMNMBR).toString();
            if (item != null && !item.trim().isEmpty() && !"0".equals(item.trim())) {
                joiner.add(item.trim());
            }
        }
    } catch (Exception e) {
        System.err.println("Error al construir JSON de ítems: " + e.getMessage());
        e.printStackTrace();
    }

    sb.append("{\"items\":\"")
      .append(joiner.toString())
      .append("\"}");

    return sb.toString();
}
    
    
            //blnCon=true;


    
        private ZafWebSer objWeb;
        
private HashMap<String, List<Map<String, Object>>> obtenerUltimosDosPorItem(String caditem) 
{
    HashMap<String, List<Map<String, Object>>> mapaResultados = new HashMap<>();

    try {
        // 🔹 Limpiar cadena (por si viene con espacios o punto y coma)
        String cadenaLimpia = caditem.trim()
                                     .replace(";", "")
                                     .replace("\"", "");

        // 🔹 Separar en ítems (por coma)
        String[] items = cadenaLimpia.split(",");

        // 🔹 Construir JSON manualmente
        StringBuilder sb = new StringBuilder("{\"items\": [");
        for (int i = 0; i < items.length; i++) {
            sb.append("\"").append(items[i].trim()).append("\"");
            if (i < items.length - 1) sb.append(", ");
        }
        sb.append("]}");

        // 🔹 JSON final que se enviará al API
        String bodyJson = sb.toString();

        // 🔹 Llamada al método del API
        String jsonStr = objWeb.obtenerConsultaPedidosJSON(caditem);

        if (jsonStr == null || jsonStr.isEmpty() || !jsonStr.startsWith("{")) {
            System.err.println("JSON vacío o inválido: " + jsonStr);
            return mapaResultados;
        }

        // 🔹 Parsear respuesta (NO el bodyJson)
        JSONObject jsonObj = new JSONObject(jsonStr);

        if (!jsonObj.has("data")) {
            System.err.println("No existe la clave 'data' en la respuesta JSON");
            return mapaResultados;
        }

        JSONArray pedidosArray = jsonObj.getJSONArray("data");

        // 🔹 Agrupar por item
        Map<String, List<JSONObject>> agrupadoPorItem = new HashMap<>();

        for (int i = 0; i < pedidosArray.length(); i++) {
            JSONObject pedido = pedidosArray.getJSONObject(i);
            String item = pedido.getString("item");
            agrupadoPorItem.computeIfAbsent(item, k -> new ArrayList<>()).add(pedido);
        }

        // 🔹 Ordenar por fecha descendente y tomar los 2 últimos
        for (Map.Entry<String, List<JSONObject>> entry : agrupadoPorItem.entrySet()) {
            String item = entry.getKey();
            List<JSONObject> listaPedidos = entry.getValue();

//            listaPedidos.sort((a, b) -> b.getString("dfeCreacion").compareTo(a.getString("dfeCreacion")));

                listaPedidos.sort((a, b) -> {
                    String fechaA = a.optString("dfeCreacion", "").trim();
                    String fechaB = b.optString("dfeCreacion", "").trim();

                    // Si ambos están vacíos → iguales
                    if (fechaA.isEmpty() && fechaB.isEmpty()) return 0;
                    // Si A está vacío → va al final
                    if (fechaA.isEmpty()) return 1;
                    // Si B está vacío → va al final
                    if (fechaB.isEmpty()) return -1;

                    // Comparar normalmente (orden descendente)
                    return fechaB.compareTo(fechaA);
                });        

            List<Map<String, Object>> ultimosDos = new ArrayList<>();
            
    for (int i = 0; i < Math.min(2, listaPedidos.size()); i++) {
    JSONObject ped = listaPedidos.get(i);
    Map<String, Object> datosCFR = new LinkedHashMap<>();

    // Función para validar texto nulo o vacío
    Function<String, String> getSafeString = (key) -> {
        String val = ped.optString(key, "").trim();
        return (val.isEmpty() || val.equalsIgnoreCase("null")) ? null : val;
    };

    // Función para convertir de forma segura a BigDecimal
    Function<String, BigDecimal> getSafeBigDecimal = (key) -> {
        String val = ped.optString(key, "").trim();
        if (val.isEmpty() || val.equalsIgnoreCase("null")) return BigDecimal.ZERO;
        try {
            return new BigDecimal(val);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Valor inválido en campo " + key + ": " + val);
            return BigDecimal.ZERO;
        }
    };

    datosCFR.put("pedido", getSafeString.apply("pedido"));
    datosCFR.put("item", getSafeString.apply("item"));
    datosCFR.put("costo", getSafeBigDecimal.apply("costo"));
    datosCFR.put("nd_valcfr", getSafeBigDecimal.apply("nd_valcfr"));
    datosCFR.put("nd_can", getSafeBigDecimal.apply("nd_can"));
    datosCFR.put("precioSug", getSafeBigDecimal.apply("precioSug"));
    datosCFR.put("dfeCreacion", getSafeString.apply("dfeCreacion"));
    datosCFR.put("ord", getSafeString.apply("ord"));

    ultimosDos.add(datosCFR);
}



            mapaResultados.put(item, ultimosDos);
        }
        System.out.println("⚠️ El ítem " + mapaResultados + " no tiene datos CFR.");

    } catch (Exception e) {
        System.err.println("Error al procesar JSON: " + e.getMessage());
        e.printStackTrace();
    }

    return mapaResultados;
}
        
// 🔸 Método para agrupar y obtener los dos últimos por ítem
////    private HashMap<String, List<Map<String, Object>>> obtenerUltimosDosPorItem(String caditem) 
////    {
////        HashMap<String, List<Map<String, Object>>> mapaResultados = new HashMap<>();
////
////        try 
////        {
////             
////            
////            // 🔹 Armar cuerpo JSON para el POST
////            //String bodyJson = "{\"items\": [\"680-000038-I\", \"680-000037-I\"]}";
////            String bodyJson = caditem;
////
////            // 🔹 Llamar al método que obtiene el JSON del API
////            String jsonStr = objWeb.obtenerConsultaPedidosJSON(bodyJson);
////
////            if (jsonStr == null || jsonStr.isEmpty() || !jsonStr.startsWith("{")) {
////                System.err.println("JSON vacío o inválido: " + jsonStr);
////                return mapaResultados;
////            }
////
////            // 🔹 Parsear JSON principal
////            JSONObject jsonObj = new JSONObject(jsonStr);
////
////            if (!jsonObj.has("consultaPedidosList")) {
////                System.err.println("No existe la clave 'consultaPedidosList' en la respuesta JSON");
////                return mapaResultados;
////            }
////
////            JSONArray pedidosArray = jsonObj.getJSONArray("consultaPedidosList");
////
////            // 🔹 Agrupar por item
////            Map<String, List<JSONObject>> agrupadoPorItem = new HashMap<>();
////            
////            
////Set<String> itemsVistos = new HashSet<>();
////
////for (int i = 0; i < pedidosArray.length(); i++) {
////    JSONObject pedido = pedidosArray.getJSONObject(i);
////    String rawItem = pedido.optString("item", "(sin item)");
////    if (itemsVistos.add(rawItem))
////        System.out.println("📦 Detectado item en JSON: [" + rawItem + "]");
////}
////System.out.println("🧩 Total items distintos en JSON: " + itemsVistos.size());
////
////
////            for (int i = 0; i < pedidosArray.length(); i++) {
////                JSONObject pedido = pedidosArray.getJSONObject(i);
////                String item = pedido.optString("item", "").trim()
////                    .replace("\u2011", "-")   // guión no estándar
////                    .replace("\u2010", "-")   // otro tipo de guión
////                    .toUpperCase();            // opcional: unifica mayúsculas
////
////                agrupadoPorItem.computeIfAbsent(item, k -> new ArrayList<>()).add(pedido);
////            }
////            
////System.out.println("🧮 Total items agrupados: " + agrupadoPorItem.size());   
////
////// 🔹 Mostrar resumen del agrupamiento
////System.out.println("===== 📦 RESUMEN DE AGRUPAMIENTO =====");
////System.out.println("🧩 Total items distintos en JSON: " + agrupadoPorItem.size());
////
////int totalPedidos = 0;
////for (Map.Entry<String, List<JSONObject>> entry : agrupadoPorItem.entrySet()) {
////    String item = entry.getKey();
////    int cantidad = entry.getValue().size();
////    totalPedidos += cantidad;
////
////    System.out.println("Item: " + item + " → " + cantidad + " registros");
////}
////
////System.out.println("📊 Total de registros sumados: " + totalPedidos);
////System.out.println("=======================================");
////
////
////
////for (String item : itemsVistos) {
////    if (!agrupadoPorItem.containsKey(item.trim()))
////        System.out.println("⚠️ Faltó en agrupadoPorItem: [" + item + "]");
////}
////
////            
////
////            // 🔹 Ordenar por fecha descendente y tomar los 2 últimos
////            for (Map.Entry<String, List<JSONObject>> entry : agrupadoPorItem.entrySet())  
////            {
////                String item = entry.getKey();
////                List<JSONObject> listaPedidos = entry.getValue();
////
////////                listaPedidos.sort((a, b) -> b.getString("fe_arr").compareTo(a.getString("fe_arr")));
////                
////                listaPedidos.sort((a, b) -> {
////                    String fechaA = a.optString("dfeCreacion", "").trim();
////                    String fechaB = b.optString("dfeCreacion", "").trim();
////
////                    // Si ambos están vacíos → iguales
////                    if (fechaA.isEmpty() && fechaB.isEmpty()) return 0;
////                    // Si A está vacío → va al final
////                    if (fechaA.isEmpty()) return 1;
////                    // Si B está vacío → va al final
////                    if (fechaB.isEmpty()) return -1;
////
////                    // Comparar normalmente (orden descendente)
////                    return fechaB.compareTo(fechaA);
////                });               
////                
////                            if (agrupadoPorItem.containsKey("018-000023-I") && item.equals("018-000023-I")) 
////                            // 🔹 Mostrar resultados
////                            {
////                            System.out.println("📦 pedidosArray length: " + pedidosArray.length());
////                            System.out.println("🧩 Ítems únicos en JSON: " + pedidosArray.length());  
////}                
////
////                List<Map<String, Object>> ultimosDos = new ArrayList<>();
////                for (int i = 0; i < Math.min(2, listaPedidos.size()); i++) {
////                    JSONObject ped = listaPedidos.get(i);
////
////                    Map<String, Object> datosCFR = new LinkedHashMap<>();
////                    datosCFR.put("pedido", ped.optString("pedido"));
////                    datosCFR.put("item", ped.optString("item"));
////                    if (ped.optString("item").compareTo("018-000023-I")==0)
////                         System.out.println("🧩 Ítems únicos en JSON: " + pedidosArray.length());  
////                    datosCFR.put("costo", new BigDecimal(ped.optString("costo", "0")));
////                    datosCFR.put("nd_valcfr", new BigDecimal(ped.optString("nd_valcfr", "0")));
////                    datosCFR.put("nd_can", new BigDecimal(ped.optString("nd_can", "0")));
////                    datosCFR.put("precioSug", new BigDecimal(ped.optString("precioSug", "0")));
////                    datosCFR.put("dfeCreacion", ped.optString("dfeCreacion"));
////                    datosCFR.put("ord", ped.optString("ord"));
////
////                    ultimosDos.add(datosCFR);
////                }
////
////                mapaResultados.put(item, ultimosDos);
////            }
////            
////System.out.println("pedidosArray.length(): " + pedidosArray.length());
////System.out.println("agrupadoPorItem.size(): " + agrupadoPorItem.size());
////System.out.println("mapaResultados.size(): " + mapaResultados.size());
////
////int conUno = 0;
////int conDos = 0;
////for (List<Map<String, Object>> lista : mapaResultados.values()) {
////    if (lista.size() == 1) conUno++;
////    else if (lista.size() == 2) conDos++;
////}
////System.out.println("→ Ítems con 1 pedido: " + conUno);
////System.out.println("→ Ítems con 2 pedidos: " + conDos);            
////
////        } catch (Exception e) {
////            System.err.println("Error al procesar JSON: " + e.getMessage());
////            e.printStackTrace();
////        }
////        
////
////
////
////
////
////        return mapaResultados;
////    }    

        
   
    private String construirJsonItemsDesdeTabla() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"items\": [");
        int contador = 0;
        try {
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) 
                {
                    // Obtiene el valor usando tu función existente
                    String item = objTblMod.getValueAt(i, INT_TBL_DAT_ITEMNMBR).toString();

                    // Solo agrega si no está vacío o es "0"
                    if (!item.isEmpty() && !item.equals("0")) {
                        if (contador > 0) {
                            sb.append(", ");
                        }
                        sb.append("\"").append(item).append("\"");
                        contador++;
                    }
                }
        } catch (Exception e) {
            System.err.println("Error al construir JSON de ítems: " + e.getMessage());
            e.printStackTrace();
        }

        sb.append("]}");
        return sb.toString();
}
        
private boolean obtenerNotasPedidosCFR() {
    boolean blnRes = false;
    String str_caditm = construirJsonItemsEnCadena();
    int cont=0;
    lblMsgSis.setText("Obteniendo NotasPedidos...");

    // 🔹 Obtener los datos desde el API
    HashMap<String, List<Map<String, Object>>> datosCFR = obtenerUltimosDosPorItem(str_caditm);

    try 
    {
        if (datosCFR == null || datosCFR.isEmpty()) {
            System.out.println("⚠️ No se recibieron datos del API.");
            return false;
        }

        // 🔹 Recorrer las filas de la tabla
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) 
        {
            // Aquí obtienes el ítem de la tabla (ajusta el índice de columna si es otro)
            String itemTabla = (objTblMod.getValueAt(i, INT_TBL_DAT_ITEMNMBR) == null)
                    ? ""
                    : objTblMod.getValueAt(i, INT_TBL_DAT_ITEMNMBR).toString().trim();

            // Validar si ese ítem existe en los datos del API
            if (datosCFR.containsKey(itemTabla)) 
            {
                List<Map<String, Object>> pedidos = datosCFR.get(itemTabla);
                cont=pedidos.size();
                
                if (pedidos != null && !pedidos.isEmpty()) 
                {
                    if (cont==1)
                    {
                        Map<String, Object> pedUltimo = pedidos.get(0);

                        // 🔹 Función auxiliar para obtener texto seguro
                        Function<String, String> getSafeStringFromMap = (key) -> {
                            Object val = pedUltimo.get(key);
                            if (val == null) return "";
                            String strVal = val.toString().trim();
                            return (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) ? "" : strVal;
                        };

                        // 🔹 Función auxiliar para obtener número seguro
                        Function<String, Double> getSafeDoubleFromMap = (key) -> {
                            Object val = pedUltimo.get(key);
                            if (val == null) return 0.0;
                            String strVal = val.toString().trim();
                            if (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) return 0.0;
                            try {
                                return Double.parseDouble(strVal);
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️ Valor inválido en campo " + key + ": " + strVal);
                                return 0.0;
                            }
                        };

                        // 🔹 Usar las funciones para asignar valores a la tabla
                        objTblMod.setValueAt(getSafeStringFromMap.apply("dfeCreacion"), i, INT_TBL_DAT_CFR_FEC_ULT);
                        objTblMod.setValueAt(getSafeStringFromMap.apply("pedido"), i, INT_TBL_DAT_CFR_PED_ULT);
                        objTblMod.setValueAt(getSafeDoubleFromMap.apply("nd_valcfr"), i, INT_TBL_DAT_CFR_PRE_ULT);
                        
                    }  
                    else
                    {
                        



                        
                        // El primer registro es el más reciente
                        Map<String, Object> pedReciente = pedidos.get(1); 
                        
                        // 🔹 Función auxiliar para obtener texto seguro
                        Function<String, String> getSafeStringFromMapPed = (key) -> {
                            Object val = pedReciente.get(key);
                            if (val == null) return "";
                            String strVal = val.toString().trim();
                            return (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) ? "" : strVal;
                        };

                        // 🔹 Función auxiliar para obtener número seguro
                        Function<String, Double> getSafeDoubleFromMapPed = (key) -> {
                            Object val = pedReciente.get(key);
                            if (val == null) return 0.0;
                            String strVal = val.toString().trim();
                            if (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) return 0.0;
                            try {
                                return Double.parseDouble(strVal);
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️ Valor inválido en campo " + key + ": " + strVal);
                                return 0.0;
                            }
                        };
                        
                        // 🔹 Usar las funciones para asignar valores a la tabla
                        objTblMod.setValueAt(getSafeStringFromMapPed.apply("dfeCreacion"), i, INT_TBL_DAT_CFR_FEC_PEN);
                        objTblMod.setValueAt(getSafeStringFromMapPed.apply("pedido"), i, INT_TBL_DAT_CFR_PED_PEN);
                        objTblMod.setValueAt(getSafeDoubleFromMapPed.apply("nd_valcfr"), i, INT_TBL_DAT_CFR_PRE_PEN);                         
                        
////                        objTblMod.setValueAt(pedReciente.get("dfeCreacion").toString() , i, INT_TBL_DAT_CFR_FEC_PEN);
////                        objTblMod.setValueAt(pedReciente.get("pedido").toString() , i, INT_TBL_DAT_CFR_PED_PEN);
////                        objTblMod.setValueAt(Double.parseDouble(pedReciente.get("nd_valcfr").toString()) , i, INT_TBL_DAT_CFR_PRE_PEN);

                        Map<String, Object> pedUltimo = pedidos.get(0);
                        
                        // 🔹 Función auxiliar para obtener texto seguro
                        Function<String, String> getSafeStringFromMap = (key) -> {
                            Object val = pedUltimo.get(key);
                            if (val == null) return "";
                            String strVal = val.toString().trim();
                            return (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) ? "" : strVal;
                        };

                        // 🔹 Función auxiliar para obtener número seguro
                        Function<String, Double> getSafeDoubleFromMap = (key) -> {
                            Object val = pedUltimo.get(key);
                            if (val == null) return 0.0;
                            String strVal = val.toString().trim();
                            if (strVal.isEmpty() || strVal.equalsIgnoreCase("null")) return 0.0;
                            try {
                                return Double.parseDouble(strVal);
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️ Valor inválido en campo " + key + ": " + strVal);
                                return 0.0;
                            }
                        };
                        
                        // 🔹 Usar las funciones para asignar valores a la tabla
                        objTblMod.setValueAt(getSafeStringFromMap.apply("dfeCreacion"), i, INT_TBL_DAT_CFR_FEC_ULT);
                        objTblMod.setValueAt(getSafeStringFromMap.apply("pedido"), i, INT_TBL_DAT_CFR_PED_ULT);
                        objTblMod.setValueAt(getSafeDoubleFromMap.apply("nd_valcfr"), i, INT_TBL_DAT_CFR_PRE_ULT);                        
                        
////                        objTblMod.setValueAt(pedUltimo.get("dfeCreacion").toString() , i, INT_TBL_DAT_CFR_FEC_ULT);
////                        objTblMod.setValueAt(pedUltimo.get("pedido").toString() , i, INT_TBL_DAT_CFR_PED_ULT);
////                        objTblMod.setValueAt(Double.parseDouble(pedUltimo.get("nd_valcfr").toString()) , i, INT_TBL_DAT_CFR_PRE_ULT);                        
                    }  
                }
            } 
            else 
                System.out.println("⚠️ El ítem " + itemTabla + " no tiene datos CFR.");
        }
        blnRes = true;
    } 
    catch (Exception e) 
    {
        System.err.println("Error al obtener notas de pedidos CFR: " + e.getMessage());
        e.printStackTrace();
        blnRes = false;
    }
    return blnRes;
}        
    

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_ITEMNMBR:
                    strMsg = "Número de Ítem";
                    break;
                case INT_TBL_DAT_ITMSHNAM:
                    strMsg = "Nombre Corto";
                    break;
                case INT_TBL_DAT_ITMGEDSC: 
                    strMsg = "Descripción";
                    break;
                case INT_TBL_DAT_UOMSCHDL:
                    strMsg = "Unidad de Medida";
                    break;
                case INT_TBL_DAT_LINEA:
                    strMsg = "Compra";
                    break;
                case INT_TBL_DAT_CLASE:
                    strMsg = "Clase Compra";
                    break;
                case INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO:
                    strMsg = "Prom. Anual Venta";
                    break;
                case INT_TBL_DAT_ND_INV_SOLITO:
                    strMsg = "Inventario";
                    break;
                case INT_TBL_DAT_ND_STOCK_BASE:
                    strMsg = "Stock Base";
                    break;
                case INT_TBL_DAT_ND_TRANSITO_INV:
                    strMsg = "Transito Inventario";
                    break;
                case INT_TBL_DAT_ND_MOQ:
                    strMsg = "MOQ";
                    break;
                case INT_TBL_DAT_ND_FRE:
                    strMsg = "Frecuencia";
                    break;
                case INT_TBL_DAT_ND_FRECUENCIA_CAL_SOLITO:
                    strMsg = "Frecuencia Calculada";
                    break;
                case INT_TBL_DAT_ND_FRECUENCIA_FINAL_SOLITO:
                    strMsg = "Frecuencia Final";
                    break;
                case INT_TBL_DAT_ND_PESO_SOLITO:
                    strMsg = "Peso";
                    break;
                case INT_TBL_DAT_ND_PRECIO_SOLITO:
                    strMsg = "Precio";
                    break;
                case INT_TBL_DAT_IMP_CANTIDAD:
                    strMsg = "Cantidad Importada";
                    break;
                case INT_TBL_DAT_NE_ORD:
                    strMsg = "Orden";
                    break;
                case INT_TBL_DAT_ITEM_CAB_GRP:
                    strMsg = "Grupo";
                    break;
                case INT_TBL_DAT_SUMATORIA:
                    strMsg = "Sumatoria";
                    break;
                case INT_TBL_DAT_ND_COBMAX_MES:
                    strMsg = "Cobertura Máx. Mes";
                    break;
                case INT_TBL_DAT_ND_COBMAX_UN:
                    strMsg = "Cobertura Máx. Unidad";
                    break;
                case INT_TBL_DAT_ND_COBMIN_UN:
                    strMsg = "Cobertura Mín. Unidad";
                    break;
                case INT_TBL_DAT_ND_INV_SOLITO_PESO:
                    strMsg = "Inventario Peso";
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
                vecCab.add("ItemGP");
                vecCab.add("ItemZa");
                vecCab.add("TresL");
                vecCab.add("Unidades");
                vecCab.add("Clase");
                vecCab.add("SubClase");
                vecCab.add("Prom.Vts.Presupuesto(UN)");
                vecCab.add("Existencia(UN)");
                vecCab.add("StockSeguridad");
                vecCab.add("LeadTime");
                vecCab.add("MOQ(UN)");
                vecCab.add("Fre.Lin."); 
                vecCab.add("Fre.Cal.Itm");
                vecCab.add("Fre.Final");
                vecCab.add("Peso");
                vecCab.add("Precio");
                vecCab.add("Importaciones(UN)");
                vecCab.add("Orden");
                vecCab.add("Item.Cabecera");
                vecCab.add("SS.LT.FC");
                vecCab.add("CobMaxMes");
                vecCab.add("CobMaxUn(UN)");
                vecCab.add("CobMinUn(UN)");
                vecCab.add("Existencia(KG)");
                vecCab.add("Ped.Sug.(UN)");
                vecCab.add("Prom.Vts.Presupuesto(KG)");
                vecCab.add("Inv.Total(UN)");
                vecCab.add("Moq(KG)");
                vecCab.add("Importaciones(KG)");
                vecCab.add("CobMaxUn(KG)");
                vecCab.add("CobMinUn(KG)");
                vecCab.add("Inv.Total(KG)");
                vecCab.add("Ped.Sug.(KG)");
                vecCab.add("Ped.Sug.($)");
                vecCab.add("tx_tipAtr");
                vecCab.add("Cat.Venta");
                vecCab.add("Cat.Peso");
                vecCab.add("Cat.Fre.");
                vecCab.add("Cat.Tuval"); 
                
                vecCab.add("Pen.Fecha");
                vecCab.add("Pen.Pedido");
                vecCab.add("Pre.Precio"); 
                vecCab.add("Ult.Fecha");
                vecCab.add("Ult.Pedido");
                vecCab.add("Ult.Precio"); 
                
//////                vecCab.add("Existencia");
//////                vecCab.add("Importaciones");
//////                vecCab.add("Transito"); 
//////                vecCab.add("Disponible");
//////                vecCab.add("Reserva");
//////                vecCab.add("Disp.Total");                
                
                
                
                vecAux = new Vector();
                vecAux.clear();
                vecAux.add(""); // Columna adicional para jerarquía
                vecAux.add("ITEMNMBR");
                vecAux.add("ITMSHNAM");
                vecAux.add("ITMGEDSC");
                vecAux.add("UOMSCHDL");
                vecAux.add("Linea");
                vecAux.add("Clase");
                vecAux.add("nd_proAnuVenManSolito");
                vecAux.add("nd_InvSolito");
                vecAux.add("nd_StockBase");
                vecAux.add("nd_TransitoInv");
                vecAux.add("nd_MOQ");
                vecAux.add("nd_fre");
                vecAux.add("nd_frecuenciaCalSolito");
                vecAux.add("nd_frecuenciaFinalSolito");
                vecAux.add("nd_pesoSolito");
                vecAux.add("nd_precioSolito");
                vecAux.add("impCantidad");
                vecAux.add("ne_ord");
                vecAux.add("itemCabGrp");
                vecAux.add("sumatoria");
                vecAux.add("nd_cobmaxMes");
                vecAux.add("nd_cobMaxUn");
                vecAux.add("nd_cobMinUn");
                vecAux.add("nd_InvSolitoPeso");
                vecAux.add("pedgeridoSolito");
                vecAux.add("nd_proAnuVenManSolitoPESO");
                vecAux.add("inventarioTOTALSOLITO");
                vecAux.add("nd_MOQPESO");
                vecAux.add("impCantidadPESO");
                vecAux.add("nd_cobMaxUnPESO");
                vecAux.add("nd_cobMinUnPESO");
                vecAux.add("inventarioTOTALSolitoPESO");
                vecAux.add("pedgeridoSolitoPESO");
                vecAux.add("pedgeridoSolitoDOLAR");
                vecAux.add("tx_tipAtr");
                vecAux.add("catven");
                vecAux.add("catpes");
                vecAux.add("catfre");
                vecAux.add("cattuv");

                vecAux.add("Pen.Fecha");
                vecAux.add("Pen.Pedido");
                vecAux.add("Pre.Precio"); 
                vecAux.add("Ult.Fecha");
                vecAux.add("Ult.Pedido");
                vecAux.add("Ult.Precio");    
                
//////                vecAux.add("existencia");
//////                vecAux.add("import");
//////                vecAux.add("transito"); 
//////                vecAux.add("disponble");
//////                vecAux.add("reservado");
//////                vecAux.add("totaldispo");   


                objTblMod = new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);
                objWeb = new ZafWebSer(objParSis);

                // Configuración del renderizador
                TableColumnModel tcmAux = tblDat.getColumnModel();
                tcmAux.getColumn(INT_TBL_DAT_ITEMNMBR).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ITMSHNAM).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ITMGEDSC).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_UOMSCHDL).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_CLASE).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_INV_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_STOCK_BASE).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_TRANSITO_INV).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_MOQ).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_FRE).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_FRECUENCIA_CAL_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_FRECUENCIA_FINAL_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_PESO_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_PRECIO_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_IMP_CANTIDAD).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_NE_ORD).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ITEM_CAB_GRP).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_SUMATORIA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_COBMAX_MES).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_COBMAX_UN).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_COBMIN_UN).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_INV_SOLITO_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PEDGERIDO_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_MOQ_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_IMP_CANTIDAD_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_COBMAX_UN_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_ND_COBMIN_UN_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PEDGERIDO_SOLITO_PESO).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PEDGERIDO_SOLITO_DOLAR).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_TX_TIP_ATR).setCellRenderer(new JerarquiaRenderer());
                
                tcmAux.getColumn(INT_TBL_DAT_VTA).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_FRE).setCellRenderer(new JerarquiaRenderer());
                tcmAux.getColumn(INT_TBL_DAT_TUV).setCellRenderer(new JerarquiaRenderer());                
                
                tcmAux.getColumn(INT_TBL_DAT_CFR_FEC_PEN).setCellRenderer(new JerarquiaRenderer());   
                tcmAux.getColumn(INT_TBL_DAT_CFR_PED_PEN).setCellRenderer(new JerarquiaRenderer());   
                tcmAux.getColumn(INT_TBL_DAT_CFR_PRE_PEN).setCellRenderer(new JerarquiaRenderer());  
                tcmAux.getColumn(INT_TBL_DAT_CFR_FEC_ULT).setCellRenderer(new JerarquiaRenderer());   
                tcmAux.getColumn(INT_TBL_DAT_CFR_PED_ULT).setCellRenderer(new JerarquiaRenderer());   
                tcmAux.getColumn(INT_TBL_DAT_CFR_PRE_ULT).setCellRenderer(new JerarquiaRenderer());   
                
//////                tcmAux.getColumn(INT_TBL_DAT_EXI).setCellRenderer(new JerarquiaRenderer());   
//////                tcmAux.getColumn(INT_TBL_DAT_IMP).setCellRenderer(new JerarquiaRenderer());   
//////                tcmAux.getColumn(INT_TBL_DAT_TRA).setCellRenderer(new JerarquiaRenderer());  
//////                tcmAux.getColumn(INT_TBL_DAT_DIS).setCellRenderer(new JerarquiaRenderer());   
//////                tcmAux.getColumn(INT_TBL_DAT_RES).setCellRenderer(new JerarquiaRenderer());   
//////                tcmAux.getColumn(INT_TBL_DAT_TOT_DIS).setCellRenderer(new JerarquiaRenderer());                 
                
                

                // Configurar anchura de las columnas
                tblDat.getColumnModel().getColumn(0).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(2).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(3).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(4).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(5).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(6).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(7).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(8).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(9).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(10).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(11).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(12).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(13).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(14).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(15).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(16).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(17).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(18).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(19).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(20).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(21).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(22).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(23).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(24).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(25).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(26).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(27).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(28).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(29).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(30).setPreferredWidth(120);
                tblDat.getColumnModel().getColumn(31).setPreferredWidth(80);
                tblDat.getColumnModel().getColumn(32).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(33).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(34).setPreferredWidth(30);                
                tblDat.getColumnModel().getColumn(35).setPreferredWidth(30);  
                tblDat.getColumnModel().getColumn(36).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(37).setPreferredWidth(100);
                tblDat.getColumnModel().getColumn(38).setPreferredWidth(30);                
                tblDat.getColumnModel().getColumn(39).setPreferredWidth(30);  
                
                tblDat.getColumnModel().getColumn(40).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(41).setPreferredWidth(30);                
                tblDat.getColumnModel().getColumn(42).setPreferredWidth(30);  
                tblDat.getColumnModel().getColumn(43).setPreferredWidth(30);
                tblDat.getColumnModel().getColumn(44).setPreferredWidth(30);                
                tblDat.getColumnModel().getColumn(45).setPreferredWidth(30);                  
                
                
////////                tblDat.getColumnModel().getColumn(46).setPreferredWidth(30);
////////                tblDat.getColumnModel().getColumn(47).setPreferredWidth(30);                
////////                tblDat.getColumnModel().getColumn(48).setPreferredWidth(30);  
////////                tblDat.getColumnModel().getColumn(49).setPreferredWidth(30);
////////                tblDat.getColumnModel().getColumn(50).setPreferredWidth(30);                
////////                tblDat.getColumnModel().getColumn(51).setPreferredWidth(30);  
                
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
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
////            if (column == INT_TBL_DAT_PROM_DOL || column == INT_TBL_DAT_PROM_KIL) {
////                setHorizontalAlignment(SwingConstants.RIGHT);
////            } else {
////                setHorizontalAlignment(SwingConstants.LEFT); // Alineación por defecto
////            }        
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

public boolean cargarPresupuestoVentas()
    {
        boolean blnRes=true;
        int intCodRegArl=-1;
        int intCodRegDb=-1; 
        String strEstPedEli="";
        java.sql.CallableStatement cal; 
        try{
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null){
                lblMsgSis.setText("Obteniendo datos...");
                pgrSis.setValue(0);
                stmGP=conGP.createStatement();
                
                //Armar la sentencia SQL.
                strSQLGP="";

                strSQLGP="{call spImp07_pr1 }";
                cal = conGP.prepareCall(strSQLGP);
                cal.execute();
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
                        vecReg.add(INT_TBL_DAT_ITEMNMBR, rstGP.getString("itemNmbr"));
                        vecReg.add(INT_TBL_DAT_ITMSHNAM, rstGP.getString("itmShNam"));
                        vecReg.add(INT_TBL_DAT_ITMGEDSC, rstGP.getString("itmGeDsc"));
                        vecReg.add(INT_TBL_DAT_UOMSCHDL, rstGP.getString("uomSchdl"));
                        vecReg.add(INT_TBL_DAT_LINEA, rstGP.getString("linea"));
                        vecReg.add(INT_TBL_DAT_CLASE, rstGP.getString("clase"));
                        vecReg.add(INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO, rstGP.getDouble("nd_ProAnuVenManSolito"));
                        vecReg.add(INT_TBL_DAT_ND_INV_SOLITO, rstGP.getDouble("nd_InvSolito"));
                        vecReg.add(INT_TBL_DAT_ND_STOCK_BASE, rstGP.getDouble("nd_StockBase"));
                        vecReg.add(INT_TBL_DAT_ND_TRANSITO_INV, rstGP.getDouble("nd_TransitoInv"));
                        vecReg.add(INT_TBL_DAT_ND_MOQ, rstGP.getDouble("nd_MOQ"));
                        vecReg.add(INT_TBL_DAT_ND_FRE, rstGP.getDouble("nd_Fre"));
                        vecReg.add(INT_TBL_DAT_ND_FRECUENCIA_CAL_SOLITO, rstGP.getDouble("nd_FrecuenciaCalSolito"));
                        vecReg.add(INT_TBL_DAT_ND_FRECUENCIA_FINAL_SOLITO, rstGP.getDouble("nd_FrecuenciaFinalSolito"));
                        vecReg.add(INT_TBL_DAT_ND_PESO_SOLITO, rstGP.getDouble("nd_PesoSolito"));
                        vecReg.add(INT_TBL_DAT_ND_PRECIO_SOLITO, rstGP.getDouble("nd_PrecioSolito"));
                        vecReg.add(INT_TBL_DAT_IMP_CANTIDAD, rstGP.getDouble("impCantidad"));
                        vecReg.add(INT_TBL_DAT_NE_ORD, rstGP.getInt("ne_Ord"));
                        vecReg.add(INT_TBL_DAT_ITEM_CAB_GRP, rstGP.getString("itemCabGrp"));
                        vecReg.add(INT_TBL_DAT_SUMATORIA, rstGP.getDouble("sumatoria"));
                        vecReg.add(INT_TBL_DAT_ND_COBMAX_MES, rstGP.getDouble("nd_CobMaxMes"));
                        vecReg.add(INT_TBL_DAT_ND_COBMAX_UN, rstGP.getDouble("nd_CobMaxUn"));
                        vecReg.add(INT_TBL_DAT_ND_COBMIN_UN, rstGP.getDouble("nd_CobMinUn"));
                        vecReg.add(INT_TBL_DAT_ND_INV_SOLITO_PESO, rstGP.getDouble("nd_InvSolitoPeso"));
                        vecReg.add(INT_TBL_DAT_PEDGERIDO_SOLITO, rstGP.getDouble("pedGeridoSolito"));
                        vecReg.add(INT_TBL_DAT_ND_PRO_ANU_VEN_MAN_SOLITO_PESO, rstGP.getDouble("nd_ProAnuVenManSolitoPeso"));
                        vecReg.add(INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO, rstGP.getDouble("inventarioTotalSolito"));
                        vecReg.add(INT_TBL_DAT_ND_MOQ_PESO, rstGP.getDouble("nd_MOQPeso"));
                        vecReg.add(INT_TBL_DAT_IMP_CANTIDAD_PESO, rstGP.getDouble("impCantidadPeso"));
                        vecReg.add(INT_TBL_DAT_ND_COBMAX_UN_PESO, rstGP.getDouble("nd_CobMaxUnPeso"));
                        vecReg.add(INT_TBL_DAT_ND_COBMIN_UN_PESO, rstGP.getDouble("nd_CobMinUnPeso"));
                        vecReg.add(INT_TBL_DAT_INVENTARIO_TOTAL_SOLITO_PESO, rstGP.getDouble("inventarioTotalSolitoPeso"));
                        vecReg.add(INT_TBL_DAT_PEDGERIDO_SOLITO_PESO, rstGP.getDouble("pedGeridoSolitoPeso"));
                        vecReg.add(INT_TBL_DAT_PEDGERIDO_SOLITO_DOLAR, rstGP.getDouble("pedGeridoSolitoDolar"));
                        vecReg.add(INT_TBL_DAT_TX_TIP_ATR, rstGP.getString("txTipAtr"));
                        vecReg.add(INT_TBL_DAT_VTA, rstGP.getString("catven"));
                        vecReg.add(INT_TBL_DAT_PES, rstGP.getString("catpes"));
                        vecReg.add(INT_TBL_DAT_FRE, rstGP.getString("catfre"));
                        vecReg.add(INT_TBL_DAT_TUV, rstGP.getString("cattuv")); 
                        
                        vecReg.add(INT_TBL_DAT_CFR_FEC_PEN, ""); 
                        vecReg.add(INT_TBL_DAT_CFR_PED_PEN, "");
                        vecReg.add(INT_TBL_DAT_CFR_PRE_PEN, 0);
                        vecReg.add(INT_TBL_DAT_CFR_FEC_ULT, "");
                        vecReg.add(INT_TBL_DAT_CFR_PED_ULT, "");
                        vecReg.add(INT_TBL_DAT_CFR_PRE_ULT, 0);  

////////                        vecReg.add(INT_TBL_DAT_EXI, rstGP.getString("existencia"));
////////                        vecReg.add(INT_TBL_DAT_IMP, rstGP.getString("import"));
////////                        vecReg.add(INT_TBL_DAT_TRA, rstGP.getString("transito"));
////////                        vecReg.add(INT_TBL_DAT_DIS, rstGP.getString("disponble"));
////////                        vecReg.add(INT_TBL_DAT_RES, rstGP.getString("reservado")); 
////////                        vecReg.add(INT_TBL_DAT_TOT_DIS, rstGP.getString("totaldispo")); 

                        vecCab.add(vecReg);
                    }         
////////////                    Collections.sort(vecCab, new Comparator<Vector>() {
////////////                        @Override
////////////                        public int compare(Vector v1, Vector v2) {
////////////                            Integer orden1 = (Integer) v1.get(INT_TBL_DAT_NE_ORDEN);
////////////                            Integer orden2 = (Integer) v2.get(INT_TBL_DAT_NE_ORDEN);
////////////                            return orden1.compareTo(orden2);
////////////                        }
////////////                    });                   
////////////                    vecAux = procesarDatosJerarquicamente(vecCab);                    
                }                
                // Ordenar por la columna ne_orden (índice INT_TBL_DAT_ORDEN)
             
                objTblMod.setData(vecCab);
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
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        panTit.setLayout(new java.awt.GridLayout(1, 0));

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Reporte Detallado de Configuracion de Parametros Formato 2");
        lblTit.setToolTipText("");
        lblTit.setPreferredSize(new java.awt.Dimension(207, 25));
        panTit.add(lblTit);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 16));
        panBarEst.setRequestFocusEnabled(false);
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
        String directorio = "C:\\Zafiro\\Reportes\\Importaciones\\ZafImp20\\";
        String fechaFormateada = fechaHoraActual.format(formato);
        String nombreArchivo = "ZafImp20_" + fechaFormateada + ".xlsx";
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
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGen1;
    private javax.swing.JPanel panPrgSis;
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


