package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import controller.TableController;
import domain.Helicopter;
import services.mapper.LocationMapper;

import javax.swing.BoxLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Interactive GUI of the application.
 */
public final class GUI extends JFrame {

	private final InputPane inputPane;
	private final DrawPane drawPane;

	public GUI(Dimension dimension) {

		setTitle("Helicopter Location Optimizer");
		int width = (int) dimension.getWidth();
		int height = (int) dimension.getHeight() - 50;

		setSize(width, height);

		setLocation((int) (dimension.getWidth() - width) / 2, 5);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		ToolTipManager manager = ToolTipManager.sharedInstance();
		manager.setInitialDelay(500);
		manager.setDismissDelay(5000);
		manager.setReshowDelay(1);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 1)
					showLocations();
			}
		});
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setFont(Font.CONSOLAS20.getFont());

		JPanel mapTab = new JPanel();
		mapTab.setLayout(new BoxLayout(mapTab, BoxLayout.X_AXIS));

		drawPane = new DrawPane();
		mapTab.add(drawPane);

		JPanel dataTab = new JPanel(new GridBagLayout());

		GridBagConstraints gbc;

		// Obere Hälfte des Data Frames //TODO (Ahmet): fix typo
		inputPane = new InputPane(new Point(1, 1));
		gbc = new GridBagConstraints(); // Zurücksetzen der GridBagConstraints
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Horizontales Gewicht für die zweite Zeile
		gbc.weighty = 1.0; // Vertikales Gewicht für die zweite Zeile
		gbc.fill = GridBagConstraints.BOTH; // Füllt den verfügbaren Platz in beiden Richtungen
		dataTab.add(inputPane, gbc);

		// Untere Hälfte des Data Frames
		ControlPane controlPane = new ControlPane(new Point(150, 40));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0; // Horizontales Gewicht für die erste Zeile
		gbc.weighty = 0.6; // Vertikales Gewicht für die erste Zeile
		gbc.fill = GridBagConstraints.BOTH; // Füllt den verfügbaren Platz in beiden Richtungen
		dataTab.add(controlPane, gbc);

		tabbedPane.addTab("Daten", null, dataTab, "Daten-Verwaltung");
		tabbedPane.addTab("Karte", null, mapTab, "Karten-Ansicht");

		getContentPane().add(tabbedPane);
		setVisible(true);
		setResizable(false);
	}

	public String getHeliNumberFieldInput() {
		return this.inputPane.getNumberOfHelicopter();
	}

	public String getSpeedFieldInput() {
		return this.inputPane.getHelicopterSpeed();
	}

	public void showInputErrorMsg() {
		this.inputPane.showInputErrorMsg();
	}

	public void showNoLocationDataMsg() {
		this.inputPane.showNoTableDataErrMsg();
	}

	public void hideInputErrorMsg() {
		this.inputPane.hideNoTxtInputErrorMsg();
		this.inputPane.hideTableDataErrorMsg();
	}

	public void showTableInputErrMsg() {
		this.inputPane.showTableInputErrMsg();
	}

	public void hideTableInputErrMsg() {
		this.inputPane.hideTableInputErrMsg();
	}

	public void showIncompatibleColumnErrMsg() {
		this.inputPane.showIncompatibleColumnErrMsg();
	}

	public void hideIncompatibleColumnErrMsg() {
		this.inputPane.hideIncompatibleColumnErrMsg();
	}

	public void showNumberFormatErrMsg() {
		this.inputPane.showNumberFormatErrMsg();
	}

	public void hideNumberFormatErrMsg() {
		this.inputPane.hideNumberFormatErrMsg();
	}

	public void showUnexpectedErrMsg() {
		this.inputPane.showUnexpectedErrMsg();
	}

	public void hideUnexpectedErrMsg() {
		this.inputPane.hideUnexpectedErrMsg();
	}

	public void showLocations() {
		this.drawPane.drawLocations(LocationMapper.mapToLocationObjects(TableController.getTableData()));
	}

	public void showSolution(List<Helicopter> helicopters) {
		this.drawPane.drawHelicopterPositions(helicopters);
	}
}