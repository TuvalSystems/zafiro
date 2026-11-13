/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenAna;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jmarin
 */
public class ZafGenAna {
 
    private ZafParSis objParSis;
    private ZafUtil objUti;
    
    private static final int INT_ARL_COD_CTA=0;
    private static final int INT_ARL_REG_DIA=1; 
    private static final int INT_ARL_MON_ANA=2;
    private static final int INT_ARL_COD_ANA=3; 
    private static final int INT_ARL_TIP_MOV=4;
    private java.awt.Component jifCnt;
    private String strSql="", strVer = " v 0.04";  // JM 27/Sep/2024
    private int intCodAna=-1;
    
    
    /**
     * 
     * @param obj
     * @param componente 
     */
     public ZafGenAna(ZafParSis obj, java.awt.Component componente){
        try{
            System.out.println(strVer);
            this.objParSis = (ZafParSis) obj.clone();
            jifCnt=componente;
            objUti = new ZafUtil();
            System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch(Exception  e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        } 
    }
    
     
     
    /**
     * Clase creada para generar Analiticas
     * 
     * @param conn
     * @param intCodSeg
     * @return 
     */
        

    public boolean agregarAnaliticas(java.sql.Connection conExt, String strLote, int intCodDia,  ArrayList arlDatAna){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(inicioProcesoCreacionAnaliticas(conExt, strLote, intCodDia, arlDatAna)){
                    blnRes=true;
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param conExt
     * @param intCodDia
     * @param arlDatAna
     * @return 
     */
    
    private boolean inicioProcesoCreacionAnaliticas(java.sql.Connection conExt, String strLote, int intCodDia, ArrayList arlDatAna){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(arlDatAna.size()>0){//No existen datos de las analiticas 
                    if(getCodAnaliticas(conExt, intCodDia, arlDatAna)){
                        if(insertar_AAG10001(conExt, strLote, intCodDia, arlDatAna)){
                            if(insertar_AAG10002_AAG10003(conExt, strLote, intCodDia, arlDatAna)){
                                
                                blnRes=true;
                            }
                            else{
                                mostrarMsgInf("Error al Insertar insertar_AAG10002_AAG10003...");  
                            }
                        }
                        else{
                            mostrarMsgInf("Error al Insertar insertar_AAG10001...");  
                        }
                    }
                    else{
                        mostrarMsgInf("Error al obtener datos de analiticas...");  
                    }
                }
                else{
                    mostrarMsgInf("No existen datos de Analiticas...");  
                }
                
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
     
    /**
     * 
     * @param conExt
     * @param strLote
     * @param intCodDia
     * @param arlDatAna
     * @return 
     */
  
    private boolean update_Percent_AAG10002(java.sql.Connection conExt, String strLote, int intCodDia){
            boolean blnRes=true;
             
             
            return blnRes;

    }
    
    private boolean insertar_AAG10002_AAG10003(java.sql.Connection conExt, String strLote, int intCodDia, ArrayList arlDatAna){
         boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc, stmIns;
        String strIns="", strSiTieneAnaliticas="0";
        int aaGLWorkDistID=1, intDistID=1, contporc=0 ,intcontana=0,intultreg=1;
        double dblValor=0.00, assper=0.00;
        String strInput="";
         // Crear ArrayLists para arlDimencion y arlAnalitica
        ArrayList<Integer> arlDimencion = new ArrayList<>();
        ArrayList<Integer> arlAnalitica = new ArrayList<>();
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                stmIns=conExt.createStatement();
                
                
                strSql="";   
                strSql+=" SELECT a1.aaGLWorkHdrID, a1.aaGLWorkDistID, a1.ACTINDX, a1.ACCTTYPE, a1.DEBITAMT, a1.CRDTAMNT, a1.SQNCLINE, COUNT(a6.aaTrxDimID) as dimenciones ";
                strSql+=" FROM TUVAL.dbo.AAG10001 AS a1  ";
                strSql+=" INNER JOIN TUVAL.dbo.AAG00200 as a5 ON (a1.ACTINDX=a5.ACTINDX) \n";
                strSql+=" LEFT OUTER JOIN TUVAL.dbo.AAG00202 as a6 ON (a5.aaAcctClassID=a6.aaAcctClassID) \n";
                strSql+=" WHERE a1.aaGLWorkHdrID="+intCodAna+"   ";
                strSql+=" GROUP BY a1.aaGLWorkHdrID, a1.aaGLWorkDistID, a1.ACTINDX, a1.ACCTTYPE, a1.DEBITAMT, a1.CRDTAMNT, a1.SQNCLINE  ";
                strSql+=" ORDER BY a1.aaGLWorkDistID ";
                System.out.println("Armando las cadenas.. \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(rstLoc.getInt("dimenciones")>0){
                        contporc=0;
                        intcontana =0; intultreg=1;
                        for(int i=0;i<arlDatAna.size();i++){ /*Quiero saber si una cuenta tiene una analitica... */
                            if( (rstLoc.getInt("ACTINDX")==objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA) ) && 
                                (rstLoc.getInt("SQNCLINE")==objUti.getIntValueAt(arlDatAna, i, INT_ARL_REG_DIA) ) ){
                                intcontana++;

                                dblValor = rstLoc.getDouble("DEBITAMT") +  rstLoc.getDouble("CRDTAMNT"); 
                                assper=(( objUti.getDoubleValueAt(arlDatAna, i, INT_ARL_MON_ANA)*100 /  dblValor )  * 100);
                                contporc +=assper;
                             //   System.out.println("antes" + objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA) + " despues " + (objUti.getIntValueAt(arlDatAna, (i+1), INT_ARL_COD_CTA)));
                               
                                if (i < arlDatAna.size()-1) {
                                    if( (objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA)) == (objUti.getIntValueAt(arlDatAna, (i+1), INT_ARL_COD_CTA))){ // Ultimo registro 
                                        intultreg++;
                                    }
                                }
                              
                                if (i < arlDatAna.size()-1) {
                                    if( (objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA)) != (objUti.getIntValueAt(arlDatAna, (i+1), INT_ARL_COD_CTA))){ // Ultimo registro 
                                        if (intcontana   == intultreg){   
                                        if (contporc>10000){
                                            assper=contporc-10000;
                                        }else{
                                            assper=10000-(contporc - assper);
                                        }
                                        }
                                    }
                                }

                                strIns+=" INSERT INTO TUVAL.dbo.AAG10002 (aaGLWorkHdrID,aaGLWorkDistID , aaGLWorkAssignID , DEBITAMT , CRDTAMNT , ORDBTAMT , ORCRDAMT , aaAssignedPercent )  ";
                                strIns+=" VALUES ("+intCodAna+", "+rstLoc.getInt("aaGLWorkDistID")+","+(intDistID)+",";

                                strIns+=objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV)=="D"?objUti.getDoubleValueAt(arlDatAna, i, INT_ARL_MON_ANA):"0";
                                strIns+=" ,";
                                strIns+=objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV)=="H"?objUti.getDoubleValueAt(arlDatAna, i, INT_ARL_MON_ANA):"0";
                                strIns+=" ,";
                                strIns+=objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV)=="D"?objUti.getDoubleValueAt(arlDatAna, i, INT_ARL_MON_ANA):"0";
                                strIns+=" ,";
                                strIns+=objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV)=="H"?objUti.getDoubleValueAt(arlDatAna, i, INT_ARL_MON_ANA):"0";
                                strIns+=" ,";
                                strIns+=(assper ) ;
                                strIns+=" );";
                                
                                // Eliminar el último carácter "|" si existe
                                if (objUti.getStringValueAt(arlDatAna, i, INT_ARL_COD_ANA).endsWith("|")) { 
                                    strInput = objUti.getStringValueAt(arlDatAna, i, INT_ARL_COD_ANA).substring(0, objUti.getStringValueAt(arlDatAna, i, INT_ARL_COD_ANA).length() - 1);
                                }

                                // Dividir la cadena por el delimitador "|"
                                String[] pairs = strInput.split("\\|");

                                arlDimencion.clear();
                                arlAnalitica.clear();

                                // Procesar cada par
                                for (String pair : pairs) {
                                    // Dividir por el delimitador "-"
                                    String[] values = pair.split("-");
                                    // Añadir los valores a los ArrayLists
                                    arlDimencion.add(Integer.parseInt(values[0]));
                                    arlAnalitica.add(Integer.parseInt(values[1]));
                                }
                                if(rstLoc.getInt("dimenciones")>0){
                                    for (int s = 0; s < arlDimencion.size(); s++) {
            //                            System.out.println(arlDimencion.get(s) + "\t" + arlAnalitica.get(s));
                                        strIns+=" INSERT INTO TUVAL.dbo.AAG10003 (aaGLWorkHdrID, aaGLWorkDistID, aaGLWorkAssignID, aaTrxDimID, aaTrxCodeID) ";
                                        strIns+=" VALUES ("+intCodAna+", "+rstLoc.getInt("aaGLWorkDistID")+","+intDistID+","+(arlDimencion.get(s))+","+arlAnalitica.get(s)+" ); ";
                                    }
                                }
                                intDistID++;    
                            }
                        }
                    }
                    else{ 
                        strIns+=" INSERT INTO TUVAL.dbo.AAG10002 (aaGLWorkHdrID,aaGLWorkDistID , aaGLWorkAssignID , DEBITAMT , CRDTAMNT , ORDBTAMT , ORCRDAMT , aaAssignedPercent )  ";
                        strIns+=" VALUES ("+intCodAna+", "+rstLoc.getInt("aaGLWorkDistID")+","+(intDistID)+",";
                        strIns+=rstLoc.getDouble("DEBITAMT");
                        strIns+=" ,";
                        strIns+=rstLoc.getDouble("CRDTAMNT");
                        strIns+=" ,";
                        strIns+=rstLoc.getDouble("DEBITAMT");
                        strIns+=" ,";
                        strIns+=rstLoc.getDouble("CRDTAMNT");
                        strIns+=" ,";
                        strIns+="10000 );";
                        intDistID++;
                    }
                        
                    
                    
                }
                
                System.out.println("strIns>>>>"+strIns);
                stmIns.executeUpdate(strIns);
                
                rstLoc.close();
                rstLoc=null;
                    
                stmIns.close();
                stmIns=null;
                
                stmLoc.close();
                stmLoc=null;
                
                
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
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
        oppMsg.showMessageDialog(jifCnt,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
      
     
    
    /**
     * 
     * @param conExt
     * @param intCodDia
     * @return 
     */
    private boolean insertar_AAG10001(java.sql.Connection conExt, String strLote, int intCodDia, ArrayList arlDatAna){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc, stmIns;
        String strIns="", strSiTieneAnaliticas="0";
        int aaGLWorkDistID=1;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                stmIns=conExt.createStatement();
                strSql="";   
                strSql+=" SELECT BACHNUMB, JRNENTRY, SQNCLINE, ACTINDX, ACCTTYPE, DEBITAMT, CRDTAMNT, DECPLACS  ";
                strSql+=" FROM TUVAL.dbo.GL10001 WHERE JRNENTRY in ("+intCodDia+") ORDER BY SQNCLINE ";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    for(int i=0;i<arlDatAna.size();i++){ /*Quiero saber si una cuenta tiene una analitica... */
                        if(rstLoc.getInt("ACTINDX")==objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA)){
                            strSiTieneAnaliticas="1";
                        }
                    }
                    
                    strIns+="INSERT INTO TUVAL.dbo.AAG10001 (aaGLWorkHdrID, aaGLWorkDistID, aaBrowseType, INTERID, ACTINDX, DEBITAMT, CRDTAMNT, ORDBTAMT,";
                    strIns+="                                ORCRDAMT, ACCTTYPE, SQNCLINE, DECPLACS, CURNCYID, CURRNIDX, aaCopyStatus, aaWinWasOpen, aaChangeDate, aaChangeTime )  ";
                    strIns+="VALUES ( "+intCodAna+", "+aaGLWorkDistID+", "+strSiTieneAnaliticas+", 'TUVAL', "+rstLoc.getString("ACTINDX")+", "+rstLoc.getDouble("DEBITAMT")+", "+rstLoc.getDouble("CRDTAMNT")+", ";
                    strIns+=" "+rstLoc.getDouble("DEBITAMT")+", "+rstLoc.getDouble("CRDTAMNT")+", "+rstLoc.getInt("ACCTTYPE")+", "+rstLoc.getInt("SQNCLINE")+", "+rstLoc.getInt("DECPLACS")+", ";
                    strIns+=" 'DOLAR', 1000,  8, "+strSiTieneAnaliticas+" , CAST(GETDATE() AS DATE), CONVERT(VARCHAR(8), GETDATE(), 108)  ); " ;
                    
                    aaGLWorkDistID++;
                    strSiTieneAnaliticas="0";
                }
                
