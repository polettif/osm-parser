package polettif.osmparser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.lib.Osm;
import polettif.osmparser.model.OsmData;
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

	public static OsmWay parseWay(OsmData osm, Node xmlNodeWay) {
		OsmWay osmWay;

		NamedNodeMap atts = xmlNodeWay.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		osmWay = new OsmWay(
				id,
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				getNodes(xmlNodeWay.getChildNodes(), osm.getNodes()),
				OsmParser.parseTags(xmlNodeWay.getChildNodes()));

		return osmWay;
	}

	// Private Methods ---------------------------------------------------------

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}

	private static List<Osm.Node> getNodes(NodeList children, Map<Long, Osm.Node> nodes) {
		List<Osm.Node> result = new ArrayList<>();

		for(int i = 0; i < children.getLength(); i++) {

			Node xmlNode = children.item(i);
			String xmlNodeName = xmlNode.getNodeName();

			if(xmlNodeName.equals("nd")) {
				String memberId = xmlNode.getAttributes().
						getNamedItem("ref").getNodeValue();
				// todo getMember().getId()

				result.add(nodes.get(memberId));
			}
		}

		return result;
	}
}
