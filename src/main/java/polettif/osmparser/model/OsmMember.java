package polettif.osmparser.model;

/**
 * @author Willy Tiengo
 */
public class OsmMember {

	public String type;
	public Long ref;
	public String role;

	public OsmMember(String type, Long ref, String role) {
		this.type = type;
		this.ref = ref;
		this.role = role;
	}

	public OsmMember(String type, String ref, String role) {
		this.type = type;
		this.ref = Long.valueOf(ref);
		this.role = role;
	}
}
