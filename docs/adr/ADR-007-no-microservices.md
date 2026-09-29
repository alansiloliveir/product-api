# ADR-007 — Não adotar microserviços neste escopo

- **Status:** Accepted
- **Data:** 2026-09-28

## Contexto

O projeto possui escopo reduzido e um único domínio funcional principal relacionado ao gerenciamento de produtos.

A adoção de microserviços exigiria decisões e infraestrutura adicionais, como comunicação entre serviços, descoberta, observabilidade distribuída, deploy independente, tratamento de falhas remotas e configuração adicional.

## Decisão

Manter a solução como um monólito modular e **não introduzir microserviços neste estágio do projeto**.

## Consequências

### Positivas

- menor complexidade arquitetural e operacional;
- desenvolvimento local simplificado;
- menor quantidade de pontos de falha distribuídos;
- testes e execução mais simples;
- maior foco nas responsabilidades realmente necessárias ao domínio atual.

### Negativas

- componentes não possuem ciclo de deploy independente;
- todo o sistema compartilha o mesmo processo de aplicação;
- uma futura necessidade de separar domínios exigirá revisão arquitetural.

## Condição para revisão

Essa decisão deve ser revisitada caso o sistema evolua para múltiplos domínios com necessidade real de escalabilidade, autonomia de deploy, isolamento operacional ou processamento distribuído.
