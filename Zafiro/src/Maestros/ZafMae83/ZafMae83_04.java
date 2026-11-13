package Maestros.ZafMae83;







/*
 * ZafMae83_04.java
 *
 * Created on 27 de marzo de 2006, 12:15
 */



import Librerias.ZafParSis.ZafParSis; 
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import java.awt.Color;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
/**
 *
 * @author  jayapata
 */
public class ZafMae83_04 extends javax.swing.JDialog {
private static final int INT_TBL_DAT_LIN=0;             //Línea
    private static final int INT_TBL_DAT_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_DAT_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_DAT_ORDEN=4;         //ORDENAMIENTO
    private static final int INT_TBL_DAT_FACTOR=5;         //FACTOR    
    private static final int INT_TBL_DAT_SELE=6;         //SELECCION     
    
    private static final int INT_TBL_CON_LIN=0;             //Línea
    private static final int INT_TBL_CON_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_CON_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_CON_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_CON_ORDEN=4;         //ORDENAMIENTO
    private static final int INT_TBL_CON_FACTOR=5;         //FACTOR
    private static final int INT_TBL_CON_SELE=6;         //SELECCION
    
    
      //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModItems;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    
    
     private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
     private ZafMouMotAdaItems objMouMotAdaItems;                  //ToolTipText en TableHeader.
   // private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatItems, vecCabItems, vecRegItems;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intCodEmp;                              //Código de la empresa.
    private int intCodLoc;                              //Código del local.
    private int intCodTipDoc;                           //Código del tipo de documento.
    private int intCodMod;                              //Código del Movimiento.
    private String CodAlt;                               // Codigo Alternativo de Item
    private int CodCli;
    private ZafVenConGP vcoItm, vcoTipDoc, vcoBod;
    private String strCodGru="-1";
    private char estado='C';
    private String strVersion="v0.06";
    private ZafPerUsr objPerUsr; 
////    private java.util.Date datFecDoc;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.    
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelRenLbl objTblCelRenLbl,objTblCelRenLblItems;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private ZafDocLis objDocLis;
    private boolean blnHayCam;    
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.  
    private ArrayList arlRegORDENAR,arlDatORDENAR;
    
////    private ZafVenConItmGP02 objVenConItm01,objVenConItm02;   
////        private ZafVenConItmGP03 objVenConItm03;   
    
    private ZafVenConItmGP01 objVenConItm01,objVenConItm02;       
    private ZafThreadGUI objThrGUI;
    
    private String strTipRegLin="";  
    private String estadoFor;
    private int tipoAgrupa =-1;
    private ArrayList<String> dataList = new ArrayList<>(); 
    private ArrayList<String> dataList1 = new ArrayList<>();     
    private String grupoGlobal ="";
    private int tipoGlobal =0,codigoGrupoGlobal =0;
    private boolean validaOk =false;    
    
    private ZafHisTblBasDatGP objHisTblBasDatGP;
    private ZafHisTblBasDat objHisTblBasDat;    
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strFecUltMod="";    
    private LocalDateTime fechaHora ;
    private String strSQLGlobal="";

    
    
    public ZafMae83_04(ZafParSis obj){
        
         try {
             
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(false);     
            butGuardar.setEnabled(false); 
          
            estadoFor="N";
            estado ='C';
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
          //  Logger.getLogger(ZafMae85_02.class.getName()).log(Level.SEVERE, null, ex);
        }
        
////        try{
////            initComponents();
////            //Inicializar objetos.
////            objParSis=(ZafParSis)obj.clone();
////        }
////        catch (CloneNotSupportedException e)
////        {
////            this.setTitle(this.getTitle() + " [ERROR]");
////        }
    }
    
    public ZafMae83_04(java.awt.Frame parent, boolean modal, ZafParSis obj, int tipo, String grupo, int codigoGrupo) {
        super(parent, modal);
        try {
            this.objParSis = (ZafParSis) obj.clone();
            grupoGlobal =grupo;
            tipoGlobal = tipo;
            codigoGrupoGlobal =codigoGrupo;
            initComponents();
            configurarFrm();
            estado ='M';
            butGuardar.setText("Modificar");
            
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(true);     
            butGuardar.setEnabled(true);             
            LLenarTabla();
            objTblMod.insertRow();  

                
            
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
           // Logger.getLogger(ZafMae85.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    } 
    public boolean acepta(){
      return validaOk;
    }
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            configurarTblDatItems();
            //Agregado por Eddye el 15/Sep/2022:
            butCons.setText("Detener");
            lblMsgSis1.setText("Obteniendo la información...");            
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis1.setText("Listo");
                pgrSis1.setValue(0);

                butCons.setText("Consultar");
                
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            objThrGUI =null;
            if (tblDatFor.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDatFor.setRowSelectionInterval(0, 0);
                tblDatFor.requestFocus();
            }
////                objThrGUI=null;            
            
        }
    } 

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
        
