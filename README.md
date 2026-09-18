# Demo DAO JDBC — Acesso a Dados com JDBC

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/guilhermenoe2020-netizen/demo-dao-jdbc/blob/main/LICENSE)

# Sobre o projeto

Este projeto é uma aplicação Java desenvolvida para praticar **acesso a banco de dados utilizando JDBC**, com implementação do padrão **DAO (Data Access Object)**.

A aplicação trabalha com **Vendedores (`Seller`)** e **Departamentos (`Department`)**, permitindo realizar operações de **CRUD** diretamente em um banco de dados MySQL.

O projeto também aplica separação de responsabilidades entre a aplicação, os objetos de acesso aos dados e a conexão com o banco.

## Modelo conceitual

![Modelo Conceitual](modelo%20conceitual.png)

# Tecnologias utilizadas

## Back end

* Java
* JDBC
* MySQL
* MySQL Connector/J
* Eclipse
* Git / GitHub

# Arquitetura

```text
Application
     │
     ▼
  DAO Interface
     │
     ▼
 DAO JDBC
     │
     ▼
    DB
     │
     ▼
 MySQL
```

A aplicação utiliza interfaces DAO para definir as operações de acesso aos dados e implementações JDBC para executar essas operações no banco.

A `DaoFactory` é responsável pela criação das implementações dos DAOs.

# Entidades

## Department

Tabela: `department`

| Campo | Tipo    | Descrição            |
| ----- | ------- | -------------------- |
| id    | Integer | Identificador        |
| name  | String  | Nome do departamento |

## Seller

Tabela: `seller`

| Campo      | Tipo       | Descrição              |
| ---------- | ---------- | ---------------------- |
| id         | Integer    | Identificador          |
| name       | String     | Nome do vendedor       |
| email      | String     | E-mail                 |
| birthDate  | Date       | Data de nascimento     |
| baseSalary | Double     | Salário base           |
| department | Department | Departamento associado |

### Relacionamento

```text
Department 1 ───────── N Seller
```

Um departamento pode possuir vários vendedores, enquanto cada vendedor pertence a um departamento.

# Banco de dados

O projeto utiliza **MySQL** com o banco:

```text
coursejdbc
```

Tabelas utilizadas:

```text
department
    │
    └── seller
```

A tabela `seller` possui a chave estrangeira `DepartmentId`, relacionada à tabela `department`.

# Tratamento de exceções

O projeto possui exceções próprias para representar erros relacionados ao banco de dados.

| Exceção                | Utilização                        |
| ---------------------- | --------------------------------- |
| `DbException`          | Erros gerais de acesso ao banco   |
| `DbIntegrityException` | Violações de integridade do banco |

Exemplo de situação tratada por `DbIntegrityException`:

* Tentativa de excluir um departamento que possui vendedores associados.

# Conceitos aplicados

**JDBC** — comunicação direta entre a aplicação Java e o banco de dados.

**DAO (Data Access Object)** — organização das operações de persistência em objetos específicos.

**DAO Factory** — criação centralizada das implementações dos DAOs.

**PreparedStatement** — execução de comandos SQL parametrizados.

**ResultSet** — leitura dos dados retornados pelas consultas.

**Generated Keys** — recuperação do ID gerado pelo banco após um `INSERT`.

**INNER JOIN** — consulta de vendedores junto com seus respectivos departamentos.

**Map** — reutilização das instâncias de `Department` durante consultas de vendedores.

**CRUD** — implementação das operações de criação, consulta, atualização e exclusão.

**Relacionamento 1:N** — associação entre `Department` e `Seller`.

# Agradecimentos

Este projeto foi desenvolvido durante meus estudos de **Java e Programação Orientada a Objetos**,
com base no curso do [DevSuperior](https://devsuperior.com.br/)
