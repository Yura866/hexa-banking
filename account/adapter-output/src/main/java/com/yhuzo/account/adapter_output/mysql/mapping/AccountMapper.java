package com.yhuzo.account.adapter_output.mysql.mapping;

import com.yhuzo.account.adapter_output.mysql.entity.AccountEntity;
import com.yhuzo.account.adapter_output.mysql.entity.OperationEntity;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Money;
import com.yhuzo.account.domain.model.Operation;
import com.yhuzo.account.domain.model.OperationsHolder;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public Account mapToDomainEntity(
            AccountEntity account,
            List<OperationEntity> operations,
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

    OperationsHolder mapToOperationHolder(List<OperationEntity> operations) {
        List<Operation> mappedOperations = new ArrayList<>();

        for (OperationEntity operation : operations) {
            mappedOperations.add(
                    Operation.builder()
                            .id(Operation.OperationId.of(operation.getId()))
                            .ownerAccountId(Account.AccountId.of(operation.getOwnerAccountId()))
                            .sourceAccountId(Account.AccountId.of(operation.getSourceAccountId()))
                            .targetAccountId(Account.AccountId.of(operation.getTargetAccountId()))
                            .createdAt(operation.getTimestamp())
                            .money(Money.of(operation.getAmount()))
                            .build());
        }

        return new OperationsHolder(mappedOperations);
    }

    public OperationEntity mapToJpaEntity(Operation operation) {
        return new OperationEntity(
                operation.getId() == null ? null : operation.getId().getValue(),
                operation.getCreatedAt(),
                operation.getOwnerAccountId().getValue(),
                operation.getSourceAccountId().getValue(),
                operation.getTargetAccountId().getValue(),
                operation.getMoney().getAmount().doubleValue());
    }
}
