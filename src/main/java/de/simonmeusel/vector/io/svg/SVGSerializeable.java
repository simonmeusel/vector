package de.simonmeusel.vector.io.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface SVGSerializeable {
	
	public Element serializeSVG(Document doc);
	
}
