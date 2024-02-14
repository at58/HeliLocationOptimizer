package test.algorithm;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
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

  @Test
  void HeliCoordinateTestWith2Locations() {

    String numberOfHelicopter = "1";
    String speed = "150";

    List<String[]> locationTuples = new ArrayList<>(List.of(
        new String[] {"A", "10", "10", "1"},
        new String[] {"A", "20", "10", "1"}
        )
    );

    List<Helicopter> helicopterPositions = new ArrayList<>();
    try {
      System.out.println("\nHelicopter: ");
      helicopterPositions = Locator.findOptimalPositions(numberOfHelicopter, speed, locationTuples);
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
    Coordinate helicopterCoordinate = helicopterPositions.get(0).getCoordinate();
    assertEquals(15, helicopterCoordinate.x());
    assertEquals(10, helicopterCoordinate.y());
  }

  @Test
  void HeliCoordinateTestWith10Locations() {

    String numberOfHelicopter = "2";
    String speed = "150";

    List<String[]> locationTuples = new ArrayList<>(List.of(
        new String[] {"A", "10", "10", "19"},
        new String[] {"B", "50", "26", "5"},
        new String[] {"C", "55", "10", "20"},
        new String[] {"D", "12", "46", "5"},
        new String[] {"F", "11", "60", "4"},
        new String[] {"G", "50", "5", "17"},
        new String[] {"H", "36", "19", "11"},
        new String[] {"I", "45", "20", "13"},
        new String[] {"J", "44", "10", "1"},
        new String[] {"K", "5", "56", "9"})
    );

    List<Helicopter> helicopterPositions = new ArrayList<>();
    try {
      System.out.println("\nHelicopter: ");
      helicopterPositions = Locator.findOptimalPositions(numberOfHelicopter, speed, locationTuples);
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
    /*Coordinate helicopterCoordinate = helicopterPositions.get(0).getCoordinate();
    assertEquals(15, helicopterCoordinate.x());
    assertEquals(10, helicopterCoordinate.y());*/
  }
}
