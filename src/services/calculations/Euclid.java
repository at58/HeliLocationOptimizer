package services.calculations;

import domain.Coordinate;

public class Euclid {

  /**
   * Calculates the Euclidean distance between two points in the coordinate system.
   *
   * @param a the first point
   * @param b the second point
   * @return the distance between these points.
   */
  public static double calculateDistance(Coordinate a, Coordinate b) {

    double xDistance = Math.abs(a.x() - b.x());
    double xSquare = Math.pow(xDistance, 2);

    double yDistance = Math.abs(a.y() - b.y());
    double ySquare = Math.pow(yDistance, 2);

    double cSquare = xSquare + ySquare;

    return Math.sqrt(cSquare);
  }
}