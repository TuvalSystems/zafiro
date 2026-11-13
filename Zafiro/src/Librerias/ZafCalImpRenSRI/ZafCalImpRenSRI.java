/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafCalImpRenSRI;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author svillalta
 */
public class ZafCalImpRenSRI {
 
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ArrayList arlReg , arlDatDatos;
    private final int INT_ARL_BAS_IMP_GRA=0;
    private final int INT_ARL_IMP_REN_CAU=1;
    private final int INT_ARL_REB_GAS_PER=2;
    private final int INT_ARL_IMP_DES_REB_GAS_PER=3;
    private final int INT_ARL_REN_RET_TRA=4;
    
    private java.awt.Component jifCnt;
    private String strSql="",strSql1="", strVer = " v 0.01";  // SV 10/Ene/2025 
    /**
     * 
     * @param obj
     * @param componente 
     */
     public ZafCalImpRenSRI(ZafParSis obj, java.awt.Component componente){
        try{
          //  System.out.println(strVer);
            this.objParSis = (ZafParSis) obj.clone();
            jifCnt=componente;
            objUti = new ZafUtil();
          //  System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch(Exception  e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        } 
    }
    
     public ArrayList arlPasar;
      
     
     
    /**
     * Clase creada para generar Analiticas
     * 
     * @param conn
     * @param intCodSeg
     * @return 
     */
        

    public boolean CalcularImpRentSRI(java.sql.Connection conExt, double totIng, double utirec, double suelanutra, int anio,int carfam, double totgasper, double iess){
        boolean blnRes=true;
        try{ 
            if(conExt!=null){  
                if (!inicioProcesoCalcularImpRentSRI(conExt, totIng,utirec,suelanutra ,anio,carfam,totgasper,iess)){
                        blnRes=false;                    
                } ;
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
    
    private boolean inicioProcesoCalcularImpRentSRI(java.sql.Connection conExt , double totIngtra, double utirectra, double suelanutra, int anio,int carfam, double totgasper, double iess){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                if (!getValRetEmp(conExt,totIngtra,utirectra,suelanutra,anio,carfam,totgasper,iess)){
                    blnRes=false;
                };   
            }
        }
        catch(Exception  e){
           // blnRes=false;
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
    
    
    private boolean getValRetEmp(java.sql.Connection conExt,double totIngtra, double utirectra, double suelanutra, int anio,int carfam, double totgasper, double iess){
       boolean blnRes=true;
        java.sql.ResultSet rstLoc,rstLoc1;
        java.sql.Statement stmLoc,stmLoc1;
        double dblSuel=0,dbluti=0, TotIngSinUti=0, TotIngConUti=0, nd_aporPer=0, nd_basimp=0, nd_frabas=0, nd_impFB=0, nd_porimpFE=0;
        double impretapagtra=0,dblbasimpFE=0, dblvalimpFE=0, dblimpCausado=0, dbllimded=0, dbllimrebpor=0, dblRebaja=0,ImpAPagTra=0;
        
        arlDatDatos = new ArrayList();

        try{
            if(conExt!=null){
               if (suelanutra > 0 ){
               //    System.out.println("totIngtra " + objUti.redondear(totIngtra, objParSis.getDecimalesMostrar())  + " suelanutra " + objUti.redondear(suelanutra, objParSis.getDecimalesMostrar()));
               TotIngSinUti=objUti.redondear((objUti.redondear(totIngtra, objParSis.getDecimalesMostrar()) ), objParSis.getDecimalesMostrar());
               TotIngConUti=(TotIngSinUti + utirectra);
              // nd_aporPer=objUti.redondear((TotIngSinUti * 0.0945 ), objParSis.getDecimalesMostrar()) ;
               //double Truncadototalapopers = Math.floor(nd_aporPer * 100) / 100;

               nd_basimp=  (TotIngConUti  - iess);//  Truncadototalapopers );
                //busco a fraccion basica segun la base imponible en la tabla
               stmLoc=conExt.createStatement();  
               stmLoc1=conExt.createStatement();

                strSql="";
                strSql+="select * from tbm_confImpRent where ne_anio=" + anio + " and " + nd_basimp +" BETWEEN nd_frabas AND nd_exchas; ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    nd_frabas = rstLoc.getDouble("nd_frabas"); 
                    nd_impFB = rstLoc.getDouble("nd_impfrabas");
                    nd_porimpFE = rstLoc.getDouble("nd_porimpfraexc");
                    dblbasimpFE= (nd_basimp - nd_frabas );
                    dblvalimpFE =(dblbasimpFE * nd_porimpFE);
                    dblimpCausado=objUti.redondear((nd_impFB  + dblvalimpFE), objParSis.getDecimalesMostrar());
                    
                    strSql1="";
                    strSql1+="select * from Tbm_RebImpRen WHERE co_per=" + anio + " and ne_carfam=" +carfam ;
                    rstLoc1 = stmLoc1.executeQuery(strSql1);
                    if(rstLoc1.next()){
                        dbllimded = objUti.redondear(rstLoc1.getDouble("nd_gasdedmax"), objParSis.getDecimalesMostrar()); 
                        dbllimrebpor=objUti.redondear((rstLoc1.getDouble("nd_gasdedmax") * rstLoc1.getDouble("nd_porreb")), objParSis.getDecimalesMostrar());
                        dblRebaja= objUti.redondear((totgasper * rstLoc1.getDouble("nd_porreb")), objParSis.getDecimalesMostrar());
                        arlReg=new ArrayList();
                        
                        if (totgasper > 0 ){
//                            if (totgasper < dbllimrebpor){
//                                dbllimrebpor=dbllimrebpor;                                
//                            }else{  
//                                dbllimrebpor=dblRebaja;
//                            } 
                            if (dblimpCausado <= dblRebaja){
                                    // ImpAPagTra=dblRebaja-dblimpCausado;     
                                    dbllimrebpor=dblRebaja;
                            }
                            
                            if (dblimpCausado > dbllimrebpor){  //dblRebaja
                                ImpAPagTra= (dblimpCausado - dbllimrebpor);   
                            } 
                                                                
                              //else{
                                //ImpAPagTra= (dbllimrebpor - dblimpCausado);
                            //}

                            arlReg.add(INT_ARL_BAS_IMP_GRA,nd_basimp);
                            arlReg.add(INT_ARL_IMP_REN_CAU,dblimpCausado);
                            if (ImpAPagTra <=0){
                                impretapagtra=0;
                                //dbllimrebpor=0;
                            }else{
                                impretapagtra=ImpAPagTra;
                            }
                            arlReg.add(INT_ARL_REB_GAS_PER, dbllimrebpor );  
                            arlReg.add(INT_ARL_IMP_DES_REB_GAS_PER, impretapagtra );                       
                            arlReg.add(INT_ARL_REN_RET_TRA, impretapagtra );
                            arlDatDatos.add(arlReg);
                        
                        }else{
                            
                            if (dblimpCausado <= dblRebaja){
                               // ImpAPagTra=dblRebaja-dblimpCausado;     
                                dbllimrebpor=dblRebaja;
                            }
                            arlReg=new ArrayList();
                            arlReg.add(INT_ARL_BAS_IMP_GRA,nd_basimp);
                            arlReg.add(INT_ARL_IMP_REN_CAU,dblimpCausado);
                            arlReg.add(INT_ARL_REB_GAS_PER, dbllimrebpor ); 
                            arlReg.add(INT_ARL_IMP_DES_REB_GAS_PER, dblimpCausado ); 
                            arlReg.add(INT_ARL_REN_RET_TRA, dblimpCausado );
                            arlDatDatos.add(arlReg);
                        }
                    } 
                }
               }
                arlPasar=arlDatDatos;
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
}
