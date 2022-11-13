package com.yhuzo.account.api_output.port;

import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Account.AccountId;

import java.time.Instant;

public interface LoadAccountPort {
    Account loadAccount(AccountId accountId, Instant baselineDate);
}