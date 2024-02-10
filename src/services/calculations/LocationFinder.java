package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import services.mapper.LocationMapper;
import services.mapper.ScaleMapper;
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

  public static void determinePreDistribution(List<Location> locationList, int helicopterNumber) {

    List<List<Location>> sectoredLocations = generateAndAssignToSectors(locationList, helicopterNumber);

  }

  public static List<List<Location>> generateAndAssignToSectors(
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