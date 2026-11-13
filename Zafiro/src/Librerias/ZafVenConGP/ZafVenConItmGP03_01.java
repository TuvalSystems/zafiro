/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafVenConGP;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiButVcoGP.ZafTblCelEdiButVcoGP;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.DriverManager;
//////private objMouMotAda objMouMotAda;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel; 

/**
 * Es una clase que muestra un JDialog con opciones para fltrar por "Item".
 * Las opciones que se muestran son las siguientes:
 * <UL>
 * <LI>Item</LI>
 * <LI>Unidad de medida</LI>
 * <LI>Código alterno: Desde - Hasta</LI>
 * <LI>Código alterno: Termina con</LI>
 * <LI>Criterios adicionales: Código alterno 1</LI>
 * <LI>Criterios adicionales: Nombre del item</LI>
 * </UL>
 * @author elino
 */
public class ZafVenConItmGP03_01 extends javax.swing.JDialog
{
    //Constantes:
    public static final int INT_BUT_CAN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    //Variables:
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafVenConGP vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private ZafVenConGP vcoItm;                                   //Ventana de consulta "Item".
    private String strSQL;
    private String strCodAlt, strCodAlt2, strNomItm;            //Contenido del campo al obtener el foco.
    private String strDesCorUniMed, strDesLarUniMed;            //Contenido del campo al obtener el foco.
    private String strAliSQL;                                   //Alias de la sentencia SQL.
    private String strConSQLItm;                                //Condiciones SQL.
    private String strConSQLAtri;                                //Condiciones ATRIBUTOS.    
    //Variables de la clase:
    private int intButSelDlg;                                   //Botón seleccionado en el JDialog.
    
    ZafVenConGP objVenConMarc; //*****************  
    ZafVenConGP objVenConMate; //*****************  
    ZafVenConGP objVenConLine; //***************** 
    ZafVenConGP objVenConClase1; //*****************    
    ZafVenConGP objVenConRefGen; //*****************  
    ZafVenConGP objVenConRefDet1; //*****************    
    ZafVenConGP objVenConRefDet; //*****************  
    ZafVenConGP objVenConAtri2; //*****************      
    ZafVenConGP objVenConAtri3; //*****************       
    
////    private static final int INT_TBL_DAT_LIN=0;
////    private static final int INT_TBL_DAT_OPCION=1;    
////    private static final int INT_TBL_DAT_NOM_ATR1=2;
////    private static final int INT_TBL_DAT_NOM_ATR2=3;
////    private static final int INT_TBL_DAT_BUT_ATR2=4;
////    private static final int INT_TBL_DAT_NOM_ATR3=5;
////    private static final int INT_TBL_DAT_BUT_ATR3=6;
////    private static final int INT_TBL_DAT_NOM_ATR4=5;
    
    private static final int INT_TBL_DAT_COD_ATR=0;
    private static final int INT_TBL_DAT_NOM_ATR=1;
    
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_OPCION=1;
    private static final int INT_TBL_DAT_COD_ATR1=2;    
    private static final int INT_TBL_DAT_NOM_ATR1=3;
////    private static final int INT_TBL_DAT_COD_ZAF=3;
////    private static final int INT_TBL_DAT_NOM_ITEM=4;
////    private static final int INT_TBL_DAT_NOM_TAMA=5;
    private static final int INT_TBL_DAT_COD_ATR2=4;
    private static final int INT_TBL_DAT_NOM_ATR2=5;
    private static final int INT_TBL_DAT_BUT_COM_ATR2=6;     
    private static final int INT_TBL_DAT_COD_ATR3=7;
    private static final int INT_TBL_DAT_NOM_ATR3=8;    
    private static final int INT_TBL_DAT_BUT_COM_ATR3=9;
////    private static final int INT_TBL_DAT_COD_CLASE1=12;
////    private static final int INT_TBL_DAT_NOM_CLASE1=13;    
////    private static final int INT_TBL_DAT_BUT_CLASE1=14;    
////    private static final int INT_TBL_DAT_COD_MAT=15;
////    private static final int INT_TBL_DAT_NOM_MAT=16;
////    private static final int INT_TBL_DAT_BUT_COM_MAT=17; 
    
////    private static final int INT_TBL_DAT_BUT_REF_GE=15;  
////    private static final int INT_TBL_DAT_COD_REF_DET=16;
////    private static final int INT_TBL_DAT_NOM_REF_DET=17;
////    private static final int INT_TBL_DAT_BUT_REF_DET=18;      
    
    private Vector vecDat, vecReg, vecCab, vecAux; 
    private ZafTblMod objTblMod;    
    private ZafColNumerada objColNum;    
    private ZafTblCelRenBut objTblCelRenBut;                     //Render: Presentar JButton en JTable.    
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoMarca;            //Editor: JButton en celda 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoAtri2;            //Editor: JButton en celda 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoAtri3;            //Editor: JButton en celda     
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoLine;  
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoMate;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCla;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefGe;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefDet;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoClase1;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefDet1;  
    
    

    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoMarca;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoMate;           //Editor: JTextField de consulta en celda
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoLine;           //Editor: JTextField de consulta en celda.
////    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoClas;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefGen;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefDet;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoClase1;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefDet1;           //Editor: JTextField de consulta en celda.  
    
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoAtri2;           //Editor: JTextField de consulta en celda   
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoAtri3;           //Editor: JTextField de consulta en celda       
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuPri;    
    private ZafTblModLis objTblModLis;                           //Detectar cambios de valores en las celdas.    
    private ZafTblCelRenCbo objTblCelRenCmbBoxAtr;    
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxAtr;
    private ZafTblCelRenCbo objTblCelRenCmbBox;    
    private ZafTblCelEdiCbo objTblCelEdiCmbBox; 
    private ZafTblCelRenCbo objTblCelRenCmbBoxPadre;    
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxPadre;    
    private ZafTblCelRenCbo objTblCelRenCmbBoxHijo;    
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxHijo;      
    private ArrayList arlDatTipAtr,arlRegTipAtr; 
    private ArrayList<String> dataList = new ArrayList<>(); 
    private ArrayList<String> dataList1 = new ArrayList<>();  
    private ArrayList<String> dataList2 = new ArrayList<>(); 
    private ArrayList<String> dataListTipoAgru = new ArrayList<>(); 
    private ArrayList arlDatCondicion,arlRegCondicion;    
    
    private String strVersion="v1.0";
    private boolean version =false; 
    private int codMenu =-1;
    private int codAgrupa =-1;
    private String strError="";
    private boolean blnValidaOK =true; 
    private int codTipFilAgrupa =-1;
    private String txt_tipo="";
    private String str_atributosnOVi="'Clase','SubCompras','SubControl'";

    /**
     * Este constructor crea una instancia de la clase ZafVenConItm01_01.
     * @param padre El formulario padre.
     * @param objParSis El objeto ZafParSis.
     * @param strAliSQL El alias utilizado en la sentencia SQL. El alias debe incluir el punto. Ejemplo: "a1.", "x.", "t1.".
     */
    public ZafVenConItmGP03_01(java.awt.Frame padre, ZafParSis objParSis, String strAliSQL,int CodMenu)
    {
        super(padre, true);
        initComponents();
        codMenu =CodMenu;
        //Inicializar objetos. 
        this.objParSis=objParSis;
        this.strAliSQL=strAliSQL;
        intButSelDlg=INT_BUT_CAN;
        
        if (!configurarFrm())
            exitForm(null);        
        
////        if(!isVersionActualizada()){
////////            mostrarMsgInf("Su version de la ventana de consulta no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            version =true;
////        dispose();
////        }
////        else
////        {
////            if (!configurarFrm())
////            exitForm(null);
////        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrCri1 = new javax.swing.ButtonGroup();
        bgrCri2 = new javax.swing.ButtonGroup();
        bgrAtri = new javax.swing.ButtonGroup();
        panFil = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panFilCodAlt = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        optY1 = new javax.swing.JRadioButton();
        optO1 = new javax.swing.JRadioButton();
        cboCri1 = new javax.swing.JComboBox();
        txtCri1 = new javax.swing.JTextField();
        cboCri2 = new javax.swing.JComboBox();
        txtCri2 = new javax.swing.JTextField();
        cboCri3 = new javax.swing.JComboBox();
        txtCri3 = new javax.swing.JTextField();
        cboCri4 = new javax.swing.JComboBox();
        txtCri4 = new javax.swing.JTextField();
        cboCri5 = new javax.swing.JComboBox();
        txtCri5 = new javax.swing.JTextField();
        cboCri6 = new javax.swing.JComboBox();
        txtCri6 = new javax.swing.JTextField();
        cboCri7 = new javax.swing.JComboBox();
        txtCri7 = new javax.swing.JTextField();
        cboCri8 = new javax.swing.JComboBox();
        txtCri8 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cboCri9 = new javax.swing.JComboBox();
        cboCri10 = new javax.swing.JComboBox();
        txtCri9 = new javax.swing.JTextField();
        txtCri10 = new javax.swing.JTextField();
        optY2 = new javax.swing.JRadioButton();
        optO2 = new javax.swing.JRadioButton();
        cboCri11 = new javax.swing.JComboBox();
        txtCri11 = new javax.swing.JTextField();
        cboCri12 = new javax.swing.JComboBox();
        txtCri12 = new javax.swing.JTextField();
        cboCri13 = new javax.swing.JComboBox();
        txtCri13 = new javax.swing.JTextField();
        cboCri14 = new javax.swing.JComboBox();
        txtCri14 = new javax.swing.JTextField();
        cboCri15 = new javax.swing.JComboBox();
        txtCri15 = new javax.swing.JTextField();
        cboCri16 = new javax.swing.JComboBox();
        txtCri16 = new javax.swing.JTextField();
        txtCodUniMed = new javax.swing.JTextField();
        txtDesCorUniMed = new javax.swing.JTextField();
        txtDesLarUniMed = new javax.swing.JTextField();
        butUniMed = new javax.swing.JButton();
        panTipItm = new javax.swing.JPanel();
        chkTipItmBie = new javax.swing.JCheckBox();
        chkTipItmSer = new javax.swing.JCheckBox();
        panFilCodAlt2 = new javax.swing.JPanel();
        lblCodAlt2Des = new javax.swing.JLabel();
        txtCodAlt2Des = new javax.swing.JTextField();
        lblCodAlt2Has = new javax.swing.JLabel();
        txtCodAlt2Has = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        panTipAtr = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblAtri = new javax.swing.JTable();
        butAnadir = new javax.swing.JButton();
        butelim = new javax.swing.JButton();
        chkSeleAtri = new javax.swing.JCheckBox();
        lblUniMed = new javax.swing.JLabel();
        cmbTipoAgru = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtro \"Item\"");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        panFil.setLayout(null);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item:");
        lblItm.setPreferredSize(new java.awt.Dimension(60, 14));
        panFil.add(lblItm);
        lblItm.setBounds(10, 0, 60, 20);

        txtCodItm.setEnabled(false);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(70, 0, 60, 20);

        txtCodAlt.setEnabled(false);
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(130, 0, 100, 20);

        txtCodAlt2.setEnabled(false);
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(230, 0, 40, 20);

        txtNomItm.setEnabled(false);
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(270, 0, 250, 20);

        butItm.setText("...");
        butItm.setEnabled(false);
        butItm.setPreferredSize(new java.awt.Dimension(20, 20));
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(520, 0, 20, 20);

        panFilCodAlt.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno 1:"));
        panFilCodAlt.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panFilCodAlt.add(lblCodAltDes);
        lblCodAltDes.setBounds(10, 20, 116, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panFilCodAlt.add(txtCodAltDes);
        txtCodAltDes.setBounds(126, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panFilCodAlt.add(lblCodAltHas);
        lblCodAltHas.setBounds(230, 20, 60, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
        });
        panFilCodAlt.add(txtCodAltHas);
        txtCodAltHas.setBounds(290, 20, 100, 20);

        lblCodAltItmTer.setText("Termina con:");
        panFilCodAlt.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(10, 40, 116, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
        });
        panFilCodAlt.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(126, 40, 100, 20);

        panFil.add(panFilCodAlt);
        panFilCodAlt.setBounds(4, 70, 410, 70);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno 1:"));
        jPanel1.setLayout(null);

        bgrCri1.add(optY1);
        optY1.setText("Y");
        optY1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(optY1);
        optY1.setBounds(130, 20, 60, 20);

        bgrCri1.add(optO1);
        optO1.setText("O");
        optO1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(optO1);
        optO1.setBounds(190, 20, 60, 20);
        jPanel1.add(cboCri1);
        cboCri1.setBounds(10, 40, 140, 20);

