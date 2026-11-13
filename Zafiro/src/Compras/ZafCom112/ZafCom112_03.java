/*
 * ZafCom112_03.java
 * Created on 20 de Mayo de 2019, 16h43
 * v0.1
 */
package Compras.ZafCom112;
////import Importaciones.ZafImp16.ZafImp16;
////import Importaciones.ZafImp17.ZafImp17;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafCom112_03 extends javax.swing.JDialog 
{
    //Variables.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafImp objZafImp;
    private ZafVenCon vcoPed;                       //Ventana de consulta "Pedidos".
    
    private boolean blnIsAce;  
    private String strSQL, strAux;
    private String strCodPed, strNumPed;            //Contenido del campo al obtener el foco.
    private String strNumPedSel="";
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_REP=1;   
    private final int INT_TBL_DAT_DESC=2;   
    private final int INT_TBL_DAT_ACC=3;   
    private Vector vecCab, vecReg, vecDat; 
    private ZafTblMod objTblMod, objTblModFix;
    private ZafTblBus objTblBus, objTblBusFix;     
    private ZafMouMotAda objMouMotAda;    
    
    private Connection conGP;
    private Statement stmGP;
    private ResultSet rstGP;    

    /**
     * New JDialog para seleccionar Pedidos
     * @param parent
     * @param modal
     * @param obj 
     */
    public ZafCom112_03(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        //Inicializar objetos.
        objParSis = obj;
        objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));   
        blnIsAce=false;
        initComponents(); 
        if (!configurarFrm())
                exitForm();
            
         
////        txtCodPed.setVisible(false); 
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }       
    private boolean  configurarFrm()
    {
         boolean blnRes=false;
         configurarTblDat();
         if (!cargarDetReg())
                exitForm();
         else
         {
////             abrirFrm();
             blnRes=true;
         }
             
         
         return blnRes;
    }  
    
   private boolean abrirFrm()
   {
        String strAux; 
        int intFilSel;
        boolean blnRes=true;
        try
        {
            intFilSel=tblDat.getSelectedRow();
            if (!((tblDat.getSelectedColumn()==-1) || (intFilSel==-1)))
            {
                strAux=tblDat.getValueAt(intFilSel,INT_TBL_DAT_ACC)==null?"":tblDat.getValueAt(intFilSel,INT_TBL_DAT_ACC).toString();
                if (!strAux.equals(""))
                {
                    if (strAux.contains("ZafImp16"))
                    {
                        //Importaciones.ZafImp16.ZafImp16 obj16 = new Importaciones.ZafImp16.ZafImp16(javax.swing.JOptionPane.getFrameForComponent(this), objParSis);
                        //this.getParent().add(obj16, javax.swing.JLayeredPane.DEFAULT_LAYER);
                       Importaciones.ZafImp16.ZafImp16 obj16 =  new Importaciones.ZafImp16.ZafImp16(javax.swing.JOptionPane.getFrameForComponent(this),true, objParSis); 
                        obj16.show();
                        
                    }
                        

                    if (strAux.contains("ZafImp17"))
                    {
                        Importaciones.ZafImp17.ZafImp17 obj17 =  new Importaciones.ZafImp17.ZafImp17(javax.swing.JOptionPane.getFrameForComponent(this),true, objParSis); 
                        obj17.show();
                    }
                    
                    if (strAux.contains("ZafImp18"))
                    {
                        Importaciones.ZafImp18.ZafImp18 obj18 =  new Importaciones.ZafImp18.ZafImp18(javax.swing.JOptionPane.getFrameForComponent(this),true, objParSis); 
                        obj18.show();
                    }                    
                    if (strAux.contains("ZafImp19"))
                    {
                        Importaciones.ZafImp19.ZafImp19 obj19 =  new Importaciones.ZafImp19.ZafImp19(javax.swing.JOptionPane.getFrameForComponent(this),true, objParSis); 
                        obj19.show();
                    }      
                    if (strAux.contains("ZafImp20"))
                    {
                        Importaciones.ZafImp20.ZafImp20 obj20 =  new Importaciones.ZafImp20.ZafImp20(javax.swing.JOptionPane.getFrameForComponent(this),true, objParSis); 
                        obj20.show();
                    }                     
                    
                }
            }       
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_REP:
                    strMsg = "Codigo del Reporte";
                    break;
                case INT_TBL_DAT_DESC:
                    strMsg = "Descripcion del Reporte";
                    break;
                case INT_TBL_DAT_ACC:
                    strMsg = "Acceso al Reporte";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  

private boolean cargarDetReg()
    {
        int intCodMnu, i;
        boolean blnRes=true;
        try
        {
            intCodMnu=objParSis.getCodigoMenu();
            intCodMnu=3198;
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());                     
            if (conGP!=null)
            {
                stmGP=conGP.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las opciones.
                if (objParSis.getCodigoUsuario()==1)
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_mnu, a1.tx_nom, a1.tx_des, a1.tx_acc";
                    strSQL+=" FROM tbm_mnuSis AS a1";
                    strSQL+=" WHERE a1.ne_pad=" + intCodMnu;
                    strSQL+=" AND a1.st_reg='A' AND a1.tx_tipmnu='R' AND a1.co_mnu in (3244,3247,3250,3253,3256)";
                    strSQL+=" ORDER BY a1.ne_ubi"; 
                    
                } 
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_mnu, a1.tx_nom, a1.tx_des, a1.tx_acc";
                    strSQL+=" FROM Dynamics2021..tbm_mnuSis AS a1";
                    strSQL+=" INNER JOIN Dynamics2021..tbr_perUsr AS a2 ON (a1.co_mnu=a2.co_mnu)";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND a1.ne_pad=" + intCodMnu;
                    strSQL+=" AND a1.st_reg='A' AND a1.tx_tipmnu='R' AND a1.co_mnu in (3244,3247,3250,3253,3256)";
                    strSQL+=" ORDER BY a1.ne_ubi";
                }
                rstGP=stmGP.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstGP.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_REP,rstGP.getString("co_mnu"));
                    vecReg.add(INT_TBL_DAT_DESC,rstGP.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_ACC,rstGP.getString("tx_acc"));
                    
                    vecDat.add(vecReg);
                }
                
