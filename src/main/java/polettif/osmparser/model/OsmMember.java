package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

/**
 * @author Willy Tiengo
 */
public class OsmMember implements Osm.Member {

	private Osm.Element element;
	private String role;

	public OsmMember(Osm.Element element, String role) {
		this.element = element;
		this.role = role;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public Osm.Element getElement() {
		return element;
	}
}
