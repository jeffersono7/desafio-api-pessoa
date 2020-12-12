package br.com.fcamara.pessoa.api.rest.v1.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(name = "ID da pessoa")
    private Long id;

    @ApiModelProperty(name = "Nome completo da pessoa", required = true)
    @NotEmpty
    @Size(max = 300)
    private String nome;

    @ApiModelProperty(name = "CPF da pessoa", required = true)
    @NotEmpty
    @CPF
    private String cpf;

    @ApiModelProperty(name = "Data de nascimento da pessoa", required = true)
    @Past
    @NotNull
    private LocalDate dataNascimento;

    @ApiModelProperty(name = "País de nascimento da pessoa", required = true)
    @NotEmpty
    private String paisNascimento;

    @ApiModelProperty(name = "Estado (UF) de nascimento da pessoa", required = true)
    @NotEmpty
    private String estadoNascimento;

    @ApiModelProperty(name = "Cidade de nascimento da pessoa", required = true)
    @NotEmpty
    private String cidadeNascimento;

    @ApiModelProperty(name = "Nome do pai da pessoa")
    private String nomePai;

    @ApiModelProperty(name = "Nome da mãe da pessoa")
    private String nomeMae;

    @ApiModelProperty(name = "E-mail da pessoa", required = true)
    @NotEmpty
    @Email
    private String email;
}
