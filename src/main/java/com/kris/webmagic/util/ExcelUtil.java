package com.kris.webmagic.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
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
        try {
            InputStream inputStream = new FileInputStream(fileName);
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
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> list = ExcelUtil.read("E:\\webmagic\\name.xlsx");
        for(String s : list){
            System.out.println(s);
        }
    }

}
