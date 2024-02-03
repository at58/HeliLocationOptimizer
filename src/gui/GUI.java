package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

/**
 * Test GUI
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

    JPanel dataTab = new JPanel();
    dataTab.setLayout(new GridLayout(2,1));

    ControlPane controlPane = new ControlPane(new Point(100,100));
    InputPane inputPane = new InputPane(new Point(1,1));

    dataTab.add(inputPane);
    dataTab.add(controlPane);

    tabbedPane.addTab("Daten", dataTab);
    tabbedPane.addTab("Karte", mapTab);

    add(tabbedPane);
    setVisible(true);
  }
}