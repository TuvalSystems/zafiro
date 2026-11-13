/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package ZafSerCon;

import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author usuario
 */
public class ZafImpCon
{
    ZafParSis objParSis;
    Connection con;
    ZafCabCon objZafCabCon;
    
    public ZafImpCon(ZafCabCon objCabCon, Connection con){
        this.con = con;
        objZafCabCon = objCabCon;
    }
    
    public ZafImpCon(Connection con){};
    
    public String implementarCon(){
         String retorno="";
         String strClaCon=obtenerClaCon(con, objZafCabCon);
         return strClaCon;
    }
    
    private String obtenerClaCon(Connection con, ZafCabCon objCab){
        Statement st,stUpdClaCon=null;
        ResultSet rs=null;
        String strQry="";
        String strClaConRet="";;
        String strUpdClaConFac="";
        try{
            con.setAutoCommit(false);
            st=con.createStatement();
            strQry="select tx_clacon as clave from tbm_claConFacEle where co_reg=(select min(co_reg)"+
                    "from tbm_claConFacEle "+
                    "where co_emp="+objCab.getIntCodEmp()+" and st_reg is null )"+
                    "and co_emp="+objCab.getIntCodEmp()+" group by tx_clacon";
            //strQry="select min(co_reg) from tbm_claConFacEle where co_emp="+objCab.getIntCodEmp()+" and st_reg is null group by tx_clacon";
            rs=st.executeQuery(strQry);
            if(rs!=null && rs.next() && rs.getString("clave")!=null){
               strClaConRet =rs.getString("clave");
            }
            strUpdClaConFac= "update tbm_ClaConFacEle set st_reg='U', fe_uticlacon=current_timestamp  where co_emp="+objCab.getIntCodEmp()+" and tx_clacon='"+strClaConRet+"'";            
            stUpdClaCon=con.createStatement();
            stUpdClaCon.execute(strUpdClaConFac);
            con.commit();
            con.setAutoCommit(true);
            st.close();
            st = null;
            stUpdClaCon.close();
            stUpdClaCon = null;
            rs.close();
            rs = null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return strClaConRet;    
    }
    
    public Boolean actualizarEnCont(Connection con, int intEmp, int intCodLoc, int intTipDoc, int intCodDoc, String strClaCon, String strDoc, String strClvAccCpb)
    {
        boolean ret=false;
        String strUpdClaConFac="";
        String strSelTipDoc="";
        String strDesCor="";
        Statement stUpdClaCon=null;
        Statement stStrSel=null;
        ResultSet rsSel=null;
        try{
            /* Trae la descripcion corta */
            strSelTipDoc = "select tx_descor from tbm_cabtipdoc where co_emp = " + intEmp + " and co_tipdoc = " + intTipDoc;
            stStrSel=con.createStatement();
            rsSel=stStrSel.executeQuery(strSelTipDoc);
            if(rsSel.next()){
                strDesCor=rsSel.getString("tx_descor");
            }
            
            strUpdClaConFac =  "update tbm_ClaConFacEle set st_reg = 'U', tx_docuticlacon = '" + strDesCor + "_" + strDoc + "', ";
            strUpdClaConFac += "fe_uticlacon = current_timestamp where co_emp = " + intEmp + " and tx_clacon = '" + strClaCon + "'";
            stUpdClaCon=con.createStatement();
            stUpdClaCon.execute(strUpdClaConFac);
            switch (intTipDoc){
                case 228:
                    //Facturas
                    actualizarCabMovInv(con, intEmp, intCodLoc, intTipDoc, intCodDoc, strClaCon, strClvAccCpb);
                  break;
                case 100:
                case 102:
                case 103:
                case 187:
                case 231:
                case 232:
                    //Guias llamar al metodo actualizar
                    actualizarCabGuiRem(con, intEmp, intCodLoc, intTipDoc, intCodDoc, strClaCon, strClvAccCpb);
                    break;
            }
            stUpdClaCon.close();
            stUpdClaCon = null;
            stStrSel.close();
            stStrSel = null;
            rsSel.close();
            rsSel = null;
                    
        }catch(Exception ex){
            ex.printStackTrace();
            ret=false;
        }
        return ret;
    }
    
    public Boolean actualizarCabGuiRem(Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCod, String strClaCon, String strClvAccCpb)
    {
        boolean ret=false;
        String strUpdCabGui="";
        Statement stUpdCabGui=null;
        try{
            if (strClaCon != null && ! strClaCon.equals("")) 
            {  strUpdCabGui =  "update tbm_cabguirem set st_autfacele = 'C', tx_clacon = '" + strClaCon + "', tx_claAccFacEle = '" + strClvAccCpb + "' ";
               strUpdCabGui += "where co_emp = " + intCodEmp + " and co_loc = " + intCodLoc + " and co_tipdoc = " + intTipDoc + " and co_doc = " + intCod;
               stUpdCabGui=con.createStatement();
               stUpdCabGui.execute(strUpdCabGui);
               stUpdCabGui.close();
               stUpdCabGui = null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ret;
    }
    
    public Boolean actualizarCabMovInv(Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCod, String strClaCon, String strClvAccCpb){
        boolean ret=false;
        String strUpdCabGui="";
        Statement stUpdCabGui=null;
        try{
            strUpdCabGui =  "update tbm_cabmovinv set st_autfacele = 'C', tx_clacon = '" + strClaCon + "', tx_claAccFacEle = '" + strClvAccCpb + "' ";
            strUpdCabGui += "where co_emp = " + intCodEmp + " and co_loc = " + intCodLoc + " and co_tipdoc = " + intTipDoc + " and co_doc = " + intCod;
            stUpdCabGui=con.createStatement();
            stUpdCabGui.execute(strUpdCabGui);
            stUpdCabGui.close();
            stUpdCabGui = null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ret;
    }
}