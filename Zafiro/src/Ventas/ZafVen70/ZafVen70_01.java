/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventas.ZafVen70;
 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import static Ventas.ZafVen70.ZafVen70.INT_TBL_DAT_LIN;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Sistemas6
 */
public class ZafVen70_01 extends javax.swing.JDialog {
    private java.awt.Frame Frame;
    private String strSql;
    private String strCodBasCal;
    private int codUsrGlo;
    public int intCodTabSel;
    public String strDesCorTabSel;
    
    private ZafUtil objUti;
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModCom;
    
    private boolean blnAcepta=false;
    private String strTitu="Tablas de comisiones";
    private String strVersion=" v0.1";
    
    private ZafTblOrd objTblOrd;
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CHK_SEL=1;    // Tabla seleccionada
    final int INT_TBL_CODTBL=2;      //CODIGO DEL ITEM 
    final int INT_TBL_DESCOR=3;      //CODIGO DE LA EMPRESA
    final int INT_TBL_DESLAR=4;      //CODIGO DE LA BODEGA
    final int INT_TBL_OBS2=5;      //NOMBRE DE LA EMPRESA
    
    final int INT_TBL_DET_LINEA =0; 
    final int INT_TBL_DET_CODTBL=1;    // Tabla seleccionada
    final int INT_TBL_DET_CODREG=2;      //CODIGO DEL ITEM 
    final int INT_TBL_DET_VALMIN=3;      //CODIGO DE LA EMPRESA
    final int INT_TBL_DET_VALMAX=4;      //CODIGO DE LA BODEGA
    final int INT_TBL_DET_PORCOM=5;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_DET_VALFIJ=6;      //NOMBRE DE LA EMPRESA
    
    
    
    /**
     * Creates new form ZafMaeAA_01
     */
    public ZafVen70_01(java.awt.Frame parent, ZafParSis ZafParSis, boolean modal,  int CodBasCal,int codUsr) {
        super(parent, modal);
        Frame=parent;
        this.objParSis = ZafParSis;
        objUti = new ZafUtil();
        codUsrGlo=codUsr;
        
        if(CodBasCal==1)
        {
            //strCodBasCal=CodBasCal+"";
            strCodBasCal="1";
        }
        else
        {
            if(CodBasCal==2)
                strCodBasCal="2 ";
            else
            {
                if(CodBasCal==3)
                    strCodBasCal="5";
                else
                {
                    if(CodBasCal==4)
                        strCodBasCal="6";
                    else
                    {
                        if(CodBasCal==5)
                            strCodBasCal="7";  
                        else
                        {
                            if(CodBasCal==6)
                                strCodBasCal="8"; 
                            else
                            {
                                if(CodBasCal==7)
                                    strCodBasCal="9";  
                                else
                                {
                                    if(CodBasCal==8)
                                        strCodBasCal="10";  
                                    else
                                    {
                                        if(CodBasCal==9)
                                            strCodBasCal="11";                                          
                                        else
                                        {
                                            if(CodBasCal==10)
                                                strCodBasCal="12";      
                                            else
                                            {
                                                if(CodBasCal==11)
                                                    strCodBasCal="13";   
                                                else
                                                {
                                                    if(CodBasCal==12)
                                                        strCodBasCal="14";
                                                    else
                                                    {
                                                        if(CodBasCal==13)
                                                            strCodBasCal="15";                                                        
                                                    }
                                                        
                                                }
                                            }
                                                    
                                        }
                                    }                                        
                                }
                            }
                                   
                        }
                    }
                }
                    
            }
        }
        
        
        initComponents();
        this.setTitle( strTitu );
        lblTit.setText( strTitu + strVersion);  // JoséMario 
        configurarFrm();       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCom = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panTit.setPreferredSize(new java.awt.Dimension(528, 48));

        lblTit.setText("titulo");

        javax.swing.GroupLayout panTitLayout = new javax.swing.GroupLayout(panTit);
        panTit.setLayout(panTitLayout);
        panTitLayout.setHorizontalGroup(
            panTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
            .addGroup(panTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panTitLayout.createSequentialGroup()
                    .addGap(0, 227, Short.MAX_VALUE)
                    .addComponent(lblTit, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 226, Short.MAX_VALUE)))
        );
        panTitLayout.setVerticalGroup(
            panTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
            .addGroup(panTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panTitLayout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(lblTit)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(546, 300));
        panDat.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        panDat.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panFrm.add(panDat, java.awt.BorderLayout.CENTER);

        panTab.setPreferredSize(new java.awt.Dimension(546, 100));
        panTab.setLayout(new java.awt.BorderLayout());

        tblCom.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblCom);

