package Maestros.ZafMae83;







/*
 * ZafMae83_02.java
 *
 * Created on 27 de marzo de 2006, 12:15
 */



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
public class ZafMae83_03_01 extends javax.swing.JDialog {
    
    
    

    
    
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
    private String strVersion="v 0.01";
    private ZafPerUsr objPerUsr; 
    private java.util.Date datFecDoc;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.    
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private String strCodAlt, strCodLetItm, strNomItm;                       //Contenido del campo al obtener el foco.
    private int estado=-1;
    private int cofFactGlobal=-1;
    private int cofFactGlobalNuevo=-1;
    private int tipoProceso;
    
    private final int INT_TBL_DAT_LIN=0;    
    private final int INT_TBL_DAT_CODIGO=1;
    private final int INT_TBL_DAT_COD_ITM=2;
    private final int INT_TBL_DAT_NOM_ITM=3;
    private final int INT_TBL_DAT_TRESL=4;    
////    private final int INT_TBL_DAT_ESTADO=5;
////    private final int INT_TBL_DAT_OB=6;
    private ZafTblMod objTblMod;    
    private ZafMouMotAda objMouMotAda;    
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes    
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje    
    
    
    /** Creates new form ZafMae83_02 */
    public ZafMae83_03_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codigoFactor, int tipo ,int codigoFactorN) 
    {
        super(parent, modal);
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            cofFactGlobal =codigoFactor;
            cofFactGlobalNuevo =codigoFactorN;
            tipoProceso =tipo;
            if (tipo==1)
            {
                butModificar.setVisible(true);
                butAnula.setVisible(false);
            }
            else
            {    
                butModificar.setVisible(false);
                butAnula.setVisible(true);
             }   
            
            configurarFrm();
            obtener_items(cofFactGlobal);
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafMae83_02.class.getName()).log(Level.SEVERE, null, ex);
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
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butModificar = new javax.swing.JButton();
        butAnula = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panPer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatFact = new javax.swing.JTable();
        lblFactor1 = new javax.swing.JLabel();
        txtFactor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butModificar.setText("Modificar");
        butModificar.setToolTipText("Modifca los factores");
        butModificar.setPreferredSize(new java.awt.Dimension(92, 25));
        butModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModificarActionPerformed(evt);
            }
        });
        panBot.add(butModificar);

        butAnula.setText("Anular");
        butAnula.setToolTipText("Anula");
        butAnula.setPreferredSize(new java.awt.Dimension(92, 25));
        butAnula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAnulaActionPerformed(evt);
            }
        });
        panBot.add(butAnula);

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        panPer.setPreferredSize(new java.awt.Dimension(100, 50));
        panPer.setLayout(null);

        tblDatFact.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatFact);

        panPer.add(jScrollPane2);
        jScrollPane2.setBounds(10, 30, 310, 250);

        lblFactor1.setText("Nuevo Factor:");
        panPer.add(lblFactor1);
        lblFactor1.setBounds(0, 0, 120, 20);

        txtFactor.setEnabled(false);
        txtFactor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFactorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFactorFocusLost(evt);
            }
        });
        txtFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFactorActionPerformed(evt);
            }
        });
        panPer.add(txtFactor);
        txtFactor.setBounds(140, 0, 90, 20);

        PanFrm.add(panPer, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(345, 385));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
 
    private boolean obtener_items(int codFactor)
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
                strSQL+=" SELECT a1.co_grpInv AS codGrupo, trim (a1.tx_codItm) as codItem, trim (a2.ITEMDESC) as nomItm, trim (a2.ITMGEDSC) as tresL \n";
                strSQL+=" FROM Dynamics2021..tbm_detGrpInv as a1  \n";
                strSQL+="   inner join "+ objParSis.getNombreBaseDatosGP()  +"..iv00101 as a2 on (a1.tx_codItm =a2.ITEMNMBR)  \n";
                strSQL+=" WHERE  a1.co_facModPre = "+ codFactor  +" )  \n";
