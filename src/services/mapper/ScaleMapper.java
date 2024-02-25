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

/**
 * Mapper for the axis scales of the coordinate map embedded in the gui.
 */
public class ScaleMapper {

  	/**
	 * Scales the range of the locations to the axes of the map. The number of
	 * partitions is a fix number of 10.
	 *
	 * @param locationList the list of all locations.
	 * @return Map of String-key consisting of the axis with the conversion key and
	 *         the list of intervals
	 */
	public static Map<Axis, List<Integer>> specifyAxisScales(List<Location> locationList) {

		if (locationList == null)
			return null;
		else if (locationList.size() == 0)
			return null;
		
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

//	    System.out.println("x_range: " + x_range);
//	    System.out.println("y-range: " + y_range);
//	    System.out.println("x-axis: " + x_axis);
//	    System.out.println("y-axis: " + y_axis);
//	    System.out.println("x conversion: " + x_conversionKey);
//	    System.out.println("y conversion: " + y_conversionKey);

		List<Integer> x_scales = new ArrayList<>();
		List<Integer> y_scales = new ArrayList<>();

		int x_steps = (int) (x_interval * x_conversionKey);
		int y_steps = (int) (y_interval * y_conversionKey);
//	    System.out.println("y_intervall: " + y_interval + "\nx_intervall: " + x_interval);
//	    System.out.println("y_steps: " + y_steps + "\nx_steps: " + x_steps);

		int nextXPosition = 0;
		int nextYPosition = 0;

		while (nextYPosition <= (y_range + y_steps)) {
			y_scales.add(nextYPosition);
			nextYPosition += y_steps;
		}
		while (nextXPosition <= (x_range + x_steps)) {
			x_scales.add(nextXPosition);
			nextXPosition += x_steps;
		}

//	    System.out.println("\nX-SCALES:");
//	    x_scales.forEach(System.out::println);
//	    System.out.println("\nY-SCALES:");
//	    y_scales.forEach(System.out::println);

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
		return new int[][] { { x_min, x_max }, { y_min, y_max } };
	}

	public static List<Coordinate> scaleCoordinates(List<Coordinate> coordinates, int panelWidth, int panelHeight,
			List<Integer> x_axis, List<Integer> y_axis) {

		// find min and max values
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Coordinate coordinate : coordinates) {
			minX = Math.min(minX, coordinate.x());
			maxX = Math.max(maxX, coordinate.x());

			minY = Math.min(minY, coordinate.y());
			maxY = Math.max(maxY, coordinate.y());
		}

		minX -= 50;
		maxX += 50;
		minY -= 50;
		maxY += 50;

		int rangeX = maxX - Math.abs(minX);
		int rangeY = maxY - Math.abs(minY);

		// calc scale factors
		double scaleX = (double) (panelWidth - 100) / rangeX;
		double scaleY = (double) (panelHeight - 100) / rangeY;

		List<Coordinate> scaledCoordinates = new ArrayList<>();
		// scale coordinates
		for (Coordinate coordinate : coordinates) {
			int scaledX = (int) ((coordinate.x()) * scaleX) + 50;
			int scaledY = (int) (panelHeight - ((coordinate.y()) * scaleY)) - 50;

			scaledCoordinates.add(new Coordinate(scaledX, scaledY));
		}

		// scale x-axis values
		for (int i = 0; i < x_axis.size(); i++) {
			x_axis.set(i, (int) ((x_axis.get(i)) * scaleX) + 50);
		}

		// scale y-axis values
		for (int i = 0; i < y_axis.size(); i++) {
			y_axis.set(i, (int) (panelHeight - (y_axis.get(i) * scaleY)) - 50);
		}

		return scaledCoordinates;
	}

}
