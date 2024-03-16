package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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

	public void drawHelicopterPositions(List<Helicopter> helicopterList) {
		this.helicopterList = helicopterList;
		cs.setWidthAndHeight(getWidth(), getHeight());
		repaint();
	}

	public void drawLocations(List<Location> locationList) {
		this.locationList = locationList;
		cs.setWidthAndHeight(getWidth(), getHeight());
		repaint();
	}

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

	private int getLocationAccidentAvg() {
		int accidents = 0;
		for (Location location : locationList) {
			accidents += location.getAccidents();
		}

		return accidents / locationList.size();
	}

	public static Image makeColorTransparent(Image im, final Color color) {
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

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (locationList != null) {
			ScaleMapper.specifyAxisScales(cs, locationList);

			if ((cs == null) || (locationList.size() == 0)) {
				locationList = null;
				return;
			}

			List<Coordinate> scaledCoordinates = drawAxes(g);

			// draw locations
			for (Coordinate coord : scaledCoordinates) {
//				CalculationUtils.accumulateTotalOfAccidents
				// TODO: Farbe anhand Unfallzahlen setzen bzw mischen?
				// locationList.get(scaledCoordinates.indexOf(coord)).getAccidents()
//				g.setColor(Color.GREEN);	
										//von 0 bis 215
//				g.setColor(new Color(255, x, 0));
				g.fillOval(coord.x() - 5, coord.y() - 5, 10, 10);
			}
		}
		
		if (helicopterList != null) {
//			x_Axis.getScale();

			List<Coordinate> scaledCoordinates = ScaleMapper.scaleCoordinates(cs, getCoordinates(null, helicopterList));
			Image image = null;
			try {
				//<a href="https://de.vecteezy.com/gratis-vektor/helicopter">Helicopter Vektoren von Vecteezy</a>
				image = ImageIO.read(new File(
						"C:\\Users\\Christoph\\Downloads\\vecteezy_helicopter-transportation-silhouette-vector-design_7926364_471\\helicopter.jpg"));				
				image = makeColorTransparent(image, Color.white);			    
			    
			} catch (IOException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}

			for (Coordinate coord : scaledCoordinates) {
				g.setColor(Color.blue);
				//draw helicopter
//				g.fillOval(coord.x() - 5, coord.y() - 5, 10, 10);

				

				Helicopter heli = helicopterList.get(scaledCoordinates.indexOf(coord));
				Map<Location, Double> locationHelicopterMap = heli.getLocationHelicopterMapping();
				List<Coordinate> scaledHeliLocations = ScaleMapper.scaleCoordinates(cs,
						getCoordinates(new ArrayList<Location>(locationHelicopterMap.keySet()), null));
				g.setColor(Color.MAGENTA);

				for (Coordinate coord2 : scaledHeliLocations) {
					g.drawLine(coord.x(), coord.y(), coord2.x(), coord2.y());
				}
				
				g.drawImage(image, coord.x() - 25, coord.y() - 25, 50, 50, null);
			}
		}
	}

	private List<Coordinate> drawAxes(Graphics g) {
		Axis x_Axis = cs.getXAxis();
		Axis y_Axis = cs.getYAxis();

		List<Integer> xAxisValues = x_Axis.getValues();
		List<Integer> yAxisValues = y_Axis.getValues();
		List<Integer> scaled_xAxisValues = new ArrayList<>(xAxisValues);
		List<Integer> scaled_yAxisValues = new ArrayList<>(yAxisValues);

		List<Coordinate> scaledCoordinates = ScaleMapper.scaleCoordinatesAndAxisValues(cs,
				getCoordinates(locationList, null), scaled_xAxisValues, scaled_yAxisValues);

		// draw x-axis
		g.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50);
		// draw y-axis
		g.drawLine(50, getHeight() - 50, 50, 50);

		// draw x-axis values
		int y = getHeight() - 55;
		for (int i = 0; i < scaled_xAxisValues.size(); i++) {
			int x = scaled_xAxisValues.get(i);

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
}
