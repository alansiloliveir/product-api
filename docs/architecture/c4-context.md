# C4 — System Context

## Objetivo

Representar o contexto externo da Product API sem detalhar a estrutura interna da aplicação.

## Elementos

### API Consumer

Cliente, sistema parceiro ou aplicação que consome a Product API por HTTP/REST.

### Product API

Sistema responsável pelo gerenciamento de produtos através de uma API REST pública.

## Relação

```text
API Consumer
     |
     | HTTP/REST
     v
Product API
```

## Regra do nível Context

Neste nível não são apresentados Controller, Service, Repository, JPA ou PostgreSQL. Esses detalhes pertencem aos níveis Container e Component.
