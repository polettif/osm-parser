package polettif.osmparser.parser;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import polettif.osmparser.Osm;
import polettif.osmparser.model.OsmMember;
import polettif.osmparser.model.OsmNode;
import polettif.osmparser.model.OsmRelation;
import polettif.osmparser.model.OsmWay;

import java.util.Map;

/**
 * @author Willy Tiengo
 * @author polettif
 */
class OsmData implements Osm {

	private final Map<Long, Osm.Node> nodes;
	private final Map<Long, Osm.Way> ways;
	private final Map<Long, Osm.Relation> relations;

	private CoordinateReferenceSystem coordSys = null;

	private Quadtree quadtree;

	OsmData(Map<Long, Node> nodes, Map<Long, Way> ways, Map<Long, Relation> relations, String EPSG) {
		this.nodes = nodes;
		this.ways = ways;
		this.relations = relations;

		connectElements();

		if(EPSG != null) {
			try {
				this.coordSys = CRS.decode(EPSG, true);
			} catch (FactoryException e) {
				throw new RuntimeException(e);
			}
			transform();
		}
	}

	@Override
	public CoordinateReferenceSystem getCoordinateReferenceSystem() {
		return coordSys;
	}

	@Override
	public Osm.Element getElement(Osm.ElementType type, Long refId) {
		switch(type) {
			case WAY:
				return this.ways.get(refId);
			case NODE:
				return this.nodes.get(refId);
			case RELATION:
				return this.nodes.get(refId);
			default:
				throw new IllegalAccessError();
		}
	}

	@Override
	public Quadtree getNodeQuadtree() {
		if(quadtree == null) {
			System.out.println("OSM is not transformed to a projected coordinate System");
		}
		return quadtree;
	}

	private void connectElements() {
		for(Relation relation : relations.values()) {
			for(Member member : relation.getMembers()) {
				Element memberElement = this.getElement(member.geType(), member.getRefId());

				if(memberElement != null) {
					((OsmMember) member).setElement(memberElement);

					switch(member.geType()) {
						case NODE:
							((OsmNode) memberElement).addContainingElement(relation);
							break;
						case WAY:
							((OsmWay) memberElement).addContainingElement(relation);
							break;
						case RELATION:
							((OsmRelation) memberElement).addContainingElement(relation);
							break;
					}
				}
			}
		}

		for(Osm.Way way : ways.values()) {
			OsmWay osmWay = (OsmWay) way;
			for(Long nodeId : osmWay.getNodeIds()) {
				Osm.Node node = this.nodes.get(nodeId);

				osmWay.addNode(node);
				((OsmNode) node).addContainingElement(way);
			}
		}
	}

	private void transform()  {
		this.quadtree = new Quadtree();

		CoordinateReferenceSystem crsWGS84;
		MathTransform mathTransform;
		try {
			crsWGS84 = CRS.decode("EPSG:4326", true);
			mathTransform = CRS.findMathTransform(crsWGS84, coordSys, true);
		} catch (FactoryException ignored) {
			throw new RuntimeException("Unable to transform map");
		}

		for(Osm.Node node : nodes.values()) {
			Point newPoint;
			try {
				newPoint = (Point) JTS.transform(node.getPoint(), mathTransform);
			} catch (TransformException e) {
				throw new RuntimeException(e);
			}

			((OsmNode) node).setPoint(newPoint);
			quadtree.insert(new Envelope(newPoint.getCoordinate()), node);
		}
	}

	@Override
	public Map<Long, Osm.Node> getNodes() {
		return nodes;
	}

	@Override
	public Map<Long, Osm.Way> getWays() {
		return ways;
	}

	@Override
	public Map<Long, Osm.Relation> getRelations() {
		return relations;
	}
}
