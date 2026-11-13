

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Contabilidad.ZafCon66; 
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
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
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
 * @author  Jota
 */
public class ZafCon66_01 extends javax.swing.JDialog {
     
    private String strSQL;
     
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
   
   
    private ZafUtil objUti;
    private Connection con;
    private java.util.Date datFecAux;      
    private Statement stm;
    private ResultSet rst;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODCTA=1;      //CODIGO DE LA CUENTA 
    final int INT_TBL_NUMDIA=2;      //Numero de diario
    final int INT_TBL_MONDEB=3;      //Monto Debe
    final int INT_TBL_MONHAB=4;      //Monto Haber
    
    final int INT_TBL_CODANA=5;       
    final int INT_TBL_DESANA=6;       
    final int INT_TBL_MONANA=7;      
    
    final int INT_TBL_CODANA_1=8;     
    final int INT_TBL_DESANA_1=9;      
    final int INT_TBL_MONANA_1=10;       
    
    final int INT_TBL_CODANA_2=11;      
    final int INT_TBL_DESANA_2=12;      
    final int INT_TBL_MONANA_2=13;      
    
     
     
    
    int intNumCotBus=0;
    int intConStbBod=0;
    int[][] intColBodEmp = new int[30][2];
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    private boolean blnCliRet;
     
    Vector vecCab=new Vector();  
     
    public boolean blnAcepta = false; 
    ArrayList arlAuxColEdi=new java.util.ArrayList();
    private ArrayList arlDat;
    private ArrayList arlReg;

    StringBuffer stbDatVen=null;
    StringBuffer stbDatDevVenCom=null;
    StringBuffer stbDatInsVen=null;
    int intDatVen=0;
    int intDatDevVenCom=0;
     
    String StrEstConfDevVen="P";
    String strNomBodIng="";

    int codDoc;
    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;
    
    private String strTitu="Datos de las analiticas";
    private String strVersion=" v0.01";
    
    
    ArrayList arlItmRec;
 
    
//    private final boolean blnReaDevComVen=true;
    private final boolean blnReaDevComVen=false;  /// NO DEVOLUCIONES EN VENTA
    
    private boolean blnGenFacIni=false;
   
    private String strEmpCompVen="";
    private String strEmpTrans="";
    
 
   
   
   
    ArrayList arlSolTraDat = new ArrayList();
    ArrayList arlSolTraReg = new ArrayList();
    private ZafTblOrd objTblOrd;
    
            
 
    private java.awt.Frame Frame;
    
    private int intColStkFalSobAdi, intColStkFalSobIni, intColStkFalSobFin;
    private boolean blnPerVerColStkSob;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.

    public ZafCon66_01() {
    }
    
    private int intCodCta=-1;
    private String strFechaDesde="";
    private String strFechaHasta="";
    
    /** Creates new form pantalla dialogo */
    public ZafCon66_01(Frame parent,  ZafParSis ZafParSis, int CodCta, String FechaDesde, String FechaHasta) {
        super(parent, true);
        try{ 
            System.out.println("si llega... ");
            Frame=parent;
            this.objParSis = ZafParSis;
            objUti = new ZafUtil(); 
            intCodCta = CodCta; 
            strFechaDesde = FechaDesde ; 
            strFechaHasta = FechaHasta ;
            
            initComponents(); 
            configurarFrm();       

            this.setTitle( strTitu );
            lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014
        }
        catch (Exception e){ 
           objUti.mostrarMsgErr_F1(Frame, e);  
        } 
    }   



    private void configurarFrm(){
        configuraTbl();      
        cargarDat();
    } 
  
      
    private Vector vecReg, vecDat;
    private ArrayList arlAnaDat, arlAnaReg;

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDat()
    {
        boolean blnRes=true;
        int intCantidadAnaliticas=-1;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        java.sql.Connection conLoc;
        try
        {
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            //BigDecimal bgdSumStk=new BigDecimal("0");
            int intSumStk=0;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conLoc!=null)
            {
                stm=conLoc.createStatement();
                stmLoc=conLoc.createStatement();
                //Obtener la condición.
                String strAux="";
                
                /* Paso 1: Cuantas dimensiones maneja la cta */
                strSQL="";
                strSQL+=" SELECT a1.ACTINDX, COUNT(a2.aaTrxDimID) as Dimensiones    \n";
                strSQL+=" FROM TUVAL.dbo.AAG00200 as a1  \n";
                strSQL+=" INNER JOIN TUVAL.dbo.AAG00202 as a2 ON (a1.aaAcctClassID=a2.aaAcctClassID)  \n";
                strSQL+=" where ACTINDX = "+intCodCta+"  \n";
                strSQL+=" GROUP BY a1.ACTINDX  \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCantidadAnaliticas = rstLoc.getInt("Dimensiones");
                }
                
                  

                arlAnaDat = new ArrayList();
                
                strSQL="";
                strSQL+=" SELECT a1.ACTINDX, a2.aaTrxDimID  as Dimensiones   \n";
                strSQL+=" FROM TUVAL.dbo.AAG00200 as a1  \n";
                strSQL+=" INNER JOIN TUVAL.dbo.AAG00202 as a2 ON (a1.aaAcctClassID=a2.aaAcctClassID) \n";
                strSQL+=" where ACTINDX = "+intCodCta+"  \n";
                strSQL+=" ORDER BY a2.aaTrxDimID  \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    arlAnaReg = new ArrayList();
                    arlAnaReg.add(rstLoc.getInt("Dimensiones"));
                    arlAnaDat.add(arlAnaReg);
                }

                

                
                

