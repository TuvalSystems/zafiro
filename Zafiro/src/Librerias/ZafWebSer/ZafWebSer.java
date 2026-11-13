/*
 * ZafWebSer.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.59
 */ 

package Librerias.ZafWebSer;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtil.ZafUtil;
import java.io.BufferedReader;
import java.io.File; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets; 
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
  

/**
 * El fin de la creación de esta clase es el de ubicar en ella todas aquellas operaciones
 * que se harían de uso común para el sistema. Esta clase contendrá tantos mï¿½todos como
 * operaciones vaya a realizar. Por ejemplo, como ejemplo de operaciones comunes tenemos
 * la opción de desactivar los componentes en el momento que se realiza una consulta para
 * que no se haga modificaciones a los datos que se presentan; otra operación es la de
 * añadir o eliminar filas de un JTable, que se usará cada vez que vayamos a crear una 
 * factura o una cotización.
 * 
 * @since 25/Ago/2004
 */
public class ZafWebSer
{
   
    static java.awt.Color colComDes;
    //Constantes: Columnas utilizadas por el proceso de recosteo:
    private static final int INT_REC_COD_EMP=0;                    //Código de la empresa.
    private static final int INT_REC_COD_LOC=1;                    //Código del local.
    private static final int INT_REC_COD_TIP_DOC=2;                //Código del tipo de documento.
    private static final int INT_REC_COD_DOC=3;                    //Código del documento.
    private static final int INT_REC_COD_REG=4;                    //Código del registro.
    private static final int INT_REC_EST_CUC=5;                    //Estado de costo unitario calculado.
    private static final int INT_REC_NAT_DOC=6;                    //Naturaleza del documento.
    private static final int INT_REC_EST_DOC=7;                    //Estado del documento.
    private static final int INT_REC_CAN=8;                        //Cantidad.
    private static final int INT_REC_CAN_DEV=9;                    //Cantidad.
    private static final int INT_REC_COS_UNI=10;                   //Costo unitario.
    private static final int INT_REC_POR_DES=11;                   //Porcentaje de descuento.
    private static final int INT_REC_COS_TOT=12;                   //Costo total.
    private static final int INT_REC_SAL_UNI=13;                   //Saldo en unidades.
    private static final int INT_REC_SAL_VAL=14;                   //Saldo en valores.
    private static final int INT_REC_COS_PRO=15;                   //Costo promedio.
    private ZafUtil objUti;   
    private static final int INT_ARL_CON_TRESL=0;     
    
    private static final int INT_ARL_RES_LIN=0;
    private static final int INT_ARL_RES_TRESL=3;
    private static final int INT_ARL_RES_ITEM_GP=1;
    private static final int INT_ARL_RES_ITEM_ZAF=2;
    private static final int INT_ARL_RES_ITEM_NOM=4;
    private java.util.Date datFecAux;  
    String mensajeConf ="",strFecUltMod="";
    private ZafParSis objParSis;
    private String version ="v1.4";  
    
    private static final int INT_ARL_CON_SQL_TRES=0;
    private static final int INT_ARL_CON_SQL_COD_GPGP=1;
    private static final int INT_ARL_CON_SQL_NOMBRE=2;
    private static final int INT_ARL_CON_SQL_COD_ZAF=3;
    private static final int INT_ARL_CON_SQL_COST=4;
    private static final int INT_ARL_CON_SQL_PREC=5;
    private static final int INT_ARL_CON_SQL_FAC_COS=6;    
    private static final int INT_ARL_CON_SQL_PRO_ANU_VEN=7;    
    private static final int INT_ARL_CON_SQL_STK_BAS=8;    
    private static final int INT_ARL_CON_SQL_TRA=9;    
    private static final int INT_ARL_CON_SQL_PUN_PAR_MIN=10;    
    private static final int INT_ARL_CON_SQL_PUN_PAR_MAX=11;    
    private static final int INT_ARL_CON_SQL_MOQ=12;    
    private static final int INT_ARL_CON_SQL_EXC=13;    
    private static final int INT_ARL_CON_SQL_PRO_VEN_TEO=14;
    private static final int INT_ARL_CON_SQL_PES=15;    
    
////    private static final int INT_ARL_CON_INV_MAR_UTI=5;
////    private static final int INT_ARL_CON_INV_MAX_VEN=6;
////    private static final int INT_ARL_CON_INV_COS_UNI=7;    
////    private static final int INT_ARL_CON_INV_PRE_VTA=8;
////    private static final int INT_ARL_CON_INV_GRP_IMP=9;
////    private static final int INT_ARL_CON_INV_NOM_ITM=10;
////    private static final int INT_ARL_CON_INV_REV_TEC=11;    
////    private static final int INT_ARL_CON_INV_ITM_STO=12;   
    
