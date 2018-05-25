package de.simonmeusel.vector.board.shape;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;
import javafx.scene.paint.Color;

public abstract class Shape {

	protected Board board;
	protected BoundingBox boundingBox;
	protected Color color;

	public Shape(Board board, BoundingBox boundingBox) {
		this.board = board;
		this.boundingBox = boundingBox;
		color = Color.BLACK;
		color = new Color(Math.random(), Math.random(), Math.random(), 1);
	}

	public Board getBoard() {
		return board;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public Color getColor() {
		return color;
	}

	public abstract void redraw();

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean containsPoint(Point point) {
		double x = point.getX();
		double y = point.getY();
		
		Point ll = getBoundingBox().getLowerLeftPoint();
		Point ur = getBoundingBox().getUpperRigthPoint();
		
		return ll.getX() <= x && ll.getY() <= y && ur.getX() >= x && ur.getY() >= y;
	}
	
	public static Shape fromBoundingBox() {
		throw new IllegalArgumentException();
	}
	
	/**
	 * Gets the class corresponding to a given shape name
	 * @param name Case-insensitive name of shape
	 * @return Shape class or null, if no shape class is found
	 */
	public static Class<? extends Shape> getShapeSubclassByName(String name) {
		switch (name.toLowerCase()) {
		case "arrow":
			return Arrow.class;
		case "dot":
			return Dot.class;
		case "ellipse":
			return Ellipse.class;
		case "line":
			return Line.class;
		case "rectangle":
			return Rectangle.class;
		default:
			return null;
		}
	}
	
	public static <T extends Shape> T createShapeByBoundingBox(Class<T> shapeClass, Board board, BoundingBox boundingBox) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Get constructor with given signature
		Constructor<T> c = shapeClass.getConstructor(Board.class, BoundingBox.class);
		// Invoke constructor
		return c.newInstance(board, boundingBox);
	}
	
	/**
	 * Creates a valid CSS 2 color string
	 * @param c Color
	 * @return CSS 2 Color string
	 */
	public static String getColorString(Color c) {
		return "rgb(" + (c.getRed() * 255) + ", " + (c.getGreen() * 255) + ", " + (c.getBlue() * 255) + ")";
	}
	
}
