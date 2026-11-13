/*
 * ZafCom123.java
 *
 * Created on Marzo 9, 2015, 17:30 PM
 */
package Compras.ZafCom123;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEleGP.ZafCorEleGP;
import Librerias.ZafDate.ZafDatePicker; 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis; 
import Librerias.ZafTblUti.ZafTblCelEdiButVcoGP.ZafTblCelEdiButVcoGP;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;  
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.Color;
import java.awt.Font;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín
 */
public class ZafCom123 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_ITM=1;
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;
    private static final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;
    private static final int INT_TBL_DAT_BTN_ITM=4;
    private static final int INT_TBL_DAT_NOM_ITM=5;
    private static final int INT_TBL_DAT_UNI_MED=6;
    private static final int INT_TBL_DAT_CAN_ING=7;
    private static final int INT_TBL_DAT_CAN_EGR=8;
    private static final int INT_TBL_DAT_PES_UNI_ITM=9;
    private static final int INT_TBL_DAT_PES_TOT_ITM=10;
    private static final int INT_TBL_DAT_CAN_ACT_STK=11; 
    private static final int INT_TBL_DAT_CAN_COST=12; 
    private static final int INT_TBL_DAT_CAN_TOT_COST_ING=13; 
    private static final int INT_TBL_DAT_CAN_TOT_COST_EGR=14; 
    
    // Constantes: Para el contenedor a enviar de solicitud de transferencia (Prefactura, Reservas) JoséMario 26/Abril/2015.
    final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
    final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
    final int INT_ARL_SOL_TRA_ITM_GRP=2;
    final int INT_ARL_SOL_TRA_CAN=3;
    final int INT_ARL_SOL_PES_UNI=4;
    final int INT_ARL_SOL_PES_TOT=5;
    
    //Constantes: Para el contenedor a enviar de solicitud de transferencia (Cotizaciones de Venta) JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=3;
    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    

    
    // Constantes: Para el contenedor solicitud de transferencia que llegara desde otro programa (Reposicion).
    final int INT_ARL_GEN_SOL_TRA_ITM_GRP=0;
    final int INT_ARL_GEN_SOL_TRA_CAN=1;
    final int INT_ARL_GEN_SOL_PES_UNI=2;
    final int INT_ARL_GEN_SOL_PES_TOT=3;
    final int INT_ARL_GEN_SOL_PES_TOT_SOLTRA=4;
    

    //ArrayList para consultar
    private ArrayList arlDatConSol, arlRegConSol;
    private static final int INT_ARL_CON_SOL_TRA_COD_TIPDOC=0;  
    private static final int INT_ARL_CON_SOL_TRA_COD_DOC=1;  
    private int intIndiceSolTra=0;
    

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoCodAltItm;     //Editor: TextField en celda.
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafDatePicker dtpFecDoc;                            //Fecha

    private ZafThreadGUI objThrGUI;
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    
////    //Para las Ventanas de Consulta
////    private ZafVenCon vcoTipDoc;                                // tipo de documento
////    private ZafVenCon vcoBodOrg;                                // bodega de origen 
////    private ZafVenCon vcoBodDes;                                // bodega de destino 
    
    //Para las Ventanas de Consulta
    private ZafVenConGP vcoTipDoc;                                // tipo de documento
    private ZafVenConGP vcoBodOrg;                                // bodega de origen 
    private ZafVenConGP vcoBodDes;                                // bodega de destino 
//    private ZafVenCon vcoItm;                                   // Items 
    private ZafVenConGP vcoItm;                                   // Items 
    
    private Date datFecAux;
    private Date datFecDoc;  
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private boolean blnHayCam;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    private int intCodBodOrg=0;
    private int intCodEmpSolTra;
    private int intCodLocSolTra;
    
    private String strSQL;
    private String strAux;
    private String strDocCod;
    private String strCodBodOrg;
    private String strCodBodDes;
    private String strNomBodOrg;
    private String strNomBodDes;
    private String strDocNom;
    private String strDesCorTipDoc;
    
    private int intCodEmpIngImpLoc;
    private int intCodLocIngImpLoc;
    private int intCodTipDocIngImpLoc;
    private int intCodDocIngImpLoc;
    private String strNumPedEmbImp="";
    
    private int intCodEmpRepInv;
    private int intCodLocRepInv;
    private int intCodTipDocRepInv;
    private int intCodDocRepInv;
    private String strMoTrans="CONV";
    private int intCodSol=0;
    private boolean opEje=false;
    
    private String strVersion="v1.2";   
    private String tx_formato="", tx_formatoDoc="",tx_motivo="",tx_forLot="",tx_tipo="";
    private int nd_tipo_doc =0;
    private int nd_secuencia=0,nd_secLot=0;    
    private int codMenu =0;
    private ZafTblCelEdiTxt objTblCelEdiTxtEgr, objTblCelEdiTxtIng;   
    private String strEstadoDoc ="";
    private ArrayList arlDatITEMDET,arlRegITEMDET;
    private ZafCorEleGP objRepCorEleGP;
    private StringBuilder htmlGlo = new StringBuilder(); 
    
    /** Creates new form ZafCom123 */
    public ZafCom123(ZafParSis obj) 
    {
        try
        {
            
            objParSis=(ZafParSis)obj.clone();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
            codMenu =objParSis.getCodigoMenu();
            
            
            if (codMenu==4861)
                opEje=false;
   
            if (codMenu==4875)
                opEje=true;            
         
            //AQUI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){  
                initComponents();
                //txtCodTipDoc.setText("269");
                configurarFrm();
                agregarDocLis();
                butTipDoc.setEnabled(false);  
                  
                
               // strMoTrans ="TRANSFER";
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            } 
            
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }

private String escaparHtml(String texto)
{
    if (texto == null)
    {
        return "";
    }

    return texto
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;");
}

private BigDecimal obtenerDecimalTabla(int fila, int columna)
{
    Object valor = objTblMod.getValueAt(fila, columna);

    if (valor == null)
    {
        return BigDecimal.ZERO;
    }

    String texto = valor.toString().trim();

    if (texto.isEmpty() || texto.equalsIgnoreCase("null"))
    {
        return BigDecimal.ZERO;
    }

    try
    {
        /*
         * Se cambia la coma por punto en caso de que el JTable
         * muestre decimales con coma.
         */
        texto = texto.replace(",", ".");

        return new BigDecimal(texto);
    }
    catch (NumberFormatException e)
    {
        return BigDecimal.ZERO;
    }
}
private String formatearDecimal(BigDecimal valor)
{
    if (valor == null)
    {
        return "0.00";
    }

    return valor
        .setScale(2, RoundingMode.HALF_UP)
        .toPlainString();
}    

private StringBuilder generarHtmlDetalleSolicitud(int tipo, int codigo)
{
    if (tipo != 0 && tipo != 1)
    {
        throw new IllegalArgumentException(
            "El tipo de HTML debe ser 0 o 1."
        );
    }

    boolean esAprobacion = (tipo == 1);

    StringBuilder html = new StringBuilder();

    BigDecimal totalCostoIngreso = BigDecimal.ZERO;
    BigDecimal totalCostoEgreso = BigDecimal.ZERO;

    html.append("<html>");
    html.append("<head>");
    html.append("<style>");

    html.append("body {");
    html.append("    font-family: Arial, sans-serif;");
    html.append("    font-size: 12px;");
    html.append("    color: #000000;");
    html.append("}");

    html.append("table {");
    html.append("    border-collapse: collapse;");
    html.append(
        esAprobacion
            ? "    width: 550px;"
            : "    width: 750px;"
    );
    html.append("}");

    html.append("th, td {");
    html.append("    border: 1px solid #808080;");
    html.append("    padding: 5px;");
    html.append("}");

    html.append("th {");
    html.append("    background-color: #f2f2f2;");
    html.append("    text-align: center;");
    html.append("    font-weight: bold;");
    html.append("}");

    html.append(".titulo {");
    html.append("    font-size: 16px;");
    html.append("    font-weight: bold;");
    html.append("    margin-bottom: 15px;");
    html.append("}");

    html.append(".datos {");
    html.append("    margin-bottom: 20px;");
    html.append("}");

    html.append(".numero {");
    html.append("    text-align: right;");
    html.append("}");

    html.append(".centro {");
    html.append("    text-align: center;");
    html.append("}");

    html.append(".ingreso {");
    html.append("    background-color: #dff0d8;");
    html.append("}");

    html.append(".egreso {");
    html.append("    background-color: #f2dede;");
    html.append("}");

    html.append(".total {");
    html.append("    background-color: #d9edf7;");
    html.append("    font-weight: bold;");
    html.append("}");

    html.append("</style>");
    html.append("</head>");

    html.append("<body>");

    /* ============================================================
       TÍTULO
       ============================================================ */

    html.append("<div class='titulo'>");

    if (esAprobacion)
    {
        html.append("Detalle de aprobacion de conversion");
    }
    else
    {
        html.append("Detalle de solicitud de conversion");
    }

    html.append("</div>");

    /* ============================================================
       DATOS GENERALES
       ============================================================ */

    html.append("<div class='datos'>");

    html.append("<b>Numero de solicitud:</b> ");
    html.append(codigo);
    html.append("<br>");

    html.append("<b>Usuario:</b> ");
    html.append(
        escaparHtml(
            objParSis.getNombreUsuario()
        )
    );

    html.append("</div>");

    /* ============================================================
       TABLA
       ============================================================ */

    html.append("<table>");

    if (esAprobacion)
    {
        /*
         * TIPO 1:
         * REG. | ÍTEM | TIPO | CANTIDAD
         */
        html.append("<tr>");

        html.append("<th style='width:55px;'>REG.</th>");
        html.append("<th style='width:220px;'>ITEM</th>");
        html.append("<th style='width:120px;'>TIPO</th>");
        html.append("<th style='width:120px;'>CANTIDAD</th>");

        html.append("</tr>");
    }
    else
    {
        /*
         * TIPO 0:
         * Formato completo con costos.
         */
        html.append("<tr>");

        html.append("<th rowspan='2' style='width:55px;'>");
        html.append("REG.");
        html.append("</th>");

        html.append("<th rowspan='2' style='width:180px;'>");
        html.append("ITEM");
        html.append("</th>");

        html.append("<th rowspan='2' style='width:100px;'>");
        html.append("TIPO");
        html.append("</th>");

        html.append("<th rowspan='2' style='width:100px;'>");
        html.append("CANTIDAD");
        html.append("</th>");

        html.append("<th colspan='2'>");
        html.append("COSTO");
        html.append("</th>");

        html.append("</tr>");

        html.append("<tr>");

        html.append("<th style='width:120px;'>");
        html.append("UNITARIO");
        html.append("</th>");

        html.append("<th style='width:120px;'>");
        html.append("TOTAL");
        html.append("</th>");

        html.append("</tr>");
    }

    /* ============================================================
       DETALLE
       ============================================================ */

    int numeroRegistro = 0;

    for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
    {
        BigDecimal cantidadIngreso = obtenerDecimalTabla(
            i,
            INT_TBL_DAT_CAN_ING
        );

        BigDecimal cantidadEgreso = obtenerDecimalTabla(
            i,
            INT_TBL_DAT_CAN_EGR
        );

        BigDecimal cantidadMovimiento;
        BigDecimal costoUnitario = BigDecimal.ZERO;
        BigDecimal costoTotal = BigDecimal.ZERO;

        String tipoMovimiento;
        String claseFila;

        if (cantidadIngreso.compareTo(BigDecimal.ZERO) != 0)
        {
            tipoMovimiento = "INGRESO";
            claseFila = "ingreso";
            cantidadMovimiento = cantidadIngreso;

            if (!esAprobacion)
            {
                costoUnitario = obtenerDecimalTabla(
                    i,
                    INT_TBL_DAT_CAN_COST
                ).abs();

                costoTotal = obtenerDecimalTabla(
                    i,
                    INT_TBL_DAT_CAN_TOT_COST_ING
                ).abs();

                totalCostoIngreso = totalCostoIngreso.add(
                    costoTotal
                );
            }
        }
        else if (cantidadEgreso.compareTo(BigDecimal.ZERO) != 0)
        {
            tipoMovimiento = "EGRESO";
            claseFila = "egreso";

            // El egreso se muestra con cantidad negativa.
            cantidadMovimiento = cantidadEgreso.abs().negate();

            if (!esAprobacion)
            {
                costoUnitario = obtenerDecimalTabla(
                    i,
                    INT_TBL_DAT_CAN_COST
                ).abs();

                costoTotal = obtenerDecimalTabla(
                    i,
                    INT_TBL_DAT_CAN_TOT_COST_EGR
                ).abs();

                totalCostoEgreso = totalCostoEgreso.add(
                    costoTotal
                );
            }
        }
        else
        {
            tipoMovimiento = "SIN MOVIMIENTO";
            claseFila = "";
            cantidadMovimiento = BigDecimal.ZERO;
        }

        numeroRegistro++;

        Object objItem = objTblMod.getValueAt(
            i,
            INT_TBL_DAT_COD_ALT_ITM
        );

        String item = objItem == null
            ? ""
            : objItem.toString().trim();

        if (!claseFila.isEmpty())
        {
            html.append("<tr class='");
            html.append(claseFila);
            html.append("'>");
        }
        else
        {
            html.append("<tr>");
        }

        /* REGISTRO */

        html.append("<td class='centro'>");
        html.append(numeroRegistro);
        html.append("</td>");

        /* ÍTEM */

        html.append("<td>");
        html.append(
            escaparHtml(item)
        );
        html.append("</td>");

        /* TIPO */

        html.append("<td class='centro'>");
        html.append(tipoMovimiento);
        html.append("</td>");

        /* CANTIDAD */

        html.append("<td class='numero'>");
        html.append(
            formatearDecimal(cantidadMovimiento)
        );
        html.append("</td>");

        /*
         * Las columnas de costos solo se agregan cuando tipo = 0.
         */
        if (!esAprobacion)
        {
            html.append("<td class='numero'>");
            html.append(
                formatearDecimal(costoUnitario)
            );
            html.append("</td>");

            html.append("<td class='numero'>");
            html.append(
                formatearDecimal(costoTotal)
            );
            html.append("</td>");
        }

        html.append("</tr>");
    }

    /* ============================================================
       TOTALES: SOLO PARA TIPO 0
       ============================================================ */

    if (!esAprobacion)
    {
        html.append("<tr class='total'>");

        html.append("<td colspan='5'>");
        html.append("TOTAL DE INGRESOS");
        html.append("</td>");

        html.append("<td class='numero'>");
        html.append(
            formatearDecimal(totalCostoIngreso)
        );
        html.append("</td>");

        html.append("</tr>");

        html.append("<tr class='total'>");

        html.append("<td colspan='5'>");
        html.append("TOTAL DE EGRESOS");
        html.append("</td>");

        html.append("<td class='numero'>");
        html.append(
            formatearDecimal(totalCostoEgreso)
        );
        html.append("</td>");

        html.append("</tr>");
    }

    html.append("</table>");
    html.append("</body>");
    html.append("</html>");

    return html;
}
    
    private boolean isVersionActualizada()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false; 
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+codMenu;
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    System.out.println("Version " + strVersion);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(strVersion.equals(rstLoc.getString("tx_ver"))){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private int intIsCotVen=0;
    
    /* Ventana de Cotizaciones de Venta */
    public ZafCom123(ZafParSis obj, int j) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
             objUti=new ZafUtil();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
            intIsCotVen=j;
            
            initComponents();
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 
//            configurarFrm();
//            agregarDocLis();
           
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }
    
    /* Constructor para Botón Consultar Solicitud de Transferencia de Inventario */
    public ZafCom123(ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try
        {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText("Solicitudes de Transferencia de Inventario");

            intCodEmpSolTra = Integer.parseInt(strCodEmp);
            intCodLocSolTra = Integer.parseInt(strCodLoc);
            txtCodTipDoc.setText(strCodTipDoc);
            strMoTrans =strCodTipDoc;
            txtCodDoc.setText(strCodDoc);
            
            panBar.setVisible(false);
            consultarReg();
           // btnCopiar.setEnabled(true);
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    }
     
    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author Rosa Zambrano
     * @fecha 12/Jun/2017
     * @param map 
     */
    public ZafCom123(HashMap map) 
    {
        ZafParSis obj;
        Integer codigoEmp, codigoLoc;
        Integer codigoTipDoc, codigoDoc;
        
        obj = (ZafParSis) map.get("objParSis");
        codigoEmp = Integer.valueOf(new String(map.get("strCodEmp").toString()));
        codigoLoc = Integer.valueOf(new String(map.get("strCodLoc").toString()));
        codigoTipDoc = Integer.valueOf(new String(map.get("strCodTipDoc").toString()));
        codigoDoc = Integer.valueOf(new String(map.get("strCodDoc").toString()));
        
        try
        {
            //this.objParSis = (ZafParSis) obj.clone();
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText("Solicitudes de Transferencia de Inventario");

            intCodEmpSolTra = codigoEmp;
            intCodLocSolTra = codigoLoc;
            txtCodTipDoc.setText(""+codigoTipDoc);
            txtCodDoc.setText(""+codigoDoc);
            
            panBar.setVisible(false);
            consultarReg();
           // btnCopiar.setEnabled(true);
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    }    
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtNomTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblDiferencia = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblCostIng = new javax.swing.JLabel();
        txtCostIng = new javax.swing.JTextField();
        lblCostEgr = new javax.swing.JLabel();
        txtCostEgr = new javax.swing.JTextField();
        lblCodDoc2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCopiar = new javax.swing.JButton();
        btnRecalcular = new javax.swing.JButton();
        panDet = new javax.swing.JPanel();
        panTabDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObsSo = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        lblDiferencia1 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setMinimumSize(new java.awt.Dimension(148, 113));

        panFil.setPreferredSize(new java.awt.Dimension(0, 330));
        panFil.setLayout(new java.awt.BorderLayout());

        panCab.setMinimumSize(new java.awt.Dimension(0, 0));
        panCab.setPreferredSize(new java.awt.Dimension(0, 95));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(5, 10, 100, 20);

        txtCodTipDoc.setEnabled(false);
        txtCodTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipDocFocusLost(evt);
            }
        });
        txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 10, 20, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 90, 20);

        txtNomTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipDocFocusLost(evt);
            }
        });
        txtNomTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtNomTipDoc);
        txtNomTipDoc.setBounds(233, 10, 170, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(403, 10, 20, 20);

        lblBod.setText("Bodega Origen:");
        panCab.add(lblBod);
        lblBod.setBounds(5, 30, 130, 20);

        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panCab.add(txtCodBod);
        txtCodBod.setBounds(140, 30, 90, 20);

        txtNomBod.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panCab.add(txtNomBod);
        txtNomBod.setBounds(233, 30, 170, 20);

        butBod.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCab.add(butBod);
        butBod.setBounds(403, 30, 20, 20);
        panCab.add(lblDiferencia);
        lblDiferencia.setBounds(0, 70, 410, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(140, 50, 90, 20);

        lblFecDoc.setText("Fecha del documento:");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(425, 10, 140, 20);

        lblNumDoc.setText("Número del documento:");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(425, 30, 150, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(575, 30, 90, 20);

        lblCostIng.setText("Total Costo Ingreso:");
        panCab.add(lblCostIng);
        lblCostIng.setBounds(425, 50, 130, 20);

        txtCostIng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCostIng);
        txtCostIng.setBounds(575, 51, 90, 20);

        lblCostEgr.setText("Total Costo Egreso:");
        panCab.add(lblCostEgr);
        lblCostEgr.setBounds(425, 70, 130, 20);

        txtCostEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCostEgr);
        txtCostEgr.setBounds(575, 72, 90, 20);

        lblCodDoc2.setText("Código del documento:");
        panCab.add(lblCodDoc2);
        lblCodDoc2.setBounds(5, 50, 130, 20);
        panCab.add(jPanel1);
        jPanel1.setBounds(510, -10, 10, 10);

        btnCopiar.setText("Copiar");
        btnCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiarActionPerformed(evt);
            }
        });
        panCab.add(btnCopiar);
        btnCopiar.setBounds(0, 70, 110, 23);

        btnRecalcular.setText("Recalcular");
        btnRecalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecalcularActionPerformed(evt);
            }
        });
        panCab.add(btnRecalcular);
        btnRecalcular.setBounds(110, 70, 110, 23);

        panFil.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

        panTabDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panTabDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panDet.add(panTabDet, java.awt.BorderLayout.CENTER);

        panFil.add(panDet, java.awt.BorderLayout.CENTER);

        panGenTot.setMinimumSize(new java.awt.Dimension(70, 30));
        panGenTot.setPreferredSize(new java.awt.Dimension(34, 58));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panLblObs.add(lblObs2);

        panGenTot.add(panLblObs, java.awt.BorderLayout.WEST);

        panTxtObs.setPreferredSize(new java.awt.Dimension(579, 70));
        panTxtObs.setLayout(new java.awt.GridLayout(0, 1));

        txaObsSo.setLineWrap(true);
        txaObsSo.setPreferredSize(new java.awt.Dimension(104, 25));
        spnObs1.setViewportView(txaObsSo);

        panTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        txaObs2.setPreferredSize(new java.awt.Dimension(104, 25));
        spnObs2.setViewportView(txaObs2);

        panTxtObs.add(spnObs2);

        panGenTot.add(panTxtObs, java.awt.BorderLayout.CENTER);

        panFil.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("General");

        panBar.setPreferredSize(new java.awt.Dimension(0, 58));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);
        getContentPane().add(lblDiferencia1, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void DesactivarActivar (boolean estado)
{
    txtDesCorTipDoc.setEnabled(estado);
    txtNomTipDoc.setEnabled(estado);
    txtCodBod.setEnabled(estado);
    txtNomBod.setEnabled(estado);
    butTipDoc.setEnabled(estado);
    butBod.setEnabled(estado);
    dtpFecDoc.setEnabled(estado);  
    txtCodDoc.setEnabled(estado);
    txtNumDoc.setEnabled(estado);
    txtCostIng.setEnabled(estado);
    txtCostEgr.setEnabled(estado);
 
 }
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        if(objTooBar.getEstado()=='c')
        {

        }
        else
        {
            clickCambiaTipoDocumento();
        }
        
        strDocCod=txtCodTipDoc.getText();
        mostrarDocumentos(0);
        
        /* JoséMario 18/Agosto/2016 */
        configurarVenBodOrg();
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNomTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusLost
        if (codMenu==4861)
        {
            if( ! (objTooBar.getEstado()=='c' || objTooBar.getEstado()=='l'))
            {
                limpiarFrm();
                clickCambiaTipoDocumento();
            }
            if (!txtNomTipDoc.getText().equalsIgnoreCase(strDocNom))
            {
                if (txtNomTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtNomTipDoc.setText("");
                }
                else
                {
                    mostrarDocumentos(2);
                }
            }
            else{
                txtNomTipDoc.setText(strDocNom);
            }
        }
        else
        {
            if (codMenu!=4875)
            {
                /* JoséMario 18/Agosto/2016 */
                txtCodBod.setText("");
                configurarVenBodOrg();                
            }
                
        }    
            


        
    }//GEN-LAST:event_txtNomTipDocFocusLost

    private void txtNomTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusGained
        strDocNom=txtNomTipDoc.getText();
        txtNomTipDoc.selectAll();
    }//GEN-LAST:event_txtNomTipDocFocusGained

    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        txtNomTipDoc.transferFocus();
    }//GEN-LAST:event_txtNomTipDocActionPerformed

    private void txtCodTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipDocFocusLost
        if (!txtCodTipDoc.getText().equalsIgnoreCase(strDocCod)){
            if (txtCodTipDoc.getText().equals("")){
                txtCodTipDoc.setText("");
                txtNomTipDoc.setText("");
            }
            else
            mostrarDocumentos(1);
        }
        else
        txtCodTipDoc.setText(strDocCod);
    }//GEN-LAST:event_txtCodTipDocFocusLost

    private void txtCodTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipDocFocusGained
        strDocCod=txtCodTipDoc.getText();
        txtCodTipDoc.selectAll();
    }//GEN-LAST:event_txtCodTipDocFocusGained

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        txtCodTipDoc.transferFocus();
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBodOrg=txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.

            if (codMenu==4861)
            {
                if (txtCodTipDoc.getText().equals("")) 
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                    txtDesCorTipDoc.requestFocus();
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    if (!txtCodBod.getText().equalsIgnoreCase(strCodBodOrg)) 
                    {
                        if (txtCodBod.getText().equals(""))
                        {
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        } 
                        else 
                        {
                            configurarVenBodOrg();
                            mostrarBodegaOrigen(1);
                            limpiarTabla();
                        }
                    } 
                    else
                        txtCodBod.setText(strCodBodOrg);
                 } 
            }            
       


        
