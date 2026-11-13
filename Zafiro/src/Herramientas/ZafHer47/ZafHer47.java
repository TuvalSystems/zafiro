/*
 * ZafVeh05.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Herramientas.ZafHer47;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
/**
 *
 * @author  Denis Toledo
 */
public class ZafHer47 extends javax.swing.JInternalFrame 
{
    //Constantes
    
    private final int INT_TBL_CFG_COTIZACIONS=1;
    private final int INT_TBL_CFG_PEDIDOS=3;
    
    private final int INT_TBL_CFG_TEXTO_BLANCK=1;
    private final int INT_TBL_CFG_NUMEROS_NULL=2;
    
    
    
    private final int INT_MIG_1_LIN=0;
    private final int INT_MIG_1_ID=1;
    private final int INT_MIG_1_cotizacion=2;
    private final int INT_MIG_1_user_id=3;
    private final int INT_MIG_1_sucursal_id=4;
    private final int INT_MIG_1_sucursal_facturacion_id=5;
    private final int INT_MIG_1_cliente_id=6;
    private final int INT_MIG_1_prospecto_id=7;
    private final int INT_MIG_1_estado=8;
    private final int INT_MIG_1_factura_posterior_despacho=9;
    private final int INT_MIG_1_factura_a_demanda=10;
    private final int INT_MIG_1_factura_siguiente_mes=11;
    private final int INT_MIG_1_BOD_BODEGA=12;
    private final int INT_MIG_1_PTO_VTA_ID=13;
    private final int INT_MIG_1_PTO_VTA_DESCRIPCION=14;
    private final int INT_MIG_1_USERID=15;
    private final int INT_MIG_1_DESC_FORMA_PAGO_CODIGO=16;
    private final int INT_MIG_1_CLI_RUC=17;
    private final int INT_MIG_1_CLI_RAZON_SOCIAL=18;
    private final int INT_MIG_1_CLI_CORREO_ELECT_SRI=19;
    private final int INT_MIG_1_COND_ID=20;
    private final int INT_MIG_1_COND_ID_ORIGINAL=21;
    private final int INT_MIG_1_VEND_ID=22;
    private final int INT_MIG_1_VEND_NOMBRES=23;
    private final int INT_MIG_1_VEND_APELLIDOS=24;
    private final int INT_MIG_1_despacho=25;
    private final int INT_MIG_1_porcentage_descuentooriginal=26;
    private final int INT_MIG_1_porcentage_descuento=27;
    private final int INT_MIG_1_subtotal=28;
    private final int INT_MIG_1_descuento=29;
    private final int INT_MIG_1_subtotalmenosdescuento=30;
    private final int INT_MIG_1_iva=31;
    private final int INT_MIG_1_total=32;
    private final int INT_MIG_1_requiere_aprobacion_comercial=33;
    private final int INT_MIG_1_aprobado_comercial=34;
    private final int INT_MIG_1_aprobador_comercial_id=35;
    private final int INT_MIG_1_aprobado_comercial_mensaje=36;
    private final int INT_MIG_1_requiere_aprobacion_crediticia=37;
    private final int INT_MIG_1_aprobado_crediticio=38;
    private final int INT_MIG_1_aprobador_crediticio_id=39;
    private final int INT_MIG_1_nota_interna=40;
    private final int INT_MIG_1_nota_cliente=41;
    private final int INT_MIG_1_orden_de_compra=42;
    private final int INT_MIG_1_impreso=43;
    private final int INT_MIG_1_fecha_impreso=44;
    private final int INT_MIG_1_fecha_entrega=45;
    private final int INT_MIG_1_created_at=46;
    private final int INT_MIG_1_updated_at=47;
    
     

    private final int INT_MIG_2_LIN=0;
    private final int INT_MIG_2_id=1;
    private final int INT_MIG_2_cotizacion_id=2;
    private final int INT_MIG_2_ART_CODIGO=3;
    private final int INT_MIG_2_ART_DESCRIPCION=4;
    private final int INT_MIG_2_ART_DESCRIPCION_ORIGINAL=5;
    private final int INT_MIG_2_ART_CODIGO_TRES_LETRAS=6;
    private final int INT_MIG_2_ART_DECIMAL_CANTIDAD=7;
    private final int INT_MIG_2_ART_PLAN_UMEDIDA=8;
    private final int INT_MIG_2_ART_MARCA=9;
    private final int INT_MIG_2_ART_MATERIAL=10;
    private final int INT_MIG_2_ART_PESO=11;
    private final int INT_MIG_2_ART_TIPO_COMPRA=12;
    private final int INT_MIG_2_ART_VALOR=13;
    private final int INT_MIG_2_ART_IVA=14;
    private final int INT_MIG_2_ART_VALOR_ORIGINAL=15;
    private final int INT_MIG_2_ART_DESCUENTO=16;
    private final int INT_MIG_2_ART_CANTIDAD=17;
    private final int INT_MIG_2_ART_STOCK=18;
    private final int INT_MIG_2_ART_IMP_VTA=19;
    private final int INT_MIG_2_ART_COSTO_PROMEDIO=20;
    private final int INT_MIG_2_descuento=21;
    private final int INT_MIG_2_descuento_original=22;
    private final int INT_MIG_2_modificador=23;
    private final int INT_MIG_2_created_at=24;
    private final int INT_MIG_2_updated_at=25;
    
   	 

    private final int INT_MIG_3_LIN=0;       
    private final int INT_MIG_3_id=1;       
    private final int INT_MIG_3_pedido=2;       
    private final int INT_MIG_3_factura=3;       
    private final int INT_MIG_3_fecha_factura=4;       
    private final int INT_MIG_3_fecha_entrega=5;       
    private final int INT_MIG_3_estado=6;       
    private final int INT_MIG_3_factura_posterior_despacho=7;       
    private final int INT_MIG_3_factura_a_demanda=8;       
    private final int INT_MIG_3_factura_siguiente_mes=9;       
    private final int INT_MIG_3_despachado=10;       
    private final int INT_MIG_3_despacho_en_proceso=11;       
    private final int INT_MIG_3_transferencia_en_proceso=12;       
    private final int INT_MIG_3_user_id=13;       
    private final int INT_MIG_3_sucursal_id=14;       
    private final int INT_MIG_3_sucursal_facturacion_id=15;       
    private final int INT_MIG_3_cliente_id=16;       
    private final int INT_MIG_3_cotizacion_id=17;       
    private final int INT_MIG_3_BOD_BODEGA=18;       
    private final int INT_MIG_3_PTO_VTA_ID=19;       
    private final int INT_MIG_3_PTO_VTA_DESCRIPCION=20;       
    private final int INT_MIG_3_USERID=21;       
    private final int INT_MIG_3_DESC_FORMA_PAGO_CODIGO=22;       
    private final int INT_MIG_3_CLI_RUC=23;       
    private final int INT_MIG_3_CLI_RAZON_SOCIAL=24;       
    private final int INT_MIG_3_CLI_CORREO_ELECT_SRI=25;       
    private final int INT_MIG_3_COND_ID=26;       
    private final int INT_MIG_3_COND_ID_ORIGINAL=27;       
    private final int INT_MIG_3_VEND_ID=28;       
    private final int INT_MIG_3_VEND_NOMBRES=29;       
    private final int INT_MIG_3_VEND_APELLIDOS=30;       
    private final int INT_MIG_3_despacho=31;       
    private final int INT_MIG_3_porcentage_descuentooriginal=32;       
    private final int INT_MIG_3_porcentage_descuento=33;       
    private final int INT_MIG_3_subtotal=34;       
    private final int INT_MIG_3_descuento=35;       
    private final int INT_MIG_3_subtotalmenosdescuento=36;       
    private final int INT_MIG_3_iva=37;       
    private final int INT_MIG_3_total=38;       
    private final int INT_MIG_3_requiere_aprobacion_comercial=39;       
    private final int INT_MIG_3_aprobado_comercial=40;       
    private final int INT_MIG_3_aprobador_comercial_id=41;        
    private final int INT_MIG_3_aprobado_comercial_mensaje=42;       
    private final int INT_MIG_3_requiere_aprobacion_crediticia=43;       
    private final int INT_MIG_3_aprobado_crediticio=44;       
    private final int INT_MIG_3_aprobador_crediticio_id=45;       
    private final int INT_MIG_3_aprobado_crediticio_mensaje=46;       
    private final int INT_MIG_3_motivo_crediticio=47;       
    private final int INT_MIG_3_nota_interna=48;       
    private final int INT_MIG_3_nota_cliente=49;       
    private final int INT_MIG_3_orden_de_compra=50;       
    private final int INT_MIG_3_MST_FAC_VTAResult=51;       
    private final int INT_MIG_3_FAC_VTA_MENSAJE=52;       
    private final int INT_MIG_3_pedido_origen=53;       
    private final int INT_MIG_3_created_at=54;       
    private final int INT_MIG_3_updated_at=55;       
    private final int INT_MIG_3_factura_origen=56;       
    private final int INT_MIG_3_USERID_cancelado=57;       
    private final int INT_MIG_3_fecha_hora_cancelado=58; 
    private final int INT_MIG_3_fecha_hora_factura=59;
    private final int INT_MIG_3_rechazado=60;
    private final int INT_MIG_3_fecha_hora_rechazado=61;
    private final int INT_MIG_3_impuesto=62;
             
 							
   
    private final int INT_MIG_4_LIN=0;       
    private final int INT_MIG_4_id=1;       
    private final int INT_MIG_4_pedido_id=2;       
    private final int INT_MIG_4_orden=3;       
    private final int INT_MIG_4_ART_CODIGO=4;       
    private final int INT_MIG_4_ART_DESCRIPCION=5;       
    private final int INT_MIG_4_ART_DESCRIPCION_ORIGINAL=6;       
    private final int INT_MIG_4_ART_CODIGO_TRES_LETRAS=7;       
    private final int INT_MIG_4_ART_DECIMAL_CANTIDAD=8;       
    private final int INT_MIG_4_ART_PLAN_UMEDIDA=9;       
    private final int INT_MIG_4_ART_MARCA=10;       
    private final int INT_MIG_4_ART_MATERIAL=11;       
    private final int INT_MIG_4_ART_PESO=12;       
    private final int INT_MIG_4_ART_TIPO_COMPRA=13;       
    private final int INT_MIG_4_ART_VALOR=14;       
    private final int INT_MIG_4_ART_IVA=15;       
    private final int INT_MIG_4_ART_VALOR_ORIGINAL=16;       
    private final int INT_MIG_4_ART_DESCUENTO=17;       
    private final int INT_MIG_4_ART_CANTIDAD=18;       
    private final int INT_MIG_4_ART_STOCK=19;       
    private final int INT_MIG_4_ART_IMP_VTA=20;       
    private final int INT_MIG_4_ART_COSTO_PROMEDIO=21;       
    private final int INT_MIG_4_descuento=22;       
    private final int INT_MIG_4_descuento_original=23;       
    private final int INT_MIG_4_modificador=24;       
    private final int INT_MIG_4_reservado=25;       
    private final int INT_MIG_4_trasladado=26;       
    private final int INT_MIG_4_devuelto=27;       
    private final int INT_MIG_4_cancelado=28;       
    private final int INT_MIG_4_created_at=29;       
    private final int INT_MIG_4_updated_at=30;       
    private final int INT_MIG_4_traslado=31;    
     
    
    private final int INT_MIG_5_LIN=0;
    private final int INT_MIG_5_id=1;
    private final int INT_MIG_5_guia_remision_id=2;
    private final int INT_MIG_5_codigo=3;
    private final int INT_MIG_5_pedido=4;
    private final int INT_MIG_5_created_at=5;
    private final int INT_MIG_5_updated_at=6; 
    
    
    
    private final int INT_MIG_6_LIN=0;       
    private final int INT_MIG_6_id=1;       
    private final int INT_MIG_6_GUI_REM_CODIGO=2;       
    private final int INT_MIG_6_tipo=3;       
    private final int INT_MIG_6_estado=4;    
    private final int INT_MIG_6_bodega_origen=5;    
    private final int INT_MIG_6_bodega_destino=6;    
    private final int INT_MIG_6_pedido=7;    
    private final int INT_MIG_6_GUI_REM_BODEGA=8;    
    private final int INT_MIG_6_GUI_REM_GUIA_REMISION=9;    
    private final int INT_MIG_6_GUI_REM_ESTABLECIMIENTO=10;    
    private final int INT_MIG_6_GUI_REM_PTO_EMISION=11;    
    private final int INT_MIG_6_GUI_REM_SECUENCIAL=12;    
    private final int INT_MIG_6_GUI_REM_DIRECCION_PARTIDA=13;    
    private final int INT_MIG_6_GUI_REM_RAZON_SOCIAL_TRANSPORTISTA=14; 
    private final int INT_MIG_6_GUI_REM_IDENTIFICACION_TRANSPORTISTA=15;
    private final int INT_MIG_6_GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA=16;    
    private final int INT_MIG_6_GUI_REM_FECHA_INICIO=17;    
    private final int INT_MIG_6_GUI_REM_FECHA_FIN=18;    
    private final int INT_MIG_6_GUI_REM_PLACA=19;    
    private final int INT_MIG_6_GUI_REM_OBSERVACION=20;    
    private final int INT_MIG_6_GUI_REM_DIRECCION_DESTINATARIO=21;    
    private final int INT_MIG_6_GUI_REM_RAZON_SOCIAL_DESTINATARIO=22;   
    private final int INT_MIG_6_GUI_REM_IDENTIFICACION_DESTINATARIO=23;    
    private final int INT_MIG_6_GUI_REM_MOTIVO_TRASLADO=24;    
    private final int INT_MIG_6_GUI_REM_RUTA=25;  
    private final int INT_MIG_6_GUI_REM_CODI_DOCU_SUSTENTO=26;  
    private final int INT_MIG_6_GUI_REM_NUME_DOCU_SUSTENTO=27;    
    private final int INT_MIG_6_GUI_REM_NUME_AUTO_DOCU_SUSTENTO=28;    
    private final int INT_MIG_6_GUI_REM_FECHA_EMISION_DOCU_SUSTENTO=29;    
    private final int INT_MIG_6_rebajado=30;  
    private final int INT_MIG_6_recibidos=31;    
    private final int INT_MIG_6_created_at=32;    
    private final int INT_MIG_6_updated_at=33;    
    private final int INT_MIG_6_user_id=34; 
    
    
    private final int INT_MIG_7_LIN=0;       
    private final int INT_MIG_7_id=1;       
    private final int INT_MIG_7_detalle_id=2;       
    private final int INT_MIG_7_guia_remision_id=3;       
    private final int INT_MIG_7_GUI_REM_CODI_INTERNO=4;       
    private final int INT_MIG_7_GUI_REM_DESCRIPCION=5;    
    private final int INT_MIG_7_GUI_REM_CODI_ADICIONAL=6;    
    private final int INT_MIG_7_GUI_REM_CANTIDAD=7;    
    private final int INT_MIG_7_GUI_REM_UNIDAD_MEDIDA=8;    
    private final int INT_MIG_7_recibido=9;    
    private final int INT_MIG_7_created_at=10;    
    private final int INT_MIG_7_updated_at=11;    
    
    
    
    
    private final int INT_MIG_8_LIN=0;
    private final int INT_MIG_8_id=1;
    private final int INT_MIG_8_devolucion=2;
    private final int INT_MIG_8_DOC_GENERADO=3;
    private final int INT_MIG_8_fecha_NC=4;
    private final int INT_MIG_8_estado=5;
    private final int INT_MIG_8_pedido_id=6;
    private final int INT_MIG_8_cliente_id=7;
    private final int INT_MIG_8_pedido=8;
    private final int INT_MIG_8_factura=9;
    private final int INT_MIG_8_volver_a_facturar=10;
    private final int INT_MIG_8_tipo_devolucion=11;
    private final int INT_MIG_8_despachado=12;
    private final int INT_MIG_8_cliente_volver_a_facturar_id=13;
    private final int INT_MIG_8_user_id=14;
    private final int INT_MIG_8_USERID=15;    
    private final int INT_MIG_8_sucursal_id=16;
    private final int INT_MIG_8_sucursal_facturacion_id=17;
    private final int INT_MIG_8_motivo=18;
    private final int INT_MIG_8_orden_de_compra=19;
    private final int INT_MIG_8_requiere_aprobacion=20;
    private final int INT_MIG_8_aprobado=21;
    private final int INT_MIG_8_aprobador_id=22;
    private final int INT_MIG_8_aprobado_mensaje=23;
    private final int INT_MIG_8_NC_VTA_MENSAJE=24;
    private final int INT_MIG_8_devolucion_bodega=25;
    private final int INT_MIG_8_rebajado=26;
    private final int INT_MIG_8_created_at=27;
    private final int INT_MIG_8_updated_at=28;
    private final int INT_MIG_8_procesando=29;
    
    
    private final int INT_MIG_9_LIN=0;
    private final int INT_MIG_9_id=1;
    private final int INT_MIG_9_devolucion_id=2;
    private final int INT_MIG_9_detalle_id=3;
    private final int INT_MIG_9_ART_CODIGO=4;
    private final int INT_MIG_9_ART_DESCRIPCION=5;
    private final int INT_MIG_9_ART_DESCRIPCION_ORIGINAL=6;
    private final int INT_MIG_9_ART_CODIGO_TRES_LETRAS=7;
    private final int INT_MIG_9_ART_DECIMAL_CANTIDAD=8;
    private final int INT_MIG_9_ART_PLAN_UMEDIDA=9;
    private final int INT_MIG_9_ART_MARCA=10;
    private final int INT_MIG_9_ART_MATERIAL=11;
    private final int INT_MIG_9_ART_PESO=12;
    private final int INT_MIG_9_ART_TIPO_COMPRA=13;
    private final int INT_MIG_9_ART_VALOR=14;
    private final int INT_MIG_9_ART_IVA=15;
    private final int INT_MIG_9_ART_VALOR_ORIGINAL=16;
    private final int INT_MIG_9_ART_DESCUENTO=17;
    private final int INT_MIG_9_ART_CANTIDAD=18;
    private final int INT_MIG_9_ART_STOCK=19;
    private final int INT_MIG_9_ART_IMP_VTA=20;
    private final int INT_MIG_9_ART_COSTO_PROMEDIO=21;
    private final int INT_MIG_9_descuento=22;
    private final int INT_MIG_9_descuento_original=23;
    private final int INT_MIG_9_modificador=24;
    private final int INT_MIG_9_seleccionado=25;
    private final int INT_MIG_9_devuelto=26;
    private final int INT_MIG_9_disponible=27;
    private final int INT_MIG_9_recibido=28;
    private final int INT_MIG_9_compartimiento=29;
    private final int INT_MIG_9_created_at=30;
    private final int INT_MIG_9_updated_at=31;
    
    
    private final int INT_MIG_10_LIN=0;
    private final int INT_MIG_10_id=1;
    private final int INT_MIG_10_PROV_RUC=2;
    private final int INT_MIG_10_PROV_RAZON_SOCIAL=3;
    private final int INT_MIG_10_PROV_CLASE=4;
    private final int INT_MIG_10_PROV_ID_DIRECCION=5;
    private final int INT_MIG_10_PROV_CONTACTO=6;
    private final int INT_MIG_10_PROV_DIRECCION_1=7;
    private final int INT_MIG_10_PROV_DIRECCION_2=8;
    private final int INT_MIG_10_PROV_DIRECCION_3=9;
    private final int INT_MIG_10_PROV_CIUDAD=10;
    private final int INT_MIG_10_PROV_PROVINCIA=11;
    private final int INT_MIG_10_PROV_PAIS=12;
    private final int INT_MIG_10_PROV_TELEFONO_1=13;
    private final int INT_MIG_10_PROV_TELEFONO_2=14;
    private final int INT_MIG_10_PROV_TELEFONO_3=15;
    private final int INT_MIG_10_PROV_TELEFONO_4=16;
    private final int INT_MIG_10_PROV_CONDICION_PAGO=17;
    private final int INT_MIG_10_PROV_DSTO_COMERCIAL=18;
    private final int INT_MIG_10_PROV_FECHA_MODIFICACION=19;
    private final int INT_MIG_10_PROV_FECHA_CREACION=20;
    private final int INT_MIG_10_PROV_CORREO_ELECT=21;
    private final int INT_MIG_10_PROV_IMPUESTO=22;
    private final int INT_MIG_10_PROV_ESTADO=23;
    private final int INT_MIG_10_created_at=24;
    private final int INT_MIG_10_updated_at=25;
    
    
    private final int INT_MIG_11_LIN=0;
    private final int INT_MIG_11_id=1;
    private final int INT_MIG_11_detalle_id=2;
    private final int INT_MIG_11_detalle_proveedores_id=3;
    private final int INT_MIG_11_estado=4;
    private final int INT_MIG_11_ORDEN_COMPRA_DOCU_ORIGEN=5;
    private final int INT_MIG_11_ORDEN_COMPRA_LINEA=6;
    private final int INT_MIG_11_DOC_GENERADO=7;
    private final int INT_MIG_11_ORDEN_COMPRA_FECHA_DOCUMENTO=8;
    private final int INT_MIG_11_ORDEN_COMPRA_FECHA_ENTREGA=9;
    private final int INT_MIG_11_ORDEN_COMPRA_ID_CLIENTE=10;
    private final int INT_MIG_11_ORDEN_COMPRA_ID_PROVEEDOR=11;
    private final int INT_MIG_11_ORDEN_COMPRA_ID_DIRECCION=12;
    private final int INT_MIG_11_ORDEN_COMPRA_NOTA_DOCU=13;
    private final int INT_MIG_11_ORDEN_COMPRA_BODEGA=14;
    private final int INT_MIG_11_ORDEN_COMPRA_ARTICULO=15;
    private final int INT_MIG_11_ORDEN_COMPRA_DESCRIPCION_ARTICULO=16;
    private final int INT_MIG_11_ORDEN_COMPRA_UMEDIDA=17;
    private final int INT_MIG_11_ORDEN_COMPRA_CANTIDAD=18;
    private final int INT_MIG_11_ORDEN_COMPRA_COSTO_UNITARIO=19;
    private final int INT_MIG_11_ORDEN_COMPRA_TOTAL=20;
    private final int INT_MIG_11_ORDEN_COMPRA_IMPUESTO=21;
    private final int INT_MIG_11_ORDEN_COMPRA_ID_USUARIO=22;
    private final int INT_MIG_11_created_at=23;
    private final int INT_MIG_11_updated_at=24;
    private final int INT_MIG_11_ORDEN_COMPRA_MENSAJE=25;
    
    
    private final int INT_MIG_12_LIN=0;
    private final int INT_MIG_12_id=1;
    private final int INT_MIG_12_orden_id=2;
    private final int INT_MIG_12_detalle_id=3;
    private final int INT_MIG_12_detalle_proveedores_id=4;
    private final int INT_MIG_12_estado=5;
    private final int INT_MIG_12_ORDEN_COMPRA_DOCUMENTO=6;
    private final int INT_MIG_12_ORDEN_COMPRA_FECHA_DOCUMENTO=7;
    private final int INT_MIG_12_ORDEN_COMPRA_FECHA_ENTREGA=8;
    private final int INT_MIG_12_ORDEN_COMPRA_ID_PROVEEDOR=9;
    private final int INT_MIG_12_ORDEN_COMPRA_DIRECCION=10;
    private final int INT_MIG_12_ORDEN_COMPRA_NOTA_DOCU=11;
    private final int INT_MIG_12_ORDEN_COMPRA_RECIBO=12;
    private final int INT_MIG_12_USERID=13;
    private final int INT_MIG_12_recibidos=14;
    private final int INT_MIG_12_created_at=15;
    private final int INT_MIG_12_updated_at=16; 
    
    private final int INT_MIG_13_LIN=0;
    private final int INT_MIG_13_id=1;
    private final int INT_MIG_13_orden_generada_id=2;
    private final int INT_MIG_13_detalle_proveedores_id=3;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_BODEGA=4;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_ARTICULO=5;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO=6;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_UMEDIDA=7;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_CANTIDAD=8;
    private final int INT_MIG_13_recibido=9;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_COSTO_UNITARIO=10;
    private final int INT_MIG_13_ORDEN_ORDEN_COMPRA_DETALLE_TOTAL=11;
    private final int INT_MIG_13_ORDEN_COMPRA_DETALLE_IMPUESTO=12;
    private final int INT_MIG_13_created_at=13;
    private final int INT_MIG_13_updated_at=14; 
    
    
    private final int INT_MIG_14_LIN=0;
    private final int INT_MIG_14_id=1;
    private final int INT_MIG_14_pedido_id=2;
    private final int INT_MIG_14_orden=3;
    private final int INT_MIG_14_estado=4;
    private final int INT_MIG_14_PTO_VTA_ID=5;
    private final int INT_MIG_14_BOD_BODEGA=6;
    private final int INT_MIG_14_autorizacion_fecha=7;
    private final int INT_MIG_14_autorizacion_user_id=8;
    private final int INT_MIG_14_autorizacion_USERID=9;
    private final int INT_MIG_14_autorizacion_comentario=10;
    private final int INT_MIG_14_created_at=11;
    private final int INT_MIG_14_updated_at=12;
    private final int INT_MIG_14_fecha_hora_liberacion=13;
    
