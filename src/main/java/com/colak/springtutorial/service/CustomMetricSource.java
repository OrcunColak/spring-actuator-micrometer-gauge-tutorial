package com.colak.springtutorial.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomMetricSource {

    private final HashMap<String, String> ageMap = new HashMap<>(Map.of(
            "John", "78",
            "Jane", "65",
            "Greg", "89",
            "Tom", "91"
    ));
    private Gauge gauge;
    private final AtomicInteger gaugeInteger = new AtomicInteger(ageMap.size());
    private static final String GAUGE_NAME = "students.marks.data";

    public CustomMetricSource(MeterRegistry registry) {
        this.gauge = createGauge(registry);
    }

    private Gauge createGauge(MeterRegistry registry) {
        return Gauge.builder(GAUGE_NAME, gaugeInteger::get)
                .description("This metrics show the marks obtained by students")
                .tags(getTags())
                .register(registry);
    }

    private List<Tag> getTags() {
        return ageMap.keySet()
                .stream()
                .map(key -> Tag.of(key, ageMap.get(key)))
                .toList();
    }

    public void addDataToMarksMap(String name, String marks) {
        ageMap.put(name, marks);
        this.gaugeInteger.set(ageMap.size());
    }

}
