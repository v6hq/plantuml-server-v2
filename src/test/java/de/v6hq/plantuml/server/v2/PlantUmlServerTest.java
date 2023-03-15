package de.v6hq.plantuml.server.v2;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

/**
 * Black box test for backend services
 */
public class PlantUmlServerTest {

	Javalin testtarget;

	@BeforeEach
	public void setup() {
		testtarget = new PlantUmlServer().app;
	}

	@Test
	public void testEncode() {
		JavalinTest.test(testtarget, (server, client) -> {
			var response = client.post("/encode");
			Assertions.assertEquals("", response.body().string());

			var plain = "Hello World!";
			response = client.post("/encode", plain);
			Assertions.assertEquals("yqZDoSbN2CyloabH1000", response.body().string());

			plain = "@startuml\nHello World!\n@enduml";
			response = client.post("/encode", plain);
			Assertions.assertEquals("yqZDoSbN2CyloabH1000", response.body().string());
		});
	}

	@Test
	public void testDecode() {
		JavalinTest.test(testtarget, (server, client) -> {
			var response = client.post("/decode");
			Assertions.assertEquals("", response.body().string());

			var plain = "yqZDoSbN2CyloabH1000";
			response = client.post("/decode", plain);
			Assertions.assertEquals("@startuml\nHello World!\n@enduml", response.body().string());
		});
	}

	@Test
	public void testMindmapSupport() throws IOException {
		var plain = " @@startmindmap\n"//
				+ "   * root\n"//
				+ "   ** leaf1\n"//
				+ "   ** leaf2\n"//
				+ "   @endmindmap";
		JavalinTest.test(testtarget, (server, client) -> {

			var encResponse = client.post("/encode", plain);
			var imgResponse = client.get("/png/" + encResponse.body().string());
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgResponse.body().bytes()));
			Assertions.assertTrue(img.getHeight() > 30, "Any error image seems to be returned.");
		});
	}

}
