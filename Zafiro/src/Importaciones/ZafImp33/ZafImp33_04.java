package Importaciones.ZafImp33; 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
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
import Librerias.ZafUtilGP.ZafUtilGP;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author  jayapata
 */
public class ZafImp33_04 extends javax.swing.JDialog {
      //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    
    
   // private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatPr, vecCabPr, vecRegPr;    
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
    private String strVersion="v0.01";
    private ZafPerUsr objPerUsr; 
    private java.util.Date datFecDoc;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.   
    private ZafTblPopMnu objTblPopMnuPro;                          //PopupMenu: Establecer PeopuMenú en JTable.       
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblBus objTblBusPro;                                //Editor de búsqueda.    
    private ZafTblFilCab objTblFilCab;
    private ZafTblFilCab objTblFilCabPro;    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblOrd objTblOrdPro, objTblOrdLocPro;                  //JTable de ordenamiento. 
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private String strCodAlt, strCodLetItm, strNomItm;                       //Contenido del campo al obtener el foco.
    private int estado=-1;
    
    private final int INT_TBL_DAT_LIN=0;    
    private final int INT_TBL_DAT_TRESL=3;
    private final int INT_TBL_DAT_CODZAF=2;
    private final int INT_TBL_DAT_CODGP=1;    
    private final int INT_TBL_DAT_NOMBRE=4;    
    
    private final int INT_TBL_DAT_LIN2=0;    
    private final int INT_TBL_DAT_CODIGO2=1;
    private final int INT_TBL_DAT_COD_ITM2=2;
    private final int INT_TBL_DAT_NOM_ITM2=3;
    private final int INT_TBL_DAT_TRESL2=4;  
    
    private ZafTblMod objTblMod;  
    private ZafTblMod objTblModPro;      
    private ZafMouMotAda objMouMotAda;   
    private ZafMouMotAda objMouMotAdaPro;       
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes    
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje 
    private int procesoEjecucion =0;
    private ArrayList arlRegITEMS,arlDatITEMS;   
    private static final int INT_ARL_CON_3L=0;
    private static final int INT_ARL_CON_ITM=1;   
    public boolean ejecucionActivo =false;
    private ZafUtilGP objUtiGP;      
    
    
    /** Creates new form ZafMae83_02 */
    public ZafImp33_04(java.awt.Frame parent, boolean modal, ZafParSis obj, ArrayList arlRegItemsInact) 
    {
        super(parent, modal);
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
             arlRegITEMS = new ArrayList(arlRegItemsInact);
            configurarFrm();
            
            
        } catch (CloneNotSupportedException ex) {
           // Logger.getLogger(ZafMae83_02.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panPer = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDatProceso = new javax.swing.JTable();
        butActivar = new javax.swing.JButton();
        butCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        panPer.setPreferredSize(new java.awt.Dimension(100, 50));
        panPer.setLayout(null);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.BorderLayout());

