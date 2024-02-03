package controller;

import gui.Button;
import java.io.File;
import java.io.IOException;
import services.exporter.CsvTemplateExporter;
import services.importer.CsvParser;
import services.persistence.LoadingService;
import utils.Separator;
import utils.log.Logger;

public class Controller {

  private static CsvParser csvParser = new CsvParser();

  public static void calculate(Button button) {
    button.setSelected(true);

    // TODO (Ahmet): implement

  }

  public static void closeApp() {
    System.exit(0);
  }

  public static void importCSV() {
    File file = LoadingService.getPath();
    try {
      csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
    } catch (IOException e) {
      Logger.log(e.getMessage());
    }
  }

  public static void exportCsvTemplate() {
    CsvTemplateExporter.export();
  }

  public static void exportSolution() {
    // TODO (Ahmet): implement
  }
}