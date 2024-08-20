package com.github.Noiseneks.crudExample.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HealthResource implements HealthIndicator {

    @Override
    @GetMapping("/health")
    @Operation(summary = "Checks if the service is up", responses = {@ApiResponse(responseCode = "200", description = "Successful response")})
    public Health health() {
        return Health.up().build();
    }

    @GetMapping("/")
    public ModelAndView redirectToSwagger(HttpServletRequest request) {
        return new ModelAndView("redirect:swagger-ui.html");
    }
}