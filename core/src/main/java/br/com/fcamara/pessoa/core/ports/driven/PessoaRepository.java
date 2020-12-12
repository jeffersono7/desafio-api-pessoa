package br.com.fcamara.pessoa.core.ports.driven;

import br.com.fcamara.pessoa.core.model.domain.Pessoa;

import java.util.Optional;

public interface PessoaRepository {

    Pessoa salvar(Pessoa pessoa);

    Optional<Pessoa> obterPorId(Long id);

    void deletar(Long id);

    Boolean isPessoaExiste(Long id);

    Boolean isCpfExiste(String cpf);
}
