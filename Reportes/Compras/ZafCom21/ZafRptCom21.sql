/*VARIOS.*/
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_bod LIMIT 10
SELECT * FROM tbm_emp LIMIT 10
SELECT * FROM tbm_loc LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LA CABECERA DEL REPORTE.*/
SELECT a1.co_emp, a2.tx_nom AS tx_nomEmp, a1.co_loc, a3.tx_nom AS tx_nomLoc, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar
, a1.co_doc, a1.fe_doc, a1.ne_numDoc
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=56
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.ne_numDoc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL DETALLE DEL REPORTE.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_codAlt, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod
, a1.nd_can, a1.nd_cosUni, a1.nd_tot
FROM tbm_detMovInv AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=56
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/