    private boolean obtener_maximo()
    {
        boolean blnRes=true;
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(co_grpInv) AS co_grp";
                strSQL+=" FROM tbm_cabGrpInv  ";
                strSQL+=" WHERE st_reg='A'";
                System.out.println("tbm_cabMovInv: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    intCodCfg=rstLoc1.getInt("co_grp")+1;
                }
                else{
                    blnRes=false;
                }
            rstLoc1.close();
            stmLoc1.close();
            conExt.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt=null;
            
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

    private void LLenarTabla()//chiqui2
    {
        if (ConsultaComboTiposAgrupacion(tipoGlobal))
        {
            if(cargarDetRegGrupo()){
                System.out.println("ok dos");
            }            
        }

    }

private boolean borrar_tbmDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_detGrpInv where co_grpInv ="+ tipo +"";
                System.out.println("borrar_tbmDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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

private boolean anular_tbmCabDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabGrpInv set st_REg ='I' where co_grpInv ="+ tipo +"";
                System.out.println("anular_tbmDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
    private ArrayList arlRegITEMSGRUPO,arlDatITEMSGRUPO,arlRegITEMZAF,arlDatITEMZAF;
    private static final int INT_ARL_CON_FAC=0;
    private static final int INT_ARL_CON_COD_ITM=1; 
    private static final int INT_ARL_CON_REG=2; 
      
    
private boolean insertar_tbmDetGrupo(java.sql.Connection conExt,int tipo,java.sql.Connection connZAF)//chiqui_antiguo
    {
        
        int contador=0;
        int ordenamiento=0;
        boolean blnRes=true;
        String codigo="";
////        java.sql.Statement stmZAF;
        String strZAF ="";   
        
        
        try{
            
            
            if (conExt!=null)
            {
                stm=conExt.createStatement();
////                stmZAF=connZAF.createStatement();                
////                objTblMod.removeEmptyRows();
////                ordenarDatPorOrden();
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                int codIngresoGrp=-1;
                strSQLGlobal="";
                arlDatITEMSGRUPO= new ArrayList();
                
                for (int i=0; i<intNumFil;i++)
                {
                    codigo =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                 
                    if  (codigo !="Null")
                    {
                        nd_orden =i+1;
                                            //Armar la sentencia SQL.
////                        contador = contador +1;
////                        ordenamiento =  Integer.valueOf(objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN).toString()) ;
////                        if (contador != ordenamiento)
////                        {
////                            blnRes=false;
////                            mensajeError ="El ordenamienro de la tabla se encuentra sin secuencia";
////                            break;
////                        }
                        strSQL="";
                        strSQL+="\n INSERT INTO tbm_detGrpInv(";
                        strSQL+="        co_grpInv,co_reg,tx_codItm,st_mosItmLisPre,co_facModPre,fe_ing,fe_ultmod,co_usring,co_usrmod)";
                        strSQL+=" VALUES (";
                        if (tipo==0)
                            codIngresoGrp =intCodCfg;
////                            strSQL+=" " + intCodCfg + ""; //
                        else
                            codIngresoGrp =codigoGrupoGlobal;
////                            strSQL+=" " + codigoGrupoGlobal + "";
////                        strSQL+=", "+ objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN) +""; //
                        strSQL+=" " + codIngresoGrp + ""; //
                        strSQL+=", "+ nd_orden +""; //
////                        if (nd_orden ==1)
////                            strZAF+="\n UPDATE tbm_inv set co_grpinv = "+codIngresoGrp +" ";
////                        else
////                            strZAF+="\n UPDATE tbm_inv set co_detgrpinv = "+codIngresoGrp +" ";
////                        
////                        strZAF+="WHERE tx_codaltdyn = '"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"' ;";
                        
                        strSQL+=", '" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) + "'"; //co_tipdoc  
                        if (nd_orden==1)
                            strSQL+=", 'S'";
                        else 
                        { 
                            if ((objTblMod.getValueAt(i,INT_TBL_DAT_SELE) ==null) || (objTblMod.getValueAt(i,INT_TBL_DAT_SELE).equals("")))
                                strSQL+=", 'N'";
                            else
                                strSQL+=", 'N'";
                        }
                        if (nd_orden==1)
                            strSQL+=", 0"; //                          
                        else
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) + ""; //                          
                        
                        strSQL+=", '"+ strFecUltMod +"'";                            
                        strSQL+=", '"+ strFecUltMod +"'"; 
                        strSQL+=", "+ objParSis.getCodigoUsuario() +"" ;
                        strSQL+=", "+ objParSis.getCodigoUsuario() +"" ;
                        
                        strSQL+=");\n";
////                        if (nd_orden==1)
////                            strSQL+="\n UPDATE Dynamics2021..tbm_inv set  co_grpinv ="+ objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +", co_detgrpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod '"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'";
////                        else
////                            strSQL+="\n UPDATE Dynamics2021..tbm_inv set  co_detgrpinv ="+ objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +" , co_grpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod '"+ strFecUltMod + "'  where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'";
////                        
                        if (tipoAgrupa ==1)
                        {
                            if (nd_orden ==1)
                            {

    ////                            strZAF+="\n UPDATE tbm_inv set co_grpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +" , co_detgrpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'";                        
                                strSQL+="\n UPDATE tbm_inv set co_grpinv = "+codIngresoGrp +" , co_detgrpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"';";                        
                            }
                            else
                            {
    ////                            strZAF+="\n UPDATE tbm_inv set co_detgrpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +", co_grpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"' ";
                                strSQL+="\n UPDATE tbm_inv set co_detgrpinv = "+codIngresoGrp +", co_grpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"';";
                            }                            
                        }
                        else
                        {
                            if (nd_orden ==1)
                            {

    ////                            strZAF+="\n UPDATE tbm_inv set co_grpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +" , co_detgrpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'";                        
                                strSQL+="\n UPDATE tbm_inv set co_grpcom = "+codIngresoGrp +" , co_detgrpcom =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"';";                        
                            }
                            else
                            {
    ////                            strZAF+="\n UPDATE tbm_inv set co_detgrpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +", co_grpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"' ";
                                strSQL+="\n UPDATE tbm_inv set co_detgrpcom = "+codIngresoGrp +", co_grpcom =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"';";
                            }                             
                        }    
                            
                            
                       
                        
                        
                        
                            

                            
////                        
////                        strZAF+="WHERE tx_codaltdyn = '"+objTblMod.getValueAt(i,INT_TBL_DAT_ITEM) +"' ;";
////                        strSQL+="WHERE tx_itm = '"+objTblMod.getValueAt(i,INT_TBL_DAT_ITEM) +"' ;";

                        strSQLGlobal += strSQL;
                        arlRegITEMSGRUPO = new ArrayList();
                        arlRegITEMSGRUPO.add(INT_ARL_CON_FAC,codIngresoGrp);
                        arlRegITEMSGRUPO.add(INT_ARL_CON_COD_ITM,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                        arlRegITEMSGRUPO.add(INT_ARL_CON_REG,nd_orden); 
                        arlDatITEMSGRUPO.add(arlRegITEMSGRUPO);

                    }
                }
                if (blnRes ==true)
                {
                    
                       if(validaItemsZafiroGP(connZAF,codIngresoGrp,arlDatITEMSGRUPO))
                        {
                            blnRes =true;
                            System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                            stm.executeUpdate(strSQLGlobal);
                        }   
                        else
                        {
                            blnRes=false;
                        }
                }

                stm.close();
                stm=null;
            }
            
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


    private boolean validaItemsZafiroGP(java.sql.Connection conZAF,int codIngresoGrp,ArrayList arlRegITEM)
    {
        java.sql.Statement stmLocZAF1;
        java.sql.ResultSet rstLocZAF1;    

        String strZAF="",strZAF1="";
        boolean validaIngreso=false,blnRes=false;
        double preVtaZa=0, preResul=0;
        int i=0,indicador =0;
        String codigoGP="";
        try
        {
            if(conZAF!=null)
            {
                strZAF1="";
                stmLocZAF1=conZAF.createStatement();
                strZAF="";
                strZAF+=" SELECT  distinct(trim(tx_codaltdyn)) as item \n";
                strZAF+="  FROM    tbm_inv \n";
                if (tipoAgrupa ==1)
                    strZAF+=" where  co_grpinv ="+ codIngresoGrp +" or co_detgrpinv ="+ codIngresoGrp +"  \n";  
                else
                    strZAF+=" where  co_grpcom ="+ codIngresoGrp +" or co_detgrpcom ="+ codIngresoGrp +"  \n";  

                rstLocZAF1 = stmLocZAF1.executeQuery(strZAF);

                  while(rstLocZAF1.next())
                  {
                    indicador =0;
                    codigoGP = rstLocZAF1.getString("item");
////                    codigoGP = rstLocZAF1.getString("item");
////                    strZAF1="";
                    for (i=0; i<arlRegITEM.size();i++) 
                    {
                        if(codigoGP.equals(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM)))
                        {
                            indicador++;
                            if (Integer.parseInt(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_REG))==1)
                            {
                                strZAF1+=" \n UPDATE tbm_inv";
                                if (tipoAgrupa ==1)
                                {
                                    strZAF1+=" \n  SET co_grpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_detgrpinv= 0";
                                }
                                else
                                {
                                    strZAF1+=" \n  SET co_grpcom= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_detgrpcom= 0";
                                }
                                    
                                
                                strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                                strZAF1+=" ;\n ";                                
                            }
                            else
                            {
                                
                               
                                strZAF1+=" \n UPDATE tbm_inv";
                                if (tipoAgrupa ==1)
                                {
                                    strZAF1+=" \n  SET co_grpinv= 0";
                                    strZAF1+=" \n  , co_detgrpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                }
                                else
                                {
                                    strZAF1+=" \n  SET co_grpcom= 0";
                                    strZAF1+=" \n  , co_detgrpcom= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                }
                                
                                
                                
                                strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                                strZAF1+=" ;\n ";                                                                
                            }   
                            break;
                        }
                    }
                    if (indicador==0)
                    {
                        strZAF1+=" \n UPDATE tbm_inv";
                        
                        if (tipoAgrupa ==1)
                        {
                            strZAF1+=" \n  SET co_grpinv= 0";
                            strZAF1+=" \n  , co_detgrpinv= 0";
                        }
                        else
                        {
                            strZAF1+=" \n  SET co_grpcom= 0";
                            strZAF1+=" \n  , co_detgrpcom= 0";
                        }

                        strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                        strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";     
                        strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                        strZAF1+=" ;\n ";
////                        stmLocZAF.executeUpdate(strZAF1);
                        strSQLGlobal+=" \n UPDATE Dynamics2021..tbm_inv";
                        
                        if (tipoAgrupa ==1)
                        {
                        strSQLGlobal+=" \n  SET co_grpinv= 0";
                        strSQLGlobal+=" \n  , co_detgrpinv= 0";
                        }
                        else
                        {
                        strSQLGlobal+=" \n  SET co_grpcom= 0";
                        strSQLGlobal+=" \n  , co_detgrpcom= 0";
                        }

                        
////                        strSQLGlobal+=" \n  SET co_grpinv= 0";
////                        strSQLGlobal+=" \n  , co_detgrpinv= 0";
                        strSQLGlobal+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario()+"'";
                        strSQLGlobal+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";     
                        strSQLGlobal+=" \n  WHERE tx_itm ='"+ codigoGP +"'";
                        strSQLGlobal+=" ;\n "; 
                    }
                  }
                  if (!strZAF1.equals(""))
                   {
                       stmLocZAF1.executeUpdate(strZAF1);
                       blnRes=true;
                   }   
                      
                    
                  stmLocZAF1.close();
                  stmLocZAF1=null;
                  rstLocZAF1.close();
                  rstLocZAF1=null;
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
           blnRes=false;
         }
        return blnRes;
    }   
 

////    private boolean validaItemsZafiroGP(java.sql.Connection conZAF,int codIngresoGrp,ArrayList arlRegITEM)
////    {
////        java.sql.Statement stmLocZAF1;
////        java.sql.ResultSet rstLocZAF1;    
////
////        String strZAF="",strZAF1="";
////        boolean validaIngreso=false,blnRes=false;
////        double preVtaZa=0, preResul=0;
////        int i=0,indicador =0;
////        String codigoGP="";
////        try
////        {
////            if(conZAF!=null)
////            {
////                strZAF1="";
////                stmLocZAF1=conZAF.createStatement();
////                strZAF="";
////                strZAF+=" SELECT  distinct(trim(tx_codaltdyn)) as item \n";
////                strZAF+="  FROM    tbm_inv \n";
////                strZAF+=" where  co_grpinv ="+ codIngresoGrp +" or co_detgrpinv ="+ codIngresoGrp +"  \n";  
////
////                rstLocZAF1 = stmLocZAF1.executeQuery(strZAF);
////
////                  while(rstLocZAF1.next())
////                  {
////                    indicador =0;
////                    codigoGP = rstLocZAF1.getString("item");
////////                    strZAF1="";
////                    for (i=0; i<arlRegITEM.size();i++) 
////                    {
////                        if(codigoGP.equals(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM)))
////                            indicador++;
////                    }
////                    if (indicador==0)
////                    {
////                        strZAF1+=" \n UPDATE tbm_inv";
////                        strZAF1+=" \n  SET co_grpinv= 0";
////                        strZAF1+=" \n  , co_detgrpinv= 0";
////                        strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
////                        strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
////                        strZAF1+=" ;\n ";
////////                        stmLocZAF.executeUpdate(strZAF1);
////                    }
////                  }
////                  if (!strZAF1.equals(""))
////                   {
////                       stmLocZAF1.executeUpdate(strZAF1);
////                       blnRes=true;
////                   }   
////                      
////                    
////                  stmLocZAF1.close();
////                  stmLocZAF1=null;
////                  rstLocZAF1.close();
////                  rstLocZAF1=null;
////            }
////        }
////        catch(java.sql.SQLException e){ 
////            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
////            blnRes=false;
////         }
////         catch (Exception e) {
////            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
////           blnRes=false;
////         }
////        return blnRes;
////    }   
    
////    private boolean desasociarGrupo()

    private boolean insertar_tbmCabGrupo(java.sql.Connection conExt, int tipo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="",itemPad="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
                for (int i=0; i<intNumFil;i++)
                {
                    if (i==0)
                    {
                        cadenaCabecera =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)+ "-"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); 
                        itemPad = objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE)); 
                        break;
                    }
                    
                }
            //modificar
                strSQL="";
                strSQL+="INSERT INTO tbm_cabGrpInv(";
                strSQL+="        co_grpInv,co_tipAgr,tx_nomGrpInv,tx_deslar,st_reg,fe_ing,co_usrIng,tx_itmPad)";
                strSQL+=" VALUES (";
                if (tipo==0)
                    strSQL+=" " + intCodCfg + ""; //
                else
                    strSQL+=" " + codigoGrupoGlobal + "";
                strSQL+=", " + tipoAgrupa + ""; //                
                strSQL+=", " + cadenaCabecera + ""; //
                strSQL+=", " + cadenaCabecera+ ""; //     
                strSQL+=", 'A'";//ne_secEmp
                strSQL+=", '"+ strFecUltMod +"'";//
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//
                strSQL+=", "+ itemPad +"";//
                strSQL+=");";
                System.out.println("tbm_cabGrpInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
private boolean modificar_tbmCabGrupo(java.sql.Connection conExt,int codigoGrupo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                String tx_itm ="";
                
                for (int i=0; i<intNumFil;i++)
                {
                    if (i==0)
                    {
                        cadenaCabecera =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)+ "-"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM));   
                        tx_itm= objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                        break;
                    }
                    
                }
            //modificar
                strSQL="";
                strSQL+="UPDATE  tbm_cabGrpInv set tx_nomGrpInv =" + cadenaCabecera + " , tx_deslar = " + cadenaCabecera + " , co_usrmod = " + objParSis.getCodigoUsuario() + " ,fe_ultmod = '"+strFecUltMod +"'\n" ;
                strSQL+="        ,tx_itmPad = "+tx_itm +"";                
                strSQL+="  WHERE      co_grpInv = "+codigoGrupo +";";
                System.out.println("modificar_tbmCabGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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
private boolean anular_tbmCabGrupo(java.sql.Connection conExt,int codigoGrupo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
                strSQL="";
                strSQL+="UPDATE  tbm_cabGrpInv set st_Reg ='I' " ;
                strSQL+="  WHERE      co_grpInv = "+codigoGrupo +";";
                System.out.println("modificar_tbmCabGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
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

    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
   private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
   private boolean cargarDetRegGrupo()//chiqui
    {
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
//            if (!txtDescGru.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT rtrim(det.tx_codItm) as cod_itm , RTRIM(iv.ITMGEDSC) as tx_codalt2 , RTRIM(iv.ITEMDESC) as item,det.co_reg,isnull(det.co_facModPre,0) as codFac";
                    strSQL+="  , det.st_mosItmLisPre as estadoFac";                    
                    strSQL+=" FROM tbm_detGrpInv as det ";
                    strSQL+="  inner join tbm_cabGrpInv as cab on (cab.co_grpInv = det.co_grpInv) \n ";
                    strSQL+="  left join TUVAL..IV00101 as iv on (iv.ITEMNMBR =det.tx_codItm) \n ";
                    strSQL+="  WHERE cab.st_reg ='A' and cab.co_grpInv = "+ codigoGrupoGlobal +" and cab.co_tipAgr ="+ tipoGlobal +" \n";
                    strSQL+="  ORDER BY det.co_reg asc";
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    rst.getString("cod_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codalt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,    rst.getString("item"));
                        vecReg.add(INT_TBL_DAT_ORDEN,    "");
                        vecReg.add(INT_TBL_DAT_FACTOR,   rst.getInt("codFac"));///12032024
                        vecReg.add(INT_TBL_DAT_SELE,   rst.getString("estadoFac"));///12032024
                         
                        vecDat.add(vecReg);
                    }
////////////                        vecReg=new Vector();
////////////                        vecReg.add(INT_TBL_DAT_LIN,"");
////////////                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,   "");
////////////                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,   "");
////////////                        vecReg.add(INT_TBL_DAT_BUT_ITM,"");
////////////                        vecReg.add(INT_TBL_DAT_NOM_ITM,  "");
////////////                        vecReg.add(INT_TBL_DAT_ORDEN,  "");
////////////                        vecDat.add(vecReg);
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDatGrupo.setModel(objTblMod);
                    vecDat.clear();
                    
                }
                else{
                    blnRes=false;
                    System.out.println("J1.. ");
                }
//            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private boolean cargarDetReg()//chiqui
{
    boolean blnRes=true;
    BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
    try{
        
            pgrSis1.setIndeterminate(true);
            butCons.setText("Detener");
            lblMsgSis1.setText("Obteniendo datos...");
            
            if (tipoAgrupa >=0)
            {
               objTblModItems.removeAllRows();
//            if (!txtDescGru.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
////                    strSQL="";

////                    strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
////                    strSQL+=" FROM TUVAL..IV00101 as iv01 ";
////////                    strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";     
////                    strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
////                    strSQL+=" FROM TUVAL..IV00101 as iv01 ";
////                    strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";   
////            strSQL="";
////            strSQL+="SELECT * FROM (SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, ";
////            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado";
////            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 ";
////            strSQL+="       left join(";
////            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm ";
////            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01"; 
////            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)";
////            strSQL+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)";            
////            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 AND cabG.st_reg ='A'  and cabg.co_tipAgr = "+ tipoAgrupa +" ";
////            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)";
////            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 "; 
            
////////            strSQL="";
////////            strSQL="SELECT * FROM (\n";            
////////            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as ITEMNMBR , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, \n";
////////            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado,iv01.INACTIVE,ica.codlinea,ica.codclase,ica.linea, ica.clase\n";
////////            strSQL+="       ,isnull(det1.co_facModPre,0) as codFac,det1.st_mosItmLisPre as estadoFac ,IV01.ITMGEDSC\n";            
////////            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 \n";
////////            strSQL+="       left join(\n";
////////            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm \n";
////////            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01\n"; 
////////            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)\n";
////////            strSQL+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)\n";            
////////            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 AND cabG.st_reg ='A'  and cabg.co_tipAgr = "+ tipoAgrupa +" \n";
////////            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)\n";
////////            strSQL+="       left join view_ItemsClasiAtrib as ica on (iv01.ITEMNMBR =ica.CodItm)\n";   
////////            strSQL+="       left join Dynamics2021..tbm_detGrpInv as det1 on (iv01.ITEMNMBR = det1.tx_codItm)\n";               
////////            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')    ) as a1\n";             
   

            strSQL="";
            strSQL="SELECT * FROM (\n";            
            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as ITEMNMBR , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, \n";
            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado,iv01.INACTIVE,ica.codlinea,ica.codclase,ica.linea, ica.clase\n";
            strSQL+="       ,isnull(detT.codFac,0) as codFac,detT.estadoFac as estadoFac  \n";            
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 \n";
            strSQL+="       left join(\n"; 
            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm,co_tipAgr as tipo \n";
            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01\n"; 
            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)\n";
            strSQL+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)\n";            
            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 AND cabG.st_reg ='A'  and cabg.co_tipAgr = "+ tipoAgrupa +" \n";
            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR and tot1.tipo ="+ tipoAgrupa +" )\n";
            
            strSQL+="       left join(\n";
            strSQL+="                  SELECT trim(detg.tx_codItm) as itm,isnull(detG.co_facModPre,0) as codFac,isnull(detG.st_mosItmLisPre,'') as estadoFac  \n";
            strSQL+="                  FROM Dynamics2021..tbm_detGrpInv as detG\n"; 
            strSQL+="                    INNER JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)\n";
            strSQL+="                   WHERE cabG.st_reg ='A' and cabg.co_tipAgr = "+ tipoAgrupa +"  \n";
            strSQL+="                 ) as detT on (detT.itm =iv01.ITEMNMBR ) \n";            
            
            
            strSQL+="       left join view_ItemsClasiAtrib as ica on (iv01.ITEMNMBR =ica.CodItm)\n";   
            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')    ) as a1\n";            




////                    if (!optTod.isSelected())
////                    {
////                        if (objVenConItm03.isCondicionAplicada())
////                            strSQL+=   " WHERE " + objVenConItm03.getCondicionesSQL();
////                    }      

                    if (!optTod.isSelected())
                    {
                        if (objVenConItm01.isCondicionAplicada())
                            strSQL+=   " WHERE a1.INACTIVE =0  " + objVenConItm01.getCondicionesSQL();
                    } 

                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatItems.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegItems=new Vector();
                        vecRegItems.add(INT_TBL_CON_LIN,"");
                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE,    rst.getString("ITEMNMBR"));
                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM,    rst.getString("tx_codalt2"));
                        vecRegItems.add(INT_TBL_CON_NOM_ITM,    rst.getString("item"));
                        vecRegItems.add(INT_TBL_CON_ORDEN, rst.getString("estado"));
                        vecRegItems.add(INT_TBL_CON_FACTOR, rst.getInt("codFac"));                        
                        vecRegItems.add(INT_TBL_CON_SELE, rst.getString("estadoFac"));                        
                        vecDatItems.add(vecRegItems);
                    }

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModItems.setData(vecDatItems);
                    tblDatFor.setModel(objTblModItems);
                    vecDatItems.clear();
                if (blnCon)
                    lblMsgSis1.setText("Se encontraron " + tblDatFor.getRowCount() + " registros.");
                else
                    lblMsgSis1.setText("Interrupción del usuario. Sólo se procesaron " + tblDatFor.getRowCount() + " registros.");
                butCons.setText("Consultar");
                pgrSis1.setIndeterminate(false);
                    
                }
                else{
                    blnRes=false;
                    System.out.println("J1.. ");
                }
            }
            else
               mostrarMsgInf("Se debe realizar la seleccion de un tipo de agrupación."); 

