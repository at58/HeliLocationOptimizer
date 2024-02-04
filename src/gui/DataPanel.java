package gui;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataPanel extends JPanel {

  private final TablePanel tablePanel;
  private JTextField[] txtTupleInputs;
  private JTextField txtLocation;
  private JTextField txtX_Coordinate;
  private JTextField txtY_Coordinate;
  private JTextField txtAccidents;


  public DataPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setVisible(true);

    JLabel headerLabel = new JLabel("                               Unfalldaten");
    headerLabel.setFont(Font.CONSOLAS18.getFont());
    headerLabel.setForeground(Color.DARK_GRAY);
    headerLabel.setLayout(null);
    headerLabel.setFont(Font.CONSOLAS16.getFont());
    add(headerLabel);

    this.tablePanel = TablePanel.getInstance();
    add(this.tablePanel);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(1,4));

    JPanel emptyPanel = new JPanel();
    // emptyPanel.setBackground(Color.LIGHT_GRAY);
    add(emptyPanel);

    this.txtLocation = new JTextField();
    this.txtLocation.setToolTipText("Eingabe des Ortsnamen");
    this.txtX_Coordinate = new JTextField();
    this.txtX_Coordinate.setToolTipText("Eingabe der X - Koordinate");
    this.txtY_Coordinate = new JTextField();
    this.txtY_Coordinate.setToolTipText("Eingabe Y - Koordinate");
    this.txtAccidents = new JTextField();
    this.txtAccidents.setToolTipText("Eingabe der Unfallzahl pro Jahr");
    txtTupleInputs = new JTextField[]
        {
            this.txtLocation,
            this.txtX_Coordinate,
            this.txtY_Coordinate,
            this.txtAccidents
        };
    Arrays.stream(txtTupleInputs).forEach(field -> {
      field.setFont(Font.CONSOLAS18.getFont());
      inputPanel.add(field);
    });
    add(inputPanel);

    JPanel confirmInputPanel = new JPanel(new BorderLayout());
    confirmInputPanel.setBackground(Color.WHITE);
    Button btnConfirmInput = new Button
        ("  +  ",
         Color.GREEN,
         Color.BLACK,
         Font.CONSOLAS16,
         "Den eingegebenen Datensatz hinzufuegen.",
         new Point(),
         new Dimension(),
         action -> {
           Controller.addTupleToTable(this.txtTupleInputs);
           Arrays.stream(txtTupleInputs).forEach(t -> t.setText(""));
         }
         );
    confirmInputPanel.add(btnConfirmInput, BorderLayout.EAST);
    add(confirmInputPanel);
  }

  public TablePanel getTablePanel() {
    return this.tablePanel;
  }
}