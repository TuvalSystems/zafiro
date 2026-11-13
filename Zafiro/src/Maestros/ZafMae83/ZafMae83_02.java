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
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import Librerias.ZafVenConGP.ZafVenConGP;
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
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DropMode;
/**
 *
 * @author  jayapata
 */
public class ZafMae83_02 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN=0;             //Línea
    private static final int INT_TBL_DAT_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_DAT_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_DAT_ORDEN=4;         //ORDENAMIENTO
    
    private static final int INT_TBL_CON_LIN=0;             //Línea
    private static final int INT_TBL_CON_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_CON_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_CON_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_CON_ORDEN=4;         //ORDENAMIENTO
    
    
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
    private char estado='0';
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
    private ZafTblCelRenLbl objTblCelRenLbl,objTblCelRenLblItems;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private ZafDocLis objDocLis;
    private boolean blnHayCam;    
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.  
    private ArrayList arlRegORDENAR,arlDatORDENAR;
    private ZafVenConItmGP01 objVenConItm01;
    private ZafThreadGUI objThrGUI;
    
    private String strTipRegLin="";   
    
    
    
    
    /** Creates new form ZafMae83_02 */
    public ZafMae83_02(java.awt.Frame parent, boolean modal, ZafParSis obj, String idGrupo, String descripcion,int tipo) {
        super(parent, modal);
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            if (tipo==0)
            {
                estado ='C';
////                if(obtener_maximo())
////                {
                    txtIdGrupo.setText(idGrupo);
                    txtDescGru.setEditable(false);
                    txtDescGru.setText(descripcion);
                    objTblMod.insertRow();  
                    
////                }
////                else
////                    mostrarMsgInf("<HTML>No se puede obtener el numero maximo de GRUPOS</HTML>");
////                txtDescGru.setEditable(true);
            }
                
            else
            {
                estado ='M';
                txtIdGrupo.setText(idGrupo);
                LLenarTabla();
                txtDescGru.setEditable(false);
                txtIdGrupo.setEditable(false);
            }
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafMae83_02.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            configurarTblDatItems();
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
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
            
        }
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
                    intCodCfg=rstLoc1.getInt("co_grp");
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
        if (txtIdGrupo.getText().equals("")) {
            txtIdGrupo.setText("");
        }else{
            if(cargarCabReg(1)){
                if(cargarDetRegGrupo()){
                    System.out.println("ok dos");
                }
            }
            
        }
    }
    
    private boolean cargarCabReg(int tipo)//chiqui2
    {
        boolean blnRes=true;
        try{ 
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stm=con.createStatement();
              strSQL="";
                strSQL+=" SELECT RTRIM (co_grpInv) codigo, RTRIM(tx_nomGrpInv) as descripcion";
                strSQL+=" FROM tbm_cabGrpInv ";
                if (tipo==0)
                        strSQL+=" WHERE  st_reg ='A' ";           
                else    
                        strSQL+=" WHERE  st_reg ='A' and co_grpInv ='"+ txtIdGrupo.getText() +"'";           
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (tipo==0)
                    {
                        txtIdGrupo.setText("");
                        txtDescGru.setText("");
                    }
                    strAux=rst.getString("codigo");
                    txtIdGrupo.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("descripcion");
                    txtDescGru.setText((strAux==null)?"":strAux);

                    lblTit.setText(objParSis.getNombreMenu() + strVersion +"  "+ ((strAux==null)?"":strAux ));                   
                }
                else{
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
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
    
        private boolean limpiarFrm()//chiqui_anterior
    {
        boolean blnRes=true;
////        try{
////            txtDescGru.setText("");
////            txtLstAfectar.setText("");
////            txtLstOrigen.setText("");
////            txtValMin.setText("");
////            txtValMax.setText("");
////            objTblMod.removeAllRows();
////        }
////        catch (Exception e){
////            blnRes=false;
////        }
        return blnRes;
    }
        
        
    private boolean consultar_DetGrupo(java.sql.Connection conExt)
    {
        boolean blnRes=false;
        
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT * from tbm_detGrpInv where co_grpInv = " + txtIdGrupo.getText() + " \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                    blnRes=true;
                    
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
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

        bgrFil = new javax.swing.ButtonGroup();
        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        lblMsgSis = new javax.swing.JLabel();
        butGuardar = new javax.swing.JButton();
        butElimi = new javax.swing.JButton();
        butLimp = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panFiltros = new javax.swing.JPanel();
        butCons = new javax.swing.JButton();
        txtIdGrupo = new javax.swing.JTextField();
        txtDescGru = new javax.swing.JTextField();
        lblPlaCtaEmp = new javax.swing.JLabel();
        lblPlaCtaFor = new javax.swing.JLabel();
        lblMsgInf = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panTablas = new javax.swing.JPanel();
        spnDatFor = new javax.swing.JScrollPane();
        tblDatFor = new javax.swing.JTable();
        spnDatEmp = new javax.swing.JScrollPane();
        tblDatGrupo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panBot.add(pgrSis);

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBot.add(lblMsgSis);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guardar los datos.");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);
        butGuardar.getAccessibleContext().setAccessibleDescription("Guardar los datos");

        butElimi.setText("Eliminar");
        butElimi.setToolTipText("Eliminar el grupo seleccionado.");
        butElimi.setPreferredSize(new java.awt.Dimension(92, 25));
        butElimi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butElimiActionPerformed(evt);
            }
        });
        panBot.add(butElimi);

        butLimp.setText("Limpiar");
        butLimp.setToolTipText("Limpiar la ventana");
        butLimp.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpActionPerformed(evt);
            }
        });
        panBot.add(butLimp);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Salir");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

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
        butCons.setBounds(530, 44, 90, 20);

        txtIdGrupo.setBackground(objParSis.getColorCamposObligatorios());
        txtIdGrupo.setEnabled(false);
        txtIdGrupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusLost(evt);
            }
        });
        txtIdGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdGrupoActionPerformed(evt);
            }
        });
        panFiltros.add(txtIdGrupo);
        txtIdGrupo.setBounds(750, 100, 40, 20);

        txtDescGru.setBackground(objParSis.getColorCamposObligatorios());
        txtDescGru.setEnabled(false);
        txtDescGru.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescGruFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescGruFocusLost(evt);
            }
        });
        txtDescGru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescGruActionPerformed(evt);
            }
        });
        panFiltros.add(txtDescGru);
        txtDescGru.setBounds(790, 100, 460, 20);

        lblPlaCtaEmp.setText("Grupo de Inventario:");
        panFiltros.add(lblPlaCtaEmp);
        lblPlaCtaEmp.setBounds(630, 100, 410, 14);

        lblPlaCtaFor.setText("Lista de Inventario:");
        panFiltros.add(lblPlaCtaFor);
        lblPlaCtaFor.setBounds(0, 100, 110, 14);

        lblMsgInf.setForeground(new java.awt.Color(0, 0, 255));
        lblMsgInf.setText("Trasladar las cuentas de detalle:   Plan de Cuentas: Empresa   <<<  A  >>>  Plan de Cuentas: *** Formato***");
        panFiltros.add(lblMsgInf);
        lblMsgInf.setBounds(10, 520, 740, 14);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFiltros.add(optTod);
        optTod.setBounds(0, 0, 270, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFiltros.add(optFil);
        optFil.setBounds(0, 20, 410, 20);

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

        setSize(new java.awt.Dimension(1275, 695));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:

        
          java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.Connection conExt1;
        try
        {
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conExt1.setAutoCommit(false);
            if (!consultar_DetGrupo(conExt1))
            {
                if(borrar_tbmCabGrupo(conExt1,1))
                 {
                     conExt1.commit();
                     mostrarMsgInf("Se procedio a ELIMINAR el grupo creado con  éxito.");
                 }
                 else
                 {
                     conExt1.rollback();
                    mostrarMsgInf("La operación ELIMINAR se NO realizó con éxito."); 
                 }
            }           
        }
         catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        
        dispose();
        
    }//GEN-LAST:event_butCanActionPerformed
    private boolean borrar_tbmDetGrupo(java.sql.Connection conExt)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_detGrpInv where co_grpInv ="+ txtIdGrupo.getText() +"";
                System.out.println("tbm_detGrpInv: " + strSQL);
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
    private boolean borrar_tbmCabGrupo(java.sql.Connection conExt, int tipo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String codigo="";
        try{
            
            
            if (conExt!=null)
            {
                stm=conExt.createStatement();
                       
                strSQL="";
                if (tipo==0)
                    strSQL+="update Dynamics2021..tbm_cabGrpInv set st_reg ='I' WHERE co_grpInv=" + txtIdGrupo.getText() + " ;";
                else
                    strSQL+="delete Dynamics2021..tbm_cabGrpInv WHERE co_grpInv=" + txtIdGrupo.getText() + " ;";
                
                System.out.println("tbm_detGrpInv: " + strSQL);
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
    
    
    private boolean insertar_tbmDetGrupo(java.sql.Connection conExt)//chiqui_antiguo
    {
        String strSQLGlobal="";
        int contador=0;
        int ordenamiento=0;
        boolean blnRes=true;
        String codigo="";
        try{
            
            
            if (conExt!=null)
            {
                stm=conExt.createStatement();
////                objTblMod.removeEmptyRows();
////                ordenarDatPorOrden();
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
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
                        strSQL+="INSERT INTO tbm_detGrpInv(";
                        strSQL+="        co_grpInv,co_reg,tx_codItm)";
                        strSQL+=" VALUES (";
                        strSQL+=" " + txtIdGrupo.getText() + ""; //co_emp
////                        strSQL+=", "+ objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN) +""; //
                        strSQL+=", "+ nd_orden +""; //                        
                        strSQL+=", '" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) + "'"; //co_tipdoc                
                        strSQL+=");";

                        strSQLGlobal += strSQL;
                        
                    }

                    
                }
                if (blnRes ==true)
                {
                     System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                     stm.executeUpdate(strSQLGlobal);
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

    private boolean insertar_tbmCabGrupo(java.sql.Connection conExt)//chiqui_antiguo
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabGrpInv(";
                strSQL+="        co_grpInv,tx_nomGrpInv,tx_deslar,st_reg,fe_ing,co_usrIng)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtIdGrupo.getText() + ""; //co_emp
                strSQL+=", '" + txtDescGru.getText() + "'"; //co_loc
                strSQL+=", '" + txtDescGru.getText()+ "'"; //co_tipdoc     
                strSQL+=", 'A'";//ne_secEmp
                strSQL+=", GETDATE()";//ne_numCot
                strSQL+=", " + 1 + "";//ne_numDoc
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
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

   private int valida_tbmCabGrupo()
   {
       int blnRes=0;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.Connection conExt;
        try
        {
           conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
           
            int intNumFil=objTblMod.getRowCountTrue();
            if (conExt!=null)
            {
                stm=conExt.createStatement();
                 if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
                 {
                     blnRes=-1;//hay error
                 }
                 else
                 {
                    stmLoc1=conExt.createStatement();
                    strSQL="";
                    strSQL+=" SELECT co_grpInv AS codigo,rtrim(tx_nomGrpInv) AS descripcion FROM tbm_cabGrpInv where st_reg='A' \n";
                    strSQL+=" AND tx_nomGrpInv='" + txtDescGru.getText() + "'";

                    System.out.println("SELECT " + strSQL);
                    rstLoc1 = stmLoc1.executeQuery(strSQL);
                    if(rstLoc1.next())
                    {
                        if (mostrarMsgCon("¿La descripción del grupo ya se encuentra , desea modificar el grupo de inventario?")==0)
                        {
                            if(borrar_tbmDetGrupo(conExt))
                            {
                                if(insertar_tbmDetGrupo(conExt))
                                 blnRes=1;//modificado correcto
                            }
                            else
                                blnRes=-1;//hay error
                        }
                        else
                            blnRes=2;// no desea modificar
                    }
////                        blnRes=false;

                      stmLoc1.close();
                      stmLoc1=null;
                      rstLoc1.close();
                      rstLoc1=null;
                 }                
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
    
////        
////        private boolean insertar_tbmDetGrupo(java.sql.Connection conExt)//chiqui_antiguo
////    {
////        String strSQLGlobal="";
////        int contador=0;
////        boolean blnRes=false;
////        String codigo="";
////        try{
////            int intNumFil=objTblMod.getRowCountTrue();
////            
////            if (conExt!=null)
////            {
////                stm=conExt.createStatement();
////                
////                for (int i=0; i<intNumFil;i++)
////                {
////                    codigo =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
////                    
////                    if  (codigo !="Null")
////                    {
////                                            //Armar la sentencia SQL.
////                        contador = i+1;
////                        strSQL="";
////                        strSQL+="INSERT INTO tbm_detGrpInv(";
////                        strSQL+="        co_grpInv,co_reg,tx_codItm)";
////                        strSQL+=" VALUES (";
////                        strSQL+=" " + txtIdGrupo.getText() + ""; //co_emp
////                        strSQL+=", "+ objTblMod.  .getValueAt(i,INT_TBL_DAT_ORDEN) +""; //
////                        strSQL+=", '" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) + "'"; //co_tipdoc                
////                        strSQL+=");";
////
////                        strSQLGlobal += strSQL;
////                    }
////
////                    
////                }
////                System.out.println("tbm_detGrpInv: " + strSQLGlobal);
////                stm.executeUpdate(strSQLGlobal);
////                stm.close();
////                stm=null;
////                blnRes =true;
////            }
////            
////        }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////        
////        
////        
////        
////        sssssssssss
////        
////   }
    
        public void ordenarDatPorOrden()
    {
////        //Configurar JTable: Establecer la clase que controla el ordenamiento.
        objTblOrd=new ZafTblOrd(tblDatGrupo);
        objTblOrd.ordenar(INT_TBL_DAT_ORDEN);
////        int indice =0,contador=0;
////        String codigo ="";
////        
////        arlDatORDENAR= new ArrayList();
////        for (int i=0; i<objTblMod.getRowCountTrue();i++)
////        {
////            codigo =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
////            if  (codigo !="Null")
////            {
////                contador =contador+1;
////                indice =Integer.valueOf(objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN).toString()) ;
////            }
////            
////
////        }
    }  
    
        
    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        boolean blnRes=false;
        String error ="";
        java.sql.Connection conLoc;
        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        try
        {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conLoc.setAutoCommit(false);
            mensajeError="";
            
             if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
             {
                if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
                      mensajeError="<HTML>Se debe ingresar los campos obligatorios</HTML>";
                if (isExisteItemRepetido())
                    mensajeError ="<HTML>Se encuentra mas de un item repetido</HTML>"; 
                if (mensajeError.equals(""))
                {
                    if (estado =='M')
                    {
                        if(borrar_tbmDetGrupo(conLoc))
                        {
                            if(insertar_tbmDetGrupo(conLoc))
                             blnRes=true;
                        }
                    }
                    else
                    {
                        if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
                            mensajeError="Se debe ingresar los campos obligatorios";
                        else
                        {

                            if(insertar_tbmDetGrupo(conLoc))
                                blnRes=true;                        
    ////                        if(valida_tbmCabGrupo()==0)
    ////                        {
    ////                            if(insertar_tbmCabGrupo(conLoc))
    ////                            {
    ////                                if(insertar_tbmDetGrupo(conLoc))
    ////                                    blnRes=true;
    ////                            }                            
    ////                        }
    ////                        else
    ////                        {
    ////                            if(valida_tbmCabGrupo()==-1)
    ////                                mensajeError="Se encuentra con error la generación del grupo";
    ////                            else
    ////                            {
    ////                               if(valida_tbmCabGrupo()==1)
    ////                               {
    ////                                   mensajeError="Se modifico el grupo de forma correcta";
    ////                                   blnRes=true;
    ////                               }
    ////                                   
    ////                               else
    ////                                   mensajeError="Se cancela la modificación";
    ////                            }
    ////                        }

                        }

    ////                    }
    ////                    else
    ////                        mensajeError ="N";
                    }
                    if(blnRes==true)
                    {
                       conLoc.commit();

                       mostrarMsgInf("Se realizo la insercion del registro, de forma correcta");
                       dispose();
                    }
                    else
                    {
                        conLoc.rollback();
                        mostrarMsgInf(mensajeError);                
                    }          
                }
                else
                    mostrarMsgInf(mensajeError);
             }
             else
                  mostrarMsgInf("Se cancelo la operacion de GUARDAR de forma correcta");
            

            
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

    private void txtIdGrupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdGrupoFocusGained
////        strCodFor=txtIdGrupo.getText();
////        txtIdGrupo.selectAll();
    }//GEN-LAST:event_txtIdGrupoFocusGained

    private void txtIdGrupoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdGrupoFocusLost
////        if (!txtIdGrupo.getText().equalsIgnoreCase(strCodFor))
////        {
////            if (txtIdGrupo.getText().equals(""))
////            {
////                txtIdGrupo.setText("");
////                txtDescGru.setText("");
////            }
////            else
////            {
////                mostrarVenConFor(1);
////            }
////        }
////        else
////        {
////            txtIdGrupo.setText(strCodFor);
////        }
    }//GEN-LAST:event_txtIdGrupoFocusLost

    private void txtIdGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdGrupoActionPerformed
        txtIdGrupo.transferFocus();
    }//GEN-LAST:event_txtIdGrupoActionPerformed

    private void txtDescGruFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescGruFocusGained
////        strFor = txtDescGru.getText();
////        txtDescGru.selectAll();
    }//GEN-LAST:event_txtDescGruFocusGained

    private void txtDescGruFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescGruFocusLost
        if (!txtDescGru.getText().equals(""))
        {
////                        vecReg=new Vector();
////                        vecReg.add(INT_TBL_DAT_LIN,"");
////                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,   "");
////                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,   "");
////                        vecReg.add(INT_TBL_DAT_NOM_ITM,  "");
////                        vecReg.add(INT_TBL_DAT_ORDEN,  "");
////                        vecDat.add(vecReg);   
////        objTblMod.insertRow();   
////        objTblMod.insertRow();   
        }
////        else
            
  

////////////                        vecReg=new Vector();
////////////                        vecReg.add(INT_TBL_DAT_LIN,"");
////////////                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,   "");
////////////                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,   "");
////////////                        vecReg.add(INT_TBL_DAT_BUT_ITM,"");
////////////                        vecReg.add(INT_TBL_DAT_NOM_ITM,  "");
////////////                        vecReg.add(INT_TBL_DAT_ORDEN,  "");
////////////                        vecDat.add(vecReg);


////        if (!txtDescGru.getText().equalsIgnoreCase(strFor))
////        {
////            if (txtDescGru.getText().equals(""))
////            {
////                txtIdGrupo.setText("");
////                txtDescGru.setText("");
////            }
////            else
////            {
////                mostrarVenConFor(2);
////            }
////        }
////        else
////        {
////            txtDescGru.setText(strFor);
////        }
    }//GEN-LAST:event_txtDescGruFocusLost

    private void txtDescGruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescGruActionPerformed
        txtDescGru.transferFocus();
    }//GEN-LAST:event_txtDescGruActionPerformed

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

    private void butElimiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butElimiActionPerformed
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.Connection conExt1;
        try
        {
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conExt1.setAutoCommit(false);
                     if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
                     {
                         
                         if(borrar_tbmCabGrupo(conExt1,0))
                         {
                             conExt1.commit();
                             mostrarMsgInf("La operación ELIMINAR se realizó con éxito.");
                         }
                         else
                         {
                             conExt1.rollback();
                            mostrarMsgInf("La operación CREAR se NO realizó con éxito."); 
                         }
                          
                     } 
                     else
                         mostrarMsgInf("La operación ELIMINAR se cancelo con éxito.");
        }
         catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

    }//GEN-LAST:event_butElimiActionPerformed

    private void butLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpActionPerformed

    LLenarTabla();
    }//GEN-LAST:event_butLimpActionPerformed
    
   public void cargaTexto(String sql,int Codcli){
        strSQL = sql;
        CodCli = Codcli;
        cargarDetReg();
    }
    
       
    
    
    private void exitForm() 
    {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCons;
    private javax.swing.JButton butElimi;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butLimp;
    private javax.swing.JButton butLimpiar;
    private javax.swing.JLabel lblMsgInf;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPlaCtaEmp;
    private javax.swing.JLabel lblPlaCtaFor;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFiltros;
    private javax.swing.JPanel panTablas;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatEmp;
    private javax.swing.JScrollPane spnDatFor;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatFor;
    private javax.swing.JTable tblDatGrupo;
    private javax.swing.JTextField txtDescGru;
    private javax.swing.JTextField txtIdGrupo;
    // End of variables declaration//GEN-END:variables
     
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
                    strSQL+=" SELECT rtrim(det.tx_codItm) as cod_itm , RTRIM(iv.ITMGEDSC) as tx_codalt2 , RTRIM(iv.ITEMDESC) as item,det.co_reg";
                    strSQL+=" FROM tbm_detGrpInv as det ";
                    strSQL+="  inner join tbm_cabGrpInv as cab on (cab.co_grpInv = det.co_grpInv) \n ";
                    strSQL+="  left join TUVAL..IV00101 as iv on (iv.ITEMNMBR =det.tx_codItm) \n ";
                    strSQL+="  WHERE cab.st_reg ='A' and cab.co_grpInv = "+ txtIdGrupo.getText() +" \n";
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
                        vecReg.add(INT_TBL_DAT_ORDEN,    rst.getString("co_reg"));
                         
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
            strSQL="";
            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, ";
            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado";
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 ";
            strSQL+="       left join(";
            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm ";
            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01"; 
            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)";
            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";
            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)";
            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0"; 
            
                    if (!optTod.isSelected())
                    {
                        if (objVenConItm01.isCondicionAplicada())
                            strSQL+=  objVenConItm01.getCondicionesSQL();
                    }
                    
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatItems.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegItems=new Vector();
                        vecRegItems.add(INT_TBL_CON_LIN,"");
                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE,    rst.getString("cod_itm"));
                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM,    rst.getString("tx_codalt2"));
                        vecRegItems.add(INT_TBL_CON_NOM_ITM,    rst.getString("item"));
                        vecRegItems.add(INT_TBL_CON_ORDEN, rst.getString("estado"));
                         
                        vecDatItems.add(vecRegItems);
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
                    objTblModItems.setData(vecDatItems);
                    tblDatFor.setModel(objTblModItems);
                    vecDatItems.clear();
                    
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
    