////                strSQL+=" WHERE st_reg='A'";
                System.out.println("obtener_items: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CODIGO,rst.getInt("codGrupo"));                     
                    vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("codItem"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("nomItm"));
                    vecReg.add(INT_TBL_DAT_TRESL,rst.getString("tresL"));
                    vecDat.add(vecReg);
                }
                
            rstLoc1.close();
            stmLoc1.close();
            conExt.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt=null;
            
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDatFact.setModel(objTblMod);
            vecDat.clear();             
            
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
    
   
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    

   
    private void butModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModificarActionPerformed
            

                       
    }//GEN-LAST:event_butModificarActionPerformed

    
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
    
    private void butAnulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAnulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butAnulaActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void txtFactorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusGained

    private void txtFactorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusLost

    private void txtFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorActionPerformed
    
    private void exitForm() 
    {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAnula;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butModificar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFactor1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panPer;
    private javax.swing.JTable tblDatFact;
    private javax.swing.JTextField txtFactor;
    // End of variables declaration//GEN-END:variables
     
    
       
        
       /** Configurar el formulario. */
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
////            txtObser1.setBackground(objParSis.getColorCamposObligatorios());
////            txtObser1.selectAll();
////            txtObser1.requestFocus(); 
            configurarTblDat();
            
////            configurarVenConItm();

////            configuraListaPrecio();
////            configuraFlutuacion();

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
            int intCol=tblDatFact.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_CODIGO: strMsg="Código del Grupo"; break;
                case INT_TBL_DAT_COD_ITM: strMsg="Còdigo del Item"; break;
                case INT_TBL_DAT_NOM_ITM: strMsg="Descripcion del Item"; break; 
                case INT_TBL_DAT_TRESL: strMsg="Tres L"; break; 
                default: strMsg=""; break;
                        
            }
            tblDatFact.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODIGO,"Còdigo dl Grupo");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Còdigo del Item");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del Item");
            vecCab.add(INT_TBL_DAT_TRESL,"Tres L");
            
////            vecCab.add(INT_TBL_DAT_ESTADO,"Estado");
////            vecCab.add(INT_TBL_DAT_OB,"Observacion");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatFact.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            

            //Configurar JTable: Establecer tipo de selección.
            tblDatFact.setCellSelectionEnabled(true);
            tblDatFact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFact);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFact.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFact.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(300);  
            tcmAux.getColumn(INT_TBL_DAT_TRESL).setPreferredWidth(50);  
////            tcmAux.getColumn(INT_TBL_DAT_ESTADO).setPreferredWidth(50);
////            tcmAux.getColumn(INT_TBL_DAT_OB).setPreferredWidth(50);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatFact.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            
////////            objTblCelRenLblDer=new ZafTblCelRenLbl();
////////            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////////            tcmAux.getColumn(INT_TBL_DAT_PESO).setCellRenderer(objTblCelRenLblDer);
////////            tcmAux.getColumn(INT_TBL_DAT_PRECIO).setCellRenderer(objTblCelRenLblDer);            
            
////////            //botones agregados
////////            objTblCelRenBut=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenBut);
////////            objTblCelRenBut=null;
////////            objTblCelRenButConOcp=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenButConOcp);
////////            objTblCelRenButConOcp = null;
////////            
////////                        objTblCelEdiBtn=new ZafTblCelEdiTxt(tblDatGru);
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellEditor(objTblCelEdiBtn);
////////////            
////////            new ButSolTraInv(tblDatGru, INT_TBL_DAT_MODIFICAR);            
            
            
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFact.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFact);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatFact);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_MODIFICAR);
////            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFact);

//////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//////            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setCellRenderer(objTblCelRenLbl);
//////            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////
//////                }
//////            });


//Agregando el listener para cuando el usuario de click en un dia
//////        tblDatFact.addMouseListener(new java.awt.event.MouseAdapter() {
//////            @Override
//////            public void mouseClicked(java.awt.event.MouseEvent evt) {
//////                String nomL="",nomC="";
//////                if(evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2)
//////                    {
//////                        txtNomLargo.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_LAR ).toString());
//////                        txtNomCorto.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_CORT ).toString());
//////                        txtFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_FAC ).toString());
//////                        txtObser1.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_OB ).toString());
//////                        txtCodFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_CODIGO ).toString());
//////                    }
//////            }
//////        });
        
        
        
            

            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_CODIGO;

            intColVen=null;
            intColTbl=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFact);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
////            objTblMod.setColumnDataType(INT_TBL_DAT_PESO, objTblMod.INT_COL_DBL, new Integer(0), null);
////            objTblMod.setColumnDataType(INT_TBL_DAT_PRECIO, objTblMod.INT_COL_DBL, new Integer(0), null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  


    
 
    
    

       
    
       
  
           

    
    

    
    
}
