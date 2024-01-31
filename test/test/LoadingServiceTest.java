package test;

import java.io.File;
import org.junit.jupiter.api.Test;
import services.persistence.LoadingService;
import services.persistence.StorageService;

import static org.junit.jupiter.api.Assertions.*;

public class LoadingServiceTest {

  @Test
  void LoadingLocationTest() {
    // Arrange
    File storageLocation = StorageService.getStorageLocation("Ortsdaten.csv");
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
