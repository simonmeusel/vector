package de.simonmeusel.vector.board.shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;

public class Dot extends Ellipse {

	public static final double RADIUS = 1;

	private Point point;

	public Dot(Board board, Point point) {
		super(board, point.add(-RADIUS, -RADIUS), point.add(RADIUS, RADIUS));
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Dot(Board board, BoundingBox boundingBox) {
		this(board, boundingBox.getLowerLeftPoint());
	}

	@Override
	public Element serializeSVG(Document doc) {
		Element e = doc.createElement("circle");
		Point ll = getBoundingBox().getLowerLeftPoint().add(RADIUS, RADIUS);
		e.setAttribute("cx", "" + ll.getX());
		e.setAttribute("cy", "" + -ll.getY());
		e.setAttribute("r", "" + RADIUS);
		e.setAttribute("fill", Shape.getColorString(color));
		return e;
	}

}
