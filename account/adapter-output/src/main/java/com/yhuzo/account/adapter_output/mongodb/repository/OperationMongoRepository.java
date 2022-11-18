package com.yhuzo.account.adapter_output.mongodb.repository;

import com.yhuzo.account.adapter_output.mongodb.document.OperationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface OperationMongoRepository extends MongoRepository<OperationDoc, Long> {

    @Query("{ 'ownerAccountId': ?0, createdAt:{'$lte': ?1 }}")
    List<OperationDoc> findByOwnerSince(
            @Param("ownerAccountId") Long ownerAccountId,
            @Param("since") Instant since);

    @Query(value = "{'count': { '$sum': 'amount'} , 'targetAccountId':?0, createdAt:{'$lte': ?1 } ")
    Double getDepositBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") Instant until);

    @Query(value = "{'count': { '$sum': 'amount'} , 'sourceAccountId':?0, 'ownerAccountId':?0, createdAt:{'$lte': ?1 } ")
    Double getWithdrawalBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") Instant until);
}
