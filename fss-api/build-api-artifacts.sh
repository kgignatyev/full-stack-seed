#!/usr/bin/env bash

java_version=$(java -version 2>&1 | awk -F[\".] '/version/ {print $2}')

if [[ "$java_version" != "17" ]]; then
  echo "Java 17 is required, but found version: $java_version"
  echo "Please install Java 17 and try again. Best to use SDKMAN (https://sdkman.io/) to install this and manage multiple JDK-s."
  exit 1
fi

export API_RELEASE=$(yq  '.info.version' definitions/fss-svc.v1.openapi.yaml)
set -e
set -x

export OPENAPI_GENERATOR_DOCKER_TAG=v7.14.0

scope=${1:-all}

if [ "$scope" == "all" ] || [ "$scope" == "docs" ]; then
  ./build-docs.sh
fi

if [ "$scope" == "all" ] || [ "$scope" == "ui" ]; then
cd ../fss-ui
./generate-api-client.sh
fi


if [ "$scope" == "all" ] || [ "$scope" == "server" ]; then
cd ../fss-api-server-stubs
./generate-server-stubs.sh
fi

if [ "$scope" == "all" ] || [ "$scope" == "jvm-client" ]; then
cd ../fss-api-client-jvm-impl
./generate-and-install-locally.sh
fi



