package com.eventledger.account.repository;

import com.eventledger.account.entity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<AccountTransactionEntity,Long> {

    Optional<AccountTransactionEntity>
    findByEventId(String eventId);

    List<AccountTransactionEntity>
    findByAccountIdOrderByEventTimestampDesc(String accountId);
}