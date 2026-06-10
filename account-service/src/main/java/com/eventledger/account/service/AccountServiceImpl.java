package com.eventledger.account.service;

import com.eventledger.account.dto.AccountResponse;
import com.eventledger.account.dto.BalanceResponse;
import com.eventledger.account.dto.TransactionRequest;
import com.eventledger.account.dto.TransactionType;
import com.eventledger.account.entity.AccountEntity;
import com.eventledger.account.entity.AccountTransactionEntity;
import com.eventledger.account.exception.AccountNotFoundException;
import com.eventledger.account.exception.DuplicateTransactionException;
import com.eventledger.account.repository.AccountRepository;
import com.eventledger.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public BalanceResponse applyTransaction(
            String accountId,
            TransactionRequest request) {

        if (transactionRepository
                .findByEventId(request.getEventId())
                .isPresent()) {

            throw new DuplicateTransactionException(
                    "Duplicate transaction");
        }

        AccountEntity account =
                accountRepository.findByAccountId(accountId)
                        .orElse(AccountEntity.builder()
                                .accountId(accountId)
                                .balance(BigDecimal.ZERO)
                                .build());

        if (TransactionType.CREDIT.equals(request.getType())) {
            account.setBalance(
                    account.getBalance()
                            .add(request.getAmount()));
        } else {
            account.setBalance(
                    account.getBalance()
                            .subtract(request.getAmount()));
        }

        accountRepository.save(account);

        transactionRepository.save(
                AccountTransactionEntity.builder()
                        .eventId(request.getEventId())
                        .accountId(accountId)
                        .amount(request.getAmount())
                        .type(request.getType())
                        .eventTimestamp(request.getEventTimestamp())
                        .build()
        );

        return new BalanceResponse(
                accountId,
                account.getBalance());
    }

    @Override
    public BalanceResponse getBalance(String accountId) {

        AccountEntity account =
                accountRepository.findByAccountId(accountId)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found"));

        return new BalanceResponse(
                accountId,
                account.getBalance());
    }

    @Override
    public AccountResponse getAccount(String accountId) {

        AccountEntity account =
                accountRepository.findByAccountId(accountId)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found"));

        return AccountResponse.builder()
                .accountId(accountId)
                .balance(account.getBalance())
                .recentTransactions(
                        transactionRepository
                                .findByAccountIdOrderByEventTimestampDesc(accountId)
                                .stream()
                                .map(AccountTransactionEntity::getEventId)
                                .collect(Collectors.toList()))
                .build();
    }
}