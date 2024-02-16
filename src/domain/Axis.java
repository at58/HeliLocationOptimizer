package domain;

/**
 * Domain class for the axis of the coordinate system.
 */
public class Axis {

  private double scale;
  private final Dimension dimension;

  public Axis(Dimension dimension) {
    this.dimension = dimension;
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
}