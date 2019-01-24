package polettif.osmparser.model;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import polettif.osmparser.lib.Osm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willy Tiengo
 */
public class OsmData implements Osm {

	private Map<Long, Osm.Node> nodes;
	private Map<Long, Osm.Way> ways;
	private Map<Long, Osm.Relation> relations;

	private CoordinateReferenceSystem coordinateReferenceSystem;
	private Quadtree quadtree;

	public OsmData() {
		nodes = new HashMap<>();
		ways = new HashMap<>();
		relations = new HashMap<>();
		try {
			coordinateReferenceSystem = CRS.decode("EPSG:4326", true);
		} catch (FactoryException ignored) {
		}
	}

	public CoordinateReferenceSystem getCoordinateReferenceSystem() {
		return coordinateReferenceSystem;
	}

	public void transform(String targetEPSG) throws FactoryException {
		CoordinateReferenceSystem targetCRS = CRS.decode(targetEPSG, true);
		this.quadtree = new Quadtree();

		MathTransform mathTransform = CRS.findMathTransform(coordinateReferenceSystem, targetCRS, true);

		for(Osm.Node node : nodes.values()) {
			Point newPoint;
			try {
				newPoint = (Point) JTS.transform(node.getPoint(), mathTransform);
			} catch (TransformException e) {
				throw new RuntimeException(e);
			}

			node.setPoint(newPoint);
			quadtree.insert(new Envelope(newPoint.getCoordinate()), node);
		}

		coordinateReferenceSystem = targetCRS;
	}

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

	public Quadtree getNodeQuadtree() {
		if(quadtree == null) {
			System.out.println("OSM is not transformed to a projected coordinate System");
		}
		return quadtree;
	}

	public void addNode(Osm.Node osmNode) {
		this.nodes.put(osmNode.getId(), osmNode);
	}

	public void addWay(Osm.Way osmWay) {
		this.ways.put(osmWay.getId(), osmWay);
	}

	public void addRelation(Osm.Relation osmRelation) {
		this.relations.put(osmRelation.getId(), osmRelation);
	}

	public void updateContainers() {
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