        panTab.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panFrm.add(panTab, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setPreferredSize(new java.awt.Dimension(0, 52));
        panBut.setLayout(new java.awt.BorderLayout());

        panSubBot.setPreferredSize(new java.awt.Dimension(100, 35));
        panSubBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText(" Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed

        try{
            blnAcepta = false;
            if(isSeleccionado()){
                blnAcepta=true;
            }else{
                MensajeInf("Error al momento de guardar...");
            }

            
            this.setVisible(false);
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(null, Evt);
            blnAcepta = false;
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTab;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTable tblCom;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables


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

    private void configurarFrm(){
        try{
            if(configuraTbl()){
                if(configuraTblCom()){
                    if(cargarDat()){
                        System.out.println("OK CARGA ZafMaeAA_01");
                    }
                }
            }      
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(null, Evt);
            blnAcepta = false;
        }
    } 
    
    public boolean acepta(){
      return blnAcepta;
    }
    
    Vector vecCab=new Vector(); 
    
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiChk objTblCelEdiChkPre;            //Editor: JCheckBox en celda.
    private boolean blnPre;
    
    
    private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CHK_SEL, "..");
            vecCab.add(INT_TBL_CODTBL,"Cód.Tbl.");
            vecCab.add(INT_TBL_DESCOR,"Des.Cor.");
            vecCab.add(INT_TBL_DESLAR,"Des.Lar.");
            vecCab.add(INT_TBL_OBS2,"Observación");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // PILAS JOTA
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            ZafVen70_01.ZafMouMotAda objMouMotAda=new ZafVen70_01.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHK_SEL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTBL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DESLAR).setPreferredWidth(500);
            tcmAux.getColumn(INT_TBL_OBS2).setPreferredWidth(250);
             
            
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODTBL);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHK_SEL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
 
            
            