        txtCri1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri1FocusGained(evt);
            }
        });
        jPanel1.add(txtCri1);
        txtCri1.setBounds(150, 40, 100, 20);
        jPanel1.add(cboCri2);
        cboCri2.setBounds(10, 60, 140, 20);

        txtCri2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri2FocusGained(evt);
            }
        });
        jPanel1.add(txtCri2);
        txtCri2.setBounds(150, 60, 100, 20);
        jPanel1.add(cboCri3);
        cboCri3.setBounds(10, 80, 140, 20);

        txtCri3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri3FocusGained(evt);
            }
        });
        jPanel1.add(txtCri3);
        txtCri3.setBounds(150, 80, 100, 20);
        jPanel1.add(cboCri4);
        cboCri4.setBounds(10, 100, 140, 20);

        txtCri4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri4FocusGained(evt);
            }
        });
        jPanel1.add(txtCri4);
        txtCri4.setBounds(150, 100, 100, 20);
        jPanel1.add(cboCri5);
        cboCri5.setBounds(10, 120, 140, 20);

        txtCri5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri5FocusGained(evt);
            }
        });
        jPanel1.add(txtCri5);
        txtCri5.setBounds(150, 120, 100, 20);
        jPanel1.add(cboCri6);
        cboCri6.setBounds(10, 140, 140, 20);

        txtCri6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri6FocusGained(evt);
            }
        });
        jPanel1.add(txtCri6);
        txtCri6.setBounds(150, 140, 100, 20);
        jPanel1.add(cboCri7);
        cboCri7.setBounds(10, 160, 140, 20);

        txtCri7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri7FocusGained(evt);
            }
        });
        jPanel1.add(txtCri7);
        txtCri7.setBounds(150, 160, 100, 20);
        jPanel1.add(cboCri8);
        cboCri8.setBounds(10, 180, 140, 20);

        txtCri8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri8FocusGained(evt);
            }
        });
        jPanel1.add(txtCri8);
        txtCri8.setBounds(150, 180, 100, 20);

        panFil.add(jPanel1);
        jPanel1.setBounds(4, 189, 260, 210);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del item:"));
        jPanel2.setLayout(null);
        jPanel2.add(cboCri9);
        cboCri9.setBounds(10, 40, 140, 20);
        jPanel2.add(cboCri10);
        cboCri10.setBounds(10, 60, 140, 20);

        txtCri9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri9FocusGained(evt);
            }
        });
        jPanel2.add(txtCri9);
        txtCri9.setBounds(150, 40, 120, 20);

        txtCri10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri10FocusGained(evt);
            }
        });
        jPanel2.add(txtCri10);
        txtCri10.setBounds(150, 60, 120, 20);

        bgrCri2.add(optY2);
        optY2.setText("Y");
        optY2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(optY2);
        optY2.setBounds(150, 20, 60, 20);

        bgrCri2.add(optO2);
        optO2.setText("O");
        optO2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(optO2);
        optO2.setBounds(210, 20, 60, 20);
        jPanel2.add(cboCri11);
        cboCri11.setBounds(10, 80, 140, 20);

        txtCri11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri11FocusGained(evt);
            }
        });
        jPanel2.add(txtCri11);
        txtCri11.setBounds(150, 80, 120, 20);
        jPanel2.add(cboCri12);
        cboCri12.setBounds(10, 100, 140, 20);

        txtCri12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri12FocusGained(evt);
            }
        });
        jPanel2.add(txtCri12);
        txtCri12.setBounds(150, 100, 120, 20);
        jPanel2.add(cboCri13);
        cboCri13.setBounds(10, 120, 140, 20);

        txtCri13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri13FocusGained(evt);
            }
        });
        jPanel2.add(txtCri13);
        txtCri13.setBounds(150, 120, 120, 20);
        jPanel2.add(cboCri14);
        cboCri14.setBounds(10, 140, 140, 20);

        txtCri14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri14FocusGained(evt);
            }
        });
        jPanel2.add(txtCri14);
        txtCri14.setBounds(150, 140, 120, 20);
        jPanel2.add(cboCri15);
        cboCri15.setBounds(10, 160, 140, 20);

        txtCri15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri15FocusGained(evt);
            }
        });
        jPanel2.add(txtCri15);
        txtCri15.setBounds(150, 160, 120, 20);
        jPanel2.add(cboCri16);
        cboCri16.setBounds(10, 180, 140, 20);

        txtCri16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri16FocusGained(evt);
            }
        });
        jPanel2.add(txtCri16);
        txtCri16.setBounds(150, 180, 120, 20);

        panFil.add(jPanel2);
        jPanel2.setBounds(268, 189, 280, 210);

        txtCodUniMed.setEnabled(false);
        panFil.add(txtCodUniMed);
        txtCodUniMed.setBounds(70, 20, 60, 20);

        txtDesCorUniMed.setEnabled(false);
        txtDesCorUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorUniMedFocusLost(evt);
            }
        });
        txtDesCorUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorUniMedActionPerformed(evt);
            }
        });
        panFil.add(txtDesCorUniMed);
        txtDesCorUniMed.setBounds(130, 20, 100, 20);

        txtDesLarUniMed.setEnabled(false);
        txtDesLarUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarUniMedFocusLost(evt);
            }
        });
        txtDesLarUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarUniMedActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarUniMed);
        txtDesLarUniMed.setBounds(230, 20, 290, 20);

        butUniMed.setText("...");
        butUniMed.setEnabled(false);
        butUniMed.setPreferredSize(new java.awt.Dimension(20, 20));
        butUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUniMedActionPerformed(evt);
            }
        });
        panFil.add(butUniMed);
        butUniMed.setBounds(520, 20, 20, 20);

        panTipItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de item:"));
        panTipItm.setLayout(null);

        chkTipItmBie.setSelected(true);
        chkTipItmBie.setText("Bien");
        panTipItm.add(chkTipItmBie);
        chkTipItmBie.setBounds(10, 20, 100, 23);

        chkTipItmSer.setSelected(true);
        chkTipItmSer.setText("Servicio");
        panTipItm.add(chkTipItmSer);
        chkTipItmSer.setBounds(10, 40, 100, 23);

        panFil.add(panTipItm);
        panTipItm.setBounds(420, 80, 130, 110);

        panFilCodAlt2.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno 2 (3 letras):"));
        panFilCodAlt2.setLayout(null);

        lblCodAlt2Des.setText("Desde:");
        panFilCodAlt2.add(lblCodAlt2Des);
        lblCodAlt2Des.setBounds(10, 20, 116, 20);

        txtCodAlt2Des.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2DesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2DesFocusLost(evt);
            }
        });
        panFilCodAlt2.add(txtCodAlt2Des);
        txtCodAlt2Des.setBounds(126, 20, 100, 20);

        lblCodAlt2Has.setText("Hasta:");
        panFilCodAlt2.add(lblCodAlt2Has);
        lblCodAlt2Has.setBounds(230, 20, 60, 20);

        txtCodAlt2Has.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2HasFocusGained(evt);
            }
        });
        panFilCodAlt2.add(txtCodAlt2Has);
        txtCodAlt2Has.setBounds(290, 20, 100, 20);

        panFil.add(panFilCodAlt2);
        panFilCodAlt2.setBounds(4, 140, 410, 50);
        panFil.add(jPanel3);
        jPanel3.setBounds(0, 0, 10, 10);

        panTipAtr.setBorder(javax.swing.BorderFactory.createTitledBorder("Selección de atributos"));
        panTipAtr.setEnabled(false);
        panTipAtr.setLayout(null);

        tblAtri.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAtri.setEnabled(false);
        tblAtri.setRowHeight(20);
        spnFix.setViewportView(tblAtri);

        panTipAtr.add(spnFix);
        spnFix.setBounds(12, 20, 670, 300);

        butAnadir.setText("Añadir");
        butAnadir.setToolTipText("Añade una fila mas al final de la tabla.");
        butAnadir.setPreferredSize(new java.awt.Dimension(92, 25));
        butAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAnadirActionPerformed(evt);
            }
        });
        panTipAtr.add(butAnadir);
        butAnadir.setBounds(500, 330, 92, 25);

        butelim.setText("Eliminar");
        butelim.setToolTipText("Elimina la fila seleccionda");
        butelim.setPreferredSize(new java.awt.Dimension(92, 25));
        butelim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butelimActionPerformed(evt);
            }
        });
        panTipAtr.add(butelim);
        butelim.setBounds(590, 330, 92, 25);

        panFil.add(panTipAtr);
        panTipAtr.setBounds(550, 40, 690, 360);

        chkSeleAtri.setText("Seleccion de Atributos");
        chkSeleAtri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSeleAtriActionPerformed(evt);
            }
        });
        panFil.add(chkSeleAtri);
        chkSeleAtri.setBounds(560, 10, 180, 23);

        lblUniMed.setText("UN:");
        lblUniMed.setToolTipText("Unidad de medida:");
        lblUniMed.setPreferredSize(new java.awt.Dimension(60, 14));
        panFil.add(lblUniMed);
        lblUniMed.setBounds(10, 20, 50, 20);

        cmbTipoAgru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguna" }));
        cmbTipoAgru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoAgruActionPerformed(evt);
            }
        });
        panFil.add(cmbTipoAgru);
        cmbTipoAgru.setBounds(160, 50, 170, 20);

        jLabel1.setText("Tipo de Agrupación:");
        panFil.add(jLabel1);
        jLabel1.setBounds(10, 50, 140, 14);

        getContentPane().add(panFil, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.setToolTipText("Acepta la opción seleccionada.");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butLim.setText("Limpiar");
        butLim.setToolTipText("Limpia el cuadro de dialogo.");
        butLim.setPreferredSize(new java.awt.Dimension(92, 25));
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panBot.add(butLim);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo.");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(1257, 477));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCri1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri1FocusGained
        txtCri1.selectAll();
    }//GEN-LAST:event_txtCri1FocusGained

private boolean isVersionActualizada()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    System.out.println("Version " + strVersion);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(strVersion.equals(rstLoc.getString("tx_ver"))){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener//chiqui
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
    
    private boolean cargarComboJtableOpcion(){
        objTblCelEdiCmbBox.removeAllItems();
////        objTblCelEdiCmbBox.addItem(" ");        
        objTblCelEdiCmbBox.addItem("Y");
        objTblCelEdiCmbBox.addItem("O");
        return true;
    } 

    private boolean ConsultaComboTiposAtributos()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                
                
                stmLoc = conLoc.createStatement();
                strSQL="";
     
                if (codMenu==4672)                    
                        strSQL+=" SELECT trim(tx_nom) as nombre,* FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' and st_cfgLisPre ='S'  AND tx_nom NOT IN ("+ str_atributosnOVi +") order by tx_nom asc";
                else
                {
                    if (codMenu==4659)                    
                        //strSQL+=" SELECT trim(tx_nom) as nombre,* FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' and st_cfgFor2='S'  AND tx_nom NOT IN ("+ str_atributosnOVi +") order by tx_nom asc";                
                        strSQL+=" SELECT trim(tx_nom) as nombre,* FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' and st_cfgFor2='S'  order by tx_nom asc";                
                    else
                        strSQL+=" SELECT trim(tx_nom) as nombre,* FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A'  AND tx_nom NOT IN ("+ str_atributosnOVi +") order by tx_nom asc";                
                }
                        
                
////                strSQL+=" and co_tipAgr ="+tipo +"";                
                rstLoc = stmLoc.executeQuery(strSQL);
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("nombre");
                    dataList.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList.toArray(new String[0]));
                    objTblCelEdiCmbBoxAtr.removeAllItems();
                    objTblCelEdiCmbBoxAtr.setModel(model); 
//                    objTblCelEdiCmbBoxAtr.setEnabled(false);
                    blnRes=true;
                }
                else
                {
                    objTblCelEdiCmbBoxAtr.removeAllItems();
                    objTblCelEdiCmbBoxPadre.removeAllItems();
                    objTblCelEdiCmbBoxHijo.removeAllItems();                    
                }
                    
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    } 

    private boolean ConsultaComboAtributo(String padre , String hijo, int tipo)//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                
                    
                stmLoc = conLoc.createStatement();
                strSQL="";
                if  (tipo ==1)
                    strSQL+=" SELECT distinct(trim(det.tx_cfgPadDat)) as dato";
                else
                    strSQL+=" SELECT distinct(trim(det.tx_cfgHijDat)) as dato ";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as det ";
                strSQL+="   inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv) ";
                strSQL+="   inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)";
                if  (tipo ==1)
                    strSQL+=" WHERE  tip.tx_nom ='"+ padre +"'";                
                else
                    strSQL+=" WHERE  det.tx_cfgPadDat ='"+ hijo +"'"; 
                 strSQL+="   AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)";     
                
////                strSQL+=" ORDER BY det.ne_ord asc ";                
                rstLoc = stmLoc.executeQuery(strSQL);
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("dato");
                    if  (tipo ==1)
                        dataList1.add(data);
                    else
                        dataList2.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {

                    if  (tipo ==1)
                    {
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList1.toArray(new String[0]));
                        objTblCelEdiCmbBoxPadre.removeAllItems();
                        objTblCelEdiCmbBoxPadre.setModel(model); 
    //                    objTblCelEdiCmbBoxPadre.setEnabled(false);
                        dataList1.clear();                    
                        blnRes=true;   
////                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_ATR2, tblAtri);                            
                        
                    }
                    else
                    {

                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList2.toArray(new String[0]));
                        objTblCelEdiCmbBoxHijo.removeAllItems();
                        objTblCelEdiCmbBoxHijo.setModel(model); 
                        dataList2.clear();                        
    //                    objTblCelEdiCmbBoxHijo.setEnabled(false);
                        blnRes=true;      
////                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_ATR3, tblAtri);                                                 
                        
                    }

                }
                else
                {
                    
                    if  (tipo ==1)
                    {
                        dataList1.clear();
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList1.toArray(new String[0]));
////                        objTblCelEdiCmbBoxPadre.removeAllItems();
                        objTblCelEdiCmbBoxPadre.setModel(model);  
                        objTblCelEdiCmbBoxPadre.setSelectedItem(" ");
                        
                        dataList2.clear();
                        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(dataList2.toArray(new String[0]));
////                        objTblCelEdiCmbBoxHijo.removeAllItems();
                        objTblCelEdiCmbBoxHijo.setModel(model1); 
                        objTblCelEdiCmbBoxHijo.setSelectedItem(" ");
////                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ATR2, tblAtri);
////                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ATR3, tblAtri);                    
                    }
                    else
                        dataList2.clear();
                        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(dataList2.toArray(new String[0]));
