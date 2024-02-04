package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

/**
 * Interactive GUI of the application.
 */
public final class GUI extends JFrame {

  public GUI(Dimension dimension) {

    setTitle("Helicopter Location Optimizer");
    int width = (int) dimension.getWidth();
    int height = (int) dimension.getHeight() - 50;
    setSize(width, height);
    setLocation((int) (dimension.getWidth() - width)/2,5);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);

    ToolTipManager manager = ToolTipManager.sharedInstance();
    manager.setInitialDelay(500);
    manager.setDismissDelay(5000);
    manager.setReshowDelay(1);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBackground(Color.WHITE);
    tabbedPane.setFont(Font.CONSOLAS20.getFont());

    JPanel mapTab = new JPanel();
    mapTab.add(new JLabel("Elemente von Tab 1..."));

    JPanel dataTab = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();

    // Zweite Zeile
    InputPane inputPane = new InputPane(new Point(1, 1));
    gbc = new GridBagConstraints(); // Zurücksetzen der GridBagConstraints
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Horizontales Gewicht für die zweite Zeile
    gbc.weighty = 1.0; // Vertikales Gewicht für die zweite Zeile
    gbc.fill = GridBagConstraints.BOTH; // Füllt den verfügbaren Platz in beiden Richtungen
    dataTab.add(inputPane, gbc);

    // Erste Zeile mit mehr Platz
    ControlPane controlPane = new ControlPane(new Point(150, 40));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0; // Horizontales Gewicht für die erste Zeile
    gbc.weighty = 0.6; // Vertikales Gewicht für die erste Zeile
    gbc.fill = GridBagConstraints.BOTH; // Füllt den verfügbaren Platz in beiden Richtungen
    dataTab.add(controlPane, gbc);

    tabbedPane.addTab("Daten", dataTab);
    tabbedPane.addTab("Karte", mapTab);

    add(tabbedPane);
    setVisible(true);
  }
}