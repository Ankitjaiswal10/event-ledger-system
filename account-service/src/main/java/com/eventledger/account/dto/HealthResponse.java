package com.eventledger.account.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthResponse {

    private String status;
    private String service;
}