////                        objTblCelEdiCmbBoxHijo.removeAllItems();
                        objTblCelEdiCmbBoxHijo.setModel(model1); 
                        objTblCelEdiCmbBoxHijo.setSelectedItem("");
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ATR3, tblAtri);                            
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }     

    
//////////    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter//chiqui
//////////    {
//////////        public void mouseMoved(java.awt.event.MouseEvent evt)
//////////        {
//////////            int intCol=tblAtri.columnAtPoint(evt.getPoint());
//////////            String strMsg="";
//////////            switch (intCol)
//////////            {
//////////                case INT_TBL_DAT_NOM_MARC: strMsg="Descripción de la Marca"; break;
//////////                case INT_TBL_DAT_BUT_COM_MARC: strMsg="Busqueda de Marca"; break;
//////////                case INT_TBL_DAT_NOM_LINEA: strMsg="Descripción de Linea"; break;
//////////                case INT_TBL_DAT_BUT_COM_LINEA: strMsg="Busqueda de Linea"; break;
//////////                case INT_TBL_DAT_NOM_MAT: strMsg="Descripción del Material"; break;
//////////                case INT_TBL_DAT_BUT_COM_MAT: strMsg="Busqueda de Material"; break;
//////////                case INT_TBL_DAT_NOM_CLASE1: strMsg="Descripción de Clase&SubClase"; break;
//////////                case INT_TBL_DAT_BUT_CLASE1: strMsg="Busqueda de Clase&SubClase"; break;                
//////////
//////////                default: strMsg=""; break;
//////////                
//////////
//////////            }
//////////            tblAtri.getTableHeader().setToolTipText(strMsg);
//////////        }
//////////    }
    
    private boolean ConfigurarLinea()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_LIN_COM' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConLine=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean ConfigurarClase1()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CLA_COM' ";             
             
            System.out.println("--- "+strSQL);
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConClase1=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean ConfigurarMarca()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_MARCA' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConMarc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean ConfigurarRefGeneral()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_GENERAL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConRefGen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean ConfigurarRefDetallada()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_DETALL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConRefDet=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean ConfigurarAtri2(int tipo, String padre,String hijo)//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("dato");
            arlCam.add("codigo");
            arlCam.add("tipoAtri");
////            arlCam.add("tx_desLar");
////            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Dato");
            arlAli.add("T");
            arlAli.add("TipoAtributos");
            
////            arlAli.add("Desc.Larga");
////            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("200");
            arlAncCol.add("20");
            arlAncCol.add("100");
////            arlAncCol.add("170");
////            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
////             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
////             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
////             strSQL+="WHERE  Extender_Form_ID = 'MSW_MATERIAL' ";   
             
////////                if  (tipo ==1)
////////                    strSQL+=" SELECT distinct(trim(det.tx_cfgPadDat)) as dato, det.co_cfgPad as codigo \n";
////////                else
////////                    
////////                    strSQL+=" SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo \n";
////////                strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
////////                strSQL+="   inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv) \n";
////////                strSQL+="   inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip) \n";
////////                if  (tipo ==1)
////////                    strSQL+=" WHERE  tip.tx_nom ='"+ padre +"'\n";                
////////                else
////////                {
////////                    strSQL+=" WHERE  det.tx_cfgPadDat ='"+ hijo +"'\n"; 
////////                    strSQL+="   AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)";  
////////                }

                if  (tipo ==1)
                {
                    strSQL+=" SELECT distinct(trim(det.tx_cfgPadDat)) as dato, det.co_cfgPad as codigo,det.tx_tipo as tipoAtri \n";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
                    strSQL+="   inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv) \n";
                    strSQL+="   inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip) \n";
                    strSQL+=" WHERE  tip.tx_nom ='"+ padre +"'\n";                
                }
                    
                else
                {
                    
                    if(hijo.equals("VALVULAS"))
                    {
                        strSQL+=" SELECT distinct(trim(ab.tx_cfgHijDat)) as dato , ab.co_cfghij as codigo,ab.tx_tipo as tipoAtri \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as ab \n";
                        strSQL+=" inner join ( \n";                
                        strSQL+="       SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo  \n";
                        strSQL+="       FROM Dynamics2021.dbo.tbm_cfgAtrInv as det  \n";
                        strSQL+="           inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)  \n";
                        strSQL+="           inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)   \n";   
                        strSQL+=" WHERE  det.tx_cfgPadDat ='"+ hijo +"'\n"; 
                        strSQL+="   AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)) as pa on (ab.tx_cfgPadDat = pa.dato)"; 
                        strSQL+=" WHERE   ab.co_cfgHij != '' \n";                         
                    }
                    else
                    {
                        strSQL+=" SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo,det.tx_tipo as tipoAtri   \n";
                        strSQL+=" FROM  Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
                        strSQL+="       inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)  \n";                
                        strSQL+="       inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)  \n";
                        strSQL+="       WHERE  det.tx_cfgPadDat = '"+ hijo +"' \n";
                        strSQL+="           AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)  \n";
                        strSQL+="           AND  tip.tx_nom ='"+ padre +"'  \n";                        
                    }
                    
                                       
                }
                 if (tipo==1)
                     objVenConAtri2=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
                 else
                     objVenConAtri3=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
                 
             
             
             
             
             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
            txtNomItm.setText(strNomItm);
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            if (txtCodAltHas.getText().length()==0)
            {
                txtCodAltHas.setText(txtCodAltDes.getText());
            }
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        
////        if(!isVersionActualizada()){
////            mostrarMsgInf("Su version de la ventana de consulta no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////        dispose();
////        }
////        else
////        {
////            if (isCamVal())
////            {
////                strConSQLItm=getConSQLTod();
////
////                if (chkSeleAtri.isSelected())
////                {
////                    if (!strConSQLItm.equals(""))
////                    {
////                        strConSQLAtri =getConAtrib(0);
////                    }
////                    else
////                    {
////                       strConSQLAtri =getConAtrib(1);
////                    }
////
////                    if (!strConSQLItm.equals(""))
////                        strConSQLItm +=  strConSQLAtri;
////                    else
////                        strConSQLItm = strConSQLAtri;
////                }
////
////                //if (strConSQLItm.equals(""))
////
////                intButSelDlg=INT_BUT_ACE;
////                dispose();
////            }            

            if (isCamVal())
            {
                
                //if (chkSeleAtri.isSelected() && (codTipFilAgrupa!=-1))
                if (chkSeleAtri.isSelected())
                {
                    strConSQLItm="";
                    if (!strConSQLItm.equals(""))
                    {
                        strConSQLAtri =getConAtrib(0);
                    }
                    else
                    {
                       strConSQLAtri =getConAtrib(1);
                    }
                    
                    if (!blnValidaOK)
                    {
                        mostrarMsgInf(strError);
                        strConSQLItm="";
                        
                    }
                        
                    else
                    {
                        if (!strConSQLItm.equals(""))
                            strConSQLItm +=  strConSQLAtri;
                        else
                            strConSQLItm = strConSQLAtri;                        
                    }    
                        


                }
                else
                    strConSQLItm=getConSQLTod();


                //if (strConSQLItm.equals(""))

                intButSelDlg=INT_BUT_ACE;
                dispose();
            }
