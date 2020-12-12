package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.exception.BusinessException;
import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.support.TestSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class PessoaServiceImplTest extends TestSupport {

    @Mock
    private PessoaRepository pessoaRepository;

    private PessoaServiceImpl pessoaService;

    @BeforeEach
    public void setup() {
        pessoaRepository = mock(PessoaRepository.class);

        pessoaService = new PessoaServiceImpl(pessoaRepository);
    }

    @Test
    public void quandoParametrosValidosDeveSalvarPessoa() {
        var pessoa = criarPessoa();
        when(pessoaRepository.isCpfExiste(anyString())).thenReturn(Boolean.FALSE);
        when(pessoaRepository.salvar(any(Pessoa.class)))
                .thenReturn(mockPessoaSalva(pessoa));

        var result = pessoaService.criar(pessoa);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(pessoa.getCidadeNascimento(), result.getCidadeNascimento());
        assertEquals(pessoa.getCpf(), result.getCpf());
        assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
        assertEquals(pessoa.getEmail(), result.getEmail());
        assertEquals(pessoa.getEstadoNascimento(), result.getEstadoNascimento());
        assertEquals(pessoa.getNome(), result.getNome());
        assertEquals(pessoa.getNomeMae(), result.getNomeMae());
        assertEquals(pessoa.getNomePai(), result.getNomePai());
        assertEquals(pessoa.getPaisNascimento(), result.getPaisNascimento());
    }

    @Test
    public void quandoCpfJaExistirDeveLancarErro() {
        var pessoa = criarPessoa();

        when(pessoaRepository.isCpfExiste(anyString())).thenReturn(Boolean.TRUE);

        try{
            pessoaService.criar(pessoa);

            Assert.fail("Deve lancar error quando CPF já estiver cadastrado!");
        } catch (BusinessException e) {
        }
    }

    private Pessoa mockPessoaSalva(Pessoa pessoa) {
        ReflectionTestUtils.setField(pessoa, "id", Long.valueOf(1));
        return pessoa;
    }

    private Pessoa criarPessoa() {
        return Pessoa
                .builder()
                .cpf(CPF)
                .cidadeNascimento("brasilia")
                .estadoNascimento("DF")
                .email("email@teste.com.br")
                .dataNascimento(LocalDate.of(1999, 1, 20))
                .nome("testador")
                .nomeMae("mae teste")
                .nomePai("pai teste")
                .paisNascimento("Brasil")
                .build();
    }
}