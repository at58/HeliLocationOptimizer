package gui;

import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPane extends JPanel {

  public ControlPane(Point alignment) {
    setLayout(null);

    JLabel lblTopic = new JLabel("Aktionen:");
    lblTopic.setBounds(50,40,350,50);
    lblTopic.setFont(Font.CONSOLAS20.getFont());
    add(lblTopic);

    add(new Button(
        "<html>Ergebnisdetails<br>herunterladen",
        Color.WHITE,
        Color.BLACK,
        Font.CONSOLAS18,
        "Details der berechneten Optimierung als CSV herunterladen",
        new Point(alignment.x + 50, alignment.y + 5),
        new Dimension(300, 60),
        action -> Controller.exportSolution()
    ));

    add(new Button(
        "Vorlage herunterladen",
        Color.WHITE,
        Color.BLACK,
        Font.CONSOLAS18,
        "Struktur-Vorlage als CSV-Datei herunterladen.",
        new Point(alignment.x + 50,alignment.y + 100),
        new Dimension(300,60),
        action -> Controller.exportCsvTemplate()
    ));

    add(new Button(
        "CSV importieren",
        Color.WHITE,
        Color.BLACK,
        Font.CONSOLAS18,
        "CSV-Datei hochladen, die der Struktur-Vorlage entspricht.",
        new Point(alignment.x + 400,alignment.y + 5),
        new Dimension(300,60),
        action -> Controller.importCSV()
    ));

    Button btnCalc = new Button("Berechnen",
                                Color.GREEN,
                                Color.BLACK,
                                Font.CONSOLAS20,
                                "Ermittlung der optimalen Stationierungen.",
                                new Point(alignment.x + 750,alignment.y + 5),
                                new Dimension(300,60),
                                action -> System.out.println());
    btnCalc.offerActionListener(e -> Controller.calculate(btnCalc));
    add(btnCalc);

    add(new Button(
        "Beenden",
        Color.RED,
        Color.WHITE,
        Font.CONSOLAS18,
        "Beenden des Programmes.",
        new Point(alignment.x + 750,alignment.y + 100),
        new Dimension(300,60),
        action -> Controller.closeApp()
    ));
  }
}