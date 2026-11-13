/*
 * ZafCom06.java
 *
 * Created on Mar 1, 2010, 9:46:43 AM
 */
package Compras.ZafCom06;
import GenOD.ZafGenOdDaoPryTra;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafParSis.ZafParSis;
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
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * "Autorización de ordenes de despacho..."
 * @author jayapata
 */
public class ZafCom06 extends JInternalFrame 
{
    //Constantes: JTable
    private static final int INT_TBL_LIN=0;                       
    private static final int INT_TBL_COD_EMP =1;                    
    private static final int INT_TBL_COD_LOC =2;                   
    private static final int INT_TBL_COD_TIP_DOC =3;                
    private static final int INT_TBL_DES_TIP_DOC =4;                
    private static final int INT_TBL_COD_DOC =5;                    
    private static final int INT_TBL_NUM_ORD_DES=6;                    
    private static final int INT_TBL_NUM_FAC_VEN=7;
    private static final int INT_TBL_REF_DOC_ORI=8;
    private static final int INT_TBL_FEC_DOC=9;                     
    private static final int INT_TBL_COD_CLI=10;                     
    private static final int INT_TBL_NOM_CLI=11;                 
    private static final int INT_TBL_CHK_AUT=12;                 
    private static final int INT_TBL_FEC_AUT=13;
    private static final int INT_TBL_TXT_OBS_AUT = 14;
    private static final int INT_TBL_BUT_OBS_AUT = 15;
    private static final int INT_TBL_TXT_OBS_IMP = 16;
    private static final int INT_TBL_BUT_OBS_IMP = 17;    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JCheckBox en celda.    
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.    
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;    
    private ZafVenCon vcoBodUsr;
    private ZafVenCon vcoCli;
    private ZafVenCon vcoVen;
    private ZafThreadGUI objThrGUI;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;
    
    private int intNumFac=0;
    
    private String strSQL, strAux;
    private String strCodBod="", strNomBod="";
    private String strCodCli="", strDesCli="";
    private String strCodVen="", strDesVen="";    
    private String strVer=" v1.8";

    
    private String strTit = "Mensaje del sistema Zafiro";
    
