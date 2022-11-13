package com.yhuzo.account.common.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Class clazz, Long id) {
        super(String.format("%s is not found by id %d", clazz.getSimpleName(), id));
    }
}
