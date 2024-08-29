package com.colak.springtutorial.controller;

import com.colak.springtutorial.service.CustomMetricSource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarksRestController {

    private final CustomMetricSource customMetricSource;

    // http://localhost:8080/name1/value1
    @GetMapping("/{name}/{marks}")
    public void addData(@PathVariable String name, @PathVariable String marks){
        customMetricSource.addDataToMarksMap(name,marks);
    }
}
