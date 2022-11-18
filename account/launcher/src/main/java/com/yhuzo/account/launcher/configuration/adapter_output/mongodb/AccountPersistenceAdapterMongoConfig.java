package com.yhuzo.account.launcher.configuration.adapter_output.mongodb;

import com.yhuzo.account.adapter_output.mongodb.AccountPersistenceMongoAdapter;
import com.yhuzo.account.adapter_output.mongodb.mapper.AccountDocMapper;
import com.yhuzo.account.adapter_output.mongodb.repository.AccountMongoRepository;
import com.yhuzo.account.adapter_output.mongodb.repository.OperationMongoRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.yhuzo.account.adapter_output.mongodb"})
@ConditionalOnProperty(value = "account.adapter_output.mongodb.enabled", havingValue = "true", matchIfMissing = true)
public class AccountPersistenceAdapterMongoConfig {

    @Bean
    @ConditionalOnProperty(value = "account.mongodb.transactions.enabled", havingValue = "true", matchIfMissing = true)
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }


    @Bean
    AccountPersistenceMongoAdapter accountPersistenceMongoAdapter(
            AccountMongoRepository accountRepository,
            OperationMongoRepository operationRepository,
            AccountDocMapper accountMapper
    ) {

        return new AccountPersistenceMongoAdapter(
                accountRepository,
                operationRepository,
                accountMapper);
    }

    @Bean
    AccountDocMapper accountMapper() {
        return new AccountDocMapper();
    }
}