//////           private boolean configurarTBLDAT1()
//////    {
//////        boolean blnRes=true;
//////        try
//////        {
//////            vecDat=new Vector();    //Almacena los datos
//////            vecCab=new Vector();    //Almacena las cabeceras
//////            vecCab.clear();
//////            vecCab.add(INT_TBL_DAT_LIN,"");
//////            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Código");
//////            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "3L");
//////            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
//////            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
//////            vecCab.add(INT_TBL_DAT_ORDEN, "Orden");
//////            //Configurar JTable: Establecer el modelo de la tabla.
//////            objTblMod=new ZafTblMod();
//////            objTblMod.setHeader(vecCab);
//////            
//////            //Configurar JTable: Establecer el modelo de la tabla.
//////            tblDat.setModel(objTblMod);
//////            
//////            //Configurar JTable: Editor de la tabla.
//////            objTblEdi=new ZafTblEdi(tblDat);
//////            
//////            //Configurar JTable: Establecer tipo de selección.
//////            tblDat.setRowSelectionAllowed(true);
//////            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//////            
//////            //Configurar JTable: Establecer la fila de cabecera.
//////            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
//////            //Configurar JTable: Establecer el menú de contexto.
//////            objTblPopMnu=new ZafTblPopMnu(tblDat);
//////            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//////            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//////            
//////            //Configurar JTable: Establecer el ancho de las columnas.
//////            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(50);
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(10);
//////            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(400); 
//////            tcmAux.getColumn(INT_TBL_DAT_ORDEN).setPreferredWidth(30);
//////            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
//////            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//////            tblDat.getTableHeader().setReorderingAllowed(false);
//////            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//////            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//////            
//////            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
//////////////            objTblCelRenLblNum=new ZafTblCelRenLbl();
//////////////            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//////////////            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
//////////////            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//////////////            
//////////////            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLblNum);
//////////////            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setCellRenderer(objTblCelRenLblNum);
//////////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setCellRenderer(objTblCelRenLblNum);
//////////////            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setCellRenderer(objTblCelRenLblNum);  // JoséMarín: NO se lo esta mostrando, se deja configurado por si deciden mostrar
//////            
//////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLbl);
//////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
//////            objTblCelRenLbl=null;
//////            
//////            //Configurar JTable: Establecer columnas editables.
//////            vecAux=new Vector();
//////            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
//////            vecAux.add("" + INT_TBL_DAT_LIN);
//////            vecAux.add("" + INT_TBL_DAT_COD_ITM_MAE);
//////            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
//////            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
//////            vecAux.add("" + INT_TBL_DAT_NOM_ITM);
//////            vecAux.add("" + INT_TBL_DAT_ORDEN);
//////            objTblMod.setColumnasEditables(vecAux);
//////            vecAux=null;
//////             //columnas ocultas
//////////////            ArrayList arlColHid=new ArrayList();
//////////////            arlColHid.add(""+INT_TBL_DAT_COD_ITM);
//////////////            arlColHid.add(""+INT_TBL_DAT_CAN_ACT_STK);  // 
//////////////            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
//////////////            //objTblMod.setc setColumnDataType(ERROR, WIDTH, WIDTH, WIDTH);
//////////////            
//////////////            arlColHid=null;
//////////////            
//////////////            //botones agregados, hacer una columna de botones 
//////////////            objTblCelRenBut=new ZafTblCelRenBut();
//////////////            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellRenderer(objTblCelRenBut);
//////////////            objTblCelRenBut=null;
//////////////            
//////            //setEditable(false);
//////            
//////            //new
//////////////            int intColVen[]=new int[7];
//////////////            intColVen[0]=1; //co_itm
//////////////            intColVen[1]=2; //tx_codAlt
//////////////            intColVen[2]=3; //tx_codAlt2
//////////////            intColVen[3]=4; //tx_nomItm
//////////////            intColVen[4]=5; //tx_desCor
//////////////            intColVen[5]=6; //nd_pesItmKgr
//////////////            intColVen[6]=7; //nd_canDis
//////////////            int intColTbl[]=new int[7];
//////////////            intColTbl[0]=INT_TBL_DAT_COD_ITM;
//////////////            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
//////////////            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM_DOS;
//////////////            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
//////////////            intColTbl[4]=INT_TBL_DAT_UNI_MED;
//////////////            intColTbl[5]=INT_TBL_DAT_PES_UNI_ITM;
//////////////            intColTbl[6]=INT_TBL_DAT_CAN_ACT_STK;
//////////////            //para que la columna de cantidad no sea negativo
//////////////            objTblMod.setColumnDataType(INT_TBL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
//////            
//////            //Cod.Alt.
//////////////            objTblCelEdiTxtVcoCodAltItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
//////////////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoCodAltItm);
//////////////            objTblCelEdiTxtVcoCodAltItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//////////////            {  
//////////////                @Override
//////////////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//////////////                {
//////////////                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
//////////////                    {
//////////////                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
//////////////                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
//////////////                        txtCodBodOrg.requestFocus();      
//////////////                    }
//////////////                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
//////////////                    {
//////////////                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
//////////////                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
//////////////                        txtCodBodDes.requestFocus();
//////////////                    }
//////////////                }
//////////////                
//////////////                @Override
//////////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//////////////                {
//////////////                     int intCelSel = tblDat.getSelectedRow();
//////////////                     if(!objTblMod.isRowEmpty(intCelSel)){
//////////////                        if (objTblCelEdiTxtVcoCodAltItm.isConsultaAceptada()) {
//////////////                            if (!existeMismoItem(intCelSel)) { /*JM*/
//////////////                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
//////////////                            }
//////////////                        }
//////////////                    }
//////////////                }
//////////////            });
//////            
//////////////            //Boton ítem.
//////////////            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
//////////////            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellEditor(objTblCelEdiButVcoItm);
//////////////            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//////////////            {
//////////////                @Override
//////////////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//////////////                {
//////////////                }   
//////////////                @Override
//////////////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////////                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
//////////////                    {
//////////////                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
//////////////                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
//////////////                        limpiarTabla();
//////////////                        txtCodBodOrg.requestFocus();
//////////////                    }
//////////////                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
//////////////                    {
//////////////                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
//////////////                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
//////////////                        limpiarTabla();
//////////////                        txtCodBodDes.requestFocus();
//////////////                    }
//////////////                }
//////////////                
//////////////                @Override
//////////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//////////////                {
//////////////                     int intCelSel = tblDat.getSelectedRow();
//////////////                     if(!objTblMod.isRowEmpty(intCelSel)){
//////////////                        if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
//////////////                            if (!existeMismoItem(intCelSel)) { /*JM*/
//////////////                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
//////////////                            }
//////////////                        }
//////////////                    }
//////////////                }
//////////////            });
//////                
//////////////            intColVen=null;
//////////////            intColTbl=null;
//////////////            
//////            //Cantidad
//////////////            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
//////////////            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
//////////////            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//////////////            {
//////////////                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
//////////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//////////////                {
//////////////                    int intCelSel = tblDat.getSelectedRow();
//////////////                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))>0){
//////////////                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))<=
//////////////                            (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ACT_STK))))
//////////////                        {
//////////////                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN));
//////////////                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
//////////////                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesMostrar());
//////////////                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
//////////////                            recalcularPeso();
//////////////                            //objTblMod.insertRow();
//////////////                            objTblEdi.seleccionarCelda(tblDat.getSelectedRow()+1, INT_TBL_DAT_COD_ALT_ITM);
//////////////                        }
//////////////                        else
//////////////                        {
//////////////                            mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
//////////////                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN);
//////////////                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
//////////////                        }
//////////////                    }
//////////////                                         
//////////////                }
//////////////            });
//////            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
//////            objDocLis=new ZafDocLis();
//////            
//////            // Configurar JTable: Detectar cambios de valores en las celdas.
//////            objTblModLis=new ZafTblModLis();
//////            objTblMod.addTableModelListener(objTblModLis);  
//////            
//////        }
//////        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
//////        return blnRes;
//////    }
       
        
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
            
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItmGP01(objParSis, "iv01.", optFil);
            panFiltros.add(objVenConItm01);
            //objVenConItm01.setBounds(484, 60, 656, 20);   
