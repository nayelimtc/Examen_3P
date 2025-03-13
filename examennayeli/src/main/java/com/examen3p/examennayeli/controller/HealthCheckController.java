package com.examen3p.examennayeli.controller;

import com.examen3p.examennayeli.health.MongoHealthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthCheckController {

    private final MongoHealthChecker mongoHealthChecker;

    @GetMapping("/mongodb")
    public ResponseEntity<Map<String, Object>> checkMongoHealth() {
        boolean isHealthy = mongoHealthChecker.isMongoHealthy();
        Map<String, Object> response = Map.of(
                "service", "MongoDB",
                "status", isHealthy ? "UP" : "DOWN");
        return isHealthy ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}