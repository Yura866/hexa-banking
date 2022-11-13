package com.yhuzo.account.launcher.configuration.application;

import com.yhuzo.account.api_output.port.AccountLock;
import com.yhuzo.account.api_output.port.LoadAccountPort;
import com.yhuzo.account.api_output.port.UpdateAccountStatePort;
import com.yhuzo.application.service.AccountLockImpl;
import com.yhuzo.application.service.SendMoneyService;
import com.yhuzo.application.service.TransferMoneyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;


@Configuration
public class SendMoneyServiceConfig {

    @Bean
    @Transactional
    SendMoneyService sendMoneyService(LoadAccountPort loadAccountPort,
                                      UpdateAccountStatePort updateAccountStatePort,
                                      AccountLock accountLock,
                                      TransferMoneyProperties props,
                                      Clock clock) {
        return new SendMoneyService(loadAccountPort,
                updateAccountStatePort,
                props,
                accountLock,
                clock);
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    AccountLockImpl accountLockImpl() {
        return new AccountLockImpl();
    }

    @Bean
    TransferMoneyProperties transferMoneyProperties() {
        return new TransferMoneyProperties();
    }

}
