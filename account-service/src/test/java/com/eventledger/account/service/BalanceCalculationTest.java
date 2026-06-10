package com.eventledger.account.service;

import com.eventledger.account.dto.BalanceResponse;
import com.eventledger.account.dto.TransactionRequest;
import com.eventledger.account.dto.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BalanceCalculationTest {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCalculateBalanceCorrectly() {

        TransactionRequest credit1 =
                new TransactionRequest();

        credit1.setEventId("evt-c1");
        credit1.setType(TransactionType.CREDIT);
        credit1.setAmount(
                new BigDecimal("100"));
        credit1.setEventTimestamp(
                Instant.now());

        accountService.applyTransaction(
                "acct-bal",
                credit1);

        TransactionRequest credit2 =
                new TransactionRequest();

        credit2.setEventId("evt-c2");
        credit2.setType(TransactionType.CREDIT);
        credit2.setAmount(
                new BigDecimal("50"));
        credit2.setEventTimestamp(
                Instant.now());

        accountService.applyTransaction(
                "acct-bal",
                credit2);

        TransactionRequest debit =
                new TransactionRequest();

        debit.setEventId("evt-d1");
        debit.setType(TransactionType.DEBIT);
        debit.setAmount(
                new BigDecimal("25"));
        debit.setEventTimestamp(
                Instant.now());

        BalanceResponse response =
                accountService.applyTransaction(
                        "acct-bal",
                        debit);

        assertEquals(
                0,
                new BigDecimal("125")
                        .compareTo(
                                response.getBalance()));
    }
}