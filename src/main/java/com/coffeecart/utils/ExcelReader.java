package com.coffeecart.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public Object[][] getTestData(String sheetName) {
        try (FileInputStream fis = new FileInputStream("resources/testdata.xlsx");
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rows - 1][cols];
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = getCellValue(cell);
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel: " + e.getMessage(), e);
        }
    }

    public Map<String, String> getCoffeeItemsMap() {
        Map<String, String> map = new LinkedHashMap<>();
        Object[][] data = getTestData("CoffeeItems");
        // Expected columns: Name | Price
        for (Object[] row : data) {
            map.put(String.valueOf(row[0]), String.valueOf(row[1]));
        }
        return map;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) return cell.getDateCellValue();
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val)) return (long) val;
                return val;
            case BOOLEAN: return cell.getBooleanCellValue();
            case FORMULA: return cell.getCellFormula();
            case BLANK: default: return "";
        }
    }
}
