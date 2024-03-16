package domain;

public class CoordinateSystem {

	private Axis x_Axis;
	private Axis y_Axis;
	private int parentWidth;
	private int parentHeight;
	private int borderSpacing;
	private static CoordinateSystem cs;
	
	public static CoordinateSystem getInstance() {
		if (cs == null) 
			cs = new CoordinateSystem();
		
		return cs;
	}
	
	private CoordinateSystem() {
		setXAxis(new Axis(Dimension.X));
		setYAxis(new Axis(Dimension.Y));
	}	

	public Axis getXAxis() {
		return x_Axis;
	}

	public Axis getYAxis() {
		return y_Axis;
	}

	public void setXAxis(Axis x_Axis) {
		this.x_Axis = x_Axis;
	}

	public void setYAxis(Axis y_Axis) {
		this.y_Axis = y_Axis;
	}

	public int getParentWidth() {
		return parentWidth;
	}

	public int getParentHeight() {
		return parentHeight;
	}
	
	public int getBorderSpacing() {
		return borderSpacing;
	}
	
	public void setBorderSpacing(int spacing) {
		this.borderSpacing = spacing;
	}
	
	public void setWidthAndHeight(int parentWidth, int parentHeight) {
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
	}

}
