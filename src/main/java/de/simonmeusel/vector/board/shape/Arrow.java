package de.simonmeusel.vector.board.shape;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;

public class Arrow extends Line {
	public static final double DEFAULT_TIP_LENGTH = 30;

	private boolean arrowDirectionInverted;
	private double tipLength;

	/**
	 * Creates an arrow
	 * 
	 * @param rotated
	 *            Whether the arrow is using the two non standard points of the
	 *            bounding box
	 * @param arrowDirectionInverted
	 *            Whether the arrow point to the lower left point or not
	 */
	public Arrow(Board board, Point lowerLeftPoint, Point upperRigthPoint, boolean rotated,
			boolean arrowDirectionInverted, double tipLength) {
		super(board, lowerLeftPoint, upperRigthPoint, rotated);

		this.arrowDirectionInverted = arrowDirectionInverted;
		this.tipLength = tipLength;
	}

	/**
	 * Creates an arrow with the default tip length
	 * 
	 * @param rotated
	 *            Whether the arrow is using the two non standard points of the
	 *            bounding box
	 * @param arrowDirectionInverted
	 *            Whether the arrow point to the lower left point or not
	 */
	public Arrow(Board board, Point lowerLeftPoint, Point upperRigthPoint, boolean rotated,
			boolean arrowDirectionInverted) {
		this(board, lowerLeftPoint, upperRigthPoint, rotated, arrowDirectionInverted, DEFAULT_TIP_LENGTH);
	}

	@Override
	public void redraw() {
		// Draw line
		super.redraw();
		// Draw tip
		board.fillPolygon(color, getTipPoints());
	}
	
	private List<Point> getTipPoints() {
		// Calculate angle to x axis
		Point d = getRigthPoint().add(getLeftPoint().multiply(-1, -1));
		double direction = Math.atan(d.getY() / d.getX());
		Point tip;
		if (arrowDirectionInverted) {
			tip = getRigthPoint();
			direction -= Math.PI;
		} else {
			tip = getLeftPoint();
		}
		Point first = tip.addDirectionally(direction + Math.PI / 4, tipLength);
		Point second = tip.addDirectionally(direction - Math.PI / 4, tipLength);
		
		return Arrays.asList(first, tip, second);
		
	}

	public Arrow(Board board, BoundingBox boundingBox) {
		this(board, boundingBox.getLowerLeftPoint(), boundingBox.getUpperRigthPoint(), false, false);
	}
	
	@Override
	public Element serializeSVG(Document doc) {
		// Create group element
		Element group = doc.createElement("g");
		// Create line element
		Element line = super.serializeSVG(doc);
		group.appendChild(line);
		// Create tip element
		Element tip = doc.createElement("polygon");
		tip.setAttribute("fill", Shape.getColorString(color));
		String points = "";
		for (Point p : getTipPoints()) {
			points += p.getX() + "," + -p.getY() + " ";
		}
		points = points.substring(0, points.length() - 1);
		tip.setAttribute("points", points);
		group.appendChild(tip);
		// Return group
		return group;
	}

	public boolean isArrowDirectionInverted() {
		return arrowDirectionInverted;
	}

	public void setArrowDirectionInverted(boolean arrowDirectionInverted) {
		this.arrowDirectionInverted = arrowDirectionInverted;
	}

}
