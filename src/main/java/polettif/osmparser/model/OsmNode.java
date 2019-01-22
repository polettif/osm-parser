package polettif.osmparser.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKBWriter;

import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmNode extends AbstractNode {

	public String lat;
	public String lon;

	public OsmNode(String id, String visible, String timestamp, String version, String changeset, String user, String uid, String lat, String lon, Map<String, String> tags) {
		super(id, visible, timestamp, version, changeset, user, uid, tags);
		this.lat = lat;
		this.lon = lon;
		this.tags = tags;
	}

	public String getLocation() {
		Point p = new GeometryFactory().createPoint(
				new Coordinate(Double.valueOf(lon), Double.valueOf(lat)));

		return WKBWriter.bytesToHex(new WKBWriter().write(p));
	}
}
