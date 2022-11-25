package com.example.plantumlspringerver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlantUmlControllerTest {

	private PlantUmlController target = new PlantUmlController();

	@Test
	public void testEncodingDecoding() {
		var plain = "@startuml\nHello World!\n@enduml";
		Assertions.assertEquals(plain, target.decode(target.encode(plain)));

		plain = "Hello World!";
		Assertions.assertTrue(target.decode(target.encode(plain)).contains(plain));

		// disable by intend; see https://github.com/kDot/plantuml-springserver/issues/6
		/*
		plain = "@startuml\n" //
				+ "\n" //
				+ "Bob->Alice : Hi there!\n" //
				+ "activate Alice\n" //
				+ "return\n" //
				+ "\n" //
				+ "@enduml";
		Assertions.assertEquals(plain, target.decode(target.encode(plain)));
		*/
	}

}
