/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anufacnoimp;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSisCon.ZafSisCon;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/** *
 * @author efloresa
 */
public class AnulaFacNoImp {
    
    ZafParSis objParSis;
    ZafUtil objUti;
    ZafThreadarrancap objThrArr;
    UltDocPrint ultDocPrint;
    ZafAsiDia objAsiDia;
    ZafSisCon objSisCon; 
    ArchivoHistorico arch;
    
    String strNomArchHis="";
    String strCorEleTo="efloresavelino@hotmail.com";  //sistemas1@tuvalsa.com";
    String strEnvCorEle="S";
    String strFormatDate ="d/m/y";
    int intHorEjePro=0;
    int intDiasProCal=0;
    int intVal=0;
    int intEstMens=0;
    int intTiempoHiloDormido=0;
    int intDiasValidezCot=0;
    boolean blnEstEjepro=false;
    boolean blnExitDocFal=false;
    
    Calendar fecha;
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rst; 
    
    public AnulaFacNoImp(){
        try{
            objParSis=new ZafParSis();
            objUti = new ZafUtil(); 
            ultDocPrint = new UltDocPrint(objParSis);
            objSisCon = new ZafSisCon(objParSis);
            objAsiDia=new ZafAsiDia(objParSis);

            //cargarDrv(objParSis.getDriverConexion());
            //Date objFec = new Date();
            fecha = Calendar.getInstance();
            
            //String strDirSis = getDirectorioSistema();
            //String strMes = getObtenerNomMes(objFec.getMonth()+1);
            //String strMes = getObtenerNomMes(fecha.get(Calendar.MONTH) + 1);
            //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";
            //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf(fecha.get(Calendar.YEAR))+".txt";
            //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.YEAR))+"_"+strMes+"_"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+".txt";
            //strNomArchHis=strDirSis+"/logs/Servicios/ZafSer07/"+String.valueOf(fecha.get(Calendar.YEAR))+"_"+String.valueOf(fecha.get(Calendar.MONTH) + 1)+"_"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+".txt";

            //arch = new ArchivoHistorico(strNomArchHis);
            arch = new ArchivoHistorico();
            strNomArchHis = arch.getNombreArchivoHistorico();
            objUti.cargarDrv_F1(null, objParSis.getDriverConexion());
            objParSis.setCodigoUsuario(1); // usuario administrador
            intTiempoHiloDormido = objParSis.getTiempoHiloDormido();
            intHorEjePro = objParSis.getHoraEjeProceso();
            
            intDiasValidezCot = objParSis.getDiasValidezCotizacion();
            
            //cargar Anios Creados en el Sistema
            if (! objParSis.cargarAniosCreadosSistema())
                System.exit(0);         
                        
            System.out.println("Proceso iniciado con exito. " + fecha.getTime());
            
        }catch(Exception e){
            arch.almacenarArchHis("==================================================================");
            arch.almacenarArchHis("Error al inicializar proceso: " + e);   
            System.exit(0);
        }
    }
    
    public void ejecutaProceso(){
        objThrArr=new ZafThreadarrancap();
        objThrArr.start();        
    }
    
    private class ZafThreadarrancap extends Thread {
        Date datFecSis;
        int intHorDia;
        public ZafThreadarrancap() {
            
        }

        @Override
        public void run() {
            while (true) {
                try{
                    //datFecSis=new java.util.Date();
                    //intHorDia=datFecSis.getHours(); // Calendar.get(Calendar.HOUR_OF_DAY);
                    fecha = Calendar.getInstance();
                    //if(intHorDia == intHorEjejePro){
                    if(fecha.get(Calendar.HOUR_OF_DAY) == intHorEjePro){
                        if(!blnEstEjepro)
                            blnEstEjepro=anularFacNotImp();                                 
                    }else 
                        blnEstEjepro=false;                 

                    Thread.sleep(intTiempoHiloDormido);
                }catch (InterruptedException e) {   
                    System.out.println("Excepción: " + e.toString());           
                }
            }
        }
    }
    
