package com.yhuzo.account.common.exception;

public class ThresholdExceededException extends Throwable {
    public ThresholdExceededException(Long maximumTransferThreshold, Double money) {
        super(String.format("Maximum transfer threshold exceed %n ,  %f", maximumTransferThreshold, money));
    }
}

