/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafMae.ZafMaeDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_planArefun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sistemas4
 */
public class Tbm_planArefunDAO implements Tbm_planArefunDAOInterface{
   
    @Override
    public List<Tbm_planArefun> consultarLisPagPlantilla(Connection connection,int codempresa) throws SQLException {
         String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_planArefun> lisTbm_planArefun = null;

        try{
            strSql = "Select co_pla,tx_deslar,tx_descor from tbm_cabplarolpag ";
            strSql += "where st_reg='A' and co_emp = ?";
            strSql += "order by co_pla ";

            preSta = connection.prepareStatement(strSql);
            
            if(codempresa >0){
                preSta.setInt(1, codempresa);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
         
            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_planArefun = new ArrayList<Tbm_planArefun>();
                do{
                    Tbm_planArefun tmp = new Tbm_planArefun();
                    tmp.setIntco_pla(resSet.getInt("co_pla"));
                    tmp.setStrtx_deslar(resSet.getString("tx_deslar"));
                    tmp.setStrtx_descor(resSet.getString("tx_descor"));

                    lisTbm_planArefun.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_planArefun;
    }

  

}
