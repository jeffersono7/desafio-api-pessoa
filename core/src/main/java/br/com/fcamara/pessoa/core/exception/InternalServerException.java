package br.com.fcamara.pessoa.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
@Getter
public class InternalServerException extends RuntimeException {

    private String reason;

    public InternalServerException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public static Supplier<InternalServerException> supplier(String reason) {
        return () -> new InternalServerException(reason);
    }
}
