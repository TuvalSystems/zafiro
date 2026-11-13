
package Proceso;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;
import java.util.StringTokenizer;
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
 * @author jayapata
 */
public class procesoClass
{

   private ZafParSis objParSis;
   private ZafUtil objUti;

   IniciarProceso pantallaPrincipal;
   public String strTipPerCli="";

   String strNomArchHis="";
   
   String strCorEleTo="sistemas6@tuvalsa.com";

    private int intCodLocGrp=1;
    private int intCodCfgCorEleCfgGen=28;
    
    private String server="", userName="", password="", fromAddres="", puerto="", toAddres="";

   int intarreglonum[] = new int[10];
   int intarreglodia[] = new int[10];
   int intarreglonumCom[] = new int[10];
   int intarreglodiaCom[] = new int[10];
   int intCanArr[]= new int[1];
   String strarreglosop[] = new String[10];

   double dblSubTotFacVen=0;
   double dblValTotDesFacVen=0;

   double dblSubTotFacCom=0;
   double dblSubTotDevCom=0;
   double dblSubTotDevVen=0;
   double dblValTotDesDevVen=0;

   double dblValFacVen=0;
   double dblValFacCom=0;

   StringBuffer stbinsFacVen;
   StringBuffer stbinsFacCom;
   StringBuffer stbinsUniReg;
   StringBuffer stbinsRelVenCom;
   StringBuffer stbinsDevVen;
   StringBuffer stbinsRelVenVen;


   StringBuffer stbinsDevCom;

    public procesoClass(Librerias.ZafParSis.ZafParSis obj ) {
      try{
        this.objParSis = obj;
        objUti = new ZafUtil();
       }catch(Exception e){  mensaje(" procesoClass "+ e.toString());    }
    }


   public procesoClass(Librerias.ZafParSis.ZafParSis obj, IniciarProceso pantallaPrin, String strNomArchHis ) {
      try{
        this.objParSis = obj;
        objUti = new ZafUtil();
        this.pantallaPrincipal = pantallaPrin;
        this.strNomArchHis = strNomArchHis;
       }catch(Exception e){   mensaje(" procesoClass "+ e.toString());   }
    }


    public boolean _CalculoPagoVen(java.sql.Connection conPag, int intCodEmp ){
     boolean blnRes=false;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     String strSql="",strSql01="";
     try{
     if(conPag!=null){

        stmLoc=conPag.createStatement();

        strSql="SELECT A1.ne_numPag, A2.ne_diaCre , A2.st_sop " +
        " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
        " Where A1.co_forPag = 3 "+//  forma de pago contado
        " and A2.co_forPag = A1.co_forPag and A1.co_emp = " +intCodEmp+ // Consultando en la empresa en la ke se esta trabajando
        " and A2.co_emp = A1.co_emp order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

        strSql01="SELECT count(A2.ne_diaCre) as c " +
        " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
        " Where A1.co_forPag = 3 "+  // forma de pago contado
        " and A2.co_forPag = A1.co_forPag  and A1.co_emp = " +intCodEmp + // Consultando en la empresa en la ke se esta trabajando
        " and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

    //    System.out.println("cocenco  1 ->  "+strSql01 );

        rstLoc=stmLoc.executeQuery(strSql01);
        if(rstLoc.next()) intCanArr[0] = rstLoc.getInt(1);
        rstLoc.close();
        rstLoc=null;

        rstLoc=stmLoc.executeQuery(strSql);
        int x=0;
        while(rstLoc.next()){
          intarreglodia[x]=rstLoc.getInt(2);
          intarreglonum[x]=rstLoc.getInt(1);
          strarreglosop[x]=rstLoc.getString("st_sop");
          x++;
        }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
      blnRes=true;
     }}catch(Exception e) { blnRes=false;  mensaje("_CalculoPagoVen "+ e.toString());    }
      return blnRes;
    }

    public boolean _calculaPagosVen(java.sql.Connection conn,  double dblSub, double dlbIva, double dlbTot,  int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strCodTipPerCli, java.util.Date datFecDoc ){
 boolean blnRes=false;
 int intVal =  intCanArr[0];
 int intsizearre = intarreglodia.length;
 int intCodMotDoc=0;
 int intCodTipPerEmp=0;
 intVal= intsizearre - (intsizearre-intVal);
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 int intFilUltPag = 0;
 double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
 String strSql="";
 int intNumDec=2;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        java.util.Calendar calObj = java.util.Calendar.getInstance();
        calObj.setTime(datFecDoc);

        String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
        calObj.get(java.util.Calendar.MONTH)+1     ,
        calObj.get(java.util.Calendar.YEAR)  );


