package br.com.fcamara.pessoa.core.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pessoa")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial", nullable = false)
    private Long id;

    @NotEmpty
    @Size(max = 300)
    @Column(name = "nome", columnDefinition = "varchar(300)", nullable = false)
    private String nome;

    @NotEmpty
    @CPF
    @Column(name = "cpf", columnDefinition = "varchar(11)", nullable = false, unique = true)
    private String cpf;

    @Past
    @NotNull
    @Column(name = "data_nascimento", columnDefinition = "timestamp", nullable = false)
    private LocalDate dataNascimento;

    @NotEmpty
    @Column(name = "pais_nascimento", columnDefinition = "varchar(200)", nullable = false)
    private String paisNascimento;

    @NotEmpty
    @Column(name = "estado_nascimento", columnDefinition = "varchar(2)", nullable = false)
    private String estadoNascimento;

    @NotEmpty
    @Column(name = "cidade_nascimento", columnDefinition = "varchar(50)", nullable = false)
    private String cidadeNascimento;

    @Column(name = "nome_pai", columnDefinition = "varchar(300)")
    private String nomePai;

    @Column(name = "nome_mae", columnDefinition = "varchar(300)")
    private String nomeMae;

    @NotEmpty
    @Email
    @Column(name = "email", columnDefinition = "varchar(400)", nullable = false)
    private String email;

    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp", nullable = false)
    @LastModifiedDate
    private LocalDate updatedAt;
}
