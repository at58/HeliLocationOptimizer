package test.algorithm;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import services.calculations.Locator;
import services.mapper.LocationMapper;
import utils.TestData;
import utils.exceptions.NoLocationDataException;

import static org.junit.jupiter.api.Assertions.*;

public class LocatorTest {

  @Test
  void locatorTest() {

    String numberOfHelicopter = "1";
    String speed = "150";

    List<String[]> locationTuples = TestData.getStringArraysWithRandomCoordinatesAndAccidents(2, 100, 100);
    System.out.println("Locations: ");
    List<Location> locationList = LocationMapper.mapToLocationObjects(locationTuples);
    locationList.forEach(location -> {
      Coordinate c = location.getCoordinate();
      System.out.println(location.getName() + ", coordinate: (" + c.x() + ", " + c.y() + "), accidents: " + location.getAccidents());
    });

    try {
      System.out.println("\nHelicopter: ");
      List<Helicopter> helicopterPositions = Locator.findOptimalPositions(numberOfHelicopter, speed, locationTuples);
      helicopterPositions.forEach(helicopter -> {
        Coordinate c = helicopter.getCoordinate();
        Map<Location, Double> mapping = helicopter.getLocationHelicopterMapping();
        System.out.print("Coordinate: " + "(" + c.x() + ", " + c.y() + "), assignments: ");
        for (Location l : mapping.keySet()) {
          System.out.print(l.getName() + ", distance: " + mapping.get(l) + " | ");
        }
        System.out.println();
      });
    } catch (NoLocationDataException e) {
      e.printStackTrace();
    }
  }
}
