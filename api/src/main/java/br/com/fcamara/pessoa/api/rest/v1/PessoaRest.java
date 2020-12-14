package br.com.fcamara.pessoa.api.rest.v1;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Serviço de pessoas", tags = "v1")
@RequestMapping(
        value = PessoaRest.PATH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public interface PessoaRest {

    String PATH = "/v1/pessoas";
    String ID_PATH_VARIABLE = "id";
    String ID_PATH = "/{"+ ID_PATH_VARIABLE + "}";

    @ApiOperation("Criar pessoa")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PessoaDTO criar(@RequestBody @Valid PessoaDTO pessoa);

    @ApiOperation("Alterar pessoa")
    @PutMapping(ID_PATH)
    PessoaDTO alterar(@PathVariable(ID_PATH_VARIABLE) Long id, @RequestBody PessoaDTO pessoa);

    @ApiOperation("Obter pessoa pelo id")
    @GetMapping(ID_PATH)
    PessoaDTO obterPor(@PathVariable(ID_PATH_VARIABLE) Long id);

    @ApiOperation("Deletar pessoa")
    @DeleteMapping(ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletar(@PathVariable(ID_PATH_VARIABLE) Long id);

    @ApiOperation("Filtrar pessoas")
    @GetMapping
    Iterable<PessoaDTO> filtrar(@RequestParam("search") @Nullable String query);
}
