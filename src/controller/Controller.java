package controller;

import gui.Button;
import gui.GUI;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import services.calculations.LocationFinder;
import services.exporter.CsvExporter;
import services.importer.CsvParser;
import services.mapper.TableDataMapper;
import services.dialog.LoadingService;
import utils.Separator;
import utils.exceptions.NoLocationDataException;
import utils.log.Logger;

public class Controller {

  private static final CsvParser csvParser = new CsvParser();
  private static GUI gui;

  public static void startGUI(GUI userInterface) {
    gui = userInterface;
  }

  public static void calculate() {

    gui.hideErrorMessages();

    // TODO (Ahmet): This is a temporary implementation for control purposes. Delete after implementation.
    /*List<String[]> dataModel = TableController.getTableData();
    dataModel.forEach(d -> {
      Arrays.stream(d).forEach(e -> {
        System.out.print(e);
        System.out.print(", ");
      });
      System.out.println();
    });
    System.out.println("\n-----------------------------\n");*/

    // TODO (Ahmet): implement
    try {
      LocationFinder.findOptimalPositions(gui.getHeliNumberFieldInput(),
                                          gui.getSpeedFieldInput(),
                                          TableController.getTableData());
    } catch (IllegalArgumentException e) {
      Logger.log(e.getMessage());
      gui.showInputErrorMsg();
    } catch (NoLocationDataException e) {
      Logger.log(e.getMessage());
      gui.showNoLocationDataMsg();
    }
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
    gui.hideErrorMessages();
  }

  public static void exportCsvTemplate() {
    String storageLocation = CsvExporter.exportTemplate();
  }

  public static void exportSolution() {
    // TODO (Ahmet): implement
  }

  public static void saveTable() {
    List<String[]> tableData = TableController.getTableData();
    String storageLocation = CsvExporter.saveTable(tableData);
  }

  public static void hideErrorMsg() {
    gui.hideErrorMessages();
  }
}