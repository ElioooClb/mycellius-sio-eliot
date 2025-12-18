#!/usr/bin/env bash
set -euo pipefail
echo "== Vérification structure du dépôt =="
test -f README.md
test -d docs
test -d infra
test -d .github/workflows
test -f docs/01_cadrage.md
test -f docs/02_architecture.md || true
test -f docs/03_securite_baseline.md || true
test -d infra/static
test -f infra/static/index.html
echo "OK: structure minimale présente."