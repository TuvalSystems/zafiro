/*
 * To change this template, choose Tools | Templates  st_bie
 * and open the template in the editor.
 */

package proceso;
import Librerias.ZafPulFacEle.ZafPulFacEle;                                     //José Marín 6/Nov/2014

/**
 *
 * @author jayapata
 */
public class arrancaProceso extends Thread {

    private IniciarProceso pantalla;
    private String strFecDocBus="";
    private int intNumLimImp=200;
    String strNomArchHis="";
    String strDirSis="";
    procesoClass objClass;
    private int intConnRem=0;
    private String strConnRem="";
    private ZafPulFacEle objPulFacEle;                                          //  José Marín 6/Nov/2014

    public arrancaProceso(IniciarProceso pantalla, String strFecCorte, String strNomArchHis, String strDirSis ) {
      try{
      this.pantalla = pantalla;
      this.strFecDocBus=strFecCorte;
      this.strNomArchHis = strNomArchHis;
      this.strDirSis = strDirSis;
      objClass = new procesoClass(pantalla.objZafParSis, null, strNomArchHis  );
     }catch(Exception e){  mensaje("Error ! arrancaProceso "+e.toString() ); }

    }

    @Override
    public void run() {
      try{

          if(obtenerEstArch()){
              procesaTuv();
              procesaDim();
              ProcesadoCtQ();
              ProcesadoCtM();
              ProcesadoCSD();
              ProcesadoCCU();/* José Marín M. 10/Nov/2014 */
              ProcesadoTIN();/* José Marín M. 28/Ene/2015 Tuval - Inmaconsa*/
              ProcesadoDIN();/* José Marín M. 28/Ene/2015 Dimulti - Inmaconsa*/  
              ProcesadoCtQuIn();/* José Marín M. 25/Sep/2015 Castek Quito- Inmaconsa*/
              ProcesadoCtMnIn();/* José Marín M. 25/Sep/2015 Castek Manta- Inmaconsa*/
              ProcesadoCtStIn();/* José Marín M. 25/Sep/2015 Castek Santo Domingo- Inmaconsa*/
              ProcesadoCtCuIn();/* José Marín M. 25/Sep/2015 Castek Cuenca- Inmaconsa*/
              if(obtenerEstArch()){
                    objClass.enviarCorreo(" Proceso entre compañias se realizo con exito.. ");
                    procesarUniDocRel();
                    System.out.println("Realiza Unir Documentos.... ");
                    enviarPulsoFacturacionElectronica();
              }
          }
          else{
           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.load(fis);
           fis.close();
           if(!proArc.getProperty("ProcesadoTuv").toString().equals("OK")){
               this.strFecDocBus = proArc.getProperty("UltFecCorTuv");
               procesaTuv();
           }
           if(!proArc.getProperty("ProcesadoDim").toString().equals("OK")){
              this.strFecDocBus = proArc.getProperty("UltFecCorDim");
              procesaDim();
           }
           if(!proArc.getProperty("ProcesadoCtQ").toString().equals("OK")){
              this.strFecDocBus = proArc.getProperty("UltFecCorCtQ");
              ProcesadoCtQ();
           }
           if(!proArc.getProperty("ProcesadoCtM").toString().equals("OK")){
              this.strFecDocBus = proArc.getProperty("UltFecCorCtM");
              ProcesadoCtM();
           }
           if(!proArc.getProperty("ProcesadoCSD").toString().equals("OK")){
             this.strFecDocBus = proArc.getProperty("UltFecCorCSD");
             ProcesadoCSD();
           }

           
           if(!proArc.getProperty("ProcesadoCCU").toString().equals("OK")){/* José Marín M. 10/Nov/2014 */
             this.strFecDocBus = proArc.getProperty("UltFecCorCCU");
             ProcesadoCCU();
           }/* José Marín M. 10/Nov/2014 */
           
           
           if(!proArc.getProperty("ProcesadoDIN").toString().equals("OK")){/* José Marín M. 28/Ene/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorDIN");
             ProcesadoDIN();
           }/* José Marín M. 28/Ene/2015 */
           if(!proArc.getProperty("ProcesadoTIN").toString().equals("OK")){/* José Marín M. 28/Ene/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorTIN");
             ProcesadoTIN();
           }/* José Marín M. 28/Ene/2015 */
           
           
           if(!proArc.getProperty("ProcesadoCtQuIn").toString().equals("OK")){/* José Marín M. 25/Sep/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorCtQuIn");
             ProcesadoCtQuIn();
           }/* José Marín M. 25/Sep/2015 */
           if(!proArc.getProperty("ProcesadoCtMnIn").toString().equals("OK")){/* José Marín M. 25/Sep/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorCtMnIn");
             ProcesadoCtMnIn();
           }/* José Marín M. 25/Sep/2015 */
           if(!proArc.getProperty("ProcesadoCtStIn").toString().equals("OK")){/* José Marín M. 25/Sep/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorCtStIn");
             ProcesadoCtStIn();
           }/* José Marín M. 25/Sep/2015 */
           if(!proArc.getProperty("ProcesadoCtCuIn").toString().equals("OK")){/* José Marín M. 25/Sep/2015 */
             this.strFecDocBus = proArc.getProperty("UltFecCorCtCuIn");
             ProcesadoCtCuIn();
           }/* José Marín M. 25/Sep/2015 */
           
           
           proArc=null;


            if(obtenerEstArch()){
                objClass.enviarCorreo(" Proceso entre compañias se realizo con exito.. ");
                procesarUniDocRel();
                System.out.println("Realiza Unir Documentos.... ");
                enviarPulsoFacturacionElectronica();
            }

         }

      }catch(Exception e){  mensaje("Error ! verifique datos "+e.toString() ); }
    }


    private void procesarUniDocRel(){
        try{
           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.load(fis);
           fis.close();

           String strFecDes = proArc.getProperty("FecDesUniDoc");
           String strFecHas = proArc.getProperty("FecHasUniDoc");

           procesoClass objUniDocClass = new procesoClass(pantalla.objZafParSis, pantalla, strNomArchHis );

           boolean bln1 = objUniDocClass.procesarUnirDocTuv( strFecDes, strFecHas);
           boolean bln2 = objUniDocClass.procesarUnirDocDim( strFecDes, strFecHas);
           boolean bln3 = objUniDocClass.procesarUnirDocCas( strFecDes, strFecHas);
           
           if( bln1 && bln2 && bln3 ){
               this.actualizaEstArch("FecDesUniDoc", strFecHas);
           }

           objUniDocClass=null;

           proArc=null;
       }catch(Exception e){  mensaje("Error ! procesarUniDocRel "+e.toString() ); }
    }

    private void procesaTuv(){
        try{
            System.out.println("procesaTuv");
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Tuval..");
          pantalla.objZafParSis.setCodigoEmpresa(1);
          pantalla.objZafParSis.setCodigoLocal(4);
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoTuv", "UltFecCorTuv");
        }catch(Exception e){  mensaje("Error ! procesaTuv "+e.toString() ); }
    }

    private void procesaDim(){
        try{
            System.out.println("procesaDim");
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Dimulti..");
          pantalla.objZafParSis.setCodigoEmpresa(4);
          pantalla.objZafParSis.setCodigoLocal(3);
//          strConnRem="jdbc:postgresql://172.16.3.1:5432/ZafDimulti";
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoDim", "UltFecCorDim");
        }catch(Exception e){  mensaje("Error ! procesaDim  "+e.toString() ); }
    }
  
    private void ProcesadoCtQ(){
       try{
           System.out.println("ProcesadoCtQ");
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Castek Quito..");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(1);
          //strConnRem="jdbc:postgresql://172.16.4.1:5432/ZafCastek";
          //intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtQ", "UltFecCorCtQ");
        }catch(Exception e){  mensaje("Error ! ProcesadoCtQ "+e.toString() ); }
    }  

    private void ProcesadoCtM(){
       try{
           System.out.println("ProcesadoCtM");
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Castek Manta..");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(4);
//          strConnRem="jdbc:postgresql://172.16.6.1:5432/ZafCastek";
//          intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtM", "UltFecCorCtM");
        }catch(Exception e){  mensaje("Error ! ProcesadoCtM "+e.toString() ); }
    }

    private void ProcesadoCSD(){
       try{
           System.out.println("ProcesadoCSD");
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Castek SantoDomingo..");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(6);
          //strConnRem="jdbc:postgresql://172.16.7.1:5432/ZafCastek";
          //intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCSD", "UltFecCorCSD");
        }catch(Exception e){  mensaje("Error ! ProcesadoCSD "+e.toString() ); }
    }
 /* José Marín M. 10/Nov/2014 */
    private void ProcesadoCCU(){
       try{
           System.out.println("ProcesadoCCU -> " );
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Castek Cuenca..");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(10);
          //strConnRem="jdbc:postgresql://172.16.7.1:5432/ZafCastek";
          //intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCCU", "UltFecCorCCU");
        }catch(Exception e){  mensaje("Error ! ProcesadoCCU "+e.toString() ); }
    }
/* José Marín M. 10/Nov/2014 */
    
    
         /* José Marín M. 28/Ene/2015*/
    private void ProcesadoTIN(){  /* TUVAL - INMACONSA */
       try{
           System.out.println("ProcesadoTIN -> " );
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Tuval Inmaconsa..");
          pantalla.objZafParSis.setCodigoEmpresa(1);
          pantalla.objZafParSis.setCodigoLocal(10);
          //strConnRem="jdbc:postgresql://172.16.7.1:5432/ZafCastek";
          //intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoTIN", "UltFecCorTIN");
        }catch(Exception e){  mensaje("Error ! ProcesadoTIN "+e.toString() ); }
    }
/* José Marín M. 28/Ene/2015 */
    
/* José Marín M. 28/Ene/2015*/
    private void ProcesadoDIN(){  /* DIMULTI - INMACONSA */
       try{
           System.out.println("ProcesadoDIN -> " );
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("Dimulti Inmaconsa..");
          pantalla.objZafParSis.setCodigoEmpresa(4);
          pantalla.objZafParSis.setCodigoLocal(10);
          //strConnRem="jdbc:postgresql://172.16.7.1:5432/ZafCastek";
          //intConnRem=1;
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoDIN", "UltFecCorDIN");
        }catch(Exception e){  mensaje("Error ! ProcesadoDIN "+e.toString() ); }
       
       
    }

    
    
    /* José Marín M. 25/Sep/2015*/
    private void ProcesadoCtQuIn(){  /* Castek Quito - INMACONSA */
       try{
           System.out.println("ProcesadoCtQuIn -> " );
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("CASTEK - Quito (Inmaconsa)");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(11);
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtQuIn", "UltFecCorCtQuIn");
        }catch(Exception e){  mensaje("Error ! ProcesadoCtQuIn "+e.toString() ); }
}
    /* José Marín M. 25/Sep/2015*/
    
    /* José Marín M. 25/Sep/2015*/
    private void ProcesadoCtMnIn(){  /* Castek Quito - INMACONSA */
       try{
            System.out.println("ProcesadoCtMnIn -> " );
            pantalla.txt.setText("");
            pantalla.txtSql.setText("");
            pantalla.objZafParSis.setNombreEmpresa("CASTEK - Manta (Inmaconsa)");
            pantalla.objZafParSis.setCodigoEmpresa(2);
            pantalla.objZafParSis.setCodigoLocal(12);
            intConnRem=0;
            iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtMnIn", "UltFecCorCtMnIn");
        }
       catch(Exception e){  
            mensaje("Error ! ProcesadoCtMnIn "+e.toString() ); 
       }
}
    /* José Marín M. 25/Sep/2015*/
    
        /* José Marín M. 25/Sep/2015*/
    private void ProcesadoCtStIn(){  /* Castek Quito - INMACONSA */
       try{
          System.out.println("ProcesadoCtStIn -> " );
          pantalla.txt.setText("");
          pantalla.txtSql.setText("");
          pantalla.objZafParSis.setNombreEmpresa("CASTEK - Santo Domingo (Inmaconsa)");
          pantalla.objZafParSis.setCodigoEmpresa(2);
          pantalla.objZafParSis.setCodigoLocal(13);
          intConnRem=0;
          iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtStIn", "UltFecCorCtStIn");
        }catch(Exception e){  mensaje("Error ! ProcesadoCtStIn "+e.toString() ); }
}
    /* José Marín M. 25/Sep/2015*/
    
        /* José Marín M. 25/Sep/2015*/
    private void ProcesadoCtCuIn(){  /* Castek Quito - INMACONSA */
       try{
            System.out.println("ProcesadoCtCuIn -> " );
            pantalla.txt.setText("");
            pantalla.txtSql.setText("");
            pantalla.objZafParSis.setNombreEmpresa("CASTEK - Cuenca (Inmaconsa)");
            pantalla.objZafParSis.setCodigoEmpresa(2);
            pantalla.objZafParSis.setCodigoLocal(14);
            intConnRem=0;
            iniciarProcesoGenerarDatos(strFecDocBus, "ProcesadoCtCuIn", "UltFecCorCtCuIn");
        }catch(Exception e){  mensaje("Error ! ProcesadoCtCuIn "+e.toString() ); }
    }
    /* José Marín M. 25/Sep/2015*/
    

    private void actualizaEstArch(String StrKeyPro1, String strValue1, String StrKeyPro2, String strValue2){
        try{
   
           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.load(fis);
           fis.close();
           java.io.FileOutputStream fos=new java.io.FileOutputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.put(StrKeyPro1, strValue1);
           proArc.put(StrKeyPro2, strValue2);
           proArc.store(fos, null);
           fos.close();
           proArc=null;

        }catch(Exception e){  mensaje("Error ! actualizaEstArch "+e.toString() ); }
    }

    private void actualizaEstArch(String StrKeyPro1, String strValue1){
        try{

           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.load(fis);
           fis.close();
           java.io.FileOutputStream fos=new java.io.FileOutputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.put(StrKeyPro1, strValue1);
           proArc.store(fos, null);
           fos.close();
           proArc=null;

        }catch(Exception e){  mensaje("Error ! actualizaEstArch "+e.toString() ); }
    }


    private boolean obtenerEstArch(){
         boolean blnRes=false;
         try{

           java.util.Properties proArc=new java.util.Properties();
           java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/Servicios/ZafSer02/config/ZafSer02_Est_Emp.properties");
           proArc.load(fis);
           fis.close();


           if(proArc.getProperty("ProcesadoTuv").toString().equals("OK")){
            if(proArc.getProperty("ProcesadoDim").toString().equals("OK")){
             if(proArc.getProperty("ProcesadoCtQ").toString().equals("OK")){
              if(proArc.getProperty("ProcesadoCtM").toString().equals("OK")){
               if(proArc.getProperty("ProcesadoCSD").toString().equals("OK")){
                   if(proArc.getProperty("ProcesadoCCU").toString().equals("OK")){ /* JoséMario 11/Ago/2015 CUENCA */
                    if(proArc.getProperty("ProcesadoDIN").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Dimulti - Inmaconsa */
                        if(proArc.getProperty("ProcesadoTIN").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Tuval - Inmaconsa */
                            if(proArc.getProperty("ProcesadoCtQuIn").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Castek Quito - Inmaconsa */
                                if(proArc.getProperty("ProcesadoCtMnIn").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Castek Manta - Inmaconsa */
                                    if(proArc.getProperty("ProcesadoCtStIn").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Castek Santo Domingo - Inmaconsa */
                                        if(proArc.getProperty("ProcesadoCtCuIn").toString().equals("OK")){ /* JoséMario 11/Ago/2015 Castek Cuenca - Inmaconsa */
                                            blnRes=true;
                                        }
                                    }
                                }
                            }
                        }                  
            }}}}}}}
           proArc=null;
           
        }catch(Exception e){  mensaje("Error ! obtenerEstArch "+e.toString() ); }
      return blnRes;
     }


    private void iniciarProcesoGenerarDatos(String strFecha, String StrKeyPro1, String StrKeyPro2 ){
      java.sql.Connection conn=null;
      java.sql.Connection connRem=null;
      boolean estConRem=true;
      try{
        objClass.almacenarArchHis(strNomArchHis, " Inicia Empresa "+ pantalla.objZafParSis.getNombreEmpresa()+"   Fecha corte: "+ strFecha );
        conn=java.sql.DriverManager.getConnection(pantalla.objZafParSis.getStringConexion(), pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
        if(conn!= null){
          conn.setAutoCommit(false);

            
          if(intConnRem==1){
             connRem=java.sql.DriverManager.getConnection(strConnRem, pantalla.objZafParSis.getUsuarioBaseDatos(), pantalla.objZafParSis.getClaveBaseDatos() );
             if(connRem!= null){
               connRem.setAutoCommit(false);
             }else estConRem=false;
          }

     if(estConRem){
         System.out.println("INICIA DOCUMENTOS");
        arrancarFacVenFacCom obj01 = new arrancarFacVenFacCom(pantalla.objZafParSis, pantalla, strNomArchHis );
//        arrancarDevVenDevCom obj02 = new arrancarDevVenDevCom(pantalla.objZafParSis, pantalla, strNomArchHis  );
        procesoClass objtClass = new procesoClass(pantalla.objZafParSis, pantalla, strNomArchHis );

        String strTipoBus1=" AND a.co_usring NOT IN (1 ,38, 24 ) ";
        String strTipoBus2=" AND a.co_usring  IN (1 ,38, 24 ) ";

        if( obj01.realizarProcesoFacVen01(conn, strFecha, intNumLimImp, strTipoBus1 ) ){
         if( obj01.realizarProcesoFacVen01(conn, strFecha, intNumLimImp, strTipoBus2 ) ){
//          if( obj02.realizarProcesoDevvenCom(conn, strFecha, intNumLimImp ) ){
           if( objtClass._ejecutaSqlUpDel(conn) ){
            if( objtClass._ejecutaSqlDatNueRem(connRem, intConnRem ) ){
  
                 if(intConnRem==1) connRem.commit();
                 conn.commit();
                 objtClass.almacenarArchHis(strNomArchHis, "  --------------------------- Procesado con Exito OK.. " );
                 actualizaEstArch(StrKeyPro1, "OK", StrKeyPro2 , strFecha );
                 actualizaEstArch("FecHasUniDoc", strFecha );
                 
            }else{ conn.rollback(); if(intConnRem==1) connRem.rollback();  mensaje(" ****** Proceso no completado paso 5 .. "); actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha ); objtClass.enviarCorreo(" Error ..Proceso no completado paso 5. "); }
           }else{ conn.rollback();  mensaje("***** Proceso no completado paso 4 .. "); actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha ); objtClass.enviarCorreo(" Error ..Proceso no completado paso 4. ");System.out.println("No grabo 4"); }
//          }else{ conn.rollback();  mensaje(" ******Proceso no completado paso 3 .. "); actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha ); objtClass.enviarCorreo(" Error ..Proceso no completado paso 3. "); }
         }else{ conn.rollback();  mensaje("***** Proceso no completado paso 2 .. "); actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha ); objtClass.enviarCorreo(" Error ..Proceso no completado paso 2. "); }
        }else{ conn.rollback();  mensaje("***** Proceso no completado paso 1 .. "); actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha ); objtClass.enviarCorreo(" Error ..Proceso no completado paso 1. "); }

        if(intConnRem==1){
            connRem.close();
            connRem=null;
        }
        
     }else {
         mensaje(" Error coneccion remota... ");
         actualizaEstArch(StrKeyPro1, "NO", StrKeyPro2 , strFecha );
     }

        
        conn.close();
        conn=null;

      }}catch(java.sql.SQLException e){ mensaje("IniPro "+ e.toString());  }
    }

    

  private void mensaje(String strMes){
      //javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
     // obj.showMessageDialog( null, strMes, "Mensaje Zafiro..", javax.swing.JOptionPane.INFORMATION_MESSAGE );
      objClass.almacenarArchHis(strNomArchHis, strMes );
  }

// José Marín M. 6/Nov/2014 // Facturación Electronica
    private void enviarPulsoFacturacionElectronica() {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
    }
// José Marín M. 6/Nov/2014 // Facturación Electronica

}
