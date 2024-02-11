package test.algorithm;

import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import services.calculations.Locator;
import services.calculations.PreDistributor;

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
    List<List<Location>> actual = PreDistributor.generateSectorsAndAssignLocations(locationList, helicopterNumber);
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
    List<List<Location>> actual = PreDistributor.generateSectorsAndAssignLocations(locationList, helicopterNumber);
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
    List<List<Location>> actual = PreDistributor.generateSectorsAndAssignLocations(locationList, helicopterNumber);
    List<Integer> actualOccurrences = new ArrayList<>();
    actual.forEach(list -> actualOccurrences.add(list.size()));

    // arrange
    assertEquals(expected.size(), actualOccurrences.size());

    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actualOccurrences.get(i));
    }
  }

  @Test
  void determinePreDistributionSizeTest() {
    // arrange
    List<Location> locations = new ArrayList<>(List.of(
        new Location("A", 5,10,20),
        new Location("B", 50,10,20),
        new Location("C", 51,10,20),
        new Location("D", 52,10,20),
        new Location("F", 53,10,20),
        new Location("G", 54,10,20),
        new Location("H", 55,10,20),
        new Location("I", 56,10,20)
    ));
    int helicopterNumber = 2;
    int expectedLocations = 2;
    Stack<Helicopter> helicopterStack = new Stack<>();
    for (int i = 1; i <= helicopterNumber; i++) {
      helicopterStack.add(new Helicopter(1));
    }

    // act
    Map<Helicopter,Location> helicopterLocationMap = PreDistributor.determinePreDistribution(
        locations, helicopterStack);

    // assert
    assertEquals(expectedLocations, helicopterLocationMap.size());

  }

  @Test
  void executeDeterminePreDistributionTest() {

    for (int i = 0; i < 10; i++) {
      determinePreDistributionTest("D");
    }
  }

  private void determinePreDistributionTest(String name) {
    // arrange
    List<Location> locations = new ArrayList<>(List.of(
        new Location("A", 5,10,20),
        new Location("B", 50,10,20),
        new Location("C", 51,10,20),
        new Location("D", 52,10,20),
        new Location("F", 53,10,20),
        new Location("G", 54,10,20),
        new Location("H", 55,10,20),
        new Location("I", 56,10,20)
    ));
    int helicopterNumber = 2;
    Stack<Helicopter> helicopterStack = new Stack<>();
    for (int i = 1; i <= helicopterNumber; i++) {
      helicopterStack.add(new Helicopter(1));
    }
    Map<Helicopter, Location> expected = new HashMap<>(Map.of(
        helicopterStack.get(0), locations.get(3),
        helicopterStack.get(1), locations.get(5)
    ));
    Set<Helicopter> helicopterSet = expected.keySet();
    List<Helicopter> helicopterList = new ArrayList<>(helicopterSet);

    // act
    Map<Helicopter,Location> helicopterLocationMap = PreDistributor.determinePreDistribution(
        locations, helicopterStack);

    // assert
    String l2 = helicopterLocationMap.get(helicopterList.get(0)).getName();

    System.out.println("Expected: " + name);
    System.out.println("Actual: " + l2);
    System.out.println("\n");
  }
}