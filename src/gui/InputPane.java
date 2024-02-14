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
  private final JLabel lblInvalidTableInputErrMsg;
  private final JLabel lblIncompatibleColumnErrMsg;
  private final JLabel lblNumberFormatErrMsg;
  private final JLabel lblUnexpectedErrMsg;

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
                                           "mit * markierten Felder eine positive Ganzzahl > 0 ein.");
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

    this.lblInvalidTableInputErrMsg = new JLabel("<html>Der eingegebene Datensatz ist invalide! " +
                                                     "Achten Sie auf komplette Eingaben und " +
                                                     "ganze Zahlen ohne Komma.");
    lblInvalidTableInputErrMsg.setForeground(Color.RED);
    lblInvalidTableInputErrMsg.setFont(Font.CONSOLAS16.getFont());
    lblInvalidTableInputErrMsg.setBounds(625, 380, 700,80);
    lblInvalidTableInputErrMsg.setVisible(false);
    add(this.lblInvalidTableInputErrMsg);

    lblIncompatibleColumnErrMsg = new JLabel("<html>Import fehlgeschlagen! Die Spalten der " +
                                                 "importierten Datei sind inkompatibel.");
    lblIncompatibleColumnErrMsg.setForeground(Color.RED);
    lblIncompatibleColumnErrMsg.setFont(Font.CONSOLAS16.getFont());
    lblIncompatibleColumnErrMsg.setBounds(625, 370, 700,80);
    lblIncompatibleColumnErrMsg.setVisible(false);
    add(this.lblIncompatibleColumnErrMsg);

    lblNumberFormatErrMsg = new JLabel("<html>Import fehlgeschlagen! In eine oder mehrere " +
                                           "Zahlenwert-Spalten der importierten Datei sind " +
                                           "Spaltenwerte enthalten, die keine ganze Zahlen sind.");
    lblNumberFormatErrMsg.setForeground(Color.RED);
    lblNumberFormatErrMsg.setFont(Font.CONSOLAS16.getFont());
    lblNumberFormatErrMsg.setBounds(625, 380, 700,80);
    lblNumberFormatErrMsg.setVisible(false);
    add(this.lblNumberFormatErrMsg);

    this.lblUnexpectedErrMsg = new JLabel("<html>Ein unerwarteter Fehler ist aufgetreten! " +
                                              "Bitte starten Sie das Programm neu oder geben andere Daten ein.");
    lblUnexpectedErrMsg.setForeground(Color.RED);
    lblUnexpectedErrMsg.setFont(Font.CONSOLAS16.getFont());
    lblUnexpectedErrMsg.setBounds(625, 380, 700,80);
    lblUnexpectedErrMsg.setVisible(false);
    add(this.lblUnexpectedErrMsg);

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

  public void hideNoTxtInputErrorMsg() {
    this.lblInputErrorMsg.setVisible(false);
  }

  public void showNoTableDataErrMsg() {
    hideNoTxtInputErrorMsg();
    this.lblNoTableDataErrMsg.setVisible(true);
  }

  public void hideTableDataErrorMsg() {
    this.lblNoTableDataErrMsg.setVisible(false);
  }

  public void showTableInputErrMsg() {
    this.lblInvalidTableInputErrMsg.setVisible(true);
  }

  public void hideTableInputErrMsg() {
    this.lblInvalidTableInputErrMsg.setVisible(false);
  }

  public void showIncompatibleColumnErrMsg() {
    this.lblIncompatibleColumnErrMsg.setVisible(true);
  }

  public void hideIncompatibleColumnErrMsg() {
    this.lblIncompatibleColumnErrMsg.setVisible(false);
  }

  public void showNumberFormatErrMsg() {
    this.lblNumberFormatErrMsg.setVisible(true);
  }

  public void hideNumberFormatErrMsg() {
    this.lblNumberFormatErrMsg.setVisible(false);
  }

  public void showUnexpectedErrMsg() {
    this.lblUnexpectedErrMsg.setVisible(true);
  }

  public void hideUnexpectedErrMsg() {
    this.lblUnexpectedErrMsg.setVisible(false);
  }

}