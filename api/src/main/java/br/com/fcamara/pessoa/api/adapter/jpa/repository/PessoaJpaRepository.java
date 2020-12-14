package br.com.fcamara.pessoa.api.adapter.jpa.repository;

import br.com.fcamara.pessoa.core.model.domain.Pessoa;
import br.com.fcamara.pessoa.core.model.domain.QPessoa;
import br.com.fcamara.pessoa.core.ports.driven.PessoaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaJpaRepository extends PessoaRepository,
        JpaRepository<Pessoa, Long>,
        QuerydslPredicateExecutor<Pessoa>,
        QuerydslBinderCustomizer<QPessoa> {

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

    @Override
    default void customize(QuerydslBindings bindings, QPessoa qPessoa) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(
                qPessoa.id,
                qPessoa.createdAt,
                qPessoa.email,
                qPessoa.nomeMae,
                qPessoa.nomePai,
                qPessoa.updatedAt);
    }

    @Override
    default Iterable<Pessoa> procurarTodos(BooleanExpression exp) {
        return Optional.ofNullable(exp)
                .map(this::findAll)
                .orElse(this.findAll());
    }
}