//                System.out.println("insertar_AAG10001 " + strIns);
                stmIns.executeUpdate(strIns);
                
                stmIns.close();
                stmIns=null;
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            // System.err.println("ERROR SQL ingreso/egreso: " + e.toString());
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    } 
    
    
    private ArrayList arlRegDia, arlDatDia;
    
    private final int INT_CON_COD_DIA=0;
    private final int INT_CON_COD_CTA=1;
    private final int INT_CON_COD_LNA=2;
    private final int INT_CON_MON_DBE=3;
    private final int INT_CON_MON_HBR=4;
    private final int INT_CON_TIP_MOV=5;
    
    
    /**
     * Obtener el codigo de la analitica con el que vamos a trabajar 
     * @param conExt
     * @param intCodDia
     * @return 
     */
    private boolean getCodAnaliticas(java.sql.Connection conExt, int intCodDia, ArrayList arlDatAna){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        int intRegDia=-1, intSecDia=-1, intTablaProcesada=0, intCodCta=-1;
        double dblAcumCont=0;
        String strTipMov="-1", strAux="-1";
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+="SELECT  * FROM TUVAL.dbo.AAG10000 WHERE  JRNENTRY in ("+intCodDia+") ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodAna = rstLoc.getInt("aaGLWorkHdrID");
                }
                else{
                    blnRes=false;
                }
                
                System.out.println("tabla que me llega: \n" + arlDatAna.toString());
                
                arlDatDia = new ArrayList();
                
                 

