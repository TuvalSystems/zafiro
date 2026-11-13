/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer16
package Librerias.ZafRemayo;

import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafRemayo 
{
   private ZafUtil objUti;
   private Statement stm;
   private ResultSet rst;
   private String strSQL;
   
   public ZafRemayo()
   {
      objUti = new ZafUtil();
   }
   
   /**
     * Este metodo permite desmayorizar/mayorizar las cuentas de acuerdo al criterio seleccionado.
     * @return true: Si se pudo realizar el proceso.
     * <BR>false: En el caso contrario.
     */
    public boolean remayorizarPeriodo(java.sql.Connection con, int intCodEmp, int intAnoCor, int intMesCor)
    {
        int i, j, k;
        String strPer;
        String strFecRan[][];
        boolean blnRes=true;
        double dblSalCta=0.00;
        String strMes="", strAni="", strMesCor, strFecDes, strFecHas;
        int intMes=0;
        
        try
        {
            //butCon.setText("Detener");
            //lblMsgSis.setText("Desmayorizando cuentas...");
            
            if (con != null)
            {  stm = con.createStatement();
               strMesCor = (intMesCor < 10)? "0" + (intMesCor) :"" + (intMesCor);
               strFecDes = "01/01/" + intAnoCor; //dd/mm/aaaa
               strFecHas = "01/" + strMesCor + "/" + intAnoCor; //dd/mm/aaaa
               //Procesar las cuentas de la "Empresa seleccionada".
               strFecRan = objUti.getIntervalosMensualesRangoFechas(strFecDes, strFecHas, "dd/MM/yyyy");
               
               //Desmayorizar
               //------------
               //pgrSis.setMinimum(0);
               //pgrSis.setMaximum(strFecRan.length);
               //pgrSis.setValue(0);
               k = 0;
               
               for (i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {   //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCta";
                       strSQL+=" SET nd_salCta=" + dblSalCta + "";
                       strSQL+=" WHERE co_emp=" + intCodEmp;
                       strSQL+=" AND co_per=" + strPer;
                       stm.executeUpdate(strSQL);
                   }
                   else
                   {   switch(intMes)
                       {   case 1:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Enero </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 2:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Febrero </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 3:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Marzo </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 4:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Abril </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 5:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Mayo </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 6:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Junio </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 7:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Julio </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 8:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Agosto </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 9:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Septiembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 10:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Octubre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 11:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Noviembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 12:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Diciembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                       } //switch(intMes)
                   } //if (! isMesCerrado(strMes, strAni))

                   k++;
                   //pgrSis.setValue(k);
               } //for (i = 0; i < strFecRan.length; i++)
               
               //Mayorizar
               //---------
               //pgrSis.setMinimum(0);
               //pgrSis.setMaximum(7*strFecRan.length);
               //pgrSis.setValue(0);
               k = 0;
               
               for (i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {   //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+=" UPDATE tbm_salcta";
                       strSQL+=" set nd_salCta=x.nd_salcta from (";
                       strSQL+=" SELECT b1.co_emp, b1.co_cta, " + strPer + " AS co_per, b2.nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS b1";
                       strSQL+=" LEFT OUTER JOIN (";
                       //strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta";
                       strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM( (case when a2.nd_monDeb is null then 0 else a2.nd_monDeb end) - (case when a2.nd_monHab is null then 0 else a2.nd_monHab end) ) AS nd_salCta";
                       strSQL+=" FROM tbm_cabDia AS a1";
                       strSQL+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.st_reg='A'";
                       strSQL+=" AND (a1.fe_dia BETWEEN '" + strFecRan[i][0] + "' AND '" + strFecRan[i][1] + "')";
                       strSQL+=" GROUP BY a1.co_emp, a2.co_cta";
                       strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                       strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                       strSQL+=" ORDER BY b1.co_emp, b1.co_cta";
                       strSQL+=" ) as x";
                       strSQL+=" WHERE x.co_emp=tbm_salcta.co_emp AND x.co_cta=tbm_salcta.co_cta AND x.co_per=tbm_salcta.co_per";
                       //System.out.println("update 1: " +strSQL);
                       stm.executeUpdate(strSQL);
                       k++;
                       //pgrSis.setValue(k);
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCta";
                       strSQL+=" SET nd_salCta=(CASE WHEN tbm_salCta.nd_salCta IS NULL THEN 0 ELSE tbm_salCta.nd_salCta END) + b1.nd_salCta";
                       strSQL+=" FROM (";
                       strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS a1";
                       strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                       strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                       strSQL+=" AND a2.co_per=" + strPer;
                       strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                       strSQL+=" ) AS b1";
                       strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                       //System.out.println("update 2: " +strSQL);
                       stm.executeUpdate(strSQL);
                       k++;
                       //pgrSis.setValue(k);
                       
                       for (j = 8; j > 1; j--)
                       {   //Armar la sentencia SQL.
                           strSQL="";
                           strSQL+="UPDATE tbm_salCta";
                           strSQL+=" SET nd_salCta=b1.nd_salCta";
                           strSQL+=" FROM (";
                           //strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" SELECT a1.co_emp, a2.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" FROM tbm_plaCta AS a1";
                           strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                           //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                           strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                           //strSQL+=" AND a1.ne_niv=" + j;
                           strSQL+=" AND a2.ne_niv=" + j;
                           strSQL+=" AND a2.co_per=" + strPer;
                           //strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                           strSQL+=" GROUP BY a1.co_emp, a2.ne_pad";
                           strSQL+=" ) AS b1";
                           strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                           //System.out.println("UPDATE 3: " +strSQL);
                           stm.executeUpdate(strSQL);
                           k++;
                           //pgrSis.setValue(k);
                       }
                   } //if (! isMesCerrado(strMes, strAni))
                   else
                   {   switch(intMes)
                       {   case 1:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Enero </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 2:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Febrero </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 3:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Marzo </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 4:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Abril </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 5:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Mayo </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 6:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Junio </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 7:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Julio </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 8:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Agosto </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 9:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Septiembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 10:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Octubre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 11:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Noviembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                           case 12:
                               //mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Diciembre </FONT> está cerrado.<BR>Se procesan sólo los meses que tengan período abierto.</HTML>");
                               break;
                       } //switch(intMes)
                   } //if (! isMesCerrado(strMes, strAni))
               } //for (i = 0; i < strFecRan.length; i++)
                        
               stm.close();
               stm = null;

               //lblMsgSis.setText("Listo");
               //pgrSis.setValue(0);
               //butCon.setText("Consultar");

               if (! procesarCuentasEfectivo(con, intCodEmp, intAnoCor, intMesCor))
               {    
                  blnRes = false;
               }
            } //if (con != null)
        } //try
        
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Metodo remayorizarPeriodo()
    
    /**
     * Esta funcion establece si el mes que se desea procesar 
     * ha sido cerrado.
     * @param periodo El periodo que se desea saber si ha sido cerrado.
     */
    private boolean isMesCerrado(java.sql.Connection con, int intCodEmp, String mes, String anio)
    {
        boolean blnRes=false;
        Statement stmMesCer;
        ResultSet rstMesCer;
        
        try
        {
            if (con != null)
            {
                stmMesCer=con.createStatement();
                strSQL="";
                strSQL+="SELECT ne_mes FROM tbm_cabCieSis AS a1";
                strSQL+=" INNER JOIN tbm_detCieSis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.ne_ani=" + anio + "";
                strSQL+=" AND a2.ne_mes=" + mes + "";
                //System.out.println("SQL ISMESCERRADO: " + strSQL);
                rstMesCer = stmMesCer.executeQuery(strSQL);
                if (rstMesCer.next())
                {
                    blnRes=true;
                }
                stmMesCer.close();
                stmMesCer=null;
                rstMesCer.close();
                rstMesCer=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion isMesCerrado()

    /**
     * Esta funcion permite mayorizar/desmayorizar las cuentas de efectivo de acuerdo al criterio seleccionado.
     * @return true: Si se pudo realizar el proceso.
     * <BR>false: En el caso contrario.
     */
    private boolean procesarCuentasEfectivo(java.sql.Connection con, int intCodEmp, int intAnoCor, int intMesCor)
    {
        String strPer;
        String strFecRan[][];
        boolean blnRes=true;
        double dblSalCta=0.00;
        String strMes="", strAni="", strMesCor, strFecDes, strFecHas;
        int intMes=0;
        
        try
        {
            //butCon.setText("Detener");
            //lblMsgSis.setText("Desmayorizando cuentas de efectivo...");
            
            if (con != null)
            {
               stm = con.createStatement();
               strMesCor = (intMesCor < 10)? "0" + (intMesCor) :"" + (intMesCor);
               strFecDes = "01/01/" + intAnoCor; //dd/mm/aaaa
               strFecHas = "01/" + strMesCor + "/" + intAnoCor; //dd/mm/aaaa
               //Procesar las cuentas de la "Empresa seleccionada".
               strFecRan = objUti.getIntervalosMensualesRangoFechas(strFecDes, strFecHas, "dd/MM/yyyy");
               
               //Desmayorizar
               //------------
               for (int i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCtaEfe";
                       strSQL+=" SET nd_salCta=" + dblSalCta + "";
                       strSQL+=" WHERE co_emp=" + intCodEmp;
                       strSQL+=" AND co_per=" + strPer;
                       stm.executeUpdate(strSQL);
                   }
               }
                    
               //Mayorizar
               //---------
               for (int i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+=" update tbm_salctaEfe";
                       strSQL+=" set nd_salCta=x.nd_salcta from (";
                       strSQL+=" SELECT b1.co_emp, b1.co_cta, " + strPer + " AS co_per, b2.nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS b1";
                       strSQL+=" LEFT OUTER JOIN (";
                       //inicio de lo nuevo
                       //strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta";
                       strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM( (case when a2.nd_monDeb is null then 0 else a2.nd_monDeb end) - (case when a2.nd_monHab is null then 0 else a2.nd_monHab end) ) AS nd_salCta";
                       strSQL+=" FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ";
                       strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" INNER JOIN (";
                       strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                       strSQL+=" 	FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ";
                       strSQL+=" 	ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.st_reg='A'";
                       strSQL+=" AND (a1.fe_dia BETWEEN '" + strFecRan[i][0] + "' AND '" + strFecRan[i][1] + "')";
                       strSQL+=" 	AND a2.co_cta IN ";
                       strSQL+="                 (SELECT co_cta FROM tbm_plaCta WHERE co_emp=" + intCodEmp + " AND st_reg NOT IN('I','E') AND st_ctaFluEfe='S')";
                       strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                       strSQL+=" 	ORDER BY a1.co_tipDoc, a1.co_dia";
                       strSQL+=" ) as x";
                       strSQL+=" ON a1.co_emp=x.co_emp AND a1.co_loc=x.co_loc AND a1.co_tipDoc=x.co_tipDoc AND a1.co_dia=x.co_dia";
                       strSQL+=" INNER JOIN tbm_plaCta AS y";
                       strSQL+=" ON a1.co_emp=y.co_emp AND a2.co_cta=y.co_cta";
                       //strSQL+=" WHERE y.st_ctaFluEfe='N'";
                       strSQL+=" GROUP BY a1.co_emp, a2.co_cta";
                       //fin de lo nuevo
                       strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                       strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                       strSQL+=" ORDER BY b1.co_emp, b1.co_cta";
                       strSQL+=" ) as x";
                       strSQL+=" WHERE x.co_emp=tbm_salctaEfe.co_emp AND x.co_cta=tbm_salctaEfe.co_cta AND x.co_per=tbm_salctaEfe.co_per";
                       //System.out.println("INSERT: " +strSQL);
                       stm.executeUpdate(strSQL);
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCtaEfe";
                       strSQL+=" SET nd_salCta=(CASE WHEN tbm_salCtaEfe.nd_salCta IS NULL THEN 0 ELSE tbm_salCtaEfe.nd_salCta END) + b1.nd_salCta";
                       strSQL+=" FROM (";
                       strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS a1";
                       strSQL+=" INNER JOIN tbm_salCtaEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                       strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                       strSQL+=" AND a2.co_per=" + strPer;
                       strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                       strSQL+=" ) AS b1";
                       strSQL+=" WHERE tbm_salCtaEfe.co_emp=b1.co_emp AND tbm_salCtaEfe.co_cta=b1.co_cta AND tbm_salCtaEfe.co_per=b1.co_per";
                       //System.out.println("UPDATE: " +strSQL);
                       stm.executeUpdate(strSQL);
                       
                       for (int j = 8; j > 1; j--)
                       {
                           //Armar la sentencia SQL.
                           strSQL="";
                           strSQL+="UPDATE tbm_salCtaEfe";
                           strSQL+=" SET nd_salCta=b1.nd_salCta";
                           strSQL+=" FROM (";
                           strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" FROM tbm_plaCta AS a1";
                           strSQL+=" INNER JOIN tbm_salCtaEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                           strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                           strSQL+=" AND a1.ne_niv=" + j;
                           strSQL+=" AND a2.co_per=" + strPer;
                           strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                           strSQL+=" ) AS b1";
                           strSQL+=" WHERE tbm_salCtaEfe.co_emp=b1.co_emp AND tbm_salCtaEfe.co_cta=b1.co_cta AND tbm_salCtaEfe.co_per=b1.co_per";
                           //System.out.println("UPDATE EN FOR: " +strSQL);
                           stm.executeUpdate(strSQL);
                       }
                   } //if (! isMesCerrado(strMes, strAni))
               } //for (int i = 0; i < strFecRan.length; i++)
                
                stm.close();
                stm=null;
                
                //lblMsgSis.setText("Listo");
                //pgrSis.setValue(0);
                //butCon.setText("Consultar");
            } //if (con != null)
        } //try
        
        catch (java.sql.SQLException e){
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion procesarCuentasEfectivo()  
}