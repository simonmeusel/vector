package de.simonmeusel.vector.io.svg;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.Point;
import de.simonmeusel.vector.board.shape.Shape;
import de.simonmeusel.vector.io.Creator;

public class SVGCreator implements Creator {

	@Override
	public void create(OutputStream out, Board board)
			throws IOException, ParserConfigurationException, TransformerException {
		// Prepare XML creation
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// Create XML document
		Document doc = docBuilder.newDocument();

		// Create SVG element
		Element rootElement = doc.createElement("svg");
		rootElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		rootElement.setAttribute("version", "1.1");

		// View box
		Point vbll = new Point(0, 0, board);
		Point vbur = new Point(0, 0, board);

		// Add shapes
		for (Shape shape : board.getShapes()) {
			// Add shape if possible
			if (shape instanceof SVGCreateable) {
				rootElement.appendChild(((SVGCreateable) shape).createSVG(doc));
			}
			Point ll = shape.getBoundingBox().getLowerLeftPoint();
			Point ur = shape.getBoundingBox().getUpperRigthPoint();
			// Update view box to include shape
			if (ll.getX() < vbll.getX()) {
				vbll.setX(ll.getX());
			}
			if (ll.getY() < vbll.getY()) {
				vbll.setY(ll.getY());
			}
			if (ur.getX() > vbur.getX()) {
				vbur.setX(ur.getX());
			}
			if (ur.getY() > vbur.getY()) {
				vbur.setY(ur.getY());
			}
		}

		rootElement.setAttribute("viewBox", vbll.getX() + " " + -vbur.getY() + " " + (vbur.getX() - vbll.getX()) + " "
				+ (vbur.getY() - vbll.getY()));

		// Add root element
		doc.appendChild(rootElement);

		// Save XML to file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		transformer.transform(source, new StreamResult(out));

		out.close();
	}

}
