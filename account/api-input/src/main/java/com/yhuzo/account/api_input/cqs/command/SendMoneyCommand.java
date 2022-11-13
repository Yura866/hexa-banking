package com.yhuzo.account.api_input.cqs.command;

import com.yhuzo.account.api_input.validation.SelfValidator;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidator<SendMoneyCommand> {

    @NotNull
    Long sourceAccountId;

    @NotNull
    Long targetAccountId;

    @NotNull
    @Positive(message = "amount to send should be a positive number")
    Double amount;

    public SendMoneyCommand(
            Long sourceAccountId,
            Long targetAccountId,
            Double amount) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.validateSelf();
    }
}