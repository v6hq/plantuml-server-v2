package de.v6hq.plantuml.server.v2;

import io.javalin.Javalin;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class MetricsController {

    private Javalin app;
    private PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    private Counter counterGenAll = prometheusRegistry.counter("generation", "format", "any");
    private Counter counterGenSvg = prometheusRegistry.counter("generation", "format", "svg");
    private Counter counterGenPng = prometheusRegistry.counter("generation", "format", "png");

    public MetricsController(Javalin app) {
        this.app = app;
    }

    void init() {
        app.get("prometheus", ctx -> {
            ctx.result(prometheusRegistry.scrape());
        });

        app.before(ctx -> {
            if (ctx.path().matches("^.*(/image/|/svg/|/png/).*$")) {
                counterGenAll.increment();
            }
            if (ctx.path().contains("svg")) {
                counterGenSvg.increment();
            }
            if (ctx.path().contains("png")) {
                counterGenPng.increment();
            }
        });

    }

}