//            }
    }
    catch(java.sql.SQLException e){
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch(Exception e){
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}
private boolean configurarVenConItm()//chiqui2
{
    boolean blnRes=true;
    int intTipCnvIngEgr=0;
    try
    {
        //Listado de campos.
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_itmMae");
        arlCam.add("tx_codAlt2");
        arlCam.add("tx_nomItm");
        //Alias de los campos.
        ArrayList arlAli=new ArrayList();
        arlAli.add("Item");
        arlAli.add("3L");
        arlAli.add("Nombre"); 
        //Ancho de las columnas.
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("80");
        arlAncCol.add("80");
        arlAncCol.add("200");


////            strSQL="";
////            strSQL+="       SELECT rtrim(ITEMNMBR) AS co_itmMae,RTRIM(ITMGEDSC) as tx_codAlt2,  RTRIM (ITEMDESC) AS tx_nomItm  ";
////            strSQL+="       FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  ";
        strSQL="";
                strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
                strSQL+=" FROM \"+ objParSis.getNombreBaseDatosGP()  +\"..IV00101 as iv01 ";
                strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";   
////            strSQL="";
////            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, ";
////            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado";
////            strSQL+="FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 ";
////            strSQL+="       left join(";
////            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm ";
////            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01"; 
////            strSQL="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)";
////            strSQL="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";
////            strSQL="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)";
////            strSQL="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0";            

        ////strSQL+="       ORDER BY b2.co_grp, b1.tx_codAlt";

        System.out.println("vcoItm: "+strSQL);

        //Ocultar Columnas
////            int intColOcu[]=new int[6];
////            intColOcu[0]=1;  // co_itmMae
////            intColOcu[1]=2;  // co_itmGrp
////            intColOcu[2]=3;  // co_itmEmp
//////            intColOcu[6]=14;  // nd_cosTotEgr

        vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

        vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
        vcoItm.setCampoBusqueda(2);
    }
    catch (Exception e)
    {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

 private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            objVenConItm01=new ZafVenConItmGP01(objParSis, "a1.", optFil);
            panFiltros.add(objVenConItm01);
            objVenConItm01.setBounds(10, 98, 500, 20);
            objVenConItm01.setEnabled(true);
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            objHisTblBasDat=new ZafHisTblBasDat();            
            


////////            objVenConItm02=new ZafVenConItmGP02(objParSis, "iv01.", optFil);
////////            panFiltros.add(objVenConItm02);
////////            objVenConItm02.setBounds(10, 98, 500, 20);
////////            objVenConItm02.setEnabled(true);
////
////            objVenConItm03=new ZafVenConItmGP03(objParSis, "a1.", optFil);
////            panFiltros.add(objVenConItm03);
////            objVenConItm03.setBounds(10, 98, 500, 20);
////            objVenConItm03.setEnabled(true);
////            
            configurarVenConItm();
            configurarTblDat();
            configurarTblDatItems();
            
            LLenarComboTiposAgrupacion();
////            datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());

////            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
 private boolean configurarTblDat()//actual
    {
        boolean blnRes=true;
		
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Código");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "3L");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_ORDEN, "Orden");
            vecCab.add(INT_TBL_DAT_FACTOR, "Factor");
            vecCab.add(INT_TBL_DAT_SELE, "Seleccion");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
	    //Configurar JTable: Establecer el modelo de la tabla.
            tblDatGrupo.setModel(objTblMod);            
             //Configurar JTable: Establecer tipo de selección.
            tblDatGrupo.setRowSelectionAllowed(true);
            tblDatGrupo.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatGrupo);            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatGrupo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatGrupo.getColumnModel();
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(400); 
            tcmAux.getColumn(INT_TBL_DAT_ORDEN).setPreferredWidth(30);   
            tcmAux.getColumn(INT_TBL_DAT_FACTOR).setPreferredWidth(70); 
            tcmAux.getColumn(INT_TBL_DAT_SELE).setPreferredWidth(30);
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatGrupo);   
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatGrupo.getTableHeader().setReorderingAllowed(false);          
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatGrupo);   
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatGrupo);   
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab); 
            //Habilitar Opción de Pegar.
            objTblPopMnu.setPegarEnabled(true);   
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN, tblDatGrupo); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FACTOR, tblDatGrupo); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SELE, tblDatGrupo); 
            lblMsgInf.setVisible(false);
