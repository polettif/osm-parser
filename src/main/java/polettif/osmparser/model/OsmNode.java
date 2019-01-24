package polettif.osmparser.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import polettif.osmparser.lib.Osm;

import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmNode extends OsmElement implements Osm.Node {

	private final double[] lonLat;
	public final double lon = -1;
	public final double lat = -1;
	private Point point;

	private GeometryFactory geometryFactory = new GeometryFactory();

	public OsmNode(long id, double lon, double lat, String visible, String timestamp, String version, String changeset, String user, String uid, Map<String, String> tags) {
		super(id, visible, timestamp, version, changeset, user, uid, tags);
		this.lonLat = new double[]{lon, lat};
		Point p = geometryFactory.createPoint(new Coordinate(lon, lat));
	}

	@Override
	public void setPoint(Point newPoint) {
		this.point = newPoint;
	}

	@Override
	public double[] getLonLat() {
		return lonLat;
	}

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.NODE;
	}

	@Override
	public Point getPoint() {
		return this.point;
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
