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
		if(this.element == null) {
			this.element = element;
		} else {
			throw new RuntimeException("Cannot set element twice");
		}
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public Osm.ElementType geType() {
		return type;
	}

	@Override
	public Long getRefId() {
		return refId;
	}

	@Override
	public Osm.Element getElement() {
		return element;
	}
}
