package services.exporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import utils.log.Logger;

@Deprecated
public class XlsxTemplate {

//  public static Workbook get() {
//
//    Workbook workbook = new XSSFWorkbook();
//
//    try {
//      Sheet sheet = workbook.createSheet("Location_Data_Template");
//      Row row = sheet.createRow(1);
//      Font font = workbook.createFont();
//      font.setBold(true);
//      font.setFontName("Consolas");
//      font.setFontHeightInPoints((short) 12);
//      CellStyle cellStyle = workbook.createCellStyle();
//      cellStyle.setFont(font);
//      row.setRowStyle(cellStyle);
//
//      row.createCell(1, CellType.STRING).setCellValue("Ort");
//      row.createCell(2, CellType.STRING).setCellValue("x-Koordinate");
//      row.createCell(3, CellType.STRING).setCellValue("y-Koordinate");
//      row.createCell(4, CellType.STRING).setCellValue("Unfallzahl p.a.");
//      workbook.close();
//
//    } catch (IOException e) {
//      Logger.log(e.getMessage());
//    }
//    return workbook;
//  }
}
