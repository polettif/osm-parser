package polettif.osmparser.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKBWriter;
import polettif.osmparser.lib.Osm;

import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmNode extends OsmElement implements Osm.Node {

	public double lat;
	public double lon;
	private Coordinate coordinate;

	public OsmNode(long id, double lon, double lat, String visible, String timestamp, String version, String changeset, String user, String uid, Map<String, String> tags) {
		super(id, visible, timestamp, version, changeset, user, uid, tags);
		this.lat = lat;
		this.lon = lon;
		this.tags = tags;
		this.coordinate = new Coordinate(this.lon, this.lat);
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public String getLocation() {
		Point p = new GeometryFactory().createPoint(this.coordinate);
		return WKBWriter.bytesToHex(new WKBWriter().write(p));
	}

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.NODE;
	}

	@Override
	public Coordinate getCoord() {
		return this.coordinate;
	}

	@Override
	public Map<Long, Osm.Way> getContainingWays() {
		throw new IllegalAccessError();
	}

	@Override
	public Map<Long, Osm.Relation> getContainingRelations() {
		throw new IllegalAccessError();
	}

}
