#!/bin/bash

set -e

cd "$(dirname "$0")"

source .env

docker compose -v down room
docker network remove $NETWORK || true