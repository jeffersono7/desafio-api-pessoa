package br.com.fcamara.pessoa.api.adapter.jpa.repository;

import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaJpaRepository extends PessoaRepository, JpaRepository<Pessoa, Long> {

    Boolean existsByCpfEquals(String cpf);

    Boolean existsByIdEquals(Long id);

    @Override
    default Pessoa salvar(Pessoa pessoa) {
        return save(pessoa);
    }

    @Override
    default Optional<Pessoa> obterPorId(Long id) {
        return findById(id);
    }

    @Override
    default void deletar(Long id) {
        deleteById(id);
    }

    @Override
    default Boolean isPessoaExiste(Long id) {
        return existsByIdEquals(id);
    }

    @Override
    default Boolean isCpfExiste(String cpf) {
        return existsByCpfEquals(cpf);
    }
}
