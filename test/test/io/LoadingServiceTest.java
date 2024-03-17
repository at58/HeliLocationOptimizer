package test.io;

import java.io.File;
import org.junit.jupiter.api.Test;
import services.dialog.LoadingService;
import services.dialog.StorageService;
import utils.exceptions.DialogCancelledException;

import static org.junit.jupiter.api.Assertions.*;

public class LoadingServiceTest {

  @Test
  void LoadingLocationTest() throws DialogCancelledException {
    // Arrange
    File storageLocation = StorageService.getStorageLocation("Ortsdaten.csv", 1, "csv");
    String expected = storageLocation.getAbsolutePath().replaceAll("\\\\", "/");
    System.out.println(expected);
    String fileName = storageLocation.getName();
    System.out.println(fileName);
    System.out.println(expected.replace(fileName, ""));
    // Act
    File actual = LoadingService.getPath();
    System.out.println(actual.getAbsolutePath());
    // Assert
    assertEquals(expected, actual.getAbsolutePath());
  }
}