////        }
                
                

    }//GEN-LAST:event_butAceActionPerformed
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private int obtener_tipoExt( String tipo)
    {
        boolean blnRes=true;
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        int Idtipo =-1;
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT co_tip";
                strSQL+=" FROM Dynamics2021.dbo.tbm_tipExt  ";
                strSQL+=" WHERE tx_nom='"+ tipo +"'";
                System.out.println("obtener_tipo: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    Idtipo=rstLoc1.getInt("co_tip");
                }
                else{
                   Idtipo =-1;
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
        return Idtipo;
    }
    
    private int obtener_tipoAtrInt( String tipo,int idTipo, int estado)
    {
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        int Idtipo =-1;
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT atr.co_atrInv,ATR.tx_deslar,EXT.tx_nom, EXT.tx_tip,EXT.co_tip \n";
                strSQL+=" FROM  Dynamics2021..tbm_tipExt as ext  \n";
                strSQL+="    left join Dynamics2021..tbm_atrInv as atr on (ext.tx_tip = atr.tx_desCor) \n";  
                if (estado==1)
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.tx_nom='"+ tipo +"' \n";
                else
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.co_atrInv= "+ idTipo +" \n";                    
                System.out.println("obtener_tipo: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    if (estado==1)
                        Idtipo=rstLoc1.getInt("co_atrInv");
                    if (estado==2)
                        Idtipo=rstLoc1.getInt("co_tip");
                }
                else{
                   Idtipo =-1;
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
             Idtipo =-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
             Idtipo =-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return Idtipo;
    }    
    
    private String obtener_tipoAtrCad( String tipo,int idTipo, int estado)
    {
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        String Idtipo ="";
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT atr.co_atrInv,ATR.tx_deslar,EXT.tx_nom, EXT.tx_tip,EXT.co_tip";
                strSQL+=" FROM  Dynamics2021..tbm_tipExt as ext  ";
                strSQL+="    left join Dynamics2021..tbm_atrInv as atr on (ext.tx_tip = atr.tx_desCor)";  
                if (estado==1)
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.tx_nom='"+ tipo +"'";
                else
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.co_atrInv= "+ idTipo +"";                    
                System.out.println("obtener_tipo: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    if (estado==2)
                        Idtipo=rstLoc1.getString("tx_nom");
                }
                else{
                   Idtipo ="";
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
             Idtipo ="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
             Idtipo ="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return Idtipo;
    }      
    
private String obtener_tipoAtrAdi( String tipo,int idTipo, int estado)
    {
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        String txAdi ="";
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT atr.co_atrInv,ATR.tx_deslar,EXT.tx_nom, EXT.tx_tip,EXT.co_tip,ext.tx_adiCo ";
                strSQL+=" FROM  Dynamics2021..tbm_tipExt as ext  ";
                strSQL+="    left join Dynamics2021..tbm_atrInv as atr on (ext.tx_tip = atr.tx_desCor)";  
                if (estado==1)
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.tx_nom='"+ tipo +"'";
                else
                    strSQL+=" WHERE atr.st_reg ='A' and ext.st_reg ='A' AND ext.co_atrInv= "+ idTipo +"";                    
                System.out.println("obtener_tipo: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    if (estado==2)
                        txAdi=rstLoc1.getString("tx_adiCo");
                }
                else{
                   txAdi ="";
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
             txAdi ="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
             txAdi ="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return txAdi;
    }     
    
    public String obtener_tipoAributo()
    {
        return txt_tipo;
    }
    
    
    private String getConAtrib(int tipoExis)//chiqui
    {
        int intNumFil=objTblMod.getRowCount();
        int valida=-1,idTipo=-1,idTipo2=-1;
        int contador =0;
        boolean validaExiste =false;
        int codAgrupInt=-1;
        String txt_tipoInt="";
        String opcion="",tipo="",condicion="",condicionGlo="",cod1="",cod2="",txtTipo="",txtTipo2="",txtAdi1="",txtAdi2="";
            arlDatCondicion= new ArrayList();
            condicion="";
                 if(tipoExis ==1)
                     validaExiste =true;
                     
                 else
                     validaExiste =false;
            for (int i=0; i<intNumFil;i++)
            {
                condicion="";
                contador++;
                System.out.println(""+objTblMod.getValueAt(i, INT_TBL_DAT_OPCION).toString());
                opcion =objTblMod.getValueAt(i, INT_TBL_DAT_OPCION).toString();
                System.out.println("-----  "+opcion);
                
                if (opcion =="Y")
                {
                    //if ((strConSQLItm.length() ==0) && (i==0))
                    if (i==0)
                        condicion=" AND ((";
                    else
                        condicion+=" AND (";
                }
                else
                {
                    //if ((strConSQLItm.length() ==0) && (i==0))
                    if (i==0)    
                        condicion=" AND (";
                    else
                        condicion+=" OR (";                    
                }
                    
////                if ((!objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3).toString().equals("")) && (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3)!=null))
                if (!objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3).toString().equals(""))
                    valida=3;
                else
                {
////                     if (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR2)!=null)
                if (!objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR2).toString().equals(""))                    
                        valida=2;
                    else
                    {
////                        if (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR1)!=null) 
                        if (!objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR1).toString().equals(""))                              
                            valida=1;
                    }
                }
                
                if (valida ==1)
                {
                    txtTipo =obtener_tipoAtrCad("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    txtAdi1 =obtener_tipoAtrAdi("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    if (txtAdi1 == null || txtAdi1.equals("")) 
                        txtTipo=txtTipo;
                    else
                        txtTipo=txtTipo +txtAdi1;
                    cod1 = "cod"+txtTipo;
                    idTipo=obtener_tipoAtrInt("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    condicion +="" + strAliSQL + cod1 +"="+ idTipo +" )";
                }
                   
                if (valida ==2)
                {
                    txtTipo =obtener_tipoAtrCad("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    txtAdi1=obtener_tipoAtrAdi("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    if (txtAdi1 == null || txtAdi1.equals("")) 
                        txtTipo=txtTipo;
                    else
                        txtTipo=txtTipo +txtAdi1;
                    cod1 = "cod"+txtTipo;
                    idTipo=obtener_tipoAtrInt("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    condicion +="" + strAliSQL + cod1 +"="+ idTipo +" and  " + strAliSQL + txtTipo +"='"+ objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR2).toString() +"' )";
                }
                if (valida ==3)
                {
                    txtTipo =obtener_tipoAtrCad("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);   
                    txtAdi1=obtener_tipoAtrAdi("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    if (txtAdi1 == null || txtAdi1.equals(""))
                        txtTipo=txtTipo;
                    else
                        txtTipo=txtTipo +txtAdi1;
                    cod1 = "cod"+txtTipo;                    
                    idTipo =obtener_tipoAtrInt("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    
                    txtTipo2 =obtener_tipoAtrCad("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR3).toString()) ,2);
                    txtAdi2=obtener_tipoAtrAdi("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString()) ,2);
                    if (txtAdi2 == null || txtAdi1.equals(""))
                        txtTipo2=txtTipo2;
                    else
                        txtTipo2=txtTipo2 +txtAdi2;
                    cod2 = "cod"+ txtTipo2;
                    idTipo2=obtener_tipoAtrInt("",Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR3).toString()) ,2);
                    
                    condicion +=  strAliSQL + cod1 +"="+ idTipo +" and ";
                    condicion +=  strAliSQL + txtTipo +"='"+ objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR2).toString() +"' and ";
                    condicion +=  strAliSQL + cod2 +"="+ idTipo2 +" and " + strAliSQL +""+txtTipo2+"='"+  objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3).toString() +"' )";                  
////                    condicion +=  strAliSQL +txtTipo2+"='"+  objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3).toString() +"'";
////                    condicion +="" + strAliSQL + "Field_ID =" + idTipo +" and " + strAliSQL + "STRGA255= '"+ objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR3).toString() +"'";
                }
                condicionGlo+=condicion;
                codAgrupInt=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ATR1).toString());
                txt_tipoInt = validaTipoAtributo(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR2).toString(),codAgrupInt);
                if (i==0)
                {
                    txt_tipo =txt_tipoInt;
                    codAgrupa =codAgrupInt;
                }
                    
                else
                {
                    if (codAgrupa!=codAgrupInt)
                    {
                        strError="No se puede tener mas de 2 atributos distintos en un filtro";
                        blnValidaOK=false;
                        codAgrupa=-1;
                        limpiar();
                        break;
                        
                    }   
                    if (!txt_tipo.equals(txt_tipoInt))
                    {
                        strError="No se puede tener mas de 2 atributos(TIPOS) distintos en un filtro";
                        blnValidaOK=false;
                        codAgrupa=-1;
                        txt_tipo="";
                        limpiar();
                        break;
                        
                    }                     
                        
                }
                blnValidaOK=true;
           }
            
////           if (!strConSQLItm.equals(""))//existe
////           {
//////////               if (contador ==1)
//////////               {
//////////                   if (opcion =="Y")
//////////                    condicionGlo =  "AND " + condicionGlo ;
//////////                   else
//////////                    condicionGlo =  "OR " + condicionGlo ;
//////////               }
//////////                   
//////////               else
//////////               {
//////////                   if (opcion =="Y")
//////////                       condicionGlo = "AND (" +condicionGlo+ ")";
//////////                   else
//////////                       condicionGlo = "OR (" +condicionGlo+ ")";
//////////               }
                   
////           }
////           else// no existe
////           {
////               if (contador ==1)
////                    condicionGlo +=  "AND " + condicionGlo ;
////               else
////                   condicionGlo = "AND (" +condicionGlo+ ")";
////           }

                condicionGlo=  condicionGlo + ")";
            
            return condicionGlo;
    }
    private String validaTipoAtributo(String tipo,int pad)
    { 
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        String strTipo="";
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT isnull(atr.tx_tipo,'') as tipo";
                strSQL+=" FROM  Dynamics2021..tbm_cfgAtrInv as atr  ";
                strSQL+=" WHERE atr.tx_cfgPadDat ='"+ tipo +"' and co_cfgPad = "+ pad +" ";
                System.out.println("validaTipoAtributo: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                        strTipo=rstLoc1.getString("tipo");
                }
                else{
                   tipo ="";
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
            strTipo="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
             strTipo="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipo;
        
    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

    private void txtCri2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri2FocusGained
        txtCri2.selectAll();
    }//GEN-LAST:event_txtCri2FocusGained

    private void txtCri3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri3FocusGained
        txtCri3.selectAll();
    }//GEN-LAST:event_txtCri3FocusGained

    private void txtCri4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri4FocusGained
        txtCri4.selectAll();
    }//GEN-LAST:event_txtCri4FocusGained

    private void txtCri5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri5FocusGained
        txtCri5.selectAll();
    }//GEN-LAST:event_txtCri5FocusGained

    private void txtCri6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri6FocusGained
        txtCri6.selectAll();
    }//GEN-LAST:event_txtCri6FocusGained

    private void txtCri7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri7FocusGained
        txtCri7.selectAll();
    }//GEN-LAST:event_txtCri7FocusGained

    private void txtCri8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri8FocusGained
        txtCri8.selectAll();
    }//GEN-LAST:event_txtCri8FocusGained

    private void txtCri9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri9FocusGained
        txtCri9.selectAll();
    }//GEN-LAST:event_txtCri9FocusGained

    private void txtCri10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri10FocusGained
        txtCri10.selectAll();
    }//GEN-LAST:event_txtCri10FocusGained

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiar();
    }//GEN-LAST:event_butLimActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        intButSelDlg=INT_BUT_CAN;
        dispose();
    }//GEN-LAST:event_exitForm

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        {
            txtCodAlt2.setText(strCodAlt2);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCri11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri11FocusGained
        txtCri11.selectAll();
    }//GEN-LAST:event_txtCri11FocusGained

    private void txtCri12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri12FocusGained
        txtCri12.selectAll();
    }//GEN-LAST:event_txtCri12FocusGained

    private void txtCri13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri13FocusGained
        txtCri13.selectAll();
    }//GEN-LAST:event_txtCri13FocusGained

    private void txtCri14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri14FocusGained
        txtCri14.selectAll();
    }//GEN-LAST:event_txtCri14FocusGained

    private void txtCri15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri15FocusGained
        txtCri15.selectAll();
    }//GEN-LAST:event_txtCri15FocusGained

    private void txtCri16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri16FocusGained
        txtCri16.selectAll();
    }//GEN-LAST:event_txtCri16FocusGained

    private void txtDesCorUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorUniMedFocusGained
        strDesCorUniMed=txtDesCorUniMed.getText();
        txtDesCorUniMed.selectAll();
    }//GEN-LAST:event_txtDesCorUniMedFocusGained

    private void txtDesCorUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorUniMedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorUniMed.getText().equalsIgnoreCase(strDesCorUniMed))
        {
            if (txtDesCorUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtDesCorUniMed.setText("");
                txtDesLarUniMed.setText("");
            }
            else
            {
                mostrarVenConUniMed(1);
            }
        }
        else
        {
            txtDesCorUniMed.setText(strDesCorUniMed);
        }
    }//GEN-LAST:event_txtDesCorUniMedFocusLost

    private void txtDesCorUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorUniMedActionPerformed
        txtDesCorUniMed.transferFocus();
    }//GEN-LAST:event_txtDesCorUniMedActionPerformed

    private void txtDesLarUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUniMedFocusGained
        strDesLarUniMed=txtDesLarUniMed.getText();
        txtDesLarUniMed.selectAll();
    }//GEN-LAST:event_txtDesLarUniMedFocusGained

    private void txtDesLarUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUniMedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarUniMed.getText().equalsIgnoreCase(strDesLarUniMed))
        {
            if (txtDesLarUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtDesCorUniMed.setText("");
                txtDesLarUniMed.setText("");
            }
            else
            {
                mostrarVenConUniMed(2);
            }
        }
        else
        {
            txtDesLarUniMed.setText(strDesLarUniMed);
        }
    }//GEN-LAST:event_txtDesLarUniMedFocusLost

    private void txtDesLarUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarUniMedActionPerformed
        txtDesLarUniMed.transferFocus();
    }//GEN-LAST:event_txtDesLarUniMedActionPerformed

    private void butUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUniMedActionPerformed
        mostrarVenConUniMed(0);
    }//GEN-LAST:event_butUniMedActionPerformed

    private void txtCodAlt2DesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2DesFocusGained
        txtCodAlt2Des.selectAll();
    }//GEN-LAST:event_txtCodAlt2DesFocusGained

    private void txtCodAlt2DesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2DesFocusLost
        if (txtCodAlt2Des.getText().length()>0)
        {
            if (txtCodAlt2Has.getText().length()==0)
            {
                txtCodAlt2Has.setText(txtCodAlt2Des.getText());
            }
        }
    }//GEN-LAST:event_txtCodAlt2DesFocusLost

    private void txtCodAlt2HasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2HasFocusGained
        txtCodAlt2Has.selectAll();
    }//GEN-LAST:event_txtCodAlt2HasFocusGained

    private void butAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAnadirActionPerformed
       int fila =0;
       objTblMod.insertRow();
       
    }//GEN-LAST:event_butAnadirActionPerformed

    private void butelimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butelimActionPerformed
        int fila =0;
        fila = tblAtri.getSelectedRow();
        objTblMod.removeRow(fila);
    }//GEN-LAST:event_butelimActionPerformed

    private void chkSeleAtriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSeleAtriActionPerformed
        // TODO add your handling code here:
            if (chkSeleAtri.isSelected())
            {
            panTipAtr.setEnabled(true);
            tblAtri.setEnabled(true);
////                cmbTipoAgru.setEnabled (true);
            }    
                
            else
            {
                tblAtri.setEnabled(false);
                objTblMod.removeAllRows();
                panTipAtr.setEnabled(false);
////                cmbTipoAgru.setEnabled (false);
            }
            
    
                
        
    }//GEN-LAST:event_chkSeleAtriActionPerformed
private int obtenerIdTipoAgrup(String tipo)
{
    java.sql.Statement stmLoc1 = null;
    java.sql.ResultSet rstLoc1 = null;
    java.sql.Connection conExt1;        
    int idTipo=-1;
    try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

        if (conExt1!=null){
            stmLoc1=conExt1.createStatement();
          strSQL="";
            strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar ";
            strSQL+=" FROM Dynamics2021..tbm_tipAgr ";
            strSQL+=" WHERE  st_reg ='A' and tx_nom ="+ objUti.codificar(tipo)  +"";                

            System.out.println("obtenerIdTipoAtri: " + strSQL);
            rstLoc1=stmLoc1.executeQuery(strSQL);
            if (rstLoc1.next())
                idTipo=rstLoc1.getInt("co_tipAgr");
        }
        rstLoc1.close();
        stmLoc1.close();
        conExt1.close();
        rstLoc1=null;
        stmLoc1=null;
        conExt1=null;
    }
    catch (java.sql.SQLException e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return idTipo;
}
    
    private void cmbTipoAgruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoAgruActionPerformed
        codTipFilAgrupa = obtenerIdTipoAgrup(cmbTipoAgru.getSelectedItem().toString());
////        if (codTipFilAgrupa!=-1)
////        {
////            panTipAtr.setEnabled(true);
////            tblAtri.setEnabled(true);
////////            chkSeleAtri.setSelected(true);
////        }
////        else
////        {
////            panTipAtr.setEnabled(false);
////            tblAtri.setEnabled(false);
////////            chkSeleAtri.setSelected(false);
////
////        }

    }//GEN-LAST:event_cmbTipoAgruActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrAtri;
    private javax.swing.ButtonGroup bgrCri1;
    private javax.swing.ButtonGroup bgrCri2;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butAnadir;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butUniMed;
    private javax.swing.JButton butelim;
    private javax.swing.JComboBox cboCri1;
    private javax.swing.JComboBox cboCri10;
    private javax.swing.JComboBox cboCri11;
    private javax.swing.JComboBox cboCri12;
    private javax.swing.JComboBox cboCri13;
    private javax.swing.JComboBox cboCri14;
    private javax.swing.JComboBox cboCri15;
    private javax.swing.JComboBox cboCri16;
    private javax.swing.JComboBox cboCri2;
    private javax.swing.JComboBox cboCri3;
    private javax.swing.JComboBox cboCri4;
    private javax.swing.JComboBox cboCri5;
    private javax.swing.JComboBox cboCri6;
    private javax.swing.JComboBox cboCri7;
    private javax.swing.JComboBox cboCri8;
    private javax.swing.JComboBox cboCri9;
    private javax.swing.JCheckBox chkSeleAtri;
    private javax.swing.JCheckBox chkTipItmBie;
    private javax.swing.JCheckBox chkTipItmSer;
    private javax.swing.JComboBox<String> cmbTipoAgru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCodAlt2Des;
    private javax.swing.JLabel lblCodAlt2Has;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblUniMed;
    private javax.swing.JRadioButton optO1;
    private javax.swing.JRadioButton optO2;
    private javax.swing.JRadioButton optY1;
    private javax.swing.JRadioButton optY2;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCodAlt;
    private javax.swing.JPanel panFilCodAlt2;
    private javax.swing.JPanel panTipAtr;
    private javax.swing.JPanel panTipItm;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JTable tblAtri;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAlt2Des;
    private javax.swing.JTextField txtCodAlt2Has;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodUniMed;
    private javax.swing.JTextField txtCri1;
    private javax.swing.JTextField txtCri10;
    private javax.swing.JTextField txtCri11;
    private javax.swing.JTextField txtCri12;
    private javax.swing.JTextField txtCri13;
    private javax.swing.JTextField txtCri14;
    private javax.swing.JTextField txtCri15;
    private javax.swing.JTextField txtCri16;
    private javax.swing.JTextField txtCri2;
    private javax.swing.JTextField txtCri3;
    private javax.swing.JTextField txtCri4;
    private javax.swing.JTextField txtCri5;
    private javax.swing.JTextField txtCri6;
    private javax.swing.JTextField txtCri7;
    private javax.swing.JTextField txtCri8;
    private javax.swing.JTextField txtCri9;
    private javax.swing.JTextField txtDesCorUniMed;
    private javax.swing.JTextField txtDesLarUniMed;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle("Filtro \"Item\"" + " v0.2");
            txtCodItm.setVisible(false);
            txtCodUniMed.setVisible(false);
            panTipItm.setVisible(false); 
            /*
            Deshabilitar la consulta por "Item" en el Grupo porque habría que agregar más código para que funcione.
            Se analizó el caso y se consideró que no valía la pena utilizar tiempo en desarrollar dicha opción.
            Para que funcione habría que manejar 2 alias. Uno para el código a nivel de empresa y otro a nivel de Grupo.
            Más o menos algo así:
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                strConSQL+=" AND a6.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_itm=" + txtCodItm.getText() + " )";
            else
                strConSQL+=" AND a2.co_itm=" + txtCodItm.getText();
            */
