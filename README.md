# Desafios Java

Desafios técnicos de nível estágio/júnior resolvidos em Java 21, todos como aplicações de console. Cada pasta é um módulo Maven independente, com enunciado, instruções de execução e testes.

| # | Desafio | Conceitos praticados | Status |
|---|---------|----------------------|--------|
| 01 | [Busca de Nomes](01-busca-de-nomes) | Leitura de arquivos, tratamento de exceções, concorrência | Em andamento |
| 02 | [Analisador de Logs](02-analisador-de-logs) | Parsing de texto, `Map` para contagem, `LocalTime` | A fazer |
| 03 | [Consolidador de Vendas](03-consolidador-de-vendas) | CSV, `BigDecimal`, validação de dados | A fazer |
| 04 | [Comparador de Cadastros](04-comparador-de-cadastros) | `Map`/`Set`, `record`, comparação de bases | A fazer |

## Tecnologias

- Java 21
- Maven
- JUnit 5

## Como rodar

O jeito mais rápido é abrir no GitHub Codespaces (botão **Code → Codespaces → Create codespace**). O ambiente já vem com Java 21 e Maven.

Rodar os testes de todos os desafios:

```bash
mvn test
```

Rodar um desafio específico:

```bash
cd 01-busca-de-nomes
mvn -q compile exec:java
```
