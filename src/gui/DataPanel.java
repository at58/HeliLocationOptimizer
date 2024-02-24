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
import javax.swing.SwingConstants;
import java.awt.Component;

/**
 * The data panel is a gui element that contains all elements of the data table. The {@link TablePanel}
 * is nested into the data panel. The data panel itself is nested in {@link InputPane}.
 */
public class DataPanel extends JPanel {

  private final JTextField[] txtTupleInputs;

  public DataPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setVisible(true);

    JLabel headerLabel = new JLabel("Unfalldaten", SwingConstants.CENTER);
    headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    headerLabel.setFont(Font.CONSOLAS18.getFont());
    headerLabel.setForeground(Color.DARK_GRAY);
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
           boolean isValidInput = TableController.put(this.txtTupleInputs);
           if (isValidInput) {
             Arrays.stream(txtTupleInputs).forEach(t -> t.setText(""));
           }
         }
         );
    confirmInputPanel.add(btnConfirmInput, BorderLayout.EAST);
    add(confirmInputPanel);
  }
}