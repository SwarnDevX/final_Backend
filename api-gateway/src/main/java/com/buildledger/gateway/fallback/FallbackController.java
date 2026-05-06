package com.buildledger.gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fallback controller for all circuit breaker fallbacks.
 * When a downstream service is down, the API Gateway routes to this controller.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/iam")
    public Mono<ResponseEntity<Map<String, Object>>> iamFallback() {
        return Mono.just(buildFallbackResponse("IAM Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/vendor")
    public Mono<ResponseEntity<Map<String, Object>>> vendorFallback() {
        return Mono.just(buildFallbackResponse("Vendor Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/contract")
    public Mono<ResponseEntity<Map<String, Object>>> contractFallback() {
        return Mono.just(buildFallbackResponse("Contract Service (Projects + Contracts) is currently unavailable. Please try again later."));
    }

    @GetMapping("/delivery")
    public Mono<ResponseEntity<Map<String, Object>>> deliveryFallback() {
        return Mono.just(buildFallbackResponse("Delivery Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/service-tracking")
    public Mono<ResponseEntity<Map<String, Object>>> serviceTrackingFallback() {
        return Mono.just(buildFallbackResponse("Service Tracking Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/finance")
    public Mono<ResponseEntity<Map<String, Object>>> financeFallback() {
        return Mono.just(buildFallbackResponse("Finance Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/compliance")
    public Mono<ResponseEntity<Map<String, Object>>> complianceFallback() {
        return Mono.just(buildFallbackResponse("Compliance Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/notification")
    public Mono<ResponseEntity<Map<String, Object>>> notificationFallback() {
        return Mono.just(buildFallbackResponse("Notification Service is currently unavailable. Please try again later."));
    }

    @GetMapping("/report")
    public Mono<ResponseEntity<Map<String, Object>>> reportFallback() {
        return Mono.just(buildFallbackResponse("Report Service is currently unavailable. Please try again later."));
    }

    private ResponseEntity<Map<String, Object>> buildFallbackResponse(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body);
    }
}

