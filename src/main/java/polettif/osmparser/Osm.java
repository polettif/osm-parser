package polettif.osmparser;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.util.List;
import java.util.Map;

/**
 * @author polettif
 */
public interface Osm {

	Osm.Element getElement(Osm.ElementType type, Long refId);

	Map<Long, Osm.Node> getNodes();

	Map<Long, Osm.Way> getWays();

	Map<Long, Osm.Relation> getRelations();

	Quadtree getNodeQuadtree();

	CoordinateReferenceSystem getCoordinateReferenceSystem();

	interface Element {
		Long getId();

		ElementType getType();

		Map<String, String> getTags();

		Map<Long, Relation> getContainingRelations();
	}

	interface Node extends Element {
		Point getPoint();

		double[] getLonLat();

		Map<Long, Way> getContainingWays();
	}

	interface Way extends Element {
		List<Node> getNodes();
	}

	interface Relation extends Element {
		List<Member> getMembers();
	}

	interface Member {
		Long getRefId();

		ElementType geType();

		String getRole();

		Element getElement();
	}

	enum ElementType {
		NODE("node"),
		WAY("way"),
		RELATION("relation");

		final String name;

		ElementType(String name) {
			this.name = name;
		}

		public static ElementType get(String str) {
			return ElementType.valueOf(str.toUpperCase());
		}

		public String toString() {
			return name;
		}
	}
}
