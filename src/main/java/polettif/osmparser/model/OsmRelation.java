package polettif.osmparser.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import polettif.osmparser.lib.Osm;

import java.util.*;

/**
 * @author Willy Tiengo
 */
public class OsmRelation extends OsmElement implements Osm.Relation {

	private final List<Osm.Member> osmMembers;

	@Deprecated
	private OsmData osm;

	public OsmRelation(OsmData osm, String id, String visible, String timestamp,
	                   String version, String changeset, String user,
	                   String uid, List<Osm.Member> osmMembers, Map<String, String> tags) {

		super(id, visible, timestamp, version, changeset, user, uid, tags);
		this.osmMembers = osmMembers;
	}

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.RELATION;
	}

	public String getName() {
		return tags.get("name");
	}

	@Override
	public List<Osm.Member> getMembers() {
		throw new IllegalAccessError();
	}

	@Override
	public Map<Long, Osm.Relation> getRelations() {
		throw new IllegalAccessError();
	}
}
