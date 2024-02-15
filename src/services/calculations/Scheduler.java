package services.calculations;

import domain.Helicopter;
import domain.Location;
import java.util.HashMap;
import java.util.Map;

public class Scheduler {

  /**
   * Calculates the duration of flight from a current helicopter base to an assigned location.
   * The formular is: f(t) = t * a*km/h = b*km
   *
   * @param helicopter a current helicopter.
   * @return A map of locations that are assigned to the current helicopter and the travel time to it.
   */
  public static Map<Location, Integer> calcFlightTime(Helicopter helicopter) {

    Map<Location, Integer> flightTimes = new HashMap<>();
    Map<Location, Double> distances = helicopter.getLocationHelicopterMapping();
    int speed = helicopter.getSpeed();

    for (Location location : distances.keySet()) {
      double distance = distances.get(location);
      double t = (distance / speed);
      int minutes = (int) (t * 60) + 1;
      flightTimes.put(location, minutes);
    }
    return flightTimes;
  }
}
