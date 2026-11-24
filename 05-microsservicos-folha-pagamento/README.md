# 05 · Microsserviços — Folha de Pagamento

> **De:** Tech Lead
> **Para:** Lucas
> **Assunto:** [HR-105] Primeira versão do sistema de folha em microsserviços

Lucas, a infraestrutura do novo sistema de RH já está montada: service registry, gateway, Swagger e um painel de testes. Falta o principal, que é a regra de negócio dos dois serviços. Está tudo descrito abaixo. O contrato da API está no Swagger e **é ele que vale**: se a sua resposta não bater com o contrato, o front quebra.

Qualquer dúvida, me chama antes de inventar. Bom trabalho.

---

## 1. Arquitetura

Baseada no curso de microsserviços (slide 3, "Visão geral do sistema"), nesta primeira entrega **só com os serviços marcados com ✅**:

```
                    navegador (front de testes / Swagger)
                                  │
                                  ▼
                     ┌──────────────────────────┐
                     │  hr-api-gateway  :8765    │ ✅ pronto
                     └─────────────┬────────────┘
                 /hr-worker/**     │     /hr-payroll/**
             ┌─────────────────────┴──────────────────────┐
             ▼                                            ▼
   ┌──────────────────┐        OpenFeign        ┌──────────────────┐
   │ hr-worker  :8001 │ ◄────────────────────── │ hr-payroll :8101 │
   │ (+ :8002, :8003) │   "lb://hr-worker"      │  sem banco       │
   │  H2 em memória   │                         └──────────────────┘
   └──────────────────┘   🔧 você implementa  🔧
             │                                            │
             └──────────── registram-se no ───────────────┘
                                  ▼
                     ┌──────────────────────────┐
                     │ hr-eureka-server :8761    │ ✅ pronto
                     └──────────────────────────┘
```

Ficam para as próximas entregas: `hr-user`, `hr-oauth` (autenticação JWT, slides 5–7) e `config server`.

### Diferenças em relação ao curso

O curso usa Java 11 e Spring Cloud de 2020. Aqui estamos em **Java 21 + Spring Boot 3.5 + Spring Cloud 2025.0**, então algumas peças mudaram de nome:

| No curso | Aqui | Por quê |
|---|---|---|
| Zuul | **Spring Cloud Gateway** | Zuul saiu do Spring Cloud |
| Ribbon | **Spring Cloud LoadBalancer** | Ribbon foi descontinuado; já vem com o eureka-client |
| Hystrix | **Resilience4j** | Hystrix foi descontinuado |
| `Double` para dinheiro | **`BigDecimal`** | `double` erra centavos (você já viu isso no desafio 03) |

O resto (Eureka, Feign, `@EnableEurekaServer`, rotas `lb://`) funciona igual ao curso.

---

## 2. O que já está pronto e o que é seu

| Módulo | Status | O que tem |
|---|---|---|
| `hr-eureka-server` | ✅ Pronto | Servidor Eureka configurado |
| `hr-api-gateway` | ✅ Pronto | Rotas, front de testes (`static/index.html`), Swagger agregado, contratos (`static/contrato/*.yaml`) |
| `hr-worker` | 🔧 **Seu** | `pom.xml`, `application.yml`, `data.sql` (8 funcionários mock) e a classe `Application`. Pacotes vazios: `model`, `repository`, `service`, `controller`, `dto`, `exception` |
| `hr-payroll` | 🔧 **Seu** | `pom.xml`, `application.yml` e a classe `Application` (Feign já habilitado). Pacotes vazios: `client`, `dto`, `service`, `controller`, `exception` |

As dependências já estão todas nos `pom.xml`. **Você não precisa adicionar nenhuma** para fazer as tasks 01 a 06.

**Não mexa** no gateway nem no Eureka. Se achar que precisa, me pergunte antes.

---

## 3. Tasks

Faça na ordem. Cada task tem critérios de aceite: só considere pronta quando **todos** passarem no painel de testes ou no Swagger.

### TASK-01 · Listar funcionários (`hr-worker`)

Criar a entidade de funcionário, o repositório e o endpoint de listagem.

- Tabela `tb_worker`, colunas `id` (gerado pelo banco), `name` e `daily_income`.
- `dailyIncome` é dinheiro: `BigDecimal`.
- O controller **não** fala direto com o repository. Passe por uma camada de service.
- Não devolva a entidade JPA na resposta. Use um DTO.

**Aceite**
- [ ] Ao subir o `hr-worker`, o log **não** mostra mais erro do `data.sql`.
- [ ] `GET /hr-worker/workers` devolve os 8 funcionários do `data.sql`.
- [ ] O JSON tem exatamente os campos `id`, `name` e `dailyIncome`.

