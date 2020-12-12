package br.com.fcamara.pessoa.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Mensagem {
    CPF_DUPLICADO("CPF já cadastrado!");

    private String descricao;
}
