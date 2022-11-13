package com.yhuzo.account.adapter_output.mysql.repository;


import com.yhuzo.account.adapter_output.mysql.entity.OperationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface OperationRepository extends JpaRepository<OperationJpaEntity, Long> {

    @Query("select a from OperationJpaEntity a " +
            "where a.ownerAccountId = :ownerAccountId " +
            "and a.timestamp >= :since")
    List<OperationJpaEntity> findByOwnerSince(
            @Param("ownerAccountId") Long ownerAccountId,
            @Param("since") Instant since);

    @Query("select sum(a.amount) from OperationJpaEntity a " +
            "where a.targetAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Double getDepositBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") Instant until);

    @Query("select sum(a.amount) from OperationJpaEntity a " +
            "where a.sourceAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Double getWithdrawalBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") Instant until);

}
