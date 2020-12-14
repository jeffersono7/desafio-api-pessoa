package br.com.fcamara.pessoa.core.service;

import br.com.fcamara.pessoa.core.exception.BusinessException;
import br.com.fcamara.pessoa.core.exception.NotFoundException;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import br.com.fcamara.pessoa.core.support.TestSupport;
import br.com.fcamara.pessoa.core.utils.Mensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

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

    //    method criar
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

        try {
            pessoaService.criar(pessoa);

            fail("Deve lancar error quando CPF já estiver cadastrado!");
        } catch (BusinessException ex) {
            assertEquals(Mensagem.CPF_JA_CADASTRADO, ex.getMensagem());
        }
    }

    //    method alterar
    @Test
    public void quandoParametrosValidosDeveAlterarPessoa() {
        var pessoa = Optional.of(criarPessoa()).map(this::mockPessoaSalva).orElse(null);

        when(pessoaRepository.isPessoaExiste(pessoa.getId())).thenReturn(Boolean.TRUE);
        when(pessoaRepository.salvar(pessoa)).thenReturn(pessoa);

        var result = pessoaService.alterar(pessoa.getId(), pessoa);

        assertEquals(pessoa.getId(), result.getId());
    }

    @Test
    public void quandoPessoaNaoExisteETentarAlterarDeveLancarError() {
        var pessoa = criarPessoa();

        when(pessoaRepository.isPessoaExiste(pessoa.getId())).thenReturn(Boolean.FALSE);

        try {
            pessoaService.alterar(pessoa.getId(), pessoa);

            fail("Deve lançar exception para pessoa não cadastrada!");
        } catch (NotFoundException ex) {
            assertEquals(Mensagem.PESSOA_NAO_ENCONTRADA, ex.getMensagem());
        }
    }

    @Test
    public void quandoIdsDePessoaSaoDiferentesDeveLancarError() {
        var pessoa = Optional.of(criarPessoa()).map(this::mockPessoaSalva).orElse(null);
        Long idDiferente = Long.valueOf(479);

        assertNotEquals(idDiferente, pessoa.getId());

        when(pessoaRepository.isPessoaExiste(idDiferente)).thenReturn(Boolean.TRUE);

        try {
            pessoaService.alterar(idDiferente, pessoa);

            fail("Deve lançar exception para pessoa não cadastrada!");
        } catch (BusinessException ex) {
            assertEquals(Mensagem.ID_INVALIDO, ex.getMensagem());
        }
    }

    // method obterPor
    @Test
    public void quandoParametrosValidosDeveRetornarUmaPessoaExistente() {
        var pessoa = Optional.of(criarPessoa()).map(this::mockPessoaSalva).orElse(null);

        when(pessoaRepository.obterPorId(pessoa.getId()))
                .thenReturn(Optional.of(pessoa));

        var result = pessoaService.obterPor(pessoa.getId());

        assertEquals(pessoa.getId(), result.getId());
    }

    @Test
    public void quandoTentarObterPorIdDePessoaQueNaoExisteDeveLancarError() {
        try {
            pessoaService.obterPor(Long.valueOf(1));

            fail("Deve lançar exception ao tentar obter pessoa que não existe!");
        } catch (NotFoundException ex) {
            assertEquals(Mensagem.PESSOA_NAO_ENCONTRADA, ex.getMensagem());
        }
    }

    //    method deletar
    @Test
    public void quandoParametrosValidosDeveDeletarPessoa() {
        var idPessoa = Long.valueOf(1);

        when(pessoaRepository.isPessoaExiste(idPessoa)).thenReturn(Boolean.TRUE);

        pessoaService.deletar(idPessoa);
    }

    @Test
    public void quandoTentarDeletarPessoaQueNaoExisteDeveLancarError() {
        var idPessoa = Long.valueOf(1);

        when(pessoaRepository.isPessoaExiste(idPessoa)).thenReturn(Boolean.FALSE);

        try {
            pessoaService.deletar(idPessoa);

            fail("Deve lançar erro ao tentar deletar pessoa que não existe!");
        } catch (NotFoundException ex) {
            assertEquals(Mensagem.PESSOA_NAO_ENCONTRADA, ex.getMensagem());
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