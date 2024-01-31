package services.persistence;

import java.io.File;
import java.util.Objects;
import javax.swing.JFileChooser;

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
    // fileChooser.setSelectedFile(new File(defaultFileName));
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
    }
    return selectedFile;
  }

  static void setStoragePath(File path) {
    storagePath = path.getAbsolutePath().replace(path.getName(), "");
  }
}
