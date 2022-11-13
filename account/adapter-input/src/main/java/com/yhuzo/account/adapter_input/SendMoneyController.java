package com.yhuzo.account.adapter_input;

import com.yhuzo.account.adapter_input.mapper.SendMoneyRequestMapper;
import com.yhuzo.account.api_input.request.SendMoneyRequestParams;
import com.yhuzo.account.api_input.usecase.SendMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/accounts")
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/money/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendMoney(@RequestBody SendMoneyRequestParams params) {
        sendMoneyUseCase.sendMoney(
                SendMoneyRequestMapper.toCommand(params)
        );
    }
}
