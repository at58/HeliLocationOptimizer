package services.mapper;

import core.Coordinate;
import core.Location;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScaleMapper {

  public static Object specifyAxisScales(List<Location> locationList,
                                         Dimension screen,
                                         int partitions) {

    Map<String, int[]> axisScales;
    if (partitions < 2) {
      // exception
    } else {
      axisScales = new HashMap<>();
      int[][] ranges = determineAxisRanges(locationList);
      int x_range = ranges[0][1] - ranges[0][0];
      int y_range = ranges[1][1] - ranges[1][0];
      int xInterval = x_range / partitions;
      int yInterval = y_range / partitions;

    }

    return null;
  }

  public static int[][] determineAxisRanges(List<Location> locationList) {

    int x_min = Integer.MAX_VALUE;
    int x_max = 0;

    int y_min = Integer.MAX_VALUE;
    int y_max = 0;

    for (Location location : locationList) {
      Coordinate coordinate = location.getCoordinate();
      int x = coordinate.x();
      if (x < x_min) {
        x_min = x;
      } else if (x > x_max) {
        x_max = x;
      }

      int y = coordinate.y();
      if (y < y_min) {
        y_min = y;
      } else if (y > y_max) {
        y_max = y;
      }
    }
    return new int[][] {{x_min, x_max}, {y_min, y_max}};
  }
}
