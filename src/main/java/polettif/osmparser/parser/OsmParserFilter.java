package polettif.osmparser.parser;

import polettif.osmparser.Osm;

/**
 * @author polettif
 */
public interface OsmParserFilter {

	boolean acceptNode(Osm.Node node);

	boolean acceptWay(Osm.Way way);

	boolean acceptRelation(Osm.Relation relation);

}
