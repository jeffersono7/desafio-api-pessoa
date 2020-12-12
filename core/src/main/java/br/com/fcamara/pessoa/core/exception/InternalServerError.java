package br.com.fcamara.pessoa.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class InternalServerError extends RuntimeException {

    public static Supplier<InternalServerError> supplier() {
        return () -> new InternalServerError();
    }
}