    private final int INT_MIG_15_LIN=0;
    private final int INT_MIG_15_id=1;
    private final int INT_MIG_15_pedido_id=2;
    private final int INT_MIG_15_detalle_id=3;
    private final int INT_MIG_15_detalle_stocks_id=4;
    private final int INT_MIG_15_ART_CODIGO=5;
    private final int INT_MIG_15_ART_DESCRIPCION=6;
    private final int INT_MIG_15_ART_CODIGO_TRES_LETRAS=7;
    private final int INT_MIG_15_ART_PLAN_UMEDIDA=8;
    private final int INT_MIG_15_ART_PESO=9;
    private final int INT_MIG_15_cantidad=10;
    private final int INT_MIG_15_bodega_origen=11;
    private final int INT_MIG_15_bodega_destino=12;
    private final int INT_MIG_15_reservado=13;
    private final int INT_MIG_15_trasladado=14;
    private final int INT_MIG_15_completado=15;
    private final int INT_MIG_15_created_at=16; 
    private final int INT_MIG_15_updated_at=17;
    private final int INT_MIG_15_picking=18; 
    
    
    private final int INT_MIG_16_LIN=0;
    private final int INT_MIG_16_id=1;
    private final int INT_MIG_16_cabecera_id=2;
    private final int INT_MIG_16_Documento=3;
    private final int INT_MIG_16_Codigo_Motivo=4;
    private final int INT_MIG_16_ART_CODIGO=5;
    private final int INT_MIG_16_ART_DESCRIPCION=6;
    private final int INT_MIG_16_ART_CODIGO_TRES_LETRAS=7;
    private final int INT_MIG_16_ART_PLAN_UMEDIDA=8;
    private final int INT_MIG_16_ART_PESO=9;
    private final int INT_MIG_16_cantidad=10;
    private final int INT_MIG_16_bodega_origen=11;
    private final int INT_MIG_16_bodega_destino=12;
    private final int INT_MIG_16_compartimiento=13;
    private final int INT_MIG_16_Fecha_Documento=14;
    private final int INT_MIG_16_reservado=15;
    private final int INT_MIG_16_trasladado=16; 
    private final int INT_MIG_16_completado=17;
    private final int INT_MIG_16_created_at=18;
    private final int INT_MIG_16_updated_at=19;
    private final int INT_MIG_16_picking=20;
    
    private final int INT_MIG_17_LIN=0;
    private final int INT_MIG_17_id=1;
    private final int INT_MIG_17_Documento=2;
    private final int INT_MIG_17_Bodega_Origen=3;
    private final int INT_MIG_17_Bodega_Destino=4;
    private final int INT_MIG_17_Codigo_Motivo=5;
    private final int INT_MIG_17_Motivo=6;
    private final int INT_MIG_17_Fecha_Documento=7;
    private final int INT_MIG_17_created_at=8;
    private final int INT_MIG_17_updated_at=9;
    
    private Vector vecDat, vecCab, vecReg;
    
    private String strSql="";
    private int intUltDocIng=-1;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;                                     //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod, objTblModDetCot, objTblModPed, objTblModPedDet, objTblModGuiaTrans, objTblModGuias, objTblModGuiasDetalles, 
            objTblModDevoluciones, objTblModDevDetalles,objTblModProveedores, objTblModOrdenCompra, objTblModOrdComGenerados,objTblModOCGenDetalles,
            objTblModODespachos, objTblModTrasladosVentas,objTblModTrasladosRep,objTblModTrasladosRepCab;
    private ZafColNumerada objColNum, objColNumDetCot, objColNumPedidos, objColNumPedidosDetalle, 
            objColNumGuiaTrans, objColNumGuias, objColNumGuiasDetalles, objColNumDevoluciones, 
            objColNumDevDetalles, objColNumProveedores, objColNumOrdenCompra, objColNumOrdComGenerados, 
            objColNumOCGenDetalles, objColNumODespachos, objColNumTrasladosVentas,objColNumTrasladosRep,objColNumTrasladosRepCab;
    
    private ZafTblPopMnu objTblPopMnu;
   
    private ZafTblFilCab objTblFilCab, objTblFilCabDetCot, objTblFilCabPedidos, objTblFilCabPedidosDetalle, objTblFilCabGuiaTrans, objTblFilCabGuias, objTblFilCabGuiasDetalles;
    private ZafTblFilCab objTblFilCabDevoluciones, objTblFilCabDevDetalles,objTblFilCabProveedores, 
            objTblFilCabOrdenCompra, objTblFilCabOrdComGenerados, objTblFilCabOCGenDetalles, objTblFilCabODespachos, objTblFilCabTrasladosVentas,objTblFilCabTrasladosRep,objTblFilCabTrasladosRepCab;
                           
    
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
       
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private ZafThreadGUI objThrGUI;

    private boolean blnHayCam;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. 
     
    
    private String  strAux;
     
    
    /** Creates new form ZafVeh05 */
    public ZafHer47(ZafParSis obj){
        try{
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
        /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti = new ZafUtil();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + "v0.08");
            lblTit.setText(strAux);
             
            //Configurar los JTables.
            configurarTblDatCotizaciones();
            configurarTblDatCotizacionesDetalle();
            configurarTblDatPedidos();
            configurarTblDatPedidosDetalle();
            configurarTblDatGuiaRemisionTransferencias();
            configurarTblDatGuias();
            configurarTblDatGuiasDetalles();
            configurarTblDatDevoluciones();
            configurarTblDatDevDetalles();
            configurarTblDatProveedores();
            configurarTblDatOrdenCompra();
            configurarTblDatOrdComGenerados();
            configurarTblDatOCGenDetalles();
            configurarTblDatODespachos();
            configurarTblDatTrasladosVentas();
            configurarTblDatTrasladosReposiciones();
            configurarTblDatTrasladosReposicionesCab();
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
   /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
        private boolean configurarTblDatPedidosDetalle()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_4_LIN,"");
            vecCab.add(INT_MIG_4_id,"id");
            vecCab.add(INT_MIG_4_pedido_id,"pedido_id");
            vecCab.add(INT_MIG_4_orden,"orden");
            vecCab.add(INT_MIG_4_ART_CODIGO,"ART_CODIGO");
            vecCab.add(INT_MIG_4_ART_DESCRIPCION,"ART_DESCRIPCION");
            vecCab.add(INT_MIG_4_ART_DESCRIPCION_ORIGINAL,"ART_DESCRIPCION_ORIGINAL");
            vecCab.add(INT_MIG_4_ART_CODIGO_TRES_LETRAS,"ART_CODIGO_TRES_LETRAS");
            vecCab.add(INT_MIG_4_ART_DECIMAL_CANTIDAD,"ART_DECIMAL_CANTIDAD");
            vecCab.add(INT_MIG_4_ART_PLAN_UMEDIDA,"ART_PLAN_UMEDIDA");
            vecCab.add(INT_MIG_4_ART_MARCA,"ART_MARCA");
            vecCab.add(INT_MIG_4_ART_MATERIAL,"ART_MATERIAL");
            vecCab.add(INT_MIG_4_ART_PESO,"ART_PESO");
            vecCab.add(INT_MIG_4_ART_TIPO_COMPRA,"ART_TIPO_COMPRA");
            vecCab.add(INT_MIG_4_ART_VALOR,"ART_VALOR");
            vecCab.add(INT_MIG_4_ART_IVA,"ART_IVA");
            vecCab.add(INT_MIG_4_ART_VALOR_ORIGINAL,"ART_VALOR_ORIGINAL");
            vecCab.add(INT_MIG_4_ART_DESCUENTO,"ART_DESCUENTO");
            vecCab.add(INT_MIG_4_ART_CANTIDAD,"ART_CANTIDAD");
            vecCab.add(INT_MIG_4_ART_STOCK,"ART_STOCK");
            vecCab.add(INT_MIG_4_ART_IMP_VTA,"ART_IMP_VTA");
            vecCab.add(INT_MIG_4_ART_COSTO_PROMEDIO,"ART_COSTO_PROMEDIO");
            vecCab.add(INT_MIG_4_descuento,"descuento");
            vecCab.add(INT_MIG_4_descuento_original,"descuento_original");
            vecCab.add(INT_MIG_4_modificador,"modificador");
            vecCab.add(INT_MIG_4_reservado,"reservado");
            vecCab.add(INT_MIG_4_trasladado,"trasladado");
            vecCab.add(INT_MIG_4_devuelto,"devuelto");
            vecCab.add(INT_MIG_4_cancelado,"cancelado");
            vecCab.add(INT_MIG_4_created_at,"created_at");
            vecCab.add(INT_MIG_4_updated_at,"updated_at");
            vecCab.add(INT_MIG_4_traslado,"traslado");
            