    private ZafExpoExc objExcel; 
    private String strDirApi="";
    private String strDirCont="";
    
    
    
    /**
    * Crea una nueva instancia de la clase ZafWebSer
    */
    public ZafWebSer(ZafParSis obj)
    {
         try {
             
            this.objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            strDirApi=this.objParSis.obtenerDireccionApi();
            strDirCont=this.objParSis.obtenerDireccionCont();
            
        } catch (CloneNotSupportedException ex) {
          //  Logger.getLogger(ZafMae85_02.class.getName()).log(Level.SEVERE, null, ex);
        }      
           
    }

   public List ejecucionWebServRepos() throws IOException        
  {
    String strFecSis = objUti.getFechaServidor(
        objParSis.getStringConexion(),
        objParSis.getUsuarioBaseDatos(),
        objParSis.getClaveBaseDatos(),
        objParSis.getQueryFechaHoraBaseDatos(),
        objParSis.getFormatoFechaBaseDatos()
    );
    String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion/" + strFecSis; 
    
 
        //System.out.println("LLAMADA>>>> " + strUrlWebTuval);
        
        // Hacer la solicitud HTTP
        URL url = new URL(strUrlWebTuval);
        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
        conn1.setRequestMethod("GET");
        conn1.setRequestProperty("Accept", "application/json; charset=utf-8");

        // Leer la respuesta
        InputStreamReader entrada = new InputStreamReader(conn1.getInputStream());
        BufferedReader lectura = new BufferedReader(entrada);
        StringBuilder respuesta = new StringBuilder();
        String linea;
        
        while ((linea = lectura.readLine()) != null) {
            respuesta.append(linea);
        }
        
        conn1.disconnect();
        
        // Convertir la respuesta en un JSONObject
        JSONObject jsonResponse = new JSONObject(respuesta.toString());
        
        // Obtener el array "documentos_procesados"
        JSONArray documentosArray = jsonResponse.getJSONArray("documentos_procesados");
        
        // Convertir JSONArray a lista de Strings
        List<String> documentosProcesados = new ArrayList<>();
        for (int i = 0; i < documentosArray.length(); i++) {
            documentosProcesados.add(documentosArray.getString(i));
        }
        
   
        
   return documentosProcesados;

  }
   
////   public boolean ejecucionWebServReposFechaRepo(int reposicion) throws IOException        
////  {
////      boolean blnres=false;
////    String strFecSis = objUti.getFechaServidor(
////        objParSis.getStringConexion(),
////        objParSis.getUsuarioBaseDatos(),
////        objParSis.getClaveBaseDatos(),
////        objParSis.getQueryFechaHoraBaseDatos(),
////        objParSis.getFormatoFechaBaseDatos()
////    );
////    String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion/" + strFecSis; 
//// 
////        //System.out.println("LLAMADA>>>> " + strUrlWebTuval);
////        
////        // Hacer la solicitud HTTP
////        URL url = new URL(strUrlWebTuval);
////        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
////        conn1.setRequestMethod("GET");
////        conn1.setRequestProperty("Accept", "application/json");
////
////        // Leer la respuesta
////        InputStreamReader entrada = new InputStreamReader(conn1.getInputStream());
////        BufferedReader lectura = new BufferedReader(entrada);
////        StringBuilder respuesta = new StringBuilder();
////        String linea;
////        
////        while ((linea = lectura.readLine()) != null) {
////            respuesta.append(linea);
////        }
////        
////        conn1.disconnect();
////        
////        // Convertir la respuesta en un JSONObject
////        JSONObject jsonResponse = new JSONObject(respuesta.toString());
////        
////        // Obtener el array "documentos_procesados"
////        JSONArray documentosArray = jsonResponse.getJSONArray("documentos_procesados");
////        
////        // Convertir JSONArray a lista de Strings
////        List<String> documentosProcesados = new ArrayList<>();
////        for (int i = 0; i < documentosArray.length(); i++) {
////            documentosProcesados.add(documentosArray.getString(i));
////        }
////        
////   
////        
////   return blnres;
////
////  }   
   
   
////import org.json.JSONObject;
////import java.io.BufferedReader;
////import java.io.IOException;
////import java.io.InputStreamReader;
////import java.net.HttpURLConnection;
////import java.net.URL;

////public boolean ejecucionWebServReposFechaRepo1(int reposicion) throws IOException {
////    boolean blnres = false;
////    
////    String strFecSis = objUti.getFechaServidor(
////        objParSis.getStringConexion(),
////        objParSis.getUsuarioBaseDatos(),
////        objParSis.getClaveBaseDatos(),
////        objParSis.getQueryFechaHoraBaseDatos(),
////        objParSis.getFormatoFechaBaseDatos()
////    );
////    
////    String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion/" + strFecSis + "/" + reposicion;
////    
////    // Hacer la solicitud HTTP
////    URL url = new URL(strUrlWebTuval);
////    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
////    conn1.setRequestMethod("GET");
////    conn1.setRequestProperty("Accept", "application/json");
////
////    // Leer la respuesta
////    InputStreamReader entrada = new InputStreamReader(conn1.getInputStream());
////    BufferedReader lectura = new BufferedReader(entrada);
////    StringBuilder respuesta = new StringBuilder();
////    String linea;
////    
////    while ((linea = lectura.readLine()) != null) {
////        respuesta.append(linea);
////    }
////    
////    conn1.disconnect();
////
////    // Convertir la respuesta en un JSONObject
////    JSONObject jsonResponse = new JSONObject(respuesta.toString());
////
////    // Validar si la respuesta es exitosa
////    if (jsonResponse.has("success") && jsonResponse.getBoolean("success")) {
////        blnres = true;
////    } else {
////        System.out.println("Error: " + jsonResponse.optString("message", "Respuesta no válida"));
////    }
////
////    return blnres;
////}

//////////public boolean ejecucionWebServReposFechaRepo(int reposicion) {
//////////    boolean blnres = false;
//////////    String fe_fecha = "2025-04-26";    
//////////
//////////    try {
//////////        // Obtener fecha del sistema desde la base de datos
//////////        String strFecSis = objUti.getFechaServidor(
//////////            objParSis.getStringConexion(),
//////////            objParSis.getUsuarioBaseDatos(),
//////////            objParSis.getClaveBaseDatos(),
//////////            objParSis.getQueryFechaHoraBaseDatos(),
//////////            objParSis.getFormatoFechaBaseDatos()
//////////        );
//////////
//////////        // URL plantilla
//////////        String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
//////////
//////////        // Reemplazar los valores en la URL
//////////        strUrlWebTuval = strUrlWebTuval.replace("ne_doc", String.valueOf(reposicion))
//////////                                       .replace("fe_fecha", fe_fecha);    
//////////
//////////        // Realizar la conexión HTTP GET
//////////        URL url = new URL(strUrlWebTuval);
//////////        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
//////////        conn1.setRequestMethod("GET");
//////////        conn1.setRequestProperty("Accept", "application/json");
//////////
//////////        // Leer la respuesta
//////////        InputStreamReader entrada = new InputStreamReader(conn1.getInputStream());
//////////        BufferedReader lectura = new BufferedReader(entrada);
//////////        StringBuilder respuesta = new StringBuilder();
//////////        String linea;
//////////
//////////        while ((linea = lectura.readLine()) != null) {
//////////            respuesta.append(linea);
//////////        }
//////////
//////////        conn1.disconnect();
//////////
//////////        // Convertir a JSON
//////////        JSONObject jsonResponse = new JSONObject(respuesta.toString());
//////////
//////////        // Formatear reposición como string de 11 dígitos (ej. 00000006661)
//////////        String strNumDoc = String.format("%011d", reposicion);
//////////
//////////        // Verificar si la respuesta fue exitosa
//////////        if (jsonResponse.has("sucess") && jsonResponse.getBoolean("sucess")) {
//////////            if (jsonResponse.has("data")) {
//////////                JSONArray dataArray = jsonResponse.getJSONArray("data");
//////////                for (int i = 0; i < dataArray.length(); i++) {
//////////                    if (dataArray.getString(i).equals(strNumDoc)) {
//////////                        blnres = true; // Documento procesado correctamente
//////////                        break;
//////////                    }
//////////                }
//////////            }
//////////        } else {
//////////            // En caso de error, revisar si el documento está en la lista de errores
//////////            if (jsonResponse.has("error")) {
//////////                JSONArray errorArray = jsonResponse.getJSONArray("error");
//////////                for (int i = 0; i < errorArray.length(); i++) {
//////////                    JSONObject objError = errorArray.getJSONObject(i);
//////////                    if (objError.getString("numdoc").equals(strNumDoc)) {
//////////                        blnres = false; // Documento con error
//////////                        System.err.println("Error en documento " + strNumDoc + ": " + objError.getString("error"));
//////////                        break;
//////////                    }
//////////                }
//////////            }
//////////        }
//////////    } catch (Exception e) {
//////////        e.printStackTrace();
//////////        blnres = false; // Si hay excepción, se considera error
//////////    }
//////////
//////////    return blnres;
//////////}

