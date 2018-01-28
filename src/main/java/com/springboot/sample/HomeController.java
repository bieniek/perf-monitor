package com.springboot.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final ExampleSpringService exampleSpringService;

    public HomeController(ExampleSpringService exampleSpringService) {
        this.exampleSpringService = exampleSpringService;
    }

    /**
     * This will not work - only Spring Bean are advised.
     */
    @GetMapping("/not-spring")
    public String get() {
        ExampleService exampleService = new ExampleService();
        return exampleService.buildGet("ok");
    }

    /**
     * This will work - call through SpringBean
     */
    @GetMapping("/spring")
    public String getWithSpring() {
        return exampleSpringService.buildGet("ok");
    }
}