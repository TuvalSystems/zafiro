/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon65;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.sql.DriverManager;
import java.util.Vector;

/**
 *
 * @author sistemas4
 */
public class ZafCon65_01 extends javax.swing.JDialog {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Vector vecDat,vecCab,vecReg;
    private ZafCon65 objCon65;
    private ZafColNumerada objColNum;

    ///CAMPOS PARA EL PASO 1
    //CAMPOS FIJOS
    private static final int INT_TBL_DAT_LINEA = 0;                           //Índice de columna Linea.    
    final int INT_DAT_HIS_COD_TRA=1;
    final int INT_DAT_HIS_NOM_TRA=2;
    final int INT_DAT_HIS_IDE_TRA=3;
    final int INT_DAT_HIS_ID_CEN_COS=4;
    final int INT_DAT_HIS_NOM_CEN_COS=5;
    final int INT_DAT_HIS_TIP_CEN_COS=6;
    final int INT_DAT_HIS_SUE_TRA=7;

    //CAMPOS VARIABLES DEPENDE DEL PERIODO QUINCENA
    final int INT_DAT_HIS_COM_ANT=8;
    final int INT_DAT_HIS_ANT_QUI=9;
    final int INT_DAT_HIS_IMP_REN_ANT=10;
    final int INT_DAT_HIS_VAC_ANT=11;
    
    //CAMPOS VARIABLES DEPENDE DEL PERIODO FIN DE MES
    final int INT_DAT_HIS_REI_VAC_LIQ=8;
    final int INT_DAT_HIS_HOR_EXT=9;
    final int INT_DAT_HIS_OTR_ING_NO_GRA=10;
    final int INT_DAT_HIS_OTR_ING_GRA=11;
    final int INT_DAT_HIS_FON_RES=12;
    final int INT_DAT_HIS_13ERA_REM=13;
    final int INT_DAT_HIS_14ERA_REM=14;
    final int INT_DAT_HIS_ALI_MEN=15;
    final int INT_DAT_HIS_REI_IMP_REN=16;
    final int INT_DAT_HIS_APO_PER=17;
    final int INT_DAT_HIS_EXT_SAL_CON=18;
    final int INT_DAT_HIS_IMP_REN_PRO=19;
    final int INT_DAT_HIS_DES_ANT_QUI=20;
    final int INT_DAT_HIS_DES_COM_ANT=21;
    final int INT_DAT_HIS_PRE_QUI=22;
    final int INT_DAT_HIS_PRE_HIP=23;    
    final int INT_DAT_HIS_OTR_DES=24;
    final int INT_DAT_HIS_ANT_SUE=25;
    final int INT_DAT_HIS_SUB_MAT=26;
    final int INT_DAT_HIS_RET_JUD=27;
    final int INT_DAT_HIS_PRE_EMP=28;
    final int INT_DAT_HIS_ATR_SAL_ANT=29;
    final int INT_DAT_HIS_MUL_FAL=30;
    final int INT_DAT_HIS_ALI=31;
    final int INT_DAT_HIS_ACU_DEC_3=32;
    final int INT_DAT_HIS_ACU_DEC_4=33;
    final int INT_DAT_HIS_PRO_APO_PAT=34;
    final int INT_DAT_HIS_PRO_VAC=35;
    final int INT_DAT_HIS_FON_RES_IEES=36;
    final int INT_DAT_HIS_TOT_REC_2DA_QUI=37;
    final int INT_DAT_HIS_SEM_INT=38;
    final int INT_DAT_HIS_SUB_X_ENF=39;
    final int INT_DAT_HIS_VAC_LIQ=40;
    final int INT_DAT_HIS_DEV_FON_RES=41;
    final int INT_DAT_HIS_STA_PRI=42;
    final int INT_DAT_HIS_IND_IESS=43;
    final int INT_DAT_HIS_DEV_IESS = 44;
    final int INT_DAT_HIS_MUL_TRA = 45;
    