////        if(txtCodBod.getText().length()>0){
////            isBodegaOrigen();
////        }
//            if(txtCodBodOrg.getText().length()>0){
//                configurarVenConItm();
//            }
         
         
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBodOrg=txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (codMenu==4861)
        {
            //Validar el contenido de la celda sálo si ha cambiado.
            if (txtCodTipDoc.getText().equals("")) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
                txtCodBod.setText("");
                txtNomBod.setText("");
            }
            else
            {
                if (!txtNomBod.getText().equalsIgnoreCase(strNomBodOrg)) 
                {
                    if (txtNomBod.getText().equals("")) 
                    {
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    } 
                    else 
                    {
                        configurarVenBodOrg();
                        mostrarBodegaOrigen(2);
                        limpiarTabla();
                    }
                }
                else
                    txtNomBod.setText(strNomBodOrg);
            }            
        }
 
        
////        if(txtCodBod.getText().length()>0){
////            isBodegaOrigen();
////        }
   
    }//GEN-LAST:event_txtNomBodFocusLost

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBod.setText("");
            txtNomBod.setText("");
        }
        else
        {
            strCodBodOrg=txtCodBod.getText();
            configurarVenBodOrg();
            mostrarBodegaOrigen(0);
        
            if (! txtCodBod.getText().equals(strCodBodOrg)) {
                limpiarTabla();
            }

////            if(txtCodBod.getText().length()>0){
////                isBodegaOrigen();
////            }
        }
        
    }//GEN-LAST:event_butBodActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    if (codMenu==4861)
    {
        if( ! (objTooBar.getEstado()=='c' || objTooBar.getEstado()=='l'))
                {
                    limpiarFrm();
                    clickCambiaTipoDocumento();
                }

                if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                {
                    if (txtDesCorTipDoc.getText().equals(""))
                    {
                        txtCodTipDoc.setText("");
                        txtDesCorTipDoc.setText("");
                        txtNomTipDoc.setText("");
                    }
                    else
                    {
                        mostrarDocumentos(1);
                    }
                }
                else {
                    txtDesCorTipDoc.setText(strDesCorTipDoc);
                }     
                /* JoséMario 18/Agosto/2016 */
                configurarVenBodOrg();
    }

        
        
    }//GEN-LAST:event_txtDesCorTipDocFocusLost
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void btnCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiarActionPerformed
        // TODO add your handling code here:
        String strCodBodega ="",strBodega="";
        int cod_doc =0;
        
            
        if ((tblDat.getRowCount() > 0) && (!txtCodDoc.getText().equals("0"))) 
        {
           if (mostrarMsgCon("¿Está seguro que desea realizar esta operación de copiado ?")==0)
           {
             strCodBodega=txtCodBod.getText();
             strBodega=txtNomBod.getText();
             cod_doc= Integer.parseInt(txtCodDoc.getText());
             txtCodDoc.setText("0");
             txtNumDoc.setText("0");
             objTooBar.setEstado('n');
             if (cargarCopiada(cod_doc))
                System.out.println("La tabla está vacía.");
             else
                 System.out.println("La tabla está vacía.");
             
             
             
           }
            
        } else {
            System.out.println("La tabla está vacía.");
        }
            
    }//GEN-LAST:event_btnCopiarActionPerformed

    private void btnRecalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecalcularActionPerformed
      if (tblDat.getRowCount() > 0)
      {
        if (RecalcularCosto())
            mostrarMsgInf("Se realizo la actualizacion de los costos");          
          
      }

      
 


      
    }//GEN-LAST:event_btnRecalcularActionPerformed

    private boolean RecalcularCosto()
    {
        boolean blnRes=false;
        String menErr="ERROR AL RECOSTEAR ";
        try{
           con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null)
            {
              if(RecalcularCostoEgreso(con,Integer.parseInt(txtCodDoc.getText())))
                        blnRes=true;                  
              else
                    mostrarMsgInf(menErr);
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    



        
        
    
private boolean RecalcularCostoEgreso(java.sql.Connection conn,  int co_doc)//insertachiqui
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
         double dblCantEgre=0,dblCantIngr=0;
         String tx_itm="";
        intCodSol=0;
        double dblCosto =0;
        double dblCostoTotal =0,dblCantidad=0;
        try
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) 
                {
                    if (objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()) > 0) 
                    {
                        tx_itm = objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                        strSQL = "";
                        strSQL += " SELECT ISNULL(CURRCOST,0) as costoActual "
                               + " FROM " + objParSis.getNombreBaseDatosGP() + "..iv00101 "
                               + " WHERE ITEMNMBR = '" + tx_itm + "'\n";
                        System.out.println("RecalcularCostoEgreso: " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        if (rstLoc.next()) 
                        {
                            dblCantidad = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString());
                            dblCosto = rstLoc.getDouble("costoActual");
                            if (rstLoc.wasNull()) dblCosto = 0.0;
                            dblCostoTotal = objUti.redondear(dblCantidad * dblCosto, objParSis.getDecimalesBaseDatos());
                            objTblMod.setValueAt(dblCosto, i, INT_TBL_DAT_CAN_COST);
                            objTblMod.setValueAt(dblCostoTotal, i, INT_TBL_DAT_CAN_TOT_COST_EGR);
                        } 
            //////            else {
            //////                blnRes = false;
            //////                objTblMod.setValueAt(0, i, INT_TBL_DAT_CAN_COST);
            //////                objTblMod.setValueAt(0, i, INT_TBL_DAT_CAN_TOT_COST_EGR);
            //            }
                    }
                }
                recalcularCosto();
            }
            
            stmLoc=null;
            rstLoc=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }    
    
    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnRecalcular;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc2;
    private javax.swing.JLabel lblCostEgr;
    private javax.swing.JLabel lblCostIng;
    private javax.swing.JLabel lblDiferencia;
    private javax.swing.JLabel lblDiferencia1;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panLblObs;
    private javax.swing.JPanel panTabDet;
    private javax.swing.JPanel panTxtObs;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextArea txaObsSo;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCostEgr;
    private javax.swing.JTextField txtCostIng;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtNumDoc;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            
            
            objRepCorEleGP = new ZafCorEleGP(objParSis);  /*Libreria para enviar correos electronicos a usuario.*/
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion );  //Titulo y version
            lblTit.setText(strAux);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.agregarBoton(btnCopiar);
            objTooBar.agregarBoton(btnRecalcular);
            if (opEje)
            {
                objTooBar.setVisibleModificar(true);  // EN LA BARRA DE OPCIONES SE MOSTRARA EL BOTON DE MODIFICAR 
                objTooBar.setVisibleInsertar(false);
                btnRecalcular.setEnabled(true);
            }
            else
            {
                objTooBar.setVisibleModificar(false);  // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR 
                btnCopiar.setEnabled(true);
                btnRecalcular.setEnabled(false);
                
            }
            
            //Configurar ZafDatePicker: Para la fecha 
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(575, 10, 90, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 
            //fin config zafDatePicker
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());  
            txtNomTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());  // AZUL
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
////            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
////            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
////            txtCostIng.setBackground(objParSis.getColorCamposSistema());          // AMARILLO
            txtCodTipDoc.setVisible(false);                                      //no estaba en el diseño de la pantalla 
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
////            if (!objPerUsr.isOpcionEnabled(3984)) 
////            {
////                objTooBar.setVisibleInsertar(false);
////            }
////            if (!objPerUsr.isOpcionEnabled(3985)) 
////            {
////                objTooBar.setVisibleConsultar(false);
////            }
////            if (!objPerUsr.isOpcionEnabled(3989)) 
////            {
////                objTooBar.setVisibleImprimir(false);
////            }
////            if (!objPerUsr.isOpcionEnabled(3990)) 
////            {
////                objTooBar.setVisibleVistaPreliminar(false);
////            }
            objTooBar.setVisibleImprimir(false);//chiqui
            objTooBar.setVisibleVistaPreliminar(false);//chiqui
            
            configurarVenBodOrg();
            configurarTipoDocumento();
            configurarVenConItm();
            configurarTblDat();
            
            
            
            if (codMenu==4861)
            {
                lblCostIng.setVisible(false);
                lblCostEgr.setVisible(false);
                txtCostIng.setVisible(false);
                txtCostEgr.setVisible(false);
                dtpFecDoc.setEnabled(false);

            }    
                
            if (codMenu==4875)
            {
                lblCostIng.setVisible(true);
                lblCostEgr.setVisible(true);
                txtCostIng.setVisible(true);
                txtCostEgr.setVisible(true);                
                dtpFecDoc.setEnabled(true);
            }            
     
        }
        catch(Exception e){       
            blnRes=false;         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_BTN_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"UN.");
            vecCab.add(INT_TBL_DAT_CAN_ING,"Cant.Ing.");
            vecCab.add(INT_TBL_DAT_CAN_EGR,"Cant.Egr.");
            vecCab.add(INT_TBL_DAT_PES_UNI_ITM,"Pes.Uni.");
            vecCab.add(INT_TBL_DAT_PES_TOT_ITM,"Pes.Tot.");
            vecCab.add(INT_TBL_DAT_CAN_ACT_STK,"Cant.Disp.");
            vecCab.add(INT_TBL_DAT_CAN_COST,"Costo");
            vecCab.add(INT_TBL_DAT_CAN_TOT_COST_ING,"Tot.Cost.Ing");            
            vecCab.add(INT_TBL_DAT_CAN_TOT_COST_EGR,"Tot.Cost.Egr");            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(245);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COST).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_COST_ING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_COST_EGR).setPreferredWidth(70);
          
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafCom123.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            //objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblNum.setFormatoNumerico("#,##0.000000", false, true);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setCellRenderer(objTblCelRenLblNum);  // JoséMarín: NO se lo esta mostrando, se deja configurado por si deciden mostrar
            tcmAux.getColumn(INT_TBL_DAT_CAN_COST).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_COST_EGR).setCellRenderer(objTblCelRenLblNum);            
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            codMenu=objParSis.getCodigoMenu();
            if (codMenu==4861)
            {
                //Configurar JTable: Establecer columnas editables.
                vecAux=new Vector();
                vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
                vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM_DOS);
                vecAux.add("" + INT_TBL_DAT_CAN_ING);
                vecAux.add("" + INT_TBL_DAT_CAN_EGR);
                vecAux.add("" + INT_TBL_DAT_BTN_ITM);
                vecAux.add("" + INT_TBL_DAT_CAN_COST);
                vecAux.add("" + INT_TBL_DAT_CAN_TOT_COST_ING);
                vecAux.add("" + INT_TBL_DAT_CAN_TOT_COST_EGR);
                objTblMod.setColumnasEditables(vecAux);                
            }
                
            if (codMenu==4875)
            {
                //Configurar JTable: Establecer columnas editables.
                vecAux=new Vector();
                vecAux.add("" + INT_TBL_DAT_CAN_COST);  
                
                objTblMod.setColumnasEditables(vecAux);                
            }
            

            vecAux=null;
             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_ITM);
