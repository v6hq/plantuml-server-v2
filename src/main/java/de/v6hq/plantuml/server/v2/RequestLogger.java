package de.v6hq.plantuml.server.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.http.Context;

public class RequestLogger implements io.javalin.http.RequestLogger {

	static final Logger logger = LoggerFactory.getLogger(RequestLogger.class);

	@Override
	public void handle(Context ctx, Float executionTimeMs) throws Exception {
		logger.info("{} {} {} {}ms", ctx.method(), ctx.fullUrl(), ctx.status(), executionTimeMs);
		logger.debug("Request{}{}", System.lineSeparator(), ctx.body());
		logger.debug("Response{}{}", System.lineSeparator(), ctx.result());
	}

}
