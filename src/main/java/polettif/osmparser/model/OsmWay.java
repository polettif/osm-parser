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

	public LineString getLineString() {
		List<Coordinate> coords = new ArrayList<>();
		GeometryFactory fac = new GeometryFactory(
		);

		Coordinate c1;
		for(Osm.Node node : nodes) {
			OsmNode n = (OsmNode) node;
			c1 = new Coordinate(n.getLonLat()[1], n.getLonLat()[2]);
			coords.add(c1);
		}

		return fac.createLineString(coords.toArray(new Coordinate[0]));
	}

	public boolean isHighway() {
		return (tags.get("highway") != null);
	}

	public boolean isOneway() {
		String oneway = tags.get("oneway");

		return ((oneway != null) && oneway.equals("yes"));

	}

	public String getName() {
		return tags.get("name");
	}

	/*
	public String getWayMiddle() {
		double lenMiddle, distance, lineDistance;
		GeometryFactory fac = new GeometryFactory();

		OsmNode n1 = null, n2 = null;

		lenMiddle = wayLength(nodes) / 2;
		distance = 0d;

		for(int i = 0; i < nodes.size() - 1; i++) {

			n1 = (OsmNode) nodes.get(i);
			n2 = (OsmNode) nodes.get(i + 1);

			lineDistance = lineDistance(n1, n2);

			if((distance + lineDistance) > lenMiddle) {
				distance = (lenMiddle - distance) / lineDistance;
				break;
			}

			distance += lineDistance;
		}

		double lat = n2.lat;
		double lon = n2.lon;

		if(distance > 0.0d) {
			distance = (1 / distance);

			// Baseado na prova do ponto médio
			lat = (n2.lat + (distance - 1) * n1.lat) / distance;
			lon = (n2.lon + (distance - 1) * n1.lon) / distance;
		}

		return WKBWriter.bytesToHex(
				new WKBWriter().write(fac.createPoint(new Coordinate(lon, lat))));
	}
	*/

	public double getWayLength() {
		return wayLength(nodes);
	}

	public String getHighwayType() {
		return tags.get("highway");
	}

	/*
	public String getShape() throws Exception {
		MultiLineString mls;

		// Precisa ser um MultiLineString
		mls = new GeometryFactory().createMultiLineString(
				new LineString[]{getLineString()});

		return WKBWriter.bytesToHex(new WKBWriter().write(mls));
	}
	*/

	public String getAltNames() {
		return tags.get("alt_name");
	}

	// Private methods ---------------------------------------------------------
	private double wayLength(List<Osm.Node> nodes) {
		double length = 0d;
		OsmNode n1, n2;

		n1 = (OsmNode) nodes.get(0);

		for(int i = 1; i < nodes.size(); i++) {
			n2 = (OsmNode) nodes.get(i);

			length += LatLongUtil.distance(
					n1.lat, n1.lon,
					n2.lat, n2.lon);

			n1 = n2;
		}

		return length;
	}

	private static Double lineDistance(OsmNode n1, OsmNode n2) {

		return LatLongUtil.distance(
				n1.lat, n1.lon,
				n2.lat, n2.lon);
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
