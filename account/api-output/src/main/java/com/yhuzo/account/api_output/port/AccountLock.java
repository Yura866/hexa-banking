package com.yhuzo.account.api_output.port;

import com.yhuzo.account.domain.model.Account;

public interface AccountLock {

    void lockAccount(Account account);

    void releaseAccount(Account account);

}
