#!/usr/bin/env bash

export API_RELEASE=$(yq  '.info.version' definitions/fss-svc.v1.openapi.yaml)
set -e
set -x

./build-docs.sh

cd ../fss-ui
./generate-api-client.sh

cd ../fss-api-server-stubs
./generate-server-stubs.sh