### TASK-02 · Buscar por id com erro padronizado (`hr-worker`)

- `GET /workers/{id}` devolve um funcionário.
- Id inexistente → **404** com o corpo `StandardError` do contrato (`timestamp`, `status`, `error`, `message`, `path`).
- O tratamento de erro fica centralizado em um lugar só, não espalhado em `try/catch` pelo controller.

**Aceite**
- [ ] `GET /hr-worker/workers/1` → 200 com Ana Souza.
- [ ] `GET /hr-worker/workers/999` → 404 com `message` "Funcionário 999 não encontrado".
- [ ] `GET /hr-worker/workers/abc` → 400 (não pode ser 500).

### TASK-03 · Cadastrar com validação (`hr-worker`)

- `POST /workers` recebe `name` e `dailyIncome` (DTO de entrada separado do de saída).
- Regras: `name` obrigatório, não pode ser só espaços, máx. 100 caracteres; `dailyIncome` obrigatório e maior que zero.
- Sucesso → **201** + header `Location` apontando para o novo recurso.
- Inválido → **400** com o corpo `ValidationError` (lista `errors` com `field` e `message` **de todos** os campos errados, não só do primeiro).

**Aceite**
- [ ] Cadastrar "Isabela Freitas" / 310.40 → 201, e ela aparece no "Listar todos".
- [ ] Nome em branco + diária -10 → 400 com **dois** itens em `errors`.
- [ ] Corpo vazio `{}` → 400 (não 500).

### TASK-04 · Calcular pagamento (`hr-payroll`)

- `GET /payments/{workerId}/days/{days}`.
- O `hr-payroll` **não tem banco**. Ele busca o funcionário no `hr-worker` por um cliente Feign que usa o **nome do serviço no Eureka**, nunca `localhost:8001`.
- `total = dailyIncome × days`, 2 casas decimais, arredondamento `HALF_EVEN`.
- `days` precisa estar entre 1 e 31, senão → 400.
- Se o `hr-worker` responder 404, o `hr-payroll` também responde **404** com `StandardError` (e não 500).

**Aceite**
- [ ] `GET /hr-payroll/payments/1/days/22` → `total` 4400.00.
- [ ] `GET /hr-payroll/payments/6/days/3` → `total` 467.97 (155.99 × 3).
- [ ] `days` = 0, -5 ou 40 → 400.
- [ ] `workerId` 999 → 404 com mensagem clara.
- [ ] Teste unitário do service do payroll com o cliente Feign mockado (Mockito), cobrindo cálculo, arredondamento e funcionário inexistente.

### TASK-05 · Balanceamento de carga (`hr-worker`)

- Toda resposta do `hr-worker` precisa trazer o header **`X-Instance`** com a porta da instância que respondeu.
- Faça isso em **um lugar só**, valendo para todos os endpoints (não repita em cada método do controller).
- Registre no log, a cada requisição, qual porta atendeu.

**Aceite**
- [ ] Rodando `WORKERS=2 ./run.sh`, o card "Balanceamento de carga" com 20 chamadas mostra as duas portas (8001 e 8002) dividindo as requisições.
- [ ] Derrube uma das instâncias (`kill` no PID dela): depois de uns segundos todas as chamadas vão para a outra, sem erro.

### TASK-06 · Tolerância a falhas (bônus)

Slide 13: microsserviços são "desenhados para falhar".

- Com o `hr-worker` fora do ar, o `hr-payroll` deve responder **503** com `StandardError` e uma mensagem amigável, rapidamente, sem travar até o timeout.
- A dependência do Resilience4j já está no `pom.xml` e existe uma flag no `application.yml` do payroll esperando por você.

**Aceite**
- [ ] Pare todas as instâncias do `hr-worker` e calcule um pagamento → 503 com a mensagem do contrato.
- [ ] Suba o `hr-worker` de novo → volta a funcionar sem reiniciar o payroll.

---

## 4. Contrato da API

Com tudo rodando, abra **http://localhost:8765/swagger-ui.html**. No seletor do topo há quatro opções:

- **Contrato – hr-worker / hr-payroll**: o que você **tem que** entregar (eu escrevi). Abre mesmo antes de você implementar.
- **Implementação – hr-worker / hr-payroll**: gerado automaticamente a partir do **seu** código. No fim, os dois têm que bater.

Resumo:

| Método | Via gateway | Task | Sucesso | Erros |
|---|---|---|---|---|
| GET | `/hr-worker/workers` | 01 | 200 `Worker[]` | — |
| GET | `/hr-worker/workers/{id}` | 02 | 200 `Worker` | 400, 404 |
| POST | `/hr-worker/workers` | 03 | 201 `Worker` + `Location` | 400 |
| GET | `/hr-payroll/payments/{workerId}/days/{days}` | 04 | 200 `Payment` | 400, 404, 503 |

