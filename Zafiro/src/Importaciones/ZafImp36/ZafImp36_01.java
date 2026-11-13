/*
 * ZafImp32_04.java
 * Created on 9 de marzo de 2012, 9:12
 * v0.1 
 */
package Importaciones.ZafImp36;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
 *
 * @author  Ingrid
 */
public class ZafImp36_01 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable:        
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_COD_LOC=3;
    final int INT_TBL_DAT_COD_TIP_DOC=4;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    final int INT_TBL_DAT_COD_DOC=7;
    final int INT_TBL_DAT_NUM_DOC_SIS=8;
    final int INT_TBL_DAT_NUM_PED_PRE=9;
    final int INT_TBL_DAT_FEC_DOC=10;
    final int INT_TBL_DAT_COD_IMP=11;
    final int INT_TBL_DAT_NOM_IMP=12;
    final int INT_TBL_DAT_COD_EXP=13;
    final int INT_TBL_DAT_NOM_EXP=14;
    final int INT_TBL_DAT_TOT_FOB_CFR=15;
    final int INT_TBL_DAT_VAL_CFR_ARA=16;
    final int INT_TBL_DAT_CHK_AUX=17;
    final int INT_TBL_DAT_NUM_PED_VER=18;
    

    //contendra el pedido previo que se cargara en la nota de pedido
    private ArrayList arlDatCab, arlRegCab;  //contendra la cabecera, sea referenciada o cargada a traves del boton consultar
    final int INT_ARL_COD_EMP=0;
    final int INT_ARL_COD_LOC=1;
    final int INT_ARL_COD_TIP_DOC=2;
    final int INT_ARL_DES_COR_TIP_DOC=3;
    final int INT_ARL_DES_LAR_TIP_DOC=4;
    final int INT_ARL_COD_DOC=5;
    final int INT_ARL_NUM_DOC_SIS=6;
    final int INT_ARL_NUM_DOC_PED=7;
    final int INT_ARL_FEC_DOC=8;
    final int INT_ARL_COD_IMP=9;
    final int INT_ARL_NOM_IMP=10;
    final int INT_ARL_COD_EXP=11;
    final int INT_ARL_NOM_EXP=12;
    final int INT_ARL_TOT_FOB_CFR=13;
    final int INT_ARL_VAL_CFR_ARA=14;
    final int INT_ARL_VAL_CHK=15;
    final int INT_ARL_VAL_CHK_AUX=16;
    final int INT_ARL_NUM_DOC_PED_VER=17;
   
    

    //Variable
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafThreadGUI objThrGUI;

    private Vector vecReg, vecDat, vecCab, vecAux;
    private char chrOpcManDatTooBar;
    private int intButPre;
    private String strNomPedSelUsr;
    private String strSQL;
    
    
    private String strCodTipDocRef, strCodDocRef;

    public ZafImp36_01(java.awt.Frame parent, boolean modal, ZafParSis obj, char opcionTooBar) {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        arlDatCab=new ArrayList();
        intButPre=0;//no se ha seleccionado el boton
        chrOpcManDatTooBar=opcionTooBar;
        vecDat=new Vector();    //Almacena los datos
        if(configurarFrm()){
        }
    }

    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.            
            vecCab=new Vector(19);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_SIS,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED_PRE,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Importador.");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_TOT_FOB_CFR,"Tot.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_VAL_CFR_ARA,"Val.Cfr.Ara.");
            vecCab.add(INT_TBL_DAT_CHK_AUX,"");
            vecCab.add(INT_TBL_DAT_NUM_PED_VER,"Núm.Ped.Ver.");
            
            
            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            vecCab.clear();
            vecCab=null;
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(64);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_PRE).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(56);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(56);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR_ARA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUX).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_VER).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                boolean blnChkSel;
                int intCodImp=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    
