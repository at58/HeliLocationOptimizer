package utils;

import domain.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalculationUtils {

  public static List<Location> getRandomLocations(List<Location> locations, int amount) {

    Random random;
    List<Location> result;
    int locationSize = locations.size();;
    if (amount >= locationSize) {
      return locations;
    } else {
      List<Integer> alreadyAssignedLocations = new ArrayList<>();
      result = new ArrayList<>();
      random = new Random();
      for (int i = 0; i < locationSize; i++) {
        int nextIndex = random.nextInt(0, locationSize);
        if (!alreadyAssignedLocations.contains(nextIndex)) {
          result.add(locations.get(nextIndex));
          alreadyAssignedLocations.add(nextIndex);
        }
      }
    }
    return result;
  }
}
