#!/bin/bash

set -e

cd "$(dirname "$0")"

source .env

npm run dev
mvn clean package

docker compose build room
docker network create $NETWORK || true
docker compose up room -d