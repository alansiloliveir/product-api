# ADR-002 — Utilizar REST com MVC e Service Layer

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

O sistema precisa expor operações de criação, consulta, atualização, contagem e remoção de produtos por meio de uma API pública HTTP.

Além da exposição HTTP, as regras de negócio precisam permanecer separadas da camada de transporte e da persistência.

## Decisão

Utilizar:

- REST como estilo de comunicação da API;
- Spring MVC para a camada HTTP;
- Controller para endpoints e transformação do contexto HTTP;
- Service Layer para regras de negócio e orquestração;
- Repository para persistência.

## Consequências

### Positivas

- responsabilidades bem definidas;
- Controllers menores e focados em HTTP;
- regras de negócio testáveis independentemente do transporte;
- persistência desacoplada da camada HTTP;
- facilidade para testar cada camada separadamente.

### Negativas

- aumento do número de classes e camadas em comparação com um CRUD concentrado em um único Controller;
- para um domínio muito pequeno, parte dessa estrutura pode parecer adicional, embora seja intencional para fins de manutenção e evolução.
