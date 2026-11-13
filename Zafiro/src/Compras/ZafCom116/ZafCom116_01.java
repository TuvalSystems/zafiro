

/*
 * Created on 19 December 2023, 10:45   fe_retemprel
 */
           
package Compras.ZafCom116; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
                 
/**     
 * @author  Denis Toledo
 */
public class ZafCom116_01 extends javax.swing.JDialog {
    
    private String strSql;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    
    
    private ZafUtil objUti;
   
    
    private final int INT_TBL_LINEA =0; 
    private final int INT_TBL_GUIA=1;       
    private final int INT_TBL_FECHA_GUIA=2;
    private final int INT_TBL_DOCUMENTO=3;           
    private final int INT_TBL_BOD_ORIGEN=4;      
    private final int INT_TBL_BOD_DESTINO=5;
    private final int INT_TBL_CODIGO=6;      
    private final int INT_TBL_3L=7;
    private final int INT_TBL_DESCRIPCION=8;      
    private final int INT_TBL_CAN_GENERADA=9;
    private final int INT_TBL_CAN_TRASLADADA=10;      
    private final int INT_TBL_CAN_PENDIENTE=11;
    private final int INT_TBL_CAN_RECIBIDO=12;
    //private final int INT_TBL_PES_UNITARIO=13;     
    //private final int INT_TBL_PES_TOTAL=14;
    
    private String strTit="Mensaje del Sistema Zafiro";
     
     
    Vector vecCab=new Vector(); 
      
    private ArrayList arlDat;
    private ArrayList arlReg;
  
    private String strTitu="Ingresos Pendientes detalle";
    private String strVersion=" v0.02";
    private String Pedido;   
    private String Documento;
    private ZafTblOrd objTblOrd;
   
     
   private java.awt.Frame Frame;
   
 
     
    /** Creates new form pantalla dialogo */
    public ZafCom116_01(Frame parent,  ZafParSis ZafParSis,  String strGuia, String strDocumento) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil(); 
             
 
          Pedido = strGuia;
          Documento = strDocumento;
          initComponents();
           
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
private void configurarFrm(){
    try{
        vecDat=new Vector();    //Almacena los datos
        objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        configuraTbl();      
        cargarDat(Pedido,Documento);  
    } 
    catch (Exception e){ 
        objUti.mostrarMsgErr_F1(this, e);  
    }
      
} 
  
