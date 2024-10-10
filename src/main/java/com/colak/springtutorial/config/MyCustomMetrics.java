package com.colak.springtutorial.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

// This example shows how to create a gauge
@Component
public class MyCustomMetrics implements MeterBinder {

    @Override
    public void bindTo(MeterRegistry registry) {
        Gauge.builder("custom.gauge", this, this::getValue)
                .description("A custom gauge metric")
                .register(registry);
    }

    private double getValue(Object object) {
        return ThreadLocalRandom.current().nextDouble(0, 10);
    }
}
