package com.example.plantumlspringerver;

import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantUmlController {
	
	@PostMapping("/encode")
	public String encode() {
		return Base64.getEncoder().encodeToString(LocalDateTime.now().toString().getBytes());
	}

}
