/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.common.jxl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author michael
 * @version $Id: ExcelUtil.java 4685 2013-06-09 03:33:51Z zhangshixi $
 */
public final class ExcelUtil {

    public static Workbook getWorkbook(MultipartFile sourceFile) throws IOException {
        String fileName = sourceFile.getOriginalFilename();
        InputStream input = sourceFile.getInputStream();
        try {
            if (fileName.endsWith(".xls")) {
                return new HSSFWorkbook(input);
            } else if (fileName.endsWith(".xlsx")) {
                return new XSSFWorkbook(input);
            } else {
                throw new IOException("Unknow excel type.");
            }
        } finally {
            input.close();
        }
    }
    
    public static Workbook getWorkbook(File sourceFile) throws IOException {
        String fileName = sourceFile.getName();
        InputStream input = new FileInputStream(sourceFile);
        try {
            if (fileName.endsWith(".xls")) {
                return new HSSFWorkbook(input);
            } else if (fileName.endsWith(".xlsx")) {
                return new XSSFWorkbook(input);
            } else {
                throw new IOException("Unknow excel type.");
            }
        } finally {
            input.close();
        }
    }

    public static Sheet getSheet(Workbook workbook, int index) {
        return workbook.getSheetAt(index);
    }

    public static int getRowNumber(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    public static Row getRow(Sheet sheet, int index) {
        return sheet == null ? null : sheet.getRow(index);
    }

    public static Cell getCell(Row row, int index) {
        return row == null ? null : row.getCell(index);
    }

    public static String getStringCellValue(Cell cell) {
        return cell == null ? null : cell.getStringCellValue().trim();
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return null;
        }
    }

    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return true;
            }
        }

        return false;
    }

    public static List<String[]> readLines(File sourceFile) throws IOException {
        Workbook workbook = getWorkbook(sourceFile);
        Sheet sheet = getSheet(workbook, 0);
        if (sheet == null)
            return null;
        List<String[]> list = new ArrayList<>();
        for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext();) {
            Row row = (Row) iterator.next();
            if (row != null) {
                List<String> cellsList = new ArrayList<>();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    cellsList.add(getCellValue(getCell(row, i)));
                }
                list.add(cellsList.toArray(new String[]{}));
            }
        }
        return list;
    }
}