    private boolean anularFacNotImp(){
        boolean blnRes=false;
        
        intVal=1;
        //String.valueOf((objFec.getMonth()+1))            
        
        arch.almacenarArchHis("==================================================================");
        arch.almacenarArchHis("Anulacion de facturas no impresas.....");
                
        try{
            conn=DriverManager.getConnection( objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() ); 
            if(conn!=null){ 
                conn.setAutoCommit(false); 
                //Statement stm=conn.createStatement(); 
                
                String sql = " select co_emp, co_loc, co_tipdoc, co_doc, co_cli, ne_numcot "
                           + " from tbm_cabmovinv "
                           + " where co_tipdoc=1 "
                           + " and st_imp='N' "
                           + " and ne_numdoc=0 "
                           + " and  st_reg not in ('I','E') "
                           //+ " and co_doc = 42984 ";
                           //+ " and current_date >= (fe_doc + " + intDiasValidezCot + " ) "
                           + " and current_date >= (fe_doc + ? ) "
                           + " order by co_emp, co_loc, co_doc " ; 
                
                pst=conn.prepareStatement(sql);
                pst.setInt(1, intDiasValidezCot);
                //rst=stm.executeQuery(sql);
                rst=pst.executeQuery();
                while(rst.next()){ 
                    System.out.println("Procesando: Empresa: " + rst.getString("co_emp") + " Local: " + rst.getString("co_loc") + " TipDoc: "+ rst.getString("co_tipdoc") + " CoDoc: " + rst.getString("co_doc") ); 
                    arch.almacenarArchHis("Procesando: Empresa: " + rst.getString("co_emp") + " Local: " + rst.getString("co_loc") + " TipDoc: "+ rst.getString("co_tipdoc") + " CoDoc: " + rst.getString("co_doc") );
                    if( verificaTerItm(conn, rst.getString("co_emp"), rst.getString("co_loc"), rst.getString("co_tipdoc"), rst.getString("co_doc"))){ 
                        if(anular(conn, rst.getString("co_emp"), rst.getString("co_loc"), rst.getString("co_tipdoc"), rst.getString("co_doc"), rst.getString("co_cli"), rst.getString("ne_numcot") ) ) { 
                            arch.almacenarArchHis("Correcto.");
                            conn.commit();
                        }else { 
                            arch.almacenarArchHis("No se pudo anular.");
                            conn.rollback(); 
                        } 
                    }else{
                        arch.almacenarArchHis("No se pudo anular.");
                        conn.rollback();                         
                    } 
                } 
                rst.close(); 
                rst=null; 
                //stm.close(); 
                //stm=null; 
                pst.close();
                pst=null;
                conn.close(); 
                conn=null; 
                blnRes=true;
                fecha=Calendar.getInstance();
                System.out.println("Proceso finalizo con exito. " + fecha.getTime());
            }
        }catch (SQLException e) { 
            arch.almacenarArchHis("Error en anularFacNotImp: "+e.toString() ); 
        }catch (Exception e) { 
            arch.almacenarArchHis("Error en anularFacNotImp: "+e.toString() ); 
        } 
        return blnRes; 
    }      
    
    private boolean verificaTerItm(Connection conn, String coemp, String coloc, String cotipdoc, String codoc ) {
        boolean blnres=true;
        String strSql;
        ResultSet rst;    
        Statement stm;
        try{
            stm=conn.createStatement();
            strSql="Select * from ( " +
                        " select b.tx_codalt, b.co_itm, " +
                        " case when trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) in ('L') then 'S' else 'N' end as Ter " +
                        " from tbm_cabmovinv as a " +
                        " inner join tbm_detmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc ) " +
                        " where a.co_emp="+coemp+
                        " and a.co_loc="+coloc+
                        " and a.co_tipdoc="+cotipdoc+
                        " and a.co_doc="+codoc+
                        " ) as x where ter='S' ";
            
             rst=stm.executeQuery(strSql);
             if(rst.next()){ 
                 blnres=false; 
                 arch.almacenarArchHis("Prefactura: "+codoc + " contiene items con terminal L." );
             }
             rst.close();
             rst=null;
             stm.close();
             stm=null;
        }catch (SQLException e) { 
            arch.almacenarArchHis("Error en verificaTerItm: "+e.toString() ); 
            blnres=false;  
        }catch (Exception e) { 
            arch.almacenarArchHis("Error en verificaTerItm: "+e.toString() ); 
            blnres=false; 
        }	
        return blnres;
    } 
      
    /*public boolean anular(Connection conn, String coemp, String coloc, String cotipdoc, String codoc, String cocli, String strCodCotVen ) {
        boolean res=true;
        try{
            //System.out.println("Pag --> 1 ");
            //if(!ultDocPrint.verificarsiesconfirmado(coemp, coloc, codoc ,cotipdoc ))
            if(!ultDocPrint.verificaSiEsConfirmado(conn, coemp, coloc, codoc ,cotipdoc ) )    
                res=false;

            if(!ultDocPrint.verificaConfirmacionRelacionada(conn, coemp, coloc, codoc ,cotipdoc ) )    
                res=false;

            //System.out.println("Pag --> 2 ");
            //if (ultDocPrint.isPagoDocumento_aso( Integer.parseInt(coemp), Integer.parseInt(coloc),Integer.parseInt(cotipdoc),Integer.parseInt(codoc)))
            if (ultDocPrint.isPagoDocAso(conn, Integer.parseInt(coemp), Integer.parseInt(coloc),Integer.parseInt(cotipdoc),Integer.parseInt(codoc)))
                res=false;

            if (!anularReg(conn, coemp, coloc, cotipdoc, codoc, cocli, strCodCotVen ))
                res=false;
        }catch(Exception e){
            arch.almacenarArchHis("Error en anular: "+e.toString() ); 
            res=false;
        }
        return res;
    }  */        

