package br.com.fcamara.pessoa.core.utils;

import br.com.fcamara.pessoa.core.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Assert {

    public static void assertTrue(Boolean param, Mensagem mensagem) {
        if (!param.booleanValue()) {
            throw new BusinessException(mensagem);
        }
    }

    public static void assertFalse(Boolean param, Mensagem mensagem) {
        if (param.booleanValue()) {
            throw new BusinessException(mensagem);
        }
    }
}
