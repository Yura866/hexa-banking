package com.yhuzo.account.adapter_output.mysql.repository;

import com.yhuzo.account.adapter_output.mysql.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