////            objVenConItm01.setBounds(18, 44, 656, 20);
            objVenConItm01.setBounds(18, 44, 500, 20);            
            
            
            
            configurarVenConItm();
            configurarTblDat();

////            configurarTBLDAT1();

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
    
    
    
    
    
    
//    private boolean configurarTblDat()//nuevo
//    {
//        boolean blnRes=true;
//        try
//        {
//            vecDat=new Vector();    //Almacena los datos
//            vecCab=new Vector();    //Almacena las cabeceras
//            vecCab.clear();
//            vecCab.add(INT_TBL_DAT_LIN,"");
//            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Código");
//            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "3L");
//            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
//            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
//            vecCab.add(INT_TBL_DAT_ORDEN, "Orden");
//            objTblMod=new ZafTblMod();
//            objTblMod.setHeader(vecCab);
//            
//            //Configurar JTable: Establecer el modelo de la tabla.
//            tblDat.setModel(objTblMod);
//            
//            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
//            
//            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setRowSelectionAllowed(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//            
//            //Configurar JTable: Establecer la fila de cabecera.
//            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
//            //Configurar JTable: Establecer el menú de contexto.
//            objTblPopMnu=new ZafTblPopMnu(tblDat);
//            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//            
//            //Configurar JTable: Establecer el ancho de las columnas.
//            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(50);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(10);
//            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(400); 
//            tcmAux.getColumn(INT_TBL_DAT_ORDEN).setPreferredWidth(30);
//            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
//            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//            tblDat.getTableHeader().setReorderingAllowed(false);
//            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAda=new ZafMouMotAda();
//            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//            
//            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
////////////            objTblCelRenLblNum=new ZafTblCelRenLbl();
////////////            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////////////            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
////////////            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//            
////////////            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLblNum);
////////////            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setCellRenderer(objTblCelRenLblNum);
////////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setCellRenderer(objTblCelRenLblNum);
////////////            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setCellRenderer(objTblCelRenLblNum);  // JoséMarín: NO se lo esta mostrando, se deja configurado por si deciden mostrar
//            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
//            
//            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
//            vecAux.add("" + INT_TBL_DAT_COD_ITM_MAE);
//            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
//            vecAux.add("" + INT_TBL_DAT_ORDEN);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
//             //columnas ocultas
////////////            ArrayList arlColHid=new ArrayList();
////////////            arlColHid.add(""+INT_TBL_DAT_COD_ITM);
////////////            arlColHid.add(""+INT_TBL_DAT_CAN_ACT_STK);  // 
////////////            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
//            //objTblMod.setc setColumnDataType(ERROR, WIDTH, WIDTH, WIDTH);
//            
////////////            arlColHid=null;
//            
//            //botones agregados, hacer una columna de botones 
//            objTblCelRenBut=new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
//            objTblCelRenBut=null;
//            
//            //setEditable(false);
//            
//            //new
//            int intColVen[]=new int[7];
//            intColVen[0]=1; //co_itm
//            intColVen[1]=2; //tx_codAlt
//            intColVen[2]=3; //tx_codAlt2
//            intColVen[3]=4; //tx_nomItm
//            intColVen[4]=5; //tx_desCor
//////            intColVen[5]=6; //nd_pesItmKgr
//////            intColVen[6]=7; //nd_canDis
//            int intColTbl[]=new int[7];
//            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
//            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
//            intColTbl[2]=INT_TBL_DAT_BUT_ITM;
//            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
//            intColTbl[4]=INT_TBL_DAT_ORDEN;
//            //para que la columna de cantidad no sea negativo
//            objTblMod.setColumnDataType(INT_TBL_DAT_ORDEN, objTblMod.INT_COL_DBL, new Integer(0), null);
//            
//            //Cod.Alt.
//            objTblCelEdiTxtVcoLet=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoLet);
//            objTblCelEdiTxtVcoLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//            {  
//                @Override
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//                {
////////////                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
////////////                    {
////////////                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
////////////                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
////////////                        txtCodBodOrg.requestFocus();      
////////////                    }
////////////                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
////////////                    {
////////////                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
////////////                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
////////////                        txtCodBodDes.requestFocus();
////////////                    }
//                }
//                
//                @Override
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//                {
//                     int intCelSel = tblDat.getSelectedRow();
//                     if(!objTblMod.isRowEmpty(intCelSel)){
//                        if (objTblCelEdiTxtVcoLet.isConsultaAceptada()) {
//                            if (!existeMismoItem(intCelSel)) { /*JM*/
//                                objTblEdi.seleccionarCelda(INT_TBL_DAT_ORDEN);
//                            }
//                        }
//                    }
//                }
//            });
//            
//            //Boton ítem.
//            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
//            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//            {
//                @Override
//                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//                {
//                     configurarTblDat();
//                }   
//                @Override
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                     configurarTblDat();
////////////                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
////////////                    {
////////////                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
////////////                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
////////////                        limpiarTabla();
////////////                        txtCodBodOrg.requestFocus();
////////////                    }
////////////                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
////////////                    {
////////////                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
////////////                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
////////////                        limpiarTabla();
////////////                        txtCodBodDes.requestFocus();
////////////                    }
//                }
//                
//                @Override
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
//                {
//                     int intCelSel = tblDat.getSelectedRow();
//                     if(!objTblMod.isRowEmpty(intCelSel)){
//                        if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
//                            if (!existeMismoItem(intCelSel)) { /*JM*/
//                                objTblEdi.seleccionarCelda(INT_TBL_DAT_ORDEN);
//                            }
//                        }
//                    }
//                }
//            });
//                
//            intColVen=null;
//            intColTbl=null;
//            
//            //Cantidad
////////////            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
////////////            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
////////////            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
////////////            {
////////////                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
////////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
////////////                {
////////////                    int intCelSel = tblDat.getSelectedRow();
////////////                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_ORDEN))>0){
////////////                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_ORDEN))<=
////////////                            (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ACT_STK))))
////////////                        {
////////////                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_ORDEN));
////////////                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
////////////                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesMostrar());
////////////                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
////////////                            recalcularPeso();
////////////                            //objTblMod.insertRow();
////////////                            objTblEdi.seleccionarCelda(tblDat.getSelectedRow()+1, INT_TBL_DAT_COD_ALT_ITM);
////////////                        }
////////////                        else
////////////                        {
////////////                            mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
////////////                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN);
////////////                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
////////////                        }
////////////                    }
//                                         
////////////                }
////////////            });
//            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
//            objDocLis=new ZafDocLis();
//            
//            // Configurar JTable: Detectar cambios de valores en las celdas.
//            objTblModLis=new ZafTblModLis();
//            objTblMod.addTableModelListener(objTblModLis);  
//            
//        }
//        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
//        return blnRes;
//    }
//    
    
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
            lblMsgInf.setVisible(false);
            lblMsgSis.setVisible(false);
            objMouMotAda=new ZafMouMotAda();
            tblDatGrupo.getTableHeader().addMouseMotionListener(objMouMotAda);   
            
            
            
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

                        //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop)._antiguo">
