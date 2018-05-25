package de.simonmeusel.vector.board;

public class Screen extends Box {

	public static final double INITIAL_WIDTH = 1000;

	private Board board;

	protected Screen(Board board) {
		super(new Point(0, 0, board), new Point(INITIAL_WIDTH, 0, board));
		this.board = board;
	}

	protected void calculateHeight() {
		double height = getWidth() * board.getHeight() / board.getWidth();
		getUpperRigthPoint().setX(getLowerLeftPoint().getX() + getWidth());
		getUpperRigthPoint().setY(getLowerLeftPoint().getY() + height);
	}

	/**
	 * Converts a distance to a distance on the canvas
	 */
	public int getRealDistance(double distance) {
		return (int) Math.abs(Math.round(distance * board.getWidth() / getWidth()));
	}

	/**
	 * Converts a distance on the canvas to a distance
	 */
	public double getUnrealDistance(double distance) {
		return Math.abs(distance * getWidth() / board.getWidth());
	}

	public void move(double dx, double dy) {
		super.move(dx, dy);
	}

	protected void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Zooms the screen
	 * 
	 * @param factor
	 *            Zoom factor, values between 0 and 1 for zooming out, values
	 *            greater than 1 for zooming in
	 */
	public void zoom(double factor) {
		double width = getWidth() / 2;
		double height = getHeight() / 2;

		width = width * factor - width;
		height = height * factor - height;

		getUpperRigthPoint().move(-width, -height);
		getLowerLeftPoint().move(width, height);

		calculateHeight();
	}

}
