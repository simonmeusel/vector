package de.simonmeusel.vector.board.shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;
import de.simonmeusel.vector.io.svg.SVGSerializeable;

public class Line extends Shape implements SVGSerializeable {
	private boolean rotated;

	/**
	 * @param rotated
	 *            Whether the arrow is using the two non standard points of the
	 *            bounding box
	 */
	public Line(Board board, Point lowerLeftPoint, Point upperRigthPoint, boolean rotated) {
		super(board, new BoundingBox(lowerLeftPoint, upperRigthPoint));
		this.rotated = rotated;
	}

	@Override
	public void redraw() {
		board.strokeLine(color, getLeftPoint(), getRigthPoint());
	}

	public Point getLeftPoint() {
		if (!rotated) {
			return getBoundingBox().getLowerLeftPoint();
		} else {
			return new Point(getBoundingBox().getLowerLeftPoint().getX(), getBoundingBox().getUpperRigthPoint().getY(),
					getBoard());
		}
	}

	public Point getRigthPoint() {
		if (!rotated) {
			return getBoundingBox().getUpperRigthPoint();
		} else {
			return new Point(getBoundingBox().getUpperRigthPoint().getX(), getBoundingBox().getLowerLeftPoint().getY(),
					getBoard());
		}
	}

	public Line(Board board, BoundingBox boundingBox) {
		this(board, boundingBox.getLowerLeftPoint(),
				boundingBox.getUpperRigthPoint(), false);
	}

	@Override
	public Element serializeSVG(Document doc) {
		Element e = doc.createElement("line");
		Point l = getLeftPoint();
		Point r = getRigthPoint();
		e.setAttribute("x1", "" + l.getX());
		e.setAttribute("y1", "" + -l.getY());
		e.setAttribute("x2", "" + r.getX());
		e.setAttribute("y2", "" + -r.getY());
		e.setAttribute("stroke", Shape.getColorString(color));
		return e;
	}

	public boolean isRotated() {
		return rotated;
	}

	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}
	
}
