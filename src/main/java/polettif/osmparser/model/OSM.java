package polettif.osmparser.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Willy Tiengo
 */
public class OSM {

	private Set<OsmNode> nodes;
	private Set<OsmWay> osmWays;
	private Set<OsmRelation> osmRelations;

	public OSM() {
		nodes = new HashSet<>();
		osmWays = new HashSet<>();
		osmRelations = new HashSet<>();
	}

	public OSM(Set<OsmNode> nodes, Set<OsmWay> osmWays,
	           Set<OsmRelation> osmRelations) {
		this.nodes = nodes;
		this.osmWays = osmWays;
		this.osmRelations = osmRelations;
	}

	public Set<OsmNode> getNodes() {
		return nodes;
	}

	public Set<OsmRelation> getOsmRelations() {
		return osmRelations;
	}

	public Set<OsmWay> getOsmWays() {
		return osmWays;
	}

	public OsmWay getWay(String id) {
		for(OsmWay osmWay : osmWays) {
			if(osmWay.id.equals(id)) {
				return osmWay;
			}
		}
		return null;
	}
}
