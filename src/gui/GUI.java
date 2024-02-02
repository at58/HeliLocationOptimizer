package gui;

import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

/**
 * Test GUI
 */
public final class GUI extends JFrame {

  private final Font consolas14 = new Font("consolas", Font.BOLD, 14);
  private final Font consolas16 = new Font("consolas", Font.BOLD, 16);
  private final Font consolas18 = new Font("consolas", Font.BOLD, 18);

  private Dimension dimension;

  public GUI(Dimension dimension) {

    setTitle("Helicopter Location Optimizer");
    this.dimension = dimension;
    int width = (int) dimension.getWidth();
    int height = (int) dimension.getHeight() - 50;
    setSize(width, height);
    setLocation((int) (dimension.getWidth() - width)/2,5);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    ToolTipManager.sharedInstance().setInitialDelay(500);
    ToolTipManager.sharedInstance().setDismissDelay(5000);
    ToolTipManager.sharedInstance().setReshowDelay(1);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBackground(Color.WHITE);
    tabbedPane.setFont(consolas16);

    JPanel tab1 = new JPanel();
    tab1.add(new JLabel("Elemente von Tab 1..."));

    JPanel dataTab = new JPanel();
    dataTab.setLayout(null);

    JPanel line = new JPanel();
    line.setBackground(Color.LIGHT_GRAY);
    line.setBounds(width - 300, 0, 1, height);
    dataTab.add(line);

    JButton btnExit = new JButton("Beenden");
    btnExit.setFont(consolas16);
    btnExit.setBackground(Color.RED);
    btnExit.setForeground(Color.WHITE);
    btnExit.addActionListener(action -> Controller.closeApp());
    btnExit.setBounds(width-250 , height-180, 170, 60);
    btnExit.setVisible(true);
    btnExit.setToolTipText("Beenden des Programmes.");
    dataTab.add(btnExit);

    JButton btnCalc = new JButton("Berechnen");
    btnCalc.setFont(consolas18);
    btnCalc.setBackground(Color.GREEN);
    btnCalc.setForeground(Color.BLACK);
    btnCalc.addActionListener(action -> Controller.calculate());
    btnCalc.setBounds(width-250 , height-280, 170, 60);
    btnCalc.setSelected(true);
    btnCalc.setVisible(true);
    btnCalc.setToolTipText("Ermittlung der optimalen Stationierungen.");
    dataTab.add(btnCalc);

    JButton btnImport = new JButton("CSV importieren");
    btnImport.setFont(consolas16);
    btnImport.setBackground(Color.WHITE);
    btnImport.setForeground(Color.BLACK);
    btnImport.setSelected(true);
    btnImport.setBounds(width-270 , height-480, 220, 60);
    btnImport.setVisible(true);
    btnImport.addActionListener(action -> Controller.uploadCSV());
    btnImport.setToolTipText("CSV-Datei hochladen, die der Struktur-Vorlage entspricht.");
    dataTab.add(btnImport);

    JButton btnGetTemplate = new JButton("Vorlage herunterladen");
    btnGetTemplate.setFont(consolas16);
    btnGetTemplate.setBackground(Color.WHITE);
    btnGetTemplate.setForeground(Color.BLACK);
    btnGetTemplate.addActionListener(action -> Controller.downloadCsvTemplate());
    btnGetTemplate.setBounds(width-270 , height-380, 220, 60);
    btnGetTemplate.setSelected(true);
    btnGetTemplate.setVisible(true);
    btnGetTemplate.setToolTipText("Struktur-Vorlage als CSV-Datei herunterladen.");
    dataTab.add(btnGetTemplate);

    JButton btnSaveSolution = new JButton("<html>Ergebnisdetails<br>herunterladen");
    btnSaveSolution.setFont(consolas16);
    btnSaveSolution.setBackground(Color.WHITE);
    btnSaveSolution.setForeground(Color.BLACK);
    btnSaveSolution.setSelected(true);
    btnSaveSolution.addActionListener(action -> Controller.calculate());
    btnSaveSolution.setBounds(width-270 , height-780, 220, 60);
    btnSaveSolution.setVisible(true);
    btnSaveSolution.setToolTipText("");
    btnSaveSolution.setToolTipText("Details der berechneten Optimierung als CSV herunterladen");
    dataTab.add(btnSaveSolution);

    tabbedPane.addTab("Daten", dataTab);
    tabbedPane.addTab("Karte", tab1);

    add(tabbedPane);
    setVisible(true);
  }
}