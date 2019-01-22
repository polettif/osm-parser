package polettif.osmparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.model.OSM;
import polettif.osmparser.model.OSMNode;
import polettif.osmparser.model.Relation;
import polettif.osmparser.model.Way;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;

/**
 * @author Willy Tiengo
 */
public class OSMParser {

	/**
	 *
	 */
	public static OSM parse(InputStream is) throws Exception {

		Document doc;
		DocumentBuilder builder;

		Node node;
		NodeList nodesList;

		Map<String, OSMNode> nodes = new LinkedHashMap<String, OSMNode>();

		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.parse(is);

		nodesList = doc.getChildNodes().item(0).getChildNodes();

		OSM osm = new OSM();
		for(int i = 0; i < nodesList.getLength(); i++) {

			node = nodesList.item(i);

			if(NodeParser.isNode(node)) {

				OSMNode osmNode = NodeParser.parseNode(node);
				nodes.put(osmNode.id, osmNode);
				osm.getNodes().add(osmNode);

			} else if(WayParser.isWay(node)) {
				Way way = WayParser.parseWay(node, nodes);
				osm.getWays().add(way);

			} else if(RelationParser.isRelation(node)) {

				Relation relation = RelationParser.parseRelation(osm, node);
				osm.getRelations().add(relation);

			}
		}

		Set<OSMNode> nodeset = new HashSet<>();

		for(String n : nodes.keySet()) {
			nodeset.add(nodes.get(n));
		}

		return osm;
	}

	protected static Map<String, String> parseTags(NodeList nodes) {

		Map<String, String> tags = new HashMap<String, String>();

		for(int i = 0; i < nodes.getLength(); i++) {

			Node node = nodes.item(i);

			if(node.getNodeName().equals("tag")) {

				addTag(tags, node);

			}
		}

		return tags;
	}

	private static void addTag(Map<String, String> tags, Node node) {
		String key = node.getAttributes().getNamedItem("k").getNodeValue();
		String value = node.getAttributes().getNamedItem("v").getNodeValue();

		if(tags.get(key) != null) {

			tags.put(key, tags.get(key) + ";" + value);

		} else {

			tags.put(key, value);

		}
	}
}
