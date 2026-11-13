/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.DatosAM;
import Librerias.ZafUtil.ZafUtil;
import com.tuval.utilities.archivos.ArchivosTuval;
import com.tuval.utilities.mail.MailTuval;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
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
 * Para subir informacion de los registros de marcacion del dia
 * Todos los registros todos.
 * @author Roberto Flores
 */
public class ZafSer08CINew
{

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) throws Exception
    {
        System.out.println("ZafSer08 v0.5");
        //String slash = File.separator;
        //final String strdirMount = slash+"home"+slash+"jflores"+slash+"NetBeansProjects"+slash+"ZafSer08"+slash+"archivos";//+slash;
        //final String strUser="sistemas2";
        //final String strdirMount = slash+"home"+slash+strUser+slash+"NetBeansProjects"+slash+"ZafSer08"+slash+"archivos";//+slash;
        //String strDir = slash+"home"+slash+"jflores"+slash+"NetBeansProjects"+slash+"ZafSer08"+slash+"archivos"+slash+"marcaciones"+slash+"cargar_informacion"+slash;
        //String strDir = slash+"home"+slash+strUser+slash+"NetBeansProjects"+slash+"ZafSer08"+slash+"archivos"+slash+"marcaciones"+slash+"cargar_informacion"+slash;
        //String strDirBk = strdirMount+slash+"respaldo marcaciones"+slash+"cargar_informacion"+slash;
        String slash = "/";
        //final String strdirMount = "D:/tony/ZafSer08/archivos";
		final String strdirMount = "D:/Users/sistemas2/Documents/NetBeansProjects/ZafSer08/archivos";
        String strDir = strdirMount+"/marcaciones"+"/"+"cargar_informacion"+"/";
        String strDirBk = strdirMount+slash+"respaldo marcaciones"+slash+"cargar_informacion"+slash;
        String strFileName="";
        ZafParSis zafParSis = null;
        ZafUtil zafUti = null;
        java.sql.Connection unCon = null;
        java.sql.ResultSet rstRes = null;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.util.Date datFecHoy = null;
        String strSql="";
        String linea="";
        java.util.ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra> listTra = null;
        java.io.BufferedReader fd = null;
        File directorioMarcaciones = null;
        String[] ficherosDirMar = null;
        String strFechaArc="";
        PreparedStatement preSta = null;
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        boolean blnRes=true;
        ResultSet rstResHor = null; //tony
        Statement stmLocHor = null; //tony
        try
        {
            SimpleDateFormat fmt = new SimpleDateFormat("dd_MMM_yyyy hh.mm.ss");
            String strFec2 = fmt.format(new Date());
                        String strRutArcLog="D:\\Users\\sistemas2\\Documents\\NetBeansProjects\\ZafSer08\\logs\\"+strFec2+"CI";
			//String strRutArcLog="D:\\tony\\ZafSer08\\logs\\"+strFec2+"CI";
            ArchivosTuval log = new ArchivosTuval(strRutArcLog);
            log.println("ini.");
            if(booIgualdadArchivos(strDir)){
                if ((unCon == null) || (unCon.isClosed()))
                {
                    zafParSis = new ZafParSis();
                    zafUti = new ZafUtil();
                    datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                    unCon = java.sql.DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
                    if (unCon!=null)
                    {
                        stmLocHor =unCon.createStatement();//tony 
                        unCon.setAutoCommit(false);
                        stmLoc=unCon.createStatement();
                        stmLocAux=unCon.createStatement();
                        Calendar cal = Calendar.getInstance();
                        String strFec=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
                        strSql = "SELECT b.co_emp, b.co_tra, tx_nom, tx_ape, b.fe_inicon, co_hor FROM tbm_tra a";
                        strSql +=" inner join tbm_traemp b on (b.co_tra = a.co_tra)";
                        strSql +=" WHERE b.st_reg like 'A'";
                        strSql +=" and (fe_inicon <= "+zafUti.codificar(strFec);
                        strSql +=" or fe_inicon is null)";
                        strSql +=" order by co_tra";
                        rstRes=stmLoc.executeQuery(strSql);
                        if (rstRes.next())
                        {
                            listTra=new java.util.ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra>();
                            do
                            {
                                Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra tbmTra = new Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra();
                                tbmTra.setIntCo_tra(rstRes.getInt("co_tra"));
                                tbmTra.setStrTx_nom(rstRes.getString("tx_nom"));
                                tbmTra.setStrTx_ape(rstRes.getString("tx_ape"));
                                tbmTra.setIntCo_hor(rstRes.getInt("co_hor"));
                                listTra.add(tbmTra);
                            } while(rstRes.next());
                        }
                        if (listTra!=null)
                        {
                            directorioMarcaciones=new File(strDir);
                            ficherosDirMar = directorioMarcaciones.list();
                            for(int x=0;x<ficherosDirMar.length;x++)
                            {
                                File directorio = new File(strDir);
                                String[] ficheros = directorio.list();
                                for (int i=0;i<ficheros.length;i++)
                                {
                                    java.util.ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM> listDatosAM = new java.util.ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM>();
                                    System.out.println();
                                    fd = new java.io.BufferedReader(new java.io.FileReader(strDir +ficherosDirMar[x]));
                                    strFileName=strDir +ficherosDirMar[x];
                                    System.out.format("Inicializando lectura fichero: " + ficheros[i].toString() + "\n");
                                    if(fd!=null)
                                    {
                                        System.out.format("Procesando registro de marcaciones fichero: " + ficheros[i].toString()+ "\n");
                                        while ((linea = fd.readLine()) != null)
                                        {
                                            String[] temp = linea.split(";");
                                            Librerias.ZafRecHum.ZafRecHumPoj.DatosAM datosAM = new Librerias.ZafRecHum.ZafRecHumPoj.DatosAM();
                                            datosAM.setIntPin(Integer.parseInt(temp[0]));
                                            datosAM.setStrName(temp[1]);
                                            String[] tempFecHor = temp[2].split(" ");
                                            String strFecha=tempFecHor[0].replace("/", "");
                                            String strDD=strFecha.substring(0, 2);
                                            String strMM=strFecha.substring(2, 4);
                                            String strAAAA=strFecha.substring(4, 8);
                                            datosAM.setStrFeDia(strAAAA+"-"+strMM+"-"+strDD);
                                            datosAM.setStrSTime(tempFecHor[1]);
                                            int intReaMacNo = -1;
                                            if (temp[3].compareTo("101")==0)
                                            {
                                                //Biométrico de "California".
                                                intReaMacNo=1;
                                            }
                                            else if (temp[3].compareTo("102")==0)
                                            {
                                                //Biométrico de "Vía Daule".
                                                intReaMacNo=5;
                                            }
                                            else if (temp[3].compareTo("103")==0)
                                            {
                                                //Biométrico de "Inmaconsa".
                                                intReaMacNo=6;
                                            }
                                            else if (temp[3].compareTo("104")==0)
                                            {
                                                //Biométrico de "Quito".
                                                intReaMacNo=2;
                                            }
                                            else if (temp[3].compareTo("105")==0)
                                            {
                                                //Biométrico de "Santo Domingo".
                                                intReaMacNo=4;
                                            }
                                            else if (temp[3].compareTo("106")==0)
                                            {
                                                //Biométrico de "Manta".
                                                intReaMacNo=3;
                                            }
                                            else if (temp[3].compareTo("107")==0)
                                            {
                                                //Biométrico de "Cuenca".
                                                intReaMacNo=7;
                                            }
                                            datosAM.setIntMacNo(intReaMacNo);
                                            listDatosAM.add(datosAM);
                                        }
                                        fd.close();
                                        String strFecArch = ficheros[i].toString().replace("-", "");
                                        String strAAAAArc = strFecArch.substring(4, 8);
                                        String strMMArc = strFecArch.substring(2, 4);
                                        String strDDArc = strFecArch.substring(0, 2);
                                        strFechaArc=strAAAAArc+"-"+strMMArc+"-"+strDDArc;
                                        if (listDatosAM!=null)
                                        {
                                            if(listDatosAM.size()>0)
                                            {
                                                ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM> listMarEmp= null;
                                                for (java.util.Iterator<Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra> itTra=listTra.iterator();itTra.hasNext();)
                                                {
                                                    listMarEmp=new ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM>();
                                                    Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra tbm_tra = itTra.next();
                                                    int intCoTra=tbm_tra.getIntCo_tra();
                                                    //tony co_hor,ho_entasi,ho_salasi, ho_mingraasient
                                                    int intCoHor= tbm_tra.getIntCo_hor();
                                                    String strHoEntAsi = null,strHoSalAsi = null,strHo_MinGraAsiEnt = null,strCoHor=null;
                                                    for (java.util.Iterator<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM> itDatAM=listDatosAM.iterator();itDatAM.hasNext();)
                                                    {
                                                        Librerias.ZafRecHum.ZafRecHumPoj.DatosAM datosAm = itDatAM.next();
                                                        if (tbm_tra.getIntCo_tra()==datosAm.getIntPin())
                                                        {
                                                            listMarEmp.add(datosAm);
                                                            itDatAM.remove();
                                                        }
                                                    }
                                                    String strFeDia=null;
                                                    if (listMarEmp.size()>0) {
                                                        strFeDia=listMarEmp.get(0).getStrFeDia();
                                                    }else{
                                                        strFeDia=strFechaArc;
                                                    }
                                                    
                                                    if (strFeDia==null || strFeDia.compareTo("")==0)
                                                        {
                                                            strFeDia="null";
                                                        }
                                                        else
                                                        {
                                                            strFeDia="'"+strFeDia+"'";
                                                        }
                                                        //Obtener el horario actual
                                                     String   strSqlHor = "";
                                                              strSqlHor += "  select *";
                                                              strSqlHor += "  from tbm_dethortra ";
                                                              strSqlHor += "  where co_hor="+intCoHor ;
                                                              strSqlHor += "  and ne_dia=extract(DOW from timestamp "+strFeDia +" )" ;
                                                        rstResHor = stmLocHor.executeQuery(strSqlHor);
                                                        while(rstResHor.next()){
                                                        strHoEntAsi = rstResHor.getString("ho_ent"); 
                                                        strHoSalAsi = rstResHor.getString("ho_sal");
                                                        strHo_MinGraAsiEnt = rstResHor.getString("ho_mingraent");
                                                         } 
                                                        if (intCoHor==0)
                                                        {
                                                            strCoHor="null";
                                                        }
                                                        else
                                                        {
                                                            strCoHor=String.valueOf(intCoHor);
                                                        }
                                                        if (strHoEntAsi==null || strHoEntAsi.compareTo("")==0)
                                                        {
                                                            strHoEntAsi="null";
                                                        }
                                                        else
                                                        {
                                                            strHoEntAsi="'"+strHoEntAsi+"'";
                                                        }
                                                        if (strHoSalAsi==null || strHoSalAsi.compareTo("")==0)
                                                        {
                                                            strHoSalAsi="null";
                                                        }
                                                        else
                                                        {
                                                            strHoSalAsi="'"+strHoSalAsi+"'";
                                                        }
                                                        if (strHo_MinGraAsiEnt==null || strHo_MinGraAsiEnt.compareTo("")==0)
                                                        {
                                                            strHo_MinGraAsiEnt="null";
                                                        }
                                                        else
                                                        {
                                                            strHo_MinGraAsiEnt="'"+strHo_MinGraAsiEnt+"'";
                                                        }
                                                        //tony
                                                    if (listMarEmp.size()>0)
                                                    {
                                                        /*INSERTAR CABECERA*/
                                                        Librerias.ZafRecHum.ZafRecHumPoj.DatosAM tbm_marEmpHoEnt = listMarEmp.get(0);
                                                        Librerias.ZafRecHum.ZafRecHumPoj.DatosAM tbm_marEmpHoSal = listMarEmp.get(listMarEmp.size()-1);
                                                        int intCotra=listMarEmp.get(0).getIntPin();
                                                        strFeDia=listMarEmp.get(0).getStrFeDia();
                                                        if (strFeDia==null || strFeDia.compareTo("")==0)
                                                        {
                                                            strFeDia="null";
                                                        }
                                                        else
                                                        {
                                                            strFeDia="'"+strFeDia+"'";
                                                        }
                                                        String strHoEnt=tbm_marEmpHoEnt.getStrSTime();
                                                        if (strHoEnt==null || strHoEnt.compareTo("")==0)
                                                        {
                                                            strHoEnt="null";
                                                        }
                                                        else
                                                        {
                                                            strHoEnt="'"+strHoEnt+"'";
                                                        }
                                                        String strHoSal=tbm_marEmpHoSal.getStrSTime();
                                                        if (strHoSal==null || strHoSal.compareTo("")==0)
                                                        {
                                                            strHoSal="null";
                                                        }
                                                        else
                                                        {
                                                            strHoSal="'"+strHoSal+"'";
                                                        }
                                                        String strOfiEnt=String.valueOf(tbm_marEmpHoEnt.getIntMacNo());
                                                        if (strOfiEnt==null || strOfiEnt.compareTo("")==0)
                                                        {
                                                            strOfiEnt="null";
                                                        }
                                                        else
                                                        {
                                                            strOfiEnt="'"+strOfiEnt+"'";
                                                        }
                                                        String strOfiSal=String.valueOf(tbm_marEmpHoSal.getIntMacNo());
                                                        if (strOfiSal==null || strOfiSal.compareTo("")==0)
                                                        {
                                                            strOfiSal="null";
                                                        }
                                                        else
                                                        {
                                                            strOfiSal="'"+strOfiSal+"'";
                                                        }
                                                        if (listMarEmp.size()%2==0)
                                                        {
                                                            //strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent,ho_sal,co_ofimarent, co_ofimarsal) values("+intCotra+","+strFeDia+","+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+")";
                                                            //tony
                                                            strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent,ho_sal,co_ofimarent, co_ofimarsal,co_hor,ho_entasi,ho_salasi,ho_mingraasient) values("+intCotra+","+strFeDia+","+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+","+strCoHor+","+strHoEntAsi+","+strHoSalAsi+","+strHo_MinGraAsiEnt+ ")";
                                                        }
                                                        else
                                                        {
                                                            if(listMarEmp.size()==1)
                                                            {
                                                                String str=strHoEnt.split(":")[0].replace("'", "");
                                                                int intHor=Integer.parseInt(str);
                                                                if(intHor>16)
                                                                {
                                                                    //strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent, ho_sal,co_ofimarent, co_ofimarsal) values("+intCotra+","+strFeDia+","+"null"+","+strHoEnt+","+strOfiEnt+","+strOfiSal+")";
                                                                    //tony
                                                                    strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent, ho_sal,co_ofimarent, co_ofimarsal,co_hor,ho_entasi,ho_salasi,ho_mingraasient) values("+intCotra+","+strFeDia+","+"null"+","+strHoEnt+","+strOfiEnt+","+strOfiSal+","+strCoHor+","+strHoEntAsi+","+strHoSalAsi+","+strHo_MinGraAsiEnt+ ")";
                                                                }
                                                                else
                                                                {
                                                                   // strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent, co_ofimarent) values("+intCotra+","+strFeDia+","+strHoEnt+","+strOfiEnt+")";
                                                                    //tony
                                                                    strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent, co_ofimarent,co_hor,ho_entasi,ho_salasi,ho_mingraasient) values("+intCotra+","+strFeDia+","+strHoEnt+","+strOfiEnt+","+strCoHor+","+strHoEntAsi+","+strHoSalAsi+","+strHo_MinGraAsiEnt+ ")";
                                                                }
                                                            }
                                                            else if(listMarEmp.size()>=2)
                                                            {
                                                                tbm_marEmpHoSal = listMarEmp.get(listMarEmp.size()-1);
                                                                strHoSal=tbm_marEmpHoSal.getStrSTime();
                                                                if (strHoSal==null || strHoSal.compareTo("")==0)
                                                                {
                                                                    strHoSal="null";
                                                                }
                                                                else
                                                                {
                                                                    strHoSal="'"+strHoSal+"'";
                                                                }
                                                                //strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent,ho_sal,co_ofimarent, co_ofimarsal) values("+intCotra+","+strFeDia+","+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+")";
                                                                //tony
                                                                strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent,ho_sal,co_ofimarent, co_ofimarsal,co_hor,ho_entasi,ho_salasi,ho_mingraasient) values("+intCotra+","+strFeDia+","+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+","+strCoHor+","+strHoEntAsi+","+strHoSalAsi+","+strHo_MinGraAsiEnt+ ")";
                                                            }
                                                        }
                                                        System.out.format("query cab: " + strSql+"\n");
                                                        String strSqlComp="";
                                                        strSqlComp+="select * from tbm_cabconasitra";
                                                        strSqlComp+=" where fe_dia = " + zafUti.codificar(strFechaArc);
                                                        strSqlComp+=" and co_tra = "+intCoTra;
                                                        rstRes=stmLocAux.executeQuery(strSqlComp);
                                                        if (!rstRes.next())
                                                        {
                                                            System.out.format("qyery cab: " + strSql+"\n");
                                                            log.println(strSql);
                                                            stmLoc.executeUpdate(strSql);
                                                        }else{/*CAMBIO DE SERVICIO MOTIVO POR REGISTRO DE HORAS EXTRAS*/
                                                            strSql="update tbm_cabconasitra set ho_ent="+strHoEnt+", ho_sal="+strHoSal+", co_ofimarent="+strOfiEnt+", co_ofimarsal="+strOfiSal+" where co_tra="+intCotra+" and fe_dia="+strFeDia+"";
                                                            log.println(strSql);
                                                            int intUpd=stmLoc.executeUpdate(strSql);
                                                            System.out.println("ACTUALIZACION");
                                                            if (intUpd>1){
                                                                throw new Exception("Error en modificacion");
                                                            }
                                                        }/*CAMBIO DE SERVICIO MOTIVO POR REGISTRO DE HORAS EXTRAS*/
                                                        /*INSERTAR DETALLE*/
                                                        int intCOmp=listMarEmp.get(0).getIntPin();
                                                        if (intCOmp==90)
                                                        {
                                                            System.out.println("hola....");
                                                        }
                                                        if (listMarEmp.size()%2==0)
                                                        {
                                                            //Solo considero las marcaciones de empleados que tienen mas de 2 marcadas al día
                                                            //si solo tienen dos eso queda registrado en la cabecera
                                                            if(listMarEmp.size()>2)
                                                            {
                                                                listMarEmp.remove(0);
                                                                listMarEmp.remove(listMarEmp.size()-1);
                                                                int intCoReg=0;
                                                                int xy=0;
                                                                for(int intPosMar=0; intPosMar<listMarEmp.size();intPosMar=intPosMar+2)
                                                                {
                                                                    strFeDia=listMarEmp.get(0).getStrFeDia();
                                                                    if(strFeDia==null || strFeDia.compareTo("")==0)
                                                                    {
                                                                        strFeDia="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strFeDia="'"+strFeDia+"'";
                                                                    }
                                                                    Date dateFeDia=listMarEmp.get(xy).getStrSDate();
                                                                    intCoTra=listMarEmp.get(xy).getIntPin();
                                                                    System.out.println("detalle(1) co_tra: " + intCoTra);
                                                                    strHoSal=listMarEmp.get(xy).getStrSTime();
                                                                    xy++;
                                                                    strHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                    xy++;
                                                                    if (strHoEnt==null || strHoEnt.compareTo("")==0)
                                                                    {
                                                                        strHoEnt="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strHoEnt="'"+strHoEnt+"'";
                                                                    }
                                                                    if (strHoSal==null || strHoSal.compareTo("")==0)
                                                                    {
                                                                        strHoSal="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strHoSal="'"+strHoSal+"'";
                                                                    }
                                                                    strOfiEnt=String.valueOf(tbm_marEmpHoEnt.getIntMacNo());
                                                                    if (strOfiEnt==null || strOfiEnt.compareTo("")==0)
                                                                    {
                                                                        strOfiEnt="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strOfiEnt="'"+strOfiEnt+"'";
                                                                    }
                                                                    strOfiSal=String.valueOf(tbm_marEmpHoSal.getIntMacNo());
                                                                    if (strOfiSal==null || strOfiSal.compareTo("")==0)
                                                                    {
                                                                        strOfiSal="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strOfiSal="'"+strOfiSal+"'";
                                                                    }
                                                                    intCoReg=intCoReg+1;
                                                                    strSql = "insert into tbm_detConAsiTra(fe_dia,co_tra,co_reg,ho_ent,ho_sal, co_ofimarent, co_ofimarsal) "
                                                                    + "values ("+strFeDia+","+intCoTra+","+intCoReg+" ,"+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+" )";
                                                                    System.out.format("query det: " + strSql+"\n");
                                                                    log.println(strSql);
                                                                    stmLoc.executeUpdate(strSql);
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            //Caso donde hay marcaciones impares... el empleado olvido marcar...
                                                            if(listMarEmp.size()>2)
                                                            {
                                                                listMarEmp.remove(0);
                                                                listMarEmp.remove(listMarEmp.size()-1);
                                                                int intCoReg=0;
                                                                int xy=0;
                                                                for (int intPosMar=0; intPosMar<listMarEmp.size();intPosMar=intPosMar+2)
                                                                {
                                                                    strFeDia=listMarEmp.get(xy).getStrFeDia();
                                                                    if(strFeDia==null || strFeDia.compareTo("")==0)
                                                                    {
                                                                        strFeDia="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strFeDia="'"+strFeDia+"'";
                                                                    }
                                                                    Date dateFeDia=listMarEmp.get(xy).getStrSDate();
                                                                    intCoTra=listMarEmp.get(xy).getIntPin();
                                                                    System.out.println("detalle(2) co_tra: " + intCoTra);
                                                                    strHoSal=listMarEmp.get(xy).getStrSTime();
                                                                    xy++;
                                                                    strHoEnt=null;
                                                                    if (xy<listMarEmp.size())
                                                                    {
                                                                        Object objHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                        if (objHoEnt!=null)
                                                                        {
                                                                            strHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                        }
                                                                    }
                                                                    xy++;
                                                                    if (strHoEnt==null || strHoEnt.compareTo("")==0)
                                                                    {
                                                                        strHoEnt="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strHoEnt="'"+strHoEnt+"'";
                                                                    }
                                                                    if (strHoSal==null || strHoSal.compareTo("")==0)
                                                                    {
                                                                        strHoSal="null";
                                                                    }
                                                                    else
                                                                    {
                                                                        strHoSal="'"+strHoSal+"'";
                                                                    }
                                                                    intCoReg=intCoReg+1;
                                                                    strSql = "insert into tbm_detConAsiTra(fe_dia,co_tra,co_reg,ho_ent,ho_sal) "
                                                                    + "values ("+strFeDia+","+intCoTra+","+intCoReg+" ,"+strHoEnt+","+strHoSal+" )";
                                                                    System.out.format("query det: " + strSql+"\n");
                                                                    log.println(strSql);
                                                                    stmLoc.executeUpdate(strSql);
                                                                }
                                                            }
                                                            else
                                                            {
                                                                //Caso donde hay marcaciones impares... el empleado olvido marcar...
                                                                if (listMarEmp.size()>2)
                                                                {
                                                                    listMarEmp.remove(0);
                                                                    listMarEmp.remove(listMarEmp.size()-1);
                                                                    int intCoReg=0;
                                                                    int xy=0;
                                                                    for (int intPosMar=0; intPosMar<listMarEmp.size();intPosMar=intPosMar+2)
                                                                    {
                                                                        strFeDia=listMarEmp.get(xy).getStrFeDia();
                                                                        if (strFeDia==null || strFeDia.compareTo("")==0)
                                                                        {
                                                                            strFeDia="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strFeDia="'"+strFeDia+"'";
                                                                        }
                                                                        Date dateFeDia=listMarEmp.get(xy).getStrSDate();
                                                                        intCoTra=listMarEmp.get(xy).getIntPin();
                                                                        System.out.println("detalle(3) co_tra: " + intCoTra);
                                                                        strHoSal=listMarEmp.get(xy).getStrSTime();
                                                                        strOfiSal=String.valueOf(listMarEmp.get(xy).getIntMacNo());
                                                                        xy++;
                                                                        strHoEnt=null;
                                                                        Object objHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                        if (objHoEnt!=null)
                                                                        {
                                                                            strHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                            strOfiEnt=String.valueOf(listMarEmp.get(xy).getIntMacNo());
                                                                        }
                                                                        xy++;
                                                                        if (strHoEnt==null || strHoEnt.compareTo("")==0)
                                                                        {
                                                                            strHoEnt="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strHoEnt="'"+strHoEnt+"'";
                                                                        }
                                                                        if (strHoSal==null || strHoSal.compareTo("")==0)
                                                                        {
                                                                            strHoSal="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strHoSal="'"+strHoSal+"'";
                                                                        }
                                                                        if (strOfiEnt==null || strOfiEnt.compareTo("")==0)
                                                                        {
                                                                            strOfiEnt="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strOfiEnt="'"+strOfiEnt+"'";
                                                                        }
                                                                        if (strOfiSal==null || strOfiSal.compareTo("")==0)
                                                                        {
                                                                            strOfiSal="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strOfiSal="'"+strOfiSal+"'";
                                                                        }
                                                                        intCoReg=intCoReg+1;
                                                                        strSql = "insert into tbm_detConAsiTra(fe_dia,co_tra,co_reg,ho_ent,ho_sal,co_ofimarent,co_ofimarsal) "
                                                                        + "values ("+strFeDia+","+intCoTra+","+intCoReg+" ,"+strHoEnt+","+strHoSal+","+strOfiEnt+","+strOfiSal+" )";
                                                                        System.out.format("query det: " + strSql+"\n");
                                                                        log.println(strSql);
                                                                        stmLoc.executeUpdate(strSql);
                                                                    }
                                                                }
                                                                else if(listMarEmp.size()==1)
                                                                {
                                                                    int intCoReg=0;
                                                                    int xy=0;
                                                                    for(int intPosMar=0; intPosMar<listMarEmp.size();intPosMar=intPosMar+1)
                                                                    {
                                                                        strFeDia=listMarEmp.get(xy).getStrFeDia();
                                                                        if (strFeDia==null || strFeDia.compareTo("")==0)
                                                                        {
                                                                            strFeDia="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strFeDia="'"+strFeDia+"'";
                                                                        }
                                                                        Date dateFeDia=listMarEmp.get(xy).getStrSDate();
                                                                        intCoTra=listMarEmp.get(xy).getIntPin();
                                                                        System.out.println("detalle(4) co_tra: " + intCoTra);
                                                                        strHoSal=listMarEmp.get(xy).getStrSTime();
                                                                        xy++;
                                                                        strHoEnt=null;
                                                                        if (xy<listMarEmp.size())
                                                                        {
                                                                            Object objHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                            if (objHoEnt!=null)
                                                                            {
                                                                                strHoEnt=listMarEmp.get(xy).getStrSTime();
                                                                            }
                                                                        }
                                                                        xy++;
                                                                        if (strHoEnt==null || strHoEnt.compareTo("")==0)
                                                                        {
                                                                            strHoEnt="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strHoEnt="'"+strHoEnt+"'";
                                                                        }
                                                                        if (strHoSal==null || strHoSal.compareTo("")==0)
                                                                        {
                                                                            strHoSal="null";
                                                                        }
                                                                        else
                                                                        {
                                                                            strHoSal="'"+strHoSal+"'";
                                                                        }
                                                                        intCoReg=intCoReg+1;
                                                                        strSql = "insert into tbm_detConAsiTra(fe_dia,co_tra,co_reg,ho_ent,ho_sal) "
                                                                        + "values ("+strFeDia+","+intCoTra+","+intCoReg+" ,"+strHoEnt+" ,"+strHoSal+" )";
                                                                        System.out.format("query det: " + strSql+"\n");
                                                                        log.println(strSql);
                                                                        stmLoc.executeUpdate(strSql);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        //Verifica si es domingo para evitar agregar marcaciones vacias para ese dia. tony
                                                        strSql="";
                                                        strSql+=" select extract(dow from '"+strFechaArc+"'::date) as dow \n";
                                                        rstRes = stmLoc.executeQuery(strSql);
                                                        boolean blnIsDomHorExt=true;
                                                        if (rstRes.next())
                                                        {
                                                            if (rstRes.getInt("dow")==0)
                                                            {
                                                                blnIsDomHorExt=false;
                                                            }
                                                        }
                                                        if (blnIsDomHorExt) {
                                                            strSql="";
                                                            strSql+="select * from tbm_cabconasitra";
                                                            strSql+=" where fe_dia = " + zafUti.codificar(strFechaArc);
                                                            strSql+=" and co_tra = "+intCoTra;
                                                            rstRes=stmLocAux.executeQuery(strSql);
                                                            if (!rstRes.next())
                                                            {
                                                                strSql="insert into tbm_cabconasitra(co_tra, fe_dia, ho_ent,ho_sal,co_hor,ho_entasi,ho_salasi,ho_mingraasient) values("+intCoTra+",'"+strFechaArc+"',null,null"+","+strCoHor+","+strHoEntAsi+","+strHoSalAsi+","+strHo_MinGraAsiEnt+")";
                                                                log.println(strSql);
                                                                stmLoc.executeUpdate(strSql);
                                                            }
                                                        }
                                                    }
                                                }
                                                blnRes=true;
                                            }
                                        }
                                        else
                                        {
                                            System.out.format("NO HAY INFORMACION EN EL FICHERO DE MARCACIONES QUE SE ESTA PROCESANDO...");
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            System.out.format("NO HAY EMPLEADOS PARA PROCESAR...");
                            System.exit(0);
                        }
                    }
                }

                if (blnRes)
                {
                    MailTuval mail = new MailTuval();
                    if (setearAlimentacion(unCon,log,mail,zafUti, strFechaArc))
                    {
                        File archivo=new File(strFileName);
                        File dir=new File(strDirBk);
                        boolean semovio=false;
                        semovio = archivo.renameTo(new File(dir, archivo.getName()));
                        semovio=true;
                        if (!(semovio))
                        {
                            blnRes=false;
                            unCon.rollback();
                        }
                        else
                        {
                            unCon.commit();
                            blnRes=true;
                        }
                    }
                }

            } else{
                    System.out.format("NO HAY CONSITENCIA EN LOS ARCHIVOS...");

                    try{

                        String server = "mail.tuvalsa.com";
                        String userName = "zafiro";
                        String password = "#tuv*sis28/10=";
                        String fromAddres = "zafiro@tuvalsa.com";
                        String toAddres = "sistemas9@tuvalsa.com";

                        String cc = "";
                        String bcc = "";
                        boolean htmlFormat = false;
                        String subject = "SafSer08";
                        String body = "NO HAY CONSITENCIA EN LOS ARCHIVOS..., ";


                        sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,htmlFormat, subject, body);


                    }catch(Throwable ignore){
                        ignore.printStackTrace();
                    }
                    System.exit(0);
                }

        }
        catch(Exception e)
        {
            unCon.rollback();
            blnRes=false;
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(unCon!=null)
                {
                    unCon.close();
                }
            }
            catch(Throwable ignore)
            {

            }
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
            catch(Throwable ignore)
            {

            }
            try
            {
                rstRes.close();
            }
            catch (Throwable ignore)
            {

            }
            try
            {
                preSta.close();
            }
            catch (Throwable ignore)
            {

            }
            try
            {
                unCon.close();
            }
            catch (Throwable ignore)
            {

            }
        }
        if (blnRes)
        {
            System.out.format("PROCESO EJECUTADO CORRECTAMENTE...");
        }
        else
        {
            System.out.format("PROCESO EJECUTADO INCORRECTAMENTE...");
        }
    }

    private static boolean setearAlimentacion(Connection unCon, ArchivosTuval log, MailTuval mail, ZafUtil zafUtil, String strFechaArc)
    {
        boolean blnRes=true;
        ResultSet rstRes = null;
        Statement stmLoc=null;
        Statement stmLocAux=null;
        try
        {
            Calendar calendario = new GregorianCalendar();
            stmLoc=unCon.createStatement();
            stmLocAux=unCon.createStatement();
            String strSql="";
            strSql+=" select extract(dow from '"+strFechaArc+"'::date) as dow \n";
            System.out.println("SQL Fecha: " + strSql);
            rstRes = stmLoc.executeQuery(strSql);
            boolean blnIsDiaPagAlm=true;
            if (rstRes.next())
            {
                if (rstRes.getInt("dow")==6 || rstRes.getInt("dow")==0)
                {
                    blnIsDiaPagAlm=false;
                }
            }
            if (blnIsDiaPagAlm)
            {
                strSql="";
                strSql+=    " SELECT a.co_emp, a.co_tra, a.co_dep, a.co_ofi, c.tx_nom as empresa , (b.tx_ape || ' ' || b.tx_nom) as empleado, a.st_recAlm, a.nd_valAlm, a.tx_forRecAlm , e.ho_ent \n" +
                " FROM tbm_traemp a \n" +
                " INNER JOIN tbm_tra b on (b.co_tra=a.co_tra) \n" +
                " INNER JOIN tbm_emp c on (c.co_emp=a.co_emp) \n" +
                " LEFT OUTER JOIN tbm_ofi d on (d.co_ofi=a.co_ofi) \n" +
                " LEFT OUTER JOIN tbm_cabconasitra e on (e.co_tra=a.co_tra and e.fe_dia="+zafUtil.codificar(strFechaArc)+") \n"+
                " WHERE a.st_reg='A'  \n" +
                " AND e.st_jusfal is null \n" +
                " ORDER BY a.co_emp, empleado";
                System.out.println("strSqlTraEmp-TbmCabConAsiTraAlm : " + strSql);
                rstRes = stmLoc.executeQuery(strSql);
                while (rstRes.next())
                {
                    log.println("EMPLEADO ALIMENTACION: " +rstRes.getString("empleado") );
                    System.out.println("EMPLEADO ALIMENTACION: " +rstRes.getString("empleado") );
                    System.out.println(rstRes.getString("ho_ent"));
                    if (rstRes.getString("ho_ent")!=null)
                    {
                        log.println("PROCESANDO ALIMENTACION DEL EMPLEADO: " +rstRes.getString("empleado") );
                        System.out.println("PROCESANDO ALIMENTACION DEL EMPLEADO: " +rstRes.getString("empleado") );
                        strSql="";
                        strSql+="UPDATE tbm_cabconasitra SET st_recAlm = "+zafUtil.codificar(rstRes.getString("st_recAlm"))+" , tx_forRecAlm = "+zafUtil.codificar(rstRes.getString("tx_forRecAlm"))+" , nd_valAlm = "+rstRes.getString("nd_valAlm")+" \n" +
                        "WHERE co_tra = "+rstRes.getString("co_tra")+"";
                        System.out.println("updateTbmCabConAsiTraAlm: " + strSql);
                        log.println(strSql);
                        stmLocAux.executeUpdate(strSql);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            try{log.println("Archivo LOG Ocurrio un error ");}catch(Throwable ignore){}
            try{log.println(e.getMessage());}catch(Throwable ignore){}
            try{mail.enviar("ZafSer08", "Archivo LOG Ocurrio un error: " + e.getStackTrace());}catch(Throwable ignore){}
            e.printStackTrace();
        }
        finally
        {
            try{rstRes.close();rstRes=null;}catch(Throwable ignore){}
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{stmLocAux.close();stmLocAux=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }

    private boolean procesoDescartesMarcacionesInterval4Minutos(ArrayList<Librerias.ZafRecHum.ZafRecHumPoj.DatosAM> listMarEmp)
    {
        boolean blnRes=true;
        return blnRes;
    }



    /**
     * Metodo que cuenta las lineas del archivo pasado por parametro.
     * @param nombreArc nombre del archivo a ser leido.
     * @return cantidad de lineas del archivo.
     * @throws IOException lanza excepcion.
     */
    private static int contarLinArc(String nombreArc) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(nombreArc));
        try {
            byte[] c = new byte[1024];
            int contador = 0;
            int intCarLee = 0;
            boolean empty = true;
            while ((intCarLee = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < intCarLee; ++i) {
                    if (c[i] == '\n') {
                        ++contador;
                    }
                }
            }
            return (contador == 0 && !empty) ? 1 : contador;
        } finally {
            is.close();
        }
   }

    /**
     * Metodo para obtener archivos del directorio donde se encuentra las marcaciones
     * Estos archivos se verificaran para conocer el numero de lineas que poseen.
     * @param ruta Ruta del directorio de archivos.
     * @return String[] con los archivos a verificar.
     */
    private static String[] obtenerArcMarc(String ruta){
        String[] arrArchivos=new String[10];
        try{
            File dirArchivos=new File(ruta);
            arrArchivos=dirArchivos.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return arrArchivos;
    }


    /**
     * Metodo que verifica igualdad de archivos
     * @param ruta path donde se encuentran los archivos.
     * @return boolean indica si hay igualdad.
     */
    private static boolean booIgualdadArchivos(String ruta){

        boolean booIgu=true;
        String [] lisArc=obtenerArcMarc(ruta);
        Integer[] cantLinAr=new Integer[lisArc.length];
        try{
            for(int i=0;i<lisArc.length;i++){
                System.out.println( lisArc[i]);
                cantLinAr[i]=contarLinArc(ruta+"\\"+lisArc[i]);
                if(i>0){
                    if(!(cantLinAr[i].equals(cantLinAr[i-1]))){
                        booIgu=false;
                        break;
                    }
                }
            }
            if(booIgu && lisArc.length > 1)//Borrar Archivos de mas, dejando uno solo para ser procesado.
            {
                int cant=lisArc.length;
                for(int i=cant-1 ;i > 0; i--){
                    File fo=new File(ruta+"\\"+lisArc[i]);
                    fo.delete();
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booIgu;
    }


/**
    * Se usa para enviar errores a los destinatarios.
    * @param server ip o url del servidor de correos.
    * @param userName nombre de usuario.
    * @param password password del usuario que envia el correo.
    * @param fromAddress desde donde se envia el correo.
    * @param toAddress hacia donde se envia el correo (Destinatario).
    * @param cc copia
    * @param bcc
    * @param htmlFormat
    * @param subject tema
    * @param body cuerpo del mensaje
    * @return boolean si se envio o no se envio.
    */
   public static boolean sendMailTuv(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String body)
   {
    boolean blnRes=false;
        try{

           Properties props = System.getProperties();
           props.put("mail.smtp.host", server);

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to address
            message.addRecipient(Message.RecipientType.TO,
            new InternetAddress(toAddress));

            // Set the subject
            message.setSubject(subject);


            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            //cuerpoCorreo.setText("Estado de Cuenta ...");
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);
           // multipart.addBodyPart(adjuntoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            // Send message
            Transport.send(message);

           blnRes=true;

       }catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }

      return blnRes;
    }



    static class MyAuthenticator extends Authenticator {
        PasswordAuthentication l = new PasswordAuthentication("zafiro", "#tuv*sis28/10=");

        protected PasswordAuthentication getPasswordAuthentication() {
            return l;
        }
    }



}
