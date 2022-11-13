package com.yhuzo.account.api_input.usecase;

import com.yhuzo.account.api_input.cqs.command.SendMoneyCommand;

public interface SendMoneyUseCase {
    boolean sendMoney(SendMoneyCommand command);
}
