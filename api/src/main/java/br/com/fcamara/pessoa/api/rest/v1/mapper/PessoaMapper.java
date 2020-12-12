package br.com.fcamara.pessoa.api.rest.v1.mapper;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.api.utils.MapperConstant;
import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = MapperConstant.SPRING)
public interface PessoaMapper {

    Pessoa toEntity(PessoaDTO pessoa);
    PessoaDTO toDto(Pessoa pessoa);
}
