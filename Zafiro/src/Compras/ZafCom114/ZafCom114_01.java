

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Compras.ZafCom114; 
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
 * @author Denis Toledo
 */
public class ZafCom114_01 extends javax.swing.JDialog {
    
    private String strSql;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    
    
    private ZafUtil objUti;
   
    
    private final int INT_TBL_LINEA =0; 
    private final int INT_TBL_PEDIDO=1;       
    private final int INT_TBL_FACTURA=2;       
    private final int INT_TBL_ORDEN=3;      
    private final int INT_TBL_BODEGA=4;      
    private final int INT_TBL_ITEM=5;      
    private final int INT_TBL_3L=6;       
    private final int INT_TBL_DESCRIPCION=7;      
    private final int INT_TBL_FACTURADO=8;      
    private final int INT_TBL_DESPACHADO=9;       
    private final int INT_TBL_PENDIENTE=10;       
    private final int INT_TBL_GUIA=11;     
    private final int INT_TBL_FECHA_GUIA=12;    
    private final int INT_TBL_USUARIO_GUIA=13;     
     
    
   
    
    private String strTit="Mensaje del Sistema Zafiro";
     
     
    Vector vecCab=new Vector(); 
      
    private ArrayList arlDat;
    private ArrayList arlReg;
  
    private String strTitu="Pendientes detalle";
    private String strVersion=" v0.02";
    private String Pedido;    
    private ZafTblOrd objTblOrd;
   
     
   private java.awt.Frame Frame;
   
 
     
