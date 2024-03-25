package services.mapper;

import domain.Axis;
import domain.Coordinate;
import domain.CoordinateSystem;
import domain.Location;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

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
	public static void specifyAxisScales(CoordinateSystem cs, List<Location> locationList) {

		if (locationList == null)
			return;
		else if (locationList.size() == 0)
			return;

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

		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();

		x_Axis.setScale(x_conversionKey);
		x_Axis.setValues(x_scales);

		y_Axis.setScale(y_conversionKey);
		y_Axis.setValues(y_scales);
	}

	/**
	 * determines the dimensions of the axis based on the given list of locations.
	 * 
	 * @param locationList The list of locations.
	 * @return A 2D array representing the axis dimensions: {{x_min, x_max}, {y_min,
	 *         y_max}}.
	 */
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

	/**
	 * calculates the scale factors for the coordinate system based on the given
	 * coordinates and assigns them to the coordinate system.
	 * 
	 * @param cs          The coordinate system.
	 * @param coordinates The list of coordinates.
	 */
	private static void calcScale(CoordinateSystem cs, List<Coordinate> coordinates) {
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

		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();

		for (int value : x_Axis.getValues()) {
			minX = Math.min(minX, value);
			maxX = Math.max(maxX, value);
		}

		for (int value : y_Axis.getValues()) {
			minY = Math.min(minY, value);
			maxY = Math.max(maxY, value);
		}

		int spacing = cs.getBorderSpacing();
		minX -= spacing;
		maxX += spacing;
		minY -= spacing;
		maxY += spacing;

		int rangeX = maxX - Math.abs(minX);
		int rangeY = maxY - Math.abs(minY);

		// calc scale factors
		double scaleX = (double) (cs.getParentWidth() - (spacing * 2)) / rangeX;
		double scaleY = (double) (cs.getParentHeight() - (spacing * 2)) / rangeY;

		x_Axis.setScale(scaleX);
		y_Axis.setScale(scaleY);
	}

	/**
	 * scales the coordinates and axis values based on the coordinate system and the
	 * given lists of coordinates, x-axis values, and y-axis values.
	 * 
	 * @param cs           The coordinate system.
	 * @param coordinates  The list of coordinates to be scaled.
	 * @param x_axisValues The list of x-axis values to be scaled.
	 * @param y_axisValues The list of y-axis values to be scaled.
	 * @return The scaled coordinates.
	 */
	public static List<Coordinate> scaleAxisValuesAndCoordinates(CoordinateSystem cs, List<Coordinate> coordinates,
			List<Integer> x_axisValues, List<Integer> y_axisValues) {

		calcScale(cs, coordinates);

		// get calculated scales
		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();

		double scaleX = x_Axis.getScale();
		double scaleY = y_Axis.getScale();
		int spacing = cs.getBorderSpacing();

		// scale x-axis values
		for (int i = 0; i < x_axisValues.size(); i++) {
			x_axisValues.set(i, (int) ((x_axisValues.get(i)) * scaleX) + spacing);
		}

		// scale y-axis values
		for (int i = 0; i < y_axisValues.size(); i++) {
			y_axisValues.set(i, (int) (cs.getParentHeight() - (y_axisValues.get(i) * scaleY)) - spacing);
		}

		return scaleCoordinates(cs, coordinates);
	}

	/**
	 * scales the coordinates based on the coordinate system and the given list of
	 * coordinates.
	 * 
	 * @param cs          The coordinate system.
	 * @param coordinates The list of coordinates to be scaled.
	 * @return The scaled coordinates.
	 */
	public static List<Coordinate> scaleCoordinates(CoordinateSystem cs, List<Coordinate> coordinates) {
		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();
		double scaleX = x_Axis.getScale();
		double scaleY = y_Axis.getScale();
		int spacing = cs.getBorderSpacing();

		List<Coordinate> scaledCoordinates = new ArrayList<>();
		// scale coordinates with factors
		for (Coordinate coordinate : coordinates) {
			int scaledX = (int) ((coordinate.x()) * scaleX) + spacing;
			int scaledY = (int) (cs.getParentHeight() - ((coordinate.y()) * scaleY)) - spacing;

			scaledCoordinates.add(new Coordinate(scaledX, scaledY));
		}

		return scaledCoordinates;
	}

}
