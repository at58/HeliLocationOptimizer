package domain;

import java.util.Arrays;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table Data Model
 */
public class DataTable {

  private final DefaultTableModel tableModel;
  private Object[][] tuples = new Object[][] {};
  private final Object[] columns = new Object[]
      { "Ort",
       "X - Koordinate",
       "Y - Koordinate",
       "Unfallzahl pro Jahr"};
  private static DataTable dataTable;

  private DataTable() {
    this.tableModel = new DefaultTableModel();
    this.tableModel.setColumnIdentifiers(columns);
  }

  public static DataTable getInstance() {
    if (dataTable == null) {
      dataTable = new DataTable();
    }
    return dataTable;
  }

  public DefaultTableModel getTableModel() {
    return this.tableModel;
  }

  public void pushDataBase(Object[][] tuples) {
    this.tuples = tuples;
    Arrays.stream(tuples).forEach(tableModel::addRow);
    refresh();
  }

  public void addTuple(Object[] tuple) {
    this.tableModel.addRow(tuple);
  }

  public void deleteTuple(int row) {
    this.tableModel.removeRow(row);
  }

  public Vector<Vector> pullDataBase() {
    return this.tableModel.getDataVector();
  }

  public void refresh() {
    this.tableModel.fireTableDataChanged();
  }
}