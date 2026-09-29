# C4 — Container

## Objetivo

Representar os principais containers da solução e suas relações.

## Elementos

### Product API — Spring Boot Application

Aplicação monolítica responsável por disponibilizar a API REST e executar as regras de negócio.

### PostgreSQL

Banco de dados relacional responsável por armazenar os produtos.

## Relações

```text
API Consumer
     |
     | HTTP/REST
     v
Product API
     |
     | JPA/JDBC
     v
PostgreSQL
```

## Observação

No vocabulário C4, `Container` representa uma unidade executável ou de armazenamento dentro do sistema. O termo não está limitado ao conceito de container Docker.
