package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.exception.NotFoundException;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.ports.driver.PessoaService;
import br.com.fcamara.pessoa.core.utils.Assert;
import br.com.fcamara.pessoa.core.utils.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public Pessoa criar(Pessoa pessoa) {
        var existeCpfCadastrado = pessoaRepository.isCpfExiste(pessoa.getCpf());

        Assert.assertFalse(existeCpfCadastrado, Mensagem.CPF_JA_CADASTRADO);

        return pessoaRepository.salvar(pessoa);
    }

    @Override
    @Transactional
    public Pessoa alterar(Long id, Pessoa pessoa) {
        assertThatPessoaExiste(id);

        Assert.assertEquals(id, pessoa.getId(), Mensagem.ID_INVALIDO);

        var pessoaPersistente = pessoaRepository.obterPorId(id).orElse(null);

        alterarPessoaPersistente(pessoaPersistente, pessoa);

        return pessoaRepository.salvar(pessoaPersistente);
    }

    @Override
    public Pessoa obterPor(@NotNull Long id) {
        return pessoaRepository
                .obterPorId(id)
                .orElseThrow(() -> new NotFoundException(Mensagem.PESSOA_NAO_ENCONTRADA));
    }

    @Override
    @Transactional
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

    private void alterarPessoaPersistente(Pessoa pessoaPersistente, Pessoa pessoa) {
        pessoaPersistente.setCidadeNascimento(pessoa.getCidadeNascimento());
        pessoaPersistente.setCpf(pessoa.getCpf());
        pessoaPersistente.setDataNascimento(pessoa.getDataNascimento());
        pessoaPersistente.setEmail(pessoa.getEmail());
        pessoaPersistente.setEstadoNascimento(pessoa.getEstadoNascimento());
        pessoaPersistente.setNome(pessoa.getNome());
        pessoaPersistente.setNomeMae(pessoa.getNomeMae());
        pessoaPersistente.setNomePai(pessoa.getNomePai());
        pessoaPersistente.setPaisNascimento(pessoa.getPaisNascimento());
    }
}
