package utils;

import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogUtils {
  
  public static FileNameExtensionFilter getFileNameExtensionFilter(String extension) {
	  return new FileNameExtensionFilter(extension.toUpperCase() + "-Dateien (*." + extension + ")", extension);  
  }
}