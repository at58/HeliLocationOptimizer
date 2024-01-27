package services.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import services.saving.StorageService;
import utils.log.Logger;

public class CsvTemplateExporter {

  public static String export() {

    File storageTarget = StorageService.getStorageLocation("Ski_Gebiet_Daten.csv");

    try {
      if (Objects.isNull(storageTarget)) {
        throw new NullPointerException();
      }
      FileWriter writer = new FileWriter(storageTarget);
      List<String> header = List.of("Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl p.a.");
      writeLine(writer, header);
      writer.close();
    } catch (NullPointerException | IOException e) {
      Logger.log(e.getMessage());
    }
    return storageTarget.getAbsolutePath();
  }

  private static void writeLine(FileWriter writer, List<String> data) throws IOException {
    String csvLine = String.join(";", data) + System.lineSeparator();
    writer.write(csvLine);
  }
}