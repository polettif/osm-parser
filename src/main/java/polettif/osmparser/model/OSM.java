package polettif.osmparser.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OSM {

	private Map<String, OsmNode> osmNodes;
	private Map<String, OsmWay> osmWays;
	private Map<String, OsmRelation> osmRelations;


	public OSM() {
		osmNodes = new HashMap<>();
		osmWays = new HashMap<>();
		osmRelations = new HashMap<>();
	}

	public OSM(Map<String, OsmNode> nodes, Map<String, OsmWay> osmWays,
	           Map<String, OsmRelation> osmRelations) {
		this.osmNodes = nodes;
		this.osmWays = osmWays;
		this.osmRelations = osmRelations;
	}

	public Map<String, OsmNode> getNodes() {
		return osmNodes;
	}

	public Map<String, OsmRelation> getRelations() {
		return osmRelations;
	}

	public Map<String, OsmWay> getWays() {
		return osmWays;
	}

	public OsmWay getWay(String id) {
		for(OsmWay osmWay : osmWays.values()) {
			if(osmWay.id.equals(id)) {
				return osmWay;
			}
		}
		return null;
	}

	public void addNode(OsmNode osmNode) {
		this.osmNodes.put(osmNode.id, osmNode);
	}


	public void addWay(OsmWay osmWay) {
		this.osmWays.put(osmWay.id, osmWay);
	}

	public void addRelation(OsmRelation osmRelation) {
		this.osmRelations.put(osmRelation.id, osmRelation);
	}
}