////            lblMsgSis.setVisible(false);
            objMouMotAda=new ZafMouMotAda();
            tblDatGrupo.getTableHeader().addMouseMotionListener(objMouMotAda); 
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN, tblDatGrupo);                
            objTblMod.insertRow(); 
            
            
            
//////            
//////////            //Configurar JTable: Establecer el menú de contexto. ES PARA LAS OPCIONES CON CLICK DERECHO DEL MOUSE
//////            objTblPopMnu=new ZafTblPopMnu(tblDatGrupo);
//////            objTblPopMnu.setBorrarContenidoEnabled(true);
//////            objTblPopMnu.setPegarEnabled(true);
//////            objTblPopMnu.setInsertarFilaEnabled(true);
//////            objTblPopMnu.setInsertarFilasEnabled(false);
//////            objTblPopMnu.setEliminarFilaEnabled(true);
//////            objTblPopMnu.setEliminarFilaVisible(true);  
//////	    //Configurar JTable: Establecer tipo de selección.
//////            tblDatGrupo.setRowSelectionAllowed(true);
//////            tblDatGrupo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
//////			//Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//////            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
//////            objTblMod.setColumnDataType(INT_TBL_DAT_COD_ITM_MAE, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
////            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_LIN);
////            vecAux.add("" + INT_TBL_DAT_COD_ITM_MAE);
////            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
////            vecAux.add("" + INT_TBL_DAT_NOM_ITM);
////            vecAux.add("" + INT_TBL_DAT_ORDEN);
////            objTblMod.setColumnasEditables(vecAux);
////            vecAux=null;

            
//<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
         
            tblDatGrupo.setDragEnabled(true);
            tblDatGrupo.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatFor.setDropTarget(new DropTarget()
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovFor[];
                    intFilMovFor = tblDatFor.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatGrupo.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowEmpOrigen = tblDatGrupo.getSelectedRow();
                    //System.out.println("intRowEmpOrigen: " + intRowEmpOrigen);
                    
                    int intRowForPtoDestino = tblDatFor.rowAtPoint(point);
                    //System.out.println("intRowForPtoDestino: " + intRowForPtoDestino);
                    
                    int intRowForDestino = tblDatFor.getSelectedRow();
                    //System.out.println("intRowForDestino: " + intRowForDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;
                    boolean blnValCtaExiFor=false;
                    
                    if (intRowEmpOrigen > -1 && intRowForDestino == -1 && intRowForPtoDestino>0) //TRASLADAR CUENTAS A OTRO JTABLE.
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
////                        for(int j=0 ; j<intFilSel.length;j++)
////                        {
////                            if (intFilSel[j] > -1 && intRowForDestino == -1) 
////                            {
////                                intTipVal=2;
////                                strIsCtaDet = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_EST_DET).toString();
////                                strCodCtaPlaEmp = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_COD_CTA).toString();
////                                strTipRegLin = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_LIN).toString();
////
////                                if (strIsCtaDet.equals("true"))
////                                {
////                                    if (validaCuentaDetalle(strCodCtaPlaEmp, strTipRegLin, intTipVal)) 
////                                    {
////                                        System.out.println("DUMMY CODE!");
////                                    }
////                                    else 
////                                    {
////                                        blnAdd=false;
////                                        blnValCtaExiFor=true;
////                                    }
////                                }
////                                else 
////                                {
////                                    blnAdd=false;
////                                    blnValCtaDet=true;
////                                }
////                            }
////                        }
////
////                        if (blnValCtaDet) {
////                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
////                            limpiarJtableSelection();
////                        }
////                        if (blnValCtaExiFor) {
////                            mostrarMsgInf("Alguna(s) de las cuenta(s) ya existe(n) en el Plan Cuenta Formato.");
////                            limpiarJtableSelection();
////                        }
                        
                        //</editor-fold>
                    
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        if(blnAdd)
                        {
                            //AGREGAR ELEMENTOS A TBLDATFOR
                            for (int j = 0; j < intFilSel.length; j++) 
                            {
                                if (intFilSel[j] > -1 && intRowForDestino == -1) // TENGA VALORES 
                                {
                                    strTipRegLin = objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_LIN).toString();

                                    vecReg = new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN, strTipRegLin.equals("E") ? "C" : "N");
                                    vecReg.add(INT_TBL_CON_COD_ITM_MAE, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ITM_MAE));
                                    vecReg.add(INT_TBL_CON_COD_ALT_ITM, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ALT_ITM));
                                    vecReg.add(INT_TBL_CON_NOM_ITM, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_NOM_ITM));
                                    vecReg.add(INT_TBL_CON_ORDEN, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_ORDEN));
                                    vecReg.add(INT_TBL_CON_FACTOR, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_FACTOR));
                                    vecReg.add(INT_TBL_CON_SELE, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_SELE));
                                    boolean blnBoo = false;
                                    for (int i = 0; i < objTblModItems.getRowCount(); i++)
                                    {
                                        if (i == intRowForPtoDestino && !blnBoo) 
                                        {
                                            vecDatItems.add(vecReg);
                                            i--;
                                            blnBoo = true;
                                        }
                                        else 
                                        {
                                            vecDatItems.add(objTblModItems.getData().get(i));
                                        }
                                    }
                                    objTblModItems.setData(vecDatItems);
                                    tblDatFor.setModel(objTblModItems);
                                    vecDatItems.clear();
                                    intRowForPtoDestino++;
                                }
                            }
                    
                            //ELIMINAR REGISTROS TBLDATEMP
                            boolean blnExi = false;

                            for (int i = 0; i < objTblMod.getRowCount(); i++)
                            {
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (i == intFilSel[j]) 
                                    {
                                        blnExi = true;
                                    }
                                }
                                if (!blnExi) 
                                {
                                    vecDat.add(objTblMod.getData().get(i));
                                }
                                blnExi = false;

                            }
                            objTblMod.setData(vecDat);
                            tblDatGrupo.setModel(objTblMod);
                            vecDat.clear();
                        }
                    //</editor-fold>
                    
                    }
                    else if (intRowForDestino > -1 && intRowEmpOrigen > -1 && intRowForPtoDestino > -1) 
                    {
                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////                        limpiarJtableSelection();
                    }
                    else if ( intRowForPtoDestino <=0 ) 
                    {
                        mostrarMsgInf("No se pueden ubicar cuentas al inicio de la tabla.");
////                        limpiarJtableSelection();
                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovFor.length; i++) 
                        {
                            if (intFilMovFor[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-FOR!!");
                            //System.out.println("intFilMovFor: " + intFilMovFor + " intRowForPtoDestino: " + intRowForPtoDestino);
                            //objTblModFor.moveRow(intRowForDestino, intRowForPtoDestino);
                            objTblModItems.moveRows(intFilMovFor, intRowForPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
////                            limpiarJtableSelection();
                        }
                                
                    }
                }
            });
            //</editor-fold>                 



            
            
            
            
            

            
            
