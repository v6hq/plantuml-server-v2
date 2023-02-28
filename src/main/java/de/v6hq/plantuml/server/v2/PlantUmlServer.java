package de.v6hq.plantuml.server.v2;

import io.javalin.Javalin;

public class PlantUmlServer {

	Javalin app;

	public PlantUmlServer() {
		app = Javalin.create(config -> {
			config.requestLogger.http(new RequestLogger());
			config.staticFiles.add("static");
			config.spaRoot.addFile("/", "static/index.html");
		});

		var plantUmlController = new PlantUmlController();

		app.post("encode", ctx -> {
			ctx.result(plantUmlController.encode(ctx.body()));
		});
		app.post("decode", ctx -> {
			ctx.result(plantUmlController.decode(ctx.body()));
		});
		app.get("image/{input}", ctx -> {
			ctx.result(plantUmlController.image(ctx.pathParam("input")));
			ctx.contentType("image/png");
		});
		app.get("png/{input}", ctx -> {
			ctx.result(plantUmlController.image(ctx.pathParam("input")));
			ctx.contentType("image/png");
		});
		app.get("svg/{input}", ctx -> {
			ctx.result(plantUmlController.svg(ctx.pathParam("input")));
			ctx.contentType("image/svg+xml");
		});
	}
	
	public void run() {
		app.start(8080);
	}

}