////            arlColHid.add(""+INT_TBL_DAT_CAN_ACT_STK);  // 
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            //objTblMod.setc setColumnDataType(ERROR, WIDTH, WIDTH, WIDTH);
            
            arlColHid=null;
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //setEditable(false);
            
            //new
            int intColVen[]=new int[8];
            intColVen[0]=1; //co_itm
            intColVen[1]=2; //tx_codAlt
            intColVen[2]=3; //tx_codAlt2
            intColVen[3]=4; //tx_nomItm
            intColVen[4]=5; //tx_desCor
            intColVen[5]=6; //nd_pesItmKgr
            intColVen[6]=7; //nd_canDis
            intColVen[7]=8; //nd_costo
           
            int intColTbl[]=new int[8];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM_DOS;
            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
            intColTbl[4]=INT_TBL_DAT_UNI_MED;
            intColTbl[5]=INT_TBL_DAT_PES_UNI_ITM;
            intColTbl[6]=INT_TBL_DAT_CAN_ACT_STK;
            intColTbl[7]=INT_TBL_DAT_CAN_COST;
          
          
            //para que la columna de cantidad no sea negativovcoItm
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_EGR, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Cod.Alt.
            objTblCelEdiTxtVcoCodAltItm=new ZafTblCelEdiTxtVcoGP(tblDat, vcoItm, intColVen, intColTbl,1);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoCodAltItm);
            objTblCelEdiTxtVcoCodAltItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {  
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    //vcoItm.setCampoBusqueda(1);//cambiochiqui
                    vcoItm.setCondicionesSQLSP( txtCodBod.getText()); 
                    vcoItm.setCampoBusquedaSP(1,1);//cambiochiqui                    

                    
                    
////                    vcoItm.setCriterio1(11);
                }                 
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if (!(txtCodBod.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega es Obligatoria para poder elegir los Items");
                        txtCodBod.requestFocus();      
                    }
                }
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    
////                    if(!objTblMod.isRowEmpty(intCelSel))
////                    {
                        if (objTblCelEdiTxtVcoCodAltItm.isConsultaAceptada()) 
                        {
                            if (!existeMismoItem(intCelSel)) 
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_ING);
                        }
//////                        double nd_stoAct=0;
//////                        nd_stoAct=obtenerStockDisponible(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_COD_ALT_ITM).toString(),txtCodBod.getText(),1);
//////                        objTblMod.setValueAt(nd_stoAct, intCelSel, INT_TBL_DAT_CAN_ACT_STK);
////                    }
                }
            });
            
            
//CodALT_2
            objTblCelEdiTxtVcoCodAltItm=new ZafTblCelEdiTxtVcoGP(tblDat, vcoItm, intColVen, intColTbl,1);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setCellEditor(objTblCelEdiTxtVcoCodAltItm);
            objTblCelEdiTxtVcoCodAltItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {  
                
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    vcoItm.setCondicionesSQLSP( txtCodBod.getText()); 
                    vcoItm.setCampoBusquedaSP(2,1);//cambiochiqui
                   
//                    vcoItm.setCriterio1(11); 
                }                
                

                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if (!(txtCodBod.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega es Obligatoria para poder elegir los Items");
                        txtCodBod.requestFocus();      
                    }

                }
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                     int intCelSel = tblDat.getSelectedRow();
////                     if(!objTblMod.isRowEmpty(intCelSel)){
                        if (objTblCelEdiTxtVcoCodAltItm.isConsultaAceptada()) {
                            if (!existeMismoItem(intCelSel)) { /*JM*/
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_ING);
//////////                                double nd_stoAct=0;
//////////                                nd_stoAct=obtenerStockDisponible(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_COD_ALT_ITM_DOS).toString(),txtCodBod.getText(),2);
//////////                                objTblMod.setValueAt(nd_stoAct, intCelSel, INT_TBL_DAT_CAN_ACT_STK);                                 
                            }
                        }
////                    }
                }
            });
            
            
            //Boton ítem.
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVcoGP(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    vcoItm.setCampoBusquedaSP(1,1);
                    vcoItm.setCriterio1(11);
                    vcoItm.setCondicionesSQLSP( txtCodBod.getText());   
                    if (!opEje)
                         objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_ACT_STK, tblDat);
                    else
                         objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ACT_STK, tblDat);
                    
////////////                    int intCelSel = tblDat.getSelectedRow();
////////////                    double nd_stoAct=0;
////////////                    nd_stoAct=obtenerStockDisponible(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_COD_ALT_ITM).toString(),txtCodBod.getText());
////////////                    objTblMod.setValueAt(nd_stoAct, intCelSel, INT_TBL_DAT_CAN_ACT_STK);                    
                }   
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!(txtCodBod.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega es Obligatoria para poder elegir los Items");
                        limpiarTabla();
                        txtCodBod.requestFocus();
                    }

                }
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                     int intCelSel = tblDat.getSelectedRow();
                     if(!objTblMod.isRowEmpty(intCelSel)){
                        if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
                            if (!existeMismoItem(intCelSel)) 
                            { /*JM*/
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_ING);
                                objTblMod.setValueAt(vcoItm.getValueAt(7), intCelSel, INT_TBL_DAT_CAN_ACT_STK);
                            }
                        }
                    }
                }
            });
                
            intColVen=null;
            intColTbl=null;
            
            //Cantidad
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    
                    int intCelSel = tblDat.getSelectedRow();
                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR))>0){
                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR))<=
                            (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ACT_STK))))
                        {
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR));
                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
                            dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesBaseDatos());
                            dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                            recalcularCosto();
                            //objTblMod.insertRow();
                            objTblEdi.seleccionarCelda(tblDat.getSelectedRow()+1, INT_TBL_DAT_COD_ALT_ITM);
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_ING);
                        }
                        else
                        {
                            mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_EGR);
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_ING);
                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_EGR);
                            
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR));
                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
                            dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesBaseDatos());
                            dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                            recalcularCosto();                            
                        }
                    }
                                         
                }
            });
            
            //PARA CANTIDAD DE INGRESO
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ING))>0)
                    {
                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR))>0)
                        {
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_EGR);
                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_ING);
                        }
                        else
                        {
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ING));
                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
                            dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesBaseDatos());
                            dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                            recalcularCosto();
                            //objTblMod.insertRow();
                            objTblEdi.seleccionarCelda(tblDat.getSelectedRow()+1, INT_TBL_DAT_COD_ALT_ITM);
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_EGR);                            
                        }    
                    }
                    else
                    {
                        dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ING));
                        dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
                        dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                        dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                        dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesBaseDatos());
                        objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                        objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                        objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
                        recalcularCosto();                        
                    }    
                                         
                }
            });
            
            

//COSTO
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COST).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    Object valor = objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR);
                    if (valor != null && objUti.parseDouble(valor) > 0)
                    {
                        mostrarMsgInf("No se puede realizar una modificacion a este valor");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtCodBod.requestFocusInWindow();
                    }    
                    
           
                }                
                
                
                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    // Obtén el valor de la celda
                    Object valor = objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ING);

                    // Verifica si es null o 0
                    if (valor != null && objUti.parseDouble(valor) > 0) 
                    {
                        dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ING));
                        dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                        dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                        objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                        
                    } else 
                    {
                        valor = objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR);
                        if (valor != null && objUti.parseDouble(valor) > 0) 
                        {
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_EGR));
                            dblCostoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_COST));
                            dblCostoTotal=objUti.redondear(dblCantidad*dblCostoUnitario,objParSis.getDecimalesBaseDatos());
                            objTblMod.setValueAt(dblCostoTotal, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                        }
                        else
                            objTblMod.setValueAt(0, intCelSel, INT_TBL_DAT_CAN_TOT_COST_EGR);
                        objTblMod.setValueAt(0, intCelSel, INT_TBL_DAT_CAN_TOT_COST_ING);
                    }
                   recalcularCosto(); 
                }
            });
            
                  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_UNI_ITM, tblDat); 
                  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_TOT_ITM, tblDat);
              if (!opEje)
              {
                  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_COST, tblDat); 
                  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_COST_ING, tblDat); 
                  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_COST_EGR, tblDat);
                  
                  
              }  
              else
              {
                   objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_COST, tblDat);
                   objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_COST_ING, tblDat);
                   objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_COST_EGR, tblDat);
              }    

                    
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objDocLis=new ZafDocLis();
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);  
            
            
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    
    
private double obtenerStockDisponible(String item, String bodega, int tipo) {
    boolean blnRes = true;
    java.sql.Connection conLoc = null;
    java.sql.CallableStatement cal = null;
    java.sql.ResultSet rstLoc = null;
    double nd_candis=-1;
    String filtro="";

    try {
        conLoc = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conLoc != null) 
        {
            if (tipo==1)
                filtro = " WHERE tx_codalt ='"+item+"'";
            if (tipo==2)
                filtro = " WHERE tx_codalt2 ='"+item+"'";
            
            // Llamada al SP (3 parámetros: 2 de entrada + 1 de salida)
            String strSql = "{call sp_Consulta_Reservado_SQL_WEB_BODEGA_TODOS(?,?,?)}";
            //String strSql = "{call sp_Consulta_Disponible_Bodega_Item_Varianza(?,?,?)}";
            cal = conLoc.prepareCall(strSql);

            // Parámetros de entrada
            cal.setString(1, bodega);          // @ITEMS
            cal.setString(2, filtro);   // @BODEGA

            // Parámetro de salida
            cal.registerOutParameter(3, java.sql.Types.INTEGER); // @intRes

            // Ejecutar
            boolean hasResults = cal.execute();

            // Leer el ResultSet que devuelve el SP
            while (hasResults) 
            {
                rstLoc = cal.getResultSet();
                while (rstLoc.next()) 
                {
                     nd_candis= rstLoc.getDouble("nd_Candis");
                }
                rstLoc.close();

                // Ver si hay más ResultSets
                hasResults = cal.getMoreResults();
            }

            // Leer parámetro de salida
            int intCodRes = cal.getInt(3);
            if (intCodRes == 1) {
                blnRes = false;
                System.out.println("SP devolvió error en intRes");
            }

            cal.close();
            conLoc.close();
        }
    } 
    catch (Exception e) {
        System.out.println("Error en obtenerStockDisponible: " + e.toString());
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return nd_candis;
}

        
        
        
    

    
    private boolean cargarCopiada(int co_doc)
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabCopiada(co_doc)) {
                if (cargarDetCopiada(co_doc)) {
                    blnRes = true;
                }
            }          
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    
    
    
      
    /**
     * Esta función valida que no se repita el item ingresado.
     * Si el item existe muestra un mensaje y lo borra para evitar problemas de stock.
     * Solicitado por Ingrid Lino 14/Junio/2017 
     * @param intFilSel
     * @return true: Si el item existe.
     * <BR>false: En el caso contrario.
     */
    private boolean existeMismoItem(int intFilSel){
        boolean blnRes = false;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM) != null){
                if (i != intFilSel){
                    if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM).toString())){
                        blnRes = true;
                        mostrarMsgInf("<HTML>El item ya fue ingresado en la fila " + (i+1) + ".</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT_ITM);
                        break;
                    }
                }
            }
        }
        return blnRes;
    }
    

    public void recalcularCosto(){
        try
        {
            Double dblCostoIng = 0.00 ,dblCostoEgr = 0.00,dblCostoTotalIng = 0.00,dblCostoTotalEgr = 0.00;
            Double dblDife=0.00;
                BigDecimal bgTotCostIng ;
                BigDecimal bgTotCostEGR ;
                BigDecimal bgTotCostTot ;       
                BigDecimal limite = BigDecimal.valueOf(0.00);                
            
            int intNumFil = objTblMod.getRowCountTrue();
            for(int i=0;i<intNumFil;i++)
            {
                dblCostoIng = objUti.redondear(Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TOT_COST_ING)==null?"0.00":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TOT_COST_ING).toString()),objParSis.getDecimalesBaseDatos());
                dblCostoEgr = objUti.redondear(Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TOT_COST_EGR)==null?"0.00":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TOT_COST_EGR).toString()), objParSis.getDecimalesBaseDatos());
                dblCostoTotalIng = dblCostoTotalIng + dblCostoIng;
                dblCostoTotalEgr = dblCostoTotalEgr + dblCostoEgr;
            }
            bgTotCostIng = BigDecimal.valueOf(dblCostoTotalIng).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
            bgTotCostEGR = BigDecimal.valueOf(dblCostoTotalEgr).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
            bgTotCostTot = bgTotCostIng.subtract(bgTotCostEGR).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
                
                txtCostIng.setText( bgTotCostIng.toString());
                txtCostEgr.setText( bgTotCostEGR.multiply(BigDecimal.valueOf(-1)).toString());
                
                if (bgTotCostTot.abs().compareTo(limite) == 0)
                    lblDiferencia.setVisible(false);
                else
                {
                    lblDiferencia.setForeground(Color.RED);
                    lblDiferencia.setText(objUti.parseString(bgTotCostTot));
                    lblDiferencia.setFont(new Font("Arial", Font.BOLD, 20)); // Arial, negrita, tamaño 20  
                    lblDiferencia.setVisible(true);
                    
                }  

         
        } 
        catch (Exception e) {    
            objUti.mostrarMsgErr_F1(this, e);   
        }
    }
    
   
    
    private String getSQLBodOrg(){
        String strCadena="";
        strCadena+=" select iv.DEX_ROW_ID as co_item ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITMGEDSC) as tx_codAlt2,RTRIM(iv.ITEMDESC) as tx_nomItm, \n";
        strCadena+="        RTRIM(iv.PRCHSUOM) as tx_desCor,inf.Peso_KG as nd_pesItmKgr, v.DISPONIBLE as nd_canDis,iv.CURRCOST,RTRIM(v.LOCNCODE) as bodega     \n";
        strCadena+="        from "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito as v \n";
        strCadena+="             left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
////        strCadena+="             left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";//modficar con respecto a altiora
        strCadena+="             left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";//modficar con respecto a altiora
        strCadena+="        where v.DISPONIBLE > 0 and v.LOCNCODE ='"+ txtCodBod.getText() +"' \n";
        //System.out.println("getSQLBodOrg:" + strCadena);
        
        
        
        return strCadena;        
    }
    