    private ZafTblTot objTblTot;                                //JTable de totales.

    
    private ZafTblMod objTblModExc;
    private ZafTblPopMnu objTblPopMnu;
    /**
     * Creates new form ZafCon65_01
     */
    public ZafCon65_01(ZafParSis obj) {
              try{
             initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCon65_01(Frame parent, ZafParSis obj, String strCodEmp, String strCodPeriodo, String strNePer)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCon65= new ZafCon65(objParSis);
            cargarReg(strCodEmp,strCodPeriodo,strNePer);

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
       private boolean cargarReg(String strCodEmp, String strCodPeriodo, String strNePer) {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "",str_TipoCosto="";
        boolean blnRes = true;  
        int i;
        int intNumReg = 0;

        try {
            con= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            lblMsgSis.setText("Obteniendo datos...");
            vecDat = new Vector();

            if (con != null) {
                stm = con.createStatement(); 
                strSql = "";      
                strSql += " select a.co_tra, (b.tx_nom + ' ' + b.tx_ape) nombreTra,b.tx_ide,c.tx_codcos idCetnroCosto,c.tx_nom NomCentroCosto,(case d.co_pla when 1 then 'A' else 'V' end )TipoCosto, \n" +
                        "  nd_suetra AS nd_suetra, nd_comant AS nd_comant, nd_antqui AS nd_antqui,nd_imprenant AS nd_imprenant, nd_vacliq AS nd_vacliq, \n"+
                        " nd_reivacliq AS nd_reivacliq, nd_horext AS nd_horext, nd_otringgra AS nd_otringgra, nd_otringnogra AS nd_otringnogra,\n" +
                        "  nd_fonres AS nd_fonres, nd_13erarem AS nd_13erarem, nd_14erarem AS nd_14erarem, nd_alimen AS nd_alimen,\n" +
                        "  nd_reiimpren AS nd_reiimpren, nd_apoper AS nd_apoper, nd_alimen AS nd_alimen, nd_extsalcon AS nd_extsalcon,\n" +
                        "  nd_imprenpro AS nd_imprenpro, nd_desantqui AS nd_desantqui, nd_descomant AS nd_descomant, nd_prequi AS nd_prequi,\n" +
                        "  nd_prehip AS nd_prehip, nd_otrdes AS nd_otrdes, nd_antsue AS nd_antsue, nd_submat AS nd_submat,\n" +
                        "  nd_retjud AS nd_retjud, nd_preemp AS nd_preemp, nd_atrsalant AS nd_atrsalant, nd_mulfal AS nd_mulfal,\n" +
                        "  nd_ali AS nd_ali, nd_acudec3 AS nd_acudec3, nd_acudec4 AS nd_acudec4, nd_proapopat AS nd_proapopat,\n" +
                        "  nd_provac AS nd_provac, nd_fonresIees AS nd_fonresIees, nd_totrec2daqui AS  nd_totrec2daqui, nd_semint AS  nd_semint,nd_subxenf,nd_devfonres, nd_stapri,nd_indiess, nd_deviess, nd_multra \n" +
                        " from  tbh_rolpagexc a inner join tbm_tra b on a.co_tra=b.co_tra inner join tbm_cencos c on c.tx_codcos=b.tx_codcos and c.st_reg='A'\n" +
                        " inner join tbm_traemp d on a.co_tra=d.co_tra where d.co_emp=1 and a.ne_per=" + strNePer + " and a.co_per=" + strCodPeriodo  ;
                System.out.println("strSql \n" + strSql);
                rst = stm.executeQuery(strSql);

                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i = 0;
                
                //se contruye la tabla en base al periodo 15na o fin de mes
                
                if (strNePer.equals("1")){
                   configurarTblRegQuincena();
                   
                   if (rst.next()) {
                   vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LINEA, "");
                        vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                        vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra")); 
                        vecReg.add(INT_DAT_HIS_IDE_TRA, rst.getString("tx_ide"));
                        vecReg.add(INT_DAT_HIS_ID_CEN_COS, rst.getString("idCetnroCosto"));
                        vecReg.add(INT_DAT_HIS_NOM_CEN_COS, rst.getString("NomCentroCosto"));
                        
                        String strTipoCosto = rst.getString("TipoCosto");
                        str_TipoCosto="";
                        if (strTipoCosto.equals("A")){
                            str_TipoCosto="Administracion";                        
                        }else{
                            str_TipoCosto="Ventas";                                                   
                        }
                        vecReg.add(INT_DAT_HIS_TIP_CEN_COS, str_TipoCosto);
                        vecReg.add(INT_DAT_HIS_SUE_TRA,rst.getString("nd_suetra"));
                        vecReg.add(INT_DAT_HIS_COM_ANT, rst.getString("nd_comant"));
                        vecReg.add(INT_DAT_HIS_ANT_QUI, rst.getString("nd_antqui"));
                        vecReg.add(INT_DAT_HIS_IMP_REN_ANT, rst.getString("nd_imprenant"));
                        vecReg.add(INT_DAT_HIS_VAC_ANT, rst.getString("nd_vacliq"));

                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                }
                   
                } 
                else if (strNePer.equals("2")){
                    configurarTblRegFinDeMEs();
                   if (rst.next()) {
                   vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LINEA, "");
                        vecReg.add(INT_DAT_HIS_COD_TRA, rst.getString("co_tra"));
                        vecReg.add(INT_DAT_HIS_NOM_TRA, rst.getString("nombreTra")); 
                        vecReg.add(INT_DAT_HIS_IDE_TRA, rst.getString("tx_ide"));
                        vecReg.add(INT_DAT_HIS_ID_CEN_COS, rst.getString("idCetnroCosto"));
                        vecReg.add(INT_DAT_HIS_NOM_CEN_COS, rst.getString("NomCentroCosto"));
                        vecReg.add(INT_DAT_HIS_TIP_CEN_COS, str_TipoCosto);
                        vecReg.add(INT_DAT_HIS_SUE_TRA,rst.getString("nd_suetra"));
                        String strTipoCosto = rst.getString("TipoCosto");
                        str_TipoCosto="";
                        if (strTipoCosto.equals("A")){
                            str_TipoCosto="Administracion";                        
                        }else{
                            str_TipoCosto="Ventas";                                                   
                        }
                        
                        vecReg.add(INT_DAT_HIS_REI_VAC_LIQ, rst.getString("nd_reivacliq"));
                        vecReg.add(INT_DAT_HIS_HOR_EXT, rst.getString("nd_horext"));
                        vecReg.add(INT_DAT_HIS_OTR_ING_NO_GRA, rst.getString("nd_otringnogra"));
                        vecReg.add(INT_DAT_HIS_OTR_ING_GRA, rst.getString("nd_otringgra"));
                        vecReg.add(INT_DAT_HIS_FON_RES, rst.getString("nd_fonres"));
                        vecReg.add(INT_DAT_HIS_13ERA_REM, rst.getString("nd_13erarem"));
                        vecReg.add(INT_DAT_HIS_14ERA_REM, rst.getString("nd_14erarem"));
                        vecReg.add(INT_DAT_HIS_ALI_MEN, rst.getString("nd_alimen"));
                        vecReg.add(INT_DAT_HIS_REI_IMP_REN, rst.getString("nd_reiimpren"));
                        vecReg.add(INT_DAT_HIS_APO_PER, rst.getString("nd_apoper"));
                        vecReg.add(INT_DAT_HIS_EXT_SAL_CON, rst.getString("nd_extsalcon"));
                        vecReg.add(INT_DAT_HIS_IMP_REN_PRO, rst.getString("nd_imprenpro"));
                        vecReg.add(INT_DAT_HIS_DES_ANT_QUI, rst.getString("nd_desantqui"));
                        vecReg.add(INT_DAT_HIS_DES_COM_ANT, rst.getString("nd_descomant"));
                        vecReg.add(INT_DAT_HIS_PRE_QUI, rst.getString("nd_prequi"));
                        vecReg.add(INT_DAT_HIS_PRE_HIP, rst.getString("nd_prehip"));
                        vecReg.add(INT_DAT_HIS_OTR_DES, rst.getString("nd_otrdes"));
                        vecReg.add(INT_DAT_HIS_ANT_SUE, rst.getString("nd_antsue"));
                        vecReg.add(INT_DAT_HIS_SUB_MAT, rst.getString("nd_submat"));
                        vecReg.add(INT_DAT_HIS_RET_JUD, rst.getString("nd_retjud"));
                        vecReg.add(INT_DAT_HIS_PRE_EMP, rst.getString("nd_preemp"));
                        vecReg.add(INT_DAT_HIS_ATR_SAL_ANT, rst.getString("nd_atrsalant"));
                        vecReg.add(INT_DAT_HIS_MUL_FAL, rst.getString("nd_mulfal"));
                        vecReg.add(INT_DAT_HIS_ALI, rst.getString("nd_ali"));
                        vecReg.add(INT_DAT_HIS_ACU_DEC_3, rst.getString("nd_acudec3"));
                        vecReg.add(INT_DAT_HIS_ACU_DEC_4, rst.getString("nd_acudec4"));
                        vecReg.add(INT_DAT_HIS_PRO_APO_PAT, rst.getString("nd_proapopat"));
                        vecReg.add(INT_DAT_HIS_PRO_VAC, rst.getString("nd_provac"));
                        vecReg.add(INT_DAT_HIS_FON_RES_IEES, rst.getString("nd_fonresIees"));
                        vecReg.add(INT_DAT_HIS_TOT_REC_2DA_QUI, rst.getString("nd_totrec2daqui"));
                        vecReg.add(INT_DAT_HIS_SEM_INT, rst.getString("nd_semint"));
                        vecReg.add(INT_DAT_HIS_SUB_X_ENF, rst.getString("nd_subxenf"));
                        vecReg.add(INT_DAT_HIS_VAC_LIQ, rst.getString("nd_vacliq"));
                        vecReg.add(INT_DAT_HIS_DEV_FON_RES, rst.getString("nd_devfonres"));
                        vecReg.add(INT_DAT_HIS_STA_PRI, rst.getString("nd_stapri"));
                        vecReg.add(INT_DAT_HIS_IND_IESS, rst.getString("nd_indiess"));
                        vecReg.add(INT_DAT_HIS_DEV_IESS, rst.getString("nd_deviess"));
                        vecReg.add(INT_DAT_HIS_MUL_TRA, rst.getString("nd_multra"));
                        
                        
                        
                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                }
                        
                }
                //Asignar vectores al modelo.
                objTblModExc.setData(vecDat);
                tblDatos.setModel(objTblModExc);
                objTblTot.calcularTotales(); 

                lblMsgSis.setText("Listo");
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }
    
private boolean configurarTblRegQuincena()
    {          
        objUti = new ZafUtil();
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA, "");
            vecCab.add(INT_DAT_HIS_COD_TRA,"Cod.Tra.");
            vecCab.add(INT_DAT_HIS_NOM_TRA,"Nom.Tra.");
            vecCab.add(INT_DAT_HIS_IDE_TRA,"Identificacion");
            vecCab.add(INT_DAT_HIS_ID_CEN_COS,"Id.Centro.Costo");
            vecCab.add(INT_DAT_HIS_NOM_CEN_COS,"Nom.Centro.Costo");
            vecCab.add(INT_DAT_HIS_TIP_CEN_COS,"Tip.Centro.Costo");
            vecCab.add(INT_DAT_HIS_SUE_TRA,"Sueldo");
            vecCab.add(INT_DAT_HIS_COM_ANT,"Comision Anticip.");
            vecCab.add(INT_DAT_HIS_ANT_QUI,"Anticip. Quincena");
            vecCab.add(INT_DAT_HIS_IMP_REN_ANT,"Imp. Renta Anticip.");
            vecCab.add(INT_DAT_HIS_VAC_ANT,"Vac. Anticip.");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModExc=new ZafTblMod();
            objTblModExc.setHeader(vecCab);
            tblDatos.setModel(objTblModExc);
            //Configurar JTable: Establecer tipo de selección.
            tblDatos.setRowSelectionAllowed(true);
            tblDatos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatos);   
            objColNum = new ZafColNumerada(tblDatos, INT_TBL_DAT_LINEA);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatos.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);            
            tcmAux.getColumn(INT_DAT_HIS_COD_TRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_DAT_HIS_NOM_TRA).setPreferredWidth(250);
            tcmAux.getColumn(INT_DAT_HIS_IDE_TRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_DAT_HIS_ID_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_NOM_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_TIP_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_SUE_TRA).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_COM_ANT).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ANT_QUI).setPreferredWidth(120);
            tcmAux.getColumn(INT_DAT_HIS_IMP_REN_ANT).setPreferredWidth(120);
            tcmAux.getColumn(INT_DAT_HIS_VAC_ANT).setPreferredWidth(100);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
            
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_HIS_SUE_TRA).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_COM_ANT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_ANT_QUI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_IMP_REN_ANT).setCellRenderer(objTblCelRenLbl);       
            tcmAux.getColumn(INT_DAT_HIS_VAC_ANT).setCellRenderer(objTblCelRenLbl);          
            
           
            objTblCelRenLbl = null;
            
          
            //Configurar JTable: Establecer el modo de operación.
            objTblModExc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            int intCol[]={INT_DAT_HIS_SUE_TRA,INT_DAT_HIS_COM_ANT,INT_DAT_HIS_ANT_QUI,INT_DAT_HIS_IMP_REN_ANT,INT_DAT_HIS_VAC_ANT};
            objTblTot=new ZafTblTot( spndatos,spnTot, tblDatos, tblTot, intCol);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


