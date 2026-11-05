# Gabarito: Analisador de Logs

Resultado esperado para os arquivos em `src/main/resources/arquivos`.

## Regra de linha válida

Uma linha só é válida se seguir exatamente o formato:

```
AAAA-MM-DD HH:MM:SS [NIVEL] mensagem
```

- A data e a hora precisam existir de verdade (mês 13 é inválido).
- O nível precisa ser `INFO`, `WARN` ou `ERROR`, entre colchetes.
- A mensagem não pode ser vazia.

Qualquer outra coisa conta como **linha fora do padrão**.

## servidor-app.log (36 linhas)

| Nível | Quantidade |
| --- | --- |
| INFO | 16 |
| WARN | 5 |
| ERROR | 10 |

- **Linhas fora do padrão:** 5 (linhas 9, 15, 16, 23, 28)
- **Primeiro erro:** 08:15:32 | **Último erro:** 17:42:10
- **Erros mais frequentes:**
    - 4x Falha ao conectar no banco de dados
    - 3x Timeout na API de pagamentos
    - 2x NullPointerException em PedidoService

| Linha | Por que é inválida |
| --- | --- |
| 9 | Linha vazia |
| 15 | Stack trace, sem data nem nível |
| 16 | Continuação do stack trace (`at br.com.loja...`) |
| 23 | Nível `DEBUG` não é aceito |
| 28 | Nível `ERROR` sem colchetes |

## servidor-banco.log (23 linhas)

| Nível | Quantidade |
| --- | --- |
| INFO | 10 |
| WARN | 4 |
| ERROR | 6 |

- **Linhas fora do padrão:** 3 (linhas 9, 14, 19)
- **Primeiro erro:** 08:15:31 | **Último erro:** 16:48:00
- **Erros mais frequentes:**
    - 4x Limite de conexões excedido
    - 2x Deadlock detectado na tabela pedidos

| Linha | Por que é inválida |
| --- | --- |
| 9 | Hora sem segundos (`09:30`) |
| 14 | Data inexistente (mês 13) |
| 19 | Linha só com espaços |

Só existem 2 mensagens de erro diferentes: o top 3 deve mostrar apenas essas duas, sem quebrar.

## servidor-email.log (15 linhas)

| Nível | Quantidade |
| --- | --- |
| INFO | 12 |
| WARN | 2 |
| ERROR | 0 |

- **Linhas fora do padrão:** 1 (linha 7, sem data e hora)
- **Primeiro/último erro:** nenhum erro registrado
- **Erros mais frequentes:** nenhum

Este arquivo testa o caso sem nenhum `ERROR`: o programa não pode lançar exceção ao buscar o primeiro e o último erro.

## Resumo geral

- INFO: 38 | WARN: 11 | ERROR: 16
- Servidor com mais erros: servidor-app.log (10)

## Teste do RF07

Renomeie `servidor-banco.log` e rode de novo. Esperado:

- mensagem de erro para `servidor-banco.log`;
- os outros dois arquivos processados normalmente;
- resumo geral: INFO 28 | WARN 7 | ERROR 10.
