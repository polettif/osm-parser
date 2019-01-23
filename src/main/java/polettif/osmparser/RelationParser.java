package polettif.osmparser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.lib.Osm;
import polettif.osmparser.model.OsmData;
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

	public static Osm.Relation parseRelation(OsmData osm, Node xmlNodeRelation) {
		NamedNodeMap atts = xmlNodeRelation.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		// create member list
		List<Osm.Member> members = getMembers(osm, xmlNodeRelation.getChildNodes());

		return new OsmRelation(osm, id,
				getAttribute(atts, "visible"),
				getAttribute(atts, "timestamp"),
				getAttribute(atts, "version"),
				getAttribute(atts, "changeset"),
				getAttribute(atts, "user"),
				getAttribute(atts, "uid"),
				members,
				OsmParser.parseTags(xmlNodeRelation.getChildNodes()));

	}

	// Private Methods ---------------------------------------------------------

	private static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}

	private static List<Osm.Member> getMembers(OsmData osm, NodeList children) {
		List<Osm.Member> members = new ArrayList<>();

		for(int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			NamedNodeMap map = node.getAttributes();

			if(node.getNodeName().equals("member")) {
				Long refId = Long.parseLong(map.getNamedItem("ref").getNodeValue());
				Osm.ElementType type = Osm.ElementType.valueOf(map.getNamedItem("type").getNodeValue().toUpperCase());
				String role = map.getNamedItem("role").getNodeValue();

				Osm.Member member = new OsmMember(osm.getElement(type, refId), role);

				members.add(member);
			}
		}

		return members;
	}
}
