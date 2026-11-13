package Principal;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Rosa Zambrano
 */
public class EnvioMail 
{
    //Constantes: Configuración
    public static final int INT_COD_CFG_NOT_PED_NO_CER=6;           //Código de Notificación de Pedidos no cerrados.
    public static final int INT_COD_CFG_NOT_PED_NO_CON=7;           //Código de Notificación de Pedidos no Contados.
    public static final int INT_COD_CFG_NOT_PED_NO_ARR=8;           //Código de Notificación de Pedidos no arribados.
    public static final int INT_COD_CFG_NOT_DOC_AJU_IMP_SOL_PEN=9;  //Código de Notificación de Documentos de ajustes pendientes de solicitudes de transferencia.
    public static final int INT_COD_CFG_NOT_DOC_AJU_IMP_PEN_AUT=11; //Código de Notificación de Documentos de ajustes de importaciones pendientes de autorización.
    
        
    //Constantes
    public static final int INT_COD_MNU_ING_IMP=2889;          //Ingresos por Importacion.
    public static final int INT_COD_MNU_ING_COM_LOC=4355;      //Ingresos por Compras Locales.
    public static final int INT_COD_MNU_PRG_AJU_INV=3460;      //Ajuste de Inventario...
    public static final int INT_COD_MNU_SOTRINI=3456;          //Solicitudes de Transferencia (Importaciones)...
    public static final int INT_COD_MNU_SOTRINC=4372;          //Solicitudes de Transferencia (Compras Locales)...
    public static final int INT_COD_MNU_CIE_PED_IMP=4161;      //Cierre de Pedido (Importaciones).
    public static final int INT_COD_MNU_CIE_PED_COM_LOC=4382;  //Cierre de Pedido (Compras Locales).
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafNotCorEle objNotCorEle;

    private int intCodLocGrp=1;
    
    private String strSQL="", strMsj="";
    

    public EnvioMail(ZafParSis obj)
    {
        //Inicializar objetos.
        objParSis=obj;
        objNotCorEle = new ZafNotCorEle(objParSis);
    }   
        
