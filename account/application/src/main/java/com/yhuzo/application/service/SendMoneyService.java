package com.yhuzo.application.service;

import com.yhuzo.account.api_input.cqs.command.SendMoneyCommand;
import com.yhuzo.account.api_input.usecase.SendMoneyUseCase;
import com.yhuzo.account.api_output.port.AccountLock;
import com.yhuzo.account.api_output.port.LoadAccountPort;
import com.yhuzo.account.api_output.port.UpdateAccountStatePort;
import com.yhuzo.account.common.exception.ThresholdExceededException;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Account.AccountId;
import com.yhuzo.account.domain.model.Money;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final TransferMoneyProperties properties;
    private final AccountLock accountLock;
    private final Clock clock;


    @SneakyThrows
    @Override
    public boolean sendMoney(SendMoneyCommand command) {

        checkThreshold(command);

        var baselineDate = Instant.now(clock)
                .minus(10,
                        ChronoUnit.DAYS
                );

        Account sourceAccount = loadAccountPort.loadAccount(
                AccountId.of(command.getSourceAccountId()),
                baselineDate
        );

        Account targetAccount = loadAccountPort.loadAccount(
                AccountId.of(command.getTargetAccountId()),
                baselineDate
        );

        accountLock.lockAccount(sourceAccount);

        if (!sourceAccount.withdraw(
                Money.of(command.getAmount()),
                targetAccount.getId())
        ) {
            accountLock.releaseAccount(sourceAccount);
            return false;
        }

        accountLock.lockAccount(targetAccount);
        if (!targetAccount.deposit(Money.of(command.getAmount()), sourceAccount.getId())) {
            accountLock.releaseAccount(sourceAccount);
            accountLock.releaseAccount(targetAccount);
            return false;
        }

        updateAccountStatePort.updateOperations(sourceAccount);
        updateAccountStatePort.updateOperations(targetAccount);

        accountLock.releaseAccount(sourceAccount);
        accountLock.releaseAccount(targetAccount);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) throws ThresholdExceededException {
        if (Money.of(command.getAmount()).isGreaterThan(Money.of(properties.getMaximumTransferThreshold()))) {
            throw new ThresholdExceededException(properties.getMaximumTransferThreshold(), command.getAmount());
        }
    }
}
