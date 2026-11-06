# Sistema de Busca de Nomes

Pesquisa um nome em vários arquivos de texto, mostra as linhas encontradas, a contagem por arquivo e o tempo total da busca.

## Enunciado

### Contexto

Imagine que você foi contratado como estagiário de desenvolvimento em uma empresa responsável pelo processamento de grandes volumes de informações. Essa empresa possui diversos arquivos de texto contendo registros de pessoas, e periodicamente os funcionários precisam localizar determinados nomes nesses arquivos.

Hoje esse processo é manual: cada arquivo é aberto e pesquisado individualmente. Para automatizar a atividade, a equipe pediu uma aplicação Java capaz de pesquisar um nome em diferentes arquivos de texto.

### Objetivo

Desenvolver uma aplicação Java que pesquise um nome em três arquivos de texto, mostre todas as linhas que contêm o nome, a quantidade de ocorrências em cada arquivo e o tempo total da pesquisa.

### Requisitos funcionais

1. **RF01 — Nome pesquisado:** o nome pode ser definido no código ou pedido ao usuário pelo console.
2. **RF02 — Leitura dos arquivos:** ler `signatarios-fev-2002.txt`, `signatarios-fev-2016.txt` e `signatarios-out-2014.txt`.
3. **RF03 — Ocorrências:** para cada linha que contém o nome, mostrar o nome do arquivo, o número da linha e o conteúdo completo da linha.
4. **RF04 — Contagem:** ao terminar cada arquivo, informar quantas linhas continham o nome.
5. **RF05 — Processamento independente:** o processamento de um arquivo não pode depender do resultado dos outros.
6. **RF06 — Tempo de execução:** mostrar o tempo total, do início do processamento até a conclusão das buscas nos três arquivos.
7. **RF07 — Tratamento de erros:** se um arquivo não existir ou não puder ser lido, mostrar uma mensagem com o nome do arquivo e o motivo.

### Exemplo de funcionamento

```
========================================
       SISTEMA DE BUSCA DE NOMES
========================================

Nome pesquisado: Dave

Iniciando pesquisa nos arquivos...

[signatarios-fev-2002.txt]
Linha 15: Dave Smith

[signatarios-out-2014.txt]
Linha 82: Dave Johnson

----------------------------------------
RESUMO DA PESQUISA
----------------------------------------

Arquivo: signatarios-fev-2002.txt
Ocorrências: 1

Arquivo: signatarios-fev-2016.txt
Ocorrências: 0

Arquivo: signatarios-out-2014.txt
Ocorrências: 1

----------------------------------------
Total de ocorrências: 2

Todas as buscas foram finalizadas.

Tempo total: 42 ms
========================================
```

Os dados e o tempo acima são ilustrativos.

### Requisitos técnicos

- Java 21 ou superior, aplicação de console.
- Usar os recursos da linguagem Java para leitura de arquivos.
- Classes e métodos com responsabilidades bem definidas, sem duplicação desnecessária.
- Todos os arquivos processados antes da apresentação do resultado final.
- Tratamento de exceções para erros de leitura.
- Sem interface gráfica, API REST ou banco de dados.

### Diferenciais

- Pesquisar diferentes nomes sem alterar o código-fonte.
- Permitir adicionar novos arquivos à pesquisa.
- Organizar a solução com orientação a objetos.
- Testes automatizados.
- Concorrência para executar as buscas.
- Tempo individual de processamento de cada arquivo.

### Entrega

Código-fonte completo, arquivos de texto usados nos testes, instruções para executar e um exemplo de saída.

## Como executar

```bash
cd 01-busca-de-nomes
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
