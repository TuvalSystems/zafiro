/*
 * ZafVen76.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Ventas.ZafVen76; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet; 
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
////import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVcoGP.ZafTblCelEdiButVcoGP;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
////import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import Librerias.ZafUtilGP.ZafUtilGP;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *INT_TBL_DAT_COD_ZAF
 * @author  José Marín 
 */
public class ZafVen76 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    
    private static int INT_ARL_EXC_PRI_ITEM=0; 
    private static int INT_ARL_EXC_PRI_FRE=1;
    private static int INT_ARL_EXC_PRI_STO=2;
    private static int INT_ARL_EXC_PRI_LEA=3;
    private static int INT_ARL_EXC_PRI_MOQ=4;
    private static int INT_ARL_EXC_PRI_COB_MAX=5;
    private static int INT_ARL_EXC_PRI_PES=6; 
    
    private static int INT_ARL_CLI_LOC_LIN=0;    
    private static int INT_ARL_CLI_LOC_CLI1=1;
    private static int INT_ARL_CLI_LOC_VEN1=2;
    private static int INT_ARL_CLI_LOC_EST1=3;
    private static int INT_ARL_CLI_LOC_ERR1=4;

    private static int INT_ARL_CLI_LOC_LOC2=1;
    private static int INT_ARL_CLI_LOC_CLI2=2;
    private static int INT_ARL_CLI_LOC_VEN2=3;    
    private static int INT_ARL_CLI_LOC_EST2=4; 
    private static int INT_ARL_CLI_LOC_ERR2=5;
    
    private static int INT_ARL_EXC_SEG_ITEM=0; 
    private static int INT_ARL_EXC_SEG_MIN=1;
    private static int INT_ARL_EXC_SEG_MAX=2;
    
    private static int INT_ARL_TAB_REG_LIN=0;
    private static int INT_ARL_TAB_REG_COD=1; 
    private static int INT_ARL_TAB_REG_DES=2;
    private static int INT_ARL_TAB_REG_EST=3;
    private static int INT_ARL_TAB_REG_BLO=4;  
    private static int INT_ARL_TAB_REG_CHK=5;
        
    private static int INT_ARL_TAB_CAN_LIN=0;
    private static int INT_ARL_TAB_CAN_COD=1; 
    private static int INT_ARL_TAB_CAN_DES=2;
    private static int INT_ARL_TAB_CAN_EST=3;    
    private static int INT_ARL_TAB_CAN_BLO=4; 
    private static int INT_ARL_TAB_CAN_CHK =5;
    
    private static int INT_ARL_TAB_REG_JEF_CAN_LIN=0;
    private static int INT_ARL_TAB_REG_JEF_CAN_COD_REG=1;
    private static int INT_ARL_TAB_REG_JEF_CAN_DES_REG=2;    
    private static int INT_ARL_TAB_REG_JEF_CAN_BTN_REG=3;
    private static int INT_ARL_TAB_REG_JEF_CAN_COD_JEF=4;    
    private static int INT_ARL_TAB_REG_JEF_CAN_DES_JEF=5;
    private static int INT_ARL_TAB_REG_JEF_CAN_BTN_JEF=6;    
    private static int INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT=7;    
    private static int INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT=8; 
    private static int INT_ARL_TAB_REG_JEF_CAN_CHK_ANU=9; 
    private static int INT_ARL_TAB_REG_JEF_CAN_BLO=10; 
    private static int INT_ARL_TAB_REG_JEF_CAN_EST=11; 
    
    
    
    private static int INT_ARL_TAB_REG_JEFVEN_LIN=0;
    private static int INT_ARL_TAB_REG_JEFVEN_COD_JEF=1;
    private static int INT_ARL_TAB_REG_JEFVEN_DES_JEF=2;    
    private static int INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF=3;       
    private static int INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF=4;       
    private static int INT_ARL_TAB_REG_JEFVEN_DES_CAN_JEF=5;           
    private static int INT_ARL_TAB_REG_JEFVEN_BTN_JEF=6;
    private static int INT_ARL_TAB_REG_JEFVEN_COD_VEN=7;    
    private static int INT_ARL_TAB_REG_JEFVEN_DES_VEN=8;
    private static int INT_ARL_TAB_REG_JEFVEN_BTN_VEN=9;    
    private static int INT_ARL_TAB_REG_JEFVEN_COD_JEF_ANT=10;    
    private static int INT_ARL_TAB_REG_JEFVEN_COD_VEN_ANT=11; 
    private static int INT_ARL_TAB_REG_JEFVEN_COD_CAN_ANT=12;     
    private static int INT_ARL_TAB_REG_JEFVEN_CHK_ANU=13; 
    private static int INT_ARL_TAB_REG_JEFVEN_BLO=14; 
    private static int INT_ARL_TAB_REG_JEFVEN_EST=15; 
    
    
    private static int INT_ARL_TAB_USU_JEF_CAN_LIN=0;
    private static int INT_ARL_TAB_USU_JEF_CAN_COD_CAN=1;
    private static int INT_ARL_TAB_USU_JEF_CAN_DES_CAN=2;    
    private static int INT_ARL_TAB_USU_JEF_CAN_BTN_CAN=3;
    private static int INT_ARL_TAB_USU_JEF_CAN_COD_USU=4;    
    private static int INT_ARL_TAB_USU_JEF_CAN_DES_USU=5;
    private static int INT_ARL_TAB_USU_JEF_CAN_BTN_USU=6;     
    private static int INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT=7;    
    private static int INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT=8;    
    private static int INT_ARL_TAB_USU_JEF_CAN_CHK_ANU=9;        
    private static int INT_ARL_TAB_USU_JEF_CAN_BLO=10;
    private static int INT_ARL_TAB_USU_JEF_CAN_EST=11;
    
    
    
    private static int INT_ARL_TAB_SEG_LIN=0;
    private static int INT_ARL_TAB_SEG_ITEM=1; 
    private static int INT_ARL_TAB_SEG_BODE=2; 
    private static int INT_ARL_TAB_SEG_MIN=3;
    private static int INT_ARL_TAB_SEG_MAX=4;    
    
   
    
    private static final int INT_TBL_DAT_LIN2=0;
    private static final int INT_TBL_DAT_CHK_SELECCION=1;
    private static final int INT_TBL_DAT_NOMBRE=2;
    
    //Variables  
    private Connection conGP;
    private Statement stmGP;
    private ResultSet rstGP;
    private Connection conZAF;    
    private Statement stmZAF;
    private ResultSet rstZAF;    
    
    private ZafParSis objParSis;
    private ZafUtil objUti;                                     //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafUtilGP objUtiGP;    
    private ZafTblMod objTblModReg;
    private ZafTblMod objTblModCliLoc;
    private ZafTblMod objTblModCan;
    private ZafTblMod objTblModRegJefCan;
    private ZafTblMod objTblModUsuJefCan;    
    private ZafTblMod objTblModJefVen;    
    private ZafTblMod objTblMod11;
    private ZafColNumerada objColNumReg;
    
   
    private ZafColNumerada objColNumCan;    
    private ZafTblPopMnu objTblPopMnuReg, objTblPopMnuPriReg;
    private ZafTblPopMnu objTblPopMnuCan, objTblPopMnuPriCan; 
    private ZafTblPopMnu objTblPopMnuRegJefCan, objTblPopMnuPriRegJefCan;     
    private ZafTblPopMnu objTblPopMnuJefVen, objTblPopMnuPriJefVen;         
    private ZafColNumerada objColNumRegJefCan;        
    private ZafColNumerada objColNumJefVen;         
    private ZafColNumerada objColNumUsuJefCan;            
    private ZafTblPopMnu objTblPopMnuUsuJefCan, objTblPopMnuPriUsuJefCan;         
  
