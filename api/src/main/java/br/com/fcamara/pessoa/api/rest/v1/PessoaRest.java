package br.com.fcamara.pessoa.api.rest.v1;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Api(value = "Serviço de pessoas", tags = "v1")
@RequestMapping(
        value = "/v1/pessoas",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public interface PessoaRest {

    @ApiOperation("Criar pessoa")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PessoaDTO criar(@RequestBody @Valid PessoaDTO pessoa);
}
