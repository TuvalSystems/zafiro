/*
 * ZafCom119.java
 *
 * Created on 02 de agosto de 2007, 16:15 PM
 */
package Compras.ZafCom121;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConMySQL.ZafVenConMySQL; 
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Vector;
import Librerias.ZafVenConMySQL.ZafVenConItmMySQL01;
import java.util.HashMap;

/**
 *
 * @author  Denis Toledo
 */
public class ZafCom121 extends javax.swing.JInternalFrame 
{
    //Constantes.
    //Columnas del JTable: TblEmp
    static final int INT_TBL_EMP_LIN=0;                         //Línea.
    static final int INT_TBL_EMP_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;                     //Código de la empresa.
                     //Nombre de la empresa.   
    
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_BOD=2; 
    
    String strCodCli="";
   String strDesCli="";
   String strCodVen="";
   String strDesVen="";
    //Columnas del JTable: TblDat PASO 1
    static final int INT_TBL_DAT_LIN=0;   
    private String strCodLoc, strNomLoc;//Línea
    
    
//    static final int INT_TBL_DAT_OD=1;                 //Código maestro del item.
//    static final int INT_TBL_DAT_EST_OD=2;                 //Código de empresa del código del ítem.
//    static final int INT_TBL_DAT_FEC_HOR_LIB=3;                     //Código del item.
//    static final int INT_TBL_DAT_PED=4;                 //Código alterno del ítem.
//    static final int INT_TBL_DAT_FAC=5;                     //Nombre del item.
//    static final int INT_TBL_DAT_FEC_FAC=6;             //Descripción corta de la unidad de medida.
//    static final int INT_TBL_DAT_EST_FAC=7; 
//    static final int INT_TBL_DAT_CLI=8; 
//    static final int INT_TBL_DAT_DIR=9; 
//    static final int INT_TBL_DAT_CIU=10; 
//    static final int INT_TBL_DAT_BOD=11; 
//    static final int INT_TBL_DAT_TIP_DES=12; 
//    static final int INT_TBL_DAT_VEN=13; 
//    static final int INT_TBL_DAT_DIA_PEN=14; 
//    static final int INT_TBL_DAT_ITEM=15; 
//    static final int INT_TBL_DAT_ITEM_3L=16; 
//    static final int INT_TBL_DAT_ITEM_DES=17; 
//    static final int INT_TBL_DAT_CAN_FAC=18; 
//    static final int INT_TBL_DAT_PES_FAC=19; 
//    static final int INT_TBL_DAT_CAN_DES=20; 
//    static final int INT_TBL_DAT_PES_DES=21; 
//    static final int INT_TBL_DAT_CAN_PEN=22; 
//    static final int INT_TBL_DAT_PES_PEN=23;
//    
//    static final int INT_TBL_DAT_UBI_ITM=24; 
        static final int INT_TBL_DAT_PED=1;                 //Código maestro del item.
    static final int INT_TBL_DAT_FEC_PED=2;                 //Código de empresa del código del ítem.
    static final int INT_TBL_DAT_BOD_ORI=3;                     //Código del item.
    static final int INT_TBL_DAT_BOD_DES=4;             //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_USR_DOC=5;
    static final int INT_TBL_DAT_ITEM=6; 
    static final int INT_TBL_DAT_ITEM_ZAF=7;  
    static final int INT_TBL_DAT_ITEM_3L=8;
    static final int INT_TBL_DAT_ITEM_DES=9; 
    static final int INT_TBL_DAT_PES_UNI=10;                 //Código alterno del ítem.
    static final int INT_TBL_DAT_PES_TOT=11;
    static final int INT_TBL_DAT_CAN_PED=12; 
    static final int INT_TBL_DAT_CAN_DES=13; 
    static final int INT_TBL_DAT_PES_DES=14; 
    static final int INT_TBL_DAT_CAN_PEN=15; 
    static final int INT_TBL_DAT_PES_PEN=16;                     //Nombre del item.
    static final int INT_TBL_DAT_DIA_PEN=17;
    
    static final int INT_TBL_DAT_UBI_ITM=18; 
    
    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab,objTblFilCabBD;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModEmp;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblMod objTblModBodOrigen;
    private ZafTblMod objTblModBodDestino;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;  
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos de Usuario.
    private ZafVenConMySQL vcoItm;    
    private ZafVenConMySQL vcoLoc; //Ventana de Consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecEstReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm; 
    private ZafVenConMySQL objVenConCLi;
    private ZafVenConMySQL objVenConVen; 
    private ZafVenConItmMySQL01 objVenConItm01_01;
    private ZafTblCelRenChk objTblCelRenChklBD;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChklBD;
    private boolean blnMarTodChkTblBod_Origen=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnMarTodChkTblBod_Destino=true;
    
   // private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    
    private ZafTblCelRenBut objTblCelRenBut;
    // private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafSelFec objSelFec;
            
     private static ArrayList<String> arrItems = new ArrayList<>();
    private static ArrayList<String> arrBodegas = new ArrayList<>();    
    private HashMap<String, String> ubicacionMap = new HashMap<>();
   
    /** Crea una nueva instancia de la clase ZafCom13. *///constructor
    public ZafCom121(ZafParSis obj) {
        try {
            initComponents();
            objParSis=(ZafParSis)obj.clone();


            // Configurar el formulario
            if (!configurarFrm()) {
                exitForm();
            }

            // Establecer el título de la ventana y el texto del label
            this.setTitle(objParSis.getNombreMenu() + " " );
            lblTit.setText(objParSis.getNombreMenu());

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
             objUti.mostrarMsgErr_F1(this, e);
        }
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panEmp = new javax.swing.JPanel();
        panBodOrg = new javax.swing.JPanel();
        spnEmp = new javax.swing.JScrollPane();
        tblBodOrigen = new javax.swing.JTable();
        panBodDes = new javax.swing.JPanel();
        spnEmp1 = new javax.swing.JScrollPane();
        tblBodDestino = new javax.swing.JTable();
        panDetFil = new javax.swing.JPanel();
        panDoc = new javax.swing.JPanel();
        lblOd = new javax.swing.JLabel();
        txtOd2 = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panFilItm = new javax.swing.JPanel();
        optTod1 = new javax.swing.JRadioButton();
        optFil1 = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.PAGE_START);

