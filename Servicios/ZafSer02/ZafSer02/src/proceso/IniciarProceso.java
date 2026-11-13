/*
 * To change this template, choose Tools | tbm_sercliserloc
 * and open the template in the editor.
 */

package proceso;

import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.Vector;
/**
 *
 * @author jayapata
 */
public class IniciarProceso {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    public ZafTblMod objTblMod1, objTblMod2;
    String strFechaCorte="";
    String strNomArchHis="";
    String strDirSis="";
    String strFormatDate ="d/m/y";
    public Librerias.ZafAsiDia.ZafAsiDia objAsiDia;

    private Vector vecCab;
    final int INT_TBL_COEMP=0;
    final int INT_TBL_COLOC=1;
    final int INT_TBL_COTDO=2;
    final int INT_TBL_CODOC=3;
    final int INT_TBL_FEDOC=4;
    final int INT_TBL_NUDOC=5;
    final int INT_TBL_NDTOT=6;

    
    private javax.swing.JPanel PanDia;
    private javax.swing.JPanel PanGen;
    private javax.swing.JPanel PanSql;
    private javax.swing.JPanel PanSql1;
    private javax.swing.JPanel PanSql2;
    private javax.swing.JPanel PanSql3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panPag;
    private javax.swing.JPanel panPag1;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnForPag1;
    public javax.swing.JTable tblCotForPag;
    public javax.swing.JTable tblOrdComForPag;
    public javax.swing.JTable tblDos;
    public javax.swing.JTable tblUno;
    public javax.swing.JTextArea txt;
    public javax.swing.JTextArea txtSql;
    public javax.swing.JTextArea txtSqlDia;
    public javax.swing.JTextArea txtSqlUpTbl;
    public javax.swing.JTextArea txtSqlUniDoc;
    public javax.swing.JTextArea txtSqlUptCC;  // Contiene query que actualiza las cuentas contables.
   
    public IniciarProceso(Librerias.ZafParSis.ZafParSis obj, String strFecCorte, String strNomArchHis, String strDirSis ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        initComponents();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.strFechaCorte=strFecCorte;
        this.strNomArchHis=strNomArchHis;
        this.strDirSis = strDirSis;

         objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
         PanDia.add(objAsiDia,java.awt.BorderLayout.CENTER);


        ConfiguraTblUno();
        ConfiguraTblDos();

        
      }catch (CloneNotSupportedException e){  System.out.println("Error 2 "+e.toString() );  }
    }


  public void arrancarProceso(){

     arrancaProceso th = new arrancaProceso(this, strFechaCorte, strNomArchHis, strDirSis );
     th.start();
      
  }



   public int [] _getFecha(String Fecha){
        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(Fecha,"/");
        int fec[] = new int[3] ;
        char formato[]  =  strFormatDate.replaceAll("/", "").toCharArray();

         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){
             String  strDate = MiToken.nextToken().toString();
             if(strDate.trim().equals("")){
                fec = null ;
                return fec;
             }
             switch (formato[i]){
                 case 'd':
                     fec[0] = Integer.parseInt(strDate.trim());
                     break;
                 case 'm':
                     fec[1] = Integer.parseInt(strDate.trim());
                     break;
                 case 'y':
                     fec[2] = Integer.parseInt(strDate.trim());
                     break;
             }
         }
        return fec;
    }

