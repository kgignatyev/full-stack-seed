#!/usr/bin/env bash

API_VERSION=v1
MODULE=fssSvc



GENERATOR=typescript-angular
OUTPUT_DIR=src/app/generated/api_client
LIB_NAME=fss-svc-api-angular2-g1

mkdir -p $OUTPUT_DIR

#rm -fR $OUTPUT_DIR/*


#lets check if we have unzipped code of definitions, then we are on DevOps server
#it will get a tagged version from git
UNTARRED_DEFINITIONS=temp/definitions
LOCAL_DEFINITIONS=../fss-api/definitions
export DEFINITIONS="undefined"

if test -d "$UNTARRED_DEFINITIONS"; then
  export DEFINITIONS=$( realpath $UNTARRED_DEFINITIONS )
else
  export DEFINITIONS="$( realpath $LOCAL_DEFINITIONS )"
fi

PREFIX=fssSvc


USER=$(id -u)

docker run --rm --user=${USER}:${USER} \
  -v ${PWD}:/local \
  -v ${DEFINITIONS}:/local/definitions \
  openapitools/openapi-generator-cli:${OPENAPI_GENERATOR_DOCKER_TAG} \
  generate \
  -i local/definitions/fss-svc.${API_VERSION}.openapi.yaml \
  --additional-properties=apiModulePrefix=${PREFIX} \
  --additional-properties=configurationPrefix=${PREFIX} \
  -g ${GENERATOR} \
  -o /local/${OUTPUT_DIR}

