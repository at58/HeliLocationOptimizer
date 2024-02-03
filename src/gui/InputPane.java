package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPane extends JPanel {

  public InputPane(Point alignment) {
    setLayout(null);
    setBackground(Color.WHITE);

    JLabel lblTopic = new JLabel("Eingaben:");
    lblTopic.setBounds(50,25,350,50);
    lblTopic.setFont(Font.CONSOLAS20.getFont());
    add(lblTopic);

    JLabel lblHeliNumber = new JLabel("Anzahl der vorhandenen Helikopter:");
    lblHeliNumber.setBounds(50, 100, 400, 50);
    lblHeliNumber.setFont(Font.CONSOLAS18.getFont());
    add(lblHeliNumber);

    JTextField txtHeliNumber = new JTextField();
    txtHeliNumber.setBounds(430,100, 100,40);
    txtHeliNumber.setFont(Font.CONSOLAS20.getFont());
    add(txtHeliNumber);

    JLabel lblHeliSpeed = new JLabel("Helikopter-Geschwindigkeit:");
    lblHeliSpeed.setBounds(50, 170, 400, 50);
    lblHeliSpeed.setFont(Font.CONSOLAS18.getFont());
    add(lblHeliSpeed);

    JTextField txtHeliSpeed = new JTextField();
    txtHeliSpeed.setBounds(430,170, 100,40);
    txtHeliSpeed.setFont(Font.CONSOLAS20.getFont());
    add(txtHeliSpeed);

    DataTable dataTable = new DataTable();
    dataTable.setBounds(650, 10, 800, 350);
    add(dataTable);
  }
}