    public boolean anular(Connection conn, String coemp, String coloc, String cotipdoc, String codoc, String cocli, String strCodCotVen ) {
        boolean res=true;
        try{
            if(ultDocPrint.verificaSiEsConfirmado(conn, coemp, coloc, codoc ,cotipdoc ) )    
                if(ultDocPrint.verificaConfirmacionRelacionada(conn, coemp, coloc, codoc ,cotipdoc ) )    
                    if (!ultDocPrint.isPagoDocAso(conn, Integer.parseInt(coemp), Integer.parseInt(coloc),Integer.parseInt(cotipdoc),Integer.parseInt(codoc)))
                        if (anularReg(conn, coemp, coloc, cotipdoc, codoc, cocli, strCodCotVen ))
                            res=true;
                        else
                            res=false;                        
                    else
                        res=false;                          
                else
                    res=false;
            else 
                res=false;
        }catch(Exception e){
            arch.almacenarArchHis("Error en anular: "+e.toString() ); 
            res=false;
        }
        return res;
    }          

    public boolean anularReg(Connection conn, String coemp, String coloc, String cotipdoc, String codoc, String cocli, String strCodCotVen ){
        boolean blnRes=false; 
        PreparedStatement pstAnuCabMovInv;
        ResultSet rst;
        String strSql;
        try{
            try{
                if (conn!=null){
                    strSql= " Update tbm_cabMovInv set " +
                            " st_reg  = 'I', " +
                            " fe_ultMod   =  "+objParSis.getFuncionFechaHoraBaseDatos()+"  , " +
                            " co_usrMod   = "+ objParSis.getCodigoUsuario() + " , " +
                            " st_regrep='M', "+
                            " tx_obs2= CURRENT_DATE || '. ANULACION HECHA POR EL PROCESO AUTOMATICO.' "+  
                            " where co_emp = " +coemp + " and "+
                            " co_loc = " + coloc+ " and "+
                            " co_tipDoc = " +  cotipdoc  + " and "+
                            " co_Doc = " + codoc ;
                    pstAnuCabMovInv = conn.prepareStatement(strSql);
                    /* Ejecutando el Update */
                    pstAnuCabMovInv.executeUpdate();

                    strSql = " select co_emp, co_loc, co_tipdoc, co_doc " +
                             " from tbm_detguirem " +
                             " where co_emprel="+coemp+
                             " and co_locrel="+coloc+
                             " and co_tipdocrel="+cotipdoc+
                             " and co_docrel= "+codoc+
                             " group by co_emp, co_loc, co_tipdoc, co_doc ";
                    Statement stm=conn.createStatement();
                    Statement stmAnuGuia=conn.createStatement();
                    rst = stm.executeQuery(strSql);
                    while(rst.next()){
                        strSql=" UPDATE tbm_cabguirem SET st_reg='I',st_regrep='M' , tx_obs2='ANULACIÓN HECHA POR EL PROCESO AUTOMÁTICO' " +
                               " where co_emp="+rst.getInt("co_emp")+" and co_loc="+rst.getInt("co_loc")+" " +
                               " and co_tipdoc= "+rst.getInt("co_tipdoc")+"  and co_doc="+rst.getInt("co_doc")+" ";
                        stmAnuGuia.executeUpdate(strSql);
                    }
                    rst.close(); 
                    rst=null;
                    stm.close(); 
                    stm=null;
                    stmAnuGuia.close(); 
                    stmAnuGuia=null;
                    
                    strSql= " Update tbm_cabcotven set " +
                            " st_reg  = 'U', st_regrep='M' " +
                            " where " +
                            " co_emp = " +coemp + " and "+
                            " co_loc = " + coloc+ " and "+
                            " co_cot = " +  strCodCotVen;
                    
                    pstAnuCabMovInv = conn.prepareStatement(strSql);
                    /* Ejecutando el Update */
                    pstAnuCabMovInv.executeUpdate();

                    //System.out.println("Anula diario.........");

                    //**********ANULAR DIARIO  *************************//
                    if(objAsiDia.anularDiario(conn, Integer.parseInt(coemp),  Integer.parseInt(coloc) , Integer.parseInt(cotipdoc), Integer.parseInt(codoc))){
                        if( anulaDet(conn, null , coemp, coloc, cotipdoc,  codoc, cocli) )
                            blnRes=true;                                
                        else   
                            blnRes=false;                 
                    }else {   
                        blnRes=false; 
                    }   
                }
            }catch(SQLException Evt){   
                arch.almacenarArchHis("Error en anularReg: "+Evt.toString() );  
                return false;  
            }
        }catch(Exception Evt){   
            arch.almacenarArchHis("Error en anularReg: "+Evt.toString() );  
            return false;  
        }
        return blnRes;
    }    

