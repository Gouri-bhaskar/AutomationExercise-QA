package helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelWriter {

    public static void markRowUsed(String filePath, String sheetName, int rowIndex) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row header = sheet.getRow(0);

        int usedColIndex = -1;
        for (Cell cell : header) {
            if (cell.getStringCellValue().equalsIgnoreCase("used")) {
                usedColIndex = cell.getColumnIndex();
                break;
            }
        }

        if (usedColIndex == -1) {
            workbook.close();
            throw new Exception("'used' column not found");
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell usedCell = row.getCell(usedColIndex);
        if (usedCell == null) usedCell = row.createCell(usedColIndex);

        usedCell.setCellValue(true);

        fis.close();  // Close input before writing

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
