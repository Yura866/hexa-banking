package com.yhuzo.account.api_input.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyRequestParams {

    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
}
