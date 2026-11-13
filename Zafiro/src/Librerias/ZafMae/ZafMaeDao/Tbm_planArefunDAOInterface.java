/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafMae.ZafMaeDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_planArefun;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sistemas4
 */
public interface Tbm_planArefunDAOInterface {
    public List<Tbm_planArefun> consultarLisPagPlantilla(Connection connection, int codempresa) throws SQLException;
}