//////            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
//////            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//////            objTblOrd=new ZafTblOrd(tblDatGrupo);
//////
//////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//////																					   
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLbl);
//////            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////								
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////			 
//////                }
//////            });
//////
//////////            //Configurar JTable: Editor de celdas.
//////////            int intColVen[]=new int[2];
//////////            intColVen[0]=1;
//////////            intColVen[1]=2;
//////////            int intColTbl[]=new int[2];
//////////            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
//////
//////            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//////            objTblOrd=new ZafTblOrd(tblDatGrupo);
//////            //Configurar JTable: Establecer que el JTable sea editable.
//////////            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
//////
//////            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
//////            objDocLis=new ZafDocLis();
//////            
//////            // Configurar JTable: Detectar cambios de valores en las celdas.
//////            objTblModLis=new ZafTblModLis();
//////            objTblMod.addTableModelListener(objTblModLis);  
//////          
//////			
//////            //Libero los objetos auxiliares.
//////            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private boolean configurarTblDatItems()//actual
    {
        boolean blnRes=true;
		
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatItems=new Vector();    //Almacena los datos
            vecCabItems=new Vector();  //Almacena las cabeceras
            vecCabItems.clear();
            vecCabItems.add(INT_TBL_CON_LIN,"");
            vecCabItems.add(INT_TBL_CON_COD_ITM_MAE, "Código");
            vecCabItems.add(INT_TBL_CON_COD_ALT_ITM, "3L");
            vecCabItems.add(INT_TBL_CON_NOM_ITM, "Nombre");
            vecCabItems.add(INT_TBL_CON_ORDEN, "Orden");
            vecCabItems.add(INT_TBL_CON_FACTOR, "Factor");
            vecCabItems.add(INT_TBL_CON_SELE, "Seleccion");
            
            objTblModItems=new ZafTblMod();
            objTblModItems.setHeader(vecCabItems);
	    //Configurar JTable: Establecer el modelo de la tabla.
            tblDatFor.setModel(objTblModItems);
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatFor);            
	    //Configurar JTable: Establecer tipo de selección.
            tblDatFor.setRowSelectionAllowed(true);
            tblDatFor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
			//Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModItems.setColumnDataType(INT_TBL_CON_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItems.setColumnDataType(INT_TBL_CON_COD_ITM_MAE, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));

            
			
////            //Configurar JTable: Establecer el menú de contexto. ES PARA LAS OPCIONES CON CLICK DERECHO DEL MOUSE
            objTblPopMnu=new ZafTblPopMnu(tblDatFor);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(true);
            objTblPopMnu.setInsertarFilasEnabled(false);
            objTblPopMnu.setEliminarFilaEnabled(true);
            objTblPopMnu.setEliminarFilaVisible(true);  
            
            
                      

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFor.getColumnModel();
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CON_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CON_COD_ALT_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CON_NOM_ITM).setPreferredWidth(400);
            tcmAux.getColumn(INT_TBL_CON_ORDEN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_CON_FACTOR).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_CON_SELE).setPreferredWidth(50);
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFor.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFor);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatFor);
            tcmAux.getColumn(INT_TBL_CON_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_CON_LIN);
            vecAux.add("" + INT_TBL_CON_COD_ITM_MAE);
            vecAux.add("" + INT_TBL_CON_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_CON_NOM_ITM);
            vecAux.add("" + INT_TBL_CON_ORDEN);
            vecAux.add("" + INT_TBL_CON_FACTOR);
            vecAux.add("" + INT_TBL_CON_SELE);
            objTblModItems.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_ORDEN, tblDatFor); 
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_FACTOR, tblDatFor); 
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_SELE, tblDatFor); 
            
            
            objMouMotAdaItems=new ZafMouMotAdaItems();
            tblDatFor.getTableHeader().addMouseMotionListener(objMouMotAdaItems);     
            
            
            
     
            
            
 
                        //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
////            tblDatFor.setDragEnabled(true);
////            tblDatFor.setDropMode(DropMode.INSERT_ROWS);
////            
////            tblDatGrupo.setDropTarget(new DropTarget() 
////            {
////                @Override
////                public synchronized void drop(DropTargetDropEvent dtde) 
////                {
////                    //Filas a Mover.
////                    int intFilMovEmp[];
////                    intFilMovEmp = tblDatGrupo.getSelectedRows();
////                    
////                    //Filas Seleccionadas.
////                    int intFilSel[];
////                    intFilSel = tblDatFor.getSelectedRows();
////                    
////                    Point point = dtde.getLocation();
////                    
////                    int intRowForOrigen = tblDatFor.getSelectedRow();                    
////                    //System.out.println("intRowForOrigen: " + intRowForOrigen);
////                    
////                    int intRowEmpPtoDestino = tblDatGrupo.rowAtPoint(point);
////                    //System.out.println("intRowEmpPtoDestino: " + intRowEmpPtoDestino);
////                    
////                    int intRowEmpDestino = tblDatGrupo.getSelectedRow();
////                    //System.out.println("intRowEmpDestino: " + intRowEmpDestino);
////                    
////                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
////                    
////                    boolean blnAdd=true;
////                    boolean blnValCtaDet=false;
////
////////                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 && intRowEmpPtoDestino>=0)
////                    if (intRowForOrigen > -1 &&  intRowEmpPtoDestino>=0)                        
////                    {
////                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
////////                        for(int j=0 ; j<intFilSel.length;j++)
////////                        {
////////                            if (intFilSel[j] > -1 && intRowEmpDestino == -1) 
////////                            {
////////                                strIsCtaCab = objTblModItems.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB).toString();
////////                                strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();
////////
////////                                if (strIsCtaCab.equals("false")) 
////////                                {
////////                                    System.out.println("DUMMY CODE!");
////////                                }
////////                                else 
////////                                {
////////                                    blnAdd=false;
////////                                    blnValCtaDet=true;
////////                                }
////////                            }
////////                        }
////////                        if (blnValCtaDet) {
////////                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
////////////                            limpiarJtableSelection();
////////                        }
////                        
////                        //</editor-fold>
////                        
////                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
////                        if(blnAdd)
////                        {
////                            String itemAcopiar ="";  
////                            boolean blnRepe=false;
////
////////                            if (estado !='C')
////////                            {
////                                //VALIDACION SI EXISTE UN REPETIDO
////                                for (int k = 0; k < intFilSel.length; k++) 
////                                {
////                                        itemAcopiar = tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_COD_ITM_MAE).toString();
////                                        if (isExisteItemRepetido(1,itemAcopiar))
////                                        {
////                                            blnRepe=true;
////                                            break;
////                                        }
////                                }  
////////                            }
////                            if(!blnRepe)
////                            {
//////AGREGAR ELEMENTOS A TBLDATEMP                                
////                                for (int j = 0; j < intFilSel.length; j++) 
////                                {
////                                    //if (intFilSel[j] > -1 && intRowEmpDestino == -1) // TENGA VALORES 
////                                    if (intFilSel[j] > -1) // TENGA VALORES                                         
////                                    {
////
////                                        strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_CON_LIN).toString();
////                                        vecReg = new Vector();
////                                        vecReg.add(INT_TBL_DAT_LIN, strTipRegLin.equals("C") ? "E" : "N");
////                                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ITM_MAE));
////                                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ALT_ITM));
////                                        vecReg.add(INT_TBL_DAT_NOM_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_NOM_ITM));
////////                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
////                                        boolean blnBoo = false;
////                                        for (int i = 0; i < objTblMod.getRowCount(); i++)
////                                        {
////                                            if (i == intRowEmpPtoDestino && !blnBoo) 
////                                            {
////                                                vecDat.add(vecReg);
////                                                i--;
////                                                blnBoo = true;
////                                            }
////                                            else 
////                                            {
////                                                vecDat.add(objTblMod.getData().get(i));
////                                            }
////                                        }
////                                        objTblMod.setData(vecDat);
////                                        tblDatGrupo.setModel(objTblMod);
////                                        vecDat.clear();
////                                        intRowEmpPtoDestino++;
////                                    }
////                                } 
//////ELIMINAR REGISTROS TBLDATEMP
////                                boolean blnExi = false;
////
////                                for (int i = 0; i < objTblModItems.getRowCount(); i++)
////                                {
////                                    for (int j = 0; j < intFilSel.length; j++) 
////                                    {
////                                        if (i == intFilSel[j]) 
////                                        {
////                                            blnExi = true;
////////                                            objTblModItems.setValueAt("M", intFilSel[i], INT_TBL_CON_ORDEN);
////////                                            objTblModItems.setValueAt("M", intFilSel[i], INT_TBL_CON_NOM_ITM);
////                                        }
////                                    }
////                                    if (!blnExi) 
////                                    {
////                                        vecDatItems.add(objTblModItems.getData().get(i));
////                                    }
////                                    else
////                                    {
////                                        vecRegItems = new Vector();
////                                        vecRegItems.add(INT_TBL_CON_LIN, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE));
////                                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE) );
////                                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ALT_ITM) );
////                                        vecRegItems.add(INT_TBL_CON_NOM_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_NOM_ITM) );
////                                        vecRegItems.add(INT_TBL_CON_ORDEN, "M");
////////                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
////                                        
////                                        
////                                        
////                                        vecDatItems.add(vecRegItems);                                        
////                                    }
////
////                                    
////                                    
////                                    blnExi = false;
////
////                                }
////                                objTblModItems.setData(vecDatItems);
////                                tblDatFor.setModel(objTblModItems);
////                                vecDatItems.clear();                                
////                            }
////                            else
////                                mostrarMsgInf("No se puede realizar esta acción ya que está un item a mover , ya se encuentra ingresado en la tabla.");
////                        }
////                    //</editor-fold>
////                    }
////                    else if (intRowEmpDestino > -1 && intRowEmpPtoDestino > -1 && intRowForOrigen > -1) 
////                    {
////                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////////                        limpiarJtableSelection();
////                    }
//////////                    else if ( intRowEmpPtoDestino <=0 ) 
//////////                    {
//////////                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
//////////////                        limpiarJtableSelection();
//////////                    }
////                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
////                    {
////                        boolean blnMov=true;
////                        for (int i = 0; i < intFilMovEmp.length; i++) 
////                        {
////                            if (intFilMovEmp[i] <= 0)
////                            {
////                                blnMov=false;
////                            }
////                        }
////                        if(blnMov)
////                        {
////                            //System.out.println("MOVER-EMP!!");
////                            //System.out.println("intFilMovEmp: " + intFilMovEmp + " intRowEmp: " + intRowEmpPtoDestino);
////                            //objTblModEmp.moveRow(intRowEmpDestino, intRowEmpPtoDestino);
////                            objTblMod.moveRows(intFilMovEmp, intRowEmpPtoDestino);
////                        }
////                        else
////                        {
////                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
////////                            limpiarJtableSelection();
////                        }
////                    }    
////                }        
////            });          
////            
            
            //</editor-fold>
            
