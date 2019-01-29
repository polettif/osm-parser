package polettif.osmparser.model;

import polettif.osmparser.Osm;

import java.util.Map;

/**
 * @author Willy Tiengo
 * @author polettif
 */
public abstract class OsmElement implements Osm.Element {

	private final Long id;
	private final Osm.ElementType type;
	private final Map<String, String> tags;

	OsmElement(Osm.ElementType type, String id, String visible, String timestamp,
	           String version, String changeset, String user, String uid,
	           Map<String, String> tags) {

		this.id = Long.valueOf(id);
		this.type = type;

		this.tags = tags;
		tags.put("visible", visible);
		tags.put("timestamp", timestamp);
		tags.put("version", version);
		tags.put("changeset", changeset);
		tags.put("user", user);
		tags.put("uid", uid);
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
