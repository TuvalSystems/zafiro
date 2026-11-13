

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package CxC.ZafCxC82;  
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import Librerias.ZafGenDocRev.ZafGenDocRev;
import java.math.BigDecimal;
                 
/**     
 * @author  Jota
 */
public class ZafCxC82_01 extends javax.swing.JDialog {
   
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    private UltDocPrint objUltDocPrint;
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
   
    private java.util.Date datFecAux;      
    private ZafTblCelRenChk objTblCelRenChk;
    
    private final int INT_TBL_LINEA =0; 
    private final int INT_TBL_CODEMP=1;      
    private final int INT_TBL_NOMEMP=2;       
    private final int INT_TBL_CODLOC=3;       
    private final int INT_TBL_NOMLOC=4;      
    private final int INT_TBL_FECING=5;     
    private final int INT_TBL_CODTIPDOC=6;      
    private final int INT_TBL_DESCORTIPDOC=7;      
    private final int INT_TBL_DESLARTIPDOC=8;       
    private final int INT_TBL_CODDOC=9;   
    private final int INT_TBL_NUMDOC=10;    
    private final int INT_TBL_VALDOC=11;    
    private final int INT_TBL_SALDO=12;   
    private final int INT_TBL_CHKREVERSAR=13;   
    private final int INT_TBL_OBSREVERSAR=14; 
    private final int INT_TBL_BTNOBSREVERSAR=15; 
    
    private ZafThrGua objThrGua; 
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    private String strSql;
     
    private Vector vecDat, vecCab, vecReg; 
       
    public boolean blnAcepta = false; 
    private ArrayList arlDat, arlReg;
    private String strTitu="Listado de documentos ";
    private String strVersion=" v0.01", strFechaPagos="";
    private ZafTblOrd objTblOrd;
    private java.awt.Frame Frame;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk; 
    
    
    public ZafCxC82_01() {
    }
    
    private int intCodEmpGenVen, intCodLocGenVen, intCodCajSel;
    /** Creates new form pantalla dialogo */
    public ZafCxC82_01(Frame parent,  ZafParSis ZafParSis, int intCodEmp, int intCodLoc, int intCodCot, String strFecha) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
          

          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        

          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
      
