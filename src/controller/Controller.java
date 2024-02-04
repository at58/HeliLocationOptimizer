package controller;

import gui.Button;
import gui.TablePanel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTextField;
import services.exporter.CsvTemplateExporter;
import services.importer.CsvParser;
import services.mapper.TableDataMapper;
import services.persistence.LoadingService;
import utils.Separator;
import utils.log.Logger;

public class Controller {

  private static final CsvParser csvParser = new CsvParser();

  public static void calculate(Button button) {
    button.setSelected(true);

    // TODO (Ahmet): implement

  }

  public static void closeApp() {
    System.exit(0);
  }

  public static void importCSV() {
    File file;
    try {
      file = LoadingService.getPath();
      List<String[]> input = csvParser.parse(file.getAbsolutePath(), Separator.SEMICOLON);
      loadCsvToTableModel(TableDataMapper.mapToTableModel(input));
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

  private static void loadCsvToTableModel(Object[][] modelData) {
    TablePanel tablePanel = TablePanel.getInstance();
    Arrays.stream(modelData).forEach(tablePanel::updateTuples);
  }

  public static void addTupleToTable(JTextField[] textFields)
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

    TablePanel.getInstance().updateTuples(newTuple);
  }
}