////            tblDatGrupo.setDragEnabled(true);
////                        tblDatGrupo.setDropMode(DropMode.INSERT_ROWS); 
////            tblDatFor.setDropTarget(new DropTarget()
////            {
////                @Override  
////                public synchronized void drop(DropTargetDropEvent dtde) 
////                {
////////                    Filas a Mover.
////                    int intFilMovGrupo[];
////                    intFilMovGrupo = tblDatGrupo.getSelectedRows();
////                    int totalFilas=0;
////                    totalFilas =objTblMod.getRowCountTrue()-1;
////                    
////                    //Filas Seleccionadas.
////                    int intFilSel[];
////                    intFilSel = tblDatGrupo.getSelectedRows();
////                    
////                    Point point = dtde.getLocation();
////                    
////                    int intRowEmpOrigen = tblDatGrupo.getSelectedRow();
////                    //System.out.println("intRowEmpOrigen: " + intRowEmpOrigen);
////                    
////                    int intRowGrupoPtoDestino = tblDatGrupo.rowAtPoint(point);
////                    //System.out.println("intRowForPtoDestino: " + intRowForPtoDestino);
////                    
////                    int intRowForDestino = tblDatFor.getSelectedRow();
////                    //System.out.println("intRowForDestino: " + intRowForDestino);
////                    
////                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
////                    
////                    boolean blnAdd=true;
////                    boolean blnValCtaDet=false;
////                    boolean blnValCtaExiFor=false;
////////                    if (intRowEmpOrigen > -1 && intRowForDestino == -1 && intRowGrupoPtoDestino>0) //TRASLADAR CUENTAS A OTRO JTABLE.
////////                    {
////                        
////////                        if (intRowForDestino > -1 && intRowEmpOrigen > -1 && intRowGrupoPtoDestino > -1) 
////////                        {
////////                            mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////////    ////                        limpiarJtableSelection();
////////                        }
////////                        else if ( intRowGrupoPtoDestino <=0 ) 
////////                        {
////////                            mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
////////    ////                        limpiarJtableSelection();
////////                        }  
////////                        else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
////////                        {
////                            boolean blnMov=true;
////                            for (int i = 0; i < intFilMovGrupo.length; i++) 
////                            {
////////                                if (intFilMovFor[i] < 0)
////////                                {
////                                    blnMov=true;
////////                                }
////                            }
////                            if(blnMov)
////                            {
////                                //System.out.println("MOVER-FOR!!");
////                                //System.out.println("intFilMovFor: " + intFilMovFor + " intRowForPtoDestino: " + intRowForPtoDestino);
////                                //objTblModFor.moveRow(intRowForDestino, intRowForPtoDestino);
//////                                if (totalFilas ==intRowGrupoPtoDestino)
////                                        objTblMod.moveRows(intFilMovGrupo, intRowGrupoPtoDestino);
////////                                    else
////////                                        objTblMod.moveRows(intFilMovGrupo, intRowGrupoPtoDestino+1);
////
////                            }
////                            else
////                            {
////                                mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
////    //                            limpiarJtableSelection();
////                            }
////////                        }                        
////    
////////                    }                    
////                }
////            });
                        //</editor-fold>  
                        
