package gui;

import controller.Command;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBackground(Color.WHITE);
    tabbedPane.setFont(consolas16);

    JPanel tab1 = new JPanel();
    tab1.add(new JLabel("Elemente von Tab 1..."));
    tabbedPane.addTab("Karte", tab1);

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
    btnExit.addActionListener(action -> Command.closeApp());
    btnExit.setBounds(width-250 , height-180, 150, 60);
    btnExit.setVisible(true);
    dataTab.add(btnExit);

    JButton btnCalc = new JButton("Berechnen");
    btnCalc.setFont(consolas18);
    btnCalc.setBackground(Color.GREEN);
    btnCalc.setForeground(Color.BLACK);
    btnCalc.setSelected(true);
    btnCalc.addActionListener(action -> Command.calculate());
    btnCalc.setBounds(width-250 , height-280, 150, 60);
    btnCalc.setSelected(true);
    btnCalc.setVisible(true);
    dataTab.add(btnCalc);

    JButton btnUpload = new JButton("CSV hochladen");
    btnUpload.setFont(consolas16);
    btnUpload.setBackground(Color.WHITE);
    btnUpload.setForeground(Color.BLACK);
    btnUpload.setSelected(true);
    btnUpload.addActionListener(action -> Command.calculate());
    btnUpload.setBounds(width-250 , height-380, 150, 60);
    btnUpload.setSelected(true);
    btnUpload.setVisible(true);
    dataTab.add(btnUpload);

    tabbedPane.addTab("Daten", dataTab);

    add(tabbedPane);
    setVisible(true);
  }
}