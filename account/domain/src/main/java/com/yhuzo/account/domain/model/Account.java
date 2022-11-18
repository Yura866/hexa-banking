package com.yhuzo.account.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.Instant;


@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private final AccountId id;
    private final Money balance;
    private final OperationsHolder operations;

    public boolean deposit(Money money, AccountId sourceAccountId) {
        var deposit = Operation.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(sourceAccountId)
                .targetAccountId(this.id)
                .createdAt(Instant.now())
                .money(money)
                .build();
        this.operations.addOperation(deposit);
        return true;
    }

    public boolean withdraw(Money money, AccountId targetAccountId) {

        if (!mayWithdraw(money)) {
            return false;
        }

        var withdrawal = Operation.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(this.id)
                .targetAccountId(targetAccountId)
                .createdAt(Instant.now())
                .money(money)
                .build();
        this.operations.addOperation(withdrawal);
        return true;
    }

    public Money calculateBalance() {
        return Money.add(
                this.balance,
                this.operations.calculateBalance(this.id));
    }

    private boolean mayWithdraw(Money money) {
        return Money.add(
                        this.calculateBalance(),
                        money.negate())
                .isPositiveOrZero();
    }

    @Value(staticConstructor = "of")
    public static class AccountId {
        Long value;
    }

}
