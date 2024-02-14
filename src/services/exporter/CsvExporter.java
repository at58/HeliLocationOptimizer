package services.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import services.dialog.StorageService;
import utils.exceptions.DialogCancelledException;
import utils.log.Logger;

public class CsvExporter {

  private static final List<String> header = List.of("Ort",
                                                     "x-Koordinate",
                                                     "y-Koordinate",
                                                     "Unfallzahl pro Jahr");

  public static String exportTemplate() {

    String storageLocation;
    File storageTarget = null;

    try {
      storageTarget = StorageService.getStorageLocation("Ski_Gebiet_Daten.csv", 1);
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
      storageTarget = StorageService.getStorageLocation("", 2);
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
}
