# Consolidador de Vendas por Filial

Consolida CSVs de vendas de três filiais, valida as linhas e calcula faturamento, ticket médio e produto campeão.

## Enunciado

### Contexto

Uma rede de lojas tem três filiais, e cada uma exporta suas vendas do mês em um arquivo CSV. O financeiro junta tudo numa planilha manualmente e sempre encontra linhas com erro de digitação. Você vai criar uma aplicação que consolida as vendas e aponta os registros inválidos.

### Formato do CSV

A primeira linha é o cabeçalho e o separador é `;`:

```csv
data;produto;quantidade;preco_unitario
2026-10-01;Mouse sem fio;2;79.90
2026-10-01;Teclado mecânico;1;349.00
2026-10-02;Monitor 24";abc;899.90
```

Arquivos: `vendas-centro.csv`, `vendas-barueri.csv`, `vendas-osasco.csv`.

### Requisitos funcionais

1. **RF01 — Leitura:** ler os três CSVs, ignorando a linha de cabeçalho.
2. **RF02 — Validação:** uma linha é inválida se tiver número de colunas errado, data inválida, quantidade não inteira ou menor que 1, ou preço não numérico ou negativo. Linhas inválidas não entram nos cálculos.
3. **RF03 — Relatório de inválidas:** para cada linha inválida, mostrar arquivo, número da linha e o motivo.
4. **RF04 — Faturamento por filial:** soma de `quantidade × preco_unitario` das linhas válidas de cada arquivo.
5. **RF05 — Ticket médio:** faturamento da filial dividido pelo número de vendas válidas.
6. **RF06 — Produto campeão:** produto com maior quantidade vendida somando as três filiais.
7. **RF07 — Tratamento de erros:** arquivo inexistente ou vazio gera mensagem clara sem interromper os demais.

**Regra importante:** valores em dinheiro devem usar `BigDecimal`, nunca `double`, e ser exibidos com duas casas decimais no formato brasileiro (R$ 1.234,56).

### Exemplo de saída

```
========================================
      CONSOLIDADOR DE VENDAS
========================================

LINHAS INVÁLIDAS
[vendas-centro.csv] Linha 4: quantidade inválida ("abc")
[vendas-osasco.csv] Linha 11: preço negativo (-15.00)

----------------------------------------
FATURAMENTO POR FILIAL
----------------------------------------
Filial: vendas-centro.csv
Vendas válidas: 42 | Faturamento: R$ 18.430,50 | Ticket médio: R$ 438,82

Filial: vendas-barueri.csv
Vendas válidas: 35 | Faturamento: R$ 12.990,00 | Ticket médio: R$ 371,14

----------------------------------------
Faturamento total: R$ 45.210,30
Produto campeão: Mouse sem fio (64 unidades)
Linhas inválidas: 3
========================================
```

Os números acima são ilustrativos.

### Requisitos técnicos

- Java 21 ou superior, aplicação de console, sem banco de dados, API ou interface gráfica.
- Classes e métodos com responsabilidades bem definidas, sem duplicação desnecessária.
- Um arquivo com problema não pode derrubar o processamento dos demais.

### Diferenciais

- Filtrar por período (data inicial e final) informado pelo usuário.
- Gerar um CSV consolidado `vendas-consolidadas.csv` com uma coluna `filial`.
- Ranking dos 5 produtos que mais faturaram.
- Testes para cada regra de validação do RF02.

**Conceitos praticados:** `BigDecimal` e `RoundingMode`, `LocalDate.parse` com tratamento de `DateTimeParseException`, `NumberFormat` com `Locale`, `Collectors.groupingBy`, validação separada da leitura.

## Como executar

```bash
cd 03-consolidador-de-vendas
mvn -q compile exec:java
```

Os arquivos de entrada ficam em `src/main/resources/arquivos`. Execute sempre de dentro desta pasta para o caminho relativo funcionar.

## Testes

```bash
mvn test
```

## Exemplo de saída

_(cole aqui a saída real da sua aplicação)_

## Decisões

_(explique aqui as escolhas que o enunciado deixou em aberto)_