////////////////nueavamente
            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
            tblDatFor.setDragEnabled(true);
            tblDatFor.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatGrupo.setDropTarget(new DropTarget() 
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovEmp[];
                    intFilMovEmp = tblDatGrupo.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatFor.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowForOrigen = tblDatFor.getSelectedRow();                    
                    //System.out.println("intRowForOrigen: " + intRowForOrigen);
                    
                    int intRowEmpPtoDestino = tblDatGrupo.rowAtPoint(point);
                    //System.out.println("intRowEmpPtoDestino: " + intRowEmpPtoDestino);
                    
                    int intRowEmpDestino = tblDatGrupo.getSelectedRow();
                    //System.out.println("intRowEmpDestino: " + intRowEmpDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;

////                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 && intRowEmpPtoDestino>0)
                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 )                        
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
////                        for(int j=0 ; j<intFilSel.length;j++)
////                        {
////                            if (intFilSel[j] > -1 && intRowEmpDestino == -1) 
////                            {
////                                strIsCtaCab = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB).toString();
////                                strTipRegLin = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();
////
////                                if (strIsCtaCab.equals("false")) 
////                                {
////                                    System.out.println("DUMMY CODE!");
////                                }
////                                else 
////                                {
////                                    blnAdd=false;
////                                    blnValCtaDet=true;
////                                }
////                            }
////                        }
////                        if (blnValCtaDet) {
////                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
////                            limpiarJtableSelection();
////                        }
                        
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        blnAdd=true;
                        if(blnAdd)
                            
                        {
                            String itemAcopiar ="";  
                            boolean blnRepe=false;                            
                                //VALIDACION SI EXISTE UN REPETIDO
                                for (int k = 0; k < intFilSel.length; k++) 
                                {
                                        itemAcopiar = tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_COD_ITM_MAE).toString();
                                        if ((tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_ORDEN).toString().equals("I")) || (tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_ORDEN).toString().equals("M")))
                                        {
                                             blnRepe=true;
                                             break;
                                        }
////                                        if (isExisteItemRepetido(1,itemAcopiar))
////                                        {
////                                            blnRepe=true;
////                                            break;
////                                        }
                                }                              
                            
                            
                            
                            if(!blnRepe)
                            { 
                            //AGREGAR ELEMENTOS A TBLDATEMP
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (intFilSel[j] > -1 && intRowEmpDestino == -1) // TENGA VALORES 
                                    {
                                        strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_CON_LIN).toString();

                                        vecReg = new Vector();
                                        vecReg.add(INT_TBL_CON_LIN, strTipRegLin.equals("C") ? "E" : "N");
                                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ITM_MAE));
                                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ALT_ITM));
                                        vecReg.add(INT_TBL_DAT_NOM_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_NOM_ITM));
                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
                                        vecReg.add(INT_TBL_DAT_FACTOR, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_FACTOR));
                                        vecReg.add(INT_TBL_DAT_SELE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_SELE));
                                        boolean blnBoo = false;
                                        for (int i = 0; i < objTblMod.getRowCount(); i++)
                                        {
                                            if (i == intRowEmpPtoDestino && !blnBoo) 
                                            {
                                                vecDat.add(vecReg);
                                                i--;
                                                blnBoo = true;
                                            }
                                            else 
                                            {
                                                vecDat.add(objTblMod.getData().get(i));
                                            }
                                        }
                                        objTblMod.setData(vecDat);
                                        tblDatGrupo.setModel(objTblMod);
                                        vecDat.clear();
                                        intRowEmpPtoDestino++;
                                    }
                                }

                                //ELIMINAR REGISTROS TBLDATEMP
                                boolean blnExi = false;

                                for (int i = 0; i < objTblModItems.getRowCount(); i++)
                                {
                                    for (int j = 0; j < intFilSel.length; j++) 
                                    {
                                        if (i == intFilSel[j]) 
                                        {
                                            blnExi = true;
                                        }
                                    }
                                    if (!blnExi) 
                                    {
                                        vecDatItems.add(objTblModItems.getData().get(i));
                                    }
                                    else
                                    {
                                        vecRegItems = new Vector();
                                        vecRegItems.add(INT_TBL_CON_LIN, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE));
                                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE) );
                                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ALT_ITM) );
                                        vecRegItems.add(INT_TBL_CON_NOM_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_NOM_ITM) );
                                        vecRegItems.add(INT_TBL_CON_ORDEN, "M");
                                        vecRegItems.add(INT_TBL_CON_FACTOR, objTblModItems.getValueAt(i, INT_TBL_CON_FACTOR) );
                                        vecRegItems.add(INT_TBL_CON_SELE, objTblModItems.getValueAt(i, INT_TBL_CON_SELE) );                                        
//                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
                                        
                                        
                                        
                                        vecDatItems.add(vecRegItems);                                                 
                                    }
                                    blnExi = false;

                                }
                                objTblModItems.setData(vecDatItems);
                                tblDatFor.setModel(objTblModItems);
                                vecDatItems.clear();                                
                            }
                            else
                                mostrarMsgInf("No se puede realizar esta acción ya que está un item a mover , ya se encuentra ingresado en la tabla.");                            

                        }
                    //</editor-fold>
                    }
////                    else if (intRowEmpDestino > -1 && intRowEmpPtoDestino > -1 && intRowForOrigen > -1) 
////                        
////                    {
////                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////////                        limpiarJtableSelection();
////                    }
////                    else if ( intRowEmpPtoDestino <=0 ) 
////                    {
////                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
////////                        limpiarJtableSelection();
////                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovEmp.length; i++) 
                        {
                            if (intFilMovEmp[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-EMP!!");
                            //System.out.println("intFilMovEmp: " + intFilMovEmp + " intRowEmp: " + intRowEmpPtoDestino);
                            //objTblModEmp.moveRow(intRowEmpDestino, intRowEmpPtoDestino);
                            objTblMod.moveRows(intFilMovEmp, intRowEmpPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se puede mover el item de arriba hacia abajo con respecto a la primera ubicacion, intente de abajo hacia arriba.");
////                            limpiarJtableSelection();
                        }
                    }    
                }        
            });          
            
            
            //</editor-fold>


///////////////////////////
            
// //Configurar JTable: Renderizar celdas.
            objTblCelRenLblItems=new ZafTblCelRenLbl();
            objTblCelRenLblItems.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_COD_ALT_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_NOM_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_ORDEN).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_FACTOR).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_SELE).setCellRenderer(objTblCelRenLblItems);
            objTblCelRenLblItems.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblItems.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("M")){
                        objTblCelRenLblItems.setBackground(Color.ORANGE);
                    }
                    else{
                        if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("I")){
                            objTblCelRenLblItems.setBackground(Color.RED);
                        }
                        else
                            objTblCelRenLblItems.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        
////                         if (objUti.parseString(objTblModItems.getValueAt(intFil,INT_TBL_CON_ORDEN)).equals("M")){
////////                            objTblCelRenLbl.setBackground(colFonColCre);
////                        }
////                        else {
////////                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
                    }
                }
            });
            
////                   
                        
          
            

            
            
            objTblModItems.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFor);

////            objTblCelRenLbl=new ZafTblCelRenLbl();
////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
																					   
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setCellRenderer(objTblCelRenLblItems);
            objTblCelRenLblItems.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
								
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
			 
                }
            });



            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFor);
            //Configurar JTable: Establecer que el JTable sea editable.
////            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);

            objTblModItems.setModoOperacion(objTblModItems.INT_TBL_INS);
            objDocLis=new ZafDocLis();
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblModItems.addTableModelListener(objTblModLis);  
            //Configurar JTable: Establecer tipo de selección.
            tblDatFor.setRowSelectionAllowed(true);
            tblDatFor.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
			
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private int obtenerultimoregistro(String codigo){
        int ultimo = 0;
         int indice =0,contador=0;
         String codigoI="";
        
////        arlDatORDENAR= new ArrayList();
        for (int i=0; i<objTblMod.getRowCountTrue();i++)
        {
            codigoI =objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM).toString();
            
            if  (codigoI !="Null")
            {
                
                indice =indice+1;
            }
            

        }

        return indice;
    }
    
private boolean existeMismoItem(int intFilSel){
        boolean blnRes = false;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            if (tblDatGrupo.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) != null){
                if (i != intFilSel){
                    if (tblDatGrupo.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString().equals(tblDatGrupo.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString())){
                        blnRes = true;
                        mostrarMsgInf("<HTML>El item ya fue ingresado en la fila " + (i+1) + ".</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDatGrupo.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT_ITM);
                        break;
                    }
                }
            }
        }
        return blnRes;
    }
           
