#!/bin/bash
# ==============================================================
# K6 Benchmark Runner (CSV Only, p90 & p95)
# Runs loadtest.js at multiple concurrency levels (VUs)
# and writes summary metrics (RPS, p90, p95) into a CSV file.
# ==============================================================
# Usage:
#   ./run-bench-k6.sh http://localhost:8080/api/users/1 mvc
# ==============================================================

URL=$1
APP_NAME=$2

if [ -z "$URL" ] || [ -z "$APP_NAME" ]; then
  echo "Usage: ./run-bench-k6.sh <url> <app_name>"
  exit 1
fi

TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
RESULT_DIR="results/${APP_NAME}_${TIMESTAMP}"
mkdir -p "$RESULT_DIR"

CSV_FILE="${RESULT_DIR}/${APP_NAME}_summary.csv"
echo "vus,requests_per_sec,p90_latency_ms,p95_latency_ms" > "$CSV_FILE"

CONCURRENCY_LEVELS=(50 200 500 1000)
DURATION="30s"

for VU in "${CONCURRENCY_LEVELS[@]}"; do
  echo "==============================================="
  echo "Running K6 for ${APP_NAME} with ${VU} virtual users..."

  TEMP_FILE="${RESULT_DIR}/${APP_NAME}_vu${VU}.json"

  # Run k6 test and export summary JSON (for parsing)
  k6 run loadtest.js \
    --vus "$VU" \
    --duration "$DURATION" \
    -e TARGET_URL="$URL" \
    --summary-export "$TEMP_FILE" > /dev/null

  # Extract metrics
  RPS=$(jq '.metrics.http_reqs.rate' "$TEMP_FILE")
  P90=$(jq '.metrics.http_req_duration["p(90)"]' "$TEMP_FILE")
  P95=$(jq '.metrics.http_req_duration["p(95)"]' "$TEMP_FILE")

  # Handle null or missing values
  if [[ "$P90" == "null" || -z "$P90" ]]; then P90=0; fi
  if [[ "$P95" == "null" || -z "$P95" ]]; then P95=0; fi
  if [[ "$RPS" == "null" || -z "$RPS" ]]; then RPS=0; fi

  echo "→ RPS=${RPS}, p90=${P90}ms, p95=${P95}ms"
  echo "${VU},${RPS},${P90},${P95}" >> "$CSV_FILE"
done

echo "==============================================="
echo "✅ Benchmark complete for ${APP_NAME}"
echo "Results saved to:"
echo " - Summary CSV: ${CSV_FILE}"
echo "==============================================="