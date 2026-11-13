/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafUtilGP;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sistemas4
 */
public class SqlConstants {
    public static final int INT_ARL_CON_SQL_TRES = 0;
    public static final int INT_ARL_CON_SQL_COD_GPGP = 1;
    public static final int INT_ARL_CON_SQL_NOMBRE = 2;
    public static final int INT_ARL_CON_SQL_COD_ZAF = 3;
    public static final int INT_ARL_CON_SQL_COST = 4;
    public static final int INT_ARL_CON_SQL_PREC = 5;
    public static final int INT_ARL_CON_SQL_FAC_COS = 6;
    public static final int INT_ARL_CON_SQL_PRO_ANU_VEN = 7;
    public static final int INT_ARL_CON_SQL_STK_BAS = 8;
    public static final int INT_ARL_CON_SQL_TRA = 9;
    public static final int INT_ARL_CON_SQL_PUN_PAR_MIN = 10;
    public static final int INT_ARL_CON_SQL_PUN_PAR_MAX = 11;
    public static final int INT_ARL_CON_SQL_MOQ = 12;
    public static final int INT_ARL_CON_SQL_EXC = 13;
    public static final int INT_ARL_CON_SQL_PRO_VEN_TEO = 14;
    public static final int INT_ARL_CON_SQL_MAR_UT = 15;
    public static final int INT_ARL_CON_SQL_MAX_CAN_VEN = 16;
    public static final int INT_ARL_CON_SQL_PES = 17;
    
    // Map para asociar constantes con valores
    private static final Map<Integer, Object> constantValues = new HashMap<>();   
    
    static {
        // Inicializar el mapa con valores para cada constante
        constantValues.put(INT_ARL_CON_SQL_TRES, "Valor TRES");
        constantValues.put(INT_ARL_CON_SQL_COD_GPGP, "Valor COD_GPGP");
        constantValues.put(INT_ARL_CON_SQL_NOMBRE, "Valor NOMBRE");
        constantValues.put(INT_ARL_CON_SQL_COD_ZAF, "Valor COD_ZAF");
        constantValues.put(INT_ARL_CON_SQL_COST, "Valor COST");
        constantValues.put(INT_ARL_CON_SQL_PREC, "Valor PREC");
        constantValues.put(INT_ARL_CON_SQL_FAC_COS, "Valor FAC_COS");
        constantValues.put(INT_ARL_CON_SQL_PRO_ANU_VEN, "Valor PRO_ANU_VEN");
        constantValues.put(INT_ARL_CON_SQL_STK_BAS, "Valor STK_BAS");
        constantValues.put(INT_ARL_CON_SQL_TRA, "Valor TRA");
        constantValues.put(INT_ARL_CON_SQL_PUN_PAR_MIN, "Valor PUN_PAR_MIN");
        constantValues.put(INT_ARL_CON_SQL_PUN_PAR_MAX, "Valor PUN_PAR_MAX");
        constantValues.put(INT_ARL_CON_SQL_MOQ, "Valor MOQ");
        constantValues.put(INT_ARL_CON_SQL_EXC, "Valor EXC");
        constantValues.put(INT_ARL_CON_SQL_PRO_VEN_TEO, "Valor PRO_VEN_TEO");
        constantValues.put(INT_ARL_CON_SQL_PES, "Valor PES");
    }    
    // Método para obtener el valor asociado a una constante
    public static Object getValue(int constant) {
        return constantValues.get(constant);
    }

    // Método para añadir o actualizar un valor en el mapa
    public static void putValue(int constant, Object value) {
        constantValues.put(constant, value);
    }    
        // Método para encerar (resetear) todos los valores del mapa
    public static void encerar(Object defaultValue) {
        for (Map.Entry<Integer, Object> entry : constantValues.entrySet()) {
            entry.setValue(defaultValue);
        }
    }
    // Método para actualizar valores null a -5
    public static void remplazarnulos() {
        for (Map.Entry<Integer, Object> entry : constantValues.entrySet()) {
            if (entry.getValue() == null) {
                entry.setValue(-5);
            }
        }
    }

    // Método para convertir todo el mapa en una cadena de texto
    public static String GenerarCadena() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Object> entry : constantValues.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("'").append(value).append("'");
            } else {
                sb.append(value);
            }
            sb.append("¬");
        }
        // Eliminar el último carácter "¬" si existe
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }    
}
