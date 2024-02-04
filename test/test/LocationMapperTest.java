package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.Location;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import services.mapper.LocationMapper;

class LocationMapperTest {

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
    List<Location> actual = LocationMapper.mapToLocationObjects(input);
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
        new String[] {"16", "109", "Wetzlar", "26"}
    ));
    List<Location> expected = new ArrayList<>();
    expected.add(new Location("Wetzlar", 16, 109, 26));
    // act
    List<Location> actual = LocationMapper.mapToLocationObjects(input);
    // assert
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertArrayEquals(expected.get(i).toStringArray(), actual.get(i).toStringArray());
    }
  }

  @Test
  void mapToLocationObjectsTest_3() {
    // arrange
    List<String[]> input = new ArrayList<>(List.of(
        new String[] {"Ort", "Unfallzahlen p.a.", "y-Koordinate", "x-Koordinate"},
        new String[] {"Wetzlar", "26", "109", "16"}
    ));
    List<Location> expected = new ArrayList<>();
    expected.add(new Location("Wetzlar", 16, 109, 26));
    // act
    List<Location> actual = LocationMapper.mapToLocationObjects(input);
    // assert
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertArrayEquals(expected.get(i).toStringArray(), actual.get(i).toStringArray());
    }
  }


}