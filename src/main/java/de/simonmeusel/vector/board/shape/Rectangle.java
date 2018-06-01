package de.simonmeusel.vector.board.shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;
import de.simonmeusel.vector.io.svg.SVGSerializeable;

public class Rectangle extends Shape implements SVGSerializeable {

	public Rectangle(Board board, Point lowerLeftPoint, Point upperRigthPoint) {
		super(board, new BoundingBox(lowerLeftPoint, upperRigthPoint));
	}

	@Override
	public void redraw() {
		board.fillRectangle(color, boundingBox.getLowerLeftPoint(), boundingBox.getUpperRigthPoint());
	}

	public Rectangle(Board board, BoundingBox boundingBox) {
		this(board, boundingBox.getLowerLeftPoint(),
				boundingBox.getUpperRigthPoint());
	}

	@Override
	public Element serializeSVG(Document doc) {
		Element e = doc.createElement("rect");
		Point ll = getBoundingBox().getLowerLeftPoint();
		Point ur = getBoundingBox().getUpperRigthPoint();
		e.setAttribute("x", "" + ll.getX());
		e.setAttribute("y", "" + -ur.getY());
		e.setAttribute("width", "" + (ur.getX() - ll.getX()));
		e.setAttribute("height", "" + (ur.getY() - ll.getY()));
		e.setAttribute("fill", Shape.getColorString(color));
		return e;
	}

}
