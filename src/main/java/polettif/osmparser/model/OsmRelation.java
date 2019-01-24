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
		this.osm = osm;
		this.osmMembers = osmMembers;
	}

	@Override
	public Osm.ElementType getType() {
		return Osm.ElementType.RELATION;
	}

	/**
	 * @return The MultiLineString of all ways osmMembers of this relation. If any
	 * way osmMembers can not be found in the datase, returns
	 * <code>null</code>.
	 */
	public Polygon getPolygon() {
		OsmWay osmWay;
		List<Coordinate> lines = new ArrayList<>();

		for(Osm.Member osmMember : osmMembers) {
			if(osmMember.getElement().getType().equals(Osm.ElementType.WAY)) {
				osmWay = (OsmWay) osmMember.getElement();

				if(osmWay == null) {
					return null;
				}

				List<Coordinate> coord = Arrays.asList(osmWay.getLineString().getCoordinates());

				if(!lines.isEmpty()) {
					Coordinate c = lines.get(lines.size() - 1);

					if(!c.equals(coord.get(0))) {

						if(c.equals(coord.get(coord.size() - 1))) {

							Collections.reverse(coord);

						} else {

							Collections.reverse(lines);
							c = lines.get(lines.size() - 1);

							if(!c.equals(coord.get(0))) {
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
