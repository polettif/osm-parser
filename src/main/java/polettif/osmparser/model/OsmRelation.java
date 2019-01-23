package polettif.osmparser.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKBWriter;
import polettif.osmparser.lib.Osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Willy Tiengo
 */
public class OsmRelation extends OsmElement implements Osm.Relation {

    private OsmData osm;
    public List<OsmMember> osmMembers;

    public OsmRelation(OsmData osm, Long id, String visible, String timestamp,
                       String version, String changeset, String user,
                       String uid, List<OsmMember> osmMembers, Map<String, String> tags) {

        super(id, visible, timestamp, version, changeset, user, uid, tags);
        this.osm = osm;
        this.osmMembers = osmMembers;
    }

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.RELATION;
	}

    /**
     * @return The MultiLineString of all ways osmMembers of this relation. If any
     *         way osmMembers can not be found in the datase, returns
     *         <code>null</code>.
     */
    public Polygon getPolygon() {
        OsmWay osmWay;
        List<Coordinate> lines = new ArrayList<>();

        for (OsmMember osmMember : osmMembers) {
            if (isWay(osmMember)) {
                osmWay = (OsmWay) osm.getWay(osmMember.ref);

                if (osmWay == null) {
                    return null;
                }

                List<Coordinate> coord = Arrays.asList(osmWay.getLineString().getCoordinates());

                if (!lines.isEmpty()) {
                    Coordinate c = lines.get(lines.size() - 1);

                    if (!c.equals(coord.get(0))) {

                        if (c.equals(coord.get(coord.size() - 1))) {

                            Collections.reverse(coord);

                        } else {

                            Collections.reverse(lines);
                            c = lines.get(lines.size() - 1);
                            
                            if (!c.equals(coord.get(0))) {
                                Collections.reverse(coord);
                            }

                        }

                    }
                }

                lines.addAll(coord);
            }
        }

        GeometryFactory fac = new GeometryFactory();
        return fac.createPolygon(fac.createLinearRing(lines.toArray(
                new Coordinate[0])), null);
    }

    public boolean isBoundary() {
        return tags.get("boundary") != null;
    }

    public int getAdminLevel() {
        return Integer.parseInt(tags.get("admin_level"));
    }

    public String getName() {
        return tags.get("name");
    }

    public String getShape() {
        Polygon pol = getPolygon();
        return (pol != null) ? WKBWriter.bytesToHex(new WKBWriter().write(pol)) : null;
    }

    private boolean isWay(OsmMember m) {
        return m.type.equals("way");
    }

	@Override
	public List<Osm.Element> getMembers() {
		throw new IllegalAccessError();
    }

	@Override
	public String getMemberRole(Osm.Element member) {
		throw new IllegalAccessError();
	}

	@Override
	public Map<Long, Osm.Relation> getRelations() {
		throw new IllegalAccessError();
	}
}
