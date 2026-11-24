#!/usr/bin/env bash
# Sobe todos os microsserviços na ordem certa.
#   ./run.sh              -> 1 instância de cada
#   WORKERS=2 ./run.sh    -> 2 instâncias do hr-worker (8001 e 8002) para testar balanceamento
#   SKIP_BUILD=1 ./run.sh -> não recompila (mais rápido se você não mexeu no código)
# Logs em ./logs/<servico>.log   |   Para parar: ./stop.sh
set -euo pipefail
cd "$(dirname "$0")"

WORKERS="${WORKERS:-1}"
mkdir -p logs
: > .pids

if [ -z "${SKIP_BUILD:-}" ]; then
  echo "==> Compilando (mvn package)..."
  mvn -q -DskipTests package
fi

wait_up() { # nome porta
  printf "    aguardando %s na porta %s " "$1" "$2"
  for _ in $(seq 1 90); do
    if curl -sf "http://localhost:$2/actuator/health" >/dev/null 2>&1; then echo " OK"; return 0; fi
    printf "."; sleep 1
  done
  echo " FALHOU (veja logs/$1.log)"; return 1
}

start() { # nome jar porta [log]
  local log="${4:-$1}"
  echo "==> Subindo $1 (porta $3)"
  PORT="$3" nohup java -jar "$2/target/$2.jar" > "logs/$log.log" 2>&1 &
  echo $! >> .pids
}

start hr-eureka-server hr-eureka-server 8761
wait_up hr-eureka-server 8761

for i in $(seq 1 "$WORKERS"); do
  port=$((8000 + i))
  start hr-worker hr-worker "$port" "hr-worker-$port"
done
start hr-payroll hr-payroll 8101
start hr-api-gateway hr-api-gateway 8765

for i in $(seq 1 "$WORKERS"); do wait_up "hr-worker-$((8000 + i))" $((8000 + i)); done
wait_up hr-payroll 8101
wait_up hr-api-gateway 8765

cat <<MSG

Tudo no ar (o gateway leva uns 5-10s para enxergar os serviços no Eureka).

  Front de testes ...... http://localhost:8765
  Swagger (contrato) ... http://localhost:8765/swagger-ui.html
  Eureka ............... http://localhost:8761
  H2 console ........... http://localhost:8001/h2-console  (JDBC URL: jdbc:h2:mem:hrworker, user: sa)

Logs: tail -f logs/*.log     Parar: ./stop.sh
MSG
