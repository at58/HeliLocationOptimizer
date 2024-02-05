package services.persistence;

import java.io.File;
import javax.swing.JFileChooser;
import utils.DialogUtils;
import utils.exceptions.DialogCancelledException;

public class StorageService {

  public static File getStorageLocation(String defaultFileName) throws DialogCancelledException {

    File selectedFile = null;
    JFileChooser fileChooser = new JFileChooser(
        System.getProperty("user.home") + File.separator + "Desktop");
    fileChooser.setSelectedFile(new File(defaultFileName));
    fileChooser.setFileFilter(DialogUtils.getCsvFilter());
    fileChooser.setDialogTitle("Vorlage speichern");
    int result = fileChooser.showSaveDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
      LoadingService.setStoragePath(selectedFile);
    } else {
      throw new DialogCancelledException("Das Dialogfenster zum Speichern der CSV-Vorlage wurde abgebrochen.");
    }
    return selectedFile;
  }
}