    /** Crea una nueva instancia de la clase ZafCom06. */
    public ZafCom06(ZafParSis obj)
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
        boolean blnRes = true;
        try 
        {
            //Título de la ventana.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            
            //Fecha del documento.
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setBounds(30, 150, 472, 72);      
            panGen.add(objSelFec);
            
            //Configurar las ZafVenCon.
            configurarVenConBodUsr();
            configurarVenConCli();
            configurarVenConVen();
            cargarBodPre();            
            
            //Configurar los JTables.
            configurarTblDat();   
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL_DES_TIP_DOC,"Tip.Doc");
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc");
            vecCab.add(INT_TBL_NUM_ORD_DES,"Num.Ord.Des.");
            vecCab.add(INT_TBL_NUM_FAC_VEN,"Num.Fac.");
            vecCab.add(INT_TBL_REF_DOC_ORI,"Doc.Ori.");
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc");
            vecCab.add(INT_TBL_COD_CLI,"Cod.Cli");
            vecCab.add(INT_TBL_NOM_CLI,"Nom.Cli");
            vecCab.add(INT_TBL_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_FEC_AUT,"Fec.Aut");
            vecCab.add(INT_TBL_TXT_OBS_AUT,"Obs.Aut");
            vecCab.add(INT_TBL_BUT_OBS_AUT,"...");
            vecCab.add(INT_TBL_TXT_OBS_IMP,"Obs.Imp");
            vecCab.add(INT_TBL_BUT_OBS_IMP,"...");     
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);          
            
            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            //Configurar JTable: Ordenamiento
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHK_AUT);
            vecAux.add("" + INT_TBL_TXT_OBS_AUT);
            vecAux.add("" + INT_TBL_BUT_OBS_AUT);
            vecAux.add("" + INT_TBL_TXT_OBS_IMP);
            vecAux.add("" + INT_TBL_BUT_OBS_IMP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DES_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_ORD_DES).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NUM_FAC_VEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_REF_DOC_ORI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_CHK_AUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TXT_OBS_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUT_OBS_AUT).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_TXT_OBS_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUT_OBS_IMP).setPreferredWidth(25);                     
                      
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_EMP, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_LOC, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_TIP_DOC, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DOC, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_REF_DOC_ORI, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_FEC_AUT, tblDat); 
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_CLI).setCellRenderer(objTblCelRenLbl);    
            objTblCelRenLbl=null;
            
            //Configurar: Celda Check
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_AUT).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;            
            
            //Configurar: Edición Check
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHK_AUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Configurar JTable:  Botones.
            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_OBS_AUT).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUT_OBS_IMP).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt)   {
                    objTblCelRenBut.setText("...");
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUT_OBS_AUT).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_BUT_OBS_IMP).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt)  {
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_BUT_OBS_AUT:
                                cargarVentanaObs(tblDat.getSelectedRow(), INT_TBL_TXT_OBS_AUT );
                              
                                break;
                            case INT_TBL_BUT_OBS_IMP:
                                cargarVentanaObs(tblDat.getSelectedRow(), INT_TBL_TXT_OBS_IMP );
                                break;
                        }
                    }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);            
            
            //Libero los objetos auxiliares.
            tcmAux = null;
            
            setEditable(true);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
                case INT_TBL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DES_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;                       
                case INT_TBL_NUM_ORD_DES:
                    strMsg="Número de Orden de Despacho";
                    break;
                case INT_TBL_NUM_FAC_VEN:
                    strMsg="Número de Factura";
                    break;     
               case INT_TBL_REF_DOC_ORI:
                    strMsg="Documento Origen";
                    break;                                   
                case INT_TBL_FEC_DOC:
                    strMsg="Fecha de Documento (Factura/Orden despacho)";
                    break;                    
                case INT_TBL_COD_CLI:
                    strMsg="Código de cliente";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg="Nombre de cliente";
                    break;                                
                case INT_TBL_CHK_AUT:
                    strMsg="Autorización";
                    break;  
                case INT_TBL_TXT_OBS_AUT:
                    strMsg="Observación de la autorización. Motivo por el cuál se autoriza la OD.";
                    break;    
                case INT_TBL_BUT_OBS_AUT:
                    strMsg="Botón observación autorización";
                    break;    
                case INT_TBL_TXT_OBS_IMP:
                    strMsg="Observación de impresión. Dato que aparecerá en OD cuando se imprima.";
                    break;  
                case INT_TBL_BUT_OBS_IMP:
                    strMsg="Botón observación Impresión";
                    break;                      
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }     

    //<editor-fold defaultstate="collapsed" desc="/* Botón Observacion*/">
    private void cargarVentanaObs( int intFil, int intCol)
    {
        String strObs = (objTblMod.getValueAt(intFil, intCol) == null ? "" : objTblMod.getValueAt(intFil, intCol).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            objTblMod.setValueAt(obj.getObser(), intFil, intCol);
        }
        obj = null;
    }
    //</editor-fold>

    private boolean configurarVenConBodUsr() 
    {
        boolean blnRes=true;
        try 
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   
            if (objParSis.getCodigoUsuario()==1)
            {   //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                strSQL ="";
                strSQL+=" SELECT a.co_bod, a.tx_nom";
                strSQL+=" FROM (  ";
                strSQL+="       SELECT a2.co_bod, a2.tx_nom FROM tbm_emp AS a1 ";
                strSQL+="       INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" ) AS a ";
                strSQL+=" ORDER BY a.co_bod";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a.co_bod, a.tx_nom";
                strSQL+=" FROM ( " ;
                strSQL+="       SELECT a2.co_bodgrp as co_bod, a1.tx_nom FROM tbr_bodlocprgusr AS a ";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod) ";
                strSQL+="       INNER JOIN tbm_bod AS a1 ON (a1.co_emp=a2.co_empgrp AND a1.co_bod=a2.co_bodgrp) " ;
                strSQL+="       WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal();
                strSQL+="       AND a.co_usr="+objParSis.getCodigoUsuario()+" AND a.co_mnu="+objParSis.getCodigoMenu();
                strSQL+=" ) AS a";
                strSQL+=" ORDER BY a.co_bod";
            }

            vcoBodUsr=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            vcoBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConCli() 
    {
        boolean blnRes=true;
        try 
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("180");
            arlAncCol.add("120");
            arlAncCol.add("80");
            arlAncCol.add("100");

            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 ";
            strSQL+=" INNER JOIN tbm_cli AS a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " ;
            strSQL+=" WHERE a.st_reg NOT IN('I','T')";
            strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+"";
            strSQL+=" ORDER BY a.tx_nom ";

            vcoCli=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
    }

    private boolean configurarVenConVen() 
    {
        boolean blnRes=true;
        try 
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a.co_usr, a.tx_nom FROM tbr_usremp AS b";
            strSQL+=" INNER JOIN tbm_usr AS a ON (a.co_usr=b.co_usr)";
            strSQL+=" WHERE b.st_ven='S' AND a.st_reg NOT IN ('I')";
            strSQL+=" AND b.co_emp="+objParSis.getCodigoEmpresa()+"";
            strSQL+=" ORDER BY a.tx_nom";

            vcoVen=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
    private void BuscarBod(String campo,String strBusqueda,int tipo)
    {
        vcoBodUsr.setTitle("Listado de Bodegas");
        if(vcoBodUsr.buscar(campo, strBusqueda )) {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod=vcoBodUsr.getValueAt(1);
            strNomBod=vcoBodUsr.getValueAt(2);
        }
        else{
            vcoBodUsr.setCampoBusqueda(tipo);
            vcoBodUsr.cargarDatos();
            vcoBodUsr.show();
            if (vcoBodUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
               txtCodBod.setText(vcoBodUsr.getValueAt(1));
               txtNomBod.setText(vcoBodUsr.getValueAt(2));
               strCodBod=vcoBodUsr.getValueAt(1);
               strNomBod=vcoBodUsr.getValueAt(2);
            }else{
               txtCodBod.setText(strCodBod);
               txtNomBod.setText(strNomBod);

            }
        }
    }

    private void BuscarCliente(String campo,String strBusqueda,int tipo)
    {
        vcoCli.setTitle("Listado de Clientes");
        if(vcoCli.buscar(campo, strBusqueda )) {
            txtCodCli.setText(vcoCli.getValueAt(1));
            txtNomCli.setText(vcoCli.getValueAt(2));
            optFil.setSelected(true);
        }else{
            vcoCli.setCampoBusqueda(tipo);
            vcoCli.cargarDatos();
            vcoCli.show();
            if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
               txtCodCli.setText(vcoCli.getValueAt(1));
               txtNomCli.setText(vcoCli.getValueAt(2));
               optFil.setSelected(true);
            }
            else{
                txtCodCli.setText(strCodCli);
                txtNomCli.setText(strDesCli);
            }
        }
    }

    private void BuscarVendedor(String campo,String strBusqueda,int tipo)
    {
        vcoVen.setTitle("Listado de Vendedores");
        if(vcoVen.buscar(campo, strBusqueda )) {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            optFil.setSelected(true);
        }
        else{
            vcoVen.setCampoBusqueda(tipo);
            vcoVen.cargarDatos();
            vcoVen.show();
            if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                txtCodVen.setText(vcoVen.getValueAt(1));
                txtNomVen.setText(vcoVen.getValueAt(2));
                optFil.setSelected(true);
            }
            else{
                txtCodVen.setText(strCodVen);
                txtNomVen.setText(strDesVen);

            }
        }
    }    

    private void cargarBodPre()
    {
        Connection  conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            conLoc =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT co_bod, tx_nom ";
                strSQL+=" FROM ( ";
                strSQL+="       SELECT a2.co_bodgrp as co_bod, a1.tx_nom FROM tbr_bodlocprgusr as a ";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod) ";
                strSQL+="       INNER JOIN tbm_bod AS a1 ON (a1.co_emp=a2.co_empgrp AND a1.co_bod=a2.co_bodgrp) ";
                strSQL+="       WHERE a.st_reg='S' AND a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" ";
                strSQL+="       AND a.co_usr="+objParSis.getCodigoUsuario()+" AND a.co_mnu="+objParSis.getCodigoMenu()+"  ";
                strSQL+=" ) AS a";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    txtCodBod.setText(rstLoc.getString("co_bod"));
                    txtNomBod.setText(rstLoc.getString("tx_nom"));
                    strCodBod=rstLoc.getString("co_bod");
                    strNomBod=rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }

    private void setEditable(boolean editable) 
    {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    private void showColumns(int intCol, int intAch){
        tblDat.getColumnModel().getColumn(intCol).setWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void hideColumns(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        chkOrdDesAut = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(457, 350));

        panGen.setPreferredSize(new java.awt.Dimension(0, 300));
        panGen.setLayout(null);

        lblBod.setText("Bodega:");
        panGen.add(lblBod);
        lblBod.setBounds(10, 10, 90, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
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
        panGen.add(txtCodBod);
        txtCodBod.setBounds(100, 10, 80, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
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
        panGen.add(txtNomBod);
        txtNomBod.setBounds(180, 10, 280, 20);

        butBod.setText("jButton2");
        butBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panGen.add(butBod);
        butBod.setBounds(460, 10, 20, 20);

        optTod.setSelected(true);
        optTod.setText("Todos las órdenes de despacho");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGen.add(optTod);
        optTod.setBounds(10, 50, 450, 20);

        optFil.setText("Sólo las órdenes de despacho que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
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
        panGen.add(optFil);
        optFil.setBounds(10, 70, 560, 20);

        lblCli.setText("Cliente");
        panGen.add(lblCli);
        lblCli.setBounds(30, 100, 100, 20);

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
        panGen.add(txtCodCli);
        txtCodCli.setBounds(100, 100, 80, 20);

        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panGen.add(txtNomCli);
        txtNomCli.setBounds(180, 100, 280, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGen.add(butCli);
        butCli.setBounds(460, 100, 20, 20);

        lblVen.setText("Vendedor");
        panGen.add(lblVen);
        lblVen.setBounds(30, 120, 100, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panGen.add(txtCodVen);
        txtCodVen.setBounds(100, 120, 80, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panGen.add(txtNomVen);
        txtNomVen.setBounds(180, 120, 280, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGen.add(butVen);
        butVen.setBounds(460, 120, 20, 20);

        chkOrdDesAut.setText("Mostrar las ordenes de despacho que ya fueron autorizadas");
        chkOrdDesAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOrdDesAutActionPerformed(evt);
            }
        });
        panGen.add(chkOrdDesAut);
        chkOrdDesAut.setBounds(30, 240, 450, 23);

        tabFrm.addTab("Filtro", panGen);
        panGen.getAccessibleContext().setAccessibleParent(tabFrm);

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
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);
        panRpt.getAccessibleContext().setAccessibleParent(tabFrm);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(296, 30));
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

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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
        lblMsgSis.setPreferredSize(new java.awt.Dimension(26, 25));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 25));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.co_bod",txtCodBod.getText(),0);
        }else
            txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.tx_nom",txtNomBod.getText(),1);
        }else
            txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        vcoBodUsr.setTitle("Listado de Bodegas");
        vcoBodUsr.setCampoBusqueda(1);
        vcoBodUsr.show();
        if (vcoBodUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod=vcoBodUsr.getValueAt(1);
            strNomBod=vcoBodUsr.getValueAt(2);
        }
}//GEN-LAST:event_butBodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
    }//GEN-LAST:event_optFilItemStateChanged

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
            txtCodVen.setText(strCodVen);
        
        if(txtCodVen.getText().length()>0){
            optFil.setSelected(true);
        }            
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);
        
        if(txtCodCli.getText().length()>0){
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
}//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strDesCli);
        if(txtNomCli.getText().length()>0){
            optFil.setSelected(true);
        }        
}//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
            txtNomVen.setText(strDesVen);
        
        if(txtNomVen.getText().length()>0){
            optFil.setSelected(true);
        }            
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        vcoVen.setTitle("Listado de Clientes");
        vcoVen.setCampoBusqueda(1);
        vcoVen.show();
        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        vcoCli.setTitle("Listado de Clientes");
        vcoCli.setCampoBusqueda(1);
        vcoCli.show();
        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodCli.setText(vcoCli.getValueAt(1));
            txtNomCli.setText(vcoCli.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butCliActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strMsg;
        String strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkOrdDesAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOrdDesAutActionPerformed
        if(chkOrdDesAut.isSelected()){
            showColumns(INT_TBL_REF_DOC_ORI, 120);
            showColumns(INT_TBL_FEC_AUT, 80);
            hideColumns(INT_TBL_CHK_AUT);
            butGua.setVisible(false);
            optFil.setSelected(true);
            objTblMod.removeAllRows();
        }
        else{
            showColumns(INT_TBL_CHK_AUT, 30);
            hideColumns(INT_TBL_REF_DOC_ORI);
            hideColumns(INT_TBL_FEC_AUT);
            butGua.setVisible(true);
            optTod.setSelected(true);
            objTblMod.removeAllRows();
        }
    }//GEN-LAST:event_chkOrdDesAutActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilStateChanged

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        asignarNumOrdDes();
        cargarDetReg(sqlConFil());
    }//GEN-LAST:event_butGuaActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if(txtCodBod.getText().equals("")){
            mostrarMsgInf("Seleccione la bodega antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBod.requestFocus();
        } else{
            if (butCon.getText().equals("Consultar")) {
                blnCon=true;
                if (objThrGUI==null) {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            } else {
                blnCon=false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkOrdDesAut;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    

    private class ZafThreadGUI extends Thread {
        @Override
        public void run()
        {
            if (!cargarDetReg( sqlConFil()))
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
  
    private String sqlConFil()
    {
        String sqlFil="";
        
        //Tipo de documento
        strAux="";
        if(objParSis.getCodigoUsuario()==1){
            strAux+=" SELECT a1.co_tipdoc FROM tbr_tipdocprg AS a ";
            strAux+=" INNER JOIN tbm_cabtipdoc AS a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)" ;
            strAux+=" WHERE a.co_emp="+objParSis.getCodigoEmpresa();
            strAux+=" AND a.co_loc = " + objParSis.getCodigoLocal();
            strAux+=" AND a.co_mnu = "+objParSis.getCodigoMenu();
        }
        else {
            strAux+=" SELECT a1.co_tipdoc FROM tbr_tipDocUsr AS a ";
            strAux+=" INNER JOIN tbm_cabtipdoc AS a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)" ;
            strAux+=" WHERE a.co_emp="+objParSis.getCodigoEmpresa();
            strAux+=" AND a.co_loc = " + objParSis.getCodigoLocal();
            strAux+=" AND a.co_mnu = "+objParSis.getCodigoMenu();
            strAux+=" AND a.co_usr = "+objParSis.getCodigoUsuario();
        }        
        sqlFil+=" AND a.co_tipdoc IN ( "+strAux+" ) " ;
            
        //Bodega de Grupo
        sqlFil+=" AND ( a3.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a3.co_bodGrp=( "+txtCodBod.getText()+" ) ) ";           
        
        //Fecha
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //Búsqueda por rangos
                    sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                  break;
            }
        }

        //Cliente: Código
        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a.co_clides="+txtCodCli.getText();

        //Nombre del cliente
        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a.tx_nomclides lIKE '"+txtNomCli.getText()+"'";

        //Vendedor
        if(!txtCodVen.getText().equals(""))
            sqlFil+=" AND a.co_ven="+txtCodVen.getText();

        return sqlFil;
    }

    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            { 
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a4.tx_descor, a.co_doc, a.ne_numOrdDes as ne_numOrdDes, a.fe_doc, a.co_clides, a.tx_nomclides";
                strSQL+="      , a.st_tieguisec, a.tx_datdocoriguirem, a2.ne_numDoc as ne_numFac, a.st_aut, a.fe_aut, a.tx_obsaut, a.tx_obsimpaut";
                strSQL+=" FROM tbm_cabguirem AS a ";
                strSQL+=" INNER JOIN tbm_detGuiRem AS a1 ON a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a2 ON a1.co_empRel=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_TipDoc AND a1.co_docRel=a2.co_doc"; 
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a3.co_emp=a.co_emp AND a3.co_bod=a.co_ptopar) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) ";
                strSQL+=" WHERE a.st_reg NOT IN ('I','E') AND a.st_tipguirem='P' AND a.ne_numdoc=0 AND a.co_cliDes IS NOT NULL AND a2.ne_numDoc>0 /*Numero factura*/ ";
                strSQL+=" "+strFil+" ";  
                if(chkOrdDesAut.isSelected()) {
                    strSQL+=" AND a.st_aut='A'";
                }
                else{
                    strSQL+="  AND (a.st_aut='P' OR a.st_aut is null) AND (a.ne_numorddes is null OR a.ne_numorddes=0)";
                }
                strSQL+=" GROUP BY a3.co_bodGrp, a.co_emp, a.co_loc, a.co_tipdoc, a4.tx_descor, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides";
                strSQL+="        , a.st_tieguisec, a.tx_datdocoriguirem, a2.ne_numDoc, a.st_aut, a.fe_aut, a.tx_obsaut, a.tx_obsimpaut ";
                strSQL+=" ORDER BY a3.co_bodGrp, a2.ne_numDoc ";
             
                System.out.println("cargarDetReg: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LIN,"");
                        vecReg.add(INT_TBL_COD_EMP,     rst.getString("co_emp"));
                        vecReg.add(INT_TBL_COD_LOC,     rst.getString("co_loc"));
                        vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DES_TIP_DOC, rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_COD_DOC,     rst.getString("co_doc"));
                        vecReg.add(INT_TBL_NUM_ORD_DES, rst.getString("ne_numOrdDes"));  
                        vecReg.add(INT_TBL_NUM_FAC_VEN, rst.getString("ne_numFac"));  
                        vecReg.add(INT_TBL_REF_DOC_ORI, rst.getString("tx_datdocoriguirem") );
                        vecReg.add(INT_TBL_FEC_DOC,     rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_COD_CLI,     rst.getString("co_clides"));
                        vecReg.add(INT_TBL_NOM_CLI,     rst.getString("tx_nomclides"));

                        if( rst.getString("st_tieguisec").equals("S") )
                            vecReg.add(INT_TBL_CHK_AUT, true);
                        else 
                            vecReg.add(INT_TBL_CHK_AUT, false);

                        if(chkOrdDesAut.isSelected()) {
                            vecReg.add(INT_TBL_FEC_AUT, rst.getString("fe_aut"));
                            vecReg.add(INT_TBL_TXT_OBS_AUT, rst.getString("tx_obsaut"));
                            vecReg.add(INT_TBL_BUT_OBS_AUT, "...");
                            vecReg.add(INT_TBL_TXT_OBS_IMP, rst.getString("tx_obsimpaut"));
                            vecReg.add(INT_TBL_BUT_OBS_IMP, "...");                            
                        }
                        else{
                            vecReg.add(INT_TBL_FEC_AUT, "");
                            vecReg.add(INT_TBL_TXT_OBS_AUT, "");
                            vecReg.add(INT_TBL_BUT_OBS_AUT, "...");
                            vecReg.add(INT_TBL_TXT_OBS_IMP, "");
                            vecReg.add(INT_TBL_BUT_OBS_IMP, "...");                              
                        }
                        vecDat.add(vecReg);
                    }
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
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

    private boolean asignarNumOrdDes()
    {
        boolean blnRes=true;
        String strLin="";
        GenOD.ZafGenOdPryTra objGenOD=new ZafGenOdPryTra();
        GenOD.ZafGenOdDaoPryTra objGenODDao=new ZafGenOdDaoPryTra();
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {      
                con.setAutoCommit(false);            
                stm=con.createStatement();
                for(int i=0; i < objTblMod.getRowCountTrue(); i++)
                {
                    strLin=objTblMod.getValueAt(i, INT_TBL_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_LIN).toString();
                    if(strLin.equals("M"))
                    {
                        if(objTblMod.isChecked(i, INT_TBL_CHK_AUT))
                        { 
                            //Obtiene PK Factura
                            int intCodEmp=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_COD_EMP));
                            int intCodLoc=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_COD_LOC));
                            int intCodTipDoc=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_COD_TIP_DOC));
                            int intCodDoc= Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_COD_DOC));

                            //Armar sentencia SQL
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabguirem ";
                            strSQL+=" SET st_aut='A' ";
                            strSQL+="    ,tx_obsAut='MANUAL: "+objTblMod.getValueAt(i, INT_TBL_TXT_OBS_AUT).toString()+"'  ";
                            strSQL+="    ,tx_obsImpAut='"+objTblMod.getValueAt(i, INT_TBL_TXT_OBS_IMP).toString()+"' ";
                            strSQL+="    ,fe_aut="+objParSis.getFuncionFechaHoraBaseDatos()+" ";
                            strSQL+="    ,tx_comAut='"+objParSis.getNombreComputadoraConDirIP()+"' ";
                            strSQL+="    ,co_usrAut="+objParSis.getCodigoUsuario()+" ";
                            strSQL+=" WHERE co_emp="+intCodEmp;
                            strSQL+=" AND co_loc="+intCodLoc;
                            strSQL+=" AND co_tipdoc="+intCodTipDoc;
                            strSQL+=" AND co_doc="+intCodDoc;
                            strSQL+=" ; ";
                            System.out.println("asignarNumOrdDes: "+strSQL);
                            stm.executeUpdate(strSQL);

                            //<editor-fold defaultstate="collapsed" desc="/*Genera Orden Despacho*/">
                            if((objGenOD.validarODExsAut(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc))){
                                if(objGenOD.generarNumODxAut(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                    con.commit();
                                    String strIp=objGenOD.obtenerIpSerImp(con);
                                    objGenOD.imprimirOdLocalxAut(con, Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_EMP).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_LOC).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_TIP_DOC).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_DOC).toString()),strIp);
                                }
                                else{
                                    con.rollback();
                                }
                            }
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="/* Generar Terminales L*/">
                            int intNumDocFac=Integer.parseInt((String)objTblMod.getValueAt(i, INT_TBL_NUM_FAC_VEN));            
                            int intTipDocFac=objGenODDao.verificarRelODFac(con, intCodEmp, intCodLoc, Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_TIP_DOC).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_COD_DOC).toString()));
                            int intCodTipDocFac= intTipDocFac>1 ? intTipDocFac : 1;
                            int intCodDocFac=objGenODDao.obtenerCodDocFac(con, intCodEmp, intCodLoc, intNumDocFac, intCodTipDocFac);            
                            boolean booGenTer=objGenOD.generarTermL(con, intCodEmp, intCodLoc, intCodTipDocFac, intCodDocFac);
                            //</editor-fold>
                        }
                    }
                }
            }
            stm.close();
            stm=null;
            con.close();
            con=null;            
        }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }









    


}