//////    public void isBodegaOrigen() //aqio
//////    {
//////        if(txtCodBod.getText().length()>0){
//////            if(intCodBodOrg != txtCodBod.getText().length()) {  // SI ES OTRA BODEGA //chiqui
//////                vcoItm.limpiar();
//////                vcoItm.setSentenciaSQL(txtCodBod.getText());
//////                intCodBodOrg=Integer.parseInt(txtCodBod.getText());
//////            }
//////        }
//////    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }

    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
    }

    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtNomTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);

        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtCostIng.getDocument().addDocumentListener(objDocLis);
        txaObsSo.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    } 
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() 
        {
            try{
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() 
        {
            try{
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() 
        {
            try{
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra < arlDatConSol.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceSolTra++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() 
        {
            try{
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra<arlDatConSol.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=arlDatConSol.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=arlDatConSol.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() 
        {
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
////                if(!isVersionActualizada()){
////                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                dispose();
////                }
                            
                //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecDoc.setEnabled(false);
                datFecAux=null;
                mostrarTipDocPre();
                configurarVenBodOrg();
                //configurarVenConItm();
                txtCodBod.requestFocus();     
////                txtNumDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtCostEgr.setEditable(false);
                txtCostIng.setEditable(false);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (Exception e){          
                objUti.mostrarMsgErr_F1(this, e);          
            }
        }

        public void clickConsultar() 
        {   
            System.out.println("clickConsultar");
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtDesCorTipDoc.requestFocus();
                    break;
                case 'j':
                    break;
            }
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
////            txtNumDoc.setEditable(true);
            dtpFecDoc.setEnabled(true);  /// TEMPORAL!!! 
            blnHayCam=false;
        }

        public void clickModificar() {
            DesactivarActivar(false);
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if(Validaciones())
            {
                if (!insertarReg())
                    return false;
                else
                {

                }
            }
            else
            {
                mostrarMsgInf("Solo se pueden Insertar Documentos, con el tipo de documento SOTRIN: Solicitudes de transferencias de inventario. ");
                return false;
            }
            return true;
        }

        public boolean consultar() {
            if (!consultarReg())
                return false;
            return true;
        }
        
        public boolean modificar() {
            if (!modificarReg())
            {
                return false;
            }
            else
            {
                obtenerCodGP();
                consultar();
                return true;
            }            
////            return true;
        }



private boolean obtenerCodGP()
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                    
                strSQL+=" select rtrim(cb.tx_numDoc) as co_idCabGP";
                strSQL+=" from tbm_cabSolTraInv cb";
                strSQL+=" inner join (";
                strSQL+="       SELECT c.co_tipDoc ";
                strSQL+="       FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                strSQL+="           INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu) ";
                strSQL+="       WHERE g.co_tipDoc =" + nd_tipo_doc + " ";  
                strSQL+="              AND g.co_emp=" + objParSis.getCodigoEmpresa() + ") as co on (cb.co_tipdoc = co.co_tipDoc) ";  
                strSQL+="       where cb.co_doc =" + txtCodDoc.getText() + " ";
                System.out.println("tbm_cabSolTraInv: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next()){
                    strAux=rstLoc.getString("co_idCabGP");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                }
                else{
                    blnRes=false;
                }
            rstLoc.close();
            stmLoc.close();
            con.close();
            rstLoc=null;
            stmLoc=null;
            con=null;
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

        public boolean eliminar() {
            try
            {
                if (!eliminarReg())
                    return false;
                 
                //Desplazarse al siguiente registro si es posible.
                if(arlDatConSol.size()>0){
                   if(intIndiceSolTra < arlDatConSol.size()-1){
                        intIndiceSolTra++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            blnHayCam=false;
            return true;
        }

        public boolean anular() {
            System.out.println("anular");
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() 
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public boolean vistaPreliminar() 
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                objTblMod.clearRowHeaderRaise();
                limpiarFrm();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);             
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        public boolean beforeInsertar() {
             if (!isCamVal())
                return false;
            if (objTblMod.getRowCountTrue()==0)
            {
                if (mostrarMsgCon("El documento no tiene ninguna fila en el detalle.\n¿Esta seguro que desea INSERTAR el documento?", false)==javax.swing.JOptionPane.NO_OPTION)
                    return false;
            }
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            return blnRes;
        }

        public boolean beforeEliminar() {
          strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() 
        {
            System.out.println("afterInsertar");
            blnHayCam=false;
            objTooBar.setEstado('w');
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            objTblMod.initRowsState();
            consultarReg();
            blnHayCam=false;
            //btnCopiar.setEnabled(true);
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterEliminar() {
            blnHayCam=false;
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean beforeClickInicio()
        {
            return isRegPro();
        }
        
        public boolean beforeClickAnterior()
        {
            return isRegPro();
        }

        public boolean beforeClickSiguiente()
        {
            return isRegPro();
        }

        public boolean beforeClickFin()
        {
            return isRegPro();            
        }

        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            return isRegPro();
        }

        public boolean beforeClickVistaPreliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
        
    }

     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
private boolean ModificarDet(java.sql.Connection conn,  int co_doc)//insertachiqui
    {
        boolean blnRes=true;
        //boolean blncorrecto=true;
         java.sql.ResultSet rstLoc;
         double dblCantEgre=0,dblCantIngr=0;
         String tx_itm="";
        intCodSol=0;
        try
        {
                if (conn!=null)
                {
                    stm=conn.createStatement();
                    for(int i=0; i<objTblMod.getRowCountTrue();i++)
                    {
                        tx_itm=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                                
                        strSQL = "";
                        strSQL+= " UPDATE  Dynamics2021.[dbo].[tbm_detSolTraInv]\n";
                        strSQL+= " SET   nd_costoUniMod  = "+objTblMod.getValueAt(i, INT_TBL_DAT_CAN_COST) +", \n";
                        
                        Object objCantEgre = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR);
                        Object objCantIngr = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING);
                        if (objCantEgre.equals(""))
                            dblCantEgre=0;
                        else
                            dblCantEgre = Double.parseDouble(objCantEgre.toString()) * (-1);

                        if (objCantIngr.equals(""))
                            dblCantIngr=0;
                        else
                            dblCantIngr = Double.parseDouble(objCantIngr.toString()); 


                        if (dblCantIngr !=0)
                            strSQL+=" nd_costoTotMod = " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_ING)+""; 
                        if (dblCantEgre !=0)
                            strSQL+=" nd_costoTotMod = " + Double.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_EGR).toString()) *(-1) +""; 

                        strSQL+= " WHERE   co_doc = "+ co_doc +" and co_motTra= '"+ strMoTrans +"' and co_itm='"+tx_itm+"'\n";
                        stm.executeUpdate(strSQL);
                    }                    
                }

            
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }    

private boolean validacionDisponibleCosto(java.sql.Connection conExt,int codDoc)
{
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLocIn;        
    boolean blnRes=true;
   double totalCantidad=0;
   String strCadenaItem="",strCadenaCant=""; 
   String strCadeItemsGlo="";
   String strBodegaGlob ="";
   int contador =0;
        
   try
   {
        if (conExt!=null)
        {
            stmLoc=conExt.createStatement();
            strSQL ="";
            strSQL+=" SELECT c.co_emp,c.co_loc,c.co_tipdoc,c.co_doc ,d.co_itm as tx_itm,trim(inv.PRCHSUOM) as tx_unimed,d.nd_can,trim (tx_bodorg) as tx_bod,D.nd_costoUniMod as nd_cosuni,c.fe_doc,  \n";
            strSQL+="        ISNULL((SELECT BIN FROM tuval..IV00112 WHERE ITEMNMBR =d.co_itm AND LOCNCODE =c.tx_bodorg ),'GENERAL') AS tx_bin,  \n";            
            strSQL+="        case when d.nd_can< 0 then ((-1)* d.nd_can) else d.nd_can end  nd_can ,c.tx_obs1  \n";                        
            strSQL+=" FROM  Dynamics2021..tbm_cabSolTraInv AS c   \n";
            strSQL+="  INNER JOIN Dynamics2021..tbm_detSolTraInv as d on (c.co_doc = d.co_doc and c.co_loc =d.co_loc and c.co_tipdoc = d.co_tipdoc) \n";
            strSQL+="  INNER JOIN "+objParSis.getNombreBaseDatosGP()+"..IV00101 as inv on (d.co_itm = inv.ITEMNMBR) \n";            
            strSQL+="  WHERE c.co_doc = "+ codDoc+" AND c.co_tipdoc =322 \n";
            rstLocIn=stmLoc.executeQuery(strSQL);
            System.out.println("SELECT  DETALLE DE SOLICITUD" + strSQL);  
            strBodegaGlob =txtCodBod.getText();
            strCadenaItem="";
            strCadenaCant="";
            BigDecimal bgdCos = BigDecimal.ZERO;
            BigDecimal bgdCant = BigDecimal.ZERO;
            arlDatITEMDET= new ArrayList();
            
            while(rstLocIn.next())
            {
                  bgdCos= new BigDecimal(rstLocIn.getString("nd_cosuni"));
                  bgdCant= new BigDecimal(rstLocIn.getString("nd_can"));

                if (bgdCant.compareTo(BigDecimal.ZERO) < 0)
                {
                   BigDecimal bgdCantMod = new BigDecimal(rstLocIn.getString("nd_can"));
                   bgdCantMod = bgdCantMod.multiply(new BigDecimal("-1"));
                   strCadenaItem = objUti.codificar(rstLocIn.getString("tx_itm"))+","+ strCadenaItem;
                   strCadenaCant = bgdCantMod+","+ strCadenaCant;     
                   
                    if (validaCosto(conExt,objUti.codificar(rstLocIn.getString("tx_itm")),bgdCos))
                        blnRes =true;
                    else
                    {
                         arlRegITEMDET = new ArrayList();
                         arlRegITEMDET.add(0,objUti.codificar(rstLocIn.getString("tx_itm").toString()));
                         arlRegITEMDET.add(1,"COSTO MODIFICADO");
                         arlDatITEMDET.add(arlRegITEMDET);
                         
                        //mostrarMsgInf("El costo del item que se ingreso para descontar se ha modificado..");
                        blnRes =false;
                        //break;
                    }
                }
            } 
            if (blnRes)
            {
                if (strCadenaItem.endsWith(",")) 
                    strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                if (strCadenaCant.endsWith(",")) 
                    strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                

                if((strCadenaItem!="") && (strCadenaCant !=""))
                {
                    strCadeItemsGlo=strCadenaItem;
                    if (consultaDisponible(conExt,strCadenaItem,strCadenaCant,strBodegaGlob))
                    {
                        blnRes =true;
                        btnRecalcular.setEnabled(false);   
                    }
                        
                    else
                    {
                        mostrarMsgInf("No tiene stock disponible el item para descontar..");
                        blnRes =false;
                    }
                }
                else
                {
                    mostrarMsgInf("No se tiene item para descontar..");
                    blnRes=false;           
                }  
            }
            else
            {
                String strMsgItmUbi="";
                int ii=0;
                strMsgItmUbi="<HTML>Se Items que tienen novedad: <BR><BR>";
                strMsgItmUbi+="<table BORDER=1><tr><td><b>ITEM</b></td><td><b>MOTIVO</b></td></tr>";
                for (ii=0; ii<arlDatITEMDET.size();ii++) 
                { 
                    strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMDET, ii, 0) + "</td>";
                    strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, ii, 1) + "</td>";
                    strMsgItmUbi+="</tr>";
                }
                strMsgItmUbi+="</table>";
                strMsgItmUbi += "</HTML>";
                mostrarMsgInf(strMsgItmUbi);
                btnRecalcular.setEnabled(true);
            }
        }            

   }  
   catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
   catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
   return blnRes;        
    }
private boolean validaCosto(java.sql.Connection conExt,String item,BigDecimal costo)
{
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLocIn;        
    boolean blnRes=false;
   try
   {
        if (conExt!=null)
        {
            stmLoc=conExt.createStatement();
            strSQL ="";
            strSQL+=" SELECT trim(ITEMNMBR) as itm, isnull(CURRCOST,0) as costo  \n";
            strSQL+=" FROM  "+ objParSis.getNombreBaseDatosGP()  +"..iv00101   \n";
            strSQL+=" WHERE ITEMNMBR = "+ item  +" and CURRCOST = "+costo +"\n";
            rstLocIn=stmLoc.executeQuery(strSQL);
            
            if(rstLocIn.next())
                blnRes=true;
        }            
   }  
   catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
   catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
   return blnRes;        
    }


    
    private boolean modificarReg(){
        boolean blnRes=false;
        String menErr="";
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            con.setAutoCommit(false);
////            if(!isVersionActualizada()){
////                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                dispose();
////            }
            
            if(con!=null){
                menErr=validacionValores();
                if (menErr.equals(""))
                {
                    if (validacionDisponibleCosto(con,Integer.parseInt(txtCodDoc.getText())))
                    {
                      if(ModificarCab(con,Integer.parseInt(txtCodDoc.getText())))
                      {
                          if(ModificarDet(con,Integer.parseInt(txtCodDoc.getText())))
                          {
////                         con.commit();
////                         blnRes=true;    
                            if(GeneraSecuencia(con))
                            {
////                                 con.commit();
////                                 blnRes=true;
                                if (insertarGP(con,tx_formatoDoc,tx_motivo,tx_forLot,tx_tipo,nd_tipo_doc))
                                {
                                    String mensajesSujeto ="EJECUCION DE CONVERSION : " +txtCodDoc.getText()  +"  REALIZADA DESDE LA BODEGA : " +txtCodBod.getText() + " POR EL USUARIO:  " + objParSis.getNombreUsuario()+ ""  ;                    
                                    htmlGlo.setLength(0); // Esto deja el StringBuilder en blanco
                                    htmlGlo = generarHtmlDetalleSolicitud(1,Integer.parseInt(txtCodDoc.getText().trim()));
                                    
                                    if (htmlGlo == null || htmlGlo.toString().trim().isEmpty())
                                    {
                                        mostrarMsgInf("No se pudo generar el correo de Solcitud de Conversion.. ");
                                        con.rollback();
                                        blnRes=false;  
                                    }
                                    else
                                    {
                                        if(objRepCorEleGP.enviarNotificacionCorreoElectronicoConAsunto(0, 1, 38, htmlGlo, mensajesSujeto  ))
                                        {
                                            con.commit();
                                            blnRes=true;                                
                                        }
                                    } 
                                }
                                else
                                     con.rollback();
                            }
                            else
                                con.rollback();
                          }
                          else
                                con.rollback();
                    }
                    else
                        con.rollback();                        
                    }
                }
                else
                    mostrarMsgInf(menErr);
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
private boolean insertarGP(java.sql.Connection conExt, String formatoFinal,String motivo,String formatoLote,String tipo,int tipo_doc)
    {
         boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strClaveOT ="",strSql ="",strTipoOT="";
        String registroGP ="";
        int intCodRes=-1;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                strTipoOT= formatoFinal;
                strClaveOT= txtCodDoc.getText();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_CREACION_VARIANZA(?, ?, ?, ?, ?,?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);

                cal.setString("DOCUMENTO_ZAFIRO",  strTipoOT);//Envio de parametro
                cal.setString("ORDEN_TRABAJO",  strClaveOT);//Envio de parametro
                cal.setInt("TIPO_DOCU",  tipo_doc);//Envio de parametro
                cal.setString("MOTIVO",  motivo);//Envio de parametro
                cal.setString("FORMA_LOTE",  formatoLote);//Envio de parametro
                cal.setString("TIPO_LOTE",  tipo);//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
               
              
                cal.close();
                cal=null;
                 
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    
private boolean GeneraSecuencia(java.sql.Connection conExt)//chiqui
         {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int stinactivo=0;
            tx_formato="";
            nd_secuencia=0;
            tx_formatoDoc="";
            
             try
            {
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();

                        strSQL="";
                        strSQL+=" SELECT m.tx_formato,m.tx_secuencia,m.tx_mot ,m.nd_secuencia,cl.tx_for as tx_forLot, cl.nd_sec as nd_secLote,c.co_tipdoc\n";
                        strSQL+=" from Dynamics2021..tbm_cfgMotMnu as m \n";
                        strSQL+="      inner join Dynamics2021..tbm_cabtipdoc as c on (m.co_tipDoc =c.co_tipdoc ) \n";
                        strSQL+="      inner join Dynamics2021..tbr_mnvLot as ml on (m.co_mnu = ml.co_mnu and m.co_tipDoc = ml.co_tipdoc) \n";       
                        strSQL+="      inner join Dynamics2021..tbm_cfglot as cl on (ml.co_lot =cl.co_lot) \n";                           
                        strSQL+=" WHERE c.tx_descor = '"+ txtDesCorTipDoc.getText()  +"' \n";
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            tx_formato = rstLoc.getString("tx_formato");
                            nd_secuencia = rstLoc.getInt("nd_secuencia");
                            tx_formatoDoc = tx_formato + String.format("%06d", nd_secuencia+1);
                            tx_motivo = rstLoc.getString("tx_mot");
                            tx_forLot = rstLoc.getString("tx_forLot");
                            tx_tipo = rstLoc.getString("tx_forLot");
                            nd_secLot = rstLoc.getInt("nd_secLote");
                            nd_tipo_doc = rstLoc.getInt("co_tipdoc");
                            tx_forLot = tx_forLot + String.format("%06d", nd_secLot+1);
                            blnRes=true;
                        }
                }
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
         }


    
//////    private String validacionValores ()
//////    {
//////        String strRes="";
//////        double nd_resul=0;
//////        BigDecimal bgTotCostIng ;
//////        BigDecimal bgTotCostEGR ;
//////        BigDecimal bgTotCostTot ;    
//////        BigDecimal limite = BigDecimal.valueOf(0.2);
//////        if (txtCodDoc.getText().equals(""))
//////            strRes ="Se debe tener un codigo de documento...";
//////        if (strRes.equals(""))
//////        {
//////            bgTotCostIng =new BigDecimal(txtCostIng.getText()).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
//////            bgTotCostEGR =new BigDecimal(txtCostEgr.getText()).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
//////            bgTotCostTot = bgTotCostIng.subtract(bgTotCostEGR.multiply(BigDecimal.valueOf(-1)));
//////            //bgTotCostTot = bgTotCostIng.subtract(bgTotCostEGR);  
//////            if (bgTotCostTot.abs().compareTo(limite) <= 0)
//////                strRes="";
//////            else
//////                strRes ="Se encuentran diferencias entre el costo de entrada vs salida..";                      
//////        }
//////        if (strRes.equals(""))
//////        {
//////            if (!strEstadoDoc.equals("S"))
//////                strRes ="Este documento NO se encuentra APROBADO...";
//////        }
//////        if (strRes.equals(""))
//////        {
//////            if (!txtNumDoc.getText().equals("0") ) 
//////             strRes ="Este documento ya se encuentra procesado...";   
//////        }
//////
//////        return strRes;
//////    }

private String validacionValores()
{
    String strRes = "";

    if (txtCodDoc.getText().trim().isEmpty())
    {
        return "Se debe tener un código de documento...";
    }

    try
    {
        int decimales = objParSis.getDecimalesBaseDatos();

        BigDecimal bgTotCostIng = new BigDecimal(
            txtCostIng.getText().trim()
        ).setScale(
            decimales,
            RoundingMode.HALF_UP
        );

        BigDecimal bgTotCostEgr = new BigDecimal(
            txtCostEgr.getText().trim()
        ).abs().setScale(
            decimales,
            RoundingMode.HALF_UP
        );

        /*
         * Los valores deben ser exactamente iguales
         * después de aplicar los decimales de la base.
         */
        if (bgTotCostIng.compareTo(bgTotCostEgr) != 0)
        {
            strRes =
                "Se encuentran diferencias entre el costo de entrada "
                + "y el costo de salida.\n"
                + "Costo de entrada: " + bgTotCostIng.toPlainString() + "\n"
                + "Costo de salida: " + bgTotCostEgr.toPlainString();
        }
    }
    catch (NumberFormatException e)
    {
        return "Los valores de costo de entrada y salida deben ser numéricos.";
    }

    if (strRes.isEmpty() && !"S".equals(strEstadoDoc))
    {
        strRes = "Este documento NO se encuentra APROBADO...";
    }

    if (
        strRes.isEmpty()
        && !"0".equals(txtNumDoc.getText().trim())
    )
    {
        strRes = "Este documento ya se encuentra procesado...";
    }

    return strRes;
}

    
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        if (blnHayCam || objTblMod.isDataModelChanged())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux, true))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            blnRes=objTooBar.beforeInsertar();
                            if (blnRes)
                                blnRes=objTooBar.insertar();
                            break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;                            
                    }
                    break;
                case 1: //NO_OPTION
                    if (objTooBar.getBotonClick()==MiToolBar.INT_BUT_CON || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ELI || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ANU || objTooBar.getBotonClick()==MiToolBar.INT_BUT_IMP || objTooBar.getBotonClick()==MiToolBar.INT_BUT_VIS)
                    {
                        blnRes=cargarReg();
                    }
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtNomTipDoc.setText("");
            
            txtCodBod.setText("");
            txtNomBod.setText("");
            

            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtCostIng.setText("");
            txtCostEgr.setText("");
            
            dtpFecDoc.setText("");
            
            txaObsSo.setText("");
            txaObs2.setText("");
            lblDiferencia.setText("");
            if (!opEje)
                lblTit.setText("SOLICITUD DE CONVERSION");
            else
                lblTit.setText("EJECUCION DE CONVERSION");
            
            limpiarTabla();
            
        }
        catch (Exception e){         blnRes=false;      }
        return blnRes;
    }
    
private boolean validacionDisponible(){
        boolean blnRes=true;
        double totalCantidad=0;
        double totalRegistros=0;      

        String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="",strCantidad ="";

            strBodega =objUti.codificar(txtCodBod.getText());
            for(int i=0; i<objTblMod.getRowCountTrue();i++)
            {
                   strCantidad = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                   if (strCantidad != null && !strCantidad.equals("") && !strCantidad.equalsIgnoreCase("null"))
                    {
                        strCadenaItem = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM))+","+ strCadenaItem;
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString().equals("") )
                            strCadenaCant = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()+","+ strCadenaCant;                    
                        else
                            strCadenaCant = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString()+","+ strCadenaCant;                                            
                    }   
            }
            if (strCadenaItem.endsWith(",")) {
                strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                }
            if (strCadenaCant.endsWith(",")) {
                strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                
                }
            
            if((strCadenaItem!="") && (strCadenaCant !=""))
            {
                if (consultaDisponible(strCadenaItem, strCadenaCant, strBodega))
                      blnRes =true;
                else
                      blnRes =false;
            }
            else
                blnRes=false;



        if(!blnRes)
        {
                strMsg="<html> La conversion posee items que no tienen disponibilidad. <BR><BR>" ;// CAMBIA
                String strTit="Mensaje del sistema Zafiro";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
        }            
  
        return blnRes;
    }  

