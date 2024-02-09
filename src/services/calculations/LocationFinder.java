package services.calculations;

import controller.TableController;
import domain.Helicopter;
import domain.Location;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import services.mapper.LocationMapper;
import utils.exceptions.NoLocationDataException;

public class LocationFinder {

  public static void findOptimalPositions(String numberOfHeli, String speed, List<String[]> data)
      throws NumberFormatException, NoLocationDataException {

    if (numberOfHeli.isBlank() || speed.isBlank()) {
      throw new IllegalArgumentException("Missing inputs for number and/or speed of helicopter.");
    }
    int numberOfHelicopter = Integer.parseInt(numberOfHeli);
    int helicopterSpeed = Integer.parseInt(speed);

    List<Helicopter> helicopterList = new ArrayList<>();
    for (int i = 0; i < numberOfHelicopter; i++) {
      helicopterList.add(new Helicopter(helicopterSpeed));
    }

    if (data.isEmpty()) {
      throw new NoLocationDataException("No location data provided.");
    }
    data.forEach(d -> Arrays.stream(d).forEach(System.out::println));
    System.out.println("Location list items:");
    List<Location> locations = LocationMapper.mapToLocationObjects(data);
    locations.forEach(l -> System.out.println(l.getName()));

  }
}
