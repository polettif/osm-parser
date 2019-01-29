package polettif.osmparser.parser;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.junit.Test;
import polettif.osmparser.Osm;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author zuq
 */
public class OsmParserTest {

	@Test
	public void testTest() throws Exception {

		InputStream bis = new BufferedInputStream(getClass().getResourceAsStream("/montenegro-latest.osm.bz2"));
		CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);

		OsmData osm = OsmParser.parse(input, null, null);
		assertEquals(990250, osm.getNodes().size());
		assertEquals(61263, osm.getWays().size());
		assertEquals(517, osm.getRelations().size());

		boolean found = false;
		for(Osm.Way w : osm.getWays().values()) {
			if(w.getTags().get("name") != null) {
				if(w.getTags().get("name").equals("Бучичко гробље")) {
					assertTrue(w.getTags().containsKey("religion"));
					assertTrue(w.getTags().containsKey("name"));
					assertTrue(w.getTags().containsKey("landuse"));
					assertTrue(w.getTags().containsKey("denomination"));
					found = true;
				}
			}
		}
		assertTrue(found);
		found = false;
		for(Osm.Relation r : osm.getRelations().values()) {
			if(r.getTags().get("name") != null && r.getTags().get("name").equals("Čapljina - Hum - Dubrovnik")) {
				assertTrue(r.getTags().containsKey("railway"));
				assertTrue(r.getTags().containsKey("route"));
				assertTrue(r.getTags().containsKey("type"));
				found = true;
			}
		}
		assertTrue(found);
	}
}
