package controller;

import gui.GUI;
import java.awt.Toolkit;

public class Application {

  public static void start() {

    GUI gui = new GUI(Toolkit.getDefaultToolkit().getScreenSize());
  }
}