////////////////////////////////////////////////            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
         
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
////                                strIsCtaDet = objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_EST_DET).toString();
////                                strCodCtaPlaEmp = objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_COD_CTA).toString();
////                                strTipRegLin = objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_LIN).toString();
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
////////                            limpiarJtableSelection();
////                        }
                        if (blnValCtaExiFor) {
                            mostrarMsgInf("Alguna(s) de las cuenta(s) ya existe(n) en el Plan Cuenta Formato.");
////                            limpiarJtableSelection();
                        }
                        
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
                                    vecReg.add(INT_TBL_CON_LIN, strTipRegLin.equals("E") ? "C" : "N");
                                    vecReg.add(INT_TBL_CON_COD_ITM_MAE, objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ITM_MAE));
                                    vecReg.add(INT_TBL_CON_COD_ALT_ITM, objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ALT_ITM));
                                    vecReg.add(INT_TBL_CON_NOM_ITM, objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_NOM_ITM));
////                                    vecReg.add(INT_TBL_CON_ORDEN, objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_ORDEN));
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
                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
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
            objTblModItems.setColumnasEditables(vecAux);
            vecAux=null;
            
////            //Configurar JTable: Ocultar columnas del sistema.
////            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_ORDEN, tblDatFor);     
            
            
            objMouMotAdaItems=new ZafMouMotAdaItems();
            tblDatFor.getTableHeader().addMouseMotionListener(objMouMotAdaItems);     
            
            
            
     
            
            
 
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

