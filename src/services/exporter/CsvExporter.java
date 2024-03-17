package services.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.csv.CSVPrinter;

import domain.Helicopter;
import services.dialog.StorageService;
import utils.exceptions.DialogCancelledException;
import utils.log.Logger;

public class CsvExporter {

	private static final List<String> header = List.of("Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl pro Jahr");
	private static final List<String> solutionHeader1 = List.of("Helikopter", "x-Koordinate", "y-Koordinate",
			"Gesamtunfälle", "Durchschnittliche Unfälle", "Zugeordnete Orte");
	private static final List<String> solutionHeader2 = List.of("Helikopter", "Ort", "Entfernung", "Flugzeit");

	public static String exportTemplate() {

		String storageLocation;
		File storageTarget = null;

		try {
			storageTarget = StorageService.getStorageLocation("Ski_Gebiet_Daten.csv", 1, "csv");
			FileWriter writer = new FileWriter(storageTarget);
			writeLine(writer, header);
			writer.close();
		} catch (DialogCancelledException | IOException e) {
			Logger.log(e.getMessage());
		} finally {
			if (Objects.isNull(storageTarget)) {
				storageLocation = "";
			} else {
				storageLocation = storageTarget.getAbsolutePath();
			}
		}
		return storageLocation;
	}

	public static String saveTable(List<String[]> data) {

		String storagePath = "";
		File storageTarget = null;

		try {
			storageTarget = StorageService.getStorageLocation("", 2, "csv");
			FileWriter writer = new FileWriter(storageTarget);
			writeLine(writer, header);
			for (String[] tuple : data) {
				writeLine(writer, List.of(tuple));
			}
			writer.close();
		} catch (DialogCancelledException | IOException e) {
			Logger.log(e.getMessage());
		} finally {
			if (storageTarget != null) {
				storagePath = storageTarget.getAbsolutePath();
			}
		}
		return storagePath;
	}

	private static void writeLine(FileWriter writer, List<String> data) throws IOException {
		String csvLine = String.join(";", data) + System.lineSeparator();
		writer.write(csvLine);
	}

	public static void saveSolution(List<Helicopter> helicopterList) {
		try {
			File storageTarget = StorageService.getStorageLocation("", 3, "xls");
			Workbook workbook = new HSSFWorkbook();

            // create first sheet
			Sheet sheet1 = workbook.createSheet("Sheet1");
            writeLine(sheet1, solutionHeader1);

            // create second sheet
            Sheet sheet2 = workbook.createSheet("Sheet2");
            writeLine(sheet2, solutionHeader2);
            
//            for (Helicopter heli : helicopterList) {
//                int index = helicopterList.indexOf(heli);
//                writeLine(sheet1, heli.getSummarizedData(index));
//
//                List<String[]> data = heli.getDetailedData(index);
//                for (String[] tuple : data) {
//                    writeLine(sheet2, List.of(tuple));
//                }
//            }
//
            for(int i = 0; i < solutionHeader1.size(); i++) {
                sheet1.autoSizeColumn(i);
            }
            
            for(int i = 0; i < solutionHeader2.size(); i++) {
                sheet2.autoSizeColumn(i);
            }
            
            // save file
            FileOutputStream fileOut = new FileOutputStream(storageTarget);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (DialogCancelledException | IOException e) {
        	Logger.log(e.getMessage());
        }
	}
	
	private static void writeLine(Sheet sheet, List<String> data) {	
		Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
		
		for (int i = 0; i < data.size(); i++) {
			String str = data.get(i);
			row.createCell(i).setCellValue(str);
		}
	}

}
