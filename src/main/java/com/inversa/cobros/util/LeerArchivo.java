/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import java.io.FileInputStream;

/**
 *
 * @author Z420WK
 */
public class LeerArchivo {

    /*
    private static void readExcel() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("data.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);

        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows; ++i) {
            HSSFRow row = sheet.getRow(i);

            HSSFCell productCell = row.getCell(0);
            HSSFCell priceCell = row.getCell(1);
            HSSFCell linkCell = row.getCell(2);

            String product = productCell.getStringCellValue();
            BigDecimal price = new BigDecimal(priceCell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            String link = linkCell.getStringCellValue();

            System.out.printf("%s, %s, %s%n", product, price.toString(), link);
        }
    }
    
    */

    /*
    private static void readCsv() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("data.csv"));
        List<String[]> lines = reader.readAll();

        System.out.println();
        lines.forEach(d -> {
            String product = (String) d[0];
            BigDecimal price = new BigDecimal(d[1]);
            String link = (String) d[2];

            System.out.printf("%s, %s, %s%n", product, price.toString(), link);
        });

        reader.close();
    }
*/
}
