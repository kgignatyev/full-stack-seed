#!/usr/bin/env bash
set -x
MODULE=fss-svc
API_VERSION=v1

GENERATOR=kotlin
OUTPUT_DIR="./out/client"

GROUP_ID=com.kgignatyev.fss_svc.api
LIB_NAME=fss-api-client

mkdir -p "out"
rm -fR ./out/*


#lets check if we have unzipped code of definitions, then we are in GitLab
UNTARRED_DEFINITIONS=temp/definitions
export DEFINITIONS="undefined"
LOCAL_DEFINITIONS=../fss-api/definitions
export DEFINITIONS="undefined"

if test -d "$UNTARRED_DEFINITIONS"; then
  export DEFINITIONS=$( realpath $UNTARRED_DEFINITIONS )
else
  export DEFINITIONS="$( realpath $LOCAL_DEFINITIONS )"
fi


API_RELEASE=$(yq eval '.info.version' $DEFINITIONS/fss-svc.v1.openapi.yaml)
echo "API_RELEASE: $API_RELEASE"



export GENERATOR_ID=g1

echo "GENERATOR_ID: $GENERATOR_ID"

JAR_NAME=fss-api-client-impl-${GENERATOR_ID}


PACKAGE=fss_client
JAVA_MODEL_PACKAGE=${GROUP_ID}.${PACKAGE}

mkdir -p $OUTPUT_DIR

rm -fR $OUTPUT_DIR/*



#DEBUG_MODELS=,debugModels
MODEL_NAME_SUFFIX="$API_VERSION"
# this can be added to generate models
#  --model-name-suffix ${MODEL_NAME_SUFFIX} \


#IMPORT_MAPPINGS=`cat temp/_import_mappings`
IMPORT_MAPPINGS=""
USER=$(id -u)

echo "using openapi definitions located at: $DEFINITIONS"
docker run --rm  --user=$USER \
  -v ${PWD}:/local \
  -v ${DEFINITIONS}:/local/definitions \
  openapitools/openapi-generator-cli:${OPENAPI_GENERATOR_DOCKER_TAG} \
  generate \
  -i local/definitions/${MODULE}.${API_VERSION}.openapi.yaml \
  $IMPORT_MAPPINGS \
  --additional-properties=packageName=${JAVA_MODEL_PACKAGE}.${API_VERSION}  \
  --additional-properties=useTags=true \
  --additional-properties=dateLibrary=java8 \
  --additional-properties=interfaceOnly=true \
  --additional-properties=skipDefaultInterface=false \
  --additional-properties=documentationProvider=none \
  --additional-properties=useBeanValidation=true \
  --additional-properties=artifactVersion=${API_RELEASE} \
  --additional-properties=artifactId=${JAR_NAME} \
  --additional-properties=groupId=${GROUP_ID} \
  --additional-properties=parentVersion=2.7.8 \
  --additional-properties=omitGradleWrapper=true \
  --global-property=supportingFiles$DEBUG_MODELS \
  --global-property=apis,models,modelDocs=false\
  -t /local/generators/${GENERATOR}/templates \
  -g ${GENERATOR} \
  -o /local/${OUTPUT_DIR}

mkdir -p $OUTPUT_DIR/src/main/openapi
cp ${DEFINITIONS}/${MODULE}.${API_VERSION}.openapi.yaml  $OUTPUT_DIR/src/main/openapi/${MODULE}.${API_VERSION}.openapi.yaml







