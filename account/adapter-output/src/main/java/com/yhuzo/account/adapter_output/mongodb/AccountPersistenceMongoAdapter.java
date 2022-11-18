package com.yhuzo.account.adapter_output.mongodb;

import com.yhuzo.account.adapter_output.mongodb.document.AccountDoc;
import com.yhuzo.account.adapter_output.mongodb.document.OperationDoc;
import com.yhuzo.account.adapter_output.mongodb.mapper.AccountDocMapper;
import com.yhuzo.account.adapter_output.mongodb.repository.AccountMongoRepository;
import com.yhuzo.account.adapter_output.mongodb.repository.OperationMongoRepository;
import com.yhuzo.account.api_output.port.LoadAccountPort;
import com.yhuzo.account.api_output.port.UpdateAccountStatePort;
import com.yhuzo.account.common.exception.ModelNotFoundException;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Operation;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class AccountPersistenceMongoAdapter implements LoadAccountPort, UpdateAccountStatePort {
    private final AccountMongoRepository accountRepository;
    private final OperationMongoRepository operationRepository;
    private final AccountDocMapper accountMapper;

    @Override
    public Account loadAccount(Account.AccountId accountId, Instant baselineDate) {

        AccountDoc account =
                accountRepository.findById(accountId.getValue())
                        .orElseThrow(() -> new ModelNotFoundException(Account.class, accountId.getValue()));

        List<OperationDoc> operations =
                operationRepository.findByOwnerSince(
                        accountId.getValue(),
                        baselineDate);

        Double withdrawalBalance = orZero(operationRepository
                .getWithdrawalBalanceUntil(
                        accountId.getValue(),
                        baselineDate));

        Double depositBalance = orZero(operationRepository
                .getDepositBalanceUntil(
                        accountId.getValue(),
                        baselineDate));

        return accountMapper.mapToDomain(
                account,
                operations,
                withdrawalBalance,
                depositBalance);
    }

    private Double orZero(Double value) {
        return value == null ? 0.00 : value;
    }


    @Override
    public void updateOperations(Account account) {

        for (Operation operation : account.getOperations().getOperations()) {
            if (operation.getId() == null) {
                operationRepository.save(accountMapper.mapToDoc(operation));
            }
        }

    }
}