   ///1
////public boolean ejecucionWebServReposFechaRepo(int reposicion) {
////    boolean blnres = false;
////    String fe_fecha = "2025-04-26";    
////
////    try {
////        // Obtener fecha del sistema desde la base de datos
////        String strFecSis = objUti.getFechaServidor(
////            objParSis.getStringConexion(),
////            objParSis.getUsuarioBaseDatos(),
////            objParSis.getClaveBaseDatos(),
////            objParSis.getQueryFechaHoraBaseDatos(),
////            objParSis.getFormatoFechaBaseDatos()
////        );
////
////        // URL plantilla
////        String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
////
////        // Reemplazar los valores en la URL
////        strUrlWebTuval = strUrlWebTuval.replace("ne_doc", String.valueOf(reposicion))
////                                       .replace("fe_fecha", fe_fecha);    
////
////        // Conexión HTTP
////        URL url = new URL(strUrlWebTuval);
////        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
////        conn1.setRequestMethod("GET");
////        conn1.setRequestProperty("Accept", "application/json");
////
////        int responseCode = conn1.getResponseCode();
////        InputStreamReader entrada = null;
////
////        if (responseCode == HttpURLConnection.HTTP_OK) {
////            entrada = new InputStreamReader(conn1.getInputStream());
////        } else {
////            // Si no es 200 OK, leer el flujo de error
////            entrada = new InputStreamReader(conn1.getErrorStream());
////            System.err.println("Error HTTP: " + responseCode + " al conectar con la URL: " + strUrlWebTuval);
////        }
////
////        BufferedReader lectura = new BufferedReader(entrada);
////        StringBuilder respuesta = new StringBuilder();
////        String linea;
////
////        while ((linea = lectura.readLine()) != null) {
////            respuesta.append(linea);
////        }
////
////        conn1.disconnect();
////
////        // Si el código fue OK, procesar como JSON
////        if (responseCode == HttpURLConnection.HTTP_OK) {
////            JSONObject jsonResponse = new JSONObject(respuesta.toString());
////            
////
////            // Formatear reposición como string de 11 dígitos
////            String strNumDoc = String.format("%011d", reposicion);
////
////            if (jsonResponse.has("sucess") && jsonResponse.getBoolean("sucess")) {
////                if (jsonResponse.has("data")) {
////                    JSONArray dataArray = jsonResponse.getJSONArray("data");
////                    for (int i = 0; i < dataArray.length(); i++) {
////                        if (dataArray.getString(i).equals(strNumDoc)) {
////                            blnres = true;
////                            break;
////                        }
////                    }
////                }
////            } else {
////                if (jsonResponse.has("error")) {
////                    JSONArray errorArray = jsonResponse.getJSONArray("error");
////                    for (int i = 0; i < errorArray.length(); i++) {
////                        JSONObject objError = errorArray.getJSONObject(i);
////                        if (objError.getString("numdoc").equals(strNumDoc)) {
////                            blnres = false;
////                            System.err.println("Error en documento " + strNumDoc + ": " + objError.getString("error"));
////                            break;
////                        }
////                    }
////                }
////            }
////        } else {
////            blnres = false; // Si no fue 200 OK, asumimos error
////        }
////
////    } catch (Exception e) {
////        e.printStackTrace();
////        blnres = false;
////    }
////
////    return blnres;
////}


