package helper;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static final String DEFAULT_PATH = "./src/test/resources/test_users_data.xlsx";

    // ✅ Returns row index of next unused record (used = false)
    public static int getNextUnusedRow(String filePath, String sheetName) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        int usedColIndex = -1;

        Row header = sheet.getRow(0);
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

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell usedCell = row.getCell(usedColIndex);
            if (usedCell == null || !usedCell.getBooleanCellValue()) {
                workbook.close();
                return i;
            }
        }

        workbook.close();
        return -1;  // No unused rows
    }

    // ✅ Returns row index of the last used row (used = true)
    public static int getLastUsedRow(String filePath, String sheetName) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        int usedColIndex = -1;
        int lastUsedRowIndex = -1;

        Row header = sheet.getRow(0);
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

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell usedCell = row.getCell(usedColIndex);
            if (usedCell != null && usedCell.getBooleanCellValue()) {
                lastUsedRowIndex = i;
            }
        }

        workbook.close();
        return lastUsedRowIndex;
    }

    // ✅ Returns value of a specific cell using column header name
    public static String getCellValue(String filePath, String sheetName, int rowIndex, String columnName) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row header = sheet.getRow(0);
        int columnIndex = -1;

        for (Cell cell : header) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        if (columnIndex == -1) {
            workbook.close();
            throw new Exception("Column '" + columnName + "' not found");
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            workbook.close();
            throw new Exception("Row " + rowIndex + " not found");
        }

        Cell cell = row.getCell(columnIndex);
        workbook.close();
        return (cell != null) ? cell.toString().trim() : "";
    }
}
