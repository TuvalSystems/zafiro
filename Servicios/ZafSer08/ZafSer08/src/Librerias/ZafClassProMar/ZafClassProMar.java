/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafClassProMar;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**
 *
 * @author Roberto Flores
 */
public class ZafClassProMar {

    
    public ArrayList<Tbm_callab> obtenerCalLaboralDia(Date datFecArc, Connection unCon, ResultSet rstRes, PreparedStatement preSta) throws SQLException, Exception{

        //Connection unCon = null;
        //ResultSet rstRes = null;
        //PreparedStatement preSta = null;

        ArrayList<Tbm_callab> arrCalLab = null;
        String strSql = "SELECT * FROM tbm_callab WHERE fe_dia = ?";
        preSta = unCon.prepareStatement(strSql);

        if(datFecArc!=null){
            preSta.setDate(1, new java.sql.Date(datFecArc.getTime()));
        }
        else{
            preSta.setNull(1, Types.DATE);
        }

        rstRes = preSta.executeQuery();

        if(rstRes.next()){

            arrCalLab = new ArrayList<Tbm_callab>();

            do{

                Tbm_callab tbm_callab = new Tbm_callab();
                tbm_callab.setDteFe_Dia(rstRes.getDate("fe_dia"));
                tbm_callab.setIntCo_Hor(rstRes.getInt("co_hor"));
                tbm_callab.setStrSt_DiaLab(rstRes.getString("st_dialab"));
                tbm_callab.setStrHo_Ent(rstRes.getString("ho_ent"));
                tbm_callab.setStrHo_Sal(rstRes.getString("ho_sal"));
                tbm_callab.setStrMin_GraEnt(rstRes.getString("ho_mingraent"));
                arrCalLab.add(tbm_callab);
            }while(rstRes.next());
        }else{
            throw new Exception("FECHA DE ARCHIVO NO SE ENCUENTRA EN EL CALENDARIO LABORAL...");
        }

        return arrCalLab;
    }

    public boolean isFeriado(Tbm_tra tbmTra, ArrayList<Tbm_callab> arrCalLab, Connection unCon, ResultSet rstRes, PreparedStatement preSta) throws SQLException, Exception{

        boolean blnIsFeriado = false;
        int co_tra = tbmTra.getIntCo_tra();//listMarEmp.get(0).getIntPin();
        String strSql = "select co_hor from tbm_traemp where co_tra = " + co_tra + " and co_hor>0";
        preSta = unCon.prepareStatement(strSql);
        rstRes = preSta.executeQuery();

        if(rstRes.next()){

            int co_hor = rstRes.getInt("co_hor");

            if(arrCalLab!=null){

                for(Iterator<Tbm_callab> itCalLab = arrCalLab.iterator();itCalLab.hasNext();){

                    Tbm_callab tbm_callab = itCalLab.next();

                    if(co_hor==tbm_callab.getIntCo_Hor()){

                        //comprobar si es feriado o no
                        if(tbm_callab.getStrSt_DiaLab().compareTo("S")==0){
                            blnIsFeriado = true;
                        }
                    }
                }
            }
        }else{

            //******************************
            //si no estan definidos en tbm_traEmp.. deben ser insertados/actualizados de todas maneras
            blnIsFeriado = false;
        }

        return blnIsFeriado;
    }

}
