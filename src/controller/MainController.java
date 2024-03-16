package controller;

import domain.Helicopter;
import gui.GUI;
import java.awt.Dimension;
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

/**
 * The main controller is responsible for any user inputs via the user interface clicks, especially
 * for the button actions.
 */
public class MainController {

  private static final CsvParser csvParser = new CsvParser();
  private static GUI gui;

  /**
   * Initialize the GUI.
   *
   * @param dimension the screen dimension users desktop.
   */
  public static void startGUI(Dimension dimension) {
    gui = new GUI(dimension);
  }

  /**
   * Calculates the optimum placements of helicopter bases.
   */
  public static void calculate() {

    gui.hideInputErrorMsg();
    gui.hideUnexpectedErrMsg();
    Logger.log("The calculation of the optimum positions for helicopter bases has been started.");
    long start = System.currentTimeMillis();

    List<Helicopter> helicopterList;
    try {
      helicopterList = Locator.findOptimalPositions(gui.getHeliNumberFieldInput(),
                                                    gui.getSpeedFieldInput(),
                                                    TableController.getTableData());
    } catch (IllegalArgumentException e) {
      Logger.log(e.getMessage());
      gui.showInputErrorMsg();
      return;
    } catch (NoLocationDataException e) {
      Logger.log(e.getMessage());
      gui.showNoLocationDataMsg();
      return;
    }
    if (helicopterList.isEmpty()) {
      Logger.log("An unexpected error occurred during the calculation of the optimum helicopter positions");
      showUnexpectedErrMsg();
    } else {
      long end = System.currentTimeMillis();
      long runtime = end - start;
      Logger.log("Calculation of optimal helicopter postions was executed successfully in " + runtime + " ms.");

      MapController.drawHelicopterPositions(helicopterList);
  	
      JOptionPane.showMessageDialog(gui, "Berechnung erfolgreich abgeschlossen!"
          + System.lineSeparator() + "Optimale Helikopter-Standorte wurden auf der Karte eingezeichnet."
          + System.lineSeparator() + "Eine CSV-Datei mit den Ergebnis-Details kann heruntergeladen werden.", "Berechnung abgeschlossen", JOptionPane.INFORMATION_MESSAGE);
      gui.drawSolution(helicopterList);
    }
  }

  /**
   * Closes the application.
   */
  public static void closeApp() {
    System.exit(0);
  }

  /**
   * Import of csv data consisting of accidents and location information.
   */
  public static void importCSV() {
    hideAllTableErrMsg();
    File file;
    try {
//      file = LoadingService.getPath(); // could be null when the dialog frame is canceled!      
//      List<String[]> input = csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
    	
      List<String[]> input = csvParser.parse("C:/Users/Christoph/eclipse-workspace/HeliLocationOptimizer/test/resources/validCsvFile.csv", Separator.SEMICOLON);
      TableController.loadCsvToTableModel(TableDataMapper.mapToTableModel(input));
    } catch (NullPointerException |
             ColumnIdentifierException |
             NumberFormatException e) {
      if (e instanceof ColumnIdentifierException) {
        gui.showIncompatibleColumnErrMsg();
      }
      if (e instanceof NumberFormatException) {
        gui.showNumberFormatErrMsg();
      }
      Logger.log(e.getMessage());
    }
    gui.hideInputErrorMsg();
  }

  /**
   * Export of a template table with the desired columns as a csv file.
   */
  public static void exportCsvTemplate() {
    String storageLocation = CsvExporter.exportTemplate();
  }

  /**
   * Export of the solution containing detailed information about the optimum placements of
   * helicopters.
   */
  public static void exportSolution() {
    // TODO (Ahmet): implement
  }

  /**
   * Storage of the current table data on the system.
   */
  public static void saveTable() {
    List<String[]> tableData = TableController.getTableData();
    String storageLocation = CsvExporter.saveTable(tableData);
  }

  public static void hideAllTableErrMsg() {
    gui.hideTableInputErrMsg();
    gui.hideNumberFormatErrMsg();
    gui.hideIncompatibleColumnErrMsg();
    gui.hideUnexpectedErrMsg();
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

  public static void showUnexpectedErrMsg() {
    hideAllTableErrMsg();
    gui.showUnexpectedErrMsg();
  }
}