////            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
////            {
////                txtCodItm.setEditable(false);
////                txtCodAlt.setEditable(false);
////                txtCodAlt2.setEditable(false);
////                txtNomItm.setEditable(false);
////                butItm.setEnabled(false);
////            }
            //Configurar objetos.
            llenarCboCri(cboCri1);
            llenarCboCri(cboCri2);
            llenarCboCri(cboCri3);
            llenarCboCri(cboCri4);
            llenarCboCri(cboCri5);
            llenarCboCri(cboCri6);
            llenarCboCri(cboCri7);
            llenarCboCri(cboCri8);
            llenarCboCri(cboCri9);
            llenarCboCri(cboCri10);
            llenarCboCri(cboCri11);
            llenarCboCri(cboCri12);
            llenarCboCri(cboCri13);
            llenarCboCri(cboCri14);
            llenarCboCri(cboCri15);
            llenarCboCri(cboCri16);
            cboCri1.setSelectedIndex(0);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            configurarVenConUniMed();
            cargarArregloAtributos();            
            configurarTblDat();
                                 
            ConsultaComboTiposAtributos();            
            cargarComboJtableOpcion();
            LLenarComboTiposAgrupacion();

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
//modificado
    private boolean configurarTblDat()//chiqui
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_OPCION,"Opcion");
            vecCab.add(INT_TBL_DAT_COD_ATR1,"Cod_At1");
            vecCab.add(INT_TBL_DAT_NOM_ATR1,"Atributo 1");
////            vecCab.add(INT_TBL_DAT_COD_ZAF,"Cod.Zaf.");
////            vecCab.add(INT_TBL_DAT_NOM_ITEM,"Nombre");
////            vecCab.add(INT_TBL_DAT_NOM_TAMA,"Tamaño");
            vecCab.add(INT_TBL_DAT_COD_ATR2,"Cod_At2");
            vecCab.add(INT_TBL_DAT_NOM_ATR2,"Atributo 2");            
            vecCab.add(INT_TBL_DAT_BUT_COM_ATR2,"");
            vecCab.add(INT_TBL_DAT_COD_ATR3,"Cod_At3");
            vecCab.add(INT_TBL_DAT_NOM_ATR3,"Atributo 3");
            vecCab.add(INT_TBL_DAT_BUT_COM_ATR3,"");            
////            vecCab.add(INT_TBL_DAT_COD_CLASE1,"Cod.Clase1");
////            vecCab.add(INT_TBL_DAT_NOM_CLASE1,"Clase");    
////            vecCab.add(INT_TBL_DAT_BUT_CLASE1,"");            
////            vecCab.add(INT_TBL_DAT_COD_MAT,"Cod.Material");
////            vecCab.add(INT_TBL_DAT_NOM_MAT,"Material"); 
////            vecCab.add(INT_TBL_DAT_BUT_COM_MAT,"");            
////            vecCab.add(INT_TBL_DAT_COD_REF_GE,"Cod.Ref.General");
////            vecCab.add(INT_TBL_DAT_BUT_COM_REF_GE,"");
////            vecCab.add(INT_TBL_DAT_NOM_REF_GE,"Ref. General");            
////            vecCab.add(INT_TBL_DAT_COD_REF_DET,"Cod.Ref.Detallada");
////            vecCab.add(INT_TBL_DAT_BUT_COM_REF_DET,"");
////            vecCab.add(INT_TBL_DAT_NOM_REF_DET,"Ref. Detallada");            
////            vecCab.add(INT_TBL_DAT_COD_CLASE,"Cod.Clase");
////            vecCab.add(INT_TBL_DAT_BUT_COM_CLASE,"");
////            vecCab.add(INT_TBL_DAT_NOM_CLASE,"Clase");            
            
              
            //
//            ConfigurarMarca();
            ConfigurarLinea();
////            ConfigurarClase();
            ConfigurarMaterial();
            ConfigurarRefGeneral();
            ConfigurarRefDetallada();
            ConfigurarClase1();
////            ConfigurarRefDetallada1();            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblAtri.setModel(objTblMod);

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblAtri.setModel(objTblMod);  
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
////            objTblMod.setColumnDataType(INT_TBL_DAT_NOM_TAMA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
////            tblDat.setRowSelectionAllowed(true);
////            //tblDat.setRowSelectionAllowed(true);
////            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            tblAtri.setCellSelectionEnabled(true);
            tblAtri.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblAtri,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
////            objTblPopMnu=new ZafTblPopMnu(tblDat);
////            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.

            

            
            tblAtri.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            
            
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblAtri.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_OPCION).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ATR1).setPreferredWidth(20);            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setPreferredWidth(150);
////            tcmAux.getColumn(INT_TBL_DAT_COD_ZAF).setPreferredWidth(50);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ITEM).setPreferredWidth(300);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_TAMA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ATR2).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR2).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR2).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ATR3).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR3).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR3).setPreferredWidth(100);
////            tcmAux.getColumn(INT_TBL_DAT_COD_MAT).setPreferredWidth(70);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setPreferredWidth(20);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_MAT).setPreferredWidth(70);
////            tcmAux.getColumn(INT_TBL_DAT_COD_CLASE1).setPreferredWidth(100);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setPreferredWidth(20);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_CLASE1).setPreferredWidth(100);            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR2).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR3).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setResizable(false);            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblAtri.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
////            objMouMotAda=new ZafMouMotAda();
////            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_OPCION);
            vecAux.add("" + INT_TBL_DAT_NOM_ATR1);
            vecAux.add("" + INT_TBL_DAT_BUT_COM_ATR2);
            vecAux.add("" + INT_TBL_DAT_BUT_COM_ATR3);
            vecAux.add("" + INT_TBL_DAT_NOM_ATR2);
////            vecAux.add("" + INT_TBL_DAT_NOM_MAT);           
            vecAux.add("" + INT_TBL_DAT_NOM_ATR2);    
            vecAux.add("" + INT_TBL_DAT_NOM_ATR3);             
////            vecAux.add("" + INT_TBL_DAT_NOM_CLASE1);              
////            vecAux.add("" + INT_TBL_DAT_BUT_COM_MAT);
////            vecAux.add("" + INT_TBL_DAT_BUT_COM_REF_GE);
////            vecAux.add("" + INT_TBL_DAT_BUT_COM_REF_DET);
////            vecAux.add("" + INT_TBL_DAT_BUT_CLASE1);            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;   
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ATR1, tblAtri); 
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ATR2, tblAtri);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ATR3, tblAtri);            
            
            objTblCelRenCmbBox=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_OPCION).setCellRenderer(objTblCelRenCmbBox);
            
            objTblCelEdiCmbBox=new ZafTblCelEdiCbo(tblAtri);
            tcmAux.getColumn(INT_TBL_DAT_OPCION).setCellEditor(objTblCelEdiCmbBox); 
            
            objTblCelRenCmbBoxAtr=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellRenderer(objTblCelRenCmbBoxAtr);
            
            objTblCelEdiCmbBoxAtr=new ZafTblCelEdiCbo(tblAtri);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellEditor(objTblCelEdiCmbBoxAtr);             

            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR2).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR3).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CLASE).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_GE).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_DET).setCellRenderer(objTblCelRenBut);     
////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setCellRenderer(objTblCelRenBut);             
            objTblCelRenBut=null;
            


            objTblCelEdiCmbBoxAtr = new ZafTblCelEdiCbo(tblAtri);//LA DE TIPOS
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellEditor(objTblCelEdiCmbBoxAtr);
            objTblCelEdiCmbBoxAtr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    String padre = objTblCelRenCmbBoxAtr.getSelectedItem().toString();
////                            
////                            ConsultaComboAtributo(padre,"",1);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=0,idCodigo=-1;
                    intFilSel=tblAtri.getSelectedRow();
                    
                    idCodigo=obtener_tipoAtrInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR1).toString(),0,1); 
                    if (idCodigo !=-1)
                    {
                        objTblMod.setValueAt(idCodigo, intFilSel, INT_TBL_DAT_COD_ATR1);   
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR2);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR2);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3);                        
                    }
                        
//////                   
//////                    }
                }
            });

            
//ATRIBUTO PADRE                    
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_ATR2;
            intColTbl[1]=INT_TBL_DAT_NOM_ATR2;


            objTblCelEdiButVcoAtri2=new ZafTblCelEdiButVcoGP(tblAtri, objVenConAtri2, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR2).setCellEditor(objTblCelEdiButVcoAtri2);
            objTblCelEdiButVcoAtri2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=0;
                    intFilSel=tblAtri.getSelectedRow();
                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR1).toString();                    

                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos 
                    objVenConAtri2.setCampoBusqueda(1);
                    objVenConAtri2.setCriterio1(11);
                    objVenConAtri2.cargarDatos();
                    objVenConAtri2.setVisible(true);   
                    if (objVenConAtri2.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        objTblMod.setValueAt(objVenConAtri2.getValueAt(1), intFilSel, INT_TBL_DAT_NOM_ATR2);   
                        objTblMod.setValueAt(objVenConAtri2.getValueAt(2), intFilSel, INT_TBL_DAT_COD_ATR2);    
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3);   
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3);                         
                    }
                    else
                    {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR2);                         
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR2);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3);                        
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3);                        
                    }

                    
               }
            });

            objTblCelEdiTxtVcoAtri2=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConAtri2, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR2).setCellEditor(objTblCelEdiTxtVcoAtri2);
            objTblCelEdiTxtVcoAtri2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=0;
                    intFilSel=tblAtri.getSelectedRow();
                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR1).toString();                    

                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos 
                    objVenConAtri2.setCampoBusqueda(1);
                    objVenConAtri2.setCriterio1(11);
                    objVenConAtri2.cargarDatos();
                    objVenConAtri2.setVisible(true);   
                    if (objVenConAtri2.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        objTblMod.setValueAt(objVenConAtri2.getValueAt(1), intFilSel, INT_TBL_DAT_NOM_ATR2);   
                        objTblMod.setValueAt(objVenConAtri2.getValueAt(2), intFilSel, INT_TBL_DAT_COD_ATR2);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3);   
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3);                        
                    }
                    else
                    {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR2); 
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR2); 
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3); 
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3); 
                    }
                }               
            });  
            
//ATRIBUTO HIJO                    
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_ATR3;
            intColTbl[1]=INT_TBL_DAT_NOM_ATR3;


            objTblCelEdiButVcoAtri3=new ZafTblCelEdiButVcoGP(tblAtri, objVenConAtri3, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_ATR3).setCellEditor(objTblCelEdiButVcoAtri3);
            objTblCelEdiButVcoAtri3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    int intFilSel=0;
                    intFilSel=tblAtri.getSelectedRow();
                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR1).toString();
                    String hijo = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR2).toString();                    
                    ConfigurarAtri2(2,padre,hijo);//debo enviar lo que es la opcion seleccionada del combo de tipos 
                    objVenConAtri3.setCampoBusqueda(1);
                    objVenConAtri3.setCriterio1(11);
                    objVenConAtri3.cargarDatos();
                    objVenConAtri3.setVisible(true);   
                    if (objVenConAtri3.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        objTblMod.setValueAt(objVenConAtri3.getValueAt(1), intFilSel, INT_TBL_DAT_NOM_ATR3);   
                        objTblMod.setValueAt(objVenConAtri3.getValueAt(2), intFilSel, INT_TBL_DAT_COD_ATR3);                        
                    }                    
                    else
                    {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3); 
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3); 
                    }
                        
               }
            });

            objTblCelEdiTxtVcoAtri3=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConAtri3, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR3).setCellEditor(objTblCelEdiTxtVcoAtri3);
            objTblCelEdiTxtVcoAtri3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=0;
                    intFilSel=tblAtri.getSelectedRow();
                    String hijo = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ATR3).toString();                    
                    ConfigurarAtri2(2,"",hijo);//debo enviar lo que es la opcion seleccionada del combo de tipos 
                    objVenConAtri3.setCampoBusqueda(1);
                    objVenConAtri3.setCriterio1(11);
                    objVenConAtri3.cargarDatos();
                    objVenConAtri3.setVisible(true);   
                    if (objVenConAtri3.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        objTblMod.setValueAt(objVenConAtri3.getValueAt(1), intFilSel, INT_TBL_DAT_NOM_ATR3);   
                        objTblMod.setValueAt(objVenConAtri3.getValueAt(2), intFilSel, INT_TBL_DAT_COD_ATR3);                        
                    }    
                    else
                    {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_ATR3); 
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_ATR3);                         
                    }

                }               
            });              
            
            
             
//Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPri=new ZafTblPopMnu(tblAtri);
            objTblPopMnuPri.setPegarEnabled(true);
            objTblPopMnuPri.setBorrarContenidoEnabled(true);
            objTblPopMnuPri.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