   //2
////   public boolean ejecucionWebServReposFechaRepo(int reposicion) {
////    boolean blnres = false;
////    String fe_fecha = "2025-04-26";
////
////    try {
////        // Obtener fecha del sistema desde la base de datos
////        String strFecSis = objUti.getFechaServidor(
////            objParSis.getStringConexion(),
////            objParSis.getUsuarioBaseDatos(),
////            objParSis.getClaveBaseDatos(),
////            objParSis.getQueryFechaHoraBaseDatos(),
////            objParSis.getFormatoFechaBaseDatos()
////        );
////
////        // URL plantilla
////        String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
////
////        // Reemplazar los valores en la URL
////        strUrlWebTuval = strUrlWebTuval.replace("ne_doc", String.valueOf(reposicion))
////                                       .replace("fe_fecha", fe_fecha);
////
////        // Conexión HTTP
////        URL url = new URL(strUrlWebTuval);
////        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
////        conn1.setRequestMethod("GET");
////        conn1.setRequestProperty("Accept", "application/json");
////
////        int responseCode = conn1.getResponseCode();
////        InputStreamReader entrada = null;
////
////        if (responseCode == HttpURLConnection.HTTP_OK) {
////            entrada = new InputStreamReader(conn1.getInputStream());
////        } else {
////            entrada = new InputStreamReader(conn1.getErrorStream());
////            System.err.println("Error HTTP: " + responseCode + " al conectar con la URL: " + strUrlWebTuval);
////        }
////
////        BufferedReader lectura = new BufferedReader(entrada);
////        StringBuilder respuesta = new StringBuilder();
////        String linea;
////
////        while ((linea = lectura.readLine()) != null) {
////            respuesta.append(linea);
////        }
////
////        conn1.disconnect();
////
////        // Imprimir la respuesta para depurar
////        System.out.println("Respuesta completa recibida: " + respuesta.toString());
////
////        // Si el código fue OK, procesar como JSON
////        if (responseCode == HttpURLConnection.HTTP_OK) {
////            try {
////                
////                JSONObject jsonResponse = new JSONObject(respuesta.toString());
////
////                // Formatear reposición como string de 11 dígitos
////                String strNumDoc = String.format("%011d", reposicion);
////
////                // Verificar si tiene el campo 'sucess'
////                if (jsonResponse.has("sucess")) {
////                    boolean isSuccess = jsonResponse.getBoolean("sucess");
////
////                    if (isSuccess) {
////                        if (jsonResponse.has("data")) {
////                            JSONArray dataArray = jsonResponse.getJSONArray("data");
////                            for (int i = 0; i < dataArray.length(); i++) {
////                                String itemDoc = dataArray.getString(i);
////                                if (itemDoc.equals(strNumDoc)) {
////                                    blnres = true;
////                                    break;
////                                }
////                            }
////                        }
////                    } else {
////                        if (jsonResponse.has("error")) {
////                            JSONArray errorArray = jsonResponse.getJSONArray("error");
////                            for (int i = 0; i < errorArray.length(); i++) {
////                                JSONObject objError = errorArray.getJSONObject(i);
////                                if (objError.has("numdoc") && objError.getString("numdoc").equals(strNumDoc)) {
////                                    blnres = false;
////                                    if (objError.has("error")) {
////                                        System.err.println("Error en documento " + strNumDoc + ": " + objError.getString("error"));
////                                    }
////                                    break;
////                                }
////                            }
////                        }
////                    }
////                } else {
////                    System.err.println("El JSON no contiene el campo 'sucess'.");
////                    blnres = false;
////                }
////
////            } catch (Exception exJson) {
////                System.err.println("Error interpretando JSON: " + exJson.getMessage());
////                blnres = false;
////            }
////        } else {
////            blnres = false; // Si no fue 200 OK, asumimos error
////        }
////
////    } catch (Exception e) {
////        e.printStackTrace();
////        blnres = false;
////    }
////
////    return blnres;
////}

//3

////////public boolean ejecucionWebServReposFechaRepo(int reposicion) {
////////    boolean blnres = false;
////////    String fe_fecha = "2025-04-26";
////////
////////    try {
////////        // Obtener fecha del sistema desde la base de datos
////////        String strFecSis = objUti.getFechaServidor(
////////            objParSis.getStringConexion(),
////////            objParSis.getUsuarioBaseDatos(),
////////            objParSis.getClaveBaseDatos(),
////////            objParSis.getQueryFechaHoraBaseDatos(),
////////            objParSis.getFormatoFechaBaseDatos()
////////        );
////////
////////        // URL plantilla
////////        String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
////////
////////        // Reemplazar los valores en la URL
////////        strUrlWebTuval = strUrlWebTuval.replace("ne_doc", String.valueOf(reposicion))
////////                                       .replace("fe_fecha", fe_fecha);
////////
////////        // Conexión HTTP
////////        URL url = new URL(strUrlWebTuval);
////////        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
////////        conn1.setRequestMethod("GET");
////////        conn1.setRequestProperty("Accept", "application/json; charset=utf-8");
////////
////////        int responseCode = conn1.getResponseCode();
////////        InputStreamReader entrada = null;
////////
////////        if (responseCode == HttpURLConnection.HTTP_OK) {
////////            entrada = new InputStreamReader(conn1.getInputStream());
////////        } else {
////////            entrada = new InputStreamReader(conn1.getErrorStream());
////////            System.err.println("Error HTTP: " + responseCode + " al conectar con la URL: " + strUrlWebTuval);
////////        }
////////
////////        BufferedReader lectura = new BufferedReader(entrada);
////////        StringBuilder respuesta = new StringBuilder();
////////        String linea;
////////
////////        while ((linea = lectura.readLine()) != null) {
////////            respuesta.append(linea);
////////        }
////////
////////        conn1.disconnect();
////////
////////        // Imprimir la respuesta para depurar
////////        System.out.println("Respuesta completa recibida: " + respuesta.toString());
////////
////////        // Validar si la respuesta es un JSON válido
////////        if (respuesta.toString().startsWith("{") && respuesta.toString().endsWith("}")) {
////////            try {
////////                JSONObject jsonResponse = new JSONObject(respuesta.toString());
////////
////////                // Formatear reposición como string de 11 dígitos
////////                String strNumDoc = String.format("%011d", reposicion);
////////
////////                // Verificar si tiene el campo 'success'
////////                if (jsonResponse.has("success")) {
////////                    boolean isSuccess = jsonResponse.getBoolean("success");
////////
////////                    if (isSuccess) {
////////                        if (jsonResponse.has("data")) {
////////                            JSONArray dataArray = jsonResponse.getJSONArray("data");
////////                            for (int i = 0; i < dataArray.length(); i++) {
////////                                String itemDoc = dataArray.getString(i);
////////                                if (itemDoc.equals(strNumDoc)) {
////////                                    blnres = true;
////////                                    break;
////////                                }
////////                            }
////////                        }
////////                    } else {
////////                        if (jsonResponse.has("error")) {
////////                            JSONArray errorArray = jsonResponse.getJSONArray("error");
////////                            for (int i = 0; i < errorArray.length(); i++) {
////////                                JSONObject objError = errorArray.getJSONObject(i);
////////                                if (objError.has("numdoc") && objError.getString("numdoc").equals(strNumDoc)) {
////////                                    blnres = false;
////////                                    if (objError.has("error")) {
////////                                        System.err.println("Error en documento " + strNumDoc + ": " + objError.getString("error"));
////////                                    }
////////                                    break;
////////                                }
////////                            }
////////                        }
////////                    }
////////                } else {
////////                    System.err.println("El JSON no contiene el campo 'success'.");
////////                    blnres = false;
////////                }
////////
////////            } catch (Exception exJson) {
////////                System.err.println("Error interpretando JSON: " + exJson.getMessage());
////////                blnres = false;
////////            }
////////        } else {
////////            System.err.println("Respuesta no es un JSON válido: " + respuesta.toString());
////////            blnres = false;
////////        }
////////
////////    } catch (Exception e) {
////////        e.printStackTrace();
////////        blnres = false;
////////    }
////////
////////    return blnres;
////////}

   
   
public boolean ejecucionWebServReposFechaRepo(int reposicion, String fe_fecha) {
    boolean blnres = false; 
    //String fe_fecha = "2025-04-26";

            // Formato de entrada
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            // Formato de salida
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
            // Convertir
           
    
    try {  
            Date date = formatoEntrada.parse(fe_fecha);
            String fechaFormateada = formatoSalida.format(date);         
        // Construir URL
//        String strUrlWebTuval = "http://tuvalapi.cosenco.ec/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
        //String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";
        
        String strUrlWebTuval =strDirApi + "bodegas/getreposicion?numDoc=ne_doc&fecha=fe_fecha";     
        strUrlWebTuval = strUrlWebTuval.replace("ne_doc", String.valueOf(reposicion))
                                       .replace("fe_fecha", fechaFormateada);

        URL url = new URL(strUrlWebTuval);
        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
        
        conn1.setRequestMethod("GET");
        conn1.setRequestProperty("Accept", "application/json; charset=utf-8");

        int responseCode = conn1.getResponseCode();
        String contentType = conn1.getHeaderField("Content-Type");
        System.out.println("Content-Type recibido: " + contentType);

        // Validar Content-Type
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            System.err.println("Advertencia: el Content-Type no es application/json. Es: " + contentType);
            conn1.disconnect();
            return false;
        }

        // Leer la respuesta usando UTF-8
        InputStreamReader entrada = (responseCode == HttpURLConnection.HTTP_OK)
            ? new InputStreamReader(conn1.getInputStream(), StandardCharsets.UTF_8)
            : new InputStreamReader(conn1.getErrorStream(), StandardCharsets.UTF_8);

        BufferedReader lectura = new BufferedReader(entrada);
        StringBuilder respuesta = new StringBuilder();
        String linea;

        while ((linea = lectura.readLine()) != null) {
            respuesta.append(linea);
        }
        conn1.disconnect();

        String respuestaStr = respuesta.toString().trim();
        System.out.println("Respuesta completa recibida: [" + respuestaStr + "]");

        // Validar que haya contenido
        if (respuestaStr.isEmpty()) {
            System.err.println("Respuesta vacía. No se puede procesar.");
            return false;
        }
        if (!respuestaStr.startsWith("{") && !respuestaStr.startsWith("[")) {
            System.err.println("Respuesta inválida, no es JSON: " + respuestaStr);
            return false;
        }
        
// Verificamos si contiene "true" o "false"
//if (respuestaStr.contains("true") || respuestaStr.contains("false")) {
        if (respuestaStr.contains("true") ) {
            System.out.println("La cadena contiene 'true'");
            return true;
        } else {
            System.out.println("La cadena contiene  'false'");
            return false;
        }


////        String jsonString = respuesta.toString();
////        jsonString = jsonString.replaceAll("[^\\x00-\\x7F]", ""); // Eliminar caracteres no ASCII
        // Parsear JSON
//////        JSONObject jsonResponse = new JSONObject(jsonString);
//////
//////        // Procesar el JSON
//////        if (jsonResponse.has("success")) {
//////            boolean isSuccess = jsonResponse.getBoolean("success");
//////            String strNumDoc = String.format("%011d", reposicion);
//////
//////            if (isSuccess) {
//////                // Si éxito, buscar el numdoc en 'data'
//////                if (jsonResponse.has("data")) {
//////                    JSONArray dataArray = jsonResponse.getJSONArray("data");
//////                    for (int i = 0; i < dataArray.length(); i++) {
//////                        String itemDoc = dataArray.getString(i);
//////                        if (itemDoc.equals(strNumDoc)) {
//////                            blnres = true;
//////                            break;
//////                        }
//////                    }
//////                }
//////            } else {
//////                // Si error, buscar en 'errors' o 'error'
//////                if (jsonResponse.has("errors")) {
//////                    JSONArray errorArray = jsonResponse.getJSONArray("errors");
//////                    for (int i = 0; i < errorArray.length(); i++) {
//////                        JSONObject objError = errorArray.getJSONObject(i);
//////                        if (objError.has("numdoc") && objError.getString("numdoc").equals(strNumDoc)) {
//////                            System.err.println("Error en documento " + strNumDoc + ": " + objError.getString("error"));
//////                            blnres = false;
//////                            break;
//////                        }
//////                    }
//////                } else if (jsonResponse.has("error")) {
//////                    System.err.println("Error general recibido: " + jsonResponse.getString("error"));
//////                    blnres = false;
//////                }
//////            }
//////        } else {
//////            System.err.println("El JSON no contiene el campo 'success'.");
//////            blnres = false;
//////        }

    } catch (Exception e) {
        System.err.println("Excepción capturada: " + e.getMessage());
        e.printStackTrace();
        blnres = false;
    } 