    private boolean anulaDet(Connection conn, Connection con_remota, String coemp, String coloc, String cotipdoc, String codoc, String cocli ){
        boolean blnRes;
        Statement stmLoc;
        ResultSet rstLoc;
        String strMerIngEgr="", strTipIngEgr="";
        String strActInv = "", strActInv2 = "", strOrAct= "";
        String sql;
        int intTipCli;
        try{            
            intTipCli= ultDocPrint.ValidarCodigoCliente( Integer.parseInt(coemp), cocli, conn);
            
            stmLoc=conn.createStatement();
            String sql2= " SELECT st_meringegrfisbod, tx_natdoc "
                       + " FROM tbm_cabtipdoc "
                       + " WHERE co_emp="+coemp
                       + " and co_loc="+coloc
                       + " and co_tipDoc="+cotipdoc;
            rstLoc = stmLoc.executeQuery(sql2);
            if(rstLoc.next()){
                strMerIngEgr=rstLoc.getString("st_meringegrfisbod");  
                strTipIngEgr=rstLoc.getString("tx_natdoc");  
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;

            String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                            " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                            " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                            " WHERE a.co_emp="+coemp+" and a.co_loc="+coloc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                            " THEN 'S' ELSE 'N' END AS proconf  ";

            sql=" select a.co_itm, a.co_bod, abs(a.nd_can) as nd_can  " 
                + strAux2+" from tbm_detmovinv as a " +
                " inner join tbm_inv as inv on (inv.co_emp=a.co_emp and inv.co_itm=a.co_itm ) " +
                " where a.co_emp="+coemp+" and a.co_loc="+coloc+" and  a.co_tipdoc="+cotipdoc+" and a.co_doc="+codoc+"  and inv.st_ser='N'";
            Statement stm=conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            while(rst.next()){
                //for(int i=0; i<tblDat.getRowCount();i++){
                String strEstFisBod=rst.getString("proconf");
                int intCodItm =   rst.getInt("co_itm");
                int intCodBod =   rst.getInt("co_bod");
                double intCodCan = rst.getDouble("nd_can");

                String strAux="";
                if(strEstFisBod.equals("N")){
                    if(strMerIngEgr.equals("S")){
                        if(strTipIngEgr.equals("I")){ 
                            if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                            if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                        }
                        if(strTipIngEgr.equals("E")){
                            if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                            if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                        } 
                    }
                } 

                sql="update  TBM_INVBOD set  " +
                    " nd_stkact = nd_stkact + " + (intCodCan)  +
                    " "+strAux+" "+        
                    " WHERE co_emp = " +coemp + "and " +
                    " co_bod = " + intCodBod + " and " +
                    " co_itm = " + intCodItm;
                PreparedStatement pstUptInvBod = conn.prepareStatement(sql);
                pstUptInvBod.executeUpdate();

                strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                strOrAct  =" or ";

                if(intTipCli==1){
                    if(!objSisCon.actualizarInventario_2_solo_OC(conn, con_remota, Integer.parseInt(coemp),Integer.parseInt(coloc), intCodItm, Math.abs(intCodCan), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
                    return false;
                    }
                }
            }

            if(!strActInv.equals("")){
                String strUpdate =  " UPDATE tbm_inv " +
                                    " SET st_regrep='M', nd_stkAct=b1.nd_stkAct " +
                                    " FROM  " +
                                    "( " +
                                    " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
                                    " FROM tbm_invBod AS a2 " +
                                    " WHERE a2.co_emp="+coemp+"   " +
                                    " and ( " + strActInv2 +" ) " +
                                    " GROUP BY a2.co_emp, a2.co_itm " +
                                    " ORDER BY a2.co_itm " +
                                    ") AS b1 " +
                                    " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                                    " AND ( " + strActInv +" ) " +
                                    " AND tbm_inv.co_emp="+coemp+" ";
                PreparedStatement pstActStkInv = conn.prepareStatement(strUpdate);
                pstActStkInv.executeUpdate();
            }
            blnRes=true;
        }catch (SQLException e) {  
            blnRes=false;  
            arch.almacenarArchHis("Error en anulaDet: "+e.toString() );
        }catch (Exception e) { 
            blnRes=false; 
            arch.almacenarArchHis("Error en anulaDet: "+e.toString() );
        }
        return blnRes;
    }

  }
