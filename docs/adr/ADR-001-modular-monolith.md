# ADR-001 — Adotar monólito modular

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

A aplicação precisa disponibilizar uma API REST para gerenciamento de produtos. O domínio inicial é pequeno e possui um único contexto funcional principal.

O projeto também precisa ser simples de desenvolver, testar, executar e demonstrar no contexto do desafio de arquitetura.

## Decisão

Adotar uma arquitetura de **monólito modular**, implementada como uma única aplicação Spring Boot, com separação interna de responsabilidades entre Controller, Service, Mapper, Repository, DTOs e tratamento de exceções.

## Alternativas consideradas

### Microserviços

Não adotados para este escopo porque introduziriam complexidade operacional e de comunicação distribuída sem uma necessidade funcional clara.

### Monólito sem separação de responsabilidades

Não adotado porque dificultaria testes, manutenção e evolução das responsabilidades internas.

## Consequências

### Positivas

- menor complexidade operacional;
- execução e deploy simplificados;
- baixo custo de comunicação entre componentes internos;
- facilidade para desenvolvimento e testes locais;
- estrutura preparada para evolução modular.

### Negativas

- componentes são implantados juntos;
- escalabilidade é principalmente no nível da aplicação;
- uma futura necessidade de implantação independente exigiria uma evolução arquitetural.
