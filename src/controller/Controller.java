package controller;

import gui.Button;
import gui.TablePanel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JTextField;
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

  public static void addTupleToTable(TablePanel table, JTextField[] textFields)
      throws IllegalArgumentException {

    Arrays.stream(textFields).forEach(field -> {
      if (field.getText().isBlank()) {
        throw new IllegalArgumentException();
      }
    });

    Object[] newTuple = new Object[4];
    newTuple[0] = textFields[0].getText();
    newTuple[1] = Integer.parseInt(textFields[1].getText());
    newTuple[2] = Integer.parseInt(textFields[2].getText());
    newTuple[3] = Integer.parseInt(textFields[3].getText());

    table.updateTuples(newTuple);
  }

  public static void clearTupleInputFields() {

  }
}