# ADR-003 — Utilizar DTOs e Mapper

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

A entidade `Product` representa o modelo interno utilizado pela aplicação e pela persistência. O contrato HTTP possui necessidades próprias para entrada e saída de dados.

Expor diretamente a entidade de persistência aumentaria o acoplamento entre o modelo interno e o contrato público da API.

## Decisão

Utilizar:

- `ProductRequest` para entrada da API;
- `ProductResponse` para saída da API;
- `ProductMapper` para conversão entre DTOs e `Product`.

O Controller trabalha com os contratos HTTP e o Service trabalha com o modelo interno.

## Consequências

### Positivas

- contrato da API desacoplado da entidade persistente;
- maior controle sobre os dados expostos;
- evolução independente entre API e modelo interno;
- responsabilidades de conversão centralizadas no Mapper.

### Negativas

- criação de objetos e código adicional de mapeamento;
- alterações nos DTOs podem exigir ajustes no Mapper.
