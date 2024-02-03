package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataTable extends JPanel {

  private DefaultTableModel tableModel;
  private JTable table;
  private JLabel headerLabel;

  public DataTable() {

    setLayout(new BorderLayout());

    // Daten für die Tabelle (Beispielwerte)
    Object[][] data = {
        {"Stadt A", 50, 30, 10},
//        {"Stadt B", 20, 40, 5},
        // ...
    };

    // Spaltenüberschriften
    String[] columns = {"Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl pro Jahr"};

    JPanel headerPanel = new JPanel(new GridLayout(1, columns.length));
    for (String column : columns) {
      JLabel label = new JLabel(column, JLabel.CENTER);
      headerPanel.add(label);
    }
    add(headerPanel, BorderLayout.NORTH);

    // Ein DefaultTableModel erstellen
    tableModel = new DefaultTableModel(data, columns);

    // JTable mit dem DefaultTableModel erstellen
    table = new JTable(tableModel);
    table.setFillsViewportHeight(true);

    // Standard-Header deaktivieren
    table.setTableHeader(null);

    // JLabel als benutzerdefinierte Überschrift hinzufügen
    headerLabel = new JLabel("Unfalldaten", JLabel.CENTER);
    headerLabel.setFont(Font.CONSOLAS16.getFont());
    // add(headerLabel, BorderLayout.NORTH);

    // JButton für das Löschen von Zeilen hinzufügen
    Button btnDelete = new Button(
        "Datensatz entfernen",
        Color.pink,
        Color.BLACK,
        Font.CONSOLAS16,
        "Entfernt den markierten Datensatz.",
        new Point(),
        new Dimension(),
        e -> {
          int selectedRow = table.getSelectedRow();
          if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
          }
        }
    );
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(btnDelete);
    add(buttonPanel, BorderLayout.EAST);

    // JScrollPane für die JTable hinzufügen
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }
}