////                    vecReg=new Vector();
////                    vecReg.add(INT_TBL_DAT_LIN,"");
////                    vecReg.add(INT_TBL_DAT_COD_REP,1);
////                    vecReg.add(INT_TBL_DAT_DESC,"REPORTE DE COMPRAS");
////                    vecReg.add(INT_TBL_DAT_ACC,"Importaciones.ZafImp18.ZafImp18");
////                    
////                    vecDat.add(vecReg);
                    
                
////                    vecReg=new Vector();
////                    vecReg.add(INT_TBL_DAT_LIN,"1");
////                    vecReg.add(INT_TBL_DAT_REPORTE,"Reporte de Presupuesto de Ventas");
////                    vecDat.add(vecReg);
////
////                    vecReg=new Vector();
////                    vecReg.add(INT_TBL_DAT_LIN,"2");
////                    vecReg.add(INT_TBL_DAT_REPORTE,"Reporte de Presupuesto de Compras");
////                    vecDat.add(vecReg);
                    
                
                rstGP.close(); 
                stmGP.close();
                conGP.close();
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
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
    
private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(9);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_REP, "Codigo");
            vecCab.add(INT_TBL_DAT_DESC, "Descripcion");
            vecCab.add(INT_TBL_DAT_ACC, "Acceso");
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_REP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DESC).setPreferredWidth(400);
            tcmAux.getColumn(INT_TBL_DAT_ACC).setPreferredWidth(100);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ACC, tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            ZafTblFilCab objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
//            //Configurar JTable: Establecer los listener para el TableHeader.  // JM Comentado el 14/Dic/2023 
//            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblDatMouseClicked(evt);
//                }
//            });



            //Libero los objetos auxiliares.
            tcmAux = null;
            
            //Configurar JTable: Ocultar columnas del sistema.
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);
////            
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REP).setWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REP).setMaxWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REP).setMinWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REP).setPreferredWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REP).setResizable(false);
////            
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESC).setWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESC).setMaxWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESC).setMinWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESC).setPreferredWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESC).setResizable(false);
////
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_ACC).setWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_ACC).setMaxWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_ACC).setMinWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_ACC).setPreferredWidth(0);
////            tblDat.getColumnModel().getColumn(INT_TBL_DAT_ACC).setResizable(false);            
////                    
            
            objTblMod.initRowsState();
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
   
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */

        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de reportes de importaciones...");
        setMinimumSize(new java.awt.Dimension(161, 157));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        panTit.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de Reportes:");
        lblTit.setPreferredSize(new java.awt.Dimension(207, 25));
        panTit.add(lblTit, java.awt.BorderLayout.NORTH);

        PanFrm.add(panTit, java.awt.BorderLayout.NORTH);

        panGen.setPreferredSize(new java.awt.Dimension(0, 50));
        panGen.setLayout(new java.awt.BorderLayout());

        panGen1.setLayout(new java.awt.BorderLayout());

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
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panGen1.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen1);

        panGen.add(tabFrm, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.LINE_END);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(445, 317));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        abrirFrm();    

////        if(!txtCodPed.getText().equals("")){
////            aceptar();
////        }
////        else {
////            mostrarMsgInf("<HTML>Seleccione un Pedido y de click en el botón Aceptar.<BR>Si no desea seleccionar pedido alguno de click en Cancelar.</HTML>");
////        }    
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnIsAce=false;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGen1;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal(){
        //Valida que exista algún pedido seleccionado
////        if(txtCodPed.getText().equals("")){
////            mostrarMsgInf("<HTML>Seleccione un Pedido y de click en el botón Aceptar.<BR>Si no desea seleccionar pedido alguno de click en Cancelar.</HTML>");
////            return false;
////        }    
        return true;
    } 
    
    private void aceptar()
    {
        if(isCamVal()){
            blnIsAce=true;
            dispose();
        } 
        else {
            mostrarMsgInf("<HTML>Verifique los datos y vuelva a intentarlo.</HTML>");
        }        
    }    
    
    /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Autorizar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Cerrar
    */
    public boolean isPedSel() {
        return blnIsAce;
    }
    
    public String getNumPedSel(){
        return strNumPedSel;
    }    
  
   
  
    
    
}