private boolean isExisteItemRepetido(int tipo,String item)//chiqui
{
    boolean blnRes=false;
    try{
        String itemCompa="";
        if (tipo==1)
        {
            int k=0;
            if (estadoFor !="N")
            {
                if (ValidaExisteItemTipo(item))
                    blnRes=true;
                else
                    blnRes=false;

                if (blnRes)
                {
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){

                        itemCompa =objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE).toString();
                        if (itemCompa.equals(item))
                            k++;
                    }
                    if (k>0)
                    {
                        blnRes=true;
                    }                          
                }
            }
            else
            {
                if (ValidaExisteItemTipo(item))
                    blnRes=true;
                else
                    blnRes=false;

////                    blnRes=false;
                estadoFor ="M";
            }
        }
        else
        {
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
                        if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ITM_MAE, j, INT_TBL_DAT_COD_ITM_MAE)){

                        blnRes=true;
                        break;
                    }

                }
                if(blnRes)
                    break;
            }                
        }


    }
    catch (Exception e){
        blnRes=true;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

private boolean ValidaExisteItemTipo(String item)//chiqui
    {
        java.sql.Statement stmLoc1 = null;
        java.sql.ResultSet rstLoc1 = null;
        java.sql.Connection conExt1;        
        int intPosRel;
        boolean blnRes=false;
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt1!=null){
                stmLoc1=conExt1.createStatement();
              strSQL="";
                strSQL+=" SELECT det.co_grpInv as co_grupo ";
                strSQL+=" FROM Dynamics2021..tbm_detGrpInv as det ";
                strSQL+="       inner join Dynamics2021..tbm_cabGrpInv as cab on (det.co_grpInv = cab.co_grpInv) ";                
                strSQL+=" WHERE  cab.st_reg='A' and det.tx_codItm ="+ objUti.codificar(item)  +"";                
                
                System.out.println("tbm_detGrpInv: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next())
                    blnRes=true;
            }
            rstLoc1.close();
            stmLoc1.close();
            conExt1.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt1=null;
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
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

    public void mouseMoved(java.awt.event.MouseEvent evt) {
        int intCol = tblDatGrupo.columnAtPoint(evt.getPoint());
        String strMsg = "";
        switch (intCol) {
            case INT_TBL_DAT_COD_ITM_MAE:
                strMsg = "Código del item maestro";
                break;
            case INT_TBL_DAT_COD_ALT_ITM:
                strMsg = "Código 3L";
                break;
            case INT_TBL_DAT_NOM_ITM:
                strMsg = "Descripción del item ";
                break;
            case INT_TBL_DAT_ORDEN:
                strMsg = "Ordenamiento";
                break;   
            case INT_TBL_DAT_FACTOR:
                strMsg = "Factor";
                break;    
            case INT_TBL_DAT_SELE:
                strMsg = "Seleccion";
                break;                 
            default:
                strMsg = "";
                break;
        }
        tblDatGrupo.getTableHeader().setToolTipText(strMsg);
    }
   }
     
     private class ZafMouMotAdaItems extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDatGrupo.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_CON_COD_ITM_MAE:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_CON_COD_ALT_ITM:
                    strMsg = "Código 3L";
                    break;
                case INT_TBL_CON_NOM_ITM:
                    strMsg = "Descripción del item ";
                    break;
                case INT_TBL_CON_ORDEN:
                    strMsg = "Ordenamiento ";
                    break;   
                case INT_TBL_CON_FACTOR:
                    strMsg = "Factor ";
                    break;    
                case INT_TBL_CON_SELE:
                    strMsg = "Seleccion ";
                    break;                     
                default:
                    strMsg = "";
                    break;
            }
            tblDatGrupo.getTableHeader().setToolTipText(strMsg);
        }
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
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
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


    private boolean LLenarComboTiposAgrupacion()//chiqui
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
                strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A'";
                strSQL+=" UNION";
                strSQL+="  SELECT 0 ,'',''";                
                rstLoc = stmLoc.executeQuery(strSQL);
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("tx_nom");
                    dataList.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList.toArray(new String[0]));
                    cmbTipoAgru.setModel(model);    
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
    
    private boolean ConsultaComboTiposAgrupacion(int tipo)//chiqui
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
                strSQL+=" SELECT co_tipAgr, trim(tx_nom) as nombreCor, trim(tx_desLar) as nombreLar FROM tbm_tipAgr where st_reg ='A'";
                strSQL+=" and co_tipAgr ="+tipo +"";                
                rstLoc = stmLoc.executeQuery(strSQL);
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("nombreCor");
                    dataList1.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList1.toArray(new String[0]));
                    cmbTipoAgru.setModel(model); 
                    cmbTipoAgru.setEnabled(false);
                    tipoAgrupa =tipoGlobal;
                    blnRes=true;
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
    
    

 


    

    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrGrp = new javax.swing.ButtonGroup();
        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butLimp = new javax.swing.JButton();
        butElimi = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panFiltros = new javax.swing.JPanel();
        butCons = new javax.swing.JButton();
        lblMsgInf = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        cmbTipoAgru = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panTablas = new javax.swing.JPanel();
        spnDatFor = new javax.swing.JScrollPane();
        tblDatFor = new javax.swing.JTable();
        spnDatEmp = new javax.swing.JScrollPane();
        tblDatGrupo = new javax.swing.JTable();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butLimp.setText("Limpiar");
        butLimp.setToolTipText("Limpiar la ventana");
        butLimp.setEnabled(false);
        butLimp.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpActionPerformed(evt);
            }
        });
        panBot.add(butLimp);

        butElimi.setText("Eliminar");
        butElimi.setToolTipText("Eliminar el grupo seleccionado.");
        butElimi.setEnabled(false);
        butElimi.setPreferredSize(new java.awt.Dimension(92, 25));
        butElimi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butElimiActionPerformed(evt);
            }
        });
        panBot.add(butElimi);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Salir");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guardar los datos.");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        spnFil.setName(""); // NOI18N
        spnFil.setPreferredSize(new java.awt.Dimension(950, 565));

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(1250, 565));
        panFil.setLayout(new java.awt.BorderLayout());

        panFiltros.setPreferredSize(new java.awt.Dimension(100, 120));
        panFiltros.setLayout(null);

        butCons.setLabel("Consultar");
        butCons.setPreferredSize(new java.awt.Dimension(92, 25));
        butCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConsActionPerformed(evt);
            }
        });
        panFiltros.add(butCons);
        butCons.setBounds(530, 98, 90, 20);

        lblMsgInf.setForeground(new java.awt.Color(0, 0, 255));
        lblMsgInf.setText("Trasladar las cuentas de detalle:   Plan de Cuentas: Empresa   <<<  A  >>>  Plan de Cuentas: *** Formato***");
        panFiltros.add(lblMsgInf);
        lblMsgInf.setBounds(10, 520, 740, 14);

        bgrGrp.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFiltros.add(optTod);
        optTod.setBounds(10, 50, 270, 20);

        bgrGrp.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFiltros.add(optFil);
        optFil.setBounds(10, 70, 410, 20);

        cmbTipoAgru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguna" }));
        cmbTipoAgru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoAgruActionPerformed(evt);
            }
        });
        panFiltros.add(cmbTipoAgru);
        cmbTipoAgru.setBounds(190, 10, 170, 20);

        jLabel1.setText("Tipo de Agrupación:");
        panFiltros.add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 14);

        panFil.add(panFiltros, java.awt.BorderLayout.NORTH);

        panTablas.setLayout(new javax.swing.BoxLayout(panTablas, javax.swing.BoxLayout.LINE_AXIS));

        tblDatFor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatFor.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDatFor.setViewportView(tblDatFor);

        panTablas.add(spnDatFor);

        tblDatGrupo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatGrupo.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDatEmp.setViewportView(tblDatGrupo);

        panTablas.add(spnDatEmp);

        panFil.add(panTablas, java.awt.BorderLayout.CENTER);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis1.setText("Listo");
        lblMsgSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis1, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis1.setBorderPainted(false);
        pgrSis1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis1.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis1, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        getContentPane().add(panBarEst, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(1363, 719));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConsActionPerformed
        // TODO add your handling code here:
        
        if (butCons.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConsActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        ////        if (optTod.isSelected())
        ////        {
            ////            objVenConItm01.limpiar();
            ////            chkSolStk.setSelected(false);
            ////        }
    }//GEN-LAST:event_optTodItemStateChanged

private int obtenerIdTipoAgrup(String tipo)
{
    java.sql.Statement stmLoc1 = null;
    java.sql.ResultSet rstLoc1 = null;
    java.sql.Connection conExt1;        
    int idTipo=-1;
    try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

        if (conExt1!=null){
            stmLoc1=conExt1.createStatement();
          strSQL="";
            strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar ";
            strSQL+=" FROM Dynamics2021..tbm_tipAgr ";
            strSQL+=" WHERE  st_reg ='A' and tx_nom ="+ objUti.codificar(tipo)  +"";                

            System.out.println("obtenerIdTipoAtri: " + strSQL);
            rstLoc1=stmLoc1.executeQuery(strSQL);
            if (rstLoc1.next())
                idTipo=rstLoc1.getInt("co_tipAgr");
        }
        rstLoc1.close();
        stmLoc1.close();
        conExt1.close();
        rstLoc1=null;
        stmLoc1=null;
        conExt1=null;
    }
    catch (java.sql.SQLException e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return idTipo;
}

    private void cmbTipoAgruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoAgruActionPerformed
        tipoAgrupa = obtenerIdTipoAgrup(cmbTipoAgru.getSelectedItem().toString());
        if (tipoAgrupa!=-1)
        {
            butCons.setEnabled(true);
            butGuardar.setEnabled(true);
            butCons.doClick();
            ////            if (!cargarDetReg())
            ////            {
                ////                //Inicializar objetos si no se pudo cargar los datos.
                ////                lblMsgSis1.setText("Listo");
                ////                pgrSis1.setValue(0);
                ////                butCons.setText("Consultar");
                ////            }
            ////            //Establecer el foco en el JTable sólo cuando haya datos.
            ////////            objThrGUI =null;
            ////            if (tblDatFor.getRowCount()>0)
            ////            {
                ////                tabFrm.setSelectedIndex(1);
                ////                tblDatFor.setRowSelectionInterval(0, 0);
                ////                tblDatFor.requestFocus();
                ////            }
        }
        else
        {
            butCons.setEnabled(false);
            butGuardar.setEnabled(false);

        }

    }//GEN-LAST:event_cmbTipoAgruActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        boolean blnRes=false;
        int indicador=-1;
        String error ="";
        int codMaxi=0;
        java.sql.Connection conLoc;
        java.sql.Connection connZAF;        
