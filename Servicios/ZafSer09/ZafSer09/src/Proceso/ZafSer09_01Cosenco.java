package Proceso;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.tuval.util.ArchivosTuval;
import com.tuval.util.TuvalUtilitiesException;


import Librerias.ZafRecHum.ZafVenFun.Mail;
import com.tuval.util.ZafParSis_Cosenco;
import com.tuval.util.ZafSueBas;
import com.tuval.util.ZafUtil;
import java.math.BigDecimal;

/**
 * Servicio para la generación de Roles de Pago.
 *
 * @author Tony Sanginez
 */
public class ZafSer09_01Cosenco
{
    private String strVersion = "v4.6";
    private String strDirectorio ;
    private int intEnviarCorreo;
    ArchivosTuval log;
    private ZafSueBas objSueBas = new ZafSueBas();//Se crea un objeto de la clase ZafSueBas para obtener el Sueldo Basico. tony
    
    public String getStrVersion()
    {
        return strVersion;
    }

    public void setStrVersion(String strVersion)
    {
        this.strVersion = strVersion;
    }

    public ZafSer09_01Cosenco(){}

    public void iniciar() throws SQLException
    {
        BigDecimal bgdSue=BigDecimal.ZERO; //Sueldo
    	ZafUtil zafUti = new ZafUtil(); 
        strDirectorio = new com.tuval.util.ZafUtil().getDirectorioSistema();
        String strFec2 = new SimpleDateFormat("ddMMyyyy hh.mm.ss").format(new Date());
        System.out.println(strDirectorio + File.separatorChar + "logs"+ File.separatorChar +"ZafSer09."+strFec2);
        log = new ArchivosTuval(strDirectorio + File.separatorChar + "logs"+ File.separatorChar +"ZafSer09."+strFec2);
        
        imprimirEnConsola("************************************************");
        imprimirEnConsola(getClass() + " Version:" + this.strVersion);
        imprimirEnConsola("Hora de ejecución del proceso: " + new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date()));
        imprimirEnConsola("************************************************");
        
        int intDiasContables    = 30;
        int intDiasContablesHE1 = 4;

        boolean blnResult = false;
        ZafParSis_Cosenco zafParSis = null;
        
        ArrayList<Tbm_tra> listTra = null;
        ArrayList<Tbm_ingegrmentra> listIngEgrMenTra = null;
        String strSql = "";

        Connection unCon  = null;
        ResultSet rstRes  = null;
        ResultSet rstRes3 = null;
        Statement stmLoc  = null;
        Statement stmLoc3 = null;
        
        ResultSet rstAux  = null;
        Statement stmAux  = null;
        Statement stmAux2 = null;

        Statement stmLocAux  = null;
        Statement stmLocAux2 = null;

