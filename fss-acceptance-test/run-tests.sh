#!/usr/bin/env bash

set -e

SPRING_PROFILE=${SPRING_PROFILE:-local}

test_info="local execution at $(date)"

mvnCmd=mvn

if  [[ "debug" == "$1" ]]; then
  export MAVEN_DEBUG_ADDRESS=7000
  mvnCmd=mvnDebug
  shift
fi


#set -x

CUCUMBER_PARAMS=" --tags \"not @scalability\""
if [[ "" != "$1" ]]; then
  CUCUMBER_PARAMS="$*"
fi

rm -fR target/*

timestamp=$(date +%Y%m%d%H%M%S)

set -x
$mvnCmd  -V package exec:java \
    -DskipTests \
    -Dspring.profiles.active=${SPRING_PROFILE} \
    -Dexec.mainClass=io.cucumber.core.cli.Main \
    -Dspring.config.location=file:./src/main/resources/application.yaml,file:./config/application-$SPRING_PROFILE.properties \
    -Dexec.args="$CUCUMBER_PARAMS" | tee target/output-$timestamp.txt


mvn -P reporting cluecumber:reporting

