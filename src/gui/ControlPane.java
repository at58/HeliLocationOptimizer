package gui;

import controller.MainController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The control pain gui element contains all buttons in the gui. The control
 * pain itself is nested in the gui class.
 */
public class ControlPane extends JPanel {

	private static final long serialVersionUID = -8255513345005544459L;
	private final Button btnCalc;

	public ControlPane(Point alignment) {
		setLayout(null);

		JLabel lblTopic = new JLabel("Aktionen:");
		lblTopic.setBounds(50, 40, 350, 50);
		lblTopic.setFont(Font.CONSOLAS20.getFont());
		add(lblTopic);

		add(new Button("<html>Ergebnisdetails<br>herunterladen", Color.WHITE, Color.BLACK, Font.CONSOLAS18,
				"Details der berechneten Optimierung als CSV herunterladen",
				new Point(alignment.x + 50, alignment.y + 5), new Dimension(300, 60),
				action -> MainController.exportSolution()));

		add(new Button("Vorlage herunterladen", Color.WHITE, Color.BLACK, Font.CONSOLAS18,
				"Struktur-Vorlage als CSV-Datei herunterladen.", new Point(alignment.x + 50, alignment.y + 100),
				new Dimension(300, 60), action -> MainController.exportCsvTemplate()));

		add(new Button("CSV importieren", Color.WHITE, Color.BLACK, Font.CONSOLAS18,
				"CSV-Datei hochladen, die der Struktur-Vorlage entspricht.",
				new Point(alignment.x + 400, alignment.y + 5), new Dimension(300, 60),
				action -> MainController.importCSV()));

		add(new Button("Tabelle speichern", Color.WHITE, Color.BLACK, Font.CONSOLAS18,
				"Aktuelle Tabellendaten speichern", new Point(alignment.x + 400, alignment.y + 100),
				new Dimension(300, 60), action -> MainController.saveTable()));

		btnCalc = new Button("Berechnen", Color.WHITE, Color.BLUE, Font.CONSOLAS20,
				"Ermittlung der optimalen Stationierungen.", new Point(alignment.x + 750, alignment.y + 5),
				new Dimension(300, 60), action -> MainController.calculate());
		add(btnCalc);

		add(new Button("Beenden", Color.WHITE, Color.RED, Font.CONSOLAS18, "Beenden des Programmes.",
				new Point(alignment.x + 750, alignment.y + 100), new Dimension(300, 60),
				action -> MainController.closeApp()));
	}
}