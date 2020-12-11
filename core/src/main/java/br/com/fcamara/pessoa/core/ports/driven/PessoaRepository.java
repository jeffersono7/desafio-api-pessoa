package br.com.fcamara.pessoa.core.ports.driven;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;

public interface PessoaRepository {

    Pessoa salvar(Pessoa pessoa);
}
