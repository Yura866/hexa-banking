package com.yhuzo.account.launcher.configuration.adapter_output.mysql;

import com.yhuzo.account.adapter_output.mysql.AccountPersistenceAdapter;
import com.yhuzo.account.adapter_output.mysql.mapping.AccountMapper;
import com.yhuzo.account.adapter_output.mysql.repository.OperationRepository;
import com.yhuzo.account.adapter_output.mysql.repository.AccountRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EntityScan("com.yhuzo.account.adapter_output.mysql.entity")
@EnableJpaRepositories(basePackages = {"com.yhuzo.account.adapter_output.*"})
public class AccountPersistenceAdapterConfig {

    @Bean
    @Transactional
    AccountPersistenceAdapter accountPersistenceAdapter(
            AccountRepository accountRepository,
            OperationRepository operationRepository,
            AccountMapper accountMapper
    ) {

        return new AccountPersistenceAdapter(
                accountRepository,
                operationRepository,
                accountMapper);
    }

    @Bean
    AccountMapper accountMapper() {
        return new AccountMapper();
    }
}
