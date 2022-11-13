package com.yhuzo.account.common.dto;

public record ErrorV1(String code, String message) {

    public static class ErrorCode {
        public static final String DUPLICATED = "Duplicated";
        public static final String BAD_REQUEST = "BadRequest";
        public static final String MODEL_NOT_FOUND = "NotFound";
    }
}
