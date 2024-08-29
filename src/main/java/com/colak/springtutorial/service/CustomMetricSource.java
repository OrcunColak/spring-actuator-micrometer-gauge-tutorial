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

    private final MeterRegistry registry;
    HashMap<String, String> marksData = new HashMap<>(Map.of(
            "John", "78",
            "Jane", "65",
            "Greg", "89",
            "Tom", "91"
    ));
    private Gauge gauge;
    private final AtomicInteger gaugeInteger = new AtomicInteger(marksData.size());
    private static final String STUDENTS_MARKS_DATA = "students.marks.data";

    public CustomMetricSource(MeterRegistry registry) {
        this.registry = registry;
        createGauge();
    }

    private void createGauge() {
        this.gauge = Gauge.builder(STUDENTS_MARKS_DATA, gaugeInteger::get)
                .description("This metrics show the marks obtained by students")
                .tags(getTags())
                .register(registry);
    }

    private List<Tag> getTags() {
        return marksData.keySet()
                .stream()
                .map(key -> Tag.of(key, marksData.get(key)))
                .toList();
    }

    public void addDataToMarksMap(String name, String marks) {
        marksData.put(name, marks);
        this.gaugeInteger.set(marksData.size());
    }

}
