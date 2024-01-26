package services.saving;

import java.awt.Font;
import java.io.File;
import javax.swing.JFileChooser;

public class StorageService {

  public static File getStorageLocation(String defaultFileName) {

    File selectedFile = null;
    JFileChooser fileChooser = new JFileChooser(
        System.getProperty("user.home") + File.separator + "Desktop");
    fileChooser.setSelectedFile(new File(defaultFileName));
    int result = fileChooser.showSaveDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      // Holen Sie die ausgewählte Datei
      selectedFile = fileChooser.getSelectedFile();
    }
    return selectedFile;
  }
}