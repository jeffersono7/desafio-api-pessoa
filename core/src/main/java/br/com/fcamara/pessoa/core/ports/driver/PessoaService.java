package br.com.fcamara.pessoa.core.ports.driver;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface PessoaService {

    Pessoa criar(@Valid Pessoa pessoa);
}