////                    System.out.println("ZafTblPopMnu... ");
////                    if (objTblPopMnuPri.isClickPegar())
////                    {
////                        
////                        System.out.println("pegoooooo xD ");
////                         int intColVen[]=new int[2];
////                    }
                    if (objTblPopMnuPri.isClickBorrarContenido())
                    {
                        System.out.println("isClickBorrarContenido... ");
                        int intFilSel[], i;
                        intFilSel=tblAtri.getSelectedRows();
                    }
                    else if (objTblPopMnuPri.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[];
                        int  i;
                        intFilSel=tblAtri.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        
                        //marca
                        if (tblAtri.isColumnSelected(INT_TBL_DAT_NOM_ATR2))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConMarc.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_ATR2)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_ATR2).toString()))
                                {
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_ATR2);
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_ATR2);                                
                                }
                                else
                                {
                                    objVenConMarc.setCampoBusqueda(1);
                                    objVenConMarc.setCriterio1(11);
                                    objVenConMarc.cargarDatos();
                                    objVenConMarc.setVisible(true);
                                    if (objVenConMarc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_ATR2);
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_ATR2); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_ATR2); 
                                    }
                                }                                  
                            }
                        }
                        //material
////                        if (tblAtri.isColumnSelected(INT_TBL_DAT_NOM_MAT))
////                        {
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////                                if (objVenConMate.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MAT)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MAT).toString()))
////                                {
////                                    objTblMod.setValueAt(objVenConMate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MAT);
////                                    objTblMod.setValueAt(objVenConMate.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MAT);                                
////                                }
////                                else
////                                {
////                                    objVenConMate.setCampoBusqueda(1);
////                                    objVenConMate.setCriterio1(11);
////                                    objVenConMate.cargarDatos();
////                                    objVenConMate.setVisible(true);
////                                    if (objVenConMate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                                    {
////                                    objTblMod.setValueAt(objVenConMate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MAT);
////                                    objTblMod.setValueAt(objVenConMate.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MAT); 
////                                    }
////                                    else
////                                    {
////                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_MAT); 
////                                    }
////                                }                                  
////                            }
////                        }   
                         //linea   
                        if (tblAtri.isColumnSelected(INT_TBL_DAT_NOM_ATR3))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConLine.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_ATR3)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_ATR3).toString()))
                                {
                                    objTblMod.setValueAt(objVenConLine.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_ATR3);
                                    objTblMod.setValueAt(objVenConLine.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_ATR3);                                
                                }
                                else
                                {
                                    objVenConLine.setCampoBusqueda(1);
                                    objVenConLine.setCriterio1(11);
                                    objVenConLine.cargarDatos();
                                    objVenConLine.setVisible(true);
                                    if (objVenConLine.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConLine.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_ATR3);
                                    objTblMod.setValueAt(objVenConLine.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_ATR3); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_ATR3); 
                                    }
                                }                                  
                            }
                        }

                        //clase
////                        if (tblAtri.isColumnSelected(INT_TBL_DAT_NOM_CLASE1))
////                        {
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////                                if (objVenConClase1.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CLASE1)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CLASE1).toString()))
////                                {
////                                    objTblMod.setValueAt(objVenConClase1.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CLASE1);
////                                    objTblMod.setValueAt(objVenConClase1.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_CLASE1);                                
////                                }
////                                else
////                                {
////                                    objVenConClase1.setCampoBusqueda(1);
////                                    objVenConClase1.setCriterio1(11);
////                                    objVenConClase1.cargarDatos();
////                                    objVenConClase1.setVisible(true);
////                                    if (objVenConClase1.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                                    {
////                                    objTblMod.setValueAt(objVenConClase1.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CLASE1);
////                                    objTblMod.setValueAt(objVenConClase1.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_CLASE1); 
////                                    }
////                                    else
////                                    {
////                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_CLASE1); 
////                                    }
////                                }                                  
////                            }
////                        }
                          
////                        objVenConMarc.setCampoBusqueda(2);
////                        objVenConMarc.setVisible(true);   


                      
                        

////                            objTblCelEdiTxtVcoMarca=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConMarc, intColVen, intColTbl);
////                            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setCellEditor(objTblCelEdiTxtVcoMarca);
////                            objTblCelEdiTxtVcoMarca.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                                    System.out.println("aqui la busqueda::...");
////                                    objVenConMarc.setCampoBusqueda(1);
////                                    objVenConMarc.setCriterio1(11);
////                                }               
////                            });

            
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
////                            }
                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
////                        {
////                            calcularCosUniFac();
////                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
////                        {
////                            calcularMarUtiCos();
////                        }
                    
                }
            });

////            
//////material            
////            intColVen=null;
////            intColTbl=null;
////            
////            //new
////            intColVen=new int[2];
////            intColVen[0]=1;
////            intColVen[1]=2;
////            intColTbl=new int[2];
////            intColTbl[0]=INT_TBL_DAT_COD_MAT;
////            intColTbl[1]=INT_TBL_DAT_NOM_MAT;
////            
////
////
////            objTblCelEdiButVcoMate=new ZafTblCelEdiButVcoGP(tblAtri, objVenConMate, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setCellEditor(objTblCelEdiButVcoMate);
////            objTblCelEdiButVcoMate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
////            
////            objTblCelEdiTxtVcoMate=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConMate, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_MAT).setCellEditor(objTblCelEdiTxtVcoMate);
////            objTblCelEdiTxtVcoMate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    System.out.println("aqui la busqueda::...");
////                    objVenConMate.setCampoBusqueda(1);
////                    objVenConMate.setCriterio1(11);
////                }               
////            });             

//linea            
//////            intColVen=null;
//////            intColTbl=null;
//////            //newEnd
//////            
//////            intColVen=new int[2];
//////            intColVen[0]=1;
//////            intColVen[1]=2;
//////            intColTbl=new int[2];
//////            intColTbl[0]=INT_TBL_DAT_COD_LINEA;
//////            intColTbl[1]=INT_TBL_DAT_NOM_LINEA;
//////            
//////
//////
//////            objTblCelEdiButVcoLine=new ZafTblCelEdiButVcoGP(tblAtri, objVenConLine, intColVen, intColTbl);
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setCellEditor(objTblCelEdiButVcoLine);
//////            objTblCelEdiButVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////               }
//////            });
//////            
//////            objTblCelEdiTxtVcoLine=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConLine, intColVen, intColTbl);
//////            tcmAux.getColumn(INT_TBL_DAT_NOM_LINEA).setCellEditor(objTblCelEdiTxtVcoLine);
//////            objTblCelEdiTxtVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////                    System.out.println("aqui la busqueda::...");
//////                    objVenConLine.setCampoBusqueda(1);
//////                    objVenConLine.setCriterio1(11);
//////                }               
//////            });             


////            
//////ref_general            
////                intColVen=null;
////                intColTbl=null;
////                //newEnd
////
////                intColVen=new int[2];
////                intColVen[0]=1;
////                intColVen[1]=2;
////                intColTbl=new int[2];
////                intColTbl[0]=INT_TBL_DAT_COD_REF_GE;
////                intColTbl[1]=INT_TBL_DAT_NOM_REF_GE;
////
////
////                
////
////            objTblCelEdiButVcoRefGe=new ZafTblCelEdiButVcoGP(tblDat, objVenConRefGen, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_GE).setCellEditor(objTblCelEdiButVcoRefGe);
////            objTblCelEdiButVcoRefGe.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
////            
//////ref_detalle            
////                intColVen=null;
////                intColTbl=null;
////                //newEnd
////
////                intColVen=new int[2];
////                intColVen[0]=1;
////                intColVen[1]=2;
////                intColTbl=new int[2];
////                intColTbl[0]=INT_TBL_DAT_COD_REF_DET;
////                intColTbl[1]=INT_TBL_DAT_NOM_REF_DET;
////
////
////                
////
////            objTblCelEdiButVcoRefDet=new ZafTblCelEdiButVcoGP(tblDat, objVenConRefDet, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_DET).setCellEditor(objTblCelEdiButVcoRefDet);
////            objTblCelEdiButVcoRefDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
            
