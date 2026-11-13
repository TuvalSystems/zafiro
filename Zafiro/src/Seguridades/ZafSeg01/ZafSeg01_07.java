/*
 *  
 *
 * Created on 16 Sep, 2019, 10:16 PM
 */

package Seguridades.ZafSeg01;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafGetDat.ZafDatMnuUsr;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author  José Marín 
 */
public class ZafSeg01_07 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
     
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
 
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg3;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
   
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
   
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
     
 
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
     
    private String strSql;
    private int intCodCla, intCodItm;
    
    private ZafTblCelEdiChk objTblCelEdiChkPre; 
    private ZafTblCelRenChk objTblCelRenChk;
    
    private boolean blnAcepta=false;
    private String strTitu="Administración de escritorios";
    private String strVersion=" v0.01";
    
    final static int INT_TBL_LINEA =0; 
    final static int INT_TBL_CHKSEL=1; 
    final static int INT_TBL_FECHA=2; 
    final static int INT_TBL_CODPER=3;      
    final static int INT_TBL_DESCOR=4;       
    final static int INT_TBL_DESLAR=5;
    final static int INT_TBL_BTNCOP=6;
    final static int INT_TBL_ESTADO=7;      
    
    
    private ZafDatMnuUsr objDatMnuUsr;    // Nueva libreria Jota 26/Agosto/2019
    
    
    /** Creates new form ZafVeh06 */
    public ZafSeg01_07(ZafParSis obj) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    
    private boolean configurarFrm(){
        boolean blnRes=false;
        try{
            if(configuraTbl()){
                if(cargarDat()){
                    blnRes=true;
                    System.out.println("OK CARGA Administración de escritorios");
                }
            }      
            
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(null, Evt);
            blnAcepta = false;
        }
        return blnRes;
    } 
    
    /**
     * 
     * @return 
     */
    private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CHKSEL,"");
            vecCab.add(INT_TBL_FECHA, "Fecha");
            vecCab.add(INT_TBL_CODPER,"Cód.Esc");
            vecCab.add(INT_TBL_DESCOR,"Nombre");
            vecCab.add(INT_TBL_DESLAR,"Des.Lar.");
            vecCab.add(INT_TBL_BTNCOP,"Copiar");
            vecCab.add(INT_TBL_ESTADO,"Estado");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // PILAS JOTA
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);

            ZafSeg01_07.ZafMouMotAda objMouMotAda=new ZafSeg01_07.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODPER).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DESLAR).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_BTNCOP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(30);
             
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODPER);
            arlColHid.add(""+INT_TBL_ESTADO);
