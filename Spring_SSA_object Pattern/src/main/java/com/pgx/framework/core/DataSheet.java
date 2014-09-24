package com.pgx.framework.core;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;

public class DataSheet {
        /*
         * This method will fetch the data from excel cell based on Row number and column name
         */	
         public static String getData(String strSheetName, String strColIdentifier)  {
                Workbook objWorkBook;
                Sheet objCurrentSheet;
                String strAbsFilePath = getAbsolutePath(EnvParameters.getDataSheetFileName());
                String strContent;
                try {
                       objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
                       objCurrentSheet = objWorkBook.getSheet(strSheetName);
                       int colNum = getColumnNumber(strSheetName, strColIdentifier);
                       strContent = objCurrentSheet.getCell(colNum, 1).getContents();
                       objWorkBook.close();
                       return strContent;
                } catch (Exception e) {
                       return null;
                }
         }

         /*
          * This method will be used get the column number based on column Identifier
          */
         public static int getColumnNumber(String strSheetName, String strColIdentifier) throws IOException {
                Workbook objWorkBook;
                Sheet objCurrentSheet;
                String strAbsFilePath = getAbsolutePath(EnvParameters.getDataSheetFileName());
                int col;
                try {
                       objWorkBook = Workbook.getWorkbook(new File(strAbsFilePath));
                       objCurrentSheet = objWorkBook.getSheet(strSheetName);
                       col = objCurrentSheet.findCell(strColIdentifier).getColumn();
                       objWorkBook.close();
                       return col;
                } catch (Exception e) {
                       return -1;
                }
         }

         /*
          * This method is used to fetch the absolute path of the given excel sheet
          */
         public static String getAbsolutePath(String strFilePath) {
                try {
                       if (strFilePath.contains("https")) {
                             return strFilePath;
                       }
                       else {
                             return new File(strFilePath).getAbsolutePath();
                       }
                } catch (Exception objException) {
                       return "";
                }
         }
}
