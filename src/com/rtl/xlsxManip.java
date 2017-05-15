package com.rtl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;


/**
 * Created by caitlin.ye on 5/13/17.
 */
public class xlsxManip {

    //output to spreadsheet, pass hashmap for retailers already created, print key and value
    //need to put index, name, rtl #
    public void toXLSX(HashMap<String, String> map, int rows) throws IOException{
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook ();
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        //use saveRetailer
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        /*
        data.put("7", new Object[] {7d, "Sonya", "75K", "SALES", "Rupert"});
        data.put("8", new Object[] {8d, "Kris", "85K", "SALES", "Rupert"});
        data.put("9", new Object[] {9d, "Dave", "90K", "SALES", "Rupert"});
        */
        // Set to Iterate and add rows into XLS file
        Set<String> newRows = data.keySet();
        // get the last row number to append new data
        int rownum = mySheet.getLastRowNum();
        for (String key : newRows) {
            // Creating a new Row in existing XLSX sheet
            Row row = mySheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                }
                else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                }
                else if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                }
                else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }
        // open an OutputStream to save written data into XLSX file
        File myFile = new File("/Users/caitlin.ye/Desktop/test2.xlsx");
        FileOutputStream os = new FileOutputStream(myFile);
        myWorkBook.write(os);
        System.out.println("Writing on XLSX file Finished ...");

    }



    //TODO rtl from spreadsheet save in String arr, change func type
    public int fromXLSX() throws IOException {
        int rowCounter = 0;
        int colCounter = 0;
        HashMap<Integer, Cell> trackColumns = new HashMap<>();
        //TODO make GUI for upload

        File myFile = new File("/Users/caitlin.ye/Desktop/test.xlsx"); //TODO change to user input later
        FileInputStream fis = new FileInputStream(myFile);
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        // Traversing over each row of XLSX file
        Row row = rowIterator.next(); //starts at 2nd row
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            rowCounter++;
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
//TODO
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        //cell = cell.getStringCellValue();
                        break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default :
                }

                trackColumns.put(colCounter, cell);
                colCounter++;
            }
        }

            //TODO return this from a diff func return rowCounter;
        return rowCounter;
    }

}

