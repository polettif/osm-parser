package polettif.osmparser.model;

import polettif.osmparser.Osm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmRelation extends OsmElement implements Osm.Relation {

	private final List<Osm.Member> members;

	private final Map<Long, Osm.Relation> containingRelations = new HashMap<>();

	public OsmRelation(String id, String visible, String timestamp,
	                   String version, String changeset, String user,
	                   String uid, List<Osm.Member> members, Map<String, String> tags) {

		super(Osm.ElementType.RELATION, id, visible, timestamp, version, changeset, user, uid, tags);
		this.members = members;
	}

	@Override
	public List<Osm.Member> getMembers() {
		return members;
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
