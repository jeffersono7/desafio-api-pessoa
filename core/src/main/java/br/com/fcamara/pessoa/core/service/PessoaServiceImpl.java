package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.ports.driver.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    public Pessoa criar(Pessoa pessoa) {
        return pessoaRepository.salvar(pessoa);
    }
}
