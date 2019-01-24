package polettif.osmparser.lib;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;
import java.util.Map;

/**
 * @author polettif
 */
public final class Osm {

	public enum ElementType {
		NODE("node"),
		WAY("way"),
		RELATION("relation");

		public final String name;

		ElementType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	public interface Element {
		Long getId();

		ElementType getType();

		Map<String, String> getTags();

		String getValue(String key);
	}

	public interface Node extends Element {
		Coordinate getCoord();

		double[] getLonLat();

		Map<Long, Way> getContainingWays();

		Map<Long, Relation> getContainingRelations();

		void setCoord(Coordinate coordinate);
	}

	public interface Way extends Element {
		List<Node> getNodes();

		Map<Long, Relation> getRelations();
	}

	public interface Relation extends Element {
		List<Member> getMembers();

		Map<Long, Relation> getRelations();
	}

	public interface Member {
		String getRole();

		Element getElement();
	}
}
