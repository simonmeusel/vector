package de.simonmeusel.vector.board;

public class Point {

	private double x;
	private double y;
	private Board board;

	public Point(double x, double y, Board board, boolean fromRealCoordinates) {
		if (fromRealCoordinates) {
			double dx = board.getScreen().getUnrealDistance(x);
			double dy = board.getScreen().getHeight() - board.getScreen().getUnrealDistance(y);

			Point ll = board.getScreen().getLowerLeftPoint();
			
			this.x = dx + ll.getX();
			this.y = dy + ll.getY();
		} else {
			this.x = x;
			this.y = y;
		}
		
		this.board = board;
	}

	public Point(double x, double y, Board board) {
		this(x, y, board, false);
	}

	public Point add(double x, double y) {
		return new Point(this.x + x, this.y + y, board);
	}

	public Point add(Point point) {
		return add(point.getX(), point.getY());
	}

	/**
	 * Returns a point moved in the given direction
	 */
	public Point addDirectionally(double direction, double lenght) {
		double dx = lenght * Math.cos(direction);
		double dy = lenght * Math.sin(direction);
		return this.add(dx, dy);
	}

	/**
	 * Gets the board the point is on
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Converts the point y coordinate to a y position on the canvas
	 */
	public int getRealX() {
		Screen screen = board.getScreen();
		Point p = subtract(screen.getLowerLeftPoint());

		return (int) Math.round(p.getX() * board.getWidth() / screen.getWidth());
	}

	/**
	 * Converts the points x coordinate to a x position on the canvas
	 */
	public int getRealY() {
		Screen screen = board.getScreen();
		Point p = subtract(screen.getLowerLeftPoint());

		return (int) Math.round(board.getHeight() - p.getY() * board.getHeight() / screen.getHeight());
	}

	/**
	 * Gets the points x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the points y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Moves the point
	 */
	public void move(double x, double y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Returns a new point, which's coordinates are multiplied by the given factors
	 */
	public Point multiply(double x, double y) {
		return new Point(this.x * x, this.y * y, board);
	}

	/**
	 * Set the point the point is on
	 * 
	 * The bard screen is used to calculate the real coordinates
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Sets the points x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Sets the points y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Returns a new point, which's coordinates got moved by the given values
	 */
	public Point subtract(Point point) {
		return new Point(x - point.getX(), y - point.getY(), board);
	}

	/**
	 * Gets a description of the point
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.x + " " + this.y;
	}

}
