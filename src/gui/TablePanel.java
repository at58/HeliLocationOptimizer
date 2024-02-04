package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

  /* Singleton instance */
  private static TablePanel tablePanel;

  private JTable table;
  private DefaultTableModel tableModel;
  private final String[] columns = { "Ort", "X - Koordinate", "Y - Koordinate", "Unfallzahl pro Jahr" };
  private Object[][] tuples;
  private boolean editLock = false;
  private int row = -1;
  private int col = -1;

  public static TablePanel getInstance() {
    if (Objects.isNull(tablePanel)) {
      tablePanel = new TablePanel();
    }
    return tablePanel;
  }

  private TablePanel() {

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(Color.LIGHT_GRAY);

    // Tuples of the table
    tuples = new Object[][] {};

    // Test tuples
    Object[][] data = {
        {"Stadt A", 50, 30, 10},
        {"Stadt B", 50, 30, 10},
        {"Stadt C", 50, 30, 10},
        {"Stadt D", 50, 30, 10},
        {"Stadt E", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt F", 50, 30, 10},
        {"Stadt G", 50, 30, 10}
    };

    tableModel = new DefaultTableModel(tuples, this.columns);
    tableModel.addTableModelListener(action -> System.out.println("Table model changed"));

    table = new JTable(tableModel);
    table.setFillsViewportHeight(true); // TODO (Ahmet): check
    table.setFont(Font.CONSOLAS14.getFont());
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
          int r = table.rowAtPoint(e.getPoint());
          int c = table.columnAtPoint(e.getPoint());
          if (r == row && c == col) {
            editLock = true;
            // System.out.println("LOCK");
          } else {
            row = r;
            col = c;
            editLock = false;
            // System.out.println("UNLOCK");
          }
        }
        if (SwingUtilities.isRightMouseButton(e) && !editLock) {
          int row = table.rowAtPoint(e.getPoint());
          int col = table.columnAtPoint(e.getPoint());

          if (row >= 0 && col >= 0) {
            // remove the row from table
            tableModel.removeRow(row);
          }
        }
      }
    });

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  public DefaultTableModel getTableModel() {
    return this.tableModel;
  }

  public void updateTuples(Object[] newTuple) {
    this.tableModel.addRow(newTuple);
    tableModel.fireTableDataChanged();
  }

  public Object[][] getTuples() {
    return this.tuples;
  }
}