////        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) +".00";        
        try
        {

            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            conLoc.setAutoCommit(false);
            connZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());      
            connZAF.setAutoCommit(false);            
            mensajeError="";

            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                //                if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
                //                      mensajeError="<HTML>Se debe ingresar los campos obligatorios</HTML>";
                ////                if (isExisteItemRepetido())
                ////                    mensajeError ="<HTML>Se encuentra mas de un item repetido</HTML>";
                if (mensajeError.equals(""))
                {
                    int intNumFil=objTblMod.getRowCountTrue();

                    if (intNumFil>1)
                    {
                        if (estado =='C')
                        {
                            if(obtener_maximo())
                            {
                                if(insertar_tbmCabGrupo(conLoc,0))
                                {
                                    if(insertar_tbmDetGrupo(conLoc,0,connZAF))
                                    {
                                        indicador=1;
                                        blnRes=true;
                                    }

                                }
                            }
                            else
                            mostrarMsgInf("No se pudo obtener el grupo siguiente");
                        }
                        else
                        {
                            if (estado =='M')
                            {
                                if(modificar_tbmCabGrupo(conLoc,codigoGrupoGlobal))
                                {
                                    if(borrar_tbmDetGrupo(conLoc,codigoGrupoGlobal))
                                    {
                                        if(insertar_tbmDetGrupo(conLoc,1,connZAF))
                                        {
                                            indicador=2;
                                            blnRes=true;
                                        }
                                    }

                                }
                            }
                        }
                    }
                    else
                    {
                        anular_tbmCabDetGrupo(conLoc,codigoGrupoGlobal);
                        blnRes=true;
                    }

                    if(blnRes==true)
                    {
                        if (estado =='M')
                        intCodCfg=codigoGrupoGlobal;
////                        if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_cabGrpInv", "tbh_cabGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_itmpad")) 
////                        {
////                            if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_detGrpInv", "tbh_detGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_coditm"))                                
////                            {
                               if(objHisTblBasDat.insertarHistoricoMasivo(connZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) 
                                {
                                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                                    {
                                         connZAF.commit();
                                         conLoc.commit();
                                         blnRes=true;
                                         mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
                                    }
                                    else
                                         blnRes=false;
                                }
                               else
                                    blnRes=false;
////                            }
////                            else
////                                blnRes=false;
                                
                            if (!blnRes)
                            {
                                connZAF.rollback();
                                conLoc.rollback();
                            }
////                        }
////                        else
////                        {
////                            connZAF.rollback();
////                            conLoc.rollback();
////                            mostrarMsgInf("No se puede realizar la insercion del historico:cabecera");
////                        }

                        if (estado =='M')
                        {
                            validaOk =true;
                            this.dispose();
                        }

                    }
                    else
                    {
                        connZAF.rollback();
                        conLoc.rollback();
                        mostrarMsgInf(mensajeError);
                    }
                }
                else
                mostrarMsgInf(mensajeError);
            }
            else
                mostrarMsgInf("Se cancelo la operacion de GUARDAR de forma correcta");

            conLoc.close();
            conLoc=null;

            ////
            ////            if (isExisteItemRepetido())
            ////            {
                ////               mostrarMsgInf(mensajeError);
                ////            }
            ////            else
            ////            {
                ////
                ////
                ////            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){
            objUti.mostrarMsgErr_F1(this, Evt);
        }

        ////        if (estado=='0')
        ////            mostrarMsgInf("<HTML>No se puede realizar el guardado, debe llenar todos los campos.</HTML>");
        ////        else
        ////        {
            ////           if (insertar_tbmCabFlu ())//chiqui
            ////                mostrarMsgInf("<HTML>Proceso Actualizado Correctamente.</HTML>");
            ////           else
            ////                mostrarMsgInf("<HTML>Proceso No Actualizado.</HTML>");
            ////        }

        // TODO add your handling code here:

    }//GEN-LAST:event_butGuardarActionPerformed
private boolean InsertarHistoricoCabDet (java.sql.Connection conEXT,int codigoGrupo,int codigoClasifi)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="";
        try
        {
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                    strSQL="";
                    
                    strSQL+=" INSERT INTO tbH_cabGrpInv\n" +
"				(co_grpInv, co_his, co_tipAgr, tx_nomGrpInv, tx_deslar, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, \n" +
"				 co_usrmod,tx_itmPad)  \n";
                    strSQL+=" SELECT a1.co_grpInv as codigoGrupo,isnull(a2.maximo,0)+1  maximo, a1.co_tipAgr as codigoClasi,trim(a1.tx_nomGrpInv) as nombreCo,"
                            + "a1.tx_deslar as nombreLa,a1.tx_obs1 as obs1 , a1.tx_obs2 as obs2 , a1.st_reg as estado, a1.fe_ing as fecingreso ,   \n"
                            + "a1.fe_ultmod as femodifica, a1.co_usring as usuarioCrea, a1.co_usrmod as usuarioModi,a1.tx_itmPad as item   \n";
                    strSQL+=" FROM tbm_cabGrpInv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+="   select isnull(max(CO_HIS),0) as maximo,co_grpInv from tbh_cabGrpInv as a2     \n";
                    strSQL+="   group by co_grpInv\n";
                    strSQL+="   ) as a2 on (a1.co_grpInv =a2.co_grpInv) \n";
                    strSQL+=" WHERE a1.co_grpInv = "+  codigoGrupo  +" and a1.co_tipAgr ="+ codigoClasifi +";\n";
                    
                    
                    strSQL+=" INSERT INTO tbh_detGrpInv\n" +
"				(co_grpInv, co_his, co_reg, tx_codItm, co_facModPre,st_mosItmLisPre) \n" ;
                    strSQL+=" SELECT a1.co_grpInv as codigoGrupo,isnull(a2.maximo,0)+1  maximo, a1.co_reg as registro,trim(a1.tx_codItm) as item,"
                            + " a1.co_facModPre as porcentaje,a1.st_mosItmLisPre   \n";
                    strSQL+=" FROM tbm_detGrpInv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+="   select isnull(max(CO_HIS),0) as maximo,co_grpInv from tbh_detGrpInv as a2     \n";
                    strSQL+="   group by co_grpInv\n";
                    strSQL+="   ) as a2 on (a1.co_grpInv =a2.co_grpInv) \n";
                    strSQL+=" WHERE a1.co_grpInv = "+  codigoGrupo  +"\n";                    
                
                    System.out.println("INSERT " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
    private void butElimiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butElimiActionPerformed
        ////        java.sql.Statement stmLoc1;
        ////        java.sql.ResultSet rstLoc1;
        ////        java.sql.Connection conExt1;
        ////        try
        ////        {
            ////            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            ////            conExt1.setAutoCommit(false);
            ////                     if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            ////                     {
                ////
                ////                         if(borrar_tbmCabGrupo(conExt1,0))
                ////                         {
                    ////                             conExt1.commit();
                    ////                             mostrarMsgInf("La operación ELIMINAR se realizó con éxito.");
                    ////                         }
                ////                         else
                ////                         {
                    ////                             conExt1.rollback();
                    ////                            mostrarMsgInf("La operación CREAR se NO realizó con éxito.");
                    ////                         }
                ////
                ////                     }
            ////                     else
            ////                         mostrarMsgInf("La operación ELIMINAR se cancelo con éxito.");
            ////        }
        ////         catch (java.sql.SQLException e)
        ////        {
            ////            objUti.mostrarMsgErr_F1(this, e);
            ////        }
        ////        catch (Exception e)
        ////        {
            ////            objUti.mostrarMsgErr_F1(this, e);
            ////        }
    }//GEN-LAST:event_butElimiActionPerformed

    private void butLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpActionPerformed

        ////      LLenarTabla();
        limpiarFrm();
        cargarDetRegGrupo();
    }//GEN-LAST:event_butLimpActionPerformed
private boolean limpiarFrm()//chiqui_anterior
    {
        boolean blnRes=true;
        try{
////            txtDescGru.setText("");
////            txtIdGrupo.setText("");
            objTblMod.removeAllRows();
            objTblMod.insertRow();
            lblMsgSis1.setText("Limpiando");

            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
////                lblMsgSis.setText("Listo");
////                pgrSis.setValue(0);
                butCons.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
////            objThrGUI =null;
            if (tblDatFor.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDatFor.setRowSelectionInterval(0, 0);
                tblDatFor.requestFocus();
            }

        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:

        ////        java.sql.Statement stmLoc1;
        ////        java.sql.ResultSet rstLoc1;
        ////        java.sql.Connection conExt1;
        ////        try
        ////        {
            ////            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            ////            conExt1.setAutoCommit(false);
            ////            if (!consultar_DetGrupo(conExt1))
            ////            {
                ////                if(borrar_tbmCabGrupo(conExt1,1))
                ////                {
                    ////                    conExt1.commit();
                    ////                    mostrarMsgInf("Se procedio a ELIMINAR el grupo creado con  éxito.");
                    ////                }
                ////                else
                ////                {
                    ////                    conExt1.rollback();
                    ////                    mostrarMsgInf("La operación ELIMINAR se NO realizó con éxito.");
                    ////                }
                ////            }
            ////        }
        ////        catch (java.sql.SQLException e)
        ////        {
            ////            objUti.mostrarMsgErr_F1(this, e);
            ////        }
        ////        catch (Exception e)
        ////        {
            ////            objUti.mostrarMsgErr_F1(this, e);
            ////        }

        if (estado =='M')
        dispose();
        else
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
 
    

    


    
    private void exitForm() 
    {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.ButtonGroup bgrGrp;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCons;
    private javax.swing.JButton butElimi;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butLimp;
    private javax.swing.JComboBox<String> cmbTipoAgru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgInf;
    private javax.swing.JLabel lblMsgSis1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFiltros;
    private javax.swing.JPanel panTablas;
    private javax.swing.JProgressBar pgrSis1;
    private javax.swing.JScrollPane spnDatEmp;
    private javax.swing.JScrollPane spnDatFor;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatFor;
    private javax.swing.JTable tblDatGrupo;
    // End of variables declaration//GEN-END:variables
     
    
       


    
    
 
    
    

       
    
       
  
           

    
    

    
    
}
