package services.dialog;

import java.io.File;
import javax.swing.JFileChooser;
import utils.DialogUtils;
import utils.exceptions.DialogCancelledException;

/**
 * Opens a dialog window to get the path of directory for saving a file.
 */
public class StorageService {

	public static File getStorageLocation(String defaultFileName, int context, String fileExtension) throws DialogCancelledException {

		String frameTitle = "";
		String errorMessage = "";
		if (context == 1) {
			frameTitle = "Vorlage speichern";
			errorMessage = "Das Dialogfenster zum Speichern der CSV-Vorlage wurde abgebrochen.";
		} else if (context == 2) {
			frameTitle = "Tabelle speichern";
			errorMessage = "Das Dialogfenster zum Speichern der Tabellen-Daten wurde abgebrochen.";
		} else if (context == 3) {
			frameTitle = "Lösung speichern";
			errorMessage = "Das Dialogfenster zum Speichern der Lösung wurde abgebrochen.";
		}

		File selectedFile;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(DialogUtils.getFileNameExtensionFilter(fileExtension));
		fileChooser.setDialogTitle(frameTitle);

		if (LoadingService.getStoragePath() == null) {
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
			fileChooser.setSelectedFile(new File(defaultFileName));
		} else {
			fileChooser.setCurrentDirectory(new File(LoadingService.getStoragePath()));
			// fileChooser.setSelectedFile(new
			// File(fileChooser.getSelectedFile().getName()));
		}
		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			LoadingService.setStoragePath(selectedFile);
		} else {
			throw new DialogCancelledException(errorMessage);
		}

		String fileExt = "." + fileExtension;
		if (!selectedFile.getAbsolutePath().endsWith(fileExt)) {
			selectedFile = new File(selectedFile.getAbsolutePath() + fileExt);
		}
		return selectedFile;
	}
}