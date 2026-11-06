# Comparador de Cadastros

Compara a base antiga e a nova de clientes e lista os novos, removidos, alterados e duplicados.

## Enunciado

### Contexto

A empresa migrou o cadastro de clientes para um sistema novo e precisa conferir se nada se perdeu. Existem dois arquivos: a exportação do sistema antigo e a do sistema novo. Você vai criar uma aplicação que compara as duas bases e mostra o que mudou.

### Formato dos arquivos

Arquivos `cadastro-antigo.txt` e `cadastro-novo.txt`, com cabeçalho e separador `;`:

```csv
id;nome;email;cidade
1001;Ana Souza;ana@email.com;Barueri
1002;Bruno Lima;bruno@email.com;Osasco
1003;Carla Dias;carla@email.com;São Paulo
```

O `id` identifica o cliente nas duas bases.

### Requisitos funcionais

1. **RF01 — Leitura:** carregar os dois arquivos em memória, ignorando o cabeçalho.
2. **RF02 — Clientes novos:** listar os `id`s que existem só no cadastro novo.
3. **RF03 — Clientes removidos:** listar os `id`s que existem só no cadastro antigo.
4. **RF04 — Clientes alterados:** para `id`s presentes nas duas bases, mostrar campo por campo o que mudou (valor antigo → valor novo).
5. **RF05 — Duplicidades:** se o mesmo `id` aparecer mais de uma vez no mesmo arquivo, avisar com o número das linhas.
6. **RF06 — Comparação justa:** diferenças só de espaços extras, ou de maiúsculas e minúsculas no e-mail, não contam como alteração.
7. **RF07 — Tratamento de erros:** arquivo ausente ou linha malformada gera mensagem clara, sem interromper a comparação.

### Exemplo de saída

```
========================================
       COMPARADOR DE CADASTROS
========================================

NOVOS (2)
  1045 - Marcos Pereira
  1046 - Juliana Alves

REMOVIDOS (1)
  1003 - Carla Dias

ALTERADOS (2)
  1001 - Ana Souza
    cidade: Barueri → Carapicuíba
  1002 - Bruno Lima
    email: bruno@email.com → bruno.lima@email.com

AVISOS
  [cadastro-novo.txt] id 1010 duplicado nas linhas 12 e 30

----------------------------------------
Iguais: 37 | Novos: 2 | Removidos: 1 | Alterados: 2
Tempo total: 18 ms
========================================
```

Os números acima são ilustrativos.

### Requisitos técnicos

- Java 21 ou superior, aplicação de console, sem banco de dados, API ou interface gráfica.
- Classes e métodos com responsabilidades bem definidas, sem duplicação desnecessária.
- Uma linha ou arquivo com problema não pode derrubar a comparação.

### Diferenciais

- Exportar o resultado em `diferencas.csv` com colunas `id;tipo;campo;antes;depois`.
- Permitir escolher pelo console qual campo é a chave (por exemplo, e-mail em vez de `id`).
- Medir o desempenho com 100 mil linhas geradas por um script seu.
- Testes para cada tipo de diferença (novo, removido, alterado, igual).

**Conceitos praticados:** `Map<String, Cliente>` para busca por chave, `record` com comparação de campos, `Set` para diferença entre conjuntos (`removeAll`, `retainAll`), normalização de texto com `strip()` e `equalsIgnoreCase`.

## Como executar

```bash
cd 04-comparador-de-cadastros
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
