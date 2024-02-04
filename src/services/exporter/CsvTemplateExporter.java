package services.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.swing.UnsupportedLookAndFeelException;
import services.persistence.StorageService;
import utils.exceptions.DialogCancelledException;
import utils.log.Logger;

public class CsvTemplateExporter {

  public static String export() {

    String storageLocation;
    File storageTarget = null;

    try {
      storageTarget = StorageService.getStorageLocation("Ski_Gebiet_Daten.csv");
      FileWriter writer = new FileWriter(storageTarget);
      List<String> header = List.of("Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl pro Jahr");
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

  private static void writeLine(FileWriter writer, List<String> data) throws IOException {
    String csvLine = String.join(";", data) + System.lineSeparator();
    writer.write(csvLine);
  }
}