private boolean validacionCantidadEgresada(){
        boolean blnRes=true;
        double totalCantidad=0;
        
        double totalRegistros=0;      

        String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="",strCantidad ="",strCantidadDispo="";

            strBodega =objUti.codificar(txtCodBod.getText());
            for(int i=0; i<objTblMod.getRowCountTrue();i++)
            {
                   strCantidad = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                   strCantidadDispo = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ACT_STK));
                   if (strCantidad != null && !strCantidad.equals("") && !strCantidad.equalsIgnoreCase("null"))
                    {
                        if (strCantidadDispo == null || strCantidad.equals("") )
                            blnRes=false;
                                                          
                    }   
            }


        if(!blnRes)
        {
                strMsg="<html> Uno de los items que egresa no tiene cantidad disponible visible, actualziar el item. <BR><BR>" ;// CAMBIA
                String strTit="Mensaje del sistema Zafiro";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
        }            
  
        return blnRes;
    }

private boolean consultaDisponible( String items,String cantidades,String bodega)//chiqui
    {
         java.sql.Connection conGP;
        java.sql.Statement stmGP;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        java.sql.ResultSet rstGPSP;
        String strSql ="";
        int intCodRes=-1;
        
        boolean blnRes=true;
        try
        {
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
             
            if (conGP!=null)
            {
                stmGP = conGP.createStatement();
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = conGP.prepareCall(strSql);
                cal.setString("ITEMS",  items);//Envio de parametro
                cal.setString("CANTIDADES",  cantidades);//Envio de parametro
                cal.setString("BODEGA",  bodega   );//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                    rstGPSP = cal.getResultSet();

                        int i=0;
                        String strMsgItmUbi="";
                         strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items: <BR><BR>";
                         strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> DESCRIPCION </td><td> CANTIDAD_SOLICITADA </td><td> DIFERENCIA </td>";
                         while (rstGPSP.next())
                         { 

                                strMsgItmUbi+="<tr><td>" + rstGPSP.getString("itemsol") + "</td>";
                                strMsgItmUbi+="<td>" + rstGPSP.getString("CantSol") + "</td>";
                                strMsgItmUbi+="<td>" + rstGPSP.getString("disponibleTotal") + "</td>";
                                strMsgItmUbi+="</tr>";
                        }
                        strMsgItmUbi+="</table><BR>";
                        strMsgItmUbi+="No tienen disponibilidad. <html>";
                        mostrarMsgInf(strMsgItmUbi);

                    
                    
                    
                    
                    
                }
                
                
              
                cal.close();
                cal=null;
                 
                stmGP.close();
                stmGP=null;
                conGP.close();
                conGP=null;
                
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

  
        
    

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        int intCodTipDoc,intUltNumDoc, intUltCodDoc;
        try
        {
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            con.setAutoCommit(false);
            if (con!=null){

                intCodTipDoc=0;
                intUltCodDoc=0;
                intUltNumDoc=0;  
                if (insertarCab(con,intCodTipDoc, intUltCodDoc,intUltNumDoc))
                {
                    if(insertarDet(con,intCodTipDoc,intUltCodDoc))
                    {
                        String mensajesSujeto ="SOLICITUD DE CONVERSION : " +intCodSol  +"  REALIZADA DESDE LA BODEGA : " +txtCodBod.getText() + " POR EL USUARIO:  " + objParSis.getNombreUsuario()+ ""  ;                    
                        htmlGlo.setLength(0); // Esto deja el StringBuilder en blanco
                        htmlGlo = generarHtmlDetalleSolicitud(0,intCodSol);

                        if (htmlGlo == null || htmlGlo.toString().trim().isEmpty())
                        {
                            mostrarMsgInf("No se pudo generar el correo de Solcitud de Conversion.. ");
                            blnRes=false;  
                        }
                        else
                        {
                            if(objRepCorEleGP.enviarNotificacionCorreoElectronicoConAsunto(0, 1, 38, htmlGlo, mensajesSujeto  ))
                            {
                                con.commit();
                                blnRes=true;                                
                            }
                        }                        

                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    private boolean ModificarCab(java.sql.Connection conn,  int co_doc)//insertachiqui
    {
        boolean blnRes=true;
        //boolean blncorrecto=true;
         java.sql.ResultSet rstLoc;
        intCodSol=0;
        try
        {
     
                if (conn!=null)
                {
                    stm=conn.createStatement();
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null) {
                        return false;  // Si no se puede obtener, la fecha 
                    }

                    strSQL = "";
                    strSQL+= " UPDATE  Dynamics2021.[dbo].[tbm_cabSolTraInv]\n";
                    strSQL+= " SET    st_aut ='A',fe_eje = '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"' \n";
                    strSQL+= " ,fe_ultmod  = '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"' \n";
                    strSQL+= " ,tx_usrmod  = '"+objParSis.getNombreUsuario()+"' , tx_usreje  = '"+objParSis.getNombreUsuario()+"' \n";
                    strSQL+= " ,tx_obseje  = "+objUti.codificar(txaObs2.getText()) +" \n";
                    strSQL+= " ,st_reg  = 'A' \n";
                    strSQL+= " WHERE   co_doc = "+ co_doc +" and co_motTra= '"+ strMoTrans +"'\n";
                    stm.executeUpdate(strSQL);
                }

            
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    

    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc,int intUltNumDoc)//insertachiqui
    {
        boolean blnRes=true;
        //boolean blncorrecto=true;
         java.sql.ResultSet rstLoc;
        intCodSol=0;
        try
        {
     
                if (conn!=null)
                {
                    stm=conn.createStatement();
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null) {
                        return false;  // Si no se puede obtener, la fecha 
                    }
                    strSQL="";

                    strSQL = "";
                    strSQL+= " SELECT isnull(MAX(a1.co_doc),0)+1 as co_seg \n";
                    strSQL+= " FROM Dynamics2021.[dbo].[tbm_cabSolTraInv] as a1  \n";
                    strSQL+= " WHERE   co_motTra= '"+ strMoTrans +"'\n";
                    rstLoc = stm.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intCodSol=rstLoc.getInt("co_seg");
                    }
                    if(intCodSol ==0)
                    {
                        intCodSol = intCodSol+1;
                    }
                    rstLoc.close();
                    rstLoc=null;

                    strSQL="";
                    strSQL+=" INSERT INTO Dynamics2021.[dbo].[tbm_cabSolTraInv] \n";
                    strSQL+="       (co_emp,co_loc,co_tipdoc,co_doc,[co_motTra],tx_numDoc,[fe_doc],[nd_pesotkgr],[tx_bodorg],[tx_boddes],[st_imp],[tx_obs1],[tx_obs2],[fe_ing],  \n";
                    strSQL+="        [fe_ultmod],[tx_usring],[tx_usrmod],[st_aut],[fe_aut],[tx_usraut],[tx_obsaut],[st_reg],[tx_obUltM],[fe_usrMod],st_conInv) \n";
                    strSQL+=" VALUES (" + objParSis.getCodigoEmpresa()+ "," + objParSis.getCodigoLocal()+ "," + txtCodTipDoc.getText()+ ","+intCodSol +",'"+txtDesCorTipDoc.getText() +"',0,'"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"','"+txtCostIng.getText() +"','"+txtCodBod.getText()+"', \n";
                    strSQL+="        '',";
                    strSQL+="        'N',"+objUti.codificar(txaObsSo.getText())+","+objUti.codificar(txaObs2.getText())+",'"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',\n";
                    strSQL+="        '','"+objParSis.getNombreUsuario()+"','','N','','','','P','','','P'); \n";
                    strSQL+=" \n ";
                    stm.executeUpdate(strSQL);
                }

            
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc)
    {
        boolean blnRes=true;
        try
        {
            double dblCantEgre=0,dblCantIngr=0;
            if (conn!=null)
            {
                stm=conn.createStatement();
                //Obtener la fecha del servidor.
                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    
                    Object objCantEgre = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR);
                    Object objCantIngr = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING);
                    if (objCantEgre.equals(""))
                        dblCantEgre=0;
                    else
                        dblCantEgre = Double.parseDouble(objCantEgre.toString()) * (-1);
                    
                    if (objCantIngr.equals(""))
                        dblCantIngr=0;
                    else
                        dblCantIngr = Double.parseDouble(objCantIngr.toString());                    
                  
                    
                    strSQL="";
                     strSQL+=" INSERT INTO Dynamics2021.[dbo].[tbm_detSolTraInv]  ([co_emp],[co_loc],[co_tipDoc],[co_doc],[co_motTra],[co_reg],[co_itm],[nd_can],[nd_pesounikgr],[nd_pesototkgr],nd_costoUni,nd_costoTot,nd_costoUniMod,nd_costoTotMod) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa() + ",";
                    strSQL+=" " + objParSis.getCodigoLocal() + ",";
                    strSQL+=" " + txtCodTipDoc.getText() + ",";
                    strSQL+=" " + intCodSol + ",";
                    strSQL+=" '" + txtDesCorTipDoc.getText() + "',";
                    strSQL+=" " + (i+1) + ",";
                    strSQL+=" '" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM) + "',";
                    if (dblCantIngr !=0)
                        strSQL+=" " + dblCantIngr + ",";
                    if (dblCantEgre !=0)
                        strSQL+=" " + dblCantEgre + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_UNI_ITM) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_TOT_ITM)+ ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN_COST) + ",";
                    if (dblCantIngr !=0)
                        strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_ING)+","; 
                    if (dblCantEgre !=0)
                        strSQL+=" " + Double.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_EGR).toString()) *(-1) +","; 
                    
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN_COST) + ",";
                    if (dblCantIngr !=0)
                        strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_ING)+""; 
                    if (dblCantEgre !=0)
                        strSQL+=" " + Double.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_TOT_COST_EGR).toString()) *(-1) +""; 

                                       
                    strSQL+=" );\n";
                    stm.executeUpdate(strSQL);
                }        
            }
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){         
            blnRes=false;         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){          
            blnRes=false;           
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
    }

    private int getUltNumDoc(java.sql.Connection conn,int codTipDoc)
    {
        int intRes=0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
           if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_ultDoc \n";
                strSQL+=" FROM tbm_cabTipDoc as a1 \n";
                strSQL+=" WHERE a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("ne_ultDoc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);          intRes = 0;       }
        catch (Exception e){           objUti.mostrarMsgErr_F1(this, e);           intRes = 0;       }
        return intRes;
    }
    
    private int getUltCodDoc(java.sql.Connection conn, int codTipDoc) 
    {
        int intRes = 0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
            if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX (co_doc) IS NULL THEN 1 ELSE MAX(co_doc) + 1 END AS co_doc  \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND\n";
                strSQL+="       a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                       intRes = rstLoc.getInt("co_doc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        
            objUti.mostrarMsgErr_F1(this, e);   
            intRes = 0;       
        }
        catch (Exception e){        
            objUti.mostrarMsgErr_F1(this, e);           
            intRes = 0;        
        }
        return intRes;
    }
    
    
     /**
     * Esta función obtiene el query de los Tipos de Documentos 
     * configurados de acuerdo al usuario.
     */
    private String getSQLTipDoc(int intTipo)//chiqui
    {
        String strTipDoc="";
        try
        {
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (codMenu==1)
            {
////                //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT RTRIM(g.tx_descor) as Codigo \n";
                        if(intTipo!=1)
                             strSQL+=" , g.co_tipdoc,RTRIM(g.tx_deslar) as Descripcion \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g \n";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)\n";
                        strSQL+=" WHERE RTRIM(g.tx_descor)  ='"+ strMoTrans +"'\n";
                        //strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                        
                        
            }
            else
            {
//                //Armar la sentencia SQL.
////////////                    strSQL="";  
////////////                    strSQL+=" SELECT RTRIM(g.tx_descor) as Codigo ";
////////////                     if(intTipo!=1)
////////////                            strSQL+=" , g.co_tipdoc,RTRIM(g.tx_deslar) as Descripcion ";
////////////                    strSQL+=" FROM    Dynamics2021.dbo.tbm_cabtipdoc  as g ";
////////////                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
////////////                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)";
////////////                    strSQL+=" WHERE  g.co_mnu ="+ codMenu +"";
////////////                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";   
////////////                    strSQL+=" AND m.co_usr=" + objParSis.getCodigoUsuario()+ ""; 
                
                        strSQL="";
                        strSQL+=" SELECT RTRIM(g.tx_descor) as Codigo \n";
                        if(intTipo!=1)
                             strSQL+=" , g.co_tipdoc,RTRIM(g.tx_deslar) as Descripcion \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g \n";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)\n";
                        strSQL+=" WHERE RTRIM(g.tx_descor)  ='"+ strMoTrans +"'\n";                        
////                        strSQL+=" WHERE g.co_mnu ="+ codMenu +"\n";
////                        strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "\n";                
                  
            }
            strTipDoc=strSQL;
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);   }
        return strTipDoc;
        
       
    }   
        

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg() 
    {
        boolean blnRes = true;
       
        try
        {
             //strMoTrans ='TRANSFER';
            
            String strFil="";
            System.out.println("consultarReg:");
            //conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conCab=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               
                //<editor-fold defaultstate="collapsed" desc="/* Filtro */">
                if(dtpFecDoc.isFecha()) {
                    strFil+=" AND cab.fe_doc='" +  objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                if(txtDesCorTipDoc.getText().length()>0) {
                    strFil+=" AND cab.co_motTra='" + txtDesCorTipDoc.getText() +"'";
                }
////                else
////                {
////                    strFil+=" AND cab.co_motTra in (" + getSQLTipDoc(1)+ " )";
////                }
                if(txtCodDoc.getText().length()>0) {
                    strFil+=" AND cab.co_doc=" + txtCodDoc.getText();
                }
                if(txtNumDoc.getText().length()>0) {
                    strFil+=" AND cab.tx_numDoc='" + txtNumDoc.getText() + "'";
                }
                if(txtCodBod.getText().length()>0) {
                    strFil+=" AND cab.tx_bodorg='" + txtCodBod.getText()+ "'";
                }

                //</editor-fold>
                    strSQL="";
                    strSQL+=" SELECT g.co_tipdoc,RTRIM(cab.co_motTra) as co_motTra,cab.co_doc  \n";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as cab \n";
                    strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = cab.co_emp and cab.co_tipdoc = g.co_tipdoc)";
                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" "+ strFil + " \n";
                    strSQL+=" AND cab.co_motTra= '"+strMoTrans +"'\n";
                    strSQL+="  ORDER BY co_doc";                
               
////////////                if(objParSis.getCodigoUsuario()==1)
////////////                {
////////////                    strSQL="";
////////////                    strSQL+=" SELECT g.co_tipdoc,RTRIM(cab.co_motTra) as co_motTra,cab.co_doc  \n";
////////////                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as cab \n";
////////////                    strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = cab.co_emp and cab.co_tipdoc = g.co_tipdoc)";
////////////                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
////////////                    strSQL+=" "+ strFil + " \n";
////////////                    strSQL+=" AND cab.co_motTra= '"+strMoTrans +"'\n";
////////////                    strSQL+="  ORDER BY co_doc";
////////////                }
////////////                else
////////////                {
////////////
////////////                    strSQL="";
////////////                    strSQL+=" SELECT g.co_tipdoc,RTRIM(cab.co_motTra) as co_motTra,cab.co_doc  \n";
////////////                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as cab \n";
////////////                    strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = cab.co_emp and cab.co_tipdoc = g.co_tipdoc)";
////////////                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)";                    
////////////                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
////////////                    strSQL+=" AND m.co_usr=" + objParSis.getCodigoUsuario()+ "";
////////////                    strSQL+=" "+ strFil + " \n";
////////////                    strSQL+=" AND cab.co_motTra= '"+strMoTrans +"'\n";
////////////                    strSQL+="  ORDER BY co_doc";                   
////////////                    
////////////                }
                System.out.println("consultarReg: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                arlDatConSol = new ArrayList();
                while(rstCab.next())
                {
                    arlRegConSol = new ArrayList();
////                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_EMP,rstCab.getInt("co_motTra"));
////                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_LOC,rstCab.getInt("co_loc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_TIPDOC,rstCab.getString("co_motTra"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_DOC,rstCab.getInt("co_doc"));
                    arlDatConSol.add(arlRegConSol);
                }
                stmCab.close();
                stmCab=null;
                rstCab.close();
                rstCab=null;
                conCab.close();
                conCab=null;
                
                if(arlDatConSol.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConSol.size()) + " registros");
                    intIndiceSolTra=arlDatConSol.size()-1;
                    cargarReg();
                    
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e){ blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e){      blnRes=false;     objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
  
    

    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg()) {
                if (cargarDetReg()) 
                {
                    blnRes = true;
                    
                }
                    
            }          
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        double peso=0;
        String strEstado ="";
        String strAux1;
        try
        {
             if (opEje)
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_ACT_STK, tblDat);
             else
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ACT_STK, tblDat);
             
            lblTit.setText("");
            strAux="";
           // con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();

                strSQL="";
                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,RTRIM(a4.Descripcion) as Descripcion,a1.co_doc, a1.fe_doc, RTRIM(a1.tx_bodorg) as tx_bodorg,RTRIM(a1.tx_bodorg) as tx_nombodorg,\n ";
                strSQL+="        ROUND(a1.nd_pesotkgr,2) as nd_pesTotKgr,a1.st_imp,a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.tx_usrIng, \n";
                strSQL+="        a1.tx_usrMod, a1.tx_obUltM, a1.st_aut,a1.tx_numDoc,a1.tx_obsaut,isnull(a1.tx_obseje ,'') as tx_obseje  \n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as a1 \n";
                //strSQL+=" INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +".dbo.vw_codigo_motivo_iv a4 on (a1.co_motTra =a4.Codigo) \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as a4 on (a1.co_tipdoc =a4.co_tipdoc)";
                                        
               // strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP)+"\n";
               // strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC)+"\n";
                strSQL+=" WHERE a4.co_mnu=" + codMenu+ "";
                //strSQL+=" WHERE co_motTra='"+ strMoTrans +"'\n";
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC)+"\n";


                //  System.out.println("cargarCabReg: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                {
                    strAux=rst.getString("co_motTra");
                    txtCodTipDoc.setText((strAux==null)?"":strAux); 
                    strAux=rst.getString("co_motTra");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("Descripcion");
                    txtNomTipDoc.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("tx_bodorg");
                    txtCodBod.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_nombodorg");
                    txtNomBod.setText((strAux==null)?"":strAux);                    
                    

