package br.com.fcamara.pessoa.api.rest.v1.mapper;

import br.com.fcamara.pessoa.api.rest.v1.dto.PessoaDTO;
import br.com.fcamara.pessoa.api.utils.MapperConstant;
import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = MapperConstant.SPRING)
public interface PessoaMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Pessoa toEntity(PessoaDTO pessoa);
    PessoaDTO toDto(Pessoa pessoa);

    Iterable<PessoaDTO> toDto(Iterable<Pessoa> pessoas);
}