//////    private ZafMouMotAda1 objMouMotAda1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLblReg;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblCan;                    //Render: Presentar JLabel en JTable.    
    private ZafTblCelRenLbl objTblCelRenLblRegJefCan;                    //Render: Presentar JLabel en JTable.        
    private ZafTblCelRenLbl objTblCelRenLblJefVen;                    //Render: Presentar JLabel en JTable.            
    private ZafTblCelRenLbl objTblCelRenLblUsuJefCan;                    //Render: Presentar JLabel en JTable.       
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
////    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtReg;                    //Editor: JTextField en celda.    
    private ZafTblCelEdiTxt objTblCelEdiTxtCan;                    //Editor: JTextField en celda.    
    private ZafTblCelEdiTxt objTblCelEdiTxtUsuJefCan;                    //Editor: JTextField en celda.        
    private ZafTblCelEdiTxt objTblCelEdiTxtRegJefCan;                    //Editor: JTextField en celda.        
    private ZafTblCelEdiTxt objTblCelEdiTxtJefVen;                    //Editor: JTextField en celda.            
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenBut objTblCelRenButRegJefCan;                     //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButJefVen;                     //Render: Presentar JButton en JTable.    
    private ZafTblCelRenBut objTblCelRenButUsuJefCan;                     //Render: Presentar JButton en JTable.    
    private ZafTblModLis objTblModLisReg;                           //Detectar cambios de valores en las celdas.
    private ZafTblModLis objTblModLisCan;                           //Detectar cambios de valores en las celdas.   
    private ZafTblModLis objTblModLisRegJefCan;                           //Detectar cambios de valores en las celdas.       
    private ZafTblModLis objTblModLisUsuJefCan;                           //Detectar cambios de valores en las celdas.       
    private ZafTblModLis objTblModLisJefVen;                           //Detectar cambios de valores en las celdas.           

    private ZafTblOrd objTblOrdReg;
    private ZafTblOrd objTblOrdCan;    
    private ZafTblBus objTblBus;
    private ZafTblEdi objTblEdiReg;                                //Editor: Editor del JTable.
    private ZafTblEdi objTblEdiCan;                                //Editor: Editor del JTable.
    private ZafTblEdi objTblEdiRegJefCan;                                //Editor: Editor del JTable.    
    private ZafTblEdi objTblEdiUsuJefCan;                                //Editor: Editor del JTable.       
    private ZafTblEdi objTblEdiJefVen;                                //Editor: Editor del JTable.       
    private ZafVenCon vcoMar;
    private ZafVenCon vcoCom;
    private ZafVenCon vcoCho;
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLisReg;
    private ZafDocLis objDocLisCan;    
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.util.Date datFecAuxZAF;                           //Auxiliar: Para almacenar fechas.    
    private ZafThreadGUI objThrGUI;

    private boolean blnHayCam;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. 
    private boolean blnEjeIni=true;      
    private Vector vecDatReg, vecRegReg, vecCabReg, vecAuxReg;
    private Vector vecDatCan, vecRegCan, vecCabCan, vecAuxCan;    
    private Vector vecDatRegJefCan, vecRegRegJefCan, vecCabRegJefCan, vecAuxRegJefCan;    
    private Vector vecDatUsuJefCan, vecRegUsuJefCan, vecCabUsuJefCan, vecAuxUsuJefCan;        
    private Vector vecDatCliLoc, vecRegCliLoc, vecCabCliLoc, vecAuxCliLoc;
    private Vector vecDatJefVen, vecRegJefVen, vecCabJefVen, vecAuxJefVen;        
    
    private Vector vecTipVeh;
    
    private String strSQLGP, strAux;
    private String strCodMar, strNomMar;                       //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                       //Contenido del campo al obtener el foco.
    

    
    
    
    private ZafVenConItmGP01 objVenConItm01GP;
    
    private ZafTblMod objTblModTipo;
    private ZafTblCelRenChk objTblCelRenChk;    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas. 
    private ZafThGuarGUI objThrGuaGUI;
    private String version="v0.2";
    private ZafHisTblBasDat objHisTblBasDat;  
    private ZafHisTblBasDatGP objHisTblBasDatGP;
     
    private String[][] ColumasExcel;
    
    String strCodItmDynBus="";
    private ArrayList arlDatExcelVal, arlRegExcelVal;
    private int INT_EXC_PRI_COD_ITM=0;
    private int INT_EXC_PRI_BLN=1;    
    private int INT_EXC_SEG_COD_ITM=0;
    private int INT_EXC_SEG_BLN=1;    
    private String strSql="";
    private boolean blnResNO=false;
    
    int valTip=0;
    private Connection con;
    private Statement stm;
    private ResultSet rst;    

    
    private int indTab =0;
    private int ne_modRe =0;
    private int ne_modCan =0;
    private int ne_modCanJef =0;
    private int ne_modRegJef =0;
    private int ne_modJefVen =1;    
    private int ne_modCliVen =0;
    ZafVenConGP objVenConUsuJefCan; //*****************  
    ZafVenConGP objVenConReg; //*****************     
    ZafVenConGP objVenConUsu; //*****************       
    ZafVenConGP objVenConCan; //*****************           
    ZafVenConGP objVenConJefVen; //*****************         }
    ZafVenConGP objVenConUsuJefVen; //*****************      
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCanJefCan;            //Editor: JButton en celda     
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoUsuJefCan;            //Editor: JButton en celda 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoReg;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoReg;           //Editor: JTextField de consulta en celda. 
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoUsuJefCan;           //Editor: JTextField de consulta en celda.     
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoUsu;            //Editor: JButton en celda         
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoUsu;           //Editor: JTextField de consulta en celda.     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoCanJefCan;           //Editor: JTextField de consulta en celda.         
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoJefVen;            //Editor: JButton en celda         
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoJefVen;           //Editor: JTextField de consulta en celda.     
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoUsuJefVen;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoUsuJefVen;           //Editor: JTextField de consulta en celda.         
    
    
    


    
    private boolean ValidacionesJefeCanal()//chiqui
    {
        boolean blnRes=false;
        int intNumFil=0,i=0,j=0;
        intNumFil=objTblModUsuJefCan.getRowCountTrue();
        String txCodcanI="",txCodUsuI="";
        String txCodcanF="",txCodUsuF="";
        int cont=0;
        for (i=0; i<intNumFil;i++)
        {       
            cont=0;
            if (objUti.parseString(objTblModUsuJefCan.getValueAt(i,0)).equals("I")) 
            {
                txCodcanI=objUti.codificar(objTblModUsuJefCan.getValueAt(i,INT_ARL_TAB_USU_JEF_CAN_COD_CAN));
                txCodUsuI=objUti.codificar(objTblModUsuJefCan.getValueAt(i,INT_ARL_TAB_USU_JEF_CAN_COD_USU));
                for (j=0; j<intNumFil;j++)
                {                 
                     if (objUti.parseString(objTblModUsuJefCan.getValueAt(j,0)).equals("I") )
                     {
                        txCodcanF=objUti.codificar(objTblModUsuJefCan.getValueAt(j,INT_ARL_TAB_USU_JEF_CAN_COD_CAN));
                        txCodUsuF=objUti.codificar(objTblModUsuJefCan.getValueAt(j,INT_ARL_TAB_USU_JEF_CAN_COD_USU));
                         if (txCodcanI.equals(txCodcanF) && txCodUsuI.equals(txCodUsuF))
                             cont++;
                     }
                }
                if (cont>1)
                {
                    blnRes=true;
                    break;
                }
            }
        }
        return blnRes;
    }   
    
    private boolean ValidacionesJefeRegion()//chiqui
    {
        boolean blnRes=false;
        int intNumFil=0,i=0,j=0;
        intNumFil=objTblModRegJefCan.getRowCountTrue();
        String txRegI="",txCodJefI="";
        String txRegF="",txCodJefF="";
        int cont=0;
        for (i=0; i<intNumFil;i++)
        {       
            cont=0;
            if (objUti.parseString(objTblModRegJefCan.getValueAt(i,0)).equals("M")) 
            {
                txRegI=objUti.codificar(objTblModRegJefCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_REG));
                txCodJefI=objUti.codificar(objTblModRegJefCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_JEF));
                for (j=0; j<intNumFil;j++)
                {                 
                     if (objUti.parseString(objTblModRegJefCan.getValueAt(i,0)).equals("M") )
                     {
                        txRegF=objUti.codificar(objTblModRegJefCan.getValueAt(j,INT_ARL_TAB_REG_JEF_CAN_COD_REG));
                        txCodJefF=objUti.codificar(objTblModRegJefCan.getValueAt(j,INT_ARL_TAB_REG_JEF_CAN_COD_JEF));
                         if (txRegI.equals(txRegF) && txCodJefI.equals(txCodJefF))
                             cont++;
                     }
                }
                if (cont>1)
                {
                    blnRes=true;
                    break;
                }
            }
        }
        return blnRes;
    }     

    private boolean ValidacionesRegion() // listo
    {
        int intNumFil = objTblModReg.getRowCountTrue();
        String txRegN = "";

        for (int i = 0; i < intNumFil; i++)
        {
            Object objBlo = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_BLO);
            String strBlo = (objBlo == null ? "" : objBlo.toString());

            // Solo validar filas nuevas
            if (!strBlo.equals("B"))
            {
                txRegN = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_DES).toString();

                if (ValidacionRegionIngresada(txRegN))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean ProcesoRegion()
    {
    int intNumFil, i;
    boolean blnRes = false;
    String strFecUltMod = "";
    String strSQLInt = "";
    String cabecera = "", values = "";
    String strRegion = "";
    Object objBlo = null;
    String strBlo = "";

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Guardando datos...");

        con = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021());

        datFecAux = objUti.getFechaServidor(
                objParSis.getStringConexion(),
                objParSis.getUsuarioBaseDatos(),
                objParSis.getClaveBaseDatos(),
                objParSis.getQueryFechaHoraBaseDatos());

        strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

        if (con != null)
        {
            stm = con.createStatement();
            con.setAutoCommit(false);

            intNumFil = objTblModReg.getRowCountTrue();
            strSQLInt = "";

            for (i = 0; i < intNumFil; i++)
            {
                objBlo = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_BLO);
                strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                // 🔹 CHECKBOX
                boolean isChecked = false;
                Object objChk = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_CHK);
                if (objChk != null)
                    isChecked = (Boolean) objChk;

                // 🔴 1. INSERT (nuevos registros)
                if (objUti.parseString(objTblModReg.getValueAt(i, 0)).equals("I") && !strBlo.equals("B"))
                {
                    strRegion = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_DES).toString();

                    cabecera = "(tx_reg, st_reg, fe_ing, co_usring)";
                    values = "('" + strRegion + "', 'A', '" + strFecUltMod + "', " + objParSis.getCodigoUsuario() + ")";

                    strSQLInt += "INSERT INTO Dynamics2021..tbm_reg " + cabecera + " VALUES " + values + ";\n";
                    blnRes = true;
                }

                // 🔴 2. UPDATE (anular si tiene B y está chequeado)
                if (isChecked && strBlo.equals("B"))
                {
                    String coReg = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_COD).toString();

                    strSQLInt += "UPDATE Dynamics2021..tbm_reg "
                            + "SET st_reg = 'I', "
                            + "fe_ing = '" + strFecUltMod + "', "
                            + "co_usring = " + objParSis.getCodigoUsuario() + " "
                            + "WHERE co_idreg = " + coReg + ";\n";

                    blnRes = true;
                }
            }

            if (blnRes)
            {
                System.out.println(strSQLInt);

                stm.executeUpdate(strSQLInt);
                con.commit();
                blnRes = true;
            }
            else
            {
                con.rollback();
                blnRes = false;
                mostrarMsgInf("<HTML>No existen registros para procesar.</HTML>");
            }

            stm.close();
            con.close();
            stm = null;
            con = null;
            datFecAux = null;

            objTblModReg.initRowsState();
            objTblModReg.setDataModelChanged(false);

            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        try
        {
            if (con != null)
                con.rollback();
        }
        catch (Exception ex) {}

        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}
    
    private boolean ValidacionRegionIngresada(String region) //listo
    {
        try
        {
            if (region == null || region.trim().equals(""))
                return false;

            String strRegion = region.trim();

            for (int i = 0; i < vecRegRegCargados.size(); i++)
            {
                Vector vecAuxInt = vecRegRegCargados.get(i);

                String strDes = vecAuxInt.get(INT_ARL_TAB_REG_DES) == null
                        ? ""
                        : vecAuxInt.get(INT_ARL_TAB_REG_DES).toString().trim();

                if (strRegion.equalsIgnoreCase(strDes))
                    return true;
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return false;
    }
    
    private boolean ValidacionesCanal() // lsito
    {
        int intNumFil = objTblModCan.getRowCountTrue();
        String txCanN = "";

        for (int i = 0; i < intNumFil; i++)
        {
            Object objBlo = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_BLO);
            String strBlo = (objBlo == null ? "" : objBlo.toString());

            // Solo validar filas nuevas
            if (!strBlo.equals("B"))
            {
                txCanN = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_DES).toString();

                if (ValidacionCanalIngresada(txCanN))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean ValidacionCanalIngresada(String canal) //lsito
    {
        try
        {
            if (canal == null || canal.trim().equals(""))
                return false;

            String strCanal = canal.trim();

            for (int i = 0; i < vecRegCanCargados.size(); i++)
            {
                Vector vecAuxInt = vecRegCanCargados.get(i);

                String strDes = vecAuxInt.get(INT_ARL_TAB_CAN_DES) == null
                        ? ""
                        : vecAuxInt.get(INT_ARL_TAB_CAN_DES).toString().trim();

                if (strCanal.equalsIgnoreCase(strDes))
                    return true;
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return false;
    }
    
    private boolean ProcesoCanal()
    {
        int intNumFil, i;
        boolean blnRes = false;
        String strFecUltMod = "";
        String strSQLInt = "";
        String cabecera = "", values = "";
        String strCanal = "";
        Object objBlo = null;
        String strBlo = "";

        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            datFecAux = objUti.getFechaServidor(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos(),
                    objParSis.getQueryFechaHoraBaseDatos()
            );

            strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

            if (con != null)
            {
                stm = con.createStatement();
                con.setAutoCommit(false);

                intNumFil = objTblModCan.getRowCountTrue();
                strSQLInt = "";

                for (i = 0; i < intNumFil; i++)
                {
                    objBlo = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_BLO);
                    strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                    // 🔹 CHECK
                    boolean isChecked = false;
                    Object objChk = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_CHK);
                    if (objChk != null)
                        isChecked = (Boolean) objChk;

                    // 🔴 1. INSERT (nuevos)
                    if (objUti.parseString(objTblModCan.getValueAt(i, 0)).equals("I") && !strBlo.equals("B"))
                    {
                        strCanal = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_DES).toString();

                        // 🔹 evitar error por comillas
                        strCanal = strCanal.replace("'", "''");

                        cabecera = "(tx_canasi, st_reg, fe_ing, co_usring)";
                        values = "('" + strCanal + "', 'A', '" + strFecUltMod + "', " + objParSis.getCodigoUsuario() + ")";

                        strSQLInt += "INSERT INTO Dynamics2021..tbm_canasi " + cabecera + " VALUES " + values + ";\n";
                        blnRes = true;
                    }

                    // 🔴 2. UPDATE (anular con check y B)
                    if (isChecked && strBlo.equals("B"))
                    {
                        String coCan = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_COD).toString();

                        strSQLInt += "UPDATE Dynamics2021..tbm_canasi "
                                + "SET st_reg = 'I', "
                                + "fe_ing = '" + strFecUltMod + "', "
                                + "co_usring = " + objParSis.getCodigoUsuario() + " "
                                + "WHERE co_id = " + coCan + ";\n";

                        blnRes = true;
                    }
                }

                if (blnRes)
                {
                    System.out.println(strSQLInt);

                    stm.executeUpdate(strSQLInt);
                    con.commit();
                    blnRes = true;
                }
                else
                {
                    con.rollback();
                    blnRes = false;
                    mostrarMsgInf("<HTML>No existen registros para procesar.</HTML>");
                }

                stm.close();
                con.close();
                stm = null;
                con = null;
                datFecAux = null;

                objTblModCan.initRowsState();
                objTblModCan.setDataModelChanged(false);

                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            try
            {
                if (con != null)
                    con.rollback();
            }
            catch (Exception ex) {}

            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean ProcesoRegJeFCan()
    {
        int intNumFil, i;
        boolean blnRes=false;
        java.sql.ResultSet rstLoc;
        String strFecUltMod="";
        String strSQLGlo ="";
        String strSQLUpdt="",cabeceraUpdt="",valuesUpdt="";
        String strSQLIns="",cabeceraIns="",valuesIns="";
        String strCodRegAnt="",strCodJefCanAnt="",strCodRegAct="",strCodJefCanACt="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());                   
            if (con!=null)
            {
                strSQLUpdt="";
                cabeceraUpdt="";
                valuesUpdt="";
                strSQLIns="";
                cabeceraIns="";
                valuesIns="";                
                
                stm=con.createStatement();
                con.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());                   
                intNumFil=objTblModReg.getRowCountTrue();
////                strSQL="";
                strSQLUpdt="";
                strSQLIns="";
                strSQLGlo="";
                for (i=0; i<intNumFil;i++)
                { 
                    if (objUti.parseString(objTblModCan.getValueAt(i,0)).equals("M")  )
                    {
                        if (objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT)) !="")
                        {
                            strCodRegAnt=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT));
                            strCodJefCanAnt=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT));
                            strCodRegAct=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_REG));
                            strCodJefCanACt=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_JEF));                        
                            if (!strCodRegAnt.equals(strCodRegAct) || !strCodJefCanAnt.equals(strCodJefCanACt))
                            {
                                cabeceraUpdt ="SET co_idreg = "+strCodRegAct+", co_jefcan ="+ strCodJefCanACt+", fe_ultmod ="+strFecUltMod+", co_usrmod ="+objParSis.getCodigoUsuario()+" ";
                                valuesUpdt =" WHERE co_idreg = "+strCodRegAnt+" and  co_jefcan = "+ strCodJefCanAnt +"";
                                strSQLUpdt+=" UPDATE Dynamics2021..tbr_regjefcan  "+ cabeceraUpdt +" VALUES "+ valuesUpdt +";\n";   
                                blnRes=true;
                            }                            
                        }
                        else
                        {
                            strCodRegAct=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_REG));
                            strCodJefCanACt=objUti.codificar(objTblModCan.getValueAt(i,INT_ARL_TAB_REG_JEF_CAN_COD_JEF));   
                            cabeceraIns =" (co_idreg,co_jefcan,st_reg,fe_ing,co_usring) ";
                            valuesIns =" VALUES ( "+strCodRegAct+","+ strCodJefCanACt +" ,'A',"+strFecUltMod+" ,"+objParSis.getCodigoUsuario()+")";
                            strSQLIns+=" INSERT INTO Dynamics2021..tbr_regjefcan  "+ cabeceraUpdt +" VALUES "+ valuesUpdt +";\n";   
                            blnRes=true;                            
                        }

                    }
                }  
                if (blnRes)
                {
                    strSQLGlo = strSQLUpdt + "\n" + strSQLIns;
                    stm.executeUpdate(strSQLGlo);
//////                    if(objHisTblBasDatGP.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
//////                    { 
                        con.commit();
                        blnRes=true;
                    }
                    else
                    {
                        con.rollback();                                        
                        blnRes=false;
                        mostrarMsgInf("<HTML>No se realizo la modificacion de forma exitosa.</HTML>");
                    }    
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblModReg.initRowsState();
                objTblModReg.setDataModelChanged(false);
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);                
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
    
    private boolean ProcesoUsuJeFCan()
    {
        int intNumFil, i;
        boolean blnRes = false;
        String strFecUltMod = "";
        String strSQLGlo = "";

        Integer CodCanAnt = null;
        Integer CodUsuAnt = null;
        Integer CodCanAct = null;
        Integer CodUsuAct = null;

        StringBuilder sqlUpdt = new StringBuilder();
        StringBuilder sqlIns = new StringBuilder();

        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            datFecAux = objUti.getFechaServidor(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos(),
                    objParSis.getQueryFechaHoraBaseDatos()
            );

            strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

            if (con != null)
            {
                stm = con.createStatement();
    ////            con.setAutoCommit(false);

                intNumFil = objTblModUsuJefCan.getRowCountTrue();

                for (i = 0; i < intNumFil; i++)
                {
                    if ("I".equals(objUti.parseString(objTblModUsuJefCan.getValueAt(i, 0))))
                    {
                        Object objCanAnt = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT);
                        Object objUsuAnt = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT);
                        Object objCanAct = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                        Object objUsuAct = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                        CodCanAnt = obtenerEntero(objCanAnt);
                        CodUsuAnt = obtenerEntero(objUsuAnt);
                        CodCanAct = obtenerEntero(objCanAct);
                        CodUsuAct = obtenerEntero(objUsuAct);

                        // Los valores actuales sí deben existir
                        if (CodCanAct == null || CodUsuAct == null)
                        {
                            throw new Exception("Los valores actuales de canal y usuario no pueden estar vacíos en la fila " + (i + 1));
                        }

                        boolean tieneAnterior = (CodCanAnt != null && CodUsuAnt != null);

                        if (tieneAnterior)
                        {
                            // Solo actualizar si cambió alguno de los dos valores
                            if (!CodCanAnt.equals(CodCanAct) || !CodUsuAnt.equals(CodUsuAct))
                            {
                                sqlUpdt.append("UPDATE Dynamics2021..tbm_jefcan ")
                                       .append("SET co_idcan = ").append(CodCanAct).append(", ")
                                       .append("co_jefcan = ").append(CodUsuAct).append(", ")
                                       .append("fe_ultmod = '").append(strFecUltMod).append("', ")
                                       .append("co_usrmod = ").append(objParSis.getCodigoUsuario()).append(" ")
                                       .append("WHERE co_idreg = ").append(CodCanAnt).append(" ")
                                       .append("AND co_jefcan = ").append(CodUsuAnt).append(";\n");

                                blnRes = true;
                            }
                        }
                        else
                        {
                            sqlIns.append("INSERT INTO Dynamics2021..tbm_jefcan ")
                                  .append("(co_idcan, co_jefcan, st_reg, fe_ing, co_usring) ")
                                  .append("VALUES (")
                                  .append(CodCanAct).append(", ")
                                  .append(CodUsuAct).append(", ")
                                  .append("'A', ")
                                  .append("'").append(strFecUltMod).append("', ")
                                  .append(objParSis.getCodigoUsuario())
                                  .append(");\n");

                            blnRes = true;
                        }

                    }
                    strSQLGlo = sqlUpdt.toString() + "\n" + sqlIns.toString();
                    System.out.println(strSQLGlo);
                    stm.executeUpdate(strSQLGlo);                
                }

                if (blnRes)
                {

    ////                con.commit();
                }
                else
                {
    ////                con.rollback();
                    mostrarMsgInf("<HTML>No hubo cambios para guardar.</HTML>");
                }

                stm.close();
                con.close();
                stm = null;
                con = null;
                datFecAux = null;

                objTblModReg.initRowsState();
                objTblModReg.setDataModelChanged(false);
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            try
            {
                if (con != null)
                    con.rollback();
            }
            catch (Exception ex) { }
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            try
            {
                if (con != null)
                    con.rollback();
            }
            catch (Exception ex) { }
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean tieneValor(Object valor)
    {
        return valor != null && !valor.toString().trim().isEmpty();
    }

    private Integer obtenerEntero(Object valor)
    {
        if (!tieneValor(valor))
        {
            return null;
        }

        return Integer.parseInt(valor.toString().trim());
    }
    
    private java.util.HashSet<String> obtenerRegistrosValidosUsuJefCan()//3
    {
        java.util.HashSet<String> setNuevos = new java.util.HashSet<>();
        java.util.HashSet<String> setExistentes = new java.util.HashSet<>();

        try
        {
            int intNumFil = objTblModUsuJefCan.getRowCount();

            // 🔹 1. EXISTENTES (B)
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                Object objCan = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                Object objUsu = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                if (objCan == null || objUsu == null)
                    continue;

                String codCan = objCan.toString().trim();
                String codUsu = objUsu.toString().trim();

                if (codCan.isEmpty() || codUsu.isEmpty())
                    continue;

                String clave = codCan + "-" + codUsu;

                if ("B".equals(estado))
                {
                    setExistentes.add(clave);
                }
            }

            // 🔹 2. NUEVOS
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                if (!"B".equals(estado))
                {
                    Object objCan = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                    Object objUsu = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                    if (objCan == null || objUsu == null)
                        continue;

                    String codCan = objCan.toString().trim();
                    String codUsu = objUsu.toString().trim();

                    if (codCan.isEmpty() || codUsu.isEmpty())
                        continue;

                    String clave = codCan + "-" + codUsu;

                    // 🔴 duplicado
                    if (setNuevos.contains(clave))
                    {
                        mostrarMsgInf("Duplicado en nuevos: " + clave);

                        // limpiar fila
                        objTblModUsuJefCan.setValueAt(null, i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                        objTblModUsuJefCan.setValueAt(null, i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                        return null;
                    }

                    // 🔴 ya existe
                    if (setExistentes.contains(clave))
                    {
                        mostrarMsgInf("El registro ya existe: " + clave);

                        // limpiar fila
                        objTblModUsuJefCan.setValueAt(null, i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                        objTblModUsuJefCan.setValueAt(null, i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                        return null;
                    }

                    setNuevos.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setNuevos;
    }

    private boolean insertarUsuJefCan(java.util.HashSet<String> setNuevos)//4
    {
        boolean blnRes = false;
        String strSQLIns = "";
        String strFecUltMod = "";

        try
        {
            if (setNuevos == null || setNuevos.isEmpty())
                return false;

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (con != null)
            {
                stm = con.createStatement();
                con.setAutoCommit(false);

                datFecAux = objUti.getFechaServidor(
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos(),
                        objParSis.getQueryFechaHoraBaseDatos()
                );

                strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                for (String clave : setNuevos)
                {
                    String[] partes = clave.split("-");

                    String codCan = partes[0];
                    String codUsu = partes[1];

                    strSQLIns += "INSERT INTO Dynamics2021..tbm_jefcan "
                               + "(co_idcan, co_jefcan, st_reg, fe_ing, co_usring) "
                               + "VALUES (" + codCan + ", " + codUsu + ", 'A', '"
                               + strFecUltMod + "', " + objParSis.getCodigoUsuario() + ");\n";

                    blnRes = true;
                }

                if (blnRes)
                {
                    stm.executeUpdate(strSQLIns);
                    con.commit();
                }
                else
                {
                    con.rollback();
                    return false;
                }

                stm.close();
                con.close();
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        }

        return blnRes;
    }
    
    private java.util.HashSet<String> obtenerRegistrosValidos()//11
    {
        java.util.HashSet<String> setNuevos = new java.util.HashSet<>();
        java.util.HashSet<String> setExistentes = new java.util.HashSet<>();

        try
        {
            int intNumFil = objTblModRegJefCan.getRowCount();

            // 🔹 1. Cargar existentes (estado 'B')
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                Object objCodReg = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_REG);
                Object objCodJef = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_JEF);

                if (objCodReg == null || objCodJef == null)
                    continue;

                String codReg = objCodReg.toString().trim();
                String codJef = objCodJef.toString().trim();

                if (codReg.isEmpty() || codJef.isEmpty())
                    continue;

                String clave = codReg + "-" + codJef;

                if ("B".equals(estado))
                {
                    setExistentes.add(clave);
                }
            }

            // 🔹 2. Validar nuevos
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                if (!"B".equals(estado))
                {
                    Object objCodReg = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_REG);
                    Object objCodJef = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_JEF);

                    if (objCodReg == null || objCodJef == null)
                        continue;

                    String codReg = objCodReg.toString().trim();
                    String codJef = objCodJef.toString().trim();

                    if (codReg.isEmpty() || codJef.isEmpty())
                        continue;

                    String clave = codReg + "-" + codJef;

                    // 🔴 duplicado en nuevos
                    if (setNuevos.contains(clave))
                    {
                        mostrarMsgInf("Duplicado en nuevos: " + clave);
                        return null;
                    }

                    // 🔴 ya existe en BD
                    if (setExistentes.contains(clave))
                    {
                        mostrarMsgInf("El registro ya existe: " + clave);
                        return null;
                    }

                    setNuevos.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setNuevos;
    }
    
    
    private java.util.HashSet<String> obtenerRegistrosChequeadosJefVen()
    {
        java.util.HashSet<String> setChequeados = new java.util.HashSet<String>();

        try
        {
            int intNumFil = objTblModJefVen.getRowCount();

            for (int i = 0; i < intNumFil; i++)
            {
                Object objChk = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_CHK_ANU);
                boolean blnChk = (objChk instanceof Boolean) ? ((Boolean)objChk).booleanValue() : false;

                if (blnChk)
                {
                    Object objCodJef = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_JEF);
                    Object objCodVen = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_VEN);
                    Object objCodCan = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF);

                    if (objCodJef == null || objCodVen == null || objCodCan == null)
                        continue;

                    String codJef = objCodJef.toString().trim();
                    String codVen = objCodVen.toString().trim();
                    String codCan = objCodCan.toString().trim();

                    if (codJef.isEmpty() || codVen.isEmpty() || codCan.isEmpty())
                        continue;

                    String clave = codJef + "-" + codVen + "-" + codCan;
                    setChequeados.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setChequeados;
    }
    
    private java.util.HashSet<String> obtenerRegistrosChequeadosRegJef()
    {
        java.util.HashSet<String> setChequeados = new java.util.HashSet<String>();

        try
        {
            int intNumFil = objTblModRegJefCan.getRowCount();

            for (int i = 0; i < intNumFil; i++)
            {
                Object objChk = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_CHK_ANU);
                boolean blnChk = (objChk instanceof Boolean) ? ((Boolean)objChk).booleanValue() : false;

                if (blnChk)
                {
                    Object objCodReg = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_REG);
                    Object objCodJef = objTblModRegJefCan.getValueAt(i, INT_ARL_TAB_REG_JEF_CAN_COD_JEF);

                    if (objCodReg == null || objCodJef == null)
                        continue;

                    String codReg = objCodReg.toString().trim();
                    String codJef = objCodJef.toString().trim();

                    if (codReg.isEmpty() || codJef.isEmpty() )
                        continue;

                    String clave = codReg + "-" + codJef + "";
                    setChequeados.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setChequeados;
    }
    
    private java.util.HashSet<String> obtenerRegistrosChequeadosJefCan()
    {
        java.util.HashSet<String> setChequeados = new java.util.HashSet<String>();

        try
        {
            int intNumFil = objTblModUsuJefCan.getRowCount();

            for (int i = 0; i < intNumFil; i++)
            {
                Object objChk = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_CHK_ANU);
                boolean blnChk = (objChk instanceof Boolean) ? ((Boolean)objChk).booleanValue() : false;

                if (blnChk)
                {
                    Object objCodCan = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
                    Object objCodUsu = objTblModUsuJefCan.getValueAt(i, INT_ARL_TAB_USU_JEF_CAN_COD_USU);

                    if (objCodCan == null || objCodUsu == null )
                        continue;

                    String codCan = objCodCan.toString().trim();
                    String codUsu = objCodUsu.toString().trim();

                    if (codCan.isEmpty() )
                        continue;

                    String clave = codCan + "-" + codUsu +"";
                    
                    setChequeados.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setChequeados;
    }    

    private java.util.HashSet<String> obtenerRegistrosChequeadosCan()
    {
        java.util.HashSet<String> setChequeados = new java.util.HashSet<String>();

        try
        {
            int intNumFil = objTblModCan.getRowCount();

            for (int i = 0; i < intNumFil; i++)
            {
                Object objChk = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_CHK);
                boolean blnChk = (objChk instanceof Boolean) ? ((Boolean)objChk).booleanValue() : false;

                if (blnChk)
                {
                    Object objCodCan = objTblModCan.getValueAt(i, INT_ARL_TAB_CAN_COD);

                    if (objCodCan == null )
                        continue;

                    String codCan = objCodCan.toString().trim();

                    if (codCan.isEmpty() )
                        continue;

                    String clave = codCan + "-P";
                    
                    setChequeados.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setChequeados;
    }     
    
    private java.util.HashSet<String> obtenerRegistrosChequeadosReg()
    {
        java.util.HashSet<String> setChequeados = new java.util.HashSet<String>();

        try
        {
            int intNumFil = objTblModReg.getRowCount();

            for (int i = 0; i < intNumFil; i++)
            {
                Object objChk = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_CHK);
                boolean blnChk = (objChk instanceof Boolean) ? ((Boolean)objChk).booleanValue() : false;

                if (blnChk)
                {
                    Object objCodReg = objTblModReg.getValueAt(i, INT_ARL_TAB_REG_COD);

                    if (objCodReg == null )
                        continue;

                    String codReg = objCodReg.toString().trim();

                    if (codReg.isEmpty() )
                        continue;

                    String clave = codReg + "-P";
                    
                    setChequeados.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setChequeados;
    }     


    private java.util.HashSet<String> obtenerRegistrosValidosJefVen()//
    {
        java.util.HashSet<String> setNuevos = new java.util.HashSet<>();
        java.util.HashSet<String> setExistentes = new java.util.HashSet<>();

        try
        {
            int intNumFil = objTblModJefVen.getRowCount();

            // 🔹 1. Cargar existentes (estado 'B')
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                Object objCodReg = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_JEF);
                Object objCodJef = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_VEN);
                Object objCodCan = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF);

                if (objCodReg == null || objCodJef == null || objCodCan == null)
                    continue;

                String codReg = objCodReg.toString().trim();
                String codJef = objCodJef.toString().trim();
                String codCan = objCodCan.toString().trim();

                if (codReg.isEmpty() || codJef.isEmpty()|| codCan.isEmpty() )
                    continue;

                String clave = codReg + "-" + codJef+ "-" + codCan;

                if ("B".equals(estado))
                {
                    setExistentes.add(clave);
                }
            }

            // 🔹 2. Validar nuevos
            for (int i = 0; i < intNumFil; i++)
            {
                Object objEstado = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_BLO);
                String estado = (objEstado == null) ? "" : objEstado.toString();

                if (!"B".equals(estado))
                {
                    Object objCodReg = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_JEF);
                    Object objCodJef = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_VEN);
                    Object objCodCan = objTblModJefVen.getValueAt(i, INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF);

                    if (objCodReg == null || objCodJef == null || objCodCan == null)
                        continue;

                    String codReg = objCodReg.toString().trim();
                    String codJef = objCodJef.toString().trim();
                    String codCan = objCodCan.toString().trim();

                    if (codReg.isEmpty() || codJef.isEmpty() || codCan.isEmpty())
                        continue;

                    String clave = codReg + "-" + codJef+ "-" + codCan;

                    // 🔴 duplicado en nuevos
                    if (setNuevos.contains(clave))
                    {
                        mostrarMsgInf("Duplicado en nuevos: " + clave);
                        return null;
                    }

                    // 🔴 ya existe en BD
                    if (setExistentes.contains(clave))
                    {
                        mostrarMsgInf("El registro ya existe: " + clave);
                        return null;
                    }

                    setNuevos.add(clave);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return setNuevos;
    }    

    private boolean insertarRegJefCan(java.util.HashSet<String> setNuevos)//22
    {
        boolean blnRes = false;
        String strSQLIns = "";
        String strFecUltMod = "";

        try
        {
            if (setNuevos == null || setNuevos.isEmpty())
                return false;

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (con != null)
            {
                stm = con.createStatement();
                con.setAutoCommit(false);

                // Fecha servidor
                datFecAux = objUti.getFechaServidor(
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos(),
                        objParSis.getQueryFechaHoraBaseDatos()
                );

                strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                for (String clave : setNuevos)
                {
                    String[] partes = clave.split("-");

                    String codReg = partes[0];
                    String codJef = partes[1];

                    strSQLIns += "INSERT INTO Dynamics2021..tbr_regjefcan "
                               + "(co_idreg, co_jefcan, st_reg, fe_ing, co_usring) "
                               + "VALUES (" + codReg + ", " + codJef + ", 'A', '"
                               + strFecUltMod + "', " + objParSis.getCodigoUsuario() + ");\n";

                    blnRes = true;
                }

                if (blnRes)
                {
                    stm.executeUpdate(strSQLIns);
                    con.commit();
                }
                else
                {
                    con.rollback();
                    return false;
                }

                stm.close();
                con.close();
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        }

        return blnRes;
    }
    
    private boolean insertarJefVend(java.util.HashSet<String> setNuevos)//22
    {
        boolean blnRes = false;
        String strSQLIns = "";
        String strFecUltMod = "";

        try
        {
            if (setNuevos == null || setNuevos.isEmpty())
                return false;

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (con != null)
            {
                stm = con.createStatement();
                con.setAutoCommit(false);

                // Fecha servidor
                datFecAux = objUti.getFechaServidor(
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos(),
                        objParSis.getQueryFechaHoraBaseDatos()
                );

                strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                for (String clave : setNuevos)
                {
                    String[] partes = clave.split("-");

                    String codJef = partes[0];
                    String codVen = partes[1];
                    String codCan = partes[2];

                    strSQLIns += "INSERT INTO Dynamics2021..tbr_canjefvenasi "
                               + "( codcan, usrjefcan, usrven, fe_ing,co_usring,st_Reg) "
                               + "VALUES (" + codCan + ", " + codJef + ", " + codVen + " , '"+ strFecUltMod + "', " + objParSis.getCodigoUsuario() + ",'A');\n";

                    blnRes = true;
                }

                if (blnRes)
                {
                    stm.executeUpdate(strSQLIns);
                    con.commit();
                }
                else
                {
                    con.rollback();
                    return false;
                }

                stm.close();
                con.close();
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        }

        return blnRes;
    }
    
    private boolean ModificarJefVend(java.util.HashSet<String> setChek)//22
    {
            boolean blnRes = false;
            String strSQLIns = "";
            String strFecUltMod = "";

            try
            {
                if (setChek == null || setChek.isEmpty())
                    return false;

                con = DriverManager.getConnection(
                        objParSis.getStringConexion_Dynamics2021(),
                        objParSis.getUsuarioBaseDatos_Dynamics2021(),
                        objParSis.getClaveBaseDatos_Dynamics2021()
                );

                if (con != null)
                {
                    stm = con.createStatement();
                    con.setAutoCommit(false);

                    // Fecha servidor
                    datFecAux = objUti.getFechaServidor(
                            objParSis.getStringConexion(),
                            objParSis.getUsuarioBaseDatos(),
                            objParSis.getClaveBaseDatos(),
                            objParSis.getQueryFechaHoraBaseDatos()
                    );

                    strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                    for (String clave : setChek)
                    {
                        String[] partes = clave.split("-");

                        String codJef = partes[0];
                        String codVen = partes[1];
                        String codCan = partes[2];

                        strSQLIns += "UPDATE Dynamics2021..tbr_canjefvenasi SET st_reg ='I' ,fe_ing ='"+ strFecUltMod + "',co_usring = " + objParSis.getCodigoUsuario() + " "
                                   + "WHERE  codcan = "+ codCan +" and usrjefcan = "+codJef +"  and usrven = "+ codVen +";\n";

                        blnRes = true;
                    }

                    if (blnRes)
                    {
                        stm.executeUpdate(strSQLIns);
                        con.commit();
                    }
                    else
                    {
                        con.rollback();
                        return false;
                    }

                    stm.close();
                    con.close();
                }
            }
            catch (Exception e)
            {
                blnRes = false;
                try { con.rollback(); } catch (Exception ex) {}
                e.printStackTrace();
            }

            return blnRes;
        }   
    
    private boolean ModificarRegJefCan(java.util.HashSet<String> setChek)//22
    {
        boolean blnRes = false;
        String strSQLIns = "";
        String strFecUltMod = "";

        try
        {
            if (setChek == null || setChek.isEmpty())
                return false;

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (con != null)
            {
                stm = con.createStatement();
                con.setAutoCommit(false);

                // Fecha servidor
                datFecAux = objUti.getFechaServidor(
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos(),
                        objParSis.getQueryFechaHoraBaseDatos()
                );

                strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                for (String clave : setChek)
                {
                    String[] partes = clave.split("-");

                    String codReg = partes[0];
                    String codJef = partes[1];

                    strSQLIns += "UPDATE Dynamics2021..tbr_regjefcan SET st_reg ='I' ,fe_ing ='"+ strFecUltMod + "',co_usring = " + objParSis.getCodigoUsuario() + " "
                               + "WHERE  co_idreg = "+ codReg +" and co_jefcan = "+codJef +" ;\n";

                    blnRes = true;
                }

                if (blnRes)
                {
                    stm.executeUpdate(strSQLIns);
                    con.commit();
                }
                else
                {
                    con.rollback();
                    return false;
                }

                stm.close();
                con.close();
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        }

        return blnRes;
    }  
    
    private boolean ModificarCanal(java.util.HashSet<String> setChek)//22
        {
            boolean blnRes = false;
            String strSQLIns = "";
            String strFecUltMod = "";

            try
            {
                if (setChek == null || setChek.isEmpty())
                    return false;

                con = DriverManager.getConnection(
                        objParSis.getStringConexion_Dynamics2021(),
                        objParSis.getUsuarioBaseDatos_Dynamics2021(),
                        objParSis.getClaveBaseDatos_Dynamics2021()
                );

                if (con != null)
                {
                    stm = con.createStatement();
                    con.setAutoCommit(false);

                    // Fecha servidor
                    datFecAux = objUti.getFechaServidor(
                            objParSis.getStringConexion(),
                            objParSis.getUsuarioBaseDatos(),
                            objParSis.getClaveBaseDatos(),
                            objParSis.getQueryFechaHoraBaseDatos()
                    );

                    strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                    for (String clave : setChek)
                    {
                        String[] partes = clave.split("-");

                        String codCan = partes[0];

                        strSQLIns += "UPDATE Dynamics2021..tbm_canasi SET st_reg ='I' ,fe_ing ='"+ strFecUltMod + "',co_usring = " + objParSis.getCodigoUsuario() + " "
                                   + "WHERE  co_id = "+ codCan +";\n";

                        blnRes = true;
                    }

                    if (blnRes)
                    {
                        stm.executeUpdate(strSQLIns);
                        con.commit();
                    }
                    else
                    {
                        con.rollback();
                        return false;
                    }

                    stm.close();
                    con.close();
                }
            }
            catch (Exception e)
            {
                blnRes = false;
                try { con.rollback(); } catch (Exception ex) {}
                e.printStackTrace();
            }

            return blnRes;
        } 

    private boolean ModificarRegion(java.util.HashSet<String> setChek)//22
        {
            boolean blnRes = false;
            String strSQLIns = "";
            String strFecUltMod = "";

            try
            {
                if (setChek == null || setChek.isEmpty())
                    return false;

                con = DriverManager.getConnection(
                        objParSis.getStringConexion_Dynamics2021(),
                        objParSis.getUsuarioBaseDatos_Dynamics2021(),
                        objParSis.getClaveBaseDatos_Dynamics2021()
                );

                if (con != null)
                {
                    stm = con.createStatement();
                    con.setAutoCommit(false);

                    // Fecha servidor
                    datFecAux = objUti.getFechaServidor(
                            objParSis.getStringConexion(),
                            objParSis.getUsuarioBaseDatos(),
                            objParSis.getClaveBaseDatos(),
                            objParSis.getQueryFechaHoraBaseDatos()
                    );

                    strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                    for (String clave : setChek)
                    {
                        String[] partes = clave.split("-");

                        String codCan = partes[0];

                        strSQLIns += "UPDATE Dynamics2021..tbm_canasi SET st_reg ='I' ,fe_ing ='"+ strFecUltMod + "',co_usring = " + objParSis.getCodigoUsuario() + " "
                                   + "WHERE  co_id = "+ codCan +";\n";

                        blnRes = true;
                    }

                    if (blnRes)
                    {
                        stm.executeUpdate(strSQLIns);
                        con.commit();
                    }
                    else
                    {
                        con.rollback();
                        return false;
                    }

                    stm.close();
                    con.close();
                }
            }
            catch (Exception e)
            {
                blnRes = false;
                try { con.rollback(); } catch (Exception ex) {}
                e.printStackTrace();
            }

            return blnRes;
        }     
    
    private boolean ModificarJefCanal(java.util.HashSet<String> setChek)//22
        {
            boolean blnRes = false;
            String strSQLIns = "";
            String strFecUltMod = "";

            try
            {
                if (setChek == null || setChek.isEmpty())
                    return false;

                con = DriverManager.getConnection(
                        objParSis.getStringConexion_Dynamics2021(),
                        objParSis.getUsuarioBaseDatos_Dynamics2021(),
                        objParSis.getClaveBaseDatos_Dynamics2021()
                );

                if (con != null)
                {
                    stm = con.createStatement();
                    con.setAutoCommit(false);

                    // Fecha servidor
                    datFecAux = objUti.getFechaServidor(
                            objParSis.getStringConexion(),
                            objParSis.getUsuarioBaseDatos(),
                            objParSis.getClaveBaseDatos(),
                            objParSis.getQueryFechaHoraBaseDatos()
                    );

                    strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                    for (String clave : setChek)
                    {
                        String[] partes = clave.split("-");

                        String codCan = partes[0];
                        String codUsu = partes[1];

                        strSQLIns += "UPDATE Dynamics2021..tbm_jefcan SET st_reg ='I' ,fe_ing ='"+ strFecUltMod + "',co_usring = " + objParSis.getCodigoUsuario() + " "
                                   + "WHERE  co_idcan = "+ codCan +" and co_jefcan ="+codUsu +";\n";

                        blnRes = true;
                    }

                    if (blnRes)
                    {
                        stm.executeUpdate(strSQLIns);
                        con.commit();
                    }
                    else
                    {
                        con.rollback();
                        return false;
                    }

                    stm.close();
                    con.close();
                }
            }
            catch (Exception e)
            {
                blnRes = false;
                try { con.rollback(); } catch (Exception ex) {}
                e.printStackTrace();
            }

            return blnRes;
        }     

    

    private class ZafThGuarGUI extends Thread
    {
       
 
        public void run()
        {
            if (objTblModReg.getRowCountTrue() > 0) 
            {
                int ne_tip = cmbTip.getSelectedIndex();
                ne_modRe =0;
                ne_modCan =0;
                ne_modCanJef =0;
                ne_modRegJef =0; 
                ne_modCliVen=0;
                ne_modJefVen=1;
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación ?")==0)
                {
                    lblMsgSis.setText("Guardando");

                    indTab = tabFrm.getSelectedIndex(); 
    //////////                if (indTab==0)
    //////////                    ingresarDatosCargadosHist();
                    //<editor-fold defaultstate="collapsed" desc="/*REGION */"> 
                    if (indTab==0)
                    {
//////////                        java.util.HashSet<String> setChe = obtenerRegistrosChequeadosReg();
                        if (ValidacionesRegion())
                            mostrarMsgInf("Se encuentra registros ya ingresados en el proceso de REGIÓN...");
                        else
                            ProcesoRegion();
                        
//////////                        if(setChe == null)
//////////                            mostrarMsgInf("No hay registros para Modificar...");
//////////                        else
//////////                        {
//////////                            ModificarCanal(setChe);
//////////                            mostrarMsgInf("Se realizaron la anulacion de forma exitosa...");
//////////                        }
                        
                        if (tblReg.getRowCount()>0)
                        {
                            tabFrm.setSelectedIndex(0);
                            tblReg.setRowSelectionInterval(0, 0);
                            tblReg.requestFocus();
                        }
                        objThrGUI=null;                        
                        cargarDetRegReg();
                    }
                    //</editor-fold>
                    
                    //<editor-fold defaultstate="collapsed" desc="/*CANAL */"> 
                    if (indTab==1)
                    {
////////                        java.util.HashSet<String> setChe = obtenerRegistrosChequeadosCan();
                        if (ValidacionesCanal())
                        {
                            mostrarMsgInf("Se encuentra registros ya ingresados en el proceso de CANAL..."); 
                            cargarDetRegCan();
                        }
                        else
                            ProcesoCanal();
                        
//////////                        if(setChe == null)
//////////                            mostrarMsgInf("No hay registros para Modificar...");
//////////                        else
//////////                        {
//////////                            ModificarCanal(setChe);
//////////                            mostrarMsgInf("Se realizaron la anulacion de forma exitosa...");
//////////                        } 
                        if (tblCan.getRowCount()>0)
                        {
                            tabFrm.setSelectedIndex(indTab);
                            tblCan.setRowSelectionInterval(0, 0);
                            tblCan.requestFocus();
                        }
                        objThrGUI=null;                         
                        cargarDetRegCan();
                        
                    }    
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="/*CANAL VS VENDEDOR */"> 
                    if (indTab==2)
                    {
                        java.util.HashSet<String> setReg = obtenerRegistrosValidosUsuJefCan();
                        java.util.HashSet<String> setChe = obtenerRegistrosChequeadosJefCan();

                        if (setReg == null)
                            mostrarMsgInf("Existen registros duplicados o ya existentes...");
                        else 
                        {
                            if (setReg.isEmpty())
                                mostrarMsgInf("No hay registros para insertar...");
                            else
                            {
                                if (insertarUsuJefCan(setReg))
                                    mostrarMsgInf("Datos guardados correctamente...");
                                else
                                    mostrarMsgInf("Error al guardar...");
                            }                  
                        }
                        if(setChe == null)
                            mostrarMsgInf("No hay registros para Modificar...");
                        else
                        {
                            if (ModificarJefCanal(setChe))
                                mostrarMsgInf("Se realizaron la anulacion de forma exitosa...");
                        } 
                        if (tblUsuJefCan.getRowCount()>0)
                        {
                            tabFrm.setSelectedIndex(indTab);
                            tblUsuJefCan.setRowSelectionInterval(0, 0);
                            tblUsuJefCan.requestFocus();
                        }
                        objThrGUI=null;                             
                        cargarDetUsuJefCan();

                    } 
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="/*REGION VS JEFE DE CANAL */"> 
                    if (indTab==3)
                    {
                        java.util.HashSet<String> setReg = obtenerRegistrosValidos();
                        java.util.HashSet<String> setChe = obtenerRegistrosChequeadosRegJef();

                        if (setReg == null)
                            mostrarMsgInf("Se encuentran registros duplicados o ya existentes en JEFEREGION...");
                        else 
                        {
                            if (setReg.isEmpty())
                                mostrarMsgInf("No hay registros para insertar...");
                            else
                            {
                                if (insertarRegJefCan(setReg))
                                    mostrarMsgInf("Datos guardados correctamente...");
                                else
                                    mostrarMsgInf("Ocurrió un error al guardar...");
                            }
                        }
                        if(setChe == null)
                            mostrarMsgInf("No hay registros para Modificar...");
                        else
                        {
                            if (ModificarRegJefCan(setChe))
                                mostrarMsgInf("Se realizaron la anulacion de forma exitosa...");
                        }
                        if (tblRegJefCan.getRowCount()>0)
                        {
                            tabFrm.setSelectedIndex(indTab);
                            tblRegJefCan.setRowSelectionInterval(0, 0);
                            tblRegJefCan.requestFocus();
                        }
                        objThrGUI=null;                          
                        cargarDetRegJefCan();
                    } 

                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="/*JEFE DE CANAL VS VENDEDORES */"> 
                    if (indTab==4)
                    {
                        java.util.HashSet<String> setReg = obtenerRegistrosValidosJefVen();
                        java.util.HashSet<String> setChe = obtenerRegistrosChequeadosJefVen();

                        if (setReg == null)
                            mostrarMsgInf("Se encuentran registros duplicados o ya existentes en JEFE CANAL VS VENDEDOR...");
                        else
                        {
                            if (setReg.isEmpty())
                                mostrarMsgInf("No hay registros para insertar...");
                            else
                            {
                                if (insertarJefVend(setReg))
                                    mostrarMsgInf("Datos guardados correctamente...");
                                else
                                    mostrarMsgInf("Ocurrió un error al guardar...");
                            }
                        }
                        if(setChe == null)
                            mostrarMsgInf("No hay registros para Modificar...");
                        else
                        {
                            if (ModificarJefVend(setChe))
                                mostrarMsgInf("Se realizaron la anulacion de forma exitosa...");
                        }                    

                        if (tblRegJefVen.getRowCount()>0)
                        {
                            tabFrm.setSelectedIndex(indTab);
                            tblRegJefVen.setRowSelectionInterval(0, 0);
                            tblRegJefVen.requestFocus();
                        }
                        objThrGUI=null;  
                        cargarDetJefCanVen();
                    }
                //</editor-fold>
                
                    //<editor-fold defaultstate="collapsed" desc="/*ASIGNACION DE CLIENTES */"> 
if (indTab == 5)
{
    boolean blnOk=true;
    

    java.util.HashMap<String, java.util.List<String>> mapNovedades =
            new java.util.LinkedHashMap<String, java.util.List<String>>();

    if (ne_tip == 0)
    {
        // Cliente - Vendedor
        validarDatosClienteVendedor(mapNovedades);
        validarClienteExisteBD(ne_tip, mapNovedades);
        validarVendedorExisteBD(ne_tip, mapNovedades);
    }
    else
    {
        // Local - Cliente - Vendedor
        validarDatosLocalClienteVendedor(mapNovedades);
        validarClienteExisteBD(ne_tip, mapNovedades);
        validarVendedorExisteBD(ne_tip, mapNovedades);
        validarLocalExisteBD(mapNovedades);
    }
    int sinNov = 1;

if (!mapNovedades.isEmpty())
{ 
    mostrarNovedadesValidacion(mapNovedades);
    if (!todosLosRegistrosTienenNovedad())
    {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                null,
                "Se encontraron registros con novedad.\n\n"
                + "¿Desea proceder a ejecutar únicamente los registros que NO tienen novedad?",
                "Confirmar proceso",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );      
        if (opcion != javax.swing.JOptionPane.YES_OPTION)
        {
            mostrarMsgInf("Proceso cancelado por el usuario.");
            objThrGUI = null;
            objThrGuaGUI=null;
            return;
        }

        sinNov = 1;        
        
    }
    else
    {
        mostrarMsgInf("Todos los registros tienen novedades....");
        objThrGUI = null;
        objThrGuaGUI=null;
        return;
        
    }
        




}





    
    if (ejecutarUpdatesProcesados(sinNov))
    {
        if (actualizarVendedorRM00102DesdeCliLoc())
        {
            mostrarMsgInf("Actualización de vendedores finalizada correctamente.");
            mostrarMsgInf("Datos guardados correctamente...");
        }
        else
            mostrarMsgInf("Ocurrió un error al actualizar los vendedores.");
        
        if (sinNov==1)
        {
            eliminarRegistrosEstadoCeroEST(ne_tip);
        }
    }
    else
    {
        mostrarMsgInf("Ocurrió un error al guardar...");
    }

    if (tblcliloc.getRowCount() > 0)
    {
        tabFrm.setSelectedIndex(indTab);
        tblcliloc.setRowSelectionInterval(0, 0);
        tblcliloc.requestFocus();
    }

    objThrGUI = null;
}
                    //</editor-fold>                
                }
                else
                    mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

                objThrGuaGUI=null;
            }
        }
    }
   
    
private boolean actualizarVendedorRM00102DesdeCliLoc()
{
    java.sql.Connection conLoc = null;
    java.sql.Statement stmQry = null;
    java.sql.Statement stmUpd = null;
    java.sql.ResultSet rstLoc = null;

    boolean blnRes = true;

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Consultando diferencias de vendedor RM00102...");

        java.util.ArrayList<String> lstScripts = new java.util.ArrayList<String>();

        conLoc = java.sql.DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conLoc != null)
        {
            conLoc.setAutoCommit(false);

            String strSQL = "";
            strSQL += " SELECT ";
            strSQL += "     R.SLPRSNID AS VENDRM, ";
            strSQL += "     R.CUSTNMBR, ";
            strSQL += "     C.co_ven, ";
            strSQL += "     'UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.RM00102 SET SLPRSNID = ''' ";
            strSQL += "     + CAST(C.co_ven AS VARCHAR(20)) ";
            strSQL += "     + ''', modifdt = CONVERT(DATETIME, CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 23) + '' 00:00:00.000'', 121)' ";
            strSQL += "     + ', dex_row_ts = CURRENT_TIMESTAMP ' ";
            strSQL += "     + ' WHERE CUSTNMBR = ''' ";
            strSQL += "     + LTRIM(RTRIM(R.CUSTNMBR)) ";
            strSQL += "     + ''';' AS ScriptUpdate ";
            strSQL += " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.RM00102 AS R ";
            strSQL += " INNER JOIN ( ";
            strSQL += "     SELECT ";
            strSQL += "         tx_cli, ";
            strSQL += "         CAST(MIN(co_ven) AS VARCHAR(20)) AS co_ven, ";
            strSQL += "         COUNT(DISTINCT co_ven) AS CantVen ";
            strSQL += "     FROM Dynamics2021.dbo.tbr_cliLoc ";
            strSQL += "     WHERE co_emp = 1 ";
            strSQL += "       AND st_tipVen IN (1,2) ";
            strSQL += "     GROUP BY tx_cli ";
            strSQL += "     HAVING COUNT(DISTINCT co_ven) = 1 ";
            strSQL += " ) AS C ";
            strSQL += "     ON LTRIM(RTRIM(R.CUSTNMBR)) = LTRIM(RTRIM(C.tx_cli)) ";
            strSQL += " WHERE LTRIM(RTRIM(R.SLPRSNID)) <> LTRIM(RTRIM(C.co_ven)) ";
            strSQL += " ORDER BY R.CUSTNMBR ";

            stmQry = conLoc.createStatement();
            rstLoc = stmQry.executeQuery(strSQL);

            while (rstLoc.next())
            {
                String scriptUpdate = rstLoc.getString("ScriptUpdate");

                if (scriptUpdate != null && !scriptUpdate.trim().equals(""))
                {
                    lstScripts.add(scriptUpdate.trim());
                }
            }

            rstLoc.close();
            rstLoc = null;

            stmQry.close();
            stmQry = null;

            if (lstScripts.isEmpty())
            {
                conLoc.rollback();

                lblMsgSis.setText("");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);

                mostrarMsgInf("No existen vendedores por actualizar en RM00102.");
                return true;
            }

            lblMsgSis.setText("Ejecutando actualización de vendedores RM00102...");

            stmUpd = conLoc.createStatement();

            int intCanUpd = 0;

            for (int i = 0; i < lstScripts.size(); i++)
            {
                String script = lstScripts.get(i);

                intCanUpd += stmUpd.executeUpdate(script);
            }

            conLoc.commit();

            mostrarMsgInf("Proceso finalizado correctamente. Registros actualizados: " + intCanUpd);
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;

        try
        {
            if (conLoc != null)
                conLoc.rollback();
        }
        catch (Exception ex)
        {
            objUti.mostrarMsgErr_F1(this, ex);
        }

        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;

        try
        {
            if (conLoc != null)
                conLoc.rollback();
        }
        catch (Exception ex)
        {
            objUti.mostrarMsgErr_F1(this, ex);
        }

        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (rstLoc != null)
                rstLoc.close();

            if (stmQry != null)
                stmQry.close();

            if (stmUpd != null)
                stmUpd.close();

            if (conLoc != null)
            {
                conLoc.setAutoCommit(true);
                conLoc.close();
            }

            pgrSis.setIndeterminate(false);
            butCon.setText("Consultar");
            lblMsgSis.setText("");
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    return blnRes;
}

private boolean todosLosRegistrosTienenNovedad()
{
    boolean blnTodosConNovedad = true;
    int intCanReg = 0;

    try
    {
        int opcion = cmbTip.getSelectedIndex();

        for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
        {
            String cliente = "";
            String vendedor = "";
            String local = "";
            String estado = "";

            if (opcion == 0)
            {
                cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI1);
                vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN1);
                estado = obtenerValorCelda(i, INT_ARL_CLI_LOC_EST1);

                // Ignorar filas totalmente vacías
                if (cliente.equals("") && vendedor.equals(""))
                    continue;
            }
            else
            {
                local = obtenerValorCelda(i, INT_ARL_CLI_LOC_LOC2);
                cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI2);
                vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN2);
                estado = obtenerValorCelda(i, INT_ARL_CLI_LOC_EST2);

                // Ignorar filas totalmente vacías
                if (local.equals("") && cliente.equals("") && vendedor.equals(""))
                    continue;
            }

            intCanReg++;

            // Si encuentra uno sin novedad, ya no todos tienen novedad
            if (estado.equals("") || estado.equals("0"))
            {
                blnTodosConNovedad = false;
                break;
            }
        }

        // Si no hay registros válidos, retorna false
        if (intCanReg == 0)
            blnTodosConNovedad = false;
    }
    catch (Exception e)
    {
        blnTodosConNovedad = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnTodosConNovedad;
}

 
    private int eliminarRegistrosEstadoCeroEST(int tipo)
{
    int intCanEli = 0;

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Eliminandos filas...");        
        Object objEst = null;
        for (int i = objTblModCliLoc.getRowCount() - 1; i >= 0; i--)
        {
            if (tipo==0)
                objEst =objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_EST1);
            else
                objEst = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_EST2);
            
            String strEst = (objEst == null) ? "" : objEst.toString().trim();

            if (strEst.equals("0"))
            {
                objTblModCliLoc.removeRow(i);
                intCanEli++;
            }
        }
        pgrSis.setIndeterminate(false);
        butCon.setText("Consultar");
        lblMsgSis.setText("Registros Actualizados...");        

        if (tblcliloc != null)
        {
            tblcliloc.repaint();
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return intCanEli;
}
    
private void agregarNovedad(
        java.util.HashMap<String, java.util.List<String>> mapNovedades,
        String categoria,
        String detalle
)
{
    if (!mapNovedades.containsKey(categoria))
    {
        mapNovedades.put(categoria, new java.util.ArrayList<String>());
    }

    mapNovedades.get(categoria).add(detalle);
}    

private String obtenerValorCelda(int fila, int columna)
{
    Object obj = objTblModCliLoc.getValueAt(fila, columna);
    return (obj == null) ? "" : obj.toString().trim();
}

private void mostrarNovedadesValidacion(
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    StringBuilder mensaje = new StringBuilder();

    mensaje.append("SE ENCONTRARON LAS SIGUIENTES NOVEDADES:\n");
    mensaje.append("========================================\n\n");

    for (java.util.Map.Entry<String, java.util.List<String>> entry : mapNovedades.entrySet())
    {
        mensaje.append(">>> ")
               .append(entry.getKey())
               .append("\n");

        mensaje.append("----------------------------------------\n");

        for (String detalle : entry.getValue())
        {
            mensaje.append("• ")
                   .append(detalle)
                   .append("\n");
        }

        mensaje.append("\n");
    }

    // Cargar novedades en el JTextArea
    jtnovedad.setText(mensaje.toString());

    // Posicionar el cursor al inicio del JTextArea
    jtnovedad.setCaretPosition(0);

    // Cambiar a la pestaña Novedades
    seleccionarPestanaNovedades();
}

private void seleccionarPestanaNovedades()
{
    try
    {
        for (int i = 0; i < tabFrm.getTabCount(); i++)
        {
            String titulo = tabFrm.getTitleAt(i);

            if (titulo != null && titulo.trim().equalsIgnoreCase("Novedades"))
            {
                tabFrm.setSelectedIndex(i);
                break;
            }
        }

        jtnovedad.requestFocus();
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}
    
private boolean validarLocalExisteBD(
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    java.sql.Connection conLoc = null;
    java.sql.PreparedStatement pstLoc = null;
    java.sql.ResultSet rstLoc = null;

    boolean blnRes = true;

    try
    {
        conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021());

        if (conLoc != null)
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Consultando Locales...");

            String strSql = "";
            strSql += " SELECT co_loc ";
            strSql += " FROM Dynamics2021.dbo.tbm_loc1 ";
            strSql += " WHERE co_emp = 1 ";
            strSql += " AND tx_ptovta <> '' ";
            strSql += " AND co_loc = ? ";

            pstLoc = conLoc.prepareStatement(strSql);

            for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
            {
                lblMsgSis.setText("Consultando Locales "+ i);
                String strLocal = obtenerValorCelda(i, INT_ARL_CLI_LOC_LOC2);

                if (strLocal.equals(""))
                    continue;

                int intLocal;

                try
                {
                    intLocal = Integer.parseInt(strLocal);
                }
                catch (NumberFormatException e)
                {
                    agregarNovedad(
                            mapNovedades,
                            "LOCALES_INVALIDOS",
                            "Fila " + (i + 1)
                            + " | Local inválido: " + strLocal
                            + " | Debe ser numérico"
                    );
                    objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST2);      
                    Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR2);
                    String strErr = (objErr == null) ? "" : objErr.toString().trim();
                    if (strErr.equals(""))
                        objTblModCliLoc.setValueAt("LOCAL INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                    else
                        objTblModCliLoc.setValueAt(strErr + " - LOCAL INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                
                    //objTblModCliLoc.setValueAt("LOCAL INVALIDO", i, INT_ARL_CLI_LOC_ERR2);   

                    blnRes = false;
                    continue;
                }

                pstLoc.setInt(1, intLocal);
                rstLoc = pstLoc.executeQuery();

                if (!rstLoc.next())
                {
                    agregarNovedad(
                            mapNovedades,
                            "LOCALES_NO_EXISTEN",
                            "Fila " + (i + 1)
                            + " | Local " + intLocal
                            + " no existe en tbm_loc1 o no tiene punto de venta"
                    );
                    objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST2);                    
                    Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR2);
                    String strErr = (objErr == null) ? "" : objErr.toString().trim();

                    if (strErr.equals(""))
                        objTblModCliLoc.setValueAt("LOCAL INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                    else
                        objTblModCliLoc.setValueAt(strErr + " - LOCAL INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                    blnRes = false;
                }

                rstLoc.close();
                rstLoc = null;
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    finally
    {
        try
        {
            if (rstLoc != null)
                rstLoc.close();

            if (pstLoc != null)
                pstLoc.close();

            if (conLoc != null)
                conLoc.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    return blnRes;
}

private boolean validarVendedorExisteBD(
        int tipo,
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    java.sql.Connection conLoc = null;
    java.sql.PreparedStatement pstLoc = null;
    java.sql.ResultSet rstLoc = null;

    boolean blnRes = true;

    try
    {
        conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021());

        if (conLoc != null)
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Consultando Vendedores...");

            String strSql = "";
            strSql += " SELECT co_usr ";
            strSql += " FROM Dynamics2021.dbo.tbm_usr ";
            strSql += " WHERE co_tipven <> '' ";
            strSql += " AND co_usr = ? ";

            pstLoc = conLoc.prepareStatement(strSql);

            for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
            {
                lblMsgSis.setText("Consultando Vendedores: "+i);
                String vendedor;

                if (tipo == 0)
                    vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN1);
                else
                    vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN2);

                if (vendedor.equals(""))
                    continue;

                int intVendedor;

                try
                {
                    intVendedor = Integer.parseInt(vendedor);
                }
                catch (NumberFormatException e)
                {
                    agregarNovedad(
                            mapNovedades,
                            "VENDEDORES_INVALIDOS",
                            "Fila " + (i + 1)
                            + " | Vendedor inválido: " + vendedor
                            + " | Debe ser numérico"
                    );
                    if (tipo == 0)
                    {
                        objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST1);
                        
                    Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR1);
                    String strErr = (objErr == null) ? "" : objErr.toString().trim();
                    if (strErr.equals(""))
                        objTblModCliLoc.setValueAt("VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR1);
                    else
                        objTblModCliLoc.setValueAt(strErr + " - VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR1);
                    
                    
                    
                        //objTblModCliLoc.setValueAt("VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR1);
                    }
                    else
                    {
                        objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST2);
                        Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR2);
                        String strErr = (objErr == null) ? "" : objErr.toString().trim();
                        if (strErr.equals(""))
                            objTblModCliLoc.setValueAt("VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                        else
                            objTblModCliLoc.setValueAt(strErr + " - VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                    
                        
                        //objTblModCliLoc.setValueAt("VENDEDOR INVALIDO", i, INT_ARL_CLI_LOC_ERR2);
                    }
                        
                    blnRes = false;
                    continue;
                }

                pstLoc.setInt(1, intVendedor);
                rstLoc = pstLoc.executeQuery();

                if (!rstLoc.next())
                {
                    agregarNovedad(
                            mapNovedades,
                            "VENDEDORES_NO_EXISTEN",
                            "Fila " + (i + 1)
                            + " | Vendedor " + vendedor
                            + " no existe en tbm_usr o no tiene co_tipven"
                    );
                    objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST1);
                    
                    Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR1);
                    String strErr = (objErr == null) ? "" : objErr.toString().trim();
                    if (strErr.equals(""))
                        objTblModCliLoc.setValueAt("VENDEDOR NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);
                    else
                        objTblModCliLoc.setValueAt(strErr + " - VENDEDOR NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);
                        
                        
                    //objTblModCliLoc.setValueAt("VENDEDOR NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);
                    blnRes = false;
                }

                rstLoc.close();
                rstLoc = null;
            }
        }
        pgrSis.setIndeterminate(false);
        butCon.setText("Consultar");
        lblMsgSis.setText("");
        
    }
    catch (java.sql.SQLException e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    finally
    {
        try
        {
            if (rstLoc != null)
                rstLoc.close();

            if (pstLoc != null)
                pstLoc.close();

            if (conLoc != null)
                conLoc.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    return blnRes;
}
   
private boolean validarClienteExisteBD(
        int tipo,
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    java.sql.Connection conLoc = null;
    java.sql.PreparedStatement pstLoc = null;
    java.sql.ResultSet rstLoc = null;

    boolean blnRes = true;

    pgrSis.setIndeterminate(true);
    butCon.setText("Detener");
    lblMsgSis.setText("Consultando Clientes...");

    try
    {
        conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021());

        if (conLoc != null)
        {
            
            String strSql = "";
            strSql += " SELECT LTRIM(RTRIM(CUSTNMBR)) AS cliente ";
            strSql += " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.RM00101 ";
            strSql += " WHERE LTRIM(RTRIM(CUSTNMBR)) = ? ";

            pstLoc = conLoc.prepareStatement(strSql);

            for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
            {
                lblMsgSis.setText("Consultando Clientes..." + i);
                String cliente;

                if (tipo == 0)
                    cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI1);
                else
                    cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI2);

                if (cliente.equals(""))
                    continue;

                pstLoc.setString(1, cliente);
                rstLoc = pstLoc.executeQuery();

                if (!rstLoc.next())
                {
                    lblMsgSis.setText("Consultando Clientes: "+i);
                    agregarNovedad(
                            mapNovedades,
                            "CLIENTES_NO_EXISTEN",
                            "Fila " + (i + 1)
                            + " | Cliente " + cliente
                            + " no existe en RM00101"
                    );
                    if (tipo == 0)
                    {
                        objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST1);
                        
                        Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR1);
                        String strErr = (objErr == null) ? "" : objErr.toString().trim();
                        if (strErr.equals(""))
                            objTblModCliLoc.setValueAt("CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);
                        else
                            objTblModCliLoc.setValueAt(strErr + " - CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);

                        
                        //objTblModCliLoc.setValueAt("CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR1);                        
                    }
                    else
                    {
                        objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST2);
                        Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR2);
                        String strErr = (objErr == null) ? "" : objErr.toString().trim();
                        if (strErr.equals(""))
                            objTblModCliLoc.setValueAt("CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR2);
                        else
                            objTblModCliLoc.setValueAt(strErr + " - CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR2);
                        
                        //objTblModCliLoc.setValueAt("CLIENTE NO EXISTE", i, INT_ARL_CLI_LOC_ERR2);                            
                    }
                    blnRes = false;
                    
                }

                rstLoc.close();
                rstLoc = null;
            }
        }
        pgrSis.setIndeterminate(false);
        butCon.setText("Consultar");
        lblMsgSis.setText("");
    }
    catch (java.sql.SQLException e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    finally
    {
        try
        {
            if (rstLoc != null)
                rstLoc.close();

            if (pstLoc != null)
                pstLoc.close();

            if (conLoc != null)
                conLoc.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    return blnRes;
}
private boolean validarDatosLocalClienteVendedor(
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    boolean blnRes = true;

    try
    {
        java.util.HashMap<String, java.util.List<Integer>> mapDuplicados =
                new java.util.HashMap<String, java.util.List<Integer>>();

        java.util.HashMap<String, java.util.List<String>> mapClienteLocal =
                new java.util.HashMap<String, java.util.List<String>>();

        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Validando Cliente vs Vendedor vs Local...");

        for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
        {
            String local = obtenerValorCelda(i, INT_ARL_CLI_LOC_LOC2);
            String cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI2);
            String vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN2);

            // Ignorar fila totalmente vacía.
            if (local.equals("") && cliente.equals("") && vendedor.equals(""))
                continue;

            // Fila incompleta.
            if (local.equals("") || cliente.equals("") || vendedor.equals(""))
            {
                agregarNovedad(
                        mapNovedades,
                        "FILAS_INCOMPLETAS",
                        "Fila " + (i + 1)
                        + " | Local: [" + local + "]"
                        + " | Cliente: [" + cliente + "]"
                        + " | Vendedor: [" + vendedor + "]"
                        + " | Debe ingresar Local, Cliente y Vendedor"
                );
                objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST2);

                Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR2);
                String strErr = (objErr == null) ? "" : objErr.toString().trim();
                if (strErr.equals(""))
                    objTblModCliLoc.setValueAt("FILA INCOMPLETA", i, INT_ARL_CLI_LOC_ERR2);
                else
                    objTblModCliLoc.setValueAt(strErr + " - FILA INCOMPLETA", i, INT_ARL_CLI_LOC_ERR2);
                
                //objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_ERR2);
                blnRes = false;
                continue;
            }

            // Duplicado exacto Local + Cliente + Vendedor.
            String clave = local + "|" + cliente + "|" + vendedor;

            if (!mapDuplicados.containsKey(clave))
            {
                mapDuplicados.put(clave, new java.util.ArrayList<Integer>());
            }

            mapDuplicados.get(clave).add(i + 1);

            // Cliente + Local con varios vendedores.
            String claveClienteLocal = cliente + "|" + local;

            if (!mapClienteLocal.containsKey(claveClienteLocal))
            {
                mapClienteLocal.put(claveClienteLocal, new java.util.ArrayList<String>());
            }

            if (!mapClienteLocal.get(claveClienteLocal).contains(vendedor))
            {
                mapClienteLocal.get(claveClienteLocal).add(vendedor);
            }
        }

        // Revisar duplicados exactos.
        for (java.util.Map.Entry<String, java.util.List<Integer>> entry : mapDuplicados.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                String[] partes = entry.getKey().split("\\|", -1);

                String local = partes.length > 0 ? partes[0] : "";
                String cliente = partes.length > 1 ? partes[1] : "";
                String vendedor = partes.length > 2 ? partes[2] : "";

                agregarNovedad(
                        mapNovedades,
                        "DUPLICADOS",
                        "Local " + local
                        + " | Cliente " + cliente
                        + " | Vendedor " + vendedor
                        + " | Repetido en filas " + entry.getValue().toString()
                );
                blnRes = false;
            }
        }

        // Revisar Cliente + Local con múltiples vendedores.
        for (java.util.Map.Entry<String, java.util.List<String>> entry : mapClienteLocal.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                String[] partes = entry.getKey().split("\\|", -1);

                String cliente = partes.length > 0 ? partes[0] : "";
                String local = partes.length > 1 ? partes[1] : "";

                agregarNovedad(
                        mapNovedades,
                        "CLIENTE_LOCAL_CON_VARIOS_VENDEDORES",
                        "Cliente " + cliente
                        + " | Local " + local
                        + " | Vendedores encontrados " + entry.getValue().toString()
                );

                blnRes = false;
            }
        }
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}

