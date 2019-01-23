package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

/**
 * @author Willy Tiengo
 */
public class OsmMember implements Osm.Member {

	private Osm.ElementType type;
	private Long ref;
	private String role;

	public OsmMember(String type, String ref, String role) {
		this.type = Osm.ElementType.valueOf(type.toUpperCase());
		this.ref = Long.valueOf(ref);
		this.role = role;
	}

	@Override
	public Osm.ElementType getType() {
		return type;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public Long getRef() {
		return ref;
	}
}
