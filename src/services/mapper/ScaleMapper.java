package services.mapper;

import core.Coordinate;
import core.Location;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScaleMapper {

  /**
   * Scales the range of the locations to the axes of the map. The number of partitions is a fix
   * number of 10.
   *
   * @param locationList the list of all locations.
   * @return Map of String-key consisting of the axis with the conversion key and the list of intervals
   */
  public static Map<String, List<Integer>> specifyAxisScales(List<Location> locationList) {

    Map<String, List<Integer>> axisScales;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    axisScales = new HashMap<>();
    int[][] ranges = determineAxisRanges(locationList);
    int x_range = ranges[0][1];
    System.out.println("x_range: " + x_range);
    int y_range = ranges[1][1];
    System.out.println("y-range: " + y_range);
    int x_axis = (int) (screen.getWidth() * 0.6);
    System.out.println("x-axis: " + x_axis);
    int y_axis = (int) (screen.getHeight() * 0.8);
    System.out.println("y-axis: " + y_axis);
    double x_conversionKey = (double) x_range / x_axis;
    System.out.println("x conversion: " + x_conversionKey);
    double y_conversionKey = (double) y_range / y_axis;
    System.out.println("y conversion: " + y_conversionKey);
    int x_interval = (x_axis / 10) + 1;
    int y_interval = (y_axis / 10) + 1;

    List<Integer> x_scales = new ArrayList<>();
    List<Integer> y_scales = new ArrayList<>();

    int x_steps = (int) (x_interval * x_conversionKey);
    int y_steps = (int) (y_interval * y_conversionKey);
    System.out.println("y_intervall: " + y_interval + "\nx_intervall: " + x_interval);
    System.out.println("y_steps: " + y_steps + "\nx_steps: " + x_steps);

    int nextXPosition = x_steps;
    int nextYPosition = y_steps;

    while (nextYPosition <= (y_range + y_steps)) {
      y_scales.add(nextYPosition);
      nextYPosition += y_steps;
    }
    while (nextXPosition <= (x_range + x_steps)) {
      x_scales.add(nextXPosition);
      nextXPosition += x_steps;
    }

    System.out.println("\nX-SCALES:");
    x_scales.forEach(System.out::println);
    System.out.println("\nY-SCALES:");
    y_scales.forEach(System.out::println);

    axisScales.put("x-Axis/1:" + x_conversionKey, x_scales);
    axisScales.put("y-Axis/1:" + y_conversionKey, y_scales);

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
      }
      if (x > x_max) {
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
