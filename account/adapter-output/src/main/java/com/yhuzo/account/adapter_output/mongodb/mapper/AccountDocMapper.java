package com.yhuzo.account.adapter_output.mongodb.mapper;

import com.yhuzo.account.adapter_output.mongodb.document.AccountDoc;
import com.yhuzo.account.adapter_output.mongodb.document.OperationDoc;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Money;
import com.yhuzo.account.domain.model.Operation;
import com.yhuzo.account.domain.model.OperationsHolder;

import java.util.ArrayList;
import java.util.List;

public class AccountDocMapper {

    public Account mapToDomain(
            AccountDoc account,
            List<OperationDoc> operations,
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

    OperationsHolder mapToOperationHolder(List<OperationDoc> operations) {
        List<Operation> mappedOperations = new ArrayList<>();

        for (OperationDoc doc : operations) {
            mappedOperations.add(
                    Operation.builder()
                            .id(Operation.OperationId.of(doc.getId()))
                            .ownerAccountId(Account.AccountId.of(doc.getOwnerAccountId()))
                            .sourceAccountId(Account.AccountId.of(doc.getSourceAccountId()))
                            .targetAccountId(Account.AccountId.of(doc.getTargetAccountId()))
                            .createdAt(doc.getCreatedAt())
                            .money(Money.of(doc.getAmount()))
                            .build());
        }

        return new OperationsHolder(mappedOperations);
    }

    public OperationDoc mapToDoc(Operation operation) {
        return new OperationDoc(
                operation.getId() == null ? null : operation.getId().getValue(),
                operation.getCreatedAt(),
                operation.getOwnerAccountId().getValue(),
                operation.getSourceAccountId().getValue(),
                operation.getTargetAccountId().getValue(),
                operation.getMoney().getAmount().doubleValue());
    }
}
