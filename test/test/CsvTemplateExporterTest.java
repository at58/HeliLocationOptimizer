package test;

import java.io.File;
import org.junit.jupiter.api.Test;
import services.exporter.CsvTemplateExporter;

import static org.junit.jupiter.api.Assertions.*;

public class CsvTemplateExporterTest {

  @Test
  void csvFileStorageTest() {
    // arrange
    File file;
    // act
    String storagedFilePath = CsvTemplateExporter.export(); // expected
    file = new File(storagedFilePath);
    // assert
    assertTrue(file.exists());
  }
}