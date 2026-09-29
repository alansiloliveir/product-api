# ADR-004 — Utilizar PostgreSQL com Spring Data JPA

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

A API precisa persistir produtos e oferecer operações de consulta, atualização, contagem e remoção.

O domínio possui estrutura relacional simples, adequada a um banco SQL.

## Decisão

Utilizar PostgreSQL como banco de dados e Spring Data JPA como tecnologia de persistência.

A aplicação utiliza uma entidade `Product` mapeada para a tabela `products`.

## Consequências

### Positivas

- banco relacional maduro e adequado ao domínio;
- persistência baseada em padrões JPA;
- integração direta com Spring Data;
- possibilidade de executar a aplicação com PostgreSQL em Docker.

### Negativas

- dependência de infraestrutura de banco relacional;
- necessidade de administrar a configuração do banco nos ambientes de execução;
- abstração JPA não elimina a necessidade de compreender o comportamento do banco e das queries geradas.