private boolean validarDatosClienteVendedor(
        java.util.HashMap<String, java.util.List<String>> mapNovedades
)
{
    boolean blnRes = true;

    try
    {
        java.util.HashMap<String, java.util.List<Integer>> mapDuplicados =
                new java.util.HashMap<String, java.util.List<Integer>>();

        java.util.HashMap<String, java.util.List<String>> mapClienteVendedor =
                new java.util.HashMap<String, java.util.List<String>>();

        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Validando Cliente vs Vendedor...");

        for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
        {
            String cliente = obtenerValorCelda(i, INT_ARL_CLI_LOC_CLI1);
            String vendedor = obtenerValorCelda(i, INT_ARL_CLI_LOC_VEN1);

            // Ignorar fila totalmente vacía.
            if (cliente.equals("") && vendedor.equals(""))
                continue;

            // Fila incompleta.
            if (cliente.equals("") || vendedor.equals(""))
            {
                agregarNovedad(
                        mapNovedades,
                        "FILAS_INCOMPLETAS",
                        "Fila " + (i + 1)
                        + " | Cliente: [" + cliente + "]"
                        + " | Vendedor: [" + vendedor + "]"
                        + " | Debe ingresar Cliente y Vendedor"
                );
                objTblModCliLoc.setValueAt(Integer.valueOf(1), i, INT_ARL_CLI_LOC_EST1);

                Object objErr = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_ERR1);
                String strErr = (objErr == null) ? "" : objErr.toString().trim();
                if (strErr.equals(""))
                    objTblModCliLoc.setValueAt("FILA INCOMPLETA", i, INT_ARL_CLI_LOC_ERR1);
                else
                    objTblModCliLoc.setValueAt(strErr + " - FILA INCOMPLETA", i, INT_ARL_CLI_LOC_ERR1);
                        
                //objTblModCliLoc.setValueAt("FILA INCOMPLETA", i, INT_ARL_CLI_LOC_ERR1);

                blnRes = false;
                continue;
            }

            // Duplicado exacto Cliente + Vendedor.
            String clave = cliente + "|" + vendedor;

            if (!mapDuplicados.containsKey(clave))
            {
                mapDuplicados.put(clave, new java.util.ArrayList<Integer>());
            }

            mapDuplicados.get(clave).add(i + 1);

            // Cliente con varios vendedores.
            if (!mapClienteVendedor.containsKey(cliente))
            {
                mapClienteVendedor.put(cliente, new java.util.ArrayList<String>());
            }

            if (!mapClienteVendedor.get(cliente).contains(vendedor))
            {
                mapClienteVendedor.get(cliente).add(vendedor);
            }
        }

        // Revisar duplicados exactos.
        for (java.util.Map.Entry<String, java.util.List<Integer>> entry : mapDuplicados.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                String[] partes = entry.getKey().split("\\|", -1);

                String cliente = partes.length > 0 ? partes[0] : "";
                String vendedor = partes.length > 1 ? partes[1] : "";

                agregarNovedad(
                        mapNovedades,
                        "DUPLICADOS",
                        "Cliente " + cliente
                        + " | Vendedor " + vendedor
                        + " | Repetido en filas " + entry.getValue().toString()
                );

                blnRes = false;
            }
        }

        // Revisar cliente con múltiples vendedores.
        for (java.util.Map.Entry<String, java.util.List<String>> entry : mapClienteVendedor.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                agregarNovedad(
                        mapNovedades,
                        "CLIENTE_CON_VARIOS_VENDEDORES",
                        "Cliente " + entry.getKey()
                        + " | Vendedores encontrados " + entry.getValue().toString()
                );

                blnRes = false;
            }
        }
        pgrSis.setIndeterminate(false);
        butCon.setText("Consultar");
        lblMsgSis.setText("");
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}

    private class RegCliLoc
    {
        String local = "";
        String cliente = "";
        String vendedor = "";
        boolean existeCliente = false;
        String sqlUpdate = "";
        String sqlUpdateOfi = "";
    }
    
