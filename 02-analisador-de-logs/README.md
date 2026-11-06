# Analisador de Logs de Servidor

Lê logs de três servidores e gera um resumo com contagem por nível, erros mais frequentes e linhas fora do padrão.

## Enunciado

### Contexto

A equipe de infraestrutura guarda os logs de três servidores em arquivos de texto. Quando algo dá errado, alguém abre cada arquivo e conta os erros na mão. Você vai criar uma aplicação que lê os logs e gera um resumo da saúde de cada servidor.

### Formato de cada linha do log

```
2026-11-05 08:15:32 [ERROR] Falha ao conectar no banco de dados
2026-11-05 08:15:40 [INFO] Requisição GET /clientes concluída
2026-11-05 08:16:02 [WARN] Tempo de resposta acima de 2s
```

Arquivos: `servidor-app.log`, `servidor-banco.log`, `servidor-email.log`.

### Requisitos funcionais

1. **RF01 — Leitura:** ler os três arquivos de log, linha por linha.
2. **RF02 — Contagem por nível:** contar quantas linhas de cada nível (`INFO`, `WARN`, `ERROR`) existem em cada arquivo.
3. **RF03 — Erros mais frequentes:** listar as 3 mensagens de `ERROR` que mais se repetem em cada arquivo, com a quantidade.
4. **RF04 — Linhas fora do padrão:** linhas que não seguem o formato (sem nível, data quebrada, vazias) não podem quebrar o programa. Conte-as e mostre o número de cada uma.
5. **RF05 — Primeiro e último erro:** mostrar o horário do primeiro e do último `ERROR` de cada arquivo.
6. **RF06 — Resumo geral:** somar os níveis de todos os servidores e informar qual servidor teve mais erros.
7. **RF07 — Tratamento de erros:** arquivo inexistente ou ilegível gera mensagem clara, e os outros continuam sendo processados.

### Exemplo de saída

```
========================================
        ANALISADOR DE LOGS
========================================

[servidor-app.log]
INFO: 120 | WARN: 15 | ERROR: 8
Linhas fora do padrão: 2 (linhas 14, 87)
Primeiro erro: 08:15:32 | Último erro: 17:42:10

Erros mais frequentes:
  5x Falha ao conectar no banco de dados
  2x Timeout na API de pagamentos
  1x Arquivo de configuração não encontrado

----------------------------------------
RESUMO GERAL
----------------------------------------
INFO: 310 | WARN: 41 | ERROR: 19
Servidor com mais erros: servidor-app.log (8)

Tempo total: 35 ms
========================================
```

Os números acima são ilustrativos. O resultado esperado para os arquivos deste repositório está em [GABARITO.md](GABARITO.md).

### Requisitos técnicos

- Java 21 ou superior, aplicação de console, sem banco de dados, API ou interface gráfica.
- Classes e métodos com responsabilidades bem definidas, sem duplicação desnecessária.
- Um arquivo com problema não pode derrubar o processamento dos demais.

### Diferenciais

- Filtrar por intervalo de horário informado pelo usuário (ex.: das 08:00 às 12:00).
- Exportar o resumo para um arquivo `relatorio-logs.txt`.
- Processar os arquivos em paralelo.
- Testes que validem o parsing de uma linha válida e de uma inválida.

**Conceitos praticados:** `String.split` ou regex, `Map` para contagem (`merge`), ordenação de `Map` por valor, `LocalTime`.

## Como executar

```bash
cd 02-analisador-de-logs
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