////            //CLASE1                    
////                intColVen=null;
////                intColTbl=null;
////                //newEnd
////
////                intColVen=new int[2];
////                intColVen[0]=1;
////                intColVen[1]=2;
////                intColTbl=new int[2];
////                intColTbl[0]=INT_TBL_DAT_COD_CLASE1;
////                intColTbl[1]=INT_TBL_DAT_NOM_CLASE1;
////
////
////            objTblCelEdiButVcoClase1=new ZafTblCelEdiButVcoGP(tblAtri, objVenConClase1, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setCellEditor(objTblCelEdiButVcoClase1);
////            objTblCelEdiButVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
////            
////            objTblCelEdiTxtVcoClase1=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConClase1, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_CLASE1).setCellEditor(objTblCelEdiTxtVcoClase1);
////            objTblCelEdiTxtVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    System.out.println("aqui la busqueda::...");
////                    objVenConClase1.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);
////                }               
////            });               
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);   

            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
           
            
            
            
            
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private boolean ConfigurarMaterial()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_MATERIAL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConMate=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
////    private boolean configurarTblDat()//chiqui
////    {
////        boolean blnRes=true;
////        try{
////            //Configurar JTable: Establecer el modelo.
////            vecDat=new Vector();    //Almacena los datos
////            vecCab=new Vector();    //Almacena las cabeceras
////            vecCab.clear();
////            vecCab.add(INT_TBL_DAT_LIN,"");
////            vecCab.add(INT_TBL_DAT_OPCION,"Opción");
////            vecCab.add(INT_TBL_DAT_NOM_ATR1,"Tipos");            
////            vecCab.add(INT_TBL_DAT_NOM_ATR2,"Atributo1"); 
////            vecCab.add(INT_TBL_DAT_BUT_ATR2,"");             
////            vecCab.add(INT_TBL_DAT_NOM_ATR3,"Atributo2");            
////            vecCab.add(INT_TBL_DAT_BUT_ATR2,"");             
////          
////            
////            objTblMod=new ZafTblMod();
////            objTblMod.setHeader(vecCab);
////            tblAtri.setModel(objTblMod);
////
////            
////            objTblMod=new ZafTblMod();
////            objTblMod.setHeader(vecCab);
////            //Configurar JTable: Establecer el modelo de la tabla.
////            tblAtri.setModel(objTblMod);  
////      
////            tblAtri.setCellSelectionEnabled(true);
////            tblAtri.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
////            //Configurar JTable: Establecer la fila de cabecera.
////            objColNum=new ZafColNumerada(tblAtri,INT_TBL_DAT_LIN);
////      
////            
////
////            
////            tblAtri.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
////            
////            
////            
////            //Configurar JTable: Establecer el ancho de las columnas.
////            javax.swing.table.TableColumnModel tcmAux=tblAtri.getColumnModel();
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
////            tcmAux.getColumn(INT_TBL_DAT_OPCION).setPreferredWidth(50);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setPreferredWidth(100);            
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR2).setPreferredWidth(200);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setPreferredWidth(30);            
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR3).setPreferredWidth(200);            
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR3).setPreferredWidth(30);             
////            
////            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
////            tblAtri.getTableHeader().setReorderingAllowed(false);
////            
////            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR3).setResizable(false);            
////
////            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_OPCION);
////            vecAux.add("" + INT_TBL_DAT_NOM_ATR1);
////            vecAux.add("" + INT_TBL_DAT_NOM_ATR2);
////            vecAux.add("" + INT_TBL_DAT_BUT_ATR2);            
////            vecAux.add("" + INT_TBL_DAT_NOM_ATR3);
////            vecAux.add("" + INT_TBL_DAT_BUT_ATR3);            
////////            vecAux.add("" + INT_TBL_DAT_NOM_ATR4);           
////            objTblMod.setColumnasEditables(vecAux);
////            vecAux=null;            
////
////            
////            
////            
////            objTblCelRenCmbBox=new ZafTblCelRenCbo();
////            tcmAux.getColumn(INT_TBL_DAT_OPCION).setCellRenderer(objTblCelRenCmbBox);
////            
////            objTblCelEdiCmbBox=new ZafTblCelEdiCbo(tblAtri);
////            tcmAux.getColumn(INT_TBL_DAT_OPCION).setCellEditor(objTblCelEdiCmbBox);           
////            
////            objTblCelRenCmbBoxAtr=new ZafTblCelRenCbo();
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellRenderer(objTblCelRenCmbBoxAtr);
////            
////            objTblCelEdiCmbBoxAtr=new ZafTblCelEdiCbo(tblAtri);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellEditor(objTblCelEdiCmbBoxAtr);   
////               
////            
////            //botones agregados
////            objTblCelRenBut=new ZafTblCelRenBut();
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR3).setCellRenderer(objTblCelRenBut);
////           
////
////            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR3).setResizable(false);
////            objTblCelRenBut=null;
////            
////            
////            objTblCelEdiCmbBoxAtr = new ZafTblCelEdiCbo(tblAtri);//LA DE TIPOS
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR1).setCellEditor(objTblCelEdiCmbBoxAtr);
////            objTblCelEdiCmbBoxAtr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////////                    String padre = objTblCelRenCmbBoxAtr.getSelectedItem().toString();
////////                            
////////                            ConsultaComboAtributo(padre,"",1);
////                }
////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    String hijo = objTblCelEdiCmbBoxAtr.getSelectedItem().toString();
////                    ConfigurarAtri2(1,"",hijo);//debo enviar lo que es la opcion seleccionada del combo de tipos                    
////////                    ConsultaComboAtributo(padre,"",1);
//////////                   
//////////                    }
////                }
////            });
////            
////////            objTblCelEdiCmbBoxPadre = new ZafTblCelEdiCbo(tblAtri);
////////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR2).setCellEditor(objTblCelEdiCmbBoxPadre);
////////            objTblCelEdiCmbBoxPadre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////////////                    String padre = objTblCelRenCmbBoxAtr.getSelectedItem().toString();
////////////                            
////////////                            ConsultaComboAtributo(padre,"",1);
////////                }
////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////////                    String hijo = objTblCelEdiCmbBoxPadre.getSelectedItem().toString();
////////                    ConsultaComboAtributo("",hijo,2);
//////////////                   
//////////////                    }
////////                }
////////            });            
////            
////            
////            
////            
////            
//////ATRIBUTO 2                   
////            int intColVen[]=new int[2];
////            intColVen[0]=1;
////            intColVen[1]=2;
////            int intColTbl[]=new int[2];
////            intColTbl[0]=INT_TBL_DAT_NOM_ATR2;
////            intColTbl[1]=INT_TBL_DAT_BUT_ATR2;
////            
////            
////
////
////            objTblCelEdiButVcoAtri2=new ZafTblCelEdiButVcoGP(tblAtri, objVenConAtri2, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setCellEditor(objTblCelEdiButVcoAtri2);
////            objTblCelEdiButVcoAtri2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
////            
////            objTblCelEdiTxtVcoAtri2=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConAtri2, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR2).setCellEditor(objTblCelEdiButVcoAtri2);
////            objTblCelEdiTxtVcoAtri2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    System.out.println("aqui la busqueda::...");
////                    int intFilSel=0;
////                    intFilSel=tblAtri.getSelectedRow();                    
////                    String padre = objTblCelEdiCmbBoxAtr.getSelectedItem().toString();                    
////                    ConfigurarAtri2(2,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos
////                    objVenConAtri2.setCampoBusqueda(1);
////                    objVenConAtri2.setCriterio1(11);
////                    if (objVenConAtri2.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
////                    {
////                        objTblMod.setValueAt(objVenConAtri2.getValueAt(0), intFilSel, INT_TBL_DAT_NOM_ATR2);
////                    }
////                    else
////                    {
////                        objTblMod.setValueAt("NO TIENE", intFilSel, INT_TBL_DAT_NOM_ATR2); 
////                    }                    
////                }               
////            });             
////
//////ATRIBUTO 3            
////            intColVen=null;
////            intColTbl=null;
////            //newEnd
////            
////            intColVen=new int[2];
////            intColVen[0]=1;
////            intColVen[1]=2;
////            intColTbl=new int[2];
////            intColTbl[0]=INT_TBL_DAT_NOM_ATR3;
////            intColTbl[1]=INT_TBL_DAT_BUT_ATR2;
////            
////
////
////            objTblCelEdiButVcoLine=new ZafTblCelEdiButVcoGP(tblAtri, objVenConLine, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_ATR2).setCellEditor(objTblCelEdiButVcoLine);
////            objTblCelEdiButVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////               }
////            });
////            
////            objTblCelEdiTxtVcoLine=new ZafTblCelEdiTxtVcoGP(tblAtri, objVenConLine, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_ATR3).setCellEditor(objTblCelEdiTxtVcoLine);
////            objTblCelEdiTxtVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);
////                }               
////            });             
////
////
////                       
////            
////            
////            
////            //Configurar JTable: Detectar cambios de valores en las celdas.
////            objTblModLis=new ZafVenConItmGP03_01.ZafTblModLis();
////            objTblMod.addTableModelListener(objTblModLis);   
////            
//////            objTblMod.insertRow();  
////
////            
////            
////            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
////           
////            
////            
////            ocultaColumnasPorFiltro();
////            
////            System.out.println("Se configuara la tabla!!!!... ");
////        }
////        catch(Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
    private boolean ocultaColumnasPorFiltro()//chiqui
    {
       boolean blnRes=false;
       
    //Obtener los locales a consultar.
    int i=0;
    String tx_atributo="";
    boolean blnExisteMarca=false;
    boolean blnExisteMaterial=false;
    boolean blnExisteLinea=false;
    boolean blnExisteClase1=false;
    for (i=0; i<arlDatTipAtr.size();i++) 
    {
//////        tx_atributo = objUti.getStringValueAt(arlDatTipAtr, i, INT_TBL_DAT_NOM_ATR);
//////        if (!blnExisteMarca)
//////        {          
//////            if (tx_atributo.equals("Marca"))
//////            {
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblAtri);                            
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblAtri); 
//////                    blnExisteMarca =true;
//////            }
//////            else
//////            {
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblAtri);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblAtri);
//////            }
//////        }
//////        if (!blnExisteLinea)
//////        {          
//////            if (tx_atributo.equals("Linea"))
//////            {
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblAtri);                            
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblAtri); 
//////                    blnExisteLinea =true;
//////            }
//////            else
//////            {
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblAtri);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblAtri);
//////            }  
//////        }
//////        if (!blnExisteMaterial)
//////        {        
//////            if (tx_atributo.equals("Material"))
//////            {
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblAtri);                            
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblAtri); 
//////                    blnExisteMaterial =true;
//////            }
//////            else
//////            {
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblAtri);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblAtri);
//////            } 
//////        }
//////        if (!blnExisteClase1)
//////        {
//////            if (tx_atributo.equals("Clase"))
//////            {
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblAtri);                            
//////                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblAtri); 
//////                    blnExisteClase1 =true;
//////            }  
//////            else
//////            {
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblAtri);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblAtri);
//////            } 
//////        }
    }
       return blnRes;
   }

    private boolean cargarArregloAtributos()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                arlDatTipAtr= new ArrayList();
                stmLoc = conLoc.createStatement();
                strSQL="";
                if (codMenu==4672)
                    strSQL+=" SELECT trim(tx_nom) as nombre,co_tipExt FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' and st_cfgLisPre ='S'   AND tx_nom NOT IN ("+ str_atributosnOVi +") order by tx_nom asc";
                else
                    strSQL+=" SELECT trim(tx_nom) as nombre,co_tipExt FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' AND tx_nom NOT IN ("+ str_atributosnOVi +") order by tx_nom asc";                    
                
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    arlRegTipAtr= new ArrayList();
                    arlRegTipAtr.add(INT_TBL_DAT_COD_ATR,rstLoc.getString("co_tipExt"));
                    arlRegTipAtr.add(INT_TBL_DAT_NOM_ATR,rstLoc.getString("nombre"));
                    arlDatTipAtr.add(arlRegTipAtr);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función llena el JComboBox especificado con las siguientes opciones:
     * <UL>
     * <LI>es igual a</LI>
     * <LI>no es igual a</LI>
     * <LI>comienza por</LI>
     * <LI>no comienza por</LI>
     * <LI>termina con</LI>
     * <LI>no termina con</LI>
     * <LI>contiene</LI>
     * <LI>no contiene</LI>
     * </UL>
     * @param objCboCri El JComboBox a llenar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean llenarCboCri(javax.swing.JComboBox objCboCri)
    {
        boolean blnRes=true;
        try
        {
            objCboCri.removeAllItems();
            objCboCri.addItem("");
            objCboCri.addItem("es igual a");
            objCboCri.addItem("no es igual a");
//            objCboCri.addItem("es mayor que");
//            objCboCri.addItem("es mayor o igual que");
//            objCboCri.addItem("es menor que");
//            objCboCri.addItem("es menor o igual que");
            objCboCri.addItem("comienza por");
            objCboCri.addItem("no comienza por");
            objCboCri.addItem("termina con");
            objCboCri.addItem("no termina con");
            objCboCri.addItem("contiene");
            objCboCri.addItem("no contiene");
            objCboCri.addItem("tiene N caracteres");
            objCboCri.addItem("no tiene N caracteres");
            objCboCri.setSelectedIndex(0);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.ITEMNMBR");
            arlCam.add("a1.ITEMNMBR");
            arlCam.add("a1.ITMSHNAM");
            arlCam.add("a1.ITEMDESC");
            arlCam.add("a2.UOMSCHDL");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.1");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("60");
            arlAncCol.add("270");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
//            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
//            strSQL+=" FROM tbm_inv AS a1";
//            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_reg='A'";
//            strSQL+=" ORDER BY a1.tx_codAlt";
            
            strSQL+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, a1.ITEMTYPE,  \n";
            strSQL+="        TRIM(ITMGEDSC) as ITMGEDSC, a1.CURRCOST, TRIM(a1.ITMTSHID) as ITMTSHID, TRIM(a2.BASEUOFM) as UOMSCHDL, TRIM(a1.PRCLEVEL) as PRCLEVEL      \n";
            strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1   \n";
            strSQL+=" INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV40201 as a2 ON (a1.UOMSCHDL=a2.UOMSCHDL)   \n";
            strSQL+=" WHERE a1.INACTIVE = 0 AND a1.ITEMTYPE = 1 AND (TRIM(a1.ITEMNMBR) like '%I' OR TRIM(a1.ITEMNMBR) like '%S') \n";
////            strSQL+=" ORDER BY a1.ITEMNMBR  \n";
            System.out.println("configurarVenConItm: " + strSQL);
            
            
            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario..", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar la "Unidad de medida".
     */
    private boolean configurarVenConUniMed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.BASEUOFM");
            arlCam.add("a1.BASEUOFM");
            arlCam.add("a1.UMSCHDSC");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Siglas");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT TRIM(UOMSCHDL) as UOMSCHDL, TRIM(UMSCHDSC) as UMSCHDSC, TRIM(BASEUOFM) as BASEUOFM,  UMDPQTYS ";
            strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV40201 ";
            vcoUniMed=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de unidades de medida", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUniMed.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoUniMed.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.ITEMNMBR", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
//                case 2: //Búsqueda directa por "Codigo alterno 2".
//                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
//                    {
//                        txtCodItm.setText(vcoItm.getValueAt(1));
//                        txtCodAlt.setText(vcoItm.getValueAt(2));
//                        txtCodAlt2.setText(vcoItm.getValueAt(3));
//                        txtNomItm.setText(vcoItm.getValueAt(4));
//                    }
//                    else
//                    {
//                        vcoItm.setCampoBusqueda(2);
//                        vcoItm.setCriterio1(11);
//                        vcoItm.cargarDatos();
//                        vcoItm.setVisible(true);
//                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
//                        {
//                            txtCodItm.setText(vcoItm.getValueAt(1));
//                            txtCodAlt.setText(vcoItm.getValueAt(2));
//                            txtCodAlt2.setText(vcoItm.getValueAt(3));
//                            txtNomItm.setText(vcoItm.getValueAt(4));
//                        }
//                        else
//                        {
//                            txtCodAlt2.setText(strCodAlt2);
//                        }
//                    }
//                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.ITEMDESC", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConUniMed(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUniMed.setCampoBusqueda(1);
                    vcoUniMed.setVisible(true);
                    if (vcoUniMed.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodUniMed.setText(vcoUniMed.getValueAt(1));
                        txtDesCorUniMed.setText(vcoUniMed.getValueAt(2));
                        txtDesLarUniMed.setText(vcoUniMed.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoUniMed.buscar("a1.BASEUOFM", txtDesCorUniMed.getText()))
                    {
                        txtCodUniMed.setText(vcoUniMed.getValueAt(1));
                        txtDesCorUniMed.setText(vcoUniMed.getValueAt(2));
                        txtDesLarUniMed.setText(vcoUniMed.getValueAt(3));
                    }
                    else
                    {
                        vcoUniMed.setCampoBusqueda(1);
                        vcoUniMed.setCriterio1(11);
                        vcoUniMed.cargarDatos();
                        vcoUniMed.setVisible(true);
                        if (vcoUniMed.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodUniMed.setText(vcoUniMed.getValueAt(1));
                            txtDesCorUniMed.setText(vcoUniMed.getValueAt(2));
                            txtDesLarUniMed.setText(vcoUniMed.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorUniMed.setText(strDesCorUniMed);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoUniMed.buscar("a1.UMSCHDSC", txtDesLarUniMed.getText()))
                    {
                        txtCodUniMed.setText(vcoUniMed.getValueAt(1));
                        txtDesCorUniMed.setText(vcoUniMed.getValueAt(2));
                        txtDesLarUniMed.setText(vcoUniMed.getValueAt(3));
                    }
                    else
                    {
                        vcoUniMed.setCampoBusqueda(2);
                        vcoUniMed.setCriterio1(11);
                        vcoUniMed.cargarDatos();
                        vcoUniMed.setVisible(true);
                        if (vcoUniMed.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodUniMed.setText(vcoUniMed.getValueAt(1));
                            txtDesCorUniMed.setText(vcoUniMed.getValueAt(2));
                            txtDesLarUniMed.setText(vcoUniMed.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarUniMed.setText(strDesLarUniMed);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene todas las condiciones SQL. Es decir, se concatenan las condiciones 
     * "Desde-Hasta", "Termina con" y los filtros adicionales "Código alterno 1", "Nombre del item".
     * @return La cadena que contiene las condiciones SQL a utilizar.
     */
    private String getConSQLTod()
    {
        String strOpe, strAux;
        String strRes="";
        try
        {
            //Obtener la condición.
            strRes="";
            if (txtCodItm.getText().length()>0)
            {
                strRes+=" AND " + strAliSQL + "ITMSHNAM=" + txtCodItm.getText();
            }
            if (txtCodUniMed.getText().length()>0)
            {
                strRes+=" AND " + strAliSQL + "UOMSCHDL=" + txtCodUniMed.getText();
            }
            if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
            {
                strRes+=" AND ((LOWER(" + strAliSQL + "ITMSHNAM) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(" + strAliSQL + "ITMSHNAM) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
            }
            if (txtCodAltItmTer.getText().length()>0)
            {
                strRes+=getConSQLTer(strAliSQL + "ITMSHNAM", txtCodAltItmTer.getText());
            }
////------------------------------------------------------------------------------
            if (txtCodAlt2Des.getText().length()>0 || txtCodAlt2Has.getText().length()>0)
            {
                strRes+=" AND ((LOWER(" + strAliSQL + "ITMGEDSC) BETWEEN '" + txtCodAlt2Des.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAlt2Has.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(" + strAliSQL + "ITMGEDSC) LIKE '" + txtCodAlt2Has.getText().replaceAll("'", "''").toLowerCase() + "%')";
            }
////------------------------------------------------------------------------------
            //Filtrar por "Código alterno 1":
            strOpe=optY1.isSelected()?"AND":"OR";
            strAux="";
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri1, txtCri1);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri2, txtCri2);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri3, txtCri3);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri4, txtCri4);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri5, txtCri5);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri6, txtCri6);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri7, txtCri7);
            strAux+=getConSQLFilAdi("ITMSHNAM", strOpe, cboCri8, txtCri8);
            if (strOpe.equals("OR"))
            {
                strAux=(strAux.equals("")?strAux:" AND (" + strAux.substring(4) + ")");
            }
            strRes+=strAux;
            //Filtrar por "Nombre del item":
            strOpe=optY2.isSelected()?"AND":"OR";
            strAux="";
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri9, txtCri9);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri10, txtCri10);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri11, txtCri11);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri12, txtCri12);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri13, txtCri13);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri14, txtCri14);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri15, txtCri15);
            strAux+=getConSQLFilAdi("ITEMDESC", strOpe, cboCri16, txtCri16);
            if (strOpe.equals("OR"))
            {
                strAux=(strAux.equals("")?strAux:" AND (" + strAux.substring(4) + ")");
            }
            strRes+=strAux;
            //Filtro: Revisión técnica.
            //Si están seleccionados todos los checkbox no filtar.
