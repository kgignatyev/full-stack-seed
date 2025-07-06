#!/usr/bin/env bash
set -e
set -x
# this assumes we manage the API version in the OpenAPI definition file,
# but it is better to put this in own repository and the git tag will define the API version
# tagging can trigger CI/CD pipeline to build and publish all the versioned artifacts
API_RELEASE=$(yq eval '.info.version' definitions/fss-svc.v1.openapi.yaml)
mkdir -p "./out"
rm -rf ./out/*
docker run --rm  \
   -v $PWD/definitions:/spec \
   -v $PWD/out:/out \
   redocly/cli:1.34.4 \
    build-docs /spec/fss-svc.v1.openapi.yaml -o /out/fss-svc-api-${API_RELEASE}-docs.html
cp definitions/fss-svc.v1.openapi.yaml out/fss-svc-api-${API_RELEASE}.yaml
