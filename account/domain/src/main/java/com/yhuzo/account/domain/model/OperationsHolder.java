package com.yhuzo.account.domain.model;

import lombok.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OperationsHolder {
    private final List<Operation> operations;

    public Instant getStartTimestamp() {
        return operations.stream()
                .min(Comparator.comparing(Operation::getCreatedAt))
                .orElseThrow(IllegalStateException::new)
                .getCreatedAt();
    }

    public Instant getEndTimestamp() {
        return operations.stream()
                .max(Comparator.comparing(Operation::getCreatedAt))
                .orElseThrow(IllegalStateException::new)
                .getCreatedAt();
    }

    public Money calculateBalance(Account.AccountId accountId) {
        Money depositBalance = operations.stream()
                .filter(a -> a.getTargetAccountId().equals(accountId))
                .map(Operation::getMoney)
                .reduce(Money.ZERO, Money::add);

        Money withdrawalBalance = operations.stream()
                .filter(a -> a.getSourceAccountId().equals(accountId))
                .map(Operation::getMoney)
                .reduce(Money.ZERO, Money::add);

        return Money.add(depositBalance, withdrawalBalance.negate());
    }

    public OperationsHolder(@NonNull List<Operation> operations) {
        this.operations = operations;
    }

    public OperationsHolder(@NonNull Operation... operations) {
        this.operations = new ArrayList<>(Arrays.asList(operations));
    }

    public List<Operation> getOperations() {
        return Collections.unmodifiableList(this.operations);
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }
}