public String  _setFechaText(int Dia, int Mes, int Anio){
        String strFecha = "", strDia,strMes,strAno ;

        strDia=(Dia<10)?"0"+Dia:""+Dia;
        strMes=(Mes<10)?"0"+Mes:""+Mes;
        //strAno="" + (1900 + Anio);
        strAno="" + Anio;


        java.util.StringTokenizer MiToken = new java.util.StringTokenizer(strFormatDate,"/");

         for(int i = 0 ; MiToken.hasMoreTokens(); i++ ){
             String  strLetra = MiToken.nextToken().toString();
                    if(strLetra.equals("d"))
                        strFecha =  strFecha + strDia + "/";
                    if(strLetra.equals("m"))
                        strFecha =  strFecha + strMes + "/";
                    if(strLetra.equals("y"))
                        strFecha =  strFecha + strAno + "/";
         }

        //strFecha= strDia + "/" + strMes + "/" + strAno;
       return strFecha.substring(0,10);
    }

  private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanGen = new javax.swing.JPanel();
        PanDia = new javax.swing.JPanel();
        panPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblCotForPag = new javax.swing.JTable();
        panPag1 = new javax.swing.JPanel();
        spnForPag1 = new javax.swing.JScrollPane();
        tblOrdComForPag = new javax.swing.JTable();
        PanSql = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        PanSql1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSql = new javax.swing.JTextArea();
        PanSql2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSqlDia = new javax.swing.JTextArea();
        PanSql3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtSqlUpTbl = new javax.swing.JTextArea();
        tblUno = new javax.swing.JTable();
        tblDos = new javax.swing.JTable();
        txtSqlUniDoc = new javax.swing.JTextArea();
        txtSqlUptCC = new javax.swing.JTextArea();
        
        PanGen.setLayout(null);

        jTabbedPane1.addTab("General", PanGen);

        PanDia.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Diario", PanDia);

        panPag.setLayout(new java.awt.BorderLayout());

        tblCotForPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "Retención", "Monto de Pago", "Días de gracia", "CodRet", "tx_sop"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCotForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCotForPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblCotForPag);

        panPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Pago", panPag);

        panPag1.setLayout(new java.awt.BorderLayout());

        tblUno.setModel(new javax.swing.table.DefaultTableModel(
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
        
        tblDos.setModel(new javax.swing.table.DefaultTableModel(
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

        tblOrdComForPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "% Retención", "Monto de Pago", "Días de gracia", "CodRet", "Cod.Sri.", "tx_apliret"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrdComForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblOrdComForPag.setColumnSelectionAllowed(true);
        spnForPag1.setViewportView(tblOrdComForPag);

        panPag1.add(spnForPag1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Pago", panPag1);

        PanSql.setLayout(new java.awt.BorderLayout());

        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        PanSql.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("SQL", PanSql);

        PanSql1.setLayout(new java.awt.BorderLayout());

        txtSql.setColumns(20);
        txtSql.setRows(5);
        jScrollPane2.setViewportView(txtSql);

        PanSql1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("SQL", PanSql1);

        PanSql2.setLayout(new java.awt.BorderLayout());

        txtSqlDia.setColumns(20);
        txtSqlDia.setLineWrap(true);
        txtSqlDia.setRows(5);
        txtSqlDia.setText("/* CASO QUE VENTA DE TUVAL A CASTEK */\nUPDATE tbm_detdia SET co_cta=2690  "
                + "FROM ( \n\nselect * from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli=603  \n"
                + "and a1.co_cta= 29\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n"
                + "/* CASO QUE VENTA DE TUVAL A DIMULTI */"
                + "\n"
                + "UPDATE tbm_detdia SET co_cta=2691  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and "
                + "a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli= 1039  \n"
                + "and a1.co_cta= 29\n) as x \n\n"
                + ") AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;\n"
                + "/* CASO QUE COMPRA DE TUVAL A CASTEK */\n"
                + "UPDATE tbm_detdia SET co_cta=2692  "
                + "FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and a.co_cli=603  \n"
                + "and a1.co_cta= 733\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n"
                + "/* CASO QUE COMPRA DE TUVAL A DOMULTI */\n"
                + "UPDATE tbm_detdia SET co_cta=2693  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=1039  \nand a1.co_cta= 733\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "/* CASO QUE VENTA DE CASTEK A TUVAL */"
                + "\nUPDATE tbm_detdia SET co_cta=1186  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' "
                + "and a.co_cli=2854  \nand a1.co_cta= 24\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n/* "
                + "CASO QUE VENTA DE CASTEK A DIMULTI */\nUPDATE tbm_detdia SET co_cta=1187  "
                + "FROM ( \n\nselect * from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and "
                + "a.fe_doc >= '2011-01-01' and a.co_cli=789  \n"
                + "and a1.co_cta= 24\n"
                + ") as x \n\n"
                + ") AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;"
                + "\n\n/* CASO QUE COMPRA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=1188  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and a.co_cli=2854  \n"
                + "and a1.co_cta= 154\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and "
                + "tbm_detdia.co_tipdoc=x.co_tipdoc and tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n"
                + "/* CASO QUE COMPRA DE CASTEK A DIMULTI */\n"
                + "UPDATE tbm_detdia SET co_cta=1189  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and a.co_cli=789  \n"
                + "and a1.co_cta= 154\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n\n\n;\n\n\n\n\n\n\n/* "
                + "CASO QUE VENTA DE DIMULTI A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=2128  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc "
                + "and a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (1,228,3) and "
                + "a.fe_doc >= '2011-01-01' and a.co_cli=3117  \nand a1.co_cta= 28\n) as x \n\n"
                + ") AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n"
                + "/* CASO QUE VENTA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=2129  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli=498  \n"
                + "and a1.co_cta= 28\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;\n\n\n/* "
                + "CASO QUE COMPRA DE DIMULTI A TUVAL */\nUPDATE tbm_detdia SET co_cta=2130  FROM ( \n\n"
                + "select * from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' "
                + "and a.co_cli=3117  \n"
                + "and a1.co_cta= 688\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n"
                + "/* CASO QUE COMPRA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=2131  FROM ( \n\n"
                + "select * from (\nselect a1.* from  "
                + "tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) "
                + "and a.fe_doc >= '2011-01-01' and a.co_cli=498  \n"
                + "and a1.co_cta= 688\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n;\n\n"
                + "UPDATE tbm_detdia SET co_cta= 545  "
                + "FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join "
                + "tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli in ( 789 , 2854 )  \n"
                + "and a1.co_cta in (1163, 1037 , 551 )\n) as x \n\n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n\n\n;\n\n\n"
                + "update tbm_pagmovinv set  tx_codsri='312' where co_emp=1 and co_loc=5 and co_tipdoc IN (1,228) and nd_porret != 0 and  "
                + "fe_ven >= '2010-05-01' and tx_codsri is null\n\n;\n"
                + "update tbm_pagmovinv set  tx_codsri='312' where co_emp=2 and co_loc=5 and co_tipdoc IN (1,228) and nd_porret != 0 and  "
                + "fe_ven >= '2010-05-01' and tx_codsri is null\n\n;\nupdate tbm_pagmovinv set  tx_codsri='312' "
                + "where co_emp=4 and co_loc=2 and co_tipdoc IN (1,228) and nd_porret != 0 and  fe_ven >= '2010-05-01' and "
                + "tx_codsri is null\n\n\n\n\n\n");
        jScrollPane3.setViewportView(txtSqlDia);

        PanSql2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("SQL", PanSql2);

        PanSql3.setLayout(new java.awt.BorderLayout());

        txtSqlUpTbl.setColumns(20);
        txtSqlUpTbl.setLineWrap(true);
        txtSqlUpTbl.setRows(5);
        txtSqlUpTbl.setText("\n"
                + "update  tbm_cabmovinv set  st_regrep='P'  where co_emp = 4  and co_loc= 2 and st_regrep='I' \n\n;\n"
                + "update  tbm_cabmovinv set  st_regrep='P'  where co_emp = 2  and co_loc= 5 and st_regrep='I' \n\n;\n"
                + "update  tbm_detmovinv set  st_regrep='P'  where co_emp = 4  and co_loc= 2 and st_regrep='I' \n\n;\n"
                + "update  tbm_detmovinv set  st_regrep='P'  where co_emp = 2  and co_loc= 5 and st_regrep='I' \n\n\n\n");
        jScrollPane4.setViewportView(txtSqlUpTbl);


        txtSqlUptCC.setColumns(20);
        txtSqlUptCC.setLineWrap(true);
        txtSqlUptCC.setRows(5);
        txtSqlUptCC.setText("/* CASO QUE COMPRA DE TUVAL A DOMULTI  PROBLEMAS IVA EN COMPRA */\n"
                + "UPDATE tbm_detdia SET co_cta=584  FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01'  \n"
                + "and a1.co_cta= 2949\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and "
                + "tbm_detdia.co_tipdoc=x.co_tipdoc and tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n"
                + "/* CASO QUE COMPRA DE TUVAL A DOMULTI */\nUPDATE tbm_detdia SET co_cta=733  "
                + "FROM ( \n\n"
                + "select * "
                + "from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01'  \n"
                + "and a1.co_cta= 2950\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n"
                + "/* CASO QUE COMPRA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=94  "
                + "FROM ( \n\n"
                + "select * "
                + "from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01'  \n"
                + "and a1.co_cta= 1250\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n"
                + "/* CASO QUE COMPRA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=154  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and "
                + "a1.co_tipdoc=a.co_tipdoc and a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and "
                + "a.fe_doc >= '2011-01-01'  \n"
                + "and a1.co_cta= 1251"
                + "\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\t\n\n"
                + "/* CASO QUE COMPRA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=545  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' \n"
                + "and a1.co_cta= 2226\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n;\n"
                + "/* CASO QUE COMPRA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=688  "
                + "FROM ( \n\n"
                + "select * from (\nselect a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' \n"
                + "and a1.co_cta= 2227\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n"
                + "/********************************************************************************/\n\n;\n\n"
                + "/* CASO QUE VENTA DE TUVAL A CASTEK */\n"
                + "UPDATE tbm_detdia SET co_cta=2690  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and "
                + "a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli=603  \n"
                + "and a1.co_cta= 29\n) as x \n\n) AS x \nWHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and "
                + "tbm_detdia.co_tipdoc=x.co_tipdoc and tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n\n\n"
                + "/* CASO QUE VENTA DE TUVAL A DIMULTI */\n"
                + "UPDATE tbm_detdia SET co_cta=2691  FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli= 1039  \nand a1.co_cta= 29\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;\n"
                + "/* CASO QUE COMPRA DE TUVAL A CASTEK */\n"
                + "UPDATE tbm_detdia SET co_cta=2692  "
                + "FROM ( \n\nselect * "
                + "from (\nselect a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=603  \nand a1.co_cta= 733\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n\n\n"
                + "/* CASO QUE COMPRA DE TUVAL A DOMULTI */\n"
                + "UPDATE tbm_detdia SET co_cta=2693  "
                + "FROM ( \n\nselect * from (\nselect a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=1 and a.co_loc=5 and a.co_tipdoc in (2,4) and "
                + "a.fe_doc >= '2011-01-01' and a.co_cli=1039  \nand a1.co_cta= 733\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n\n\n\n"
                + "/* CASO QUE VENTA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=1186  "
                + "FROM ( \n\nselect * from (\nselect a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=2854  \nand a1.co_cta= 1156\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n\n"
                + "/* CASO QUE VENTA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=1186  FROM ( \n\nselect * "
                + "from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=2854  \nand a1.co_cta= 24\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n"
                + "/* CASO QUE VENTA DE CASTEK A DIMULTI */\nUPDATE tbm_detdia SET co_cta=1187  "
                + "FROM ( \n\nselect * from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=789  \nand a1.co_cta= 24\n) as x \n\n) AS x \nWHERE tbm_detdia.co_emp=x.co_emp and "
                + "tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and tbm_detdia.co_dia=x.co_dia and "
                + "tbm_detdia.co_reg=x.co_reg\n\n;\n\n/* "
                + "CASO QUE COMPRA DE CASTEK A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=1188  "
                + "FROM ( \n\nselect * "
                + "from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=2854  \nand a1.co_cta= 154\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n\n"
                + "/* CASO QUE COMPRA DE CASTEK A DIMULTI */\nUPDATE tbm_detdia SET co_cta=1189  "
                + "FROM ( \n\nselect * "
                + "from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=789  \nand a1.co_cta= 154\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;\n\n\n"
                + "/* CASO QUE VENTA DE DIMULTI A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=2128  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\n"
                + "where a.co_emp=4 and a.co_loc=2 and "
                + "a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli=3117  \n"
                + "and a1.co_cta= 28\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n"
                + "/* CASO QUE VENTA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=2129  FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* "
                + "from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and "
                + "a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and a.co_cli=498  \n"
                + "and a1.co_cta= 28\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n;\n\n\n"
                + "/* CASO QUE COMPRA DE DIMULTI A TUVAL */\n"
                + "UPDATE tbm_detdia SET co_cta=2130  "
                + "FROM ( \n\nselect * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) and "
                + "a.fe_doc >= '2011-01-01' and a.co_cli=3117  \nand a1.co_cta= 688\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n;\n/* "
                + "CASO QUE COMPRA DE DIMULTI A CASTEK   */\n"
                + "UPDATE tbm_detdia SET co_cta=2131  FROM ( \n\n"
                + "select * from (\nselect a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=4 and a.co_loc=2 and a.co_tipdoc in (2,4) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli=498  \nand a1.co_cta= 688\n) as x \n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n;\n\n"
                + "UPDATE tbm_detdia SET co_cta= 545  FROM ( \n\n"
                + "select * from (\n"
                + "select a1.* from  tbm_cabmovinv as a \n"
                + "inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and "
                + "a1.co_dia=a.co_doc)\nwhere a.co_emp=2 and a.co_loc=5 and a.co_tipdoc in (1,228,3) and a.fe_doc >= '2011-01-01' and "
                + "a.co_cli in ( 789 , 2854 )  \nand a1.co_cta in (1163, 1037 , 551 )\n) as x \n\n\n) AS x \n"
                + "WHERE tbm_detdia.co_emp=x.co_emp and tbm_detdia.co_loc=x.co_loc and tbm_detdia.co_tipdoc=x.co_tipdoc and "
                + "tbm_detdia.co_dia=x.co_dia and tbm_detdia.co_reg=x.co_reg\n\n\n;\n"
                + "update tbm_pagmovinv set  tx_codsri='312' where co_emp=1 and co_loc=5 and co_tipdoc IN (1,228) and nd_porret != 0 and  "
                + "fe_ven >= '2011-01-01' and tx_codsri is null\n\n;\n"
                + "update tbm_pagmovinv set  tx_codsri='312' where co_emp=2 and co_loc=5 and co_tipdoc IN (1,228) and nd_porret != 0 and  "
                + "fe_ven >= '2011-01-01' and tx_codsri is null\n\n;\n"
                + "update tbm_pagmovinv set  tx_codsri='312' where co_emp=4 and co_loc=2 and co_tipdoc IN (1,228) and nd_porret != 0 and  "
                + "fe_ven >= '2011-01-01' and tx_codsri is null\n\n\n\n\t");
        
  

        PanSql3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("SQL", PanSql3);

       
    }



  private boolean ConfiguraTblUno(){
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_COEMP,"Cod.Emp");
            vecCab.add(INT_TBL_COLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_COTDO,"Cod.TipDoc.");
            vecCab.add(INT_TBL_CODOC,"Cod.Doc.");
            vecCab.add(INT_TBL_FEDOC,"FecDoc.");
            vecCab.add(INT_TBL_NUDOC,"Num.Doc.");
            vecCab.add(INT_TBL_NDTOT,"Total.");

            objTblMod1=new ZafTblMod();
            objTblMod1.setHeader(vecCab);
            tblUno.setModel(objTblMod1);
            //Configurar JTable: Establecer tipo de selección.
            tblUno.setRowSelectionAllowed(true);
            tblUno.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblUno.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblUno.getTableHeader().setReorderingAllowed(false);


        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Error ConfiguraTblUno "+e.toString() );
        }
        return blnRes;
    }

  private boolean ConfiguraTblDos(){
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_COEMP,"Cod.Emp");
            vecCab.add(INT_TBL_COLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_COTDO,"Cod.TipDoc.");
            vecCab.add(INT_TBL_CODOC,"Cod.Doc.");
            vecCab.add(INT_TBL_FEDOC,"FecDoc.");
            vecCab.add(INT_TBL_NUDOC,"Num.Doc.");
            vecCab.add(INT_TBL_NDTOT,"Total.");

            objTblMod2=new ZafTblMod();
            objTblMod2.setHeader(vecCab);
            tblDos.setModel(objTblMod2);
            //Configurar JTable: Establecer tipo de selección.
            tblDos.setRowSelectionAllowed(true);
            tblDos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDos.getTableHeader().setReorderingAllowed(false);


        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Error ConfiguraTblDos "+e.toString() );
        }
        return blnRes;
    }




}
