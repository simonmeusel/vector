package de.simonmeusel.vector.io.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface SVGCreateable {
	
	public Element createSVG(Document doc);
	
}