//                    strAux=rst.getString("nd_pesTotKgr");
//                    txtPesKgr.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObsSo.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obsaut") ;
                    if (!rst.getString("tx_obseje").equals(""))
                        strAux += "/" + rst.getString("tx_obseje");
                    txaObs2.setText((strAux==null)?"":strAux);  
                    
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));                    
                    strAux=rst.getString("tx_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);   
//                    strAux=objUti.redondear(rst.getDouble("nd_pesTotKgr"),objParSis.getDecimalesMostrar()) ;

                    
                    
                    
                    //Mostrar el estado del registro
                    strEstado=rst.getString("st_reg");
                    if (strEstado.equals("I"))
                    {
                        strAux="Anulado";
                        if (opEje)
                            objTooBar.setVisibleModificar(false);  // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR                        
                    }
                        
                    if (strEstado.equals("E"))
                    {
                        strAux="Eliminado";
                        if (opEje)
                            objTooBar.setVisibleModificar(false);  // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR                        
                    }                    
                    if (strEstado.equals("S"))
                    {
                        strAux="Por Ejecutar";
                        if (opEje)
                            objTooBar.setVisibleModificar(true); 
                    }                    
                    if (strEstado.equals("N"))
                    {
                        strAux="Rechazado";
                        if (opEje)
                            objTooBar.setVisibleModificar(false); // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR                        
                    }                                        
                    if (strEstado.equals("P"))
                    {
                        strAux="Pendiente de Autorizar";    
                        if (opEje)
                            objTooBar.setVisibleModificar(false);  // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR 
                    }                    
                    if (strEstado.equals("A"))
                    {
                        strAux="Ejecutada"; 
                        if (opEje)
                            objTooBar.setVisibleModificar(false); // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR                        
                    }                                                            
                    if (strEstado.equals("D"))
                    {
                        strAux="Denegada";                    
                        if (opEje)
                            objTooBar.setVisibleModificar(false); // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR                        
                    }                                                            
                    
                    strEstadoDoc =strEstado;
                    
                    
                        
                    objTooBar.setEstadoRegistro(strAux);
                    strAux1 = lblTit.getText();
                    if (!opEje)
                        strAux1 =   "Solicitud de Conversion - "  + strAux;
                    else
                        strAux1 =   "Solicitud de Conversion - "  + strAux;
                    lblTit.setText(strAux1);
                }
                con.close();
                con=null;
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceSolTra+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConSol.size()) );
        }
        catch (java.sql.SQLException e){      blnRes=false;        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    private boolean cargarCabCopiada( int co_doc)
    {
        boolean blnRes=true;
        double peso=0;
        String strEstado ="";
        String strAux1;
        try
        {

                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_ACT_STK, tblDat);
 
             
            lblTit.setText("");
            strAux="";
           // con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();

                strSQL="";
                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,RTRIM(a4.Descripcion) as Descripcion,a1.co_doc, a1.fe_doc, RTRIM(a1.tx_bodorg) as tx_bodorg,RTRIM(a1.tx_bodorg) as tx_nombodorg,\n ";
                strSQL+="        ROUND(a1.nd_pesotkgr,2) as nd_pesTotKgr,a1.st_imp,a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.tx_usrIng, \n";
                strSQL+="        a1.tx_usrMod, a1.tx_obUltM, a1.st_aut,a1.tx_numDoc,a1.tx_obsaut,isnull(a1.tx_obseje ,'') as tx_obseje  \n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as a1 \n";
                //strSQL+=" INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +".dbo.vw_codigo_motivo_iv a4 on (a1.co_motTra =a4.Codigo) \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as a4 on (a1.co_tipdoc =a4.co_tipdoc)";
                                        
               // strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP)+"\n";
               // strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC)+"\n";
                strSQL+=" WHERE a4.co_mnu=" + codMenu+ "";
                //strSQL+=" WHERE co_motTra='"+ strMoTrans +"'\n";
                strSQL+=" AND co_doc=" + co_doc +"\n";


                //  System.out.println("cargarCabReg: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                {
                    strAux=rst.getString("co_motTra");
                    txtCodTipDoc.setText((strAux==null)?"":strAux); 
                    strAux=rst.getString("co_motTra");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("Descripcion");
                    txtNomTipDoc.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("tx_bodorg");
                    txtCodBod.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_nombodorg");
                    txtNomBod.setText((strAux==null)?"":strAux);                    
                    

//                    strAux=rst.getString("nd_pesTotKgr");
//                    txtPesKgr.setText((strAux==null)?"":strAux);
                    strAux="0";
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObsSo.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obsaut") ;
                    if (!rst.getString("tx_obseje").equals(""))
                        strAux += "/" + rst.getString("tx_obseje");
                    txaObs2.setText((strAux==null)?"":strAux);  
                    
                    //datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
                    dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
                    dtpFecDoc.setEnabled(false);  /// TEMPORAL!!!                     
            
                    //dtpFecDoc.setText(objUti.formatearFecha(dtpFecDoc,"dd/MM/yyyy"));                    
                    strAux="0";
                    txtNumDoc.setText((strAux==null)?"":strAux);   
//                    strAux=objUti.redondear(rst.getDouble("nd_pesTotKgr"),objParSis.getDecimalesMostrar()) ;

                    
                    
                    
                    //Mostrar el estado del registro
////                    strEstado=rst.getString("st_reg");
////                    if (strEstado.equals("I"))
////                        strAux="Anulado";
////                    if (strEstado.equals("E"))
////                        strAux="Eliminado";
////                    if (strEstado.equals("S"))
////                        strAux="Por Ejecutar";
////                    if (strEstado.equals("N"))
////                        strAux="Rechazado";
////                    if (strEstado.equals("P"))
////                        strAux="Pendiente de Autorizar";       
////                    if (strEstado.equals("A"))
////                        strAux="Ejecutada"; 
////                    strEstadoDoc =strEstado;
                        
                        strAux1 =   "Solicitud de Conversion ";
                        lblTit.setText(strAux1);
                        dtpFecDoc.setEnabled(false);
                }
                con.close();
                con=null;
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            //Mostrar la posición relativa del registro.
        }
        catch (java.sql.SQLException e){      blnRes=false;        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }    
   
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        double  nd_costTotIng=0,nd_costTotEgr=0,nd_costDife=0;
        try
        {
            objTblMod.removeAllRows();
            objTooBar.setMenSis("Obteniendo datos...");
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";


                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITMGEDSC) as tx_codAlt2,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.PRCHSUOM,a1.nd_can,  \n";
                strSQL+="         a1.nd_costoUni,nd_costoTot as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC,a1.nd_costoUniMod,a1.nd_costoTotMod \n";
                strSQL+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1 \n";
                strSQL+=" INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