        tblDatProceso.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDatProceso);

        butActivar.setText("Activar");
        butActivar.setToolTipText("Proceso");
        butActivar.setPreferredSize(new java.awt.Dimension(92, 25));
        butActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butActivarActionPerformed(evt);
            }
        });

        butCancelar.setText("Cancelar");
        butCancelar.setToolTipText("Proceso");
        butCancelar.setPreferredSize(new java.awt.Dimension(92, 25));
        butCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panDetLayout = new javax.swing.GroupLayout(panDet);
        panDet.setLayout(panDetLayout);
        panDetLayout.setHorizontalGroup(
            panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panDetLayout.createSequentialGroup()
                .addComponent(butActivar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panDetLayout.setVerticalGroup(
            panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDetLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butActivar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Items Inactivos", panRpt);

        panPer.add(tabFrm);
        tabFrm.setBounds(0, 0, 570, 290);

        PanFrm.add(panPer, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(589, 385));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

  
    
   
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
   

private boolean validarCampos(java.sql.Connection conExt){
  boolean blnRes=true;
////  try{
////      
////      if(txtNomCorto.getText().equals("")) {
////        strMsg = "El campo <<Nombre del Factor>> es obligatorio.\nEscriba el Nombre del Factor y vuelva a intentarlo.";
////        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////        txtNomCorto.requestFocus();
////        blnRes=false;
////     }
////     if(txtFactor.getText().equals("")) {
////        strMsg = "El campo <<Factor> es obligatorio.\nNo se puede tener vacio el Factor.";
////        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////        txtFactor.requestFocus();
////        blnRes=false;
////     }     
////     if(objUti.isNumero(txtFactor.getText())==false) {
////        strMsg = "El campo <<Factor> debe ser de tipo Numero";
////        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////        txtFactor.requestFocus();
////        blnRes=false;
////     }
////     if (!consultar_Factor(conExt))
////     {
////        strMsg = "La informacion por ingresar , ya se encuentra ingresada dentro de los factores.";
////        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
////        txtFactor.requestFocus();
////        blnRes=false;         
////     }
////     
////
////
////
////     
////  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}
            /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    private boolean ejecutarProceso()
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strSQLUp="";
        boolean blnRes=false;
        String proceso ="",mensaje ="",mensajeConfirmacion;
        
////        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
////        try
////        {
////            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
////////            conLoc.setAutoCommit(false);
////            if (conLoc!=null)
////            {
////                if (procesoEjecucion ==1)
////                    proceso ="Modificaciòn";
////                else
////                    proceso ="Anulaciòn";
////                mensaje ="¿Está seguro que desea realizar esta operación de "+ proceso;
////                mensajeConfirmacion ="Se realizo el proceso de  "+ proceso;
////                if (mostrarMsgCon(mensaje)==0)
////                {
////                    stmLoc=conLoc.createStatement();
////                    int intNumFil=objTblModPro.getRowCountTrue();
////                    if (procesoEjecucion==2)
////                    {
////                        for (int i=0; i<intNumFil;i++)
////                        {
////                            strSQLUp="";
////        ////                    strSQLUp+="UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = "+cofFactGlobalNuevo  " + objParSis.getCodigoUsuario() + " ,fe_ultmod = '"+datFecDoc +"'" ;
////                            if (procesoEjecucion==1)
////                            {
////    ////                            strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = "+ objUti.parseDouble(txtFactorMod.getText())  +"\n";
////    ////                            strSQLUp+=" WHERE tx_coditm = '"+tblDatProceso.getValueAt(i, INT_TBL_DAT_COD_ITM2 ).toString() +"' and co_grpInv = "+ tblDatProceso.getValueAt(i, INT_TBL_DAT_CODIGO2 ).toString()  +"  ; \n";
////                            }
////                            else
////                            {
////                                strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = 0 \n";
////                                strSQLUp+=" WHERE tx_coditm = '"+tblDatProceso.getValueAt(i, INT_TBL_DAT_COD_ITM2 ).toString() +"' and co_grpInv = "+ tblDatProceso.getValueAt(i, INT_TBL_DAT_CODIGO2 ).toString()  +"  ; \n";                            
////                            }
////    ////                            
////    ////                        else
////    ////                            strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = 0 \n";
////
////
////                            blnRes =true;
////                        }
////                        
////                    }
////
////                    if (procesoEjecucion==2)
////                    {
////                        strSQLUp+=" UPDATE  Dynamics2021..tbm_facModPre set   st_reg ='I' \n";
////                        strSQLUp+=" WHERE co_facModPre = "+txtCodFactor.getText() +" ; \n"; 
////                        blnRes =true;
////                    }
////                    else
////                    {
////                        strSQLUp+=" UPDATE  Dynamics2021..tbm_facModPre set  \n";
////                        strSQLUp+="             tx_desLar = "+objUti.codificar(txtNomLargo.getText())  +"  \n"; 
////                        if ((!txtFactorMod.getText().equals(txtFactor.getText()) ) && (!txtFactorMod.getText().equals(0)))
////                            strSQLUp+="         ,   nd_fac = "+ objUti.parseDouble(txtFactorMod.getText())  +"  \n";
////                        else
////                            strSQLUp+="         ,   nd_fac = "+ objUti.parseDouble(txtFactor.getText())  +"  \n";
////                        
////                        strSQLUp+="         ,   tx_obs1 = "+ objUti.codificar(txtObser1.getText())  +"  \n";
////                        strSQLUp+="         ,   tx_obs2 = "+ objUti.codificar(txtObser1.getText())  +"  \n";
////                        strSQLUp+="         ,   tx_desCor = "+objUti.codificar(txtNomCorto.getText())  +"  \n";     
////                        strSQLUp+=" WHERE co_facModPre = "+txtCodFactor.getText() +" ; \n";    
////                        blnRes =true;
////                    }
////                    
////                    
////
////                    if (blnRes ==true)
////                    {
////                         System.out.println("tbm_detGrpInv_tbm_facModPre: " + strSQLUp); 
////                         stmLoc.executeUpdate(strSQLUp);
////                    }                    
////                        mostrarMsgInf(mensajeConfirmacion);                    
////                        stmLoc.close();
////                        stmLoc=null;                    
////
////                } 
////            }            
////           
////        }
////        catch (java.sql.SQLException e) { 
////           objUti.mostrarMsgErr_F1(this, e); 
////           blnRes=false;
////       }
////       catch(Exception  Evt){ 
////                objUti.mostrarMsgErr_F1(this, Evt);
////                blnRes=false;
////        }

    return  blnRes;      
    }
    
    private void butActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butActivarActionPerformed
        // TODO add your handling code here:
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación de ACTIVACIÓN ?"  )==0)
        {
            if (objUtiGP.actualizarDet(arlRegITEMS, 2) )
            {
                if (objUtiGP.actualizarDetHis(arlRegITEMS))
                {
                    mostrarMsgInf("La operación ACTIVAR se realizó con éxito.");
                    ejecucionActivo=true;
                    dispose();
                }
                else
                {
                    mostrarMsgInf("La operación ACTIVAR ocurrio un error."); 
                    ejecucionActivo=false;
                    dispose();
                }
            }
            else
            {
                mostrarMsgInf("La operación ACTIVAR ocurrio un error.");
                ejecucionActivo=false;
                dispose();
            }                
               
        }        
        
        
        
    }//GEN-LAST:event_butActivarActionPerformed
    public boolean ProcesoActivar()
    {
        return ejecucionActivo;
    }
    private void butCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butCancelarActionPerformed
    
    private void exitForm() 
    {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butActivar;
    private javax.swing.JButton butCancelar;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panPer;
    private javax.swing.JPanel panRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatProceso;
    // End of variables declaration//GEN-END:variables
     
    
       
        
       /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            configurarTblDat();  
            cargarItemsInactivos();

            datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());

////            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDatProceso.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_CODGP: strMsg="Código de GP"; break;
                case INT_TBL_DAT_CODZAF: strMsg="Código de ZAFIRO"; break;
                case INT_TBL_DAT_TRESL: strMsg="Código de Tres Letras"; break;                
                case INT_TBL_DAT_NOMBRE: strMsg="Descripciòn del Item"; break;
                default: strMsg=""; break;
                        
            }
            tblDatProceso.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(5);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODGP,"CodGP");
            vecCab.add(INT_TBL_DAT_CODZAF,"CodZAF");
            vecCab.add(INT_TBL_DAT_TRESL,"3L");
            vecCab.add(INT_TBL_DAT_NOMBRE,"Descripciòn del Item");            
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatProceso.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
////            objTblMod.setColumnDataType(INT_TBL_DAT_CODIGO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
////            objTblMod.setColumnDataType(INT_TBL_DAT_FAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer tipo de selección.
            tblDatProceso.setCellSelectionEnabled(true);
            tblDatProceso.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatProceso);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatProceso.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatProceso.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODGP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CODZAF).setPreferredWidth(100);            
            tcmAux.getColumn(INT_TBL_DAT_TRESL).setPreferredWidth(50);                        
            tcmAux.getColumn(INT_TBL_DAT_NOMBRE).setPreferredWidth(300);            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatProceso.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
                    
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatProceso.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatProceso);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatProceso);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatProceso);



            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatProceso);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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

