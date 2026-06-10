package com.eventledger.account.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private String accountId;

    private BigDecimal balance;

    private List<String> recentTransactions;
}
