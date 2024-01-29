package controller;

import gui.GUI;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Application {

  public static void start() {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    GUI gui = new GUI(dimension);
  }
}