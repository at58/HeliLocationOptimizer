package test.algorithm;

import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import services.calculations.Locator;
import services.calculations.Scheduler;
import utils.TestData;
import utils.exceptions.NoLocationDataException;
import static org.junit.jupiter.api.Assertions.*;

public class SchedulerTest {

  @Test
  void flightTimeTest() {
    // arrange
    String numberHelicopter = "1";
    String speed = "60";
    List<String[]> testData = TestData.getStringArraysWithRandomCoordinatesAndAccidents(10, 250, 250);
    List<Helicopter> helicopterList;
    try {
      helicopterList = Locator.findOptimalPositions(numberHelicopter, speed, testData);
    } catch (NoLocationDataException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Flight Times from Base " + helicopterList.get(0).getUuid().toString() + " to:");
    Map<Location, Integer> flightTimes = Scheduler.calcFlightTime(helicopterList.get(0));
    for (Location location : flightTimes.keySet()) {
      System.out.println(location.getName() + ", time: " + flightTimes.get(location) + " minutes.");
    }
  }

  @Test
  void flightTimeCalculatorTest() {
    // arrange
    Helicopter helicopter = new Helicopter(180);
    helicopter.setCoordinates(100,10);
    helicopter.allocateLocation(new Location("A", 5, 10, 5), 95.0);
    helicopter.allocateLocation(new Location("B", 150, 10, 5), 50.0);
    int expected_A = 32;
    int expected_B = 17;
    // act
    Map<Location, Integer> flightTimes = Scheduler.calcFlightTime(helicopter);
    for (Location location : flightTimes.keySet()) {
      System.out.println(location.getName() + ", time: " + flightTimes.get(location) + " minutes.");
    }

    // assert
    List<Location> locationList= new ArrayList<>(flightTimes.keySet());
    assertEquals(expected_A, flightTimes.get(locationList.get(1)));
    assertEquals(expected_B, flightTimes.get(locationList.get(0)));
  }
}
