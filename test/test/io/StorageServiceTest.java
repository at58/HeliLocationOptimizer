package test.io;

import java.io.File;
import org.junit.jupiter.api.Test;
import services.dialog.StorageService;
import utils.exceptions.DialogCancelledException;

import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceTest {

  @Test
  void storageLocationTest() throws DialogCancelledException {
    // arrange
    String expected = "C:\\Users\\toy\\Desktop\\Ortsdaten.csv";
    // act
    File actual = StorageService.getStorageLocation("Ortsdaten.csv", 1, "csv");
    // assert
    assertEquals(expected, actual.getAbsolutePath());
  }

  @Test
  void storageLocationTest_2() throws DialogCancelledException {
    // arrange
    String expected = "C:\\Users\\toy\\Downloads\\Beispiel.csv";
    // act
    File actual = StorageService.getStorageLocation("Beispiel.csv", 1, "csv");
    // assert
    assertEquals(expected, actual.getAbsolutePath());
  }
}