private java.util.ArrayList<RegCliLoc> obtenerRegistrosDesdeTabla(int soloSinNovedad)
{
    java.util.ArrayList<RegCliLoc> lstReg = new java.util.ArrayList<RegCliLoc>();

    int opcion = cmbTip.getSelectedIndex();

    for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
    {
        RegCliLoc reg = new RegCliLoc();
        String estado = "";

        if (opcion == 0)
        {
            Object objCli = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_CLI1);
            Object objVen = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_VEN1);
            Object objEst = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_EST1);

            reg.cliente = (objCli == null ? "" : objCli.toString().trim());
            reg.vendedor = (objVen == null ? "" : objVen.toString().trim());
            estado = (objEst == null ? "" : objEst.toString().trim());
            
        }
        else
        {
            Object objLoc = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_LOC2);
            Object objCli = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_CLI2);
            Object objVen = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_VEN2);
            Object objEst = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_EST2);

            reg.local = (objLoc == null ? "" : objLoc.toString().trim());
            reg.cliente = (objCli == null ? "" : objCli.toString().trim());
            reg.vendedor = (objVen == null ? "" : objVen.toString().trim());
            estado = (objEst == null ? "" : objEst.toString().trim());
        }

        // Ignorar registros sin cliente.
        if (reg.cliente.equals(""))
            continue;

        /*
         * soloSinNovedad = 1:
         * Solo carga registros cuyo estado sea 0 o vacío.
         *
         * soloSinNovedad = 0:
         * Carga todos los registros.
         */
        if (soloSinNovedad == 1)
        {
            if (!estado.equals("") && !estado.equals("0"))
                continue;
        }

        lstReg.add(reg);
    }

    return lstReg;
}    

////////    private java.util.ArrayList<RegCliLoc> obtenerRegistrosDesdeTabla()
////////    {
////////        java.util.ArrayList<RegCliLoc> lstReg = new java.util.ArrayList<RegCliLoc>();
////////        int opcion = cmbTip.getSelectedIndex();
////////        //int opcion = 0;
////////
////////        for (int i = 0; i < objTblModCliLoc.getRowCount(); i++)
////////        {
////////            RegCliLoc reg = new RegCliLoc();
////////
////////            if (opcion == 0)
////////            {
////////                Object objCli = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_CLI1);
////////                Object objVen = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_VEN1);
////////
////////                reg.cliente = (objCli == null ? "" : objCli.toString().trim());
////////                reg.vendedor = (objVen == null ? "" : objVen.toString().trim());
////////            }
////////            else
////////            {
////////                Object objLoc = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_LOC2);
////////                Object objCli = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_CLI2);
////////                Object objVen = objTblModCliLoc.getValueAt(i, INT_ARL_CLI_LOC_VEN2);
////////
////////                reg.local = (objLoc == null ? "" : objLoc.toString().trim());
////////                reg.cliente = (objCli == null ? "" : objCli.toString().trim());
////////                reg.vendedor = (objVen == null ? "" : objVen.toString().trim());
////////            }
////////
////////            if (!reg.cliente.equals(""))
////////                lstReg.add(reg);
////////        }
////////
////////        return lstReg;
////////    }

    private java.util.ArrayList<RegCliLoc> procesarRegistrosCliLoc(int sinNov)
    {
        java.util.ArrayList<RegCliLoc> lstReg = new java.util.ArrayList<RegCliLoc>();

        java.sql.Connection con = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rst = null;

        try
        {
            lstReg = obtenerRegistrosDesdeTabla(sinNov);

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021());

            String strSQL = "SELECT tx_cli FROM Dynamics2021..tbr_cliloc WHERE  tx_cli = ?";
            pst = con.prepareStatement(strSQL);

            int opcion = cmbTip.getSelectedIndex();
            ///int opcion = 0;

            for (int i = 0; i < lstReg.size(); i++)
            {
                RegCliLoc reg = lstReg.get(i);

                pst.setString(1, reg.cliente);
                rst = pst.executeQuery();

                if (rst.next())
                {
                    reg.existeCliente = true;

                    if (opcion == 0)
                    {
                        if (reg.vendedor.equals("9"))
                        {
                            reg.sqlUpdate = "UPDATE tbr_cliloc "
                                      + "SET st_tipven =1 ,co_ven = " + reg.vendedor + " "
                                      + "WHERE co_emp =1 and st_tipven =1 and  tx_cli = '" + reg.cliente.replace("'", "''") + "'";
                            reg.sqlUpdateOfi = "UPDATE tbr_cliloc "        
                                      + "SET st_cligencom ='O' ,st_tipven =1 "
                                      + "WHERE co_emp =1 and st_tipven =1 and co_ven = " + reg.vendedor + " and  tx_cli = '" + reg.cliente.replace("'", "''") + "'";                            
                        }
                        else
                        {
                            reg.sqlUpdate = "UPDATE tbr_cliloc "
                                      + "SET st_tipven =1 ,co_ven = " + reg.vendedor + " "
                                      + "WHERE co_emp =1 and st_tipven =1 and  tx_cli = '" + reg.cliente.replace("'", "''") + "'";                            
                        }
                    }
                    else
                    {
                        if (reg.vendedor.equals("9"))
                        {
                            reg.sqlUpdate = "UPDATE tbr_cliloc "
                                      //+ "SET co_loc = " + reg.local + ", "
                                      + "SET co_ven = " + reg.vendedor + ", "
                                      + "    st_tipven = 1 "
                                      + "WHERE co_emp =1 and st_tipven =1 and  tx_cli = '" + reg.cliente.replace("'", "''") + "' and co_loc = " + reg.local + "";     
                            reg.sqlUpdateOfi = "UPDATE tbr_cliloc "        
                                      + "SET st_cligencom ='O' ,st_tipven =1 "
                                      + "WHERE co_emp =1 and st_tipven =1 and  co_loc = "+reg.local +" and co_ven = " + reg.vendedor + " and  tx_cli = '" + reg.cliente.replace("'", "''") + "'";                                 
                        }
                        else
                        {
                            reg.sqlUpdate = "UPDATE tbr_cliloc "
                                     // + "SET co_loc = " + reg.local + ", "
                                      + "SET    co_ven = " + reg.vendedor + ", "
                                      + "    st_tipven = 1 "
                                      + "WHERE co_emp =1 and st_tipven =1 and  co_loc = "+ reg.local +" and tx_cli = '" + reg.cliente.replace("'", "''") + "'";                             
                        }

                    }
                }
                else
                {
                    reg.existeCliente = false;
                    reg.sqlUpdate = "";
                }

                if (rst != null)
                {
                    rst.close();
                    rst = null;
                }
            }

            if (pst != null)
                pst.close();
            if (con != null)
                con.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

        return lstReg;
    }

    private boolean ejecutarUpdatesProcesados(int sinNov)
    {
        boolean blnRes = true;

        java.sql.Connection con = null;
        java.sql.Statement stm = null;

        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");
            
            java.util.ArrayList<RegCliLoc> lstReg = procesarRegistrosCliLoc(sinNov);

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021());

            stm = con.createStatement();

            int intAct = 0;
            int intNoExiste = 0;

            for (int i = 0; i < lstReg.size(); i++)
            {
                lblMsgSis.setText("Guardando datos:" + i);
                RegCliLoc reg = lstReg.get(i);

                if (reg.existeCliente && !reg.sqlUpdate.equals(""))
                {
                    stm.executeUpdate(reg.sqlUpdate);
                    if (reg.existeCliente && !reg.sqlUpdateOfi.equals(""))
                        stm.executeUpdate(reg.sqlUpdateOfi);
                    
                    intAct++;
                }
                else
                {
                    intNoExiste++;
                }
            }

            mostrarMsgInf("Proceso terminado."
                    + "\nActualizados: " + intAct
                    + "\nNo encontrados: " + intNoExiste);
            pgrSis.setIndeterminate(false);
            butCon.setText("Consultar");
            lblMsgSis.setText("Datos Actualizados");            

            if (stm != null)
                stm.close();
            if (con != null)
                con.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }

        return blnRes;
    }

    private void revisarRegistrosProcesados(int sinNov)
    {
        java.util.ArrayList<RegCliLoc> lstReg = procesarRegistrosCliLoc(sinNov);

        String strMen = "";

        for (int i = 0; i < lstReg.size(); i++)
        {
            RegCliLoc reg = lstReg.get(i);

            strMen += "Cliente: " + reg.cliente
                    + " | Existe: " + reg.existeCliente
                    + " | SQL: " + reg.sqlUpdate + "\n";
        }

        mostrarMsgInf(strMen);
    }

    
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
                    strSQLGP="";
                    strSQLGP+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
                    rstLoc = stmLoc.executeQuery(strSQLGP);
                    if(rstLoc.next()){
                        System.out.println("Version " + version);
                        System.out.println("tx_ver " + rstLoc.getString("tx_ver"));

                        if(version.equals(rstLoc.getString("tx_ver"))){
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

    
    /** Creates new form ZafVen76 */
    public ZafVen76(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
           
            

////            if(!isVersionActualizada()){
////                mostrarMsgInf("Su versión del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                dispose();
////            }
////            else
////            {
                initComponents();
                            if (!configurarFrm()) 
                exitForm();
////            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    } 
    
        /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti = new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);  
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);    
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + version);
            lblTit.setText(strAux);
            
            
           objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
             //ConfigurarCombos
            
        

            
            
            //Configurar los JTables.
            configurarTblDatReg();
            configurarTblDatCan();
            configurarTblDatRegJefCan();   
            configurarTblDatCanJefCan();
            configurarTblDatJefVen();
           /// tabFrm.removeTabAt(2);
           
            cargarDetRegReg();
            cargarDetUsuJefCan();
            cargarDetRegJefCan();
            cargarDetRegCan();
            cargarDetJefCanVen();
            
        int index = tabFrm.indexOfTab("Carga-3");
        if (index == -1) {
            //Component tab2 = tabFrm.getComponentAt(1);
            

            // Si luego quieres volver a mostrarla:
            // jTabbedPane1.addTab("CARGA-2", tab);
        }
        