    /**
     * Función que envía correo de las Notificaciones.
     * Envia notificaciones a los correos configurados en la tabla tbm_notSisCorEleLoc.
     * @return 
     */
    public boolean correoNotificaciones(int intCodEmp, int intCodLoc, int intCodCfg)
    {
        boolean blnRes=true;
        try
        {
            switch (intCodCfg)
            {
                case INT_COD_CFG_NOT_PED_NO_CER:
                    strMsj=strMsjPedNoCer();
                    break;
               case INT_COD_CFG_NOT_PED_NO_CON:
                    strMsj=strMsjPedNoCon();
                    break;
               case INT_COD_CFG_NOT_PED_NO_ARR:
                    strMsj=strMsjPedNoArrConAra();
                    break;    
               case INT_COD_CFG_NOT_DOC_AJU_IMP_SOL_PEN:
                    strMsj=strMsjDocAjuAutSinSol();
                    break;  
               case INT_COD_CFG_NOT_DOC_AJU_IMP_PEN_AUT:
                    strMsj=strMsjDocAjuPenAut();
                    break;                      
                default:
                    strMsj="";
                    break;
            }
            if(strMsj.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(intCodEmp, intCodLoc, intCodCfg, strMsj)){
                    blnRes=false;
                }
            }
        }   
        catch (Exception e)  {
           System.err.println("ERROR: " + e);
           blnRes=false;
        }
        return blnRes;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Función obtiene Mensaje para las Notificaciones de pedidos no cerrados. 
     * Validación del fe_ingIngImp vs fecha actual (hoy) y que no hayan sido cerrados.
     * Envia notificaciones a Gerencia.
     * @return 
     */
    public String strMsjPedNoCer()
    {
        String strMsj="", strCab="", strDet="", strFin="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Arma Cabecera Tabla
                strCab+="<HTML> Notificaciones Sistema de Importaciones.<BR><BR>" ;
                strCab+=" <TABLE BORDER=1> ";   
                strCab+="   <tr> <td> FECHA PEDIDO </td> <td>TIPO DOCUMENTO</td><td> NUM.DOC. </td><td> PEDIDO </td> <td> DIAS ARRIBO</td> </tr>";  
                //Arma Detalle Tabla
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc,  a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.tx_numDoc2, a.fe_Doc, a.ne_diaPen";      
                strSQL+="      , CASE WHEN  ne_diaPen <= 11 THEN 'V'  ELSE CASE WHEN (ne_diaPen > 11 AND ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";  
                strSQL+=" FROM ( ";        
                strSQL+="    SELECT a.* , (current_date - a.fe_ingIngImp) AS ne_diaPen"; 
                strSQL+="    FROM ("; 
                strSQL+="         SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc"; 
                strSQL+="              , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END as st_ingImp"; 
                strSQL+="              , CAST(CASE WHEN a1.fe_ingIngImp IS NULL THEN a1.fe_doc ELSE a1.fe_ingIngImp END AS DATE) AS fe_ingIngImp"; 
                strSQL+="         FROM tbm_cabMovInv AS a1 "; 
                strSQL+="         INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc"; 
                strSQL+="         WHERE a1.st_Reg in ('A') "; 
                strSQL+=" 	  AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) NOT IN ('N', 'P', 'C') "; 
                strSQL+="         AND a1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+"";
                strSQL+="                               and co_loc="+intCodLocGrp+" and co_mnu IN("+INT_COD_MNU_CIE_PED_IMP+", "+INT_COD_MNU_CIE_PED_COM_LOC+")  )" ;
                strSQL+="    ) as a"; 
                strSQL+=" ) as a"; 
                strSQL+=" WHERE a.ne_diaPen > 5";
                strSQL+=" ORDER BY a.fe_ingIngImp, a.tx_numDoc2"; 
                System.out.println("strMsjPedNoCer: " + strSQL);
                rst=stm.executeQuery(strSQL);
                strDet="";
                while (rst.next())
                {
                    if(rst.getString("st_doc").equals("R"))
                    {
                        strDet+="<tr bgcolor='#FF4000'> ";//Rojo
                    }
                    else if(rst.getString("st_doc").equals("A"))
                    {
                        strDet+="<tr bgcolor='#FFFF00'> ";//Amarillo
                    }
                    else if(rst.getString("st_doc").equals("V"))
                    {
                        strDet+="<tr bgcolor='#D0F5A9'> "; //Verde
                    }
                    strDet+="  <td> "+rst.getString("fe_doc")    +" </td>";
                    strDet+="  <td> "+rst.getString("tx_desCor") +" </td>";
                    strDet+="  <td> "+rst.getString("ne_numDoc") +" </td>";
                    strDet+="  <td> "+rst.getString("tx_numDoc2")+" </td>";
                    strDet+="  <td> "+(rst.getString("ne_diaPen")==null ?"0":rst.getString("ne_diaPen")) +" </td>";
                    strDet+="</tr>";
                }
                strFin=" </TABLE><BR> <HTML>";

                //Arma el correo.
                if(strDet.length()>0)
                {
                    strMsj+=strCab+strDet+strFin;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }   
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            strMsj="";
        }        
        catch (Exception e)   {
           System.err.println("ERROR: " + e);
           strMsj="";
        }
        return strMsj;
    }
    
    /**
     * Función obtiene Mensaje para las Notificaciones de pedidos no contados.
     * Validación del fe_ingIngImp vs fecha actual (hoy) y que no hayan sido contados.
     * Envia notificaciones a Gerencia.
     * @return 
     */
    public String strMsjPedNoCon()
    {
        String strMsj="", strCab="", strDet="", strFin="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Arma Cabecera Tabla
                strCab+="<HTML> Notificaciones Sistema de Importaciones. <BR><BR>" ;
                strCab+=" <TABLE BORDER=1> ";   
                strCab+="   <tr> <td> FECHA PEDIDO </td> <td>TIPO DOCUMENTO</td><td> NUM.DOC. </td><td> PEDIDO </td> <td> DIAS ARRIBO</td> </tr>";  
                //Arma Detalle Tabla
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc,  a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.tx_numDoc2, a.fe_Doc, a.ne_diaPen";      
                strSQL+="      , CASE WHEN (ne_diaPen ) <= 11 THEN 'V'  ELSE CASE WHEN (ne_diaPen > 11 AND ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";  
                strSQL+=" FROM ( ";        
                strSQL+="    SELECT a.* , (current_date - a.fe_ingIngImp) AS ne_diaPen"; 
                strSQL+="    FROM ("; 
                strSQL+="         SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc"; 
                strSQL+="              , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END as st_ingImp"; 
                strSQL+="              , CAST(CASE WHEN a1.fe_ingIngImp IS NULL THEN a1.fe_doc ELSE a1.fe_ingIngImp END AS DATE) AS fe_ingIngImp"; 
                strSQL+="         FROM tbm_cabMovInv AS a1 "; 
                strSQL+="         INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc"; 
                strSQL+="         WHERE a1.st_Reg in ('A') "; 
                strSQL+="         AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) IN ('A')"; 
                strSQL+="         AND a1.co_mnu IN ("+INT_COD_MNU_ING_IMP+", "+INT_COD_MNU_ING_COM_LOC+")";  
                strSQL+="    ) as a"; 
                strSQL+=" ) as a"; 
                strSQL+=" WHERE a.ne_diaPen > 5";
                strSQL+=" ORDER BY a.fe_ingIngImp, a.tx_numDoc2"; 
                
                //<editor-fold defaultstate="collapsed" desc="/* comentado */">
                //strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.tx_numDoc2, a.fe_Doc,  a.ne_diaPen";
                //strSQL+="      , CASE WHEN (ne_diaPen ) <= 11 THEN 'V'  ELSE CASE WHEN (ne_diaPen > 11 AND ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";   
                //strSQL+=" FROM (";
                //strSQL+="        SELECT a1.tx_desCor, a1.tx_desLar, a.* ";
                //strSQL+="        FROM (";
                //strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_numDoc2, a.fe_doc";
                //strSQL+="                 ,(current_Date - cast(a.fe_ingIngImp as date) )as ne_diaPen";        
                //strSQL+="            FROM tbm_cabMovInv AS a";
                //strSQL+="            WHERE a.st_Reg IN ('A')";
                //strSQL+="            AND a.fe_ingIngImp IS NOT NULL ";               
                //strSQL+="            AND (CASE WHEN a.st_ingImp IS NULL THEN 'P' ELSE a.st_ingImp END) IN ('A')"; 
                //strSQL+="            AND a.co_mnu IN ("+INT_COD_MNU_ING_IMP+", "+INT_COD_MNU_ING_COM_LOC+");   
                //strSQL+="            AND NOT EXISTS(";
                //strSQL+=" 	           SELECT a1.co_empRel AS co_empIngImp, a1.co_locRel AS co_locIngImp, a1.co_tipDocRel AS co_tipDocIngImp, a1.co_docRel AS co_docIngImp";
                //strSQL+=" 	           FROM tbm_cabOrdConInv AS a1 INNER JOIN tbm_detOrdConInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";       
                //strSQL+=" 	           INNER JOIN tbm_conInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel)";
                //strSQL+=" 	           WHERE a3.fe_solCon IS NULL AND a1.co_empRel=a.co_Emp AND a1.co_locRel=a.co_loc AND a1.co_tipDocRel=a.co_tipDoc AND a1.co_docRel=a.co_doc";
                //strSQL+=" 	           GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";  
                //strSQL+="            )";
                //strSQL+="         ) as a"; 
                //strSQL+="         INNER JOIN tbm_cabTipDoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc)";
                //strSQL+=" ) as a";
                //strSQL+=" ORDER BY a.co_emp, a.co_loc, a.co_tipDoc, a.fe_doc, a.tx_numDoc2";

                //</editor-fold>
                
                System.out.println("strMsjPedNoCon: " + strSQL);
                rst=stm.executeQuery(strSQL);
                strDet="";
                while (rst.next())
                {
                    if(rst.getString("st_doc").equals("R"))
                    {
                        strDet+="<tr bgcolor='#FF4000'> ";//Rojo
                    }
                    else if(rst.getString("st_doc").equals("A"))
                    {
                        strDet+="<tr bgcolor='#FFFF00'> ";//Amarillo
                    }
                    else if(rst.getString("st_doc").equals("V"))
                    {
                        strDet+="<tr bgcolor='#D0F5A9'> "; //Verde
                    }
                    strDet+="  <td> "+rst.getString("fe_doc")    +" </td>";
                    strDet+="  <td> "+rst.getString("tx_desCor") +" </td>";
                    strDet+="  <td> "+rst.getString("ne_numDoc") +" </td>";
                    strDet+="  <td> "+rst.getString("tx_numDoc2")+" </td>";
                    strDet+="  <td> "+(rst.getString("ne_diaPen")==null ?"0":rst.getString("ne_diaPen")) +" </td>";
                    strDet+="</tr>";
                }

                strFin=" </TABLE><BR> <HTML>";

                //Arma el correo.
                if(strDet.length()>0)
                {
                    strMsj+=strCab+strDet+strFin;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }

        }   
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            strMsj="";
        }                 
        catch (Exception e)  {
           System.err.println("ERROR: " + e);
           strMsj="";
        }
        return strMsj;
    }   
    
    /**
     * Función obtiene Mensaje para las Notificaciones de pedidos no ingresados a bodega.
     * Cuando se cancele el arancel: 15 días en que no se haya marcado como recibido.
     * Envia notificaciones a Gerencia.
     * @return 
     */
    public String strMsjPedNoArrConAra()
    {
        String strMsj="", strCab="", strDet="", strFin="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Arma Cabecera Tabla
                strCab+="<HTML> Notificaciones Sistema de Importaciones. <BR><BR>" ;
                strCab+=" <TABLE BORDER=1> ";   
                strCab+="   <tr> <td> FECHA PEDIDO </td> <td>TIPO DOCUMENTO</td><td> NUM.DOC. </td><td> PEDIDO </td> <td> FECHA ARANCEL </td> <td> DIAS PENDIENTE DE ARRIBO</td> </tr>";  
                //Arma Detalle Tabla
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a1.tx_desCor, a1.tx_desLar, a.ne_numDoc, a.tx_numDoc2, a.fe_Doc, a.fe_PagAra, a.ne_diaPen";
                strSQL+="      , CASE WHEN (ne_diaPen ) <= 11 THEN 'V'  ELSE CASE WHEN (ne_diaPen > 11 AND ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";   
                strSQL+=" FROM(";
                strSQL+="      SELECT a.* , a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+="      FROM( ";
                strSQL+=" 	  SELECT a.*,  (current_Date - a.fe_PagAra) AS ne_diaPen";           
                strSQL+=" 	  FROM ( ";
                strSQL+=" 		/* Obtiene la fecha del primer pago arancelario*/";
                strSQL+=" 	        select a.co_emp as co_empPedEmb, a.co_loc as co_locPedEmb, a.co_tipDoc  as co_tipDocPedEmb, a.co_doc as co_DocPedEmb, MIN(a3.fe_doc) as fe_PagAra"; 	             
                strSQL+=" 	        from tbm_cabPedEmbImp as a"; 
                strSQL+=" 	        INNER JOIN tbm_detDia as a1 ON (a1.co_Emp=a.co_imp AND a1.co_cta=a.co_ctaAct)";
                strSQL+=" 	    	INNER JOIN tbm_docGenOtrMovBan as a2 ON (a2.co_empRelCabDia=a1.co_emp AND a2.co_locRelCabDia=a1.co_loc AND a2.co_tipDocRelCabDia=a1.co_tipDoc AND a2.co_DocRelCabDia=a1.co_dia)";
                strSQL+=" 		INNER JOIN tbm_cabOtrMovBan as a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_doc=a2.co_doc)";             
                strSQL+=" 		WHERE a.st_Reg IN ('A')"; 
                strSQL+=" 	 	GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";	
                strSQL+=" 	  ) as a";
                strSQL+="      ) as a";
                strSQL+="      INNER JOIN tbm_cabMovInv as a1 ON (a1.co_empRelPedEmbImp=a.co_empPedEmb AND a1.co_locRelPedEmbImp=a.co_locPedEmb";
                strSQL+="      AND a1.co_tipDocRelPedEmbImp=a.co_tipDocPedEmb AND a1.co_docRelPedEmbImp=a.co_docPedEmb AND a1.co_mnu IN ("+INT_COD_MNU_ING_IMP+", "+INT_COD_MNU_ING_COM_LOC+")  )";   
                strSQL+="      WHERE a1.st_reg IN ('A') AND a1.st_ingImp IS NOT NULL";   //Nuevo Esquema 
                strSQL+="      AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) IN ('P')"; 
                strSQL+=" ) as a";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc)";
                strSQL+=" WHERE a.ne_diaPen > 5";
                strSQL+=" ORDER BY a.ne_diaPen DESC, a.co_emp, a.co_loc, a.co_tipDoc, a.tx_numDoc2"; 
                System.out.println("strMsjPedNoArrConAra: " + strSQL);
                rst=stm.executeQuery(strSQL);
                strDet="";
                while (rst.next())
                {
                    if(rst.getString("st_doc").equals("R"))
                    {
                        strDet+="<tr bgcolor='#FF4000'> ";//Rojo
                    }
                    else if(rst.getString("st_doc").equals("A"))
                    {
                        strDet+="<tr bgcolor='#FFFF00'> ";//Amarillo
                    }
                    else if(rst.getString("st_doc").equals("V"))
                    {
                        strDet+="<tr bgcolor='#D0F5A9'> "; //Verde
                    }
                    strDet+="  <td> "+rst.getString("fe_doc")    +" </td>";
                    strDet+="  <td> "+rst.getString("tx_desCor") +" </td>";
                    strDet+="  <td> "+rst.getString("ne_numDoc") +" </td>";
                    strDet+="  <td> "+rst.getString("tx_numDoc2")+" </td>";
                    strDet+="  <td> "+rst.getString("fe_PagAra") +" </td>";
                    strDet+="  <td> "+(rst.getString("ne_diaPen")==null ?"0":rst.getString("ne_diaPen")) +" </td>";
                    strDet+="</tr>";
                }
                strFin=" </TABLE><BR> <HTML>";

                //Arma el correo.
                if(strDet.length()>0)
                {
                    strMsj+=strCab+strDet+strFin;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        } 
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            strMsj="";
        }            
        catch (Exception e)   {
           System.err.println("ERROR: " + e);
           strMsj="";
        }
         return strMsj;
    }   
    
    /**
     * Función obtiene Mensaje para las Notificaciones de documentos de ajustes pendientes de solicitud de transferencia.
     * Solo documentos de ajustes autorizados, es decir cuando VB1 y/o VB 2 se hayan realizado.
     * Envia notificaciones a mmontezuma.
     * @return 
     */
    public String strMsjDocAjuAutSinSol()
    {
        String strMsj="", strCab="", strDet="", strFin="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Arma Cabecera Tabla
                strCab+="<HTML> Notificaciones Sistema de Importaciones. <BR><BR>" ;
                strCab+=" <TABLE BORDER=1> ";   
                strCab+="   <tr> <td> FECHA DOCUMENTO </td> <td>TIPO DOCUMENTO AJUSTE</td><td> NUM.DOC.AJU.</td><td> PEDIDO ORIGEN </td> <td> DIAS PENDIENTES </td> </tr>";  
                //Arma Detalle Tabla
                strSQL ="";
                strSQL+=" SELECT a.co_Emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.fe_doc, a.tx_numDoc2, a.ne_diaPen";
                strSQL+="       , CASE WHEN (ne_diaPen ) <= 11 THEN 'V'  ELSE CASE WHEN (ne_diaPen > 11 AND ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";
                strSQL+=" FROM";
                strSQL+=" ( ";
                strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.tx_desCor, a6.tx_desLar, a1.ne_numDoc, substring(a1.tx_numDoc2 from 1 for LENGTH(a1.tx_numDoc2)-3) as tx_numDoc2, a1.fe_doc ";
                strSQL+="          ,(current_Date - a1.fe_doc )as ne_diaPen , a2.nd_can, CASE WHEN a5.nd_CanSol IS NULL THEN 0 ELSE a5.nd_CanSol end AS nd_CanSol";
                strSQL+="    FROM tbm_cabMovInv AS a1";   
                strSQL+="    INNER JOIN tbm_detMovInv as a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_Doc)";   
                strSQL+="    INNER JOIN tbm_inv AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm)";
                strSQL+="    INNER JOIN tbm_equInv AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_itm=a3.co_itm) ";
                strSQL+="    LEFT OUTER JOIN ";
                strSQL+="    (";
                strSQL+=" 	SELECT a.co_empRelCabMovInv, a.co_locRelCabMovInv, a.co_tipDocRelCabMovInv, a.co_docRelCabMovInv, a4.co_itmMae, SUM(a2.nd_can) as nd_canSol";	
                strSQL+=" 	FROM tbr_cabSolTraInvCabMovInv as a";
                strSQL+=" 	INNER JOIN tbm_cabSolTraInv as a1";
                strSQL+=" 	ON a.co_empRelCabSolTraInv=a1.co_emp AND a.co_locRelCabSolTraInv=a1.co_loc AND a.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a.co_docRelCabSolTraInv=a1.co_doc";
                strSQL+=" 	INNER JOIN tbm_detSolTraInv as a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_Doc)";
                strSQL+=" 	INNER JOIN tbm_inv AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm)";
                strSQL+="       INNER JOIN tbm_equInv AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_itm=a3.co_itm)";
                strSQL+=" 	WHERE  a1.st_reg IN ('A') AND a1.st_aut NOT IN ('D') ";
                strSQL+=" 	AND a1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_loc="+intCodLocGrp+" and co_mnu IN("+INT_COD_MNU_SOTRINI+", "+INT_COD_MNU_SOTRINC+") )";
                strSQL+="       AND a.co_tipDocRelCabMovInv IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_loc="+intCodLocGrp+" and co_mnu="+INT_COD_MNU_PRG_AJU_INV+" )";
                strSQL+=" 	GROUP BY a.co_empRelCabMovInv, a.co_locRelCabMovInv, a.co_tipDocRelCabMovInv, a.co_docRelCabMovInv, a4.co_itmMae";
                strSQL+="    ) as a5 ON (a5.co_empRelCabMovInv=a1.co_emp AND a5.co_locRelCabMovInv=a1.co_loc AND a5.co_tipDocRelCabMovInv=a1.co_tipDoc AND a5.co_docRelCabMovInv=a1.co_Doc AND a5.co_itmMae=a4.co_itmMae)";   
                strSQL+="    INNER JOIN tbm_cabTipDoc AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_loc=a1.co_loc AND a6.co_tipDoc=a1.co_tipDoc AND a1.co_mnu="+INT_COD_MNU_PRG_AJU_INV+" )";
                strSQL+="    WHERE a1.st_Reg IN ('A') AND a2.nd_Can>0 ";
                strSQL+="    AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="    AND (CASE WHEN a1.st_aut IS NULL THEN 'P' ELSE a1.st_aut END) IN ('A')";
                strSQL+="    GROUP BY a1.co_Emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.tx_desCor, a6.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc , a2.nd_can, a5.nd_CanSol";
                strSQL+=" ) AS a ";
                strSQL+=" WHERE a.nd_Can<>a.nd_canSol";
                strSQL+=" GROUP BY a.co_Emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.tx_numDoc2, a.fe_doc, a.ne_diaPen";
                strSQL+=" ORDER BY a.ne_diaPen DESC, a.co_Emp, a.co_loc, a.co_tipDoc, a.tx_numDoc2";                
                
                System.out.println("strMsjDocAjuAutSinSol: " + strSQL);
                rst=stm.executeQuery(strSQL);
                strDet="";
                while (rst.next())
                {
                    if(rst.getString("st_doc").equals("R"))
                    {
                        strDet+="<tr bgcolor='#FF4000'> ";//Rojo
                    }
                    else if(rst.getString("st_doc").equals("A"))
                    {
                        strDet+="<tr bgcolor='#FFFF00'> ";//Amarillo
                    }
                    else if(rst.getString("st_doc").equals("V"))
                    {
                        strDet+="<tr bgcolor='#D0F5A9'> "; //Verde
                    }
                    strDet+="  <td> "+rst.getString("fe_doc")    +" </td>";
                    strDet+="  <td> "+rst.getString("tx_desCor") +" </td>";
                    strDet+="  <td> "+rst.getString("ne_numDoc") +" </td>";
                    strDet+="  <td> "+rst.getString("tx_numDoc2")+" </td>";
                    strDet+="  <td> "+(rst.getString("ne_diaPen")==null ?"0":rst.getString("ne_diaPen")) +" </td>";
                    strDet+="</tr>";
                }
                strFin=" </TABLE><BR> <HTML>";

                //Arma el correo.
                if(strDet.length()>0)
                {
                    strMsj+=strCab+strDet+strFin;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }   
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            strMsj="";
        }        
        catch (Exception e)   {
           System.err.println("ERROR: " + e);
           strMsj="";
        }
        return strMsj;
    }
    
    /**
     * Función obtiene Mensaje para las Notificaciones de documentos de ajustes pendientes de autorización.
     * Envia notificaciones a apaulson.
     * @return 
     */
    public String strMsjDocAjuPenAut()
    {
        String strMsj="", strCab="", strDet="", strFin="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Arma Cabecera Tabla
                strCab+="<HTML> Notificaciones Sistema de Importaciones. <BR><BR>" ;
                strCab+=" <TABLE BORDER=1> ";   
                strCab+="   <tr> <td> FECHA DOCUMENTO </td> <td>TIPO DOCUMENTO AJUSTE</td><td> NUM.DOC.AJU.</td><td> PEDIDO ORIGEN </td> <td> DIAS PENDIENTES </td> </tr>";  
                //Arma Detalle Tabla
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.fe_doc, a.tx_desCor, a.ne_numDoc, a.tx_numDoc2, a.ne_diaPen";
                strSQL+="      , CASE WHEN ( a.ne_diaPen ) <= 11 THEN 'V'  ELSE CASE WHEN (a.ne_diaPen > 11 AND a.ne_diaPen < 15 ) THEN 'A' ELSE 'R' END END as st_doc";
                strSQL+=" FROM (";
                strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                strSQL+="         , substring(a1.tx_numDoc2 from 1 for LENGTH(a1.tx_numDoc2)-3) as tx_numDoc2";
                strSQL+="         , a1.fe_doc, a1.nd_tot, a3.tx_desCor, a3.tx_desLar";
                strSQL+="         ,(current_Date - a1.fe_doc )as ne_diaPen ";
                strSQL+="    FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+="    INNER JOIN tbr_cabMovInv AS a2";
                strSQL+="    ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="    WHERE a1.st_aut IN('P','E') AND a1.st_reg='O' AND a1.st_ingImp='U' ";
                strSQL+="    AND a1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+="                         and co_loc="+intCodLocGrp+" and co_mnu="+INT_COD_MNU_PRG_AJU_INV+" )";
                strSQL+=") AS a ";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.fe_doc, a.tx_desCor, a.ne_numDoc, a.tx_numDoc2, a.ne_diaPen";
                strSQL+=" ORDER BY a.ne_diaPen DESC, a.co_Emp, a.co_loc, a.co_tipDoc, a.tx_numDoc2";                
                System.out.println("strMsjDocAjuPenAut: " + strSQL);
                rst=stm.executeQuery(strSQL);
                strDet="";
                while (rst.next())
                {
                    if(rst.getString("st_doc").equals("R"))
                    {
                        strDet+="<tr bgcolor='#FF4000'> ";//Rojo
                    }
                    else if(rst.getString("st_doc").equals("A"))
                    {
                        strDet+="<tr bgcolor='#FFFF00'> ";//Amarillo
                    }
                    else if(rst.getString("st_doc").equals("V"))
                    {
                        strDet+="<tr bgcolor='#D0F5A9'> "; //Verde
                    }
                    System.out.println("si hay datos1111: "+strDet);
                    strDet+="  <td> "+rst.getString("fe_doc")    +" </td>";
                    strDet+="  <td> "+rst.getString("tx_desCor") +" </td>";
                    strDet+="  <td> "+rst.getString("ne_numDoc") +" </td>";
                    strDet+="  <td> "+rst.getString("tx_numDoc2")+" </td>";
                    strDet+="  <td> "+(rst.getString("ne_diaPen")==null ?"0":rst.getString("ne_diaPen")) +" </td>";
                    strDet+="</tr>";
                }
                strFin=" </TABLE><BR> <HTML>";
              
                //Arma el correo.
                if(strDet.length()>0)
                {
                    strMsj+=strCab+strDet+strFin;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }  
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            strMsj="";
        }          
        catch (Exception e)  {
           System.err.println("ERROR: " + e);
           strMsj="";
        }
        return strMsj;
         
    }       
     
    

    
}
