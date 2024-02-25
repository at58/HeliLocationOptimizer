package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import controller.TableController;
import domain.Axis;
import domain.Coordinate;
import domain.Dimension;
import domain.Helicopter;
import domain.Location;
import services.mapper.LocationMapper;
import services.mapper.ScaleMapper;

public class DrawPane extends JPanel {

	private static final long serialVersionUID = 5416885486769951018L;

	private List<Helicopter> helicopterList;
	private List<Location> locationList;

	private Axis x_Axis;
	private Axis y_Axis;

	public DrawPane() {
		setLayout(null);
		setBackground(Color.WHITE);
	}

	public void drawHelicopterPositions(List<Helicopter> helicopterList) {
		this.helicopterList = helicopterList;
		repaint();
	}

	public void drawLocations(List<Location> locationList) {
		this.locationList = locationList;
		repaint();
	}	

	private static List<Coordinate> getCoordinates(List<Location> locations) {
		List<Coordinate> coordinates = new ArrayList<>();
		for (Location location : locations) {
			coordinates.add(location.getCoordinate());
		}
		return coordinates;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (locationList != null) {
			Map<Axis, List<Integer>> axesData = ScaleMapper.specifyAxisScales(locationList);

			if ((axesData == null) || (locationList.size() == 0)) {
				locationList = null;
				return;
			}

			List<Coordinate> scaledCoordinates = drawAxes(g, axesData);

			// draw locations
			for (Coordinate coord : scaledCoordinates) {
				g.fillOval(coord.x() - 5, coord.y() - 5, 10, 10);
			}
		}

		if (helicopterList != null)
			for (Helicopter helicopter : helicopterList) {
				Coordinate coord = helicopter.getCoordinate();

			}
	}
	
	private void specifyAxis(Set<Axis> keySet) {
		List<Axis> keyList = new ArrayList<>(keySet);
		
		if (keyList.get(0).getDimension() == Dimension.X) {
			x_Axis = keyList.get(0);
			y_Axis = keyList.get(1);
		} else {
			x_Axis = keyList.get(1);
			y_Axis = keyList.get(0);
		}
	}

	private List<Coordinate> drawAxes(Graphics g, Map<Axis, List<Integer>> axesData) {
		specifyAxis(axesData.keySet());

		List<Integer> xScale = axesData.get(x_Axis);
		List<Integer> yScale = axesData.get(y_Axis);
		List<Integer> scaled_xAxisValues = new ArrayList<>(xScale);
		List<Integer> scaled_yAxisValues = new ArrayList<>(yScale);

		List<Coordinate> scaledCoordinates = ScaleMapper.scaleCoordinates(getCoordinates(locationList), getWidth(),
				getHeight(), scaled_xAxisValues, scaled_yAxisValues);

		// draw x-axis
		g.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50);
		// draw y-axis
		g.drawLine(50, getHeight() - 50, 50, 50);

		// draw x-axis values
		int y = getHeight() - 55;
		for (int i = 0; i < scaled_xAxisValues.size(); i++) {
			int x = scaled_xAxisValues.get(i);

			g.fillRect(x - 3, y, 5, 10);
			g.drawString(Integer.toString(xScale.get(i)), x - 5, y + 25);

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
			g.drawString(Integer.toString(yScale.get(i)), x - 25, y + 5);

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