////////        int index3 = tabFrm.indexOfTab("Region");
////////        if (index3 != -1) {
////////            Component tab3 = tabFrm.getComponentAt(index3);
////////            tabFrm.removeTabAt(index3);
////////
////////            // Si luego quieres volver a mostrarla:
////////            // jTabbedPane1.addTab("CARGA-2", tab);
////////        }        

            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    




 /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblTipoMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModTipo.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
////////            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblCarga1.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_SELECCION)
////////            {
////////                if (blnMarTodChkTblEmp){
////////                    for (i=0; i<intNumFil; i++){
////////                        objTblModTipo.setChecked(true, i, INT_TBL_DAT_CHK_SELECCION);
////////                    }
////////                    blnMarTodChkTblEmp=false;
////////                }
////////                else
////////                {
////////                    for (i=0; i<intNumFil; i++)
////////                    {
////////                        objTblModTipo.setChecked(false, i, INT_TBL_DAT_CHK_SELECCION);
////////                    }
////////                    blnMarTodChkTblEmp=true;
////////                }
////////                ocultaColumnasPorFiltro();                
////////            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    

////////private class ZafMouMotAdaTipo extends java.awt.event.MouseMotionAdapter//chiqui
////////    {
////////        public void mouseMoved(java.awt.event.MouseEvent evt)
////////        {
////////            int intCol=tblCarga1.columnAtPoint(evt.getPoint());
////////            String strMsg="";
////////            switch (intCol)
////////            {
////////                case INT_TBL_DAT_LIN2:
////////                    strMsg="";
////////                    break;
////////                case INT_TBL_DAT_CHK_SELECCION:
////////                    strMsg="Selección";
////////                    break;
////////                case INT_TBL_DAT_NOMBRE:
////////                    strMsg="Nombre del Tipo de Atributo";
////////                    break;
////////                default:
////////                    strMsg="";
////////                    break;
////////            }
////////            tblCarga1.getTableHeader().setToolTipText(strMsg);
////////        }
////////    }
    

    private boolean esFilaBloqueadaReg(int intFil)
    {
        boolean blnRes = false;
        try
        {
            if (intFil >= 0)
            {
                Object objBlo = objTblModReg.getValueAt(intFil, INT_ARL_TAB_REG_BLO);
                String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                if (strBlo.equals("B"))
                    blnRes = true;
            }
        }
        catch (Exception e)
        {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean configurarTblDatReg()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatReg=new Vector();
            vecCabReg=new Vector();
            vecCabReg.clear();

            vecCabReg.add(INT_ARL_TAB_REG_LIN,"");
            vecCabReg.add(INT_ARL_TAB_REG_COD,"Cod.");
            vecCabReg.add(INT_ARL_TAB_REG_DES,"Descripción");
            vecCabReg.add(INT_ARL_TAB_REG_EST,"Estado");
            vecCabReg.add(INT_ARL_TAB_REG_BLO,"Bloqueada");
            vecCabReg.add(INT_ARL_TAB_REG_CHK,"✔");

            objTblModReg=new ZafTblMod();
            objTblModReg.setHeader(vecCabReg);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblReg.setModel(objTblModReg);

            //Configurar JTable: Editor de la tabla.
            objTblEdiReg=new ZafTblEdi(tblReg);

            //Configurar JTable: Establecer tipo de selección.
            tblReg.setRowSelectionAllowed(true);
            tblReg.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumReg=new ZafColNumerada(tblReg,INT_ARL_TAB_REG_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuReg=new ZafTblPopMnu(tblReg);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblReg.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_REG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_COD).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_REG_DES).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_REG_EST).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_REG_BLO).setPreferredWidth(60);
            tcmAux.getColumn(INT_ARL_TAB_REG_CHK).setPreferredWidth(40);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblReg.getTableHeader().setReorderingAllowed(false);

            // Editor base para campos texto
            objTblCelEdiTxtReg = new ZafTblCelEdiTxt(tblReg);
            tcmAux.getColumn(INT_ARL_TAB_REG_COD).setCellEditor(objTblCelEdiTxtReg);
            tcmAux.getColumn(INT_ARL_TAB_REG_DES).setCellEditor(objTblCelEdiTxtReg);
            tcmAux.getColumn(INT_ARL_TAB_REG_EST).setCellEditor(objTblCelEdiTxtReg);

            // Bloquear edición de texto cuando la fila está bloqueada
            objTblCelEdiTxtReg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblReg.getSelectedRow();

                    if (esFilaBloqueadaReg(intFil))
                    {
                        objTblCelEdiTxtReg.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtReg.setCancelarEdicion(false);
                    }
                }
            });

            // Renderer para columnas texto
            objTblCelRenLblReg=new ZafTblCelRenLbl();
            objTblCelRenLblReg.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_ARL_TAB_REG_COD).setCellRenderer(objTblCelRenLblReg);

            // CHECK: usar el mismo modelo que configurarTblDatCanJefCan
            javax.swing.JCheckBox chkAnuReg = new javax.swing.JCheckBox();
            chkAnuReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            tcmAux.getColumn(INT_ARL_TAB_REG_CHK).setCellEditor(
                new javax.swing.DefaultCellEditor(chkAnuReg)
                {
                    @Override
                    public boolean isCellEditable(java.util.EventObject e)
                    {
                        int intFil = tblReg.getSelectedRow();

                        if (e instanceof java.awt.event.MouseEvent)
                        {
                            java.awt.event.MouseEvent me = (java.awt.event.MouseEvent)e;
                            intFil = tblReg.rowAtPoint(me.getPoint());
                        }

                        if (intFil < 0)
                            return false;

                        Object objBlo = objTblModReg.getValueAt(intFil, INT_ARL_TAB_REG_BLO);
                        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                        // SOLO permitir editar el check cuando BLO = "B"
                        if (!strBlo.equals("B"))
                        {
                            mostrarMsgInf("Solo puede seleccionar registros bloqueados.");
                            return false;
                        }

                        return super.isCellEditable(e);
                    }
                }
            );

            tcmAux.getColumn(INT_ARL_TAB_REG_CHK).setCellRenderer(tblReg.getDefaultRenderer(Boolean.class));

            // Columnas editables
            vecAuxReg=new Vector();
            vecAuxReg.add("" + INT_ARL_TAB_REG_COD);
            vecAuxReg.add("" + INT_ARL_TAB_REG_DES);
            //vecAuxReg.add("" + INT_ARL_TAB_REG_EST);
            vecAuxReg.add("" + INT_ARL_TAB_REG_CHK);
            objTblModReg.setColumnasEditables(vecAuxReg);

            // Columnas ocultas
            ArrayList arlColHid=new ArrayList();
            //arlColHid.add("" + INT_ARL_TAB_REG_EST);
            arlColHid.add("" + INT_ARL_TAB_REG_COD);
            arlColHid.add("" + INT_ARL_TAB_REG_BLO);
            objTblModReg.setSystemHiddenColumns(arlColHid, tblReg);

            objTblModReg.setModoOperacion(objTblModReg.INT_TBL_INS);
            objTblModReg.insertRow();

            int intUltFil = objTblModReg.getRowCountTrue() - 1;
            objTblModReg.setValueAt(Boolean.FALSE, intUltFil, INT_ARL_TAB_REG_CHK);
            objTblModReg.setValueAt("", intUltFil, INT_ARL_TAB_REG_BLO);

            // Detectar cambios
            objTblModLisReg=new ZafTblModLis();
            objTblModReg.addTableModelListener(objTblModLisReg);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean esFilaBloqueadaCan(int intFil)
    {
        boolean blnRes = false;
        try
        {
            if (intFil >= 0)
            {
                Object objBlo = objTblModCan.getValueAt(intFil, INT_ARL_TAB_CAN_BLO);
                String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                if (strBlo.equals("B"))
                    blnRes = true;
            }
        }
        catch (Exception e)
        {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean configurarTblDatCan()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatCan=new Vector();
            vecCabCan=new Vector();
            vecCabCan.clear();

            vecCabCan.add(INT_ARL_TAB_CAN_LIN,"");
            vecCabCan.add(INT_ARL_TAB_CAN_COD,"Cod.");
            vecCabCan.add(INT_ARL_TAB_CAN_DES,"Descripción");
            vecCabCan.add(INT_ARL_TAB_CAN_EST,"Estado");
            vecCabCan.add(INT_ARL_TAB_CAN_BLO,"Bloqueada");
            vecCabCan.add(INT_ARL_TAB_CAN_CHK,"✔");

            objTblModCan=new ZafTblMod();
            objTblModCan.setHeader(vecCabCan);

            tblCan.setModel(objTblModCan);

            objTblEdiCan=new ZafTblEdi(tblCan);

            tblCan.setRowSelectionAllowed(true);
            tblCan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            objColNumCan=new ZafColNumerada(tblCan,INT_ARL_TAB_CAN_LIN);

            objTblPopMnuCan=new ZafTblPopMnu(tblCan);

            tblCan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            javax.swing.table.TableColumnModel tcmAux=tblCan.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_CAN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_CAN_COD).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_CAN_DES).setPreferredWidth(200);
            tcmAux.getColumn(INT_ARL_TAB_CAN_EST).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_CAN_BLO).setPreferredWidth(60);
            tcmAux.getColumn(INT_ARL_TAB_CAN_CHK).setPreferredWidth(40);

            tblCan.getTableHeader().setReorderingAllowed(false);

            // Editor base para texto
            objTblCelEdiTxtCan = new ZafTblCelEdiTxt(tblCan);
            tcmAux.getColumn(INT_ARL_TAB_CAN_COD).setCellEditor(objTblCelEdiTxtCan);
            tcmAux.getColumn(INT_ARL_TAB_CAN_DES).setCellEditor(objTblCelEdiTxtCan);
            tcmAux.getColumn(INT_ARL_TAB_CAN_EST).setCellEditor(objTblCelEdiTxtCan);

            // Bloquear edición de texto si la fila está bloqueada
            objTblCelEdiTxtCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblCan.getSelectedRow();

                    if (esFilaBloqueadaCan(intFil))
                    {
                        objTblCelEdiTxtCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtCan.setCancelarEdicion(false);
                    }
                }
            });

            objTblCelRenLblCan=new ZafTblCelRenLbl();
            objTblCelRenLblCan.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_ARL_TAB_CAN_COD).setCellRenderer(objTblCelRenLblCan);

            // CHECK: mismo modelo que en la otra tabla
            javax.swing.JCheckBox chkAnuCan = new javax.swing.JCheckBox();
            chkAnuCan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            tcmAux.getColumn(INT_ARL_TAB_CAN_CHK).setCellEditor(
                new javax.swing.DefaultCellEditor(chkAnuCan)
                {
                    @Override
                    public boolean isCellEditable(java.util.EventObject e)
                    {
                        int intFil = tblCan.getSelectedRow();

                        if (e instanceof java.awt.event.MouseEvent)
                        {
                            java.awt.event.MouseEvent me = (java.awt.event.MouseEvent)e;
                            intFil = tblCan.rowAtPoint(me.getPoint());
                        }

                        if (intFil < 0)
                            return false;

                        Object objBlo = objTblModCan.getValueAt(intFil, INT_ARL_TAB_CAN_BLO);
                        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                        // Solo permitir check si BLO = "B"
                        if (!strBlo.equals("B"))
                        {
                            mostrarMsgInf("Solo puede seleccionar registros bloqueados.");
                            return false;
                        }

                        return super.isCellEditable(e);
                    }
                }
            );

            tcmAux.getColumn(INT_ARL_TAB_CAN_CHK).setCellRenderer(tblCan.getDefaultRenderer(Boolean.class));

            // Columnas editables
            vecAuxCan=new Vector();
            vecAuxCan.add("" + INT_ARL_TAB_CAN_COD);
            vecAuxCan.add("" + INT_ARL_TAB_CAN_DES);
////            vecAuxCan.add("" + INT_ARL_TAB_CAN_EST);
            vecAuxCan.add("" + INT_ARL_TAB_CAN_CHK);
            objTblModCan.setColumnasEditables(vecAuxCan);

            // Columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add("" + INT_ARL_TAB_CAN_COD);
            arlColHid.add("" + INT_ARL_TAB_CAN_BLO);
            objTblModCan.setSystemHiddenColumns(arlColHid, tblCan);

            objTblModCan.setModoOperacion(objTblModCan.INT_TBL_INS);
            objTblModCan.insertRow();

            int intUltFil = objTblModCan.getRowCountTrue() - 1;
            objTblModCan.setValueAt(Boolean.FALSE, intUltFil, INT_ARL_TAB_CAN_CHK);
            objTblModCan.setValueAt("", intUltFil, INT_ARL_TAB_CAN_BLO);

            objTblModLisCan=new ZafTblModLis();
            objTblModCan.addTableModelListener(objTblModLisCan);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean ConfigurarUsuariosJefeCanal()
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_usr");
            arlCam.add("tx_nom");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Usu.");
            arlAli.add("Des.Usu.");
            arlAli.add("Nom.Usu.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             //strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,jefcan.co_idcan,can.tx_canasi \n";
////////             strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,usr.tx_nom \n";             
////////             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id) \n";             
////////             strSQL+=" WHERE  jefcan.st_reg ='A' "; 
             strSQL+="SELECT distinct(co_jefcan) as co_usr, usr.tx_usr,usr.tx_nom \n";             
             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
             strSQL+=" WHERE  jefcan.st_reg ='A' "; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConUsuJefCan=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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
    
    private boolean ConfigurarRegion()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_idreg");
            arlCam.add("tx_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Reg.");
            arlAli.add("Des.Reg.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("90");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT co_idreg,tx_reg";
             strSQL+=" FROM  Dynamics2021..tbm_reg ";
            // strSQL+=" WHERE  st_reg ='A'"; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConReg=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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
    
    private boolean ConfigurarUsuarios()
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_usr");
            arlCam.add("tx_nom");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Usu.");
            arlAli.add("Des.Usu.");
            arlAli.add("Nom.Usu.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             //strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,jefcan.co_idcan,can.tx_canasi \n";
////////             strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,usr.tx_nom \n";             
////////             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id) \n";             
////////             strSQL+=" WHERE  jefcan.st_reg ='A' "; 
             strSQL+="SELECT  co_usr, tx_usr,tx_nom \n";             
             strSQL+=" FROM  Dynamics2021..tbm_usr \n";
             //strSQL+=" WHERE  st_reg ='A'  and co_tipven !=''"; 
             strSQL+=" WHERE  co_tipven !=''"; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConUsu=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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
    
    private boolean ConfigurarUsuarios2()
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_usr");
            arlCam.add("tx_nom");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Usu.");
            arlAli.add("Des.Usu.");
            arlAli.add("Nom.Usu.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             //strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,jefcan.co_idcan,can.tx_canasi \n";
////////             strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,usr.tx_nom \n";             
////////             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id) \n";             
////////             strSQL+=" WHERE  jefcan.st_reg ='A' "; 
             strSQL+="SELECT  co_usr, tx_usr,tx_nom \n";             
             strSQL+=" FROM  Dynamics2021..tbm_usr \n";
             //strSQL+=" WHERE  st_reg ='A'  and co_tipven !=''"; 
             strSQL+=" WHERE  co_tipven !=''"; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConUsuJefVen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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
    
    private boolean ConfigurarCanal()
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_id");
            arlCam.add("tx_canasi");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Can.");
            arlAli.add("Des.Can.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("200");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             //strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,jefcan.co_idcan,can.tx_canasi \n";
////////             strSQL+="SELECT jefcan.co_jefcan AS co_usr,usr.tx_usr ,usr.tx_nom \n";             
////////             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
////////             strSQL+="       INNER JOIN Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id) \n";             
////////             strSQL+=" WHERE  jefcan.st_reg ='A' "; 
             strSQL+="SELECT  co_id, tx_canasi \n";             
             strSQL+=" FROM  Dynamics2021..tbm_canasi \n";
            // strSQL+=" WHERE  st_reg ='A'  "; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConCan=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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
    
    private boolean ConfigurarJefeCanal()
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_usr");
            arlCam.add("tx_nom");
            arlCam.add("co_idcan");
            arlCam.add("tx_canasi");            
            
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Usu.");
            arlAli.add("Des.Usu.");
            arlAli.add("Nom.Usu.");
            arlAli.add("Cod.Can.");
            arlAli.add("Des.Can.");            
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            arlAncCol.add("10");
            arlAncCol.add("300");            
            
              
            //Armar la sentencia SQL.
             String  strSQL="";

             strSQL+="SELECT distinct(co_jefcan) as co_usr, usr.tx_usr,usr.tx_nom,jefcan.co_idcan,can.tx_canasi \n";             
             strSQL+=" FROM  Dynamics2021..tbm_jefcan as jefcan \n";
             strSQL+="       INNER JOIN Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr) \n";
             strSQL+="       INNER JOIN Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id)   \n";             
            // strSQL+=" WHERE  jefcan.st_reg ='A' "; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConJefVen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
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

    private boolean esFilaBloqueadaUsuJefCan(int intFil)//listo
    {
        if (intFil < 0)
            return false;

        Object objBlo = objTblModUsuJefCan.getValueAt(intFil, INT_ARL_TAB_USU_JEF_CAN_BLO);
        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

        return strBlo.equals("B");
    }

    private boolean configurarTblDatCanJefCan()//listo
    {
        boolean blnRes=true;
        try
        {
            ConfigurarUsuarios();
            ConfigurarCanal();

            //Configurar JTable: Establecer el modelo.
            vecDatUsuJefCan=new Vector();    //Almacena los datos
            vecCabUsuJefCan=new Vector();    //Almacena las cabeceras
            vecCabUsuJefCan.clear();

            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_LIN,"");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_CAN,"Cod.");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_DES_CAN,"Des.Canal");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN,"..");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_USU,"Cod.");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_DES_USU,"Des.Usuario");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BTN_USU,"..");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT,"Cod.Ant.Can");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT,"Cod.Ant.Usu");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU,"✔");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BLO,"Blo");
            vecCabUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_EST,"Est.");

            objTblModUsuJefCan=new ZafTblMod();
            objTblModUsuJefCan.setHeader(vecCabUsuJefCan);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblUsuJefCan.setModel(objTblModUsuJefCan);

            //Configurar JTable: Editor de la tabla.
            objTblEdiUsuJefCan=new ZafTblEdi(tblUsuJefCan);

            //Configurar JTable: Establecer tipo de selección.
            tblUsuJefCan.setRowSelectionAllowed(true);
            tblUsuJefCan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumUsuJefCan=new ZafColNumerada(tblUsuJefCan,INT_ARL_TAB_USU_JEF_CAN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuUsuJefCan=new ZafTblPopMnu(tblUsuJefCan);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblUsuJefCan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblUsuJefCan.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_CAN).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_DES_CAN).setPreferredWidth(200);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_USU).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_DES_USU).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_USU).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU).setPreferredWidth(40);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BLO).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_EST).setPreferredWidth(15);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblUsuJefCan.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN).setResizable(false);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_USU).setResizable(false);

            objTblCelEdiTxtRegJefCan = new ZafTblCelEdiTxt(tblUsuJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_CAN).setCellEditor(objTblCelEdiTxtRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_DES_CAN).setCellEditor(objTblCelEdiTxtRegJefCan);

            objTblCelRenLblUsuJefCan=new ZafTblCelRenLbl();
            objTblCelRenLblUsuJefCan.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_CAN).setCellRenderer(objTblCelRenLblUsuJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_USU).setCellRenderer(objTblCelRenLblUsuJefCan);
            objTblCelRenLblUsuJefCan=null;

            //Configurar JTable: Establecer columnas editables.
            vecAuxUsuJefCan=new Vector();
            //vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_COD_CAN);
            vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_DES_CAN);
            vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_BTN_CAN);
            //vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_COD_USU);
            vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_DES_USU);
            vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_BTN_USU);
            vecAuxUsuJefCan.add("" + INT_ARL_TAB_USU_JEF_CAN_CHK_ANU);

            objTblModUsuJefCan.setColumnasEditables(vecAuxUsuJefCan);
            vecAuxUsuJefCan=null;

            //botones agregados, hacer una columna de botones
            objTblCelRenButUsuJefCan=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN).setCellRenderer(objTblCelRenButUsuJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_USU).setCellRenderer(objTblCelRenButUsuJefCan);
            objTblCelRenButUsuJefCan=null;

            // Editor base para campos texto directos
            objTblCelEdiTxtUsuJefCan = new ZafTblCelEdiTxt(tblUsuJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_CAN).setCellEditor(objTblCelEdiTxtUsuJefCan);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_COD_USU).setCellEditor(objTblCelEdiTxtUsuJefCan);

            objTblCelEdiTxtUsuJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiTxtUsuJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtUsuJefCan.setCancelarEdicion(false);
                    }
                }
            });

            //////////////USUARIO
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_ARL_TAB_USU_JEF_CAN_COD_USU;
            intColTbl[1]=INT_ARL_TAB_USU_JEF_CAN_DES_USU;

            objTblCelEdiButVcoUsu=new ZafTblCelEdiButVcoGP(tblUsuJefCan, objVenConUsu, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_USU).setCellEditor(objTblCelEdiButVcoUsu);
            objTblCelEdiButVcoUsu.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiButVcoUsu.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoUsu.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiButVcoUsu.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoUsu.setCancelarEdicion(false);
                    }
               }
            });

            objTblCelEdiTxtVcoUsu=new ZafTblCelEdiTxtVcoGP(tblUsuJefCan, objVenConUsu, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_DES_USU).setCellEditor(objTblCelEdiTxtVcoUsu);
            objTblCelEdiTxtVcoUsu.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoUsu.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsu.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoUsu.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsu.setCancelarEdicion(false);
                    }

                    System.out.println("aqui la busqueda::...");
                    objVenConUsu.setCampoBusqueda(1);
                    objVenConUsu.setCriterio1(11);
                }
            });

            //CANAL
            int intColVenC[]=new int[2];
            intColVenC[0]=1;
            intColVenC[1]=2;
            int intColTblC[]=new int[2];
            intColTblC[0]=INT_ARL_TAB_USU_JEF_CAN_COD_CAN;
            intColTblC[1]=INT_ARL_TAB_USU_JEF_CAN_DES_CAN;

            objTblCelEdiButVcoCanJefCan=new ZafTblCelEdiButVcoGP(tblUsuJefCan, objVenConCan, intColVenC, intColTblC);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN).setCellEditor(objTblCelEdiButVcoCanJefCan);
            objTblCelEdiButVcoCanJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiButVcoCanJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoCanJefCan.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiButVcoCanJefCan.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoCanJefCan.setCancelarEdicion(false);
                    }
               }
            });

            objTblCelEdiTxtVcoCanJefCan=new ZafTblCelEdiTxtVcoGP(tblUsuJefCan, objVenConCan, intColVenC, intColTblC);
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_DES_CAN).setCellEditor(objTblCelEdiTxtVcoCanJefCan);
            objTblCelEdiTxtVcoCanJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoCanJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoCanJefCan.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblUsuJefCan.getSelectedRow();

                    if (esFilaBloqueadaUsuJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoCanJefCan.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoCanJefCan.setCancelarEdicion(false);
                    }

                    System.out.println("aqui la busqueda::...");
                    objVenConCan.setCampoBusqueda(1);
                    objVenConCan.setCriterio1(11);
                }
            });
            

           // CHECK: mismo modelo que en la otra tabla
            javax.swing.JCheckBox chkAnuCan = new javax.swing.JCheckBox();
            chkAnuCan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU ).setCellEditor(
                new javax.swing.DefaultCellEditor(chkAnuCan)
                {
                    @Override
                    public boolean isCellEditable(java.util.EventObject e)
                    {
                        int intFil = tblUsuJefCan.getSelectedRow();

                        if (e instanceof java.awt.event.MouseEvent)
                        {
                            java.awt.event.MouseEvent me = (java.awt.event.MouseEvent)e;
                            intFil = tblUsuJefCan.rowAtPoint(me.getPoint());
                        }

                        if (intFil < 0)
                            return false;

                        Object objBlo = objTblModUsuJefCan.getValueAt(intFil, INT_ARL_TAB_USU_JEF_CAN_BLO);
                        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                        // Solo permitir check si BLO = "B"
                        if (!strBlo.equals("B"))
                        {
                            mostrarMsgInf("Solo puede seleccionar registros bloqueados.");
                            return false;
                        }

                        return super.isCellEditable(e);
                    }
                }
            );
            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU)
            .setCellRenderer(tblUsuJefCan.getDefaultRenderer(Boolean.class));
            
            

