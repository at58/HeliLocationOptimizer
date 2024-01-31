package controller;

import services.exporter.CsvTemplateExporter;
import services.importer.CsvParser;

public class Controller {

  private static CsvParser csvParser = new CsvParser();

  public static void calculate() {
    // TODO (Ahmet): implement
  }

  public static void closeApp() {
    System.exit(0);
  }

  public static void uploadCSV() {
    // TODO (Ahmet): implement
  }

  public static void downloadCsvTemplate() {
    CsvTemplateExporter.export();
  }
}