                //Obtener los datos de la "Empresa seleccionada".
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a2.ACTINDX, a1.JRNENTRY,  a2.DEBITAMT Debito, a2.CRDTAMNT Credito,   \n";
                
                for(int i=0;i<arlAnaDat.size();i++){
                    strSQL+=" TRIM(j"+i+".aaTrxDimCode) as aaTrxDimCode"+i+", TRIM(j"+i+".aaTrxDimCodeDescr) as aaTrxDimCodeDescr"+i+",  (a3.DEBITAMT + a3.CRDTAMNT) as ValorAnalitica"+i+"   \n";
                    
                    if((i+1)<arlAnaDat.size()){
                        strSQL+=",";
                    }
                }
                
                strSQL+=" FROM TUVAL.dbo.AAG30000 as a1    \n";  /* -- Diario y clave de la analitica */
                strSQL+=" INNER JOIN TUVAL.dbo.AAG30001 as a2 ON (a1.aaGLHdrID=a2.aaGLHdrID)  \n";  /*  -- Diario de las analiticas  */
                strSQL+=" INNER JOIN TUVAL.dbo.AAG30002 as a3 ON (a2.aaGLHdrID=a3.aaGLHdrID AND a2.aaGLDistID=a3.aaGLDistID)   \n";  /*  -- Analiticas - valor de la cta - y porcentaje de uso */
                
                for(int i=0;i<arlAnaDat.size();i++){
                    strSQL+=" /* BLOQUE */  \n";
                    strSQL+=" INNER JOIN TUVAL.dbo.AAG30003 as S"+i+" ON (a3.aaGLHdrID=S"+i+".aaGLHdrID AND a3.aaGLDistID=S"+i+".aaGLDistID AND a3.aaGLAssignID=S"+i+".aaGLAssignID) \n"; /* Analiticas en la cta */
                    strSQL+=" INNER JOIN TUVAL.dbo.AAG00401 as J"+i+" ON ("+objUti.getIntValueAt(arlAnaDat, i, 0)+"=J"+i+".aaTrxDimID AND S"+i+".aaTrxCodeID=J"+i+".aaTrxDimCodeID) \n";   /*  Analiticas Maestro   */
                }
                strSQL+=" WHERE CAST(a1.GLPOSTDT as DATE) between "+ strFechaDesde +" AND "+ strFechaHasta +" AND a2.ACTINDX = "+intCodCta+"    \n";
                strSQL+=" ORDER BY 2, 4  \n";
                System.out.println("veamos.. " + strSQL);
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat=new Vector();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODCTA,rst.getInt("JRNENTRY"));
                    vecReg.add(INT_TBL_NUMDIA,rst.getString("JRNENTRY"));
                    vecReg.add(INT_TBL_MONDEB,rst.getInt("Debito"));
                    vecReg.add(INT_TBL_MONHAB,rst.getInt("Credito"));
                    
                    switch(arlAnaDat.size()){
                        case 1: 
                                vecReg.add(INT_TBL_CODANA,rst.getString("aaTrxDimCode0"));
                                vecReg.add(INT_TBL_DESANA,rst.getString("aaTrxDimCodeDescr0"));
                                vecReg.add(INT_TBL_MONANA,rst.getDouble("ValorAnalitica0"));
                                vecReg.add(INT_TBL_CODANA_1,"");
                                vecReg.add(INT_TBL_DESANA_1,"");
                                vecReg.add(INT_TBL_MONANA_1,"");
                                vecReg.add(INT_TBL_CODANA_2,"");
                                vecReg.add(INT_TBL_DESANA_2,"");
                                vecReg.add(INT_TBL_MONANA_2,""); 
                                break; 
                        case 2: 
                                vecReg.add(INT_TBL_CODANA,rst.getString("aaTrxDimCode0"));
                                vecReg.add(INT_TBL_DESANA,rst.getString("aaTrxDimCodeDescr0"));
                                vecReg.add(INT_TBL_MONANA,rst.getDouble("ValorAnalitica0"));
                                vecReg.add(INT_TBL_CODANA_1,rst.getString("aaTrxDimCode1"));
                                vecReg.add(INT_TBL_DESANA_1,rst.getString("aaTrxDimCodeDescr1"));
                                vecReg.add(INT_TBL_MONANA_1,rst.getDouble("ValorAnalitica1"));
                                vecReg.add(INT_TBL_CODANA_2,"");
                                vecReg.add(INT_TBL_DESANA_2,"");
                                vecReg.add(INT_TBL_MONANA_2,""); 
                                break; 
                        case 3: 
                                vecReg.add(INT_TBL_CODANA,rst.getString("aaTrxDimCode0"));
                                vecReg.add(INT_TBL_DESANA,rst.getString("aaTrxDimCodeDescr0"));
                                vecReg.add(INT_TBL_MONANA,rst.getDouble("ValorAnalitica0"));
                                vecReg.add(INT_TBL_CODANA_1,rst.getString("aaTrxDimCode1"));
                                vecReg.add(INT_TBL_DESANA_1,rst.getString("aaTrxDimCodeDescr1"));
                                vecReg.add(INT_TBL_MONANA_1,rst.getDouble("ValorAnalitica1"));
                                vecReg.add(INT_TBL_CODANA_2,rst.getString("aaTrxDimCode2"));
                                vecReg.add(INT_TBL_DESANA_2,rst.getString("aaTrxDimCodeDescr2"));
                                vecReg.add(INT_TBL_MONANA_2,rst.getDouble("ValorAnalitica2")); 
                                break;         
                    
                    }
                    vecDat.add(vecReg);
                        
                     
                }
                
                rst.close();
                stm.close();
                stmLoc.close();
                conLoc.close();
                
                
                rst=null;
                stm=null;
                stmLoc=null;
                conLoc = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
                objTblTot.calcularTotales();   
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setIndeterminate(false);
                
