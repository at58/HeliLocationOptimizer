package domain;

import java.util.Arrays;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table Data Model.
 * Singleton implementation.
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

  /**
   * Inserts all tuples into the table model. If there is already data exists, all data will be
   * replaced by the tuples come from uploading a csv file.
   *
   * @param tuples the uploaded tuples.
   */
  public void pushDataBase(Object[][] tuples) {
    tableModel.setRowCount(0);
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

  public Vector pullDataBase() {
    return this.tableModel.getDataVector();
  }

  public void refresh() {
    this.tableModel.fireTableDataChanged();
  }
}