/*
 * ZafVen25_01.java
 *
 * Created on 20 de mayo de 2008, 15:46
 */

package Compras.ZafCom92;  


import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;


/**
 *
 * @author  José Marín
 */
public class ZafCom92_02 extends javax.swing.JDialog {
    private String numdocGlo="";
    boolean blnRes=false; 
    private ZafParSis objParSisInt;
    private ZafPerUsr objPerUsrInt;                                //Objeto que almacena el perfil del usuario.
    private Vector vecDatInt, vecRegInt, vecCabInt, vecAuxInt;    
    
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_ITM=1;
    private final int INT_TBL_DAT_CAN_REP=2;
    private final int INT_TBL_DAT_CAN_GUI=3;    
    private final int INT_TBL_DAT_CAN_MOD=4;    
    private final int INT_TBL_DAT_CAN_ANT=5;
    private final int INT_TBL_DAT_EST_MOD = 6;
    private ZafTblMod objTblModInt;    
    private ZafColNumerada objColNumInt;   
    private ZafTblPopMnu objTblPopMnuInt;  
    private ZafTblCelRenLbl objTblCelRenLblNumInt;                 //Render: Presentar JLabel en JTable (Números).   
    private ZafTblCelRenLbl objTblCelRenLblCodInt;                 //Render: Presentar JLabel en JTable (Números).
    private ZafMouMotAda objMouMotAdaInt;    
    private ZafTblEdi objTblEdiInt;                                //Editor: Editor del JTable.    
    private ZafTblCelRenLbl objTblCelRenLblInt;                    //Render: Presentar JLabel en JTable.       
    private ZafTblFilCab objTblFilCabInt;
    private ZafTblOrd objTblOrdInt;    
    private ZafTblBus objTblBusInt;    
    private ZafDocLis objDocLisInt;   
    private ZafTblModLis objTblModLisInt;                          //Detectar cambios de valores en las celdas. 
    private ZafUtil objUtiInt;                                    //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private boolean blnHayCamInt;
    private boolean blnCorrigiendoCanMod = false;
    public boolean blnOK =false;
    
    public ZafCom92_02(java.awt.Frame parent, boolean modal, ZafParSis obj,String numdoc)
    {
        super(parent, modal);
        //Inicializar objetos.
        objParSisInt = obj;
        numdocGlo =numdoc;
        objUtiInt = new ZafUtil(); 
        objPerUsrInt=new ZafPerUsr(objParSisInt);
        blnOK=false;
        
        initComponents(); 
        lblTitulo.setText("Reposición N°: " + numdocGlo);
        if (!configurarFrm())
                exitForm();
        else
            obtenerRepoPendMYSQL(numdoc);
        
            
         
////        txtCodPed.setVisible(false); 
    }    
    
