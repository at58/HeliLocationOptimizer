package services.saving;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Workbook;
import services.exporter.XlsxTemplate;
import utils.log.Logger;

@Deprecated
public class XlsxSaver {

  public static void save() {

    JFileChooser fileChooser = new JFileChooser(
        System.getProperty("user.home") + File.separator + "Desktop");
    fileChooser.setSelectedFile(new File("Ortsdaten.xlsx"));
    int result = fileChooser.showSaveDialog(null);

    // Überprüfen Sie, ob der Benutzer "Öffnen" ausgewählt hat
    if (result == JFileChooser.APPROVE_OPTION) {
      // Holen Sie die ausgewählte Datei
      File selectedFile = fileChooser.getSelectedFile();
      Workbook workbook = XlsxTemplate.get();

      saveWorkbookAsXlsx(workbook, selectedFile);
      System.out.println("Ausgewählte Datei: " + selectedFile.getAbsolutePath());
    } else {
      System.out.println("Aktion abgebrochen");
    }
  }

  private static void saveWorkbookAsXlsx(Workbook workbook, File file) {
    try (FileOutputStream fileOut = new FileOutputStream(file)) {
      workbook.write(fileOut);
      workbook.close();
    } catch (IOException e) {
      Logger.log(e.getMessage());
    }
  }

/*  public static void main(String[] args) {
    save();
  }*/
}