    return blnres;
}   
   
   
// 🔸 Método para consumir el API (POST con body JSON)
    public String obtenerConsultaPedidosJSON(String jsonBody) 
    {
        String respuestaStr = "";

        try {
            String strUrlWebTuval = strDirCont + "api/consulta-pedidos";
            URL url = new URL(strUrlWebTuval);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json; charset=utf-8");
            conn.setDoOutput(true);

            // Enviar body JSON
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta HTTP: " + responseCode);

            String contentType = conn.getHeaderField("Content-Type");
            System.out.println("Content-Type recibido: " + contentType);

            // Validar tipo de contenido
            if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
                System.err.println("Advertencia: el Content-Type no es application/json. Es: " + contentType);
                conn.disconnect();
                return "{\"error\": \"Content-Type inválido\"}";
            }

            // Leer respuesta
            InputStreamReader entrada = (responseCode == HttpURLConnection.HTTP_OK)
                    ? new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                    : new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);

            BufferedReader lectura = new BufferedReader(entrada);
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = lectura.readLine()) != null) {
                respuesta.append(linea);
            }
            conn.disconnect();

            respuestaStr = respuesta.toString().trim();
            System.out.println("Respuesta completa recibida: [" + respuestaStr + "]");

            if (respuestaStr.isEmpty() ||
               (!respuestaStr.startsWith("{") && !respuestaStr.startsWith("["))) {
                return "{\"error\": \"Respuesta vacía o inválida\"}";
            }

        } catch (Exception e) {
            System.err.println("Excepción capturada: " + e.getMessage());
            e.printStackTrace();
            respuestaStr = "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
        }

        return respuestaStr;
    }
    