////                strSQL+=" WHERE co_motTra='"+strMoTrans +"'\n";
                strSQL+=" WHERE a1.co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC)+"\n";
                strSQL+="       AND a1.co_motTra= '"+strMoTrans +"' \n";
                strSQL+=" ORDER BY a1.co_reg";
                
                // System.out.println("cargarDetReg::.." + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("DEX_ROW_ID"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("ITEMNMBR"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS,rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BTN_ITM,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("ITEMDESC"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("PRCHSUOM"));
                    if (rst.getDouble("nd_can")>0)
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING,rst.getDouble("nd_can"));
                        vecReg.add(INT_TBL_DAT_CAN_EGR,0);
                    }   
                    else
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING,0);
                        vecReg.add(INT_TBL_DAT_CAN_EGR, rst.getDouble("nd_can")*(-1) );
                    }

                    vecReg.add(INT_TBL_DAT_PES_UNI_ITM,rst.getString("nd_pesounikgr"));
                    vecReg.add(INT_TBL_DAT_PES_TOT_ITM,rst.getString("nd_pesototkgr"));

////                    double nd_stoAct=0;
////                    nd_stoAct=obtenerStockDisponible(rst.getString("ITEMNMBR"),txtCodBod.getText());
////                    vecReg.add(INT_TBL_DAT_CAN_ACT_STK,nd_stoAct);
                    
                    vecReg.add(INT_TBL_DAT_CAN_ACT_STK,"");
                    vecReg.add(INT_TBL_DAT_CAN_COST,rst.getString("nd_costoUniMod"));
                    if (rst.getDouble("nd_can")>0)
                    {
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_ING,rst.getDouble("nd_costoTotMod"));
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_EGR,0);
                        nd_costTotIng = nd_costTotIng + rst.getDouble("nd_costoTotMod");
                    }   
                    else
                    {
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_ING,0);
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_EGR, rst.getDouble("nd_costoTotMod")*(-1) );
                        nd_costTotEgr = nd_costTotEgr + rst.getDouble("nd_costoTotMod");
                    }                
                   
                    vecDat.add(vecReg);
                }
                BigDecimal bgTotCostIng ;
                BigDecimal bgTotCostEGR ;
                BigDecimal bgTotCostTot ;
                BigDecimal limite = BigDecimal.valueOf(0.0);
                bgTotCostIng =new BigDecimal(nd_costTotIng).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
                bgTotCostEGR =new BigDecimal(nd_costTotEgr).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
                bgTotCostTot = bgTotCostIng.subtract(bgTotCostEGR.multiply(BigDecimal.valueOf(-1)));
                
                txtCostIng.setText( bgTotCostIng.toString());
                txtCostEgr.setText( bgTotCostEGR.toString());
                
                
           
                
                if (bgTotCostTot.abs().compareTo(limite) == 0)
                    lblDiferencia.setVisible(false);
                else
                {
                    lblDiferencia.setForeground(Color.RED);
                    lblDiferencia.setText("La Diferencia es : "+ objUti.parseString(bgTotCostTot));
                    lblDiferencia.setFont(new Font("Arial", Font.BOLD, 20)); // Arial, negrita, tamaño 20  
                    lblDiferencia.setVisible(true);
                     
                }    
                    
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTooBar.setMenSis("Listo");
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
    