      java.util.Calendar objFec = java.util.Calendar.getInstance();
      int fecDoc [] = pantallaPrincipal._getFecha(strFechaCal);  // txtFecDoc.getFecha(txtFecDoc.getText());
      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
      }

       Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker("d/m/y");


       javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel());
        modTbl.setRowCount(0);

   dblBaseDePagos = dlbTot;
   if(dblBaseDePagos>0){

    strSql="SELECT co_mot FROM tbm_motdoc WHERE co_emp="+intCodEmp+" AND tx_tipmot in ('S') ";
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()) intCodMotDoc = rstLoc.getInt(1);
    rstLoc.close();
    rstLoc=null;

    strSql="SELECT b.co_tipper,  b.tx_descor FROM tbm_emp AS a "+
    " LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper) WHERE a.co_emp="+intCodEmp;
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()) intCodTipPerEmp = rstLoc.getInt("co_tipper");
    rstLoc.close();
    rstLoc=null;

    strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta"+
    " FROM tbm_polret AS polret left OUTER JOIN tbm_motdoc AS mot ON (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" +
    " WHERE polret.co_emp = "+intCodEmp+" and co_mot = "+intCodMotDoc+" and co_sujret="+intCodTipPerEmp+" and co_ageret  = "+strCodTipPerCli+" "+
    " AND polret.st_reg='A'   AND  '"+objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd")+"' BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel()).addRow(new java.util.Vector());
      intFilUltPag = pantallaPrincipal.tblCotForPag.getRowCount()-1;
      pantallaPrincipal.tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
      pantallaPrincipal.tblCotForPag.setValueAt(strFechaCal, intFilUltPag, 2);

      if(rstLoc.getString("tx_aplret").equals("S")){
        dblRetFue =dblSub;
        dblRetFue = objUti.redondeo( (dblRetFue  * ( rstLoc.getDouble("nd_porret")/100)),2 );
        pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("nd_porret"), intFilUltPag, 3);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
      }
      if(rstLoc.getString("tx_aplret").equals("I")){
        dblRetIva = objUti.redondeo( (dlbIva * ( rstLoc.getDouble("nd_porret")/100)) ,2 );
        pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("nd_porret") , intFilUltPag, 3);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
      }
      pantallaPrincipal.tblCotForPag.setValueAt(rstLoc.getString("co_tipret") , intFilUltPag, 6);
    }
    rstLoc.close();
    rstLoc=null;
   }
  //*************************************************************************************///
    double dblPago=0.00;
    double dblPagos=0.00;
    int i=0;

    double dblRete=0;
    dblRete = dblRetFueFle+dblRetFue+dblRetIva;
    dblBaseDePagos = objUti.redondear( ( dlbTot - dblRete ), 2);

    for(i=0; i < intVal; i++) {
      Calendar objFecPagActual = Calendar.getInstance();
      objFecPagActual.setTime(objFec.getTime());

      int diaCre = intarreglodia[i];
      int numPag = intarreglonum[i];
      String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
      if(diaCre!=0)
        objFecPagActual.add(java.util.Calendar.DATE, diaCre);

        dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

         System.out.print("-->"+ dtePckPag.getText() );

        ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblCotForPag.getModel()).addRow(new java.util.Vector());
        intFilUltPag = pantallaPrincipal.tblCotForPag.getRowCount()-1;
        pantallaPrincipal.tblCotForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
        pantallaPrincipal.tblCotForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
        dblPago += dblPagos;
        dblPagos = objUti.redondear(dblPagos , intNumDec);
        pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
        pantallaPrincipal.tblCotForPag.setValueAt(new Integer(0),  intFilUltPag, 5);

        pantallaPrincipal.tblCotForPag.setValueAt(strSop,  intFilUltPag, 7);
     }

     double dblultpag =  objUti.redondear(Double.parseDouble(pantallaPrincipal.tblCotForPag.getValueAt( intFilUltPag, 4).toString()), intNumDec);
     dblultpag = objUti.redondear(dblultpag + (dlbTot  - (dblPago + dblRete ) ), intNumDec);
     pantallaPrincipal.tblCotForPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);


     for(int x=0; x<pantallaPrincipal.tblCotForPag.getRowCount(); x++){
       int FecPagDoc[] =  pantallaPrincipal._getFecha(pantallaPrincipal.tblCotForPag.getValueAt(x, 2).toString());
       String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
       String strSop = ((pantallaPrincipal.tblCotForPag.getValueAt(x, 7)==null)?"":pantallaPrincipal.tblCotForPag.getValueAt(x, 7).toString() );
  
       strSql="INSERT INTO  tbm_pagMovInv( co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
       " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet , st_regrep ,st_sop, co_tipret )" +
       " VALUES("+intCodEmp+", "+intCodLoc+", "+
       intCodTipDoc+", "+intCodDoc+", "+(x+1)+", "+
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 1)==null || pantallaPrincipal.tblCotForPag.getValueAt(x, 1).toString().equals(""))?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 1)) + ",'" +
       strFechaPag+"',"+
       (objUti.redondear(Double.parseDouble( (pantallaPrincipal.tblCotForPag.getValueAt(x, 4)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 4).toString() ),intNumDec) * -1 )+ ", " +
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 5)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 5).toString() ) + ", " +
       ((pantallaPrincipal.tblCotForPag.getValueAt(x, 3)==null || pantallaPrincipal.tblCotForPag.getValueAt(x, 3).toString().equals(""))?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 3).toString()) +
       " ,'I','"+strSop+"' ,"+Integer.parseInt(((pantallaPrincipal.tblCotForPag.getValueAt(x, 6)==null)?"0":pantallaPrincipal.tblCotForPag.getValueAt(x, 6).toString()))+")";
       stmLoc.executeUpdate(strSql);
     }
     stmLoc.close();
     stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException e) {  blnRes=false;    mensaje("_calculaPagosVen "+ e.toString());     }
    catch(Exception e){  blnRes=false;   mensaje("_calculaPagosVen "+ e.toString());    }
  return  blnRes;
}

    public boolean _generaVenCab(java.sql.Connection connCos, String strFec, int intSecEmp, int intSecGrp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, double dblivaEmpCos, String strSqlCliCos  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intTipMov=-1;
  try{
        if(connCos!=null){
         stmLoc=connCos.createStatement();

         strSql=strSqlCliCos;

         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             strTipPerCli=rstLoc.getString("co_tipper");

              strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
              " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
              " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, " +
              " co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
              " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
              " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
              ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
              " '', '', "+intNumDoc+" , 0, "+intNumDoc+" , '' ,'' , "+ objUti.redondear(dlbSub,2)*intTipMov+" ,"+dblivaEmpCos+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )*intTipMov+", "+ objUti.redondear( dlbIva, 2)*intTipMov+"  "
              + " , 3 ,'Crédito 30-60 días', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+
              " 1 , null, null," +
              " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
              " ,'N', 'S', null )";

              //System.out.println("procesoClass._generaVenCab: "+ strSql );
              stmLoc.executeUpdate(strSql);

              strSql="  INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
              " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
              " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
              ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil ) "
              + "  "+stbinsFacVen.toString();

            //System.out.println("procesoClass._generaVenCab: "+ strSql ); 
              stmLoc.executeUpdate(strSql);


              blnRes=true;
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;

         }}catch(java.sql.SQLException e){ blnRes=false;        }
    catch(Exception e)   { blnRes=false;     mensaje("_CalculoPagoVen "+ e.toString());      }
 return blnRes;
}

    public void iniciarObj(){
     stbinsFacVen=new StringBuffer(); //VARIABLE TIPO BUFFER}
     stbinsFacCom=new StringBuffer();
     stbinsUniReg=new StringBuffer();
     stbinsRelVenCom=new StringBuffer();
     stbinsDevCom=new StringBuffer();
     stbinsDevVen=new StringBuffer();
     stbinsRelVenVen=new StringBuffer();
     dblValTotDesFacVen=0;
     dblSubTotFacVen=0;
     dblSubTotFacCom=0;
     dblSubTotDevCom=0;
     dblSubTotDevVen=0;
     dblValTotDesDevVen=0;
}

    public void cerrarObj(){
     stbinsFacVen=null;
     stbinsFacCom=null;
     stbinsUniReg=null;
     stbinsRelVenCom=null;
     stbinsDevCom=null;
     stbinsDevVen=null;
     stbinsRelVenVen=null;
}

    public boolean _generaVenDet(java.sql.Connection connLoc, java.sql.Connection connCos, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCos, int intCodLocCos, int intCodTipDocCos, int intCodDocCos, int intCodBodPreCos  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmCos;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intEstIns=0;
  int intCodReg=0;
  int intCodItmCos=0;
  int intTipMov=-1;
  double dblMarDes=20;
  try{
    if(connLoc!=null){
      if(connCos!=null){
       stmLoc=connLoc.createStatement();
       stmCos=connCos.createStatement();



     strSql="select *  from ( "
     + " select * "
     + " , round((( costot - ( costot * "+(dblMarDes/100)+" ) )  * abs(canvenfin) ), 2 ) as ndtot "
     + " , round(((  ( costot * "+(dblMarDes/100)+" ) )  * abs(canvenfin) ), 2 ) as nddes  "
     + " FROM ( "
     + " select *, (ndcan-case when candev is null then 0 else candev end ) as canvenfin "
     + " from ( "
     +" select a1.*,a2.candev   from ( "
     +"    select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, "
     + " a1.co_itm, a2.tx_codalt, a1.tx_unimed, a2.tx_nomitm, abs(a1.nd_can) as ndcan ,a1.st_ivaCom  "
     + "  ,a1.st_meringegrfisbod , round((( a1.nd_preuni - ( a1.nd_preuni * ( a1.nd_pordes / 100 ) ) )   ), 2 ) as costot "
     + "  from tbm_cabmovinv as a "
     + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
     + " inner join tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) "
     + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and   a2.st_ser='S' "
     + " and upper(trim(substr(a1.tx_codalt, length(a1.tx_codalt) ,1))) <> 'P'   "        
     + " ) as a1  left join ( "
     + "  select ( a1.nd_can - a2.nd_canvolfac)  as candev, a2.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel "
     + "  ,a.co_loc, a.co_tipdoc, a.co_doc "
     + "  from tbm_cabmovinv as a  "
     + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
     + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
     + " where a.co_tipdoc=3 and a.st_itmserpro='S'"
     + " ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg ) "
     + "  ) as x   ) as x  "
     + "  ) as x  where  canvenfin > 0 order by co_reg ";

//     System.out.println(" --> "+ strSql );

     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

            intCodItmCos=_getCodItmEmpCos(connCos, intCodEmpCos, rstLoc.getString("tx_codalt") );

//            System.out.println(" INGRESA DET "+intCodItmCos);

            if(intCodItmCos != -1 ){
             if (intEstIns==1) stbinsFacVen.append(" UNION ALL ");


             dblSubTotFacVen += rstLoc.getDouble("ndtot");
             dblValTotDesFacVen += rstLoc.getDouble("nddes");



//             nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
//        ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil ) "

             intCodReg++;
             stbinsFacVen.append("SELECT "+intCodEmpCos+","+intCodLocCos+","+intCodTipDocCos+","+intCodDocCos+","+intCodReg+", "
             +" '"+rstLoc.getString("tx_codalt")+"','"+rstLoc.getString("tx_codalt")+"',"+intCodItmCos+", "+intCodItmCos+", "
             +" '"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"',"+intCodBodPreCos+","
             +" "+rstLoc.getDouble("canvenfin")*intTipMov+" , "+rstLoc.getDouble("ndtot")*intTipMov+" , "+rstLoc.getString("costot")+" "
             + " ,"+rstLoc.getDouble("ndtot")*intTipMov+", "+rstLoc.getString("costot")+", "+dblMarDes+", '"+rstLoc.getString("st_ivaCom")+"'  "
             +" ,"+rstLoc.getDouble("ndtot")*intTipMov+", '"+rstLoc.getString("st_meringegrfisbod")+"', 0 ,0, 0, 0  "
             + " ");

             System.out.println(" INGRESA DET ");

             intEstIns=1;

         }else{
             blnRes=false;
             break;
         }
      }
      rstLoc.close();
      rstLoc=null;

      dblSubTotFacVen= objUti.redondear(dblSubTotFacVen,2);

//      if(intEstIns==1){
//        strSql="  INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
//        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
//        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
//        ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil ) "
//        + "  "+stbinsFacVen.toString();
//
//        //System.out.println(" --> "+ strSql );
//
//        stmCos.executeUpdate(strSql);
        blnRes=true;
//
//      }

      stmLoc.close();
      stmLoc=null;
      stmCos.close();
      stmCos=null;
    
  }}}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_generaVenDet "+ e.toString());   }
    catch(Exception e)   { blnRes=false;     mensaje("_generaVenDet "+ e.toString());  System.out.println(""+e.toString());    }
 return blnRes;
}

    /**
     * Funcion permite obtener el codigo del item de cosenco
     * @param connCos
     * @param intCodEmpCos
     * @param strCodItmAlt
     * @return -1  no hay o error  <br> retorna el codigo del item
     */
    private int _getCodItmEmpCos(java.sql.Connection connCos, int intCodEmpCos, String strCodItmAlt  ){
      int intCodItm=-1;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
          if(connCos!=null){
           stmLoc=connCos.createStatement();

         strSql="select co_itm from tbm_inv where co_emp="+intCodEmpCos+" and upper(tx_codalt)=upper('"+strCodItmAlt+"') ";
         System.out.println("OK "+strSql);
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             intCodItm=rstLoc.getInt("co_itm");
         }
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;

      }}catch(java.sql.SQLException e){ intCodItm=-1;   }
        catch(Exception e)   { intCodItm=-1;  }
     return intCodItm;
    }

    public int _getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
      int intCodDoc=0;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
        if(conn!=null){
          stmLoc=conn.createStatement();

         strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
         " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next()) intCodDoc = rstLoc.getInt("co_doc");

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

     }}catch(java.sql.SQLException e) {  mensaje("_getCodigoDoc "+ e.toString());    }
       catch(Exception e) {  mensaje("_getCodigoDoc "+ e.toString());   }
     return intCodDoc;
    }

    public int _getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc,  int intCodTipDoc ){
      int intUltNumDoc=0;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
        if(conn!=null){
          stmLoc=conn.createStatement();

         strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

     }}catch(java.sql.SQLException e) {   mensaje("_getUltNumDoc "+ e.toString());    }
       catch(Exception e) {   mensaje("_getUltNumDoc "+ e.toString());   }
     return intUltNumDoc;
    }

    public void mensaje(String strMes){
        System.out.println("Error. "+strMes );
        almacenarArchHis(strMes );
    }


    /**
     * Funcion que permite verifica si existe el item en empresa cosenco
     * @param conn
     * @param connCos
     * @param strCodEmp
     * @param strCodLoc
     * @param strCodTipDoc
     * @param strCodDoc
     * @return  true  si todo bien  false algo salio mal
     */
    public boolean _getVerificaExisItmCos(java.sql.Connection conn, java.sql.Connection connCos, int intCodEmpCos,  int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc  ){
     boolean blnRes=false;
     java.sql.Statement stmLoc, stmLoc01;
     java.sql.ResultSet rstLoc, rstLoc01;
     String strSql="";
      try{
       if(conn != null ){
           stmLoc=conn.createStatement();
           stmLoc01=connCos.createStatement();

           CrearItm objCreItm = new CrearItm(objParSis);

           strSql="SELECT a.co_itm, a1.tx_codalt FROM tbm_detmovinv as a "
           + " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm ) "
           + " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" "
           + " and a1.st_ser='S' ";

           System.out.println("procesoClass._getVerificaExisItmCos: (T-C-D) \n" + strSql);

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               strSql="select tx_codalt, tx_nomitm  from tbm_inv where co_emp="+intCodEmpCos+" and UPPER(tx_codalt)=UPPER('"+rstLoc.getString("tx_codalt")+"') ";

               System.out.println("procesoClass._getVerificaExisItmCos2: (COCENCO) \n" + strSql);

               rstLoc01=stmLoc01.executeQuery(strSql);
               if(rstLoc01.next()){

                    System.out.println(" Si Existe --> COCENCO --> ");

                   blnRes=true;
               }else{

                    System.out.println(" No Existe y lo crea -->  ");

                    if(objCreItm.crearItmCos(conn, intCodEmp,  rstLoc.getInt("co_itm") )){
                        blnRes=true;
                        System.out.println(" cREADO ");
                    }else{
                        blnRes=false; System.out.println(" eRROR cREADO ");
                        break;
                    }
               }
               rstLoc01.close();
               rstLoc01=null;

           }
           rstLoc.close();
           rstLoc=null;

           stmLoc.close();
           stmLoc=null;
           stmLoc01.close();
           stmLoc01=null;

           objCreItm=null;

     }}catch(java.sql.SQLException e)  {  blnRes=false;  System.out.println(""+e );   mensaje("getVerificaExisItmCos 1  "+ e.toString());  }
       catch(Exception e)  { blnRes=false;   System.out.println(""+e );     mensaje("getVerificaExisItmCos 2 "+ e.toString()); }
      return blnRes;
    }

    public boolean _ingCabCom(java.sql.Connection connEmp, String strFec, int intSecEmp, int intSecGrp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodCliPrv, double dlbSub, double dlbIva, double dblIvaEmpCom  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
        if(connEmp!=null){
         stmLoc=connEmp.createStatement();

         strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli="+intCodCliPrv;
        //System.out.println("Cli. "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             strTipPerCli=rstLoc.getString("co_tipper");

              strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
              " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
              " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, " +
              " co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
              " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
              " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
              ",null, '', '"+rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', " +
              " '', '', "+intNumDoc+" , 0, "+intNumDoc+" , '' ,'' , "+ objUti.redondear(dlbSub,2)+" ,"+dblIvaEmpCom+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )+", "+ objUti.redondear( dlbIva, 2)+" , "
              + " 3 ,'Crédito 30-60 días', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+
              " 1 , null, null," +
              " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'N', null "+
              " ,'N', 'S', null )";
              //System.out.println(" com1.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);


              strSql="  INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
              " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
              " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_cosuni, nd_porDes, st_ivaCom " +
              ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil ) "
              + "  "+stbinsFacCom.toString();
              //System.out.println(" com2.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);


              strSql="  INSERT INTO  tbr_detMovInvOtrBasDat(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
              " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel ) " +
              "  "+stbinsUniReg.toString();
              //System.out.println(" com3.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);


              strSql="  INSERT INTO  tbr_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, st_reg, "+
              " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel ) " +
              "  "+stbinsRelVenCom.toString();
             // System.out.println(" com4.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);




              blnRes=true;
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;

      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingCabCom 1 "+ e.toString());     }
        catch(Exception e)   { blnRes=false;     mensaje("_ingCabCom 2 "+ e.toString());      }
     return blnRes;
    }

    public boolean _ingDetCom(java.sql.Connection connLoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodTipDocCom, int intCodDocCom , int intCodBodPreCos, int intCodEmpCos, int intCodLocCos, int intCodTipDocCos, int intCodDocCos  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      int intEstIns=0;
      int intCodReg=0;
      int intCodItm=0;
      double dblMarDes=20;
      try{
        if(connLoc!=null){
           stmLoc=connLoc.createStatement();

         strSql="select *  from ( "
         + " select * "
         + " , round((( costot - ( costot * "+(dblMarDes/100)+" ) )  * abs(canvenfin) ), 2 ) as ndtot "
         + " , round(((  ( costot * "+(dblMarDes/100)+" ) )  * abs(canvenfin) ), 2 ) as nddes  "
         + " , round((( costot - ( costot *"+(dblMarDes/100)+" ) ) ), 4 ) as costotfin "
         + " FROM ( "
         + " select *, (ndcan-case when candev is null then 0 else candev end ) as canvenfin "
         + " from ( "
         +" select a1.*,a2.candev   from ( "
         +"    select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, "
         + " a1.co_itm, a2.tx_codalt, a1.tx_unimed, a1.tx_nomitm, abs(a1.nd_can) as ndcan ,a1.st_ivaCom  "
         + "  ,a1.st_meringegrfisbod , round((( a1.nd_preuni - ( a1.nd_preuni * ( a1.nd_pordes / 100 ) ) )   ), 2 ) as costot "
         + "  from tbm_cabmovinv as a "
         + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
         + " inner join tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) "
         + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and   a2.st_ser='S' "
         + " and upper(trim(substr(a1.tx_codalt, length(a1.tx_codalt) ,1))) <> 'P' "        
         + " ) as a1  left join ( "
         + "  select ( a1.nd_can - a2.nd_canvolfac) as candev, a2.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel "
         + "  ,a.co_loc, a.co_tipdoc, a.co_doc "
         + "  from tbm_cabmovinv as a  "
         + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
         + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
         + " where a.co_tipdoc=3 and a.st_itmserpro='S'"
         + " ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg ) "
         + "  ) as x   ) as x  "
         + "  ) as x  where  canvenfin > 0 order by co_reg ";


        // System.out.println(" --> "+ strSql );

         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
            // strTipPerCli=rstLoc.getString("co_tipper");

                intCodItm=rstLoc.getInt("co_itm");


                 dblSubTotFacCom += rstLoc.getDouble("ndtot");


                 if (intEstIns==1) stbinsFacCom.append(" UNION ALL ");

                 intCodReg++;
                 stbinsFacCom.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocCom+","+intCodDocCom+","+intCodReg+", "
                 +" '"+rstLoc.getString("tx_codalt")+"','"+rstLoc.getString("tx_codalt")+"',"+intCodItm+", "+intCodItm+", "
                 +" '"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"',"+intCodBodPreCos+","
                 +" "+rstLoc.getDouble("canvenfin")+" ,"+rstLoc.getDouble("ndtot")+" ,"+rstLoc.getDouble("costotfin")+" ,"+rstLoc.getDouble("ndtot")+" "
                 + " , "+rstLoc.getDouble("costotfin")+", 0, '"+rstLoc.getString("st_ivaCom")+"'  "
                 +" ,"+rstLoc.getDouble("ndtot")+", '"+rstLoc.getString("st_meringegrfisbod")+"', 0 ,0, 0, 0  "
                 + " ");


                 if(intEstIns==1) stbinsUniReg.append(" UNION ALL ");
                 stbinsUniReg.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocCom+","+intCodDocCom+","+intCodReg+" "
                 + ", "+intCodEmpCos+","+intCodLocCos+","+intCodTipDocCos+","+intCodDocCos+","+intCodReg+" ");


                 if(intEstIns==1) stbinsRelVenCom.append(" UNION ALL ");
                 stbinsRelVenCom.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+","+rstLoc.getInt("co_reg")+",'A', "
                 + " "+intCodEmp+","+intCodLoc+","+intCodTipDocCom+","+intCodDocCom+","+intCodReg+" ");


                 intEstIns=1;


          }
          rstLoc.close();
          rstLoc=null;

    //      if(intEstIns==1){
    //        strSql="  INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
    //        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
    //        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
    //        ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil ) "
    //        + "  "+stbins.toString();
    //
    //       // System.out.println(" --> "+ strSql );
    //
    //        stmLoc.executeUpdate(strSql);
            blnRes=true;
    //
    //      }

          stmLoc.close();
          stmLoc=null;


      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingDetCom 1 "+ e.toString());   }
        catch(Exception e)   { blnRes=false;     mensaje("_ingDetCom 2 "+ e.toString());  System.out.println(""+e.toString());    }
     return blnRes;
    }

    public boolean _CalculoPagoCom(java.sql.Connection conPag, int  intCodEmp ){
     boolean blnRes=false;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     String strSql="",strSql01="";
     try{
     if(conPag!=null){

        stmLoc=conPag.createStatement();

        strSql="SELECT A1.ne_numPag, A2.ne_diaCre " +
        " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
        " Where A1.co_emp = " +intCodEmp + // Consultando en la empresa en la ke se esta trabajando
        " and A1.co_forPag = 3 Order by A2.co_reg";

        strSql01="SELECT count(A2.ne_diaCre) as c  " +
       " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
       " Where A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
       " and A1.co_forPag = 3 ";
        rstLoc=stmLoc.executeQuery(strSql01);
        if(rstLoc.next()) intCanArr[0] = rstLoc.getInt(1);
        rstLoc.close();
        rstLoc=null;

        rstLoc=stmLoc.executeQuery(strSql);
        int x=0;
        while(rstLoc.next()){
          intarreglodiaCom[x]=rstLoc.getInt(2);
          intarreglonumCom[x]=rstLoc.getInt(1);
          x++;
        }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
      blnRes=true;
     }}catch(Exception e) { blnRes=false;  mensaje("_CalculoPagoCom "+ e.toString());    }
      return blnRes;
    }


    public boolean _calculaPagosCom(java.sql.Connection conn, double dblSub, double dlbIva, double dlbTot, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strCodTipPerCli, java.util.Date datFecDoc ){
     boolean blnRes=false;
     int intVal =  intCanArr[0];
     int intsizearre = intarreglodiaCom.length;
     int intCodMotDoc=0;
     int intCodTipPerEmp=0;
     intVal= intsizearre - (intsizearre-intVal);
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     int intFilUltPag = 0;
     double dblTotalDoc=0, dblMonCal=0, dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
     String strSql="";
     int intNumDec=2;
     try{
        if(conn!=null){
          stmLoc=conn.createStatement();


        java.util.Calendar calObj = java.util.Calendar.getInstance();
        calObj.setTime(datFecDoc);

         String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
         calObj.get(java.util.Calendar.MONTH)+1     ,
         calObj.get(java.util.Calendar.YEAR)  );

    //    txtFecDoc.setText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
    //    calObj.get(java.util.Calendar.MONTH)+1     ,
    //    calObj.get(java.util.Calendar.YEAR)  );


          java.util.Calendar objFec = java.util.Calendar.getInstance();
          int fecDoc [] =  pantallaPrincipal._getFecha(strFechaCal);  //txtFecDoc.getFecha(txtFecDoc.getText());

           objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
           objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
           objFec.set(java.util.Calendar.YEAR, fecDoc[2]);

          dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)
          dblTotalDoc = dlbTot;

          while(pantallaPrincipal.tblOrdComForPag.getRowCount()>0)
            ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).removeRow(0);

             Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker("d/m/y");
             int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
             double dblPagos=0.00;
             double dblPago=0.00;
             int i=0;


            strSql="SELECT co_mot FROM tbm_motdoc WHERE co_emp="+intCodEmp+" AND tx_tipmot in ('S') ";
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()) intCodMotDoc = rstLoc.getInt(1);
            rstLoc.close();
            rstLoc=null;

            strSql="SELECT b.co_tipper,  b.tx_descor FROM tbm_emp AS a "+
            " LEFT JOIN tbm_tipper AS b ON(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper) WHERE a.co_emp="+intCodEmp;
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()) intCodTipPerEmp = rstLoc.getInt("co_tipper");
            rstLoc.close();
            rstLoc=null;

              strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri "+
              " FROM tbm_polret as polret " +
              " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
              " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+
              " where polret.co_emp = "+ intCodEmp+" and co_mot = "+intCodMotDoc+" and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" " +
              "  AND polret.st_reg='A'  AND  '"+objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){

                 ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                 pantallaPrincipal.tblOrdComForPag.setValueAt(new String("") ,pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 1);
                 pantallaPrincipal.tblOrdComForPag.setValueAt(strFechaCal, pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 2);

                 if(rstLoc.getString("tx_aplret").equals("S")){

                  String fecha = objUti.formatearFecha(strFechaCal,"dd/MM/yyyy","yyyy/MM/dd");
                  java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                  java.util.Date fe2 = objUti.parseDate("2007/07/01","yyyy/MM/dd");

                   if(fe1.before(fe2)) { // maneja el porsentaje anterior antes del 1 de julio del 2007
                        dblRetFue = objUti.redondeo( (   dblSub  * 0.01 ),2 );

                        pantallaPrincipal.tblOrdComForPag.setValueAt("1.000", pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                        pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetFue) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                        pantallaPrincipal.tblOrdComForPag.setValueAt("1" , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                        pantallaPrincipal.tblOrdComForPag.setValueAt(""  , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
                   }else {
                       dblRetFue = objUti.redondeo( (  dblSub * ( rstLoc.getDouble("nd_porret")/100)),2 );

                       pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("nd_porret"), pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                       pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetFue) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                       pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("co_tipret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                       pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_codsri") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
                   }
                  }
                  if(rstLoc.getString("tx_aplret").equals("I")){
                     dblRetIva = objUti.redondeo(  (  dlbIva * ( rstLoc.getDouble("nd_porret")/100)),2 );
                     pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("nd_porret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 3);
                     pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblRetIva) , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 4);
                     pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("co_tipret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 6);
                     pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_codsri") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 7);
                  }

                pantallaPrincipal.tblOrdComForPag.setValueAt(rstLoc.getString("tx_aplret") , pantallaPrincipal.tblOrdComForPag.getRowCount()-1, 8);
              }
              rstLoc.close();
              rstLoc=null;

             double dblRete = 0;
             dblRete = dblRetFueFle+dblRetFue+dblRetIva;
             dblMonCal = objUti.redondear( ( dblTotalDoc - dblRete ),intNumDec);

             for(i=0; i < intVal; i++) {
                    diaCreAnt = diaCre;
                    diaCre = intarreglodiaCom[i];
                    numPag = intarreglonumCom[i];
                    diaCreAcum = diaCre - diaCreAnt;
                    if (diaCre!=0)
                      objFec.add(java.util.Calendar.DATE, diaCreAcum);

                    dtePckPag.setAnio( objFec.get(java.util.Calendar.YEAR));
                    dtePckPag.setMes( objFec.get(java.util.Calendar.MONTH)+1);
                    dtePckPag.setDia(objFec.get(java.util.Calendar.DAY_OF_MONTH));
                    double dblValPag =0;

                   dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
                   dblPago += dblPagos;
                   dblPagos = objUti.redondear(dblPagos ,intNumDec);
                   dblValPag = dblPagos;

                   ((javax.swing.table.DefaultTableModel)pantallaPrincipal.tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                    intFilUltPag = pantallaPrincipal.tblOrdComForPag.getRowCount()-1;
                    pantallaPrincipal.tblOrdComForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                    pantallaPrincipal.tblOrdComForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                    pantallaPrincipal.tblOrdComForPag.setValueAt(new Double(dblValPag), intFilUltPag, 4);
                    pantallaPrincipal.tblOrdComForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
            }

          double dblultpag =  objUti.redondear(Double.parseDouble(pantallaPrincipal.tblOrdComForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
          dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);
          pantallaPrincipal.tblOrdComForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);


      String strApliRet="";
      for(int x=0; x<pantallaPrincipal.tblOrdComForPag.getRowCount(); x++){

         strApliRet=(pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8).toString();
         int FecPagDoc[] =  pantallaPrincipal._getFecha(pantallaPrincipal.tblOrdComForPag.getValueAt(x, 2).toString());
         String strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];

         strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " +
         " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
         " VALUES("+intCodEmp+", "+intCodLoc+","+intCodTipDoc+","+
         intCodDoc+", "+(x+1)+", "+((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 1).equals(""))?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 1))+", "+
         "'"+strFechaPag+"', "+((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 3)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 3))+", "+
         ""+objUti.redondeo(Double.parseDouble( (pantallaPrincipal.tblOrdComForPag.getValueAt(x, 4)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 4).toString() ),6)+", "+
         ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 5)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 5).toString())+","+
         ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 6)==null)?"0":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 6).toString())+",'"+
         ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 7)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 7).toString())+"','"+
         ((pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8)==null)?"":pantallaPrincipal.tblOrdComForPag.getValueAt(x, 8).toString())+"',"+
         (strApliRet.equals("S")?dblSub:(strApliRet.equals("I")?dlbIva:0) )+",'I' )";
                 //(strApliRet.equals("S")?dblSub:0)+",'I' )";
         stmLoc.executeUpdate(strSql);
      }
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

     }}catch(java.sql.SQLException e) { blnRes=false;   mensaje("_calculaPagosCom  1  "+ e.toString());    }
       catch(Exception e){ blnRes=false;  mensaje("_calculaPagosCom 2  "+ e.toString());  }
     return blnRes;
    }





    /**
     * Funcion que cambia los estado que ya fue procesado.
     * @param conn
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @return true = todo bien  <br> false =  error.
     */
    public boolean _cambiarEstProCos(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();
            stmLoc01=conn.createStatement();

            strSql="select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  "
            + " ,a2.co_emp as coempdev, a2.co_loc as colocdev, a2.co_tipdoc as cotipdocdev, a2.co_doc as codocdev  "
            + " from ( "
            + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_can as canven "
            + " from tbm_cabmovinv as a  "
            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + " where  a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  "
            + " ) as a1  "
            + " left join (  "
            + " select a1.nd_can as candev, a.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel  "
            + " ,a.co_loc, a.co_tipdoc, a.co_doc  "
            + "  From tbm_cabmovinv as a  "
            + "  inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + "  inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
            + "  where a.co_tipdoc=3 and a.st_itmserpro='S'  "
            + " ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg ) "
            + " ";
            rstLoc = stmLoc.executeQuery(strSql);
            while(rstLoc.next()){

                strSql="UPDATE tbm_cabmovinv set st_itmSerPro = 'P' where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" ";
                strSql+=" ; UPDATE tbm_cabmovinv set st_itmSerPro = 'P' where co_emp="+rstLoc.getInt("coempdev")+" and co_loc="+rstLoc.getInt("colocdev")+" and co_tipdoc="+rstLoc.getInt("cotipdocdev")+" and co_doc="+rstLoc.getInt("codocdev")+" ";
                stmLoc01.executeUpdate(strSql);
                blnRes=true;

            }
            rstLoc.close();
            rstLoc=null;

     }}catch(java.sql.SQLException e){ blnRes=false; }
       catch(Exception e){  blnRes=false; }
    return blnRes;
    }




    /**
     * Funcion que cambia los estado que ya fue procesado.
     * @param conn
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @return true = todo bien  <br> false =  error.
     */
    public boolean _cambiarEstNoProCos(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();
            stmLoc01=conn.createStatement();

            strSql="select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  "
            + " ,a2.co_emp as coempdev, a2.co_loc as colocdev, a2.co_tipdoc as cotipdocdev, a2.co_doc as codocdev  "
            + " from ( "
            + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_can as canven "
            + " from tbm_cabmovinv as a  "
            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + " where  a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  "
            + " ) as a1  "
            + " left join (  "
            + " select a1.nd_can as candev, a.co_emp, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel  "
            + " ,a.co_loc, a.co_tipdoc, a.co_doc  "
            + "  From tbm_cabmovinv as a  "
            + "  inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + "  inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
            + "  where a.co_tipdoc=3 and a.st_itmserpro='S'  "
            + " ) as  a2 on ( a2.co_emp= a1.co_emp  and a2.co_locrel= a1.co_loc  and a2.co_tipdocrel= a1.co_tipdoc  and a2.co_docrel= a1.co_doc  and a2.co_regrel= a1.co_reg ) "
            + " ";
            rstLoc = stmLoc.executeQuery(strSql);
            while(rstLoc.next()){

                strSql="UPDATE tbm_cabmovinv set st_itmSerPro = null where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc")+" ";
                strSql+=" ; UPDATE tbm_cabmovinv set st_itmSerPro = null where co_emp="+rstLoc.getInt("coempdev")+" and co_loc="+rstLoc.getInt("colocdev")+" and co_tipdoc="+rstLoc.getInt("cotipdocdev")+" and co_doc="+rstLoc.getInt("codocdev")+" ";
                stmLoc01.executeUpdate(strSql);
                blnRes=true;

            }
            rstLoc.close();
            rstLoc=null;

     }}catch(java.sql.SQLException e){ blnRes=false; }
       catch(Exception e){  blnRes=false; }
    return blnRes;
    }




    public boolean _ingDetDevCom(java.sql.Connection connLoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodTipDocDev, int intCodDocDev  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      int intEstIns=0;
      int intCodReg=0;
      int intCodItm=0;
      try{
        if(connLoc!=null){
           stmLoc=connLoc.createStatement();

          strSql=" select a1.nd_can as candev, a5.co_emprel, a5.co_locrel, a5.co_tipdocrel, a5.co_docrel, a5.co_regrel  "
          + " , a6.co_itm, a6.tx_codalt, a6.tx_nomitm, a6.tx_unimed, a6.co_bod, a6.nd_cosuni, a6.nd_pordes  "
          + " ,round(((a6.nd_cosuni-(a6.nd_cosuni*(a6.nd_pordes/100))) * a1.nd_can ), 2) as ndtot, a6.st_ivaCom, a6.st_meringegrfisbod, a6.nd_can "
          + " from tbm_cabmovinv as a  "
          + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
          + " inner join tbm_inv as ser on (ser.co_emp=a1.co_emp and ser.co_itm=a1.co_itm )  "
          + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven )  "
          + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )  "
          + " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc )  "
          + " inner join tbr_detmovinv as a5 on (a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=a3.co_reg   "
          + " and  a5.co_tipdocrel=2 )   "
          + " inner join tbm_detmovinv as a6 on (a6.co_emp=a5.co_emprel and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel and a6.co_reg=a5.co_regrel )  "
          + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  and  a4.st_itmserpro = 'P' and ser.st_ser='S'  "
          + " ";
        // System.out.println(" --> "+ strSql );

         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

                intCodItm=rstLoc.getInt("co_itm");

                 dblSubTotDevCom += rstLoc.getDouble("ndtot");

                 if (intEstIns==1) stbinsDevCom.append(" UNION ALL ");

                 intCodReg++;
                 stbinsDevCom.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocDev+","+intCodDocDev+","+intCodReg+", "
                 +" '"+rstLoc.getString("tx_codalt")+"','"+rstLoc.getString("tx_codalt")+"',"+intCodItm+", "+intCodItm+", "
                 +" '"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"',"+rstLoc.getInt("co_bod")+","
                 +" "+rstLoc.getDouble("candev")*-1+" ,"+rstLoc.getDouble("ndtot")*-1+" ,"+rstLoc.getDouble("nd_cosuni")+" ,"+rstLoc.getDouble("ndtot")*-1+" "
                 + " , "+rstLoc.getDouble("nd_cosuni")+", "+rstLoc.getDouble("nd_pordes")+", '"+rstLoc.getString("st_ivaCom")+"'  "
                 +" ,"+rstLoc.getDouble("ndtot")*-1+", '"+rstLoc.getString("st_meringegrfisbod")+"', 0 ,"+rstLoc.getDouble("nd_cosuni")+" "
                 + " , "+rstLoc.getDouble("nd_can")+" ");


                 if(intEstIns==1) stbinsRelVenCom.append(" UNION ALL ");
                 stbinsRelVenCom.append("SELECT "+rstLoc.getInt("co_emprel")+","+rstLoc.getInt("co_locrel")+","+rstLoc.getInt("co_tipdocrel")+","+rstLoc.getInt("co_docrel")+","+rstLoc.getInt("co_regrel")+",'A', "
                 + " "+intCodEmp+","+intCodLoc+","+intCodTipDocDev+","+intCodDocDev+","+intCodReg+" ");


                 intEstIns=1;


          }
          rstLoc.close();
          rstLoc=null;


          blnRes=true;

          stmLoc.close();
          stmLoc=null;


      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingDetCom 1 "+ e.toString());   }
        catch(Exception e)   { blnRes=false;     mensaje("_ingDetCom 2 "+ e.toString());  System.out.println(""+e.toString());    }
     return blnRes;
    }

    public boolean _ingCabDevCom(java.sql.Connection connEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strFec, int intSecEmp, int intSecGrp, int intCodTipDocDev, int intCodDocDev, int intNumDoc, double dlbSub, double dlbIva, double dblIvaEmpCom  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
        if(connEmp!=null){
         stmLoc=connEmp.createStatement();

         strSql="select co_cli,tx_nomCli,tx_dirCli,tx_ruc,tx_telCli,ne_numdoc   from tbm_cabmovinv where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
        //System.out.println("Cli. "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){

              strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
              " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
              " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, " +
              " co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
              " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
              " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDocDev+", "+intCodDocDev+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
              ",null, '', '"+rstLoc.getString("tx_nomCli")+"','"+rstLoc.getString("tx_dirCli")+"','"+rstLoc.getString("tx_ruc")+ "','"+rstLoc.getString("tx_telCli")+"', " +
              " '', '', "+intNumDoc+" , "+rstLoc.getString("ne_numdoc")+", 0 , '' ,'' , "+ objUti.redondear(dlbSub,2)+" ,"+dblIvaEmpCom+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )+", "+ objUti.redondear( dlbIva, 2)+" , "
              + " 1 ,'Contado', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+
              " 1 , null, null," +
              " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
              " ,'N', 'S', null )";
              //System.out.println(" com1.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);

              strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
              " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
              " co_bod, nd_can ,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
              ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_cosuni, nd_canorg ) "
              + "  "+stbinsDevCom.toString();

              //System.out.println(" com2.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);


    //          strSql="  INSERT INTO  tbr_detMovInvOtrBasDat(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
    //          " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel ) " +
    //          "  "+stbinsUniReg.toString();
    //          //System.out.println(" com3.. --> "+ strSql );
    //          stmLoc.executeUpdate(strSql);


              strSql="  INSERT INTO  tbr_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, st_reg, "+
              " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel ) " +
              "  "+stbinsRelVenCom.toString();
             // System.out.println(" com4.. --> "+ strSql );
              stmLoc.executeUpdate(strSql);




              blnRes=true;
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;

      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingCabCom 1 "+ e.toString());     }
        catch(Exception e)   { blnRes=false;     mensaje("_ingCabCom 2 "+ e.toString());      }
     return blnRes;
    }

    public boolean _ingresarPagDevVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, java.util.Date datFecDoc, int intTip  ){
     boolean blnRes=false;
     int intVal =  intCanArr[0];
     int intsizearre = intarreglodia.length;
     intVal= intsizearre - (intsizearre-intVal);
     java.sql.Statement stmLoc;
     double dblBaseDePagos = dlbTot;
     String strSql="";
     int VarC = 0;
     String VarN = "N";
     String VarA = "A";
     try{
        if(conn!=null){
          stmLoc=conn.createStatement();

            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(datFecDoc);

            String strFechaCal=pantallaPrincipal._setFechaText( calObj.get(java.util.Calendar.DAY_OF_MONTH),
            calObj.get(java.util.Calendar.MONTH)+1     ,
            calObj.get(java.util.Calendar.YEAR)  );


          int Fecha[] =  pantallaPrincipal._getFecha(strFechaCal);  //txtFecDoc.getFecha(txtFecDoc.getText());
          String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

          strSql = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
          " values("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+", 1 ,"+
          VarC+",'"+strFecha+"',abs("+dblBaseDePagos+")*"+intTip+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I')";
          stmLoc.executeUpdate(strSql);

         stmLoc.close();
         stmLoc=null;
         blnRes=true;
      }}catch(java.sql.SQLException e) {  blnRes=false;   mensaje("_ingresarPagDevVen "+ e.toString());    }
        catch(Exception e){  blnRes=false;  mensaje("_ingresarPagDevVen "+ e.toString());   }
      return  blnRes;
    }





    public boolean _ingDetDevVen(java.sql.Connection connCos, int intCodEmpCos, int intCodLocCos, int intCodTipDocDevCos, int intCodDocDevCos, StringBuffer stbDatFacVen  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      String strSql="";
      int intEstIns=0;
      int intCodReg=0;
      int intCodItm=0;
      try{
        if(connCos!=null){
           stmLoc=connCos.createStatement();

           strSql=" select a.cantidad as candev, a1.tx_codalt, a1.co_itm, a1.tx_nomitm, a1.tx_unimed, a1.co_bod "
           + " ,a1.nd_preuni, a1.nd_pordes, a1.st_ivaCom, a1.st_meringegrfisbod, a1.nd_cosuni, abs(a1.nd_can) as nd_can "
           + " ,round((  (a1.nd_preuni-(a1.nd_preuni*(a1.nd_pordes/100))) * a.cantidad ), 2) as ndtot "
           + " ,round(( ((a1.nd_preuni*(a1.nd_pordes/100))) * a.cantidad ), 2) as nddes "
           + " ,a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "
           + " from ( "+stbDatFacVen.toString()+"  ) as a "
           + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg ) "
           + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
           + "  where a2.st_reg = 'A'  ";

         //System.out.println(" --> "+ strSql );

         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

                intCodItm=rstLoc.getInt("co_itm");

                 dblSubTotDevVen += rstLoc.getDouble("ndtot");
                 dblValTotDesDevVen +=rstLoc.getDouble("nddes");

                 if (intEstIns==1) stbinsDevVen.append(" UNION ALL ");

                 intCodReg++;
                 stbinsDevVen.append("SELECT "+intCodEmpCos+","+intCodLocCos+","+intCodTipDocDevCos+","+intCodDocDevCos+","+intCodReg+", "
                 +" '"+rstLoc.getString("tx_codalt")+"','"+rstLoc.getString("tx_codalt")+"',"+intCodItm+", "+intCodItm+", "
                 +" '"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"',"+rstLoc.getInt("co_bod")+","
                 +" "+rstLoc.getDouble("candev")+" ,"+rstLoc.getDouble("ndtot")+" ,"+rstLoc.getDouble("nd_cosuni")+" ,"+rstLoc.getDouble("ndtot")+" "
                 + " , "+rstLoc.getDouble("nd_preuni")+", "+rstLoc.getDouble("nd_pordes")+", '"+rstLoc.getString("st_ivaCom")+"'  "
                 +" ,"+rstLoc.getDouble("ndtot")+", '"+rstLoc.getString("st_meringegrfisbod")+"', 0 ,"+rstLoc.getDouble("nd_cosuni")+" "
                 + " , "+rstLoc.getDouble("nd_can")+" ");

                 if(intEstIns==1) stbinsRelVenVen.append(" UNION ALL ");
                 stbinsRelVenVen.append("SELECT "+rstLoc.getInt("co_emp")+","+rstLoc.getInt("co_loc")+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+","+rstLoc.getInt("co_reg")+",'A', "
                 + " "+intCodEmpCos+","+intCodLocCos+","+intCodTipDocDevCos+","+intCodDocDevCos+","+intCodReg+" ");

                 intEstIns=1;

          }
          rstLoc.close();
          rstLoc=null;

          blnRes=true;

          stmLoc.close();
          stmLoc=null;


      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingDetCom 1 "+ e.toString());   }
        catch(Exception e)   { blnRes=false;     mensaje("_ingDetCom 2 "+ e.toString());  System.out.println(""+e.toString());    }
     return blnRes;
    }

    public boolean _ingCabDevVen(java.sql.Connection connCos, int intCodEmpCos, int intCodLocCos, int intCodTipDocDevCos, int intCodDocDevCos, int intNumDoc, String strFec, int intSecEmp, int intSecGrp, double dlbSub, double dlbIva,  StringBuffer stbDatFacVen  ){
      boolean blnRes=false;
      java.sql.Statement stmLoc, stmLoc01;
      java.sql.ResultSet rstLoc;
      String strSql="";
      try{
        if(connCos!=null){
         stmLoc=connCos.createStatement();
         stmLoc01=connCos.createStatement();

        strSql="select  a1.*, a2.co_cli, a2.tx_nomCli, a2.tx_dirCli, a2.tx_ruc, a2.tx_telCli, a2.ne_numdoc , a2.nd_poriva from ( "
        + " select co_emp, co_loc, co_tipdoc, co_doc from( "+stbDatFacVen+") as x group by co_emp, co_loc, co_tipdoc, co_doc "
        + " ) as a1 "
        + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
        + " ";
         //System.out.println("Cli. "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){

              strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, " +
              " co_com, tx_ate, tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
              " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, " +
              " co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
              " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
              " VALUES("+intCodEmpCos+", "+intCodLocCos+", "+intCodTipDocDevCos+", "+intCodDocDevCos+", '"+strFec+"', "+rstLoc.getString("co_cli")+" " +
              ",null, '', '"+rstLoc.getString("tx_nomCli")+"','"+rstLoc.getString("tx_dirCli")+"','"+rstLoc.getString("tx_ruc")+ "','"+rstLoc.getString("tx_telCli")+"', " +
              " '', '', "+intNumDoc+" , "+rstLoc.getString("ne_numdoc")+", 0 , '' ,'' , "+ objUti.redondear(dlbSub,2)+" ,"+rstLoc.getString("nd_poriva")+" ,"+ objUti.redondear( (dlbSub+dlbIva),2 )+", "+ objUti.redondear( dlbIva, 2)+" , "
              + " 1 ,'Contado', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+
              " 1 , null, null," +
              " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
              " ,'N', 'S', null )";
              //System.out.println(" com1.. --> "+ strSql );
              stmLoc01.executeUpdate(strSql);

              strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
              " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
              " co_bod, nd_can ,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
              ",nd_costotgrp , st_meringegrfisbod , nd_cancon, nd_cosuni, nd_canorg ) "
              + "  "+stbinsDevVen.toString();

              //System.out.println(" com2.. --> "+ strSql );
              stmLoc01.executeUpdate(strSql);


              strSql="  INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipdoc , co_doc, st_reg, "+
              " co_emprel, co_locrel, co_tipdocrel , co_docrel ) VALUES ( "
              + " "+intCodEmpCos+", "+intCodLocCos+", "+intCodTipDocDevCos+", "+intCodDocDevCos+", 'A' "
              + " ,"+rstLoc.getInt("co_emp")+","+rstLoc.getInt("co_loc")+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+" ) ";
              //System.out.println(" com3.. --> "+ strSql );
              stmLoc01.executeUpdate(strSql);


              strSql="  INSERT INTO  tbr_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, st_reg, "+
              " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel ) " +
              "  "+stbinsRelVenVen.toString();
             // System.out.println(" com4.. --> "+ strSql );
              stmLoc01.executeUpdate(strSql);

              blnRes=true;
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;
          stmLoc01.close();
          stmLoc01=null;

      }}catch(java.sql.SQLException e){ blnRes=false;    mensaje("_ingCabDevVen 1 "+ e.toString());     }
        catch(Exception e)   { blnRes=false;     mensaje("_ingCabDevVen 2 "+ e.toString());      }
     return blnRes;
    }





    /**
     * Funcion que cambia los estado que ya fue procesado.
     * @param conn
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @return true = todo bien  <br> false =  error.
     */
    public boolean _cambiarEstProDev(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    String strSql="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();

            strSql="UPDATE tbm_cabmovinv set st_itmSerPro ='P' where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
            stmLoc.executeUpdate(strSql);
            blnRes=true;


     }}catch(java.sql.SQLException e){ blnRes=false; }
       catch(Exception e){  blnRes=false; }
    return blnRes;
    }



    /**
     * Funcion que permite saber si realiza cruce automatico de la factura y la orden de compra
     * @param connEmp
     * @param connCos
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @return true = si realiza cruce  <br>  false= no realiza cruce
     */
    public boolean _getSiHaceCruceAuto(java.sql.Connection connEmp, java.sql.Connection connCos, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
        if(connEmp!=null){
         if(connCos!=null){
            stmLoc=connEmp.createStatement();

            strSql="select  a6.co_emp, a6.co_loc, a6.co_tipdoc, a6.co_doc,  a6.co_emprel, a6.co_locrel, a6.co_tipdocrel, a6.co_docrel "
            + " from tbm_cabmovinv as a "
            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
            + " inner join tbm_inv as ser on (ser.co_emp=a1.co_emp and ser.co_itm=a1.co_itm ) "
            + " inner join tbm_detsoldevven as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsoldevven and a2.co_tipdoc=a1.co_tipdocrelsoldevven and a2.co_doc=a1.co_docrelsoldevven and a2.co_reg=a1.co_regrelsoldevven ) "
            + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )  "
            + " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc )  "
            + " inner join tbr_detmovinv as a5 on (a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=a3.co_reg "
            + " and  a5.co_tipdocrel=2 )  "
            + " inner join tbr_detmovinvotrbasdat as a6 on (a6.co_emp=a5.co_emprel and a6.co_loc=a5.co_locrel and a6.co_tipdoc=a5.co_tipdocrel and a6.co_doc=a5.co_docrel and a6.co_reg=a5.co_regrel )  "
            + " where a.co_emp="+intCodEmp+"  and a.co_loc="+intCodLoc+"  and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_itmserpro='S'  and  a4.st_itmserpro = 'P' and ser.st_ser='S'   "
            + " group by a6.co_emp, a6.co_loc, a6.co_tipdoc, a6.co_doc,  a6.co_emprel, a6.co_locrel, a6.co_tipdocrel, a6.co_docrel ";
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()){

               if(_getExitPagoDoc(connEmp, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") )){  
                if(_getExitPagoDoc(connCos, rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel") )){  

                    dblValFacVen=_cargarValDoc(connCos, rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel") );
                    dblValFacCom=_cargarValDoc(connEmp, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") );
                    blnRes = true;
               }}

            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;

     }}}catch(java.sql.SQLException e){ blnRes=false; }
       catch(Exception e){  blnRes=false; }
    return blnRes;
    }



    private boolean _getExitPagoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    boolean blnRes=true;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();

            strSql="select  co_reg, mo_pag  from tbm_pagmovinv  "
            + " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C') "
            + " and abs(nd_abo) > 0  ";
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()){
                 blnRes=false;
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;

     }}catch(java.sql.SQLException e){ blnRes=false; }
       catch(Exception e){  blnRes=false; }
    return blnRes;
    }



    private double _cargarValDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    double dblValDoc=0;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();

            strSql="select  sum(abs(mo_pag)) as valdoc  from tbm_pagmovinv  "
            + " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C') "
            + "  ";
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()){
                 dblValDoc= objUti.redondear(rstLoc.getDouble("valdoc"),2);
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;

     }}catch(java.sql.SQLException e){ System.out.println("erro"+e.toString() ); }
       catch(Exception e){ System.out.println("erro"+e.toString() ); }
    return dblValDoc;
    }


    /**
     * Almacena un archivo de txt el proceso a ejecutarse.
     * @param strMenArchHis
     */
    public void almacenarArchHis(String strMenArchHis){
     try {

           java.util.Date objFec = new java.util.Date();
           java.util.Calendar fecha = java.util.Calendar.getInstance();
           int intHora = fecha.getTime().getHours();
           int intMin = fecha.getTime().getMinutes();
           int intSec = fecha.getTime().getSeconds();
           String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));


          java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(strNomArchHis, true));
          java.io.PrintWriter salida = new java.io.PrintWriter(bw);
          salida.println( strHora+" "+strMenArchHis );
          salida.close();
      }catch(java.io.IOException Ext ){ System.out.println(""+Ext ); }
    }

    public boolean enviarCorreo(String message ){
        boolean blnRes=false;
        boolean htmlFormat = false;
        String strSQL="";
        String CC="", CCO="";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;  
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_loc="+intCodLocGrp+" AND co_not="+intCodCfgCorEleCfgGen+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem"); // "zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    toAddres = strCorEleTo;
                    puerto = rstLoc.getString("ne_pueSerCor");
                    if(sendMail(server, userName, password, fromAddres, toAddres, CC, CCO, htmlFormat, "SafSer05", message)){
                        blnRes=true;
                    }                    
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;            
        }
        catch (java.sql.SQLException ex) {
            System.err.println("ERROR " + ex.toString());
            blnRes=false;
        }  
        catch (Exception e) {  
            System.err.println("ERROR "+ e.toString());
            blnRes=false;
        } 
        return blnRes;
    }
    
    
    public boolean sendMail(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) 
    {
        boolean blnRes=false;
        try{
           Properties props = System.getProperties();
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", puerto);
           props.put("mail.smtp.auth", "true");  

            // Get session
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() { 
                        return new PasswordAuthentication(userName, password);
                    }
                }
            );
            
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject); 

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }    



}
