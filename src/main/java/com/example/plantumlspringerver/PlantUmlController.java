package com.example.plantumlspringerver;

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
		
		String modelEncoded="";
		Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
        try {
        	modelEncoded = transcoder.encode(model);
        } catch (IOException ioe) {
        	//modelEncoded = "' unable to decode string";
        }
        try {
        	modelEncoded = URLEncoder.encode(modelEncoded, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
        	//model2 = "' invalid encoded string";
        }
        
		
		return "{ \"modelEncoded\": \""+modelEncoded+"\" }";
	}

	@GetMapping("/image")
	public ResponseEntity<byte[]> image(@RequestParam String model) throws IOException {
		logger.info("image "+model);
		
        String model2;
        try {
        	model2 = URLDecoder.decode(model, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
        	model2 = "' invalid encoded string";
        }
        Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
        try {
        	model2 = transcoder.decode(model2);
        } catch (IOException ioe) {
        	model2 = "' unable to decode string";
        }

		SourceStringReader reader = new SourceStringReader(model2);
		byte[] imageBytes;
		try (ByteArrayOutputStream outstream = new ByteArrayOutputStream()) {
			reader.generateImage(outstream);
			imageBytes = outstream.toByteArray();
		}
		var headers = new HttpHeaders();
		headers.add("Content-Type", "image/png");

		var response = new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);

		return response;
	}

}
