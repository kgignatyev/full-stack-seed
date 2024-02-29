#!/usr/bin/env bash
API_RELEASE=$(yq eval '.info.version' definitions/fss-svc.v1.openapi.yaml)
mkdir -p "./out"
rm -rf ./out/*
docker run --rm  \
   -v $PWD/definitions:/spec \
   -v $PWD/out:/out \
   redocly/cli \
    build-docs /spec/fss-svc.v1.openapi.yaml -o /out/fss-svc-api-docs-${API_RELEASE}.html
cp definitions/fss-svc.v1.openapi.yaml out/fss-svc-api-${API_RELEASE}.yaml