private boolean configurarTblRegFinDeMEs()
    {          
        objUti = new ZafUtil();
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LINEA, "");
            vecCab.add(INT_DAT_HIS_COD_TRA,"Cod.Tra.");
            vecCab.add(INT_DAT_HIS_NOM_TRA,"Nom.Tra.");
            vecCab.add(INT_DAT_HIS_IDE_TRA,"Identificacion");
            vecCab.add(INT_DAT_HIS_ID_CEN_COS,"Id.Centro.Costo");
            vecCab.add(INT_DAT_HIS_NOM_CEN_COS,"Nom.Centro.Costo");
            vecCab.add(INT_DAT_HIS_TIP_CEN_COS,"Tip.Centro.Costo");
            vecCab.add(INT_DAT_HIS_SUE_TRA,"Sueldo");  
            vecCab.add(INT_DAT_HIS_REI_VAC_LIQ,"ReVac. Liq. Ant.");
            vecCab.add(INT_DAT_HIS_HOR_EXT,"Horas Extras");
            vecCab.add(INT_DAT_HIS_OTR_ING_NO_GRA,"Otros Ing. NoGrav.");
            vecCab.add(INT_DAT_HIS_OTR_ING_GRA,"Otros Ing. Grav.");
            vecCab.add(INT_DAT_HIS_FON_RES,"Fondos Reserv.");
            vecCab.add(INT_DAT_HIS_13ERA_REM,"Pag. Men. 3raRem.");
            vecCab.add(INT_DAT_HIS_14ERA_REM,"Pag. Men. 4raRem.");
            vecCab.add(INT_DAT_HIS_ALI_MEN,"Alimentacion Mens.");
            vecCab.add(INT_DAT_HIS_REI_IMP_REN,"Reint. Imp. Rent.");
            vecCab.add(INT_DAT_HIS_APO_PER,"Aporte Pers.");
            vecCab.add(INT_DAT_HIS_EXT_SAL_CON,"Ext. Salud Conyugal");
            vecCab.add(INT_DAT_HIS_IMP_REN_PRO,"Imp. renta Proy.");
            vecCab.add(INT_DAT_HIS_DES_ANT_QUI,"Desc. Ant. Quinc.");
            vecCab.add(INT_DAT_HIS_DES_COM_ANT,"Desc. Com. Antic");
            vecCab.add(INT_DAT_HIS_PRE_QUI,"Prest. Quiro");
            vecCab.add(INT_DAT_HIS_PRE_HIP,"Prest. Hipo");
            vecCab.add(INT_DAT_HIS_OTR_DES,"Otros Desc.");
            vecCab.add(INT_DAT_HIS_ANT_SUE,"Anticip. Sueldo");
            vecCab.add(INT_DAT_HIS_SUB_MAT,"Sub. Materno");
            vecCab.add(INT_DAT_HIS_RET_JUD,"Ret. Judicial");
            vecCab.add(INT_DAT_HIS_PRE_EMP,"Prest. Empresa");
            vecCab.add(INT_DAT_HIS_ATR_SAL_ANT,"Atrasos/Salid Anticip.");
            vecCab.add(INT_DAT_HIS_MUL_FAL,"Multas/Faltas");
            vecCab.add(INT_DAT_HIS_ALI,"Alimentacion");
            vecCab.add(INT_DAT_HIS_ACU_DEC_3,"Dec. 3ro Acum.");
            vecCab.add(INT_DAT_HIS_ACU_DEC_4,"Dec. 4ro Acum.");
            vecCab.add(INT_DAT_HIS_PRO_APO_PAT,"Aporte Patronal");
            vecCab.add(INT_DAT_HIS_PRO_VAC,"Provision Vac.");
            vecCab.add(INT_DAT_HIS_FON_RES_IEES,"Fondo Reserv. Iess");
            vecCab.add(INT_DAT_HIS_TOT_REC_2DA_QUI,"Tot. Rec 2daQui");
            vecCab.add(INT_DAT_HIS_SEM_INT,"Semana Integral");
            vecCab.add(INT_DAT_HIS_SUB_X_ENF,"Sub.xEnfermedad");
            vecCab.add(INT_DAT_HIS_VAC_LIQ,"Vac. Liq.");
            vecCab.add(INT_DAT_HIS_DEV_FON_RES,"Dev.FonRes");
            vecCab.add(INT_DAT_HIS_STA_PRI,"Sta. Priscila");
            vecCab.add(INT_DAT_HIS_IND_IESS,"Indemnizaciones IESS");
            vecCab.add(INT_DAT_HIS_DEV_IESS,"Dev. Iess Pasantes");
            vecCab.add(INT_DAT_HIS_MUL_TRA,"Multas transito");
            
            

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModExc=new ZafTblMod();
            objTblModExc.setHeader(vecCab);
            tblDatos.setModel(objTblModExc);
            //Configurar JTable: Establecer tipo de selección.
            tblDatos.setRowSelectionAllowed(true);
            tblDatos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatos);
            objColNum = new ZafColNumerada(tblDatos, INT_TBL_DAT_LINEA);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatos.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);            
            tcmAux.getColumn(INT_DAT_HIS_COD_TRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_DAT_HIS_NOM_TRA).setPreferredWidth(250);
            tcmAux.getColumn(INT_DAT_HIS_IDE_TRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_DAT_HIS_ID_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_NOM_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_TIP_CEN_COS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_SUE_TRA).setPreferredWidth(100);   
            
            tcmAux.getColumn(INT_DAT_HIS_REI_VAC_LIQ).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_HOR_EXT).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_OTR_ING_NO_GRA).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_OTR_ING_GRA).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_FON_RES).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_13ERA_REM).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_14ERA_REM).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_ALI_MEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_REI_IMP_REN).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_APO_PER).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_EXT_SAL_CON).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_IMP_REN_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_DES_ANT_QUI).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_DES_COM_ANT).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_PRE_QUI).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_PRE_HIP).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_OTR_DES).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ANT_SUE).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_SUB_MAT).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_RET_JUD).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_PRE_EMP).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ATR_SAL_ANT).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_MUL_FAL).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ALI).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ACU_DEC_3).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_ACU_DEC_4).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_PRO_APO_PAT).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_PRO_VAC).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_FON_RES_IEES).setPreferredWidth(110);    
            tcmAux.getColumn(INT_DAT_HIS_TOT_REC_2DA_QUI).setPreferredWidth(110);
            tcmAux.getColumn(INT_DAT_HIS_SEM_INT).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_SUB_X_ENF).setPreferredWidth(110); 
            tcmAux.getColumn(INT_DAT_HIS_VAC_LIQ).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_DEV_FON_RES).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_STA_PRI).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_IND_IESS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_DEV_IESS).setPreferredWidth(100);
            tcmAux.getColumn(INT_DAT_HIS_MUL_TRA).setPreferredWidth(100);
                         
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_DAT_HIS_SUE_TRA).setCellRenderer(objTblCelRenLbl);    
            tcmAux.getColumn(INT_DAT_HIS_REI_VAC_LIQ).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_HOR_EXT).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_DAT_HIS_OTR_ING_NO_GRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_OTR_ING_GRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_FON_RES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_13ERA_REM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_14ERA_REM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_ALI_MEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_REI_IMP_REN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_APO_PER).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_EXT_SAL_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_IMP_REN_PRO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_DES_ANT_QUI).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_DAT_HIS_DES_COM_ANT).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_PRE_QUI).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_PRE_HIP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_OTR_DES).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_ANT_SUE).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_SUB_MAT).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_RET_JUD).setCellRenderer(objTblCelRenLbl);          
            
            tcmAux.getColumn(INT_DAT_HIS_PRE_EMP).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_ATR_SAL_ANT).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_MUL_FAL).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_ALI).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_ACU_DEC_3).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_ACU_DEC_4).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_PRO_APO_PAT).setCellRenderer(objTblCelRenLbl);          
            
            tcmAux.getColumn(INT_DAT_HIS_PRO_VAC).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_FON_RES_IEES).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_TOT_REC_2DA_QUI).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_SEM_INT).setCellRenderer(objTblCelRenLbl);    
            tcmAux.getColumn(INT_DAT_HIS_SUB_X_ENF).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_DAT_HIS_VAC_LIQ).setCellRenderer(objTblCelRenLbl);  
            tcmAux.getColumn(INT_DAT_HIS_DEV_FON_RES).setCellRenderer(objTblCelRenLbl);  
            tcmAux.getColumn(INT_DAT_HIS_STA_PRI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_IND_IESS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_DEV_IESS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_DAT_HIS_MUL_TRA).setCellRenderer(objTblCelRenLbl);
 
            
            objTblCelRenLbl = null;
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDatos.getTableHeader().addMouseMotionListener(new ZafVen42.ZafMouMotAdaLoc());
          
            //Configurar JTable: Establecer el modo de operación.
            objTblModExc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null; 
            int intCol[]={INT_DAT_HIS_SUE_TRA,INT_DAT_HIS_REI_VAC_LIQ,INT_DAT_HIS_HOR_EXT,INT_DAT_HIS_OTR_ING_NO_GRA,INT_DAT_HIS_OTR_ING_GRA,INT_DAT_HIS_FON_RES,INT_DAT_HIS_13ERA_REM,INT_DAT_HIS_14ERA_REM,INT_DAT_HIS_ALI_MEN,INT_DAT_HIS_REI_IMP_REN,INT_DAT_HIS_APO_PER,INT_DAT_HIS_EXT_SAL_CON,INT_DAT_HIS_IMP_REN_PRO,INT_DAT_HIS_DES_ANT_QUI,INT_DAT_HIS_DES_COM_ANT,INT_DAT_HIS_PRE_QUI,INT_DAT_HIS_PRE_HIP,INT_DAT_HIS_OTR_DES,INT_DAT_HIS_ANT_SUE,INT_DAT_HIS_SUB_MAT,INT_DAT_HIS_RET_JUD,INT_DAT_HIS_PRE_EMP,INT_DAT_HIS_ATR_SAL_ANT,INT_DAT_HIS_MUL_FAL,INT_DAT_HIS_ALI,INT_DAT_HIS_ACU_DEC_3,INT_DAT_HIS_ACU_DEC_4,INT_DAT_HIS_PRO_APO_PAT,INT_DAT_HIS_PRO_VAC,INT_DAT_HIS_FON_RES_IEES,INT_DAT_HIS_TOT_REC_2DA_QUI,INT_DAT_HIS_SEM_INT,INT_DAT_HIS_SUB_X_ENF,INT_DAT_HIS_VAC_LIQ,INT_DAT_HIS_DEV_FON_RES,INT_DAT_HIS_STA_PRI,INT_DAT_HIS_IND_IESS,INT_DAT_HIS_DEV_IESS,INT_DAT_HIS_MUL_TRA};
            objTblTot=new ZafTblTot( spndatos,spnTot, tblDatos, tblTot, intCol);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        spndatos = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setPreferredSize(new java.awt.Dimension(900, 478));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Informacion cargada desde Zafiro");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        spndatos.setViewportView(tblDatos);

        jPanel4.add(spndatos, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 20));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        jPanel4.add(spnTot, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(700, 20));
        jPanel1.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lblMsgSis.setPreferredSize(new java.awt.Dimension(610, 20));
        jPanel1.add(lblMsgSis, java.awt.BorderLayout.WEST);

        jPanel6.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(150, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel3.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spndatos;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
}
