package polettif.osmparser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.model.OSM;
import polettif.osmparser.model.OsmMember;
import polettif.osmparser.model.OsmRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuq
 */
public class RelationParser {

	public static boolean isRelation(Node node) {
		return node.getNodeName().equals("relation");
	}

	public static OsmRelation parseRelation(OSM osm, Node xmlNodeRelation) {
		NamedNodeMap atts = xmlNodeRelation.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		return new OsmRelation(osm, id,
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				getMembers(xmlNodeRelation.getChildNodes()),
				OsmParser.parseTags(xmlNodeRelation.getChildNodes()));
	}

	// Private Methods ---------------------------------------------------------

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}

	private static List<OsmMember> getMembers(NodeList children) {
		List<OsmMember> result;
		Node node;
		NamedNodeMap map;

		result = new ArrayList<>();

		for(int i = 0; i < children.getLength(); i++) {
			node = children.item(i);
			map = node.getAttributes();

			if(node.getNodeName().equals("member")) {
				result.add(new OsmMember(
						map.getNamedItem("type").getNodeValue(),
						map.getNamedItem("ref").getNodeValue(),
						map.getNamedItem("role").getNodeValue()));
			}
		}

		return result;
	}
}
