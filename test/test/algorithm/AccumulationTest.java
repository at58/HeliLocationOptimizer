package test.algorithm;

import domain.Location;
import java.util.Set;
import org.junit.jupiter.api.Test;
import utils.CalculationUtils;

import static org.junit.jupiter.api.Assertions.*;

public class AccumulationTest {

  @Test
  void accidentAccumulationTest() {

    // arrange
    Set<Location> locationSet = Set.of(
        new Location("A", 1,1,5),
        new Location("B", 1,1,2),
        new Location("C", 1,1,106),
        new Location("D", 1,1,56),
        new Location("E", 1,1,921),
        new Location("F", 1,1,48),
        new Location("G", 1,1,365),
        new Location("H", 1,1,85),
        new Location("I", 1,1,321),
        new Location("J", 1,1,13)
        );
    int expected = 1922;
    // act
    int actual = CalculationUtils.accumulateTotalOfAccidents(locationSet);
    // assert
    assertEquals(expected, actual);
  }
}
