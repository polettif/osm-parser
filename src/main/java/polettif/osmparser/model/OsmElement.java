package polettif.osmparser.model;

import polettif.osmparser.lib.Osm;

import java.util.Map;

/**
 * @author Willy Tiengo
 */
public abstract class OsmElement implements Osm.Element {

	public Long id;
	public String visible;
	public String timestamp;
	public String version;
	public String changeset;
	public String user;
	public String uid;
	public Map<String, String> tags;

	public OsmElement(String id, String visible, String timestamp,
	                  String version, String changeset, String user, String uid,
	                  Map<String, String> tags) {

		this.id = Long.valueOf(id);
		this.visible = visible;
		this.timestamp = timestamp;
		this.version = version;
		this.changeset = changeset;
		this.user = user;
		this.uid = uid;
		this.tags = tags;
	}

	public OsmElement(long id, String visible, String timestamp,
	                  String version, String changeset, String user, String uid,
	                  Map<String, String> tags) {
		this.id = id;
		this.visible = visible;
		this.timestamp = timestamp;
		this.version = version;
		this.changeset = changeset;
		this.user = user;
		this.uid = uid;
		this.tags = tags;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}

		final OsmNode other = (OsmNode) obj;

		return (id != null && id.equals(other.id));

	}

	@Override
	public int hashCode() {
		return (id != null ? id.hashCode() : super.hashCode());
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
	public String getValue(String key) {
		return null;
	}
}
