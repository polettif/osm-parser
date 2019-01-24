package polettif.osmparser.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import polettif.osmparser.lib.Osm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmNode extends OsmElement implements Osm.Node {

	private final double[] lonLat;
	private Point point;

	private GeometryFactory geometryFactory = new GeometryFactory();

	private Map<Long, Osm.Way> containingWays = new HashMap<>();
	private Map<Long, Osm.Relation> containingRelations = new HashMap<>();

	public OsmNode(String id, double lon, double lat, String visible, String timestamp, String version, String changeset, String user, String uid, Map<String, String> tags) {
		super(Osm.ElementType.NODE, id, visible, timestamp, version, changeset, user, uid, tags);
		this.lonLat = new double[]{lon, lat};
		this.point = geometryFactory.createPoint(new Coordinate(lon, lat));
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
	public Point getPoint() {
		return this.point;
	}

	@Override
	public Map<Long, Osm.Way> getContainingWays() {
		return containingWays;
	}

	@Override
	public void addContainingElement(Osm.Element parentElement) {
		if(parentElement.getType().equals(Osm.ElementType.WAY)) {
			containingWays.put(parentElement.getId(), (Osm.Way) parentElement);
		} else if(parentElement.getType().equals(Osm.ElementType.RELATION)){
			containingRelations.put(parentElement.getId(), (Osm.Relation) parentElement);
		} else {
			throw new RuntimeException("Can't add element type " + parentElement.getType());
		}
	}

	@Override
	public Map<Long, Osm.Relation> getContainingRelations() {
		return containingRelations;
	}

	@Override
	public void update(Osm osm) {

	}

}
