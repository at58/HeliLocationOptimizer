package test.algorithm;

import domain.Location;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import services.calculations.LocationFinder;

import static org.junit.jupiter.api.Assertions.*;

public class PreDistributionTest {

  /**
   * This test checks whether the number of location objects in each sector matches the expected
   * number. If the division of the objects into sectors works, the distribution must correspond to
   * the expected distribution depending on the width of the area in the x-direction and the number
   * of helicopters to be stationed.
   */
  @Test
  void rangeTest() {
    // arrange
    List<Location> locationList = List.of(new Location("A", 5, 10, 1),
                                          new Location("B", 7, 10,1),
                                          new Location("C", 11, 10,1),
                                          new Location("D", 35, 10,1),
                                          new Location("E", 41, 10,1)
                                          );
    int helicopterNumber = 3;
    List<Integer> expected = new ArrayList<>(List.of(3,0,1));

    // act
    List<List<Location>> actual = LocationFinder.generateSectorsAndAssignLocations(locationList, helicopterNumber);
    List<Integer> actualOccurrences = new ArrayList<>();
    actual.forEach(list -> actualOccurrences.add(list.size()));

    // arrange
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actualOccurrences.get(i));
    }
  }

  @Test
  void rangeTest_2() {
    // arrange
    List<Location> locationList = List.of(new Location("A", 5, 10, 1),
                                          new Location("B", 7, 10,1),
                                          new Location("C", 11, 10,1),
                                          new Location("D", 55, 10,1),
                                          new Location("E", 56, 10,1),
                                          new Location("F", 57, 10,1),
                                          new Location("G", 58, 10,1),
                                          new Location("H", 59, 10,1),
                                          new Location("I", 60, 10,1)
    );
    int helicopterNumber = 3;
    List<Integer> expected = new ArrayList<>(List.of(3,0,6));

    // act
    List<List<Location>> actual = LocationFinder.generateSectorsAndAssignLocations(locationList, helicopterNumber);
    List<Integer> actualOccurrences = new ArrayList<>();
    actual.forEach(list -> actualOccurrences.add(list.size()));

    // arrange
    assertEquals(expected.size(), actualOccurrences.size());

    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actualOccurrences.get(i));
    }
  }

  @Test
  void rangeTest_3() {
    // arrange
    List<Location> locationList = List.of(new Location("A", 5, 10, 1),
                                          new Location("B", 23, 10,1),
                                          new Location("C", 25, 10,1),
                                          new Location("D", 26, 10,1),
                                          new Location("E", 27, 10,1),
                                          new Location("F", 28, 10,1),
                                          new Location("G", 29, 10,1),
                                          new Location("H", 30, 10,1),
                                          new Location("I", 60, 10,1)
    );
    int helicopterNumber = 3;
    List<Integer> expected = new ArrayList<>(List.of(1,7,1));

    // act
    List<List<Location>> actual = LocationFinder.generateSectorsAndAssignLocations(locationList, helicopterNumber);
    List<Integer> actualOccurrences = new ArrayList<>();
    actual.forEach(list -> actualOccurrences.add(list.size()));

    // arrange
    assertEquals(expected.size(), actualOccurrences.size());

    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actualOccurrences.get(i));
    }
  }
}