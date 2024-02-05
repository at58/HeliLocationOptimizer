package gui;

import controller.TableController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataPanel extends JPanel {

  private final JTextField[] txtTupleInputs;

  public DataPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setVisible(true);

    JLabel headerLabel = new JLabel("                               Unfalldaten");
    headerLabel.setFont(Font.CONSOLAS18.getFont());
    headerLabel.setForeground(Color.DARK_GRAY);
    headerLabel.setLayout(null);
    headerLabel.setFont(Font.CONSOLAS16.getFont());
    add(headerLabel);

    add(TablePanel.getInstance());

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(1,4));

    JPanel emptyPanel = new JPanel();
    add(emptyPanel);

    JTextField txtLocation = new JTextField();
    txtLocation.setToolTipText("Eingabe des Ortsnamen");
    JTextField txtX_Coordinate = new JTextField();
    txtX_Coordinate.setToolTipText("Eingabe der X - Koordinate");
    JTextField txtY_Coordinate = new JTextField();
    txtY_Coordinate.setToolTipText("Eingabe Y - Koordinate");
    JTextField txtAccidents = new JTextField();
    txtAccidents.setToolTipText("Eingabe der Unfallzahl pro Jahr");
    txtTupleInputs = new JTextField[]
        {
            txtLocation,
            txtX_Coordinate,
            txtY_Coordinate,
            txtAccidents
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
           TableController.put(this.txtTupleInputs);
           Arrays.stream(txtTupleInputs).forEach(t -> t.setText(""));
         }
         );
    confirmInputPanel.add(btnConfirmInput, BorderLayout.EAST);
    add(confirmInputPanel);
  }
}