//                    if(!objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)){//antes de seleccionarlo está desmarcado, por eso valido asi
//                        intCodImp=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_IMP).toString();
//                        
//                        
//                        
//                        
//                    }
                    
                    
                    
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    objTblMod.removeAllRows();
//                    if(objTblMod.isChecked(intFil, intCol)){
//                        cargarMovReg(intFil);
//                    }                        
//                    
//                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                        if(i!=intFil){
//                            objTblMod.setChecked(false, i, intCol);
//                        }
//                    }
                }

            });

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_PED_VER, tblDat);
            

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR_ARA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
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
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC_SIS:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_NUM_PED_PRE:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_COD_IMP:
                    strMsg="Código de importador";
                    break;
                case INT_TBL_DAT_NOM_IMP:
                    strMsg="Nombre de importador";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código de exportador";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre de exportador";
                    break;
                case INT_TBL_DAT_TOT_FOB_CFR:
                    strMsg="Total FOB/CFR";
                    break;
                case INT_TBL_DAT_VAL_CFR_ARA:
                    strMsg="Valor CFR/ARA";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPgrSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de Pedido Facturado/Embarcado");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(310, 56));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBot.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPgrSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPgrSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPgrSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPgrSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPgrSis, java.awt.BorderLayout.EAST);

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if(objTblMod.isDataModelChanged()){
            intButPre=1;//se presiono aceptar
        }
        else{
            intButPre=2;//se presiono aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButPre=2;//se presiono cancelar
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (butCon.getText().equals("Consultar")){
            //blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            intButPre=2;//se presiono la X de cerrar formulario
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panPgrSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
     
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            //Limpiar objetos.
            if (!cargarPedidosEmbarcados()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objTblMod.setDataModelChanged(true);
            objThrGUI=null;
        }
    }
    
    /**
     * Funciòn que permite cargar los pedidos facturados/embarcados abiertos
     * @return true Si se pudo realizar la operaciòn
     * <BR> false Caso contrario
     */
    public boolean cargarPedidosEmbarcados(){
        boolean blnRes=true;
        String strNumDocPedCon="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numDoc";
                strSQL+=" , a1.tx_numDoc2, a1.tx_numDoc4, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" , a1.co_imp, a4.tx_nom AS tx_nomImp, a1.co_exp, a3.tx_nom AS tx_nomExp, a1.nd_totFobCfr, a1.nd_totCfrAra";
                strSQL+=" , a5.co_empRel, a1.tx_numDocCon";
                strSQL+=" , (CASE WHEN a1.tx_numdoc4 IS NOT NULL THEN a1.tx_numdoc4 ELSE a1.tx_numdoc2 END) AS tx_numPed";
                strSQL+="  FROM (((tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_detPedConCabPedEmbImp AS a5";
                strSQL+=" 		ON a1.co_emp=a5.co_empRel AND a1.co_loc=a5.co_locRel AND a1.co_tipDoc=a5.co_tipDocRel AND a1.co_doc=a5.co_docRel)";
                strSQL+=" 	INNER JOIN tbm_emp AS a4 ON a1.co_imp=a4.co_emp)";
                strSQL+="                            INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_doc='A' AND a1.st_cie='N'";
                strSQL+=" AND a1.tx_numDocCon IS NULL";
                if(chrOpcManDatTooBar=='n'){
                    
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numDoc , a1.tx_numDoc2, a1.tx_numDoc4";
                    strSQL+=" , a2.tx_desCor, a2.tx_desLar , a1.co_imp, a4.tx_nom, a1.co_exp, a3.tx_nom, a1.nd_totFobCfr, a1.nd_totCfrAra";
                    strSQL+=" , a5.co_empRel, a1.tx_numDocCon";                    
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_numDoc4";
                }
                
                if(chrOpcManDatTooBar!='n'){
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numDoc , a1.tx_numDoc2, a1.tx_numDoc4";
                    strSQL+=" , a2.tx_desCor, a2.tx_desLar , a1.co_imp, a4.tx_nom AS tx_nomImp, a1.co_exp, a3.tx_nom AS tx_nomExp, a1.nd_totFobCfr";
                    strSQL+=" , a1.nd_totCfrAra , a5.co_empRel, a1.tx_numDocCon";
                    strSQL+=" , (CASE WHEN a1.tx_numdoc4 IS NOT NULL THEN a1.tx_numdoc4 ELSE a1.tx_numdoc2 END) AS tx_numPed";
                    strSQL+=" FROM (((tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_detPedConCabPedEmbImp AS a5";
                    strSQL+=" ON a1.co_emp=a5.co_empRel AND a1.co_loc=a5.co_locRel AND a1.co_tipDoc=a5.co_tipDocRel AND a1.co_doc=a5.co_docRel)";
                    strSQL+=" INNER JOIN tbm_emp AS a4 ON a1.co_imp=a4.co_emp)";
                    strSQL+=" INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL+=" WHERE a1.st_reg='A' AND a1.st_doc='A' AND a1.st_cie='N'";
                    strSQL+=" AND a5.co_emp=" + objParSis.getCodigoEmpresa() + " AND a5.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a5.co_tipDoc=" + strCodTipDocRef + " AND a5.co_doc=" + strCodDocRef + "";
                    
                    
                    
                    
                    strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, tx_numDoc4";
                    strSQL+=" ";
                }
                
                System.out.println("SQL cargarPedidosEmbarcados: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,             null);
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC_SIS,     rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_DAT_NUM_PED_PRE,     rst.getString("tx_numPed"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));    
                    vecReg.add(INT_TBL_DAT_COD_IMP,         rst.getString("co_imp"));
                    vecReg.add(INT_TBL_DAT_NOM_IMP,         rst.getString("tx_nomImp"));
                    vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));
                    vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));
                    vecReg.add(INT_TBL_DAT_TOT_FOB_CFR,     objUti.redondearBigDecimal(rst.getString("nd_totFobCfr"), objParSis.getDecimalesMostrar()));
                    vecReg.add(INT_TBL_DAT_VAL_CFR_ARA,     objUti.redondearBigDecimal(rst.getString("nd_totCfrAra"), objParSis.getDecimalesMostrar()));
                    vecReg.add(INT_TBL_DAT_CHK_AUX,             null);
                    vecReg.add(INT_TBL_DAT_NUM_PED_VER,     rst.getString("tx_numDoc2"));
                    
                    
                    
                    strNumDocPedCon=rst.getObject("tx_numDocCon")==null?"":rst.getString("tx_numDocCon");
                    
                    if(strNumDocPedCon.length()==0){
                        vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CHK);
                        vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CHK_AUX);
                    }
                    else{
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AUX);
                    }

                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
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

    
    public ArrayList getCabeceraSeleccionada(){
        try{
            if(intButPre==1){//se refresca el arreglo con los nuevos datos
                arlDatCab.clear();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        arlRegCab=new ArrayList();
                        arlRegCab.add(INT_ARL_COD_EMP,          objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP));
                        arlRegCab.add(INT_ARL_COD_LOC,          objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC));
                        arlRegCab.add(INT_ARL_COD_TIP_DOC,      objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                        arlRegCab.add(INT_ARL_DES_COR_TIP_DOC,  objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_TIP_DOC));
                        arlRegCab.add(INT_ARL_DES_LAR_TIP_DOC,  objTblMod.getValueAt(i, INT_TBL_DAT_DES_LAR_TIP_DOC));
                        arlRegCab.add(INT_ARL_COD_DOC,          objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC));
                        arlRegCab.add(INT_ARL_NUM_DOC_SIS,      objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC_SIS));
                        arlRegCab.add(INT_ARL_NUM_DOC_PED,      objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE));
                        arlRegCab.add(INT_ARL_FEC_DOC,          objTblMod.getValueAt(i, INT_TBL_DAT_FEC_DOC));
                        arlRegCab.add(INT_ARL_COD_IMP,          objTblMod.getValueAt(i, INT_TBL_DAT_COD_IMP));
                        arlRegCab.add(INT_ARL_NOM_IMP,          objTblMod.getValueAt(i, INT_TBL_DAT_NOM_IMP));
                        arlRegCab.add(INT_ARL_COD_EXP,          objTblMod.getValueAt(i, INT_TBL_DAT_COD_EXP));
                        arlRegCab.add(INT_ARL_NOM_EXP,          objTblMod.getValueAt(i, INT_TBL_DAT_NOM_EXP));
                        arlRegCab.add(INT_ARL_TOT_FOB_CFR,      objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR));
                        arlRegCab.add(INT_ARL_VAL_CFR_ARA,      objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA));
                        arlRegCab.add(INT_ARL_VAL_CHK,          objTblMod.getValueAt(i, INT_TBL_DAT_CHK));
                        arlRegCab.add(INT_ARL_VAL_CHK_AUX,      objTblMod.getValueAt(i, INT_TBL_DAT_CHK_AUX));
                        arlRegCab.add(INT_ARL_NUM_DOC_PED_VER,  objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_VER));
                        arlDatCab.add(arlRegCab);
                    }
                }
            }
            else{//no se refresca y se mantienen los datos anteriores
            }
            //System.out.println("arlDatCab: " +arlDatCab.toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatCab;
    }


    public int getBotonPresionado() {
        return intButPre;
    }

    public void setBotonPresionado(int intButPre) {
        this.intButPre = intButPre;
    }


    public void setModoOperacionTooBar(char opcionTooBar){
        chrOpcManDatTooBar=opcionTooBar;
    }

    public String getNotaPedidoSeleccionado() {
        strNomPedSelUsr="";
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                if(strNomPedSelUsr.equals(""))
                    strNomPedSelUsr="" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE).toString());
                else
                    strNomPedSelUsr+="/" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE).toString());
            }
        }
        return strNomPedSelUsr;
    }
 
    
    
    public void setCodTipDocRef(String strCodTipDocRef){
        this.strCodTipDocRef=strCodTipDocRef;
    }

    public void setCodDocRef(String strCodDocRef){
        this.strCodDocRef=strCodDocRef;
    }



    

}