            objTblModPedDet=new ZafTblMod();
            objTblModPedDet.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblPedDetalle.setModel(objTblModPedDet);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblPedDetalle.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblPedDetalle.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPedidosDetalle=new ZafColNumerada(tblPedDetalle,INT_MIG_4_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblPedDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblPedDetalle.getColumnModel();
            tcmAux.getColumn(INT_MIG_4_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_4_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_pedido_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_orden).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_DESCRIPCION_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_CODIGO_TRES_LETRAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_DECIMAL_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_PLAN_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_MARCA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_MATERIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_TIPO_COMPRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_VALOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_VALOR_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_DESCUENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_STOCK).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_IMP_VTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_ART_COSTO_PROMEDIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_descuento_original).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_modificador).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_reservado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_trasladado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_devuelto).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_cancelado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_4_traslado).setPreferredWidth(50);
            
        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabPedidosDetalle=new ZafTblFilCab(tblPedDetalle);
            tcmAux.getColumn(INT_MIG_4_LIN).setCellRenderer(objTblFilCabPedidosDetalle);
            objTblFilCabPedidosDetalle=null;
             
            objTblModPedDet.setModoOperacion(objTblModPedDet.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarTblDatProveedores()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_10_LIN,"");
            vecCab.add(INT_MIG_10_id,"id");
            vecCab.add(INT_MIG_10_PROV_RUC,"PROV_RUC");
            vecCab.add(INT_MIG_10_PROV_RAZON_SOCIAL,"PROV_RAZON_SOCIAL");
            vecCab.add(INT_MIG_10_PROV_CLASE,"PROV_CLASE");
            vecCab.add(INT_MIG_10_PROV_ID_DIRECCION,"PROV_ID_DIRECCION");
            vecCab.add(INT_MIG_10_PROV_CONTACTO,"PROV_CONTACTO");
            vecCab.add(INT_MIG_10_PROV_DIRECCION_1,"PROV_DIRECCION_1");
            vecCab.add(INT_MIG_10_PROV_DIRECCION_2,"PROV_DIRECCION_2");
            vecCab.add(INT_MIG_10_PROV_DIRECCION_3,"PROV_DIRECCION_3");
            vecCab.add(INT_MIG_10_PROV_CIUDAD,"PROV_CIUDAD");
            vecCab.add(INT_MIG_10_PROV_PROVINCIA,"PROV_PROVINCIA");
            vecCab.add(INT_MIG_10_PROV_PAIS,"PROV_PAIS");
            vecCab.add(INT_MIG_10_PROV_TELEFONO_1,"PROV_TELEFONO_1");
            vecCab.add(INT_MIG_10_PROV_TELEFONO_2,"PROV_TELEFONO_2");
            vecCab.add(INT_MIG_10_PROV_TELEFONO_3,"PROV_TELEFONO_3");
            vecCab.add(INT_MIG_10_PROV_TELEFONO_4,"PROV_TELEFONO_4");
            vecCab.add(INT_MIG_10_PROV_CONDICION_PAGO,"PROV_CONDICION_PAGO");
            vecCab.add(INT_MIG_10_PROV_DSTO_COMERCIAL,"PROV_DSTO_COMERCIAL");
            vecCab.add(INT_MIG_10_PROV_FECHA_MODIFICACION,"PROV_FECHA_MODIFICACION");
            vecCab.add(INT_MIG_10_PROV_FECHA_CREACION,"PROV_FECHA_CREACION");
            vecCab.add(INT_MIG_10_PROV_CORREO_ELECT,"PROV_CORREO_ELECT");
            vecCab.add(INT_MIG_10_PROV_IMPUESTO,"PROV_IMPUESTO");
            vecCab.add(INT_MIG_10_PROV_ESTADO,"PROV_ESTADO");
            vecCab.add(INT_MIG_10_created_at,"created_at");
            vecCab.add(INT_MIG_10_updated_at,"updated_at");

            
            objTblModProveedores=new ZafTblMod();
            objTblModProveedores.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblProveedores.setModel(objTblModProveedores);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblProveedores.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblProveedores.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumProveedores=new ZafColNumerada(tblProveedores,INT_MIG_10_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblProveedores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblProveedores.getColumnModel();
            tcmAux.getColumn(INT_MIG_10_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_10_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_RUC).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_RAZON_SOCIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_CLASE).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_ID_DIRECCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_CONTACTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_DIRECCION_1).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_DIRECCION_2).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_DIRECCION_3).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_CIUDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_PROVINCIA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_PAIS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_TELEFONO_1).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_TELEFONO_2).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_TELEFONO_3).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_TELEFONO_4).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_CONDICION_PAGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_DSTO_COMERCIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_FECHA_MODIFICACION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_FECHA_CREACION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_CORREO_ELECT).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_IMPUESTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_PROV_ESTADO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_10_updated_at).setPreferredWidth(50);

            
        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabProveedores=new ZafTblFilCab(tblProveedores);
            tcmAux.getColumn(INT_MIG_10_LIN).setCellRenderer(objTblFilCabProveedores);
            objTblFilCabProveedores=null;
             
            objTblModProveedores.setModoOperacion(objTblModProveedores.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatDevoluciones()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_8_LIN,"");
            vecCab.add(INT_MIG_8_id,"id");
            vecCab.add(INT_MIG_8_devolucion,"devolucion");
            vecCab.add(INT_MIG_8_DOC_GENERADO,"DOC_GENERADO");
            vecCab.add(INT_MIG_8_fecha_NC,"fecha_NC");
            vecCab.add(INT_MIG_8_estado,"estado");
            vecCab.add(INT_MIG_8_pedido_id,"pedido_id");
            vecCab.add(INT_MIG_8_cliente_id,"cliente_id");
            vecCab.add(INT_MIG_8_pedido,"pedido");
            vecCab.add(INT_MIG_8_factura,"factura");
            vecCab.add(INT_MIG_8_volver_a_facturar,"volver_a_facturar");
            vecCab.add(INT_MIG_8_tipo_devolucion,"tipo_devolucion");
            vecCab.add(INT_MIG_8_despachado,"despachado");
            vecCab.add(INT_MIG_8_cliente_volver_a_facturar_id,"cliente_volver_a_facturar_id"); 
            vecCab.add(INT_MIG_8_user_id,"user_id");
            vecCab.add(INT_MIG_8_USERID,"USERID");
            vecCab.add(INT_MIG_8_sucursal_id,"sucursal_id");
            vecCab.add(INT_MIG_8_sucursal_facturacion_id,"sucursal_facturacion_id");
            vecCab.add(INT_MIG_8_motivo,"motivo");
            vecCab.add(INT_MIG_8_orden_de_compra,"orden_de_compra");
            vecCab.add(INT_MIG_8_requiere_aprobacion,"requiere_aprobacion");
            vecCab.add(INT_MIG_8_aprobado,"aprobado");
            vecCab.add(INT_MIG_8_aprobador_id,"aprobador_id");
            vecCab.add(INT_MIG_8_aprobado_mensaje,"aprobado_mensaje");
            vecCab.add(INT_MIG_8_NC_VTA_MENSAJE,"NC_VTA_MENSAJE");
            vecCab.add(INT_MIG_8_devolucion_bodega,"devolucion_bodega");
            vecCab.add(INT_MIG_8_rebajado,"rebajado");
            vecCab.add(INT_MIG_8_created_at,"created_at");
            vecCab.add(INT_MIG_8_updated_at,"updated_at");
            vecCab.add(INT_MIG_8_procesando,"procesando");
              
            objTblModDevoluciones=new ZafTblMod();
            objTblModDevoluciones.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDevoluciones.setModel(objTblModDevoluciones);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDevoluciones.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDevoluciones.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumDevoluciones=new ZafColNumerada(tblDevoluciones,INT_MIG_8_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDevoluciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDevoluciones.getColumnModel();
            tcmAux.getColumn(INT_MIG_8_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_8_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_devolucion).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_DOC_GENERADO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_fecha_NC).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_pedido_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_cliente_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_pedido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_factura).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_volver_a_facturar).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_tipo_devolucion).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_despachado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_cliente_volver_a_facturar_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_user_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_USERID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_sucursal_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_sucursal_facturacion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_motivo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_orden_de_compra).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_requiere_aprobacion).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_aprobado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_aprobador_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_aprobado_mensaje).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_NC_VTA_MENSAJE).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_devolucion_bodega).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_rebajado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_8_procesando).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabDevoluciones=new ZafTblFilCab(tblDevoluciones);
            tcmAux.getColumn(INT_MIG_8_LIN).setCellRenderer(objTblFilCabDevoluciones);
            objTblFilCabDevoluciones=null;
            
            objTblModDevoluciones.setModoOperacion(objTblModDevoluciones.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatDevDetalles()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_9_LIN,"");
            vecCab.add(INT_MIG_9_id,"id");
            vecCab.add(INT_MIG_9_devolucion_id,"devolucion_id");
            vecCab.add(INT_MIG_9_detalle_id,"detalle_id");
            vecCab.add(INT_MIG_9_ART_CODIGO,"ART_CODIGO");
            vecCab.add(INT_MIG_9_ART_DESCRIPCION,"ART_DESCRIPCION");
            vecCab.add(INT_MIG_9_ART_DESCRIPCION_ORIGINAL,"ART_DESCRIPCION_ORIGINAL");
            vecCab.add(INT_MIG_9_ART_CODIGO_TRES_LETRAS,"ART_CODIGO_TRES_LETRAS");
            vecCab.add(INT_MIG_9_ART_DECIMAL_CANTIDAD,"ART_DECIMAL_CANTIDAD");
            vecCab.add(INT_MIG_9_ART_PLAN_UMEDIDA,"ART_PLAN_UMEDIDA");
            vecCab.add(INT_MIG_9_ART_MARCA,"ART_MARCA");
            vecCab.add(INT_MIG_9_ART_MATERIAL,"ART_MATERIAL");
            vecCab.add(INT_MIG_9_ART_PESO,"ART_PESO");
            vecCab.add(INT_MIG_9_ART_TIPO_COMPRA,"ART_TIPO_COMPRA");
            vecCab.add(INT_MIG_9_ART_VALOR,"ART_VALOR");
            vecCab.add(INT_MIG_9_ART_IVA,"ART_IVA");
            vecCab.add(INT_MIG_9_ART_VALOR_ORIGINAL,"ART_VALOR_ORIGINAL");
            vecCab.add(INT_MIG_9_ART_DESCUENTO,"ART_DESCUENTO");
            vecCab.add(INT_MIG_9_ART_CANTIDAD,"ART_CANTIDAD");
            vecCab.add(INT_MIG_9_ART_STOCK,"ART_STOCK");
            vecCab.add(INT_MIG_9_ART_IMP_VTA,"ART_IMP_VTA");
            vecCab.add(INT_MIG_9_ART_COSTO_PROMEDIO,"ART_COSTO_PROMEDIO");
            vecCab.add(INT_MIG_9_descuento,"descuento");
            vecCab.add(INT_MIG_9_descuento_original,"descuento_original");
            vecCab.add(INT_MIG_9_modificador,"modificador");
            vecCab.add(INT_MIG_9_seleccionado,"reservado");
            vecCab.add(INT_MIG_9_devuelto,"trasladado");
            vecCab.add(INT_MIG_9_disponible,"devuelto");
            vecCab.add(INT_MIG_9_recibido,"cancelado");
            vecCab.add(INT_MIG_9_compartimiento,"created_at");
            vecCab.add(INT_MIG_9_created_at,"updated_at");
            vecCab.add(INT_MIG_9_updated_at,"traslado");
                
            
            objTblModDevDetalles=new ZafTblMod();
            objTblModDevDetalles.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDevolucionesDetalles.setModel(objTblModDevDetalles);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDevolucionesDetalles.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDevolucionesDetalles.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPedidosDetalle=new ZafColNumerada(tblDevolucionesDetalles,INT_MIG_9_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDevolucionesDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDevolucionesDetalles.getColumnModel();
            tcmAux.getColumn(INT_MIG_9_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_9_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_devolucion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_detalle_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_DESCRIPCION_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_CODIGO_TRES_LETRAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_DECIMAL_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_PLAN_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_MARCA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_MATERIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_TIPO_COMPRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_VALOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_VALOR_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_DESCUENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_STOCK).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_IMP_VTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_ART_COSTO_PROMEDIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_descuento_original).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_modificador).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_seleccionado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_devuelto).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_disponible).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_recibido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_compartimiento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_9_updated_at).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabDevDetalles=new ZafTblFilCab(tblDevolucionesDetalles);
            tcmAux.getColumn(INT_MIG_9_LIN).setCellRenderer(objTblFilCabDevDetalles);
            objTblFilCabDevDetalles=null;
             
            objTblModDevDetalles.setModoOperacion(objTblModDevDetalles.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    
    private boolean configurarTblDatGuias(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_6_LIN,"");
            vecCab.add(INT_MIG_6_id,"id");
            
            vecCab.add(INT_MIG_6_GUI_REM_CODIGO,"GUI_REM_CODIGO");
            vecCab.add(INT_MIG_6_tipo,"tipo");
            vecCab.add(INT_MIG_6_estado,"estado");
            vecCab.add(INT_MIG_6_bodega_origen,"bodega_origen");
            vecCab.add(INT_MIG_6_bodega_destino,"bodega_destino");
            vecCab.add(INT_MIG_6_pedido,"pedido");
            vecCab.add(INT_MIG_6_GUI_REM_BODEGA,"GUI_REM_BODEGA");
            vecCab.add(INT_MIG_6_GUI_REM_GUIA_REMISION,"GUI_REM_GUIA_REMISION");
            vecCab.add(INT_MIG_6_GUI_REM_ESTABLECIMIENTO,"GUI_REM_ESTABLECIMIENTO");
            vecCab.add(INT_MIG_6_GUI_REM_PTO_EMISION,"GUI_REM_PTO_EMISION");
            vecCab.add(INT_MIG_6_GUI_REM_SECUENCIAL,"GUI_REM_SECUENCIAL");
            vecCab.add(INT_MIG_6_GUI_REM_DIRECCION_PARTIDA,"GUI_REM_DIRECCION_PARTIDA");
            vecCab.add(INT_MIG_6_GUI_REM_RAZON_SOCIAL_TRANSPORTISTA,"GUI_REM_RAZON_SOCIAL_TRANSPORTISTA");
            vecCab.add(INT_MIG_6_GUI_REM_IDENTIFICACION_TRANSPORTISTA,"GUI_REM_IDENTIFICACION_TRANSPORTISTA");
            vecCab.add(INT_MIG_6_GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA,"GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA");
            vecCab.add(INT_MIG_6_GUI_REM_FECHA_INICIO,"GUI_REM_FECHA_INICIO");
            vecCab.add(INT_MIG_6_GUI_REM_FECHA_FIN,"GUI_REM_FECHA_FIN");
            vecCab.add(INT_MIG_6_GUI_REM_PLACA,"GUI_REM_PLACA");
            vecCab.add(INT_MIG_6_GUI_REM_OBSERVACION,"GUI_REM_OBSERVACION");
            vecCab.add(INT_MIG_6_GUI_REM_DIRECCION_DESTINATARIO,"GUI_REM_DIRECCION_DESTINATARIO");
            vecCab.add(INT_MIG_6_GUI_REM_RAZON_SOCIAL_DESTINATARIO,"GUI_REM_RAZON_SOCIAL_DESTINATARIO");
            vecCab.add(INT_MIG_6_GUI_REM_IDENTIFICACION_DESTINATARIO,"GUI_REM_IDENTIFICACION_DESTINATARIO");
            vecCab.add(INT_MIG_6_GUI_REM_MOTIVO_TRASLADO,"GUI_REM_MOTIVO_TRASLADO");
            vecCab.add(INT_MIG_6_GUI_REM_RUTA,"GUI_REM_RUTA");
            vecCab.add(INT_MIG_6_GUI_REM_CODI_DOCU_SUSTENTO,"GUI_REM_CODI_DOCU_SUSTENTO");
            vecCab.add(INT_MIG_6_GUI_REM_NUME_DOCU_SUSTENTO,"GUI_REM_NUME_DOCU_SUSTENTO");
            vecCab.add(INT_MIG_6_GUI_REM_NUME_AUTO_DOCU_SUSTENTO,"GUI_REM_NUME_AUTO_DOCU_SUSTENTO");
            vecCab.add(INT_MIG_6_GUI_REM_FECHA_EMISION_DOCU_SUSTENTO,"GUI_REM_FECHA_EMISION_DOCU_SUSTENTO");
            vecCab.add(INT_MIG_6_rebajado,"rebajado");
            vecCab.add(INT_MIG_6_recibidos,"recibidos");
            vecCab.add(INT_MIG_6_created_at,"created_at");
            vecCab.add(INT_MIG_6_updated_at,"updated_at");
            vecCab.add(INT_MIG_6_user_id,"user_id");
            objTblModGuias=new ZafTblMod();
            objTblModGuias.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblGuias.setModel(objTblModGuias);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblGuias.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblGuias.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumGuias=new ZafColNumerada(tblGuias,INT_MIG_6_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblGuias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblGuias.getColumnModel();
            tcmAux.getColumn(INT_MIG_6_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_6_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_tipo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_bodega_origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_bodega_destino).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_pedido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_GUIA_REMISION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_ESTABLECIMIENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_PTO_EMISION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_SECUENCIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_DIRECCION_PARTIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_RAZON_SOCIAL_TRANSPORTISTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_IDENTIFICACION_TRANSPORTISTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_FECHA_INICIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_FECHA_FIN).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_PLACA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_OBSERVACION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_DIRECCION_DESTINATARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_RAZON_SOCIAL_DESTINATARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_IDENTIFICACION_DESTINATARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_MOTIVO_TRASLADO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_RUTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_CODI_DOCU_SUSTENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_NUME_DOCU_SUSTENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_NUME_AUTO_DOCU_SUSTENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_GUI_REM_FECHA_EMISION_DOCU_SUSTENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_rebajado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_recibidos).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_6_user_id).setPreferredWidth(50);
            
    
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabGuias=new ZafTblFilCab(tblGuias);
            tcmAux.getColumn(INT_MIG_6_LIN).setCellRenderer(objTblFilCabGuias);
            objTblFilCabGuias=null;
            
            
            
            objTblModGuias.setModoOperacion(objTblModGuias.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatPedidos()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_3_LIN,"");
            vecCab.add(INT_MIG_3_id,"id");
            vecCab.add(INT_MIG_3_pedido,"pedido");
            vecCab.add(INT_MIG_3_factura,"factura");
            vecCab.add(INT_MIG_3_fecha_factura,"fecha_factura");
            vecCab.add(INT_MIG_3_fecha_entrega,"fecha_entrega");
            vecCab.add(INT_MIG_3_estado,"estado");
            vecCab.add(INT_MIG_3_factura_posterior_despacho,"factura_posterior_despacho");
            vecCab.add(INT_MIG_3_factura_a_demanda,"factura_a_demanda");
            vecCab.add(INT_MIG_3_factura_siguiente_mes,"factura_siguiente_mes");
            vecCab.add(INT_MIG_3_despachado,"despachado");
            vecCab.add(INT_MIG_3_despacho_en_proceso,"despacho_en_proceso");
            vecCab.add(INT_MIG_3_transferencia_en_proceso,"transferencia_en_proceso");
            vecCab.add(INT_MIG_3_user_id,"user_id"); 
            vecCab.add(INT_MIG_3_sucursal_id,"sucursal_id");
            vecCab.add(INT_MIG_3_sucursal_facturacion_id,"sucursal_facturacion_id");
            vecCab.add(INT_MIG_3_cliente_id,"cliente_id");
            vecCab.add(INT_MIG_3_cotizacion_id,"cotizacion_id");
            vecCab.add(INT_MIG_3_BOD_BODEGA,"BOD_BODEGA");
            vecCab.add(INT_MIG_3_PTO_VTA_ID,"PTO_VTA_ID");
            vecCab.add(INT_MIG_3_PTO_VTA_DESCRIPCION,"PTO_VTA_DESCRIPCION");
            vecCab.add(INT_MIG_3_USERID,"USERID");
            vecCab.add(INT_MIG_3_DESC_FORMA_PAGO_CODIGO,"DESC_FORMA_PAGO_CODIGO");
            vecCab.add(INT_MIG_3_CLI_RUC,"CLI_RUC");
            vecCab.add(INT_MIG_3_CLI_RAZON_SOCIAL,"CLI_RAZON_SOCIAL");
            vecCab.add(INT_MIG_3_CLI_CORREO_ELECT_SRI,"CLI_CORREO_ELECT_SRI");
            vecCab.add(INT_MIG_3_COND_ID,"COND_ID");
            vecCab.add(INT_MIG_3_COND_ID_ORIGINAL,"COND_ID_ORIGINAL");
            vecCab.add(INT_MIG_3_VEND_ID,"VEND_ID");
            vecCab.add(INT_MIG_3_VEND_NOMBRES,"VEND_NOMBRES");
            vecCab.add(INT_MIG_3_VEND_APELLIDOS,"VEND_APELLIDOS");
            vecCab.add(INT_MIG_3_despacho,"despacho");
            vecCab.add(INT_MIG_3_porcentage_descuentooriginal,"porcentage_descuentooriginal");
            vecCab.add(INT_MIG_3_porcentage_descuento,"porcentage_descuento");
            vecCab.add(INT_MIG_3_subtotal,"subtotal");
            vecCab.add(INT_MIG_3_descuento,"descuento");
            vecCab.add(INT_MIG_3_subtotalmenosdescuento,"subtotalmenosdescuento");
            vecCab.add(INT_MIG_3_iva,"iva");
            vecCab.add(INT_MIG_3_total,"total");
            vecCab.add(INT_MIG_3_requiere_aprobacion_comercial,"requiere_aprobacion_comercial");
            vecCab.add(INT_MIG_3_aprobado_comercial,"aprobado_comercial");
            vecCab.add(INT_MIG_3_aprobador_comercial_id,"aprobador_comercial_id");
            vecCab.add(INT_MIG_3_aprobado_comercial_mensaje,"aprobado_comercial_mensaje");
            vecCab.add(INT_MIG_3_requiere_aprobacion_crediticia,"requiere_aprobacion_crediticia");
            vecCab.add(INT_MIG_3_aprobado_crediticio,"aprobado_crediticio");
            vecCab.add(INT_MIG_3_aprobador_crediticio_id,"aprobador_crediticio_id");
            vecCab.add(INT_MIG_3_aprobado_crediticio_mensaje,"aprobado_crediticio_mensaje");
            vecCab.add(INT_MIG_3_motivo_crediticio,"motivo_crediticio");
            vecCab.add(INT_MIG_3_nota_interna,"nota_interna");
            vecCab.add(INT_MIG_3_nota_cliente,"nota_cliente");
            vecCab.add(INT_MIG_3_orden_de_compra,"orden_de_compra");
            vecCab.add(INT_MIG_3_MST_FAC_VTAResult,"MST_FAC_VTAResult");
            vecCab.add(INT_MIG_3_FAC_VTA_MENSAJE,"FAC_VTA_MENSAJE");
            vecCab.add(INT_MIG_3_pedido_origen,"pedido_origen");
            vecCab.add(INT_MIG_3_created_at,"created_at");
            vecCab.add(INT_MIG_3_updated_at,"updated_at");
            vecCab.add(INT_MIG_3_factura_origen,"factura_origen");
            vecCab.add(INT_MIG_3_USERID_cancelado,"USERID_cancelado");
            vecCab.add(INT_MIG_3_fecha_hora_cancelado,"fecha_hora_cancelado");
            vecCab.add(INT_MIG_3_fecha_hora_factura,"fecha_hora_factura");
            vecCab.add(INT_MIG_3_rechazado,"rechazado");
            vecCab.add(INT_MIG_3_fecha_hora_rechazado,"fecha_hora_rechazado");
            vecCab.add(INT_MIG_3_impuesto,"impuesto");
              
            objTblModPed=new ZafTblMod();
            objTblModPed.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblPedidos.setModel(objTblModPed);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblPedidos.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblPedidos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPedidos=new ZafColNumerada(tblPedidos,INT_MIG_3_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblPedidos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblPedidos.getColumnModel();
            tcmAux.getColumn(INT_MIG_3_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_3_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_pedido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_factura).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_fecha_factura).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_fecha_entrega).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_factura_posterior_despacho).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_factura_a_demanda).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_factura_siguiente_mes).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_despachado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_despacho_en_proceso).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_transferencia_en_proceso).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_user_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_sucursal_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_sucursal_facturacion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_cliente_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_cotizacion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_BOD_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_PTO_VTA_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_PTO_VTA_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_USERID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_DESC_FORMA_PAGO_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_CLI_RUC).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_CLI_RAZON_SOCIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_CLI_CORREO_ELECT_SRI).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_COND_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_COND_ID_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_VEND_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_VEND_NOMBRES).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_VEND_APELLIDOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_despacho).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_porcentage_descuentooriginal).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_porcentage_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_subtotal).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_subtotalmenosdescuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_iva).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_total).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_requiere_aprobacion_comercial).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobado_comercial).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobador_comercial_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobado_comercial_mensaje).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_requiere_aprobacion_crediticia).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobado_crediticio).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobador_crediticio_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_aprobado_crediticio_mensaje).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_motivo_crediticio).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_nota_interna).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_nota_cliente).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_orden_de_compra).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_MST_FAC_VTAResult).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_FAC_VTA_MENSAJE).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_pedido_origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_factura_origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_USERID_cancelado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_fecha_hora_cancelado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_fecha_hora_factura).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_rechazado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_fecha_hora_rechazado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_3_impuesto).setPreferredWidth(40);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabPedidos=new ZafTblFilCab(tblPedidos);
            tcmAux.getColumn(INT_MIG_3_LIN).setCellRenderer(objTblFilCabPedidos);
            objTblFilCabPedidos=null;
            
            
            
            objTblModPed.setModoOperacion(objTblModPed.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatCotizacionesDetalle()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_2_LIN,"");
            vecCab.add(INT_MIG_2_id,"id");
            vecCab.add(INT_MIG_2_cotizacion_id,"cotizacion_id");
            vecCab.add(INT_MIG_2_ART_CODIGO,"ART_CODIGO");
            vecCab.add(INT_MIG_2_ART_DESCRIPCION,"ART_DESCRIPCION");
            vecCab.add(INT_MIG_2_ART_DESCRIPCION_ORIGINAL,"ART_DESCRIPCION_ORIGINAL");
            vecCab.add(INT_MIG_2_ART_CODIGO_TRES_LETRAS,"ART_CODIGO_TRES_LETRAS");
            vecCab.add(INT_MIG_2_ART_DECIMAL_CANTIDAD,"ART_DECIMAL_CANTIDAD");
            vecCab.add(INT_MIG_2_ART_PLAN_UMEDIDA,"ART_PLAN_UMEDIDA");
            vecCab.add(INT_MIG_2_ART_MARCA,"ART_MARCA");
            vecCab.add(INT_MIG_2_ART_MATERIAL,"ART_MATERIAL");
            vecCab.add(INT_MIG_2_ART_PESO,"ART_PESO");
            vecCab.add(INT_MIG_2_ART_TIPO_COMPRA,"ART_TIPO_COMPRA");
            vecCab.add(INT_MIG_2_ART_VALOR,"ART_VALOR");
            vecCab.add(INT_MIG_2_ART_IVA,"ART_IVA");
            vecCab.add(INT_MIG_2_ART_VALOR_ORIGINAL,"ART_VALOR_ORIGINAL");
            vecCab.add(INT_MIG_2_ART_DESCUENTO,"ART_DESCUENTO");
            vecCab.add(INT_MIG_2_ART_CANTIDAD,"ART_CANTIDAD");
            vecCab.add(INT_MIG_2_ART_STOCK,"ART_STOCK");
            vecCab.add(INT_MIG_2_ART_IMP_VTA,"ART_IMP_VTA");
            vecCab.add(INT_MIG_2_ART_COSTO_PROMEDIO,"ART_COSTO_PROMEDIO");
            vecCab.add(INT_MIG_2_descuento,"descuento");
            vecCab.add(INT_MIG_2_descuento_original,"descuento_original");
            vecCab.add(INT_MIG_2_modificador,"modificador");
            vecCab.add(INT_MIG_2_created_at,"created_at");
            vecCab.add(INT_MIG_2_updated_at,"updated_at");
            objTblModDetCot=new ZafTblMod();
            objTblModDetCot.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDetCot.setModel(objTblModDetCot);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDetCot.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDetCot.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumDetCot=new ZafColNumerada(tblDetCot,INT_MIG_1_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDetCot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDetCot.getColumnModel();
            tcmAux.getColumn(INT_MIG_2_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_2_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_cotizacion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_DESCRIPCION_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_CODIGO_TRES_LETRAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_DECIMAL_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_PLAN_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_MARCA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_MATERIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_TIPO_COMPRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_VALOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_VALOR_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_DESCUENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_STOCK).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_IMP_VTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_ART_COSTO_PROMEDIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_descuento_original).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_modificador).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_2_updated_at).setPreferredWidth(50);
            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabDetCot=new ZafTblFilCab(tblDetCot);
            tcmAux.getColumn(INT_MIG_2_LIN).setCellRenderer(objTblFilCabDetCot);
            objTblFilCabDetCot=null;
             
            objTblModDetCot.setModoOperacion(objTblModDetCot.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatGuiaRemisionTransferencias()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_5_LIN,"");
            vecCab.add(INT_MIG_5_id,"id");
            vecCab.add(INT_MIG_5_guia_remision_id,"guia_remision_id");
            vecCab.add(INT_MIG_5_codigo,"codigo");
            vecCab.add(INT_MIG_5_pedido,"pedido");
            vecCab.add(INT_MIG_5_created_at,"created_at");
            vecCab.add(INT_MIG_5_updated_at,"updated_at");
            
            objTblModGuiaTrans=new ZafTblMod();
            objTblModGuiaTrans.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblGuiaTrans.setModel(objTblModGuiaTrans);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblGuiaTrans.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblGuiaTrans.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumGuiaTrans=new ZafColNumerada(tblGuiaTrans,INT_MIG_5_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblGuiaTrans.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblGuiaTrans.getColumnModel();
            tcmAux.getColumn(INT_MIG_5_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_5_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_5_guia_remision_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_5_codigo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_5_pedido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_5_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_5_updated_at).setPreferredWidth(50);
  
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabGuiaTrans=new ZafTblFilCab(tblGuiaTrans);
            tcmAux.getColumn(INT_MIG_5_LIN).setCellRenderer(objTblFilCabGuiaTrans);
            objTblFilCabGuiaTrans=null;
            
            
            objTblModGuiaTrans.setModoOperacion(objTblModGuiaTrans.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatGuiasDetalles()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_7_LIN,"");
            vecCab.add(INT_MIG_7_id,"id");
            vecCab.add(INT_MIG_7_detalle_id,"detalle_id");
            vecCab.add(INT_MIG_7_guia_remision_id,"guia_remision_id");
            vecCab.add(INT_MIG_7_GUI_REM_CODI_INTERNO,"GUI_REM_CODI_INTERNO");
            vecCab.add(INT_MIG_7_GUI_REM_DESCRIPCION,"GUI_REM_DESCRIPCION");
            vecCab.add(INT_MIG_7_GUI_REM_CODI_ADICIONAL,"GUI_REM_CODI_ADICIONAL");
            vecCab.add(INT_MIG_7_GUI_REM_CANTIDAD,"GUI_REM_CANTIDAD");
            vecCab.add(INT_MIG_7_GUI_REM_UNIDAD_MEDIDA,"GUI_REM_UNIDAD_MEDIDA");
            vecCab.add(INT_MIG_7_recibido,"recibido");
            vecCab.add(INT_MIG_7_created_at,"created_at");
            vecCab.add(INT_MIG_7_updated_at,"updated_at");
            objTblModGuiasDetalles=new ZafTblMod();
            objTblModGuiasDetalles.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblGuiasDetalles.setModel(objTblModGuiasDetalles);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblGuiasDetalles.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblGuiasDetalles.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumGuiasDetalles=new ZafColNumerada(tblGuiasDetalles,INT_MIG_7_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblGuiasDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDetCot.getColumnModel();
            tcmAux.getColumn(INT_MIG_7_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_7_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_detalle_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_guia_remision_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_GUI_REM_CODI_INTERNO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_GUI_REM_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_GUI_REM_CODI_ADICIONAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_GUI_REM_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_GUI_REM_UNIDAD_MEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_recibido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_7_updated_at).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabGuiasDetalles=new ZafTblFilCab(tblGuiasDetalles);
            tcmAux.getColumn(INT_MIG_7_LIN).setCellRenderer(objTblFilCabGuiasDetalles);
            objTblFilCabGuiasDetalles=null;
             
            objTblModGuiasDetalles.setModoOperacion(objTblModGuiasDetalles.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
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
    private boolean configurarTblDatCotizaciones()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_1_LIN,"");
            vecCab.add(INT_MIG_1_ID,"id");
            vecCab.add(INT_MIG_1_cotizacion,"cotizacion");
            vecCab.add(INT_MIG_1_user_id,"user_id");
            vecCab.add(INT_MIG_1_sucursal_id,"sucursal_id");
            vecCab.add(INT_MIG_1_sucursal_facturacion_id,"sucursal_facturacion_id");
            vecCab.add(INT_MIG_1_cliente_id,"cliente_id");
            vecCab.add(INT_MIG_1_prospecto_id,"prospecto_id");
            vecCab.add(INT_MIG_1_estado,"estado");
            vecCab.add(INT_MIG_1_factura_posterior_despacho,"factura_posterior_despacho");
            vecCab.add(INT_MIG_1_factura_a_demanda,"factura_a_demanda");
            vecCab.add(INT_MIG_1_factura_siguiente_mes,"factura_siguiente_mes");
            vecCab.add(INT_MIG_1_BOD_BODEGA,"BOD_BODEGA");
            vecCab.add(INT_MIG_1_PTO_VTA_ID,"PTO_VTA_ID");
            vecCab.add(INT_MIG_1_PTO_VTA_DESCRIPCION,"PTO_VTA_DESCRIPCION");
            vecCab.add(INT_MIG_1_USERID,"USERID");
            vecCab.add(INT_MIG_1_DESC_FORMA_PAGO_CODIGO,"DESC_FORMA_PAGO_CODIGO");
            vecCab.add(INT_MIG_1_CLI_RUC,"CLI_RUC");
            vecCab.add(INT_MIG_1_CLI_RAZON_SOCIAL,"CLI_RAZON_SOCIAL");
            vecCab.add(INT_MIG_1_CLI_CORREO_ELECT_SRI,"CLI_CORREO_ELECT_SRI");
            vecCab.add(INT_MIG_1_COND_ID,"COND_ID");
            vecCab.add(INT_MIG_1_COND_ID_ORIGINAL,"COND_ID_ORIGINAL");
            vecCab.add(INT_MIG_1_VEND_ID,"VEND_ID");
            vecCab.add(INT_MIG_1_VEND_NOMBRES,"VEND_NOMBRES");
            vecCab.add(INT_MIG_1_VEND_APELLIDOS,"VEND_APELLIDOS");
            vecCab.add(INT_MIG_1_despacho,"despacho");
            vecCab.add(INT_MIG_1_porcentage_descuentooriginal,"porcentage_descuentooriginal");
            vecCab.add(INT_MIG_1_porcentage_descuento,"porcentage_descuento");
            vecCab.add(INT_MIG_1_subtotal,"subtotal");
            vecCab.add(INT_MIG_1_descuento,"descuento");
            vecCab.add(INT_MIG_1_subtotalmenosdescuento,"subtotalmenosdescuento");
            vecCab.add(INT_MIG_1_iva,"iva");
            vecCab.add(INT_MIG_1_total,"total");
            vecCab.add(INT_MIG_1_requiere_aprobacion_comercial,"requiere_aprobacion_comercial");
            vecCab.add(INT_MIG_1_aprobado_comercial,"aprobado_comercial");
            vecCab.add(INT_MIG_1_aprobador_comercial_id,"aprobador_comercial_id");
            vecCab.add(INT_MIG_1_aprobado_comercial_mensaje,"aprobado_comercial_mensaje");
            vecCab.add(INT_MIG_1_requiere_aprobacion_crediticia,"requiere_aprobacion_crediticia");
            vecCab.add(INT_MIG_1_aprobado_crediticio,"aprobado_crediticio");
            vecCab.add(INT_MIG_1_aprobador_crediticio_id,"aprobador_crediticio_id");
            vecCab.add(INT_MIG_1_nota_interna,"nota_interna");
            vecCab.add(INT_MIG_1_nota_cliente,"nota_cliente");
            vecCab.add(INT_MIG_1_orden_de_compra,"orden_de_compra");
            vecCab.add(INT_MIG_1_impreso,"impreso");
            vecCab.add(INT_MIG_1_fecha_impreso,"fecha_impreso");
            vecCab.add(INT_MIG_1_fecha_entrega,"fecha_entrega");
            vecCab.add(INT_MIG_1_created_at,"created_at");
            vecCab.add(INT_MIG_1_updated_at,"updated_at");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_MIG_1_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_MIG_1_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_1_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_cotizacion).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_user_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_sucursal_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_sucursal_facturacion_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_cliente_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_prospecto_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_factura_posterior_despacho).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_factura_a_demanda).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_factura_siguiente_mes).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_BOD_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_PTO_VTA_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_PTO_VTA_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_USERID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_DESC_FORMA_PAGO_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_CLI_RUC).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_CLI_RAZON_SOCIAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_CLI_CORREO_ELECT_SRI).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_COND_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_COND_ID_ORIGINAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_VEND_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_VEND_NOMBRES).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_VEND_APELLIDOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_despacho).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_porcentage_descuentooriginal).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_porcentage_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_subtotal).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_descuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_subtotalmenosdescuento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_total).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_requiere_aprobacion_comercial).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_aprobado_comercial).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_aprobador_comercial_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_aprobado_comercial_mensaje).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_requiere_aprobacion_crediticia).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_aprobado_crediticio).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_aprobador_crediticio_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_nota_interna).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_nota_cliente).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_orden_de_compra).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_impreso).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_fecha_impreso).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_fecha_entrega).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_1_updated_at).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_MIG_1_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
               
            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    private boolean configurarTblDatOrdenCompra()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_11_LIN,"");
            vecCab.add(INT_MIG_11_id,"id");
            vecCab.add(INT_MIG_11_detalle_id,"detalle_id");
            vecCab.add(INT_MIG_11_detalle_proveedores_id,"detalle_proveedores_id");
            vecCab.add(INT_MIG_11_estado,"estado");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_DOCU_ORIGEN,"ORDEN_COMPRA_DOCU_ORIGEN");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_LINEA,"ORDEN_COMPRA_LINEA");
            vecCab.add(INT_MIG_11_DOC_GENERADO,"DOC_GENERADO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_FECHA_DOCUMENTO,"ORDEN_COMPRA_FECHA_DOCUMENTO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_FECHA_ENTREGA,"ORDEN_COMPRA_FECHA_ENTREGA");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_ID_CLIENTE,"ORDEN_COMPRA_ID_CLIENTE");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_ID_PROVEEDOR,"ORDEN_COMPRA_ID_PROVEEDOR");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_ID_DIRECCION,"ORDEN_COMPRA_ID_DIRECCION");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_NOTA_DOCU,"ORDEN_COMPRA_NOTA_DOCU"); 
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_BODEGA,"ORDEN_COMPRA_BODEGA");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_ARTICULO,"ORDEN_COMPRA_ARTICULO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_DESCRIPCION_ARTICULO,"ORDEN_COMPRA_DESCRIPCION_ARTICULO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_UMEDIDA,"ORDEN_COMPRA_UMEDIDA");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_CANTIDAD,"ORDEN_COMPRA_CANTIDAD");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_COSTO_UNITARIO,"ORDEN_COMPRA_COSTO_UNITARIO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_TOTAL,"ORDEN_COMPRA_TOTAL");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_IMPUESTO,"ORDEN_COMPRA_IMPUESTO");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_ID_USUARIO,"ORDEN_COMPRA_ID_USUARIO");
            vecCab.add(INT_MIG_11_created_at,"created_at");
            vecCab.add(INT_MIG_11_updated_at,"updated_at");
            vecCab.add(INT_MIG_11_ORDEN_COMPRA_MENSAJE,"ORDEN_COMPRA_MENSAJE");
              
            objTblModOrdenCompra=new ZafTblMod();
            objTblModOrdenCompra.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblOrdenCompra.setModel(objTblModOrdenCompra);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblOrdenCompra.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblOrdenCompra.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumOrdenCompra=new ZafColNumerada(tblOrdenCompra,INT_MIG_11_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblOrdenCompra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblOrdenCompra.getColumnModel();
            tcmAux.getColumn(INT_MIG_11_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_11_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_detalle_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_detalle_proveedores_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_DOCU_ORIGEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_LINEA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_DOC_GENERADO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_FECHA_DOCUMENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_FECHA_ENTREGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_ID_CLIENTE).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_ID_PROVEEDOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_ID_DIRECCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_NOTA_DOCU).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_ARTICULO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_DESCRIPCION_ARTICULO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_COSTO_UNITARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_TOTAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_IMPUESTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_ID_USUARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_11_ORDEN_COMPRA_MENSAJE).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabOrdenCompra=new ZafTblFilCab(tblOrdenCompra);
            tcmAux.getColumn(INT_MIG_11_LIN).setCellRenderer(objTblFilCabOrdenCompra);
            objTblFilCabOrdenCompra=null;
            
            
            
            objTblModOrdenCompra.setModoOperacion(objTblModOrdenCompra.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean configurarTblDatOrdComGenerados()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_12_LIN,"");
            vecCab.add(INT_MIG_12_id,"id");
            vecCab.add(INT_MIG_12_orden_id,"orden_id");
            vecCab.add(INT_MIG_12_detalle_id,"detalle_id");
            vecCab.add(INT_MIG_12_detalle_proveedores_id,"detalle_proveedores_id");
            vecCab.add(INT_MIG_12_estado,"estado");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_DOCUMENTO,"ORDEN_COMPRA_DOCUMENTO");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_FECHA_DOCUMENTO,"ORDEN_COMPRA_FECHA_DOCUMENTO");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_FECHA_ENTREGA,"ORDEN_COMPRA_FECHA_ENTREGA");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_ID_PROVEEDOR,"ORDEN_COMPRA_ID_PROVEEDOR");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_DIRECCION,"ORDEN_COMPRA_DIRECCION");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_NOTA_DOCU,"ORDEN_COMPRA_NOTA_DOCU");
            vecCab.add(INT_MIG_12_ORDEN_COMPRA_RECIBO,"ORDEN_COMPRA_RECIBO");
            vecCab.add(INT_MIG_12_USERID,"USERID"); 
            vecCab.add(INT_MIG_12_recibidos,"recibidos");
            vecCab.add(INT_MIG_12_created_at,"created_at");
            vecCab.add(INT_MIG_12_updated_at,"updated_at");
              
            objTblModOrdComGenerados=new ZafTblMod();
            objTblModOrdComGenerados.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblOrdComGenerados.setModel(objTblModOrdComGenerados);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblOrdComGenerados.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblOrdComGenerados.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumOrdComGenerados=new ZafColNumerada(tblOrdComGenerados,INT_MIG_12_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblOrdComGenerados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblOrdComGenerados.getColumnModel();
            tcmAux.getColumn(INT_MIG_12_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_12_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_orden_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_detalle_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_detalle_proveedores_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_DOCUMENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_FECHA_DOCUMENTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_FECHA_ENTREGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_ID_PROVEEDOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_DIRECCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_NOTA_DOCU).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_ORDEN_COMPRA_RECIBO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_USERID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_recibidos).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_12_updated_at).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabOrdComGenerados=new ZafTblFilCab(tblOrdComGenerados);
            tcmAux.getColumn(INT_MIG_12_LIN).setCellRenderer(objTblFilCabOrdComGenerados);
            objTblFilCabOrdComGenerados=null;
            
            //faltan las tablas
            
            objTblModOrdComGenerados.setModoOperacion(objTblModOrdComGenerados.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
        
        private boolean configurarTblDatOCGenDetalles()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_13_LIN,"");
            vecCab.add(INT_MIG_13_id,"id");
            vecCab.add(INT_MIG_13_orden_generada_id,"orden_generada_id");
            vecCab.add(INT_MIG_13_detalle_proveedores_id,"detalle_proveedores_id");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_BODEGA,"ORDEN_COMPRA_DETALLE_BODEGA");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_ARTICULO,"ORDEN_COMPRA_DETALLE_ARTICULO");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO,"ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_UMEDIDA,"ORDEN_COMPRA_DETALLE_UMEDIDA");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_CANTIDAD,"ORDEN_COMPRA_DETALLE_CANTIDAD");
            vecCab.add(INT_MIG_13_recibido,"recibido");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_COSTO_UNITARIO,"ORDEN_COMPRA_DETALLE_COSTO_UNITARIO");
            vecCab.add(INT_MIG_13_ORDEN_ORDEN_COMPRA_DETALLE_TOTAL,"ORDEN_COMPRA_DETALLE_TOTAL");
            vecCab.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_IMPUESTO,"ORDEN_COMPRA_DETALLE_IMPUESTO");
            vecCab.add(INT_MIG_13_created_at,"created_at");
            vecCab.add(INT_MIG_13_updated_at,"updated_at");
            
            objTblModOCGenDetalles=new ZafTblMod();
            objTblModOCGenDetalles.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblOCGenDetalles.setModel(objTblModOCGenDetalles);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblOCGenDetalles.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblOCGenDetalles.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumOCGenDetalles=new ZafColNumerada(tblOCGenDetalles,INT_MIG_13_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblOCGenDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblOCGenDetalles.getColumnModel();
            tcmAux.getColumn(INT_MIG_13_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_13_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_orden_generada_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_detalle_proveedores_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_ARTICULO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_CANTIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_recibido).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_COSTO_UNITARIO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_ORDEN_COMPRA_DETALLE_TOTAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_ORDEN_COMPRA_DETALLE_IMPUESTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_13_updated_at).setPreferredWidth(50);
            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabOCGenDetalles=new ZafTblFilCab(tblOCGenDetalles);
            tcmAux.getColumn(INT_MIG_13_LIN).setCellRenderer(objTblFilCabOCGenDetalles);
            objTblFilCabOCGenDetalles=null;
             
            objTblModOCGenDetalles.setModoOperacion(objTblModOCGenDetalles.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
        
         private boolean configurarTblDatODespachos()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_14_LIN,"");
            vecCab.add(INT_MIG_14_id,"id");
            vecCab.add(INT_MIG_14_pedido_id,"pedido_id");
            vecCab.add(INT_MIG_14_orden,"orden");
            vecCab.add(INT_MIG_14_estado,"estado");
            vecCab.add(INT_MIG_14_PTO_VTA_ID,"PTO_VTA_ID");
            vecCab.add(INT_MIG_14_BOD_BODEGA,"BOD_BODEGA");
            vecCab.add(INT_MIG_14_autorizacion_fecha,"autorizacion_fecha");
            vecCab.add(INT_MIG_14_autorizacion_user_id,"autorizacion_user_id");
            vecCab.add(INT_MIG_14_autorizacion_USERID,"autorizacion_USERID");
            vecCab.add(INT_MIG_14_autorizacion_comentario,"autorizacion_comentario");
            vecCab.add(INT_MIG_14_created_at,"created_at");
            vecCab.add(INT_MIG_14_updated_at,"updated_at");
            vecCab.add(INT_MIG_14_fecha_hora_liberacion,"fecha_hora_liberacion");
            
            objTblModODespachos=new ZafTblMod();
            objTblModODespachos.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblODespachos.setModel(objTblModODespachos);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblODespachos.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblODespachos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumODespachos=new ZafColNumerada(tblODespachos,INT_MIG_14_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblODespachos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblODespachos.getColumnModel();
            tcmAux.getColumn(INT_MIG_14_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_14_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_pedido_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_orden).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_estado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_PTO_VTA_ID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_BOD_BODEGA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_autorizacion_fecha).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_autorizacion_user_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_autorizacion_USERID).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_autorizacion_comentario).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_14_fecha_hora_liberacion).setPreferredWidth(50);
            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabODespachos=new ZafTblFilCab(tblODespachos);
            tcmAux.getColumn(INT_MIG_14_LIN).setCellRenderer(objTblFilCabODespachos);
            objTblFilCabODespachos=null;
             
            objTblModODespachos.setModoOperacion(objTblModODespachos.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
         private boolean configurarTblDatTrasladosVentas()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_15_LIN,"");
            vecCab.add(INT_MIG_15_id,"id");
            vecCab.add(INT_MIG_15_pedido_id,"pedido_id");
            vecCab.add(INT_MIG_15_detalle_id,"detalle_id");
            vecCab.add(INT_MIG_15_detalle_stocks_id,"detalle_stocks_id");
            vecCab.add(INT_MIG_15_ART_CODIGO,"ART_CODIGO");
            vecCab.add(INT_MIG_15_ART_DESCRIPCION,"ART_DESCRIPCION");
            vecCab.add(INT_MIG_15_ART_CODIGO_TRES_LETRAS,"ART_CODIGO_TRES_LETRAS");
            vecCab.add(INT_MIG_15_ART_PLAN_UMEDIDA,"ART_PLAN_UMEDIDA");
            vecCab.add(INT_MIG_15_ART_PESO,"ART_PESO");
            vecCab.add(INT_MIG_15_cantidad,"cantidad");
            vecCab.add(INT_MIG_15_bodega_origen,"bodega_origen");
            vecCab.add(INT_MIG_15_bodega_destino,"bodega_destino");
            vecCab.add(INT_MIG_15_reservado,"reservado"); 
            vecCab.add(INT_MIG_15_trasladado,"trasladado");
            vecCab.add(INT_MIG_15_completado,"completado");
            vecCab.add(INT_MIG_15_created_at,"created_at");
            vecCab.add(INT_MIG_15_updated_at,"updated_at");
            vecCab.add(INT_MIG_15_picking,"picking");
            
            objTblModTrasladosVentas=new ZafTblMod();
            objTblModTrasladosVentas.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblTrasladosVentas.setModel(objTblModTrasladosVentas);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblTrasladosVentas.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblTrasladosVentas.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTrasladosVentas=new ZafColNumerada(tblTrasladosVentas,INT_MIG_15_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTrasladosVentas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblTrasladosVentas.getColumnModel();
            tcmAux.getColumn(INT_MIG_15_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_15_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_pedido_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_detalle_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_detalle_stocks_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_ART_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_ART_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_ART_CODIGO_TRES_LETRAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_ART_PLAN_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_ART_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_cantidad).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_bodega_origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_bodega_destino).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_reservado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_trasladado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_completado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_15_picking).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTrasladosVentas=new ZafTblFilCab(tblTrasladosVentas);
            tcmAux.getColumn(INT_MIG_15_LIN).setCellRenderer(objTblFilCabTrasladosVentas);
            objTblFilCabTrasladosVentas=null;
            
            //faltan las tablas
            
            objTblModTrasladosVentas.setModoOperacion(objTblModTrasladosVentas.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
         private boolean configurarTblDatTrasladosReposiciones()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_16_LIN,"");
            vecCab.add(INT_MIG_16_id,"id");
            vecCab.add(INT_MIG_16_cabecera_id,"cabecera_id");
            vecCab.add(INT_MIG_16_Documento,"Documento");
            vecCab.add(INT_MIG_16_Codigo_Motivo,"Codigo_Motivo");
            vecCab.add(INT_MIG_16_ART_CODIGO,"ART_CODIGO");
            vecCab.add(INT_MIG_16_ART_DESCRIPCION,"ART_DESCRIPCION");
            vecCab.add(INT_MIG_16_ART_CODIGO_TRES_LETRAS,"ART_CODIGO_TRES_LETRAS");
            vecCab.add(INT_MIG_16_ART_PLAN_UMEDIDA,"ART_PLAN_UMEDIDA");
            vecCab.add(INT_MIG_16_ART_PESO,"ART_PESO");
            vecCab.add(INT_MIG_16_cantidad,"cantidad");
            vecCab.add(INT_MIG_16_bodega_origen,"bodega_origen");
            vecCab.add(INT_MIG_16_bodega_destino,"bodega_destino");
            vecCab.add(INT_MIG_16_compartimiento,"compartimiento");
            vecCab.add(INT_MIG_16_Fecha_Documento,"Fecha_Documento");
            vecCab.add(INT_MIG_16_reservado,"reservado"); 
            vecCab.add(INT_MIG_16_trasladado,"trasladado");
            vecCab.add(INT_MIG_16_completado,"completado");
            vecCab.add(INT_MIG_16_created_at,"created_at");
            vecCab.add(INT_MIG_16_updated_at,"updated_at");
            vecCab.add(INT_MIG_16_picking,"picking");
            
            objTblModTrasladosRep=new ZafTblMod();
            objTblModTrasladosRep.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblTrasladosRep.setModel(objTblModTrasladosRep);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblTrasladosRep.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblTrasladosRep.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTrasladosRep=new ZafColNumerada(tblTrasladosRep,INT_MIG_16_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTrasladosRep.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblTrasladosRep.getColumnModel();
            tcmAux.getColumn(INT_MIG_16_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_16_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_cabecera_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_Documento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_Codigo_Motivo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_ART_CODIGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_ART_DESCRIPCION).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_ART_CODIGO_TRES_LETRAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_ART_PLAN_UMEDIDA).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_ART_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_cantidad).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_bodega_origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_bodega_destino).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_compartimiento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_Fecha_Documento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_reservado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_trasladado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_completado).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_updated_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_16_picking).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTrasladosRep=new ZafTblFilCab(tblTrasladosRep);
            tcmAux.getColumn(INT_MIG_16_LIN).setCellRenderer(objTblFilCabTrasladosRep);
            objTblFilCabTrasladosRep=null;
            
            //faltan las tablas
            
            objTblModTrasladosRep.setModoOperacion(objTblModTrasladosRep.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
         private boolean configurarTblDatTrasladosReposicionesCab()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_MIG_17_LIN,"");
            vecCab.add(INT_MIG_17_id,"id");
            vecCab.add(INT_MIG_17_Documento,"Documento");
            vecCab.add(INT_MIG_17_Bodega_Origen,"bodega_origen");
            vecCab.add(INT_MIG_17_Bodega_Destino,"bodega_destino");
            vecCab.add(INT_MIG_17_Codigo_Motivo,"Codigo_Motivo"); 
            vecCab.add(INT_MIG_17_Motivo,"Motivo");
            vecCab.add(INT_MIG_17_Fecha_Documento,"Fecha_Documento");
            vecCab.add(INT_MIG_17_created_at,"created_at");
            vecCab.add(INT_MIG_17_updated_at,"updated_at");
            
            objTblModTrasladosRepCab=new ZafTblMod();
            objTblModTrasladosRepCab.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblTrasladosRepCab.setModel(objTblModTrasladosRepCab);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblTrasladosRepCab.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblTrasladosRepCab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTrasladosRepCab=new ZafColNumerada(tblTrasladosRepCab,INT_MIG_17_LIN);
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTrasladosRepCab.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblTrasladosRepCab.getColumnModel();
            tcmAux.getColumn(INT_MIG_17_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_MIG_17_id).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Documento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Bodega_Origen).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Bodega_Destino).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Codigo_Motivo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Motivo).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_Fecha_Documento).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_created_at).setPreferredWidth(50);
            tcmAux.getColumn(INT_MIG_17_updated_at).setPreferredWidth(50);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTrasladosRepCab=new ZafTblFilCab(tblTrasladosRepCab);
            tcmAux.getColumn(INT_MIG_17_LIN).setCellRenderer(objTblFilCabTrasladosRepCab);
            objTblFilCabTrasladosRepCab=null;
            
            //faltan las tablas
            
            objTblModTrasladosRepCab.setModoOperacion(objTblModTrasladosRepCab.INT_TBL_NO_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        chkTrasladosReposicion = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFecDes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFecHas = new javax.swing.JTextField();
        chkCotizaciones = new javax.swing.JCheckBox();
        chkCotizacionesDetalles = new javax.swing.JCheckBox();
        chkPedidos = new javax.swing.JCheckBox();
        chkPedidosDetalles = new javax.swing.JCheckBox();
        chkGuiaTrans = new javax.swing.JCheckBox();
        chkGuias = new javax.swing.JCheckBox();
        chkGuiasDetalles = new javax.swing.JCheckBox();
        chkDevoluciones = new javax.swing.JCheckBox();
        chkDevDetalles = new javax.swing.JCheckBox();
        chkProveedores = new javax.swing.JCheckBox();
        chkOrdenCompra = new javax.swing.JCheckBox();
        chkOrdComGenerados = new javax.swing.JCheckBox();
        chkOCGenDetalles = new javax.swing.JCheckBox();
        chkODespachos = new javax.swing.JCheckBox();
        chkTrasladosVentas = new javax.swing.JCheckBox();
        chkTrasladosRep = new javax.swing.JCheckBox();
        chkTrasladosRepCab = new javax.swing.JCheckBox();
        panCotizacion = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panCotDetalles = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetCot = new javax.swing.JTable();
        panPedidos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        panPedDetalle = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPedDetalle = new javax.swing.JTable();
        panGuiaTrans = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGuiaTrans = new javax.swing.JTable();
        panGuias = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblGuias = new javax.swing.JTable();
        panGuiasDetalles = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblGuiasDetalles = new javax.swing.JTable();
        panDevoluciones = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblDevoluciones = new javax.swing.JTable();
        panDevolucionesDetalles = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDevolucionesDetalles = new javax.swing.JTable();
        panProveedores = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        panOrdencompra = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblOrdenCompra = new javax.swing.JTable();
        panOrdComGenerados = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblOrdComGenerados = new javax.swing.JTable();
        panOCGenDetalles = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblOCGenDetalles = new javax.swing.JTable();
        panODespachos = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblODespachos = new javax.swing.JTable();
        panTrasladosVentas = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblTrasladosVentas = new javax.swing.JTable();
        panTrasladosRep = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblTrasladosRep = new javax.swing.JTable();
        panTrasladosRepCab = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblTrasladosRepCab = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butInicia = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
        lblTit.setText("Migracion MySQL");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        chkTrasladosReposicion.setPreferredSize(new java.awt.Dimension(610, 130));
        chkTrasladosReposicion.setLayout(null);

        jLabel1.setText("Codigo Tabla: ");
        chkTrasladosReposicion.add(jLabel1);
        jLabel1.setBounds(10, 10, 210, 14);
        chkTrasladosReposicion.add(jTextField1);
        jTextField1.setBounds(140, 0, 150, 20);

        jLabel2.setText("Desde: ");
        chkTrasladosReposicion.add(jLabel2);
        jLabel2.setBounds(20, 40, 110, 14);

        txtFecDes.setBackground(new java.awt.Color(102, 204, 255));
        txtFecDes.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        chkTrasladosReposicion.add(txtFecDes);
        txtFecDes.setBounds(100, 40, 120, 20);

        jLabel3.setText("Hasta: ");
        chkTrasladosReposicion.add(jLabel3);
        jLabel3.setBounds(260, 40, 70, 14);

        txtFecHas.setBackground(new java.awt.Color(102, 204, 255));
        chkTrasladosReposicion.add(txtFecHas);
        txtFecHas.setBounds(340, 40, 140, 20);

        chkCotizaciones.setSelected(true);
        chkCotizaciones.setText("Cotizaciones");
        chkTrasladosReposicion.add(chkCotizaciones);
        chkCotizaciones.setBounds(10, 70, 200, 23);

        chkCotizacionesDetalles.setSelected(true);
        chkCotizacionesDetalles.setText("Cotizaciones detalles");
        chkTrasladosReposicion.add(chkCotizacionesDetalles);
        chkCotizacionesDetalles.setBounds(10, 100, 200, 23);

        chkPedidos.setSelected(true);
        chkPedidos.setText("Pedidos");
        chkTrasladosReposicion.add(chkPedidos);
        chkPedidos.setBounds(10, 130, 200, 23);

        chkPedidosDetalles.setSelected(true);
        chkPedidosDetalles.setText("Pedidos detalles");
        chkPedidosDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPedidosDetallesActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkPedidosDetalles);
        chkPedidosDetalles.setBounds(10, 160, 200, 23);

        chkGuiaTrans.setSelected(true);
        chkGuiaTrans.setText("Guias transferencias");
        chkTrasladosReposicion.add(chkGuiaTrans);
        chkGuiaTrans.setBounds(10, 190, 190, 23);

        chkGuias.setSelected(true);
        chkGuias.setText("Guias");
        chkTrasladosReposicion.add(chkGuias);
        chkGuias.setBounds(10, 220, 150, 23);

        chkGuiasDetalles.setSelected(true);
        chkGuiasDetalles.setText("Guias Detalles");
        chkTrasladosReposicion.add(chkGuiasDetalles);
        chkGuiasDetalles.setBounds(10, 250, 150, 23);

        chkDevoluciones.setSelected(true);
        chkDevoluciones.setText("Devoluciones");
        chkTrasladosReposicion.add(chkDevoluciones);
        chkDevoluciones.setBounds(210, 80, 170, 23);

        chkDevDetalles.setSelected(true);
        chkDevDetalles.setText("Dev detalles");
        chkTrasladosReposicion.add(chkDevDetalles);
        chkDevDetalles.setBounds(210, 110, 180, 23);

        chkProveedores.setSelected(true);
        chkProveedores.setText("Proveedores");
        chkProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkProveedoresActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkProveedores);
        chkProveedores.setBounds(210, 140, 160, 23);

        chkOrdenCompra.setSelected(true);
        chkOrdenCompra.setText("Orden de compra");
        chkOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOrdenCompraActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkOrdenCompra);
        chkOrdenCompra.setBounds(210, 170, 120, 23);

        chkOrdComGenerados.setSelected(true);
        chkOrdComGenerados.setText("OC Generados");
        chkOrdComGenerados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOrdComGeneradosActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkOrdComGenerados);
        chkOrdComGenerados.setBounds(210, 200, 110, 23);

        chkOCGenDetalles.setSelected(true);
        chkOCGenDetalles.setText("OC Gen Detalles");
        chkOCGenDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOCGenDetallesActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkOCGenDetalles);
        chkOCGenDetalles.setBounds(210, 230, 140, 23);

        chkODespachos.setSelected(true);
        chkODespachos.setText("Orden Despachos");
        chkODespachos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkODespachosActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkODespachos);
        chkODespachos.setBounds(210, 260, 140, 23);

        chkTrasladosVentas.setSelected(true);
        chkTrasladosVentas.setText("Traslados Ventas");
        chkTrasladosVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTrasladosVentasActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkTrasladosVentas);
        chkTrasladosVentas.setBounds(410, 90, 140, 23);

        chkTrasladosRep.setSelected(true);
        chkTrasladosRep.setText("Traslados Reposicion");
        chkTrasladosRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTrasladosRepActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkTrasladosRep);
        chkTrasladosRep.setBounds(410, 120, 140, 23);
        chkTrasladosRep.getAccessibleContext().setAccessibleDescription("");

        chkTrasladosRepCab.setSelected(true);
        chkTrasladosRepCab.setText("Cab Traslados Reposicion");
        chkTrasladosRepCab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTrasladosRepCabActionPerformed(evt);
            }
        });
        chkTrasladosReposicion.add(chkTrasladosRepCab);
        chkTrasladosRepCab.setBounds(410, 150, 170, 23);

        panDat.add(chkTrasladosReposicion, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panDat);

        panCotizacion.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panCotizacion.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cotizaciones", panCotizacion);

        panCotDetalles.setLayout(new java.awt.BorderLayout());

        tblDetCot.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetCot);

        panCotDetalles.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cot Detalle", panCotDetalles);

        panPedidos.setLayout(new java.awt.BorderLayout());

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblPedidos);

        panPedidos.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Pedidos", panPedidos);

        panPedDetalle.setLayout(new java.awt.BorderLayout());

        tblPedDetalle.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblPedDetalle);

        panPedDetalle.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Ped Detalle", panPedDetalle);

        panGuiaTrans.setLayout(new java.awt.BorderLayout());

        tblGuiaTrans.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblGuiaTrans);

        panGuiaTrans.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("GuiaTrans", panGuiaTrans);

        panGuias.setLayout(new java.awt.BorderLayout());

        tblGuias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblGuias);

        panGuias.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Guias", panGuias);

        panGuiasDetalles.setLayout(new java.awt.BorderLayout());

        tblGuiasDetalles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblGuiasDetalles);

        panGuiasDetalles.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Guia Detalles", panGuiasDetalles);

        panDevoluciones.setLayout(new java.awt.BorderLayout());

        tblDevoluciones.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblDevoluciones);

        panDevoluciones.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Devoluciones", panDevoluciones);

        panDevolucionesDetalles.setLayout(new java.awt.BorderLayout());

        tblDevolucionesDetalles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblDevolucionesDetalles);

        panDevolucionesDetalles.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Devoluciones Detalles", panDevolucionesDetalles);

        panProveedores.setLayout(new java.awt.BorderLayout());

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(tblProveedores);

        panProveedores.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Proveedores", panProveedores);

        panOrdencompra.setLayout(new java.awt.BorderLayout());

        tblOrdenCompra.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tblOrdenCompra);

        panOrdencompra.add(jScrollPane11, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("OrdenCompra", panOrdencompra);

        panOrdComGenerados.setLayout(new java.awt.BorderLayout());

        tblOrdComGenerados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tblOrdComGenerados);

        panOrdComGenerados.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("OC Generados", panOrdComGenerados);

        panOCGenDetalles.setLayout(new java.awt.BorderLayout());

        tblOCGenDetalles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(tblOCGenDetalles);

        panOCGenDetalles.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("OC Gen Detalles", panOCGenDetalles);

        panODespachos.setLayout(new java.awt.BorderLayout());

        tblODespachos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(tblODespachos);

        panODespachos.add(jScrollPane13, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("OrdenDespachos", panODespachos);

        panTrasladosVentas.setLayout(new java.awt.BorderLayout());

        tblTrasladosVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tblTrasladosVentas);

        panTrasladosVentas.add(jScrollPane14, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("TrasladosVentas", panTrasladosVentas);

        panTrasladosRep.setLayout(new java.awt.BorderLayout());

        tblTrasladosRep.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane15.setViewportView(tblTrasladosRep);

        panTrasladosRep.add(jScrollPane15, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("TrasladosRep", panTrasladosRep);

        panTrasladosRepCab.setLayout(new java.awt.BorderLayout());

        tblTrasladosRepCab.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane16.setViewportView(tblTrasladosRepCab);

        panTrasladosRepCab.add(jScrollPane16, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("CabTrasladosRep", panTrasladosRepCab);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butInicia.setText("Iniciar");
        butInicia.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butInicia.setPreferredSize(new java.awt.Dimension(92, 25));
        butInicia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butIniciaActionPerformed(evt);
            }
        });
        panBot.add(butInicia);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
     
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    strTit="Mensaje del sistema Zafiro";
    strMsg="¿Está seguro que desea cerrar este programa?";
    if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
    {

        dispose();
    }
      
}//GEN-LAST:event_exitForm

    private void butIniciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butIniciaActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if(txtFecDes.getText().length()>0 && txtFecHas.getText().length()>0){
            if (butInicia.getText().equals("Iniciar")){
                blnCon=true;
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafHer47.ZafThreadGUI();
                    objThrGUI.start();    
                }            
            }
            else
            {
                blnCon=false;
            }
        }
        else{
            mostrarMsgInf("Agregar fechas de corte... "); 
        }
        
    }//GEN-LAST:event_butIniciaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void chkProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkProveedoresActionPerformed

    private void chkOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOrdenCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOrdenCompraActionPerformed

    private void chkOrdComGeneradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOrdComGeneradosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOrdComGeneradosActionPerformed

    private void chkOCGenDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOCGenDetallesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOCGenDetallesActionPerformed

    private void chkODespachosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkODespachosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkODespachosActionPerformed

    private void chkPedidosDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPedidosDetallesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPedidosDetallesActionPerformed

    private void chkTrasladosVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTrasladosVentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTrasladosVentasActionPerformed

    private void chkTrasladosRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTrasladosRepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTrasladosRepActionPerformed

    private void chkTrasladosRepCabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTrasladosRepCabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTrasladosRepCabActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butInicia;
    private javax.swing.JCheckBox chkCotizaciones;
    private javax.swing.JCheckBox chkCotizacionesDetalles;
    private javax.swing.JCheckBox chkDevDetalles;
    private javax.swing.JCheckBox chkDevoluciones;
    private javax.swing.JCheckBox chkGuiaTrans;
    private javax.swing.JCheckBox chkGuias;
    private javax.swing.JCheckBox chkGuiasDetalles;
    private javax.swing.JCheckBox chkOCGenDetalles;
    private javax.swing.JCheckBox chkODespachos;
    private javax.swing.JCheckBox chkOrdComGenerados;
    private javax.swing.JCheckBox chkOrdenCompra;
    private javax.swing.JCheckBox chkPedidos;
    private javax.swing.JCheckBox chkPedidosDetalles;
    private javax.swing.JCheckBox chkProveedores;
    private javax.swing.JCheckBox chkTrasladosRep;
    private javax.swing.JCheckBox chkTrasladosRepCab;
    private javax.swing.JPanel chkTrasladosReposicion;
    private javax.swing.JCheckBox chkTrasladosVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCotDetalles;
    private javax.swing.JPanel panCotizacion;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDevoluciones;
    private javax.swing.JPanel panDevolucionesDetalles;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGuiaTrans;
    private javax.swing.JPanel panGuias;
    private javax.swing.JPanel panGuiasDetalles;
    private javax.swing.JPanel panOCGenDetalles;
    private javax.swing.JPanel panODespachos;
    private javax.swing.JPanel panOrdComGenerados;
    private javax.swing.JPanel panOrdencompra;
    private javax.swing.JPanel panPedDetalle;
    private javax.swing.JPanel panPedidos;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panProveedores;
    private javax.swing.JPanel panTrasladosRep;
    private javax.swing.JPanel panTrasladosRepCab;
    private javax.swing.JPanel panTrasladosVentas;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDetCot;
    private javax.swing.JTable tblDevoluciones;
    private javax.swing.JTable tblDevolucionesDetalles;
    private javax.swing.JTable tblGuiaTrans;
    private javax.swing.JTable tblGuias;
    private javax.swing.JTable tblGuiasDetalles;
    private javax.swing.JTable tblOCGenDetalles;
    private javax.swing.JTable tblODespachos;
    private javax.swing.JTable tblOrdComGenerados;
    private javax.swing.JTable tblOrdenCompra;
    private javax.swing.JTable tblPedDetalle;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTable tblTrasladosRep;
    private javax.swing.JTable tblTrasladosRepCab;
    private javax.swing.JTable tblTrasladosVentas;
    private javax.swing.JTextField txtFecDes;
    private javax.swing.JTextField txtFecHas;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
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
    
    /**
     * 
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (iniciaMigracion()){
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
                pgrSis.setValue(0);         
                butInicia.setText("Iniciar"); 
                mostrarMsgInf("Listo!!!");
            }
            objThrGUI=null;
        }
    }
    
    
    private boolean iniciaMigracion(){
        boolean blnRes=true;
        try{
            System.out.println("...Iniciar Migracion...");
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Procesando...");
            butInicia.setText("Procesando..."); 
            
            if(chkCotizaciones.isSelected() && blnRes){
                if(borrarCotizaciones()){
                    if(cargarCabeceraCotizaciones()){
                        if(insertarCabCotizacion()){
                            System.out.println("OK cotizaciones ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Cabeceras de cotizacion...");
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargarCabeceras...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al borrar cotizacion...");
                }
                
            }
            
            if(chkCotizacionesDetalles.isSelected() && blnRes){
                if(borrarCotizacionesDetalles()){
                    if(cargarDetalleCotizaciones()){
                        if(insertarDetCotizacion()){
                            System.out.println("ok cotizaciones detalles ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Detalle de cotizacion...");   
                        }

                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar detalles de cotizacion...");    
                    }
                }
                else{
                    blnRes=false;
                }
                
            }
            
            if(chkPedidos.isSelected() && blnRes){
                if(borrarPedidos()){
                    if(cargarCabeceraPedidos()){
                        if(insertarCabPedidos()){
                            System.out.println("ok pedidos");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al cargarCabeceras Pedidos...");
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargarCabeceras Pedidos...");
                    }
                }
                else{
                    blnRes=false;
                        System.out.println("Error al borrar Pedidos...");
                }
                
            }
            
            if(chkPedidosDetalles.isSelected() && blnRes){
                if(borrarPedidosDetalles()){
                    if(cargarPedidosDetalle()){
                        if(insertarPedidosDetalle()){
                            System.out.println("ok pedido detalles ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Detalle de del pedido...");   
                        }

                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Detalle Pedidos...");
                    }
                }
                
            }
            
            
            if(chkGuiaTrans.isSelected() && blnRes){
                if(borrarGuiasTransfer()){
                    if(cargarGuiaTrans()){
                        if(insertarGuiaTrans()){
                            System.out.println("ok GuiaTrans detalles ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Detalle de del pedido...");   
                        }

                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Detalle Pedidos...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al borrar guias transfer...");
                }
                
            }
            
            
            if(chkGuias.isSelected() && blnRes){
                if(borrarGuias()){
                    if(cargarGuias()){
                        if(insertarGuias()){
                            System.out.println("ok Guias detalles ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Detalle de del pedido...");   
                        }

                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Detalle Pedidos...");
                    }
                }
                
            }
            
            if(chkGuiasDetalles.isSelected() && blnRes){
                if(borrarGuiasDetalles()){
                    if(cargarGuiasDetalles()){
                        if(insertarGuiasDetalles()){
                            System.out.println("ok GuiasDetalles detalles ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al GuiasDetalles...");   
                        }

                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar GuiasDetalles...");
                    }
                }
                
            }
            
            if(chkDevoluciones.isSelected()  && blnRes){
                if(borrarDevoluciones()){
                    if(cargarDevoluciones()){
                        if(insertarDevoluciones()){
                            System.out.println("ok Devoluciones.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Devoluciones...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Devoluciones...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al borrarDevoluciones...");
                }
                
            }
            
            if(chkDevDetalles.isSelected()  && blnRes){
                if(borrarDevolucionesDetalles()){
                    if(cargarDevolucionesDetalles()){
                        if(insertarDevolucionesDetalles()){
                            System.out.println("ok DevolucionesDetalles.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar DevolucionesDetalles...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar DevolucionesDetalles...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al borrarDevolucionesDetalles...");
                }
                
            }
            
            if(chkProveedores.isSelected()  && blnRes){
                if(borrarProveedores()){
                    if(cargarProveedores()){
                        if(insertarProveedores()){
                            System.out.println("ok Proveedores.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Proveedores...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Proveedores...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Proveedores...");
                }
                
            }
            
            if(chkOrdenCompra.isSelected()  && blnRes){
                if(borrarOrdenCompra()){
                    if(cargarOrdenCompra()){
                        if(insertarOrdenCompra()){
                            System.out.println("ok Orden de Compra.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Orden de Compras...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Orden de Compras...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Orden de Compras...");
                }
                
            }
            
            if(chkOrdComGenerados.isSelected()  && blnRes){
                if(borrarOrdComGenerados()){
                    if(cargarOrdComGenerados()){
                        if(insertarOrdComGenerados()){
                            System.out.println("ok Orden de Compra Generados.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Orden de Compras Generados...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Orden de Compras Generados...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Orden de Compras Generados...");
                }
                
            }
            
            
            if(chkOCGenDetalles.isSelected()  && blnRes){
                if(borrarOCGenDetalles()){
                    if(cargarOCGenDetalles()){
                        if(insertarOCGenDetalles()){
                            System.out.println("ok Orden de Compra Generado Detalles.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Orden de Compras Generado Detalles...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Orden de Compras Generado Detalles...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Orden de Compras Generado Detalles...");
                }
                
            }
            if(chkODespachos.isSelected()  && blnRes){
                if(borrarODespachos()){
                    if(cargarODespachos()){
                        if(insertarODespachos()){
                            System.out.println("ok Orden de Despachos.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Orden de Despachos...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Orden de Despachos...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Orden de Despachos...");
                }
                
            }
            
            
            
            if(chkTrasladosVentas.isSelected()  && blnRes){
                if(borrarTrasladosVentas()){
                    if(cargarTrasladosVentas()){
                        if(insertarTrasladosVentas()){
                            System.out.println("ok Traslados Ventas.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Traslados Ventas...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Traslados Ventas...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Traslados Ventas...");
                }
                
            }
            
            if(chkTrasladosRep.isSelected()  && blnRes){
                if(borrarTrasladosReposicion()){
                    if(cargarTrasladosReposicion()){
                        if(insertarTrasladosReposicion()){
                            System.out.println("ok Traslados Reposicion.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Traslados Reposicion...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Traslados Reposicion...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Traslados Reposicion...");
                }
                
            }
            
            if(chkTrasladosRepCab.isSelected()  && blnRes){
                if(borrarTrasladosReposicionCab()){
                    if(cargarTrasladosReposicionCab()){
                        if(insertarTrasladosReposicionCab()){
                            System.out.println("ok Traslados Reposicion Cab.... ");
                        }
                        else{
                            blnRes=false;
                            System.out.println("Error al insertar Traslados Reposicion Cab...");   
                        }
                    }
                    else{
                        blnRes=false;
                        System.out.println("Error al cargar Traslados Reposicion Cab...");
                    }
                }
                else{
                    blnRes=false;
                    System.out.println("Error al Traslados Reposicion Cab...");
                }
                
            }
            
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    
    /**
     * migrarPedidos 
     * @return 
     */ 
    private boolean migrarPedidos(){
        boolean blnRes=true;
        try{
            if(getNumeroUltimoDocumento(INT_TBL_CFG_PEDIDOS)){
                
            }
            else{
                blnRes=false;
                System.out.println("Error al obtener el numero del ultimo documento ingresado...");
            }
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * migrarCotizaciones 
     * @return 
     */
    
    private boolean migrarCotizaciones(){
        boolean blnRes=true;
        try{
            if(getNumeroUltimoDocumento(INT_TBL_CFG_COTIZACIONS)){
                
            }
            else{
                blnRes=false;
                System.out.println("Error al obtener el numero del ultimo documento ingresado...");
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarPedidosDetalle(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModPedDet.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_pedido_detalles(id,pedido_id,orden,ART_CODIGO,ART_DESCRIPCION,ART_DESCRIPCION_ORIGINAL,ART_CODIGO_TRES_LETRAS,ART_DECIMAL_CANTIDAD ";
                    strSql+="  ,ART_PLAN_UMEDIDA,ART_MARCA,ART_MATERIAL,ART_PESO,ART_TIPO_COMPRA,ART_VALOR,ART_IVA,ART_VALOR_ORIGINAL,ART_DESCUENTO,ART_CANTIDAD,ART_STOCK,ART_IMP_VTA";
                    strSql+="  ,ART_COSTO_PROMEDIO,descuento,descuento_original,modificador,reservado,trasladado,devuelto,cancelado,created_at,updated_at,traslado)";
                    strSql+="VALUES("+objTblModPedDet.getValueAt(i,INT_MIG_4_id)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_pedido_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_orden),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_DESCRIPCION_ORIGINAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_CODIGO_TRES_LETRAS),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_DECIMAL_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_PLAN_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_MARCA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_MATERIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_PESO),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_TIPO_COMPRA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_VALOR),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_IVA),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_VALOR_ORIGINAL),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_DESCUENTO),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_STOCK),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_IMP_VTA),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_ART_COSTO_PROMEDIO),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_descuento),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_descuento_original),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_modificador),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_reservado),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_trasladado),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_devuelto),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_cancelado),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_created_at),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPedDet.getValueAt(i,INT_MIG_4_traslado),INT_TBL_CFG_NUMEROS_NULL)+" "; 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    
                    intLast = Integer.parseInt(objTblModPedDet.getValueAt(i,INT_MIG_4_id).toString());
                }
                //strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=4; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarDevolucionesDetalles(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModDevDetalles.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_devoluciones_detalles( id,devolucion_id,detalle_id,ART_CODIGO,ART_DESCRIPCION,ART_DESCRIPCION_ORIGINAL,ART_CODIGO_TRES_LETRAS  ";
                    strSql+="  ,ART_DECIMAL_CANTIDAD,ART_PLAN_UMEDIDA,ART_MARCA,ART_MATERIAL,ART_PESO,ART_TIPO_COMPRA,ART_VALOR,ART_IVA,ART_VALOR_ORIGINAL,ART_DESCUENTO,ART_CANTIDAD";
                    strSql+="  ,ART_STOCK,ART_IMP_VTA,ART_COSTO_PROMEDIO,descuento,descuento_original,modificador,seleccionado,devuelto,disponible,recibido,compartimiento,created_at";
                    strSql+=" ,updated_at) ";
                    strSql+=" VALUES("+objTblModDevDetalles.getValueAt(i,INT_MIG_9_id)+",";  
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_devolucion_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_detalle_id),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_DESCRIPCION_ORIGINAL),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_CODIGO_TRES_LETRAS),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_DECIMAL_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_PLAN_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_MARCA),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_MATERIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_PESO),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_TIPO_COMPRA),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_VALOR),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_IVA),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_VALOR_ORIGINAL),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_DESCUENTO),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_STOCK),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_IMP_VTA),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_ART_COSTO_PROMEDIO),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_descuento),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_descuento_original),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_modificador),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_seleccionado),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_devuelto),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_disponible),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_recibido),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_compartimiento),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDevDetalles.getValueAt(i,INT_MIG_9_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";   
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    
                    intLast = Integer.parseInt(objTblModDevDetalles.getValueAt(i,INT_MIG_9_id).toString());
                }
                //strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=9; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
    
    /**
     * 
     * @return 
     */
    private boolean cargarDevolucionesDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(9);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT a2.* ";
                strSql+=" FROM tuvalapi.gp_devoluciones as a1 INNER JOIN tuvalapi.gp_devoluciones_detalles as a2 ON (a1.id=a2.devolucion_id) ";
                strSql+=" WHERE CAST(a1.created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY a2.id ";
                System.out.println("cargarPedidosDetalle: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_9_LIN,"");
                    vecReg.add(INT_MIG_9_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_9_devolucion_id,    rstLoc.getInt("devolucion_id"));  
                    vecReg.add(INT_MIG_9_detalle_id,    rstLoc.getInt("detalle_id"));  
                    vecReg.add(INT_MIG_9_ART_CODIGO,    rstLoc.getString("ART_CODIGO"));  
                    vecReg.add(INT_MIG_9_ART_DESCRIPCION,    rstLoc.getString("ART_DESCRIPCION"));  
                    vecReg.add(INT_MIG_9_ART_DESCRIPCION_ORIGINAL,  rstLoc.getString("ART_DESCRIPCION_ORIGINAL"));
                    vecReg.add(INT_MIG_9_ART_CODIGO_TRES_LETRAS,    rstLoc.getString("ART_CODIGO_TRES_LETRAS"));  
                    vecReg.add(INT_MIG_9_ART_DECIMAL_CANTIDAD,    rstLoc.getInt("ART_DECIMAL_CANTIDAD"));  
                    vecReg.add(INT_MIG_9_ART_PLAN_UMEDIDA,    rstLoc.getString("ART_PLAN_UMEDIDA"));  
                    vecReg.add(INT_MIG_9_ART_MARCA,    rstLoc.getString("ART_MARCA"));  
                    vecReg.add(INT_MIG_9_ART_MATERIAL,    rstLoc.getString("ART_MATERIAL"));  
                    vecReg.add(INT_MIG_9_ART_PESO,    rstLoc.getDouble("ART_PESO"));  
                    vecReg.add(INT_MIG_9_ART_TIPO_COMPRA,   rstLoc.getString("ART_TIPO_COMPRA"));  
                    vecReg.add(INT_MIG_9_ART_VALOR,    rstLoc.getDouble("ART_VALOR"));  
                    vecReg.add(INT_MIG_9_ART_IVA,    rstLoc.getDouble("ART_IVA"));  
                    vecReg.add(INT_MIG_9_ART_VALOR_ORIGINAL,    rstLoc.getDouble("ART_VALOR_ORIGINAL"));  
                    vecReg.add(INT_MIG_9_ART_DESCUENTO,    rstLoc.getDouble("ART_DESCUENTO"));  
                    vecReg.add(INT_MIG_9_ART_CANTIDAD,    rstLoc.getDouble("ART_CANTIDAD"));  
                    vecReg.add(INT_MIG_9_ART_STOCK,    rstLoc.getDouble("ART_STOCK"));  
                    vecReg.add(INT_MIG_9_ART_IMP_VTA,    rstLoc.getDouble("ART_IMP_VTA"));  
                    vecReg.add(INT_MIG_9_ART_COSTO_PROMEDIO,    rstLoc.getDouble("ART_COSTO_PROMEDIO"));  
                    vecReg.add(INT_MIG_9_descuento,    rstLoc.getDouble("descuento"));  
                    vecReg.add(INT_MIG_9_descuento_original,    rstLoc.getDouble("descuento_original"));  
                    vecReg.add(INT_MIG_9_modificador,    rstLoc.getDouble("modificador"));  
                    vecReg.add(INT_MIG_9_seleccionado,    rstLoc.getDouble("seleccionado"));  
                    vecReg.add(INT_MIG_9_devuelto,    rstLoc.getDouble("devuelto"));  
                    vecReg.add(INT_MIG_9_disponible,    rstLoc.getDouble("disponible"));   
                    vecReg.add(INT_MIG_9_recibido,    rstLoc.getDouble("recibido"));  
                    vecReg.add(INT_MIG_9_compartimiento,    rstLoc.getString("compartimiento"));  
                    vecReg.add(INT_MIG_9_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_9_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModDevDetalles.setData(vecDat);
                tblDevolucionesDetalles.setModel(objTblModDevDetalles);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * @return 
     */
    private boolean cargarPedidosDetalle(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(4);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.id  "; // " + intUltDocIng + " 
                strSql+=" ,a1.pedido_id ";
                strSql+=" ,a1.orden    ";
                strSql+=" ,a1.ART_CODIGO ";
//                strSql+=" ,a1.ART_DESCRIPCION";
//                strSql+=" ,a1.ART_DESCRIPCION_ORIGINAL ";
                strSql+=" ,a1.ART_CODIGO_TRES_LETRAS ";
                strSql+=" ,a1.ART_DECIMAL_CANTIDAD ";
                strSql+=" ,a1.ART_PLAN_UMEDIDA ";
//                strSql+=" ,a1.ART_MARCA  ";
//                strSql+=" ,a1.ART_MATERIAL ";
                strSql+=" ,a1.ART_PESO ";
                strSql+=" ,a1.ART_TIPO_COMPRA  ";
                strSql+=" ,a1.ART_VALOR  ";
                strSql+=" ,a1.ART_IVA  ";
                strSql+=" ,a1.ART_VALOR_ORIGINAL ";
                strSql+=" ,a1.ART_DESCUENTO     ";
                strSql+=" ,a1.ART_CANTIDAD";
                strSql+=" ,a1.ART_STOCK ";
                strSql+=" ,a1.ART_IMP_VTA ";
                strSql+=" ,a1.ART_COSTO_PROMEDIO ";
                strSql+=" ,a1.descuento ";
                strSql+=" ,a1.descuento_original ";
                strSql+=" ,a1.modificador ";
//                strSql+=" ,a1.reservado  ";
//                strSql+=" ,a1.trasladado ";
//                strSql+=" ,a1.devuelto  ";
//                strSql+=" ,a1.cancelado ";
//                strSql+=" ,a1.created_at ";
//                strSql+=" ,a1.updated_at ";
//                strSql+="  ,a1.traslado     ";
                strSql+=" from tuvalapi.gp_pedido_detalles as a1 inner join tuvalapi.gp_pedidos as a2 ON (a1.pedido_id=a2.id) ";
                strSql+=" WHERE CAST(a1.created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY a1.id ";
                System.out.println("cargarPedidosDetalle: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_4_LIN,"");
                    vecReg.add(INT_MIG_4_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_4_pedido_id,    rstLoc.getInt("pedido_id"));  
                    vecReg.add(INT_MIG_4_orden,    rstLoc.getInt("orden"));  
                    vecReg.add(INT_MIG_4_ART_CODIGO,    rstLoc.getString("ART_CODIGO"));  
                    vecReg.add(INT_MIG_4_ART_DESCRIPCION,    "");  
                    vecReg.add(INT_MIG_4_ART_DESCRIPCION_ORIGINAL,    "");
//                    vecReg.add(INT_MIG_4_ART_DESCRIPCION,    rstLoc.getString("ART_DESCRIPCION"));  
//                    vecReg.add(INT_MIG_4_ART_DESCRIPCION_ORIGINAL,    rstLoc.getString("ART_DESCRIPCION_ORIGINAL"));  
                    vecReg.add(INT_MIG_4_ART_CODIGO_TRES_LETRAS,    rstLoc.getString("ART_CODIGO_TRES_LETRAS"));  
                    vecReg.add(INT_MIG_4_ART_DECIMAL_CANTIDAD,    rstLoc.getInt("ART_DECIMAL_CANTIDAD"));  
                    vecReg.add(INT_MIG_4_ART_PLAN_UMEDIDA,    rstLoc.getString("ART_PLAN_UMEDIDA"));  
                    vecReg.add(INT_MIG_4_ART_MARCA,    "");  
                    vecReg.add(INT_MIG_4_ART_MATERIAL,   "");  
//                    vecReg.add(INT_MIG_4_ART_MARCA,    rstLoc.getString("ART_MARCA"));  
//                    vecReg.add(INT_MIG_4_ART_MATERIAL,    rstLoc.getString("ART_MATERIAL"));  
                    vecReg.add(INT_MIG_4_ART_PESO,    rstLoc.getDouble("ART_PESO"));  
                    vecReg.add(INT_MIG_4_ART_TIPO_COMPRA,    rstLoc.getString("ART_TIPO_COMPRA"));  
                    vecReg.add(INT_MIG_4_ART_VALOR,    rstLoc.getDouble("ART_VALOR"));  
                    vecReg.add(INT_MIG_4_ART_IVA,    rstLoc.getDouble("ART_IVA"));  
                    vecReg.add(INT_MIG_4_ART_VALOR_ORIGINAL,    rstLoc.getDouble("ART_VALOR_ORIGINAL"));  
                    vecReg.add(INT_MIG_4_ART_DESCUENTO,    rstLoc.getDouble("ART_DESCUENTO"));  
                    vecReg.add(INT_MIG_4_ART_CANTIDAD,    rstLoc.getDouble("ART_CANTIDAD"));  
                    vecReg.add(INT_MIG_4_ART_STOCK,    rstLoc.getDouble("ART_STOCK"));  
                    vecReg.add(INT_MIG_4_ART_IMP_VTA,    rstLoc.getDouble("ART_IMP_VTA"));  
                    vecReg.add(INT_MIG_4_ART_COSTO_PROMEDIO,    rstLoc.getDouble("ART_COSTO_PROMEDIO"));  
                    vecReg.add(INT_MIG_4_descuento,    rstLoc.getDouble("descuento"));  
                    vecReg.add(INT_MIG_4_descuento_original,    rstLoc.getDouble("descuento_original"));   
                    vecReg.add(INT_MIG_4_modificador,    rstLoc.getDouble("modificador"));  
                    
                    vecReg.add(INT_MIG_4_reservado,    "");  
                    vecReg.add(INT_MIG_4_trasladado,   "");  
                    vecReg.add(INT_MIG_4_devuelto,     "");  
                    vecReg.add(INT_MIG_4_cancelado,    "");  
                    vecReg.add(INT_MIG_4_created_at,   "");  
                    vecReg.add(INT_MIG_4_updated_at,   "");  
                    vecReg.add(INT_MIG_4_traslado,     ""); 
                    
                    
//                    vecReg.add(INT_MIG_4_reservado,    rstLoc.getDouble("reservado"));  
//                    vecReg.add(INT_MIG_4_trasladado,    rstLoc.getDouble("trasladado"));  
//                    vecReg.add(INT_MIG_4_devuelto,    rstLoc.getDouble("devuelto"));  
//                    vecReg.add(INT_MIG_4_cancelado,    rstLoc.getDouble("cancelado"));  
//                    vecReg.add(INT_MIG_4_created_at,    rstLoc.getString("created_at"));  
//                    vecReg.add(INT_MIG_4_updated_at,    rstLoc.getString("updated_at"));  
//                    vecReg.add(INT_MIG_4_traslado,    rstLoc.getInt("traslado"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModPedDet.setData(vecDat);
                tblPedDetalle.setModel(objTblModPedDet);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarProveedores(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(10);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_proveedors ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarProveedores: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_10_LIN,"");
                    vecReg.add(INT_MIG_10_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_10_PROV_RUC,    rstLoc.getString("PROV_RUC"));  
                    vecReg.add(INT_MIG_10_PROV_RAZON_SOCIAL,    rstLoc.getString("PROV_RAZON_SOCIAL"));  
                    vecReg.add(INT_MIG_10_PROV_CLASE,    rstLoc.getString("PROV_CLASE"));  
                    vecReg.add(INT_MIG_10_PROV_ID_DIRECCION,    rstLoc.getString("PROV_ID_DIRECCION"));  
                    vecReg.add(INT_MIG_10_PROV_CONTACTO,  rstLoc.getString("PROV_CONTACTO"));
                    vecReg.add(INT_MIG_10_PROV_DIRECCION_1,    rstLoc.getString("PROV_DIRECCION_1"));  
                    vecReg.add(INT_MIG_10_PROV_DIRECCION_2,    rstLoc.getString("PROV_DIRECCION_2"));  
                    vecReg.add(INT_MIG_10_PROV_DIRECCION_3,    rstLoc.getString("PROV_DIRECCION_3"));  
                    vecReg.add(INT_MIG_10_PROV_CIUDAD,    rstLoc.getString("PROV_CIUDAD"));  
                    vecReg.add(INT_MIG_10_PROV_PROVINCIA,    rstLoc.getString("PROV_PROVINCIA"));  
                    vecReg.add(INT_MIG_10_PROV_PAIS,    rstLoc.getString("PROV_PAIS"));  
                    vecReg.add(INT_MIG_10_PROV_TELEFONO_1,   rstLoc.getString("PROV_TELEFONO_1"));  
                    vecReg.add(INT_MIG_10_PROV_TELEFONO_2,    rstLoc.getString("PROV_TELEFONO_2"));  
                    vecReg.add(INT_MIG_10_PROV_TELEFONO_3,    rstLoc.getString("PROV_TELEFONO_3"));  
                    vecReg.add(INT_MIG_10_PROV_TELEFONO_4,    rstLoc.getString("PROV_TELEFONO_4"));  
                    vecReg.add(INT_MIG_10_PROV_CONDICION_PAGO,    rstLoc.getString("PROV_CONDICION_PAGO"));  
                    vecReg.add(INT_MIG_10_PROV_DSTO_COMERCIAL,    rstLoc.getDouble("PROV_DSTO_COMERCIAL"));  
                    vecReg.add(INT_MIG_10_PROV_FECHA_MODIFICACION,    rstLoc.getString("PROV_FECHA_MODIFICACION"));  
                    vecReg.add(INT_MIG_10_PROV_FECHA_CREACION,    rstLoc.getString("PROV_FECHA_CREACION"));  
                    vecReg.add(INT_MIG_10_PROV_CORREO_ELECT,    rstLoc.getString("PROV_CORREO_ELECT"));  
                    vecReg.add(INT_MIG_10_PROV_IMPUESTO,    rstLoc.getDouble("PROV_IMPUESTO"));  
                    vecReg.add(INT_MIG_10_PROV_ESTADO,    rstLoc.getString("PROV_ESTADO"));  
                    vecReg.add(INT_MIG_10_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_10_updated_at,    rstLoc.getString("updated_at"));   
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModProveedores.setData(vecDat);
                tblProveedores.setModel(objTblModProveedores);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean cargarOrdenCompra(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(11);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_orden_de_compras ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarOrdenCompra: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_11_LIN,"");
                    vecReg.add(INT_MIG_11_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_11_detalle_id,    rstLoc.getInt("detalle_id"));  
                    vecReg.add(INT_MIG_11_detalle_proveedores_id,    rstLoc.getInt("detalle_proveedores_id"));  
                    vecReg.add(INT_MIG_11_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_DOCU_ORIGEN,    rstLoc.getString("ORDEN_COMPRA_DOCU_ORIGEN"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_LINEA,  rstLoc.getString("ORDEN_COMPRA_LINEA"));
                    vecReg.add(INT_MIG_11_DOC_GENERADO,    rstLoc.getString("DOC_GENERADO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_FECHA_DOCUMENTO,    rstLoc.getString("ORDEN_COMPRA_FECHA_DOCUMENTO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_FECHA_ENTREGA,    rstLoc.getString("ORDEN_COMPRA_FECHA_ENTREGA"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_ID_CLIENTE,    rstLoc.getString("ORDEN_COMPRA_ID_CLIENTE"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_ID_PROVEEDOR,    rstLoc.getString("ORDEN_COMPRA_ID_PROVEEDOR"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_ID_DIRECCION,    rstLoc.getString("ORDEN_COMPRA_ID_DIRECCION"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_NOTA_DOCU,   rstLoc.getString("ORDEN_COMPRA_NOTA_DOCU"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_BODEGA,    rstLoc.getString("ORDEN_COMPRA_BODEGA"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_ARTICULO,    rstLoc.getString("ORDEN_COMPRA_ARTICULO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_DESCRIPCION_ARTICULO,    rstLoc.getString("ORDEN_COMPRA_DESCRIPCION_ARTICULO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_UMEDIDA,    rstLoc.getString("ORDEN_COMPRA_UMEDIDA"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_CANTIDAD,    rstLoc.getDouble("ORDEN_COMPRA_CANTIDAD"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_COSTO_UNITARIO,    rstLoc.getDouble("ORDEN_COMPRA_COSTO_UNITARIO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_TOTAL,    rstLoc.getDouble("ORDEN_COMPRA_TOTAL"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_IMPUESTO,    rstLoc.getDouble("ORDEN_COMPRA_IMPUESTO"));  
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_ID_USUARIO,    rstLoc.getString("ORDEN_COMPRA_ID_USUARIO"));  
                    vecReg.add(INT_MIG_11_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_11_updated_at,    rstLoc.getString("updated_at"));   
                    vecReg.add(INT_MIG_11_ORDEN_COMPRA_MENSAJE,    rstLoc.getString("ORDEN_COMPRA_MENSAJE"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModOrdenCompra.setData(vecDat);
                tblOrdenCompra.setModel(objTblModOrdenCompra);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean cargarOrdComGenerados(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(12);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_orden_de_compra_generados ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarOrdenCompraGenerados: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_12_LIN,"");
                    vecReg.add(INT_MIG_12_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_12_orden_id,    rstLoc.getInt("orden_id"));  
                    vecReg.add(INT_MIG_12_detalle_id,    rstLoc.getInt("detalle_id"));  
                    vecReg.add(INT_MIG_12_detalle_proveedores_id,    rstLoc.getInt("detalle_proveedores_id"));  
                    vecReg.add(INT_MIG_12_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_DOCUMENTO,  rstLoc.getString("ORDEN_COMPRA_DOCUMENTO"));
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_FECHA_DOCUMENTO,    rstLoc.getString("ORDEN_COMPRA_FECHA_DOCUMENTO"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_FECHA_ENTREGA,    rstLoc.getString("ORDEN_COMPRA_FECHA_ENTREGA"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_ID_PROVEEDOR,    rstLoc.getString("ORDEN_COMPRA_ID_PROVEEDOR"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_DIRECCION,    rstLoc.getString("ORDEN_COMPRA_DIRECCION"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_NOTA_DOCU,    rstLoc.getString("ORDEN_COMPRA_NOTA_DOCU"));  
                    vecReg.add(INT_MIG_12_ORDEN_COMPRA_RECIBO,    rstLoc.getString("ORDEN_COMPRA_RECIBO"));  
                    vecReg.add(INT_MIG_12_USERID,   rstLoc.getString("USERID"));  
                    vecReg.add(INT_MIG_12_recibidos,    rstLoc.getInt("recibidos"));  
                    vecReg.add(INT_MIG_12_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_12_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModOrdComGenerados.setData(vecDat);
                tblOrdComGenerados.setModel(objTblModOrdComGenerados);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        
        
        private boolean cargarOCGenDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(13);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_orden_de_compra_generado_detalles ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarOCGenDetalles: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_13_LIN,"");
                    vecReg.add(INT_MIG_13_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_13_orden_generada_id,    rstLoc.getInt("orden_generada_id"));  
                    vecReg.add(INT_MIG_13_detalle_proveedores_id,    rstLoc.getInt("detalle_proveedores_id"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_BODEGA,    rstLoc.getString("ORDEN_COMPRA_DETALLE_BODEGA"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_ARTICULO,    rstLoc.getString("ORDEN_COMPRA_DETALLE_ARTICULO"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO,  rstLoc.getString("ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO"));
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_UMEDIDA,    rstLoc.getString("ORDEN_COMPRA_DETALLE_UMEDIDA"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_CANTIDAD,    rstLoc.getDouble("ORDEN_COMPRA_DETALLE_CANTIDAD"));  
                    vecReg.add(INT_MIG_13_recibido,    rstLoc.getDouble("recibido"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_COSTO_UNITARIO,    rstLoc.getDouble("ORDEN_COMPRA_DETALLE_COSTO_UNITARIO"));  
                    vecReg.add(INT_MIG_13_ORDEN_ORDEN_COMPRA_DETALLE_TOTAL,    rstLoc.getDouble("ORDEN_COMPRA_DETALLE_TOTAL"));  
                    vecReg.add(INT_MIG_13_ORDEN_COMPRA_DETALLE_IMPUESTO,    rstLoc.getDouble("ORDEN_COMPRA_DETALLE_IMPUESTO"));  
                    vecReg.add(INT_MIG_13_created_at,   rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_13_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModOCGenDetalles.setData(vecDat);
                tblOCGenDetalles.setModel(objTblModOCGenDetalles);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
        
        
        private boolean cargarODespachos(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(14);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_orden_despachos ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarODespachos: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_14_LIN,"");
                    vecReg.add(INT_MIG_14_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_14_pedido_id,    rstLoc.getInt("pedido_id"));  
                    vecReg.add(INT_MIG_14_orden,    rstLoc.getString("orden"));  
                    vecReg.add(INT_MIG_14_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_14_PTO_VTA_ID,    rstLoc.getString("PTO_VTA_ID"));  
                    vecReg.add(INT_MIG_14_BOD_BODEGA,  rstLoc.getString("BOD_BODEGA"));
                    vecReg.add(INT_MIG_14_autorizacion_fecha,    rstLoc.getString("autorizacion_fecha"));  
                    vecReg.add(INT_MIG_14_autorizacion_user_id,    rstLoc.getInt("autorizacion_user_id"));  
                    vecReg.add(INT_MIG_14_autorizacion_USERID,    rstLoc.getString("autorizacion_USERID"));  
                    vecReg.add(INT_MIG_14_autorizacion_comentario,    rstLoc.getString("autorizacion_comentario"));   
                    vecReg.add(INT_MIG_14_created_at,   rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_14_updated_at,    rstLoc.getString("updated_at"));  
                    vecReg.add(INT_MIG_14_fecha_hora_liberacion,    rstLoc.getString("fecha_hora_liberacion")); 
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModODespachos.setData(vecDat);
                tblODespachos.setModel(objTblModODespachos);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
        
    private boolean cargarTrasladosVentas(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(15);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_traslados_ventas ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarTrasladosVentas: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_15_LIN,"");
                    vecReg.add(INT_MIG_15_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_15_pedido_id,    rstLoc.getInt("pedido_id"));  
                    vecReg.add(INT_MIG_15_detalle_id,    rstLoc.getInt("detalle_id"));  
                    vecReg.add(INT_MIG_15_detalle_stocks_id,    rstLoc.getInt("detalle_stocks_id"));  
                    vecReg.add(INT_MIG_15_ART_CODIGO,    rstLoc.getString("ART_CODIGO"));  
                    vecReg.add(INT_MIG_15_ART_DESCRIPCION,  rstLoc.getString("ART_DESCRIPCION"));
                    vecReg.add(INT_MIG_15_ART_CODIGO_TRES_LETRAS,    rstLoc.getString("ART_CODIGO_TRES_LETRAS"));  
                    vecReg.add(INT_MIG_15_ART_PLAN_UMEDIDA,    rstLoc.getString("ART_PLAN_UMEDIDA"));  
                    vecReg.add(INT_MIG_15_ART_PESO,    rstLoc.getDouble("ART_PESO"));  
                    vecReg.add(INT_MIG_15_cantidad,    rstLoc.getDouble("cantidad"));   
                    vecReg.add(INT_MIG_15_bodega_origen,   rstLoc.getString("bodega_origen"));  
                    vecReg.add(INT_MIG_15_bodega_destino,    rstLoc.getString("bodega_destino"));
                    vecReg.add(INT_MIG_15_reservado,    rstLoc.getDouble("reservado"));
                    vecReg.add(INT_MIG_15_trasladado,    rstLoc.getDouble("trasladado"));
                    vecReg.add(INT_MIG_15_completado,    rstLoc.getInt("completado"));
                    vecReg.add(INT_MIG_15_created_at,    rstLoc.getString("created_at"));
                    vecReg.add(INT_MIG_15_updated_at,    rstLoc.getString("updated_at"));
                    vecReg.add(INT_MIG_15_picking,    rstLoc.getInt("picking"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModTrasladosVentas.setData(vecDat);
                tblTrasladosVentas.setModel(objTblModTrasladosVentas);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean cargarTrasladosReposicion(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(16);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_traslados_reposicions ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarTrasladosReposicion: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_16_LIN,"");
                    vecReg.add(INT_MIG_16_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_16_cabecera_id,    rstLoc.getInt("cabecera_id"));  
                    vecReg.add(INT_MIG_16_Documento,    rstLoc.getInt("Documento"));  
                    vecReg.add(INT_MIG_16_Codigo_Motivo,    rstLoc.getString("Codigo_Motivo"));  
                    vecReg.add(INT_MIG_16_ART_CODIGO,    rstLoc.getString("ART_CODIGO"));  
                    vecReg.add(INT_MIG_16_ART_DESCRIPCION,  rstLoc.getString("ART_DESCRIPCION"));
                    vecReg.add(INT_MIG_16_ART_CODIGO_TRES_LETRAS,    rstLoc.getString("ART_CODIGO_TRES_LETRAS"));  
                    vecReg.add(INT_MIG_16_ART_PLAN_UMEDIDA,    rstLoc.getString("ART_PLAN_UMEDIDA"));  
                    vecReg.add(INT_MIG_16_ART_PESO,    rstLoc.getDouble("ART_PESO"));  
                    vecReg.add(INT_MIG_16_cantidad,    rstLoc.getDouble("cantidad"));   
                    vecReg.add(INT_MIG_16_bodega_origen,   rstLoc.getString("bodega_origen"));  
                    vecReg.add(INT_MIG_16_bodega_destino,    rstLoc.getString("bodega_destino"));
                    vecReg.add(INT_MIG_16_compartimiento,    rstLoc.getString("compartimiento"));
                    vecReg.add(INT_MIG_16_Fecha_Documento,    rstLoc.getString("Fecha_Documento"));
                    vecReg.add(INT_MIG_16_reservado,    rstLoc.getDouble("reservado"));
                    vecReg.add(INT_MIG_16_trasladado,    rstLoc.getDouble("trasladado"));
                    vecReg.add(INT_MIG_16_completado,    rstLoc.getInt("completado"));
                    vecReg.add(INT_MIG_16_created_at,    rstLoc.getString("created_at"));
                    vecReg.add(INT_MIG_16_updated_at,    rstLoc.getString("updated_at"));
                    vecReg.add(INT_MIG_16_picking,    rstLoc.getInt("picking"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModTrasladosRep.setData(vecDat);
                tblTrasladosRep.setModel(objTblModTrasladosRep);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private boolean cargarTrasladosReposicionCab(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(17);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="  SELECT * ";
                strSql+=" FROM tuvalapi.gp_traslados_reposicion_cabeceras ";
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'    ";
                strSql+=" ORDER BY id ";
                System.out.println("cargarTrasladosReposicionCab: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_17_LIN,"");
                    vecReg.add(INT_MIG_17_id,    rstLoc.getInt("id"));  
                    vecReg.add(INT_MIG_17_Documento,    rstLoc.getString("Documento"));    
                    vecReg.add(INT_MIG_17_Bodega_Origen,   rstLoc.getString("bodega_origen"));  
                    vecReg.add(INT_MIG_17_Bodega_Destino,    rstLoc.getString("bodega_destino"));
                    vecReg.add(INT_MIG_17_Codigo_Motivo,    rstLoc.getString("Codigo_Motivo"));
                    vecReg.add(INT_MIG_17_Motivo,    rstLoc.getString("Motivo"));
                    vecReg.add(INT_MIG_17_Fecha_Documento,    rstLoc.getString("Fecha_Documento"));
                    vecReg.add(INT_MIG_17_created_at,    rstLoc.getString("created_at"));
                    vecReg.add(INT_MIG_17_updated_at,    rstLoc.getString("updated_at"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModTrasladosRepCab.setData(vecDat);
                tblTrasladosRepCab.setModel(objTblModTrasladosRepCab);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * 
     * @return 
     */
    private boolean insertarCabPedidos(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModPed.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_pedidos(id,pedido,factura,fecha_factura,fecha_entrega,estado,factura_posterior_despacho,factura_a_demanda,factura_siguiente_mes";
                    strSql+=" ,despachado,despacho_en_proceso,transferencia_en_proceso,user_id,sucursal_id,sucursal_facturacion_id,cliente_id,cotizacion_id,BOD_BODEGA,PTO_VTA_ID";
                    strSql+=" ,PTO_VTA_DESCRIPCION,USERID,DESC_FORMA_PAGO_CODIGO,CLI_RUC,CLI_RAZON_SOCIAL,CLI_CORREO_ELECT_SRI,COND_ID,COND_ID_ORIGINAL,VEND_ID,VEND_NOMBRES";
                    strSql+=" ,VEND_APELLIDOS,despacho,porcentage_descuentooriginal,porcentage_descuento,subtotal,descuento,subtotalmenosdescuento,iva,total,requiere_aprobacion_comercial";
                    strSql+=" ,aprobado_comercial,aprobador_comercial_id,aprobado_comercial_mensaje,requiere_aprobacion_crediticia,aprobado_crediticio,aprobador_crediticio_id";
                    strSql+=" ,aprobado_crediticio_mensaje,motivo_crediticio,nota_interna,nota_cliente,orden_de_compra,MST_FAC_VTAResult,FAC_VTA_MENSAJE,pedido_origen,created_at";
                    strSql+=" ,updated_at,factura_origen,USERID_cancelado,fecha_hora_cancelado,fecha_hora_factura,rechazado,fecha_hora_rechazado,impuesto)";
                    strSql+="VALUES("+objTblModPed.getValueAt(i,INT_MIG_3_id)+",";   
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_pedido),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_factura),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_fecha_factura),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_fecha_entrega),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_estado),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_factura_posterior_despacho),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_factura_a_demanda),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_factura_siguiente_mes),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_despachado),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_despacho_en_proceso),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_transferencia_en_proceso),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_user_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_sucursal_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_sucursal_facturacion_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_cliente_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_cotizacion_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_BOD_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_PTO_VTA_ID),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_PTO_VTA_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_USERID),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_DESC_FORMA_PAGO_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_CLI_RUC),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_CLI_RAZON_SOCIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_CLI_CORREO_ELECT_SRI),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_COND_ID),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_COND_ID_ORIGINAL),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_VEND_ID),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_VEND_NOMBRES),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_VEND_APELLIDOS),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_despacho),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_porcentage_descuentooriginal),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_porcentage_descuento),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_subtotal),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_descuento),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_subtotalmenosdescuento),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_iva),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_total),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_requiere_aprobacion_comercial),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobado_comercial),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobador_comercial_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobado_comercial_mensaje),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_requiere_aprobacion_crediticia),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobado_crediticio),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobador_crediticio_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_aprobado_crediticio_mensaje),INT_TBL_CFG_TEXTO_BLANCK)+",";
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_motivo_crediticio),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_nota_interna),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_nota_cliente),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_orden_de_compra),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_MST_FAC_VTAResult),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_FAC_VTA_MENSAJE),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_pedido_origen),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_factura_origen),INT_TBL_CFG_TEXTO_BLANCK)+",";          
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_USERID_cancelado),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_fecha_hora_cancelado),INT_TBL_CFG_TEXTO_BLANCK)+",";         
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_fecha_hora_factura),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_rechazado),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_fecha_hora_rechazado),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModPed.getValueAt(i,INT_MIG_3_impuesto),INT_TBL_CFG_NUMEROS_NULL)+""; 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModPed.getValueAt(i,INT_MIG_3_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=3; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarGuias(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModGuias.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_guia_remisions(id,GUI_REM_CODIGO,tipo,estado,bodega_origen,bodega_destino,pedido,GUI_REM_BODEGA,GUI_REM_GUIA_REMISION,GUI_REM_ESTABLECIMIENTO ";
                    strSql+=" ,GUI_REM_PTO_EMISION,GUI_REM_SECUENCIAL,GUI_REM_DIRECCION_PARTIDA,GUI_REM_RAZON_SOCIAL_TRANSPORTISTA,GUI_REM_IDENTIFICACION_TRANSPORTISTA ";
                    strSql+=" ,GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA,GUI_REM_FECHA_INICIO,GUI_REM_FECHA_FIN,GUI_REM_PLACA,GUI_REM_OBSERVACION,GUI_REM_DIRECCION_DESTINATARIO ";
                    strSql+=" ,GUI_REM_RAZON_SOCIAL_DESTINATARIO,GUI_REM_IDENTIFICACION_DESTINATARIO,GUI_REM_MOTIVO_TRASLADO,GUI_REM_RUTA,GUI_REM_CODI_DOCU_SUSTENTO ";
                    strSql+=" ,GUI_REM_NUME_DOCU_SUSTENTO,GUI_REM_NUME_AUTO_DOCU_SUSTENTO,GUI_REM_FECHA_EMISION_DOCU_SUSTENTO,rebajado,recibidos,created_at ";
                    strSql+=" ,updated_at,user_id) ";
                    strSql+=" VALUES("+objTblModGuias.getValueAt(i,INT_MIG_6_id)+",";  // id, --1  
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_tipo),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_estado),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_bodega_origen),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_bodega_destino),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_pedido),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_GUIA_REMISION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_ESTABLECIMIENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_PTO_EMISION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_SECUENCIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_DIRECCION_PARTIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_RAZON_SOCIAL_TRANSPORTISTA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_IDENTIFICACION_TRANSPORTISTA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_FECHA_INICIO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_FECHA_FIN),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_PLACA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_OBSERVACION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_DIRECCION_DESTINATARIO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_RAZON_SOCIAL_DESTINATARIO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_IDENTIFICACION_DESTINATARIO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_MOTIVO_TRASLADO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_RUTA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_CODI_DOCU_SUSTENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_NUME_DOCU_SUSTENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_NUME_AUTO_DOCU_SUSTENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_GUI_REM_FECHA_EMISION_DOCU_SUSTENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_rebajado),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_recibidos),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModGuias.getValueAt(i,INT_MIG_6_user_id),INT_TBL_CFG_NUMEROS_NULL)+"";  // cotizacion, --2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModGuias.getValueAt(i,INT_MIG_6_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=6; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @return 
     */
    private boolean insertarGuiasDetalles(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModGuiasDetalles.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_guia_remision_detalles(id,detalle_id,guia_remision_id,GUI_REM_CODI_INTERNO,GUI_REM_DESCRIPCION,GUI_REM_CODI_ADICIONAL ";
                    strSql+=" ,GUI_REM_CANTIDAD,GUI_REM_UNIDAD_MEDIDA,recibido,created_at,updated_at) ";
                    strSql+="VALUES("+objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_id)+",";   
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_detalle_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_guia_remision_id),INT_TBL_CFG_NUMEROS_NULL)+",";   
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_GUI_REM_CODI_INTERNO),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_GUI_REM_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+",";
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_GUI_REM_CODI_ADICIONAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_GUI_REM_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_GUI_REM_UNIDAD_MEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_recibido),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModGuiasDetalles.getValueAt(i,INT_MIG_7_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=7; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarGuiaTrans(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModGuiaTrans.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_guia_remision_transferencias(id, guia_remision_id, codigo, pedido, created_at, updated_at)";
                    strSql+="VALUES("+objTblModGuiaTrans.getValueAt(i,INT_MIG_5_id)+",";   
                    strSql+=""+objUti.codificar(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_guia_remision_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_codigo),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_pedido),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModGuiaTrans.getValueAt(i,INT_MIG_5_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=5; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarDevoluciones(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModDevoluciones.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+=" INSERT INTO WebTuval.dbo.gp_devoluciones(id,devolucion,DOC_GENERADO,fecha_NC,estado,pedido_id,cliente_id,pedido,factura,volver_a_facturar ";
                    strSql+="  ,tipo_devolucion,despachado,cliente_volver_a_facturar_id,user_id,USERID,sucursal_id,sucursal_facturacion_id,motivo,orden_de_compra ";
                    strSql+="  ,requiere_aprobacion,aprobado,aprobador_id,aprobado_mensaje,NC_VTA_MENSAJE,devolucion_bodega,rebajado,created_at,updated_at,procesando) ";
                    strSql+=" VALUES("+objTblModDevoluciones.getValueAt(i,INT_MIG_8_id)+",";   
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_devolucion),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_DOC_GENERADO),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_fecha_NC),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_estado),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_pedido_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_cliente_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_pedido),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_factura),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_volver_a_facturar),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_tipo_devolucion),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_despachado),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_cliente_volver_a_facturar_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_user_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_USERID),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_sucursal_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_sucursal_facturacion_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_motivo),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_orden_de_compra),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_requiere_aprobacion),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_aprobado),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_aprobador_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_aprobado_mensaje),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_NC_VTA_MENSAJE),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_devolucion_bodega),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_rebajado),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModDevoluciones.getValueAt(i,INT_MIG_8_procesando),INT_TBL_CFG_NUMEROS_NULL)+"";    
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModDevoluciones.getValueAt(i,INT_MIG_8_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=8; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * 
     * @return 
     */
    private boolean cargarGuiaTrans(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(5);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" select * FROM gp_guia_remision_transferencias "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY id  ";
                System.out.println("cargarGuiaTrans: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_5_LIN,"");
                    vecReg.add(INT_MIG_5_id, rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_5_guia_remision_id, rstLoc.getInt("guia_remision_id"));  
                    vecReg.add(INT_MIG_5_codigo, rstLoc.getString("codigo"));  
                    vecReg.add(INT_MIG_5_pedido, rstLoc.getString("pedido"));  
                    vecReg.add(INT_MIG_5_created_at, rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_5_updated_at, rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModGuiaTrans.setData(vecDat);
                tblGuiaTrans.setModel(objTblModGuiaTrans);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @return 
     */
    private boolean cargarGuias(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(6);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tuvalapi.gp_guia_remisions "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY id  ";
                System.out.println("gp_guia_remisions ss: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_6_LIN,"");
                    vecReg.add(INT_MIG_6_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_6_GUI_REM_CODIGO,    rstLoc.getString("GUI_REM_CODIGO"));  
                    vecReg.add(INT_MIG_6_tipo,    rstLoc.getString("tipo"));  
                    vecReg.add(INT_MIG_6_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_6_bodega_origen,    rstLoc.getString("bodega_origen"));  
                    vecReg.add(INT_MIG_6_bodega_destino,    rstLoc.getString("bodega_destino"));  
                    vecReg.add(INT_MIG_6_pedido,    rstLoc.getString("pedido"));  
                    vecReg.add(INT_MIG_6_GUI_REM_BODEGA,    rstLoc.getString("GUI_REM_BODEGA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_GUIA_REMISION,    rstLoc.getString("GUI_REM_GUIA_REMISION"));  
                    vecReg.add(INT_MIG_6_GUI_REM_ESTABLECIMIENTO,    rstLoc.getString("GUI_REM_ESTABLECIMIENTO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_PTO_EMISION,    rstLoc.getString("GUI_REM_PTO_EMISION"));  
                    vecReg.add(INT_MIG_6_GUI_REM_SECUENCIAL,    rstLoc.getString("GUI_REM_SECUENCIAL")); 
                    vecReg.add(INT_MIG_6_GUI_REM_DIRECCION_PARTIDA,    rstLoc.getString("GUI_REM_DIRECCION_PARTIDA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_RAZON_SOCIAL_TRANSPORTISTA,    rstLoc.getString("GUI_REM_RAZON_SOCIAL_TRANSPORTISTA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_IDENTIFICACION_TRANSPORTISTA,    rstLoc.getString("GUI_REM_IDENTIFICACION_TRANSPORTISTA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA,    rstLoc.getString("GUI_REM_TIPO_IDENTIFICACION_TRANSPORTISTA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_FECHA_INICIO,    rstLoc.getString("GUI_REM_FECHA_INICIO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_FECHA_FIN,    rstLoc.getString("GUI_REM_FECHA_FIN"));  
                    vecReg.add(INT_MIG_6_GUI_REM_PLACA,    rstLoc.getString("GUI_REM_PLACA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_OBSERVACION,    rstLoc.getString("GUI_REM_OBSERVACION"));  
                    vecReg.add(INT_MIG_6_GUI_REM_DIRECCION_DESTINATARIO,    rstLoc.getString("GUI_REM_DIRECCION_DESTINATARIO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_RAZON_SOCIAL_DESTINATARIO,    rstLoc.getString("GUI_REM_RAZON_SOCIAL_DESTINATARIO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_IDENTIFICACION_DESTINATARIO,    rstLoc.getString("GUI_REM_IDENTIFICACION_DESTINATARIO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_MOTIVO_TRASLADO,    rstLoc.getString("GUI_REM_MOTIVO_TRASLADO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_RUTA,    rstLoc.getString("GUI_REM_RUTA"));  
                    vecReg.add(INT_MIG_6_GUI_REM_CODI_DOCU_SUSTENTO,    rstLoc.getString("GUI_REM_CODI_DOCU_SUSTENTO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_NUME_DOCU_SUSTENTO,    rstLoc.getString("GUI_REM_NUME_DOCU_SUSTENTO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_NUME_AUTO_DOCU_SUSTENTO,    rstLoc.getString("GUI_REM_NUME_AUTO_DOCU_SUSTENTO"));  
                    vecReg.add(INT_MIG_6_GUI_REM_FECHA_EMISION_DOCU_SUSTENTO,    rstLoc.getString("GUI_REM_FECHA_EMISION_DOCU_SUSTENTO"));  
                    vecReg.add(INT_MIG_6_rebajado,    rstLoc.getInt("rebajado"));  
                    vecReg.add(INT_MIG_6_recibidos,    rstLoc.getInt("recibidos"));  
                    vecReg.add(INT_MIG_6_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_6_updated_at,    rstLoc.getString("updated_at"));  
                    vecReg.add(INT_MIG_6_user_id,    rstLoc.getInt("user_id"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModGuias.setData(vecDat);
                tblGuias.setModel(objTblModGuias);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * 
     * @return 
     */
    
    private boolean cargarDevoluciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(8);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tuvalapi.gp_devoluciones "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY id  ";
                System.out.println("cargarDatosPK: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_8_LIN,"");
                    vecReg.add(INT_MIG_8_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_8_devolucion,    rstLoc.getString("devolucion"));  
                    vecReg.add(INT_MIG_8_DOC_GENERADO,    rstLoc.getString("DOC_GENERADO"));  
                    vecReg.add(INT_MIG_8_fecha_NC,    rstLoc.getString("fecha_NC"));  
                    vecReg.add(INT_MIG_8_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_8_pedido_id,    rstLoc.getInt("pedido_id"));  
                    vecReg.add(INT_MIG_8_cliente_id,    rstLoc.getInt("cliente_id"));  
                    vecReg.add(INT_MIG_8_pedido,    rstLoc.getString("pedido"));  
                    vecReg.add(INT_MIG_8_factura,    rstLoc.getString("factura"));  
                    vecReg.add(INT_MIG_8_volver_a_facturar,    rstLoc.getInt("volver_a_facturar"));  
                    vecReg.add(INT_MIG_8_tipo_devolucion,    rstLoc.getString("tipo_devolucion"));  
                    vecReg.add(INT_MIG_8_despachado,    rstLoc.getInt("despachado")); 
                    vecReg.add(INT_MIG_8_cliente_volver_a_facturar_id,    rstLoc.getInt("cliente_volver_a_facturar_id"));  
                    vecReg.add(INT_MIG_8_user_id,    rstLoc.getInt("user_id"));  
                    vecReg.add(INT_MIG_8_USERID,    rstLoc.getString("USERID"));  
                    vecReg.add(INT_MIG_8_sucursal_id,    rstLoc.getInt("sucursal_id"));  
                    vecReg.add(INT_MIG_8_sucursal_facturacion_id,    rstLoc.getInt("sucursal_facturacion_id"));  
                    vecReg.add(INT_MIG_8_motivo,    rstLoc.getString("motivo"));  
                    vecReg.add(INT_MIG_8_orden_de_compra,    rstLoc.getString("orden_de_compra"));  
                    vecReg.add(INT_MIG_8_requiere_aprobacion,    rstLoc.getString("requiere_aprobacion"));  
                    vecReg.add(INT_MIG_8_aprobado,    rstLoc.getString("aprobado"));  
                    vecReg.add(INT_MIG_8_aprobador_id,    rstLoc.getInt("aprobador_id"));  
                    vecReg.add(INT_MIG_8_aprobado_mensaje,    rstLoc.getString("aprobado_mensaje"));  
                    vecReg.add(INT_MIG_8_NC_VTA_MENSAJE,    rstLoc.getString("NC_VTA_MENSAJE"));  
                    vecReg.add(INT_MIG_8_devolucion_bodega,    rstLoc.getInt("devolucion_bodega"));  
                    vecReg.add(INT_MIG_8_rebajado,    rstLoc.getInt("rebajado"));  
                    vecReg.add(INT_MIG_8_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_8_updated_at,    rstLoc.getString("updated_at"));  
                    vecReg.add(INT_MIG_8_procesando,    rstLoc.getInt("procesando"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModDevoluciones.setData(vecDat);
                tblDevoluciones.setModel(objTblModDevoluciones);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * @return 
     */
    
    private boolean cargarCabeceraPedidos(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(3);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tuvalapi.gp_pedidos "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY id  ";
                System.out.println("cargarDatosPK: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_3_LIN,"");
                    vecReg.add(INT_MIG_3_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_3_pedido,    rstLoc.getString("pedido"));  
                    vecReg.add(INT_MIG_3_factura,    rstLoc.getString("factura"));  
                    vecReg.add(INT_MIG_3_fecha_factura,    rstLoc.getString("fecha_factura"));  
                    vecReg.add(INT_MIG_3_fecha_entrega,    rstLoc.getString("fecha_entrega"));  
                    vecReg.add(INT_MIG_3_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_3_factura_posterior_despacho,    rstLoc.getInt("factura_posterior_despacho"));  
                    vecReg.add(INT_MIG_3_factura_a_demanda,    rstLoc.getInt("factura_a_demanda"));  
                    vecReg.add(INT_MIG_3_factura_siguiente_mes,    rstLoc.getInt("factura_siguiente_mes"));  
                    vecReg.add(INT_MIG_3_despachado,    rstLoc.getInt("despachado"));  
                    vecReg.add(INT_MIG_3_despacho_en_proceso,    rstLoc.getInt("despacho_en_proceso"));  
                    vecReg.add(INT_MIG_3_transferencia_en_proceso,    rstLoc.getInt("transferencia_en_proceso")); 
                    vecReg.add(INT_MIG_3_user_id,    rstLoc.getInt("user_id"));  
                    vecReg.add(INT_MIG_3_sucursal_id,    rstLoc.getInt("sucursal_id"));  
                    vecReg.add(INT_MIG_3_sucursal_facturacion_id,    rstLoc.getInt("sucursal_facturacion_id"));  
                    vecReg.add(INT_MIG_3_cliente_id,    rstLoc.getInt("cliente_id"));  
                    vecReg.add(INT_MIG_3_cotizacion_id,    rstLoc.getInt("cotizacion_id"));  
                    vecReg.add(INT_MIG_3_BOD_BODEGA,    rstLoc.getString("BOD_BODEGA"));  
                    vecReg.add(INT_MIG_3_PTO_VTA_ID,    rstLoc.getString("PTO_VTA_ID"));  
                    vecReg.add(INT_MIG_3_PTO_VTA_DESCRIPCION,    rstLoc.getString("PTO_VTA_DESCRIPCION"));  
                    vecReg.add(INT_MIG_3_USERID,    rstLoc.getString("USERID"));  
                    vecReg.add(INT_MIG_3_DESC_FORMA_PAGO_CODIGO,    rstLoc.getString("DESC_FORMA_PAGO_CODIGO"));  
                    vecReg.add(INT_MIG_3_CLI_RUC,    rstLoc.getString("CLI_RUC"));  
                    vecReg.add(INT_MIG_3_CLI_RAZON_SOCIAL,    rstLoc.getString("CLI_RAZON_SOCIAL"));  
                    vecReg.add(INT_MIG_3_CLI_CORREO_ELECT_SRI,    rstLoc.getString("CLI_CORREO_ELECT_SRI"));  
                    vecReg.add(INT_MIG_3_COND_ID,    rstLoc.getString("COND_ID"));  
                    vecReg.add(INT_MIG_3_COND_ID_ORIGINAL,    rstLoc.getString("COND_ID_ORIGINAL"));  
                    vecReg.add(INT_MIG_3_VEND_ID,    rstLoc.getString("VEND_ID"));  
                    vecReg.add(INT_MIG_3_VEND_NOMBRES,    rstLoc.getString("VEND_NOMBRES"));  
                    vecReg.add(INT_MIG_3_VEND_APELLIDOS,    rstLoc.getString("VEND_APELLIDOS"));  
                    vecReg.add(INT_MIG_3_despacho,    rstLoc.getString("despacho"));  
                    vecReg.add(INT_MIG_3_porcentage_descuentooriginal,    rstLoc.getDouble("porcentage_descuentooriginal"));  
                    vecReg.add(INT_MIG_3_porcentage_descuento,    rstLoc.getDouble("porcentage_descuento"));  
                    vecReg.add(INT_MIG_3_subtotal,    rstLoc.getDouble("subtotal"));  
                    vecReg.add(INT_MIG_3_descuento,    rstLoc.getDouble("descuento"));  
                    vecReg.add(INT_MIG_3_subtotalmenosdescuento,    rstLoc.getDouble("subtotalmenosdescuento"));  
                    vecReg.add(INT_MIG_3_iva,    rstLoc.getDouble("iva"));  
                    vecReg.add(INT_MIG_3_total,    rstLoc.getDouble("total"));  
                    vecReg.add(INT_MIG_3_requiere_aprobacion_comercial,    rstLoc.getString("requiere_aprobacion_comercial"));  
                    vecReg.add(INT_MIG_3_aprobado_comercial,    rstLoc.getString("aprobado_comercial"));  
                    vecReg.add(INT_MIG_3_aprobador_comercial_id,    rstLoc.getInt("aprobador_comercial_id"));  
                    vecReg.add(INT_MIG_3_aprobado_comercial_mensaje,    rstLoc.getString("aprobado_comercial_mensaje"));  
                    vecReg.add(INT_MIG_3_requiere_aprobacion_crediticia,    rstLoc.getString("requiere_aprobacion_crediticia"));  
                    vecReg.add(INT_MIG_3_aprobado_crediticio,    rstLoc.getString("aprobado_crediticio"));  
                    vecReg.add(INT_MIG_3_aprobador_crediticio_id,    rstLoc.getInt("aprobador_crediticio_id"));  
                    vecReg.add(INT_MIG_3_aprobado_crediticio_mensaje,    rstLoc.getString("aprobado_crediticio_mensaje"));  
                    vecReg.add(INT_MIG_3_motivo_crediticio,    rstLoc.getString("motivo_crediticio"));  
                    vecReg.add(INT_MIG_3_nota_interna,    rstLoc.getString("nota_interna"));  
                    vecReg.add(INT_MIG_3_nota_cliente,    rstLoc.getString("nota_cliente"));  
                    vecReg.add(INT_MIG_3_orden_de_compra,    rstLoc.getString("orden_de_compra"));  
                    vecReg.add(INT_MIG_3_MST_FAC_VTAResult,    rstLoc.getString("MST_FAC_VTAResult"));  
                    vecReg.add(INT_MIG_3_FAC_VTA_MENSAJE,    rstLoc.getString("FAC_VTA_MENSAJE"));  
                    vecReg.add(INT_MIG_3_pedido_origen,    rstLoc.getString("pedido_origen"));  
                    vecReg.add(INT_MIG_3_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_3_updated_at,    rstLoc.getString("updated_at"));  
                    vecReg.add(INT_MIG_3_factura_origen,    rstLoc.getString("factura_origen"));  
                    vecReg.add(INT_MIG_3_USERID_cancelado,    rstLoc.getString("USERID_cancelado"));  
                    vecReg.add(INT_MIG_3_fecha_hora_cancelado,    rstLoc.getString("fecha_hora_cancelado"));  
                    vecReg.add(INT_MIG_3_fecha_hora_factura,    rstLoc.getString("fecha_hora_factura"));
                    vecReg.add(INT_MIG_3_rechazado,    rstLoc.getInt("rechazado"));
                    vecReg.add(INT_MIG_3_fecha_hora_rechazado,    rstLoc.getString("fecha_hora_rechazado"));
                    vecReg.add(INT_MIG_3_impuesto,    rstLoc.getInt("impuesto"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModPed.setData(vecDat);
                tblPedidos.setModel(objTblModPed);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarDetCotizacion(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModDetCot.getRowCountTrue();i++){
                    intEjecutar++;
                    
                    strSql+="INSERT INTO WebTuval.dbo.gp_cotizacion_detalles(id,cotizacion_id,ART_CODIGO,ART_DESCRIPCION,ART_DESCRIPCION_ORIGINAL,ART_CODIGO_TRES_LETRAS,ART_DECIMAL_CANTIDAD,ART_PLAN_UMEDIDA,ART_MARCA,ART_MATERIAL ";
                    strSql+=" ,ART_PESO,ART_TIPO_COMPRA,ART_VALOR,ART_IVA,ART_VALOR_ORIGINAL ,ART_DESCUENTO,ART_CANTIDAD,ART_STOCK ,ART_IMP_VTA,ART_COSTO_PROMEDIO";
                    strSql+=" ,descuento,descuento_original,modificador,created_at,updated_at) ";
                    strSql+="VALUES("+objTblModDetCot.getValueAt(i,INT_MIG_2_id)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_cotizacion_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_DESCRIPCION_ORIGINAL),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_CODIGO_TRES_LETRAS),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_DECIMAL_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_PLAN_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";   
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_MARCA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_MATERIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_PESO),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_TIPO_COMPRA),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_VALOR),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_IVA),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_VALOR_ORIGINAL),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_DESCUENTO),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_STOCK),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_IMP_VTA),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_ART_COSTO_PROMEDIO),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_descuento),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_descuento_original),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_modificador),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_created_at),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModDetCot.getValueAt(i,INT_MIG_2_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+""; 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModDetCot.getValueAt(i,INT_MIG_2_id).toString());
                }
                //strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=2; ";
                System.out.println("insertarDetCotizacion "+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
    
    /**
     * 
     * @return 
     */
    private boolean insertarCabCotizacion(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblMod.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_cotizacions(id, cotizacion, user_id, sucursal_id, sucursal_facturacion_id, cliente_id, prospecto_id,estado,factura_posterior_despacho  ";
                    strSql+=",factura_a_demanda,factura_siguiente_mes,BOD_BODEGA,PTO_VTA_ID,PTO_VTA_DESCRIPCION,USERID,DESC_FORMA_PAGO_CODIGO,CLI_RUC,CLI_RAZON_SOCIAL,CLI_CORREO_ELECT_SRI";
                    strSql+=",COND_ID,COND_ID_ORIGINAL,VEND_ID,VEND_NOMBRES,VEND_APELLIDOS,despacho,porcentage_descuentooriginal,porcentage_descuento,subtotal,descuento,subtotalmenosdescuento   ";
                    strSql+=",iva,total,requiere_aprobacion_comercial,aprobado_comercial,aprobador_comercial_id,aprobado_comercial_mensaje,requiere_aprobacion_crediticia,aprobado_crediticio ";
                    strSql+=",aprobador_crediticio_id,nota_interna,nota_cliente,orden_de_compra,impreso,fecha_impreso,fecha_entrega,created_at,updated_at) ";
                    strSql+="VALUES("+objTblMod.getValueAt(i,INT_MIG_1_ID)+",";  // id, --1 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_cotizacion),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_user_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_sucursal_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_sucursal_facturacion_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_cliente_id),INT_TBL_CFG_NUMEROS_NULL)+","; // cliente_id, --6 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_prospecto_id),INT_TBL_CFG_NUMEROS_NULL)+","; // prospecto_id,--7 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_estado),INT_TBL_CFG_TEXTO_BLANCK)+",";  // estado,  --8 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_factura_posterior_despacho),INT_TBL_CFG_NUMEROS_NULL)+","; // factura_posterior_despacho  , --9 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_factura_a_demanda),INT_TBL_CFG_NUMEROS_NULL)+","; // factura_a_demanda, --10 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_factura_siguiente_mes),INT_TBL_CFG_NUMEROS_NULL)+",";  // factura_siguiente_mes, --11 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_BOD_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+","; // BOD_BODEGA, --12 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_PTO_VTA_ID),INT_TBL_CFG_TEXTO_BLANCK)+","; // PTO_VTA_ID, --13 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_PTO_VTA_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+","; // PTO_VTA_DESCRIPCION, --14  
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_USERID),INT_TBL_CFG_TEXTO_BLANCK)+","; // USERID,--15 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_DESC_FORMA_PAGO_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+","; // DESC_FORMA_PAGO_CODIGO,  --16 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_CLI_RUC),INT_TBL_CFG_TEXTO_BLANCK)+","; // CLI_RUC, --17 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_CLI_RAZON_SOCIAL),INT_TBL_CFG_TEXTO_BLANCK)+","; // CLI_RAZON_SOCIAL, --18  
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_CLI_CORREO_ELECT_SRI),INT_TBL_CFG_TEXTO_BLANCK)+","; // CLI_CORREO_ELECT_SRI, --18 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_COND_ID),INT_TBL_CFG_TEXTO_BLANCK)+","; // COND_ID, -- 19 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_COND_ID_ORIGINAL),INT_TBL_CFG_TEXTO_BLANCK)+","; // COND_ID_ORIGINAL, --20 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_VEND_ID),INT_TBL_CFG_TEXTO_BLANCK)+","; // VEND_ID, --21 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_VEND_NOMBRES),INT_TBL_CFG_TEXTO_BLANCK)+","; // VEND_NOMBRES, --22 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_VEND_APELLIDOS),INT_TBL_CFG_TEXTO_BLANCK)+","; // VEND_APELLIDOS, --23 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_despacho),INT_TBL_CFG_TEXTO_BLANCK)+","; // despacho, --24 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_porcentage_descuentooriginal),INT_TBL_CFG_NUMEROS_NULL)+","; // porcentage_descuentooriginal, --25 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_porcentage_descuento),INT_TBL_CFG_NUMEROS_NULL)+",";  // porcentage_descuento, --26 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_subtotal),INT_TBL_CFG_NUMEROS_NULL)+","; // subtotal, --27 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_descuento),INT_TBL_CFG_NUMEROS_NULL)+",";  // descuento, --28  
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_subtotalmenosdescuento),INT_TBL_CFG_NUMEROS_NULL)+","; // subtotalmenosdescuento, --29 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_iva),INT_TBL_CFG_NUMEROS_NULL)+","; // iva, --30 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_total),INT_TBL_CFG_NUMEROS_NULL)+","; // total, --31 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_requiere_aprobacion_comercial),INT_TBL_CFG_TEXTO_BLANCK)+","; // requiere_aprobacion_comercial, --32 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_aprobado_comercial),INT_TBL_CFG_TEXTO_BLANCK)+","; // aprobado_comercial, --33 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_aprobador_comercial_id),INT_TBL_CFG_NUMEROS_NULL)+","; // aprobador_comercial_id, --34 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_aprobado_comercial_mensaje),INT_TBL_CFG_TEXTO_BLANCK)+","; // aprobado_comercial_mensaje, --35 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_requiere_aprobacion_crediticia),INT_TBL_CFG_TEXTO_BLANCK)+","; // requiere_aprobacion_crediticia, --36 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_aprobado_crediticio),INT_TBL_CFG_TEXTO_BLANCK)+","; // aprobado_crediticio, --37 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_aprobador_crediticio_id),INT_TBL_CFG_NUMEROS_NULL)+","; // aprobador_crediticio_id,--38
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_nota_interna),INT_TBL_CFG_TEXTO_BLANCK)+","; // nota_interna,--39 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_nota_cliente),INT_TBL_CFG_TEXTO_BLANCK)+","; // nota_cliente,--40 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_orden_de_compra),INT_TBL_CFG_TEXTO_BLANCK)+","; // orden_de_compra,--41 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_impreso),INT_TBL_CFG_TEXTO_BLANCK)+","; // impreso,--42 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_fecha_impreso),INT_TBL_CFG_TEXTO_BLANCK)+","; // fecha_impreso, --43 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_fecha_entrega),INT_TBL_CFG_TEXTO_BLANCK)+","; // fecha_entrega, --44 
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";
                    strSql+=""+objUti.codificar(objTblMod.getValueAt(i,INT_MIG_1_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";          
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblMod.getValueAt(i,INT_MIG_1_ID).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=1; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @return 
     */
    private boolean cargarGuiasDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(7);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a2.* "; 
                strSql+=" FROM tuvalapi.gp_guia_remisions as a1  "; 
                strSql+=" INNER JOIN gp_guia_remision_detalles as a2 ON (a1.id=a2.guia_remision_id) ";
                strSql+=" WHERE CAST(a1.created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY a2.ID ";
                System.out.println("cargarGuiasDetalles: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_7_LIN,"");
                    vecReg.add(INT_MIG_7_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_7_detalle_id,    rstLoc.getInt("detalle_id"));  
                    vecReg.add(INT_MIG_7_guia_remision_id,    rstLoc.getInt("guia_remision_id"));  
                    vecReg.add(INT_MIG_7_GUI_REM_CODI_INTERNO,    rstLoc.getString("GUI_REM_CODI_INTERNO"));  
                    vecReg.add(INT_MIG_7_GUI_REM_DESCRIPCION,    rstLoc.getString("GUI_REM_DESCRIPCION"));  
                    vecReg.add(INT_MIG_7_GUI_REM_CODI_ADICIONAL,    rstLoc.getString("GUI_REM_CODI_ADICIONAL"));  
                    vecReg.add(INT_MIG_7_GUI_REM_CANTIDAD,    rstLoc.getDouble("GUI_REM_CANTIDAD"));  
                    vecReg.add(INT_MIG_7_GUI_REM_UNIDAD_MEDIDA,    rstLoc.getString("GUI_REM_UNIDAD_MEDIDA"));  
                    vecReg.add(INT_MIG_7_recibido,    rstLoc.getDouble("recibido"));  
                    vecReg.add(INT_MIG_7_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_7_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModGuiasDetalles.setData(vecDat);
                tblGuiasDetalles.setModel(objTblModGuiasDetalles);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @return 
     */
    private boolean cargarDetalleCotizaciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(2);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a2.* "; 
                strSql+=" FROM tuvalapi.gp_cotizacions as a1  "; 
                strSql+=" INNER JOIN gp_cotizacion_detalles as a2 ON (a1.id=a2.cotizacion_id) ";
                strSql+=" WHERE CAST(a1.created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY a2.ID ";
                System.out.println("cargarDetalleCotizaciones: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_2_LIN,"");
                    vecReg.add(INT_MIG_2_id,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_2_cotizacion_id,    rstLoc.getInt("cotizacion_id"));  
                    vecReg.add(INT_MIG_2_ART_CODIGO,    rstLoc.getString("ART_CODIGO"));  
                    vecReg.add(INT_MIG_2_ART_DESCRIPCION,    rstLoc.getString("ART_DESCRIPCION"));  
                    vecReg.add(INT_MIG_2_ART_DESCRIPCION_ORIGINAL,    rstLoc.getString("ART_DESCRIPCION_ORIGINAL"));  
                    vecReg.add(INT_MIG_2_ART_CODIGO_TRES_LETRAS,    rstLoc.getString("ART_CODIGO_TRES_LETRAS"));  
                    vecReg.add(INT_MIG_2_ART_DECIMAL_CANTIDAD,    rstLoc.getInt("ART_DECIMAL_CANTIDAD"));  
                    vecReg.add(INT_MIG_2_ART_PLAN_UMEDIDA,    rstLoc.getString("ART_PLAN_UMEDIDA"));  
                    vecReg.add(INT_MIG_2_ART_MARCA,    rstLoc.getString("ART_MARCA"));  
                    vecReg.add(INT_MIG_2_ART_MATERIAL,    rstLoc.getString("ART_MATERIAL"));  
                    vecReg.add(INT_MIG_2_ART_PESO,    rstLoc.getDouble("ART_PESO"));  
                    vecReg.add(INT_MIG_2_ART_TIPO_COMPRA,    rstLoc.getString("ART_TIPO_COMPRA"));  
                    vecReg.add(INT_MIG_2_ART_VALOR,    rstLoc.getDouble("ART_VALOR"));  
                    vecReg.add(INT_MIG_2_ART_IVA,    rstLoc.getDouble("ART_IVA"));  
                    vecReg.add(INT_MIG_2_ART_VALOR_ORIGINAL,    rstLoc.getDouble("ART_VALOR_ORIGINAL"));  
                    vecReg.add(INT_MIG_2_ART_DESCUENTO,    rstLoc.getDouble("ART_DESCUENTO"));  
                    vecReg.add(INT_MIG_2_ART_CANTIDAD,    rstLoc.getDouble("ART_CANTIDAD"));  
                    vecReg.add(INT_MIG_2_ART_STOCK,    rstLoc.getDouble("ART_STOCK"));  
                    vecReg.add(INT_MIG_2_ART_IMP_VTA,    rstLoc.getDouble("ART_IMP_VTA"));  
                    vecReg.add(INT_MIG_2_ART_COSTO_PROMEDIO,    rstLoc.getDouble("ART_COSTO_PROMEDIO"));  
                    vecReg.add(INT_MIG_2_descuento,    rstLoc.getDouble("descuento"));  
                    vecReg.add(INT_MIG_2_descuento_original,    rstLoc.getDouble("descuento_original"));  
                    vecReg.add(INT_MIG_2_modificador,    rstLoc.getString("modificador"));  
                    vecReg.add(INT_MIG_2_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_2_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModDetCot.setData(vecDat);
                tblDetCot.setModel(objTblModDetCot);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    
    
    
    /**
     * 
     * @return 
     */
    
    private boolean cargarCabeceraCotizaciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            tabFrm.setSelectedIndex(1);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            // Establecer la conexión
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tuvalapi.gp_cotizacions  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"'   ";
                strSql+=" ORDER BY id "; 
                System.out.println("cargarDatosPK: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_MIG_1_LIN,"");
                    vecReg.add(INT_MIG_1_ID,    rstLoc.getInt("ID"));  
                    vecReg.add(INT_MIG_1_cotizacion,    rstLoc.getString("cotizacion"));  
                    vecReg.add(INT_MIG_1_user_id,    rstLoc.getInt("user_id"));  
                    vecReg.add(INT_MIG_1_sucursal_id,    rstLoc.getInt("sucursal_id"));  
                    vecReg.add(INT_MIG_1_sucursal_facturacion_id,    rstLoc.getString("sucursal_facturacion_id"));  
                    vecReg.add(INT_MIG_1_cliente_id,    rstLoc.getInt("cliente_id"));  
                    vecReg.add(INT_MIG_1_prospecto_id,    rstLoc.getInt("prospecto_id"));  
                    vecReg.add(INT_MIG_1_estado,    rstLoc.getString("estado"));  
                    vecReg.add(INT_MIG_1_factura_posterior_despacho,    rstLoc.getString("factura_posterior_despacho"));  
                    vecReg.add(INT_MIG_1_factura_a_demanda,    rstLoc.getString("factura_a_demanda"));  
                    vecReg.add(INT_MIG_1_factura_siguiente_mes,    rstLoc.getString("factura_siguiente_mes"));  
                    vecReg.add(INT_MIG_1_BOD_BODEGA,    rstLoc.getString("BOD_BODEGA"));  
                    vecReg.add(INT_MIG_1_PTO_VTA_ID,    rstLoc.getString("PTO_VTA_ID"));  
                    vecReg.add(INT_MIG_1_PTO_VTA_DESCRIPCION,    rstLoc.getString("PTO_VTA_DESCRIPCION"));  
                    vecReg.add(INT_MIG_1_USERID,    rstLoc.getString("USERID"));  
                    vecReg.add(INT_MIG_1_DESC_FORMA_PAGO_CODIGO,    rstLoc.getString("DESC_FORMA_PAGO_CODIGO"));  
                    vecReg.add(INT_MIG_1_CLI_RUC,    rstLoc.getString("CLI_RUC"));  
                    vecReg.add(INT_MIG_1_CLI_RAZON_SOCIAL,    rstLoc.getString("CLI_RAZON_SOCIAL"));  
                    vecReg.add(INT_MIG_1_CLI_CORREO_ELECT_SRI,    rstLoc.getString("CLI_CORREO_ELECT_SRI"));  
                    vecReg.add(INT_MIG_1_COND_ID,    rstLoc.getString("COND_ID"));  
                    vecReg.add(INT_MIG_1_COND_ID_ORIGINAL,    rstLoc.getString("COND_ID_ORIGINAL"));  
                    vecReg.add(INT_MIG_1_VEND_ID,    rstLoc.getString("VEND_ID"));  
                    vecReg.add(INT_MIG_1_VEND_NOMBRES,    rstLoc.getString("VEND_NOMBRES"));  
                    vecReg.add(INT_MIG_1_VEND_APELLIDOS,    rstLoc.getString("VEND_APELLIDOS"));  
                    vecReg.add(INT_MIG_1_despacho,    rstLoc.getString("despacho"));  
                    vecReg.add(INT_MIG_1_porcentage_descuentooriginal,    rstLoc.getDouble("porcentage_descuentooriginal"));  
                    vecReg.add(INT_MIG_1_porcentage_descuento,    rstLoc.getDouble("porcentage_descuento"));  
                    vecReg.add(INT_MIG_1_subtotal,    rstLoc.getDouble("subtotal"));  
                    vecReg.add(INT_MIG_1_descuento,    rstLoc.getDouble("descuento"));  
                    vecReg.add(INT_MIG_1_subtotalmenosdescuento,    rstLoc.getDouble("subtotalmenosdescuento"));  
                    vecReg.add(INT_MIG_1_iva,    rstLoc.getDouble("iva"));  
                    vecReg.add(INT_MIG_1_total,    rstLoc.getDouble("total"));  
                    vecReg.add(INT_MIG_1_requiere_aprobacion_comercial,    rstLoc.getString("requiere_aprobacion_comercial"));  
                    vecReg.add(INT_MIG_1_aprobado_comercial,    rstLoc.getString("aprobado_comercial"));  
                    vecReg.add(INT_MIG_1_aprobador_comercial_id,    rstLoc.getString("aprobador_comercial_id"));  
                    vecReg.add(INT_MIG_1_aprobado_comercial_mensaje,    rstLoc.getString("aprobado_comercial_mensaje"));  
                    vecReg.add(INT_MIG_1_requiere_aprobacion_crediticia,    rstLoc.getString("requiere_aprobacion_crediticia"));  
                    vecReg.add(INT_MIG_1_aprobado_crediticio,    rstLoc.getString("aprobado_crediticio"));  
                    vecReg.add(INT_MIG_1_aprobador_crediticio_id,    rstLoc.getString("aprobador_crediticio_id"));  
                    vecReg.add(INT_MIG_1_nota_interna,    rstLoc.getString("nota_interna"));  
                    vecReg.add(INT_MIG_1_nota_cliente,    rstLoc.getString("nota_cliente"));  
                    vecReg.add(INT_MIG_1_orden_de_compra,    rstLoc.getString("orden_de_compra"));  
                    vecReg.add(INT_MIG_1_impreso,    rstLoc.getString("impreso"));  
                    vecReg.add(INT_MIG_1_fecha_impreso,    rstLoc.getString("fecha_impreso"));  
                    vecReg.add(INT_MIG_1_fecha_entrega,    rstLoc.getString("fecha_entrega"));  
                    vecReg.add(INT_MIG_1_created_at,    rstLoc.getString("created_at"));  
                    vecReg.add(INT_MIG_1_updated_at,    rstLoc.getString("updated_at"));  
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean insertarProveedores(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModProveedores.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_proveedors(id,PROV_RUC,PROV_RAZON_SOCIAL,PROV_CLASE,PROV_ID_DIRECCION,PROV_CONTACTO,PROV_DIRECCION_1,PROV_DIRECCION_2,PROV_DIRECCION_3,PROV_CIUDAD ";
                    strSql+=" ,PROV_PROVINCIA,PROV_PAIS,PROV_TELEFONO_1,PROV_TELEFONO_2,PROV_TELEFONO_3 ";
                    strSql+=" ,PROV_TELEFONO_4,PROV_CONDICION_PAGO,PROV_DSTO_COMERCIAL,PROV_FECHA_MODIFICACION,PROV_FECHA_CREACION,PROV_CORREO_ELECT ";
                    strSql+=" ,PROV_IMPUESTO,PROV_ESTADO,created_at,updated_at) ";
                    strSql+=" VALUES("+objTblModProveedores.getValueAt(i,INT_MIG_10_id)+",";  // id, --1  
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_RUC),INT_TBL_CFG_TEXTO_BLANCK)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_RAZON_SOCIAL),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_CLASE),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_ID_DIRECCION),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_CONTACTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_DIRECCION_1),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_DIRECCION_2),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_DIRECCION_3),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_CIUDAD),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_PROVINCIA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_PAIS),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_TELEFONO_1),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_TELEFONO_2),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_TELEFONO_3),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_TELEFONO_4),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_CONDICION_PAGO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_DSTO_COMERCIAL),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_FECHA_MODIFICACION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_FECHA_CREACION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_CORREO_ELECT),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_IMPUESTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_PROV_ESTADO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModProveedores.getValueAt(i,INT_MIG_10_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  // cotizacion, --2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModProveedores.getValueAt(i,INT_MIG_10_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=10; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private boolean insertarOrdenCompra(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModOrdenCompra.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_orden_de_compras(id,detalle_id,detalle_proveedores_id,estado,ORDEN_COMPRA_DOCU_ORIGEN,ORDEN_COMPRA_LINEA,DOC_GENERADO,ORDEN_COMPRA_FECHA_DOCUMENTO,ORDEN_COMPRA_FECHA_ENTREGA,ORDEN_COMPRA_ID_CLIENTE,ORDEN_COMPRA_ID_PROVEEDOR ";
                    strSql+=" ,ORDEN_COMPRA_ID_DIRECCION,ORDEN_COMPRA_NOTA_DOCU,ORDEN_COMPRA_BODEGA,ORDEN_COMPRA_ARTICULO,ORDEN_COMPRA_DESCRIPCION_ARTICULO ";
                    strSql+=" ,ORDEN_COMPRA_UMEDIDA,ORDEN_COMPRA_CANTIDAD,ORDEN_COMPRA_COSTO_UNITARIO,ORDEN_COMPRA_TOTAL,ORDEN_COMPRA_IMPUESTO,ORDEN_COMPRA_ID_USUARIO ";
                    strSql+=" ,created_at,updated_at,ORDEN_COMPRA_MENSAJE) ";
                    strSql+=" VALUES("+objTblModOrdenCompra.getValueAt(i,INT_MIG_11_id)+",";  // id, --1  
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_detalle_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_detalle_proveedores_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_estado),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_DOCU_ORIGEN),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_LINEA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_DOC_GENERADO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_FECHA_DOCUMENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_FECHA_ENTREGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_ID_CLIENTE),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_ID_PROVEEDOR),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_ID_DIRECCION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_NOTA_DOCU),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_ARTICULO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_DESCRIPCION_ARTICULO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_COSTO_UNITARIO),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_TOTAL),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_IMPUESTO),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_ID_USUARIO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_ORDEN_COMPRA_MENSAJE),INT_TBL_CFG_TEXTO_BLANCK)+"";  // cotizacion, --2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModOrdenCompra.getValueAt(i,INT_MIG_11_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=11; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean insertarOrdComGenerados(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModOrdComGenerados.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_orden_de_compra_generados(id,orden_id,detalle_id,detalle_proveedores_id,estado,ORDEN_COMPRA_DOCUMENTO,ORDEN_COMPRA_FECHA_DOCUMENTO,ORDEN_COMPRA_FECHA_ENTREGA,ORDEN_COMPRA_ID_PROVEEDOR,ORDEN_COMPRA_DIRECCION ";
                    strSql+=" ,ORDEN_COMPRA_NOTA_DOCU,ORDEN_COMPRA_RECIBO,USERID";
                    strSql+=" ,recibidos,created_at,updated_at) ";
                    strSql+=" VALUES("+objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_id)+",";  // id, --1  
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_orden_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_detalle_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_detalle_proveedores_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_estado),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_DOCUMENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_FECHA_DOCUMENTO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_FECHA_ENTREGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_ID_PROVEEDOR),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_DIRECCION),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_NOTA_DOCU),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_ORDEN_COMPRA_RECIBO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_USERID),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_recibidos),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  // cotizacion, --2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModOrdComGenerados.getValueAt(i,INT_MIG_12_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=12; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean insertarOCGenDetalles(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModOCGenDetalles.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_orden_de_compra_generado_detalles(id,orden_generada_id,detalle_proveedores_id,ORDEN_COMPRA_DETALLE_BODEGA,ORDEN_COMPRA_DETALLE_ARTICULO,ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO,ORDEN_COMPRA_DETALLE_UMEDIDA,ORDEN_COMPRA_DETALLE_CANTIDAD,recibido,ORDEN_COMPRA_DETALLE_COSTO_UNITARIO ";
                    strSql+=" ,ORDEN_COMPRA_DETALLE_TOTAL,ORDEN_COMPRA_DETALLE_IMPUESTO,created_at,updated_at) ";
