package controller;

import domain.Helicopter;
import gui.GUI;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import services.calculations.Locator;
import services.exporter.CsvExporter;
import services.importer.CsvParser;
import services.mapper.TableDataMapper;
import services.dialog.LoadingService;
import utils.Separator;
import utils.exceptions.ColumnIdentifierException;
import utils.exceptions.NoLocationDataException;
import utils.log.Logger;

public class MainController {

  private static final CsvParser csvParser = new CsvParser();
  private static GUI gui;

  public static void startGUI(GUI userInterface) {
    gui = userInterface;
  }

  public static void calculate() {

    gui.hideInputErrorMsg();
    Logger.log("The calculation of the optimum positions for helicopter bases has been started.");

    List<Helicopter> helicopterList = null;
    try {
      helicopterList = Locator.findOptimalPositions(gui.getHeliNumberFieldInput(),
                                                    gui.getSpeedFieldInput(),
                                                    TableController.getTableData());
    } catch (IllegalArgumentException e) {
      Logger.log(e.getMessage());
      gui.showInputErrorMsg();
    } catch (NoLocationDataException e) {
      Logger.log(e.getMessage());
      gui.showNoLocationDataMsg();
    }
    Logger.log("Calculation of optimal helicopter postions was executed successfully.");
    if (helicopterList != null) {
      MapController.drawHelicopterPositions(helicopterList);
      JOptionPane.showMessageDialog(gui, "Berechnung erfolgreich abgeschlossen!"
          + System.lineSeparator() + "Optimale Helikopter-Standorte wurden auf der Karte eingezeichnet."
          + System.lineSeparator() + "Eine CSV-Datei mit den Ergebnis-Details kann heruntergeladen werden.");
    } else {
      Logger.log("An unexpected error occurred during the calculation of the optimum helicopter positions");
      // TODO (Ahmet): implement error dialog in gui
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