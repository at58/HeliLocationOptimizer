package services.calculations;

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
}
