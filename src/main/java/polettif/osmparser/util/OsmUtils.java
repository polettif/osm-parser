package polettif.osmparser.util;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKBWriter;
import polettif.osmparser.lib.Osm;
import polettif.osmparser.model.OsmNode;
import polettif.osmparser.model.OsmWay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Willy Tiengo
 */
public class OsmUtils {

	public String getWayMiddle(List<Osm.Node> nodes) {
		double lenMiddle, distance, lineDistance;
		GeometryFactory fac = new GeometryFactory();

		OsmNode n1 = null, n2 = null;

		lenMiddle = wayLength(nodes) / 2;
		distance = 0d;

		for(int i = 0; i < nodes.size() - 1; i++) {

			n1 = (OsmNode) nodes.get(i);
			n2 = (OsmNode) nodes.get(i + 1);

			lineDistance = LatLongUtil.distance(n1, n2);
//			lineDistance = 0;

			if((distance + lineDistance) > lenMiddle) {
				distance = (lenMiddle - distance) / lineDistance;
				break;
			}

			distance += lineDistance;
		}

		double lat = n2.getLonLat()[1];
		double lon = n2.getLonLat()[0];

		if(distance > 0.0d) {
			distance = (1 / distance);

			// Baseado na prova do ponto médio
			lat = (n2.getLonLat()[1] + (distance - 1) * n1.getLonLat()[1]) / distance;
			lon = (n2.getLonLat()[0] + (distance - 1) * n1.getLonLat()[0]) / distance;
		}

		return WKBWriter.bytesToHex(
				new WKBWriter().write(fac.createPoint(new Coordinate(lon, lat))));
	}

	public String getShape(Iterable<? extends Osm.Node> nodes) throws Exception {
		MultiLineString mls;

		// Precisa ser um MultiLineString
		mls = new GeometryFactory().createMultiLineString(
				new LineString[]{getLineString(nodes)});

		return WKBWriter.bytesToHex(new WKBWriter().write(mls));
	}

	private double wayLength(List<Osm.Node> nodes) {
		double length = 0d;
		OsmNode n1, n2;

		n1 = (OsmNode) nodes.get(0);

		for(int i = 1; i < nodes.size(); i++) {
			n2 = (OsmNode) nodes.get(i);

			length += LatLongUtil.distance(
					n1.getLonLat()[1], n1.getLonLat()[0],
					n2.getLonLat()[1], n2.getLonLat()[0]);

			n1 = n2;
		}

		return length;
	}

	public LineString getLineString(Iterable<? extends Osm.Node> nodes) {
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

	/**
	 * @return The MultiLineString of all ways osmMembers of this relation. If any
	 * way osmMembers can not be found in the datase, returns
	 * <code>null</code>.
	 * @param osmMembers
	 */
	public Polygon getPolygon(Iterable<? extends Osm.Member> osmMembers) {
		OsmWay osmWay;
		List<Coordinate> lines = new ArrayList<>();

		for(Osm.Member osmMember : osmMembers) {
			if(osmMember.getElement().getType().equals(Osm.ElementType.WAY)) {
				osmWay = (OsmWay) osmMember.getElement();

				if(osmWay == null) {
					return null;
				}

				List<Coordinate> coord = Arrays.asList(getLineString(osmWay.getNodes()).getCoordinates());

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
}