////                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 && intRowEmpPtoDestino>=0)
                    if (intRowForOrigen > -1 &&  intRowEmpPtoDestino>=0)                        
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
////                        for(int j=0 ; j<intFilSel.length;j++)
////                        {
////                            if (intFilSel[j] > -1 && intRowEmpDestino == -1) 
////                            {
////                                strIsCtaCab = objTblModItems.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB).toString();
////                                strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();
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
////////                            limpiarJtableSelection();
////                        }
                        
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        if(blnAdd)
                        {
                            String itemAcopiar ="";  
                            boolean blnRepe=false;

                            if (estado !='C')
                            {
                                //VALIDACION SI EXISTE UN REPETIDO
                                for (int k = 0; k < intFilSel.length; k++) 
                                {
                                        itemAcopiar = tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_COD_ITM_MAE).toString();
                                        if (isExisteItemRepetido(1,itemAcopiar))
                                        {
                                            blnRepe=true;
                                            break;
                                        }
                                }  
                            }
                            if(!blnRepe)
                            {
//AGREGAR ELEMENTOS A TBLDATEMP                                
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    //if (intFilSel[j] > -1 && intRowEmpDestino == -1) // TENGA VALORES 
                                    if (intFilSel[j] > -1) // TENGA VALORES                                         
                                    {

                                        strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_CON_LIN).toString();
                                        vecReg = new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN, strTipRegLin.equals("C") ? "E" : "N");
                                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ITM_MAE));
                                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ALT_ITM));
                                        vecReg.add(INT_TBL_DAT_NOM_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_NOM_ITM));