O gateway corta o prefixo: `/hr-worker/workers` chega no serviço como `/workers`. **Seus controllers não têm o prefixo.**

---

## 5. Como rodar

Pré-requisitos: Java 21 e Maven (o Codespace já tem os dois).

```bash
cd 05-microsservicos-folha-pagamento
./run.sh                 # compila e sobe tudo
WORKERS=2 ./run.sh       # mesma coisa, com 2 instâncias do hr-worker
SKIP_BUILD=1 ./run.sh    # sobe sem recompilar
./stop.sh                # derruba tudo
tail -f logs/*.log       # acompanha os logs
```

| O quê | URL |
|---|---|
| Painel de testes | http://localhost:8765 |
| Swagger | http://localhost:8765/swagger-ui.html |
| Eureka | http://localhost:8761 |
| H2 console | http://localhost:8001/h2-console (JDBC URL `jdbc:h2:mem:hrworker`, usuário `sa`, sem senha) |

**No Codespaces:** abra a aba **Ports** e clique no globo da porta **8765**. O painel funciona pela URL encaminhada.

**Rodando pela IDE:** suba na ordem Eureka → hr-worker → hr-payroll → gateway. Para uma segunda instância do worker, rode com a variável de ambiente `PORT=8002`.

> Logo depois de subir, o gateway pode responder **503** por uns 5–10 segundos: é o tempo de os serviços aparecerem no Eureka. O card "Serviços registrados" do painel mostra quando estão prontos.

---

## 6. Dados mock

Já estão em `hr-worker/src/main/resources/data.sql`. São carregados toda vez que o serviço sobe (o banco é em memória, então qualquer cadastro some ao reiniciar).

| id | name | dailyIncome |
|---|---|---|
| 1 | Ana Souza | 200.00 |
| 2 | Bruno Lima | 350.50 |
| 3 | Carla Dias | 180.75 |
| 4 | Diego Martins | 420.00 |
| 5 | Eduarda Rocha | 275.30 |
| 6 | Felipe Nogueira | 155.99 |
| 7 | Gabriela Teixeira | 510.00 |
| 8 | Henrique Alves | 230.10 |

**Casos de borda para testar:** id 999 (inexistente), id `abc` (tipo errado), `days` 0 / negativo / 40, POST com `{}`, nome só com espaços, diária negativa, nome com mais de 100 caracteres, e o hr-worker fora do ar.

---

## 7. Por onde começar (sem spoiler)

Pesquise estes nomes quando chegar em cada task. É tudo da biblioteca do Spring.

- **TASK-01:** `@Entity`, `@Table`, `JpaRepository`, `@RestController`, `@GetMapping`, `ResponseEntity`. Pense em por que não expor a entidade direto.
- **TASK-02:** `Optional` do repository, exceção própria de "recurso não encontrado", `@RestControllerAdvice` + `@ExceptionHandler`. Qual exceção o Spring lança quando o `{id}` não é número?
- **TASK-03:** `@Valid`, `@NotBlank`, `@Size`, `@NotNull`, `@Positive`, `MethodArgumentNotValidException`, `ServletUriComponentsBuilder`. Por que `@NotBlank` e não `@NotNull` para o nome?
- **TASK-04:** `@FeignClient(name = ...)` — por que o `name` tem que ser igual ao `spring.application.name` do worker? `FeignException.NotFound`. `BigDecimal.multiply`, `setScale`, `RoundingMode`. Para validar `@PathVariable`, pesquise `@Min`/`@Max` em parâmetros e `HandlerMethodValidationException` (Spring 6.1+).
- **TASK-05:** `@Value("${server.port}")` ou `Environment`. Para "um lugar só": `OncePerRequestFilter` ou `HandlerInterceptor` (compare os dois).
- **TASK-06:** `spring.cloud.openfeign.circuitbreaker.enabled`, atributo `fallback` / `fallbackFactory` do `@FeignClient`, `CallNotPermittedException`.

Siga a estrutura de pacotes que já está criada. Injeção por construtor (`@RequiredArgsConstructor`) e log com `@Slf4j`, nada de `System.out`.

---

## 8. Definição de pronto

- [ ] Todos os critérios de aceite das TASK-01 a 05 passando (06 é bônus).
- [ ] As abas "Contrato" e "Implementação" do Swagger mostram os mesmos endpoints, campos e status.
- [ ] Nenhum `localhost:<porta>` no código do payroll.
- [ ] Nenhum erro 500 em nenhum caso de borda da seção 6.
- [ ] `mvn test` passando dentro de `05-microsservicos-folha-pagamento`.
- [ ] Um commit por task, mensagens no padrão `feat(hr-worker): ...` / `feat(hr-payroll): ...`.