//                 switch(arlAnaDat.size()){
//                    case 1: 
//                        objTblMod.addSystemHiddenColumn(INT_TBL_CODANA_1, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DESANA_1, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_MONANA_1, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_CODANA_2, tblDat );
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DESANA_2, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_MONANA_2, tblDat); 
//                        break;
//                    case 2:
//                        objTblMod.addSystemHiddenColumn(INT_TBL_CODANA_2, tblDat );
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DESANA_2, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_MONANA_2, tblDat); 
//                        break;
//                 }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(Frame, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(Frame, e);
        }
        return blnRes;
    }
    
    

 
 
    private void MensajeInf(String strMensaje){
     //JOptionPane obj =new JOptionPane();
     String strTit="Mensaje del sistema Zafiro";
     JOptionPane.showMessageDialog(Frame,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
    
  
  

  
    private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODCTA,"Cód.Cta");
            vecCab.add(INT_TBL_NUMDIA,"Diario");
            vecCab.add(INT_TBL_MONDEB,"Debe");
            vecCab.add(INT_TBL_MONHAB,"Haber");
            
            vecCab.add(INT_TBL_CODANA,"CodAna");
            vecCab.add(INT_TBL_DESANA,"Analitica");
            vecCab.add(INT_TBL_MONANA,"Monto");
            
            vecCab.add(INT_TBL_CODANA_1,"CodAna2");
            vecCab.add(INT_TBL_DESANA_1,"Analitica 2");
            vecCab.add(INT_TBL_MONANA_1,"Monto");
            
            vecCab.add(INT_TBL_CODANA_2,"CodAna3");
            vecCab.add(INT_TBL_DESANA_2,"Analitica 3");
            vecCab.add(INT_TBL_MONANA_2,"Monto");
            
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
            tcmAux.getColumn(INT_TBL_CODCTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDIA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_MONDEB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_MONHAB).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_CODANA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DESANA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_MONANA).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_CODANA_1).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DESANA_1).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_MONANA_1).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_CODANA_2).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DESANA_2).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_MONANA_2).setPreferredWidth(70);
            
             
             
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_TBL_MONDEB).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_MONHAB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_MONANA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_MONANA_1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_MONANA_2).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            /////////////////////////////////////
               
            
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODCTA);
            
            arlColHid.add(""+INT_TBL_CODANA);
            arlColHid.add(""+INT_TBL_CODANA_1);
            arlColHid.add(""+INT_TBL_CODANA_2);
        
            
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
             
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            
            int intCol[]={INT_TBL_MONDEB, INT_TBL_MONHAB, INT_TBL_MONANA, INT_TBL_MONANA_1, INT_TBL_MONANA_2};
            objTblTot=new ZafTblTot(spnTbl, spnTot, tblDat, tblTot, intCol);
 
            blnRes=true;
        }
        catch(Exception e) {  
             blnRes=false;   
             objUti.mostrarMsgErr_F1(Frame,e);  
        }
        return blnRes;
    }

    
    private ZafTblTot objTblTot;                                //JTable de totales.
    
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
        spnTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
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
        spnTbl.setViewportView(tblDat);

        panTbl.add(spnTbl, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(452, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panTbl.add(spnTot, java.awt.BorderLayout.SOUTH);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
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

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
     
      try{
         
        this.setVisible(false);
    }  
      catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(Frame, Evt); 
          blnAcepta = false; 
      }
}//GEN-LAST:event_butAceActionPerformed
     
    private String strSql; 
    


  
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(Frame,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=false;
            dispose();
        }
    }
    

   
    public boolean acepta(){
      return blnAcepta;
    }
 
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
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
    private javax.swing.JScrollPane spnTbl;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
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
            
            case INT_TBL_CODCTA:
                strMsg="";
            break;
             
            case INT_TBL_MONDEB:
                strMsg="Monto Debe";
            break;
            case INT_TBL_MONHAB:
                strMsg="Monto Haber";
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