            //Cheks 
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_SEL).setCellRenderer(objTblCelRenChk);  
            
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_CHK_SEL).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHK_SEL))
//                        objTblCelEdiChkPre.setCancelarEdicion(false);
//                    else
//                        objTblCelEdiChkPre.setCancelarEdicion(true);
//                            
//                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHK_SEL)){
                        blnPre=true;
                        quitarPredeterminadoAnterior(tblDat.getSelectedRow());
                        if(!cargarTabla( Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODTBL).toString() ) ) ){
                            System.out.println("ERROR AL CARGAR");
                        }
                    }
                    else
                        blnPre=false;
                }
            });
            
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
             
            
            objTblOrd=new ZafTblOrd(tblDat);
             
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
             
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

           blnRes=true;
	 }
        catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this,e);  
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param intRow
     * @return 
     */
    
    private boolean cargarTabla(int CodTbl){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            vecDat = new Vector();
            //conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_tblCom, a2.co_reg, ROUND( a2.nd_valMinCob, 2) as nd_valMinCob, ROUND(a2.nd_valMaxCob,2) as nd_valMaxCob,  \n";
                strSql+="        ROUND( a2.nd_porCom * 100,3)  as nd_porCom, ROUND( a2.nd_valFij,2) as nd_valFij     \n";
                strSql+=" FROM tbm_cabTblComVen as a1  \n";
                strSql+=" INNER JOIN tbm_detTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)  \n";
                strSql+=" WHERE a1.co_tblCom="+CodTbl+" \n";
                strSql+=" ORDER BY a2.co_reg ASC ";  	 
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DET_LINEA,"");
                    vecReg.add(INT_TBL_DET_CODTBL,rstLoc.getString("co_tblCom"));
                    vecReg.add(INT_TBL_DET_CODREG,rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_DET_VALMIN,rstLoc.getString("nd_valMinCob"));
                    vecReg.add(INT_TBL_DET_VALMAX,rstLoc.getString("nd_valMaxCob"));
                    vecReg.add(INT_TBL_DET_PORCOM,rstLoc.getString("nd_porCom")+"%");
                    vecReg.add(INT_TBL_DET_VALFIJ,rstLoc.getString("nd_valFij"));
                    vecDat.add(vecReg);
                }
                objTblModCom.setData(vecDat);
                tblCom.setModel(objTblModCom);         
                rstLoc.close();
                rstLoc=null;
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this,e);  
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param fila
     * @return 
     */
    
    private boolean quitarPredeterminadoAnterior(int fila){
        boolean blnRes=true;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_CHK_SEL)){
                    if(i!=fila)
                        objTblMod.setChecked(false, i, INT_TBL_CHK_SEL);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private Vector vecDatReg, vecRegReg, vecCabReg, vecAuxReg;
    /**
     * 
     * @return 
     */
    
    private boolean configuraTblCom(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_DET_LINEA,"..");
            vecCab.add(INT_TBL_DET_CODTBL,"Cód.Tbl.");
            vecCab.add(INT_TBL_DET_CODREG,"Cód.Reg.");
            vecCab.add(INT_TBL_DET_VALMIN,"Val.Min.");
            vecCab.add(INT_TBL_DET_VALMAX,"Val.Max.");
            vecCab.add(INT_TBL_DET_PORCOM,"Por.Com.");
            vecCab.add(INT_TBL_DET_VALFIJ,"Val.Fij.");
            objTblModCom=new ZafTblMod();
            objTblModCom.setHeader(vecCab);
            tblCom.setModel(objTblModCom);
            
           
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
   

	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblCom.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_DET_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DET_CODTBL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_CODREG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_VALMIN).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DET_VALMAX).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DET_PORCOM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DET_VALFIJ).setPreferredWidth(100);
            
            objTblFilCab=new ZafTblFilCab(tblCom);
            tcmAux.getColumn(INT_TBL_DET_LINEA).setCellRenderer(objTblFilCab);
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DET_CODTBL);
            arlColHid.add(""+INT_TBL_DET_CODREG);
            objTblModCom.setSystemHiddenColumns(arlColHid, tblCom);
            arlColHid=null;
            
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_TBL_DET_VALMIN).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DET_VALMAX).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DET_PORCOM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_VALFIJ).setCellRenderer(objTblCelRenLbl);          
            objTblCelRenLbl = null;
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAuxReg=new Vector();
            vecAuxReg.add("" + INT_TBL_DET_VALMIN);
            vecAuxReg.add("" + INT_TBL_DET_VALMAX);
            vecAuxReg.add("" + INT_TBL_DET_PORCOM);
            vecAuxReg.add("" + INT_TBL_DET_VALFIJ);
            objTblModCom.setColumnasEditables(vecAuxReg);
            vecAuxReg=null;

            
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DET_PORCOM).setCellRenderer(objTblCelRenLbl);        
            objTblCelRenLbl = null;
             
            tblCom.getTableHeader().setReorderingAllowed(false);
            
            objTblModCom.setModoOperacion(objTblModCom.INT_TBL_EDI);
          
           blnRes=true;
	 }
        catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this,e);  
        }
        return blnRes;
    }
    
    
    private ZafTblCelRenChk objTblCelRenChk;
    private Vector vecDat, vecReg;
    
    
    /**
     * 
     * @return 
     */
    
    private boolean cargarDat()
{
    boolean blnRes = false;
    java.sql.Connection conLoc = null;
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;
    boolean blnHayConfiguracion = false;

    try
    {
        vecDat = new Vector();

        conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conLoc != null)
        {
            stmLoc = conLoc.createStatement();

            // =========================================================
            // 1. QUERY PRINCIPAL: configuraciones del usuario
            // =========================================================
            strSql = "";
            //strSql+=" SELECT pre.co_usrven,cab.co_tblcom,cab.tx_descor,cab.tx_deslar, \n";
            strSql+=" SELECT distinct(cab.co_tblcom) as co_tblcom,cab.tx_descor,cab.tx_deslar, \n";            
            strSql+="        (CAST(cab.co_tblcom AS VARCHAR(20)) + '-' + ISNULL(cab.tx_desLar,'')) AS desCom, \n";                
            strSql+="        cab.tx_obs2, usr.co_tipven,bas.tx_deslar,bas.tx_descor, bas.co_bascal \n";                   
            strSql+=" FROM Dynamics2021..tbm_preasitblcomvenusrven as pre  \n";
            strSql+="      INNER JOIN Dynamics2021..tbm_usr as usr on (pre.co_usrven = usr.co_usr )   \n";
            strSql+="      INNER JOIN Dynamics2021..tbm_cabtblcomven as cab on (pre.co_tblcom = cab.co_tblcom  AND cab.st_reg ='A')  \n";
            strSql+="      INNER JOIN Dynamics2021..tbm_bascalcomven as bas on (cab.co_bascal = bas.co_bascal)  \n";                
            //strSql+=" WHERE  pre.st_Reg !='E' and bas.co_basCal IN ("+strCodBasCal+") AND usr.co_usr ="+ codUsrGlo +"  \n";
            strSql+=" WHERE  pre.st_Reg !='E' and bas.co_basCal IN ("+strCodBasCal+")  \n";
            strSql += "   AND usr.co_tipven = ( \n";
            strSql += "       SELECT co_tipven \n";
            strSql += "       FROM Dynamics2021..tbm_usr \n";
            strSql += "       WHERE co_usr = " + codUsrGlo + " )\n";            
            
            
            strSql+=" ORDER BY bas.tx_descor ";

            // System.out.println("cargarDat-Principal: " + strSql);

            rstLoc = stmLoc.executeQuery(strSql);

            while (rstLoc.next())
            {
                blnHayConfiguracion = true;
                agregarFilaTblDat(rstLoc);
            }

            rstLoc.close();
            rstLoc = null;

            // =========================================================
            // 2. SI NO HAY CONFIGURACION, USAR QUERY ALTERNO
            //    TOMA LA CONFIGURACION SEGUN EL co_tipven DEL USUARIO
            // =========================================================
            if (!blnHayConfiguracion)
            {
                strSql = "";
                strSql += " SELECT DISTINCT cab.co_tblcom AS co_tblcom, cab.tx_descor, cab.tx_deslar, \n";
                strSql += "        (CAST(cab.co_tblcom AS VARCHAR(20)) + '-' + ISNULL(cab.tx_desLar,'')) AS desCom, \n";
                strSql += "        cab.tx_obs2, usr.co_tipven, bas.tx_deslar, bas.tx_descor, bas.co_bascal \n";
                strSql += " FROM Dynamics2021..tbm_preasitblcomvenusrven AS pre \n";
                strSql += "      INNER JOIN Dynamics2021..tbm_usr AS usr ON (pre.co_usrven = usr.co_usr) \n";
                strSql += "      INNER JOIN Dynamics2021..tbm_cabtblcomven AS cab ON (pre.co_tblcom = cab.co_tblcom  AND cab.st_reg ='A') \n";
                strSql += "      INNER JOIN Dynamics2021..tbm_bascalcomven AS bas ON (cab.co_bascal = bas.co_bascal) \n";
                strSql += " WHERE bas.co_bascal IN (" + strCodBasCal + ") \n";
                strSql += "   AND usr.co_tipven = ( \n";
                strSql += "       SELECT co_tipven \n";
                strSql += "       FROM Dynamics2021..tbm_usr \n";
                strSql += "       WHERE co_usr = " + codUsrGlo + " \n";
                strSql += "   ) \n";
                strSql += " ORDER BY bas.tx_descor ";

                // Si realmente quieres dejar fijo el tipo 10, reemplaza lo de arriba por esto:
                // strSql += "   AND usr.co_tipven = 10 \n";

                // System.out.println("cargarDat-Alterno: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);

                while (rstLoc.next())
                {
                    agregarFilaTblDat(rstLoc);
                }

                rstLoc.close();
                rstLoc = null;
            }

            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);

            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);

            blnRes = true;
        }
    }
    catch (java.sql.SQLException Evt)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }
    catch (Exception Evt)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }
    finally
    {
        try
        {
            if (rstLoc != null)
                rstLoc.close();
        }
        catch (Exception e) {}

        try
        {
            if (stmLoc != null)
                stmLoc.close();
        }
        catch (Exception e) {}

        try
        {
            if (conLoc != null)
                conLoc.close();
        }
        catch (Exception e) {}
    }

    return blnRes;
} 
    private void agregarFilaTblDat(java.sql.ResultSet rstLoc) throws java.sql.SQLException
{
    vecReg = new Vector();
    vecReg.add(INT_TBL_LINEA, "");
    vecReg.add(INT_TBL_CHK_SEL, false);
    vecReg.add(INT_TBL_CODTBL, getStringNull(rstLoc, "co_tblcom"));
    vecReg.add(INT_TBL_DESCOR, getStringNull(rstLoc, "tx_desCor"));
    vecReg.add(INT_TBL_DESLAR, getStringNull(rstLoc, "desCom"));
    vecReg.add(INT_TBL_OBS2, getStringNull(rstLoc, "tx_obs2"));
    vecDat.add(vecReg);
}
    
    private String getStringNull(java.sql.ResultSet rst, String strNomCam) throws java.sql.SQLException
{
    String strVal = rst.getString(strNomCam);
    return (strVal == null ? "" : strVal);
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
            case INT_TBL_CODTBL:
                strMsg="Código de la tabla";
            break;
            case INT_TBL_DESCOR:
                strMsg="Descripción corta...";
            break;
            case INT_TBL_DESLAR:
                strMsg="Descripción larga...";
            break;
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

 
    
    
    /**
     * Revisa si hay algo seleccionado
     * @return 
     */
    
    private boolean isSeleccionado(){
        boolean blnRes=false;
        try{
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.isRowSelected(i)){
                    intCodTabSel = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODTBL).toString() ) ;
                    strDesCorTabSel = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DESLAR).toString();
                    blnRes=true;
                }
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;     
    }
    

}
