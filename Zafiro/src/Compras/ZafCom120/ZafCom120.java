package Compras.ZafCom120;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafLocPrgUsr;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javax.print.PrintService;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;
import Librerias.ZafVenCon.ZafVenCon;

/**
 *
 * @author Jose Mario Marin 
 */
public class ZafCom120 extends javax.swing.JInternalFrame {

    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;                        
    private static final int INT_TBL_DAT_CHK_SEL = 1;                   
    private static final int INT_TBL_DAT_ORDEN = 2;                    
    private static final int INT_TBL_DAT_RUC_PROV = 3;                 
    private static final int INT_TBL_DAT_PROV = 4;                 
    private static final int INT_TBL_DAT_USER = 5;                
    private static final int INT_TBL_DAT_VALOR = 6;                 

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
////    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenConGP vcoCli;                                   //Ventana de consulta.
////    private ZafVenConGP vcoVen;                                   //Ventana de consulta.
   // private ZafVenConGP vcoForImp;                                //Ventana de consulta.
    private ZafVenCon vcoForImp;                                //Ventana de consulta.
    
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstReg;                                   //Estado del registro.
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strNomForImp, strCodForImp;                                //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private boolean blnMarTodChkTblSel = true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private String strVersion = "v0.01";
    private boolean ctrl = false;
    private String keyHit = "", strDesForPagSri;
    private boolean booFormatA4 = false;
    private boolean booFormatA5 = true;
    private StringBuffer sb = new StringBuffer("");
    private int intCont = 0;
    private String strRutRpt, strNomRpt, strLogo;
    private String strSQLGlo;
    private List<InputStream> listPdfs = new ArrayList<InputStream>();
    private List<String> listPdfsDir = new ArrayList<String>();
    
    public ZafCom120(ZafParSis obj) {
        try {
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addKeyEventDispatcher(new KeyEventDispatcher() {
                        @Override
                        public boolean dispatchKeyEvent(KeyEvent e) {

                            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                                ctrl = true;
                            } else if (e.getKeyCode() == KeyEvent.VK_H) {
                                keyHit = KeyEvent.getKeyText(e.getKeyCode());
                            } else {
                                sb = new StringBuffer("");
                            }
                            if (ctrl) {
                                sb.append("ctrl ");
                            }
                            sb.append(keyHit);
                            ctrl = false;
                            keyHit = "";
                            if (intCont == 0) {
                                if (!sb.toString().startsWith("c")) {
                                    sb = new StringBuffer("");
                                }
                                if (sb.toString().contains("ctrl") && sb.toString().contains("H")) {
                                    intCont++;
                                    sb = new StringBuffer("");
                                    if (booFormatA5) {
                                        sb = new StringBuffer("");
                                        mostrarMsg("Se cambió a formato A4.");
                                        booFormatA4 = true;
                                        booFormatA5 = false;
                                        intCont = 0;
                                    } else {
                                        sb = new StringBuffer("");
                                        mostrarMsg("Se cambió a formato A5.");
                                        booFormatA4 = false;
                                        booFormatA5 = true;
                                        intCont = 0;
                                    }
                                }
                            } else {
                                sb = new StringBuffer("");
                            }
                            return false;
                        }
                    });
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            
////            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
////                
////            } else {
////                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
////                dispose();
////            }
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtorden = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblTipDoc.setText("No. orden de compra:");
        lblTipDoc.setToolTipText("No. orden de compra:");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(20, 10, 210, 20);

        txtorden.setBackground(objParSis.getColorCamposObligatorios());
        txtorden.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtordenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtordenFocusLost(evt);
            }
        });
        txtorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtordenActionPerformed(evt);
            }
        });
        panFil.add(txtorden);
        txtorden.setBounds(210, 10, 130, 20);

        lblCli.setText("Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(20, 210, 150, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(180, 210, 60, 20);

        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(240, 210, 360, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(600, 210, 20, 20);

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
        tblDat.setToolTipText("");
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

        butImp.setText("Imprimir");
        butImp.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
            //optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtDesLarCli.getText().equals("")) {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(2);
            }
        } else {
            txtDesLarCli.setText(strDesLarCli);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
            //optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli = txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } else {
            txtCodCli.setText(strCodCli);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length() > 0) {
           // optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            evt.consume();
            //();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount() == 2) {
            // abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
            
            if (tblDat != null) {
                String strMsg = "¿Está seguro que desea Imprimir?";
                if (JOptionPane.showConfirmDialog(this, strMsg, "Impresión directa", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                    if(imprimirMasivo()){
                        mostrarMsgInf("Listo");
                    }
                    else{
                        mostrarMsgInf("Error al imprimir...");
                    }
                }
            }

    }//GEN-LAST:event_butImpActionPerformed

    private void txtordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtordenActionPerformed
        txtorden.transferFocus();
    }//GEN-LAST:event_txtordenActionPerformed

    private void txtordenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtordenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtorden.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtorden.getText().equals("")) {
               
              
            } else {
               
               
            }
        } else {
            txtorden.setText(strDesCorTipDoc);
            
        }

    }//GEN-LAST:event_txtordenFocusLost

    private void txtordenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtordenFocusGained
        strDesCorTipDoc = txtorden.getText();
        txtorden.selectAll();
    }//GEN-LAST:event_txtordenFocusGained

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtorden;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 60, 472, 72);
            //Inicializar objetos.
            objUti = new ZafUtil();
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr = new ZafLocPrgUsr(objParSis);
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
            //Configurar Combos.
            configurarComboEstReg();
            //Configurar las ZafVenCon.
            configurarVenConCli();

            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarComboEstReg() {
        boolean blnRes = true;
        try {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK_SEL, "");
            vecCab.add(INT_TBL_DAT_ORDEN, "OC");
            vecCab.add(INT_TBL_DAT_RUC_PROV, "RUC");
            vecCab.add(INT_TBL_DAT_PROV, "Proveedor");
            vecCab.add(INT_TBL_DAT_USER, "Usuario");
            vecCab.add(INT_TBL_DAT_VALOR, "Valor");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ORDEN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_RUC_PROV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PROV).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_USER).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_VALOR).setPreferredWidth(65);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