////////            // CHECK: este SIEMPRE editable, incluso si BLO = "B"
////////            javax.swing.JCheckBox chkAnu = new javax.swing.JCheckBox();
////////            chkAnu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
////////            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU).setCellEditor(new javax.swing.DefaultCellEditor(chkAnu));
////////            tcmAux.getColumn(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU).setCellRenderer(tblUsuJefCan.getDefaultRenderer(Boolean.class));

            objTblModUsuJefCan.setModoOperacion(objTblModUsuJefCan.INT_TBL_INS);
            objTblModUsuJefCan.insertRow();

            int intUltFil = objTblModUsuJefCan.getRowCountTrue() - 1;
            objTblModUsuJefCan.setValueAt(Boolean.FALSE, intUltFil, INT_ARL_TAB_USU_JEF_CAN_CHK_ANU);
            objTblModUsuJefCan.setValueAt("", intUltFil, INT_ARL_TAB_USU_JEF_CAN_BLO);
            
            // Columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add("" + INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT);
            arlColHid.add("" + INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT);
            arlColHid.add("" + INT_ARL_TAB_USU_JEF_CAN_BLO);        
            objTblModUsuJefCan.setSystemHiddenColumns(arlColHid, tblUsuJefCan);        
            

            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLisUsuJefCan=new ZafTblModLis();
            objTblModUsuJefCan.addTableModelListener(objTblModLisUsuJefCan);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean esFilaBloqueadaRegJefCan(int intFil)
    {
        if (intFil < 0)
            return false;

        Object objBlo = objTblModRegJefCan.getValueAt(intFil, INT_ARL_TAB_REG_JEF_CAN_BLO);
        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

        return strBlo.equals("B");
    }   

    private boolean configurarTblDatRegJefCan()
    {
        boolean blnRes=true;
        try
        {
            ConfigurarUsuariosJefeCanal();
            ConfigurarRegion();

            //Configurar JTable: Establecer el modelo.
            vecDatRegJefCan=new Vector();    //Almacena los datos
            vecCabRegJefCan=new Vector();    //Almacena las cabeceras
            vecCabRegJefCan.clear();

            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_LIN,"");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_REG,"Cod.");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_DES_REG,"Des.Región");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BTN_REG,"..");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_JEF,"Cod.");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_DES_JEF,"Usu.Jefe");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF,"..");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT,"..");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT,"..");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU,"✔");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BLO,"Blo");
            vecCabRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_EST,"Est");

            objTblModRegJefCan=new ZafTblMod();
            objTblModRegJefCan.setHeader(vecCabRegJefCan);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblRegJefCan.setModel(objTblModRegJefCan);

            //Configurar JTable: Editor de la tabla.
            objTblEdiRegJefCan=new ZafTblEdi(tblRegJefCan);

            //Configurar JTable: Establecer tipo de selección.
            tblRegJefCan.setRowSelectionAllowed(true);
            tblRegJefCan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumRegJefCan=new ZafColNumerada(tblRegJefCan,INT_ARL_TAB_REG_JEF_CAN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuRegJefCan=new ZafTblPopMnu(tblRegJefCan);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRegJefCan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRegJefCan.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_REG).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_DES_REG).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_REG).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_JEF).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_DES_JEF).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU).setPreferredWidth(40);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BLO).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_EST).setPreferredWidth(15);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRegJefCan.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_REG).setResizable(false);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF).setResizable(false);

            objTblCelEdiTxtRegJefCan = new ZafTblCelEdiTxt(tblRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_REG).setCellEditor(objTblCelEdiTxtRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_JEF).setCellEditor(objTblCelEdiTxtRegJefCan);

            objTblCelRenLblRegJefCan=new ZafTblCelRenLbl();
            objTblCelRenLblRegJefCan.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_REG).setCellRenderer(objTblCelRenLblRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_JEF).setCellRenderer(objTblCelRenLblRegJefCan);
            objTblCelRenLblRegJefCan=null;

            //Configurar JTable: Establecer columnas editables.
            vecAuxRegJefCan=new Vector();
//////            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_COD_REG);
            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_DES_REG);
            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_BTN_REG);
//////            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_COD_JEF);
            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_DES_JEF);
            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_BTN_JEF);
            vecAuxRegJefCan.add("" + INT_ARL_TAB_REG_JEF_CAN_CHK_ANU);

            objTblModRegJefCan.setColumnasEditables(vecAuxRegJefCan);
            vecAuxRegJefCan=null;

            //botones agregados, hacer una columna de botones
            objTblCelRenButRegJefCan=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_REG).setCellRenderer(objTblCelRenButRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF).setCellRenderer(objTblCelRenButRegJefCan);
            objTblCelRenButRegJefCan=null;

            // Editor base para campos texto directos
            objTblCelEdiTxtRegJefCan = new ZafTblCelEdiTxt(tblRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_REG).setCellEditor(objTblCelEdiTxtRegJefCan);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_COD_JEF).setCellEditor(objTblCelEdiTxtRegJefCan);

            objTblCelEdiTxtRegJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiTxtRegJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtRegJefCan.setCancelarEdicion(false);
                    }
                }
            });

            //////////////REGION
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_ARL_TAB_REG_JEF_CAN_COD_REG;
            intColTbl[1]=INT_ARL_TAB_REG_JEF_CAN_DES_REG;

            objTblCelEdiButVcoReg=new ZafTblCelEdiButVcoGP(tblRegJefCan, objVenConReg, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_REG).setCellEditor(objTblCelEdiButVcoReg);
            objTblCelEdiButVcoReg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiButVcoReg.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoReg.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiButVcoReg.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoReg.setCancelarEdicion(false);
                    }
                }
            });

            objTblCelEdiTxtVcoReg=new ZafTblCelEdiTxtVcoGP(tblRegJefCan, objVenConReg, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_DES_REG).setCellEditor(objTblCelEdiTxtVcoReg);
            objTblCelEdiTxtVcoReg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoReg.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoReg.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoReg.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoReg.setCancelarEdicion(false);
                    }

                    System.out.println("aqui la busqueda::...");
                    objVenConReg.setCampoBusqueda(1);
                    objVenConReg.setCriterio1(11);
                }
            });

            //////////////USUARIO JEFE
            int intColVenU[]=new int[2];
            intColVenU[0]=1;
            intColVenU[1]=2;
            int intColTblU[]=new int[2];
            intColTblU[0]=INT_ARL_TAB_REG_JEF_CAN_COD_JEF;
            intColTblU[1]=INT_ARL_TAB_REG_JEF_CAN_DES_JEF;

            objTblCelEdiButVcoUsuJefCan=new ZafTblCelEdiButVcoGP(tblRegJefCan, objVenConUsuJefCan, intColVenU, intColTblU);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF).setCellEditor(objTblCelEdiButVcoUsuJefCan);
            objTblCelEdiButVcoUsuJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiButVcoUsuJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoUsuJefCan.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiButVcoUsuJefCan.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoUsuJefCan.setCancelarEdicion(false);
                    }
                }
            });

            objTblCelEdiTxtVcoUsuJefCan=new ZafTblCelEdiTxtVcoGP(tblRegJefCan, objVenConUsuJefCan, intColVenU, intColTblU);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_DES_JEF).setCellEditor(objTblCelEdiTxtVcoUsuJefCan);
            objTblCelEdiTxtVcoUsuJefCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoUsuJefCan.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsuJefCan.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefCan.getSelectedRow();

                    if (esFilaBloqueadaRegJefCan(intFil))
                    {
                        objTblCelEdiTxtVcoUsuJefCan.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsuJefCan.setCancelarEdicion(false);
                    }

                    System.out.println("aqui la busqueda::...");
                    objVenConUsuJefCan.setCampoBusqueda(1);
                    objVenConUsuJefCan.setCriterio1(11);
                }
            });
            
            javax.swing.JCheckBox chkAnuCan = new javax.swing.JCheckBox();
            chkAnuCan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU).setCellEditor(
                new javax.swing.DefaultCellEditor(chkAnuCan)
                {
                    @Override
                    public boolean isCellEditable(java.util.EventObject e)
                    {
                        int intFil = tblRegJefCan.getSelectedRow();

                        if (e instanceof java.awt.event.MouseEvent)
                        {
                            java.awt.event.MouseEvent me = (java.awt.event.MouseEvent)e;
                            intFil = tblRegJefCan.rowAtPoint(me.getPoint());
                        }

                        if (intFil < 0)
                            return false;

                        Object objBlo = objTblModRegJefCan.getValueAt(intFil, INT_ARL_TAB_REG_JEF_CAN_BLO);
                        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                        if (!strBlo.equals("B"))
                        {
                            mostrarMsgInf("Solo puede seleccionar registros bloqueados.");
                            return false;
                        }

                        return super.isCellEditable(e);
                    }
                }
            );

            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU)
                  .setCellRenderer(tblRegJefCan.getDefaultRenderer(Boolean.class));



////////            // CHECK: este SIEMPRE editable, incluso si BLO = "B"
////////            javax.swing.JCheckBox chkAnu = new javax.swing.JCheckBox();
////////            chkAnu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
////////            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU).setCellEditor(new javax.swing.DefaultCellEditor(chkAnu));
////////            tcmAux.getColumn(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU).setCellRenderer(tblRegJefCan.getDefaultRenderer(Boolean.class));

            objTblModRegJefCan.setModoOperacion(objTblModRegJefCan.INT_TBL_INS);
            objTblModRegJefCan.insertRow();

            int intUltFil = objTblModRegJefCan.getRowCountTrue() - 1;
            objTblModRegJefCan.setValueAt(Boolean.FALSE, intUltFil, INT_ARL_TAB_REG_JEF_CAN_CHK_ANU);
            objTblModRegJefCan.setValueAt("", intUltFil, INT_ARL_TAB_REG_JEF_CAN_BLO);

            // Columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add("" + INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT);
            arlColHid.add("" + INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT);
            arlColHid.add("" + INT_ARL_TAB_REG_JEF_CAN_BLO);        
            objTblModRegJefCan.setSystemHiddenColumns(arlColHid, tblRegJefCan);        

            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLisRegJefCan=new ZafTblModLis();
            objTblModRegJefCan.addTableModelListener(objTblModLisRegJefCan);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean esFilaBloqueadaJefVen(int intFil)
    {
        if (intFil < 0)
            return false;

        Object objBlo = objTblModJefVen.getValueAt(intFil, INT_ARL_TAB_REG_JEFVEN_BLO);
        String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

        return strBlo.equals("B");
    }   

    private boolean configurarTblDatJefVen()
    {
        boolean blnRes=true;
        try
        {
            ConfigurarJefeCanal();
            ConfigurarUsuarios2();

            //Configurar JTable: Establecer el modelo.
            vecDatJefVen=new Vector();    //Almacena los datos
            vecCabJefVen=new Vector();    //Almacena las cabeceras
            vecCabJefVen.clear();

            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_LIN,"");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_JEF,"Cod.");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_JEF,"DesCor.Jefe");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF,"DesLar.Jefe");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF,"Cod.Can");            
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_CAN_JEF,"Des.Can");              
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_BTN_JEF,"..");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_VEN,"Cod.");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_VEN,"Usuario");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_BTN_VEN,"..");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_JEF_ANT,"..");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_VEN_ANT,"..");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_CAN_ANT,"..");            
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_CHK_ANU,"✔");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_BLO,"Blo");
            vecCabJefVen.add(INT_ARL_TAB_REG_JEFVEN_EST,"Est.");
            

 
            

            objTblModJefVen=new ZafTblMod();
            objTblModJefVen.setHeader(vecCabJefVen);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblRegJefVen.setModel(objTblModJefVen);

            //Configurar JTable: Editor de la tabla.
            objTblEdiJefVen=new ZafTblEdi(tblRegJefVen);

            //Configurar JTable: Establecer tipo de selección.
            tblRegJefVen.setRowSelectionAllowed(true);
            tblRegJefVen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNumJefVen=new ZafColNumerada(tblRegJefVen,INT_ARL_TAB_REG_JEFVEN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuJefVen=new ZafTblPopMnu(tblRegJefVen);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRegJefVen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRegJefVen.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_JEF).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_JEF).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_CAN_JEF).setPreferredWidth(250);            
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_JEF).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_VEN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_VEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_VEN).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_JEF_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_VEN_ANT).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_CAN_ANT).setPreferredWidth(15);            
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_CHK_ANU).setPreferredWidth(40);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BLO).setPreferredWidth(15);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_EST).setPreferredWidth(15);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRegJefVen.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_JEF).setResizable(false);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_VEN).setResizable(false);

            objTblCelEdiTxtJefVen = new ZafTblCelEdiTxt(tblRegJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_JEF).setCellEditor(objTblCelEdiTxtJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_VEN).setCellEditor(objTblCelEdiTxtJefVen);

            objTblCelRenLblJefVen=new ZafTblCelRenLbl();
            objTblCelRenLblJefVen.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_JEF).setCellRenderer(objTblCelRenLblJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_VEN).setCellRenderer(objTblCelRenLblJefVen);
            objTblCelRenLblJefVen=null;

            //Configurar JTable: Establecer columnas editables.
            vecAuxJefVen=new Vector();
            //vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_COD_JEF);
            vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_DES_JEF);
            vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_BTN_JEF);
            //vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_COD_VEN);
            vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_DES_VEN);
            vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_BTN_VEN);
            vecAuxJefVen.add("" + INT_ARL_TAB_REG_JEFVEN_CHK_ANU);

            objTblModJefVen.setColumnasEditables(vecAuxJefVen);
            vecAuxJefVen=null;

            //botones agregados, hacer una columna de botones
            objTblCelRenButJefVen=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_JEF).setCellRenderer(objTblCelRenButJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_VEN).setCellRenderer(objTblCelRenButJefVen);
            objTblCelRenButJefVen=null;

            // Editor base para campos texto directos
            objTblCelEdiTxtJefVen = new ZafTblCelEdiTxt(tblRegJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_JEF).setCellEditor(objTblCelEdiTxtJefVen);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_COD_VEN).setCellEditor(objTblCelEdiTxtJefVen);

            objTblCelEdiTxtJefVen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiTxtJefVen.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtJefVen.setCancelarEdicion(false);
                    }
                }
            });

            //////////////REGION
            int intColVen[]=new int[5];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;  
            intColVen[4]=5;                        
            int intColTbl[]=new int[5];
            intColTbl[0]=INT_ARL_TAB_REG_JEFVEN_COD_JEF;
            intColTbl[1]=INT_ARL_TAB_REG_JEFVEN_DES_JEF;
            intColTbl[2]=INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF;
            intColTbl[3]=INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF;
            intColTbl[4]=INT_ARL_TAB_REG_JEFVEN_DES_CAN_JEF;            

            objTblCelEdiButVcoJefVen=new ZafTblCelEdiButVcoGP(tblRegJefVen, objVenConJefVen, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_JEF).setCellEditor(objTblCelEdiButVcoJefVen);
            objTblCelEdiButVcoJefVen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiButVcoJefVen.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoJefVen.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiButVcoJefVen.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoJefVen.setCancelarEdicion(false);
                    }
                }
            });

            
            
            objTblCelEdiTxtVcoJefVen=new ZafTblCelEdiTxtVcoGP(tblRegJefVen, objVenConJefVen, intColVen, intColTbl);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_JEF).setCellEditor(objTblCelEdiTxtVcoJefVen);
            objTblCelEdiTxtVcoJefVen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiTxtVcoJefVen.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoJefVen.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiTxtVcoJefVen.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoJefVen.setCancelarEdicion(false);
                    }

                    System.out.println("aqui la busqueda::...");
                    objVenConJefVen.setCampoBusqueda(1);
                    objVenConJefVen.setCriterio1(11);
                }
            });

            //////////////USUARIO JEFE
            int intColVenU[]=new int[2];
            intColVenU[0]=1;
            intColVenU[1]=2;
            int intColTblU[]=new int[2];
            intColTblU[0]=INT_ARL_TAB_REG_JEFVEN_COD_VEN;
            intColTblU[1]=INT_ARL_TAB_REG_JEFVEN_DES_VEN;

            objTblCelEdiButVcoUsuJefVen=new ZafTblCelEdiButVcoGP(tblRegJefVen, objVenConUsuJefVen, intColVenU, intColTblU);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_BTN_VEN).setCellEditor(objTblCelEdiButVcoUsuJefVen);
            objTblCelEdiButVcoUsuJefVen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiButVcoUsuJefVen.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiButVcoUsuJefVen.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiButVcoUsuJefVen.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiButVcoUsuJefVen.setCancelarEdicion(false);
                    }
                }
            });

            objTblCelEdiTxtVcoUsuJefVen=new ZafTblCelEdiTxtVcoGP(tblRegJefVen, objVenConUsuJefVen, intColVenU, intColTblU);
            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_DES_VEN).setCellEditor(objTblCelEdiTxtVcoUsuJefVen);
            objTblCelEdiTxtVcoUsuJefVen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiTxtVcoUsuJefVen.setCancelarEdicion(true);
                        mostrarMsgInf("No se pueden modificar los registros cargados. Solo puede editar el check de anulación.");
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsuJefVen.setCancelarEdicion(false);
                    }
                }

                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblRegJefVen.getSelectedRow();

                    if (esFilaBloqueadaJefVen(intFil))
                    {
                        objTblCelEdiTxtVcoUsuJefVen.setCancelarEdicion(true);
                        return;
                    }
                    else
                    {
                        objTblCelEdiTxtVcoUsuJefVen.setCancelarEdicion(false);
                    }
 
                    System.out.println("aqui la busqueda::...");
                    objVenConUsuJefVen.setCampoBusqueda(1);
                    objVenConUsuJefVen.setCriterio1(11);
                }
            });
            

    javax.swing.JCheckBox chkAnuCan = new javax.swing.JCheckBox();
    chkAnuCan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_CHK_ANU).setCellEditor(
        new javax.swing.DefaultCellEditor(chkAnuCan)
        {
            @Override
            public boolean isCellEditable(java.util.EventObject e)
            {
                int intFil = tblRegJefVen.getSelectedRow();

                if (e instanceof java.awt.event.MouseEvent)
                {
                    java.awt.event.MouseEvent me = (java.awt.event.MouseEvent)e;
                    intFil = tblRegJefVen.rowAtPoint(me.getPoint());
                }

                if (intFil < 0)
                    return false;

                Object objBlo = objTblModJefVen.getValueAt(intFil, INT_ARL_TAB_REG_JEFVEN_BLO);
                String strBlo = (objBlo == null ? "" : objBlo.toString().trim());

                if (!strBlo.equals("B"))
                {
                    mostrarMsgInf("Solo puede seleccionar registros bloqueados.");
                    return false;
                }

                return super.isCellEditable(e);
            }
        }
    );

    tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_CHK_ANU)
          .setCellRenderer(tblRegJefVen.getDefaultRenderer(Boolean.class));

            