        ResultSet rstLoc    = null;
        ResultSet rstLocAux = null;
        ResultSet rstAuxI   = null;
        Statement stmAuxI   = null;
        //Prepara Archivo de logs
        Calendar calendario = new GregorianCalendar();
        int intMes = calendario.get(Calendar.MONTH) +1 ;
        int intDia = calendario.get(Calendar.DATE);
        int intAno = calendario.get(Calendar.YEAR);
        ArrayList<Integer> arrLstEmp = null;
        try
        {
            if ((unCon == null) || (unCon.isClosed()))
            {
                zafParSis = new ZafParSis_Cosenco();
                intEnviarCorreo = zafParSis.getINT_ENVIAR_CORREO();
                imprimirEnConsola( "Conexion: " + zafParSis.getStringConexion() + "     Correo:" + intEnviarCorreo );
                unCon = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
                stmLoc = unCon.createStatement();

                strSql = "";
                strSql += "select distinct  co_emp from tbm_emp where co_emp not in (0,3) and st_reg like 'A'";// and co_emp=2";//noooooooooooooo";//
                strSql += " order by co_emp asc";
                rstRes = stmLoc.executeQuery(strSql);
                
                if (rstRes.next())
                {
                    arrLstEmp = new ArrayList<Integer>();
                    do
                    {
                        arrLstEmp.add(rstRes.getInt("co_emp"));
                        restablecerSemanaIntegral(unCon, intAno, intMes, rstRes.getInt("co_emp"), 0);
                    } while (rstRes.next());
                }
                else
                {
                    System.exit(0);
                }
                
                for (int intCoEmp : arrLstEmp)
                {
                    strSql = "SELECT distinct a.co_tra,b.tx_nom,b.tx_ape, fe_inicon, Tx_ForPagDecTerSue, Tx_ForPagDecCuaSue, st_reccomven,"; //Aumento de dos campos nuevos para Visualizar la forma de pago Dmo 3 o Dmo4. tony
                    strSql += " case ( (EXTRACT (MONTH FROM fe_inicon)=" + intMes + ") AND (EXTRACT (YEAR FROM fe_inicon)=" + intAno + ") ) when true then true else false end as blnIsTraNew";
                    strSql += " FROM tbm_traemp a";
                    strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSql += " WHERE a.st_reg like 'A'";// and a.co_tra = 125 ";//noooooooooooooo
                    strSql += " and co_emp = " + intCoEmp;
                    strSql += " order by a.co_tra";
                    rstRes = stmLoc.executeQuery(strSql);
                    //System.out.println(strSql);
                    if (rstRes.next())
                    {
                        listTra = new ArrayList<Tbm_tra>();
                        do
                        {
                            Tbm_tra tbmTra = new Tbm_tra();
                            tbmTra.setIntCo_tra(rstRes.getInt("co_tra"));
                            tbmTra.setStrTx_nom(rstRes.getString("tx_nom"));
                            tbmTra.setStrTx_ape(rstRes.getString("tx_ape"));
                            tbmTra.setBlnIsTraNew(rstRes.getBoolean("blnIsTraNew"));
                            tbmTra.setStrFeIniCon(rstRes.getString("fe_inicon"));
                            tbmTra.setStrTx_ForPagDecTerSue(rstRes.getString("Tx_ForPagDecTerSue"));//Se agrego para obtener la forma de pago del decimo tercer. tony
                            tbmTra.setStrTx_ForPagDecCuaSue(rstRes.getString("Tx_ForPagDecCuaSue"));//Se agrego para obtener la forma de pago del decimo cuarto. tony
                            tbmTra.setStrSt_reccomven(rstRes.getString("st_reccomven"));//Se agrego para obtener el estado de un trabajador para ver si recibe comision. tony
                            listTra.add(tbmTra);
                        } while (rstRes.next());
                    }
                    else
                    {
                        System.exit(0);
                    }
                    
                    if (unCon != null)
                    {
                        unCon.setAutoCommit(false);
                        if (listTra != null) {
                            stmAux = unCon.createStatement();
                            stmAux2 = unCon.createStatement();
                            stmLoc = unCon.createStatement();
                            stmLocAux = unCon.createStatement();
                            stmLocAux2 = unCon.createStatement();
                            stmLoc3 =unCon.createStatement();
                            stmAuxI = unCon.createStatement();
                            //Eddye: Cambié el "1" por el día actual para que se ejecute éste código.
                            if (intDia == 1)
                            {
                                for (Tbm_tra tbm_tra : listTra)
                                {
                                    /*
                                     * INSERTAR RUBROS DE EMPLEADOS CORRESPONDIENTES A TODO EL MES EL PRIMER DIA DEL MES
                                     */
                                    strSql = "select distinct a.co_rub,a.tx_tipvalrub,a.tx_tiprub,a.tx_nom from tbm_rubRolPag a";
                                    strSql += " inner join tbm_rubrolpagemp b on (a.co_rub=b.co_rub)";
                                    strSql += " where a.st_reg like 'A'";
                                    strSql += " and b.co_emp = " + intCoEmp;
                                    strSql += " order by a.co_rub asc";
                                    rstRes = stmLoc.executeQuery(strSql);
                                    String strNd_porRubPag = "NULL";
                                    int intMesAnt, intAnioAnt;
                                    intMesAnt=intMes-1;
                                    intAnioAnt=intAno;
                                    if (intMes-1==0) {
                                        intMesAnt=12;
                                        intAnioAnt=intAno-1;
                                    }
                                     
                                    while (rstRes.next())
                                    {
                                        if (!tbm_tra.isBlnIsTraNew()) {
                                            strSql = " select co_tra, ne_per,co_rub, nd_porrubpag"; 
                                            strSql += " from tbm_ingegrmentra";
                                            strSql += " where co_emp=" + intCoEmp;
                                            strSql += " and   co_tra=" + tbm_tra.getIntCo_tra();
                                            strSql += "  and   ne_ani=" + intAnioAnt;
                                            strSql += "  and   ne_mes=" + intMesAnt;
                                            strSql += "  and   ne_per in(1,2)";
                                            strSql += " order by ne_per,co_rub";
                                            rstAuxI = stmAuxI.executeQuery(strSql);

                                            if (rstAuxI.next())
                                            {
                                                listIngEgrMenTra = new ArrayList<Tbm_ingegrmentra>();
                                                do
                                                {
                                                    Tbm_ingegrmentra tbmingegrmentra = new Tbm_ingegrmentra();
                                                    //tbmingegrmentra.setIntCo_tra(rstRes.getInt("co_tra"));
                                                    tbmingegrmentra.setIntNe_per(rstAuxI.getInt("ne_per"));
                                                    tbmingegrmentra.setIntCo_rub(rstAuxI.getInt("co_rub"));
                                                    tbmingegrmentra.setDouNd_porrubpag(rstAuxI.getString("nd_porrubpag"));
                                                    listIngEgrMenTra.add(tbmingegrmentra);
                                                } while (rstAuxI.next());
                                            }
                                            else
                                            {
                                                System.out.println("Error - Rubros anteriores del empleado "+ tbm_tra.getIntCo_tra()+ " no existen.");
                                                System.exit(0);
                                            }
                                        }
                                        
                                        for (int intNePer = 0; intNePer <= 2; intNePer++)
                                        {
                                            if (tbm_tra.isBlnIsTraNew()) {
                                                    if (rstRes.getString("tx_tiprub").equals("I")) //Rubros de ingreso.
                                            {
                                                //Eddye: Se modificó el código para que los rubros "4, 22 y 23" trabajen con el 40%.
                                                //Además se hizo que en quincena se pague el 0% en Bono y 100% en Movilización y en fin de mes a la inversa.
                                                if (intNePer == 0) //Periodo 0
                                                {
                                                    strNd_porRubPag = "NULL";
                                                }
                                                else if (intNePer == 1) //Primera Quincena. Primer periodo.
                                                {
                                                    //if (rstRes.getInt("co_rub") ==3 || rstRes.getInt("co_rub") ==5 || rstRes.getInt("co_rub") ==4 || rstRes.getInt("co_rub") == 22 || rstRes.getInt("co_rub") == 23)
                                                    if (rstRes.getInt("co_rub") ==3 || rstRes.getInt("co_rub") ==5 || rstRes.getInt("co_rub") ==2 || rstRes.getInt("co_rub") ==3 || rstRes.getInt("co_rub") ==4)
                                                    {
                                                        strNd_porRubPag = "NULL";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==6) //Bono
                                                    {
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==7) //Movilización
                                                    {
                                                        strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==22) //Otros ingresos
                                                    {
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==31) {//Decimo tercer sueldo. tony
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==32) {//Decimo cuarto sueldo. tony
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==33) {//Comision. tony
                                                         strNd_porRubPag = "100";
                                                    }
                                                    else
                                                    {
                                                        strNd_porRubPag = "40";
                                                    }
                                                }
                                                else if (intNePer == 2)
                                                {
                                                    //if (rstRes.getInt("co_rub") == 3 || rstRes.getInt("co_rub") ==5 || rstRes.getInt("co_rub") ==4 || rstRes.getInt("co_rub") == 22 || rstRes.getInt("co_rub") == 23)
                                                    if (rstRes.getInt("co_rub") == 3 || rstRes.getInt("co_rub") ==5 || rstRes.getInt("co_rub") ==2 || rstRes.getInt("co_rub") ==3 || rstRes.getInt("co_rub") ==4)
                                                    {
                                                        strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==6) //Bono
                                                    {
                                                        strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==7) //Movilización
                                                    {
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==22) //Otros Ingresos
                                                    {
                                                        strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==31){ //Decimo tercer sueldo. tony
                                                         strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==32){ //Decimo cuarto sueldo. tony
                                                         strNd_porRubPag = "100";
                                                    }
                                                    else if (rstRes.getInt("co_rub")==33){ //Comision. tony
                                                         strNd_porRubPag = "0";
                                                    }
                                                    else
                                                    {
                                                        strNd_porRubPag = "60";
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (intNePer==0)
                                                {
                                                    strNd_porRubPag = "NULL";
                                                }
                                                else if (intNePer == 1)
                                                {
                                                        strNd_porRubPag = "NULL";
                                                }
                                                else if (intNePer == 2)// Todos los egresos se descuentan a fin de mes
                                                {
                                                        strNd_porRubPag = "100";   
                                                }   
                                            }
                                                    if ( !grabarIngEgrMenTra(unCon, intCoEmp, tbm_tra.getIntCo_tra(), intAno, intMes, intNePer, rstRes.getInt("co_rub"), "NULL", strNd_porRubPag, "NULL"))
                                            {
                                                 throw new SQLException();
                                            }
                                            }else{
                                            for (Tbm_ingegrmentra tbm_ingegrmentra : listIngEgrMenTra)
                                            {
                                                if (intNePer == 0) //Periodo 0
                                                {
                                                    strNd_porRubPag = "NULL";
                                                    break;
                                                }
                                                else if (intNePer == tbm_ingegrmentra.getIntNe_per()) //Primera Quincena. Primer periodo.
                                                {
                                                    if (rstRes.getInt("co_rub") ==tbm_ingegrmentra.getIntCo_rub())
                                                    {
                                                        strNd_porRubPag = tbm_ingegrmentra.getStrNd_porrubpag();
                                                        break;
                                                    }
                                                }
                                            }
                                            //System.out.println(intCoEmp + " - " + tbm_tra.getIntCo_tra() + " - " + intAno + " - " + intMes + " - " + intNePer + " - " + rstRes.getInt("co_rub") + " - " + "NULL" + " - " + strNd_porRubPag + " - " + "NULL");
                                            if ( !grabarIngEgrMenTra(unCon, intCoEmp, tbm_tra.getIntCo_tra(), intAno, intMes, intNePer, rstRes.getInt("co_rub"), "NULL", strNd_porRubPag, "NULL"))
                                            {
                                                 throw new SQLException();
                                            }
                                            }
                                        }
                                    }
                                    
                                    if (!grabarDatGenIngEgrMenTra(unCon, intCoEmp, tbm_tra.getIntCo_tra(), intAno, intMes, 0, "NULL", "NULL", 30))
                                    {
                                        throw new SQLException();
                                    }
                                }
                                
                            }
                            else
                            {
                                //Eddye: Se procesa cada empresa desde el día 2 hasta el último día del mes (TUVAL, CASTEK, DIMULTI).
                                /*VERIFICAR EXISTENCIA DE NUEVOS TRABAJADORES EN TBM_TRAEMP*/
                                strSql = "select * from tbm_traemp where not exists (select * from tbm_ingegrmentra where tbm_traemp.co_tra=tbm_ingegrmentra.co_tra)";
                                strSql += " and co_emp = " + intCoEmp + " and st_reg like 'A'";
                                rstRes = stmLoc.executeQuery(strSql);
                                while (rstRes.next())
                                {
                                    strSql = "select distinct a.co_rub,a.tx_tipvalrub,a.tx_tiprub,a.tx_nom from tbm_rubRolPag a";
                                    strSql += " inner join tbm_rubrolpagemp b on (a.co_rub=b.co_rub)";
                                    strSql += " where a.st_reg like 'A'";
                                    strSql += " and b.co_emp = " + intCoEmp;
                                    strSql += " order by a.co_rub asc";
                                    rstAux = stmAux.executeQuery(strSql);
                                    String strNd_porRubPag = "NULL";
                                    while (rstAux.next())
                                    {
                                        for (int intNePer = 0; intNePer <= 2; intNePer++)
                                        {
                                            if (rstAux.getString("tx_tiprub").equals("I"))
                                            {
                                                if (intNePer == 0)
                                                {
                                                    strNd_porRubPag = "NULL";
                                                }
                                                else if (intNePer == 1){
                                                    if (rstAux.getInt("co_rub") == 3 || rstAux.getInt("co_rub") == 5 || rstAux.getInt("co_rub") == 2 || rstAux.getInt("co_rub") == 4 )
                                                    {
                                                        strNd_porRubPag = "NULL";
                                                    }
                                                    else if (rstAux.getInt("co_rub")==33 || rstAux.getInt("co_rub")==7) {//Comision. tony
                                                        strNd_porRubPag = "100";
                                                    }else if (rstAux.getInt("co_rub")==6 || rstAux.getInt("co_rub")==22 || rstAux.getInt("co_rub")==31 || rstAux.getInt("co_rub")==32) {
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else
                                                    {
                                                        strNd_porRubPag = "40";
                                                    }
                                                }
                                                else if (intNePer == 2)
                                                {
                                                    if (rstAux.getInt("co_rub") == 3 || rstAux.getInt("co_rub") == 5 || rstAux.getInt("co_rub") == 6 || rstAux.getInt("co_rub") == 31 || rstAux.getInt("co_rub") == 32 || rstAux.getInt("co_rub") == 2 || rstAux.getInt("co_rub") == 4)
                                                    {
                                                        strNd_porRubPag = "100";
                                                    }else if (rstAux.getInt("co_rub") == 33 || rstAux.getInt("co_rub")==7) {
                                                        strNd_porRubPag = "0";
                                                    }
                                                    else
                                                    {
                                                        strNd_porRubPag = "60";
                                                    } 
                                                }
                                            }
                                            else
                                            {
                                                if (intNePer == 0)
                                                {
                                                    strNd_porRubPag = "NULL";
                                                }
                                                else if (intNePer == 1)
                                                {
                                                        strNd_porRubPag = "NULL";   
                                                }
                                                else if (intNePer == 2)
                                                {
                                                        strNd_porRubPag = "100";   
                                                }
                                            }
                                            //imprimirEnConsola( rstRes.getInt("co_tra") +" " +  rstAux.getInt("co_rub") + " " + strNd_porRubPag);
                                            if (!grabarIngEgrMenTra(unCon, intCoEmp, rstRes.getInt("co_tra"), intAno, intMes, intNePer, rstAux.getInt("co_rub"), "null", strNd_porRubPag, "null"))
                                            {
                                                new SQLException();
                                            }
                                        }
                                    }
                                }
                                
                                /*VERIFICAR EXISTENCIA DE NUEVOS TRABAJADORES EN TBM_TRAEMP*/
                                /* 
                                 * 
                                 * ACTUALIZACION DE SUELDOS A LOS TRABAJADORES TODO EL MES
                                 * 2.1) Obtener los valores que se van a asignar a los registros correspondientes a "Todo el mes".	
                                 * Ingresos:	
                                 * De los ingresos de la tabla "tbm_sueTra".
                                 * 
                                 */
                                for (Iterator<Tbm_tra> itLstTra = listTra.iterator(); itLstTra.hasNext();)
                                {
                                    Tbm_tra tbm_tra = itLstTra.next();
                                    strSql = "";
                                    strSql += " select a.*,b.*,c.*,c.nd_valrub as nd_valRubCom from tbm_suetra a";
                                    strSql += " inner join tbm_rubrolpag b on (a.co_rub=b.co_rub and b.tx_tiprub='I')";
                                    strSql += " left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.co_rub=a.co_rub and c.ne_ani=" + intAno + " and c.ne_mes=" + intMes + ")";
                                    strSql += " where a.co_tra = " + tbm_tra.getIntCo_tra();
                                    strSql += " and a.co_emp = " + intCoEmp;
                                    strSql += " and a.co_rub not in(3,4,5)";
                                    strSql += " order by a.co_rub asc";
                                    rstRes = stmLoc.executeQuery(strSql);
                                    while (rstRes.next())
                                    {
                                        strSql = "";
                                        if (rstRes.getString("co_rub").compareTo("6") == 0)
                                        {
                                            strSql += "update tbm_ingegrmentra set nd_valrub=" + rstRes.getString("nd_valrubprg");
                                            strSql += " , nd_valrubsindes=" + rstRes.getString("nd_valrubprg");
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("7") == 0)
                                        {
                                            strSql += "update tbm_ingegrmentra set nd_valrub=" + rstRes.getString("nd_valrubprg");
                                            strSql += " , nd_valrubsindes=" + rstRes.getString("nd_valrubprg");
                                        }
                                        //Se agrega para comisiones. tony
                                        else if (rstRes.getString("co_rub").compareTo("33") == 0)
                                        {
                                            strSql += "update tbm_ingegrmentra set nd_valrub=" + rstRes.getString("nd_valrubCom");
                                            strSql += " , nd_valrubsindes=" + rstRes.getString("nd_valrubCom");
                                         //   imprimirEnConsola(strSql);
                                        }
                                        else
                                        {
                                            strSql += "update tbm_ingegrmentra set nd_valrub=" + rstRes.getString("nd_valrub");
                                            strSql += " , nd_valrubsindes=" + rstRes.getString("nd_valrub");
                                        }
                                        strSql += " where co_emp = " + intCoEmp;
                                        strSql += " and co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = " + 0;
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and st_rolPagGen is null";
                                        stmAux.executeUpdate(strSql);
                                        //imprimirEnConsola(strSql);
                                        /*
                                         * 
                                         * 2.2) Calcular los valores correspondientes a la "Primera quincena".
                                         * Ingresos
                                         * 
                                         */
                                        strSql = "select * from tbm_ingegrmentra where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 1";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        rstAux = stmAux.executeQuery(strSql);
                                        if (rstAux.next())
                                        {
                                            double dblValRub = 0;
                                            if (!tbm_tra.isBlnIsTraNew())
                                            {
                                                if (rstAux.getString("co_rub").compareTo("6") == 0)
                                                {
                                                    dblValRub = rstRes.getDouble("nd_valrubprg") * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                else if (rstAux.getString("co_rub").compareTo("7") == 0)
                                                {
                                                    dblValRub = rstRes.getDouble("nd_valrubprg") * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                //Comision. tony
                                                else if (rstAux.getString("co_rub").compareTo("33") == 0)
                                                {
                                                    dblValRub = rstRes.getDouble("nd_valrubCom") * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                else
                                                {
                                                    dblValRub = rstRes.getDouble("nd_valrub") * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                                                String strValRub = "";
                                                if (dblValRub == 0)
                                                {
                                                    strValRub = "NULL";
                                                }
                                                else
                                                {
                                                    strValRub = String.valueOf(dblValRub);
                                                }
                                                if (!actualizarIngEgrMenTra(unCon, intCoEmp, tbm_tra.getIntCo_tra(), intAno, intMes, 1, rstAux.getInt("co_rub") , strValRub, " is null"))
                                                {
                                                    new SQLException();
                                                }
                                            }
                                            else
                                            {
                                                int intDiaEntTra = Integer.parseInt(tbm_tra.getStrFeIniCon().split("-")[2].toString());
                                                int intCanDiasTra = ((15 - intDiaEntTra)) + 1;
                                                if (rstAux.getString("co_rub").compareTo("6") == 0)
                                                {
                                                    dblValRub = ((rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra) * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                else if (rstAux.getString("co_rub").compareTo("7") == 0)
                                                {
                                                    dblValRub = ((rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra) * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                //Comision, valor neto. tony 
                                                else if (rstAux.getString("co_rub").compareTo("33") == 0)
                                                {
                                                    dblValRub = rstRes.getDouble("nd_valrubCom") * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                else
                                                {
                                                    dblValRub = ((rstRes.getDouble("nd_valrub") / intDiasContables) * intCanDiasTra) * (rstAux.getDouble("nd_porrubpag") / 100);
                                                }
                                                dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                                                String strValRub = "";
                                                if (dblValRub == 0)
                                                {
                                                    strValRub = "NULL";
                                                }
                                                else
                                                {
                                                    strValRub = String.valueOf(dblValRub);
                                                }
                                                strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 1";
                                                strSql += " and co_rub = " + rstAux.getString("co_rub");
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                stmAux2.executeUpdate(strSql);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    String strFeIniCor = "";
                    String strFeFinCor = "";
                    for (Iterator<Tbm_tra> itLstTra = listTra.iterator(); itLstTra.hasNext();)
                    {
                        Tbm_tra tbm_tra = itLstTra.next();
                        strSql = "";
                        strSql += " select a.*,b.*,c.*,c.nd_valrub as nd_valrubCom from tbm_suetra a";
                        strSql += " inner join tbm_rubrolpag b on (a.co_rub=b.co_rub and b.tx_tiprub='I')";
                        strSql += " left outer join tbm_ingegrmenprgtra c on (c.co_emp=a.co_emp and c.co_tra=a.co_tra and c.co_rub=a.co_rub and c.ne_ani=" + intAno + " and c.ne_mes=" + intMes + ")";
                        strSql += " where a.co_tra = " + tbm_tra.getIntCo_tra();
                        strSql += " and a.co_emp = " + intCoEmp;
                        strSql += " and a.co_rub not in(3,4,5,22,23)";
                        strSql += " order by a.co_rub asc";
                        rstRes = stmLoc.executeQuery(strSql);
                        boolean blnActDiasLab = false;
                        strFeIniCor = "";
                        //David
                        strFeFinCor = consultarFecCorRolPag(unCon, intAno, intMes, intCoEmp, 2).split(";")[1];
                        strFeIniCor = consultarFecCorRolPag(unCon, intAno, intMes, intCoEmp, 1).split(";")[0];
                        String strFecIniPeriodo = strFeIniCor;
                        if (tbm_tra.isBlnIsTraNew())
                        {
                            strFeIniCor=zafUti.codificar(tbm_tra.getStrFeIniCon());
                        }
                        while (rstRes.next())
                        {
                            double dblValRubFDM = 0;
                            //Obtener el sueldo para poder utilizarlo cuando se necesite calcular las "Horas extras 1".
                            if (rstRes.getString("co_rub").compareTo("1")==0) //1=Sueldo
                            {
                                bgdSue=rstRes.getBigDecimal("nd_valrub");
                            }
                            if (rstRes.getString("co_rub").compareTo("6")==0) //6=Préstamo a funcionarios
                            {
                                dblValRubFDM = rstRes.getDouble("nd_valrubprg");
                            }
                            else if (rstRes.getString("co_rub").compareTo("7") == 0) //7=Reembolso por movilización
                            {
                                dblValRubFDM = rstRes.getDouble("nd_valrubprg");
                            }
                            else if (rstRes.getString("co_rub").compareTo("33") == 0) //33=Comision tony
                            {
                                dblValRubFDM = rstRes.getDouble("nd_valrubCom");
                            }
                            else
                            {
                                dblValRubFDM=rstRes.getDouble("nd_valrub");
                            }
                            //Calcular todo lo que no sea "Horas extras 1".
                            if (!rstRes.getString("co_rub").equals("2"))
                            {
                                //David Cambio SQL
                                strSql = "select co_tra, count (fe_dia) as canDia, sum(canDiaP) as canDiaP from (";
                                strSql += " select fe_dia, c.co_tra, case when st_jusfal = 'P' then 1 else 0 end as canDiaP";
                                strSql += " from ( ";
                                strSql += " select fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg ";
                                strSql += " from tbm_cabconasitra a ";
                                strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A' ";
                                strSql += " where 	a.co_tra =" +  rstRes.getString("co_tra") ;
                                strSql += " and not (EXTRACT(DOW FROM fe_dia ) in (6,7))";
                                strSql += " AND (ho_ent is null AND ho_sal is null)";
                                strSql += " and (st_jusfal != 'S' or st_jusfal is null )  )c  ";
                                strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')";
                                strSql += " inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))";
                                strSql += " WHERE  c.fe_dia BETWEEN case ( (EXTRACT (YEAR FROM f.fe_inicon)="+intAno+") AND (EXTRACT (MONTH FROM f.fe_inicon)="+intMes+") ) when true then f.fe_inicon ";
                                strSql += " else ( select fe_des from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno+" and ne_mes="+intMes+" and ne_per=1) end ";
                                strSql += " AND (select fe_has from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno+" and ne_mes="+intMes+" and ne_per=2)";
                                strSql += " UNION   ";
                                strSql += " select fe_dia, c.co_tra, case when st_jusfal = 'P' then 1 else 0 end as canDiaP";
                                strSql += " from (  ";
                                strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg  ";
                                strSql += " from tbm_cabconasitra a   ";
                                strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)) ";
                                strSql += " where 	a.co_tra ="+  rstRes.getString("co_tra");
                                strSql += " and not (EXTRACT(DOW FROM fe_dia ) in (6,7))";
                                strSql += " AND (ho_ent is null AND ho_sal is null)";
                                strSql += " and (st_jusfal != 'S' or st_jusfal is null ) )c";
                                strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' and f.st_reg='A') ";
                                strSql += " left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))     ";
                                strSql += " WHERE c.fe_dia BETWEEN case ( (EXTRACT (YEAR FROM f.fe_inicon)="+intAno+") AND (EXTRACT (MONTH FROM f.fe_inicon)="+intMes+") ) when true then f.fe_inicon ";
                                strSql += " else ( select fe_des from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno+" and ne_mes="+intMes+" and ne_per=1) end ";
                                strSql += " AND (select fe_has from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno+" and ne_mes="+intMes+" and ne_per=2)";
                                strSql += " ) x";
                                strSql += " group by co_tra";
                                rstAux = stmAux.executeQuery(strSql);
                                if (rstAux.next())
                                {
                                    if (!tbm_tra.isBlnIsTraNew())
                                    {
                                        //No son empleados nuevos y tienen faltas
                                        double dblValRubDes = 0;
                                        if (rstRes.getString("co_rub").compareTo("6") == 0)
                                        {
//                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContablesHE1)* (rstAux.getDouble("canDia")*2);
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContablesHE1) * (rstAux.getDouble("canDia"));
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("7") == 0)
                                        {
//                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContablesHE1)* (rstAux.getDouble("canDia")*2);
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContablesHE1) * (rstAux.getDouble("canDia"));
                                        }
                                        else
                                        {
//                                            dblValRubDes = (rstRes.getDouble("nd_valrub") / intDiasContables) * (rstAux.getDouble("canDia")*2);
                                            dblValRubDes = (rstRes.getDouble("nd_valrub") / intDiasContables) * (rstAux.getDouble("canDia"));
                                        }
                                        dblValRubFDM-=zafUti.redondear(zafUti.parseDouble(dblValRubDes), zafParSis.getDecimalesMostrar());
                                        String strValRub="";
                                        if (dblValRubFDM==0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("1.4-" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                        if (rstRes.getString("co_rub").compareTo("1") == 0)
                                        {
                                            //David no considerar los dias de permiso.
                                            double   dblValRubDesAux = (rstRes.getDouble("nd_valrub") / intDiasContables) * (rstAux.getDouble("canDia") - rstAux.getDouble("canDiaP"));
                                            dblValRubDesAux = zafUti.redondear(dblValRubDesAux, zafParSis.getDecimalesMostrar());
                                            strSql = "update tbm_ingegrmentra set nd_valrub = -" + dblValRubDesAux;
                                            strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                            strSql += " and ne_ani = " + intAno;
                                            strSql += " and ne_mes = " + intMes;
                                            strSql += " and ne_per IN (0,2)";
                                            strSql += " and co_rub = 30";
                                            strSql += " and co_emp = " + intCoEmp;
                                            strSql += " and st_rolPagGen is null";
                                            int numFil = stmAux2.executeUpdate(strSql);
                                        }
                                        //                                    dblValRubFDM-=dblValRubPQ;
                                        strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        stmAux2.executeUpdate(strSql);
                                        if (rstRes.getString("co_rub").compareTo("1") == 0)
                                        {
                                            //David no considerar los dias de permiso.
                                            double   dblValRubDesAux = (rstRes.getDouble("nd_valrub") / intDiasContables) * (rstAux.getDouble("canDia") - rstAux.getDouble("canDiaP"));
                                            dblValRubDesAux = zafUti.redondear(dblValRubDesAux, zafParSis.getDecimalesMostrar());
                                            strSql = "update tbm_ingegrmentra set nd_valrub = -" + dblValRubDesAux;
                                            strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                            strSql += " and ne_ani = " + intAno;
                                            strSql += " and ne_mes = " + intMes;
                                            strSql += " and ne_per = 2";
                                            strSql += " and co_rub = 30";
                                            strSql += " and co_emp = " + intCoEmp;
                                            strSql += " and st_rolPagGen is null";
                                            int numFil = stmAux2.executeUpdate(strSql);
                                        }
                                        if (!blnActDiasLab)
                                        {
                                            strSql = "";
                                            strSql += "update tbm_datgeningegrmentra\n"
                                                    + "SET ne_numdialab = " + (intDiasContables - (rstAux.getInt("canDia"))) + " , \n"
                                                    + "ne_numFal = " + (rstAux.getInt("canDia")) + "\n"
                                                    + "WHERE co_emp = " + intCoEmp + "\n"
                                                    + "and ne_ani = " + intAno + " \n"
                                                    + "and ne_mes = " + intMes + "\n"
                                                    + "and co_tra = " + tbm_tra.getIntCo_tra() + "\n"
                                                    + "and ne_per = 0"
                                                    + "and st_rolpaggen is null";
                                            stmAux2.executeUpdate(strSql);
                                            blnActDiasLab = true;
                                        }
                                    }
                                    else
                                    {
                                        //Empleados nuevos y tienen faltas
                                        double dblValRubDes = 0;
                                        int intDiaEntTra = Integer.parseInt(tbm_tra.getStrFeIniCon().split("-")[2].toString());
                                        int intQCEE = rstAux.getInt("canDia");
                                        int intCanDiasTra = ((intDiasContables - intDiaEntTra) - intQCEE) + 1;
                                        if (rstRes.getString("co_rub").compareTo("6") == 0)
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra;
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("7") == 0)
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra;
                                        }
                                        else
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrub") / intDiasContables) * intCanDiasTra;
                                        }
                                        dblValRubFDM = dblValRubDes;
                                        String strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        stmAux2.executeUpdate(strSql);
                                        //                                    dblValRubFDM-=dblValRubPQ;
                                        strValRub = "";
                                        if (dblValRubFDM == 0) {
                                            strValRub = "NULL";
                                        } else {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        stmAux2.executeUpdate(strSql);
                                        strSql = "";
                                        strSql += "update tbm_datgeningegrmentra\n"
                                                + "SET ne_numdialab = " + intCanDiasTra + "\n"
                                                + "WHERE co_emp = " + intCoEmp + "\n"
                                                + "and ne_ani = " + intAno + " \n"
                                                + "and ne_mes = " + intMes + "\n"
                                                + "and co_tra = " + tbm_tra.getIntCo_tra() + "\n"
                                                + "and ne_per = 0";
                                        stmAux2.executeUpdate(strSql);
                                    }
                                }
                                else
                                {
                                    //Validar sólo empleados nuevos que no tienen faltas.
                                    if (tbm_tra.isBlnIsTraNew())
                                    {
                                        double dblValRubDes = 0;
                                        int intCanDiasTra = (intDiasContables - Integer.parseInt(tbm_tra.getStrFeIniCon().split("-")[2].toString())) + 1;
                                        if (rstRes.getString("co_rub").compareTo("6") == 0)
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra;
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("7") == 0)
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrubprg") / intDiasContables) * intCanDiasTra;
                                        }
                                        else
                                        {
                                            dblValRubDes = (rstRes.getDouble("nd_valrub") / intDiasContables) * intCanDiasTra;
                                        }
                                        dblValRubFDM = dblValRubDes;
                                        String strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("2.2" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                        //                                    dblValRubFDM-=dblValRubPQ;
                                        strValRub = "";
                                        if (dblValRubFDM == 0) {
                                            strValRub = "NULL";
                                        } else {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("2.3-" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                    }
                                    else
                                    {
                                        String strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                  
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("2.4" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        stmAux2.executeUpdate(strSql);
                                        if (!blnActDiasLab)
                                        {
                                            strSql = "";
                                            strSql += "update tbm_datgeningegrmentra\n"
                                                    + "SET ne_numdialab = " + intDiasContables + "\n"
                                                    + "WHERE co_emp = " + intCoEmp + "\n"
                                                    + "and ne_ani = " + intAno + " \n"
                                                    + "and ne_mes = " + intMes + "\n"
                                                    + "and co_tra = " + tbm_tra.getIntCo_tra() + "\n"
                                                    + "and ne_per = 0"
                                                    + "and st_rolpaggen is null";
                                            stmAux2.executeUpdate(strSql);
                                            blnActDiasLab = true;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                //2=Horas extras 1:
                                //tony query para obtener si la persona tiene horario fijo o no
                                String strStHorFij = null;
                                strSql="";
                                strSql+=" select * from tbm_traemp";
                                strSql+=" where co_tra="+rstRes.getString("co_tra");
                                strSql+=" and st_reg='A'";
                                rstAux = stmAux.executeQuery(strSql);
                                if (rstAux.next())
                                {
                                    if (!tbm_tra.isBlnIsTraNew())
                                    {
                                        strStHorFij=rstAux.getString("st_horfij");
                                    }
                                }
                                
                                BigDecimal bgdMinHorExt1Lab = null;
                                BigDecimal bgdMinHorExt1 = null;
                                String strAux="";
                                strAux+=" SELECT (GENERATE_SERIES(0, a1.ne_numDiaPer) + a1.fe_des) AS fe_dia";
                                strAux+=" FROM";
                                strAux+=" (";
                                strAux+=" SELECT";
                                strAux+=" (SELECT fe_des FROM tbm_feccorrolpag WHERE co_emp=" + intCoEmp + " AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1) AS fe_des";
                                strAux+=" , (SELECT CAST(EXTRACT(DAYS FROM ( (SELECT CAST(fe_has AS TIMESTAMP) FROM tbm_feccorrolpag WHERE co_emp=" + intCoEmp + " AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2)-(SELECT CAST(fe_des AS TIMESTAMP) FROM tbm_feccorrolpag WHERE co_emp=" + intCoEmp + " AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1))) AS INT2)) AS ne_numDiaPer";
                                strAux+=" ) AS a1";
                                //Sentencia SQL para calcular horas extras 1 exactas. @author:Tony Sanginez
//                                strSql="";
//                                strSql+="select (sum((extract(hours from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a3.ho_ent\n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a3.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   else '00:00:00' end)))*60)+\n" ;
//                                strSql+="(extract(minutes from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a3.ho_ent\n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a3.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   else '00:00:00' end))))+\n" ;
//                                strSql+="(extract(seconds from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a3.ho_ent\n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)<(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a1.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)<(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a3.ho_ent \n" ;
//                                strSql+="			   when ((a1.ho_ent)>(a3.ho_ent) and (a1.ho_sal)>(a3.ho_sal) and (a3.ho_ent)<=(a1.ho_sal)) then a3.ho_sal-a1.ho_ent \n" ;
//                                strSql+="			   else '00:00:00' end)))/60))) as minutosTotales,\n" ;
//                                strSql+="			   (extract(hours from(a1.ho_sal-a1.ho_ent)*60)+ (extract( minutes from(a1.ho_sal-a1.ho_ent))*60)) as minutosLabTot		   			   \n" ;
//                                strSql+="from tbm_detHorTra as a1\n" ;
//                                strSql+="inner join TBM_TRAEMP as a2 on(a1.co_hor=a2.co_hor)\n" ;
//                                strSql+="inner join tbm_cabconasitra as a3 on(a2.co_tra=a3.co_tra)\n" ;
//                                strSql+="where  extract(DOW from a3.fe_dia) in (6) and a1.ne_dia in (6) \n" ;
//                                strSql+="and ((1=1) or a3.st_jusMarInc in ('S', 'L'))  \n" ;
//                                strSql+="and a2.st_reg='A' and a3.co_tra=" + rstRes.getString("co_tra") ;
//                                strSql+=" and a3.fe_dia in(" ;
//                                strSql+=strAux;
//                                strSql+=") \n" ;
//                                strSql+="group by a1.ho_sal,a1.ho_ent";
                                //tony modificación para el cambio de horario
                                strSql="";
                                strSql+="select (sum((extract(hours from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_ent\n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_entasi  \n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_ent  \n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_entasi  \n" ;
                                strSql+="			   else '00:00:00' end)))*60)+\n" ;
                                strSql+="(extract(minutes from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_ent\n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_entasi  \n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_ent  \n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_entasi  \n" ;
                                strSql+="			   else '00:00:00' end))))+\n" ;
                                strSql+="(extract(seconds from ((case when a3.st_jusMarInc in ('S', 'L') then a3.ho_saljus-a3.ho_entjus\n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_ent\n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)<=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_salasi-a3.ho_entasi  \n" ;
                                strSql+="			   when ((a3.ho_entasi)<=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_ent  \n" ;
                                strSql+="			   when ((a3.ho_entasi)>=(a3.ho_ent) and (a3.ho_salasi)>=(a3.ho_sal) and (a3.ho_ent)<=(a3.ho_salasi)) then a3.ho_sal-a3.ho_entasi  \n" ;
                                strSql+="			   else '00:00:00' end)))/60))) as minutosTotales,\n" ;
                                strSql+=" (extract(hours from(a3.ho_salasi-a3.ho_entasi)*60)+ (extract( minutes from(a3.ho_salasi-a3.ho_entasi))*60)) as minutosLabTot		   			   \n" ;
                                strSql+=" from  TBM_TRAEMP as a2 \n" ;
                                strSql+=" inner join tbm_cabconasitra as a3 on(a2.co_tra=a3.co_tra)\n" ;
                                strSql+=" where  extract(DOW from a3.fe_dia) in (6) \n" ;
                                strSql+="and ((1=1) or a3.st_jusMarInc in ('S', 'L'))  \n" ;
                                strSql+="and a2.st_reg='A' and a3.co_tra=" + rstRes.getString("co_tra") ;
                                strSql+=" and a3.fe_dia in(" ;
                                strSql+=strAux;
                                strSql+=") \n" ;
                                strSql+="group by a3.ho_salasi,a3.ho_entasi";
                                rstAux = stmAux.executeQuery(strSql);
                                if (rstAux.next())
                                {
                                    if (!tbm_tra.isBlnIsTraNew())
                                    {
                                        bgdMinHorExt1Lab=rstAux.getBigDecimal("minutosTotales");
                                        bgdMinHorExt1=rstAux.getBigDecimal("minutosLabTot");
                                    }
                                }
                                //Armar la sentencia SQL.
                                strSql="";
                                strSql+=" SELECT";
                                strSql+=" (";
                                strSql+=" SELECT COUNT(b1.fe_dia) AS ne_numTotSab";
                                strSql+=" FROM";
                                strSql+=" (";
                                strSql+=strAux;
                                strSql+=" ) AS b1";
                                strSql+=" WHERE EXTRACT(DOW FROM b1.fe_dia)='6'";
                                strSql+=" ) AS ne_numTotSab";
                                strSql+=" , (";
                                strSql+=" SELECT COUNT(b1.fe_dia) AS ne_numTotSabLab";
                                strSql+=" FROM";
                                strSql+=" (";
                                strSql+=strAux;
                                strSql+=" ) AS b1";
                                strSql+=" INNER JOIN tbm_cabConAsiTra AS b2 ON (b1.fe_dia=b2.fe_dia)";
                                strSql+=" WHERE EXTRACT(DOW FROM b1.fe_dia)='6' AND b2.co_tra=" + rstRes.getString("co_tra") + " AND (b2.ho_ent IS NOT NULL OR b2.st_jusFal IN ('S', 'L'))";
                                strSql+=" ) AS ne_numTotSabLab";
                                rstAux = stmAux.executeQuery(strSql);
                                //System.out.println(strSql);
                                if (rstAux.next())
                                {
                                    if (!tbm_tra.isBlnIsTraNew())
                                    {
                                        BigDecimal bgdAux;
                                        if (rstRes.getString("co_rub").compareTo("2")==0) //2=Horas extras 1
                                        {
                                            bgdAux=rstRes.getBigDecimal("nd_valrub");
                                            if (bgdAux==null)
                                                bgdAux=BigDecimal.ZERO;
                                            if (bgdMinHorExt1==null)
                                                bgdMinHorExt1=BigDecimal.ZERO;
                                            if (bgdMinHorExt1Lab==null)
                                                bgdMinHorExt1Lab=BigDecimal.ZERO;
                                            if (bgdAux==BigDecimal.ZERO)
                                            {
                                                dblValRubFDM=bgdAux.doubleValue();
                                            }
                                            else
                                            {
                                                if (strStHorFij.equals("S")) {
                                                  if (bgdMinHorExt1Lab==BigDecimal.ZERO || bgdMinHorExt1==BigDecimal.ZERO) {
                                                    dblValRubFDM=0;
                                                }else{
                                                 dblValRubFDM=(((bgdSue.divide(BigDecimal.valueOf(30),5,BigDecimal.ROUND_HALF_UP)).divide(bgdMinHorExt1,5,BigDecimal.ROUND_HALF_UP)).multiply(bgdMinHorExt1Lab)).setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
                                                 //System.out.println(dblValRubFDM);
                                                }   
                                                }else{
                                                    dblValRubFDM=bgdSue.divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP).multiply(rstAux.getBigDecimal("ne_numTotSabLab")).doubleValue();
                                                }
                                                 //System.out.println(dblValRubFDM);
                                            }
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("6")==0) //6=Préstamo a funcionarios
                                        {
                                            bgdAux=rstRes.getBigDecimal("nd_valrubprg");
                                            if (bgdAux==null)
                                                bgdAux=BigDecimal.ZERO;
                                            dblValRubFDM=bgdAux.multiply(rstAux.getBigDecimal("ne_numTotSabLab").divide(rstAux.getBigDecimal("ne_numTotSab"), 2, BigDecimal.ROUND_HALF_UP)).doubleValue();
                                        }
                                        else if (rstRes.getString("co_rub").compareTo("7")==0) //7=Reembolso por movilización
                                        {
                                            bgdAux=rstRes.getBigDecimal("nd_valrubprg");
                                            if (bgdAux==null)
                                                bgdAux=BigDecimal.ZERO;
                                            dblValRubFDM=bgdAux.multiply(rstAux.getBigDecimal("ne_numTotSabLab").divide(rstAux.getBigDecimal("ne_numTotSab"), 2, BigDecimal.ROUND_HALF_UP)).doubleValue();
                                        }
                                        else
                                        {
                                            
                                            bgdAux=rstRes.getBigDecimal("nd_valrub");
                                            //System.out.println(bgdAux);
                                            if (bgdAux==null)
                                                bgdAux=BigDecimal.ZERO;
                                            dblValRubFDM=bgdAux.multiply(rstAux.getBigDecimal("ne_numTotSabLab").divide(rstAux.getBigDecimal("ne_numTotSab"), 2, BigDecimal.ROUND_HALF_UP)).doubleValue();
                                            System.out.println(dblValRubFDM);
                                        }
                                        //dblValRubFDM-= zafUti.redondear(zafUti.parseDouble(dblValRubDes), zafParSis.getDecimalesMostrar());
                                        //Eddye: Para calcular las "Horas extras 1" se utiliza la siguiente formula:
                                        //"Horas extras 1"= "Horas extras 1"-("Horas extras 1"/4 sábados)*Faltas
                                        //Se considera que cada mes tiene 4 sábados. Cuando el mes tiene 5 sàbados y el empleado ha faltado los 5 sábados el
                                        //Sistema genera un negativo. Para evitar dicho problema se puso la siguiente validación:
                                        if (rstRes.getString("co_rub").equals("2"))
                                        {
                                            if (dblValRubFDM<=0)
                                            {
                                                dblValRubFDM=0;
                                            }
                                        }
                                        String strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("2.7-" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                        //                                        dblValRubFDM-=dblValRubPQ;
                                        strValRub = "";
                                        if (dblValRubFDM == 0)
                                        {
                                            strValRub = "NULL";
                                        }
                                        else
                                        {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("2.8" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                    }
                                    else
                                    {
                                        //empleados nuevos y tienen faltas los dias sabados
                                        int intDiasLabSab = 0;
                                        int intDiasLabTra = 0;
                                        strSql = "";
                                        strSql += " select count(distinct fe_dia) as canDiasSab from tbm_callab ";
                                        strSql += " WHERE fe_dia BETWEEN "+ zafUti.codificar(strFecIniPeriodo) + " AND (select fe_has from tbm_feccorrolpag where co_emp=" + intCoEmp + " and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)" + "\n";
                                        strSql += " and (EXTRACT(DOW FROM fe_dia ) in (6)) and st_dialab='S";//David and st_dialab='S' //tony se quita los dias laborales. porque no cuadraba
                                        System.out.println("error:" + strSql);
                                        rstLocAux = stmLocAux.executeQuery(strSql);

                                        if (rstLocAux.next()) {
                                            intDiasLabSab = rstLocAux.getInt("candiassab");
                                            if (intDiasLabSab > 0) {
                                                strSql = "";
                                                strSql += " select count(distinct fe_dia) as canDiasSabTra from tbm_cabconasitra ";
                                                strSql += " WHERE fe_dia BETWEEN " + strFeIniCor + " AND (select fe_has from tbm_feccorrolpag where co_emp=" + intCoEmp + " and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)" + "\n";
                                                strSql += " and (EXTRACT(DOW FROM fe_dia ) in (6))";
                                                strSql += " and co_tra = " + tbm_tra.getIntCo_tra();
						strSql += " AND (ho_ent IS NOT NULL )";
                                                System.out.println("error2:" + strSql);
                                                rstRes3 = stmLocAux2.executeQuery(strSql);

                                                if (rstRes3.next()) {
                                                    intDiasLabTra = rstRes3.getInt("canDiasSabTra");
                                                    //intDiasLabTra-=rstAux.getInt("candia");Conmentado David
                                                }
                                                //intDiasLabSab = 4;David
                                                double dblValFDM = ((rstRes.getDouble("nd_valrub") / intDiasLabSab) * intDiasLabTra);
                                                String strValRub = "";
                                                if (dblValRubFDM == 0) {
                                                    strValRub = "NULL";
                                                } else {
                                                    strValRub = String.valueOf(dblValFDM);
                                                }

                                                strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 0";
                                                strSql += " and co_rub = " + rstRes.getString("co_rub");
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                imprimirEnConsola("2.9-" + strSql);
                                                stmAux2.executeUpdate(strSql);
                                                
                                                strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 2";
                                                strSql += " and co_rub = " + rstRes.getString("co_rub");
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                imprimirEnConsola("3.0-" + strSql);
                                                stmAux2.executeUpdate(strSql);
                                            }
                                        }
                                    }
                                } else {
                                    if (!tbm_tra.isBlnIsTraNew()) {
                                        String strValRub = "";
                                        if (dblValRubFDM == 0) {
                                            strValRub = "NULL";
                                        } else {
                                            strValRub = String.valueOf(dblValRubFDM);
                                        }
                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 0";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("3.1- " + strSql);
                                        stmAux2.executeUpdate(strSql);

                                        strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                        strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                        strSql += " and ne_ani = " + intAno;
                                        strSql += " and ne_mes = " + intMes;
                                        strSql += " and ne_per = 2";
                                        strSql += " and co_rub = " + rstRes.getString("co_rub");
                                        strSql += " and co_emp = " + intCoEmp;
                                        strSql += " and st_rolPagGen is null";
                                        //imprimirEnConsola("3.2-" + strSql);
                                        stmAux2.executeUpdate(strSql);
                                    } else {
                                        //empleados nuevos y no tienen faltas los sabados

                                        int intDiasLabSab = 0;
                                        int intDiasLabTra = 0;
                                        strSql = "";
                                        strSql += " select count(distinct fe_dia) as canDiasSab from tbm_callab ";
                                        strSql += " WHERE fe_dia BETWEEN " + zafUti.codificar(strFecIniPeriodo) + " AND " + zafUti.codificar(strFeFinCor);
                                        strSql += " and (EXTRACT(DOW FROM fe_dia ) in (6)) and st_dialab='S'";
                                        rstLocAux = stmLocAux.executeQuery(strSql);

                                        if (rstLocAux.next()) {//Sabados Nuevos
                                            intDiasLabSab = rstLocAux.getInt("candiassab");
                                            if (intDiasLabSab > 0) {
                                                strSql = "";
                                                strSql += " select count(distinct fe_dia) as canDiasSabTra from tbm_cabconasitra ";
                                                strSql += " WHERE fe_dia BETWEEN " + zafUti.codificar(tbm_tra.getStrFeIniCon()) + " AND " + zafUti.codificar(strFeFinCor);
                                                strSql += " and (EXTRACT(DOW FROM fe_dia ) in (6))";
                                                strSql += " and co_tra = " + tbm_tra.getIntCo_tra();
                                                rstRes3 = stmLocAux2.executeQuery(strSql);

                                                if (rstRes3.next()) {
                                                    intDiasLabTra = rstRes3.getInt("canDiasSabTra");
                                                }
                                                //intDiasLabSab = 4;
                                                double dblValFDM = ((rstRes.getDouble("nd_valrub") / intDiasLabSab) * intDiasLabTra);

                                                String strValRub = "";
                                                if (dblValRubFDM == 0) {
                                                    strValRub = "NULL";
                                                } else {
                                                    strValRub = String.valueOf(dblValFDM);
                                                }
                                                strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 0";
                                                strSql += " and co_rub = " + rstRes.getString("co_rub");
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                //imprimirEnConsola("3.3- " + strSql);
                                                stmAux2.executeUpdate(strSql);

                                                strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 2";
                                                strSql += " and co_rub = " + rstRes.getString("co_rub");
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                //imprimirEnConsola("3.4-" + strSql);
                                                stmAux2.executeUpdate(strSql);
                                            } else {//David, si no hay sabados en dicho periodo, entonces es null
                                                strSql = "update tbm_ingegrmentra set nd_valrub = null";
                                                strSql += " where co_tra = " + tbm_tra.getIntCo_tra();
                                                strSql += " and ne_ani = " + intAno;
                                                strSql += " and ne_mes = " + intMes;
                                                strSql += " and ne_per = 0";
                                                strSql += " and co_rub = 2";
                                                strSql += " and co_emp = " + intCoEmp;
                                                strSql += " and st_rolPagGen is null";
                                                //imprimirEnConsola("3.5-" + strSql);
                                                stmAux2.executeUpdate(strSql);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println("Hola Fin empresas");
                }
                for(int i : arrLstEmp ){//David Cerrar Periodos anteriores
                    cerrarPerAntEmpNuevo(unCon, intAno, intMes, i);
                }
                unCon.commit();

                stmAux2 = unCon.createStatement();
                /*
                 * HORAS EXTRAS 2 EXTRAS FERIADOS ETC NO LABORABLES
                 */
                //Limpiar los rubros.
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = null";
                strSql += " where ne_ani=" + intAno;
                strSql += " and ne_mes=" + intMes;
                strSql += " and ne_per in (0,2)";
                strSql += " and co_rub in(3,4,5,9,10,11,12,13,14,15,16,17,18,19,20,21,22,29)";
                strSql += " and st_rolpaggen is null";
                stmAux2.executeUpdate(strSql);
                unCon.commit();
                //Sobretiempos.
                strSql = "";
                strSql += " select co_emp, co_tra, empleado ,sum(sobretiempo) as sobretiempo from ( ";
                strSql += " select co_emp, co_tra, empleado ,sum(valorSobretiempo) as sobretiempo  from ( ";
                strSql += " select c.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.co_dep, c.co_hor ,a.fe_sol, a.ho_autdes, a.ho_authas, a.fe_aut, c.co_tra, ";
                strSql += " (d.tx_ape || ' ' || d.tx_nom) as empleado, e.fe_dia, e.ho_ent, e.ho_sal, ";
                strSql += " g.tx_tiphorpag as st_tipsob, ";
                strSql += " g.ho_des as hordessob,g.ho_has as horhassob, ";
                strSql += " nd_porpag as porpagar, nd_valrub as sueldo, (((nd_valrub/30)/8)/60) as valPerMin, ";
                strSql += " (ho_authas-ho_autdes) as sobretiempoautorizado, ";
                strSql += " (e.ho_sal-ho_autdes) as sobretiempoempleado, ";
                strSql += " case ((e.ho_sal-ho_autdes)<=(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end as sobretiempo, ";
                strSql += " ( ";
                strSql += " extract(hours from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "			   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
                strSql += "		                           when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "                           else (e.ho_sal-ho_autdes) end)  )*60, ";
                strSql += " extract(minutes from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "			   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "                         when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "                         else (e.ho_sal-ho_autdes) end)  ), ";
                strSql += " extract(seconds from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )/60 ";
                strSql += " ) as matrinf, ";
                strSql += " ( ";
                strSql += " extract(hours from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )*60 + ";
                strSql += " extract(minutes from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )+ ";
                strSql += " (extract(seconds from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )/60) ";
                strSql += " )as matrinfsum, ";
                strSql += " ( ";
		strSql += " (extract(hours from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )*60) + ";
		strSql += " (extract(minutes from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "                when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "                else (e.ho_sal-ho_autdes) end)  ))+ ";
		strSql += " (extract(seconds from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "                when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "                else (e.ho_sal-ho_autdes) end)  )/60) ";
		strSql += " ) * ( ((((nd_valrub/30)/8)/60) * ((nd_porpag+100)/100)) ) as valorSobretiempo ";
//------------------------------------------------------------------------------
                //Fecha: 29/May/2015
                //Responsable: Eddye Lino M.
                //Cambio realizado: En las horas suplementarias estaba configurado que de "00:00 hasta 06:00 se paga el 100%" y de "06:00 hasta 24:00 se paga el 50%".
                //Cuando la solicitud era autorizada antes de las 06h00 el Sistema no calculaba las horas extras porque no sabía si aplicar el 100% o el 50%.
                //strSql += " from tbm_cabsolhorsupext a" + "\n";
                strSql += " FROM (";
                strSql += " SELECT co_emp, co_loc, co_tipdoc, co_doc, fe_doc, co_dep, fe_sol, st_aut, ho_authas, st_reg, fe_aut, ho_autdes FROM tbm_cabSolHorSupExt WHERE (ho_autDes>'00:00' AND ho_autHas<'06:00') OR (ho_autDes>='06:00' AND ho_autHas<='24:00') AND st_reg='A'";
                strSql += " UNION ALL";
                strSql += " SELECT co_emp, co_loc, co_tipdoc, co_doc, fe_doc, co_dep, fe_sol, st_aut, '06:00' AS ho_authas, st_reg, fe_aut, ho_autdes FROM tbm_cabSolHorSupExt WHERE (ho_autDes<'06:00' AND ho_autHas>'06:00') AND st_reg='A'";
                strSql += " UNION ALL";
                strSql += " SELECT co_emp, co_loc, co_tipdoc, co_doc, fe_doc, co_dep, fe_sol, st_aut, ho_authas, st_reg, fe_aut, CAST('06:00' AS TIME) AS ho_autdes FROM tbm_cabSolHorSupExt WHERE (ho_autDes<'06:00' AND ho_autHas>'06:00') AND st_reg='A'";
                strSql += " ) AS a";
//------------------------------------------------------------------------------
                strSql += " inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                strSql += " inner join tbm_traemp c on (b.co_emppag=c.co_emp and b.co_tra=c.co_tra)";
                strSql += " inner join tbm_tra d on (c.co_tra=d.co_tra)";
                strSql += " left outer join tbm_cabconasitra e on (e.co_tra=d.co_tra and e.fe_dia=a.fe_sol)";
                strSql += " inner join tbm_callab f on (e.fe_dia=f.fe_dia and f.co_hor=c.co_hor)";
                strSql += " inner join tbm_porPagHorSupExt g on (g.tx_tiphorpag in (";
                strSql += " case (extract(DOW from e.fe_dia) in (1,2,3,4,5) and f.st_dialab='S' and";
                strSql += " (a.ho_autdes between ho_des and ho_has) and (a.ho_authas between ho_des and ho_has)";
                strSql += " ) when true then 'S' else 'E' end))";
                strSql += " inner join tbm_suetra h on (h.co_tra=c.co_tra and h.co_emp=c.co_emp and h.co_rub=1)";
                //Join que impedia contar los domingos como horas extras Tony 
                //strSql += " inner join tbm_dethortra i on(c.co_hor=i.co_hor and i.ne_dia in(extract(dow from e.fe_dia)))"; 
                strSql += " where date(fe_aut) between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=1)" + "\n";
                strSql += " and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)" + "\n";
                strSql += " and  b.co_tra not in (90 , 65 , 253) ";
                strSql += " and (e.ho_ent is not null and e.ho_sal is not null) ";
                strSql += " and a.st_reg='A' ";
                strSql += " and a.st_aut='T' ";
                strSql += " and ( ";
                strSql += " extract(hours from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
                strSql += "					   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )*60 + ";
                strSql += " extract(minutes from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )+ ";
                strSql += " (extract(seconds from (case when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)<=(e.ho_ent)) then (ho_authas-e.ho_ent) ";
		strSql += "		   when ((ho_authas)>=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (e.ho_sal-ho_autdes) ";
		strSql += "               when ((ho_authas)<=(e.ho_sal)) and ((ho_autdes)>=(e.ho_ent)) then (ho_authas-ho_autdes) ";
		strSql += "               else (e.ho_sal-ho_autdes) end)  )/60) ";
                strSql += " )>0 ";
                strSql += " order by a.co_doc ";
                strSql += " )xsob ";
                strSql += " group by xsob.co_emp, xsob.co_tra, xsob.empleado ";
                strSql += " union ";
                strSql += " select co_emp, co_tra, empleado, sum(valorSobretiempo) as sobretiempo from ( ";
                strSql += " select a.co_emp,b.co_tra,(d.tx_ape || ' ' || d.tx_nom) as empleado ,b.co_rub,tx_tipegrprg,sum(nd_valcuo) as valorSobretiempo, a.fe_cuo from tbm_detingegrprgtra a ";
                strSql += " inner join tbm_cabingegrprgtra b on (a.co_emp=b.co_emp and a.co_egr=b.co_egr) ";
                strSql += " inner join tbm_rubrolpag c on (b.co_rub=c.co_rub and tx_tiprub='I') ";
                strSql += " inner join tbm_tra d on (d.co_tra=b.co_tra) ";
                strSql += " where EXTRACT(YEAR FROM fe_cuo) = " + intAno + "\n";
                strSql += " and EXTRACT(MONTH FROM fe_cuo) = " + intMes + "\n";
                strSql += " and tx_tipegrprg like 'D' and b.st_reg like 'A' and a.st_reg like 'A'";
                strSql += " and date(a.fe_cuo) ";// Se cambio de b.fe_ing a a.fe_cuo ya que no ingresaba todos los valores tony
                strSql += " BETWEEN (select fe_des as fe_des from tbm_feccorrolpag ";
                strSql += " where co_emp=b.co_emp ";
                strSql += " and ne_ani = " + intAno + "\n";
                strSql += " and ne_mes = " + intMes + "\n";
                strSql += " and ne_per=1)";
                strSql += " and (select fe_has as fe_des from tbm_feccorrolpag ";
                strSql += " where co_emp=b.co_emp ";
                strSql += " and ne_ani = " + intAno + "\n";
                strSql += " and ne_mes = " + intMes + "\n";
                strSql += " and ne_per=2) ";
                strSql += " and b.co_rub in (3) ";
                strSql += " group by a.co_emp,b.co_tra,empleado,b.co_rub,tx_tipegrprg, a.fe_cuo)xvaldefhe2 ";
                strSql += " group by xvaldefhe2.co_emp, xvaldefhe2.co_tra,xvaldefhe2.empleado ";
                strSql += " )xvaldefhe2Sum ";
                strSql += " group by xvaldefhe2Sum.co_emp,xvaldefhe2Sum.co_tra,xvaldefhe2Sum.empleado ";
                rstRes = stmLoc.executeQuery(strSql);
                //System.out.println(strSql);
                while (rstRes.next()) {
                    double dblValRub = rstRes.getDouble("sobretiempo");
                    String strValRub = "";
                    if (dblValRub == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRub);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 0";
                    strSql += " and co_rub = 3";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.0-calculo rubro todo el mes (horas extras 2): " + strSql);
                    stmAux2.executeUpdate(strSql);

                    double dblValRubPQ = 0;
                    strSql = "";
                    strSql += "select * from tbm_ingegrmentra";
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = 3";
                    rstAux = stmAux2.executeQuery(strSql);

                    if (rstAux.next()) {
                        dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                    }


                    strValRub = "";
                    if (dblValRubPQ == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRubPQ);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = 3";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.1-calculo rubro pq (horas extras 2): " + strSql);
                    stmAux2.executeUpdate(strSql);

                    double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = "";
                    if (dblFDM == 0) {
                        strValRub = "NULL";
                    } else {
                        dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblFDM);
                    }

                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 2";
                    strSql += " and co_rub = 3";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.2-calculo rubro fin mes (horas extras 2): " + strSql);
                    stmAux2.executeUpdate(strSql);
                }
                unCon.commit();
                /*
                 * ACTUALIZACION FONDOS DE RESERVA
                 */
                strSql = "";
                strSql += "select a.co_emp,a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado, sum(nd_valrub) as nd_sumaBase , " + "\n";
                //Se realizó este cambio ya que al momento de no cumplir el anio pero tenia entre 360 y 365 dias le asignaba como si cumplia el anio. tony 
                //strSql += "round((sum(nd_valrub)/12),2) as nd_totfonres,((current_date-b.fe_inicon)/30) as ne_MesLab, (current_date-b.fe_inicon) as ne_diaLab from " + "\n";
                strSql += "round((sum(nd_valrub)/12),2) as nd_totfonres,CASE WHEN (current_date-b.fe_inicon)>360 AND (current_date-b.fe_inicon)<365 THEN 359/30 ELSE ((current_date-b.fe_inicon)/30) END as ne_MesLab, (current_date-b.fe_inicon) as ne_diaLab from " + "\n";
                strSql += "tbm_ingegrmentra a " + "\n";
                strSql += "inner join tbm_traemp b on (b.co_tra=a.co_tra and b.co_emp=a.co_emp and b.st_reg like 'A') " + "\n";
                strSql += "inner join tbm_tra c on (c.co_tra=b.co_tra) " + "\n";
                strSql += "where b.st_recfonres='S' " + "\n";
                strSql += "and tx_forrecfonres='R'  " + "\n";
                strSql += "and ((co_rub in (1,2,3) and a.ne_per=2) or (co_rub in (33) and a.ne_per=0)) " + "\n"; //Modificacion para agregar comision al calculo de fondo de reserva. tony
                strSql += "and a.ne_ani= " + intAno + "\n";
                strSql += "and a.ne_mes= " + intMes + "\n";
                strSql += "group by a.co_emp,a.co_tra,empleado ,ne_meslab, ne_diaLab  " + "\n";
                strSql += "ORDER BY co_emp , empleado ";
                rstRes = stmLoc.executeQuery(strSql);
                while (rstRes.next()) {
                    
                    double dblValRub = rstRes.getDouble("nd_totfonres");
                    String strValRub = "";
                    if (dblValRub == 0) {
                        strValRub = "NULL";
                    } else {
                        int intMesLab = rstRes.getInt("ne_MesLab");
                        if (intMesLab==13) {
                            strSql ="";
                            strSql += " select b.fe_inicon, a.fe_has, (extract(Day from a.fe_has)-extract(Day from b.fe_inicon)) as dias, ";
                            strSql += "(extract(month from a.fe_has)-extract(month from b.fe_inicon)) as mes";
                            strSql += " from tbm_feccorrolpag a ";
                            strSql += " inner join tbm_traemp b on (a.co_emp=b.co_emp)  ";
                            strSql += " where a.co_emp = " + rstRes.getString("co_emp");
                            strSql += " and a.ne_ani = " + intAno;
                            strSql += " and a.ne_mes = " + intMes;
                            strSql += " and b.co_tra = " + rstRes.getString("co_tra");
                            strSql += " and a.ne_per = 2 ";
                            rstRes3 = stmLoc3.executeQuery(strSql);
                            while (rstRes3.next()) {
                                int intDiasLabRec = rstRes3.getInt("dias");
                                int intMesFalRec = rstRes3.getInt("mes");
                                if (intMesFalRec==0) {
                                  //  System.out.println("ENTRA");
                                    if (intDiasLabRec>0) {
                                    //System.out.println("ENTRA: " + dblValRub);
                                    //System.out.println("ENTRA: " + dblValRub/30);
                                    //System.out.println("ENTRA: " + (dblValRub/30)*intDiasLabRec);
                                        dblValRub = (dblValRub/30)*intDiasLabRec;    
                                }
                                }
                                
                            }
                        }
                        dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRub);
                    }

                    //actualiza todo el mes ... fondos de reserva
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and co_emp = " + rstRes.getString("co_emp");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 0";
                    strSql += " and co_rub = 5";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.2-calculo rubro todo el mes (fondos de reserva): " + strSql);
                    stmAux2.executeUpdate(strSql);

                    //actualiza primera quincena ... fondos de reserva
                    double dblValRubPQ = 0;

                    strSql = "";
                    strSql += "select * from tbm_ingegrmentra";
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and co_emp = " + rstRes.getString("co_emp");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = 5";
                    rstAux = stmAux2.executeQuery(strSql);

                    if (rstAux.next()) {
                        dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                    }

                    strValRub = "";
                    if (dblValRubPQ == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRubPQ);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and co_emp = " + rstRes.getString("co_emp");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = 5";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.3-calculo rubro pq (fondos de reserva): " + strSql);
                    stmAux2.executeUpdate(strSql);
                    double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = "";
                    if (dblFDM == 0) {
                        strValRub = "NULL";
                    } else {
                        dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblFDM);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and co_emp = " + rstRes.getString("co_emp");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 2";
                    strSql += " and co_rub = 5";
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.4-calculo rubro fin mes (fondos de reserva): " + strSql);
                    stmAux2.executeUpdate(strSql);
                }
                unCon.commit();
                /*
                 * ACTUALIZACION ALIMENTACION
                 */
//                 System.out.println("ACTUALIZACION DE ALIMENTACION");
                strSql = "";
                /*strSql+="SELECT b.co_emp, b.co_tra , (a.tx_ape || ' ' || a.tx_nom) as empleado from tbm_tra a \n"+
                 "INNER JOIN tbm_traemp b on (b.co_tra = a.co_tra) \n"+
                 "WHERE a.st_reg = 'A' \n"+
                 "AND b.st_recalm = 'S' \n"+
                 "AND b.tx_forrecalm = 'R' \n"+
                 "ORDER BY b.co_emp, b.co_tra";*/

                strSql += "SELECT b.co_emp, b.co_tra , (a.tx_ape || ' ' || a.tx_nom) as empleado from tbm_tra a \n"
                        + "INNER JOIN tbm_traemp b on (b.co_tra = a.co_tra) \n"
                        + "WHERE a.st_reg = 'A' \n"
                        + //                        "AND b.st_recalm = 'S' \n"+
                        //                        "AND b.tx_forrecalm = 'R' \n"+
                        "ORDER BY b.co_emp, b.co_tra";
//                 System.out.println("strSql Alm: " + strSql);
                rstRes = stmLoc.executeQuery(strSql);
                while (rstRes.next()) {
                    strSql = "";
                    strSql += "SELECT co_emp,co_tra,empleado,co_rub,tx_nom, sum(nd_valdef) as nd_valdef   FROM ( " + "\n";
                    strSql += "SELECT b.co_emp, b.co_tra ,(c.tx_ape || ' ' || c.tx_nom)as empleado , d.co_rub ,  d.tx_nom , null as tx_tipegrprg , case (sum(a.nd_valalm)  is null) when true then 0 else sum(a.nd_valalm) end as nd_valdef , null as st_reg from tbm_cabconasitra a " + "\n";
                    strSql += "INNER JOIN tbm_traemp b on (b.co_tra = a.co_tra) " + "\n";
                    strSql += "INNER JOIN tbm_tra c on (c.co_tra = a.co_tra) " + "\n";
                    strSql += "INNER JOIN tbm_rubrolpag d on (d.co_rub=4 and d.tx_tiprub='I') " + "\n";
                    strSql += "WHERE date(a.fe_dia) between (select fe_des from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=1) " + "\n";
                    strSql += "AND (select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2) " + "\n";
                    strSql += "AND NOT ((EXTRACT(DOW FROM a.fe_dia )=6) or  (EXTRACT(DOW FROM a.fe_dia )=7)) " + "\n";
                    strSql += "AND NOT (a.ho_ent is null and a.ho_sal is null) " + "\n";
                    strSql += "GROUP BY b.co_emp, b.co_tra, empleado, d.co_rub, d.tx_nom  " + "\n";
                    strSql += "UNION " + "\n";
                    strSql += "SELECT a.co_emp,b.co_tra,(d.tx_ape || ' ' || d.tx_nom)as empleado, b.co_rub,c.tx_nom,tx_tipegrprg,case (sum(nd_valcuo)  is null) when true then 0 else sum(nd_valcuo) end as nd_valdef, a.st_reg from tbm_detingegrprgtra a " + "\n";
                    strSql += "INNER JOIN tbm_cabingegrprgtra b on (a.co_emp=b.co_emp and a.co_egr=b.co_egr) " + "\n";
                    strSql += "INNER JOIN tbm_rubrolpag c on (b.co_rub=c.co_rub and tx_tiprub='I') " + "\n";
                    strSql += "INNER JOIN tbm_tra d on (d.co_tra = b.co_tra) " + "\n";
                    strSql += "WHERE tx_tipegrprg like 'D' and b.st_reg like 'A' " + "\n";
                    strSql += "AND date(b.fe_ing) between (select fe_des from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=1) " + "\n";
                    strSql += "AND (select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2) " + "\n";
                    strSql += "AND b.co_rub in(4) " + "\n";
                    strSql += "GROUP BY a.co_emp,b.co_tra,empleado,b.co_rub,c.tx_nom,tx_tipegrprg, a.st_reg " + "\n";
                    strSql += ") as x " + "\n";
                    strSql += "WHERE x.co_emp =" + rstRes.getString("co_emp") + " \n ";
                    strSql += "AND x.co_tra =" + rstRes.getString("co_tra") + " \n ";
                    strSql += "GROUP BY x.co_emp,x.co_tra,empleado,x.co_rub,x.tx_nom ";
                    rstAux = stmAux2.executeQuery(strSql);

                    /* DEJO SIN EFECTO ALGUNO DIAS SABADOS Y DOMINGOS
                     select *
                     from tbm_cabconasitra a
                     where a.fe_dia between '2013-08-01' and '2013-08-28' 
                     and (EXTRACT(DOW FROM a.fe_dia )=6) or  (EXTRACT(DOW FROM a.fe_dia )=7)
                     order by a.fe_dia

                     update tbm_cabconasitra set st_recalm=null , tx_forrecalm=null, nd_valalm=null
                     where fe_dia between '2013-08-01' and '2013-08-28' 
                     and (EXTRACT(DOW FROM fe_dia )=6) or  (EXTRACT(DOW FROM fe_dia )=7)
                     */
                    while (rstAux.next()) {
                        double dblValRub = rstAux.getDouble("nd_valdef");

                        String strValRub = "";
                        if (dblValRub == 0) {
                            strValRub = "NULL";
                        } else {
                            dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                            strValRub = String.valueOf(dblValRub);
                        }

                        //actualiza todo el mes ... alimentacion
                        strSql = "";
                        strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                        strSql += " where co_tra = " + rstRes.getString("co_tra");
                        strSql += " and co_emp = " + rstRes.getString("co_emp");
                        strSql += " and ne_ani = " + intAno;
                        strSql += " and ne_mes = " + intMes;
                        strSql += " and ne_per = 0";
                        strSql += " and co_rub = 4";
                        strSql += " and st_rolPagGen is null";
                        //imprimirEnConsola("4.5-calculo rubro todo el mes (alimentacion): " + strSql);
                        stmAux.executeUpdate(strSql);

                        //actualiza primera quincena ... alimentacion
                        double dblValRubPQ = 0;
                        strSql = "";
                        strSql += "select * from tbm_ingegrmentra";
                        strSql += " where co_tra = " + rstRes.getString("co_tra");
                        strSql += " and co_emp = " + rstRes.getString("co_emp");
                        strSql += " and ne_ani = " + intAno;
                        strSql += " and ne_mes = " + intMes;
                        strSql += " and ne_per = 1";
                        strSql += " and co_rub = 5";
                        rstLocAux = stmLocAux2.executeQuery(strSql);

                        if (rstLocAux.next()) {
                            dblValRubPQ += dblValRub * (rstLocAux.getDouble("nd_porrubpag") / 100);
                        }

                        strValRub = "";
                        if (dblValRubPQ == 0) {
                            strValRub = "NULL";
                        } else {
                            dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                            strValRub = String.valueOf(dblValRubPQ);
                        }

                        strSql = "";
                        strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                        strSql += " where co_tra = " + rstRes.getString("co_tra");
                        strSql += " and co_emp = " + rstRes.getString("co_emp");
                        strSql += " and ne_ani = " + intAno;
                        strSql += " and ne_mes = " + intMes;
                        strSql += " and ne_per = 1";
                        strSql += " and co_rub = 4";
                        strSql += " and st_rolPagGen is null";
                        //imprimirEnConsola("4.6-calculo rubro pq (fondos de reserva): " + strSql);
                        stmAux.executeUpdate(strSql);
                        double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());

                        strValRub = "";
                        if (dblFDM == 0) {
                            strValRub = "NULL";
                        } else {
                            dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                            strValRub = String.valueOf(dblFDM);
                        }

                        strSql = "";
                        strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                        strSql += " where co_tra = " + rstRes.getString("co_tra");
                        strSql += " and co_emp = " + rstRes.getString("co_emp");
                        strSql += " and ne_ani = " + intAno;
                        strSql += " and ne_mes = " + intMes;
                        strSql += " and ne_per = 2";
                        strSql += " and co_rub = 4";
                        strSql += " and st_rolPagGen is null";
                        //imprimirEnConsola("4.7-calculo rubro fin mes (alimentacion): " + strSql);
                        stmAux.executeUpdate(strSql);
                    }
                }

                /*ACTUALIZACION DE EGRESOS PROGRAMADOS*/
                //Egresos Programados Indefinidos.
                strSql = "";
                strSql += "select a.co_emp,co_tra,a.co_rub,c.tx_nom,tx_tipegrprg,sum(nd_valegrprg) as nd_valindef from tbm_cabingegrprgtra a";
                strSql += " left outer join tbm_detingegrprgtra b on (a.co_egr=b.co_egr and a.co_emp=b.co_emp)";
                strSql += " inner join tbm_rubrolpag c on (a.co_rub=c.co_rub and tx_tiprub='E')";
                strSql += " where a.tx_tipegrprg like 'I' and a.st_reg like 'A'";
                strSql += " and (fe_ultcuo is null OR EXTRACT(YEAR FROM fe_ultcuo )=" + intAno;
                strSql += " and EXTRACT(MONTH FROM fe_ultcuo )=" + intMes + ")";
                strSql += " group by a.co_emp,co_tra,a.co_rub,c.tx_nom,tx_tipegrprg";
                rstRes = stmLoc.executeQuery(strSql);

                while (rstRes.next()) {

                    double dblValRub = 0;
                    dblValRub += rstRes.getDouble("nd_valindef");

                    String strValRub = "";
                    if (dblValRub == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRub * -1);
                    }
                    //actualiza todo el mes ... egresos programados... 
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 0";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.8-calculo rubro todo el mes (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                    //actualiza primera quincena ... egresos programados... 
                    double dblValRubPQ = 0;
                    strSql = "";
                    strSql += "select * from tbm_ingegrmentra";
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    rstAux = stmAux2.executeQuery(strSql);
                    if (rstAux.next()) {

                        if (rstRes.getString("tx_tipegrprg") != null) {
                            if (rstRes.getString("tx_tipegrprg").compareTo("I") == 0) {
                                dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                            } else if (rstRes.getString("tx_tipegrprg").compareTo("D") == 0) {
                                dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                            }
                        }
                    }
                    strValRub = "";
                    if (dblValRubPQ == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRubPQ * -1);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolPagGen is null";
                   // imprimirEnConsola("calculo rubro todo el mes (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                    double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = "";
                    if (dblFDM == 0) {
                        strValRub = "NULL";
                    } else {
                        dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblFDM * -1);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 2";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.9-calculo rubro primera quincena (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                }
                unCon.commit();
                //Egresos Programados Definidos.
                strSql = "";
                strSql += " SELECT a.co_emp,b.co_tra,(d.tx_ape || ' ' || d.tx_nom)as empleado, b.co_rub,c.tx_nom,tx_tipegrprg,sum(nd_valcuo) as nd_valdef, a.st_reg";
                strSql += " FROM tbm_detingegrprgtra a";
                strSql += " INNER JOIN tbm_cabingegrprgtra b on (a.co_emp=b.co_emp and a.co_egr=b.co_egr)";
                strSql += " INNER JOIN tbm_rubrolpag c on (b.co_rub=c.co_rub and tx_tiprub='E')";
                strSql += " INNER JOIN tbm_tra d on (d.co_tra = b.co_tra)";
                strSql += " WHERE tx_tipegrprg like 'D' and b.st_reg like 'A'  and a.st_reg ='A' "; //Se corrige porque mostraba anulados.
                //Eddye: Se reemplazó las 2 líneas que están a continuación porque estaban mal.
                //       La idea es que sólo se considere los ingresos menores o iguales a la fecha de corte.
                //       Es decir, si el ingreso es ingresado pasada la fecha de corte ya no se debe considerar.
//                strSql += " AND date(b.fe_ing) between (select fe_des from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=1)";
//                strSql += " AND (select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)";
                strSql += " AND date(b.fe_ing)<=(select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)";
                strSql += " AND b.co_rub not in(13,14,15,18,20,27,9,10,29)";
                //Eddye: Se agregó subquery para que considere las cuotas y no el total del documento.
                strSql += " AND a.fe_cuo>=(SELECT fe_des FROM tbm_fecCorRolPag WHERE co_emp=b.co_emp AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1)";
                strSql += " AND a.fe_cuo<=(SELECT fe_has FROM tbm_fecCorRolPag WHERE co_emp=b.co_emp AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2)";

                strSql += " GROUP BY a.co_emp,b.co_tra,empleado,b.co_rub,c.tx_nom,tx_tipegrprg, a.st_reg";
                strSql += " UNION";
                strSql += " SELECT a.co_emp,b.co_tra,(d.tx_ape || ' ' || d.tx_nom)as empleado, b.co_rub,c.tx_nom,tx_tipegrprg,sum(nd_valcuo), a.st_reg as nd_valdef  ";
                strSql += " FROM tbm_detingegrprgtra a ";
                strSql += " INNER JOIN tbm_cabingegrprgtra b on (a.co_emp=b.co_emp and a.co_egr=b.co_egr) ";
                strSql += " INNER JOIN tbm_rubrolpag c on (b.co_rub=c.co_rub and tx_tiprub='E') ";
                strSql += " INNER JOIN tbm_tra d on (d.co_tra = b.co_tra)";
                strSql += " WHERE EXTRACT(YEAR FROM fe_cuo)=" + intAno;
                strSql += " AND EXTRACT(MONTH FROM fe_cuo) = " + intMes;
                strSql += " AND tx_tipegrprg like 'D' and b.st_reg = 'A'";
                strSql += " AND b.co_rub in(13,14,15,18,20,27,9,10,29)";
                strSql += " AND a.st_reg = 'A'";
                strSql += " GROUP BY a.co_emp,b.co_tra,empleado,b.co_rub,c.tx_nom,tx_tipegrprg, a.st_reg";
                rstRes = stmLoc.executeQuery(strSql);
                //System.out.println("Egreso" + strSql);
                while (rstRes.next()) {
                    double dblValRub = 0;
                    dblValRub += rstRes.getDouble("nd_valdef");

                    String strValRub = "";
                    if (dblValRub == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRub * -1);
                    }
                    //actualiza todo el mes ... egresos programados... 
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 0";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("4.9-calculo rubro todo el mes (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                    //actualiza primera quincena ... egresos programados... 

                    double dblValRubPQ = 0;
                    strSql = "";
                    strSql += "select * from tbm_ingegrmentra";
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    rstAux = stmAux2.executeQuery(strSql);

                    if (rstAux.next()) {
                        if (rstRes.getString("tx_tipegrprg") != null) {
                            if (rstRes.getString("tx_tipegrprg").compareTo("I") == 0) {
                                dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                            } else if (rstRes.getString("tx_tipegrprg").compareTo("D") == 0) {
                                dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                            }
                        }
                    }

                    strValRub = "";
                    if (dblValRubPQ == 0) {
                        strValRub = "NULL";
                    } else {
                        dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblValRubPQ * -1);
                    }

                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRubPQ * -1);

                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 1";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolpaggen is null";
                    //imprimirEnConsola("5.0-calculo rubro todo el mes (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                    double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = "";
                    if (dblFDM == 0) {
                        strValRub = "NULL";
                    } else {
                        dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                        strValRub = String.valueOf(dblFDM * -1);
                    }
                    strSql = "";
                    strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + intAno;
                    strSql += " and ne_mes = " + intMes;
                    strSql += " and ne_per = 2";
                    strSql += " and co_rub = " + rstRes.getString("co_rub");
                    strSql += " and st_rolpaggen is null";
                    //imprimirEnConsola("5.1calculo rubro primera quincena (egresos programados): " + strSql);
                    stmAux2.executeUpdate(strSql);
                }
                unCon.commit();
                /*ACTUALIZACION DE EGRESOS PROGRAMADOS*/
            }

            /*CALCULO IESS*/
            strSql = "";
            strSql += "select co_emp, co_tra, nd_valrubbase, nd_valIESS, nd_valRealIESS, co_rub from (";
            strSql += " select a.co_emp, a.co_tra, sum(a.nd_valrub) as nd_valrubbase,  ";
            strSql += " trunc((sum(a.nd_valrub) * (b.nd_apoperies/100)),2) as nd_valIESS,   ((sum(a.nd_valrub) * (b.nd_apoperies/100))*-1) as nd_valRealIESS, ";
            strSql += " 8 as co_rub  from tbm_ingegrmentra a ";
            strSql += " inner join tbm_traemp b on (a.co_tra=b.co_tra and a.co_emp=b.co_emp) ";
            strSql += " where a.ne_ani=" + intAno;
            strSql += " and a.ne_mes=" + intMes;
            strSql += " and a.ne_per=0";
            strSql += " and a.co_rub in(1,2,3,33)  "; //Agregar codigo 33 comisiones
            //strSql+=" --and b.co_emp=4 ";
            strSql += " group by a.co_emp, a.co_tra, a.ne_ani, a.ne_mes, a.ne_per, b.nd_apoperies ";
            strSql += " order by a.co_emp, a.co_tra, a.ne_ani, a.ne_mes, a.ne_per)tiess";
            strSql += " where tiess.nd_valrubbase is not null";
            rstRes = stmLoc.executeQuery(strSql);
            while (rstRes.next()) {                
                double dblValRub = rstRes.getDouble("nd_valRealIESS");
                String strValRub = "";
                if (dblValRub == 0) {
                    strValRub = "NULL";
                } else {
                    dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRub);
                }
                //actualiza todo el mes ... iess... 
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 0";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.2-calculo rubro todo el mes (iess): " + strSql);
                stmAux2.executeUpdate(strSql);
                //actualiza primera quincena ... iess... 
                double dblValRubPQ = 0;
                strSql = "";
                strSql += "select * from tbm_ingegrmentra";
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                rstAux = stmAux2.executeQuery(strSql);
                strValRub = "";
                if (dblValRubPQ == 0) {
                    strValRub = "NULL";
                } else {
                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRubPQ);
                }
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.21-calculo rubro todo el mes (iess): " + strSql);
                stmAux2.executeUpdate(strSql);

                double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());

                strValRub = "";
                if (dblFDM == 0) {
                    strValRub = "NULL";
                } else {
                    dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblFDM);
                }

                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 2";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.3-calculo rubro primera quincena (iess): " + strSql);
                stmAux2.executeUpdate(strSql);
            }
            unCon.commit();
            /*
             * 
             * CALCULO DE ATRASOS DEL PERSONAL
             */
//            System.out.println("PROCESANDO DESCUENTOS POR ATRASOS PARA TODAS LAS EMPRESAS");
//            strSql = "";
//            strSql += "select p.co_emp,p.co_tra,(p.tx_ape || ' ' || p.tx_nom) as empleado,t.nd_valrub,sum(totalFavor) as totalFavor, sum(totalContra) as totalContra,sum(p1) as p1," + "\n";
//            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then (sum(totalContra)-sum(totalFavor)) else null end as diferencia," + "\n";
//            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then (extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor)))) else null end as cantMinutos," + "\n";
//            strSql += " (((t.nd_valrub/30)/8)/60) as valMinuto, " + "\n";
//            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/30)/8)/60))*-1 else null end as nd_valDesAtrAntiguo," + "\n";
//            strSql += " case (sum(p1)>'00:00:00') when true then ((extract(HOUR from (sum(p1)))*60+extract(MINUTES from (sum(p1))))*(((t.nd_valrub/30)/8)/60))*-1 else null end as nd_valDesAtr" + "\n";
//            strSql += " from (" + "\n";
//            strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, g.ho_ent as horaEntrada, g.ho_sal as horaSalida, g.ho_mingraent as minutosGrac," + "\n";
//            strSql += " c.ho_ent, c.ho_sal, st_jusatr, fe_jusatr, " + "\n";
//            strSql += " (case (g.ho_ent > c.ho_ent) when true then (g.ho_ent-c.ho_ent) else null end) as minutosAdelantadoEntrada," + "\n";
//            strSql += " (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) as minutosAtrasadoEntrada," + "\n";
//            strSql += " (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else null end) as minutosAntesSalida," + "\n";
//            strSql += " (case (g.ho_sal< c.ho_sal) when true then (c.ho_sal-g.ho_sal) else null end) as minutosDespuesSalida," + "\n";
//            strSql += " case (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))>((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then" + "\n";
//            strSql += " (c.ho_sal-g.ho_sal)-(c.ho_ent - g.ho_ent)" + "\n";
//            strSql += " else null end as totalFavor," + "\n";
//            strSql += " case st_jusatr is null and (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))<((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then" + "\n";
//            strSql += " (c.ho_ent - g.ho_ent)-(c.ho_sal-g.ho_sal)" + "\n";
//            strSql += " else null end as totalContra , f.co_emp ," + "\n";
//            strSql += " case (st_jusatr is null and (g.ho_ent < c.ho_ent)) when true then (c.ho_ent-g.ho_ent) else '00:00:00' end" + "\n";
//            strSql += " +" + "\n";
//            strSql += " (case st_jussalade is null and (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else '00:00:00' end)" + "\n";
//            strSql += " as p1" + "\n";
//            strSql += " FROM (" + "\n";
//            strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal," + "\n";
//            strSql += " a.tx_obsjusfal,b.st_reg, a.fe_jusatr, a.st_jussalade, a.fe_jussalade  from tbm_cabconasitra a " + "\n";
//            strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c " + "\n";
//            strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra) " + "\n";
//            strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep  " + "\n";
//            strSql += " inner join tbm_callab g on(f.co_hor=g.co_hor and g.fe_dia=c.fe_dia) " + "\n";
//            strSql += " WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=f.co_emp" + "\n";
//            strSql += " and ne_ani=" + intAno + "\n";
//            strSql += " and ne_mes=" + intMes + "\n";
//            strSql += " and ne_per=1) and" + "\n";
//            strSql += " (select fe_has as fe_des from tbm_feccorrolpag where co_emp=f.co_emp" + "\n";
//            strSql += " and ne_ani=" + intAno + "\n";
//            strSql += " and ne_mes=" + intMes + "\n";
//            strSql += " and ne_per=2) " + "\n";
//            strSql += " AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)" + "\n";
//            strSql += " AND NOT (EXTRACT(DOW FROM c.fe_dia)=6 OR EXTRACT(DOW FROM c.fe_dia)=7) " + "\n";
//            strSql += " AND  c.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' )" + "\n";
//            strSql += " AND ( ( (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) is not null " + "\n";
//            strSql += " AND (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) > g.ho_mingraent) " + "\n";
//            strSql += " OR ((case (g.ho_sal > c.ho_sal) when true then (g.ho_sal - c.ho_sal) else null end) IS NOT NULL " + "\n";
//            strSql += " and (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal - c.ho_sal) else null end) > '00:00:00' ) ) " + "\n";
//            strSql += " order by fe_dia asc,tx_ape, tx_nom ) p" + "\n";
//            strSql += " inner join tbm_suetra t on (t.co_emp=p.co_emp and t.co_tra=p.co_tra and t.co_rub = 1)" + "\n";
//            strSql += " group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub" + "\n";
//            strSql += " order by co_emp, empleado" + "\n";
            //Marcaciones modificacion tony
            strSql = "";
            strSql += "select p.co_emp,p.co_tra,(p.tx_ape || ' ' || p.tx_nom) as empleado,t.nd_valrub,sum(totalFavor) as totalFavor, sum(totalContra) as totalContra,sum(p1) as p1," + "\n";
            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then (sum(totalContra)-sum(totalFavor)) else null end as diferencia," + "\n";
            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then (extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor)))) else null end as cantMinutos," + "\n";
            strSql += " (((t.nd_valrub/30)/8)/60) as valMinuto, " + "\n";
            strSql += " case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/30)/8)/60))*-1 else null end as nd_valDesAtrAntiguo," + "\n";
            strSql += " case (sum(p1)>'00:00:00') when true then ((extract(HOUR from (sum(p1)))*60+extract(MINUTES from (sum(p1))))*(((t.nd_valrub/30)/8)/60))*-1 else null end as nd_valDesAtr" + "\n";
            strSql += " from (" + "\n";
            strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_entasi as horaEntrada, c.ho_salasi as horaSalida, c.ho_mingraasient as minutosGrac," + "\n";
            strSql += " c.ho_ent, c.ho_sal, st_jusatr, fe_jusatr, " + "\n";
            strSql += " (case (c.ho_entasi > c.ho_ent) when true then (c.ho_entasi-c.ho_ent) else null end) as minutosAdelantadoEntrada," + "\n";
            strSql += " (case (c.ho_entasi < c.ho_ent) when true then (c.ho_ent - c.ho_entasi) else null end) as minutosAtrasadoEntrada," + "\n";
            strSql += " (case (c.ho_salasi > c.ho_sal) when true then (c.ho_salasi-c.ho_sal) else null end) as minutosAntesSalida," + "\n";
            strSql += " (case (c.ho_salasi< c.ho_sal) when true then (c.ho_sal-c.ho_salasi) else null end) as minutosDespuesSalida," + "\n";
            strSql += " case (((c.ho_entasi-c.ho_ent)+(c.ho_sal-c.ho_salasi))>((c.ho_ent - c.ho_entasi)+(c.ho_salasi-c.ho_sal))) when true then" + "\n";
            strSql += " (c.ho_sal-c.ho_salasi)-(c.ho_ent - c.ho_entasi)" + "\n";
            strSql += " else null end as totalFavor," + "\n";
            strSql += " case st_jusatr is null and (((c.ho_entasi-c.ho_ent)+(c.ho_sal-c.ho_salasi))<((c.ho_ent - c.ho_entasi)+(c.ho_salasi-c.ho_sal))) when true then" + "\n";
            strSql += " (c.ho_ent - c.ho_entasi)-(c.ho_sal-c.ho_salasi)" + "\n";
            strSql += " else null end as totalContra , f.co_emp ," + "\n";
            strSql += " case (st_jusatr is null and (c.ho_entasi < c.ho_ent)) when true then (c.ho_ent-c.ho_entasi) else '00:00:00' end" + "\n";
            strSql += " +" + "\n";
            strSql += " (case st_jussalade is null and (c.ho_salasi > c.ho_sal) when true then (c.ho_salasi-c.ho_sal) else '00:00:00' end)" + "\n";
            strSql += " as p1" + "\n";
            strSql += " FROM (" + "\n";
            strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal," + "\n";
            strSql += " a.tx_obsjusfal,b.st_reg, a.fe_jusatr, a.st_jussalade, a.fe_jussalade,a.ho_entasi,a.ho_salasi,a.ho_mingraasient  from tbm_cabconasitra a " + "\n";
            strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c " + "\n";
            strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra) " + "\n";
            strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep  " + "\n";
            //strSql += " inner join tbm_callab g on(f.co_hor=g.co_hor and g.fe_dia=c.fe_dia) " + "\n";
            strSql += " WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=f.co_emp" + "\n";
            strSql += " and ne_ani=" + intAno + "\n";
            strSql += " and ne_mes=" + intMes + "\n";
            strSql += " and ne_per=1) and" + "\n";
            strSql += " (select fe_has as fe_des from tbm_feccorrolpag where co_emp=f.co_emp" + "\n";
            strSql += " and ne_ani=" + intAno + "\n";
            strSql += " and ne_mes=" + intMes + "\n";
            strSql += " and ne_per=2) " + "\n";
            strSql += " AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)" + "\n";
            strSql += " AND NOT (EXTRACT(DOW FROM c.fe_dia)=6 OR EXTRACT(DOW FROM c.fe_dia)=7) " + "\n";
            strSql += " AND  c.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' )" + "\n";
            strSql += " AND ( ( (case (c.ho_entasi < c.ho_ent) when true then (c.ho_ent - c.ho_entasi) else null end) is not null " + "\n";
            //strSql += " AND (case (c.ho_entasi < c.ho_ent) when true then (c.ho_ent - c.ho_entasi) else null end) > c.ho_mingraasient) " + "\n";
            strSql += " AND (case (c.ho_entasi < c.ho_ent) when true then (c.ho_ent - c.ho_entasi) else null end) > '00:00:00') " + "\n";
            strSql += " OR ((case (c.ho_salasi > c.ho_sal) when true then (c.ho_salasi - c.ho_sal) else null end) IS NOT NULL " + "\n";
            strSql += " and (case (c.ho_salasi > c.ho_sal) when true then (c.ho_salasi - c.ho_sal) else null end) > '00:00:00' ) ) " + "\n";
            strSql += " order by fe_dia asc,tx_ape, tx_nom ) p" + "\n";
            strSql += " inner join tbm_suetra t on (t.co_emp=p.co_emp and t.co_tra=p.co_tra and t.co_rub = 1)" + "\n";
            strSql += " group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub" + "\n";
            strSql += " order by co_emp, empleado" + "\n";
            rstRes = stmLoc.executeQuery(strSql);
            while (rstRes.next()) {
                double dblValRub = 0;
                dblValRub += rstRes.getDouble("nd_valDesAtr");
                String strValRub = "";
                if (dblValRub == 0) {
                    strValRub = "NULL";
                } else {
                    dblValRub = zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRub);
                }
                //actualiza todo el mes ... atrasos
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 0";
                strSql += " and co_rub = 16";//16
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.4-calculo atrasos co_tra " + rstRes.getString("co_tra") + " per 0 --->" + strSql);
                stmAux2.executeUpdate(strSql);
                //actualiza primera quincena ... atrasos...
                double dblValRubPQ = 0;

                strSql = "";
                strSql += "select * from tbm_ingegrmentra";
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = 16";
                //imprimirEnConsola("5.5-" + strSql);
                rstAux = stmAux2.executeQuery(strSql);

                if (rstAux.next()) {
                    dblValRubPQ += dblValRub * (rstAux.getDouble("nd_porrubpag") / 100);
                }

                strValRub = "";
                if (dblValRubPQ == 0) {
                    strValRub = "NULL";
                } else {
                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRubPQ);
                }

                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = 16";
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.6--calculo rubro pq (atrasos): " + strSql);
                stmAux2.executeUpdate(strSql);

                double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());

                strValRub = "";
                if (dblFDM == 0) {
                    strValRub = "NULL";
                } else {
                    dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblFDM);
                }

                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 2";
                strSql += " and co_rub = 16";
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.7-calculo rubro fin mes (atrasos): " + strSql);
                stmAux2.executeUpdate(strSql);
            }
            unCon.commit();
            /*
             * 
             * CALCULO DESCUENTOS POR MARCACIONES INCOMPLETAS PARA TODAS LAS EMPRESAS
             * REVISAR CASO MARIA ELENA / SUSANA PIN
             */

//            System.out.println("PROCESANDO DESCUENTOS POR MARCACIONES INCOMPLETAS PARA TODAS LAS EMPRESAS");
//            strSql = "";
//            strSql += "select p.co_emp,p.co_tra,(p.tx_ape || ' ' || p.tx_nom) as empleado , count(p.fe_dia), t.nd_valrub, (((t.nd_valrub/30)/8)/60) as nd_valPerMin, (count(p.co_tra) * ( (((t.nd_valrub/30)/8)/60) * 2 * 60) ) as nd_valDesc  from (";
//            strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent as ho_enttra, c.ho_sal as ho_saltra, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, ";
//            strSql += " c.tx_obsjusatr, ";
//            strSql += " (case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, ";
//            strSql += " (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc,  ";
//            strSql += " g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,";
//            strSql += " (case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade, f.co_emp from ( ";
//            strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,";
//            strSql += " a.tx_obsjusfal,a.st_jusmarinc,a.tx_obsjusmarinc, b.st_reg from tbm_cabconasitra a ";
//            strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c ";
//            strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra) ";
//            strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep  ";
//            strSql += " inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   ";
//            //strSql+=" WHERE c.fe_dia BETWEEN '"+strFecIni+"' AND '"+strFecFin+"'";
//            strSql += " WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=f.co_emp";
//            strSql += " and ne_ani=" + intAno;
//            strSql += " and ne_mes=" + intMes;
//            strSql += " and ne_per=1) and";
////            strSql+=" '2013-07-25'";
//            strSql += " (select fe_has as fe_des from tbm_feccorrolpag where co_emp=f.co_emp";
//            strSql += " and ne_ani=" + intAno;
//            strSql += " and ne_mes=" + intMes;
//            strSql += " and ne_per=2) ";
//            strSql += " AND NOT (EXTRACT(DOW FROM c.fe_dia)=6 OR EXTRACT(DOW FROM c.fe_dia)=7) "; //tony Se estaba reaizando descuentos para los dias sabados y domingos
//            strSql += " and c.st_jusmarinc is null";
//            strSql += " AND (c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)";
////            strSql += " AND c.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81,163, 124 , 15 , 141 , 125 , 247 , 251 , 267 , 299)";
//            strSql += " AND c.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' ) and f.fe_inicon <= fe_dia " + "\n";//David 
//            strSql += " order by fe_dia asc,tx_ape, tx_nom ) p";
//            strSql += " inner join tbm_suetra t on (p.co_emp=t.co_emp and p.co_tra=t.co_tra and t.co_rub = 1)";
//            strSql += " group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub";
//            strSql += " order by co_emp, co_tra";
            //Modificacion para los horarios tony
            strSql = "";
            strSql += "select p.co_emp,p.co_tra,(p.tx_ape || ' ' || p.tx_nom) as empleado , count(p.fe_dia), t.nd_valrub, (((t.nd_valrub/30)/8)/60) as nd_valPerMin, (count(p.co_tra) * ( (((t.nd_valrub/30)/8)/60) * 2 * 60) ) as nd_valDesc  from (";
            strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent as ho_enttra, c.ho_sal as ho_saltra, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  ";
            strSql += " c.tx_obsjusatr, ";
            strSql += " (case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal,  ";
            strSql += " (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc,    ";
            strSql += " c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,";
            strSql += " (case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade, f.co_emp from (  ";
            strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,";
            strSql += " a.tx_obsjusfal,a.st_jusmarinc,a.tx_obsjusmarinc, b.st_reg,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a  ";
            strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c ";
            strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra) ";
            strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep  ";
            // strSql += " inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   ";
            //strSql+=" WHERE c.fe_dia BETWEEN '"+strFecIni+"' AND '"+strFecFin+"'";
            strSql += " WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=f.co_emp";
            strSql += " and ne_ani=" + intAno;
            strSql += " and ne_mes=" + intMes;
            strSql += " and ne_per=1) and";
//            strSql+=" '2013-07-25'";
            strSql += " (select fe_has as fe_des from tbm_feccorrolpag where co_emp=f.co_emp";
            strSql += " and ne_ani=" + intAno;
            strSql += " and ne_mes=" + intMes;
            strSql += " and ne_per=2) ";
            strSql += " AND NOT (EXTRACT(DOW FROM c.fe_dia)=6 OR EXTRACT(DOW FROM c.fe_dia)=7) "; //tony Se estaba reaizando descuentos para los dias sabados y domingos
            strSql += " and c.st_jusmarinc is null";
            strSql += " AND (c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)";
//            strSql += " AND c.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81,163, 124 , 15 , 141 , 125 , 247 , 251 , 267 , 299)";
            strSql += " AND c.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' ) and f.fe_inicon <= fe_dia " + "\n";//David 
            strSql += " order by fe_dia asc,tx_ape, tx_nom ) p";
            strSql += " inner join tbm_suetra t on (p.co_emp=t.co_emp and p.co_tra=t.co_tra and t.co_rub = 1)";
            strSql += " group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub";
            strSql += " order by co_emp, co_tra";
//            System.out.println("query descuentos x marcacion incompleta ---> " + strSql);
            rstRes = stmLoc.executeQuery(strSql);
            while (rstRes.next()) {

//                System.out.println("DESCUENTO POR MARCACION INCOMPLETA CO_TRA ---> " + rstRes.getString("co_tra") + " EMPLEADO ---> " + rstRes.getString("empleado"));

                double dblValRub = zafUti.redondear(zafUti.parseDouble(rstRes.getDouble("nd_valDesc")), zafParSis.getDecimalesMostrar());

                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + dblValRub;
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = " + 0;
                strSql += " and co_rub = " + 21;
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("5.8act fin de mes rubro (21) periodo 0 ---> " + strSql);
                stmAux.executeUpdate(strSql);

                strSql = "";
                strSql = "select * from tbm_ingegrmentra";
                strSql += " where co_emp = " + rstRes.getString("co_emp");
                strSql += " and co_tra = " + rstRes.getString("co_tra");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = " + 1;
                strSql += " and co_rub = " + 21;
                //imprimirEnConsola("5.9-query obt porcentaje rubro (21) periodo 1 ---> " + strSql);
                rstAux = stmAux.executeQuery(strSql);

                if (rstAux.next()) {

                    double dblValRubQui = rstRes.getDouble("nd_valDesc") * (rstAux.getDouble("nd_porrubpag") / 100);
                    dblValRubQui = zafUti.redondear(zafUti.parseDouble(dblValRubQui), zafParSis.getDecimalesMostrar());

                    String strValRubQ = "";
                    if (dblValRubQui == 0) {
                        strValRubQ = "NULL";
                    } else {
                        strValRubQ = String.valueOf(dblValRubQui * -1);
                    }

                    strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRubQ;
                    strSql += " where co_emp = " + rstAux.getString("co_emp");
                    strSql += " and co_tra = " + rstAux.getString("co_tra");
                    strSql += " and ne_ani = " + rstAux.getString("ne_ani");
                    strSql += " and ne_mes = " + rstAux.getString("ne_mes");
                    strSql += " and ne_per = " + rstAux.getString("ne_per");
                    strSql += " and co_rub = " + 21;
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("6.0-act fin de mes rubro (21) periodo 1 ---> " + strSql);
                    stmAux2.executeUpdate(strSql);

                    double dblValRubFDM = dblValRub - dblValRubQui;
                    dblValRubFDM = zafUti.redondear(zafUti.parseDouble(dblValRubFDM), zafParSis.getDecimalesMostrar());
                    String strValRubFDM = "";
                    if (dblValRubFDM == 0) {
                        strValRubFDM = "NULL";
                    } else {
                        strValRubFDM = String.valueOf(dblValRubFDM * -1);
                    }

                    strSql = "update tbm_ingegrmentra set nd_valrub = " + strValRubFDM;
                    strSql += " where co_emp = " + rstRes.getString("co_emp");
                    strSql += " and co_tra = " + rstRes.getString("co_tra");
                    strSql += " and ne_ani = " + rstAux.getString("ne_ani");//intAno;
                    strSql += " and ne_mes = " + rstAux.getString("ne_mes");
                    strSql += " and ne_per = " + 2;
                    strSql += " and co_rub = " + 21;
                    strSql += " and st_rolPagGen is null";
                    //imprimirEnConsola("6.1-act fin de mes rubro (21) periodo 2 ---> " + strSql);
                    stmAux2.executeUpdate(strSql);

                }
            }
            unCon.commit();


            /*ACTUALIZACION INGRESOS PROGRAMADOS*/
            strSql = "";
            strSql += "SELECT co_emp,co_tra,empleado, co_rub,tx_nom,tx_tipegrprg,sum(nd_valdef) as nd_valdef, st_reg FROM ( " + "\n";
            strSql += " SELECT a.co_emp,b.co_tra,(d.tx_ape || ' ' || d.tx_nom)as empleado, b.co_rub,c.tx_nom,tx_tipegrprg,sum(nd_valcuo) as nd_valdef, a.st_reg " + "\n";
            strSql += " FROM tbm_detingegrprgtra a " + "\n";
            strSql += " INNER JOIN tbm_cabingegrprgtra b ON (a.co_emp=b.co_emp and a.co_egr=b.co_egr) " + "\n";
            strSql += " INNER JOIN tbm_rubrolpag c ON (b.co_rub=c.co_rub and tx_tiprub='I') " + "\n";
            strSql += " INNER JOIN tbm_tra d ON (d.co_tra = b.co_tra) " + "\n";
            strSql += " WHERE tx_tipegrprg like 'D' and b.st_reg like 'A' " + "\n";
            //Eddye: Se reemplazó las 2 líneas que están a continuación porque estaban mal.
            //       La idea es que sólo se considere los ingresos menores o iguales a la fecha de corte.
            //       Es decir, si el ingreso es ingresado pasada la fecha de corte ya no se debe considerar.
//            strSql += "AND date(b.fe_ing) between (select fe_des from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=1) " + "\n";
//            strSql += "AND (select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2) " + "\n";
            //se cambio de b.fe_ing a a.fe_cuo ya que no dividia la cuota pertenenciente al mes que le tocaba
            strSql += " AND date(a.fe_cuo)<=(select fe_has from tbm_feccorrolpag where co_emp=b.co_emp and ne_ani=" + intAno + " and ne_mes=" + intMes + " and ne_per=2)";
            strSql += " AND b.co_rub not IN (1,2,3,4,5,6,7) " + "\n";
            //Eddye: Se agregó subquery para que considere las cuotas y no el total del documento.
            //tony: Se modifica b.fe_doc antes era a.fe_cuo ya que no incluia los dias antes del 1 de cada mes
            strSql += " AND b.fe_doc>=(SELECT fe_des FROM tbm_fecCorRolPag WHERE co_emp=b.co_emp AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1)";
            strSql += " AND b.fe_doc<=(SELECT fe_has FROM tbm_fecCorRolPag WHERE co_emp=b.co_emp AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2)";
            
            strSql += " GROUP BY a.co_emp,b.co_tra,empleado,b.co_rub,c.tx_nom,tx_tipegrprg, a.st_reg " + "\n";
            strSql += " UNION " + "\n";
            strSql += " SELECT a.co_emp,a.co_tra,(c.tx_ape || ' ' || c.tx_nom) as empleado, b.co_rub,b.tx_nom,null as tx_tipegrprg,sum(nd_valrub) as nd_valdef, c.st_reg  " + "\n";
            strSql += " FROM tbm_ingegrmentra a " + "\n";
            strSql += " INNER JOIN tbm_rubrolpag b ON (b.co_rub=a.co_rub and tx_tiprub='I') " + "\n";
            strSql += " INNER JOIN tbm_tra c ON (c.co_tra = a.co_tra) " + "\n";
            strSql += " WHERE a.co_rub not IN (1,2,3,4,5,6,7) " + "\n";
            strSql += " AND a.ne_ani=" + intAno + "\n";
            strSql += " AND a.ne_mes=" + intMes + "\n";
            strSql += " AND a.ne_per=0 " + "\n";
            strSql += " AND a.nd_valrub>0 " + "\n";
            strSql += " group by a.co_emp,a.co_tra,empleado,b.co_rub,b.tx_nom,tx_tipegrprg, c.st_reg " + "\n";
            strSql += ") AS x " + "\n";
            strSql += "GROUP BY co_emp,co_tra,empleado,co_rub,tx_nom,tx_tipegrprg, st_reg " + "\n";
            strSql += "ORDER BY co_emp , empleado ";
            rstRes = stmLoc.executeQuery(strSql);
            
            while (rstRes.next())
            {
                double dblValRubIngDef = rstRes.getDouble("nd_valdef");
                String strValRub = "";
                if (dblValRubIngDef == 0)
                {
                    strValRub = "NULL";
                }
                else
                {
                    dblValRubIngDef = zafUti.redondear(zafUti.parseDouble(dblValRubIngDef), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRubIngDef);
                }
                //actualiza todo el mes ... iess... 
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 0";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                stmAux2.executeUpdate(strSql);
                //actualiza primera quincena ... ingresos programados... 
//                double dblValRubPQ = 0;
//                strSql = "select * from tbm_ingegrmentra where co_tra = " + rstRes.getString("co_tra");
//                strSql += " and ne_ani = " + intAno;
//                strSql += " and ne_mes = " + intMes;
//                strSql += " and ne_per = 1";
//                strSql += " and co_rub = " + rstRes.getString("co_rub");
//                strSql += " and co_emp = " + rstRes.getString("co_emp");
//                rstAux = stmAux.executeQuery(strSql);
//                if (rstAux.next()) {
//                    dblValRubPQ = rstRes.getDouble("nd_valdef") * rstAux.getDouble("nd_porrubpag");
//                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
//                }
//                strValRub = "";
//                if (dblValRubPQ == 0) {
//                    strValRub = "NULL";
//                } else {
//                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
//                    strValRub = String.valueOf(dblValRubPQ);
//                }
                //Primera Quincena-> 22="Otros ingresos".
                //Eddye: Juan solicitó que en la primera quincena no se pague "Otros ingresos".
                
                if (!rstRes.getString("co_rub").equals("33")) {
                strSql = "";
                //strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += "update tbm_ingegrmentra set nd_valrub = NULL"; 
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                stmAux2.executeUpdate(strSql);
                }
                //imprimirEnConsola(strSql);
//                double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRubIngDef - dblValRubPQ), zafParSis.getDecimalesMostrar());
//                strValRub = "";
//                if (dblFDM == 0) {
//                    strValRub = "NULL";
//                } else {
//                    dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
//                    strValRub = String.valueOf(dblFDM);
//                }
                if (!rstRes.getString("co_rub").equals("33")) {
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 2";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                stmAux2.executeUpdate(strSql);
                }
            }
            unCon.commit();
            /*ACTUALIZACION EMPLEADOS QUE SE ENCUENTRAN EN SU DECIMO TERCER MES DE LABORALES Y RECIBIRAN FONDOS DE RESERVA EN LOS ROLES DE PAGO*/
            strSql = "";
            strSql += "SELECT a.co_emp, a.co_tra, a.co_dep, a.co_ofi, c.tx_nom as empresa , (b.tx_ape || ' ' || b.tx_nom) as empleado, \n"
                    + "case (a.st_recfonres) when 'S' then true else false end as blnrecfonres,tx_forrecfonres, a.fe_inicon, current_date, (current_date-a.fe_inicon) as ne_diaLab,\n"
                    + "((current_date-a.fe_inicon)/30) as ne_MesLab, \n"
                    + "case(CASE WHEN (current_date-a.fe_inicon)>360 AND (current_date-a.fe_inicon)<365 THEN 359/30 ELSE ((current_date-a.fe_inicon)/30) END)>=12 when true then true else false end as tx_Actforrecfonres      \n"
                    + "from tbm_traemp a \n"
                    + "INNER JOIN tbm_tra b on (b.co_tra=a.co_tra) \n"
                    + "INNER JOIN tbm_emp c on (c.co_emp=a.co_emp) \n"
                    + "LEFT OUTER JOIN tbm_ofi d on (d.co_ofi=a.co_ofi) \n"
                    + "WHERE a.st_reg='A'  \n"
                    + "AND a.st_recfonres is null\n"
                    + "AND a.co_tra not in (30)\n"
                    + "ORDER BY co_emp, empleado";
//            System.out.println("actualización fondos de reservas empleados: " + strSql);
            rstRes = stmLoc.executeQuery(strSql);
            while (rstRes.next()) {
                if (rstRes.getInt("ne_MesLab") >= 13) {

                    strSql = "";
                    strSql += "UPDATE tbm_traemp SET st_recfonres = 'S' , tx_forrecfonres = 'R' \n"
                            + "WHERE co_emp = " + rstRes.getString("co_emp") + " \n"
                            + "AND co_tra = " + rstRes.getString("co_tra") + "";
                    stmAux2.executeUpdate(strSql);
                }
            }
            unCon.commit();
            /*ACTUALIZACION EMPLEADOS QUE SE ENCUENTRAN EN SU DECIMO TERCER MES DE LABORALES Y RECIBIRAN FONDOS DE RESERVA EN LOS ROLES DE PAGO*/
            /*ACTUALIZACION INGRESOS PROGRAMADOS*/
////                /*DEVOLUCIONES DE DESCUENTOS POR FALTAS*/
////                strSql="";
////                strSql+="select c.co_emp,fe_dia, a.co_tra, (tx_ape || ' ' || tx_nom) as empleado, ho_ent, ho_sal, st_jusfal, tx_obsjusfal, fe_jusfal, co_usrjusfal from tbm_cabconasitra a";
////                strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
////                strSql+=" inner join tbm_traemp c on (c.co_tra=b.co_tra)";
////                //--where fe_dia between '2012-09-24' and '2012-10-25'--corte mes anterior
////                strSql+=" where fe_dia between (select fe_des as fe_des from tbm_feccorrolpag where co_emp=c.co_emp and ne_ani=2012 and ne_mes=10 and ne_per=1)";
////                strSql+=" and (select fe_has as fe_des from tbm_feccorrolpag where co_emp=c.co_emp and ne_ani=2012 and ne_mes=10 and ne_per=2)";
////                strSql+=" and fe_jusfal is not null";
////                //--and fe_jusfal >= '2012-10-26' and fe_jusfal <= '2012-11-25'--corte fdmes actual
////                strSql+=" and fe_jusfal >= (select fe_des as fe_des from tbm_feccorrolpag where co_emp=c.co_emp and ne_ani=2012 and ne_mes=11 and ne_per=1)";
////                strSql+=" and fe_jusfal <= (select fe_has as fe_des from tbm_feccorrolpag where co_emp=c.co_emp and ne_ani=2012 and ne_mes=11 and ne_per=2)";
////                strSql+=" order by (tx_ape || ' ' || tx_nom), fe_dia";
////                System.out.println("devoluciones de descuentos por faltas: " + strSql);
////                rstRes=stmLoc.executeQuery(strSql);
////                while(rstRes.next()){
////                    
////                    String strFil="";
////                    strFil+="Devolución por falta justificada del día: " + rstRes.getString("fe_dia") + ", motivo de justificación: " + rstRes.getString("tx_obsjusfal");
////                    strFil+=", justificada el día: " + rstRes.getString("fe_jusfal");
////                    strSql+="select * from tbm_cabingegrprgtra";
////                    strSql+=" where co_emp = " + rstRes.getString("co_emp");
////                    strSql+=" and co_tra = " + rstRes.getString("co_tra");
////                    strSql+=" and co_rub = " + rstRes.getString("co_rub");
////                    strSql+=" and tx_obs1 = " + zafUti.codificar(strFil);
////                    rstAux = stmLoc.executeQuery(strSql);
////                    if(rstAux.next()){
////                        
////                        strSql+="";
////                    
////                        int intCodDoc=0;
////                        String strSQL="SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_doc  FROM tbm_cabingegrprgtra WHERE " +
////                            " co_emp="+rstRes.getString("co_emp");
////                        rstAux = stmLoc.executeQuery(strSQL);
////                        if(rstRes3.next()){
////                            intCodDoc = rstRes3.getInt("co_doc");
////                            
////                        }
////                            
////                        
////                    }
////                    
////                    
////                    
////                    
////                    
////                    
////                }
////                
////                /*DEVOLUCIONES DE DESCUENTOS POR FALTAS*/


            /*ENVIO DE NOTIFICACIONES*/

            GregorianCalendar gCal = new GregorianCalendar();
            Calendar cal = Calendar.getInstance();
            //Date date = cal.getTime();
            //cal.setTime(date.gett);
            int intDiaL = cal.get(Calendar.DATE);
//            int intDiaL = 1;
//            if (intDiaL == 27) {

            String strCorEleTo = "geren_admin@tuvalsa.com;administracion@tuvalsa.com;rrhh@tuvalsa.com;contador@tuvalsa.com;sistemas2@tuvalsa.com";
//                String strCorEleTo = "sistemas2@tuvalsa.com;";
            String strAsunto = "NOTIFICACIONES RRHH";

            /*Empleados que no tienen asignado departamento*/

            strSql = "";
            strSql += "select (tx_ape || ' ' || tx_nom) as empleado from tbm_traemp a";
            strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSql += " where a.co_dep is null";
            strSql += " and a.st_reg='A'";
            strSql += " and a.co_tra not in (4,41,42)";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetDep = "";
            while (rstRes.next()) {
                strDetDep += rstRes.getString("empleado") + "\n";
            }
            if (!strDetDep.equals("")) {
                strDetDep = "EMPLEADOS QUE NO TIENEN ASIGNADO UN DEPARTAMENTO: \n\n" + strDetDep + "\n\n\n";
            }

            /*Empleados que no tienen asignado Jefes*/

            strSql = "";
            strSql += "select (tx_ape || ' ' || tx_nom) as empleado, * from tbm_traemp a";
            strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSql += " where a.co_jef is null";
            strSql += " and a.st_reg='A'";
            strSql += " and a.co_tra not in (4,41,42)";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetJef = "";

            while (rstRes.next()) {
                strDetJef += rstRes.getString("empleado") + "\n";
            }
            if (!strDetJef.equals("")) {
                strDetJef = "EMPLEADOS QUE NO TIENEN ASIGNADO UN JEFE: \n\n" + strDetJef + "\n\n\n";
            }


            /*Empleados que no tienen asignado Horario de Trabajo (no tienen asignado un horario de trabajo y fisicamente se encuentran en tuval)*/

            strSql = "";
            strSql += "select (tx_ape || ' ' || tx_nom) as empleado, * from tbm_traemp a";
            strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSql += " where a.co_hor is null and co_ofi=1 and st_horfij='S'";
            strSql += " and a.st_reg='A'";
            strSql += " and a.co_tra not in (4,41,42)";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetHorTra = "";

            while (rstRes.next()) {
                strDetHorTra += rstRes.getString("empleado") + "\n";
            }
            if (!strDetHorTra.equals("")) {
                strDetHorTra = "EMPLEADOS QUE NO TIENEN ASIGNADO UN HORARIO DE TRABAJO: \n\n" + strDetHorTra + "\n\n\n";
            }


            /*Empleados que no tienen asignada cuenta bancaria*/

            strSql = "";
            strSql += "select (tx_ape || ' ' || tx_nom) as empleado, * from tbm_traemp a";
            strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSql += " where (tx_tipctaban is null or tx_numctaban is null)";
            strSql += " and a.st_reg='A'";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetCtaBan = "";

            while (rstRes.next()) {
                strDetCtaBan += rstRes.getString("empleado") + "\n";
            }
            if (!strDetCtaBan.equals("")) {
                strDetCtaBan = "EMPLEADOS QUE NO TIENEN ASIGNADA UNA CUENTA BANCARIA: \n\n" + strDetCtaBan + "\n\n\n";
            }
            //enviarCorreo(strCorEleTo, strAsunto, (strDetDep + strDetJef + strDetHorTra + strDetCtaBan));

            strCorEleTo = "geren_admin@tuvalsa.com;contador@tuvalsa.com;sistemas2@tuvalsa.com;administracion@tuvalsa.com";
            /*Empleados que no tienen asignados los rubros de ingreso*/

            strSql = "";
            strSql += "select co_emp,tx_nomemp as tx_nomemp,nd_minsecasi,d.co_tra,d.co_dep,tx_tipmodsue,(e.tx_ape ||' '|| e.tx_nom) as nomapetra,tx_nomcar, minSecSug from (";
            strSql += " select a.co_emp,a.co_tra,a.co_dep,a.tx_tipmodsue,a.nd_minsecasi,b.tx_nom as tx_nomemp, c.tx_nomcar,c.co_comsec,c.tx_codcomsec, c.nd_minSec as minSecSug from tbm_traemp a";
            strSql += " inner join tbm_emp b on(a.co_emp=b.co_emp)";
            strSql += " left join tbm_carlab c on(a.co_car=c.co_car) where a.st_reg like 'A') d";
            strSql += " inner join tbm_tra e on (d.co_tra=e.co_tra) ";
            strSql += " where d.tx_tipmodsue is null";
            strSql += " group by d.co_emp, tx_nomemp,nd_minsecasi,d.co_tra, d.co_dep, d.tx_tipmodsue, (tx_ape ||' '|| e.tx_nom) , tx_nomcar, minSecSug";
            strSql += " order by nomapetra";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetIng = "";

            while (rstRes.next()) {
                strDetIng += rstRes.getString("nomapetra") + "\n";
            }
            if (!strDetIng.equals("")) {
                strDetIng = "EMPLEADOS QUE NO TIENEN ASIGNADOS LOS RUBROS DE INGRESOS: \n\n" + strDetIng + "\n\n\n";
            }


            /*Empleados que no tienen asignado una plantilla para el rol de pagos*/


            strSql = "";
            strSql += "select (tx_ape || ' ' || tx_nom) as empleado, * from tbm_traemp a";
            strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSql += " where co_pla is null";
            strSql += " and a.st_reg='A'";
            rstRes = stmLoc.executeQuery(strSql);

            String strDetPla = "";

            while (rstRes.next()) {
                strDetPla += rstRes.getString("empleado") + "\n";
            }
            if (!strDetPla.equals("")) {
                strDetPla = "EMPLEADOS QUE NO TIENEN ASIGNADA UNA PLANTILLA PARA EL ROL DE PAGOS: \n\n" + strDetPla + "\n\n\n";
            }


//            if (!strDetIng.equals("") || !strDetPla.equals("")) {
//            	enviarCorreo(strCorEleTo, strAsunto, (strDetIng + strDetPla));
//            }
//            }

            /*NOVEDADES DE RRHH MENSUALES*/
            boolean blnEnvNov = false;
            int intConNov = 0;
            if (intDiaL == 32) {
                strSql = "";
                strSql += "select * from tbm_planovrechum ";
                strSql += " where st_reg='A' ";
                strSql += " order by co_nov";
                rstRes = stmLoc.executeQuery(strSql);

                while (rstRes.next()) {
                    String strCodUsrRee = rstRes.getString("co_usrrem");
                    String strCodUsrDes = rstRes.getString("co_usrdes");
                    String strCodTipNov = rstRes.getString("co_tipnov");
                    String strDesAsuNov = rstRes.getString("tx_asu");
                    String strMsj = rstRes.getString("tx_men");
                    if (insertarRegNovedades(unCon, zafUti, zafParSis, strCodUsrRee, strCodUsrDes, strCodTipNov, strDesAsuNov, strMsj)) {
                        intConNov++;
                    }
                }
                if (intConNov == 5) {
                    blnEnvNov = true;
                }
            }

            if (blnEnvNov) {
                unCon.commit();
            }


            /*ACTUALIZACION DE DIAS LABORABLES DE NUEVOS EMPLEADOS*/
            boolean blnDiaLabNueTra = insertaDiasLaboradosEmpleadosNuevos(unCon, zafUti, zafParSis, intAno, intMes);

            if (blnDiaLabNueTra)
            {
                unCon.commit();
            }
            /*ACTUALIZACION DE DIAS LABORABLES DE NUEVOS EMPLEADOS*/
            /*CREACION DE CUENTAS POR COBRAR AL EMPLEADO EN CASO DE NEGATIVO*/
            //10;"Préstamo empresa"
            strSql = "";
            strSql += "select * ,  10 as co_rub from ( " + "\n"
                    + "select co_emp, co_tra, sum(nd_valrub) as nd_valRecFDM " + "\n"
                    + "from tbm_ingegrmentra  " + "\n"
                    + "where ne_ani=" + intAno + " " + "\n"
                    + "and ne_mes=" + intMes + "\n"
                    + "and ne_per in (2) and co_rub not in (6,7,25,26)" + "\n"//David
                    + "group by co_emp, co_tra " + "\n"
                    + ") as x " + "\n"
                    + "where x.nd_valRecFDM<0 " + "\n";
            rstRes=stmLoc.executeQuery(strSql);
            while (rstRes.next())
            {
                double dblValRub=rstRes.getDouble("nd_valRecFDM");
                String strValRub="";
                if (dblValRub==0)
                {
                    strValRub="NULL";
                }
                else
                {
                    dblValRub=zafUti.redondear(zafUti.parseDouble(dblValRub), zafParSis.getDecimalesMostrar()) * -1;
                    strValRub=String.valueOf(dblValRub);
                }
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = coalesce(nd_valrub,0) + " + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 0";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                stmAux2.executeUpdate(strSql);
                double dblValRubPQ = 0;
                strValRub = "";
                if (dblValRubPQ == 0)
                {
                    strValRub = "NULL";
                }
                else
                {
                    dblValRubPQ = zafUti.redondear(zafUti.parseDouble(dblValRubPQ), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblValRubPQ);
                }
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = coalesce(nd_valrub,0) +" + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 1";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("6.5-calculo rubro todo el mes (iess):" + strSql);
                stmAux2.executeUpdate(strSql);

                double dblFDM = zafUti.redondear(zafUti.parseDouble(dblValRub - dblValRubPQ), zafParSis.getDecimalesMostrar());

                strValRub = "";
                if (dblFDM == 0) {
                    strValRub = "NULL";
                } else {
                    dblFDM = zafUti.redondear(zafUti.parseDouble(dblFDM), zafParSis.getDecimalesMostrar());
                    strValRub = String.valueOf(dblFDM);
                }
                
                strSql = "";
                strSql += "update tbm_ingegrmentra set nd_valrub = coalesce(nd_valrub,0) +" + strValRub;
                strSql += " where co_tra = " + rstRes.getString("co_tra");
                strSql += " and co_emp = " + rstRes.getString("co_emp");
                strSql += " and ne_ani = " + intAno;
                strSql += " and ne_mes = " + intMes;
                strSql += " and ne_per = 2";
                strSql += " and co_rub = " + rstRes.getString("co_rub");
                strSql += " and st_rolPagGen is null";
                //imprimirEnConsola("6.4-calculo rubro primera quincena (iess):" + strSql);
                stmAux2.executeUpdate(strSql);
//tony prueba decimo tercer                actualizarRubDecTer(unCon, intAno, intMes, Integer.parseInt(rstRes.getString("co_emp")), Integer.parseInt(rstRes.getString("co_tra")), dblValRubFDM, dblFDM, dblFDM, dblFDM);
            }
            
            if (!actualizarRubDecTer(unCon, arrLstEmp, intMes, intAno)) {
                imprimirEnConsola("Error Decimos.");
            }
       
            unCon.commit();
        } catch (Throwable e) {

            blnResult = false;

            String strMsj = " " + e.getMessage() + " " + "\n" + " " + e.getCause();
            StackTraceElement[] st = e.getStackTrace();

            e.printStackTrace();

            try {
//                mail.enviar("ZafSer09", strMsj + "\n" + e.toString());
            } catch (Throwable ignore) {
            }

            String str = e.toString();

            unCon.rollback();

            System.exit(0);
        } finally {
            try {
                rstLocAux.close();
                rstLocAux = null;
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
                rstLoc = null;
            } catch (Throwable ignore) {
            }

            try {
                rstAux.close();
                rstAux = null;
            } catch (Throwable ignore) {
            }
            try {
                rstRes.close();
                rstRes = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLocAux2.close();
                stmLocAux2 = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLocAux.close();
                stmLocAux = null;
            } catch (Throwable ignore) {
            }

            try {
                stmAux.close();
                stmAux = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
                stmLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                stmAux2.close();
                stmAux2 = null;
            } catch (Throwable ignore) {
            }
            try {
                unCon.close();
                unCon = null;
            } catch (Throwable ignore) {
            }
        }

