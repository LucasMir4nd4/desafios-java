# Gabarito: Consolidador de Vendas

Resultado esperado para os arquivos em `src/main/resources/arquivos`.

## Regras usadas

- Data no formato `AAAA-MM-DD` e precisa existir (30/02 e mês 13 são inválidos).
- Quantidade inteira e maior ou igual a 1 (`2.5`, `0` e `-2` são inválidos).
- Preço com ponto decimal (`29,90` é inválido) e não negativo.
- Linha vazia conta como linha inválida (número de colunas errado).
- Um `;` sobrando no fim da linha gera uma 5ª coluna vazia, então é inválida.
  Atenção: `String.split(";")` descarta colunas vazias do fim; use `split(";", -1)`.
- Ticket médio arredondado com `RoundingMode.HALF_UP` para 2 casas.
- Números de linha contam o cabeçalho como linha 1.

## vendas-centro.csv (40 linhas de dados)

- **Vendas válidas:** 35
- **Faturamento:** R$ 12.326,96
- **Ticket médio:** R$ 352,20

| Linha | Conteúdo | Motivo |
| --- | --- | --- |
| 4 | `2026-10-02;Monitor 24";abc;899.90` | quantidade inválida ("abc") |
| 10 | `Mouse sem fio;2;79.90` | número de colunas inválido (3 em vez de 4) |
| 16 | `2026-10-09;Hub USB-C;0;149.50` | quantidade menor que 1 (0) |
| 23 | `2026-10-13;Cabo HDMI 2m;3;29,90` | preço inválido ("29,90") |
| 31 | _(vazia)_ | número de colunas inválido (1 em vez de 4) |

## vendas-barueri.csv (34 linhas de dados)

- **Vendas válidas:** 30
- **Faturamento:** R$ 15.214,11
- **Ticket médio:** R$ 507,14

| Linha | Conteúdo | Motivo |
| --- | --- | --- |
| 6 | `2026-02-30;SSD 480GB;1;229.99` | data inválida ("2026-02-30") |
| 13 | `2026-10-08;Headset USB;2.5;189.90` | quantidade inválida ("2.5") |
| 19 | `2026-10-11;Webcam Full HD;1;;extra` | número de colunas inválido (5 em vez de 4) |
| 27 | `2026-10-16;Pendrive 64GB;1;abc` | preço inválido ("abc") |

## vendas-osasco.csv (38 linhas de dados)

- **Vendas válidas:** 33
- **Faturamento:** R$ 15.179,31
- **Ticket médio:** R$ 459,98

| Linha | Conteúdo | Motivo |
| --- | --- | --- |
| 5 | `01/10/2026;Teclado mecânico;1;349.00` | data inválida ("01/10/2026") |
| 12 | `2026-10-07;Cabo HDMI 2m;1;-15.00` | preço negativo (-15.00) |
| 18 | `2026-10-10;Mouse sem fio;-2;79.90` | quantidade menor que 1 (-2) |
| 25 | `2026-13-05;Hub USB-C;1;149.50` | data inválida ("2026-13-05") |
| 32 | `2026-10-21;Pendrive 64GB;1;39.90;` | número de colunas inválido (5 em vez de 4) |

## Resumo geral

- **Faturamento total:** R$ 42.720,38
- **Produto campeão:** Pendrive 64GB (47 unidades)
- **Linhas inválidas:** 14

### Quantidade vendida por produto (três filiais)

| Produto | Unidades |
| --- | --- |
| Pendrive 64GB | 47 |
| Mouse sem fio | 45 |
| SSD 480GB | 32 |
| Teclado mecânico | 26 |
| Cabo HDMI 2m | 18 |
| Webcam Full HD | 17 |
| Headset USB | 16 |
| Hub USB-C | 13 |
| Cadeira gamer | 7 |
| Monitor 24" | 2 |

## Como testar o RF07

Renomeie um dos CSVs (ou crie um arquivo vazio com o mesmo nome) e rode de novo.
O programa deve avisar o problema daquela filial e continuar com as outras duas.
