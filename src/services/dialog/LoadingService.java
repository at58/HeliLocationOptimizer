package services.dialog;

import java.io.File;
import java.util.Objects;
import javax.swing.JFileChooser;
import utils.DialogUtils;

public class LoadingService {

  private static String storagePath;

  public static File getPath() {

    File selectedFile = null;
    JFileChooser fileChooser;
    if (Objects.nonNull(storagePath)) {
      fileChooser = new JFileChooser(storagePath);
    } else {
      fileChooser = new JFileChooser(System.getProperty("user.home" + File.separator + "Desktop"));
    }
    fileChooser.setDialogTitle("CSV Importieren");
    fileChooser.setFileFilter(DialogUtils.getCsvFilter());
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
    }
    return selectedFile;
  }

  static void setStoragePath(File path) {
    // TODO (Ahmet): Check if it is possible to avoid the omission of the file name
    storagePath = path.getAbsolutePath().replace(path.getName(), "");
  }

  static String getStoragePath() {
    return storagePath;
  }
}