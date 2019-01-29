package polettif.osmparser;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import polettif.osmparser.parser.OsmParser;
import polettif.osmparser.parser.OsmParserFilter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author polettif
 */
public final class Parser {

	public static Osm parseFile(String osmFilename) throws Exception {
		InputStream inputStream = new FileInputStream(osmFilename);
		return OsmParser.parse(inputStream, null, null);
	}

	public static Osm parseFile(String osmFilename, String outputEPSG) throws Exception {
		InputStream inputStream = new FileInputStream(osmFilename);
		return OsmParser.parse(inputStream, null, outputEPSG);
	}

	public static Osm parseFile(String osmFilename, OsmParserFilter filter, String outputEPSG) throws Exception {
		InputStream inputStream = new FileInputStream(osmFilename);

		return OsmParser.parse(inputStream, filter, outputEPSG);
	}

	public static Osm parseBz2File(String bz2filename, OsmParserFilter filter, String outputEPSG) throws Exception {
		InputStream bis = new BufferedInputStream(Parser.class.getResourceAsStream(bz2filename));
		CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);

		return OsmParser.parse(input, filter, outputEPSG);
	}

	public static Osm parse(InputStream is, OsmParserFilter filter, String outputEPSG) throws Exception {
		return OsmParser.parse(is, filter, outputEPSG);
	}

	public static Osm parse(InputStream is) throws Exception {
		return OsmParser.parse(is, null, null);
	}

	public static Osm parse(InputStream is, String outputEPSG) throws Exception {
		return OsmParser.parse(is, null, outputEPSG);
	}

}
