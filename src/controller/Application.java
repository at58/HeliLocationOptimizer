package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Initialize the dimension of the GUI and invokes the method of main controller which launches the
 * user interface.
 */
public class Application {

  public static void start() {

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    MainController.startGUI(dimension);
  }
}