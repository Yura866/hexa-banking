package com.yhuzo.account.api_output.port;

import com.yhuzo.account.domain.model.Account;

public interface UpdateAccountStatePort {

    void updateOperations(Account account);

}
