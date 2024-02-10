package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import services.mapper.LocationMapper;
import services.mapper.ScaleMapper;
import utils.CalculationUtils;
import utils.exceptions.NoLocationDataException;

public class LocationFinder {

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

    List<Helicopter> helicopterList = new ArrayList<>();
    for (int i = 0; i < numberOfHelicopter; i++) {
      helicopterList.add(new Helicopter(helicopterSpeed));
    }

    List<Location> locations = LocationMapper.mapToLocationObjects(data);
    locations.forEach(l -> System.out.println(l.getName()));


  }

  /**
   * Determines the first allocation of locations to helicopters. This is the starting point of the
   * calculation.
   *
   * @param locationList the list with all locations.
   * @param helicopterList the list of helicopters.
   */
  public static void determinePreDistribution(List<Location> locationList,
                                              List<Helicopter> helicopterList) {

    int helicopterNumber = helicopterList.size();
    List<List<Location>> sectoredLocations = generateSectorsAndAssignLocations(locationList, helicopterNumber);
    int[] helicopterAssignments = getHelicopterAssignmentsPerSector(sectoredLocations, helicopterNumber);
    Map<Helicopter, Location> helicopterLocationMap = new HashMap<>();

    for (int i = 0; i < helicopterAssignments.length; i++) {
      int assignments = helicopterAssignments[i];
      List<Location> l =  CalculationUtils.getRandomLocations()
    }

  }

  /**
   * Calculates the proportion of helicopters assigned to a sector based on the relative proportion
   * of locations in the sectors out of all locations.
   *
   * @param sectors The sector list with the amount of location in each sector.
   * @param helicopterNumber the number of helicopters.
   * @return An array with the number of helicopters that are assigned to the current sector.
   */
  public static int[] getHelicopterAssignmentsPerSector(List<List<Location>> sectors,
                                                        int helicopterNumber) {
    int percentageRate = 100 / helicopterNumber;
    int[] relativeShares = new int[sectors.size()];
    for (int i = 0; i < sectors.size(); i++) {
      int locationAmount = sectors.get(i).size();
      int ratio = (locationAmount / helicopterNumber) * 100;
      if (ratio < percentageRate) {
        relativeShares[i] = 0;
      } else {
        int shares = ratio / percentageRate;
        relativeShares[i] = shares;
      }
    }
    return relativeShares;
  }

  /**
   * Divides the entire area into sectors of equal size, as many in total as there are helicopters.
   * The locations are then assigned to the sectors
   *
   * @param locationList the list with all locations.
   * @param helicopterNumber the number of helicopters.
   * @return list of sectors with a list of locations assigned to the current sector.
   */
  public static List<List<Location>> generateSectorsAndAssignLocations(
      List<Location> locationList,
      int helicopterNumber) {

    List<List<Location>> distributionSectors = new ArrayList<>();
    for (int i = 0; i < helicopterNumber; i++) {
      distributionSectors.add(new ArrayList<Location>());
    }
    int[][] dimension = ScaleMapper.determineAxisDimensions(locationList);
    int[] xPosition = new int[2];
    xPosition[0] = dimension[0][0]; // min
    xPosition[1] = dimension[0][1]; // max
    int range = xPosition[1] - xPosition[0];

    int sectorRange = range / helicopterNumber;
    int sectorA = xPosition[0]; // left border
    int sectorB = sectorA + sectorRange; // right border
    for (int i = 0; i < distributionSectors.size(); i++) {
      for (Location location : locationList) {
        Coordinate coordinate = location.getCoordinate();
        if (coordinate.x() >= sectorA
            && coordinate.x() < sectorB) {
          distributionSectors.get(i).add(location);
        }
      }
      sectorA = sectorB;
      if (i == (distributionSectors.size() - 2)) {
        sectorB = (xPosition[1] + distributionSectors.size());
      } else {
        sectorB = (sectorA + sectorRange);
      }
    }
    return distributionSectors;
  }

}