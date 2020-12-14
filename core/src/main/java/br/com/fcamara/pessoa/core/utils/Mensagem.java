package br.com.fcamara.pessoa.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Mensagem {
    CPF_JA_CADASTRADO("CPF já cadastrado!"),
    PESSOA_NAO_ENCONTRADA("Pessoa não encontrada!"),
    ID_INVALIDO("Id inválido!");

    private String descricao;
}
