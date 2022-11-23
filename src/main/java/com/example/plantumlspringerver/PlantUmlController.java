package com.example.plantumlspringerver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sourceforge.plantuml.SourceStringReader;

@RestController
public class PlantUmlController {

	Logger logger = LoggerFactory.getLogger(PlantUmlController.class);

	@PostMapping("/encode")
	public String encode() {
		logger.info("encode");
		return "{ \"modelEncoded\": \"SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80!\" }";
	}

	@GetMapping("/image")
	public ResponseEntity<byte[]> image(@RequestParam String model) throws IOException {
		logger.info("image "+model);

		SourceStringReader reader = new SourceStringReader(model);
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
