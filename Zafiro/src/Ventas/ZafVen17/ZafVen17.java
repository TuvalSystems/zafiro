/*
 * ZafCom85.java
 *
 * Created on 19 de Abril del 2016, 15:26
 */

package Ventas.ZafVen17;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafLocPrgUsr;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFrame;

/**
 *
 * @author  Jose Mario
 */
public class ZafVen17 extends javax.swing.JInternalFrame {
    
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    private static final int INT_TBL_DAT_CODSEG = 1;                            //Código de Seguimiento.
    
    private static final int INT_TBL_DAT_CODEMP_COTVEN = 2;                     //Código de Empresa de Cotización de Venta.
    private static final int INT_TBL_DAT_CODLOC_COTVEN = 3;                     //Código de Local de Cotización de Venta.
    private static final int INT_TBL_DAT_NOMLOC_COTVEN = 4;                     //Nombre del Local de Cotización de Venta.
    private static final int INT_TBL_DAT_FECDOC_COTVEN = 5;                     //Fecha de Cotización de Venta.
    private static final int INT_TBL_DAT_CODCOT_COTVEN = 6;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_TOTDOC_COTVEN = 7;                     //Código de Cotización de Venta.
    
    private static final int INT_TBL_DAT_BUT_COTVEN = 8;                        //Botón "Cotización de Venta".
    private static final int INT_TBL_DAT_BUT_FACPAR = 9;                        //Botón "Proceso FACTURACION PARCIAL".
    
    private static final int INT_TBL_DAT_CODEMP_COTVEN_HIJA = 10;                     
    private static final int INT_TBL_DAT_CODLOC_COTVEN_HIJA = 11;                     
    private static final int INT_TBL_DAT_NOMLOC_COTVEN_HIJA = 12;                    
    private static final int INT_TBL_DAT_FECDOC_COTVEN_HIJA = 13;                     //Fecha de Cotización de Venta.
    private static final int INT_TBL_DAT_CODCOT_COTVEN_HIJA = 14;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_TOTDOC_COTVEN_HIJA = 15;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_BUT_COTVEN_HIJA = 16;                        //Botón "Cotización de Venta".
    
    private static final int INT_TBL_DAT_CODEMP_FACVEN = 17;                     
    private static final int INT_TBL_DAT_CODLOC_FACVEN = 18;                     
    private static final int INT_TBL_DAT_CODTIPDOC_FACVEN = 19;                     
    private static final int INT_TBL_DAT_NOMLOC_FACVEN = 20;                    
    private static final int INT_TBL_DAT_FECDOC_FACVEN = 21;                     //Fecha de Cotización de Venta.
    private static final int INT_TBL_DAT_CODCOT_FACVEN = 22;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_NUMDOC_FACVEN = 23;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_TOTDOC_FACVEN = 24;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_BUT_FACVEN = 25;                        //Botón "Cotización de Venta".
    
    private static final int INT_TBL_DAT_CHK_PROCOM = 26;                       //Proceso Completo.
    
     
     
    
    //Variables
    
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Check Locales.
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblMod objTblModDat;           //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafLocPrgUsr objLocPrgUsr;                                          //Objeto que almacena los locales por usuario y programa.
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta.
    private ZafVenCon vcoDes;                                                   //Ventana de consulta.
    private ZafVenCon vcoLoc;                                                   //Ventana de consulta.
    private ZafSelFec objSelFec;                                                //Selector de Fecha.
    
   
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    
    private Vector vecDat, vecCab, vecReg, vecEstReg, vecAux;
    private String strSQL, strAux;
  
    private String strCodVen, strNomVen;                                        //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc;                                        //Contenido del campo al obtener el foco.
    
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Clientes".
    
    private String strSql;
    
