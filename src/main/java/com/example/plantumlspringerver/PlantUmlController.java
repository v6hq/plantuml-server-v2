package com.example.plantumlspringerver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantUmlController {
	
	@PostMapping("/encode")
	public String encode() {
		return "{ \"modelEncoded\": \"SyfFKj2rKt3CoKnELR1Io4ZDoSa70000!\" }";
	}

}
