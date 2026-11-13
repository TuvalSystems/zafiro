/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso;

import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.Archivos;
import Librerias.ZafUtil.UtilitiesException;
import Librerias.ZafUtil.ZafUtil;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Tony Sanginez
 */
public class ZafSer18 {

    private final String strVersion = "ZafSer18 v0.2";
    private ZafCorEle objCorEle;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strCorEle = "sistemas4@tuvalsa.com";//;rrhh@tuvalsa.com;sistemas2@tuvalsa.com
    private String strCorEleUnDia = "sistemas4@tuvalsa.com";
    private String strTit = "Notificación de Solicitudes de Horas Extra Pendientes Por Autorizar";
    private String strDirectorio;
    Archivos log;

    /**
     * @param args the command line arguments
     */
    public ZafSer18() {

    }
    public void iniciar() throws SQLException {
        objParSis = new ZafParSis();
        objUti = new ZafUtil();
        objCorEle = new ZafCorEle(objParSis);

        strDirectorio = new ZafUtil().getDirectorioSistema();
        String strFec2 = new SimpleDateFormat("ddMMyyyy hh.mm.ss").format(new Date());
        System.out.println(strDirectorio + File.separatorChar + "logs" + File.separatorChar + "ZafSer18." + strFec2);
        log = new Archivos(strDirectorio + File.separatorChar + "logs" + File.separatorChar + "ZafSer18." + strFec2);

        imprimirEnConsola("************************************************");
        imprimirEnConsola(getClass() + " Version:" + this.strVersion);
        imprimirEnConsola("Hora de ejecución del proceso: " + new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date()));
        imprimirEnConsola("************************************************");
        try {
            java.util.Date objFec = new java.util.Date();

            /*  ENVIO DE CORREOS POR NOTIFICACION */
            if (verificaDiaPeriodos(objFec)) {
                if (iniciarProcesoNotificaiones(objFec)) {
                    System.out.println("...Termina notificaciones...");
                } else {
                    objCorEle.enviarCorreoMasivo("sistemas4@tuvalsa.com", strTit, "ERROR: NOTIFICACION de Horas Extras por autorizar ");
                }
            }
            objFec = null;
        } catch (Exception e) {
            System.out.println("ERROR " + e.toString());
        }
    }

    /**
     * Imprime el mensaje en consola y tambien en un log
     *
     * @param mensaje
     */
    public void imprimirEnConsola(String mensaje) {
        System.out.println(mensaje);
        try {
            log.println(mensaje);
        } catch (UtilitiesException e) {
            e.printStackTrace();
        }
    }
    /**
     * LLama a los metodos de notificacione del sistema de reserva
     *
     * @return
     */
    private boolean iniciarProcesoNotificaiones(Date objFec) {
        boolean blnRes = false;
        try {
            System.out.println("iniciarProcesoNotificaiones.... ");
            obtieneCorreos();
            objCorEle.enviarCorreoMasivo(strCorEle, strTit, " <br> Usted tiene solicitudes de horas extras por autorizar. Esta notificacion es previa al corte de periodo.  <br>  Fecha=" + objUti.getFechaServidor(objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()) + " <br><br>                                                     Este correo es informativo, favor no responder a esta DIRECCION de correo ya que no se encuentra habilitada para recibir mensajes.\n"
                    + "<br>Si requiere mayor INFORMACION sobre el contenido de este mensaje llame a nuestras oficinas que con mucho gusto lo atenderemos.");
            if (verificaDiaPeriodosExcepciones(objFec)) {
                objCorEle.enviarCorreoMasivo(strCorEleUnDia, strTit, " <br> Usted tiene solicitudes de horas extras por autorizar. Esta notificacion es previa al corte de periodo.  <br>  Fecha=" + objUti.getFechaServidor(objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()) + " <br><br>                                                     Este correo es informativo, favor no responder a esta DIRECCION de correo ya que no se encuentra habilitada para recibir mensajes.\n"
                        + "<br>Si requiere mayor INFORMACION sobre el contenido de este mensaje llame a nuestras oficinas que con mucho gusto lo atenderemos.");
            }
            blnRes = true;
            System.out.println("#Fin InciciarProceso... ");
            strCorEle = "sistemas4@tuvalsa.com";
            strCorEleUnDia = "sistemas4@tuvalsa.com";
        } catch (Exception Evt) {
            blnRes = false;
            System.out.println("Error 1 " + Evt);
        }
        return blnRes;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        ZafSer18 ZafSer18 = new ZafSer18();
        try {
            ZafSer18.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtieneCorreos() {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();

            strSql = "";
            strSql += "   SELECT DISTINCT b.tx_corele, b.co_usr, b.tx_usr, b.tx_nom--, d.st_aut, e.ho_autDes, e.ho_autHas, d.tx_obsAut a.*,\n"
                    + " FROM tbm_usrAutHorSupExtDep  a\n"
                    + " INNER JOIN tbm_usr b ON (b.co_usr=a.co_usr) \n"
                    + " INNER JOIN tbm_dep c ON (c.co_dep=a.co_dep) \n"
                    + " LEFT OUTER JOIN  tbm_cabSolHorSupExt e ON (e.co_dep=c.co_Dep) \n"
                    + " LEFT OUTER JOIN tbm_autSolHorSupExt  d ON (e.co_emp=d.co_Emp AND e.co_loc=d.co_loc AND e.co_tipDoc=d.co_tipDoc AND e.co_doc=d.co_doc ) \n"
                    + " WHERE e.co_dep in (SELECT a1.co_dep\n"
                    + "from tbm_cabsolhorsupext a1 \n"
                    + "INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc \n"
                    + "inner join tbm_motHorSupExt b on (a1.co_mot = b.co_mot) \n"
                    + "inner join tbm_dep c on (a1.co_dep=c.co_dep) \n"
                    + "inner join tbm_emp d on (d.co_emp=a1.co_emp) \n"
                    + " where a1.st_reg like 'A'  AND st_aut IN ('P','A') \n"
                    + ") AND a.tx_tipHorAut='S' AND e.st_aut!='T' ";

            rstLoc = stmLoc.executeQuery(strSql);
            //System.out.println(strSql);
            while (rstLoc.next()) {
                if (rstLoc.getString("tx_corele") != "") {
                    if (rstLoc.getInt("co_usr") == 90
                            || rstLoc.getInt("co_usr") == 122
                            || rstLoc.getInt("co_usr") == 246
                            || rstLoc.getInt("co_usr") == 24) {
                        strCorEleUnDia += ";" + rstLoc.getString("tx_corele");
                    } else {
                        strCorEle += ";" + rstLoc.getString("tx_corele");
                    }
                }
            }

            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private boolean verificaDiaPeriodos(Date datFecHoy) {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes = false;
        String strSql;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(datFecHoy);
            Calendar calendario = new GregorianCalendar();
            int intMes = calendario.get(Calendar.MONTH) + 1;
            int intAno = calendario.get(Calendar.YEAR);
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();

            strSql = "";
            strSql += "   select distinct fe_dia\n"
                    + " from tbm_callab \n"
                    + " where st_dialab='S' and fe_dia between (select distinct fe_des\n"
                    + "	from tbm_feccorrolpag as a1\n"
                    + "	where ne_ani=" + intAno
                    + "	and ne_mes=" + intMes
                    + "	and ne_per=2)  and(select distinct fe_has\n"
                    + "	from tbm_feccorrolpag as a1\n"
                    + "	where ne_ani=" + intAno
                    + "	and ne_mes=" + intMes
                    + "	and ne_per=2) \n"
                    + "	order by fe_dia desc\n"
                    + "	limit 3 offset 1 ";

            rstLoc = stmLoc.executeQuery(strSql);
            while (rstLoc.next()) {
                if (str.compareTo(rstLoc.getString("fe_dia")) == 0) {
                    blnRes = true;
                }
            }
            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return blnRes;
    }

    private boolean verificaDiaPeriodosExcepciones(Date datFecHoy) {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes = false;
        String strSql;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(datFecHoy);
            Calendar calendario = new GregorianCalendar();
            int intMes = calendario.get(Calendar.MONTH) + 1;
            int intAno = calendario.get(Calendar.YEAR);
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();

            strSql = "";
            strSql += "   select distinct fe_dia\n"
                    + " from tbm_callab \n"
                    + " where st_dialab='S' and fe_dia between (select distinct fe_des\n"
                    + "	from tbm_feccorrolpag as a1\n"
                    + "	where ne_ani=" + intAno
                    + "	and ne_mes=" + intMes
                    + "	and ne_per=2)  and(select distinct fe_has\n"
                    + "	from tbm_feccorrolpag as a1\n"
                    + "	where ne_ani=" + intAno
                    + "	and ne_mes=" + intMes
                    + "	and ne_per=2) \n"
                    + "	order by fe_dia desc\n"
                    + "	limit 1 offset 1 ";

            rstLoc = stmLoc.executeQuery(strSql);
            while (rstLoc.next()) {
                if (str.compareTo(rstLoc.getString("fe_dia")) == 0) {
                    blnRes = true;
                }
            }
            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return blnRes;
    }

    
}