    private String strVersion=" v 0.02";                                        //Versión del Programa.  
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen17(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
                    
            //Configurar Programas por Código de Menu.
            configurarFrmMnu();
    	     
            txtCodEmpLoc.setVisible(false);
          
            
            //Configurar las ZafVenCon.
            configurarVenConLoc();
            configurarVenConCli();
     
            configurarTblDat();
            
            //Configurar Combos.
            configurarComboEstReg();  
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /** Configurar Programas por Código de Menú */
    private void configurarFrmMnu(){
        //Configurar Selector de Fecha. 
        objSelFec=new ZafSelFec();
        objSelFec.setCheckBoxVisible(true);
        objSelFec.setCheckBoxChecked(true); 
        objSelFec.setTitulo("Fecha de Cotización ");
        panFil.add(objSelFec);
        objSelFec.setBounds(6, 4, 490, 75);
        configurarFechaDesde();

    
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_CODEMP_COTVEN,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_COTVEN,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOMLOC_COTVEN,"Local");
            vecCab.add(INT_TBL_DAT_FECDOC_COTVEN,"Fec.Cot.");
            vecCab.add(INT_TBL_DAT_CODCOT_COTVEN,"Núm.Cot.");  
            vecCab.add(INT_TBL_DAT_TOTDOC_COTVEN,"Tot.Dóc.");  
            vecCab.add(INT_TBL_DAT_BUT_COTVEN,"");
            vecCab.add(INT_TBL_DAT_BUT_FACPAR,"");
            vecCab.add(INT_TBL_DAT_CODEMP_COTVEN_HIJA,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_COTVEN_HIJA,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOMLOC_COTVEN_HIJA,"Local");
            vecCab.add(INT_TBL_DAT_FECDOC_COTVEN_HIJA,"Fec.Cot.");
            vecCab.add(INT_TBL_DAT_CODCOT_COTVEN_HIJA,"Núm.Cot.");  
            vecCab.add(INT_TBL_DAT_TOTDOC_COTVEN_HIJA,"Tot.Dóc.");  
            vecCab.add(INT_TBL_DAT_BUT_COTVEN_HIJA,"");
            vecCab.add(INT_TBL_DAT_CODEMP_FACVEN,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_FACVEN,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_FACVEN,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NOMLOC_FACVEN,"Local");
            vecCab.add(INT_TBL_DAT_FECDOC_FACVEN,"Fec.Fac.");
            vecCab.add(INT_TBL_DAT_CODCOT_FACVEN,"Cód.Doc.");  
            vecCab.add(INT_TBL_DAT_NUMDOC_FACVEN,"Núm.Doc.");  
            vecCab.add(INT_TBL_DAT_TOTDOC_FACVEN,"Tot.Dóc.");  
            vecCab.add(INT_TBL_DAT_BUT_FACVEN,"");
            vecCab.add(INT_TBL_DAT_CHK_PROCOM,"Proceso Completo");
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACPAR).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN_HIJA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN_HIJA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN_HIJA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN_HIJA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN_HIJA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN_HIJA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN_HIJA).setPreferredWidth(20);
      
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_FACVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_FACVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_FACVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_FACVEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_FACVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_FACVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_FACVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_FACVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Agrupar Columnas.
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
            //Grupo: Cotizaciones de Venta.
            ZafTblHeaColGrp objTblHeaColGrpCotVen=new ZafTblHeaColGrp("Cotizaciones de Venta");
            objTblHeaColGrpCotVen.setHeight(16);
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_BUT_FACPAR));
            
            
            
            //Grupo: Cotizaciones de Venta Hija.
            ZafTblHeaColGrp objTblHeaColGrpCotVenHija=new ZafTblHeaColGrp("Cotizaciones de Venta hija");
            objTblHeaColGrpCotVenHija.setHeight(16);
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN_HIJA));
            objTblHeaColGrpCotVenHija.add(tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN_HIJA));
            
            //Grupo: Facturas de Venta.
            ZafTblHeaColGrp objTblHeaColGrpFacVen=new ZafTblHeaColGrp("Facturas de Venta");
            objTblHeaColGrpFacVen.setHeight(16);
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODCOT_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_TOTDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN));
             
            //Agregar todos los grupos del Jtable.
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVen);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVenHija);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpFacVen);
         
