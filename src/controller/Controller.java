package controller;

import gui.Button;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import services.exporter.CsvTemplateExporter;
import services.importer.CsvParser;
import services.mapper.TableDataMapper;
import services.dialog.LoadingService;
import utils.Separator;
import utils.log.Logger;

public class Controller {

  private static final CsvParser csvParser = new CsvParser();

  public static void calculate(Button button) {
    button.setSelected(true);

    // TODO (Ahmet): This is a temporary implementation for control purposes. Delete after implementation.
    List<String[]> dataModel = TableController.getTableData();
    dataModel.forEach(d -> {
      Arrays.stream(d).forEach(e -> {
        System.out.print(e);
        System.out.print(", ");
      });
      System.out.println();
    });
    System.out.println("\n-----------------------------\n");

    // TODO (Ahmet): implement

  }

  public static void closeApp() {
    System.exit(0);
  }

  public static void importCSV() {
    File file;
    try {
      file = LoadingService.getPath(); // could be null!
      List<String[]> input = csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
      TableController.loadCsvToTableModel(TableDataMapper.mapToTableModel(input));
    } catch (NullPointerException e) {
      Logger.log(e.getMessage());
    }
  }

  public static void exportCsvTemplate() {
    String storageLocation = CsvTemplateExporter.export();
  }

  public static void exportSolution() {
    // TODO (Ahmet): implement
  }

  public static void saveTable() {
    List<String[]> tableData = TableController.getTableData();
    String storageLocation = CsvTemplateExporter.saveTable(tableData);
  }
}