package com.eventledger.account.service;

import com.eventledger.account.dto.BalanceResponse;
import com.eventledger.account.dto.TransactionRequest;
import com.eventledger.account.dto.TransactionType;
import com.eventledger.account.exception.AccountNotFoundException;
import com.eventledger.account.exception.DuplicateTransactionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldApplyCredit() {
        TransactionRequest request =
                new TransactionRequest();

        request.setEventId("evt-1");
        request.setType(TransactionType.CREDIT);
        request.setAmount(
                new BigDecimal("100"));
        request.setEventTimestamp(
                Instant.now());

        BalanceResponse response =
                accountService.applyTransaction(
                        "acct-1",
                        request);

        assertEquals(
                0,
                new BigDecimal("100")
                        .compareTo(
                                response.getBalance()));
    }

    @Test
    void shouldRejectDuplicateTransaction() {

        TransactionRequest request =
                new TransactionRequest();

        request.setEventId("evt-dup");
        request.setType(TransactionType.CREDIT);
        request.setAmount(
                new BigDecimal("50"));
        request.setEventTimestamp(
                Instant.now());

        accountService.applyTransaction(
                "acct-2",
                request);

        assertThrows(
                DuplicateTransactionException.class,
                () -> accountService
                        .applyTransaction(
                                "acct-2",
                                request));
    }

    @Test
    void shouldThrowWhenAccountNotFound() {

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getBalance(
                        "missing-account"));
    }
}
