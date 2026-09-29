# ADR-006 — Utilizar OpenAPI e Swagger UI

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

A API expõe endpoints REST que podem ser consumidos por clientes externos. Para facilitar o entendimento e a integração, o contrato precisa estar documentado.

Além da descoberta automática dos endpoints, é necessário documentar respostas, erros, DTOs, exemplos e regras relevantes.

## Decisão

Utilizar OpenAPI 3.1 com springdoc para geração da especificação e Swagger UI para visualização e interação com a documentação.

A documentação será enriquecida no código com anotações como `@Tag`, `@Operation`, `@ApiResponse` e `@Schema` quando necessário.

## Consequências

### Positivas

- contrato HTTP explícito;
- documentação acessível diretamente pela aplicação;
- melhor entendimento para consumidores da API;
- documentação de respostas e erros padronizados;
- menor necessidade de documentação manual separada para endpoints.

### Negativas

- anotações adicionais no código;
- a documentação precisa ser mantida coerente com o comportamento da API.