    public boolean ejeOK()
    {
        return blnOK;
    }
private boolean obtenerRepoPendMYSQL(String numdoc)
{
    boolean blnRes = true;

    String strSql = "";

    try
    {
//////////        pgrSis.setIndeterminate(true);
//////////        lblMsgSis.setText("Obteniendo datos desde MySQL...");

        try (java.sql.Connection conLocMy = DriverManager.getConnection(
                objParSisInt.getStringConexion_TuvalApi(),
                objParSisInt.getUsuarioBaseDatos_TuvalApi(),
                objParSisInt.getClaveBaseDatos_TuvalApi()
        ))
        {
            if (conLocMy != null)
            {
                strSql = "";
                strSql += " SELECT rep.Documento,    rep.id AS idDetRep,    rep.cabecera_id AS idCabRep,    rep.art_codigo AS itmRep,\n";
                strSql += "        rep.art_descripcion AS repdescItm,    rep.cantidad AS repCant, \n";
                strSql += "        guidet.detalle_id AS idDetGui,    guidet.guia_remision_id AS idGuiRem,    guidet.gui_Rem_codi_interno AS itmGui, \n";
                strSql += "        guidet.gui_rem_cantidad AS guiCant,    guidet.recibido AS guiCantRec, \n";
                strSql += "         CASE WHEN IFNULL(guidet.detalle_id, 0) = 0 THEN 'NO ESTA EN GUIA' ELSE 'SI ESTA EN GUIA' END AS estado_guia \n";
                strSql += " FROM tuvalapi.gp_traslados_reposicions AS rep \n";
                strSql += "     LEFT JOIN ( \n";
                strSql += "                 SELECT  det.* \n";
                strSql += "                 FROM tuvalapi.gp_guia_remision_detalles AS det \n";
                strSql += "                     INNER JOIN tuvalapi.gp_guia_remisions AS gui ON gui.id = det.guia_remision_id  AND gui.tipo = 'reposicion' \n";
                strSql += "               ) AS guidet   ON guidet.detalle_id = rep.id\n";
                strSql += " WHERE EXISTS( \n";
                strSql += "                 SELECT 1 \n";                
                strSql += "                 FROM tuvalapi.gp_traslados_reposicions AS rep2\n";
                strSql += "                     LEFT JOIN ( \n";
                strSql += "                                 SELECT  det2.*\n";
                strSql += "                                 FROM tuvalapi.gp_guia_remision_detalles AS det2 \n";
                strSql += "                                         INNER JOIN tuvalapi.gp_guia_remisions AS gui2  ON gui2.id = det2.guia_remision_id AND gui2.tipo = 'reposicion'\n";
                strSql += "                               ) AS guidet2  ON guidet2.detalle_id = rep2.id\n";                
                strSql += "                 WHERE rep2.Documento = rep.Documento AND IFNULL(guidet2.detalle_id, 0) = 0\n";                 
                strSql += "             )   and rep.documento='"+ numdoc +"' \n";                                 

                System.out.println("Consulta MYSQL obtenerTraPend:");
                System.out.println(strSql);

                vecDatInt.clear();
                //lblMsgSis.setText("Cargando datos...");

                try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql);
                     java.sql.ResultSet rstLocMy = pstLocMy.executeQuery())
                {
                    while (rstLocMy.next())
                    {
                        vecRegInt = new Vector();
                        vecRegInt.add(INT_TBL_DAT_LIN, "");
                        vecRegInt.add(INT_TBL_DAT_COD_ITM,rstLocMy.getString("itmRep") == null ? "" : rstLocMy.getString("itmRep"));
                        vecRegInt.add(INT_TBL_DAT_CAN_REP,rstLocMy.getString("repCant") == null ? "" : rstLocMy.getString("repCant"));
                        vecRegInt.add(INT_TBL_DAT_CAN_GUI,rstLocMy.getString("guiCant") == null ? "" : rstLocMy.getString("guiCant"));
                        vecRegInt.add(INT_TBL_DAT_CAN_MOD,rstLocMy.getString("repCant") == null ? "" : rstLocMy.getString("repCant"));
                        vecRegInt.add(INT_TBL_DAT_CAN_ANT,rstLocMy.getString("repCant") == null ? "" : rstLocMy.getString("repCant"));
                        vecRegInt.add(INT_TBL_DAT_EST_MOD,'N');
                        vecDatInt.add(vecRegInt);
                    }
                }
            }
        }

        objTblModInt.setData(vecDatInt);
        tblDatItm.setModel(objTblModInt);
        vecDatInt.clear();
        //lblMsgSis.setText("Se encontraron " + tblDatItm.getRowCount() + " registros.");
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
    }
    catch (Exception e)
    {
        blnRes = false;
    }
    finally
    {
    }

    return blnRes;
}

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }     
    
     class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCamInt=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCamInt=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCamInt=true;
        }
    }
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }     
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatItm = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butgua = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 140));
        jPanel2.setLayout(new java.awt.BorderLayout());

        tblDatItm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDatItm);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        lblTitulo.setText("jLabel1");
        jPanel2.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        butgua.setText("Aceptar");
        butgua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butguaActionPerformed(evt);
            }
        });
        jPanel4.add(butgua);

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(401, 254));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       blnRes=false; 
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        blnRes=false; 
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butguaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butguaActionPerformed
        try
        {
            if (procesarItemsModificados())
                dispose();
        }
        catch (Exception e)
        {
            objUtiInt.mostrarMsgErr_F1(this, e);
        }

    }//GEN-LAST:event_butguaActionPerformed
    
private boolean esFilaModificada(int intFil)
{
    boolean blnRes = false;

    try
    {
        Object objVal = objTblModInt.getValueAt(intFil, INT_TBL_DAT_EST_MOD);

        if (objVal != null && objVal.toString().trim().equalsIgnoreCase("S"))
        {
            blnRes = true;
        }
    }
    catch (Exception e)
    {
        blnRes = false;
    }

    return blnRes;
}   

private String obtenerValorTabla(int intFil, int intCol)
{
    String strVal = "";

    try
    {
        Object objVal = objTblModInt.getValueAt(intFil, intCol);

        if (objVal != null)
        {
            strVal = objVal.toString().trim();
        }
    }
    catch (Exception e)
    {
        strVal = "";
    }

    return strVal;
}