        panFil.setLayout(new java.awt.BorderLayout());

        panEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panEmp.setPreferredSize(new java.awt.Dimension(464, 200));
        panEmp.setLayout(new java.awt.GridLayout());

        panBodOrg.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodega Origen"));
        panBodOrg.setLayout(new java.awt.BorderLayout());

        tblBodOrigen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnEmp.setViewportView(tblBodOrigen);

        panBodOrg.add(spnEmp, java.awt.BorderLayout.WEST);

        panEmp.add(panBodOrg);

        panBodDes.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodega Destino"));
        panBodDes.setLayout(new java.awt.BorderLayout());

        tblBodDestino.setModel(new javax.swing.table.DefaultTableModel(
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
        spnEmp1.setViewportView(tblBodDestino);

        panBodDes.add(spnEmp1, java.awt.BorderLayout.CENTER);

        panEmp.add(panBodDes);

        panFil.add(panEmp, java.awt.BorderLayout.NORTH);

        panDetFil.setLayout(new java.awt.BorderLayout());

        panDoc.setPreferredSize(new java.awt.Dimension(100, 70));
        panDoc.setLayout(null);

        lblOd.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblOd.setText("Documento:");
        lblOd.setToolTipText("Local");
        panDoc.add(lblOd);
        lblOd.setBounds(10, 60, 57, 15);

        txtOd2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOd2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOd2FocusLost(evt);
            }
        });
        txtOd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOd2ActionPerformed(evt);
            }
        });
        panDoc.add(txtOd2);
        txtOd2.setBounds(80, 60, 160, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
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
        panDoc.add(optTod);
        optTod.setBounds(10, 10, 230, 23);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panDoc.add(optFil);
        optFil.setBounds(10, 30, 400, 23);

        panDetFil.add(panDoc, java.awt.BorderLayout.CENTER);

        panFilItm.setPreferredSize(new java.awt.Dimension(410, 130));
        panFilItm.setLayout(null);

        bgrFil.add(optTod1);
        optTod1.setText("Todos los items");
        optTod1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTod1ItemStateChanged(evt);
            }
        });
        panFilItm.add(optTod1);
        optTod1.setBounds(8, 2, 410, 14);

        bgrFil.add(optFil1);
        optFil1.setText("Sólo los items que cumplan el criterio seleccionado");
        panFilItm.add(optFil1);
        optFil1.setBounds(8, 18, 410, 14);

        panDetFil.add(panFilItm, java.awt.BorderLayout.SOUTH);

        panFil.add(panDetFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

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

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

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
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 643);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {

            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";


            

        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void txtOd2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOd2FocusGained
        strNomLoc=txtOd2.getText();
        txtOd2.selectAll();
    }//GEN-LAST:event_txtOd2FocusGained

    private void txtOd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOd2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtOd2.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtOd2.getText().equals(""))
            {
                txtOd2.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
        txtOd2.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
//        if (txtOd1.getText().length()>0)
//        {
//            optFil.setSelected(true);
//        }
    }//GEN-LAST:event_txtOd2FocusLost

    private void txtOd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOd2ActionPerformed
        txtOd2.transferFocus();
    }//GEN-LAST:event_txtOd2ActionPerformed

    private void optTod1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTod1ItemStateChanged
        // TODO add your handling code here:
        if (optTod.isSelected())
        {
            objVenConItm01_01.limpiar();
        }
    }//GEN-LAST:event_optTod1ItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblOd;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optFil1;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JRadioButton optTod1;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBodDes;
    private javax.swing.JPanel panBodOrg;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDetFil;
    private javax.swing.JPanel panDoc;
    private javax.swing.JPanel panEmp;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnEmp;
    private javax.swing.JScrollPane spnEmp1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodDestino;
    private javax.swing.JTable tblBodOrigen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtOd2;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
            
            objVenConItm01_01=new ZafVenConItmMySQL01(objParSis, "art.", optFil1); // el a1. sale de la funcion que hace la consulta supuestamente
            panFilItm.add(objVenConItm01_01); // defino en que panel voy a agregar el menu de items
            objVenConItm01_01.setBounds(30, 50, 656, 20); // define coordenadas (donde se va a ubicar el menu de seleccion de items)
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.05");
            lblTit.setText(strAux);
           
            
            //Configurar Combos.
            configurarComboEstReg();
            //Configurar los JTables.
            configurarTblBodOrigen();
            configurarTblBodDestino();
            //configurarTblEmp();
            configurarTblDat();
            //cargarEmp();
            cargarDetReg();
            
            cargarBodegaOrigen1();
            cargarBodegaDestino();
//            agregarColTblDat();
            //Configurar ZafVenConItm01:
            
            
            
          
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("L");
            vecEstReg.add("P");
