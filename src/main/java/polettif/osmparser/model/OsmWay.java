package polettif.osmparser.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import polettif.osmparser.lib.Osm;
import polettif.osmparser.util.LatLongUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmWay extends OsmElement implements Osm.Way {

	private List<Osm.Node> nodes;

	public OsmWay(String id, String visible, String timestamp,
	              String version, String changeset, String user,
	              String uid, List<Osm.Node> nodes, Map<String, String> tags) {

		super(id, visible, timestamp, version, changeset, user, uid, tags);
		this.nodes = nodes;
	}

	public boolean isOneway() {
		String oneway = tags.get("oneway");

		return ((oneway != null) && oneway.equals("yes"));

	}

	public String getName() {
		return tags.get("name");
	}

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.WAY;
	}

	@Override
	public List<Osm.Node> getNodes() {
		return nodes;
	}

	@Override
	public Map<Long, Osm.Relation> getRelations() {
		return null;
	}
}
