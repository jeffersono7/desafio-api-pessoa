package br.com.fcamara.pessoa.api.rest.v1.controller;

import br.com.fcamara.pessoa.api.rest.v1.PessoaRest;
import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.api.rest.v1.mapper.PessoaMapper;
import br.com.fcamara.pessoa.core.exception.InternalServerException;
import br.com.fcamara.pessoa.core.ports.driver.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PessoaRestController implements PessoaRest {

    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    @Override
    public PessoaDTO criar(@Valid PessoaDTO pessoa) {
        return Optional.of(pessoa)
                .map(pessoaMapper::toEntity)
                .map(pessoaService::criar)
                .map(pessoaMapper::toDto)
                .orElseThrow(InternalServerException.supplier("Objeto nulo não esperado!"));
    }

    @Override
    public PessoaDTO alterar(Long id, PessoaDTO pessoa) {
        return Optional.of(pessoa)
                .map(pessoaMapper::toEntity)
                .map(p -> pessoaService.alterar(id, p))
                .map(pessoaMapper::toDto)
                .orElseThrow(InternalServerException.supplier("Objeto nulo não esperado!"));
    }

    @Override
    public PessoaDTO obterPor(Long id) {
        return Optional.of(id)
                .map(pessoaService::obterPor)
                .map(pessoaMapper::toDto)
                .orElseThrow(InternalServerException.supplier("Objeto nulo não esperado!"));
    }

    @Override
    public void deletar(Long id) {
        pessoaService.deletar(id);
    }
}