//            cboEstOrdDes.addItem("(Todos)");
//            cboEstOrdDes.addItem("Liberada");
//            cboEstOrdDes.addItem("Pendiente");
//            cboEstOrdDes.setSelectedIndex(0);   //Por default muestre solo los items activos. 0 es la 1era 
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    
    /**
     * Esta función configura el JTable "tblEmp".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
//    private boolean configurarTblEmp()
//    {
//        boolean blnRes=true;
//        try
//        {
//            //Configurar JTable: Establecer el modelo.
//            vecDat=new Vector();    //Almacena los datos
//            vecCab=new Vector(4);   //Almacena las cabeceras
//            vecCab.clear();
//            vecCab.add(INT_TBL_EMP_LIN,"");
//            vecCab.add(INT_TBL_EMP_CHK,"");
//            vecCab.add(INT_TBL_EMP_COD_EMP,"Bodegas");
//
//            //Configurar JTable: Establecer el modelo de la tabla.
//            objTblModEmp=new ZafTblMod();
//            objTblModEmp.setHeader(vecCab);
//            tblBodOrigen.setModel(objTblModEmp);
//            //Configurar JTable: Establecer tipo de selección.
//            tblBodOrigen.setRowSelectionAllowed(true); // Permitir la selección de filas
//            tblBodOrigen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//            //Configurar JTable: Establecer el menú de contexto.
//            objTblPopMnu=new ZafTblPopMnu(tblBodOrigen);
//            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//            tblBodOrigen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//            //Configurar JTable: Establecer el ancho de las columnas.
//            javax.swing.table.TableColumnModel tcmAux=tblBodOrigen.getColumnModel();
//            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(200);
//
//            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
//            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//            tblBodOrigen.getTableHeader().setReorderingAllowed(false);
//            
//            //Configurar JTable: Ocultar columnas del sistema.
//            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setWidth(0);
//            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMaxWidth(0);
//            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMinWidth(0);
//            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setPreferredWidth(0);
//            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setResizable(false);
//            
//            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            tblBodOrigen.getTableHeader().addMouseMotionListener(new ZafMouMotAdaEmp());
//            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblBodOrigen.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
////                    tblEmpMouseClicked(evt); // habilita el marcar todos los checks
//                }
//            });
//            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_EMP_CHK);
//            objTblModEmp.setColumnasEditables(vecAux);
//            vecAux=null;
//            
//            //Configurar JTable: Editor de la tabla.
//            //  objTblEdi=new ZafTblEdi(tblDat);
//            //Configurar JTable: Editor de búsqueda.
//            //  objTblBus=new ZafTblBus(tblEmp);
//            
//            //Configurar JTable: Establecer la fila de cabecera.
//            objTblFilCab=new ZafTblFilCab(tblBodOrigen);
//            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
//            //Configurar JTable: Renderizar celdas.
//            objTblCelRenChk=new ZafTblCelRenChk();
//            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
//            objTblCelRenChk=null;
//            
////            objTblCelRenLbl=new ZafTblCelRenLbl();
////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
////            objTblCelRenLbl=null;
//            
//            //Configurar JTable: Editor de celdas.
//            objTblCelEdiChk=new ZafTblCelEdiChk(tblBodOrigen);
//            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChk);
//            
//        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                if (objTblCelEdiChk.isChecked()) {// si esta marcado
//                    // Recorre todas las filas y desmarcar los cheks, excepto la  seleccionada inicialmente
//                    for (int i = 0; i < objTblModEmp.getRowCountTrue(); i++) {// este objeto se debe utilizar para las validaciones
//                        if (i != tblBodOrigen.getSelectedRow()) {
//                            objTblModEmp.setValueAt(false, i, INT_TBL_EMP_CHK); // Desmarcar los checks en otras filas
//                        }
//                    }
//                    // oculta supuestamente
//                    // objTblMod.removeSystemHiddenColumn(7 + tblEmp.getSelectedColumn() , tblDat);
//                } 
////                else {
////                    // muestra supuestamente
////                   // objTblMod.addSystemHiddenColumn(7 + tblEmp.getSelectedColumn(), tblDat);
////                }
//            }
//        });
//
//            //Configurar JTable: Establecer el ListSelectionListener.
////            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
////            lsm.addListSelectionListener(new ZafLisSelLisCre());
//            //Configurar JTable: Establecer el modo de operación.
//            objTblModEmp.setModoOperacion(objTblModEmp.INT_TBL_EDI);
//            //Libero los objetos auxiliares.
//            tcmAux=null;
//        }
//        catch(Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    private boolean configurarTblBodOrigen()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_LIN,"");
            vecCab.add(INT_TBL_EMP_CHK,"");
            vecCab.add(INT_TBL_EMP_COD_EMP,"Bodegas Origen");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodOrigen=new ZafTblMod();
            objTblModBodOrigen.setHeader(vecCab);
            tblBodOrigen.setModel(objTblModBodOrigen);
            //Configurar JTable: Establecer tipo de selección.
            tblBodOrigen.setRowSelectionAllowed(true);
            tblBodOrigen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBodOrigen);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodOrigen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBodOrigen.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(200);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodOrigen.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMaxWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMinWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setPreferredWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setResizable(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodOrigen.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodOrigen.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //tblEmpMouseClickedBodOrigen(evt);
                }

            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_EMP_CHK);
            objTblModBodOrigen.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            //  objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
            //  objTblBus=new ZafTblBus(tblEmp);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBodOrigen);
            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            
            
            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBodOrigen);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChk);
//            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    if (objTblCelEdiChk.isChecked())
////                    {
////                        objTblMod.removeSystemHiddenColumn(7 + tblEmp.getSelectedRow(), tblDat);
////                    }
////                    else
////                    {
////                        objTblMod.addSystemHiddenColumn(7 + tblEmp.getSelectedRow(), tblDat);
////                    }
//                }
//            });

        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if (objTblCelEdiChk.isChecked()) {// si esta marcado
                    // Recorre todas las filas y desmarcar los cheks, excepto la  seleccionada inicialmente
                    for (int i = 0; i < objTblModBodOrigen.getRowCountTrue(); i++) {// este objeto se debe utilizar para las validaciones
                        if (i != tblBodOrigen.getSelectedRow()) {
                            objTblModBodOrigen.setValueAt(false, i, INT_TBL_EMP_CHK); // Desmarcar los checks en otras filas
                        }
                    }
                    // oculta supuestamente
                    // objTblMod.removeSystemHiddenColumn(7 + tblEmp.getSelectedColumn() , tblDat);
                } 
//                else {
//                    // muestra supuestamente
//                   // objTblMod.addSystemHiddenColumn(7 + tblEmp.getSelectedColumn(), tblDat);
//                }
            }
        });









            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodOrigen.setModoOperacion(objTblModBodOrigen.INT_TBL_EDI);
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
    
    private boolean cargarBodegaOrigen1(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
          
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                //Armar la sentencia SQL.
              if (objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+=" select a2.tx_descor as bodega_origen \n";
                strSQL+=" FROM tbm_bod as a2 \n";
                strSQL+=" where a2.co_emp=0 and a2.st_bodRep ='S'   ";
                strSQL+=" order by 1\n";
              }
              else{
                strSQL="";
                strSQL+=" select  a2.tx_descor as bodega_origen \n";
                strSQL+=" from tbr_bodlocprgusr as a1 \n";
                strSQL+=" inner join tbm_bod as a2 on (a1.co_bod=a2.co_bod and a2.co_emp=0 and a2.st_bodRep ='S')\n";
                strSQL+=" inner join tbm_usr as a3 on (a1.co_usr=a3.co_usr)\n";
                strSQL+=" where a1.co_mnu ="+objParSis.getCodigoMenu()+" and a1.co_emp=0 and a1.co_loc=1 and a1.co_usr="+ objParSis.getCodigoUsuario() + "";;
                strSQL+=" order by 1\n";
              }

                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(false));
                    vecReg.add(INT_TBL_EMP_COD_EMP,rstLoc.getString("bodega_origen"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblModBodOrigen.setData(vecDat);
                tblBodOrigen.setModel(objTblModBodOrigen);
                vecDat.clear();
                blnMarTodChkTblBod_Origen=false;
            }
            }
        catch (java.sql.SQLException e) {    blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
       
        }
    private boolean cargarBodegaDestino()
    {
        int intCodBodDes;
        boolean blnResBD=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
          Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" select BOD_BODEGA  as bodega_destino from tuvalapi.gp_bodegas where id not in (4,7,10,11,12,13,16,17,18,19,20,21,24,14) order by 1 \n";

                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_EMP_COD_EMP,rstLoc.getString("bodega_destino"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblModBodDestino.setData(vecDat);
                tblBodDestino.setModel(objTblModBodDestino);
                vecDat.clear();
                blnMarTodChkTblBod_Destino=false;
            }
        }
        catch (java.sql.SQLException e) {    blnResBD=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnResBD=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnResBD;
    }
    
    
    private void tblEmpMouseClickedBodOrigen(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        java.util.ArrayList arlAux=new java.util.ArrayList();
        try
        {
            intNumFil=objTblModBodOrigen.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBodOrigen.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK)
            {
                if (blnMarTodChkTblBod_Origen)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodOrigen.setChecked(true, i, INT_TBL_EMP_CHK);
                    }
//                    objTblMod.removeAllSystemHiddenColumns(tblDat);
                    blnMarTodChkTblBod_Origen=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodOrigen.setChecked(false, i, INT_TBL_EMP_CHK);
                        arlAux.add("" + (7 + i));
                    }
//                    objTblMod.addSystemHiddenColumns(arlAux, tblDat);
                    blnMarTodChkTblBod_Origen=true;
                }
                arlAux=null;
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    private boolean configurarTblBodDestino(){
        boolean blnRes=true;
        
     try{
        vecDat=new Vector();    //Almacena los datos
        vecCab=new Vector(5);   //Almacena las cabeceras
        vecCab.clear();  
        vecCab.add(INT_TBL_BOD_LIN,"");
        vecCab.add(INT_TBL_BOD_CHK,"");
        vecCab.add(INT_TBL_BOD_COD_BOD,"Bodegas Destino");
        
        objTblModBodDestino=new ZafTblMod();
        objTblModBodDestino.setHeader(vecCab);
        tblBodDestino.setModel(objTblModBodDestino);
        //Configurar JTable: Establecer tipo de selección.
        tblBodDestino.setRowSelectionAllowed(true);
        tblBodDestino.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblBodDestino);
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblBodDestino.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        javax.swing.table.TableColumnModel tcmAuxBD=tblBodDestino.getColumnModel();
        tcmAuxBD.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
        tcmAuxBD.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
        tcmAuxBD.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(200);

        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
        tcmAuxBD.getColumn(INT_TBL_BOD_CHK).setResizable(false);
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblBodDestino.getTableHeader().setReorderingAllowed(false);
        
        tblBodDestino.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodDestino());
        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBodDestino.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBDestinoMouseClicked(evt);
            }
        });
        

        //Configurar JTable: Establecer columnas editables.
        vecAux=new Vector();
        vecAux.add("" + INT_TBL_BOD_CHK);
        objTblModBodDestino.setColumnasEditables(vecAux);
        vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            //  objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
            //  objTblBus=new ZafTblBus(tblEmp);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabBD=new ZafTblFilCab(tblBodDestino);
            tcmAuxBD.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCabBD);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChklBD=new ZafTblCelRenChk();
            tcmAuxBD.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChklBD);
            objTblCelRenChklBD=null;
            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAuxBD.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChklBD=new ZafTblCelEdiChk(tblBodDestino);
            tcmAuxBD.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChklBD);
//            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    if (objTblCelEdiChk.isChecked())
////                    {
////                        objTblMod.removeSystemHiddenColumn(7 + tblEmp.getSelectedRow(), tblDat);
////                    }
////                    else
////                    {
////                        objTblMod.addSystemHiddenColumn(7 + tblEmp.getSelectedRow(), tblDat);
////                    }
//                }
//            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodDestino.setModoOperacion(objTblModBodDestino.INT_TBL_EDI); // LA TABLA SE PONE EN MODO editar -- Existen modo insercion, edicion, y no 
            //Libero los objetos auxiliares.
            tcmAuxBD=null;
     }
     catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
//    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt)
//    {
//        int i, intNumFil;
//        java.util.ArrayList arlAux=new java.util.ArrayList();
//        try
//        {
//            intNumFil=objTblModEmp.getRowCountTrue();
//            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
//            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBodOrigen.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK)
//            {
//                if (blnMarTodChkTblEmp)
//                {
//                    //Mostrar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                        objTblModEmp.setChecked(true, i, INT_TBL_EMP_CHK);
//                    }
////                    objTblMod.removeAllSystemHiddenColumns(tblDat);
//                    blnMarTodChkTblEmp=false;
//                }
//                else
//                {
//                    //Ocultar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                        objTblModEmp.setChecked(false, i, INT_TBL_EMP_CHK);
//                        arlAux.add("" + (7 + i));
//                    }
////                    objTblMod.addSystemHiddenColumns(arlAux, tblDat);
//                    blnMarTodChkTblEmp=true;
//                }
//                arlAux=null;
//            }
//        }
//        catch (Exception e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//    }
    
    
    private void tblBDestinoMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        java.util.ArrayList arlAux=new java.util.ArrayList();
        try
        {
            System.out.println("destino.... click");
            intNumFil=objTblModBodDestino.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBodDestino.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK) // puede ser ese boton que ambos utilizan
            {
                if (blnMarTodChkTblBod_Destino)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodDestino.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
//                    objTblMod.removeAllSystemHiddenColumns(tblDat);
                    blnMarTodChkTblBod_Destino=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodDestino.setChecked(false, i, INT_TBL_BOD_CHK);
                        arlAux.add("" + (7 + i));
                    }
//                    objTblMod.addSystemHiddenColumns(arlAux, tblDat);
                    blnMarTodChkTblBod_Destino=true;
                }
                arlAux=null;
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
	 //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(7);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_PED,"Pedido/Documento");
            vecCab.add(INT_TBL_DAT_FEC_PED, "Fecha");
            vecCab.add(INT_TBL_DAT_BOD_ORI,"Bodega Origen");
            vecCab.add(INT_TBL_DAT_BOD_DES,"Bodega Destino");
            vecCab.add(INT_TBL_DAT_USR_DOC,"Usuario Doc.");
            vecCab.add(INT_TBL_DAT_ITEM,"Item");
            vecCab.add(INT_TBL_DAT_ITEM_ZAF,"Cód. Zafiro");
            vecCab.add(INT_TBL_DAT_ITEM_3L,"Item 3L");
            vecCab.add(INT_TBL_DAT_ITEM_DES,"Descripción");
            vecCab.add(INT_TBL_DAT_PES_UNI,"Peso Unit.");
            vecCab.add(INT_TBL_DAT_PES_TOT,"Peso Total");
            vecCab.add(INT_TBL_DAT_CAN_PED,"Cant.Total");
            vecCab.add(INT_TBL_DAT_CAN_DES,"Cant. Desp.");
            vecCab.add(INT_TBL_DAT_PES_DES,"Peso Desp.");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Cant. Pen.");
            vecCab.add(INT_TBL_DAT_PES_PEN,"Peso Pen.");
            vecCab.add(INT_TBL_DAT_DIA_PEN,"Días Pendientes");
            vecCab.add(INT_TBL_DAT_UBI_ITM,"Ubicación");

            

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_PED).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PED).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_BOD_ORI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BOD_DES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_PEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_USR_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_ITEM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ITEM_ZAF).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ITEM_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ITEM_DES).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_PEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(100);
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            // tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);

            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            //Columna Código de la  Empresa
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ITM).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            //Otras Columnas
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAux.getColumn(INT_TBL_DAT_PED).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_FAC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_EST_FAC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_FEC_FAC).setCellRenderer(objTblCelRenLbl);
            
//            // JM 8/Dic/202023 
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_BTN_DET);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
//            
//            
//            //botones agregados
//            objTblCelRenBut=new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_DAT_BTN_DET).setCellRenderer(objTblCelRenBut);
//            objTblCelRenBut=null;
//            
//            ButDetalle butDetalle = new ButDetalle(tblDat, INT_TBL_DAT_BTN_DET); //*****
            
            // JM 8/Dic/202023 
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento. sorting
            objTblOrd=new ZafTblOrd(tblDat);
            //Libero los objetos auxiliares.
            tcmAux=null;
            //configurarVenConClientes();
//            configurarVenConVendedor();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
//     */
//    private class ButDetalle extends Librerias.ZafTableColBut.ZafTableColBut_uni{
//        public ButDetalle(javax.swing.JTable tbl, int intIdx){
//            super(tbl,intIdx, "Detalle pendiente.");
//           
//        }
//        @Override
//        public void butCLick() {
//           int intCol = tblDat.getSelectedRow();
//           String strPedido = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_PED  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_PED  ).toString());
//          llamarVen(strPedido);
//        }
//    }
//    
//    
//    private void llamarVen(String strPedido){
//            ZafCom119_01 obj1 = new  Compras.ZafCom119.ZafCom119_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strPedido);
////            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
//            obj1.show();
//    }
       
    /**
     * Esta función permite consultar las empresas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las empresae.
     * <LI>Si se ingresa a cualquier otra empresa se muestra sólo la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
//    private boolean cargarEmp()
//    {
//        int intCodEmp;
//        boolean blnRes=true;
//        java.sql.Connection conLoc;
//        java.sql.Statement stmLoc;
//        java.sql.ResultSet rstLoc;
//        try
//        {
//          Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
//            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
//            // conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
//            if (conLoc!=null)
//            {
//            stmLoc = conLoc.createStatement();
//                //Armar la sentencia SQL.
//              if (objParSis.getCodigoUsuario()==1){
//                strSQL="";
//                strSQL+=" select a2.tx_descor as bod_bodega \n";
//                strSQL+=" FROM tbm_bod as a2 \n";
//                strSQL+=" where a2.co_emp=0 and a2.st_bodRep ='S'   ";
//                strSQL+=" order by 1\n";
//              }
//              else{
//                strSQL="";
//                strSQL+=" select  a2.tx_descor as bod_bodega \n";
//                strSQL+=" from tbr_bodUsr as a1 \n";
//                strSQL+=" inner join tbm_bod as a2 on (a1.co_bod=a2.co_bod and a2.co_emp=0 and a2.st_bodRep ='S')\n";
//                strSQL+=" inner join tbm_usr as a3 on (a1.co_usr=a3.co_usr)\n";
//                strSQL+="  where a1.co_emp=0 and a1.st_reg in ('P','A') and  a1.co_usr="+ objParSis.getCodigoUsuario() + "";;
//                strSQL+=" order by 1\n";
//              }
//
//                rstLoc=stmLoc.executeQuery(strSQL);
//                //Limpiar vector de datos.
//                vecDat.clear();
//                //Obtener los registros.
//                while (rstLoc.next())
//                {
//                    vecReg=new Vector();
//                    vecReg.add(INT_TBL_EMP_LIN,"");
//                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(false));
//                    vecReg.add(INT_TBL_EMP_COD_EMP,rstLoc.getString("bod_bodega"));
//                    vecDat.add(vecReg);
//                }
//                rstLoc.close();
//                stmLoc.close();
//                conLoc.close();
//                rstLoc=null;
//                stmLoc=null;
//                conLoc=null;
//                //Asignar vectores al modelo.
//                objTblModEmp.setData(vecDat);
//                tblBodOrigen.setModel(objTblModEmp);
//                vecDat.clear();
//                blnMarTodChkTblEmp=false;
//            }
//        }
//        catch (java.sql.SQLException e) 
//        {    
//            blnRes=false;  
//            objUti.mostrarMsgErr_F1(this, e);  
//        }
//        catch (Exception e)  
//        {  
//            blnRes=false;  
//            objUti.mostrarMsgErr_F1(this, e); 
//        }
//        return blnRes;
//    } 

    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter //
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_PED:
                    strMsg="Pedido/Documento";
                    break;
                case INT_TBL_DAT_FEC_PED:
                    strMsg="Fecha del Ped./Documento";
                    break;    
                case INT_TBL_DAT_BOD_ORI:
                    strMsg="Bodega Origen";
                    break;
                case INT_TBL_DAT_BOD_DES:
                    strMsg="Bodega Destino";
                    break;
                case INT_TBL_DAT_USR_DOC:
                    strMsg="Usuario que generó el documento";
                    break;
                case INT_TBL_DAT_ITEM:
                    strMsg="Items";
                    break;
                case INT_TBL_DAT_ITEM_ZAF:
                    strMsg="Código Zafiro";
                    break;
                case INT_TBL_DAT_ITEM_3L:
                    strMsg="3 Letras";
                    break;
                case INT_TBL_DAT_ITEM_DES:
                    strMsg="Descripción";
                    break;
                case INT_TBL_DAT_PES_UNI:
                    strMsg="Peso Unitario";
                    break;
                case INT_TBL_DAT_PES_TOT:
                    strMsg="Peso Total";
                    break;
                case INT_TBL_DAT_CAN_PED:
                    strMsg="Cantidad Solicitada";
                    break;
                case INT_TBL_DAT_CAN_DES:
                    strMsg="Cantidad Despachada";
                    break;
                case INT_TBL_DAT_PES_DES:
                    strMsg="Peso Despachado";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg="Cantidad Pendiente";
                    break;
                case INT_TBL_DAT_PES_PEN:
                    strMsg="Peso Pendiente";
                    break;
                case INT_TBL_DAT_DIA_PEN:
                    strMsg="Días Pendientes";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación";
                    break;
                    
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaEmp extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBodOrigen.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_LIN:
                    strMsg="";
                    break;
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Bodega";
                    break;
               
                default:
                    strMsg="";
                    break;
            }
            tblBodOrigen.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBod, i, j;
        //Validar que esté seleccionada al menos un local si es la Bodega grupo.
        intNumFilTblBod=objTblModBodOrigen.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBod; j++)
        {
            if (objTblModBodOrigen.isChecked(j, INT_TBL_EMP_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una Bodega de Origen.<BR>Seleccione una Bodega de Origen y vuelva a intentarlo.</HTML>");
            tblBodOrigen.requestFocus();
            return false;
        }
        
        return true;
    }
    
     private boolean isCamValBD() 
    {
        int intNumFilTblBodDes, i, h;
        //Validar que esté seleccionada al menos un local si es la Bodega grupo.
        intNumFilTblBodDes=objTblModBodDestino.getRowCountTrue();
        i=0;
        for (h=0; h<intNumFilTblBodDes; h++)
        {
            if (objTblModBodDestino.isChecked(h, INT_TBL_BOD_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una Bodega Destino.<BR>Seleccione una Bodega Destino y vuelva a intentarlo.</HTML>");
            tblBodDestino.requestFocus();
            return false;
        }
        
        return true;
    }
    
         /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg()) 
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
    
     
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
     private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        boolean blnEmp = false,  blnEmp1 = false;
        String sqlFil ="",strEmp ="",sqlFilBD="",strBodOrig="";
        java.sql.Connection conLoc;//conectarme
        java.sql.Statement stmLoc;//conectarme
        java.sql.ResultSet rstLoc;//obtener informacion
        
        int intNumFilTblEmp,  j,intNumFilTblBD, intEstReg=0;
        String strAux="";
        
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intNumFilTblEmp = objTblModBodOrigen.getRowCountTrue();
            intNumFilTblBD = objTblModBodDestino.getRowCountTrue();
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                
                //Filtrar por "Item".
                if (objVenConItm01_01.isCondicionAplicada())
                {
                    strAux+=objVenConItm01_01.getCondicionesSQL();
                }
                
                
                for (int k = 0; k < tblBodOrigen.getRowCount(); k++) 
                {
                    if (tblBodOrigen.getValueAt(k, INT_TBL_EMP_CHK).toString().equals("true"))
                    {
                        if (!blnEmp) //Primera vez que ingresa.
                        {
                            strEmp += " ( ";
                        } else {
                            strEmp += " OR ";
                        }
                        strEmp += " (a.bodega_origen=" + objUti.codificar(tblBodOrigen.getValueAt(k, INT_TBL_EMP_COD_EMP), 0)   + ") ";
                        blnEmp = true;
                    }
                }
                if (blnEmp) 
                {
                    strEmp += " ) ";
                    sqlFil += strEmp;
                }
                
                for (int m = 0; m < tblBodDestino.getRowCount(); m++) 
                {
                    if (tblBodDestino.getValueAt(m, INT_TBL_EMP_CHK).toString().equals("true"))
                    {
                        if (!blnEmp1) //Primera vez que ingresa.
                        {
                            strBodOrig += " ( ";
                        } else {
                            strBodOrig += " OR ";
                        }
                        strBodOrig += " (a.bodega_destino=" + objUti.codificar(tblBodDestino.getValueAt(m, INT_TBL_BOD_COD_BOD), 0)   + ") ";
                        blnEmp1 = true;
                    }
                }
                if (blnEmp) 
                {
//                    strEmp += " ) ";
//                    sqlFil += strEmp;
                    strBodOrig += " ) ";
                    sqlFilBD += strBodOrig;
                }
                    strSQL ="";
                    strSQL+="select a.* from tuvalapi.vw_ListadoDetalladoEgresosPendientes as a \n";
                    strSQL+="inner join tuvalapi.gp_articulos as art on (a.item=art.art_codigo)\n";
//                    strSQL+=" select a.pedido, a.fecha_pedido, a.bodega_origen, a.bodega_destino, a.item, art.ART_CODIGO_ZAFIRO as codigo_zafiro, a.item3L, a.descripcion, a.peso_unit, a.peso_total, a.cant_pedida, a.cant_trasladada, a.peso_trasladado, a.Cant_Pendiente, a.peso_pendiente , a.dias_pendientes \n";
//                    strSQL+=" from \n";
//                    strSQL+="	(	select a2.pedido ,a2.created_at as fecha_pedido ,a3.bodega_origen ,a3.bodega_destino , (a1.ART_PESO*a1.ART_CANTIDAD) as peso_total,\n";
//                    strSQL+="            datediff(current_date(),a2.created_at) as dias_pendientes ,a1.ART_CODIGO as item ,a1.ART_CODIGO_TRES_LETRAS as item3L, a1.ART_DESCRIPCION as descripcion , \n";
//                    strSQL+="            a1.ART_PESO as peso_unit, a3.cantidad as cant_pedida ,a3.trasladado as cant_trasladada ,(a1.ART_PESO*a3.trasladado) as peso_trasladado, \n";
//                    strSQL+="            (a3.cantidad -  CASE WHEN  a3.trasladado IS NULL THEN 0 ELSE a3.trasladado END )as Cant_Pendiente , \n";
//                    strSQL+="             ((a1.ART_PESO)*(a3.cantidad - CASE WHEN  a3.trasladado IS NULL THEN 0 ELSE a3.trasladado END )) as peso_pendiente   \n";
//                    strSQL+="		from tuvalapi.gp_pedido_detalles as a1 \n";
//                    strSQL+="			inner join tuvalapi.gp_pedidos as a2 on (a1.pedido_id=a2.id) \n";
//                    strSQL+="			inner join tuvalapi.gp_traslados_ventas as a3 on (a2.id=a3.pedido_id and a1.id=a3.detalle_id) \n";
//                    strSQL+="		where a2.estado not in('facturado','eliminado','rechazado')    \n";
//                    strSQL+="		union all\n";
//                    strSQL+="		select a1.documento, a1.created_at as fecha_reposicion ,a1.bodega_origen, a1.bodega_destino, (a1.ART_PESO*a1.cantidad) as peso_total ,\n";
//                    strSQL+="            datediff(current_date(),a2.created_at) as dias_pendientes, a1.art_codigo as item , a1.ART_CODIGO_TRES_LETRAS as item3L, a1.ART_DESCRIPCION as descripcion ,\n";
//                    strSQL+="            a1.ART_PESO as peso_unit, a1.cantidad as cant_pedida ,a1.trasladado as cant_trasladada , (a1.ART_PESO*a1.trasladado) as peso_trasladado,\n";
//                    strSQL+="            (a1.cantidad - CASE WHEN  a1.trasladado IS NULL THEN 0 ELSE a1.trasladado END ) as Cant_Pendiente ,\n";
//                    strSQL+="            ((a1.ART_PESO)*(a1.cantidad - CASE WHEN  a1.trasladado IS NULL THEN 0 ELSE a1.trasladado END )) as peso_pendiente  \n";
//                    strSQL+="		from tuvalapi.gp_traslados_reposicions as a1  \n";
//                    strSQL+="			inner join tuvalapi.gp_traslados_reposicion_cabeceras as a2 on (a1.Documento=a2.Documento and a1.cabecera_id=a2.id) \n";
//                    strSQL+="		where a1.completado is null \n";
//                    strSQL+=") as a\n";
//                    strSQL+="inner join tuvalapi.gp_articulos as art on (a.item=art.art_codigo)\n";
                    strSQL+="where a.Cant_Pendiente !=0 \n";
                    if(sqlFil.length()>0){
                        strSQL+=" and "+sqlFil;
                    }
                    if(sqlFilBD.length()>0){
                         strSQL+=" and "+sqlFilBD;
                    }
                    if(txtOd2.getText().length()>0){
                    strSQL+=" AND a.pedido like "+ objUti.codificar(txtOd2.getText())+" \n";
                    }
                    strSQL+=""+strAux;
                    strSQL+=" ORDER BY a.dias_pendientes,a.pedido desc ; \n";
//                    if(txtCodCli.getText().length()>0){
//                    strSQL+=" AND peds.CLI_RAZON_SOCIAL like "+ objUti.codificar(txtNomCli.getText())+" \
                    
                    System.out.println("ZafCom121.cargarDetReg.EMPRESAS: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    //ArrayList<String> itemsListado = new ArrayList<>();
                    //ArrayList<String> bodegasListado = new ArrayList<>();
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next()) 
                {
                    String item = rstLoc.getString("item");
                    String bodega = rstLoc.getString("bodega_origen");
                    
                    // Agregar a los arreglos
                    arrItems.add(rstLoc.getString("item"));
                    arrBodegas.add(rstLoc.getString("bodega_origen"));
                    
                    if (blnCon) 
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_PED, rstLoc.getString("pedido"));
                        vecReg.add(INT_TBL_DAT_FEC_PED, rstLoc.getString("fecha_pedido"));
                        vecReg.add(INT_TBL_DAT_BOD_ORI, rstLoc.getString("bodega_origen"));
                        vecReg.add(INT_TBL_DAT_BOD_DES, rstLoc.getString("bodega_destino"));
                        vecReg.add(INT_TBL_DAT_USR_DOC, rstLoc.getString("Usuario_doc"));
                        vecReg.add(INT_TBL_DAT_ITEM, rstLoc.getString("item"));
                        vecReg.add(INT_TBL_DAT_ITEM_ZAF, rstLoc.getString("codigo_zafiro"));
                        vecReg.add(INT_TBL_DAT_ITEM_3L, rstLoc.getString("item3L"));
                        vecReg.add(INT_TBL_DAT_ITEM_DES, rstLoc.getString("descripcion"));
                        vecReg.add(INT_TBL_DAT_PES_UNI, rstLoc.getDouble("peso_unit"));
                        vecReg.add(INT_TBL_DAT_PES_TOT, rstLoc.getDouble("peso_total"));
                        vecReg.add(INT_TBL_DAT_CAN_PED, rstLoc.getDouble("cant_pedida"));
                        vecReg.add(INT_TBL_DAT_CAN_DES, rstLoc.getDouble("cant_trasladada")); 
                        vecReg.add(INT_TBL_DAT_PES_DES, rstLoc.getDouble("peso_trasladado"));
                        vecReg.add(INT_TBL_DAT_CAN_PEN, rstLoc.getDouble("Cant_Pendiente"));
                        vecReg.add(INT_TBL_DAT_PES_PEN, rstLoc.getDouble("peso_pendiente"));
                        vecReg.add(INT_TBL_DAT_DIA_PEN, rstLoc.getInt("dias_pendientes"));
                        
                        //vecReg.add(INT_TBL_DAT_UBI_ITM, "");
                        //vecReg.add(INT_TBL_DAT_UBI_ITM, getUbicacionItem(rstLoc.getString("ubicacion")));
                        vecReg.add(INT_TBL_DAT_UBI_ITM,"" );
                        
                        //vecReg.add(INT_TBL_DAT_PES_PEN, "");
                        
                        
                        vecDat.add(vecReg);
                    }
                    else 
                    {
                        break;
                    }
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if(realizarNuevaConsulta()){
                    if(AsignarUbicaciones()){
                        System.out.println(" OK Denis xD ");
                    }
                }
                if (blnCon) 
                {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } 
                else 
                {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                
                
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        } 
        catch (java.sql.SQLException e) { blnRes = false;   objUti.mostrarMsgErr_F1(this, e); } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenConMySQL.INT_BUT_ACE)
                    {
              
                        txtOd2.setText(vcoLoc.getValueAt(2));
                    }
                    break;
//                case 1: //Búsqueda directa por "Nombre".
//                    if (vcoLoc.buscar("a1.USERID", txtCodVen.getText()))
//                    {
//                        txtCodVen.setText(vcoLoc.getValueAt(1));
//                    }
//                    else
//                    {
//                        vcoLoc.setCampoBusqueda(2);
//                        vcoLoc.setCriterio1(11);
//                        vcoLoc.cargarDatos();
//                        vcoLoc.setVisible(true);
//                        if (vcoLoc.getSelectedButton()==ZafVenConMySQL.INT_BUT_ACE)
//                        {
//                            txtCodVen.setText(vcoLoc.getValueAt(2));
//                        }
//                        else
//                        {
//                            txtCodVen.setText(strCodVen);
//                        }
//                    }
//                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private class ZafMouMotAdaBodOri extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBodOrigen.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_LIN:
                    strMsg="";
                    break;
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Bodega Origen";
                    break;
               
                default:
                    strMsg="";
                    break;
            }
            tblBodOrigen.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ZafMouMotAdaBodDestino extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBodDestino.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Bodega Destino";
                    break;
               
                default:
                    strMsg="";
                    break;
            }
            tblBodDestino.getTableHeader().setToolTipText(strMsg);
        }
    }
    /**
     * DT
     */
    private boolean realizarNuevaConsulta() {
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        //ResultSet rstLoc = null;

        try {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc!=null)
                {
                    stmLoc=conLoc.createStatement();
                    StringBuilder strSQL = new StringBuilder();// se utiliza para concatenar 
                    strSQL.append("SELECT TRIM(ITEMNMBR) as item, TRIM(locncode) as bodega_origen, TRIM(bin) as ubicacion FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.iv00112 WHERE ");
                    
                    for (int i = 0; i < arrItems.size(); i++) { //recorre el arreglo y arma la consulta 
                        if (i > 0) {
                            strSQL.append(" OR ");
                        }
                        strSQL.append("(ITEMNMBR = '").append(arrItems.get(i))
                              .append("' AND locncode = '").append(arrBodegas.get(i)).append("')");
                    }

                    // Ejecutar la consulta
                    rstLoc = stmLoc.executeQuery(strSQL.toString());
                    ubicacionMap = new HashMap();
                    while (rstLoc.next()) {
                        // Aquí puedes procesar los resultados de la nueva consulta
//                        System.out.println("Item: " + rstLoc.getString("item"));
//                        System.out.println("Bodega: " + rstLoc.getString("bodega"));
//                        System.out.println("Ubicación: " + rstLoc.getString("ubicacion"));
//                        ubicacionMap.get(rstLoc.getString("ubicacion")); //obtendria la ubicacion
                        
                        ubicacionMap.put(rstLoc.getString("item") /*Mando mi clave ID*/, rstLoc.getString("ubicacion") /*Mando mi dato para despues buscar*/);
                    }
                    rstLoc.close();
                    stmLoc.close();
                    rstLoc=null;
                    stmLoc=null;
                }
            conLoc.close();
            conLoc=null;

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
    
     
   /**
    * 
    * @return 
    */ 
    private boolean AsignarUbicaciones(){
        boolean blnRes=true;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
//                System.out.println("dato a buscar " + objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString() ) ;
//                System.out.println("GET MAP " + ubicacionMap.get(objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString()));
                //objTblMod.setValueAt VOY a poner mi valor en ese objeto de mi tabla 
                // ubicacionMap.get ===>  Me obtiene mi dato de la coleccion "ubicacionMap"
                objTblMod.setValueAt( ubicacionMap.get(objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString()) /* OBTENER el dato */  , i /*Colocar en esta fila*/, INT_TBL_DAT_UBI_ITM /*En esta columna */); 
            }
        }
        catch (Exception e)  
        {
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;     
    }
    

}