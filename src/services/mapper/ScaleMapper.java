package services.mapper;

import domain.Axis;
import domain.Coordinate;
import domain.Location;
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
  public static Map<Axis, List<Integer>> specifyAxisScales(List<Location> locationList) {

    Map<Axis, List<Integer>> axisScales = new HashMap<>();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    int[][] ranges = determineAxisDimensions(locationList);
    int x_range = ranges[0][1];
    int y_range = ranges[1][1];
    int x_axis = (int) (screen.getWidth() * 0.6);
    int y_axis = (int) (screen.getHeight() * 0.8);
    double x_conversionKey = (double) x_range / x_axis;
    double y_conversionKey = (double) y_range / y_axis;
    int x_interval = (x_axis / 10) + 1;
    int y_interval = (y_axis / 10) + 1;

    System.out.println("x_range: " + x_range);
    System.out.println("y-range: " + y_range);
    System.out.println("x-axis: " + x_axis);
    System.out.println("y-axis: " + y_axis);
    System.out.println("x conversion: " + x_conversionKey);
    System.out.println("y conversion: " + y_conversionKey);

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

    Axis xAxis = new Axis(domain.Dimension.X);
    xAxis.setScale(x_conversionKey);
    Axis yAxis = new Axis(domain.Dimension.Y);
    yAxis.setScale(y_conversionKey);
    axisScales.put(xAxis, x_scales);
    axisScales.put(yAxis, y_scales);

    return axisScales;
  }

  public static int[][] determineAxisDimensions(List<Location> locationList) {

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