// 🔸 Método para consumir el API (POST con body JSON)
    public String obtenerConsultaReservadosJSON(String jsonBody) 
    {
        String respuestaStr = "";

        try {
            //String strUrlWebTuval = "http://api.tuvalsa.com/api/consultas-externas/reservados";
            String strUrlWebTuval = strDirApi + "api/consultas-externas/reservados";
            URL url = new URL(strUrlWebTuval);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json; charset=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Enviar body JSON
            try (OutputStream os = conn.getOutputStream()) { 
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta HTTP: " + responseCode);

            String contentType = conn.getHeaderField("Content-Type");
            System.out.println("Content-Type recibido: " + contentType);

            // Validar tipo de contenido
            if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
                System.err.println("Advertencia: el Content-Type no es application/json. Es: " + contentType);
                conn.disconnect();
                return "{\"error\": \"Content-Type inválido\"}";
            }

            // Leer respuesta
            InputStreamReader entrada = (responseCode == HttpURLConnection.HTTP_OK)
                    ? new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                    : new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);

            BufferedReader lectura = new BufferedReader(entrada);
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = lectura.readLine()) != null) {
                respuesta.append(linea);
            }

            conn.disconnect();

            respuestaStr = respuesta.toString().trim();
            System.out.println("Respuesta completa recibida: [" + respuestaStr + "]");

            if (respuestaStr.isEmpty() ||
               (!respuestaStr.startsWith("{") && !respuestaStr.startsWith("["))) {
                return "{\"error\": \"Respuesta vacía o inválida\"}";
            }

        } catch (Exception e) {
            System.err.println("Excepción capturada: " + e.getMessage());
            e.printStackTrace();
            respuestaStr = "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
        }

        return respuestaStr;
    }     
    