    private Vector vecDat,  vecReg; 


 
    private boolean cargarDat(String strGuia, String strDocumento){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{    
            System.out.println("cargarDat");
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                //Limpiar vector de datos.
                vecDat.clear();
                strSql="";
                strSql+=" select a.* from (\n";
                strSql+="   select a.* from (\n";
                strSql+=" 		select a4.gui_rem_guia_remision,cast(a4.created_at as date) as fecha_guia , a2.Documento,a2.bodega_origen,a2.bodega_destino,a1.ART_CODIGO,\n";
                strSql+="                  a1.ART_CODIGO_TRES_LETRAS as item3L, a1.ART_DESCRIPCION, a1.cantidad as generado, a1.trasladado, (a1.trasladado  - CASE WHEN  a3.recibido IS NULL THEN 0 ELSE a3.recibido END  ) as pendiente_ingresar , a3.recibido \n";
                strSql+="		from tuvalapi.gp_traslados_reposicions as a1";
                strSql+="                   inner join tuvalapi.gp_traslados_reposicion_cabeceras as a2 on (a1.cabecera_id=a2.id) \n";
                strSql+="                   inner join tuvalapi.gp_guia_remision_detalles as a3 on (a1.id=a3.detalle_id) \n";  
                strSql+="                   inner join tuvalapi.gp_guia_remisions as a4 on (a3.guia_remision_id=a4.id and a4.estado='enviada' and a4.tipo='reposicion') \n";  
                strSql+=" ) as a \n";  
                strSql+=" where a.pendiente_ingresar>0 \n";
                strSql+=" union \n";
                strSql+="   select a.* from (\n";
                strSql+="		select a4.gui_rem_guia_remision,cast(a3.created_at as date) as fecha_guia ,a4.pedido as Documento,a1.bodega_origen,a1.bodega_destino,a1.ART_CODIGO, \n";  
                strSql+="                a1.ART_CODIGO_TRES_LETRAS as item3L, a1.ART_DESCRIPCION, a1.cantidad as generado, a1.trasladado, (a1.trasladado  - CASE WHEN  a3.recibido IS NULL THEN 0 ELSE a3.recibido END  ) as pendiente_ingresar , a3.recibido \n";  
                strSql+="		from tuvalapi.gp_traslados_ventas as a1 \n";  
                strSql+="                   inner join tuvalapi.gp_guia_remision_detalles as a3 on (a1.id=a3.detalle_id) \n";  
                strSql+="                   inner join (select a1.id as id_pedguia, a1.guia_remision_id as guia_rem_id ,a3.gui_rem_guia_remision,a1.codigo,a2.id,a2.pedido, a2.estado\n";  
                strSql+="				from tuvalapi.gp_guia_remision_transferencias as a1\n";  
                strSql+="                                   inner join tuvalapi.gp_pedidos as a2 on (a1.pedido=a2.pedido and a2.estado='traslado')\n";
                strSql+="                                   inner join tuvalapi.gp_guia_remisions as a3 on (a1.guia_remision_id=a3.id and a3.tipo='transferencia' and a3.estado='enviada' )\n";
                strSql+="				) a4 on (a3.guia_remision_id=a4.guia_rem_id and a1.pedido_id=a4.id)\n";
                strSql+="    ) as a\n";  
                strSql+="    where a.pendiente_ingresar>0 ) as a  \n";
                strSql+=" where a.gui_rem_guia_remision="+objUti.codificar(strGuia)+"  and a.Documento="+objUti.codificar(strDocumento)+";\n" ;  
                System.out.println("ZafCom116_01.cargarDat: "+strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_GUIA, rstLoc.getString("gui_rem_guia_remision"));
                    vecReg.add(INT_TBL_FECHA_GUIA, rstLoc.getString("fecha_guia"));
                    vecReg.add(INT_TBL_DOCUMENTO, rstLoc.getString("Documento"));
                    vecReg.add(INT_TBL_BOD_ORIGEN, rstLoc.getString("bodega_origen"));
                    vecReg.add(INT_TBL_BOD_DESTINO, rstLoc.getString("bodega_destino"));
                    vecReg.add(INT_TBL_CODIGO, rstLoc.getString("ART_CODIGO"));
                    vecReg.add(INT_TBL_3L, rstLoc.getString("item3L"));
                    vecReg.add(INT_TBL_DESCRIPCION, rstLoc.getString("ART_DESCRIPCION"));
                    vecReg.add(INT_TBL_CAN_GENERADA, rstLoc.getString("generado"));
                    vecReg.add(INT_TBL_CAN_TRASLADADA, rstLoc.getString("trasladado"));
                    vecReg.add(INT_TBL_CAN_PENDIENTE, rstLoc.getString("pendiente_ingresar"));
                    vecReg.add(INT_TBL_CAN_RECIBIDO, rstLoc.getString("recibido"));
                    //vecReg.add(INT_TBL_PES_UNITARIO, rstLoc.getString("peso_unitario"));
                    //vecReg.add(INT_TBL_PES_TOTAL, rstLoc.getString("peso_total"));
                    vecDat.add(vecReg);   
                } 
                objTblMod.setData(vecDat);
                tblDat .setModel(objTblMod);
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;                
                conLoc.close();
                conLoc=null;
                

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    

            

                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;          
    }   





double dblCabFalComAut=0.00;


 

 
private void MensajeInf(String strMensaje){
 //JOptionPane obj =new JOptionPane();
 String strTit="Mensaje del sistema Zafiro";
 JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
}
 

  
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_GUIA,"Guia Remisión");
            vecCab.add(INT_TBL_FECHA_GUIA,"Fecha Guía");
            vecCab.add(INT_TBL_DOCUMENTO,"Documento");
            vecCab.add(INT_TBL_BOD_ORIGEN,"Bod.Origen");
            vecCab.add( INT_TBL_BOD_DESTINO,"Bod.Destino" ); 
            vecCab.add( INT_TBL_CODIGO,"Código");
            vecCab.add( INT_TBL_3L,"3L" );
            vecCab.add( INT_TBL_DESCRIPCION,"Descripción" );
            vecCab.add( INT_TBL_CAN_GENERADA,"Can.Generada" );
            vecCab.add( INT_TBL_CAN_TRASLADADA,"Can.Trasladada" );
            
            vecCab.add( INT_TBL_CAN_PENDIENTE,"Can.Pendiente" );
            
            vecCab.add( INT_TBL_CAN_RECIBIDO,"Can.Recibido" );
            //vecCab.add( INT_TBL_PES_UNITARIO,"Peso Unit." );
            //vecCab.add( INT_TBL_PES_TOTAL,"Peso Total" );
 	    objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);


            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_GUIA).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_FECHA_GUIA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DOCUMENTO).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_BOD_ORIGEN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_BOD_DESTINO).setPreferredWidth(90);            
            tcmAux.getColumn(INT_TBL_CODIGO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCRIPCION).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_CAN_GENERADA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CAN_TRASLADADA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CAN_PENDIENTE).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CAN_RECIBIDO).setPreferredWidth(90);
            
            //tcmAux.getColumn(INT_TBL_PES_UNITARIO).setPreferredWidth(90);
            //tcmAux.getColumn(INT_TBL_PES_TOTAL).setPreferredWidth(90);
 
                
            objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);

            objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblMod.setColumnDataType(INT_TBL_CAN_GENERADA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_TRASLADADA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_PENDIENTE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_RECIBIDO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //objTblMod.setColumnDataType(INT_TBL_PES_UNITARIO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //objTblMod.setColumnDataType(INT_TBL_PES_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            tcmAux.getColumn(INT_TBL_CAN_GENERADA).setCellRenderer(objTblCelRenLbl3);
            tcmAux.getColumn(INT_TBL_CAN_TRASLADADA).setCellRenderer(objTblCelRenLbl3);
            tcmAux.getColumn(INT_TBL_CAN_PENDIENTE).setCellRenderer(objTblCelRenLbl3);
            tcmAux.getColumn(INT_TBL_CAN_RECIBIDO).setCellRenderer(objTblCelRenLbl3);
            //tcmAux.getColumn(INT_TBL_PES_UNITARIO).setCellRenderer(objTblCelRenLbl3);
            //tcmAux.getColumn(INT_TBL_PES_TOTAL).setCellRenderer(objTblCelRenLbl3);
                
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 20);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

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

        setSize(new java.awt.Dimension(1322, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
         
}//GEN-LAST:event_formWindowOpened
  
      
       
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
           
            dispose();
        }
    }
    

    
    
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
 




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
            
            case INT_TBL_GUIA:
                strMsg="Número de Guía pendiente de ingresar";
            break;
            case INT_TBL_FECHA_GUIA:
                strMsg="Fecha de Guía";
            break;
            case INT_TBL_DOCUMENTO:
                strMsg="Número de Pedido/Documento";
            break;
            case INT_TBL_BOD_ORIGEN:
                strMsg="Nombre de la bodega de Origen";
            break; 
            case INT_TBL_BOD_DESTINO:
                strMsg="Nombre de la bodega de Destino";
            break;
            case INT_TBL_CODIGO:
                strMsg="Código del ítem";
            break;
            case INT_TBL_3L:
                strMsg="Código de 3 letras";
            break;
            case INT_TBL_DESCRIPCION:
                strMsg="Descripción del item";
            break;
            case INT_TBL_CAN_GENERADA:
                strMsg="Cantidad Generada de la reposción/pedido";
            break;
            case INT_TBL_CAN_TRASLADADA:
                strMsg="Cantidad trasladada";
            break;
            case INT_TBL_CAN_PENDIENTE:
                strMsg="Cantidad pendiente de ingresar";
            break;
            case INT_TBL_CAN_RECIBIDO:
                strMsg="Cantidad recibida";
            break;
            /*case INT_TBL_PES_UNITARIO:
                strMsg="Peso Unitario";
            break;
            case INT_TBL_PES_TOTAL:
                strMsg="Peso Total";
            break;*/
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
 

     
  
    
}

