package de.simonmeusel.vector.board;

import java.util.ArrayList;
import java.util.List;

import de.simonmeusel.vector.board.shape.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Manages shapes on a canvas
 */
public class Board extends Canvas {

	private ArrayList<Shape> shapes;
	private Screen screen;

	GraphicsContext gc;

	public Board() {
		super();
		screen = new Screen(this);
		shapes = new ArrayList<>();

		gc = getGraphicsContext2D();

		// Automatically redraw on resize
		widthProperty().addListener(observable -> {
			screen.calculateHeight();
			redraw();
		});
		heightProperty().addListener(observable -> {
			screen.calculateHeight();
			redraw();
		});

		// Draw initial shape
		screen.calculateHeight();
		redraw();

		/*
		 * setOnScroll(event -> { int multiplier = (int) Math.signum(event.getDeltaY() /
		 * event.getMultiplierY()); System.out.println(multiplier);
		 * screen.zoom(Math.pow(1.1, multiplier)); }); setOnZoom(event -> {
		 * System.out.println(event.getTotalZoomFactor());
		 * screen.zoom(((event.getTotalZoomFactor() - 1) / 4) + 1); });
		 * setOnMouseClicked(event -> screen.zoom(1.5));
		 */
	}

	/**
	 * Draws a filled ellipse
	 */
	public void fillEllipse(Color c, Point ll, Point ur) {
		int realWidth = screen.getRealDistance(ur.getX() - ll.getX());
		int realHeight = screen.getRealDistance(ur.getY() - ll.getY());
		gc.setFill(c);
		gc.fillOval(ll.getRealX(), ur.getRealY(), realWidth, realHeight);
	}

	/**
	 * Draws a filled rectangle
	 */
	public void fillRectangle(Color c, Point ll, Point ur) {
		int realWidth = screen.getRealDistance(ll.getX() - ur.getX());
		int realHeight = screen.getRealDistance(ll.getY() - ur.getY());
		gc.setFill(c);
		gc.fillRect(ll.getRealX(), ur.getRealY(), realWidth, realHeight);
	}

	/**
	 * Gets the raw graphics context of the canvas
	 */
	public GraphicsContext getGraphicsContext() {
		return gc;
	}

	/**
	 * Gets the screen associated with the board
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * Updates the canvas
	 */
	public void redraw() {
		gc = getGraphicsContext2D();
		// Clear canvas
		gc.clearRect(0, 0, getWidth(), getHeight());
		// Draw shapes
		for (Shape shape : shapes) {
			shape.redraw();
		}
	}

	/**
	 * Draws a filled polygon
	 */
	public void fillPolygon(Color c, List<Point> points) {
		double[] xPostitions = new double[points.size()];
		double[] yPostitions = new double[points.size()];

		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			xPostitions[i] = point.getRealX();
			yPostitions[i] = point.getRealY();
		}

		gc.setFill(c);
		gc.fillPolygon(xPostitions, yPostitions, points.size());
	}

	/**
	 * Draws a line
	 */
	public void strokeLine(Color c, Point ll, Point ur) {
		gc.setStroke(c);
		gc.strokeLine(ll.getRealX(), ll.getRealY(), ur.getRealX(), ur.getRealY());
	}

	/**
	 * Swaps the z position of two shapes
	 */
	public void swapShapes(Shape first, Shape second) {
		shapes.set(shapes.indexOf(first), second);
		shapes.set(shapes.indexOf(second), first);
	}

	/**
	 * Swaps the z position of two shapes
	 */
	public void swapShapes(int first, int second) {
		Shape firstShape = shapes.get(first);
		shapes.set(first, shapes.get(second));
		shapes.set(second, firstShape);
	}

	public void moveToForeground(Shape shape) {
		while (shapes.indexOf(shape) != shapes.size() - 1) {
			moveTowardsForeground(shape);
		}
	}

	public void moveToBackground(Shape shape) {
		while (shapes.indexOf(shape) != 0) {
			moveTowardsBackground(shape);
		}
	}

	public void moveTowardsForeground(Shape shape) {
		int position = shapes.indexOf(shape);
		if (position != shapes.size() - 1) {
			swapShapes(position, position + 1);
		}
	}

	public void moveTowardsBackground(Shape shape) {
		int position = shapes.indexOf(shape);
		if (position != 0) {
			swapShapes(position, position - 1);
		}
	}

	/**
	 * Add a shape to the board
	 * 
	 * @param shape
	 *            SHape to add
	 */
	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	/**
	 * Gets a list of shapes on the board
	 * 
	 * @return List of shapes
	 */
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	/**
	 * Gets the shape at a given position
	 * 
	 * If multiple shapes are at the given position, the topmost shape is returned
	 * 
	 * @param p
	 *            Point where to search for a shape
	 * @return Shape at given position or null if no shape is found
	 */
	public Shape getShapeAtPoint(Point p) {
		Shape s = null;
		for (Shape shape : shapes) {
			if (shape.containsPoint(p)) {
				s = shape;
			}
		}
		return s;
	}

	/**
	 * Removes a shape from the board
	 * 
	 * @param shape Shape to remove
	 */
	public void removeShape(Shape shape) {
		shapes.remove(shape);
	}

}
