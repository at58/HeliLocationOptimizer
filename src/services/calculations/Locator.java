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
    // locations.forEach(l -> System.out.println(l.getName()));

    Map<Helicopter, Location> firstMapping = PreDistributor.determinePreDistribution(locations, helicopterStack);


  }


}