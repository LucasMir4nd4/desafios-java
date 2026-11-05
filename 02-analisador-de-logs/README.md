# Analisador de Logs de Servidor

Lê logs de três servidores e gera um resumo com contagem por nível, erros mais frequentes e linhas fora do padrão.

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

_(ex.: a busca diferencia maiúsculas? por quê?)_
