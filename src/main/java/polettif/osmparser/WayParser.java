package polettif.osmparser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.model.OsmNode;
import polettif.osmparser.model.OsmWay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zuq
 */
public class WayParser {

	public static boolean isWay(Node node) {
		return node.getNodeName().equals("way");
	}

	public static OsmWay parseWay(Node wayNode, Map<String, OsmNode> nodes) {
		OsmWay osmWay;

		NamedNodeMap atts = wayNode.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		osmWay = new OsmWay(
				id,
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				getNodes(wayNode.getChildNodes(), nodes),
				OsmParser.parseTags(wayNode.getChildNodes()));

		return osmWay;
	}

	// Private Methods ---------------------------------------------------------

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}

	private static List<OsmNode> getNodes(NodeList children, Map<String, OsmNode> nodes) {
		List<OsmNode> result = new ArrayList<>();

		Node node;
		String nodeName;

		for(int i = 0; i < children.getLength(); i++) {

			node = children.item(i);
			nodeName = node.getNodeName();

			if(nodeName.equals("nd")) {

				result.add(nodes.get(node.getAttributes().
						getNamedItem("ref").getNodeValue()));

			}
		}

		return result;
	}
}
