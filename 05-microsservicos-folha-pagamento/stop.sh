#!/usr/bin/env bash
# Para todos os serviços iniciados pelo run.sh
cd "$(dirname "$0")"
if [ -f .pids ]; then
  while read -r pid; do kill "$pid" 2>/dev/null && echo "parado PID $pid"; done < .pids
  rm -f .pids
fi
# Garante que nada ficou preso nas portas (caso tenha subido pela IDE, por exemplo)
for port in 8761 8765 8101 8001 8002 8003; do
  pid=$(lsof -ti tcp:"$port" 2>/dev/null || true)
  [ -n "$pid" ] && kill $pid 2>/dev/null && echo "liberada porta $port"
done
echo "Pronto."
