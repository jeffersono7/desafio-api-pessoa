package br.com.fcamara.pessoa.api.adapter.jpa.repository;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.model.predicate.PessoaPredicatesBuilder;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PessoaJpaRepositoryIT {
    static final String CPF_GERADO = "49008655080";

    @Autowired
    PessoaJpaRepository pessoaJpaRepository;

    private Pessoa pessoa;

    @BeforeEach
    public void setup() {
        pessoa = Pessoa.builder()
                .cidadeNascimento("Brasilia")
                .cpf(CPF_GERADO)
                .dataNascimento(LocalDate.of(1995, 2, 20))
                .email("spring@teste.com.br")
                .estadoNascimento("DF")
                .nome("testador")
                .nomeMae("mae do testador")
                .nomePai("pai do testador")
                .paisNascimento("Brasil")
                .build();

        pessoaJpaRepository.salvar(pessoa);
    }

    @Test
    public void deveObterPessoasPesquisandoPeloNome() {
        PessoaPredicatesBuilder builder = new PessoaPredicatesBuilder()
                .with("nome", ":", "testador");

        Iterable<Pessoa> result = pessoaJpaRepository.procurarTodos(builder.build());

        assertThat(result, containsInAnyOrder(pessoa));
    }
}
