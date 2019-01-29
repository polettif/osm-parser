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
				Double.parseDouble(Objects.requireNonNull(getAttribute(atts, "lon"))),
				Double.parseDouble(Objects.requireNonNull(getAttribute(atts, "lat"))),
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				OsmParser.parseTags(xmlNodeNode.getChildNodes()));
	}

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}
}
