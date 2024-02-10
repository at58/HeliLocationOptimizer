package controller;

import gui.GUI;
import java.io.File;
import java.util.List;
import services.calculations.LocationFinder;
import services.exporter.CsvExporter;
import services.importer.CsvParser;
import services.mapper.TableDataMapper;
import services.dialog.LoadingService;
import utils.Separator;
import utils.exceptions.ColumnIdentifierException;
import utils.exceptions.NoLocationDataException;
import utils.log.Logger;

public class Controller {

  private static final CsvParser csvParser = new CsvParser();
  private static GUI gui;

  public static void startGUI(GUI userInterface) {
    gui = userInterface;
  }

  public static void calculate() {

    gui.hideInputErrorMsg();

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
    hideAllTableErrMsg();
    File file;
    try {
      file = LoadingService.getPath(); // could be null when the dialog frame is canceled!
      List<String[]> input = csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
      TableController.loadCsvToTableModel(TableDataMapper.mapToTableModel(input));
    } catch (NullPointerException |
             ColumnIdentifierException |
             NumberFormatException e) {
      if (e instanceof ColumnIdentifierException) {
        showIncompatibleColumnErrMsg();
      }
      if (e instanceof NumberFormatException) {
        showNumberFormatErrMsg();
      }
      Logger.log(e.getMessage());
    }
    gui.hideInputErrorMsg();
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

  public static void hideAllTableErrMsg() {
    hideTableInputErrorMsg();
    hideNumberFormatErrMsg();
    hideIncompatibleColumnErrMsg();
  }

  public static void hideInputErrorMsg() {
    gui.hideInputErrorMsg();
  }

  public static void showTableInputErrMsg() {
    gui.showTableInputErrMsg();
  }

  public static void hideTableInputErrorMsg() {
    gui.hideTableInputErrMsg();
  }

  public static void showIncompatibleColumnErrMsg() {
    gui.showIncompatibleColumnErrMsg();
  }

  public static void hideIncompatibleColumnErrMsg() {
    gui.hideIncompatibleColumnErrMsg();
  }

  public static void showNumberFormatErrMsg() {
    gui.showNumberFormatErrMsg();
  }

  public static void hideNumberFormatErrMsg() {
    gui.hideNumberFormatErrMsg();
  }
}