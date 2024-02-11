package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import services.mapper.LocationMapper;
import services.mapper.ScaleMapper;
import utils.CalculationUtils;
import utils.exceptions.NoLocationDataException;

public class Locator {

  public static void findOptimalPositions(String numberOfHeli, String speed, List<String[]> data)
      throws NumberFormatException, NoLocationDataException {

    if (numberOfHeli.isBlank() || speed.isBlank()) {
      throw new IllegalArgumentException("Missing inputs for number and/or speed of helicopter.");
    }
    if (data.isEmpty()) {
      throw new NoLocationDataException("No location data provided.");
    }

    int numberOfHelicopter = Integer.parseInt(numberOfHeli);
    int helicopterSpeed = Integer.parseInt(speed);

    Stack<Helicopter> helicopterStack = new Stack<>();
    for (int i = 0; i < numberOfHelicopter; i++) {
      helicopterStack.add(new Helicopter(helicopterSpeed));
    }

    List<Location> locations = LocationMapper.mapToLocationObjects(data);
    List<Helicopter> initialHelicopterPositions = PreDistributor.determinePreDistribution(locations, helicopterStack);

    for (Location location : locations) {
      Coordinate locationCoordinate = location.getCoordinate();
      Helicopter closestSpot = null;
      double minimalDistance = Double.MAX_VALUE;
      for (Helicopter helicopter : initialHelicopterPositions) {
        Coordinate helicopterCoordinate = helicopter.getCoordinate();
        double distance = Euclid.calculateDistance(locationCoordinate, helicopterCoordinate);
        if (distance < minimalDistance) {
          minimalDistance = distance;
          closestSpot = helicopter;
        }
      }
      closestSpot.allocateLocation(location, minimalDistance);
    }
  }

  public static double getWeight(List<Location> locations, int accidents) {

    return (double) accidents / CalculationUtils.accumulateTotalOfAccidents(locations);
  }


}