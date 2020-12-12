package br.com.fcamara.pessoa.api.rest.v1.dto;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable {
    public static final long serialVersionUID = 1946115243492376507L;

    private Long id;

    @NotEmpty
    @Max(300)
    private String nome;

    @NotEmpty
    @CPF
    private String cpf;

    @Past
    @NotNull
    private LocalDate dataNascimento;

    @NotEmpty
    private String paisNascimento;

    @NotEmpty
    private String estadoNascimento;

    @NotEmpty
    private String cidadeNascimento;

    private String nomePai;

    private String nomeMae;

    @NotEmpty
    @Email
    private String email;
}