////                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
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
////                                            objTblModItems.setValueAt("M", intFilSel[i], INT_TBL_CON_ORDEN);
////                                            objTblModItems.setValueAt("M", intFilSel[i], INT_TBL_CON_NOM_ITM);
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
////                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
                                        
                                        
                                        
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
                    else if (intRowEmpDestino > -1 && intRowEmpPtoDestino > -1 && intRowForOrigen > -1) 
                    {
                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////                        limpiarJtableSelection();
                    }
//////                    else if ( intRowEmpPtoDestino <=0 ) 
//////                    {
//////                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
//////////                        limpiarJtableSelection();
//////                    }
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
                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
////                            limpiarJtableSelection();
                        }
                    }    
                }        
            });          
            
            
            //</editor-fold>
            
// //Configurar JTable: Renderizar celdas.
            objTblCelRenLblItems=new ZafTblCelRenLbl();
            objTblCelRenLblItems.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_COD_ALT_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_NOM_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_ORDEN).setCellRenderer(objTblCelRenLblItems);
            objTblCelRenLblItems.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblItems.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("M")){
                        objTblCelRenLblItems.setBackground(Color.RED);
                    }
                    else{
                        if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("I")){
                            objTblCelRenLblItems.setBackground(Color.BLUE);
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
    
       
    
       
    private boolean isExisteItemRepetido()
    {
        boolean blnRes=false;
        try{
            if (objTblMod.getRowCountTrue() <1)
            {
                blnRes=true;
                mensajeError ="<HTML>No se encuentra ningun registro ingresado</HTML>";                
            }

            else
            {
                
                
                
////                for (int i=0; i<objTblMod.getRowCountTrue(); i++){
////            if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) != null){
////                if (i != intFilSel){
////                    if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString().equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString())){
////                        blnRes = true;
////                        mostrarMsgInf("<HTML>El item ya fue ingresado en la fila " + (i+1) + ".</HTML>");
////                        objTblMod.removeRow(intFilSel);
////                        tblDat.setRowSelectionInterval(intFilSel, intFilSel);
////                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT_ITM);
////                        break;
////                    }
////                }
////            }
////        }
                
                
                String itemA="",itemB="";
                int contador =0;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    contador =0;
                    for(int j=0; j<objTblMod.getRowCountTrue(); j++){
                        
                        itemA = objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE)) ;
                        
                        itemB =objUti.codificar(objTblMod.getValueAt(j,INT_TBL_DAT_COD_ITM_MAE));
                        if (itemA!= "Null")
                        {
                            if (itemA.equals(itemB))
                                 contador =contador +1;
                                                            
                        }
                        if (contador >1)
                        {
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
           
        private boolean isExisteItemRepetido(int tipo,String item)//chiqui
    {
        boolean blnRes=false;
        try{
            String itemCompa="";
            if (tipo==1)
            {
                int k=0;
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
                    strMsg = "Ordenamiento";
                    break;                    
                default:
                    strMsg = "";
                    break;
            }
            tblDatGrupo.getTableHeader().setToolTipText(strMsg);
        }
       }     
    
    
}
