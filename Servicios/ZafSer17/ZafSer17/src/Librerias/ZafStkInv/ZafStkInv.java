/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafStkInv;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author sistemas3
 */
public class ZafStkInv {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    
    private Statement stm;
    private ResultSet rst;
    
    private String strSQL;
    private int intCodEmp;
    
    private ArrayList arlDatItm;
    private static final int INT_ARL_COD_ITM_GRP=0;
    private static final int INT_ARL_COD_ITM_EMP=1;
    private static final int INT_ARL_COD_ITM_MAE=2;    
    private static final int INT_ARL_COD_LET_ITM=3;     
    private static final int INT_ARL_CAN_ITM=4;
    private static final int INT_ARL_COD_BOD_EMP=5;
    
    private int intTipMov;
    
    public ZafStkInv(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            intTipMov=0;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(null, e);
        }
    }
    
    /**
     * Función que permite llamar a los métodos que realizarán la actualización de inventario
     * @param conexion: Parámetro recibido de la conexión para generar los SQL's
     * @param codigoEmpresa: Código de empresa donde se guardará el documento
     * @param codigoBodega: Código de bodega donde se ingresará/egresará la mercadería
     * @param indiceCampo: Parámetro que permitirá conocer cual es el nombre del campo que se debe actualizar
     * @param signoCampo:Parámetro que permite conocer el signo que debe aplicarse al campo (+ ó -)
     * @param intTipMov: Indica si el método debe ser o no ejecutado
     *                  <HTML>    
     *                      <BR> 0= El método no se debe ejecutar
     *                      <BR> 1= El método si se debe ejecutar
     *                  </HTML>
     * @param arlDatosItem:             <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item. <BR>  Si es por entrada de mercadería la cantidad se envía en positivo.
     *                                  </HTML>
     * @return true: Si se pudo efectuar la operación
     * <BR> false: Caso contrario
     */
    public boolean actualizaInventario(Connection conexion 
            , int codigoEmpresa, int indiceCampo, String signoCampo, int intTipoMovimiento, ArrayList arlDatosItem){
        boolean blnRes=false;
        intCodEmp=codigoEmpresa;
        intTipMov=intTipoMovimiento;        
        arlDatItm=arlDatosItem;
        
        if(validaStkInv(conexion, intTipoMovimiento, signoCampo)){//por confirmaciones no actualizar nada en tbm_inv
            if(actualizaStkInv(conexion, intTipoMovimiento, signoCampo)){//por confirmaciones no actualizar nada en tbm_inv
                if(actualizaStkInvBod(conexion, indiceCampo, signoCampo)){//cuando es por confirmaciones si actualiza : Depende del tipo de confirmación para actualiza el campo correcto
                    blnRes=true;
                }
                else
                    blnRes=false;
            }
            else
                blnRes=false;
        }
        else
            blnRes=false;
        

        return blnRes;
    }
    
    /**
     * Función que permite actualizar el stock en la tabla de inventarios
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    private boolean actualizaStkInv(java.sql.Connection con, int tipoMovimiento, String signoCampo){
        boolean blnRes=true;
        
        String strSQLUpd="";
        try{
            if(tipoMovimiento==1){
                if(con!=null){
                    stm=con.createStatement();
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" SET nd_stkact=nd_stkact" + signoCampo + "" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM);
                        strSQL+=" WHERE co_emp=" + intCodEmp + "";
                        strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                    System.out.println("actualizaStkInv: " + strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                    stm.close();
                    stm=null;

                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
      
    /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo ""
     *              <BR>  2: Actualiza en campo ""
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesEntBod"
     *              <BR>  6: Actualiza en campo "nd_canDesEntCli"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *              <BR> 11: Actualiza en campo "nd_canResVen"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    private String getNomCamInvBod(int indiceNombreCampo){
        String strNomCamAct="";
        switch(indiceNombreCampo){
            case 0:
                strNomCamAct="nd_stkAct";
                break;
            case 1:
                strNomCamAct="";
                break;
            case 2:
                strNomCamAct="";
                break;
            case 3:
                strNomCamAct="nd_canIngBod";
                break;
            case 4:
                strNomCamAct="nd_canEgrBod";
                break;
            case 5:
                strNomCamAct="nd_canDesEntBod";
                break;
            case 6:
                strNomCamAct="nd_canDesEntCli";
                break;
            case 7:
                strNomCamAct="nd_canTra";
                break;
            case 8:
                strNomCamAct="nd_canRev";
                break;
            case 9:
                strNomCamAct="nd_canRes";
                break;
            case 10:
                strNomCamAct="nd_canDis";
                break;
            case 11:
                strNomCamAct="nd_canResVen";
                break;
            default:
                strNomCamAct="";
                break;
        }
        return strNomCamAct;
    }
    
    
    /**
     * Función que permite actualizar el stock en la tabla de inventarios por bodega
     * @param indiceCampo : Indica el campo que se debe actualizar en la tabla
     * @param signoCampo  : Indica el signo que se debe aplicar para realizar la operación
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean actualizaStkInvBod(java.sql.Connection con,int indiceCampo, String signoCampo){
        boolean blnRes=true;
        String strNomCam="";
        String strSQLUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strNomCam=getNomCamInvBod(indiceCampo);
                if(!strNomCam.equals("")){
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_invBod";
                        strSQL+=" SET " + strNomCam + "=(CASE WHEN " + strNomCam + " IS NULL THEN 0 ELSE " + strNomCam + " END)" + signoCampo + "" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM) + "";
                        strSQL+="  WHERE co_emp=" + intCodEmp + "";
                        strSQL+="  AND co_bod=" +  objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_EMP) + "";
                        strSQL+="  AND co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                    System.out.println("actualizaStkInvBod: " + strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                }
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    /**
     * Función que permite validar si el stock en la tabla de inventarios abastece el stock que se desea mover
     * @param con: conexión
     * @param tipoMovimiento: indica si se debe ejecutar el proceso - Cuando se decrementa
     * @param signoCampo: indica el signo que se debe aplicar para realizar la operación
     * @return true: 
     * <BR> false: Caso contrario
     */
    private boolean validaStkInv(java.sql.Connection con, int tipoMovimiento, String signoCampo){
        boolean blnRes=true;
        try{
            if(tipoMovimiento==1){//si es para incrementar/decrementar se aplica, caso contrario no 
                if(con!=null){
                    stm=con.createStatement();
                    for(int i=0; i<arlDatItm.size(); i++){
                        strSQL="";
                        strSQL+="SELECT *FROM tbm_inv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                        strSQL+=" AND a1.co_itm=" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + "";
                        strSQL+=" AND (a1.nd_stkact" + signoCampo + "" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM) + ")<0";//(a1.nd_stkAct-4)>=0
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){//si es negativo
                            System.out.println("Negativos tbm_inv " + strSQL);
                            return false;//no se cuenta con stock suficiente  
                            
                        }
                    }                    
                    stm.close();
                    stm=null;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
}
