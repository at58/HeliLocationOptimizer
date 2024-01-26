package test;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import services.importer.CsvParser;
import utils.Separator;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

  private final CsvParser csvParser = new CsvParser();

  @Test
  void CsvParserTest() {
    // arrange
    String path = "C:/Users/toy/IdeaProjects/HeliLocationOptimizer/test/resources/validCsvFile.csv";
    List<String []> expected = List.of(
        new String[] {"Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl p.a."},
        new String[] {"Musterstadt", "260", "80", "239"},
        new String[] {"StadtA", "120", "65", "150"},
        new String[] {"DorfB", "80", "40", "55"},
        new String[] {"MittelstadtC", "200", "100", "500"},
        new String[] {"KleinstadtD", "90", "75", "80"},
        new String[] {"DorfE", "75", "60", "30"},
        new String[] {"MetropoleF", "300", "150", "1000"},
        new String[] {"KleinstadtG", "95", "70", "90"},
        new String[] {"OrtH", "110", "55", "120"},
        new String[] {"StadtI", "130", "85", "200"},
        new String[] {"DorfJ", "70", "45", "25"},
        new String[] {"StadtK", "160", "75", "180"},
        new String[] {"KleinstadtL", "85", "65", "50"},
        new String[] {"DorfM", "60", "30", "15"},
        new String[] {"MittelstadtN", "250", "120", "800"},
        new String[] {"OrtO", "100", "50", "100"},
        new String[] {"DorfP", "55", "35", "20"},
        new String[] {"StadtQ", "140", "95", "220"},
        new String[] {"MetropoleR", "350", "180", "1200"}
    );

    // act
    List<String[]> actual = null;
    try {
      actual = csvParser.parse(path, Separator.SEMICOLON);
    } catch (IOException ignored) {}

    // assert
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < actual.size(); i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  void exceptionTest_EmptyCsv() {
    // arrange
    String path = "C:/Users/toy/IdeaProjects/HeliLocationOptimizer/test/resources/emptyCsv.csv";
    // act
    Executable executable = () -> csvParser.parse(path, null);
    // assert
    assertThrows(IOException.class, executable);
  }

  @Test
  void exceptionTest_OnlyHeader() {
    // arrange
    String path = "C:/Users/toy/IdeaProjects/HeliLocationOptimizer/test/resources/onlyHeader.csv";
    // act
    Executable executable = () -> csvParser.parse(path, null);
    // assert
    assertThrows(IOException.class, executable);
  }

  @Test
  void exceptionTest_OneTuple() {
    // arrange
    String path = "C:/Users/toy/IdeaProjects/HeliLocationOptimizer/test/resources/oneTuple.csv";
    List<String[]> expected = List.of(
        new String[] {"Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl p.a."},
        new String[] {"Musterstadt", "260", "80", "239"}
    );
    // act
    Executable executable = () -> csvParser.parse(path, null);
    List<String[]> actual = null;
    try {
      actual = csvParser.parse(path, Separator.SEMICOLON);
    } catch (IOException ignored) {}
    // assert
    assertDoesNotThrow(executable);
    assertNotNull(actual);

    for (int i = 0; i < actual.size(); i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  void csvParserTest_InconsistentTuples() {
    // arrange
    String path = "C:/Users/toy/IdeaProjects/HeliLocationOptimizer/test/resources/inconsistentCsv.csv";
    List<String[]> expected = List.of(
        new String[] {"Ort", "x-Koordinate", "y-Koordinate", "Unfallzahl p.a."},
        new String[] {"Musterstadt", "260", "80", "239"},
        new String[] {"DorfB", "80", "40", "55"},
        new String[] {"MittelstadtC", "200", "100", "500"},
        new String[] {"KleinstadtD", "90", "75", "80"},
        new String[] {"DorfE", "75", "60", "30"},
        new String[] {"KleinstadtG", "95", "70", "90"},
        new String[] {"OrtH", "110", "55", "120"},
        new String[] {"StadtI", "130", "85", "200"},
        new String[] {"StadtK", "160", "75", "180"},
        new String[] {"KleinstadtL", "85", "65", "50"},
//        new String[] {"DorfM", "60", "30", "15"}, // this tuple is invalid in the underlying csv file
        new String[] {"MittelstadtN", "250", "120", "800"},
        new String[] {"OrtO", "100", "50", "100"},
        new String[] {"DorfP", "55", "35", "20"},
        new String[] {"StadtQ", "140", "95", "220"},
        new String[] {"MetropoleR", "350", "180", "1200"}
    );
    // act
    List<String[]> actual = null;
    try {
      actual = csvParser.parse(path, Separator.SEMICOLON);
    } catch (IOException ignored) {}
    // assert
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < actual.size(); i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }
}