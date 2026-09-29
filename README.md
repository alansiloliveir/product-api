# Product API

API REST para gerenciamento de produtos, desenvolvida como um projeto prático de arquitetura de software.

O projeto foi construído com foco em **simplicidade, separação de responsabilidades, testabilidade e documentação arquitetural**, evitando complexidade desnecessária para o domínio proposto.

## Objetivo

Disponibilizar uma API REST para criação, consulta, atualização, contagem e remoção de produtos.

O projeto atende ao cenário proposto no desafio de Software Architect, que solicita uma API pública para exposição dos dados de produtos, utilizando MVC e operações CRUD, incluindo consulta por nome, consulta por ID e contagem. A persistência foi implementada como diferencial.

## Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web / MVC
- Spring Data JPA
- PostgreSQL
- Gradle
- Docker / Docker Compose
- OpenAPI 3.1 / Swagger UI
- JUnit
- Mockito
- Spring MVC Test / MockMvc
- Spring Boot Test
- Testcontainers

## Arquitetura

A aplicação foi estruturada como um **monólito modular**, utilizando REST, MVC, Service Layer e Repository.

```text
API Consumer
    |
    | HTTP/REST
    v
Product API
    |
    +-- Controller
    |      |
    |      +-- DTOs
    |      +-- Mapper
    |      +-- Exception Handler
    |
    +-- Service
    |
    +-- Repository
           |
           | JPA/JDBC
           v
       PostgreSQL
```

### Responsabilidades

**Controller**  
Responsável pela camada HTTP, endpoints REST, entrada e saída de dados e códigos de status.

**DTOs**  
`ProductRequest` e `ProductResponse` representam os contratos de entrada e saída da API, evitando acoplamento direto entre a API e a entidade de persistência.

**Mapper**  
`ProductMapper` realiza a conversão entre DTOs e a entidade `Product`.

**Service**  
`ProductService` concentra as regras de negócio e orquestra as operações do domínio.

**Repository**  
`ProductRepository` abstrai a persistência utilizando Spring Data JPA.

**Exception Handler**  
`GlobalExceptionHandler` converte exceções da aplicação em respostas HTTP padronizadas.

## Domínio

A entidade principal é `Product`.

| Campo | Tipo | Regra |
|---|---|---|
| `id` | `Long` | Identificador gerado pelo banco |
| `name` | `String` | Obrigatório, até 100 caracteres |
| `description` | `String` | Até 500 caracteres |
| `price` | `BigDecimal` | Obrigatório e maior que zero |
| `status` | `ProductStatus` | `ACTIVE` ou `INACTIVE` |

### Regra de status

Na criação, o campo `status` é opcional. Quando não informado, a aplicação define `ACTIVE` no Service.

`DELETE` representa remoção física do recurso e é diferente de alterar o status para `INACTIVE`.

## API

Base URL local:

```text
http://localhost:8080
```

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/api/products` | Cria um produto | `201 Created` |
| `GET` | `/api/products` | Lista produtos | `200 OK` |
| `GET` | `/api/products/{id}` | Busca por ID | `200 OK` / `404 Product not Found` |
| `GET` | `/api/products/search?name=...` | Busca por nome | `200 OK` |
| `GET` | `/api/products/count` | Conta produtos | `200 OK` |
| `PUT` | `/api/products/{id}` | Atualiza um produto | `200 OK` / `400` / `404` |
| `DELETE` | `/api/products/{id}` | Remove um produto | `204 No Content` / `404` |

## Documentação OpenAPI

Após iniciar a aplicação, a documentação está disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

Especificação OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

A documentação inclui endpoints, DTOs, exemplos, códigos HTTP e estrutura padronizada de erros.

## Tratamento de erros

A API utiliza uma estrutura padronizada por meio de `ErrorResponse`.

Exemplo de recurso não encontrado:

```json
{
  "timestamp": "2026-09-28T20:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found",
  "errors": []
}
```

Para erros de validação, `errors` contém os campos que apresentaram problema e suas respectivas mensagens.

## Testes

O projeto possui três níveis principais de teste.

### Testes unitários

`ProductServiceTest` utiliza JUnit e Mockito para testar as regras de negócio com `ProductRepository` isolado por mock.

### Testes da camada Web

`ProductControllerTest` utiliza `@WebMvcTest` e `MockMvc` para validar o comportamento HTTP do Controller sem executar banco de dados real.

### Testes de integração

`ProductApiIntegrationTest` utiliza `@SpringBootTest`, `MockMvc` e Testcontainers com PostgreSQL real, validando a integração entre Controller, Service, Repository, JPA e banco.

## Execução local

### Pré-requisitos

- Java 21
- Docker Desktop / Docker Engine
- Git

### 1. Subir o PostgreSQL

Na raiz do projeto:

```powershell
docker compose up -d
```

### 2. Executar os testes

```powershell
.\gradlew.bat test
```

### 3. Executar a aplicação

```powershell
.\gradlew.bat bootRun
```

### 4. Abrir o Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

## Estrutura do projeto

```text
product-api/
├── docs/
│   ├── adr/
│   └── architecture/
├── src/
│   ├── main/
│   │   ├── java/com/alan/product_api/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ProductApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/alan/product_api/
├── build.gradle
├── docker-compose.yml
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Decisões arquiteturais

As principais decisões do projeto estão registradas em ADRs:

- [ADR-001 — Monólito modular](docs/adr/ADR-001-modular-monolith.md)
- [ADR-002 — REST + MVC + Service Layer](docs/adr/ADR-002-rest-mvc-service-layer.md)
- [ADR-003 — DTOs + Mapper](docs/adr/ADR-003-dto-mapper.md)
- [ADR-004 — PostgreSQL + JPA](docs/adr/ADR-004-postgresql-jpa.md)
- [ADR-005 — Testcontainers para testes de integração](docs/adr/ADR-005-testcontainers.md)
- [ADR-006 — OpenAPI/Swagger](docs/adr/ADR-006-openapi.md)
- [ADR-007 — Não adotar microserviços neste escopo](docs/adr/ADR-007-no-microservices.md)

## Arquitetura C4

Os diagramas C4 do projeto ficam em `docs/architecture/`.

- Context: visão do sistema e seus atores externos.
- Container: visão da aplicação e seus principais containers.
- Component: visão interna dos principais componentes da Product API.

Os arquivos editáveis podem ser mantidos em formato `.drawio` e exportados para SVG para visualização no GitHub.

## Princípios utilizados

O projeto prioriza:

- separação clara de responsabilidades;
- baixo acoplamento entre API, regras de negócio e persistência;
- contratos explícitos por meio de DTOs;
- testes em diferentes níveis;
- documentação da API;
- decisões arquiteturais registradas;
- complexidade compatível com o tamanho do problema.

## Escopo e evolução

A arquitetura atual foi mantida deliberadamente simples. Não foram introduzidos microserviços, Kafka, Redis, Kubernetes ou outras tecnologias distribuídas porque o domínio e os requisitos atuais não justificam essa complexidade.

Em uma evolução futura, a necessidade de novos domínios, requisitos de escala, processamento assíncrono ou implantação independente poderia motivar uma revisão dessas decisões.
