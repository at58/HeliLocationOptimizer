package services.calculations;

import domain.Coordinate;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import services.mapper.ScaleMapper;
import utils.CalculationUtils;

public class PreDistributor {

  /**
   * <p>
   *   Determines the first allocation of locations to helicopters. This is the starting point of the
   *   calculation. The First step is to divide the entire area into sectors of equal size, one for
   *   each helicopter. So if there are five helicopters that need to be located, there are five
   *   sectors. After that, every location in each sector is collected into a list. Then, an array
   *   with the number of helicopters that need to be placed in each sector is generated. The
   *   allocation logic of helicopters is described in
   *   {@link PreDistributor#getHelicopterAssignmentsPerSector(List, int, int)}.
   *   Finally, as many helicopters as specified in this array are assigned to the locations of each
   *   sector. By doing this, the allocation of each helicopter to a locations is selected randomly.
   * </p>
   * <br>
   * <p><b>For example:</b></p>
   * <p>
   *   There are 7 locations in sector A and 1 locations in sector B.
   *   The total amount of helicopters that need to be placed is 2.
   *   So the relative share of helicopter for sector A is 2 because 7/8 * 100 = 87,5 % since one
   *   sector receives one helicopter for every 50 % percentage reached, because 100 % divided by
   *   2 sectors (because 2 helicopters) is 50 % per sector. Consequently sector B gets zero helicopters.
   * </p>
   *
   * @param locationList the list with all locations.
   * @param helicopterStack the list of helicopters.
   */
  public static List<Helicopter> determinePreDistribution(List<Location> locationList,
                                                                   Stack<Helicopter> helicopterStack) {

    int helicopterNumber = helicopterStack.size();
    List<List<Location>> zonedLocations = generateSectorsAndAssignLocations(locationList, helicopterNumber);
    int[] helicopterAssignments = getHelicopterAssignmentsPerSector(zonedLocations,
                                                                    locationList.size(),
                                                                    helicopterNumber);
    List<Helicopter> initialHelicopterPositions = new ArrayList<>();

    for (int i = 0; i < helicopterAssignments.length; i++) {
      int assignments = helicopterAssignments[i];
      if (assignments == 0) {
        continue;
      }
      List<Location> randomLocations =  CalculationUtils.getRandomLocations(zonedLocations.get(i), assignments);
      for (Location location : randomLocations) {
        Coordinate locationCoordinate = location.getCoordinate();
        Helicopter helicopter = helicopterStack.pop();
        helicopter.setCoordinates(locationCoordinate.x(), locationCoordinate.y());
        initialHelicopterPositions.add(helicopter);
      }
    }
    return initialHelicopterPositions;
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
                                                        int totalNumberOfLocations,
                                                        int helicopterNumber) {
    double allocationKey = (double) 100 / helicopterNumber; // average share per sector
    int[] relativeShares = new int[sectors.size()];
    for (int i = 0; i < sectors.size(); i++) {
      int locationAmount = sectors.get(i).size();
      double ratio = (((double) locationAmount) / totalNumberOfLocations) * 100;
      if (ratio < (allocationKey / 2)) {
        relativeShares[i] = 0;
      } else {
        int shares = (int) (ratio / allocationKey);
        double decimal = (ratio / allocationKey) - shares;
        if (decimal >= 0.5) {
          shares = shares + 1;
        }
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

    // initialize sector lists
    List<List<Location>> distributionSectors = new ArrayList<>();
    for (int i = 1; i <= helicopterNumber; i++) {
      distributionSectors.add(new ArrayList<Location>());
    }
    if (locationList.size() < 2) {
      distributionSectors.set(0, locationList);
      return distributionSectors;
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