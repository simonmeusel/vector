package de.simonmeusel.vector.board.shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;
import de.simonmeusel.vector.io.svg.SVGCreateable;

public class Ellipse extends Shape implements SVGCreateable {

	public Ellipse(Board board, Point lowerLeftPoint, Point upperRigthPoint) {
		super(board, new BoundingBox(lowerLeftPoint, upperRigthPoint));
	}

	@Override
	public void redraw() {
		board.fillEllipse(color, boundingBox.getLowerLeftPoint(), boundingBox.getUpperRigthPoint());
	}
	
	public Ellipse(Board board, BoundingBox boundingBox) {
		this(board, boundingBox.getLowerLeftPoint(), boundingBox.getUpperRigthPoint());
	}

	@Override
	public Element createSVG(Document doc) {
		Element e = doc.createElement("ellipse");
		// TODO
		e.setAttribute("fill", Shape.getColorString(color));
		return e;
	}
	
}
