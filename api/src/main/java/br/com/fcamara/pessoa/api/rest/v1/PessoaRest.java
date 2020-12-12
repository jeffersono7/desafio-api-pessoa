package br.com.fcamara.pessoa.api.rest.v1;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequestMapping("/v1/pessoas")
public interface PessoaRest {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PessoaDTO criar(@RequestBody @Valid PessoaDTO pessoa);
}
