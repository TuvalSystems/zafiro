/*
 * ZafImp35_01.java
 *
 * Created on 22 de May de 2019, 10h42
 *
 * v0.1
 */
package Importaciones.ZafImp35;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de presupuestos"
 */
public class ZafImp35_01 extends javax.swing.JDialog  
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN             = 0;
    private static final int INT_TBL_DAT_LIN_PED_APL     = 1;   //I=Insertar registro nuevo; M=Modificar registro existente; E=Eliminar registro existente.
    private static final int INT_TBL_DAT_CHK_SEL         = 2;
    private static final int INT_TBL_DAT_COD_EMP_PRE     = 3;
    private static final int INT_TBL_DAT_COD_LOC_PRE     = 4;
    private static final int INT_TBL_DAT_COD_TIP_DOC_PRE = 5;
    private static final int INT_TBL_DAT_COD_DOC_PRE     = 6;
    private static final int INT_TBL_DAT_COD_REG_PRE     = 7;
    private static final int INT_TBL_DAT_NOM_RUB_PRE     = 8;
    private static final int INT_TBL_DAT_VAL_TOT_DOC_PRE = 9;
    private static final int INT_TBL_DAT_VAL_TOT_APL_PRE =10;
    private static final int INT_TBL_DAT_VAL_TOT_PEN_PRE =11;
    private static final int INT_TBL_DAT_VAL_APL_PED_SEL =12;
    private static final int INT_TBL_DAT_CHK_CIE_PRE     =13;
    private static final int INT_TBL_DAT_CHK_CIE_RUB     =14;
    private static final int INT_TBL_DAT_NUM_REQ         =15;
    
    //ArrayList para Asociar los presupuestos a los pedidos. Este arraylist es para almacenar los datos a guardarse.
    public ArrayList arlDatAsoPre, arlRegAsoPre;
    public static final int INT_ARL_ASO_LINEA=              0;  
    public static final int INT_ARL_ASO_COD_EMP_PRE=        1;  
    public static final int INT_ARL_ASO_COD_LOC_PRE=        2; 
    public static final int INT_ARL_ASO_COD_TIP_DOC_PRE=    3; 
    public static final int INT_ARL_ASO_COD_DOC_PRE=        4; 
    public static final int INT_ARL_ASO_COD_REG_PRE=        5; 
    public static final int INT_ARL_ASO_NOM_RUB_PRE=        6; 
    public static final int INT_ARL_ASO_VAL_APL_PRE=        7;  
    public static final int INT_ARL_ASO_NUM_REQ=            8;
    
    //Arraylist del pedido seleccionado.
    public ArrayList arlDatPedImp, arlRegPedImp;
    public static final int INT_ARL_DAT_PED_COD_EMP=        0;  
    public static final int INT_ARL_DAT_PED_COD_LOC=        1;   
    public static final int INT_ARL_DAT_PED_COD_TIP_DOC=    2;  
    public static final int INT_ARL_DAT_PED_COD_DOC=        3;         
    public int intIndicePedImp=0; 

    //Variables.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                  //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblNum;  //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtValApl; 
    private ZafTblCelRenChk objTblCelRenChk;                      //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                      //Editor: JCheckBox en celda.
    private ZafTblPopMnu objTblPopMnu;                            //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda;                            //ToolTipText en TableHeader.
    private ZafTblTot objTblTot;                                  //JTable de totales.
    private ZafTblBus objTblBus;                                  //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                  //JTable de ordenamiento.
    private ZafImp objZafImp;
    private Vector vecReg, vecDat, vecCab;
    
    private boolean blnIsAce;  
    private String strSQL, strSQLSentencia;

    /**
     * Constructor
     */
    public ZafImp35_01(ZafParSis obj)
    {
        objParSis=obj;
        //Inicializar objetos.
        objUti = new ZafUtil();
    }   

    /**
     * Constructor
     */
    public ZafImp35_01(java.awt.Frame parent, boolean modal, ZafParSis obj, String sentencia, ArrayList arlPedido)
    {
        super(parent, modal);
        initComponents();
        
        arlDatPedImp=arlPedido; 
        strSQLSentencia=sentencia;
        
        //Inicializar objetos.
        objParSis = obj;
        objUti = new ZafUtil();
        objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));   
        
        if (configurarTblDat()) {
            if(cargarDetReg()) {
            }
        }
    }
    
    /**
     * Configuración de la tabla.
     * @return 
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_LIN_PED_APL, "Apl.");    
            vecCab.add(INT_TBL_DAT_CHK_SEL, "");
            vecCab.add(INT_TBL_DAT_COD_EMP_PRE, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_PRE, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_PRE, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC_PRE, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG_PRE, "Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NOM_RUB_PRE, "Presupuesto");
            vecCab.add(INT_TBL_DAT_VAL_TOT_DOC_PRE, "Val.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_APL_PRE, "Val.Tot.Apl.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_PEN_PRE, "Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_APL_PED_SEL, "Val.Apl.");
            vecCab.add(INT_TBL_DAT_CHK_CIE_PRE, "Cie.Pre.");  
            vecCab.add(INT_TBL_DAT_CHK_CIE_RUB, "Cie.Rub.");
            vecCab.add(INT_TBL_DAT_NUM_REQ, "Núm.Req.");
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_LIN_PED_APL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB_PRE).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_DOC_PRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_APL_PRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN_PRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_PED_SEL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_RUB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setPreferredWidth(30);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Ocultar columnas
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN_PED_APL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_CIE_PRE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_CIE_RUB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_REQ, tblDat);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false); 
                      
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat); 
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB_PRE).setCellRenderer(objTblCelRenLbl);
         
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_SEL);
            vecAux.add("" + INT_TBL_DAT_VAL_APL_PED_SEL);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Formato de Números
            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(objTblCelRenLblNum.INT_FOR_NUM);                           
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(), true, false); //Muestra 0
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_DOC_PRE).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_APL_PRE).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN_PRE).setCellRenderer(objTblCelRenLblNum);
            
            //Configurar JTable: Formato de Números
            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(objTblCelRenLblNum.INT_FOR_NUM);               
            objTblCelRenLblNum.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(), true, false); //Muestra 0
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_PED_SEL).setCellRenderer(objTblCelRenLblNum);  
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_PRE).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_RUB).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setCellRenderer(objTblCelRenChk);
           
        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellEditor(objTblCelEdiChk);  
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                String strLinSelApl="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  {
                        //Obtiene si el registro que se seleccionará existe o no en la BD.
                        strLinSelApl=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN_PED_APL)==null?"":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN_PED_APL).toString()); 

                        //Validar cierres.- No se valida el cierre del presupuesto mensual, por que AP solicitó que la parte de pedidos no verifique el cierre del presupuesto mensual.
                        if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_CIE_RUB) ){ 
                            //Valida Si pedido SI ha sido cerrado el rubro, bloquear edición.
                            objTblCelEdiChk.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El rubro del presupuesto seleccionado está cerrado.<BR>El pedido no puede asociarse a un rubro de presupuesto cerrado.</HTML>");
                        }
                        else{  
                            objTblCelEdiChk.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        String strDat=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_VAL_TOT_PEN_PRE)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_VAL_TOT_PEN_PRE).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_VAL_TOT_PEN_PRE).toString()); 
                        //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_SEL)) { //Desmarcado                
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_SEL);
                            objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_VAL_APL_PED_SEL);
                            if(strLinSelApl.equals("M")) {  //Si el registro existe y se desmarca la casilla, se asigna E para eliminar registro.
                                objTblMod.setValueAt("E", intFilSel, INT_TBL_DAT_LIN_PED_APL); //Eliminar registro existente 
                            } 
                        } 
                        else { //Marcado  
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_SEL);
                            if(objUti.isNumero(strDat)) {
                                if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)<=0) /* Valida que solo se pueda ingresar valores positivos. */
                                {   
                                    objTblMod.setValueAt("0", intFilSel, INT_TBL_DAT_VAL_APL_PED_SEL);
                                }
                                else{
                                    objTblMod.setValueAt(""+strDat, intFilSel, INT_TBL_DAT_VAL_APL_PED_SEL);
                                }
                            }                             
                            
                            if(strLinSelApl.equals("E")) {   //Si habia sido desmarcado un registro existente y vuelven a marcar la casilla, se asigna M para modificar.
                                objTblMod.setValueAt("M", intFilSel, INT_TBL_DAT_LIN_PED_APL);  //Modificar registro existente   
                            }                  
                        }
                    }
                }
            });   
            
            //Configurar JTable: Edición
            objTblCelEdiTxtValApl= new ZafTblCelEdiTxt();
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_PED_SEL).setCellEditor(objTblCelEdiTxtValApl); 
            objTblCelEdiTxtValApl.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                int intFilSel=-1;
                int intColSel=-1;
                String strDat="";  
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    
                    //Validar cierres.- No se valida el cierre del presupuesto mensual, por que AP solicitó que la parte de pedidos no verifique el cierre del presupuesto mensual.
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_CIE_RUB) ){ 
                        //Valida Si pedido SI ha sido cerrado el rubro, bloquear edición.
                        objTblCelEdiTxtValApl.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El rubro del presupuesto seleccionado está cerrado.<BR>No se puede aplicar ningún valor del rubro cerrado al pedido.</HTML>");
                    }
                    else{  
                        objTblCelEdiTxtValApl.setCancelarEdicion(false);
                    }                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strDat=objTblMod.getValueAt(intFilSel, intColSel)==null?"0":(objTblMod.getValueAt(intFilSel, intColSel).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, intColSel).toString()); 
                    if(objUti.isNumero(strDat)) {
                        if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)<=0) /* Valida que solo se pueda ingresar valores positivos. */
                        {   
                            objTblMod.setValueAt("", intFilSel, intColSel);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_SEL);
                        }
                        else{
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_SEL);
                        }
                    } 
                }
            });                
                        
            
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_COD_EMP_PRE:
                    strMsg="Código de la empresa del presupuesto";
                    break;
                case INT_TBL_DAT_COD_LOC_PRE:
                    strMsg="Código del local del presupuesto";
                    break;                    
                case INT_TBL_DAT_COD_TIP_DOC_PRE:
                    strMsg="Código del tipo del presupuesto";
                    break;
                case INT_TBL_DAT_COD_DOC_PRE:
                    strMsg="Código del presupuesto";
                    break;   
                case INT_TBL_DAT_COD_REG_PRE:
                    strMsg="Código del registro";
                    break;                       
                case INT_TBL_DAT_NOM_RUB_PRE:
                    strMsg="Rubro del presupuesto";
                    break;                
                case INT_TBL_DAT_VAL_TOT_DOC_PRE:
                    strMsg="Valor total del presupuesto";
                    break;
                case INT_TBL_DAT_VAL_TOT_APL_PRE:
                    strMsg="Valor total aplicado";
                    break;    
                case INT_TBL_DAT_VAL_TOT_PEN_PRE:
                    strMsg="Valor total pendiente";
                    break;                           
                case INT_TBL_DAT_VAL_APL_PED_SEL: 
                    strMsg="Valor a aplicar";
                    break;  
                case INT_TBL_DAT_CHK_CIE_PRE: 
                    strMsg="Cierre del presupuesto Mensual";
                    break;    
                case INT_TBL_DAT_CHK_CIE_RUB: 
                    strMsg="Cierre del rubro del presupuesto";                    
                    break;
                case INT_TBL_DAT_NUM_REQ: 
                    strMsg="Número de requerimiento(Presupuesto)";                    
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Presupuesto de Importaciones...");
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Asignación de presupuesto a los pedidos");
        panTit.add(lblTit, java.awt.BorderLayout.CENTER);

        PanFrm.add(panTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

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

        getAccessibleContext().setAccessibleName("Presupuesto de Importaciones...");

        setSize(new java.awt.Dimension(650, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        aceptar();

//        if (objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK_SEL)) {
//            aceptar();
//        } 
//        else {
//            mostrarMsgInf("<HTML>Seleccione un presupuesto y de click en el botón Aceptar.<BR>Si no desea seleccionar registro alguno de click en Cancelar.</HTML>");
//        }  

//        if (objTblMod.isDataModelChanged()) {
//            aceptar();
//        } 
//        else {
//            mostrarMsgInf("<HTML>No se han realizado cambios que guardar.<BR>De click en Cancelar.</HTML>");
//        }    
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnIsAce=false;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        BigDecimal bgdAux=BigDecimal.ZERO;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (conLoc!=null) 
            {
                stmLoc=conLoc.createStatement();
                //System.out.println("VentanaRubrosAplicados (2): "+strSQLSentencia);
                rstLoc=stmLoc.executeQuery(strSQLSentencia);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_LIN_PED_APL,       "");
                    vecReg.add(INT_TBL_DAT_CHK_SEL,           null);
                    vecReg.add(INT_TBL_DAT_COD_EMP_PRE,       rstLoc.getString("co_emp"));         
                    vecReg.add(INT_TBL_DAT_COD_LOC_PRE,       rstLoc.getString("co_loc"));         
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_PRE,   rstLoc.getString("co_tipDoc"));      
                    vecReg.add(INT_TBL_DAT_COD_DOC_PRE,       rstLoc.getString("co_doc"));  
                    vecReg.add(INT_TBL_DAT_COD_REG_PRE,       rstLoc.getString("co_reg"));   
                    vecReg.add(INT_TBL_DAT_NOM_RUB_PRE,       rstLoc.getString("tx_nomRub"));      
                    vecReg.add(INT_TBL_DAT_VAL_TOT_DOC_PRE,   rstLoc.getString("nd_ValTotDoc"));       
                    vecReg.add(INT_TBL_DAT_VAL_TOT_APL_PRE,   rstLoc.getString("nd_ValTotApl"));      
                    vecReg.add(INT_TBL_DAT_VAL_TOT_PEN_PRE,   rstLoc.getString("nd_ValTotPen"));
                    
                    bgdAux=objUti.redondearBigDecimal(rstLoc.getString("nd_valapl")==null?"0":rstLoc.getString("nd_valapl").equals("")?"0":rstLoc.getString("nd_valapl"), objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_DAT_VAL_APL_PED_SEL,   ""+bgdAux);   
  
                    vecReg.add(INT_TBL_DAT_CHK_CIE_PRE,       null);
                    vecReg.add(INT_TBL_DAT_CHK_CIE_RUB,       null);
                    vecReg.add(INT_TBL_DAT_NUM_REQ,           rstLoc.getString("tx_numReq"));//tx_numReq
                    
                    if ( rstLoc.getString("st_ciePre").equals("S") )  {   
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CIE_PRE);
                    }

                    if ( rstLoc.getString("st_cieRub").equals("S") )  {   
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CIE_RUB);
                    }

                    if ( rstLoc.getString("st_aplRub").equals("S") )  {   
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_SEL);
                        vecReg.setElementAt("M", INT_TBL_DAT_LIN_PED_APL); //Indica que ya existe (Modificar)
                    }
                    else{
                        vecReg.setElementAt(false, INT_TBL_DAT_CHK_SEL);
                        vecReg.setElementAt("I", INT_TBL_DAT_LIN_PED_APL); //Indica que es nuevo (Insertar)
                    }
                    vecDat.add(vecReg);
                }    
                rstLoc.close(); 
                rstLoc=null;
                stmLoc.close(); 
                stmLoc=null;
                conLoc.close();
                conLoc=null;

                //Asignar vectores al modelo.                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                //Calcular totales.
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal()
    {
        //Valida que el modelo haya sido cambiado
//        if(!objTblMod.isDataModelChanged()){
//            mostrarMsgInf("<HTML>La tabla de presupuesto no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
//            return false;
//        }
        
        //Valida que exista algún pedido seleccionado para cerrar pagos.
//        if(!objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK_SEL)){
//            mostrarMsgInf("<HTML>Debe seleccionar un presupuesto asociado al pedido.<BR>Si no desea asociar ningún presupuesto al pedido de click en Cancelar.</HTML>");
//            return false;
//        }
        
        //Valida que no existan cierres de los rubros del presupuesto
//        if(!validaEstCieRubPre()){
//            mostrarMsgInf("<HTML>Existen rubros del presupuesto que han sido cerrados</HTML>");
//            return false;
//        }
        return true;
    } 
    
    /**
     * Esta función valida que el rubro del presupuesto no haya sido cerrado para realizar asignación de pedido.
     * @return true: Si el rubro del presupuesto esta abierto, se puede continuar con el proceso de asignar presupuesto.
     * <BR>false: En el caso contrario.
     */
    private boolean validaEstCieRubPre()
    {
        boolean blnRes=false;
        boolean blnCie=false;
        String strLin="";
        Connection conCie;
        Statement stmCie;
        ResultSet rstCie;
        try
        {
            conCie=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (conCie!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue();i++)  
                {
                    strLin = objTblMod.getValueAt(i, INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if (strLin.equals("M"))   {
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_SEL))  {
                            stmCie=conCie.createStatement();
                            //Armar la sentencia SQL.
                            strSQL ="";
                            strSQL+=" SELECT * FROM tbm_detPreImp as a1";
                            strSQL+=" WHERE a1.st_cie IN ('S')";
                            strSQL+=" AND a1.co_Emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_PRE);        //co_emp
                            strSQL+=" AND a1.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_PRE);        //co_loc  
                            strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_PRE); //co_tipDoc  
                            strSQL+=" AND a1.co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_PRE);        //co_doc  
                            rstCie=stmCie.executeQuery(strSQL);
                            if(rstCie.next())   {
                                blnCie=true;
                                objTblMod.setChecked(new Boolean(false), i, INT_TBL_DAT_CHK_SEL);   //OJO: Puede ya existir valores aplicados.
                                objTblMod.setChecked(new Boolean(true), i, INT_TBL_DAT_CHK_CIE_RUB); 
                                objTblMod.setValueAt("", i, INT_TBL_DAT_VAL_APL_PED_SEL);           //OJO: Puede ya existir valores aplicados.                            
                            } 
                            rstCie.close();
                            rstCie=null;
                            stmCie.close();
                            stmCie=null;                            
                        }
                    }
                } 
                if(!blnCie)
                    blnRes=true;
                
                conCie.close();
                conCie=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Función que genera un arraylist con los datos de los presupuestos relacionados.
     * @return 
     */
    private boolean generaArlPreSel() { 
        boolean blnRes=true;
        String strLinApl; 
        BigDecimal bgdAux=BigDecimal.ZERO; 
        try  
        {  
            arlDatAsoPre = new ArrayList(); 
            for (int i=0; i<objTblMod.getRowCountTrue();i++)  
            {  
                arlRegAsoPre = new ArrayList();  
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_SEL))
                {
                    strLinApl = objTblMod.getValueAt(i, INT_TBL_DAT_LIN_PED_APL) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN_PED_APL).toString(); 
                    bgdAux= new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_PED_SEL)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_PED_SEL).equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_PED_SEL).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_LINEA,           ""+strLinApl); 
                    arlRegAsoPre.add(INT_ARL_ASO_COD_EMP_PRE,     ""+objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_PRE).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_COD_LOC_PRE,     ""+objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_PRE).toString()); 
                    arlRegAsoPre.add(INT_ARL_ASO_COD_TIP_DOC_PRE, ""+objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_PRE).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_COD_DOC_PRE,     ""+objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_PRE).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_COD_REG_PRE,     ""+objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_PRE).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_NOM_RUB_PRE,     ""+objTblMod.getValueAt(i, INT_TBL_DAT_NOM_RUB_PRE).toString());
                    arlRegAsoPre.add(INT_ARL_ASO_VAL_APL_PRE,     ""+bgdAux);
                    arlRegAsoPre.add(INT_ARL_ASO_NUM_REQ,         ""+ (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_REQ)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_REQ).toString()));
                    arlDatAsoPre.add(arlRegAsoPre);
                    
                }
            }
            System.out.println("arlDatAsoPre: "+arlDatAsoPre.toString());
        } 
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }        
        return blnRes;
    }     
    
    private void aceptar() 
    {
        if(isCamVal()){
            if(generaArlPreSel()){
                blnIsAce=true;
                dispose();
            }
        }
        else {
            mostrarMsgInf("<HTML>Verifique los datos y vuelva a intentarlo.</HTML>");
        }
    }

    /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Autorizar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Cerrar
    */
    public boolean isAceptar() {
        return blnIsAce;
    }    
    
    public ArrayList getArlPreSel(){ 
        return arlDatAsoPre;
    }     
    
}


