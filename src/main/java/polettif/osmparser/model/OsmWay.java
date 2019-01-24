package polettif.osmparser.model;

import polettif.osmparser.Osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmWay extends OsmElement implements Osm.Way {

	private final List<Long> nodeIds;
	private final List<Osm.Node> nodes = new ArrayList<>();

	private final Map<Long, Osm.Relation> containingRelations = new HashMap<>();

	public OsmWay(String id, String visible, String timestamp,
	              String version, String changeset, String user,
	              String uid, List<Long> nodeIds, Map<String, String> tags) {
		super(Osm.ElementType.WAY, id, visible, timestamp, version, changeset, user, uid, tags);
		this.nodeIds = nodeIds;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public void addNode(Osm.Node node) {
		nodes.add(node);
	}

	@Override
	public List<Osm.Node> getNodes() {
		return nodes;
	}

	public void addContainingElement(Osm.Element parentElement) {
		if(parentElement.getType().equals(Osm.ElementType.RELATION)){
			containingRelations.put(parentElement.getId(), (Osm.Relation) parentElement);
		} else {
			throw new RuntimeException("Can't add element type " + parentElement.getType());
		}
	}

	@Override
	public Map<Long, Osm.Relation> getContainingRelations() {
		return containingRelations;
	}
}
