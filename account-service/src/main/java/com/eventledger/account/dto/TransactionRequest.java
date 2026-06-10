package com.eventledger.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private TransactionType type;

    @Positive
    private BigDecimal amount;

    @NotNull
    private Instant eventTimestamp;
}