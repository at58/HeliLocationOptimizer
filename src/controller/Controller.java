package controller;

import java.io.File;
import java.io.IOException;
import services.exporter.CsvTemplateExporter;
import services.importer.CsvParser;
import services.persistence.LoadingService;
import utils.Separator;
import utils.log.Logger;

public class Controller {

  private static CsvParser csvParser = new CsvParser();

  public static void calculate() {
    // TODO (Ahmet): implement
  }

  public static void closeApp() {
    System.exit(0);
  }

  public static void uploadCSV() {

    File file = LoadingService.getPath();
    try {
      csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
    } catch (IOException e) {
      Logger.log(e.getMessage());
    }
  }

  public static void downloadCsvTemplate() {
    CsvTemplateExporter.export();
  }
}