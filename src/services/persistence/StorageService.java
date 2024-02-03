package services.persistence;

import java.io.File;
import javax.swing.JFileChooser;

public class StorageService {

  public static File getStorageLocation(String defaultFileName) {

    File selectedFile = null;
    JFileChooser fileChooser = new JFileChooser(
        System.getProperty("user.home") + File.separator + "Desktop");
    fileChooser.setSelectedFile(new File(defaultFileName));
    fileChooser.setDialogTitle("Vorlage speichern");
    int result = fileChooser.showSaveDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
      LoadingService.setStoragePath(selectedFile);
    }
    return selectedFile;
  }
}