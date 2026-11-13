/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_planArefunDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_planArefun;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sistemas4
 */
public class Tbm_planArefunBO {
    private Tbm_planArefunDAOInterface Tbm_planArefunDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;
   
    public Tbm_planArefunBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            Tbm_planArefunDAOInterface = (Tbm_planArefunDAOInterface) Tbm_planArefunBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_planArefunDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_planArefunDAO");
        }
    }
    
    public List<Tbm_planArefun> consultarPlantilla(int codempresa) throws Exception{
        List<Tbm_planArefun> strTx_DesLar  = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                strTx_DesLar = Tbm_planArefunDAOInterface.consultarLisPagPlantilla(con,codempresa);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return strTx_DesLar;
    }

}