//            if (!(chkRevTecNo.isSelected() && chkRevTecSi.isSelected()))
//            {
//                strAux="";
//                if (chkRevTecNo.isSelected())
//                {
//                    strAux+=strAliSQL + "st_revTec IS NULL";
//                }
//                if (chkRevTecSi.isSelected())
//                {
//                    strAux+=(strAux.equals("")?"": " OR ") + strAliSQL + "st_revTec='S'";
//                }
//                strRes+=" AND (" + strAux + ")";
//            }
            //Filtro: Tipo de item.
            //Si están seleccionados todos los checkbox no filtar.
            if (!(chkTipItmBie.isSelected() && chkTipItmSer.isSelected() )  )
            {
                strAux="";
                if (chkTipItmBie.isSelected())
                {
                    strAux+="1";
                }
                if (chkTipItmSer.isSelected())
                {
                    strAux+=(strAux.equals("")?"": ", ") + "5";
                }
//                if (chkTipItmTra.isSelected())
//                {
//                    strAux+=(strAux.equals("")?"": ", ") + "'T'";
//                }
//                if (chkTipItmOtr.isSelected())
//                {
//                    strAux+=(strAux.equals("")?"": ", ") + "'O'";
//                }
                strRes+=" AND " + strAliSQL + "ITEMTYPE IN (" + strAux + ")";
            }
        }
        catch (Exception e)
        {
            strRes="";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strRes;
    }
    
 
//    Item Types:
//1 – Sales Inventory
//2 – Discontinued
//3 – Kit
//4 – Misc Charges
//5 – Services
//6 – Flat Fee
 
    
    
    /**
     * Esta función obtiene la condición SQL para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará a la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL.
     */
    private String getConSQLTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    /**
     * Esta función obtiene las condiciones adicionales de acuerdo al criterio seleccionado.
     * @param strCamUti Campo a utilizar. Sólo se puede utilizar las siguientes opciones: "tx_codAlt", "tx_nomItm".
     * @param strOpeUti Operación a utilizar. Sólo se puede utilizar las siguientes opciones: "AND" y "OR".
     * @param objCboCri JComboBox que contiene el criterio a utilizar.
     * @param objTxtCri JTextField que contiene el valor a utilizar.
     * @return La cadena que contiene el filtro adicional a utilizar.
     */
    private String getConSQLFilAdi(String strCamUti, String strOpeUti, javax.swing.JComboBox objCboCri, javax.swing.JTextField objTxtCri)
    {
        String strRes="";
        try
        {
            if (objTxtCri.getText().length()>0)
            {
                switch (objCboCri.getSelectedIndex())
                {
                    case 1: //es igual a
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti +")='" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "'";
                        break;
                    case 2: //no es igual a
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ")<>'" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "'";
                        break;
                    case 3: //comienza por
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") LIKE '" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "%'";
                        break;
                    case 4: //no comienza por
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") NOT LIKE '" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "%'";
                        break;
                    case 5: //termina con
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") LIKE '%" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "'";
                        break;
                    case 6: //no termina con
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") NOT LIKE '%" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "'";
                        break;
                    case 7: //contiene
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") LIKE '%" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "%'";
                        break;
                    case 8: //no contiene
                        strRes+=" " + strOpeUti + " LOWER(" + strAliSQL + strCamUti + ") NOT LIKE '%" + objTxtCri.getText().replaceAll("'", "''").toLowerCase() + "%'";
                        break;
                    case 9: //tiene N caracteres
                        strRes+=" " + strOpeUti + " LENGTH(" + strAliSQL + strCamUti + ")=" + objTxtCri.getText();
                        break;
                    case 10: //no tiene N caracteres
                        strRes+=" " + strOpeUti + " LENGTH(" + strAliSQL + strCamUti + ")<>" + objTxtCri.getText();
                        break;
                }
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        boolean blnRes=true;
        try
        {
            //Validar que esté seleccionado al menos una opción en "Revisión técnica".
//            if (!chkRevTecNo.isSelected() && !chkRevTecSi.isSelected())
//            {
//                mostrarMsgInf("<HTML>Es obligatorio seleccionar al menos una opción en \"Revisión técnica\".<BR>Por favor corrija y vuelva a intentarlo.</HTML>");
//                chkRevTecNo.requestFocus();
//                return false;
//            }
            //Validar que esté seleccionado al menos un "Tipo de item".
            if (!chkTipItmBie.isSelected() && !chkTipItmSer.isSelected() /*&& !chkTipItmTra.isSelected() && !chkTipItmOtr.isSelected()*/ )
            {
                mostrarMsgInf("<HTML>Es obligatorio seleccionar al menos un \"Tipo de item\".<BR>Por favor corrija y vuelva a intentarlo.</HTML>");
                chkTipItmBie.requestFocus();
                return false;
            }
            if (!isCriVal(cboCri1, txtCri1))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri2, txtCri2))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri3, txtCri3))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri4, txtCri4))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri5, txtCri5))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri6, txtCri6))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri7, txtCri7))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri8, txtCri8))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri9, txtCri9))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri10, txtCri10))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri11, txtCri11))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri12, txtCri12))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri13, txtCri13))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri14, txtCri14))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri15, txtCri15))
            {
                blnRes=false;
            }
            else if (!isCriVal(cboCri16, txtCri16))
            {
                blnRes=false;
            }
            
            if (chkSeleAtri.isSelected())
            {
                int intNumFil=objTblMod.getRowCount();                
                for (int i=0; i<intNumFil;i++)
                {
                     if ((objTblMod.getValueAt(i, INT_TBL_DAT_OPCION)==null) || (objTblMod.getValueAt(i, INT_TBL_DAT_OPCION).equals("")))
                     {
                        blnRes=false;
                        break;
                     }
                     else
                     {
                         if ((objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR1)==null) || (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ATR1).equals("")))
                             {
                                blnRes=false;
                                break;
                             }
                             else
                                blnRes =true;
                         }
                } 
                if (!blnRes)
                     mostrarMsgInf("Se debe seleccionar minimo dos campos por fila, el campo de Opción y la columna de Atributo 1 , si no desea esa selección proceda a eliminar la fila");                
            }
////////            if ((codMenu ==4659 )|| (codMenu==4672))
////////            {
////////                if ((codTipFilAgrupa ==-1) &&(chkSeleAtri.isSelected()))
////////                {
////////                    mostrarMsgInf("Para el formulario seleccionado se debe seleccionar un  tipo de agrupacion");                  
////////                    blnRes=false;
////////                }
////////                    
////////            }            
            

              
                        
                    



        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si el criterio es válido.
     * @param objCboCri JComboBox que contiene el criterio a utilizar.
     * @param objTxtCri JTextField que contiene el valor a utilizar.
     * @return true: Si el criterio es válido.
     * <BR>false: En el caso contrario.
     */
    private boolean isCriVal(javax.swing.JComboBox objCboCri, javax.swing.JTextField objTxtCri)
    {
        boolean blnRes=true;
        try
        {
            if (objCboCri.getSelectedIndex()==0 && !objTxtCri.getText().equals(""))
            {
                mostrarMsgInf("<HTML>No ha seleccionado el citerio para el valor <FONT COLOR=\"blue\">" + objTxtCri.getText() + "</FONT>.<BR>Seleccione un criterio y vuelva a intentarlo.</HTML>");
                objTxtCri.requestFocus();
                blnRes=false;
            }
            else
            {
                //Validar que el usuario haya ingresado un número en caso que la opción seleccionada sea "tiene N caracteres" o "no tiene N caracteres".
                if (objCboCri.getSelectedIndex()==9 || objCboCri.getSelectedIndex()==10)
                {
                    //Validar que sea un número entero.
                    try
                    {
                        //Validar que sea un número entero mayor a cero.
                        if (Integer.parseInt(objTxtCri.getText())<0)
                        {
                            mostrarMsgInf("Debe ingresar un número entero mayor o igual a cero.\nPor favor corrija y vuelva a intentarlo.");
                            objTxtCri.requestFocus();
                            blnRes=false;
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        mostrarMsgInf("El valor ingresado no es un número entero.\nDebe ingresar un número entero mayor o igual a cero.\nPor favor corrija y vuelva a intentarlo.");
                        objTxtCri.requestFocus();
                        blnRes=false;
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_CAN).</LI>
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).</LI>
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    /**
     * Esta función obtiene todas las condiciones SQL. Es decir, se concatenan las condiciones 
     * "Desde-Hasta", "Termina con" y los filtros adicionales "Código alterno 1", "Nombre del item".
     * @return La cadena que contiene las condiciones SQL a utilizar.
     */
    public String getCondicionesSQL()
    {
        return strConSQLItm;
    }
    
     public boolean VersionErronea()
    {
        return version;
    }    
     public boolean ValidaAtributos()
    {
        if(chkSeleAtri.isSelected())
            
           return true;
        else
           return false; 
    }      
    
    /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    public boolean limpiar()
    {
        boolean blnRes=true;
        try
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtCodUniMed.setText("");
            txtDesCorUniMed.setText("");
            txtDesLarUniMed.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
//------------------------------------------------------------------------------
            txtCodAlt2Des.setText("");
            txtCodAlt2Has.setText("");
//------------------------------------------------------------------------------
//            chkRevTecNo.setSelected(true);
//            chkRevTecSi.setSelected(true);
            chkTipItmBie.setSelected(true);
            chkTipItmSer.setSelected(true);
//            chkTipItmTra.setSelected(true);
//            chkTipItmOtr.setSelected(true);
            optY1.setSelected(true);
            optY2.setSelected(true);
            cboCri1.setSelectedIndex(0);
            txtCri1.setText("");
            cboCri2.setSelectedIndex(0);
            txtCri2.setText("");
            cboCri3.setSelectedIndex(0);
            txtCri3.setText("");
            cboCri4.setSelectedIndex(0);
            txtCri4.setText("");
            cboCri5.setSelectedIndex(0);
            txtCri5.setText("");
            cboCri6.setSelectedIndex(0);
            txtCri6.setText("");
            cboCri7.setSelectedIndex(0);
            txtCri7.setText("");
            cboCri8.setSelectedIndex(0);
            txtCri8.setText("");
            cboCri9.setSelectedIndex(0);
            txtCri9.setText("");
            cboCri10.setSelectedIndex(0);
            txtCri10.setText("");
            cboCri11.setSelectedIndex(0);
            txtCri11.setText("");
            cboCri12.setSelectedIndex(0);
            txtCri12.setText("");
            cboCri13.setSelectedIndex(0);
            txtCri13.setText("");
            cboCri14.setSelectedIndex(0);
            txtCri14.setText("");
            cboCri15.setSelectedIndex(0);
            txtCri15.setText("");
            cboCri16.setSelectedIndex(0);
            txtCri16.setText("");
            strConSQLItm="";
            txtCodAlt.requestFocus();
            objTblMod.removeAllRows();
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public int obtenerCodAgrup()
    {
        return codAgrupa;
    }
    
    public int obtenerTipoFiltAgrup()
    {
        return codTipFilAgrupa;
    }
    
    private boolean LLenarComboTiposAgrupacion()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc; 
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        String strSQLTi="";
        try{
            dataListTipoAgru.clear();
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQLTi="";
                 if (codMenu ==4659)
                     strSQLTi+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A' and co_tipagr=2 ";
                 else
                     strSQLTi+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A'";
                     
////                strSQLTi+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A'";
//////                strSQLTi+=" UNION";
//////                strSQLTi+="  SELECT 0 ,'',''";                
                rstLoc = stmLoc.executeQuery(strSQLTi);
////                cmbTipoAgru.removeAllItems();
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("tx_nom");
                    dataListTipoAgru.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataListTipoAgru.toArray(new String[0]));
                    cmbTipoAgru.setModel(model);    
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    } 
    
}
