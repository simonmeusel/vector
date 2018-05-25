package de.simonmeusel.vector.board;

public class Box {

	protected Point lowerLeftPoint;
	protected Point upperRigthPoint;

	public Box(Point lowerLeftPoint, Point upperRigthPoint) {
		setPoints(lowerLeftPoint, upperRigthPoint);
	}
	
	public void setPoints(Point lowerLeftPoint, Point upperRigthPoint) {
		if (lowerLeftPoint.getX() > upperRigthPoint.getX()) {
			Point swapPoint = upperRigthPoint;
			upperRigthPoint = lowerLeftPoint;
			lowerLeftPoint = swapPoint;
		}
		if (lowerLeftPoint.getY() > upperRigthPoint.getY()) {
			this.lowerLeftPoint = new Point(lowerLeftPoint.getX(), upperRigthPoint.getY(), lowerLeftPoint.getBoard());
			this.upperRigthPoint = new Point(upperRigthPoint.getX(), lowerLeftPoint.getY(), upperRigthPoint.getBoard());
		} else {
			this.lowerLeftPoint = lowerLeftPoint;
			this.upperRigthPoint = upperRigthPoint;
		}
 	}

	public double getHeight() {
		return Math.abs(this.getUpperRigthPoint().getY() - this.getLowerLeftPoint().getY());
	}

	public Point getLowerLeftPoint() {
		return lowerLeftPoint;
	}

	public Point getUpperRigthPoint() {
		return upperRigthPoint;
	}

	public double getWidth() {
		return Math.abs(this.getLowerLeftPoint().getX() - this.getUpperRigthPoint().getX());
	}
	
	public void setLowerLeftPoint(Point lowerLeftPoint) {
		this.lowerLeftPoint = lowerLeftPoint;
	}
	
	public void setUpperRigthPoint(Point upperRigthPoint) {
		this.upperRigthPoint = upperRigthPoint;
	}
	
	
	public void move(double dx, double dy) {
		setUpperRigthPoint(getUpperRigthPoint().add(dx, dy));
		setLowerLeftPoint(getLowerLeftPoint().add(dx, dy));
	}
	
}
