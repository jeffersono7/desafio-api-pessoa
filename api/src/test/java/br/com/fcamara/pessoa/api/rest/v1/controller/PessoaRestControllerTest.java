package br.com.fcamara.pessoa.api.rest.v1.controller;

import br.com.fcamara.pessoa.api.adapter.jpa.repository.PessoaJpaRepository;
import br.com.fcamara.pessoa.api.rest.v1.PessoaRest;
import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.api.support.ITSupport;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaRestControllerTest implements ITSupport {

    @Getter
    @Autowired
    private MockMvc mockMvc;

    @Getter
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PessoaRepository pessoaRepository;

    //    criar pessoa
    @Test
    @SneakyThrows
    public void quandoParametrosValidosDeveCriarNovaPessoa() {
        var pessoa = PessoaDTO.builder()
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

        var pessoaSalva = post(PessoaRest.PATH, pessoa, status().isCreated(), PessoaDTO.class);

        assertNotNull(pessoaSalva.getId());
        assertEquals(pessoa.getCidadeNascimento(), pessoaSalva.getCidadeNascimento());
        assertEquals(pessoa.getCpf(), pessoaSalva.getCpf());
        assertEquals(pessoa.getDataNascimento(), pessoaSalva.getDataNascimento());
        assertEquals(pessoa.getEmail(), pessoaSalva.getEmail());
        assertEquals(pessoa.getEstadoNascimento(), pessoaSalva.getEstadoNascimento());
        assertEquals(pessoa.getNome(), pessoaSalva.getNome());
        assertEquals(pessoa.getNomeMae(), pessoaSalva.getNomeMae());
        assertEquals(pessoa.getNomePai(), pessoaSalva.getNomePai());
        assertEquals(pessoa.getPaisNascimento(), pessoaSalva.getPaisNascimento());

        pessoaRepository.deletar(pessoaSalva.getId());
    }

    @Test
    @SneakyThrows
    public void quandoTentarCriarPessoaComParametrosInvalidosDeveRetornarStatus400() {
        var pessoa = PessoaDTO.builder().build();

        post(PessoaRest.PATH, pessoa, status().isBadRequest(), String.class);
    }


    @Test
    @SneakyThrows
    public void quandoParametrosValidosDeveAlterarPessoa() {
        var pessoa = PessoaDTO.builder()
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

        var pessoaSalva = post(PessoaRest.PATH, pessoa, status().isCreated(), PessoaDTO.class);

        pessoaSalva.setEmail("outro-email@teste.com.br");
        pessoaSalva.setEstadoNascimento("SP");

        var pathPessoa = PessoaRest.PATH + "/" + pessoaSalva.getId().toString();

        var pessoaAlterada = put(pathPessoa, pessoaSalva, status().isOk(), PessoaDTO.class);

        assertEquals(pessoaSalva.getId(), pessoaAlterada.getId());
        assertEquals(pessoaSalva.getEmail(), pessoaAlterada.getEmail());
        assertEquals(pessoaSalva.getEstadoNascimento(), pessoaAlterada.getEstadoNascimento());

        pessoaRepository.deletar(pessoaAlterada.getId());
    }
}