package polettif.osmparser.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import polettif.osmparser.Osm;
import polettif.osmparser.model.OsmNode;

import java.util.Objects;

/**
 * @author zuq
 * @author polettif
 */
class NodeParser {

	static boolean isNode(Node node) {
		return (node.getNodeName().equals("node"));
	}

	static Osm.Node parseNode(Node xmlNodeNode) {
		NamedNodeMap atts = xmlNodeNode.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		return new OsmNode(
				id,
				Double.parseDouble(Objects.requireNonNull(OsmParser.getAttribute(atts, "lon"))),
				Double.parseDouble(Objects.requireNonNull(OsmParser.getAttribute(atts, "lat"))),
				OsmParser.getAttribute(atts, "visible"),
				OsmParser.getAttribute(atts, "timestamp"),
				OsmParser.getAttribute(atts, "version"),
				OsmParser.getAttribute(atts, "changeset"),
				OsmParser.getAttribute(atts, "user"),
				OsmParser.getAttribute(atts, "uid"),
				OsmParser.parseTags(xmlNodeNode.getChildNodes()));
	}

}
