package gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.RGBImageFilter;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import domain.Axis;
import domain.Coordinate;
import domain.CoordinateSystem;
import domain.Helicopter;
import domain.Location;
import services.mapper.ScaleMapper;
import utils.CalculationUtils;

/**
 * DrawPane is a custom JPanel that provides various visual elements for
 * displaying helicopter positions and accident locations.
 */
public class DrawPane extends JPanel {

	private static final long serialVersionUID = 5416885486769951018L;

	private List<Helicopter> helicopterList;
	private List<Location> locationList;
	private CoordinateSystem cs;

	public DrawPane() {
		setLayout(null);
		setBackground(Color.WHITE);
		cs = CoordinateSystem.getInstance();
		cs.setBorderSpacing(50);
	}

	/**
	 * draws the positions of the helicopters passed in.
	 * 
	 * @param helicopterList List of helicopters whose positions are to be drawn.
	 */
	public void drawHelicopterPositions(List<Helicopter> helicopterList) {
		this.helicopterList = helicopterList;
		cs.setWidthAndHeight(getWidth(), getHeight());
		repaint();
	}

	/**
	 * draws the given accident locations.
	 * 
	 * @param locationList List of accident locations to be drawn.
	 */
	public void drawLocations(List<Location> locationList) {
		this.locationList = locationList;
		cs.setWidthAndHeight(getWidth(), getHeight());
		repaint();
	}

	/**
	 * retrieves a list of coordinates from either the list of locations or
	 * helicopters.
	 * 
	 * @param locations   List of locations to retrieve coordinates from. Can be
	 *                    null.
	 * @param helicopters List of helicopters to retrieve coordinates from. Can be
	 *                    null.
	 * @return List of coordinates extracted from the locations or helicopters.
	 */
	private static List<Coordinate> getCoordinates(List<Location> locations, List<Helicopter> helicopters) {
		List<Coordinate> coordinates = new ArrayList<>();

		if (locations != null)
			for (Location location : locations) {
				coordinates.add(location.getCoordinate());
			}
		else if (helicopters != null)
			for (Helicopter heli : helicopters) {
				coordinates.add(heli.getCoordinate());
			}

		return coordinates;
	}

