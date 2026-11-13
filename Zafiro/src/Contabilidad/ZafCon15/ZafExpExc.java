package Contabilidad.ZafCon15;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
import jxl.write.Number;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import jxl.write.NumberFormats;

/**
 *
 * @author Ingrid Lino
 */
public class ZafExpExc 
{
    //Constantes
    private final int INT_INC_CAB=6;
    
    //Variables
    private ZafUtil objUti;
    private File file;
    private JTable table;
    private String nombreTab;
    private WritableFont fuente;
    private Number numCol;
    private Label LblCol;
    private BigDecimal num;
    private String strNomEmp;
    private String strNumCta;
    private String strNomCta;
    private String strFecDes;
    private String strFecHas;
    private BigDecimal bgdSalAct; 
    private BigDecimal bgdSalAnt;  
    private BigDecimal bgdSalAcu;  
    private Label lblCab;
    private final WritableCellFormat doubleFormat = new WritableCellFormat (NumberFormats.FLOAT);
    private org.apache.poi.poifs.filesystem.POIFSFileSystem fs;

    public ZafExpExc(JTable table,File file,String nombreTab, String nomEmpresa, String numCta, String nomCta, String fecDes, String fecHas, BigDecimal salAct, BigDecimal salAnt, BigDecimal salAcu)
    {
        try
        {
            this.file=file; 
            this.table=table;
            this.nombreTab=nombreTab;
            strNomEmp=nomEmpresa;
            strNumCta=numCta;
            strNomCta=nomCta;
            strFecDes=fecDes;
            strFecHas=fecHas;
            bgdSalAct=salAct; 
            bgdSalAnt=salAnt; 
            bgdSalAcu=salAcu; 
            objUti=new ZafUtil();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(table, e);
        }
    }
        
    public boolean export()
    {
        boolean blnRes=false;
        try
        {
            try{
                if(file.exists())
                   file.delete();
                // A partir del objeto File creamos el fichero físicamente
                if (file.createNewFile()){
                    blnRes=true;
                }
                else{
                    blnRes=false;
                }
            }
            catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                blnRes=false;
            }

            DataOutputStream out=new DataOutputStream(new FileOutputStream(file));
            //WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableWorkbook w = Workbook.createWorkbook(out);
            WritableSheet s = w.createSheet(nombreTab, 0);

            //Cabecera
            //nombre de empresa
            lblCab = new Label(8, 0, strNomEmp);
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD)));
            s.addCell(lblCab);
            
            //titulo de cuenta
            lblCab = new Label(1, 1, "CUENTA:   " + strNumCta);
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
            s.addCell(lblCab);

            //titulo de nombre de cuenta
            lblCab = new Label(8, 1, "NOMBRE:   " + strNomCta);
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
            s.addCell(lblCab);

            //titulo de fecha desde
            lblCab = new Label(1, 2, "FECHA DESDE:   " + strFecDes);
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
            s.addCell(lblCab);

            //titulo de fecha hasta
            lblCab = new Label(8, 2, "FECHA HASTA:   " + strFecHas);
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
            s.addCell(lblCab);

            //titulo de saldo anterior
            lblCab = new Label(8, 3, "SALDO ANTERIOR: ");
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
            s.addCell(lblCab);
            //valor de saldo anterior
            num = bgdSalAnt;

            numCol = new Number(10, 3, num.doubleValue(), doubleFormat);
            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
            s.addCell(numCol);

            for(int i=0;i<table.getColumnCount();i++){
                Label lbl=new Label((i), (INT_INC_CAB-1), table.getModel().getColumnName(i));
                lbl.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
                s.addCell(lbl);        
            }

            for(int i=0;i<table.getRowCount();i++){
                for(int j=0;j<table.getColumnCount();j++){
                    Object objeto=table.getValueAt(i, j);
                    if(objeto!=null){
                        if( (j==10) || (j==11) || (j==12) ){
                            //Col 10 = Debe. Col 11 = Haber. Col 12 = Saldo.
                            num = new BigDecimal (String.valueOf(objeto));
                            numCol = new Number(j, i+INT_INC_CAB, num.doubleValue(), doubleFormat); 
                            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(numCol);
                        }
                        else{
                                LblCol = new Label(j, i+INT_INC_CAB, String.valueOf(objeto));
                                LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                                s.addCell(LblCol);
                        }
                    }        
                    else
                        s.addCell(new Label(j, i+INT_INC_CAB, ""));
                }
            }

            //titulo de saldo actual
            lblCab = new Label(8, (table.getRowCount()+1+INT_INC_CAB), "SALDO ACTUAL: ");
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
            s.addCell(lblCab);
            
            //valor de saldo actual
            num = bgdSalAct;
            numCol = new Number(10, (table.getRowCount()+1+INT_INC_CAB), num.doubleValue(), doubleFormat); 
            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
            s.addCell(numCol);

            //titulo de saldo acumulado
            lblCab = new Label(8, (table.getRowCount()+2+INT_INC_CAB), "SALDO ACUMULADO: ");
            lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
            s.addCell(lblCab);
            
            //valor de saldo acumulado
            num = bgdSalAcu;
            numCol = new Number(10, (table.getRowCount()+2+INT_INC_CAB), num.doubleValue(), doubleFormat); 
            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
            s.addCell(numCol);               
            
            s.setColumnView(0, 0); //Col 0: vacio.
            s.setColumnView(2, 0); //Col 2: Num.Cta.
            s.setColumnView(3, 0); //Col 3: Nom.Cta.
            s.setColumnView(4, 11); //Col 4: Fec.Doc.
            s.setColumnView(5, 0); //Col 5: Cod.Dia.
            s.setColumnView(6, 10); //Col. 6: Tip.Doc.
            s.setColumnView(7, 8); //Col 7: Num.Doc.
            s.setColumnView(8, 8); //Col 8: Num.Chq.
            s.setColumnView(9, 30); //Col 9: Cliente/Proveedor
            s.setColumnView(10, 12); //Col 10: Debe
            s.setColumnView(11, 12); //Col 11: Haber
            s.setColumnView(12, 0); //Col 12: Saldo

            //s.setPageSetup(PageOrientation.PORTRAIT.PORTRAIT , PaperSize.A4, 0.7, 0.7);

            w.write();
            w.close();
            out.close();
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
}