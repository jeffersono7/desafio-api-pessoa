package br.com.fcamara.pessoa.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class InternalServerException extends RuntimeException {

    public static Supplier<InternalServerException> supplier() {
        return () -> new InternalServerException();
    }
}
