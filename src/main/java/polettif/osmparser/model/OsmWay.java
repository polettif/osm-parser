package polettif.osmparser.model;

import polettif.osmparser.util.LatLongUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.WKBWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Willy Tiengo
 */
public class OsmWay extends OsmElement {

    // Constants ---------------------------------------------------------------
    public static final String HIGHWAY = "highway";
    //Attributes ---------------------------------------------------------------
    public List<OsmNode> nodes;

    public OsmWay(String id, String visible, String timestamp,
                  String version, String changeset, String user,
                  String uid, List<OsmNode> nodes, Map<String, String> tags) {

        super(id, visible, timestamp, version, changeset, user, uid, tags);
        this.nodes = nodes;
    }

    public LineString getLineString() {
        List<Coordinate> coords = new ArrayList<>();
        GeometryFactory fac = new GeometryFactory(
               );

        Coordinate c1;
        for (OsmNode node : nodes) {
            c1 = new Coordinate(Double.parseDouble(node.lon), Double.parseDouble(node.lat));
            coords.add(c1);
        }

        return fac.createLineString(coords.toArray(new Coordinate[0]));
    }

    public boolean isHighway() {
        return (tags.get(HIGHWAY) != null);
    }

    public boolean isOneway() {
        String oneway = tags.get("oneway");

        return ((oneway != null) && oneway.equals("yes"));

    }

    public String getName() {
        return tags.get("name");
    }

    public String getWayMiddle() {
        double lenMiddle, distance, lineDistance;
        GeometryFactory fac = new GeometryFactory();

        OsmNode n1 = null, n2 = null;

        lenMiddle = wayLength(nodes) / 2;
        distance = 0d;

        for (int i = 0; i < nodes.size() - 1; i++) {

            n1 = nodes.get(i);
            n2 = nodes.get(i + 1);

            lineDistance = lineDistance(n1, n2);

            if ((distance + lineDistance) > lenMiddle) {
                distance = (lenMiddle - distance) / lineDistance;
                break;
            }

            distance += lineDistance;
        }

        double lat = Double.parseDouble(n2.lat);
        double lon = Double.parseDouble(n2.lon);

        if (distance > 0.0d) {
            distance = (1 / distance);

            // Baseado na prova do ponto médio
            lat = (Double.parseDouble(n2.lat) + (distance - 1) * Double.parseDouble(n1.lat)) / distance;
            lon = (Double.parseDouble(n2.lon) + (distance - 1) * Double.parseDouble(n1.lon)) / distance;
        }

        return WKBWriter.bytesToHex(
                new WKBWriter().write(fac.createPoint(new Coordinate(lon, lat))));
    }

    public double getWayLength() {
        return wayLength(nodes);
    }

    public String getType() {
        return tags.get(HIGHWAY);
    }

    public String getShape() throws Exception {
        MultiLineString mls;

        // Precisa ser um MultiLineString
        mls = new GeometryFactory().createMultiLineString(
                new LineString[] { getLineString() });

        return WKBWriter.bytesToHex(new WKBWriter().write(mls));
    }

    public String getAltNames() {
        return tags.get("alt_name");
    }
    
    // Private methods ---------------------------------------------------------
    private double wayLength(List<OsmNode> nodes) {
        double length = 0d;
        OsmNode n1, n2;

        n1 = nodes.get(0);

        for (int i = 1; i < nodes.size(); i++) {
            n2 = nodes.get(i);

            length += LatLongUtil.distance(
                    Double.parseDouble(n1.lat), Double.parseDouble(n1.lon),
                    Double.parseDouble(n2.lat), Double.parseDouble(n2.lon));

            n1 = n2;
        }

        return length;
    }

    private static Double lineDistance(OsmNode n1, OsmNode n2) {

        return LatLongUtil.distance(
                Double.parseDouble(n1.lat), Double.parseDouble(n1.lon),
                Double.parseDouble(n2.lat), Double.parseDouble(n2.lon));

    }
}