private boolean cargarItemsInactivos()//chiqui
    {
        int i=0;
        boolean blnRes=true;
       
        for (i=0; i<arlRegITEMS.size();i++)
        {
            vecReg=new Vector();
            vecReg.add(INT_TBL_DAT_LIN,objUti.getStringValueAt(arlRegITEMS, i, INT_TBL_DAT_LIN));
            vecReg.add(INT_TBL_DAT_CODGP,objUti.getStringValueAt(arlRegITEMS, i, INT_TBL_DAT_CODGP));
            vecReg.add(INT_TBL_DAT_CODZAF,objUti.getStringValueAt(arlRegITEMS, i, INT_TBL_DAT_CODZAF));
            vecReg.add(INT_TBL_DAT_TRESL,objUti.getStringValueAt(arlRegITEMS, i, INT_TBL_DAT_TRESL));                     
            vecReg.add(INT_TBL_DAT_NOMBRE,objUti.getStringValueAt(arlRegITEMS, i, INT_TBL_DAT_NOMBRE));                    
            vecDat.add(vecReg);
        }
        //Asignar vectores al modelo.
        objTblMod.setData(vecDat);
        tblDatProceso.setModel(objTblMod);
        vecDat.clear();
        return blnRes;
    }
    
    
 
    
    

       
    
       
  
           

    
    

    
    
}