private boolean procesarItemsModificados()
{
    boolean blnRes = true;

    java.sql.Connection conSQL = null;
    java.sql.Connection conMySQL = null;

    int intCanFilMod = 0;
    int intCanUpd = 0;
    int intCanDel = 0;

    try
    {
        if (numdocGlo == null || numdocGlo.trim().equals(""))
        {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se pudo obtener el número de reposición.",
                    "Mensaje del sistema",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        /*
         * Contar filas modificadas.
         */
        for (int i = 0; i < objTblModInt.getRowCountTrue(); i++)
        {
            if (esFilaModificada(i))
            {
                intCanFilMod++;
            }
        }

        if (intCanFilMod == 0)
        {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No existen ítems modificados para procesar.",
                    "Mensaje del sistema",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }

        int intOpc = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Se procesarán " + intCanFilMod + " ítem(s) modificados de la reposición "
                + numdocGlo + ".\n\n"
                + "Si Cant.Mod. es diferente a Cant.Ant. se modificará.\n"
                + "Si Cant.Mod. es igual a Cant.Ant. se eliminará.\n\n"
                + "¿Está seguro de continuar?",
                "Confirmación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (intOpc != javax.swing.JOptionPane.YES_OPTION)
        {
            return false;
        }

        /*
         * Conexión SQL Server.
         */
        conSQL = DriverManager.getConnection(
                objParSisInt.getStringConexion_Dynamics2021(),
                objParSisInt.getUsuarioBaseDatos_Dynamics2021(),
                objParSisInt.getClaveBaseDatos_Dynamics2021()
        );

        /*
         * Conexión MySQL.
         */
        conMySQL = DriverManager.getConnection(
                objParSisInt.getStringConexion_TuvalApi(),
                objParSisInt.getUsuarioBaseDatos_TuvalApi(),
                objParSisInt.getClaveBaseDatos_TuvalApi()
        );

        if (conSQL == null || conMySQL == null)
        {
            throw new Exception("No se pudo establecer conexión con SQL Server o MySQL.");
        }

        conSQL.setAutoCommit(false);
        conMySQL.setAutoCommit(false);

        for (int i = 0; i < objTblModInt.getRowCountTrue(); i++)
        {
            if (!esFilaModificada(i))
                continue;

            String txitm = obtenerValorTabla(i, INT_TBL_DAT_COD_ITM);
            String txrepo = numdocGlo.trim();

            java.math.BigDecimal bgdCanMod = obtenerBigDecimal(
                    objTblModInt.getValueAt(i, INT_TBL_DAT_CAN_MOD)
            );

            java.math.BigDecimal bgdCanAnt = obtenerBigDecimal(
                    objTblModInt.getValueAt(i, INT_TBL_DAT_CAN_ANT)
            );

            if (txitm.equals(""))
            {
                throw new Exception("No se pudo obtener el código de ítem en la fila " + (i + 1));
            }

            System.out.println("Procesando fila: " + (i + 1));
            System.out.println("Documento: " + txrepo);
            System.out.println("Item: " + txitm);
            System.out.println("Cant.Mod: " + bgdCanMod);
            System.out.println("Cant.Ant: " + bgdCanAnt);

            /*
             * ESCENARIO 1:
             * Cant.Mod. diferente a Cant.Ant. => UPDATE.
             */
            if (bgdCanMod.compareTo(bgdCanAnt) != 0)
            {
                if (!actualizarItmRepSQL(conSQL, txitm, txrepo, bgdCanMod.doubleValue()))
                {
                    throw new Exception(
                            "Error al modificar en SQL Server. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                if (!actualizarRepoPendMYSQL(conMySQL, txitm, txrepo, bgdCanMod.doubleValue()))
                {
                    throw new Exception(
                            "Error al modificar en MySQL. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                intCanUpd++;
            }
            /*
             * ESCENARIO 2:
             * Cant.Mod. igual a Cant.Ant. => DELETE.
             */
            else
            {
                if (!eliminarItmRepSQL(conSQL, txitm, txrepo))
                {
                    throw new Exception(
                            "Error al eliminar en SQL Server. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                if (!eliminarRepoPendMYSQL(conMySQL, txitm, txrepo))
                {
                    throw new Exception(
                            "Error al eliminar en MySQL. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                intCanDel++;
            }
        }

        /*
         * Primero confirma MySQL.
         * Luego confirma SQL Server.
         */
        conMySQL.commit();
        conSQL.commit();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Proceso ejecutado correctamente.\n\n"
                + "Ítems modificados: " + intCanUpd + "\n"
                + "Ítems eliminados: " + intCanDel,
                "Mensaje del sistema",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        obtenerRepoPendMYSQL(numdocGlo);
        blnOK=true;
        
    }
    catch (Exception e)
    {
        blnRes = false;

        try
        {
            if (conMySQL != null)
                conMySQL.rollback();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            if (conSQL != null)
                conSQL.rollback();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        objUtiInt.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (conMySQL != null)
            {
                conMySQL.setAutoCommit(true);
                conMySQL.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            if (conSQL != null)
            {
                conSQL.setAutoCommit(true);
                conSQL.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    return blnRes;
}



    
private boolean eliminarItmRepSQL(java.sql.Connection conExt, String txitm, String txrepo)
{
    boolean blnRes = false;
    String strSQLInt = "";

    try
    {
        if (conExt != null)
        {
            strSQLInt = "";
            strSQLInt += " DELETE FROM " + objParSisInt.getNombreBaseDatosGP() + ".dbo.tbmsw_transferencia_det \n";
            strSQLInt += " WHERE trd_numdoc = ? \n";
            strSQLInt += "   AND trd_item = ? \n";

            System.out.println("DELETE SQL SERVER:");
            System.out.println(strSQLInt);
            System.out.println("Documento: " + txrepo);
            System.out.println("Item: " + txitm);

            try (java.sql.PreparedStatement pstLoc = conExt.prepareStatement(strSQLInt))
            {
                pstLoc.setString(1, txrepo);
                pstLoc.setString(2, txitm);

                int intFilDel = pstLoc.executeUpdate();

                System.out.println("Filas eliminadas SQL Server: " + intFilDel);

                if (intFilDel <= 0)
                {
                    throw new java.sql.SQLException(
                            "No se eliminó ningún registro en SQL Server. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                blnRes = true;
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}
    
private boolean eliminarRepoPendMYSQL(java.sql.Connection conLocMy, String txitm, String numdoc)
{
    boolean blnRes = false;
    String strSql = "";

    try
    {
        if (conLocMy != null)
        {
            strSql = "";
            strSql += " DELETE rep \n";
            strSql += " FROM tuvalapi.gp_traslados_reposicions AS rep \n";
            strSql += " WHERE rep.Documento = ? \n";
            strSql += "   AND rep.art_codigo = ? \n";

            System.out.println("DELETE MYSQL:");
            System.out.println(strSql);
            System.out.println("Documento: " + numdoc);
            System.out.println("Item: " + txitm);

            try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql))
            {
                pstLocMy.setString(1, numdoc);
                pstLocMy.setString(2, txitm);

                int intFilDel = pstLocMy.executeUpdate();

                System.out.println("Filas eliminadas MySQL: " + intFilDel);

                if (intFilDel <= 0)
                {
                    throw new java.sql.SQLException(
                            "No se eliminó ningún registro en MySQL. Documento: "
                            + numdoc + ", Item: " + txitm
                    );
                }

                blnRes = true;
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}





private boolean actualizarItmRepSQL(java.sql.Connection conExt, String txitm, String txrepo, double valItm)
{
    boolean blnRes = false;
    String strSQLInt = "";

    try
    {
        if (conExt != null)
        {
            strSQLInt = "";
            strSQLInt += " UPDATE " + objParSisInt.getNombreBaseDatosGP() + ".dbo.tbmsw_transferencia_det \n";
            strSQLInt += " SET trd_cantidad_trf = ? \n";
            strSQLInt += " WHERE trd_numdoc = ? \n";
            strSQLInt += "   AND trd_item = ? \n";

            System.out.println("UPDATE SQL SERVER:");
            System.out.println(strSQLInt);
            System.out.println("Documento: " + txrepo);
            System.out.println("Item: " + txitm);
            System.out.println("Cantidad: " + valItm);

            try (java.sql.PreparedStatement pstLoc = conExt.prepareStatement(strSQLInt))
            {
                pstLoc.setDouble(1, valItm);
                pstLoc.setString(2, txrepo);
                pstLoc.setString(3, txitm);

                int intFilUpd = pstLoc.executeUpdate();

                System.out.println("Filas modificadas SQL Server: " + intFilUpd);

                if (intFilUpd <= 0)
                {
                    throw new java.sql.SQLException(
                            "No se modificó ningún registro en SQL Server. Documento: "
                            + txrepo + ", Item: " + txitm
                    );
                }

                blnRes = true;
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}
    
private boolean actualizarRepoPendMYSQL(java.sql.Connection conLocMy, String txitm, String numdoc, double valItm)
{
    boolean blnRes = false;
    String strSql = "";

    try
    {
        if (conLocMy != null)
        {
            strSql = "";
            strSql += " UPDATE tuvalapi.gp_traslados_reposicions AS rep \n";
            strSql += " SET rep.cantidad = ? \n";
            strSql += " WHERE rep.Documento = ? \n";
            strSql += "   AND rep.art_codigo = ? \n";

            System.out.println("UPDATE MYSQL:");
            System.out.println(strSql);
            System.out.println("Documento: " + numdoc);
            System.out.println("Item: " + txitm);
            System.out.println("Cantidad: " + valItm);

            try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql))
            {
                pstLocMy.setDouble(1, valItm);
                pstLocMy.setString(2, numdoc);
                pstLocMy.setString(3, txitm);

                int intFilUpd = pstLocMy.executeUpdate();

                System.out.println("Filas modificadas MySQL: " + intFilUpd);

                if (intFilUpd <= 0)
                {
                    throw new java.sql.SQLException(
                            "No se modificó ningún registro en MySQL. Documento: "
                            + numdoc + ", Item: " + txitm
                    );
                }

                blnRes = true;
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}


    
   
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDatItm.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
//                case INT_TBL_DAT_COD_EMP: strMsg="Código de la Empresa"; break;
//                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
                case INT_TBL_DAT_COD_ITM: strMsg="Código del Item"; break;
                case INT_TBL_DAT_CAN_REP: strMsg="Cantidad de la Reposición"; break;
                case INT_TBL_DAT_CAN_GUI: strMsg="Cantidad de la Guia"; break;
                case INT_TBL_DAT_CAN_MOD: strMsg="Cantidad Modificada"; break;
                case INT_TBL_DAT_CAN_ANT: strMsg="Cantidad Modificada Anterior"; break;
                case INT_TBL_DAT_EST_MOD: strMsg="Mod"; break;
                default: strMsg=""; break;
            }
            tblDatItm.getTableHeader().setToolTipText(strMsg);
        }
    }  

    
    public boolean getAceptar(){
       return  blnRes;
    }
    
private boolean configurarFrm()
{
    boolean blnRes = false;

    try
    {
        if (!objPerUsrInt.isOpcionEnabled(3995))
        {
            butgua.setVisible(false);
        }

        //Configurar JTable: Establecer el modelo.
        vecDatInt = new Vector();
        vecCabInt = new Vector();
        vecCabInt.clear();

        vecCabInt.add(INT_TBL_DAT_LIN, "");
        vecCabInt.add(INT_TBL_DAT_COD_ITM, "Cód.Itm.");
        vecCabInt.add(INT_TBL_DAT_CAN_REP, "Cant.Rep.");
        vecCabInt.add(INT_TBL_DAT_CAN_GUI, "Cant.Guia.");
        vecCabInt.add(INT_TBL_DAT_CAN_MOD, "Cant.Mod.");
        vecCabInt.add(INT_TBL_DAT_CAN_ANT, "Cant.Mod.Ant.");
        vecCabInt.add(INT_TBL_DAT_EST_MOD, "Mo.");

        objTblModInt = new ZafTblMod();
        objTblModInt.setHeader(vecCabInt);

        tblDatItm.setModel(objTblModInt);

        tblDatItm.setCellSelectionEnabled(true);
        tblDatItm.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        objColNumInt = new ZafColNumerada(tblDatItm, INT_TBL_DAT_LIN);

        objTblPopMnuInt = new ZafTblPopMnu(tblDatItm);
        objTblPopMnuInt.setPegarEnabled(true);
        objTblPopMnuInt.setBorrarContenidoEnabled(true);

        tblDatItm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        javax.swing.table.TableColumnModel tcmAuxTra = tblDatItm.getColumnModel();

        tcmAuxTra.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
        tcmAuxTra.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(130);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_REP).setPreferredWidth(60);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_GUI).setPreferredWidth(80);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_MOD).setPreferredWidth(80);
        //tcmAuxTra.getColumn(INT_TBL_DAT_CAN_ANT).setPreferredWidth(80);
        //tcmAuxTra.getColumn(INT_TBL_DAT_EST_MOD).setPreferredWidth(10);
////////        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_ANT).setResizable(false);
////////        tcmAuxTra.getColumn(INT_TBL_DAT_EST_MOD).setResizable(false);

             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_CAN_ANT);
            arlColHid.add(""+INT_TBL_DAT_EST_MOD);  // 
            objTblModInt.setSystemHiddenColumns(arlColHid, tblDatItm);

        /*
         * RENDERIZADORES CON COLORES POR FILA
         *
         * Verde claro:
         *   Cant.Guia nulo, vacío o cero.
         *
         * Azul claro:
         *   Cant.Mod diferente a Cant.Rep.
         *
         * Prioridad:
         *   Si Cant.Mod es diferente a Cant.Rep, se pinta azul.
         *   Si no, y Cant.Guia es 0/nulo/vacío, se pinta verde.
         */
        ZafTblCelRenLbl objRenTxt = crearRenderFilaDetalle(javax.swing.JLabel.LEFT);
        ZafTblCelRenLbl objRenNum = crearRenderFilaDetalle(javax.swing.JLabel.RIGHT);

        tcmAuxTra.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objRenTxt);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_REP).setCellRenderer(objRenNum);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_GUI).setCellRenderer(objRenNum);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_MOD).setCellRenderer(objRenNum);
        tcmAuxTra.getColumn(INT_TBL_DAT_CAN_ANT).setCellRenderer(objRenNum);

        tblDatItm.getTableHeader().setReorderingAllowed(false);

        objMouMotAdaInt = new ZafMouMotAda();
        tblDatItm.getTableHeader().addMouseMotionListener(objMouMotAdaInt);

        //Configurar JTable: Establecer columnas editables.
        vecAuxInt = new Vector();
        vecAuxInt.add("" + INT_TBL_DAT_CAN_MOD);
        objTblModInt.setColumnasEditables(vecAuxInt);
        vecAuxInt = null;

        //Configurar JTable: Editor de la tabla.
        objTblEdiInt = new ZafTblEdi(tblDatItm);

        /*
         * BLOQUEAR EDICIÓN DE Cant.Mod CUANDO Cant.Guia > 0
         */
        configurarEditorCantMod();

        //Configurar JTable: Establecer la fila de cabecera.
        objTblFilCabInt = new ZafTblFilCab(tblDatItm);
        tcmAuxTra.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabInt);
        objTblFilCabInt = null;

        objTblModInt.setModoOperacion(objTblModInt.INT_TBL_EDI);

        objTblOrdInt = new ZafTblOrd(tblDatItm);
        objTblBusInt = new ZafTblBus(tblDatItm);
        objDocLisInt = new ZafDocLis();

        //Configurar JTable: Detectar cambios de valores en las celdas.
        objTblModLisInt = new ZafTblModLis();
        objTblModInt.addTableModelListener(objTblModLisInt);

        /*
         * REGLA DE SEGURIDAD:
         * Si por pegado u otro evento se llega a modificar Cant.Mod
         * cuando Cant.Guia > 0, se revierte automáticamente a Cant.Rep.
         */
        objTblModInt.addTableModelListener(new javax.swing.event.TableModelListener()
        {
            public void tableChanged(javax.swing.event.TableModelEvent evt)
            {
                try
                {
                    if (blnCorrigiendoCanMod)
                        return;

                    if (evt.getType() != javax.swing.event.TableModelEvent.UPDATE)
                        return;

                    if (evt.getColumn() != INT_TBL_DAT_CAN_MOD)
                    {
                        tblDatItm.repaint();
                        return;
                    }

                    int intFilIni = evt.getFirstRow();
                    int intFilFin = evt.getLastRow();

                    if (intFilIni < 0)
                        return;

                    if (intFilFin == javax.swing.event.TableModelEvent.HEADER_ROW)
                        return;

                    if (intFilFin < intFilIni)
                        intFilFin = intFilIni;
                    
                    

                    for (int i = intFilIni; i <= intFilFin; i++)
                    {
                        Object objCanMod = objTblModInt.getValueAt(i, INT_TBL_DAT_CAN_MOD);

                        if (!esNumeroDecimalNoNegativo(objCanMod == null ? "" : objCanMod.toString()))
                        {
                            blnCorrigiendoCanMod = true;
                            objTblModInt.setValueAt("0", i, INT_TBL_DAT_CAN_MOD);
                            blnCorrigiendoCanMod = false;

                            continue;
                        }

                        if (cantModMayorCantRep(i))
                        {
                            blnCorrigiendoCanMod = true;

                            Object objCanRep = objTblModInt.getValueAt(i, INT_TBL_DAT_CAN_REP);
                            objTblModInt.setValueAt(objCanRep, i, INT_TBL_DAT_CAN_MOD);

                            blnCorrigiendoCanMod = false;

                            javax.swing.JOptionPane.showMessageDialog(
                                    null,
                                    "La Cant.Mod. no puede ser mayor que la Cant.Rep. Se asignó la Cant.Rep. como valor máximo.",
                                    "Mensaje del sistema",
                                    javax.swing.JOptionPane.WARNING_MESSAGE
                            );

                            continue;
                        }

                        if (tieneCantGuiaMayorCero(i))
                        {
                            blnCorrigiendoCanMod = true;

                            Object objCanRep = objTblModInt.getValueAt(i, INT_TBL_DAT_CAN_REP);
                            objTblModInt.setValueAt(objCanRep, i, INT_TBL_DAT_CAN_MOD);

                            blnCorrigiendoCanMod = false;

                            continue;
                        }

                        /*
                         * Aquí se marca como modificada.
                         * No importa si Cant.Mod. quedó igual o diferente a Cant.Ant.
                         * Si el usuario editó la celda, queda marcada.
                         */
                        objTblModInt.setValueAt("S", i, INT_TBL_DAT_EST_MOD);
                    }

                    tblDatItm.repaint();
                }
                catch (Exception e)
                {
                    blnCorrigiendoCanMod = false;
                    objUtiInt.mostrarMsgErr_F1(null, e);
                }
            }
        });

        blnRes = true;
    }
    catch (Exception e)
    {
        blnRes = false;
        objUtiInt.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}   

private boolean cantModMayorCantRep(int intFil)
{
    try
    {
        java.math.BigDecimal bgdCanRep = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_REP)
        );

        java.math.BigDecimal bgdCanMod = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_MOD)
        );

        return bgdCanMod.compareTo(bgdCanRep) > 0;
    }
    catch (Exception e)
    {
        return false;
    }
}
private boolean filaFueModificada(int intFil)
{
    try
    {
        Object objEstMod = objTblModInt.getValueAt(intFil, INT_TBL_DAT_EST_MOD);

        return objEstMod != null && objEstMod.toString().trim().equalsIgnoreCase("S");
    }
    catch (Exception e)
    {
        return false;
    }
}
private ZafTblCelRenLbl crearRenderFilaDetalle(final int intAlineacion)
{
    ZafTblCelRenLbl objRen = new ZafTblCelRenLbl()
    {
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
        {
            java.awt.Component objCom = super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column
            );

            try
            {
                if (!isSelected)
                {
                    int intFilMod = table.convertRowIndexToModel(row);

                    if (filaFueModificada(intFilMod))
                    {
                        // Azul claro: fila modificada.
                        objCom.setBackground(new java.awt.Color(204, 229, 255));
                        objCom.setForeground(java.awt.Color.BLACK);
                    }
                    else if (esCantGuiaNulaVaciaOCero(intFilMod))
                    {
                        // Verde claro: Cant.Guia nulo, vacío o cero.
                        objCom.setBackground(new java.awt.Color(214, 245, 214));
                        objCom.setForeground(java.awt.Color.BLACK);
                    }
                    else
                    {
                        objCom.setBackground(java.awt.Color.WHITE);
                        objCom.setForeground(java.awt.Color.BLACK);
                    }
                }
            }
            catch (Exception e)
            {
                objCom.setBackground(java.awt.Color.WHITE);
                objCom.setForeground(java.awt.Color.BLACK);
            }

            return objCom;
        }
    };

    objRen.setHorizontalAlignment(intAlineacion);
    return objRen;
}

private void configurarEditorCantMod()
{
    javax.swing.JTextField txtCanMod = new javax.swing.JTextField();
    txtCanMod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

    /*
     * Permite solo:
     * - números
     * - un punto decimal
     * - una coma decimal
     *
     * No permite:
     * - negativos
     * - letras
     * - símbolos
     */
    ((javax.swing.text.AbstractDocument) txtCanMod.getDocument()).setDocumentFilter(
        new javax.swing.text.DocumentFilter()
        {
            @Override
            public void insertString(
                    javax.swing.text.DocumentFilter.FilterBypass fb,
                    int offset,
                    String string,
                    javax.swing.text.AttributeSet attr
            ) throws javax.swing.text.BadLocationException
            {
                if (string == null)
                    return;

                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String textoNuevo = textoActual.substring(0, offset) + string + textoActual.substring(offset);

                if (esTextoDecimalNoNegativoValido(textoNuevo))
                {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(
                    javax.swing.text.DocumentFilter.FilterBypass fb,
                    int offset,
                    int length,
                    String text,
                    javax.swing.text.AttributeSet attrs
            ) throws javax.swing.text.BadLocationException
            {
                if (text == null)
                    return;

                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String textoNuevo = textoActual.substring(0, offset) + text + textoActual.substring(offset + length);

                if (esTextoDecimalNoNegativoValido(textoNuevo))
                {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }
    );

    javax.swing.DefaultCellEditor objCelEdi = new javax.swing.DefaultCellEditor(txtCanMod)
    {
        @Override
        public boolean isCellEditable(java.util.EventObject evt)
        {
            try
            {
                int intFil = -1;

                if (evt instanceof java.awt.event.MouseEvent)
                {
                    java.awt.event.MouseEvent evtMou = (java.awt.event.MouseEvent) evt;
                    intFil = tblDatItm.rowAtPoint(evtMou.getPoint());
                }
                else
                {
                    intFil = tblDatItm.getSelectedRow();
                }

                if (intFil >= 0)
                {
                    int intFilMod = tblDatItm.convertRowIndexToModel(intFil);

                    if (tieneCantGuiaMayorCero(intFilMod))
                    {
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "No puede modificar la Cant.Mod. porque este ítem ya tiene Cant.Guia mayor a cero.",
                                "Mensaje del sistema",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );

                        return false;
                    }
                }
            }
            catch (Exception e)
            {
                return false;
            }

            return super.isCellEditable(evt);
        }

        @Override
        public boolean stopCellEditing()
        {
            try
            {
                String valor = ((javax.swing.JTextField) getComponent()).getText();

                if (valor == null || valor.trim().equals(""))
                {
                    ((javax.swing.JTextField) getComponent()).setText("0");
                    return super.stopCellEditing();
                }

                valor = valor.trim();

                if (!esNumeroDecimalNoNegativo(valor))
                {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "La Cant.Mod. solo permite valores numéricos mayores o iguales a cero.",
                            "Mensaje del sistema",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );

                    return false;
                }

                java.math.BigDecimal bgdCanMod = obtenerBigDecimal(valor);

                if (bgdCanMod.compareTo(java.math.BigDecimal.ZERO) < 0)
                {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "La Cant.Mod. no puede ser negativa.",
                            "Mensaje del sistema",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );

                    return false;
                }

                int intFilVista = tblDatItm.getEditingRow();

                if (intFilVista >= 0)
                {
                    int intFilMod = tblDatItm.convertRowIndexToModel(intFilVista);

                    java.math.BigDecimal bgdCanRep = obtenerBigDecimal(
                            objTblModInt.getValueAt(intFilMod, INT_TBL_DAT_CAN_REP)
                    );

                    if (bgdCanMod.compareTo(bgdCanRep) > 0)
                    {
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "La Cant.Mod. no puede ser mayor que la Cant.Rep.",
                                "Mensaje del sistema",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );

                        return false;
                    }
                }

                ((javax.swing.JTextField) getComponent()).setText(
                        bgdCanMod.stripTrailingZeros().toPlainString()
                );
            }
            catch (Exception e)
            {
                return false;
            }

            return super.stopCellEditing();
        }
    };

    tblDatItm.getColumnModel()
            .getColumn(INT_TBL_DAT_CAN_MOD)
            .setCellEditor(objCelEdi);
}
private boolean esTextoDecimalNoNegativoValido(String texto)
{
    try
    {
        if (texto == null)
            return true;

        texto = texto.trim();

        if (texto.equals(""))
            return true;

        if (texto.contains("-"))
            return false;

        int contadorSeparadorDecimal = 0;

        for (int i = 0; i < texto.length(); i++)
        {
            char c = texto.charAt(i);

            if (Character.isDigit(c))
            {
                continue;
            }

            if (c == '.' || c == ',')
            {
                contadorSeparadorDecimal++;

                if (contadorSeparadorDecimal > 1)
                    return false;

                continue;
            }

            return false;
        }

        return true;
    }
    catch (Exception e)
    {
        return false;
    }
}
private boolean esNumeroDecimalNoNegativo(String valor)
{
    try
    {
        if (valor == null)
            return false;

        valor = valor.trim();

        if (valor.equals(""))
            return false;

        if (valor.contains("-"))
            return false;

        java.math.BigDecimal bgdVal = obtenerBigDecimal(valor);

        return bgdVal.compareTo(java.math.BigDecimal.ZERO) >= 0;
    }
    catch (Exception e)
    {
        return false;
    }
}

private boolean esCantGuiaNulaVaciaOCero(int intFil)
{
    try
    {
        java.math.BigDecimal bgdCanGui = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_GUI)
        );

        return bgdCanGui.compareTo(java.math.BigDecimal.ZERO) == 0;
    }
    catch (Exception e)
    {
        return true;
    }
}

private boolean tieneCantGuiaMayorCero(int intFil)
{
    try
    {
        java.math.BigDecimal bgdCanGui = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_GUI)
        );

        return bgdCanGui.compareTo(java.math.BigDecimal.ZERO) > 0;
    }
    catch (Exception e)
    {
        return false;
    }
}
private boolean cantModDiferenteCantAnt(int intFil)
{
    try
    {
        java.math.BigDecimal bgdCanMod = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_MOD)
        );

        java.math.BigDecimal bgdCanAnt = obtenerBigDecimal(
                objTblModInt.getValueAt(intFil, INT_TBL_DAT_CAN_ANT)
        );

        return bgdCanMod.compareTo(bgdCanAnt) != 0;
    }
    catch (Exception e)
    {
        return false;
    }
}
private java.math.BigDecimal obtenerBigDecimal(Object objVal)
{
    try
    {
        if (objVal == null)
            return java.math.BigDecimal.ZERO;

        String strVal = objVal.toString().trim();

        if (strVal.equals(""))
            return java.math.BigDecimal.ZERO;

        /*
         * Soporta:
         * 1.000
         * 1,000.00
         * 1,50
         */
        if (strVal.indexOf(",") >= 0 && strVal.indexOf(".") >= 0)
        {
            strVal = strVal.replace(",", "");
        }
        else if (strVal.indexOf(",") >= 0 && strVal.indexOf(".") < 0)
        {
            strVal = strVal.replace(",", ".");
        }

        return new java.math.BigDecimal(strVal);
    }
    catch (Exception e)
    {
        return java.math.BigDecimal.ZERO;
    }
}

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton butgua;
    javax.swing.JButton jButton2;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JLabel lblTitulo;
    javax.swing.JTable tblDatItm;
    // End of variables declaration//GEN-END:variables
    
}
