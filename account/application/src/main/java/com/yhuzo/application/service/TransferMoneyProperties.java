package com.yhuzo.application.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyProperties {

    @Value("${account.transfer.threshold.max:${ACCOUNT.TRANSFER.THRESHOLD.MAX:10000}}")
    Long maximumTransferThreshold = 10000L;

}
