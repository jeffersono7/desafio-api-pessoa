package br.com.fcamara.pessoa.api.adapter.jpa.repository;

import br.com.fcamara.pessoa.core.model.entity.Pessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJpaRepository extends PessoaRepository, JpaRepository<Pessoa, Long> {

    Boolean existsByCpfEquals(String cpf);

    @Override
    default Pessoa salvar(Pessoa pessoa) {
        return save(pessoa);
    }

    @Override
    default Boolean isCpfExiste(String cpf) {
        return existsByCpfEquals(cpf);
    }
}
