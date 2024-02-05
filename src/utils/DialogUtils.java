package utils;

import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogUtils {

  public static FileNameExtensionFilter getCsvFilter() {
    return new FileNameExtensionFilter("CSV-Dateien (*.csv)", "csv");
  }
}