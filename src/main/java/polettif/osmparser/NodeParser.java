/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package polettif.osmparser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import polettif.osmparser.model.OsmNode;

/**
 * @author zuq
 */
public class NodeParser {

	public static boolean isNode(Node node) {
		return (node.getNodeName().equals("node"));
	}

	public static OsmNode parseNode(Node node) {
		NamedNodeMap atts = node.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		OsmNode osmNode = new OsmNode(id,
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				getAttribute(atts, "lat"),
				getAttribute(atts, "lon"),
				OsmParser.parseTags(node.getChildNodes()));

		return osmNode;
	}

	// Private Methods ---------------------------------------------------------

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}
}
