package com.eventledger.account.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceResponse {

    private String accountId;
    private BigDecimal balance;
}