//            
//            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_COTVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_COTVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_COTVEN_HIJA, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_COTVEN_HIJA, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_FACVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_FACVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODCOT_FACVEN, tblDat);
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
 
         
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setCellRenderer(objTblCelRenChk);
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi objZafTblEdi = new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_COTVEN);
            vecAux.add("" + INT_TBL_DAT_BUT_FACPAR);
            vecAux.add("" + INT_TBL_DAT_BUT_COTVEN_HIJA);
             vecAux.add("" + INT_TBL_DAT_BUT_FACVEN);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
                       
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACPAR).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN_HIJA).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setCellRenderer(objTblCelRenBut);
          
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_COTVEN:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_COTVEN).toString().equals("")) 
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
                         
                        case INT_TBL_DAT_BUT_COTVEN_HIJA:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_COTVEN_HIJA).toString().equals("")) 
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
                        case INT_TBL_DAT_BUT_FACVEN:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_FACVEN).toString().equals("")) 
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
 
                    }
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACPAR).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN_HIJA).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUT_COTVEN:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_COTVEN).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                            break;
                           case INT_TBL_DAT_BUT_COTVEN_HIJA:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_COTVEN_HIJA).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                            break;
                            case INT_TBL_DAT_BUT_FACVEN:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_FACVEN).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                            break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUT_COTVEN:
                                cargarVentanaCotizacion(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_COTVEN).toString(), 
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_COTVEN).toString(),
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT_COTVEN).toString() );
                            break;
                            case INT_TBL_DAT_BUT_FACPAR:
                                llamarVenProcesoFacturaParcial(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_COTVEN).toString(), 
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_COTVEN).toString(),
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT_COTVEN).toString() );
                            break;
                            case INT_TBL_DAT_BUT_COTVEN_HIJA:
                                cargarVentanaCotizacion(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_COTVEN_HIJA).toString(), 
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_COTVEN_HIJA).toString(),
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT_COTVEN_HIJA).toString() );
                            break;
                            case INT_TBL_DAT_BUT_FACVEN:
                                cargarVentanaFactura(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_COTVEN).toString(), 
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_FACVEN).toString(),
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_FACVEN).toString(),
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT_FACVEN).toString() );
                            break;
                        }
                    }
                }
            });
            
            
            
           
           
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_COTVEN_HIJA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_FACVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOTDOC_FACVEN).setCellRenderer(objTblCelRenLbl);
             objTblCelRenLbl=null;
            
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
      
    
    private boolean esImpar(int iNumero) 
    {
        if (iNumero%2!=0)
           return true;
        else
          return false;
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Cotización */">
    private void cargarVentanaCotizacion(String strCodEmp, String strCodLoc,  String strCodCot)
    {
        Ventas.ZafVen01.ZafVen01 obj = new Ventas.ZafVen01.ZafVen01(objParSis, strCodEmp, strCodLoc, strCodCot);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Factura */">
    private void cargarVentanaFactura(String strCodEmp, String strCodLoc,String strCodTipDoc,  String strCodDoc)
    {
        Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 14);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Llamar proceso de Factura parcial */">
    private void llamarVenProcesoFacturaParcial(String strCodEmp, String strCodLoc,  String strCodCot){
        ZafVen17_01 obj1 = new  ZafVen17_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis , strCodEmp, strCodLoc, strCodCot );
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();

//       obj1=null; 
    }
     //</editor-fold>
    
    /* Configurar Fecha Desde 1 mes atras. */
    private void configurarFechaDesde(){
        ZafDatePicker txtFecDoc;
        txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new Dimension(70, 20));
        txtFecDoc.setHoy();
        Calendar objFec = Calendar.getInstance();
        ZafDatePicker dtePckPag =  new ZafDatePicker(new JFrame(),"d/m/y");
        int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
        if (fecDoc!=null)
        {
            objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
            objFec.set(Calendar.MONTH, fecDoc[1] - 1);
            objFec.set(Calendar.YEAR, fecDoc[2]);
        }
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());
        objFecPagActual.add(Calendar.DATE, -30 );

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
        String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
    }
    
    private boolean configurarComboEstReg(){
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("Y");
            vecEstReg.add("D");
            vecEstReg.add("I");
          
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
   
     
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        int intTipLoc=2; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.co_emp");
            arlCam.add("a1.Emp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Loc.");
            arlAli.add("Local");
            arlAli.add("Cód.Emp.");
            arlAli.add("Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            arlAncCol.add("50");
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) 
            {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) //Grupo
                {
                    strSQL = objLocPrgUsr.cargarLocGrp(intTipLoc, objParSis.getCodigoEmpresaGrupo());
                }
                else 
                {
                    strSQL = objLocPrgUsr.cargarLoc(intTipLoc);
                }
            }
            else
            {
                strSQL = objLocPrgUsr.cargarLocUsr(intTipLoc);
            }
            System.out.println("ConfigurarVenConLoc: "+strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=3;  //Código Empresa
            intColOcu[1]=4;  //Nombre Empresa
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){  blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
      
    
   
    
     /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una busqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
      
    

    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código Seguimiento";
                    break;   
                case INT_TBL_DAT_CODEMP_COTVEN:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOC_COTVEN:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_NOMLOC_COTVEN:
                    strMsg="Nombre del Local";
                    break;
                case INT_TBL_DAT_FECDOC_COTVEN:
                    strMsg="Fecha de la cotización";
                    break;
                case INT_TBL_DAT_CODCOT_COTVEN:
                    strMsg="Número de la Cotización";
                    break;
                case INT_TBL_DAT_BUT_COTVEN:
                    strMsg="Muestra la Cotización de Venta";
                    break;
                
                    
                    
                case INT_TBL_DAT_CHK_PROCOM:
                    strMsg="Proceso Completo";
                    break; 
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
      
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodEmpLoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panCotVenDesHas = new javax.swing.JPanel();
        lblCotVenDes = new javax.swing.JLabel();
        txtCotVenDes = new javax.swing.JTextField();
        lblCotVenHas = new javax.swing.JLabel();
        txtCotVenHas = new javax.swing.JTextField();
        chkCotProPen = new javax.swing.JCheckBox();
        chkCotProCom = new javax.swing.JCheckBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
        setPreferredSize(new java.awt.Dimension(475, 580));
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

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(475, 580));
        panFil.setLayout(null);

        optTod.setText("Todos los documentos");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(20, 100, 400, 20);

        optFil.setSelected(true);
        optFil.setText("Sólo los  documentos que cumplan el criterio seleccionado");
        optFil.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optFilStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 120, 400, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Vendedor/Comprador");
        panFil.add(lblLoc);
        lblLoc.setBounds(40, 150, 100, 20);
        panFil.add(txtCodEmpLoc);
        txtCodEmpLoc.setBounds(90, 150, 32, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(120, 150, 94, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(215, 150, 396, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(610, 150, 20, 20);

        panCotVenDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Cotización"));
        panCotVenDesHas.setLayout(null);

        lblCotVenDes.setText("Desde:");
        panCotVenDesHas.add(lblCotVenDes);
        lblCotVenDes.setBounds(15, 20, 60, 16);

        txtCotVenDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotVenDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCotVenDesFocusLost(evt);
            }
        });
        panCotVenDesHas.add(txtCotVenDes);
        txtCotVenDes.setBounds(60, 20, 100, 20);

        lblCotVenHas.setText("Hasta:");
        panCotVenDesHas.add(lblCotVenHas);
        lblCotVenHas.setBounds(172, 20, 50, 16);

        txtCotVenHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotVenHasFocusGained(evt);
            }
        });
        panCotVenDesHas.add(txtCotVenHas);
        txtCotVenHas.setBounds(215, 20, 100, 20);

        panFil.add(panCotVenDesHas);
        panCotVenDesHas.setBounds(70, 210, 322, 48);

        chkCotProPen.setSelected(true);
        chkCotProPen.setText("Mostrar las cotizaciones que tienen procesos pendientes.");
        panFil.add(chkCotProPen);
        chkCotProPen.setBounds(40, 260, 400, 20);

        chkCotProCom.setText("Mostrar las cotizaciones que tienen el proceso completo.");
        panFil.add(chkCotProCom);
        chkCotProCom.setBounds(40, 280, 400, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(40, 170, 60, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(120, 170, 94, 20);

        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(215, 170, 396, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(610, 170, 20, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

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

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 451);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
 
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodEmpLoc.setText("");
            txtCodLoc.setText("");
            txtNomLoc.setText("");
          
            txtCotVenDes.setText("");
            txtCotVenHas.setText("");
            chkCotProPen.setSelected(false);
            chkCotProCom.setSelected(false);
            
            
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodVen=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodVen)) 
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodEmpLoc.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } 
            else
            {
                mostrarVenConLoc(1);
            }
        } 
        else
        {
            txtCodLoc.setText(strCodVen);
        }
        
        if (txtCodLoc.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomVen=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomVen)) 
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodEmpLoc.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } 
            else
            {
                mostrarVenConLoc(2);
            }
        } 
        else
        {
            txtNomLoc.setText(strNomVen);
        }
        
        if (txtNomLoc.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }  
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void txtCotVenDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenDesFocusGained
         txtCotVenDes.selectAll();
    }//GEN-LAST:event_txtCotVenDesFocusGained

    private void txtCotVenDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenDesFocusLost
        if (txtCotVenDes.getText().length() > 0) 
        {
            optFil.setSelected(true);
            if (txtCotVenHas.getText().length() == 0) 
                txtCotVenHas.setText(txtCotVenDes.getText());
        }
    }//GEN-LAST:event_txtCotVenDesFocusLost

    private void txtCotVenHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenHasFocusGained
        txtCotVenHas.selectAll();
    }//GEN-LAST:event_txtCotVenHasFocusGained

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilStateChanged

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)){
            if (txtCodCli.getText().equals("")){
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else{
                mostrarVenConCli(1);
            }
        }
        else
        txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.

    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.

    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        optFil.setSelected(true);
    }//GEN-LAST:event_butCliActionPerformed
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JCheckBox chkCotProCom;
    private javax.swing.JCheckBox chkCotProPen;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCotVenDes;
    private javax.swing.JLabel lblCotVenHas;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCotVenDesHas;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmpLoc;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCotVenDes;
    private javax.swing.JTextField txtCotVenHas;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
    
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        
        return true;
    }
    
    
     
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String sqlFil="",strConInv="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            strAux="";
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();      
                if(objSelFec.isCheckBoxChecked() ){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAux+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                    sqlFil+=" "+strAux;

                }
                
                //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Cotización. */">
                if(txtCotVenDes.getText().length()>0 && txtCotVenHas.getText().length() > 0){
                    strAux=" a1.co_cot BETWEEN "+objUti.codificar(txtCotVenDes.getText())+" AND "+objUti.codificar(txtCotVenHas.getText());//JM 
                    sqlFil+=" AND "+strAux;

                }
                //</editor-fold>


                strSql="";
                strSql+=" SELECT x1.*   \n";
                strSql+=" FROM (   \n";
                strSql+="   SELECT seg.co_seg, x1.tx_nom as nomLoc,  \n";
                strSql+="          a1.co_emp as co_empCotVen, a1.co_loc as co_locCotVen, a1.co_cot as co_cotLocVen, a1.fe_cot as fe_cotVen,\n";
                strSql+="          a1.st_reg as st_regCotVen, a1.nd_tot as nd_totCotVen,  \n";
                strSql+="          a3.co_emp as co_empCotRel, a3.co_loc as co_locCotRel, a3.co_cot as co_cotCotRel, a3.fe_cot as fe_cotCotRel, \n";
                strSql+="          a3.nd_tot as nd_totCotRel,  \n";
                strSql+="          a4.co_emp as co_empFacVen, a4.co_loc as co_locFacVen, a4.co_tipDoc as co_tipDocFacVen, a4.co_doc as co_docFacVen,   \n";
                strSql+="          a4.ne_numDoc as ne_numDocFacVen,  \n";
                strSql+="          a4.fe_doc as fe_docFacVen, (a4.nd_tot*-1) as nd_totFacVen  \n";
                strSql+="   FROM tbm_cabCotVen as a1  \n";
                strSql+="   INNER JOIN tbm_cabSegMovInv as seg ON (a1.co_emp=seg.co_empRelCabCotVen AND a1.co_loc=seg.co_locRelCabCotVen AND a1.co_cot=seg.co_cotRelCabCotVen) \n";
                strSql+="   INNER JOIN tbm_loc AS x1 ON (a1.co_emp=x1.co_emp AND a1.co_loc=x1.co_loc) \n";
                strSql+="   LEFT OUTER JOIN tbr_cabCotVen as a2 ON (a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel AND a1.co_cot=a2.co_cotRel /*AND a2.tx_tipRel='P'*/) \n";
                strSql+="   LEFT OUTER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot) \n";
                strSql+="   LEFT OUTER JOIN tbm_cabMovInv AS a4 ON (   a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_cot=a4.ne_numCot AND a4.co_tipDoc=228)    \n";
                if(objParSis.getCodigoUsuario()!=1){
                    strSql+="   INNER JOIN tbr_locPrgUsr AS a5 ON (a1.co_emp=a5.co_empRel AND a1.co_loc=a5.co_locRel AND a5.co_mnu="+objParSis.getCodigoMenu()+" AND ";
                    strSql+="   a5.co_usr="+objParSis.getCodigoUsuario()+" AND a5.co_emp="+objParSis.getCodigoEmpresa()+" AND a5.co_loc="+objParSis.getCodigoLocal()+ ") ";
                }
                
                strSql+="   WHERE a1.st_solResInv IS NULL AND (a1.tx_tipCot IS NULL OR a1.tx_tipCot='P') \n";
                strSql+=sqlFil;

                if(txtCodLoc.getText().length()>0){
                    strSql+=" AND a1.co_emp="+txtCodEmpLoc.getText()+" AND a1.co_loc="+txtCodLoc.getText()+" ";
                }
                
                if(chkCotProPen.isSelected() && chkCotProCom.isSelected()){
                    strSql+=" AND a1.st_reg IN ('E','F')";
                }
                else if(chkCotProPen.isSelected() && !chkCotProCom.isSelected()){
                    strSql+=" AND a1.st_reg IN ('E')";
                }
                else if(chkCotProCom.isSelected() && !chkCotProPen.isSelected()){
                    strSql+=" AND a1.st_reg IN ('F')";
                }
                
                if (txtCodCli.getText().length()>0){
                    strSql+=" AND a1.co_cli=" + txtCodCli.getText();
                }
                strSql+="  UNION ALL \n";
                strSql+="  SELECT seg.co_seg, x1.tx_nom as nomLoc,   \n";
                strSql+="        a1.co_emp as co_empCotVen, a1.co_loc as co_locCotVen, a1.co_cot as co_cotLocVen, a1.fe_cot as fe_cotVen, \n";
                strSql+="        a1.st_reg as st_regCotVen, a1.nd_tot as nd_totCotVen, \n";
                strSql+="        null as co_empCotRel, null as co_locCotRel, null as co_cotCotRel, null as fe_cotCotRel, null as nd_totCotRel,   \n";
                strSql+="        a4.co_emp as co_empFacVen, a4.co_loc as co_locFacVen, a4.co_tipDoc as co_tipDocFacVen, a4.co_doc as co_docFacVen,  \n";
                strSql+="        a4.ne_numDoc as ne_numDocFacVen, a4.fe_doc as fe_docFacVen, (a4.nd_tot*-1) as nd_totFacVen     \n";
                strSql+="   FROM tbm_cabCotVen as a1   \n";
                strSql+="   INNER JOIN tbm_cabSegMovInv as seg ON (a1.co_emp=seg.co_empRelCabCotVen AND a1.co_loc=seg.co_locRelCabCotVen AND a1.co_cot=seg.co_cotRelCabCotVen)  \n";
                strSql+="   INNER JOIN tbm_loc AS x1 ON (a1.co_emp=x1.co_emp AND a1.co_loc=x1.co_loc)  \n";
                strSql+="   INNER JOIN tbm_cabMovInv AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cot=a4.ne_numCot AND a4.co_tipDoc=228 AND a1.tx_tipCot='P')   \n";
                if(objParSis.getCodigoUsuario()!=1){
                    strSql+="       INNER JOIN tbr_locPrgUsr AS a5 ON (a1.co_emp=a5.co_empRel AND a1.co_loc=a5.co_locRel AND a5.co_mnu="+objParSis.getCodigoMenu()+" AND ";
                    strSql+="   a5.co_usr="+objParSis.getCodigoUsuario()+" AND a5.co_emp="+objParSis.getCodigoEmpresa()+" AND a5.co_loc="+objParSis.getCodigoLocal()+ ") ";
                }
                strSql+="   WHERE 1=1 \n";
                strSql+=sqlFil;
                if(txtCodLoc.getText().length()>0){
                    strSql+=" AND a1.co_emp="+txtCodEmpLoc.getText()+" AND a1.co_loc="+txtCodLoc.getText()+" ";
                }
                if(chkCotProPen.isSelected() && chkCotProCom.isSelected()){
                    strSql+=" AND a1.st_reg IN ('E','F')";
                }
                else if(chkCotProPen.isSelected() && !chkCotProCom.isSelected()){
                    strSql+=" AND a1.st_reg IN ('E')";
                }
                else if(chkCotProCom.isSelected() && !chkCotProPen.isSelected()){
                    strSql+=" AND a1.st_reg IN ('F')";
                }
                
                if (txtCodCli.getText().length()>0){
                    strSql+=" AND a1.co_cli=" + txtCodCli.getText();
                }
                strSql+=" ) as x1  \n";
                strSql+=" ORDER BY x1.co_empCotVen, x1.co_locCotVen, x1.co_cotLocVen   \n";
                 
	 
	  
                strSql+=" \n";
                System.out.println("cargarDetReg: "+strSql);   
                rstLoc=stmLoc.executeQuery(strSql);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CODSEG,rstLoc.getString("co_Seg")==null?"":rstLoc.getString("co_Seg"));
                        vecReg.add(INT_TBL_DAT_CODEMP_COTVEN,rstLoc.getString("co_empCotVen")==null?"":rstLoc.getString("co_empCotVen"));
                        vecReg.add(INT_TBL_DAT_CODLOC_COTVEN,rstLoc.getString("co_locCotVen")==null?"":rstLoc.getString("co_locCotVen"));
                        vecReg.add(INT_TBL_DAT_NOMLOC_COTVEN,rstLoc.getString("nomLoc")==null?"":rstLoc.getString("nomLoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC_COTVEN,rstLoc.getString("fe_cotVen")==null?"":rstLoc.getString("fe_cotVen"));
                        vecReg.add(INT_TBL_DAT_CODCOT_COTVEN,rstLoc.getString("co_cotLocVen")==null?"":rstLoc.getString("co_cotLocVen"));
                        vecReg.add(INT_TBL_DAT_TOTDOC_COTVEN,objUti.redondear(rstLoc.getString("nd_totCotVen")==null?"0":rstLoc.getString("nd_totCotVen"), objParSis.getDecimalesMostrar()));
                        vecReg.add(INT_TBL_DAT_BUT_COTVEN,"");
                        vecReg.add(INT_TBL_DAT_BUT_FACPAR,"");
                        
                        vecReg.add(INT_TBL_DAT_CODEMP_COTVEN_HIJA,rstLoc.getString("co_empCotRel")==null?"":rstLoc.getString("co_empCotRel"));
                        vecReg.add(INT_TBL_DAT_CODLOC_COTVEN_HIJA,rstLoc.getString("co_locCotRel")==null?"":rstLoc.getString("co_locCotRel"));
                        if(rstLoc.getString("co_empCotRel")==null){
                            vecReg.add(INT_TBL_DAT_NOMLOC_COTVEN_HIJA,"");
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_NOMLOC_COTVEN_HIJA,rstLoc.getString("nomLoc")==null?"":rstLoc.getString("nomLoc"));
                        }
                        vecReg.add(INT_TBL_DAT_FECDOC_COTVEN_HIJA,rstLoc.getString("fe_CotCotRel")==null?"":rstLoc.getString("fe_CotCotRel"));
                        vecReg.add(INT_TBL_DAT_CODCOT_COTVEN_HIJA,rstLoc.getString("co_cotCotRel")==null?"":rstLoc.getString("co_CotCotRel"));
                        vecReg.add(INT_TBL_DAT_TOTDOC_COTVEN_HIJA,objUti.redondear(rstLoc.getString("nd_totCotRel")==null?"0":rstLoc.getString("nd_totCotRel"), objParSis.getDecimalesMostrar()));
                        vecReg.add(INT_TBL_DAT_BUT_COTVEN_HIJA,"");
                        
                        vecReg.add(INT_TBL_DAT_CODEMP_FACVEN,rstLoc.getString("co_empFacVen")==null?"":rstLoc.getString("co_empFacVen"));
                        vecReg.add(INT_TBL_DAT_CODLOC_FACVEN,rstLoc.getString("co_locFacVen")==null?"":rstLoc.getString("co_locFacVen"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC_FACVEN,rstLoc.getString("co_tipDocFacVen")==null?"":rstLoc.getString("co_tipDocFacVen"));
                        if(rstLoc.getString("co_empFacVen")==null){
                            vecReg.add(INT_TBL_DAT_NOMLOC_FACVEN,"");
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_NOMLOC_FACVEN,rstLoc.getString("nomLoc")==null?"":rstLoc.getString("nomLoc"));
                        }
                        vecReg.add(INT_TBL_DAT_FECDOC_FACVEN,rstLoc.getString("fe_docFacVen")==null?"":rstLoc.getString("fe_docFacVen"));
                        vecReg.add(INT_TBL_DAT_CODCOT_FACVEN,rstLoc.getString("co_docFacVen")==null?"":rstLoc.getString("co_docFacVen"));
                        vecReg.add(INT_TBL_DAT_NUMDOC_FACVEN,rstLoc.getString("ne_numDocFacVen")==null?"":rstLoc.getString("ne_numDocFacVen"));
                        vecReg.add(INT_TBL_DAT_TOTDOC_FACVEN,objUti.redondear(rstLoc.getString("nd_totFacVen")==null?"0":rstLoc.getString("nd_totFacVen"), objParSis.getDecimalesMostrar()));
                        vecReg.add(INT_TBL_DAT_BUT_FACVEN,"");
                         
        
                        if(rstLoc.getString("st_regCotVen")!=null)  strConInv=rstLoc.getString("st_regCotVen");    else    strConInv="";
                  
                        vecReg.add(INT_TBL_DAT_CHK_PROCOM,((strConInv.compareTo("F")==0)? Boolean.TRUE: Boolean.FALSE));
               
                        vecDat.add(vecReg);
                        
                    }
                }
                
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("394");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
    
}
