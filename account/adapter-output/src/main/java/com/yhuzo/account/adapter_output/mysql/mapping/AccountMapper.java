package com.yhuzo.account.adapter_output.mysql.mapping;

import com.yhuzo.account.adapter_output.mysql.entity.AccountJpaEntity;
import com.yhuzo.account.adapter_output.mysql.entity.OperationJpaEntity;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Money;
import com.yhuzo.account.domain.model.Operation;
import com.yhuzo.account.domain.model.OperationsHolder;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public Account mapToDomainEntity(
            AccountJpaEntity account,
            List<OperationJpaEntity> operations,
            Double withdrawalBalance,
            Double depositBalance) {

        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));

        return Account.builder()
                .id(Account.AccountId.of(account.getId()))
                .balance(baselineBalance)
                .operations(mapToOperationHolder(operations)).build();


    }

    OperationsHolder mapToOperationHolder(List<OperationJpaEntity> operations) {
        List<Operation> mappedOperations = new ArrayList<>();

        for (OperationJpaEntity operation : operations) {
            mappedOperations.add(
                    Operation.builder()
                            .id(Operation.OperationId.of(operation.getId()))
                            .ownerAccountId(Account.AccountId.of(operation.getOwnerAccountId()))
                            .sourceAccountId(Account.AccountId.of(operation.getSourceAccountId()))
                            .targetAccountId(Account.AccountId.of(operation.getTargetAccountId()))
                            .timestamp(operation.getTimestamp())
                            .money(Money.of(operation.getAmount()))
                            .build());
        }

        return new OperationsHolder(mappedOperations);
    }

    public OperationJpaEntity mapToJpaEntity(Operation operation) {
        return new OperationJpaEntity(
                operation.getId() == null ? null : operation.getId().getValue(),
                operation.getTimestamp(),
                operation.getOwnerAccountId().getValue(),
                operation.getSourceAccountId().getValue(),
                operation.getTargetAccountId().getValue(),
                operation.getMoney().getAmount().doubleValue());
    }
}
