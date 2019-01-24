package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

/**
 * @author Willy Tiengo
 */
public class OsmMember implements Osm.Member {

	private final Osm.ElementType type;
	private final Long refId;
	private String role;

	private Osm.Element element = null;

	public OsmMember(Osm.ElementType type, Long refId, String role) {
		this.type = type;
		this.refId = refId;
		this.role = role;
	}

	public void setElement(Osm.Element element) {
		this.element = element;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public String geType() {
		return role;
	}

	@Override
	public String getRefId() {
		return role;
	}

	@Override
	public Osm.Element getElement() {
		return element;
	}
}
