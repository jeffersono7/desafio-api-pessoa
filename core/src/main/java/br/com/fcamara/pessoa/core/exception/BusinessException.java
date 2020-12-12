package br.com.fcamara.pessoa.core.exception;

import java.util.function.Supplier;

public class BusinessException extends RuntimeException {

    public static Supplier<BusinessException> supplier() {
        return () -> new BusinessException();
    }
}
