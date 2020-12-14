package br.com.fcamara.pessoa.core.exception;

import br.com.fcamara.pessoa.core.utils.Mensagem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {
    private Mensagem mensagem;

    public NotFoundException(Mensagem mensagem) {
        super(mensagem.name());
        this.mensagem = mensagem;
    }

    public static Supplier<NotFoundException> supplier() {
        return () -> new NotFoundException();
    }
}
