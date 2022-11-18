package com.yhuzo.account.adapter_output.mysql;

import com.yhuzo.account.adapter_output.mysql.entity.AccountEntity;
import com.yhuzo.account.adapter_output.mysql.entity.OperationEntity;
import com.yhuzo.account.adapter_output.mysql.mapping.AccountMapper;
import com.yhuzo.account.adapter_output.mysql.repository.AccountRepository;
import com.yhuzo.account.adapter_output.mysql.repository.OperationRepository;
import com.yhuzo.account.api_output.port.LoadAccountPort;
import com.yhuzo.account.api_output.port.UpdateAccountStatePort;
import com.yhuzo.account.common.exception.ModelNotFoundException;
import com.yhuzo.account.domain.model.Account;
import com.yhuzo.account.domain.model.Operation;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class AccountPersistenceMySqlAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(
            Account.AccountId accountId,
            Instant baselineDate) {

        AccountEntity account =
                accountRepository.findById(accountId.getValue())
                        .orElseThrow(() -> new ModelNotFoundException(Account.class, accountId.getValue()));

        List<OperationEntity> operations =
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

        return accountMapper.mapToDomainEntity(
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
                operationRepository.save(accountMapper.mapToJpaEntity(operation));
            }
        }
    }

}
