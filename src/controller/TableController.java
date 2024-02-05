package controller;

import domain.DataTable;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import services.mapper.TableDataMapper;
import utils.log.Logger;

public class TableController {

  private static final DataTable dataTable = DataTable.getInstance();

  public static void put(JTextField[] textFields) {

    try {
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

      dataTable.addTuple(newTuple);
      dataTable.refresh();
    } catch (IllegalArgumentException e) {
      Logger.log(e.getMessage());
    }
  }

  public static void delete(int row) {
    dataTable.deleteTuple(row);
    dataTable.refresh();
  }

  public static void loadCsvToTableModel(Object[][] modelData) {
    dataTable.pushDataBase(modelData);
  }

  public static DefaultTableModel getTableModel() {
    return dataTable.getTableModel();
  }

  public static List<String[]> getTableData() {

    Vector<Vector> dataVector =  dataTable.pullDataBase();
    return TableDataMapper.mapToStringArrayList(dataVector);
  }
}