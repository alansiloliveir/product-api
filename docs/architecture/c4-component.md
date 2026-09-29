# C4 — Component

## Objetivo

Detalhar os principais componentes internos da Product API.

## Componentes

### ProductController

Expõe os endpoints REST, recebe as requisições HTTP, valida os DTOs e constrói as respostas HTTP.

### ProductService

Concentra as regras de negócio e orquestra as operações relacionadas aos produtos.

### ProductMapper

Converte entre os DTOs da API e a entidade `Product`.

### ProductRepository

Abstrai o acesso ao PostgreSQL utilizando Spring Data JPA.

### DTOs

- `ProductRequest`: dados recebidos nas operações de criação e atualização.
- `ProductResponse`: dados retornados pela API.

### Domínio

- `Product`: entidade do produto.
- `ProductStatus`: enumeração `ACTIVE` / `INACTIVE`.

### GlobalExceptionHandler

Centraliza o tratamento de exceções e converte falhas da aplicação em respostas HTTP padronizadas.

### ErrorResponse / FieldErrorResponse

Representam o contrato das respostas de erro da API.

## Fluxo principal

```text
API Consumer
     |
     | HTTP/REST
     v
ProductController
     |
     | chama
     v
ProductService
     |
     | usa
     v
ProductRepository
     |
     | JPA/JDBC
     v
PostgreSQL
```

O Controller utiliza DTOs e o Mapper para separar o contrato HTTP do modelo interno.
