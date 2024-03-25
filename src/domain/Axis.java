package domain;

import java.util.List;

/**
 * Domain class for the axis of the coordinate system.
 */
public class Axis {

	private double scale;
	private final Dimension dimension;
	private List<Integer> values;

	public Axis(Dimension dimension) {
		this.dimension = dimension;
	}

	public Axis(Dimension dimension, double scale, List<Integer> values) {
		this.dimension = dimension;
		this.values = values;
		setScale(scale);
	}

	public Dimension getDimension() {
		return this.dimension;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return this.scale;
	}
	
	public void setValues(List<Integer> values) {
		this.values = values;
	}
	
	public List<Integer> getValues() {
		return values;
	}
}