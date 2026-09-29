# ADR-005 — Utilizar Testcontainers para testes de integração

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

Os testes de integração precisam validar o fluxo real entre Controller, Service, Repository, JPA e PostgreSQL.

Usar apenas mocks não validaria a integração com o banco de dados. Por outro lado, depender de uma instalação manual e persistente do PostgreSQL tornaria os testes mais dependentes do ambiente local.

## Decisão

Utilizar Testcontainers para executar um PostgreSQL real em container durante os testes de integração.

O teste de integração utiliza Spring Boot, MockMvc e o banco PostgreSQL iniciado pelo Testcontainers.

## Consequências

### Positivas

- validação com banco real;
- menor dependência de configuração manual do ambiente;
- maior proximidade entre o comportamento testado e a infraestrutura real;
- isolamento do banco usado pelos testes.

### Negativas

- testes de integração são mais lentos que testes unitários;
- Docker é necessário para executar esses testes;
- existe custo adicional de inicialização do container.
