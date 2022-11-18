package com.yhuzo.account.adapter_output.mongodb.repository;

import com.yhuzo.account.adapter_output.mongodb.document.AccountDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountMongoRepository extends MongoRepository<AccountDoc, Long> {
}
