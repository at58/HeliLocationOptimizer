package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    List<Helicopter> helicopterList = PreDistributor.determinePreDistribution(locations, helicopterStack);

    allocateClosestLocations(locations, helicopterList);
    determinePseudoFocus(helicopterList);
  }

  public static void determinePseudoFocus(List<Helicopter> helicopterList) {

    double weightSum = 0;
    double[] weightedPointSum = new double[] {0, 0}; // {x,y} -coordinates

    for (Helicopter helicopter : helicopterList) {
      Set<Location> assignedLocations = helicopter.getAssignedLocations();
      int totalAccidents = CalculationUtils.accumulateTotalOfAccidents(assignedLocations);
      for (Location location: assignedLocations) {
        int thisAccidents = location.getAccidents();
        double weight = getWeight(totalAccidents, thisAccidents);
        Coordinate locationCoordinate = location.getCoordinate();
        double weighted_X = locationCoordinate.x() * weight;
        double weighted_Y = locationCoordinate.y() * weight;
        weightedPointSum[0] = weightedPointSum[0] + weighted_X;
        weightedPointSum[1] = weightedPointSum[1] + weighted_Y;
        weightSum += weight;
      }
      helicopter.setCoordinates((int) weightedPointSum[0], (int) weightedPointSum[1]);
      System.out.println("weighted sum: " + weightSum);
      weightSum = 0;
      weightedPointSum[0] = 0;
      weightedPointSum[1] = 0;
    }
  }

  /**
   * This method assigns each helicopter the locations that are closest to the coordinates of the
   * respective helicopter.
   *
   * @param locations list of all locations
   * @param helicopters list of all helicopters that needs to be placed.
   */
  public static void allocateClosestLocations(List<Location> locations, List<Helicopter> helicopters) {

    /* first of all, clear all allocated locations to a helicopter to beginn a re-allocation of
    location objects.
    */
    clearAssignedLocations(helicopters);

    for (Location location : locations) {
      Coordinate locationCoordinate = location.getCoordinate();
      Helicopter closestSpot = null;
      double minimalDistance = Double.MAX_VALUE;
      for (Helicopter helicopter : helicopters) {
        Coordinate helicopterCoordinate = helicopter.getCoordinate();
        double distance = Euclid.calculateDistance(locationCoordinate, helicopterCoordinate);
        if (distance < minimalDistance) {
          minimalDistance = distance;
          closestSpot = helicopter;
        }
      }
      if (Objects.nonNull(closestSpot)) {
        closestSpot.allocateLocation(location, minimalDistance);
      }
    }
  }

  private static void clearAssignedLocations(List<Helicopter> helicopterList) {

    helicopterList.forEach(helicopter -> helicopter.getLocationHelicopterMapping().clear());
  }

  /**
   * Calculates the weighting according to the relative share of accidents at a location in relation
   * to the total number of accidents.
   *
   * @param totalAccidents total number of accidents.
   * @param accidents current accidents in one location.
   * @return the weighting.
   */
  public static double getWeight(int totalAccidents, int accidents) {

    return (double) accidents / totalAccidents;
  }
}