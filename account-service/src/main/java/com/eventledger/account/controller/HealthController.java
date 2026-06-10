package com.eventledger.account.controller;

import com.eventledger.account.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {

        return new HealthResponse(
                "UP",
                "account-service");
    }
}