////            // CHECK: este SIEMPRE editable, incluso si BLO = "B"
////            javax.swing.JCheckBox chkAnu = new javax.swing.JCheckBox();
////            chkAnu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
////            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_CHK_ANU).setCellEditor(new javax.swing.DefaultCellEditor(chkAnu));
////            tcmAux.getColumn(INT_ARL_TAB_REG_JEFVEN_CHK_ANU).setCellRenderer(tblRegJefVen.getDefaultRenderer(Boolean.class));

            objTblModJefVen.setModoOperacion(objTblModJefVen.INT_TBL_INS);
            objTblModJefVen.insertRow();

            int intUltFil = objTblModJefVen.getRowCountTrue() - 1;
            objTblModJefVen.setValueAt(Boolean.FALSE, intUltFil, INT_ARL_TAB_REG_JEFVEN_CHK_ANU);
            objTblModJefVen.setValueAt("", intUltFil, INT_ARL_TAB_REG_JEFVEN_BLO);

            // Columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_COD_JEF_ANT);
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF);
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF);
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_COD_VEN_ANT);
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_COD_CAN_ANT);
            arlColHid.add("" + INT_ARL_TAB_REG_JEFVEN_BLO);        
            objTblModJefVen.setSystemHiddenColumns(arlColHid, tblRegJefVen);        

            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLisJefVen=new ZafTblModLis();
            objTblModJefVen.addTableModelListener(objTblModLisJefVen);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat1 = new javax.swing.JPanel();
        panDet1 = new javax.swing.JPanel();
        spnDet1 = new javax.swing.JScrollPane();
        tblReg = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat2 = new javax.swing.JPanel();
        panDet2 = new javax.swing.JPanel();
        spnDet2 = new javax.swing.JScrollPane();
        tblCan = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat7 = new javax.swing.JPanel();
        spnDet7 = new javax.swing.JScrollPane();
        tblUsuJefCan = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat3 = new javax.swing.JPanel();
        spnDet3 = new javax.swing.JScrollPane();
        tblRegJefCan = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat9 = new javax.swing.JPanel();
        spnDet8 = new javax.swing.JScrollPane();
        tblRegJefVen = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat8 = new javax.swing.JPanel();
        cmbTip = new javax.swing.JComboBox<>();
        spnDet9 = new javax.swing.JScrollPane();
        tblcliloc = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtnovedad = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCarExc = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        panFrm1 = new javax.swing.JPanel();
        lblTit1 = new javax.swing.JLabel();
        tabFrm1 = new javax.swing.JTabbedPane();
        panDat4 = new javax.swing.JPanel();
        panDet3 = new javax.swing.JPanel();
        spnDet4 = new javax.swing.JScrollPane();
        tblReg1 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat5 = new javax.swing.JPanel();
        panDet4 = new javax.swing.JPanel();
        spnDet5 = new javax.swing.JScrollPane();
        tblCan1 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat6 = new javax.swing.JPanel();
        spnDet6 = new javax.swing.JScrollPane();
        tblCar4 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar1 = new javax.swing.JPanel();
        panBot1 = new javax.swing.JPanel();
        butCon1 = new javax.swing.JButton();
        butCarExc1 = new javax.swing.JButton();
        butGua1 = new javax.swing.JButton();
        butCer1 = new javax.swing.JButton();
        panBarEst1 = new javax.swing.JPanel();
        lblMsgSis1 = new javax.swing.JLabel();
        panPrgSis1 = new javax.swing.JPanel();
        pgrSis1 = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Actualizacion Masiva de items.....");
        lblTit.setToolTipText("");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat1.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat1.setLayout(new java.awt.BorderLayout());

        panDet1.setName(""); // NOI18N
        panDet1.setPreferredSize(new java.awt.Dimension(610, 180));
        panDet1.setLayout(new java.awt.BorderLayout());

        tblReg.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet1.setViewportView(tblReg);

        panDet1.add(spnDet1, java.awt.BorderLayout.CENTER);

        panDat1.add(panDet1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Región", panDat1);

        panDat2.setLayout(new java.awt.BorderLayout());

        panDet2.setLayout(new java.awt.BorderLayout());

        tblCan.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet2.setViewportView(tblCan);

        panDet2.add(spnDet2, java.awt.BorderLayout.CENTER);

        panDat2.add(panDet2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Canal", panDat2);

        panDat7.setLayout(new java.awt.BorderLayout());

        tblUsuJefCan.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet7.setViewportView(tblUsuJefCan);

        panDat7.add(spnDet7, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Jefe Canal", panDat7);

        panDat3.setLayout(new java.awt.BorderLayout());

        tblRegJefCan.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet3.setViewportView(tblRegJefCan);

        panDat3.add(spnDet3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Región vs Jefe Canal", panDat3);

        panDat9.setLayout(new java.awt.BorderLayout());

        tblRegJefVen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet8.setViewportView(tblRegJefVen);

        panDat9.add(spnDet8, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Jefe Canal vs Vendedor", panDat9);

        panDat8.setLayout(new java.awt.BorderLayout());

        cmbTip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cliente - Vendedor", "Cliente - Local - Vendedor" }));
        cmbTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipActionPerformed(evt);
            }
        });
        panDat8.add(cmbTip, java.awt.BorderLayout.PAGE_START);

        tblcliloc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet9.setViewportView(tblcliloc);

        panDat8.add(spnDet9, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cliente/Vendedor/Local", panDat8);

        panDat10.setLayout(new java.awt.BorderLayout());

        jtnovedad.setColumns(20);
        jtnovedad.setRows(5);
        jScrollPane1.setViewportView(jtnovedad);

        panDat10.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Novedades", panDat10);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(110, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCarExc.setText("Cargar Excel");
        butCarExc.setToolTipText("Cargar Excel");
        butCarExc.setPreferredSize(new java.awt.Dimension(110, 25));
        butCarExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarExcActionPerformed(evt);
            }
        });
        panBot.add(butCarExc);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(110, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(110, 25));
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

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitjInternalFrame1(evt);
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

        panFrm1.setLayout(new java.awt.BorderLayout());

        lblTit1.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit1.setText("Actualizacion Masiva de items.....");
        lblTit1.setToolTipText("");
        panFrm1.add(lblTit1, java.awt.BorderLayout.NORTH);

        tabFrm1.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat4.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat4.setLayout(new java.awt.BorderLayout());

        panDet3.setName(""); // NOI18N
        panDet3.setPreferredSize(new java.awt.Dimension(610, 180));
        panDet3.setLayout(new java.awt.BorderLayout());

        tblReg1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet4.setViewportView(tblReg1);

        panDet3.add(spnDet4, java.awt.BorderLayout.CENTER);

        panDat4.add(panDet3, java.awt.BorderLayout.CENTER);

        tabFrm1.addTab("Region", panDat4);

        panDat5.setLayout(new java.awt.BorderLayout());

        panDet4.setLayout(new java.awt.BorderLayout());

        tblCan1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet5.setViewportView(tblCan1);

        panDet4.add(spnDet5, java.awt.BorderLayout.CENTER);

        panDat5.add(panDet4, java.awt.BorderLayout.CENTER);

        tabFrm1.addTab("Canal", panDat5);

        panDat6.setLayout(new java.awt.BorderLayout());

        tblCar4.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet6.setViewportView(tblCar4);

        panDat6.add(spnDet6, java.awt.BorderLayout.CENTER);

        tabFrm1.addTab("CARGA-3", panDat6);

        panFrm1.add(tabFrm1, java.awt.BorderLayout.CENTER);

        panBar1.setLayout(new java.awt.BorderLayout());

        panBot1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon1.setText("Consultar");
        butCon1.setToolTipText("Consultar");
        butCon1.setPreferredSize(new java.awt.Dimension(110, 25));
        butCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon1ActionPerformed(evt);
            }
        });
        panBot1.add(butCon1);

        butCarExc1.setText("Cargar Excel");
        butCarExc1.setToolTipText("Cargar Excel");
        butCarExc1.setPreferredSize(new java.awt.Dimension(110, 25));
        butCarExc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarExc1ActionPerformed(evt);
            }
        });
        panBot1.add(butCarExc1);

        butGua1.setText("Guardar");
        butGua1.setPreferredSize(new java.awt.Dimension(110, 25));
        butGua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGua1ActionPerformed(evt);
            }
        });
        panBot1.add(butGua1);

        butCer1.setText("Cerrar");
        butCer1.setToolTipText("Cierra la ventana.");
        butCer1.setPreferredSize(new java.awt.Dimension(110, 25));
        butCer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCer1ActionPerformed(evt);
            }
        });
        panBot1.add(butCer1);

        panBar1.add(panBot1, java.awt.BorderLayout.CENTER);

        panBarEst1.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst1.setLayout(new java.awt.BorderLayout());

        lblMsgSis1.setText("Listo");
        lblMsgSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst1.add(lblMsgSis1, java.awt.BorderLayout.CENTER);

        panPrgSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis1.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis1.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis1.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis1.setBorderPainted(false);
        pgrSis1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis1.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis1.add(pgrSis1, java.awt.BorderLayout.CENTER);

        panBarEst1.add(panPrgSis1, java.awt.BorderLayout.EAST);

        panBar1.add(panBarEst1, java.awt.BorderLayout.SOUTH);

        panFrm1.add(panBar1, java.awt.BorderLayout.SOUTH);

        jInternalFrame1.getContentPane().add(panFrm1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jInternalFrame1, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
    try
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            if(conGP!=null){
                conGP.close();
                conGP=null;
            }
            dispose();
        }
    }
    catch (java.sql.SQLException e)
    {
        dispose();
    }        
}//GEN-LAST:event_exitForm

    private void importarExcel()
    {
        try
        {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            int seleccion = fileChooser.showOpenDialog(this);

            if (seleccion != javax.swing.JFileChooser.APPROVE_OPTION)
                return;

            java.io.File archivo = fileChooser.getSelectedFile();
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo));

            String linea;
            int fila = 0;
            int opcion = cmbTip.getSelectedIndex();

            objTblModCliLoc.removeAllRows();

            while ((linea = br.readLine()) != null)
            {
                // 🔥 LIMPIEZA FUERTE (quita caracteres invisibles)
                linea = linea.replace("\r", "")
                             .replace("\u0000", "")
                             .trim();

                if (linea.isEmpty())
                    continue;

                // 🔹 saltar cabecera
                if (fila == 0)
                {
                    fila++;
                    continue;
                }

                String[] datos = linea.split("\t");

                // 🔥 VALIDAR CONTENIDO REAL
                boolean filaValida = false;
                for (String d : datos)
                {
                    if (d != null && !d.trim().isEmpty())
                    {
                        filaValida = true;
                        break;
                    }
                }

                if (!filaValida)
                    continue;

                // 🔴 VALIDACIÓN SEGÚN COMBO
                if (opcion == 0 && datos.length < 2)
                {
                    mostrarMsgInf("Formato incorrecto (2 columnas esperadas).");
                    return;
                }

                if (opcion == 1 && datos.length < 3)
                {
                    mostrarMsgInf("Formato incorrecto (3 columnas esperadas).");
                    return;
                }

                objTblModCliLoc.insertRow(objTblModCliLoc.getRowCount());
                int lastRow = objTblModCliLoc.getRowCount() - 1;

                if (opcion == 0)
                {
                    objTblModCliLoc.setValueAt(datos[0].trim(), lastRow, INT_ARL_CLI_LOC_CLI1);
                    objTblModCliLoc.setValueAt(datos[1].trim(), lastRow, INT_ARL_CLI_LOC_VEN1);
                    objTblModCliLoc.setValueAt(0, lastRow, INT_ARL_CLI_LOC_EST1);
                    objTblModCliLoc.setValueAt("", lastRow, INT_ARL_CLI_LOC_ERR1);
                }
                else
                {
                    objTblModCliLoc.setValueAt(datos[0].trim(), lastRow, INT_ARL_CLI_LOC_LOC2);
                    objTblModCliLoc.setValueAt(datos[1].trim(), lastRow, INT_ARL_CLI_LOC_CLI2);
                    objTblModCliLoc.setValueAt(datos[2].trim(), lastRow, INT_ARL_CLI_LOC_VEN2);
                    objTblModCliLoc.setValueAt(0, lastRow, INT_ARL_CLI_LOC_EST2);
                    objTblModCliLoc.setValueAt("", lastRow, INT_ARL_CLI_LOC_ERR2);
                }

                fila++;
            }

            br.close();

            mostrarMsgInf("Archivo cargado correctamente.");
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void eliminarFilasVacias()
    {
        try
        {
            for (int i = objTblModCliLoc.getRowCount() - 1; i >= 0; i--)
            {
                boolean filaVacia = true;

                // 🔹 ignoramos la columna LIN (empieza en 1)
                for (int j = 1; j < objTblModCliLoc.getColumnCount(); j++)
                {
                    Object val = objTblModCliLoc.getValueAt(i, j);

                    if (val != null && !val.toString().trim().equals(""))
                    {
                        filaVacia = false;
                        break;
                    }
                }

                // 🔥 si la fila está vacía → eliminar
                if (filaVacia)
                {
                    objTblModCliLoc.removeRow(i);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void butCarExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarExcActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        int indTab=0;
        indTab = tabFrm.getSelectedIndex();
        if (indTab==5)
        {
            if (butCarExc.getText().equals("Cargar Excel"))
            {
                if (cmbTip.getSelectedIndex() == -1)
                {
                    mostrarMsgInf("Seleccione el tipo antes de importar.");
                    return;
                }
                else
                {
                    importarExcel();
                    eliminarFilasVacias();
                }

            }
            else
            {
                blnCon=false;
            }            
        }
        else
            mostrarMsgInf("No puede realizar la carga , no se encuentra en la pestaña de Cliente/Vendedor/Local...");
            

    }//GEN-LAST:event_butCarExcActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      
     blnCon=true;
    if (objThrGuaGUI==null)
    {
        objThrGuaGUI=new ZafThGuarGUI();
        objThrGuaGUI.start();
    }
        
        
        


    }//GEN-LAST:event_butGuaActionPerformed
    
    private Vector<Vector> vecRegRegCargados = new Vector<Vector>();
    private boolean cargarDetRegReg()//chiqui
    {
        String strSQL ="";
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                stm = con.createStatement();      

                strAux="";
                strSQL="  SELECT co_idreg as cod, tx_reg as nom,st_reg \n";
                strSQL+=" FROM Dynamics2021..tbm_reg  \n";
                //strSQL+=" WHERE st_reg ='A'  \n";                
                strSQL+=" ORDER BY cod asc  \n";
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatReg.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if ((blnCon) || (blnEjeIni))
                    {
                        vecRegReg=new Vector();
                        vecRegReg.add(INT_ARL_TAB_REG_LIN,"");
                        vecRegReg.add(INT_ARL_TAB_REG_COD, rst.getString("cod")==null?"":rst.getString("cod"));
                        vecRegReg.add(INT_ARL_TAB_REG_DES, rst.getString("nom")==null?"":rst.getString("nom"));
                        vecRegReg.add(INT_ARL_TAB_REG_EST, rst.getString("st_reg")==null?"":rst.getString("st_reg"));
                        vecRegReg.add(INT_ARL_TAB_REG_BLO, "B");
                        vecRegReg.add(INT_ARL_TAB_REG_CHK, false);
                        vecDatReg.add(vecRegReg);   
                        
                        vecRegRegCargados.add((Vector) vecRegReg.clone());
                        
                    }
                    else
                        break;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

                //Asignar vectores al modelo.
                objTblModReg.setData(vecDatReg);
                tblReg.setModel(objTblModReg);
                vecDatReg.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblReg.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblReg.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetUsuJefCan()//chiqui
    {
        String strSQL ="";
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                stm = con.createStatement();      

                strAux="";
                strSQL="  SELECT jefcan.co_jefcan AS coUsuJef,usr.tx_usr as desusujef,jefcan.co_idcan as cocan,can.tx_canasi as descan,jefcan.st_reg \n";
                strSQL+=" FROM Dynamics2021..tbm_jefcan as jefcan  \n";
                strSQL+="   INNER JOIN  Dynamics2021..tbm_usr as usr on (jefcan.co_jefcan = usr.co_usr)  \n";
                strSQL+="   INNER JOIN  Dynamics2021..tbm_canasi as can on (jefcan.co_idcan = can.co_id)  \n";   
////                strSQL+=" WHERE jefcan.st_reg ='A'  \n"; 
                strSQL+=" ORDER BY can.tx_canasi asc  \n";
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatUsuJefCan.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if ((blnCon) || (blnEjeIni))
                    {
                        vecRegUsuJefCan=new Vector();
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_LIN,"");
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_CAN, rst.getString("cocan")==null?"":rst.getString("cocan"));
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_DES_CAN, rst.getString("descan")==null?"":rst.getString("descan"));
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BTN_CAN, "");
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_USU, rst.getString("coUsuJef")==null?"":rst.getString("coUsuJef"));
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_DES_USU, rst.getString("desusujef")==null?"":rst.getString("desusujef"));                        
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BTN_USU, "");
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_CAN_ANT, rst.getString("cocan")==null?"":rst.getString("cocan"));                        
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_COD_USU_ANT, rst.getString("coUsuJef")==null?"":rst.getString("coUsuJef"));                        
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_CHK_ANU,false);   
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_BLO,'B');   
                        vecRegUsuJefCan.add(INT_ARL_TAB_USU_JEF_CAN_EST,rst.getString("st_reg")==null?"":rst.getString("st_reg"));   
                        vecDatUsuJefCan.add(vecRegUsuJefCan);     
                    }
                    else
                        break;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

                //Asignar vectores al modelo.
                objTblModUsuJefCan.setData(vecDatUsuJefCan);
                tblUsuJefCan.setModel(objTblModUsuJefCan);
                vecDatUsuJefCan.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblUsuJefCan.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblUsuJefCan.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

    private boolean cargarDetRegJefCan()//chiqui
    {
        String strSQL ="";
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                stm = con.createStatement();      

                strAux="";
                strSQL="  SELECT regje.co_idreg as codReg , reg.tx_reg as desReg, regje.co_jefcan as codJef,usr.tx_usr as desJef, regje.st_Reg\n";
                strSQL+=" FROM Dynamics2021..tbr_regjefcan as regje  \n";
                strSQL+="   LEFT JOIN  Dynamics2021..tbm_usr as usr on (regje.co_jefcan = usr.co_usr)  \n";
                strSQL+="   LEFT JOIN  Dynamics2021..tbm_reg as reg on (regje.co_idreg = reg.co_idreg)  \n";                
////                strSQL+=" WHERE  regje.st_reg='A'  \n";                     
                strSQL+=" ORDER BY regje.co_idreg asc  \n";
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatRegJefCan.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if ((blnCon) || (blnEjeIni))
                    {
                        vecRegRegJefCan=new Vector();
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_LIN,"");
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_REG, rst.getString("codReg")==null?"":rst.getString("codReg"));
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_DES_REG, rst.getString("desReg")==null?"":rst.getString("desReg"));
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BTN_REG, "");
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_JEF, rst.getString("codJef")==null?"":rst.getString("codJef"));
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_DES_JEF, rst.getString("desJef")==null?"":rst.getString("desJef"));                        
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BTN_JEF, "");
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_REG_ANT, rst.getString("codReg")==null?"":rst.getString("codReg")); 
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_COD_JEF_ANT, rst.getString("codJef")==null?"":rst.getString("codJef"));                         
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_CHK_ANU,false);   
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_BLO,'B');   
                        vecRegRegJefCan.add(INT_ARL_TAB_REG_JEF_CAN_EST,rst.getString("st_Reg")==null?"":rst.getString("st_Reg"));   
                        vecDatRegJefCan.add(vecRegRegJefCan);    
                        

    
    
                    }
                    else
                        break;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

                //Asignar vectores al modelo.
                objTblModRegJefCan.setData(vecDatRegJefCan);
                tblRegJefCan.setModel(objTblModRegJefCan);
                vecDatRegJefCan.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblRegJefCan.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblRegJefCan.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean cargarDetJefCanVen()//chiqui
    {
        String strSQL ="";
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                stm = con.createStatement();      

                strAux="";
                strSQL="  SELECT tbr.usrJefCan as codJef,usrJef.tx_usr as desJef,usrJef.tx_nom,tbr.usrVen as codVen,usrVen.tx_usr as desVen,tbr.codcan as codcan,can.tx_canasi ,tbr.st_reg \n";
                strSQL+=" FROM Dynamics2021..tbr_canjefvenasi as tbr  \n";
                strSQL+="   LEFT JOIN  Dynamics2021..tbm_usr as usrJef on (tbr.usrjefcan = usrJef.co_usr)  \n";
                strSQL+="   LEFT JOIN  Dynamics2021..tbm_usr as usrVen on (tbr.usrven = usrVen.co_usr)  \n";                
                strSQL+="   LEFT JOIN  Dynamics2021..tbm_canasi as can on (tbr.codcan = can.co_id)   \n";  
////                strSQL+=" WHERE  tbr.st_reg='A'  \n"; 
                strSQL+=" ORDER BY usrJef.tx_usr asc  \n";
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatJefVen.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if ((blnCon) || (blnEjeIni))
                    {
                        vecRegJefVen=new Vector();
                        
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_LIN,"");
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_JEF, rst.getString("codJef")==null?"":rst.getString("codJef"));
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_JEF, rst.getString("desJef")==null?"":rst.getString("desJef"));
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_LAR_JEF, rst.getString("codJef")==null?"":rst.getString("tx_nom"));
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_CAN_JEF, rst.getString("desJef")==null?"":rst.getString("codcan"));                        
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_CAN_JEF, rst.getString("desJef")==null?"":rst.getString("tx_canasi"));                        
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_BTN_JEF, "");
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_VEN, rst.getString("codVen")==null?"":rst.getString("codVen"));
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_DES_VEN, rst.getString("desVen")==null?"":rst.getString("desVen"));                        
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_BTN_VEN, "");
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_JEF_ANT, rst.getString("codJef")==null?"":rst.getString("codJef")); 
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_VEN_ANT, rst.getString("codVen")==null?"":rst.getString("codVen"));                         
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_COD_CAN_ANT, rst.getString("codcan")==null?"":rst.getString("codcan"));                           
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_CHK_ANU,false);   
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_BLO,'B');       
                        vecRegJefVen.add(INT_ARL_TAB_REG_JEFVEN_EST,rst.getString("st_reg")==null?"":rst.getString("st_reg"));       
                        vecDatJefVen.add(vecRegJefVen);    
                    }
                    else
                        break;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

                //Asignar vectores al modelo.
                objTblModJefVen.setData(vecDatJefVen);
                tblRegJefVen.setModel(objTblModJefVen);
                vecDatJefVen.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblRegJefCan.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblRegJefVen.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private Vector<Vector> vecRegCanCargados = new Vector<Vector>();
    private boolean cargarDetRegCan()
    {
        String strSQL ="";
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            con=DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null) 
            {
                stm = con.createStatement();      

                strAux="";
                strSQL="  SELECT co_id as cod, tx_canasi as nom ,st_reg \n";
                strSQL+=" FROM Dynamics2021..tbm_canasi  \n";
                //strSQL+=" WHERE  st_reg='A'  \n"; 
                strSQL+=" ORDER BY co_id asc  \n";

                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatCan.clear();

                lblMsgSis.setText("Cargando datos...");

                while (rst.next())
                {
                    if ((blnCon) || (blnEjeIni))
                    {
                        vecRegCan=new Vector();

                        vecRegCan.add(INT_ARL_TAB_CAN_LIN,"");
                        vecRegCan.add(INT_ARL_TAB_CAN_COD, rst.getString("cod")==null?"":rst.getString("cod"));
                        vecRegCan.add(INT_ARL_TAB_CAN_DES, rst.getString("nom")==null?"":rst.getString("nom"));
                        vecRegCan.add(INT_ARL_TAB_CAN_EST, rst.getString("st_reg")==null?"":rst.getString("st_reg"));


                        // 🔴 BLOQUEADA SIEMPRE
                        vecRegCan.add(INT_ARL_TAB_CAN_BLO, "B");

                        // 🔹 CHECK (inicialmente false)
                        vecRegCan.add(INT_ARL_TAB_CAN_CHK, false);

                        vecDatCan.add(vecRegCan); 
                        vecRegCanCargados.add((Vector) vecRegCan.clone());
                    }
                    else
                        break;
                }

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

                //Asignar vectores al modelo.
                objTblModCan.setData(vecDatCan);
                tblCan.setModel(objTblModCan);

                vecDatCan.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblCan.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblCan.getRowCount() + " registros.");

                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            int valTip = tabFrm.getSelectedIndex();
            //valTip=0;

            //<editor-fold defaultstate="collapsed" desc="/*REGION */"> 

            if (valTip==0)
            {
                if (!cargarDetRegReg())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblReg.getRowCount()>0)
                {
                     tabFrm.setSelectedIndex(valTip);
                     objThrGUI=null;
                }                           

            }   
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/*CANAL */"> 
            if (valTip==1)
            {
                if (!cargarDetRegCan())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblCan.getRowCount()>0)
                {
                     tabFrm.setSelectedIndex(valTip);
                     objThrGUI=null;
                }                
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/*CANAL VS VENDEDOR */"> 
            if (valTip==2)
            {
                if (!cargarDetUsuJefCan())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblUsuJefCan.getRowCount()>0)
                {
                     tabFrm.setSelectedIndex(valTip);
                     objThrGUI=null;
                }                           

            }                
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/*REGION VS JEFE DE CANAL */"> 
            if (valTip==3)
            {
                if (!cargarDetRegJefCan())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblRegJefCan.getRowCount()>0)
                {
                     tabFrm.setSelectedIndex(valTip);
                     objThrGUI=null;
                }                           

            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/*JEFE DE CANAL VS VENDEDOR */"> 
            if (valTip==4)
            {
                if (!cargarDetJefCanVen())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblRegJefVen.getRowCount()>0)
                {
                     tabFrm.setSelectedIndex(valTip);
                     objThrGUI=null;
                }                           

            }                
            //</editor-fold>



        }
        }    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
