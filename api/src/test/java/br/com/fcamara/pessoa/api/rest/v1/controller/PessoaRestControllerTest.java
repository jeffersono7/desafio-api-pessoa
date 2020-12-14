package br.com.fcamara.pessoa.api.rest.v1.controller;

import br.com.fcamara.pessoa.api.rest.v1.PessoaRest;
import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.api.support.ITSupport;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

//    method alterar
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

    @Test
    @SneakyThrows
    public void quandoTentarAlterarPessoaComIdsDiferentesDeveRetornarError() {
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

        pessoaSalva.setId(pessoaSalva.getId() + 1);

        var pathPessoa = PessoaRest.PATH + "/" + pessoaSalva.getId().toString();

        put(pathPessoa, pessoaSalva, status().isBadRequest(), String.class);
    }

    //    method obterPor
    @Test
    public void quandoTentarObterUmaPessoaExistentePeloIdDeveRetornarAPessoa() {
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

        var pathPessoa = PessoaRest.PATH + "/" + pessoaSalva.getId().toString();

        var pessoaRecuperada = get(pathPessoa, status().isOk(), PessoaDTO.class);

        assertEquals(pessoaSalva.getId(), pessoaRecuperada.getId());
        assertEquals(pessoaSalva.getCidadeNascimento(), pessoaRecuperada.getCidadeNascimento());
        assertEquals(pessoaSalva.getCpf(), pessoaRecuperada.getCpf());
        assertEquals(pessoaSalva.getDataNascimento(), pessoaRecuperada.getDataNascimento());
        assertEquals(pessoaSalva.getEmail(), pessoaRecuperada.getEmail());
        assertEquals(pessoaSalva.getEstadoNascimento(), pessoaRecuperada.getEstadoNascimento());
        assertEquals(pessoaSalva.getNome(), pessoaRecuperada.getNome());
        assertEquals(pessoaSalva.getNomeMae(), pessoaRecuperada.getNomeMae());
        assertEquals(pessoaSalva.getNomePai(), pessoaRecuperada.getNomePai());
        assertEquals(pessoaSalva.getPaisNascimento(), pessoaRecuperada.getPaisNascimento());
    }

    @Test
    public void quandoTentarObterUmaPessoaPeloIdQueNaoExisteDeveRetornarError() {
        get(PessoaRest.PATH + "/1", status().isNotFound(), String.class);
    }

//    method deletar
    @Test
    public void quandoParametrosValidosDeveDeletarPessoaExistente() {
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

        var pathPessoa = PessoaRest.PATH + "/" + pessoaSalva.getId().toString();

        delete(pathPessoa, status().isNoContent());
    }

    @Test
    public void quandoTentarDeletarUmaPessoaQueNaoExisteDeveRetornarError() {
        delete(PessoaRest.PATH + "/1", status().isNotFound());
    }
}