//                    strSql+=" VALUES("+objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_LIN)+",";  // id, --1  
                    strSql+="VALUES( "+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_orden_generada_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_detalle_proveedores_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_ARTICULO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_DESCRIPCION_ARTICULO),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_CANTIDAD),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_recibido),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_COSTO_UNITARIO),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_ORDEN_COMPRA_DETALLE_TOTAL),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_ORDEN_COMPRA_DETALLE_IMPUESTO),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  // cotizacion--2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=13; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
        
        private boolean insertarODespachos(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModODespachos.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_orden_despachos(id,pedido_id,orden,estado,PTO_VTA_ID,BOD_BODEGA,autorizacion_fecha,autorizacion_user_id,autorizacion_USERID,autorizacion_comentario,created_at,updated_at,fecha_hora_liberacion) ";
//                    strSql+=" VALUES("+objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_LIN)+",";  // id, --1  
                    strSql+="VALUES( "+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // user_id,  -- 3 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_pedido_id),INT_TBL_CFG_NUMEROS_NULL)+","; // sucursal_id, --4  
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_orden),INT_TBL_CFG_TEXTO_BLANCK)+","; // sucursal_facturacion_id, --5 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_estado),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_PTO_VTA_ID),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_BOD_BODEGA),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_autorizacion_fecha),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_autorizacion_user_id),INT_TBL_CFG_NUMEROS_NULL)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_autorizacion_USERID),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_autorizacion_comentario),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion, --2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  // cotizacion--2 
                    strSql+=""+objUti.codificar(objTblModODespachos.getValueAt(i,INT_MIG_14_fecha_hora_liberacion),INT_TBL_CFG_TEXTO_BLANCK)+"";  // cotizacion--2 
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModODespachos.getValueAt(i,INT_MIG_14_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=14; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarTrasladosVentas(){ // falta corregir los formatos
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModTrasladosVentas.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_traslados_ventas(id,pedido_id, detalle_id, detalle_stocks_id, ART_CODIGO, ART_DESCRIPCION, ART_CODIGO_TRES_LETRAS, ART_PLAN_UMEDIDA, ART_PESO, cantidad, bodega_origen, bodega_destino, reservado, trasladado, completado, created_at, updated_at,picking ) ";
//                    strSql+=" VALUES("+objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_LIN)+",";  // id, --1  
                    strSql+="VALUES( "+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_pedido_id),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_detalle_id),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_detalle_stocks_id),INT_TBL_CFG_NUMEROS_NULL)+",";    
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_ART_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_ART_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_ART_CODIGO_TRES_LETRAS),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_ART_PLAN_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_ART_PESO),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_cantidad),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_bodega_origen),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_bodega_destino),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_reservado),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_trasladado),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_completado),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_picking),INT_TBL_CFG_TEXTO_BLANCK)+"";  
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModTrasladosVentas.getValueAt(i,INT_MIG_15_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=15; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarTrasladosReposicion(){ // falta corregir los formatos
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModTrasladosRep.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_traslados_reposicions(id,cabecera_id,Documento,Codigo_Motivo,ART_CODIGO,ART_DESCRIPCION,ART_CODIGO_TRES_LETRAS,ART_PLAN_UMEDIDA,ART_PESO,cantidad, bodega_origen, bodega_destino, compartimiento, Fecha_Documento, reservado, trasladado, completado, created_at, updated_at,picking ) ";
//                    strSql+=" VALUES("+objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_LIN)+",";  // id, --1  
                    strSql+="VALUES( "+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_id),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_cabecera_id),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_Documento),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_Codigo_Motivo),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_ART_CODIGO),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_ART_DESCRIPCION),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_ART_CODIGO_TRES_LETRAS),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_ART_PLAN_UMEDIDA),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_ART_PESO),INT_TBL_CFG_NUMEROS_NULL)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_cantidad),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_bodega_origen),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_bodega_destino),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_compartimiento),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_Fecha_Documento),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_reservado),INT_TBL_CFG_NUMEROS_NULL)+",";
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_trasladado),INT_TBL_CFG_NUMEROS_NULL)+",";
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_completado),INT_TBL_CFG_TEXTO_BLANCK)+",";
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_picking),INT_TBL_CFG_TEXTO_BLANCK)+"";  
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModTrasladosRep.getValueAt(i,INT_MIG_16_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=16; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarTrasladosReposicionCab(){ // falta corregir los formatos
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        int intLast=-1, intEjecutar=0;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for (int i=0; i<objTblModTrasladosRepCab.getRowCountTrue();i++){
                    intEjecutar++;
                    strSql+="INSERT INTO WebTuval.dbo.gp_traslados_reposicion_cabeceras(id,Documento,bodega_origen,bodega_destino,Codigo_Motivo,Motivo,Fecha_Documento,created_at,updated_at) ";
//                    strSql+=" VALUES("+objTblModOCGenDetalles.getValueAt(i,INT_MIG_13_LIN)+",";  // id, --1  
                    strSql+="VALUES( "+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_id),INT_TBL_CFG_NUMEROS_NULL)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Documento),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Bodega_Origen),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Bodega_Destino),INT_TBL_CFG_TEXTO_BLANCK)+",";    
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Codigo_Motivo),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Motivo),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_Fecha_Documento),INT_TBL_CFG_TEXTO_BLANCK)+","; 
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_created_at),INT_TBL_CFG_TEXTO_BLANCK)+",";  
                    strSql+=""+objUti.codificar(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_updated_at),INT_TBL_CFG_TEXTO_BLANCK)+"";  
                    strSql+=" );";
                    if(intEjecutar==100){
                        System.out.println(""+strSql);
                        stmLoc.executeUpdate(strSql);
                        strSql="";
                        intEjecutar=0;
                    }
                    intLast = Integer.parseInt(objTblModTrasladosRepCab.getValueAt(i,INT_MIG_17_id).toString());
                }
