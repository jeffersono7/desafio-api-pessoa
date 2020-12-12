package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.ports.driver.PessoaService;
import br.com.fcamara.pessoa.core.utils.Assert;
import br.com.fcamara.pessoa.core.utils.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    public Pessoa criar(Pessoa pessoa) {
        var existeCpfCadastrado = pessoaRepository.isCpfExiste(pessoa.getCpf());

        Assert.assertFalse(existeCpfCadastrado, Mensagem.CPF_JA_CADASTRADO);

        return pessoaRepository.salvar(pessoa);
    }

    @Override
    public Pessoa alterar(Long id, Pessoa pessoa) {
        return null;
    }

    @Override
    public Pessoa obterPor(@NotNull Long id) {
        return null;
    }

    @Override
    public void deletar(@NotNull Long id) {

    }

    private void assertThatPessoaExiste(Long id) {

    }
}
