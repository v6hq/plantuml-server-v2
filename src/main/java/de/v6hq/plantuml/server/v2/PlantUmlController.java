package de.v6hq.plantuml.server.v2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

@RestController
public class PlantUmlController {

	Logger logger = LoggerFactory.getLogger(PlantUmlController.class);

	@PostMapping("/encode")
	public String encode(@RequestBody String model) {
		logger.info("encode");
		return encodeModel(model);
	}

	@PostMapping("/decode")
	public String decode(@RequestBody String model) {
		logger.info("decode");
		return decodeModel(model);
	}

	@GetMapping("/image")
	public ResponseEntity<byte[]> image(@RequestParam("model") String encodedModel) throws IOException {
		logger.info("image " + encodedModel);

		var decoded = decodeModel(encodedModel);

		SourceStringReader reader = new SourceStringReader(decoded);
		byte[] imageBytes;
		try (ByteArrayOutputStream outstream = new ByteArrayOutputStream()) {
			reader.outputImage(outstream);
			imageBytes = outstream.toByteArray();
		}
		var headers = new HttpHeaders();
		headers.add("Content-Type", "image/png");

		var response = new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);

		return response;
	}

	private String decodeModel(String encoded) {
		String decoded;
		try {
			decoded = URLDecoder.decode(encoded, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			decoded = "' invalid encoded string";
		}
		Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
		try {
			decoded = transcoder.decode(decoded);
		} catch (IOException ioe) {
			decoded = "' unable to decode string";
		}
		return decoded;
	}

	private String encodeModel(String plain) {
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