//                strSql="";
                strSql+=" UPDATE tbm_cabTblWeb SET ne_ultSecMig ="+intLast+" WHERE co_tbl=17; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                 
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * @param intCodTbl Codigo de la tabla que se desea migrar 
     * @return 
     */
    private boolean getNumeroUltimoDocumento(int intCodTbl){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            intUltDocIng = -1 ;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT CASE WHEN ne_ultSecMig IS NULL THEN 0 ELSE ne_ultSecMig END AS ne_ultSecMig FROM Dynamics2021.dbo.tbm_cabTblWeb WHERE co_tbl = "+intCodTbl+";";
                System.out.println("getNumeroUltimoDocumento: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intUltDocIng = rstLoc.getInt("ne_ultSecMig");
                }
                else{
                    blnRes=false;
                    System.out.println("no trajo ultimo documento...");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
                System.out.println("no abrio la conexion...");
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @return 
     */
    private boolean borrarCotizaciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_cotizacions  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ;  ";
                System.out.println("borrarCotizaciones: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @return 
     */
    private boolean borrarCotizacionesDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_cotizacion_detalles  "; // " + intUltDocIng + " 
                strSql+=" WHERE cotizacion_id IN ( SELECT id FROM WebTuval.dbo.gp_cotizacions WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"') ; ";
                System.out.println("borrarCotizacionesDetalles: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     /**
     * 
     * @return 
     */
    private boolean borrarPedidos(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_pedidos  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ;  ";
                System.out.println("borrarPedidos: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarPedidosDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_pedido_detalles  "; // " + intUltDocIng + " 
                strSql+=" WHERE pedido_id IN ( SELECT id FROM WebTuval.dbo.gp_pedidos WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"') ; ";
                System.out.println("borrarPedidosDetalles: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarGuiasTransfer(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_guia_remision_transferencias  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ;  ";
                System.out.println("borrarGuiasTransfer: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarGuias(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_guia_remisions  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ;  ";
                System.out.println("borrarGuias: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarGuiasDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_guia_remision_detalles  "; // " + intUltDocIng + " 
                strSql+=" WHERE guia_remision_id IN ( SELECT id FROM WebTuval.dbo.gp_guia_remisions WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"') ; ";
                System.out.println("borrarGuiasDetalles: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarDevoluciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_devoluciones  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ;  ";
                System.out.println("borrarDevoluciones: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
     /**
     * 
     * @return 
     */
    private boolean borrarDevolucionesDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_devoluciones_detalles  "; // " + intUltDocIng + " 
                strSql+=" WHERE devolucion_id IN ( SELECT id FROM WebTuval.dbo.gp_devoluciones WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"') ; ";
                System.out.println("borrarDevolucionesDetalles: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean borrarProveedores(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_proveedors  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarProveedores: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        private boolean borrarOrdenCompra(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_orden_de_compras  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarOrdenCompra: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private boolean borrarOrdComGenerados(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_orden_de_compra_generados  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarOrdenCompraGenerados: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     private boolean borrarOCGenDetalles(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_orden_de_compra_generado_detalles  "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarOCGenDetalles: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
     private boolean borrarODespachos(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_orden_despachos "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarOrdenDespachos: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private boolean borrarTrasladosVentas(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_traslados_ventas "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarTrasladosVentas: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private boolean borrarTrasladosReposicion(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_traslados_reposicions "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarTrasladosReposicion: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean borrarTrasladosReposicionCab(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
             conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM WebTuval.dbo.gp_traslados_reposicion_cabeceras "; // " + intUltDocIng + " 
                strSql+=" WHERE CAST(created_at as DATE) between '"+txtFecDes.getText()+"' AND '"+txtFecHas.getText()+"' ; ";
                System.out.println("borrarTrasladosReposicionCab: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
}