          initComponents();
          intCodEmpGenVen=intCodEmp;intCodLocGenVen=intCodLoc;intCodCajSel=intCodCot;
          strFechaPagos = strFecha; 
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(Frame, e);  
       } 
    }   


        
    private void configurarFrm(){
        try{
            if(configuraTbl()){
                if(cargarDat(intCodEmpGenVen,intCodLocGenVen,intCodCajSel,strFechaPagos)){
                    blnAcepta = true;
//                    dispose();
                }
            }
        }
        catch (Exception e){ 
            objUti.mostrarMsgErr_F1(Frame, e);  
        }
    }
   
    private boolean cargarDat(int CodEmp, int CodLoc, int CodCaj, String strFecha){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;       
        BigDecimal bgdCan, bgdSalUni;
        try{    
            System.out.println("cargarDat");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                vecDat = new Vector();
                strSql="";
                strSql+=" SELECT a2.tx_nom as tx_nomEmp, a3.tx_nom as tx_nomLoc, a1.* \n";
                strSql+=" FROM ( \n";
                strSql+="   SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.fe_doc, CAST( a1.ne_numDoc1 as Varchar) as ne_numDoc,  \n";
                strSql+="           a1.fe_ing  , a4.tx_desCor, a4.tx_desLar, ROUND(SUM(a2.nd_abo),2) as nd_aboModCxC  \n";
                strSql+="   FROM tbm_cabPag AS a1 \n";
                strSql+="   INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSql+="   INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                strSql+="   INNER JOIN tbm_tipDocCaj as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locTipDoc AND a4.co_tipDoc=a5.co_tipDoc)   \n";
                strSql+="   INNER JOIN tbm_caj as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_caj=a6.co_caj) \n";
                strSql+="   WHERE a1.st_reg='A' AND a1.fe_doc='"+strFecha+"' AND a2.st_reg='A' AND a6.co_emp="+CodEmp+" AND a6.co_loc="+CodLoc+"  AND a6.co_caj="+CodCaj+"  \n";
                strSql+="   GROUP BY a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.fe_doc, a1.fe_ing, a1.ne_numDoc1, a4.tx_desCor, a4.tx_desLar  \n";
                strSql+="   UNION  ALL  \n";
                strSql+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia as co_doc, a1.fe_dia as fe_doc, a1.tx_numDia as ne_numDoc,  \n";
                strSql+="           a1.fe_ing  , a5.tx_desCor, a5.tx_desLar,   \n";
                strSql+="           ROUND(SUM(CASE WHEN a1.co_tipDoc IN (311, 312, 313) THEN a2.nd_monDeb ELSE a2.nd_monDeb*-1 END),2) as nd_aboModCxC       \n";
                strSql+="   FROM tbm_cabDia as a1 \n";
                strSql+="   INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia) \n";
                strSql+="   INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc) \n";
                strSql+="   INNER JOIN tbm_tipDocCaj as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locTipDoc AND a1.co_tipDoc=a3.co_tipDoc) \n";
                strSql+="   INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj) \n";
                strSql+="   WHERE  a1.st_reg IN('A','O','I') AND a1.fe_dia='"+strFecha+"' AND a4.co_emp="+CodEmp+" AND a4.co_loc="+CodLoc+"  AND a4.co_caj="+CodCaj+" AND \n";
                strSql+="            a3.co_tipDoc NOT IN (24, 183, 287) \n";
                strSql+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia,a1.fe_ing,  a1.tx_numDia, a5.tx_desCor, a5.tx_desLar   \n";
                strSql+=" ) as a1 \n";
                strSql+=" INNER JOIN tbm_emp as a2 ON (a1.co_emp=a2.co_emp) \n";
                strSql+=" INNER JOIN tbm_loc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc) \n";
                strSql+=" ORDER BY a1.fe_ing ";
                System.out.println("ZafCxC82_01.cargarDat" + strSql);
                bgdSalUni=BigDecimal.ZERO;
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_NOMEMP, rstLoc.getString("tx_nomEmp")); 
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc")); 
                    vecReg.add(INT_TBL_NOMLOC, rstLoc.getString("tx_nomLoc")); 
                    vecReg.add(INT_TBL_FECING, rstLoc.getString("fe_ing")); 
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipDoc")); 
                    vecReg.add(INT_TBL_DESCORTIPDOC, rstLoc.getString("tx_desCor")); 
                    vecReg.add(INT_TBL_DESLARTIPDOC, rstLoc.getString("tx_desLar")); 
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") ); 
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numDoc")); 
                    vecReg.add(INT_TBL_VALDOC,  rstLoc.getString("nd_aboModCxC")); 
                    bgdCan=rstLoc.getBigDecimal("nd_aboModCxC");
                    bgdSalUni=bgdSalUni.add(bgdCan);
                    vecReg.add(INT_TBL_SALDO,bgdSalUni);
                    vecReg.add(INT_TBL_CHKREVERSAR,"");
                    vecReg.add(INT_TBL_OBSREVERSAR, "");
                    vecReg.add(INT_TBL_BTNOBSREVERSAR, "");
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                rstLoc=null;
                objTblMod.setData(vecDat);
                tblDat .setModel(objTblMod);         
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    

                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);

                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(Frame, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(Frame, Evt); 
        }
        return blnRes;          
    }   

 
    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(Frame,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
    
  
  
    /**
     * Se configura la tabla 
     * @return 
     */
    
    private boolean configuraTbl(){
        boolean blnRes=false;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODEMP,"Cód.Emp");
            vecCab.add(INT_TBL_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
            vecCab.add(INT_TBL_NOMLOC,"Local");
            vecCab.add(INT_TBL_FECING,"Fec.Ing.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cód.Tip.Doc.");
            vecCab.add( INT_TBL_DESCORTIPDOC,"Tip.Doc." ); 
            vecCab.add( INT_TBL_DESLARTIPDOC,"Tipo de documento" );
            vecCab.add( INT_TBL_CODDOC,"Cód.Doc." );
            vecCab.add( INT_TBL_NUMDOC,"Núm.Doc." );
            vecCab.add( INT_TBL_VALDOC,"Val.Doc." );
            vecCab.add( INT_TBL_SALDO ,"Saldo");
            vecCab.add( INT_TBL_CHKREVERSAR ,"Reversar");
            vecCab.add( INT_TBL_OBSREVERSAR ,"Obs.Rev.");
            vecCab.add( INT_TBL_BTNOBSREVERSAR ,"ObsRev.");

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
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMLOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FECING).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOC).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_SALDO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHKREVERSAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_OBSREVERSAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BTNOBSREVERSAR).setPreferredWidth(30);
          
            //////////////////////////////////////////
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODDOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_SALDO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BTNOBSREVERSAR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            ButRevDoc butRevDoc = new ButRevDoc(tblDat, INT_TBL_BTNOBSREVERSAR);  
            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKREVERSAR).setCellRenderer(objTblCelRenChk);

            
            //Check: Autorizar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_CHKREVERSAR).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel, intCodTipDocSel=-1;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(objTblMod.isChecked(intFilSel, INT_TBL_CHKREVERSAR)) {
                            intCodTipDocSel = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC).toString());
                            
                            if(!(intCodTipDocSel == 92 || intCodTipDocSel == 184 ||intCodTipDocSel == 288 )){
                                objTblMod.setValueAt(false, intFilSel, INT_TBL_CHKREVERSAR);
                                MensajeInf("Solo se pueden seleccionar los cierres de caja.");
                            }
                            
                        }
                    }
                }
            });
                
            
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
           
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHKREVERSAR);
            vecAux.add("" + INT_TBL_OBSREVERSAR);    
            vecAux.add("" + INT_TBL_BTNOBSREVERSAR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;


            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

           blnRes=true;
        }catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(Frame,e);  
        }
        return blnRes;
    }
    
    
    
    private class ButRevDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButRevDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación de reversión.");
           
        }
        @Override
        public void butCLick() {
            int intRow = tblDat.getSelectedRow();
            String strObs = ( tblDat.getValueAt(intRow,  INT_TBL_OBSREVERSAR  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_OBSREVERSAR).toString());
            ZafCxC82_02 obj1 = new  ZafCxC82_02(javax.swing.JOptionPane.getFrameForComponent(Frame), true , strObs );
            obj1.show();
            if(obj1.getAceptar())
                tblDat.setValueAt( obj1.getObser(), intRow, INT_TBL_OBSREVERSAR );   
            obj1=null;  
            }
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
        btnConsultar = new javax.swing.JButton();
        btnGuardarReversion = new javax.swing.JButton();
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
        lblTit.setBounds(210, 7, 300, 14);

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

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        panSubBot.add(btnConsultar);

        btnGuardarReversion.setText("Guardar");
        btnGuardarReversion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarReversionActionPerformed(evt);
            }
        });
        panSubBot.add(btnGuardarReversion);

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

        setSize(new java.awt.Dimension(641, 470));
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

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        if(cargarDat(intCodEmpGenVen,intCodLocGenVen,intCodCajSel,strFechaPagos)){
            blnAcepta = true;
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnGuardarReversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarReversionActionPerformed
        // TODO add your handling code here:
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(objThrGua==null){
                    objThrGua=new ZafCxC82_01.ZafThrGua();
                    objThrGua.start();
                }
            }
    }//GEN-LAST:event_btnGuardarReversionActionPerformed
  
     
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
     private class ZafThrGua extends Thread{
        public void run(){
            try{
                 
                if(!guardarReversionDocumentos()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    
                }
                //Inicializar objetos si no se pudo cargar los datos.
                objThrGua=null;
            }
            catch (Exception e){
                System.err.println(e);
            }
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
    

   
    public boolean acepta(){
      return blnAcepta;
    }
 
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnGuardarReversion;
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
            
            case INT_TBL_CODEMP:
                strMsg="";
            break;
             
            case INT_TBL_NOMLOC:
                strMsg="Local";
            break;
            case INT_TBL_FECING:
                strMsg="Fecha de ingreso del documento";
            break;
            case INT_TBL_CHKREVERSAR:
                strMsg="Reversar documentos";
            break;
            case INT_TBL_DESLARTIPDOC:
                strMsg="Tipo de documento";
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
 
     
     
     /**
      * 
      * @return 
      */
     
     private boolean guardarReversionDocumentos(){
        boolean blnRes=false;
        int intCodEmp=-1, intCodLoc=-1, intCodTipDoc=-1, intCodDoc=-1;
        java.sql.Connection conLoc;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    if(objTblMod.isChecked(i,INT_TBL_CHKREVERSAR)){
                        intCodEmp=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_CODEMP).toString());  intCodLoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_CODLOC).toString());
                        intCodTipDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_CODTIPDOC).toString());   intCodDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_CODDOC).toString());
                        if(isUsuarioReversion(intCodEmp, intCodLoc, intCodCajSel, objParSis.getCodigoUsuario())){
                            if(isDocumentoReversado(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                if(conLoc!=null){
                                    conLoc.setAutoCommit(false);
                                    ZafGenDocRev objGenDocRev = new Librerias.ZafGenDocRev.ZafGenDocRev(objParSis, Frame);
                                    if(objGenDocRev.generaReversionDocumentoCierreDeCaja(conLoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                        blnRes=true;
                                        conLoc.commit();
                                        MensajeInf("Reversión realizada con éxito."); 
                                        btnConsultar.doClick();
                                    }
                                    else{
                                        conLoc.rollback();
                                        MensajeInf("El Documento "+ intCodDoc+ " no pudo ser reversado.");
                                    }
                                    objGenDocRev = null;
                                    conLoc.close();
                                    conLoc=null;
                                }
                            }
                            else{
                                MensajeInf("Documento ya posee una reversion");
                            }
                        }
                        else{
                            MensajeInf("Su usuario no cuenta con los permisos para reversar un cierre de caja");
                        }
                        
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    
    /**
    *
    * @param CodEmp
    * @param CodLoc
    * @param CodCaj
    * @param CodUsr
    * @return 
    */
     
    private boolean isUsuarioReversion(int CodEmp, int CodLoc, int CodCaj, int CodUsr){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(CodUsr==1){
                /* JM: Admin que reverse */
                blnRes=true;
            }
            else{
                /* JM: Todos los demas revision que puedan reversar */
                conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    stmLoc = conLoc.createStatement();
                    strSql="";
                    strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_caj, a1.tx_desLar, a2.co_usr, a2.st_usrResCaj, a2.st_usrRevCieCaj \n";
                    strSql+=" FROM tbm_caj as a1  \n";
                    strSql+=" INNER JOIN tbm_usrCaj as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_caj=a2.co_caj) \n";
                    strSql+=" WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_caj="+CodCaj+" AND a2.co_usr="+CodUsr+" AND a1.st_reg='A' AND a2.st_usrRevCieCaj='S'    \n";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        blnRes=true;
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null;
                }
            }
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
     
     
     
     /**
      * 24, 183, 287 INCA INCA2 INCA3     Inca se revisa en tbm_cabPag
      *   
      * 92;"CIECAJ";"Cierre de caja"        Cierres de caja, se revisan en tbm_cabDia   
      * 184;"CIECA2";"Cierre de caja 2"
      * 288;"CIECA3";"Cierre de caja 3"
      * @param CodEmp
      * @param CodLoc
      * @param CodTipDoc
      * @param CodDoc
      * @return 
      */
    private boolean isDocumentoReversado(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT st_reg \n";
                if(CodTipDoc==24 || CodTipDoc==183 || CodTipDoc==287){
                    strSql+=" FROM tbm_cabPag \n";
                }
                else if(CodTipDoc==92 || CodTipDoc==184 || CodTipDoc==288){
                    strSql+=" FROM tbm_cabDia \n";
                }
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND ";
                if(CodTipDoc==24 || CodTipDoc==183 || CodTipDoc==287){
                    strSql+=" co_doc="+CodDoc+" ";
                }
                else if(CodTipDoc==92 || CodTipDoc==184 || CodTipDoc==288){
                    strSql+=" co_dia="+CodDoc+" ";
                }
                strSql+=" \n";
                System.out.println("isDocumentoReversado " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_reg").equals("I")){
                        blnRes=false;
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
        catch (java.sql.SQLException e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    
}