	/**
	 * makes a specific color transparent in the given image.
	 * 
	 * @param im    The image to make transparent.
	 * @param color The color to make transparent.
	 * @return Image with the specified color made transparent.
	 */
	public static Image makeColorTransparent(Image img, final Color color) {
		ImageFilter filter = new RGBImageFilter() {
			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};

		ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	/**
	 * calculates the color for representing accident intensity based on the number
	 * of accidents.
	 * 
	 * @param accidents       Number of accidents.
	 * @param accidentHalfMax Half of the maximum accident count.
	 * @return Color representing the accident intensity.
	 */
	private Color getAccidentColor(int accidents, int accidentHalfMax) {

		int g;
		int b;

		// smaller than avg-range
		if (accidents <= (accidentHalfMax * 0.75)) {
			// Calculate g between 135 and 200
			g = 200 - (int) (65 * accidents / accidentHalfMax);
			g = Math.max(0, Math.min(255, g));
			return new Color(255, g, 0);
			// bigger than avg-range
		} else if (accidents >= (accidentHalfMax * 1.25)) {
			// Calculate b between 0 and 100
			b = (int) (100 * accidents / accidentHalfMax);
			b = Math.max(0, Math.min(100, b));
			return new Color(215, 0, b);
			// in avg range
		} else {
			// Calculate g between 0 and 135
			g = 135 - (int) (135 * accidents / accidentHalfMax);
			g = Math.max(0, Math.min(255, g));
			return new Color(255, g, 0);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (locationList != null) {
			ScaleMapper.specifyAxisScales(cs, locationList);

			// exit if no coordinate system or no locations
			if ((cs == null) || (locationList.size() == 0)) {
				locationList = null;
				return;
			}

			List<Coordinate> scaledCoordinates = drawAxes(g);
			int accidentMax = CalculationUtils.getAccidentsMax(locationList);

			// draw locations
			for (Coordinate coord : scaledCoordinates) {
				g.setColor(getAccidentColor(locationList.get(scaledCoordinates.indexOf(coord)).getAccidents(),
						accidentMax / 2));
				g.fillOval(coord.x() - 5, coord.y() - 5, 10, 10);
			}

			drawColoredAccidentScale(g, accidentMax);
		}

		if (helicopterList != null) {

			// try get image of helicopter
			Image image = null;
			try {
				// <a href="https://de.vecteezy.com/gratis-vektor/helicopter">Helicopter
				// Vektoren von Vecteezy</a>
				image = ImageIO.read(new File("src/helicopter.jpg"));
				image = makeColorTransparent(image, Color.white);

			} catch (IOException e) {
				image = null;
			}

			// get scaled coordinates for solution (helicopter postions)
			List<Coordinate> scaledCoordinates = ScaleMapper.scaleCoordinates(cs, getCoordinates(null, helicopterList));

			for (Coordinate coord : scaledCoordinates) {
				g.setColor(Color.blue);

				// get assigned locations and scale them
				Helicopter heli = helicopterList.get(scaledCoordinates.indexOf(coord));
				Map<Location, Double> locationHelicopterMap = heli.getLocationHelicopterMapping();
				List<Coordinate> scaledHeliLocations = ScaleMapper.scaleCoordinates(cs,
						getCoordinates(new ArrayList<Location>(locationHelicopterMap.keySet()), null));
				g.setColor(Color.MAGENTA);

				// draw lines from locations to assigned helicopter
				for (Coordinate coord2 : scaledHeliLocations) {
					g.drawLine(coord.x(), coord.y(), coord2.x(), coord2.y());
				}

				// draw helicopter
				if (image == null) {
					g.setColor(Color.BLUE);
					g.fillOval(coord.x() - 8, coord.y() - 8, 16, 16);
				} else
					g.drawImage(image, coord.x() - 25, coord.y() - 25, 50, 50, null);

				//draw helicopter index
				g.setColor(Color.BLACK);
				g.drawString(Integer.toString(helicopterList.indexOf(heli) + 1), coord.x() - 5, coord.y() - 15);
			}
		}
	}

	/**
	 * draws the axes on the graphics context and returns scaled coordinates.
	 * 
	 * @param g Graphics context.
	 * @return List of scaled coordinates.
	 */
	private List<Coordinate> drawAxes(Graphics g) {
		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();

		List<Integer> xAxisValues = x_Axis.getValues();
		List<Integer> yAxisValues = y_Axis.getValues();
		List<Integer> scaled_xAxisValues = new ArrayList<>(xAxisValues);
		List<Integer> scaled_yAxisValues = new ArrayList<>(yAxisValues);

		// get scaled coordinates from locations and scale for axes
		List<Coordinate> scaledCoordinates = ScaleMapper.scaleAxisValuesAndCoordinates(cs,
				getCoordinates(locationList, null), scaled_xAxisValues, scaled_yAxisValues);

		// draw x-axis
		g.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50);
		g.drawString("X", getWidth() - 40, getHeight() - 45);
		// draw y-axis
		g.drawLine(50, getHeight() - 50, 50, 50);
		g.drawString("Y", 47, 40);

		// draw x-axis values
		int y = getHeight() - 55;
		for (int i = 0; i < scaled_xAxisValues.size(); i++) {
			int x = scaled_xAxisValues.get(i);

			// draw value and line on axis
			g.fillRect(x - 3, y, 5, 10);
			String scaleStr = Integer.toString(xAxisValues.get(i));
			int strWidth = g.getFontMetrics().stringWidth(scaleStr);
			g.drawString(scaleStr, x - (strWidth / 2) - 1, y + 25);

			// draw bisector between two main axis-points
			if (i < scaled_xAxisValues.size() - 1) {
				int nextX = scaled_xAxisValues.get(i + 1);
				int midX = (x + nextX) / 2;
				g.fillRect(midX - 1, y + 2, 3, 7);
			}
		}

		// draw y-axis values
		int x = 45;
		for (int i = 0; i < scaled_yAxisValues.size(); i++) {
			y = scaled_yAxisValues.get(i);

			// draw value and line on axis
			g.fillRect(x, y - 3, 10, 5);
			String scaleStr = Integer.toString(yAxisValues.get(i));
			int strWidth = g.getFontMetrics().stringWidth(scaleStr);
			g.drawString(scaleStr, x - strWidth - 5, y + 5);

			// draw bisector between two main axis-points
			if (i < scaled_yAxisValues.size() - 1) {
				int nextY = scaled_yAxisValues.get(i + 1);
				int midY = (y + nextY) / 2;
				g.fillRect(x + 2, midY - 1, 7, 3);
			}
		}

		return scaledCoordinates;
	}

	/**
	 * draws the colored accident scale on the graphics context.
	 * 
	 * @param g           Graphics context.
	 * @param accidentMax Maximum number of accidents.
	 */
	private void drawColoredAccidentScale(Graphics g, int accidentMax) {
		int accidentCount = CalculationUtils.accumulateTotalOfAccidents(locationList);
		int accidentAvg = accidentCount / locationList.size();

		// draw rectangle with colored gradient for accident numbers
		int rectW = 400;
		Rectangle rect = new Rectangle(getWidth() - rectW - 20, 10, rectW, 25);

		Color startColor = new Color(255, 200, 0);
		Color endColor = new Color(210, 0, 100);

		GradientPaint gradient = new GradientPaint((float) rect.getX(), (float) rect.getY(), startColor,
				(float) (rect.getWidth() + rect.getX()), (float) rect.getY(), endColor, false);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(gradient);
		g2d.fill(rect);

		// draw black triangles below the rectangle
		int triangleHeight = 10;
		int triangleWidth = 10;

		// calculate the positions for the triangles
		int leftX = (int) rect.getX();
		int middleX = (int) (rect.getX() + rect.getWidth() / 2);
		int rightX = (int) (rect.getX() + rect.getWidth()) - triangleWidth;

		// calculate the position for the average triangle
		int avgX = (int) (rect.getX() + (accidentAvg / (float) accidentMax) * rect.getWidth()) - triangleWidth / 2;

		int triangleY = (int) (rect.getY() + rect.getHeight());
		int stringY = triangleY + triangleHeight + 12;

		// draw left triangle
		int[] leftXPoints = { leftX, leftX + triangleWidth, leftX + triangleWidth / 2 };
		int[] leftYPoints = { triangleY, triangleY, triangleY + triangleHeight };
		g2d.setColor(Color.BLACK);
		g2d.fillPolygon(leftXPoints, leftYPoints, 3);
		g2d.drawString("0", leftX + triangleWidth / 2 - 3, stringY);

		// draw middle triangle
		String half = Integer.toString(accidentMax / 2);
		int[] middleXPoints = { middleX, middleX + triangleWidth, middleX + triangleWidth / 2 };
		int[] middleYPoints = { triangleY, triangleY, triangleY + triangleHeight };
		g2d.fillPolygon(middleXPoints, middleYPoints, 3);
		g2d.drawString(half, middleX + triangleWidth / 2 - g.getFontMetrics().stringWidth(half) / 2, stringY);

		// draw right triangle
		String max = Integer.toString(accidentMax);
		int[] rightXPoints = { rightX, rightX + triangleWidth, rightX + triangleWidth / 2 };
		int[] rightYPoints = { triangleY, triangleY, triangleY + triangleHeight };
		g2d.fillPolygon(rightXPoints, rightYPoints, 3);
		g2d.drawString(max, rightX - g.getFontMetrics().stringWidth(max) + triangleWidth / 2 + 3, stringY);

		// draw triangle for average
		String avg = "⌀";
		int[] avgXPoints = { avgX, avgX + triangleWidth, avgX + triangleWidth / 2 };
		int[] avgYPoints = { triangleY, triangleY, triangleY + triangleHeight };
		g2d.fillPolygon(avgXPoints, avgYPoints, 3);
		g2d.drawString(avg, avgX + triangleWidth / 2 - g.getFontMetrics().stringWidth(avg) / 2, stringY);

		// draw scale caption
		java.awt.Font f = g2d.getFont();
		g2d.setFont(new java.awt.Font(f.getFontName(), f.getStyle(), 18));
		g2d.drawString("Unfallzahlen pro Jahr", (int) (rect.getX() + 5),
				(int) (rect.getY() + rect.getHeight() / 2) + 7);
	}
}