//            arlColHid.add(""+INT_TBL_DESLAR);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHKSEL);
            vecAux.add("" + INT_TBL_DESCOR);
            vecAux.add("" + INT_TBL_DESLAR);
            vecAux.add("" + INT_TBL_BTNCOP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
 
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);    
            objTblCelRenChk = null;
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChkPre);
            
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BTNCOP).setCellRenderer(objTblCelRenBut);           
            objTblCelRenBut=null;
            
            new ButCop(tblDat, INT_TBL_BTNCOP);
            
            ButCop butCop = new ButCop(tblDat, INT_TBL_BTNCOP); //*****
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tblDat.getTableHeader().setReorderingAllowed(false);
          
            blnRes=true;
        }
        catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this,e);  
        }
        return blnRes;
    }
    
    private class ButCop extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButCop(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación.");
           
        }
        @Override
        public void butCLick() {
           llamarVen(tblDat.getSelectedRow());
        }
    }
     
     
    private void llamarVen(int intFil){
        try{
            String strCodEsc = ( tblDat.getValueAt(intFil,  INT_TBL_CODPER  )==null?"":tblDat.getValueAt(intFil,  INT_TBL_CODPER  ).toString());
            String strNomEsc = ( tblDat.getValueAt(intFil,  INT_TBL_DESCOR  )==null?"":tblDat.getValueAt(intFil,  INT_TBL_DESCOR  ).toString());
            ZafSeg01_08 obj1 = new  ZafSeg01_08(objParSis,this, Integer.parseInt(strCodEsc), strNomEsc );
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();  
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(null, Evt);
        }
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
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCargar = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butEliminar = new javax.swing.JButton();
        butCerrar = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.BorderLayout());

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

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCargar.setText("Cargar");
        butCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCargarActionPerformed(evt);
            }
        });
        panBot.add(butCargar);

        butGuardar.setText("Guardar");
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        butEliminar.setText("Eliminar");
        butEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarActionPerformed(evt);
            }
        });
        panBot.add(butEliminar);

        butCerrar.setText("Cerrar");
        butCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrarActionPerformed(evt);
            }
        });
        panBot.add(butCerrar);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
         
              
}//GEN-LAST:event_exitForm

    private void butCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrarActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_butCerrarActionPerformed

    private void butCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCargarActionPerformed
        try{
            blnAcepta = false;
            int intNumFil=objTblMod.getRowCountTrue(); 
            if(objTblMod.isCheckedAnyRow(INT_TBL_CHKSEL)){
                for (int i=0; i<intNumFil;i++){
                    if(objTblMod.isChecked(i,INT_TBL_CHKSEL)){
                        if(procesoGrupos( Integer.parseInt(tblDat.getValueAt(i,INT_TBL_CODPER).toString()) )){
                            blnAcepta=true;
                            this.setVisible(false);
                        }else{
                            MensajeInf("Error al momento de cargar el escritorio...");
                        }
                    }
                }
            }
            else{
                MensajeInf("Debe seleccionar un escritorio...");
            }
            
            //dispose();
            
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(null, Evt);
            blnAcepta = false;
        }
    }//GEN-LAST:event_butCargarActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        // TODO add your handling code here:
        if(guardarModificaciones()){
            MensajeInf("Escritorios guardados con exito...");
        }
        else{
            MensajeInf("Error al momento de guardar...");
        }
    }//GEN-LAST:event_butGuardarActionPerformed

    private void butEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarActionPerformed
        // TODO add your handling code here:
        if(objTblMod.isCheckedAnyRow(INT_TBL_CHKSEL)){
            if(guardarEliminaciones()){
                MensajeInf("Escritorios eliminados con exito...");
            }
            else{
                MensajeInf("Error al momento de guardar...");
            }
        } 
        else{
            MensajeInf("Debe seleccionar un escritorio...");
        }
    }//GEN-LAST:event_butEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCargar;
    private javax.swing.JButton butCerrar;
    private javax.swing.JButton butEliminar;
    private javax.swing.JButton butGuardar;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }    

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
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
    
    
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=false;
            dispose();
        }
    }
    
    

    private void MensajeInf(String strMensaje){
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
     
    public boolean acepta(){
      return blnAcepta;
    }
    
    
   

    private class ZafMouMotAda extends MouseMotionAdapter
    {
         @Override
        public void mouseMoved(MouseEvent evt)
        {
        int intCol=tblDat.columnAtPoint(evt.getPoint());
        String strMsg="";
        switch (intCol)
        {
            case INT_TBL_LINEA:
                strMsg="";
            break;
            
             case INT_TBL_FECHA:
                strMsg="Fecha del Escritorio";
            break;
            
            case INT_TBL_CODPER:
                strMsg="Código del Escritorio";
            break;
             
            case INT_TBL_DESCOR:
                strMsg="Nombre del Escritorio";
            break;
            case INT_TBL_DESLAR:
                strMsg="Descripción larga del escritorio";
            break;
             case INT_TBL_BTNCOP:
                strMsg="Copiar escritorio";
            break;
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    
    private boolean cargarDat(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{    
            vecDat = new Vector();
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_usr, a1.co_per , a1.tx_desCor, a1.tx_desLar, CAST(a1.fe_ing as date) as fe_ing, a1.st_reg \n";
                strSql+=" FROM tbm_cabPerUsr as a1  \n";
                strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+"  and a1.co_loc="+objParSis.getCodigoLocal()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+" AND \n";
                strSql+="        a1.st_reg='A' ";
                strSql+=" ORDER BY a1.co_per ";  
                System.out.println("cargarDat " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CHKSEL,"");
                    vecReg.add(INT_TBL_FECHA, rstLoc.getString("fe_ing"));
                    vecReg.add(INT_TBL_CODPER,rstLoc.getString("co_per"));
                    vecReg.add(INT_TBL_DESCOR,rstLoc.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DESLAR,rstLoc.getString("tx_desLar"));
                    vecReg.add(INT_TBL_BTNCOP, "COPIAR");
                    vecReg.add(INT_TBL_ESTADO,rstLoc.getString("st_reg"));
                    vecDat.add(vecReg);
                }
                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);         
                rstLoc.close();
                rstLoc=null;
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
              
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;          
    }   

    
    /**
     * JM: Pendinte debe haber una mejor manera de hacerlo..... this.getParent().getParent().getParent().getParent().getParent()   
     * @param CodEsc
     * @return 
     */
 

    private boolean procesoGrupos(int CodEsc){
        boolean blnRes=false;
        try{
            System.out.println("procesoGrupos...");
            if(isSeleccionado()){
                ZafSeg01_02 obj1 = (ZafSeg01_02) this.getParent().getParent().getParent().getParent().getParent(); // 
                obj1.mostrarEscritorios(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario(), CodEsc);
                blnRes=true;
            }
            else{
                MensajeInf("Seleccione un escritorio...");
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;        
    }
    
    
    
     
     
    
    
     
    
    private boolean datosCompletos(int Row){
        boolean blnRes=true;
        try{
            if(!(objTblMod.getValueAt(Row,INT_TBL_DESCOR).toString().length()>0)){
                blnRes=false;
            }
            if(!(objTblMod.getValueAt(Row,INT_TBL_DESLAR).toString().length()>0)){
                blnRes=false;
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes; 
    }
    
    
    public String CodEmp, CodLoc, CodUsr, CodPer;
    
     
     
    
     
    /**
     * Revisa si hay algo seleccionado
     * @return 
     */
    
    private boolean isSeleccionado(){
        boolean blnRes=false;
        int intCont=0;
        try{
            for(int i=0; i<tblDat.getRowCount(); i++){
               if(objTblMod.isChecked(i,INT_TBL_CHKSEL)){
                     blnRes=true;
                }
            }
            for(int i=0; i<tblDat.getRowCount(); i++){
               if(objTblMod.isChecked(i,INT_TBL_CHKSEL)){
                    intCont++;
                }
            }
            if(intCont!=1){
                blnRes=false;
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;     
    }
    
    
   private boolean guardarEliminaciones(){
       boolean blnRes=true;
        java.sql.Connection conLoc;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            objDatMnuUsr = new ZafDatMnuUsr(objParSis,this);
            for (int i=0; i<intNumFil;i++){
                if (objTblMod.isChecked(i,INT_TBL_CHKSEL)){
                     conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if(conLoc!=null){
                            conLoc.setAutoCommit(false);
                            if(objDatMnuUsr.eliminarEscritorio(conLoc, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario(),
                                                                Integer.parseInt(tblDat.getValueAt(i,INT_TBL_CODPER).toString()) )){
                                conLoc.commit();
                            }
                            else{
                                conLoc.rollback();
                            }
                            conLoc.close();
                            conLoc=null;
                        }
                }
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes; 
   }
    
    
    
    private boolean guardarModificaciones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            objDatMnuUsr = new ZafDatMnuUsr(objParSis,this);
            for (int i=0; i<intNumFil;i++){
                if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_LINEA)).equals("M")){
                    conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(conLoc!=null){
                        conLoc.setAutoCommit(false);
                        if(objDatMnuUsr.modificarEscritorio(conLoc, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario(),
                                                            Integer.parseInt(tblDat.getValueAt(i,INT_TBL_CODPER).toString()), 
                                                            tblDat.getValueAt(i,INT_TBL_DESCOR).toString(), tblDat.getValueAt(i,INT_TBL_DESLAR).toString() )){
                            conLoc.commit();
                        }
                        else{
                            conLoc.rollback();
                        }
                        conLoc.close();
                        conLoc=null;
                    }
                }
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes; 
    }
    
    
    
    
    
}