////////        int index2 = tabFrm.indexOfTab("Canal");
////////        if (index2 ==1)
////////        {
////////            mostrarCanal();
////////        }
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafVen76.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }


    }//GEN-LAST:event_butConActionPerformed

    private void butCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butCon1ActionPerformed

    private void butCarExc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarExc1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butCarExc1ActionPerformed

    private void butGua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGua1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butGua1ActionPerformed

    private void butCer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butCer1ActionPerformed

    private void exitjInternalFrame1(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitjInternalFrame1
        // TODO add your handling code here:
    }//GEN-LAST:event_exitjInternalFrame1

 
    
    private boolean configurarTblSegunCombo()
    {
    boolean blnRes = true;
    try
    {
        vecDatCliLoc = new Vector();
        vecCabCliLoc = new Vector();
        vecCabCliLoc.clear();

        int opcion = cmbTip.getSelectedIndex();

        // 🔹 Columna numeración
        vecCabCliLoc.add(INT_ARL_CLI_LOC_LIN, "");

        if (opcion == 0)
        {
            vecCabCliLoc.add(INT_ARL_CLI_LOC_CLI1, "Cliente");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_VEN1, "Vendedor");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_EST1, "Estado");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_ERR1, "Error");
        }
        else if (opcion == 1)
        {
            vecCabCliLoc.add(INT_ARL_CLI_LOC_LOC2, "Local");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_CLI2, "Cliente");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_VEN2, "Vendedor");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_EST2, "Estado");
            vecCabCliLoc.add(INT_ARL_CLI_LOC_ERR2, "Error");
        }

        objTblModCliLoc = new ZafTblMod();
        objTblModCliLoc.setHeader(vecCabCliLoc);
        tblcliloc.setModel(objTblModCliLoc);

        // 🔥 MODO INSERTAR
        objTblModCliLoc.setModoOperacion(objTblModCliLoc.INT_TBL_INS);

        // 🔥 COLUMNAS EDITABLES
        Vector vecAux = new Vector();

        if (opcion == 0)
        {
            vecAux.add("" + INT_ARL_CLI_LOC_CLI1);
            vecAux.add("" + INT_ARL_CLI_LOC_VEN1);
        }
        else
        {
            vecAux.add("" + INT_ARL_CLI_LOC_LOC2);
            vecAux.add("" + INT_ARL_CLI_LOC_CLI2);
            vecAux.add("" + INT_ARL_CLI_LOC_VEN2);
        }

        objTblModCliLoc.setColumnasEditables(vecAux);
        vecAux = null;

        // 🔹 Numeración automática
        objColNumReg = new ZafColNumerada(tblcliloc, INT_ARL_CLI_LOC_LIN);

        tblcliloc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblcliloc.getTableHeader().setReorderingAllowed(false);

        javax.swing.table.TableColumnModel tcmAux = tblcliloc.getColumnModel();

        tcmAux.getColumn(INT_ARL_CLI_LOC_LIN).setPreferredWidth(30);

        if (opcion == 0)
        {
            tcmAux.getColumn(INT_ARL_CLI_LOC_CLI1).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_CLI_LOC_VEN1).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_CLI_LOC_EST1).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_CLI_LOC_ERR1).setPreferredWidth(150);
        }
        else
        {
            tcmAux.getColumn(INT_ARL_CLI_LOC_LOC2).setPreferredWidth(80);
            tcmAux.getColumn(INT_ARL_CLI_LOC_CLI2).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_CLI_LOC_VEN2).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_CLI_LOC_EST2).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_CLI_LOC_ERR2).setPreferredWidth(150);
        }
        
    if (opcion == 0)
    {
        tcmAux.getColumn(INT_ARL_CLI_LOC_CLI1).setPreferredWidth(150);
        tcmAux.getColumn(INT_ARL_CLI_LOC_VEN1).setPreferredWidth(100);
        tcmAux.getColumn(INT_ARL_CLI_LOC_EST1).setPreferredWidth(30);
        tcmAux.getColumn(INT_ARL_CLI_LOC_ERR1).setPreferredWidth(150);
        ocultarColumna(tcmAux, INT_ARL_CLI_LOC_EST1);
    }
    else
    {
        tcmAux.getColumn(INT_ARL_CLI_LOC_LOC2).setPreferredWidth(80);
        tcmAux.getColumn(INT_ARL_CLI_LOC_CLI2).setPreferredWidth(150);
        tcmAux.getColumn(INT_ARL_CLI_LOC_VEN2).setPreferredWidth(100);
        tcmAux.getColumn(INT_ARL_CLI_LOC_EST2).setPreferredWidth(30);
        tcmAux.getColumn(INT_ARL_CLI_LOC_ERR2).setPreferredWidth(150);
        ocultarColumna(tcmAux, INT_ARL_CLI_LOC_EST2);
    }


        // 🔹 Primera fila
        objTblModCliLoc.insertRow();

        // 🔥 FILA DINÁMICA (tipo Excel)
        objTblModCliLoc.addTableModelListener(new javax.swing.event.TableModelListener()
        {
            public void tableChanged(javax.swing.event.TableModelEvent e)
            {
                int lastRow = objTblModCliLoc.getRowCount() - 1;

                boolean filaVacia = true;

                for (int col = 1; col < objTblModCliLoc.getColumnCount(); col++)
                {
                    Object val = objTblModCliLoc.getValueAt(lastRow, col);
                    if (val != null && !val.toString().trim().equals(""))
                    {
                        filaVacia = false;
                        break;
                    }
                }

                if (!filaVacia)
                {
                    objTblModCliLoc.insertRow();
                }
            }
        });

    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
    }
    
private void ocultarColumna(javax.swing.table.TableColumnModel tcmAux, int columna)
{
    tcmAux.getColumn(columna).setMinWidth(0);
    tcmAux.getColumn(columna).setPreferredWidth(0);
    tcmAux.getColumn(columna).setMaxWidth(0);
    tcmAux.getColumn(columna).setWidth(0);
    tcmAux.getColumn(columna).setResizable(false);
}    
    
    private void exportarExcel()
    {
    try
    {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Guardar Excel");

        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File archivo = fileChooser.getSelectedFile();

            java.io.FileWriter fw = new java.io.FileWriter(archivo + ".xls");

            // 🔹 CABECERAS (empieza en 1, NO en 0)
            for (int i = 1; i < tblcliloc.getColumnCount(); i++)
            {
                fw.write(tblcliloc.getColumnName(i) + "\t");
            }
            fw.write("\n");

            // 🔹 DATOS
            for (int i = 0; i < tblcliloc.getRowCount(); i++)
            {
                boolean filaVacia = true;

                // validar si tiene datos (ignorando LIN)
                for (int j = 1; j < tblcliloc.getColumnCount(); j++)
                {
                    Object val = tblcliloc.getValueAt(i, j);
                    if (val != null && !val.toString().trim().equals(""))
                    {
                        filaVacia = false;
                        break;
                    }
                }

                // 🔥 solo exporta filas con datos
                if (!filaVacia)
                {
                    for (int j = 1; j < tblcliloc.getColumnCount(); j++) // 👈 empieza en 1
                    {
                        Object val = tblcliloc.getValueAt(i, j);
                        fw.write((val == null ? "" : val.toString()) + "\t");
                    }
                    fw.write("\n");
                }
            }

            fw.close();

            mostrarMsgInf("Archivo exportado correctamente (sin columna LIN).");
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}
    
  
    private void cmbTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipActionPerformed
           configurarTblSegunCombo();
           exportarExcel();
    }//GEN-LAST:event_cmbTipActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCarExc;
    private javax.swing.JButton butCarExc1;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCer1;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCon1;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butGua1;
    private javax.swing.JComboBox<String> cmbTip;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtnovedad;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblMsgSis1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTit1;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBar1;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBarEst1;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBot1;
    private javax.swing.JPanel panDat1;
    private javax.swing.JPanel panDat10;
    private javax.swing.JPanel panDat2;
    private javax.swing.JPanel panDat3;
    private javax.swing.JPanel panDat4;
    private javax.swing.JPanel panDat5;
    private javax.swing.JPanel panDat6;
    private javax.swing.JPanel panDat7;
    private javax.swing.JPanel panDat8;
    private javax.swing.JPanel panDat9;
    private javax.swing.JPanel panDet1;
    private javax.swing.JPanel panDet2;
    private javax.swing.JPanel panDet3;
    private javax.swing.JPanel panDet4;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panFrm1;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panPrgSis1;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JProgressBar pgrSis1;
    private javax.swing.JScrollPane spnDet1;
    private javax.swing.JScrollPane spnDet2;
    private javax.swing.JScrollPane spnDet3;
    private javax.swing.JScrollPane spnDet4;
    private javax.swing.JScrollPane spnDet5;
    private javax.swing.JScrollPane spnDet6;
    private javax.swing.JScrollPane spnDet7;
    private javax.swing.JScrollPane spnDet8;
    private javax.swing.JScrollPane spnDet9;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTabbedPane tabFrm1;
    private javax.swing.JTable tblCan;
    private javax.swing.JTable tblCan1;
    private javax.swing.JTable tblCar4;
    private javax.swing.JTable tblReg;
    private javax.swing.JTable tblReg1;
    private javax.swing.JTable tblRegJefCan;
    private javax.swing.JTable tblRegJefVen;
    private javax.swing.JTable tblUsuJefCan;
    private javax.swing.JTable tblcliloc;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
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
    
    private void mostrarMsgInfCopia(String strMsg)
    {
        JTextArea textArea = new JTextArea(strMsg);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(240, 240, 240)); // mismo color que el fondo del dialogo

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Mensaje del sistema Zafiro",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
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
    
    
    private void ConfiguracionColumnas(int tipo)
    {
        if (tipo ==0)
        {
            ColumasExcel =  new String[][]
            {
                {"FRECUENCIA", objUti.parseString(INT_ARL_EXC_PRI_FRE)},
                {"STOCKBASE", objUti.parseString(INT_ARL_EXC_PRI_STO)},
                {"LEADTIME", objUti.parseString(INT_ARL_EXC_PRI_LEA)},
                {"MOQ", objUti.parseString(INT_ARL_EXC_PRI_MOQ)},
                {"COBMAX", objUti.parseString(INT_ARL_EXC_PRI_COB_MAX)},
                {"PESO", objUti.parseString(INT_ARL_EXC_PRI_PES)}
            };            
        }
        else
        {
            if (tipo ==1)
            {
                ColumasExcel =  new String[][]
                {
                    {"ITEM", objUti.parseString(INT_ARL_EXC_PRI_FRE)},
                    {"MIN", objUti.parseString(INT_ARL_EXC_PRI_STO)},
                    {"MAX", objUti.parseString(INT_ARL_EXC_PRI_LEA)},
                };                                    
                
            }
        }

    }  
    
    public  String buscarColumnaSeleccionada(String[][] datos, String clave) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0].equals(clave)) {
                return datos[i][1]; // Retorna el valor de la segunda columna
            }
        }
        return null; // Si no se encuentra la clave, devuelve null
    }     
    private boolean blnCargarArchivo; 
    private int intNumeroItemsProcesar=0;
    private ArrayList arlDatExcel, arlRegExcel;
    boolean blnNoSeleccion=true;
    StringBuilder cadenaColAct = new StringBuilder();
    
    private boolean getArchivoExcel()
    {
        boolean blnRes = true;
        try{  
            blnCargarArchivo = false;
            intNumeroItemsProcesar=0;
            arlDatExcel = new ArrayList();
            JFileChooser objfileChooser = new JFileChooser();
            objfileChooser.setDialogTitle("Seleccionar archivo Excel Formato (XLS)");
            objfileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xls"/*, "xlsx"*/));

            int userSelection = objfileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                blnNoSeleccion=false;
                File selectedFile = objfileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Aquí puedes hacer lo que necesites con el archivo Excel seleccionado
//                System.out.println("Archivo seleccionado: " + filePath);
                blnCargarArchivo=true;

                Workbook workbook = Workbook.getWorkbook(new File(filePath));

                // Leer la primera hoja del libro
                Sheet sheet = workbook.getSheet(0);

                // Obtener el número de filas y columnas en la hoja
                int numRows = sheet.getRows();
                int numCols = sheet.getColumns();
                boolean blnExi=false;
                
                String[] columnasExcel = new String[numCols];
                for (int j = 0; j < numCols; j++) {
                    Cell cell = sheet.getCell(j, 0);
                    columnasExcel[j] = cell.getContents().trim();
                }                
                
                /**/
                
                if (!validarColumnasExcel(columnasExcel, ColumasExcel)) {
                    blnCargarArchivo = false;
                    mostrarMsgInf("El archivo Excel NO cumple con la estructura requerida.");
                    return false;
                }                

                
                String columna ="";
                cadenaColAct = new StringBuilder(); 
                for (int i = 0; i <=0; i++) {
                    // Iterar sobre las columnas de la fila actual

                    for (int j = 0; j < numCols; j++) 
                    {
                        Cell cell = sheet.getCell(j, i);
                        String cellValue = cell.getContents();
                        if (j==0)
                        {
                            if (cellValue.equals(""))
                                blnExi=true;
                        }   
                           columna = buscarColumnaSeleccionada(ColumasExcel,cellValue);
                           if (columna!=null)
                              cadenaColAct.append(cellValue).append(",");  
                    }
                }
                /**/
                
                if (cadenaColAct.length() > 0) {
                    cadenaColAct.setLength(cadenaColAct.length() - 1); // Elimina la última coma
                }
                String [] columnasAct = cadenaColAct.toString().split(",");
                arlDatExcel=new ArrayList();
                // Iterar sobre las filas de la hoja
                for (int i = 1; i < numRows; i++) 
                {
                    arlRegExcel = new ArrayList();
                    blnExi = false;

                    // Leer la primera columna (ITEM)
                    Cell celItem = sheet.getCell(0, i);
                    String strItem = celItem.getContents().trim();

                    // Si el ítem está vacío, saltar esta fila
                    if (strItem.isEmpty()) {
                        continue; // ❌ No se toma en cuenta esta fila
                    }

                    // Agregar todas las columnas normalmente
                    for (int j = 0; j < numCols; j++) {
                        Cell cell = sheet.getCell(j, i);
                        String cellValue = cell.getContents();
                        arlRegExcel.add(cellValue);
                        System.out.print(cellValue + "\t");
                    }

                    intNumeroItemsProcesar++;
                    arlDatExcel.add(arlRegExcel);
                    System.out.println();
                }

//                System.out.println("arlDatExcel<<<" + arlDatExcel.toString());
                workbook.close();


            }
            else{
                blnRes=false;
                mostrarMsgInf("No se seleccionó ningun archivo...");
            }
        } 
        catch (IOException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  

    
    
    private boolean validarDatosCargados(int tipo){
        boolean blnRes=true;
        strCodItmDynBus="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCuentaItemsEncontrados=0;
        try{
            arlDatExcelVal=new ArrayList();
            if (tipo==0)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM)+"'";

                        if(i+1<arlDatExcel.size()) {
                            strCodItmDynBus+=",";
                        }
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_PRI_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM));
                        arlRegExcelVal.add(INT_EXC_PRI_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    

                }                
            }
            if (tipo==1)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM)+"'";

                        if(i+1<arlDatExcel.size()) {
                            strCodItmDynBus+=",";
                        }
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_SEG_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM));
                        arlRegExcelVal.add(INT_EXC_SEG_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    

                }                
                
            }

            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null)
            {
                
                if (strCodItmDynBus.endsWith(",")) 
                {
    // Elimina la coma del final
                    strCodItmDynBus = strCodItmDynBus.substring(0, strCodItmDynBus.length() - 1);
                }
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, TRIM(ITMGEDSC) as ITMGEDSC, a1.ITEMTYPE  "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1 "
                       //18112024 + " WHERE a1.ITMGEDSC IN ( "+strCodItmDynBus+" )  and a1.inactive =0"  ;
                        + " WHERE a1.ITMGEDSC IN ( "+strCodItmDynBus+" )  "  ;
//                System.out.println("obtenerDatosGP: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (tipo==1)
                {
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN, "OK");
                                break;
                            }


    //                        else{
    //                            objUti.setStringValueAt(arlDatExcelVal, x,INT_EXC_BLN, "NO");
    //                        }
                        }
                    }                    
                }
                if (tipo==2)
                {
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_SEG_ITEM)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_SEG_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }

                rstLoc.close();
                rstLoc=null;
                
                if(intCuentaItemsEncontrados!=intNumeroItemsProcesar){
                    blnResNO=true;
                    
                }
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    private HashMap<String, Integer> mapDatItems;     
    
    private boolean validarDatosCargadosGrupo() 
    {
        boolean blnRes = true;
        mapDatItems = new HashMap<>();

        try (SQLServerConnection conLoc = (SQLServerConnection) DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021())) {

            if (conLoc != null) {

                // Quita coma final si existe
                if (strCodItmDynBus.endsWith(",")) {
                    strCodItmDynBus = strCodItmDynBus.substring(0, strCodItmDynBus.length() - 1);
                }

                // Convertir lista "SAN,EZA,PGB" en un SQLServerDataTable
                SQLServerDataTable tvp = new SQLServerDataTable();
                tvp.addColumnMetadata("codigo", java.sql.Types.NVARCHAR);

                String[] codigos = strCodItmDynBus.split(",");
                for (String cod : codigos) {
                    tvp.addRow(cod.trim().replace("'", ""));
                }

                // Llamar al procedimiento
                try (SQLServerCallableStatement cstmt = 
                        (SQLServerCallableStatement) conLoc.prepareCall("{call dbo.spValidarDatosGrupo(?)}")) {
                    cstmt.setStructured(1, "dbo.ItemCodeList", tvp);

                    try (ResultSet rstLoc = cstmt.executeQuery()) {
                        while (rstLoc.next()) {
                            String item = rstLoc.getString("tresL");
                            int detalle = rstLoc.getInt("detalle");
                            mapDatItems.put(item, detalle);
                        }
                    }
                }
            } else {
                blnRes = false;
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
}  
   
    private  Map<String, Map<String, Object>> obtenerHistorialItems( String cadenaItems)  
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;    
        String strSql="";
        Map<String, Map<String, Object>> historial = new HashMap<>();
        try
        {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null)
            {
                
                stmLoc = conLoc.createStatement();
                strSql+=" select tx_codalt2,tx_itm, nd_fre, nd_moq, nd_pes, nd_punParMax as nd_cobmax, nd_tra, nd_stkBas "
                        + " FROM Dynamics2021..tbm_inv "
                        + " WHERE tx_codalt2 IN ( "+cadenaItems+" )  "  ;
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    String item = rstLoc.getString("tx_codalt2");

                    Map<String, Object> datos = new HashMap<>();
                    datos.put("tx_itm", rstLoc.getString("tx_itm"));
                    datos.put("nd_fre", rstLoc.getBigDecimal("nd_fre"));
                    datos.put("nd_moq", rstLoc.getBigDecimal("nd_moq"));
                    datos.put("nd_pes", rstLoc.getBigDecimal("nd_pes"));
                    datos.put("nd_cobmax", rstLoc.getBigDecimal("nd_cobmax"));
                    datos.put("nd_tra", rstLoc.getBigDecimal("nd_tra"));
                    datos.put("nd_stkBas", rstLoc.getBigDecimal("nd_stkBas"));

                    historial.put(item, datos);
                }                    
                    
            } 
        }
        catch(java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return historial;
    }

    /**
 * Valida las columnas del Excel versus las columnas requeridas según el tipo.
 * Retorna true si todas las requeridas están presentes.
 */
private boolean validarColumnasExcel(String[] columnasExcel, String[][] columnasEsperadas) 
{
    boolean blnValido = true;

    ArrayList<String> colCoinciden = new ArrayList<>();
    ArrayList<String> colFaltantes = new ArrayList<>();
    ArrayList<String> colSobrantes = new ArrayList<>();

    // Convertir columnasExcel a mayúsculas para comparar sin error
    HashSet<String> setExcel = new HashSet<>();
    for (String col : columnasExcel) {
        if (col != null)
            setExcel.add(col.trim().toUpperCase());
    }

    // Validar columnas requeridas
    for (String[] colEsp : columnasEsperadas) {
        String colReq = colEsp[0].trim().toUpperCase();

        if (setExcel.contains(colReq)) {
            colCoinciden.add(colReq);
        } 
        else {
            colFaltantes.add(colReq);
            blnValido = false;
        }
    }

    // Detectar columnas sobrantes en el Excel
    for (String colExcel : setExcel) {
        boolean existe = false;

        for (String[] colEsp : columnasEsperadas) {
            if (colExcel.equals(colEsp[0].trim().toUpperCase())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            colSobrantes.add(colExcel);
        }
    }

    // Mostrar resultado en consola (o usar tu propio mensaje)
    System.out.println("==== VALIDACIÓN DE COLUMNAS ====");
    System.out.println("Columnas del Excel: " + Arrays.toString(columnasExcel));
    System.out.println("Columnas requeridas: " + Arrays.deepToString(columnasEsperadas));
    System.out.println("Coinciden: " + colCoinciden);
    System.out.println("Faltantes: " + colFaltantes);
    System.out.println("Sobrantes: " + colSobrantes);

    if (!colFaltantes.isEmpty()) {
        mostrarMsgInf("Faltan columnas obligatorias en el Excel:\n" + colFaltantes);
    }

    return blnValido;
}

    
private boolean cargarArchivoXLS()
     {
        boolean blnRes=true;
        boolean blnCarGrp=false;
        String strAux;
       
        try
        {  
            // Leer fila 0 (títulos del Excel)

            
            if (indTab==0)
                ConfiguracionColumnas(0);
            if (indTab==1)
                ConfiguracionColumnas(1);            
                
            
            if (getArchivoExcel() == true)
            {
                if(validarDatosCargados(indTab))
                {
                    if (indTab==0)
                    {
                        if(validarDatosCargadosGrupo())
                        {
                            Map<String, Map<String, Object>> resultado = obtenerHistorialItems(strCodItmDynBus);
                            if (!resultado.isEmpty())
                            {
//////                                if(ingresarDatosCargadosTbl(0))
//////                                {
//////                                    mostrarMsgInf("La carga del archivo fue exitosa.");
//////                                }
//////                                else
//////                                {
//////                                    blnRes=false;
//////                                    mostrarMsgInf("No se puedo guardar los datos...");
//////                                }                             
                            }                            
                        }
                    }
                    if (indTab==1)
                    {
//////                        if(ingresarDatosCargadosTbl(1))
//////                        {
//////                            mostrarMsgInf("La carga del archivo fue exitosa.");
//////                        }
//////                        else
//////                        {
//////                            blnRes=false;
//////                            mostrarMsgInf("No se puedo guardar los datos...");
//////                        }                          
                    }
                }
                else
                {
                    strAux =  "Existen Items en el documento que no son encontrados en el Sistema." + "\n\n";
                    strAux += "Notifique esta novedad al Administrador del sistema. \n";
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN).equals("N")){
                            strAux+=" "+objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM) + "-"; 
                        }
                    }
                    if (strAux.endsWith("-")) 
                        strAux = strAux.substring(0, strAux.length() - 1);
                        
                    blnRes=false;
                    mostrarMsgInf(strAux);
                }
                
                if (blnResNO)//PARA ENVIAR UNA NOTIFICACION DE ITEMS QUE NO ESTAN INGRESADOS EN EL MAESTRO
                {
                    strAux =  "Existen Items en el documento que no son encontrados en el Sistema." + "\n\n";
                    strAux += "Notifique esta novedad al Administrador del sistema. \n";
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN).equals("N")){
                            strAux+=" "+objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM) + "-"; 
                        }
                    }
                    if (strAux.endsWith("-")) 
                        strAux = strAux.substring(0, strAux.length() - 1);
                        
                    blnRes=false;
                    mostrarMsgInfCopia(strAux);                    
                }
                
                
                if (mapDatItems != null && !mapDatItems.isEmpty()) 
                {
                    strAux =  "Existen Items en el documento pertencen a un detalle, no se pueden modificar ." + "\n\n";
                    strAux += "Notifique esta novedad al Administrador del sistema. \n";                    
                    for (Map.Entry<String, Integer> entry : mapDatItems.entrySet()) 
                    {
                        String item = entry.getKey();
                        int detalle = entry.getValue();
                        strAux+="" +item + " - ";
                    }
                    mostrarMsgInf(strAux);
                }
            }
            else{  
                
                if(blnNoSeleccion){
                    blnRes=false;
                }
                else{
                    if (blnCargarArchivo == false){   
                        strAux =  "La carga del archivo no fue exitosa." + "\n\n";
                        strAux += "Notifique esta novedad al Administrador del sistema.";
                        mostrarMsgInf(strAux);
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
    

    
   


    
    
    
    
}

