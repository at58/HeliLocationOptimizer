package test;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import services.exporter.CsvExporter;

import static org.junit.jupiter.api.Assertions.*;

public class CsvExporterTest {

  /**
   * This test will open a dialog window for saving files. The test result depends on the selection
   * of a storage location. The test can only be passed if a storage location is selected.
   */
  @Test
  void csvFileStorageTest() {
    // arrange
    File file;
    // act
    String storagedFilePath = CsvExporter.exportTemplate(); // expected
    file = new File(storagedFilePath);
    // assert
    assertTrue(file.exists());
  }

  /**
   * This test will open a dialog window for saving files. For this test, it is necessary to cancel
   * the saving process to verify the expected exception.
   */
  @Test
  void csvFileStorageCancellationTest() {
    // arrange

    // act
    Executable testExecution = new Executable() {
      @Override
      public void execute() throws Throwable {
        CsvExporter.exportTemplate();
      }
    };
    // assert
    assertThrows(NullPointerException.class, testExecution);
  }
}