        if (blnResult) {
            //log.println("ZAFSER09 - PROCESO EJECUTADO CORRECTAMENTE");
            try {
//                mail.enviar("ZafSer09", "PROCESO EJECUTADO CORRECTAMENTE" + "\n");
            } catch (Throwable ignore) {
            }
        }

    }

    private static boolean insertarRegNovedades(Connection con, ZafUtil objUti, ZafParSis_Cosenco objZafParSis, String strCodUsrRee, String strCodUsrDes, String strCodTipNov,
            String strDesAsuNov, String strMsj) throws Exception {
        boolean blnRes = false;

//        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        int intCodNov = 0;

        try {
//            con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con != null) {
                con.setAutoCommit(false);
                stmLoc = con.createStatement();

                strSQL = "SELECT max(co_nov) as co_nov from tbm_novRecHum";
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodNov = rstLoc.getInt("co_nov") + 1;
                }
                rstLoc.close();
                rstLoc = null;

//                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                String FecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), "dd/MM/yyyy HH:MM:SS");

                if (insertarRegNov(con, objUti, objZafParSis, intCodNov, FecAux, strCodUsrRee, strCodUsrDes, strCodTipNov, strDesAsuNov, strMsj)) {
//                    con.commit();
                    blnRes = true;
                }
//                else{
//                    con.rollback();
//                }
            }
        } //        catch (java.sql.SQLException Evt) {
        //            Evt.printStackTrace();
        //            blnRes = false;
        //        } catch (Exception Evt) {
        //            blnRes = false;
        //        }
        finally {
            try {
                stmLoc.close();
                stmLoc = null;
            } catch (Throwable ignore) {
            }
//            try{con.close();con=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }

    /**
     * Esta función permite asignar los días laborados a los empleados nuevos.
     * Es decir, los empleados que ingresaron en el mes a procesar.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param objUti El objeto de ZafUtil. 
     * @param objZafParSis El objeto ZafParSis que contiene la configuración.
     * @param intNeAnio El año a actualizar.
     * @param intNeMes El mes a actualizar.
     * @return true: Si se pudo actualizar los días laborados de los empleados nuevos.
     * <BR>false: En el caso contrario.
     */
    private static boolean insertaDiasLaboradosEmpleadosNuevos(java.sql.Connection con, ZafUtil objUti, ZafParSis_Cosenco objZafParSis, int intNeAnio, int intNeMes)
    {
        boolean blnRes = true;
        String strFecSis = "";
        String strSql = "";
        java.sql.Statement stmLoc=null, stmLocAux=null, stmAux=null;
        java.sql.ResultSet rstLoc=null, rstLocAux=null;
        try
        {
            if (con!=null)
            {
                stmLoc=con.createStatement();
                stmLocAux=con.createStatement();
                stmAux=con.createStatement();
                strSql="";
                String strDiaMax="";
                if (intNeMes==2)
                {
                    strDiaMax="28";
                }
                else
                {
                    strDiaMax="30";
                }
                strSql+="select co_emp , co_tra , fe_inicon, current_date, ('" + intNeAnio + "-" + intNeMes + "-" + strDiaMax + "'::date-fe_inicon)+1 as ne_diaLab, EXTRACT(MONTH FROM fe_inicon) as neMes, EXTRACT(YEAR FROM fe_inicon) as neAni\n"
                + "from tbm_traemp \n"
                + "where EXTRACT(MONTH FROM fe_inicon)=" + intNeMes + "\n"
                + "AND EXTRACT(YEAR FROM fe_inicon)=" + intNeAnio + "\n"
                + "AND st_reg = 'A'" + "\n"
                + "ORDER BY co_emp, co_tra";
                rstLoc=stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    strSql="";
                    strSql+="SELECT * FROM tbm_datgeningegrmentra \n"
                    + "WHERE co_emp = " + rstLoc.getString("co_emp") + "\n"
                    + "AND co_tra = " + rstLoc.getString("co_tra") + "\n"
                    + "AND ne_ani = " + rstLoc.getString("neAni") + "\n"
                    + "AND ne_mes = " + rstLoc.getString("neMes") + "\n"
                    + "AND ne_per = 0";
                    rstLocAux=stmLocAux.executeQuery(strSql);
                    if (rstLoc.getInt("co_tra")==219)
                    {
                        
                    }
                    int intCanDia=diasFaltasSueldo(con, objUti, objZafParSis, rstLoc.getInt("co_emp"), rstLoc.getInt("co_tra"), rstLoc.getInt("neAni"), rstLoc.getInt("neMes"), rstLoc.getString("fe_inicon"));
                    if (rstLocAux.next())
                    {
                        strSql = "";
                        strSql += "update tbm_datgeningegrmentra\n"
                        + "SET ne_numdialab = " + (rstLoc.getInt("ne_diaLab") - (intCanDia)) + " , \n"
                        + "ne_numFal = " + (intCanDia) + "\n"
                        + "WHERE co_emp = " + rstLocAux.getString("co_emp") + "\n"
                        + "and ne_ani = " + rstLoc.getString("neAni") + " \n"
                        + "and ne_mes = " + rstLoc.getString("neMes") + "\n"
                        + "and co_tra = " + rstLocAux.getString("co_tra") + " \n"
                        + "and ne_per = 0"
                        + " and st_rolpaggen is null";
                        stmAux.executeUpdate(strSql);
                    }
                    else
                    {
                        //INSERTAR DIAS LABORABLES tbm_datgeningegrmentra PARA EMPLEADOS NUEVOS
                        strSql = "";
                        strSql += "INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab)";
                        strSql += " VALUES (";
                        strSql += " " + rstLoc.getString("co_emp") + " , ";
                        strSql += " " + rstLoc.getString("co_tra") + " , ";
                        strSql += " " + rstLoc.getString("neAni") + " , ";
                        strSql += " " + rstLoc.getString("neMes") + " , ";
                        strSql += " " + 0 + " , ";
                        strSql += " NULL , ";
                        strSql += " NULL , ";
                        strSql += " " + (rstLoc.getInt("ne_diaLab") - intCanDia) + " ) ";
                        stmAux.executeUpdate(strSql);
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            e.printStackTrace();
        }
        finally
        {
            try
            {
                stmLoc.close();
            }
            catch (Throwable ignore)
            {
                
            }
            try
            {
                stmLocAux.close();
            }
            catch (Throwable ignore)
            {
                
            }
            try
            {
                rstLoc.close();
            }
            catch (Throwable ignore)
            {
                
            }
            try
            {
                rstLocAux.close();
            }
            catch (Throwable ignore)
            {
                
            }
        }
        return blnRes;
    }

    /*
     * Faltas que afectan al sueldo.
     * Solo faltas de Lunes a Viernes.
     */
    private static int diasFaltasSueldo(java.sql.Connection con, ZafUtil objUti, ZafParSis_Cosenco objZafParSis, int intCoEmp, int intCoTra, int intNeAnio, int intNeMes, String fe_inicon) throws Exception {

        boolean blnRes = false;
        String strFecSis = "";
        String strSql = "";
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        int intCantFal = 0;

        try {
            if (con != null) {

                stmLoc = con.createStatement();

                strSql = "";
                strSql = "select co_tra, count (fe_dia) as canDia from (";
                strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  ";
                strSql += " c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, ";
                strSql += " g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, ";
                strSql += " (case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade, f.co_emp from (  ";
                strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg ";
                strSql += " from tbm_cabconasitra a  ";
                strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c  ";
                strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')  ";
                strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep   ";
                strSql += " inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    ";
                strSql += " WHERE c.fe_dia BETWEEN " + objUti.codificar(fe_inicon) + " AND '2014-03-27' ";
                strSql += " and not (EXTRACT(DOW FROM c.fe_dia ) in (6,7))";
                strSql += " AND (c.ho_ent is null AND c.ho_sal is null) ";
                strSql += " AND not ((case c.st_jusfal when 'S' then true else false end)=true) ";
                strSql += " UNION  ";
                strSql += " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr,  ";
                strSql += " c.tx_obsjusatr,  (case c.st_jusfal when 'S' then true else false end) as chkJusFal,  c.tx_obsjusfal, g.ho_ent as ho_entHorTra, ";
                strSql += " g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra, ";
                strSql += " (case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade,f.co_emp  from ( ";
                strSql += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg ";
                strSql += " from tbm_cabconasitra a  ";
                strSql += " inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c  ";
                strSql += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' and f.st_reg='A')  ";
                strSql += " left outer join tbm_dep d on d.co_dep=f.co_dep   ";
                strSql += " left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    ";
                strSql += " WHERE c.fe_dia BETWEEN " + objUti.codificar(fe_inicon) + " AND '2014-03-27' ";
                strSql += " and not (EXTRACT(DOW FROM c.fe_dia ) in (6,7))";
                strSql += " AND (c.ho_ent is null AND c.ho_sal is null) ";
                strSql += " AND not ((case c.st_jusfal when 'S' then true else false end)=true) ";
                strSql += " order by fe_dia asc,tx_ape, tx_nom ";
                strSql += " ) x";
                strSql += " WHERE x.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' )" + "\n";
                strSql += " and x.co_tra=" + intCoTra;
                strSql += " and x.co_emp=" + intCoEmp;
                strSql += " group by co_tra";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCantFal = rstLoc.getInt("canDia");
                }
            }
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
        }
        return intCantFal;
    }

    private static boolean insertarRegNov(java.sql.Connection con, ZafUtil objUti, ZafParSis_Cosenco objZafParSis, int intCodNov, String FecAux, String strCodUsrRee, String strCodUsrDes, String strCodTipNov,
            String strDesAsuNov, String strMsj) throws Exception {
        boolean blnRes = false;
        String strFecSis = "";
        String strSql = "";
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try {
            if (con != null) {
                stmLoc = con.createStatement();

                strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());
                String strStReg = "P";

                strSql = "INSERT INTO tbm_novrechum(co_nov, co_usrrem, co_usrdes, co_tipnov, tx_asu, tx_men, st_lec, fe_lec, ne_numree, co_usrdesree,";
                strSql += " tx_obsfin, fe_fin, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) VALUES (";
                strSql += " " + intCodNov + ",";
                strSql += " " + strCodUsrRee + ",";
                strSql += " " + strCodUsrDes + ",";
                strSql += " " + strCodTipNov + ",";
                strSql += " '" + strDesAsuNov + "',";
                strSql += " " + objUti.codificar(strMsj) + ",";
                strSql += " NULL ,";
                strSql += " NULL ,";
                strSql += " NULL ,";
                strSql += " NULL ,";
                strSql += " NULL ,";
                strSql += " NULL ,";
                strSql += " " + objUti.codificar(strStReg) + ",";
                strSql += " CURRENT_TIMESTAMP ,";
                strSql += " NULL ,";
                strSql += " " + strCodUsrRee + ",";
                strSql += " NULL ";
                strSql += ")";

//                      System.out.println("insertTbm_NovRecHum: " + strSql);
                stmLoc.executeUpdate(strSql);
                blnRes = true;
            }
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    /**
     * Imprime el mensaje en consola y tambien en un log
     * @param mensaje 
     */
    public void imprimirEnConsola(String mensaje) {
        System.out.println(mensaje);
        try {
			log.println(mensaje);
		} catch (TuvalUtilitiesException e) {
			e.printStackTrace();
		}
        
    }

    public static void main(String[] args) {
        ZafSer09 ZafSer09 = new ZafSer09();
        try {
            ZafSer09.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Insertar rubros con porcentajes.
     * @param conex
     * @param intCoEmp
     * @param intCoTra
     * @param intAnio
     * @param intMes
     * @param intPer
     * @param intCoRub
     * @param strValRub
     * @param strPorRubPag
     * @param strStRolPaGgen
     * @return 
     */
    public boolean grabarIngEgrMenTra( Connection conex, int intCoEmp, int intCoTra, int intAnio, int intMes, int intPer, int intCoRub, String strValRub, String strPorRubPag, String strStRolPaGgen) {
        boolean resp = false;
        StringBuilder strSql = new StringBuilder();
        strSql.append("INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, ");
        strSql.append("nd_valrub, nd_porrubpag, st_rolpaggen)");
        strSql.append(" VALUES (" + intCoEmp + ", " + intCoTra + ", " + intAnio + ", " + intMes + ", " + intPer + ", " + intCoRub+", " ) ;
        strSql.append( strValRub+", " + strPorRubPag+", " + strStRolPaGgen + ")");
        try {
            CallableStatement cs = conex.prepareCall(strSql.toString());
            //System.out.println(strSql.toString());
            cs.execute();
            resp= true;
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
    
    public boolean grabarDatGenIngEgrMenTra( Connection conex, int intCoEmp, int intCoTra, int intAnio, int intMes, int intPer, String strTxObs1, String strStRolPagGen, int intNeNumDiaLab ) {
        boolean resp = false;
        StringBuilder strSql = new StringBuilder();
        strSql.append("INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab)");
        strSql.append(" VALUES (" + intCoEmp + ", " + intCoTra + ", " + intAnio + ", " + intMes + ", " + intPer + ", " + strTxObs1+", " + strStRolPagGen + ", " + intNeNumDiaLab + ")"  ) ;
        try {
            //imprimirEnConsola("7.4-" + strSql.toString());
            CallableStatement cs = conex.prepareCall(strSql.toString());
            cs.execute();
            resp= true;
            cs.close();
        } catch (Exception e) {
            e                     .printStackTrace();
        }
        return resp;
    }    
    
    public boolean actualizarIngEgrMenTra( Connection conex, int intCoEmp, int intCoTra, int intAnio, int intMes, int intPer, 
                int intCoRub, String strValRub, String strStRolPagGen) {
        boolean resp = false;
        StringBuilder strSql = new StringBuilder();
        strSql.append("update tbm_ingegrmentra set nd_valrub = " + strValRub);
        strSql.append(" where co_tra = " + intCoTra);
        strSql.append(" and ne_ani = " + intAnio);
        strSql.append(" and ne_mes = " + intMes);
        strSql.append(" and ne_per = " + intPer);
        strSql.append(" and co_rub = " + intCoRub);
        strSql.append(" and co_emp = " + intCoEmp);
        strSql.append(" and st_rolPagGen " + strStRolPagGen );
        try {
            //imprimirEnConsola("7.0-actualizarIngEgrMenTra:" + strSql.toString());
            CallableStatement cs = conex.prepareCall(strSql.toString());
            cs.execute();
            resp= true;
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
    
 
    public String restablecerSemanaIntegral(Connection conex, int intAnio, int intMes, int intCoEmp, int intNePer ){
        String resp = "";
        try {
        	String strSql2 = "update tbm_ingegrmentra set nd_valrub = null";
            strSql2 += " where ne_ani = " + intAnio;
            strSql2 += " and ne_mes = " + intMes;
            strSql2 += " and co_rub = 30 and co_emp = " + intCoEmp;
            strSql2 += " and st_rolPagGen is null and nd_valrub is not null";
            CallableStatement cs = conex.prepareCall( strSql2 );
            int numFil = cs.executeUpdate();
            //imprimirEnConsola("0.0-" + strSql2 + "" + numFil);
            cs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resp;       
    }
    
    
    public String consultarFecCorRolPag(Connection conex, int intAnio, int intMes, int intCoEmp, int intNePer ){
        String resp = "";
        try {
            CallableStatement cs = conex.prepareCall("select * from tbm_feccorrolpag where ne_ani =" + intAnio +" and ne_mes=" + intMes+ " and co_emp =" + intCoEmp + " and ne_per=" + intNePer );
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
              resp = rs.getString("fe_des")  ;
              resp = resp + ";" + rs.getString("fe_has")  ;
            }
            rs.close();
            cs.close();
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return resp;       
    }
    
    /** Validación de cerrar periodos pasados para empleados nuevos.
     */
    private void cerrarPerAntEmpNuevo( Connection conex,  int intAnio, int intMes, int intCoEmp ){
        try {
            //consulto si exite un rol generado de la 1 quincena
            String strSql ;
            strSql = "select count(1) from tbm_ingegrmentra ie where st_rolpaggen = 'S' and co_rub = 1 "+
                                                    " and ie.ne_ani ="+ intAnio + " and ie.ne_mes ="+ intMes +
                                                    " and ne_per= 1"+ " and co_emp= " + intCoEmp;
            CallableStatement cs = conex.prepareCall(strSql);
            //imprimirEnConsola("7.1-" + strSql);
            ResultSet rs2 = cs.executeQuery();
            if(rs2.next()){
                if(rs2.getInt(1) > 0) {
                    strSql = "select ie.co_emp, ie.co_tra from tbm_ingegrmentra ie, tbm_traemp te where extract(year from fe_inicon)="+ intAnio + " and extract(month from fe_inicon)=" + intMes +
                                " and st_rolpaggen is null and st_reg = 'A' and ie.co_emp=" + intCoEmp + " and ie.co_emp = te.co_emp"+
                                " and ie.co_tra = te.co_tra"+ " and ie.ne_ani = "+ intAnio+
                                " and ie.ne_mes ="+ intMes + " and ne_per= 1"+
                                "group by ie.co_emp, ie.co_tra";
                    cs = conex.prepareCall(strSql);
                    //imprimirEnConsola("7.2-" + strSql);
                    ResultSet rs = cs.executeQuery();
                    while(rs.next()){
                        strSql = "update tbm_ingegrmentra set st_rolpaggen = 'S'"+
                                    "where st_rolpaggen is null and co_tra = "+  rs.getString("co_tra") +
                                            "and ne_ani="+  intAnio +  "and ne_mes="+ intMes + "and ne_per=1";
                        cs = conex.prepareCall(strSql);
                        //imprimirEnConsola("7.3-" + strSql);
                        cs.execute();
                    }
                    rs.close();
                }
            }
            cs.close();
            rs2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void enviarCorreo(String strCorEleTo, String subject, String strMsj){
    	if(intEnviarCorreo == 1){
    		new Mail().enviarCorreoMasivo(strCorEleTo, subject, strMsj);
    		imprimirEnConsola(subject);
    	}
    }
   
     public Boolean actualizarRubDecTer(Connection conex, ArrayList<Integer> arrLstEmpDec, int intMes1, int intAno1){
        double douValTotDecTer=0.00,douValTotDecCua=0.00;
        double douSue=0.00, douHorExt1=0.00, douHorExt2=0.00, douCom=0.00;
        int intNumDiaTra=0;
        String strSql="";
        Statement stmLoc = null;
        Statement stmAux = null;
        Statement stmTra = null;
        Statement stmAux2 = null;
        ResultSet rstRes = null;
        ResultSet rstResTra = null;
        ResultSet rstAux = null;
        ArrayList<Tbm_tra> listTra=null;
        ZafSueBas objSueBas = new ZafSueBas();
        try {
            
            for (int intCoEmp : arrLstEmpDec)
                {
                    strSql = "SELECT distinct a.co_tra,b.tx_nom,b.tx_ape, fe_inicon, Tx_ForPagDecTerSue, Tx_ForPagDecCuaSue, "; //Aumento de dos campos nuevos para Visualizar la forma de pago Dmo 3 o Dmo4. tony
                    strSql += " case ( (EXTRACT (MONTH FROM fe_inicon)=" + intMes1 + ") AND (EXTRACT (YEAR FROM fe_inicon)=" + intAno1 + ") ) when true then true else false end as blnIsTraNew";
                    strSql += " FROM tbm_traemp a";
                    strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSql += " WHERE a.st_reg like 'A'";
                    strSql += " and co_emp = " + intCoEmp;
                    strSql += " and (a.Tx_ForPagDecTerSue= 'M' or   a.Tx_ForPagDecCuaSue='M' )" ;
                    strSql += " order by a.co_tra";
                    stmLoc = conex.createStatement();
                    rstRes = stmLoc.executeQuery(strSql);
                    //imprimirEnConsola(strSql);
                    if (rstRes.next())
                    {
                        listTra = new ArrayList<Tbm_tra>();
                        do
                        {
                            Tbm_tra tbmTra = new Tbm_tra();
                            tbmTra.setIntCo_tra(rstRes.getInt("co_tra"));
                            tbmTra.setStrTx_nom(rstRes.getString("tx_nom"));
                            tbmTra.setStrTx_ape(rstRes.getString("tx_ape"));
                            tbmTra.setBlnIsTraNew(rstRes.getBoolean("blnIsTraNew"));
                            tbmTra.setStrFeIniCon(rstRes.getString("fe_inicon"));
                            tbmTra.setStrTx_ForPagDecTerSue(rstRes.getString("Tx_ForPagDecTerSue"));//Se agrego para obtener la forma de pago del decimo tercer. tony
                            tbmTra.setStrTx_ForPagDecCuaSue(rstRes.getString("Tx_ForPagDecCuaSue"));//Se agrego para obtener la forma de pago del decimo cuarto. tony
                            listTra.add(tbmTra);
                        } while (rstRes.next());
                    }
                    if (conex != null)
                    {
                        if (listTra != null) {
                         stmTra = conex.createStatement();
                         stmAux = conex.createStatement();
                         stmAux2 = conex.createStatement();
                                for (Tbm_tra tbm_tra : listTra)
                                {
                                    douSue=0.00;
                                    douHorExt1=0.00;
                                    douHorExt2=0.00;
                                    douCom=0.00;
                                    
                                    strSql = " select * ";
                                    strSql +=" from tbm_ingegrmentra "; 
                                    strSql +=" where co_tra= "+ tbm_tra.getIntCo_tra();
                                    strSql +=" and  co_emp= " + intCoEmp;
                                    strSql +=" and  co_rub in(1,2,3,33) ";
                                    strSql +=" and   ne_per=0 ";
                                    strSql +=" and   ne_ani="+ intAno1;
                                    strSql +=" and   ne_mes="+ intMes1;
                                    rstResTra = stmTra.executeQuery(strSql);
                                    //imprimirEnConsola(strSql);
                                    if (rstResTra.next())
                                    {
                                     do{
                                         if (rstResTra.getInt("co_rub")==1) {
                                             douSue=rstResTra.getDouble("nd_Valrub");
                                         }else if (rstResTra.getInt("co_rub")==2) {
                                             douHorExt1=rstResTra.getDouble("nd_Valrub");
                                         }else if (rstResTra.getInt("co_rub")==3) {
                                             douHorExt2=rstResTra.getDouble("nd_Valrub");
                                         }else if (rstResTra.getInt("co_rub")==33) {
                                             douCom=rstResTra.getDouble("nd_Valrub");
                                         } }      
                                    while(rstResTra.next());
                                    if (tbm_tra.getStrTx_ForPagDecTerSue()!=null && !tbm_tra.getStrTx_ForPagDecTerSue().isEmpty() && tbm_tra.getStrTx_ForPagDecTerSue().equals("M")) {
                                        douValTotDecTer = (douSue + douHorExt1+ douHorExt2 + douCom )/12;
                                        String strSql2 = "update tbm_ingegrmentra set nd_valrub = " + douValTotDecTer;
                                                strSql2 += " where ne_ani = " + intAno1;
                                                strSql2 += " and ne_mes = " + intMes1;
                                                strSql2 += " and co_rub = 31 and co_emp = " + intCoEmp;
                                                strSql2 += " and co_tra = " + tbm_tra.getIntCo_tra() + " and ne_per in(2,0); ";
                                       stmAux.executeUpdate(strSql2);
                                       //imprimirEnConsola(strSql2);
                                    }
                                     if (tbm_tra.getStrTx_ForPagDecCuaSue()!=null && !tbm_tra.getStrTx_ForPagDecCuaSue().isEmpty() && tbm_tra.getStrTx_ForPagDecCuaSue().equals("M")) {
                                String strSql3 = "select co_tra, count (fe_dia) as canDia, sum(canDiaP) as canDiaP from (";
                                strSql3 += " select fe_dia, c.co_tra, case when st_jusfal = 'P' then 1 else 0 end as canDiaP";
                                strSql3 += " from ( ";
                                strSql3 += " select fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg ";
                                strSql3 += " from tbm_cabconasitra a ";
                                strSql3 += " inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A' ";
                                strSql3 += " where 	a.co_tra =" +  tbm_tra.getIntCo_tra();
                                strSql3 += " and not (EXTRACT(DOW FROM fe_dia ) in (6,7))";
                                strSql3 += " AND (ho_ent is null AND ho_sal is null)";
                                strSql3 += " and (st_jusfal != 'S' or st_jusfal is null )  )c  ";
                                strSql3 += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')";
                                strSql3 += " inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))";
                                strSql3 += " WHERE  c.fe_dia BETWEEN case ( (EXTRACT (YEAR FROM f.fe_inicon)="+intAno1+") AND (EXTRACT (MONTH FROM f.fe_inicon)="+intMes1+") ) when true then f.fe_inicon ";
                                strSql3 += " else ( select fe_des from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno1+" and ne_mes="+intMes1+" and ne_per=1) end ";
                                strSql3 += " AND (select fe_has from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno1+" and ne_mes="+intMes1+" and ne_per=2)";
                                strSql3 += " UNION   ";
                                strSql3 += " select fe_dia, c.co_tra, case when st_jusfal = 'P' then 1 else 0 end as canDiaP";
                                strSql3 += " from (  ";
                                strSql3 += " select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg  ";
                                strSql3 += " from tbm_cabconasitra a   ";
                                strSql3 += " inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)) ";
                                strSql3 += " where 	a.co_tra ="+  tbm_tra.getIntCo_tra();
                                strSql3 += " and not (EXTRACT(DOW FROM fe_dia ) in (6,7))";
                                strSql3 += " AND (ho_ent is null AND ho_sal is null)";
                                strSql3 += " and (st_jusfal != 'S' or st_jusfal is null ) )c";
                                strSql3 += " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' and f.st_reg='A') ";
                                strSql3 += " left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))     ";
                                strSql3 += " WHERE c.fe_dia BETWEEN case ( (EXTRACT (YEAR FROM f.fe_inicon)="+intAno1+") AND (EXTRACT (MONTH FROM f.fe_inicon)="+intMes1+") ) when true then f.fe_inicon ";
                                strSql3 += " else ( select fe_des from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno1+" and ne_mes="+intMes1+" and ne_per=1) end ";
                                strSql3 += " AND (select fe_has from tbm_feccorrolpag where co_emp=f.co_emp and ne_ani="+intAno1+" and ne_mes="+intMes1+" and ne_per=2)";
                                strSql3 += " ) x";
                                strSql3 += " group by co_tra";
                                rstAux = stmAux2.executeQuery(strSql3);
                                if (rstAux.next())
                                {
                                    intNumDiaTra= rstAux.getInt("canDia");
                                     douValTotDecCua = ((objSueBas.getDouNd_SueBas()/30)/12)*(30-(intNumDiaTra-rstAux.getInt("canDiaP")));
                                }else{
                                      douValTotDecCua = (objSueBas.getDouNd_SueBas())/12;
                                }
                                         String strSql2 = "update tbm_ingegrmentra set nd_valrub = " + douValTotDecCua;
                                                strSql2 += " where ne_ani = " + intAno1;
                                                strSql2 += " and ne_mes = " + intMes1;
                                                strSql2 += " and co_rub = 32 and co_emp = " + intCoEmp;
                                                strSql2 += " and co_tra = " + tbm_tra.getIntCo_tra() + " and ne_per in(2,0) ; ";
                                       stmAux.executeUpdate(strSql2);
                                       //imprimirEnConsola(strSql2);
                                    }
                                }   }
                        }
                    }   
                    }
                rstRes.close();
                if (listTra!=null) {
                rstResTra.close();
                }
                rstRes = null; 
                rstResTra = null; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;       
    }
}