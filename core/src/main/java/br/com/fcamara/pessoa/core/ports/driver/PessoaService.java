package br.com.fcamara.pessoa.core.ports.driver;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface PessoaService {

    Pessoa criar(@Valid Pessoa pessoa);

    Pessoa alterar(@NotNull Long id, @Valid Pessoa pessoa);

    Pessoa obterPor(@NotNull Long id);

    void deletar(@NotNull Long id);
}
