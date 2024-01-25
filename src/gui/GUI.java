package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Test GUI
 */
public class GUI extends JFrame {

  private final Toolkit tk = Toolkit.getDefaultToolkit();

  public GUI() {
    setTitle("GUI");
    Dimension dimension = tk.getScreenSize();
    setSize((int) dimension.getWidth(), (int) dimension.getHeight() - 50);

    JTabbedPane tabbedPane = new JTabbedPane();

    JPanel tab1 = new JPanel();
    tab1.add(new JLabel("Inhalt von Tab 1"));
    tabbedPane.addTab("Karte", tab1);

    JPanel tab2 = new JPanel();
    tab2.add(new JLabel("Inhalt von Tab 2"));
    tabbedPane.addTab("Daten", tab2);

    add(tabbedPane);
    setVisible(true);
  }

  public static void main(String[] args) {
    GUI gui = new GUI();
  }
}
