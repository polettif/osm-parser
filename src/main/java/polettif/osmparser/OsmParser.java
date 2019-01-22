package polettif.osmparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.model.OSM;
import polettif.osmparser.model.OsmNode;
import polettif.osmparser.model.OsmRelation;
import polettif.osmparser.model.OsmWay;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;

/**
 * @author Willy Tiengo
 */
public class OsmParser {

	/**
	 *
	 */
	public static OSM parse(InputStream is) throws Exception {

		Node xmlNode;
		NodeList xmlNodesList;

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(is);

		xmlNodesList = doc.getChildNodes().item(0).getChildNodes();

		OSM osm = new OSM();
		for(int i = 0; i < xmlNodesList.getLength(); i++) {

			xmlNode = xmlNodesList.item(i);

			if(NodeParser.isNode(xmlNode)) {
				OsmNode osmNode = NodeParser.parseNode(xmlNode);
				osm.addNode(osmNode);

			} else if(WayParser.isWay(xmlNode)) {
				OsmWay osmWay = WayParser.parseWay(osm, xmlNode);
				osm.addWay(osmWay);

			} else if(RelationParser.isRelation(xmlNode)) {
				OsmRelation osmRelation = RelationParser.parseRelation(osm, xmlNode);
				osm.addRelation(osmRelation);

			}
		}

//		Set<OsmNode> nodeset = new HashSet<>();
//		for(String n : nodes.keySet()) {
//			nodeset.add(nodes.get(n));
//		}

		return osm;
	}

	protected static Map<String, String> parseTags(NodeList nodes) {

		Map<String, String> tags = new HashMap<>();

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
