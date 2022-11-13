package com.yhuzo.account.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;

@Value
@Getter
@Builder
@RequiredArgsConstructor
public class Operation {

    @Getter
    OperationId id;

    @Getter
    @NonNull Account.AccountId ownerAccountId;

    @Getter
    @NonNull
    Account.AccountId sourceAccountId;

    @Getter
    @NonNull
    Account.AccountId targetAccountId;

    @Getter
    @NonNull
    Instant timestamp;

    @Getter
    @NonNull
    Money money;

    @Value(staticConstructor = "of")
   public static class OperationId {
        Long value;
    }

}
