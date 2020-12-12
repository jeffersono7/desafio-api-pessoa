CREATE SEQUENCE ${schema}.hibernate_sequence START 1;

CREATE TABLE ${schema}.pessoa (
    id serial not null,
    nome varchar(300) not null,
    cpf varchar(11) not null unique,
    data_nascimento timestamp not null,
    pais_nascimento varchar(200) not null,
    estado_nascimento varchar(2) not null,
    cidade_nascimento varchar(50) not null,
    nome_pai varchar(300),
    nome_mae varchar(300),
    email varchar(400) not null,
    primary key(id)
)