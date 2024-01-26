package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

  private final Toolkit tk = Toolkit.getDefaultToolkit();

  public GUI() {
    setTitle("Helicopter Location Optimizer");
    Dimension dimension = tk.getScreenSize();
    int widht = (int) dimension.getWidth();
    int height = (int) dimension.getHeight() - 50;
    setSize(widht, height);
    setLocation((int) (dimension.getWidth() - widht)/2,5);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBackground(Color.ORANGE);
    tabbedPane.setFont(consolas16);

    JPanel tab1 = new JPanel();
    tab1.add(new JLabel("Elemente von Tab 1..."));
    tabbedPane.addTab("Karte", tab1);

    JPanel tab2 = new JPanel();
    tab2.add(new JLabel("Elemente von Tab 2..."));
    tabbedPane.addTab("Daten", tab2);

    add(tabbedPane);
    setVisible(true);
  }

  public static void main(String[] args) {
    GUI gui = new GUI();
  }
}