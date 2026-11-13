/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author sistemas4
 */
public class MetodosAlternos {

    private Statement sql;
    private String query;
    private ResultSet rs;
    
    public String traeRuc(Connection oc, int emp){
    String resp="";
    
        try {
            sql=oc.createStatement();
            query = "select tx_ruc from tbm_emp where co_emp = " + emp;
            rs=sql.executeQuery(query);
            if (rs.next()) {
               resp = rs.getString("tx_ruc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    return resp;
    }
}