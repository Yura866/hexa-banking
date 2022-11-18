package com.yhuzo.account.launcher.configuration.adapter_output.mysql;

import com.yhuzo.account.adapter_output.mysql.AccountPersistenceMySqlAdapter;
import com.yhuzo.account.adapter_output.mysql.mapping.AccountMapper;
import com.yhuzo.account.adapter_output.mysql.repository.AccountRepository;
import com.yhuzo.account.adapter_output.mysql.repository.OperationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.yhuzo.account.adapter_output.mysql.entity")
@EnableJpaRepositories(basePackages = {"com.yhuzo.account.adapter_output.*"})
@ConditionalOnProperty(value = "account.adapter_output.mysql.enabled", havingValue = "true")
public class AccountPersistenceAdapterMysqlConfig {

    @Bean
    AccountPersistenceMySqlAdapter accountPersistenceAdapter(
            AccountRepository accountRepository,
            OperationRepository operationRepository,
            AccountMapper accountMapper
    ) {

        return new AccountPersistenceMySqlAdapter(
                accountRepository,
                operationRepository,
                accountMapper);
    }

    @Bean
    AccountMapper accountMapper() {
        return new AccountMapper();
    }
}
