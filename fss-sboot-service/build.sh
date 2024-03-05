#!/usr/bin/env bash

set -e
set -x

./collect-api-version.py
TAG=$(git describe --tags --abbrev=0)

export PUBLISH=${1:-no}



USER=$(id -u)

mvn -P prod clean package -DskipTests -Drevision=$TAG


export IMAGE=kgignatyev/ladder-service:$TAG

#docker buildx build --platform=linux/amd64 --build-arg APP_VERSION=$TAG -t $IMAGE .
docker build --build-arg APP_VERSION=$TAG -t $IMAGE .

if [ $PUBLISH == "yes" ]; then
  docker push $IMAGE
fi

