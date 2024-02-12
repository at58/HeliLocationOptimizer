package controller;

import domain.DataTable;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import services.mapper.TableDataMapper;

public class TableController {

  private static final DataTable dataTable = DataTable.getInstance();

  public static boolean put(JTextField[] textFields) {

    MainController.hideAllTableErrMsg();
    Object[] newTuple = TableDataMapper.extractTextFieldContent(textFields);
    if (newTuple != null) {
      dataTable.addTuple(newTuple);
      dataTable.refresh();
      MainController.hideInputErrorMsg();
      MainController.hideTableInputErrorMsg();
      return true;
    } else {
      MainController.showTableInputErrMsg();
      return false;
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

    Vector dataVector =  dataTable.pullDataBase();
    return TableDataMapper.mapToStringArrayList(dataVector);
  }
}