public String sincronizarArticuloJSON(String jsonBody) {
    String respuestaStr = "";
    HttpURLConnection conn = null;

    try {
        //String strUrlWebTuval = "http://tuvalapi.cosenco.ec/api/inventario/articulos/sincronizar";
        String strUrlWebTuval = strDirApi + "api/inventario/articulos/sincronizar";

        URL url = new URL(strUrlWebTuval);
        conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("Accept", "application/json; charset=utf-8");

        conn.setConnectTimeout(15000);
        conn.setReadTimeout(30000);
        conn.setDoOutput(true);

        // Enviar body JSON
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        System.out.println("Código de respuesta HTTP: " + responseCode);

        String contentType = conn.getHeaderField("Content-Type");

        System.out.println("Content-Type recibido: " + contentType);

        // Leer respuesta de la API
        InputStream inputStream = (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        if (inputStream == null) {
            return "{\"error\":\"La API no devolvió contenido\"}";
        }

        try (BufferedReader lectura = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            StringBuilder respuesta = new StringBuilder();
            String linea;

            while ((linea = lectura.readLine()) != null) {
                respuesta.append(linea);
            }

            respuestaStr = respuesta.toString().trim();
        }

        System.out.println("Respuesta completa recibida: [" + respuestaStr + "]");

        if (respuestaStr.isEmpty()) {
            return "{\"error\":\"Respuesta vacía de la API\"}";
        }

        if (!respuestaStr.startsWith("{") && !respuestaStr.startsWith("[")) {
            return "{\"error\":\"La API devolvió una respuesta que no es JSON\"}";
        }

    } catch (Exception e) {
        System.err.println("Error al sincronizar artículo: " + e.getMessage());
        e.printStackTrace();

        JSONObject jsonError = new JSONObject();
        jsonError.put("error", e.getMessage() == null
                ? "Error desconocido"
                : e.getMessage());

        respuestaStr = jsonError.toString();

    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }

    return respuestaStr;
}


}    
 
    



  