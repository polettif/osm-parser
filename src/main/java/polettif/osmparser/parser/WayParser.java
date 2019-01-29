package polettif.osmparser.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import polettif.osmparser.model.OsmWay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuq
 * @author polettif
 */
class WayParser {

	static boolean isWay(Node node) {
		return node.getNodeName().equals("way");
	}

	static OsmWay parseWay(Node xmlNodeWay) {
		NamedNodeMap atts = xmlNodeWay.getAttributes();

		String id = atts.getNamedItem("id").getNodeValue();

		List<Long> memberNodeIds = new ArrayList<>();
		NodeList children = xmlNodeWay.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node xmlNode = children.item(i);
			String xmlNodeName = xmlNode.getNodeName();

			if(xmlNodeName.equals("nd")) {
				Long nodeId = Long.parseLong(xmlNode.getAttributes().
						getNamedItem("ref").getNodeValue());

				memberNodeIds.add(nodeId);
			}
		}

		return new OsmWay(
				id,
				OsmParser.getAttribute(atts, "visible"),
				OsmParser.getAttribute(atts, "timestamp"),
				OsmParser.getAttribute(atts, "version"),
				OsmParser.getAttribute(atts, "changeset"),
				OsmParser.getAttribute(atts, "user"),
				OsmParser.getAttribute(atts, "uid"),
				memberNodeIds,
				OsmParser.parseTags(xmlNodeWay.getChildNodes()));
	}

}
