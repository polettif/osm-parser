package polettif.osmparser.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.Osm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 * @author polettif
 */
public class OsmParser {

	public static OsmData parse(InputStream is, OsmParserFilter filter, String EPSG) throws Exception {
		if(filter == null) {
			filter = new AcceptAllFilter();
		}

		Node xmlNode;
		NodeList xmlNodesList;

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(is);

		xmlNodesList = doc.getChildNodes().item(0).getChildNodes();

		Map<Long, Osm.Node> nodes = new HashMap<>();
		Map<Long, Osm.Way> ways = new HashMap<>();
		Map<Long, Osm.Relation> relations = new HashMap<>();

		for(int i = 0; i < xmlNodesList.getLength(); i++) {
			xmlNode = xmlNodesList.item(i);

			if(NodeParser.isNode(xmlNode)) {
				Osm.Node osmNode = NodeParser.parseNode(xmlNode);
				if(filter.acceptNode(osmNode)) {
					nodes.put(osmNode.getId(), osmNode);
				}
			} else if(WayParser.isWay(xmlNode)) {
				Osm.Way osmWay = WayParser.parseWay(xmlNode);
				if(filter.acceptWay(osmWay)) {
					ways.put(osmWay.getId(), osmWay);
				}
			} else if(RelationParser.isRelation(xmlNode)) {
				Osm.Relation osmRelation = RelationParser.parseRelation(xmlNode);
				if(filter.acceptRelation(osmRelation)) {
					relations.put(osmRelation.getId(), osmRelation);
				}
			}
		}

		return new OsmData(nodes, ways, relations, EPSG);
	}

	static String getAttribute(NamedNodeMap atts, String key) {
		Node node = atts.getNamedItem(key);
		return (node == null) ? null : node.getNodeValue();
	}

	static Map<String, String> parseTags(NodeList nodes) {
		Map<String, String> tags = new HashMap<>();

		for(int i = 0; i < nodes.getLength(); i++) {
			Node xmlNode = nodes.item(i);
			if(xmlNode.getNodeName().equals("tag")) {
				addTag(tags, xmlNode);
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

	private static class AcceptAllFilter implements OsmParserFilter {
		@Override
		public boolean acceptNode(Osm.Node node) {
			return true;
		}

		@Override
		public boolean acceptWay(Osm.Way way) {
			return true;
		}

		@Override
		public boolean acceptRelation(Osm.Relation relation) {
			return true;
		}
	}

}
