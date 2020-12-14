package br.com.fcamara.pessoa.core.model.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PessoaPredicatesBuilder {
    private List<SearchCriteria> params;

    public PessoaPredicatesBuilder() {
        params = new ArrayList<>();
    }

    public PessoaPredicatesBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }

        List<BooleanExpression> predicates = params
                .stream()
                .map(param -> {
                    PessoaPredicate predicate = new PessoaPredicate(param);
                    return predicate.getPredicate();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();

        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }
}