private boolean cargarDetCopiada(int co_doc)
    {
        boolean blnRes=true;
        double  nd_costTotIng=0,nd_costTotEgr=0,nd_costDife=0;
        try
        {
            objTblMod.removeAllRows();
            objTooBar.setMenSis("Obteniendo datos...");
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";


                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITMGEDSC) as tx_codAlt2,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.PRCHSUOM,a1.nd_can,  \n";
                strSQL+="         a1.nd_costoUni,nd_costoTot as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                strSQL+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1 \n";
                strSQL+=" INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
////                strSQL+=" WHERE co_motTra='"+strMoTrans +"'\n";
                strSQL+=" WHERE a1.co_doc=" + co_doc +"\n";
                strSQL+="       AND a1.co_motTra= '"+strMoTrans +"' \n";
                strSQL+=" ORDER BY a1.co_reg";
                
                // System.out.println("cargarDetReg::.." + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("DEX_ROW_ID"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("ITEMNMBR"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS,rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BTN_ITM,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("ITEMDESC"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("PRCHSUOM"));
                    if (rst.getDouble("nd_can")>0)
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING,rst.getDouble("nd_can"));
                        vecReg.add(INT_TBL_DAT_CAN_EGR,0);
                    }   
                    else
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING,0);
                        vecReg.add(INT_TBL_DAT_CAN_EGR, rst.getDouble("nd_can")*(-1) );
                    }

                    vecReg.add(INT_TBL_DAT_PES_UNI_ITM,rst.getString("nd_pesounikgr"));
                    vecReg.add(INT_TBL_DAT_PES_TOT_ITM,rst.getString("nd_pesototkgr"));
                    vecReg.add(INT_TBL_DAT_CAN_ACT_STK,"");
                    vecReg.add(INT_TBL_DAT_CAN_COST,rst.getString("nd_costoUniMod"));
                    if (rst.getDouble("nd_can")>0)
                    {
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_ING,rst.getDouble("nd_costoTotMod"));
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_EGR,0);
                        nd_costTotIng = nd_costTotIng + rst.getDouble("nd_costoTotMod");
                    }   
                    else
                    {
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_ING,0);
                        vecReg.add(INT_TBL_DAT_CAN_TOT_COST_EGR, rst.getDouble("nd_costoTotMod")*(-1) );
                        nd_costTotEgr = nd_costTotEgr + rst.getDouble("nd_costoTotMod");
                    }                    
                                        
                   
                    vecDat.add(vecReg);
                }
                BigDecimal bgTotCostIng ;
                BigDecimal bgTotCostEGR ;
                BigDecimal bgTotCostTot ;
                BigDecimal limite = BigDecimal.valueOf(0.00);
                bgTotCostIng =new BigDecimal(nd_costTotIng).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
                bgTotCostEGR =new BigDecimal(nd_costTotEgr).setScale(objParSis.getDecimalesBaseDatos(), RoundingMode.HALF_UP);
                bgTotCostTot = bgTotCostIng.subtract(bgTotCostEGR.multiply(BigDecimal.valueOf(-1)));
                
                txtCostIng.setText( bgTotCostIng.toString());
                txtCostEgr.setText( bgTotCostEGR.toString());
                
                
           
                
                if (bgTotCostTot.abs().compareTo(limite) == 0)
                    lblDiferencia.setVisible(false);
                else
                {
                    lblDiferencia.setForeground(Color.RED);
                    lblDiferencia.setText("La Diferencia es: "+objUti.parseString(bgTotCostTot));
                    lblDiferencia.setFont(new Font("Arial", Font.BOLD, 20)); // Arial, negrita, tamaño 20  
                    lblDiferencia.setVisible(true);
                     
                }    
                    
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTooBar.setMenSis("Listo");
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

    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

     /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtNomBod.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Bodega de Origen </FONT> es obligatorio.<BR>Ingrese una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }   
        if (txtNomTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Tipo de documento </FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
            txtNomTipDoc.requestFocus();
            return false;
        }

        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        boolean blncorrecto=true;
        int indicador =0;
         for(int i=0; i<objTblMod.getRowCountTrue(); i++)
         {
                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)==null)
                {
                    blncorrecto=false;
                    indicador=1;
                    break;
                }
                if (objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)==null)
                {
                    blncorrecto=false;
                    indicador=2;
                    break;
                }

         }
         if (blncorrecto ==false)
         {
            if (indicador ==1)
            {
             tabFrm.setSelectedIndex(0);
             mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Codigo del Item</FONT> es obligatorio.<BR>Proceda a buscar nuevamente el item a seleccion.</HTML>");
            }
            if (indicador ==2)
            {
             tabFrm.setSelectedIndex(0);
             mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cantidad del Item</FONT> es obligatorio.<BR>Proceda a ingresar la cantidad del item.</HTML>");
            }

            dtpFecDoc.requestFocus();
            return false;
         }
         
        
        return true;
      
    }
    

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try{
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            con.setAutoCommit(false);
            if (con!=null){
                if (anularGrp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularGrp()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabSolTraInv ";
                strSQL+=" SET st_reg='I',co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" , fe_usrMod="+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" , tx_comUltMod="+objParSis.getNombreComputadoraConDirIP();
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" and co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
                strSQL+=" ;";
                //  System.out.println("anularGrp" + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

            con.setAutoCommit(false);
            if (con!=null){
                if (eliminarGrp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarGrp()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabSolTraInv";
                strSQL+=" SET st_reg='I',co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" , fe_usrMod="+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" , tx_comUltMod="+objParSis.getNombreComputadoraConDirIP();
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" and co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
                
                //  System.out.println("eliminarGrp" + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
        
    
    private boolean mostrarDocumentos(int intTipBus)
    {
       boolean blnRes=true;
        try
        {
            //  System.out.println("mostrarDocumentos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(0);
                    vcoTipDoc.setVisible(true);
                   if (vcoTipDoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(3));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(2));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por descripcion corta".
                     if (vcoTipDoc.buscar("a1.codigo", txtDesCorTipDoc.getText()))
                     {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(3));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(2));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(3));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtNomTipDoc.setText(vcoTipDoc.getValueAt(2));
                            //if(objTooBar.getEstado()=='n')
                            //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                        }
                        else
                        {
                            txtCodTipDoc.setText(strDocCod);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.descripcion", txtNomTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(3));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(2));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //if(objTooBar.getEstado()=='n')
                            //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                        }
                        else
                        {
                            txtNomTipDoc.setText(strDocNom);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean mostrarBodegaOrigen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodOrg.setCampoBusqueda(0);
                    vcoBodOrg.setVisible(true);
                   if (vcoBodOrg.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodBod.setText(vcoBodOrg.getValueAt(1));
                        txtNomBod.setText(vcoBodOrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por Código".
                    if (vcoBodOrg.buscar("CodigoGP", txtCodBod.getText()))
                    {
                        txtCodBod.setText(vcoBodOrg.getValueAt(1));
                        txtNomBod.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                        vcoBodOrg.setCampoBusqueda(1);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBodOrg.getValueAt(1));
                            txtNomBod.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtCodBod.setText(strCodBodOrg);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodOrg.buscar("NombreLargo", txtNomBod.getText()))
                    {
                         txtCodBod.setText(vcoBodOrg.getValueAt(1));
                         txtNomBod.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                         vcoBodOrg.setCampoBusqueda(2);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBodOrg.getValueAt(1));
                            txtNomBod.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtNomBod.setText(strCodBodOrg);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    


    private boolean configurarTipoDocumento()//CHIQUI
    {
        boolean blnRes=true;
        int intTipo=2;
        try
        {
 //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("codigo");
            arlCam.add("descripcion");
            arlCam.add("co_tipDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("co_tipDoc");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("100");
            //ocultar columnas
            int[] arlOcuCol = new int[1];
            arlOcuCol[0]=3;
        
       
            //Armar la sentencia SQL.
            strSQL=getSQLTipDoc(intTipo);
            
            //System.out.println("configurarTipoDocumento: "+strSQL);
          //  vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Documentos", strSQL, arlCam, arlAli, arlAncCol);
            vcoTipDoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Documentos", strSQL, arlCam, arlAli, arlAncCol,arlOcuCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarVenBodOrg()//CHIQUI
    {
        boolean blnRes=true;
        try
        {
           //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("CodigoGP");
            arlCam.add("NombreLargo");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("250");
            arlAncCol.add("350"); 
            //Armar la sentencia SQL. 
            if(objParSis.getCodigoUsuario()==1)//Admin
            {

                  strSQL="";
                  //strSQL+=" SELECT RTRIM(CodigoGP) as CodigoGP, RTRIM(NombreLargo) as NombreLargo  from Dynamics2021.dbo.vwBodegas";
                  strSQL+=" select rtrim(tx_descor) as CodigoGP, rtrim(UPPER(tx_nom)) as NombreLargo  from tbm_bod where co_emp=1 and tx_descor IS NOT NULL";
                  
////                  if(txtCodBod.getText().length()>0)
////                    strSQL+=" WHERE  CodigoGP LIKE '%"+txtCodBod.getText() +"%'";
            
            }
            else
            {
                strSQL="";
                strSQL+=" select rtrim(tx_descor) as CodigoGP, rtrim(UPPER(tx_nom)) as NombreLargo  from tbm_bod where co_emp=1 and tx_descor IS NOT NULL";                
//////////////                strSQL+=" SELECT RTRIM(g.CodigoGP) as CodigoGP,RTRIM(b.tx_nom) as NombreLargo \n";
//////////////                strSQL+=" FROM Dynamics2021.dbo.vwBodegas as g \n";
//////////////                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_bod as b on (g.CodigoGP = b.tx_descor) \n";
//////////////                strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_bodTipDocPrgUsr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
//////////////                strSQL+=" WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
//////////////                strSQL+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
//////////////                strSQL+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
//////////////                //strSQL+="       AND t.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
//////////////                //strSQL+="       AND b.co_tipDoc=269 \n";
//////////////                strSQL+="       AND t.co_mnu=" + codMenu + " \n";
//////////////                strSQL+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
////                if(txtCodBod.getText().length()>0)
////                    strSQL+=" AND  CodigoGP LIKE '%"+txtCodBod.getText() +"%'";
            
            }
            //  System.out.println("configurarVenBodOrg: \n" + strSQL);
            vcoBodOrg=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBodOrg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
   
      
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()//CHIQUI
    {
        boolean blnRes=true;
        try
        {
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                if(objParSis.getCodigoUsuario()==1) //Admin
                {

                        strSQL="";
                        strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as Codigo, RTRIM(g.tx_deslar) as Descripcion \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g \n";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipdoc =c.co_tipDoc and g.co_mnu =c.co_mnu) \n";
                        strSQL+=" WHERE g.co_mnu =" + codMenu + " \n";
                        strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + " \n";
                        
                     
                }//se quema por que se llama desde cotizaciones de venta 
                else
                {
                                  
                        strSQL="";
                        strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as Codigo, RTRIM(g.tx_deslar) as Descripcion \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g \n";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipdoc =c.co_tipDoc and g.co_mnu =c.co_mnu) \n";
                        strSQL+=" WHERE g.co_mnu =" + codMenu + " \n";
                        strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + " \n";                    
////////////                    strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as Codigo, RTRIM(g.tx_deslar) as Descripcion ";
////////////                    strSQL+=" FROM    Dynamics2021.dbo.tbm_cabtipdoc  as g ";
////////////                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
////////////                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)";
////////////                    strSQL+=" WHERE g.co_mnu =" + codMenu+ "";
////////////                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";   
////////////                    strSQL+=" AND m.co_usr=" + objParSis.getCodigoUsuario()+ "";                       
                 }
                //System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipdoc"));
                    txtDesCorTipDoc.setText(rst.getString("Codigo"));
                    txtNomTipDoc.setText(rst.getString("Descripcion"));
                    //txtNumDoc.setText(String.valueOf(getUltNumDoc(con,rst.getInt("co_tipDoc"))));
                }
                else
                {
                    mostrarMsgAdv("No tiene ningun documento autorizado...");
                    objTooBar.setEnabledAceptar(false);
                    return false;
                }
                rst.close();
                stm.close();
                con.close();
                butTipDoc.setEnabled(false);
                rst=null;
                stm=null;
                con=null;
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

    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del Item";
                    break;
                 case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código Alterno del Item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM_DOS:
                    strMsg="Código alterno 2 del item";
                break;
                case INT_TBL_DAT_BTN_ITM:
                    strMsg="Listado de items";
                    break;
               case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
               case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
               case INT_TBL_DAT_CAN_ING:
                    strMsg="Cant. Ingresada";
                    break;
               case INT_TBL_DAT_CAN_EGR:
                    strMsg="Cant. Egresada";
                    break;                    
               case INT_TBL_DAT_PES_UNI_ITM:
                    strMsg="Peso unitario (Kg)";
                    break;
               case INT_TBL_DAT_PES_TOT_ITM:
                    strMsg="Peso total (Kg)";
                    break;
               case INT_TBL_DAT_CAN_COST:
                    strMsg="Costo Uni.";
                    break;
               case INT_TBL_DAT_CAN_TOT_COST_ING:
                    strMsg="Costo Total Ingreso";
                    break;                 
               case INT_TBL_DAT_CAN_TOT_COST_EGR:
                    strMsg="Costo Total Egreso";
                    break;                      
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_item");
            arlCam.add("tx_codAlt");
            arlCam.add("tx_codAlt2");
            arlCam.add("tx_nomItm");
            arlCam.add("tx_desCor"); // Unidad de medida
            arlCam.add("nd_pesItmKgr");
            arlCam.add("nd_canDis");
            arlCam.add("nd_costo");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.p");
            arlAli.add("Cód.Alt.p");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre del Item");
            arlAli.add("Uni.Med.");
            arlAli.add("Peso (Kg)");
            arlAli.add("Disponible");
            arlAli.add("Costo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("30");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("70");
            
             int intColOcu[] = new int[1];
             intColOcu[0] = 1;
             intColOcu[0] = 1;
             
            //Armar la sentencia SQL.
            String strCadena="";
            strCadena=txtCodBod.getText();
////            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a2.nd_stkAct, a1.nd_cosUni, a1.st_ser";
////            strSQL+=" FROM tbm_inv AS a1";
////            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
////            strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
////            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////            strSQL+=" AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.tx_codAlt";
            
////////            strCadena+=" SELECT  TRIM(iv01.ITEMNMBR) as co_item,TRIM(iv01.ITEMNMBR) as tx_codAlt,TRIM(iv01.ITMGEDSC) as tx_codAlt2,  \n";
////////            strCadena+="         TRIM(iv01.ITEMDESC) as tx_nomItm,TRIM(iv01.PRCHSUOM) as tx_desCor,0 nd_canDis,     \n";
////////            strCadena+="         isnull(inv.nd_pes,0) as nd_pesItmKgr, iv01.CURRCOST as nd_costo    \n";
////////            strCadena+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 \n";
////////           // strCadena+="             INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..iv00102 as disp ON (disp.ITEMNMBR = iv01.ITEMNMBR and RCRDTYPE=2) \n";
////////            strCadena+="             INNER JOIN Dynamics2021..tbm_inv as inv ON (inv.tx_itm = iv01.ITEMNMBR) \n";            
////////            strCadena+=" WHERE iv01.INACTIVE =0 and iv01.ITEMNMBR not like '%-L%'  \n";

            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strCadena, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(), false, true);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean validacionDisponible(java.sql.Connection conExt){
        boolean blnRes=true;
        double totalCantidad=0;
        double totalRegistros=0;      

        String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="";
        if(conExt!=null)
        {
            strBodega =objUti.codificar(txtCodBod.getText());
            for(int i=0; i<objTblMod.getRowCountTrue();i++)
            {
                if(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR))>0)
                {
                    strCadenaItem = objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM))+","+ strCadenaItem;
                    strCadenaCant = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()+","+ strCadenaCant;                    
                }    
            }
            if (strCadenaItem.endsWith(",")) {
                strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                }
            if (strCadenaCant.endsWith(",")) {
                strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                
                }
            
            if((strCadenaItem!="") && (strCadenaCant !=""))
            {
                if (consultaDisponible(conExt,strCadenaItem,strCadenaCant,strBodega))
                      blnRes =true;
                else
                      blnRes =false;
            }
            else
                blnRes=false;


        }
        else
        {
            blnRes=false;
        }
        if(!blnRes)
        {
                strMsg="<html> La reposición posee items que no tienen disponibilidad. <BR><BR>" ;// CAMBIA
                String strTit="Mensaje del sistema Zafiro";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
        }            
  
        return blnRes;
    } 

private boolean consultaDisponible(java.sql.Connection conExt, String items,String cantidades,String bodega)//chiqui
    {
        
        java.sql.Statement stmGP;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSql ="";
        int intCodRes=-1;
        
        boolean blnRes=true;
        try
        {
            //conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
             
            if (conExt!=null)
            {
                stmGP = conExt.createStatement();
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("ITEMS",  items);//Envio de parametro
                cal.setString("CANTIDADES",  cantidades);//Envio de parametro
                cal.setString("BODEGA",  bodega   );//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmGP.close();
                stmGP=null;
                
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    
    private ArrayList arlRegSolTra;
    private ArrayList arlDatSolTra;
    private static final int INT_ARL_COD_CFG_SOL_TRA_VEN=2000;  // CODIGO DE LA CONFIGURACION PARA SOLICITUDES POR VENTA 
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaPreFactura(java.sql.Connection con, ArrayList arlDat,int intCodEmpCot, int intCodLocCot, int intCodDocCot){
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        try{
            if (con!=null){
                arlDatSolTra= new ArrayList();
                //objTooBar.clickInsertar();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT a1.co_seg, MAX(a1.co_reg) as co_reg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                strSQL+= " WHERE a1.co_empRelCabCotVen="+intCodEmpCot+" AND a1.co_locRelCabCotVen="+intCodLocCot+" \n";
                strSQL+= "       AND a1.co_cotRelCabCotVen="+intCodDocCot+" \n";
                strSQL+= " GROUP BY a1.co_seg  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=rstLoc.getInt("co_reg");
                }
                for(int i=0; i<arlDat.size(); i++){
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaReservas(java.sql.Connection con, ArrayList arlDat,int intCodEmpCot, int intCodLocCot, int intCodDocCot)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                 datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT a1.co_seg, MAX(a1.co_reg) as co_reg\n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                strSQL+= " WHERE a1.co_empRelCabCotVen="+intCodEmpCot+" AND a1.co_locRelCabCotVen="+intCodLocCot+" \n";
                strSQL+= "       AND a1.co_cotRelCabCotVen="+intCodDocCot+" \n";
                strSQL+= " GROUP BY a1.co_seg  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=rstLoc.getInt("co_reg");
                }
                
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaSistemas(java.sql.Connection con, ArrayList arlDat)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                //objTooBar.clickInsertar();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT MAX(a1.co_seg)  as co_seg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=1;
                }
                intCodSeg++;
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    private int getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(java.sql.Connection con,int intCodCfg){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        int intCodTipDoc=0;
        try{
            if (con!=null){
                stmLoc=con.createStatement();
                strSQL=" select co_tipDoc from tbm_cfgTipDocUtiProAut where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_cfg="+intCodCfg;
                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodTipDoc=rstLoc.getInt("co_tipDoc");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return intCodTipDoc;
    }
    
    
 
    
    
    
    
   
    
    
    public String getSQLSolTrsCab(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        String strSolTra="";

        try
        {
            strSolTra+=" SELECT a1.tx_DesCor, a1.tx_DesLar, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.nd_pesTotKgr, ((a.nd_pesTotKgr* 2.2)/100)  as nd_kilQQ , ";      
            strSolTra+="        a.co_bodOrg, b.tx_nom as NomBodOrg, b.tx_dir as DirBodOrg, a.co_bodDes, b1.tx_nom as NomBodDes, b1.tx_dir as DirBodDes, ";
            strSolTra+="        a.st_imp, a.co_usrIng, a2.tx_nom as NomUsr, a.tx_obs1 ";
            strSolTra+=" FROM tbm_cabSolTraInv as a ";
            strSolTra+=" INNER JOIN tbm_cabTipDoc as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) ";
            strSolTra+=" INNER JOIN tbm_usr as a2 ON (a2.co_usr=a.co_usrIng) ";
            strSolTra+=" INNER JOIN tbm_bod as b ON (b.co_emp=a.co_emp AND b.co_bod=a.co_bodOrg ) ";
            strSolTra+=" INNER JOIN tbm_bod as b1 ON (b1.co_emp=a.co_emp AND b1.co_bod=a.co_bodDes ) ";
            strSolTra+=" WHERE a.co_emp="+CodEmp;
            strSolTra+=" AND a.co_loc="+CodLoc;
            strSolTra+=" AND a.co_tipDoc="+CodTipDoc;
            strSolTra+=" AND a.co_doc="+CodDoc;
            //System.out.println("PK-SOLICITUD-IMPRIMIR: "+CodEmp+" - "+CodLoc+" - "+CodTipDoc+" - "+CodDoc);
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);     }
        return strSolTra;
    }
    
    public String getSQLSolTrsDet(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        String strSolTra="";

        try
        {
            strSolTra+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, a1.tx_codAlt";
            strSolTra+="       ,(substring(a1.tx_codAlt2 from 1 for 1 ) || ' ' || substring(a1.tx_codAlt2 from 2 for 1 )|| ' ' || substring(a1.tx_codAlt2 from 3 for 1 )) as tx_codAlt2 ";
            strSolTra+="       , a1.tx_nomItm, a.nd_can, a2.tx_DesCor as tx_uniMed, a.nd_pesUniKgr as nd_pesUniKgrItm, a.nd_pesTotKgr as nd_pesTotKgrItm, a5.tx_ubi";        
            strSolTra+=" FROM tbm_detSolTraInv as a";
            strSolTra+=" INNER JOIN tbm_inv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm)";
            strSolTra+=" INNER JOIN tbm_equInv as a3 ON (a3.co_Emp=a1.co_emp AND a3.co_itm=a1.co_itm)";
            strSolTra+=" INNER JOIN tbm_cabSolTraInv as a4 ON (a4.co_Emp=a.co_emp AND a4.co_loc=a.co_loc AND a4.co_tipDoc=a.co_TipDoc AND a4.co_doc=a.co_Doc)";  
            strSolTra+=" LEFT OUTER JOIN (";
            strSolTra+="     SELECT DISTINCT b3.co_empGrp, b3.co_bodGrp, b1.co_itmMae, b2.tx_ubi";
            strSolTra+="     FROM tbm_equInv AS b1";
            strSolTra+="     INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSolTra+="     INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
            strSolTra+="     INNER JOIN ";
            strSolTra+="     (";
            strSolTra+="         SELECT co_emp, co_bodDes FROM tbm_CabSolTraInv"; 
            strSolTra+="         WHERE co_emp="+CodEmp;
            strSolTra+="         AND co_loc="+CodLoc;
            strSolTra+="         AND co_tipDoc="+CodTipDoc;
            strSolTra+="         AND co_doc="+CodDoc;
            strSolTra+="     ) as b4 ON (b4.co_emp=b3.co_empGrp AND b4.co_bodDes=b3.co_bodGrp) ";       
            strSolTra+=" ) AS a5 ON (a5.co_itmMae=a3.co_itmMae AND a5.co_empGrp=a4.co_emp AND a5.co_bodGrp=a4.co_bodDes)";
            strSolTra+=" LEFT OUTER JOIN tbm_var as a2 ON (a2.co_reg=a1.co_uni)";
            strSolTra+=" WHERE a4.co_emp="+CodEmp;
            strSolTra+=" AND a4.co_loc="+CodLoc;
            strSolTra+=" AND a4.co_tipDoc="+CodTipDoc;
            strSolTra+=" AND a4.co_doc="+CodDoc;
            
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);     }
        return strSolTra;
    }
    
    /**
     * Actualiza estado de Impresión en las solicitudes de transferencias de inventario.
     * @return 
     */
    public boolean setEstImp(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc) 
    {
        boolean blnRes = false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try 
        {
            //conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                strSQL += " UPDATE tbm_cabSolTraInv";
                strSQL += " SET st_imp='S'";
                strSQL += " WHERE co_emp=" + CodEmp;
                strSQL += " AND co_loc=" + CodLoc;
                strSQL += " AND co_tipDoc=" + CodTipDoc;
                strSQL += " AND co_doc="  + CodDoc;
                strSQL += "; ";
        
                System.out.println("setEstImp" + strSQL);
                stmLoc.executeUpdate(strSQL);
                blnRes = true;
                stmLoc.close();
                conLoc.close();
                stmLoc = null;
                conLoc = null;
            }
            return blnRes;
        } 
        catch (java.sql.SQLException e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
   
    
    //269;SOTRIN;Solicitudes de transferencias de inventario
    private boolean Validaciones(){
        boolean blnRes=false;
        if(txtDesCorTipDoc.getText().equals("CONV"))
            blnRes=true;
        else
        {
            blnRes=false;
            mostrarMsgInf("<HTML>No se ha seleccionado el tipo de documento correcto.</HTML>");            
        }    
            
            
        
        if(validacionDisponible())
             blnRes=true;
        else
            blnRes=false;
        
        if(validacionCantidadEgresada())
             blnRes=true;
        else
            blnRes=false;        
        
        
        return true;
    }
    
    
    private void clickCambiaTipoDocumento(){
        try
        {
            if (rstCab!=null)
            {
                rstCab.close();
                stmCab.close();
                conCab.close();
                rstCab=null;
                stmCab=null;
                conCab=null;
            }
            limpiarFrm();
            
            //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
            datFecAux=null;
            mostrarTipDocPre();
            configurarVenBodOrg();
            //configurarVenConItm();
            txtCodBod.requestFocus();     

            if(objTooBar.getEstado()=='c'){
                dtpFecDoc.setEnabled(true);
                txtNumDoc.setEditable(true);
                txtCodDoc.setEditable(false);
                txtCostIng.setEditable(false);
            }
            else{
                dtpFecDoc.setEnabled(false);
                txtNumDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtCostIng.setEditable(false);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }
        catch (Exception e){          
            objUti.mostrarMsgErr_F1(this, e);          
        }
    }
    
    
}