#!/usr/bin/env bash
MODULE=fss
API_VERSION=v1

GENERATOR=kotlin
OUTPUT_DIR="./out/client"

GROUP_ID=com.kgignatyev.badminton_club.api
LIB_NAME=badminton-club-api

mkdir -p "out"
rm -fR ./out/*


USER=$(id -u)

echo "using openapi definitions located at: $DEFINITIONS"
docker run --rm  --user=$USER \
  -v ${PWD}:/local \
  -v ${DEFINITIONS}:/local/definitions \
  openapitools/openapi-generator-cli:v7.0.0 \
  author template \
  -g ${GENERATOR} \
  -o /local/${OUTPUT_DIR}

