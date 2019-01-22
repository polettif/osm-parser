package polettif.osmparser.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OSM {

	private Map<Long, OsmNode> osmNodes;
	private Map<Long, OsmWay> osmWays;
	private Map<Long, OsmRelation> osmRelations;


	public OSM() {
		osmNodes = new HashMap<>();
		osmWays = new HashMap<>();
		osmRelations = new HashMap<>();
	}

	public OSM(Map<Long, OsmNode> nodes, Map<Long, OsmWay> osmWays,
	           Map<Long, OsmRelation> osmRelations) {
		this.osmNodes = nodes;
		this.osmWays = osmWays;
		this.osmRelations = osmRelations;
	}

	public Map<Long, OsmNode> getNodes() {
		return osmNodes;
	}

	public Map<Long, OsmRelation> getRelations() {
		return osmRelations;
	}

	public Map<Long, OsmWay> getWays() {
		return osmWays;
	}

	public OsmWay getWay(Long id) {
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
