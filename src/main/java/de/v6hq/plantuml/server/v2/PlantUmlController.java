package de.v6hq.plantuml.server.v2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

public class PlantUmlController {

	Logger logger = LoggerFactory.getLogger(PlantUmlController.class);

	public byte[] image(String encodedModel) throws IOException {
		logger.info("image " + encodedModel);

		var decoded = decode(encodedModel);

		SourceStringReader reader = new SourceStringReader(decoded);
		byte[] imageBytes;
		try (ByteArrayOutputStream outstream = new ByteArrayOutputStream()) {
			reader.outputImage(outstream);
			imageBytes = outstream.toByteArray();
		}

		return imageBytes;
	}

	public String decode(String encoded) {

		if (encoded == null || encoded.isBlank()) {
			return "";
		}

		String decoded;
		try {
			decoded = URLDecoder.decode(encoded, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			decoded = "invalid encoded string";
		}
		Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
		try {
			decoded = transcoder.decode(decoded);
		} catch (IOException ioe) {
			decoded = "unable to decode string";
		}
		return decoded;
	}

	public String encode(String plain) {
		String modelEncoded = "";
		Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
		try {
			modelEncoded = transcoder.encode(plain);
		} catch (IOException ioe) {
			// modelEncoded = "' unable to decode string";
		}
		try {
			modelEncoded = URLEncoder.encode(modelEncoded, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			// model2 = "' invalid encoded string";
		}
		return modelEncoded;
	}

}
