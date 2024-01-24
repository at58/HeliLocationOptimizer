package test;

import static org.junit.jupiter.api.Assertions.*;

import core.Location;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import services.mapper.Mapper;

class MapperTest {

  @Test
  void mapToLocationObjectsTest_1() {
    // arrange
    List<String[]> input = new ArrayList<>(List.of(
        new String[] {"Ort", "x-Koordinate", "y-Koordinate", "Unfallzahlen p.a."},
        new String[] {"Schwalbach", "16", "109", "26"}
    ));
    List<Location> expected = new ArrayList<>();
    expected.add(new Location("Schwalbach", 16, 109, 26));
    // act
    List<Location> actual = Mapper.mapToLocationObjects(input);
    // assert
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertArrayEquals(expected.get(i).toStringArray(), actual.get(i).toStringArray());
    }
  }

  @Test
  void mapToLocationObjectsTest_2() {
    // arrange
    List<String[]> input = new ArrayList<>(List.of(
        new String[] {"x-Koordinate", "y-Koordinate", "Ort", "Unfallzahlen p.a."},
        new String[] {"16", "109", "Schwalbach", "26"}
    ));
    List<Location> expected = new ArrayList<>();
    expected.add(new Location("Schwalbach", 16, 109, 26));
    // act
    List<Location> actual = Mapper.mapToLocationObjects(input);
    // assert
    assertEquals(expected.size(), actual.size());
  }
}