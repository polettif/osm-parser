package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmData {

	private Map<Long, Osm.Node> osmNodes;
	private Map<Long, Osm.Way> osmWays;
	private Map<Long, Osm.Relation> osmRelations;


	public OsmData() {
		osmNodes = new HashMap<>();
		osmWays = new HashMap<>();
		osmRelations = new HashMap<>();
	}

	public OsmData(Map<Long, Osm.Node> nodes, Map<Long, Osm.Way> osmWays,
	               Map<Long, Osm.Relation> osmRelations) {
		this.osmNodes = nodes;
		this.osmWays = osmWays;
		this.osmRelations = osmRelations;
	}

	public Map<Long, Osm.Node> getNodes() {
		return osmNodes;
	}

	public Map<Long, Osm.Relation> getRelations() {
		return osmRelations;
	}

	public Map<Long, Osm.Way> getWays() {
		return osmWays;
	}

	public Osm.Way getWay(Long id) {
		for(Osm.Way osmWay : osmWays.values()) {
			if(osmWay.getId().equals(id)) {
				return osmWay;
			}
		}
		return null;
	}

	public void addNode(Osm.Node osmNode) {
		this.osmNodes.put(osmNode.getId(), osmNode);
	}

	public void addWay(Osm.Way osmWay) {
		this.osmWays.put(osmWay.getId(), osmWay);
	}

	public void addRelation(Osm.Relation osmRelation) {
		this.osmRelations.put(osmRelation.getId(), osmRelation);
	}
}
