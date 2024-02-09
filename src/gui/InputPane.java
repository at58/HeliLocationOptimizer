package gui;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPane extends JPanel {

  private final JTextField txtHeliNumber;
  private final JTextField txtHeliSpeed;
  private final JLabel lblInputErrorMsg;
  private final JLabel lblNoTableDataErrMsg;

  public InputPane(Point alignment) {
    setLayout(null);
    setBackground(Color.WHITE);

    JLabel lblTopic = new JLabel("Eingaben:");
    lblTopic.setBounds(50,25,350,50);
    lblTopic.setFont(Font.CONSOLAS20.getFont());
    add(lblTopic);

    JLabel lblHeliNumber = new JLabel("*Anzahl vorhandener Helikopter:");
    lblHeliNumber.setBounds(50, 100, 400, 50);
    lblHeliNumber.setFont(Font.CONSOLAS18.getFont());
    add(lblHeliNumber);

    this.txtHeliNumber = new JTextField();
    txtHeliNumber.setBounds(430,100, 100,40);
    txtHeliNumber.setFont(Font.CONSOLAS20.getFont());
    add(txtHeliNumber);

    JLabel lblHeliSpeed = new JLabel("*Helikopter-Geschwindigkeit:");
    lblHeliSpeed.setBounds(50, 170, 400, 50);
    lblHeliSpeed.setFont(Font.CONSOLAS18.getFont());
    add(lblHeliSpeed);

    this.txtHeliSpeed = new JTextField();
    txtHeliSpeed.setBounds(430,170, 100,40);
    txtHeliSpeed.setFont(Font.CONSOLAS20.getFont());
    add(txtHeliSpeed);

    this.lblInputErrorMsg = new JLabel("<html>Die Eingaben sind invalide! Tragen Sie in die " +
                                           "mit * markierten Felder eine Ganzzahl ein.");
    lblInputErrorMsg.setForeground(Color.RED);
    lblInputErrorMsg.setFont(Font.CONSOLAS18.getFont());
    lblInputErrorMsg.setBounds(50, 250, 450, 80);
    lblInputErrorMsg.setVisible(false);
    add(this.lblInputErrorMsg);

    this.lblNoTableDataErrMsg = new JLabel("<html>Die Unfalldaten-Tabelle ist leer!" +
                                               "<br>Importieren oder tragen Sie Daten ein.");
    lblNoTableDataErrMsg.setForeground(Color.RED);
    lblNoTableDataErrMsg.setFont(Font.CONSOLAS18.getFont());
    lblNoTableDataErrMsg.setBounds(50, 250, 450, 80);
    lblNoTableDataErrMsg.setVisible(false);
    add(this.lblNoTableDataErrMsg);

    DataPanel dataPanel = new DataPanel();
    dataPanel.setBounds(625, 20, 800, 400);
    add(dataPanel);
  }

  public String getNumberOfHelicopter() {
    return this.txtHeliNumber.getText();
  }

  public String getHelicopterSpeed() {
    return this.txtHeliSpeed.getText();
  }

  public void showInputErrorMsg() {
    hideTableDataErrorMsg();
    this.lblInputErrorMsg.setVisible(true);
  }

  public void hideInputErrorMsg() {
    if (this.lblInputErrorMsg.isVisible()) {
      this.lblInputErrorMsg.setVisible(false);
    }
  }

  public void showNoTableDataErrMsg() {
    hideInputErrorMsg();
    this.lblNoTableDataErrMsg.setVisible(true);
  }

  public void hideTableDataErrorMsg() {
    if (this.lblNoTableDataErrMsg.isVisible()) {
      this.lblNoTableDataErrMsg.setVisible(false);
    }
  }
}