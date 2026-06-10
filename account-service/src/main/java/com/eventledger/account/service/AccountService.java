package com.eventledger.account.service;

import com.eventledger.account.dto.*;

public interface AccountService {

    BalanceResponse applyTransaction(
            String accountId,
            TransactionRequest request);

    BalanceResponse getBalance(String accountId);

    AccountResponse getAccount(String accountId);
}
