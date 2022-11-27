package de.v6hq.plantuml.server.v2;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		 * plain = "@startuml\n" // + "\n" // + "Bob->Alice : Hi there!\n" // +
		 * "activate Alice\n" // + "return\n" // + "\n" // + "@enduml";
		 * Assertions.assertEquals(plain, target.decode(target.encode(plain)));
		 */
	}

	@Test
	public void testMindmapSupport() throws IOException {
		var plain = " @@startmindmap\n"//
				+ "   * root\n"//
				+ "   ** leaf1\n"//
				+ "   ** leaf2\n"//
				+ "   @endmindmap";
		var encoded = target.encode(plain);
		var imageString = target.image(encoded);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageString.getBody()));
		// Not the best way to test but a little more robust than comparing
		// to an reference image
		Assertions.assertTrue(img.getHeight() > 30, "Any error image seems to be returned.");
	}

}
