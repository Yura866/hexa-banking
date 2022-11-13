package com.yhuzo.account.adapter_input.mapper;

import com.yhuzo.account.api_input.cqs.command.SendMoneyCommand;
import com.yhuzo.account.api_input.request.SendMoneyRequestParams;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SendMoneyRequestMapper {


    public static SendMoneyCommand toCommand(SendMoneyRequestParams request) {
        return SendMoneyCommand.builder()
                .sourceAccountId(request.getSourceAccountId())
                .targetAccountId(request.getTargetAccountId())
                .amount(request.getAmount())
                .build();


    }
}
