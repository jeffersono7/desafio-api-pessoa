# API para crud de pessoa

Desafio técnico: API para crud de pessoa.

## Subir projeto na maquina local

Requisitos:

- Java 11
- Maven
- PostgreSQL
- Docker e Docker Compose

### Passos para começar

- Clonar o repositório na sua máquina.

- Subir um banco de dados local com docker, através de um script shell

```shell script
$ chmod +x ./devops/start.sh

$ ./devops/start.sh
```

- Importar o projeto em sua IDE de preferência

- Limpar e executar as migrações de banco de dados

```shell script
$ cd db
$ mvn flyway:clean flyway:migrate
```

- Caso desejar subir o projeto através do terminal:

```shell script
$ mvn clean install
$ cd api
$ mvn spring-boot:run
```
