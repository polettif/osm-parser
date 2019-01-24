package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

import java.util.Map;

/**
 * @author Willy Tiengo
 */
public abstract class OsmElement implements Osm.Element {

	private final Long id;
	private final Osm.ElementType type;
	public String visible;
	public String timestamp;
	public String version;
	public String changeset;
	public String user;
	public String uid;
	private Map<String, String> tags;

	public OsmElement(Osm.ElementType type, String id, String visible, String timestamp,
	                  String version, String changeset, String user, String uid,
	                  Map<String, String> tags) {

		this.id = Long.valueOf(id);
		this.type = type;
		this.visible = visible;
		this.timestamp = timestamp;
		this.version = version;
		this.changeset = changeset;
		this.user = user;
		this.uid = uid;
		this.tags = tags;
	}

	@Override
	public Map<String, String> getTags() {
		return tags;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Osm.ElementType getType() {
		return type;
	}

}
