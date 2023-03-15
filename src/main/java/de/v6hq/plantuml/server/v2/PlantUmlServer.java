package de.v6hq.plantuml.server.v2;

import java.io.IOException;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class PlantUmlServer {

	private static final String DEFAULT_IMAGEFORMAT;

	static {
		DEFAULT_IMAGEFORMAT = System.getProperty("DEFAULT_IMAGEFORMAT", "svg");
	}

	Javalin app;
	private PlantUmlController plantUmlController = new PlantUmlController();

	public PlantUmlServer() {
		app = Javalin.create(config -> {
			config.requestLogger.http(new RequestLogger());
			config.staticFiles.add("static");
			config.spaRoot.addFile("/", "static/index.html");
		});
		
		new MetricsController(app).init();

		app.post("encode", ctx -> {
			ctx.result(plantUmlController.encode(ctx.body()));
		});
		app.post("decode", ctx -> {
			ctx.result(plantUmlController.decode(ctx.body()));
		});
		app.get("image/{input}", ctx -> {
			if (DEFAULT_IMAGEFORMAT.equalsIgnoreCase("svg")) {
				this.generateSvg(ctx);
			} else {
				this.generatePng(ctx);
			}
		});
		app.get("png/{input}", this::generatePng);
		app.get("svg/{input}", this::generateSvg);
	}

	private void generatePng(Context ctx) throws IOException {
		ctx.result(plantUmlController.png(ctx.pathParam("input")));
		ctx.contentType("image/png");
	}

	private void generateSvg(Context ctx) throws IOException {
		ctx.result(plantUmlController.svg(ctx.pathParam("input")));
		ctx.contentType("image/svg+xml");
	}

	public void run() {
		app.start(8080);
	}

}