//                strSql = "";
//                strSql += " SELECT BACHNUMB, JRNENTRY, SQNCLINE, ACTINDX, ACCTTYPE, DEBITAMT, CRDTAMNT, DECPLACS  "; 
//                strSql += " FROM TUVAL.dbo.GL10001 WHERE JRNENTRY in (" + intCodDia + ") ORDER BY SQNCLINE ";
//                rstLoc = stmLoc.executeQuery(strSql);
//
//                while (rstLoc.next()) {
//                    // Obtenemos el valor de la cuenta del diario actual
//                    int actIndx = rstLoc.getInt("ACTINDX");
//
//                    // Recorremos el ArrayList 'arlDatAna' para verificar la cuenta
//                    for (int i = intTablaProcesada; i < arlDatAna.size(); i++) {
//                        // Verificamos si la cuenta del diario coincide con la del ArrayList
//                        if (actIndx == objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA)) {
//                            intRegDia = objUti.getIntValueAt(arlDatAna, i, INT_ARL_REG_DIA);
//                            strTipMov = objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV);
//
//                            // Recorremos nuevamente 'arlDatAna' para acumular los montos analíticos
//                            for (int j = 0; j < arlDatAna.size(); j++) {
//                                if (intRegDia == objUti.getIntValueAt(arlDatAna, j, INT_ARL_REG_DIA) && strTipMov.equals(objUti.getStringValueAt(arlDatAna, j, INT_ARL_TIP_MOV))) {
//                                    dblAcumCont += objUti.redondear(objUti.getDoubleValueAt(arlDatAna, j, INT_ARL_MON_ANA),objParSis.getDecimalesMostrar() );
//                                }
//                            }
//
//                            // Comprobamos si el valor acumulado es igual al total de la cuenta
//                            if (objUti.redondear(dblAcumCont, 2) ==  (rstLoc.getDouble("DEBITAMT") + rstLoc.getDouble("CRDTAMNT"))) {
//
//                                // Obtenemos la secuencia del diario
//                                int secDia = rstLoc.getInt("SQNCLINE");
//
//                                // Actualizamos el 'INT_ARL_REG_DIA' en el ArrayList
//                                for (int j = intTablaProcesada; j < arlDatAna.size(); j++) {
//                                    if (objUti.getIntValueAt(arlDatAna, i, INT_ARL_REG_DIA) ==  objUti.getIntValueAt(arlDatAna, j, INT_ARL_REG_DIA)) { 
//                                        objUti.setIntValueAt(arlDatAna, j, INT_ARL_REG_DIA, secDia);
//                                    }
//                                }
//                            }
//
//                            // Reiniciamos el acumulador
//                            dblAcumCont = 0;
//                        }
//                    }
//
//                    // Avanzamos al siguiente registro en el ArrayList
//                    intTablaProcesada++;
//                }

                strSql = "";
                strSql += " SELECT BACHNUMB, JRNENTRY, SQNCLINE, ACTINDX, ACCTTYPE, DEBITAMT, CRDTAMNT, DECPLACS  "; 
                strSql += " FROM TUVAL.dbo.GL10001 WHERE JRNENTRY in (" + intCodDia + ") ORDER BY SQNCLINE ";
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    arlRegDia = new ArrayList();
                    arlRegDia.add(INT_CON_COD_DIA, rstLoc.getInt("JRNENTRY"));
                    arlRegDia.add(INT_CON_COD_CTA, rstLoc.getInt("ACTINDX"));
                    arlRegDia.add(INT_CON_COD_LNA, rstLoc.getInt("SQNCLINE"));
                    arlRegDia.add(INT_CON_MON_DBE, rstLoc.getDouble("DEBITAMT"));
                    arlRegDia.add(INT_CON_MON_HBR, rstLoc.getDouble("CRDTAMNT"));
                    if(rstLoc.getDouble("DEBITAMT")>0){
                        strAux="D";
                    }
                    else{
                        strAux="H";
                    }
                    arlRegDia.add(INT_CON_TIP_MOV, strAux);
                    arlDatDia.add(arlRegDia);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;

                for (int i = 0; i < arlDatAna.size(); i++) {
                    intCodCta = objUti.getIntValueAt(arlDatAna, i, INT_ARL_COD_CTA);
                    strTipMov = objUti.getStringValueAt(arlDatAna, i, INT_ARL_TIP_MOV);
                    for(int J=0;J<arlDatDia.size();J++){
                        if( (intCodCta==objUti.getIntValueAt(arlDatDia, J, INT_CON_COD_CTA) ) &&  strTipMov.equals( objUti.getStringValueAt(arlDatDia, J, INT_CON_TIP_MOV) ) ){
                            objUti.setIntValueAt(arlDatAna, i, INT_ARL_REG_DIA, objUti.getIntValueAt(arlDatDia, J, INT_CON_COD_LNA));
                            break;
                        }
                    }
                }
                
                 
                System.out.println("tabla final: \n" + arlDatAna.toString());

                
                
                
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            // System.err.println("ERROR SQL ingreso/egreso: " + e.toString());
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
            
    
    
    
    
    
    
    
}
