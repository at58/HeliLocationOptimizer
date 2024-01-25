package services.mapper;

import core.Coordinate;
import core.Location;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScaleMapper {

  /**
   * scales the range of the locations to the axes of the map.
   *
   * @param locationList the list of all locations.
   * @param screen the size of the screen.
   * @param partitions the number of axis separations.
   * @return Map of String-key consisting of the axis with the conversion key and the array of intervals
   */
  public static Map<String, int[]> specifyAxisScales(List<Location> locationList,
                                         Dimension screen,
                                         int partitions) {

    Map<String, int[]> axisScales;
    if (partitions < 2) {
      throw new IllegalArgumentException("Die Anzahl der Partitionen ist kleiner als 2");
    } else {
      axisScales = new HashMap<>();
      int[][] ranges = determineAxisRanges(locationList);
      int x_range = ranges[0][1] - ranges[0][0];
      int y_range = ranges[1][1] - ranges[1][0];
      int x_axis = (int) screen.getWidth() - 100;
      int y_axis = (int) screen.getHeight() - 100;
      double x_conversionKey = (double) x_range / x_axis;
      double y_conversionKey = (double) y_range / y_axis;
      int x_interval = (int) ((x_range / partitions) * x_conversionKey);
      int y_interval = (int) ((y_range / partitions) * y_conversionKey);

      int[] x_scale = new int[partitions + 1];
      int[] y_scale = new int[partitions + 1];

      for (int i = 1; i <= partitions ; i++) {
        x_scale[i] = i * x_interval;
        y_scale[i] = i * y_interval;
      }
      axisScales.put("x-Axis/1:" + x_conversionKey, x_scale);
      axisScales.put("y-Axis/1:" + y_conversionKey, y_scale);
    }

    return axisScales;
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
