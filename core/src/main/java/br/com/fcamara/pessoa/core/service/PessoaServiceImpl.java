package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.exception.NotFoundException;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.model.predicate.PessoaPredicatesBuilder;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.ports.driver.PessoaService;
import br.com.fcamara.pessoa.core.utils.Assert;
import br.com.fcamara.pessoa.core.utils.Mensagem;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public Iterable<Pessoa> search(String query) {
        var predicatesBuilder = new PessoaPredicatesBuilder();

        if (query != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(query + ",");

            while (matcher.find()) {
                predicatesBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = predicatesBuilder.build();
        return pessoaRepository.procurarTodos(exp);
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
