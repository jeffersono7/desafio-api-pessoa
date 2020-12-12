package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.exception.NotFoundException;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
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
        assertThatPessoaExiste(id);

        Assert.assertEquals(id, pessoa.getId(), Mensagem.ID_INVALIDO);

        return pessoaRepository.salvar(pessoa);
    }

    @Override
    public Pessoa obterPor(@NotNull Long id) {
        return pessoaRepository
                .obterPorId(id)
                .orElseThrow(() -> new NotFoundException(Mensagem.PESSOA_NAO_ENCONTRADA));
    }

    @Override
    public void deletar(@NotNull Long id) {
        assertThatPessoaExiste(id);

        pessoaRepository.deletar(id);
    }

    private void assertThatPessoaExiste(Long id) {
        var pessoaExiste = pessoaRepository.isPessoaExiste(id);

        if (!pessoaExiste) {
            throw new NotFoundException(Mensagem.PESSOA_NAO_ENCONTRADA);
        }
    }
}