    /** Creates new form pantalla dialogo */
    public ZafCom114_01(Frame parent,  ZafParSis ZafParSis,  String strPedido) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil(); 
             
 
          Pedido = strPedido;
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
        cargarDat(Pedido);  
    } 
    catch (Exception e){ 
        objUti.mostrarMsgErr_F1(this, e);  
    }
      
} 
  
    private Vector vecDat,  vecReg; 


 
    private boolean cargarDat(String strPedido){
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
                strSql+=" SELECT a2.pedido, a2.factura, a3.orden,a3.bod_bodega, a1.ART_CODIGO, a1.ART_DESCRIPCION,a1.ART_CODIGO_TRES_LETRAS,  \n";
                strSql+="         a1.ART_CANTIDAD  as cant_facturada,   \n";
                strSql+="        CASE WHEN  a4.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE a4.GUI_REM_CANTIDAD END as cant_despachada,  \n";
                strSql+="         a4.GUI_REM_GUIA_REMISION, CAST(a4.fecha_guia AS DATE) AS fecha_guia, a4.usuario_guia,   ";
                strSql+="        (a1.ART_CANTIDAD  - CASE WHEN  a4.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE a4.GUI_REM_CANTIDAD END  ) as pendiente  \n";
                strSql+=" FROM tuvalapi.gp_pedido_detalles as a1 \n";  
                strSql+=" INNER JOIN tuvalapi.gp_pedidos as a2 on (a1.pedido_id=a2.id and a2.estado='facturado') \n";  
                strSql+=" INNER JOIN tuvalapi.gp_orden_despachos as a3 on (a2.id=a3.pedido_id and a3.estado ='liberada') \n";  
                strSql+=" LEFT JOIN( \n";  
                strSql+="       SELECT a2.pedido, a2.bodega_origen ,a2.GUI_REM_GUIA_REMISION ,a1.detalle_id,a1.guia_remision_id, a1.GUI_REM_CODI_INTERNO,  \n";  
                strSql+="              a1.GUI_REM_DESCRIPCION,a1.GUI_REM_CODI_ADICIONAL ,a1.GUI_REM_CANTIDAD, a2.created_at as fecha_guia, a3.USERID as usuario_guia  \n";  
                strSql+="       FROM tuvalapi.gp_guia_remision_detalles as a1      \n";  
                strSql+="       INNER JOIN tuvalapi.gp_guia_remisions as a2 on (a1.guia_remision_id=a2.id) \n";  
                strSql+="       INNER JOIN tuvalapi.gp_usuarios as a3 on (a2.user_id=a3.id) \n";
                strSql+="       WHERE  a2.tipo='despacho' and a1.recibido is null  \n";  
                strSql+=" ) as a4 on (a1.id=a4.detalle_id and a2.pedido=a4.pedido) \n";  
                strSql+=" where a2.pedido="+objUti.codificar(strPedido)+" ; \n";  
                System.out.println("ZafCom114_01.cargarDat: "+strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_PEDIDO, rstLoc.getString("pedido"));
                    vecReg.add(INT_TBL_FACTURA, rstLoc.getString("factura"));
                    vecReg.add(INT_TBL_ORDEN, rstLoc.getString("orden"));
                    vecReg.add(INT_TBL_BODEGA, rstLoc.getString("bod_bodega"));
                    vecReg.add(INT_TBL_ITEM, rstLoc.getString("ART_CODIGO"));
                    vecReg.add(INT_TBL_3L, rstLoc.getString("ART_CODIGO_TRES_LETRAS"));
                    vecReg.add(INT_TBL_DESCRIPCION, rstLoc.getString("ART_DESCRIPCION"));
                    vecReg.add(INT_TBL_FACTURADO, rstLoc.getString("cant_facturada"));
                    vecReg.add(INT_TBL_DESPACHADO, rstLoc.getString("cant_despachada"));
                    vecReg.add(INT_TBL_PENDIENTE, rstLoc.getString("pendiente"));
                    vecReg.add(INT_TBL_GUIA, rstLoc.getString("GUI_REM_GUIA_REMISION"));
                    vecReg.add(INT_TBL_FECHA_GUIA, rstLoc.getString("fecha_guia"));
                    vecReg.add(INT_TBL_USUARIO_GUIA, rstLoc.getString("usuario_guia"));
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
            vecCab.add(INT_TBL_PEDIDO,"Pedido");
            vecCab.add(INT_TBL_FACTURA,"Factura");
            vecCab.add(INT_TBL_ORDEN,"OD");
            vecCab.add(INT_TBL_BODEGA,"Bodega");
            vecCab.add(INT_TBL_ITEM,"Item");
            vecCab.add(INT_TBL_3L,"3L");
            vecCab.add( INT_TBL_DESCRIPCION,"Descripción" ); // V A U : Jose Marin //C Cliente retira 3/Sep/2014
            vecCab.add( INT_TBL_FACTURADO,"Can.Facturado" );
            vecCab.add( INT_TBL_DESPACHADO,"Despachado" );
            vecCab.add( INT_TBL_PENDIENTE,"Pendiente" );
            vecCab.add( INT_TBL_GUIA,"Guia" );
            vecCab.add( INT_TBL_FECHA_GUIA,"Fec.Guia" );
            vecCab.add( INT_TBL_USUARIO_GUIA,"Usr.Guia" );
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
            tcmAux.getColumn(INT_TBL_PEDIDO).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_FACTURA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_ORDEN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_BODEGA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ITEM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_3L).setPreferredWidth(40);            
            tcmAux.getColumn(INT_TBL_DESCRIPCION).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_FACTURADO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESPACHADO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PENDIENTE).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_GUIA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_FECHA_GUIA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_USUARIO_GUIA).setPreferredWidth(70);
             
 
                
            objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);

            objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblMod.setColumnDataType(INT_TBL_FACTURADO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESPACHADO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PENDIENTE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            tcmAux.getColumn(INT_TBL_FACTURADO).setCellRenderer(objTblCelRenLbl3);
            tcmAux.getColumn(INT_TBL_DESPACHADO).setCellRenderer(objTblCelRenLbl3);
            tcmAux.getColumn(INT_TBL_PENDIENTE).setCellRenderer(objTblCelRenLbl3);
                
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
            
            case INT_TBL_PEDIDO:
                strMsg="";
            break;
             
            case INT_TBL_BODEGA:
                strMsg="Código de la bodega";
            break;
            case INT_TBL_ITEM:
                strMsg="Nombre de la bodega";
            break;
            case INT_TBL_3L:
                strMsg="Stock actual";
            break;
            case INT_TBL_FACTURADO:
                strMsg="Cantidad pedida a otras bodegas";
            break;
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

