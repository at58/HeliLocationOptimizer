package services.calculations;

import core.Coordinate;

public class Euclid {

  public static double calculateDistance(Coordinate a, Coordinate b) {

    double xDistance = Math.abs(a.x() - b.x());
    double xSquare = Math.pow(xDistance, 2);

    double yDistance = Math.abs(a.y() - b.y());
    double ySquare = Math.pow(yDistance, 2);

    double cSquare = xSquare + ySquare;

    return Math.sqrt(cSquare);
  }
}