////            //Configurar JTable: Ocultar columnas del sistema.
////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblSelTodMouseClicked(evt);
                }
            });

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_VALOR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_SEL);
            //vecAux.add("" + INT_TBL_DAT_);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//////            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar Jtable: Renderizar checkbox
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;
            tblDat.setEditingColumn(INT_TBL_DAT_CHK_SEL);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd = new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
////            int intCol[] = {INT_TBL_DAT_TOT_DOC};
////            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux = null;
            setEditable(true);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * Metodo para armar los filtros
     * @return 
     */
    private String sqlConFil() {
        String sqlFil = "";
        try{
            if (txtorden.getText().length()!=0)
                sqlFil +="AND ponumber ='"+ txtorden.getText() +"'";
            
            sqlFil += " AND DOCDATE BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";	
        }
        catch (Exception e) {
            sqlFil = "ERROR AL FILTRAR >>> " + e.toString();
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return sqlFil;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @param strConSQL Filtros de Consulta
     * @return true: Si se pudo realizar la consulta de los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strConSQL) {
        int intCodEmp;
        int intTipLoc = 1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        boolean blnRes = true;
        try {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp = objParSis.getCodigoEmpresa();
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) {
                stm = con.createStatement();
 
                if (objParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
 
                    strSQL += "SELECT trim(ponumber) as orden,trim (VENDORID) as ruc,trim (VENDNAME) as proveedor,";
                    strSQL += "     trim (USER2ENT) as usuario,  (ORSUBTOT+ORTAXAMT ) AS TOTAL,DOCDATE";
                    strSQL += " FROM tuval..POP10100";
                    strSQL += " WHERE ponumber!=''";
                    strSQL += strConSQL;
                    strSQL += " ORDER BY dex_row_id desc";
                    rst = stm.executeQuery(strSQL);
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT trim(ponumber) as orden,trim (VENDORID) as ruc,trim (VENDNAME) as proveedor,";
                    strSQL += "     trim (USER2ENT) as usuario,  (ORSUBTOT+ORTAXAMT ) AS TOTAL,DOCDATE";
                    strSQL += " FROM tuval..POP10100";
                    strSQL += " WHERE ponumber!=''";
                    strSQL += strConSQL;
                    strSQL += " ORDER BY dex_row_id desc";
                    rst = stm.executeQuery(strSQL);
                }
                System.out.println("cargarDetReg: " + strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()) {
                    if (blnCon) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CHK_SEL, "");
                        vecReg.add(INT_TBL_DAT_ORDEN, rst.getString("orden"));
                        vecReg.add(INT_TBL_DAT_RUC_PROV, rst.getString("ruc"));
                        vecReg.add(INT_TBL_DAT_PROV, rst.getString("proveedor"));
                        vecReg.add(INT_TBL_DAT_USER, rst.getString("usuario"));
                        vecReg.add(INT_TBL_DAT_VALOR, rst.getString("TOTAL"));
                        vecDat.add(vecReg);
                    } else {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Calcular totales.
////                objTblTot.calcularTotales();
                if (blnCon) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } else {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

  

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli() {
        boolean blnRes = true;
        int intTipLoc = 1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
 
                
                strSQL += "SELECT TRIM(VENDORID) AS co_cli,TRIM(VENDORID) AS tx_ide, TRIM(VENDNAME) AS tx_nom,TRIM(ADDRESS1) AS tx_dir";
                strSQL += " FROM tuval..PM00200 AS a1";
                strSQL += " WHERE  VADDCDPR='PRINCIPAL'";
                
            } else {
               
                strSQL = "";
 
                strSQL += "SELECT TRIM(VENDORID) AS co_cli,TRIM(VENDORID) AS tx_ide, TRIM(VENDNAME) AS tx_nom,TRIM(ADDRESS1) AS tx_dir";
                strSQL += " FROM tuval..PM00200 AS a1";
                strSQL += " WHERE  VADDCDPR='PRINCIPAL'";
            }

            //System.out.println("configurarVenConCli: "+strSQL);
            vcoCli = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */


    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */


    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton() == ZafVenConGP.INT_BUT_ACE) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    } else {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenConGP.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        } else {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    } else {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenConGP.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        } else {
                            txtDesLarCli.setText(strDesLarCli);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread {

        public void run() {
////            if (validarFiltros()) {
                if (!cargarDetReg(sqlConFil())) {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount() > 0) {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
////            }
            objThrGUI = null;
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
                        
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_ORDEN:
                    strMsg = "Ordend de Compra";
                    break;
                case INT_TBL_DAT_RUC_PROV:
                    strMsg = "Ruc del Proveedor";
                    break;
                case INT_TBL_DAT_PROV:
                    strMsg = "Nombre del Proveedor";
                    break;
                case INT_TBL_DAT_USER:
                    strMsg = "Usuario";
                    break;
                case INT_TBL_DAT_VALOR:
                    strMsg = "Valor de la orden de compra";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblSelTodMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_DAT_CHK_SEL) {
                if (blnMarTodChkTblSel) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnMarTodChkTblSel = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnMarTodChkTblSel = true;
                }
//                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                {
//                    //Mostrar columnas.
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
//                else
//                {
//                    //Ocultar columnas.
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConForImp() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_rpt");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_nomrpt");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("Reporte");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            arlAncCol.add("120");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT distinct a1.co_rpt, a1.tx_descor, a1.tx_nomrpt, a1.tx_rutabsrpt,a1.tx_rutrelrpt";
                strSQL += " from tbm_rptsis as a1";
                strSQL += " inner join tbr_rptsisprg as a2 ON (a1.co_rpt=a2.co_rpt) ";
                strSQL +="  WHERE a2.co_mnu=" + objParSis.getCodigoMenu();
               strSQL += "  ORDER BY a1.tx_desCor";
            } else {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT distinct a1.co_rpt, a1.tx_descor, a1.tx_nomrpt, a1.tx_rutabsrpt,a1.tx_rutrelrpt";
                strSQL += " from tbm_rptsis as a1";
                strSQL += " inner join tbr_rptSisUsr as a2 on (a1.co_rpt=a2.co_rpt)";
                strSQL += " where " ;
                strSQL += " a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " and a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " and a2.st_reg='S' ";
                strSQL += " ORDER BY a1.tx_desCor";
            }
            System.out.println("strSQL>>>> " + strSQL);
            strSQLGlo = strSQL;
            cargarRutas();
            vcoForImp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de los formatos de impresión", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoForImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForImp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoForImp.setCampoBusqueda(1);
                    vcoForImp.setVisible(true);
//////                    if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
//////                        txtCodForImp.setText(vcoForImp.getValueAt(1));
//////                        txtNomForImp.setText(vcoForImp.getValueAt(3));
//////                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
////                    if (vcoForImp.buscar("a1.co_rpt", txtCodForImp.getText())) {
////                        txtCodForImp.setText(vcoForImp.getValueAt(1));
////                        txtNomForImp.setText(vcoForImp.getValueAt(3));
////                    } else {
////                        vcoForImp.setCampoBusqueda(1);
////                        vcoForImp.setCriterio1(11);
////                        vcoForImp.cargarDatos();
////                        vcoForImp.setVisible(true);
////                        if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
////                            txtCodForImp.setText(vcoForImp.getValueAt(1));
////                            txtNomForImp.setText(vcoForImp.getValueAt(3));
////                        } else {
////                            txtCodForImp.setText(strCodForImp);
////                        }
////                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
////                    if (vcoForImp.buscar("a1.tx_desCor", txtNomForImp.getText())) {
////                        txtCodForImp.setText(vcoForImp.getValueAt(1));
////                        txtNomForImp.setText(vcoForImp.getValueAt(3));
////                    } else {
////                        vcoForImp.setCampoBusqueda(2);
////                        vcoForImp.setCriterio1(11);
////                        vcoForImp.cargarDatos();
////                        vcoForImp.setVisible(true);
////                        if (vcoForImp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
////                            txtCodForImp.setText(vcoForImp.getValueAt(1));
////                            txtNomForImp.setText(vcoForImp.getValueAt(3));
////                        } else {
////                            txtNomForImp.setText(strNomForImp);
////                        }
////                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    

    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strLogo = "";
        int intNumFil;
        boolean blnRes = true;
        boolean blnEnt = false;
        try {
            listPdfs = new ArrayList<InputStream>();
            intNumFil = objTblMod.getRowCountTrue();
            for (int i = 0; i < intNumFil; i++) {
                if (objTblMod.getValueAt(i, INT_TBL_DAT_CHK_SEL).equals(true)) {
                    blnEnt = true;
                    strRutRpt = this.strRutRpt;
                    strNomRpt = this.strNomRpt;
                    strLogo = this.strLogo+"logo.png";
                    Map mapPar = new HashMap();
                    mapPar.put("Cotizacion", objTblMod.getValueAt(i, INT_TBL_DAT_ORDEN).toString());
                    mapPar.put("RUTA_LOGO", strLogo);
//                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                    generarPdfs(strRutRpt, strNomRpt, mapPar);
                    
                }
            }
            if (blnEnt) {
                final String strRutaTemp = "/tmp/PdfsGrupo/";
                Random rnd = new Random();
                int intCodArcPdfTod = rnd.nextInt();
                OutputStream out = new FileOutputStream(new File(strRutaTemp + "ArcPdf" + intCodArcPdfTod + ".pdf"));
                doMerge(listPdfs, out, strRutaTemp + "ArcPdf" + intCodArcPdfTod + ".pdf", intTipRpt);
            } else {
                mostrarMsgInf("No ha seleccionado ningún registro.");
            }
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void mostrarMsg(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarRutas() {
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        Connection conn;
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                rstLoc = stmLoc.executeQuery(strSQLGlo);
                while (rstLoc.next()) {
                    strRutRpt = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                    strLogo = rstLoc.getString("tx_rutabsrpt") + rstLoc.getString("tx_rutrelrpt");
                    strNomRpt = rstLoc.getString("tx_nomrpt");
                }
                stmLoc.close();
                stmLoc = null;
                rstLoc.close();
                rstLoc = null;
                conn.close();
                conn = null;
            }

        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    /* Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param rutaReporte La ruta del reporte a generar.
     * @param nombreReporte El nombre del reporte a generar.
     * @param parametros Los parámetros que se van a pasar al reporte a generar.
     * @param tipoReporte El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */

    public boolean generarReporteA5(String rutaReporte, String nombreReporte, java.util.Map parametros, int tipoReporte) {
        boolean blnRes = true;
        try {
            //Generar el reporte.
            //Obtener la conexión sólo si es necesario.
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            }
            if (con != null) {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp = "";
                final String strRutaTemp = "/tmp/";
                final String DIRECCION_REPORTE_GUIA_VP;
                final String strNumImpVp;
                final java.util.Map parametrosVp;
                final String nombreReporteVp;
                final String strDireccionNuevaVp;
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                //System.out.println(DIRECCION_REPORTE_GUIA);
                String strDireccionNueva = strRutaTemp + nombreReporte;
                strDireccionNuevaVp = strDireccionNueva;
                DIRECCION_REPORTE_GUIA_VP = DIRECCION_REPORTE_GUIA;
                strNumImp = parametros.get("codTipDoc").toString() + parametros.get("codDoc").toString();
                strNumImpVp = strNumImp;
                parametrosVp = parametros;
                nombreReporteVp = nombreReporte;
                File filCarp = new File(strRutaTemp);
                if (!filCarp.exists()) {
                    filCarp.mkdir();
                }
                switch (tipoReporte) {
                    case 0: //Impresión directa.
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                        break;
                    case 1: //Impresión directa (Cuadro de dialogo de impresión).
                        String fileName = DIRECCION_REPORTE_GUIA;
                        String fileNumber = strNumImp;
                        String printerName = "";//Ej:sistemas_inmaconsa";
                        String jobName = "PDF Print Job";
                        String DireccionExpo = strDireccionNueva;
                        printPDF(fileName, fileNumber, printerName, jobName, parametros, con, DireccionExpo);
                        break;
                    case 2: //Vista preliminar.
                        File pdfA4 = null;
                        File pdfA5 = null;

                        JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
                        int intCantPaginas = 0;
                        if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                            intCantPaginas = reportGuiaRem.getPages().size();
                        }
                        JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                        Rectangle recA5 = new Rectangle(PageSize.A5);
                        float floRecA5 = recA5.getHeight() - 35;
                        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                        docu.setPageSize(recA52);
                        PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                        FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        PdfWriter writer = PdfWriter.getInstance(docu, fos);
                        pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        docu.open();
                        PdfContentByte cb = writer.getDirectContent();
                        PdfImportedPage page = null;
                        //Se reduce la medida escalandolo de a4 a a5
                        float Scale = 0.67f;
                        for (int i = 0; i < intCantPaginas; i++) {
                            if (i > 0) {
                                docu.newPage();
                            }
                            page = writer.getImportedPage(reader, i + 1);
                            cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
                        docu.close();
                        pdfA4.delete();
                        SwingController controller = new SwingController();
                        controller.setIsEmbeddedComponent(true);
                        PropertiesManager properties = new PropertiesManager(
                                System.getProperties(),
                                ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                        // read/store the font cache.
                        //controller.
                        ResourceBundle messageBundle = ResourceBundle.getBundle(
                                PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                        new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                        properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                        properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                        SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                
                        // add interactive mouse link annotation support via callback
                        controller.getDocumentViewController().setAnnotationCallback(
                                new MyAnnotationCallback(controller.getDocumentViewController()));
                        JPanel viewerComponentPanel = factory.buildViewerPanel();
                        JFrame applicationFrame = new JFrame();
                        //applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        applicationFrame.getContentPane().add(viewerComponentPanel);
                        // Now that the GUI is all in place, we can try openning a PDF
                        controller.openDocument(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                        applicationFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                File file = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + "vp.pdf"));
                                if (file.exists()) {
                                    file.delete();
                                    file = null;
                                }
                                File file2 = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + ".pdf"));
                                if (file2.exists()) {
                                    file2.delete();
                                    file2 = null;
                                }
                            }
                        });

                        // add the window event callback to dispose the controller and
                        // currently open document.
                        applicationFrame.addWindowListener(controller);

                        // show the component
                        applicationFrame.pack();
                        applicationFrame.setVisible(true);
                        break;
                }
                con.close();
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (net.sf.jasperreports.engine.JRException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Metodo para imprimir directamente
     *
     * @param filePath
     * @param fileNumber
     * @param printerName
     * @param jobName
     * @param parametros
     * @param con
     */
    public void printPDF(String filePath, String fileNumber, String printerName, String jobName, java.util.Map parametros, Connection con, String strDireccionExportar) {
        FileInputStream fileInputStream = null;
        File pdfA4 = null;
        File pdfA5 = null;
        try {
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(filePath, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            pdfA4 = new File(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale = 0.67f;
            for (int i = 0; i < intCantPaginas; i++) {
                if (i > 0) {
                    docu.newPage();
                }
                page = writer.getImportedPage(reader, i + 1);
                cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
            }
            docu.close();
            fileInputStream = new FileInputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            byte[] pdfContent = new byte[fileInputStream.available()];
            fileInputStream.read(pdfContent, 0, fileInputStream.available());
            ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
            final PDFFile pdfFile = new PDFFile(buffer);
            PDFPrintPage pages = new PDFPrintPage(pdfFile);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
                System.out.println(printJob.getPrintService().getName());
                printJob.getPrintService().getName();
                PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                printJob.setJobName(jobName);
                Book book = new Book();
                book.append(pages, pageFormat, pdfFile.getNumPages());
                printJob.setPageable(book);
                Paper paper = new Paper();
                if (System.getProperty("os.name").equals("Linux")) {
                    paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 200);
                } else {
                    paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 90);
                }
                pageFormat.setPaper(paper);
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                for (int count = 0; count < printServices.length; ++count) {
                    if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
                        printJob.setPrintService(printServices[count]);
                        break;
                    }
                }
                printJob.print();
            }
            fileInputStream.close();
            pdfA4.delete();
            pdfA5.delete();
        } catch (FileNotFoundException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (JRException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (IOException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (DocumentException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (PrinterException ex) {
            if (pdfA4 != null) {
                pdfA4.delete();
            }
            if (pdfA5 != null) {
                pdfA5.delete();
            }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } finally {
            try {
                fileInputStream.close();
                if (pdfA4 != null) {
                    pdfA4.delete();
                }
                if (pdfA5 != null) {
                    pdfA5.delete();
                }
            } catch (IOException ex) {
                if (pdfA4 != null) {
                    pdfA4.delete();
                }
                if (pdfA5 != null) {
                    pdfA5.delete();
                }
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
    }

    /**
     * 
     * @param rutaReporte
     * @param nombreReporte
     * @param parametros
     * @return 
     */
    public boolean generarPdfs(String rutaReporte, String nombreReporte, Map parametros) {
        boolean blnRes = true;
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            }
            if (con != null) {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp = "";
                final String strRutaTemp = "/tmp/PdfsGrupo/";
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                String strDireccionNueva = strRutaTemp + nombreReporte;
                strNumImp = "ola";
                File filCarp = new File(strRutaTemp);
                if (!filCarp.exists()) {
                    filCarp.mkdir();
                }
                File pdfA4 = null;
                File pdfA5 = null;
                System.out.println("DIRECCION_REPORTE_GUIA " + DIRECCION_REPORTE_GUIA);
                System.out.println("parametros " + parametros.toString());
                
                JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
//                int intCantPaginas = 0;
//                if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
//                    intCantPaginas = reportGuiaRem.getPages().size();
//                }
                JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                
                
                Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                Rectangle recA5 = new Rectangle(PageSize.A5);
                float floRecA5 = recA5.getHeight() - 35;
                Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                docu.setPageSize(recA52);
                PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
                FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                PdfWriter writer = PdfWriter.getInstance(docu, fos);
                pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                
//                
//                listPdfs.add(new FileInputStream(pdfA4));
//                listPdfsDir.add(strDireccionNueva.replace(".jasper",  strNumImp   + "vp.pdf"));
//                docu.open();
//                PdfContentByte cb = writer.getDirectContent();
//                PdfImportedPage page = null;
//                //Se reduce la medida escalandolo de a4 a a5
//                float Scale = 0.67f;
//                for (int i = 0; i < intCantPaginas; i++) {
//                    if (i > 0) {
//                        docu.newPage();
//                    }
//                    page = writer.getImportedPage(reader, i + 1);
//                    cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
//                }
//               docu.close();
                pdfA4.delete();
                
                con.close();
                con=null;
                
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (net.sf.jasperreports.engine.JRException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void doMerge(List<InputStream> list, OutputStream outputStream, final String strDirArcPdf, int tipoReporte)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        Rectangle recA5 = new Rectangle(PageSize.A5);
        float floRecA5 = recA5.getHeight() - 35;
        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
        document.setPageSize(recA52);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        float Scale = 0.67f;
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }
        }
        for (InputStream in : list) {
            in.close();
        }
        outputStream.flush();
        document.close();
        outputStream.close();
        for (String strDirFil : listPdfsDir) {
            File objFilEli = new File(strDirFil);
            objFilEli.delete();
        }
        listPdfsDir = new ArrayList<String>();

        switch (tipoReporte) {
            case 0: //Impresión directa.
                //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                break;
            case 1: //Impresión directa (Cuadro de dialogo de impresión).
                String printerName = "";//Ej:sistemas_inmaconsa";
                String jobName = "PDF Print Job";
                FileInputStream fileInputStream = null;
                fileInputStream = new FileInputStream(strDirArcPdf);
                byte[] pdfContent = new byte[fileInputStream.available()];
                fileInputStream.read(pdfContent, 0, fileInputStream.available());
                ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
                final PDFFile pdfFile = new PDFFile(buffer);
                PDFPrintPage pages = new PDFPrintPage(pdfFile);
                PrinterJob printJob = PrinterJob.getPrinterJob();
                if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
                    System.out.println(printJob.getPrintService().getName());
                    printJob.getPrintService().getName();
                    PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                    printJob.setJobName(jobName);
                    Book book = new Book();
                    book.append(pages, pageFormat, pdfFile.getNumPages());
                    printJob.setPageable(book);
                    Paper paper = new Paper();
                    if (System.getProperty("os.name").equals("Linux")) {
                        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 200);
                    } else {
                        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight() - 90);
                    }
                    pageFormat.setPaper(paper);
                    PrintService[] printServices = PrinterJob.lookupPrintServices();
                    for (int count = 0; count < printServices.length; ++count) {
                        if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
                            try {
                                printJob.setPrintService(printServices[count]);
                                break;
                            } catch (PrinterException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    try {
                        printJob.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
                fileInputStream.close();
                break;
            case 2: //Vista preliminar.
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(
                        System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                ResourceBundle messageBundle = ResourceBundle.getBundle(
                        PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                controller.getDocumentViewController().setAnnotationCallback(
                        new MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame();
                applicationFrame.getContentPane().add(viewerComponentPanel);
                controller.openDocument(strDirArcPdf);
                applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                applicationFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        File file = new File(strDirArcPdf);
                        if (file.exists()) {
                            file.delete();
                            file = null;
                        }
                    }
                });

                applicationFrame.addWindowListener(controller);
                applicationFrame.pack();
                applicationFrame.setVisible(true);
                break;
        }
    }
    
    private ZafRptSis objRptSisCom;                   //Reportes del Sistema.
    /**
     * 
     * @return 
     */
    
    private boolean imprimirMasivo(){
        boolean blnRes=true;
        int intNumTotRpt=-1;
        try{
            objRptSisCom = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);
            objRptSisCom.cargarListadoReportes(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoMenu(), objParSis.getCodigoUsuario()); 
            objRptSisCom.setVisible(true); 
            int intNumFil = objTblMod.getRowCountTrue();
            if (objRptSisCom.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE){
                intNumTotRpt = objRptSisCom.getNumeroTotalReportes();
                for (int i = 0; i < intNumTotRpt; i++) {
                    if (objRptSisCom.isReporteSeleccionado(i)){
                        for (int J = 0; J < intNumFil; J++) {
                            if (objTblMod.getValueAt(J, INT_TBL_DAT_CHK_SEL).equals(true)) {
                                strRutRpt = objRptSisCom.getRutaReporte(i);
                                strNomRpt = objRptSisCom.getNombreReporte(i);
                                strLogo = strRutRpt + "logo.PNG";
                                Map mapPar = new HashMap();
                                mapPar.put("Cotizacion", objTblMod.getValueAt(J, INT_TBL_DAT_ORDEN).toString());
                                mapPar.put("RUTA_LOGO", strLogo);
                                objRptSisCom.generarReporteSQL(strRutRpt, strNomRpt, mapPar, 1/* Para que se vaya por impresion directa */);
                            }
                        }
                    }
                }
            }
            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
       
    }
    
    
}
