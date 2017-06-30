package com.kris.webmagic.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kris on 2017/6/15.
 */
public class ExcelUtil {

    public static  List<String> read(String fileName){
        List<String> list = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            Workbook workbook = null;
            if(fileName.endsWith("xls")){
                workbook = new HSSFWorkbook(inputStream);
            }else if(fileName.endsWith("xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }
            if(workbook != null){
                for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++ ){
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if(sheet == null){
                        continue;
                    }
                    Iterator<Row> rowIterator = sheet.rowIterator();
                    while (rowIterator.hasNext()){
                        Row row = rowIterator.next();
                        Cell cell = row.getCell(0);
                        String value = cell.getStringCellValue();
                        list.add(value);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void write(String fileName, List<String> list) {
        FileOutputStream fileOutputStream = null;
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(list.get(i));
            }
            fileOutputStream = new FileOutputStream(fileName);
            wb.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void overwrite(String fileName, List<String> list) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            Workbook workbook = new XSSFWorkbook(inputStream);
            int sheetSize = workbook.getNumberOfSheets();
            Sheet sheet = workbook.createSheet("sheet" + sheetSize);
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(list.get(i));
            }

            fileOutputStream = new FileOutputStream(fileName);
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        List<String> list = ExcelUtil.read("E:\\webmagic\\name.xlsx");
        for(String s : list){
            System.out.println(s);
        }
    }

}
